package com.neu.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.me.pojo.SubCategory;



public class SubCategoryValidator implements Validator {

    public boolean supports(Class aClass)
    {
        return aClass.equals(SubCategory.class);
    }

    public void validate(Object obj, Errors errors)
    {
    	
        SubCategory subcat = (SubCategory) obj;
        String subcatname = subcat.getSubCatName();
        
        String valsubatname = subcatname.replaceAll("[^A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
        
        subcat.setSubCatName(valsubatname);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subCatName", "error.invalid.subcategory", "SubCategory name Required");
        
        
    }
}
