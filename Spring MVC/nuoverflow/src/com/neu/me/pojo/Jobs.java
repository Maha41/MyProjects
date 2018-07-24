package com.neu.me.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="jobs")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="herbs")
public class Jobs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="jobid")
	private int jobid;
	
	@Column(name="jobname",unique = true)
	private String jobName;
	
	@Column(name="companylogo",unique = true)
	private String companylogo;
	
	@Column(name="jobdescription")
	private String jobdescription;
	
	@Column(name="companyname")
	private String companyname;
	
	@Transient
	private MultipartFile companyImage;
	
	public Jobs()
	{
		
	}

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanylogo() {
		return companylogo;
	}

	public void setCompanylogo(String companylogo) {
		this.companylogo = companylogo;
	}

	public String getJobdescription() {
		return jobdescription;
	}

	public void setJobdescription(String jobdescription) {
		this.jobdescription = jobdescription;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public MultipartFile getCompanyImage() {
		return companyImage;
	}

	public void setCompanyImage(MultipartFile companyImage) {
		this.companyImage = companyImage;
	}
	
	

}
