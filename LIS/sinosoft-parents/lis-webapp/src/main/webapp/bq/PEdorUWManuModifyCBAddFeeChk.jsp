<%@page contentType="text/html;charset=gb2312" %>
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
 <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
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
	LPPremSet tLPPremSet = new LPPremSet();
    TransferData tTransferData = new TransferData();
	String tPolNo = request.getParameter("PolNo"); //保全项目所针对的保单
	String tPolNo2 = request.getParameter("PolNo2"); //保全加费所针对的保单
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tAddReason = request.getParameter("AddReason");
	
	
	String tdutycode[] = request.getParameterValues("Spec2Grid1");
	String tpayplantype[] = request.getParameterValues("Spec2Grid2");
	String tstartdate[] = request.getParameterValues("Spec2Grid3");
	String tenddate[] = request.getParameterValues("Spec2Grid4");
	String trate[] = request.getParameterValues("Spec2Grid5");
	String tpayplancode[] = request.getParameterValues("Spec2Grid6");
	
	System.out.println("polno:"+tPolNo);
	System.out.println("AddReason:"+tAddReason);
	System.out.println("EdorNo:"+tEdorNo);
	
	
	int feeCount = tdutycode.length;
	if (feeCount == 0 )
	{
		Content = "请录入加费信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		if (!tPolNo.equals("")&& !tEdorNo.equals("")&& !tEdorType.equals("")&& !tMissionID.equals("")&& !tSubMissionID.equals(""))
		{
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
			  			tLPPremSchema.setEdorType(tEdorType); //保全针对项目加费
			  			tLPPremSchema.setDutyCode( tdutycode[i]);
			  			tLPPremSchema.setPayStartDate( tstartdate[i]);
						tLPPremSchema.setPayPlanType( tpayplantype[i]);
						tLPPremSchema.setPayEndDate( tenddate[i]);
				    	tLPPremSchema.setPrem( trate[i]);
				    	tLPPremSchema.setPayPlanCode(tpayplancode[i]);
	    			    tLPPremSet.add( tLPPremSchema );
	    			    flag = true;
	    			    
	    			 	System.out.println("i:"+i);
	    			    System.out.println("责任编码"+i+":  "+tdutycode[i]);
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
			tTransferData.setNameAndValue("PolNo",tPolNo);
			tTransferData.setNameAndValue("PolNo2",tPolNo2);
			tTransferData.setNameAndValue("EdorNo",tEdorNo) ;
			tTransferData.setNameAndValue("EdorType",tEdorType) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("AddReason",tAddReason);
			tTransferData.setNameAndValue("LPPremSet",tLPPremSet);	
			
		} // End of if
		else
		{
			Content = "传输数据失败!";
			flag = false;
		}
	}
	
	System.out.println("flag:"+flag);
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tG );
		tVData.add( tTransferData);				
		// 数据传输
		PEdorUWModifyCBAddFeeBL tPEdorUWModifyCBAddFeeBL   = new PEdorUWModifyCBAddFeeBL();
		if (!tPEdorUWModifyCBAddFeeBL.submitData(tVData,"UPDATE")) //执行保全核保特约工作流节点0000000003
		{
			int n = tPEdorUWModifyCBAddFeeBL.mErrors.getErrorCount();
			Content = " 条件核保失败，原因是: " + tPEdorUWModifyCBAddFeeBL.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tPEdorUWModifyCBAddFeeBL.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 条件核保成功! ";
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
