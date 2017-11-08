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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
  //loggerDebug("RnewManuHealthChk","start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";


  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
    out.println("session has expired");
    return;
  }

  loggerDebug("RnewManuHealthChk","step1..................................");
    // 投保单列表
    RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();

    String tContNo = request.getParameter("ContNo");
    String tInsureNo = request.getParameter("InsureNo");
    String tHospital = request.getParameter("Hospital");
    String tPEReason = request.getParameter("PEReason");
    String tPrtNo =  request.getParameter("PrtNo");
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
    loggerDebug("RnewManuHealthChk","step2..................................tHealthItem:"+tHealthItem+":tHealthCode:"+tHealthCode);

 		
    boolean flag = true;

    //loggerDebug("RnewManuHealthChk","count:"+ChkCount);
        //体检资料一
        tRnewPENoticeSchema.setContNo(tContNo);
        tRnewPENoticeSchema.setPrtSeq(tPrtSeq);
      //  tRnewPENoticeSchema.setProposalContNo(tContNo);
        //tRnewPENoticeSchema.setPEAddress(tHospital);
        tRnewPENoticeSchema.setPEDate(tEDate);
        tRnewPENoticeSchema.setPEBeforeCond(tIfEmpty);
        tRnewPENoticeSchema.setRemark(tNote);
        tRnewPENoticeSchema.setCustomerNo(tInsureNo);
        tRnewPENoticeSchema.setPEReason(tPEReason);  
        tRnewPENoticeSchema.setCustomerType(tCustomerType);

    loggerDebug("RnewManuHealthChk","flag:"+flag);
  if (flag == true)
  {
        // 准备传输数据 VData
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("ContNo",tContNo);
       tTransferData.setNameAndValue("PrtNo",tPrtNo);
       tTransferData.setNameAndValue("CustomerNo",tInsureNo );
       tTransferData.setNameAndValue("MissionID",tMissionID );
       tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
     	 tTransferData.setNameAndValue("RnewPENoticeSchema",tRnewPENoticeSchema);
       tTransferData.setNameAndValue("HealthItem",tHealthItem);
       tTransferData.setNameAndValue("HealthCode",tHealthCode);
       tTransferData.setNameAndValue("OtherItem",tOtherItem);
       tTransferData.setNameAndValue("Flag",tFlag);

       tVData.add(tGlobalInput);
       tVData.add(tTransferData);
       tVData.add(tRnewPENoticeSchema);
        // 数据传输
        /*
        RnewAutoHealthBL tRnewAutoHealthBL = new RnewAutoHealthBL();
        if(tRnewAutoHealthBL.submitData(tVData,tAction)==false)
        {
        	 int n = tRnewAutoHealthBL.mErrors.getErrorCount();
             Content = "体检资料录入失败，原因是:";
             Content = Content + tRnewAutoHealthBL.mErrors.getError(0).errorMessage;
             FlagStr = "Fail";
        }
        */
        String busiName="RnewAutoHealthUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
          if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
          {    
              if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
              { 
            	  int n = tBusinessDelegate.getCErrors().getErrorCount();
      				Content = "体检资料录入失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
      				FlagStr = "Fail";
      				}
      	 }
    
        //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegate.getCErrors();
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
        }
    }
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
