<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>${league.displayString}</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/league/list' />">League List</a>
		<div class="clr"></div>
	</div>
	
	<div class="well">
		<div class="form-group col-sm-9">
			<div class="row">
				<div class="form-group col-sm-6 col-md-4">
					<label class="control-label">Start Date</label>
					<input class="form-control" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${league.startDate}" />"
						readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6 col-md-4">
					<label class="control-label">End Date</label>
					<input class="form-control" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${league.endDate}" />"
						readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6 col-md-4">
					<label class="control-label">Prize</label>
					<input class="form-control" value="${league.prize}" readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6 col-md-4">
					<label class="control-label">Description</label>
					<textarea class="form-control" readonly="readonly">${league.description}</textarea>
				</div>
				
				<div class="form-group col-sm-6 col-md-4">
					<label class="control-label">Address</label>
					<textarea class="form-control" readonly="readonly">${league.address}</textarea>
				</div>
				
				<div class="form-group col-sm-6 col-md-4">
					<label class="control-label">Max Team Count</label>
					<input class="form-control" value="${league.maxTeamCount}" readonly="readonly"/>
				</div>
			</div>
		</div>
		
		<div class="col-sm-3">
			<div class="form-group">
				<img src="<c:url value="/resources/images/trophy.png" />"/>
				<label class="control-label">Championships</label>
			</div>
			
			<c:choose>
				<c:when test="${league.champion == null}">
					<p class="help-block text-center">League not yet finished.</p>
				</c:when>
				<c:otherwise>
					<div class="teamImage">
						<a href="<c:url value='/team/view-${league.champion.id}-team' />">
							<c:choose>
								<c:when test="${not empty league.champion.image}">
									<img src="<c:url value='/team/image?id=${league.champion.id}'/>" />
								</c:when>
	
								<c:otherwise>
									<img src="<c:url value="/resources/images/placeholder.png" />" />
								</c:otherwise>
							</c:choose>
						</a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="clearfix"></div>
	</div>
	
	<div class="row">
		<div class="col-md-4">
			<h4>Teams</h4>
			
			<div class="table-responsive">
				<table class="table table-hover table-striped">
					<tr>
						<td>Team</td>
						<td>Win</td>
						<td>Lose</td>
					</tr>
					<c:forEach items="${teamWinLoseRecords}" var="record">
						<tr>
							<td>
								<a href="<c:url value='/team/view-${record.team.id}-team' />">
									${record.team.displayString}									
								</a>
							</td>
							<td>${record.win}</td>
							<td>${record.lose}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	
</t:template>