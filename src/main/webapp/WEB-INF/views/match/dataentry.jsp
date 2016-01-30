<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:template>
	<script>
		$(function() {
			$("#time").datetimepicker({
				controlType: 'select',
				stepMinute: 5,
				oneLine: true
			});
		});
	</script>
	

	<div class="app-header">
		<h2>Match</h2>
		<a type="button" class="btn btn-default" href="<c:url value='/match/list' />">Back to List</a>
		<div class="clr"></div>
	</div>

	<form:form method="POST" modelAttribute="match">
		<form:input type="hidden" path="id" id="id" />

		<div class="form-group col-md-4">
			<label for="leagueSelect" class="control-label">League *</label>
			<form:select path="league" id="leagueSelect" cssClass="form-control"
				cssErrorClass="form-control has-error" items="${leagues}"
				itemLabel="name" itemValue="id">
			</form:select>
		</div>
		
		<div class="form-group col-md-4">
			<label for="time" class="control-label">Time</label>
			<form:input path="time" id="time" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="form-group col-md-4">
			<label for="referee" class="control-label">Referee</label>
			<form:input path="referee" id="referee" cssClass="form-control"
				cssErrorClass="form-control has-error" />
		</div>
		
		<div class="clearfix"></div>
		
		<div class="form-group col-md-4">
			<label for="teamASelect" class="control-label">Team A *</label>
			<form:select path="teamA" id="teamASelect" cssClass="form-control"
				cssErrorClass="form-control has-error" items="${teams}"
				itemLabel="name" itemValue="id">
			</form:select>
		</div>
		
		<div class="form-group col-md-4">
			<label for="teamBSelect" class="control-label">Team B *</label>
			<form:select path="teamB" id="teamBSelect" cssClass="form-control"
				cssErrorClass="form-control has-error" items="${teams}"
				itemLabel="name" itemValue="id">
			</form:select>
		</div>
		
		<div class="clearfix"></div>
		
		<div class="form-group col-md-4">
			<label for="teamAPlayers" class="control-label">First 5 (Team A) *</label>
			<select id="teamAPlayers" multiple="multiple" class="form-control" size="${fn:length(teamAPlayers)}" name="teamAPlayers">
				<c:forEach items="${teamAPlayers}" var="player">
					<option value="${player.id}">${player.displayString}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group col-md-4">
			<label for="teamBPlayers" class="control-label">First 5 (Team B) *</label>
			<select id="teamBPlayers" multiple="multiple" class="form-control" size="${fn:length(teamBPlayers)}" name="teamBPlayers">
				<c:forEach items="${teamBPlayers}" var="player">
					<option value="${player.id}">${player.displayString}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group col-md-4">
			<label for="committees" class="control-label">Committees</label>
			<select id="committees" multiple="multiple" class="form-control" size="${fn:length(committees)}" name="committees">
				<c:forEach items="${committees}" var="committee">
					<c:choose>
						<c:when test="${committee.includedOnMatch}">
							<option selected="selected" value="${committee.id}">${committee.displayString}</option>
						</c:when>
						<c:otherwise>
							<option value="${committee.id}">${committee.displayString}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group col-sm-12">
			<button type="submit" class="btn btn-primary">Save</button>
			<a type="button" class="btn btn-default" href="<c:url value='/match/list' />">Cancel</a>
		</div>
	</form:form>

	<script>
		$("#leagueSelect").change(function() {
			$.ajax({
				url : '/match/on-league-change',
				data: {
		            leagueId : $(this).val(),
		            matchId : $('#id').val()
		        },
				success : function(data) {
					$("#teamASelect").empty();
					$("#teamBSelect").empty();
					
					try {
						var jsonArray = jQuery.parseJSON(data);
						
						for(i in jsonArray.teams){
							$("#teamASelect").append($("<option></option>").attr("value", jsonArray.teams[i].key).text(jsonArray.teams[i].value));
							$("#teamBSelect").append($("<option></option>").attr("value", jsonArray.teams[i].key).text(jsonArray.teams[i].value));
						}
						
						$("#teamASelect").val(jsonArray.teamA.id).trigger('change');
						$("#teamBSelect").val(jsonArray.teamB.id).trigger('change');
					} catch (e) {
						console.log('This doesn\'t look like a valid JSON: ', data);
						return;
					}
				}
			});
		}).change();
	
		$("#teamASelect").change(function() {
			$.ajax({
				url : '/match/on-team-change',
				data: {
		            teamId : $(this).val(),
		            matchId : $('#id').val()
		        },
				success : function(data) {
					$("#teamAPlayers").empty();
					
					try {
						var jsonArray = jQuery.parseJSON(data);
						
						console.log(jsonArray.players)
						console.log(jsonArray.startingPlayerPKs)
						
						for(i in jsonArray.players){
							var optn = $("<option></option>").attr("value", jsonArray.players[i].key).text(jsonArray.players[i].value);
							
							if($.inArray(jsonArray.players[i].key, jsonArray.startingPlayerPKs) != -1){
								optn.attr("selected", "selected");
							}
							$("#teamAPlayers").append(optn);
						}
						
						$("#teamAPlayers").attr('size', jsonArray.players.length);
					} catch (e) {
						console.log('This doesn\'t look like a valid JSON: ', data);
						return;
					}
				}
			});
		}).change();
	
		$("#teamBSelect").change(function() {
			$.ajax({
				url : '/match/on-team-change',
				data: {
		            teamId : $(this).val(),
		            matchId : $('#id').val()
		        },
				success : function(data) {
					$("#teamBPlayers").empty();
					
					try {
						var jsonArray = jQuery.parseJSON(data);
						
						console.log(jsonArray.players)
						console.log(jsonArray.startingPlayerPKs)
						
						for(i in jsonArray.players){
							var optn = $("<option></option>").attr("value", jsonArray.players[i].key).text(jsonArray.players[i].value);
							
							if($.inArray(jsonArray.players[i].key, jsonArray.startingPlayerPKs) != -1){
								optn.attr("selected", "selected");
							}
							$("#teamBPlayers").append(optn);
						}
						
						$("#teamBPlayers").attr('size', jsonArray.players.length);
					} catch (e) {
						console.log('This doesn\'t look like a valid JSON: ', data);
						return;
					}
				}
			});
		}).change();
	</script>
	
</t:template>