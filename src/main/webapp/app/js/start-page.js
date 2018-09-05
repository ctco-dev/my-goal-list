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
        document.getElementById("phone_email").innerHTML = "Phone: " + user.phone + " |  E-mail: " + user.email;
    });
}

function showUserGoals() {
    fetch(path + "/api/goal/mygoals", {
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
                    "id": "-1",
                    "daysLeft": "",
                    "deadlineDate": "",
                    "goalMessage": "You need to create new Goals"
                }]
            };
        }
        w3DisplayData("goals-list", tabledata);
    });
}