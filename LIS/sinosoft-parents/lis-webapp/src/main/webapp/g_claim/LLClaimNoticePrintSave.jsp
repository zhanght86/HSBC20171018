<%
/***************************************************************
 * <p>ProName��LLClaimNoticePrintSave.jsp</p>
 * <p>Title��֪ͨ���ӡ</p>
 * <p>Description��֪ͨ���ӡ</p>
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
			String tGrpRgtNo =request.getParameter("GrpRgtNo");	
			System.out.println(tOperate);
			
			System.out.println("========="+tGrpRgtNo);
			LLRegisterSet tLLRegisterSet = new LLRegisterSet();
			
			String tRaidoNo[] = request.getParameterValues("InpCaseGridChk");  //�õ�����е�����ֵ
			String tGrpRgtNos [] = request.getParameterValues("CaseGrid1"); //�õ���1�е�����ֵ ���κ�
			String tRgtNos [] = request.getParameterValues("CaseGrid2"); //�õ���2�е�����ֵ �ⰸ��
			String tCustomerNos [] = request.getParameterValues("CaseGrid3"); //�õ���2�е�����ֵ �ⰸ��

			tTransferData.setNameAndValue("Type","2");//1�����ӡ��2��ͨ������ӡ������������ӡ
			if("BATCHENDPRINT".equals(tOperate)){
				
				tTransferData.setNameAndValue("GrpRgtNo",tGrpRgtNo);
			}else{
				
				for(int index=0;index<tRaidoNo.length;index++){
					if(tRaidoNo[index].equals("1")) {
						LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
						tLLRegisterSchema.setGrpRgtNo(tGrpRgtNos[index]);
						tLLRegisterSchema.setRgtNo(tRgtNos[index]);
						tLLRegisterSchema.setCustomerNo(tCustomerNos[index]);
						tLLRegisterSet.add(tLLRegisterSchema);
					}
				}
			}								

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLRegisterSet);
			tVData.add(tTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimNoticePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				
				tFilePath1 = (String) trd.getValueByName("FilePath");
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
