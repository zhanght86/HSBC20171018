<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorNoscanAppSave.jsp
//程序功能：保全无扫描申请创建开始节点
//创建日期：2005-04-27 15:59:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

   String busiName="EdorWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 
  //输出参数
  String FlagStr = "";
  String Content = ""; 
  		     
  String sOperate = "8888888882";  //创建开始节点分支参数
  String rEdorAcceptNo = "";
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
   
  //EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI(); 
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);
		tBusinessDelegate.submitData(tVData, sOperate,busiName);
	}
	catch(Exception ex)
	{
	      Content = "申请失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          

				//获取返回结果
				VData mResult;
				VData mINResult;
				TransferData rTransferData;	
							
				try
				{
					mResult = tBusinessDelegate.getResult();

                	

                	rTransferData = (TransferData) 
                		mResult.getObjectByObjectName("TransferData", 0);

                	rEdorAcceptNo = (String)
						rTransferData.getValueByName("EdorAcceptNo");	
				}
				catch(Exception ex)
				{
		    		Content = "申请成功,获取返回结果失败！原因是:" + ex.toString();
		    		FlagStr = "Fail";
				}
				
				Content ="申请成功！保全受理号：" + rEdorAcceptNo;
		    	FlagStr = "Succ";						
 	
		    }
		    else                                                                           
		    {
		    	Content = "申请失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>
   
<html>
<script language="javascript">
	parent.fraInterface.fm.EdorAcceptNo.value= "<%=rEdorAcceptNo%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 