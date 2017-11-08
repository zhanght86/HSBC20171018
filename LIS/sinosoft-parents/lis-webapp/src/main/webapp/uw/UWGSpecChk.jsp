<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWGSpecChk.jsp
//程序功能：团体人工核保加费录入
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
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null)
   {
	out.println("session has expired");
	return;
   }
 
  	//接收信息
  	// 投保单列表
	LCPolSet tLCPolSet = new LCPolSet();
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCPremSet tLCPremSet = new LCPremSet();

	String tProposalNo = request.getParameter("ProposalNoHide");
	String tUWFlag = request.getParameter("Flag");
	String tUWIdea = request.getParameter("UWIdea");
	String tspec = request.getParameter("Remark");
	String treason = request.getParameter("Reason");
	String tSpecReason = request.getParameter("SpecReason");
	
	String tdutycode[] = request.getParameterValues("SpecGrid1");
	String tpayplantype[] = request.getParameterValues("SpecGrid2");
	String tstartdate[] = request.getParameterValues("SpecGrid3");
	String tenddate[] = request.getParameterValues("SpecGrid4");
	String trate[] = request.getParameterValues("SpecGrid5");
	//String tpayplancode = request.getParameterValues("SpecGrid6");
	
	
	loggerDebug("UWGSpecChk","polno:"+tProposalNo);
	loggerDebug("UWGSpecChk","remark:"+tspec);
	loggerDebug("UWGSpecChk","flag:"+tUWFlag);
	
	boolean flag = true;
	int feeCount = tdutycode.length;
	if (feeCount == 0 && tspec.equals(""))
	{
		Content = "请录入承保条件!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("UWGSpecChk","111");
	}
	else
	{
		loggerDebug("UWGSpecChk","222");
		if (!tProposalNo.equals("")&& !tUWFlag.equals(""))
		{

 			LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
	
	    	tLCGUWMasterSchema.setGrpPolNo(tProposalNo);
	    	tLCGUWMasterSchema.setPassFlag(tUWFlag);
	    	if(tUWFlag.equals("1") == true)
	    	{	    		
	    		tLCGUWMasterSchema.setSpecReason(tSpecReason);
	    	}
	    	if(tUWFlag.equals("2") == true)
	    	{
	    		tLCGUWMasterSchema.setSpecReason(treason);
	    	}
	    
	    	tLCGUWMasterSet.add(tLCGUWMasterSchema);
	    		
	    	loggerDebug("UWGSpecChk","feecount="+feeCount);
	    	if (feeCount > 0)
	    	{
	    	  for (int i = 0; i < feeCount; i++)
			  {
				if (!tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					{
			  			LCPremSchema tLCPremSchema = new LCPremSchema();
	
						tLCPremSchema.setDutyCode( tdutycode[i]);
						tLCPremSchema.setPayStartDate( tstartdate[i]);
						tLCPremSchema.setPayPlanType( tpayplantype[i]);
						tLCPremSchema.setPayEndDate( tenddate[i]);
				    	tLCPremSchema.setPrem( trate[i]);
	    			    
	    			 	loggerDebug("UWGSpecChk","i:"+i);
	    			    loggerDebug("UWGSpecChk","dutycode:"+tdutycode[i]);
	    			    
				    	tLCPremSet.add( tLCPremSchema );
				    
				   		flag = true;
					}
				}
				    
			}
		}
		else
		{
			Content = "传输数据失败!";
			flag = false;
		}
	}
	
	loggerDebug("UWGSpecChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();		
		tVData.add( tLCGUWMasterSet);
		tVData.add( tLCPremSet);		
		tVData.add( tG );
		
		// 数据传输
		UWGSpecChkUI tUWGSpecChkUI   = new UWGSpecChkUI();
		if (tUWGSpecChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWGSpecChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 条件核保失败，原因是: " + tUWGSpecChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWGSpecChkUI.mErrors;
		    //tErrors = tUWGSpecChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 加费录入成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
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
