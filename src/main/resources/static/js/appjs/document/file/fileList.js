jQuery(document).ready(function($)
{

    var fields = $('#fileForm').serializeArray();
    var params = {}; //声明一个对象
    $.each(fields, function(index, field) {
        params[field.name] = field.value; //通过变量，将属性值，属性一起放到对象中
    })

	var $example_dropzone_filetable = $("#example-dropzone-filetable"),
	    i = $example_dropzone_filetable.find('tbody').children('tr').length,
		example_dropzone = $("#advancedDropzone").dropzone({
		url: '/document/file/upload',
		params: params,
		// Events
		addedfile: function(file)
		{
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
							<td class="progress-option">操作</td>\
						</tr>');
			
			$example_dropzone_filetable.find('tbody').append($el);
			file.fileEntryTd = $el;
			file.progressBar = $el.find('.progress-bar');
		},
		
		uploadprogress: function(file, progress, bytesSent)
		{
			file.progressBar.width(progress + '%');
		},
		
		success: function(file, data)
		{   
			file.fileEntryTd.find('.progress-status').html('<span class="text-success">上传成功</span>');
			file.progressBar.removeClass('progress-bar-warning').addClass('progress-bar-success');
			file.fileEntryTd.find('.progress-option').html(data.id);
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
