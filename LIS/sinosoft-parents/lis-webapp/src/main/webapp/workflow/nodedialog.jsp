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
<TITLE>��������ҳ</TITLE>
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
					<LEGEND align=left style="font-size: 9pt;color:#1faeff">&nbsp;�ڵ�����&nbsp;</LEGEND>
					<TABLE  width="100%" height="100%" class="maxbox1">
						<TR valign=top>
							<TD width=5></TD>
							<TD>�ڵ���<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT 
								class="common" style="width:300px;background: url("../common/images/select--bg_03.png") no-repeat right;"
								NAME="nodeId"  id="nodeId" 
								<%
								if("".equals(nodeID))
								{%>
									value="��ѡ��"
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
							<TD>�ڵ�����<font color=red>&nbsp;*</font>&nbsp;&nbsp;<INPUT
								id="nodeN"
								class="common" style="width:300px"
								NAME="nodeN" value="<%=nodeName %>" readonly=readonly></TD>
							<TD></TD>
						</TR>
						
						<TR valign=top>
							<TD></TD>
							<TD>�ڵ�����<font color=red>&nbsp;*</font><INPUT TYPE="radio"
								NAME="nodeType" value="begin" disabled><span
								style="font-size: 9pt; COLOR: #919CD0">��ʼ�ڵ�&nbsp;</span><INPUT
								TYPE="radio" NAME="nodeType" value="end" disabled><span
								style="font-size: 9pt; COLOR: #919CD0">�����ڵ�&nbsp;</span><INPUT
								TYPE="radio" NAME="nodeType" value="course" checked disabled><span
								style="font-size: 9pt; COLOR: #919CD0" id="nodeType">���̽ڵ�&nbsp;</span>
								<!--<INPUT TYPE="radio" NAME="nodeType" value="judge"  disabled>���߽ڵ�&nbsp;--></TD>
							<TD></TD>
						</TR>
						<TR valign=top>
							<TD width=5></TD>
							<TD>ʱЧ����&nbsp;&nbsp;&nbsp;&nbsp;<SELECT NAME="timeType"
								class="readonly" style="width:300px;height:26px;line-height:26px;"
								disabled><option value="01" selected>���ʱЧ</option>
									<option value="02">����ʱЧ</option></SELECT></TD>
							<TD></TD>
						</TR>
						<TR valign=top>
							<TD></TD>
							<TD>��תʱЧ&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE="text"
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
			<td><input id="btnOk" class="cssButton" type="button" value="ȷ��"
				onclick="jscript: okOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input id="btnCancel" class="cssButton" type="button" value="ȡ��"
				onclick="jscript: cancelOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input id="btnApply" class="cssButton" type="button" value="Ӧ��"
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
					alert("��Ϣ������");
					return false;
				}
				if (window.top.opener._loadNode(<%/*ע�⣺��һ�������Ƿ���ֵ�����������������޸�*/if("".equals(nodeID)){%>(has?"new":null)<%}else{%>"new"<%}%>, "course", 15,15,nodeId,nodeName,"01",time,null)){
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
				alert("ҵ�����Ͳ���Ϊ��")
				return false;
			}
			return true;
		}

		function checkTime() {
			var time = document.getElementById("time").value;
			if (time != null && time != "") {//����͸�ʽΪ HH+MM
				var check = /^\d+\-\d+/;
				if (!check.test(time)) {
					alert("ʱЧ��ʽӦ��Ϊ'Сʱ-����'!");
					return false;
				}
			}
			return true;
		}
	</script>
</BODY>
</HTML>
