$.validator.setDefaults({
	submitHandler: function() {
		save();
	}
});

function save () {
	$.ajax({
		url: '/common/role/role',
		type: 'PUT',
		data: $("#roleForm").serializeArray(),
		error:function(data){
			layer.alert("出错了，请检查！")
		},
		success: function(data) {
			if (data.code == 0) {
				parent.reLoad();
				layer_close();
			} else {
				layer.alert(data.msg)
			}
		}
		
	})
	
}