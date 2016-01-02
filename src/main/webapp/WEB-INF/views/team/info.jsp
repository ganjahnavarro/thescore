<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>Team Info..</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/team/list' />">Back to List</a>
		<div class="clr"></div>
	</div>
</t:template>