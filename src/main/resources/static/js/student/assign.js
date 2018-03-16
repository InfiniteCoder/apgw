var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split("&"),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split("=");

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function displayAssignFunc() {
    $.ajax(
        {
            url: "/api/assignmentsById",
            type: "GET",
            data: {subjectId: getUrlParameter("id")},
            //contentType: "application/json;charset=utf-8",
            //dataType: "json",
            success: function (data) {
                var result = "";
                for (var i = 0; i < data.length; i++) {
                    result += "<li class=\"list-group-item\" onclick=\"addSubmission(this)\" data-assign-id=" + data[i].id + ">" + data[i].title + "</li>";
                    console.log(data[i].id);
                }
                var listElement = $("#assignList");
                listElement.empty();
                listElement.append(result);
                document.getElementById("assignList").style.cursor = "pointer";

            },
            error: function (jqXHR) {
                console.log(jqXHR);
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

    var displayAssignBtn = document.getElementById("displayAssign");
    displayAssignBtn.addEventListener("click", displayAssignFunc);

};