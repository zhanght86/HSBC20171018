<%
//**************************************************************************************************
//Name��LLClaimRegisterSave.jsp
//Function��������Ϣ�ύ
//Author��zhoulei
//Date: 2005-6-15 16:28
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
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.service.*" %>
<%
loggerDebug("LLClaimRegisterSave","######################LLClaimRegisterSave.jsp start#############################");

//�������
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //�¼���
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //������
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //��������
LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //�ⰸ��
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //�������ͱ�
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //�������ͼ���

//******************************************************************jinsh20070403
LLReportSchema tLLReportSchema = new LLReportSchema(); //������
LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema(); //�ְ���
LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //�������ͱ�
LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //�������ͼ���
//*********************************************************************jinsh20070403


//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//LLClaimRegisterUI tLLClaimRegisterUI = new LLClaimRegisterUI();
//LLClaimRegisterBL tLLClaimRegisterBL = new LLClaimRegisterBL();
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
String busiName1="grpLLClaimRegisterUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
String busiName2="grpLLClaimRegisterBL";
BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimRegisterSave","ҳ��ʧЧ,�����µ�½");
}
else //ҳ����Ч
{

  String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //��ȡ��������insert||first��insert||customer��update��
    String transact = request.getParameter("fmtransact");
    
    //���ո���
    String tRgtClass = request.getParameter("RgtClass");
    if (tRgtClass == null || tRgtClass == "")
    {
        tRgtClass = "2";
    }
    loggerDebug("LLClaimRegisterSave","tRgtClass:"+tRgtClass);
    //��������
    String tClaimType[] = request.getParameterValues("claimType");
    //����ԭ��
    String toccurReason = request.getParameter("occurReason");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");
    String tAddPersonFlag = ""; //���˱�־
    //�ж������˲���������������
    ///////////////////////////////////////////////////////////////////////////////////////
    ExeSQL tExeSQL = new ExeSQL();
    String tSql = "select count(1) from llregister where rgtno='"+mRptNo+"'";
	String tRgtFlag = tExeSQL.getOneValue(tSql);
	if("1".equals(tRgtFlag)){
		tAddPersonFlag = "1";
	}
	////////////////////////////////////////////////////////////////////////////////////////
    //��ȡ����ҳ����Ϣ
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //�¼���
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //�¹�����===����ԭ��
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //���յص�
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //�¹�����
    tLLClaimSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��

    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //������=�ⰸ��
    tLLRegisterSchema.setRgtantName(request.getParameter("RptorName")); //����������
    tLLRegisterSchema.setRgtantPhone(request.getParameter("RptorPhone")); //�����˵绰
    tLLRegisterSchema.setRgtantAddress(request.getParameter("RptorAddress")); //������ͨѶ��ַ
    tLLRegisterSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ
    tLLRegisterSchema.setAccidentSite(request.getParameter("AccidentSite")); //���յص�
    tLLRegisterSchema.setAccidentDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLRegisterSchema.setAccidentReason(request.getParameter("occurReason")); //����ԭ��
    tLLRegisterSchema.setAssigneeType(request.getParameter("AssigneeType")); //����������
    tLLRegisterSchema.setAssigneeCode(request.getParameter("AssigneeCode")); //�����˴���
    tLLRegisterSchema.setAssigneeName(request.getParameter("AssigneeName")); //����������
    tLLRegisterSchema.setAssigneeSex(request.getParameter("AssigneeSex")); //�������Ա�
    tLLRegisterSchema.setAssigneePhone(request.getParameter("AssigneePhone")); //�����˵绰
    tLLRegisterSchema.setAssigneeAddr(request.getParameter("AssigneeAddr")); //�����˵�ַ
    tLLRegisterSchema.setAssigneeZip(request.getParameter("AssigneeZip")); //�������ʱ�
    tLLRegisterSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLRegisterSchema.setRgtClass(tRgtClass); //�����ŵ�
//��������������Ϣ
    tLLRegisterSchema.setGrpContNo(request.getParameter("GrpContNo")); //���屣����
    tLLRegisterSchema.setRiskCode(request.getParameter("Polno")); //�������ֺ�
    tLLRegisterSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //����ͻ���
    tLLRegisterSchema.setGrpName(request.getParameter("GrpName")); //��λ����
    tLLRegisterSchema.setPeoples2(request.getParameter("Peoples")); //Ͷ��������
    tLLRegisterSchema.setGrpStandpay(request.getParameter("Grpstandpay")); //����Ԥ��ֵ
    tLLRegisterSchema.setRptFlag("1"); //������־  1-�ѱ���
    tLLRegisterSchema.setAcceptedDate(request.getParameter("AcceptedDate")); //��������
    
    
    //*********************************jinsh20070405�ӱ�����
    tLLReportSchema.setRptNo(request.getParameter("RptNo")); //������=�ⰸ��
    //loggerDebug("LLClaimRegisterSave","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+request.getParameter("RptNo"));    
 	tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //����ͻ���
  	tLLReportSchema.setGrpName(request.getParameter("GrpName")); //��λ����
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //���屣����
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //����ԭ��
    tLLReportSchema.setRiskCode(request.getParameter("Polno")); //���ֱ���
    tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //��������
    tLLReportSchema.setRptorName(request.getParameter("RptorName")); //����������
    tLLReportSchema.setRptorPhone(request.getParameter("RptorPhone")); //�����˵绰
    tLLReportSchema.setRptorAddress(request.getParameter("RptorAddress")); //������ͨѶ��ַ
    tLLReportSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ 
    tLLReportSchema.setAccidentSite(request.getParameter("AccidentSite")); //���յص�
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //����ԭ��
    tLLReportSchema.setRgtState(request.getParameter("RgtState"));
    //tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //Ͷ��������
    //�����˴���
    //����������
    
    
    

    tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo")); //�ְ���=������=�ⰸ��
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //�ͻ��� 
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //��λ����
    tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate")); //��������
    tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate")); //��������
    tLLSubReportSchema.setCureDesc(request.getParameter("cureDesc")); //�������
    //��֤��ȫ��ʾ
    tLLSubReportSchema.setAccResult2(request.getParameter("AccResult2")); //���ս������
    //���ս��
    tLLSubReportSchema.setHospitalCode(request.getParameter("hospital")); //����ҽԺ����
    //����ҽԺ
    tLLSubReportSchema.setAccidentDetail(request.getParameter("accidentDetail")); //����ϸ�ڱ���
    //����ϸ��
    tLLSubReportSchema.setAccDesc(request.getParameter("AccDesc")); //�¹����� 
    tLLSubReportSchema.setRemark(request.getParameter("Remark")); //��ע 
    tLLSubReportSchema.setAccidentType(request.getParameter("occurReason"));

    
    
    
    
		for (int i = 0; i < tClaimType.length; i++)
			   {
			        tClaimType[i] = toccurReason + tClaimType[i];
			        loggerDebug("LLClaimRegisterSave","tClaimType[i]:"+tClaimType[i]);
			        tLLReportReasonSchema = new LLReportReasonSchema();
			        tLLReportReasonSchema.setRpNo(request.getParameter("RptNo")); //������=�ⰸ��			      
			        tLLReportReasonSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
			        tLLReportReasonSchema.setReasonCode(tClaimType[i]); //��������
			        tLLReportReasonSet.add(tLLReportReasonSchema);
			        
			        
			        tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
				      tLLAppClaimReasonSchema.setCaseNo(request.getParameter("RptNo")); //������=�ⰸ��
				      tLLAppClaimReasonSchema.setRgtNo(request.getParameter("RptNo")); //������=�ⰸ��
				      tLLAppClaimReasonSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
				      tLLAppClaimReasonSchema.setReasonCode(tClaimType[i]); //��������
				      tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
			   }
    //*******************************************jinsh20070405
    
    
    
    
    

    tLLCaseSchema.setCaseNo(request.getParameter("RptNo")); //�ְ���=������=�ⰸ��
    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
    tLLCaseSchema.setCustomerName(request.getParameter("customerName")); //��������
    String tStringAge = request.getParameter("customerAge");
    loggerDebug("LLClaimRegisterSave","tStringAge======="+tStringAge);
    try{
    	tLLCaseSchema.setCustomerAge(request.getParameter("customerAge")); //����������
    }catch(Exception ex){
    	ex.printStackTrace();
    }
    tLLCaseSchema.setCustomerSex(request.getParameter("customerSex")); //�������Ա�
    
    //tongmeng 2009-01-05 add
    //�ְ���RGTType ���11
    tLLCaseSchema.setRgtType("11");
    tLLCaseSchema.setAccDate(request.getParameter("AccidentDate")); //��������
    tLLCaseSchema.setMedAccDate(request.getParameter("AccidentDate")); //ҽ�Ƴ�������
    
    tLLCaseSchema.setAccidentDetail(request.getParameter("accidentDetail")); //����ϸ��
    tLLCaseSchema.setCureDesc(request.getParameter("cureDesc")); //�������
    tLLCaseSchema.setAccResult1(request.getParameter("AccResult2")); //ICD10������
    tLLCaseSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10�Ӵ���
    tLLCaseSchema.setAccdentDesc(request.getParameter("AccDesc")); //�¹�����
    tLLCaseSchema.setHospitalCode(request.getParameter("hospital")); //����ҽԺ����
    tLLCaseSchema.setHospitalName(request.getParameter("TreatAreaName")); //����ҽԺ����
    tLLCaseSchema.setHospitalName(request.getParameter("TreatAreaName")); //����ҽԺ����
    tLLCaseSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLCaseSchema.setStandpay(request.getParameter("standpay")); //����Ԥ��ֵ
    tLLCaseSchema.setAffixConclusion(request.getParameter("IsAllReady"));


    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("Remark", request.getParameter("Remark"));
    mTransferData.setNameAndValue("hospital", request.getParameter("hospital"));

    //���������
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "20");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("GrpCustomerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("GrpName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("AddPersonFlag", tAddPersonFlag);

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());
    
    if(request.getParameter("GrpCustomerNo")!=null && !request.getParameter("GrpCustomerNo").equals("")){
    mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    }else{
    mTransferData.setNameAndValue("GrpContNo","");
    }
    
    String tsql="select rgtstate from llreport where rptno='"+mRptNo+"' ";
    
    //String tRgtState=tExeSQL.getOneValue(tsql);
    //loggerDebug("LLClaimRegisterSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",request.getParameter("RgtState"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLAppClaimReasonSet);
    
    //******************************************************jinsh20070405
    tVData.add(tLLReportSchema);
    tVData.add(tLLSubReportSchema);
    tVData.add(tLLReportReasonSchema);
    tVData.add(tLLReportReasonSet);
    //******************************************************jinsh20070405


    //��һ�α����ύʱ���ύ�����������棬������ֱ���ύ��ҵ����,���ύ������ʱwFlag=9899999999
    if (transact.equals("insert||first"))
    {
        wFlag = "9899999999";
        try
        {
//�ֺ��ж�
    TransferData  mTransferData2 =new TransferData();
    mTransferData2.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    mTransferData2.setNameAndValue("InsuredNo",request.getParameter("customerNo"));
    mTransferData2.setNameAndValue("AccDate",request.getParameter("AccidentDate"));
    //String[] tResult=new String[3];
    //tResult=PubFun1.CheckFeildClaim(mTransferData2);
    //if ("1".equals(tResult[0]))
    //{
    //   Content = "����ʧ�ܣ�ԭ����:" + tResult[2];
    //   FlagStr = "Fail";
    //}else{ //----------------�ֺ��ж�
            loggerDebug("LLClaimRegisterSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " +tClaimWorkFlowUI.mErrors.getError(0).errorMessage; 
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
                //tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLClaimRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //ȡ�ô����Ľڵ����,Ŀǰ�������ø��ݴ��������ѯ�����Ĺ������ڵ㣺
                //**********************************************************************************
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLClaimRegisterSave","tRptNo="+tRgtNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000009015' "
                           + " and processid = '0000000009'"
                           + " and  missionprop1 = '" +tRgtNo+"'";
                loggerDebug("LLClaimRegisterSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "��ѯ�������ڵ�ʧ��!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLClaimRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    String ClmNo = tLWMissionSchema.getMissionProp1();
                    loggerDebug("LLClaimRegisterSave","---�����½��������ڵ�="+MissionID);
%>
<script language="javascript">
    parent.fraInterface.fm.all("MissionID").value="<%=MissionID%>";
    parent.fraInterface.fm.all("SubMissionID").value="<%=SubMissionID%>";
    parent.fraInterface.fm.all("ClmNo").value="<%=ClmNo%>";
</script>
<%
                    Content = "�����ύ�ɹ�";
                    FlagStr = "Succ";
                }
                //**********************************************************************************End
            }
        //}//----------------�ֺ��ж�
     }catch(Exception ex)
        {
            Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
    }else if(transact.equals("DELETE")){
        try
        {
            //�����ύ
            loggerDebug("LLClaimRegisterSave","---tLLClaimRegisterBL submitData and transact="+transact);
//            if (!tLLClaimRegisterBL.submitData(tVData,transact))
//            {
//                Content = " tLLClaimRegisterBL��������ʧ�ܣ�ԭ����: " + tLLClaimRegisterBL.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            if(!tBusinessDelegate2.submitData(tVData,transact,busiName2))
			{    
			    if(tBusinessDelegate2.getCErrors()!=null&&tBusinessDelegate2.getCErrors().getErrorCount()>0)
			    { 
					Content = "tLLClaimRegisterBL��������ʧ�ܣ�ԭ����:" + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "tLLClaimRegisterBL��������ʧ��";
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
            Content = "�����ύtLLClaimRegisterBLʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
    }
    else
    {
        if(transact.equals("INSERT"))
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
            loggerDebug("LLClaimRegisterSave","---LLClaimRegisterUI submitData and transact="+transact);
//            if (!tLLClaimRegisterUI.submitData(tVData,transact)){
//                Content = " LLClaimRegisterUI��������ʧ�ܣ�ԭ����: " + tLLClaimRegisterUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
            if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "LLClaimRegisterUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "LLClaimRegisterUI��������ʧ��";
					FlagStr = "Fail";				
				}
			}
            else{
                tVData.clear();
                Content = " �����ύ�ɹ���";
                FlagStr = "Succ";
                tVData.clear();
                //tVData = tLLClaimRegisterUI.getResult();
                  tVData = tBusinessDelegate1.getResult();
                loggerDebug("LLClaimRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //ȡ�ô����Ľڵ����,Ŀǰ�������ø��ݴ��������ѯ�����Ĺ������ڵ㣺
                //**********************************************************************************
                if(!"UPDATE".equals(transact)&&!"1".equals(tAddPersonFlag)){
	                TransferData tTransferData = new TransferData();
	                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
	                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
	                loggerDebug("LLClaimRegisterSave","tRgtNo="+tRgtNo);
	                LWMissionDB ttLWMissionDB = new LWMissionDB();
	                LWMissionSchema ttLWMissionSchema = new LWMissionSchema();
	                String sql = "select * from LWMission where"
	                           + " activityid = '0000009015' "
	                           + " and processid = '0000000009'"
	                           + " and  missionprop1 = '" +tRgtNo+"'";
	                loggerDebug("LLClaimRegisterSave","Start query mission:" + sql);
	                LWMissionSet tLWMissionSet = ttLWMissionDB.executeQuery(sql);
	                if (tLWMissionSet == null && tLWMissionSet.size() == 0){
	                    Content = "��ѯ�������ڵ�ʧ��!" ;
	                    FlagStr = "Fail";
	                }else{
		                    loggerDebug("LLClaimRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
		                    ttLWMissionSchema = tLWMissionSet.get(1);
		                    String MissionID = ttLWMissionSchema.getMissionID();
		                    String SubMissionID = ttLWMissionSchema.getSubMissionID();
		                    String ClmNo = ttLWMissionSchema.getMissionProp1();
		                    loggerDebug("LLClaimRegisterSave","---�����½��������ڵ�="+MissionID);
		                %>
		                <script language="javascript">
		                    parent.fraInterface.fm.all("MissionID").value="<%=MissionID%>";
		                    parent.fraInterface.fm.all("SubMissionID").value="<%=SubMissionID%>";
		                    parent.fraInterface.fm.all("ClmNo").value="<%=ClmNo%>";
		                </script>
		                <%
		            }
                }
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
