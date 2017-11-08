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
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupContQueryInit.jsp"%>
</head>
<body onload="initForm('<%=tGrpContNo%>');">
	<form method=post name=fm id="fm" target="fraSubmit">
	  <!-- 团单下个人单查询条件 -->
		<div id="divSearch">
			<table class=common border=0 width=100%>
				<tr>
					<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
					</td>
					<td class=titleImg align=center>请输入查询条件：</td>
				</tr>
			</table>
			<div class="maxbox1">
			<div  id= "divFCDay" style= "display: ''"> 
			<table class=common align=center>
				<TR class=common>
					<TD class=title>合同号码 </TD>
					<TD class=input><Input class="common wid" name=QContNo id="QContNo" TABINDEX="-1"  MAXLENGTH="40"></TD>
					<TD class=title>被保人姓名</TD>
					<TD class=input><Input class="common wid" name=QInsuredName id="QInsuredName"></TD>
					<TD class=title>管理机构 </TD>
					<TD class=input>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name=QManageCom id="QManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<!--Input class="code" name=GUWState CodeData= "0|^1|拒保^4|通融承保^6|待上级核保^9|正常承保^a|撤销投保单" ondblclick= "showCodeListEx('cond',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('cond',[this,''],[0,1]);"-->
					</TD>
				</TR>
			</table>
			</div>
			</div>
			<a href="javascript:void(0)" class=button onclick="easyQueryClick('<%=tGrpContNo%>');">查  询</a>
			<!-- <INPUT VALUE="查  询" class="cssButton" TYPE=button onclick="easyQueryClick('<%=tGrpContNo%>');"> -->
			<INPUT type="hidden" name="Operator" id="Operator" value="">
			<INPUT type="hidden" name="blank" id="blank" value="">
			<TD class=input><INPUT type=checkbox name=SearchFlag id="SearchFlag" checked=true></TD>
			<TD class=title>仅查询未通过自动核保的险种保单</TD>
		
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
			<hr class="line">
			<table>
		    	<tr>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
		    		</td>
		    		<td class= titleImg>
		    			 个单核保信息 
		    		</td>
		    	</tr>
		    </table>
			<Div  id= "divLCUWSub1" style= "display: ''">
		    	<table  class= common>
		        	<tr  class= common>
		    	  		<td text-align: left colSpan=1 >
							<span id="spanUWErrGrid" >
							</span> 
						</td>
					</tr>
				</table>
		      <!-- <INPUT VALUE="首  页" class= cssButton90 TYPE=button onclick="turnPage1.firstPage();"> 
		      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="turnPage1.previousPage();"> 					
		      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="turnPage1.nextPage();"> 
		      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onclick="turnPage1.lastPage();">  -->
			</Div>	
			<div align=left>
				<a href="javascript:void(0)" class=button onclick="goBack();">返  回</a>
				<a href="javascript:void(0)" class=button onclick="manuchk(3);">批量核保通过</a>
			</div>
			<!-- <INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="goBack();">
			<INPUT VALUE="批量核保通过" class="cssButton" TYPE=button onclick="manuchk(3);"> -->
		</Div>

		<DIV id="DivLCAppntIndButton" STYLE="display:none">
			<!-- 个人单投保人信息部分 -->
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCAppntInd);"></td>
					<td class=titleImg>投保单位信息</td>
				</tr>
			</table>
		</DIV>
		
		<DIV id="DivLCAppntInd" STYLE="display:none">
			<div class="maxbox1">
			<table class=common>
				<TR class=common>
					<TD class=title>投保单位</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly name=GrpName id="GrpName">
					</TD>
					<TD class=title>电话</TD>
					<TD class=input>
						<Input name=Phone id="Phone" CLASS="readonly wid" readonly>
					</TD>
					<TD class=title>单位地址</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly name="PostalAddress" id="PostalAddress">
					</TD>
				</TR>
				<TR class=common>
					
					<TD class=title>邮政编码</TD>
					<TD class=input>
						<Input CLASS="readonly wid" readonly name="ZipCode" id="ZipCode">
					</TD>
					<td CLASS="title">业务员编码</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="AgentCode" id="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</TR>
			</table>
			</div>
		</DIV>
		
		<!--个人单合同信息-->
		<DIV id="DivLCContButton" STYLE="display:none">
			<table id="table1">
				<tr>
					<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
					<td class="titleImg">个单合同信息</td>
				</tr>
			</table>
		</DIV>
		
		<div id="DivLCCont" STYLE="display:none">
			<div class="maxbox1">
			<table class="common" id="table2">
				<tr CLASS="common">
					<td CLASS="title">姓名</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredName" id="InsuredName" CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">性别</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredSex" id="InsuredSex"  CLASS="readonly wid"   MAXLENGTH="20" readonly>
					</td>
					<td CLASS="title">出生日期</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredBirthday" id="InsuredBirthday" MAXLENGTH="10" CLASS="readonly" readonly>
					</td>
				</tr>
				<tr CLASS="common">
					<td CLASS="title">证件类型</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredIDType" id="InsuredIDType" CLASS="readonly wid"  MAXLENGTH="2" readonly>
					</td>
					<td CLASS="title">证件号</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="InsuredIDno" id="InsuredIDno" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
					<td CLASS="title">职业类别</td>
					<td CLASS="input" COLSPAN="1">
						<input NAME="OccupationType" id="OccupationType" MAXLENGTH="10" CLASS="readonly wid" readonly>
					</td>
				</tr>
			</table>
			</div>
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
			<div id="divContUWResult" style="display : 'none'">
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
		    <Input type="hidden" class=Common name=ProposalContNo id="ProposalContNo" value="">
			<INPUT type="hidden" class=Common name=UWGrade id="UWGrade" value="">
			<INPUT type="hidden" class=Common name=AppGrade id="AppGrade" value="">
			<INPUT type="hidden" class=Common name=PrtNo id="PrtNo" value="">
			<INPUT type="hidden" class=Common name=PolNo id="PolNo" value="">
			<INPUT type="hidden" class=Common name=ContNo id="ContNo" value="">
			<INPUT type="hidden" class=Common name=GrpContNo id="GrpContNo" value="">
			<INPUT type="hidden" class=Common name=AppntNo id="AppntNo" value="">
			<INPUT type="hidden" class=Common name=ProposalNo id="ProposalNo" value="">
			<INPUT type="hidden" class=Common name=RiskCode id="RiskCode" value="">
			<INPUT type="hidden" class=Common name=RiskVersion id="RiskVersion" value="">
			<INPUT type="hidden" class=Common name=InsuredNo id="InsuredNo" value="">
			<INPUT type="hidden" class=Common name=Mult id="Mult" value="">
			<INPUT type="hidden" class=Common name=Prem id="Prem" value="">
			<INPUT type="hidden" class=Common name=Amnt id="Amnt" value="">
			<INPUT type="hidden" class=Common name=InsuredNo id="InsuredNo" value="">
				
				
			<hr class="line"></hr>
			<div>
				<a href="javascript:void(0)" class=button onclick="showPolDetail();">个单保单明细信息</a>
				<a href="javascript:void(0)" class=button onclick="queryProposal();">被保人已承保保单查询</a>
				<a href="javascript:void(0)" class=button onclick="queryNotProposal();">被保人未承保保单查询</a>
				<a href="javascript:void(0)" class=button onclick="showNewPolUWSub();">个人自动核保信息</a>
			</div>
			<!-- <INPUT VALUE="个单保单明细信息" Class="cssButton" TYPE=button onclick="showPolDetail();"> -->
			<!--input value="被保人保额累计信息" class=cssButton type=button onclick="amntAccumulate();" -->
			<!-- <INPUT VALUE="被保人已承保保单查询" class="cssButton"TYPE=button onclick="queryProposal();">
        	<INPUT VALUE="被保人未承保保单查询" class="cssButton"TYPE=button onclick="queryNotProposal();">
		   	<INPUT VALUE="个人自动核保信息" Class="cssButton" TYPE=button onclick="showNewPolUWSub();"> -->
		   	<!--INPUT VALUE="个单核保轨迹" Class="cssButton" TYPE=button onclick="showOldUWSub();"-->
		   	<br>
		   	<div>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="queryHealthImpart();">健康告知查询</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showBeforeHealthQ();">体检资料查询</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showHealth();">体检资料录入</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showAdd();">个单加费承保录入</a>
		   		<a href="javascript:void(0)" class=button type="hidden" onclick="showSpec();">个单特约承保录入</a>
		   	</div>
			<!-- <input value="健康告知查询"  type="hidden" class=cssButton type=button onclick="queryHealthImpart();">
			<input value="体检资料查询" type="hidden" class=cssButton type=button onclick="showBeforeHealthQ();" > -->         	    
	     <!--INPUT VALUE="契调资料查询" class=cssButton TYPE=button onclick="RReportQuery();"-->	
	     	<!-- <input value="体检资料录入" type="hidden" class=cssButton type=button onclick="showHealth();">
	     	<INPUT VALUE="个单加费承保录入" type="hidden"  Class="cssButton" type=button onclick="showAdd();">
			<INPUT VALUE="个单特约承保录入" type="hidden" Class="cssButton" type=button onclick="showSpec();"> -->
		   <!--input value="契调资料录入" class=cssButton type=button onclick="showRReport();"-->
				<!--INPUT VALUE="险种自动核保信息" Class="cssButton" TYPE=button onclick="showNewUWSub();"-->			
		</Div>
		<Div id = "divLCPolButton" style="display: none"></Div>
		<!-- 个人单核保结论 -->
		<div id="divUWResult" style="display : none">
			<table class=common border=0 width=100%>
				<tr>
					<td class=common>
    					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
					</td>
					<td class=titleImg align=center>个人单核保结论：</td>
				</tr>
			</table>
			<div  id= "divFCDay1" style= "display: ''"> 
			<table class=common border=0 width=100%>
				<TR class=common>
					<TD class=title5>
						个人单核保结论
					</TD>
					<TD class=input5>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=UWState id="UWState" CodeData="0|^1|拒保^4|通融承保^9|标准承保" onclick="showCodeListEx('condition',[this,''],[0,1]);" ondblclick="showCodeListEx('condition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">
					</TD>
					<td class=title5></td>
					<td class=input5></td>
				</TR>
				<tr>
					<TD class=title5>个人单核保意见</TD>
					<TD class=input5><textarea name="UWIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></TD>
					<td class=title5></td>
					<td class=input5></td>
				</tr>
			</table>
			</div>
			<a href="javascript:void(0)" class=button onclick="manuchk(1);">个单核保确定</a>
			<a href="javascript:void(0)" class=button onclick="InitClick();">返  回</a>
			<!-- <INPUT VALUE="个单核保确定" Class="cssButton" TYPE=button onclick="manuchk(1);"> -->
			<INPUT TYPE="hidden" NAME="UWDelay" id="UWDelay" VALUE="">
			<INPUT TYPE="hidden" NAME="tempgrpcontno" id="tempgrpcontno" VALUE="">
			<INPUT TYPE="hidden" NAME="tempriskcode" id="tempriskcode" VALUE="">
			<INPUT TYPE="hidden" NAME="PolNoHide"  id="PolNoHide" VALUE="">
			<!-- <INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="InitClick();"> -->
		</div>
		
			
			
	
		<div id="divChangeResult" style="display: 'none'"></div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
