
<%
	//**************************************************************************************************
	//Name：LLClaimAuditInput.jsp
	//Function：普通案件审核
	//Author：zl
	//Date: 2005-6-20 14:00
	//Desc:
	//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	String tClmNo = request.getParameter("claimNo"); //赔案号

	String tMissionID = request.getParameter("MissionID"); //工作流任务ID
	String tSubMissionID = request.getParameter("SubMissionID"); //工作流子任务ID
	String tBudgetFlag = request.getParameter("BudgetFlag");
	String tAuditPopedom = request.getParameter("AuditPopedom");
%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="LLClaimAudit.js"></SCRIPT>
<%@include file="LLClaimAuditInit.jsp"%>
</head>

<body onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit">
<table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLLReport1);"></TD>
		<TD class=titleImg>立案信息</TD>
	</TR>
</table>
<Div id="divLLReport1" style="display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<!--    <TD  class= title> 事件号 </TD> -->
		<Input type=hidden class="readonly wid" readonly name=AccNo>
		<TD class=title>申请类型</TD>
		<TD class=input><Input class="wid" class=readonly readonly name=RgtClassName id=RgtClassName></TD>
		<TD class=title>赔案号</TD>
		<TD class=input><Input class="readonly wid" readonly name=RptNo id=RptNo></TD>
		<TD class=title>申请人姓名</TD>
		<TD class=input><Input class="readonly wid" readonly name=RptorName id=RptorName></TD>
	</TR>
	<TR class=common>
		<TD class=title>申请人电话</TD>
		<TD class=input><Input class="readonly wid" readonly name=RptorPhone id=RptorPhone></TD>
		<TD class=title>申请人通讯地址</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=RptorAddress id=RptorAddress></TD>
		<TD class=title>申请人与事故人关系</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=Relation id=Relation
			ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);"
            onclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);"
			onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input
			class=codename name=RelationName id=RelationName readonly=true></TD>
	</TR>
	<TR class=common>
		<TD class=title>出险地点</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=AccidentSite id=AccidentSite></TD>
		<TD class=title>申请受理日期</TD>
		<TD class=input><input class="readonly wid" readonly name="RptDate" id="RptDate"></TD>
		<TD class=title>管辖机构</TD>
		<TD class=input><Input class="readonly wid" readonly name=MngCom id=MngCom></TD>
	</TR>
	<TR class=common>
		<TD class=title>立案受理人</TD>
		<TD class=input><Input class="readonly wid" readonly name=Operator id=Operator></TD>
		<TD class=title>受托人类型</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=AssigneeType id=AssigneeType></TD>
		<TD class=title>受托人代码</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=AssigneeCode id=AssigneeCode></TD>
	</TR>
	<TR class=common>
		<TD class=title>受托人姓名</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=AssigneeName id=AssigneeName></TD>
		<TD class=title>受托人性别</TD>
		<TD class=input><Input class="readonly wid" readonly name=AssigneeSex id=AssigneeSex></TD>
		<TD class=title>受托人电话</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=AssigneePhone id=AssigneePhone></TD>
	</TR>
	<TR class=common>
		<TD class=title>受托人地址</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=AssigneeAddr 	id=AssigneeAddr></TD>
		<TD class=title>受托人邮编</TD>
		<TD class=input><Input class="readonly wid" readonly name=AssigneeZip id=AssigneeZip></TD>
		<TD class=title>出险原因</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=occurReason id=occurReason
			ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);"
            onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);"
			onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input
			class=codename name=ReasonName id=ReasonName readonly=true><font size=1
			color='#ff0000'><b>*</b></font></TD>
	</TR>
    <TR class=common>
         <TD class= title> 受理日期</TD>
         <TD class= input>  <input class="readonly wid" readonly  name="AcceptedDate" id="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
</table>
</Div>


<!--出险人信息表单-->
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanSubReportGrid"></span></td>
	</tr>
</table>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLLSubReport);"></td>
		<td class=titleImg>客户信息</td>
	</tr>
</table>
<Div id="divLLSubReport" style="display: ''" class="maxbox">


<span id="spanSubReport">
<table class=common>
	<TR class=common>
		<!--出险人编码,见隐藏区-->
		<TD class=title>客户姓名</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=customerName id=customerName></TD>
		<TD class=title>客户年龄</TD>
		<TD class=input><Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
		<TD class=title>客户性别</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=customerSex id=customerSex
			ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);"
			onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid"
			class=readonly  readonly name=SexName id=SexName readonly=true></TD>
	</TR>
	<TR class=common>
		<TD class=title>社保标志</TD>
		<TD class=input><Input class="readonly wid" readonly
			name=SocialInsuFlag id=SocialInsuFlag></TD>
		<TD class=title>出险日期</TD>
		<TD class=input><input class="readonly wid" readonly
			name="AccidentDate" id="AccidentDate"><font size=1 color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
		<!--<TD class= title> 出险原因</TD>
    	<TD class= input> <Input class=codeno disabled name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD> -->
	</TR>
	<!--<TR class= common>
    	<TD class= title> 治疗医院</TD>
    	<TD class= input> <Input class=codeno disabled name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
    	<TD class= title> 出险日期 </TD>
    	<TD class= input>  <input class= "readonly wid" readonly name="AccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font></TD>
    	<TD class= title> 出险细节</TD>
    	<TD class= input> <Input class=codeno disabled name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName readonly=true></TD>
    </TR> -->
	<TR class=common>
		<TD class=title>治疗情况</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=cureDesc id=cureDesc
			ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);"
			onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input
			class=codename name=cureDescName id=cureDescName readonly=true></TD>
		<TD class=title>单证齐全标示</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady disabled
			CodeData="0|^0|否^1|是"
			ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"
			onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input
			class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
		<TD class=title>重要信息修改标示</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsModify id=IsModify disabled
			CodeData="0|^0|否^1|是"
			ondblclick="return showCodeListEx('IsAllReady', [this,IsModifyName],[0,1])" onclick="return showCodeListEx('IsAllReady', [this,IsModifyName],[0,1])"
			onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsModifyName],[0,1])"><Input
			class=codename name=IsModifyName id=IsModifyName readonly=true></TD>
	</TR>
	<TR class=common>
		<span id="spanText7" style="display: 'none'">
		<table class=common>
        <TR class=common>
			<TD class=title>出险结果1</TD>
			<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1
				ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');"
				onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');"><input
				class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
                <TD class=title></td><TD class=input></td>
                <TD class=title></td><TD class=input></td></tr>
		</table>
		</span>
		<!--TD class= title> 出险结果2</TD>
    		<TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true></TD-->
	</tr>
</table>

<table class=common>
	<TR class=common>
		<TD class=title>出险结果编码</TD>
		<TD class=input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AccResult2 id=AccResult2 disabled
			ondblclick="return showCodeList('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onclick="return showCodeList('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"
			onkeyup="return showCodeListKey('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"></TD>
            <TD class=title>治疗医院代码</TD>
		<TD class=input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=hospital id=hospital disabled
			ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"
			onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"></TD>
            <TD class=title>出险细节编码</TD>
		<TD class=input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=accidentDetail id=accidentDetail disabled
			ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"
			onkeyup="return showCodeListKey('llaccidentdetail',[this],[0,1],null,null,null,null,'400');"></TD>
            </tr></table>
            <table class=common>
	<TR class=common>
		<TD class=title>出险结果</TD></tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
		<span id="spanText9" style="display: ''">
		<textarea name="AccResult2Name" disabled cols="196" rows="4" witdh=25%
			class="common" onkeydown="QueryOnKeyDown(4) "></textarea> </span></TD>
	</TR>
</table>

<table class=common>
	<TR class=common>
		
		<TD class=title>治疗医院</TD></tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
		<span id="spanText5" style="display: ''">
		<textarea disabled name="TreatAreaName" cols="196" rows="4" witdh=25%
			class="common" onkeydown="QueryOnKeyDown(2)"></textarea> </span> <!--隐藏表单作为显示数据用-->
		<!--<span id="spanText6" style="display: 'none'"> <textarea
			disabled name="TreatAreaNameDisabled" cols="100" rows="2" witdh=25%
			class="common"></textarea> </span></TD>-->
		<!-- <TD class= title> 出险日期 </TD>-->
		<input class="readonly" type="hidden" dateFormat="short"
			name="AccidentDate2">
	</TR>
</table>

<table class=common>
	<TR class=common>
		
		<TD class=title>出险细节</TD></tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
		<span id="spanText5" style="display: ''">
		<textarea disabled name="accidentDetailName" cols="196" rows="4"
			witdh=25% class="common" onkeydown="QueryOnKeyDown(1)"></textarea> </span> <!--隐藏表单作为显示数据用-->
		<!--<span id="spanText6" style="display: 'none'"> <textarea
			disabled name="accidentDetailNameDisabled" cols="100" rows="2"
			witdh=25% class="common"></textarea> </span>--></TD>
	</TR>
</table>

<table class=common>
	<TR class=common>
		<TD class=title>理赔类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
		<TD align=left>
			<input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input> 
			<input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">高残 </span></input> 
			<input style="vertical-align:middle" type="checkbox" value="04" name="claimType"><span style="vertical-align:middle"> 重大疾病</span> </input>
			<input style="vertical-align:middle" type="checkbox" value="01" name="claimType">	<span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input> 
			<!--<input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">豁免 </input> -->
			<input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"><span style="vertical-align:middle"> 医疗</span> </input> 
			<input style="vertical-align:middle" type="checkbox" value="05" name="claimType"> <span style="vertical-align:middle">特种疾病</span> </input> 
			<!-- <input type="checkbox" value="06" name="claimType" > <span style="vertical-align:middle">失业失能 </input> -->
		</td>
	</TR>
</table>

<table class=common>
	<TR class=common>
		<TD class=title>事故描述</TD>
	</tr>
	<TR class=common>
		<TD colspan="6" style="padding-left:16px"><textarea name="AccDesc" cols="196" rows="4"
			witdh=25% class="common" disabled></textarea></TD>
	</tr>
	<TR class=common>
		<TD class=title>备注</TD>
	</tr>
	<TR class=common>
		<TD colspan="6" style="padding-left:16px"><textarea name="Remark" cols="196" rows="4"
			witdh=25% class="common" disabled></textarea></TD>
	</tr>
</table>
</span></div>
<table>
	<tr>
	<!--
     <td><input class=cssButton name=QueryPerson value="出险人查询" type=button onclick="showInsPerQuery()" ></td>
     <td><input class=cssButton name=QueryReport value=" 事件查询 " type=button onclick="queryLLAccident()"></td>
     <td>
    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
     </td>
    -->
		<td><input class=cssButton name=QueryCont2 VALUE=" 保单查询 " TYPE=button onclick="showInsuredLCPol()"></td>
		<td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
		<td><input class=cssButton name=QueryCont5 VALUE="重要信息修改" TYPE=button onclick="editImpInfo()"></td>
	<!-- 
     <td><input class=cssButton type=hidden name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>  
 	-->
	</tr>
</table>

<!--立案结论信息-->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBaseUnit6);"></td>
		<td class=titleImg>立案结论信息</td>
	</tr>
</table>
<Div id="divBaseUnit6" style="display:''" class="maxbox1">
<table class=common>
	<TR class=common>
		<TD class=title>立案结论</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=RgtConclusion id=RgtConclusion
			ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);"
			onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input
			class=codename name=RgtConclusionName id=RgtConclusionName readonly=true><font
			size=1 color='#ff0000'><b>*</b></font></TD>
		<TD class=title>案件标识</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=rgtType id=rgtType
			ondblclick="return showCodeList('llrgttype', [this,rgtTypeTypeName],[0,1]);" onclick="return showCodeList('llrgttype', [this,rgtTypeTypeName],[0,1]);"
			onkeyup="return showCodeListKey('llrgttype', [this,rgtTypeTypeName],[0,1]);"><Input
			class=codename name=rgtTypeTypeName id=rgtTypeTypeName readonly=true></TD>
		
        <TD class=title></td><TD class=input></td>
	</tr>
</table>
</div>


<table>
	<tr>
		<td><input class=cssButton VALUE="账户资金调整" TYPE=button name="LCInsureAcc"  disabled onclick="ctrllcinsureacc();"></td>
		<td><INPUT class=cssButton name="dutySet" VALUE=" 匹配并理算 " TYPE=button onclick="showMatchDutyPay();"></td>
		<td><INPUT class=cssButton name="MedicalFeeInp" VALUE="医疗单证调整" TYPE=button onclick="showLLMedicalFeeInp();"></td>
        <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>   
   	    <td><input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
		<td><INPUT class=cssButton name="" VALUE="单证费用调整" TYPE=hidden onclick="showLLMedicalFeeAdj();"></td>
		<!--<td><INPUT class=cssButton name="AddAffix" VALUE="案件情况上载" TYPE=button onclick="AddAffixClick();"></td>
		<td><INPUT class=cssButton name="LoadAffix" VALUE="案件情况浏览" TYPE=button onclick="LoadAffixClick();"></td>
		-->
		<td><INPUT class=cssButton name="medicalFeeCal" type=hidden VALUE="费用计算查看" TYPE=button onclick="showLLMedicalFeeCal();"></td>	
		</tr>
</table>


<!--整个赔案计算信息-->
<span id="spanConclusion1" style="display: "> 
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divClaimGrid);"></td>
		<td class=titleImg>赔案计算信息</td>
	</tr>
</table>
<Div id="divClaimGrid" style="display:''">
<Table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanClaimGrid"></span></td>
	</tr>
</Table>
</div>

<!--按照赔案理赔类型计算信息-->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBaseUnit1);"></td>
		<td class=titleImg>理赔类型计算信息</td>
	</tr>
</table>
<Div id="divBaseUnit1" style="display:''">
<Table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanDutyKindGrid"></span>
		</td>
	</tr>
</Table>
</div>

<!--按照保单理赔类型计算信息-->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBaseUnit2);"></td>
		<td class=titleImg>保单计算信息</td>
	</tr>
</table>
<Div id="divBaseUnit2" style="display:''">
<Table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanPolDutyKindGrid"></span>
		</td>
	</tr>
</Table>
</div>

<!--保项匹配信息-->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBaseUnit3);"></td>
		<td class=titleImg>保项计算信息</td>
	</tr>
</table>
<Div id="divBaseUnit3" style="display:''">
<Table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
	</tr>
</Table>
</div>

<!--保全匹配信息-->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,PolDutyCode);"></td>
		<td class=titleImg>保全项目信息</td>
	</tr>
</table>
<Div id="LPEdorItem" style="display:''">
<Table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanLPEdorItemGrid"></span></td>
	</tr>
</Table>
</div>
</span>

<div id="divBaseUnit5" style="display: none">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divBaseUnit4);"></td>
		<td class=titleImg>保项赔付结论</td>
	</tr>
</table>
<Div id="divBaseUnit4" style="display:''">
<table class=common>
	<TR class=common>
		<TD class=title>赔付结论</TD>
		<TD class=input><Input class=codeno name=GiveType
			ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);"
			onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);"
			onfocus="choiseGiveTypeType();"><input class=codename
			name=GiveTypeName readonly=true></TD>
		<TD class=title></TD>
		<TD class=input><Input class=readonly readonly></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</tr>
</table>

<Div id="divGiveTypeUnit1" style="display: 'none'">
<table class=common>
	<TR class=common>
		<TD class=title>调整金额</TD>
		<TD class=input><Input class=common name=RealPay></TD>
		<TD class=title>调整原因</TD>
		<TD class=input><Input class=codeno name=AdjReason
			ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1],null,null,null,null,220);"
			onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"
			onfocus="choiseAdjReasonType();"><input class=codename
			name=AdjReasonName readonly=true></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</tr>
</table>

<Div id="divGiveTypeUnit3" style="display: 'none'">
<table class=common>
	<TD class=title>调整保单状态</TD>
	<!--金述海20070418修改,原因:保单状态只要三种
	<TD  class= input><Input class=codeno name=polstate ondblclick="return showCodeList('polstate',[this,polstateName],[0,1],null,null,null,null,120);" onkeyup="return showCodeListKey('polstate',[this,polstateName],[0,1]);"><input class=codename name=polstateName readonly=true></font>
	-->
	<TD class=input><Input class=codeno name=polstate
		ondblclick="return showCodeList('newpolstate',[this,polstateName],[0,1],null,null,null,null,120);"
		onkeyup="return showCodeListKey('polstate',[this,polstateName],[0,1]);"><input
		class=codename name=polstateName readonly=true></TD>
	<TD class=title></TD>
	<TD class=input><Input class=readonly readonly></TD>
	<TD class=title></TD>
	<TD class=input></TD>
</table>
</Div>

<table class=common>
	<TR class=common>
		<TD class=title>调整备注</TD>
	</tr>
	<TR class=common>
		<TD class=input><textarea name="AdjRemark" cols="100" rows="2"
			witdh=25% class="common"></textarea></TD>
	</tr>
</table>
</div>

<Div id="divGiveTypeUnit2" style="display: none">
<table class=common>
	<TR class=common>
		<TD class=title>拒付原因</TD>
		<TD class=input><Input class=codeno name=GiveReason
			ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);"
			onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input
			class=codename name=GiveReasonName readonly=true></td>
		<TD class=title>拒付依据</TD>
		<TD class=input><Input class=common name=GiveReasonDesc></td>
		<TD class=title></TD>
		<TD class=input></TD>
	</tr>
</table>
<table class=common>
	<TR class=common>
		<TD class=title>特殊备注</TD>
	</tr>
	<TR class=common>
		<TD class=input><textarea name="SpecialRemark" cols="100"
			rows="2" witdh=25% class="common"></textarea></TD>
	</tr>
</table>
</div>

<table>
	<tr>
		<td><INPUT class=cssButton name="addUpdate"  VALUE=" 添加修改 " TYPE=button onclick="AddUpdate()"></td>
		<td><INPUT class=cssButton name="saveUpdate" VALUE=" 保存修改 " TYPE=button onclick="SaveUpdate();"></td>
	</tr>
</table>
</div>
</div>


<table>
	<tr>
		<td><INPUT class=cssButton type=hidden name="DoHangUp" VALUE=" 保单挂起 " TYPE=button onclick="showLcContSuspend()"></td>
		<td><INPUT class=cssButton name="AccidentDesc" VALUE=" 备注信息 " TYPE=button onclick="showClaimDesc()"></td>
		<td><INPUT class=cssButton name="BeginInq" VALUE=" 发起调查 " TYPE=button onclick="showBeginInq()"></td>
		<td><INPUT class=cssButton name="ViewInvest" VALUE=" 查看调查 " TYPE=button onclick="showQueryInq()"></td>
		<td><INPUT class=cssButton name="SubmitReport" VALUE="发起呈报"  TYPE=button onclick="showBeginSubmit()"></td>
		<td><INPUT class=cssButton name="ViewReport" VALUE=" 查看呈报 " TYPE=button onclick="showQuerySubmit()"></td>
		<td><INPUT class=cssButton name="CreateNote" VALUE="受益人分配" TYPE=button onclick="showBnf()"></td>
		<!--<td><INPUT class=cssButton name="NoteCreate" VALUE="发出问题件"  TYPE=button onclick="showRgtAddMAffixList()"></td>
   			<td><INPUT class=cssButton name="NoteBack" VALUE="问题件回销"  TYPE=button onclick="showRgtMAffixList()"></td> -->
		<td><INPUT class=cssButton type=hidden name="" VALUE=" 发起二核 " TYPE=button onclick="showSecondUWInput()"></td>
	</tr>
	<tr>
		<td><INPUT class=cssButton type=hidden name="" VALUE=" 二核结论 " TYPE=button onclick="SecondUWInput();"></td>
		<td><INPUT class=cssButton type=hidden name="CreateNote" VALUE=" 帐户处理 " TYPE=button onclick="showLCInsureAccont()"></td>
		<td><INPUT class=cssButton type=hidden name="CreateNote" VALUE=" 豁免处理 " TYPE=button onclick="showExempt();"></td>
		<td><INPUT class=cssButton type=hidden name="AccidentDesc" VALUE=" 保单结算 " TYPE=button onclick="showPolDeal();"></td>
		<td><INPUT class=cssButton type=hidden name="AccidentDesc" VALUE=" 合同处理 " TYPE=button onclick="showContDeal();"></td>
	</tr>
</table>

<!--审核管理信息-->

<table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLLAudit);"></TD>
		<TD class=titleImg>审核管理</TD>
	</TR>
</table>
<Div id="divLLAudit" style="display: ''" class="maxbox1">
<table class=common>
	<TR class=common>
		<TD class=title>审核意见</TD>
	</tr>
	<TR class=common>
		<TD colspan="6" style="padding-left:16px"><textarea name="AuditIdea" cols="196" rows="4"
			witdh=25% class="common"></textarea></TD>
	</tr>
</table>

<table class=common>
	<TR class=common>
		<TD class=title>审核结论</TD>
		<!--TD  class= input><Input class=codeno name=AuditConclusion ondblclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class=codename name=AuditConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AuditConclusion id=AuditConclusion
			ondblclick="return showCodeList('llexamconclusion2',[this,AuditConclusionName],[0,1],null,null,null,1,'150');" onclick="return showCodeList('llexamconclusion2',[this,AuditConclusionName],[0,1],null,null,null,1,'150');"
			onkeyup="return showCodeListKey('llexamconclusion2',[this,AuditConclusionName],[0,1],null,null,null,1,'150');"
			onfocus="choiseConclusionType();"><input class=codename
			name=AuditConclusionName id=AuditConclusionName readonly=true><font size=1
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title>特殊备注</TD>
		<TD class=input><Input class="wid" class=common name=SpecialRemark1 id=SpecialRemark1></TD><TD class=title></TD>
		<TD class=input></TD>
        
        <TD style="display: none" class=title>拒付原因</TD>
		<TD style="display: none" class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ProtestReason id=ProtestReason
			ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);"
			onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input
			class=codename name=ProtestReasonName id=ProtestReasonName readonly=true></TD>
	</tr>
</table>

<Div id="divLLAudit2" style="display: none">
<table class=common>
	<TR class=common>
		
		<TD class=title>拒付依据</TD>
		<TD class=input><Input class="wid" class=common name=ProtestReasonDesc id=ProtestReasonDesc></TD>
        <TD class=title></TD>
		<TD class=input></TD><TD class=title></TD>
		<TD class=input></TD>
	</tr>
</table>
</div>

<table>
	<tr>
		<td><INPUT class=cssButton name="conclusionSave1" VALUE="结论保存" TYPE=button onclick="ConclusionSaveClick()"></td>
		<td><INPUT class=cssButton name="" VALUE="审核审批结论查询" TYPE=button onclick="LLQueryUWMDetailClick()"></td>
		<!-- <td><INPUT class=cssButton name="printAuditRpt" VALUE="打印审核报告"  TYPE=button onclick="LLPRTClaimAuditiReport()" ></td>    -->
		<td><INPUT class=cssButton type=hidden name="printPatchFee" VALUE="打印补缴保费通知" TYPE=button onclick="LLPRTPatchFeePrint()"></td>
	</tr>
</table>
</Div>


<table>
	<tr>
		<!-- <td><INPUT class=cssButton name="ConclusionUp" VALUE="超权限上报"  TYPE=button onclick="ConclusionUpClick()"></td> -->
		<td><INPUT class=cssButton name="ConclusionSave" VALUE="审核确认"	 TYPE=button onclick="ConclusionSaveClick2()"></td>
		<td><INPUT class=cssButton type=hidden name="ConclusionBack" VALUE="回退确认"	 TYPE=button onclick="AuditConfirmClick()"></td>
		<td><INPUT class=cssButton name="goBack" 		 VALUE="返  回"   TYPE=button onclick="goToBack()"></td>
	</tr>
</table>


<!--隐藏区,保存信息用-->
 <Input type=hidden name=customerNo> 
 <Input	type=hidden id="adjpay" name="adjpay">
 <input type=hidden	id="fmtransact" name="fmtransact"> 
 <input type=hidden	name=hideOperate value=''> 
 <input type=hidden	id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空--> 
 <Input	type=hidden id="ClmNo" name="ClmNo"><!--赔案号--> 
 <input	type=hidden id="isNew" name="isNew"><!--是否创建新建--> 
 <input	type=hidden id="clmState" name="clmState"><!--立案状态--> 
 <Input	type=hidden id="ICDCode" name="ICDCode"><!--icd10代码--> 
 <input	type=hidden id="RgtClass" name="RgtClass"><!--团险个险代码--> 
 <input	type=hidden id="BudgetFlag" name="BudgetFlag"><!--是否预付--> 
 <input	type=hidden id="AuditPopedom" name="AuditPopedom"><!--审核权限--> 
 <input	type=hidden id="ManageCom" name="ManageCom"><!--管理机构代码前两位--> 
 <input	type=hidden id="ALLManageCom" name="ALLManageCom"><!--管理机构--> 
 <input	type=hidden id="tOperator" name="tOperator"><!--登陆用户代码--> 
 <input	type=hidden id="WorkFlowFlag" name="WorkFlowFlag"> 
 <input	type=hidden id="MissionID" name="MissionID"> 
 <input type=hidden id="SubMissionID" name="SubMissionID"> 
 <input type=hidden id="PolNo" name="PolNo"> <!--##########打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到######-->
 <input type=hidden id="PrtSeq" name="PrtSeq">
 <input type=hidden class=common  name="AccRiskCode" > 
 <input type=hidden class=common  name="GrpContNo" > 
 </form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
