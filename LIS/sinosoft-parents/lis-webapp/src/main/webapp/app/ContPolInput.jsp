<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体复核
// 5 -- 复核
// 6 -- 查询
// 7 -- 保全新保加人
// 8 -- 保全新增附加险
// 9 -- 无名单补名单
// 10-- 浮动费率
// 13-- 团单复核修改
// 14-- 团单核保修改
// 16-- 团单明细查询
// 99-- 随动定制

String tLoadFlag ="";
String tGrpContNo ="";

try
{
	tLoadFlag =request.getParameter( "LoadFlag" );
	tGrpContNo =request.getParameter( "GrpContNo" );
	//默认情况下为个人保单直接录入
	if( tLoadFlag ==null || tLoadFlag.equals( "" ))
		tLoadFlag ="2"; //LoadFlag本身的 意义关键就在于个单部分
}
catch( Exception e1 )
{
	tLoadFlag ="2";
}

GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
loggerDebug("ContPolInput","LoadFlag:" + tLoadFlag);
loggerDebug("ContPolInput","扫描类型:" + request.getParameter("scantype"));
%>
<script>
var prtNo ="<%=request.getParameter("prtNo")%>";
var polNo ="<%=request.getParameter("polNo")%>";
var scantype ="<%=request.getParameter("scantype")%>";
var MissionID ="<%=request.getParameter("MissionID")%>";
var ManageCom ="<%=request.getParameter("ManageCom")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
var ScanFlag ="<%=request.getParameter("ScanFlag")%>";
var PolApplyDate="<%=request.getParameter("PolApplyDate")%>";
if (ScanFlag ==null||ScanFlag=="null") ScanFlag="0";
if (polNo =="null") polNo ="";
if (prtNo =="null") prtNo ="";
var LoadFlag ="<%=tLoadFlag%>";

var tLoadFlag ="<%=tLoadFlag%>";
//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
var BQFlag ="<%=request.getParameter("BQFlag")%>";
if (BQFlag ==null||BQFlag=="null") BQFlag ="0";
//保全调用会传险种过来
var BQRiskCode ="<%=request.getParameter("riskCode")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPolInit.jsp"%>
<SCRIPT src="ContPolInput.js"></SCRIPT>
<SCRIPT src="ProposalAutoMove5.js"></SCRIPT>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
%>
<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
<%
}
%>
</head>
<body  onload="initForm();initElementtype();checkNotePad(prtNo,LoadFlag);">
	<form action="./ContPolSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<!-- 合同信息部分 GroupPolSave.jsp-->
		<DIV id=DivLCContButton STYLE="display:''">
			<table id="table1">
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
					</td>
					<td class="titleImg">集体合同信息</td>
				</tr>
			</table>
		</DIV>
		<Div id="divGroupPol1" style="display: ''" class="maxbox">
			<table border="0" class=common>
				<tr class=common>
					<!--TD class=title>
						集体投保单号码
					</td>
					<td class=input>
						<input class="common" name=ProposalGrpContNo readonly TABINDEX="-1"  MAXLENGTH="40">
					</TD-->
					<td class="title">投保单号码</td>
					<td class="input">
						<input class="common wid" name=PrtNo id=PrtNo elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="投保单号码|notnull&len<=14">
					</td>
				  <td class="title">呈报件号</td>
					<td class="input">
            <input class="common wid" name="ReportNo" id="ReportNo">
					</td>
				  <td class="title">代理协议书号</td>
					<td class="input">
            <input class="common wid" name="AgentConferNo" id="AgentConferNo">
					</td>
					<!--
					<td class=title>管理机构</td>
					<td class=input>
						<input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<input class=codeno name=ManageCom verify="管理机构|code:comcode&notnull" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
					</td>
					-->
				</tr>
				<tr class=common>
					 <td class="title">服务协议书号</td>
					<td class="input">
            <input class="common wid" name="ConferNo" id="ConferNo">
					</td>
				  <td class="title">签报件</td>
					<td class="input">
            <input class="common wid" name="SignReportNo" id="SignReportNo">
					</td>
					<td class=title>投保申请日期</td>
					<td class=input>
						<!--<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=PolApplyDate verify="投保申请日期|notnull&DATE verifyorder="1"">-->
                        <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDate'});" verify="投保申请日期|notnull&DATE verifyorder="1"" dateFormat="short" name=PolApplyDate id="PolApplyDate"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>

				</tr> 
				<tr class=common>
						<td class=title>保单生效日期</td>
					<td class=input>
						<!--<input class="common" dateFormat="short" elementtype=nacessary onblur="checkCValidate();" name=CValiDate verify="生效日期|notnull&DATE">-->
                        <Input class="coolDatePicker" onblur="checkCValidate();" onClick="laydate({elem: '#CValiDate'});" verify="生效日期|notnull&DATE" dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>			    		

			    <td CLASS="title">管理机构
    	    </td>
			    <td CLASS="input">
			      <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" NAME="ManageCom" id="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull"><input NAME="ManageComName" id="ManageComName" elementtype=nacessary CLASS="codename" readonly>
    	    </td>
    	    <td class="title">财务收费日期</td>
					<td class="input">
            <input class="common wid" name="PayDate" id="PayDate" readonly>
					</td>

				</tr>
				<tr>
				    <td class="title">销售方式</td>		
				    <td class="input">
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AgentType id=AgentType verify="销售方式|notnull" ondblclick="return showCodeList('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);"onclick="return showCodeList('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);"><input class=codename name=AgentTypeName id=AgentTypeName readonly=true elementtype=nacessary>
					</td>
					<td class=title>中介机构</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MediAgentCom id=MediAgentCom ondblclick="return showCodeList('GrpAgentCom',[this,MediAgentComName],[0,1],null,'managecom like #'+ fm.ManageCom.value + '%# and Branchtype=#2# and actype=#' + fm.AgentType.value + '#','1',1);" onclick="return showCodeList('GrpAgentCom',[this,MediAgentComName],[0,1],null,'managecom like #'+ fm.ManageCom.value + '%# and Branchtype=#2# and actype=#' + fm.AgentType.value + '#','1',1);" onkeyup="return showCodeListKey('GrpAgentCom',[this,MediAgentComName],[0,1],null,'managecom like #'+ fm.ManageCom.value + '%# and actype=#' + fm.AgentType.value + '#','1',1);"><input class=codename name=MediAgentComName id=MediAgentComName readonly=true>
					</td>
					<td class=title>中介机构业务员</td>
					<td class=input>
						<input class="wid" NAME=MediAgentCode id=MediAgentCode VALUE="" CLASS=common>
					</td>
				</tr>
<!--
				<tr class = common>
					  
				</tr>
-->
<!--
				<tr class=common>
					<td class=title>代理机构</td>
					<td class=input>
						<input class=codeno name=AgentCom ondblclick="return showCodeList('AgentCom',[this,AgentComName],[0,1],null, fm.all('ManageCom').value, 'ManageCom');" onkeyup="return showCodeListKey('AgentCom',[this,AgentComName],[0,1],null, fm.all('ManageCom').value, 'ManageCom');"><input class=codename name=AgentComName readonly=true>
					</td>
					<td class=title>业务员</td>
					<td class=input>
						<input NAME=AgentCode VALUE="" MAXLENGTH=8 CLASS=code8 elementtype=nacessary ondblclick="return queryAgent();"onkeyup="return queryAgent2();" verify="业务员|notnull">
					</td>
					<td class=title>业务员组别</td>
					<td class=input>
						<input class="readonly"  readonly name=AgentGroup verify="代理人组别|notnull&len<=12">
					</td>
				</tr>
-->
</table>
<hr class="line">
<table class="common">
  		<tr class="common">
			<td class="title">业务员代码
    	</td>
			<td class="input">
			  <input NAME="AgentCode" id="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code wid" elementtype=nacessary onkeyup="return queryAgent();" ondblclick="return queryAgent();">
      </td>
			<td class="title">业务员姓名
    	</td>
			<td CLASS="input">
			  <input NAME="AgentName" id="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary  verifyorder="1" >
      </td>
			<td CLASS="title">所属机构
    	</td>
			<td CLASS="input">
				<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" NAME="AgentManageCom" id="AgentManageCom" readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onclick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull"><input name="AppntManageComName" id="AppntManageComName" elementtype=nacessary CLASS="codename" readonly>
    	</td>
		</tr>
		<tr class="common">
			<td CLASS="title">所属分部
    	</td>
			<td CLASS="input">
			  <input NAME="BranchAttr" id="BranchAttr"  verifyorder="1" VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
    	  <input NAME="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">
        星级业务员
      </td>
      <td class="input">
        <input class="codeno" readonly name="starAgent" id="starAgent"><input class="codename" name="starAgentName" id="starAgentName" readonly>
      </td>
      <td class="title">多业务员，请勾选
      </td>
      <td class="title">
        <input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();">
      </td>

      </table>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                   多业务员MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <div id="DivMultiAgent" style="display:'none'">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
            </td>
            <td class= titleImg>
                其他业务员信息
            </td>
        </tr>
    </table>

    <div id="divMultiAgent1" style="display:''">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMultiAgentGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
      <table class="common">
					<td class=title>溢交保费方式</td>
					<td class=input>
						<input class=codeno name=OutPayFlag value="1" MAXLENGTH=1 ondblclick="return showCodeList('OutPayFlag', [this,OutPayFlagName],[0,1]);" onkeyup="return showCodeListKey('OutPayFlag', [this,OutPayFlagName],[0,1]);" verify="溢交保费处理方式|code:OutPayFlag&notnull"><input class=codename name=OutPayFlagName readonly=true value="退费">
					</td>
				<tr class=common>
					<td class=title>参保形式</td>
					<td class=input>
						<input class=codeno name=EnterKind ondblclick="return showCodeList('enterkind',[this,EnterKindName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('enterkind',[this,EnterKindName],[0,1],null,null,null,1);"><input class=codename name=EnterKindName readonly=true>
					</td>
					<td class=title>保额等级</td>
					<td class=input>
						<input class=codeno name=AmntGrade ondblclick="return showCodeList('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);"><input class=codename name=AmntGradeName readonly=true>
					</td>
					<td class=title>单位可投保人数</td>
					<td class=input>
						<input name=Peoples3 class=common>
					</td>
				</tr>
				<tr class=common>
					<td class=title>在职人数</td>
					<td class=input>
						<input name=OnWorkPeoples class=common>
					</td>
					<td class=title>退休人数</td>
					<td class=input>
						<input name=OffWorkPeoples class=common>
					</td>
					<td class=title>其他人员人数</td>
					<td class=input>
						<input name=OtherPeoples class=common>
					</td>
				</tr>
				<tr class=common>
					<td class=title>连带被保险人数（家属）</td>
					<td class=input>
						<input name=RelaPeoples class=common>
					</td>
				</tr>
				<tr class=common>
					<td class=title>配偶人数</td>
					<td class=input>
						<input name=RelaMatePeoples class=common>
					</td>
					<td class=title>子女人数</td>
					<td class=input>
						<input name=RelaYoungPeoples class=common>
					</td>
					<td class=title>其他人员人数</td>
					<td class=input>
						<input name=RelaOtherPeoples class=common>
					</td>
				</tr>
					<TD class=title>
					联合代理人代码
					</TD>
					<TD class=input>
					<input class=common type=hidden name=AgentCode1>
					<input class=common type=hidden name=MissionID>
					<input class=common type=hidden name=SubMissionID>
					<input class="common" type=hidden name=ProposalGrpContNo>
					</TD>
			</table>
-->
		</Div>


		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol2);">
				</td>
				<td class=titleImg>
					投保单位资料（客户号 <input class=common name=GrpNo><input id="butGrpNoQuery" class=cssButton VALUE="查  询" TYPE=button onclick="showAppnt();"> ）(首次投保单位无需填写客户号)
				</td>
			</tr>
		</table>
		<Div id="divGroupPol2" style="display: ''" class="maxbox">
			<table class=common>
			    <TR class="common">
        			<TD class="title">VIP客户</TD>
        			<TD class=input>
        				<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" name="AppntVIPValue" id="AppntVIPValue" class="codeno" verify="VIP客户|CODE:vipvalue" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly>
        			</TD>
                    <td class=title>单位名称</td>
					<td class=input>
						<input class="wid" class=common name=GrpName id=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60">
					</td>
					<td class=title>资产总额(元)</td>
					<td class=input>
						<input class="wid" class=common name=Asset id=Asset verify="资产总额|num&len<=17">
					</td>
        		</TR>
				<tr>
					
					<td class=title>单位性质</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GrpNature id=GrpNature verify="单位性质|notnull&code:grpNature&len<=10" ondblclick="showCodeList('GrpNature',[this,GrpNatureName],[0,1]);" onclick="showCodeList('GrpNature',[this,GrpNatureName],[0,1]);" onkeyup="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1]);"><input class=codename name=GrpNatureName id=GrpNatureName readonly=true elementtype=nacessary>
					</td>
                    <td class=title>行业类别</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=BusinessType id=BusinessType verify="行业类别|notnull&code:BusinessType&len<=20" ondblclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);" onclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);" onkeyup="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);"><input class=codename name=BusinessTypeName id=BusinessTypeName readonly=true elementtype=nacessary>
					</td>
					<td class=title>单位总人数</td>
					<td class=input>
						<input class="wid" class=common name=Peoples id=Peoples  elementtype=nacessary verify="单位总人数|notnull&int">
					</td>
				</tr>
				<tr class=common>
					
					<td class=title>传真号码</td>
					<td class=input>
						<input class="wid" class=common name=Fax id=Fax>
					</td>
                    <td class=title>社会保障号</td>
					<td class=input>
						<input class="wid" class=common name=BankAccNo1 id=BankAccNo1 >
					</td>
				</tr>
					
<!--
				<tr class=common>
					<td class=title>在职人数</td>
					<td class=input>
						<input class=common name=AppntOnWorkPeoples verify="在职人数|int">
					</td>
					<td class=title>退休人数</td>
					<td class=input>
						<input class=common name=AppntOffWorkPeoples verify="退休人数|int">
					</td>
					<td class=title>其他人员人数</td>
					<td class=input>
						<input class=common name=AppntOtherPeoples verify="其他人员人数|int">
					</td>
				</tr>
-->
<!--
        <tr class=common>
					<td class=title>单位总机</td>
					<td class=input>
						<input class=common  name=Phone elementtype=nacessary verify="单位总机|notnull&NUM&len<=30">
					</td-->
					<!--td class=title>成立时间</td>
					<td class=input>
						<input class="coolDatePicker" dateFormat="short" name=FoundDate>
					</td>
				</tr>
-->
				<tr>
					<td class=title>联系地址编码</td>
					<td class=input>
						<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name="GrpAddressNo" id="GrpAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0],'', '', '', true);">
					</td>
                    <td class=title>联系地址</td>
					<td class=input>
						<input class="common3 wid" name=GrpAddress id=GrpAddress elementtype=nacessary verify="单位地址|notnull&len<=60">
					</td>
					<td class=title>邮政编码</td>
					<td class=input>
						<input class="wid" class=common name=GrpZipCode id=GrpZipCode  elementtype=nacessary verify="邮政编码|notnull&zipcode">
					</td>
				</tr>
				
				<tr class=common>
					<td class=title>保险联系人一</td><td class=input></td>
                    <td class=title>姓  名</td>
					<td class=input>
						<input class="wid" class=common name=LinkMan1 id=LinkMan1 elementtype=nacessary verify="保险联系人一姓名|notnull&len<=10">
					</td>
					<td class=title>联系电话</td>
					<td class=input>
						<input class="wid" class=common name=Phone1 id=Phone1 elementtype=nacessary verify="保险联系人一联系电话|notnull&len<=30">
					</td>
				</tr>
				<tr class=common>
					
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="wid" class=common name=E_Mail1 verify="保险联系人一E-MAIL|len<=60&Email">
					</td>
                    <td class=title>所在部门</td>
					<td class=input>
						<input class="wid" class=common3 name=Department1 verify="保险联系人一部门|len<=30">
					</td>
                    <td class=title>保险联系人二</td><td class=input></td>
				</tr>
				
				
				<tr class=common>
					<td class=title>姓  名</td>
					<td class=input>
						<input class="wid" class=common name=LinkMan2 id=LinkMan2 verify="保险联系人二姓名|len<=10">
					</td>
					<td class=title>联系电话</td>
					<td class=input>
						<input class="wid" class=common name=Phone2 id=Phone2 verify="保险联系人二联系电话|NUM&len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="wid" class=common name=E_Mail2 id=E_Mail2 verify="保险联系人二E-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>所在部门</td>
					<td class=input>
						<input class="wid" class=common3 name=Department2 id=Department2 verify="保险联系人二部门|len<=30">
					</td>
                    <td class=title>付款方式</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GetFlag id=GetFlag verify="付款方式|notnull&code:PayMode" ondblclick="return showCodeList('PayMode',[this,GetFlagName],[0,1]);" onclick="return showCodeList('PayMode',[this,GetFlagName],[0,1]);" onkeyup="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName id=GetFlagName readonly=true elementtype=nacessary>
					</td>
					<td class=title>开户银行</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=BankCode id=BankCode verify="开户银行|code:bank&len<=24" ondblclick="showCodeList('bank',[this,BankCodeName],[0,1]);" onclick="showCodeList('bank',[this,BankCodeName],[0,1]);" onkeyup="showCodeListKey('bank',[this,BankCodeName],[0,1]);"><input class=codename name=BankCodeName id=BankCodeName readonly=true>
					</td>
				</tr>
				<tr class=common>
					
					<td class=title>账  号</td>
					<td class=input>
						<input class="wid" class=common name=BankAccNo id=BankAccNo verify="帐号|len<=40">
					</td>
                    <td class=title></td><td class=input></td><td class=title></td><td class=input></td>
				</tr>

			</table>
			<table class=common>
				<tr class=common>
					<td class=title>备注</td>
				</tr>
				<tr class=common>
					<td colspan="6" style="padding-left:16px">
						<textarea name="Remark" cols="224" rows="4" class="common"></textarea>
					</td>
				</tr>
				<input type=hidden name=EmployeeRate verify="雇员自付比例|num&len<=5">
				<input type=hidden name=FamilyRate verify="家属自付比例|num&len<=80">
			</table>
		</Div>
			<!-- 告知信息部分（列表） -->
<!--
		<DIV id=DivLCImpart STYLE="display:''">
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart1);">
					</td>
					<td class=titleImg>客户服务需求</td>
				</tr>
			</table>
			<div id="divLCImpart1" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanServInfoGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart1);">
					</td>
					<td class=titleImg>团体告知信息</td>
				</tr>
			</table>
			<div id="divLCImpart1" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanImpartGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart2);">
					</td>
					<td class=titleImg>既往情况告知</td>
				</tr>
			</table>
			<div id="divLCImpart2" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanHistoryImpartGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart3);">
					</td>
					<td class=titleImg>严重疾病情况告知</td>
				</tr>
			</table>
			<div id="divLCImpart3" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanDiseaseGrid"></span>
						</td>
					</tr>
				</table>
			</div>
		</DIV>
-->
		<Div id="divButton" style="display: ''" align=left>
			<!--INPUT class=cssButton VALUE="查 询"  TYPE=button onclick="queryClick()">
			<input class=cssButton VALUE="修 改"  TYPE=button onclick="updateClick()">
			<input class=cssButton VALUE="删 除"  TYPE=button onclick="deleteClick()">
			<input class=cssButton VALUE="保 存"  TYPE=button onclick="submitForm();"-->
			<%@include file="OperateButton.jsp"%>
			<%@include file="../common/jsp/InputButton.jsp"%>
		</DIV>
		<Div id="divUpdateButton" style="display: 'none'" align=right>	
		    <table>
		        <tr>		
			        <td><input class=cssButton VALUE="修 改"  TYPE=button onclick="updateClick()"></td>
			    </tr>
			</table>
		</DIV>
		<Div id="divhiddeninfo" style="display: 'none'">
			<table class=common>
				<tr class=common>
					<td class=title>销售渠道</td>
					<td class=input>
						<!--<input class="readonly" readonly name=SaleChnl verify="销售渠道|code:SaleChnl&notnull">-->
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno  name=SaleChnl  id=SaleChnl verify="销售渠道|code:SaleChnl&notnull"  ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName id=SaleChnlName readonly=true elementtype=nacessary>
					</td>   
					<td class=title>注册资本金</td>
					<td class=input>
						<input class="wid" class=common name=RgtMoney id=RgtMoney verify="注册资本金|num&len<=17">
					</td>
					<td class=title>净资产收益率</td>
					<td class=input>
						<input class="wid" class=common name=NetProfitRate id=NetProfitRate verify="净资产收益率|num&len<=17">
					</td>
				</tr>
				<tr class=common>
					<td class=title>主营业务</td>
					<td class=input>
						<input class="wid" class=common name=MainBussiness id=MainBussiness verify="主营业务|len<=60">
					</td>
					<td class=title>单位法人代表</td>
					<td class=input>
						<input class="wid" class=common name=Corporation id=Corporation verify="单位法人代表|len<=20">
					</td>
					<td class=title>机构分布区域</td>
					<td class=input>
						<input class="wid" class=common name=ComAera id=ComAera verify="机构分布区域|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>职务</td>
					<td class=input>
						<input class="wid" class=common name=HeadShip1 id=HeadShip1 verify="保险联系人一职务|len<=30">
					</td>
					<td class=title>传真</td>
					<td class=input>
						<input class="wid" class=common name=Fax1 id=Fax1 verify="保险联系人一传真|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>职务</td>
					<td class=input>
						<input class="wid" class=common name=HeadShip2 id=HeadShip2 verify="保险联系人二职务|len<=30">
					</td>
					<td class=title>传真</td>
					<td class=input>
						<input class="wid" class=common name=Fax2 id=Fax2 verify="保险联系人二传真|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>币种</td>
					<td class=input>
						<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=wid name=Currency id=Currency verify="币种|len<=2" ondblclick="showCodeList('currency',[this]);"  onclick="showCodeList('currency',[this]);" onkeyup="showCodeListKey('currency',[this]);">
					</td>
				</tr>
			</table>
		</DIV>
		<table class=common>
    		<tr>
    		    <td class="title">产品组合录入，请勾选<input type="checkbox" name="PlanFlag" value="true" onclick="inputPlan();"></td>
            </tr>
        </table>
		<DIV id="divGrpNormalRisk" style="display: ''">
    		<table>
    			<tr>
    				<td class=common>
    					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
    				</td>
    				<td class=titleImg>集体保单险种信息</td>
    			</tr>
    		</table>
    		<Div id="divGroupPol3" style="display: ''">
    			<Div id="divGroupPol5" style="display: 'none'" class="maxbox1">
    				<table class=common>
    					<tr class=common>
    						<td class=title>集体合同号码</td>
    						<td class=input>
    							<input class="readonly wid" readonly name=GrpContNo id=GrpContNo >
    						</td><td class=title></td>
                            <td class=input></td>
    					</tr>
    				</table>
    			</Div>
    			<!--录入的暂交费表部分 -->
    			<Table class=common>
    				<tr>
    					<td text-align: left colSpan=1>
    						<span id="spanRiskGrid"></span>
    					</td>
    				</tr>
    			</Table>
    			<Div  id="divPage" align=center style="display:'none'">
        			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
        			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
        			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
        			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
    		    </Div>
    			<table>
    				<tr>
    					<td class=common>
    						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol4);">
    					</td>
    					<td class=titleImg>险种信息</td>
    				</tr>
    			</table>
    			<Div id="divGroupPol4" style="display: ''" class="maxbox1">
    				<table class=common>
    					<tr class=common>
    						<td class=title>险种编码</td>
    						<td class=input>
    							<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskGrp',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>
    						</td>
    						<td class=title>交费期间</td>
    						<td class=input>
    							<!--<input class=codeno name=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>-->
    							<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PayIntv id=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');"><input class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>	
    						</td>
                            <TD class=title8 colspan=1>
                              				  分红标志
                              				</TD>
                              				<TD class=input8>
                              				    <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=wid name=BonusFlag id=BonusFlag value="" CodeData="0|^0|批量分红^1|个别分红" ondblclick="return showCodeListEx('BonusFlag',[this]);" onkeyup="return showCodeListKeyEx('BonusFlag',[this]);">
                              			    </TD>
    <!--
    						<td class=title>预计人数</td>
    						<td class=input>
    							<input class=common name=ExpPeoples  elementtype=nacessary>
    						</td>
    -->
    					</tr>
    					
    					<tr class=common>
    					   <td class=title>保费</td>
    						<td class=input>
    							<input class="wid" class=common name=ExpPrem id=ExpPrem elementtype=nacessary value="0">
    						</td>
    						<td class=title>保额/份数</td>
    						<td class=input>
    							<!--<input class=codeno name=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>-->
    							<input class="wid" class=common name=ExpAmnt id=ExpAmnt>
    						</td>
    						<td>
    						    <Div id="divRiskDeal" style="display: ''">
                					<table>
                			        	<tr>
                							<td>
                								<input type =button class=cssButton value="添加险种" onclick="addRecord();">
                							</td>
                							<td>
                								<input type =button class=cssButton value="删除险种" onclick="deleteRecord();">
                							</td>
                						</tr>						
                					</table>
                				</Div>
    						</td>
    					</tr>
    				</table>
    				
    			</Div>
    		</DIV>
	    </DIV>
	    <DIV id="divGrpPlanRisk" style="display: 'none'">
	        <table>
    			<tr>
    				<td class=common>
    					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPlan);">
    				</td>
    				<td class=titleImg>集体保单产品组合信息</td>
    			</tr>
    		</table>
    		<Div id="divGroupPlan" style="display: ''">			
    	 		<Table class=common>
    				<tr>
    					<td text-align: left colSpan=1>
    						<span id="spanPlanGrid"></span>
    					</td>
    				</tr>
    			</Table>
     		    <table>
    				<tr>
    					<td class=common>
    						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlnaRiskDetail);">
    					</td>
    					<td class=titleImg>险种详细信息</td>
    				</tr>
    			</table>
    			<div id="divPlnaRiskDetail">
        		    <Table class=common>
        				<tr>
        					<td text-align: left colSpan=1>
        						<span id="spanPlanRiskGrid"></span>
        					</td>
        				</tr>
        			</Table>
        			<center>
            			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();">
            			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();">
            			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();">
            			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">
        			</center>
        		</div>
    			<table>
    				<tr>
    					<td class=common>
    						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPlan1);">
    					</td>
    					<td class=titleImg>产品组合信息</td>
    				</tr>
    			</table>
    			<Div id="divGroupPlan1" style="display: ''" class="maxbox1">
    				<table class=common>
    					<tr class=common>
    						<td class=title>产品组合编码</td>
                            <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=PlanRiskCode id=PlanRiskCode ondblclick="showCodeList('PlanRiskCode',[this,PlanRiskCodeName],[0,1],null,null,null,1,'400');" onclick="showCodeList('PlanRiskCode',[this,PlanRiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="showCodeListKey('PlanRiskCode',[this,PlanRiskCodeName],[0,1],null,null,null,1,'400');"><input class="codename" name="PlanRiskCodeName" id="PlanRiskCodeName" elementtype=nacessary readonly></TD>                           
                            
                    <td class=title>封顶线</td>
					<td class=input>
						<input class="wid" class=common name=PeakLine id=PeakLine>
					</td>
				<!--</tr>
				<tr class=common>-->
					<td class=title>医疗费用限额</td>
					<td class=input>
						<input class="wid" class=common name=MaxMedFee id=MaxMedFee>
					</td>
    					    <!--
    					    <td class=title>份数</td>
    						<td class=input>    							
    							<input class=common name=Mult value="1">	
    						</td>
    						-->
    					   <!-- <td>
    						    <Div id="divPlanDeal" style="display: ''">
                					<table>
                			        	<tr>
                							<td>
                								<input type =button class=cssButton value="添加产品组合" onclick="addPlanRecord();">
                							</td>
                							<td>
                								<input type =button class=cssButton value="删除产品组合" onclick="deletePlanRecord();">
                							</td>
                						</tr>						
                					</table>
                				</Div>
    						</td>-->
    					</tr>					
    				</table>
    				
    			</Div>
    		</DIV>
    	</DIV><br><br>
        <a href="javascript:void(0);" class="button" onClick="addPlanRecord();">添加产品组合</a>
        <a href="javascript:void(0);" class="button" onClick="deletePlanRecord();">删除产品组合</a>
        
		<Div id="divHidden" style="display: 'none'">
			<table class=common>
				<tr class=common>
					
				</tr>
			</table>
		</Div>
		
		    <Div id="divnormalbtn" style="display: ''">
    			<input class=cssButton VALUE="险种信息"  TYPE=button onclick="grpFeeInput()">
    	  	    <input class=cssButton VALUE="保险计划定制"  TYPE=button onclick="grpRiskPlanInfo()">
    			<!--input type =button class=cssButton value="添加被保人" onclick="grpInsuInfo();"-->
    			<input class=cssButton VALUE="被保人清单"  TYPE=button onclick="grpInsuList()">
    			<input class=cssButton VALUE="车辆清单"  TYPE=button onclick="grpCarList()">
			</Div>
			<Div id="divapprovenormalbtn" style="display: 'none'">
    			<input class=cssButton VALUE="险种信息"  TYPE=button onclick="grpFeeInput()">
    			<input class=cssButton VALUE="保险计划定制"  TYPE=button onclick="grpRiskPlanInfo()">
    			<input class=cssButton VALUE="被保人清单"  TYPE=button onclick="grpInsuList()">
    			<input class=cssButton VALUE="车辆清单"  TYPE=button onclick="grpCarList()"> 
			    <!--<input class=cssButton VALUE="分单定制"  TYPE=button onclick="grpSubContInfo()">-->
			</Div>
			<Div id="divnormalquesbtn" style="display: 'none'">
			
			<input VALUE="录入完毕"   class=cssButton TYPE=button onclick="GrpInputConfirm(1);">
			<INPUT VALUE="记事本查看" class=cssButton  id="NotePadButton2" name="NotePadButton" TYPE=button onclick="showNotePad();">
			<input VALUE="团体问题件查询"  name="qryQuestionBtn2" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="团体问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<INPUT VALUE="影像件查询"    Class=cssButton TYPE=button onclick="showImage()">
		</Div>
		<Div id="divapprovebtn" style="display: 'none'">
			
			<INPUT class=cssButton VALUE="记事本查看"  id="NotePadButton4" TYPE=button onclick="showNotePad();">
			<input VALUE="团体复核通过" class=cssButton TYPE=button onclick="gmanuchk();">
			<input VALUE="团体问题件查询"  id="qryQuestionBtn4" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="团体问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<INPUT VALUE="影像件查询"    Class=cssButton TYPE=button onclick="showImage()">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn" style="display: 'none'">
			
			<INPUT class=cssButton VALUE="记事本查看"  id="NotePadButton" TYPE=button onclick="showNotePad();">
			<input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();">
			<input VALUE="问题件修改确认" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="团体问题件查询"   class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="团体问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn1" style="display: 'none'">
			
			<INPUT class=cssButton VALUE="记事本查看" id="NotePadButton13" TYPE=button onclick="showNotePad();">
			<input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();">
			<input VALUE="问题件修改确认" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="团体问题件回复"   class=cssButton TYPE=button onclick="GrpQuestQuery(1);">
			<INPUT VALUE="影像件查询"    Class=cssButton TYPE=button onclick="showImage()">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		    <Div id="divchangplanbtn" style="display: 'none'">
			<input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();">
			<input VALUE="团体问题件查询"  name="qryQuestionBtn23" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<!--<input VALUE="团体问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">-->
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
			</Div>

			<Div id="divuwupdatebtn" style="display: 'none'">
			
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divquerybtn" style="display: 'none'" align=right>
			
			<input VALUE="团体问题件查询"  name="qryQuestionBtn16" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<div id="autoMoveButton" style="display: none">
			<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('21');" class=cssButton>
			<input type="button" name="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+tLoadFlag+'&prtNo='+prtNo+'&checktype=2&scantype='+scantype" class=cssButton>
			<input VALUE="返  回" class=cssButton TYPE=button onclick="top.close();">
			<input type=hidden id="" name="autoMoveFlag">
			<input type=hidden id="" name="autoMoveValue">
			<input type=hidden id="" name="pagename" value="21">
			</div>
			<input type=hidden id="fmAction" name="fmAction">
			<input type=hidden id="" name="WorkFlowFlag">
					<input class=common type=hidden name=AgentCode1>
					<input class=common type=hidden name=MissionID>
					<input class=common type=hidden name=SubMissionID>
					<input class="common" type=hidden name=ProposalGrpContNo>

					<input class="common" type=hidden name=ActivityID>
					<input class="common" type=hidden name=NoType>

<!--
#########################################################################################
                                 以前页面里的控件，现在不需要了，故隐藏在此，不能删除
-->
						<input type="hidden" class=codeno name=OutPayFlag value="1" MAXLENGTH=1 ondblclick="return showCodeList('OutPayFlag', [this,OutPayFlagName],[0,1]);" onkeyup="return showCodeListKey('OutPayFlag', [this,OutPayFlagName],[0,1]);" verify="溢交保费处理方式|code:OutPayFlag&notnull">
						<input type="hidden" class=codeno name=EnterKind ondblclick="return showCodeList('enterkind',[this,EnterKindName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('enterkind',[this,EnterKindName],[0,1],null,null,null,1);">
						<input type="hidden" class=codeno name=AmntGrade ondblclick="return showCodeList('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);">
						<input type="hidden" name=Peoples3 class=common>
						<input type="hidden" name=OnWorkPeoples class=common>
						<input type="hidden" name=OffWorkPeoples class=common>
						<input type="hidden" name=OtherPeoples class=common>
						<input type="hidden" name=RelaPeoples class=common>
						<input type="hidden" name=RelaMatePeoples class=common>
						<input type="hidden" name=RelaYoungPeoples class=common>
						<input type="hidden" name=RelaOtherPeoples class=common>

<!--
#########################################################################################
-->
			<!--INPUT class=cssButton VALUE="导入被保人清单"  TYPE=button onclick=""-->
			<!--<input class=cssButton VALUE="进入保单险种信息"  TYPE=button onclick="grpRiskInfo()">-->
		</Div>
		<input type=hidden name=LoadFlag>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
