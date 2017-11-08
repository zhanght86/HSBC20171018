<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：GrpFeeInput.jsp
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人 ：CrtHtml程序创建
//更新记录： 更新人  更新日期   更新原因/内容
%>
<%
String RiskName = request.getParameter("RiskName");
String GrpPolNo = request.getParameter("GrpPolNo");
String LoadFlag = request.getParameter("LoadFlag");
%>
<script>
	var ProposalGrpContNo = "<%=request.getParameter("ProposalGrpContNo")%>";
	var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
  var polNo = "<%=request.getParameter("polNo")%>";
  var MissionID = "<%=request.getParameter("MissionID")%>";
  var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="GrpFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GrpFeeInit.jsp"%>

<SCRIPT src="ProposalAutoMove5.js"></SCRIPT>
	<%
		if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
		{
		  loggerDebug("GrpFeeInput","扫描的情况"+request.getParameter("scantype"));
	%>
      <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
      <SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
	<%
	  }
	%>

</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="GrpFeeSave.jsp">
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class=titleImg align=center>合同险种信息</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''"> 
		<table class=common>
			<TR class=common>
				<TD class=title>
					集体合同号
				</TD>
				<TD class=input>
					<Input class="readonly wid" readonly name=ProposalGrpContNo id="ProposalGrpContNo" value="">
					<input type=hidden name=GrpContNo id="GrpContNo" value="">					
				</TD>
				<td >
					<a href="javascript:void(0)" class=button onclick="RiskQueryClick();">险种信息查询</a>
					<!-- <input type=button class="cssButton" value="险种信息查询" onclick="RiskQueryClick();"> -->
				</td>
			</tr>
			<tr class=common>
				<TD class=title>
					险种编码
				</TD>
				<TD class=input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode" onchange="CheckRisk();"  onclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo');"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true>
					<input type=hidden name=RiskFlag id="RiskFlag">
				</TD>
			
				<TD id="divmainriskname" style="display:none" class=title>
					主险编码
				</TD>
				<TD id="divmainrisk" style="display:none" class=input>
					<Input class=codeno maxlength=6 name=MainRiskCode id="MainRiskCode" ondblclick="getriskcode();return showCodeList('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,acodeSql,'b.GrpContNo',1);" onkeyup="getriskcode();return showCodeListKey('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,acodeSql,'b.GrpContNo',1);"><Input class=codename name="MainRiskCodeName" id="MainRiskCodeName" readonly=true>
				</TD>
				<td>
					<a href="javascript:void(0)" class=button onclick="QueryDutyClick();">险种责任查询</a>
					<!-- <Input VALUE="险种责任查询" class=cssButton TYPE=button onclick="QueryDutyClick();"> -->
				</td>
			</TR>
		</table>
		</div>
		</div>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>  
		<div id="divRiskAdd" style= "display: ''" align= left> 
			<a href="javascript:void(0)" class=button onclick="RiskAddClick();">定义责任要素</a>
		    <!-- <Input VALUE="定义责任要素" class=cssButton  TYPE=button onclick="RiskAddClick();"> -->
		</div>
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContPlanGrid);">
				</td>
				<td class= titleImg>险种特别信息</td>
			</tr>
		</table>
		<div id="divContPlanGrid" style="display: ''" align=center>
    		<table  class= common>
    			<tr  class= common>
    				<td text-align: left colSpan=1>
    					<span id="spanContPlanGrid" ></span>
    				</td>
    			</tr>
    		</table>
    		<Div  id = "divPage" align=center style = "display: 'none' ">
    			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
    			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
    			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
    			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
		    </Div>
		</div>
<!--
		<br>
		<input type=button class="cssButton" value="查  询" onclick="easyQueryClick();">
-->
		
	
		<!--table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGrpFee);">
				</td>
				<td class=titleImg>
					险种管理费明细
				</td>
			</tr>
		</table>
		<Div id="divGrpFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpFeeGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<br-->
		<Div  id= "divRiskSave" style= "display: ''" align= right> 
			<a href="javascript:void(0)" class=button onclick="submitForm();">险种信息保存</a>
			<a href="javascript:void(0)" class=button onclick="UptContClick();">险种信息更新</a>
			<a href="javascript:void(0)" class=button onclick="DelContClick();">险种信息删除</a>
			<!-- <input type=button class="cssButton" value="险种信息保存" onclick="submitForm();">
			<input type=button class="cssButton" value="险种信息更新" onclick="UptContClick();">
			<input type=button class="cssButton" value="险种信息删除" onclick="DelContClick();"> -->
		</Div>
		<hr class="line">
		<Div id="divPubAccGrid0" style="display:''">
			<div>
				<br>
				<a href="javascript:void(0)" style="display:none" class=button name="btnDefPubAcc" onclick="ShowPubAcc();">定义公共帐户</a>
		    	<a href="javascript:void(0)" class=button onclick="QureyPubAcc();">查询公共帐户</a>
				
			</div>
		    <br>
    		<!-- <input type=button class="cssButton" name="btnDefPubAcc" value="定义公共帐户" onclick="ShowPubAcc();">
    		<input type=button class="cssButton" value="查询公共帐户" onclick="QureyPubAcc();">  -->
		</Div> 
		<Div id="divPubAccGrid" style="display:none">
            <table class=common>
            	<tr class=common>
            		<td text-align: left colSpan=1><span id="spanPubAccGrid"></span></td>
            	</tr>
            </table>
			<Div  id= "PubAccSave" style= "display: ''" align= right>
				<a href="javascript:void(0)" class=button onclick="SavePubAcc();">公共帐户保存</a>
				<a href="javascript:void(0)" class=button onclick="UpdatePubAcc();">公共帐户更新</a>
				<a href="javascript:void(0)" class=button onclick="DelPubAcc();">公共帐户删除</a>
				<a href="javascript:void(0)" class=button onclick="CompPubAcc();">试算帐户余额</a>
    			<!-- <input type=button class="cssButton" value="公共帐户保存" onclick="SavePubAcc();">
    			<input type=button class="cssButton" value="公共帐户更新" onclick="UpdatePubAcc();">
    			<input type=button class="cssButton" value="公共帐户删除" onclick="DelPubAcc();">
    			<input type=button class="cssButton" value="试算帐户余额" onclick="CompPubAcc();"> -->
			</Div>
		</Div>
		<Div id="divPubAmntGrid0" style="display:''">
		<hr class="line">
			<br>
			<div>
			<a href="javascript:void(0)" class=button style="display:none" name="btnDefPubAmnt" onclick="ShowPubAmnt();">定义公共保额</a>
			<a href="javascript:void(0)" class=button onclick="PubAmntQueryClick();">查询公共保额</a>
			</div>
    		<!-- <input type=button class="cssButton" name="btnDefPubAmnt" value="定义公共保额" onclick="ShowPubAmnt();">
    		<input type=button class="cssButton" value="查询公共保额" onclick="PubAmntQueryClick();"> -->
    		<br> 
		</Div> 
		<Div id="divPubAmntGrid" style="display:none">
            <table class=common>
                <tr class=common>
                	<td text-align: left colSpan=1><span id="spanPubAmntGrid"></span></td>
                </tr>
            </table>
			<Div  id= "PubAmntSave" style= "display: ''" align= right>
				<a href="javascript:void(0)" class=button onclick="SavePubAmnt();">公共保额保存</a>
				<a href="javascript:void(0)" class=button onclick="UptContClick();">公共保额更新</a>
				<a href="javascript:void(0)" class=button onclick="DelPubAmnt();">公共保额删除</a>
    			<!-- <input type=button class="cssButton" value="公共保额保存" onclick="SavePubAmnt();">
    			<input type=hidden class="cssButton" value="公共保额更新" onclick="UptContClick();">
    			<input type=button class="cssButton" value="公共保额删除" onclick="DelPubAmnt();"> -->			
			</Div>
		</Div>
		<input type=hidden name=PlanType>
		<hr class="line">
		<Div  id= "divRiskRela" style= "display: ''" align= left>
			<br>
			<a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>
			<a href="javascript:void(0)" class=button onclick="GrpRiskFeeInfo();">管理费定义</a>
			<a href="javascript:void(0)" class=button onclick="PayRuleInfo();">缴费定义</a>
			<a href="javascript:void(0)" class=button onclick="AscriptionRuleInfo();">归属规则定义</a>
			<a href="javascript:void(0)" class=button onclick="GrpAccTrigger();">账户触发器</a>
			<a href="javascript:void(0)" class=button style= "display: none" onclick="feeRecord();">手续费定义</a>
			<a href="javascript:void(0)" class=button onclick="grpRiskPlanInfo();">保险计划定制</a>
			<a href="javascript:void(0)" class=button onclick="grpInsuList();">被保人清单</a>
			<!-- <input type=button class="cssButton" value="上一步" onclick="returnparent();">
			<Input VALUE="管理费定义" class=cssButton  TYPE=button onclick="GrpRiskFeeInfo();">
			<Input VALUE="缴费定义" class=cssButton  TYPE=button onclick="PayRuleInfo();">
		    <Input VALUE="归属规则定义" class=cssButton  TYPE=button onclick="AscriptionRuleInfo();">
			<Input VALUE="账户触发器" class=cssButton TYPE=button onclick="GrpAccTrigger();">
			<Input VALUE="手续费定义" class=cssButton TYPE=button onclick="feeRecord();" style= "display: 'none'">
			<Input VALUE="保险计划定制" class=cssButton TYPE=button onclick="grpRiskPlanInfo();" >
			<Input VALUE="被保人清单" class=cssButton TYPE=button onclick="grpInsuList();"> -->
		</Div>
		<Input type=hidden name=AppntNo id="AppntNo" value="">
		<input type=hidden name=mOperate id="mOperate">
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
<script>
  var acodeSql ="";
function getriskcode()
{
 acodeSql ="#"+fm.GrpContNo.value+"# and a.riskcode in (select riskcode from lmriskrela where relariskcode =#"+fm.RiskCode.value+"#)";
}
</script>
</html>
