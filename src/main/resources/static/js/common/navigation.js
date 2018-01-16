function hideLogin() {
    //check if user is authenticated
    var cmnRequest = new XMLHttpRequest();
    cmnRequest.open("GET", "all/isUserAuth");
    cmnRequest.onload = function () {
        var data = cmnRequest.responseText;
        if (data === "true") {
            document.getElementById("login_btn").style.visibility = "hidden";
            //console.log("Hidden success");

        }
    };
    cmnRequest.send();
}