package controller;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.ListManager;
import model.TopList;
import model.UserManager;
import service.ListDAO;

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

	@RequestMapping(value = "/Welcome")
	public ModelAndView welcome() {

		return new ModelAndView("Welcome");
	}

	@RequestMapping(value = "/Home")
	public ModelAndView home(HttpServletRequest request) {

		ModelAndView model = new ModelAndView("home");
		List<TopList> toplist = listManager.getTopListFromDB();
		model.addObject("username", request.getSession().getAttribute("username"));
		model.addObject("TopList", toplist);

		return model;
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addListItem(HttpServletRequest request, @RequestParam("Product") String product,
			@RequestParam("Producturl") String producturl) {

		ModelAndView model = new ModelAndView();
		System.out.println("Entered additem");
		listManager.addListItem(product, producturl);
		List<TopList> toplist = listManager.getTopListFromDB();
		//model.addObject("username", request.getSession().getAttribute("username"));
		model.addObject("TopList", toplist);
		model.setViewName("redirect:/Home");
		return model;
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) {

		ModelAndView model = new ModelAndView();
		List<TopList> toplist = listManager.getTopListFromDB();

		if (userManager.handleUserLogin(userName, passWord)) {
			request.getSession().setAttribute("username", userName);
			//model.addObject("username", userName);
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

	@RequestMapping(value = "/Register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) {

		ModelAndView model = new ModelAndView();
		List<TopList> toplist = listManager.getTopListFromDB();

		if (userManager.addUser(userName, passWord)) {
			request.getSession().setAttribute("username", userName);
			//model.addObject("username", userName);
			model.addObject("TopList", toplist);
			System.out.println("Du lyckades registrera dig");
			model.setViewName("redirect:/Home");
			return model;

		} else{

			System.out.println("Du misslyckades att registrera dig");
			model.setViewName("Welcome");
			model.addObject("errorReg", "Username already exists");
			return model;
		}
		
	}

	@RequestMapping(value = "/Logout")
	public ModelAndView logout(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView("redirect:/Welcome");
		request.getSession().removeAttribute("username");
		
		return model;
	}

}
