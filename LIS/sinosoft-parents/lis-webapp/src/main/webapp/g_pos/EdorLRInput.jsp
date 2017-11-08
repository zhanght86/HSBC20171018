<%
/***************************************************************
 * <p>ProName:EdorLRInput.jsp</p>
 * <p>Title:  ��������</p>
 * <p>Description:��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-26
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
	String tEdorNo = request.getParameter("EdorNo");
	String tGrpContNo = request.getParameter("GrpContNo");

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
	<title>��������</title>
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
	<script src="./EdorLRInput.js"></script>
	<%@include file="./EdorLRInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryCont);">
			</td>
			<td class=titleImg>��������</td>
		</tr>
	</table>
	<div id="divQueryCont" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title><input type="checkBox" id="GrpLR"  name="GrpLR" onclick="showGrpLR();">��������</td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class="wid readonly" id=Times name="Times"></td>
				<td class=input><input type=checkBox id="NeedGetMoney"  name="NeedGetMoney" onclick="showMoney();">��ȡ������������</td>
				<td class=title></td>
				<td class= title>��������(Ԫ)</td>
				<td class=input><input  name="Money" id=Money value='0' class="wid readonly" readonly></td>
			</tr>				
		</table>
	</div>
	<table class=common>
		<tr class=common>
			<td class=input><input type="checkBox" id="PerLR" onclick="showHiddenInfo();" name="PerLR">����ƾ֤����</td>
			<td class=title></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>

<div id=hiddenInfo style="display:none">
	<div id="showOldList" style="display:''">	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
				</td>
				<td class=titleImg>��ѯ����</td>
			</tr>
		</table>
		<div id="divQuery" class=maxbox1 style="display: ''">
			<div id="div01" style="display: ''">
				<table class=common>
					<tr class=common>  
						<td class=title>������������</td>
						<td class = input ><input class ="wid common"  name="OldInsuredName" id=OldInsuredName></td> 
						<td class=title>֤������</td>
						<td class = input ><input class ="wid common"  name="OldInsuredIDNo" id=OldInsuredIDNo></td>
						<td class=title>���շ���</td>
						<td class=input><Input class=codeno name=ContPlanCode id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);">
						<input class=codename name=ContPlanCodeName ><input type=hidden name=sysPlanCode ></td>
					</tr>
					<tr class=common>
						<td class=title>�������˿ͻ���</td>
						<td class = input ><input class ="wid common" id=CustomernNo  name="CustomernNo"></td>
						<td class=title></td>
						<td class = input ></td>
						<td class=title></td>
						<td class = input ></td> 
					</tr>
				</table>
				<input class=cssButton type=button value="��  ѯ" onclick="queryOldClick();">	
			</div>
		<br/>
			<div id="div02" style="display: ''">
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
				<input class=cssButton type=button value="����ƾ֤����" onclick="contClick();">
				<input class=cssButton type=button value="����ƾ֤����" onclick="grpContClick();">
			</div>
		</div>
	</div>
	
	<!--���������б�-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>�����б�</td>
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
		<div id="divButton03" style="display: none">
			<input class=cssButton type=button value="��������ƾ֤" onclick="deleteOperate();">
		</div>
	</div>
	<hr class=line>
	
</div>
	<!--����ԭ��-->
	<table class=common>
		<tr class=common>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
		<tr class=common>
			<td class=title>����ԭ��</td>
			<td class=input colspan=5><textarea cols=80 rows=3 name=Reason id=Reason verify="����ԭ��|notnull" elementtype=nacessary></textarea></td>
		</tr>
	</table>
	<div id="divButton01" style="display: none">
 	 	<Input type=button  class = cssButton value="��  ��" onclick="saveClick();">
 		<Input type=button  class = cssButton value="��  ��" onclick="top.close();">
	</div>
	<div id="divButton02" style="display: none">
 		<Input type=button  class = cssButton value="��  ��" onclick="top.close();">
	</div>
	
	<!--������-->
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=EdorNo>
	<input type=hidden name=GrpLRFlag value='0'><!-- ����������� -->
	<input type=hidden name=FeeLRFlag value='0'><!-- �����������ñ�� -->
	<input type=hidden name=PreLRFlag value='0'><!-- ����ƾ֤��� -->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
