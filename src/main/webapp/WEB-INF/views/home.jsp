<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<section id="typography" style="text-align: center;">
	<h1>Hello world!</h1>

	<form id="messageForm" class="form-inline" action="/">
		<input id="messageInput" type="text" class="input-big"
			placeholder="Message" required>
		<button type="submit" class="btn">Send</button>
	</form>

	<div id="messageDiv">
		<div id="insertMessage"></div>
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
			return false;
		});
	});

	function showMessage(status) {
		$j("<pre>" + status + "</pre>").hide().fadeIn(500).insertAfter(
				$j("#insertMessage"));
		$j("#messageDiv").append("<br/>");
	}
</script>
