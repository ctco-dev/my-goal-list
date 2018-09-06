<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add goal | C.T.Co Goal list</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="css/style.css"/>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script type="text/javascript" src="js/commons.js"></script>
    <script type="text/javascript" src="js/addgoal-page.js"></script>
</head>
<body onload="setMinInputDate();loadTags()">
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
    </div>
    <br>
    <div id="form">
        <h2>Add new goal</h2>
        <form>
            Input your goal:<br>
            <textarea id="goal-txt" name="goal" rows="10" cols="30"
                      placeholder="Write here your goal..." autofocus></textarea>
            <label for="goal-deadline"><br/>Deadline Date: <br/></label>
            <input id="goal-deadline" type="date" onkeydown="return false">
            <br>Tags:(Max:3)
            <br>
            <div id="tag-list-holder">
                <input id="tags-field-1" type="text" list="tags"/>
                <input id="tags-field-2" type="text" list="tags"/>
                <input id="tags-field-3" type="text" list="tags"/>
                <datalist id="tags">
                </datalist>
            </div>
            <input type="button" class="w3-button w3-blue w3-round" value="Submit" onclick="submitData()">
        </form>
    </div>
</div>
</body>
</html>