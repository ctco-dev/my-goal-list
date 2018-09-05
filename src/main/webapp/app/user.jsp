<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title id="title">{{username}}</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript" src="js/redirects.js"></script>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script type="text/javascript" src="js/user-page.js"></script>
</head>
<body onload="loadUser()">
<div id="menu">
    <div class="button-div">
        <button class="menu-button" onclick="logout()" type="button">Log out</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="findGoals()" type="button">Search</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="goToMain()" type="button">Go to Main</button>
    </div>
</div>

<h1 id="username">{{username}}</h1>

<div id="personal">
    <input id="personal-data" class="w3-check" type="checkbox" onchange="switchPersonalData();">
    <label for="personal-data">Personal Data</label>
</div>

<div id="personal-block" class="w3-hide">
    <H3 id="email-and-phone">{{email}} | {{phone}}</H3>
</div>

<h4 id="hidden" class="w3-hide">!!Current User has no goals Set for now!!</h4>
<table id="goals-list" class="w3-table-all w3-hoverable">
    <tr class="w3-blue">
        <th>My goals</th>
        <th>Deadline</th>
        <th>Days left</th>

    </tr>
    <tr w3-repeat="goals" id="{{id}}" class="{{goalStatus}}" onclick="redirectToGoalsAndComments(id)">

        <td>{{goalMessage}}</td>

        <td>{{deadlineDate}}</td>

        <td>{{daysLeft}}</td>

    </tr>
</table>
</body>
</html>