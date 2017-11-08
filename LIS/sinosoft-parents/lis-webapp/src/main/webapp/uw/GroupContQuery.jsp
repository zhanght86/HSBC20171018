<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：GroupContQuery.jsp.
//程序功能：团单下个人人工核保
//创建日期：2003-12-29 11:10:36
//创建人 ：sxy  zhangrong
//更新记录： 更新人  更新日期   更新原因/内容
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupContQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupContQueryInit.jsp"%>
</head>
<body onload="initForm('<%=tGrpContNo%>');">
	<form method=post id="fm" name=fm target="fraSubmit">
	  <!-- 团单下个人单查询条件 -->
		<div id="divSearch">
			<table class=common border=0 width=100%>
				<tr>
					<td class=titleImg align=center>请输入查询条件：</td>
				</tr>
			</table>
			<div class="maxbox1">
			<table class=common align=center>
				<TR class=common>
					<TD class=title>合同号码 </TD>
					<TD class=input><Input class="common wid" id="QContNo" name=QContNo TABINDEX="-1"  MAXLENGTH="40"></TD>
					<TD class=title>被保人姓名</TD>
					<TD class=input><Input class="common wid" id="QInsuredName" name=QInsuredName></TD>
					<TD class=title>管理机构 </TD>
					<TD class=input>
						<Input class="code wid" id="QManageCom" name=QManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<!--Input class="code" name=GUWState CodeData= "0|^1|拒保^4|通融承保^6|待上级核保^9|正常承保^a|撤销投保单" ondblclick= "showCodeListEx('cond',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('cond',[this,''],[0,1]);"-->
					</TD>
				</TR>
			</table>
			</div>
			<INPUT VALUE="查  询" class="cssButton" TYPE=button onclick="easyQueryClick('<%=tGrpContNo%>');">
			<INPUT type="hidden" id="Operator" name="Operator" value="">
			<INPUT type="hidden" id="blank" name="blank" value="">
			<INPUT type="hidden" id="isContPlan" name="isContPlan" value="">
		
		</div>
		<Div id="divLCPol1" style="display: ''" >
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCPol1);"></td>
					<td class=titleImg>保单查询结果</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPolGrid"></span></td>
				</tr>
				<tr>
					<td align=center>
			<INPUT VALUE="首  页" class="cssButton90" TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();">
			<INPUT VALUE="尾  页" class="cssButton93" TYPE=button onclick="getLastPage();">
					</td>
				</tr>					
			</table>
		


			<P>
			<INPUT VALUE="返  回" style="float: left" class="cssButton" TYPE=button onclick="goBack();">
			<!--<INPUT VALUE="批量核保通过" class="cssButton" TYPE=button onclick="manuchk(3);">-->
			</P>
		</div>

		<DIV id=DivLCAppntIndButton STYLE="display:none">
			<!-- 个人单投保人信息部分 -->
			<table>
				<tr>
					<td class="common"><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCAppntInd);"></td>
					<td class=titleImg>投保单位信息</td>
				</tr>
			</table>
		</DIV>
		<DIV id=DivLCAppntInd class="maxbox1" STYLE="display:none">
			<table class=common>
				<TR class=common>
					<TD class=title>投保单位</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly id="GrpName" name=GrpName>
					</TD>
					<TD class=title>电话</TD>
					<TD class=input>
						<Input id="Phone" name=Phone CLASS="readonly wid" readonly>
					</TD>
					<TD class=title>单位地址</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly id="PostalAddress" name="PostalAddress">
					</TD>
				</TR>
				<TR class=common>
					
					<TD class=title>邮政编码</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly id="ZipCode" name="ZipCode">
					</TD>
					<td CLASS="title">业务员编码</td>
					<td CLASS="input" COLSPAN="1">
						<input id="AgentCode" NAME="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</TR>
			</table>
		</DIV>
		
		<!--个人单合同信息-->
		<DIV id=DivLCContButton  STYLE="display:none">
			<table id="table1">
				<tr>
					<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
					<td class="titleImg">个单合同信息</td>
				</tr>
			</table>
		</DIV>
		
		<div id="DivLCCont" class="maxbox1" STYLE="display:none">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title">姓名</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredName" NAME="InsuredName" CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">性别</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredSex" NAME="InsuredSex"  CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">出生日期</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredBirthday" NAME="InsuredBirthday" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">证件类型</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredIDType" NAME="InsuredIDType" CLASS="readonly wid"  MAXLENGTH="2" readonly>
					</td>
					<td CLASS="title">证件号</td>
					<td CLASS="input" COLSPAN="1">
						<input id="InsuredIDno" NAME="InsuredIDno" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
					<td CLASS="title">职业类别</td>
					<td CLASS="input" COLSPAN="1">
						<input id="OccupationType" NAME="OccupationType" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</tr>
			</table>
		</div>
<!--
		<div id="DivLCCont" STYLE="display:'none'">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title">个单合同投保单号码</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="ProposalContNo" CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="40">
					</td>
					<td CLASS="title">印刷号码</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="PrtNo" VALUE CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="20">
					</td>
					<td CLASS="title">管理机构</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="ManageCom" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">销售渠道</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="SaleChnl" CLASS="readonly" readonly MAXLENGTH="2">
					</td>
					<td CLASS="title">代理人编码</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCode" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
					<td CLASS="title">代理人组别</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentGroup" CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="12">
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">联合代理人编码</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCode1" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
					<td CLASS="title">代理机构</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCom" CLASS="readonly" readonly>
					</td>
					<td CLASS="title">银行营业网点</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentType" CLASS="readonly" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">备注</td>
					<td CLASS="input" COLSPAN="5">
						<input NAME="Remark" CLASS="readonly" readonly MAXLENGTH="255">
					</td>
				</tr>
			</table>
		</div>
-->		

			<%-- <hr class="line"/> -->--%>
			
			<div id="divContUWResult" style="display : none">
			<!--
			<INPUT VALUE="个单保单明细信息" Class="cssButton" TYPE=button onclick="showPolDetail();">
			<INPUT VALUE="个单既往投保信息" Class="cssButton" TYPE=button onclick="showApp();">
			<INPUT VALUE="个单以往核保记录" Class="cssButton" TYPE=button onclick="showOldUWSub();">
		
			<input value="被保人保额累计信息" class=cssButton type=button onclick="amntAccumulate();" > 
			<input value="健康告知查询" class=cssButton type=button onclick="queryHealthImpart();">
			 <input value="体检资料查询" class=cssButton type=button onclick="showBeforeHealthQ();" >         	    	
	     <input value="体检资料录入" class=cssButton type=button onclick="showHealth();">
		   <input value="契调资料录入" class=cssButton type=button onclick="showRReport();">
		   	<INPUT VALUE="个人自动核保信息" Class="cssButton" TYPE=button onclick="showNewPolUWSub();">			
			
-->	    
		</Div>

		<!-- 个人单单查询结果部分（列表） -->
		<Div id="divMain" style="display: none"></div>
		<!--附加险-->
		<Div id="divLCPol2" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" ></td>
					<td class=titleImg>险种查询结果</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPolAddGrid" onclick="getPolGridCho();"></span></td>
				</tr>
				<tr>
					<td align=center>
						<INPUT VALUE="首  页" class="cssButton90" TYPE=button onclick="getFirstPage();">
						<INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();">
						<INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();">
						<INPUT VALUE="尾  页" class="cssButton93" TYPE=button onclick="getLastPage();">
					</td>
				</tr>	
			</table>
		</div>
		
				<!----产品组合队列--------------->
			
		<Div id="divLCPlan" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" ></td>
					<td class=titleImg>产品查询结果</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPlanAddGrid"></span></td>
				</tr>
				<tr>
					<td align=center>
						<INPUT VALUE="首  页" class="cssButton90" TYPE=button onclick="getFirstPage();">
						<INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();">
						<INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();">
						<INPUT VALUE="尾  页" class="cssButton93" TYPE=button onclick="getLastPage();">
					</td>
				</tr>	
	  </table>
	  </Div>
<!--
			<table class=common border=0 width=100%>
				<tr>
					<td class=titleImg align=center>保单信息：</td>
				</tr>
			</table>
-->
<!--
			<table class=common align=center>
				<TR class=common>
					<TD class=title>投保单号码</TD>
					<TD class=input><Input class="readonly" readonly name=ProposalNo></TD>
					<TD class=title>险种编码</TD>
					<TD class=input><Input class="readonly" readonly name=RiskCode></TD>
					<TD class=title>险种版本</TD>
					<TD class=input><Input class="readonly" readonly name=RiskVersion></TD>
				</TR>
				<TR class=common>
					<TD class=title>被保人客户号</TD>
					<TD class=input><Input class="readonly" readonly name=InsuredNo></TD>
					<TD class=title>被保人姓名</TD>
					<!--TD class=input><Input class="readonly" readonly name=InsuredName></TD>
					<TD class=title>被保人性别</TD>
					<!--TD class=input><Input class="readonly" readonly name=InsuredSex></TD>
				</TR>
				<TR class=common>
					<TD class=title>份数</TD>
					<TD class=input><Input class="readonly" readonly name=Mult></TD>
					<TD class=title>保费</TD>
					<TD class=input><Input class="readonly" readonly name=Prem></TD>
					<TD class=title>保额</TD>
					<TD class=input><Input class="readonly" readonly name=Amnt></TD>
				</TR>
				<TR class=common>
					<TD class=input>
						<INPUT type="hidden" class=Common name=UWGrade value="">
						<INPUT type="hidden" class=Common name=AppGrade value="">
						<INPUT type="hidden" class=Common name=PolNo value="">
						<INPUT type="hidden" class=Common name=ContNo value="">
						<INPUT type="hidden" class=Common name=GrpContNo value="">
						<INPUT type="hidden" class=Common name=AppntNo value="">
					</TD>
					</TR>
				</table>
-->
				    <Input type="hidden" class=Common id="ProposalContNo" name=ProposalContNo value="">
						<INPUT type="hidden" class=Common id="UWGrade" name=UWGrade value="">
						<INPUT type="hidden" class=Common id="AppGrade" name=AppGrade value="">
						<INPUT type="hidden" class=Common id="PrtNo" name=PrtNo value="">
						<INPUT type="hidden" class=Common id="PolNo" name=PolNo value="">
						<INPUT type="hidden" class=Common id="ContNo" name=ContNo value="">
						<INPUT type="hidden" class=Common id="GrpContNo" name=GrpContNo value="">
						<INPUT type="hidden" class=Common id="AppntNo" name=AppntNo value="">
						<INPUT type="hidden" class=Common id="ProposalNo" name=ProposalNo value="">
						<INPUT type="hidden" class=Common id="RiskCode"　name=RiskCode value="">
						<INPUT type="hidden" class=Common id="RiskVersion" name=RiskVersion value="">
						<INPUT type="hidden" class=Common id="InsuredNo" name=InsuredNo value="">
						<INPUT type="hidden" class=Common id="Mult" name=Mult value="">
						<INPUT type="hidden" class=Common id="Prem" name=Prem value="">
						<INPUT type="hidden" class=Common id="Amnt" name=Amnt value="">
						<INPUT type="hidden" class=Common id="ContPlanCode"　name=ContPlanCode value="">
						<INPUT type="hidden" class=Common id="PlanType" name=PlanType value="">
						

				
				<div id='divOperateButtion'  style="display: none">
				<hr class="line">
		
				<INPUT VALUE="  个单保单明细信息  " Class="cssButton" id="uwButton1" name="uwButton1" TYPE=button onclick="showPolDetail();">
			  <input value=" 被保人保额累计信息 " class=cssButton id="uwButton2" name="uwButton2" type=button onclick="amntAccumulate();" >
			  <INPUT VALUE=" 个人自动核保信息 " Class="cssButton" id="uwButton5" name="uwButton5" TYPE=button onclick="showNewPolUWSub();">
			  <INPUT VALUE="   个单核保轨迹   " Class="cssButton" id="uwButton6" name="uwButton6" TYPE=button onclick="showOldUWSub();">
			  <br> 
				<INPUT VALUE="被保人已承保保单查询" class="cssButton" id="uwButton3" name="uwButton3" TYPE=button onclick="queryProposal();">
        <INPUT VALUE="被保人未承保保单查询" class="cssButton" id="uwButton4" name="uwButton4" TYPE=button onclick="queryNotProposal();">
		   	<INPUT VALUE ="被保人既往保全查询" Class="cssButton" id="uwButton14" name="uwButton14" TYPE=button onclick="queryEdor();">
		   <INPUT VALUE = "被保人既往理赔查询" Class="cssButton" id="uwButton15" name="uwButton15" TYPE=button onclick="queryClaim();">
		   	<br>
			<input value="    健康告知查询    " class=cssButton id="uwButton7" name="uwButton7" type=button onclick="queryHealthImpart();">
			 <input value="   体检资料查询     " class=cssButton id="uwButton8" name="uwButton8" type=button onclick="showBeforeHealthQ();" >         	    
	     <!--INPUT VALUE="契调资料查询" class=cssButton TYPE=button onclick="RReportQuery();"-->	
	     <input value="   体检资料录入   " class=cssButton id="uwButton9" name="uwButton9" type=button onclick="showHealth();">
		   <input value="   契调资料录入   " class=cssButton id="uwButton10" name="uwButton10" type=button onclick="showRReport();">
		   
				<!--INPUT VALUE="险种自动核保信息" Class="cssButton" TYPE=button onclick="showNewUWSub();"-->
				
							
	
			<hr class="line"/>			
		</Div>
		
		
		<Div id = divLCPolButton style="display: none">
		</Div>
		<!-- 个人单核保结论 -->
		<div id="divUWResult" style="display : none">
			<table class=common border=0 width=100%>
				<tr>
					<td class=titleImg align=center>个人单核保结论：</td>
				</tr>
			</table>
			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title>
						个人单核保结论
						<Input class="code" id="UWState" name=UWState readonly='true' CodeData="0|^1|拒保^j|加费承保^t|特约承保^x|限额承保^9|标准承保" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('condition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">
					</TD>
				</TR>
				<tr>
				  <td>
	        <hr class="line"/>
					<INPUT VALUE="个单加费承保录入" Class="cssButton" id="AddPremInput" name="AddPremInput" type=button onclick="showAdd();">
					<INPUT VALUE="个单特约承保录入" Class="cssButton" id="AddSpecInput" name="AddSpecInput" type=button onclick="showSpec();">
	        <hr class="line"/>
	        <td>
        </tr>
				<tr>
					<TD class=title>个人单核保意见</TD>
				</tr>

				<tr>
					<TD class=input><textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
				</tr>
			</table>
			
			<INPUT VALUE="个单核保确定" Class="cssButton" TYPE=button onclick="manuchk(1);">
			<INPUT TYPE="hidden" id="UWDelay" NAME="UWDelay" VALUE="">
			<INPUT TYPE="hidden" id="PolNoHide" NAME="PolNoHide" VALUE="">
			<INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="InitClick();">
		</Div>
		
			
			
	
		<div id="divChangeResult" style="display: none"></div>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
