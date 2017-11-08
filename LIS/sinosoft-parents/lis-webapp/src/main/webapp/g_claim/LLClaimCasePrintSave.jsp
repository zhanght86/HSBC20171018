<%
/***************************************************************
 * <p>ProName��LLClaimCasePrintSave.jsp</p>
 * <p>Title��������ӡ</p>
 * <p>Description��������ӡ</p>
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
	String tFileName="";
	String tFilePath1="";
	GlobalInput tGI = new GlobalInput();
	TransferData tTransferData=new TransferData();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			System.out.println("��ʼ");
			String tOperate = request.getParameter("Operate");
				
			LLRegisterSet tLLRegisterSet = new LLRegisterSet();

			String tRaidoNo[] = request.getParameterValues("InpCustomerListGridChk");  //�õ�����е�����ֵ
			String tGrid1 [] = request.getParameterValues("CustomerListGrid1"); //�õ���3�е�����ֵ ���κ�
			String tGrid2 [] = request.getParameterValues("CustomerListGrid2"); //�õ���5�е�����ֵ �ⰸ��
			String tGrid3 [] = request.getParameterValues("CustomerListGrid4"); //�ͻ���

			tTransferData.setNameAndValue("Type","2");//1�����ӡ��2��ͨ������ӡ������������ӡ
			if("BatchPrint".equals(tOperate)){
				
				String tGrpRgtNo=request.getParameter("tGrpRgtNo");
				tTransferData.setNameAndValue("GrpRgtNo",tGrpRgtNo);
			}else{
				for(int index=0;index<tRaidoNo.length;index++){
					if(tRaidoNo[index].equals("1")) {
						LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
						tLLRegisterSchema.setGrpRgtNo(tGrid1[index]);
						tLLRegisterSchema.setRgtNo(tGrid2[index]);
						tLLRegisterSchema.setCustomerNo(tGrid3[index]);
						tLLRegisterSet.add(tLLRegisterSchema);
					}
				}
		}
		String tempFilePath = request.getRealPath("");
		String tempImagePath = request.getRealPath("");
		tempFilePath=tempFilePath.replaceAll("\\\\","/");
		tempImagePath=tempImagePath.replaceAll("\\\\","/");
	
		tempImagePath+="/common/images/image004.png";
		tTransferData.setNameAndValue("tempFilePath",tempFilePath);
		tTransferData.setNameAndValue("tempImagePath",tempImagePath);
		
	
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLRegisterSet);
			tVData.add(tTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimOutCasePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				if("BatchPrint".equals(tOperate)||"Print".equals(tOperate)){
					
					TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tFilePath1 = (String) trd.getValueByName("FilePath");
					tFileName = (String) trd.getValueByName("FileName");
					
				}
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>