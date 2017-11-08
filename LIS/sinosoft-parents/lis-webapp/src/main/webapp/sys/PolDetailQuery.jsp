<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2003-1-22
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
//修改：刘岩松
//修改日期：2004-02-17
//修改：辛玉强
//修改日期：2005-11-03

//修改：liuxiaosong
//修改日期：2006-10-19
//修改内容：加入保单密码查询入口
%>
<%
  String tPrtNo = "";
	String tContNo = "";
	String tIsCancelPolFlag = "";
	try
	{
		tContNo = request.getParameter("ContNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
		loggerDebug("PolDetailQuery","tContNo="+tContNo);
	}
	catch( Exception e )
	{
		tContNo = "";
		tIsCancelPolFlag = "0";
	}
	tPrtNo = request.getParameter("PrtNo");
%>
<head >
<script>
	var tContNo = "<%=tContNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
	var PrtNo = "<%=tPrtNo%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="PolDetailQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolDetailQueryInit.jsp"%>

	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>保单查询 </title>
</head>

<body  onload="initForm();queryPolType();querySignDate();queryMakeDate();" >
  <form  name=fm id=fm >
    <table class="common">
    	<tr class="common">
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    		<td class= titleImg>
    			合同信息部分
    		</td>
    	</tr>
    </table>

   <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
	   <table class="common" id="table2">
	   	 <tr CLASS="common">
	   	 	 <td CLASS="title">保单号码</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input NAME="GrpContNo" type=hidden CLASS="common">
     	 	   <input NAME="ContNo" id="ContNo" readonly  CLASS="readonly wid">
         </td>
	   	 	 <td CLASS="title">投保单号码</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input NAME="PrtNo" id="PrtNo" CLASS="readonly wid" readonly>
         </td>
	   	 	 <td CLASS="title">服务机构</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input NAME="ManageCom" id="ManageCom" readonly CLASS="codeno"><input class=codename name=ManageComName id=ManageComName readonly>
         </td>
	   	 </tr>

	   	 <tr CLASS="common">
	   	 	 <td CLASS="title">销售渠道</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input class=codeno readonly name="SaleChnl" id="SaleChnl"><input class=codename name=SaleChnlName id=SaleChnlName readonly>
         </td>
	   	   <td CLASS="title">银保通标识</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="PayMode" id="PayMode" CLASS="readonly wid" readonly >
         </td>
	   	   <td CLASS="title">客户签收日期</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="CustomGetPolDate" id="CustomGetPolDate" readonly CLASS="multiDatePicker wid">
         </td>
	   	 </tr>

	   	 <tr CLASS="common">
	   	 	<td CLASS="title">业务员编码</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="AgentCode" id="AgentCode" CLASS="readonly wid" readonly>
        	</td>
         <td CLASS="title">业务员组别</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="AgentGroup" id="AgentGroup" CLASS="readonly wid" readonly>
        	</td>
	   	 	<td CLASS="title">代理机构</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="AgentCom" id="AgentCom" CLASS="codeno" readonly><input class=codename name=AgentComName id=AgentComName readonly>
	   	 	</td>
       </tr>

       <tr>
         <td CLASS="title">保单服务状态</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="PolType" id="PolType" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">签单机构</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="SignCom" id="SignCom" readonly CLASS="codeno"><input class=codename name=SignComName id=SignComName readonly>
         </td>
         <td CLASS="title">签单日期</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	     <input NAME="SignDate" id="SignDate" CLASS="multiDatePicker wid" readonly >
         </td>
       </tr>

       <tr>
         <td CLASS="title">销售机构</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="Name" id="Name" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">保单打印日期</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="MakeDate" id="MakeDate" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">回单日期</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="GetPolDate" id="GetPolDate" CLASS="multiDatePicker wid" readonly >
         </td>
       </tr>
       <tr CLASS="common">
       </tr>

       <tr>
         <td CLASS="title">续期缴费银行代码</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="RNBankCode" id="RNBankCode" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">续期缴费银行账号</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="RNAccNo" id="RNAccNo" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">续期缴费户名</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="RNAccName" id="RNAccName" CLASS="readonly wid" readonly >
         </td>
       </tr>
       <tr>
         <td CLASS="title">保单补发次数</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="PrintTimes" id="PrintTimes" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title"></td>
	   	 	 <td CLASS="input"></td>
         <td CLASS="title"></td>
	   	 	 <td CLASS="input" ></td>
       </tr>
     </table>
   </div>

    <table>
    	<tr>
       <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);"></td>
    		<td class= titleImg>投保人信息部分</td>
    	</tr>
    </table>

<Div  id= "asd" style= "display: ''" class="maxbox1">
 <table  class= common>
   <TR  class= common>
     <TD  class= title>
       姓名
     </TD>
     <TD  class= input>
       <Input name=AppntName id=AppntName CLASS="readonly wid" readonly>
     </TD>
     <TD  class= title>
       性别
     </TD>
     <TD class= input>
       <Input readonly class="codeno" name=AppntSex id=AppntSex><input class=codename name=AppntSexName id=AppntSexName readonly>
     </TD>
     <TD  class= title>
       出生日期
     </TD>
     <TD  class= input>
       <input readonly class="multiDatePicker wid" name="AppntBirthday" id="AppntBirthday">
     </TD>
   </TR>

   <TR  class= common>
   	 <TD  class= title>
       客户号
     </TD>
     <TD  class= input>
     	 <Input class="wid" readonly class=readonly name="AppntNo" id="AppntNo">
     </TD>
     <TD  class= title>
       证件类型
     </TD>
     <TD  class= input>
       <Input readonly class="codeno" name="AppntIDType" id="AppntIDType"><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly>
     </TD>
     <TD  class= title>
       证件号码
     </TD>
     <TD  class= input>
       <Input readonly class="readonly wid" name="AppntIDNo" id="AppntIDNo">
     </TD>
   </TR>

   <TR  class= common>
     <TD  class= title>
       职业等级
     </TD>
     <TD  class= input>
       <input readonly class="codeno" name="WorkType" id="WorkType"><input class=codename name=WorkTypeName id=WorkTypeName readonly>
     </TD>
     <TD  class= title>
       国籍
     </TD>
     <TD  class= input>
       <Input readonly class="codeno" name="NativePlace" id="NativePlace"><input class=codename name=NativePlaceName id=NativePlaceName readonly>
     </TD>
     <TD  class= title>
     <!--客户级别  (暂时隐藏)-->
     </TD>
     <TD  class= input >
       <Input readonly class="codeno" name="VIPType"type=hidden><input type=hidden class=codename name=VIPTypeName readonly>
     </TD>
   </TR>
 </table>
</div>


  <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divInsured);">
    		</td>
    		<td class= titleImg>
    			 被保人信息部分
    		</td>
    	</tr>
    </table>
  <Div  id= "divInsured" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanInsuredGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
  	</Div>

    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divPol1);">
    		</td>
    		<td class= titleImg>
    			 保单险种信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divPol1" style= "display: ''">
      	<table align= center class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>

    	</table>
  	<!--<Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="首  页" class= cssButton TYPE=button onclick="turnPage1.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="上一页" class= cssButton TYPE=button onclick="turnPage1.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="下一页" class= cssButton TYPE=button onclick="turnPage1.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="尾  页" class= cssButton TYPE=button onclick="turnPage1.lastPage();">
      </Div>-->
  	</Div>



   <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton1);">
    		</td>
    		<td class= titleImg>
    			新契约类
    		</td>
    	</tr>
    </table>
  <Div id= "divButton1" class="maxbox1" style= "display: ''">

  <table class= common align=center>
    <tr class= common>
       <td class= input>
           <INPUT class=cssButton VALUE="保单明细查询" TYPE=button onClick="getQueryDetail();">
           <INPUT class=cssButton VALUE="影像资料查询" TYPE=button onClick="ImageQuery();">
           <INPUT class=cssButton VALUE="操作履历查询" TYPE=button onClick="OperationQuery();">
           <INPUT class=cssButton VALUE="保单状态查询" TYPE=button onClick="StateQuery();">
           <INPUT class=cssButton VALUE="首期暂交费查询 " TYPE=button onClick="NewTempFeeQuery();">
           <INPUT class=cssButton VALUE=" 被保人查询 " TYPE=button onClick="InsuredQuery();">
           <INPUT class=cssButton VALUE="给付查询" TYPE=hidden onClick="GetQueryClick();">
	       <!--INPUT class=common VALUE="批改补退费查询" TYPE=button onclick="EdorQueryClick();"-->
	       <INPUT class=cssButton VALUE="保全查询" TYPE=hidden onClick="BqEdorQueryClick();">
	       <!--INPUT class=common VALUE="主险查询" TYPE=button onclick="MainRiskQuery();"-->
	       <!--INPUT class=common VALUE="附加险查询" TYPE=button onclick="OddRiskQuery();"-->
	       <!--<INPUT class=cssButton VALUE="借款查询" TYPE=button onclick="LoanQuery();"-->
	   </td>
    </tr>
  </table>
  </Div>


	 <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton2);">
    		</td>
    		<td class= titleImg>
    			续期收费类
    		</td>
    	</tr>
    </table>
  <Div id= "divButton2" class="maxbox1" style= "display: ''">

	 <table class= common align=center>
    <tr class= common>
     <td class= input align=lift>
	     <INPUT class=cssButton VALUE="  交费查询  "TYPE=button onClick="FeeQueryClick();">
	     <INPUT class=cssButton VALUE=" 暂交费查询 " TYPE=button onClick="TempFeeQuery();">
	     <INPUT class=cssButton VALUE=" 保费项查询 " TYPE=button onClick="PremQuery();">
	     <INPUT class=cssButton VALUE="欠交保费查询" TYPE=hidden onClick="PayPremQuery();">
	     <INPUT class=cssButton VALUE="  账户查询  " TYPE=button onClick="InsuredAccQuery();">
	     <INPUT class=cssButton VALUE="代收数据查询" TYPE=button onClick="BaoPanShuju();">
	     <br><br>
	     <INPUT class=cssButton VALUE=" 保单服务轨迹查询 " TYPE=hidden TYPE=button onclick="ShowTraceQuery();">
	     <INPUT class=cssButton VALUE="收费员基本信息查询" TYPE=button onClick="ShowCollectorQuery();">
	     <INPUT class=cssButton VALUE=" 给付项查询 " TYPE=hidden onClick="GetItemQuery();">
	   </td>
    </tr>
  </table>
	</Div>


	<table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton3);">
    		</td>
    		<td class= titleImg>
    			保全类
    		</td>
    	</tr>
    </table>
  <Div id= "divButton3" class="maxbox1" style= "display: ''">

	 <table class= common align=center>
    <tr class= common>
     <td class= input align=left>
   <INPUT class=cssButton VALUE="  红利查询  " TYPE=button onClick="BonusQuery();">
   <INPUT class=cssButton VALUE="生存领取查询" TYPE=button onClick="LiveGetQuery();">

   <!-- XinYQ added on 2005-11-03 -->
   <INPUT class=cssButton VALUE="   账户轨迹查询   " TYPE=button onClick="LCInsuAccQuery();">
   <input class="cssButton" value="代收代付查询" type="button" onClick="ProxyIncomePayQuery()">

   <INPUT class=cssButton VALUE="保全批改查询" TYPE=button onClick="BqEdorQueryClick();">

   <!-- XinYQ added on 2006-03-01 -->
   <input class="cssButton" value="补发打印查询" type="button" onClick="ReissuePrintQuery()">

   <INPUT class=cssButton VALUE="银行利率查询" TYPE=hidden onClick="ShowBankRateQuery();" >
   <br><br>
   <INPUT class=cssButton VALUE="险种信息查询" TYPE=button onClick="ShowRiskInfoQuery();" >
   <INPUT class=cssButton VALUE="垫交/借款查询" TYPE=button onClick="LoLoanQuery();">
   <INPUT class=cssButton VALUE="万能险保单结算查询" TYPE=button onClick="OmniQuery();">
   <INPUT class=cssButton VALUE="密码修改轨迹查询" TYPE=hidden onClick="PwdChangeTrackQuery();">
    </td>
   </tr>
   <tr class= common>
     <td class=input>

   <INPUT class=cssButton VALUE="投连计价查询" TYPE=hidden onClick="ShowCountQuery();"  >
   <INPUT class=cssButton VALUE="保全试算查询" TYPE=hidden onClick="ShowEdorTrialQuery();" >
   <INPUT class=cssButton VALUE="  账户查询  " TYPE=hidden onClick="InsuredAccQuery();" >
   <INPUT class=cssButton VALUE="备注信息" TYPE=hidden onClick="ShowRemark();" >
	 <INPUT class=cssButton VALUE="问题件查询" TYPE=hidden onClick="ShowQuest();">
   <INPUT class=cssButton VALUE="核保查询" TYPE=hidden onClick="UWQuery();">
	 <INPUT class=cssButton VALUE="保单状态查询" TYPE=hidden onClick="StateQuery();">
	  </td>
   </tr>
     </td>
   </tr>
  </table>
  </Div>

<!--Modify by zhaorx 2006-03-06
  <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton4);">
    		</td>
    		<td class= titleImg>
    			理赔类
    		</td>
    	</tr>
    </table>
  <Div id= "divButton4" style= "display: ''">

     <table class= common align=center>
        <tr class= common>
            <td class=input>
                <INPUT class=cssButton VALUE="  理赔查询  " TYPE=button onclick="ClaimGetQuery();">
                <INPUT class=cssButton VALUE="体检结果查询" TYPE=hidden onclick="HealthQuery();">
                <INPUT class=cssButton VALUE="生调结果查询" TYPE=hidden onclick="MeetQuery();">
                <INPUT class=cssButton VALUE="再保回复查询" TYPE=hidden onclick="UpReportQuery();">
                <INPUT class=cssButton VALUE="核保通知书查询" TYPE=hidden onclick="UWNoticQuery();">
                <INPUT class=cssButton VALUE="客户合并通知书查询" TYPE=hidden onclick="KHHBNoticQuery();">
                <INPUT class=cssButton VALUE="自核提示查询" TYPE=hidden onclick="UWErrQuery();">
                <!--INPUT class=cssButton VALUE="健康告知查询" TYPE=button onclick="ImpartQuery();"-->
                <!--INPUT class=cssButton VALUE="被保人体检资料查询" TYPE=button onclick="InsuredHealthQuery();"-->
                <!--INPUT class=cssButton VALUE="被保人健康告知查询" TYPE=button onclick="InsuredImpartQuery();"-->
                <!--INPUT class=cssButton VALUE="投保人保额累计查询" TYPE=button onclick="AmntAccumulateQuery();"-->
                <!--INPUT class=cssButton VALUE="被保人保额累计查询" TYPE=button onclick="InsuredAmntAccumulateQuery();"-->
        <!--        </td>
            </tr>
        </table>
    </Div>-->


  <table style= "display: none">
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton5);">
    		</td>
    		<td class= titleImg>
    			渠道类
    		</td>
    	</tr>
    </table>
  <Div id= "divButton5" class="maxbox1" style= "display: none">
  	<table class= common align=center>
        <tr class= common>
        <td class= input align=lift>
  	        <INPUT class=cssButton VALUE="工资历史查询" TYPE=hidden onClick="ShowWageHistoQuery();">
            <INPUT class=cssButton VALUE="组织关系查询" TYPE=button onClick="ShowOrganizationQuery();">
            <INPUT class=cssButton VALUE="血缘关系查询" TYPE=button onClick="ShowConsanguinityQuery();">
            <INPUT class=cssButton VALUE="福利历史查询" TYPE=hidden onClick="ShowWelfareHistoQuery();">
            <INPUT class=cssButton VALUE="考核历史查询" TYPE=button onClick="ShowCheckHistoQuery();">
            <input class="cssButton" value="代理人基本信息查询" type="button" onClick="AgentQuery();">
            <!-- INPUT class=cssButton VALUE="业务员基本信息查询" TYPE=button onclick="ShowOperInfoQuery();" -->
            <INPUT class=cssButton VALUE="代理人历史信息查询" TYPE=button onClick="ShowOperHistoQuery();">
      </tr>
     </td>
   </table>
  </Div>

     <table>
    	 <tr>
         <td class=common>
			     <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton6);">
    		 </td>
    		 <td class= titleImg>
    			 客户保单
    		 </td>
    	 </tr>
     </table>

  <Div id= "divButton6" class="maxbox1" style= "display: ''">
    <table class= common align=center>
      <tr class= common>
        <td class= input align=lift>
          <INPUT class=cssButton VALUE="投保人已承保保单查询" TYPE=button onClick="AppntqueryProposal();">
          <INPUT class=cssButton VALUE="投保人未承保保单查询" TYPE=button onClick="AppntqueryNotProposal();">
          <INPUT class=cssButton VALUE="被保人已承保保单查询" TYPE=button onClick="InsuredqueryProposal();">
          <INPUT class=cssButton VALUE="被保人未承保保单查询" TYPE=button onClick="InsuredqueryNotProposal();">
	     </td>
  	  </tr>
  	</table>
  </Div>

  <table class= common>
    <tr class= common>
      <td>
        <INPUT class=cssButton VALUE="  返  回  " TYPE=button onClick="GoBack();">
      </td>
   </tr>
  </table>
 </form>
 <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
