<%
//**************************************************************************************************
//Name��LLClaimRegisterConfirmSave.jsp
//Function��������Ϣȷ��
//Author��zhoulei
//Date: 2005-6-20 11:34
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
String FlagStr="";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimRegisterConfirmSave","ҳ��ʧЧ,�����µ�½");
}
else 
{
    String RgtConclusion = request.getParameter("RgtConclusion");
    String mRptNo = request.getParameter("RptNo");
    String GrpContNo=request.getParameter("GrpContNo");
    
    //**************************************************
    //��������ز���ʹ��TransferData������ύ
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
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //�������ͣ�����Ϊ1,����Ϊ2
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("PrepayFlag", "0");  //Ԥ����־
    mTransferData.setNameAndValue("ComeWhere", "A");  //����:A����������ˣ�B�������������Ϊ��ͨ����C�������������Ϊ�ⰸΪ������DΪԤ���������
    mTransferData.setNameAndValue("Auditer", "0");  //��˲�����,Ϊ������ͨ��ʱ���ظ��˹���������
    //------------------------------------------------------------------------------BEG
    //���Ȩ�ޣ���ƥ��������ƺ����service���ȥ������
    //------------------------------------------------------------------------------
//    mTransferData.setNameAndValue("Popedom","A01"); 
    //------------------------------------------------------------------------------END
    //ת����������
    mTransferData.setNameAndValue("SimpleFlag", "0");  //�Ƿ���װ���
    //ҵ������Ҫ����
    mTransferData.setNameAndValue("PopedomPhase","A"); //Ȩ�޽׶α�ʾA:���B:����
    mTransferData.setNameAndValue("standpay",request.getParameter("standpay")); //Ԥ�����
    mTransferData.setNameAndValue("adjpay",request.getParameter("adjpay")); //��������
    
    mTransferData.setNameAndValue("RgtConclusion",RgtConclusion); //����ͨ����־01ͨ����02����������03�ӳ�������
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009015");
    
    String tsql="select rgtstate from llreport where rptno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLClaimRegisterConfirmSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);    
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //���ݴ���
    if (RgtConclusion.equals("03"))
    {
        loggerDebug("LLClaimRegisterConfirmSave","-------------------start LLClaimRegisterDealUI---------------------");
        //�ӳ��������߹�����
       // LLClaimRegisterDealUI tLLClaimRegisterDealUI = new LLClaimRegisterDealUI();
//        if(!tLLClaimRegisterDealUI.submitData(tVData,"update"))
//        {
//            Content = " �����ύLLClaimRegisterDealUIʧ�ܣ�ԭ����: " + tLLClaimRegisterDealUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="grpLLClaimRegisterDealUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"update",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ύLLClaimRegisterDealUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ύLLClaimRegisterDealUIʧ��";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " �����ύ�ɹ���";
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
                if (RgtConclusion.equals("01")) //����ͨ��
                {
                 //tongmeng 2009-01-05 modify
                 //���θô�����
                 /*
                 //�����ٱ�����֪ͨ�ж�    sujd   2007-11-29
                 loggerDebug("LLClaimRegisterConfirmSave","===============��ʼ�����ٱ��մ���======================");
                 SSRS tmpSSRS=new SSRS();
                 ExeSQL tmpExeSQL=new ExeSQL();
                 tmpSSRS=tmpExeSQL.execSQL("select distinct customerno from llclaimdetail where clmno='"+mRptNo+"'");
                     for (int tmpi=1;tmpi<=tmpSSRS.getMaxRow();tmpi++)
                      {
                             String tmpSQL="select nvl(sum(amnt),0) from lcpol where polstate='1' and insuredno='"+tmpSSRS.GetText(tmpi,1)+"' and exists (select * from lmriskapp where substr(risktypedetail,1,1)='S' and riskcode=lcpol.riskcode)";
                             loggerDebug("LLClaimRegisterConfirmSave","tmpSQL:"+tmpSQL);
                             String TotalAmnt=tmpExeSQL.getOneValue(tmpSQL);
                             String LimitAmnt=tmpExeSQL.getOneValue("select codealias from ldcode where codetype='relife' and code='limit'");                  
                             
                             loggerDebug("LLClaimRegisterConfirmSave","�������ܱ���:"+TotalAmnt);
                             loggerDebug("LLClaimRegisterConfirmSave","����֪ͨ���Ʊ���:"+LimitAmnt);
                           
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
                                        
                                        SendClaimMess tSendClaimMess=new SendClaimMess();
                                        tSendClaimMess.submitdata(Rvdata,"");
                                 }
                      }
                  
               loggerDebug("LLClaimRegisterConfirmSave","===============�ٱ��մ������======================");  
               */
                  //**************88�ٱ��մ������***************************//
              }
        
            Content = " �����ύ�ɹ���";
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
