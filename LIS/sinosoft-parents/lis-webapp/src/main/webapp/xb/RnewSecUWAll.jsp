<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：RnewSecUWAll.jsp.
//程序功能：续期续保二次核保
//创建日期：2005-1-28 11:10:36
//创建人  ：HYQ
//更新记录：ML   2006-03-08     更新原因/内容
%>

<html>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>";     //记录登陆机构

</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
   <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="RnewSecUWAll.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewSecUWAllInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<form method=post name=fm id="fm" target="fraSubmit" action="./RnewSecUWAllChk.jsp">
<div id="RnewSecUWAllInputPool"></div>
	<!------#############      共享工作池查询条件部分         ###############--------->
	<!--<Table class= common>
		<TR>
			<TD class= titleImg align= center>请输入查询条件：</TD>
		</TR>
	</Table>
	<Div  id= "divSearch" style= "display: ''">
		<Table  class= common>
		  	<TR  class= common>
				  <TD  class= title>保单号</TD>
				  <TD  class= input><Input class= common name=ContNo1 ></TD>
				
				  <TD  class= title>客户号</TD>
				  <TD  class= input><Input class= common name=CustomerNo1 ></TD>
				  
				  <TD  class= title>管理机构</TD>
				  <TD  class= input>
					  <Input class="codeno" name=ManageCom1 ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" 
					  onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);">
					  <input class=codename name=ManageComName1 readonly=true>
				  </TD>
		   </TR>
		   <TR  class= common>	
		      <TD  class= title>核保任务状态</TD>
			    <TD  class= input>
				     <Input class="codeno" name=UWState1 value= "1"  
				     CodeData= "0|^1|未处理任务^2|核保已回复^3|核保未回复" 
				     ondblClick="showCodeListEx('uwstatus1',[this,UWStateName1],[0,1]);" 
				     onkeyup="showCodeListKeyEx('uwstatus1',[this,UWStateName1],[0,1]);">
				     <input class="codename" name="UWStateName1" value ="未处理任务" readonly="readonly" >
			    </TD>
			    <TD class="title" >星级业务员</TD>
				  <TD class="input">
					   <Input class="codeno" name="StarAgent1" CodeData= "0|^1|一星^2|二星^3|三星^4|四星" 
					   ondblClick="showCodeListEx('StarAgent1',[this,StarAgentName1],[0,1]);" 
					   onkeyup="showCodeListKeyEx('StarAgent1',[this,StarAgentName1],[0,1]);" >
					   <input class="codename" name="StarAgentName1" readonly="readonly">
				  </TD>
				  
				  <TD class="title">VIP客户</TD>
				  <TD class="input">
					   <Input class="codeno" name="VIPCustomer1" value="1" CodeData= "0|^0|非^1|是" 
					   ondblClick="showCodeListEx('VIPCustomer1',[this,VIPCustomerName1],[0,1]);" 
					   onkeyup="showCodeListKeyEx('VIPCustomer1',[this,VIPCustomerName1],[0,1]);" >
					   <input class="codename" name="VIPCustomerName1" value="是" readonly="readonly">
				  </TD>
		   </TR>
		   <TR  class= common>	
          <TD  class= title>投保人</TD>
				  <TD  class= input><Input class= common name=AppntName1 ></TD>
				  <TD  class= title>被保人</TD>
				  <TD  class= input><Input class= common name=InsurName1 ></TD>
		   </TR>
		   <TR class="common">
		   		<TD  class= title>转核日期</TD>
				  <TD  class= input>
					   <Input class="coolDatePicker" dateFormat="short"  name=ZHDate1 >
				  </TD>
				  <TD  class= title>最后回复日期</TD>
				  <TD  class= input>
					   <Input class="coolDatePicker" dateFormat="short"  name=LAAnswerDate1 >
				  </TD>					
		   </TR>
		</Table>
	</DIV>
    
    <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickAll();">
     <!---- <INPUT VALUE="撤单申请查询" class=cssButton TYPE=button onclick="withdrawQueryClick();"> --> 
     <!--<TD  class= title> 总单数</TD>
      <TD  class= input> <Input class="readonly" readonly name=PolSum  ></TD>
	<!------#############      共享工作池列表信息部分         ###############--------->
	<!--<DIV id=DivLCContInfo STYLE="display:''">
		<Table>
			<TR>
				<TD class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllPolGrid);">
				</TD>
				<TD class= titleImg>共享工作池</TD>
			</TR>
		</Table>
	</Div>
	<Div  id= "divAllPolGrid" style= "display: ''" align = center>
		<Table  class= common >
			<TR  class=common>
				<TD style=" text-align: left" colSpan=1 >
					<span id="spanAllPolGrid" ></span>
			  	</TD>
			</TR>
		</Table>
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();">
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();">
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();">
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();">
	</Div>
	<br>
	
	<br>
      <!-- 申请数据操作按钮 -->
     <!-- <input type="button" class="cssButton" value=" 申 请 " onclick="applyTheMission()">
  <br>

	<!---#####################以下为个人队列部分###########################------>
	<!--<INPUT class=cssButton  type=hidden id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();">

	<Table  class= common>
		  <TR>
			  <TD class= titleImg align= center>请输入查询条件：</TD>
		  </TR>
		  	<TR  class= common>
				  <TD  class= title>保单号</TD>
				  <TD  class= input><Input class= common name=ContNo2 ></TD>
				
				  <TD  class= title>客户号</TD>
				  <TD  class= input><Input class= common name=CustomerNo2 ></TD>
				  
				  <TD  class= title>管理机构</TD>
				  <TD  class= input>
					  <Input class="codeno" name=ManageCom2 ondblclick="showCodeList('station',[this,ManageComName2],[0,1]);" 
					  onkeyup="showCodeListKey('station',[this,ManageComName2],[0,1]);">
					  <input class=codename name=ManageComName2 readonly=true>
				  </TD>
		   </TR>
		   <TR  class= common>	
		      <TD  class= title>核保任务状态</TD>
			    <TD  class= input>
				     <Input class="codeno" name=UWState2 value= "1"  
				     CodeData= "0|^1|未处理任务^2|核保已回复^3|核保未回复" 
				     ondblClick="showCodeListEx('uwstatus1',[this,UWStateName2],[0,1]);" 
				     onkeyup="showCodeListKeyEx('uwstatus1',[this,UWStateName2],[0,1]);">
				     <input class="codename" name="UWStateName2" value ="未处理任务" readonly="readonly" >
			    </TD>
			    <TD class="title" >星级业务员</TD>
				  <TD class="input">
					   <Input class="codeno" name="StarAgent2" CodeData= "0|^1|一星^2|二星^3|三星^4|四星" 
					   ondblClick="showCodeListEx('StarAgent2',[this,StarAgentName2],[0,1]);" 
					   onkeyup="showCodeListKeyEx('StarAgent2',[this,StarAgentName2],[0,1]);" >
					   <input class="codename" name="StarAgentName2" readonly="readonly">
				  </TD>
				  
				  <TD class="title">VIP客户</TD>
				  <TD class="input">
					   <Input class="codeno" name="VIPCustomer2" value="1" CodeData= "0|^0|非^1|是" 
					   ondblClick="showCodeListEx('VIPCustomer2',[this,VIPCustomerName2],[0,1]);" 
					   onkeyup="showCodeListKeyEx('VIPCustomer2',[this,VIPCustomerName2],[0,1]);" >
					   <input class="codename" name="VIPCustomerName2" value="是" readonly="readonly">
				  </TD>
		   </TR>
		   <TR  class= common>	
          <TD  class= title>投保人</TD>
				  <TD  class= input><Input class= common name=AppntName2 ></TD>
				  <TD  class= title>被保人</TD>
				  <TD  class= input><Input class= common name=InsurName2 ></TD>
		   </TR>
		   <TR class="common">
		   		<TD  class= title>转核日期</TD>
				  <TD  class= input>
					   <Input class="coolDatePicker" dateFormat="short"  name=ZHDate2 >
				  </TD>
				  <TD  class= title>最后回复日期</TD>
				  <TD  class= input>
					   <Input class="coolDatePicker" dateFormat="short"  name=LAAnswerDate2 >
				  </TD>					
		   </TR>
		</Table>

 	<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="NeweasyQueryClick();">
	<DIV id=DivLCContInfo STYLE="display:''">
		<Table>
			<TR>
		    	<TD class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
				</TD>
				<TD class= titleImg>个人工作池</TD>
			</TR>
		</Table>
	</Div>
	<Div  id= "divPolGrid" style= "display: ''" align = center>
		<Table  class= common >
			<TR  class=common>
				<TD style=" text-align: left" colSpan=1 >
					<span id="spanPolGrid" ></span>
			  	</TD>
			</TR>
		</Table>
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();">
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();">
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();">
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">
	</Div>-->

	<!--INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->
	<Table>
		 <Input type=hidden name="ApplyType" >
		 <Input type=hidden name="MissionID" >
		 <Input type=hidden name="SubMissionID" >
		 <Input type=hidden name="ActivityID" >
		 <Input type=hidden name="Com" >
		 <Input type=hidden name="Result" >
		 <Input type=hidden name="PrtNo" >
		 <Input type=hidden name="Type" >
	     <Input type=hidden name="priva" ><!--add by yaory-->
	</Table>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>
</body>
</html>
