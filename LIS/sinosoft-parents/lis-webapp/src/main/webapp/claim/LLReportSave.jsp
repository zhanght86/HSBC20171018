<%
//**************************************************************************************************
//Name��LLReportSave.jsp
//Function��������Ϣ�ύ
//Author��zl
//Date: 2005-6-9 15:31
//Desc: ��ҳ����Ҫ����������ܣ�������Ϣ���жϲ��������ֱ��ύ���������½����������޸���ֱ�Ӵ���
//�޸ģ�niuzj,2006-01-12,�����������ʱ�
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
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
//�������
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //�¼���
LLAccidentSubSchema tLLAccidentSubSchema = new LLAccidentSubSchema(); //���¼���
LLReportSchema tLLReportSchema = new LLReportSchema(); //������
LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema(); //�ְ���
LLReportRelaSchema tLLReportRelaSchema = new LLReportRelaSchema(); //�����ְ�������
LLCaseRelaSchema tLLCaseRelaSchema = new LLCaseRelaSchema(); //�ְ��¼�������
LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //�������ͱ�
LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //�������ͼ���

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//LLReportUI mLLReportUI = new LLReportUI(); //�����޸��ύ��
//LLReportUI tLLReportUI = new LLReportUI(); //�����޸��ύ��
//String busiName="ClaimWorkFlowUI";
//BusinessDelegate tBusinessDelegate0=BusinessDelegate.getBusinessDelegate();
String busiName="LLReportUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();


//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLReportSave","ҳ��ʧЧ,�����µ�½");
}
else
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    
    //**********************************************************************************************
    //��ȡ����ҳ����Ϣ�������ֶ���schema�����String����ɢ����ʹ��TransferData���
    //**********************************************************************************************

    //��ȡ��������insert||first��insert||customer��update��
    String transact = request.getParameter("fmtransact");
    //��������
    String tClaimType[] = request.getParameterValues("claimType");
    //����ԭ��
    String toccurReason = request.getParameter("occurReason");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");

    //���ո���
    String tRgtClass = request.getParameter("RgtClass");
    if (tRgtClass == null || tRgtClass == "")
    {
        tRgtClass = "1";
    }
    
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //�¼���(���¼�ʱΪ��)
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //�¹�����===����ԭ��
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //���յص�
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //�¹�����
    
    tLLReportSchema.setRptNo(request.getParameter("RptNo")); //������=�ⰸ��
	tLLReportSchema.setRptorName(request.getParameter("RptorName")); //����������
    tLLReportSchema.setRptorPhone(request.getParameter("RptorPhone")); //�����˵绰
    tLLReportSchema.setRptorAddress(request.getParameter("RptorAddress")); //������ͨѶ��ַ
    
    tLLReportSchema.setPostCode(request.getParameter("PostCode")); //�������ʱ�
    tLLReportSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ
    tLLReportSchema.setAccidentDate(tLLAccidentSchema.getAccDate()); //�����¹ʷ�������
    tLLReportSchema.setAccidentReason(tLLAccidentSchema.getAccType()); //����ԭ��
    tLLReportSchema.setAccidentSite(tLLAccidentSchema.getAccPlace()); //���յص�
    
    tLLReportSchema.setRptMode(request.getParameter("RptMode")); //������ʽ  
    tLLReportSchema.setRgtFlag("10"); //������־Ϊ10��ʾδ����
    tLLReportSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLReportSchema.setRgtClass(request.getParameter("RgtClass")); //�����ŵ�
    tLLReportSchema.setRgtState("11"); //��������:��ͨ����
    tLLReportSchema.setPeoples2(1); //Ͷ������

    tLLSubReportSchema.setSubRptNo(tLLReportSchema.getRptNo()); //�ְ���=������=�ⰸ��
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //����������
    tLLSubReportSchema.setSex(request.getParameter("customerSex")); //�������Ա�
	tLLSubReportSchema.setAccDate(request.getParameter("OtherAccidentDate")); //������������
	tLLSubReportSchema.setMedAccDate(request.getParameter("MedicalAccidentDate")); //ҽ�Ƴ�������
	
    tLLSubReportSchema.setHospitalCode(request.getParameter("hospital")); //����ҽԺ
    tLLSubReportSchema.setHospitalName(request.getParameter("TreatAreaName")); //����ҽԺ
    tLLSubReportSchema.setAccidentDetail(request.getParameter("accidentDetail")); //����ϸ��
//    tLLSubReportSchema.setDieFlag(request.getParameter("IsDead")); //������־
    tLLSubReportSchema.setCureDesc(request.getParameter("cureDesc")); //�������
    tLLSubReportSchema.setRemark(tLLReportSchema.getRemark()); //��ע
    //tLLSubReportSchema.setVIPFlag(request.getParameter("IsVip")); //vip��־

    //************************************************************************************
    //6��1�ձ���������ģ�
    //------ICD10�����루��ѡ���ICD10�Ӵ��루��ѡ�������ԭ��ϸ��û�н�����Ӧ���ֶΡ�
    //------������������סԺ���ۺϣ�����Ӧ�ֶΡ�������ǰ̨���ģ�
    //------���б��������еġ��������������¼���ֶΣ�Ӧ�ø���Ϊ�����վ��������ߡ��¹���������
    //���������������£�
    //************************************************************************************
    tLLSubReportSchema.setAccDesc(request.getParameter("AccDesc")); //�¹�����
    tLLSubReportSchema.setAccResult1(request.getParameter("AccResult1")); //ICD10������
    tLLSubReportSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10�Ӵ���

    for (int i = 0; i < tClaimType.length; i++)
    {
        tClaimType[i] = toccurReason + tClaimType[i];
        tLLReportReasonSchema = new LLReportReasonSchema();
        tLLReportReasonSchema.setRpNo(tLLReportSchema.getRptNo()); //������=�ⰸ��
        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //��������
        tLLReportReasonSet.add(tLLReportReasonSchema);
    }

    //Stringʹ��TransferData������ύ
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

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLReportSchema);
    tVData.add(tLLSubReportSchema);
    tVData.add(tLLReportReasonSet);

    //��һ�α����ύʱ���ύ�����������棬������ֱ���ύ��ҵ����,���ύ������ʱwFlag=9999999999
    if (transact.equals("insert||first"))
    {
        wFlag = "insertnoflow";
        try
        {
            loggerDebug("LLReportSave","---LLReportUI submitData and transact="+transact);
//            if(!mLLReportUI.submitData(tVData,wFlag))
//            {
//                Content = " LLReportUI��������ʧ�ܣ�ԭ����: ";
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName))
			   {    
			        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			        { 
							   Content = " LLReportUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getFirstError();
							   FlagStr = "Fail";
					}
					else
					{
							   Content = "LLReportUI��������ʧ��";
							   FlagStr = "Fail";				
					}
			   }

            else
            {
            	VData tempVData1 = new VData();
    			//tempVData1 = mLLReportUI.getResult();
    			tempVData1 = tBusinessDelegate1.getResult();
    			// ��ǰ̨���ش�����Ĳ����Ա��ѯ������
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
                Content = " �����ύ�ɹ���";
                FlagStr = "Succ";
            }
        }
        catch(Exception ex)
        {
            Content = "�����ύLLReportUIʧ�ܣ�ԭ����:" + ex.toString();
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
                Content = "����������" ;
                FlagStr = "Fail";
                return;
            }
        }
        try
        {
            //�����ύ
            loggerDebug("LLReportSave","---LLReportUI submitData and transact="+transact);
//            if (!tLLReportUI.submitData(tVData,transact))
//            {
//                Content = " LLReportUI��������ʧ�ܣ�ԭ����: " + tLLReportUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate2.submitData(tVData,transact,busiName))
			   {    
			        if(tBusinessDelegate2.getCErrors()!=null&&tBusinessDelegate2.getCErrors().getErrorCount()>0)
			        { 
							   Content = " LLReportUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate2.getCErrors().getFirstError();
							   FlagStr = "Fail";
					}
					else
					{
							   Content = "LLReportUI��������ʧ��";
							   FlagStr = "Fail";				
					}
			   }
            else
            {
                tVData.clear();
                Content = " �����ύ�ɹ���";
                FlagStr = "Succ";
            }
        }
        catch(Exception ex)
        {
            Content = "�����ύLLReportUIʧ�ܣ�ԭ����:" + ex.toString();
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
