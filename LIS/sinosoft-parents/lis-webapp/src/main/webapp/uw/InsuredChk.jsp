<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�InsuredChk.jsp
//�����ܣ������˲���
//�������ڣ�2002-11-23 17:06:57
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	//String tContNo = "00000000000000000000";
	String tContNo = request.getParameter("ProposalNo1");
	String tFlag = request.getParameter("Flag");
	String tInsuredNo = request.getParameter("InsuredNo");	
	//loggerDebug("InsuredChk","proposalno:"+tPolNo);
	String tLoadFlag = request.getParameter("LoadFlag");
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("InsuredChk","operator:"+tGI.Operator);
%>

<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var tContNo = "<%=tContNo%>";
	var tFlag = "<%=tFlag%>";
	var tInsuredNo= "<%=tInsuredNo%>";
	var LoadFlag = "<%=tLoadFlag%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="AppntChk.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="AppntChkInit.jsp"%>
  
  <title>������У��</title>
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraTitle">
    <!-- ������Ϣ���� -->
        <table class= common>
        	<tr class= common>
        		ԭ��������Ϣ
        	</tr>
        </table>
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

        <table class= common>
        	<tr class= common>
        		��ͬ��������Ϣ
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
    	
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=common90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=common91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=common92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=common93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div>
      <table>
    	<tr>
    		<td class= titleImg>
    			<INPUT VALUE="ȷ�ϸ��±�����" class= cssButton TYPE=button onclick="sure();">
    			<INPUT value="�ͻ��ϲ�¼��" class = cssButton TYPE = button onclick="unitPrint();">
    			<INPUT type= "hidden" id="ProposalNo" name= "ProposalNo" value= "">
    			<INPUT type= "hidden" id="InsuredNo" name= "InsuredNo" value= "">    			
    			<INPUT type= "hidden" id="Flag" name= "Flag" value= "">
    		</td>
    	</tr>
      </table> 	 	
      				
    <table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>�������Ϣ</td>
		</tr>
	</table>
	 <table>
    	<TR  class= common>
	
          <TD id = divcustomernoname style = "display:none" class = title>
          	ԭ�ͻ�����
          </TD>
          <TD  id = divcustomerno style = "display:none" class= input>
            <Input class=common  id="CustomerNo" name=CustomerNo >
          </TD>
          <TD id=divnamename style = "display:none" class = title>
          	ԭ�ͻ�����
          </TD>
          <TD  id = divname style = "display:none" class= input>
            <Input class=common id="CustomerName" name=CustomerName >
          </TD>
          <TD id = divcurrentnoname style = "display:none" class = title>
          	��ͬ�ͻ�����
          </TD>
          <TD  id = divcurrentno style = "display:none" class= input>
            <Input class=common id="CurrentCustomerNo" name=CurrentCustomerNo >
          </TD>
           
        </tr>
      </table>
         <table width="121%" height="37%">
    <TR  class= common> 
    	 
      <TD id = divcontent style = "display:none" width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD id = divcontentname style = "display:none" height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
    
        <DIV id = "divSureButton" style = "display:none" >   
     	<INPUT  value="ȷ  ��" class = cssButton TYPE = button onclick="makesure();"> 				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

