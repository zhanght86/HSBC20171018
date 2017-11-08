<%
/***************************************************************
 * <p>ProName��LCGrpUWSave.jsp</p>
 * <p>Title�������˱�ȷ��</p>
 * <p>Description�������˱�ȷ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : dianjing
 * @version  : 8.0
 * @date     : 2014-04-28
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%> 
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			System.out.println("tOperate="+tOperate);
			
			String tMissionID = request.getParameter("MissionID");	//����ID
			String tSubMissionID = request.getParameter("SubMissionID");	//������ID
			String tActivityID = request.getParameter("ActivityID");	//��ڵ�ID
			String tGrpPropNo = request.getParameter("GrpPropNo");	//Ͷ������
			String tContPlanType = request.getParameter("ContPlanType");	//��������
			String tValDate = request.getParameter("ValDate");	//��������
			String tReinsFlag = request.getParameter("ReinsFlag");	//�Ƿ�ɾ���ٱ�����
			
			VData tVData = new VData();
			tVData.add(tGI);
			TransferData vTransferData = new TransferData();
			 
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			vTransferData.setNameAndValue("ContPlanType",tContPlanType);
			vTransferData.setNameAndValue("ValDate",tValDate);
			vTransferData.setNameAndValue("ReinsFlag",tReinsFlag);

			tVData.add(vTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCGrpUWDealUI")) {
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
