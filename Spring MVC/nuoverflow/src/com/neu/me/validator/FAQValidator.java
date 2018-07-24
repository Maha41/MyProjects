package com.neu.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.me.pojo.FAQ;



public class FAQValidator implements Validator {

    public boolean supports(Class aClass)
    {
        return aClass.equals(FAQ.class);
    }

    public void validate(Object obj, Errors errors)
    {
    	
        FAQ faq = (FAQ) obj;
        String question= faq.getQuestion();
        String answer= faq.getAnswer();
        
        String valquestion = question.replaceAll("[^A-Za-z0-9? ]", "").replaceAll("\\s"," ").trim();
        String valqanswer = answer.replaceAll("[^-,.A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
        
        faq.setQuestion(valquestion);
        faq.setAnswer(valqanswer);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "question", "error.invalid.question", "Question is Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "answer", "error.invalid.answer", "Response is Required");
        
    }
}
