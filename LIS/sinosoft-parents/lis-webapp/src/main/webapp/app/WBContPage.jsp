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
<div id=DivLCContButton style="display:''">
	<table id="table1">
		<tr>
			<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
			<td class="titleImg">��ͬ��Ϣ
			<!-- 	<INPUT VALUE="��ѯ������Ϣ" TYPE=button onclick="showDuty();">
          		<INPUT VALUE="�����ݽ�����Ϣ" TYPE=button onclick="showFee();">  -->
			</td>
		</tr>
	</table>
</div>


<div id="DivLCCont" class="maxbox" STYLE="display:''">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title">ӡˢ�� </td>
			<td CLASS="input" COLSPAN="1">				
				<input name="PrtNo" id="PrtNo"  class="common wid" elementtype=nacessary tabindex="-1" maxlength="9" verify="ӡˢ����|notnull&amp;len=14" readonly>
			</td>
			<td class="title">Ͷ������</td>
			<td class="input"><input class="common wid" dateFormat="short" elementtype=nacessary onblur="initialAge();checkapplydate();" name="PolApplyDate" id="PolApplyDate" verify="Ͷ������|notnull&DATE" verifyorder="1" ></td>
			<td CLASS="title">ɨ����� </td>
			<td CLASS="input">
				<input NAME="ManageCom" id="ManageCom" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " MAXLENGTH="10" CLASS="codeno" onclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=6','1',1);" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=6','1',1);" onkeyup="showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and char_length(trim(comcode))=6','1',1);" verify="ɨ�����|notnull" ><input NAME="ManageComName" id="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
			</td>	
		</tr>
		<tr class="common">   
			<td class="title" style="display:none">���۷�ʽ</td>
			<td class="input" style="display:none">
				<input class="codeno" name="SellType" id="SellType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="showCodeList('sellType',[this,sellTypeName],[0,1])"   ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input name="sellTypeName" id="sellTypeName" class="codename" readonly="readonly"><!-- verify="���۷�ʽ|notnull" verifyorder="1"-->
				<!--input type="hidden" name="SaleChnl" value="02" elementtype='nacessary' -->
			</td>    
			
			<td CLASS="title" style="display:none">�߶����ʶ </td>
			<td class="input" style="display:none">
				<input class="codeno" readonly="readonly" name="highAmntFlag" id="highAmntFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|�� ^2|��" onclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" id="highAmntFlagName" class="codename" readonly="readonly">
			</td>
		</tr>
	</table>

	<table class="common">
		<tr class="common">
			<TD  class= title>�������</TD>
			<TD  class= input>
			  <Input name="AgentCom" id="AgentCom" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " class='codeno' verifyorder="3" verify="�������|code:AgentCom" onclick="return getClickAgentCom(AgentCom,InputAgentComName);" ondblclick="return getClickAgentCom(AgentCom,InputAgentComName);"  onkeyup="return getClickUpAgentCom(AgentCom,InputAgentComName);"><input name=InputAgentComName id="InputAgentComName" class='codename' readonly=true >
			</TD>
			<td class="title">�����˱��� </td>
			<td class="input" COLSPAN="1">
				<input NAME="AgentCode" id="AgentCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " VALUE="" MAXLENGTH="10" CLASS="codeno" verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" onclick="return queryAgent();" ondblclick="return queryAgent();" ><input name=AgentName id="AgentName" class='codename' readonly=true  elementtype=nacessary >
			</td> 			
			<td CLASS="title">������� </td>
			<td CLASS="input" COLSPAN="1">
				<input NAME="AgentManageCom" id="AgentManageCom" readonly  MAXLENGTH="10" CLASS="codeno" ><input NAME="AgentManageComName" id="AgentManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
			</td>			 	
		</tr>
		<tr class="common">	
			<td CLASS="title">��������� </td>
			<td CLASS="input" COLSPAN="1">
				<input NAME="BranchAttr" id="BranchAttr" type="hidden" verifyorder="1" VALUE CLASS="codeno" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" ><!-- verify="ҵ��ԱӪҵ����Ӫҵ��|notnull"> -->
				<input NAME="AgentGroup" id="AgentGroup" readonly CLASS="common wid" value="">
			</td>		
			<td class="title" style="display:none">�Ǽ�ҵ��Ա</td>
			<td class="input" style="display:none">
				<input class="codeno" readonly="readonly" name="starAgent" id="starAgent"><input class="codename" name="starAgentName" id="starAgentName" readonly="readonly">
			</td>
			<td class="title" style="display:none">��Ͷ���˹�ϵ</td>
			<td class="input" style="display:none">
				<input class="codeno" name="RelationShip" id="RelationShip" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="showCodeList('agentrelatoappnt',[this,RelationShipName],[0,1],null,null,null,null,'150');" ondblclick="showCodeList('agentrelatoappnt',[this,RelationShipName],[0,1],null,null,null,null,'150');" onkeyup="showCodeListKey('agentrelatoappnt',[this,RelationShipName],[0,1],null,null,null,null,'150');" ><input class="codename" name="RelationShipName" id="RelationShipName" readonly="readonly">
			</td>
			<td class="title" style="display:">��������</td>
			<td class="input" style="display:">
				<input class="codeno" name="SaleChnl" id="SaleChnl" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  verify="��������|notnull&CODE:salechnl"  verifyorder="1"  onclick="return showCodeList('salechnl',[this,SaleChnlName],[0,1],null,null,null,1);" ondblclick="return showCodeList('salechnl',[this,SaleChnlName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('salechnl',[this,SaleChnlName],[0,1],null,null,null,1);" ><input class="codename" name="SaleChnlName" id="SaleChnlName" readonly="readonly">
			</td>
			<td class="title" style="display:">����Աǩ��</td>
			<td class="input" style="display:">
				<input class="common wid" name="SignName" id="SignName" verify="����Աǩ��|notnull"  verifyorder="1" >
			</td>
			<tr class="common">
				<td class="title" style="display:">��������</td>
				<td class="input" style="display:">
					<input class="common wid" name="FirstTrialDate" id="FirstTrialDate" onblur = "checkFirstTrialDate();"  verify="��������|null#DATE"  verifyorder="1" >
				</td>
				<td class="title" >���ڽɷ���ʾ</td>
				<td class="input" >
					<input class="codeno" name="XQremindFlag" id="XQremindFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  CodeData="0|^1|�� ^0|��" onclick="showCodeListEx('XQremindFlag',[this,XQremindFlagName],[0,1])" ondblclick="showCodeListEx('XQremindFlag',[this,XQremindFlagName],[0,1])" onkeyup="return showCodeListKeyEx('XQremindFlag',[this,XQremindFlagName],[0,1])" ><input name="XQremindFlagName" id="XQremindFlagName" class="codename" readonly="readonly">
				</td>
		  </tr>
			<!-- 
			<td CLASS="title">ɨ����� </td>
			<td CLASS="input" COLSPAN="1">
				<input NAME="ScanManageCom" readonly  MAXLENGTH="10" CLASS="codeno" ><input NAME="ScanManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
			</td>
			-->
		</tr>
		</table>
	<table class="common">
		<tr class="common">
			<td class="title" style="display:''">��ע:</td>
		</tr>
		<tr>
	    <TD  class= input>
        	<textarea name="Remark" id="Remark" cols="100" rows="2" witdh=25% class="common"></textarea>
        </TD>
	  	</tr>
	</table>
		
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
        <Input type="hidden" class= 'common wid' name=CValiDate id="CValiDate" >
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
