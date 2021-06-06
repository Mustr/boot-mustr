$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save () {
	$.ajax({
		type : "POST",
		url : "/document/project/project",
		data : $('#projectForm').serializeArray(),
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