var repasswordVali = true;
var usernameVali = true;
$(function(){
	$("#repassword").blur(function(){
		var pass = $("#password").val();
		var repass = $(this).val();
		if (pass != "" && pass != repass) {
			$("#repassword-validate").show();
			repasswordVali = false;
		} else {
			$("#repassword-validate").hide();
			repasswordVali = true;
		}
	});
	
	$("#username").blur(function(){
		var username = $(this).val();
		if (username != "") {
			$.get("/common/user/check",{username:username},function(data){
				if (data.code == 0) {
					$("#username-validate").hide();
					usernameVali = true;
				} else {
					$("#username-validate").show();
					usernameVali = false;
				}
			});
		}
	});
	
})


$.validator.setDefaults({
	submitHandler : function() {
		if (repasswordVali && usernameVali) {
			save();
		}
	}
});

function save(){
	$.ajax({
		url : '/common/user/user',
		type : 'post',
		data: $("#userForm").serializeArray(),
		error : function(request) {
			layer.alert("出错了，请检查！");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.reLoad();
				layer_close();
			} else {
				layer.alert(data.msg)
			}
		}
	
	});
	
}