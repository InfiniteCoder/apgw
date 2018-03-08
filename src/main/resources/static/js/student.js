function showSubjects() {
    $.getJSON("/student/subjects", function (data) {
        var sublist = "";
        var subid;
        for (var i = 0; i < data.length; i++) {
            subid = data[i].id;
            sublist += "<li class=\"list-group-item\" onclick=\"showAssign(this)\" data-sub-id=" + subid + ">" + data[i].name + "</li>";
            console.log(data[i].name);
            console.log(subid);
            //id[i] = data[i].id;
        }

        var listElement = $("#subList");
        listElement.empty();
        listElement.append(sublist);
        //console.log(id.length);

        /*for(var j=0;j<id.length;j++)
        {
            var subId = id[j];
            console.log(subId);
            //$('#subList li').attr('data-subId', function(subId) {
              //  return 'subList'+(subId);
            //});
            // after elements have been created
            $('#sublist li').each(function (subId) {
                console.log(subId);
                $(this).attr('data-subId',+subId);
            });
        }*/


        document.getElementById("subList").style.cursor = "pointer";
    });
}

function showAssign(e) {
    //var abc = $('.list-group-item:first');
    var subId = e.getAttribute("data-sub-id");
    console.log(subId);
    //console.log(subName);
    window.location = "/Student/Assignment.html" + "?id=" + subId;
}

window.onload = function () {
    hideLogin();

    var btn = document.getElementById("subjectList");
    btn.addEventListener("click", function () {
        showSubjects();
    });

    //var subListBtn = document.getElementById("subList");
    //subListBtn.addEventListener("click", showAssign);

};