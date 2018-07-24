<html>
<head>

<title>Insert title here</title>
</head>
<body>
<div>
<div class="row">

            <div class="col-sm-12" style="text-align: center">
                <h1 class="page-header">Jobs</h1>
            </div>
</div>
<div style="min-height: 10px"></div>
<div class="row">
 <div class="col-sm-6">
 <input type="button" value="Add Job" onclick="displayDiv()" class="btn btn-success"/>
 </div>
 
 </div>
 <div style="min-height: 20px"></div>
 <div style="min-height: 30px"></div>
 <div id = "addJob" style="display:none">
 <h4>Add Job</h4>    
 <div class="row" >
   <div class="col-sm-12">
<div class = "form-group">
<form action="addJob.htm"  method="post" onsubmit="return ValidateData()" enctype="multipart/form-data" >
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Job Name: 
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.jobName")    
	          <input type="text" name="jobName" id="alphanum1" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
      
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Company Name: 
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.companyname")    
	          <input type="text" name="companyname" id="text1" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Company Logo: 
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.companylogo")    
	          <input type="text" name="companylogo" id="alphanum2" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Image: (size 400 X 400)
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.companyImage")    
	          <input type="file" name="companyImage" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
       <div class="row">
	        	<div class="col-sm-2" >
	         		 Jobdescription:
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.jobdescription")    
	          <input type="text" name="jobdescription" id="alphanum2" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
       <div class="row">
       		  <div class="col-sm-12">
        		 #if($error)
		         $error
		         
		         #end
		      </div>
       </div>
         <div class="row">
        	<div class="col-sm-12">
        	
         		 <input type="submit" value="Add" class="btn btn-success" />
         	
          	</div>
          	
        </div>
        
        </form>
</div>
</div>
</div>
</div>
<div id = "editJob" style="display:none">
 <h4>Edit Job</h4>    
 <div class="row" >
   <div class="col-sm-12">
<div class = "form-group">
<form action="editJob.htm"  method="post" >
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Job Name: 
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.jobName")    
	          <input type="text" name="jobName" id="jobname1" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
      
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Company Name: 
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.companyname")    
	          <input type="text" name="companyname" id="companyname1" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Company logo: 
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.companylogo")    
	          <input type="text" name="companylogo" id="companylogo1" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
        <div class="row">
	        	<div class="col-sm-2" >
	         		 Image: (size 400 X 400)
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.jobImage")    
	          <input type="file" name="jobImage" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
       <div class="row">
	        	<div class="col-sm-2" >
	         		 Description:
				</div>
	          	<div class="col-sm-2" >
	  		#springBind("job.jobdescription")    
	          <input type="text" name="jobdescription" id="desc1" class = "form-control"/>
	          </div>
	          <div class="col-sm-8">
	          <font color="red">${status.errorMessage}</font>
	          </div>
       </div>
       <div class="row">
       		  <div class="col-sm-12">
        		 #if($error)
		         $error
		         
		         #end
		      </div>
       </div>
         <div class="row">
        	<div class="col-sm-12">
        	#springBind("jobb.jobbid")
        		<input type = "hidden" name="jobid" value="jobid" />
         		 <input type="submit" value="Update" class="btn btn-success" />
         	
          	</div>
          	
        </div>
        
        </form>
</div>
</div>
</div>
</div>

 
#if($message == "Job Already Exist")
<font color="green">$message</font>
#else
<div class="row" >
<div class="col-sm-12" style="text-align: center">
 #if($jobList.size() == 0)
 <font color="red">No Data found</font>
  #else
  <table class="table table-bordered">
  <thead>
  <tr>
      <th>Job Id </th>
      <th> Job Name </th>
      <th> Company Name </th>
      <th> Image </th>
      <th> Delete </th>
      <th> Edit </th>
  </tr>  
  </thead>        
	#foreach($jobs in $jobList)
	<tbody>
   	<tr id="job${jobs.jobid}">
   		<td>${jobs.jobid}</td>
   		<td>${jobs.jobName}</td>
   		<td>${jobs.companyname}</td>
   		<td><img src="resources/images/jobs/${jobs.companyLogo}" />  </td>
   		<td><a href="#" onclick= "deleteRow(${jobs.jobid})" class="glyphicon glyphicon-trash" ></a></td>
   		<td><a href="#" onclick= "displayDivEdit(${jobs.jobid},'${jobs.jobName}','${jobs.companyname}','${jobs.companylogo}')" class="glyphicon glyphicon-pencil" ></a></td>
   	</tr>
   	</tbody>
 	#end
 	</table>
#end	
 
 </div>
 </div>
#end
 

<script>
function displayDiv()
{
	var dividadd = document.getElementById("addJob");
	var dividedit = document.getElementById("editJob");
	dividadd.style.display = "block";
	dividedit.style.display = "none";
}
function displayDivEdit(jobid,jobName,companyname,companylogo)
{

	var dividadd = document.getElementById("addJob");
	var dividedit = document.getElementById("editJob");
	dividadd.style.display = "none";
	dividedit.style.display = "block";
	
}
 var xmlHttp;
        xmlHttp = GetXmlHttpObject();
 function deleteRow(jobID) {
 
            if (xmlHttp == null)
            {
                alert("Your browser does not support AJAX!");
                return;
            }

            var query = "jobid=" + jobID;

            xmlHttp.onreadystatechange = function stateChanged()
            {
                if (xmlHttp.readyState == 4)
                {
                	var rowid = "job"+jobID;
                    var row = document.getElementById(rowid);
                    
                    row.parentNode.removeChild(row);
                }
            };
            xmlHttp.open("POST", "deleteJob.htm", true);
            xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlHttp.send(query);
            
            return false;
        }
        function GetXmlHttpObject()
        {
            var xmlHttp = null;
            try
            {
                // Firefox, Opera 8.0+, Safari
                xmlHttp = new XMLHttpRequest();
            } catch (e)
            {
                // Internet Explorer
                try
                {
                    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e)
                {
                    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
            }
            return xmlHttp;
        }

</script>

</div>

</body>
</html>