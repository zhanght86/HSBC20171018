<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体下个人投保单明细查询

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{ 
		tLoadFlag = "1";
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
loggerDebug("ProposalOnlyInput","LoadFlag:" + tLoadFlag);
%>
<head >
<script>
	var loadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
	var type = "<%=request.getParameter("type")%>";
	var Operator = "<%=tGI.Operator%>";   //记录操作员
	var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
	<%@include file="ProposalInit.jsp"%>
	<SCRIPT src="ProposalInput.js"></SCRIPT>
	
</head>

<body  onload="initForm(); " >
  <form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
    <Div  id= "divButton" style= "display: none">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    </DIV>
    <Div  id= "divRiskCode0" class=maxbox1>
    <table class=common>
       <tr class=common>
          <TD  class= title5>
            险种编码
          </TD>
          <TD  class= input5>
            <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="if(loadFlag=='1') showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);">
          </TD>
		  <TD  class= title5></TD>
          <TD  class= input5></td>
       </tr>
    </table>
    </Div>
    <!-- 隐藏信息 -->
    <Div  id= "divALL0"   style= "display: none">
    <Div  id= "divLCPol0" class=maxbox style= "display: none">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            险种版本
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion id=RiskVersion  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('RiskVersion',[this]);" onkeyup="return showCodeListKey('RiskVersion',[this]);">
          </TD>
          <TD  class= title>
            合同号
          </TD>
          <TD  class= input>
            <Input class="wid common" name=ContNo id=ContNo >
          </TD>
          <TD  class= title>
            集体投保单号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpPolNo id=GrpPolNo >
          </TD>
		</tr>
		<TR  class= common>
          <TD  class= title>
            主险投保单号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=MainPolNo id=MainPolNo >
          </TD>
        
          <TD  class= title>
            首期交费日期
          </TD>
          <TD  class= input>
            <Input class="wid common" name=FirstPayDate id=FirstPayDate >
          </TD>
          <TD  class= title>
            语种
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Lang id=Lang >
          </TD>
		</tr>
		<tr class=common>
          <TD  class= title>
            货币种类
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Currency id=Currency >
          </TD>
          <TD  class= title>
            合同争议处理方式
          </TD>
          <TD  class= input>
            <Input class="wid common" name=DisputedFlag id=DisputedFlag >
          </TD>
          <TD  class= title>
            银行代收标记
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentPayFlag id=AgentPayFlag >
          </TD>
		</TR>
        <TR  class= common>
          <TD  class= title>
            银行代付标记
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentGetFlag id=AgentGetFlag >
          </TD>
          <TD  class= title>
             备注
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Remark id=Remark >
          </TD>
		  <td class=title></td>
		  <td class=input></td>
        </TR>
      </table>
    </Div>
    <!-- 保单信息部分 -->
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
        </td>
        <td class= titleImg>
          保单信息
          <INPUT VALUE="查询责任信息" class=cssButton TYPE=button onclick="showDuty();"> 
          <INPUT VALUE="关联暂交费信息" class=cssButton TYPE=button onclick="showFee();">
          <INPUT id="butChooseDuty" VALUE="选择责任" TYPE=button class=cssButton onclick="chooseDuty();">
          <INPUT id="butBack" VALUE="返回" TYPE=button class=cssButton >
        </td>
      </tr>
    </table>
    <Div  id= "divLCPol1" class=maxbox style= "display: ">
      <table  class= common>
        <TR  class="wid common">
          <TD  class= title>
            投保单号码
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=ProposalNo id=ProposalNo >
          </TD>
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=PrtNo id=PrtNo verify="印刷号码|len<=20" >
          </TD>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom verify="管理机构|code:station" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            销售渠道
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl id=SaleChnl verify="销售渠道|code:SaleChnl" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            代理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCom id=AgentCom verify="代理机构|code:AgentCom" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('AgentCom',[this]);" onkeyup="return showCodeListKey('AgentCom',[this]);">
          </TD>
          <TD  class= title>
            经办人
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Handler id=Handler verify="经办人|len<=10" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode id=AgentCode verify="代理人编码|notnull&code:AgentCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('AgentCode',[this, AgentGroup], [1, 0]);" onkeyup="return showCodeListKey('AgentCode', [this, AgentGroup], [1, 0]);">
          </TD>
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AgentGroup id=AgentGroup verify="代理人组别|notnull&len<=12" >
          </TD>
          <TD  class= title>
            联合代理人编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentCode1 id=AgentCode1 verify="联合代理人编码|len<=10" >
          </TD>
        </TR>
      </table>
    </Div>

    <!-- 被保人信息部分 -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured1);">
    		</td>
    		<td class= titleImg>
    			 被保人信息（客户号：<Input class= common name=CustomerNo > <INPUT id="butBack" VALUE="查询" class=cssButton TYPE=button onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" class=maxbox style= "display: ">
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Name id=Name verify="被保人姓名|notnull&len<=20" >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex id=Sex verify="被保人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="Birthday" id=Birthday verify="被保人出生日期|notnull&date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            年龄
          </TD>
          <TD  class= input>
          <input class="wid common" readonly name="Age" id=Age >
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name="IDType" id=IDType verify="被保人证件类型|code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name="IDNo" id=IDNo verify="被保人证件号码|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="code" name="NativePlace" id=NativePlace verify="被保人国籍|code:NativePlace" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="wid common" name="RgtAddress" id=RgtAddress verify="被保人户口所在地|len<=80" >
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code" name="Marriage" id=Marriage verify="被保人婚姻状况|code:Marriage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            民族
          </TD>
          <TD  class= input>
          <input class="code" name="Nationality" id=Nationality verify="被保人民族|code:Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            学历
          </TD>
          <TD  class= input>
            <Input class="code" name="Degree" id=Degree verify="被保人学历|code:Degree" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="code" name="SmokeFlag" id=SmokeFlag verify="被保人是否吸烟|code:YesNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="PostalAddress" id=PostalAddress verify="被保人联系地址|len<=80" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name="ZipCode" id=ZipCode  verify="被保人邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            家庭电话
          </TD>
          <TD  class= input>
          <input class="wid common" name="Phone" id=Phone verify="被保人家庭电话|len<=18" >
          </TD>
          <TD  class= title>
            移动电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name="Mobile" id=Mobile verify="被保人移动电话|len<=15" >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class="wid common" name="EMail" id=EMail verify="被保人电子邮箱|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpName" id=GrpName verify="被保人工作单位|len<=60" >
          </TD>
          <TD  class= title>
            单位电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name="GrpPhone" id=GrpPhone verify="被保人单位电话|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpAddress" id=GrpAddress verify="被保人单位地址|len<=80" >
          </TD>
          <TD  class= title>
            单位邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name="GrpZipCode" id=GrpZipCode verify="被保人单位邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业（工种）
          </TD>
          <TD  class= input>
            <Input class="wid common" name="WorkType" id=WorkType verify="被保人职业（工种）|len<=10" >
          </TD>
          <TD  class= title>
            兼职（工种）
          </TD>
          <TD  class= input>
            <Input class="wid common" name="PluralityType" id=PluralityType verify="被保人兼职（工种）|len<=10" >
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="code" name="OccupationType" id=OccupationType verify="被保人职业类别|notnull&code:OccupationType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业代码
          </TD>
          <TD  class= input>
            <Input class="code" name="OccupationCode" id=OccupationCode verify="被保人职业代码|code:OccupationCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>

        </TR>
      </table>
    </Div>    
    <!-- 隐藏信息 -->
    <Div  id= "divLCPol01" style= "display: none">
          <TD  class= title>
            健康状况
          </TD>
          <TD  class= input>
            <Input class="code" name=Health id=Health style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Health',[this]);" onkeyup="return showCodeListKey('Health',[this]);">
          </TD>
    </Div> 

    <!-- 个人投保人信息部分 -->
    <table>
    	<tr>
        	<td>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    		</td>
    		<td class= titleImg>
    			 投保人信息（客户号：<Input class= common  name=AppntCustomerNo > 
	    		 <INPUT id="butBack" VALUE="查询" TYPE=button class=cssButton onclick="queryAppntNo();"> 
	    		 首次投保客户无需填写客户号。）
    			 <Div  id= "divSamePerson" style= "display: ">
	    			 <font color=red>
	    			 	如投保人为被保险人本人，可免填本栏，请选择
	    			 	<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
	    			 </font>
    			 </div>
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" class=maxbox style= "display: ">
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntName id=AppntName verify="投保人姓名|notnull&len<=20" >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex verify="投保人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="AppntBirthday" id=AppntBirthday verify="投保人出生日期|notnull&date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            年龄
          </TD>
          <TD  class= input>
          <input class="wid common" readonly name="AppntAge" id=AppntAge >
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" id=AppntIDType verify="投保人证件类型|code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntIDNo" id=AppntIDNo verify="投保人证件号码|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="code" name="AppntNativePlace" id=AppntNativePlace verify="投保人国籍|code:NativePlace" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntRgtAddress" id=AppntRgtAddress verify="投保人户口所在地|len<=80" >
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntMarriage" id=AppntMarriage verify="投保人婚姻状况|code:Marriage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            民族
          </TD>
          <TD  class= input>
          <input class="code" name="AppntNationality" id=AppntNationality verify="投保人民族|code:Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            学历
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntDegree" id=AppntDegree verify="投保人学历|code:Degree" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            与被保险人关系
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntRelationToInsured" id=AppntRelationToInsured verify="投保人与被保险人关系|code:Relation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Relation',[this]);" onkeyup="return showCodeListKey('Relation',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntPostalAddress" id=AppntPostalAddress verify="投保人联系地址|len<=80" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntZipCode" id=AppntZipCode verify="投保人邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            家庭电话
          </TD>
          <TD  class= input>
          <input class="wid common" name="AppntPhone" id=AppntPhone verify="投保人家庭电话|len<=18" >
          </TD>
          <TD  class= title>
            移动电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntMobile" id=AppntMobile verify="投保人移动电话|len<=15" >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntEMail" id=AppntEMail verify="投保人电子邮箱|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntGrpName" id=AppntGrpName verify="投保人工作单位|len<=60" >
          </TD>
          <TD  class= title>
            单位电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntGrpPhone" id=AppntGrpPhone verify="投保人单位电话|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntGrpAddress" id=AppntGrpAddress verify="投保人单位地址|len<=80" >
          </TD>
          <TD  class= title>
            单位邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntGrpZipCode" id=AppntGrpZipCode verify="投保人单位邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业（工种）
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntWorkType" id=AppntWorkType verify="投保人职业（工种）|len<=10" >
          </TD>
          <TD  class= title>
            兼职（工种）
          </TD>
          <TD  class= input> 
            <Input class="wid common" name="AppntPluralityType" id=AppntPluralityType verify="投保人兼职（工种）|len<=10" >
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntOccupationType" id=AppntOccupationType verify="投保人职业类别|code:OccupationType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业代码
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntOccupationCode" id=AppntOccupationCode verify="投保人职业代码|code:OccupationCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntSmokeFlag" id=AppntSmokeFlag verify="投保人是否吸烟|code:YesNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>

        </TR>
      </table>   
    </Div>

    <!-- 集体投保人信息部分 修改所有字段名称-->
    <Div  id= "divLCAppntGrp0" style= "display: none">
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntGrp1);">
    		</td>
    		<td class= titleImg>
    			 投保人信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntGrp1" class=maxbox style= "display: ">
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            投保人客户号
          </TD>
          <TD  class= input>
            <Input class="code" readonly name=ColGrpNo id=ColGrpNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showAppnt();" value="00010220020990000017">
          </TD>
          <TD  class= title>
            单位名称
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AppGrpName id=AppGrpName >
          </TD>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AppGrpAddress id=AppGrpAddress >
          </TD>          
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AppGrpZipCode id=AppGrpZipCode >
          </TD>       
          <TD  class= title>
            单位性质
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=GrpNature id=GrpNature >
          </TD>
          <TD  class= title>
            行业类别
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=BusinessType id=BusinessType >
          </TD>
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            单位总人数
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Peoples id=Peoples >
          </TD>       
          <TD  class= title>
            注册资本金
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=RgtMoney id=RgtMoney >
          </TD>
          <TD  class= title>
            资产总额
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Asset id=Asset >
          </TD>
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            净资产收益率
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=NetProfitRate id=NetProfitRate >
          </TD>       
          <TD  class= title>
            主营业务
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=MainBussiness id=MainBussiness >
          </TD>
          <TD  class= title>
            单位法人代表
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Corporation id=Corporation >
          </TD>
        </TR>        
        <TR  class= common>
          <TD  class= title>
            机构分布区域
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=ComAera id=ComAera >
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            保险联系人一
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=LinkMan1 id=LinkMan1 >
          </TD>
          <TD  class= title>
            部门
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Department1 id=Department1 >
          </TD>
          <TD  class= title>
            职务
          </TD>
          <TD  class= input>
            <Input name=HeadShip1 id=HeadShip1 class="wid common" readonly>
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            联系电话
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Phone1 id=Phone1 >
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input name=E_Mail1 id=E_Mail1 class="wid common" readonly>
          </TD>
          <TD  class= title>
            传真
          </TD>
          <TD  class= input>
            <Input  name=Fax1 id=Fax1 class="wid common" readonly>
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            保险联系人二
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=LinkMan2  id=LinkMan2>
          </TD>
          <TD  class= title>
            部门
          </TD>
          <TD  class= input>
            <Input name=Department2 id=Department2 class="wid common" readonly>
          </TD>
          <TD  class= title>
            职务
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=HeadShip2 id=HeadShip2 >
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            联系电话
          </TD>
          <TD  class= input>
            <Input name=Phone2 id=Phone2 class="wid common" readonly>
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input name=E_Mail2 id=E_Mail2 class="wid common" readonly>
          </TD>
          <TD  class= title>
            传真
          </TD>
          <TD  class= input>
            <Input name=Fax2 id=Fax2 class="wid common" readonly>
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            付款方式
          </TD>
          <TD  class= input>
            <Input name=GetFlag id=GetFlag class="wid common" readonly>
          </TD>
          <TD  class= title>
            开户银行
          </TD>
          <TD  class= input>
            <Input name=GrpBankCode id=GrpBankCode class="wid common" readonly>
          </TD>
          <TD  class= title>
            帐号
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=GrpBankAccNo id=GrpBankAccNo >
          </TD>       
        </TR>        
      </table>
    </Div>
    </Div>

    <!-- 险种信息部分 -->
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCKind1);">
    		</td>
    		<td class= titleImg>
    			 险种信息
    		</td>
    	</tr>
      </table>
      <Div  id= "divLCKind1" class=maxbox style= "display: ">
        <table  class= common>
          <TR  class= common>
            <TD  class= title>
              保单生效日期
            </TD>
            <TD  class= input>
	          <input class="coolDatePicker wid" dateFormat="short" name="CValiDate" id=CValiDate verify="保单生效日期|notnull&date" >
            </TD>         
            <TD  class= title>
              是否指定生效日
            </TD>
            <TD  class= input>
              <Input class="code" name=SpecifyValiDate id=SpecifyValiDate verify="是否体检件|code:YesNo"  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            <TD  class= title>
              保险期间
            </TD>
            <TD  class= input>
              <Input class="wid common" name=InsuYear id=InsuYear verify="保险期间|value<=65535&value>=0&num" >
            </TD>
          </TR>
          
          <TR  class= common>            
            <TD  class= title>
              交费年期
            </TD>
            <TD  class= input>
              <Input class="wid common" name=PayEndYear id=PayEndYear verify="交费年期|value<=65535&value>=0&num" >
            </TD>
            <TD  class= title>
              交费方式
            </TD>
            <TD  class= input>
              <Input class="code" name=PayIntv id=PayIntv verify="交费方式|code:PayIntv" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PayIntv',[this]);" onkeyup="return showCodeListKey('PayIntv',[this]);">
            </TD>          
            <TD  class= title>
              溢交处理方式
            </TD>
            <TD  class= input>
              <Input class="code" name=OutPayFlag id=OutPayFlag verify="溢交处理方式|code:OutPayFlag" 
			  ondblclick="return showCodeList('OutPayFlag',[this]);" onkeyup="return showCodeListKey('OutPayFlag',[this]);">
            </TD>                      
          </TR>
          
          <TR  class= common>
            <TD  class= title>
              收费方式
            </TD>
            <TD  class= input>
              <Input class="code" name=PayLocation id=PayLocation verify="收费方式|code:PayLocation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PayLocation',[this]);" onkeyup="return showCodeListKey('PayLocation',[this]);">
            </TD>
            <TD  class= title>
              开户行
            </TD>
            <TD  class= input>
              <Input class="code" name=BankCode id=BankCode verify="开户行|code:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);">
            </TD>
            <TD  class= title>
              户名
            </TD>
            <TD  class= input>
              <Input class="wid common" name=countName id=countName verify="户名|len<=20" >
            </TD>
          </TR>

          <TR  class= common>
            <TD  class= title>
              银行帐号
            </TD>
            <TD  class= input>
              <Input class="wid common" name=BankAccNo id=BankAccNo verify="银行帐号|len<=40" >
            </TD>
            <TD  class= title>
              生存保险金领取方式
            </TD>
            <TD  class= input>
              <Input class="code" name=LiveGetMode id=LiveGetMode verify="生存保险金领取方式|code:LiveGetMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('LiveGetMode',[this]);" onkeyup="return showCodeListKey('LiveGetMode',[this]);">
            </TD>
          </TR>
          
          <TR  class= common>
            <TD  class= title>
              领取期限
            </TD>
            <TD  class= input>
              <Input class="wid common" name=GetYear id=GetYear verify="领取期限|len<=10" >
            </TD>
            <TD  class= title>
              领取方式
            </TD>
            <TD  class= input>
              <Input class="code" name=GetIntv id=GetIntv verify="领取方式|code:getIntv" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('getIntv',[this]);" onkeyup="return showCodeListKey('getIntv',[this]);">
            </TD>
            <TD  class= title>
              红利领取方式
            </TD>
            <TD  class= input>
              <Input class="code" name=BonusGetMode id=BonusGetMode verify="红利领取方式|code:BonusMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			  ondblclick="return showCodeList('BonusMode',[this]);" onkeyup="return showCodeListKey('BonusMode',[this]);">
            </TD>       
          </TR>
          
          <TR  class= common>                    
            <TD  class= title>
              份数
            </TD>
            <TD  class= input>
              <Input class="wid common" name=Mult id=Mult verify="份数|notnull&len<=13&num" >
            </TD>
            <TD  class= title>
              保费
            </TD>
            <TD  class= input>
              <Input class="wid common" readonly name=Prem id=Prem >
            </TD>
            <TD  class= title>
              保额
            </TD>
            <TD  class= input>
              <Input class="wid common" name=Amnt id=Amnt verify="保额|notnull&len<=10&num" >
            </TD> 
          </TR>

          <TR  class= common>                         
            <TD  class= title>
              是否体检件
            </TD>
            <TD  class= input>
              <Input class="code" name=HealthCheckFlag id=HealthCheckFlag verify="是否体检件|code:YesNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            
          </TR>
        </table>
      </Div>
      <!-- 隐藏 -->
    <Div  id= "divLCKind0" style= "display: none">
      <table  class= common>
	      <TR  class= common>
            
            <TD  class= title>
              终交期间单位
            </TD>
            <TD  class= input>
              <Input class="code" name=PayEndYearFlag id=PayEndYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);">
            </TD>
            <TD  class= title>
              起领期间单位
            </TD>
            <TD  class= input>
              <Input class="code" name=GetYearFlag id=GetYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);">
            </TD>
            <TD  class= title>
              起领日期计算类型
            </TD>
            <TD  class= input>
              <Input class="code" name=GetStartType id=GetStartType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('CalRefType',[this]);" onkeyup="return showCodeListKey('CalRefType',[this]);">
            </TD>
          </TR>
          <TR  class= common>
            <TD  class= title>
              保险期间单位
            </TD>
            <TD  class= input>
              <Input class="code" name=InsuYearFlag id=InsuYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit1',[this]);" onkeyup="return showCodeListKey('PeriodUnit1',[this]);">
            </TD>
            <TD  class= title>
              自动垫交标志
            </TD>
            <TD  class= input>
              <Input class="code" name=AutoPayFlag id=AutoPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            <TD  class= title>
              利差返还方式
            </TD>
            <TD  class= input>
              <Input class="code" name=InterestDifFlag id=InterestDifFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('InterestDifFlag',[this]);" onkeyup="return showCodeListKey('InterestDifFlag',[this]);">
            </TD>
		
          </TR>
          <TR  class= common>
            <TD  class= title>
              减额交清标志
            </TD>
            <TD  class= input>
              <Input class="code" name=SubFlag id=SubFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
			<TD  class= title></TD>
            <TD  class= input></td>
			<TD  class= title></TD>
            <TD  class= input></td>
          </TR>
      </table>
    </Div>

    <!-- 连带被保人信息部分（列表） -->
	<Div  id= "divLCInsured0" style= "display: none">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 连带被保人信息
    		</td>
    	</tr>
      </table>
	  <Div  id= "divLCInsured2" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
	  </div>
	</div>
    <!-- 受益人信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
    		</td>
    		<td class= titleImg>
    			 受益人信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCBnf1" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanBnfGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- 告知信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 告知信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- 特约信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 特约信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCSpec1" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>

    <!--可以选择的责任部分，该部分始终隐藏-->
	<Div  id= "divDutyGrid" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<!--确定是否需要责任信息-->
	</div>
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
  </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>


