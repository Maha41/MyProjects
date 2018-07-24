package com.neu.me.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="liketable")
public class Like {

	//@GenericGenerator(name="generator", strategy="foreign", parameters = @Parameter(name = "property", value = "itemlist"))
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="likeid")
	private int likeid;
	
	@Transient
	private int person_id;
	
	@Transient
	private int item_id;
	
	@ManyToOne
	@JoinColumn(name="itemid")
	private Items itemid;
	
	@OneToOne
	@JoinColumn(name="personid")
	private Person person;
	
	public Like()
	{
		
	}
	
	public Like(Items item ,Person person)
	{
		this.itemid = item;
		this.person = person;
	}
	
	
	public int getPerson_id() {
		return person_id;
	}


	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public int getLikeid() {
		return likeid;
	}


	public void setLikeid(int likeid) {
		this.likeid = likeid;
	}


	public int getItem_id() {
		return item_id;
	}


	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}


	public Items getItemid() {
		return itemid;
	}


	public void setItemid(Items itemid) {
		this.itemid = itemid;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	
}
