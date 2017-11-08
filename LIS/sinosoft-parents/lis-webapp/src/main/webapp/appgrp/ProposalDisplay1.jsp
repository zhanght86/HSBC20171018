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
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalDisplayInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ProposalDetail.jsp" method=post name=fm target="fraSubmit">
      <INPUT VALUE="责任信息" TYPE=button onclick="showDuty();"> 
      <INPUT VALUE="暂交费信息" TYPE=button onclick="showFee();"> 					
      <INPUT VALUE="返回" TYPE=button onclick="parent.close();"> 
    <!-- 保单信息部分 -->
    <table>
      <tr>
      <td>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
      </td>
      <td class= titleImg>
        保单信息
      </td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            投保单号码
          </TD>
          <TD  class= input>
            <Input class= common name=ProposalNo >
          </TD>
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class= input>
            <Input class= common name=PrtNo >
          </TD>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            销售渠道
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl ondblclick="return showCodeList('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            代理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCom ondblclick="return showCodeList('AgentCom',[this]);">
          </TD>
          <TD  class= title>
            经办人
          </TD>
          <TD  class= input>
            <Input class= common name=Handler >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);">
          </TD>
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this]);">
          </TD>
          <TD  class= title>
            联合代理人编码
          </TD>
          <TD  class= input>
            <Input class= common name=AgentCode1 >
          </TD>
        </TR>
      </table>
    </Div>
    <!-- 投保人信息部分 -->
    <table>
    	<tr>
        	<td>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntInd1);">
    		</td>
    		<td class= titleImg>
    			 投保人信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" style= "display: ''">
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            投保人客户号
          </TD>
          <TD  class= input>
            <Input class= common name=AppntCustomerNo >
          </TD>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class= common name=AppntName >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <input class="code" name=AppntSex ondblclick="return showCodeList('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
            <input class="coolDatePicker" dateFormat="short" name="AppntBirthday" >
            <!--<Input class= common name=AppntBirthday >-->
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntIDType ondblclick="return showCodeList('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class= common name=AppntIDNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            与被保人关系
          </TD>
          <TD  class= input>
            <Input class="code" name=RelationToInsured ondblclick="return showCodeList('Relation',[this]);">
          </TD>
          <TD  class= title>
            电话
          </TD>
          <TD  class= input>
            <Input class= common name=AppntPhone >
          </TD>
          <TD  class= title>
            手机
          </TD>
          <TD  class= input>
            <Input class= common name=AppntMobile >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            通讯地址
          </TD>
          <TD  class= input>
            <Input class= common name=AppntPostalAddress >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class= common name=AppntZipCode >
          </TD>
          <TD  class= title>
            e_mail
          </TD>
          <TD  class= input>
            <Input class= common name=AppntEMail >
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
    <Div  id= "divLCInsured1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            被保人客户号
          </TD>
          <TD  class= input>
            <Input class= common name=CustomerNo >
          </TD>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class= common name=Name >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex ondblclick="return showCodeList('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="Birthday" >
            <!--<Input class= common name=Birthday >-->
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name=IDType ondblclick="return showCodeList('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class= common name=IDNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            健康状况
          </TD>
          <TD  class= input>
            <Input class="code" name=Health ondblclick="return showCodeList('Health',[this]);">
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="code" name=OccupationType ondblclick="return showCodeList('OccupationType',[this]);">
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code" name=Marriage ondblclick="return showCodeList('Marriage',[this]);">
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
    <Div  id= "divLCKind1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);">
          </TD>
          <TD  class= title>
            险种版本
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion ondblclick="return showCodeList('RiskVersion',[this]);">
          </TD>
          <TD  class= title>
            保单生效日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="CValiDate" >
            <!--<Input class= common name=CValiDate >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            终交期间
          </TD>
          <TD  class= input>
            <Input class= common name=PayEndYear >
          </TD>
          <TD  class= title>
            终交期间单位
          </TD>
          <TD  class= input>
            <Input class= common name=PayEndYearFlag >
          </TD>
          <TD  class= title>
            终交日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="PayEndDate" >
            <!--<Input class= common name=PayEndDate >-->
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            起领期间
          </TD>
          <TD  class= input>
            <Input class= common name=GetYear >
          </TD>
          <TD  class= title>
            起领期间单位
          </TD>
          <TD  class= input>
            <Input class= common name=GetYearFlag >
          </TD>
          <TD  class= title>
            起领日期计算类型
          </TD>
          <TD  class= input>
            <Input class= common name=GetStartType >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            保险期间
          </TD>
          <TD  class= input>
            <Input class= common name=InsuYear >
          </TD>
          <TD  class= title>
            保险期间单位
          </TD>
          <TD  class= input>
            <Input class= common name=InsuYearFlag >
          </TD>
          <TD  class= title>
            保险终止日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="EndDate" >
            <!--<Input class= common name=EndDate >-->
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            份数
          </TD>
          <TD  class= input>
            <Input class= common name=Mult >
          </TD>
          <TD  class= title>
            保费
          </TD>
          <TD  class= input>
            <Input class= common name=StandPrem >
          </TD>
          <TD  class= title>
            保额
          </TD>
          <TD  class= input>
            <Input class= common name=Amnt >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            自动垫交标志
          </TD>
          <TD  class= input>
            <Input class="code" name=AutoPayFlag ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
          <TD  class= title>
            红利分配方式
          </TD>
          <TD  class= input>
            <Input class="code" name=BonusMode ondblclick="return showCodeList('BonusMode',[this]);">
          </TD>
          <TD  class= title>
            利差返还方式
          </TD>
          <TD  class= input>
            <Input class="code" name=InterestDifFlag ondblclick="return showCodeList('InterestDifFlag',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            减额交清标志
          </TD>
          <TD  class= input>
            <Input class="code" name=SubFlag ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
          <TD  class= title>
            缴费位置
          </TD>
          <TD  class= input>
            <Input class="code" name=PayLocation ondblclick="return showCodeList('PayLocation',[this]);">
          </TD>
          <TD  class= title>
            体检标志
          </TD>
          <TD  class= input>
            <Input class="code" name=HealthCheckFlag ondblclick="return showCodeList('YesNo',[this]);">
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
	<Div  id= "divLCInsured2" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
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
	<Div  id= "divLCBnf1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
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
	<Div  id= "divLCImpart1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
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
	<Div  id= "divLCSpec1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSpecGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
