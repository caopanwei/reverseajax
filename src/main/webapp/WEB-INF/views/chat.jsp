<section id="typography" style="text-align: center;">
     <h1>Hello world!</h1>

    <form id="messageForm" class="form-inline" action="/">
        <input id="nick" type="text" class="input-big" placeholder="Nickname"
        required>
        <br/>
        <input id="messageInput" type="text" class="input-xxlarge" placeholder="Message"
        required>
        <br/>
        <button type="submit" class="btn">Send</button>
    </form>
    <div class="alert alert-info" ondrop="drop(event)" ondragover="allowDrop(event)">You can drag and drop messages here..</div>
    <div id="messageDiv">
        <div id="insertMessage"></div>
        <c:forEach var="message" items="${messages}" varStatus="counter">
            <!-- Modal -->
            <div id="modal_${message.objectId}" class="modal hide fade" tabindex="-1">
                <div class="modal-header">
                     <h3 id="myModalLabel">Reply</h3>
                </div>
               <form class="form-inline" method="post" action="">
                <div class="modal-body">
                    <textarea id="messageInput" placeholder="Message" style="max-width: 510px;min-width: 510px;"></textarea>
                    <input type="hidden" value="${message.objectId}"/>
                </div>
                <div class="modal-footer">
                        <button class="btn" data-dismiss="modal">Close</button>
                        <button id="reply" class="btn btn-primary">Send</button>
                    <c:url value=""/>
                </div>
               </form>
            </div> <pre id="${message.objectId}" draggable="true" ondragstart="drag(event)">
                <span class='label label-important' style='float:left'>${message.from}</span><button type="button" class="btn btn-mini" data-toggle="modal" data-target="#modal_${message.objectId}" style='float:left'><i class="icon-pencil"></i></button>${message.message}<label style='float:right;font-size:11px' data-dismiss="alert">${message.date} x</label></pre>
        </c:forEach>
        <div id="insertMoreMessage"></div>
        <button id="loadmore" class="btn btn-primary btn-large btn-block"><span id="load_text">Load more</span>
        </button>
    </div>
    <br/>
</section>
<c:url var="resources" value="/resources/" />
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script>
    $j = jQuery.noConflict();
</script>
<script src="<c:url value="../dwr/engine.js "/>"></script>
<script src="<c:url value="../dwr/util.js "/>"></script>
<script src="<c:url value="../dwr/interface/DwrService.js "/>"></script>
<script src="${resources}js/chat.js"></script>