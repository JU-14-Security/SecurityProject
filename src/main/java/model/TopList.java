package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the LIST database table.
 * 
 */
@Entity
@Table(name = "LIST", schema = "TOPLISTAN")
@NamedQuery(name = "TopList.findAll", query = "SELECT t FROM TopList t")
public class TopList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String product;

	@Column(name = "PRODUCT_URL")
	private String productUrl;

	@Column(name = "SEK_SUM")
	private int sekSum;

	@Column(name = "USERID")
	private int userId;

	public TopList() {
	}

	public TopList(String product, String productUrl, int userId) {
		super();
		this.product = product;
		this.productUrl = productUrl;
		this.sekSum = 0;
		this.userId = userId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductUrl() {
		return this.productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public int getSekSum() {
		return this.sekSum;
	}

	public void setSekSum(int sekSum) {
		this.sekSum = sekSum;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

}