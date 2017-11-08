<%
//**************************************************************************************************
//Name：LLGrpReportSave.jsp
//Function：团体报案信息提交
//Author：zhangzheng
//Date: 2008-10-29 
//Desc: 本页面主要完成三个功能：接收信息，判断操作符，分别提交（保存则新建工作流，修改则直接处理）
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
loggerDebug("LLGrpReportSave","#########################LLReportSave.jsp start#################################");
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
GlobalInput tGI=(GlobalInput)session.getValue("GI");
//GrpClaimWorkFlowUI tGrpClaimWorkFlowUI = new GrpClaimWorkFlowUI(); //团险理赔工作流引擎
//LLGrpReportUI tLLGrpReportUI = new LLGrpReportUI(); //报案修改提交类
String busiName="grpGrpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

String busiName1="grpLLGrpReportUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();

//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLGrpReportSave","页面失效,请重新登陆");
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
        tRgtClass = "2";
    }
    
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //事件号(新事件时为空)
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //事故类型===出险原因
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //出险地点
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述
    
    loggerDebug("LLGrpReportSave",request.getParameter("Relation"));

	//新增报案信息
	tLLReportSchema.setRptNo(request.getParameter("RptNo")); //报案号=赔案号
	tLLReportSchema.setRgtObj("2"); //号码类型:1-个险报案号，2-团险报案号
	tLLReportSchema.setRgtObjNo(tLLReportSchema.getRptNo());
    tLLReportSchema.setRptorName(request.getParameter("RptorName")); //报案人姓名
    tLLReportSchema.setRptorPhone(request.getParameter("RptorPhone")); //报案人电话
    tLLReportSchema.setRptorAddress(request.getParameter("RptorAddress")); //报案人通讯地址
    tLLReportSchema.setRelation(request.getParameter("Relation")); //报案人与事故人关系
    tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLReportSchema.setRptMode(request.getParameter("RptMode")); //报案方式
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //出险原因
    tLLReportSchema.setAccidentSite(request.getParameter("AccidentSite")); //出险地点
    tLLReportSchema.setRgtFlag("10"); //立案标志为10表示未立案
    tLLReportSchema.setRemark(request.getParameter("Remark")); //备注
    tLLReportSchema.setRgtClass("2"); //个单团单标志 1:个单,2:团单
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体保单号
    tLLReportSchema.setRiskCode(request.getParameter("RiskCode")); //保单险种号
    tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //团体客户号
    tLLReportSchema.setGrpName(request.getParameter("GrpName")); //单位名称
    tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //投保总人数
    tLLReportSchema.setRgtState(request.getParameter("rgtstate")); //案件类型
    
	//分案表
    tLLSubReportSchema.setSubRptNo(tLLReportSchema.getRptNo()); //分案号=报案号=赔案号
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //出险人姓名
    tLLSubReportSchema.setSex(request.getParameter("customerSex")); //出险人性别
	tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate2")); //其他出险日期
	tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate2")); //医疗出险日期
	tLLSubReportSchema.setAccidentType(request.getParameter("occurReason"));
	
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
    tLLSubReportSchema.setAccResult1(request.getParameter("AccResult2")); //ICD10主代码
    tLLSubReportSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10子代码
    tLLSubReportSchema.setIDNo(request.getParameter("IDNo"));
    tLLSubReportSchema.setIDType(request.getParameter("IDType"));
    


    for (int i = 0; i < tClaimType.length; i++)
    {
        if("00".equals(tClaimType[i])){
        	tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate2"));
        }
        tClaimType[i] = toccurReason + tClaimType[i];
        tLLReportReasonSchema = new LLReportReasonSchema();
        tLLReportReasonSchema.setRpNo(tLLReportSchema.getRptNo()); //报案号=赔案号
        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //理赔类型
        tLLReportReasonSchema.setReasonType(request.getParameter("occurReason"));
        tLLReportReasonSchema.setReason(request.getParameter("ReasonName"));
        tLLReportReasonSet.add(tLLReportReasonSchema);
    }

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    
    mTransferData.setNameAndValue("RptNo", tLLReportSchema.getRptNo()); //报案号
	mTransferData.setNameAndValue("RptorName", tLLReportSchema.getRptorName()); //报案人姓名
	mTransferData.setNameAndValue("CustomerNo", tLLSubReportSchema.getCustomerNo()); //出险人客户号
	mTransferData.setNameAndValue("CustomerName", tLLSubReportSchema.getCustomerName()); //出险人姓名
	mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex")); //出险人性别
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2")); //其他出险日期
    mTransferData.setNameAndValue("MngCom", ManageCom); //管理机构
    mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo")); //团体合同号
    mTransferData.setNameAndValue("RgtState", tLLReportSchema.getRgtState()); //报案类型       
    mTransferData.setNameAndValue("GrpCustomerNo", request.getParameter("GrpCustomerNo")); //报案类型       
    mTransferData.setNameAndValue("GrpName", request.getParameter("GrpName")); //报案类型       

    
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
        wFlag = "9999999999";
        try
        {
            loggerDebug("LLGrpReportSave","---GrpClaimWorkFlowUI submitData and transact="+transact);
//            if(!tGrpClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " 提交工作流ClaimWorkFlowUI失败，原因是: " + tGrpClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "提交工作流ClaimWorkFlowUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "提交工作流ClaimWorkFlowUI失败";
					FlagStr = "Fail";				
				}
			}

            else
            {
                //从结果集中取出前台需要数据
                tVData.clear();
                //tVData = tGrpClaimWorkFlowUI.getResult();
                 tVData = tBusinessDelegate.getResult();
                loggerDebug("LLGrpReportSave","tVData="+tVData);
                //**********************************************************************************Beg
                //取得处理后的节点参数,目前方法采用根据传入参数查询新增的工作流节点：
                //**********************************************************************************
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRptNo = (String) tTransferData.getValueByName("RptNo");
                String tRptorName = (String) tTransferData.getValueByName("RptorName");
                String tCustomerNo = (String) tTransferData.getValueByName("CustomerNo");
                String tCustomerName = (String) tTransferData.getValueByName("CustomerName");
                String tCustomerSex = (String) tTransferData.getValueByName("CustomerSex");
                String tAccDate = (String) tTransferData.getValueByName("AccDate");
                loggerDebug("LLGrpReportSave","tRptNo="+tRptNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000009005' "
                           + " and processid = '0000000009'"
                           + " and  missionprop1 = '" +tRptNo+"'";
                loggerDebug("LLGrpReportSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "查询工作流节点失败!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLGrpReportSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    loggerDebug("LLGrpReportSave","mLWMissionSchema" + tLWMissionSchema.getActivityID());
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    loggerDebug("LLGrpReportSave","---返回新建工作流节点="+MissionID);
                //**********************************************************************************End
%>
<script language="javascript">
    parent.fraInterface.fm.all("ClmNo").value="<%=tRptNo%>";    
    parent.fraInterface.fm.all("MissionID").value="<%=MissionID%>";
    parent.fraInterface.fm.all("SubMissionID").value="<%=SubMissionID%>";
</script>
<%
                Content = "数据提交成功";
                FlagStr = "Succ";
                }
            }
        }
        catch(Exception ex)
        {
            Content = "数据提交GrpClaimWorkFlowUI失败，原因是:" + ex.toString();
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
            else if(transact.equals("delete"))
            {
                transact = "DELETE";
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
            loggerDebug("LLGrpReportSave","---LLGrpReportUI submitData and transact="+transact);
//            if (!tLLGrpReportUI.submitData(tVData,transact))
//            {
//                Content = " LLGrpReportUI处理数据失败，原因是: " + tLLGrpReportUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "LLGrpReportUI处理数据失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "LLGrpReportUI处理数据失败";
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
            Content = "数据提交LLGrpReportUI失败，原因是:" + ex.toString();
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
