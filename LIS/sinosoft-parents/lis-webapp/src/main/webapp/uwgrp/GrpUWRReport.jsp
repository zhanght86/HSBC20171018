<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWManuRReport.jsp
//�����ܣ�����Լ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpUWRReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> ����Լ������鱨�� </title>
  <%@include file="GrpUWRReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./GrpUWRReportChk.jsp">
    <!-- �����˱���¼���֣��б� -->
     <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>����������Ϣ</td>
	</tr>
    </table>
    <table>
    	<tr class= common>
    	    <td class= title>   Ͷ������ </td>
            <TD  class= input>  <Input class= "readonly" name=ContNo >  </TD>
    	     <td class= title>   ӡˢ���� </td>
            <TD  class= input>  <Input class= "readonly" name=PrtNo >  </TD>
    	    <td class= title>   �˱���  </td>
            <TD  class= input>   <Input class= "readonly" name=Operator >  </TD>
    	</tr>
    </table>
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>	 ������Ŀ¼��</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  							<span id="spanInvestigateGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
    </div>
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
     
      <INPUT type= "hidden" name= "MissionID" value= "">
      <INPUT type= "hidden" name= "SubMissionID" value= "">
      <INPUT type= "hidden" name= "SubNoticeMissionID" value= "">
      <INPUT type= "hidden" name= "Flag"  value = "">
      <INPUT type= "button" name= "sure" value="ȷ��" class= common onclick="submitForm()">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
