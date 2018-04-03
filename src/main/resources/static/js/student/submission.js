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

};