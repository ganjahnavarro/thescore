<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<table class="table table-hover table-striped">
	<tr>
		<td>Newsfeed</td>
		<td></td>
		<c:choose>
			<c:when test="${viewOnly == false}">
				<td style="width: 100px"></td>
			</c:when>
		</c:choose>
	</tr>

	<c:forEach items="${newsfeeds}" var="newsfeed">
		<tr>
			<td><fmt:formatDate pattern="MMM dd, HH:mm" value="${newsfeed.date}" /></td>
			<td>${newsfeed.description}</td>
			<c:choose>
				<c:when test="${viewOnly == false}">
					<td>
						<a href="<c:url value='/newsfeed/edit-${newsfeed.id}-newsfeed' />">
							<button type="button" class="btn btn-default" aria-label="Edit">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</button>
						</a>
	
						<button type="button" class="btn btn-default" aria-label="Delete"
							data-toggle="modal" data-target="#defaultModal"
							data-action="<c:url value='/newsfeed/delete-${newsfeed.id}-newsfeed' />">
							<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</button>
					</td>
				</c:when>
			</c:choose>
		</tr>
	</c:forEach>
</table>