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

import com.example.demo.Services.NhaNuocService;
import com.example.demo.models.NhaNuoc;

@Controller
@RequestMapping("/NhaNuoc")
public class NhaNuocController {
	@Autowired
	NhaNuocService NhaNuocService;
	@RequestMapping("/")
	public String index(Model model) {
		NhaNuoc dto=new NhaNuoc();
		model.addAttribute("NhaNuoc",dto);
		model.addAttribute("action","/NhaNuoc/saveorupdate");
		return "register-NhaNuoc";
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
	public String save(ModelMap model,@ModelAttribute("NhaNuoc")NhaNuoc NhaNuoc) {
		NhaNuoc.setId(random());
		NhaNuocService.save(NhaNuoc);
	    NhaNuoc u=new NhaNuoc();
		model.addAttribute("NhaNuoc",u);
		return "register-NhaNuoc";
		
		}
	@PostMapping("/update")
	public String update(ModelMap model,@ModelAttribute("NhaNuoc")NhaNuoc NhaNuoc) {
		//NhaNuocService.update(NhaNuoc.getName(),NhaNuoc.getGender(),NhaNuoc.getBirthday(),NhaNuoc.getEmail()
			//	,NhaNuoc.getPhone(),NhaNuoc.getPassword(),NhaNuoc.getAddress(),NhaNuoc.getId());
		NhaNuocService.save(NhaNuoc);
		model.addAttribute("NhaNuoc",NhaNuoc);
		return "register-NhaNuoc";

		}
	@RequestMapping("/edit/{id}")
	public String edit(ModelMap model,@PathVariable(name="id")long id) {
		Optional<NhaNuoc> u= NhaNuocService.find(id);
		if(u.isPresent()) {
			model.addAttribute("NhaNuoc", u.get());
		}else {
			model.addAttribute("NhaNuoc", new NhaNuoc());
		}
		model.addAttribute("action", "/NhaNuoc/update");
		
	return "register-NhaNuoc";
	}
	 @Autowired
	    JdbcTemplate jdbcT;
	@RequestMapping("/delete/{id}")
	public String delete(ModelMap model,@PathVariable(name="id")long id
			,HttpServletRequest request,RedirectAttributes redirect) {
	
	 String sql="update NhaNuoc set isdelete=1 where id=?";
	 NhaNuoc NhaNuoc=new  NhaNuoc(id);
	 jdbcT.update(sql,new Object[] {NhaNuoc.getId()});
	request.getSession().setAttribute("employeelist", null);
	 redirect.addAttribute("id", 1);
	return "redirect:/NhaNuoc/page/{id}";
	}
	@RequestMapping("/xoanhieu")
	public String deletes(ModelMap model,@RequestParam("xoa[]")List<Long> ll,
			HttpServletRequest request,RedirectAttributes redirect) {
		for(int i=0;i<ll.size();i++) {
			
			Long g=ll.get(i);
			 String sql="update NhaNuoc set isdelete=1 where id=?";
			 NhaNuoc NhaNuoc =new  NhaNuoc(g);
			 jdbcT.update(sql,new Object[] {NhaNuoc.getId()});
			//employeeService.isdelete(g);
		}
		
		request.getSession().setAttribute("employeelist", null);
		 redirect.addAttribute("id", 1);
		 
		return "redirect:/NhaNuoc/page/{id}";
	}
	@GetMapping("/page")
	public String index(Model model,HttpServletRequest request
			,RedirectAttributes redirect) {
		request.getSession().setAttribute("employeelist", null);
		
		 redirect.addAttribute("id", 1);
		
		return "redirect:/NhaNuoc/page/{id}";
	}
	@GetMapping("/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request, 
			@PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
		int pagesize = 3;
		//model.addAttribute("customer",customerService.findAll());
		List<NhaNuoc> list =(List<NhaNuoc>) NhaNuocService.getAll();
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
		String baseUrl = "/NhaNuoc/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("employees", pages);

		return "view-NhaNuoc";
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
			return "/NhaNuoc/page";	
		}	
		List<NhaNuoc> list = NhaNuocService.findup(xuly(s));	
		if (list == null) {	
			return "/NhaNuoc/page";	
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
		String baseUrl = "/NhaNuoc/page/";	
		model.addAttribute("beginIndex", begin);	
		model.addAttribute("endIndex", end);	
		model.addAttribute("currentIndex", current);	
		model.addAttribute("totalPageCount", totalPageCount);	
		model.addAttribute("baseUrl", baseUrl);	
		model.addAttribute("employees", pages);	
		return "view-NhaNuoc";	
	}
	@GetMapping("/search/pageNumber")	
	public String searchpage(@RequestParam("sa") String s, Model model, HttpServletRequest request
			) {	
		if (s.equals("")) {	
			return "/NhaNuoc/page";	
		}	
		List<NhaNuoc> list = NhaNuocService.getAll();	
		if (list == null) {	
			return "/NhaNuoc/page";	
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
		String baseUrl = "/NhaNuoc/page/";	
		model.addAttribute("beginIndex", begin);	
		model.addAttribute("endIndex", end);	
		model.addAttribute("currentIndex", current);	
		model.addAttribute("totalPageCount", totalPageCount);	
		model.addAttribute("baseUrl", baseUrl);	
		model.addAttribute("employees", pages);	
		return "view-NhaNuoc";	
	}
}
