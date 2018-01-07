function hideLogin() {
    //check if user is authenticated
    var cmn_request = new XMLHttpRequest();
    cmn_request.open("GET", "all/isUserAuth");
    cmn_request.onload = function () {
        var data = cmn_request.responseText;
        if (data === "true") {
            document.getElementById('login_btn').style.visibility = 'hidden';
            console.log("Hidden success");

        }
    };
    cmn_request.send();
}

window.onload = function () {
    hideLogin();

};