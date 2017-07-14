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
模仿百度、谷歌的分页效果，即点击前x页，和点击后y页，页块是不变的；其他的大多数情况下，随着页码的变化，当前页码始终保持在中间。
</p></div>
<div><p>
<%
int pageNow = request.getParameter("pageNow") == null ? 1 :Integer.parseInt(request.getParameter("pageNow"));
int pageFrontNum = 5; //居中时，要在当前页码前显示几个页码
int pageBackNum = 4;  //居中时，要在当前页码后显示几个页码
int pageBlockSize = pageFrontNum + pageBackNum + 1;  // 每一个页块有多少个页码
int pageAll = 28;  //总页数

//上一页
if(pageNow <= 1) {
%>
[上一页]
<%
} else {
%>
[<a href="page1.jsp?pageNow=<%=pageNow-1 %>">上一页</a>]
<%
}

if(pageAll <= pageBlockSize) { //如果总页数不足一个页块大小，将所有页码输出
	for(int i = 1; i <= pageAll; i++) {
		if(pageNow != i) {
%>
[<a href="page1.jsp?pageNow=<%=i %>"><%=i %></a>]
<%
		} else {
%>
[<%=i %>]
<%
		}
	}
} else {  // 大于一个页块的情况下
	if(pageNow <= pageFrontNum + 1) { //点击前6页，页块都是不变的
		for(int i = 1; i <= pageBlockSize; i++) {
			if(pageNow != i) {
%>
[<a href="page1.jsp?pageNow=<%=i %>"><%=i %></a>]
<%
			} else {
%>
[<%=i %>]
<%
			}
		}
	} else if(pageNow + pageBackNum >= pageAll) {  // 点击后5页，页块也是不变的
		for(int i = pageAll - pageBlockSize + 1; i <= pageAll; i++) {
			if(pageNow != i) {
%>
[<a href="page1.jsp?pageNow=<%=i %>"><%=i %></a>]
<%
			} else {
%>
[<%=i %>]
<%
			}
		}
	} else {  //其他的大多数情况下，随着页码的变化，当前页码始终保持在中间
		for(int i = pageNow - pageFrontNum; i <= pageNow + pageBackNum; i++) {
			if(pageNow != i) {
%>
[<a href="page1.jsp?pageNow=<%=i %>"><%=i %></a>]
<%
			} else {
%>
[<%=i %>]
<%
			}
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
[<a href="page1.jsp?pageNow=<%=pageNow+1 %>">下一页</a>]
<%
}
%>
第 <%=pageNow %> 页，共 <%=pageAll %> 页
</p></div>
</body>
</html>