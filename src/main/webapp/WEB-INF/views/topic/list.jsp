<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Forum</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
            <a type="button" class="btn btn-default" href="<c:url value='/topic/new' />">Add Topic</a>
        </sec:authorize>
        
        <sec:authorize access="hasRole('HEAD_COMMITTEE')">
            <a type="button" class="btn btn-danger additional-btn" href="<c:url value='/topic/filter' />">Forum Filters</a>
        </sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<jsp:include page="table.jsp" />
</t:template>