package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import model.TopList;

@Component
public class ListDAO {

	private static final String PERSISTENCE_UNIT_NAME = "TopListan";
	private EntityManagerFactory factory;
	private EntityManager em;

	public ListDAO() {

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

	public List<TopList> getListItems() {
		openConnection();
		TypedQuery<TopList> createQuery = em.createQuery("SELECT c FROM TopList c", TopList.class);

		List<TopList> resultList = createQuery.getResultList();
		closeConnection();

		return resultList;

	}

	/***
	 * Get logged in users topList from DB
	 * 
	 * @author Joel
	 * @param userId the logged in users id
	 * @return user specific topList
	 */
	public List<TopList> getUserListItems(int userId) {
		openConnection();
		TypedQuery<TopList> createQuery = em.createQuery("SELECT t FROM TopList t WHERE t.userId = :userId", TopList.class)
				.setParameter("userId", userId);
		List<TopList> usersList = createQuery.getResultList();
		return usersList;
	}

	public void addListItemToDB(TopList item) {
		try {
			System.out.println("addar" + item.getProduct());
			openConnection();
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
