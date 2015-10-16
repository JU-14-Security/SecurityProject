package controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.UserManager;


/**
 * Class for handling request.
 * @author Erik Nylander
 *
 */
@Controller
public class TopController {
	UserManager userManager;

	/**
	 * Constructor for the Spring MVC Controller.
	 * 
	 * @param centerHandler
	 *            
	 */
	
	
	@Inject
	public TopController(UserManager um) {
		this.userManager=um;

	}
	
	public TopController() {

	}

	
	@RequestMapping (value = { "/", "/Home" })
	public String getList(Model model) {
		
		return "home";
	}
	
	@RequestMapping(value = "/Login", method=RequestMethod.POST)
	public String login(Model model , @RequestParam("Username") String userName, @RequestParam("Password") String passWord) {
		//TODO:här skall det senare komma kontroll av indatat och Exception hantering
		
		System.out.println("Entered Login");
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
		
		System.out.println("Entered Register");
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
