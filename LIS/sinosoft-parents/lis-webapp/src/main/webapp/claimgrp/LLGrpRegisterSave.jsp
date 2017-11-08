<%
//**************************************************************************************************
//Name：LLGrpRegisterSave.jsp
//Function：简易立案信息提交
//Author：pd
//Date: 2005-11-8
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.workflow.claimgrp.*" %>
<%
loggerDebug("LLGrpRegisterSave","######################LLGrpRegisterSave.jsp start#############################");

//输入参数
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //立案表
//******************************************************************jinsh20070403
LLReportSchema tLLReportSchema = new LLReportSchema(); //报案表
LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema(); //分案表
LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //理赔类型表
LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //理赔类型集合
//*********************************************************************jinsh20070403
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //分立案表
//LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //赔案表
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //理赔类型表
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //理赔类型集合

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
VData tVData = new VData();
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new LLGrpClaimRegisterBL();
//LLClaimRegisterUI tLLClaimRegisterUI = new LLClaimRegisterUI();
//LLClaimRegisterBL tLLClaimRegisterBL = new LLClaimRegisterBL();
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
String busiName1="grpLLGrpClaimRegisterBL";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
String busiName2="grpLLClaimRegisterUI";
BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
//String busiName3="grpLLClaimRegisterBL";
//BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();



String transact = request.getParameter("fmtransact");

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLGrpRegisterSave","页面失效,请重新登陆");
}
else //页面有效
{

    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //获取操作符（insert||first、insert||customer、update）
  
    
    //团险个险
    String tRgtClass = request.getParameter("RgtClass");
    if (tRgtClass == null || tRgtClass == "")
    {
        tRgtClass = "2";
    }
    loggerDebug("LLGrpRegisterSave","tRgtClass:"+tRgtClass);
    //理赔类型
    String tClaimType[] = request.getParameterValues("claimType");
    //出险原因
    String toccurReason = request.getParameter("occurReason");
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");



    //*********************************jinsh20070403加报案表
    tLLReportSchema.setRptNo(request.getParameter("RptNo")); //报案号=赔案号
    //loggerDebug("LLGrpRegisterSave","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+request.getParameter("RptNo"));    
 	tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //团体客户号
  	tLLReportSchema.setGrpName(request.getParameter("GrpName")); //单位名称
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体保单号
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //出险原因
    tLLReportSchema.setRiskCode(request.getParameter("Polno")); //险种编码
    tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //出险日期
    tLLReportSchema.setRelation(request.getParameter("Relation")); //申请人与事故人关系 

    tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo")); //分案号=报案号=赔案号
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //客户号 
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //单位名称
    tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate")); //出险日期
    tLLSubReportSchema.setIDNo(request.getParameter("IDNo"));
    tLLSubReportSchema.setSex(request.getParameter("customerSex"));
    tLLSubReportSchema.setIDType(request.getParameter("IDType"));
    tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate"));
    tLLSubReportSchema.setAccidentType(request.getParameter("occurReason"));
		for (int i = 0; i < tClaimType.length; i++)
			   {
			        tClaimType[i] = toccurReason + tClaimType[i];
			        loggerDebug("LLGrpRegisterSave","tClaimType[i]:"+tClaimType[i]);
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
    //*******************************************jinsh20070403
    
    
    
    //获取报案页面信息
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //事件号
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //报案号=赔案号
    tLLCaseSchema.setCaseNo(request.getParameter("RptNo")); //分案号=报案号=赔案号
//    tLLClaimSchema.setClmNo(request.getParameter("RptNo")); //赔案号

    tLLRegisterSchema.setAccidentDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLRegisterSchema.setAccidentReason(request.getParameter("occurReason")); //出险原因
    tLLRegisterSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体保单号
    tLLRegisterSchema.setRiskCode(request.getParameter("Polno")); //保单险种号
    tLLRegisterSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //团体客户号
    tLLRegisterSchema.setGrpName(request.getParameter("GrpName")); //单位名称
    tLLRegisterSchema.setPeoples2(request.getParameter("Peoples")); //投保总人数
//    tLLRegisterSchema.setAppPeoples(request.getParameter("PeopleNo")); //申请人数
    tLLRegisterSchema.setRgtClass(tRgtClass); //(1 个单) ( 2 团单)
    tLLRegisterSchema.setRelation(request.getParameter("Relation")); //报案人与事故人关系
    tLLRegisterSchema.setAssigneeType(request.getParameter("AssigneeType")); //受托人类型
    tLLRegisterSchema.setAssigneeCode(request.getParameter("AssigneeCode")); //受托人代码
    tLLRegisterSchema.setAssigneeName(request.getParameter("AssigneeName")); //受托人姓名
    tLLRegisterSchema.setAssigneeSex(request.getParameter("AssigneeSex")); //受托人性别
    tLLRegisterSchema.setAssigneePhone(request.getParameter("AssigneePhone")); //受托人电话
    tLLRegisterSchema.setAssigneeAddr(request.getParameter("AssigneeAddr")); //受托人地址
    tLLRegisterSchema.setAssigneeZip(request.getParameter("AssigneeZip")); //受托人邮编
    tLLRegisterSchema.setRgtantName(request.getParameter("RptorName")); //报案人姓名
    tLLRegisterSchema.setRgtantPhone(request.getParameter("RptorPhone")); //报案人电话
    tLLRegisterSchema.setRgtantAddress(request.getParameter("RptorAddress")); //报案人通讯地址
    tLLRegisterSchema.setAcceptedDate(request.getParameter("AcceptedDate")); //受理日期

    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
    tLLCaseSchema.setCustomerName(request.getParameter("customerName")); //出险人名称
    tLLCaseSchema.setCustomerAge(request.getParameter("customerAge")); //出险人年龄
    tLLCaseSchema.setCustomerSex(request.getParameter("customerSex")); //出险人性别
    tLLCaseSchema.setAccDate(request.getParameter("AccidentDate")); //出险日期
    tLLCaseSchema.setMedAccDate(request.getParameter("AccidentDate")); //医疗出险日期
    tLLCaseSchema.setIDType(request.getParameter("IDType")); //出险人证件类型
    tLLCaseSchema.setIDNo(request.getParameter("IDNo")); //出险人证件号码


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

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());

    if(request.getParameter("GrpCustomerNo")!=null && !request.getParameter("GrpCustomerNo").equals(""))
    {
    mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    }else{
    mTransferData.setNameAndValue("GrpContNo","");
    }

    String tsql="select rgtstate from llreport where rptno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpRegisterSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);

    //准备传输数据 VData
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLAppClaimReasonSet);
    //******************************************************jinsh20070403
    tVData.add(tLLReportSchema);
    tVData.add(tLLSubReportSchema);
    tVData.add(tLLReportReasonSchema);
    tVData.add(tLLReportReasonSet);
    //******************************************************jinsh20070403

    //第一次保存提交
    loggerDebug("LLGrpRegisterSave","transact:::::::::::::::"+transact);
    if (transact.equals("insert||first"))
    {
      /*      transact = "INSERT";
        try
        {
            loggerDebug("LLGrpRegisterSave","---first LLGrpClaimRegisterBL submitData and transact="+transact);
//            if(!tLLGrpClaimRegisterBL.submitData(tVData,transact))
//            {
//                Content = " 提交工作流LLGrpClaimRegisterBL失败，原因是: " + tLLGrpClaimRegisterBL.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
		if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
		{    
		    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "提交工作流LLGrpClaimRegisterBL失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "提交工作流LLGrpClaimRegisterBL失败";
				FlagStr = "Fail";				
			}
		}

            else
            {
                tVData.clear();
                Content = " 数据提交成功！";
                FlagStr = "Succ";
                tVData.clear();
               // tVData = tLLGrpClaimRegisterBL.getResult();
                tVData =  tBusinessDelegate1.getResult();
      %>
     
      <%           
             }
        }
        catch(Exception ex)
        {
            Content = "数据提交LLGrpClaimRegisterBL失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }   
       */
       
       wFlag = "9899999999";
        try
        {

    TransferData  mTransferData2 =new TransferData();
    mTransferData2.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    mTransferData2.setNameAndValue("InsuredNo",request.getParameter("customerNo"));
    mTransferData2.setNameAndValue("AccDate",request.getParameter("AccidentDate"));
    String[] tResult=new String[3];
    tResult=PubFun1.CheckFeildClaim(mTransferData2);
    if ("1".equals(tResult[0]))
    {
       Content = "保存失败，原因是:" + tResult[2];
       FlagStr = "Fail";
    }else{
            loggerDebug("LLGrpRegisterSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " 提交工作流ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
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
                ClaimWorkFlowUI tClaimWorkFlowUI=new ClaimWorkFlowUI();
                //从结果集中取出前台需要数据
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                loggerDebug("LLGrpRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //取得处理后的节点参数,目前方法采用根据传入参数查询新增的工作流节点：
                //**********************************************************************************
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLGrpRegisterSave","tRgtNo="+tRgtNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000005015' "
                           + " and processid = '0000000005'"
                           + " and  missionprop1 = '" +tRgtNo+"'";
                loggerDebug("LLGrpRegisterSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "查询工作流节点失败!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLGrpRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    String ClmNo = tLWMissionSchema.getMissionProp1();
                    loggerDebug("LLGrpRegisterSave","---返回新建工作流节点="+MissionID);
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
        }
     }catch(Exception ex)
        {
            Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
       
    }
    else if(transact.equals("DELETE"))
    {
         //批量案件借用账户类案件的删除程序
         loggerDebug("LLGrpRegisterSave","－－－删除测试开始－－－");
         transact = "ACCDELETE";
         try
         {
              //数据提交
//              if (!tLLGrpClaimRegisterBL.submitData(tVData,transact))
//              {
//                  Content = " LLGrpClaimRegisterBL处理数据失败，原因是: " + tLLGrpClaimRegisterBL.mErrors.getError(0).errorMessage;
//                  FlagStr = "Fail";
//              }
        if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
		{    
		    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLGrpClaimRegisterBL处理数据失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLGrpClaimRegisterBL处理数据失败";
				FlagStr = "Fail";				
			}
		}
              else
              {
                  tVData.clear();
                  Content = " 数据提交成功！";
                  FlagStr = "Succ";
                  tVData.clear();
                  //tVData = tLLGrpClaimRegisterBL.getResult();
                  tVData = tBusinessDelegate1.getResult();
              }
         }
         catch(Exception ex)
         {
              Content = "数据提交LLGrpClaimRegisterBL失败，原因是:" + ex.toString();
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
            loggerDebug("LLGrpRegisterSave","---customer LLGrpClaimRegisterBL submitData and transact="+transact);
            //if (!tLLGrpClaimRegisterBL.submitData(tVData,transact))
//            if (!tLLClaimRegisterUI.submitData(tVData,transact))
//            {
//                Content = " LLClaimRegisterUI处理数据失败，原因是: " + tLLClaimRegisterUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
        if(!tBusinessDelegate2.submitData(tVData,transact,busiName2))
		{    
		    if(tBusinessDelegate2.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLClaimRegisterUI处理数据失败，原因是:" + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLClaimRegisterUI处理数据失败";
				FlagStr = "Fail";				
			}
		}
            else
            {
                tVData.clear();
                Content = " 数据提交成功！";
                FlagStr = "Succ";
                
                //tVData = tLLGrpClaimRegisterBL.getResult();
               // tVData = tLLClaimRegisterUI.getResult();
                  tVData = tBusinessDelegate2.getResult();
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLGrpRegisterSave","tRptNo="+tRgtNo);
                %>
                <script language="javascript">
                    parent.fraInterface.fm.all("ClmNo").value="<%=tRgtNo%>";
                    parent.fraInterface.fm.all("RptNo").value="<%=tRgtNo%>";
                </script>
                <%                
            }
        }
        catch(Exception ex)
        {
            Content = "数据提交LLClaimRegisterUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
    }
}
 /*
  LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
  mLLRegisterSchema.setSchema((LLRegisterSchema)tVData.getObjectByObjectName("LLRegisterSchema", 0));
  String tRgtNo = mLLRegisterSchema.getRgtNo();
  loggerDebug("LLGrpRegisterSave","tRgtNo:"+tRgtNo);
 */ 
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
	


</script>
</html>
