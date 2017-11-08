<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：MissionApply.jsp
//程序功能：保全工作流任务申请
//创建日期：2005-06-29 11:10:36
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.service.*" %>  
  
<%
  //输出参数
  BusinessDelegate tBusinessDelegate;
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
 
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
		loggerDebug("MissionApply.jsp","session has expired");
		
		return;
  }

  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
 String ConfirmFlag=request.getParameter("Confirm");
  VData tVData ;
  TransferData tTransferData=new TransferData();

  String xEdorAcceptNo = null;
  try
  {
	   //如果是保全确认申请，增加加锁控制，以防自动运行和手工申请并发
	   String sucflag="0";
	   /***改为保全去人用标记来确定，missionprop1从js传入**/
	  // if("0000000009".equals(sActivityID))
		if("1".equals(ConfirmFlag))
	   {	   
	    
        /*String sql1 ="select a.missionprop1 from lwmission a where a.missionid='"+sMissionID+"' and activityid = '"+sActivityID+"' ";
        
        tTransferData.setNameAndValue("SQL", sql1);
        tVData = new VData();
        tVData.add(tTransferData);
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
        {
            xEdorAcceptNo=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
        }		*/   
        xEdorAcceptNo=request.getParameter("EdorAcceptNo");
        tTransferData=new TransferData();
        tTransferData.setNameAndValue("OperatedNo", xEdorAcceptNo);
        tTransferData.setNameAndValue("LockModule", "BQCA01");
        tTransferData.setNameAndValue("Operator",  tG.Operator);
        tVData = new VData();
        tVData.add(tTransferData);			
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData, "lock3String", "PubConcurrencyLockUI"))
				{
					  Content = "申请失败，原因是:"+tBusinessDelegate.getCErrors().getFirstError();
				    FlagStr = "Fail";
				    sucflag = "1";
				} 
 
	   }
		  // 准备传输数据 VData
		  tVData = new VData();
      //tTransferData.setNameAndValue("ApplyType", sApplyType);	
      tTransferData.setNameAndValue("MissionID", sMissionID);
      tTransferData.setNameAndValue("SubMissionID", sSubMissionID);
      tTransferData.setNameAndValue("ActivityID", sActivityID);		
		  
		  tVData.add( tTransferData );
		  tVData.add( tG );
		
		// 数据传输
 		
		if("0".equals(sucflag))
		{
         String busiName="cbcheckUWApplyUI";
         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         if(!tBusinessDelegate.submitData(tVData,"",busiName))
         {    
             if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
             { 
		     		   Content = "申请失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
		     		   FlagStr = "Fail";
		     		 }
		     		 else
		     		 {
		     		   Content = "操作失败";
		     		   FlagStr = "Fail";				
		     		 }
		     }
		     else
		     {
				    Content ="申请成功！";
		    	  FlagStr = "Succ";					     
		     }	
		     
		}

  }
  catch(Exception e)
  {
	   e.printStackTrace();
	   Content = Content.trim()+".提示：异常终止!";
  }
  finally
  {
	  
	  	if(xEdorAcceptNo != null && !xEdorAcceptNo.equals("")){
			  TransferData lockTransferData=new TransferData();
			  VData lockVData = new VData();
			 		

			  lockTransferData.setNameAndValue("OperatedNo", xEdorAcceptNo);
			  lockTransferData.setNameAndValue("LockModule", "BQCA01");

			  lockVData.add(lockTransferData);	
		      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(lockVData, "unLockJSP", "PubConcurrencyLockUI"))
			  {
				 Content = "撤销失败，原因是:"+ tBusinessDelegate.getCErrors().getFirstError();
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
