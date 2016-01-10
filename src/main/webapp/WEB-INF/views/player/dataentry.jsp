<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<script>
		$(function() {
			$("#birthDate").datepicker();
		});
	</script>

	<div class="app-header">
		<h2>Player</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/player/list' />">Back to List</a>
		<div class="clr"></div>
	</div>

	<form:form method="POST" modelAttribute="player" 
		action="${pageContext.request.contextPath}${actionParam}${_csrf.parameterName}=${_csrf.token}"
		enctype="multipart/form-data">
		
		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="lastName" class="control-label">Last Name *</label>
			<form:input path="lastName" id="lastName" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="firstName" class="control-label">First Name *</label>
			<form:input path="firstName" id="firstName" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="middleName" class="control-label">Middle Name</label>
			<form:input path="middleName" id="middleName"
				cssClass="form-control" cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="birthDate" class="control-label">Birthdate</label>
			<form:input path="birthDate" id="birthDate" cssClass="date-picker form-control"
				cssErrorClass="date-picker form-control has-error"/>
		</div>
		
		<div class="form-group col-md-4">
			<label for="contactNo" class="control-label">Contact No.</label>
			<form:input path="contactNo" id="contactNo" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="email" class="control-label">Email</label>
			<form:input path="email" id="email" cssClass="form-control"
				cssErrorClass="form-control has-error" type="email" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="team" class="control-label">Team *</label>
			<form:select path="team" id="team" cssClass="form-control"
				cssErrorClass="form-control has-error" items="${teams}"
				itemLabel="name" itemValue="id">
			</form:select>
		</div>
		
		<div class="form-group col-md-4">
			<label for="position" class="control-label">Position *</label>
			<form:select path="position" id="position" cssClass="form-control"
				cssErrorClass="form-control has-error" items="${positions}"
				itemLabel="displayString">
			</form:select>
		</div>
		
		<div class="form-group col-md-3">
			<label for="number" class="control-label">Number *</label>
			<form:input path="number" id="number" cssClass="form-control"
				cssErrorClass="form-control has-error" type="number" />
		</div>
		
		<div class="form-group col-md-3">
			<label for="height" class="control-label">Height</label>
			<form:input path="height" id="height" cssClass="form-control"
				cssErrorClass="form-control has-error" type="number" step="0.01"/>
		</div>
		
		<div class="form-group col-md-3">
			<label for="weight" class="control-label">Weight</label>
			<form:input path="weight" id="weight" cssClass="form-control"
				cssErrorClass="form-control has-error" type="number" step="0.01"/>
		</div>
		
		<div class="form-group col-md-3">
			<label for="image" class="control-label">Image</label>
			<input id="image" class="form-control" type="file" name="fileUpload" size="50" />
		</div>
		
		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default" href="<c:url value='/player/list' />">Cancel</a>
		</div>
	</form:form>

</t:template>