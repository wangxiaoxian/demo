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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta content="telephone=no" name="format-detection">
<meta name="wap-font-scale" content="no">
<link href="common/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<title>国际漫游</title>
</head>
<body>
<header class="layout navigation">
    <div align="left" class="navleft"><img src="resources/heb/fuwuhao/image/back.png" height="26px" /></div>
    <div align="left" class="navcenter center">国际漫游</div>
    <div align="right" class="navright"><img src="resources/heb/fuwuhao/image/more.png"  width="20px"></div>
</header>
<div class="am-g-12 am-list-item-desced am-list-item-thumbed"></div>
<div class="am-g-12 am-list-item-desced2 ">
    <div class="am-g-65 pad_l5">
        <input class="input_num" placeholder="请输入国家或地区" id="keyword"/>
    </div>
    <div class="am-g-3">
        <div class="btn" id="searchBtn">查 询</div>
    </div>
</div>

<div class="am-g-12 am-list-item-desced am-list-item-thumbed clear area" id="hotRoamingNameDiv"> 
</div>

<script src="common/js/jquery-1.7.2.min.js"></script>
<script src="common/js/jquery-ui.min.js"></script>
<script src="autocomplete/autocomplete1.js"></script>

</body>
</html>
