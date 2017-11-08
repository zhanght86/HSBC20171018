<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String tPayDate = "";
	try 
	{
		tPayDate = request.getParameter("PayDate");
		loggerDebug("FinFeeGetQueryLJSPayEdor",tPayDate);
	}
	catch( Exception e )
	{
		tPayDate = null;
	}
%>

<head>
<script>
	var tPayDate = "<%=tPayDate%>"; 
</script>
<title>实收总表查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./FinFeePayQueryLJSPayEdor.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FinFeePayQueryLJSPayEdorInit.jsp"%>


</head>

<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm id=fm action=./FinFeePayQueryLJAGetResult.jsp target=fraSubmit method=post>
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
        </td>
        <td class= titleImg>
          请输入查询条件：         
        </td>
      </tr>
    </table>
	<div class=maxbox1>
    <table  class= common>
      	<TR  class= common>
          <!--
          <TD  class= title>
          通知书号码:
          </TD>
          <TD  class= input>
            <Input class= common name=GetNoticeNo >
          </TD>
          -->
         <TD  class= title5>
          合同受理号：
          </TD>  
          <TD  class= input5>
         <Input class="common wid" name=OtherNo id=OtherNo >
          </TD>   
          <TD  class= title5>
          合同号码:
          </TD>
          <TD  class= input5>
          <Input class="common wid" name=ContNo id=ContNo >  
          </TD>
      	<TR  class= common>
          <TD  class= title5>
          投保人姓名:
          </TD>
          <TD  class= input5>
         <Input class="common wid" name=AppntName id=AppntName >
          </TD>      	      
          <TD  class= title5>
          被保人姓名:
          </TD>
          <TD  class= input5>
         <Input class="common wid" name=InsuredName id=InsuredName >
          </TD>      	               	      
       </TR>                     
   </Table>  
      <Input class="common" name=PayDate type=hidden value=<%=tPayDate%>>
      <INPUT VALUE="查询" Class=cssButton TYPE=button onclick="easyQueryClick();"> 
      <INPUT VALUE="返回" Class=cssButton TYPE=button onclick="returnParent();">   
      <INPUT VALUE="关闭" Class=cssButton TYPE=button onclick="top.close();">  
    </div>	  
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
    		</TD>
    		<TD class= titleImg>
    			 实收总表信息
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divFinFee1" style= "display: ;text-align:center">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanQueryLJAGetGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
 </Div>					
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>
