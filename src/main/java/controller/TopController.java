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
	public String welcome(Model model) {

		return "Welcome";
	}

	@RequestMapping(value = "/Home")
	public String home(Model model, HttpServletRequest request) {

		List<TopList> toplist = listManager.getTopListFromDB();
		model.addAttribute("username", request.getSession().getAttribute("username"));
		model.addAttribute("TopList", toplist);

		return "home";
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public String addListItem(HttpServletRequest request, Model model, @RequestParam("Product") String product,
			@RequestParam("Producturl") String producturl) {

		System.out.println("Entered additem");
		listManager.addListItem(product, producturl);
		List<TopList> toplist = listManager.getTopListFromDB();
		model.addAttribute("username", request.getSession().getAttribute("username"));
		model.addAttribute("TopList", toplist);

		return "home";
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) {

		List<TopList> toplist = listManager.getTopListFromDB();

		if (userManager.handleUserLogin(userName, passWord)) {
			request.getSession().setAttribute("username", userName);
			model.addAttribute("username", userName);
			model.addAttribute("TopList", toplist);
			System.out.println("Du lyckades logga in");
			return "home";

		} else
			model.addAttribute("error", "invalid username or password");

		System.out.println("Du misslyckades att logga in");
		return "Welcome";
	}

	@RequestMapping(value = "/Register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, Model model, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) {

		List<TopList> toplist = listManager.getTopListFromDB();

		if (userManager.addUser(userName, passWord)) {
			request.getSession().setAttribute("username", userName);
			model.addAttribute("username", userName);
			model.addAttribute("TopList", toplist);
			System.out.println("Du lyckades registrera dig");
			return "home";

		} else

			System.out.println("Du misslyckades att registrera dig");
		return "error";
	}

	@RequestMapping(value = "/Logout")
	public String logout(HttpServletRequest request) {
		
		request.getSession().removeAttribute("username");
		
		return "Welcome";
	}

}
