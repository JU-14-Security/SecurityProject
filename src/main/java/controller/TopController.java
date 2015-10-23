package controller;

import java.util.List;

import javax.inject.Inject;
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
 * @author Erik Nylander
 *
 */
@Controller
public class TopController {
	UserManager userManager;
	ListManager listManager;
	Filter filter;

	/**
	 * Constructor for the Spring MVC Controller.
	 * 
	 * @param centerHandler
	 * 
	 */

	@Inject
	public TopController(UserManager um, ListManager lm, Filter filter) {
		this.userManager = um;
		this.listManager = lm;
		this.filter = filter;
	}

	public TopController() {

	}

	/**
	 * Method which takes request on /Welcome and any other random Url which the
	 * user might have entered. Returns the Welcome page.
	 */
	@RequestMapping(value = { "/Welcome", "/{s}/**", "/{url}", "/" })
	public ModelAndView welcome(HttpServletRequest request) {

		return new ModelAndView("Welcome");
	}

	/**
	 * Method which handles the main-page 'Home'. If the session attribute
	 * 'username' is null it redirects to the Login-Welcome page. If the user is
	 * logged in. It fetches all the list items & the logged in user's items and
	 * returns the home view.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Home")
	public ModelAndView home(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();

		if (request.getSession().getAttribute("username") != null) {

			model.setViewName("home");
			User user = (User) request.getSession().getAttribute("username");
			int userId = user.getId();
			try {
				List<TopList> toplist = listManager.getTopListFromDB();
				List<TopList> userList = listManager.getUserTopListFromDB(userId);
				model.addObject("username", request.getSession().getAttribute("username"));
				model.addObject("userList", userList);
				model.addObject("TopList", toplist);
			} catch (Exception e) {
				throw e;
			}
		} else {

			model.setViewName("redirect:/Welcome");
		}

		return model;
	}

	/**
	 * Method which handles requests on /addItem. Checks if the user is logged
	 * in, if yes, it adds the list-item. If not logged in, redirects to
	 * Welcome.
	 * 
	 * @param request
	 * @param product
	 * @param producturl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addListItem(HttpServletRequest request, @RequestParam("Product") String product,
			@RequestParam("Producturl") String producturl) throws Exception {

		ModelAndView model = new ModelAndView();

		if (request.getSession().getAttribute("username") != null) {
			product = filter.cleanProductInput(product);
			producturl = filter.cleanProductInput(producturl);

			User user = (User) request.getSession().getAttribute("username");

			listManager.addListItem(product, producturl, user.getId());

			model.setViewName("redirect:/Home");

		} else {
			model.setViewName("redirect:/Welcome");
		}

		return model;
	}

	/**
	 * Method which handles requests on /Login. First validates the username
	 * input. If input was ok, it calls the handleUserLogin() method which
	 * returns true or false if the user login was successfull. If successfull,
	 * it sets the user to the session as an attribute.
	 * 
	 * @param request
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, @RequestParam("username") String userName,
			@RequestParam("password") String passWord) throws Exception {

		ModelAndView model = new ModelAndView();

		if (filter.validateInput(userName)) {
			try {
				if (userManager.handleUserLogin(userName, passWord)) {
					setSessionUser(request, userName, passWord);
					model.setViewName("redirect:/Home");

				} else {
					model.addObject("errorLog", "invalid username or password");
					model.setViewName("Welcome");
				}
			} catch (Exception e) {
				throw e;
			}
		} else {
			model.addObject("errorLog", "invalid username or password");
			model.setViewName("Welcome");
		}
		return model;
	}

	/**
	 * Method which handles requests on /Register. First validates the username
	 * input and checks for invalid characters. If validation was ok, it tries
	 * add the user to the database via the addUser method in userManager. If
	 * successfully added, it sets the user as a sessionAttribute and returns
	 * the home view.
	 * 
	 * @param request
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
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
			if (filter.validateInput(userName)) {
				
				
					if (userManager.addUser(userName, passWord)) {
						setSessionUser(request, userName, passWord);
						model.setViewName("redirect:/Home");
						return model;

					} else {

						model.setViewName("Welcome");
						model.addObject("errorReg", "Username already exists");
						return model;
					}
			} else {
				model.setViewName("Welcome");
				model.addObject("errorReg", "Username already exists");
				return model;
			}
		
		} catch(Exception e){
			throw e;
		}
	}

	/**
	 * Method for login out. Removes the user's sessionAttribute and returns the
	 * welcome view.
	 * 
	 * @param request
	 * @return
	 */
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
	 */
	public void setSessionUser(HttpServletRequest request, String userName, String passWord) throws Exception {
		request.getSession().setAttribute("username", userManager.getUser(userName, passWord));
	}

	/**
	 * Method used when updating an item's value after a payment has been done.
	 * This method is only called via an Ajax javascript after a payment is
	 * completed.
	 * 
	 * @param request
	 * @param id
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, @RequestParam("id") String id,
			@RequestParam("amount") String amount) throws Exception {

		ModelAndView model = new ModelAndView();
		if (filter.validateInput(id) && filter.validateInput(amount)) {

			try{
			listManager.updateListItemValue(id, amount);
			model.setViewName("home");
			}catch(Exception e){
				throw e;
			}
		} else {
			model.setViewName("error");
		}

		return model;
	}

	@ExceptionHandler(RuntimeException.class)
	public String databaseConnectionError(Model model) {
		return "error";
	}
}
