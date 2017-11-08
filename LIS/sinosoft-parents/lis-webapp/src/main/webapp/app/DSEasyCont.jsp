  	<!------############# FamilyType:0-其他，1-家庭单 #######################--------->

<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
BeginOfCont tBeginOfCont = new BeginOfCont();
String tPrtNo=request.getParameter("prtNo");
String tInputTime=request.getParameter("InputTime");
String tInputNo="";
if(tInputTime.equals("0")){
	tInputNo="1";
}
if(tInputTime.equals("1")){
	tInputNo="2";
}
if(tInputTime.equals("2")){
	tInputNo="3";
}
String tSql="Select * from lbpocont where contno='"+tPrtNo+"' and inputNo='"+tInputNo+"'";
ExeSQL tAgentExeSQL = new ExeSQL();
SSRS tAgentSSRS = new SSRS();
tAgentSSRS = tAgentExeSQL.execSQL(tSql);
if (tAgentSSRS == null || tAgentSSRS.getMaxRow() == 0) {
	if(!tBeginOfCont.submitData(tPrtNo,tGI,tInputTime)){
%>
<script language="javascript">
alert("信息初始化失败！");
parent.window.close();
//window.close();
</script>
<%
	}
}
%>


<script language="javascript">
	//alert(14);
	//从服务器端取得数据:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	
	var prtNo = "<%=request.getParameter("prtNo")%>";
	//alert("1tMissionID:"+tMissionID);
	//alert("2tSubMissionID:"+tSubMissionID);
	//alert("3prtNo:"+prtNo);
	
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	//alert("4ActivityID:"+ActivityID);
	var NoType = "<%=request.getParameter("NoType")%>";
	//alert("5NoType:"+NoType);
	
	var ManageCom = "<%=tGI.ManageCom%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	
	//alert("6ManageCom:"+ManageCom);
	//alert("7ContNo:"+ContNo);
	var ContType = 1;
	//alert("8ContType:"+ContType);
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";
	var InputTime = "";
		InputTime = <%=request.getParameter("InputTime")%>;
	var InputNo = InputTime+1;
	var ScanType = "<%=request.getParameter("scantype")%>";
	//alert("9AppntNo :"+AppntNo);
	//alert("10AppntName :"+AppntName);
	//alert("11EdorType :"+EdorType);
	//alert("12ScanType :"+ScanType);
	//alert("13InputTime: "+InputTime);
</script>


<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
    
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <SCRIPT src="../common/javascript/VerifyWorkFlow.js"></SCRIPT>
  <script src="ProposalAutoMove3.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <%
//if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
//}
%>

  
<%@include file="DSEasyContInit.jsp"%>

<script src="DSEasyCont.js"></script>
</head>
  
<body  onload="initForm();" >
<form action="./DSContSave.jsp" method=post name=fm id=fm target="fraSubmit">
<!-- 合同信息 -->
<div id="ContMessage" style="display:''" >
	<table class=common >   
		<tr >
			<td class=titleImg >印刷号: <input class="common wid" readonly name=PrtNo id=PrtNo>
				<input readonly type=hidden name=ProposalContNo id=ProposalContNo>
			</td>
		</tr>
	</table>
</div>
<!-- 投保人信息 -->
<div id="DivComplexAppnt" style="display:''">
	<jsp:include page="DSEasyAppnt.jsp"/>
</div>

<div  id= "divTempFeeInput" style= "display:none">
	<Table class=common>
		<tr>
			<td text-align: left colSpan=1>
				<span id="spanInsuredGrid" ></span>
			</td>
		</tr>
	</Table>
</div>
<hr>
<!-- 被保人信息 -->
<jsp:include page="DSEasyInsured.jsp"/>

<table  class=common align=center>
	<tr align=right>
    	<td class=button >
       		&nbsp;&nbsp;
    	</td>
    	<td class=button width="10%" align=right>
    		<input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return saveInsured(1);">
    	</td>
    	<!-- <td class=button width="10%" align=right>
    		<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateInsured(1);">
    	</td> -->
	</tr>
</table>
<div  id= "divBnfInput" style= "display:''">
	<Table class=common>
		<tr><td class= titleImg>身故受益人资料</td></tr>
		<tr>
			<td text-align: left colSpan=1>
				<span id="spanBnfGrid" ></span>
			</td>
		</tr>
	</Table>
</div>

    
<!-- 被保人险种信息部分 -->
<div id=DivLCPol STYLE="display:''">
	<table class="common">
		<tr>
			<td class= titleImg>
				被保险人险种信息:
			</td>
		</tr>
    	<!-- <tr>
    		<td class=title>被保险人类型：<Input class="code" name="SequenceNo2"  value="1" CodeData="0|^1|主被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo2', [this],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo2', [this],[0,1]);">
    	</tr> -->
        <tr  class= common>
           	<td style="text-align:left" colSpan=1>
           		<span id="spanPolGrid" ></span>
           	</td>
        </tr>
    </table>
    <table  class=common align=center>
		<tr align=right>
    		<td class=button >
       			&nbsp;&nbsp;
    		</td>
	    	<td class=button width="10%" align=right>
	    		<input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return saveRisk(2);">
	    	</td>
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateRisk(2);">
	    	</td> -->
		</tr>
	</table>
    <table class=common>
      	<tr>
      		<td class=title5>保险费合计(人民币大写)</td>
      		<td class=input5><input verifyorder="3" class="common wid" name="Password" id=Password></td>
      		<td class=title5>(小写)￥</td>
      		<td class=input5><input verifyorder="3" class="common wid" name="SumPrem" id=SumPrem>元</td>
      	</tr>
      	<!-- 
      	<tr>
      		<td class=title>追加保险费(人民币大写)</td>
      		<td class=input><input verifyorder="3" class=common name="ApproveCode"></td>
      		<td class=title>￥</td>
      		<td class=input><input verifyorder="3" class=common name="ApproveTime">元</td>
      	</tr> 
      	
      	<TR class="common">
	        <TD class=title>首期交费</TD>
		    <TD class=input><input verifyorder="3" class="code" name="PayMode" CodeData="0|^0|银行转账^1|人工收取^2|支票^3|其它"
            								    ondblClick="showCodeListEx('ForPayMode',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForPayMode',[this],[0,1]);">
		    </TD>
		    <td class=title>续期缴费</td>
		    <TD class=input><input verifyorder="3" class="code" name="PayLocation" CodeData="0|^0|银行转账^1|人工收取^2|客户自交^3|其它"
            								    ondblClick="showCodeListEx('ForPayLocation',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForPayLocation',[this],[0,1]);">
		    </TD>
	    </TR>
	    <tr>
	    </tr> -->
	    <tr>
	    	<td class=title>是否同意自动续保</td>
	    	<td class=input><input verifyorder="3" class="code" name="RnewFlag"  id=RnewFlag
			style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            								    ondblClick="return showCodeList('rnewflag',[this],[0,1]);"
            								    onkeyup="return showCodeListKey('rnewflag',[this],[0,1]);">
            </td>
			<td class=title5></td>
      		<td class=input5></td>
	    </tr>
      <!-- 
      <tr>
      		<td class=title>交费方式</td>
      		<td class=input><input verifyorder="3" class="code" name=PayIntv CodeData="0|^0|趸交^1|年交^2|半年交^3|季交^4|月交"
            								    ondblClick="showCodeListEx('ForPayIntv',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForPayIntv',[this],[0,1]);">
            <td class=title>溢交保费处理方式</td>
            <td class=input><input verifyorder="3" class="code" name=OutPayFlag CodeData="0|^1|退费^2|抵交续期保费"
            								    ondblClick="showCodeListEx('ForOutPayFlag',[this],[0,1]);"
            								    onkeyup="showCodeListKeyEx('ForOutPayFlag',[this],[0,1]);"> 
     		<td class=title>领取方式</td>
    		<td class=input>
      			<Input verifyorder="3" NAME=StandbyFlag3 VALUE="" CLASS=code CodeData="0|^0|趸领^1|年领^2|月领" ondblClick="showCodeListEx('ForGetDutyKind',[this],[0]);" onkeyup="showCodeListKeyEx('ForGetDutyKind',[this],[0]);" >
    		</td>
    		<td class=title>年金开始领取年龄</td>
    		<td class=input><input verifyorder="3" class=common name=GetYear><input type=hidden name=GetYearFlag value="A">周岁</td>
     </tr> -->
      </table>
</div>
<div id=DivLCPol2 STYLE="display:none">	
	<hr>
	<table class=common>
		<tr><td class= titleImg>生存保险金、年金领取</td></tr>
    	<tr>
    		<td class=title>生存保险金/年金处理方式</td>
    		<td class=input><input verifyorder="3" class="code" name="LiveGetMode"  id=LiveGetMode
			style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            								    ondblClick="return showCodeList('livegetmode',[this],[0,1]);"
            								    onkeyup="return showCodeListKey('livegetmode',[this],[0,1]);">
          	</td>
    		<td class=title>红利领取方式</td>
            <td class=input><input verifyorder="3" class="code" name="BonusGetMode" id=BonusGetMode
			style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            								    ondblClick="return showCodeList('bonusgetmode',[this],[0,1]);"
            								    onkeyup="return showCodeListKey('bonusgetmode',[this],[0,1]);">
    	</tr>
    	<tr>	
    		
    		<td class=title>领取期限</td>
    		<td class=input><input verifyorder="3" class=common name=GetLimit id=GetLimit></td>
    		
    	</tr>
    </table>
</div>
    <table  class=common align=center>
		<tr align=right>
    		<td class=button >
       			&nbsp;&nbsp;
    		</td>
	    	<td class=button width="10%" align=right>
	    		<input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return saveGet(3);">
	    	</td>
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateGet(3);">
	    	</td> -->
		</tr>
	</table>
    <br>
    <!-- 告知信息 -->
    <div id="divLCImpart1" style="display:''">
          <table class="common">
          	<tr>
          		<td class= titleImg> 被保险人告知事项</td>
         	</tr>
              <tr class="common">
                  <td style="text-align:left" colSpan="1">
                      <span id="spanImpartGrid">
                      </span>
                  </td>
              </tr>
          </table>
    	<table class=common>
    		<tr>
    			<td>告知说明及备注栏</td>
    		</tr>
    		<tr>
    		<TD  class= input>
        		<input verifyorder="4" name="ImpartRemark" id=ImpartRemark class="common wid">
        	</TD>
    		</tr>
    	</table>
    </div>
    <hr>
    <table class=common>
    	<tr class=common>
    		<td class=titleImg>投保人、被保险人声明及授权</td>
    	</tr>
    	<tr class=common>
    		<td class=title>开户户名(投保人)</td>
	    	<td class=input><input verifyorder="4" class="common wid" name=AccName id=AccName></td>
	    	<td class=title>开户行(局)</td>
	    	<td><input verifyorder="4" NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20   id=BankCode
			style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('bank',[this,null],[0,1]);" onkeyup="return showCodeListKey('bank',[this,null],[0,1]);" ></td>
    		<td class=title>交费帐号</td>
	    	<td class=input><input verifyorder="4" class="common wid" name=BankAccNo id=BankAccNo></td>
    	</tr>
    	<tr>
    		<td class=title>投保人签名</td>
    		<td class=input><input verifyorder="4" class="common wid" name=AppSignName id=AppSignName></td>
    		<td class=title>被保险人/法定监护人签名</td>
    		<td class=input><input verifyorder="4" class="common wid" name=InsSignName2 id=InsSignName2 ></td>
    	</tr>
		<tr>
			<td class=title>投保申请日期</td>
			<td class=input><input verifyorder="4" class="common wid" name=PolApplyDate id=PolApplyDate ></td>
			<td class=title></td>
    		<td class=input></td>
			<td class=title></td>
    		<td class=input></td>
		</tr>
    </table>
    <hr>
    <table class=common>
    	<tr class=common>
    		<td class=titleImg>业务员填写栏</td>
    	</tr>
    	<tr>
    		<td class=title5>管理机构</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=ManageCom id=ManageCom readonly></td>
    		<td class=title5>扫描机构</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=ScanCom id=ScanCom readonly></td>
    	</tr>
    	<tr>
    		<td class=title5>业务员</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=Handler id=Handler></td>
    		<td class=title5>业务员工号</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=AgentCode id=AgentCode></td>
    	</tr>
    	<tr>
    		<td class=title5>中介机构名称</td>
            <td class=input5><input verifyorder="4" class="common wid" name=AgentComName id=AgentComName></td>
            <td class=title5>中介机构代码</td>
            <td class=input5><input verifyorder="4" class="common wid" name=AgentCom id=AgentCom></td>
    	</tr>
    	<tr>
    		<td class=title5>客户经理</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=Lang id=Lang></td>
    		<td class=title5>客户经理工号</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=Currency id=Currency></td>
    	</tr>
    	<tr>
    		<td class=title5>业务员签名</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=SignAgentName id=SignAgentName></td>
    		<td class=title5>日期</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=AgentSignDate id=AgentSignDate></td>
    	</tr>
    	<tr>
    		<td class=title>销售渠道</td>
    		<td class=input><input verifyorder="4" class="code" name="SaleChnl" id=SaleChnl
			style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            								    ondblClick="return showCodeList('SaleChnl',[this],[0,1]);"
            								    onkeyup="return showCodeListKey('SaleChnl',[this],[0,1]);">
            </td>
    	</tr>
    	<tr>
    		<td class=title5>初审员签名</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=FirstTrialOperator id=FirstTrialOperator></td>
    		<td class=title5>日期</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=FirstTrialDate id=FirstTrialDate></td>
    	</tr>
    	<tr>
    		<td class=title5>扫描员签名</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=UWOperator id=UWOperator></td>
    		<td class=title5>日期</td>
    		<td class=input5><input verifyorder="4" class="common wid" name=UWDate id=UWDate></td>
    	</tr>
    </table>
    <div id="divLCImpart2" style="display:none">
    <table>
    	<tr>
    		<td style="text-align:left" colSpan="1">
                      <span id="spanAgentImpartGrid"></span>
            </td>
    	</tr>
    </table>
    </div>
    <hr>
    <!-- 
    <table class=common>
    	<tr>
    		<td class=title>业务员签名</td>
    		<td class=input><input verifyorder="4" class=common name=SignAgentName></td>
    		<td class=title>日期</td>
    		<td class=input><input verifyorder="4" class=common name=AgentSignDate></td>
    	</tr>
    </table> -->
    <table class=common>
    	
    </table>
    <table  class=common align=center>
		<tr align=right>
    		<td class=button >
       			&nbsp;&nbsp;
    		</td>
	    	<td class=button width="10%" align=right>
	    		<input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return saveImpart(4);">
	    	</td>
	    	<!-- <td class=button width="10%" align=right>
	    		<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateImpart(4);">
	    	</td> -->
	    	<td class=button width="10%" align=right>
	    		<input class=cssButton name="Confirm"VALUE="录入完毕"  TYPE=button onclick="inputConfirm(0);">
	    	</td>
		</tr>
	</table>    
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               隐藏控件位置                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <div  id= "HiddenValue" style="display:none;float: right">
      <input type="hidden" id="fmAction" name="fmAction">
      <input type="hidden" id="contFillNo" name="contFillNo">
    	<!-- 工作流任务编码 -->
	  <input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
	  <input type="hidden" name="MissionID" id=MissionID value= "">
      <input type="hidden" name="SubMissionID" id=SubMissionID value= "">
      <input type="hidden" name="ActivityID" id=ActivityID value= "">
      <input type="hidden" name="NoType" id=NoType value= "">
      <input type="hidden" name="PrtNo2" id=PrtNo2 value= "">
      <input type="hidden" name="FamilyType" id=FamilyType value= "0">
      <input type="hidden" name="FamilyID" id=FamilyID value= "">
      <input type="hidden" name="PolType" id=PolType value= "0">
      <input type="hidden" name="CardFlag" id=CardFlag value= "0">
      <input type="hidden" name="SequenceNo" id=SequenceNo value= "1">
      <input type="hidden" name="ContNo" id=ContNo value="" >
      <input type="hidden" name="ProposalGrpContNo" id=ProposalGrpContNo value="">
      <input type="hidden" name="SelPolNo" id=SelPolNo value="">
      <input type="hidden" name="BQFlag" id=BQFlag>  
      <input type='hidden' name="MakeDate" id=MakeDate>
      <input type='hidden' name="MakeTime" id=MakeTime>
      <input type='hidden' name="InputNo" id=InputNo value="">
      <input type='hidden' name="InputTime" id=InputTime value="">
      <input type='hidden' id="ContType" id=ContType name="ContType" value="4">
      <input type='hidden' id="pagename" id=pagename name="pagename" value="16">
      <input type='hidden' id="GrpContNo" id=GrpContNo name="GrpContNo" value="00000000000000000000">
    </div>          
</form>


  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
