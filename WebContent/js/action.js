/**
 * 
 */

$(document).ready(function(){
	initIsLiked = $("#like").attr("data-isLiked");
	initIsForwarded = $("#forward").attr("data-isForwarded");
	initIsCollected = $("#collect").attr("data-isCollected");
	if(initIsLiked == "true"){
		$("#like").html("取消点赞");
	}
	if(initIsForwarded == "true"){
		$("#forward").attr("href","#");
		$("#forward").html("已转发");
	}
	if(initIsCollected == "true"){
		$("#collect").attr("href","#");
		$("#collect").html("已收藏");
	}
	
});

$(window).on("beforeunload",function() {
	var isLiked = $("#like").attr("data-isLiked");
	if(isLiked != initIsLiked){
		var articleId = $("#like-data").attr("data-articleId");
		var userId = $("#like-data").attr("data-userId");
		var url = "/douban/like?likeType=1&targetId="+articleId+"&userId="+userId;
		$.get(url,function(data,status){
			console.log(data);
		});
	}

	
});

function like() {
	var isLiked = $("#like").attr("data-isLiked");

	if (isLiked == "false") {
		var oldCount = $("#likeCount").html();
		var newCount = parseInt(oldCount) + 1;
		$("#likeCount").html(newCount);
		$("#like").html("取消点赞");
		$("#like").attr("data-isLiked", "true");
	} else {
		var oldCount = $("#likeCount").html();
		var newCount = parseInt(oldCount) - 1;
		$("#likeCount").html(newCount);
		$("#like").html("点赞");
		$("#like").attr("data-isLiked", "false");
	}
}

