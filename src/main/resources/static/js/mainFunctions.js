$(document).ready(function(){
    $("#myModal").on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var id = button.attr('data-id'); // Extract info from data-id attribute
        var modal = $(this);
        $('#delete-id').attr('onclick', 'deleteTodo('+id+')');
    });

//BEGIN NEW SECTION
    var tdRow=$('table').find('tr').eq(1);
    
    document.onscroll = function() {
        var scroll = $(window).scrollTop();
        if (scroll >= 50) {
            $("thead").css({
                "position": "fixed",
                "top": "68px",
                "transition": "all 0.5s ease-out"
            });

            $("th").each(function( index ) {
                var width = tdRow.find("td:eq("+index+")").css("width");
                $(this).css("width",width);
            });

        } else {
            $("thead").css({
                "position": "relative",
                "top": "0px"
            });
        }
    };

//END
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