package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Services.LoaiDTService;
import com.example.demo.models.LoaiDT;


@Controller
@RequestMapping("/LoaiDT")
public class LoaiDTController {
@Autowired
LoaiDTService loaiDTService;
@RequestMapping("/")
public String index(Model model) {
	LoaiDT dto=new LoaiDT();
	model.addAttribute("loaiDT",dto);
	model.addAttribute("action","/LoaiDT/saveorupdate");
	return "register-loaiDT";
}


public long random() {
	Random rd = new Random();
	long longNumber = rd.nextLong();
	if(longNumber<0) {return (-1*longNumber);}
	return longNumber;
}
//Kiem Tra Loi
//tat ca loi co the xay ra
//chon dong tren trang
//hien thi tong so
//tim kiem giu lai
//
@PostMapping("/saveorupdate")
public String save(ModelMap model,@ModelAttribute("loaiDT")LoaiDT loaiDT) {
	//loaiDT.setId(random());
	loaiDT.setIsdelete(0);
	loaiDTService.save(loaiDT);
    LoaiDT u=new LoaiDT();
	model.addAttribute("loaiDT",u);
	model.addAttribute("action","/LoaiDT/saveorupdate");
	return "register-loaiDT";
	
	}
@PostMapping("/update")
public String update(ModelMap model,@ModelAttribute("loaiDT")LoaiDT loaiDT) {
	//loaiDTService.update(loaiDT.getName(),loaiDT.getGender(),loaiDT.getBirthday(),loaiDT.getEmail()
		//	,loaiDT.getPhone(),loaiDT.getPassword(),loaiDT.getAddress(),loaiDT.getId());
	loaiDTService.save(loaiDT);
	model.addAttribute("loaiDT",loaiDT);
	return "register-loaiDT";

	}
@RequestMapping("/edit/{id}")
public String edit(ModelMap model,@PathVariable(name="id")String id) {
	Optional<LoaiDT> u= loaiDTService.findById(id);
	if(u.isPresent()) {
		model.addAttribute("loaiDT", u.get());
	}else {
		model.addAttribute("loaiDT", new LoaiDT());
	}
	model.addAttribute("action", "/LoaiDT/update");
	
return "register-loaiDT";
}
 @Autowired
    JdbcTemplate jdbcT;
@RequestMapping("/delete/{id}")
public String delete(ModelMap model,@PathVariable(name="id")String id
		,HttpServletRequest request,RedirectAttributes redirect) {
//employeeService.isdelete(id);
 String sql="update loaiDT set isdelete=1 where maloaidt=?";
 LoaiDT loaiDT=new  LoaiDT(id);
 jdbcT.update(sql,new Object[] {loaiDT.getMaloaidt()});
request.getSession().setAttribute("employeelist", null);
 redirect.addAttribute("id", 1);
 
//model.addAttribute("list1",employeeService.findAll());

return "redirect:/LoaiDT/page/{id}";
}
@RequestMapping("/xoanhieu")
public String deletes(ModelMap model,@RequestParam("xoa[]")List<String> ll,
		HttpServletRequest request,RedirectAttributes redirect) {
	for(int i=0;i<ll.size();i++) {
		System.out.println("so la : "+ll.get(i));
	String g=ll.get(i);
		 String sql="update loaiDT set isdelete=1 where maloaidt=?";
		 LoaiDT loaiDT =new  LoaiDT(g);
		 jdbcT.update(sql,new Object[] {loaiDT.getMaloaidt()});
		//employeeService.isdelete(g);
	}
	
	request.getSession().setAttribute("employeelist", null);
	 redirect.addAttribute("id", 1).addFlashAttribute("message", "Account created!");
	return "redirect:/LoaiDT/page/{id}";
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
	return "redirect:/LoaiDT/page/{id}";
}
@GetMapping("/page/{pageNumber}")
public String showEmployeePage(HttpServletRequest request, 
		@PathVariable int pageNumber, Model model) {
	PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
	int pagesize = 3;
	//model.addAttribute("customer",customerService.findAll());
	List<LoaiDT> list =(List<LoaiDT>) loaiDTService.getAll();
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
	String baseUrl = "/LoaiDT/page/";

	model.addAttribute("beginIndex", begin);
	model.addAttribute("endIndex", end);
	model.addAttribute("currentIndex", current);
	model.addAttribute("totalPageCount", totalPageCount);
	model.addAttribute("baseUrl", baseUrl);
	model.addAttribute("employees", pages);

	return "view-loaiDT";
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
		return "/LoaiDT/page";	
	}	
	List<LoaiDT> list = loaiDTService.findup(xuly(s));	
	if (list == null) {	
		return "/LoaiDT/page";	
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
	String baseUrl = "/loaiDT/page/";	
	model.addAttribute("beginIndex", begin);	
	model.addAttribute("endIndex", end);	
	model.addAttribute("currentIndex", current);	
	model.addAttribute("totalPageCount", totalPageCount);	
	model.addAttribute("baseUrl", baseUrl);	
	model.addAttribute("employees", pages);	
	return "view-loaiDT";	
}
@GetMapping("/search/pageNumber")	
public String searchpage(@RequestParam("sa") String s, Model model, HttpServletRequest request
		) {	
	if (s.equals("")) {	
		return "/LoaiDT/page";	
	}	
	List<LoaiDT> list = loaiDTService.getAll();	
	if (list == null) {	
		return "/LoaiDT/page";	
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
	String baseUrl = "/LoaiDT/page/";	
	model.addAttribute("beginIndex", begin);	
	model.addAttribute("endIndex", end);	
	model.addAttribute("currentIndex", current);	
	model.addAttribute("totalPageCount", totalPageCount);	
	model.addAttribute("baseUrl", baseUrl);	
	model.addAttribute("employees", pages);	
	return "view-loaiDT";	
}
}
