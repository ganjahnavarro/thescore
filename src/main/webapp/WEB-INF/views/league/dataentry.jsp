<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:template>
	<script>
		$(function() {
			$("#startDate").datepicker();
		});
	</script>

	<div class="app-header">
		<h2>League</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/league/list' />">Back to List</a>
		<div class="clr"></div>
	</div>

	<form:form method="POST" modelAttribute="league">
		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="name" class="control-label">Name</label>
			<form:input path="name" id="name" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="startDate" class="control-label">Start Date</label>
			<form:input path="startDate" id="startDate" cssClass="date-picker form-control"
				cssErrorClass="date-picker form-control has-error"/>
		</div>
		
		<div class="form-group col-md-4">
			<label for="prize" class="control-label">Prize</label>
			<form:input path="prize" id="prize" cssClass="form-control" type="number"
				cssErrorClass="form-control has-error" step="0.01"/>
		</div>
		
		<div class="clearfix"></div>
		
		<div class="form-group col-md-4">
			<label for="description" class="control-label">Description</label>
			<form:textarea path="description" id="description" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="address" class="control-label">Address</label>
			<form:textarea path="address" id="address" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="maxTeamCount" class="control-label">Max Team Count</label>
			<form:input path="maxTeamCount" id="maxTeamCount" cssClass="form-control"
				cssErrorClass="form-control has-error" type="number"/>
		</div>
		
		<div class="clearfix"></div>
		
		<div class="form-group col-md-4">
			<label for="teams" class="control-label">Teams</label>
			<select id="teams" multiple="multiple" class="form-control" size="${fn:length(teams)}" name="teams">
				<c:forEach items="${teams}" var="team">
					<c:choose>
						<c:when test="${existingTeamPKs.contains(team.id)}">
							<option selected="selected" value="${team.id}">${team.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${team.id}">${team.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default" href="<c:url value='/league/list' />">Cancel</a>
		</div>
	</form:form>

</t:template>