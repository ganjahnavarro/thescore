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
					<a id="actionButton">
						<button type="button" class="btn btn-danger">Confirm</button>
					</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
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
					//console.log('Incoming Player ID: ' + ui.draggable.data('playerid'));
					//console.log('Outgoing Player ID: ' + $(this).data('playerid'));

					var draggedImg = ui.draggable.find('img');
					var droppedImg = $(this).find('img');

					//swap img src
					var srcHolder = draggedImg.attr('src');
					draggedImg.attr('src', droppedImg.attr('src'));
					droppedImg.attr('src', srcHolder);

					//swap playerid attribute
					var playerIdHolder = ui.draggable.data('playerid');
					ui.draggable.data('playerid', $(this).data('playerid'));
					$(this).data('playerid', playerIdHolder);
					
					//swap img playerid attribute
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
					//console.log('Incoming Player ID: ' + ui.draggable.data('playerid'));
					//console.log('Outgoing Player ID: ' + $(this).data('playerid'));

					var draggedImg = ui.draggable.find('img');
					var droppedImg = $(this).find('img');

					//swap img src
					var srcHolder = draggedImg.attr('src');
					draggedImg.attr('src', droppedImg.attr('src'));
					droppedImg.attr('src', srcHolder);

					//swap playerid attribute
					var playerIdHolder = ui.draggable.data('playerid');
					ui.draggable.data('playerid', $(this).data('playerid'));
					$(this).data('playerid', playerIdHolder);
					
					//swap img playerid attribute
					var imagePlayerIdHolder = draggedImg.data('playerid');
					draggedImg.data('playerid', droppedImg.data('playerid'));
					droppedImg.data('playerid', imagePlayerIdHolder);
				}
			});
		});
	</script>
	
	<script>
		$('#defaultModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var action = button.data('action');
			var modal = $(this);
			modal.find('#actionButton').attr("href", action);
		})
	</script>
	
</body>
</html>