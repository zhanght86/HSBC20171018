<%@page import="com.sinosoft.lis.vschema.LPRenewalSet"%>
<%@page import="com.sinosoft.lis.schema.LPRenewalSchema"%>
<%
/***************************************************************
 * <p>ProName��RenewalManualSave.jsp</p>
 * <p>Title�����ڴ���</p>
 * <p>Description�����ڴ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			/** ��������
			String tPayCount = request.getParameter("PayCount");
			String tGrpContNo =  request.getParameter("GrpContNo");
			**/
			LPRenewalSet tLPRenewalSet = new LPRenewalSet();
			String tCheckChk[] = request.getParameterValues("InpContInfoGridSel");//��ȡ�������Ϣ
			System.out.println("***************************"+tCheckChk.length);
			if (tCheckChk!=null && tCheckChk.length>0) {
				
				String tGrpContNo[] = request.getParameterValues("ContInfoGrid3");//������
				String tPayCount[] = request.getParameterValues("ContInfoGrid10");//��������
				String tMoney[] = request.getParameterValues("ContInfoGrid14");//�����
				
				for (int i=0; i<tCheckChk.length; i++) {
					
					if ("1".equals(tCheckChk[i])) {
					
						LPRenewalSchema tLPRenewalSchema = new LPRenewalSchema();
						tLPRenewalSchema.setGrpContNo(tGrpContNo[i]);
						tLPRenewalSchema.setPayCount(tPayCount[i]);
						tLPRenewalSchema.setGrpContNo(tGrpContNo[i]);
						
						tLPRenewalSet.add(tLPRenewalSchema);
					}
				}
			}
			String tRenewalType =  request.getParameter("RenewalType");
			
			tTransferData.setNameAndValue("RenewalType",tRenewalType);
			tVData.add(tLPRenewalSet);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "RenewalManualUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
