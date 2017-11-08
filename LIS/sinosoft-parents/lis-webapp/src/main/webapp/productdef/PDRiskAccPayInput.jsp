<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//程序名称：PDRiskAccPayInput.jsp 
		//程序功能：账户型险种定义
		//创建日期：2009-3-14
		//创建人  ：
		//更新记录：  更新人    更新日期     更新原因/内容
	%>
	<%
		String mRiskCode = request.getParameter("riskcode");
	%>
	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="buttonshow.jsp"%>
		<SCRIPT src="PDRiskAccPay.js"></SCRIPT>
		<%@include file="PDRiskAccPayInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDRiskAccPaySave.jsp" method=post name=fm
			target="fraSubmit">
			<br>

			<table>
				<tr>
					<td class="titleImg">
险种下的账户明细
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline11Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
				onclick="Mulline11GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button
				onclick="Mulline11GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button
				onclick="Mulline11GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
				onclick="Mulline11GridTurnPage.lastPage();">
			</BR>
			</BR>
			<table>
				<tr>
					<td class="titleImg">
已保存的险种账户缴费
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline10Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
				onclick="Mulline10GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button
				onclick="Mulline10GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button
				onclick="Mulline10GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
				onclick="Mulline10GridTurnPage.lastPage();">
			</BR>
			</BR>
			<!--input value="帐户管理费定义" type=button  onclick="button184( )" class="cssButton" type="button" -->
			<br>
			<br>

			<table>
				<tr>
					<td class="titleImg">
险种账户缴费定义
					</td>
				</tr>
			</table>

			<table class=common>
				<tr class=common>
					<TD class=title5>
险种编码
					</TD>
					<TD class=input5>
						<Input class=readonly readonly="readonly"   name=RISKCODE  elementtype="nacessary">
					</TD>
					<TD class=title5>
缴费编码
					</TD>
					
					<TD class=input5>
						<Input class="codeno" name=PAYPLANCODE readonly="readonly"
							verify="缴费编码|NOTNUlL"  
							ondblclick="return showCodeList('pd_payplancode',[this,PAYPLANNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"
							onkeyup="return showCodeListKey('pd_payplancode',[this,PAYPLANNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=PAYPLANNAME readonly="readonly"  elementtype="nacessary">

					</TD>
					</tr>
					<tr class = common>
					<TD class=title5>
保险帐户号码
					</TD>
					<TD class=input5>
						<Input class="codeno" name=INSUACCNO readonly="readonly"
							verify="保险帐户号码|NOTNUlL"
							ondblclick="return showCodeList('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"
							onkeyup="return showCodeListKey('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=INSUACCNOName readonly="readonly"  elementtype="nacessary">
					</TD>
					<TD class=title5 STYLE="display:none;">
默认比例
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class=common verify="默认比例|num" name=DEFAULTRATE value="1">
					</TD>
				</tr>
				<tr class=common>
					<TD class=title5 STYLE="display:none;">
是否需要录入
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=NEEDINPUT value="0" readonly="readonly"
							verify="是否需要录入|NOTNUlL"
							ondblclick="return showCodeList('pd_zeroone',[this,NEEDINPUTName],[0,1]);"
							onkeyup="return showCodeListKey('pd_zeroone',[this,NEEDINPUTName],[0,1]);">
						<input class=codename name=NEEDINPUTName value="0" readonly="readonly">

					</TD>
					<TD class=title5 STYLE="display:none;">
账户产生位置
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=ACCCREATEPOS value="0" readonly="readonly"
							ondblclick="return showCodeList('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);"
							onkeyup="return showCodeListKey('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);">
						<input class=codename name=ACCCREATEPOSName value="投保单录入时产生"
							readonly="readonly">
					</TD>

					<TD class=title5 STYLE="display:none;">
转入账户时的算法编码
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class=common name=CALCODEMONEY value="0">
					</TD>

					<TD class=title5 STYLE="display:none;">
账户转入计算标志
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=CALFLAG value="0" readonly="readonly"
							ondblclick="return showCodeList('pd_calflag',[this,CALFLAGName],[0,1]);"
							onkeyup="return showCodeListKey('pd_calflag',[this,CALFLAGName],[0,1]);">
						<input class=codename value="完全转入账户" name=CALFLAGName
							readonly="readonly">
					</TD>
				</tr>
				<tr class=common>
					<TD class=title5 STYLE="display:none;">
账户交费转入位置
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=PAYNEEDTOACC value="9" readonly="readonly"
							ondblclick="return showCodeList('pd_zeroone',[this,PAYNEEDTOACCName],[0,1]);"
							onkeyup="return showCodeListKey('pd_zeroone',[this,PAYNEEDTOACCName],[0,1]);">
						<input class=codename value="1" name=PAYNEEDTOACCName
							readonly="readonly">
					</TD>

					<TD class=title5 STYLE="display:none;">
						险种帐户交费名
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input type=hidden name=RISKACCPAYNAME>
					</TD>
					<TD class=title5 STYLE="display:none;">
是否归属标记
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=ASCRIPTION readonly="readonly"
							ondblclick="return showCodeList('pd_zeroone',[this,ASCRIPTIONName],[0,1]);"
							onkeyup="return showCodeListKey('pd_zeroone',[this,ASCRIPTIONName],[0,1]);">
						<input class=codename name=ASCRIPTIONName readonly="readonly">
					</TD>
				</tr>
				<tr class=common>


				</tr>
			</table>
			
			<hr>

			<div align=left id=savabuttonid1>
				<input value="重  置" type=button onclick="initDetail()"
					class="cssButton" type="button">
				<input value="保  存" type=button onclick="save()" class="cssButton"
					type="button">
				<input value="修  改" type=button onclick="update()" class="cssButton"
					type="button">
				<input value="删  除" type=button onclick="del()" class="cssButton"
					type="button">
				<input value="返  回" type=button onclick="returnParent( )"
					class="cssButton" type="button">
			</div>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline9Grid"> </span>
					</td>
				</tr>
			</table>
			<!--INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();"-->
			</BR>
			</BR>


			<br>
			<br>
			<table>
				<tr>
					<td class="titleImg">
已保存的缴费/给付/账户关联
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline12Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
				onclick="Mulline12GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button
				onclick="Mulline12GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button
				onclick="Mulline12GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
				onclick="Mulline12GridTurnPage.lastPage();">
			</BR>
			</BR>
			<table>
				<tr>
					<td class="titleImg">
缴费/给付/账户关联定义
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<TD class=title5>
缴费编码
					</TD>
					<TD class=input5>
						<Input class="codeno" name=PayPlanCode000 readonly="readonly"
							ondblclick="return showCodeList('pd_payplancode',[this,PayPlanCodeName000],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"
							onkeyup="return showCodeListKey('pd_payplancode',[this,PayPlanCodeName000],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=PayPlanCodeName000 readonly="readonly">
					</TD>
					<TD class=title5>
账户类型
					</TD>
					<TD class=input5>
						<Input class="codeno" name=AccountType000 readonly="readonly"
							ondblclick="return showCodeList('pd_accounttype',[this,AccountTypeName000],[0,1]);"
							onkeyup="return showCodeListKey('pd_accounttype',[this,AccountTypeName000],[0,1]);"><input class=codename name=AccountTypeName000 readonly="readonly">
					</TD>
					<TD class=title5>
缴费/给付/账户编码
					</TD>
					<TD class=input5>
						<Input class=common name=AccountCode000>
					</TD>
					</tr>
					<tr class=common>
						<TD class=title5>
缴费/给付/账户名称
						</TD>
						<TD class=input5>
							<input class=common name=AccountCodeName000>
						</TD>
						<TD class=title5>
						</TD>
						<TD class=input5>
						</TD>
						<TD class=title5>
						</TD>
						<TD class=input5>
						</TD>
					</tr>
			</table>
			<br />
			<hr>
			<div align=left id=savabuttonid2>
				<input value="重  置" type=button onclick="reset()"  class="cssButton" type="button">
				<input value="保  存" type=button onclick="hand('save')" class="cssButton" type="button">
				<input value="修  改" type=button onclick="hand('update')"  class="cssButton" type="button">
				<input value="删  除" type=button onclick="hand('delete')"  class="cssButton" type="button">
				<input value="返  回" type=button onclick="returnParent( )" class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskAccPay">
			<input type=hidden name=IsReadOnly>
		</form>

		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
