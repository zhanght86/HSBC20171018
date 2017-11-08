<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ManuUWInput.jsp.
//程序功能：新契约个人人工核保
//创建日期：2005-12-29 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPrtNo = "";
	String tContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tUWState = "";
	String tNoType = "";
	String tReportFlag="";
	String tCustomerNo = "";
	String tUWAuthority = "";
	
	tPrtNo = request.getParameter("PrtNo");
	tContNo = request.getParameter("ContNo");
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
    tActivityID = request.getParameter("ActivityID");
    
    tUWState = request.getParameter("UWState");
    tNoType = request.getParameter("NoType");
    tUWAuthority = request.getParameter("UWAuthority");
    System.out.println("@@@@@tUWAuthority:"+tUWAuthority);
	session.putValue("ContNo",tContNo);
	
	tReportFlag = request.getParameter("ReportFlag");
	tCustomerNo=request.getParameter("CustomerNo");
	session.putValue("ContNo",tCustomerNo);
	System.out.println("@@@@@tReportFlag:"+tReportFlag);
%>
<html>
<%
  //个人下个人
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";          //个人单的查询条件
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	var MissionID = "<%=tMissionID%>";
	var SubMissionID = "<%=tSubMissionID%>";
	var PrtNo = "<%=tPrtNo%>";
	var ActivityID = "<%=tActivityID%>";
    var UWState = "<%=tUWState%>"; 
	var NoType = "<%=tNoType%>";
	var uwgrade = "";
    var appgrade= "";
    var uwpopedomgrade = "";
    var codeSql = "code";
    var ReportFlag = "<%=tReportFlag%>";
    var CustomerNo = "<%=tCustomerNo%>";
    var UWAuthority = "<%=tUWAuthority%>";
   
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="ManuUW.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ManuUWInit.jsp"%>
  
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm();initInpBox();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./ManuUWCho.jsp">
    <!-- 保单查询条件 -->
    <div id="divSearch">
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
         </td>
		<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
    </table>
    <div class="maxbox" id="maxbox">
    <table  class= common>
      	<tr  class= common>
          <td  class= title>投保单号</TD>
          <td  class= input> <Input class= "common wid" name=QProposalNo id="QProposalNo" > </TD>
          <td  class= title>印刷号码</TD>
          <td  class= input> <Input class="common wid" name=QPrtNo id="QPrtNo"> </TD>
          <td  class= title> 管理机构  </TD>
          <td  class= input>  <Input class="code" name=QManageCom id="QManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('station',[this]);" onDblClick="return showCodeList('station',[this]);" onKeyUp="return showCodeListKey('station',[this]);">  </TD>
        </TR>
        <tr  class= common>
          <td  class= title> 核保级别  </TD>
          <td  class= input>  <Input class="code" name=UWGradeStatus id="UWGradeStatus" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" value= "1" CodeData= "0|^1|本级保单^2|下级保单" onClick="showCodeListEx('Grade',[this,''],[0,1]);" ondblClick="showCodeListEx('Grade',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('Grade',[this,''],[0,1]);">  </TD>
          <td  class= title>  投保人名称 </TD>
          <td  class= input> <Input class="common wid" name=QAppntName id="QAppntName" > </TD>
          <td  class= title>  核保人  </TD>
          <td  class= input>   <Input class= "common wid" name=QOperator id="QOperator" value= "">   </TD>
        </TR>
        <tr  class= common>
          <td  class= title>  保单状态 </TD>
          <td  class= input>  <Input class="code" readonly name=State id="State" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" value= "" CodeData= "0|^1|未人工核保^2|核保已回复^3|核保未回复" onClick="showCodeListEx('State',[this,''],[0,1]);" ondblClick="showCodeListEx('State',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('State',[this,''],[0,1]);"> </TD>
          <td  class= title>   </TD>
		  <td  class= input>   </TD>
		  <td  class= title>   </TD>
		  <td  class= input>   </TD>
        </TR>
        <tr  class= common>
		  <td  class= title>   </TD>
          <td  class= input>  <Input  type= "hidden" class= "common wid" name = QComCode id="QComCode" >  </TD>
		  <td  class= title>   </TD>
		  <td  class= input>   </TD>
		  <td  class= title>   </TD>
		  <td  class= input>   </TD>

       </TR>
    </table>
   </div>
          <INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQueryClick();">
          <!--INPUT VALUE="撤单申请查询" class=common TYPE=button onclick="withdrawQueryClick();"-->
          <INPUT type= "hidden" name= "Operator" id="Operator" value= "">
    </div>
    <!--合同信息-->
	<DIV id=DivLCContButton STYLE="display:none">
	<table id="table1">
			<tr>
				<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
				</td>
				<td class="titleImg">合同信息
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLCCont" class="maxbox" STYLE="display:none">
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>印刷号
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="PrtNo" id="PrtNo" VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>管理机构
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id="ManageCom"  MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title" nowrap>销售渠道
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" id="SaleChnl" CLASS="readonly wid" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">业务员编码
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="AgentCode" id="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
	    		
	    	<!--td> CLASS="title">应交保费(本币)
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="ShouldPayPrem" CLASS="readonly" readonly MAXLENGTH="255">
	    		</td-->
	    		
	    	<td CLASS="title">填单保费
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="OldPayPrem" id="OldPayPrem" CLASS="readonly wid" readonly MAXLENGTH="255">
	    		</td>
	    </tr>
	    <tr CLASS="common">
	     	<td CLASS="title">高额件标识
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="HighAmntFlag" id="HighAmntFlag" CLASS="readonly wid" readonly MAXLENGTH="1">
	    		</td>
				<td CLASS="title">备注信息
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="Remark" id="Remark" CLASS="readonly wid" readonly MAXLENGTH="255">
	    		</td>
	    	
		</tr>
			  <input NAME="ProposalContNo" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="20">
				<input NAME="AgentGroup" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="12">
				<input NAME="AgentCode1" type=hidden MAXLENGTH="10" CLASS="readonly" readonly>
				<input NAME="AgentCom" type=hidden CLASS="readonly" readonly>
				<input NAME="AgentType" type=hidden CLASS="readonly" readonly>
		</table>
	</div>
	<div id =DivRevise  STYLE="display:none">
	<table class="common">
	    <tr class="common">
	    	<TD  class= title >订正原因</TD>
				<TD  class= input COLSPAN="1">
				    <Input class="readonly wid" name=Revise id="Revise" readonly>
			<!--<Input class="codeno" name=Revise  CodeData= "0|^0|生日单^1|保费不足^2|代理人不一致^3|人工订正" ondblClick="showCodeListEx('Revise',[this,ReviseName],[0,1]);" onkeyup="showCodeListKeyEx('Revise',[this,ReviseName],[0,1]);"><input class="codename" name="ReviseName" readonly="readonly"> -->
				</TD>
				<TD  class= title><Input type='hidden' ></TD>
				<TD  class= input><Input type='hidden' ></TD>
				<TD  class= title><Input type='hidden' ></TD>
				<TD  class= input><Input type='hidden' ></TD>
	  </tr>
	  </table>
    </div>
    <div id =DivUpper  STYLE="display:none">
	<table class="common">
	    <tr class="common">
	    	<TD  class= title >上报原因</TD>
				<TD  class= input COLSPAN="1">
					<Input class="readonly wid" name=UpperReason id="UpperReason" readonly>
				</TD>
			<td CLASS="title" >上报核保师工号
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="UpperUwUser" id="UpperUwUser" CLASS="readonly wid" readonly >
	    	</td>
	    	<TD  class= title><Input type='hidden' ></TD>
			<TD  class= input><Input type='hidden' ></TD>
	  </tr>
	  </table>
    </div>
	<div id=DivLCSendTrance STYLE="display:none">
		<table class = "common">
				<tr CLASS="common">
			    <td CLASS="title">上报标志
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendFlag" id="SendFlag" MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title">核保结论
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendUWFlag" id="SendUWFlag" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title">核保意见
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendUWIdea" id="SendUWIdea" CLASS="readonly wid" readonly>
	    		</td>
			</tr>
		</table>
	</div>
<hr class="line">
<table>
  <tr>
    <td nowrap>
    <INPUT VALUE=" 投保单明细查询 " class=cssButton TYPE=button name="uwButton1" onClick="showPolDetail();">
    <INPUT VALUE="  影像资料查询  " class=cssButton TYPE=button name="uwButton2" onClick="ScanQuery();">
    <INPUT VALUE="财务交费信息查询" class=cssButton TYPE=button name="uwButton5" onClick="showTempFee();">
   <INPUT VALUE="  核保结论查询  " class=cssButton TYPE=button name="uwButton7" onClick="UWQuery()">
    <!--INPUT VALUE="自动核保提示信息" class=cssButton TYPE=button name="uwButton3" onclick="showNewUWSub();"-->
    <!--INPUT VALUE=" 问题件信息查询 " class=cssButton TYPE=button name="uwButton4" onclick="QuestQuery()"-->
    </td>
  </tr>
  <tr>
    <td>
    
    <INPUT VALUE="投保操作履历查询" class=cssButton TYPE=button name="uwButton6" onClick="QueryRecord()">
    
    <INPUT VALUE="强制人工核保原因" class=cssButton TYPE=button name="uwButton8" onClick="ForceUWReason()">    
    
    </td>
  </tr>
</table>
	<DIV id=DivLCAutoUWButton STYLE="display:''">
	<!-- 自动核保提示信息部分 -->
	<table>
	<tr>
	<td class=common>
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAutoUW);">
	</td>
	<td class= titleImg>
	自动核保提示信息
	</td>
	</tr>
	</table>
	</DIV>
	
	<DIV id=DivLCAutoUW STYLE="display:''">
		<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWErrGrid" >
					</span> 
				</td>
			</tr>
		</table>
     <!--<INPUT VALUE="首  页" class=cssButton TYPE=button onClick="turnPage2.firstPage();">
		<INPUT VALUE="上一页" class=cssButton TYPE=button onClick="turnPage2.previousPage();">
		<INPUT VALUE="下一页" class=cssButton TYPE=button onClick="turnPage2.nextPage();">
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onClick="turnPage2.lastPage();">-->
	</div>
	  <p>
	  	  <INPUT VALUE="审阅完毕" class= CssButton TYPE=button name=AutoUWButton id=AutoUWButton onClick="submitFormUW();"> 					
    </P>

	<DIV id=DivLCAppntIndButton STYLE="display:none">
	<!-- 投保人信息部分 -->
	<table>
	<tr>
	<td class=common>
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
	</td>
	<td class= titleImg>
	投保人信息
	</td>
	</tr>
	</table>
	</DIV>

	<DIV id=DivLCAppntInd class="maxbox" STYLE="display:none">
	 <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            姓名
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="AppntName" id="AppntName" value="">
	          </TD>
	          <td  class= title>
	            性别
	          </TD>
	          <td  class= input>
	            <Input name=AppntSex id="AppntSex" CLASS="readonly wid" type="hidden">
	            <Input name=AppntSexName CLASS="readonly wid" >
	          </TD>
	          <td  class= title>
	            年龄
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntBirthday" id="AppntBirthday">
	          </TD>
	        </TR>

	        <tr  class= common>
	            <Input CLASS="readonly wid" readonly type=hidden name="AppntNo" id="AppntNo" value="">
	            <Input CLASS="readonly wid" readonly  type=hidden name="AddressNo" id="AddressNo">
	          <td  class= title>
	            职业代码
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="OccupationCode" id="OccupationCode" >
	          <input CLASS="readonly wid"  readonly name="OccupationCodeName" id="OccupationCodeName" type="hidden">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="OccupationType" id="OccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="OccupationTypeName" id="OccupationTypeName">
	          </TD>
	         	<td  class= title>
           	 年收入
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=income id="income" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
           	 身高
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=Stature id="Stature" >
          	</TD>
	        	<td  class= title>
           	 体重
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=Weight id="Weight" >
          	</TD>
          	<td  class= title>
           	 BMI值
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=BMI id="BMI" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
	            国籍
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="NativePlace" id="NativePlace" type="hidden">
	          <input CLASS="readonly wid" readonly name="NativePlaceName" id="NativePlaceName">
	          </TD>
	        	<td  class= title>
           	 VIP标记
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=VIPValue id="VIPValue" type="hidden">
            	<Input class="readonly wid" readonly name=VIPValueName id="VIPValueName" >
          	</TD>
	        <td  class="title" nowrap>
            	黑名单标记
          	</TD>
         	 <td  class= input>
            	<Input class="readonly wid" readonly name=BlacklistFlag id="BlacklistFlag" type="hidden">
            	<Input class="readonly wid" readonly name=BlacklistFlagName id="BlacklistFlagName" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
	            累计寿险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntSumLifeAmnt" id="AppntSumLifeAmnt" >
	          </TD>
	        	<td  class= title>
           	 累计重大疾病保额
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=AppntSumHealthAmnt id="AppntSumHealthAmnt" >
          	</TD>
	         <td  class="title" nowrap>
            	累计医疗险保额
           </TD>
         	 <td  class= input>
            	<Input class="readonly wid" readonly name=AppntMedicalAmnt id="AppntMedicalAmnt" >
           </TD>
	      	</tr>	
	      	<tr>
	      	<td  class= title>
	            累计意外险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntAccidentAmnt" id="AppntAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 累计风险保额
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=AppntSumAmnt id="AppntSumAmnt" >
          	</TD>
          	<td  class= title>
           	 累计保费
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=SumPrem id="SumPrem" >
          	</TD>
	        </tr>
	      </table>
	</DIV>
    <!-- 保单查询结果部分（列表） -->
   <DIV id=DivLCContInfo STYLE="display:''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单查询结果
    		</td>
    	</tr>
    </table>
    </Div>
         <Div  id= "divLCPol1" style= "display: ''" align = center>
         <table>
       		<tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class=cssButton TYPE=button onClick="getFirstPage();">
      <INPUT VALUE="上一页" class=cssButton TYPE=button onClick="getPreviousPage();">
      <INPUT VALUE="下一页" class=cssButton TYPE=button onClick="getNextPage();">
      <INPUT VALUE="尾  页" class=cssButton TYPE=button onClick="getLastPage();">
    </div>
<table>
  <tr>
    <td nowrap>
    <!--INPUT VALUE=" 投保人既往投保信息 " class=cssButton TYPE=button onclick="showApp(1);"-->
    <INPUT VALUE=" 投保人健康告知查询 " class=cssButton TYPE=button name="uwButton9" onClick="queryHealthImpart()">
    <INPUT VALUE=" 投保人既往投保资料查询 " class=cssButton TYPE=button name="uwButton30" onClick="showApp(1)">
 	<input class="CssButton" name="button14" value="  投保人既往理赔资料查询  " type="button" onClick="queryClaimCus();"> 
	<input class="CssButton" name="button15" value="  投保人既往保全资料查询  " type="button" onClick="queryEdorCus();">
 <!--   <INPUT VALUE=" 投保人体检资料查询 " class=cssButton TYPE=button name="uwButton10" onclick="showBeforeHealthQ()">
    <INPUT VALUE=" 投保人生调资料查询(待定) " class=cssButton TYPE=button name="uwButton10" onclick="showRiseQ()">
    <INPUT VALUE=" 投保人保额累计信息 " class=cssButton TYPE=button name="uwButton11" onclick="amntAccumulate();">
    <INPUT VALUE=" 投保人影像资料查询 " class=cssButton TYPE=bu name="uwButton8"tton onclick=""> -->
    </td>
  </tr>   
  <tr>
    <td nowrap>
    <!-- 
    <INPUT VALUE="投保人已承保保单查询" class=cssButton TYPE=button name="uwButton12" onclick="queryProposal();">
    <INPUT VALUE="投保人未承保保单查询" class=cssButton TYPE=button name="uwButton13" onclick="queryNotProposal();">
    <INPUT VALUE=" 投保人既往保全查询 " class=cssButton TYPE=button name="uwButton14" onclick="queryEdor()">
    <INPUT VALUE=" 投保人既往理赔查询 " class=cssButton TYPE=button name="uwButton15" onclick="queryClaim();">
     -->
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
    <!--INPUT VALUE="家庭既往投保信息" class=cssButton TYPE=button onclick="showApp(2);"-->
    <!--INPUT VALUE="被保人既往投保信息" class=cssButton TYPE=button onclick="showApp(2);"-->
    <!--INPUT VALUE="以往核保记录" class=common TYPE=button onclick="showOldUWSub();"-->
   <Div  id= "divMain" style= "display:none">
    <!--附加险-->

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 被保人信息
    		</td>
    	    </tr>
        </table>
        <Div  id= "divLCPol2" style= "display: none" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
    </Div>
    <!--被保人信息明细-->
    <jsp:include page="ManuUWInsuredShow.jsp"/>
<table class= common border=0 width=100% >

	  <div id=divUWButton1 style="display:''">
<table>
  <tr>
    <td nowrap>
          <input value=" 体检通知录入 "        class=cssButton type=button name="uwButton16" onClick="showHealth();" width="200">
          <input value=" 生调申请录入 "        class=cssButton type=button name="uwButton17" onClick="showRReport();">
          <!--input value="    承保计划变更    "      class=cssButton type=button name="uwButton18" onclick="showChangePlan();"-->
          <!--add by tongmeng 2007-11-08-->
          <INPUT VALUE="  问题件录入  " class=cssButton TYPE=button onClick="QuestInput();" name="uwButtonQU">
          <input value="  问题件差错  " class=cssButton type=hidden name="wButtonQuestErr" onClick="IssueMistake();">
          <input value="   再保呈报   "  name="uwButton24"  class=cssButton type=button onClick="UWUpReport();" width="200">
          <INPUT VALUE="  记事本  " class=cssButton id="NotePadButton" TYPE=button onClick="showNotePad();">
          <INPUT VALUE="  变更申请书  " class=cssButton TYPE=hidden name="uwScanQuery" onClick="ScanQuery1();">
    </td>
  </tr>
  <tr>
    <td >
          <input value=" 查询体检结果 "  class="cssButton" type="button" name=uwButton21 onClick="queryHealthReportResult();">
          <input value=" 查询生调结果 "  class="cssButton" type="button" name=uwButton22 onClick="queryRReportResult();">
          <input value="  问题件查询  " class=cssButton name=uwButton4 type=button onClick="QuestQuery();">
    	<!--	<input value="   核保等待   " class="cssButton" type="button" name="uwButton55" onclick="UWWait()">  modify by lzf 2013-04-03 -->
          <input value=" 再保回复信息 "      class=cssButton type=button name="uwButton20" onClick="showUpReportReply();" width="200">
          <input value=" 发核保通知书 " class="cssButton" type="button" name="uwButton23" onClick="SendAllNotice_MS()"> 

      <!-- <input value="承保计划变更结论录入"  class=cssButton type=button name="uwButton19" onclick="showChangeResultView();"> -->
          <!--input value="   记   事   本     " class=cssButton type=but name="uwButton19"ton onclick="showNotePad();" width="200"-->
         <!--edit by yaory-->                                             

           <!--end add-->
 
    </td>
  </tr>

  <tr>
  	<td nowrap>
  		    <input value="修改保单生效日期" class=cssButton type=button name="uwButton27" onClick="ChangeCvalidate();" >
          <input value="核保分析报告"  class=cssButton name="ReportButton" TYPE=button onClick="showUWReport();">
  		    <input value="体检医院品质管理"   class=cssButton type=hidden name="uwButtonHospital" onClick="HospitalQuality();" >
  		    <input value="业务员品质管理"   class=cssButton type=hidden name="uwButton26" onClick="AgentQuality();" >
  		    <input value=" 客户品质管理 "   class=cssButton type=hidden name="uwButton25" onClick="CustomerQuality();" >

  		<!-- <input value="撤单申请查询" name=uwButton28 class=cssButton type=button onclick="BackPolQuery();"> -->
  	 </td>
  </tr>
</table>
<hr class="line">
          <span id= "divAddButton1" style= "display: ''">
          	<!--input value="体检资料查询" class=common type=button onclick="showHealthQ();" width="200"-->
          </span>
          <!--input value="体检回销" class=cssButton type=button onclick="showHealthQ();" -->
          <!--INPUT VALUE="契调回销" class=cssButton TYPE=button onclick="RReportQuery();"-->
          <!--INPUT VALUE="保全明细查询" class= common TYPE=button onclick="Prt();"-->
          <!--INPUT VALUE="保全项目查询" class= common TYPE=button onclick="ItemQuery();"-->
          <!--INPUT VALUE="问题件录入" class=cssButton TYPE=button onclick="QuestInput();"-->
          <!--input value="问题件回销" class=cssButton type=button onclick="QuestBack();"-->
          <!--input value="问题件查询" class=cssButton type=button onclick="QuestQuery();"-->
          <!--input value="告知整理" class=cssButton type=button onclick="ImpartToICD();"-->
          <!--INPUT  VALUE="既往理赔查询" class=cssButton TYPE=button onclick="ClaimGetQuery();"-->

          <span  id= "divAddButton2" style= "display: ''">
          	<!--INPUT VALUE="生存调查查询" class=common TYPE=button onclick="RReportQuery();"-->
          </span>

          <span  id= "divAddButton3" style= "display: ''">
          	<!--input value="撤单申请查询" name=uwButton28 class=cssButton type=button onclick="BackPolQuery();"-->
          </span>
          <span  id= "divAddButton4" style= "display: ''">
          	<!--input value="催办查询" class=common type=button onclick="OutTimeQuery();"-->
          </span>

          <span  id= "divAddButton5" style= "display: ''">
          <!--input value="体检资料录入" class=common type=button onclick="showHealth();" width="200"-->
          </span>

          <span  id= "divAddButton6" style= "display: ''">
          	<!--input value="生调请求说明" class=common type=button onclick="showRReport();"-->
          </span>        

           <!--input value="发核保通知书" class=cssButton type=button onclick="SendNotice();"-->
           <!--input value="发问题件通知书" class=cssButton type=button onclick="SendIssue();"-->
            <!--input value="发拒保通知书" class=cssButton type=button onclick="SendRanNotice();"-->
           <!--input value="发延期通知书" class=cssButton type=button onclick="SendDanNotice();"-->
           <!--input value="发加费通知书" class = cssButton type= button onclick="SendAddNotice()"-->

          <!--tr> <hr> </hr>  </tr-->
          <span  id= "divAddButton7" style= "display: ''">
          <input value="发首期交费通知书" class=cssButton type=button onClick="SendFirstNotice();">
          </span>
          
          <!--<input value="发催办通知书" type=button onclick="SendPressNotice();">-->
          <span  id= "divAddButton8" style= "display: ''">
           <input value="修改银行发盘方式" name=SendBankFlagButton class=cssButton type=hidden onClick="SendBankFlagChange();"> 	
          <!--input value="发核保通知书" class=common type=button onclick="SendNotice();">-->
          </span>
          <!--input value="发体检通知书" class=common type=button onclick="SendHealth();"-->
          <!--input value="核保分析报告录入" class=cssButton type=button onclick="showReport();" width="200"-->
           <span  id= "divAddButton9" style= "display: ''">

      </div>
    </table>
    	  <input type="hidden" name= "PrtNoHide" value= "">
    	  <input type="hidden" name= "PolNoHide" value= "">
    	  <input type="hidden" name= "MainPolNoHide" value= "">
    	  <input type="hidden" name= "NoHide" value= "">
    	  <input type="hidden" name= "TypeHide" value= "">
          <INPUT  type= "hidden" class= Common name= UWGrade id="UWGrade" value= "">
          <INPUT  type= "hidden" class= Common name= AppGrade id="AppGrade" value= "">
          <INPUT  type= "hidden" class= Common name= PolNo  value= "">
          <INPUT  type= "hidden" class= Common name= "ContNo"  value= "">
           <INPUT  type= "hidden" class= Common name= "CustomerNo"  value= "">
          <INPUT  type= "hidden" class= Common name= "YesOrNo"  value= "">
          <INPUT  type= "hidden"  class= Common name= "MissionID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "SubMissionID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "ActivityID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "NoType"  value= "">
          <INPUT  type= "hidden"  class= Common name= "SubConfirmMissionID"  value= "">
          <INPUT  type= "hidden" class= Common name= SubNoticeMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= UWPopedomCode id="UWPopedomCode" value= "">
		  <INPUT  type= "hidden" class= Common name= FBFlag value= "">
		  <INPUT  type= "hidden" class= Common name= AddFeeFlag value= "">
		  <INPUT  type= "hidden" class= Common name= UpReporFlag value= "N">
		  <INPUT  type= "hidden" class= Common name= UWAuthority value= ""><!-- lzf -->
    <div id = "divUWResult" style = "display: ''">
        <!-- 核保结论 -->
        <table>
    	<tr>        	
    		<td class=common>
                  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
                </td>
            <td class= titleImg>
    			 核保结论：
    		</td>
    	    </tr>
        </table>
        <div class="maxbox1">
        <table class=common border=0>
           
            <!--
            <tr class = common>
                <td class=title >建议结论</td>
                <td class =input><Input class= common name="SugIndUWFlag"  ></td>
            </tr>
            -->

            <tr>
                <td class= title5>核保结论
                	<!--span id= "UWResult"> 保全核保结论 <Input class="code" name=uwstate value= "1" CodeData= "0|^1|本级保单^2|下级保单" ondblClick="showCodeListEx('uwstate',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('uwstate',[this,''],[0,1]);">  </span-->
                </td>
                <td class=input5>
                    <Input class=codeno name=uwState id="uwState" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onDblClick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onKeyUp="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename id="uwStatename" readonly elementtype=nacessary>
                </td>

                <td class=title5>
                    核保流向
                </td>

                <td class=input5>
                    <Input class="codeno" name=uwUpReport id="uwUpReport" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onDblClick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onKeyUp="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary>
                </td>

            </TR>
        </table>
     </div>
    <div id= "UWDelaydiv" style = "display: none">
       <table class=common>
       <tr>
			<TD  class= title5>延长时间</TD>
			<TD  class= input5 ><Input class="code" name=UWDelay id="UWDelay" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="showCodeList('UWDelaySpan', [this],[1]);" onDblClick="showCodeList('UWDelaySpan', [this],[1]);" onKeyUp="return showCodeListKey('UWDelaySpan', [this],[1]);">
			<TD  class= title5>延期拒保原因</TD>
			<TD  class= input5 ><Input class="codeno" name=UWDelayReasonCode id="UWDelayReasonCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('UWDeRefReason', [this,UWDelayReason],[0,1]);" onDblClick="showCodeList('UWDeRefReason', [this,UWDelayReason],[0,1]);" onKeyUp="return showCodeListKey('UWDeRefReason', [this,UWDelayReason],[0,1]);"><Input class="codename" name=UWDelayReason id="UWDelayReason" readonly>
		</tr>
			</table>
  	</div>
  	<div id= "UWRefdiv" style = "display: none">
       <table class=common>
       <tr>			
			<TD  class= title5>延期拒保原因</TD>
			<TD  class= input5 ><Input class="codeno" name=UWRefuReasonCode id="UWRefuReasonCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('UWDeRefReason', [this,UWRefuReason],[0,1]);" onDblClick="showCodeList('UWDeRefReason', [this,UWRefuReason],[0,1]);" onKeyUp="return showCodeListKey('UWDeRefReason', [this,UWRefuReason],[0,1]);"><Input class="codename" name=UWRefuReason id="UWRefuReason" readonly>
			<TD  class= title5 TYPE=hidden></TD>
			<TD  class=input5 TYPE=hidden><Input class="common wid" TYPE=hidden></TD>
		</tr>
			</table>
  	</div>
  	<div id= "UWWithdrawdiv" style = "display: none">
       <table class=common>
       <tr>
			<TD  class= title5>撤单原因</TD>
			<TD  class= input5 ><Input class="codeno" name=UWWithDReasonCode id="UWWithDReasonCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('UWWithDReason', [this,UWWithDReason],[0,1]);" onDblClick="showCodeList('UWWithDReason', [this,UWWithDReason],[0,1]);" onKeyUp="return showCodeListKey('UWWithDReason', [this,UWWithDReason],[0,1]);"><Input class="codename" name=UWWithDReason id="UWWithDReason" readonly>
			<TD  class= title5 TYPE=hidden></TD>
			<TD  class=input5 TYPE=hidden><Input class="common wid" TYPE=hidden></TD>
		</tr>
			</table>
  	</div>
  	<div id= "UWDeRefdiv" style = "display: none">
       <table class=common>
         <tr>
			<TD  class= common><input type=checkbox name=SuggestCont id="SuggestCont" value="1"><Input class="common wid" name=SugContInput value="建议您投保我公司年金类险种"> </TD>			
		 </tr>
	   </table>
  	</div>
  	 <div id= "UWUpperUwer" style = "display: none">
       <table class=common>
       <tr>
			<TD  class= title5>上报核保师</TD>
			<TD  class= input5 ><Input class="codeno" name=TUWPopedomCode id="TUWPopedomCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('UWPopedomCode1', [this,UWPopedomCodename,TUWPopedomGrade],[0,1,2]);" onDblClick="showCodeList('UWPopedomCode1', [this,UWPopedomCodename,TUWPopedomGrade],[0,1,2]);" onKeyUp="return showCodeListKey('UWPopedomCode1', [this,UWPopedomCodename,TUWPopedomGrade],[0,1,2]);"><Input class="codename" name=UWPopedomCodename id="UWPopedomCodename" readonly>
			<TD  class= title5 >上报核保师级别</TD>
			<TD  class=input5 ><Input class="readonly wid" readonly name = TUWPopedomGrade id="TUWPopedomGrade"></TD>
		</tr>
		<tr>
			<TD  class= title5>上报原因</TD>
			<TD  class= input5 ><Input class="codeno" name=UWUpperReasonCode id="UWUpperReasonCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('UWUpperReason', [this,UWUpperReason],[0,1]);" onDblClick="showCodeList('UWUpperReason', [this,UWUpperReason],[0,1]);" onKeyUp="return showCodeListKey('UWUpperReason', [this,UWUpperReason],[0,1]);"><Input class="codename" name=UWUpperReason id="UWUpperReason" readonly>
		</tr>
			</table>
  	</div>
    <table class=common>
		<tr>
		      	<td height="24"  class= title>
            		核保意见
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<td  class= title> <textarea name="UWIdea" id="UWIdea" cols="100%" rows="5" witdh=100% ></textarea></TD>
      		<td>
      			<input name=uwPopedom type=hidden>

      			</td>
      		</tr>
	  </table>
</div>
<!--div id= "UWDelaydiv" style = "display: none">延长时间<Input class="common" name=UWDelay value= ""> </div>-->


        <!--****************************************************************
            修改人：续涛，修改时间20050420，修改原因：呈报
            ****************************************************************
        -->
<!--
        <div id = "divUWup" style = "display: ''">

          <table  class= common align=center>
              <TR>


                  <td class=title8>
                      核保师级别
                  </td>


                  <td class=input8>
                      <Input class="codeno" name=uwPopedom ondblclick="return showCodeList('uwPopedom',[this,uwPopedomname],[0,1]);" onkeyup="return showCodeListKey('uwPopedom',[this,uwPopedomname],[0,1]);"><Input class="codename" name=uwPopedomname readonly>
                  </td>
                  <td class=title></td>
                  <td class=input></td>
                  <td class=title>
                      核保师
                  </td>

                  <td class=input>
                      <Input class="codeno" name=uwPer ondblclick="return showCodeList('uwper',[this,uwPername],[0,1]);" onkeyup="return showCodeListKey('uwper',[this,uwPername],[0,1]);"><Input class="codename" name=uwPername readonly>
                  </td>
              </TR>
          </table>
<!--
  </div>
-->

<div id = "divSugUWResult" style = "display: none">
  <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>建议核保结论：</td>
	  </tr>
	</table>
  <table class=common>
    	<tr class = common>
				<td class=title nowrap >
					建议结论
				</td>
				<td class =input>
					<Input class=codeno style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('contuwstate',[this,SugIndUWFlagName],[0,1]);" onDblClick="return showCodeList('contuwstate',[this,SugIndUWFlagName],[0,1]);" onKeyUp="return showCodeListKey('contuwstate',[this,SugIndUWFlagName],[0,1]);" name="SugIndUWFlag" id="SugIndUWFlag"><input type="text" class="codename" name="SugIndUWFlagName" id="SugIndUWFlagName" readonly>
				</td>
				</tr><tr class="common">
				<td class=title nowrap >
					建议核保意见
				</td>
				<td class =input colspan=3>
					<!--Input class= common name="SugIndUWIdea"  -->
          <textarea name="SugIndUWIdea" cols="100%" rows="5" witdh=100% ></textarea>
        </td>
	  	</tr>
  	</table>
<!--
  	<table  class= common align=center>

    	  	<tr  class= common>
          		<td height="29"  class= title>
          		 	<span id= "UWResult">核保结论<Input class="code" name=uwstate ondblclick="return showCodeList('uwstate',[this]);" onkeyup="return showCodeListKey('uwstate',[this]);">  </span>
	   			      <span id= "UWDelay" style = "display: none">延长时间<Input class="common" name=UWDelay value= ""> </span>
	   		        <span id= "UWPopedom" style = "display: none">上报核保<Input class="code" name=UWPopedomCode ondblclick="showCodeList('UWPopedomCode1', [this]);" onkeyup="return showCodeListKey('UWPopedomCode1', [this]);"> </span>
	   		 	    <span id= "Delay" style = "display: none">本单核保师<Input class="common" name=UWOperater value= ""> </span>
	   		 	   </TD>
          </TR>
		<tr></tr>
          	<td height="24"  class= title>
            		核保意见
          	</TD>
		<tr></tr>
      		<td  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
	  </table>
-->

</div>
  	<div id = "divUWSave" style = "display:''">
        <INPUT VALUE="确  定" class=cssButton id="button1" style="display:" TYPE=button onClick="submitForm(0);">
        <INPUT VALUE="返回下级" class=cssButton id="button2" style="display:none" TYPE=button onClick="submitForm(1);">
        <INPUT VALUE="取  消" class=cssButton TYPE=button onClick="cancelchk();">
        <INPUT VALUE="返  回" class=cssButton TYPE=button onClick="InitClick();">
        <INPUT VALUE="返回复核" class=cssButton TYPE=hidden onClick="returnApprove();">
        <INPUT VALUE="退出到公共池" class=cssButton TYPE=hidden onClick="returnPublicPool();">        
    </div>
    
  	<div id = "divUWAgree" style = "display: none">
    		<INPUT VALUE="同  意" class=cssButton TYPE=button onClick="submitForm(1);">
    		<INPUT VALUE="不同意" class=cssButton TYPE=button onClick="submitForm(2);">
    		<INPUT VALUE="返  回" class=cssButton TYPE=button onClick="InitClick();">
  	</div>

    <div id = "divChangeResult" style = "display: none">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		承保计划变更结论录入:
          	</TD>
		<tr></tr>
      		<td  class= title><textarea name="ChangeIdea" cols="100%" rows="5" witdh=100% ></textarea></TD>
    	 </table>
    	 <INPUT VALUE="确  定" class=cssButton TYPE=button onClick="showChangeResult();">
    	 <INPUT VALUE="取  消" class=cssButton TYPE=button onClick="HideChangeResult();">
    </div>
  </Div>

  </form>
  <br><br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
 <script language="JavaScript">
    try
	{
		document.getElementsByName("PrtNo")[0].focus();
    }
    catch (ex) {}
</script>