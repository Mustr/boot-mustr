$.validator.setDefaults({
	submitHandler: function () {
		update();
	}
});

function update() {
	$.ajax({
		url: '/common/user/user',
		type: 'POST',
		data: $("#userForm").serializeArray(),
		error: function(data) {
			layer.alert("出错了，请检查！");
		},
		success: function (data) {
			if (data.code == 0) {
				parent.reLoad();
				layer_close();
			} else {
				layer.alert(data.msg);
			}
		}
	});
	
}