<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLSecondUWSave.jsp
//程序功能：合同单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
  //输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	if(tG == null)
	 {
		System.out.println("session has expired");
		return;
	}
	System.out.println("Start Save...");
	String tCaseNo = request.getParameter("CaseNo"); //赔案号
	String tInsuredNo = request.getParameter("InsuredNo"); //被保人客户号
	String tInsuredName = request.getParameter("InsuredName");	//被保人名称
	String tSendUW[] = request.getParameterValues("LCContGridChk");
	String tContNo[] = request.getParameterValues("LCContGrid1"); //合同号
	String tAppntNo[] = request.getParameterValues("LCContGrid2"); //投保人客户号码
	String tAppntName[] = request.getParameterValues("LCContGrid3"); //投保人名称
	String tClaimRelFlag[] = request.getParameterValues("LCContGrid5"); //赔案相关标志
	String tHealthImpartNo1[] = request.getParameterValues("LCContGrid6"); //投保书健康告知栏询问号
	String tHealthImpartNo2[] = request.getParameterValues("LCContGrid7"); //体检健康告知栏
	String tNoImpartDesc[] = request.getParameterValues("LCContGrid8"); //对应未告知情况
    String tChk[] = request.getParameterValues("InpLCContGridChk");; //参数格式=” Inp+MulLine对象名+Chk”
	LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
	if (tChk!=null&&tChk.length>0)
	{
		for(int index=0;index<tChk.length;index++)
		{
			if(tChk[index].equals("1")) 
			{         
				 LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();
				 tLLCUWBatchSchema.setCaseNo(tCaseNo); //赔案号
				 tLLCUWBatchSchema.setInsuredNo(tInsuredNo); //被保人客户号
				 tLLCUWBatchSchema.setInsuredName(tInsuredName); //被保人名称
			     tLLCUWBatchSchema.setContNo(tContNo[index]); //合同号
			     tLLCUWBatchSchema.setAppntNo(tAppntNo[index]); //投保人号码			     
			     tLLCUWBatchSchema.setAppntName(tAppntName[index]); //投保人名称
			     tLLCUWBatchSchema.setClaimRelFlag(tClaimRelFlag[index]); //赔案相关标志			     
			     tLLCUWBatchSchema.setHealthImpartNo1(tHealthImpartNo1[index]); //投保书健康告知栏询问号
			     tLLCUWBatchSchema.setHealthImpartNo2(tHealthImpartNo2[index]); //体检健康告知栏
			     tLLCUWBatchSchema.setNoImpartDesc(tNoImpartDesc[index]); //对应未告知情况
			     tLLCUWBatchSchema.setRemark1(request.getParameter("Note")); //出险人目前健康状况介绍 
			     tLLCUWBatchSet.add( tLLCUWBatchSchema );
			}
		}          	
	}
  	
  	//String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("CaseNo",request.getParameter("CaseNo")); //赔案号
    mTransferData.setNameAndValue("BatNo",""); //调查批次号 ---后台生成   
    mTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //出险人号码
    mTransferData.setNameAndValue("InsuredName",request.getParameter("InsuredName")); //出险人姓名   
    mTransferData.setNameAndValue("ClaimRelFlag",""); //相关标志----后台处理
    mTransferData.setNameAndValue("MngCom",request.getParameter("ManageCom")); //机构
    
	try
	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLLCUWBatchSet);
		tVData.add(mTransferData);		
		tVData.add(tG);
		String wFlag="Create||ScondUWNode"; //创建节点
//    	ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();		
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {           
//            Content = "提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//
//        }
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "提交工作流失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "提交工作流失败";
		FlagStr = "Fail";				
	}
}

		else 
		{
		    Content = " 保存成功! ";
		    FlagStr = "SUCC";
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

