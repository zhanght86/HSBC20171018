<%@include file="/i18n/language.jsp"%>

<%@page import="org.apache.tools.zip.ZipEntry"%>
<%@page import="org.apache.tools.zip.ZipOutputStream"%>
<%
	//程序名称：LRSearchSave.jsp
	//程序功能：再保查询
	//创建日期：2008-2-20
	//创建人  ：zhangbin
%>

<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.report.f1report.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.reinsure.tools.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strResult = "";

	String tOperateType = request.getParameter("OperateType");
	if ("DOWNLOAD".equals(tOperateType)) {
		//在此设置导出Excel的列名，应与sql语句取出的域相对应
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput = (GlobalInput) session.getAttribute("GI");

		RIExportExcel.Format format = new RIExportExcel.Format();
		ArrayList listCell = new ArrayList();
		ArrayList listLB = new ArrayList();
		ArrayList listColWidth = new ArrayList();
		format.mListCell = listCell;
		format.mListBL = listLB;
		format.mListColWidth = listColWidth;

		RIExportExcel.Cell tCell = null;
		RIExportExcel.ListBlock tLB = null;

		String tManageCom = tGlobalInput.ComCode;
		listColWidth.add(new String[]{"0", "5000"});

		tLB = new RIExportExcel.ListBlock("001");

		String tEventType = request.getParameter("EventType");;

		String tStartDate = request.getParameter("StartDate");
		String tEndDate = request.getParameter("EndDate");
		String tContNo = request.getParameter("ContNo");
		String tGrpContNo = request.getParameter("GrpContNo");
		String tInsuredNo = request.getParameter("InsuredNo");
		String tInsuredName = request.getParameter("InsuredName");
		String tTempType = request.getParameter("TempType");
		String tRIcomCode = request.getParameter("RIcomCode");
		String tRIContNo = request.getParameter("RIContNo");

		String tContType = request.getParameter("ContType");
		String tAccumulateDefNO = request
				.getParameter("AccumulateDefNO");

		TransferData exportDate = new TransferData();

		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getAttribute("GI");

		VData tVData = new VData();
		VData mResult = new VData();
		CErrors mErrors = new CErrors();
		tVData.addElement(tG);

		tVData.addElement(exportDate);
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer sqlStrBuffer = new StringBuffer();
		if (tEventType.equals("01")) { //新单
			//sqlStrBuffer
			//.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12),max(x.A13),sum(x.A14),sum(x.A14)/max(x.A12),sum(x.A15),sum(x.A16),sum(A23),sum(A24),max(x.A18),max(x.A19),max(x.A20),max(x.A21),max(x.A22) getdate from (");
			//sqlStrBuffer
			//.append(" select (case when a.grpcontno='00000000000000000000' then a.contno else a.grpcontno end) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5,a.Insuredbirthday A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,a.accamnt A13,b.CessionAmount A14,b.PremChang A15,b.Commchang A16,a.eventno A17,a.standbydouble2 A18,a.standbydouble3 A19,a.peakline A20, a.payyears A21, a.getdate A22,nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A23,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0)) A24 ");
			//sqlStrBuffer
			//.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType in('01','02')");
			sqlStrBuffer
					.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12),max(x.A13),sum(x.A14),decode(max(x.A12),0,0,sum(x.A14)/max(x.A12)),sum(x.A15),sum(x.A16),sum(A23),sum(A24),max(x.A18),max(x.A19),max(x.A20),max(x.A21),max(x.A22) getdate from (");
			sqlStrBuffer
					.append(" select (case when a.grpcontno='00000000000000000000' then a.contno else a.grpcontno end) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5,a.Insuredbirthday A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "'))) A11,a.riskamnt A12,a.accamnt A13,b.CessionAmount A14,b.PremChang A15,b.Commchang A16,a.eventno A17,a.standbydouble2 A18,a.standbydouble3 A19,a.peakline A20, a.payyears A21, a.getdate A22,nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A23,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0)) A24 ");
			sqlStrBuffer
					.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType in('01','02')");

			if (tStartDate != null && !tStartDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate >= '" + tStartDate
						+ "%'");
			}
			if (tEndDate != null && !tEndDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate <= '" + tEndDate
						+ "%'");
			}
			if (tRIcomCode != null && !tRIcomCode.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
								+ tRIcomCode + "')");
			}

			if (tContType.equals("1")) {
				if (tContNo != null && !tContNo.equals("")) {
					sqlStrBuffer.append(" and a.contno='" + tContNo
							+ "'");
				}
			} else {
				if (tGrpContNo != null && !tGrpContNo.equals("")) {
					sqlStrBuffer.append(" and a.grpcontno='"
							+ tGrpContNo + "'");
				}
			}
			if (tInsuredNo != null && !tInsuredNo.equals("")) {
				sqlStrBuffer.append(" and a.InsuredNo='" + tInsuredNo
						+ "'");
			}
			if (tInsuredName != null && !tInsuredName.equals("")) {
				sqlStrBuffer.append(" and a.InsuredName like '%"
						+ tInsuredName + "%'");
			}
			if (tTempType.equals("1")) {
				sqlStrBuffer
						.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
			} else if (tTempType.equals("2")) {
				sqlStrBuffer.append(" and b.StandbyString1='03' ");
			}
			if (tAccumulateDefNO != null
					&& !tAccumulateDefNO.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
								+ tAccumulateDefNO + "') ");
			}
			sqlStrBuffer
					.append(" and rownum<=500 ) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate");

		} else if (tEventType.equals("03")) { //保全
			//sqlStrBuffer
			//.append("  select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),sum(x.A17),sum(A24),sum(A25),max(x.A19),max(x.A20), max(x.A21),max(x.A22),max(x.A23) getdate ");
			//sqlStrBuffer.append(" from (");
			//sqlStrBuffer
			//.append(" select (case when a.grpcontno='00000000000000000000' then a.contno else a.grpcontno end) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5,a.Insuredbirthday A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16, b.Commchang A17,a.eventno A18,a.standbydouble2 A19,a.standbydouble3 A20,a.peakline A21,a.payyears A22,a.getdate A23,nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A24,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0)) A25");
			//sqlStrBuffer
			//.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '03' and (a.Feefinatype is null or a.Feefinatype ='BF') ");
			sqlStrBuffer
					.append("  select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),sum(x.A17),sum(A24),sum(A25),max(x.A19),max(x.A20), max(x.A21),max(x.A22),max(x.A23) getdate ");
			sqlStrBuffer.append(" from (");
			sqlStrBuffer
					.append(" select (case when a.grpcontno='00000000000000000000' then a.contno else a.grpcontno end) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5,a.Insuredbirthday A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "'))) A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16, b.Commchang A17,a.eventno A18,a.standbydouble2 A19,a.standbydouble3 A20,a.peakline A21,a.payyears A22,a.getdate A23,nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0) A24,(b.Commchang+nvl((select sum(SumBonus) from RIBonusAcc ba where ba.Riskcode = a.Riskcode),0)) A25");
			sqlStrBuffer
					.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '03' and (a.Feefinatype is null or a.Feefinatype ='BF') ");

			if (tStartDate != null && !tStartDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate >= '" + tStartDate
						+ "%'");
			}
			if (tEndDate != null && !tEndDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate <= '" + tEndDate
						+ "%'");
			}
			if (tRIcomCode != null && !tRIcomCode.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
								+ tRIcomCode + "')");
			}

			if (tContType.equals("1")) {
				if (tContNo != null && !tContNo.equals("")) {
					sqlStrBuffer.append(" and a.contno='" + tContNo
							+ "'");
				}
			} else {
				if (tGrpContNo != null && !tGrpContNo.equals("")) {
					sqlStrBuffer.append(" and a.grpcontno='"
							+ tGrpContNo + "'");
				}
			}
			if (tInsuredNo != null && !tInsuredNo.equals("")) {
				sqlStrBuffer.append(" and a.grpcontno='" + tInsuredNo
						+ "'");
			}
			if (tInsuredName != null && !tInsuredName.equals("")) {
				sqlStrBuffer.append(" and a.InsuredName like '%"
						+ tInsuredName + "%'");
			}
			if (tTempType.equals("1")) {
				sqlStrBuffer
						.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
			} else if (tTempType.equals("2")) {
				sqlStrBuffer.append(" and b.StandbyString1='03' ");
			}
			if (tAccumulateDefNO != null
					&& !tAccumulateDefNO.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
								+ tAccumulateDefNO + "') ");
			}
			sqlStrBuffer
					.append(" and rownum<=500 ) x group by x.A1,x.A2,x.A9,x.A10,x.A18 order by getdate");
		} else if (tEventType.equals("04")) { //理赔
			//sqlStrBuffer
			//.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12), max(x.A13),max(x.A15),max(x.A16), max(x.A17),max(x.A18),max(x.A19) getdate");
			//sqlStrBuffer.append(" from (");
			//sqlStrBuffer
			//.append(" select  decode(a.grpcontno,'00000000000000000000',a.contno,a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5, a.Insuredbirthday A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.returnpay A13,a.eventno A14,a.standbydouble2 A15, a.standbydouble3 A16,a.peakline A17,a.payyears A18,a.getdate A19 ");
			//sqlStrBuffer
			//.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '04' ");
			sqlStrBuffer
					.append(" select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,max(x.A11),max(x.A12), max(x.A13),max(x.A15),max(x.A16), max(x.A17),max(x.A18),max(x.A19) getdate");
			sqlStrBuffer.append(" from (");
			sqlStrBuffer
					.append(" select  decode(a.grpcontno,'00000000000000000000',a.contno,a.grpcontno ) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5, a.Insuredbirthday A6,a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "'))) A11,a.riskamnt A12,b.returnpay A13,a.eventno A14,a.standbydouble2 A15, a.standbydouble3 A16,a.peakline A17,a.payyears A18,a.getdate A19 ");
			sqlStrBuffer
					.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '04' ");

			if (tStartDate != null && !tStartDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate >= '" + tStartDate
						+ "%'");
			}
			if (tEndDate != null && !tEndDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate <= '" + tEndDate
						+ "%'");
			}
			if (tRIcomCode != null && !tRIcomCode.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
								+ tRIcomCode + "')");
			}

			if (tContType.equals("1")) {
				if (tContNo != null && !tContNo.equals("")) {
					sqlStrBuffer.append(" and a.contno='" + tContNo
							+ "'");
				}
			} else {
				if (tGrpContNo != null && !tGrpContNo.equals("")) {
					sqlStrBuffer.append(" and a.grpcontno='"
							+ tGrpContNo + "'");
				}
			}
			if (tInsuredNo != null && !tInsuredNo.equals("")) {
				sqlStrBuffer.append(" and a.grpcontno='" + tInsuredNo
						+ "'");
			}
			if (tInsuredName != null && !tInsuredName.equals("")) {
				sqlStrBuffer.append(" and a.InsuredName like '%"
						+ tInsuredName + "%'");
			}
			if (tTempType.equals("1")) {
				sqlStrBuffer
						.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
			} else if (tTempType.equals("2")) {
				sqlStrBuffer.append(" and b.StandbyString1='03' ");
			}
			if (tAccumulateDefNO != null
					&& !tAccumulateDefNO.equals("")) {
				sqlStrBuffer
						.append(" and exists (select 'X' from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
								+ tAccumulateDefNO + "') ");
			}
			sqlStrBuffer
					.append(" and rownum<=500 order by a.getdate) x group by x.A1,x.A2,x.A9,x.A10,x.A14 order by getdate ");

		} else if (tEventType.equals("05")) { //费用
			//sqlStrBuffer
			//.append("select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),(select codename from ldcode where codetype = 'feefinatype' and code = x.A17 and rownum =1 ),max(x.A17),max(x.A19),max(x.A20) getdate ");
			//sqlStrBuffer.append(" from (");
			//sqlStrBuffer
			//.append("select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,a.InsuredName A3,(select codename from Ldcode where code=a.InsuredSex and codetype='sex') A4,a.smokeflag A50,a.insuredage A5,a.Insuredbirthday A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,a.amnt A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16,a.FeeFinaType A17,a.eventno A19,a.getdate A20 ");
			//sqlStrBuffer
			//.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '03' and (a.Feefinatype is null or a.Feefinatype ='BF') ");
			sqlStrBuffer
					.append("select x.A1,x.A2,max(x.A3),max(x.A4),max(x.A5),max(x.A50),max(x.A6),max(x.A7),max(x.A8),x.A9,x.A10,max(x.A11),max(x.A12), max(x.A13),max(x.A14),sum(x.A15),sum(x.A16),(select codename from ldcode where codetype = 'feefinatype' and code = x.A17 and rownum =1 ),max(x.A17),max(x.A19),max(x.A20) getdate ");
			sqlStrBuffer.append(" from (");
			sqlStrBuffer
					.append("select decode(a.grpcontno,'00000000000000000000' , a.contno , a.grpcontno ) A1, a.InsuredNo A2,(nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')) A3,(select codename from ldcode where code = decode((select x.sex from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'0','0','1') and codetype = 'sex') A4,(select codename from ldcode where code = nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') and codetype = 'smoke') A50,(SELECT x1.insuredappage FROM lcpol x1 where x1.polno=a.polno) A5,a.Insuredbirthday A6, a.cvalidate A7, a.enddate A8,a.riskcode A9,a.dutycode A10,decode((select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "')),null,0,(select sum(amnt) from lcduty du where du.polno=a.polno and du.dutycode like (a.dutycode)||'%' and du.firstpaydate<=('"
							+ PubFun.getCurrentDate()
							+ "'))) A11,a.riskamnt A12,b.amountchang A13,a.accamnt A14,b.CessionAmount A15, b.PremChang A16,a.FeeFinaType A17,a.eventno A19,a.getdate A20 ");
			sqlStrBuffer
					.append(" from RIPolRecordBake a,RIRecordTrace b where b.EventNo=a.EventNo  and a.EventType = '03' and (a.Feefinatype is null or a.Feefinatype ='BF') ");

			if (tStartDate != null && !tStartDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate >= '" + tStartDate
						+ "%'");
			}
			if (tEndDate != null && !tEndDate.equals("")) {
				sqlStrBuffer.append(" and a.getdate <= '" + tEndDate
						+ "%'");
			}
			if (tRIcomCode != null && !tRIcomCode.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from ririskdivide where ripreceptno=b.ripreceptno and areaid=b.areaid and incomecompanyno='"
								+ tRIcomCode + "')");
			}

			if (tContType.equals("1")) {
				if (tContNo != null && !tContNo.equals("")) {
					sqlStrBuffer.append(" and a.contno='" + tContNo
							+ "'");
				}
			} else {
				if (tGrpContNo != null && !tGrpContNo.equals("")) {
					sqlStrBuffer.append(" and a.grpcontno='"
							+ tGrpContNo + "'");
				}
			}
			if (tInsuredNo != null && !tInsuredNo.equals("")) {
				sqlStrBuffer.append(" and a.grpcontno='" + tInsuredNo
						+ "'");
			}
			if (tInsuredName != null && !tInsuredName.equals("")) {
				sqlStrBuffer.append(" and a.InsuredName like '%"
						+ tInsuredName + "%'");
			}
			if (tTempType.equals("1")) {
				sqlStrBuffer
						.append(" and (b.StandbyString1 <> '03' or b.StandbyString1 is null)");
			} else if (tTempType.equals("2")) {
				sqlStrBuffer.append(" and b.StandbyString1='03' ");
			}
			if (tAccumulateDefNO != null
					&& !tAccumulateDefNO.equals("")) {
				sqlStrBuffer
						.append(" and exists (select * from RIAccumulateDef where AccumulateDefNO = a.AccumulateDefNO and AccumulateDefNO ='"
								+ tAccumulateDefNO + "') ");
			}
			sqlStrBuffer
					.append(" and rownum<=500 ) x group by x.A1,x.A2,x.A9,x.A10,x.A17 order by getdate ");
		}

		if (tOperateType.equals("QUERY")) {
			strResult = tExeSQL.getEncodedResult(sqlStrBuffer
					.toString());
		} else {
			if (tEventType.equals("01")) { //新单
				tLB.colName = new String[]{""+"保单号"+"", ""+"被保人客户号"+"", ""+"被保人姓名"+"",
						""+"被保人性别"+"", ""+"投保年龄"+"", ""+"吸烟标志"+"", ""+"生日"+"", ""+"保单生效日期"+"",
						""+"保单终止日期"+"", ""+"险种编码"+"", ""+"保额"+"", ""+"风险保额"+"", ""+"累计风险保额"+"",
						""+"分保保额"+"", ""+"分保比例"+"", ""+"分保保费"+"", ""+"分保佣金1"+"", ""+"分保佣金2"+"",
						""+"分保佣金汇总"+"", ""+"年缴化保费"+"", ""+"准备金"+"", ""+"现金价值"+"", ""+"缴费年期"+"",
						""+"业务日期"+""};
			}
			if (tEventType.equals("03")) { //保全
				tLB.colName = new String[]{""+"保单号"+"", ""+"被保人客户号"+"", ""+"被保人姓名"+"",
						""+"被保人性别"+"", ""+"投保年龄"+"", ""+"吸烟标志"+"", ""+"生日"+"", ""+"保单生效日期"+"",
						""+"保单终止日期"+"", ""+"险种编码"+"", ""+"保额"+"", ""+"风险保额"+"", ""+"分保保额变化量"+"",
						""+"累计风险保额"+"", ""+"分保保额"+"", ""+"分保保费"+"", ""+"分保佣金1"+"", ""+"分保佣金2"+"",
						""+"分保佣金汇总"+"", ""+"年缴化保费"+"", ""+"准备金"+"", ""+"现金价值"+"", ""+"缴费年期"+"",
						""+"保全确认日期"+""};
			}
			if (tEventType.equals("04")) { //理赔
				tLB.colName = new String[]{""+"保单号"+"", ""+"被保人客户号"+"", ""+"被保人姓名"+"",
						""+"被保人性别"+"", ""+"投保年龄"+"", ""+"吸烟标志"+"", ""+"生日"+"", ""+"保单生效日期"+"",
						""+"保单终止日期"+"", ""+"险种编码"+"", ""+"保额"+"", ""+"风险保额"+"", ""+"理赔摊回"+"",
						""+"年缴化保费"+"", ""+"准备金"+"", ""+"现金价值"+"", ""+"缴费年期"+"", ""+"赔付日期"+""};
			}
			if (tEventType.equals("05")) { //费用
				tLB.colName = new String[]{""+"保单号"+"", ""+"被保人客户号"+"", ""+"被保人姓名"+"",
						""+"被保人性别"+"", ""+"投保年龄"+"", ""+"吸烟标志"+"", ""+"生日"+"", ""+"保单生效日期"+"",
						""+"保单终止日期"+"", ""+"险种编码"+"", ""+"保额"+"", ""+"风险保额"+"", ""+"分保保额变化量"+"",
						""+"累计风险保额"+"", ""+"分保保额"+"", ""+"共保费用"+"", ""+"共保费用类型"+" ",
						""+"共保费用类型编码"+"", ""+"时间号"+"", ""+"费用日期"+""};
			}
			String sql = sqlStrBuffer.toString();
			tLB.sql = sql;
			tLB.row1 = 0;
			tLB.col1 = 0;
			tLB.InitData();
			listLB.add(tLB);

			try {
				response.reset();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment; filename=LRBsnzDataExport_List.xls");

				OutputStream outOS = response.getOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(
						outOS);

				RIExportExcel excel = new RIExportExcel();
				excel.write(format, bos);
				out.clear();
				out = pageContext.pushBody();
				bos.flush();
				bos.close();
			} catch (Exception e) {
			}
		}
	} else if ("RIALL".equals(tOperateType)) {
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment; filename=ri_all.zip");

			OutputStream outOS = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(outOS);

			// 			RIExportExcel excel = new RIExportExcel();
			// 			excel.write(format, bos);
			// 			out.clear();
			// 			out = pageContext.pushBody();
			// 			
			String filePath = new ExeSQL().getOneValue("SELECT a.sysvarvalue FROM ldsysvar a WHERE a.sysvar='RITempDir'");
			filePath = filePath+File.separator+"RI_ALL.txt";
			System.out.println(filePath);
			
			byte[] buf = new byte[1024];
			//ZipOutputStream类：完成文件或文件夹的压缩
			ZipOutputStream zos = new ZipOutputStream(bos);
			zos.setEncoding("UTF-8");//解决中文问题
			FileInputStream in = new FileInputStream(filePath);
			zos.putNextEntry(new ZipEntry("RI_ALL.txt"));
			//out.putNextEntry(new ZipEntry(srcfile[i].getAbsolutePath()));//处理压缩文件带路径的情况，使用srcfile[i].getAbsolutePath()
			int len;
			while ((len = in.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
			zos.close();
			out.clear();
			out = pageContext.pushBody();
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
%>
<html>

<script type="text/javascript">	
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=strResult%>");
</script>
</html>
