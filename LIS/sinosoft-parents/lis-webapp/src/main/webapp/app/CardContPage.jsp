<Div id=DivLCContButton style="display: ">
	<Table id="table1">
		<TR>
			<TD class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></TD>
			<TD class="titleImg">��ͬ��Ϣ</TD>
		</TR>
	</Table>
</Div>


<Div id="DivLCCont" class=maxbox STYLE="display: ">
	<Table class="common" id="table2">
		<TR CLASS="common">
			<TD CLASS="title">���� </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="ContCardNo" id=ContCardNo CLASS="common wid"  MAXLENGTH="20" onblur="confirmSecondInput(this,'onblur');"  elementtype=nacessary verify="����|notnull" verifyorder="1">
			</TD>
			<TD class="title">Ͷ������</TD>
			<TD class="input">
				<input class="common wid" dateFormat="short" elementtype=nacessary onblur="checkapplydate();" name="PolAppntDate" id=PolAppntDate verify="Ͷ������|notnull&DATE" verifyorder="1" onClick="laydate({elem: '#PolAppntDate'});" >
				<span class="icon"><a onClick="laydate({elem: '#PolAppntDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD CLASS="title">Ͷ������ </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="ProposalContNo" id=ProposalContNo CLASS="common wid"  MAXLENGTH="14" onblur="confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ������|notnull&num&len=14" verifyorder="1">
				<input name="PrtNo" id=PrtNo type="hidden" class="common wid" tabindex="-1" maxlength="9" verify="ӡˢ����|notnull&amp;len=9">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">���۷�ʽ</TD>
			<TD class="input">
				<input class="codeno" value="01" name="SellType" id=SellType verify="���۷�ʽ|notnull" verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])">
				<input name="sellTypeName" id=sellTypeName class="codename" readonly="readonly" value="ҵ��Ա" elementtype='nacessary'>
				<input type="hidden" name="SaleChnl" id=SaleChnl value="1" >
			</TD>      
			<TD CLASS="title">������� </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id=ManageCom  verifyorder="1"  VALUE MAXLENGTH="10" CLASS="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:comcode&amp;notnull">
				<input NAME="ManageComName" id=ManageComName elementtype=nacessary CLASS="codename" readonly="readonly">
			</TD>
			<TD class="title">ҵ��Ա���� </TD>
			<TD class="input" COLSPAN="1">
				<input NAME="AgentCode" id=AgentCode VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" onblur="queryAgent();" ondblclick="queryAgent();" >
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">ҵ��Ա����</TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="AgentName" id=AgentName readonly VALUE="" MAXLENGTH="10" class="common wid" elementtype=nacessary  verifyorder="1" >
			</TD>
			<TD CLASS="title">�������� </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="AgentManageCom" id=AgentManageCom verifyorder="1"  VALUE MAXLENGTH="10" CLASS="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" verify="��������|code:comcode&amp;notnull">
				<input NAME="AgentManageComName" id=AgentManageComName elementtype=nacessary CLASS="codename" readonly="readonly">
			</TD>
			<TD CLASS="title">Ӫҵ����Ӫҵ�� </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="BranchAttr" id=BranchAttr  verifyorder="1" VALUE class="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
				<input NAME="AgentGroup" id=AgentGroup type="hidden" value="">
			</TD>
		</TR>	
	</Table>
</Div>

		
<Div  id= "divLCAppnt1" style= "display: none">
	<Table  class= common align='center' >
		<TR>
			<TD class="title">�߶����ʶ</TD>
			<TD class=input>
				<input class="codeno" readonly="readonly wid" name="highAmntFlag" id=highAmntFlag CodeData="0|^1|�� ^2|��" aondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" class="codename" readonly="readonly">
			</TD>
			<TD class="title">�Ǽ�ҵ��Ա</TD>
			<TD class="input">
				<input class="codeno" readonly="readonly wid" name="starAgent" id=starAgent>
				<input class="codename" name="starAgentName" id=starAgentName readonly="readonly">
			</TD>
			<TD class="title"></TD>
			<TD class="input"></TD>
		</TR>
	</Table>
</Div>
