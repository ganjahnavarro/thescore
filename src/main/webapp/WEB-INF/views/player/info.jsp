<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>${player.displayString}</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/player/list' />">Back to List</a>
		<div class="clr"></div>
	</div>
	
	<a type="button" class="btn btn-danger" href="<c:url value='/player/list/download' />">Print PDF</a>

	<!-- 
	<div class="row">
		<div class="col-lg-6 col-md-8">
			<div class="table-responsive">
				<table class="table table-hover table-striped">
					<tr>
						<td></td>
						<td>Total</td>
						<td>Maximum</td>
						<td>Average</td>
					</tr>
					<c:forEach items="${records}" var="record">
						<tr>
							<td>${record.action}</td>
							<td>${record.total}</td>
							<td>${record.maxOnSingleMatch}</td>
							<td>${record.averageDisplay}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	 -->

</t:template>