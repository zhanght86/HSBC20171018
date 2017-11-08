<%
//程序名称：ClaimDetailQueryInit.jsp
//程序功能：既往理赔详细查询
//创建日期：2005-10-08 09:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
                   

 <SCRIPT src="../common/javascript/Common.js"></SCRIPT>

 <script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
   document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
}	

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
  try
  {
		initParam();
		initQuery();
		queryRegister();
//		afterQueryLL();
		ClaimType();
		Register();
		queryAudit();
		queryConfirm();
		queryEndCase();
		queryConfDate();
		
		//LLCollectorGrid();
  }
  catch(re)
  {
    alert("ClaimDetailQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


</script>





