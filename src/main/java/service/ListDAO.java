package service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import model.TopList;

/**
 * 
 * @author Erik Nylander, Robin, Joel
 *	Handles the List userbase transcations.
 */
@Component
public class ListDAO {

	private static final String PERSISTENCE_UNIT_NAME = "TopListan";
	private EntityManagerFactory factory;
	private EntityManager em;
	private PersistenceUnitUtil util;

	public ListDAO() {

	}

	/**
	 * Method which initializes the entitymanager which is opened and closed
	 * between every database transaction.
	 * 
	 * @throws PersistenceException
	 * @throws IllegalStateException
	 */
	public void openConnection() throws Exception {
		try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();
			util = factory.getPersistenceUnitUtil();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Method which closes the entitymanager after transaction.
	 * 
	 * @throws IllegalStateException
	 */
	public void closeConnection() throws Exception {
		em.close();
	}

	/**
	 * Method which fetches all the list-items from the database.
	 * 
	 * @return the list of items.
	 * @throws PersistenceException
	 * @throws IllegalStateException
	 */
	public List<TopList> getListItems() throws Exception {
		openConnection();
		TypedQuery<TopList> createQuery = em.createQuery("SELECT c FROM TopList c ORDER BY c.sekSum DESC",
				TopList.class);

		List<TopList> resultList = createQuery.getResultList();
		closeConnection();

		if(resultList.isEmpty()){
			return null;
		}else
		return resultList;
	}

	/***
	 * Get a specific user's list items.
	 * 
	 * @author Joel
	 * @param userId
	 *            the logged in users id
	 * @return user specific topList
	 */
	public List<TopList> getUserListItems(int userId) throws Exception {
		openConnection();
		TypedQuery<TopList> createQuery = em
				.createQuery("SELECT t FROM TopList t WHERE t.userId = :userId", TopList.class)
				.setParameter("userId", userId);
		List<TopList> usersList = createQuery.getResultList();
		closeConnection();
		
		if(usersList.isEmpty()){
			return null;
		}else
		return usersList;
	}

	/**
	 * Adds a list object to the database table.
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void addListItemToDB(TopList item) throws Exception {
		
			openConnection();
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			closeConnection();
	
	}

	/**
	 * Fetches a single list item by using the id. This method is used before
	 * updating a list-item's SekSum.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TopList getListItemByID(int id) throws Exception {
	
		openConnection();
		TypedQuery<TopList> createQuery = em.createQuery("SELECT t FROM TopList t WHERE t.id = :id", TopList.class)
				.setParameter("id", id);
		List<TopList> toplist = createQuery.getResultList();
		closeConnection();

		if(toplist.isEmpty()){
			return null;
		}else
		return toplist.get(0);

	}

	/**
	 * Updates a single item's sekSum. This method is called when a user makes a
	 * payment.
	 * 
	 * @param toplist
	 * @param addValue
	 * @throws Exception
	 */
	public void updateListItem(TopList toplist, int addValue) throws Exception {
		
		openConnection();
		Object identifier = util.getIdentifier(toplist);

		TopList toplistQuery = em.find(TopList.class, identifier);
		int oldValue = toplistQuery.getSekSum();
		toplistQuery.setSekSum(oldValue + addValue);

		em.getTransaction().begin();
		em.merge(toplistQuery);
		em.getTransaction().commit();
		closeConnection();
	}

}
