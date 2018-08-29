<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title id="title">{{username}}</title>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
</head>
<body onload="loadUser()">

<div>
    <h1>{{username}}</h1>
</div>

<div id="user-data">
</div>

<div id="user-goals">
    <table id="goals-list" class="w3-table-all w3-hoverable">
        <tr class="w3-blue">
            <th>{{username}} goals</th>
            <th>Deadline</th>
            <th>Days left</th>

        </tr>
        <tr w3-repeat="goals" id="{{id}}" onclick="redirectToGoalsAndComments(id)">

            <td>{{goalMessage}}</td>

            <td>{{deadlineDate}}</td>

            <td>{{daysLeft}}</td>

        </tr>
    </table>
</div>

<script>
    function loadUser() {
        var userid =
            fetch("<c:url value='/api/auth/user/'/>" + userid, {
                "method": "GET",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                return response.json();
            }).then(function (user) {

            });
    }

    function redirectToGoalsAndComments(id) {
        if (id >= 0) {
            location.href = "<c:url value='/app/goal.jsp?id='/>" + id;
        } else {
            addNewGoal()
        }
    }
</script>
</body>
</html>
