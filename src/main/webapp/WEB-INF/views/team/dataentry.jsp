<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>Team</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/team/list' />">Back to List</a>
		<div class="clr"></div>
	</div>

	<form:form method="POST" modelAttribute="team" 
		action="${pageContext.request.contextPath}${actionParam}${_csrf.parameterName}=${_csrf.token}"
		enctype="multipart/form-data">

		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="code" class="control-label">Code</label>
			<form:input path="code" id="code" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>

		<div class="form-group col-md-4">
			<label for="name" class="control-label">Name</label>
			<form:input path="name" id="name" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>

		<div class="clearfix"></div>

		<div class="form-group col-md-4">
			<label for="image" class="control-label">Image</label> <input
				id="image" class="form-control" type="file" name="fileUpload"
				size="50" />
		</div>

		<div class="form-group col-md-4">
			<label for="coach" class="control-label">Coach</label>
			<form:input path="coach" id="coach" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>

		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default"
				href="<c:url value='/team/list' />">Cancel</a>
		</div>
	</form:form>

</t:template>