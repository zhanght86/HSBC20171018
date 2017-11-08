<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%	
	String action =  request.getParameter("action");
	if(action == null) action = "query";  
	String nodeID = request.getParameter("id");
	String nodeName = request.getParameter("name"); 
	String nodeTime = request.getParameter("time"); 
	String flowType = request.getParameter("type"); 
	if(nodeID == null) nodeID = "";  
	if(nodeName == null) nodeName = ""; 
	if(nodeTime == null) nodeTime = ""; 
	if(flowType == null) flowType = ""; 
	nodeName = new String(nodeName.getBytes("iso8859-1"),"UTF-8");
	System.out.println("++++-----"+nodeID+":"+nodeName+":"+flowType+":"+nodeTime+"------+++++");
	if(nodeID.startsWith("n"))
		nodeID = nodeID.substring(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<HEAD>
<TITLE>流程属性页</TITLE>
<SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</HEAD>

<BODY onload='' onunload=''>
	<TABLE border=0 width="100%" height="100%">
		<TR>
			<td>&nbsp;</td>
		</TR>
		<TR valign=top>
			<TD></TD>
			<TD width="100%" valign=top>
				<Fieldset style="border:none">
					<LEGEND align=left style="font-size: 9pt;color:#1faeff">&nbsp;节点属性&nbsp;</LEGEND>
					<TABLE  width="100%" height="100%" class="maxbox1">
						<TR valign=top>
							<TD width=5></TD>
							<TD>节点编号<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT 
								class="common" style="width:300px;background: url("../common/images/select--bg_03.png") no-repeat right;"
								NAME="nodeId"  id="nodeId" 
								<%
								if("".equals(nodeID))
								{%>
									value="请选择"
									ondblclick="checkBusiType();return showCodeList('queryactivityid',[this,nodeN],[0,1],null,'<%=flowType%>','BusiType');"
									onkeyup="checkBusiType();return showCodeList('queryactivityid',[this,nodeN],[0,1],null,'<%=flowType%>','BusiType');"
								<%}
								else {
									%>
									value="<%=nodeID %>"
									readonly=readonly
									<%
								}
								%>
								></TD>
							<TD></TD>
						</TR>
						<TR valign=top>
							<TD></TD>
							<TD>节点名称<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT
								id="nodeN"
								class="common" style="width:300px"
								NAME="nodeN" value="<%=nodeName %>" readonly=readonly></TD>
							<TD></TD>
						</TR>
						
						<TR valign=top>
							<TD></TD>
							<TD>节点类型<font color=red>&nbsp;*</font><INPUT TYPE="radio"
								NAME="nodeType" value="begin" disabled><span
								style="font-size: 9pt; COLOR: #919CD0">开始节点&nbsp;</span><INPUT
								TYPE="radio" NAME="nodeType" value="end" disabled><span
								style="font-size: 9pt; COLOR: #919CD0">结束节点&nbsp;</span><INPUT
								TYPE="radio" NAME="nodeType" value="course" checked disabled><span
								style="font-size: 9pt; COLOR: #919CD0" id="nodeType">过程节点&nbsp;</span>
								<!--<INPUT TYPE="radio" NAME="nodeType" value="judge"  disabled>决策节点&nbsp;--></TD>
							<TD></TD>
						</TR>
						<TR valign=top>
							<TD width=5></TD>
							<TD>时效类型&nbsp;&nbsp;&nbsp;&nbsp;<SELECT NAME="timeType"
								class="readonly" style="width:300px;height:26px;line-height:26px;"
								disabled><option value="01" selected>相对时效</option>
									<option value="02">绝对时效</option></SELECT></TD>
							<TD></TD>
						</TR>
						<TR valign=top>
							<TD></TD>
							<TD>流转时效&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE="text"
								NAME="time" value="<%=nodeTime %>"
								class="common" style="width:300px"
								placeholder="HH-MM"
								<%if("query".equals(action)  || "rebuild".equals(action)){%>
									readonly=readonly 
								<%}%>
								></TD>
							<TD></TD>
						</TR>
						<TR height="3">
							<TD></TD>
							<TD></TD>
						</TR>
					</TABLE>
				</Fieldset>
			</TD>
		</TR>
	</TABLE>
	<table cellspacing="1" cellpadding="0" border="0">
		<TR>
			<td>&nbsp;</td>
		</TR>
		<tr>
			<td width="55%"></td>
			<td><input id="btnOk" class="cssButton" type="button" value="确定"
				onclick="jscript: okOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input id="btnCancel" class="cssButton" type="button" value="取消"
				onclick="jscript: cancelOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input id="btnApply" class="cssButton" type="button" value="应用"
				onclick="jscript: applyOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	<span id="spanCode" style="display: none; position: absolute;"></span>
	<script>
		var has = false;
		function okOnClick() {
			if (applyOnClick()) {
				window.top.close();
			}
		}
		
		function cancelOnClick() {
			window.top.close();
		}
		
		function applyOnClick() {
			<%if(!"query".equals(action) && !"rebuild".equals(action)) {
			%>
				if(!checkTime()) return false;
				var nodeId = document.getElementById("nodeId").value;
				var nodeName = document.getElementById("nodeN").value;
				var time = document.getElementById("time").value;
				if (nodeId == null || nodeId === '' || nodeName == null
						|| nodeName === '') {
					alert("信息不完整");
					return false;
				}
				if (window.top.opener._loadNode(<%/*注意：第一个参数是否有值，决定是新增还是修改*/if("".equals(nodeID)){%>(has?"new":null)<%}else{%>"new"<%}%>, "course", 15,15,nodeId,nodeName,"01",time,null)){
					has = true;
					return true;
				}
				else
					return false;
			<%}else {%>
			return true;
			<%}%>
		}
		
		function checkBusiType()
		{
			if ("null" == "<%=flowType%>" ||  "" == "<%=flowType%>" ){
				alert("业务类型不能为空")
				return false;
			}
			return true;
		}

		function checkTime() {
			var time = document.getElementById("time").value;
			if (time != null && time != "") {//相对型格式为 HH+MM
				var check = /^\d+\-\d+/;
				if (!check.test(time)) {
					alert("时效格式应该为'小时-分钟'!");
					return false;
				}
			}
			return true;
		}
	</script>
</BODY>
</HTML>
