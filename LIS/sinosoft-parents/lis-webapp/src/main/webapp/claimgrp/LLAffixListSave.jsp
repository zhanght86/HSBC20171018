<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//**************************************************************************************************
//Name��LLAffixListSave.jsp
//Function������__�������__����
//Author��pd
//Date: 2005-11-1
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="java.util.*"%>
<%
  LLStandPayInfoSet tLLStandPayInfoSet = new LLStandPayInfoSet();
  //LLStandPayInfoBL tLLStandPayInfoBL = new LLStandPayInfoBL();
  String busiName="grpLLStandPayInfoBL";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  VData tVData = new VData();
  String FlagStr = "";
  String transact = "";
  String Content = "";
  CErrors tError = null;
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput) session.getValue("GI");
  transact = request.getParameter("fmtransact");
  String tRiskCode[] = request.getParameterValues("AffixGrid1"); //���ִ���
  String tCurrency[] = request.getParameterValues("AffixGrid3"); //����
  String tStandPay[] = request.getParameterValues("AffixGrid4"); //Ԥ�����

  for (int index = 0;  index<tRiskCode.length; index++) {
    LLStandPayInfoSchema tLLStandPayInfoSchema = new LLStandPayInfoSchema();
    tLLStandPayInfoSchema.setRiskCode(tRiskCode[index]);
    tLLStandPayInfoSchema.setCurrency(tCurrency[index]);
    tLLStandPayInfoSchema.setStandPay(tStandPay[index]);
//    tLLStandPayInfoSchema.setCustomerName(request.getParameter("customerName"));
    tLLStandPayInfoSchema.setCaseNo(request.getParameter("CaseNo"));
//    tLLStandPayInfoSchema.setCustomerNo(request.getParameter("customerNo"));
    tLLStandPayInfoSet.add(tLLStandPayInfoSchema);
  }

    try {
      // ׼���������� VData
      tVData.add(tLLStandPayInfoSet);
      tVData.add(tGlobalInput);
//      if (!tLLStandPayInfoBL.submitData(tVData, transact)) {
//        FlagStr = "";
//      }
	if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else
		{
			Content = "ʧ��";
			FlagStr = "Fail";				
		}
	}

    }catch (Exception ex) {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      FlagStr = "Fail";
    }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr.equals("")) {
      //tError = tLLStandPayInfoBL.mErrors;
      tError = tBusinessDelegate.getCErrors();
      if (!tError.needDealError()) {
        Content = " ����ɹ�! ";
        FlagStr = "Success";
        tVData.clear();
       // tVData = tLLStandPayInfoBL.getResult();
         tVData = tBusinessDelegate.getResult();
      }
      else {
        Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
        FlagStr = "Fail";
      }
    }
%>
<html>
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script></html>
