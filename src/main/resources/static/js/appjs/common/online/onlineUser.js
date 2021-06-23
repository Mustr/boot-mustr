$(function(){
	load();
});

function load() {
	layui.use('table', function(){
	    var table = layui.table;
	
	    table.render({
			id:'onlineUserTable'
		    ,elem: '#onlineUserTable'
		    ,url: '/common/online/onlineUsers' //数据接口
		    ,height: 400
		    ,limit:10
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
		      ,{title: '用户名', width:'25%',sort: true,templet:function(data){return data.principal.username}}
		      ,{title: '姓名', width:'25%', sort: true,templet:function(data){return data.principal.name}}
		      ,{field: 'lastRequest', title: '最后请求时间', width:'32%'} 
		      ,{title: '操作',align:'center', toolbar: '#optBtns', width:'10%'}
		    ]]
		  });
		

		// 监听工具条
		table.on('tool(filter)', function(obj) {
			var data = obj.data;
			var uId = data.sessionId;
			if (obj.event === 'del') {
				delUser(uId,obj);
			}
		});
		
	});

}


function delUser(id,obj) {
	layer.confirm('真的踢出该用户吗么?', function(index) {
		$.ajax({
			url :"/common/online/tick/",
			method : "DELETE",
			data : {'sessionId':id},
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
