var romaingNameLibrary = [
	"中国香港",
	"中国澳门",
	"中国台湾",
	"韩国",
	"泰国",
	"日本",
	"美国",
	"英国",
	"德国",
	"新加坡",
	"澳大利亚",
	"法国"
];

var cache = {};

$(document).ready(function() {
	
	queryHotCity();
	
	// 将热门地区绑定搜索事件。在父标签绑定事件，通过event.target判断是a标签就处理事件。
	$("#hotRoamingNameDiv").on("click", function(event) {
		if(event.target.name == "hotRomaingName") {
			var keyword = event.target.innerText;
			$("#keyword").val(keyword);
			searchRoamingInfo();
		}
	});
	
	$("#searchBtn").on("click", searchRoamingInfo);
						   
	$("#keyword").autocomplete({
		//source: romaingNameLibrary,
		source: function(request, response) {
			var keyword = request.term;        
			if (keyword in cache) {				
				response(cache[keyword]);          
				return;        
			}
					
			$.ajax({          
				url: "heb/fuwuhao/roaming/queryCity.tv",  
				type : "POST",
				dataType: "json",          
				data: {
					"romaingName": keyword
				},          
				success: function(resp) {
					var romaingList = resp.data;
					var cityNameList = [];
					for(var i = 0; i < romaingList.length; i++) {
						cityNameList.push(romaingList[i].romaingName);
					}
					
					cache[keyword] = cityNameList;
					response(cityNameList);
				}        
			});      
		},
		minLength: 1,
		delay : 500,
		select : function(event, ui) {
			$("#keyword").val(ui.item.value);
			searchRoamingInfo();
		}
	});
	
});

/**
 * 加载热门地区列表
 */
function queryHotCity() {
	
	$.ajax({
		type : "POST",
		url : 'heb/fuwuhao/roaming/queryCity.tv',
		data : {
			"isHot" : 1
		},
		dataType : 'json',
		success : function(resp){
			if(!resp.isSuccess) {
				alert(resp.msg);
				return;
			}
			
			var list = resp.data;
			var dataEle = "";
			for(var i = 0; i < list.length; i++) {
				var obj = list[i];
				dataEle += "<a name=\"hotRomaingName\" href=\"javascript:void(0);\">" + obj.romaingName + "</a>";
			}
			$("#hotRoamingNameDiv").append(dataEle);
		}
	});
}

/**
 * 搜索漫游套餐的详情
 * @param keyword
 */
function searchRoamingInfo() {
	var keyword = $("#keyword").val();
	if (!keyword) {
		msgbox('请填写或选择一个国家或地区');
		return;
	} 
	
	$.ajax({
		type : "POST",
		url : 'heb/fuwuhao/roaming/queryDetail.tv',
		data : {
			"romaingName" : keyword
		},
		dataType : 'json',
		success : function(resp){
			if(!resp.isSuccess) {
				alert(resp.msg);
				return;
			}
			
			var obj = resp.data;
			if (obj) {
				
				$("#tczfDiv").html(obj.packageRecommendStr);
				$("#bzzfDiv").html(obj.chargesStr);
				$("#wayDiv").html(obj.wayStr);
				$("#warntipsDiv").html(obj.tipsStr);
				
				$("#defaultDiv").hide();
				$("#resultDiv").show();
			} else {
				msgbox('抱歉！暂不支持您所填写的国家或地区漫游资费查询，您可拨打客服热线10086咨询详情。');
				return;			
			}
		}
	});
	
	
}