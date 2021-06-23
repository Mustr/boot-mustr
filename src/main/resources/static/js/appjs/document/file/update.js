$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save () {
	$.ajax({
		type : "PUT",
		url : "/document/file/file",
		data : $('#documentForm').serializeArray(),
		error : function(request) {
			layer.alert("出错了，请检查！");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.location.reload();
				layer_close();
			} else {
				layer.alert(data.msg)
			}

		}
	});
}