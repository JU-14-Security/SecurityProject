package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Component;

import service.UserDAO;

@Component
public class UserManager {

	UserDAO userDAO;
	DataCrypter crypter;

	public UserManager() {

	}

	@Inject
	public UserManager(UserDAO userDAO, DataCrypter crypter) {
		this.userDAO = userDAO;
		this.crypter = crypter;

	}

	public boolean handleUserLogin(String userName, String passWord)throws Exception {
		User user = null;
		User validUser = null;
		try {
			// För att hämta användarens salt
			user = userDAO.getUserLogin(userName);

			String salt = user.getPassword().substring(0, 32);
			String passWordCrypto = crypter.passwordHasher(passWord, salt);
			validUser = userDAO.getUserFromDB(userName, passWordCrypto);

			if (validUser != null) {
				System.out.println("returnar true från handleUserLogin");
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("returnar false från handleuserlogin");
		return false;
	}

	public boolean addUser(String userName, String passWord)throws Exception {
		try {
			if (userDAO.checkifAvailable(userName)) {
				String salt = crypter.getRandomSalt();
				String passWordCrypto = crypter.passwordHasher(passWord, salt);

				System.out.println("addar user " + userName);
				userDAO.addUser(new User(userName, passWordCrypto));
				return true;
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		}

		return false;

	}

	// ..joel har pillat här..
	public User getUser(String userName, String passWord) throws Exception {

		User user = null;
		User validUser = null;
		try {

			// För att hämta användarens salt
			user = userDAO.getUserLogin(userName);

			String salt = user.getPassword().substring(0, 32);
			String passWordCrypto = crypter.passwordHasher(passWord, salt);
			validUser = userDAO.getUserFromDB(userName, passWordCrypto);

			if (validUser == null) {
				System.out.println("returnar null från getUser");
				return null;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println("returnar inte null från getUser");
		return validUser;
	}
}
