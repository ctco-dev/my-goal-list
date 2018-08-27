<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title id="title">{{goalMessage}}</title>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body onload="onLoad()">

<table id="goal-fields">
    <tr>
        <th>Author: {{username}}</th>
    </tr>
    <tr>
        <th>To be completed till: {{deadlineDate}} (days left: {{daysLeft}})</th>
    </tr>
    <tr>
        <th>
            <h2>{{goalMessage}}</h2>
        </th>
    </tr>
</table>


<div class="container">
    <div class="col-lg-4 col-sm-6 text-center">
        <div class="well">
            <h4>What do you think about this Goal?</h4>
            <div class="input-group">
                <input type="text" id="userComment" class="form-control input-sm chat-input"
                       placeholder="Write your message here..."/>
                <span class="input-group-btn" onclick="addComment()">
                    <a href="#" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-comment"></span> Add Comment</a>
                </span>
            </div>
            <hr data-brackets-id="12673">


            <ul data-brackets-id="12674" id="sortable" class="list-unstyled ui-sortable w3-hide">
                <strong class="pull-left primary-font">{{Comentator}}</strong>
                <small class="pull-right text-muted">
                    <span class="glyphicon glyphicon-time"></span>{{Time Since Post}}
                </small>
                </br>
                <li class="ui-state-default">{{comentText}}</li>
                </br>
            </ul>

        </div>
    </div>


    <script>
        var id = getQueryVariable("id");

        function getComments() {
            fetch("<c:url value='/api/goal/comments/'/>" + id, {
                "method": "GET",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                return response.json();
            }).then(function (coments) {
                console.log(coments);
                if (coments.length > 0) {
                    document.getElementById("sortable").classList.remove("w3-hide");
                }

            });
        }

        function onLoad() {
            fetch("<c:url value='/api/goal/mygoals/'/>" + id, {
                "method": "GET",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                return response.json();
            }).then(function (goal) {
                w3.displayObject("title", goal);
                w3.displayObject("goal-fields", goal);
            });
            getComments();
        }

        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return (false);
        }


    </script>
</body>
</html>
