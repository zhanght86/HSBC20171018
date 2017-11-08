<%
//程序名称：LLClaimExemptinput.jsp
//程序功能：豁免处理
//创建日期：2005-07-19
//创建人  ：yuejw
//更新记录：
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI"); 
	  String tClmNo = request.getParameter("ClaimNo");	//赔案号
%>
    <title>豁免处理 </title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/Verifyinput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="./LLClaimExempt.js"></SCRIPT>
    <%@include file="LLClaimExemptInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimExemptGrid);"></TD>
            <TD class= titleImg> 提取豁免信息 </TD>
        </TR>
    </Table>
    

    <Div id= "button" style= "display: none">
    <input type="hidden" TYPE=button class=cssButton name="exemptquery" value="获取豁免信息" onclick="LLExemptQueryClick();">
   <Input type="hidden" class=codeno readonly name=ExemptReason ondblclick="return showCodeList('llexemptreason',[this,ExemptReasonname],[0,1]);" onkeyup="return showCodeListKey('llexemptreason',[this,ExemptReasonname],[0,1]);">
    </div>
                        
   <Div id= "divLLClaimExemptGrid" style= "display: ''">
        <Table  class= common>

            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimExemptGrid" ></span> </TD>
            </TR>
        </Table>
<!--
                        <TD><input class=cssButton value="保  存" type=button onclick="LLExemptSaveClick();"></TD>
        <Table>
            <tr>
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="lastPage();"></td>
            </tr>
        </Table>
-->        
    </Div> 
    <a href="javascript:void(0);" name="exemptquery" class="button" onClick="LLExemptQueryClick();">获取豁免信息</a>
    <Div id= "divLLClaimExempt" style= "display: none">
        
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimExemptInfo);"></TD>
            <TD class= titleImg>豁免记录详细信息 </TD>
        </TR>
    </Table>
   	<Div id= "divLLClaimExemptInfo" style= "display: ''" class="maxbox">
		<TABLE class=common>
			<tr class=common>
				<td class=title> 催缴标记 </td>
				<!--<td class= input><input class="readonly" readonly  name="UrgePayFlag"></td>-->
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="UrgePayFlag" id="UrgePayFlag" CodeData="0|3^Y|催缴^N|不催缴" ondblclick="return showCodeListEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200)"  onclick="return showCodeListEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200)" onkeyup="return showCodeListKeyEx('UrgePayFlag', [this,UrgePayFlagName],[0,1],'','','','',200);"><input class=codename name=UrgePayFlagName id=UrgePayFlagName readonly></TD>
				<td class=title> 是否和账户相关 </td>
				<td class= input><input class="readonly wid" readonly  name="NeedAcc" id="NeedAcc"></td>
				<td class=title> 已交费次数 </td>
				<td class= input><input class="readonly wid" readonly  name="PayTimes" id="PayTimes"></td>                
			</tr>
			<tr class=common>
				<td class=title> 保费分配比率 </td>
				<td class= input><input class="readonly wid" readonly  name="Rate" id="Rate"></td>
				<td class=title> 起交日期 </td>
				<td class= input><input class="readonly wid" readonly  name="PayStartDate" id="PayStartDate"></td>
				<td class=title> 终交日期 </td>
				<td class= input><input class="readonly wid" readonly  name="PayEndDate" id="PayEndDate"></td>                
			</tr>
			<tr class=common>
				<td class=title> 每期保费 </td>
				<td class= input><input class="readonly wid" readonly  name="StandPrem" id="StandPrem"></td>
				<td class=title> 实际保费 </td>
				<td class= input><input class="readonly wid" readonly  name="Prem" id="Prem"></td>
				<td class=title> 累计保费 </td>
				<td class= input><input class="readonly wid" readonly  name="SumPrem" id="SumPrem"></td>                
			</tr>	
			<tr class=common>
				<td class=title> 交至日期 </td>
				<td class= input><input class="readonly wid" readonly  name="PaytoDate" id="PaytoDate"></td>
				<td class=title> 交费间隔 </td>
				<!--<td class= input><input class="readonly" readonly  name="PayIntv"></td>-->
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="PayIntv" id="PayIntv" CodeData="0|3^-1|不定期缴费^0|趸缴^1|月缴^1|季缴^6|半年缴^12|年缴^36|三年缴" ondblclick="return showCodeListEx('PayIntv', [this,PayIntvName],[0,1],'','','','',200)"  onclick="return showCodeListEx('PayIntv', [this,PayIntvName],[0,1],'','','','',200)" onkeyup="return showCodeListKeyEx('PayIntv', [this,],PayIntvName[0,1],'','','','',200);"><input class=codename name=PayIntvName id=PayIntvName readonly ></TD>			
				<td class=title> 额外风险评分 </td>
				<td class= input><input class="readonly wid" readonly  name="SuppRiskScore" id="SuppRiskScore"></td>                
			</tr>
			<tr class=common>			
		        <TD  class= title>免交标志</TD>
                <!--<TD  class= input><input class=common name="FreeFlag"></TD>-->
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="FreeFlag" id="FreeFlag" CodeData="0|3^0|不免交^1|免交" ondblclick="return showCodeListEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100)"  onclick="return showCodeListEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('FreeFlag', [this,FreeFlagName],[0,1],'','','','',100);"onfocus="FreeFlagClick();"><input class=codename name=FreeFlagName id=FreeFlagName readonly ></TD>
                <TD  class= title>状态</TD>
                <!--<TD  class= input><input class=common name="State"></TD>-->               
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="State" id="State" CodeData="0|3^0|承保时的保费项^1|承保时的加费项^2|本次项目加费项^3|前几次不通批单下的加费" ondblclick="return showCodeListEx('State', [this,StateName],[0,1],'','','','',200)" onclick="return showCodeListEx('State', [this,StateName],[0,1],'','','','',200)"onkeyup="return showCodeListKeyEx('State', [this,StateName],[0,1],'','','','',200);"><input class=codename name=StateName id=StateName readonly ></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>   
                
		</Table>
	</Div>		
	<Div id= "divLLClaimExemptFreeInfo" style= "display: none">
		<Table class=common>
			<tr class=common>
	            <TD class= title>豁免原因</TD>
				<td class=input><Input class="readonly wid" name=ExemptReasonname id=ExemptReasonname readonly ></td>
		        <TD class= title>免交起期</TD>
	            <TD class= input><input class="coolDatePicker wid" readonly name="FreeStartDate" id="FreeStartDate"  ></TD>
	            <TD class= title>免交止期</TD>
	            <TD class= input><input class="coolDatePicker wid" readonly name="FreeEndDate" id="FreeEndDate"  ></TD>
	        </tr>
            <tr class=common>
                <td class=title>豁免期数</td>
                <td class= input><input class="readonly wid" readonly  name="ExemptPeriod" id="ExemptPeriod"></td>
                <td class=title>豁免总保费</td>
                <td class= input><input class="readonly wid" readonly  name="ExemptSumAmnnt" id="ExemptSumAmnnt"></td>                                
                <TD class= title></TD>
                <TD class= input></TD>
            </tr>
                        
        </Table>
        <Table class= common>
            <tr class= common>           	               
                <td class= title> 豁免描述 </td>
            </tr> 
            <tr class= common>                  
                <td colspan="6" style="padding-left:16px"><textarea name="ExemptDesc" id="ExemptDesc" cols="225" rows="4" witdh=25% class="common" ></textarea></td>
            </tr>
		</Table>
	</Div>			
	
	<!--<Table>
		<TR>

			<TD><input class=cssButton value="返  回" type=button onclick="top.close();"></TD>
		</TR>
	</Table> -->

    </Div>
    <br>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    <!--隐藏表单区域-->
	<input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->  
	<input type=hidden id="PolNo" name="PolNo"><!--保单险种号码-->  
	<input type=hidden id="DutyCode" name="DutyCode"><!--责任编码-->  
	<input type=hidden id="PayPlanCode" name="PayPlanCode"><!--交费计划编码-->  
	<input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
	<input type=hidden id="ManageCom" name="ManageCom"><!--机构-->  
	
	<!--<TD class= title>免交比率</TD>--隐藏---->
	<TD class= input><input type=hidden class=codeno name="FreeRate" CodeData="0|3^0|全免^1|不免交" ondblclick="return showCodeListEx('FreeRate', [this,FreeRateName],[0,1])"onkeyup="return showCodeListKeyEx('FreeRate', [this,],FreeRateName[0,1]);"><input type=hidden class=codename name=FreeRateName readonly ></TD>            					
	
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>			
</body>
</html>
