<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Players</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
			<a type="button" class="btn btn-default" href="<c:url value='/player/new' />">Add Player</a>
		</sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td></td>
				<td>Name</td>
				<td>Birthdate</td>
				<td>Contact No.</td>
				<td>Email</td>
				<td>Height</td>
				<td>Weight</td>
				<td>Team</td>
				<td>Position</td>
				<td style="width: 50px"></td>
				
				<sec:authorize access="hasRole('COMMITTEE')">
					<td style="width: 100px"></td>
				</sec:authorize>
			</tr>
			<c:forEach items="${players}" var="player">
				<tr>
					<td>
						<c:choose>
							<c:when test="${not empty player.image}">
								<img class="listImage" src="<c:url value='/player/image?id=${player.id}'/>"/>
							</c:when>
							
							<c:otherwise>
								<img class="listImage" src="<c:url value="/resources/images/placeholder.png" />"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${player.displayString}</td>
					<td><fmt:formatDate pattern="MM/dd/yyyy" value="${player.birthDate}" /></td>
					<td>${player.contactNo}</td>
					<td>${player.email}</td>
					<td>${player.height}</td>
					<td>${player.weight}</td>
					<td>${player.team.code}</td>
					<td>${player.position.code}</td>
					<td>
						<a href="<c:url value='/player/view-${player.id}-player' />">
							<button type="button" class="btn btn-default" aria-label="View">
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
							</button>
						</a>
					</td>
					
					<sec:authorize access="hasRole('COMMITTEE')">
						<td>
							<a href="<c:url value='/player/edit-${player.id}-player' />">
								<button type="button" class="btn btn-default" aria-label="Edit">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								</button>
							</a>
							
							<button type="button" class="btn btn-default" aria-label="Delete"
								data-toggle="modal" data-target="#defaultModal"
								data-action="<c:url value='/player/delete-${player.id}-player' />">
								<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
							</button>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</t:template>