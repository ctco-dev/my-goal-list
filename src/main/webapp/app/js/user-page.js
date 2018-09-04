var userid = getQueryVariable("id");
function loadUser() {
    var path = "/api/goal/user";
    if (userid) {
        path = path + "/" + userid;
    }
    fetch(path, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (userDto) {
        if (userDto.username !== "undefined") {
            setDataToFields(userDto);
        } else {
            location.href = "/app/404.jsp";
        }
    });
}

function setDataToFields(userDto) {
    var list = userDto.goalDtoList;
    var tabledata;
    console.log(userDto);
    w3.displayObject("title", userDto);
    w3.displayObject("username", userDto);
    w3.displayObject("email-and-phone", userDto);
    if (list.length > 0) {
        tabledata = {"goals": list};
        console.log(tabledata);
        w3DisplayData("goals-list", tabledata);
    } else if (list.length === 0 && !userid) {
        tabledata = {
            "goals": [{
                "id": "-1",
                "daysLeft": "",
                "deadlineDate": "",
                "goalMessage": "You need to create new Goals"
            }]
        };
        w3DisplayData("goals-list", tabledata);
    } else {
        document.getElementById("hidden").classList.remove("w3-hide");
        document.getElementById("goals-list").classList.add("w3-hide");
    }
}

function redirectToGoalsAndComments(id) {
    if (id >= 0) {
        location.href = "<c:url value='/app/goal.jsp?id='/>" + id;
    } else {
        addNewGoal()
    }
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