<%-- <%@ page session="false"%> --%>

<section id="typography" style="text-align: center;">
    <h1>Hello world!</h1>

    <form id="messageForm" class="form-inline" action="/">
        <input id="messageInput" type="text" class="input-big"
            placeholder="Message" required>
        <button type="submit" class="btn">Send</button>
    </form>

    <div id="messageDiv">
        <div id="insertMessage"></div>
        <c:forEach var="message" items="${messages}">
            <pre>${message.message}<label style='float:right;font-size:11px'>${message.date}</label></pre>
        </c:forEach>
        <div id="insertMoreMessage"></div>
        <button id="loadmore" class="btn btn-primary btn-large btn-block">Load more</button>
    </div>
</section>


<script type="text/javascript"
    src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $j = jQuery.noConflict();
</script>
<script type="text/javascript" src="<c:url value="dwr/engine.js"/>"></script>
<script type="text/javascript" src="<c:url value="dwr/util.js"/>"></script>
<script type="text/javascript"
    src="<c:url value="dwr/interface/DwrService.js"/>"></script>
<script type="text/javascript">
    $j(function() {
        dwr.engine.setActiveReverseAjax(true);
        $j("#messageForm").submit(function() {
            DwrService.sendMessage($j("#messageInput").val());
            $j("#messageInput").val("");
            return false;
        });
    });

    function showMessage(status, date) {
        $j(
                "<pre>" + status + "<label style='float:right;font-size:11px'>"
                        + date + "</label>" + "</pre>").hide().fadeIn(500)
                .insertAfter($j("#insertMessage"));
        $j("#messageDiv").append("<br/>");
    }
</script>
<script>
$j(document).ready(function() {
    $("#loadmore").live("click", function() {
                var visibleMessages = 0;
                $j('pre').each(function(index) {
                    visibleMessages++;
                });
                $j.ajax({
                    type : 'POST',
                    data: ({skip: visibleMessages}),
                    url : location.href + "/more",
                            success : function(data) {
                               for(var i = 0; i < data.length; i++){
                                   $j("<pre>" + data[i].message + "<label style='float:right;font-size:11px'>"
                                                   + data[i].date + "</label>" + "</pre>").hide().fadeIn(500)
                                           .insertBefore($j("#insertMoreMessage"));
                                   $j("#messageDiv").append("<br/>");
                               }
                            }
                        });
            });
});
</script>