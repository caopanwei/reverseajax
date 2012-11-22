<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<form id="messageForm" action="/">
		<input id="messageInput" type="text" name="status" /> <input
			type="submit" value="Post" />
	</form>

	<div id="messageDiv"></div>

</body>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$j = jQuery.noConflict();
</script>

<script type="text/javascript" src="<c:url value="dwr/engine.js"/>"></script>
<script type="text/javascript" src="<c:url value="dwr/util.js"/>"></script>
<script type="text/javascript" src="<c:url value="dwr/interface/DwrService.js"/>"></script>

<script type="text/javascript">
	$j(function() {
		dwr.engine.setActiveReverseAjax(true);
		$j("#messageForm").submit(function() {
			DwrService.sendMessage($j("#messageInput").val());
			return false;
		});
	});

	function showMessage(status) {
		$j("#messageDiv").append(status);
		$j("#messageDiv").append("<br/>");
	}
</script>
</html>
