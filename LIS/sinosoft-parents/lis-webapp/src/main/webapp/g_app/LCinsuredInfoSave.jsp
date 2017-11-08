<%
/***************************************************************
* <p>ProName��LCinsuredInfoSave.jsp</p>
 * <p>Title���������嵥�б�</p>
 * <p>Description���������嵥�б�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-04-22
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
		
			// ��������Ϣ			
			String tOperate = request.getParameter("Operate");	
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tContPlanType = request.getParameter("ContPlanType");
			
			String tCustomerno = request.getParameter("Customerno");
			String tCustomName =  request.getParameter("CustomName");
			String tIDNO =  request.getParameter("IDNO");
			String tCustomerID = request.getParameter("CustomerID");
			String tContPlanCode = request.getParameter("ContPlanCode");
			String tSysPlanCode = request.getParameter("SysPlanCode");

			String tInsredNum[] = request.getParameterValues("InpQueryScanGridChk");
			String tInsredNo[] = request.getParameterValues("QueryScanGrid1");//�����˿ͻ���
			String tContNo[] = request.getParameterValues("QueryScanGrid2");//�����˱�����
			String tRelationtomain[] = request.getParameterValues("QueryScanGrid3");//���������˹�ϵ

			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			
			if(null!=tInsredNum && tInsredNum.length>0){
				for (int i = 0; i < tInsredNum.length; i++){
					if (tInsredNum[i].equals("1")) {
						tLCInsuredSchema = new LCInsuredSchema();
						tLCInsuredSchema.setGrpContNo(tGrpPropNo);
						tLCInsuredSchema.setContNo(tContNo[i]);
						tLCInsuredSchema.setInsuredNo(tInsredNo[i]);
						tLCInsuredSchema.setRelationToMainInsured(tRelationtomain[i]);
						tLCInsuredSet.add(tLCInsuredSchema);
					}
				}
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Flag",request.getParameter("Flag"));
			tTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			tTransferData.setNameAndValue("MissionID",tMissionID);
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("Customerno",tCustomerno);
			tTransferData.setNameAndValue("CustomName",tCustomName);
			tTransferData.setNameAndValue("IDNO",tIDNO);
			tTransferData.setNameAndValue("CustomerID",tCustomerID);
			tTransferData.setNameAndValue("ContPlanCode",tContPlanCode);
			tTransferData.setNameAndValue("SysPlanCode",tSysPlanCode);
		
			VData tVData = new VData();
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCInsuredSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCInsuredDealUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "��������Ϣ�����ɹ���";
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
