<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Goals</title>
</head>
<body onLoad="getTagList();">
<h1>Goal search</h1>



<script>
   function getTagList(){
       fetch("<c:url value='/api/goal/taglist'/>", {
           "method": "GET",
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           }
       }).then(function (response) {
           return response.json();
       }).then(function (tags) {
           console.log(JSON.stringify(tags));
       });

   }




</script>

</body>
</html>
