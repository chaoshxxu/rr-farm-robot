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
			<s:textfield name="email" size="100" label="账号"/>
			<s:password name="pw" size="100" label="密码" />
			<s:textarea name="feedFfriends" label="喂友" cols="85" rows="3"/>
			<s:textarea name="stealFriends" label="偷友" cols="85" rows="3"/>
		</s:form>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="删除 " onclick="document.forms['add'].action='remove.action';document.forms['add'].submit();" />
		<input type="button" value="添加/更新 " onclick="document.forms['add'].action='add.action';document.forms['add'].submit();" />
		
		<br /><br />
		
		<table>
			<s:iterator value="list" status="stat">
				<tr>
					<td>
						<s:property value="list[#stat.index][0]" escape="false"/>
					</td>
					<td>
						<s:property value="list[#stat.index][2]" />
					</td>
					<td>
						<a href="work.action?email=<s:property value="list[#stat.index][1]" />" >干活</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</body>
	 
</html>
