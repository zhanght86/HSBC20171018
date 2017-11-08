<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorOverDueSave.jsp
//程序功能：工作流保全保全手动终止
//创建日期：2005-12-19 20:02:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
	String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);   			
	mTransferData.setNameAndValue("EdorState", "d");  
	VData tVData = new VData();       
	tVData.addElement(tG);
	tVData.add(mTransferData);
    String busiName="PEdorOverDueBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

    
	try
	{		   
		if(!tBusinessDelegate.submitData(tVData,"",busiName))
		{
			tError = tBusinessDelegate.getCErrors();
	    	Content = "保全终止失败，原因是:" + tError.getFirstError();
	    	FlagStr = "Fail";
		}
	}
	catch(Exception ex)
	{
	      Content = "保全终止失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    { 
		    	Content = "保全终止成功!";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "保全终止失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	} 
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 