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
			var jsonArray = jQuery.parseJSON(message);
		} catch (e) {
			console.log('This doesn\'t look like a valid JSON: ', message);
			return;
		}
		
		var keys = ["", "a", "b"];
		
		for(i in jsonArray){
			$('#fg-' + keys[i]).html(jsonArray[i].fg + " / " + jsonArray[i].fga);
			$('#threefg-' + keys[i]).html(jsonArray[i].threefg + " / " + jsonArray[i].threefga);
			$('#ft-' + keys[i]).html(jsonArray[i].ft + " / " + jsonArray[i].fta);
			$('#ast-' + keys[i]).html(jsonArray[i].ast);
			$('#stl-' + keys[i]).html(jsonArray[i].stl);
			$('#blk-' + keys[i]).html(jsonArray[i].blk);
			$('#def-' + keys[i]).html(jsonArray[i].def);
			$('#off-' + keys[i]).html(jsonArray[i].off);
			$('#to-' + keys[i]).html(jsonArray[i].to);
			$('#foul-' + keys[i]).html(jsonArray[i].foul);
			
			$('#score-' + keys[i]).html(jsonArray[i].score);
			$('#timeout-' + keys[i]).html('Timeout/s: ' + jsonArray[i].timeout);
		}
	};

	request.onClose = function(response) {

	}

	request.onError = function(response) {
		console.log('Sorry, but there\'s some problem with your socket or the server is down');
	};

	var subSocket = socket.subscribe(request);
	
	$("#quarters-holder").on("click", "#add-quarter", function(){
		quarterCount = quarterCount + 1;
		$('#add-quarter').before('<span class="quarter-selection-item" data-quarter="' + quarterCount + '"> OT' + (quarterCount - 4) + '</span>');
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

});
