$(function(){
	loadRoles();
	
	//添加用户
	$("#addBtn").click(function(){
		layer_show("添加角色","/common/role/role",800,420);
	})
});

function loadRoles() {
	layui.use("table",function(){
		 var table = layui.table;
		 
		 table.render({
			 id:'roleTable'
			,elem:'#roleTable'
		    ,url: '/common/role/roles'
	    	,cols: [[ //表头
			      {type:'numbers',title: '序号',width:'5%'}
			      ,{field: 'name', title: '角色名', width:'15%',sort: true}
			      ,{field: 'sign', title: '标识', width:'13%', sort: true}
			      ,{field: 'remark', title: '备注', width:'30%'} 
			      ,{field: 'permissionName', title: '权限', width: '22%'}
			      ,{title: '操作',align:'center', toolbar: '#optBtns', width:'15%'}
			    ]]
			 
		 });
		 
		// 监听工具条
		table.on('tool(filter)', function(obj) {
			var data = obj.data;
			var rId = data.id;
			if (obj.event === 'del') {
				delRole(rId,obj);
			} else if (obj.event === 'edit') {
				editRole(rId);
			}
		});
	})
}

function delRole(rId, obj) {
	layer.confirm("真的要删除该角色吗?",function(index){
		$.ajax({
			url :"/common/role/role/" + rId,
			method : "DELETE",
			error : function(request) {
				layer.alert("出错了，请检查");
			},
			success : function (data) {
				if (data.code == 0) {
					obj.del();
					layer.close(index);
				} else {
					layer.alert(data.msg);
				}
			}
		});
	});
}

function editRole(rId) {
	layer_show("编辑角色","/common/role/role/" +rId,800,420);
}

function reLoad() {
	layui.use('table', function(){
	    var table = layui.table;
	    table.reload('roleTable', null);
	});
}