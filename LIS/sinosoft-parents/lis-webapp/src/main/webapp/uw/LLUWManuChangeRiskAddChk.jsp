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
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.util.Hashtable"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  boolean flag = true;
	GlobalInput tG = new GlobalInput();  
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  loggerDebug("LLUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//接收信息
  	// 保单列表
	LLUWPremMasterSet tLLUWPremMasterSet = new LLUWPremMasterSet();
	LCPolSet tLCPolSet = new LCPolSet();
    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	Hashtable PolHashtable = new Hashtable();
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	String tUpReporFlag = request.getParameter("UpReporFlag");
	String tClmNo = request.getParameter("ClmNo");
	String tBatNo = request.getParameter("BatNo");
	//
	String[] tPolNo = request.getParameterValues("PolAddGrid12");
	 loggerDebug("LLUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@111111111");
	//责任编码
	String tdutycode[] = request.getParameterValues("PolAddGrid3");
	//加费类型
	String tpayplantype[] = request.getParameterValues("PolAddGrid4");
	//缴费起期
	String tstartdate[] = request.getParameterValues("PolAddGrid9");
	//缴费止期
	String tenddate[] = request.getParameterValues("PolAddGrid10");
	//加费方式
	String tAddFeeDirect[] = request.getParameterValues("PolAddGrid5");
	//加费评点
	String tsuppriskscore[] = request.getParameterValues("PolAddGrid6");
	//
	//String tSecondScore[] = request.getParameterValues("SpecGrid6");
	//String AddObj[] = request.getParameterValues("SpecGrid7");
	//加费金额
	String trate[] = request.getParameterValues("PolAddGrid7");
	
	String taddForm[] = request.getParameterValues("PolAddGrid15");
	
	int feeCount = tPolNo.length;
	if (feeCount == 0 )
	{
		Content = "请录入加费信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		//循环处理每个险种的加费数据
		for(int i=0;i<tPolNo.length;i++)
		{
			String tCurrPolNo = tPolNo[i];
			
			//String tCurrDuty = tdutycode[i];
			if (!tdutycode[i].equals("")&&!tpayplantype[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
			{
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setPolNo(tCurrPolNo);
				tLCPolSchema.setContNo(tContNo);
				if(i==0)
				{
					PolHashtable.put(tCurrPolNo,tCurrPolNo);
					tLCPolSet.add(tLCPolSchema);
				}
				else if(!PolHashtable.containsKey(tCurrPolNo))
				{
					PolHashtable.put(tCurrPolNo,tCurrPolNo);
					tLCPolSet.add(tLCPolSchema);
				}
					
				
				
				LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();
				tLLUWPremMasterSchema.setPolNo(tPolNo[i]);
				tLLUWPremMasterSchema.setDutyCode( tdutycode[i]);
				tLLUWPremMasterSchema.setPayStartDate( tstartdate[i]);
				tLLUWPremMasterSchema.setClmNo(tClmNo);
				tLLUWPremMasterSchema.setBatNo(tBatNo);
				tLLUWPremMasterSchema.setPayPlanType( tpayplantype[i]);
				tLLUWPremMasterSchema.setPayEndDate( tenddate[i]);
				tLLUWPremMasterSchema.setAddFeeDirect(tAddFeeDirect[i]); //加费方式
				//tLCPremSchema.setSecInsuAddPoint(tSecondScore[i]);
				tLLUWPremMasterSchema.setPrem(trate[i]);
				tLLUWPremMasterSchema.setSuppRiskScore( tsuppriskscore[i]);
				tLLUWPremMasterSchema.setAddForm(taddForm[i]);
			    tLLUWPremMasterSet.add( tLLUWPremMasterSchema);
    		  	flag = true;
    		  	    
    		  	loggerDebug("LLUWManuChangeRiskAddChk","i:"+i);
    		  	loggerDebug("LLUWManuChangeRiskAddChk","责任编码"+i+":  "+tdutycode[i]);
    		  } // End of if
    		  else
    		  {
    		  	Content = "加费信息未填写完整,请确认!";
    		  	flag = false;	
    		  	FlagStr = "Fail";   
    		  	break; 			 
    		  }
			}
		
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		tInputData.add(tG);
		tInputData.add(tAddReason);
		tInputData.add(tUpReporFlag); //程序判断是否需要再保标志
		tInputData.add(tLCPolSet);
		tInputData.add(tLLUWPremMasterSet);
		tInputData.add(tTransferData);
	}
	
	loggerDebug("LLUWManuChangeRiskAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{		
		// 数据传输
		/*LLUWManuAddChkUI tLLUWManuAddChkUI = new LLUWManuAddChkUI();
		if (!tLLUWManuAddChkUI.submitData(tInputData,""))
		{
			int n = tLLUWManuAddChkUI.mErrors.getErrorCount();
			Content = " 人工核保加费失败，原因是: " + tLLUWManuAddChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tLLUWManuAddChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 人工核保加费成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}*/
		String busiName="LLUWManuAddChkUI";
		  String mDescType="人工核保加费";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tInputData,"",busiName))
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
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+".提示：异常终止!";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
