package model;

import java.util.List;

import javax.inject.Inject;

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
	public List<TopList> getUserTopListFromDB(int userId) {
		List<TopList> topList = listDAO.getUserListItems(userId);
		return topList;
	}

	public List<TopList> getTopListFromDB() {
		List<TopList> topList = listDAO.getListItems();

		return topList;
	}

	public boolean addListItem(String product, String producturl, int userId) {

		listDAO.addListItemToDB(new TopList(product, producturl, userId));

		return true;

	}

}
