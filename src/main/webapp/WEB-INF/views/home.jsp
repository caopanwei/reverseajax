<%@taglib tagdir="/WEB-INF/tags" prefix="captcha" %>

<section id="typography" style="text-align: center;">
	<h1><spring:message code="create.channel.header"/></h1>
	<hr />
	<form class="form-inline" method="post"
		action="<c:url value="/create"/>">
		<input type="text" class="input-large" name="channel"
			placeholder="<spring:message code="channel.name"/>" required>
		<button type="submit" class="btn"><spring:message code="button.text.create"/></button>
		<br/><br/>
		<center>
            <captcha:captcha publickey="6LdbmdkSAAAAAENC9yWH-z7pWRNSCagqJ_4uFKPu" privatekey="6LdbmdkSAAAAAFo_sUAlShyOY3qioTZPYIp-PKzU" theme="blackglass"/>
        </center>
	</form>
	<h1><spring:message code="channel.list"/></h1>
	<hr />
	<div id="channels" style="width: 50%; margin: auto">
		<c:forEach var="channel" items="${channelList}">
			<form method="post" action="<c:url value="/${channel.name}/remove"/>">
				<a href="<c:url value="/${channel.name}"/>"
					class="btn btn-large span4">${channel.name}</a>
				<button class="btn btn-danger btn-large" type="submit"><spring:message code="button.text.remove"/></button>
			</form>
		</c:forEach>
	</div>
</section>