<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AppntChk.jsp
//�����ܣ�Ͷ���˲���
//�������ڣ�2002-11-23 17:06:57
//������  ��WHN
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
	//String tContNo = "00000000000000000000";
	String tContNo = request.getParameter("ProposalNo1");
	String tFlag = request.getParameter("Flag");
	//loggerDebug("AppntChk","proposalno:"+tPolNo);
	String tLoadFlag = request.getParameter("LoadFlag");
	String tInsuredNo = request.getParameter("InsuredNo");
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("AppntChk","operator:"+tGI.Operator);
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	//alert(grpPolNo);
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	//alert(contNo);
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var tContNo = "<%=tContNo%>";
	var tFlag = "<%=tFlag%>";
	var LoadFlag = "<%=tLoadFlag%>";
	var tInsuredNo = "<%=tInsuredNo%>";
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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="AppntChkInit.jsp"%>
  
  <title>�ظ��ͻ�У��</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="AppntChkUnionQuestInputSave.jsp">
    <!-- ������Ϣ���� -->
	  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 ԭ�ͻ���Ϣ 
	    	</td>
	    </tr>
	    </table>    
	<div id= "divOPolGrid" style= "display:  " >
        <table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </div>	
	  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 �ظ��ͻ���Ϣ
	    	</td>
	    </tr>
	    </table>    

  	<Div  id= "divPolGrid" style= "display:  ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: none ">
      <input CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div>
      </Div>
      <table>
    	<tr>
    		<td class=titleImg>
    			<!--INPUT VALUE=" �ϲ��ͻ� " class= cssButton TYPE=button onclick="customerUnion();"-->
    			<input type= "hidden" name= "ProposalNo" id=ProposalNo value= "">
    			<input type= "hidden" name= "Flag" id=Flag value= "">
    			<INPUT type= "hidden" name= "InsuredNo" id=InsuredNo value= "">    			
    		</td>
    	</tr>
      </table> 	
      
<Div  id= "divCustomerUnion" style= "display:   "> 
	  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerUnionInfo);">
	    	</td>
	    	<td class= titleImg>
	    	 �ϲ��ͻ���Ϣ 
	    	</td>
	    </tr>
	    </table>        
	<div id= "divCustomerUnionInfo" class=maxbox1 style= "display:  " >
	 <table class= common>
    	<TR  class= common>
          <TD class = title> �ͻ����� </td>
          <TD class= input><input class="readonly wid"  name=CustomerName id=CustomerName readonly ></td>    	
          <TD class= title> ԭ�ͻ����� </td>
          <TD class= input><input class="readonly wid"  name=CustomerNo_OLD id=CustomerNo_OLD readonly ></td>
          <TD class= title> �ϲ���ͻ����� </td>
          <TD class= input><input class="readonly wid"  name=CustomerNo_NEW id=CustomerNo_NEW readonly ></td>
        </tr>
        <tr>
          <td class="title">�ϲ�����</td>
          <td class="input">
			      <input type="radio" name="exchangeRadio"  checked  value="1"  OnClick="exchangeCustomerNo();" >���� 
			      <input type="radio" name="exchangeRadio"  value="-1" OnClick="exchangeCustomerNo();">����          
		      </td>
        </tr>
      </table>		
	</div>
	<div id= "divCustomerUnionIssue" style= "display: none" >
		<table>
			<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerUnionIssueInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 �ϲ��ͻ��������Ϣ 
		    	</td>
		    </tr>
		</table> 	
	
	<div id= "divCustomerUnionIssueInfo" style= "display:  " >	
		<table width="121%" height="37%" class= common>
		    <TR  class= common> 
		      <TD width="100%" height="13%"  class= title> ��������� </td>
		    </TR>
		    <TR  class= common>
		      <TD height="87%"  class= title><textarea name="CUIContent" id=CUIContent cols="100" rows="10" class="common" >����Ͷ��������*******���������ҹ�˾Ͷ��������������********�������ṩ�������֤��ӡ�����Ա��ҹ�˾���к˶�</textarea></td>
		    </TR>
		</table>      	
	</div>
	</div>
	<div id="divBtCustomerUnion" style= "display:  " >	
	
		<input value="��д֪ͨ��" id="button2" class = cssButton TYPE = button onclick="sendCustomerUnionIssue();">
		<input value="����" id="button4" class = cssButton TYPE = button onclick="sendCustomerUnionIssue1();" disabled="disabled">
		<input value="���Ϳͻ��ϲ�֪ͨ��" id="button5" class = cssButton TYPE = button onclick="sendCustomerUnionIssue2();" disabled="disabled">
    <input value="     ��  ��       " id="button3" class="cssButton" type="button" onclick="top.close();">
	</div>

	<div id= "divBtCustomerUnionSendIssue" style= "display: none" >	
		<P>
			<input value="ȷ�Ϻϲ��ͻ�" id="button1"  class = cssButton TYPE = button onclick="customerUnionSubmit();"> 
		<!--input value="���������" class = cssButton TYPE = button onclick="showCustomerUnionIssueSend();"-->
		<!--input value="  ȡ  ��  " class = cssButton TYPE = button onclick="showCustomerUnion();"-->
	 </P>
	
	</div>
</DIV>      
      
    </DIV>  
        <DIV id = "divSureButton" style = "display:none" >   
			<!--������-->
			<input name ="SureFlag" type="hidden">     				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
