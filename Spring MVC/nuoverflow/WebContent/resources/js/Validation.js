/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function ValidateData()
 {
     
               if(!validateform()){
                   alert('Invalid Data');
                    return false;
                }
                else
                {
                    
                    return true;
                }
 }

function ValidateCat()
{
	var documentid = document.getElementById("catname");
	
        if(!isAlphanumeric(documentid.value))
        {
            //alert('Invalid data');
            
            documentid.style.borderColor= 'red';
            return false;
        }
        else
        {
        	documentid.style.borderColor= '#D9D9D9';
        		return true;
        }
                 
}
function ValidateSubCat()
{
	var documentid = document.getElementById("subcatname");
	
        if(!isAlphanumeric(documentid.value))
        {
            //alert('Invalid data');
            
            documentid.style.borderColor= 'red';
            return false;
        }
        else
        {
        	documentid.style.borderColor= '#D9D9D9';
        		return true;
        }
                 
}
function ValidateHerb()
{
	if(!validateform())
		{
			alert('Invalid data');
			return false;
		}
	else
		{
			if( document.getElementById("filetype").files.length == 0 ){
				alert('Invalid data');
			}
			else
			{
				var documentid = document.getElementById("textarea1");
				 if(!isTextArea(documentid.value))
			        {
			            alert('Invalid data');
			            
			            documentid.style.borderColor= 'red';
			            return false;
			        }
			}
		}
	                return true;
}

function ValidateItems()
{
	var dropdown1 = document.getElementById('subcategory');
	var dropdown2 = document.getElementById('herbs');

	 if(!validateform()){
         alert('Invalid Data');
          return false;
      }
	 else
		 {
		 	if(dropdown1.selectedIndex == 0 || dropdown2.selectedIndex == 0  )
		 	{
		 		alert('Select any option');
		 		return false;
		 	}
		 	else
		 	{
	    	  var tags = document.getElementsByTagName("tetarea");
	          for(var i=0;i<tags.length;i++)
	          {
	        	   var tagid = tags[i].id;
	               
	               var tagval = tags[i].value;
	               var documentid = document.getElementById(tagid);
	               documentid.style.borderColor= '#D9D9D9';
	                 if(tagid.substr(0,8) == "textarea")
	                 {
	                     
	                     if(!isTextarea(tagval))
	                     {
	                         alert('Invalid data');
	                        
	                        documentid.style.borderColor= 'red';
	                         return false;
	                     }
	                 }
	          }
		 	}
          
      }   
	 return true;
}
 function validateform()
 {
  
  var present = false;

      var tags = document.getElementsByTagName("input");
      for(var i=0;i<tags.length;i++)
      {
          
           present = false;
          if(tags[i].type == "text")
          {
              
                var tagid = tags[i].id;
                
              var tagval = tags[i].value;
              var documentid = document.getElementById(tagid);
              documentid.style.borderColor= '#D9D9D9';
                if(tagid.substr(0,4) == "text")
                {
                    
                    if(!isAlpha(tagval))
                    {
                        //alert('Invalid data');
                       present = true;
                       documentid.style.borderColor= 'red';
                        return false;
                    }
                }
                else if(tagid.substr(0,3) == "num")
                {
                    
                    if(!isNumber(tagval))
                    {
                        
                       present = true;
                        documentid.style.borderColor= 'red';
                        return false;
                    }
                }
                else if(tagid.substr(0,8) == "alphanum")
                {
                    if(!isAlphanumeric(tagval))
                    {
                        //alert('Invalid data');
                        present = true;
                        documentid.style.borderColor= 'red';
                        return false;
                    }
                }
                 else if(tagid.substr(0,5) == "phone")
                {
                    if(!isPhone(tagval))
                    {
                        //alert('Invalid data');
                        present = true;
                        documentid.style.borderColor= 'red';
                        return false;
                    }
                }
              
                else if(tagid.substr(0,4) == "date")
                {
                    if(!isDate(tagval))
                    {
                        //alert('Invalid data');
                        present = true;
                        documentid.style.borderColor= 'red';
                        return false;
                    }
                }
                else if(tagid.substr(0,3) == "age")
                {
                    if(!isAge(tagval))
                    {
                        //alert('Invalid data');
                        present = true;
                        documentid.style.borderColor= 'red';
                        return false;
                    }
                }
                  
             
          }             
                      
        }
         if(present == false)
        {
            
            return true;
        }
       
 }
 function isTextarea(tagval)
 {
      
     if(tagval.match(/^[a-zA-Z0-9]+[ a-zA-Z0-9.,?+]*$/))
     {
         return true;
     }
     else
     {
         return false;
     }
 }
 function isAlpha(tagval)
 {
      
     if(tagval.match(/^[a-zA-Z]+[ a-zA-Z+]*$/))
     {
         return true;
     }
     else
     {
         return false;
     }
 }
function isNumber(tagval) {
                                    
               
if(!isNaN(tagval) && tagval>0){

return true;
}
 else{
 
return false;   
 }
 }
function isAge(tagval) {
    
    
	if(!isNaN(tagval) && tagval>0 && tagval <120){

	return true;
	}
	 else{
	 
	return false;   
	 }
	 }
function isAlphanumeric(tagval)
{
    if(tagval.match(/^[a-zA-Z0-9]+[ a-zA-Z0-9+]*$/) )
     {
         return true;
     }
     else
     {
         return false;
     }
}
function isPhone(tagval)
{
    if(isNumber(tagval) && tagval.length ==10 )
     {
         return true;
     }
     else
     {
         return false;
     }
}
function isDate(tagval)
{
    re = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;

      if(regs = tagval.match(re)) {
        
        // day value between 1 and 31
        if(regs[2] < 1 || regs[2] > 31) {
               
    // month value between 1 and 12         
                if(regs[1] < 1 || regs[1] > 12) {
                    
                    // year value between 1902 and 2016
                         if(regs[3] < 1902 || regs[3] > (new Date()).getFullYear()) {
                            
                             return false;
                           }
                    }
        }
        else
        {
            return true;
        }
        
    
        
        
        
      } else {
        
        return false;
      }
}

function isEmail(tagval)
{
	if(tagval.match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/) )
    {
        return true;
    }
    else
    {
        return false;
    }
}