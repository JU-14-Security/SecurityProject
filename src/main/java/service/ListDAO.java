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

@Component
public class ListDAO {

	private static final String PERSISTENCE_UNIT_NAME = "TopListan";
	private EntityManagerFactory factory;
	private EntityManager em;
	private PersistenceUnitUtil util;

	public ListDAO() {

	}

	public void openConnection() throws PersistenceException, IllegalStateException {
		try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();
			util = factory.getPersistenceUnitUtil();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void closeConnection() throws IllegalStateException {
		em.close();
	}

	public List<TopList> getListItems() throws PersistenceException, IllegalStateException {
		openConnection();
		TypedQuery<TopList> createQuery = em.createQuery("SELECT c FROM TopList c ORDER BY c.sekSum DESC",
				TopList.class);

		List<TopList> resultList = createQuery.getResultList();
		closeConnection();

		return resultList;
	}

	/***
	 * Get logged in users topList from DB
	 * 
	 * @author Joel
	 * @param userId
	 *            the logged in users id
	 * @return user specific topList
	 */
	public List<TopList> getUserListItems(int userId)throws Exception {
		openConnection();
		TypedQuery<TopList> createQuery = em
				.createQuery("SELECT t FROM TopList t WHERE t.userId = :userId", TopList.class)
				.setParameter("userId", userId);
		List<TopList> usersList = createQuery.getResultList();
		closeConnection();
		return usersList;
	}

	public void addListItemToDB(TopList item) throws Exception {
		try {
			openConnection();
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public TopList getListItemByID(int id)throws Exception {
		System.out.println("Hämtar listitem med id");
		openConnection();
		TypedQuery<TopList> createQuery = em.createQuery("SELECT t FROM TopList t WHERE t.id = :id", TopList.class)
				.setParameter("id", id);
		List<TopList> toplist = createQuery.getResultList();
		closeConnection();

		return toplist.get(0);

	}

	public void updateListItem(TopList toplist, int addValue)throws Exception  {
		System.out.println("uppdaterad listitem med id");
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
