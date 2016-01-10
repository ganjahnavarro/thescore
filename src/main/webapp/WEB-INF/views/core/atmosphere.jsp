<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>
<html>
<head>
	<title>Basketball Statistics System</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value="/resources/css/core.css" />">
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="body.jsp" />
	<jsp:include page="footer.jsp" />
	
	<div id="defaultModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p id="modal-title" class="modal-title modal-margin-bottom">Are you sure you want to End this Match?</p>
					
					<div id="defwin-match-option">
						<p class="help-block">Please select winner for default win.</p>
						<a href="<c:url value='/core/end-${match.id}-match-defwin-${match.teamA.id}' />">
							<button type="button" class="btn btn-danger">${match.teamA.code}</button>
						</a>
						<a href="<c:url value='/core/end-${match.id}-match-defwin-${match.teamB.id}' />">
							<button type="button" class="btn btn-danger">${match.teamB.code}</button>
						</a>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
					
					<div id="normal-match-option">
						<a id="actionButton" href="<c:url value='/core/end-${match.id}-match' />">
							<button type="button" class="btn btn-danger">Confirm</button>
						</a>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
					
					<div id="tie-score-match-option">
						<p class="help-block">Match is tied. Can't end yet.</p>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	<script src="<c:url value="/resources/js/jquery.atmosphere.js" />"></script>
	<script src="<c:url value="/resources/js/atmosphere.js" />"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui-touch-punch/0.2.3/jquery.ui.touch-punch.min.js"></script>

	<script>
		$(function() {
			$('.draggable-a').draggable({
				revert : true,
				stack : ".draggable-a"
			});
			
			$('.droppable-a').droppable({
				accept : ".draggable-a",
				drop : function(event, ui) {
					var draggedImg = ui.draggable.find('img');
					var droppedImg = $(this).find('img');

					var srcHolder = draggedImg.attr('src');
					draggedImg.attr('src', droppedImg.attr('src'));
					droppedImg.attr('src', srcHolder);

					var playerIdHolder = ui.draggable.data('playerid');
					ui.draggable.data('playerid', $(this).data('playerid'));
					$(this).data('playerid', playerIdHolder);
					
					var imagePlayerIdHolder = draggedImg.data('playerid');
					draggedImg.data('playerid', droppedImg.data('playerid'));
					droppedImg.data('playerid', imagePlayerIdHolder);
				}
			});
			
			$('.draggable-b').draggable({
				revert : true,
				stack : ".draggable-b"
			});
			
			$('.droppable-b').droppable({
				accept : ".draggable-b",
				drop : function(event, ui) {
					var draggedImg = ui.draggable.find('img');
					var droppedImg = $(this).find('img');

					var srcHolder = draggedImg.attr('src');
					draggedImg.attr('src', droppedImg.attr('src'));
					droppedImg.attr('src', srcHolder);

					var playerIdHolder = ui.draggable.data('playerid');
					ui.draggable.data('playerid', $(this).data('playerid'));
					$(this).data('playerid', playerIdHolder);
					
					var imagePlayerIdHolder = draggedImg.data('playerid');
					draggedImg.data('playerid', droppedImg.data('playerid'));
					droppedImg.data('playerid', imagePlayerIdHolder);
					
					var draggedNumber = ui.draggable.find('span').html();
					var droppedNumber = $(this).find('span').html();
					ui.draggable.find('span').html(droppedNumber);
					$(this).find('span').html(draggedNumber);
				}
			});
		});
	</script>
	
	<script>
		$('#defaultModal').on('show.bs.modal', function(event) {
			var teamAScore = $('#score-a').html();
			var teamBScore = $('#score-b').html();
			
			if((teamAScore == null || teamAScore == 0) && (teamBScore == null || teamBScore == 0)){
				$('#defwin-match-option').removeClass('hidden');
				$('#normal-match-option').addClass('hidden');
				$('#tie-score-match-option').addClass('hidden');
				
			} else if(teamAScore == teamBScore){
				$('#defwin-match-option').addClass('hidden');
				$('#normal-match-option').addClass('hidden');
				$('#tie-score-match-option').removeClass('hidden');
			} else {
				$('#defwin-match-option').addClass('hidden');
				$('#normal-match-option').removeClass('hidden');
				$('#tie-score-match-option').addClass('hidden');
			}
		})
	</script>
	
</body>
</html>