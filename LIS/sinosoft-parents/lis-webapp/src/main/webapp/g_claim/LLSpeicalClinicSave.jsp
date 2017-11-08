<%
/***************************************************************
 * <p>ProName��LLSpeicalClinicSave.jsp</p>
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
		String tOldBillName=request.getParameter("SpecialClinicBill1");
		String tCaseSource =request.getParameter("CaseSource");
		String tSerialNo1 =request.getParameter("tSerialNo3");
		String tHospCode= request.getParameter("tempHosp");
		String tHospStart =request.getParameter("tempHospStart");
		String tHospEnd =request.getParameter("tempHospEnd");
		String tResult1 =request.getParameter("tempResult1");
		//׼������������Ϣ
		LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();    
    	//׼����̨����
		tLLFeeMainSchema.setClmNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCaseNo(request.getParameter("tCaseNo3"));
		tLLFeeMainSchema.setMainFeeNo(request.getParameter("SpecialClinicNo").trim());
		tLLFeeMainSchema.setCustomerNo(request.getParameter("tCustomerNo"));
		tLLFeeMainSchema.setHospitalCode(request.getParameter("SpecialClinicHospID"));
		tLLFeeMainSchema.setFeeType("PA");
		tLLFeeMainSchema.setReceiptNo(tSerialNo1);
		tLLFeeMainSchema.setHospitalName(request.getParameter("SpecialClinicHospIDName"));
		tLLFeeMainSchema.setHospStartDate(request.getParameter("SpecialClinicStart"));
		tLLFeeMainSchema.setHospEndDate(request.getParameter("SpecialClinicStart"));
		tLLFeeMainSchema.setBillMoney(request.getParameter("SpecialClinicMoney"));
		tLLFeeMainSchema.setOtherExplain(request.getParameter("SpecailClinicRemark"));
		tLLFeeMainSchema.setECMNO(request.getParameter("SpecialClinicScanNum"));
		tLLFeeMainSchema.setAccResult1(request.getParameter("SpecialClinicICD"));
   		tLLFeeMainSchema.setAccResult2(request.getParameter("SpecialClinicICDDetail"));
  		String arrCount[] = request.getParameterValues("SpecialClinicItemGridNo");
  		LLCaseReceiptSet tLLCaseReceiptSet =new LLCaseReceiptSet();
  	
  	if (arrCount != null){
		String tFeeItemCode[] =request.getParameterValues("SpecialClinicItemGrid1"); //�������ʹ���
		String tFeeItemName[] = request.getParameterValues("SpecialClinicItemGrid2");	//��������
		String tFee[] = request.getParameterValues("SpecialClinicItemGrid3");	//���ý��
		String tSelfAmnt[] = request.getParameterValues("SpecialClinicItemGrid4");	//�Է�
		String tPartlySelfAmnt[] = request.getParameterValues("SpecialClinicItemGrid5");	//�����Է�
		String tUnReasonAmnt[] = request.getParameterValues("SpecialClinicItemGrid6");	//�������Է�
		String tRefuseAmnt[] = request.getParameterValues("SpecialClinicItemGrid7");	//�۳����
  		String tAdjRemark[] = request.getParameterValues("SpecialClinicItemGrid8");	//�۳�˵��
  		String tRemark[] = request.getParameterValues("SpecialClinicItemGrid9");	//��ע
  		int	lineCount = arrCount.length; //����
	
		for(int i=0;i<lineCount;i++){
		
		LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
		tLLCaseReceiptSchema.setCustomerNo(request.getParameter("tCustomerNo"));
	    tLLCaseReceiptSchema.setCaseNo(request.getParameter("tCaseNo3"));
	    tLLCaseReceiptSchema.setClmNo(request.getParameter("tRgtNo"));
	    tLLCaseReceiptSchema.setRgtNo(request.getParameter("tRgtNo"));
	    tLLCaseReceiptSchema.setFeeItemType("PA");    //����
	    tLLCaseReceiptSchema.setFeeItemCode(tFeeItemCode[i]);   //��������
	    tLLCaseReceiptSchema.setFeeItemName(tFeeItemName[i]);  //��������name
	    tLLCaseReceiptSchema.setFee(tFee[i]);  //���ý��
	    tLLCaseReceiptSchema.setStartDate(request.getParameter("SpecialClinicStart"));   //��ʼ����
	    tLLCaseReceiptSchema.setEndDate(request.getParameter("SpecialClinicStart"));   //��������
	    tLLCaseReceiptSchema.setAdjRemark(tAdjRemark[i]);
	    tLLCaseReceiptSchema.setRefuseAmnt(tRefuseAmnt[i]);
	    tLLCaseReceiptSchema.setRemark(tRemark[i]);
	    tLLCaseReceiptSchema.setSelfAmnt(tSelfAmnt[i]);
	    tLLCaseReceiptSchema.setPartlyselfAmnt(tPartlySelfAmnt[i]);
	    tLLCaseReceiptSchema.setUnreasonAmnt(tUnReasonAmnt[i]);
	    tLLCaseReceiptSchema.setDayCount("0");
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
