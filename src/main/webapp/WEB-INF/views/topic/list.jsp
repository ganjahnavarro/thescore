<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Forum</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
            <a type="button" class="btn btn-default" href="<c:url value='/topic/new' />">Add Topic</a>
        </sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td>Title</td>
				<td>Description</td>
				<td>Date</td>
				<td>Created By</td>
				<td style="width: 50px"></td>
				
				<sec:authorize access="hasRole('COMMITTEE')">
					<td style="width: 100px"></td>
				</sec:authorize>
			</tr>
			
			<c:forEach items="${topics}" var="topic">
				<tr>
					<td>${topic.title}</td>
					<td>${topic.description}</td>
					<td><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${topic.date}" /></td>
					<td>${topic.entryBy}</td>
					<td>
						<a href="<c:url value='/topic/view-${topic.id}-topic' />">
							<button type="button" class="btn btn-default" aria-label="View">
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
							</button>
						</a>
					</td>
					
					<sec:authorize access="hasRole('COMMITTEE')">
						<td>
							<a href="<c:url value='/topic/edit-${topic.id}-topic' />">
								<button type="button" class="btn btn-default" aria-label="Edit">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								</button>
							</a>
							
							<button type="button" class="btn btn-default" aria-label="Delete"
								data-toggle="modal" data-target="#defaultModal"
								data-action="<c:url value='/topic/delete-${topic.id}-topic' />">
								<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
							</button>
						</td>
					</sec:authorize>
					
					</tr>
				</c:forEach>
		</table>
	</div>
</t:template>