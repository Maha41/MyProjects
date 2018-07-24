package com.neu.me.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="Items")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="items")
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="itemid")
	private int itemId;
	
	@Column(name="itemname")
	private String itemName;
	
	@Transient
	private int subCatId;
	
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn(name="subcategoryid")
	private SubCategory subcat;
	
	@Column(name="description")
	private String description;
	
	@Transient
	private int herb_Id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="herbid")
	private Jobs herbs;
	
	@Column(name="benefits")
	private String benefits;
	
	@Column(name="procedurestep")
	private String procedurestep;
	
	@OneToMany(mappedBy="item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Comments> comments = new HashSet<Comments>();
	
	@OneToMany(mappedBy="itemid",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Like> like =  new HashSet<Like>();
	
	@Column(name="postDate")
	String postDate;
	

	@JoinColumn(name="personid")
	private int personid;
	
	public Items()
	{
		
	}
	
	public Items(String itemname, SubCategory sub, Jobs herb, String benifits, String procedure, String description,int postedBy)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Date date = new Date();
		this.itemName = itemname;
		this.subcat = sub;
		this.herbs = herb;
		this.benefits = benifits;
		this.procedurestep = procedure;
		this.description = description;
		this.like =  new HashSet<Like>();
		this.comments = new HashSet<Comments>();
		this.postDate = sdf.format(date);
		this.personid = postedBy;
		
	}
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBenefits() {
		return benefits;
	}
	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}
	
	
	public String getProcedurestep() {
		return procedurestep;
	}

	public void setProcedurestep(String procedurestep) {
		this.procedurestep = procedurestep;
	}

	public SubCategory getSubcat() {
		return subcat;
	}

	public void setSubcat(SubCategory subcat) {
		this.subcat = subcat;
	}

	public int getHerb_Id() {
		return herb_Id;
	}

	public void setHerb_Id(int herb_Id) {
		this.herb_Id = herb_Id;
	}

	public Jobs getHerbs() {
		return herbs;
	}

	public void setHerbs(Jobs herbs) {
		this.herbs = herbs;
	}

	public Set<Comments> getComments() {
		return comments;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}

	public Set<Like> getLike() {
		return like;
	}

	public void setLike(Set<Like> like) {
		this.like = like;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	


	
}
