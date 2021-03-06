<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>${team.displayString}</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/team/list' />">Team List</a>
		<div class="clr"></div>
	</div>
	
	<div class="well">
		<div class="form-group col-md-3 col-lg-2">
			<label class="control-label">Image</label>
			<div class="teamImage">
				<c:choose>
					<c:when test="${not empty team.image}">
						<img src="<c:url value='/team/image?id=${team.id}'/>"/>
					</c:when>
					
					<c:otherwise>
						<img src="<c:url value="/resources/images/placeholder.png" />"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="form-group col-sm-7 col-md-5 col-lg-6">
			<div class="row">
				<div class="form-group col-md-8">
					<label class="control-label">Coach</label>
					<input class="form-control" value="${team.coach}" readonly="readonly"/>
				</div>
				
				<div class="form-group col-md-12">
					<label class="control-label">Remarks</label>
					<textarea class="form-control" readonly="readonly">${team.remarks}</textarea>
				</div>
			</div>
		</div>
		
		<div class="col-sm-5 col-md-4 col-lg-4">
			<div class="form-group">
				<img src="<c:url value="/resources/images/trophy.png" />"/>
				<label class="control-label">Championships</label>
			</div>
			
			<c:choose>
				<c:when test="${empty championships}">
					<p class="help-block text-center">No Championships yet.</p>
				</c:when>
				<c:otherwise>
					<ul class="list-group">
						<c:forEach items="${championships}" var="league">
							<li class="list-group-item">
								<a href="<c:url value='/league/view-${league.id}-league' />">
									<span class="recent-match-unplayed">
										<fmt:formatNumber value="${league.prize}" pattern="#,##0.00"/>
									</span>
									${league.name}
								</a>
							</li>
						</c:forEach>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="clearfix"></div>
	</div>
	
	<div class="row">
		<div class="form-group col-lg-7 col-md-6">
			<h4>Players</h4>
			<c:forEach items="${players}" var="player">
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
		
		<div class="form-group col-lg-5 col-md-6">
			<h4>Matches</h4>
			<ul class="list-group">
				<c:forEach items="${matches}" var="match">
					<li class="list-group-item">
						<a href="<c:url value='/match/view-${match.id}-match' />">
							<c:choose>
								<c:when test="${match.winner == null}">
									<span class="recent-match-unplayed">
										<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${match.time}" />
									</span>
								</c:when>
								
								<c:when test="${match.winner.id == team.id}">
									<span class="label label-success pull-right recent-match-badge">W</span>
								</c:when>
								
								<c:otherwise>
									<span class="label label-danger pull-right recent-match-badge">L</span>
								</c:otherwise>
							</c:choose>
							${match.matchUp}
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="clearfix"></div>
	</div>
	
	<div class="row">
		<div class="col-md-4">
			<h4>Career</h4>
			
			<div class="info-table-label">
				<span class="label label-default">Overall</span>
			</div>
			
			<div class="table-responsive">
				<table class="table table-hover table-striped">
					<tr>
						<td></td>
						<td>Total</td>
						<td>Maximum</td>
						<td>Average</td>
					</tr>
					<c:forEach items="${overAllRecords}" var="record">
						<tr>
							<td>${record.action}</td>
							<td>${record.total}</td>
							<td>${record.maxOnSingleMatch}</td>
							<td>${record.displayString}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="col-md-4">
			<h4>Per League</h4>
			
			<c:forEach items="${perLeagueRecords}" var="entry">
				<div class="info-table-label">
					<a href="<c:url value='/league/view-${entry.key.id}-league' />">
						<span class="label label-success">${entry.key.displayString}</span>
					</a>
				</div>
			
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<td></td>
							<td>Total</td>
							<td>Maximum</td>
							<td>Average</td>
						</tr>
						<c:forEach items="${entry.value}" var="record">
							<tr>
								<td>${record.action}</td>
								<td>${record.total}</td>
								<td>${record.maxOnSingleMatch}</td>
								<td>${record.displayString}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
			
			<c:if test="${allLeague == false}">
				<div class="info-table-label">
					<a href="<c:url value='/view-${team.id}-team?allLeague=true&allMatch=${allMatch}' />">
						<span class="label label-danger">VIEW ALL</span>
					</a>
				</div>
			</c:if>
		</div>
		
		<div class="col-md-4">
			<h4>Per Match</h4>
			
			<c:forEach items="${perMatchRecords}" var="entry">
				<div class="info-table-label">
					<a href="<c:url value='/match/view-${entry.key.id}-match' />">
						<span class="label label-primary">${entry.key.teamA.code} VS. ${entry.key.teamB.code}</span>
					</a>
				</div>
			
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<td></td>
							<td>Total</td>
							<td>Maximum</td>
							<td>Average</td>
						</tr>
						<c:forEach items="${entry.value}" var="record">
							<tr>
								<td>${record.action}</td>
								<td>${record.total}</td>
								<td>${record.maxOnSingleMatch}</td>
								<td>${record.displayString}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
			
			<c:if test="${allMatch == false}">
				<div class="info-table-label">
					<a href="<c:url value='/view-${team.id}-team?allLeague=${allLeague}&allMatch=true' />">
						<span class="label label-danger">VIEW ALL</span>
					</a>
				</div>
			</c:if>
		</div>
	</div>
	
	<br/>
	
</t:template>