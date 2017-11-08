<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDEdorWTInput.jsp
 //程序功能：犹豫期退保描述表
 //创建日期：2009-3-16
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
  <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDEdorWT.js"></SCRIPT>
 <%@include file="PDEdorWTInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDEdorWTSave.jsp" method=post name=fm target="fraSubmit">
		<table>
				<tr>
					<td class="titleImg">
保全基础信息
					</td>
				</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title5>
险种代码
				</td>
				<td class=input5>
					<input class="readonly" name="RiskCode" readonly="readonly" >
				</td>
				<td class=title5>
产品险种名称
				</td>
				<td class=input5>
					<input class="readonly" name="RiskName" readonly="readonly" >
				</td>
			</tr>
			<tr class=common>
				<td class=title5>
保全项目编码
				</td>
				<td class=input5>
					<input class="readonly" name="EdorType" readonly="readonly" >	
				</td>
				<td class=title5>
保全项目名称
				</td>	
				<td class=input5> 
					<input class="readonly" name="EdorName" readonly="readonly" >
				</td>
			</tr>
		</table>
		
		<table>
				<tr>
					<td class="titleImg">
犹豫期退保描述信息
					</td>
				</tr>
		</table>
		<table  class= common>
		   <tr  class= common>
		      <td style="text-align:left;" colSpan=1>
		     <span id="spanMulline9Grid" >
		     </span> 
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
	
		<input id=savabutton1 value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
		<input id=savabutton2 value="修  改" type=button  onclick="update()" class="cssButton" type="button" ><!--  
		<input value="算法定义" type=button  onclick="button282( )" class="cssButton" type="button" >
		<br><br>-->
		<input  value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
		<br><br>
		
		<input type=hidden name="operator">
		<input type=hidden name="tableName" value="PD_LMEdorWT">
		<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
