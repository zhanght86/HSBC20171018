<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AnswerUWReInsureChk.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.reinsure.faculreinsure.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	    //输出参数
	    CErrors tError = null;
	    String FlagStr = "Fail";
	    String Content = "";
		
		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getAttribute("GI");
		
		String tPolNo = request.getParameter("PolNo"); 
		String tFilePath= request.getParameter("FilePath"); 
		String tFileName= request.getParameter("FileName");    
		String tRemark = request.getParameter("Remark");
		System.out.println("xxxxxxxxxxxxxxxxxxxxx: "+tRemark);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("FilePath",tFilePath);
		tTransferData.setNameAndValue("FileName",tFileName);		
		tTransferData.setNameAndValue("Remark",tRemark);
		System.out.println("PolNo:  "+tPolNo);
		System.out.println("FilePath: "+tFilePath);
			
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG );
		
		// 数据传输
		//AnswerUWReInsureUI tAnswerUWReInsureUI = new AnswerUWReInsureUI();
		BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (uiBusinessDelegate.submitData(tVData,"","AnswerUWReInsureUI") == false)
		{
			Content = " "+"保存失败，原因是:"+" " + uiBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
        else 
		{                          
		    Content = " "+"保存成功!"+" ";
		    FlagStr = "Succ";
		 }
%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
