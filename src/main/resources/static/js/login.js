jQuery(document).ready(function($){
     // Reveal Login form
     setTimeout(function(){ $(".fade-in-effect").addClass('in'); }, 1);
     
     // Validation and Ajax action
     /*$("form#login").validate({
         rules: {
             username: {
                 required: true
             },
             passwd: {
                 required: true
             }
         },
         
         messages: {
             username: {
                 required: '请输入用户名'
             },
             passwd: {
                 required: '请输入密码'
             }
         },
         
         // Form Processing via AJAX
         submitHandler: function(form)
         {
             show_loading_bar(70); // Fill progress bar to 70% (just a given value)
             
             var opts = {
                 "closeButton": true,// 是否显示关闭按钮（提示框右上角关闭按钮）；
                 "debug": false,//是否为调试； 
                 "progressBar":false,//是否显示进度条（设置关闭的超时时间进度条）； 
                 "positionClass": "toast-top-full-width",//消息框在页面显示的位置
                 
                  toast-top-left  顶端左边
                  toast-top-right    顶端右边
                  toast-top-center  顶端中间
                  toast-top-full-width 顶端，宽度铺满整个屏幕
                  toast-botton-right  
                  toast-bottom-left
                  toast-bottom-center
                  toast-bottom-full-width
                 
                 "onclick": null,//点击消息框自定义事件 
                 "showDuration": "300",//显示动作时间 
                 "hideDuration": "1000",//隐藏动作时间 
                 "timeOut": "5000",//自动关闭超时时间 
                 "extendedTimeOut": "1000",
                 "showEasing": "swing",
                 "hideEasing": "linear",
                 "showMethod": "fadeIn",//显示的方式，和jquery相同 
                 "hideMethod": "fadeOut"//隐藏的方式，和jquery相同 
             };
                 
             $.ajax({
                 url: "/login",
                 method: 'POST',
                 dataType: 'json',
                 data: {
                     do_login: true,
                     username: $(form).find('#username').val(),
                     password: $(form).find('#password').val(),
                 },
                 error: function(res) {
                	 alert(res);
                 },
                 success: function(res)
                 {
                     show_loading_bar({
                         delay: .5,
                         pct: 100,
                         finish: function(){
                             if(res.code == '0')
                             {
                                 window.location.href = '/index';
                             }
                               else
                             {
                                 toastr.error(res.msg, "登录失败!", opts);
                                 $passwd.select();
                             }
                         }
                     });
                 }
             });
             
         }
     });*/
     
     // Set Form focus
     $("form#login .form-group:has(.form-control):first .form-control").focus();
 });