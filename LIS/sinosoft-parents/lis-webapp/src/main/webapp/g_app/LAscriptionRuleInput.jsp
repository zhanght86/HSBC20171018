<%
/***************************************************************
 * <p>ProName��LAscriptionRuleInput.jsp</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tOperator = tGI.Operator;
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContPlanType = request.getParameter("ContPlanType");
	String tBussNo = request.getParameter("BussNo");
	String tBussType = request.getParameter("BussType");
	String tQueryFlag = request.getParameter("QueryFlag");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tContPlanType = "<%=tContPlanType%>";
	var tBussNo = "<%=tBussNo%>";
	var tBussType = "<%=tBussType%>";
	var tOperator = "<%=tOperator%>";
	var tQueryFlag = "<%=tQueryFlag%>";
</script>
<html>
<head>
	<title>��������ά��</title>
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
	<script src="./LAscriptionRuleInput.js"></script>
	<%@include file="./LAscriptionRuleInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
 <form method=post name=fm id=fm target="fraSubmit" action="">
 <div style="margin-left:10px">
	<table class=common>
		<tr class=common>
			<td class=common>
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divPositionGrid);">
			</td>
			<td class= titleImg>������ְ����Ϣ</td>
		</tr>
	</table>	
	<div id="divPositionGrid" style="display: ''"> 
		<table class= common>
			<tr class= common>
				<td text-align: left colSpan=1>
					<span id="spanPositionGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		<br>
		<table class=common>	
			<tr class=common>
				<td class=title>ְ������</td>
				<td class=input><input class="wid common" id=PositionCode name=PositionCode elementtype=nacessary verify="ְ������|notnull&len<=4" ></td>
				<td class=title>ְ������</td>
				<td class=input><input class="wid common" id=PositionCodeName name=PositionCodeName elementtype=nacessary verify="ְ������|notnull"></td>
				<td class=title>������ȼ��㷽ʽ</td>
				<td class=input><input class=codeno name=CountType id=CountType readonly verify="������ȼ��㷽ʽ|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('counttype',[this,CountTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('counttype',[this,CountTypeName],[0,1],null,null,null,'1',null);">
				<input class=codename name=CountTypeName id=CountTypeName elementtype=nacessary ></td>
			</tr>	
		</table>
		<div id= "divAccascriptionSave" style= "display: none" >
			<Input VALUE="��  ��" class=cssButton type=button onclick="addPosition();">
			<Input VALUE="��  ��" class=cssButton type=button onclick="updatePosition();">
			<Input VALUE="ɾ  ��" class=cssButton type=button onclick="deletePosition();">
		</div>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick= "showPage(this,divAscriptionGrid);">
			</td>
			<td class= titleImg>��ͨ����������</td>
		</tr>
	</table>
	<div id="divAscriptionGrid" style="display: ''">
		<table class= common>
			<tr class= common>
				<td text-align: left colSpan=1>
					<span id="spanAscriptionGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
		<br>
		<table class= common>
			<tr class=common>
				<td class=title>ְ������</td>
				<td class=input><input class=code name=AscriptionCode id=AscriptionCode readonly style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="getData(); return showCodeListEx('AscriptionCode',[this,AscriptionCodeName],[0,1],null,null,null,1,null);" onkeyup="getData(); return showCodeListKeyEx('AscriptionCode',[this,AscriptionCodeName],[0,1],null,null,null,'1',null);" elementtype=nacessary> </td>
				<td class=title>ְ������</td>
				<td class=input><input class="wid readonly" id=AscriptionCodeName name=AscriptionCodeName ></td>
				<td class=title></td>
				<td class=input></td>	
			</tr>
			<tr class=common>
				<td class=title>��ʼֵ��>��</td>
				<td class=input><input class="wid common" name=StartYears id=StartYears elementtype=nacessary></td>
				<td class=title>��ֵֹ���ܣ�</td>
				<td class=input><input class="wid common" name=EndYears id=EndYears  elementtype=nacessary></td>	
				<td class=title>��������</td>
				<td class=input><input class="wid common" name=Rate id=Rate elementtype=nacessary></td>
			</tr>
		 </table>
		<div  id= "divRiskSave" style= "display: none"> 
			<input type=button class="cssButton" value="��  ��" onclick="saveAscription();">
			<input type=button class="cssButton" value="��  ��" onclick="updateAscription();">
			<input type=button class="cssButton" value="ɾ  ��" onclick="deleteAscription();">
		</div>
	</div> 
	<hr class=line />
	<br />
	<div id="divMultiAgent2" style="display:''">
		<tr> 
			<td class="title">��������������빴ѡ���빴ѡ</td>
			<td class="title"><input type="checkbox" name="multiageflag" value="true" onclick="sepicalTpye();"></td>
		</tr>
	</div>
	<br />
	<div id="DivshowSepical" style="display:none">
		<table>
			<tr>
				<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divSepicalType);"></ td>
				<td class= titleImg>�������������</ td>
			</tr>
		</table> 
		<div id="divSepicalType" name=divSepicalType style="display: ''">
			<table class= common>
				<tr class= common>
					<td text-align: left colSpan=1> 
						<span id="spanSepicalAscriptionGrid" ></span>  
					</td>
				</tr>
			</table> 
		<br>
			<table class= common>
				<tr  class= common> 
					<td class=title>��������</td>
						<td class=input><input class=codeno name=SepicalType id=SepicalType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" return showCodeList('spepositioncode',[this,SepicalTypeName],[0,1],null,null,null,'1',null);" onkeyup=" return showCodeListKey('spepositioncode',[this,SepicalTypeName],[0,1],null,null,null,'1',null);">
						<input class=codename name=SepicalTypeName id=SepicalTypeName  elementtype=nacessary></td>
						<td class=title>��������</td>
						<td class=input><input class="wid common" id=Rate1 name=Rate1 ></td>
						<td class=title></td>
						<td class=input></td>
				</tr>
			</table>	 	
			<div  id= "divSepicalSave" style= "display: none" align= left> 
				<input type=button class="cssButton" value="��  ��" onclick="saveSpeAscription();">
				<input type=button class="cssButton" value="��  ��" style= "display: ''" onclick="updateSpeAscription();">
				<input type=button class="cssButton" value="ɾ  ��" style= "display: ''" onclick="deleteSpeAscription();">
		 	</div>
		</div>
	</div>
	<br />
	<hr class=line />
		<input type=button class="cssButton" value="��  ��" style= "display: ''" onclick="top.close();">
		<input type=hidden name=Operate id=Operate>
		<input type=hidden name="mOperate" id=mOperate>
		<input type=hidden name="tOperate" id=tOperate>
		<input type=hidden name=RiskCode id=RiskCode>
</div>
</form>
<br /><br /><br /><br /><br />
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
