<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title id="title">{{goalMessage}} | C.T.Co Goal list</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript" src="js/commons.js"></script>
    <script type="text/javascript" src="js/goal-page.js"></script>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
</head>
<body onload="onLoad()">
<div id="wrapper">
    <div id="menu">
        <div class="button-div">
            <button class="menu-button w3-button w3-blue w3-round" onclick="logout()" type="button">Log out</button>
        </div>
        <div class="button-div">
            <button class="menu-button w3-button w3-blue w3-round" onclick="findGoals()" type="button">Search</button>
        </div>
        <div class="button-div">
            <button class="menu-button w3-button w3-blue w3-round" onclick="goToMain()" type="button">Go to Main</button>
        </div>
        <div class="button-div">
            <button id="edit-button" class="menu-button w3-button w3-blue w3-round" onclick="editGoal()" type="button">Edit Goal</button>
        </div>
        <div class="button-div" id="status-achieved">
            <button class="menu-button w3-button w3-blue w3-round" onclick="setStatusAchieved('{{id}}')" type="button">Achieved</button>
        </div>
    </div>
    <div id="goal-fields" class="w3-panel w3-light-grey w3-leftbar w3-border w3-round-large">
        <div id="show-goal">
            <h3>Goal: {{goalMessage}}</h3>
            <h5>Author: <a onclick="redirectToUserById('{{userId}}')">{{username}}</a></h5>
            <h5>Deadline: {{deadlineDate}}</h5>
            <h5 class="{{goalStatus}}">Goal status: {{goalStatus}}</h5>
            <h5>Tags:</h5>
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
                <button class="menu-button w3-button w3-blue w3-round" onclick="saveEditGoal()" type="button">Save</button>
            </div>
        </div>
    </div>
    <div class="container w3-panel w3-leftbar w3-border w3-light-grey w3-round-large">
        <div class="text-center">
            <div class="well">
                <h4>What do you think about this Goal?</h4>
                <div class="input-group">
                    <input type="text" id="userComment" placeholder="Write your message here..."/>
                    <span>
                    <a onclick="addComment()" class="w3-button w3-blue w3-round"> Add Comment</a>
                </span>
                </div>
                <hr>
                <ul id="sortable" class="w3-hide">
                    <li w3-repeat="comments" class="repeatable">
                        <strong> <a onclick="redirectToUserById('{{userId}}')">{{username}}</a></strong>
                        <small>
                            <span class="glyphicon glyphicon-time"></span>{{registeredDate}}
                        </small>
                        </br>
                        </br>
                        <p>{{commentMessage}}</p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
