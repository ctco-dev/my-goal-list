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

<h1 style="text-align:center;font-family:Cursive;color:#000000;">My goals</h1>
<H2 id="name">name</H2>
<div style="display: inline;" align="center">
    <div style="float: right;">
        <button style="color: white; background-color: deepskyblue; padding: 15px 32px; text-align: center; text-decoration: none; border-radius: 4px; display: inline-block; font-size: 100%;" onclick="logout()" type="button">Log out</button>
    </div>
    <div style="float: right;">
        <button style="color: white; background-color: deepskyblue; padding: 15px 32px; text-align: center; text-decoration: none; border-radius: 4px; display: inline-block; font-size: 100%;" onclick="findGoals()" type="button">Find goals</button>
    </div>
    <div style="float: right;">
        <button style="color: white; background-color: deepskyblue; padding: 15px 32px; text-align: center; text-decoration: none; border-radius: 4px; display: inline-block; font-size: 100%;" onclick="addNewGoal()" type="button">Add new goal</button>
    </div>
</div>
<input id="personal-data" style = "display: inline-block; float: left" class="w3-check" type="checkbox" onchange="switchPersonalData();">
<label for="personal-data" style = "display: inline-block; float: left;">Personal Data</label>

<div id="personal-block" class="w3-hide" style = "display: inline-block; float: left;" >
    <H3 id="phone_email"></H3>
</div>

<table id="goals-list" class="w3-table-all w3-hoverable">
    <tr class="w3-blue">
        <th>My goals</th>
        <th>Days left</th>
    </tr>
    <tr w3-repeat="goals">

        <td>{{goalMessage}}</td>

        <td>{{}}</td>

    </tr>
</table>
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
            document.getElementById("name").innerHTML = user.username;
            document.getElementById("phone_email").innerHTML = "Phone: "+user.phone+" |  E-mail: "+user.email;
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
            var tabledata = {"goals": goals};
            document.getElementById("topic-list").classList.remove("w3-hide");
            w3DisplayData("goals-list", tabledata);
        });
    }
    function addNewGoal() {
        fetch("<c:url value='/api/goal'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/app/addgoal.jsp";
            });
    }
    function findGoals() {
        fetch("<c:url value='/api/goal'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/app/findgoals.jsp";
            });
    }
</script>
</body>
</html>
