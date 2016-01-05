<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<script>
		$(function() {
			$("#birthDate").datepicker();
		});
	</script>

	<div class="app-header">
		<h2><c:out value="${title}"/> </h2>
		<a type="button" class="btn btn-default" href="<c:url value='/user/list' />">Back to List</a>
		<div class="clr"></div>
	</div>

	<form:form method="POST" modelAttribute="user" 
		action="${pageContext.request.contextPath}${actionParam}${_csrf.parameterName}=${_csrf.token}"
		enctype="multipart/form-data">
	
		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="lastName" class="control-label">Last Name</label>
			<form:input path="lastName" id="lastName" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="firstName" class="control-label">First Name</label>
			<form:input path="firstName" id="firstName" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="middleName" class="control-label">Middle Name</label>
			<form:input path="middleName" id="middleName"
				cssClass="form-control" cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="gender" class="control-label">Gender</label>
			<form:select path="gender" id="gender" cssClass="form-control"
				cssErrorClass="form-control has-error" items="${genders}"
				itemLabel="displayString">
			</form:select>
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
		
		<sec:authorize access="hasRole('ADMIN')">
			<c:if test="${isCommitteesView}">
				<div class="form-group col-md-4">
					<label for="type" class="control-label">Type</label>
					<form:select path="type" id="type" cssClass="form-control"
						cssErrorClass="form-control has-error" items="${types}"
						itemLabel="displayString">
					</form:select>
				</div>
			</c:if>
		</sec:authorize>
		
		<div class="form-group col-md-3">
			<label for="image" class="control-label">Image</label>
			<input id="image" class="form-control" type="file" name="fileUpload" size="50" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="userName" class="control-label">Username</label>
			<form:input path="userName" id="userName" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<c:if test="${edit == false}">
			<div class="form-group col-md-4">
				<label for="password" class="control-label">Password</label>
				<form:input path="password" id="password" cssClass="form-control"
					cssErrorClass="form-control has-error" type="password" />
			</div>
			
			<div class="form-group col-md-4">
				<label for="passwordConfirmation" class="control-label">Password Confirmation</label>
				<input id="passwordConfirmation" class="form-control"
					type="password" name="passwordConfirmation" />
			</div>
		</c:if>
		
		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default" href="<c:url value='/user/list' />">Cancel</a>
		</div>
	</form:form>

</t:template>