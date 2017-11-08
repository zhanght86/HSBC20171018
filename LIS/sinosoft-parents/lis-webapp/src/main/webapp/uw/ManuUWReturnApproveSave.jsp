<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--用户校验类--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuNormChk.jsp
//程序功能：人工核保最终结论录入保存
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期    更新原因/内容
//            续涛      2005-04-20  呈报
//            韩霖      2005-06-15  呈报
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>  
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
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
  String tuwPopedom = request.getParameter("uwPopedom");                  //呈报到的核保级别
  String tuwPer = request.getParameter("uwPer");                          //呈报指定的核保师
  String tUpUserCode = request.getParameter("UWPopedomCode");   
  String tSugIndUWFlag = request.getParameter("SugIndUWFlag");
  String tSugIndUWIdea = request.getParameter("SugIndUWIdea");
  String tChangeIdea = request.getParameter("ChangeIdea"); 
  
            //上报核保级别
  VData tVData = new VData();
  String tContNo = "";
  tContNo = request.getParameter("ContNo");

//  loggerDebug("ManuUWReturnApproveSave","UWIDEA:"+tUWIdea);
//  loggerDebug("ManuUWReturnApproveSave","ContNo:"+tContNo);
//  loggerDebug("ManuUWReturnApproveSave","uwflag+"+tUWFlag);
  

  try
  {
       
        FlagStr = "Succ";		
        String tUWSendFlag;
        String tUserCode;

        loggerDebug("ManuUWReturnApproveSave","----------------返回复核节点---开始----------------");			


        String tReDisMark = "0000001001";
        tuwUpReport = "9999";
        
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
               
        nTransferData.setNameAndValue("UWFlag",tUWFlag); //准备返回到复核，置上复核的节点号
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
               
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport); //准备返回到复核，置上复核的节点号
        
        nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
        nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        //
        nTransferData.setNameAndValue("ReDisMark",tReDisMark);
        nTransferData.setNameAndValue("ReturnCheck","Y"); //Y代表返回复核
        
                
        loggerDebug("ManuUWReturnApproveSave","----合同号:["+tContNo+"]");
        loggerDebug("ManuUWReturnApproveSave","----任务号:["+tMissionID+"]");
        loggerDebug("ManuUWReturnApproveSave","----子任务号:["+tSubMissionID+"]");
        loggerDebug("ManuUWReturnApproveSave","----UWIdea:["+tUWIdea+"]");
        
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);

        TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
       if (tTbWorkFlowUI.submitData(tVData,"0000001110") == false)
        {
          int n = tTbWorkFlowUI.mErrors.getErrorCount();
          for (int i = 0; i < n; i++)
            {
                Content = "返回复核岗失败，原因是：" + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
       else
       {
		    	Content = "返回复核岗操作成功!";
		    	FlagStr = "Succ";
        }
                					
        loggerDebug("ManuUWReturnApproveSave","----------------返回复核节点---结束----------------");
                                     
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".提示：异常终止!";
  }

  loggerDebug("ManuUWReturnApproveSave","flag="+FlagStr);
  loggerDebug("ManuUWReturnApproveSave","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterReturnApprove("<%=FlagStr%>","<%=Content%>");    
</script>
</html>
