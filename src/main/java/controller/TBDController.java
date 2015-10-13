package controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Class for handling request.
 * @author Erik Nylander
 *
 */
@Controller
public class TBDController {


	/**
	 * Constructor for the Spring MVC Controller.
	 * 
	 * @param centerHandler
	 *            The class which handles Center objects.
	 */
	
	/*
	@Inject
	public TBDController() {
		

	}
	*/
	public TBDController() {

	}

	@RequestMapping("/TBD")
	public String getCenters(Model model) {
		
		
		return "index";
	}

}
