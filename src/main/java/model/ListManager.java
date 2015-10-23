package model;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import service.ListDAO;

/**
 * 
 * @author Erik Nylander, Robin, Joel
 * Handles the logic for managing list-items.
 */
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
	public List<TopList> getUserTopListFromDB(int userId) throws Exception {
		List<TopList> topList = listDAO.getUserListItems(userId);
		
		return topList;
	}

	/**
	 * Method which calls the listDAO class, who returns all the list-items from
	 * the database.
	 * 
	 * @return list of all items.
	 * @throws Exception
	 */
	public List<TopList> getTopListFromDB() throws Exception {
		List<TopList> topList = listDAO.getListItems();
	
		return topList;
	}

	/**
	 * Method which calls the addListItemToDB method in listDAO, which adds the
	 * list-item.
	 * 
	 * @param product
	 * @param producturl
	 * @param userId
	 * @throws Exception
	 */
	public void addListItem(String product, String producturl, int userId) throws Exception {
		listDAO.addListItemToDB(new TopList(product, producturl, userId));

	}

	/**
	 * Method which handles the logic of updating an item's sekSum/value. It
	 * first parses the id and amount to integers and fetches the list-item
	 * object via the ID. It then updates the list item's seksum witch the
	 * amount by calling the updateListItem() method in listDAO class.
	 * 
	 * @param id
	 * @param amount
	 * @throws Exception
	 */
	public void updateListItemValue(String id, String amount) throws Exception {
		try {
			int parsedID = Integer.parseInt(id);
			int parsedAmount = Integer.parseInt(amount);
			TopList toplist = listDAO.getListItemByID(parsedID);
			if(toplist!=null){
				listDAO.updateListItem(toplist, parsedAmount);
			}else
				throw new Exception();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e){
			throw e;
		}
	}

}
