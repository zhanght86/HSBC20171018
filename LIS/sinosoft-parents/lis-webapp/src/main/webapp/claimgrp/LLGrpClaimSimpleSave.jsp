<%
//**************************************************************************************************
//Name��LLGrpClaimSimpleSave.jsp
//Function��������װ�����Ϣ�ύ
//Author��pd
//Date: 2005-11-9
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>


<%

//�������
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
//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLGrpClaimSimpleSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
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
    //��������ز���ʹ��TransferData������ύ
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "30");
    mTransferData.setNameAndValue("CustomerNo", CustomerNo);
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2"));
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //��������
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("PrepayFlag", "0");  //Ԥ����־
    mTransferData.setNameAndValue("Auditer", "0");  //��˲�����,Ϊ������ͨ��ʱ���ظ��˹���������
    mTransferData.setNameAndValue("standpay", tstandpay);  
    mTransferData.setNameAndValue("adjpay", tadjpay);  
    //ת����������
    if(request.getParameter("Flag")!= null && request.getParameter("Flag").equals("2")){
    mTransferData.setNameAndValue("AuditFlag", request.getParameter("SimpleConclusion2"));  
     }else{
    mTransferData.setNameAndValue("AuditFlag", request.getParameter("SimpleConclusion"));  
     }
    //1���װ��� 2���׸���
    mTransferData.setNameAndValue("Flag", request.getParameter("Flag"));  
    //ҵ������Ҫ����
    mTransferData.setNameAndValue("PopedomPhase","A"); //Ȩ�޽׶�:���A;      
    mTransferData.setNameAndValue("RgtConclusion","01"); //����ͨ����־01ͨ����02����������03�ӳ�������
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009015");

    String tsql="select rgtstate from llregister where rgtno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpClaimSimpleSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //���ݴ���
    loggerDebug("LLGrpClaimSimpleSave","tisNew:"+tisNew);
   if (tisNew == "" || tisNew == null || tisNew.equals(""))
   {
    loggerDebug("LLGrpClaimSimpleSave","-------------------start LLGrpClaimSimpleBL---------------------");

//    if(!tLLGrpClaimSimpleBL.submitData(tVData,transact))
//    {
//        Content = " �����ύʧ�ܣ�ԭ����: " + tLLGrpClaimSimpleBL.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύʧ��";
		FlagStr = "Fail";				
	}
}

    else
    {
        Content = " �����ύ�ɹ���";
        FlagStr = "Succ";
        
         //�����ٱ�����֪ͨ�ж�    sujd   2007-11-28
         loggerDebug("LLGrpClaimSimpleSave","===============��ʼ�����ٱ��մ���======================");
         SSRS tmpSSRS=new SSRS();
         ExeSQL tmpExeSQL=new ExeSQL();
         tmpSSRS=tmpExeSQL.execSQL("select distinct customerno from llclaimdetail where clmno='"+mRptNo+"'");
             for (int tmpi=1;tmpi<=tmpSSRS.getMaxRow();tmpi++)
              {
                     String tmpSQL="select nvl(sum(amnt),0) from lcpol where polstate='1' and insuredno='"+tmpSSRS.GetText(tmpi,1)+"' and exists (select * from lmriskapp where substr(risktypedetail,1,1)='S' and riskcode=lcpol.riskcode)";
                     loggerDebug("LLGrpClaimSimpleSave","tmpSQL:"+tmpSQL);
                      String TotalAmnt=tmpExeSQL.getOneValue(tmpSQL);
                       String LimitAmnt=tmpExeSQL.getOneValue("select codealias from ldcode where codetype='relife' and code='limit'");                  
                     
                     loggerDebug("LLGrpClaimSimpleSave","�������ܱ���:"+TotalAmnt);
                     loggerDebug("LLGrpClaimSimpleSave","����֪ͨ���Ʊ���:"+LimitAmnt);
                   
                         if (Stringtools.stringtodouble(TotalAmnt)>=Stringtools.stringtodouble(LimitAmnt))  //ÿһ��������40��
                         {
                              	TransferData RTransferData = new TransferData();
                              	RTransferData.setNameAndValue("RgtNo",mRptNo);
                                RTransferData.setNameAndValue("CustomerNo",tmpSSRS.GetText(tmpi,1)); //���˿ͻ���
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
          
       loggerDebug("LLGrpClaimSimpleSave","===============�ٱ��մ������======================");                  
          //**************88�ٱ��մ������***************************//                    
    }
        /*��������ֹ�м��2�ν᰸��ť*/             
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
            CError.buildErr(this, "�������ʧ�ܣ�");                
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
//            Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName1="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName1))
{    
    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύClaimWorkFlowUIʧ��";
		FlagStr = "Fail";				
	}
}

        else
        {
                       
            Content = " �����ύ�ɹ���";
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
