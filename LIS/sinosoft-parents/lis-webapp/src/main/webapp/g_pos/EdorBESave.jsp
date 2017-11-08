<%
/***************************************************************
* <p>ProName：EdorBESave.jsp</p>
 * <p>Title：被保险人复效</p>
 * <p>Description：被保险人复效</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LBPOEdorInsuredListSet mLBPOEdorInsuredListSet = new LBPOEdorInsuredListSet();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");	
			String tEdorType = request.getParameter("EdorType");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorNo  = request.getParameter("EdorNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");

			if("UPDATE".equals(tOperate)){
			
				String tChk[]=request.getParameterValues("InpOldInsuredInfoGridChk");
				String OldInsuredName [] = request.getParameterValues("OldInsuredInfoGrid2");
				String OldInsuredIDNo [] = request.getParameterValues("OldInsuredInfoGrid8");
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
			
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
						mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
						mLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
						mLBPOEdorInsuredListSchema.setOldInsuredIDNo(OldInsuredIDNo[index]);
						mLBPOEdorInsuredListSchema.setOldInsuredName(OldInsuredName[index]);	
						mLBPOEdorInsuredListSchema.setInsuredIDNo(OldInsuredIDNo[index]);
						mLBPOEdorInsuredListSchema.setInsuredName(OldInsuredName[index]);	
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}										
				} 
							
			}else if("DELETE".equals(tOperate)){
				String tGrid1 [] = request.getParameterValues("UpdateInsuredInfoGrid1");
				String tChk[]=request.getParameterValues("InpUpdateInsuredInfoGridChk");
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){			
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
						mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
						mLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
						mLBPOEdorInsuredListSchema.setSerialNo(tGrid1 [index]);
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}										
				} 
			}
			
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			VData tVData = new VData();		
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(mLBPOEdorInsuredListSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorBEUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功!";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
