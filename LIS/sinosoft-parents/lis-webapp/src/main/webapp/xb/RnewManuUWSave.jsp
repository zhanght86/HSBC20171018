<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorAppManuUWSave.jsp
//程序功能：保全人工核保
//创建日期：2005-06-02 15:59:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.xb.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
  
  CErrors tError = null;
  
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
    
    String UWType       = request.getParameter("UWType");  //核保类型
      
    String tContNo      = request.getParameter("ContNo");
    String tPrtNo      = request.getParameter("PrtNo");
    String tPolNo       = request.getParameter("PolNo");
    String tInsuredNo   = request.getParameter("InsuredNo");
    
    String tMissionID   = request.getParameter("MissionID");
    String tSubMissionID= request.getParameter("SubMissionID");
    String tActivityID= request.getParameter("ActivityID");
    String tOtherNo     = request.getParameter("OtherNo");
    String tOtherNoType = request.getParameter("OtherNoType");
    String tEdorAppName = request.getParameter("EdorAppName");
    String tApptype     = request.getParameter("Apptype");
    String tManageCom   = request.getParameter("ManageCom");
    String tAppntName   = request.getParameter("AppntNamew");
    String tAppntNo     = request.getParameter("AppntNo");
    String tPaytoDate   = request.getParameter("PaytoDate");
    
    String tUWIdea      = request.getParameter("UWIdea");  //核保意见
    String tUWFlag      = request.getParameter("EdorUWState");  //核保结论
    String tUWDelay     = request.getParameter("UWDelay");      //延长时间
    String tAppUser     = request.getParameter("UWPopedomCode"); //上报级别
    String mtestRnewFlag = "2";

    //人工核保信息
    tTransferData.setNameAndValue("UWFlag", tUWFlag);
    tTransferData.setNameAndValue("UWIdea", tUWIdea);
    tTransferData.setNameAndValue("PostponeDate", tUWDelay);
    tTransferData.setNameAndValue("AppUser", tAppUser);
    
    //创建工作流节点[保全复核]需要的数据
    tTransferData.setNameAndValue("MissionID", tMissionID);
    tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
    tTransferData.setNameAndValue("OtherNo", tOtherNo);
    tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
    tTransferData.setNameAndValue("EdorAppName", tEdorAppName);
    tTransferData.setNameAndValue("Apptype", tApptype);
    tTransferData.setNameAndValue("ManageCom", tManageCom);
    tTransferData.setNameAndValue("AppntName", tAppntName);
    tTransferData.setNameAndValue("PaytoDate", tPaytoDate);
    tTransferData.setNameAndValue("ContNo", tContNo);
    tTransferData.setNameAndValue("PrtNo", tPrtNo);
    tTransferData.setNameAndValue("AppntNo", tAppntNo);
    tTransferData.setNameAndValue("BusiType", "1004");
    tTransferData.setNameAndValue("testRnewFlag", mtestRnewFlag); 
  tTransferData.setNameAndValue("ActivityID", tActivityID);    
   // RnewWorkFlowUI tRnewWorkFlowUI = new RnewWorkFlowUI(); 
     // String busiName="OrderManageUI";
      String busiName="WorkFlowUI";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();       
    try
    {   
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);     
        tBusinessDelegate.submitData(tVData, "create",busiName);
    }
    catch(Exception ex)
    {
          Content = "续保二核核保失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {                          
                Content ="续保二核核保成功！";
                FlagStr = "Succ";
            }
            else                                                                           
            {
                Content = "续保二核核保失败，原因是:" + tError.getFirstError();
                FlagStr = "Fail";
            }
    }   
%>
   
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
