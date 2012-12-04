function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("msg", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("msg");
    ev.target.appendChild(document.getElementById(data));
}

$j(function () {
    dwr.engine.setActiveReverseAjax(true);
    $j("#messageForm").submit(function () {
        DwrService.sendMessage($j("#nick").val(), $j("#messageInput").val());
        $j("#messageInput").val("");
        return false;
    });
});

function showMessage(from, message, date, id) {
    $j("<pre id='" + id + "' draggable='true' ondragstart='drag(event)'><span class='label label-important' style='float:left'> " + from + "</span>" + message + "<label style='float:right;font-size:11px' data-dismiss='alert'>" + date + " x</label>" + "</pre>").hide().fadeIn(500)
        .insertAfter($j("#insertMessage"));
    $j("#messageDiv").append("<br/>");
}

$j(document).ready(function () {
    $("#loadmore").live("click", function () {
        var visibleMessages = 0;
        $j('pre').each(function (index) {
            visibleMessages++;
        });
        $j.ajax({
            type: 'POST',
            data: ({
                skip: visibleMessages
            }),
            beforeSend: function () {
                $j("#loadmore").remove();
                $j('#insertMoreMessage').after('<img id="loading_img" src="http://www.tagmobile.com/site/images/ajax-loader.gif" width=30px height=30px/>');
            },
            url: location.href + "/more",
            success: function (data) {
                $j("#loading_img").remove();
                $j('#insertMoreMessage').after('<button id="loadmore" class="btn btn-primary btn-large btn-block"><span id="load_text">Load more</span></button>');
                for (var i = 0; i < data.length; i++) {
                    $j("<pre id='" + data[i].objectId +"' draggable='true' ondragstart='drag(event)'><span class='label label-important' style='float:left'> " + data[i].from + "</span>" + data[i].message + "<label style='float:right;font-size:11px' data-dismiss='alert'>" + data[i].date + " x</label>" + "</pre>").hide().fadeIn(500)
                        .insertBefore($j("#insertMoreMessage"));
                }
            }
        });
    });
});