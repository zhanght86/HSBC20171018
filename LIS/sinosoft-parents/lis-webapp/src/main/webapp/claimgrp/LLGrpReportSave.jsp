<%
//**************************************************************************************************
//Name��LLGrpReportSave.jsp
//Function�����屨����Ϣ�ύ
//Author��zhangzheng
//Date: 2008-10-29 
//Desc: ��ҳ����Ҫ����������ܣ�������Ϣ���жϲ��������ֱ��ύ���������½����������޸���ֱ�Ӵ���
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
loggerDebug("LLGrpReportSave","#########################LLReportSave.jsp start#################################");
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
GlobalInput tGI=(GlobalInput)session.getValue("GI");
//GrpClaimWorkFlowUI tGrpClaimWorkFlowUI = new GrpClaimWorkFlowUI(); //�������⹤��������
//LLGrpReportUI tLLGrpReportUI = new LLGrpReportUI(); //�����޸��ύ��
String busiName="grpGrpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

String busiName1="grpLLGrpReportUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLGrpReportSave","ҳ��ʧЧ,�����µ�½");
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
        tRgtClass = "2";
    }
    
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //�¼���(���¼�ʱΪ��)
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //�¹�����===����ԭ��
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //���յص�
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //�¹�����
    
    loggerDebug("LLGrpReportSave",request.getParameter("Relation"));

	//����������Ϣ
	tLLReportSchema.setRptNo(request.getParameter("RptNo")); //������=�ⰸ��
	tLLReportSchema.setRgtObj("2"); //��������:1-���ձ����ţ�2-���ձ�����
	tLLReportSchema.setRgtObjNo(tLLReportSchema.getRptNo());
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
    tLLReportSchema.setRgtClass("2"); //�����ŵ���־ 1:����,2:�ŵ�
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //���屣����
    tLLReportSchema.setRiskCode(request.getParameter("RiskCode")); //�������ֺ�
    tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //����ͻ���
    tLLReportSchema.setGrpName(request.getParameter("GrpName")); //��λ����
    tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //Ͷ��������
    tLLReportSchema.setRgtState(request.getParameter("rgtstate")); //��������
    
	//�ְ���
    tLLSubReportSchema.setSubRptNo(tLLReportSchema.getRptNo()); //�ְ���=������=�ⰸ��
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //����������
    tLLSubReportSchema.setSex(request.getParameter("customerSex")); //�������Ա�
	tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate2")); //������������
	tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate2")); //ҽ�Ƴ�������
	tLLSubReportSchema.setAccidentType(request.getParameter("occurReason"));
	
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
    tLLSubReportSchema.setAccResult1(request.getParameter("AccResult2")); //ICD10������
    tLLSubReportSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10�Ӵ���
    tLLSubReportSchema.setIDNo(request.getParameter("IDNo"));
    tLLSubReportSchema.setIDType(request.getParameter("IDType"));
    


    for (int i = 0; i < tClaimType.length; i++)
    {
        if("00".equals(tClaimType[i])){
        	tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate2"));
        }
        tClaimType[i] = toccurReason + tClaimType[i];
        tLLReportReasonSchema = new LLReportReasonSchema();
        tLLReportReasonSchema.setRpNo(tLLReportSchema.getRptNo()); //������=�ⰸ��
        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //��������
        tLLReportReasonSchema.setReasonType(request.getParameter("occurReason"));
        tLLReportReasonSchema.setReason(request.getParameter("ReasonName"));
        tLLReportReasonSet.add(tLLReportReasonSchema);
    }

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    
    mTransferData.setNameAndValue("RptNo", tLLReportSchema.getRptNo()); //������
	mTransferData.setNameAndValue("RptorName", tLLReportSchema.getRptorName()); //����������
	mTransferData.setNameAndValue("CustomerNo", tLLSubReportSchema.getCustomerNo()); //�����˿ͻ���
	mTransferData.setNameAndValue("CustomerName", tLLSubReportSchema.getCustomerName()); //����������
	mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex")); //�������Ա�
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2")); //������������
    mTransferData.setNameAndValue("MngCom", ManageCom); //�������
    mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo")); //�����ͬ��
    mTransferData.setNameAndValue("RgtState", tLLReportSchema.getRgtState()); //��������       
    mTransferData.setNameAndValue("GrpCustomerNo", request.getParameter("GrpCustomerNo")); //��������       
    mTransferData.setNameAndValue("GrpName", request.getParameter("GrpName")); //��������       

    
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
            loggerDebug("LLGrpReportSave","---GrpClaimWorkFlowUI submitData and transact="+transact);
//            if(!tGrpClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " + tGrpClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "�ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "�ύ������ClaimWorkFlowUIʧ��";
					FlagStr = "Fail";				
				}
			}

            else
            {
                //�ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                //tVData = tGrpClaimWorkFlowUI.getResult();
                 tVData = tBusinessDelegate.getResult();
                loggerDebug("LLGrpReportSave","tVData="+tVData);
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
                loggerDebug("LLGrpReportSave","tRptNo="+tRptNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000009005' "
                           + " and processid = '0000000009'"
                           + " and  missionprop1 = '" +tRptNo+"'";
                loggerDebug("LLGrpReportSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "��ѯ�������ڵ�ʧ��!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLGrpReportSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    loggerDebug("LLGrpReportSave","mLWMissionSchema" + tLWMissionSchema.getActivityID());
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    loggerDebug("LLGrpReportSave","---�����½��������ڵ�="+MissionID);
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
            Content = "�����ύGrpClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
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
            loggerDebug("LLGrpReportSave","---LLGrpReportUI submitData and transact="+transact);
//            if (!tLLGrpReportUI.submitData(tVData,transact))
//            {
//                Content = " LLGrpReportUI��������ʧ�ܣ�ԭ����: " + tLLGrpReportUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "LLGrpReportUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "LLGrpReportUI��������ʧ��";
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
            Content = "�����ύLLGrpReportUIʧ�ܣ�ԭ����:" + ex.toString();
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
