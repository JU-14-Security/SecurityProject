package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
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
	

	public void openConnection() {
		try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();

		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		em.close();
	}
	
	public boolean checkifAvailable(String userName){
		openConnection();
		TypedQuery<User> createQuery = em.createQuery(
				"SELECT c FROM User c WHERE c.username = :userName", User.class);
		createQuery.setParameter("userName", userName);
		if(createQuery.getResultList().isEmpty()){
			closeConnection();
			return true;
		}else
			closeConnection();
			return false;
	}

	public void addUser(User user) {
		openConnection();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		closeConnection();
	}
	
	public boolean getUserFromDB(String userName, String passWord) {
		openConnection();
		TypedQuery<User> createQuery = em.createQuery(
				"SELECT c FROM User c WHERE c.username = :userName and c.password = :passWord", User.class);
		createQuery.setParameter("userName", userName);
		createQuery.setParameter("passWord", passWord);
		
		List<User> resultList = createQuery.getResultList();
		closeConnection();
		
		if(resultList.isEmpty()){
			return false;
		}else
			return true;
	}
}
