
//部门的展开与收缩
$(".deptName").click(function() {
	var onf = $(this).find(".onf");
	if (onf.hasClass("fa-angle-right")) {
		onf.removeClass("fa-angle-right");
		onf.addClass("fa-angle-down");
	} else {
		onf.removeClass("fa-angle-down");
		onf.addClass("fa-angle-right");
	}
	var deptId = $(this).attr("deptId");
	$(".childTr_" + deptId).toggle();
});

	
function removeDept(id) {
	layer.confirm('请确认是否删除该部门?', {btn: ['确定','取消']}, function(){
		$.ajax({
			url : '/oa/dept/dept/'+id,
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

function addDept(pid) {
	layer_show("添加部门","/oa/dept/addDept/" + pid,800,450);
}

function editDept(id) {
	layer_show("编辑部门","/oa/dept/updateDept/" + id,800,450);
}

function reLoad() {
	location.reload();
}


