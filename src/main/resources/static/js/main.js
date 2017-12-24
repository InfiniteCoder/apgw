function checkUserType() {
    var userTypeRequest = new XMLHttpRequest();
    userTypeRequest.open("GET", "/userType");
    userTypeRequest.onload = function () {
        //get the type
        var type = userTypeRequest.responseText;
        if (type === "student") {
            window.location = "student/homepage.html";
        }
        else if (type === "teacher") {
            window.location = "teacher/homepage.html";
        }
        else {
            //show a popup to select student or teacher
            $("#userModal").modal("show");
        }
    };
    userTypeRequest.send();
}

//check auth and type
function checkAuth() {
    //check if user is authenticated
    var request = new XMLHttpRequest();
    request.open("GET", "all/isUserAuth");
    request.onload = function () {
        var data = request.responseText;
        if (data === "true") {
            checkUserType();
        }
    };

    request.send();
}

//on load, check if authenticated
window.onload = function () {
    checkAuth();

    //get type for new user, called when modal is shown
    $("#userModal").on("hide.bs.modal", function () {
        var type = $("input[type=radio]:checked").val();
        if (type === "teacher") {
            //call /createTeacher API
            $.post("/createTeacher", function () {
                window.location = "teacher/homepage.html";
            });
        }
        else {
            //call /createStudent API
            $.post("/createStudent", function () {
                window.location = "student/homepage.html";
            });
        }
    });
};