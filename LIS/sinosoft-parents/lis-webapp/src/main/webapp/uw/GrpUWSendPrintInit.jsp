<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSendPrintInit.jsp
//程序功能：送打印队列
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tProposalNo = "";
  String tOtherNoType = "";
  String tCode = "";
  tProposalNo = request.getParameter("ProposalNo2");
  tOtherNoType = request.getParameter("OtherNoType2");
  tCode = request.getParameter("Code2");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">                                

function initForm(tProposalNo,tOtherNoType,tCode)
{
  try
  {
	initHide(tProposalNo,tOtherNoType,tCode);
	submitForm();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initHide(tProposalNo,tOtherNoType,tCode)
{
	document.all('ProposalNoHide').value=tProposalNo;
	document.all('OtherNoType').value=tOtherNoType;
	document.all('Code').value= tCode;	
}

</script>


