var ws = new WebSocket("ws://localhost:8085/ws");
var stompClient = Stomp.over(ws);

stompClient.connect({}, function(frame){})