function longPolling() {
    var timeLimit = 60000;
    $.ajax({
        method:"POST",
        url: "/douban/waitForChat",
        data : JSON.stringify({
            "userId": $("#user-id").attr("data-user-id"),
            "timeLimit": timeLimit
        }),
        dataType: "json",
        timeout: timeLimit,
        contentType: 'application/json; charset=UTF-8',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus);
            if (textStatus == "timeout") { // 请求超时
                longPolling();
            } else {
                // longPolling();
            }

        },
        success: function (data) {
            if(data.code == 200){
                var callerId = data.data.callerId;
                var name = data.data.callerName;
                var choice = confirm(name+"请求与你私聊，按确定进入私聊");
                if (choice == true) {
                    window.location.href="/douban/requireChat?receiverId="+callerId;
                }
            }else{
                // console.log("timeout")
                longPolling();
            }
        }
    });
}