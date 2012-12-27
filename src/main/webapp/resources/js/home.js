$j(function () {
	dwr.engine.setActiveReverseAjax(true);
});

function sendJaxForm(){
	$j("#jax_form").submit();
}

function showNotification(channalName){
	console.log(channalName);
}