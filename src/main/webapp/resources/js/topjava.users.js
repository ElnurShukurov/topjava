const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, updateTableAction);
    }
}

// $(document).ready(function () {
$(function () {
        makeEditable(
            $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        );
    }
)
;

function enable(checkbox, userId) {
    const enabled = checkbox.checked;
    $.ajax({
        url: userAjaxUrl + userId,
        type: 'POST',
        data: {enabled: enabled}
    }).done(function () {
        const row = $(checkbox).closest('tr');
        row.attr('data-user-enabled', enabled);
        successNoty(enabled ? "Enabled" : "Disabled");
    }).fail(function () {
        checkbox.checked = !enabled;
        alert("Failed to update user status");
    });
}