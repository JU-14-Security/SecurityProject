package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import service.UserDAO;

@Component
public class UserManager {

	UserDAO userDAO;
	DataCrypter crypter;
	
	public UserManager (){
		
	}
	
	@Inject
	public UserManager(UserDAO userDAO, DataCrypter crypter) {
		this.userDAO=userDAO;
		this.crypter=crypter;

	}
	
	public boolean handleUserLogin(String userName, String passWord){
		try {
			String userNameCrypto = crypter.inputHasher(userName);
			String passWordCrypto = crypter.inputHasher(passWord);
			
			if(userDAO.getUserFromDB(userNameCrypto, passWordCrypto)){
				
				return true;
			}else
				return false;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addUser(String userName, String passWord){
		try {
			String userNameCrypto = crypter.inputHasher(userName);
			String passWordCrypto = crypter.inputHasher(passWord);
			if(userDAO.checkifAvailable(userNameCrypto)){
				
				userDAO.addUser(new User(userNameCrypto,passWordCrypto));
				return true;
			}else
				return false;
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		return false;

	}
}
