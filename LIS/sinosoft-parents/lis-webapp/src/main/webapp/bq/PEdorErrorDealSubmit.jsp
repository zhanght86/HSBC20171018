<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//EdorApproveSave.jsp
//程序功能：保全函件回销
//创建日期：2005-05-08 15:59:52
//创建人  ：sinosoft
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
    <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
  String Flag = "0";
  
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
  String tPrtSerNo	= request.getParameter("PrtSerNo");
 String tEdorAcceptNo	= request.getParameter("EdorAcceptNo1");
  String tLetterType	= request.getParameter("LetterType");
 String ActionFlag	= request.getParameter("ActionFlag");
 
      //其他原因内容
	String CancelReasonContent = request.getParameter("CancelReasonContent");
	//原因编号
	String 	SCanclReason = request.getParameter("CancelReasonCode");
	tTransferData.setNameAndValue("CancelReasonContent", CancelReasonContent);
	tTransferData.setNameAndValue("SCanclReason", SCanclReason);
  
  //创建保全确认、审批修改节点的数据元素
  tTransferData.setNameAndValue("PrtSerNo", tPrtSerNo);
  tTransferData.setNameAndValue("BackReason", SCanclReason);
  tTransferData.setNameAndValue("OtherReason", CancelReasonContent);
  tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
    tTransferData.setNameAndValue("LetterType", tLetterType);
        
//  PEdorErrorForceBackBL tPEdorErrorForceBackBL = new PEdorErrorForceBackBL(); 
	 String busiName="tPEdorErrorForceBackBL";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
   tVData.add(tG);		
   tVData.add(tTransferData);
//   tPEdorErrorForceBackBL.submitData(tVData, ActionFlag);
   tBusinessDelegate.submitData(tVData, ActionFlag,busiName);
	
//	tError = tPEdorErrorForceBackBL.mErrors;
	tError=tBusinessDelegate.getCErrors();
	 if (!tError.needDealError())
	{                          
           		
        		 Content = "回收成功!";
        		 FlagStr = "Succ";
	}
		 else                                                                           
	 {
		    	Content = "回收失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
	 }
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Flag%>");
</script>
</html>
   
   
   
 