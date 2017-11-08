<%
/***************************************************************
 * <p>ProName：LSQuotETEnginSave.jsp</p>
 * <p>Title：一般询价工程信息保存</p>
 * <p>Description：一般询价工程信息保存</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotEngineeringSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotEnginDetailSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotEnginDetailSet"%>
<%@page import="com.sinosoft.lis.g_quot.LSQuotPubFun"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
			
			tOperate = request.getParameter("Operate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tTranProdType = request.getParameter("TranProdType");
			
			String tEnginName = request.getParameter("EnginName");
			String tEnginType = request.getParameter("EnginType");
			String tEnginArea = tLSQuotPubFun.isNumNull(request.getParameter("EnginArea"));
			String tEnginCost = tLSQuotPubFun.isNumNull(request.getParameter("EnginCost"));
			String tEnginPlace = request.getParameter("EnginPlace");
			String tEnginStartDate = request.getParameter("EnginStartDate");
			String tEnginEndDate = request.getParameter("EnginEndDate");
			String tEnginDesc = request.getParameter("EnginDesc");
			String tEnginCondition = request.getParameter("EnginCondition");
			String tRemark = request.getParameter("Remark");
			String tInsurerName = request.getParameter("InsurerName");
			String tInsurerType = request.getParameter("InsurerType");
			String tContractorName = request.getParameter("ContractorName");
			String tContractorType = request.getParameter("ContractorType");
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LSQuotEngineeringSchema tLSQuotEngineeringSchema = new LSQuotEngineeringSchema();
			
			LSQuotEnginDetailSet tLSQuotEnginDetailSet = new LSQuotEnginDetailSet();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			tLSQuotEngineeringSchema.setQuotNo(tQuotNo);
			tLSQuotEngineeringSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotEngineeringSchema.setEnginName(tEnginName);
			tLSQuotEngineeringSchema.setEnginType(tEnginType);
			tLSQuotEngineeringSchema.setEnginArea(tEnginArea);
			tLSQuotEngineeringSchema.setEnginCost(tEnginCost);
			tLSQuotEngineeringSchema.setMinEnginArea(-1);
			tLSQuotEngineeringSchema.setMinEnginCost(-1);
			tLSQuotEngineeringSchema.setEnginPlace(tEnginPlace);
			tLSQuotEngineeringSchema.setEnginStartDate(tEnginStartDate);
			tLSQuotEngineeringSchema.setEnginEndDate(tEnginEndDate);
			tLSQuotEngineeringSchema.setEnginDesc(tEnginDesc);
			tLSQuotEngineeringSchema.setEnginCondition(tEnginCondition);
			tLSQuotEngineeringSchema.setRemark(tRemark);
			tLSQuotEngineeringSchema.setInsurerName(tInsurerName);
			tLSQuotEngineeringSchema.setInsurerType(tInsurerType);
			tLSQuotEngineeringSchema.setContractorName(tContractorName);
			tLSQuotEngineeringSchema.setContractorType(tContractorType);
			
			String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='engindetail'";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			
			if (tSSRS==null || tSSRS.getMaxRow()==0) {
				tContent = "获取基础信息失败！";
				tFlagStr = "Fail";
			} else {
				
				for (int i=1; i<=tSSRS.getMaxRow(); i++) {
				
					String tElementCode = tSSRS.GetText(i, 1)+tSSRS.GetText(i, 2);
					String tElementValue = request.getParameter(tElementCode +"Value");
					
					String isChecked = request.getParameter(tElementCode);
					
					if (isChecked!=null && "on".equals(isChecked)) {
						
						LSQuotEnginDetailSchema tLSQuotEnginDetailSchema = new LSQuotEnginDetailSchema();
						tLSQuotEnginDetailSchema.setQuotNo(tQuotNo);
						tLSQuotEnginDetailSchema.setQuotBatNo(tQuotBatNo);
						tLSQuotEnginDetailSchema.setEnginCode(tSSRS.GetText(i, 2));
						tLSQuotEnginDetailSchema.setOtherDesc(tElementValue);

						tLSQuotEnginDetailSet.add(tLSQuotEnginDetailSchema);
					}
				}
			}

			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSQuotEngineeringSchema);
			tVData.add(tLSQuotEnginDetailSet);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPlanUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
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
