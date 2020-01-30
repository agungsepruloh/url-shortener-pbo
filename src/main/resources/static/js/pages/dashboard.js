$(document).ready(function() {
    // Checkbox button
    $("#hash").hide().prop("disabled", true);
    $("#isGenerate").prop("checked", true);
    $(document).on("click", "#isGenerate", function() {
        if ($(this).is(":checked")) $("#hash").hide().prop("disabled", true);
        else $("#hash").show().prop("disabled", false);
    });
    let endPoint = null;
    let id = null;

    // On click add new button
    $(document).on("click", "#addNewButton", function() {
        $("#titleModal").text("Add New URL");
        setTimeout(function() {
            $("#title").focus();
        }, 100);
        endPoint = 'url';
        $("#title").val("");
        $("#originalUrl").val("");
        $("#hash").val("");
    });

    // On click edit button
    $(document).on("click", ".edit-btn", function() {
        $("#titleModal").text("Edit URL");
        setTimeout(function() {
            $("#title").focus();
        }, 100);
        id = this.id;
        endPoint = `url/update/${id}`;
        $("#title").val($(this).closest('tr').find('#title-data').text().trim());
        $("#originalUrl").val($(this).closest('tr').find('#original-url-data').text().trim());
        $("#hash").val($(this).closest('tr').find('#hash-data').text().trim());
    });

    // On click delete button
    $(document).on("click", ".delete-btn", function() {
        id = this.id;
        endPoint = `url/delete/${id}`;
        $("#confirmModalTitle").text("Delete URL");
        $("#confirm-modal-body").text("Are you sure want to delete this URL?");
    });

    // On click confirm delete button
    $(document).on("click", "#confirm-delete-btn", function() {
        $.get(endPoint, function(data) {
            $("#table-div").load(location.href+" #table-div>*", "");
            let alert = createAlert(data.status, data.message);
            $("#alerts").append(alert);
            setTimeout(function() {
                $("#alerts .alert").eq(0).alert("close");
            }, 3000);
        });
    });

    // On form submit
    $(document).on("submit", "#url-form", function(event) {
        event.preventDefault();
        let title = $("#title").val();
        let originalUrl = $("#originalUrl").val();
        let hash = $("#hash").val();

        // Post all the values to url controller
        $.post(
            endPoint,
            { title: title, originalUrl: originalUrl, hash: hash },
            function(data) {
                $("#table-div").load(location.href+" #table-div>*", "");
                $("#addUrlModal").modal("toggle");
                let alert = createAlert(data.status, data.message);
                $("#alerts").append(alert);
                setTimeout(function() {
                    $("#alerts .alert").eq(0).alert("close");
                }, 3000);
            }
        );
    });

    /**
     * @param status
     * @param message
     * @return alert
     */
    function createAlert(status, message) {
        return `<div class="alert alert-${status === 'success' ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                   <strong>${status === 'success' ? 'Success' : 'Error'}!</strong> ${message}.
                   <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                       <span aria-hidden="true">&times;</span>
                   </button>
               </div>`;
    }
});
