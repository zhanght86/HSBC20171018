<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLGrpMedicalFeeInpSave.jsp
//�����ܣ����װ�������/סԺ��Ϣ����
//�������ڣ�2005-11-7
//������  ��pd
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//׼��ͨ�ò���
CErrors tError = null;
String FlagStr = "FAIL";
String Content = "";
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//ҳ����Ч��
if(tG == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLGrpMedicalFeeInpSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    String tOperate = request.getParameter("hideOperate");

    //׼������������Ϣ
    LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
    LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();

    String tChk[] = request.getParameterValues("InpMedFeeInHosInpGridChk");
    String tCustomerNo[] = request.getParameterValues("MedFeeInHosInpGrid1"); //�ͻ���
    String tFeeType[] = request.getParameterValues("MedFeeInHosInpGrid2"); //�˵�����
    String tHospitalCode[] = request.getParameterValues("MedFeeInHosInpGrid4"); //ҽԺ����
    String tHospitalName[] = request.getParameterValues("MedFeeInHosInpGrid3"); //ҽԺ����
    String tHospitalGrade[] = request.getParameterValues("MedFeeInHosInpGrid5"); //ҽԺ�ȼ�
    String tMainFeeNo[] = request.getParameterValues("MedFeeInHosInpGrid6"); //�ʵ�����
    String tDiseaseName[] = request.getParameterValues("MedFeeInHosInpGrid7"); //��������
    String tDiseaseCode[] = request.getParameterValues("MedFeeInHosInpGrid8"); //��������
    String tClinicStartDate[] = request.getParameterValues("MedFeeInHosInpGrid9"); //��ʼ����
    String tClinicEndDate[] = request.getParameterValues("MedFeeInHosInpGrid10"); //��������
    String tClinicDayCount1[] = request.getParameterValues("MedFeeInHosInpGrid11"); //����
    String tClinicMedFeeType[] = request.getParameterValues("MedFeeInHosInpGrid13"); //��������
    String tClinicMedFeeTypeName[] = request.getParameterValues("MedFeeInHosInpGrid12"); //��������name
    String tClinicMedFeeSum[] = request.getParameterValues("MedFeeInHosInpGrid14"); //ԭʼ����
    String tRefuseAmnt[] = request.getParameterValues("MedFeeInHosInpGrid15"); //�۳�����
    String tAdjRemark[] = request.getParameterValues("MedFeeInHosInpGrid21"); //�۳���ϸ
    String tAdjReason[] = request.getParameterValues("MedFeeInHosInpGrid17"); //�۳�����
    String tAdjSum[] = request.getParameterValues("MedFeeInHosInpGrid18"); //�������
    String tSecurityAmnt[] = request.getParameterValues("MedFeeInHosInpGrid19"); //�籣�⸶����
    String tHospLineAmnt[] = request.getParameterValues("MedFeeInHosInpGrid20"); //סԺ����
    
    String tFeeDetailNo[] = request.getParameterValues("MedFeeInHosInpGrid22"); //�˵�������ϸ

    //׼����̨����
    loggerDebug("LLGrpMedicalFeeInpSave","tChk_length:--->"+tChk.length);
    for(int index=0;index<tChk.length;index++)
    {
      if(tChk[index].equals("1"))
      {
    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    tLLFeeMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLFeeMainSchema.setCaseNo(request.getParameter("RptNo")); //�ְ���
    tLLFeeMainSchema.setMainFeeNo(tMainFeeNo[index]); //�ʵ�����
    tLLFeeMainSchema.setCustomerNo(tCustomerNo[index]); //�ͻ���
    tLLFeeMainSchema.setHospitalCode(tHospitalCode[index]); //ҽԺ����
    tLLFeeMainSchema.setHospitalName(tHospitalName[index]); //ҽԺ����
    tLLFeeMainSchema.setHospitalGrade(tHospitalGrade[index]); //ҽԺ�ȼ�
    tLLFeeMainSchema.setFeeType(tFeeType[index]);    //A ���� BסԺ
    tLLFeeMainSet.add(tLLFeeMainSchema);

    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
    tLLCaseReceiptSchema.setClmNo(request.getParameter("RptNo"));
    tLLCaseReceiptSchema.setCaseNo(request.getParameter("RptNo"));
    tLLCaseReceiptSchema.setMainFeeNo(tMainFeeNo[index]);
    tLLCaseReceiptSchema.setRgtNo(request.getParameter("RptNo"));
    tLLCaseReceiptSchema.setFeeItemType(tFeeType[index]);    //A ���� BסԺ
    tLLCaseReceiptSchema.setDiseaseName(tDiseaseName[index]); //��������
    tLLCaseReceiptSchema.setDiseaseCode(tDiseaseCode[index]); //��������
    tLLCaseReceiptSchema.setFeeItemCode(tClinicMedFeeType[index]);   //��������
    tLLCaseReceiptSchema.setFeeItemName(tClinicMedFeeTypeName[index]);  //��������name
    tLLCaseReceiptSchema.setFee(tClinicMedFeeSum[index]);  //ԭʼ����

    tLLCaseReceiptSchema.setCustomerNo(tCustomerNo[index]); //�ͻ���
    tLLCaseReceiptSchema.setSelfAmnt(tRefuseAmnt[index]);  //�۳�����
    tLLCaseReceiptSchema.setAdjRemark(tAdjRemark[index]);  //�۳�ԭ��
    tLLCaseReceiptSchema.setAdjReason(tAdjReason[index]);  //�۳�����
    tLLCaseReceiptSchema.setAdjSum(tAdjSum[index]);  //�������
    tLLCaseReceiptSchema.setSecurityAmnt(tSecurityAmnt[index]);  //�籣�⸶����

    tLLCaseReceiptSchema.setStartDate(tClinicStartDate[index]);   //��ʼ����
    tLLCaseReceiptSchema.setEndDate(tClinicEndDate[index]);   //��������
    tLLCaseReceiptSchema.setDayCount(tClinicDayCount1[index]);   //����
    tLLCaseReceiptSchema.setDealFlag("1");   //��ʼ�����Ƿ����ڳ�������,0��1����
    tLLCaseReceiptSchema.setHospLineAmnt(tHospLineAmnt[index]);   //סԺ����
    
    tLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo[index]);   //�˵�������ϸ
    tLLCaseReceiptSet.add(tLLCaseReceiptSchema);
     }
  }
    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLFeeMainSet );
        tVData.add( tLLCaseReceiptSet );

//        LLGrpMedicalFeeInpBL tLLGrpMedicalFeeInpBL = new LLGrpMedicalFeeInpBL();
//        if (tLLGrpMedicalFeeInpBL.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLGrpMedicalFeeInpBL.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��Ϣ�ύ���棬ԭ����: " + tLLGrpMedicalFeeInpBL.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
String busiName="grpLLGrpMedicalFeeInpBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLGrpMedicalFeeInpSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content =  Content + "��Ϣ�ύ���棬ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "��Ϣ�ύ����";
		   FlagStr = "Fail";				
		} 
}

        else
        {
            Content = " ����ɹ�! ";
            FlagStr = "SUCC";
        }
    }
    catch(Exception ex)
    {
        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
