$(document).ready(function(){
    $("#myModal").on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var id = button.attr('data-id'); // Extract info from data-id attribute
        var modal = $(this);
        $('#delete-id').attr('onclick', 'deleteTodo('+id+')');
    });
});


function deleteTodo(id) {
    var owner = $('#owner').val();
    var context = $('#context').val();
    var project = $('#project').val();
    $(location).attr('href','/todo/id/'+id+'/owner/'+owner+'/project/'+project+'/context/'+context+'/delete');
}


function completeTodo(id) {
    var owner = $('#owner').val();
    var context = $('#context').val();
    var project = $('#project').val();
    $(location).attr('href','/todo/id/'+id+'/owner/'+owner+'/project/'+project+'/context/'+context+'/done');
}