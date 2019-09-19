$(function(){
    $('input:radio').click(function(){
     
        var domName = $(this).attr('name');

        var $radio = $(this);
        // if this was previously checked
        if ($radio.data('waschecked') == true){
            $radio.prop('checked', false);
            $("input:radio[name='" + domName + "']").data('waschecked',false);
            $("input:radio[name='" + domName + "']").val('0');
            //$radio.data('waschecked', false);
        } else {
            $radio.prop('checked', true);
            $("input:radio[name='" + domName + "']").data('waschecked',false);
            $("input:radio[name='" + domName + "']").val('1');
            $radio.data('waschecked', true);
        }
    });
});

$(document).ready(function(){

	$ ("#submit").click(function(){
		isLoginValid();
	});
	$ ("#regist").click(function(){
		isRegistValid();
	});
});

function isLoginValid(){
	var name = $("#username").val();
	var password = $("#password").val();
	var code = $("#code").val();
	var autoLogin = $("#auto-login").val();
	if(name ==null || name==""){
		alert("用户名不能为空！")
		return;
	}
	if(password == null || password == ""){
		alert("密码不能为空");
		return;
	}
	if(code == null || code == ""){
		alert("验证码不能为空");
		return;
	}
	$.ajax({
		type : "POST",
		async: false,
		datatype : "json",
		url: "login",
		data : JSON.stringify({
			"userName":	name,
			"password":	password,
			"code":		code,
			"autoLogin": autoLogin
		}),
		contentType: 'application/json; charset=UTF-8',
		success : function(data) {
//			var obj = eval("("+data+")");
			if(data.code != 200){
				alert(data.msg);
				return false;
			}else{
				window.location.href="http://localhost:8080/douban/index";
	            window.event.returnValue=false;
			}
		},
	});
	return false;
}

function isRegistValid(){
	var name = $("#username").val();
	var password = $("#password").val();
	var repeatPassword = $("#confirmPassword").val();
	var email = $("#email").val();
	var nickname = $("#nickname").val();
	var code = $("#code").val();
	if(name ==null || name==""){
		alert("用户名不能为空！")
		return;
	}
	if(password == null || password == ""){
		alert("密码不能为空1");
		return;
	}
	if(confirmPassword == null || confirmPassword == ""){
		alert("确认密码不能为空");
		return;
	}
	if(email == null || email == ""){
		alert("邮箱不能为空");
		return;
	}
	if(nickname == null || nickname == ""){
		alert("昵称不能为空");
		return;
	}
	if(code == null || code == ""){
		alert("验证码不能为空");
		return;
	}
	$.ajax({
		type : "POST",
		async: false,
		datatype : "json",
		url: "regist",
		data : JSON.stringify({
			"userName" : 	name,
			"password" : 	password,
			"repeatPassword" : repeatPassword,
			"email" : email,
			"nickname" : nickname,
			"code" : 	code
		}),
		contentType: 'application/json; charset=UTF-8',
		success : function(data) {
//			var obj = eval("("+data+")");
			if(data.code == 200){
//				alert(data);
				window.location.href="http://localhost:8080/douban/login.jsp";
	            window.event.returnValue=false;
			}else{
				alert(data.msg);
			}
		},
	});
	return false;
}