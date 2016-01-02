<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:if test="${allowedStatisticsModification}">
	<div class="col-md-6">
		<div class="well">
			<div class="row">
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="FG">FG</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="FG" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="FGA" data-missed="true">FGA</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="FGA" data-missed="true" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="3FG">3FG</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="3FG" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="3FGA" data-missed="true">3FGA</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="3FGA" data-missed="true" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="FT">FT</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="FT" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="FTA" data-missed="true">FTA</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="FTA" data-missed="true" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="AST">AST</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="AST" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="STL">STL</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="STL" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="BLK">BLK</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="BLK" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="DEF">DEF</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="DEF" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="OFF">OFF</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="OFF" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="TO">TO</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="TO" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="btn-group action-item-group" role="group">
						<button type="button" class="btn btn-primary action-item main"
							data-action="FOUL">FOUL</button>
						
						<sec:authorize access="hasRole('HEAD_COMMITTEE')">
							<button type="button" class="btn btn-primary action-item sub"
								data-action="FOUL" data-subtract="true">-</button>
						</sec:authorize>
					</div>
				</div>
				
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</c:if>