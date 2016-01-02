<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">
	<!-- Substitution Players -->
	<div class="col-md-6">
		<div class="player-selection well">
			<c:forEach items="${teamABench}" var="player">
				<div class="substitution-item draggable droppable" data-playerid="${player.id}">
					<c:choose>
						<c:when test="${player.image != null}">
							<img class="substitution-img" data-playerid="${player.id}"
								src="<c:url value='/player/image?id=${player.id}'/>"/>
						</c:when>
						
						<c:otherwise>
							<img class="substitution-img" data-playerid="${player.id}"
								src="<c:url value="/resources/images/person-dummy.png" />"/>
						</c:otherwise>
					</c:choose>
				</div>	
			</c:forEach>
		</div>
	</div>

	<div class="col-md-6">
		<div class="player-selection well">
			<c:forEach items="${teamBBench}" var="player">
				<div class="substitution-item draggable droppable" data-playerid="${player.id}">
					<c:choose>
						<c:when test="${player.image != null}">
							<img class="substitution-img" data-playerid="${player.id}"
								src="<c:url value='/player/image?id=${player.id}'/>"/>
						</c:when>
						
						<c:otherwise>
							<img class="substitution-img" data-playerid="${player.id}"
								src="<c:url value="/resources/images/person-dummy.png" />"/>
						</c:otherwise>
					</c:choose>
				</div>	
			</c:forEach>
		</div>
	</div>

	<!-- Active Players -->
	<div class="col-md-6">
		<div class="player-selection well">
			<c:forEach items="${teamAPlayers}" var="player">
				<div class="player-selection-item draggable droppable" data-playerid="${player.id}">
					<c:choose>
						<c:when test="${player.image != null}">
							<img class="player-selection-img" data-playerid="${player.id}"
								src="<c:url value='/player/image?id=${player.id}'/>"/>
						</c:when>
						
						<c:otherwise>
							<img class="player-selection-img" data-playerid="${player.id}"
								src="<c:url value="/resources/images/person-dummy.png" />"/>
						</c:otherwise>
					</c:choose>
				</div>	
			</c:forEach>
		</div>
	</div>

	<div class="col-md-6">
		<div class="player-selection well">
			<c:forEach items="${teamBPlayers}" var="player">
				<div class="player-selection-item draggable droppable" data-playerid="${player.id}">
					<c:choose>
						<c:when test="${player.image != null}">
							<img class="player-selection-img" data-playerid="${player.id}"
								src="<c:url value='/player/image?id=${player.id}'/>"/>
						</c:when>
						
						<c:otherwise>
							<img class="player-selection-img" data-playerid="${player.id}"
								src="<c:url value="/resources/images/person-dummy.png" />"/>
						</c:otherwise>
					</c:choose>
				</div>	
			</c:forEach>
		</div>
	</div>

	<jsp:include page="actions.jsp" />
	<jsp:include page="info.jsp" />
	
</div>