var path = "";
function logout() {
    fetch(path + '/api/auth/logout', {"method": "POST"})
        .then(function (response) {
            location.href = "/";
        });
}
function redirectToGoalsAndComments(id) {
    if (id === "") {
        addNewGoal();
    } else {
        location.href = path + "/app/goal.jsp?id=" + id;
    }
}
function redirectToUserPage(id) {
    location.href = path + "/app/user.jsp?id=" + id;
}
function addNewGoal() {
    location.href = path + "/app/addgoal.jsp";
}
function findGoals() {
    location.href = path + "/app/findgoals.jsp";
}
function goToMain() {
    location.href = path + "/app/start.jsp";
}
function redirectToUserById(id) {
    location.href = path + "/app/user.jsp?id=" + id;
}
function setMinInputDate() {
    var today = new Date();
    var dd = today.getDate() + 1;
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("goal-deadline").setAttribute("min", today);
}
function switchPersonalData() {
    var checkbox = document.getElementById("personal-data");
    var block = document.getElementById("personal-block");
    if (checkbox.checked) {
        block.classList.remove("w3-hide");
    } else {
        block.classList.add("w3-hide");
    }
}

function displayError(response, expected) {
    if (response.status !== expected) {
        alert("Something went wrong! error: " + response.status.toString());
        history.go(-1);
    }
}