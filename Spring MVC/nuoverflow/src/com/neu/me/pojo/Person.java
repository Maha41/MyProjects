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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Person")
public class Person {

	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="personid")
	private int personid;
	
	@OneToOne
	@JoinColumn(name="userid")
	private useraccount useraccount;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="age")
	private int age;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@OneToMany(mappedBy="person", fetch = FetchType.EAGER)
	private Set<Comments> comments = new HashSet<Comments>();

	
	public Person()
	{
		
	}
	public Person(String first, String last, String email, String phone, int age)
	{
		this.firstName = first;
		this.lastName = last;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.comments = new HashSet<Comments>();
	}
	public int getPersonid() {
		return personid;
	}
	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public useraccount getUserAccount() {
		return useraccount;
	}
	public void setUserAccount(useraccount useraccount) {
		this.useraccount = useraccount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
