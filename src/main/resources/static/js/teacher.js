function hideLogin() {
    //check if user is authenticated
    var cmn_request = new XMLHttpRequest();
    cmn_request.open("GET", "all/isUserAuth");
    cmn_request.onload = function () {
        var data = cmn_request.responseText;
        if (data === "true") {
            document.getElementById('login_btn').style.visibility = 'hidden';

        }
    };
    cmn_request.send();
}

function addSubject() {
    //console.log("In Add");
    var name = document.getElementById("subnametxt").value;
    $.post("/addSubject", {name: name}, function () {
        $('#successModal').modal('show');
        document.getElementById("subnametxt").value = "";
    });

}

function showSubjects() {
    $.getJSON("/teacher/subjects", function (data) {
        var sublist = "";
        for (var i = 0; i < data.length; i++) {
            sublist += "<li class=\"list-group-item\"><span>" + data[i].name + "</span></li>";
        }
        var listElement = $("#sub_list");
        listElement.empty();
        listElement.append(sublist);
    });
}

function teachersub(e) {
    var subName = e.target.textContent;
    console.log(subName);
    window.location = "/Teacher/Subject.html" + "?name=" + subName;
}

window.onload = function () {
    hideLogin();

    var btn = document.getElementById("addsubbtn");
    btn.addEventListener("click", function () {
        addSubject();
    });

    var btn1 = document.getElementById("tab_sub");
    btn1.addEventListener("click", function () {
        showSubjects();
    });

    var subList = document.getElementById("sub_list");
    subList.addEventListener("click", teachersub);


};