<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="table-responsive">
	<table class="table table-hover table-striped">
		<tr>
			<td>Title</td>
			
			<c:if test="${viewOnly == false}">
				<td>Description</td>
			</c:if>
			
			<td>Date</td>
			
			<c:if test="${viewOnly == false}">
				<td>Created By</td>
			</c:if>
			
			<td style="width: 150px"></td>
		</tr>
		
		<c:forEach items="${topics}" var="topic">
			<tr>
				<td>${topic.title}</td>
				
				<c:if test="${viewOnly == false}">
					<td>${topic.description}</td>
				</c:if>
				
				<td><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${topic.date}" /></td>
				
				<c:if test="${viewOnly == false}">
					<td>${topic.entryBy}</td>
				</c:if>
					
				<td>
					<sec:authorize access="hasRole('HEAD_COMMITTEE')">
						<button type="button" class="btn btn-default pull-right" aria-label="Delete"
							data-toggle="modal" data-target="#defaultModal"
							data-action="<c:url value='/topic/delete-${topic.id}-topic' />">
							<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</button>
					</sec:authorize>
					
					<sec:authorize access="!hasRole('HEAD_COMMITTEE')">
						<c:if test="${viewOnly == false}">
							<c:if test="${topic.entryBy == userName}">
								<button type="button" class="btn btn-default pull-right" aria-label="Delete"
									data-toggle="modal" data-target="#defaultModal"
									data-action="<c:url value='/topic/delete-${topic.id}-topic' />">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
								</button>
							
								<a href="<c:url value='/topic/edit-${topic.id}-topic' />">
									<button type="button" class="btn btn-default pull-right" aria-label="Edit">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</button>
								</a>
							</c:if>
						</c:if>
					</sec:authorize>
				
					<a href="<c:url value='/topic/view-${topic.id}-topic' />">
						<button type="button" class="btn btn-default pull-right" aria-label="View">
							<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
						</button>
					</a>
				</tr>
			</c:forEach>
	</table>
</div>