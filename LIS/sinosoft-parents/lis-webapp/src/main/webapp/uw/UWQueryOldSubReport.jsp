<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWQueryOldSubReport.jsp
//�����ܣ��˱�Ա���������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var PolNo = "<%=request.getParameter("PolNo")%>"; //��¼��½����
</script>

<head >
<title>�˱�Ա���������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="UWQueryOldSubReport.js"></SCRIPT>
  <%@include file="UWQueryOldSubReportInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action="./UWQuerySubReportSave.jsp">  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 �˱�Ա�����������ݣ�
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	
    	<Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=common VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=common VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	
      
    </div>

  <hr class="line">
  
  <table width="80%" height="20%" class= common>
    <TR  class= common> 
      <TD width="100%" height="15%"  class= title> �˱�Ա������������ </TD>
    </TR>
    <TR  class= common>
      <TD height="85%" colspan=3 class= title><textarea name="Content" id=Content cols="130" rows="25" class="common" readonly ></textarea></TD>
    </TR>
  </table>
  
  <Input type="hidden" id="ProposalNo" name=ProposalNo >
  <Input type="hidden" id="Operator" name=Operator >
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
