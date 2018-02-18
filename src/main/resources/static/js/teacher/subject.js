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
        success: function (data) {
            // console.log("success!");
            // console.log(data);
        },
        error: function (jqXHR) {
            console.log(jqXHR);
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
    var studentUploadBtn = document.getElementById("studentUploadBtn");
    studentUploadBtn.addEventListener("click", studentUpload);

    var displayStudentsBtn = document.getElementById("displayStudents");
    displayStudentsBtn.addEventListener("click", displayStudent);
};