<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�RIDetailBillInput.jsp
//�����ܣ�
//�������ڣ�2011-11-16
//������  ��jintao
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
	<SCRIPT src="RIDetailProlossInput.js"></SCRIPT> 
	<%@include file="RIDetailProlossInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initElementtype();initForm();">    
	<form action="" method=post name=fm target="fraSubmit" >
		<div style="width:200">
			<table class="common">
				<tr class="common">
					<td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
					<td class="titleImg">ӯ��Ӷ���˵�</td>
				</tr>
			</table>
		</div>
	
		<Div  id= "divCessGetData" style= "display: ''" >
			<table class="common">
			<tr class="common">
				<td class= title5>ӯ��Ӷ���˵�����</td>
				<td class= input5>
					<input class="codeno" name="ProlossType" 
				    ondblclick="return showCodeList('riprolosstype', [this,ProlossTypeName],[0,1],null,null,null,1);" 
				    onkeyup="return showCodeListKey('riprolosstype', [this,ProlossTypeName],[0,1],null,null,null,1);"   
				    verify="ӯ��Ӷ���˵�����|NOTNULL"  nextcasing='' ><input class="codename" name="ProlossTypeName" elementtype=nacessary >
				</td>
				<TD  class= title5 >�ֱ���˾</TD>
		        <TD  class= input5 > 
		        	<Input class="codeno" name="RIcomCode"  verify="�ֱ���˾|NOTNULL"
				      ondblClick="showCodeList('',[this,null],[0,1],null,null,null,1,250);"
				      onkeyup="showCodeListKey('',[this,null],[0,1],null,null,null,1,250);" readonly="readonly" verify="�ֱ���˾|NOTNULL" ><Input 
				      class= codename name= 'RIComName' readonly="readonly" elementtype=nacessary>
		        </TD>		
			</tr>
			<tr class="common">
			<td class="title5">�ٱ���ͬ</td>
				<td class="input5"><input class="codeno" name="ContNo"
					ondblClick="showCodeList('riquerycont',[this,ContName],[0,1],null,fm.RIcomCode.value,'Recomcode',1,260);"
					onkeyup="showCodeListKey('riquerycont',[this,ContName],[0,1],null,null,null,1,260);"
					verify="�ٱ���ͬ|NOTNULL"><Input class=codename name='ContName'
					elementtype=nacessary></td>
				<td class="title5">���</td>
				<td class="input5"><Input class="code" name=CalYear
					verify="���|NOTNULL&len=4&num"
					ondblclick="showCodeList('riprofityear',[this],[0])"
					onkeyup="showCodeListKey('riprofityear',[this],[0])"
					elementtype=nacessary>
				</td>
			</tr>
		</table>
		 		
			<hr>

			<INPUT class=cssButton  VALUE="��  ӡ" TYPE=button onClick="StatisticData();">
			<INPUT class=cssButton  VALUE="��   ��" TYPE=button onClick="ResetForm();">	
		</Div>
	    <input type="hidden" name=OperateType value="">
	    <input type="hidden" name="Operator" value="<%=Operator%>">   
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
