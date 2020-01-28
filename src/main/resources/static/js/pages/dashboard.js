$(document).ready(function() {
    // Checkbox button
    $("#hash").hide().prop("disabled", true);
    $("#isGenerate").prop("checked", true);
    $(document).on("click", "#isGenerate", function() {
        if ($(this).is(":checked")) $("#hash").hide().prop("disabled", true);
        else $("#hash").show().prop("disabled", false);
    });

    // On click add new button
    $(document).on("click", "#addNewButton", function() {
        $("#titleModal").text("Add New URL");
        setTimeout(function() {
            $("#title").focus();
        }, 100);
        $("#title").val("");
        $("#originalUrl").val("");
        $("#hash").val("");
    });

    // On form submit
    $(document).on("submit", "#url-form", function(event) {
        event.preventDefault();
        let title = $("#title").val();
        let originalUrl = $("#originalUrl").val();
        let hash = $("#hash").val();

        // Post values to urls controller
        $.post(
            "urls",
            { title: title, originalUrl: originalUrl, hash: hash },
            function(data) {
                $("#table-div").load(location.href+" #table-div>*", "");
                $("#addUrlModal").modal("toggle");
                let alert =
                `<div class="alert alert-${data.status === 'success' ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                    <strong>${data.status === 'success' ? 'Success' : 'Error'}!</strong> ${data.message}.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>`;
                $("#alerts").append(alert);
                setTimeout(function() {
                    $("#alerts .alert").eq(0).alert("close");
                }, 3000);
            }
        );
    });
});
