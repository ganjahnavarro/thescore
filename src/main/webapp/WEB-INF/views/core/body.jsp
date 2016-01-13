<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">
	<!-- Substitution Players -->
	<div class="col-md-6">
		<div class="col-sm-12">
			<div class="player-selection well">
				<c:forEach items="${teamABench}" var="player">
					<div id="player-${player.id}" class="substitution-item draggable-a droppable-a bench-player" data-playerid="${player.id}">
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
						
						<div class="player-detail">
							<span>${player.number}</span>
						</div>
					</div>	
				</c:forEach>
			</div>
		</div>
		
		<div class="col-sm-12">
			<div class="player-selection well">
				<c:forEach items="${teamBBench}" var="player">
					<div id="player-${player.id}" class="substitution-item draggable-b droppable-b bench-player" data-playerid="${player.id}">
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
						
						<div class="player-detail">
							<span>${player.number}</span>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- Active Players -->
	<div class="col-md-6">
		<div class="col-sm-12">
			<div class="player-selection well">
				<c:forEach items="${teamAPlayers}" var="player">
					<div id="player-${player.id}" class="player-selection-item draggable-a droppable-a active-player" data-playerid="${player.id}">
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
						
						<div class="player-detail">
							<span>${player.number}</span>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<div class="col-sm-12">
			<div class="player-selection well">
				<c:forEach items="${teamBPlayers}" var="player">
					<div id="player-${player.id}" class="player-selection-item draggable-b droppable-b active-player" data-playerid="${player.id}">
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
						
						<div class="player-detail">
							<span>${player.number}</span>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<jsp:include page="actions.jsp" />
	<jsp:include page="info.jsp" />
	
</div>