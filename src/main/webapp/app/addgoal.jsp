<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gatis.laizans01
  Date: 8/22/2018
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add goals</title>
    <link rel="stylesheet" href="css/calendar.css" />
    <script type="text/javascript" src="js/pureJSCalendar.js"></script>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
</head>
<body onload="onBodyLoad();">
<br>
<h1 style="text-align:center;font-family:Cursive;color:#000000;">Add new goal</h1>
<form style="margin-top: 100px; margin-left: 400px;">
    Input your goal:<br>
    <input id="goal-txt" style="height:100px; width:300px;" type="text" name="goal"><br>
    Click deadline on calendar:<br>
    <input id="deadlineDate" type="text" id="txtTest" />
    <input type="submit" value="Submit" onclick="submitData()">
</form>
<script>
    function submitData() {
        var goalTxt = document.getElementById("goal-txt");
        var deadlineTxt = document.getElementById("deadlineDate");
        if(String(goalTxt.value) === "" || String(deadlineTxt.value) ==="")
        {
            alert("Not all fields filled out!");
            return false;
        }

        var dto = {
            "goal": goalTxt.value,
            "deadline": deadlineTxt.value,
        };
        console.log(JSON.stringify(dto));
        fetch("<c:url value='/api/goal/newgoal'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        }).then(function (response) {

        });
    }
    function onBodyLoad() {
        var d = new Date();
        var year = d.getFullYear();
        var month = d.getMonth()+1;
        var day = d.getDate();
        if(day<10) day='0'+day;
        if(month<10) month='0'+month;

        var today = year +'-'+month+'-'+day;
        var nextYear = (year+1) +'-'+month+'-'+(day+1);
        pureJSCalendar.open('dd.MM.yyyy', 400, 30, 1, today, nextYear, 'deadlineDate', 20);
    }
</script>
</body>
</html>
