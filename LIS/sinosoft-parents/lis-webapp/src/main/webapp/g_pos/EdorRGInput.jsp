<%
/***************************************************************
 * <p>ProName:EdorRGInput.jsp</p>
 * <p>Title:  ������ȡ</p>
 * <p>Description:������ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-27
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
	<script src="./EdorRGInput.js"></script>
	<%@include file="./EdorRGInit.jsp"%>
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
	
	<table class=common>
		<tr class=common>
			<td class=title>��ȡ��ʽ</td>
			<td class=input><input class=codeno name=ExpiryGetMode id=ExpiryGetMode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeList('expirygetmode',[this, ExpiryGetModeName],[0, 1],null,null,null,'1',180);"
				onkeyup="return showCodeListKey('expirygetmode',[this, ExpiryGetModeName],[0, 1],null,null,null,'1',180);"><input class=codename name=ExpiryGetModeName id=ExpiryGetModeName elementtype=nacessary readonly></td>
			<td class=title id="TransModeTitle" style="display: none">ת������</td>
			<td class=input id="TransModeInput" style="display: none"><input class=codeno name=TransMode id=TransMode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeList('transmode',[this, TransModeName],[0, 1],null,null,null,'1',180);"
				onkeyup="return showCodeListKey('transmode',[this, TransModeName],[0, 1],null,null,null,'1',180);"><input class=codename name=TransModeName id=TransModeName elementtype=nacessary readonly></td>
			<td class=title id="TransAmountTitle" style="display: none">ת�����</td>
			<td class=input id="TransAmountInput" style="display: none"><input class="wid common" name=TransAmount id=TransAmount elementtype=nacessary></td>
			<td class=title id="td1" style="display: ''"></td>
			<td class=input id="td2" style="display: ''"></td>
			<td class=title id="td3" style="display: ''"></td>
			<td class=input id="td4" style="display: ''"></td>
		</tr>
		<tr class=common id="divPayMode" style="display: none">
			<td class=title>֧����ʽ</td>
			<td class=input><input class=codeno name=ExpiryPayMode id=ExpiryPayMode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeList('expirypaymode',[this, ExpiryPayModeName],[0, 1],null,null,null,'1',180);"
				onkeyup="return showCodeListKey('expirypaymode',[this, ExpiryPayModeName],[0, 1],null,null,null,'1',180);"><input class=codename name=ExpiryPayModeName id=ExpiryPayModeName elementtype=nacessary readonly></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
		<tr class=common id="divGrpBank" style="display: none">
			<td class=title>��λ��������</td>
			<td class=input><input class="wid readonly" name=GrpBankName id=GrpBankName readonly></td>
			<td class=title>��λ������</td>
			<td class=input><input class="wid readonly" name=GrpBankAccName id=GrpBankAccName readonly></td>
			<td class=title>��λ�����˺�</td>
			<td class=input><input class="wid readonly" name=GrpBankAccNo id=GrpBankAccNo readonly></td>
		</tr>
	</table>
	
	<div id="divModifyButton" style="display: none">
		<input class=cssButton type=button value="������ȡ" onclick="getClick();">
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
				<td class=input><input class="wid common" id=InsuredName name=InsuredName></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" id=InsuredIDNo name=InsuredIDNo></td>
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
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
