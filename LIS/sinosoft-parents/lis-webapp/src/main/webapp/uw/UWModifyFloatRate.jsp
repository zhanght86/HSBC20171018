<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2008-11-3 11:10:36
//������  ��liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
//�����¸���
String tContNo=request.getParameter("ContNo");
loggerDebug("UWModifyFloatRate","ContNo:"+tContNo);
String tLoadFlag = request.getParameter("LoadFlag");
loggerDebug("UWModifyFloatRate","tLoadFlag:"+tLoadFlag);
String tPolNo = request.getParameter("SelPolNo");
loggerDebug("UWModifyFloatRate","tPolNo: "+tPolNo);
String tInsuredNo = request.getParameter("InsuredNo");
loggerDebug("UWModifyFloatRate","InsuredNo:"+tInsuredNo);
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //���˵��Ĳ�ѯ����.
var polNo="<%=tPolNo%>";
var insuredNo = "<%=tInsuredNo%>";
var operator="<%=tGI.Operator%>";   //��¼����Ա
var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="UWModifyFloatRate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="UWModifyFloatRateInit.jsp"%>
<title>���������޸�</title>
</head>
<body  onload="initForm();" >
	<form action="./UWModifyFloatRateSave.jsp" method=post id="fm" name=fm target="fraSubmit">
		<!-- ������Ϣ���� -->
		<table>
			<tr>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class=titleImg>Ͷ������Ϣ</td>
			</tr>
			<INPUT type="hidden" class=Common id="MissionID" name=MissionID  value="">
			<INPUT type="hidden" class=Common id="SubMissionID" name=SubMissionID  value="">
		</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFloatRateGrid" ></span>
					</td>
				</tr>
			</table>
			<center>
			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
			</center>
		<p>
		 <input class="cssButton" value="Ա����Լ¼��" type="button" id="Button1" name="Button1" onclick="specInput();">
			<INPUT VALUE="��   ��" class=cssButton TYPE=button onclick="return submitForm();">
			<INPUT VALUE="ɾ��Ա����Լ" class=cssButton TYPE=button onclick="return deleteSpec();">
			<!--INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"-->
		</p>
		<div id = "divChangeResult" style = "display: none">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		Ա����Լ:
          	</TD>
		<tr></tr>
      		<td  class= input><textarea name="FloatRateIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    	 </table>
     </div>
		<input class=common type=hidden id="tFlag" name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden id="Operator" name=Operator >
		<Input class=common type=hidden id="ProposalNo" name=ProposalNo >
		<Input class=common type=hidden id="ContNo" name=ContNo value="<%=tContNo%>">
		<Input class=common type=hidden id="InsuredNo" name=InsuredNo value="<%=tInsuredNo%>">
		<Input class=common type=hidden id="PolNo" name=PolNo >
		<Input class=common type=hidden id="SamePersonFlag" name=SamePersonFlag >
		<Input class=common type=hidden id="NewFloatRate" name=NewFloatRate >
		<Input class=common type=hidden id="DivFlag" name=DivFlag value = "1" >
		<Input class=common type=hidden id="SpecFlag" name=SpecFlag value = "1" >
		<Input class=common type=hidden id="SpecType" name=SpecType value = "" >
		<Input class=common type=hidden id="SpecCode" name=SpecCode value = "" >
		<Input class=common type=hidden id="SpecReason" name=SpecReason value = "" >
		<Input class=common type=hidden id="SerialNo" name=SerialNo value = "" >
		<Input class=common type=hidden id="SpecOperate" name=SpecOperate value = "INSERT" >
		<Input class=common type=hidden id="GetDutyKind" name=GetDutyKind value = "INSERT" >
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
