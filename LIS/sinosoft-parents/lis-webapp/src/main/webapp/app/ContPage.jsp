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
/*
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
*/
function getClickAgentCom(AgentCom,InputAgentComName)
{
   var tManageCom =  document.all('ManageCom').value;
    var sql_managecom = "";
    if(tManageCom.length<=4)
    {
      sql_managecom = "1  and #1#=#1# and managecom like #"+tManageCom+"%%# ";
    }
    else
    {
      sql_managecom = "1   and #1#=#1# and managecom like concat(substr(#"+tManageCom+"#,1,4),#%%#)  ";
    }
   //alert(tManageCom.length);
   //alert(sql_managecom);
   showCodeList('AgentCom',[AgentCom,InputAgentComName],[0,1],null,sql_managecom,"1",'1',300);
}

function getClickUpAgentCom(AgentCom,InputAgentComName)
{
   var tManageCom =  document.all('ManageCom').value;
   var sql_managecom = "";
    if(tManageCom.length<=4)
    {
      sql_managecom = "1  and #1#=#1# and managecom like #"+tManageCom+"%%# ";
    }
    else
    {
      sql_managecom = "1  and #1#=#1# and managecom like concat(substr(#"+tManageCom+"#,1,4),#%%#)  ";
    }
   showCodeListKey('AgentCom',[AgentCom,InputAgentComName],[0,1],null,sql_managecom,"1",'1');
}

</script>
<div id="DivLCContButton" style="display:''">
	<table id="table1">
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td><td class="titleImg">��ͬ��Ϣ</td>
		</tr>
	</table>
</div>
<div class="maxbox">
<div id="DivLCCont" STYLE="display:''">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title">ӡˢ�� </td>
			<td CLASS="input" COLSPAN="1">
				<input NAME="ProposalContNo" id="ProposalContNo" VALUE CLASS="common wid" readonly TABINDEX="-1" MAXLENGTH="20">
				<input name="PrtNo" id="PrtNo" type="hidden" class="common" elementtype=nacessary tabindex="-1" maxlength="9" verify="ӡˢ����|notnull&amp;len=9">
				<font color="red">*</font>
			</td>
			<td class="title">Ͷ������</td>
			<td class="input">
			    <Input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur="checkapplydate();" onClick="laydate({elem: '#PolAppntDate'});" verify="Ͷ������|notnull&DATE" verifyorder="1"  name=PolAppntDate id="PolAppntDate"><span class="icon"><a onClick="laydate({elem: '#PolAppntDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			    <font color="red">*</font>
			</td>
			<td CLASS="title">������� </td>
			<td CLASS="input">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" NAME="ManageCom" id="ManageCom" verifyorder="1" MAXLENGTH="10" CLASS="codeno" onclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=8','1',1);" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=8','1',1);" onkeyup="showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=8','1',1);" verify="�������|notnull"><input NAME="ManageComName" id="ManageComName" elementtype=nacessary CLASS="codename" readonly>
				<font color="red">*</font>
			</td>	
		</tr>
		<tr class="common">   
			<td class="title" style="display:none">���۷�ʽ</td>
			<td class="input" style="display:none">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="SellType" id="SellType" verify="���۷�ʽ|notnull" verifyorder="1"  onclick="showCodeList('sellType',[this,sellTypeName],[0,1])" ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input name="sellTypeName" id="sellTypeName" class="codename" readonly>
				<!--input type="hidden" name="SaleChnl" value="02" elementtype='nacessary' -->
				<font color="red">*</font>
			</td>    
			
			<td CLASS="title" style="display:none">�߶����ʶ </td>
			<td style="display:none">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" readonly name="highAmntFlag" id="highAmntFlag" CodeData="0|^1|�� ^2|��" onclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" id="highAmntFlagName" class="codename" readonly>
				<font color="red">*</font>
			</td>
		</tr>
	</table>

	<table class="common">
		<tr class="common">
			<TD  class= title>�������</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" name="AgentCom" id="AgentCom" class='codeno' verifyorder="3" verify="�������|code:AgentCom&notnull" onclick="return getClickAgentCom(this,'InputAgentComName');" ondblclick="return getClickAgentCom(this,'InputAgentComName');" onkeyup="return getClickUpAgentCom(this,'InputAgentComName');"><input name=InputAgentComName id="InputAgentComName" class='codename' readonly=true >
			</TD>
			<td class="title">�����˱��� </td>
			<td class="input" COLSPAN="1">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" NAME="AgentCode" id="AgentCode" VALUE="" MAXLENGTH="10" CLASS="codeno" verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" onclick="return queryAgent();" ondblclick="return queryAgent();"><input name=AgentName id="AgentName" class='codename' readonly=true  elementtype=nacessary >
				<font color="red">*</font>
			</td>
			<td CLASS="title">�������� </td>
			<td CLASS="input" COLSPAN="1">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" NAME="AgentManageCom" id="AgentManageCom" verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" onclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=8','1',1,'240');" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=8','1',1,'240');" verify="��������|notnull"><input NAME="AgentManageComName" id="AgentManageComName" elementtype=nacessary CLASS="codename" readonly>
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="common">
			<td CLASS="title">��������� </td>
			<td CLASS="input" COLSPAN="1">
				<input NAME="BranchAttr" id="BranchAttr" verifyorder="1" VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
				<input NAME="AgentGroup" id="AgentGroup" type="hidden" value="">
				<font color="red">*</font>
			</td>
			<td class="title" style="display:none">�Ǽ�ҵ��Ա</td>
			<td class="input" style="display:none">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" readonly name="starAgent" id="starAgent"><input class="codename" name="starAgentName" id="starAgentName" readonly>
				<font color="red">*</font>
			</td>
			<td class="title" style="display:none">��Ͷ���˹�ϵ</td>
			<td class="input" style="display:none">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="RelationShip" id="RelationShip" onclick="showCodeList('agentrelatoappnt',[this,RelationShipName],[0,1],null,null,null,null,'150');" ondblclick="showCodeList('agentrelatoappnt',[this,RelationShipName],[0,1],null,null,null,null,'150');" onkeyup="showCodeListKey('agentrelatoappnt',[this,RelationShipName],[0,1],null,null,null,null,'150');" ><input class="codename" name="RelationShipName" id="RelationShipName" readonly>
				<font color="red">*</font>
			</td> 
			<td class="title" style="display:''">��������</td>
			<td class="input" style="display:''">
				<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="SaleChnl" id="SaleChnl" verify="��������|notnull&CODE:salechnl"  verifyorder="1"  onclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" ondblclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" onkeyup="showCodeListKey('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" ><input class="codename" name="SaleChnlName" id="SaleChnlName" readonly elementtype=nacessary>
				<font color="red">*</font>
			</td>
			<td class="title" style="display:''">����Աǩ��</td>
			<td class="input" style="display:''">
				<input class="common wid" name="SignName" id="SignName" verify="����Աǩ��|notnull"  verifyorder="1" elementtype=nacessary>
				<font color="red">*</font>
			</td>
		</tr>
    <tr class="common">
			<td class="title" style="display:''">��������</td>
			<td class="input" style="display:''">
        <Input class="coolDatePicker" onClick="laydate({elem: '#FirstTrialDate'});" onblur="checkFirstTrialDate();" verify="��������|notnull&DATE"  verifyorder="1" elementtype=nacessary dateFormat="short" name=FirstTrialDate id="FirstTrialDate"><span class="icon"><a onClick="laydate({elem: '#FirstTrialDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</td> 
			<td class="title" >���ڽɷ���ʾ</td>
				<td class="input" >
					<input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name="XQremindFlag" id="XQremindFlag" CodeData="0|^1|�� ^0|��" onclick="showCodeListEx('XQremindFlag',[this,XQremindFlagName],[0,1])" ondblclick="showCodeListEx('XQremindFlag',[this,XQremindFlagName],[0,1])" onkeyup="return showCodeListKeyEx('XQremindFlag',[this,XQremindFlagName],[0,1])" ><input name="XQremindFlagName" id="XQremindFlagName" class="codename"  readonly="readonly">
			</td>
		</tr>
	</table>
	<table class="common">
		<tr class="common">
			<td class="title" style="display:''">��ע:</td>
		</tr>
		<tr>
	    <TD  colspan="6" style="padding-left:16px">
        	<textarea name="Remark" id="Remark" cols="226" rows="4" witdh=25% class="common"></textarea>
        </TD>
	  	</tr>
	</table>
</div>	
</div>	
<!--
		<tr class="common">
      <td class="title">
        ��ɥʧ��ֵѡ��	
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
        �����ͬ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GrpContNo >
      </TD>
      <TD  class= title>
        ��ͬ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ContNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �ܵ�Ͷ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ProposalContNo >
      </TD>
      <TD  class= title>
        ӡˢ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PrtNo >
      </TD>
    </TR>
    
    <TR  class= common>
      <TD  class= title>
        �ܵ�����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ContType >
      </TD>
      <TD  class= title>
        ��ͥ������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=FamilyType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ��ͥ���Ϻ�
      </TD>
      <TD  class= input>
        <Input class= 'common' name=FamilyID >
      </TD>
      <TD  class= title>
        �������ͱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PolType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ������־
      </TD>
      <TD  class= input>
        <Input class= 'common' name=CardFlag >
      </TD>
     <TD  class= title>
        �������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ManageCom >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ExecuteCom >
      </TD>
     <TD  class= title>
        �������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentCom >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �����˱���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentCode >
      </TD>
      <TD  class= title>
        ���������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentGroup >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ���ϴ����˴���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentCode1 >
      </TD>
       <TD  class= title>
        ��������ڲ�����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AgentType >
      </TD>
    </TR>
    <TR  class= common>
     <TD  class= title>
        ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SaleChnl >
      </TD>
      <TD  class= title>
        ������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Handler >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Password >
      </TD>
     <TD  class= title>
        Ͷ���˿ͻ�����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        Ͷ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntName >
      </TD>
      <TD  class= title>
        Ͷ�����Ա�
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntSex >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        Ͷ���˳�������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntBirthday >
      </TD>
      <TD  class= title>
        Ͷ����֤������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntIDType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        Ͷ����֤������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppntIDNo >
      </TD>
      <TD  class= title>
        �����˿ͻ���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ����������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredName >
      </TD>
      <TD  class= title>
        �������Ա�
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredSex >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �����˳�������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredBirthday >
      </TD>
      <TD  class= title>
        ֤������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredIDType >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ֤������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InsuredIDNo >
      </TD>
      <TD  class= title>
        ���Ѽ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PayIntv >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ���ѷ�ʽ
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PayMode >
      </TD>
      <TD  class= title>
        ����λ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PayLocation >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ��ͬ���鴦��ʽ
      </TD>
      <TD  class= input>
        <Input class= 'common' name=DisputedFlag >
      </TD>
      <TD  class= title>
        �罻����ʽ
      </TD>
      <TD  class= input>
        <Input class= 'common' name=OutPayFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �����ʹ﷽ʽ
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GetPolMode >
      </TD>
      <TD  class= title>
        ǩ������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SignCom >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ǩ������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SignDate >
      </TD>
      <TD  class= title>
        ǩ��ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SignTime >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ����ί�������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ConsignNo >
      </TD>
      <TD  class= title>
        ���б���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=BankCode >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �����ʺ�
      </TD>
      <TD  class= input>
        <Input class= 'common' name=BankAccNo >
      </TD>
      <TD  class= title>
        �����ʻ���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AccName >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ������ӡ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PrintCount >
      </TD>
      <TD  class= title>
        ��ʧ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=LostTimes >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ���ֱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Lang >
      </TD>
      <TD  class= title>
        �ұ�
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Currency >
      </TD>
    </TR>
    <TR  class= common>
     TD  class= title>
        ��ע
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Remark >
      </TD>
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Peoples >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Mult >
      </TD>
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Prem >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Amnt >
      </TD>
      <TD  class= title>
        �ۼƱ���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=SumPrem >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Dif >
      </TD>
      <TD  class= title>
        ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PaytoDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ���ڽ�������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=FirstPayDate >
      </TD>
      <TD  class= title>
        ������Ч����
      </TD>
      -->
      <TD  class= input>
        <Input type="hidden" class= 'common' name=CValiDate >
      </TD>
      <!--  
    </TR>
    <TR  class= common>
      <TD  class= title>
        ¼����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InputOperator >
      </TD>
      <TD  class= title>
        ¼���������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InputDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ¼�����ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=InputTime >
      </TD>
      <TD  class= title>
        ����״̬
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �����˱���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveCode >
      </TD>
      <TD  class= title>
        ��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ����ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ApproveTime >
      </TD>
      <TD  class= title>
        �˱�״̬
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �˱���
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWOperator >
      </TD>
      <TD  class= title>
        �˱��������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �˱����ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=UWTime >
      </TD>
      <TD  class= title>
        Ͷ����/������־
      </TD>
      <TD  class= input>
        <Input class= 'common' name=AppFlag >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        Ͷ������������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=PolApplyDate >
      </TD>
      <TD  class= title>
        �����ʹ�����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GetPolDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �����ʹ�ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=GetPolTime >
      </TD>
      <TD  class= title>
        ������ִ�ͻ�ǩ������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=CustomGetPolDate >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ״̬
      </TD>
      <TD  class= input>
        <Input class= 'common' name=State >
      </TD>
      <TD  class= title>
        ����Ա
      </TD>
      <TD  class= input>
        <Input class= 'common' name=Operator >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        �������
      </TD>
      <TD  class= input>
        <Input class= 'common' name=MakeDate >
      </TD>
      <TD  class= title>
        ���ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=MakeTime >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
        ���һ���޸�����
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ModifyDate >
      </TD>
      <TD  class= title>
        ���һ���޸�ʱ��
      </TD>
      <TD  class= input>
        <Input class= 'common' name=ModifyTime >
      </TD>
    </TR>
  </table>
</Div>
-->
