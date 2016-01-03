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
	
	
	<div class="well">
		<div class="form-group col-md-3 col-lg-2">
			<label class="control-label">Image</label>
			<div class="profileImage">
				<c:choose>
					<c:when test="${not empty player.image}">
						<img src="<c:url value='/player/image?id=${player.id}'/>"/>
					</c:when>
					
					<c:otherwise>
						<img src="<c:url value="/resources/images/placeholder.png" />"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="form-group col-md-9 col-lg-10">
			<div class="row">
				<div class="form-group col-md-4">
					<label class="control-label">Birthdate</label>
					<input class="form-control" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${player.birthDate}" />" disabled="disabled"/>
				</div>
				
				<div class="form-group col-md-4">
					<label class="control-label">Contact No.</label>
					<input class="form-control" value="${player.contactNo}" disabled="disabled"/>
				</div>
				
				<div class="form-group col-md-4">
					<label class="control-label">Email</label>
					<input class="form-control" value="${player.email}" disabled="disabled"/>
				</div>
				
				<div class="form-group col-md-4">
					<label class="control-label">Position</label>
					<input class="form-control" value="${player.position.displayString}" disabled="disabled"/>
				</div>
				
				<div class="form-group col-md-4">
					<label class="control-label">Height</label>
					<input class="form-control" value="${player.height}" disabled="disabled"/>
				</div>
				
				<div class="form-group col-md-4">
					<label class="control-label">Weight</label>
					<input class="form-control" value="${player.weight}" disabled="disabled"/>
				</div>
			</div>
		</div>
		
		<div class="clearfix"></div>
	</div>
	
	<div class="row">
		<div class="col-md-4">
			<h4>Career</h4>
			
			<div class="info-table-label">
				<span class="label label-default">Overall</span>
			</div>
			
			<div class="table-responsive">
				<table class="table table-hover table-striped">
					<tr>
						<td></td>
						<td>Total</td>
						<td>Maximum</td>
						<td>Average</td>
					</tr>
					<c:forEach items="${overAllRecords}" var="record">
						<tr>
							<td>${record.action}</td>
							<td>${record.total}</td>
							<td>${record.maxOnSingleMatch}</td>
							<td>${record.displayString}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="col-md-4">
			<h4>Per League</h4>
			
			<c:forEach items="${perLeagueRecords}" var="entry">
				<div class="info-table-label">
					<a href="<c:url value='/league/view-${entry.key.id}-league' />">
						<span class="label label-success">${entry.key.displayString}</span>
					</a>
				</div>
			
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<td></td>
							<td>Total</td>
							<td>Maximum</td>
							<td>Average</td>
						</tr>
						<c:forEach items="${entry.value}" var="record">
							<tr>
								<td>${record.action}</td>
								<td>${record.total}</td>
								<td>${record.maxOnSingleMatch}</td>
								<td>${record.displayString}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
		</div>
		
		<div class="col-md-4">
			<h4>Per Match</h4>
			
			<c:forEach items="${perMatchRecords}" var="entry">
				<div class="info-table-label">
					<a href="<c:url value='/match/view-${entry.key.id}-league' />">
						<span class="label label-primary">${entry.key.displayString}</span>
					</a>
				</div>
			
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<td></td>
							<td>Total</td>
							<td>Maximum</td>
							<td>Average</td>
						</tr>
						<c:forEach items="${entry.value}" var="record">
							<tr>
								<td>${record.action}</td>
								<td>${record.total}</td>
								<td>${record.maxOnSingleMatch}</td>
								<td>${record.displayString}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
		</div>
	</div>

</t:template>