$j(function() {
	dwr.engine.setActiveReverseAjax(true);
});

function sendJaxForm() {
	$j("#jax_form").submit();
}

function showNotification(channalName) {
	var num = parseInt($j("#" + channalName + "_bubble").html());
	num++;
	console.log(num);
	$j("#" + channalName + "_bubble").html(num);
}