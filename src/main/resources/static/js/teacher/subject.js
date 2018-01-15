//function to get url parameter
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

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
    formData.append('subject', getUrlParameter(name));
    formData.append('file', file);

    //make post call
    $.ajax({
        url: '/addStudents',
        type: 'POST',
        data: formData,
        processData: false,  // tell jQuery not to process the data
        contentType: false,  // tell jQuery not to set contentType
        success: function (data) {
            // console.log("success!");
            // console.log(data);
            alert(data);
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });

}

window.onload = function () {
    var studentUploadBtn = document.getElementById("studentUploadBtn");
    studentUploadBtn.addEventListener("click", studentUpload);
};