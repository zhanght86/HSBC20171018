<%
//**************************************************************************************************
//Name：LLClaimRegisterConfirmSave.jsp
//Function：立案信息确认
//Author：zhoulei
//Date: 2005-6-20 11:34
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

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
String FlagStr="";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimRegisterConfirmSave","页面失效,请重新登陆");
}
else 
{
    String RgtConclusion = request.getParameter("RgtConclusion");
    String mRptNo = request.getParameter("RptNo");
    String GrpContNo=request.getParameter("GrpContNo");
    
    //**************************************************
    //工作流相关参数使用TransferData打包后提交
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "30");
    if(request.getParameter("RptNo") == null && request.getParameter("RptNo").equals("")){
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("GrpCustomerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("GrpName"));
    }else{
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    }
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //申请类型：个险为1,团险为2
    mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //其他号码
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
    mTransferData.setNameAndValue("PrepayFlag", "0");  //预付标志
    mTransferData.setNameAndValue("ComeWhere", "A");  //来自:A立案进入审核，B审批进入审核因为不通过，C审批进入审核因为赔案为负数，D为预付进入审核
    mTransferData.setNameAndValue("Auditer", "0");  //审核操作人,为审批不通过时返回个人工作队列用
    //------------------------------------------------------------------------------BEG
    //审核权限，在匹配计算完善后加入service类后去掉此行
    //------------------------------------------------------------------------------
//    mTransferData.setNameAndValue("Popedom","A01"); 
    //------------------------------------------------------------------------------END
    //转移条件参数
    mTransferData.setNameAndValue("SimpleFlag", "0");  //是否简易案件
    //业务类需要数据
    mTransferData.setNameAndValue("PopedomPhase","A"); //权限阶段标示A:审核B:审批
    mTransferData.setNameAndValue("standpay",request.getParameter("standpay")); //预估金额
    mTransferData.setNameAndValue("adjpay",request.getParameter("adjpay")); //调整后金额
    
    mTransferData.setNameAndValue("RgtConclusion",RgtConclusion); //立案通过标志01通过，02不予立案，03延迟立案；
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009015");
    
    String tsql="select rgtstate from llreport where rptno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLClaimRegisterConfirmSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);    
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //数据传输
    if (RgtConclusion.equals("03"))
    {
        loggerDebug("LLClaimRegisterConfirmSave","-------------------start LLClaimRegisterDealUI---------------------");
        //延迟立案不走工作流
       // LLClaimRegisterDealUI tLLClaimRegisterDealUI = new LLClaimRegisterDealUI();
//        if(!tLLClaimRegisterDealUI.submitData(tVData,"update"))
//        {
//            Content = " 数据提交LLClaimRegisterDealUI失败，原因是: " + tLLClaimRegisterDealUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="grpLLClaimRegisterDealUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"update",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "数据提交LLClaimRegisterDealUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "数据提交LLClaimRegisterDealUI失败";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimRegisterConfirmSave","-------------------end LLClaimRegisterDealUI---------------------");
    }
    else
    {
        loggerDebug("LLClaimRegisterConfirmSave","-------------------start workflow---------------------");
        wFlag = "0000009015";
//        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {
//            Content = " 数据提交ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName1="grpClaimWorkFlowUI";
		BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName1))
		{    
		    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "数据提交ClaimWorkFlowUI失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "数据提交ClaimWorkFlowUI失败";
				FlagStr = "Fail";				
			}
		}

        else
        {
                if (RgtConclusion.equals("01")) //立案通过
                {
                 //tongmeng 2009-01-05 modify
                 //屏蔽该处处理
                 /*
                 //加入再保发送通知判断    sujd   2007-11-29
                 loggerDebug("LLClaimRegisterConfirmSave","===============开始进行再保险处理======================");
                 SSRS tmpSSRS=new SSRS();
                 ExeSQL tmpExeSQL=new ExeSQL();
                 tmpSSRS=tmpExeSQL.execSQL("select distinct customerno from llclaimdetail where clmno='"+mRptNo+"'");
                     for (int tmpi=1;tmpi<=tmpSSRS.getMaxRow();tmpi++)
                      {
                             String tmpSQL="select nvl(sum(amnt),0) from lcpol where polstate='1' and insuredno='"+tmpSSRS.GetText(tmpi,1)+"' and exists (select * from lmriskapp where substr(risktypedetail,1,1)='S' and riskcode=lcpol.riskcode)";
                             loggerDebug("LLClaimRegisterConfirmSave","tmpSQL:"+tmpSQL);
                             String TotalAmnt=tmpExeSQL.getOneValue(tmpSQL);
                             String LimitAmnt=tmpExeSQL.getOneValue("select codealias from ldcode where codetype='relife' and code='limit'");                  
                             
                             loggerDebug("LLClaimRegisterConfirmSave","被保人总保额:"+TotalAmnt);
                             loggerDebug("LLClaimRegisterConfirmSave","发送通知限制保额:"+LimitAmnt);
                           
                                 if (Stringtools.stringtodouble(TotalAmnt)>=Stringtools.stringtodouble(LimitAmnt))  //每一生命大于40万
                                 {
                                      	TransferData RTransferData = new TransferData();
                                      	RTransferData.setNameAndValue("RgtNo",mRptNo);
                                        RTransferData.setNameAndValue("CustomerNo",tmpSSRS.GetText(tmpi,1)); //个人客户号
                                        RTransferData.setNameAndValue("GrpContNo",GrpContNo);
                                      
                                        SSRS MailSSRS=new SSRS();
                                        SSRS PhoneSSRS=new SSRS(); 
									                    	
									                    	MailSSRS=tmpExeSQL.execSQL("select codealias from ldcode where codetype='relife' and code='mail' and codename='address'");                    
                                        PhoneSSRS=tmpExeSQL.execSQL("select codealias from ldcode where codetype='relife' and code='phone' and codename='number'");                    
                                        
                                        VData Rvdata=new VData();
                                        Rvdata.add(RTransferData);
                                        Rvdata.add(MailSSRS);
                                        Rvdata.add(PhoneSSRS);
                                        
                                        SendClaimMess tSendClaimMess=new SendClaimMess();
                                        tSendClaimMess.submitdata(Rvdata,"");
                                 }
                      }
                  
               loggerDebug("LLClaimRegisterConfirmSave","===============再保险处理完成======================");  
               */
                  //**************88再保险处理完成***************************//
              }
        
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimRegisterConfirmSave","-------------------end workflow---------------------");
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
