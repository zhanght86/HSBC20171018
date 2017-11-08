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
loggerDebug("LLClaimRegisterSave","######################LLClaimRegisterSave.jsp start#############################");

//�������
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //�¼���
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //������
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //��������
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //�������ͱ�
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //�������ͼ���


//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();



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
    
    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //������=�ⰸ��
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
    
    String ActivityID = new ExeSQL().getOneValue("select activityid from lwactivity where functionid='10030003'");

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
    mTransferData.setNameAndValue("ScanFlag", "0");//��ɨ
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("RptNo", tLLRegisterSchema.getRgtNo());

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",ActivityID);
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());
    mTransferData.setNameAndValue("BusiType", "1003");
  
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLAppClaimReasonSet);

        try
        {
            //�����ύ
            loggerDebug("LLClaimRegisterSave","---LLClaimRegisterBLF submitData and transact="+transact);

            if(transact.equals("insert||customer")||transact.equals("insert||first")){
            	if(!tBusinessDelegate.submitData(tVData,"create",busiName)){
            		if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    		        { 
    						   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
    						   FlagStr = "Fail";
    				}
    				else
    				{
    						   Content = "����ʧ��";
    						   FlagStr = "Fail";				
    				}
            	}else{
            		 Content = " �����ύ�ɹ���";
                     FlagStr = "Succ";
     		    	//�ӽ������ȡ��ǰ̨��Ҫ����
                     tVData.clear();
                     tVData = tBusinessDelegate.getResult();               
                     loggerDebug("LLClaimRegisterSave","tVData="+tVData);
                     //**********************************************************************************Beg
                     //ȡ�ô����Ľڵ����,Ŀǰ�������ø��ݴ��������ѯ�����Ĺ������ڵ㣺
                     //**********************************************************************************
                     TransferData tTransferData = new TransferData();
                     tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                     String tRptNo = (String) tTransferData.getValueByName("RptNo");
                     loggerDebug("LLClaimRegisterSave","tRptNo="+tRptNo);
                     String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                     loggerDebug("LLClaimRegisterSave","tRgtNo="+tRgtNo);
                     
                     String sql = "select MissionID,SubMissionID,MissionProp1,activityid from LWMission where"
                                + " activityid in(select activityid from lwactivity where functionid='10030003') "
                                + " and  missionprop1 = '" +tRgtNo+"'";
                     loggerDebug("LLClaimRegisterSave","Start query mission:" + sql);
                  
                     TransferData sqlTransferData = new TransferData();
   				  VData sqlVData = new VData();
   			    sqlTransferData.setNameAndValue("SQL",sql);
   			    sqlVData.add(sqlTransferData);
   			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
   			  	  {    
   			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
   			  	       { 
   			  				loggerDebug("LLClaimRegisterSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
   			  		   }
   			  		   else
   			  		   {
   			  			 	loggerDebug("LLClaimRegisterSave","��ѯʧ��");				
   			  		   }
   			  	  }
   			  	  else
   			  	  {			  	  
   			  			SSRS tSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
   		                if (tSSRS == null || tSSRS.getMaxRow()<1)
   		                {
   		                    Content = "��ѯ�������ڵ�ʧ��!" ;
   		                    FlagStr = "Fail";
   		                }
   		                else
   		                {
   		                    loggerDebug("LLClaimRegisterSave","tSSRS.getMaxRow()=" + tSSRS.getMaxRow());
   		                    String MissionID = tSSRS.GetText(1,1);
   		                    String SubMissionID = tSSRS.GetText(1,2);
   		                    String RptNo = tSSRS.GetText(1,3);
   		                    loggerDebug("LLClaimRegisterSave","��������"+RptNo);
   		                    loggerDebug("LLClaimRegisterSave","---�����½��������ڵ�="+MissionID);
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
   			  	  }
     			  	  
            	}
            }else if(transact.equals("UPDATE")){
            	 String busiName1="LLClaimRegisterBLF";
     			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
     			if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
     		   {    
     		        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
     		        { 
     						   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getFirstError();
     						   FlagStr = "Fail";
     				}
     				else
     				{
     						   Content = "����ʧ��";
     						   FlagStr = "Fail";				
     				}
     		   }else{
     			  Content = " �����ύ�ɹ���";
                  FlagStr = "Succ";
     		   }
            }else if(!"insert||first".equals(transact))
            {
                Content = "����������" ;
                FlagStr = "Fail";
                return;
            }
		    
        }
        catch(Exception ex)
        {
            Content = "�����ύLLClaimRegisterUIʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
}   
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
