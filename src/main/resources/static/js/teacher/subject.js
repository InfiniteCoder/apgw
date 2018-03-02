//function to get url parameter
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

function assignUpload() {
    console.log("Assignment Upload Button clicked");
    var subName = getUrlParameter("name");
    console.log(subName);
    var assignmentTitle = document.getElementById("assignTitle").value;
    console.log(assignmentTitle);
    var inputFile = document.getElementById("assignInputFile").files[0];
    var outputFile = document.getElementById("assignOutputFile").files[0];
    var quesFile = document.getElementById("assignQuesFile").files[0];

    var formData = new FormData();
    formData.append("subjectName", subName);
    formData.append("title", assignmentTitle);
    formData.append("inputFile", inputFile);
    formData.append("outputFile", outputFile);
    formData.append("questionFile", quesFile);

    $.ajax(
        {
            url: "/api/addAssignment",
            type: "POST",
            data: formData,
            processData: false,  // tell jQuery not to process the data
            contentType: false,  // tell jQuery not to set contentType
            success: function (data, textStatus, jqXHR) {
                var responseMsg = jqXHR.statusText;
                if (responseMsg === "created") {
                    $("#assignModalMsg").text("Files Uploaded Successfully");
                    $("#assignModal").modal("show");

                }
                else if (responseMsg === "nocontent") {
                    $("#assignModalMsg").text("Empty File");
                    $("#assignModal").modal("show");

                }
                else {
                    $("#assignModalMsg").text("Not Modified");
                    $("#assignModal").modal("show");
                }

                // console.log("success!");
                // console.log(data);
            },
            error: function (jqXHR) {
                console.log(jqXHR);
                $("#assignModalMsg").text("Error, Select a file");
                $("#assignModal").modal("show");
            }
        }
    );
}

function studentUpload() {
    console.log("student upload btn clicked");
    var file = document.getElementById("fileInput").files[0];

    //create formdata object
    var formData = new FormData();
    formData.append("subject", getUrlParameter("name"));
    formData.append("file", file);

    //make post call
    $.ajax({
        url: "/addStudents",
        type: "POST",
        data: formData,
        processData: false,  // tell jQuery not to process the data
        contentType: false,  // tell jQuery not to set contentType
        success: function (data, textStatus, jqXHR) {
            var responseMsg = jqXHR.statusText;
            if (responseMsg === "created") {
                $("#studModalMessage").text("List Uploaded Successfully");
                $("#studUploadModal").modal("show");

            }
            else if (responseMsg === "nocontent") {
                $("#studModalMessage").text("Empty File");
                $("#studUploadModal").modal("show");

            }
            else {
                $("#studModalMessage").text("Not Modified");
                $("#studUploadModal").modal("show");
            }

            // console.log("success!");
            // console.log(data);
        },
        error: function (jqXHR) {
            console.log(jqXHR);
            $("#studModalMessage").text("Error, Select a file");
            $("#studUploadModal").modal("show");
        }
    });

}

function displayStudent() {
    //var formdata = new FormData();
    //formdata.append("subjectName",getUrlParameter("name"));
    $.ajax(
        {
            url: "/api/getStudents",
            type: "GET",
            data: {subjectName: getUrlParameter("name")},
            //contentType: "application/json;charset=utf-8",
            //dataType: "json",
            success: function (data) {
                var result = "";
                for (var i = 0; i < data.length; i++) {
                    result += "<li class=\"list-group-item\"><span>" + data[i].studentEmail + "</span></li>";
                    console.log(data[i].studentEmail);
                }
                var listElement = $("#studentList");
                listElement.empty();
                listElement.append(result);

            },
            error: function (jqXHR) {
                console.log(jqXHR);
            }

        }
    );
    /*$.getJSON("/api/getStudents", function (data) {
        var studlist = "";
        console.log("Display Students");
        for (var i = 0; i < data.length; i++) {
            studlist += "<li class=\"list-group-item\"><span>" + data[i].name + "</span></li>";
        }
        var listElement = $("#studentList");
        listElement.empty();
        listElement.append(studlist);
    });*/
}


window.onload = function () {
    hideLogin();
    $("#subName").text(getUrlParameter("name"));

    var studentUploadBtn = document.getElementById("studentUploadBtn");
    studentUploadBtn.addEventListener("click", studentUpload);

    var displayStudentsBtn = document.getElementById("displayStudents");
    displayStudentsBtn.addEventListener("click", displayStudent);

    var assignmentUploadBtn = document.getElementById("assignUploadBtn");
    assignmentUploadBtn.addEventListener("click", assignUpload);
};