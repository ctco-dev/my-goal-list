var path = "";
var id = getQueryVariable("id");
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
    fetch(path + "/api/goals/" + id, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        if (response.status === 200) {
            return response.json();
        } else {
            alert("Something went wrong! error:404");
            return false;
        }
    }).then(function (goal) {
        w3.displayObject("title", goal);
        w3.displayObject("goal-fields", goal);
        w3DisplayData("tags-list", goal);
        getComments();
    });
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
            if (response.status === 204) {
                document.getElementById("userComment").value = "";
                onLoad();
            } else {
                alert("Something went wrong! error:404");
                return false;
            }
        });
    }
}