package com.neu.me.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryid")
	private int catId;
	
	@Column(name="categoryname",unique = true)
	private String categoryName;
	
	@OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
	private Set<SubCategory> subcategory = new HashSet<SubCategory>();
	
	public Category()
	{
		
		
	}
	public Category(String catname)
	{
		this.categoryName = catname;
		this.subcategory = new HashSet<SubCategory>();
	}
	
	public void addSubcategory(SubCategory subcategory)
	{
		getSubcategory().add(subcategory);
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Set<SubCategory> getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(Set<SubCategory> subcategory) {
		this.subcategory = subcategory;
	}
	
	
	
}
