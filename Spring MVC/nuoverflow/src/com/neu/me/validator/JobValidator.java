package com.neu.me.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.neu.me.pojo.Jobs;





public class JobValidator implements Validator {

	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    public boolean supports(Class aClass)
    {
        return aClass.equals(Jobs.class);
    }

    public void validate(Object obj, Errors errors)
    {
    	Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher;
        MultipartFile companyImage;
    	
        Jobs job = (Jobs) obj;
        String jobname = job.getJobName();
        
        String comname = job.getCompanyname();
        String desc = job.getJobdescription();
        String valjobname = jobname.replaceAll("[^A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
        
        String valcomname = comname.replaceAll("[^A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
        String valdesc = desc.replaceAll("[^-,.A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
        System.out.println("replace invalid character from jobs");
        job.setJobName(valjobname);
        
        job.setCompanyname(valcomname);
        job.setJobdescription(valdesc);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobName", "error.invalid.jobname", "Job name Required");
      
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyname", "error.invalid.scienname", "company name name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobdescription", "error.invalid.desc", "Job Description name Required");
        try
        {
        	if(job.getCompanyImage() != null)
        	{
        		companyImage = job.getCompanyImage();
        		matcher = pattern.matcher(companyImage.getOriginalFilename());
        
        		if(0 == companyImage.getSize()) {
	        		errors.rejectValue("companyImage","Test","File is empty");
	        	}
	        	if(!matcher.matches()) {
	        		errors.rejectValue("companyImage","Test","Invalid Image Format");
	        	}
	        
	        	if(2000000 < companyImage.getSize()) {
	             errors.rejectValue("companyImage","Test","File size is over 2mb !");
	        	}
        	}
        }
        catch(Exception e)
        {
        	System.out.println("inside exception");
        	errors.rejectValue("companyImage","Test","File is empty");
        }
        
    }
}
