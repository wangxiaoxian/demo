<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
<meta content="telephone=no" name="format-detection"/>
<meta name="wap-font-scale" content="no"/>
<title>优惠集锦</title>
</head>
<body>
<div class="am-g-12 am-list-item-desced am-list-item-thumbed"></div>
	<div class="am-g-12 am-list-item-desced paixu">
	<span id="orderByTime">按时间排序</span> <!--选中改变颜色-->
	<span id="orderByHeat">按热度排序</span>  <!--默认未选中-->
</div>
<div id="main"></div>
<div class="center" id="loading" style="display:none;">加载中...</div>
<div class="center" id="notMore" style="display:none;">没有了</div>

<script src="common/js/jquery-1.7.2.min.js"></script>
<script src="common/js/promotion.js"></script>
</body>
</html>
