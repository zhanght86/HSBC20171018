<%
/***************************************************************
 * <p>ProName：LSQuotETBasicSave.jsp</p>
 * <p>Title：一般询价基本信息录入</p>
 * <p>Description：一般询价基本信息录入</p>
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
<%@page import="com.sinosoft.lis.schema.LSQuotBasicSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotAgencySchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotAgencySet"%>
<%@page import="com.sinosoft.lis.schema.LSQuotRelaCustomerSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotSaleChnlSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotRelaCustomerSet"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotPlanDivSet"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDivSchema"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			
			String tPreCustomerNo = request.getParameter("PreCustomerNo");
			String tPreCustomerName = request.getParameter("PreCustomerName");
			String tIDType = request.getParameter("IDType");
			String tIDNo = request.getParameter("IDNo");
			String tGrpNature = request.getParameter("GrpNature");
			String tBusiCategory = request.getParameter("BusiCategory");
			String tAddress = request.getParameter("Address");
			String tProdType = request.getParameter("ProdType");
			String tSaleChannel = request.getParameter("SaleChannel");
			String tPremMode = request.getParameter("PremMode");
			String tPrePrem = request.getParameter("PrePrem");
			String tRenewFlag = request.getParameter("RenewFlag");
			String tBlanketFlag = request.getParameter("BlanketFlag");
			String tPayIntv = request.getParameter("PayIntv");
			String tInsuPeriod = request.getParameter("InsuPeriod");
			String tInsuPeriodFlag = request.getParameter("InsuPeriodFlag");
			String tCoinsurance = request.getParameter("Coinsurance");
			String tValDateType = request.getParameter("ValDateType");
			String tAppointValDate = request.getParameter("AppointValDate");
			String tCustomerIntro = request.getParameter("CustomerIntro");
			
			String tElasticFlag = "0";//是否为弹性询价
			if ("00".equals(tProdType)) {
			
				tElasticFlag = request.getParameter("ElasticFlag");
			}
			//TransferData tTransferData = new TransferData();
			//tTransferData.setNameAndValue("QuotType", tQuotType);
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LSQuotBasicSchema tLSQuotBasicSchema = new LSQuotBasicSchema();
			LSQuotSaleChnlSchema tLSQuotSaleChnlSchema = new LSQuotSaleChnlSchema();
			LSQuotAgencySet tLSQuotAgencySet = new LSQuotAgencySet();
			LSQuotRelaCustomerSet tLSQuotRelaCustomerSet = new LSQuotRelaCustomerSet();
			LSQuotPlanDivSet tLSQuotPlanDivSet = new LSQuotPlanDivSet();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			tLSQuotBasicSchema.setQuotNo(tQuotNo);
			tLSQuotBasicSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotBasicSchema.setPreCustomerNo(tPreCustomerNo);
			tLSQuotBasicSchema.setPreCustomerName(tPreCustomerName);
			tLSQuotBasicSchema.setIDType(tIDType);
			tLSQuotBasicSchema.setIDNo(tIDNo);
			tLSQuotBasicSchema.setGrpNature(tGrpNature);
			tLSQuotBasicSchema.setBusiCategory(tBusiCategory);
			tLSQuotBasicSchema.setAddress(tAddress);
			tLSQuotBasicSchema.setProdType(tProdType);
			//tLSQuotBasicSchema.setSaleChannel(tSaleChannel);
			tLSQuotBasicSchema.setPremMode(tPremMode);
			tLSQuotBasicSchema.setPrePrem(tPrePrem);
			tLSQuotBasicSchema.setRenewFlag(tRenewFlag);
			tLSQuotBasicSchema.setBlanketFlag(tBlanketFlag);
			tLSQuotBasicSchema.setElasticFlag(tElasticFlag);
			tLSQuotBasicSchema.setPayIntv(tPayIntv);
			tLSQuotBasicSchema.setInsuPeriod(tInsuPeriod);
			tLSQuotBasicSchema.setInsuPeriodFlag(tInsuPeriodFlag);
			tLSQuotBasicSchema.setCoinsurance(tCoinsurance);
			tLSQuotBasicSchema.setValDateType(tValDateType);
			tLSQuotBasicSchema.setAppointValDate(tAppointValDate);
			tLSQuotBasicSchema.setCustomerIntro(tCustomerIntro);
			
			tLSQuotSaleChnlSchema.setQuotNo(tQuotNo);
			tLSQuotSaleChnlSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotSaleChnlSchema.setSaleChannel(tSaleChannel);
			
			String tAgencyNoArr[] = request.getParameterValues("AgencyListGridNo");//处理中介机构信息
			if (tAgencyNoArr!=null && tAgencyNoArr.length>0) {
			
				String tAgencyCode[] = request.getParameterValues("AgencyListGrid2");
				String tAgencyName[] = request.getParameterValues("AgencyListGrid1");
				for (int i=0; i<tAgencyNoArr.length; i++) {
				
					if (tAgencyName[i]!=null && !"".equals(tAgencyName[i])) {
				
						LSQuotAgencySchema tLSQuotAgencySchema = new LSQuotAgencySchema();
						tLSQuotAgencySchema.setQuotNo(tQuotNo);
						tLSQuotAgencySchema.setQuotBatNo(tQuotBatNo);
						tLSQuotAgencySchema.setSerialNo(i+1);
						tLSQuotAgencySchema.setAgencyName(tAgencyName[i]);
						tLSQuotAgencySchema.setAgencyCode(tAgencyCode[i]);
						
						tLSQuotAgencySet.add(tLSQuotAgencySchema);
					}
				}
			}
			
			String tRelaCustomerFlag = request.getParameter("RelaCustomerFlag");//处理其他准客户信息
			if (tRelaCustomerFlag!=null && "on".equals(tRelaCustomerFlag)) {
				
				String tRealCustNoArr[] = request.getParameterValues("RelaCustListGridNo");//处理其他准客户信息
				if (tRealCustNoArr!=null && tRealCustNoArr.length>0) {
				
					String tOtherPreCustomerNo[] = request.getParameterValues("RelaCustListGrid1");
					String tOtherPreCustomerName[] = request.getParameterValues("RelaCustListGrid2");
					for (int i=0; i<tRealCustNoArr.length; i++) {
					
						if (tOtherPreCustomerNo[i]!=null && !"".equals(tOtherPreCustomerNo[i])) {
					
							LSQuotRelaCustomerSchema tLSQuotRelaCustomerSchema = new LSQuotRelaCustomerSchema();
							tLSQuotRelaCustomerSchema.setQuotNo(tQuotNo);
							tLSQuotRelaCustomerSchema.setQuotBatNo(tQuotBatNo);
							tLSQuotRelaCustomerSchema.setPreCustomerNo(tOtherPreCustomerNo[i]);
							tLSQuotRelaCustomerSchema.setPreCustomerName(tOtherPreCustomerName[i]);
      	
							tLSQuotRelaCustomerSet.add(tLSQuotRelaCustomerSchema);
						}
					}
				}
			}

			String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='quotplandiv'";
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
					System.out.print(tElementCode+"----------------------------------");
					String isChecked = request.getParameter(tElementCode);
					System.out.print(isChecked+"----------------------------------");
					if (isChecked!=null && "on".equals(isChecked)) {
						
						LSQuotPlanDivSchema tLSQuotPlanDivSchema = new LSQuotPlanDivSchema();
						tLSQuotPlanDivSchema.setQuotNo(tQuotNo);
						tLSQuotPlanDivSchema.setQuotBatNo(tQuotBatNo);
						tLSQuotPlanDivSchema.setDivType(tSSRS.GetText(i, 2));
						tLSQuotPlanDivSchema.setOtherDesc(tElementValue);

						tLSQuotPlanDivSet.add(tLSQuotPlanDivSchema);
					}
				}
			}

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSQuotBasicSchema);
			tVData.add(tLSQuotSaleChnlSchema);
			tVData.add(tLSQuotAgencySet);
			tVData.add(tLSQuotRelaCustomerSet);
			tVData.add(tLSQuotPlanDivSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotETBasicUI")) {
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
