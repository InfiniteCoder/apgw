function showSubjects() {
    $.getJSON("/student/subjects", function (data) {
        var sublist = "";
        for (var i = 0; i < data.length; i++) {
            sublist += "<li class=\"list-group-item\"><span>" + data[i].name + "</span></li>";
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