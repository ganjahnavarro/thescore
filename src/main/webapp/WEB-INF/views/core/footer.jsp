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
					<span id="qtr-1-a">22 </span>
					<span id="qtr-2-a">27 </span>
					<span id="qtr-3-a">24 </span>
					<span id="qtr-4-a">26 </span>
				</div>
			</div>
			
			<div class="col-md-4">
				<p class="footer-score">${match.teamB.code}</p>
				
				<div class="footer-score">
					<span id="qtr-1-b">28 </span>
					<span id="qtr-2-b">23 </span>
					<span id="qtr-3-b">18 </span>
					<span id="qtr-4-b">25 </span>
				</div>
			</div>
		</div>
	</div>
</div>