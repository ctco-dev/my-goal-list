var userid = getQueryVariable("id");
var path;
function loadUser() {
    var userPath = "/api/goals/user";
    if (userid) {
        userPath = path + userPath + "/" + userid;
        fetch(userPath, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            displayError(response, 200);
            return response.json();
        }).then(function (userDto) {
            if (userDto.username !== "undefined") {
                setDataToFields(userDto);
            } else {
                location.href = "/app/404.jsp";
            }
        });
    } else {
        location.href = path + "/app/start.jsp";
    }
}
function setDataToFields(userDto) {
    var list = userDto.goalList;
    var tabledata;
    w3.displayObject("title", userDto);
    w3.displayObject("username", userDto);
    w3.displayObject("email-and-phone", userDto);
    if (list.length > 0) {
        tabledata = {"goals": list};
        w3DisplayData("goals-list", tabledata);
        generateMetrics(list);
    } else {
        document.getElementById("hidden").classList.remove("w3-hide");
        document.getElementById("goals-list").classList.add("w3-hide");
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