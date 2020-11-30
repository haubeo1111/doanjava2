package com.example.demo.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Services.TangKemService;
import com.example.demo.Services.LoaiDTService;
import com.example.demo.dto.TangKemDTO;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.models.ChucVu;
import com.example.demo.models.TangKem;
import com.example.demo.models.Employee;
import com.example.demo.models.LoaiDT;

@Controller
@RequestMapping("/TangKem")
public class TangKemController {
@Autowired
TangKemService TangKemService;
@Autowired
LoaiDTService loaiDTService;
@GetMapping("/")
public String index(Model model) {
	TangKemDTO dto=new TangKemDTO();
	model.addAttribute("dto",dto);
	model.addAttribute("action","/TangKem/saveorupdate");
	return "register-TangKem";
}

@ModelAttribute(name="loaidt")
public List<LoaiDT> getAll(){
	
return  (List<LoaiDT>) loaiDTService.getAll();
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
public String save(ModelMap model,@ModelAttribute("dto")TangKemDTO dto) {
	dto.setId(random());
	//public String save(ModelMap model){
//		System.out.println("hau bbb000 ");
	//
	Optional<TangKem> optionalstaff=TangKemService.find(idd);
	
	//System.out.println("hau bbb1111 ");
	TangKem staff=null;
	String image="logo.png";
	//System.out.println("hau bbb2222 ");
	Path path=Paths.get("uploads/");
	//System.out.println("hau bbb ");
	if(optionalstaff.isPresent()) {
//		//save
		if(dto.getHinhanh().isEmpty()) {
		image=optionalstaff.get().getHinhanh();	
		}else {
			try {
			InputStream inputStream=dto.getHinhanh().getInputStream();
				Files.copy(inputStream,path.resolve(dto.getHinhanh().getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			image=dto.getHinhanh().getOriginalFilename().toString();
		} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			 staff=new TangKem(idd,dto.getName(),dto.getMatk(),dto.getNgaynhap(),image,dto.getSoluong(),
						dto.getGianhap(),0);

			TangKemService.save(staff);
			idd=-1;
			return "register-TangKem";
		}
	}else {
//		//add new.
	if(!dto.getHinhanh().isEmpty()) {
		try {
			InputStream inputStream=dto.getHinhanh().getInputStream();
			Files.copy(inputStream,path.resolve(dto.getHinhanh().getOriginalFilename()), 
					StandardCopyOption.REPLACE_EXISTING);
			image=dto.getHinhanh().getOriginalFilename().toString();
		} catch (Exception e) {
//			// TODO: handle exception
			e.printStackTrace(); 
	}
	}
	}

//dto.setId(random());
		 staff=new TangKem(dto.getId(),dto.getName(),dto.getMatk(),dto.getNgaynhap(),image,dto.getSoluong(),
		dto.getGianhap(),0);

	TangKemService.save(staff);
	return "register-TangKem";

	}



public static long idd=-1;
@RequestMapping("/edit/{id}")
public String edit(ModelMap model,@PathVariable(name="id")Long id) {
Optional<TangKem> optionalstaff=TangKemService.find(id);
TangKemDTO dto=null;
if(optionalstaff.isPresent()) {
	TangKem st=optionalstaff.get();
	File file=new File("uploads/"+st.getHinhanh());
	FileInputStream input; 
	try {
		input=new FileInputStream(file);
		MultipartFile multiphoto=new MockMultipartFile("file",file.getName(),"text/plain",
				IOUtils.toByteArray(input)); 
		dto=new TangKemDTO(st.getId(),st.getName(),st.getMatk(),st.getNgaynhap(),multiphoto,st.getSoluong(),st.getGianhap(),0);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	model.addAttribute("dto",dto);
	model.addAttribute("action","/TangKem/saveorupdate");
	idd=st.getId();
}else {
	model.addAttribute("dto",new EmployeeDTO());
	model.addAttribute("action","/TangKem/saveorupdate");
}
//model.addAttribute("action","/employee/update");
return "register-TangKem";
}
 @Autowired
    JdbcTemplate jdbcT;
@RequestMapping("/delete/{id}")
public String delete(ModelMap model,@PathVariable(name="id")Long id
		,HttpServletRequest request,RedirectAttributes redirect) {
//employeeService.isdelete(id);
 String sql="update TangKem set isdelete=1 where id=?";
 TangKem TangKem=new  TangKem(id);
 jdbcT.update(sql,new Object[] {TangKem.getId()});
request.getSession().setAttribute("employeelist", null);
 redirect.addAttribute("id", 1).addFlashAttribute("message", "Account created!");
//model.addAttribute("list1",employeeService.findAll());
return "redirect:/TangKem/page/{id}";
}
@RequestMapping("/xoanhieu")
public String deletes(ModelMap model,@RequestParam("xoa[]")List<Long> ll,
		HttpServletRequest request,RedirectAttributes redirect) {
	for(int i=0;i<ll.size();i++) {
		System.out.println("so la : "+ll.get(i));
		Long g=ll.get(i);
		 String sql="update TangKem set isdelete=1 where id=?";
		 TangKem TangKem=new  TangKem(g);
		 jdbcT.update(sql,new Object[] {TangKem.getId()});
		//employeeService.isdelete(g);
	}
	request.getSession().setAttribute("employeelist", null);
	 redirect.addAttribute("id", 1).addFlashAttribute("message", "Account created!");
	return "redirect:/TangKem/page/{id}";
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
	return "redirect:/TangKem/page/{id}";
}
@GetMapping("/page/{pageNumber}")
public String showEmployeePage(HttpServletRequest request, 
		@PathVariable int pageNumber, Model model) {
	PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
	int pagesize = 3;
	model.addAttribute("chucvu",loaiDTService.getAll());
	List<TangKem> list =(List<TangKem>) TangKemService.getAll();
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
	int begin=1;
	if(current==1) {
	 begin = Math.max(current, current - list.size());
	}else {
		 begin = Math.max(current-1, current - list.size());
	}
	int end = Math.min(begin + 3, pages.getPageCount());
	int totalPageCount = pages.getPageCount();
	String baseUrl = "/TangKem/page/";

	model.addAttribute("beginIndex", begin);
	model.addAttribute("endIndex", end);
	model.addAttribute("currentIndex", current);
	model.addAttribute("totalPageCount", totalPageCount);
	model.addAttribute("baseUrl", baseUrl);
	model.addAttribute("employees", pages);

	return "view-TangKem";
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
		return "/TangKem/page";	
	}	
	List<TangKem> list = TangKemService.findup(xuly(s));	
	if (list == null) {	
		return "/TangKem/page";	
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
	String baseUrl = "/employee/page/";	
	model.addAttribute("beginIndex", begin);	
	model.addAttribute("endIndex", end);	
	model.addAttribute("currentIndex", current);	
	model.addAttribute("totalPageCount", totalPageCount);	
	model.addAttribute("baseUrl", baseUrl);	
	model.addAttribute("employees", pages);	
	return "view-TangKem";	
}
@GetMapping("/search/pageNumber")	
public String searchpage(@RequestParam("sa") String s, Model model, HttpServletRequest request
		) {	
	if (s.equals("")) {	
		return "/TangKem/page";	
	}	
	List<TangKem> list = TangKemService.getAll();	
	if (list == null) {	
		return "/TangKem/page";	
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
	String baseUrl = "/TangKem/page/";	
	model.addAttribute("beginIndex", begin);	
	model.addAttribute("endIndex", end);	
	model.addAttribute("currentIndex", current);	
	model.addAttribute("totalPageCount", totalPageCount);	
	model.addAttribute("baseUrl", baseUrl);	
	model.addAttribute("employees", pages);	
	return "view-TangKem";	
}
}
