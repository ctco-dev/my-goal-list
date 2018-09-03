var path = "";

function submitData() {
    var goalTxt = document.getElementById("goal-txt");
    var deadlineTxt = document.getElementById("datepicker");
    if (String(goalTxt.value) === "" || String(deadlineTxt.value) === "") {
        alert("Not all fields filled out!");
        return false;
    }
    var t1 = document.getElementById("tags-field-1").value;
    var t2 = document.getElementById("tags-field-2").value;
    var t3 = document.getElementById("tags-field-3").value;
    var tags = t1 + "|" + t2 + "|" + t3;
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
        addOptions(tags, "tags");
    });

    function addOptions(tags, name) {
        var i;
        var obj = "";
        for (i = 0; i < tags.length; i++) {
            obj += "<option>" + tags[i].tagMessage + "</option>";

        }
        document.getElementById(name).innerHTML = obj;
    }
}