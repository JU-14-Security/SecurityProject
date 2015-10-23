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

	public UserManager() {

	}

	@Inject
	public UserManager(UserDAO userDAO, DataCrypter crypter) {
		this.userDAO = userDAO;
		this.crypter = crypter;

	}

	/**
	 * The method which handles the user login logic. It first fetches the user
	 * object from the database via the username input. If user was found. It
	 * fetches the salt from the password which is the first 32 characters of
	 * the password. It then takes the salt and the password-input and hashes it
	 * and checks the database again if the password is correct. If it was
	 * correct, it returns true, else false.
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	public boolean handleUserLogin(String userName, String passWord) throws Exception {
		User user = null;
		User validUser = null;
		try {

			// För att hämta användarens salt
			user = userDAO.getUserLogin(userName);
			if (user != null) {
				String salt = user.getPassword().substring(0, 32);
				String passWordCrypto = crypter.passwordHasher(passWord, salt);
				validUser = userDAO.getUserFromDB(userName, passWordCrypto);

				if (validUser != null) {
					return true;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * The method which handles the registration logic. It first checks if the
	 * username already is registered. If not, it gets a random salt and passes
	 * the password and salt to the hasher method in the DataCrypter class. It
	 * finally adds the user to the database with a password format of
	 * salt+(hash(password+salt))
	 * 
	 * @param userName
	 * @param passWord
	 * @return true if successfull registration, false if not.
	 * @throws Exception
	 */
	public boolean addUser(String userName, String passWord) throws Exception {
		try {
			if (userDAO.checkifAvailable(userName)) {
				String salt = crypter.getRandomSalt();
				String passWordCrypto = crypter.passwordHasher(passWord, salt);

				userDAO.addUser(new User(userName, passWordCrypto));
				return true;
			}

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Similar method of handleUserLogin() except it returns the user object
	 * instead of true/false. This method is used for fetching the user object
	 * which is added to the session in topcontroller.
	 * 
	 * @param userName
	 * @param passWord
	 * @return the user object.
	 * @throws Exception
	 */
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
				return null;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return validUser;

	}
}
