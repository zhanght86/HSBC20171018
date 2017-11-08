<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorConfirmSave.jsp
//程序功能：工作流保全保全确认
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  String busiName="EdorWorkFlowUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
	String sTemplatePath = application.getRealPath("xerox/printdata") + "/";
	
	TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));
    tTransferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
    tTransferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));
    tTransferData.setNameAndValue("TemplatePath", sTemplatePath);
    tTransferData.setNameAndValue("ActivityID", "0000008009");
  
  System.out.println("=========+++" + request.getParameter("EdorAcceptNo"));
  VData tVData = new VData();        
  
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);
		tBusinessDelegate.submitData(tVData, "0000008009",busiName);
	}
	catch(Exception ex)
	{
	      Content = "确认失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		
		String sOverDueFlag = "";
		String busiNameExeSQL="ExeSQLUI";
	    BusinessDelegate tBusinessDelegateExeSQL=BusinessDelegate.getBusinessDelegate();
	    VData cInputData = new VData();
	    TransferData tTransferDataExeSQL = new TransferData();
	    String querySql = " select edorstate from lpedorapp " +
					 " where edoracceptno = '" + request.getParameter("EdorAcceptNo") + "' ";
	    tTransferDataExeSQL.setNameAndValue("SQL",querySql);
	    cInputData.add(tTransferDataExeSQL);
	    boolean dealFlag = tBusinessDelegateExeSQL.submitData(cInputData,"getOneValue",busiNameExeSQL);
	    if(dealFlag){
	    	VData responseVData = tBusinessDelegateExeSQL.getResult();
	    	sOverDueFlag =(String) responseVData.getObjectByObjectName("String", 0);
	    	
	    }
		
		
//		String sql = " select edorstate from lpedorapp " +
//					 " where edoracceptno = '" + request.getParameter("EdorAcceptNo") + "' ";
//		
//		ExeSQL exeSQL = new ExeSQL();
//		String sOverDueFlag = exeSQL.getOneValue(sql);
		
		if (sOverDueFlag.equals("4"))  
		{
			Content = "保全确认成功！超过了补费止期，保全受理逾期终止！";
			FlagStr = "Succ";
		}
		else
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
//=========保全确认成功后自动执行确认生效处理================BGN======================
				//GEdorValidBL tGEdorValidBL = new GEdorValidBL();
				//try
				//{
				//	tGEdorValidBL.submitData(tVData, "");
				//}
				//catch(Exception ex)
				//{
				//      Content = "保全确认成功！但是保全确认生效失败，原因是:" + ex.toString();
				//      FlagStr = "Fail";
				//}
				if ("".equals(FlagStr))
				{
					
					    //tError = tGEdorValidBL.mErrors;
					    //if (!tError.needDealError())
					   // {
							Content ="保全确认成功！";
					    	FlagStr = "Succ";
					   // }
					    //else                                                                           
					    //{
					    //	Content = "保全确认成功！但是保全确认生效失败，原因是:" + tError.getFirstError();
					    //	FlagStr = "Fail";
					    //}
				}
//=========保全确认成功后自动执行确认生效处理================END======================
		    }
		    else                                                                           
		    {
		    	Content = "保全确认失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	} 
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 