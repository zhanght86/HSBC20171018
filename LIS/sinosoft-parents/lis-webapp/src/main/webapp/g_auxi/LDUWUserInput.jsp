<%
/***************************************************************
 * <p>ProName��LDUWUserInput.jsp</p>
 * <p>Title���˱��û�����</p>
 * <p>Description���˱��û�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tManageCom = tGI.ManageCom;
%>
<script>
	var tOperator = "<%=tOperator%>";
	var tManageCom = "<%=tManageCom%>";
</script>
<html>
<head>
	<title>�˱��û�����</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LDUWUserInput.js"></script>
	<%@include file="./LDUWUserInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWUser);">
				</td>
				<td class=titleImg>��ѯ����</td>
			</tr>
		</table>
		<div id="divUWUser" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�û�����</td>
					<td class=input><input class="wid common" name=UserCode1 id=UserCode1></td>
					<td class=title>�û�����</td>
					<td class=input><input class="wid common" name=UserName1 id=UserName1></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="��  ѯ" onclick="querySubmit();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWUserList);">
				</td>
				<td class=titleImg>�˱��û��б�</td>
			</tr>
		</table>
		<div id="divUWUserList" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUWUserGrid"></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWUserInfo);">
				</td>
				<td class=titleImg>�˱��û���Ϣ</td>
			</tr>
		</table>
		<div id="divUWUserInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�û�����</td>
					<td class=input><input class="wid readonly" name=UserCode id=UserCode readonly></td>
					<td class=title>�û�����</td>
					<td class=input><input class="wid readonly" name=UserName id=UserName readonly></td>
					<td class=title>�Ƿ�˱�����</td>
					<td class=input><input class=codeno name=SupervisorFlag id=SupervisorFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('trueflag',[this,SupervisorName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,SupervisorName],[0,1],null,null,null,1);"><input class=codename name=SupervisorName readonly elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>�˱�����</td>
					<td class=input><input class=codeno name=PopedomLevel id=PopedomLevel style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('popedomlevel',[this,PopedomLevelName],[0,1],null,'1','1',null);" onkeyup="return showCodeListKey('popedomlevel',[this,PopedomLevelName],[0,1],null,'1','1',null);"><input class=codename name=PopedomLevelName readonly elementtype=nacessary></td>	
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="��  ��" name="AddButton" onclick="addSubmit();">
			<input class=cssButton type=button value="��  ��" name="ModButton" onclick="modSubmit();">
			<input class=cssButton type=button value="ɾ  ��" name="DelButton" onclick="delSubmit();">
			<input class=cssButton type=button value="�û���ѯ" onclick="queryUser();">	
		</div>
	</div>
	<input type=hidden name=Operate>
	<input type=hidden name=SerialNo>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
