$(document).ready(function () {
    longPolling();
    initIsFollowed = $("#follow").attr("data-is-followed");
    if (initIsFollowed == "true") {
        $("#follow").html("已关注");
    } else {
        $("#follow").html("关注Ta");
    }

})

window.onbeforeunload = function(event){
    var url = "/douban/endWaitForChat";
    $.get(url, function(data,status){
    })
}

function follow() {
    var isFollowed = $("#follow").attr("data-is-followed");
    var url;
    var toUserId = GetQueryString("userId");
    if (isFollowed == "false") {
        $("#follow").html("已关注");
        $("#follow").attr("data-is-followed", "true");
        url = "/douban/follow";
    } else {
        $("#follow").html("关注ta");
        $("#follow").attr("data-is-followed", "false");
        url = "/douban/cancelFollow";
    }
    url = url + "?toUserId=" + toUserId;
    $.get(url, function (data, status) {
        window.location.reload();
    })
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}

function openchat(){

}