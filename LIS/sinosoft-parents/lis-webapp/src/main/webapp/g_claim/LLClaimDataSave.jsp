<%
/***************************************************************
 * <p>ProName��LLClaimDataSave.jsp</p>
 * <p>Title��������</p>
 * <p>Description��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	
	String tFilePath = "";
	String tFileName = "";	
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
			try {
				
			String tOperate = request.getParameter("Operate");
			String tRptNo = request.getParameter("RptNo");
			String tCustomerNo = request.getParameter("CustomerNo");
			TransferData tTransferData = new TransferData();
	 		tTransferData.setNameAndValue("RptNo", tRptNo);			
	 		tTransferData.setNameAndValue("CustomerNo", tCustomerNo);			
	
			//׼������������Ϣ
			LLReportAffixSet tLLReportAffixSet = new LLReportAffixSet(); //����������
	
			//����MulLine��������ݼ��� 
			String tGridNo[] = request.getParameterValues("DocumentListGridNO");  //�õ�����е�����ֵ
			String tAffixCode[] = request.getParameterValues("DocumentListGrid1"); //�õ���1�е�����ֵ
			String tAffixName[] = request.getParameterValues("DocumentListGrid2"); //�õ���2�е�����ֵ
			String tReasonCode[] = request.getParameterValues("DocumentListGrid3"); //�õ���3�е�����ֵ
			String tReasonName[] = request.getParameterValues("DocumentListGrid4"); //�õ���4�е�����ֵ
			String tProperty[] = request.getParameterValues("DocumentListGrid6"); //�õ���6�е�����ֵ
			String tAffixNo[] = request.getParameterValues("DocumentListGrid8"); //�õ���8�е�����ֵ
	
			String tSel[] = request.getParameterValues("InpDocumentListGridChk"); //������ʽ=�� Inp+MulLine������+Chk��
			  
		  for(int index=0;index<tSel.length;index++) {
			  
				LLReportAffixSchema tLLReportAffixSchema = new LLReportAffixSchema();
				if(tSel[index].equals("1")) {
				
					tLLReportAffixSchema.setRptNo(tRptNo);//�ⰸ��
					tLLReportAffixSchema.setRptNo(tRptNo);//�ⰸ��
					tLLReportAffixSchema.setCustomerNo(tCustomerNo);//�ͻ���   
					tLLReportAffixSchema.setAffixCode(tAffixCode[index]);//��֤����
					tLLReportAffixSchema.setAffixName(tAffixName[index]);//��֤����
					tLLReportAffixSchema.setReasonCode(tReasonCode[index]);//��֤����Ӧ��������
					tLLReportAffixSchema.setReasonName(tReasonName[index]);//��֤�������
					tLLReportAffixSchema.setProperty(tProperty[index]);//ԭ��������ʶ(0-ԭ����1-����)		  
					tLLReportAffixSchema.setAffixNo(tAffixNo[index]);//��֤���
					tLLReportAffixSchema.setReadyCount(1);
					tLLReportAffixSet.add(tLLReportAffixSchema);				
				}
		  }
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLReportAffixSet);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimDataUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				
				TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				
				tFilePath = (String) trd.getValueByName("FilePath");
				tFileName = (String) trd.getValueByName("FileName");				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath%>","<%=tFileName%>");
</script>
</html>

