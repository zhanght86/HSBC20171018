<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSendPrintChk.jsp
//程序功能：送打印队列
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "部分通知书发送失败,原因是:";
	//在核保结论中执行类的判断标志
	String testAflag = "1";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  BusinessDelegate tBusinessDelegate;
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  //tongmeng 2007-11-09 modify
  //修改为统一发放核保通知书,一期以新契约为主,二期再对其他复用此功能程序做修改.
  //校验处理
  //内容待填充	
			
  	//接收信息
  	// 投保单列表	
	String tProposalNo = request.getParameter("ContNo");
    String tEDorNo = request.getParameter("EdorNo");
    String tEDorType = request.getParameter("EdorType");
	//String tOtherNoType = "02";
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("BQSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("BQSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  	
  	//tongmeng 2008-08-12 modify
  	//为支持多主险多被保人,同一时间可以对不同接收对象发送核保通知书,
  	//并且体检和生调通知书也在这里发放.
  	String tSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid='0000000006' ";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);
		int errorCount = 0;
		for(int m=1;m<=tSSRS.getMaxRow();m++)
		{
			String tMissionId = tSSRS.GetText(m,1);
			String tSubMissionId = tSSRS.GetText(m,2);
			// 准备传输数据 VData
			VData tVData = new VData();
			//tVData.add( tLOPRTManagerSchema);
			tVData.add( tG );
			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("ContNo",tProposalNo);
			mTransferData.setNameAndValue("NoticeType",tCode);
			mTransferData.setNameAndValue("MissionID",tMissionId);
   		mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
   		mTransferData.setNameAndValue("mSubMissionID",tSubMissionID); 
   		mTransferData.setNameAndValue("EdorNo",tEDorNo); 
   		mTransferData.setNameAndValue("EdorType",tEDorType); 
      mTransferData.setNameAndValue("Code","");
      //mTransferData.setNameAndValue("DefaultOperator", tG.Operator);
      mTransferData.setNameAndValue("testAflag", testAflag);
      //需要重新查询自动核保节点的子任务ID
      ExeSQL yExeSQL = new ExeSQL();
    	SSRS ySSRS = new SSRS();
      String sqlstr="select activityid from lwactivity where functionid='10020004'";
    	ySSRS = yExeSQL.execSQL(sqlstr);
    	String xActivityID="";
    	if(ySSRS.MaxRow==0)
    	{}
    	else
    	{
      		 xActivityID = ySSRS.GetText(1,1);
  		}
  		mTransferData.setNameAndValue("ActivityID", xActivityID);
  		mTransferData.setNameAndValue("BusiType", "1002");
    		tVData.add(mTransferData);
			// 数据传输
		//	EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
			//tongmeng 2007-11-08 modify
			//统一发放核保通知书
		// String busiName="tWorkFlowUI";
		 String busiName="WorkFlowUI";
     			
    			
     tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     System.out.println("开始执行：tBusinessDelegate");
     if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
    		{
    		 if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
     {  
    			   Content = "添加失败，原因是: 没有需要下发的问题件、体检、生调，不能发核保通知书 或" + tBusinessDelegate.getCErrors().getFirstError();
    			   FlagStr = "Fail";
    			}
    			else
    			{

    			   Content = "添加失败";
    			   FlagStr = "Fail";		
    			   continue;		
    			}		
    		
    		
    		/**
    			int n = tBusinessDelegate.getCErrors().getErrorCount();
    			System.out.println(tBusinessDelegate.getCErrors);
    			if(tBusinessDelegate.getCErrors.equals("没有需要下发的问题件、体检、生调，不能发核保通知书!"))
    			{
    				n = n + 1;
    				if(n == tSSRS.getMaxRow())
    				{
    					FlagStr = "Fail";
    					Content = " 操作失败,原因是:";
    				}
    				else
    				{
    					tBusinessDelegate.mErrors.clearErrors();
    					continue;
    				}					    
    			}
    			else
    			{			
    				FlagStr = "Fail";	
    			}
    			**/
    			
    			
    		}
    		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+FlagStr);
    		//如果在Catch中发现异常，则不从错误类中提取错误信息
    		if (FlagStr == "Fail")
    		{
    	    	tError = tBusinessDelegate.getCErrors();		    
    	    	if (!tError.needDealError())
    	    	{                     
    	    		if (m == tSSRS.getMaxRow())
    		    	{                     
    	    		Content = " 操作成功! ";
    		    	}
    	    		FlagStr = "Succ";
    	    	}
    	    	else                                                              
    	   		{		    		
    	    		int n = tError.getErrorCount();
    				if (n > 0)
    				{
    	        
    		        	Content = Content.trim() + tError.getError(0).errorMessage.trim();
    				}
    	    			FlagStr = "Fail";
    	    			break;
    	   		}
    		}
    }
    }
    }
    catch(Exception e)
    {
    e.printStackTrace();
    Content = Content.trim()+".提示：异常终止!";
    }
%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmitSendNotice("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
