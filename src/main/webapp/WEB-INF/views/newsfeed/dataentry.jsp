<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<script>
		$(function() {
			$("#date").datetimepicker({
				controlType: 'select',
				stepMinute: 5,
				oneLine: true
			});
		});
	</script>

	<div class="app-header">
		<h2>Newsfeed</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/newsfeed/list' />">Back to List</a>
		<div class="clr"></div>
	</div>

	<form:form method="POST" modelAttribute="newsfeed">
		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="description" class="control-label">Description</label>
			<form:textarea path="description" id="description" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="clearfix"></div>
		
		<div class="form-group col-md-4">
			<label for="date" class="control-label">Date</label>
			<form:input path="date" id="date" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default" href="<c:url value='/newsfeed/list' />">Cancel</a>
		</div>
	</form:form>

</t:template>