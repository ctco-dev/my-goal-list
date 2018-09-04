function switchSearch() {
    var userRadioButton = document.getElementById("rbtnUser");
    var goalRadioButton = document.getElementById("rbtnGoal");
    var blockUser = document.getElementById("search-by-Username");
    var blockGoal = document.getElementById("search-by-Tags");
    if (userRadioButton.checked) {
        blockUser.classList.remove("w3-hide");
        blockGoal.classList.add("w3-hide");
        document.getElementById("Goals-List").classList.add("w3-hide");
    } else if (goalRadioButton.checked) {
        blockUser.classList.add("w3-hide");
        blockGoal.classList.remove("w3-hide");
        document.getElementById("Users-List").classList.add("w3-hide");
    } else {
        blockUser.classList.add("w3-hide");
        blockGoal.classList.add("w3-hide");
        document.getElementById("Users-List").classList.add("w3-hide");
        document.getElementById("Goals-List").classList.add("w3-hide");
    }
}
function findUserByName() {
    var username = document.getElementById("username");
    var dto = {
        "usersearch": username.value
    };
    console.log(JSON.stringify(dto));
    fetch("/api/goal/search-user", {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    }).then(function (response) {
        return response.json();
    }).then(function (users) {
        console.log(users);
        if (users.length > 0) {
            var tabledata = {'users': users};
            document.getElementById("Users-List").classList.remove("w3-hide");
            w3DisplayData("Users-List", tabledata);
        } else {
            document.getElementById("Users-List").classList.add("w3-hide");
        }
    });
}
function findGoalsByTag() {
    var tag = document.getElementById("tag").value;
    fetch("/api/goal/tag/" + tag, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (similargoals) {
        console.log(similargoals);
        if (similargoals.length > 0) {
            var tabledata = {'goals': similargoals};
            document.getElementById("Goals-List").classList.remove("w3-hide");
            w3DisplayData("Goals-List", tabledata);
        } else {
            alert("There are no goals with such tag");
        }
    });
}