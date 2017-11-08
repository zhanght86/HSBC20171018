<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GrpContPolInit.jsp"%>
<SCRIPT src="GrpContPol.js"></SCRIPT>
<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
loggerDebug("GrpContPolInput","扫描录入");
%>
<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
<%
}
%>
</head>
<body  onload="initForm();">
<form name=fm id=fm target="fraSubmit">
	<!-- 合同信息部分 GroupPolSave.jsp-->
	<DIV id=DivLCContButton STYLE="display: ">
		<table id="table1">
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
				</td>
				<td class="titleImg">集体合同信息</td>
			</tr>
		</table>
	</DIV>
	<Div id="divGroupPol1" class=maxbox style="display:  ">
		<table border="0" class=common>
			<tr class=common>
				<!--TD class=title>
					集体投保单号码
				</td>
				<td class=input>
					<input class="common" name=ProposalGrpContNo readonly TABINDEX="-1"  MAXLENGTH="40">
				</TD-->
				<td class="title">投保单号码</td>
				<td class="input"><input class="common wid" name=PrtNo id=PrtNo elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="投保单号码|notnull&len<=14" readonly=true></td>
			    <td class="title">呈报件号</td>
				<td class="input"><input class="common wid" name="ReportNo" id=ReportNo readonly=true></td>
			    <TD  class=title>销售渠道</TD>
                <TD  class=input><Input class="codeno" name=SaleChnl id=SaleChnl elementtype=nacessary verify="销售渠道|notnull" readonly=true>
				<input name=SaleChnlName id=SaleChnlName class=codename readonly=true></TD>
			</tr>
			<tr class=common>
				<TD  class=title>中介机构</TD>
                <TD  class=input8><Input class="code" id=AgentCom name=AgentCom readonly=true"></TD>
			  	<td class=title>投保申请日期</td>
				<td class=input>
				<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=PolApplyDate verify="投保申请日期|notnull&DATE" verifyorder="1" readonly=true onClick="laydate({elem: '#PolApplyDate'});" id="PolApplyDate"><span class="icon">
				<a onClick="laydate({elem: '#PolApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class=title>保单生效日期</td>
				<td class=input><input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=CValiDate verify="保单生效日期|notnull&DATE" verifyorder="1" readonly=true onClick="laydate({elem: '#CValiDate'});" id="CValiDate">
				<span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr> 
			<tr class=common>
                <td CLASS="title">管理机构</td>
		        <td CLASS="input"><Input name=ManageCom class='codeno' id="ManageCom" readonly=true><input name=ManageComName id=ManageComName class=codename readonly=true></td>
	            <td class="title">财务收费日期</td>
				<td class="input"><input class="common wid" name="PayDate" id=PayDate readonly="readonly"></td>
			</tr>
        </table>
        <hr class="line">
        <table class="common">
  		    <tr class="common">
			    <td class="title">业务员代码</td>
			    <td class="input"><input NAME="AgentCode" id=AgentCode VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary readonly="readonly"></td>
			    <td class="title">业务员姓名</td>
			    <td CLASS="input"><input NAME="AgentName" id=AgentName readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary  verifyorder="1" readonly=true></td>
			    <td CLASS="title">所属机构</td>
			    <td CLASS="input"><input NAME="AgentManageCom" id=AgentManageCom readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" readonly=true verify="管理机构|code:station&amp;notnull">
				<input name="AppntManageComName" id=AppntManageComName elementtype=nacessary CLASS="codename" readonly="readonly"></td>
		    </tr>
		    <tr class="common">
			    <td CLASS="title">所属分部</td>
			    <td CLASS="input"><input NAME="BranchAttr" id=BranchAttr  verifyorder="1" VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
				<input NAME="AgentGroup" id=AgentGroup type="hidden" value=""></td>
                <td class="title">星级业务员</td>
                <td class="input"><input class="codeno" readonly="readonly" name="starAgent" id=starAgent>
				<input class="codename" name="starAgentName" id=starAgentName readonly="readonly"></td>
                <td class="title">多业务员，请勾选</td>
                <td class="title"><input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();"></td>
            </tr>
        </table>
        <div id="DivMultiAgent" style="display:none">
            <table>
                <tr>
                    <td class=common>
                        <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
                    </td>
                    <td class= titleImg>其他业务员信息</td>
                </tr>
            </table>
            <div id="divMultiAgent1" style="display: ">
                <table class="common">
                    <tr class="common">
                        <td style="text-align:left" colSpan="1">
                            <span id="spanMultiAgentGrid"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
	</Div>
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol2);"></td>
			<td class=titleImg>投保单位资料（客户号 <input class=common name=GrpNo readonly=true>）</td>
		</tr>
	</table>
	<Div id="divGroupPol2" class=maxbox style="display:  ">
		<table class=common>
			<tr>
				<td class=title>单位名称</td>
				<td class=input><input class="common wid" name=GrpName id=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60" readonly=true></td>
				<td class=title>资产总额(元)</td>
				<td class=input><input class="common wid" name=Asset id=Asset verify="资产总额|num&len<=17" readonly=true></td>
				<td class=title>单位性质</td>
				<td class=input><input class=codeno name=GrpNature id=GrpNature verify="单位性质|notnull&code:grpNature&len<=10" readonly=true>
				<input class=codename name=GrpNatureName id=GrpNatureName readonly=true elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>行业类别</td>
				<td class=input><input class=codeno name=BusinessType  id=BusinessType verify="行业类别|notnull&code:BusinessType&len<=20" readonly=true>
				<input class=codename name=BusinessTypeName id=BusinessTypeName readonly=true elementtype=nacessary></td>
				<td class=title>员工总数</td>
				<td class=input><input class="common wid" name=Peoples id=Peoples  elementtype=nacessary verify="单位总人数|notnull&int" readonly=true></td>
				<td class=title>单位传真</td>
				<td class=input><input class="common wid" name=Fax id=Fax readonly=true></td>
			</tr>
			<tr>
				<td class=title>社保登记证号码</td>
				<td class=input><input class="common wid" name=BankAccNo1 id=BankAccNo1 readonly=true></td>
			</tr>
			<tr>
				<td class=title>单位地址编码</td>
				<td class=input><input class="code" name="GrpAddressNo" id=GrpAddressNo readonly=true></td>
			</tr>
			<tr class=common>
				<td class=title>单位地址</td>
				<td class=input colspan="3"><input class="common3" name=GrpAddress id=GrpAddress elementtype=nacessary verify="单位地址|notnull&len<=60" readonly=true></td>
				<td class=title>邮政编码</td>
				<td class=input><input class="common wid" name=GrpZipCode id=GrpZipCode maxlength=6 elementtype=nacessary verify="邮政编码|notnull&zipcode" readonly=true></td>
			</tr>
			<tr class=common>
				<td class=title>保险联系人一</td>
			</tr>
			<tr class=common>
				<td class=title>姓  名</td>
				<td class=input><input class="common wid" name=LinkMan1 id=LinkMan1 elementtype=nacessary verify="保险联系人一姓名|notnull&len<=10" readonly=true></td>
				<td class=title>联系电话</td>
				<td class=input><input class="common wid" name=Phone1 id=Phone1 elementtype=nacessary verify="保险联系人一联系电话|notnull&len<=30" readonly=true></td>
				<td class=title>E-MAIL</td>
				<td class=input><input class=common name=E_Mail1 id=E_Mail1 verify="保险联系人一E-MAIL|len<=60&Email" readonly=true></td>
			</tr>
			<tr class=common>
				<td class=title>部  门</td>
				<td class=input colspan=3><input class=common3 name=Department1 id=Department1 verify="保险联系人一部门|len<=30" readonly=true></td>
			</tr>
			<tr class=common>
				<td class=title>保险联系人二</td>
			</tr>
			<tr class=common>
				<td class=title>姓  名</td>
				<td class=input><input class="common wid" name=LinkMan2 id=LinkMan2 verify="保险联系人二姓名|len<=10" readonly=true></td>
				<td class=title>联系电话</td>
				<td class=input><input class="common wid" name=Phone2 id=Phone2 verify="保险联系人二联系电话|NUM&len<=30" readonly=true></td>
				<td class=title>E-MAIL</td>
				<td class=input><input class="common wid" name=E_Mail2 id=E_Mail2 verify="保险联系人二E-MAIL|len<=60&Email" readonly=true></td>
			</tr>
			<tr class=common>
				<td class=title>部  门</td>
				<td class=input colspan=3><input class=common3 name=Department2 verify="保险联系人二部门|len<=30" readonly=true></td>
			</tr>
			<tr class=common>
				<td class=title>付款方式</td>
				<td class=input><input class=codeno name=GetFlag id=GetFlag readonly=true><input class=codename name=GetFlagName readonly=true ></td>
				<td class=title>开户银行</td>
				<td class=input><input class=codeno name=BankCode id=BankCode verify="开户银行|code:bank&len<=24" readonly=true><input class=codename name=BankCodeName readonly=true></td>
				<td class=title>账  号</td>
				<td class=input><input class="common wid" name=BankAccNo id=BankAccNo verify="帐号|len<=40" readonly=true></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title>备注</td>
			</tr>
			<tr class=common>
				<td class=title><textarea name="Remark" id=Remark cols="110" rows="3" class="common" readonly=true></textarea></td>
			</tr>
			<input type=hidden name=EmployeeRate id=EmployeeRate verify="雇员自付比例|num&len<=5" readonly=true>
			<input type=hidden name=FamilyRate id=FamilyRate verify="家属自付比例|num&len<=80" readonly=true>
		</table>
	</Div>
	
	<table>
		<tr>
			<td class=common>
				<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
			</td>
			<td class=titleImg>集体保单险种信息</td>
		</tr>
	</table>
	<Div id="divGroupPol3" style="display:  ">
		<Div id="divGroupPol5" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>集体合同号码</td>
					<td class=input><input class="readonly" readonly name=GrpContNo ></td>
				</tr>
			</table>
		</Div>
		<!--录入的暂交费表部分 -->
		<Table class=common>
			<tr>
				<td style="text-align: left" colSpan=1>
					<span id="spanRiskGrid"></span>
				</td>
			</tr>
		</Table>
		<div id="divGroupPol5" style="display: none">
    		<table>
    			<tr>
    				<td class=common>
    					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol4);">
    				</td>
    				<td class=titleImg>险种信息</td>
    			</tr>
    		</table>
	    </div>
		<Div id="divGroupPol4" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>险种编码</td>
						<td class=input>
							<input class=codeno name=RiskCode readonly=true ondblclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskGrp',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary>
						</td>
						<td class=title>交费期间</td>
						<td class=input>
							<input class=codeno name=PayIntv  readonly=true ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>
						</td>
				</tr>
			</table>				
		</Div>
	</DIV>		
    <br /><hr class=line><br />
	<Div id="divnormalbtn" style="display:  ">
			<input class=cssButton VALUE="补名单"  TYPE=button onclick="grpFillList()">
			<input class=cssButton VALUE="保险计划查看"  TYPE=button onclick="grpRiskPlanInfo()">			
	</Div>
	<hr class=line>	
	<Div id="divquerybtn" style="display:  " align=right>
		<input VALUE="团体问题件查询"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
		<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
	</Div>
	<input type=hidden name="ContNo" id="ContNo">
	<input type=hidden name="AgentType" id="AgentType">
	<input type=hidden name="ScanType" id="ScanType">
	<input type=hidden name="ProposalGrpContNo" id="ProposalGrpContNo">
</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	<br /><br /><br /><br />
</body>
</html>
