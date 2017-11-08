<%
//**************************************************************************************************
//Name：LLClaimImportModifySave.jsp
//Function：重要信息修改提交
//Author：quyang
//Date: 2005-6-23 
//Desc: 本页面主要完成三个功能：接收信息，判断操作符，分别提交（保存则新建工作流，修改则直接处理）
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLClaimImportModifySave","#########################LLClaimImportModifySave.jsp start#################################");
	//输入参数
	
	LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //立案事件表
	LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //立案分案
	LLAppClaimReasonSchema tLLAppClaimReasonSchema = null; //立案理赔类型
	LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //立案分案
	LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //理赔类型集合
	LLAffixSchema tLLAffixSchema = new LLAffixSchema(); //立案附件表
	
	
	
	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
    
    String newAccNo=""; //返回的新事件號

	//页面有效性判断
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "页面失效,请重新登陆";
	    loggerDebug("LLClaimImportModifySave","页面失效,请重新登陆");
	}
	else
	{
	    //**********************************************************************************************
	    //获取报案页面信息，表中字段用schema打包，String等零散数据使用TransferData打包
	    //**********************************************************************************************
	
	    //获取操作符（insert||first、insert||customer、update）
	    String transact = request.getParameter("fmtransact");
	    //理赔类型
	    String tClaimType[] = request.getParameterValues("claimType");
		//理赔类型备份
		String newtClaimType[] = request.getParameterValues("newclaimType");
	    //出险原因
	    String toccurReason = request.getParameter("newoccurReason");
	    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
	    String wFlag = request.getParameter("WorkFlowFlag");

	
	    //数据区--事故信息
	    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //事件号
	    tLLAccidentSchema.setAccType(request.getParameter("newoccurReason")); //事故类型===出险原因
	    tLLAccidentSchema.setAccDate(request.getParameter("newAccidentDate")); //事故日期
	    
	        
	    //数据区--准备往当前业务表中写入的赔案号                    
	    tLLCaseSchema.setRgtNo(request.getParameter("ClmNo")); //赔案号
	    tLLCaseSchema.setCaseNo(tLLCaseSchema.getRgtNo()); //赔案号
	    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //出险人客户号
	    tLLCaseSchema.setCustomerName(request.getParameter("newcustomerName")); //出险人姓名
	    tLLCaseSchema.setCustomerAge(request.getParameter("newcustomerAge")); //出险人年龄
	    tLLCaseSchema.setCustomerSex(request.getParameter("newcustomerSex")); //出险人性别
	
	    tLLCaseSchema.setHospitalCode(request.getParameter("newhospital")); //治疗医院
	    tLLCaseSchema.setHospitalName(request.getParameter("newTreatAreaName")); //治疗医院名称
	    
	    tLLCaseSchema.setMedAccDate(request.getParameter("newMedicalAccidentDate")); //医疗出险日期
	    tLLCaseSchema.setAccDate(request.getParameter("newOtherAccidentDate")); //其他出险日期
	    tLLCaseSchema.setAccidentDetail(request.getParameter("newaccidentDetail")); //出险细节
	    
	    tLLCaseSchema.setCureDesc(request.getParameter("newcureDesc")); //治疗情况            
	    tLLCaseSchema.setAccResult1(request.getParameter("newAccResult1")); //出险结果1
	    tLLCaseSchema.setAccResult2(request.getParameter("newAccResult2")); //出险结果2
	    
	    tLLCaseSchema.setCustState(request.getParameter("newAccDateModSign")); //事故者现状，存数据的含义：0-出险日期正常修改 1-出险日期非正常修改
	    
			
	    if(null!=newtClaimType){
	        for (int i = 0; i < newtClaimType.length; i++)
	        {
	            loggerDebug("LLClaimImportModifySave","newtClaimType"+newtClaimType[i]);
	            newtClaimType[i] = toccurReason + newtClaimType[i];
	            
	            tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
	            
			    tLLAppClaimReasonSchema.setCaseNo(tLLCaseSchema.getCaseNo()); //赔案号
	            tLLAppClaimReasonSchema.setRgtNo(tLLCaseSchema.getCaseNo()); //申请登记号
	            tLLAppClaimReasonSchema.setCustomerNo(tLLCaseSchema.getCustomerNo()); //出险人编码/出险人客户号
	            tLLAppClaimReasonSchema.setReasonCode(newtClaimType[i]); //理赔类型
	            tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
	            
	            tLLAppClaimReasonSchema=null;
	        }
	    }
	
	    tLLRegisterSchema.setAcceptedDate(request.getParameter("newAcceptedDate")); //交接日期
	    tLLRegisterSchema.setApplyDate(request.getParameter("newApplyDate")); //客户申请日期 
	    tLLRegisterSchema.setAccidentDate(tLLAccidentSchema.getAccDate()); //事故日期
	    
	    tLLRegisterSchema.setRgtantMobile(request.getParameter("newRptorMoPhone")); //申请人手机号码 
	    tLLRegisterSchema.setRgtantAddress(request.getParameter("newRptorAddress")); //报案人通讯地址
	    tLLRegisterSchema.setPostCode(request.getParameter("newAppntZipCode")); //申请人邮编
	    
	    tLLAffixSchema.setRgtNo(request.getParameter("ClmNo"));
	    tLLAffixSchema.setReAffixDate(request.getParameter("newAddFileDate"));
	    
	    TransferData mTransferData = new TransferData();
	    mTransferData.setNameAndValue("EditReason", request.getParameter("EditReason")); //修改备注
	    mTransferData.setNameAndValue("tPayGetMode", request.getParameter("newPayGetMode")); //赔付金领取方式
	    mTransferData.setNameAndValue("CancleMergeFlag",request.getParameter("cancelMergeFlag")); //是否清除案件合并信息标志
	
	    //准备传输数据 VData
	    VData tVData = new VData();
	    tVData.add(tGI);
	    tVData.add(tLLAccidentSchema);    
	    tVData.add(tLLCaseSchema);
	    tVData.add(tLLRegisterSchema);
	    tVData.add(tLLAppClaimReasonSet);
	    tVData.add(tLLAffixSchema);
	    tVData.add(mTransferData);
	     
	    
	    // 数据传输
	    //LLClaimImportModifyUI tLLClaimImportModifyUI = new LLClaimImportModifyUI();
	 	String busiName="LLClaimImportModifyUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 	
	    try
	    {
//			if (!tLLClaimImportModifyUI.submitData(tVData,""))
//			{
//		        Content = " 保存失败，原因是: " + tLLClaimImportModifyUI.mErrors.getError(0).errorMessage;
//		        FlagStr = "Fail";
//			}
			if(!tBusinessDelegate.submitData(tVData,"",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "保存失败";
					FlagStr = "Fail";				
				}
			}

		    else
		    {
		        tVData.clear();
		    	Content = " 保存成功! ";
		    	FlagStr = "Succ";
		    	
		    	VData tResult = new VData();
	            //tResult = tLLClaimImportModifyUI.getResult();
	            tResult = tBusinessDelegate.getResult();	 
		        newAccNo = (String)tResult.getObjectByObjectName("String",0);
		        //如果没有清除案件合并信息,则表名事件号没变,回传原来的事件号
		        if(newAccNo==null||newAccNo.equals(""))
		        {
		        	newAccNo=tLLAccidentSchema.getAccNo();
		        }
		        
		        loggerDebug("LLClaimImportModifySave","案件"+tLLCaseSchema.getCaseNo()+"原有事件号"+tLLAccidentSchema.getAccNo()+",新事件号:"+newAccNo);
		    }
	    }
	    catch(Exception ex)
	    {
	        //Content = "数据提交失败，原因是:" + tLLClaimImportModifyUI.mErrors.getLastError();
	       	Content = "数据提交失败，原因是:" + tBusinessDelegate.getCErrors().getLastError();
	        FlagStr = "Fail";
	    }
	}
	%>
	
	<html>
	<script language="javascript">
	    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=newAccNo%>");
	</script>
</html>
