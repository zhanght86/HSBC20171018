<%
/***************************************************************
 * <p>ProName��LLClaimNoMediclInput.jsp</p>
 * <p>Title����ҽ����ͨ�˵�</p>
 * <p>Description����ҽ����ͨ�˵�</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom = request.getParameter("ManageCom");
	String tOperator = tGI.Operator;
	String mCurrentDate = PubFun.getCurrentDate();
  String tAccidentDate = request.getParameter("AccidentDate"); 
  String tRgtNo = request.getParameter("RgtNo"); 
  String tCaseNo= request.getParameter("CaseNo"); 
  String tCustomerNo = request.getParameter("CustomerNo"); //�����˱���
  String tMode= request.getParameter("Mode"); 
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //��¼��½����
	var tOperator = "<%=tOperator%>";  //��¼������
	var tCurrentDate = "<%=mCurrentDate%>"; 	//ϵͳʱ��
	var tRgtNo ="<%=tRgtNo%>";
	var tAccidentDate ="<%=tAccidentDate%>";
	var tCustomerNo ="<%=tCustomerNo%>";
	var tCaseNo ="<%=tCaseNo%>";
	var tMode ="<%=tMode%>";
</script>
<html>
<head>
	<title>��ҽ����ͨ�˵�</title>
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
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimNoMedicalInput.js"></script>
	<%@include file="./LLClaimNoMedicalInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
				<img src="../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this, divMaimInfo);">
				</td>
				<td class=titleImg>�˲�¼����Ϣ</ td>
			</tr>
		</table>
	
		<div id="divMaimInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanMaimInfoGrid"></span>
					</td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button id=addMaimBill1 value="��  ��" onclick="addMaimBill();">
				<input class=cssButton91 type=button id=modifyMaimBill1 value="��  ��" onclick="modifyMaimBill();">
				<input class=cssButton92 type=button id=deleteMaimBill1 value="ɾ  ��" onclick="deleteMaimBill();">
				<input class=cssButton93 type=button id=resetDefoInfo1 value="��  ��" onclick="resetDefoInfo();">
			</center>
			<table class=common> 
				<tr class=common>
					<td class=title>�м�����</td>
	  			<td class=input><input class=codeno id=DefoType name=DefoTypeCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('defotype',[this,DefoTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('defotype',[this,DefoTypeName],[0,1],null,null,null,1);"  readonly><input class=codename name=DefoTypeName  elementtype=nacessary readonly></td>
	  			<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
	  		</tr>
				<tr class=common>	
					<td class= title style="display:none" id=DefoClass>�˲з���</td>
	 				<td class=input style="display:none" id=DefoClassCode ><input class=codeno name=DefoClassCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('llparadeformity1',[this,DefoClassName],[0,1],null,fm.DefoType.value,1,1);" onkeyup="return showCodeListKey('llparadeformity1',[this,DefoClassName],[0,1],null,fm.DefoType.value,1,1);"  readonly><input class=codename name=DefoClassName ></td>
					<td class=title>�˲м���</td>
					<!-- <td class=input><input class=codeno name=DefoGradeCode ondblclick="showCodeList('llparadeformity2',[this,DefoGradeName],[0,1],null,fm.DefoType.value,1,1);" onkeyup="return showCodeListKey('llparadeformity2',[this,DefoGradeName],[0,1],null,fm.DefoType.value,1,1);"  readonly><input class=codename name=DefoGradeName elementtype=nacessary></td> -->
					<td class=input><input class=codeno name=DefoGradeCode  readonly><input class=codename name=DefoGradeName onkeydown="queryDefoGradeInfo(this);" elementtype=nacessary></td> 
					<td class=title>�˲д���</td>
					<td class=input><input class=codeno name=DefoCode  readonly><input class=codename name=DefoName onkeydown="queryDefoGradeInfo(this);" 	elementtype=nacessary></td>
				</tr>		
				<tr class=common>
					<td class=title>�м���������</td>
					<td class=input><input class="wid readonly" name=defoRate readonly></td>
					<td class=title>��������</td>
					<td class=input><input class="wid common" name=JudgeOrganName></td>
					<td class=title>��������</td>
					<td class=input><input class="coolDatePicker" dateFormat="short" name=JudgeDate  verify="��������|not null" elementtype=nacessary  maxlength="10" onClick="laydate({elem: '#JudgeDate'});" id="JudgeDate"><span class="icon"><a onClick="laydate({elem: '#JudgeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					
				</tr>
			</table>
		</div>
	
		<table>
			<tr>
				<td class=common>
					<img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divMajorBill);">
				</td>
				<td class= titleImg>�ش󼲲���Ϣ</ td>
			</tr>
		</table>
	
		<div id="divMajorBill" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanMajorBillGrid"></span>
					</td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button id=addMajorBill1 value="��  ��" onclick="addMajorBill();">
				<input class=cssButton91 type=button id=modifyMajorBill1 value="��  ��" onclick="modifyMajorBill();">
				<input class=cssButton92 type=button id=deleteMajorBill1 value="ɾ  ��" onclick="deleteMajorBill();">
				<input class=cssButton93 type=button id=resetMajorInfo1 value="��  ��" onclick="resetMajorInfo();">
			</center>
			<table class=common> 
				<tr class=common>
					<td class=title>�ؼ�����</td>
					<td class=input><input class=codeno  name=OperationType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('stricken',[this,OperationTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('stricken',[this,OperationTypeName],[0,1],null,null,null,1);"  readonly><input class=codename name=OperationTypeName  elementtype=nacessary readonly></td>
					<td class=title>�ؼ�����</td>
					<td class=input><input class=codeno  name=OperationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llserioustype',[this,OperationName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llserioustype',[this,OperationName],[0,1],null,null,null,1);"  readonly><input class=codename name=OperationName elementtype=nacessary></td>
		<!--			<td class=title>���</td>
					<td class=input><input class=common name=OpFee onblur="checkNumber(this);" elementtype=nacessary></td>-->
					<td class=title>ҽ�ƻ���</td>
					<td class=input><input class="wid common" name=UnitName elementtype=nacessary></td>
				</tr>		
				<tr class=common>
					<td class=title>ȷ������</td>
					<td class=input><input class="coolDatePicker" name=DiagnoseDate elementtype=nacessary onClick="laydate({elem: '#DiagnoseDate'});" id="DiagnoseDate"><span class="icon"><a onClick="laydate({elem: '#DiagnoseDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
		<input class=cssButton type=button value="��  ��" onclick=top.close()>
		<Input type=hidden  name=Operate> 	 	 <!--��������-->
		<Input type=hidden  name=tCustomerNo>
		<Input type=hidden  name=tRgtNo>
		<Input type=hidden  name=tCaseNo>
		<Input type=hidden  name=tDefoSerialNo>
		<Input type=hidden  name=tMajorSerialNo>
		
	</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
