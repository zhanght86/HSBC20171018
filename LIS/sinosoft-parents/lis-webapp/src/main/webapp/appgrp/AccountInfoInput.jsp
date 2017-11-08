<!--
//*******************************************************
//* 程序名称：AccountInfoInput.jsp
//* 程序功能：页面中 子公司录入 按钮
//* 创建日期：2010-09-09
//*	创建者:	 wangxw@sinosoft.com.cn 
//* 更新记录：  更新人    更新日期     更新原因/内容
//******************************************************
-->


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String PrtNo = request.getParameter("PrtNo");
String ManageCom = request.getParameter("ManageCom");
String LoadFlag = request.getParameter("LoadFlag");
String CValiDate=request.getParameter("CValiDate");
String SupGrpNo = request.getParameter("SupGrpNo");
String SupGrpName = request.getParameter("SupGrpName");

%>
<head>
<script>
var MissionID = "<%=request.getParameter("MissionID")%>";
var scantype  = "<%=request.getParameter("scantype")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var CValiDate="<%=request.getParameter("CValiDate")%>";
</script>
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
    <script src="AccountInfoInput.js"></SCRIPT>
    <%@include file="AccountInfoInit.jsp"%>
  </head>
<body  onload="initForm();initElementtype()" >
<Form action="./AccountInfoSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	
<!-- 合同信息 -->
<Div  id= "divConttInfo" style= "display:  " >
<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>合同信息</td>
			</tr>
</table>
<div class="maxbox">
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>投保单号码</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=GrpContNo id="GrpContNo" value="<%=GrpContNo%>">
					<Input type=hidden name=PrtNo value="<%=PrtNo%>">
					<input type=hidden name=mOperate>
					<input type=hidden name=CValiDate value="<%=CValiDate%>">
					<input type=hidden name=mFlagStr>
				</TD>
				<TD  class= title>管理机构</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=ManageCom id="ManageCom" value="<%=ManageCom%>">
				</TD>
				<TD  class= title>总公司客户号</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=SupGrpNo id="SupGrpNo" value="<%=SupGrpNo%>">
				</TD>
			</TR>
			<TR  class= common>
				
				<TD  class= title>总公司单位名称</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=SupGrpName id="SupGrpName"  value="<%=SupGrpName%>"
				</TD>
			</TR>
		</table>
</div>

<!-- 子公司信息 -->

<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
				</td>
				<td class= titleImg>子公司信息</td>
			</tr>
</table>
<Div  id= "divAccountInfo" class="maxbox1" style= "display:  " >
			<table class=common align=center>
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
						<input class=codeno name=GrpNature id="GrpNature" style="background: url(../common/images/select--bg_03.png) no-repeat center; " verify="单位性质|code:grpNature&len<=10" onClick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onDblClick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onKeyUp="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);"><input class=codename name=GrpNatureName id="GrpNatureName" readonly=true >
					</td>
				</tr>				
				<tr class=common>
					<td class=title>行业类别</td>
					<td class=input>
						<input class=codeno name=BusinessType id="BusinessType" style="background: url(../common/images/select--bg_03.png) no-repeat center; " verify="行业类别|notnull&code:BusinessType&len<=20"  onClick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onDblClick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onKeyUp="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);"><input class=codename name=BusinessTypeName id="BusinessTypeName" readonly=true elementtype=nacessary >
					</td>
					<td class=title>员工总数</td>
					<td class=input>
						<input class="common wid" name=Peoples id="Peoples"   verify="单位总人数|int">
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
				</tr>
				<tr>
					<td class=title>单位地址编码</td>
					<td class=input>
						<input class="code" name="GrpAddressNo" id="GrpAddressNo" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0], ,  ,  , true);"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0], ,  ,  , true);" onKeyUp="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0], ,  ,  , true);">
					</td>
				</tr>
				<tr class=common>
					<td class=title>单位地址</td>
					<td class=input colspan="3">
						<input class="common3" name=GrpAddress id="GrpAddress"  verify="单位地址|len<=60">
					</td>
					<td class=title>邮政编码</td>
					<td class=input>
						<input class="common wid" name=GrpZipCode id="GrpZipCode" maxlength=6  verify="邮政编码|zipcode">
					</td>
				</tr>

				<tr class=common>
					<td class=title>保险联系人一</td>
				</tr>
				<tr class=common>
					<td class=title>姓  名</td>
					<td class=input>
						<input class="common wid" name=LinkMan1 id="LinkMan1"  verify="保险联系人一姓名|len<=10">
					</td>
					<td class=title>联系电话</td>
					<td class=input>
						<input class="common wid" name=Phone1 id="Phone1"  verify="保险联系人一联系电话|len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="common wid" name=E_Mail1 id="E_Mail1" verify="保险联系人一E-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>部  门</td>
					<td class=input colspan=3>
						<input class="common wid"3 name=Department1 id="Department1" verify="保险联系人一部门|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>保险联系人二</td>
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
					<td class=input colspan=3>
						<input class="common wid"3 name=Department2 id="Department2" verify="保险联系人二部门|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>付款方式</td>
					<td class=input>
						<input class=codeno name=GetFlag id="GetFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onClick="return showCodeList('paymode',[this,GetFlagName],[0,1]);"  ondblclick="return showCodeList('paymode',[this,GetFlagName],[0,1]);" onKeyUp="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName id="GetFlagName" readonly=true >
					</td>
					<td class=title>开户银行</td>
					<td class=input>
            <Input NAME=BankCode id="BankCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" VALUE="" CLASS="code" MAXLENGTH=20 readonly onClick="return queryBank();"  ondblclick="return queryBank();" onKeyUp=" queryBank();" > 						
					</td>
					<td class=title>账  号</td>
					<td class=input>
						<input class="common wid" name=BankAccNo id="BankAccNo" verify="帐号|len>=6&len<=40">
					</td>
				</tr>
	
		</table>
</Div>
</br>
<Div  id= "divAddDelButton" style= "display:  " style="float: right">
    <input type =button class=cssButton value="添加子公司" onClick="addRecord();"> 
    <input type =button class=cssButton value="删除子公司" onClick="deleteRecord();"> 
    <input type =hidden class=cssButton value="修改子公司" onClick="modifyRecord();">
</DIV>
</br></br>


<div  id= "divLCImpart1" style= "display: ">
<table  class= common>
	<tr  class= common>
		<td text-align: left colSpan=1>
			<span id="spanAccountInfoGrid" >
			</span>
		</td>
	</tr>
</table>
</div>
</br></br>
 <hr class="line">
    <DIV id = "divaddPerButton" style = "display: " style="float: left">
      <INPUT class=cssButton id="riskbutton9" VALUE="上一步" TYPE=button onClick="returnparent();">      
    </DIV>     
        <input type=hidden id="" name="autoMoveFlag">
        <input type=hidden id="" name="autoMoveValue">
        <input type=hidden id="" name="pagename" value="">

      </div>
	<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<INPUT  type= "hidden" class= Common name= MissionID value= ""><!-- 工作流任务编码 -->
  <INPUT  type= "hidden" class= Common name= SubMissionID value= "">
  <INPUT  type= "hidden" class= Common name= CustomerNo value= "">    
  <INPUT  type= "hidden" class= Common name= AddressNo value= "">
  <!--无名单补单时用到的原无名单合同号-->
  <INPUT  type= "hidden" class= Common name= vContNo value= "">
  <input type=hidden name=BQFlag>
  <input type=hidden name=EdorType>
  <input type=hidden name=EdorTypeCal>
  <input type=hidden name=EdorValiDate>
</Form>
<Br><Br><Br><Br><Br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
