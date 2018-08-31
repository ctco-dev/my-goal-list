var path = "";

function submitData() {
    var goalTxt = document.getElementById("goal-txt");
    var deadlineTxt = document.getElementById("datepicker");
    if (String(goalTxt.value) === "" || String(deadlineTxt.value) === "") {
        alert("Not all fields filled out!");
        return false;
    }
    var t1 = document.getElementById("field1").value;
    var t2 = document.getElementById("field2").value;
    var t3 = document.getElementById("field3").value;
    var tags = t1 + "|" + t2 + "|" + "|" + t3;
    var dto = {
        "goalMessage": goalTxt.value,
        "deadline": deadlineTxt.value,
        "tags": tags
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
function loadTags() {
    fetch(path + "/api/goal/tags", {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (tags) {
        if (tags.length > 0) {
            var tableData = {"tags": tags};
        } else {
            var tableData = {"tags": [{"tagMessage": ""}]};
        }
        w3DisplayData("field1", tableData);
        w3DisplayData("field2", tableData);
        w3DisplayData("field3", tableData);
    });
}