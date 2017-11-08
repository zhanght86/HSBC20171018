<%
//程序名称：在LLSecondManuUWInput.jsp.
//程序功能：理赔个人人工核保-----合同或保单核保
//创建日期：2005-12-29 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人 yuejw    更新日期     更新原因/内容
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<head >
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
    	String tCaseNo = request.getParameter("CaseNo"); //赔案号		
		String tBatNo = request.getParameter("BatNo");   //批次号			
		String tInsuredNo = request.getParameter("InsuredNo"); //出险人客户号 
		String tClaimRelFlag = request.getParameter("ClaimRelFlag"); //赔案相关标志 	
		
		String tMissionid = request.getParameter("Missionid");   //任务ID 
		String tSubmissionid = request.getParameter("Submissionid"); //子任务ID 
		String tActivityid = request.getParameter("Activityid");  //节点号 	
		
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="./LLSecondManuUW.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="./LLSecondManuUWInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" >
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></td>
			<td class= titleImg>合同列表信息</td>
		</tr>
	</table>	
	<Div  id= "DivLLCUWBatchGrid" align= center style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1 ><span id="spanLLCUWBatchGrid"></span> </td>
			</tr>
		</table>
	</Div>   
	<!--合同信息-->
	<table >
		<tr>
			<td><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
			<td class="titleImg">合同详细信息</td>
		</tr>
	</table>
	<div id="DivLCCont" STYLE="display:''">
		<table class=common >
			<tr class=common>
				<td class="title">印刷号码 </td>
				<td><input name="PrtNo" VALUE class="readonly" readonly TABINDEX="-1" MAXLENGTH="14"></td>
				<td class="title">总单投保单号码</td>
				<td ><input name="ProposalContNo" class="readonly" readonly ></td>	
				<td class="title">管理机构 </td>
				<td><input name="ManageCom"  MAXLENGTH="10" class="readonly" readonly></td>

			</tr>
			<tr class=common>
				<td class="title" >销售渠道 </td>
				<td ><input name="SaleChnl" class="readonly" readonly ></td>
				<td class="title">代理人编码 </td>
				<td><input name="AgentCode" MAXLENGTH="10" class="readonly" readonly></td>
				<td class="title">代理机构</td>
				<td ><input name="AgentCom" class="readonly" readonly ></td>					    		
			</tr>
		</table>
		<table class=common>
			<tr>
				<TD class= title>备注</TD>
			</tr>
			<tr>
				<TD  class= input> <textarea name="Remark" readonly cols="100%" rows="3" witdh=100% class=common></textarea></TD>
			</tr>
		</table>
	</div>	
	
	<!----投保书健康告知栏询问号,体检健康告知栏询问号,对应未告知情况,出险人健康状况告知内容----->
	<hr>
	<div>
		<table class=common>
			<tr><TD class= title>投保书健康告知栏询问号：</TD></tr>
			<tr>
				<TD  class= input><textarea name="HealthImpartNo1" readonly cols="100%" rows="1" witdh=100% class=common></textarea></TD>
			</tr>
			<tr><TD class= title>体检健康告知栏询问号：</TD></tr>
			<tr>
				<TD  class= input> <textarea name="HealthImpartNo2" readonly cols="100%" rows="1" witdh=100% class=common></textarea></TD>
			</tr>
				
			<tr><TD class= title>对应未告知情况：</TD></tr>
			<tr>
				<TD  class= input> <textarea name="NoImpartDesc" readonly cols="100%" rows="3" witdh=100% class=common></textarea></TD>
			</tr>
			<tr><TD class= title>出险人健康状况告知内容：</TD></tr>
			<tr>
				<TD  class= input> <textarea name="Remark1" readonly cols="100%" rows="3" witdh=100% class=common></textarea></TD>
			</tr>					
		</table>
	</div>
	
	<!-- 投保人信息部分 -->
	<table>
		<tr>
			<td><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);"></td>
			<td class= titleImg>投保人信息</td>
		</tr>
	</table>
	<DIV id=DivLCAppntInd STYLE="display:''">
		<table  class= common>
			<TR  class= common>        
				<TD  class= title>客户号</TD>
				<TD  class= input><Input class="readonly" readonly name=AppntNo></TD>				
				<TD  class= title>姓名</TD>
				<TD  class= input><Input class="readonly" readonly name=AppntName></TD>
				<TD  class= title> 性别</TD>
				<TD  class= input><Input class="readonly" readonly name=AppntSex ></TD>

			</TR>
			<TR  class= common>
				<TD  class= title> 年龄</TD>
				<TD  class= input><input class="readonly" readonly name="AppntBirthday"></TD>				
				<TD  class= title>职业</TD>
		  		<TD  class= input><input class="readonly" readonly name="OccupationCode"></TD>	         
		  		<TD  class= title>国籍</TD>
		  		<TD  class= input><input class="readonly" readonly name="NativePlace"></TD>

			</TR>
			<TR>
				<TD  class= title>VIP标记</TD>
				<TD  class= input><Input class="readonly" readonly name=VIPValue ></TD>
				<TD  class=title>黑名单标记</TD>
		 		<TD  class= input><Input class="readonly" readonly name=BlacklistFlag ></TD>
				<TD  class=title></TD>
		 		<TD  class= input></TD> 				 		
			</TR>
		</table>   
	</DIV>
	<hr>
	<INPUT VALUE="投保单明细" disabled class=cssButton TYPE=hidden onclick="showPolDetail();"> 
	<INPUT VALUE="扫描件查询"  class=cssButton  TYPE=button onclick="ScanQuery();">  
	<INPUT VALUE="投保人既往投保信息"  disabled class=cssButton TYPE=hidden onclick="showApp(1);"> 
	<input value="体检录入"  class=cssButton type=button onclick="showHealth();" >
	<input value="生存调查录入"  class=cssButton type=button onclick="showRReport();">   
	<input value="记事本" disabled class=cssButton TYPE=hidden onclick="showNotePad();" >   
	<input value ="进入险种信息" class = cssButton type = button onclick="enterRiskInfo();">
	<hr>   
	<div id = "divUWResult" style = "display: ''">
		<!-- 核保结论 -->   	
		<table  class= common>
			<TR class= common>
				<TD class= title>核保结论</td>
				<td class=input><Input class=codeno readonly name=uwState ondblclick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly ></td>
				<TD class=title></TD>
				<TD class=input></TD>	
				<TD class=title></TD>
				<TD class=input></TD>	
			</TR>               
		</table>            
		<table class=common>
			<tr>
				<TD class= title>核保意见</TD>
			</tr>
			<tr>
				<TD  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class=common></textarea></TD>
			</tr>
		</table>
		<table class=common>		
			<tr>
				<INPUT VALUE="确   定" class=cssButton TYPE=button onclick="uwSaveClick();">
	    		<INPUT VALUE="重   置" class=cssButton TYPE=button onclick="uwCancelClick();">
        	</tr>		
		</table>
	</div>
	<hr>
	<Input value ="核保完成" class = cssButton Type=button onclick="llSecondUWFinish();">
	<INPUT value ="返   回" class=cssButton TYPE=button onclick="turnBack();"> 
	<!--隐藏区域-->
	<input type=hidden id="Operator" name="Operator" ><!--//-->
	<input type=hidden id="ComCode" name="ComCode" ><!--//-->
	<input type=hidden id="ManageCom" name="ManageCom" ><!--//-->			
	
	<input type=hidden id="tCaseNo" name="tCaseNo" ><!--//赔案号<上页传入>-->
	<input type=hidden id="tBatNo" name="tBatNo" ><!--//批次号<上页传入>-->
	<input type=hidden id="tInsuredNo" name="tInsuredNo" ><!--//出险人客户号<上页传入> -->
	<input type=hidden id="tClaimRelFlag" name="tClaimRelFlag" ><!--//赔案相关标志<上页传入> -->	
	<input type=hidden id="tMissionid" name="tMissionid"><!--  //任务ID -->
	<input type=hidden id="tSubmissionid" name="tSubmissionid"><!--//子任务ID -->
	<input type=hidden id="tActivityid" name="tActivityid"><!--//节点号 	-->	
		  
	<input type=hidden id="tContNo" name="tContNo" ><!--//合同号码 -->	
	
    <input type=hidden id="fmtransact" name="fmtransact" ><!--隐常的操作符号-->
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
