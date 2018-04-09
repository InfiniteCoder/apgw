function displaySubFunc() {
    $.ajax(
        {
            url: "/api/submissions",
            type: "GET",
            data: {id: getUrlParameter("id")},
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
    var assignName = Cookies.get('assignName');
    $("#assignName").text(assignName);
    displaySubFunc();

    var gradingBtn = document.getElementById("gradeBtn");
    gradingBtn.addEventListener("click", gradeAssign);
};