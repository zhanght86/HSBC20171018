<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRFeeRateBatchInput.jsp
//function :LRFeeRateBatchInput.jsp
//Creator :
//date :2008-06-04
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
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

<SCRIPT src = "./LRFeeRateBatchRiskInput.js"></SCRIPT> 
<%@include file="./LRFeeRateBatchRiskInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
  <form action="" method=post name=fm target="fraSubmit">
	  <table>
	  	<tr>
	     <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	     	OnClick= "showPage(this,divLRFeeRateBatch);"></td>
	   <td class= titleImg>����Ӷ��������</td></tr>
	  </table>
  	<Div id= "divLRFeeRateBatch" style= "display: ''">
  		<table  class= common>
          <tr  class= common>
            <td>
          		<span id="spanFeeRateBatchGrid" ></span>
        		</td>
      		</tr>
    	</table>
    	</Div>
	    
		   	<input class="cssButton" type="button" value="����" onclick="submitForm()" >
			<input class="cssButton" type="button" value="ɾ��" onclick="deleteClick()" >
		   	<input class="cssButton" type="button" value="�ر�" onclick="ClosePage()" >
	  </Div>
    	<input type=hidden name=AccumulateDefNO value="">
    	<input type=hidden name=RIPreceptNo value="">
    	<input type=hidden name=OperateType value="">
    	<input type=hidden name=IncomeCompanyNo value="">
    	<input type=hidden name=AreaId value="">
    	<input type=hidden name=UpperLimit value="">
    	<input type=hidden name=LowerLimit value="">
	</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>