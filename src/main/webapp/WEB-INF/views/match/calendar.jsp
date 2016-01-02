<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.5.0/fullcalendar.min.css">

	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.5.0/fullcalendar.min.js"></script>
	
	<script>
		$(document).ready(function() {
			var calendar = $('#calendar'); 
			
			calendar.fullCalendar({
				header : {
					left : 'prev, next today',
					center : 'title',
					right : 'month, agendaWeek, agendaDay'
				},
				
				eventClick: function(event) {
			        if (event.url) {
			            window.open(event.url, "_self");
			            return false;
			        }
			    }
			});
			
			var matches = new Array();
			
			<c:forEach items="${matches}" var="match">
				matches.push({
					id : '${match.id}',
					description : '${match.teamA.code} VS. ${match.teamB.code}',
					date : '${match.time}',
					url : '<c:url value="/match/view-${match.id}-match" />'
				});
			</c:forEach>

			for (i = 0; i < matches.length; i++) {
				var ev = new Object();
				ev.title = matches[i].description;
				ev.start = matches[i].date;
				ev.url = matches[i].url;
				ev.allDay = false;
				$('#calendar').fullCalendar('renderEvent', ev, true);
			}
		});
	</script>
	
	<div id="calendar"></div>

</t:template>