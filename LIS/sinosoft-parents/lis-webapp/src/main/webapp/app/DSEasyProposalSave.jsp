<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//�������ƣ�ProposalSave.jsp
	//�����ܣ�
	//�������ڣ�2002-06-19 11:10:36
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//Modify by niuzj,2006-08-23,Ӣ����Ҫ��¼����������Ϣʱ����һ�����Ա��ֶ�
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//�������
	String FORMATMODOL = "0.00"; //���ѱ�����������ľ�ȷλ��
	DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //����ת������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String tAction = "";
	String tOldAction = "";
	String tOperate = "";
	String tFamilyType="";
	boolean ChangePlanFlag = false;
	tAction = request.getParameter("fmAction");
	tFamilyType = request.getParameter("FamilyType");
	String InputNo = "";
	InputNo = request.getParameter("InputNo");
	String tempInputTime="";
	tempInputTime=request.getParameter("InputTime");
	loggerDebug("DSEasyProposalSave","tempInputTime"+tempInputTime);
	if(tempInputTime.equals("0")||tempInputTime.equals("")||tempInputTime==null){
		tempInputTime="0";
	}
	int tInputTime = 0;
	tInputTime = Integer.parseInt(tempInputTime);
	tInputTime = tInputTime+1;
	InputNo = String.valueOf(tInputTime);
	loggerDebug("DSEasyProposalSave","InputNo: "+InputNo);
	int ttInputNo = 0;
	//ttInputNo = Integer.parseInt(InputNo);
	tAction = request.getParameter("fmAction");
	loggerDebug("DSEasyProposalSave","������:" + tAction);
	loggerDebug("DSEasyProposalSave","save....111111");
	LBPOPolSchema mLBPOPolSchema = new LBPOPolSchema();
	try {
		//DSProposalUI tDSProposalUI = new DSProposalUI();
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		VData tVData = new VData();

		tOldAction = tAction;
		loggerDebug("DSEasyProposalSave","�û�ѡ��Ĳ���ΪtAction:" + tAction);

		//��ӡˢ��
		loggerDebug("DSEasyProposalSave","tOldAction.indexOf_Modify:"
		+ tOldAction.indexOf("Modify"));
		loggerDebug("DSEasyProposalSave","GrpContNo:"
		+ request.getParameter("GrpContNo"));
		if (request.getParameter("GrpContNo").equals(
		"00000000000000000000")
		&& tOldAction.indexOf("Modify") == -1) {
			LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
			tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
			tLDSysTraceSchema.setCreatePos("�б�¼��");
			tLDSysTraceSchema.setPolState("1002");
			LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
			inLDSysTraceSet.add(tLDSysTraceSchema);
			VData VData3 = new VData();
			VData3.add(tG);
			VData3.add(inLDSysTraceSet);

			String busiName="pubfunLockTableUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
			//LockTableUI LockTableUI1 = new LockTableUI();
			if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName)) {
		VData rVData = tBusinessDelegate.getResult();
		loggerDebug("DSEasyProposalSave","LockTable Failed! "
				+ (String) rVData.get(0));
			} else {
		loggerDebug("DSEasyProposalSave","LockTable Succed!");
			}
		}

		LBPOBnfSet tLBPOBnfSet = new LBPOBnfSet();

		String tBnfNum[] = request.getParameterValues("BnfGridNo");
		String tInsuredType[] = request.getParameterValues("BnfGrid1");
		String tBnfType[] = request.getParameterValues("BnfGrid2");
		String tName[] = request.getParameterValues("BnfGrid3");
		String tSex[] = request.getParameterValues("BnfGrid4");
		String tIDNo[] = request.getParameterValues("BnfGrid5");
		String tBnfRelationToInsured[] = request
		.getParameterValues("BnfGrid6");
		String tBnfLot[] = request.getParameterValues("BnfGrid7");
		String tBnfGrade[] = request.getParameterValues("BnfGrid8");
		String tBnfAddress[] = request.getParameterValues("BnfGrid9");
		String tBnfFillNo[] = request.getParameterValues("BnfGrid10");
		String tInsuredNo[] = request.getParameterValues("BnfGrid11");
		String tMainPolNo[] = request.getParameterValues("BnfGrid12");
		
		double tBnfCount = 0;
		double tRateCount = 0;
		double tRateCount1 = 0;
		double tRateCount2 = 0;
		double tRateCount3 = 0;
		double tRateCount4 = 0;
		double tRateCount5 = 0;
		int BnfCount = 0;
		if (tBnfNum != null)
			BnfCount = tBnfNum.length;
		loggerDebug("DSEasyProposalSave",BnfCount);
		for (int i = 0; i < BnfCount; i++) {
			loggerDebug("DSEasyProposalSave",tBnfAddress[i]);
			//if (tName[i] == null || tName[i].equals(""))
		//break;

			LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
			
			tLBPOBnfSchema.setContNo(request.getParameter("ContNo"));
			tLBPOBnfSchema.setPolNo(tMainPolNo[i]);
			if(tFamilyType.equals("1")){
				tLBPOBnfSchema.setBnfType("1");
			}else{
				tLBPOBnfSchema.setBnfType(tBnfType[i]);
			}
			tLBPOBnfSchema.setInputNo(InputNo);
			tLBPOBnfSchema.setName(tName[i]);
			tLBPOBnfSchema.setSex(tSex[i]);
			//tLBPOBnfSchema.setIDType(tIDType[i]);
			tLBPOBnfSchema.setIDNo(tIDNo[i]);
			tLBPOBnfSchema.setFillNo(tBnfFillNo[i]);
			loggerDebug("DSEasyProposalSave","������������ǣ�"+tInsuredType[i]);
			tLBPOBnfSchema.setInsuredNo(tInsuredNo[i]);
			tLBPOBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
			tLBPOBnfSchema.setBnfLot(tBnfLot[i]);
			tLBPOBnfSchema.setBnfGrade(tBnfGrade[i]);
			tLBPOBnfSchema.setBnfAddress(tBnfAddress[i]);
			loggerDebug("DSEasyProposalSave","tLBPOBnfSchema.Encode="
			+ tLBPOBnfSchema.encode());
			tLBPOBnfSet.add(tLBPOBnfSchema);

		}
		loggerDebug("DSEasyProposalSave","end set schema ��������Ϣ...");

		LBPOPolSet tLBPOPolSet = new LBPOPolSet();
		String tPolNum[] = request.getParameterValues("PolGridNo");
		String tRisksequence[] = request.getParameterValues("PolGrid1");
		String tRiskcode[] = request.getParameterValues("PolGrid3");
		String tAmnt[] = request.getParameterValues("PolGrid4");
		String tMult[] = request.getParameterValues("PolGrid5");
		String tStandByFlag1[] = request.getParameterValues("PolGrid6");
		String tInsuyear[] = request.getParameterValues("PolGrid7");
		String tPayyears[] = request.getParameterValues("PolGrid8");
		String tPrem[] = request.getParameterValues("PolGrid9");
		String tStandbyflag2[] = request.getParameterValues("PolGrid10");
		String tRemark[] = request.getParameterValues("PolGrid11");
		String tPolNo[] = request.getParameterValues("PolGrid12");
		String tFillNo[] = request.getParameterValues("PolGrid13");
		String ttInsuredNo[] = request.getParameterValues("PolGrid14");
		String ttMainPolNo[] = request.getParameterValues("PolGrid15");
		int POLCount = 0;
		if (tPolNum != null)
			POLCount = tPolNum.length;
		loggerDebug("DSEasyProposalSave","���ָ�����"+POLCount);
		for (int j = 0; j < POLCount; j++) {
			//if (tRiskcode[j] == null || tRiskcode[j].equals(""))
			//break;
			LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
			tLBPOPolSchema.setRiskCode(tRiskcode[j]);
			tLBPOPolSchema.setGrpContNo("00000000000000000000");
			tLBPOPolSchema.setGrpPolNo("00000000000000000000");
			tLBPOPolSchema.setContNo(request.getParameter("ContNo"));
			tLBPOPolSchema.setInputNo(InputNo);
			tLBPOPolSchema.setPolNo(tPolNo[j]);
			tLBPOPolSchema.setProposalNo(request
			.getParameter("PrtNo"));
			tLBPOPolSchema.setPrtNo(request.getParameter("PrtNo"));
			tLBPOPolSchema.setManageCom(request
			.getParameter("ManageCom"));
			tLBPOPolSchema.setInsuredNo(ttInsuredNo[j]);
			tLBPOPolSchema.setAppntNo(request.getParameter("AppntNo"));
			tLBPOPolSchema.setAmnt(tAmnt[j]);
			tLBPOPolSchema.setRiskAmnt(tAmnt[j]);
			tLBPOPolSchema.setPrem(tPrem[j]);
			tLBPOPolSchema.setStandPrem(tPrem[j]);
			tLBPOPolSchema.setSumPrem(tPrem[j]);
			tLBPOPolSchema.setMult(tMult[j]);
			tLBPOPolSchema.setStandbyFlag1(tStandByFlag1[j]);
			tLBPOPolSchema.setInsuYear(tInsuyear[j]);
			tLBPOPolSchema.setPayYears(tPayyears[j]);
			tLBPOPolSchema.setStandbyFlag2(tStandbyflag2[j]);
			tLBPOPolSchema.setRemark(tRemark[j]);
			tLBPOPolSchema.setRiskSequence(tRisksequence[j]);
			tLBPOPolSchema.setFillNo(tFillNo[j]);
			tLBPOPolSchema.setMainPolNo(ttMainPolNo[j]);
			loggerDebug("DSEasyProposalSave","======================"+tRisksequence[j]);
			tLBPOPolSet.add(tLBPOPolSchema);
		}

		//���ݷ�SCHEMA��Ϣ
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("LiveGetMode", request.getParameter("LiveGetMode")); //�������ʽ
		tTransferData.setNameAndValue("BonusGetMode", request.getParameter("BonusGetMode")); //������ȡ��ʽ
		tTransferData.setNameAndValue("SequenceNo3", request.getParameter("SequenceNo3")); //����������
		tTransferData.setNameAndValue("GetYear", request.getParameter("GetYear")); //���ʼ��ȡ����
		tTransferData.setNameAndValue("GetLimit", request.getParameter("GetLimit")); //��ȡ����
		tTransferData.setNameAndValue("StandbyFlag3", request.getParameter("StandbyFlag3")); //��ȡ��ʽ
		tTransferData.setNameAndValue("InputNo",InputNo); //��ȡ��ʽ
		tTransferData.setNameAndValue("ContNo",request.getParameter("PrtNo")); //��ȡ��ʽ
		

		loggerDebug("DSEasyProposalSave","getIntv=" + request.getParameter("getIntv"));
		loggerDebug("DSEasyProposalSave","GetDutyKind="
		+ request.getParameter("GetDutyKind"));

		loggerDebug("DSEasyProposalSave","�Ƿ�Ϊ��ͥ��"+request.getParameter("FamilyType"));
		tTransferData.setNameAndValue("FamilyType", request.getParameter("FamilyType"));
		tVData.addElement(tLBPOPolSet);
		tVData.addElement(tLBPOBnfSet);
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		// ���ݴ���
		//DSProposalUI tDSProposalUI = new DSProposalUI();
		String tbusiName="tbDSProposalUI";
        BusinessDelegate ttBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (!ttBusinessDelegate.submitData(tVData, tOperate,tbusiName)) {
			tError = ttBusinessDelegate.getCErrors();
			Content = tError.getFirstError();
			FlagStr = "Fail";
			loggerDebug("DSEasyProposalSave","Content:" + Content);
		} else {
			Content = "����ɹ�!";
			FlagStr = "Succ";
			loggerDebug("DSEasyProposalSave","Content:" + Content);
			tVData.clear();
			tVData = ttBusinessDelegate.getResult();
		}
	} catch (Exception e1) {
		Content = " ����ʧ�ܣ�ԭ����: " + e1.toString().trim();
		FlagStr = "Fail";
		loggerDebug("DSEasyProposalSave",Content);
	}
%>

<html>
<script language="javascript">
              try
              {
                parent.fraInterface.afterSubmit4('<%=FlagStr%>','<%=Content%>');
              }  catch(ex) {
                //alert("after Save but happen err:" + ex);
              }
              </script>

</html>

