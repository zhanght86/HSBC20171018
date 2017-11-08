<%
//程序名称：GetSendToBankInit.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>


<%@page import="com.sinosoft.lis.pubfun.PubFun"%><SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
	fm.EndDate.value=formartDate(getCurrentDate());
	fm.StartDate.value=calSpecDate(getCurrentDate(),'D',-60);
}                                     

function initForm() {
  try {
  	initInpBox();
  	
  }
  catch(re) {
    alert("InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>

	
