<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDLoanInput.jsp
 //程序功能：保单贷款描述表
 //创建日期�?009-3-16
 //创建�? �?
 //更新记录�? 更新�?   更新日期     更新原因/内容
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
 <SCRIPT src="PDLoan.js"></SCRIPT>
 <%@include file="PDLoanInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDLoanSave.jsp" method=post name=fm target="fraSubmit">
		<table>
				<tr>
					<td class="titleImg">
��ȫ������Ϣ
					</td>
				</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title5>
���ִ���
				</td>
				<td class=input5>
					<input class="readonly" name="RiskCode" readonly="readonly" >
				</td>
				<td class=title5>
��Ʒ��������
				</td>
				<td class=input5>
					<input class="readonly" name="RiskName" readonly="readonly" >
				</td>
			</tr>
			<tr class=common>
				<td class=title5>
��ȫ��Ŀ����
				</td>
				<td class=input5>
					<input class="readonly" name="EdorType" readonly="readonly" >	
				</td>
				<td class=title5>
��ȫ��Ŀ����
				</td>	
				<td class=input5> 
					<input class="readonly" name="EdorName" readonly="readonly" >
				</td>
			</tr>
		</table>
		
		<table>
				<tr>
					<td class="titleImg">
��������������Ϣ
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
		<input id=savabutton1 value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
		<input id=savabutton2 value="��  ��" type=button  onclick="update()" class="cssButton" type="button" ><!-- 
		<input value="算法定义" type=button  onclick="button282( )" class="cssButton" type="button" >
		<br><br>-->
		<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
		<br><br>
		<input type=hidden name="operator">
		<input type=hidden name="tableName" value="PD_LMLoan">
		<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
