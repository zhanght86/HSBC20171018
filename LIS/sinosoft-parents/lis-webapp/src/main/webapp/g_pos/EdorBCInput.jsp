<%
/***************************************************************
 * <p>ProName:EdorBCInput.jsp</p>
 * <p>Title:  ������ά��</p>
 * <p>Description:������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tEdorAppNo = request.getParameter("EdorAppNo"); 
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorType =  request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tCurrentDate = "<%=tCurrentDate%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head>
	<title>������ά��</title>
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
	<script src="./EdorBCInput.js"></script>
	<%@include file="./EdorBCInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="EdorBCSave.jsp" target=fraSubmit>
	<div id="divQueryOld" style="display: ''">
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
				<td class = input ><input class="wid common"  name=OldInsuredName id=OldInsuredName></td> 
				<td class=title>֤������</td>
				<td class = input ><input class="wid common"  name=OldInsuredIDNo id=OldInsuredIDNo></td>
				<td class=title>�������˿ͻ��� </td>
				<td class = input ><input class="wid common"  name=OldInsuredNo id=OldInsuredNo></td>
			</tr>
			<tr class=common>  
				<td class=title>���շ���</td>
				<td class=input><Input class="codeno" name="ContPlanCodeOld" id=ContPlanCodeOld style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeOldName,SysPlanCodeOld);" onkeyup="showContPlanCodeName(this,ContPlanCodeOldName,SysPlanCodeOld);">
				<input class=codename name=ContPlanCodeOldName id=ContPlanCodeOldName ><input type=hidden name=SysPlanCodeOld id=SysPlanCodeOld ></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
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
</div>

	<!--�޸Ĺ��ı���������Ϣ-->
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
		<input class=cssButton type=button value="��  ѯ" onclick="queryUpdateClick(1);">
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
	
	<!--�޸Ĺ�����������Ϣ -->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBnfUpdateInfo);">
			</td>
			<td class=titleImg>�޸Ĺ�����������Ϣ</td>
		</tr>
	</table>
	<div id="divBnfUpdateInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBnfUpdateInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage4.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage4.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage4.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage4.lastPage();">
		</center>
	</div>
	
	<table class=common>
				<tr class=common>
					<td id=td1 style="display: ''" class=title>��ȫ��Ч����</td>
				 	<td id=td2 style="display: ''" class=input><input class="coolDatePicker" dateFormat="short" name=edorValDate verify="��ȫ��Ч����|date&&notnull" elementtype=nacessary onClick="laydate({elem: '#edorValDate'});" id="edorValDate"><span class="icon"><a onClick="laydate({elem: '#edorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				 	<td class=title></td>
				 	<td class=input></td>
				 	<td class=title></td>
				 	<td class=input></td>
				</tr>
</table>
<div id="divShowButton" style="display: none">
	<input class=cssButton type=button value="��  ��" onclick="addRecord();">
	<input class=cssButton type=button value="��  ��" onclick="deleteRecord();">
</div>
	<!--������-->
	<input type=hidden name=Operate id=Operate>	
	<input type=hidden name=CurrentDate id=CurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=GrpPropNo id=GrpPropNo value="<%=tGrpContNo%>">
	<input type=hidden name=EdorAppNo id=EdorAppNo value="<%=tEdorAppNo%>">
	<input type=hidden name=EdorType id=EdorType value="<%=tEdorType%>">
	<input type=hidden name=EdorNo id=EdorNo value= "<%=tEdorNo%>">
	<input type=hidden name=InsuredOldName id=InsuredOldName>
	<input type=hidden name=IdOldNo id=IdOldNo>	
	<input type=hidden name=SerialNo id=SerialNo>	
	<input type=hidden name=BatchNo id=BatchNo>	
	
	<input type=hidden name=MissionID id=MissionID value="<%=tMissionID%>"> <!-- ��������ID -->
	<input type=hidden name=SubMissionID id=SubMissionID value="<%=tSubMissionID%>"> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID id=ActivityID value="<%=tActivityID%>"> <!-- �����ڵ�ID -->
	<input type=hidden name=InsuredNameTemp id=InsuredNameTemp>	
	<Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
