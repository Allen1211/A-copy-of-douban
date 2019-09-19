
function show(){
	$("#hidebg").css("display","block");
	$("#hidebg").css("height",document.body.clientHeight+"px");
	$("#info-edit-wrap").css("display","block");
}

function hide(){
	$("#hidebg").css("display","none");
	$("#info-edit-wrap").css("display","none");
}

function save(){
	var nickname = $("#edit-nickname").val();
	var email = $("#edit-email").val();
	var desc = $("#edit-desc").val();
	var file = $("#head-img")[0].src;
	if($("#is-file-chosen").val() == "false"){
		file = "";
	}
	if(nickname == null || nickname ==""){
		alert("昵称不能为空");
	}
	if(email == null || email == ""){
		alert("邮箱不能为空");
	}
	if(desc == null || desc == ""){
		alert("个人简介不能为空")
	}
	$.ajax({
		url: "/douban/editUser",
		type: "POST",
		cache: false,
		data: JSON.stringify({
			"img": file,
			"nickname": nickname,
			"email": email,
			"desc" : desc
		}),
		dataType: "json",
		contentType: "application/json;charset=utf-8",
		success: function(data){
			if(data.code == 200){
				hide();
				var s = $("#user-head-img").src;
				$("#user-head-img").src = s + Math.random();
				window.location.reload();
			}else{
				alert(data.msg);
			}
		}
	});
}

function showImg(obj){
	var file = obj.files[0];
	var fileName = obj.value;
	var index = fileName.lastIndexOf(".") + 1;
	var fileType = fileName.substr(index).toLowerCase();
	var fileSize = file.size;
	if((fileSize / 1024) > 200){
		obj.value = "";
		alert("只能上传200kb以内的文件");
		return;
	}
	if(fileType != "jpg" && fileType != "jpeg"){
		obj.value = "";
		alert("只能选择jpg图片");
		return;
	}
	
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onloadstart = function(e){
		reader.onload = function(e){
		$("#head-img")[0].src = this.result;
		$("#is-file-chosen").val("true");
		}
	}
}