<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>������Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./OnePolStatesList.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="OnePolStatesListInit.jsp"%>


</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id='fm' target=fraSubmit method=post>
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	 <td class= titleImg align= center>�������ѯ������(��ҳ���ѯ���Ϊ������������������״̬,����Ҫ��ѯ��ϸ������״̬,�뵽����״̬��ѯ)</td>
	</tr>
    </table>
    <div class="maxbox1">
    <div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
       <TR  class= common>   	
          <TD  class= title5>
          ������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ContNo id="ContNo">
          </TD>  
          <TD  class= title5>
          �������ֺ�
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=MainPolNo id="MainPolNo">
          </TD>  
       </TR>
       <TR>     	       
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class= code name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this]);" ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">
          </TD>                       
       </TR>                     
   </table> 
   </div> 
   </div>
   <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
   
      <!-- <INPUT VALUE="��ѯ" Class=common TYPE=button onclick="easyQueryClick();"> 
      <INPUT VALUE="����" Class=common TYPE=button onclick="returnParent();"> -->   
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 ������Ϣ
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divTempFee1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanIndiDueQueryGrid" ></span> 
  	</TD>
      </TR>
    </Table>	
    <div align=center>			
      <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS="cssButton93" VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
    </div>  
 </Div>	
 <a href="javascript:void(0)" class=button onclick="returnParent();">��  ��</a>				
<br>
<br>
<br>
<br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>