<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<script>
		$(document).ready(function() {
			$('#comment-text-area').keydown(function(event) {
				if (event.keyCode == 13) {
					this.form.submit();
					return false;
				}
			});
		});
	</script>

	<div class="app-header">
		<h2><c:out value="${topic.title}"/></h2>
		<a type="button" class="btn btn-default" href="<c:url value='/topic/list' />">Back to List</a>
		<div class="clr"></div>
		<p class="help-block">Created By: ${topic.entryBy} <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${topic.date}" /></p>
	</div>
	
	<div>
		<br />
		<h4>Comments</h4>
		
		<c:forEach items="${comments}" var="comment">
			<div class="well well-sm comment">
				<div class="col-xs-10">
					<p>${comment.value}</p>
					<p class="author">${comment.entryBy} <fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${comment.date}" /></p>
				</div>
				
				<div class="col-xs-2">
					<sec:authorize access="hasRole('HEAD_COMMITTEE')">
						<button type="button" class="btn btn-default btn-sm pull-right" aria-label="Delete"
							data-toggle="modal" data-target="#defaultModal"
							data-action="<c:url value='/topic/comment/delete-${comment.id}-comment' />">
							<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						</button>
					</sec:authorize>
					
					<sec:authorize access="!hasRole('HEAD_COMMITTEE')">
						<c:if test="${comment.entryBy == userName}">
							<button type="button" class="btn btn-default btn-sm pull-right" aria-label="Delete"
								data-toggle="modal" data-target="#defaultModal"
								data-action="<c:url value='/topic/comment/delete-${comment.id}-comment' />">
								<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
							</button>
						</c:if>
					</sec:authorize>
				</div>
				
				<div class="clearfix"></div>
			</div>
		</c:forEach>
		
		<sec:authorize access="hasRole('DEFAULT')">
			<form:form method="POST" modelAttribute="comment" cssClass="row">
				<form:input type="hidden" path="id" id="id" />
		
				<div class="form-group col-lg-6">
					<form:textarea path="value" id="comment-text-area" cssClass="form-control"
						cssErrorClass="form-control has-error" placeholder="Write your comment here.."/>
				</div>
				
				<div class="form-group col-sm-12">
					<button type="submit" class="btn btn-primary">Add</button>
				</div>
			</form:form>
		</sec:authorize>
	</div>
	
</t:template>