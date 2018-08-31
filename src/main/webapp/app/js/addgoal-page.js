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
function loadTegs() {
    fetch(path + "/api/goal/tegs", {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    }).then(function (response) {
        return response.json();
    }).then(function (tegs) {
        if (tegs.length > 0) {
            tabledata = {"tegs": tegs};
            w3DisplayData("field1", tabledata);
            w3DisplayData("field2", tabledata);
            w3DisplayData("field3", tabledata);
        }
    });
}