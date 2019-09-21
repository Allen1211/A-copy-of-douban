
var target = "ws://"+window.location.host+"/douban/chatting/";
var callerId ;
var receiverId ;
var socket;
$(document).ready(function(){
    callerId = $("#user-id").attr("data-caller-id");
    receiverId = $("#user-id").attr("data-receiver-id");
    target = target +callerId + "_" + receiverId;
    socket = new WebSocket(target);
    socket.onmessage = receive;
})
// ajax send the message to server socket
function send(){
    var msg = $("#edit-area").html();
    if(!msg || msg.length===0){
        alert("发表信息不能为空");
        return;
    }
    //send the message to server socket and then wait for success response
    socket.send(JSON.stringify({
        "msg" : msg,
        "receiverId": receiverId
    }));

    showMessage(msg,true);
    $("#edit-area").html("");
    var scrollHeight = $('#message-box').prop("scrollHeight");
    $('#message-box').animate({scrollTop:scrollHeight}, 400);
}

function showMessage(msg, isSend){
    var row;
    if(isSend){
        row = '<li style="float:right"><span class="message">'+msg+'</span><img src="/douban/getUserImage?userId='+callerId+'" class="user-head"></li>';
    }else{
        row = '<li><img src="/douban/getUserImage?userId='+receiverId+'" class="user-head"><span class="message">'+msg+'</span></li>';
    }
    $("#message-ul").append(row);
}

function receive(event){
    var data = JSON.parse(event.data);
    showMessage(data.msg, false);
}

function quit() {
    socket.close();
    window.location.href="http://localhost:8080/douban/userpage?userId="+receiverId;
}