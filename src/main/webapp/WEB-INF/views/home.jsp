<%@taglib tagdir="/WEB-INF/tags" prefix="captcha"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section id="typography" style="text-align: center;">
	<h1>
		<spring:message code="create.channel.header" />
	</h1>
	<hr />
	<form class="form-inline" method="post"
		action="<c:url value="/create"/>">
		<input type="text" class="input-large" name="channel"
			placeholder="<spring:message code="channel.name"/>" required>
		<button type="submit" class="btn">
			<spring:message code="button.text.create" />
		</button>
		<br />
		<br />
		<center>
			<captcha:captcha publickey="6LdbmdkSAAAAAENC9yWH-z7pWRNSCagqJ_4uFKPu"
				privatekey="6LdbmdkSAAAAAFo_sUAlShyOY3qioTZPYIp-PKzU"
				theme="blackglass" />
		</center>
	</form>

	<h1>
		<spring:message code="webservice" />
	</h1>
	<hr />
	<a href="<c:url value="/ws?wsdl"/>" class="btn btn-large btn-info"><spring:message
			code="JAX-WS WSDL" /></a> 
	<button class="btn btn-large btn-warning" data-toggle="modal" data-target="#jax_modal"><spring:message code="use.jaxws" /></button>
	<br />
	<br />

	<h1>
		<spring:message code="channel.list" />
	</h1>
	<hr />
	<div id="channels" style="width: 50%; margin: auto">
		<c:forEach var="channel" items="${channelList}">
			<form method="post" action="<c:url value="/${channel.name}/remove"/>">
				<a href="<c:url value="/${channel.name}"/>"
					class="btn btn-large span4">${channel.name}</a>
				<button class="btn btn-danger btn-large" type="submit">
					<spring:message code="button.text.remove" />
				</button>
			</form>
		</c:forEach>
	</div>
</section>

<!-- Modal -->
<div id="jax_modal" class="modal hide fade"	tabindex="-1">
	<div class="modal-header">
		<h3 id="myModalLabel"><spring:message code="use.jaxws" /></h3>
	</div>
	<div class="modal-body">
	
	<div style="text-align:center">
		<c:url value="/webservice/send" var="url" />
	    <spring:message code="channel.name" var="channelName" />
	    <spring:message code="message" var="message" />
	    <spring:message code="user.nickname" var="from" />
	
	    <form:form id="jax_form" method="POST" action="${url}" modelAttribute="message">
	        <form:input path="channel" placeholder="${channelName}" />
	        <br />
	        <form:input path="from" placeholder="${from}" />
	        <br />
	        <form:input path="message" placeholder="${message}" />
	        <br />
	    </form:form>
    </div>
    
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal"><spring:message code="button.text.close"/></button>
		<button id="reply" class="btn btn-primary" data-dismiss="modal" onclick="sendJaxForm()"><spring:message code="button.text.send"/></button>
	</div>
</div>

<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script>
    $j = jQuery.noConflict();
</script>
<script src="<c:url value=" dwr/engine.js "/>"></script>
<script src="<c:url value=" dwr/util.js "/>"></script>
<script src="${resource}/js/home.js"></script>