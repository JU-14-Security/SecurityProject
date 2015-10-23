package model;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;



@Component
public class DataCrypter {
	
	
	public DataCrypter(){
		
	}

	/**
	 * Method which hashes the password with a random generated salt string using the SHA-256 lib.
	 * @param input
	 * @param salt
	 * @return the salt+hashed(salt+password)
	 * @throws Exception
	 */
	public String passwordHasher(String input, String salt) throws Exception {

		String saltAndPW = input + salt;

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] enCryptedUsername = md.digest(saltAndPW.getBytes("UTF-8"));
				
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < enCryptedUsername.length; ++i) {
          sb.append(Integer.toHexString((enCryptedUsername[i] & 0xFF) | 0x100).substring(1,3));
        }
        
		return salt+sb.toString();
	}

	/**
	 * Method which returns a random byte-salt string. Is called each time a new user is registered. 
	 * @return the random byte-salt string.
	 */
	public String getRandomSalt(){
		final Random r = new SecureRandom();
		byte[] salt = new byte[16];
		r.nextBytes(salt);
	
		
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < salt.length; ++i) {
          sb.append(Integer.toHexString((salt[i] & 0xFF) | 0x100).substring(1,3));
        }
        
        System.out.println("Random salt "+sb.toString());
		return sb.toString();
	}
}
