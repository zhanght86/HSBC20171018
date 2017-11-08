<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%><%@include file="../common/jsp/UsrCheck.jsp"%>
<%	
	String action =  request.getParameter("action");
	if(action == null) action = "query";  
	String flowID = request.getParameter("id");
	String flowName = request.getParameter("name"); 
	String flowVersion = request.getParameter("version"); 
	String flowType = request.getParameter("type"); 
	if(flowID == null) flowID = "";  
	if(flowName == null) flowName = ""; 
	if(flowVersion == null) flowVersion = ""; 
	if(flowType == null) flowType = ""; 
	flowName = new String(flowName.getBytes("iso8859-1"),"UTF-8");
	System.out.println("++++-----"+flowID+":"+flowName+":"+flowType+":"+flowVersion+"------+++++");
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
<SCRIPT src="./interact.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</HEAD>

<BODY onload='init()'>
	<form>
		<TABLE class="common" border=0 width="100%" height="100%">
			<TR>
				<td>&nbsp;</td>
			</TR>
			<TR valign=top>
				<TD></TD>
				<TD width="100%" valign=top>
					<Fieldset style="border:none">
						<LEGEND align=left style="font-size: 9pt;color:#1faeff">&nbsp;流程属性&nbsp;</LEGEND>
						<TABLE class="maxbox1" width="100%" height="100%" style="font-size: 9pt;">
							<TR valign=top>
								<TD></TD>
								<TD>业务类型<font color=red>&nbsp;*</font>&nbsp;&nbsp;<Input class="code" style="width:100px"
									name="flowType" id="flowType"
									<%
								if(!"query".equals(action) && !"copy".equals(action) && !"rebuild".equals(action)) {
								%>
									ondblclick="return showCodeList('busitype',[this,flowTypeName],[0,1]);"
									onkeyup="return showCodeList('busitype',[this,flowTypeName],[0,1]);"
									<%}else{%> readonly=readonly <%}%> value="<%=flowType %>"><input
									name=flowTypeName id="flowTypeName"
									class="codename" style="width:200px;background: url("../common/images/select--bg_03.png") no-repeat right;" 
									readonly=readonly></TD>
								<TD></TD>
							</TR>
							<TR valign=top>
								<TD width=5></TD>
								<TD>流程编号<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT
									TYPE="text" name="flowId" class="common" style="width:300px"
									readonly=readonly value="<%=flowID%>"> <%
								if(!"query".equals(action) && !"rebuild".equals(action)) {
								%> <img src="images/character_map.gif" onclick="getFlowId()"
									style="position: bottom left"> <%}%> <SPAN id="ActionTip"></SPAN></TD>
								<TD></TD>
							</TR>
							<TR valign=top>
								<TD width=5></TD>
								<TD>流程版本<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT
									TYPE="text" name="flowVersion" value="0" id="flowVersion"
									class="common" style="width:300px"
									value="<%=flowVersion %>" readonly=readonly></TD>
								<TD></TD>
							</TR>
							<TR valign=top>
								<TD></TD>
								<TD>流程名称<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT
									TYPE="text" NAME="flowName" id="flowName"
									class="common" style="width:300px"
									value="<%=flowName%>"
									<%
								if("query".equals(action) || "rebuild".equals(action)) {%>
									readonly=readonly <%}%>></TD>
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
				<td><input id="btnOk" type="button" class="cssButton" value="确定"
					onclick="okOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><input id="btnCancel" class="cssButton" type="button" value="取消"
					onclick="cancelOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><input id="btnApply" class="cssButton" type="button" value="应用"
					onclick="applyOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
	<script>
		function init(){
			showOneCodeName('busitype', 'flowType', 'flowTypeName');
		}
		function okOnClick() {
			if(applyOnClick()) {
				window.top.close();
			}
		}
		function cancelOnClick() {
			<%if(!"copy".equals(action)) {%>
				window.top.close();
			<%}%>
		}
		function applyOnClick() {
			<%if(!"query".equals(action) && !"rebuild".equals(action)) {%>
				var flowType = document.getElementById("flowType").value;
				var flowId = document.getElementById("flowId").value;
				var flowVersion = document.getElementById("flowVersion").value;
				var flowName = document.getElementById("flowName").value;
				if (flowType == null || flowType === '' || flowId == null
						|| flowId === '' || flowVersion == null
						|| flowVersion === '' || flowName == null
						|| flowName === '') {
					alert("信息不完整");
					return false;
				}
				if (window.top.opener._loadProcess(null, flowId, flowVersion,
						flowName, flowType))
					return true;
				else
					return false;
			<%}else {%>
				return true;
			<%}%>
		}
		
		// 生成流程号
		function getFlowId() {

			var action = "INSERT";
			var actionId = "FlowId";
			var para = document.getElementById("flowType").value;
			var url = "./interact.jsp";

			if (para == null || para == "") {
				alert("业务类型不能为空!");
				return;
			}

			var AjaxRequestObj = InitRequest();

			if (AjaxRequestObj != null) {
				document.getElementById("ActionTip").innerHTML = '<span class="Loading">正在生成,请稍候...</span>';
				AjaxRequestObj.onreadystatechange = function() {
					if (AjaxRequestObj.readyState == 4 && AjaxRequestObj.status == 200) {
						if (AjaxRequestObj.responseText) {
							eval(AjaxRequestObj.responseText);
							document.getElementById("flowId").value = flowId;
							document.getElementById("flowVersion").value = versionId;
							document.getElementById("ActionTip").innerHTML = '';
						} else {
							document.getElementById("ActionTip").innerHTML = '<span class="Error">生成错误</span>';
						}
					}
				};
				AjaxRequestObj.open("POST", url, true);
				AjaxRequestObj.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded");
				var data = "Action=" + action + "&ActionId=" + actionId + "&Para="
						+ para;
				AjaxRequestObj.send(data);
			}
		}
	</script>
</BODY>
</HTML>
