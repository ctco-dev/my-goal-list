<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript" src="js/redirects.js"></script>
    <script type="text/javascript" src="js/start-page.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <title>My Goals</title>
</head>

<body onload="showUserProfile();showUserGoals();switchPersonalData();">
<div id="menu">
    <div class="button-div">
        <button class="menu-button" onclick="logout()" type="button">Log out</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="findGoals()" type="button">Search</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="addNewGoal()" type="button">Add new goal</button>
    </div>
</div>
<h1 id="name">name</h1>
<div id="personal">
    <input id="personal-data" class="w3-check" type="checkbox" onchange="switchPersonalData();">
    <label for="personal-data">Personal Data</label>
</div>
<div id="personal-block" class="w3-hide">
    <H3 id="phone_email"></H3>
</div>
<table id="goals-list" class="w3-table-all w3-hoverable">
    <tr class="w3-blue">
        <th>My goals</th>
        <th>Deadline</th>
        <th>Days left</th>
    </tr>
    <tr w3-repeat="goals" class="{{goalStatus}}" onclick="redirectToGoalsAndComments('{{id}}')">
        <td>{{goalMessage}}</td>
        <td>{{deadlineDate}}</td>
        <td>
            <span class="{{goalStatus}}">{{daysLeft}}</span>
        </td>
    </tr>
</table>
</body>
</html>
