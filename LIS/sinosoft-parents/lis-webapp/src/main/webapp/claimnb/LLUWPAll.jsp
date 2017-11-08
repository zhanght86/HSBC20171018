<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
 <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="LLUWPAll.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LLUWPAllInit.jsp"%>
  <title>打印承保核保通知书 </title>
</head>
<body  onload="initForm();initElementtype();" >
<!--  <form action="./UWNoticeQuery.jsp" method=post name=fm target="fraSubmit">
  
   <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入投保单查询条件：</td>
		  </tr>
	  </table>
    <table  class= common align=center>
      	<TR  class= common>
      	
                  
          </TD> 
          <TD  class= title>  保单号码   </TD>
          <TD  class= input>  <Input class= common name=ContNo > </TD>
          <Input  type = "hidden" class= common name=PrtNo >
          <TD  class= title>   管理机构  </TD>
          <TD  class= input>  <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
        
         
         </TR>
         <TR  class= common style = "display: 'none'">
         	  <TD  class= title> 营业员编码 </TD>
          <TD  class= input>  <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">   </TD>
         <TD  class= title> 营业部、营业组 </TD>
          <TD  class= input>  <Input class="code" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this]);" onkeyup="return showCodeListKey('AgentGroup',[this]);">   </TD>
         
		
          </TD>
        </TR>
    </table>
<Input class= "codeno" type=hidden name = NoticeType value="LP90" ondblclick="return showCodeList('lluwnoticeprint',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('lluwnoticeprint',[this,NoticeTypeName],[0,1]);"><!-- <Input class = codename name= NoticeTypeName readonly = true > -->
 <!--lzf         <INPUT VALUE="查  询" class= cssButton TYPE=button onclick="easyQueryClick();">
   <hr>
  </form>
  -->
  <form action="./LLUWPAllSave.jsp" method=post name=fm  id="fm"  target="fraSubmit">
  <div id = "UWPAllPool"></div>
<!--    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>投保单信息 </td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class= cssButton TYPE=button onclick="getFirstPage();">
      <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="getNextPage();">
      <INPUT VALUE="尾  页" class= cssButton TYPE=button onclick="getLastPage();">
  	</div>
  	 <hr>
  	 --> 
  	<p>
      <INPUT VALUE="打印理赔核保通知书" class= cssButton TYPE=button onClick="printPol();">
  	</p>
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="OldPrtSeq" name="OldPrtSeq">
  	<input type=hidden id="ContNo" name="ContNo">
  	<input type=hidden id="ManageCom" name="ManageCom">
  	<input type=hidden id="NoticeType" name="NoticeType">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="SubMissionID" name="SubMissionID">
  	<input type=hidden id="PrtNo" name="PrtNo">
  	<input type=hidden id="Address" name="Address">
  	<input type=hidden id="BranchGroup" name="BranchGroup">
  	<input type=hidden id="ClmNo" name="ClmNo">
  	<input type=hidden id="BatNo" name="BatNo">
    </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
