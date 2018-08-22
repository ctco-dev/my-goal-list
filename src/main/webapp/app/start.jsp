<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <title>My Goals</title>
</head>
<body onload="showUserProfile();showUserGoals();">
<button type="button" onclick="logout()">Log out</button>

<H1>Name and Surname</H1>
<input id="personal-data" class="w3-check" type="checkbox" onchange="switchPersonalData();">
<label for="personal-data">Personal Data</label>

<div id="personal-block" class="w3-hide">
<H2>Phone Number</H2>
<H2>e-mail</H2>
</div>

<script>
    function logout() {
        fetch("<c:url value='/api/auth/logout'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/";
            });
    }

    function switchPersonalData() {
        var checkbox = document.getElementById("personal-data");
        var block = document.getElementById("personal-block");
        if (checkbox.checked) {
            block.classList.remove("w3-hide");
        } else {
            block.classList.add("w3-hide");
        }
    }

    function showUserProfile() {
        console.log("User Profile Data");
        fetch("<c:url value='/api/auth/myprofile'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (user) {
            console.log(JSON.stringify(user));
        });
    }

    function showUserGoals() {
        console.log("User Goals List");
        fetch("<c:url value='/api/goal/mygoals'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (goals) {
            console.log(JSON.stringify(goals));
        });
    }


</script>
</body>
</html>
