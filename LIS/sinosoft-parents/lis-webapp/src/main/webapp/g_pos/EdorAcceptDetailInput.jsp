<%
/***************************************************************
 * <p>ProName��EdorAcceptDetailInput.jsp</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String scantype = request.getParameter("ScanFlag");
	String tScanManageCom = request.getParameter("ScanManageCom");
	String tCurrentDate = PubFun.getCurrentDate();
	String tCurrentTime = PubFun.getCurrentTime();
%>
<style type="text/css">
	#MessageTable{ position:absolute; background-color:blue;}
</style>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tScanManageCom = "<%=tScanManageCom%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var scantype = "<%=scantype%>";
	var tCurrentDate = "<%=tCurrentDate%>";
	var tCurrentTime = "<%=tCurrentTime%>";
	if(scantype==null||scantype=="") {
		scantype = "0";
	}
</script>
<html>
<head >
	<title>��ȫ����</title>

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
	<script src="./EdorAcceptDetailInput.js"></script>
	<script src="./EdorCommonInput.js"></script>
	<%@include file="./EdorAcceptDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorAppInfo);">
			</td>
		 	<td class=titleImg>��ȫ������Ϣ</td>
		</tr>
	</table>
	<div id="divEdorAppInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��ȫ�����</td>
				<td class=input><input class="wid readonly" name=EdorAppNo id=EdorAppNo value="<%=tEdorAppNo%>" readonly></td>
				<td class=title>������</td>
				<td class=input><input class="wid readonly" name=PolicyNo id=PolicyNo readonly></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid readonly" name=AppntName id=AppntName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ���������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AppDate verify="�ͻ���������|DATE" elementtype=nacessary onClick="laydate({elem: '#AppDate'});" id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��˾��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ReceiveDate verify="��˾��������|DATE" elementtype=nacessary onClick="laydate({elem: '#ReceiveDate'});" id="ReceiveDate"><span class="icon"><a onClick="laydate({elem: '#ReceiveDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>���뷽ʽ</td>
				<td class=input><input class=codeno name=AppMode id=AppMode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('appmode',[this, AppModeName],[0, 1],null,null,null,'1',180);"  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
					onkeyup="return showCodeListKey('appmode',[this, AppModeName],[0, 1],null,null,null,'1',180);"><input class=codename name=AppModeName readonly elementtype=nacessary></td>
			</tr>
		</table>
		
		<input class=cssButton name=ConfButton type=button style="display: none" value="ȷ  ��" onclick="confClick();">
		<input class=cssButton name=ScanQueryButton type=button value="Ӱ�����ѯ" onclick="queryScanPage();">
		<input class=cssButton name=ReturnButton type=button value="��  ��" onclick="returnClick();">
	</div>
	
	<div id="divEdorAppDetailInfo" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butCollapse.gif" style="cursor:hand;" onclick="showPage(this, divPolicyInfo);">
				</td>
			 	<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<div id="divPolicyInfo" class=maxbox1 style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>������Ч����</td>
					<td class=input><input class="wid readonly" name=ValDate id=ValDate readonly></td>
					<td class=title>�ڳ�����</td>
					<td class=input><input class="wid readonly" name=InitNumPeople id=InitNumPeople readonly></td>
					<td class=title>�ڳ�����(Ԫ)</td>
					<td class=input><input class="wid readonly" name=InitPrem id=InitPrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>������ֹ����</td>
					<td class=input><input class="wid readonly" name=EndDate id=EndDate readonly></td>
					<td class=title>��ǰ����</td>
					<td class=input><input class="wid readonly" name=NumPeople id=NumPeople readonly></td>
					<td class=title>��ǰ����(Ԫ)</td>
					<td class=input><input class="wid readonly" name=Prem id=Prem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>����״̬</td>
					<td class=input><input class="wid readonly" name=ContState id=ContState readonly></td>
					<td class=title>��������ȫ����</td>
					<td class=input><input class="wid readonly" name=EdorTimes id=EdorTimes readonly></td>
					<td class=title>�ɷѷ�ʽ</td>
					<td class=input><input class="wid readonly" name=PayIntv id=PayIntv readonly></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ񶨽�</td>
					<td class=input><input class="wid readonly" name=BanlanceFlag id=BanlanceFlag readonly></td>
					<td class=title>������˷ѹ���</td>
					<td class=input><input class="wid readonly" name=AfterClmRule id=AfterClmRule readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorTypeGrid);">
				</td>
				<td class=titleImg>��ȫ��Ŀ��Ϣ</td>
			</tr>
		</table>
		<div id="divEdorTypeGrid" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanEdorTypeGrid"></span>
					</td>
				</tr>
			</table>
			<div id= "divEdorTypeGridPage" style= "display: ;text-align:center">
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</div>
			<br>
			<table class=common>
				<tr class=common>
					<td class=title>��ȫ��Ŀ</td>
					<td class=input><input class=codeno name=EdorType id=EdorType readonly  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
						ondblclick="return showCodeList('edorcode',[this, EdorTypeName, HidEdorLevel, HidValDateFlag],[0, 1, 2, 3],null,'1 and b.appobj in(#G#,#C#) and exists(select 1 from lpedorapp m,lcgrppol n where n.grpcontno=m.otherno and n.riskcode=a.riskcode and m.edoracceptno=#<%=tEdorAppNo%>#)','1','1',180);" 
						onkeyup="return showCodeListKey('edorcode',[this, EdorTypeName, HidEdorLevel, HidValDateFlag],[0, 1, 2, 3],null,'1 and b.appobj in(#G#,#C#) and exists(select 1 from lpedorapp m,lcgrppol n where n.grpcontno=m.otherno and n.riskcode=a.riskcode and m.edoracceptno=#<%=tEdorAppNo%>#)','1','1',180);"><input class=codename name=EdorTypeName readonly elementtype=nacessary></td>
					<td class=title id="divEdorValDateTitle" style="display: none">��ȫ��Ч����</td>
					<td class=input id="divEdorValDateInput" style="display: none"><input class=coolDatePicker dateFormat=short name=EdorValDate verify="��ȫ��Ч����|DATE" elementtype=nacessary onClick="laydate({elem: '#EdorValDate'});" id="EdorValDate"><span class="icon"><a onClick="laydate({elem: '#EdorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title id="divGetObjTitle" style="display: none">�˷�֧����ʽ</td>
					<td class=input id="divGetObjInput" style="display: none"><input class=codeno name=GetObj readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
						ondblclick="return showCodeList('sfobj',[this, GetObjName],[0, 1],null,null,null,'1',180);" 
						onkeyup="return showCodeListKey('sfobj',[this, GetObjName],[0, 1],null,null,null,'1',180);"><input class=codename name=GetObjName readonly elementtype=nacessary></td>
					<td class=title id="divTD1" style="display: ''"></td>
					<td class=input id="divTD2" style="display: ''"></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			
			<input class=cssButton name=AddEdorTypeButton type=button value="��ӱ�ȫ��Ŀ" onclick="addEdorType();">
			<input class=cssButton name=EdorTypeDetailButton type=button value="��ȫ��Ŀ��ϸ" onclick="detailClick();">
			<input class=cssButton name=DelEdorTypeButton type=button value="ɾ����ȫ��Ŀ" onclick="delEdorType();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input class=cssButton name=InsuredListButton type=button value="��Ա�嵥����" onclick="edorInsuredList();">
			<input class=cssButton type=button value="���������" onclick="goToQuestion();">
			<br>
			<input class=cssButton type=button value="�������" onclick="acceptClick();">
		</div>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=HidGrpContNo>
	<input type=hidden name=DelEdorType>
	<input type=hidden name=HidEdorLevel>
	<input type=hidden name=HidValDateFlag>
	<input type=hidden name=InsuAccFlag>
	<input type=hidden name=MissionID value="<%=tMissionID%>">
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>">
	<input type=hidden name=ActivityID value="<%=tActivityID%>">
	<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
