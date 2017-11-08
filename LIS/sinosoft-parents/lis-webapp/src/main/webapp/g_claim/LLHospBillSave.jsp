<%
/***************************************************************
 * <p>ProName��LLClinicBillSave.jsp</p>
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
		String tOldBillName=request.getParameter("HospBillNo1");
		String tCaseSource =request.getParameter("CaseSource");
		String tSerialNo1 =request.getParameter("tSerialNo2");
		String tHospCode= request.getParameter("tempHosp");
		String tHospStart =request.getParameter("tempHospStart");
		String tHospEnd =request.getParameter("tempHospEnd");
		String tResult1 =request.getParameter("tempResult1");
		
		//׼������������Ϣ
		LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();    
		LLEasyBillDeductItemSet tLLEasyBillDeductItemSet=new LLEasyBillDeductItemSet();
    //׼����̨����
		tLLFeeMainSchema.setClmNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCaseNo(request.getParameter("tCaseNo2"));
		tLLFeeMainSchema.setMainFeeNo(request.getParameter("HospBillNo").trim());
		tLLFeeMainSchema.setRgtNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCustomerNo(request.getParameter("tCustomerNo")); 
		tLLFeeMainSchema.setHospitalCode(request.getParameter("HospID"));
		tLLFeeMainSchema.setFeeType("OB");
		tLLFeeMainSchema.setReceiptNo(tSerialNo1);
		tLLFeeMainSchema.setHospitalName(request.getParameter("HospIDName"));
		tLLFeeMainSchema.setHospStartDate(request.getParameter("HospStartdate"));
		tLLFeeMainSchema.setHospEndDate(request.getParameter("HospEnddate"));
		tLLFeeMainSchema.setBillMoney(request.getParameter("HospBillMoney"));
		tLLFeeMainSchema.setOtherExplain(request.getParameter("HospRemark"));
		tLLFeeMainSchema.setECMNO(request.getParameter("HospScanNum"));
		tLLFeeMainSchema.setAccResult1(request.getParameter("HospICDNo"));
		tLLFeeMainSchema.setAccResult2(request.getParameter("HospICDDetail"));
		tLLFeeMainSchema.setRealHospDate(request.getParameter("HospDays"));
    
		LLCaseReceiptSet tLLCaseReceiptSet =new LLCaseReceiptSet(); 
 		String arrCount[] = request.getParameterValues("HospBillItemGridNo");
 	if (arrCount != null){
 	
				String tDeducttypecode[] =request.getParameterValues("HospBillItemGrid1"); //�������ʹ���
				String tDeducttypename[] = request.getParameterValues("HospBillItemGrid2");	//��������
				String tDeductmoney[] = request.getParameterValues("HospBillItemGrid3");	//���ý��
				String tDeductdesc[] = request.getParameterValues("HospBillItemGrid4");	//�Է�
				String tRemark[] = request.getParameterValues("HospBillItemGrid5");	//�����Է�
 			  int	lineCount = arrCount.length; //����
			  for(int i=0;i<lineCount;i++){
			  
					LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
					tLLCaseReceiptSchema.setCustomerNo(request.getParameter("tCustomerNo"));
		    		tLLCaseReceiptSchema.setClmNo(request.getParameter("tRgtNo"));
		    		tLLCaseReceiptSchema.setRgtNo(request.getParameter("tRgtNo"));
					tLLCaseReceiptSchema.setCaseNo(request.getParameter("tCaseNo2"));
					tLLCaseReceiptSchema.setFeeItemType("OB");    //��������
					tLLCaseReceiptSchema.setFeeItemCode(tDeducttypecode[i]);
					tLLCaseReceiptSchema.setFeeItemName(tDeducttypename[i]);
					tLLCaseReceiptSchema.setRefuseAmnt(tDeductmoney[i]);
					tLLCaseReceiptSchema.setAdjRemark(tDeductdesc[i]);
					tLLCaseReceiptSchema.setRemark(tRemark[i]);
					tLLCaseReceiptSchema.setStartDate(request.getParameter("HospStartdate"));   //��ʼ����
					tLLCaseReceiptSchema.setEndDate(request.getParameter("HospEnddate"));   //��������
					tLLCaseReceiptSchema.setDayCount(request.getParameter("HospDays"));
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
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSimpleBillUI")) {
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
