<script lanaguage="javascript">

function getClickBankCode(BankCode,BankName,AgentCom)
{
	var tManageCom = document.all('ManageCom').value;
	//alert(tManageCom.length);
	//if(length(tManageCom))
    var tAgentBankCode = document.all('AgentBankCode').value;
    var getBankCodeSQL = " 1 and ManageCom=#"+tManageCom+"#" ;
    getBankCodeSQL = getBankCodeSQL + " and AgentCom like #"+tAgentBankCode+"%#" ;
    showCodeList('BankCode',[BankCode,BankName,AgentCom],[0,1,2],null, getBankCodeSQL, "1",'1');
}

function getKeyUpBank(BankCode,BankName,AgentCom)
{
	var tManageCom = document.all('ManageCom').value;
    var tAgentBankCode = document.all('AgentBankCode').value;
    var getBankCodeSQL = " 1 and ManageCom=#"+tManageCom+"#" ;
    getBankCodeSQL = getBankCodeSQL + " and AgentCom like #"+tAgentBankCode+"%#" ;
    showCodeListKey('BankCode',[BankCode,BankName,AgentCom],[0,1,2],null, getBankCodeSQL, "1",'1');
}

function getClickAgentCom(AgentCom,InputAgentComName)
{
   var tManageCom =  document.all('ManageCom').value;
    var sql_managecom = "";
    if(tManageCom.length<=4)
    {
    	sql_managecom = "1 and  branchtype=#3# and #1#=#1# and managecom like #"+tManageCom+"%%# ";
  	}
  	else
  	{
  		sql_managecom = "1  and  branchtype=#3# and #1#=#1# and managecom like substr(#"+tManageCom+"#,0,4)||#%%#  ";
  	}
   //alert(tManageCom.length);
   //alert(sql_managecom);
   showCodeList('AgentCom',[AgentCom,InputAgentComName],[0,1],null,sql_managecom,"1",'1');
}

function getClickUpAgentCom(AgentCom,InputAgentComName)
{
   var tManageCom =  document.all('ManageCom').value;
   var sql_managecom = "";
    if(tManageCom.length<=4)
    {
    	sql_managecom = "1  and  branchtype=#3# and #1#=#1# and managecom like #"+tManageCom+"%%# ";
  	}
  	else
  	{
  		sql_managecom = "1  and  branchtype=#3# and #1#=#1# and managecom like substr(#"+tManageCom+"#,0,4)||#%%#  ";
  	}
   showCodeListKey('AgentCom',[AgentCom,InputAgentComName],[0,1],null,sql_managecom,"1",'1');
}

</script>

<Div id="DivLCContButton" style="display:''">
  <Table id="table1">
  	<TR>
  		<TD class="common">
  		  <img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
  		</TD>
  		<TD class="titleImg">合同信息</TD>
  	</TR>
  </Table>
</Div>
<Div id="DivLCCont" class="maxbox" STYLE="display:''">
	<Table class="common" id="table2"> 
		<TR CLASS="common">
			<TD CLASS="title">印刷号  </TD>
			<TD CLASS="input" COLSPAN="1">
				<input name="ProposalContNo" id="ProposalContNo"  CLASS="common wid" readonly TABINDEX="-1" MAXLENGTH="20">
				<input name="PrtNo"  type="hidden" value="" class="common wid" elementtype=nacessary tabindex="-1" maxlength="9" verify="印刷号码|notnull&amp;len=14">
			</TD>
			<TD class="title">投保日期</TD>
			<TD class="input">
        <Input class="coolDatePicker" elementtype=nacessary  onblur="checkapplydate();" onClick="laydate({elem: '#PolAppntDate'});" verify="投保日期|notnull&DATE" verifyorder="1" dateFormat="short" name=PolAppntDate id="PolAppntDate"><span class="icon"><a onClick="laydate({elem: '#PolAppntDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD> 
			<td class="title">生效日期</td>
			<td class="input">
        <Input class="coolDatePicker"  onClick="laydate({elem: '#CValiDate'});" verify="有效开始日期|DATE" dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</td>  
		</TR>
		<!--
		<TD CLASS="title">印刷号码 </TD>
		<TD class="input" colspan="1"></TD>
		<TD CLASS="title">销售渠道 </TD>
		<TD CLASS="input" COLSPAN="1">
		  <input NAME="SaleChnl" VALUE MAXLENGTH="2" CLASS="codeno" elementtype=nacessary ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input NAME="SaleChnlName" CLASS="codename" readonly="readonly">
		</TD>
		-->  
		<TR class="common">
			<TD class="title">销售方式</TD>
			<TD class="input">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="SellType" id="SellType" verify="销售方式|notnull" verifyorder="1" readonly value='02' ><input name="sellTypeName" id="sellTypeName" class="codename" value="柜面" readonly="readonly">
				<input type="hidden" name="SaleChnl" id="SaleChnl" value="03" elementtype='nacessary'>
			</TD>      
			<TD CLASS="title">管理机构 </TD>
			<TD CLASS="input">
			  <input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" name="ManageCom" id="ManageCom" verifyorder="1" MAXLENGTH="10" CLASS="codeno" onclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|notnull"><input name="ManageComName" id="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
			</TD>
			<TD class="title">高额件标识</TD>
			<TD>
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" readonly="readonly" name="highAmntFlag" id="highAmntFlag" CodeData="0|^1|是 ^2|否" onclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ndblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" id="highAmntFlagName" class="codename" readonly="readonly">
			</TD>
		</TR>
	</Table>
	<Table class="common">
		<TR class="common">
			<TD  class= title>代理机构</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" name=AgentCom id="AgentCom" class='codeno' verifyorder="3" verify="代理机构|code:AgentCom&notnull" onclick="return getClickAgentCom(AgentCom,InputAgentComName);" ondblclick="return getClickAgentCom(AgentCom,InputAgentComName);" onkeyup="return getClickUpAgentCom(AgentCom,InputAgentComName);"><input name=InputAgentComName id="InputAgentComName" class='codename' readonly=true elementtype=nacessary>
			</TD>
			<TD class="title">代理人编码</TD> 
			<TD class="input" colspan="1">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name="AgentCode" id="AgentCode" class="codeno" verifyorder="1" verify="银行专管员|notnull" onclick="queryAgent();" ondblclick="queryAgent();" onblur="return queryAgent();" onkeyup="queryAgent();"><input name="AgentName" id="AgentName" class="codename" readonly="readonly" elementtype=nacessary>
			</TD>   
			
		
			<TD CLASS="title">代理人组别</TD> 
			<TD CLASS="input" COLSPAN="1">  
				<input name="BranchAttr" id="BranchAttr"  verifyorder="1" VALUE CLASS="common wid" readonly="readonly"  elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
				<input name="AgentGroup" id="AgentGroup" class="common wid" value="" readonly="readonly"  type="hidden"  >
			</TD>
		</TR>
		<TR class="common" style="display:">
			<TD class="title">柜员代码 </TD>
			<TD class="input" colspan="1"><input name="CounterCode" id="CounterCode" class="common wid" > </TD>
			<TD  class= title>
        备注
      </TD>
      <TD  class= input>
        <Input class= "common wid" name="Remark" id="Remark">
      </TD>
      <td class="title" style="display:''">初审员签名</td>
			<td class="input" style="display:''">
				<input class="common wid" name="SignName" id="SignName" verify="初审员签名|notnull"  verifyorder="1"  elementtype=nacessary>
			</td>
		</TR>   
	
		<TR class="common">
			<td class="title" style="display:''">初审日期</td>
			<td class="input" style="display:''">
				<!-- <input class="multiDatePicker" name="FirstTrialDate" onblur="checkFirstTrialDate();" verify="初审日期|notnull&DATE" verifyorder="1"  elementtype=nacessary> -->
        <Input class="coolDatePicker" onClick="laydate({elem: '#FirstTrialDate'});" onblur="checkFirstTrialDate();" verify="初审日期|notnull&DATE" verifyorder="1"  elementtype=nacessary dateFormat="short" name=FirstTrialDate id="FirstTrialDate"><span class="icon"><a onClick="laydate({elem: '#FirstTrialDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</td>
			<td class="title" >续期缴费提示</td>
				<td class="input" >
					<input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name="XQremindFlag" id="XQremindFlag" CodeData="0|^1|是 ^0|否" onclick="showCodeListEx('XQremindFlag',[this,XQremindFlagName],[0,1])" ondblclick="showCodeListEx('XQremindFlag',[this,XQremindFlagName],[0,1])" onkeyup="return showCodeListKeyEx('XQremindFlag',[this,XQremindFlagName],[0,1])" ><input name="XQremindFlagName" id="XQremindFlagName" class="codename" readonly="readonly">
			</td>
		</TR>		
	</Table>
</Div>	
</div>
<hr class="line">
<!--
		<TR class="common">
      <TD class="title">
        不丧失价值选择	
      </TD>
      <TD class="input">
        <Input class= 'codeno' name=KeepValueFlag ondblclick="showCodeList('KeepValue',[this,KeepValueFlagName],[0,1])" onkeyup="return showCodeListKey('KeepValue',[this,KeepValueFlagName],[0,1])" ><input name="KeepValueFlagName" class="codename" readonly="readonly">
      </TD>
		</TR>
    -->

<!--
<Div  id= "divLCCont1" style= "display: 'none'">
  <Table  class= common align='center' >
    <TR  class= common>
      <TD  class= title>
        集体合同号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GrpContNo >
      </TD>
      <TD  class= title>
        合同号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ContNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        总单投保单号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ProposalContNo >
      </TD>
      <TD  class= title>
        印刷号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PrtNo >
      </TD>
    </TR>
    
    <TR  class= common>
      <TD  class= title>
        总单类型
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ContType >
      </TD>
      <TD  class= title>
        家庭单类型
      </TD>
      <TD  class= input>
        <Input class= 'common' name=FamilyType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        家庭保障号
      </TD>
      <TD  class= input>
        <Input class= 'common' name=FamilyID >
      </TD>
      <TD  class= title>
        保单类型标记
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PolType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        卡单标志
      </TD>
      <TD  class= input>
        <Input class= 'common' name=CardFlag >
      </TD>
     <TD  class= title>
        管理机构
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ManageCom >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        处理机构
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ExecuteCom >
      </TD>
     <TD  class= title>
        代理机构
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentCom >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        代理人编码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentCode >
      </TD>
      <TD  class= title>
        代理人组别
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentGroup >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        联合代理人代码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentCode1 >
      </TD>
       <TD  class= title>
        代理机构内部分类
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentType >
      </TD>
    </TR>
    <TR  class= common>
     <TD  class= title>
        销售渠道
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SaleChnl >
      </TD>
      <TD  class= title>
        经办人
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Handler >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        保单口令
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Password >
      </TD>
     <TD  class= title>
        投保人客户号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        投保人名称
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntName >
      </TD>
      <TD  class= title>
        投保人性别
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntSex >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        投保人出生日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntBirthday >
      </TD>
      <TD  class= title>
        投保人证件类型
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntIDType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        投保人证件号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntIDNo >
      </TD>
      <TD  class= title>
        被保人客户号
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        被保人名称
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredName >
      </TD>
      <TD  class= title>
        被保人性别
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredSex >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        被保人出生日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredBirthday >
      </TD>
      <TD  class= title>
        证件类型
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredIDType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        证件号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredIDNo >
      </TD>
      <TD  class= title>
        交费间隔
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PayIntv >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        交费方式
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PayMode >
      </TD>
      <TD  class= title>
        交费位置
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PayLocation >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        合同争议处理方式
      </TD>
      <TD  class= input>
        <Input class= 'common' name=DisputedFlag >
      </TD>
      <TD  class= title>
        溢交处理方式
      </TD>
      <TD  class= input>
        <Input class= 'common' name=OutPayFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        保单送达方式
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GetPolMode >
      </TD>
      <TD  class= title>
        签单机构
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SignCom >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        签单日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SignDate >
      </TD>
      <TD  class= title>
        签单时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SignTime >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        银行委托书号码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ConsignNo >
      </TD>
      <TD  class= title>
        银行编码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=BankCode >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        银行帐号
      </TD>
      <TD  class= input>
        <Input class= 'common' name=BankAccNo >
      </TD>
      <TD  class= title>
        银行帐户名
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AccName >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        保单打印次数
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PrintCount >
      </TD>
      <TD  class= title>
        遗失补发次数
      </TD>
      <TD  class= input>
        <Input class= 'common' name=LostTimes >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        语种标记
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Lang >
      </TD>
      <TD  class= title>
        币别
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Currency >
      </TD>
    </TR>
    <TR  class= common>
     TD  class= title>
        备注
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Remark >
      </TD>
      <TD  class= title>
        人数
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Peoples >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        份数
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Mult >
      </TD>
      <TD  class= title>
        保费
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Prem >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        保额
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Amnt >
      </TD>
      <TD  class= title>
        累计保费
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SumPrem >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        余额
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Dif >
      </TD>
      <TD  class= title>
        交至日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PaytoDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        首期交费日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=FirstPayDate >
      </TD>
      <TD  class= title>
        保单生效日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=CValiDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        录单人
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InputOperator >
      </TD>
      <TD  class= title>
        录单完成日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InputDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        录单完成时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InputTime >
      </TD>
      <TD  class= title>
        复核状态
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        复核人编码
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveCode >
      </TD>
      <TD  class= title>
        复核日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        复核时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveTime >
      </TD>
      <TD  class= title>
        核保状态
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        核保人
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWOperator >
      </TD>
      <TD  class= title>
        核保完成日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        核保完成时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWTime >
      </TD>
      <TD  class= title>
        投保单/保单标志
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        投保单申请日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PolApplyDate >
      </TD>
      <TD  class= title>
        保单送达日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GetPolDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        保单送达时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GetPolTime >
      </TD>
      <TD  class= title>
        保单回执客户签收日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=CustomGetPolDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        状态
      </TD>
      <TD  class= input>
        <Input class= 'common' name=State >
      </TD>
      <TD  class= title>
        操作员
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Operator >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        入机日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=MakeDate >
      </TD>
      <TD  class= title>
        入机时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=MakeTime >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        最后一次修改日期
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ModifyDate >
      </TD>
      <TD  class= title>
        最后一次修改时间
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ModifyTime >
      </TD>
    </TR>
  </Table>
</Div>
-->
