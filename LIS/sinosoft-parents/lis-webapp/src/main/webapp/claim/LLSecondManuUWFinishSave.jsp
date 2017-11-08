<%
//程序名称：LLSecondManuUWFinishSave.jsp
//程序功能：理赔人工核保----[核保完成]
//创建日期：2002-09-24 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容
%> 

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//输入参数

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
 
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLSecondManuUWFinishSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
//    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    String transact = "Finish||ToClaim"; //获取操作insert||update
    
    String tMissionID = request.getParameter("tMissionid");
	String tSubMissionID = request.getParameter("tSubmissionid");
	String tActivityID = request.getParameter("tActivityid");
	
	String tUWIdea = request.getParameter("UWIdea");
	String tUWIdea2 = request.getParameter("UWIdea2");
	String tCaseNo = request.getParameter("tCaseNo"); //赔案分案号
	String tBatNo = request.getParameter("tBatNo"); ////批次号
	String tInsuredNo = request.getParameter("tInsuredNo"); //出险人客户号
	String tClaimRelFlag = request.getParameter("tClaimRelFlag"); //赔案相关标志			
	String UWFlag = request.getParameter("lluwflag"); //赔案相关标志			
	
    //*******************获取页面信息 *****************************  
    LLCUWMasterSet tLLCUWMasterSet=new LLCUWMasterSet(); //个人合同理陪核保最近结果表
//    LLCUWMasterSchema tLLCUWMasterSchema=new LLCUWMasterSchema(); 
    String tGridNo[] = request.getParameterValues("LLCUWBatchGridNo");  //得到序号列的所有值
	String tContNo[] = request.getParameterValues("LLCUWBatchGrid3"); //得到第1列的所有值《合同号码》
	int  Count = tGridNo.length; //得到接受到的记录数
	for(int index=0;index<Count;index++)
	{
		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema(); //个人保单表
		tLLCUWMasterSchema.setCaseNo(tCaseNo); //赔案分案号		
		tLLCUWMasterSchema.setContNo(tContNo[index]); //合同号
        tLLCUWMasterSet.add(tLLCUWMasterSchema);
	}
    
 	//打包数据《节点信息》
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("ContNo", tContNo[0]);
	mTransferData.setNameAndValue("ClmNo", tCaseNo);
	mTransferData.setNameAndValue("CaseNo", tCaseNo);
	mTransferData.setNameAndValue("BatNo", tBatNo);
	mTransferData.setNameAndValue("InsuredNo", tInsuredNo);
	mTransferData.setNameAndValue("ClaimRelFlag", tClaimRelFlag);
	mTransferData.setNameAndValue("MissionID", tMissionID);
	mTransferData.setNameAndValue("SubMissionID", tSubMissionID);
	mTransferData.setNameAndValue("ActivityID", tActivityID);
	mTransferData.setNameAndValue("UWFlag", UWFlag);
	mTransferData.setNameAndValue("UWIdea2", tUWIdea2);
//    loggerDebug("LLSecondManuUWFinishSave","------m"MissionID"------"+ mTransferData.getValueByName("MissionID"));
//    loggerDebug("LLSecondManuUWFinishSave","------"tSubMissionID"------"+ mTransferData.getValueByName("SubMissionID"));
//    loggerDebug("LLSecondManuUWFinishSave","------"tActivityID"------"+ mTransferData.getValueByName("ActivityID"));
	
	
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(mTransferData); 
        tVData.add(tLLCUWMasterSet);
        //String wFlag="0000005520";    
        try
        {
            //loggerDebug("LLSecondManuUWFinishSave","---ClaimWorkFlowUI submitData and transact="+wFlag);
            /*ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = "提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
        	{
        		//从结果集中取出前台需要数据
//                tVData.clear();
//                tVData = tClaimWorkFlowUI.getResult();
//                loggerDebug("LLSecondManuUWFinishSave","tVData="+tVData);	    
                Content = "数据提交成功";
                FlagStr = "Succ";            
        	}*/
        	String busiName="tWorkFlowUI";
        		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        		  if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
        		  {    
        		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        		       { 
        					Content = "提交工作流失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
        					FlagStr = "Fail";
        			   }
        			   else
        			   {
        					Content = "提交工作流失败";
        					FlagStr = "Fail";				
        			   }
        		  }
        		  else
        		  {
        		     	Content = "数据提交成功! ";
        		      	FlagStr = "Succ";  
        		  }
    	}
    	catch(Exception ex)
        {
        	Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }    
    catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLSecondManuUWFinishSave","------LLSecondManuUWFinishSave.jsp end------");
    loggerDebug("LLSecondManuUWFinishSave",FlagStr);
    loggerDebug("LLSecondManuUWFinishSave",Content);
}    
%>
<html>
<script language="javascript">
    parent.fraInterface.UWFinishafterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
