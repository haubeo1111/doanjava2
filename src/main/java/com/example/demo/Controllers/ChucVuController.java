package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Repository.ChucVuRepository;
import com.example.demo.Repository.QuyenChucNang;
import com.example.demo.models.ChucVu;


@Controller
@RequestMapping("/chucvu")
public class ChucVuController {
@Autowired
ChucVuRepository chucVuRepository;
@Autowired
QuyenChucNang QuyenChucNang;
@RequestMapping("/")
public String index(Model model,HttpSession session) {
	List<String > ten=QuyenChucNang.getAll("/chucvu/");
	if(session.getAttribute("tendangnhap")==null) {
		return "redirect:/Login/";
	}
	String tendangnhap=session.getAttribute("tendangnhap").toString();
	for(int i=0;i<ten.size();i++) {
		if(ten.get(i).equalsIgnoreCase(tendangnhap)) {
	ChucVu dto=new ChucVu();
	model.addAttribute("chucvu",dto);
	model.addAttribute("action","/chucvu/saveorupdate");
	return "register-chucvu";
		}
	}
	return "layout/main-layout1";
		
}


public long random() {
	Random rd = new Random();
	long longNumber = rd.nextLong();
	if(longNumber<0) {return (-1*longNumber);}
	return longNumber;
}
public int random1() {
	Random rd = new Random();
	int a=rd.nextInt();
	if(a<0) {return (-1*a);}
	return a;
}
//Kiem Tra Loi
//tat ca loi co the xay ra
//chon dong tren trang
//hien thi tong so
//tim kiem giu lai
//
@PostMapping("/saveorupdate")
public String save(ModelMap model,@Valid @ModelAttribute("chucvu")ChucVu chucvu,BindingResult result,
		HttpSession session) {
	//chucvu.setId(random());
	
	if(session.getAttribute("tendangnhap")==null) {
		return "redirect:/Login/";
	}
	 if(result.hasErrors()) {
		 
		 model.addAttribute("saidd","sai dinh dang");
		 return  "redirect:/chucvu/";
     }
	 chucvu.setId(random1());
	chucvu.setIsdelete(0);
	chucVuRepository.save(chucvu);
    ChucVu u=new ChucVu();
	model.addAttribute("chucvu",u);
	 model.addAttribute("saidd","");
	return "register-chucvu";
	
	}
@PostMapping("/update")
public String update(ModelMap model,@Valid @ModelAttribute("chucvu")ChucVu chucvu,HttpSession session,
		BindingResult result) {
	//chucvuService.update(chucvu.getName(),chucvu.getGender(),chucvu.getBirthday(),chucvu.getEmail()
	
	//	,chucvu.getPhone(),chucvu.getPassword(),chucvu.getAddress(),chucvu.getId());
	if(session.getAttribute("tendangnhap")==null) {
		return "redirect:/Login/";
	}
	
	chucVuRepository.edit(chucvu);
	model.addAttribute("chucvu",chucvu);
	return "register-chucvu";

	}
@RequestMapping("/edit/{id}")
public String edit(ModelMap model,@PathVariable(name="id")int id) {
	Optional<ChucVu> u= chucVuRepository.findById(id);
	if(u.isPresent()) {
		model.addAttribute("chucvu", u.get());
	}else {
		model.addAttribute("chucvu", new ChucVu());
	}
	model.addAttribute("action", "/chucvu/update");
	
return "register-chucvu";
}
 @Autowired
    JdbcTemplate jdbcT;
@RequestMapping("/delete/{id}")
public String delete(ModelMap model,@PathVariable(name="id")int id
		,HttpServletRequest request,RedirectAttributes redirect) {
//employeeService.isdelete(id);
 String sql="update chucvu set isdelete=1 where id=?";
 ChucVu chucvu=new  ChucVu(id);
 jdbcT.update(sql,new Object[] {chucvu.getId()});
request.getSession().setAttribute("employeelist", null);
 redirect.addAttribute("id", 1).addFlashAttribute("message", "Account created!");
//model.addAttribute("list1",employeeService.findAll());

return "redirect:/chucvu/page/{id}";
}
@RequestMapping("/xoanhieu")
public String deletes(ModelMap model,@RequestParam("xoa[]")List<Integer> ll,
		HttpServletRequest request,RedirectAttributes redirect) {
	for(int i=0;i<ll.size();i++) {
		System.out.println("so la : "+ll.get(i));
		int g=ll.get(i);
		 String sql="update chucvu set isdelete=1 where id=?";
		 ChucVu chucvu =new  ChucVu(g);
		 jdbcT.update(sql,new Object[] {chucvu.getId()});
		//employeeService.isdelete(g);
	}
	
	request.getSession().setAttribute("employeelist", null);
	 redirect.addAttribute("id", 1).addFlashAttribute("message", "Account created!");
	return "redirect:/chucvu/page/{id}";
}
@GetMapping("/page")
public String index(Model model,HttpServletRequest request
		,RedirectAttributes redirect) {
	request.getSession().setAttribute("employeelist", null);
	// RedirectView redirectView = new RedirectView();
	  //  redirectView.setUrl("http://www.yahoo.com");
	    //return redirectView;
	 redirect.addAttribute("id", 1).addFlashAttribute("message", "Account created!");
	   //return "redirect:/accounts/{id}";
	//if(model.asMap().get("success") != null)
	//	redirect.addFlashAttribute("success",model.asMap().get("success").toString());
	return "redirect:/chucvu/page/{id}";
}
@GetMapping("/page/{pageNumber}")
public String showEmployeePage(HttpServletRequest request, 
		@PathVariable int pageNumber, Model model) {
	PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
	int pagesize = 3;
	//model.addAttribute("customer",customerService.findAll());
	List<ChucVu> list =(List<ChucVu>) chucVuRepository.findAll();
	System.out.println(list.size());
	if (pages == null) {
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);
	} else {
		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
	}
	request.getSession().setAttribute("employeelist", pages);
	int current = pages.getPage() + 1;
	int begin = Math.max(1, current - list.size());
	int end = Math.min(begin + 5, pages.getPageCount());
	int totalPageCount = pages.getPageCount();
	String baseUrl = "/chucvu/page/";

	model.addAttribute("beginIndex", begin);
	model.addAttribute("endIndex", end);
	model.addAttribute("currentIndex", current);
	model.addAttribute("totalPageCount", totalPageCount);
	model.addAttribute("baseUrl", baseUrl);
	model.addAttribute("employees", pages);

	return "view-chucvu";
}
public String xuly(String name) {
name.trim();
String tim="";
List<String> T=new ArrayList<>();
String[] words = name.split("\\s");
for (String w : words) {
	   System.out.println(w);
	   T.add(w);
}
for(int i=0;i<T.size();i++) {
	if(i==T.size()-1) {
		tim=tim+T.get(i);
	}else {
	tim=tim+T.get(i)+"|" ;
	}
}
 System.out.println("so ls: "+ tim);
			return tim;
}
@GetMapping("/search")	
public String search(@RequestParam("s") String s, Model model, HttpServletRequest request	
	) {	
	if (s.equals("")) {	
		return "/chucvu/page";	
	}	
	List<ChucVu> list = chucVuRepository.findup(xuly(s));	
	if (list == null) {	
		return "/chucvu/page";	
	}	
	PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");	
	int pagesize = 3;	
	pages = new PagedListHolder<>(list);	
	pages.setPageSize(pagesize);	
		
	//final int goToPage = pageNumber - 1;	
	//if (goToPage <= pages.getPageCount() && goToPage >= 0) {	
	//	pages.setPage(goToPage);	
	//}	
	//request.getSession().setAttribute("employeelist", pages);	
	int current = pages.getPage() + 1;	
	int begin = Math.max(1, current - list.size());	
	int end = Math.min(begin + 5, pages.getPageCount());	
	int totalPageCount = pages.getPageCount();	
	String baseUrl = "/chucvu/page/";	
	model.addAttribute("beginIndex", begin);	
	model.addAttribute("endIndex", end);	
	model.addAttribute("currentIndex", current);	
	model.addAttribute("totalPageCount", totalPageCount);	
	model.addAttribute("baseUrl", baseUrl);	
	model.addAttribute("employees", pages);	
	return "view-chucvu";	
}
@GetMapping("/search/pageNumber")	
public String searchpage(@RequestParam("sa") String s, Model model, HttpServletRequest request
		) {	
	if (s.equals("")) {	
		return "/chucvu/page";	
	}	
	List<ChucVu> list = chucVuRepository.findAll();	
	if (list == null) {	
		return "/chucvu/page";	
	}	
	PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");	
	int pagesize = 3;	
	pages = new PagedListHolder<>(list);	
	pages.setPageSize(pagesize);	
		
	final int goToPage = Integer.parseInt(s) - 1;	
	if (goToPage <= pages.getPageCount() && goToPage >= 0) {	
		pages.setPage(goToPage);	
	}	
	request.getSession().setAttribute("employeelist", pages);	
	int current = pages.getPage() + 1;	
	int begin = Math.max(1, current - list.size());	
	int end = Math.min(begin + 5, pages.getPageCount());	
	int totalPageCount = pages.getPageCount();	
	String baseUrl = "/chucvu/page/";	
	model.addAttribute("beginIndex", begin);	
	model.addAttribute("endIndex", end);	
	model.addAttribute("currentIndex", current);	
	model.addAttribute("totalPageCount", totalPageCount);	
	model.addAttribute("baseUrl", baseUrl);	
	model.addAttribute("employees", pages);	
	return "view-chucvu";	
}
}
