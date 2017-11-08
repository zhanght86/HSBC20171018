<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
	String action =  request.getParameter("action");
	if(action == null) action = "query";  
	String lineID = request.getParameter("id");
	String lineName = request.getParameter("name");
	String pid = request.getParameter("pid");
	String pver = request.getParameter("pver");
	String ctype = request.getParameter("ctype");
	String condition = request.getParameter("condition");
	String condDesc = request.getParameter("desc");
	String ftype = request.getParameter("ftype");
	if (lineID == null)
		lineID = "";
	if (lineName == null)
		lineName = "";
	if (pid == null)
		pid = "";
	if (pver == null)
		pver = "";
	if (ctype == null)
		ctype = "";
	if (condition == null)
		condition = "";
	if (condDesc == null)
		condDesc = "";
	if (ftype == null)
		ftype = "";

	lineName = new String(lineName.getBytes("iso8859-1"), "UTF-8");
	condition = new String(condition.getBytes("iso8859-1"), "UTF-8");
	condDesc = new String(condDesc.getBytes("iso8859-1"), "UTF-8");

	if (lineID.startsWith("l"))
		lineID = lineID.substring(1);

	String from = lineID.substring(0, lineID.indexOf("and"));
	String to = lineID.substring(lineID.indexOf("and") + 3);

	//兼容历史数据，如果conditionType为空，则LWCondition中获取信息
	if ("".equals(ctype)) {
		ExeSQL exesql = new ExeSQL();
		Map condMap = exesql.getOneRowData(
				"select transitioncondt,transitioncond,conddesc from lwcondition where processid = '" + pid
						+ "' " + " and transitionstart = '" + from + "' and transitionend = '" + to
						+ "' and version = '" + pver + "'");
		ctype = (String) condMap.get("transitioncondt");
		condition = (String) condMap.get("transitioncond");
		condDesc = (String) condMap.get("condDesc");
		if (ctype == null)
			ctype = "";
		if (condition == null)
			condition = "";
		if (condDesc == null)
			condDesc = "";
	}
	if ("".equals(ctype)) {
		ctype = "0";
	}
	System.out.println("++++-----" + lineID + ":" + ":" + from + ":" + to + ":" + lineName + ":" + pid + ":"
			+ pver + ":" + ctype + ":" + condition + ":" + condDesc + "------+++++");
	String Oper = "";
	GlobalInput _gi = (GlobalInput)session.getAttribute("GI");
	if (_gi != null) {
		Oper = _gi.Operator;
	}
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

<BODY onload='' onunload=''>
	<TABLE border=0 width="100%" height="100%">
		<TR>
			<td>&nbsp;</td>
		</TR>

		<TR valign=top>
			<TD></TD>
			<TD width="100%" valign=top>
				<fieldset style="border:none">
					<legend align=left style="font-size: 9pt;color:#1faeff">&nbsp;联线属性&nbsp;</legend>
					<table class="maxbox1" width="100%" height="100%" style="font-size: 9pt;">
						<tr valign=top>
							<td width=5></td>
							<td>流向编号&nbsp;&nbsp;<input type="text" name="lineID" class="common" style="300px" 
								id="lineID" value="<%=lineID%>" class='linedialog' readonly></td>
						</tr>
						<tr valign=top>
							<td></td>
							<td>流向名称&nbsp;&nbsp;<input TYPE="text" NAME="lineName" class="common" style="300px" 
								id="lineName" value="<%=lineName%>" class='linedialog' readonly
								></td>
						</tr>
						<tr height="2px">
						</tr>
						<tr valign=top height="22px">
							<td width=5></td>
							<td>转移类型&nbsp;
								<select name="conditionType" id="conditionType" class='linedialog common' onchange="rule();" style="300px" 
									<%if("query".equals(action) || "rebuild".equals(action)) {%>disabled=disabled <%}%>>
									<option value="0">0_默认(SQL)</option>
									<option value="1" <%if ("1".equals(ctype)) {%> selected <%}%>>服务类</option>
									<option value="2" <%if ("2".equals(ctype)) {%> selected <%}%>>规则引擎</option>
									<option value="F" <%if ("F".equals(ctype)) {%> selected <%}%>>虚边，永假</option>
								</select>
							</td>
						</tr>
						<tr valign=top>
							<td width=5></td>
							<td>转移条件&nbsp;&nbsp;
								<textarea NAME="condition" id="condition" class='linedialog common' style="width: 300px"
									<%if("query".equals(action)  || "rebuild".equals(action)) {%>readonly=readonly <%}%>><%=condition%></textarea>
							</td>
						<tr valign=top>
							<td width=5></td>
							<td>转移描述&nbsp;&nbsp;
								<textarea NAME="condDesc" id="condDesc" class='linedialog common' style="width: 300px"
									<%if("query".equals(action) || "rebuild".equals(action)) {%>readonly=readonly <%}%>><%=condDesc%></textarea>
							</td>
						</tr>
						<tr height="3">
							<td></td>
							<td></td>
						</tr>
					</table>
				</fieldset>
			</TD>
		</TR>
	</TABLE>
	<table id="Rule1" border=0 width="100%" height="30%"
		style="display: none">
		<TR>
			<td>&nbsp;</td>
		</TR>
		<tr valign=top>
			<td></td>
			<td width="100%" valign=top>
				<fieldset>
					<legend align=left style="font-size: 9pt;color:#1faeff">&nbsp;规则引擎定义&nbsp;</legend>
					<table class="maxbox1" width="100%" height="100%" style="font-size: 9pt;">
						<tr>
							<td width=5></td>

							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<!--<input type="button" id="searchs" value="查询" onclick="jscript:search();">-->
								<input type=button class="cssButton"
								value="&nbsp;&nbsp;规则定义&nbsp;&nbsp;" onclick="CalCode();" <%if("query".equals(action)  || "rebuild".equals(action)) {%>disabled=disabled <%}%>>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
								type=button class="cssButton"
								value="&nbsp;&nbsp;生成新规则&nbsp;&nbsp;" onclick="CreateNewCode();" <%if("query".equals(action) || "rebuild".equals(action)) {%>disabled=disabled <%}%>>
							</td>
						</TR>
					</table>
				</fieldset>
			</td>
		</tr>
	</table>
	<table cellspacing="1" cellpadding="0" border="0">
		<TR>
			<td>&nbsp;</td>
		</TR>
		<tr>
			<td width="55%"></td>
			<td><input id="btnOk" class="cssButton" type="button" value="确定"
				onclick="okOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input id="btnCancel" class="cssButton" type="button" value="取消"
				onclick="cancelOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input id="btnApply" class="cssButton" type="button" value="应用"
				onclick="applyOnClick();" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	<span id="spanCode" style="display: none; position: absolute;"></span>
	<script>
		function rule() {
			var tCaltype = document
					.getElementById("conditionType").selectedIndex;
			if (tCaltype == 2) {
				document.getElementById("Rule1").style.display = "";
			} else {
				document.getElementById("Rule1").style.display = "none";
			}
		}
		
		function okOnClick() {
			<%if(!"query".equals(action) && !"rebuild".equals(action)) {%>
				applyOnClick("close");
			<%}else {%>
				window.top.close();
			<%}%>
		}
		
		function cancelOnClick() {
			window.top.close();
		}
		
		function applyOnClick(action) {
			var lineID = document.getElementById("lineID").value;
			if (lineID == null || lineID === '') {
				alert("信息不完整");
				return false;
			}
			SaveClick(action);
		}
		
		function SaveClick(action) {
			<%if(!"query".equals(action) && !"rebuild".equals(action)) {%>
				var lineID = document.getElementById("lineID").value;
				var conditionType = document.getElementById("conditionType").value;
				var condition = document.getElementById("condition").value;
				var condDesc = document.getElementById("condDesc").value;
				
				var req = InitRequest();
				req.open("POST", "linedialogsave.jsp", true);
				req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				req.onreadystatechange = function() {
					if (req.readyState == 4 &&  req.status == 200){
						var result =  req.responseText;
						var showStr = result.substr(1,5);
						if(showStr==("设置成功!")){
							if (window.top.opener._loadLink(null, null, lineID, null,
									condition, conditionType, condDesc)){
								if("close" == action){
									window.top.close();
								}
								return true;
							}
							else
								return false;
				        }else{
				        	alert("设置失败!");
				        	return false;
				          }
				      }
				}
				
				var postdata = "OperFlag=MODIFY||Condition"
								+"&StartActivityID=<%=from%>"
								+"&ProcessId=<%=pid%>"
								+"&EndActivityID=<%=to%>"
								+"&Version=<%=pver%>"
								+"&BusiType=<%=ftype%>"
								+"&TransitionCond="+condition
								+"&TransitionCondT="+conditionType
								+"&CondDesc="+condDesc;
				req.send(postdata);
			<%}%>
		}
		
		function CreateNewCode() {
			var mCalCodeType = "N";
			var tCaltype = document.getElementById("conditionType").value;
			if (tCaltype == 2) {
				var mCalCodeType = "Y";
			}
			var tCalCode = document.getElementById("condition").value;
			if (tCalCode == null || tCalCode == "") {
				var req = InitXMLHttp();
				req.open("POST", "linedialogsave.jsp", true);
				req.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded");
				req.onreadystatechange = function() {
					if (req.readyState == 4
							&& req.status == 200) {
						var result = req.responseText.replace(/\r\n/g,"");
						result = result.substr(0, 11);
						document.getElementById("condition").value = result;
					}
				}
				req.send("OperFlag=Create||Condition&mCalCodeType=" + mCalCodeType);
			} else {
				alert("算法已存在")
			}
		}
		
		function CalCode() {
			var CalCodeCode = document.getElementById("condition").value;
			if (CalCodeCode == null || CalCodeCode == '') {
				alert('算法编码为空，请生成新算法!');
				return;
			}
			if (DutyGraceswitchCalCodeType(CalCodeCode)) {
				var tUrl = "../ibrms/IbrmsPDAlgoDefiMain.jsp?RuleName="
						+ CalCodeCode + "&Creator=<%=Oper%>&Business=03&State=0&RuleType=1";
			}
			 window.open(tUrl);
		}
		
		function DutyGraceswitchCalCodeType(tCalCode) {
			if (tCalCode.length >= 2
					&& tCalCode.substring(0, 2).toUpperCase() == 'RU') {
				return true;
			} else {
				return false;
			}
		}
	</script>
</BODY>
</HTML>
