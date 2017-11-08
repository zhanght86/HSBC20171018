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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
  
	//接收信息
  	// 保单列表
	LCPremSet tLCPremSet = new LCPremSet();
//    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	String tPolNo = request.getParameter("PolNo"); //保全项目所针对的保单
	String tPolNo2 = request.getParameter("PolNo2"); //保全加费所针对的保单
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	
	String tdutycode[] = request.getParameterValues("SpecGrid1");
	String tpayplantype[] = request.getParameterValues("SpecGrid2");
	String tstartdate[] = request.getParameterValues("SpecGrid3");
	String tenddate[] = request.getParameterValues("SpecGrid4");
	String trate[] = request.getParameterValues("SpecGrid5");
	String tsuppriskscore[] = request.getParameterValues("SpecGrid6");
	
	loggerDebug("UWGrpManuAddChk","polno:"+tPolNo);
	loggerDebug("UWGrpManuAddChk","AddReason:"+tAddReason);
	loggerDebug("UWGrpManuAddChk","ContNo:"+tContNo);
	
	
	int feeCount = tdutycode.length;
	if (feeCount == 0 )
	{
		Content = "请录入加费信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		if (!tPolNo.equals(""))
		{
	    	//准备特约加费信息
	    	if (feeCount > 0)
	    	 {
	    	  for (int i = 0; i < feeCount; i++)
				{
					if (!tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					{
			  			LCPremSchema tLCPremSchema = new LCPremSchema();
			  			tLCPremSchema.setPolNo(tPolNo2);
			  			tLCPremSchema.setDutyCode( tdutycode[i]);
			  			tLCPremSchema.setPayStartDate( tstartdate[i]);
						tLCPremSchema.setPayPlanType(tpayplantype[i]);
						tLCPremSchema.setPayEndDate( tenddate[i]);
				    	tLCPremSchema.setPrem( trate[i]);
				    	//tLCPremSchema.setSuppRiskScore( tsuppriskscore[i]);
	    			    tLCPremSet.add( tLCPremSchema );
	    			    flag = true;
	    			    
	    			 	loggerDebug("UWGrpManuAddChk","i:"+i);
	    			    loggerDebug("UWGrpManuAddChk","责任编码"+i+":  "+tdutycode[i]);
	    			} // End of if
	    			else
	    			{
	    			 Content = "加费信息未填写完整,请确认!";
	    			 flag = false;	
	    			 FlagStr = "Fail";   
	    			 break; 			 
	    			}
				} // End of for				    
			} // End of if
			
		    //准备公共传输信息
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setContNo(tContNo);
			tLCPolSchema.setPolNo(tPolNo);

			tInputData.add(tG);
			tInputData.add(tLCPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLCPremSet);
/*			tTransferData.setNameAndValue("PolNo",tPolNo);
			tTransferData.setNameAndValue("PolNo2",tPolNo2);
			tTransferData.setNameAndValue("PrtNo",tContNo) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("AddReason",tAddReason);
			tTransferData.setNameAndValue("LCPremSet",tLCPremSet);*/	
			
		} // End of if
		else
		{
			Content = "传输数据失败!";
			flag = false;
		}
	}
	
	loggerDebug("UWGrpManuAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
//		VData tVData = new VData();
//		tVData.add( tG );
//		tVData.add( tTransferData);				
		// 数据传输
		UWGrpManuAddChkUI tUWGrpManuAddChkUI = new UWGrpManuAddChkUI();
		if (!tUWGrpManuAddChkUI.submitData(tInputData,""))
		{
			int n = tUWGrpManuAddChkUI.mErrors.getErrorCount();
			Content = " 人工核保加费失败，原因是: " + tUWGrpManuAddChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWGrpManuAddChkUI.mErrors;
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
