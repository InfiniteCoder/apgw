function addSubject() {
    getdept();

    var yearList = document.getElementById("selectDept");
    yearList.addEventListener("click", getyear);

    var nameList = document.getElementById("selectYear");
    nameList.addEventListener("click", getname);

    var addSub1 = document.getElementById("addSubjectBtn");
    addSub1.addEventListener("click", addSub);

}

function getdept() {

    $.getJSON("/api/dept", function (data) {
        var deptlistarray = [];
        for (var k = 0; k < data.length; k++) {
            deptlistarray.push(data[k].dept);
        }

        Array.prototype.unique = function () {
            var arr = [];
            for (var j = 0; j < this.length; j++) {
                if (!arr.includes(this[j])) {
                    arr.push(this[j]);
                }
            }
            return arr;
        };

        var uniquedept = deptlistarray.unique();
        var deptlist = "<option>Select</option>";
        for (var i = 0; i < uniquedept.length; i++) {
            var a = i + 1;
            deptlist += "<option value=" + a + ">" + uniquedept[i] + "</option>";

        }
        var listElement = $("#selectDept");
        listElement.empty();
        listElement.append(deptlist);

    });

}

function getyear() {

    var e1 = document.getElementById("selectDept");
    var str1 = e1.options[e1.selectedIndex].text;
    console.log(str1);
    $.ajax(
        {
            url: "/api/year",
            type: "GET",
            data: {dept: str1},
            success: function (data) {
                var yearlistarray = [];
                for (var k = 0; k < data.length; k++) {
                    yearlistarray.push(data[k].year);
                }

                Array.prototype.unique = function () {
                    var arr = [];
                    for (var j = 0; j < this.length; j++) {
                        if (!arr.includes(this[j])) {
                            arr.push(this[j]);
                        }
                    }
                    return arr;
                };

                var uniqueyear = yearlistarray.unique();
                var yearlist = "<option>Select</option>";
                for (var i = 0; i < uniqueyear.length; i++) {
                    var a = i + 1;
                    yearlist += "<option value=" + a + ">" + uniqueyear[i] + "</option>";

                }
                var listElement = $("#selectYear");
                listElement.empty();
                listElement.append(yearlist);


            },
            error: function (jqXHR) {
            }

        }
    );
}

function getname() {
    var e1 = document.getElementById("selectDept");
    var str1 = e1.options[e1.selectedIndex].text;
    var e2 = document.getElementById("selectYear");
    var str2 = e2.options[e2.selectedIndex].text;
    $.ajax(
        {
            url: "/api/name",
            type: "GET",
            data: {dept: str1, year: str2},
            success: function (data) {
                var namelist = "<option>Select</option>";
                for (var i = 0; i < data.length; i++) {
                    namelist += "<option value=" + data[i].id + ">" + data[i].name + "</option>";

                }
                var listElement = $("#selectName");
                listElement.empty();
                listElement.append(namelist);


            },
            error: function (jqXHR) {
            }

        }
    );
}

function addSub() {

    var e3 = document.getElementById("selectName");
    var str3 = e3.options[e3.selectedIndex].value;
    console.log(str3);

    $.ajax(
        {
            url: "/addSubject",
            type: "POST",
            data: {id: str3},
            success: function (data, textStatus, jqXHR) {
                var responseMsg = jqXHR.status;
                if (responseMsg === 201) {
                    $("#modalMessage").text("Subject added");
                    $("#successModal").modal("show");

                }
                else if (responseMsg === 304) {
                    $("#modalMessage").text("Subject Already exists");
                    $("#successModal").modal("show");

                }
                else if (responseMsg === 204) {
                    $("#modalMessage").text("Error");
                    $("#successModal").modal("show");
                }
            },
            error: function (jqXHR) {
                $("#modalMessage").text("Error");
                $("#successModal").modal("show");

            }

        }
    );
}

function showSubjects() {
    $.getJSON("/teacher/subjects", function (data) {
        var sublist = "";
        for (var i = 0; i < data.length; i++) {
            sublist += "<tr>" +
                "<td onclick=\"nextPage(this)\" data-subj-id=" + data[i].id + ">" + data[i].details.name + "</td>" +
                "<td>" + data[i].details.year + "</td>" +
                "<td>" + data[i].details.dept + "</td>" +
                "<td><i class=\"material-icons\" onclick=\" deleteSubject(this)\" data-sub-id=" + data[i].id + ">delete</i></td>" +
                "</tr>";
        }
        var listElement = $("#subTable");
        listElement.empty();
        listElement.append(sublist);
        document.getElementById("subTable").style.cursor = "pointer";
    });
}

function nextPage(e) {
    var subName = e.innerHTML;

    var subId = e.getAttribute("data-subj-id");
    window.location = "Teacher/Subject.html" + "?id=" + subId + "&name=" + subName;
}

function deleteSubject(e) {
    var subId = e.getAttribute("data-sub-id");
    $.ajax({
        url: "/api/subject?id=" + subId,
        type: "DELETE",
        success: function (data) {
            var row = e.parentNode.parentNode;
            row.parentNode.removeChild(row);

        },
        error: function (jqXHR) {
        }
    });

}
window.onload = function () {
    var btn = document.getElementById("addsubtab");
    btn.addEventListener("click", function () {
        addSubject();
    });

    var btn1 = document.getElementById("tab_sub");
    btn1.addEventListener("click", function () {
        showSubjects();
    });

};