package com.neu.me.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="subcategory")
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subcategoryid")
	private int subCatId;
	
	@Column(name="subcategoryname", unique = true)
	private String subCatName;
	
	
	@JoinColumn(name="categoryid")
	private int category;
	
	@OneToMany(mappedBy = "subcat" ,cascade =CascadeType.ALL )
	private Set<Items> items = new HashSet<Items>();

	public SubCategory()
	{
		
	}
	public SubCategory(String subcatname, int catid)
	{
		this.subCatName = subcatname;
		this.category = catid;
		this.items = new HashSet<Items>();
		
	}
	
	public int getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	public String getSubCatName() {
		return subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}




	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Set<Items> getItems() {
		return items;
	}

	public void setItems(Set<Items> items) {
		this.items = items;
	}


	
	
}
