<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<html>
	<head>
		<script type="text/javascript" src="/_ah/channel/jsapi"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>

		<!-- 
			Initialize the Google Maps JS control
			TODO: replace the Key with the correct Key when ready
		 -->
	   <script type="text/javascript"
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhsW91k1kk72HSr4Qqk-LxhJHfHF7IYdA">
		</script>
		<script type="text/javascript">
			function initialize() {
				var mapOptions = {
				  center: { lat: -34.397, lng: 150.644},
				  zoom: 8
				};
			  	var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
			}
			google.maps.event.addDomListener(window, 'load', initialize);
		</script>
 
	</head>
	<body>
		<script>
			var channel;
			function startMonitoringRoute() {
				channel = goog.appengine.channel("${channelID}");
				var socket = channel.open();
				socket.onopen = socket_onOpen;
				socket.onmessage = socket_onMessage;
			    socket.onerror = socket_onError;
			    socket.onclose = socket_onClose;				
			}
			
			// sendRouteToTrack: send back the ID of the Route user wants to track
			function sendRouteToTrack(routeID) {
				var path = "" + routeID;
				var xhr = new XMLHttpRequest();
				xhr.open('POST', path, true);
				xhr.send();
			}
			
		    // socket_onOpen: 
		   	socket_onOpen = function() {
		    	
		    }
		    
		    socket_onMessage() = function(locationMessage) {
		    	
		    }
		</script>
		
		
		<div id="map-canvas"></div>
		
	</body>
</html>