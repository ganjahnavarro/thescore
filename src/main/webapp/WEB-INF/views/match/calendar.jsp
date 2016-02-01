<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <link rel="stylesheet" href="<c:url value="/resources/plugins/fullcalendar.min.css" />">
	
	<script src="<c:url value="/resources/plugins/moment.min.js" />"></script>
	<script src="<c:url value="/resources/plugins/fullcalendar.min.js" />"></script>

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