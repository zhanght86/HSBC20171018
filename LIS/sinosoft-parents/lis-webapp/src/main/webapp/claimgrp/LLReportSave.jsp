<%
//**************************************************************************************************
//Name：LLReportSave.jsp
//Function：报案信息提交
//Author：zl
//Date: 2005-6-9 15:31
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
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
LLReportUI tLLReportUI = new LLReportUI(); //报案修改提交类

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
    String trgtstate[] = request.getParameterValues("rgtstate");
    //报案类型
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
    //tLLReportSchema.setRptNo(request.getParameter("RptNo")); //报案号=赔案号
    tLLReportSchema.setRptNo(request.getParameter("ClmNo")); //报案号=赔案号
    //tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo")); //分案号=报案号=赔案号
    tLLSubReportSchema.setSubRptNo(request.getParameter("ClmNo")); //分案号=报案号=赔案号 
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //事故类型===出险原因
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //出险地点
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述

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
    tLLReportSchema.setRgtClass(request.getParameter("RgtClass")); //个单团单
//新增团体报案信息
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体保单号
    tLLReportSchema.setRiskCode(request.getParameter("Polno")); //保单险种号
    tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //团体客户号
    tLLReportSchema.setGrpName(request.getParameter("GrpName")); //单位名称
    tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //投保总人数
    tLLReportSchema.setRgtObj(request.getParameter("AccFlag")); //投保总人数

    if (trgtstate==null)
    {         
      tLLReportSchema.setAvaliReason(""); //报案类型
    }    
     else 
    {    
      for (int i = 0; i < trgtstate.length; i++)
      {
        tLLReportSchema.setAvaliReason(trgtstate[i]); //报案类型
        loggerDebug("LLReportSave","trgtstate[i]:"+trgtstate[i]);
      }       
    }

    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //出险人编码
    tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate")); //出险日期
    tLLSubReportSchema.setHospitalCode(request.getParameter("hospital")); //治疗医院代码
    tLLSubReportSchema.setHospitalName(request.getParameter("TreatAreaName")); //治疗医院名称
    tLLSubReportSchema.setAccidentDetail(request.getParameter("accidentDetail")); //出险细节
//    tLLSubReportSchema.setDieFlag(request.getParameter("IsDead")); //死亡标志
    tLLSubReportSchema.setCureDesc(request.getParameter("cureDesc")); //治疗情况
    tLLSubReportSchema.setRemark(request.getParameter("Remark")); //备注
    tLLSubReportSchema.setCondoleFlag(request.getParameter("strcondoleflag"));
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
        //tLLReportReasonSchema.setRpNo(request.getParameter("RptNo")); //报案号=赔案号
        tLLReportReasonSchema.setRpNo(request.getParameter("ClmNo")); //报案号=赔案号
        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //理赔类型
        tLLReportReasonSet.add(tLLReportReasonSchema);
    }

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    String GrpCustomerNo = request.getParameter("GrpCustomerNo");
    if(GrpCustomerNo!=null&&!GrpCustomerNo.equals("")){
    mTransferData.setNameAndValue("RptorName", request.getParameter("RptorName")); //报案人姓名
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("GrpCustomerNo")); //团体客户号
    mTransferData.setNameAndValue("CustomerName", request.getParameter("GrpName")); //单位名称
    mTransferData.setNameAndValue("CustomerSex", ""); //出险人性别
    mTransferData.setNameAndValue("AccDate", PubFun.getCurrentDate()); //报案日期
    mTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo")); //团体保单号
    }else{
    mTransferData.setNameAndValue("RptorName", request.getParameter("RptorName")); //报案人姓名
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo")); //出险人代码
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName")); //出险人姓名
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex")); //出险人性别
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2")); //报案日期
    mTransferData.setNameAndValue("GrpContNo", ""); //团体保单号
    }
    //mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo")); //赔案号
    mTransferData.setNameAndValue("RptNo", request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("MngCom", ManageCom);
    if (trgtstate==null)
    {         
      mTransferData.setNameAndValue("RgtState", ""); //报案类型
    }    
     else 
    {    
      for (int i = 0; i < trgtstate.length; i++)
      {        
        mTransferData.setNameAndValue("RgtState", trgtstate[i]); //报案类型       
      }       
    }
    
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
            loggerDebug("LLReportSave","---ClaimWorkFlowUI submitData and transact="+transact);
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = " 提交工作流ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
            {
                //从结果集中取出前台需要数据
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                loggerDebug("LLReportSave","tVData="+tVData);
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
                loggerDebug("LLReportSave","tRptNo="+tRptNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000005005' "
                           + " and processid = '0000000005'"
                           + " and  missionprop1 = '" +tRptNo+"'";
                loggerDebug("LLReportSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "查询工作流节点失败!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLReportSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    loggerDebug("LLReportSave","mLWMissionSchema" + tLWMissionSchema.getActivityID());
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    loggerDebug("LLReportSave","---返回新建工作流节点="+MissionID);
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
            Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
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
            loggerDebug("LLReportSave","---LLReportUI submitData and transact="+transact);
            if (!tLLReportUI.submitData(tVData,transact))
            {
                Content = " LLReportUI处理数据失败，原因是: " + tLLReportUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
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
