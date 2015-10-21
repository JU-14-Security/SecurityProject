package controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.ListManager;
import model.TopList;
import model.User;
import model.UserManager;

/**
 * Class for handling request.
 * 
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
	
	

	@RequestMapping(value ={ "/Welcome", "/{s}/**", "/{url}"})
	public ModelAndView welcome() {

		return new ModelAndView("Welcome");
	}

	@RequestMapping(value = "/Home")
	public ModelAndView home(HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		
		if(request.getSession().getAttribute("username") != null)
		{
			model.setViewName("home");
			User user = (User)request.getSession().getAttribute("username");
			int userId = user.getId();
			List<TopList> toplist = listManager.getTopListFromDB();
			List<TopList> userList = listManager.getUserTopListFromDB(userId);
			model.addObject("username", request.getSession().getAttribute("username"));
			model.addObject("userList", userList);
			model.addObject("TopList", toplist);
		}
		else
		{
			model.setViewName("redirect:/Welcome");
		}

		return model;
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addListItem(HttpServletRequest request, @RequestParam("Product") String product,
			@RequestParam("Producturl") String producturl) {

		ModelAndView model = new ModelAndView();
		
		if(request.getSession().getAttribute("username") != null)
		{
			User user = (User)request.getSession().getAttribute("username");
			System.out.println("Entered additem");
			listManager.addListItem(product, producturl, user.getId());
			List<TopList> toplist = listManager.getTopListFromDB();

			model.addObject("TopList", toplist);
			model.setViewName("redirect:/Home");
		}
		else
		{
			model.setViewName("redirect:/Welcome");
		}
		
		return model;
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) {

		ModelAndView model = new ModelAndView();
		List<TopList> toplist = listManager.getTopListFromDB();
		
		if (userManager.handleUserLogin(userName, passWord)) {
			setSessionUser(request, userName);
			
			model.addObject("TopList", toplist);
			System.out.println("Du lyckades logga in");
			model.setViewName("redirect:/Home");
			return model;

		} else{
			model.addObject("errorLog", "invalid username or password");

		System.out.println("Du misslyckades att logga in");
		model.setViewName("Welcome");
		return model;
		}
	}

	@RequestMapping(value = "/Register", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView register(HttpServletRequest request, @RequestParam(value="username", required=false) String userName,
			@RequestParam(value="password", required = false) String passWord) {

		try{
			
		
		ModelAndView model = new ModelAndView();
		List<TopList> toplist = listManager.getTopListFromDB();

		if(userName.isEmpty() || userName == null || passWord.isEmpty() || passWord == null)
		{
			model.setViewName("redirect:/Welcome");
			return model;
		}
		
		if (userManager.addUser(userName, passWord)) {
			
			model.addObject("TopList", toplist);
			System.out.println("Du lyckades registrera dig");
			// bellow currently not working
			//setSessionUser(request, userName); 
			model.setViewName("redirect:/Home");
			return model;

		} else{

			System.out.println("Du misslyckades att registrera dig");
			model.setViewName("Welcome");
			model.addObject("errorReg", "Username already exists");
			return model;
		}
		}catch(NullPointerException npe)
		{
			return new ModelAndView("redirect:/Welcome");
		}
		
	}

	@RequestMapping(value = "/Logout")
	public ModelAndView logout(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView("redirect:/Welcome");
		request.getSession().removeAttribute("username");
		
		return model;
	}
	
	/***
	 * Adds logged in user to session
	 * @author Joel
	 * @param request
	 * @param userName
	 */
	public void setSessionUser(HttpServletRequest request, String userName) {
		request.getSession().setAttribute("username", userManager.getUser(userName));
	}
}
