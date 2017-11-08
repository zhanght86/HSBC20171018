<%
//**************************************************************************************************
//Name��LLClaimRegisterConclusionSave.jsp
//Function������������Ϣ�ύ
//Author��zhoulei
//Date: 2005-6-15 16:28
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
<%@page import="com.sinosoft.service.*" %>

<%

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//LLClaimRegisterConclusionUI tLLClaimRegisterConclusionUI = new LLClaimRegisterConclusionUI();
String busiName="grpLLClaimRegisterConclusionUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimRegisterConclusionSave","ҳ��ʧЧ,�����µ�½");    
}
else 
{
  String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //��ȡ������
    String RgtFlag="0"; //�������������������ӳ�����������0-�ӳ�������1����������
    if(request.getParameter("RgtFlag")!=null) 
    { 
       RgtFlag=request.getParameter("RgtFlag");
    }
    loggerDebug("LLClaimRegisterConclusionSave","RgtFlag:"+RgtFlag);    
    String transact = request.getParameter("fmtransact");
    String tRptNo = request.getParameter("RptNo");
    String tAccNo = request.getParameter("AccNo");
    String GrpCustomerNo = request.getParameter("GrpCustomerNo");
    String DeferRgtReason=StrTool.unicodeToGBK(request.getParameter("DeferRgtReason"));   //��ֹ�������� 
    String DeferRgtRemark=StrTool.unicodeToGBK(request.getParameter("DeferRgtRemark"));   //��ֹ�������� 
    
    if(GrpCustomerNo == null && GrpCustomerNo.equals("")){
    //����������Ϣ
    if (request.getParameterValues("PolDutyCodeGridNo") == null)
    {
        loggerDebug("LLClaimRegisterConclusionSave","�޸�������!");
    }
    else
    {
        String tGrid1 [] = request.getParameterValues("PolDutyCodeGrid1"); //������
        String tGrid2 [] = request.getParameterValues("PolDutyCodeGrid2"); //������������
        String tGrid4 [] = request.getParameterValues("PolDutyCodeGrid4"); //�������α���
        String tGrid13 [] = request.getParameterValues("PolDutyCodeGrid13"); //�õ���13�и������������ֵ
        String tGrid16 [] = request.getParameterValues("PolDutyCodeGrid16"); //dutycode
      for (int index=0;index < tGrid1.length;index++)
        {
            LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
            tLLClaimDetailSchema.setClmNo(tRptNo);
            tLLClaimDetailSchema.setCaseNo(tRptNo);
            tLLClaimDetailSchema.setPolNo(tGrid1[index]);
            tLLClaimDetailSchema.setGetDutyKind(tGrid2[index]);
            tLLClaimDetailSchema.setGetDutyCode(tGrid4[index]);
            tLLClaimDetailSchema.setDutyCode(tGrid16[index]);
            tLLClaimDetailSchema.setCaseRelaNo(tAccNo);
            tLLClaimDetailSchema.setGiveType(tGrid13[index]);
            mLLClaimDetailSet.add(tLLClaimDetailSchema);
        }
    }
  }
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("ClmNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RgtConclusion",request.getParameter("RgtConclusion") );
    mTransferData.setNameAndValue("NoRgtReason",request.getParameter("NoRgtReason") );
    mTransferData.setNameAndValue("DeferRgtReason",DeferRgtReason );
    mTransferData.setNameAndValue("DeferRgtRemark",DeferRgtRemark );    
    mTransferData.setNameAndValue("RgtState", request.getParameter("rgtType"));    
    mTransferData.setNameAndValue("RgtFlag", RgtFlag);    
    
if(GrpCustomerNo!=null&&!GrpCustomerNo.equals("")){
    mTransferData.setNameAndValue("BeAdjSum", request.getParameter("Grpstandpay"));
    mTransferData.setNameAndValue("GrpFlag", "1"); //�ŵ����
    }else{
    mTransferData.setNameAndValue("BeAdjSum", request.getParameter("adjpay"));
    mTransferData.setNameAndValue("GrpFlag", "2"); //�������
    }
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(mLLClaimDetailSet);

    try
    {
        //�����ύ
//        if (!tLLClaimRegisterConclusionUI.submitData(tVData,transact))
//        {
//            Content = " LLClaimRegisterConclusionUI��������ʧ�ܣ�ԭ����: " + tLLClaimRegisterConclusionUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
        if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLClaimRegisterConclusionUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLClaimRegisterConclusionUI��������ʧ��";
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
        Content = "�����ύLLClaimRegisterConclusionUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }

}

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
