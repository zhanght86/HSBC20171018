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
<SCRIPT src="LRTempCessEventDealInput.js"></SCRIPT>
<%@include file="LRTempCessEventDealInit.jsp"%>
<title>�ٱ��ظ�</title>

</head>
<body  onload="initForm();" >
<form name=fm method="post" target="fraSubmit" action="./CessTempConclusionSave.jsp">
  <%@include file="../common/jsp/InputButton.jsp"%>
  
	<table>
	  <tr>
	    <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempEventGrid);">
	    </td>
	  	<td class= titleImg> 
�ٷ��¼�
	    </td>
	  </tr>
	</table>
		<Div  id= "divTempEventGrid" style= "display: ''"> 
	  <table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanTempEventGrid"> 
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
	</Div>
	<table>
  	<tr>
      <td class=common>
		  	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divTempCessPreceptGrid);">
  	  </td>
  		<td class= titleImg>
�ٷַ���
  	  </td>
  	</tr>
	</table>
	<Div  id= "divTempCessPreceptGrid" style= "display: ''">
		<table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanTempCessPreceptGrid"> 
					  </span> 
			    </td>
		  </tr>
		</table>  
	</Div>
	<br>
	
	<table>
  	<tr>
      <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempCessInputGrid);">
  	  </td>
  		<td class= titleImg>
�ٷ�¼����
  	  </td>
  	</tr>
	</table>
	<Div  id= "divTempCessInputGrid" style= "display: ''">
		<table  class= common>
	    	<tr  class= common>
	    		<td style="text-align:left;" colSpan=1> 
					  <span id="spanTempCessInputGrid"> 
					  </span> 
			    </td>
		  </tr>
		</table>  
	</Div>
	<br>
	
	<INPUT VALUE="�ٷ��¼�����" class= cssButton TYPE=button onclick="TempEventDeal();">
	
  <input type="hidden" name="ProposalNo">
  <input type="hidden" name="PrtNo">
</form>  


<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>