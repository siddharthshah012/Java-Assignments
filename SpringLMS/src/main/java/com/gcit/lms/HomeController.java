package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcit.lms.entity.Author;
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "welcome";
	}
		
	@RequestMapping(value = "/admin", method= RequestMethod.GET)
	public String admin(){
		return "admin";
	}
	
	@RequestMapping(value = "/author", method= RequestMethod.GET)
	public String author(){
		return "author";
	}
	
	@RequestMapping(value = "/viewAuthors", method= RequestMethod.GET)
	public String viewAuthor(Model model) throws SQLException{
		
		try {
			model.addAttribute("authors", adminService.getAllAuthors(1, null));
			List<Author> authors = new ArrayList<>();
			Integer authCount = adminService.getAuthorsCount();
			int pages = 0;
			if (authCount % 10 > 0) {
				pages = authCount / 10 + 1;
			} else {
				pages = authCount / 10;
			}
			model.addAttribute("pages", pages);		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "viewauthors";
	}
	@RequestMapping(value = "/addAuthor", method= RequestMethod.GET)
	public String addAuthor(Model model){
		
		return "addauthor";
	}
	
	
	
	
	
	
}
