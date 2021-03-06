var path = "";
var id = getQueryVariable("id");
var isMyGoal;
function getComments() {
    fetch(path + "/api/goals/" + id + "/comments", {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (coments) {
        if (coments.length > 0) {
            var tabledata = {'comments': coments};
            document.getElementById("sortable").classList.remove("w3-hide");
            w3DisplayData("sortable", tabledata);
        }
    });
}
function onLoad() {
    document.getElementById("edit-goal").classList.add("w3-hide");
    fetch(path + "/api/goals/" + id, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        displayError(response, 200);
        return response.json();
    }).then(function (goal) {
        w3.displayObject("title", goal);
        w3.displayObject("goal-fields", goal);
        w3.displayObject("status-achieved", goal);
        w3DisplayData("tags-list", goal);
        document.getElementById("goal-deadline").setAttribute("value", goal.deadlineDate);
        getComments();
    });
    enableEditForGoalOwner();
    setMinInputDate();
}
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}
function addComment() {
    var data = {'message': document.getElementById("userComment").value};
    if (document.getElementById("userComment").value) {
        fetch(path + "/api/goals/" + id + "/comments", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }, body: JSON.stringify(data)
        }).then(function (response) {
            displayError(response, 204);
        }).then(function () {
            document.getElementById("userComment").value = "";
            onLoad();
        })
    }
}
function enableEditForGoalOwner() {
    fetch(path + "/api/goals/" + id + "/edit", {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        displayError(response, 200);
        return response.json();
    }).then(function (goalEdit) {
        if (!goalEdit) {
            document.getElementById("edit-button").classList.add("w3-hide");
            document.getElementById("status-achieved").classList.add("w3-hide");
        }
        isMyGoal = goalEdit;
    });
}
function editGoal() {
    if (isMyGoal) {
        document.getElementById("show-goal").classList.add("w3-hide");
        document.getElementById("edit-goal").classList.remove("w3-hide");
        document.getElementById("edit-button").classList.add("w3-hide");
        document.getElementById("status-achieved").classList.add("w3-hide");
    } else {
        alert("You can edit only your own goals");
    }
}
function saveEditGoal() {
    var goalTxt = document.getElementById("edit-goal-text");
    var deadlineDate = document.getElementById("goal-deadline");
    if (String(goalTxt.value) === "" || String(deadlineDate.value) === "") {
        alert("You can not delete your goal!");
        return false;
    }
    var dto = {
        "goalMessage": goalTxt.value,
        "deadline": deadlineDate.value
    };
    fetch(path + "/api/goals/" + id + "/edit", {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    }).then(function (response) {
        displayError(response, 204);
    }).then(function () {
        redirectToGoalsAndComments(id);
    })
}
function setStatusAchieved(id) {
    fetch(path + "/api/goals/" + id, {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        displayError(response, 204);
    }).then(function () {
        onLoad();
    })
}