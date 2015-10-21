package model;

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
	
	public void validateProduktUrl(){
		String uncheckedUrl="ht<a href ='minsida'</a>tps://mi!#¤%&/()n<script>blabla</script>sida.com/minprodukt/;";	
		System.out.println(uncheckedUrl);
		System.out.println(Jsoup.clean(uncheckedUrl, Whitelist.none().removeAttributes(":all","class")));
	
	}
	
	public static void main(String[] args) {
		Filter f = new Filter();
		f.validateProduktUrl();
	}

}
