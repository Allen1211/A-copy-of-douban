$(document).ready(function() {
	var mode = getParam("mode");
	if (mode == "EDIT") {
		var id = $("#hidden-data").attr("data-id");
		$.ajax({
			url : "/douban/getArticleSelectedType?articleId=" + id,
			async: "false",
			type : "GET",
			dataType : "json",
			success : function(data) {
				$("input[name=type]").each(function() {
					for (var i = 0; i < data.data.length; i++) {
						console.log(data.data[i].typeId);
						if ($(this).val() == data.data[i].typeId) {
							$(this).attr("checked", true);
							continue;
						}
					}
				});
			}
		});
	}

	$("button").click(function() {
		if ($(this).attr("on") === "0" || !$(this).attr("on")) {
			$(this).attr("on", "1");
			$(this).css("background", "gray");
		} else {
			$(this).attr("on", "0");
			$(this).css("background", "inherit");
		}
	})
});

function getParam(paramName) {
	var url = document.location.toString();
	var arrObj = url.split("?");
	if (arrObj.length >= 1) {
		var arrPara = arrObj[1].split("&");
		var arr;

		for (var i = 0; i < arrPara.length; i++) {
			arr = arrPara[i].split("=");

			if (arr != null && arr[0] == paramName) {
				return arr[1];
			} else {
				return ""
			}
		}

	} else {
		return "";
	}
}

function setBold() {
	document.execCommand("bold", false);
}

function setItalic() {
	document.execCommand("italic", false);
}

function setFontSize() {
	var size = $("#font-size-select").val();
	document.execCommand("fontSize", false, size);
}

function setFontName() {
	var name = $("#font-name-select").val();
	document.execCommand("fontName", false, name);
}

function addImage() {

	$("#file-upload").click();
}

function showImg(obj) {
	$("#editor").focus();
	var file = obj.files[0];
	var fileName = obj.value;
	var index = fileName.lastIndexOf(".") + 1;
	var fileType = fileName.substr(index).toLowerCase();
	var fileSize = file.size;
	if ((fileSize / 1024) > 200) {
		obj.value = "";
		alert("只能上传200kb以内的文件");
		return;
	}
	if (fileType != "jpg" && fileType != "jpeg") {
		obj.value = "";
		alert("只能选择jpg图片");
		return;
	}
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onloadstart = function(e) {
		reader.onload = function(e) {
			var imgFile = e.target.result;
			document.execCommand("insertImage",false,imgFile);
//			var img = '<img src="' + imgFile + '"/>'
//			$("#editor").append(img);
		}
	}
}

function submit() {
	var title = $("#title").val();
	var textTemp = $('#editor').html();
	var typeId = [];
	$.each($("input:checkbox:checked"), function() {
		typeId.push($(this).val());
	})
	if (!title || title == "") {
		alert("标题不能为空");
		return;
	}
	if (!textTemp || textTemp == "") {
		alert("文章内容不能为空");
		return;
	}
	if (typeId.length <= 0) {
		alert("请至少选择一个分类");
		return;
	}

	var imgArray = [];
	var imgTag = $("#editor img");
	if (!imgTag) {
		return;
	}
	for (var i = 0; i < imgTag.length; i++) {
		imgArray.push(imgTag[i].src);
	}
	var id = $("#hidden-data").attr("data-id");
	if (id == "") {
		id = "-1";
	}
	console.log(id);
	$.ajax({
		url : "/douban/saveArticleImage",
		type : "POST",
		cache : false,
		data : JSON.stringify({
			"img" : imgArray,
			"id" : id
		}),
		dataType : "json",
		contentType : "application/json;charset=utf-8",
		success : function(data) {
			if (data.code == 200) {
				var imgURLS = data.data.img;
				for (var i = 0; i < imgTag.length; i++) {
					$("#editor img")[i].src = imgURLS[i];
				}
				var text = $("#editor").html();
				if (id == "-1") {
					saveArticleAdd(title, text, typeId);
				} else {
					saveArticleEdit(id, title, text, typeId);
				}
			} else {
				alert("wrong");
				return null;
			}
		},
		error : function() {
			return null;
		}
	});
}

function saveArticleAdd(title, text, typeId) {
	$.ajax({
		url : "/douban/addArticle",
		type : "POST",
		data : JSON.stringify({
			"title" : title,
			"text" : text,
			"typeId" : typeId
		}),
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function(data) {
			if (data.code == 200) {
				window.location.href = "http://localhost:8080/douban/myArticle";
			} else {
				alert(data.msg);
				window.location.href = "http://localhost:8080/douban/error.jsp";
			}
		}
	});
}

function saveArticleEdit(id, title, text, typeId) {
	$.ajax({
		url : "/douban/editArticle",
		type : "POST",
		data : JSON.stringify({
			"id" : id,
			"title" : title,
			"text" : text,
			"typeId" : typeId
		}),
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function(data) {
			if (data.code == 200) {
				window.location.href = "http://localhost:8080/douban/myArticle";
			} else {
				alert(data.msg);
			}
		}
	});
}
