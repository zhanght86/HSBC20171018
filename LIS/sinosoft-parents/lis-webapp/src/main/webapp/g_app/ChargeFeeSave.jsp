<%
/***************************************************************
 * <p>ProName��ChargeFeeSave.jsp</p>
 * <p>Title���н���������ά��</p>
 * <p>Description���н���������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCPrintChangeFeeSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCPrintChangeFeeSet"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPrintChangeFeeSet mLCPrintChangeFeeSet = null;
	LCPrintChangeFeeSchema mLCPrintChangeFeeSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			mLCPrintChangeFeeSet = new LCPrintChangeFeeSet();
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tRiskCode=request.getParameter("RiskCode");
			
			if("INSERT".equals(tOperate)){
				String tGridNo[] = request.getParameterValues("ZJGridNo");
				String tGrid1 [] = request.getParameterValues("ZJGrid1"); //�õ���1�е�����ֵ 
				String tGrid2 [] = request.getParameterValues("ZJGrid2");
				String tGrid4 [] = request.getParameterValues("ZJGrid4");    		
				int Count = tGridNo.length; //�õ����ܵ��ļ�¼��
	
				for(int index=0;index<Count;index++){
			
					mLCPrintChangeFeeSchema = new LCPrintChangeFeeSchema();
					mLCPrintChangeFeeSchema.setGrpPropNo(tGrpPropNo);
					mLCPrintChangeFeeSchema.setGrpContNo(tGrpPropNo);
					mLCPrintChangeFeeSchema.setRiskCode(tRiskCode);
					mLCPrintChangeFeeSchema.setAgentCom(tGrid1[index]);
					mLCPrintChangeFeeSchema.setAgentComName(tGrid2[index]);
					mLCPrintChangeFeeSchema.setChangeFee(tGrid4[index]);
					mLCPrintChangeFeeSet.add(mLCPrintChangeFeeSchema);
			}
		}
		tVData.add(mLCPrintChangeFeeSet);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "ChargeFeeUI")) {
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
