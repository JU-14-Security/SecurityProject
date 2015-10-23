package controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.Filter;
import model.ListManager;
import model.TopList;
import model.User;
import model.UserManager;

/**
 * Class for handling request.
 * 
 * @author Erik Nylander, Robin Ejderholt, Joel Julle ToaRulle
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

	@RequestMapping(value = { "/Welcome", "/{s}/**", "/{url}" })
	public ModelAndView welcome(HttpServletRequest request) {

		return new ModelAndView("Welcome");
	}

	@RequestMapping(value = "/Home")
	public ModelAndView home(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();

		if (request.getSession().getAttribute("username") != null) {
			System.out.println("Entered method in home");
			model.setViewName("home");
			User user = (User) request.getSession().getAttribute("username");
			int userId = user.getId();
			List<TopList> toplist = listManager.getTopListFromDB();
			List<TopList> userList = listManager.getUserTopListFromDB(userId);
			model.addObject("username", request.getSession().getAttribute("username"));
			model.addObject("userList", userList);
			model.addObject("TopList", toplist);
		} else {

			model.setViewName("redirect:/Welcome");
		}

		return model;
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addListItem(HttpServletRequest request, @RequestParam("Product") String product,
			@RequestParam("Producturl") String producturl) throws Exception {

		ModelAndView model = new ModelAndView();
		System.out.println("Entered additem");
		User user1 = (User) request.getSession().getAttribute("username");
		System.out.println(user1.getUsername());

		// Joels skit om urlfuckidering...
		Filter filter = new Filter();
		product = filter.cleanProductInput(product);
		producturl = filter.cleanProductInput(producturl);

		if (request.getSession().getAttribute("username") != null) {
			User user = (User) request.getSession().getAttribute("username");
			if (filter.validateProduktUrl(producturl)) {
				System.out.println("adding valid url");
				listManager.addListItem(product, producturl, user.getId());
			}
			model.setViewName("redirect:/Home");
		} else {
			model.setViewName("redirect:/Welcome");
		}
		return model;
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) throws Exception {

		ModelAndView model = new ModelAndView();

		if (userManager.handleUserLogin(userName, passWord)) {
			setSessionUser(request, userName, passWord);
			model.setViewName("redirect:/Home");

		} else {
			model.addObject("errorLog", "invalid username or password");
			model.setViewName("Welcome");
		}
		return model;
	}

	@RequestMapping(value = "/Register", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView register(HttpServletRequest request,
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "password", required = false) String passWord) throws Exception {

		try {

			ModelAndView model = new ModelAndView();

			if (userName.isEmpty() || userName == null || passWord.isEmpty() || passWord == null) {
				model.setViewName("redirect:/Welcome");
				return model;
			}

			if (userManager.addUser(userName, passWord)) {
				setSessionUser(request, userName, passWord);
				model.setViewName("redirect:/Home");
				return model;

			} else {

				model.setViewName("Welcome");
				model.addObject("errorReg", "Username already exists");
				return model;
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			return new ModelAndView("redirect:/Welcome");
		}

	}

	@RequestMapping(value = "/Logout")
	public ModelAndView logout(HttpServletRequest request) {

		removeSessionUser(request);
		ModelAndView model = new ModelAndView("redirect:/Welcome");
		return model;
	}

	public void removeSessionUser(HttpServletRequest request) {
		request.getSession().removeAttribute("username");

	}

	/***
	 * Adds logged in user to session
	 * 
	 * @author Joel
	 * @param request
	 * @param userName
	 * @throws IndexOutOfBoundsException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws PersistenceException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public void setSessionUser(HttpServletRequest request, String userName, String passWord) throws Exception {
		request.getSession().setAttribute("username", userManager.getUser(userName, passWord));
	}

	@RequestMapping(value = "/Update")
	public ModelAndView update(HttpServletRequest request, @RequestParam("id") String id,
			@RequestParam("amount") String amount) throws Exception {
		System.out.println("id:" + id + " amount: " + amount);

		listManager.updateListItemValue(id, amount);

		ModelAndView model = new ModelAndView("home");
		model.addObject("successPayment", "Payment was successfull");
		return model;
	}

	@ExceptionHandler(RuntimeException.class)
	public String databaseConnectionError(Model model) {
		model.addAttribute("error", "Snopp");
		return "error";
	}
}
