<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�LRBsnsTabInput.jsp
//�����ܣ�
//�������ڣ�2007-2-8
//������  ��zhangbin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="LRBsnsBillPrintInput.js"></SCRIPT> 
	<%@include file="LRBsnsBillPrintInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initElementtype();initForm();">    
	<form action="" method=post name=fm target="fraSubmit" >
    <div style="width:200">	
		<table class="common">
			<tr class="common">
				<td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBillPrint);"></td>
				<td class="titleImg">����ӡ�˵�</td>
			</tr>
		</table>
	</div>
	<br>
	<Div id= "divBillPrint" style= "display: ''">
		<table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanBillPrintListGrid"></span></td>
			</tr>
		</table>
	</Div> 
	
    <br>	
	<INPUT class=cssButton  VALUE="�˵���ӡ" TYPE=button onClick="billPrint();">
    <input type="hidden" name=OperateType value="">
    <input type="hidden" name="Operator" value="<%=Operator%>">
    
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 