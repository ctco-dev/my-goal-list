<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search | C.T.Co Goal list</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="css/style.css"/>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script type="text/javascript" src="js/commons.js"></script>
    <script type="text/javascript" src="js/findgoals-page.js"></script>
    <title>Search</title>
</head>
<body onLoad="switchSearch();loadTags()">
<div id="wrapper">
    <div id="menu">
        <div class="button-div">
            <button class="menu-button w3-button w3-brown w3-round" onclick="logout()" type="button">Log out</button>
        </div>
        <div class="button-div">
            <button class="menu-button w3-button w3-brown w3-round" onclick="findGoals()" type="button">Search</button>
        </div>
        <div class="button-div">
            <button class="menu-button w3-button w3-brown w3-round" onclick="goToMain()" type="button">Go to Main</button>
        </div>
    </div>
    <div id="search-block" class="w3-panel w3-light-grey w3-leftbar w3-border w3-round-large">
    <h2>Search for:</h2>
        <center><input type="radio" name="colors" id="rbtnUser" onclick="switchSearch();">Goal's Creator by Name
        <input type="radio" name="colors" id="rbtnGoal" onclick="switchSearch();">Goals by Tag</center>
    <div id="search-by-Username" class="w3-hide">
        <input id="username" type="text"/>
        <input type="button" class="findButton w3-button w3-brown w3-round" value="Find" onclick="findUserByName();">
    </div>
    <div id="search-by-Tags" class="w3-hide">
        <input id="tag" type="text" list="tags"/>
        <datalist id="tags">
        </datalist>
        <input type="button" class="findButton w3-button w3-brown w3-round" value="Find" onclick="findGoalsByTag();">
    </div>
    <table id="Users-List" class="w3-table-all w3-hoverable w3-hide">
        <tr class="w3-brown">
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
    </div>
    <table id="Goals-List" class="w3-table-all w3-hoverable w3-hide">
        <tr class="w3-brown">
            <th>Goal</th>
            <th>Deadline</th>
            <th>Days left</th>
        </tr>
        <tr w3-repeat="goals" id="{{id}}" class="{{goalStatus}}" onclick="redirectToGoalsAndComments(id)">
            <td>{{goalMessage}}</td>
            <td>{{deadlineDate}}</td>
            <td>{{daysLeft}}</td>
        </tr>
    </table>

</div>
</body>
</html>