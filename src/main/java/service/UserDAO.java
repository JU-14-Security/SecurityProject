package service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import model.User;

@Component
public class UserDAO {

	private static final String PERSISTENCE_UNIT_NAME = "TopListan";
	private EntityManagerFactory factory;
	private EntityManager em;

	public UserDAO() {

	}

	public void openConnection()throws Exception {
		try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void closeConnection() throws Exception {
		em.close();
	}

	public boolean checkifAvailable(String userName)throws Exception {
		openConnection();
		TypedQuery<User> createQuery = em.createQuery("SELECT c FROM User c WHERE c.username = :userName", User.class);
		createQuery.setParameter("userName", userName);
		if (createQuery.getResultList().isEmpty()) {
			closeConnection();
			return true;
		} else
			closeConnection();
		return false;
	}

	public void addUser(User user) throws Exception {
		try {
			openConnection();
			 em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			closeConnection();
		} catch (Exception e) {
			System.out.println("kastar exception");
			throw e;
		}
	}

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

	public User getUserFromDB(String userName, String passWord)throws Exception  {
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
