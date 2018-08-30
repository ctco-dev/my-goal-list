var path = "";

function logout() {
    fetch(path + '/api/auth/logout', {"method": "POST"})
        .then(function (response) {
            location.href = "/";
        });
}
function redirectToGoalsAndComments(id) {
    if (id >= 0) {
        location.href = path + "/app/goal.jsp?id=" + id;
    } else {
        addNewGoal()
    }
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
