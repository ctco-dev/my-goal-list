<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html>
<head>
    <title>Main | C.T.Co Goal list</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="shortcut icon" type="image/x-icon" href="../images/favicon.ico" />
    <script type="text/javascript" src="js/commons.js"></script>
    <script type="text/javascript" src="js/start-page.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <title>My Goals</title>
</head>
<body onload="showUserProfile();showUserGoals();switchPersonalData();">
<div id="wrapper">
    <div id="menu">
        <div class="button-div">
            <button class="menu-button w3-button w3-brown w3-round" onclick="logout()" type="button">Log out</button>
        </div>
        <div class="button-div">
            <button class="menu-button w3-button w3-brown w3-round" onclick="findGoals()" type="button">Search</button>
        </div>
        <div class="button-div">
            <button class="menu-button w3-button w3-brown w3-round" onclick="addNewGoal()" type="button">Add new goal</button>
        </div>
    </div>
    <h1 id="name">name</h1>
    <div id="personal">
        <input id="personal-data" class="w3-check" type="checkbox" onchange="switchPersonalData();">
        <label for="personal-data">Personal Data</label>
    </div>
    <div id="personal-block" class="w3-hide">
        <h3 id="phone_email"></h3>
    </div>
    <table id="goals-list" class="w3-table-all w3-hoverable">
        <tr class="w3-brown">
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
</div>
</body>
</html>
