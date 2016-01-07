<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>Forum Filter</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/topic/list' />">Back to List</a>
		<div class="clr"></div>
	</div>
	
	<form:form method="POST" modelAttribute="forumFilter" cssClass="row">
		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="value" class="control-label">Invalid Words</label>
			<p class="help-block">(Comma separated)</p>
			<form:textarea path="value" id="value" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default" href="<c:url value='/topic/list' />">Cancel</a>
		</div>
	</form:form>

</t:template>