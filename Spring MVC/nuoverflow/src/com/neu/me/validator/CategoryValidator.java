package com.neu.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.me.pojo.Category;



public class CategoryValidator implements Validator {

    public boolean supports(Class aClass)
    {
        return aClass.equals(Category.class);
    }

    public void validate(Object obj, Errors errors)
    {
    	
        Category cat = (Category) obj;
        String catname = cat.getCategoryName();
        
        String valcatname = catname.replaceAll("[^A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
        
        cat.setCategoryName(valcatname);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "error.invalid.category", "Category name Required");
        
        
    }
}
