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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>  
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
  String tSubMissionID = request.getParameter("SubConfirmMissionID");	    //工作流子任务号
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

//  loggerDebug("UWManuNormChk","UWIDEA:"+tUWIdea);
//  loggerDebug("UWManuNormChk","ContNo:"+tContNo);
//  loggerDebug("UWManuNormChk","uwflag+"+tUWFlag);
  

  try
  {
       
      FlagStr = "Succ";		
      String tUWSendFlag;
      String tUserCode;

			
      // 根据核保结论进行操作
      if(tUWFlag.equals("b"))
      {
        tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
        tLCContSchema.setContNo(request.getParameter("ContNo")); 
        tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
        tLCContSchema.setManageCom(request.getParameter("ManageCom"));  
        tLCContSchema.setRemark(tChangeIdea);   
        
        tLCContSet.add(tLCContSchema);
        tVData.add(tLCContSet);
        tVData.add(tG);
        //UWManuChgPlanConclusionChkUI tUWManuChgPlanConclusionChkUI = new UWManuChgPlanConclusionChkUI();
        String busiName="cbcheckgrpUWManuChgPlanConclusionChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
        
        
        if (tBusinessDelegate.submitData(tVData, "",busiName) == false)
			  {
			    int n = tBusinessDelegate.getCErrors().getErrorCount();
			    for (int i = 0; i < n; i++)
			    Content = " 承保计划变更原因录入失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			    FlagStr = "Fail";
			  }
			  else
			  {
			  	 Content = " 承保计划变更成功！";
			  	 FlagStr = "Succ";
			  }
			}
			else if(tUWFlag.equals("1")||tUWFlag.equals("2")||tUWFlag.equals("4")||tUWFlag.equals("9"))
			{
        FlagStr = "Succ";
        loggerDebug("UWManuNormChk","#################1");
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
        loggerDebug("UWManuNormChk","#################1");
        
        
        nTransferData.setNameAndValue("UWFlag",tUWFlag);
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
        loggerDebug("UWManuNormChk","#################1");
        
        
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport);
        loggerDebug("UWManuNormChk","#################1");
        //nTransferData.setNameAndValue("UWPopedom",tuwPopedom);
        //nTransferData.setNameAndValue("UWPer",tuwPer);
        
//        if((tuwUpReport!=null)&&!(tuwUpReport.equals("0"))){
        
          nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
          nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        loggerDebug("UWManuNormChk","#################1");
          //核保上报轨迹表
          tLCUWSendTraceSchema.setOtherNo(tContNo);		
          tLCUWSendTraceSchema.setOtherNoType("1");                   //个险 
          tLCUWSendTraceSchema.setUWFlag(tUWFlag);
          tLCUWSendTraceSchema.setUWIdea(tUWIdea);
//        tLCUWSendTraceSchema.setYesOrNo(tYesOrNo);
        loggerDebug("UWManuNormChk","#################1");
          nTransferData.setNameAndValue("LCUWSendTraceSchema",tLCUWSendTraceSchema);
//        }
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);
        String busiName="tbTbWorkFlowUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
        //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
        if (tBusinessDelegate.submitData(tVData,"0000001110",busiName) == false)
        {
          int n = tBusinessDelegate.getCErrors().getErrorCount();
          for (int i = 0; i < n; i++)
          Content = "人工核保失败，原因是：" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
          FlagStr = "Fail";
        }
        else{
			    	Content = "人工核保操作成功!";
			    	FlagStr = "Succ";
        }
                
			  //如果在Catch中发现异常，则不从错误类中提取错误信息
			  if (FlagStr == "Fail") //if 5
			  {
			   
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {
			    	Content = "人工核保操作成功!";
			    	FlagStr = "Succ";
			    }
			    else
			    {
			 	    FlagStr = "Fail";
			    }
			  }  //end of if 5
		  
		  }
		  else{
		    
			 	    FlagStr = "Fail";
			    	Content = "请选择正确的核保结论代码!";
		  
		  }
		  
		  
			
			
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".提示：异常终止!";
  }

  loggerDebug("UWManuNormChk","flag="+FlagStr);
  loggerDebug("UWManuNormChk","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
