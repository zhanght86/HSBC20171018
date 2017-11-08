<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>催收信息查询</title>
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
<!--登录画面表格-->
<form name=fm id='fm' target=fraSubmit method=post>
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	 <td class= titleImg align= center>请输入查询条件：(该页面查询结果为保单的续期续保催收状态,如需要查询详细的续保状态,请到续保状态查询)</td>
	</tr>
    </table>
    <div class="maxbox1">
    <div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
       <TR  class= common>   	
          <TD  class= title5>
          保单号
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ContNo id="ContNo">
          </TD>  
          <TD  class= title5>
          主险险种号
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=MainPolNo id="MainPolNo">
          </TD>  
       </TR>
       <TR>     	       
          <TD  class= title5>
            险种编码
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class= code name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this]);" ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">
          </TD>                       
       </TR>                     
   </table> 
   </div> 
   </div>
   <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
   
      <!-- <INPUT VALUE="查询" Class=common TYPE=button onclick="easyQueryClick();"> 
      <INPUT VALUE="返回" Class=common TYPE=button onclick="returnParent();"> -->   
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 保单信息
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
      <INPUT CLASS="cssButton90" VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS="cssButton93" VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
    </div>  
 </Div>	
 <a href="javascript:void(0)" class=button onclick="returnParent();">返  回</a>				
<br>
<br>
<br>
<br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>