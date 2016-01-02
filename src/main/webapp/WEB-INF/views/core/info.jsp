<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-6">
	<div class="well">
		<div class="row">
			<div class="table-responsive col-sm-6">
				<table class="table table-hover table-striped">
					<tr>
						<td></td>
						<td>Team A</td>
						<td>Team B</td>
					</tr>
					
					<tr>
						<td class="info-label">Field Goals</td>
						<td id="fg-a"><c:out value="${teamPerformanceA.fg}"/> / <c:out value="${teamPerformanceA.fga}"/></td>
						<td id="fg-b"><c:out value="${teamPerformanceB.fg}"/> / <c:out value="${teamPerformanceB.fga}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">3 Point Field Goals</td>
						<td id="threefg-a"><c:out value="${teamPerformanceA.threefg}"/> / <c:out value="${teamPerformanceA.threefga}"/></td>
						<td id="threefg-b"><c:out value="${teamPerformanceB.threefg}"/> / <c:out value="${teamPerformanceB.threefga}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Free Throws</td>
						<td id="ft-a"><c:out value="${teamPerformanceA.ft}"/> / <c:out value="${teamPerformanceA.fta}"/></td>
						<td id="ft-b"><c:out value="${teamPerformanceB.ft}"/> / <c:out value="${teamPerformanceB.fta}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Assists</td>
						<td id="ast-a"><c:out value="${teamPerformanceA.ast}"/></td>
						<td id="ast-b"><c:out value="${teamPerformanceB.ast}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Steals</td>
						<td id="stl-a"><c:out value="${teamPerformanceA.stl}"/></td>
						<td id="stl-b"><c:out value="${teamPerformanceB.stl}"/></td>
					</tr>
				</table>
			</div>
			
			<div class="table-responsive col-sm-6">
				<table class="table table-hover table-striped">
					<tr>
						<td></td>
						<td>Team A</td>
						<td>Team B</td>
					</tr>
					
					<tr>
						<td class="info-label">Blocks</td>
						<td id="blk-a"><c:out value="${teamPerformanceA.blk}"/></td>
						<td id="blk-b"><c:out value="${teamPerformanceB.blk}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Defensive Rebounds</td>
						<td id="def-a"><c:out value="${teamPerformanceA.def}"/></td>
						<td id="def-b"><c:out value="${teamPerformanceB.def}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Offensive Rebounds</td>
						<td id="off-a"><c:out value="${teamPerformanceA.off}"/></td>
						<td id="off-b"><c:out value="${teamPerformanceB.off}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Turnovers</td>
						<td id="to-a"><c:out value="${teamPerformanceA.to}"/></td>
						<td id="to-b"><c:out value="${teamPerformanceB.to}"/></td>
					</tr>
					
					<tr>
						<td class="info-label">Fouls</td>
						<td id="foul-a"><c:out value="${teamPerformanceA.foul}"/></td>
						<td id="foul-b"><c:out value="${teamPerformanceB.foul}"/></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>