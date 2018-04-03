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