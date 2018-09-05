<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title id="title">{{goalMessage}}</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript" src="js/redirects.js"></script>
    <script type="text/javascript" src="js/goal-page.js"></script>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body onload="onLoad()">
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
    <div class="button-div">
        <button id="edit-button" class="menu-button" onclick="editGoal()" type="button">Edit Goal</button>
    </div>
</div>
<div id="goal-fields">
    <div id="show-goal">
        <h3>Goal: {{goalMessage}}</h3>
        <h5>Author: <a onclick="redirectToUserById('{{userId}}')">{{username}}</a></h5>
        <h5>Deadline: {{deadlineDate}} (days left: {{daysLeft}})</h5>
        <div id="tags-list" w3-repeat="tags">
            <span id="{{id}}">{{tagMessage}}</span>
        </div>
    </div>
    <div id="edit-goal">
        <label for="edit-goal-text">Goal: </label>
        <input id="edit-goal-text" type="text" value="{{goalMessage}}">
        <br/>
        <h5>Author: <a onclick="redirectToUserById('{{userId}}')">{{username}}</a></h5>
        <label for="goal-deadline">Deadline: </label>
        <input id="goal-deadline" type="date" onkeydown="return false">
        <div class="button-div">
            <button class="menu-button" onclick="saveEditGoal()" type="button">Save</button>
        </div>
    </div>
</div>
<div class="container">
    <div class="text-center">
        <div class="well">
            <h4>What do you think about this Goal?</h4>
            <div class="input-group">
                <input type="text" id="userComment" placeholder="Write your message here..."/>
                <span>
                    <a onclick="addComment()" class="btn"> Add Comment</a>
                </span>
            </div>
            <hr>
            <ul id="sortable" class="w3-hide">
                <li w3-repeat="comments" class="repeatable">
                    <strong>{{username}}</strong>
                    <small>
                        <span class="glyphicon glyphicon-time"></span>{{registeredDate}}
                    </small>
                    <br/>
                    <br/>
                    <p>{{commentMessage}}</p>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
