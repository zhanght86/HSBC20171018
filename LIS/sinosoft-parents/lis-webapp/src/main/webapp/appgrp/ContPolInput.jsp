<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.service.*"%>
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
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPolInit.jsp"%>
<SCRIPT src="ContPolInput.js"></SCRIPT>
<%
	//根据保单号判断是否是年金险
	String tvalue="1000";
	String tsql="select subtype From es_doc_main where doccode='"+request.getParameter("prtNo")+"'";

	TransferData sTransferData=new TransferData();
    sTransferData.setNameAndValue("SQL", tsql);
    VData sVData = new VData();
	sVData.add(sTransferData);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
	{
		String subtype = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
		if(subtype!=null && !"".equals(subtype))
		{
			tvalue = subtype ;
		}
		loggerDebug("ContPolInput","=========tvalue="+subtype+"=========tvalue="+tvalue);
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
	<form action="./ContPolSave.jsp" method=post name=fm id="fm" target="fraSubmit">
		<!-- 合同信息部分 GroupPolSave.jsp-->
	<DIV id="DivLCContButton" STYLE="display:''">
		<table id="table1">
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
				</td>
				<td class="titleImg">集体合同信息</td>
			</tr>
		</table>
	</DIV>
	
	<Div id="divGroupPol1" style="display: ''"><div class="maxbox">
		<table border="0" class=common>
			<tr class=common>
				<td class="title">投保单号码</td>
				<td class="input">
					<input class="common wid" name=PrtNo id="PrtNo" elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="投保单号码|notnull&len<=14">
				</td>
				<td class="title">呈报件号</td>
				<td class="input">
            		<input class="common wid" name="ReportNo" id="ReportNo">
				</td>
				<TD  class= title>销售渠道</TD>
          		<TD  class= input>
            		<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center"  class="code" name=AgentType id="AgentType" elementtype=nacessary verify="销售渠道|notnull" onMouseDown="return showCodeList('AgentType',[this],null,null,null,null,1);" onDblClick="return showCodeList('AgentType',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('AgentType',[this],null,null,null,null,1);">
          		</TD>
			</tr>
			<tr class=common>
				<TD  class= title>二级渠道分类</TD>
          		<TD  class= input>
            		<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center"  class="code" name=SecondAgentType id="SecondAgentType" onMouseDown="return showCodeList('SecondAgentType',[this],null,null,null,null,1);"   ondblclick="return showCodeList('SecondAgentType',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('SecondAgentType',[this],null,null,null,null,1);">
          		</TD> 
				<TD  class= title>中介机构</TD>
          		<TD  class= input>
            		<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name=AgentCom id="AgentCom" onMouseDown="return showCodeList('AgentCom',[this],null,null, document.all('ManageCom').value, 'ManageCom');" onDblClick="return showCodeList('AgentCom',[this],null,null, document.all('ManageCom').value, 'ManageCom');" onKeyUp="return showCodeListKey('AgentCom',[this],null,null, document.all('ManageCom').value, 'ManageCom');">
            <!-- <Input class="code" name=AgentCom onkeyup="return queryCom();" ondblclick="return queryCom();"> -->
          		</TD>
				<td class=title>投保申请日期</td>
				<td class=input>
					<Input class="coolDatePicker" elementtype=nacessary onClick="laydate({elem: '#PolApplyDate'});" verify="投保申请日期|notnull&DATE verifyorder="1"" dateFormat="short" name=PolApplyDate id="PolApplyDate" onBlur="checkapplydate();"><span class="icon"><a onClick="laydate({elem: '#PolApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</td>
			</tr> 
			<tr class=common>
           		<td class=title>保单生效日期</td>
					<td class=input>
						<Input class="coolDatePicker" elementtype=nacessary onClick="laydate({elem: '#CValiDate'});" verify="保单生效日期|notnull&DATE verifyorder="1"" dateFormat="short" name=CValiDate id="CValiDate" onBlur="checkapplydate();"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>
			    <td CLASS="title">管理机构</td>
			    <td CLASS="input">
			      	<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"  name=ManageCom id="ManageCom" class='codeno' id="ManageCom" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName id="ManageComName" class=codename readonly=true>
    	    	</td>
    	    	<td class="title">财务收费日期</td>
				<td class="input">
            		<input class="common wid" name="PayDate" id="PayDate" readonly>
				</td>
			</tr>
			<tr class=common style="display:none">
				<td class="title">保全换人比例</td>
				<td class="input">
            		<input class="common wid" name="EdorTransPercent" id="EdorTransPercent" verify="保全换人比例|DECIMAL">
				</td>		
					<!-- wangxw 0100925 续保 -->
				<td class=title>续保原保单号码</td>
				<td class=input>
					<input NAME=OldGrpcontno id="OldGrpcontno" class="common wid" verify="续保原保单号码|num">
					<input NAME="ReNewTime" id="ReNewTime" type="hidden" value="">
					<input NAME="OldGrpcontno1" id="OldGrpcontno1" type="hidden" value="">
				</td>	  
				        					
			</tr>
		</table>
	</Div>
	</div>
		<div id="DivContPlanFlag" style="display:none">
    		<table class="common">
    			<tr class=common>
				<td class="title">产品组合佣金比例标志</td>
				<td class="input">
            		<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ContPlanFlag id="ContPlanFlag"  readonly  readonly = "true"  CodeData="0|^Y|组合产品佣金比例^N|非组合产品佣金比例" ondblclick="showCodeListEx('ContPlanFlag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('ContPlanFlag',[this,''],[0,1]);" >
				</td>	
     	      	<TD  class= title>&nbsp;</TD>
          		<TD  class= input>&nbsp;</TD>
            	<TD  class= title>&nbsp;</TD>
          		<TD  class= input>&nbsp;</TD>
     		</tr>    
   		</table>
  	</div>
  
 	<div id="DivContPlan" style="display:none">
    	<table class="common">
    		<tr class=common>
				<td class="title">佣金比例值</td>
				<td class="input">
			  		<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code"  readonly  readonly = "true" value  = "" name=FYCRate id="FYCRate" CodeData="0|^15|15%^16|16%^17|17%^18|18%^19|19%^20|20%" ondblclick="showCodeListEx('FYCRate',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('FYCRate',[this,''],[0,1]);" >
     	    	</td>
     	      	<TD  class= title>&nbsp;</TD>
          		<TD  class= input>&nbsp;</TD>
            	<TD  class= title>&nbsp;</TD>
          		<TD  class= input>&nbsp;</TD>
     		</tr>    
   		</table>
  	</div>
  
	<table class="common">
		<tr class=common>
			<td class=title>日期追溯备注</td></tr>
            <tr class=common>
			<td colspan="6" style="padding-left:16px"><textarea name="BackDateRemark" cols="227" rows="4" class="common"></textarea></td>
		</tr>
	</table>
<hr class="line">
<div class="maxbox1" style="border:none">
<table class="common">
  	<tr class="common">
  		<td class="title">业务员代码</td>
		<td class="input">
			<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" NAME="AgentCode" id="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary onKeyUp="return queryAgent();" onMouseDown="return queryAgent();" onDblClick="return queryAgent();">
      	</td>
		<td class="title">业务员姓名</td>
		<td CLASS="input">
			<input NAME="AgentName" id="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary  verifyorder="1" >
      	</td>
		<td CLASS="title">所属机构</td>
		<td CLASS="input">
			<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"  NAME="AgentManageCom" id="AgentManageCom" readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" onMouseDown="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onDblClick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onKeyUp="return showCodeListKey('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="所属机构|code:station&amp;notnull"><input name="AppntManageComName" id="AppntManageComName" elementtype=nacessary CLASS="codename" readonly>
    	</td>
	</tr>
	<tr class="common">
		<td CLASS="title">所属分部</td>
		<td CLASS="input">
			<input NAME="BranchAttr" id="BranchAttr"  verifyorder="1" VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
    	  	<input NAME="AgentGroup" id="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">&nbsp;</td>
      <td class="title">&nbsp;</td>
</table>
<div id="DivAssiOper" style="display:none">
    <table class="common">
      	<tr class="common">
			<td class="title">综合开拓专员编码</td>
			<td class="input">
			  	<input NAME="AgentCodeOper" id="AgentCodeOper" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary>
      		</td>
			<td class="title">综合开拓专员姓名</td>
			<td CLASS="input">
			  <input NAME="AgentNameOper" id="AgentNameOper" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary>
      		</td>
      		<td class="title">综合开拓助理编码</td>
			<td class="input">
			  <input NAME="AgentCodeAssi" id="AgentCodeAssi" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary>
      		</td>
      	</tr>
      	<tr class="common">
			<td class="title">综合开拓助理姓名</td>
			<td CLASS="input">
			  <input NAME="AgentNameAssi" id="AgentNameAssi" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary>
      		</td>
      	</tr>
   	</table>
</div>
</div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                   多业务员MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <div id="DivMultiAgent" style="display:none">
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

<hr class="line">
<table>
	<tr>
		<td class=common>
    		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
		</td>
		<td class=titleImg>
			投保单位资料（客户号 <input class=common name=GrpNo id="GrpNo"><input id="butGrpNoQuery" class=cssButton VALUE="查  询" TYPE=button onClick="showAppnt();"> ）(首次投保单位无需填写客户号)
		</td>
	</tr>
</table>

<Div  id= "divFCDay" style= "display: ''">
<Div id="divGroupPol2" style="display: ''"><div class="maxbox">
	<table class=common>
		<tr>
			<td class=title>单位名称</td>
			<td class=input>
				<input class="common wid" name=GrpName id="GrpName" elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60">
			</td>
			<td class=title>资产总额(元)</td>
				<td class=input>
					<input class="common wid" name=Asset id="Asset">
				</td>
			<td class=title>单位性质</td>
				<td class=input>
					<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=GrpNature id="GrpNature" verify="单位性质|code:grpNature&len<=10" onMouseDown="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onDblClick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onKeyUp="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);"><input class=codename name=GrpNatureName id="GrpNatureName" readonly=true >
			</td>
		</tr>
		<tr class=common>
			<td class=title>行业类别</td>
			<td class=input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=BusinessType id="BusinessType" verify="行业类别|notnull&code:BusinessType&len<=20" onMouseDown="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onDblClick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onKeyUp="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);"><input class=codename name=BusinessTypeName id="BusinessTypeName" readonly=true elementtype=nacessary >
			</td>
			<td class=title>员工总数</td>
			<td class=input>
				<input class="common wid" name=Peoples id="Peoples"  verify="单位总人数|int">
			</td>
			<td class=title>单位传真</td>
			<td class=input>
				<input class="common wid" name=Fax id="Fax">
			</td>
		</tr>
		<tr>
			<td class=title>单位法人代表</td>
			<td class=input>
				<input class="common wid" name=Corporation id="Corporation" verify="单位法人代表|len<=20">
			</td>
            <td class=title>单位地址编码</td>
			<td class=input>
				<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name="GrpAddressNo" id="GrpAddressNo" onMouseDown="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onKeyUp="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0],'', '', '', true);">
			</td>
            <td class=title>单位地址</td>
			<td class=input>
				<input class="wid" class="common3" name=GrpAddress id="GrpAddress" verify="单位地址|len<=60">
			</td>
		</tr>
		
		<tr class=common>
			
			<td class=title>邮政编码</td>
			<td class=input>
				<input class="common wid" name=GrpZipCode id="GrpZipCode" maxlength=6  verify="邮政编码|zipcode">
			</td>
            <td class=title>保险联系人一</td>
		</tr>
		
		<tr class=common>
			<td class=title>姓  名</td>
			<td class=input>
				<input class="common wid" name=LinkMan1 id="LinkMan1" verify="保险联系人一姓名|len<=10">
			</td>
			<td class=title>联系电话</td>
			<td class=input>
				<input class="common wid" name=Phone1 id="Phone1" verify="保险联系人一联系电话|len<=30">
			</td>
			<td class=title>E-MAIL</td>
			<td class=input>
				<input class="common wid" name=E_Mail1 id="E_Mail1" verify="保险联系人一E-MAIL|len<=60&Email">
			</td>
		</tr>
		<tr class=common>
			<td class=title>部  门</td>
			<td class=input>
				<input class="wid" class=common3 name=Department1 id="Department1" verify="保险联系人一部门|len<=30">
			</td><td class=title>保险联系人二</td>
		</tr>
		
		<tr class=common>
			<td class=title>姓  名</td>
			<td class=input>
				<input class="common wid" name=LinkMan2 id="LinkMan2" verify="保险联系人二姓名|len<=10">
			</td>
			<td class=title>联系电话</td>
			<td class=input>
				<input class="common wid" name=Phone2 id="Phone2" verify="保险联系人二联系电话|len<=30">
			</td>
			<td class=title>E-MAIL</td>
			<td class=input>
				<input class="common wid" name=E_Mail2 id="E_Mail2" verify="保险联系人二E-MAIL|len<=60&Email">
			</td>
		</tr>
		<tr class=common>
			<td class=title>部  门</td>
			<td class=input>
				<input class="wid" class=common3 name=Department2 id="Department2" verify="保险联系人二部门|len<=30">
			</td>
		</tr>
		<tr class=common>
			<td class=title>付款方式</td>
			<td class=input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=GetFlag id="GetFlag" onMouseDown="return showCodeList('paymode',[this,GetFlagName],[0,1]);"  ondblclick="return showCodeList('paymode',[this,GetFlagName],[0,1]);" onKeyUp="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName id="GetFlagName" readonly=true >
			</td>
			<td class=title>开户银行</td>
			<td class=input>
    			<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" NAME=BankCode id="BankCode" VALUE="" CLASS="code" MAXLENGTH=20 readonly onMouseDown="return queryBank();"  ondblclick="return queryBank();" onKeyUp=" queryBank();" > 						
			</td>
			<td class=title>账  号</td>
			<td class=input>
				<input class="common wid" name=BankAccNo id="BankAccNo" verify="帐号|len>=6&len<=40">
			</td>
		</tr>				
	</table>			
	<table class=common>
		<tr class=common>
			<td class=title>备注</td></tr>
            <tr class=common>
			<td colspan="6" style="padding-left:16px">
				<textarea name="Remark" cols="227" rows="4" class="common"></textarea>
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
    </div>
    <table class="common">
		<tr class=common>
			<td colspan="6" style="padding-left:16px">
			
				<textarea name="GrpSpec" cols="227" rows="4" class="common"></textarea>
			</td>
		</tr>
		<input type=hidden name=EmployeeRate id="EmployeeRate" verify="雇员自付比例|num&len<=5">
		<input type=hidden name=FamilyRate id="FamilyRate" verify="家属自付比例|num&len<=80">
	</table>
</Div>
</Div>
</div>
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
	<input class=cssButton VALUE="子公司录入"  TYPE=button onClick="InputAccountInfo()">
	<!--INPUT class=cssButton VALUE="查 询"  TYPE=button onclick="queryClick()">
	<input class=cssButton VALUE="修 改"  TYPE=button onclick="updateClick()">
	<input class=cssButton VALUE="删 除"  TYPE=button onclick="deleteClick()">
	<input class=cssButton VALUE="保 存"  TYPE=button onclick="submitForm();"-->
	<%@include file="OperateButton.jsp"%>
	<%@include file="../common/jsp/InputButton.jsp"%>
</DIV>
<Div id="divhiddeninfo" style="display: none">
	<div class="maxbox" style="border:none">
	<table class=common>
		<tr class=common>
			<td class=title>销售渠道</td>
			<td class=input>
				<!--<input class="readonly" readonly name=SaleChnl verify="销售渠道|code:SaleChnl&notnull">-->
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno  name=SaleChnl id="SaleChnl" onMouseDown="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onDblClick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onKeyUp="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName id="SaleChnlName" readonly=true elementtype=nacessary>
			</td>   
			<td class=title>社保登记证号码</td>
			<td class=input>
				<input class="common wid" name=BankAccNo1 id="BankAccNo1">
			</td>
			<td class="title">签报件</td>
			<td class="input">
    			<input class="common wid" name="SignReportNo" id="SignReportNo">
			</td>
		</tr>
		<tr class=common>
			<td class="title">代理协议书号</td>
			<td class="input">
    			<input class="common wid" name="AgentConferNo" id="AgentConferNo">
			</td>
			<td class="title">服务协议书号</td>
			<td class="input">
    			<input class="common wid" name="ConferNo" id="ConferNo">
			</td>
			<td class=title>注册资本金</td>
			<td class=input>
				<input class="common wid" name=RgtMoney id="RgtMoney" verify="注册资本金|num&len<=17">
			</td>
		</tr>
		<tr class=common>
			<td class=title>净资产收益率</td>
			<td class=input>
				<input class="common wid" name=NetProfitRate id="NetProfitRate" verify="净资产收益率|num&len<=17">
			</td>
			<td class="title">星级业务员</td>
		    <td class="input">
		        <input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" readonly name="starAgent" id="starAgent"><input class="codename" name="starAgentName" id="starAgentName" readonly>
		    </td>
			<td class=title>主营业务</td>
			<td class=input>
				<input class="common wid" name=MainBussiness id="MainBussiness" verify="主营业务|len<=60">
			</td>
		</tr>
		<tr class=common>	
			<td class=title>机构分布区域</td>
			<td class=input>
				<input class="common wid" name=ComAera id="ComAera" verify="机构分布区域|len<=30">
			</td>
			<td class=title>职务</td>
			<td class=input>
				<input class="common wid" name=HeadShip1 id="HeadShip1" verify="保险联系人一职务|len<=30">
			</td>
			<td class=title>传真</td>
			<td class=input>
				<input class="common wid" name=Fax1 id="Fax1" verify="保险联系人一传真|len<=30">
			</td>
		</tr>
		<tr class=common>
			<td class=title>职务</td>
			<td class=input>
				<input class="common wid" name=HeadShip2 id="HeadShip2" verify="保险联系人二职务|len<=30">
			</td>
			<td class=title>传真</td>
			<td class=input>
				<input class="common wid" name=Fax2 id="Fax2" verify="保险联系人二传真|len<=30">
			</td>
			<td class=title>币种</td>
			<td class=input>
				<input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=Currency id="Currency" verify="币种|len<=2" onMouseDown="showCodeList('currency',[this]);" onDblClick="showCodeList('currency',[this]);" onKeyUp="showCodeListKey('currency',[this]);">
			</td>
		</tr>
	</table>
	</div>
</Div>

<table>
	<tr>
		<td class=common>
			<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
		</td>
		<td class=titleImg>集体保单险种信息</td>
	</tr>
</table>
<DIV id="divGroupPol3" style="display: ''">
	<Div id="divGroupPol5" style="display: 'none'" class="maxbox1">
		<table class=common>
			<tr class=common>
				<td class=title>集体合同号码</td>
				<td class=input>
					<input class="readonly wid" readonly name=GrpContNo id="GrpContNo">
				</td><td class=title></td><td class=input></td><td class=title></td><td class=input></td>
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
	<Div id="divGroupPol4" style="display: ''" class="maxbox1">
		<table class=common>
			<tr class=common>
				<td class=title>险种编码</td>
				<td class=input>
					<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode" onMouseDown="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onDblClick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onKeyUp="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true elementtype=nacessary>
				</td>
				<td class=title>交费期间</td>
				<td class=input>
					<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PayIntv id="PayIntv" onMouseDown="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onDblClick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onKeyUp="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');"><input class=codename name=PayIntvName id="PayIntvName" readonly=true elementtype=nacessary>
				</td>
				<td class=title>币种</td>
				<td class=input>
					 <input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCurrency id="RiskCurrency" verify="币种|code:BusinessType&len<=2" onMouseDown="return showCodeList('currency',[this,RiskCurrencyName],[0,1],null,null,null,null);" onDblClick="return showCodeList('currency',[this,RiskCurrencyName],[0,1],null,null,null,null);" onKeyUp="return showCodeListKey('currency',[this,RiskCurrencyName],[0,1],null,null,null,null);"><input class=codename name=RiskCurrencyName id="RiskCurrencyName" readonly=true elementtype=nacessary >
				</td>
			</tr>
            <tr class=common>
            	<td class=title>手续费比率</td>
				<td CLASS="input">
					<input class="common wid" NAME="ChargeFeeRate" id="ChargeFeeRate"  value="-1"    verify="手续费比率|value<=1||value=-1"  onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
		    	</td>
		    	<td class=title>佣金比率</td>
				<td CLASS="input">
					<input class="common wid" NAME="CommRate" id="CommRate" value="-1"    verify="佣金比率|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
		    	</td>
				<td class=title>分保标记</td>
				<td class=input>
					<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name=DistriFlag id="DistriFlag" CodeData="0|^0|否^1|法定分保^2|商业分保"  onMouseDown="showCodeListEx('condition3',[this,''],[0,1]);" onDblClick="showCodeListEx('condition3',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('condition3',[this,''],[0,1]);"> 
				</td>
		    </tr>
		</table>
		<div id ="divFH" style="display:none">
		    <table class=common>
		      	<TD  class= title>分红标记</TD>
				<TD  class= input>
	               <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=StandbyFlag1  id="StandbyFlag1" CodeData="0|^0|个别分红^1|标准分红" onMouseDown="showCodeListEx('condition2',[this,''],[0,1]);" onDblClick="showCodeListEx('condition2',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('condition2',[this,''],[0,1]);"> 		
			    </td>
			    <td class= title></td>
			    <td class= input></td>
			    <td class= title></td>
			    <td class= input></td>
		    </table>
		</div>
		<div  id= "divExplain" style= "display: none">
		    <table class=common>
		      	<tr class=common>
		      		<td class=title>红利分配比例</td>
					<td CLASS="input">
						<input class="common wid" NAME="BonusRate" id="BonusRate" value="0.0"    verify="红利分配比例|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    </td>
	   				<td class=title>固定收益比例</td>
					<td CLASS="input">
						<input class="common wid" NAME="FixprofitRate" id="FixprofitRate" value="0.0"    verify="固定收益比例|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    </td>
				    <td class= title></td>
			    	<td class= input></td>  
		      	</tr>
        	</table>
        </div>
        <div id ="divKF" style="display:none">
		    <table class=common>
		      	<TD  class= title>业务类型</TD>
				<TD  class= input>
	               <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=StandbyFlag4 id="StandbyFlag4"  CodeData="0|^0|新单^1|续保单" onMouseDown="showCodeListEx('condition4',[this,''],[0,1]);" onDblClick="showCodeListEx('condition4',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('condition4',[this,''],[0,1]);"> 		
			    </td>
			    <td class= title></td>
			    <td class= input></td>
			    <td class= title></td>
			    <td class= input></td>
		    </table>
		</div>
		<div id="divRiskDeal" style="display: ''">
			<table>
	        	<tr>
					<td><input type =button class=cssButton value="添加险种" onClick="addRecord();"></td>
					<td><input type =button class=cssButton value="删除险种" onClick="deleteRecord();"></td>
				</tr>
			</table>
		<div>
		<Div id="divHidden" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>封顶线</td>
					<td class=input>
						<input class="common wid" name=PeakLine id="PeakLine">
					</td>
					<td class=title>医疗费用限额</td>
					<td class=input>
						<input class="common wid" name=MaxMedFee id="MaxMedFee">
					</td>
					<td class= title></td>
				    <td class= input></td>
				</tr>
			</table>
		</Div>
	</Div>
</DIV>
<Div id="divnormalbtn" style="display: none">
	<hr class="line">
	<input class=cssButton VALUE="保险计划定制"  TYPE=button onClick="grpRiskPlanInfo()">
	<input class=cssButton VALUE="被保人清单"  TYPE=button onClick="grpInsuList()">
	<input class=cssButton VALUE="添加被保人"  TYPE=button onClick="getintopersoninsured();">
	<input class=cssButton VALUE="分单定制"  disabled=true TYPE=button onClick="grpSubContInfo()">
	<input value="归属规则定义" disabled=true class=cssButton type=button onClick="ascriptDefine();">
	<input value="管理费定义" class=cssButton type=button onClick="feeDefine();">
	<input class=cssButton VALUE="理赔责任控制" TYPE=button onClick="ctrlclaimduty();">
	<input value="该团单管理费"  class=cssButton type=button onClick="showRealFee();">
</Div>
<Div id="divapprovenormalbtn" style="display: none">
	<hr class="line">
	<input class=cssButton VALUE="被保人清单"  TYPE=button onClick="grpInsuList()">
	<input class=cssButton VALUE="保险计划查看" name=buttonContPlan id="buttonContPlan"  TYPE=button onClick="grpRiskPlanInfo()">
	<input class=cssButton VALUE="分单定制"  disabled=true TYPE=button onClick="grpSubContInfo()">
	<input value="归属规则定义" class=cssButton type=button disabled=true onClick="ascriptDefine();">
	<input value="管理费定义" class=cssButton name=buttonManageFee id="buttonManageFee" type=button onClick="feeDefine();">
	<input class=cssButton VALUE="理赔责任控制" name=buttonClaimDutyCtrl id="buttonClaimDutyCtrl" TYPE=button onClick="ctrlclaimduty();">
	<INPUT VALUE ="团体扫描件查询" Class="cssButton" name=buttonScanDoc id="buttonScanDoc" TYPE=button onClick="ScanQuery2();"> 
	<input value="该团单管理费" id="feewatch"  name=buttonShowManageFee id="buttonShowManageFee" class=cssButton type=button onclick="showRealFee();">
</Div>
<Div id="divnopertoperbtn" style="display: none">
	<hr class="line">
	<input class=cssButton VALUE="补名单"  TYPE=button onClick="grpfilllist()">
	<input class=cssButton VALUE="保险计划查看"  TYPE=button onClick="grpRiskPlanInfo()">
	<INPUT VALUE="批量补名单" class= cssButton TYPE=button onClick="AlladdNameClick();">
</Div>
<Div id="divnormalquesbtn" style="display: none" align=right>
	<hr class="line">
	<input VALUE="录入完毕"   class=cssButton TYPE=button onClick="GrpInputConfirm(1);">
	<INPUT VALUE="记事本查看" class=cssButton TYPE=button onClick="showNotePad();">
	<input VALUE="团体问题件查询"   class=cssButton TYPE=button onClick="GrpQuestQuery();">
	<input VALUE="团体问题件录入"   class=cssButton TYPE=button onClick="GrpQuestInput();">
</Div>
<Div id="divapprovebtn" style="display: none" align=right>	
	<hr class="line">	
	<INPUT class=cssButton VALUE="记事本查看" TYPE=button onClick="showNotePad();">
	<input VALUE="团体复核通过" class=cssButton TYPE=button onClick="gmanuchk();">
	<input VALUE="团体问题件查询"   class=cssButton name=buttonIssueQuery TYPE=button onClick="GrpQuestQuery();">
	<input VALUE="团体问题件录入"   class=cssButton TYPE=button onClick="GrpQuestInput();">
	<input VALUE="返  回" class=cssButton TYPE=button onClick="goback();">
</Div>
<Div id="divapproveupdatebtn" style="display: none" align=right>
	<hr class="line">
	<INPUT class=cssButton VALUE="记事本查看" TYPE=button onClick="showNotePad();">
	<!--input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();"-->
	<input VALUE="问题件修改确认" class=cssButton TYPE=button onClick="GrpInputConfirm(2);">
	<input VALUE="团体问题件查询"   class=cssButton TYPE=button onClick="GrpQuestQuery();">
	<input VALUE="团体问题件录入"   class=cssButton TYPE=button onClick="GrpQuestInput();">
	<input VALUE="返  回" class=cssButton TYPE=button onClick="goback();">
</Div>
<Div id="divapproveupdatebtn1" style="display: none" align=right>
	<hr class="line">
	<INPUT class=cssButton VALUE="记事本查看" TYPE=button onClick="showNotePad();">
	<!--input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();"-->
	<input VALUE="问题件修改确认" class=cssButton TYPE=button onClick="GrpInputConfirm(2);">
	<input VALUE="团体问题件回复"   class=cssButton TYPE=button onClick="GrpQuestQuery();">
	<!--input VALUE="团体问题件录入"   class=cssButton TYPE=button onclick="GrpQuestInput();"-->
	<input VALUE="返  回" class=cssButton TYPE=button onClick="goback();">
</Div>
<Div id="divchangplanbtn" style="display: none" align=right>
	<hr class="line">
	<!--input VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();"-->
	<input VALUE="团体问题件查询"   class=cssButton TYPE=button onClick="GrpQuestQuery();">
	<input VALUE="团体问题件录入"   class=cssButton TYPE=button onClick="GrpQuestInput();">
	<input VALUE="返  回" class=cssButton TYPE=button onClick="goback();">
	</Div>
<Div id="divuwupdatebtn" style="display: none" align=left>
	<hr class="line">
	<input VALUE="返  回" class=cssButton TYPE=button onClick="goback();">
</Div>
<Div id="divquerybtn" style="display: 'none'" align=right>
	<hr class="line">
	<input VALUE="团体问题件查询"   class=cssButton TYPE=button onClick="GrpQuestQuery();">
	<input VALUE="返  回" class=cssButton TYPE=button onClick="parent.close();">
</Div>
<div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" id="autoMoveInput" value="随动定制确定" onClick="submitAutoMove('21');" class=cssButton>
	<input type="button" name="Next" id="Next" value="下一步" onClick="location.href='ContInsuredInput.jsp?LoadFlag='+tLoadFlag+'&prtNo='+prtNo+'&checktype=2&scantype='+scantype" class=cssButton>
	<input VALUE="返  回" class=cssButton TYPE=button onClick="top.close();">
	<input type=hidden id="autoMoveFlag" name="autoMoveFlag" >
		<%
  		String today=PubFun.getCurrentDate();
  
  		%>
	<input type=hidden id="sysdate" name="sysdate" value="<%=today%>">
	<input type=hidden id="autoMoveValue" name="autoMoveValue">
	<input type=hidden id="pagename" name="pagename" value="21">
</div>
<input type=hidden id="fmAction" name="fmAction">
<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
<input class="common wid" type=hidden name=AgentCode1 id="AgentCode1">
<input class="common wid" type=hidden name=BankCodeName id="BankCodeName">
<input class="common wid" type=hidden name=MissionID  id="MissionID">
<input class="common wid" type=hidden name=SubMissionID  id="SubMissionID">
<input class="common wid" type=hidden name=ProposalGrpContNo  id="ProposalGrpContNo">
<!-- wangxw 20100916 -->
<input class="common wid" type=hidden name=AskpriceNo id="AskpriceNo" value=<%=request.getParameter("AskpriceNo")%>>
<input class="common wid" type=hidden name=AskNo id="AskNo" value=<%=request.getParameter("AskNo")%>>

<input class="common wid" type=hidden name=ActivityID id="ActivityID">
<input class="common wid" type=hidden name=NoType id="NoType">

<!--
#########################################################################################
                                 以前页面里的控件，现在不需要了，故隐藏在此，不能删除
-->
<input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="hidden" class=codeno name=OutPayFlag id="OutPayFlag" value="1" MAXLENGTH=1 onMouseDown="return showCodeList('OutPayFlag', [this,OutPayFlagName],[0,1]);" onDblClick="return showCodeList('OutPayFlag', [this,OutPayFlagName],[0,1]);" onKeyUp="return showCodeListKey('OutPayFlag', [this,OutPayFlagName],[0,1]);" >
<input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="hidden" class=codeno name=EnterKind id="EnterKind" onMouseDown="return showCodeList('enterkind',[this,EnterKindName],[0,1],null,null,null,1);" onDblClick="return showCodeList('enterkind',[this,EnterKindName],[0,1],null,null,null,1);" onKeyUp="return showCodeListKey('enterkind',[this,EnterKindName],[0,1],null,null,null,1);">
<input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="hidden" class=codeno name=AmntGrade id="AmntGrade" onMouseDown="return showCodeList('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);" onDblClick="return showCodeList('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);" onKeyUp="return showCodeListKey('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);">
<input type="hidden" name=Peoples3 id="Peoples3" class="common wid">
<input type="hidden" name=OnWorkPeoples id="OnWorkPeoples" class="common wid">
<input type="hidden" name=OffWorkPeoples id="OffWorkPeoples" class="common wid">
<input type="hidden" name=OtherPeoples id="OtherPeoples" class="common wid">
<input type="hidden" name=RelaPeoples id="RelaPeoples" class="common wid">
<input type="hidden" name=RelaMatePeoples id="RelaMatePeoples" class="common wid">
<input type="hidden" name=RelaYoungPeoples id="RelaYoungPeoples" class="common wid">
<input type="hidden" name=RelaOtherPeoples id="RelaOtherPeoples" class="common wid">
<input type="hidden" name=SpecFlag id="SpecFlag" class="common wid">

<!--
#########################################################################################
-->
			<!--INPUT class=cssButton VALUE="导入被保人清单"  TYPE=button onclick=""-->
			<!--<input class=cssButton VALUE="进入保单险种信息"  TYPE=button onclick="grpRiskInfo()">-->
<input type=hidden name=LoadFlag id="LoadFlag">
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	<script>changecolor(); </script>
</body>
</html>
