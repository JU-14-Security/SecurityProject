package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import model.User;

/**
 * 
 * @author Erik Nylander, Robin, Joel
 *	Handles the user database transcations.
 */
@Component
public class UserDAO {

	private static final String PERSISTENCE_UNIT_NAME = "TopListan";
	private EntityManagerFactory factory;
	private EntityManager em;

	public UserDAO() {

	}

	/**
	 * Opens the connection to the database.
	 * 
	 * @throws Exception
	 */
	public void openConnection() throws Exception {
		try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Closes the connection to the database.
	 * 
	 * @throws IllegalStateException
	 */
	public void closeConnection() throws Exception {
		em.close();
	}

	/**
	 * Method which checks if the username is already taken or not. Called when
	 * registering a user.
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public boolean checkifAvailable(String userName) throws Exception {
		openConnection();
		TypedQuery<User> createQuery = em.createQuery("SELECT c FROM User c WHERE c.username = :userName", User.class);
		createQuery.setParameter("userName", userName);
		if (createQuery.getResultList().isEmpty()) {
			closeConnection();
			return true;
		} else{
			closeConnection();
		return false;
		}
	}

	/**
	 * Method which adds a user to the database.
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void addUser(User user) throws Exception {
		
			openConnection();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			closeConnection();
		
	}

	/**
	 * Fetches a user from the database via the username. This method is used to
	 * fetch the salt from the password which later is used to check if valid
	 * input was given.
	 * 
	 * @param userName
	 * @return the user object.
	 * @throws Exception
	 */
	public User getUserLogin(String userName) throws Exception {
		openConnection();
		TypedQuery<User> createQuery = em.createQuery("SELECT c FROM User c WHERE c.username = :userName", User.class);
		createQuery.setParameter("userName", userName);

		List<User> resultList = createQuery.getResultList();
		closeConnection();

		if (resultList.isEmpty()) {
			return null;
		} else
			return resultList.get(0);
	}

	/**
	 * Method used after the salt has been retrieved and hashed together with
	 * the password-input. Returns the user object if password was correct.
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	public User getUserFromDB(String userName, String passWord) throws Exception {
		openConnection();
		TypedQuery<User> createQuery = em.createQuery(
				"SELECT c FROM User c WHERE c.username = :userName and c.password = :passWord", User.class);
		createQuery.setParameter("userName", userName);
		createQuery.setParameter("passWord", passWord);

		// ..joel har pillat här..
		List<User> resultList = createQuery.getResultList();
		closeConnection();

		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

}
