<%
/***************************************************************
 * <p>ProName:EdorRDInput.jsp</p>
 * <p>Title:  ������ȡ</p>
 * <p>Description:������ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tCurrenDate = PubFun.getCurrentDate();
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tCurrenDate = "<%=tCurrenDate%>";
</script>
<html>
<head>
	<title>������ȡ</title>
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
	<script src="./EdorRDInput.js"></script>
	<%@include file="./EdorRDInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
<div id="divQueryOld" style="display: none">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>ԭ�������˲�ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>  
				<td class=title>������������</td>
				<td class=input ><input class="wid common" name=OldInsuredName id=OldInsuredName></td> 
				<td class=title>֤������</td>
				<td class=input ><input class="wid common" name=OldInsuredIDNo id=OldInsuredIDNo></td>
				<td class=title>�������˿ͻ���</td>
				<td class=input ><input class="wid common" name=OldInsuredNo id=OldInsuredNo></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="��  ѯ" onclick="queryOldClick();">
	</div>
	
	<br/>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult);">
			</td>
			<td class=titleImg>ԭ����������Ϣ</td>
		</tr>
	</table>
	<div id="divResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOldInsuredInfoGrid" ></span>
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
	<br/>
	
	<div id="divModifyButton" style="display: none">
		<input class=cssButton type=button value="ѡ  ��" onclick="selectClick();">
	</div>
</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInsuredInfoUp);">
			</td>
			<td class=titleImg>�޸Ĺ��ı������˲�ѯ����</td>
		</tr>
	</table>
	<div id="divInsuredInfoUp" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=InsuredIDNo id=InsuredIDNo></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="��  ѯ" onclick="queryUpClick(1);">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	<br>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>�޸Ĺ��ı���������Ϣ</td>
		</tr>
	</table>
	<div id="divResultUpdate" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanUpdateInsuredInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	</div>
	
	<div id="divCancelButton" style="display: none">
		<input class=cssButton type=button value="��  ��" onclick="deleteOperate();">
	</div>
	
		<!--�ɷ�����Ϣ-->
	<div id="divPayPlan" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPayPlanInfo);">
				</td>
				<td class=titleImg>�ɷ�����Ϣ</td>
			</tr>
		</table>
		<div id="divPayPlanInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPayPlanGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		
		<div id=divPayPlanSaveButton style="display: none"><input class=cssButton type=button value="��ȡ��Ϣ����" onclick="payPlanSave();"></div>
	</div>
	
	<!--Ͷ����Ϣ-->
	<div id="divInvest" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInvestInfo);">
				</td>
				<td class=titleImg>Ͷ���˻���Ϣ</td>
			</tr>
		</table>
		<div id="divInvestInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanInvestGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=EdorNo>
	<input type=hidden name=BatchNo>
	<input type=hidden name=InsuredID>
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
	<br /><br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
