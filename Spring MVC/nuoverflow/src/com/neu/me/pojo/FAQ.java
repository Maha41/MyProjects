package com.neu.me.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="faqtable")
public class FAQ {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="faqid")
	private int faqId;
	
	@Column(name="question")
	private String question;
	
	@Column(name="answer")
	private String answer;

	public FAQ()
	{
		
	}
	
	public FAQ(String question,String answer )
	{
		this.question = question;
		this.answer = answer;
	}
	public int getFaqId() {
		return faqId;
	}

	public void setFaqId(int faqId) {
		this.faqId = faqId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
