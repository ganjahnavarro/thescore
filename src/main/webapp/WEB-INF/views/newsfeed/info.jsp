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
	
	<br/>
	
	<c:if test="${not empty newsfeed.image}">
		<div class="newsfeed-image">
			<img src="<c:url value='/newsfeed/image?id=${newsfeed.id}'/>"/>
		</div>
	</c:if>
	
	<div class="newsfeed-body panel panel-default">
		<div class="panel-body">
			<p class="help-block">${newsfeed.description}</p>
			<p>${newsfeed.body}</p>
		</div>
	</div>

</t:template>