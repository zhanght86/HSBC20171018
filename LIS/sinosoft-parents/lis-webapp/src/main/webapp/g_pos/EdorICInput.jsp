<%
/***************************************************************
 * <p>ProName:EdorICInput.jsp</p>
 * <p>Title:  ����������Ҫ���ϱ��</p>
 * <p>Description:����������Ҫ���ϱ��</p>
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
	<title>����������Ҫ���ϱ��</title>
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
	<script src="./EdorICInput.js"></script>
	<%@include file="./EdorICInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="EdorICSave.jsp" target=fraSubmit>
	
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
				<td class = input ><input class ="wid common"  name=OldInsuredNameQ id=OldInsuredNameQ></td> 
				<td class=title>֤������</td>
				<td class = input ><input class ="wid common"  name=OldInsuredIDNoQ id=OldInsuredIDNoQ></td>
				<td class=title>�������˿ͻ��� </td>
				<td class = input ><input class ="wid common"  name=OldInsuredNoQ id=OldInsuredNoQ></td>
			</tr>
			<tr class=common>  
				<td class=title>���շ���</td>
				<td class=input><Input class="codeno" name="ContPlanCodeOldQ" id=ContPlanCodeOldQ style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeOldName,SysPlanCodeOldQ);" onkeyup="showContPlanCodeName(this,ContPlanCodeOldName,SysPlanCodeOldQ);"><input class=codename name=ContPlanCodeOldName >
				<input type=hidden name=SysPlanCodeOldQ id=SysPlanCodeOldQ ></td>
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
				<td class=input><input class="wid common" name=InsuredNameQ id=InsuredNameQ></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=InsuredIDNoQ id=InsuredIDNoQ></td>
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
	
	<!--�޸ĺ󱻱����˻�����Ϣ-->
	<div id="divBBNewInfoInsured" style="display: ''">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divBBOldInsured);">
			</td>
			<td class=titleImg>�޸ĺ󱻱����˻�����Ϣ</td>
		</tr>
	</table>
	<div id="divBBNewInsured" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td id=td1 style="display: ''" class=title>��ȫ��Ч����</td>
				 	<td id=td2 style="display: ''" class=input><input class="coolDatePicker" dateFormat="short" name="edorValDate" verify="��ȫ��Ч����|date&&notnull" elementtype=nacessary onClick="laydate({elem: '#edorValDate'});" id="edorValDate"><span class="icon"><a onClick="laydate({elem: '#edorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				 	<td class=title></td>
				 	<td class=input></td>
				 	<td class=title></td>
				 	<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>������������</td>
					<td class=input><input class="wid common" name="InsuredName"  verify="������������|len<=40" onkeydown="selectUser();" ></td>	
					<td class=title>֤������</td>
					<td class=input><input class=codeno name="IDType" id=IDType verify="֤������|code:IDType" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="clearInput(IDNo,IDTypeName); showCodeList('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" onkeyup="clearInput(IDNo,IDTypeName); return showCodeListKey('idtype',[this,IDTypeName],[0,1],null, null, null, '1', null);" ><input class=codename name=IDTypeName readonly=true ></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name="IDNo" id=IDNo  verify="֤������|len<=20"  onblur="checkidtype();"  ></td>
				</tr>
				<tr class=common>
					<td class=title>�Ա�</td>
					<td class=input><input class=codeno name="InsuredGender" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('sex',[this,InsuredGenderName],[0,1],null, null, null, '1', null);"  ><input class=codename name="InsuredGenderName" readonly=true ></td>	
					<td class=title>��������</td>
					<td class= input><input class="coolDatePicker" dateFormat="short" name="InsuredBirthDay" verify="��������|date" onClick="laydate({elem: '#InsuredBirthDay'});" id="InsuredBirthDay"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>����</td>
					<td class=input><input class="wid readonly" name="InsuredAppAge"  readonly ></td>	
				</tr>
				<tr class= common>
					<td class=title>ְҵ����</td>
					<td class=input><input class=codeno name="OccupationCode" id=OccupationCode ><input class=codename name=OccupationCodeName onkeydown="showOccupationCodeList(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" onkeyup="showOccupationCodeListKey(fm.OccupationCode,fm.OccupationCodeName,fm.OccupationType,fm.OccupationTypeName)" ></td>	          
					<td class=title>ְҵ���</td>
					<td class=input><input type=hidden name="OccupationType" id=OccupationType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('occuptype',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('occuptype',[this,OccupationTypeName],[0,1]);" ><input class="wid readonly" name=OccupationTypeName readonly=true ></td>
 					<input type=hidden id="date" name=date value="<%=tCurrentDate%>">
 					<td class=title>��н��Ԫ��</td>
					<td class=input><input class="wid common" name=Salary  verify="��н|LEN<=12" onblur="checkNumber(this);"></td>
 				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class=codeno name="ServerArea" id=ServerArea style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('serverarea',[this,ServiceArName],[0,1],null, null, null, '1', null);" ><input class=codename name=ServiceArName readonly=true></td>
					<td class=title>�Ƿ�α�׼��</td>
					<td class=input><input class=codeno name="Substandard" id=Substandard style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,SubstandardName],[0,1],null, null, null, '1', null);" ><input class=codename name=SubstandardName readonly=true></td>
					<td class=title>�籣���</td>
					<td class=input><input class=codeno name="Social" id=Social style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('haveflag',[this,SocialName],[0,1],null, null, null, '1', null);" ><input class=codename name=SocialName readonly=true></td>	
				</tr>
			</table>	
	</div>
</div>
<div id="divShowButton" style="display: ''">
	<input class=cssButton type=button value="��  ��" onclick="addRecord();">
	<input class=cssButton type=button value="��  ��" onclick="deleteRecord();">
</div>
	<!--������-->
	<input type=hidden name=Operate>	
	<input type=hidden name=CurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=GrpPropNo value="<%=tGrpContNo%>">
	<input type=hidden name=EdorAppNo value="<%=tEdorAppNo%>">
	<input type=hidden name=EdorType value="<%=tEdorType%>">
	<input type=hidden name=EdorNo value= "<%=tEdorNo%>">
	<input type=hidden name=InsuredOldName>
	<input type=hidden name=IdOldNo>	
	<input type=hidden name=SerialNo>		
	<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- ��������ID -->
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- �����ڵ�ID -->
	<input type=hidden name=InsuredNameTemp>
	<input type=hidden name=BatchNo>
	<Br /><Br /><Br /><Br />
	
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
