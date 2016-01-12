<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="footer">
	<div class="container-fluid">
		<div id="quarter-selection-panel" class="col-md-4">
			<c:if test="${allowedStatisticsModification}">
				<p class="footer-score">Quarter</p>
				
				<div id="quarters-holder" class="footer-score">
					<span class="quarter-selection-item" data-quarter="1">1st </span>
					<span class="quarter-selection-item" data-quarter="2">2nd </span>
					<span class="quarter-selection-item" data-quarter="3">3rd </span>
					<span class="quarter-selection-item" data-quarter="4">4th </span>
					
					<c:forEach items="${teamPerformanceA.quarterScores}" var="entry" varStatus="loop">
						<c:if test="${loop.index} > 4">
							<span class="quarter-selection-item" data-quarter="${loop.index}">OT${loop.index - 4}</span>
						</c:if>
					</c:forEach>
					
					<a id="add-quarter">
						<button type="button" class="close" aria-label="Close"><span aria-hidden="true">+</span></button>
					</a>
				</div>
			</c:if>
		</div>
		
		<div>
			<div class="col-md-4">
				<p class="footer-score">${match.teamA.code}</p>
				
				<div class="footer-score">
					<c:forEach items="${teamPerformanceA.quarterScores}" var="entry">
						<span id="qtr-${entry.key}-a">${entry.value}</span>
					</c:forEach>
					
					<span id="qtr-score-a" class="hidden"></span>
				</div>
			</div>
			
			<div class="col-md-4">
				<p class="footer-score">${match.teamB.code}</p>
				
				<div class="footer-score">
					<c:forEach items="${teamPerformanceB.quarterScores}" var="entry">
						<span id="qtr-${entry.key}-b">${entry.value}</span>
					</c:forEach>
					
					<span id="qtr-score-b" class="hidden"></span>
				</div>
			</div>
		</div>
	</div>
</div>