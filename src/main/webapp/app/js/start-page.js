var path = "";
var goalList;
function showUserProfile() {
    fetch(path + "/api/auth/myprofile", {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (user) {
        document.getElementById("name").innerHTML = user.username;
        document.getElementById("phone_email").innerHTML = "Phone: " + user.phone + " | E-mail: " + user.email;
    });
}
function showUserGoals() {
    fetch(path + "/api/goals", {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (goals) {
        goalList = goals;
        var tabledata;
        if (goals.length > 0) {
            tabledata = {"goals": goals};
        } else {
            tabledata = {
                "goals": [{
                    "id": "",
                    "daysLeft": "",
                    "deadlineDate": "",
                    "goalMessage": "You need to create new Goals"
                }]
            };
        }
        w3DisplayData("goals-list", tabledata);
        generateMetrics(goals);
    });
}

function generateMetrics(goals) {
    var completed = 0;
    var overdue = 0;
    var open = 0;
    for (var i = 0; i < goals.length; i++) {
        var goal = goals[i];
        switch (goal.goalStatus) {
            case "ACHIEVED":
                completed++;
                break;
            case "OVERDUE":
                overdue++;
                break;
            default:
                open++;
        }
    }
    var obj = "<table><tr>Metrics</tr>";
    obj += "<td>" + "Goals Total: " + goals.length + "</td>";
    obj += "<td>" + "Goals Achieved: " + completed + "</td>";
    obj += "<td>" + "Goals Overdue: " + overdue + "</td>";
    obj += "<td>" + "Goals Open: " + open + "</td>";
    obj += "</table>";

    document.getElementById("metrics").innerHTML = obj;
}
