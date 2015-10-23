package model;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import org.springframework.stereotype.Component;

import service.ListDAO;

@Component
public class ListManager {

	ListDAO listDAO;
	DataCrypter crypter;

	@Inject
	public ListManager(ListDAO listDAO, DataCrypter crypter) {
		this.crypter = crypter;
		this.listDAO = listDAO;
	}

	public ListManager() {

	}

	/***
	 * Get logged in users topList
	 * 
	 * @author Joel
	 * @param userId
	 * @return user specific topList
	 */
	public List<TopList> getUserTopListFromDB(int userId)throws Exception {
		List<TopList> topList = listDAO.getUserListItems(userId);
		return topList;
	}

	public List<TopList> getTopListFromDB()throws Exception  {
		List<TopList> topList = listDAO.getListItems();

		return topList;
	}

	public boolean addListItem(String product, String producturl, int userId)throws Exception {
		listDAO.addListItemToDB(new TopList(product, producturl, userId));

		return true;
	}

	public void updateListItemValue(String id, String amount)throws Exception {
		try {
			int parsedID = Integer.parseInt(id);
			int parsedAmount = Integer.parseInt(amount);
			TopList toplist = listDAO.getListItemByID(parsedID);
			System.out.println(toplist.getProduct());
			listDAO.updateListItem(toplist, parsedAmount);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
