function hideLogin() {
    //check if user is authenticated
    var cmn_request = new XMLHttpRequest();
    cmn_request.open("GET", "all/isUserAuth");
    cmn_request.onload = function () {
        var data = cmn_request.responseText;
        if (data === "true") {
            document.getElementById('login_btn').style.visibility = 'hidden';

        }
    };
    cmn_request.send();
}

function addSubject() {
    //console.log("In Add");
    var name = document.getElementById("subnametxt").value;
    console.log(name);
    $.post("/addSubject", {name: name}, function (data) {
        console.log("added");
        $('#successModal').modal('show');
        document.getElementById("subnametxt").value = "";
    });

}

window.onload = function () {
    hideLogin();


    var btn = document.getElementById("addsubbtn");
    btn.addEventListener("click", function () {
        addSubject();
        //console.log("Added sub");
    });



};