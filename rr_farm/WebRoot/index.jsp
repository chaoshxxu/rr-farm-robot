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
			<h1>科学发展观学习交流系统 Ver1.2</h1>
		</center>
		
		<s:form action="add">
			<s:textfield name="email" size="100" label="毛主席万岁"/>
			<s:password name="pw" size="100" label="社会主义好" />
			<s:textfield name="feedFriends" size="100" label="走进新时代"/>
			<s:hidden name="stealFriends" value="猥琐坚" />
			<s:textfield name="reservedFood" size="100" label="坚坚无鸭腻"/>
		</s:form>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="删除" onclick="document.forms['add'].action='remove.action';document.forms['add'].submit();" />
		<input type="button" value="添加/更新 " onclick="document.forms['add'].action='add.action';document.forms['add'].submit();" />
		
		<br /><br />
		
		<table cellpadding="10">
			<tr>
				<th></th>
				<th>改革开放</th>
				<th>三个代表</th>
				<th>科学发展观</th>
				<th>主体思想</th>
				<th>真呀真导致</th>
			</tr>
			<s:iterator value="list" status="stat">
				<tr>
					<td>
						<s:if test="list[#stat.index][3] != null" >
							<a href="work.action?email=<s:property value='list[#stat.index][1]' />" >立刻学习</a>
						</s:if>
					</td>
					<td>
						<s:property value="list[#stat.index][0]" escape="false"/>
					</td>
					<td>
						<s:property value="list[#stat.index][2]" />
					</td>
					<td>
						<s:if test="list[#stat.index][3] == null" >学习交流中...</s:if>
						<s:else><s:date name="list[#stat.index][3]" /></s:else>
					</td>
					<td>
						<s:property value="list[#stat.index][4]" />
					</td>
					<td>
						<s:property value="list[#stat.index][6]" />
					</td>
				</tr>
			</s:iterator>
		</table>
		<br /><br /><br /><br /><br /><br />

	</body>



</html>
