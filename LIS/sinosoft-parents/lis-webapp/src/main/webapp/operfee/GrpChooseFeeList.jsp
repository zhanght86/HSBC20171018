<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
  <script src="./GrpChooseFeeList.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpChooseFeeListInit.jsp"%>


</head>
<body  onload="initForm();">
<!--登录画面表格-->
<!--<form name=fm action=./GrpChooseFeeListResult.jsp target=fraSubmit method=post>-->
<form name=fm id=fm target=fraSubmit method=post>
    <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class= titleImg align= center>请输入查询条件：(查询已交费的不定期团单并未核销的数据)</td>
		</tr>
	</table>
	<div class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>         
            <TD  class=title5>集体保单号码</TD>
            <TD  class=input5><Input class="common wid" id= GrpPolNo name=GrpPolNo></TD>
			<TD  class=title5></TD>
			<TD  class=input5></TD>
        </TR>                       
      	     
   </Table>  
      <INPUT VALUE="查   询" Class=cssButton TYPE=button onclick="easyQueryClick();">
     <!-- <INPUT VALUE="查询" Class=common TYPE=button onclick="submitForm();"> -->
      <INPUT VALUE="返   回" Class=cssButton TYPE=button onclick="returnParent();">   
	</div>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 未核销已交费的不定期团单信息
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
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
 </Div>					
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>
