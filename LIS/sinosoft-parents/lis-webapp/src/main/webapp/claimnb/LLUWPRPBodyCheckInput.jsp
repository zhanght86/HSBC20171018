<html> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LLUWPRPBodyCheck.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLUWPRPBodyCheckInit.jsp"%>
  <title>理赔二核补打核保通知书 </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LLRePrintSave.jsp" method=post name=fm  id="fm" target="fraSubmit">
  <div id ="UWPRPBodyPool"></div>
    <!-- 保单信息部分 -->
<!-- lzf    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>请输入保单查询条件：</td>
	</tr>
    </table>
    <table  class= common align=center>
    <tr  class= common>
      	  
          <TD  class= title> 保单号   </TD>
          <TD  class= input><Input class= common name=ContNoS ></TD>  
          <TD  class= title> 通知书号 </TD>
          <TD  class= input><Input class= common name=PrtSeqS ></TD>
          
    </tr>
    <tr  class= common>
         <TD  class= title>   起始日期   </TD>
          <TD  class= input>  <Input class= "coolDatePicker" dateFormat="short" name=StartDay>  </TD>
          <TD  class= title>  结束日期 </TD>
          <TD  class= input>  <Input class= "coolDatePicker" dateFormat="short" name=EndDay>  </TD>        	  
     </tr>
    </table>
    
<Input class= "codeno" type=hidden name = CodeS value="LP03" ondblclick="return showCodeList('lluwnoticeprint',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('lluwnoticeprint',[this,NoticeTypeName],[0,1]);"><!-- <Input class = codename name= NoticeTypeName readonly = true > -->
<!-- lzf           <INPUT VALUE="查  询" class= cssButton TYPE=button onclick="easyQueryClick();"> 
    <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	  </td>
    	  <td class= titleImg>
    		补打通知书信息
    	  </td>
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
  	-->
  	<p>
      <INPUT VALUE="提交补打通知书数据" class= cssButton TYPE=button onClick="printPol();">
  	</p>
  	 
      <input type=hidden id="fmtransact" name="fmtransact">
      <input type=hidden id="Code" name="Code" value="LP03">
      <input type=hidden id="ContNo" name="ContNo">
      <input type=hidden id="ClmNo" name="ClmNo">
      <input type=hidden id="BatNo" name="BatNo">
      <input type=hidden id="PrtSeq" name="PrtSeq">
      <input type=hidden id="MissionID" name="MissionID">
      <input type=hidden id="SubMissionID" name="SubMissionID">
      <input type=hidden id="ActivityID" name="ActivityID">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
