<html>
<%
//�������ƣ�LLSecondUWInput.jsp
//�����ܣ���ͬ���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <head >
	<%
	//##############BGN#########################
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI"); 
	String  tCaseNo = request.getParameter("CaseNo"); //�ⰸ��
 	String tInsuredNo= request.getParameter("InsuredNo"); //�����˱���
	String tInsuredName = request.getParameter("InsuredName");
	String tRptorName = request.getParameter("RptorName"); //����������
	String tMissionID=request.getParameter("MissionID");//������id
	loggerDebug("LLSecondUWInput","JSP�е�tRptorName====================>"+tRptorName);
	//tInsuredName =  new String(tInsuredName.getBytes("ISO-8859-1"),"GB2312"); //����������
	//tRptorName =  new String(tRptorName.getBytes("ISO-8859-1"),"GB2312"); //����������
	//###############END######################
	%>
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
   <SCRIPT src="LLSecondUW.js"></SCRIPT>
   <%@include file="LLSecondUWInit.jsp"%>
 </head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit"> 
	<Table>
		<TR>
			<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);" ></TD>
			<TD class= titleImg>�ñ��������µ����к�ͬ</TD>
		</TR>
	</Table>
	<Div  id= "divCont" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLCContGrid" ></span></TD>
			</TR>
		</Table>
		
	</Div>
	<br>
	<Table class=title>
		<TR  class= common>
			<TD  class= title> �ͻ�Ŀǰ����״������ </TD>
		</TR>
		<TR  class= common>
			<TD  class= input><textarea name="Note" id="Note" cols="224" rows="4" class="common" ></textarea></TD>
		</TR>
	</Table>
	<!--<Table style="display:none">
		<TR>
			<input name="" class=cssButton type=button value="������ѯ" onclick="showPolDetail();">
			<input name="" class=cssButton type=button value="��ȫ��ϸ" onclick="showPolBqEdor();">	 	
		 	<input name="" class=cssButton type=button value="ȷ    ��" onclick="LLSeUWSaveClick()">
		 	<input name="" class=cssButton type=button value="��    ��" onclick="top.close();">
		</TR>
	</Table>-->
    <br>
    <a href="javascript:void(0);" class="button" onClick="showPolDetail();">������ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="showPolBqEdor();">��ȫ��ϸ</a>
    <a href="javascript:void(0);" class="button" onClick="LLSeUWSaveClick();">ȷ    ��</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>
	<!--****��������*****-->
	<Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
 	<Input type=hidden id="CaseNo" name="CaseNo" ><!--�ⰸ��-->
 	<Input type=hidden id="InsuredNo" name="InsuredNo" ><!--�����˺���-->
 	<Input type=hidden id="InsuredName" name="InsuredName" ><!--����������-->
	<Input type=hidden id="RptorName" name="RptorName" ><!--����������-->
	<Input type=hidden id="CurrentDate" name="CurrentDate" ><!--�����������-->
	<Input type=hidden id="MissionID" name="MissionID" >
	
</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
