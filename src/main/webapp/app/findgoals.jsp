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
    <script type="text/javascript" src="js/addgoal-page.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script src="https://rawgithub.com/indrimuska/jquery-editable-select/master/dist/jquery-editable-select.min.js"></script>
    <link href="https://rawgithub.com/indrimuska/jquery-editable-select/master/dist/jquery-editable-select.min.css"
          rel="stylesheet">
    <title>Find Goals</title>
</head>
<body onLoad="getTagList();switchSearch();">
<div id="menu">
    <div class="button-div">
        <button class="menu-button" onclick="logout()" type="button">Log out</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="findGoals()" type="button">Find goals</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="addNewGoal()" type="button">Add new goal</button>
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
    <input id="tag" type="text"/>
    <input type="button" id="findGoalButton" value="Find" onclick="findGoalsByTag();">
</div>
<div id="Users-List" class="w3-hide">
    <h2>Found Names</h2>
    <ul id="users">
        <li w3-repeat="users">{{username}}
        </li>
    </ul>
</div>
<div id="Goals-List" class="w3-hide">
    <h2>Found Goals</h2>
    <ul id="goals">
        <li w3-repeat="goals">{{goalMessage}}
        </li>
    </ul>
</div>

<script>
    function switchSearch() {
        var userRadioButton = document.getElementById("rbtnUser");
        var goalRadioButton = document.getElementById("rbtnGoal");
        var blockUser = document.getElementById("search-by-Username");
        var blockGoal = document.getElementById("search-by-Tags");
        if (userRadioButton.checked) {
            blockUser.classList.remove("w3-hide");
            blockGoal.classList.add("w3-hide");
            document.getElementById("Goals-List").classList.add("w3-hide");
        } else if (goalRadioButton.checked)   {
            blockUser.classList.add("w3-hide");
            blockGoal.classList.remove("w3-hide");
            document.getElementById("Users-List").classList.add("w3-hide");
        } else {
            blockUser.classList.add("w3-hide");
            blockGoal.classList.add("w3-hide");
            document.getElementById("Users-List").classList.add("w3-hide");
            document.getElementById("Goals-List").classList.add("w3-hide");
        }
    }

    function getTagList() {
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
            console.log(users);
            if (users.length > 0) {
                var tabledata = {'users': users};
                document.getElementById("Users-List").classList.remove("w3-hide");
                w3DisplayData("users", tabledata);
            } else {
                document.getElementById("Users-List").classList.add("w3-hide");
            }
        });
    }

    function findGoalsByTag() {
        var tag = document.getElementById("tag").value;
        fetch("<c:url value='/api/goal/tag/'/>" + tag, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (similargoals) {
            console.log(similargoals);
            if (similargoals.length > 0) {
                var tabledata = {'goals': similargoals};
                document.getElementById("Goals-List").classList.remove("w3-hide");
                w3DisplayData("goals", tabledata);
            } else {
                document.getElementById("Goals-List").classList.add("w3-hide");
            }
        });
    }
</script>

</body>
</html>