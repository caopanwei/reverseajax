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

function showReplies(message, from, date, parent_child){
	var ids = parent_child.split("_");
	$($("#" + ids[0])).append($j("<pre id='" + ids[1] + "' draggable='true' ondragstart='drag(event)'><span class='label label-important' style='float:left'> " + from + "</span><button type='button' class='btn btn-mini' data-toggle='modal' data-target='#modal_'" + ids[1] + "' style='float:left'><i class='icon-pencil'></i></button>" + message + "<label style='float:right;font-size:11px' data-dismiss='alert'>" + date + " x</label>" + "</pre>").hide().fadeIn(500));
}

function showMessage(from, message, date, id) {
    $j('<div id="modal_"' + id + '" class="modal hide fade" tabindex="-1"><div class="modal-header"><h3 id="myModalLabel">Reply</h3></div><div class="modal-body"><textarea id="messageInput" placeholder="Message" style="max-width: 510px;min-width: 510px;"></textarea></div><div class="modal-footer"><button class="btn" data-dismiss="modal">Close</button><button class="btn btn-primary">Send</button></div></div> ').insertAfter($j("#insertMessage"));
    $j("<pre id='" + id + "' draggable='true' ondragstart='drag(event)'><span class='label label-important' style='float:left'> " + from + "</span><button type='button' class='btn btn-mini' data-toggle='modal' data-target='#modal_'" + id + "' style='float:left'><i class='icon-pencil'></i></button>" + message + "<label style='float:right;font-size:11px' data-dismiss='alert'>" + date + " x</label>" + "</pre>").hide().fadeIn(500)
        .insertAfter($j("#insertMessage"));
    $j("#messageDiv").append("<br/>");
}

function sendReply(originalMessageId){
	var message = $("textarea[name='" + originalMessageId + "']").val();
	var from = $("#nick").val();
	if(from == ""){
		alert("Please choose a name");
	} else {
		DwrService.sendReply(originalMessageId, from, message);
	}
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
                	$j('<div id="modal_' + data[i].objectId + '" class="modal hide fade" tabindex="-1"><div class="modal-header"><h3 id="myModalLabel">Reply</h3></div><div class="modal-body"><textarea id="messageInput" placeholder="Message" style="max-width: 510px;min-width: 510px;"></textarea></div><div class="modal-footer"><button class="btn" data-dismiss="modal">Close</button><button class="btn btn-primary">Send</button></div></div> ').insertAfter($j("#insertMoreMessage"));
                	$j("<pre id='" + data[i].objectId + "' draggable='true' ondragstart='drag(event)'><span class='label label-important' style='float:left'> " + data[i].from + "</span><button type='button' class='btn btn-mini' data-toggle='modal' data-target='#modal_" + data[i].objectId + "' style='float:left'><i class='icon-pencil'></i></button>" + data[i].message + "<label style='float:right;font-size:11px' data-dismiss='alert'>" + data[i].date + " x</label>" + "</pre>").hide().fadeIn(500)
                        .insertBefore($j("#insertMoreMessage"));
                }
            }
        });
    });
});