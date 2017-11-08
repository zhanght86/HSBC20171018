<Div id=DivLCContButton style="display: ">
	<Table id="table1">
		<TR>
			<TD class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></TD>
			<TD class="titleImg">合同信息</TD>
		</TR>
	</Table>
</Div>


<Div id="DivLCCont" class=maxbox STYLE="display: ">
	<Table class="common" id="table2">
		<TR CLASS="common">
			<TD CLASS="title">卡号 </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="ContCardNo" id=ContCardNo CLASS="common wid"  MAXLENGTH="20" onblur="confirmSecondInput(this,'onblur');"  elementtype=nacessary verify="卡号|notnull" verifyorder="1">
			</TD>
			<TD class="title">投保日期</TD>
			<TD class="input">
				<input class="common wid" dateFormat="short" elementtype=nacessary onblur="checkapplydate();" name="PolAppntDate" id=PolAppntDate verify="投保日期|notnull&DATE" verifyorder="1" onClick="laydate({elem: '#PolAppntDate'});" >
				<span class="icon"><a onClick="laydate({elem: '#PolAppntDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD CLASS="title">投保单号 </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="ProposalContNo" id=ProposalContNo CLASS="common wid"  MAXLENGTH="14" onblur="confirmSecondInput(this,'onblur');" elementtype=nacessary verify="投保单号|notnull&num&len=14" verifyorder="1">
				<input name="PrtNo" id=PrtNo type="hidden" class="common wid" tabindex="-1" maxlength="9" verify="印刷号码|notnull&amp;len=9">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">销售方式</TD>
			<TD class="input">
				<input class="codeno" value="01" name="SellType" id=SellType verify="销售方式|notnull" verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])">
				<input name="sellTypeName" id=sellTypeName class="codename" readonly="readonly" value="业务员" elementtype='nacessary'>
				<input type="hidden" name="SaleChnl" id=SaleChnl value="1" >
			</TD>      
			<TD CLASS="title">管理机构 </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id=ManageCom  verifyorder="1"  VALUE MAXLENGTH="10" CLASS="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:comcode&amp;notnull">
				<input NAME="ManageComName" id=ManageComName elementtype=nacessary CLASS="codename" readonly="readonly">
			</TD>
			<TD class="title">业务员代码 </TD>
			<TD class="input" COLSPAN="1">
				<input NAME="AgentCode" id=AgentCode VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" onblur="queryAgent();" ondblclick="queryAgent();" >
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">业务员姓名</TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="AgentName" id=AgentName readonly VALUE="" MAXLENGTH="10" class="common wid" elementtype=nacessary  verifyorder="1" >
			</TD>
			<TD CLASS="title">所属机构 </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="AgentManageCom" id=AgentManageCom verifyorder="1"  VALUE MAXLENGTH="10" CLASS="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" verify="所属机构|code:comcode&amp;notnull">
				<input NAME="AgentManageComName" id=AgentManageComName elementtype=nacessary CLASS="codename" readonly="readonly">
			</TD>
			<TD CLASS="title">营业部、营业组 </TD>
			<TD CLASS="input" COLSPAN="1">
				<input NAME="BranchAttr" id=BranchAttr  verifyorder="1" VALUE class="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="业务员营业部、营业组|notnull">
				<input NAME="AgentGroup" id=AgentGroup type="hidden" value="">
			</TD>
		</TR>	
	</Table>
</Div>

		
<Div  id= "divLCAppnt1" style= "display: none">
	<Table  class= common align='center' >
		<TR>
			<TD class="title">高额件标识</TD>
			<TD class=input>
				<input class="codeno" readonly="readonly wid" name="highAmntFlag" id=highAmntFlag CodeData="0|^1|是 ^2|否" aondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" ><input name="highAmntFlagName" class="codename" readonly="readonly">
			</TD>
			<TD class="title">星级业务员</TD>
			<TD class="input">
				<input class="codeno" readonly="readonly wid" name="starAgent" id=starAgent>
				<input class="codename" name="starAgentName" id=starAgentName readonly="readonly">
			</TD>
			<TD class="title"></TD>
			<TD class="input"></TD>
		</TR>
	</Table>
</Div>
