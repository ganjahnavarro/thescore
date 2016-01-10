<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="slider" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<c:forEach items="${newsfeeds}" var="newsfeed" varStatus="loop">
			<li data-target="#slider" data-slide-to="${loop.index}" class="indicator"></li>
		</c:forEach>
	</ol>

	<!-- Wrapper for slides -->
	<div class="carousel-inner" role="listbox">
		<c:forEach items="${newsfeeds}" var="newsfeed">
			<div class="item carousel-item">
				<c:choose>
					<c:when test="${not empty newsfeed.image}">
						<a href="<c:url value='/newsfeed/view-${newsfeed.id}-newsfeed' />">
							<img src="<c:url value='/slider/image?id=${newsfeed.id}'/>" alt="${newsfeed.title}">
						</a>
					</c:when>
					
					<c:otherwise>
						<a href="<c:url value='/newsfeed/view-${newsfeed.id}-newsfeed' />">
							<img src="<c:url value="/resources/images/placeholder.png" />"/>
						</a>
					</c:otherwise>
				</c:choose>
				<div class="carousel-caption">
					<a href="<c:url value='/newsfeed/view-${newsfeed.id}-newsfeed' />">
						${newsfeed.title}
					</a>
					
					<a class="carousel-description" href="<c:url value='/newsfeed/view-${newsfeed.id}-newsfeed' />">
						${newsfeed.description}
					</a>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- Left and right controls -->
	<a class="left carousel-control" href="#slider" role="button" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		<span class="sr-only">Previous</span>
	</a>
	
	<a class="right carousel-control" href="#slider" role="button" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		<span class="sr-only">Next</span>
	</a>
</div>