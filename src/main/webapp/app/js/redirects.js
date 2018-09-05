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

function setMinDateInputDate() {
    var today = new Date();
    var dd = today.getDate()+1;
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    }
    if(mm<10){
        mm='0'+mm
    }

    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("goal-deadline").setAttribute("min", today);
}

