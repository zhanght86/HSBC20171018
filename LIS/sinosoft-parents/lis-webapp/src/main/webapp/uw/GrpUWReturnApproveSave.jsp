<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--用户校验类--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuNormChk.jsp
//程序功能：人工核保最终结论录入保存
//创建日期：2002-06-19 11:10:36
//创建人  ：Ght
//更新记录：  更新人    更新日期    更新原因/内容
//            
//            
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>  
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
      out.println("session has expired");
      return;
  }
  
  // 投保单列表
  LCContSet tLCContSet = new LCContSet();
  LCContSchema tLCContSchema=new LCContSchema();
  LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();                  //个人合同核保最近结果表
  LCUWSendTraceSchema tLCUWSendTraceSchema = new LCUWSendTraceSchema();   //核保上报轨迹表
  
  String tUWFlag = request.getParameter("uwState");		                    //核保结论
  String tUWIdea = request.getParameter("UWIdea");                        //核保意见
  //String tvalidate = request.getParameter("UWDelay");                   
  String tMissionID = request.getParameter("MissionID");                  //工作流任务号
  String tPrtNo = request.getParameter("PrtNo");                          //印刷号
  String tSubMissionID = request.getParameter("SubMissionID");	         //工作流子任务号
  String tSendFlag = request.getParameter("SendFlag");	                  //上报标志
  String tYesOrNo = request.getParameter("YesOrNo");	                    //上级回复意见，Y同意，N不同意
  String tuwUpReport = request.getParameter("uwUpReport");                //核保流向，上报标志，疑难案例
  String tuwPopedom = request.getParameter("uwPopedom");
  String tAgentCode = request.getParameter("AgentCode");  
  String tAgentGroup = request.getParameter("AgentGroup");  
  String tSaleChnl = request.getParameter("SaleChnl"); 
  String tManageCom = request.getParameter("ManageCom"); 
  String tGrpNo = request.getParameter("AppntNo"); 
  String tGrpName = request.getParameter("AppntName");
                
//  String tuwPer = request.getParameter("uwPer");                          //呈报指定的核保师
//  String tUpUserCode = request.getParameter("UWPopedomCode");   
//  String tSugIndUWFlag = request.getParameter("SugIndUWFlag");
//  String tSugIndUWIdea = request.getParameter("SugIndUWIdea");
  String tChangeIdea = request.getParameter("ChangeIdea"); 
  
            //上报核保级别
  VData tVData = new VData();
  String tContNo = "";
  tContNo = request.getParameter("GrpContNo");

//  loggerDebug("GrpUWReturnApproveSave","UWIDEA:"+tUWIdea);
//  loggerDebug("GrpUWReturnApproveSave","ContNo:"+tContNo);
//  loggerDebug("GrpUWReturnApproveSave","uwflag+"+tUWFlag);
  

  try
  {
       
        FlagStr = "Succ";		
        String tUWSendFlag;
        String tUserCode;

        loggerDebug("GrpUWReturnApproveSave","----------------返回复核节点--GGGG--开始----------------");			


        String tReDisMark = "0000002001";
        tuwUpReport = "9999";
        
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("GrpContNo",tContNo);
        nTransferData.setNameAndValue("ProposalGrpContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
        nTransferData.setNameAndValue("AgentCode",tAgentCode);       
        nTransferData.setNameAndValue("AgentGroup",tAgentGroup); 
        nTransferData.setNameAndValue("SaleChnl",tSaleChnl); 
        nTransferData.setNameAndValue("ManageCom",tManageCom); 
        nTransferData.setNameAndValue("ManageCom",tManageCom); 
        
        nTransferData.setNameAndValue("GrpNo",""); 
        nTransferData.setNameAndValue("GrpName",tGrpName); 
        nTransferData.setNameAndValue("CValiDate",""); 
        nTransferData.setNameAndValue("UWDate",""); 
        nTransferData.setNameAndValue("UWTime",""); 
        nTransferData.setNameAndValue("ReportType",""); 
        nTransferData.setNameAndValue("UWAuthority",""); 
        nTransferData.setNameAndValue("ApproveDate",""); 
        
        nTransferData.setNameAndValue("UWFlag",""); //准备返回到复核，置上复核的节点号
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
               
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport); //准备返回到复核，置上复核的节点号
        
        //nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
        //nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        //
        nTransferData.setNameAndValue("ReDisMark",tReDisMark);
        nTransferData.setNameAndValue("ReturnCheck","Y"); //Y代表返回复核
        
                
        loggerDebug("GrpUWReturnApproveSave","----合同号:["+tContNo+"]");
        loggerDebug("GrpUWReturnApproveSave","----任务号:["+tMissionID+"]");
        loggerDebug("GrpUWReturnApproveSave","----子任务号:["+tSubMissionID+"]");
        loggerDebug("GrpUWReturnApproveSave","----UWIdea:["+tUWIdea+"]");
        loggerDebug("GrpUWReturnApproveSave","----PrtNo:["+tPrtNo+"]");
        loggerDebug("GrpUWReturnApproveSave","----UWFlag:["+tUWFlag+"]");
        loggerDebug("GrpUWReturnApproveSave","----UWUpReport:["+tuwUpReport+"]");
        loggerDebug("GrpUWReturnApproveSave","----ReDisMark:["+tReDisMark+"]");
        loggerDebug("GrpUWReturnApproveSave","----ReturnCheck:[Y]");
        loggerDebug("GrpUWReturnApproveSave","----AgentCode:["+tAgentCode+"]");
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);

        //GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
        
        String busiName="tbgrpGrpTbWorkFlowUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        
       if (tBusinessDelegate.submitData(tVData,"0000002010",busiName) == false)
        {
          int n = tBusinessDelegate.getCErrors().getErrorCount();
          for (int i = 0; i < n; i++)
            {
                Content = "返回复核岗失败，原因是：" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
       else
       {
		    	Content = "返回复核岗操作成功!";
		    	FlagStr = "Succ";
        }
                					
        loggerDebug("GrpUWReturnApproveSave","----------------返回复核节点---结束----------------");
                                     
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".提示：异常终止!";
  }

  loggerDebug("GrpUWReturnApproveSave","flag="+FlagStr);
  loggerDebug("GrpUWReturnApproveSave","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterReturnApprove("<%=FlagStr%>","<%=Content%>");    
</script>
</html>
