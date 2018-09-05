<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<title>My Goal List</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<style>
    body, h1 {
        font-family: "Raleway", sans-serif
    }

    body, html {
        height: 100%
    }

    .bgimg {
        background-image: url('http://stephaniemulac.com/blog/wp-content/uploads/2015/09/powerfully.jpg');
        min-height: 100%;
        background-position: center;
        background-size: cover;
    }
</style>
<body onload="addDatLeft();">

<div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topleft w3-padding-large w3-xlarge">
        C.T.Co
    </div>
    <div class="w3-display-middle">
        <h1 class="w3-jumbo w3-animate-top">COMING SOON</h1>
        <hr class="w3-border-grey" style="margin:auto;width:40%">
        <p id="days-left" class="w3-large w3-center">days left</p>
    </div>
    <div class="w3-display-bottomleft w3-padding-large">
        <a href="<c:url value='/login.jsp'/>">Log in</a>
    </div>
</div>

<script>
    function addDatLeft() {
        var today = new Date();
        var deadline = new Date("2018-09-07");
        if (deadline.getDate() < today.getDate()) {
            var daysLeftOfMonth = new Date(today.getYear(), today.getMonth() + 1, 0).getDate();
            var line = ((daysLeftOfMonth - today.getDate() + 1) + deadline.getDate()).toString() + " days left"
        } else var line = ((deadline.getDate() + 1) - today.getDate()).toString() + " days left";
        document.getElementById('days-left').innerHTML = line;
    }

</script>

</body>
</html>
