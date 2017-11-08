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
  var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
</script>
<head>
<title>实付总表信息查询 </title>
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
<!--登录画面表格-->
<form name=fm id="fm" action=./FinFeePayQueryLJAGetResult.jsp target=fraSubmit method=post>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</TD>
    		<TD class= titleImg>
    			 请输入查询条件
    		</TD>
    	</TR>
    </Table> 
  <div id="maxbox" class="maxbox1"> 
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
          实付号码:
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ActuGetNo id="ActuGetNo" >
          </TD>
          <TD  class= title5>
          付费方式:
          </TD>
          <TD  class= input5>
          <Input class="code" name=PayMode id="PayMode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('PayMode',[this]);" onKeyUp="return showCodeListKey('PayMode',[this]);"  ondblclick="return showCodeList('PayMode',[this]);" onKeyUp="return showCodeListKey('PayMode',[this]);">
          </TD> 
        </TR>
      	<TR  class= common>
          <TD  class= title5>
          批单号码:
          </TD>
          <TD  class= input5>
         <Input class="common wid" name=OtherNo id="OtherNo" >
          </TD>      	      
       </TR>                     
   </Table>  
 </div>
     <td class=button >&nbsp;&nbsp;</td>
      <INPUT VALUE="查  询" Class=cssButton TYPE=button onClick="easyQueryClick();"> 
      <td class=button >&nbsp;&nbsp;</td>
      <INPUT VALUE="返  回" Class=cssButton TYPE=button onClick="returnParent();">   
      <td class=button >&nbsp;&nbsp;</td>
      <INPUT VALUE="关  闭" Class=cssButton TYPE=button onClick="top.close();">   
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
    		</TD>
    		<TD class= titleImg>
    			 实付总表信息
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
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
     </center>
 </Div>					

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>
