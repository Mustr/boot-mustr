function logout() {
	$("#logOutForm").submit();
}

$("#main-menu a").click(function(){
	$(this).parent().siblings().removeClass("active");
	$(this).parent().addClass("active");
	var theHref = $(this).attr("href");
	if (typeof(theHref) != "undefined" && theHref != "" && theHref != "#") {
		var theName = $(this).data("act");
		if (isSet(theName)) {
			$("#action-path").html(getContent(null, theName));
		} else {
			var parent = $(this).parent().parent().data("parent");
			var thisName = $(this).children().first().html();
			
			$("#action-path").html(getContent(parent, thisName));
		}
		
		//设置ifram的地址
		$("#ifram-container").attr("src",theHref);
		
		
	}
	return false;
});

function getContent(parent, thisName) {
	var content = "";
	if (isSet(parent)) {
		content += "<li><span>" + parent + "<span></li>";
	}
	if (isSet(thisName)) {
		content += "<li class='active'><strong>" + thisName + "<strong></li>";
	}
	return content;
}

function isSet(title) {
	return title != null && title != "" && title != "undefined";
}

