<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWManuRReport.jsp
//�����ܣ�����Լ�˹��˱������ٱ��ʱ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������ ln   ��������  2008-11-04   ����ԭ��/���� ��������������޸�
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="UWManuUpReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> ����Լ������鱨�� </title>
  <%@include file="UWManuUpReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWManuUpReportChk.jsp">
    <!-- �����˱���¼���֣��б� -->
   <table class= common border=0 width=100%>
     <tr>
		  <td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divUWSpec1);"></td> <td class=titleImg align=center>�ٱ�������Ϣ</td>
	   </tr>
   </table>
   <div id="divUWSpec1" class="maxbox">
   <table  class=common border=0 align=center>
     <tr>
       <td class=title>ӡˢ��</td>
       <TD  class=input>
         <Input class= "readonly wid" readonly id="PrtNo" name=PrtNo >      
         <input type='hidden' aclass="codeno" id="SellType" name="SellType" verify="���۷�ʽ|notnull" verifyorder="1"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input type="hidden" id="sellTypeName" name="sellTypeName" class="codename" readonly="readonly">
       </TD>	  
      <!--td class="title">���۷�ʽ</td>
������<td class="input"-->
      <!--/td-->     
       <TD class = title> ҵ��Ա���� </TD>
       <TD  class=input><Input class="readonly wid" readonly id="AgentCode" name=AgentCode ></TD>     
       <TD class = title>�������</TD>
       <TD  class=input><Input class="readonly wid" readonly id="ManageCom" name=ManageCom ></TD>
     </tr>
     <tr>       
      <TD class = title> �������� </TD>
       <TD  class=input><Input class="readonly wid" readonly id="SaleChnl" name=SaleChnl ></TD>
       <TD class = title> �ͻ����� </TD>
       <TD  class=input><Input class="readonly wid" readonly id="CustomerName" name=CustomerName ></TD>
     </tr>
     <!--

     <tr>                     
       <td class= title>��������</td>
       <TD  class= input><Input class= "readonly" name=BankCode ></TD>
     </tr>
     -->
    </table>
   <table class=common align="center">     
     <tr class= common>
	       <td class=title>�ʱ�ԭ��</td>
	       <TD  class=input><input class="codeno" id="ReportReason" name="ReportReason" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('ReportReason',[this,ReportReasonDes],[0,1])" onkeyup="return showCodeListKey('ReportReason',[this,ReportReasonDes],[0,1])"><input id="ReportReasonDes" name="ReportReasonDes" class="codename" readonly="readonly">
	       </TD>
	       <td><input type='hidden'></td>	 
	       <td><input type='hidden'></td>   
	 </tr>
    </table>
    </div>
      <table class=common>         
         <TR  class= common> 
           <TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;�ʱ�ԭ��������800������,�س���ռһ�����֣� </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="ReportRemark" cols="120" rows="3" class="common" ></textarea>
           </TD>
         </TR>
      </table>
      
   
      <INPUT type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
      <INPUT type= "hidden" id="SubNoticeMissionID" name= "SubNoticeMissionID" value= "">
      <INPUT type= "hidden" id="Flag" name= "Flag"  value = "">
      <INPUT type= "button" id="sure" name= "sure" value="ȷ  ��" class= cssButton onclick="submitForm()">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
