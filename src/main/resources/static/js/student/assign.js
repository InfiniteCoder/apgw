function displayAssignFunc() {
    $.ajax(
        {
            url: "/api/assignmentsById",
            type: "GET",
            data: {subjectId: getUrlParameter("id")},
            success: function (data) {
                var result = "";
                for (var i = 0; i < data.length; i++) {
                    result += "<li class=\"list-group-item\" onclick=\"addSubmission(this)\" data-assign-id=" + data[i].id + ">" + data[i].title + "</li>";
                }
                var listElement = $("#assignList");
                listElement.empty();
                listElement.append(result);
                document.getElementById("assignList").style.cursor = "pointer";

            },
            error: function (jqXHR) {
            }

        }
    );
}

function addSubmission(e) {
    var assignId = e.getAttribute("data-assign-id");
    var assignName = e.innerHTML;

    window.location = "Submission.html" + "?id=" + assignId + "&name=" + assignName;

}

window.onload = function () {
    $("#subName").text(getUrlParameter("name"));

    displayAssignFunc();

};