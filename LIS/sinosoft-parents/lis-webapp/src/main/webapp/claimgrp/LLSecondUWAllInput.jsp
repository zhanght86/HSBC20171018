<%
	//程序名称：LLSecondUWAllInput.jsp.
	//程序功能：理赔人工核保获取队列
	//创建日期：2005-1-28 11:10:36
	//创建人  ：zhangxing
	//更新记录：  更新人 yuejw   更新日期     更新原因/内容
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<head >
	<%	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//    String tFlag = request.getParameter("type");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="LLSecondUWAll.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LLSecondUWAllInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" > 
	<table class= common border=0 width=100%>
		<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>赔案号 </TD>
				<TD  class= input><Input class= common name=QCaseNo ></TD>
				<TD  class= title>被保险人号码</TD>
				<TD  class= input><Input class= common name=QInsuredNo ></TD>
				<TD  class= title>被保险人名称</TD>
				<TD  class= input><Input class= common name=QInsuredName ></TD>
			</TR>
			<TR  class= common>
				<TD  class= title>赔案相关标志 </TD>
                <TD  class= input><input class=codeno readonly name=QClaimRelFlag CodeData="0|3^0|相关 ^1|不相关" ondblclick="return showCodeListEx('QClaimRelFlag', [this,QClaimRelFlagName],[0,1],'','','','',100);" onkeyup="return showCodeListKeyEx('QClaimRelFlag', [this,QClaimRelFlagName],[0,1],'','','','',100);"><input class=codename name=QClaimRelFlagName readonly ></TD>				
				<TD  class= title>机构</TD>
				<TD  class= input><Input class="common" name=QMngCom ></TD>
				<TD  class= title> </TD>
				<TD  class= input></TD>
			</TR>
		</table>
	</DIV>
	<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryClick();">
	<INPUT class=cssButton VALUE="重  置" TYPE=button onclick="cancleClick();">

	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></td>
			<td class= titleImg>共享池</td>
		</tr>  	
	</table>
	<Div  id= "DivLLCUWBatchGrid" style= "display: ''" align = center>
		<table  class= common >
			<tr  class=common>
				<td text-align: left colSpan=1 ><span id="spanLLCUWBatchGrid" ></span> </td>
			</tr>
		</table>
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();">    
	</Div>
	<hr>
	<INPUT class=cssButton VALUE="申请任务" TYPE=button onclick="applyClick();">
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivSelfLLCUWBatchGrid);"></td>
			<td class= titleImg>待核保任务队列</td>
		</tr>  	
	</table>
	<Div  id= "DivSelfLLCUWBatchGrid" style= "display: ''" align = center>
		<table  class= common >
			<tr  class=common>
				<td text-align: left colSpan=1 ><span id="spanSelfLLCUWBatchGrid" ></span> </td>
			</tr>
		</table>
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">    
	</Div>
	<!--隐藏区域，用来记录隐藏表单信息-->
	<table>
		<Input type=hidden id="" name="Operator" ><!--//记录操作员-->
		<Input type=hidden id="" name="ComCode" ><!--//记录登陆机构-->
		<Input type=hidden id="" name="ManageCom" ><!-- //记录管理机构-->
		
		<input type=hidden id="MissionID" 	 name= "MissionID">
		<input type=hidden id="SubMissionID" name= "SubMissionID">
		<input type=hidden id="ActivityID" name= "ActivityID">
	
	</table>
</form>
</body>
</html>
