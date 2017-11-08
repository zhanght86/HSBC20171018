<%
/***************************************************************
* <p>ProName��LCDutyAdjustPremSave.jsp</p>
 * <p>Title�������������ε�������</p>
 * <p>Description�������������ε�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
		
			// ���κ˱�����			
			String tOperate = request.getParameter("Operate");	
			String tGrpPropNo =  request.getParameter("GrpPropNo");	
			
			String tUWAdjustNum[] = request.getParameterValues("CpolErrGridNo");
			String tContNo[] = request.getParameterValues("CpolErrGrid1");//�����˿ͻ���
			String tPlancode[] = request.getParameterValues("CpolErrGrid2");//��������
			String tPolno[] = request.getParameterValues("CpolErrGrid3");//���������ֺ�
			String tRiskCode[] = request.getParameterValues("CpolErrGrid4");//���ֱ���
			String tDutyCode[] = request.getParameterValues("CpolErrGrid6");//���α���
			String tPrem[] = request.getParameterValues("CpolErrGrid8");//����
			String tAmnt[] = request.getParameterValues("CpolErrGrid9");//����
			String tAdjustPrem[] = request.getParameterValues("CpolErrGrid10");//��������
			String tAdjustAmnt[] = request.getParameterValues("CpolErrGrid11");//��������
			String tInsuredNo[] = request.getParameterValues("CpolErrGrid12");//���õ��������˿ͻ���

			LCDutyUWAdjustSet tLCDutyUWAdjustSet = new LCDutyUWAdjustSet();
			LCDutyUWAdjustSchema tLCDutyUWAdjustSchema = new LCDutyUWAdjustSchema();
			if(null != tUWAdjustNum &&tUWAdjustNum.length>=1 ){
			for (int i = 0; i < tUWAdjustNum.length; i++){
				tLCDutyUWAdjustSchema   = new LCDutyUWAdjustSchema();
				tLCDutyUWAdjustSchema.setBussType("NB");
				tLCDutyUWAdjustSchema.setBussNo(tGrpPropNo);
				tLCDutyUWAdjustSchema.setContNo(tContNo[i]);
				tLCDutyUWAdjustSchema.setPolNo(tPolno[i]);
				tLCDutyUWAdjustSchema.setPlanCode(tPlancode[i]);
				tLCDutyUWAdjustSchema.setRiskCode(tRiskCode[i]);
				tLCDutyUWAdjustSchema.setDutyCode(tDutyCode[i]);
				tLCDutyUWAdjustSchema.setAmnt(tAmnt[i]);
				tLCDutyUWAdjustSchema.setUWAmnt(tAdjustAmnt[i]);
				tLCDutyUWAdjustSchema.setPrem(tPrem[i]);
				tLCDutyUWAdjustSchema.setUWPrem(tAdjustPrem[i]);
				tLCDutyUWAdjustSchema.setOtherNo(tInsuredNo[i]);
		
				tLCDutyUWAdjustSet.add(tLCDutyUWAdjustSchema);
			}			
			
		}
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Flag",request.getParameter("Flag"));

		VData tVData = new VData();
		
		tVData.add(tGI);
		tVData.add(tTransferData);
		tVData.add(tLCDutyUWAdjustSet);
	
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "LCDutyUWAdjustUI")) {
			tContent = tBusinessDelegate.getCErrors().getFirstError();
			tFlagStr = "Fail";
		} else {
			tContent = "�������ֱ��ѵ��������ɹ���";
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
