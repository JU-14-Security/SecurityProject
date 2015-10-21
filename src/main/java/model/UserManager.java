package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
			String passWordCrypto = crypter.inputHasher(passWord);
			
			if(userDAO.getCheckLogin(userName , passWordCrypto)){
				
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
			
			String passWordCrypto = crypter.inputHasher(passWord);
			if(userDAO.checkifAvailable(userName)){
				
				userDAO.addUser(new User(userName,passWordCrypto));
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
	
	//..joel har pillat här..
	public User getUser(String userName, String passWord) {
		User user=null;
		try {
			String passWordCrypto = crypter.inputHasher(passWord);
			user = userDAO.getUserFromDB(userName, passWordCrypto);
			System.out.println(user.getUsername());
		
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		}
		return user;
	
	}
}
