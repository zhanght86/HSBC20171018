<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWCustomerQualityInit.jsp
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
           
<script language="JavaScript">
	
function initAll() {
    fm.all('customer').value = '';
    fm.all('customername').value = '';
    fm.all('CustomerNo').value = '';
    fm.all('Name').value = '';
    fm.all('Sex').value = '';
    fm.all('BirthDay').value = '';
    fm.all('IDType').value = '';
    fm.all('IDNumber').value = '';
    fm.all('BlacklistFlagNo').value = '';
    fm.all('BlacklistFlagName').value = '';
    fm.all('Remark').value = '';
}

function initForm() {
  try {

	initAll();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>


