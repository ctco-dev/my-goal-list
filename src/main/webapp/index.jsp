<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<title>C.T.Co Goal list</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico" />
<style>
    body, h1 {
        font-family: "Raleway", sans-serif
    }
    body, html {
        height: 100%
    }
    .bgimg {
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.3)), url("/images/background.jpg") center;
        min-height: 100%;
        background-size: cover;
    }
</style>
<body>
<div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topleft w3-padding-large w3-xlarge">
        C.T.Co
    </div>
    <div class="w3-display-middle">
        <h1 class="w3-jumbo w3-animate-top w3-center">My Goal List</h1>
        <hr class="w3-border-grey" style="margin:auto;width:40%">
        <p class="w3-xlarge w3-center">A dream becomes a goal</br> when action is taken toward its achievement.</p>
    </div>
    <div class="w3-display-bottomleft w3-padding-large">
        <a href="<c:url value='/login.jsp'/>">Log in</a>
    </div>
</div>
</body>
</html>
