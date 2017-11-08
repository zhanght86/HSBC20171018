<%
//**************************************************************************************************
//Name：LLGrpClaimSimpleSave.jsp
//Function：团体简易案件信息提交
//Author：pd
//Date: 2005-11-9
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
String FlagStr = "Fail";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//LLGrpClaimSimpleBL tLLGrpClaimSimpleBL = new LLGrpClaimSimpleBL();
String busiName="grpLLGrpClaimSimpleBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

String mRptNo="";
//页面有效
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLGrpClaimSimpleSave","页面失效,请重新登陆");
}
else
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    mRptNo = request.getParameter("RptNo");
    String tisNew = request.getParameter("isNew");
    String CustomerNo=request.getParameter("customerNo");
    String GrpContNo=request.getParameter("GrpContNo");
    String tstandpay = "0.0";
    String tadjpay = "0.0";
    /*String tMoneySql = " select a.realpay,b.BeAdjSum "
        + " from LLClaim a,LLRegister b  where 1=1 "
        + " and a.ClmNo = b.RgtNo and a.ClmNo = '"+mRptNo+"'";
    SSRS tempSSRS = new SSRS();
    ExeSQL  tempExeSQL = new ExeSQL();
    tempSSRS = tempExeSQL.execSQL(tMoneySql);
    if(tempSSRS!=null){
    	tstandpay = tempSSRS.GetText(1,1);
    	tadjpay = tempSSRS.GetText(1,2);
    	if(tadjpay=="0"){
    		tadjpay = tstandpay;
    	}
    }*/
    //**************************************************
    //工作流相关参数使用TransferData打包后提交
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "30");
    mTransferData.setNameAndValue("CustomerNo", CustomerNo);
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2"));
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //申请类型
    mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //其他号码
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
    mTransferData.setNameAndValue("PrepayFlag", "0");  //预付标志
    mTransferData.setNameAndValue("Auditer", "0");  //审核操作人,为审批不通过时返回个人工作队列用
    mTransferData.setNameAndValue("standpay", tstandpay);  
    mTransferData.setNameAndValue("adjpay", tadjpay);  
    //转移条件参数
    if(request.getParameter("Flag")!= null && request.getParameter("Flag").equals("2")){
    mTransferData.setNameAndValue("AuditFlag", request.getParameter("SimpleConclusion2"));  
     }else{
    mTransferData.setNameAndValue("AuditFlag", request.getParameter("SimpleConclusion"));  
     }
    //1简易案件 2简易复核
    mTransferData.setNameAndValue("Flag", request.getParameter("Flag"));  
    //业务处理需要数据
    mTransferData.setNameAndValue("PopedomPhase","A"); //权限阶段:审核A;      
    mTransferData.setNameAndValue("RgtConclusion","01"); //立案通过标志01通过，02不予立案，03延迟立案；
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009015");

    String tsql="select rgtstate from llregister where rgtno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpClaimSimpleSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //数据传输
    loggerDebug("LLGrpClaimSimpleSave","tisNew:"+tisNew);
   if (tisNew == "" || tisNew == null || tisNew.equals(""))
   {
    loggerDebug("LLGrpClaimSimpleSave","-------------------start LLGrpClaimSimpleBL---------------------");

//    if(!tLLGrpClaimSimpleBL.submitData(tVData,transact))
//    {
//        Content = " 数据提交失败，原因是: " + tLLGrpClaimSimpleBL.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "数据提交失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "数据提交失败";
		FlagStr = "Fail";				
	}
}

    else
    {
        Content = " 数据提交成功！";
        FlagStr = "Succ";
        
         //加入再保发送通知判断    sujd   2007-11-28
         loggerDebug("LLGrpClaimSimpleSave","===============开始进行再保险处理======================");
         SSRS tmpSSRS=new SSRS();
         ExeSQL tmpExeSQL=new ExeSQL();
         tmpSSRS=tmpExeSQL.execSQL("select distinct customerno from llclaimdetail where clmno='"+mRptNo+"'");
             for (int tmpi=1;tmpi<=tmpSSRS.getMaxRow();tmpi++)
              {
                     String tmpSQL="select nvl(sum(amnt),0) from lcpol where polstate='1' and insuredno='"+tmpSSRS.GetText(tmpi,1)+"' and exists (select * from lmriskapp where substr(risktypedetail,1,1)='S' and riskcode=lcpol.riskcode)";
                     loggerDebug("LLGrpClaimSimpleSave","tmpSQL:"+tmpSQL);
                      String TotalAmnt=tmpExeSQL.getOneValue(tmpSQL);
                       String LimitAmnt=tmpExeSQL.getOneValue("select codealias from ldcode where codetype='relife' and code='limit'");                  
                     
                     loggerDebug("LLGrpClaimSimpleSave","被保人总保额:"+TotalAmnt);
                     loggerDebug("LLGrpClaimSimpleSave","发送通知限制保额:"+LimitAmnt);
                   
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
                                
                                /** SendClaimMess tSendClaimMess=new SendClaimMess();
                                tSendClaimMess.submitdata(Rvdata,"");*/
                                	
                                
                         }
                   
              }
          
       loggerDebug("LLGrpClaimSimpleSave","===============再保险处理完成======================");                  
          //**************88再保险处理完成***************************//                    
    }
        /*解锁，防止中间点2次结案按钮*/             
		    MMap tempMMap=new MMap();
		    ExeSQL ttExeSQL=new ExeSQL();
		    SSRS tSSRS=new SSRS();
		    String ttsql="select getdutykind from llclaim where clmno='"+mRptNo+"' and getdutykind is not null ";
		    tSSRS=ttExeSQL.execSQL(ttsql);
		    if(tSSRS.MaxRow>0 && tSSRS.GetText(1,1).equals("1"))
		    {
		      tempMMap.put("update llclaim set getdutykind='' where clmno='"+mRptNo+"' ","UPDATE");
		      PubSubmit tempSubmit = new PubSubmit();
          VData temVData = new VData();
          temVData.add(tempMMap);
          if (!tempSubmit.submitData(temVData, null)) 
          {
            CError.buildErr(this, "理赔解锁失败！");                
          }
		    }      
    
    loggerDebug("LLGrpClaimSimpleSave","-------------------end LLGrpClaimSimpleBL---------------------");
   }else
     {
        loggerDebug("LLGrpClaimSimpleSave","-------------------start workflow---------------------");
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
                       
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLGrpClaimSimpleSave","-------------------end workflow---------------------");  
      }    
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
