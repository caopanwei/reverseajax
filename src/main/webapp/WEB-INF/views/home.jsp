<section id="typography" style="text-align: center;">
	<h1>Create new channel</h1>
	<hr />
	<form class="form-inline" method="post" action="<c:url value="/create"/>">
		<input type="text" class="input-large" name="channel" placeholder="Channel's name" required>
		<button type="submit" class="btn">Create</button>
	</form>
	<h1>List of the channels</h1>
	<hr />
	<div id="channels" style="width: 50%; margin: auto">
		<c:forEach var="channel" items="${channelList}">
			<a href="<c:url value="/${channel.name}"/>"
				class="btn btn-large btn-block">${channel.name}</a>
			<br />
		</c:forEach>
	</div>
</section>