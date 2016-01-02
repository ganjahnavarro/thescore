<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Newsfeed</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
            <a type="button" class="btn btn-default" href="<c:url value='/newsfeed/new' />">Add Newsfeed</a>
        </sec:authorize>
		
		<div class="clr"></div>
	</div>

	<jsp:include page="table.jsp" />
</t:template>