<%
//�������ƣ�DirectContInfoPage.jsp
//�����ܣ�ֱ������¼���ͬ��Ϣҳ��
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script lanaguage="javascript">

</script>

<Table id="">
	<TR>
		<TD class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCContInfo);"></TD>
		<TD class="titleImg normal">��ͬ��Ϣ</TD>
	</TR>
</Table>

<Div id="DivLCContInfo" class="maxbox" style="display: ">
	
	<Table class="common">
		<TR class="common">
			<TD class="title">Ͷ������</TD>
			<TD class="input">
				<input name="ProposalContNo" id=ProposalContNo class="common wid" verifyorder="1" verify="Ͷ������|notnull&amp;len=14" readonly >
				<input name="PrtNo" type="hidden" class="common wid" elementtype=nacessary>
			</TD>
			<TD class="title">Ͷ������</TD>
			<TD class="input"><input class="common wid" name="PolApplyDate" id="PolApplyDate" elementtype=nacessary onblur="checkapplydate();"  verifyorder="1" verify="Ͷ������|notnull&DATE"  ></TD>
			<TD class="title">��Ч����</TD>
			<TD class="input"><input class="common wid" name="CValiDate" id="CValiDate" readonly ></TD>      
		</TR>
		<TR class="common">   
			<TD class="title">���۷�ʽ</TD>
			<TD class="input" >
				<input class="codeno" name="SellType" id=SellType verifyorder="1"  verify="���۷�ʽ|code:sellType&amp;notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="showCodeList('sellType',[this,SellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,SellTypeName],[0,1])"><input name="SellTypeName" class="codename" readonly="readonly" elementtype='nacessary'>
				<input type="hidden" name="SaleChnl" id=SaleChnl value="" >
			</TD>      
			<TD class="title">������� </TD>
			<TD class="input" >
				<input name="ManageCom" id="ManageCom"  verifyorder="1" verify="�������|notnull" class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" >
				<input name="ManageComName" id=ManageComName elementtype=nacessary class="codename" readonly="readonly">
			</TD>	
			<TD class="title">�߶����ʶ </TD>
			<TD>
				<input class="codeno" readonly="readonly" name="highAmntFlag" id=highAmntFlag CodeData="0|^1|�� ^2|��" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" >
				<input name="highAmntFlagName" id=highAmntFlagName class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">����Ա����</TD>
			<TD class="input" >
				<input name="AgentCode" id=AgentCode class="code"  verify="ҵ��Ա����|notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onblur="queryAgent();" ondblclick="queryAgent();" >
			</TD>
			<TD class="title">����Ա����</TD>
			<TD class="input" >
				<input name="AgentName" id=AgentName class="common wid" readonly  verify="ҵ��Ա����|notnull"  >
			</TD>
			<TD class="title">����Ա��������</TD>
			<TD class="input" >
				<input name="AgentManageCom" id=AgentManageCom  verify="��������|notnull" readonly class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" >
				<input name="AgentManageComName" id=AgentManageComName class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">����ԱӪҵ����Ӫҵ��</TD>
			<TD class="input" >
				<input name="AgentBranchAttr" id=AgentBranchAttr class="common wid" readonly  verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
				<input name="AgentGroup" id=AgentGroup class="common wid" type="hidden" >
			</TD>
			<TD class="title">�Ǽ�ҵ��Ա</TD>
			<TD class="input">
				<input class="codeno" readonly="" name="starAgent" id=starAgent>
				<input class="codename" name="starAgentName" id=starAgentName readonly="readonly">
			</TD>
			<TD class="title"></TD> <!--ҵ��ԱӶ����� -->
			<TD class="input"><input name="AgentBusiRate" id=AgentBusiRate type="hidden" class="common wid"  value="0.00"></TD>
		</TR>
		<TR class="common">
			<TD class="title">����Ա����</TD>
			<TD class="input" >
				<input name="TelephonistCode" id=TelephonistCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code"  verify="����Ա����|notnull" onblur="queryTelephonist();" ondblclick="queryTelephonist();">
			</TD>
			<TD class="title">����Ա����</TD>
			<TD class="input" >
				<input name="TelephonistName" id=TelephonistName  readonly  class="common wid"  verify="����Ա����|notnull">
			</TD>
			<TD class="title">����Ա��������</TD>
			<TD class="input" >
				<input name="TelephManageCom" id=TelephManageCom  class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,TelephManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,TelephManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" verify="��������|notnull">
				<input name="TelephManageComName" id=TelephManageComName class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">����ԱӪҵ����Ӫҵ��</TD>
			<TD class="input" >
				<input name="TelephBranchAttr" readonly  class="common wid"  verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
				<input name="TelephGroup" type="hidden" value="">
			</TD>
			<TD class="title">�Ǽ�ҵ��Ա</TD>
			<TD class="input">
				<input class="codeno" readonly="readonly" name="StarTelephonist" id=StarTelephonist>
				<input class="codename" name="StarTelephonistName" id=StarTelephonistName readonly="readonly">
			</TD>
			<TD class="title"></TD> <!--����ԱӶ����� -->
			<TD class="input"><input name="TeleBusiRate" id=TeleBusiRate type="hidden" class="common wid"  value="0.00"></TD>
		</TR>
		<TR class="common">
			<!-------
			<TD class="title">ֱ������</TD>
			<TD class="input">
				<input class="codeno" name="DirectStyle" ondblclick="showCodeList('directstyle',[this,DirectStyleName],[0,1]);" onkeyup="return showCodeListKey('directstyle',[this,DirectStyleName],[0,1]);" verify="ֱ������|code:directstyle&notnull"><input class="codename" name="DirectStyleName" readonly="readonly">
			</TD>
			-------->
			<TD class="title">ֱ������</TD>
			<TD class="input">
				<input class="codeno" name="CSplit" id=CSplit verifyorder="1" verify="ֱ������|CODE:commisionratio&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('commisionratio',[this,CSplitName],[0,1]);" onkeyup="return showCodeListKey('commisionratio',[this,CSplitName],[0,1]);" verify="Ӷ���ֱ���|code:commisionratio&notnull">
				<input class="codename" name="CSplitName" id=CSplitName elementtype=nacessary readonly="readonly">
			</TD>
			<TD class="title"></TD>
			<TD class="input"></TD>
			<TD class="title"></TD>
			<TD class="input"></TD>
		</TR>
	</Table>		
</Div>	
