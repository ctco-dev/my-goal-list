<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<title>W3.CSS</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="http://www.w3schools.com/lib/w3data.js"></script>
<body>

<header class="w3-container w3-teal">
    <h1>Login or Register</h1>
</header>

<div class="w3-container w3-half w3-margin-top">

    <form class="w3-container w3-card-4">
        <div id="error-panel" class="w3-panel w3-red w3-hide">
            <h3>Error!</h3>
            <p>{{message}}</p>
        </div>

        <p>
            <input id="register-cb" class="w3-check" type="checkbox" onchange="switchRegistration()">
            <label for="register-cb">Registration</label>
        </p>

        <p>
            <input id="username-txt" class="w3-input" type="text" style="width:90%" required>
            <label for="username-txt">Name</label>
        </p>
        <p>
            <input id="password1-txt" class="w3-input" type="password" style="width:90%" required>
            <label for="password1-txt">Password</label>
        </p>

        <p id="password2-grp" class="w3-hide">
            <input id="password2-txt" class="w3-input" type="password" style="width:90%" required>
            <label for="password2-txt">Repeat Password</label>
        </p>

        <p>
            <button id="login-btn" type="button" class="w3-button w3-section w3-teal w3-ripple" onclick="login()">Log
                in
            </button>
            <button id="register-btn" type="button" class="w3-button w3-section w3-teal w3-ripple w3-hide"
                    onclick="register()">Register
            </button>
        </p>

    </form>

</div>

<script>
    function switchRegistration() {
        hideError();
        var checkbox = document.getElementById("register-cb");
        var pwd2 = document.getElementById("password2-grp");
        var loginBtn = document.getElementById("login-btn");
        var registerBtn = document.getElementById("register-btn");
        if (checkbox.checked) {
            pwd2.classList.remove("w3-hide");
            loginBtn.classList.add("w3-hide");
            registerBtn.classList.remove("w3-hide");
        } else {
            pwd2.classList.add("w3-hide");
            loginBtn.classList.remove("w3-hide");
            registerBtn.classList.add("w3-hide");
        }
    }

    function login() {
        hideError();
        console.log("start login");
        var usernameTxt = document.getElementById("username-txt");
        var passwordTxt = document.getElementById("password1-txt");
        var dto = {
            "username": usernameTxt.value,
            "password": passwordTxt.value
        };
        console.log("sending login data");
        fetch("<c:url value='/api/auth/login'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        }).then(function (response) {
            if (response.status === 200) {
                console.log("login success");
                location.href = "/app/start.jsp";
            } else {
                showError("Username or Password is incorrect!");
            }
        })
    }

    function register() {
        hideError();
        console.log("start registration");
        var usernameTxt = document.getElementById("username-txt");
        var password1Txt = document.getElementById("password1-txt");
        var password2Txt = document.getElementById("password2-txt");
        var pwd1 = password1Txt.value;
        var pwd2 = password2Txt.value;
        if (pwd1 !== pwd2) {
            showError("Passwords doesn't match!")
            return;
        }
        var dto = {
            "username": usernameTxt.value,
            "password": pwd1
        };
        console.log("sending registration data");
        fetch("<c:url value='/api/auth/register'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        }).then(function (response) {
            if (response.status === 200) {
                console.log("registration success");
                location.href = "<c:url value='/app/start.jsp'/>";
            } else if (response.status === 401) {
                showError("Something is wrong!");
            } else {
                response.json().then(function(json) {
                    switch(json.errorCode) {
                        case "CONFLICT":
                            showError("A user with the same username already exists!");
                            break;
                        case "BAD_USERNAME":
                            showError("Username is invalid!");
                            break;
                        case "BAD_PASSWORD":
                            showError("Password is invalid!");
                            break;
                        default:
                            showError("Something is wrong!");
                    }
                })
            }
        })
    }

    function hideError() {
        var errorPanel = document.getElementById("error-panel");
        errorPanel.classList.add("w3-hide");
    }
    function showError(msg) {
        var errorPanel = document.getElementById("error-panel");
        errorPanel.classList.remove("w3-hide");
        w3DisplayData("error-panel", {"message" : msg});
    }
</script>
</body>
</html>
