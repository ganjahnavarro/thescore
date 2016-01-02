<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2><c:out value="${title}"/>s</h2>
		
		<sec:authorize access="hasRole('HEAD_COMMITTEE')">
			<a type="button" class="btn btn-default" href="<c:url value='/user/new?isCommitteesView=${isCommitteesView}' />">Add <c:out value="${title}"/></a>
		</sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td></td>
				<td>Name</td>
				<td>Gender</td>
				<td>Birthdate</td>
				<td>Contact No.</td>
				<td>Email</td>
				<td>Username</td>
				
				<sec:authorize access="hasRole('HEAD_COMMITTEE')">
					<td style="width: 100px"></td>
				</sec:authorize>
			</tr>
			<c:forEach items="${users}" var="user">
				<tr>
					<td>
						<c:choose>
							<c:when test="${not empty user.image}">
								<img class="listImage" src="<c:url value='/user/image?id=${user.id}'/>"/>
							</c:when>
							
							<c:otherwise>
								<img class="listImage" src="<c:url value="/resources/images/placeholder.png" />"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${user.displayString}</td>
					<td>${user.gender.code}</td>
					<td><fmt:formatDate pattern="MM/dd/yyyy" value="${user.birthDate}" /></td>
					<td>${user.contactNo}</td>
					<td>${user.email}</td>
					<td>${user.userName}</td>
					
					<sec:authorize access="hasRole('HEAD_COMMITTEE')">
						<td>
							<a href="<c:url value='/user/edit-${user.id}-user' />">
								<button type="button" class="btn btn-default" aria-label="Edit">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								</button>
							</a>
							
							<button type="button" class="btn btn-default" aria-label="Delete"
								data-toggle="modal" data-target="#defaultModal"
								data-action="<c:url value='/user/delete-${user.id}-user' />">
								<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
							</button>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</t:template>