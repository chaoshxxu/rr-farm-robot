<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
    	<base href="<%=basePath%>" />
	    <title>RR_Farm</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="javascript/common.js"></script>
	</head>

	<body>
		<center>
			<h1>人人农场无耻外挂</h1>
		</center>
		
		<s:form action="add">
			<s:textfield name="email" size="100" label="Email"/>
			<s:password name="pw" size="100" label="Password" />
			<s:textarea name="friends" label="Friends" cols="100" rows="5"/>
			<s:submit value="添加"/>
		</s:form>
		
		<br/ ><br/ >
		
		<s:actionerror />
		<s:iterator value="list" status="stat">
			<s:property value="list[#stat.index][0]" escape="false"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<s:property value="list[#stat.index][2]" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="remove.action?email=<s:property value="list[#stat.index][1]" />" >删除</a>
			<br />
		</s:iterator>
		
	</body>
</html>
