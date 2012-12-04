	$j(function() {
		dwr.engine.setActiveReverseAjax(true);
		$j("#messageForm").submit(function() {
			DwrService.sendTemporaryMessage($j("#nick").val(), $j("#messageInput").val());
			$j("#messageInput").val("");
			return false;
		});
	});

	function showMessage(from, message, date) {
        $j(
                "<pre><span class='label label-info' style='float:left'> " + from + "</span>" + message + "<label style='float:right;font-size:11px'>"
                        + date + "</label>" + "</pre>").hide().fadeIn(500)
                .insertAfter($j("#insertMessage"));
        $j("#messageDiv").append("<br/>");
    }
