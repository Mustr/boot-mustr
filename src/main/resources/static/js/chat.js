var user;
$(function(){//获取当前用户
	$.getJSON("/common/online/currUser", function(result) {
		user = result;
	});
});

//websocket连接
var sockjs = new SockJS("/endpoint");
var stomp = Stomp.over(sockjs);
stomp.debug = null;
stomp.connect({},function(iframe){
	console.log("连接成功" + iframe);
});

function getCurrDate() {
	var currDate = (new Date()).format("yyyy-MM-dd HH:mm:ss");
	return currDate;
}

$(function(){
	$(".chat-text").keyup(function(){
		var con = $(this).val();
		if (con) {
			$('.chat-mess-botton').removeClass("disabled");
		} else {
			$('.chat-mess-botton').addClass("disabled");
		}
	});
	
	$(".chat-mess-botton").click(function(){
		var message = $(".chat-text").val();
		var userId = $(this).data("userid");
		
		var body = {
				date : new Date(),
		        userName : user.name,
				userId : user.id,
				content: message
		}
		
		stomp.send("/topic/user/"+userId+"/chat",{},JSON.stringify(body));
		$(".chat-text").val("");
		$('.chat-mess-botton').addClass("disabled");
		
		
		applyMessage(message,'自己',getCurrDate(),'mys');
	});
})

function addListen() {
	var $chat_conversation = $(".chat-conversation");
	$(".chat-group a").on('click', function(ev)
	{
		ev.preventDefault();
		$chat_conversation.toggleClass('is-open');
		if(!$chat_conversation.hasClass("is-open")) {
			return;
		}
		
		var userId = $(this).data('userid');
		var userName = $(this).data('username');
		
		subscribeUser();
		$(".display-name").html(userName);
		
		$(".chat-conversation textarea").trigger('autosize.resize').focus();
		
		var $btn = $('.chat-mess-botton');
		$btn.data("userid",userId);
		$btn.data("username",userName);;
	});
	
	$(".conversation-close").on('click', function(ev)
	{
		ev.preventDefault();
		$chat_conversation.removeClass('is-open');
	});
	
}
//获取所有在线用户
$.getJSON("/common/online/chatUsers",function(user){
	if (user) {
		var onlneUser = "<strong>在线用户</strong>";
		for (var i = 0 ; i < user.length ;i++) {
			onlneUser += '<a href="javascript:void(0)" data-username="'+user[i].name+'" data-userid="'+user[i].id+'"><span class="user-status is-online"></span> <em>'+user[i].name+'</em></a>'
		}
		$(".userContainer").html(onlneUser);
		addListen();
	}
});

//订阅用户聊天频道
function subscribeUser() {
	stomp.subscribe("/topic/user/" + user.id + "/chat", receive);
}
//接受发送来的消息
function receive(result) {
	var body = JSON.parse(result.body);
	var date = body.date;
	var dateStr = (new Date(date)).format("yyyy-MM-dd HH:mm:ss");
	var message = body.content;
	var userName = body.userName;
	
	applyMessage(message,userName,dateStr,'ots');
}

//渲染消息
function applyMessage(message,userName,date,clas) {
    var content = '<li class="'+clas+'">'
		content += '<span class="user">'+userName+'</span>';
	    content += '<span class="time">'+date+'</span>';
	    content += '<p>'+message+'</p>';
	    content += '</li>';
	var $body = $(".conversation-body");
	$body.append(content);
	$body.animate({scrollTop:$body[0].scrollHeight});
}
