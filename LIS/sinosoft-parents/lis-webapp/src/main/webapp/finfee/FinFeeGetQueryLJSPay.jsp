<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>Ӧ����Ϣ��ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./FinFeePayQueryLJSPay.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="FinFeePayQueryLJSPayInit.jsp"%>


</head>

<body  onload="initForm();">
<!--��¼������-->
<form name=fm id=fm action=""  target=fraSubmit method=post>
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
        </td>
        <td class= titleImg>
          �������ѯ������         
        </td>
      </tr>
    </table>
	<div class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
          ֪ͨ�����:
          </TD>
          <TD  class= input>
            <Input class="common wid" name=GetNoticeNo id=GetNoticeNo >
          </TD>
         <TD  class= title>
          ��ͬ����:
          </TD>
          <TD  class= input>
         <Input class="common wid" name=OtherNo id=OtherNo >
          </TD>   
          <TD  class= title>
          ���ֱ���:
          </TD>
          <TD  class= input>
          	<Input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly=true>           
          </TD>
      	<TR  class= common>
          <TD  class= title>
          Ͷ��������:
          </TD>
          <TD  class= input>
         <Input class="common wid" name=AppntName id=AppntName >
          </TD>      	      
          <TD  class= title>
          ����������:
          </TD>
          <TD  class= input>
         <Input class="common wid" name=InsuredName id=InsuredName >
          </TD>      	               	      
       </TR>                     
   </Table>  
      <INPUT VALUE="��    ѯ" Class=cssButton TYPE=button onclick="easyQueryClick();"> 
      <INPUT VALUE="��    ��" Class=cssButton TYPE=button onclick="returnParent();">   
      <INPUT VALUE="��    ��" Class=cssButton TYPE=button onclick="top.close();"> 
    </div>	  
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
 <Div  id= "divFinFee1" style= "display: ;text-align:center">
   <Table  class= common>
       <TR  class= common>
        <TD style="text-align: left" colSpan=1>
            <span id="spanQueryLJAGetGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      <INPUT CLASS=cssButton90 VALUE="��ҳ"   TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="βҳ"   TYPE=button onclick="turnPage.lastPage();">
 </Div>					
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>
