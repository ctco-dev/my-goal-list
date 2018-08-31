<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add goals</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script type="text/javascript" src="js/redirects.js"></script>
    <script type="text/javascript" src="js/addgoal-page.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script>
        $(function () {
            $("#datepicker").datepicker({dateFormat: 'dd.mm.yy'});
            $('#field1').editableSelect({effects: 'fade'});
            $('#field2').editableSelect({effects: 'fade'});
            $('#field3').editableSelect({effects: 'fade'});
        });
    </script>
</head>
<body onload="loadTegs()">
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
        <br>Deadline Date:<br>
        <input type="text" id="datepicker">

        <br>Tags:(Max:3)
        <br>
        <div id="tag-list-holder">
            <select id="field1">
                <option w3-repeat="tegs" value="{{tagMessage}}">{{tagMessage}}</option>
            </select>
            <select id="field2">
                <option w3-repeat="tegs" value="{{tagMessage}}">{{tagMessage}}</option>
            </select>
            <select id="field3">
                <option w3-repeat="tegs" value="{{tagMessage}}">{{tagMessage}}</option>
            </select>
        </div>
        <input type="button" value="Submit" onclick="submitData()">
    </form>

</div>
</body>
</html>

