function hideLogin() {
    //check if user is authenticated
    var cmnRequest = new XMLHttpRequest();
    cmnRequest.open("GET", "all/isUserAuth");
    cmnRequest.onload = function () {
        var data = cmnRequest.responseText;
        if (data === "true") {
            document.getElementById('loginBtn').style.visibility = "hidden";

        }
    };
    cmnRequest.send();
}

function showSubjects() {
    $.getJSON("/student/subjects", function (data) {
        var sublist = "";
        for (var i = 0; i < data.length; i++) {
            sublist += "<li class=\"list-group-item\"><span>" + data[i].subject.name + "</span></li>";
        }
        var listElement = $("#subList");
        listElement.empty();
        listElement.append(sublist);
    });
}

window.onload = function () {
    hideLogin();


    var btn = document.getElementById("subjectList");
    btn.addEventListener("click", function () {
        showSubjects();
    });


};