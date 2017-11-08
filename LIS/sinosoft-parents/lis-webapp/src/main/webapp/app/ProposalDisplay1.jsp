<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ProposalDisplay.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalDisplayInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ProposalDetail.jsp" method=post name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
      </td>
      <td class= titleImg>
        保单信息
      </td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" class=maxbox style= "display:  ">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            投保单号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=ProposalNo id=ProposalNo>
          </TD>
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=PrtNo id=PrtNo >
          </TD>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            销售渠道
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl id=SaleChnl style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            代理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCom id=AgentCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentCom',[this]);">
          </TD>
          <TD  class= title>
            经办人
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Handler id=Handler >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode id=AgentCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentCode',[this]);">
          </TD>
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentGroup id=AgentGroup style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentGroup',[this]);">
          </TD>
          <TD  class= title>
            联合代理人编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentCode1 id=AgentCode1 >
          </TD>
        </TR>
      </table>
    </Div>
    <!-- 投保人信息部分 -->
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntInd1);">
    		</td>
    		<td class= titleImg>
    			 投保人信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" class=maxbox style= "display:  ">
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            投保人客户号
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntCustomerNo id=AppntCustomerNo >
          </TD>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntName id=AppntName >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <input class="code" name=AppntSex id=AppntSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
            <input class="coolDatePicker wid" dateFormat="short" name="AppntBirthday" id=AppntBirthday >
            <!--<Input class="wid common" name=AppntBirthday >-->
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntIDType id=AppntIDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntIDNo id=AppntIDNo>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            与被保人关系
          </TD>
          <TD  class= input>
            <Input class="code" name=RelationToInsured id=RelationToInsured style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation',[this]);">
          </TD>
          <TD  class= title>
            电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntPhone id=AppntPhone >
          </TD>
          <TD  class= title>
            手机
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntMobile id=AppntMobile >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            通讯地址
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntPostalAddress id=AppntPostalAddress >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntZipCode id=AppntZipCode >
          </TD>
          <TD  class= title>
            e_mail
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntEMail id=AppntEMail >
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
    			 被保人信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" class=maxbox style= "display:  ">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            被保人客户号
          </TD>
          <TD  class= input>
            <Input class="wid common" name=CustomerNo id=CustomerNo >
          </TD>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Name id=Name >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex id=Sex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="Birthday" id=Birthday >
            <!--<Input class= common name=Birthday >-->
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name=IDType id=IDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=IDNo id=IDNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            健康状况
          </TD>
          <TD  class= input>
            <Input class="code" name=Health id=Health style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Health',[this]);">
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="code" name=OccupationType id=OccupationType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('OccupationType',[this]);">
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code" name=Marriage id=Marriage style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Marriage',[this]);">
          </TD>
        </TR>
      </table>
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
    <Div  id= "divLCKind1" class=maxbox style= "display:  ">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RiskCode',[this]);">
          </TD>
          <TD  class= title>
            险种版本
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion id=RiskVersion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RiskVersion',[this]);">
          </TD>
          <TD  class= title>
            保单生效日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="CValiDate" id=CValiDate >
            <!--<Input class="wid common" name=CValiDate >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            终交期间
          </TD>
          <TD  class= input>
            <Input class="wid common" name=PayEndYear id=PayEndYear >
          </TD>
          <TD  class= title>
            终交期间单位
          </TD>
          <TD  class= input>
            <Input class="wid common" name=PayEndYearFlag id=PayEndYearFlag >
          </TD>
          <TD  class= title>
            终交日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="PayEndDate" id=PayEndDate >
            <!--<Input class="wid common" name=PayEndDate >-->
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            起领期间
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GetYear id=GetYear>
          </TD>
          <TD  class= title>
            起领期间单位
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GetYearFlag id=GetYearFlag >
          </TD>
          <TD  class= title>
            起领日期计算类型
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GetStartType id=GetStartType >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            保险期间
          </TD>
          <TD  class= input>
            <Input class="wid common" name=InsuYear id=InsuYear >
          </TD>
          <TD  class= title>
            保险期间单位
          </TD>
          <TD  class= input>
            <Input class="wid common" name=InsuYearFlag id=InsuYearFlag >
          </TD>
          <TD  class= title>
            保险终止日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="EndDate" id=EndDate >
            <!--<Input class="wid common" name=EndDate >-->
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            份数
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Mult id=Mult >
          </TD>
          <TD  class= title>
            保费
          </TD>
          <TD  class= input>
            <Input class="wid common" name=StandPrem id=StandPrem >
          </TD>
          <TD  class= title>
            保额
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Amnt id=Amnt >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            自动垫交标志
          </TD>
          <TD  class= input>
            <Input class="code" name=AutoPayFlag id=AutoPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
          <TD  class= title>
            红利分配方式
          </TD>
          <TD  class= input>
            <Input class="code" name=BonusMode id=BonusMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('BonusMode',[this]);">
          </TD>
          <TD  class= title>
            利差返还方式
          </TD>
          <TD  class= input>
            <Input class="code" name=InterestDifFlag id=InterestDifFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('InterestDifFlag',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            减额交清标志
          </TD>
          <TD  class= input>
            <Input class="code" name=SubFlag id=SubFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
          <TD  class= title>
            缴费位置
          </TD>
          <TD  class= input>
            <Input class="code" name=PayLocation id=PayLocation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PayLocation',[this]);">
          </TD>
          <TD  class= title>
            体检标志
          </TD>
          <TD  class= input>
            <Input class="code" name=HealthCheckFlag id=HealthCheckFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
        </TR>
      </table>
    </Div>
    <!-- 连带被保人信息部分（列表） -->
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
	<Div  id= "divLCInsured2" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
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
	<Div  id= "divLCBnf1" style= "display:  ">
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
	<Div  id= "divLCImpart1" style= "display:  ">
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
	<Div  id= "divLCSpec1" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanSpecGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
	<INPUT VALUE="责任信息" TYPE=button class=cssButton onclick="showDuty();"> 
    <INPUT VALUE="暂交费信息" TYPE=button class=cssButton onclick="showFee();"> 					
    <INPUT VALUE="返回" TYPE=button class=cssButton onclick="parent.close();"> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>
