<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Teams</h2>
		
		<sec:authorize access="hasRole('ADMIN')">
			<a type="button" class="btn btn-default" href="<c:url value='/team/new' />">Add Team</a>
		</sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td></td>
				<td>Code</td>
				<td>Name</td>
				<td>Coach</td>
				<td style="width: 50px"></td>
				
				<sec:authorize access="hasRole('ADMIN')">
					<td style="width: 100px"></td>
				</sec:authorize>
			</tr>
			<c:forEach items="${teams}" var="team">
				<tr>
					<td>
						<c:choose>
							<c:when test="${not empty team.image}">
								<img class="listImage" src="<c:url value='/team/image?id=${team.id}'/>"/>
							</c:when>
							
							<c:otherwise>
								<img class="listImage" src="<c:url value="/resources/images/placeholder.png" />"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${team.code}</td>
					<td>${team.name}</td>
					<td>${team.coach}</td>
					<td>
						<a href="<c:url value='/team/view-${team.id}-team' />">
							<button type="button" class="btn btn-default" aria-label="View">
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
							</button>
						</a>
					</td>
					
					<sec:authorize access="hasRole('ADMIN')">
						<td>
							<a href="<c:url value='/team/edit-${team.id}-team' />">
								<button type="button" class="btn btn-default" aria-label="Edit">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								</button>
							</a>
							
							<button type="button" class="btn btn-default" aria-label="Delete"
								data-toggle="modal" data-target="#defaultModal"
								data-action="<c:url value='/team/delete-${team.id}-team' />">
								<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
							</button>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
</t:template>
