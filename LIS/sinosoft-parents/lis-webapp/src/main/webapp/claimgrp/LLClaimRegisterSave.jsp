<%
//**************************************************************************************************
//Name：LLClaimRegisterSave.jsp
//Function：立案信息提交
//Author：zhoulei
//Date: 2005-6-15 16:28
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
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.service.*" %>
<%
loggerDebug("LLClaimRegisterSave","######################LLClaimRegisterSave.jsp start#############################");

//输入参数
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //立案表
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //分立案表
LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //赔案表
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //理赔类型表
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //理赔类型集合

//******************************************************************jinsh20070403
LLReportSchema tLLReportSchema = new LLReportSchema(); //报案表
LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema(); //分案表
LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //理赔类型表
LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //理赔类型集合
//*********************************************************************jinsh20070403


//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//LLClaimRegisterUI tLLClaimRegisterUI = new LLClaimRegisterUI();
//LLClaimRegisterBL tLLClaimRegisterBL = new LLClaimRegisterBL();
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
String busiName1="grpLLClaimRegisterUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
String busiName2="grpLLClaimRegisterBL";
BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimRegisterSave","页面失效,请重新登陆");
}
else //页面有效
{

  String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //获取操作符（insert||first、insert||customer、update）
    String transact = request.getParameter("fmtransact");
    
    //团险个险
    String tRgtClass = request.getParameter("RgtClass");
    if (tRgtClass == null || tRgtClass == "")
    {
        tRgtClass = "2";
    }
    loggerDebug("LLClaimRegisterSave","tRgtClass:"+tRgtClass);
    //理赔类型
    String tClaimType[] = request.getParameterValues("claimType");
    //出险原因
    String toccurReason = request.getParameter("occurReason");
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");
    String tAddPersonFlag = ""; //增人标志
    //判断是增人操作还是立案保存
    ///////////////////////////////////////////////////////////////////////////////////////
    ExeSQL tExeSQL = new ExeSQL();
    String tSql = "select count(1) from llregister where rgtno='"+mRptNo+"'";
	String tRgtFlag = tExeSQL.getOneValue(tSql);
	if("1".equals(tRgtFlag)){
		tAddPersonFlag = "1";
	}
	////////////////////////////////////////////////////////////////////////////////////////
    //获取报案页面信息
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //事件号
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //事故类型===出险原因
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //出险地点
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述
    tLLClaimSchema.setClmNo(request.getParameter("RptNo")); //赔案号

    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //报案号=赔案号
    tLLRegisterSchema.setRgtantName(request.getParameter("RptorName")); //报案人姓名
    tLLRegisterSchema.setRgtantPhone(request.getParameter("RptorPhone")); //报案人电话
    tLLRegisterSchema.setRgtantAddress(request.getParameter("RptorAddress")); //报案人通讯地址
    tLLRegisterSchema.setRelation(request.getParameter("Relation")); //报案人与事故人关系
    tLLRegisterSchema.setAccidentSite(request.getParameter("AccidentSite")); //出险地点
    tLLRegisterSchema.setAccidentDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLRegisterSchema.setAccidentReason(request.getParameter("occurReason")); //出险原因
    tLLRegisterSchema.setAssigneeType(request.getParameter("AssigneeType")); //受托人类型
    tLLRegisterSchema.setAssigneeCode(request.getParameter("AssigneeCode")); //受托人代码
    tLLRegisterSchema.setAssigneeName(request.getParameter("AssigneeName")); //受托人姓名
    tLLRegisterSchema.setAssigneeSex(request.getParameter("AssigneeSex")); //受托人性别
    tLLRegisterSchema.setAssigneePhone(request.getParameter("AssigneePhone")); //受托人电话
    tLLRegisterSchema.setAssigneeAddr(request.getParameter("AssigneeAddr")); //受托人地址
    tLLRegisterSchema.setAssigneeZip(request.getParameter("AssigneeZip")); //受托人邮编
    tLLRegisterSchema.setRemark(request.getParameter("Remark")); //备注
    tLLRegisterSchema.setRgtClass(tRgtClass); //个单团单
//新增团体立案信息
    tLLRegisterSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体保单号
    tLLRegisterSchema.setRiskCode(request.getParameter("Polno")); //保单险种号
    tLLRegisterSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //团体客户号
    tLLRegisterSchema.setGrpName(request.getParameter("GrpName")); //单位名称
    tLLRegisterSchema.setPeoples2(request.getParameter("Peoples")); //投保总人数
    tLLRegisterSchema.setGrpStandpay(request.getParameter("Grpstandpay")); //团体预估值
    tLLRegisterSchema.setRptFlag("1"); //报案标志  1-已报案
    tLLRegisterSchema.setAcceptedDate(request.getParameter("AcceptedDate")); //受理日期
    
    
    //*********************************jinsh20070405加报案表
    tLLReportSchema.setRptNo(request.getParameter("RptNo")); //报案号=赔案号
    //loggerDebug("LLClaimRegisterSave","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+request.getParameter("RptNo"));    
 	tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //团体客户号
  	tLLReportSchema.setGrpName(request.getParameter("GrpName")); //单位名称
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体保单号
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //出险原因
    tLLReportSchema.setRiskCode(request.getParameter("Polno")); //险种编码
    tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //出险日期
    tLLReportSchema.setRptorName(request.getParameter("RptorName")); //申请人姓名
    tLLReportSchema.setRptorPhone(request.getParameter("RptorPhone")); //申请人电话
    tLLReportSchema.setRptorAddress(request.getParameter("RptorAddress")); //申请人通讯地址
    tLLReportSchema.setRelation(request.getParameter("Relation")); //申请人与事故人关系 
    tLLReportSchema.setAccidentSite(request.getParameter("AccidentSite")); //出险地点
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //出险原因
    tLLReportSchema.setRgtState(request.getParameter("RgtState"));
    //tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //投保总人数
    //受托人代码
    //受托人姓名
    
    
    

    tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo")); //分案号=报案号=赔案号
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //客户号 
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //单位名称
    tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate")); //出险日期
    tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate")); //出险日期
    tLLSubReportSchema.setCureDesc(request.getParameter("cureDesc")); //治疗情况
    //单证齐全标示
    tLLSubReportSchema.setAccResult2(request.getParameter("AccResult2")); //出险结果编码
    //出险结果
    tLLSubReportSchema.setHospitalCode(request.getParameter("hospital")); //治疗医院代码
    //治疗医院
    tLLSubReportSchema.setAccidentDetail(request.getParameter("accidentDetail")); //出险细节编码
    //出险细节
    tLLSubReportSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述 
    tLLSubReportSchema.setRemark(request.getParameter("Remark")); //备注 
    tLLSubReportSchema.setAccidentType(request.getParameter("occurReason"));

    
    
    
    
		for (int i = 0; i < tClaimType.length; i++)
			   {
			        tClaimType[i] = toccurReason + tClaimType[i];
			        loggerDebug("LLClaimRegisterSave","tClaimType[i]:"+tClaimType[i]);
			        tLLReportReasonSchema = new LLReportReasonSchema();
			        tLLReportReasonSchema.setRpNo(request.getParameter("RptNo")); //报案号=赔案号			      
			        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
			        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //理赔类型
			        tLLReportReasonSet.add(tLLReportReasonSchema);
			        
			        
			        tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
				      tLLAppClaimReasonSchema.setCaseNo(request.getParameter("RptNo")); //报案号=赔案号
				      tLLAppClaimReasonSchema.setRgtNo(request.getParameter("RptNo")); //报案号=赔案号
				      tLLAppClaimReasonSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
				      tLLAppClaimReasonSchema.setReasonCode(tClaimType[i]); //理赔类型
				      tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
			   }
    //*******************************************jinsh20070405
    
    
    
    
    

    tLLCaseSchema.setCaseNo(request.getParameter("RptNo")); //分案号=报案号=赔案号
    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
    tLLCaseSchema.setCustomerName(request.getParameter("customerName")); //出险姓名
    String tStringAge = request.getParameter("customerAge");
    loggerDebug("LLClaimRegisterSave","tStringAge======="+tStringAge);
    try{
    	tLLCaseSchema.setCustomerAge(request.getParameter("customerAge")); //出险人年龄
    }catch(Exception ex){
    	ex.printStackTrace();
    }
    tLLCaseSchema.setCustomerSex(request.getParameter("customerSex")); //出险人性别
    
    //tongmeng 2009-01-05 add
    //分案表RGTType 设成11
    tLLCaseSchema.setRgtType("11");
    tLLCaseSchema.setAccDate(request.getParameter("AccidentDate")); //出险日期
    tLLCaseSchema.setMedAccDate(request.getParameter("AccidentDate")); //医疗出险日期
    
    tLLCaseSchema.setAccidentDetail(request.getParameter("accidentDetail")); //出险细节
    tLLCaseSchema.setCureDesc(request.getParameter("cureDesc")); //治疗情况
    tLLCaseSchema.setAccResult1(request.getParameter("AccResult2")); //ICD10主代码
    tLLCaseSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10子代码
    tLLCaseSchema.setAccdentDesc(request.getParameter("AccDesc")); //事故描述
    tLLCaseSchema.setHospitalCode(request.getParameter("hospital")); //治疗医院代码
    tLLCaseSchema.setHospitalName(request.getParameter("TreatAreaName")); //治疗医院名称
    tLLCaseSchema.setHospitalName(request.getParameter("TreatAreaName")); //治疗医院名称
    tLLCaseSchema.setRemark(request.getParameter("Remark")); //备注
    tLLCaseSchema.setStandpay(request.getParameter("standpay")); //个人预估值
    tLLCaseSchema.setAffixConclusion(request.getParameter("IsAllReady"));


    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("Remark", request.getParameter("Remark"));
    mTransferData.setNameAndValue("hospital", request.getParameter("hospital"));

    //工作流相关
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "20");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("GrpCustomerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("GrpName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("AddPersonFlag", tAddPersonFlag);

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());
    
    if(request.getParameter("GrpCustomerNo")!=null && !request.getParameter("GrpCustomerNo").equals("")){
    mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    }else{
    mTransferData.setNameAndValue("GrpContNo","");
    }
    
    String tsql="select rgtstate from llreport where rptno='"+mRptNo+"' ";
    
    //String tRgtState=tExeSQL.getOneValue(tsql);
    //loggerDebug("LLClaimRegisterSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",request.getParameter("RgtState"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLAppClaimReasonSet);
    
    //******************************************************jinsh20070405
    tVData.add(tLLReportSchema);
    tVData.add(tLLSubReportSchema);
    tVData.add(tLLReportReasonSchema);
    tVData.add(tLLReportReasonSet);
    //******************************************************jinsh20070405


    //第一次保存提交时，提交到工作流引擎，其他的直接提交到业务类,当提交工作流时wFlag=9899999999
    if (transact.equals("insert||first"))
    {
        wFlag = "9899999999";
        try
        {
//分红判断
    TransferData  mTransferData2 =new TransferData();
    mTransferData2.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    mTransferData2.setNameAndValue("InsuredNo",request.getParameter("customerNo"));
    mTransferData2.setNameAndValue("AccDate",request.getParameter("AccidentDate"));
    //String[] tResult=new String[3];
    //tResult=PubFun1.CheckFeildClaim(mTransferData2);
    //if ("1".equals(tResult[0]))
    //{
    //   Content = "保存失败，原因是:" + tResult[2];
    //   FlagStr = "Fail";
    //}else{ //----------------分红判断
            loggerDebug("LLClaimRegisterSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " 提交工作流ClaimWorkFlowUI失败，原因是: " +tClaimWorkFlowUI.mErrors.getError(0).errorMessage; 
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
                //tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLClaimRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //取得处理后的节点参数,目前方法采用根据传入参数查询新增的工作流节点：
                //**********************************************************************************
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLClaimRegisterSave","tRptNo="+tRgtNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000009015' "
                           + " and processid = '0000000009'"
                           + " and  missionprop1 = '" +tRgtNo+"'";
                loggerDebug("LLClaimRegisterSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "查询工作流节点失败!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLClaimRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    String ClmNo = tLWMissionSchema.getMissionProp1();
                    loggerDebug("LLClaimRegisterSave","---返回新建工作流节点="+MissionID);
%>
<script language="javascript">
    parent.fraInterface.fm.all("MissionID").value="<%=MissionID%>";
    parent.fraInterface.fm.all("SubMissionID").value="<%=SubMissionID%>";
    parent.fraInterface.fm.all("ClmNo").value="<%=ClmNo%>";
</script>
<%
                    Content = "数据提交成功";
                    FlagStr = "Succ";
                }
                //**********************************************************************************End
            }
        //}//----------------分红判断
     }catch(Exception ex)
        {
            Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
    }else if(transact.equals("DELETE")){
        try
        {
            //数据提交
            loggerDebug("LLClaimRegisterSave","---tLLClaimRegisterBL submitData and transact="+transact);
//            if (!tLLClaimRegisterBL.submitData(tVData,transact))
//            {
//                Content = " tLLClaimRegisterBL处理数据失败，原因是: " + tLLClaimRegisterBL.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            if(!tBusinessDelegate2.submitData(tVData,transact,busiName2))
			{    
			    if(tBusinessDelegate2.getCErrors()!=null&&tBusinessDelegate2.getCErrors().getErrorCount()>0)
			    { 
					Content = "tLLClaimRegisterBL处理数据失败，原因是:" + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "tLLClaimRegisterBL处理数据失败";
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
            Content = "数据提交tLLClaimRegisterBL失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
    }
    else
    {
        if(transact.equals("INSERT"))
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
            loggerDebug("LLClaimRegisterSave","---LLClaimRegisterUI submitData and transact="+transact);
//            if (!tLLClaimRegisterUI.submitData(tVData,transact)){
//                Content = " LLClaimRegisterUI处理数据失败，原因是: " + tLLClaimRegisterUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "LLClaimRegisterUI处理数据失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "LLClaimRegisterUI处理数据失败";
					FlagStr = "Fail";				
				}
			}
            else{
                tVData.clear();
                Content = " 数据提交成功！";
                FlagStr = "Succ";
                tVData.clear();
                //tVData = tLLClaimRegisterUI.getResult();
                  tVData = tBusinessDelegate1.getResult();
                loggerDebug("LLClaimRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //取得处理后的节点参数,目前方法采用根据传入参数查询新增的工作流节点：
                //**********************************************************************************
                if(!"UPDATE".equals(transact)&&!"1".equals(tAddPersonFlag)){
	                TransferData tTransferData = new TransferData();
	                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
	                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
	                loggerDebug("LLClaimRegisterSave","tRgtNo="+tRgtNo);
	                LWMissionDB ttLWMissionDB = new LWMissionDB();
	                LWMissionSchema ttLWMissionSchema = new LWMissionSchema();
	                String sql = "select * from LWMission where"
	                           + " activityid = '0000009015' "
	                           + " and processid = '0000000009'"
	                           + " and  missionprop1 = '" +tRgtNo+"'";
	                loggerDebug("LLClaimRegisterSave","Start query mission:" + sql);
	                LWMissionSet tLWMissionSet = ttLWMissionDB.executeQuery(sql);
	                if (tLWMissionSet == null && tLWMissionSet.size() == 0){
	                    Content = "查询工作流节点失败!" ;
	                    FlagStr = "Fail";
	                }else{
		                    loggerDebug("LLClaimRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
		                    ttLWMissionSchema = tLWMissionSet.get(1);
		                    String MissionID = ttLWMissionSchema.getMissionID();
		                    String SubMissionID = ttLWMissionSchema.getSubMissionID();
		                    String ClmNo = ttLWMissionSchema.getMissionProp1();
		                    loggerDebug("LLClaimRegisterSave","---返回新建工作流节点="+MissionID);
		                %>
		                <script language="javascript">
		                    parent.fraInterface.fm.all("MissionID").value="<%=MissionID%>";
		                    parent.fraInterface.fm.all("SubMissionID").value="<%=SubMissionID%>";
		                    parent.fraInterface.fm.all("ClmNo").value="<%=ClmNo%>";
		                </script>
		                <%
		            }
                }
            }
        }
        catch(Exception ex)
        {
            Content = "数据提交LLClaimRegisterUI失败，原因是:" + ex.toString();
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
