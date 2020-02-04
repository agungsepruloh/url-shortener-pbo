$(document).ready(function() {
    // Checkbox button
    $("#hash").hide().prop("disabled", true);
    $("#isGenerate").prop("checked", true);
    $(document).on("click", "#isGenerate", function() {
        if ($(this).is(":checked")) $("#hash").hide().prop("disabled", true);
        else $("#hash").show().prop("disabled", false).prop('required',true);
    });
    $(".original-url-alert").hide();
    let endPoint = null;
    let id = null;
    let isValid = false;

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
        $(".original-url-alert").hide();
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
        $(".original-url-alert").hide();
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
        let isValidUrl = validURL(originalUrl);

        if (!isValidUrl) {
            $(".original-url-alert").show();
        } else {
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
        }
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

    function validURL(str) {
      var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
        '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
      return !!pattern.test(str);
    }
});
