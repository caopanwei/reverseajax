<c:url var="resources" value="/resources/" />
<section id="typography" style="text-align: center;">
     <h1>
        <a href="<c:url value="/${channel.name}.rss"/>">
            <img src="${resources}img/RSS.jpg" width="50px" height="50px"/>
        </a>
        <a href="<c:url value="/${channel.name}/rss"/>">
            <img src="${resources}img/rss2.jpg" width="50px" height="50px"/>
        </a>
         <a href="<c:url value="/${channel.name}.json"/>">
            <img src="${resources}img/json.jpg" width="50px" height="50px"/>
        </a>
     </h1>

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
            <pre id="${message.objectId}" draggable="true" ondragstart="drag(event)"> <span class='label label-important' style='float:left'>${message.from}</span>${message.message}<label style='float:right;font-size:11px' data-dismiss="alert">${message.date} x</label></pre>
        </c:forEach>
        <div id="insertMoreMessage"></div>
        <button id="loadmore" class="btn btn-primary btn-large btn-block"><span id="load_text">Load more</span>
        </button>
    </div>
    <br/>
</section>
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script>
    $j = jQuery.noConflict();
</script>
<script src="<c:url value=" dwr/engine.js "/>"></script>
<script src="<c:url value=" dwr/util.js "/>"></script>
<script src="<c:url value=" dwr/interface/DwrService.js "/>"></script>
<script src="${resources}js/chat.js"></script>