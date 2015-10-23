package model;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

@Component
public class Filter {

	public Filter() {

	}

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

	// for testing purpouses..
	public static void main(String[] args) {
		Filter f = new Filter();
		String testUrl = "http://www.google.se<script></script>";
		System.out.println("dirtyUrl: " + testUrl);
		testUrl = f.cleanProductInput(testUrl);
		System.out.println("cleanUrl: " + testUrl);
		if (f.validateProduktUrl(testUrl)) {
			System.out.println(testUrl + " valid");
		} else {
			System.out.println(testUrl + " invalid");
		}
	}
}
