<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealthChk.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  //loggerDebug("LLUWManuHealthChk","start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";


  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
    out.println("session has expired");
    return;
  }

  loggerDebug("LLUWManuHealthChk","step1..................................");
    // 投保单列表
    LLUWPENoticeSchema tLLUWPENoticeSchema = new LLUWPENoticeSchema();

    String tContNo = request.getParameter("ContNo");
    String tInsureNo = request.getParameter("InsureNo");
    String tHospital = request.getParameter("Hospital");
    String tPEReason = request.getParameter("PEReason");
    String tPrtNo =  request.getParameter("PrtNo");
    String tRgtNo =  request.getParameter("RgtNo");
    String tBatchNo =  request.getParameter("BatchNo");
    
    String tIfEmpty = request.getParameter("IfEmpty");
    String tEDate = request.getParameter("EDate");
    String tNote = request.getParameter("Note");
    String tOtherItem = request.getParameter("otheritem2");
    String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tFlag = request.getParameter("Flag");
    //体检项目
    String tHealthItem = request.getParameter("CheckedItem");
    //体检类型
    String tHealthCode = request.getParameter("HealthCode");
    String tCustomerType = request.getParameter("CustomerType");
    String tAction = request.getParameter("Action");
    if(tAction==null||tAction.equals(""))
    {
    	tAction = "INSERT";
    }
    String tPrtSeq = request.getParameter("PrtSeq");
    loggerDebug("LLUWManuHealthChk","step2..................................tHealthItem:"+tHealthItem+":tHealthCode:"+tHealthCode);

 		
    boolean flag = true;

    //loggerDebug("LLUWManuHealthChk","count:"+ChkCount);
        //体检资料一
        tLLUWPENoticeSchema.setContNo(tContNo);
        tLLUWPENoticeSchema.setPrtSeq(tPrtSeq);
      //  tLCPENoticeSchema.setProposalContNo(tContNo);
        //tLCPENoticeSchema.setPEAddress(tHospital);
        tLLUWPENoticeSchema.setPEDate(tEDate);
        tLLUWPENoticeSchema.setPEBeforeCond(tIfEmpty);
        tLLUWPENoticeSchema.setRemark(tNote);
        tLLUWPENoticeSchema.setCustomerNo(tInsureNo);
        tLLUWPENoticeSchema.setPEReason(tPEReason);
        tLLUWPENoticeSchema.setCustomerType(tCustomerType);
        tLLUWPENoticeSchema.setClmNo(tRgtNo);
        tLLUWPENoticeSchema.setBatNo(tBatchNo);
        

    loggerDebug("LLUWManuHealthChk","flag:"+flag);
  if (flag == true){
        // 准备传输数据 VData
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("ContNo",tContNo);
       tTransferData.setNameAndValue("PrtNo",tPrtNo);
       tTransferData.setNameAndValue("CustomerNo",tInsureNo );
       tTransferData.setNameAndValue("MissionID",tMissionID );
       tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
     	 tTransferData.setNameAndValue("LLUWPENoticeSchema",tLLUWPENoticeSchema);
       tTransferData.setNameAndValue("HealthItem",tHealthItem);
       tTransferData.setNameAndValue("HealthCode",tHealthCode);
       tTransferData.setNameAndValue("OtherItem",tOtherItem);
       tTransferData.setNameAndValue("Flag",tFlag);
       tTransferData.setNameAndValue("RgtNo",tRgtNo);
       tTransferData.setNameAndValue("BatchNo",tBatchNo);

       tVData.add(tGlobalInput);
       tVData.add(tTransferData);
       tVData.add(tLLUWPENoticeSchema);
        // 数据传输
        /*LLUWAutoHealthBL tLLUWAutoHealthBL = new LLUWAutoHealthBL();
        if(tLLUWAutoHealthBL.submitData(tVData,tAction)==false)
        {
        	 int n = tLLUWAutoHealthBL.mErrors.getErrorCount();
             Content = "体检资料录入失败，原因是:";
             Content = Content + tLLUWAutoHealthBL.mErrors.getError(0).errorMessage;
             FlagStr = "Fail";
        }
    
        //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr == "Fail")
        {
            tError = tLLUWAutoHealthBL.mErrors;
            if (!tError.needDealError())
            {
               if(!tAction.equals("DELETE"))
               {
                Content = "体检资料录入成功! ";
                }
              else
              	{
              	 Content = "体检资料删除成功! ";
              	}
                FlagStr = "Succ";
            }
            else
            {
                Content = "体检资料录入失败，原因是:" + tError.getFirstError();
                FlagStr = "Fail";
            }
        }*/
        String busiName="LLUWAutoHealthBL";
        String mDescType="体检资料录入";
        if(tAction.equals("DELETE"))
        {
        	mDescType="体检资料删除";
        }
      	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      	  if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
      	  {    
      	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
      	       { 
      				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
      				FlagStr = "Fail";
      		   }
      		   else
      		   {
      				Content = mDescType+"失败";
      				FlagStr = "Fail";				
      		   }
      	  }
      	  else
      	  {
      	     	Content = mDescType+"成功! ";
      	      	FlagStr = "Succ";  
      	  }
    }
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
