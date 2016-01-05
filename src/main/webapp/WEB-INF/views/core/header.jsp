<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="col-md-6">
			<div class="pull-right">
				<c:if test="${allowedStatisticsModification}">
					<button type="button" class="btn btn-default btn-xs time-out-item"
						data-teamid="${match.teamA.id}">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</button>
				</c:if>

				<p id="timeout-a" class="help-block header-middle">Timeout/s: 0</p>
				<span id="score-a" class="header-score"><c:out value="${teamPerformanceA.score}"/></span>
				
				<c:choose>
					<c:when test="${match.teamA.image != null}">
						<img class="header-team-logo" src="<c:url value='/team/image?id=${match.teamA.id}'/>"/>
					</c:when>
					
					<c:otherwise>
						<img class="header-team-logo" src="<c:url value="/resources/images/placeholder.png" />"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="col-md-6">
			<div class="pull-left">
				<c:choose>
					<c:when test="${match.teamB.image != null}">
						<img class="header-team-logo" src="<c:url value='/team/image?id=${match.teamB.id}'/>"/>
					</c:when>
					
					<c:otherwise>
						<img class="header-team-logo" src="<c:url value="/resources/images/placeholder.png" />"/>
					</c:otherwise>
				</c:choose>
				
				<span id="score-b" class="header-score"><c:out value="${teamPerformanceB.score}"/></span>
				<p id="timeout-b" class="help-block header-middle">Timeout/s: 0</p>

				<c:if test="${allowedStatisticsModification}">				
					<button type="button" class="btn btn-default btn-xs time-out-item"
						data-teamid="${match.teamA.id}">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</button>
					
					<button type="button" class="btn btn-danger header-middle" aria-label="End Match"
						data-toggle="modal" data-target="#defaultModal"
						data-action="<c:url value='/core/end-${match.id}-match' />">
						<span class="glyphicon glyphicon-flag" aria-hidden="true"></span> End Match
					</button>
				</c:if>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	
	<p id="matchId" class="hidden"><c:out value="${match.id}"/></p>
</nav>