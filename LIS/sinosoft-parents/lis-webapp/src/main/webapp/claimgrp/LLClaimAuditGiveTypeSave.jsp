<%
//**************************************************************************************************
//Name��LLClaimAuditGiveTypeSave.jsp
//Function����˱�������ύ
//Author��zhoulei
//Date: 2005-7-1 16:06
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

LLPolTraceSchema tLLPolTraceSchema=new LLPolTraceSchema();


if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimAuditGiveTypeSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    //��ȡ������
    String tRptNo = request.getParameter("RptNo");
    String tAccNo = request.getParameter("AccNo");
    String tpolstate = request.getParameter("polstate");
    String tPolNo = request.getParameter("PolNo");
    String tGiveType = request.getParameter("GiveType");
    
    
    //����������Ϣ
    if (request.getParameterValues("PolDutyCodeGridNo") == null)
    {
        loggerDebug("LLClaimAuditGiveTypeSave","�޸�������!");
    }
    else
    {
    	  String tGrid1 [] = request.getParameterValues("PolDutyCodeGrid1"); //������
          String tGrid2 [] = request.getParameterValues("PolDutyCodeGrid2"); //������������
          String tGrid4 [] = request.getParameterValues("PolDutyCodeGrid4"); //�������α���
          String tGrid13 [] = request.getParameterValues("PolDutyCodeGrid13"); //�⸶����
          String tGrid15 [] = request.getParameterValues("PolDutyCodeGrid15"); //�ܸ�ԭ�����
          String tGrid17 [] = request.getParameterValues("PolDutyCodeGrid17"); //�ܸ�����
          String tGrid18 [] = request.getParameterValues("PolDutyCodeGrid18"); //���ⱸע
          String tGrid21 [] = request.getParameterValues("PolDutyCodeGrid21"); //�������
          String tGrid22 [] = request.getParameterValues("PolDutyCodeGrid22"); //����ԭ�����
          String tGrid23 [] = request.getParameterValues("PolDutyCodeGrid23"); //����ԭ������
          String tGrid24 [] = request.getParameterValues("PolDutyCodeGrid24"); //������ע
          String tGrid28 [] = request.getParameterValues("PolDutyCodeGrid28"); //dutycode
          String tGrid29 [] = request.getParameterValues("PolDutyCodeGrid29"); //�ͻ���
          
    	  String tRadio[] = request.getParameterValues("InpPolDutyCodeGridSel");  
          //������ʽ=�� Inp+MulLine������+Sel�� 
		  for(int index=0; index< tRadio.length;index++)
		  {
			  if(tRadio[index].equals("1"))
			  {
				    LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
		            tLLClaimDetailSchema.setClmNo(tRptNo);
		            tLLClaimDetailSchema.setCaseNo(tRptNo);
		            tLLClaimDetailSchema.setPolNo(tGrid1[index]);
		            tLLClaimDetailSchema.setGetDutyKind(tGrid2[index]);
		            tLLClaimDetailSchema.setGetDutyCode(tGrid4[index]);
		            tLLClaimDetailSchema.setDutyCode(tGrid28[index]);
		            tLLClaimDetailSchema.setCaseRelaNo(tAccNo);
		            tLLClaimDetailSchema.setGiveType(tGrid13[index]);
		            tLLClaimDetailSchema.setRealPay(tGrid21[index]);
		            tLLClaimDetailSchema.setAdjReason(tGrid22[index]);
//		            tLLClaimDetailSchema.setAdjReasonName(tGrid18[index]);
		            tLLClaimDetailSchema.setAdjRemark(tGrid24[index]);
		            tLLClaimDetailSchema.setGiveReason(tGrid15[index]);
		            tLLClaimDetailSchema.setGiveReasonDesc(tGrid17[index]);
		            tLLClaimDetailSchema.setSpecialRemark(tGrid18[index]);
		            tLLClaimDetailSchema.setCustomerNo(tGrid29[index]);
		            mLLClaimDetailSet.add(tLLClaimDetailSchema);
		            
		            loggerDebug("LLClaimAuditGiveTypeSave","GiveReasonDesc:"+tGrid17[index]);
		            loggerDebug("LLClaimAuditGiveTypeSave","GiveReasonDesc:"+tGrid18[index]);
			  }
		  }



        tLLPolTraceSchema.setPolNo(tPolNo);
        tLLPolTraceSchema.setnewPolState(tpolstate);        
            
    }
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("AccNo", request.getParameter("AccNo"));
    mTransferData.setNameAndValue("ClmNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("ContType","1");   //�ܵ�����,1-������Ͷ����,2-�����ܵ�        
    mTransferData.setNameAndValue("ClmState","30");  //�ⰸ״̬��20������30���
    mTransferData.setNameAndValue("GiveType", request.getParameter("GiveType"));
    //---------------------------------------jinsh20070515----------------------------------//
    mTransferData.setNameAndValue("AdjReason", request.getParameter("AdjReason"));
    //---------------------------------------jinsh20070515----------------------------------//
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(mLLClaimDetailSet);
    tVData.add(tLLPolTraceSchema);    

    try
    {
//        LLClaimAuditGiveTypeUI tLLClaimAuditGiveTypeUI = new LLClaimAuditGiveTypeUI();
//        //�����ύ
//        if (!tLLClaimAuditGiveTypeUI.submitData(tVData,"UPDATE"))
//        {
//            Content = " LLClaimAuditGiveTypeUI��������ʧ�ܣ�ԭ����: " + tLLClaimAuditGiveTypeUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="grpLLClaimAuditGiveTypeUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"UPDATE",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLClaimAuditGiveTypeUI��������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLClaimAuditGiveTypeUI��������ʧ��";
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
        Content = "�����ύLLClaimAuditGiveTypeUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }

}

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
