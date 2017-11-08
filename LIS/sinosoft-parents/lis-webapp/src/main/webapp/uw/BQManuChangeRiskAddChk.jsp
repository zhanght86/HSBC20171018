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
  loggerDebug("BQManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//接收信息
  	// 保单列表
	LPPremSet tLPPremSet = new LPPremSet();
	LPPolSet tLPPolSet = new LPPolSet();
    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	Hashtable PolHashtable = new Hashtable();
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	String tUpReporFlag = request.getParameter("UpReporFlag");
	
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	//
	String tRadio[] = request.getParameterValues("InpPolAddGridSel");  
	
	String[] tPolNo = request.getParameterValues("PolAddGrid12");
	 loggerDebug("BQManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@111111111");
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
	//缴费计划编码
	String tpayPlanCode[] = request.getParameterValues("PolAddGrid14");
	//加费开始时间类型 0-追溯  1-当期  2-下期
	String taddForm[] = request.getParameterValues("PolAddGrid15");
	//
	//String tSecondScore[] = request.getParameterValues("SpecGrid6");
	//String AddObj[] = request.getParameterValues("SpecGrid7");
	//加费金额
	String trate[] = request.getParameterValues("PolAddGrid7");
  
	int feeCount = tRadio.length;
	if (feeCount == 0 )
	{
		Content = "请录入加费信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		//循环处理每个险种的加费数据
		for(int i=0;i<feeCount;i++)
		{ 
			loggerDebug("BQManuChangeRiskAddChk","是否选中"+i+":  "+tRadio[i]);
			if(tRadio[i].equals("1")) 
			{
				String tCurrPolNo = tPolNo[i];
				
				//String tCurrDuty = tdutycode[i];
				if (!tdutycode[i].equals("")&&!tpayplantype[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals("")&&!taddForm[i].equals(""))
				{
					LPPolSchema tLPPolSchema = new LPPolSchema();
					tLPPolSchema.setPolNo(tCurrPolNo);
					tLPPolSchema.setContNo(tContNo);
					tLPPolSchema.setEdorNo(tEdorNo);
					tLPPolSchema.setEdorType(tEdorType);
					if(i==0)
					{
						PolHashtable.put(tCurrPolNo,tCurrPolNo);
						tLPPolSet.add(tLPPolSchema);
					}
					else if(!PolHashtable.containsKey(tCurrPolNo))
					{
						tLPPolSet.add(tLPPolSchema);
					}			
					
					LPPremSchema tLPPremSchema = new LPPremSchema();
			  	  	tLPPremSchema.setPolNo(tPolNo[i]);
			  	  	tLPPremSchema.setDutyCode( tdutycode[i]);
			  	  	tLPPremSchema.setPayStartDate( tstartdate[i]);
					tLPPremSchema.setPayPlanType( tpayplantype[i]);
					tLPPremSchema.setPayEndDate( tenddate[i]);
					tLPPremSchema.setAddFeeDirect(tAddFeeDirect[i]); //加费方式
					tLPPremSchema.setAddForm(taddForm[i]); //加费开始时间类型
				    tLPPremSchema.setPrem(trate[i]);
				    tLPPremSchema.setSuppRiskScore( tsuppriskscore[i]);
				    tLPPremSchema.setPayPlanCode( tpayPlanCode[i]);
				    tLPPremSchema.setEdorNo(tEdorNo);
				    tLPPremSchema.setEdorType(tEdorType);
				    
				    //tLPPremSchema.setAddFeeDirect(taddForm[i]); //记录加费形式
	    		  	tLPPremSet.add(tLPPremSchema);
	    		  	flag = true;
	    		  	    
	    		  	loggerDebug("BQManuChangeRiskAddChk","i:"+i);
	    		  	loggerDebug("BQManuChangeRiskAddChk","责任编码"+i+":  "+tdutycode[i]);
	    	  } // End of if
	    	  else
	    	  {
	    		  	Content = "加费信息未填写完整,请确认!";
	    		  	flag = false;	
	    		  	FlagStr = "Fail";   
	    		  	break; 			 
	    	  }
					//if(taddForm[i].equals("0")){
					//}
			  break;
			}
			
		}
		
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("EdorType",tEdorType);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tInputData.add(tG);
		tInputData.add(tAddReason);
		tInputData.add(tUpReporFlag); //程序判断是否需要再保标志
		tInputData.add(tLPPolSet);
		tInputData.add(tLPPremSet);
		tInputData.add(tTransferData);
	}
	
	loggerDebug("BQManuChangeRiskAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{		
  		 String busiName="BQManuAddCHKUI";//TODO hq
  		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		// 数据传输
		if (!tBusinessDelegate.submitData(tInputData,"",busiName))
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " 人工核保加费失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " 人工核保加费成功! ";
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
