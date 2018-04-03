function showSubjects() {
    $.getJSON("/student/subjects", function (data) {
        var sublist = "";
        var subid;
        for (var i = 0; i < data.length; i++) {
            subid = data[i].id;
            sublist += "<li class=\"list-group-item\" onclick=\"showAssign(this)\" data-sub-id=" + subid + ">" + data[i].details.name + "</li>";
        }

        var listElement = $("#subList");
        listElement.empty();
        listElement.append(sublist);
        document.getElementById("subList").style.cursor = "pointer";
    });
}

function showAssign(e) {
    var subId = e.getAttribute("data-sub-id");
    var subName = e.innerHTML;
    window.location = "/Student/Assignment.html" + "?id=" + subId + "&name=" + subName;
}

window.onload = function () {

    showSubjects();
};