<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
String tLoadFlag = "6";
%>
<head >
<script>
	
 //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
 var loadFlag = "<%=tLoadFlag%>";  
 var prtNo = "<%=request.getParameter("prtNo")%>";
 window.onfocus = FocusIt; 
 var showInfo;
 function FocusIt() {
   try { 
   	showInfo.focus(); 
   } catch(e) {}
 }
</script>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
 <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
 <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
 <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
 <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
 <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

 <SCRIPT src="GEdorTypeNIInput.js"></SCRIPT>
 <%@include file="GEdorTypeNIInit.jsp"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
</head>
<body  onload="initForm(); " >
 <form action="./GEdorTypeNISubmit.jsp" method=post name=fm id=fm target="fraSubmit">
 	<div class=maxbox1>
	<table class=common>
     <TR  class= common> 
       <TD  class= title > 批单号</TD>
       <TD  class= input > 
         	<input class="readonly wid" readonly name=EdorNo id=EdorNo >
       </TD>
       <TD class = title > 批改类型 </TD>
       <TD class = input >
        	<Input class = codeno     readonly name=EdorType id=EdorType><input class = codename name=EdorTypeName id=EdorTypeName readonly=true>
        	<input class = "readonly wid" readonly name=EdorTypeCal id=EdorTypeCal type=hidden>
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
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
      </td>
      <td class= titleImg>
        新增被保人
      </td>
    </tr>
   </table>  
   <Div  id= "divLPInsured" style= "display: ''">
   	
     <table  class= common>
        <tr  class= common>
          <td text-align: left colSpan=1>
       			<span id="spanLPInsuredGrid" ></span> 
         	</td>
     		</tr>
     </table>  
     <br>   
      <Div  id= "divPageButton" style= "display: ''" align="center" >
      	<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
      	<INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();">      
      	<INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
      	<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();">
      </Div> 
      <br>
      <center>
   		<a href="../appgrp/download1.xls">短险导入模板</a> &nbsp;<a href="../appgrp/download2.xls">长险导入模板</a>
   		<br><br>   
   		<a href="../appgrp/explain.rar">下载磁盘导入说明（rar文件）</a>
   		</center>                
   </div>  
       
 <hr>
   <Div  id= "divGetMoney" style= "display:none">
		<table class=common>
     <TR  class= common> 
       <TD  class= title > 缴费金额合计</TD>
       <TD  class= input > 
         <input class="readonly wid" readonly name=GetMoney id=GetMoney>元   </TD>      
       <TD class = input ></TD>
       <TD class = input ></TD>       
       <TD class = input ></TD>   
     </TR>
   	</TABLE>    
   </Div>       
   <Div  id= "divSubmit" style= "display:''" align="center">    
    	
    	      
  		<Input class= cssButton type=Button value="  保存申请  " 		onclick="edorSave()"> 
    	<Input class= cssButton type=Button  			value="增加被保险人" 		onclick="edorNewInsured()">          
    	<INPUT class= cssButton id="pisdbutton1" 	value="导入被保人清单" 	TYPE=button 	onclick="getin();">
    	<Input class= cssButton type=Button 			value="   费用明细   "   onclick="MoneyDetail()">
		<Input class= cssButton type=Button value=" 返  回 "       onclick="returnParent()">
    	
    	
    	
   </Div>  
  
    
 
     	
    
     
 	 <input type=hidden id="fmtransact" 	name="fmtransact">
 	 <input type=hidden id="ContType" 		name="ContType">
 	 <input type=hidden id="ContNo" 			name="ContNo">
 	 <input type=hidden id="InsuredNo" 		name="InsuredNo">
 	 <input type=hidden id="EdorValiDate" name="EdorValiDate">
 	 <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
 	 <input type=hidden id="NameType" 		name="NameType">
 	 <input type=hidden id="Flag" 				name="Flag">
 	 <input type=hidden id="Money" 				name="Money">
 </form>
  <span id="spanCode"  		style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


