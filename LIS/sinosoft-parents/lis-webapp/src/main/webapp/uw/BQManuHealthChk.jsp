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
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
  //loggerDebug("BQManuHealthChk","start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";


  GlobalInput tGlobalInput = new GlobalInput();
  ExeSQL tExeSQL = new ExeSQL();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
    out.println("session has expired");
    return;
  }

  	loggerDebug("BQManuHealthChk","step1..................................");
    // 投保单列表
    LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();

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
    String tEdorNo = request.getParameter("EdorNo");
    String tEdorAcceptNo ="";
    //体检项目
    String tHealthItem = request.getParameter("CheckedItem");
    //体检类型
    String tHealthCode = request.getParameter("HealthCode");
    String tCustomerType = request.getParameter("CustomerType");
    loggerDebug("BQManuHealthChk","CustomerType: "+tCustomerType);
    String tAction = request.getParameter("Action");
    if(tAction==null||tAction.equals(""))
    {
    	tAction = "INSERT";
    }
    String tPrtSeq = request.getParameter("PrtSeq");
    loggerDebug("BQManuHealthChk","step2..................................tHealthItem:"+tHealthItem+":tHealthCode:"+tHealthCode);

 		
    boolean flag = true;
	String EdorAcceptSql = "select a.edoracceptno from lpedormain a where a.edorno='"+tEdorNo+"'";
	tEdorAcceptNo = tExeSQL.getOneValue(EdorAcceptSql);
    //loggerDebug("BQManuHealthChk","count:"+ChkCount);
        //体检资料一
        tLPPENoticeSchema.setContNo(tContNo);
        tLPPENoticeSchema.setPrtSeq(tPrtSeq);
        tLPPENoticeSchema.setEdorNo(tEdorNo);
        tLPPENoticeSchema.setEdorAcceptNo(tEdorAcceptNo);
        //tLPPENoticeSchema.setProposalContNo(tContNo);
        //tLPPENoticeSchema.setPEAddress(tHospital);
        tLPPENoticeSchema.setPEDate(tEDate);
        tLPPENoticeSchema.setPEBeforeCond(tIfEmpty);
        tLPPENoticeSchema.setRemark(tNote);
        tLPPENoticeSchema.setCustomerNo(tInsureNo);
        tLPPENoticeSchema.setPEReason(tPEReason);  
        tLPPENoticeSchema.setCustomerNoType(tCustomerType);

    loggerDebug("BQManuHealthChk","flag:"+flag);
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
       tTransferData.setNameAndValue("LPPENoticeSchema",tLPPENoticeSchema);
       tTransferData.setNameAndValue("HealthItem",tHealthItem);
       tTransferData.setNameAndValue("HealthCode",tHealthCode);
       tTransferData.setNameAndValue("OtherItem",tOtherItem);
       tTransferData.setNameAndValue("Flag",tFlag);
       tTransferData.setNameAndValue("EdorNo",tEdorNo);


      // tVData.add(tGlobalInput);
      // tVData.add(tTransferData);
       //tVData.add(tLPPENoticeSchema);
        // 数据传输
      
        String busiName="BQAutoHealthUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        try
            {        
            	
			  tTransferData.setNameAndValue("BusiType", "1002");
              tVData.add(tGlobalInput);
              tVData.add(tTransferData);
              tVData.add(tLPPENoticeSchema);    
              tBusinessDelegate.submitData(tVData, tAction,busiName);
             /* 
              
             BQSendNoticeAfterInitService tBQSendNoticeAfterInitService=new BQSendNoticeAfterInitService();
     		 tBQSendNoticeAfterInitService.submitData(tVData,"");
     		 VData qVData=tBQSendNoticeAfterInitService.getResult();
     		 MMap pedorMap =(MMap) qVData.getObjectByObjectName("MMap",0); 
     		 VData tResult=new VData();
     		 tResult.add(pedorMap);
     		 PubSubmit tPubSubmit = new PubSubmit();
     		 tPubSubmit.submitData(tResult, "");
            */  
              
            }
            catch(Exception ex)
            {
                  Content = "体检资料录入失败，原因是:" + ex.toString();
                  FlagStr = "Fail";
            }
            
            /* 
        BQAutoHealthBL tBQAutoHealthBL = new BQAutoHealthBL();
        if(tBQAutoHealthBL.submitData(tVData,tAction)==false)
        {
        	 int n = tBQAutoHealthBL.mErrors.getErrorCount();
             Content = "体检资料录入失败，原因是:";
             Content = Content + tBQAutoHealthBL.mErrors.getError(0).errorMessage;
             FlagStr = "Fail";
        }
        */
        //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr == "Fail")
        {
        	//BQAutoHealthUI tBQAutoHealthUI = new BQAutoHealthUI();
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content = "体检资料录入成功! ";
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

<%@page import="com.sinosoft.workflow.bq.BQSendNoticeAfterInitService"%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
