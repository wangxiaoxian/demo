var limit = 5;
var start = 0;
var hasMore = true;
var isLoading = false;
var orderType = null;

$(function() {
	queryPage();
	$("#orderByHeat").on("click", {"orderType":"heat"}, resetParamAndQuery);
	$("#orderByTime").on("click", {"orderType":"time"}, resetParamAndQuery);
	
	$(window).on("scroll",queryPage);
});

/**
 * 无限滚动翻页
 */
function queryPage() {
	var currHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); 
	var totalHeight = $(document).height();
	if(currHeight >= totalHeight && hasMore && !isLoading){ 
		$.ajax({
			type : "POST",
			url : 'heb/fuwuhao/promotion/queryPage.tv',
			data : {
				"orderType" : orderType,
				"start" : start,
				"limit" : limit
			},
			dataType : 'json',
			beforeSend : function() {
				isLoading = true;
				$("#notMore").hide();
				$("#loading").show();
			},
			success : function(resp){
				isLoading = false;
				if(!resp.isSuccess) {// 出错
					alert(resp.msg);
					return;
				}
				
				var list = resp.data;
				var len = list.length;
				if(len == 0) {// 没有更多数据
					$("#notMore").show();
					$("#loading").hide();
					hasMore = false;
					return;
				}
				
				var elements = "";
				for(var i = 0; i < len; i++) {
					var obj = list[i];
					elements += genDataEle(obj);
				}
				$("#main").append(elements);
				
				// 更新分页信息
				start += len;
				if(len % limit != 0) {// 没达到最大记录数，说明到达最后一页了
					hasMore = false; 
					$("#notMore").show();
					$("#loading").hide();
				} else {
					hasMore = true; 
				}
			}
		});
	}
}

function genDataEle(obj) {
	var dataEle = "";
	dataEle += "<div class=\"am-g-12 am-list-item-desced clear pic_yh\" onclick=\"javascript:window.location.href='" + obj.url + "';\">";
	if(obj.isHot == 1) {
		dataEle += "<div class=\"hot\"><img src=\"resources/heb/fuwuhao/image/youhui/hot.png\" width=\"100%\"/></div>";
	}
	dataEle += "<div class=\"remain\">剩余 <span>" + obj.restDays + "</span> 天 </div>";
	dataEle += "<div class=\"hot_sum\">活动热度：<span>" + obj.heat + "</span></div>";
	dataEle += "<img  src=\"" + obj.promotionPic + "\" width=\"100%\" />";
	dataEle += "</div>";
	return dataEle;
}

/**
 * 将查找的参数重置，页面的记录清空，再查找
 * @param event
 */
function resetParamAndQuery(event) {
	// 设置“按时间排序 按热度排序”的点击后样式
	if(event.data.orderType == "heat") {
		orderType = "heat";
		$("#orderByHeat").addClass("on_px");
		$("#orderByTime").removeClass("on_px");
	} else if (event.data.orderType == "time") {
		orderType = "time";
		$("#orderByTime").addClass("on_px");
		$("#orderByHeat").removeClass("on_px");
	}
	
	limit = 5;
	start = 0;
	hasMore = true;
	isLoading = false;
	$("#main").html("");
	
	queryPage();
}

