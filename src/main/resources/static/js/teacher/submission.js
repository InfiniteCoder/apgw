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


function displaySubFunc() {
    $.ajax(
        {
            url: "/api/submissions",
            type: "GET",
            data: {id: getUrlParameter("id")},
            //contentType: "application/json;charset=utf-8",
            //dataType: "json",
            success: function (data) {
                var result = "";
                var cnt = 0;
                var sid = getUrlParameter("sid");
                for (var i = 0; i < data.length; i++) {
                    var subuid;
                    for (var j = 0; j < data[i].student.subjects.length; j++) {
                        var temp = data[i].student.subjects[j].subjectId;

                        if (temp == sid) {
                            subuid = data[i].student.subjects[j].uid;
                            cnt++;
                        }
                    }
                    //result += "<li class=\"list-group-item\"><span>" + subuid + "</span> - " + data[i].student.name + "</li>";
                    result += "<tr>" +
                        "<td>" + subuid + "</td>" +
                        "<td>" + data[i].student.name + "</td>" +
                        "<td>" + data[i].marks + "</td>" +
                        "</tr>";
                }
                var listElement = $("#submissionTable");
                listElement.empty();
                listElement.append(result);
                $("#totalSubmissions").text(cnt);


            },
            error: function (jqXHR) {
            }

        }
    );
}

function gradeAssign() {

    console.log("Grading");
    $.ajax(
        {
            url: "/api/grade",
            type: "POST",
            data: {id: getUrlParameter("id")},
            success: function (data, textStatus, jqXHR) {
                var responseMsg = jqXHR.status;
                if (responseMsg === 201) {
                    $("#modalMessage").text("Assignments Graded Successfully");
                    $("#successModal").modal("show");

                }
                displaySubFunc();
            },
            error: function (jqXHR) {
                $("#modalMessage").text("Error");
                $("#successModal").modal("show");

            }
        }
    );
}

window.onload = function () {
    hideLogin();
    var assignName = Cookies.get('assignName');
    $("#assignName").text(assignName);
    displaySubFunc();

    var gradingBtn = document.getElementById("gradeBtn");
    gradingBtn.addEventListener("click", gradeAssign);
};