<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�LRTempCessInput.jsp
//�����ܣ��ٱ���˹���
//�������ڣ�2007-3-30 15:22
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="LRTempCessInput.js"></SCRIPT>
<%@include file="LRTempCessInit.jsp"%>
<title>�ٱ��ظ�</title>

</head>
<body  onload="initForm();" >
<form name=fm method="post" target="fraSubmit" action="./CessTempConclusionSave.jsp">
  <%@include file="../common/jsp/InputButton.jsp"%>
  <table>
  	<tr>
      	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchCondition);">
  	   	</td>
  		  <td class= titleImg>
��ѯ����
  	   	</td>
  	</tr>
	</table>
	<Div  id= "divSearchCondition" style= "display: ''">
		<Table class= common>
	 		<TR class= common>
	 			<TD class= title5>�������˱���</TD>
	 			<TD class= input5>
	 				<input class=common name="InsuredNo">
	 			</TD>
	 			<TD class= title5>����������</TD>
	 			<TD class= input5>
	 				<input class=common name="InsuredName">
	 			</TD>
	 			<TD class= title5>���ֱ���</TD>
	 			<TD class="input5">
	 				<input class=common name="RiskCode">
	 			</TD>
	 		</TR>
	 		<TR>
	 			<TD class= title5>����״̬</TD>
	      <TD class= input5 >
	      	<input class=codeno readonly="readonly" name="AppFlag" CodeData="0|^0|δǩ��|^1|��ǩ��|" 
	        ondblclick="return showCodeListEx('audittype', [this,AppFlagName],[0,1],null,null,null,1);" 
	        onkeyup="return showCodeListKeyEx('audittype', [this,AppFlagName],[0,1],null,null,null,1);" ><input 
	        class=codename name=AppFlagName readonly="readonly" elementtype=nacessary>
	      </TD>
	      <TD class= title5>�ٷֽ���</TD>
	      <TD class= input5>
	      	<input class=codeno readonly="readonly" name="TempContClusion" CodeData="0|^0|�����ٷֽ���|^1|δ���ٷֽ���|" 
	        ondblclick="return showCodeListEx('audittype', [this,TempContClusionName],[0,1],null,null,null,1);" 
	        onkeyup="return showCodeListKeyEx('audittype', [this,TempContClusionName],[0,1],null,null,null,1);" ><input 
	        class=codename name=TempContClusionName readonly="readonly" elementtype=nacessary>
	      </TD>
	      <TD class= title5></TD>
	      <TD class= input5></TD>
	    </TR> 
	  </Table>
	  <INPUT VALUE="��ѯ" class= cssButton TYPE=button onclick="QueryRiskInfo();">&nbsp;&nbsp;
	  <INPUT VALUE="����" class= cssButton TYPE=button onclick="resetForm();">
	</Div>
	<table>
	  <tr>
	    <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);">
	    </td>
	  	<td class= titleImg> 
���α�����Ϣ
	    </td>
	  </tr>
	</table>
	<Div  id= "divRiskInfo" style= "display: ''"> 
	  <table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanRiskInfoGrid"> 
					  </span> 
			    </td>
		  </tr>
		</table>   
		<Div id= "div1" align="center" style= "display: '' ">
			<INPUT VALUE="��ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		  <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		  <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
		  <INPUT VALUE="βҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();">  
		</Div>
	</div>
	<br>
	<INPUT VALUE="�ٱ��ظ�" class= cssButton TYPE=button onclick="ReinsureAnswer();">
	<br><br>
	<table>
	  <tr>
	    <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPrecept);">
	    </td>
	  	<td class= titleImg> 
�ٱ�����
	    </td>
	  </tr>
	</table>
	<Div id="divPrecept" style= "display: ''"> 
	  <table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanPreceptGrid"> 
					  </span> 
			    </td>
		  </tr>
		</table>
	</div>
	<br> <hr>
	<INPUT VALUE="�ٷֽ���" class= cssButton TYPE=button onclick="TempCessConcls();">&nbsp;&nbsp;
	
  <input type="hidden" name="ProposalNo">
  <input type="hidden" name="PrtNo">
</form>  


<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>