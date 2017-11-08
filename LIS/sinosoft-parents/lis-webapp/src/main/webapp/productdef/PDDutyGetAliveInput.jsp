<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>

	<%
		//程序名称：PDDutyGetAliveInput.jsp
		//程序功能：责任给付生存
		//创建日期：2009-3-16
		//创建人  ：
		//更新记录：  更新人    更新日期     更新原因/内容

		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput) session.getAttribute("GI");
	%>

	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<SCRIPT src="DictionaryUtilities.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">


		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<script>
	
	var mOperator = "<%=tGI.Operator%>";   //记录操作员
	var mRequDate = <%=(String) request.getParameter("requdate")%>;   //记录操作员

</script>
		<%@include file="buttonshow.jsp"%>
		<SCRIPT src="PDDutyGetAlive.js"></SCRIPT>
		<%@include file="PDDutyGetAliveInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDDutyGetAliveSave.jsp" method=post name=fm
			target="fraSubmit">
			<table class=common>
				<tr class=common>
					<td class=title5>
产品险种代码
					</td>
					<td class=input5>
						<input class="common" name="RiskCode" readonly="readonly">
					</td>
					<td class=title5>
给付代码
					</td>
					<td class=input5>
						<input class="common" name="GetDutyCode" readonly="readonly">
					</td>
					<td class=title5></td>
					<td class=input5>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td class="titleImg">
已有责任给付生存项
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

			<hr>
			<table>
				<tr>
					<td class="titleImg">
责任给付生存属性定义
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<!--TD  class= title>给付代码</TD>
        <TD  class= input>
						<Input class=common name=GETDUTYCODE verify="给付代码|NOTNUlL&LEN<=6" >
        </TD> 
        <TD  class= title>给付名称</TD>
        <TD  class= input>
						<Input class=common name=GETDUTYNAME verify="给付名称|NOTNUlL" >
        </TD-->
					<TD class=title5 STYLE="display:none;">
给付责任类型
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class=common name=GETDUTYKIND
							verify="给付责任类型|NOTNUlL&LEN<=6" elementtype="nacessary" value="1">
					</TD>
					<TD class=title5 STYLE="display:none;">
给付间隔
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class=common name=GETINTV>
					</TD>

					<TD class=title5>
起领期间
					</TD>
					<TD class=input5>
						<Input class=common name=GETSTARTPERIOD>
					</TD>

					<TD class=title5>
起领期间单位
					</TD>
					<TD class=input5>
						<Input class="codeno" name=GETSTARTUNIT readonly="readonly"
							verify="起领期间单位|CODE:pd_payendyearflag"
							ondblclick="return showCodeList('pd_payendyearflag',[this,GETSTARTUNITName],[0,1]);"
							onkeyup="return showCodeListKey('pd_payendyearflag',[this,GETSTARTUNITName],[0,1]);"><input class=codename name=GETSTARTUNITName readonly="readonly">
					</TD>
					</tr>
					<tr class = common>
					<TD class=title5>
起领日期计算参照
					</TD>
					<TD class=input5>
						<Input class="codeno" name=STARTDATECALREF readonly="readonly"
							verify="起领日期计算参照|NOTNUlL&CODE:pd_payenddatecalref"
							ondblclick="return showCodeList('pd_payenddatecalref',[this,STARTDATECALREFName],[0,1]);"
							onkeyup="return showCodeListKey('pd_payenddatecalref',[this,STARTDATECALREFName],[0,1]);"><input class=codename name=STARTDATECALREFName readonly="readonly" elementtype="nacessary">
					</TD>
					<TD class=title5>
起领日期计算方式
					</TD>
					<TD class=input5>
						<Input class="codeno" name=STARTDATECALMODE readonly="readonly"
							verify="起领日期计算方式|NOTNUlL&CODE:pd_startdatecalmode"
							ondblclick="return showCodeList('pd_startdatecalmode',[this,STARTDATECALMODEName],[0,1]);"
							onkeyup="return showCodeListKey('pd_startdatecalmode',[this,STARTDATECALMODEName],[0,1]);"><input class=codename name=STARTDATECALMODEName readonly="readonly" elementtype="nacessary">
					</TD>
				</tr>
				<tr class=common>
					<TD class=title5>
止领期间
					</TD>
					<TD class=input5>
						<Input class=common name=GETENDPERIOD>
					</TD>
					<TD class=title5>
止领期间单位
					</TD>
					<TD class=input5>
						<Input class="codeno" name=GETENDUNIT readonly="readonly"
							ondblclick="return showCodeList('pd_payendyearflag',[this,GETENDPERIODName],[0,1]);"
							onkeyup="return showCodeListKey('pd_payendyearflag',[this,GETENDPERIODName],[0,1]);"><input class=codename name=GETENDPERIODName readonly="readonly">
					</TD>
					</tr>
					<tr class = common>
					<TD class=title5>
止领日期计算参照
					</TD>
					<TD class=input5>
						<Input class="codeno" name=ENDDATECALREF readonly="readonly"
							verify="止领日期计算参照|NOTNUlL&CODE:pd_payenddatecalref"
							ondblclick="return showCodeList('pd_payenddatecalref',[this,ENDDATECALREFName],[0,1]);"
							onkeyup="return showCodeListKey('pd_payenddatecalref',[this,ENDDATECALREFName],[0,1]);"><input class=codename name=ENDDATECALREFName readonly="readonly" elementtype="nacessary">
					</TD>
					<TD class=title5>
止领日期计算方式
					</TD>
					<TD class=input5>
						<Input class="codeno" name=ENDDATECALMODE readonly="readonly"
							verify="止领日期计算方式|NOTNUlL&CODE:pd_enddatecalmode"
							ondblclick="return showCodeList('pd_startdatecalmode',[this,ENDDATECALMODEName],[0,1]);"
							onkeyup="return showCodeListKey('pd_startdatecalmode',[this,ENDDATECALMODEName],[0,1]);"><input class=codename name=ENDDATECALMODEName readonly="readonly" elementtype="nacessary">
					</TD>
					
				</tr>
				<tr class=common>
					<TD class=title5 STYLE="display:none;">
给付后动作
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=AFTERGET readonly="readonly"
							verify="给付后动作|NOTNUlL&CODE:pd_afterget"
							ondblclick="return showCodeList('pd_afterget',[this,AFTERGETName],[0,1]);"
							onkeyup="return showCodeListKey('pd_afterget',[this,AFTERGETName],[0,1]);"
							value="000"><input class=codename name=AFTERGETName readonly="readonly" value="无动作">
					</TD>
					
					<TD class=title5>
算法编码
					</TD>
					<TD class=input5>
						<input class="common" name="CALCODE" verify="算法编码|LEN>=6">
					</TD>
					<TD class=title5>
催付标记
					</TD>
					<TD class=input5>
						<Input class="codeno" name=URGEGETFLAG readonly="readonly"
							ondblclick="return showCodeList('pd_urgegetflag',[this,URGEGETFLAGName],[0,1]);"
							onkeyup="return showCodeListKey('pd_urgegetflag',[this,URGEGETFLAGName],[0,1]);"><input class=codename name=URGEGETFLAGName readonly="readonly">
					</TD>
				</tr>
				<tr class=common>
					<!--  
         	<TD  class= title>
          领取动作类型
        </TD>
        <TD  class= input>
           <Input class="codeno" name=GETACTIONTYPE readonly="readonly"  ondblclick="return showCodeList('pd_getactiontype',[this,GETACTIONTYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_getactiontype',[this,GETACTIONTYPEName],[0,1]);"><input class=codename name=GETACTIONTYPEName readonly="readonly">
        </TD>
        -->
					<Input type="hidden" name=GETACTIONTYPE readonly="readonly"
						ondblclick="return showCodeList('pd_getactiontype',[this,GETACTIONTYPEName],[0,1]);"
						onkeyup="return showCodeListKey('pd_getactiontype',[this,GETACTIONTYPEName],[0,1]);">
					<input type="hidden" name=GETACTIONTYPEName readonly="readonly">
					
					<!--  
        	<TD  class= title>
          给付最大次数类型
        </TD>
        <TD  class= input>
           <Input class="codeno" name=MAXGETCOUNTTYPE readonly="readonly"  ondblclick="return showCodeList('pd_maxgetcounttype',[this,MAXGETCOUNTTYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_maxgetcounttype',[this,MAXGETCOUNTTYPEName],[0,1]);"><input class=codename name=MAXGETCOUNTTYPEName readonly="readonly">
        </TD>
          -->
					<Input type="hidden" name=MAXGETCOUNTTYPE readonly="readonly" value="0"
						ondblclick="return showCodeList('pd_maxgetcounttype',[this,MAXGETCOUNTTYPEName],[0,1]);"
						onkeyup="return showCodeListKey('pd_maxgetcounttype',[this,MAXGETCOUNTTYPEName],[0,1]);">
					<input type="hidden" name=MAXGETCOUNTTYPEName readonly="readonly">
					<TD class=title5 STYLE="display:none;">
领取时是否需要重算
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=NeedReCompute readonly="readonly" value="0"
							ondblclick="return showCodeList('pd_needrecompute',[this,NeedReComputeName],[0,1]);"
							onkeyup="return showCodeListKey('pd_needrecompute',[this,NeedReComputeName],[0,1]);">
						<input class=codename name=NeedReComputeName readonly="readonly" value="领取时不需要重新计算">
					</TD>
				</tr>
			</table>
			<!--算法定义引用页-->
			<jsp:include page="CalCodeDefMain.jsp" />
			<hr>
			<div align=left id=savabuttonid>
				<!--   <input value="查询给付生存库" type=button  onclick="queryDutyGetLib( )" class="cssButton" type="button" >-->
				<input value="重  置" type=button onclick="initGetAliveDetail()"
					class="cssButton" type="button">
				<input value="保  存" type=button onclick="save()" class="cssButton"
					type="button">
				<input value="修  改" type=button onclick="update()" class="cssButton"
					type="button">
				<input value="删  除" type=button onclick="del()" class="cssButton"
					type="button">
			</div>
			<div align=left id=checkFunc>
			<input value="查看算法内容" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
			</div>

			<br>
			<br>
			<input value="返  回" type=button onclick="returnParent( )"
				class="cssButton" type="button">
			<br>
			<br>

			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMDutyGetAlive">
			<input type=hidden name="tableName2" value="PD_LMDutyGetAlive_Lib">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name=getDutyCode2>
			<input type=hidden name=dutyType2 value=3>
		</form>

		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
