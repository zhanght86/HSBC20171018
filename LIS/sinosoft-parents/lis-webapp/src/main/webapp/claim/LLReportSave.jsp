<%
//**************************************************************************************************
//Name：LLReportSave.jsp
//Function：报案信息提交
//Author：zl
//Date: 2005-6-9 15:31
//Desc: 本页面主要完成三个功能：接收信息，判断操作符，分别提交（保存则新建工作流，修改则直接处理）
//修改：niuzj,2006-01-12,新增报案人邮编
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
loggerDebug("LLReportSave","#########################LLReportSave.jsp start#################################");
//输入参数
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
LLAccidentSubSchema tLLAccidentSubSchema = new LLAccidentSubSchema(); //分事件表
LLReportSchema tLLReportSchema = new LLReportSchema(); //报案表
LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema(); //分案表
LLReportRelaSchema tLLReportRelaSchema = new LLReportRelaSchema(); //报案分案关联表
LLCaseRelaSchema tLLCaseRelaSchema = new LLCaseRelaSchema(); //分案事件关联表
LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //理赔类型表
LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //理赔类型集合

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//LLReportUI mLLReportUI = new LLReportUI(); //报案修改提交类
//LLReportUI tLLReportUI = new LLReportUI(); //报案修改提交类
//String busiName="ClaimWorkFlowUI";
//BusinessDelegate tBusinessDelegate0=BusinessDelegate.getBusinessDelegate();
String busiName="LLReportUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();


//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLReportSave","页面失效,请重新登陆");
}
else
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    
    //**********************************************************************************************
    //获取报案页面信息，表中字段用schema打包，String等零散数据使用TransferData打包
    //**********************************************************************************************

    //获取操作符（insert||first、insert||customer、update）
    String transact = request.getParameter("fmtransact");
    //理赔类型
    String tClaimType[] = request.getParameterValues("claimType");
    //出险原因
    String toccurReason = request.getParameter("occurReason");
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");

    //团险个险
    String tRgtClass = request.getParameter("RgtClass");
    if (tRgtClass == null || tRgtClass == "")
    {
        tRgtClass = "1";
    }
    
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //事件号(新事件时为空)
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //事故类型===出险原因
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //出险地点
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述
    
    tLLReportSchema.setRptNo(request.getParameter("RptNo")); //报案号=赔案号
	tLLReportSchema.setRptorName(request.getParameter("RptorName")); //报案人姓名
    tLLReportSchema.setRptorPhone(request.getParameter("RptorPhone")); //报案人电话
    tLLReportSchema.setRptorAddress(request.getParameter("RptorAddress")); //报案人通讯地址
    
    tLLReportSchema.setPostCode(request.getParameter("PostCode")); //报案人邮编
    tLLReportSchema.setRelation(request.getParameter("Relation")); //报案人与事故人关系
    tLLReportSchema.setAccidentDate(tLLAccidentSchema.getAccDate()); //意外事故发生日期
    tLLReportSchema.setAccidentReason(tLLAccidentSchema.getAccType()); //出险原因
    tLLReportSchema.setAccidentSite(tLLAccidentSchema.getAccPlace()); //出险地点
    
    tLLReportSchema.setRptMode(request.getParameter("RptMode")); //报案方式  
    tLLReportSchema.setRgtFlag("10"); //立案标志为10表示未立案
    tLLReportSchema.setRemark(request.getParameter("Remark")); //备注
    tLLReportSchema.setRgtClass(request.getParameter("RgtClass")); //个单团单
    tLLReportSchema.setRgtState("11"); //案件类型:普通案件
    tLLReportSchema.setPeoples2(1); //投保人数

    tLLSubReportSchema.setSubRptNo(tLLReportSchema.getRptNo()); //分案号=报案号=赔案号
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //出险人姓名
    tLLSubReportSchema.setSex(request.getParameter("customerSex")); //出险人性别
	tLLSubReportSchema.setAccDate(request.getParameter("OtherAccidentDate")); //其他出险日期
	tLLSubReportSchema.setMedAccDate(request.getParameter("MedicalAccidentDate")); //医疗出险日期
	
    tLLSubReportSchema.setHospitalCode(request.getParameter("hospital")); //治疗医院
    tLLSubReportSchema.setHospitalName(request.getParameter("TreatAreaName")); //治疗医院
    tLLSubReportSchema.setAccidentDetail(request.getParameter("accidentDetail")); //出险细节
//    tLLSubReportSchema.setDieFlag(request.getParameter("IsDead")); //死亡标志
    tLLSubReportSchema.setCureDesc(request.getParameter("cureDesc")); //治疗情况
    tLLSubReportSchema.setRemark(tLLReportSchema.getRemark()); //备注
    //tLLSubReportSchema.setVIPFlag(request.getParameter("IsVip")); //vip标志

    //************************************************************************************
    //6月1日报案界面更改：
    //------ICD10主代码（单选项）、ICD10子代码（单选项）、出险原因细节没有建立相应的字段。
    //------治疗情况（门诊、住院、综合）无相应字段。（已在前台更改）
    //------现有报案界面中的“治疗情况”自由录入字段，应该更名为“出险经过”或者“事故描述”。
    //初步增加内容如下：
    //************************************************************************************
    tLLSubReportSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述
    tLLSubReportSchema.setAccResult1(request.getParameter("AccResult1")); //ICD10主代码
    tLLSubReportSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10子代码

    for (int i = 0; i < tClaimType.length; i++)
    {
        tClaimType[i] = toccurReason + tClaimType[i];
        tLLReportReasonSchema = new LLReportReasonSchema();
        tLLReportReasonSchema.setRpNo(tLLReportSchema.getRptNo()); //报案号=赔案号
        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //理赔类型
        tLLReportReasonSet.add(tLLReportReasonSchema);
    }

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", tLLReportSchema.getRptNo());
    mTransferData.setNameAndValue("RptorName", tLLReportSchema.getRptorName());
    mTransferData.setNameAndValue("CustomerNo", tLLSubReportSchema.getCustomerNo());
    mTransferData.setNameAndValue("CustomerName", tLLSubReportSchema.getCustomerName());
    mTransferData.setNameAndValue("CustomerSex", tLLSubReportSchema.getSex());
    mTransferData.setNameAndValue("AccDate", tLLReportSchema.getAccidentDate());
    mTransferData.setNameAndValue("MngCom", ManageCom);

    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLReportSchema);
    tVData.add(tLLSubReportSchema);
    tVData.add(tLLReportReasonSet);

    //第一次保存提交时，提交到工作流引擎，其他的直接提交到业务类,当提交工作流时wFlag=9999999999
    if (transact.equals("insert||first"))
    {
        wFlag = "insertnoflow";
        try
        {
            loggerDebug("LLReportSave","---LLReportUI submitData and transact="+transact);
//            if(!mLLReportUI.submitData(tVData,wFlag))
//            {
//                Content = " LLReportUI处理数据失败，原因是: ";
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName))
			   {    
			        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			        { 
							   Content = " LLReportUI处理数据失败，原因是:" + tBusinessDelegate1.getCErrors().getFirstError();
							   FlagStr = "Fail";
					}
					else
					{
							   Content = "LLReportUI处理数据失败";
							   FlagStr = "Fail";				
					}
			   }

            else
            {
            	VData tempVData1 = new VData();
    			//tempVData1 = mLLReportUI.getResult();
    			tempVData1 = tBusinessDelegate1.getResult();
    			// 向前台返回处理完的参数以便查询工作流
    			mTransferData = null;
    			mTransferData = (TransferData) tempVData1.getObjectByObjectName("TransferData", 0);
    		    String tRptNo = (String) mTransferData.getValueByName("RptNo");
    		    loggerDebug("LLReportSave","---tRptNo="+tRptNo);
               %>
            	<script language="javascript">
            	    parent.fraInterface.fm.all("ClmNo").value="<%=tRptNo%>";
            	</script>
            	<%
            	
                tVData.clear();
                Content = " 数据提交成功！";
                FlagStr = "Succ";
            }
        }
        catch(Exception ex)
        {
            Content = "数据提交LLReportUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
    }
    else
    {
        if(transact.equals("insert||customer"))
        {
            transact = "INSERT";
        }
        else
        {
            if(transact.equals("update"))
            {
                transact = "UPDATE";
            }
            else
            {
                Content = "操作符错误！" ;
                FlagStr = "Fail";
                return;
            }
        }
        try
        {
            //数据提交
            loggerDebug("LLReportSave","---LLReportUI submitData and transact="+transact);
//            if (!tLLReportUI.submitData(tVData,transact))
//            {
//                Content = " LLReportUI处理数据失败，原因是: " + tLLReportUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate2.submitData(tVData,transact,busiName))
			   {    
			        if(tBusinessDelegate2.getCErrors()!=null&&tBusinessDelegate2.getCErrors().getErrorCount()>0)
			        { 
							   Content = " LLReportUI处理数据失败，原因是:" + tBusinessDelegate2.getCErrors().getFirstError();
							   FlagStr = "Fail";
					}
					else
					{
							   Content = "LLReportUI处理数据失败";
							   FlagStr = "Fail";				
					}
			   }
            else
            {
                tVData.clear();
                Content = " 数据提交成功！";
                FlagStr = "Succ";
            }
        }
        catch(Exception ex)
        {
            Content = "数据提交LLReportUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
