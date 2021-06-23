function download(fileId) {
   window.open("/document/file/download/" + fileId);
}

function removeDocument(fileId) {
	layer.confirm('请确定是否继续?', {btn: ['确定','取消']}, function(){
		$.ajax({
			url : '/document/file/file/log/'+fileId,
			type : 'DELETE',
			error : function(request) {
				layer.alert("出错了，请检查！");
			},
			success : function(data) {
				if (data.code == 0) {
					location.reload();
				} else {
					layer.alert(data.msg)
				}

			}
		});
	});
}

