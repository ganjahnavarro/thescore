<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>${newsfeed.title}</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/newsfeed/list' />">Back to List</a>
		<div class="clr"></div>
	</div>
	
	<c:if test="${not empty newsfeed.image}">
		<div class="newsfeed-image">
			<img src="<c:url value='/newsfeed/image?id=${newsfeed.id}'/>"/>
		</div>
	</c:if>
	
	<div class="newsfeed-description">
		<p>${newsfeed.description}</p>
	</div>
	
	<div class="newsfeed-body">
		<p>${newsfeed.body}</p>
	</div>

</t:template>