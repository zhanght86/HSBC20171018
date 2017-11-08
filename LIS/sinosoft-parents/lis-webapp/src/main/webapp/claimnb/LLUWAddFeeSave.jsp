<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecChk.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.claimnb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
    String tOperate = "";
    boolean flag = true;
    GlobalInput tG = new GlobalInput();  
    tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
    loggerDebug("LLUWAddFeeSave","------------------理赔核保-----加费处理Save-----开始------------------");
    VData tVData = new VData();
    TransferData tTransferData = new TransferData(); 
  
	//接收信息
	LLUWPremMasterSet tLLUWPremMasterSet = new LLUWPremMasterSet();


	String tPolNo           = request.getParameter("PolNo"); //保全项目所针对的保单
	String tPolNo2          = request.getParameter("PolNo2"); //保全加费所针对的保单
	String tContNo          = request.getParameter("ContNo");
	String tAddReason       = request.getParameter("AddReason");
    String tClmNo           = request.getParameter("ClmNo");
    String tBatNo           = request.getParameter("BatNo");
        	
    String tChk[]           = request.getParameterValues("InpSpecGridSel");            
	String tdutycode[]      = request.getParameterValues("SpecGrid1"); //加费类型
    String tPayPlanType[]   = request.getParameterValues("SpecGrid2"); //加费原因
    String tstartdate[]     = request.getParameterValues("SpecGrid3"); //起始日期
	String tenddate[]       = request.getParameterValues("SpecGrid4"); //终止日期
    String tsuppriskscore[] = request.getParameterValues("SpecGrid5"); //加费评点
	String tSecondScore[]   = request.getParameterValues("SpecGrid6"); //第二被保险人加费评点
	String AddObj[]         = request.getParameterValues("SpecGrid7"); //加费对象
	String trate[]          = request.getParameterValues("SpecGrid8"); //加费金额

        
    tOperate = request.getParameter("hideOperate");
        
    loggerDebug("LLUWAddFeeSave","合同号ContNo:"+tContNo);	
    loggerDebug("LLUWAddFeeSave","批次号BatNo:"+tBatNo);       
	loggerDebug("LLUWAddFeeSave","险种号PolNo:"+tPolNo);
	loggerDebug("LLUWAddFeeSave","加费原因AddReason:"+tAddReason);
    loggerDebug("LLUWAddFeeSave","动作:"+tOperate);
    loggerDebug("LLUWAddFeeSave","个数:"+tChk.length);	
	
	int feeCount = tChk.length;
	if (feeCount == 0 ){
		Content = "请录入加费信息!";
		FlagStr = "Fail";
		flag = false;
	}else{
		if (!tPolNo.equals("")){
		
	    	//准备特约加费信息
	    	if (feeCount > 0){
                  for (int i = 0; i < feeCount; i++) {
                        if (tChk[i].equals("1") && !tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals("")) {
                                LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();
                                tLLUWPremMasterSchema.setPolNo(tPolNo);
                                tLLUWPremMasterSchema.setDutyCode(tdutycode[i]);
                                tLLUWPremMasterSchema.setPayPlanType(tPayPlanType[i]);
                                //tLLUWPremMasterSchema.setAddFeeType(tAddFeeType[i]);                                                                
                             
                                tLLUWPremMasterSchema.setPayStartDate(tstartdate[i]);
                                tLLUWPremMasterSchema.setPayEndDate(tenddate[i]);
                                
                                tLLUWPremMasterSchema.setAddFeeDirect(AddObj[i]);
                                tLLUWPremMasterSchema.setSecInsuAddPoint(tSecondScore[i]);
                                tLLUWPremMasterSchema.setPrem( trate[i]);
                                tLLUWPremMasterSchema.setSuppRiskScore( tsuppriskscore[i]);
                                tLLUWPremMasterSet.add( tLLUWPremMasterSchema );
                                flag = true;	    		  	    
                                loggerDebug("LLUWAddFeeSave","责任编码"+i+":  "+tdutycode[i]);
                            } // End of if

				  } // End of for				    
			  } // End of if
		    //准备公共传输信息
            tTransferData.setNameAndValue("ClmNo",tClmNo);
            tTransferData.setNameAndValue("BatNo",tBatNo);            
            tTransferData.setNameAndValue("ContNo",tContNo);
            tTransferData.setNameAndValue("PolNo",tPolNo);                                   
            tTransferData.setNameAndValue("AddReason",tAddReason);
            
			tVData.add(tG);
			tVData.add(tTransferData);
			tVData.add(tLLUWPremMasterSet);

			
		} // End of if
		else{
			Content = "传输数据失败!";
			flag = false;
		}
	}
    loggerDebug("LLUWAddFeeSave","flag:"+flag);
    try{
        if (flag == true){
			
            // 数据传输
//            LLUWAddFeeUI tLLUWAddFeeUI = new LLUWAddFeeUI();
//            if (!tLLUWAddFeeUI.submitData(tVData,tOperate)) {
//                int n = tLLUWAddFeeUI.mErrors.getErrorCount();
//                Content = "理赔核保加费提示信息: " + tLLUWAddFeeUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            String busiName="LLUWAddFeeUI";
		 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "理赔核保加费提示信息:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "理赔核保加费";
						   FlagStr = "Fail";				
				}
		   }
            
            //如果在Catch中发现异常，则不从错误类中提取错误信息
          if (FlagStr == "Fail"){
		          //tError = tLLUWAddFeeUI.mErrors;
		           tError = tBusinessDelegate.getCErrors();
		          if (!tError.needDealError()){                          
		    	           Content = "理赔核保加费处理成功! ";
		    	           FlagStr = "Succ";
		          } else{
		    	          FlagStr = "Fail";
		         }
		     }
	   } 
 }catch(Exception e){
	   e.printStackTrace();
	   Content = Content.trim()+".提示：异常终止!";
}

loggerDebug("LLUWAddFeeSave","------------------理赔核保-----加费处理Save-----结束------------------");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
