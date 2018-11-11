$(function(){
	load();
});

function load() {
	layui.use('table', function(){
	    var table = layui.table;
	
	    table.render({
			id:'logTab'
		    ,elem: '#logTable'
		    ,url: '/common/log/logList' //数据接口
		    ,limit:5
		    ,toolbar: '#toolbar'
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
		      {type: 'checkbox', fixed: 'left',width:'5%'}
		      ,{field: 'username', title: '用户名', width:'15%',sort: true}
		      ,{field: 'operation', title: '操作', width:'25%', sort: true}
		      ,{field: 'time',title: '相应时间', width:'10%', sort:true}
		      ,{field: 'ip', title: 'IP地址', width:'20%'} 
		      ,{field: 'createTime', title: '操作时间', width: '25%'}
		      
		    ]]
		  });
		

		// 监听工具条
		table.on('toolbar(logFilter)', function(obj) {
			var checkStatus = table.checkStatus(obj.config.id);
			if (obj.event == "batchDel") {
				var data = checkStatus.data;
				if (data == null || data.length == 0) {
					layer.msg("请先选中记录！");
					return;
				}
				var ids = "";
				var len = data.length;
				for (var i = 0; i < len ; i++) {
					ids += data[i].id;
					if (i != len - 1) {
						ids += ",";
					}
				}
				delLog(ids, table);
			}
		});
		

		//定义对象
		var active = {
			search : function(obj) {
				// 执行重载
				table.reload('logTab', {
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

function delLog(ids,table) {
	layer.confirm('真的删除这些日志吗么?', function(index) {
		$.ajax({
			url :"/common/log/batchLog",
			method : "POST",
			data : {'_method':'DELETE','ids':ids},
			error : function(request) {
				layer.alert("出错了，请检查");
			},
			success : function (data) {
				if (data.code == 0) {
					layer.close(index);
					// 执行重载
					table.reload('logTab', {
						page : {
							curr : 1// 重新从第 1 页开始
						},
						where : {
							username : $('#username').val()
						}
					});
				} else {
					layer.alert(data.msg);
				}
			}
		});
	});
}