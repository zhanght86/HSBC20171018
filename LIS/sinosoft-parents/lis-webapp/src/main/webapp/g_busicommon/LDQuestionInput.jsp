<%
/***************************************************************
 * <p>ProName��LDQuestionInput.jsp</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tOtherNoType = request.getParameter("OtherNoType");
	String tOtherNo = request.getParameter("OtherNo");
	String tSubOtherNo = request.getParameter("SubOtherNo");
	String tActivityID = request.getParameter("ActivityID");
	String tShowStyle = request.getParameter("ShowStyle");
	String tShowFlag = request.getParameter("ShowFlag");
%>
<script>
	var tOtherNoType = "<%=tOtherNoType%>";
	var tOtherNo = "<%=tOtherNo%>";
	var tSubOtherNo = "<%=tSubOtherNo%>";
	var tActivityID = "<%=tActivityID%>";
	var tShowStyle = "<%=tShowStyle%>";
	var tShowFlag = "<%=tShowFlag%>";//�����ģ��ֻ��չʾʱʹ��
</script>
<html>
<head >
	<title>���������</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LDQuestionInput.js"></script>
	<%@include file="./LDQuestionInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<!--
				<td class=title>���������</td>
				<td class=input><input class=codeno name=QuesTrend readonly
					ondblclick="return showCodeList('questrend',[this, QuesTrendName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('questrend',[this, QuesTrendName],[0, 1],null,null,null,'1',180);"><input class=codename name=QuesTrendName readonly></td>
				-->
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=SendStartDate onClick="laydate({elem: '#SendStartDate'});" id="SendStartDate"><span class="icon"><a onClick="laydate({elem: '#SendStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</td>
				<td class=title>����ֹ��</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=SendEndDate onClick="laydate({elem: '#SendEndDate'});" id="SendEndDate"><span class="icon"><a onClick="laydate({elem: '#SendEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</td>
				<td class=title>�����״̬</td>
				<td class=input><input class=codeno name=QuesState id=QuesState readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('quesstate',[this, QuesStateName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('quesstate',[this, QuesStateName],[0, 1],null,null,null,'1',180);"><input class=codename name=QuesStateName id=QuesStateName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�ظ�����</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=ReplyStartDate onClick="laydate({elem: '#ReplyStartDate'});" id="ReplyStartDate"><span class="icon"><a onClick="laydate({elem: '#ReplyStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�ظ�ֹ��</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=ReplyEndDate onClick="laydate({elem: '#ReplyEndDate'});" id="ReplyEndDate"><span class="icon"><a onClick="laydate({elem: '#ReplyEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="��  ѯ" onclick="queryClick('1');">
		<input class=cssButton name=CloseButton type=button value="��  ��" onclick="top.close();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuestion);">
			</td>
			<td class=titleImg>������б�</td>
		</tr>
	</table>
	<div id="divQuestion" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuestionGrid"></span>
				</td>
			</tr>
		</table>
	</div>
	<br>
	<div id="divRequestDetail" style="display: ''">
		<table class=common>
			<tr class=common id="divQuesType" style="display: none">
				<td class=title>���������</td>
				<td class=input><input class=codeno name=QuesType id=QuesType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return returnShowCodeList('questype', [this,QuesTypeName],[0,1]);" 
					onkeyup="return returnShowCodeListKey('questype', [this,QuesTypeName],[0,1]);"><input class=codename name=QuesTypeName readonly></td>
				<td class=title><div id="divMistake" style="display: none"><input class=checkbox name=Mistake value='0' type=checkbox onclick="checkClick();">�Ƿ������</div></td>
				<td class=input></td>
				<td class=title><div id="divSubTypeName" style="display: none">��֤ϸ��</div></td>
				<td class=input><div id="divSubTypeCode" style="display: none">
					<input class=codeno name=SubType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return returnShowCodeList('subtype', [this,SubTypeName],[0,1]);" 
					onkeyup="return returnShowCodeListKey('subtype', [this,SubTypeName],[0,1]);"><input class=codename name=SubTypeName readonly>
					</div>
				</td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=SendContent id=SendContent></textarea></td>
			</tr>
			<tr class=common id="divReplyContent" style="display: none">
				<td class=title>�ظ�����</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=ReplyContent id=ReplyContent elementtype=nacessary></textarea></td>
			</tr>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton name=AddButton type=button value="��  ��" onclick="addClick();">
		<input class=cssButton name=ModifyButton type=button value="��  ��" onclick="modifyClick();">
		<input class=cssButton name=DeleteButton type=button value="ɾ  ��" onclick="deleteClick();">
		<input class=cssButton name=ReplyButton type=button value="��  ��" onclick="replyClick();">
		<input class=cssButton name=ShowButton type=button value="�Ի�չʾ" onclick="showClick();">
	</div>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
