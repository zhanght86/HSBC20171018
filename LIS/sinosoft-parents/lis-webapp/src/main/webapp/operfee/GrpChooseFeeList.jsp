<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
  <script src="./GrpChooseFeeList.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpChooseFeeListInit.jsp"%>


</head>
<body  onload="initForm();">
<!--��¼������-->
<!--<form name=fm action=./GrpChooseFeeListResult.jsp target=fraSubmit method=post>-->
<form name=fm id=fm target=fraSubmit method=post>
    <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class= titleImg align= center>�������ѯ������(��ѯ�ѽ��ѵĲ������ŵ���δ����������)</td>
		</tr>
	</table>
	<div class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>         
            <TD  class=title5>���屣������</TD>
            <TD  class=input5><Input class="common wid" id= GrpPolNo name=GrpPolNo></TD>
			<TD  class=title5></TD>
			<TD  class=input5></TD>
        </TR>                       
      	     
   </Table>  
      <INPUT VALUE="��   ѯ" Class=cssButton TYPE=button onclick="easyQueryClick();">
     <!-- <INPUT VALUE="��ѯ" Class=common TYPE=button onclick="submitForm();"> -->
      <INPUT VALUE="��   ��" Class=cssButton TYPE=button onclick="returnParent();">   
	</div>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 δ�����ѽ��ѵĲ������ŵ���Ϣ
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divIndiDue1" style= "display: ''" align=center>
   <Table  class= common>
       <TR  class= common>
        <TD style="text-align: left" colSpan=1>
            <span id="spanIndiDueQueryGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
 </Div>					
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>
