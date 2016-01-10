$(function() {
	"use strict";

	var playerId = null;
	var selectedQuarter = null;
	var quarterCount = 4;
	
	var socket = $.atmosphere;

	var request = {
		url : '/thescore/core/atmosphere',
		contentType : "application/json",
		logLevel : 'debug',
		transport : 'websocket',
		fallbackTransport : 'long-polling'
	};

	request.onOpen = function(response) {
		console.log('Atmosphere connected using ' + response.transport);
	};

	request.onTransportFailure = function(errorMsg, request) {
		jQuery.atmosphere.info(errorMsg);
		if (window.EventSource) {
			request.fallbackTransport = "sse";
		}
	};

	request.onReconnect = function(request, response) {
		socket.info("Reconnecting")
	};

	request.onMessage = function(response) {
		var message = response.responseBody;
		try {
			var reply = jQuery.parseJSON(message);
		} catch (e) {
			console.log('This doesn\'t look like a valid JSON: ', message);
			return;
		}
		
		var draggedImg = $('#player-' + reply.fromPlayerId).find('img');
		var droppedImg = $('#player-' + reply.toPlayerId).find('img');
		
		var srcHolder = draggedImg.attr('src');
		draggedImg.attr('src', droppedImg.attr('src'));
		droppedImg.attr('src', srcHolder);
		
		var playerIdHolder = $('#player-' + reply.fromPlayerId).data('playerid');
		$('#player-' + reply.fromPlayerId).data('playerid', $('#player-' + reply.toPlayerId).data('playerid'));
		$('#player-' + reply.toPlayerId).data('playerid', playerIdHolder);
		
		var imagePlayerIdHolder = draggedImg.data('playerid');
		draggedImg.data('playerid', droppedImg.data('playerid'));
		droppedImg.data('playerid', imagePlayerIdHolder);
		
		var draggedNumber = $('#player-' + reply.fromPlayerId).find('span').html();
		var droppedNumber = $('#player-' + reply.toPlayerId).find('span').html();
		$('#player-' + reply.fromPlayerId).find('span').html(droppedNumber);
		$('#player-' + reply.toPlayerId).find('span').html(draggedNumber);
	};

	request.onClose = function(response) {

	}

	request.onError = function(response) {
		console.log('Sorry, but there\'s some problem with your socket or the server is down');
	};

	var subSocket = socket.subscribe(request);
	
	$("#quarters-holder").on("click", "#add-quarter", function(){
		quarterCount = quarterCount + 1;
		$('#add-quarter').before('<span class="quarter-selection-item" data-quarter="' + quarterCount + '">OT' + (quarterCount - 4) + '</span>');
	});
	
	$('.time-out-item').click(function(event) {
		var matchId = $('#matchId').html();
		
		var target = $(event.target);
		var teamIdParam = target.data('teamid');
		
		if(matchId != null && teamIdParam != null){
			subSocket.push(jQuery.stringifyJSON({
				action : 'TIMEOUT',
				teamId : teamIdParam,
				matchId : matchId
			}));
		}
	});
	
	$('.player-selection-item').click(function(event) {
		var target = $(event.target);
		var playerIdParam = target.data('playerid');
		playerId = playerIdParam;
		
        $('.player-selection-img').css("border-color", "gainsboro");
        target.css("border-color", "dodgerblue");
	});
	
	$('#quarter-selection-panel').on('click', '.quarter-selection-item', function(event) {
		var target = $(event.target);
		var selectedQuarterParam = target.data('quarter');
		selectedQuarter = selectedQuarterParam;
		
		$('.quarter-selection-item').css("color", "slategray");
		target.css("color", "dodgerblue");
		
		$('#current-quarter').html(target.html());
	});
	
	$('.action-item').click(function(event) {
		var matchId = $('#matchId').html();
		
		var target = $(event.target);
		var actionParam = target.data('action');
		var missedParam = target.data('missed');
		var subtractParam = target.data('subtract');
		
		missedParam = missedParam == null ? false : missedParam;
		subtractParam = subtractParam == null ? false : subtractParam;
		
		if(playerId == null){
			alert("No player selected.");
			return;
		}
		
		if(selectedQuarter == null){
			alert("No quarter selected.");
			return;
		}
		
		if(matchId != null && actionParam != null){
			subSocket.push(jQuery.stringifyJSON({
				playerId : playerId,
				matchId : matchId,
				action : actionParam,
				missed : missedParam,
				subtract : subtractParam,
				quarter : selectedQuarter
			}));
		}
	});
	
	$('.draggable-a').draggable({
		revert : true,
		stack : ".draggable-a"
	});
	
	$('.droppable-a').droppable({
		accept : ".draggable-a",
		drop : function(event, ui) {
			var matchId = $('#matchId').html();
			
			var fromPlayerId = ui.draggable.data('playerid');
			var toPlayerId = $(this).data('playerid');
			
			if(fromPlayerId != null && toPlayerId != null){
				subSocket.push(jQuery.stringifyJSON({
					matchId : matchId,
					action : 'SUBSTITUTION',
					fromPlayerId : fromPlayerId,
					toPlayerId : toPlayerId,
				}));
			}
		}
	});
	
	$('.draggable-b').draggable({
		revert : true,
		stack : ".draggable-b"
	});
	
	$('.droppable-b').droppable({
		accept : ".draggable-b",
		drop : function(event, ui) {
			var draggedImg = ui.draggable.find('img');
			var droppedImg = $(this).find('img');

			var srcHolder = draggedImg.attr('src');
			draggedImg.attr('src', droppedImg.attr('src'));
			droppedImg.attr('src', srcHolder);

			var playerIdHolder = ui.draggable.data('playerid');
			ui.draggable.data('playerid', $(this).data('playerid'));
			$(this).data('playerid', playerIdHolder);
			
			var imagePlayerIdHolder = draggedImg.data('playerid');
			draggedImg.data('playerid', droppedImg.data('playerid'));
			droppedImg.data('playerid', imagePlayerIdHolder);
			
			var draggedNumber = ui.draggable.find('span').html();
			var droppedNumber = $(this).find('span').html();
			ui.draggable.find('span').html(droppedNumber);
			$(this).find('span').html(draggedNumber);
		}
	});

});
