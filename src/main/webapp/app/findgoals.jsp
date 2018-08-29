<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <title>Find Goals</title>
</head>
<body onLoad="getTagList();switchSearch();">
    <h1>Search for:<br/>
        <input type="radio" name="colors" id="rbtnUser" onclick="switchSearch(); checked ">Goal's Creator by Name
        <input type="radio" name="colors" id="rbtnGoal" onclick="switchSearch();">Goals by tags </h1>
<div id="search-by-Username" class="w3-hide" >
    <input id="username" type="text"  />
    <input type="button" value="Find" onclick="findUserByName();">
</div>
<div id="search-by-Tags" class="w3-hide" >
    <h1>Search by Goal's tags</h1>
    <input type="button" value="Find" onclick="findGoalByTags();">
</div>
<div id="Users-List" class="w3-hide" >
    <h1>Found Names ({{nameTemplate}})</h1>
</div>
<div id="Goals-List" class="w3-hide" >
    <h1>Found Goals ({{tagsTemplate}})</h1>
</div>

<script>

    function switchSearch() {
        var rbutton = document.getElementById("rbtnUser");
        var blockUser = document.getElementById("search-by-Username");
        var blockGoal = document.getElementById("search-by-Tags");
        if (rbutton.checked) {
            blockUser.classList.remove("w3-hide");
            blockGoal.classList.add("w3-hide");
        } else {
            blockUser.classList.add("w3-hide");
            blockGoal.classList.remove("w3-hide");
        }
    }

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

   function findUserByName() {
       var username = document.getElementById("username");
       var dto = {
           "usersearch": username.value
       };
       console.log(JSON.stringify(dto));
       fetch("<c:url value='/api/goal/search-user'/>", {
           "method": "POST",
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           },
           body: JSON.stringify(dto)
       }).then(function (response) {
           return response.json();
       }).then(function (users) {
           console.log(JSON.stringify(users));
       });
   }

   function findGoalByTags() {
       var username = document.getElementById("username");
       var arr=[];
       var dto = {
//           "tagMessage": tagMsg.value
       };
       arr.add(dto);
       console.log(JSON.stringify(dto));
       fetch("<c:url value='/api/goal/findgoals'/>", {
           "method": "POST",
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           },
           body: JSON.stringify(arr)
       }).then(function (response) {

       });
   }


</script>

</body>
</html>
