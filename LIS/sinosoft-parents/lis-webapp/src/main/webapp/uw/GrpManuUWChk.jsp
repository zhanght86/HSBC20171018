<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuUWChk.jsp
//程序功能：个人人工核保
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LCContSet tLCContSet = new LCContSet();

	String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tGrpContNo = request.getParameter("GrpContNo");
  String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;
  TransferData tTransferData = new TransferData();
  LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
if (!tGrpContNo.equals("") )
		{
		
	  		
		    tLCGrpContSchema.setGrpContNo( tGrpContNo );
		    loggerDebug("GrpManuUWChk","tGrpContNo:"+tGrpContNo);
		    tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
		    tTransferData.setNameAndValue("PrtNo",tPrtNo);
	    	tTransferData.setNameAndValue("MissionID",tMissionID);
	      tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		    flag = true;

		}
		try
		{
		loggerDebug("GrpManuUWChk","flag=="+flag);
		  	if (flag == true)
		  	{
				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tTransferData );
				tVData.add( tG );
				// 数据传输
				//GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
				String busiName="tbgrpGrpTbWorkFlowUI";
                BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
                
				if (tBusinessDelegate.submitData(tVData,"0000002004",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("GrpManuUWChk","n=="+n);
					for (int j = 0; j < n; j++)
					loggerDebug("GrpManuUWChk","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
					Content = " 自动核保失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();				  
				    Content = " 自动核保成功! ";
				    if (!tError.needDealError())
				    {                          
				    	//Content = "自动核保成功!";
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{    			      
					      for(int j = 0;j < n;j++)
					      {					       
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
					    }
		
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	int n = tError.getErrorCount();
				    	//Content = "自动核保失败!";
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
							}
				    	FlagStr = "Fail";
				    }
				}
			}
			else
			{
				Content = "请选择保单！";
			}  
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" 提示:异常退出.";
		}	

%>                      
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
//	parent.fraInterface.initPolGrid();
	<%
	//parent.fraInterface.fm.submit();
	%>
</script>
</html>
