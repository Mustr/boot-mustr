$(".projectName").click(function() {
	var onf = $(this).find(".onf");
	if (onf.hasClass("fa-angle-right")) {
		onf.removeClass("fa-angle-right");
		onf.addClass("fa-angle-down");
	} else {
		onf.removeClass("fa-angle-down");
		onf.addClass("fa-angle-right");
	}
	var projectId = $(this).attr("projectId");
	$(".childTr_" + projectId).toggle();
});


function addProject(pid) {
	layer_show("添加项目","/document/project/addProject/" + pid,800,450);
}

function editProject(id) {
	layer_show("编辑项目","/document/project/updateProject/" + id,800,450);
}

function reLoad() {
	location.reload();
}

function removeProject(id) {
    layer.confirm('请确认是否删除该项目?', {btn: ['确定','取消']}, function(){
		$.ajax({
			url : '/document/project/project/'+id,
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

function toFileList(projectId) {
	location.href ="/document/file/fileList/" + projectId;
}