<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//程序名称：GrpEdorTypeBSInput.jsp
//程序功能：保险期间中断操作
//创建日期：2006-04-19 11:10:36
//创建人  ：万泽辉
%>
<%
  String tLoadFlag = "6";
%>
<head >
<script>
	var loadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
	var prtNo = "<%=request.getParameter("prtNo")%>";
	window.onfocus = f; 
	var showInfo;
	function f() {
	  try { showInfo.focus(); } catch(e) {}
	}
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

	<SCRIPT src="GrpEdorTypeBR.js"></SCRIPT>
	<%@include file="GrpEdorTypeBRInit.jsp"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

</head>

<body  onload="initForm(); " >
  <form  method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <table class=common>
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
  </TABLE> 
  </div>
   <table>
     <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
      </td>
      <td class= titleImg>
        保单险种信息
      </td>
    </tr>
   </table>
   
    <Div  id= "divLCGrpPol" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();">
      </Div>	
  	</div>	 

  	<br>
  	  <table>
     <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpState);">
      </td>
      <td class= titleImg>
        保单险种状态信息
      </td>
    </tr>
   </table>
   <Div  id= "divLCGrpState" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: center colSpan=1>
  					<span id="spanLCGrpContStateGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">
      </Div>	
  	</div>
  	<br> 
  	
     <tr class=common>
       <TD  class= title> 恢复日期 </TD>
       <TD  class= input><input class="multiDatePicker" dateFormat="short" name="StatDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>                    
    	 <TD  class= input><input type =button class=cssButton value="确  认" onclick="submitForm();"></TD>
    	 <TD  class= input><input type =button class=cssButton value="返  回" onclick="returnParent();"></TD>
    </tr>   
  <input type=hidden id="OldEndDate" name="OldEndDate"> 
	<input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
	</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


