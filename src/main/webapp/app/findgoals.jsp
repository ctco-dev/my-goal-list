<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script type="text/javascript" src="js/redirects.js"></script>
    <script type="text/javascript" src="js/findgoals-page.js"></script>
    <title>Search</title>
</head>
<body onLoad="switchSearch();loadTags()">
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
<h2>Search for:<br/>
    <input type="radio" name="colors" id="rbtnUser" onclick="switchSearch();">Goal's Creator by Name<br>
    <input type="radio" name="colors" id="rbtnGoal" onclick="switchSearch();">Goals by Tag</h2>
<div id="search-by-Username" class="w3-hide">
    <input id="username" type="text"/>
    <input type="button" id="findUserButton" value="Find" onclick="findUserByName();">
</div>
<div id="search-by-Tags" class="w3-hide">
    <input id="tag"  type="text" list="tags"/>
    <datalist id="tags">
    </datalist>
    <input type="button" id="findGoalButton" value="Find" onclick="findGoalsByTag();">
</div>
<table id="Users-List" class="w3-table-all w3-hoverable w3-hide">
    <tr class="w3-blue">
        <th>User</th>
        <th>E-mail</th>
        <th>Phone number</th>
    </tr>
    <tr w3-repeat="users" id="{{id}}" onclick="redirectToUserPage(id)">
        <td>{{username}}</td>
        <td>{{email}}</td>
        <td>{{phone}}</td>
    </tr>
</table>
<table id="Goals-List" class="w3-table-all w3-hoverable w3-hide">
    <tr class="w3-blue">
        <th>Goal</th>
        <th>Deadline</th>
        <th>Days left</th>
    </tr>
    <tr w3-repeat="goals" id="{{id}}" onclick="redirectToGoalsAndComments(id)">
        <td>{{goalMessage}}</td>
        <td>{{deadlineDate}}</td>
        <td>{{daysLeft}}</td>
    </tr>
</table>
</body>
</html>