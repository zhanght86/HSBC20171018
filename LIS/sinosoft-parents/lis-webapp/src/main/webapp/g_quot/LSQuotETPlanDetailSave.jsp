<%
/***************************************************************
 * <p>ProName��LSQuotPlanDetailSave.jsp</p>
 * <p>Title��������ϸ��Ϣ����</p>
 * <p>Description��������ϸ��Ϣ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailSubSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailRelaSubSchema"%>
<%@page import="com.sinosoft.lis.g_quot.LSQuotPubFun"%>
<%@page import="com.sinosoft.utility.SQLwithBindVariables"%>
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
		
			tOperate = request.getParameter("Operate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tTranProdType = request.getParameter("TranProdType");
			String tPlanType = request.getParameter("PlanType");
			String tPremCalType = request.getParameter("PremCalType");
			String tPlanFlag = request.getParameter("PlanFlag");
			String tOccupTypeFlag = request.getParameter("OccupTypeFlag");
			
			LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
			LSQuotPlanDetailSchema tLSQuotPlanDetailSchema = new LSQuotPlanDetailSchema();
			LSQuotPlanDetailSubSchema tLSQuotPlanDetailSubSchema = new LSQuotPlanDetailSubSchema();
			LSQuotPlanDetailRelaSubSchema tLSQuotPlanDetailRelaSubSchema = new LSQuotPlanDetailRelaSubSchema();
			
			//��ȡ�̶���
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			String tAmntType = request.getParameter("AmntType");
			String tFixedAmnt = request.getParameter("FixedAmnt");
			String tSalaryMult = request.getParameter("SalaryMult");
			String tMaxAmnt = request.getParameter("MaxAmnt");
			String tMinAmnt = request.getParameter("MinAmnt");
			String tExceptPremType = request.getParameter("ExceptPremType");
			String tExceptPrem = request.getParameter("ExceptPrem");
			String tInitPrem = request.getParameter("InitPrem");
			String tExceptYield = request.getParameter("ExceptYield");
			String tRelaShareFlag = request.getParameter("RelaShareFlag");
			String tRelaToMain = request.getParameter("RelaToMain");
			String tMainAmntRate = request.getParameter("MainAmntRate");
			String tRelaAmntRate = request.getParameter("RelaAmntRate");
			
			if (!"1".equals(tRelaShareFlag)) {
			
				tRelaToMain = "";
				tMainAmntRate = "-1";
				tRelaAmntRate = "-1";
			}
			
			String tRemark = request.getParameter("Remark");
			
			tLSQuotPlanDetailSchema.setQuotNo(tQuotNo);
			tLSQuotPlanDetailSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPlanDetailSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotPlanDetailSchema.setPlanCode(tPlanCode);
			tLSQuotPlanDetailSchema.setRiskCode(tRiskCode);
			tLSQuotPlanDetailSchema.setDutyCode(tDutyCode);
			tLSQuotPlanDetailSchema.setAmntType(tAmntType);
			tLSQuotPlanDetailSchema.setFixedAmnt(tFixedAmnt);
			tLSQuotPlanDetailSchema.setSalaryMult(tSalaryMult);
			tLSQuotPlanDetailSchema.setMaxAmnt(tMaxAmnt);
			tLSQuotPlanDetailSchema.setMinAmnt(tMinAmnt);
			tLSQuotPlanDetailSchema.setExceptPremType(tExceptPremType);
			tLSQuotPlanDetailSchema.setExceptPrem(tExceptPrem);
			tLSQuotPlanDetailSchema.setInitPrem(tInitPrem);
			tLSQuotPlanDetailSchema.setExceptYield(tExceptYield);
			tLSQuotPlanDetailSchema.setRelaShareFlag(tRelaShareFlag);
			tLSQuotPlanDetailSchema.setRemark(tRemark);
			
			TransferData tTransferData = new TransferData();
			//��ѯ����̬���ֶ�,��ȡ��̬��ֵ
			if ("ADDPLANDETAIL".equals(tOperate) || "MODIFYPLANDETAIL".equals(tOperate)) {
			
				//select a.calfactor,b.factorcode,decode(b.factorname,null,a.factorname,'',a.factorname,b.factorname),b.fieldtype,b.valuetype,a.calsql,b.fieldlength from lmriskdutyfactor a,lmdutyfactorrelaconfig b where 1=1 and a.calfactor=b.calfactor {0} {1} order by a.factororder
				String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
										+" from lmdutyfactorrela a, lmfactorconfig b "
										+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='?tRiskCode?' and a.dutycode='?tDutyCode?' "
										+" order by a.factororder ";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tRiskCode", tRiskCode);
				sqlbv.put("tDutyCode", tDutyCode);
				tLSQuotPlanDetailSubSchema.setQuotNo(tQuotNo);
				tLSQuotPlanDetailSubSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotPlanDetailSubSchema.setSysPlanCode(tSysPlanCode);
				tLSQuotPlanDetailSubSchema.setPlanCode(tPlanCode);
				tLSQuotPlanDetailSubSchema.setRiskCode(tRiskCode);
				tLSQuotPlanDetailSubSchema.setDutyCode(tDutyCode);
				
				tLSQuotPlanDetailRelaSubSchema.setQuotNo(tQuotNo);
				tLSQuotPlanDetailRelaSubSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotPlanDetailRelaSubSchema.setSysPlanCode(tSysPlanCode);
				tLSQuotPlanDetailRelaSubSchema.setPlanCode(tPlanCode);
				tLSQuotPlanDetailRelaSubSchema.setRiskCode(tRiskCode);
				tLSQuotPlanDetailRelaSubSchema.setDutyCode(tDutyCode);
				tLSQuotPlanDetailRelaSubSchema.setRelaToMain(tRelaToMain);
				tLSQuotPlanDetailRelaSubSchema.setMainAmntRate(tMainAmntRate);
				tLSQuotPlanDetailRelaSubSchema.setRelaAmntRate(tRelaAmntRate);
				
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS==null || tSSRS.getMaxRow()==0) {
					tTransferData.setNameAndValue("DetailSubArr", null);
				} else {
					String[][] tArr = new String[tSSRS.getMaxRow()][2];
					for (int i=1;i<=tSSRS.getMaxRow(); i++) {
						
						String tFactorCode = tSSRS.GetText(i, 2);
						String tValue = "";
						if ("P16".equals(tFactorCode)) {//���Ѽ��㷽ʽ
							tValue = request.getParameter("PremCalWay");
						} else if ("P17".equals(tFactorCode)) {//�˾�����
							tValue = request.getParameter("PerPrem");
						} else if ("P18".equals(tFactorCode)) {//��׼�˾����� ��ʼĬ�� -1��������������
							tValue = "-1";
						} else  {
							tValue = request.getParameter(tFactorCode);
						}
						tLSQuotPlanDetailSubSchema.setV(tFactorCode, tValue);
						
						tArr[i-1][0] = tSSRS.GetText(i, 1);
						tArr[i-1][1] = tValue;
					}
					tTransferData.setNameAndValue("DetailSubArr", tArr);
					
					if ("1".equals(tRelaShareFlag)) {
      	
						for (int i=1;i<=tSSRS.getMaxRow(); i++) {
						
							String tFactorCode = tSSRS.GetText(i, 2);
							String tValue = request.getParameter("Relation"+tFactorCode);
							tLSQuotPlanDetailRelaSubSchema.setV(tFactorCode, tValue);
						}
					}
				}
			}
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSQuotPlanDetailSchema);
			tVData.add(tLSQuotPlanDetailSubSchema);
			tVData.add(tLSQuotPlanDetailRelaSubSchema);
			
			
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			tTransferData.setNameAndValue("PlanType", tPlanType);
			tTransferData.setNameAndValue("PremCalType", tPremCalType);
			tTransferData.setNameAndValue("PlanFlag", tPlanFlag);
			tTransferData.setNameAndValue("OccupTypeFlag", tOccupTypeFlag);
			tTransferData.setNameAndValue("ChangePremType", "N");
			
			if ("MODIFYPLANDETAIL".equals(tOperate)) {//�޸�ʱ��ȡԭ���ݼ�¼
		
				String tOSysPlanCode = request.getParameter("OSysPlanCode");
				String tOPlanCode = request.getParameter("OPlanCode");
				String tORiskCode = request.getParameter("ORiskCode");
				String tODutyCode = request.getParameter("ODutyCode");
				
				tTransferData.setNameAndValue("OSysPlanCode", tOSysPlanCode);
				tTransferData.setNameAndValue("OPlanCode", tOPlanCode);
				tTransferData.setNameAndValue("ORiskCode", tORiskCode);
				tTransferData.setNameAndValue("ODutyCode", tODutyCode);
			}
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPlanDetailUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
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
