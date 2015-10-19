package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;



@Component
public class DataCrypter {
	
	private static final String salt="Kx3LePZ0";
	
	public DataCrypter(){
		
	}

	public String inputHasher(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{

		input+=salt;
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] enCryptedUsername = md.digest(input.getBytes("UTF-8"));
				
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < enCryptedUsername.length; ++i) {
          sb.append(Integer.toHexString((enCryptedUsername[i] & 0xFF) | 0x100).substring(1,3));
        }
       
		return sb.toString();
	}
	
	
}
