<%
//**************************************************************************************************
//Name��LLClaimAuditConclusionSave.jsp
//Function����˽����ύ
//Author��zhoulei
//Date: 2005-7-2 11:37
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//�������
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
String AuditConclusion = request.getParameter("SimpleConclusion2");
//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLGrpSimpleSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    //��ȡ������
    String transact = request.getParameter("fmtransact");
    String SimpleConclusion2 = request.getParameter("SimpleConclusion2");
    String mRptNo = request.getParameter("RptNo");
    //��ȡ����ҳ����Ϣ
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setCaseNo(request.getParameter("RptNo")); //�ְ���
    tLLClaimUWMainSchema.setRgtNo(request.getParameter("RptNo")); //������
    tLLClaimUWMainSchema.setAuditConclusion(request.getParameter("SimpleConclusion2")); //��˽���
    tLLClaimUWMainSchema.setAuditIdea(request.getParameter("AuditIdea")); //������
    tLLClaimUWMainSchema.setSpecialRemark(request.getParameter("SpecialRemark1")); //���ⱸע
   // tLLClaimUWMainSchema.setAuditNoPassReason(request.getParameter("ProtestReason")); //��˲�ͨ��ԭ��
   // tLLClaimUWMainSchema.setAuditNoPassDesc(request.getParameter("ProtestReasonDesc")); //��˲�ͨ������

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "40");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("RgtClass", "1");  //��������
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag"));
    mTransferData.setNameAndValue("AuditPopedom", request.getParameter("AuditPopedom")); //���Ȩ��
    mTransferData.setNameAndValue("Auditer", request.getParameter("Operator")); //�����
    //ת����������
    mTransferData.setNameAndValue("BudgetFlag", "0");  //�Ƿ�Ԥ��
    mTransferData.setNameAndValue("IsRunBL", "1");  //�Ƿ�����BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //Ȩ�޽׶α�ʾA:���B:����
    //����������־
    mTransferData.setNameAndValue("SimpleFlag","1");
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    String tsql="select rgtstate from llregister where rgtno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpSimpleSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimUWMainSchema);
    tVData.add(mTransferData);

    try
    {
        //�ύ����
        loggerDebug("LLGrpSimpleSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
//        LLClaimAuditUI tLLClaimAuditUI = new LLClaimAuditUI();
//        
//        
//        if(!tLLClaimAuditUI.submitData(tVData,transact))
//        {
//            Content = " �����ύLLClaimAuditUIʧ�ܣ�ԭ����: " + tLLClaimAuditUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="grpLLClaimAuditUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύLLClaimAuditUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύLLClaimAuditUIʧ��";
		FlagStr = "Fail";				
	}
}

        else
        {
            
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
            
            loggerDebug("LLGrpSimpleSave","========�����ٱ��մ���     sujd   2007-12-17=====");
            if (AuditConclusion.equals("0"))
            {     
                 //�����ٱ��մ���     sujd   2007-12-17
                 //ReLifeNeed tReLifeNeed=new ReLifeNeed();
                 VData tmpvdata=new VData();
                 
                 TransferData tmpTransferData = new TransferData();
                 tmpTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
                 tmpTransferData.setNameAndValue("Operator",tGI.Operator);
                 tmpTransferData.setNameAndValue("OtherNo",request.getParameter("RptNo"));
                 
                 tmpvdata.add(tmpTransferData);
                 
                 /** if (!tReLifeNeed.submitdata(tmpvdata,"2"))
                 {
                      Content=Content+";�ٱ��մ���ʧ��,����ϵ��Ϣ��������Ա(������Ҫ�᰸)!";
                      FlagStr = "Fail";
                 }
                 else
                 {
                     Content=Content+";�ٱ��մ���ɹ�!";           
                 } */
                 loggerDebug("LLGrpSimpleSave","==================�ٱ����������=================");
            }            
            
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
        
        loggerDebug("LLGrpSimpleSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
    }
    catch(Exception ex)
    {
        Content = "�����ύLLClaimAuditUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>","<%=AuditConclusion%>");
</script>
</html>
