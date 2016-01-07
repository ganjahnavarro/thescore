<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>
	<div id="slides">
		<jsp:include page="slider.jsp"></jsp:include>
	</div>
	<br />

	<div class="row">
		<div class="col-md-7">
			<div class="row">
				<div class="col-xs-6 col-sm-4">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/league/list' />">
								<img src="<c:url value="/resources/images/league.png" />" alt="Leagues">
							</a>
							<p class="launcher-label">Leagues</p>
						</div>
					</div>
					
					<div class="clearfix"></div>
				</div>
		
				<div class="col-xs-6 col-sm-4">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/match/list' />">
								<img src="<c:url value="/resources/images/match.png" />" alt="Matches">
							</a>
							<p class="launcher-label">Matches</p>
						</div>
					</div>
					
					<div class="clearfix"></div>
				</div>
		
				<div class="col-xs-6 col-sm-4">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/team/list' />">
								<img src="<c:url value="/resources/images/team.png" />" alt="Teams">
							</a>
							<p class="launcher-label">Teams</p>
						</div>
					</div>
				</div>
		
				<div class="col-xs-6 col-sm-4">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/player/list' />">
								<img src="<c:url value="/resources/images/player.png" />" alt="Players">
							</a>
							<p class="launcher-label">Players</p>
						</div>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/match/calendar' />">
								<img src="<c:url value="/resources/images/calendar.png" />" alt="Calendar">
							</a>
							<p class="launcher-label">Schedule</p>
						</div>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/topic/' />">
								<img src="<c:url value="/resources/images/forum.png" />" alt="Forum">
							</a>
							<p class="launcher-label">Forum</p>
						</div>
					</div>
				</div>
				
				<sec:authorize access="hasRole('COMMITTEE')">
					<div class="col-xs-6 col-sm-4">
						<div class="thumbnail">
							<div class="launcher-content">
								<a href="<c:url value='/newsfeed/' />">
									<img src="<c:url value="/resources/images/newsfeed.png" />" alt="Newsfeed">
								</a>
								<p class="launcher-label">Newsfeed</p>
							</div>
						</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasRole('HEAD_COMMITTEE')">	
					<div class="col-xs-6 col-sm-4">
						<div class="thumbnail">
							<div class="launcher-content">
								<a href="<c:url value='/user/' />">
									<img src="<c:url value="/resources/images/user.png" />" alt="Newsfeed">
								</a>
								<p class="launcher-label">Users</p>
							</div>
						</div>
					</div>
					
					<div class="col-xs-6 col-sm-4">
						<div class="thumbnail">
							<div class="launcher-content">
								<a href="<c:url value='/user/committees' />">
									<img src="<c:url value="/resources/images/committee.png" />" alt="Newsfeed">
								</a>
								<p class="launcher-label">Committees</p>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
		
		<div class="col-md-5">
			<jsp:include page="../topic/table.jsp" />
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".indicator").first().addClass("active");
		    $(".carousel-item").first().addClass("active");
			
			$(function(){
			    $('.carousel').carousel({
			      interval: 5000
			    });
			});
		});
	</script>

</t:template>