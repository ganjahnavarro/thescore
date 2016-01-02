<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div class="app-header">
		<h2>Leagues</h2>
		
		<sec:authorize access="hasRole('COMMITTEE')">
            <a type="button" class="btn btn-default" href="<c:url value='/league/new' />">Add League</a>
        </sec:authorize>
		
		<div class="clr"></div>
	</div>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<tr>
				<td>Name</td>
				<td>Description</td>
				<td style="width: 50px"></td>
				
				<sec:authorize access="hasRole('COMMITTEE')">
					<td style="width: 150px"></td>
				</sec:authorize>
			</tr>
			
			<c:forEach items="${leagues}" var="league">
				<tr>
					<td>${league.name}</td>
					<td>${league.description}</td>
					<td>
						<a href="<c:url value='/league/view-${league.id}-league' />">
							<button type="button" class="btn btn-default" aria-label="View">
								<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
							</button>
						</a>
					</td>
					
					<sec:authorize access="hasRole('COMMITTEE')">
						<td>
							<c:if test="${league.lockedDate == null}">
								<a href="<c:url value='/league/edit-${league.id}-league' />">
									<button type="button" class="btn btn-default" aria-label="Edit" title="Edit league">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									</button>
								</a>
								
								<button type="button" class="btn btn-default" aria-label="Delete"
									data-toggle="modal" data-target="#defaultModal" title="Delete league"
									data-action="<c:url value='/league/delete-${league.id}-league' />">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
								</button>
							
								<button type="button" class="btn btn-default" title="Lock league"
									aria-label="Lock" data-toggle="modal" data-target="#defaultModal"
									data-action="<c:url value='/league/lock-${league.id}-league' />"
									data-message="Are you sure you want to lock this league?">
									<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
								</button>
							</c:if>
							
							<c:if test="${league.lockedDate != null && league.generated == false}">
								<button type="button" class="btn btn-default" title="Generate round robin matches"
									aria-label="Generate" data-toggle="modal" data-target="#defaultModal"
									data-action="<c:url value='/league/generate-${league.id}-league' />"
									data-message="Are you sure you want to generate round robin matches?">
									<span class="glyphicon glyphicon-retweet" aria-hidden="true"></span>
								</button>
							</c:if>
							
							<c:if test="${league.lockedDate != null && league.endDate == null}">
								<button type="button" class="btn btn-default league-end-button" title="End league"
									aria-label="End" data-toggle="modal" data-target="#leagueEndModal"
									value="${league.id}" data-leagueid="${league.id}"
									data-action="<c:url value='/league/end-${league.id}-league' />">
									<span class="glyphicon glyphicon-flag" aria-hidden="true"></span>
								</button>
							</c:if>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- Champion Selection Modal -->
	<div id="leagueEndModal" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p id="modal-title" class="modal-title modal-margin-bottom">Are you sure you want to end this league?</p>
					
					<form action="/update-this" method="POST">
						<div class="form-group col-md-4">
							<label for="championSelect" class="control-label">Champion</label>
							<select id="championSelect" class="form-control" required="required">
							</select>
						</div>
						
						<div class="form-group col-sm-12">
							<button type="submit" class="btn btn-danger">Confirm</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		$('#leagueEndModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var action = button.data('action');
			var modal = $(this);
			modal.find('form').attr("action", action);
		})
	</script>
	
	<script>
		$(".league-end-button").click(function() {
			console.log($(this).data('leagueid'));
			console.log($(this).val());
			
			$.ajax({
				url : '/thescore/league/on-league-end',
				data: {
		            leagueId : $(this).data('leagueid')
		        },
				success : function(data) {
					$("#championSelect").empty();
					
					try {
						var jsonArray = jQuery.parseJSON(data);
						
						for(i in jsonArray){
							$("#championSelect").append($("<option></option>").attr("value", jsonArray[i].key).text(jsonArray[i].value));
						}
					} catch (e) {
						console.log('This doesn\'t look like a valid JSON: ', data);
						return;
					}
				}
			});
		}).change();
	</script>
	
</t:template>