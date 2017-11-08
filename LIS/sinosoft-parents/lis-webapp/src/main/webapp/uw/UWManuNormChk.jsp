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
//            ln      2008-10-17  对整个合同下结论
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
  String FBFlagStr = "Succ";  //分保标志
  GlobalInput tG = new GlobalInput();
  ExeSQL tExeSQL=new ExeSQL();
  SSRS tSSRS=new SSRS();
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
      out.println("session has expired");
      return;
  }
  
  	String tContNo = request.getParameter("ContNo");
  	String tFBFlag = request.getParameter("FBFlag");
  	String tAddFeeFlag = request.getParameter("AddFeeFlag");
  	//tongmeng 2007-12-14 Add
  	//增加上报用户,上报的核保师级别
  	String tUWPopedomCode = request.getParameter("TUWPopedomCode");
  	String tUWPopedomGrade = request.getParameter("TUWPopedomGrade");
	loggerDebug("UWManuNormChk","FB ContNo=="+tContNo);
	loggerDebug("UWManuNormChk","FB Flag=="+tFBFlag);
	loggerDebug("UWManuNormChk","FB AddFeeFlag=="+tAddFeeFlag);
	if(tAddFeeFlag.equals("Y"))
	{
		try
		{	
			VData tVData = new VData();
   			LCContSchema tLCContSchema = new LCContSchema();
   			tLCContSchema.setContNo(tContNo);
   			tVData.add(tLCContSchema);
   			//UWFBCal tUWFBCal = new UWFBCal();
   			
   		   String busiName1="cbcheckUWFBCal";
   		   BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
   			
   			if(!tBusinessDelegate1.submitData(tVData,"",busiName1))
   			{
   		    	loggerDebug("UWManuNormChk","不需要临分");
   		    	FBFlagStr = "Succ";
   			}
   			else
   			{
   		    	loggerDebug("UWManuNormChk","需要临分");
   		    	FBFlagStr = "FB";
   		    	Content = "需要进行再保呈报，不允许进行核保确认！";
   			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" 提示:异常退出.";
		}
	}
	loggerDebug("UWManuNormChk","-----------------flagStr="+FlagStr);
  if(FBFlagStr.equals("Succ"))
  {
  // 投保单列表
  LCContSet tLCContSet = new LCContSet();
  LCContSchema tLCContSchema=new LCContSchema();
  LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();                  //个人合同核保最近结果表
  LCUWSendTraceSchema tLCUWSendTraceSchema = new LCUWSendTraceSchema();   //核保上报轨迹表
  
  String tUWFlag = request.getParameter("uwState");		                    //核保结论
  String tUWIdea = request.getParameter("UWIdea");                        //核保意见
  String tvalidate = request.getParameter("UWDelay");                     //延长时间
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
//ln 2008-10-17 add 上报原因 撤单原因 延期原因 拒保原因 投保建议
  String tUpReason = request.getParameter("UWUpperReason");
  //modify by ln 2009-2-9 记录撤单原因 延期原因 拒保原因编码
  String tWithDReasonCode = request.getParameter("UWWithDReasonCode");
  String tWithDReason = request.getParameter("UWWithDReason");
  String tDelayReasonCode = request.getParameter("DelayReasonCode");
  String tDelayReason = request.getParameter("UWDelayReason");
  String tRefuReasonCode = request.getParameter("UWRefuReasonCode"); 
  String tRefuReason = request.getParameter("UWRefuReason");  
  String tSuggestCont = request.getParameter("SuggestCont");
  String tSugContInput = "";
  if(tSuggestCont!=null && tSuggestCont.equals("1")){
	  tSugContInput = request.getParameter("SugContInput");
    }
  //end add ln 2008-10-17
            //上报核保级别
  VData tVData = new VData();



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
        
           	  String busiName2="cbcheckUWManuChgPlanConclusionChkUI";
   		      BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
        
			  if (tBusinessDelegate2.submitData(tVData, "",busiName2) == false)
			  {
			    int n = tBusinessDelegate2.getCErrors().getErrorCount();
			    for (int i = 0; i < n; i++)
			    Content = " 承保计划变更原因录入失败，原因是: " + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
			    FlagStr = "Fail";
			  }
			  else
			  {
			  	 Content = " 承保计划变更成功！";
			  	 FlagStr = "Succ";
			  }
	  }
			//tongmeng 2007-10-30 modify
			//增加撤单结论
			//tongmeng 2009-01-15 modify
			//如果 上报的话,可以使用5结论
	  else if((tUWFlag.equals("1")||tUWFlag.equals("2")||tUWFlag.equals("4")||tUWFlag.equals("9")||tUWFlag.equals("a"))
	  	      ||(!tuwUpReport.equals("0")&&tUWFlag.equals("5"))
	  	
	  	)
	  {
        FlagStr = "Succ";
        loggerDebug("UWManuNormChk","#################1");
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
        nTransferData.setNameAndValue("ValiDate",tvalidate);
        loggerDebug("UWManuNormChk","#################1");
		//检查是否有已下发问题件、通知书
        String SendFlag=tExeSQL.getOneValue("select count(*) from lcissuepol where contno='"+tContNo+"' and state='0'");
        String ReportFlag=tExeSQL.getOneValue("select count(*) from LCRReport where contno='"+tContNo+"' and ReplyFlag='0'");
        String PeFlag=tExeSQL.getOneValue("select count(*) from LCPENotice where contno='"+tContNo+"' and PrintFlag='0'");
        if(!"0".equals(SendFlag) || !"0".equals(ReportFlag) || !"0".equals(PeFlag)){
        	nTransferData.setNameAndValue("UWFlag","5");//如果有已下发的问题件、通知书核保结论默认为“5”	
        }else{
        	nTransferData.setNameAndValue("UWFlag",tUWFlag);	
        }
        
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
        loggerDebug("UWManuNormChk","#################1");
        
        
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        //增加工作流参数
        nTransferData.setNameAndValue("BusiType", "1001");
        //String sqlStr="select activityid from lwmission where MissionID = '"+tMissionID+"'  and activityid in(select activityid from lwactivity where functionid='10010028') ";//and missionprop1='"+tPrtNo+"'
        //String ActivityID=tExeSQL.getOneValue(sqlStr);
        String ActivityID = request.getParameter("ActivityID");//add by lzf
    	nTransferData.setNameAndValue("ActivityID",ActivityID); 
        String UWAuthority = request.getParameter("UWAuthority");
        nTransferData.setNameAndValue("UWAuthority", UWAuthority);
        System.out.println("UWAuthority==="+UWAuthority);
        
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport);
        loggerDebug("UWManuNormChk","#################1");
        nTransferData.setNameAndValue("UWPopedomCode",tUWPopedomCode);
 				nTransferData.setNameAndValue("UWPopedomGrade",tUWPopedomGrade);
        //nTransferData.setNameAndValue("UWPopedom",tuwPopedom);
        //nTransferData.setNameAndValue("UWPer",tuwPer);
        
//        if((tuwUpReport!=null)&&!(tuwUpReport.equals("0"))){
        
          nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
          nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        //ln 2008-10-17 add 撤单原因 延期原因 拒保原因 投保建议
          nTransferData.setNameAndValue("WithDReasonCode",tWithDReasonCode);
          nTransferData.setNameAndValue("DelayReasonCode",tDelayReasonCode);
          nTransferData.setNameAndValue("RefuReasonCode",tRefuReasonCode);
          nTransferData.setNameAndValue("WithDReason",tWithDReason);
          nTransferData.setNameAndValue("DelayReason",tDelayReason);
          nTransferData.setNameAndValue("RefuReason",tRefuReason);
          nTransferData.setNameAndValue("SugContInput",tSugContInput);          
          
        loggerDebug("UWManuNormChk","#################1");
          //核保上报轨迹表
          tLCUWSendTraceSchema.setOtherNo(tContNo);		
          tLCUWSendTraceSchema.setOtherNoType("1");                   //个险 
          tLCUWSendTraceSchema.setUWFlag(tUWFlag);
          tLCUWSendTraceSchema.setUWIdea(tUWIdea);
         // ln 2008-10-17 add 上报原因
          tLCUWSendTraceSchema.setUpReason(tUpReason);
//        tLCUWSendTraceSchema.setYesOrNo(tYesOrNo);
        loggerDebug("UWManuNormChk","#################1");
          //nTransferData.setNameAndValue("LCUWSendTraceSchema",tLCUWSendTraceSchema);
//        }
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tLCUWSendTraceSchema);
        tVData.add(tG);

        //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
        //String busiName3="tbTbWorkFlowUI";
        String busiName3="tWorkFlowUI";
   		BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
   		      
        //if (tBusinessDelegate3.submitData(tVData,"0000001110",busiName3) == false)
        if (tBusinessDelegate3.submitData(tVData,"excute",busiName3) == false)
        {
          int n = tBusinessDelegate3.getCErrors().getErrorCount();
          for (int i = 0; i < n; i++)
          Content = "人工核保失败，原因是：" + tBusinessDelegate3.getCErrors().getError(0).errorMessage;
          FlagStr = "Fail";
        }
        else{
			    	Content = "人工核保操作成功!";
			    	FlagStr = "Succ";
        }
                
			  //如果在Catch中发现异常，则不从错误类中提取错误信息
			  if (FlagStr == "Fail") //if 5
			  {
			   
			    tError = tBusinessDelegate3.getCErrors();
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
  }

  loggerDebug("UWManuNormChk","flag="+FlagStr);
  loggerDebug("UWManuNormChk","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
