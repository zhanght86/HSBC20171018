<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
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
String tCardFlag ="";

try
{
	tLoadFlag =request.getParameter( "LoadFlag" );
	tGrpContNo =request.getParameter( "GrpContNo" );
	tCardFlag =request.getParameter("CardFlag");
	//loggerDebug("ContPolInput","团单保单号："+request.getParameter("prtNo"));
	
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
var ManageCom ="<%=tGI.ManageCom%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
var vContNo ="<%=request.getParameter("vContNo")%>";
var ScanFlag ="<%=request.getParameter("ScanFlag")%>";
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
var tuser= "<%=tGI.Operator%>"; 
var cardflag ="<%=tCardFlag%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPolInit.jsp"%>
<SCRIPT src="ContPolInput.js"></SCRIPT>
<%
//根据保单号判断是否是年金险
	ExeSQL tExeSQL=new ExeSQL();
	String tsql="select subtype From es_doc_main where doccode='"+request.getParameter("prtNo")+"'";
	SSRS tSSRS=new SSRS();
	String tvalue="1000";
	tSSRS=tExeSQL.execSQL(tsql);
	if(tSSRS.MaxRow>0)
	{
	 tvalue=tSSRS.GetText(1,1);
	 }
	String tflag="0";
	if(tvalue.equals("1000"))
	{
	 loggerDebug("ContPolInput","tflag==0");
	}else{
	tflag="1";
	}
 if(tflag.equals("0"))
 {
%>
<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
<%
 }else{
%>
<SCRIPT src="ProposalAutoMove2.js"></SCRIPT>
<%
}
%>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
loggerDebug("ContPolInput","扫描录入");
%>
<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
<%
}
%>
</head>
<body  onload="initForm();initElementtype();">
	<form action="./ContPolSave.jsp" method=post name=fm target="fraSubmit">
		<!-- 合同信息部分 GroupPolSave.jsp-->
		<DIV id=DivLCContButton STYLE="display:''">
			<table id="table1">
				<tr>
					<td>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
					</td>
					<td class="titleImg">结算批次信息</td>
				</tr>
			</table>
		</DIV>
		<Div id="divGroupPol1" style="display: ''">
			<table border="0" class=common>
				<tr class=common>
					<!--TD class=title>
						集体投保单号码
					</td>
					<td class=input>
						<input class="common" name=ProposalGrpContNo readonly TABINDEX="-1"  MAXLENGTH="40">
					</TD-->
					<td class="title">结算批次号</td>
					<td class="input">
						<input class="common" name=PrtNo elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="投保单号码|notnull&len<=14">
					</td>
				  <td class="title">呈报件号</td>
					<td class="input">
            <input class="common" name="ReportNo">
					</td>
				  
					<TD  class= title8>
            销售渠道
          </TD>
          <TD  class= input8>
            <Input class="code8" name=AgentType elementtype=nacessary verify="销售渠道|notnull" ondblclick="return showCodeList('AgentType',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this],null,null,null,null,1);">
          </TD>
					<!--
					<td class=title>管理机构</td>
					<td class=input>
						<input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<input class=codeno name=ManageCom verify="管理机构|code:comcode&notnull" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
					</td>
					-->
				</tr>
				<tr class=common>
								<TD  class= title8>
           二级渠道分类
          </TD>
          <TD  class= input8>
            <Input class="code8" name=SecondAgentType   ondblclick="return showCodeList('SecondAgentType',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('SecondAgentType',[this],null,null,null,null,1);">
          </TD> 
					<TD  class= title8>
            中介机构
          </TD>
          <TD  class= input8>
            <!--Input class="code8" name=AgentCom ondblclick="return showCodeList('AgentCom',[this],null,null, fm.all('ManageCom').value, 'ManageCom');" onkeyup="return showCodeListKey('AgentCom',[this],null,null, fm.all('ManageCom').value, 'ManageCom');"-->
            <!-------20060217----changing start--------->
            <Input class="code8" name=AgentCom onkeyup="return queryCom();" ondblclick="return queryCom();">
            <!-----------changing end------------------->
          </TD>
				  
					<td class=title>投保申请日期</td>
					<td class=input>
						<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=PolApplyDate verify="投保申请日期|notnull&DATE verifyorder="1"">
					</td>
				</tr> 
				<tr class=common>
           <td class=title>保单生效日期</td>
						<td class=input>
						<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkCValidate();" name=CValiDate verify="保单生效日期|notnull&DATE verifyorder="1"">
					</td>
						
					<!--td class=input>
						<input class="common" elementtype=nacessary dateFormat="short" onblur="checkCValidate();" name=CValiDate verify="保单生效日期|notnull&DATE veryfyorder="1"">
					</td-->
<!--					<td class="title">销售渠道</td>
					<td class="input">
						<input class=codeno name=AgentType verify="销售渠道|notnull" ondblclick="return showCodeList('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);"><input class=codename name=AgentTypeName readonly=true elementtype=nacessary>
					</td>
-->
			    <td CLASS="title">管理机构
    	    </td>
			    <td CLASS="input">
			      <Input name=ManageCom class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true>
			      <!--input NAME="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly"-->
    	    </td>
    	    <td class="title">财务收费日期</td>
					<td class="input">
            <input class="common" name="PayDate" readonly="readonly">
					</td>
					
	<!--				
    	    <td class="title">是否使用保全特殊算法</td>
					<td class="input">
            <Input class="code" name=EdorCalType CodeData="0|^Y|是^N|否" ondblclick="showCodeListEx('EdorCalType',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('EdorCalType',[this,''],[0,1]);" >          
					</td>					

				</tr>	
			<tr class=common>	
				  <td class="title">是否为赠送团单</td>
					<td class="input">
            <Input class="code" name=DonateContflag CodeData="0|^0|否^1|是" ondblclick="showCodeListEx('DonateContflag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('DonateContflag',[this,''],[0,1]);" >          
					</td>	
					 <td class="title">审批号码</td>
					<td class="input">
            <input class="common" name="ExamAndAppNo">
					</td>
			</tr>				
	 -->
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
<tr class=common>
		<td class="title">保全换人比例</td>
					<td class="input">
            <input class="common" name="EdorTransPercent" verify="保全换人比例|DECIMAL">
					</td>			  
</tr>
</table>
<table class="common">
				<tr class=common>
					<td class=title>日期追溯备注</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="BackDateRemark" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr>
</table>
<hr>
<table class="common">
  		<tr class="common">
			<td class="title">业务员代码
    	</td>
			<td class="input">
			  <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary onkeyup="return queryAgent();" ondblclick="return queryAgent();">
      </td>
			<td class="title">业务员姓名
    	</td>
			<td CLASS="input">
			  <input NAME="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary  verifyorder="1" >
      </td>
			<td CLASS="title">所属机构
    	</td>
			<td CLASS="input">
				<input NAME="AgentManageCom" readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="所属机构|code:station&amp;notnull"><input name="AppntManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
    	</td>
		</tr>
		<tr class="common">
			<td CLASS="title">所属分部
    	</td>
			<td CLASS="input">
			  <input NAME="BranchAttr"  verifyorder="1" VALUE CLASS="common" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
    	  <input NAME="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">&nbsp;
      </td>
      <td class="title">
        &nbsp;
      </td>
      <!-- 
      <td class="title">多业务员，请勾选
      </td>
      <td class="title">
        <input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();">
      </td>
      -->
      </table>
  <div id="DivAssiOper" style="display:'none'">
    <table class="common">
      <tr class="common">
			<td class="title">综合开拓专员编码
    	</td>
			<td class="input">
			  <input NAME="AgentCodeOper" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      </td>
			<td class="title">综合开拓专员姓名
    	</td>
			<td CLASS="input">
			  <input NAME="AgentNameOper" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      </td>
      <td class="title">综合开拓助理编码
    	</td>
			<td class="input">
			  <input NAME="AgentCodeAssi" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      </td>
      </tr>
      <tr class="common">
			<td class="title">综合开拓助理姓名
    	</td>
			<td CLASS="input">
			  <input NAME="AgentNameAssi" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      	</td>
      </tr>
   </table>
  </div>
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

<hr>
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol2);">
				</td>
				<td class=titleImg>
					结算单位资料（客户号 <input class=common name=GrpNo><input id="butGrpNoQuery" class=cssButton VALUE="查  询" TYPE=button onclick="showAppnt();"> ）(首次投保单位无需填写客户号)
				</td>
			</tr>
		</table>
		<Div id="divGroupPol2" style="display: ''">
			<table class=common>
				<tr>
					<td class=title>结算单位名称</td>
					<td class=input>
						<input class=common name=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60">
					</td>
                 </tr>

				<tr>
					<td class=title>单位地址编码</td>
					<td class=input>
						<input class="code" name="GrpAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0],'', '', '', true);">
					</td>
				</tr>
				<tr class=common>
					<td class=title>单位地址</td>
					<td class=input colspan="3">
						<input class="common3" name=GrpAddress  verify="单位地址|len<=60">
					</td>
					<td class=title>邮政编码</td>
					<td class=input>
						<input class=common name=GrpZipCode maxlength=6  verify="邮政编码|zipcode">
					</td>
				</tr>
				<tr class=common>
					<td class=title>保险联系人一</td>
				</tr>
				<tr class=common>
					<td class=title>姓  名</td>
					<td class=input>
						<input class=common name=LinkMan1  verify="保险联系人一姓名|len<=10">
					</td>
					<td class=title>联系电话</td>
					<td class=input>
						<input class=common name=Phone1  verify="保险联系人一联系电话|len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class=common name=E_Mail1 verify="保险联系人一E-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>部  门</td>
					<td class=input colspan=3>
						<input class=common3 name=Department1 verify="保险联系人一部门|len<=30">
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
</table>
</div>
  <Div id='divAppntUnit' style="display:'none'">			
  <Table class=common>
               <tr class=common>
					<td class=title>资产总额(元)</td>
					<td class=input>
						<input class=common name=Asset>
					</td>
					<td class=title>单位性质</td>
					<td class=input>
						<input class=codeno name=GrpNature  ondblclick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onkeyup="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);"><input class=codename name=GrpNatureName readonly=true >
					</td>
				</tr>
				<tr class=common>
					<td class=title>行业类别</td>
					<td class=input>
						<input class=codeno name=BusinessType  ondblclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onkeyup="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);"><input class=codename name=BusinessTypeName readonly=true elementtype=nacessary >
					</td>
					<td class=title>员工总数</td>
					<td class=input>
						<input class=common name=Peoples   >
					</td>
					<td class=title>单位传真</td>
					<td class=input>
						<input class=common name=Fax>
					</td>
				</tr>
					<tr>
						<td class=title>单位法人代表</td>
					<td class=input>
						<input class=common name=Corporation >
					</td>
					
					
				</tr>
				<tr class=common>
					<td class=title>保险联系人二</td>
				</tr>
				<tr class=common>
					<td class=title>姓  名</td>
					<td class=input>
						<input class=common name=LinkMan2 >
					</td>
					<td class=title>联系电话</td>
					<td class=input>
						<input class=common name=Phone2 >
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class=common name=E_Mail2 >
					</td>
				</tr>
				<tr class=common>
					<td class=title>部  门</td>
					<td class=input colspan=3>
						<input class=common3 name=Department2 >
					</td>
				</tr>
				<tr class=common>
					<td class=title>付款方式</td>
					<td class=input>
						<input class=codeno name=GetFlag  ondblclick="return showCodeList('paymode',[this,GetFlagName],[0,1]);" onkeyup="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName readonly=true >
					</td>
					<td class=title>开户银行</td>
					<td class=input>
            <Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 readonly  ondblclick="return queryBank();" onkeyup=" queryBank();" > 						
					</td>
					<td class=title>账  号</td>
					<td class=input>
						<input class=common name=BankAccNo >
					</td>

				</tr>
  </Table>
  </Div>
<!--
				<tr class=common>
					<td class=title>客户关注功能</td>
					<td class=input>
						<input class=codeno name=ClientCare  ondblclick="return showCodeList('clientcare',[this,ClientCareContent],[0,1]);" onkeyup="return showCodeListKey('clientcare',[this,ClientCareContent],[0,1]);"><input class=codename name=ClientCareContent readonly=true elementtype=nacessary >
					</td>
          <td class="title">投保单购买的产品与客户表述需求是否一致
          </td>
          <td class="title">
	        <Input class="code" name=ClientNeedJudge CodeData="0|^Y|是^N|否" ondblclick="showCodeListEx('ClientNeedJudge',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('ClientNeedJudge',[this,''],[0,1]);" elementtype=nacessary>             
          </td>
          <td class="title">如客户购买的保单与其需求明显不符,是否已向客户解释清楚
          </td>
          <td class="title">
	        <Input class="code" name=FundReason CodeData="0|^Y|是^N|否" ondblclick="showCodeListEx('FundReason',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('FundReason',[this,''],[0,1]);" elementtype=nacessary>          
          </td>	        
				<tr class=common>        
          <td class="title">保费是否来源于投保单位
          </td>
          <td class="title">
	        <Input class="code" name=FundJudge CodeData="0|^Y|是^N|否" ondblclick="showCodeListEx('FundJudge',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('FundJudge',[this,''],[0,1]);" elementtype=nacessary>          
          </td>
				</tr>			
-->					
       	<!--table class=common>
       	<tr class=common>
					<td class=title>资金不是投保人提供原因描述</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="FundReason" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr>
			</table-->			
<!--			
			<table class=common>
				<tr class=common>
					<td class=title>备注</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="Remark" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr>
				<tr class=common>
					<td class=title>特别约定</td>
				</tr>
				</table>
		<div id="divSpecInfo" style="display:''">
          <table class="common">
              <tr class="common">
                  <td style="text-align:left" colSpan="1">
                      <span id="spanSpecInfoGrid">
                      </span>
                  </td>
              </tr>
          </table>
-->          
    <table class="common">
    <!--
				<tr class=common>
					<td class=title>
						<textarea name="GrpSpec" cols="110" rows="7.5" class="common"></textarea>
					</td>
				</tr>
    -->				
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
		<Div id="divhiddeninfo" style="display: 'none'">
			<table class=common>
				<tr class=common>
					<td class=title>销售渠道</td>
					<td class=input>
						<!--<input class="readonly" readonly name=SaleChnl verify="销售渠道|code:SaleChnl&notnull">-->
						<input class=codeno  name=SaleChnl ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true elementtype=nacessary>
					</td>   
					<td class=title>社保登记证号码</td>
					<td class=input>
						<input class=common name=BankAccNo1 >
					</td>
					<td class="title">签报件</td>
					<td class="input">
            <input class="common" name="SignReportNo">
					</td>
					<td class="title">代理协议书号</td>
					<td class="input">
            <input class="common" name="AgentConferNo">
					</td>
					<td class="title">服务协议书号</td>
					<td class="input">
            <input class="common" name="ConferNo">
					</td>
					<td class=title>注册资本金</td>
					<td class=input>
						<input class=common name=RgtMoney verify="注册资本金|num&len<=17">
					</td>
					<td class=title>净资产收益率</td>
					<td class=input>
						<input class=common name=NetProfitRate verify="净资产收益率|num&len<=17">
					</td>
				<td class="title">
        星级业务员
      </td>
      <td class="input">
        <input class="codeno" readonly="readonly" name="starAgent"><input class="codename" name="starAgentName" readonly="readonly">
      </td>
				</tr>
				<tr class=common>
					<td class=title>主营业务</td>
					<td class=input>
						<input class=common name=MainBussiness verify="主营业务|len<=60">
					</td>
					
					<td class=title>机构分布区域</td>
					<td class=input>
						<input class=common name=ComAera verify="机构分布区域|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>职务</td>
					<td class=input>
						<input class=common name=HeadShip1 verify="保险联系人一职务|len<=30">
					</td>
					<td class=title>传真</td>
					<td class=input>
						<input class=common name=Fax1 verify="保险联系人一传真|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>职务</td>
					<td class=input>
						<input class=common name=HeadShip2 verify="保险联系人二职务|len<=30">
					</td>
					<td class=title>传真</td>
					<td class=input>
						<input class=common name=Fax2 verify="保险联系人二传真|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>币种</td>
					<td class=input>
						<input class=code8 name=Currency verify="币种|len<=2" ondblclick="showCodeList('currency',[this]);" onkeyup="showCodeListKey('currency',[this]);">
					</td>
				</tr>
			</table>
		</DIV>
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
				</td>
				<td class=titleImg>险种信息</td>
			</tr>
		</table>
		<Div id="divGroupPol3" style="display: ''">
			<Div id="divGroupPol5" style="display: 'none'">
				<table class=common>
					<tr class=common>
						<td class=title>集体合同号码</td>
						<td class=input>
							<input class="readonly" readonly name=GrpContNo >
						</td>
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
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol4);">
					</td>
					<td class=titleImg>险种信息</td>
				</tr>
			</table>
			<Div id="divGroupPol4" style="display: ''">
				<table class=common>
					<tr class=common>
						<td class=title>险种编码</td>
						<td class=input>
							<input class=codeno name=RiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary>							
							<!--Input class= "codeno" name=RiskCode  ondblclick="initRiskGrp(this,RiskCodeName);" onkeyup="initRiskGrp(this,RiskCodeName);" ><input class=codename name=RiskCodeName readonly=true elementtype=nacessary-->
						<!--Input name=riskgrade class='codeno' id="riskgrade" ondblclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" onkeyup="getcodedata2();return showCodeListEx('riskgrade',[this,cooperateName],[0,1]);"><input name=riskgradeName class=codename readonly=true><font color=red>*</font-->                                  
                </td>
						<td class=title>交费期间</td>
						<td class=input>
							<input class=codeno name=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>
						</td>
						<td class=title>分保标记</td>
						<td class=input>
							 <Input class="code" name=DistriFlag CodeData="0|^0|否^1|法定分保^2|商业分保" ondblclick="showCodeListEx('condition3',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition3',[this,''],[0,1]);"> 
			
						</td>
						
		      </tr>
		            <tr class=common>
		            <td class=title>手续费比率</td>
							<td CLASS="input">
								<input class=common NAME="ChargeFeeRate"  value="-1"    verify="手续费比率|value<=1||value=-1"  onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	    </td>
				    <td class=title>佣金比率</td>
							<td CLASS="input">
								<input class=common NAME="CommRate" value="-1"    verify="佣金比率|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	</td>
		            </tr>
		    </table>
		    <div id ="divFH" style="display:'none'">
		    <table class=common>
		      	<TD  class= title>分红标记</TD>
				<TD  class= input>
	               <Input class="code" name=StandbyFlag1  CodeData="0|^0|个别分红^1|标准分红" ondblclick="showCodeListEx('condition2',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition2',[this,''],[0,1]);"> 		
			      </td>
		    </table>
		    </div>
		    <div  id= "divExplain" style= "display: 'none'">
		    <table class=common>
		      <tr class=common>
		      
		       
			    <td class=title>红利分配比例</td>
							<td CLASS="input">
								<input class=common NAME="BonusRate" value="0.0"    verify="红利分配比例|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	</td>
	   <td class=title>固定收益比例</td>
							<td CLASS="input">
								<input class=common NAME="FixprofitRate" value="0.0"    verify="固定收益比例|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	</td>  
				    	<td></td>
		      </tr>
        	</table>
           </div>
<!--
						<td class=title>预计人数</td>
						<td class=input>
							<input class=common name=ExpPeoples  elementtype=nacessary>
						</td>
-->
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
			</Div>
		</DIV>
		<Div id="divHidden" style="display: 'none'">
			<table class=common>
				<tr class=common>
					<td class=title>封顶线</td>
					<td class=input>
						<input class=common name=PeakLine>
					</td>
				</tr>
				<tr class=common>
					<td class=title>医疗费用限额</td>
					<td class=input>
						<input class=common name=MaxMedFee>
					</td>
				</tr>
			</table>
		</Div>
		<hr/>
		<Div id="divnormalbtn" style="display: ''">
			
	<input class=cssButton VALUE="保险计划定制"  TYPE=button onclick="grpRiskPlanInfo()">
			<!--input type =button class=cssButton value="添加被保人" onclick="grpInsuInfo();"-->
			
			<input class=cssButton VALUE="被保人清单"  TYPE=button onclick="grpInsuList()">
			<input class=cssButton VALUE="添加被保人"  TYPE=button onclick="getintopersoninsured();">
		<input class=cssButton VALUE="分单定制"  disabled=true TYPE=button onclick="grpSubContInfo()">
		<input value="归属规则定义" disabled=true class=cssButton type=button onclick="ascriptDefine();">
		<input value="管理费定义" class=cssButton type=button onclick="feeDefine();">
		<!--input value="缴费规则定义" class=cssButton type=button onclick="payDefine();"-->
		<!--input class=cssButton VALUE="险种信息"  TYPE=button onclick="grpFeeInput()"-->
		<!--input value="特 约 录 入" class=cssButton type=button onclick="showSpecInfo();"-->
		<input class=cssButton VALUE="理赔责任控制" TYPE=button onclick="ctrlclaimduty();">

		<input value="该团单管理费"  class=cssButton type=button onclick="showRealFee();">

		<!--input class=cssButton VALUE="管理费查询" TYPE=button onclick="ManageFeeCal();"-->
		<!--INPUT class=cssButton VALUE="年龄分布"  TYPE=button onclick="grpPersonAge()"-->
			</Div>
			<Div id="divapprovenormalbtn" style="display: 'none'">
			<!--<input class=cssButton VALUE="险种信息"  TYPE=button onclick="grpFeeInput()">-->
			<!--<input class=cssButton VALUE="保险计划定制"  TYPE=button onclick="grpRiskPlanInfo()">-->
			<!--input value="归属规则定义" class=cssButton type=button onclick="ascriptDefine();"-->
			
			<input class=cssButton VALUE="被保人清单"  TYPE=button onclick="grpInsuList()">
			
			<input class=cssButton VALUE="保险计划查看" name=buttonContPlan  TYPE=button onclick="grpRiskPlanInfo()">
			<input class=cssButton VALUE="分单定制"  disabled=true TYPE=button onclick="grpSubContInfo()">
		<input value="归属规则定义" class=cssButton type=button disabled=true onclick="ascriptDefine();">
		<input value="管理费定义" class=cssButton name=buttonManageFee type=button onclick="feeDefine();">
		<!--input value="缴费规则定义" class=cssButton type=button onclick="payDefine();"-->
			<!--input class=cssButton VALUE="险种信息查看"  TYPE=button onclick="grpFeeInput()"-->
			<input class=cssButton VALUE="理赔责任控制" name=buttonClaimDutyCtrl TYPE=button onclick="ctrlclaimduty();">
			<INPUT VALUE ="团体扫描件查询" Class="cssButton" name=buttonScanDoc TYPE=button onclick="ScanQuery2();"> 
			<!--input class=cssButton VALUE="管理费查询" TYPE=button onclick="ManageFeeCal();"-->
			
			<input value="该团单管理费" id="feewatch"  name=buttonShowManageFee class=cssButton type=button onclick="showRealFee();">
			<!--<input class=cssButton VALUE="分单定制"  TYPE=button onclick="grpSubContInfo()">-->
			</Div>
			<Div id="divnopertoperbtn" style="display: 'none'">
			<!--input class=cssButton VALUE="险种信息"  TYPE=button onclick="grpFeeInput()"-->
			<!--<input class=cssButton VALUE="保险计划定制"  TYPE=button onclick="grpRiskPlanInfo()">-->
			<!--input class=cssButton VALUE="被保人清单"  TYPE=button onclick="grpInsuList()"-->
			<input class=cssButton VALUE="补名单"  TYPE=button onclick="grpfilllist()">
			<input class=cssButton VALUE="保险计划查看"  TYPE=button onclick="grpRiskPlanInfo()">
			<INPUT VALUE="批量补名单" class= cssButton TYPE=button onclick="AlladdNameClick();">
			<!--<input class=cssButton VALUE="分单定制"  TYPE=button onclick="grpSubContInfo()">-->
			</Div>
			<Div id="divnormalquesbtn" style="display: 'none'" align=right>
			<hr/>
			<input VALUE="录入完毕"   class=cssButton TYPE=button onclick="GrpInputConfirm(1);">
			<INPUT VALUE="记事本查看" class=cssButton TYPE=button onclick="showNotePad();">
			<input VALUE="问题件查询"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
		</Div>
		<Div id="divapprovebtn" style="display: 'none'" align=right>
			<hr/>
			<INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();">
			<input VALUE="复核通过" class=cssButton TYPE=button onclick="gmanuchk();">
			<input VALUE="问题件查询"   class=cssButton name=buttonIssueQuery TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn" style="display: 'none'" align=right>
			<hr/>
			<INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();">
			<!--input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();"-->
			<input VALUE="问题件修改确认" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="问题件查询"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn1" style="display: 'none'" align=right>
			<hr/>
			<INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();">
			<!--input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();"-->
			<input VALUE="问题件修改确认" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="问题件回复"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<!--input VALUE="团体问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();"-->
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divchangplanbtn" style="display: 'none'" align=right>
			<!--input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();"-->
			<input VALUE="问题件查询"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
			</Div>

			<Div id="divuwupdatebtn" style="display: 'none'" align=left>
			<hr/>
			<input VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divquerybtn" style="display: 'none'" align=right>
			<hr/>
			<input VALUE="问题件查询"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="返  回" class=cssButton TYPE=button onclick="parent.close();">
		</Div>
		<div id="autoMoveButton" style="display: none">
			<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('21');" class=cssButton>
			<input type="button" name="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+tLoadFlag+'&prtNo='+prtNo+'&checktype=2&scantype='+scantype" class=cssButton>
			<input VALUE="返  回" class=cssButton TYPE=button onclick="top.close();">
			<input type=hidden id="" name="autoMoveFlag">
			<%
          String today=PubFun.getCurrentDate();
          
          %>
			<input type=hidden id="" name="sysdate" value="<%=today%>">
			<input type=hidden id="" name="autoMoveValue">
			<input type=hidden id="" name="pagename" value="21">
			</div>
			<input type=hidden id="fmAction" name="fmAction">
			<input type=hidden id="" name="WorkFlowFlag">
					<input class=common type=hidden name=AgentCode1>
					<input class=common type=hidden name=BankCodeName>
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
						<input type="hidden" name=SpecFlag class=common>

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
