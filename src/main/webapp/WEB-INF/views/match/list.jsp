<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Matches</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
			<a type="button" class="btn btn-default" href="<c:url value='/match/new' />">Add Match</a>
		</sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td>League</td>
				<td>Time</td>
				<td>Match Up</td>
				<td>Referee</td>
				<td>Winner</td>
				<td style="width: 50px"></td>
				
				<sec:authorize access="hasRole('COMMITTEE')">
					<td style="width: 100px"></td>
				</sec:authorize>
			</tr>
			<c:forEach items="${matches}" var="match">
				<tr>
					<td>${match.league.name}</td>
					<td><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${match.time}" /></td>
					<td>
						<span>${match.matchUp} </span>
						<c:if test="${match.actualStart != null && match.winner == null}">
							<span class="label label-danger">live</span>
						</c:if>
					</td>
					<td>${match.referee}</td>
					<td>${match.winner.name}</td>
					
					<td>
						<c:choose>
							<c:when test="${match.winner == null}">
								<a href="<c:url value='/core/play-${match.id}-match' />" title="Open Match">
									<button type="button" class="btn btn-default" aria-label="Start">
										<span class="glyphicon glyphicon-play" aria-hidden="true"></span>
									</button>
								</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value='/match/view-${match.id}-match' />">
									<button type="button" class="btn btn-default" aria-label="View">
										<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
									</button>
								</a>
							</c:otherwise>
						</c:choose>
					</td>
					
					<sec:authorize access="hasRole('COMMITTEE')">
						<td>
							<c:if test="${match.winner == null}">
								<a href="<c:url value='/match/edit-${match.id}-match' />">
									<button type="button" class="btn btn-default" aria-label="Edit">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</button>
								</a>
								
								<button type="button" class="btn btn-default" aria-label="Delete"
									data-toggle="modal" data-target="#defaultModal"
									data-action="<c:url value='/match/delete-${match.id}-match' />">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
								</button>
							</c:if>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
</t:template>