<%
//**************************************************************************************************
//Name��LLGrpRegisterSave.jsp
//Function������������Ϣ�ύ
//Author��pd
//Date: 2005-11-8
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.workflow.claimgrp.*" %>
<%
loggerDebug("LLGrpRegisterSave","######################LLGrpRegisterSave.jsp start#############################");

//�������
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //�¼���
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //������
//******************************************************************jinsh20070403
LLReportSchema tLLReportSchema = new LLReportSchema(); //������
LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema(); //�ְ���
LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //�������ͱ�
LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //�������ͼ���
//*********************************************************************jinsh20070403
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //��������
//LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //�ⰸ��
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //�������ͱ�
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //�������ͼ���

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
VData tVData = new VData();
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new LLGrpClaimRegisterBL();
//LLClaimRegisterUI tLLClaimRegisterUI = new LLClaimRegisterUI();
//LLClaimRegisterBL tLLClaimRegisterBL = new LLClaimRegisterBL();
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
String busiName1="grpLLGrpClaimRegisterBL";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
String busiName2="grpLLClaimRegisterUI";
BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
//String busiName3="grpLLClaimRegisterBL";
//BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();



String transact = request.getParameter("fmtransact");

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLGrpRegisterSave","ҳ��ʧЧ,�����µ�½");
}
else //ҳ����Ч
{

    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //��ȡ��������insert||first��insert||customer��update��
  
    
    //���ո���
    String tRgtClass = request.getParameter("RgtClass");
    if (tRgtClass == null || tRgtClass == "")
    {
        tRgtClass = "2";
    }
    loggerDebug("LLGrpRegisterSave","tRgtClass:"+tRgtClass);
    //��������
    String tClaimType[] = request.getParameterValues("claimType");
    //����ԭ��
    String toccurReason = request.getParameter("occurReason");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");



    //*********************************jinsh20070403�ӱ�����
    tLLReportSchema.setRptNo(request.getParameter("RptNo")); //������=�ⰸ��
    //loggerDebug("LLGrpRegisterSave","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+request.getParameter("RptNo"));    
 	tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //����ͻ���
  	tLLReportSchema.setGrpName(request.getParameter("GrpName")); //��λ����
    tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //���屣����
    tLLReportSchema.setAccidentReason(request.getParameter("occurReason")); //����ԭ��
    tLLReportSchema.setRiskCode(request.getParameter("Polno")); //���ֱ���
    tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //��������
    tLLReportSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ 

    tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo")); //�ְ���=������=�ⰸ��
    tLLSubReportSchema.setCustomerNo(request.getParameter("customerNo")); //�ͻ��� 
    tLLSubReportSchema.setCustomerName(request.getParameter("customerName")); //��λ����
    tLLSubReportSchema.setAccDate(request.getParameter("AccidentDate")); //��������
    tLLSubReportSchema.setIDNo(request.getParameter("IDNo"));
    tLLSubReportSchema.setSex(request.getParameter("customerSex"));
    tLLSubReportSchema.setIDType(request.getParameter("IDType"));
    tLLSubReportSchema.setMedAccDate(request.getParameter("AccidentDate"));
    tLLSubReportSchema.setAccidentType(request.getParameter("occurReason"));
		for (int i = 0; i < tClaimType.length; i++)
			   {
			        tClaimType[i] = toccurReason + tClaimType[i];
			        loggerDebug("LLGrpRegisterSave","tClaimType[i]:"+tClaimType[i]);
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
    //*******************************************jinsh20070403
    
    
    
    //��ȡ����ҳ����Ϣ
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //�¼���
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //������=�ⰸ��
    tLLCaseSchema.setCaseNo(request.getParameter("RptNo")); //�ְ���=������=�ⰸ��
//    tLLClaimSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��

    tLLRegisterSchema.setAccidentDate(request.getParameter("AccidentDate")); //�����¹ʷ�������
    tLLRegisterSchema.setAccidentReason(request.getParameter("occurReason")); //����ԭ��
    tLLRegisterSchema.setGrpContNo(request.getParameter("GrpContNo")); //���屣����
    tLLRegisterSchema.setRiskCode(request.getParameter("Polno")); //�������ֺ�
    tLLRegisterSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //����ͻ���
    tLLRegisterSchema.setGrpName(request.getParameter("GrpName")); //��λ����
    tLLRegisterSchema.setPeoples2(request.getParameter("Peoples")); //Ͷ��������
//    tLLRegisterSchema.setAppPeoples(request.getParameter("PeopleNo")); //��������
    tLLRegisterSchema.setRgtClass(tRgtClass); //(1 ����) ( 2 �ŵ�)
    tLLRegisterSchema.setRelation(request.getParameter("Relation")); //���������¹��˹�ϵ
    tLLRegisterSchema.setAssigneeType(request.getParameter("AssigneeType")); //����������
    tLLRegisterSchema.setAssigneeCode(request.getParameter("AssigneeCode")); //�����˴���
    tLLRegisterSchema.setAssigneeName(request.getParameter("AssigneeName")); //����������
    tLLRegisterSchema.setAssigneeSex(request.getParameter("AssigneeSex")); //�������Ա�
    tLLRegisterSchema.setAssigneePhone(request.getParameter("AssigneePhone")); //�����˵绰
    tLLRegisterSchema.setAssigneeAddr(request.getParameter("AssigneeAddr")); //�����˵�ַ
    tLLRegisterSchema.setAssigneeZip(request.getParameter("AssigneeZip")); //�������ʱ�
    tLLRegisterSchema.setRgtantName(request.getParameter("RptorName")); //����������
    tLLRegisterSchema.setRgtantPhone(request.getParameter("RptorPhone")); //�����˵绰
    tLLRegisterSchema.setRgtantAddress(request.getParameter("RptorAddress")); //������ͨѶ��ַ
    tLLRegisterSchema.setAcceptedDate(request.getParameter("AcceptedDate")); //��������

    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //�����˱���
    tLLCaseSchema.setCustomerName(request.getParameter("customerName")); //����������
    tLLCaseSchema.setCustomerAge(request.getParameter("customerAge")); //����������
    tLLCaseSchema.setCustomerSex(request.getParameter("customerSex")); //�������Ա�
    tLLCaseSchema.setAccDate(request.getParameter("AccidentDate")); //��������
    tLLCaseSchema.setMedAccDate(request.getParameter("AccidentDate")); //ҽ�Ƴ�������
    tLLCaseSchema.setIDType(request.getParameter("IDType")); //������֤������
    tLLCaseSchema.setIDNo(request.getParameter("IDNo")); //������֤������


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

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());

    if(request.getParameter("GrpCustomerNo")!=null && !request.getParameter("GrpCustomerNo").equals(""))
    {
    mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    }else{
    mTransferData.setNameAndValue("GrpContNo","");
    }

    String tsql="select rgtstate from llreport where rptno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpRegisterSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState",tRgtState);

    //׼���������� VData
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLAppClaimReasonSet);
    //******************************************************jinsh20070403
    tVData.add(tLLReportSchema);
    tVData.add(tLLSubReportSchema);
    tVData.add(tLLReportReasonSchema);
    tVData.add(tLLReportReasonSet);
    //******************************************************jinsh20070403

    //��һ�α����ύ
    loggerDebug("LLGrpRegisterSave","transact:::::::::::::::"+transact);
    if (transact.equals("insert||first"))
    {
      /*      transact = "INSERT";
        try
        {
            loggerDebug("LLGrpRegisterSave","---first LLGrpClaimRegisterBL submitData and transact="+transact);
//            if(!tLLGrpClaimRegisterBL.submitData(tVData,transact))
//            {
//                Content = " �ύ������LLGrpClaimRegisterBLʧ�ܣ�ԭ����: " + tLLGrpClaimRegisterBL.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
		if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
		{    
		    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "�ύ������LLGrpClaimRegisterBLʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�ύ������LLGrpClaimRegisterBLʧ��";
				FlagStr = "Fail";				
			}
		}

            else
            {
                tVData.clear();
                Content = " �����ύ�ɹ���";
                FlagStr = "Succ";
                tVData.clear();
               // tVData = tLLGrpClaimRegisterBL.getResult();
                tVData =  tBusinessDelegate1.getResult();
      %>
     
      <%           
             }
        }
        catch(Exception ex)
        {
            Content = "�����ύLLGrpClaimRegisterBLʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }   
       */
       
       wFlag = "9899999999";
        try
        {

    TransferData  mTransferData2 =new TransferData();
    mTransferData2.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
    mTransferData2.setNameAndValue("InsuredNo",request.getParameter("customerNo"));
    mTransferData2.setNameAndValue("AccDate",request.getParameter("AccidentDate"));
    String[] tResult=new String[3];
    tResult=PubFun1.CheckFeildClaim(mTransferData2);
    if ("1".equals(tResult[0]))
    {
       Content = "����ʧ�ܣ�ԭ����:" + tResult[2];
       FlagStr = "Fail";
    }else{
            loggerDebug("LLGrpRegisterSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
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
                ClaimWorkFlowUI tClaimWorkFlowUI=new ClaimWorkFlowUI();
                //�ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                loggerDebug("LLGrpRegisterSave","tVData="+tVData);
                //**********************************************************************************Beg
                //ȡ�ô����Ľڵ����,Ŀǰ�������ø��ݴ��������ѯ�����Ĺ������ڵ㣺
                //**********************************************************************************
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLGrpRegisterSave","tRgtNo="+tRgtNo);
                LWMissionDB tLWMissionDB = new LWMissionDB();
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                String sql = "select * from LWMission where"
                           + " activityid = '0000005015' "
                           + " and processid = '0000000005'"
                           + " and  missionprop1 = '" +tRgtNo+"'";
                loggerDebug("LLGrpRegisterSave","Start query mission:" + sql);
                LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
                if (tLWMissionSet == null && tLWMissionSet.size() == 0)
                {
                    Content = "��ѯ�������ڵ�ʧ��!" ;
                    FlagStr = "Fail";
                }
                else
                {
                    loggerDebug("LLGrpRegisterSave","tLWMissionSet.size()=" + tLWMissionSet.size());
                    tLWMissionSchema = tLWMissionSet.get(1);
                    String MissionID = tLWMissionSchema.getMissionID();
                    String SubMissionID = tLWMissionSchema.getSubMissionID();
                    String ClmNo = tLWMissionSchema.getMissionProp1();
                    loggerDebug("LLGrpRegisterSave","---�����½��������ڵ�="+MissionID);
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
        }
     }catch(Exception ex)
        {
            Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
       
    }
    else if(transact.equals("DELETE"))
    {
         //�������������˻��స����ɾ������
         loggerDebug("LLGrpRegisterSave","������ɾ�����Կ�ʼ������");
         transact = "ACCDELETE";
         try
         {
              //�����ύ
//              if (!tLLGrpClaimRegisterBL.submitData(tVData,transact))
//              {
//                  Content = " LLGrpClaimRegisterBL��������ʧ�ܣ�ԭ����: " + tLLGrpClaimRegisterBL.mErrors.getError(0).errorMessage;
//                  FlagStr = "Fail";
//              }
        if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
		{    
		    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLGrpClaimRegisterBL��������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLGrpClaimRegisterBL��������ʧ��";
				FlagStr = "Fail";				
			}
		}
              else
              {
                  tVData.clear();
                  Content = " �����ύ�ɹ���";
                  FlagStr = "Succ";
                  tVData.clear();
                  //tVData = tLLGrpClaimRegisterBL.getResult();
                  tVData = tBusinessDelegate1.getResult();
              }
         }
         catch(Exception ex)
         {
              Content = "�����ύLLGrpClaimRegisterBLʧ�ܣ�ԭ����:" + ex.toString();
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
            loggerDebug("LLGrpRegisterSave","---customer LLGrpClaimRegisterBL submitData and transact="+transact);
            //if (!tLLGrpClaimRegisterBL.submitData(tVData,transact))
//            if (!tLLClaimRegisterUI.submitData(tVData,transact))
//            {
//                Content = " LLClaimRegisterUI��������ʧ�ܣ�ԭ����: " + tLLClaimRegisterUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
        if(!tBusinessDelegate2.submitData(tVData,transact,busiName2))
		{    
		    if(tBusinessDelegate2.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLClaimRegisterUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLClaimRegisterUI��������ʧ��";
				FlagStr = "Fail";				
			}
		}
            else
            {
                tVData.clear();
                Content = " �����ύ�ɹ���";
                FlagStr = "Succ";
                
                //tVData = tLLGrpClaimRegisterBL.getResult();
               // tVData = tLLClaimRegisterUI.getResult();
                  tVData = tBusinessDelegate2.getResult();
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                loggerDebug("LLGrpRegisterSave","tRptNo="+tRgtNo);
                %>
                <script language="javascript">
                    parent.fraInterface.fm.all("ClmNo").value="<%=tRgtNo%>";
                    parent.fraInterface.fm.all("RptNo").value="<%=tRgtNo%>";
                </script>
                <%                
            }
        }
        catch(Exception ex)
        {
            Content = "�����ύLLClaimRegisterUIʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
    }
}
 /*
  LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
  mLLRegisterSchema.setSchema((LLRegisterSchema)tVData.getObjectByObjectName("LLRegisterSchema", 0));
  String tRgtNo = mLLRegisterSchema.getRgtNo();
  loggerDebug("LLGrpRegisterSave","tRgtNo:"+tRgtNo);
 */ 
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
	


</script>
</html>
