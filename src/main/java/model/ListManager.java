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
		this.crypter=crypter;
		this.listDAO=listDAO;
	}
	
	public ListManager(){
		
		
	}
	
	public List<TopList> getTopListFromDB(){
	List<TopList> topList=listDAO.getListItems();
		
		return topList;
	}
	
	public boolean addListItem(String product, String producturl){
		
		listDAO.addListItemToDB(new TopList(product, producturl));
		
		return true;
		
	}
	
}
