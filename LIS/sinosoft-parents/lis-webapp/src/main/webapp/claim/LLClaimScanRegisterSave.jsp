<%
//**************************************************************************************************
//Name��LLRegisterSave.jsp
//Function��������Ϣ�ύ
//Author��zhoulei
//Date: 2005-6-15 16:28
//�޸ģ�niuzj,2006-01-13,�����Ǽ�ʱ��Ҫ��LLRegister���е���ȡ��ʽGetMode�ֶ�Ĭ���ó�1
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
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLClaimScanRegisterSave","######################LLClaimRegisterSave.jsp start#############################");

//�������
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //�¼���
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //������
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //��������
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //�������ͱ�
LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema(); //�������ͱ�
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //�������ͼ���


//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
String busiName="tWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();


if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimScanRegisterSave","ҳ��ʧЧ,�����µ�½");
}
else //ҳ����Ч
{

	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //��ȡ��������insert||first��insert||customer��update��
    String transact = request.getParameter("fmtransact");
    
    //��������
    String tClaimType[] = request.getParameterValues("claimType");
    //����ԭ��
    String toccurReason = request.getParameter("occurReason");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");

    //��ȡ����ҳ����Ϣ
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //�¼���
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //�¹�����===����ԭ��
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //���յص�
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //�¹�����
    
    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //������
    tLLRegisterSchema.setRgtantName(request.getParameter("RptorName")); //����������
    tLLRegisterSchema.setRgtantPhone(request.getParameter("RptorPhone")); //�����˵绰
    tLLRegisterSchema.setRgtantMobile(request.getParameter("RptorMoPhone")); //�������ֻ����� 
    tLLRegisterSchema.setRgtantAddress(request.getParameter("RptorAddress")); //������ͨѶ��ַ
    tLLRegisterSchema.setPostCode(request.getParameter("AppntZipCode")); //�������ʱ�
    tLLRegisterSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ
    tLLRegisterSchema.setRgtState("11"); //��������:��ͨ����
    
    tLLRegisterSchema.setAccidentSite(tLLAccidentSchema.getAccPlace()); //���յص�
    tLLRegisterSchema.setAccidentDate(tLLAccidentSchema.getAccDate()); //�����¹ʷ�������
    tLLRegisterSchema.setAccidentReason(tLLAccidentSchema.getAccType()); //����ԭ��
    
    tLLRegisterSchema.setAssigneeType(request.getParameter("AssigneeType")); //����������
    tLLRegisterSchema.setAssigneeCode(request.getParameter("AssigneeCode")); //�����˴���
    tLLRegisterSchema.setAssigneeName(request.getParameter("AssigneeName")); //����������
    tLLRegisterSchema.setAssigneeSex(request.getParameter("AssigneeSex")); //�������Ա�
    tLLRegisterSchema.setAssigneePhone(request.getParameter("AssigneePhone")); //�����˵绰
    tLLRegisterSchema.setAssigneeAddr(request.getParameter("AssigneeAddr")); //�����˵�ַ
    tLLRegisterSchema.setAssigneeZip(request.getParameter("AssigneeZip")); //�������ʱ�
    tLLRegisterSchema.setAcceptedDate(request.getParameter("AcceptedDate")); //��������
    tLLRegisterSchema.setApplyDate(request.getParameter("ApplyDate")); //�ͻ���������
    
    tLLRegisterSchema.setRgtClass("1"); //�����ŵ� 1������
    tLLRegisterSchema.setGetMode("1");  //��ȡ��ʽ��Ĭ��ֵΪ1
    tLLRegisterSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLRegisterSchema.setPeoples2("1"); //��������
    tLLRegisterSchema.setCasePayType("1"); //������������ 1--һ�������

    tLLCaseSchema.setCaseNo(tLLRegisterSchema.getRgtNo()); //�ְ���=������=�ⰸ��
    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
    tLLCaseSchema.setCustomerName(request.getParameter("customerName")); //����������
    tLLCaseSchema.setCustomerAge(request.getParameter("customerAge")); //����������
    tLLCaseSchema.setCustomerSex(request.getParameter("customerSex")); //�������Ա�
    
    //tLLCaseSchema.setVIPFlag(request.getParameter("IsVip")); //vip��־
    tLLCaseSchema.setAccDate(request.getParameter("OtherAccidentDate")); //������������
    tLLCaseSchema.setMedAccDate(request.getParameter("MedicalAccidentDate")); //ҽ�Ƴ�������
    tLLCaseSchema.setAccidentDetail(request.getParameter("accidentDetail")); //����ϸ��
    
    tLLCaseSchema.setCureDesc(request.getParameter("cureDesc")); //�������
    tLLCaseSchema.setAccResult1(request.getParameter("AccResult1")); //ICD10������
    tLLCaseSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10�Ӵ���
    tLLCaseSchema.setAccdentDesc(request.getParameter("AccDesc")); //�¹�����
    tLLCaseSchema.setHospitalCode(request.getParameter("hospital")); //����ҽԺ����
    tLLCaseSchema.setHospitalName(request.getParameter("TreatAreaName")); //����ҽԺ����
    tLLCaseSchema.setRemark(tLLRegisterSchema.getRemark()); //��ע
    tLLCaseSchema.setSeqNo("1"); //���
    
    tLLRegisterIssueSchema.setRgtNo(request.getParameter("RptNo"));
    tLLRegisterIssueSchema.setCustomerNo(request.getParameter("customerNo"));
    
	for (int i = 0; i < tClaimType.length; i++)
	{
	    tClaimType[i] = toccurReason + tClaimType[i];
	    tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
	    
	    tLLAppClaimReasonSchema.setCaseNo(tLLRegisterSchema.getRgtNo()); //������=�ⰸ��,�����������ᱻ�滻��������
	 	tLLAppClaimReasonSchema.setRgtNo(tLLRegisterSchema.getRgtNo()); //������=�ⰸ��,���滻
	 	tLLAppClaimReasonSchema.setCustomerNo(tLLCaseSchema.getCustomerNo()); //�����˱���
	 	tLLAppClaimReasonSchema.setReasonCode(tClaimType[i]); //��������
	 	
	   	tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
	}
    

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    //���������
    mTransferData.setNameAndValue("RgtNo", tLLRegisterSchema.getRgtNo());
    mTransferData.setNameAndValue("RptorState", "20");
    mTransferData.setNameAndValue("CustomerNo", tLLCaseSchema.getCustomerNo());
    mTransferData.setNameAndValue("CustomerName", tLLCaseSchema.getCustomerName());
    mTransferData.setNameAndValue("CustomerSex", tLLCaseSchema.getCustomerSex());
    mTransferData.setNameAndValue("AccDate", tLLAccidentSchema.getAccDate());
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("ScanFlag", "1");//��ɨ
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("RptNo", tLLRegisterSchema.getRgtNo());

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("Operator",Operator);
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());
    
    mTransferData.setNameAndValue("BusiType", "1003");
  
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLRegisterIssueSchema);
    tVData.add(tLLAppClaimReasonSet);
    
    //��һ�α����ύʱ���ύ�����������棬������ֱ���ύ��ҵ����,���ύ������ʱwFlag=9899999999
    if (transact.equals("insert||first"))
    {
        try
        {
            loggerDebug("LLClaimScanRegisterSave","---WorkFlowUI submitData and transact="+transact);
            //if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: ";
//                for(int i=0;i<tClaimWorkFlowUI.mErrors.getErrorCount();i++){
//                	Content = Content + "  " + tClaimWorkFlowUI.mErrors.getError(i).errorMessage;
//                }
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			    {    
			        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			        { 
			        	 Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: ";
			        	int n = tBusinessDelegate.getCErrors().getErrorCount();
				        for (int i = 0; i < n; i++)
				        {
				            //loggerDebug("LLClaimScanRegisterSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
				            Content = Content + "  "  + tBusinessDelegate.getCErrors().getError(i).errorMessage;
				        }
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
                //tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLClaimScanRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //ȡ�ô����Ľڵ����,Ŀǰ�������ø��ݴ��������ѯ�����Ĺ������ڵ㣺
                //**********************************************************************************
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRptNo = (String) tTransferData.getValueByName("RptNo");
                loggerDebug("LLClaimScanRegisterSave","tRptNo="+tRptNo);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLClaimScanRegisterSave","tRgtNo="+tRgtNo);
 
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid in(select activityid from lwactivity where functionid='10030003') "
                           + " and  missionprop1 = '" +tRgtNo+"'";
                loggerDebug("LLClaimScanRegisterSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null || tLWMissionSet.size() == 0)
                {
                    Content = "��ѯ�������ڵ�ʧ��!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLClaimScanRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    String ActivityID = tLWMissionSchema.getActivityID();
//                  String ClmNo = tLWMissionSchema.getMissionProp7();
//                  loggerDebug("LLClaimScanRegisterSave","���º����������"+ClmNo);
                    String RptNo = tLWMissionSchema.getMissionProp1();
                    loggerDebug("LLClaimScanRegisterSave","��������"+RptNo);
                    loggerDebug("LLClaimScanRegisterSave","---�����½��������ڵ�="+MissionID);
%>
<script language="javascript">
    parent.fraInterface.document.all("MissionID").value="<%=MissionID%>";
    parent.fraInterface.document.all("SubMissionID").value="<%=SubMissionID%>";
    parent.fraInterface.document.all("RptNo").value="<%=RptNo%>";
    parent.fraInterface.document.all("ActivityID").value="<%=ActivityID%>";
</script>
<%
                    Content = "�����ύ�ɹ�";
                    FlagStr = "Succ";
                }
                //**********************************************************************************End
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

        try
        {
            //�����ύ
            loggerDebug("LLClaimScanRegisterSave","---LLClaimRegisterBLF submitData and transact="+transact);
//            LLClaimRegisterBLF tLLClaimRegisterBLF = new LLClaimRegisterBLF();
//            if (!tLLClaimRegisterBLF.submitData(tVData,transact))
//            {
//                Content = " �޸���Ϣʧ�ܣ�ԭ����: " + tLLClaimRegisterBLF.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            String busiName1="LLClaimRegisterBLF";
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
             if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
		   {    
		        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		        { 
						   Content = " �޸���Ϣʧ�ܣ�ԭ����: "  + tBusinessDelegate1.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = " �޸���Ϣʧ�� "; 
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
            Content = "�����ύLLClaimRegisterUIʧ�ܣ�ԭ����:" + ex.toString();
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
