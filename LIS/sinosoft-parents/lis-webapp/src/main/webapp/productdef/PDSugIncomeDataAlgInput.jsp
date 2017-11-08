<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//程序名称：PDSugIncomeDataAlgInput.jsp
		//程序功能：责任给付生存
		//创建日期：2011-10-16
		//创建人  ：
		//更新记录：  更新人    更新日期     更新原因/内容
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
		<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
		<script type="text/javascript">
	 		var riskcode = '<%=request.getParameter("riskcode")%>';
	 		var proceedscode = '<%=request.getParameter("proceedscode")%>';
	 		var termsStr = '<%=request.getParameter("terms")%>';
	 		var operator = '<%=request.getParameter("operator")%>';
		</script>
		<SCRIPT src="PDSugIncomeDataAlg.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
 		<script src="../common/javascript/jquery.easyui.min.js"></script>
 		<%@include file="PDSugIncomeDataAlgInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="init(),initElementtype();">
		<form action="./PDSugIncomeDataAlgSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
收益算法定义:
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td class=title5>
收益项代码:
					</td>
					<td class=input5>
						<input class="common8" name=PROCEEDSCODE verify="收益项代码|NOTNUlL&LEN<=20" elementtype="nacessary" >
					</td>
					<td class=title5>
算法类型：
					</td>
					<td class=input5>
						<Input class=codeno name=EXPRESSPROPERTY  onpropertychange="controlValueChange(this.value)" verify="算法类型|NOTNUlL&LEN=1" ondblclick="return showCodeList('sugcaltype',[this,EXPRESSPROPERTYName],[0,1]);" onkeyup="return showCodeListKey('sugcaltype',[this,EXPRESSPROPERTYName],[0,1]);"><input class=codename name="EXPRESSPROPERTYName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
计算顺序：
					</td>
					<td class=input5>
						<input class="common8" name=CALORDERNO verify="计算顺序|NOTNUlL&NUM" elementtype="nacessary">
					</td>
					<td class=title5>
是否需要展示：
					</td>
					<td class=input5>
						<Input class=codeno name=SHOWFLAG  verify="是否需要展示|NOTNUlL" ondblclick="return showCodeList('sugflag',[this,SHOWFLAGName],[0,1]);" onkeyup="return showCodeListKey('sugflag',[this,SHOWFLAGName],[0,1]);"><input class=codename name="SHOWFLAGName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common id="MaxMin">
					<td class=title5>
递归初始值：
					</td>
					<td class=input5>
						<input class="common8" name=RECURSIONINITVALUE  verify="递归初始值|LEN<=20">
					</td>
					<td class=title5>
计算公式：
					</td>
					<td class=input5>
						<input class="common8" name=CALEXPRESS  verify="计算公式|NOTNUlL" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
精确位数：
					</td>
					<td class=input5>
						<input class="common8" name=RESULTPRECISION  verify="精确位数|NUM">
					</td>
					<td class=title5>
备注信息：
					</td>
					<td class=input5>
						<input class="common8" name=BACK >
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
算法条件：
					</td>
					<td class=input5>
						<input class="common" readonly="readonly" name=TERMS verify="算法条件|NOTNULL" >
						<input value=添加条件 name="btnTerms" onClick="terms()"
							class="cssButton" type="button">
					</td>
					<td class=title5 id="REMARKTitile">
最值处理类型：
					</td>
					<td class=input5 id="REMARKInput">
						<Input class=codeno name=REMARK   ondblclick="return showCodeList('sugmaxtype',[this,REMARKName],[0,1]);" onkeyup="return showCodeListKey('sugmaxtype',[this,REMARKName],[0,1]);"><input class=codename name="REMARKName" readonly="readonly">
					</td>
				</tr>

			</table>
			<div align=left>
				<input value="保  存" name="btnEdit" onClick="save()"
					class="cssButton" type="button">
			</div>
			<table>
				<tr>
					<td class="titleImg">
计算因子列表：
					</td>
				</tr>
			</table>

			<table  class= common>
			   <tr  class= common>
			      <td style="text-align:left;" colSpan=1>
			     <span id="spanMulline10Grid" >
			     </span> 
			      </td>
			   </tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
			<br>
			<input value="新  增" name="btnDelete" onClick="newFactor()"
				class="cssButton" type="button">
			<input value="修  改" name="btnDelete" onClick="update()"
				class="cssButton" type="button">				
			<input value="删  除" name="btnDelete" onClick="del()"
				class="cssButton" type="button">
			<input value="返   回" name="btnDelete" onClick="cannelFactor()"
				class="cssButton" type="button">
			
			<input type=hidden name="operator" value=<%=request.getParameter("operator")%> >
			<input type=hidden name="tableName" value="PD_ProceedsExpress">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%> >
			<input type=hidden name="ProceedsCode" value=<%=request.getParameter("proceedscode")%> >
			
			<input type=hidden name=hiddenItemCode >
			<input type=hidden name=hiddenCalElement >
			
	</form>
	<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;"></span>
	</body>
</html>

