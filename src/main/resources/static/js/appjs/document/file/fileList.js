jQuery(document).ready(function($)
{

    var fields = $('#fileForm').serializeArray();
    var params = {}; //声明一个对象
    $.each(fields, function(index, field) {
        params[field.name] = field.value; //通过变量，将属性值，属性一起放到对象中
    })

	var $example_dropzone_filetable = $("#example-dropzone-filetable"),
	    i = 1,
	    $filetable = $("#filetable"),
	    fileLength = 0;
		example_dropzone = $("#advancedDropzone").dropzone({
		url: '/document/file/upload',
		params: params,
		// Events
		addedfile: function(file)
		{
		    fileLength++;
		    $filetable.addClass("hide");
		    $example_dropzone_filetable.removeClass("hide");
		    
			if(i == 1)
			{
				$example_dropzone_filetable.find('tbody').html('');
			}
			
			var size = parseInt(file.size/1024, 10);
			size = size < 1024 ? (size + " KB") : (parseInt(size/1024, 10) + " MB");
			
			var	$el = $('<tr>\
							<td class="text-center">'+(i++)+'</td>\
							<td>'+file.name+'</td>\
							<td><div class="progress progress-striped"><div class="progress-bar progress-bar-warning"></div></div></td>\
							<td>'+size+'</td>\
							<td class="progress-status">上传中...</td>\
						</tr>');
			
			$example_dropzone_filetable.find('tbody').append($el);
			file.fileEntryTd = $el;
			file.progressBar = $el.find('.progress-bar');
		},
		
		uploadprogress: function(file, progress, bytesSent)
		{
			file.progressBar.width(progress + '%');
		},
		
		success: function(file)
		{   
		    fileLength--;
			file.fileEntryTd.find('.progress-status').html('<span class="text-success">上传成功</span>');
			file.progressBar.removeClass('progress-bar-warning').addClass('progress-bar-success');
			if (fileLength == 0) {
			  location.reload();
			}
		},
		
		error: function(file)
		{
			file.fileEntryTd.find('.progress-status').html('<span class="text-danger">上传失败</span>');
			file.progressBar.removeClass('progress-bar-warning').addClass('progress-bar-red');
		}
	});
	
	
	function removeUpload() {
            //取消上传
       example_dropzone.removeAllFiles();
    }
});

function download(fileId) {
   window.open("/document/file/download/" + fileId);
}

function removeDocument(fileId) {
	layer.confirm('删除该文件，连日志一并删除，请确定是否继续?', {btn: ['确定','取消']}, function(){
		$.ajax({
			url : '/document/file/file/'+fileId,
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

function ediDocument(fileId) {
   layer_show("编辑文件属性","/document/file/toUpdate/" + fileId,800,450);
}

function logs(projectId, name) {
  layer_show("文件记录","/document/file/fileLogs/" + projectId + "?name=" + name,900,600);
}

function view(fileId) {
  layer_show("","/document/file/view/" + fileId,900,600);
}

function sendMsg(fileId) {
    $.ajax({
		url : '/document/file/sendMsg/'+fileId,
		type : 'POST',
		error : function(request) {
			layer.alert("出错了，请检查！");
		},
		success : function(data) {
			if (data.code == 0) {
				layer.alert("发送成功!");
			} else {
				layer.alert(data.msg);
			}

		}
	});
}