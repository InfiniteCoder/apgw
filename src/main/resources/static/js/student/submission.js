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

function subUploadFunc() {
    var file = document.getElementById("subFile").files[0];

    //create formdata object
    var formData = new FormData();
    formData.append("id", getUrlParameter("id"));
    formData.append("file", file);

    //make post call
    $.ajax({
        url: "/api/addSubmission",
        type: "POST",
        data: formData,
        processData: false,  // tell jQuery not to process the data
        contentType: false,  // tell jQuery not to set contentType
        success: function (data, textStatus, jqXHR) {
            var responseMsg = jqXHR.status;
            var msg = textStatus;
            console.log(msg);
            console.log(responseMsg);
            if (responseMsg === 201) {
                $("#modalMessage").text("Assignment Submitted");
                $("#successModal").modal("show");

            }
            else if (responseMsg === 304) {
                $("#modalMessage").text("Error...");
                $("#successModal").modal("show");

            }
            else {
                $("#modalMessage").text("Error.");
                $("#successModal").modal("show");
            }
        },
        error: function (jqXHR) {
            //console.log(jqXHR.status);
            $("#modalMessage").text("Error");
            $("#successModal").modal("show");
        }
    });

}

function displayQuestion() {
    /*var file = new XMLHttpRequest();
    file.open("GET", "/api/questionFile", true);
    file.onreadystatechange = function() {
        if (file.readyState === 4) {  // Makes sure the document is ready to parse
            if (file.status === 200) {  // Makes sure it's found the file
                text = file.responseText;


                document.getElementById("questionFile").innerHTML = text;
            }
        }
    }*/
    $(document).ready(function () {
        $.ajax({
            url: "/api/questionFile",
            type: "GET",
            data: {id: getUrlParameter("id")},
            contentType: "text/plain; charset=utf-8",
            dataType: "text",
            success: function (data) {
                $("#questionFile").html(data);
            }
        });
    });
}

window.onload = function ()
{
    $("#assignTitle").text(getUrlParameter("name"));

    displayQuestion();

    var submissionUploadBtn = document.getElementById("subUploadBtn");
    submissionUploadBtn.addEventListener("click", subUploadFunc);

    // displayQuestion();

};