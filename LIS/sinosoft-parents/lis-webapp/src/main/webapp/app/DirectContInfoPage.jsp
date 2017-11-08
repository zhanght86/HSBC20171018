<%
//程序名称：DirectContInfoPage.jsp
//程序功能：直销险种录入合同信息页面
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script lanaguage="javascript">

</script>

<Table id="">
	<TR>
		<TD class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCContInfo);"></TD>
		<TD class="titleImg normal">合同信息</TD>
	</TR>
</Table>

<Div id="DivLCContInfo" class="maxbox" style="display: ">
	
	<Table class="common">
		<TR class="common">
			<TD class="title">投保单号</TD>
			<TD class="input">
				<input name="ProposalContNo" id=ProposalContNo class="common wid" verifyorder="1" verify="投保单号|notnull&amp;len=14" readonly >
				<input name="PrtNo" type="hidden" class="common wid" elementtype=nacessary>
			</TD>
			<TD class="title">投保日期</TD>
			<TD class="input"><input class="common wid" name="PolApplyDate" id="PolApplyDate" elementtype=nacessary onblur="checkapplydate();"  verifyorder="1" verify="投保日期|notnull&DATE"  ></TD>
			<TD class="title">生效日期</TD>
			<TD class="input"><input class="common wid" name="CValiDate" id="CValiDate" readonly ></TD>      
		</TR>
		<TR class="common">   
			<TD class="title">销售方式</TD>
			<TD class="input" >
				<input class="codeno" name="SellType" id=SellType verifyorder="1"  verify="销售方式|code:sellType&amp;notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="showCodeList('sellType',[this,SellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,SellTypeName],[0,1])"><input name="SellTypeName" class="codename" readonly="readonly" elementtype='nacessary'>
				<input type="hidden" name="SaleChnl" id=SaleChnl value="" >
			</TD>      
			<TD class="title">管理机构 </TD>
			<TD class="input" >
				<input name="ManageCom" id="ManageCom"  verifyorder="1" verify="管理机构|notnull" class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" >
				<input name="ManageComName" id=ManageComName elementtype=nacessary class="codename" readonly="readonly">
			</TD>	
			<TD class="title">高额件标识 </TD>
			<TD>
				<input class="codeno" readonly="readonly" name="highAmntFlag" id=highAmntFlag CodeData="0|^1|是 ^2|否" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('highAmntFlag',[this,highAmntFlagName],[0,1])" onkeyup="return showCodeListKeyEx('highAmntFlag',[this,highAmntFlagName],[0,1])" >
				<input name="highAmntFlagName" id=highAmntFlagName class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">外务员代码</TD>
			<TD class="input" >
				<input name="AgentCode" id=AgentCode class="code"  verify="业务员代码|notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onblur="queryAgent();" ondblclick="queryAgent();" >
			</TD>
			<TD class="title">外务员姓名</TD>
			<TD class="input" >
				<input name="AgentName" id=AgentName class="common wid" readonly  verify="业务员姓名|notnull"  >
			</TD>
			<TD class="title">外务员所属机构</TD>
			<TD class="input" >
				<input name="AgentManageCom" id=AgentManageCom  verify="所属机构|notnull" readonly class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,AgentManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" >
				<input name="AgentManageComName" id=AgentManageComName class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">外务员营业部、营业组</TD>
			<TD class="input" >
				<input name="AgentBranchAttr" id=AgentBranchAttr class="common wid" readonly  verify="业务员营业部、营业组|notnull">
				<input name="AgentGroup" id=AgentGroup class="common wid" type="hidden" >
			</TD>
			<TD class="title">星级业务员</TD>
			<TD class="input">
				<input class="codeno" readonly="" name="starAgent" id=starAgent>
				<input class="codename" name="starAgentName" id=starAgentName readonly="readonly">
			</TD>
			<TD class="title"></TD> <!--业务员佣金比例 -->
			<TD class="input"><input name="AgentBusiRate" id=AgentBusiRate type="hidden" class="common wid"  value="0.00"></TD>
		</TR>
		<TR class="common">
			<TD class="title">话务员代码</TD>
			<TD class="input" >
				<input name="TelephonistCode" id=TelephonistCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code"  verify="话务员代码|notnull" onblur="queryTelephonist();" ondblclick="queryTelephonist();">
			</TD>
			<TD class="title">话务员姓名</TD>
			<TD class="input" >
				<input name="TelephonistName" id=TelephonistName  readonly  class="common wid"  verify="话务员姓名|notnull">
			</TD>
			<TD class="title">话务员所属机构</TD>
			<TD class="input" >
				<input name="TelephManageCom" id=TelephManageCom  class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('comcode',[this,TelephManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" onkeyup="return showCodeListKey('comcode',[this,TelephManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1,'240');" verify="所属机构|notnull">
				<input name="TelephManageComName" id=TelephManageComName class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">
			<TD class="title">话务员营业部、营业组</TD>
			<TD class="input" >
				<input name="TelephBranchAttr" readonly  class="common wid"  verify="业务员营业部、营业组|notnull">
				<input name="TelephGroup" type="hidden" value="">
			</TD>
			<TD class="title">星级业务员</TD>
			<TD class="input">
				<input class="codeno" readonly="readonly" name="StarTelephonist" id=StarTelephonist>
				<input class="codename" name="StarTelephonistName" id=StarTelephonistName readonly="readonly">
			</TD>
			<TD class="title"></TD> <!--话务员佣金比例 -->
			<TD class="input"><input name="TeleBusiRate" id=TeleBusiRate type="hidden" class="common wid"  value="0.00"></TD>
		</TR>
		<TR class="common">
			<!-------
			<TD class="title">直销类型</TD>
			<TD class="input">
				<input class="codeno" name="DirectStyle" ondblclick="showCodeList('directstyle',[this,DirectStyleName],[0,1]);" onkeyup="return showCodeListKey('directstyle',[this,DirectStyleName],[0,1]);" verify="直销类型|code:directstyle&notnull"><input class="codename" name="DirectStyleName" readonly="readonly">
			</TD>
			-------->
			<TD class="title">直销类型</TD>
			<TD class="input">
				<input class="codeno" name="CSplit" id=CSplit verifyorder="1" verify="直销类型|CODE:commisionratio&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('commisionratio',[this,CSplitName],[0,1]);" onkeyup="return showCodeListKey('commisionratio',[this,CSplitName],[0,1]);" verify="佣金拆分比例|code:commisionratio&notnull">
				<input class="codename" name="CSplitName" id=CSplitName elementtype=nacessary readonly="readonly">
			</TD>
			<TD class="title"></TD>
			<TD class="input"></TD>
			<TD class="title"></TD>
			<TD class="input"></TD>
		</TR>
	</Table>		
</Div>	
