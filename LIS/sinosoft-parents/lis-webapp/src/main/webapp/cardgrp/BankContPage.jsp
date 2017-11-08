<div id=DivLCContButton style="display:''">
  <table id="table1">
  	<tr>
  		<td>
  		  <img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
  		</td>
  		<td class="titleImg">合同信息
  		</td>
  	</tr>
  </table>
</div>


<div id="DivLCCont" STYLE="display:''">
	<table class="common" id="table2"> 

		<tr CLASS="common">
			<td CLASS="title">投保单号  
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="ProposalContNo" VALUE CLASS="common" readonly TABINDEX="-1" MAXLENGTH="20">
			  <input name="PrtNo" type="hidden" class="common" elementtype=nacessary tabindex="-1" maxlength="9" verify="印刷号码|notnull&amp;len=9">
    	</td>
      <td class="title">投保日期</td>
      <td class="input">
        <input class="common" dateFormat="short" elementtype=nacessary onblur="checkapplydate();" name="PolAppntDate" verify="投保日期|notnull&DATE" verifyorder="1" >
      </td> 
      <td class="title">销售方式</td>
　　　<td class="input">
        <input class="codeno" name="SellType" verify="销售方式|notnull" verifyorder="1"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input name="sellTypeName" class="codename" readonly="readonly">
        <input type="hidden" name="SaleChnl" value="02" elementtype='nacessary'>
      </td>      
<!--
			<td CLASS="title">印刷号码 
    	</td>
			<td class="input" colspan="1">
    	</td>
-->
		</tr>

    <tr class="common">
    	
<!--
			<td CLASS="title">销售渠道 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="SaleChnl" VALUE MAXLENGTH="2" CLASS="codeno" elementtype=nacessary ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input NAME="SaleChnlName" CLASS="codename" readonly="readonly">
    	</td>
-->  

			<td CLASS="title">管理机构 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="ManageCom"  verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:comcode&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
    	</td>
      <td class="title">
        高额件标识
      </td>
      <td>
        <input class="codeno" readonly="readonly" name="highAmntFlag" CodeData="0|^1|是 ^2|否" aondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" class="codename" readonly="readonly">
      </td>
    </tr>

	</table>

  <table class="common">
<!--
  		<tr class="common"> 
			<td class="title">业务员代码 
    	</td>
			<td class="input" COLSPAN="1">
			  <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" onkeyup="return queryAgent();" ondblclick="return queryAgent();" >
      </td>
			<td class="title">业务员姓名
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary  verifyorder="1" >
      </td>
			<td CLASS="title">所属机构 
    	</td>
			<td CLASS="input" COLSPAN="1"> 

			  <input NAME="AgentManageCom"  verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" verify="所属机构|code:station&amp;notnull"><input NAME="AgentManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
			  
                       <input class="codeno" name="AgentManageCom" verify="所属机构|notnull" verifyorder="1"  ondblclick="showCodeList('comcode',[this,ManageComName],[0,1])" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1])"><input name="ManageComName" elementtype=nacessary class="codename" readonly="readonly">

                         <input NAME="AgentManageCom"  verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">   
    
    	</td>
		</tr>
-->

		<tr class="common">

			<td class="title"> 银代网点 
    	</td>
			<td class="input" colspan="1">
			  <input name="BankCode1" class="codeno" ondblclick="showCodeList('BankCode',[this,BankCodeName,AgentCom],[0,1,2],null,fm.all('ManageCom').value,'ManageCom');" onkeyup="return showCodeListKey('BankCode',[this,BankCodeName,AgentCom],[0,1,2],null,fm.all('ManageCom').value,'ManageCom');" verify="代理机构|code:BankCode"><input name="BankCodeName" class="codename" readonly="readonly"><input name="AgentCom" class="codename" type=hidden > 
    	</td> 
			<td class="title">银行专管员 
      </td> 
			<td class="input" colspan="1">
			  <input name="AgentCode" class="codeno" ondblclick="showCodeList('BankAgentCode',[this,AgentName],[0,1],null,fm.all('AgentCom').value, 'a.AgentCom','1');" onkeyup="return showCodeListKey('BankAgentCode',[this,AgentName],[0,1],null,fm.all('AgentCom').value, 'a.AgentCom','1');"><input name="AgentName" class="codename" readonly="readonly">
      </td>   
      <td CLASS="title">营业部、营业组 
    	</td> 
			<td CLASS="input" COLSPAN="1">  
			  <input NAME="BranchAttr"  verifyorder="1" VALUE CLASS="common" readonly="readonly"  elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
    	  <input NAME="AgentGroup" class="common" value="" readonly="readonly"  type="hidden"  >
    	</td>
    </tr>
      
    	<tr class="common" style="display:">
 
			<td class="title">柜员代码 
    	</td>
			<td class="input" colspan="1">
			  <input name="CounterCode" class="common" > 
    	</td>
    	
      <td class="title">银行代码 
    	</td>
			<td class="input" colspan="1">
			  <input name="AgentBankCode" class="common"> 
    	</td>   
    	   
     </tr>    
    
<!--
			<td CLASS="title">营业部、营业组 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="BranchAttr"  verifyorder="1" VALUE CLASS="common" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
    	  <input NAME="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">
        星级业务员
      </td>
      <td class="input">
        <input class="codeno" readonly="readonly" name="starAgent"><input class="codename" name="starAgentName" readonly="readonly">
      </td>

      <td class="title">多业务员，请勾选
      </td>
      <td class="title">
        <input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();">
      </td>
-->
		</tr>
  </table>
<hr>
<!--
		<tr class="common">
      <td class="title">
        不丧失价值选择	
      </td>
      <td class="input">
        <Input class= 'codeno' name=KeepValueFlag ondblclick="showCodeList('KeepValue',[this,KeepValueFlagName],[0,1])" onkeyup="return showCodeListKey('KeepValue',[this,KeepValueFlagName],[0,1])" ><input name="KeepValueFlagName" class="codename" readonly="readonly">
      </td>
		</tr>
    -->

<!--
<Div  id= "divLCCont1" style= "display: 'none'">
  <table  class= common align='center' >
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
  </table>
</Div>
-->
