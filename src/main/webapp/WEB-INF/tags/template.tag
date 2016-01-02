<%@ tag description="Template Tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>
	<title>Basketball Statistics System</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-timepicker-addon/1.6.1/jquery-ui-timepicker-addon.min.css">
	
	<link rel="stylesheet" href="<c:url value="/resources/css/styles.css" />">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.js"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-timepicker-addon/1.6.1/jquery-ui-timepicker-addon.min.js"></script>
</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="<c:url value='/home' />">Basketball Statistics Application</a>
			</div>
			
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test="${not empty userName}">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
									<c:out value="${userName}"/>
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a href="<c:url value='/logout' />">Logout</a></li>
								</ul>
							</li>
						</c:when>
						
						<c:otherwise>
							<li><a href="<c:url value='/register'/>"><strong>Register</strong></a></li>
							<li><a href="<c:url value='/login' />">Login</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Body -->
	<div class="container">
		<!-- Message -->
		<c:choose>
			<c:when test="${not empty infoMessage}">
				<div class="alert alert-success alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<c:out value="${infoMessage}"></c:out>
				</div>
			</c:when>
			
			<c:when test="${not empty errorMessage}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<c:out value="${errorMessage}"></c:out>
				</div>
			</c:when>
		</c:choose>
	
		<jsp:doBody />
	</div>

	<!-- Modal -->
	<div id="defaultModal" class="modal fade bs-example-modal-sm"
		tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p id="modal-title" class="modal-title modal-margin-bottom">Are you sure you want to delete this record?</p>
					<a id="actionButton">
						<button type="button" class="btn btn-danger">Confirm</button>
					</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		$('#defaultModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var action = button.data('action');
			var message = button.data('message');
			
			var modal = $(this);
			
			if(message){
				modal.find('#modal-title').text(message);
			}
			
			modal.find('#actionButton').attr("href", action);
		})
	</script>
</body>
</html>
