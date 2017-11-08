
<%
/***************************************************************
 * <p>ProName��LLSpeicalHospSave.jsp</p>
 * <p>Title�����������˵�</p>
 * <p>Description�����������˵�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
		String tOperate = request.getParameter("Operate");//��ò���״̬
		String tOldBillName=request.getParameter("SpecialHospBillNo1");
		String tCaseSource =request.getParameter("CaseSource");
		String tSerialNo1 =request.getParameter("tSerialNo4");
		String tHospCode= request.getParameter("tempHosp");
		String tHospStart =request.getParameter("tempHospStart");
		String tHospEnd =request.getParameter("tempHospEnd");
		String tResult1 =request.getParameter("tempResult1");
		//׼������������Ϣ
		LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();    
    	//׼����̨����
		tLLFeeMainSchema.setClmNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCaseNo(request.getParameter("tCaseNo4"));
		tLLFeeMainSchema.setMainFeeNo(request.getParameter("SpecialHospBillNo").trim());
		tLLFeeMainSchema.setCustomerNo(request.getParameter("tCustomerNo"));
		tLLFeeMainSchema.setHospitalCode(request.getParameter("SpecialHospID"));
		tLLFeeMainSchema.setFeeType("PB");
		tLLFeeMainSchema.setReceiptNo(tSerialNo1);
		tLLFeeMainSchema.setRealHospDate(request.getParameter("SpecialHospNum"));
		tLLFeeMainSchema.setHospitalName(request.getParameter("SpecialHospIDName"));
		tLLFeeMainSchema.setHospStartDate(request.getParameter("SpecialHospStart"));
		tLLFeeMainSchema.setHospEndDate(request.getParameter("SpecialHospEnd"));
		tLLFeeMainSchema.setBillMoney(request.getParameter("SpecialHospBillMoney"));
		tLLFeeMainSchema.setOtherExplain(request.getParameter("SpecialHospRemark"));
		tLLFeeMainSchema.setECMNO(request.getParameter("SpecialHospScanNum"));
		tLLFeeMainSchema.setAccResult1(request.getParameter("SpecialHospICD"));
   	tLLFeeMainSchema.setAccResult2(request.getParameter("SpecialHospICDDetail"));

  	String arrCount[] = request.getParameterValues("SpecialHospItemGridNo");
  	LLCaseReceiptSet tLLCaseReceiptSet =new LLCaseReceiptSet();
  	if (arrCount != null){
		String tFeeItemCode[] =request.getParameterValues("SpecialHospItemGrid1"); //�������ʹ���
		String tFeeItemName[] = request.getParameterValues("SpecialHospItemGrid2");	//��������
		String tFee[] = request.getParameterValues("SpecialHospItemGrid3");	//���ý��
		String tSelfAmnt[] = request.getParameterValues("SpecialHospItemGrid4");	//�Է�
		String tPartlySelfAmnt[] = request.getParameterValues("SpecialHospItemGrid5");	//�����Է�
		String tUnReasonAmnt[] = request.getParameterValues("SpecialHospItemGrid6");	//�������Է�
		String tRefuseAmnt[] = request.getParameterValues("SpecialHospItemGrid7");	//�۳����
  	String tAdjRemark[] = request.getParameterValues("SpecialHospItemGrid8");	//�۳�˵��
  	String tRemark[] = request.getParameterValues("SpecialHospItemGrid9");	//��ע
  		int	lineCount = arrCount.length; //����
	
		for(int i=0;i<lineCount;i++){
		
		LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
		tLLCaseReceiptSchema.setCustomerNo(request.getParameter("tCustomerNo"));
	    tLLCaseReceiptSchema.setCaseNo(request.getParameter("tCaseNo4"));
	    tLLCaseReceiptSchema.setClmNo(request.getParameter("tRgtNo"));
	    tLLCaseReceiptSchema.setRgtNo(request.getParameter("tRgtNo"));
	    tLLCaseReceiptSchema.setFeeItemType("PB");    //����
	    tLLCaseReceiptSchema.setFeeItemCode(tFeeItemCode[i]);   //��������
	    tLLCaseReceiptSchema.setFeeItemName(tFeeItemName[i]);  //��������name
	    tLLCaseReceiptSchema.setFee(tFee[i]);  //���ý��
	    tLLCaseReceiptSchema.setStartDate(request.getParameter("SpecialHospStart"));   //��ʼ����
	    tLLCaseReceiptSchema.setEndDate(request.getParameter("SpecialHospEnd"));   //��������
	    tLLCaseReceiptSchema.setAdjRemark(tAdjRemark[i]);
	    tLLCaseReceiptSchema.setRefuseAmnt(tRefuseAmnt[i]);
	    tLLCaseReceiptSchema.setRemark(tRemark[i]);
	    tLLCaseReceiptSchema.setSelfAmnt(tSelfAmnt[i]);
	    tLLCaseReceiptSchema.setPartlyselfAmnt(tPartlySelfAmnt[i]);
	    tLLCaseReceiptSchema.setUnreasonAmnt(tUnReasonAmnt[i]);
	    tLLCaseReceiptSchema.setDayCount(request.getParameter("SpecialHospNum"));
		tLLCaseReceiptSet.add(tLLCaseReceiptSchema);
  		}
  	}
 		TransferData mTransferData = new TransferData();
 		mTransferData.setNameAndValue("OldBillName", tOldBillName);
 		mTransferData.setNameAndValue("CaseSource", tCaseSource);
 		mTransferData.setNameAndValue("SerialNo", tSerialNo1);
 		mTransferData.setNameAndValue("HospCode", tHospCode);
 		mTransferData.setNameAndValue("HospStart", tHospStart);
 		mTransferData.setNameAndValue("HospEnd", tHospEnd);
 		mTransferData.setNameAndValue("Result1", tResult1);
	// ׼���������� VData
		VData tVData = new VData();	
		tVData.add(tGI);
		tVData.add(tLLFeeMainSchema);
		tVData.add(tLLCaseReceiptSet);
		tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSpecialBillUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
