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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script language="JavaScript">
	
function initAll() {
    //document.all('customername').value = '';
    document.all('ManageCom').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('EmployDate').value = '';
    document.all('IDType').value = '';
    document.all('IDNumber').value = '';
    document.all('QualityFlag').value = '';
    document.all('UnusualType').value = '';
    document.all('Remark').value = '';
}

function initForm() {
  try {
	initAll();
	initData();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>


