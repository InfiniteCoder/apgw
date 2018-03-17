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
                var sid = getUrlParameter("sid");
                //console.log(sid);
                for (var i = 0; i < data.length; i++) {
                    var subuid;
                    for (var j = 0; j < data[i].student.subjects.length; j++) {
                        var temp = data[i].student.subjects[j].subjectId;
                        //console.log(temp);

                        if (temp == sid) {
                            subuid = data[i].student.subjects[j].uid;
                        }
                        //subuid = data[i].student.subjects[j].uid;
                        //console.log(subuid);
                    }
                    result += "<li class=\"list-group-item\"><span>" + subuid + "</span> - " + data[i].student.name + "</li>";
                    //console.log(data[i].marks);
                    //console.log(data[i].student.subjects[0].uid);
                    //var sub = data[i].student;

                }
                var listElement = $("#subList");
                listElement.empty();
                listElement.append(result);
                //document.getElementById("assignList").style.cursor = "pointer";

            },
            error: function (jqXHR) {
                //console.log(jqXHR);
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
            success: function (data) {
                console.log("graded success");
            },
            error: function (jqXHR) {
                console.log("error");
            }
        }
    );

}

window.onload = function () {
    hideLogin();
    //$("#subName").text(getUrlParameter("name"));
    var assignName = Cookies.get('assignName');
    $("#assignName").text(assignName);
    //var aid = getUrlParameter("id");
    //console.log(aid);

    displaySubFunc();

    var gradingBtn = document.getElementById("gradeBtn");
    gradingBtn.addEventListener("click", gradeAssign);
};