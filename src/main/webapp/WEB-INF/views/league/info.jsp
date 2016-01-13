<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
	<div class="app-header">
		<h2>${league.displayString}</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/league/list' />">League List</a>
		<p id="leagueId" class="hidden"><c:out value="${league.id}"/></p>
		<div class="clr"></div>
	</div>
	
	<div class="well">
		<div class="form-group col-sm-6">
			<div class="row">
				<div class="form-group col-sm-6">
					<label class="control-label">Start Date</label>
					<input class="form-control" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${league.startDate}" />"
						readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6">
					<label class="control-label">End Date</label>
					<input class="form-control" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${league.endDate}" />"
						readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6">
					<label class="control-label">Prize</label>
					<input class="form-control" value="<fmt:formatNumber value="${league.prize}" pattern="#,##0.00"/>" readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6">
					<label class="control-label">Max Team Count</label>
					<input class="form-control" value="${league.maxTeamCount}" readonly="readonly"/>
				</div>
				
				<div class="form-group col-sm-6">
					<label class="control-label">Description</label>
					<textarea class="form-control" readonly="readonly">${league.description}</textarea>
				</div>
				
				<div class="form-group col-sm-6">
					<label class="control-label">Address</label>
					<textarea class="form-control" readonly="readonly">${league.address}</textarea>
				</div>
			</div>
		</div>
		
		<div class="col-sm-6">
			<div class="form-group">
				<img src="<c:url value="/resources/images/trophy.png" />"/>
				<label class="control-label">Championships</label>
			</div>
			
			<c:choose>
				<c:when test="${league.champion == null && onLeagueEnd == false}">
					<p class="help-block text-center">League not yet finished.</p>
				</c:when>
				
				<c:when test="${league.champion == null && onLeagueEnd == true}">
					<form action="<c:url value='/league/end-${league.id}-league' />" method="GET">
						<div class="form-group col-sm-12">
							<label for="championSelect" class="control-label">Champion</label>
							<select id="championSelect" class="form-control" required="required" name="championPK">
								<c:forEach items="${teams}" var="team">
									<option value="${team.id}">${team.displayString}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="form-group col-sm-12">
							<table id="mythicalFiveSelect" class="table table-hover table-striped">
								<thead>
									<tr>
										<td>Mythical Five</td>
									</tr>
									
									<c:forEach items="${mythicalFive}" var="player">
										<tr>
											<td>
												<button type="button" class="btn btn-default btn-xs remove-player-to-mythical-five"
													data-playerid="${player.id}">
													<span class="glyphicon glyphicon-minus" aria-hidden="true" data-playerid="${player.id}"></span>
												</button>
												${player.displayString}
											</td>
										</tr> 
									</c:forEach>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						
						<div class="form-group col-sm-12">
							<button type="submit" class="btn btn-danger">Confirm</button>
							<a type="button" class="btn btn-default" href="<c:url value='/league/list' />">Cancel</a>
						</div>
					</form>
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
					
					<br/>
					
					<div>
						<label class="control-label">Mythical Five</label>
					</div>
					
					<c:forEach items="${mythicalFive}" var="player">
						<div class="myth-five-info">
							<a href="<c:url value='/player/view-${player.id}-player' />">
								<c:choose>
									<c:when test="${player.image != null}">
										<img src="<c:url value='/player/image?id=${player.id}'/>"/>
									</c:when>
									
									<c:otherwise>
										<img src="<c:url value="/resources/images/person-dummy.png" />"/>
									</c:otherwise>
								</c:choose>
							</a>
						</div>	
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="clearfix"></div>
	</div>
	
	<div class="row">
		<div class="col-md-3">
			<h4>Teams</h4>
			
			<div class="table-responsive">
				<table id="team-records" class="table table-hover table-striped">
					<thead> 
						<tr>
							<td>Team</td>
							<td>W</td>
							<td>L</td>
						</tr>
					</thead>
					
					<tbody>
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
					</tbody>
				</table>
			</div>
		</div>
	
		<div class="col-md-9">
			<h4>Players</h4>
			
			<div class="table-responsive">
				<table id="player-records" class="table table-hover table-striped">
					<thead> 
						<tr>
							<th>Player</th>
							<th>Team</th>
							<c:forEach items="${actions}" var="action">
								<th>${action}</th> 
							</c:forEach> 
						</tr> 
					</thead>
					
					<tbody>
						<c:forEach items="${playerRecords}" var="entry">
							<tr>
								<td>
									<c:if test="${onLeagueEnd == true}">
										<button type="button" class="btn btn-default btn-xs add-player-to-mythical-five" data-playerid="${entry.key.id}">
											<span class="glyphicon glyphicon-plus" aria-hidden="true" data-playerid="${entry.key.id}"></span>
										</button>
									</c:if>
								
									<a href="<c:url value='/player/view-${entry.key.id}-player' />">
										${entry.key.displayString}
									</a>
								</td>
								
								<td>
									<a href="<c:url value='/team/view-${entry.key.team.id}-team' />">
										${entry.key.team.code}
									</a>
								</td>
								
								<c:forEach items="${entry.value}" var="subentry">
									<td>${subentry.value}</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<br/>
	
	<script>
		$(document).ready(function() {
			$("#player-records").tablesorter();
			
			$('body').on('click', '.add-player-to-mythical-five', function(){
				$.ajax({
					url : '/thescore/league/on-player-add',
					data: {
						leagueId : $('#leagueId').html(),
			            playerId : $(this).data('playerid')
			        },
					success : function(data) {
						$("#mythicalFiveSelect tbody").empty();
						
						try {
							var jsonArray = jQuery.parseJSON(data);
							
							var leagueId = $('#leagueId').html(); 
							
							for(i in jsonArray){
								var rowContents = '<tr><td>';
								rowContents += '<button type="button" class="btn btn-default btn-xs remove-player-to-mythical-five" data-playerid="' + jsonArray[i].key + '"> ';
								rowContents += '<span class="glyphicon glyphicon-minus" aria-hidden="true" data-playerid="' + jsonArray[i].key + '"></span>';
								rowContents += '</button> ';
								rowContents += jsonArray[i].value;
								rowContents += '</td></tr> ';
								$('#mythicalFiveSelect > tbody:last-child').append(rowContents);
							}
						} catch (e) {
							console.log('This doesn\'t look like a valid JSON: ', data);
							return;
						}
					}
				});
			});
			
			$('body').on('click', '.remove-player-to-mythical-five', function(){
				$.ajax({
					url : '/thescore/league/on-player-remove',
					data: {
						leagueId : $('#leagueId').html(),
			            playerId : $(this).data('playerid')
			        },
					success : function(data) {
						$("#mythicalFiveSelect tbody").empty();
						
						try {
							var jsonArray = jQuery.parseJSON(data);
							var leagueId = $('#leagueId').html(); 
							
							for(i in jsonArray){
								var rowContents = '<tr><td>';
								rowContents += '<button type="button" class="btn btn-default btn-xs remove-player-to-mythical-five" data-playerid="' + jsonArray[i].key + '"> ';
								rowContents += '<span class="glyphicon glyphicon-minus" aria-hidden="true" data-playerid="' + jsonArray[i].key + '"></span>';
								rowContents += '</button> ';
								rowContents += jsonArray[i].value;
								rowContents += '</td></tr> ';
								$('#mythicalFiveSelect > tbody:last-child').append(rowContents);
							}
						} catch (e) {
							console.log('This doesn\'t look like a valid JSON: ', data);
							return;
						}
					}
				});
			});
		});
	</script>
	
</t:template>