<%
//**************************************************************************************************
//Name：LLCaseBackInputSave.jsp
//Function：案件回退类信息保存提交
//Author：wanzh
//Date: 2005-10-21 14:33
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    if(tG == null) 
    {
        loggerDebug("LLCaseBackInputSave","登录信息没有获取!!!");
       return;
     } 
     loggerDebug("LLCaseBackInputSave","合同自动处理");
    //准备数据容器信息
    
    String tBackType=request.getParameter("BackTypeQ");
    String busiName="LLCaseBackUI";
    
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("BackNo", request.getParameter("BackNo")); //回退号
      
    LLCaseBackSchema tLLCaseBackSchema = new LLCaseBackSchema();
    tLLCaseBackSchema.setClmNo(request.getParameter("ClmNo"));
    tLLCaseBackSchema.setBackDesc(request.getParameter("BackDesc"));
    tLLCaseBackSchema.setRemark(request.getParameter("Remark"));
    tLLCaseBackSchema.setBackReason(request.getParameter("BackReason"));
    tLLCaseBackSchema.setBackType(tBackType); //回退类型 0-已财务给付回退,1-已签批未财务给付回退,2-预付回退
    tLLCaseBackSchema.setNewGiveType(request.getParameter("NewGiveType")); //新理赔理论
    tLLCaseBackSchema.setApplyUser(tG.Operator); //申请人
    tLLCaseBackSchema.setCheckBackPreFlag(request.getParameter("whetherBackPre")); //是否同时回退预付信息标志
    
    int mFlag = Integer.parseInt(request.getParameter("Flag"));
    
   
    try
    {    
        if( mFlag == 1)
        {
           // 准备传输数据 VData
           VData tVData = new VData();
           tVData.add( tG );        
           tVData.add( tLLCaseBackSchema );    
           tVData.add( mTransferData );  
//           LLCaseBackUI tLLCaseBackUI = new LLCaseBackUI();
//        
//           if (!tLLCaseBackUI.submitData1(tVData,""))
//           {
//                Content = " 数据提交失败，原因是: " + tLLCaseBackUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//           } else {
//                Content = " 保存成功! ";
//                FlagStr = "Succ";
//           }
//           int n = tLLCaseBackUI.mErrors.getErrorCount();
//           for (int i = 1; i < n; i++)
//           {
//                Content = Content + "原因是: " + tLLCaseBackUI.mErrors.getError(i).errorMessage;
//                FlagStr = "Fail";
//           } 

		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,"1",busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLCaseBackInputSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content ="数据提交失败,原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "数据提交失败";
				   FlagStr = "Fail";				
				} 
		}
		else {
                Content = " 保存成功! ";
                FlagStr = "Succ";
        }

        }
        if( mFlag == 2 )
        {
           tLLCaseBackSchema.setBackNo(request.getParameter("BackNo"));
           // 准备传输数据 VData
           VData tVData = new VData();
           tVData.add( tG ); 
                  
           tVData.add( tLLCaseBackSchema );    
           tVData.add( mTransferData );  
//           LLCaseBackUI tLLCaseBackUI = new LLCaseBackUI();
//        
//           if (!tLLCaseBackUI.submitData(tVData,""))
//           {
//                Content = " 数据提交失败，原因是: " + tLLCaseBackUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//           } else {
//                Content = " 保存成功! ";
//                FlagStr = "Succ";
//           }
//           int n = tLLCaseBackUI.mErrors.getErrorCount();
//           for (int i = 1; i < n; i++)
//           {
//                Content = Content + "原因是: " + tLLCaseBackUI.mErrors.getError(i).errorMessage;
//                FlagStr = "Fail";
//           } 
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			
			if(!tBusinessDelegate1.submitData(tVData,"2",busiName))
			    {    
			        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			        { 
			        	int n = tBusinessDelegate1.getCErrors().getErrorCount();
				        for (int i = 0; i < n; i++)
				        {
				            //loggerDebug("LLCaseBackInputSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
				            Content ="数据提交失败,原因是: " + tBusinessDelegate1.getCErrors().getError(i).errorMessage;
				        }
			       		FlagStr = "Fail";				   
					}
					else
					{
					   Content = "数据提交失败";
					   FlagStr = "Fail";				
					} 
			}
			else {
	                Content = " 保存成功! ";
	                FlagStr = "Succ";
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
    
var mFlag = <%=mFlag%>;
var mBackType = <%=tBackType%>;

if(mBackType == 1 && mFlag == 1)
{
	 parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>"); //已签批没有财务给付的则可以继续在该界面操作,其他情况都需要到财务收费去交费,所以返回任务工作队列接着处理前提其他案件
}
else
{
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
}
</script>
</html>
