<%
//name :RateCardInit.jsp
//Creator :zz
//date :2008-06-19
//卡单费率表定义菜单
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('Riskcode').value = '';
    document.all('ProductPlan').value = '';
    document.all('InsuYear').value = '';
    document.all('InsuYearFlag').value = '';
    document.all('Prem').value = '';
  }
  catch(ex)
  {
    alert("RateCardInit-initInpBox进行初始化时出现错误！！！！");
  }
}


function initForm()
{
  try
  {
    initInpBox();
  }
  catch(re)
  {
    alert("RateCardInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>


