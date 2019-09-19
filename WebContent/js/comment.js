$(document).ready(function(){
	var arr = $(".comment-like");
	initIsLikedArr = [];
	for (var i = 0; i < arr.length; i++) {
		var isLiked = $(arr[i]).attr("data-isLiked");
		initIsLikedArr.push(isLiked);
		if(isLiked == "true"){
			$(arr[i]).html("取消点赞");
		}
	}
})

$(window).on("beforeunload",function() {
	var arr = $(".comment-like");
	for(var i = 0; i < arr.length ; i++){
		if($(arr[i]).attr("data-isLiked") != initIsLikedArr[i]){
			var commentId = $(arr[i]).siblings("input").attr("data-commentId");
			var userId = $("#like-data").attr("data-userId");
			var url = "/douban/like.do?likeType=2&targetId="+commentId+"&userId="+userId;
			$.get(url,function(data,status){
				console.log(data);
			});
		}
	}
});

function commentLike(obj) {
	var isLiked = $(obj).attr("data-isLiked");

	if (isLiked == "false") {
		var oldCount = $(obj).siblings(".comment-likeCount").html();
		var newCount = parseInt(oldCount) + 1;
		$(obj).siblings(".comment-likeCount").html(newCount);
		$(obj).html("取消点赞");
		$(obj).attr("data-isLiked", "true");
	} else {
		var oldCount = $(obj).siblings(".comment-likeCount").html();
		var newCount = parseInt(oldCount) - 1;
		$(obj).siblings(".comment-likeCount").html(newCount);
		$(obj).html("点赞");
		$(obj).attr("data-isLiked", "false");
	}
}

function showReply(obj){
	if($(obj).html()=="收起回复"){
		$(obj).html("查看回复");
	}else{
		$(obj).html("收起回复");
	}
	$(obj).parent().parent().siblings(".reply-wrap").slideToggle("fast");
}

function addComment(){
	var text = $("#comment-editor").val();
	var articleId = $("#article-data").attr("data-articleId");
	
	if(text == null || text == ""){
		alert("评论不能为空");
		return;
	}
	
	$.ajax({
		url : "/douban/addComment",
		type : "POST",
		data : JSON.stringify({
			"text" : text,
			"articleId" : articleId
		}),
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function(data) {
			if (data.code == 200) {
				window.location.reload();
			} else {
				alert(data.msg);
			}
		}
	});
}

function showReplyEditor(obj){
	$("body").css("overflow","hidden");
	$("#hidebg").css("display","block");
	$("#hidebg").css("height",document.body.clientHeight+"px");
	$("#reply-edit-wrap").css("display","block");
	var id = $(obj).siblings(".reply-data").attr("id");
	$("#reply-data-edit").attr("data-id",id);
}

function hideReplyEditor(){
	$("body").css("overflow","auto");
	$("#hidebg").css("display","none");
	$("#reply-edit-wrap").css("display","none");
}

function addReply(obj){
	var dataId = $("#reply-data-edit").attr("data-id");
	
	var commentId = $("#"+dataId).attr("data-commentId");
	var toReplyId = $("#"+dataId).attr("data-toReplyId");
	var toUserId = $("#"+dataId).attr("data-toUserId");
	var replyType = $("#"+dataId).attr("data-replyType");
	var text = $("#reply-text").val();
	
	if(text == null || text==""){
		alert("回复内容不能为空");
		return;
	}
	
	$.ajax({
		url : "/douban/addReply",
		type : "POST",
		data : JSON.stringify({
			"text" : text,
			"commentId":commentId,
			"toReplyId":toReplyId,
			"toUserId": toUserId,
			"replyType" : replyType
		}),
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function(data) {
			if (data.code == 200) {
				window.location.reload();
			} else {
				alert(data.msg);
			}
		}
	});
}
