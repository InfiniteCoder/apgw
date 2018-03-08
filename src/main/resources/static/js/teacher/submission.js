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


function displaySubFunc() {
    $.ajax(
        {
            url: "/api/submissions",
            type: "GET",
            data: {id: getUrlParameter("id")},
            //contentType: "application/json;charset=utf-8",
            //dataType: "json",
            success: function (data) {
                var result = "";
                //var assignid;
                for (var i = 0; i < data.length; i++) {
                    //assignid = data[i].id;
                    result += "<li class=\"list-group-item\" data-sub-id=" + data[i].id + ">" + data[i].studentName + "</li>";
                    console.log(data[i].id);
                    console.log(data[i].studentName);
                }
                var listElement = $("#subList");
                listElement.empty();
                listElement.append(result);
                //document.getElementById("assignList").style.cursor = "pointer";

            },
            error: function (jqXHR) {
                console.log(jqXHR);
            }

        }
    );
}

window.onload = function () {
    hideLogin();
    //$("#subName").text(getUrlParameter("name"));
    var assignName = Cookies.get('assignName');
    $("#assignName").text(assignName);
    var aid = getUrlParameter("id");
    console.log(aid);

    displaySubFunc();
};