$(function(){
	load();
	
	//添加用户
	$("#addBtn").click(function(){
		layer_show("添加用户","/common/user/user",1000,450);
	})
});

function load() {
	layui.use('table', function(){
	    var table = layui.table;
	
	    table.render({
			id:'userTab'
		    ,elem: '#userTable'
		    ,url: '/common/user/userList' //数据接口
		    ,limit:5
		    ,method: 'get'
		    ,loading:true
		    ,limits: [5,10,20,30,50,100]
		    ,page: { //支持传入 laypage 组件的所有参数
		        layout: [ 'limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
		        ,groups: 5 //只显示 5 个连续页码
		    }  
		    ,request: {
		        pageName: 'pageNum' //页码的参数名称，默认：page
		        ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		      }
		    ,cols: [[ //表头
		      {type:'numbers',title: '序号',width:'8%'}
		      ,{field: 'username', title: '用户名', width:'15%',sort: true}
		      ,{field: 'name', title: '姓名', width:'15%', sort: true}
		      ,{title: '性别', width:'8%', templet:function(data){
		    	  if (data.sex) { return '男';}
		    	  else {return '女';}
		      }}
		      ,{field: 'email', title: '邮箱', width:'20%'} 
		      ,{field: 'deptName', title: '部门', width: '14%'}
		      ,{title: '操作',align:'center', toolbar: '#optBtns', width:'20%'}
		      
		    ]]
		  });
		

		// 监听工具条
		table.on('tool(userFilter)', function(obj) {
			var data = obj.data;
			var uId = data.id;
			if (obj.event === 'view') {
				viewUser(uId);
			} else if (obj.event === 'del') {
				delUser(uId,obj);
			} else if (obj.event === 'edit') {
				editUser(uId);
			}
		});
		

		//定义对象
		var active = {
			search : function(obj) {
				// 执行重载
				table.reload('userTab', {
					page : {
						curr : 1// 重新从第 1 页开始
					},
					where : {
						username : $('#username').val()
					}
				});
			}
		};
		$('#search').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		});
	});

}

function viewUser(id) {
	layer_show("用户信息","/common/user/view/"+id,800,400);
}

function editUser(id) {
	layer_show("编辑用户","/common/user/user/"+id,1000,450);
}

function delUser(id,obj) {
	layer.confirm('真的删除该用户吗么?', function(index) {
		$.ajax({
			url :"/common/user/user/" + id,
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

function reLoad() {
	layui.use('table', function(){
	    var table = layui.table;
	    table.reload('userTab', null);
	});
}