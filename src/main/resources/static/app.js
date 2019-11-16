var stompClient = null;
var token = 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTczOTMzNTc3LCJpc3MiOiJpby5hc2NlbmRpbmciLCJleHAiOjE1NzQwMTk5NzcsImFsbG93ZWRSZWFkUmVzb3VyY2VzIjoiL2FwaS91c2Vycy91c2VyLC9hcGkvcGFja3MvcGFjayIsImFsbG93ZWRDcmVhdGVSZXNvdXJjZXMiOiIiLCJhbGxvd2VkVXBkYXRlUmVzb3VyY2VzIjoiIiwiYWxsb3dlZERlbGV0ZVJlc291cmNlcyI6IiJ9.GOI5mYBK32sfgCVPXC-_ftX8XGsEFLzNnQeVfllfieI';

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8080/myWebsocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('http://localhost:8080/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("http://localhost:8080/app/sayHello", JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});