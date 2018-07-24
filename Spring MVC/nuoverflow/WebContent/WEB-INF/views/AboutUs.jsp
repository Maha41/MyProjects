<!doctype html>
<html>
<head>
	<title>ContactUs</title>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
</head>
<body  style="padding:0;margin:0">
 <div style="padding:0;margin:0">


<div style="min-height: 50px"></div>

<div>
<div ng-repeat="person in personList">
  <h3>{{person.firstName}} {{person.firstName}}</h3>
  <p>Email: {{person.email}}</p>
  <p>Phone: {{person.Phone}}</p>
   
</div>
</div>
</div>

</body>
</html>
