<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");	
%>
<script>
  var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
</script>
<head>
<title>ʵ���ܱ���Ϣ��ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./FinFeePayQueryLJAGet.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FinFeePayQueryLJAGetInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id="fm" action=./FinFeePayQueryLJAGetResult.jsp target=fraSubmit method=post>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</TD>
    		<TD class= titleImg>
    			 �������ѯ����
    		</TD>
    	</TR>
    </Table> 
  <div id="maxbox" class="maxbox1"> 
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
          ʵ������:
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ActuGetNo id="ActuGetNo" >
          </TD>
          <TD  class= title5>
          ���ѷ�ʽ:
          </TD>
          <TD  class= input5>
          <Input class="code" name=PayMode id="PayMode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('PayMode',[this]);" onKeyUp="return showCodeListKey('PayMode',[this]);"  ondblclick="return showCodeList('PayMode',[this]);" onKeyUp="return showCodeListKey('PayMode',[this]);">
          </TD> 
        </TR>
      	<TR  class= common>
          <TD  class= title5>
          ��������:
          </TD>
          <TD  class= input5>
         <Input class="common wid" name=OtherNo id="OtherNo" >
          </TD>      	      
       </TR>                     
   </Table>  
 </div>
     <td class=button >&nbsp;&nbsp;</td>
      <INPUT VALUE="��  ѯ" Class=cssButton TYPE=button onClick="easyQueryClick();"> 
      <td class=button >&nbsp;&nbsp;</td>
      <INPUT VALUE="��  ��" Class=cssButton TYPE=button onClick="returnParent();">   
      <td class=button >&nbsp;&nbsp;</td>
      <INPUT VALUE="��  ��" Class=cssButton TYPE=button onClick="top.close();">   
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
    		</TD>
    		<TD class= titleImg>
    			 ʵ���ܱ���Ϣ
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divFinFee1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanQueryLJAGetGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
    <center>
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
     </center>
 </Div>					

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>
