<%
//�������ƣ�AbnormityErrAndRecordErr.jsp
//�����ܣ��쳣������ԭ���ѯ�Լ���¼�����
//�������ڣ�2007-08-01 14:32:57
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";  
	String tContNo = "00000000000000000000";
	String tPrtNo=(String)request.getParameter("prtNo");
	//tPrtNo="";
	loggerDebug("AbnormityErrAndRecordErr","ҳ���õ�ӡˢ��Ϊ"+tPrtNo);
	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var prtNo="<%=tPrtNo%>";
	var BussNoType = "<%=request.getParameter("BussNoType")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="AbnormityErrAndRecordErr.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="AbnormityErrAndRecordErrInit.jsp"%>
  
  <title>�쳣������ԭ���ѯ�Լ���¼�����</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm1 target="fraSubmit">
   <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
             <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
            </td>
            <td class= titleImg align= center>�쳣������ԭ��</td>
		</tr>
	</table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
       <Div  id= "divPage1" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
  </Div> 
  	</div>	
  </form>  
  
  <form method=post name=fm id="fm" target="fraSubmit" action="./AbnormityErrAndRecordErrSave.jsp">
   <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
             <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
            </td>
            <td class= titleImg align= center>����¼</td>
		</tr>
	</table>
  	<Div  id= "divLCPol" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanErrGrid" >
  					</span> 
  			  	</td>
  			</tr>
  			<tr class=common>
  			    <td>
  			       <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onClick="submitForm();">
  			    </td>
  		   </tr> 
    	</table>    
    	<Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage1.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage1.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage1.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage1.lastPage();">     
  </Div> 					
  	</div>
  	<p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "BussNoType" value= "">    
  </p>
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
