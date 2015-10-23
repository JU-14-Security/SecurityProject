package model;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

@Component
public class Filter {

	public Filter() {

	}

	/**
	 * Checks an input string after unwanted charaters. We only allow a-Ö/0-9 in our username. 
	 * @param input
	 * @return true if no malicious input was found, false if found.
	 */
	public boolean validateInput(String input) {

		for (char c : input.toCharArray()) {

			if (!Character.isDigit(c) && !Character.isLetter(c)) {
				return false;

			}
		}
		return true;

	}

	/***
	 * Cleans url from unwanted tags etc..
	 * 
	 * @author Joel
	 * @param productInput Url to be cleaned
	 * @return a string cleaned from html tags
	 */
	public String cleanProductInput(String productInput) {
		productInput = Jsoup.clean(productInput, Whitelist.none().removeAttributes(":all", "class"));
		return productInput;
	}

	/***
	 * Validades url
	 * 
	 * @author Joel
	 * @param producturl Url to be validated
	 * @return
	 */
	public boolean validateProduktUrl(String producturl) {
		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes);

		if (urlValidator.isValid(producturl)) {
			System.out.println(producturl + " url is valid");
			return true;
		} else if (urlValidator.isValid("http://" + producturl)) {
			System.out.println(producturl + " url is valid (adding http://)");
			return true;
		} else if (urlValidator.isValid("https://" + producturl)) {
			System.out.println(producturl + " url is valid (adding https://)");
			return true;
		} else {
			System.out.println(producturl + " url is invalid");
			return false;
		}
	}

}
