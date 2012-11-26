<%@taglib uri="/WEB-INF/tlds/capthca.tld" prefix="captcha"%>

<section id="typography" style="text-align: center;">
	<h1>Create new channel</h1>
	<hr />
	<form class="form-inline" method="post"
		action="<c:url value="/create"/>">
		<input type="text" class="input-large" name="channel"
			placeholder="Channel's name" required>
		<button type="submit" class="btn">Create</button>
		<center>
            <captcha:captcha publickey="6LdbmdkSAAAAAENC9yWH-z7pWRNSCagqJ_4uFKPu" privatekey="6LdbmdkSAAAAAFo_sUAlShyOY3qioTZPYIp-PKzU" themeName="clean"/>
        </center>
	</form>
	<h1>List of the channels</h1>
	<hr />
	<div id="channels" style="width: 50%; margin: auto">
		<c:forEach var="channel" items="${channelList}">
			<form method="post" action="<c:url value="/${channel.name}/remove"/>">
				<a href="<c:url value="/${channel.name}"/>"
					class="btn btn-large span4">${channel.name}</a>
				<button class="btn btn-danger btn-large" type="submit">Remove</button>
			</form>
		</c:forEach>
	</div>
</section>