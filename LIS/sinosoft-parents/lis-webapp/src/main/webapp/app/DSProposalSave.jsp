<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//程序名称：ProposalSave.jsp
	//程序功能：
	//创建日期：2002-06-19 11:10:36
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
	//Modify by niuzj,2006-08-23,英大需要在录入受益人信息时增加一个“性别”字段
%>
<!--用户校验类-->
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
	//输出参数
	String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
	DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //数字转换对象
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
	loggerDebug("DSProposalSave","tempInputTime"+tempInputTime);
	if(tempInputTime.equals("0")||tempInputTime.equals("")||tempInputTime==null){
		tempInputTime="0";
	}
	int tInputTime = 0;
	tInputTime = Integer.parseInt(tempInputTime);
	tInputTime = tInputTime+1;
	InputNo = String.valueOf(tInputTime);
	loggerDebug("DSProposalSave","InputNo: "+InputNo);
	int ttInputNo = 0;
	//ttInputNo = Integer.parseInt(InputNo);
	tAction = request.getParameter("fmAction");
	loggerDebug("DSProposalSave","动作是:" + tAction);
	loggerDebug("DSProposalSave","save....111111");
	LBPOPolSchema mLBPOPolSchema = new LBPOPolSchema();
	try {
		//DSProposalUI tDSProposalUI = new DSProposalUI();
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		VData tVData = new VData();

		tOldAction = tAction;
		loggerDebug("DSProposalSave","用户选择的操作为tAction:" + tAction);

		//锁印刷号
		loggerDebug("DSProposalSave","tOldAction.indexOf_Modify:"
		+ tOldAction.indexOf("Modify"));
		loggerDebug("DSProposalSave","GrpContNo:"
		+ request.getParameter("GrpContNo"));
		if (request.getParameter("GrpContNo").equals(
		"00000000000000000000")
		&& tOldAction.indexOf("Modify") == -1) {
			LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
			tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
			tLDSysTraceSchema.setCreatePos("承保录单");
			tLDSysTraceSchema.setPolState("1002");
			LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
			inLDSysTraceSet.add(tLDSysTraceSchema);
			VData VData3 = new VData();
			VData3.add(tG);
			VData3.add(inLDSysTraceSet);

			//LockTableUI LockTableUI1 = new LockTableUI();
			String busiName="pubfunLockTableUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName)) {
		VData rVData = tBusinessDelegate.getResult();
		loggerDebug("DSProposalSave","LockTable Failed! "
				+ (String) rVData.get(0));
			} else {
		loggerDebug("DSProposalSave","LockTable Succed!");
			}
		}

		LBPOBnfSet tLBPOBnfSet = new LBPOBnfSet();

		String tBnfNum[] = request.getParameterValues("BnfGridNo");
		String tInsuredType[] = request.getParameterValues("BnfGrid1");
		String tBnfType[] = request.getParameterValues("BnfGrid2");
		String tName[] = request.getParameterValues("BnfGrid3");
		String tIDType[] = request.getParameterValues("BnfGrid4");
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
		loggerDebug("DSProposalSave",BnfCount);
		for (int i = 0; i < BnfCount; i++) {
			loggerDebug("DSProposalSave",tBnfAddress[i]);
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
			tLBPOBnfSchema.setIDType(tIDType[i]);
			tLBPOBnfSchema.setIDNo(tIDNo[i]);
			tLBPOBnfSchema.setFillNo(tBnfFillNo[i]);
			loggerDebug("DSProposalSave","被保险人序号是："+tInsuredType[i]);
			tLBPOBnfSchema.setInsuredNo(tInsuredNo[i]);
			tLBPOBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
			tLBPOBnfSchema.setBnfLot(tBnfLot[i]);
			tLBPOBnfSchema.setBnfGrade(tBnfGrade[i]);
			tLBPOBnfSchema.setBnfAddress(tBnfAddress[i]);
			loggerDebug("DSProposalSave","tLBPOBnfSchema.Encode="
			+ tLBPOBnfSchema.encode());
			tLBPOBnfSet.add(tLBPOBnfSchema);

		}
		loggerDebug("DSProposalSave","end set schema 受益人信息...");

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
		loggerDebug("DSProposalSave","险种个数："+POLCount);
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
			loggerDebug("DSProposalSave","======================"+tRisksequence[j]);
			tLBPOPolSet.add(tLBPOPolSchema);
		}

		//传递非SCHEMA信息
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("LiveGetMode", request.getParameter("LiveGetMode")); //生存金处理方式
		tTransferData.setNameAndValue("BonusGetMode", request.getParameter("BonusGetMode")); //红利领取方式
		tTransferData.setNameAndValue("SequenceNo3", request.getParameter("SequenceNo3")); //被保人类型
		tTransferData.setNameAndValue("GetYear", request.getParameter("GetYear")); //年金开始领取年龄
		tTransferData.setNameAndValue("GetLimit", request.getParameter("GetLimit")); //领取期限
		tTransferData.setNameAndValue("StandbyFlag3", request.getParameter("StandbyFlag3")); //领取方式
		tTransferData.setNameAndValue("InputNo",InputNo); //领取方式
		tTransferData.setNameAndValue("ContNo",request.getParameter("PrtNo")); //领取方式
		

		loggerDebug("DSProposalSave","getIntv=" + request.getParameter("getIntv"));
		loggerDebug("DSProposalSave","GetDutyKind="
		+ request.getParameter("GetDutyKind"));

		loggerDebug("DSProposalSave","是否为家庭单"+request.getParameter("FamilyType"));
		tTransferData.setNameAndValue("FamilyType", request.getParameter("FamilyType"));
		tVData.addElement(tLBPOPolSet);
		tVData.addElement(tLBPOBnfSet);
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		// 数据传输
		//DSProposalUI tDSProposalUI = new DSProposalUI();
		String busiName="tbDSProposalUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate,busiName)) {
			tError = tBusinessDelegate.getCErrors();
			Content = tError.getFirstError();
			FlagStr = "Fail";
			loggerDebug("DSProposalSave","Content:" + Content);
		} else {
			Content = "保存成功!";
			FlagStr = "Succ";
			loggerDebug("DSProposalSave","Content:" + Content);
			tVData.clear();
			tVData = tBusinessDelegate.getResult();
		}
	} catch (Exception e1) {
		Content = " 保存失败，原因是: " + e1.toString().trim();
		FlagStr = "Fail";
		loggerDebug("DSProposalSave",Content);
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

