<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:template>
	<div class="app-header">
		<h2>Notifications</h2>
		<div class="clr"></div>
	</div>
	
	<div class="list-group">
		<c:forEach items="${ntfcs}" var="ntfc">
			<a href="<c:url value='${ntfc.url}'/>" class="list-group-item">${ntfc.message}</a>
		</c:forEach>
	</div>

</t:template>