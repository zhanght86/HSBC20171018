<%
/***************************************************************
 * <p>ProName��LSQuotProjBasicSave.jsp</p>
 * <p>Title����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Description����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-28
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSProjQuotBasicSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDivSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotPlanDivSet"%>
<%@page import="com.sinosoft.lis.schema.LSQuotAgencySchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotAgencySet"%>
<%@page import="com.sinosoft.lis.schema.LSProjQuotPayIntvSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSProjQuotPayIntvSet"%>
<%@page import="com.sinosoft.lis.schema.LSProjQuotComSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSProjQuotComSet"%>
<%@page import="com.sinosoft.lis.schema.LSQuotSaleChnlSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotSaleChnlSet"%>
<%@page import="com.sinosoft.lis.vschema.LSProjQuotRelaQuotSet"%>
<%@page import="com.sinosoft.lis.schema.LSProjQuotRelaQuotSchema"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");//ѯ�ۺ�
			String tQuotBatNo = request.getParameter("QuotBatNo");//���κ�
			String tQuotType = request.getParameter("QuotType");//��Ŀ����
			
			String tProjName = request.getParameter("ProjName");//��Ŀ����
			String tTargetCust = request.getParameter("TargetCust");//Ŀ��ͻ�
			String tNumPeople = request.getParameter("NumPeople");//������������
			String tPrePrem = request.getParameter("PrePrem");//ҵ���ģ(Ԫ)
			String tPreLossRatio = request.getParameter("PreLossRatio");//Ԥ���⸶��(%)
			String tPartner = request.getParameter("Partner");//������
			String tExpiryDate = request.getParameter("ExpiryDate");//��Чֹ��
			String tProdType = request.getParameter("ProdType");//��Ʒ����
			String tBlanketFlag = request.getParameter("BlanketFlag");//�Ƿ�Ϊͳ����
			String tProjDesc = request.getParameter("ProjDesc");//��Ŀ˵��
			
			String tElasticFlag = "0";//�Ƿ�Ϊ����ѯ��
			if ("00".equals(tProdType)) {
			
				tElasticFlag = request.getParameter("ElasticFlag");
			}
			
			String tLinkProNo = request.getParameter("LinkProNo");////�Ƿ�ѡ����������Ŀѯ�ۺ� 0-δ��ѡ
			
			//TransferData tTransferData = new TransferData();
			//tTransferData.setNameAndValue("QuotType", tQuotType);
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LSProjQuotBasicSchema tLSProjQuotBasicSchema = new LSProjQuotBasicSchema();
			LSQuotPlanDivSet tLSQuotPlanDivSet = new LSQuotPlanDivSet();
			LSProjQuotPayIntvSet tLSProjQuotPayIntvSet = new LSProjQuotPayIntvSet();
			LSProjQuotComSet tLSProjQuotComSet = new   LSProjQuotComSet();
			LSQuotSaleChnlSet tLSQuotSaleChnlSet = new LSQuotSaleChnlSet();
			LSQuotAgencySet tLSQuotAgencySet = new LSQuotAgencySet();
			LSProjQuotRelaQuotSet tLSProjQuotRelaQuotSet = new LSProjQuotRelaQuotSet();
			
			//������
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			//��Ŀ��ѯ�ۻ�����Ϣ��
			tLSProjQuotBasicSchema.setQuotNo(tQuotNo);
			tLSProjQuotBasicSchema.setQuotBatNo(tQuotBatNo);
			tLSProjQuotBasicSchema.setProjName(tProjName);
			tLSProjQuotBasicSchema.setTargetCust(tTargetCust);
			tLSProjQuotBasicSchema.setNumPeople(tNumPeople);
			tLSProjQuotBasicSchema.setPrePrem(tPrePrem);
			tLSProjQuotBasicSchema.setPreLossRatio(tPreLossRatio);
			tLSProjQuotBasicSchema.setPartner(tPartner);
			tLSProjQuotBasicSchema.setExpiryDate(tExpiryDate);
			tLSProjQuotBasicSchema.setProdType(tProdType);
			tLSProjQuotBasicSchema.setBlanketFlag(tBlanketFlag);
			tLSProjQuotBasicSchema.setElasticFlag(tElasticFlag);
			tLSProjQuotBasicSchema.setProjDesc(tProjDesc);
			
			//���ϲ㼶���ֱ�׼
			String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='quotplandiv'";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			
			if (tSSRS==null || tSSRS.getMaxRow()==0) {
				tContent = "��ȡ������Ϣʧ�ܣ�";
				tFlagStr = "Fail";
			} else {
				
				for (int i=1; i<=tSSRS.getMaxRow(); i++) {
				
					String tElementCode = tSSRS.GetText(i, 1)+tSSRS.GetText(i, 2);
					String tElementValue = request.getParameter(tElementCode +"Value");
					
					String isChecked = request.getParameter(tElementCode);
					
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
			
			//�ɷѷ�ʽ
			String tPayIntvSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='payintv' order by a.orderno,a.code ";
			SSRS tPayIntvSSRS = new SSRS();
			tPayIntvSSRS = tExeSQL.execSQL(tPayIntvSql);
			
			if (tPayIntvSSRS==null || tPayIntvSSRS.getMaxRow()==0) {
				tContent = "��ȡ������Ϣʧ�ܣ�";
				tFlagStr = "Fail";
			} else {
				
				for (int i=1; i<=tPayIntvSSRS.getMaxRow(); i++) {
				
					String tElementCode = tPayIntvSSRS.GetText(i, 1)+tPayIntvSSRS.GetText(i, 2);
					String tElementValue = request.getParameter(tElementCode +"Value");
					
					String isChecked = request.getParameter(tElementCode);
					
					if (isChecked!=null && "on".equals(isChecked)) {
					
						LSProjQuotPayIntvSchema tLSProjQuotPayIntvSchema = new LSProjQuotPayIntvSchema();
						tLSProjQuotPayIntvSchema.setQuotNo(tQuotNo);
						tLSProjQuotPayIntvSchema.setQuotBatNo(tQuotBatNo);
						tLSProjQuotPayIntvSchema.setPayIntv(tPayIntvSSRS.GetText(i, 2));
						
						tLSProjQuotPayIntvSet.add(tLSProjQuotPayIntvSchema);
					}
				}
			}
			
			//���û���
			String tProjQuotComArr[] = request.getParameterValues("AppOrgCodeGridNo");
			if (tProjQuotComArr!=null && tProjQuotComArr.length>0) {
			
				String tComCode[] = request.getParameterValues("AppOrgCodeGrid1");
				String tComName[] = request.getParameterValues("AppOrgCodeGrid2");
				for (int i=0; i<tProjQuotComArr.length; i++) {
				
					if (tComCode[i]!=null && !"".equals(tComCode[i])) {
					
						LSProjQuotComSchema tLSProjQuotComSchema = new LSProjQuotComSchema();
						tLSProjQuotComSchema.setQuotNo(tQuotNo);
						tLSProjQuotComSchema.setQuotBatNo(tQuotBatNo);
						tLSProjQuotComSchema.setManageCom(tComCode[i]);

						tLSProjQuotComSet.add(tLSProjQuotComSchema);
					}
				}
			}
			
			//��������
			String tChannelSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='salechannel'";
			SSRS tChannelSSRS = new SSRS();
			tChannelSSRS = tExeSQL.execSQL(tChannelSql);
			String tempChanNo = "";
			
			if (tChannelSSRS==null || tChannelSSRS.getMaxRow()==0) {
				tContent = "��ȡ������Ϣʧ�ܣ�";
				tFlagStr = "Fail";
			} else {
				
				for (int i=1; i<=tChannelSSRS.getMaxRow(); i++) {
				
					String tElementCode = tChannelSSRS.GetText(i, 1)+tChannelSSRS.GetText(i, 2);
					String tElementValue = request.getParameter(tElementCode +"Value");
					
					String isChecked = request.getParameter(tElementCode);
					
					if (isChecked!=null && "on".equals(isChecked)) {
					
						tempChanNo += "'"+tChannelSSRS.GetText(i, 2)+"',";
						
						LSQuotSaleChnlSchema tLSQuotSaleChnlSchema = new LSQuotSaleChnlSchema();
						tLSQuotSaleChnlSchema.setQuotNo(tQuotNo);
						tLSQuotSaleChnlSchema.setQuotBatNo(tQuotBatNo);
						tLSQuotSaleChnlSchema.setSaleChannel(tChannelSSRS.GetText(i, 2));

						tLSQuotSaleChnlSet.add(tLSQuotSaleChnlSchema);
					}
				}
				
				if (tempChanNo!=null && !"".equals(tempChanNo)){
					tempChanNo = tempChanNo.substring(0,tempChanNo.length()-1);
				}
			}
			
			
			//�н�����
			String tSelectedSQL = "select count(1) from ldcode a where a.codetype='salechannel' and a.othersign like '1%' and a.code in ("+"'?tempChanNo?'"+")";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSelectedSQL);
			sqlbv1.put("tempChanNo",tempChanNo);
			String tAgentMark = tExeSQL.getOneValue(sqlbv1);
			if (tAgentMark!=null && !"0".equals(tAgentMark)){//������0��֤����ѡ����Ҫչʾ�н����Ƶ���������
				
				String tAgencyNoArr[] = request.getParameterValues("AgencyNameGridNo");
				if (tAgencyNoArr!=null && tAgencyNoArr.length>0) {
				
					String tAgencyCode[] = request.getParameterValues("AgencyNameGrid1");
					String tAgencyName[] = request.getParameterValues("AgencyNameGrid2");
					for (int i=0; i<tAgencyNoArr.length; i++) {
					
						if (tAgencyName[i]!=null && !"".equals(tAgencyName[i])) {
					
							LSQuotAgencySchema tLSQuotAgencySchema = new LSQuotAgencySchema();
							tLSQuotAgencySchema.setQuotNo(tQuotNo);
							tLSQuotAgencySchema.setQuotBatNo(tQuotBatNo);
							tLSQuotAgencySchema.setAgencyCode(tAgencyCode[i]);
							tLSQuotAgencySchema.setSerialNo(i+1);
							tLSQuotAgencySchema.setAgencyName(tAgencyName[i]);
							
							tLSQuotAgencySet.add(tLSQuotAgencySchema);
						}
					}
				}	
			}
			
			//����ѯ�ۺ�
			if (tLinkProNo!=null && "1".equals(tLinkProNo)) {
				String tRelaQuotNoArr[] = request.getParameterValues("LinkInquiryNoGridNo");
				if (tRelaQuotNoArr!=null && tRelaQuotNoArr.length>0) {
				
					String tRelaQuotNo[] = request.getParameterValues("LinkInquiryNoGrid1");
					for (int i=0; i<tRelaQuotNoArr.length; i++) {
					
						if (tRelaQuotNo[i]!=null && !"".equals(tRelaQuotNo[i])) {
					
							LSProjQuotRelaQuotSchema tLSProjQuotRelaQuotSchema = new LSProjQuotRelaQuotSchema();
							tLSProjQuotRelaQuotSchema.setQuotNo(tQuotNo);
							tLSProjQuotRelaQuotSchema.setQuotBatNo(tQuotBatNo);
							tLSProjQuotRelaQuotSchema.setRelaQuotNo(tRelaQuotNo[i]);
							
							tLSProjQuotRelaQuotSet.add(tLSProjQuotRelaQuotSchema);
						}
					}
				}	
			}
			
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSProjQuotBasicSchema);
			tVData.add(tLSQuotPlanDivSet);
			tVData.add(tLSProjQuotPayIntvSet);
			tVData.add(tLSProjQuotComSet);
			tVData.add(tLSQuotSaleChnlSet);
			tVData.add(tLSQuotAgencySet);
			tVData.add(tLSProjQuotRelaQuotSet);

		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotProjBasicUI")) {
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
