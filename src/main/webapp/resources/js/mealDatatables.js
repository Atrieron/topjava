var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function toTwoDigitString(number) {
    return (number>9?"":"0")+number.toString();
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "defaultContent":"",
                "render":function (row, data, dataIndex) {
                    var d = new Date(row);
                    return d.getFullYear()+"-"+toTwoDigitString(d.getMonth())+"-"+toTwoDigitString(d.getDate())+" "
                        +toTwoDigitString(d.getHours())+":"+toTwoDigitString(d.getMinutes());
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "Delete",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).addClass("exceeded");
            } else {
                $(row).addClass("normal");
            }
        },
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});