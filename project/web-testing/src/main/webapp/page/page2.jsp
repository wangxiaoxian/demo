<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div><p>
每个页码块只显示固定的页码数（最后一页除外，这里以10页为例），到达页码块的最后一页时，点击下一页，跳转到下一个页码块。<br>
最后一个页码块要判断是否足够10页，不够的要特殊处理。
</p></div>
<div><p>
<%
//当前是第几页
int pageNow = request.getParameter("pageNow") == null ? 1 :Integer.parseInt(request.getParameter("pageNow"));
int pageBlockSize = 10;  // 每一个页块有多少个页码
int pageBlockNo = (pageNow - 1) / pageBlockSize;  //pageBlock代表一个页码块，即页面上显示页码的那个区域。pageBlockNo代表第几个页码块。从0开始计算。
int pageAll = 28;  //总页数

//上一页
if(pageNow <= 1) {
%>
[上一页]
<%
} else {
%>
[<a href="page2.jsp?pageNow=<%=pageNow-1 %>">上一页</a>]
<%
}

// 循环页码
// 最后一页是否需要特殊处理，如果最后一页正好是整十的数，则不需要特殊处理；如果不是整十的数，则需要特殊处理。
boolean isNeedLastPageBlock = pageAll % pageBlockSize != 0 ? true : false;
if(isNeedLastPageBlock && pageBlockNo == (pageAll / pageBlockSize)) {  //最后一个页码块需要特殊处理，并且已经到达最后一个页码块
	for(int i = pageBlockNo * pageBlockSize + 1; 
			i < (pageBlockNo * pageBlockSize + 1) + pageAll % pageBlockSize; 
			i++) {
		if(pageNow != i) {
	%>
	[<a href="page2.jsp?pageNow=<%=i %>"><%=i %></a>]
	<%
		} else {
	%>
	[<%=i %>]
	<%
		}
	}
} else {
	for(int i = pageBlockNo * pageBlockSize+1; 
			i < pageBlockNo * pageBlockSize + 1 + pageBlockSize; 
			i++) {
		if(pageNow != i) {
	%>
	[<a href="page2.jsp?pageNow=<%=i %>"><%=i %></a>]
	<%
		} else {
	%>
	[<%=i %>]
	<%
		}
	}
}

//下一页
if(pageNow >= pageAll) {
%>
[下一页]
<%
} else {
%>
[<a href="page2.jsp?pageNow=<%=pageNow+1 %>">下一页</a>]
<%
}
%>
第 <%=pageNow %> 页，共 <%=pageAll %> 页
</p></div>
</body>
</html>