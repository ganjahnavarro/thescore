<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Leagues</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
            <a type="button" class="btn btn-default" href="<c:url value='/league/new' />">Add League</a>
        </sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td>Name</td>
				<td>Description</td>
				<td style="width: 50px"></td>
				
				<sec:authorize access="hasRole('COMMITTEE')">
					<td style="width: 150px"></td>
				</sec:authorize>
			</tr>
			
			<c:forEach items="${leagues}" var="league">
				<tr>
					<td>${league.name}</td>
					<td>${league.description}</td>
					<td>
						<a href="<c:url value='/league/view-${league.id}-league' />">
							<button type="button" class="btn btn-default" aria-label="View">
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
							</button>
						</a>
					</td>
					
					<sec:authorize access="hasRole('COMMITTEE')">
						<td>
							<c:if test="${league.lockedDate == null}">
								<a href="<c:url value='/league/edit-${league.id}-league' />">
									<button type="button" class="btn btn-default" aria-label="Edit" title="Edit league">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</button>
								</a>
								
								<button type="button" class="btn btn-default" aria-label="Delete"
									data-toggle="modal" data-target="#defaultModal" title="Delete league"
									data-action="<c:url value='/league/delete-${league.id}-league' />">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
								</button>
							
								<button type="button" class="btn btn-default" title="Lock league"
									aria-label="Lock" data-toggle="modal" data-target="#defaultModal"
									data-action="<c:url value='/league/lock-${league.id}-league' />"
									data-message="Are you sure you want to lock this league?">
									<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
								</button>
							</c:if>
							
							<c:if test="${league.lockedDate != null && league.endDate == null && league.generated == false}">
								<button type="button" class="btn btn-default" title="Generate round robin matches"
									aria-label="Generate" data-toggle="modal" data-target="#defaultModal"
									data-action="<c:url value='/league/generate-${league.id}-league' />"
									data-message="Are you sure you want to generate round robin matches?">
									<span class="glyphicon glyphicon-retweet" aria-hidden="true"></span>
								</button>
							</c:if>
							
							<c:if test="${league.lockedDate != null && league.endDate == null}">
								<a href="<c:url value='/league/on-league-end-${league.id}' />">
									<button type="button" class="btn btn-default" aria-label="End League">
										<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
									</button>
								</a>
							</c:if>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</t:template>