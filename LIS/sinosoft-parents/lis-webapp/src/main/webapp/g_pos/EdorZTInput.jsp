<%
/***************************************************************
 * <p>ProName:EdorZTInput.jsp</p>
 * <p>Title:  ���ٱ�������</p>
 * <p>Description:���ٱ�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorType = request.getParameter("EdorType");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorNo =  request.getParameter("EdorNo");

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
	<title>���ٱ�������</title>
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
	<script src="./EdorZTInput.js"></script>
	<%@include file="./EdorZTInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
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
				<td class = input ><input class="wid common" id=OldInsuredName name=OldInsuredName></td> 
				<td class=title>֤������</td>
				<td class = input ><input class="wid common" id=OldInsuredIDNo name=OldInsuredIDNo></td>
				<td class=title></td>
				<td class =input></td>
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
			<td class=title>��ȫ��Ч����</td>
			<td class=input><input class=coolDatePicker name=EdorValDate dateFormat="short" verify="��ȫ��Ч����|DATE&NOTNULL" elementtype=nacessary onClick="laydate({elem: '#EdorValDate'});" id="EdorValDate"><span class="icon"><a onClick="laydate({elem: '#EdorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	<div id="divButton01" style="display: ''">
		<input class=cssButton type=button value="��  ��" onclick="deleteInsured();">
	</div>
 </div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInsuredInfoUp);">
			</td>
			<td class=titleImg>���ٱ������˲�ѯ����</td>
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
			<td class=titleImg>���ٱ���������Ϣ</td>
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
	<div id="divButton02" style="display: ''">
		<input class=cssButton type=button value="��  ��" onclick="deleteOperate();">
	</div>
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=EdorNo>
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
	<input type=hidden name=BatchNo>
<Br /><Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
