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
			<h1>科学发展观学习交流系统 Ver1.1</h1>
		</center>
		
		<s:form action="add">
			<s:textfield name="email" size="100" label="账号"/>
			<s:password name="pw" size="100" label="密码" />
			<s:textfield name="feedFriends" size="100" label="喂友"/>
			<s:textfield name="stealFriends" size="100" label="偷友"/>
			<s:textfield name="reservedFood" size="100" label="锁定"/>
		</s:form>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="删除 " onclick="document.forms['add'].action='remove.action';document.forms['add'].submit();" />
		<input type="button" value="添加/更新 " onclick="document.forms['add'].action='add.action';document.forms['add'].submit();" />
		
		<br /><br />
		
		<table cellpadding="10">
			<tr>
				<th>姓名	</th>
				<th>账号</th>
				<th>下次开工时间</th>
				<th>喂友</th>
				<th>偷友</th>
				<th>锁定作物</th>
				<th></th>
			</tr>
			<s:iterator value="list" status="stat">
				<tr>
					<td>
						<s:property value="list[#stat.index][0]" escape="false"/>
					</td>
					<td>
						<s:property value="list[#stat.index][2]" />
					</td>
					<td>
						<s:if test="list[#stat.index][3] == null" >工作中...</s:if>
						<s:else><s:date name="list[#stat.index][3]" /></s:else>
					</td>
					<td>
						<s:property value="list[#stat.index][4]" />
					</td>
					<td>
						<s:property value="list[#stat.index][5]" />
					</td>
					<td>
						<s:property value="list[#stat.index][6]" />
					</td>
					<td>
						<s:if test="list[#stat.index][3] != null" >
							<a href="work.action?email=<s:property value='list[#stat.index][1]' />" >立刻开工</a>
						</s:if>
					</td>
				</tr>
			</s:iterator>
		</table>
	</body>
	 
</html>
