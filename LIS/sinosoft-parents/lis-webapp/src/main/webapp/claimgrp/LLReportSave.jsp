<%
//**************************************************************************************************
//Name��LLReportSave.jsp
//Function��������Ϣ�ύ
//Author��zl
//Date: 2005-6-9 15:31
//Desc: ��ҳ����Ҫ����������ܣ�������Ϣ���жϲ��������ֱ��ύ���������½����������޸���ֱ�Ӵ���
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
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
LLReportUI tLLReportUI = new LLReportUI(); //�����޸��ύ��

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
    String trgtstate[] = request.getParameterValues("rgtstate");
    //��������
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
    //tLLReportSchema.setRptNo(request.getParameter("RptNo")); //������=�ⰸ��
    tLLReportSchema.setRptNo(request.getParameter("ClmNo")); //������=�ⰸ��
    //tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo")); //�ְ���=������=�ⰸ��
    tLLSubReportSchema.setSubRptNo(request.getParameter("ClmNo")); //�ְ���=������=�ⰸ�� 
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //�¹�����===����ԭ��
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //���յص�
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //�¹�����

    tLLReportSchema.setRptorName(request.getParameter("RptorName")); //����������
    tLLReportSchema.setRptorPhone(request.getParameter("RptorPhone")); //�����˵绰
    tLLReportSchema.setRptorAddress(request.getParameter("RptorAddress")); //������ͨѶ��ַ
    tLLReportSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ
    tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLReportSchema.setRptMode(request.getParameter("RptMode")); //������ʽ
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //����ԭ��
    tLLReportSchema.setAccidentSite(request.getParameter("AccidentSite")); //���յص�
    tLLReportSchema.setRgtFlag("10"); //������־Ϊ10��ʾδ����
    tLLReportSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLReportSchema.setRgtClass(request.getParameter("RgtClass")); //�����ŵ�
//�������屨����Ϣ
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //���屣����
    tLLReportSchema.setRiskCode(request.getParameter("Polno")); //�������ֺ�
    tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //����ͻ���
    tLLReportSchema.setGrpName(request.getParameter("GrpName")); //��λ����
    tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //Ͷ��������
    tLLReportSchema.setRgtObj(request.getParameter("AccFlag")); //Ͷ��������

    if (trgtstate==null)
    {         
      tLLReportSchema.setAvaliReason(""); //��������
    }    
     else 
    {    
      for (int i = 0; i < trgtstate.length; i++)
      {
        tLLReportSchema.setAvaliReason(trgtstate[i]); //��������
        loggerDebug("LLReportSave","trgtstate[i]:"+trgtstate[i]);
      }       
    }

    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //�����˱���
    tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate")); //��������
    tLLSubReportSchema.setHospitalCode(request.getParameter("hospital")); //����ҽԺ����
    tLLSubReportSchema.setHospitalName(request.getParameter("TreatAreaName")); //����ҽԺ����
    tLLSubReportSchema.setAccidentDetail(request.getParameter("accidentDetail")); //����ϸ��
//    tLLSubReportSchema.setDieFlag(request.getParameter("IsDead")); //������־
    tLLSubReportSchema.setCureDesc(request.getParameter("cureDesc")); //�������
    tLLSubReportSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLSubReportSchema.setCondoleFlag(request.getParameter("strcondoleflag"));
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
        //tLLReportReasonSchema.setRpNo(request.getParameter("RptNo")); //������=�ⰸ��
        tLLReportReasonSchema.setRpNo(request.getParameter("ClmNo")); //������=�ⰸ��
        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //��������
        tLLReportReasonSet.add(tLLReportReasonSchema);
    }

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    String GrpCustomerNo = request.getParameter("GrpCustomerNo");
    if(GrpCustomerNo!=null&&!GrpCustomerNo.equals("")){
    mTransferData.setNameAndValue("RptorName", request.getParameter("RptorName")); //����������
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("GrpCustomerNo")); //����ͻ���
    mTransferData.setNameAndValue("CustomerName", request.getParameter("GrpName")); //��λ����
    mTransferData.setNameAndValue("CustomerSex", ""); //�������Ա�
    mTransferData.setNameAndValue("AccDate", PubFun.getCurrentDate()); //��������
    mTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo")); //���屣����
    }else{
    mTransferData.setNameAndValue("RptorName", request.getParameter("RptorName")); //����������
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo")); //�����˴���
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName")); //����������
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex")); //�������Ա�
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2")); //��������
    mTransferData.setNameAndValue("GrpContNo", ""); //���屣����
    }
    //mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo")); //�ⰸ��
    mTransferData.setNameAndValue("RptNo", request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("MngCom", ManageCom);
    if (trgtstate==null)
    {         
      mTransferData.setNameAndValue("RgtState", ""); //��������
    }    
     else 
    {    
      for (int i = 0; i < trgtstate.length; i++)
      {        
        mTransferData.setNameAndValue("RgtState", trgtstate[i]); //��������       
      }       
    }
    
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
        wFlag = "9999999999";
        try
        {
            loggerDebug("LLReportSave","---ClaimWorkFlowUI submitData and transact="+transact);
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
            {
                //�ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                loggerDebug("LLReportSave","tVData="+tVData);
                //**********************************************************************************Beg
                //ȡ�ô����Ľڵ����,Ŀǰ�������ø��ݴ��������ѯ�����Ĺ������ڵ㣺
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
                    Content = "��ѯ�������ڵ�ʧ��!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLReportSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    loggerDebug("LLReportSave","mLWMissionSchema" + tLWMissionSchema.getActivityID());
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    loggerDebug("LLReportSave","---�����½��������ڵ�="+MissionID);
                //**********************************************************************************End
%>
<script language="javascript">
    parent.fraInterface.fm.all("ClmNo").value="<%=tRptNo%>";    
    parent.fraInterface.fm.all("MissionID").value="<%=MissionID%>";
    parent.fraInterface.fm.all("SubMissionID").value="<%=SubMissionID%>";
</script>
<%
                Content = "�����ύ�ɹ�";
                FlagStr = "Succ";
                }
            }
        }
        catch(Exception ex)
        {
            Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
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
                Content = "����������" ;
                FlagStr = "Fail";
                return;
            }
        }
        try
        {
            //�����ύ
            loggerDebug("LLReportSave","---LLReportUI submitData and transact="+transact);
            if (!tLLReportUI.submitData(tVData,transact))
            {
                Content = " LLReportUI��������ʧ�ܣ�ԭ����: " + tLLReportUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
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
