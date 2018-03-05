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
                var responseMsg = jqXHR.status;
                console.log(responseMsg);
                if (responseMsg === 201) {
                    $("#assignModalMsg").text("Files Uploaded Successfully");
                    $("#assignModal").modal("show");

                }
                else if (responseMsg === 204) {
                    $("#assignModalMsg").text("Error, Something's missing");
                    $("#assignModal").modal("show");

                }
                else if (responseMsg === 304) {
                    $("#assignModalMsg").text("Error");
                    $("#assignModal").modal("show");
                }

                // console.log("success!");
                // console.log(data);
            },
            error: function (jqXHR) {
                console.log(jqXHR.status);
                $("#assignModalMsg").text("Error");
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
            console.log(jqXHR.status);
            $("#studModalMessage").text("Error");
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
}

function addTestCases() {
    console.log("add test cases");
    var result = "<li class=\"list-group-item\" style=\"height:45px;\">" +
        "<div class=\"col-md-12\" style=\"width:50%;height:auto;\"><input type=\"file\" style=\"color:rgb(249,244,244);height:28px;\" id=\"assignInputFile\"></div>" +
        "<div class=\"col-md-12\" style=\"width:50%;height:auto;\"><input type=\"file\" style=\"color:rgb(249,244,244);height:28px;\" id=\"assignOutputFile\"></div>" +
        "</li>";
    var elementId = $("#addTestCase");
    elementId.append(result);
}

function displayAssignFunc() {
    $.ajax(
        {
            url: "/api/assignments",
            type: "GET",
            data: {subjectName: getUrlParameter("name")},
            //contentType: "application/json;charset=utf-8",
            //dataType: "json",
            success: function (data) {
                var result = "";
                for (var i = 0; i < data.length; i++) {
                    result += "<li class=\"list-group-item\"><span>" + data[i].title + "</span></li>";
                    console.log(data[i].title);
                }
                var listElement = $("#assignList");
                listElement.empty();
                listElement.append(result);

            },
            error: function (jqXHR) {
                console.log(jqXHR);
            }

        }
    );
}
window.onload = function () {
    hideLogin();
    document.getElementById("addFiles").style.cursor = "pointer";
    $("#subName").text(getUrlParameter("name"));

    var studentUploadBtn = document.getElementById("studentUploadBtn");
    studentUploadBtn.addEventListener("click", studentUpload);

    var displayStudentsBtn = document.getElementById("displayStudents");
    displayStudentsBtn.addEventListener("click", displayStudent);

    var assignmentUploadBtn = document.getElementById("assignUploadBtn");
    assignmentUploadBtn.addEventListener("click", assignUpload);

    var addTestCasesBtn = document.getElementById("addFiles");
    addTestCasesBtn.addEventListener("click", addTestCases);

    var displayAssignBtn = document.getElementById("displayAssign");
    displayAssignBtn.addEventListener("click", displayAssignFunc)
};
