var path = "";

function submitData() {
    var goalTxt = document.getElementById("goal-txt");
    var deadlineTxt = document.getElementById("datepicker");
    if (String(goalTxt.value) === "" || String(deadlineTxt.value) === "") {
        alert("Not all fields filled out!");
        return false;
    }
    var dto = {
        "goalMessage": goalTxt.value,
        "deadline": deadlineTxt.value,
    };
    fetch(path + "/api/goal/newgoal", {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    }).then(function (response) {
        location.href = path + "/app/start.jsp";
    });
}