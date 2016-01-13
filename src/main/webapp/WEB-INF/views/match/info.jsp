<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>${match.matchUp}</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/match/list' />">Match List</a>
		<a type="button" class="btn btn-danger additional-btn" href="<c:url value='/match/result/download?matchId=${match.id}' />">Print PDF</a>
		<div class="clr"></div>
	</div>
	
	<div class="well">
		<div class="form-group col-sm-6 col-md-3">
			<label class="control-label">League</label>
			<input class="form-control" value="${match.league.displayString}" readonly="readonly"/>
		</div>
		
		<div class="form-group col-sm-6 col-md-3">
			<label class="control-label">Time</label>
			<input class="form-control" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${match.time}" />"
				readonly="readonly"/>
		</div>
		
		<div class="form-group col-sm-6 col-md-3">
			<label class="control-label">Referee</label>
			<input class="form-control" value="${match.referee}" readonly="readonly"/>
		</div>
		
		<div class="form-group col-sm-6 col-md-3">
			<label class="control-label">Winner</label>
			<input class="form-control" value="${match.winner.name}" readonly="readonly"/>
		</div>
		<div class="clearfix"></div>
	</div>
	
	<div class="row">
		<div class="table-responsive col-sm-6">
			<table class="table table-hover table-striped">
				<tr>
					<td></td>
					<td>${match.teamA.code}</td>
					<td>${match.teamB.code}</td>
				</tr>
				
				<tr>
					<td class="info-label">Total Score</td>
					<td id="score-a"><c:out value="${teamPerformanceA.score}"/></td>
					<td id="score-b"><c:out value="${teamPerformanceB.score}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Field Goals</td>
					<td id="fg-a"><c:out value="${teamPerformanceA.fg}"/> / <c:out value="${teamPerformanceA.fga}"/></td>
					<td id="fg-b"><c:out value="${teamPerformanceB.fg}"/> / <c:out value="${teamPerformanceB.fga}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">3 Point Field Goals</td>
					<td id="threefg-a"><c:out value="${teamPerformanceA.threefg}"/> / <c:out value="${teamPerformanceA.threefga}"/></td>
					<td id="threefg-b"><c:out value="${teamPerformanceB.threefg}"/> / <c:out value="${teamPerformanceB.threefga}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Free Throws</td>
					<td id="ft-a"><c:out value="${teamPerformanceA.ft}"/> / <c:out value="${teamPerformanceA.fta}"/></td>
					<td id="ft-b"><c:out value="${teamPerformanceB.ft}"/> / <c:out value="${teamPerformanceB.fta}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Assists</td>
					<td id="ast-a"><c:out value="${teamPerformanceA.ast}"/></td>
					<td id="ast-b"><c:out value="${teamPerformanceB.ast}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Steals</td>
					<td id="stl-a"><c:out value="${teamPerformanceA.stl}"/></td>
					<td id="stl-b"><c:out value="${teamPerformanceB.stl}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Blocks</td>
					<td id="blk-a"><c:out value="${teamPerformanceA.blk}"/></td>
					<td id="blk-b"><c:out value="${teamPerformanceB.blk}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Defensive Rebounds</td>
					<td id="def-a"><c:out value="${teamPerformanceA.def}"/></td>
					<td id="def-b"><c:out value="${teamPerformanceB.def}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Offensive Rebounds</td>
					<td id="off-a"><c:out value="${teamPerformanceA.off}"/></td>
					<td id="off-b"><c:out value="${teamPerformanceB.off}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Turnovers</td>
					<td id="to-a"><c:out value="${teamPerformanceA.to}"/></td>
					<td id="to-b"><c:out value="${teamPerformanceB.to}"/></td>
				</tr>
				
				<tr>
					<td class="info-label">Fouls</td>
					<td id="foul-a"><c:out value="${teamPerformanceA.foul}"/></td>
					<td id="foul-b"><c:out value="${teamPerformanceB.foul}"/></td>
				</tr>
			</table>
		</div>
		
		<div class="table-responsive col-sm-6">
			<div class="row">
				<div class="form-group col-sm-12">
					<h4>First 5 (${match.teamA.code})</h4>
					<c:forEach items="${teamAPlayers}" var="player">
						<div class="substitution-item" data-playerid="${player.id}">
							<a href="<c:url value='/player/view-${player.id}-player' />">
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
							</a>
						</div>	
					</c:forEach>
				</div>
				
				<div class="form-group col-sm-12">
					<h4>First 5 (${match.teamB.code})</h4>
					<c:forEach items="${teamBPlayers}" var="player">
						<div class="substitution-item" data-playerid="${player.id}">
							<a href="<c:url value='/player/view-${player.id}-player' />">
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
							</a>
						</div>	
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	
</t:template>