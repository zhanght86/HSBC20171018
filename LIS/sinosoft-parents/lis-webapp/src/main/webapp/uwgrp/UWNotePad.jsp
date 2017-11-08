<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWNotePad.jsp
//程序功能：记事本查询
//创建日期：2002-09-24 11:10:36
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
//	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>记事本查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="UWNotePad.js"></SCRIPT>
  <%@include file="UWNotePadInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./UWNotePadSave.jsp">
    <table>
    	<tr>
      	<td class=common>
		      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 记事本查询
    		</td>
    	</tr>
    </table>
    <table class= common>
    	<TR  class= common>
    	  <TD  class= title>
          操作员
        </TD>
        <TD  class= input>
          <Input class=common name=Operator>
        </TD>
        <TD  class= title>
          录入日期
        </TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name=MakeDate >
        </TD>
        <TD  class= input>
          <input type= "button" name= "Query" value="查  询" class=cssButton onClick= "queryClick()">
        </TD>
      </TR>
    </table>
    

    <hr>
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 记事本内容：
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	
    	<Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=common VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=common VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=common VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=common VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	
      
    </div>

  <hr>
  
  <table width="80%" height="20%" class= common>
    <TR  class= common>  
      <TD width="100%" height="15%"  class= title> 记事本内容（400字以内,回车符占一个字符） </TD>
    </TR>
    <TR  class= common>
      <TD height="85%"  class= title><textarea name="Content" verify="记事本内容|len<800" verifyorder="1" cols="135" rows="5" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">
  <input type="hidden" name= "OperatePos" value= "">
  <INPUT CLASS=cssButton VALUE="新  增" TYPE=button onclick="insertClick()">
  <INPUT CLASS=cssButton VALUE="清空记事本内容" TYPE=button onclick="fm.Content.value=''">
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
