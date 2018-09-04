<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add goals</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="css/style.css"/>

    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script type="text/javascript" src="js/redirects.js"></script>
    <script type="text/javascript" src="js/addgoal-page.js"></script>
</head>
<body>
<div id="menu">
    <div class="button-div">
        <button class="menu-button" onclick="logout()" type="button">Log out</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="findGoals()" type="button">Find goals</button>
    </div>
    <div class="button-div">
        <button class="menu-button" onclick="goToMain()" type="button">Go to Main</button>
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
        <input id="goal-deadline" type="date" onkeydown="return false" >
        <input type="button" value="Submit" onclick="submitData()">
    </form>
</div>
</body>
</html>
