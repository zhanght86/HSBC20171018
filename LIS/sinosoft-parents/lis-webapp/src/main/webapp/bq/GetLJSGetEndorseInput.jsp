<html> 
<% 
//程序名称：
//程序功能：保全
//创建日期：2005-10-28 09:18上午
//创建人  ：wenhuan
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>  
  
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <!--SCRIPT src="./GetLJSGetEndorse.js"></SCRIPT-->
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="GetLJSGetEndorseInit.jsp"%>

<title>保全费用明细</title> 
</head>
<body  onload="initForm();" >
  <form action="./GetLJSGetEndorseSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
 <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > 批单号</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorNo id=EdorNo >
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
      <TD class = title > 团体保单号 </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
      </TD>   
    </TR>
    <!--TR class=common>
    	<TD class =title>申请日期</TD>
    	<TD class = input>    		
    		<Input class= "readonly" readonly name=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<Input class= "readonly" readonly name=EdorValiDate ></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR-->
  </TABLE>
  </div>
  <!--保单险种列表信息-->  
 <Div  id= "divPolInfo" style= "display: ''">
 	 <table>
 	 	<tr>
 	 		<td class=common>
 	 			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Customer);">
 	 		</td>
 	 		<td class= titleImg>
 	 			费用明细
 	 		</td>
 	 	</tr>
 	</table>
 	<Div id = "MoneyDetail" style = "display : ''" align=center>
 		<table>
 			<tr>
 				<td>
 					<span id = "spanMoneyDetailGrid">
 					</span>
 				</td>
 			</tr>
 		</table>
 		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage11.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage11.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage11.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage11.lastPage();"> 		
 	</Div>           
    
    <Input class= cssButton type=Button value="返 回" onclick="returnParent()">	

	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" value= 2 name="ContType">
	 <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">
	 <input type=hidden id="ContNo" name="ContNo">
	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
 <script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";	
</script>
</html>
