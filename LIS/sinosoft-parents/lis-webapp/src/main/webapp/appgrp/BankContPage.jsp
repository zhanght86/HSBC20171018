<div id=DivLCContButton style="display:''">
  <table id="table1">
  	<tr>
  		<td>
  		  <img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
  		</td>
  		<td class="titleImg">��ͬ��Ϣ
  		</td>
  	</tr>
  </table>
</div>


<div id="DivLCCont" STYLE="display:''">
	<table class="common" id="table2"> 

		<tr CLASS="common">
			<td CLASS="title">Ͷ������  
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="ProposalContNo" VALUE CLASS="common" readonly TABINDEX="-1" MAXLENGTH="20">
			  <input name="PrtNo" type="hidden" class="common" elementtype=nacessary tabindex="-1" maxlength="9" verify="ӡˢ����|notnull&amp;len=9">
    	</td>
      <td class="title">Ͷ������</td>
      <td class="input">
        <input class="common" dateFormat="short" elementtype=nacessary onblur="checkapplydate();" name="PolAppntDate" verify="Ͷ������|notnull&DATE" verifyorder="1" >
      </td> 
      <td class="title">���۷�ʽ</td>
������<td class="input">
        <input class="codeno" name="SellType" verify="���۷�ʽ|notnull" verifyorder="1"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input name="sellTypeName" class="codename" readonly="readonly">
        <input type="hidden" name="SaleChnl" value="02" elementtype='nacessary'>
      </td>      
<!--
			<td CLASS="title">ӡˢ���� 
    	</td>
			<td class="input" colspan="1">
    	</td>
-->
		</tr>

    <tr class="common">
    	
<!--
			<td CLASS="title">�������� 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="SaleChnl" VALUE MAXLENGTH="2" CLASS="codeno" elementtype=nacessary ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input NAME="SaleChnlName" CLASS="codename" readonly="readonly">
    	</td>
-->  

			<td CLASS="title">������� 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="ManageCom"  verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:comcode&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
    	</td>
      <td class="title">
        �߶����ʶ
      </td>
      <td>
        <input class="codeno" readonly="readonly" name="highAmntFlag" CodeData="0|^1|�� ^2|��" aondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" class="codename" readonly="readonly">
      </td>
    </tr>

	</table>

  <table class="common">
<!--
  		<tr class="common"> 
			<td class="title">ҵ��Ա���� 
    	</td>
			<td class="input" COLSPAN="1">
			  <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" onkeyup="return queryAgent();" ondblclick="return queryAgent();" >
      </td>
			<td class="title">ҵ��Ա����
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary  verifyorder="1" >
      </td>
			<td CLASS="title">�������� 
    	</td>
			<td CLASS="input" COLSPAN="1"> 

			  <input NAME="AgentManageCom"  verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" verify="��������|code:station&amp;notnull"><input NAME="AgentManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
			  
                       <input class="codeno" name="AgentManageCom" verify="��������|notnull" verifyorder="1"  ondblclick="showCodeList('comcode',[this,ManageComName],[0,1])" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1])"><input name="ManageComName" elementtype=nacessary class="codename" readonly="readonly">

                         <input NAME="AgentManageCom"  verifyorder="1" readonly VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">   
    
    	</td>
		</tr>
-->

		<tr class="common">

			<td class="title"> �������� 
    	</td>
			<td class="input" colspan="1">
			  <input name="BankCode1" class="codeno" ondblclick="showCodeList('BankCode',[this,BankCodeName,AgentCom],[0,1,2],null,fm.all('ManageCom').value,'ManageCom');" onkeyup="return showCodeListKey('BankCode',[this,BankCodeName,AgentCom],[0,1,2],null,fm.all('ManageCom').value,'ManageCom');" verify="�������|code:BankCode"><input name="BankCodeName" class="codename" readonly="readonly"><input name="AgentCom" class="codename" type=hidden > 
    	</td> 
			<td class="title">����ר��Ա 
      </td> 
			<td class="input" colspan="1">
			  <input name="AgentCode" class="codeno" ondblclick="showCodeList('BankAgentCode',[this,AgentName],[0,1],null,fm.all('AgentCom').value, 'a.AgentCom','1');" onkeyup="return showCodeListKey('BankAgentCode',[this,AgentName],[0,1],null,fm.all('AgentCom').value, 'a.AgentCom','1');"><input name="AgentName" class="codename" readonly="readonly">
      </td>   
      <td CLASS="title">Ӫҵ����Ӫҵ�� 
    	</td> 
			<td CLASS="input" COLSPAN="1">  
			  <input NAME="BranchAttr"  verifyorder="1" VALUE CLASS="common" readonly="readonly"  elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
    	  <input NAME="AgentGroup" class="common" value="" readonly="readonly"  type="hidden"  >
    	</td>
    </tr>
      
    	<tr class="common" style="display:">
 
			<td class="title">��Ա���� 
    	</td>
			<td class="input" colspan="1">
			  <input name="CounterCode" class="common" > 
    	</td>
    	
      <td class="title">���д��� 
    	</td>
			<td class="input" colspan="1">
			  <input name="AgentBankCode" class="common"> 
    	</td>   
    	   
     </tr>    
    
<!--
			<td CLASS="title">Ӫҵ����Ӫҵ�� 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="BranchAttr"  verifyorder="1" VALUE CLASS="common" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
    	  <input NAME="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">
        �Ǽ�ҵ��Ա
      </td>
      <td class="input">
        <input class="codeno" readonly="readonly" name="starAgent"><input class="codename" name="starAgentName" readonly="readonly">
      </td>

      <td class="title">��ҵ��Ա���빴ѡ
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
      <TD  class= input>
        <Input class= 'common' name=CValiDate >
      </TD>
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
