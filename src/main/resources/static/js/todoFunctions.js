$(document).ready(function () {
    if (instantTaskAdded == 'ok') $("#myModal").modal('show');
    $("#todo-form").attr("action", "/todo" + (displayMode == "edit" ? "/" + id : "") + "/" + displayMode);
    if (displayMode == "edit") {
        $(".show-edit").show();
        $(".show-add").hide();
        $("h3").text("Edit todo");
    } else {
        $(".show-add").show();
        $(".show-edit").hide();
        $("h3").text("Add todo");
    }
});

function addAnother() {
    $("#myModal").modal('hide');
    $('#title').focus();
}

function addInstant() {
    var title = $('#title').val();
    if (title != null && title != '') {
        $("#todo-form").attr("action", "/todo/add-instant");
        $("#todo-form").submit()
    } else {
        $("#todo-form").click();
        return false;
    }
}