<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:template>

	<div class="row">
		<div class="col-md-8">
			<div class="col-sm-6 col-md-4 col-lg-3">
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
	
			<div class="col-sm-6 col-md-4 col-lg-3">
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
	
			<div class="col-sm-6 col-md-4 col-lg-3">
				<div class="thumbnail">
					<div class="launcher-content">
						<a href="<c:url value='/team/list' />">
							<img src="<c:url value="/resources/images/team.png" />" alt="Teams">
						</a>
						<p class="launcher-label">Teams</p>
					</div>
				</div>
			</div>
	
			<div class="col-sm-6 col-md-4 col-lg-3">
				<div class="thumbnail">
					<div class="launcher-content">
						<a href="<c:url value='/player/list' />">
							<img src="<c:url value="/resources/images/player.png" />" alt="Players">
						</a>
						<p class="launcher-label">Players</p>
					</div>
				</div>
			</div>
			
			<div class="col-sm-6 col-md-4 col-lg-3">
				<div class="thumbnail">
					<div class="launcher-content">
						<a href="<c:url value='/match/calendar' />">
							<img src="<c:url value="/resources/images/calendar.png" />" alt="Calendar">
						</a>
						<p class="launcher-label">Schedule</p>
					</div>
				</div>
			</div>
			
			<div class="col-sm-6 col-md-4 col-lg-3">
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
				<div class="col-sm-6 col-md-4 col-lg-3">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/newsfeed/' />">
								<img src="<c:url value="/resources/images/newsfeed.png" />" alt="Newsfeed">
							</a>
							<p class="launcher-label">Newsfeed</p>
						</div>
					</div>
				</div>
				
				<div class="col-sm-6 col-md-4 col-lg-3">
					<div class="thumbnail">
						<div class="launcher-content">
							<a href="<c:url value='/user/' />">
								<img src="<c:url value="/resources/images/user.png" />" alt="Newsfeed">
							</a>
							<p class="launcher-label">Users</p>
						</div>
					</div>
				</div>
				
				<div class="col-sm-6 col-md-4 col-lg-3">
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
		
		<div class="col-md-4">
			<div class="well">
				<jsp:include page="../newsfeed/table.jsp" />
			</div>
		</div>
	</div>

</t:template>