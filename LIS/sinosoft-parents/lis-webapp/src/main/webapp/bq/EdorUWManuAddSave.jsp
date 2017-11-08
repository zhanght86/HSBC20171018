<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuSpecSave.jsp
//程序功能：人工核保加费
//创建日期：2005-07-16 11:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
    //输出参数
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
    boolean flag = true;
	GlobalInput tG = new GlobalInput();  
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) 
  	{
		out.println("session has expired");
		return;
	}
  
	//接收信息
  	// 保单列表
	LPPremSet tLPPremSet = new LPPremSet();
    VData tInputData = new VData();
	String tPolNo = request.getParameter("PolNo"); //保全项目所针对的保单
	String tPolNo2 = request.getParameter("PolNo2"); //保全加费所针对的保单
	String tContNo = request.getParameter("ContNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String tAddReason = request.getParameter("AddReason");
	String sNoAddPrem = request.getParameter("NoAddPrem");
	int feeCount = 0;
	if (sNoAddPrem != null && sNoAddPrem.equals("Y"))  //没有加费
	{
		feeCount = 0;
	}
	else
	{
		String tdutycode[] = request.getParameterValues("SpecGrid1");
		String tpayplantype[] = request.getParameterValues("SpecGrid2");
		String tstartdate[] = request.getParameterValues("SpecGrid9");
		String tenddate[] = request.getParameterValues("SpecGrid11");
		String tpaytodate[] = request.getParameterValues("SpecGrid10");
		
		String tsuppriskscore[] = request.getParameterValues("SpecGrid4");
		String tSecondScore[] = request.getParameterValues("SpecGrid5");
		String AddObj[] = request.getParameterValues("SpecGrid6");
		String trate[] = request.getParameterValues("SpecGrid7");
		feeCount = tdutycode.length;
	    	//准备特约加费信息
	    	if (feeCount > 0)
	    	{
	        	for (int i = 0; i < feeCount; i++)
				{
				    if (!tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					{
	    		  	  	LPPremSchema tLPPremSchema = new LPPremSchema();
			  	  	    tLPPremSchema.setPolNo(tPolNo2);
			  	  	    tLPPremSchema.setEdorNo(tEdorNo);
    			  	  	tLPPremSchema.setEdorType(tEdorType);
	    		  	  	tLPPremSchema.setDutyCode( tdutycode[i]);
		    	  	  	tLPPremSchema.setPayStartDate( tstartdate[i]);
		    	  	  	tLPPremSchema.setPaytoDate(tpaytodate[i]);
					  	tLPPremSchema.setPayPlanType( tpayplantype[i]);
					  	tLPPremSchema.setPayEndDate( tenddate[i]);
					  	
					  	tLPPremSchema.setAddFeeDirect(AddObj[i]);
					  	tLPPremSchema.setSecInsuAddPoint(tSecondScore[i]);
			            tLPPremSchema.setPrem( trate[i]);
				        tLPPremSchema.setSuppRiskScore( tsuppriskscore[i]);
    	    		  	tLPPremSet.add( tLPPremSchema );
	        		  	flag = true;
	    		  	    
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
	}

		if (!tPolNo.equals(""))
		{
		    //准备公共传输信息
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setContNo(tContNo);
			tLPPolSchema.setEdorType(tEdorType);
			tLPPolSchema.setEdorNo(tEdorNo);
			tLPPolSchema.setPolNo(tPolNo);

			tInputData.add(tG);
			tInputData.add(tLPPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLPPremSet);
			
		} // End of if
		else
		{
			Content = "传输数据失败!";
			flag = false;
		}
//	}
	loggerDebug("EdorUWManuAddSave","-------------------------------------------" + flag);
try
{
  	if (flag == true)
  	{
		// 数据传输
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//EdorUWManuAddUI tEdorUWManuAddUI = new EdorUWManuAddUI();
		//if (!tEdorUWManuAddUI.submitData(tInputData,""))
	    if(!tBusinessDelegate.submitData(tInputData, "", "EdorUWManuAddUI"))
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
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
