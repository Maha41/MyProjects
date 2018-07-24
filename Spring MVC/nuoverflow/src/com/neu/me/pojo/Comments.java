package com.neu.me.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Comments")
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="commentid")
	private int commentId;
	
	@Column(name="description")
	private String commentDesc;
	
	@Transient
	private int item_Id;
	
	@ManyToOne
	@JoinColumn(name = "itemid")
	private Items item;
	
	@Column(name="commentDate")
	private Date commentDate;
	
	@Transient
	private int person_id;
	
	@ManyToOne(optional=false, fetch = FetchType.EAGER)
	@JoinColumn(name="personid")
	private Person person;
	
	
	
	public Comments()
	{
		
	}
	public Comments(String desc, Items item, Person person)
	{
		Date date = new Date();
		this.commentDesc = desc;
		this.item = item;
		this.person = person;
		this.commentDate = date;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getCommentDesc() {
		return commentDesc;
	}
	public void setCommentDesc(String commentDesc) {
		this.commentDesc = commentDesc;
	}
	
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public int getItem_Id() {
		return item_Id;
	}
	public void setItem_Id(int item_Id) {
		this.item_Id = item_Id;
	}
	public Items getItem() {
		return item;
	}
	public void setItem(Items item) {
		this.item = item;
	}
	
	
}
