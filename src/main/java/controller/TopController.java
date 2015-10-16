package controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.ListManager;
import model.TopList;
import model.UserManager;
import service.ListDAO;


/**
 * Class for handling request.
 * @author Erik Nylander
 *
 */
@Controller
public class TopController {
	UserManager userManager;
	ListManager listManager;

	/**
	 * Constructor for the Spring MVC Controller.
	 * 
	 * @param centerHandler
	 *            
	 */
	
	
	@Inject
	public TopController(UserManager um, ListManager lm) {
		this.userManager = um;
		this.listManager = lm;

	}
	
	public TopController() {

	}

	@RequestMapping (value = { "/", "/Home" })
	public String getList(Model model) {
		
		List<TopList> toplist=listManager.getTopListFromDB();
		model.addAttribute("TopList",toplist);
		
		return "home";
	}
	
	@RequestMapping (value = "/addItem",method=RequestMethod.POST)
	public String addListItem(Model model,  @RequestParam("Product") String product, @RequestParam("Producturl") String producturl) {
		
		System.out.println("Entered additem");
		listManager.addListItem(product, producturl);
		List<TopList> toplist=listManager.getTopListFromDB();
		model.addAttribute("TopList",toplist);
		
		return "home";
	}
	
	@RequestMapping(value = "/Login", method=RequestMethod.POST)
	public String login(Model model , @RequestParam("Username") String userName, @RequestParam("Password") String passWord) {
		//TODO:här skall det senare komma kontroll av indatat och Exception hantering
		
		
		System.out.println(userName +" "+ passWord);
		
		if(userManager.handleUserLogin(userName, passWord)){
			
			model.addAttribute("username", userName);
			System.out.println("Du lyckades logga in");
			return "home";
			
		}else
			System.out.println("Du misslyckades att logga in");
			return "error";
	}
	
	
	@RequestMapping(value="/Register", method=RequestMethod.POST)
	public String register(Model model, @RequestParam("Username") String userName, @RequestParam("Password") String passWord){
		//TODO:här skall det senare komma kontroll av indatat och Exception hantering
		
		
		System.out.println(userName +" "+ passWord);
		
		
		if(userManager.addUser(userName, passWord)){
			model.addAttribute("username",userName);
			System.out.println("Du lyckades registrera dig");
			return "home";
			
		}else
			
			System.out.println("Du misslyckades att registrera dig");
			return "error";
	}
	
}
