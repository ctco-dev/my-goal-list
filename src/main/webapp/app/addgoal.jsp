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
<body onload="onBodyLoad();getAllTagsList();">
<br>
<h1 style="text-align:center;font-family:Cursive;color:#000000;">Add new goal</h1>
<form style="margin-top: 100px; margin-left: 400px;">
    Input your goal:<br>
    <input id="goal-txt" style="height:100px; width:300px;" type="text" name="goal"><br>
    Click deadline on calendar:<br>
    <input id="dealineDate" type="text" id="txtTest" />
    <br>
    <br>
    Input tags
    <input type="text" id="tag"/>
    <button onclick="addItem(); return false;">add item</button>
    <button onclick="removeItem(); return false;">remove item</button>
    <ul id="dynamic-list"></ul>
    <br>

<h2 style="color:red">  filled drop downlist (for test purpusses)
    <select id="tags-list">
        <option w3-repeat="tags">{{tagMessage}}</option>
    </select>
</h2>


    <input type="submit" value="Submit" onclick="submitData()">
</form>
<script>
    var tagList = [];

    function addItem(){
        var ul = document.getElementById("dynamic-list");
        var tag = document.getElementById("tag");
        var li = document.createElement("li");
        if(tag.value == "")
        {
            alert("empty");
            return false;
        }
        li.setAttribute('id',tag.value);
        li.appendChild(document.createTextNode(tag.value));
        ul.appendChild(li);
        tagList.push(tag.value);
    }
    function remove(arr, what) {
        var found = arr.indexOf(what);

        while (found !== -1) {
            arr.splice(found, 1);
            found = arr.indexOf(what);
        }
    }
    function removeItem(){
        var ul = document.getElementById("dynamic-list");
        var tag = document.getElementById("tag");
        var item = document.getElementById(tag.value);
        if(tag.value === "")
        {
            alert("empty");
            return false;
        }
        ul.removeChild(item);
        remove(tagList, tag.value);
    }
    function submitData() {
        var goalTxt = document.getElementById("goal-txt");
        var deadlineTxt = document.getElementById("dealineDate");
        console.log(tagList);
        console.log(String(goalTxt.value));
        console.log(String(deadlineTxt.value));
        if(tagList === "" || String(goalTxt.value) === "" || String(deadlineTxt.value) ==="")
        {
            alert("Not all fields filled out!");
            return false;
        }

        var dto = {
            "goal": goalTxt.value,
            "deadline": deadlineTxt.value,
            "tagList": tagList
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
            console.log("DONE");
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
        pureJSCalendar.open('dd.MM.yyyy', 400, 30, 1, today, nextYear, 'dealineDate', 20);
    }

    function getAllTagsList(){
        console.log( "All Tags list from DB" );
        fetch("<c:url value='/api/goal/taglist'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (tag) {
            var tagData = {"tags": tag };
            console.log(JSON.stringify(tagData));
            w3.displayObject("tags-list", tagData);
        });
    }
</script>
</body>
</html>
