package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 郝攀
 * function :团体承保清单
 * @version 1.0
 * @date 2006-12-05
 */

import java.text.DecimalFormat;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class UWGrpListBL {
private static Logger logger = Logger.getLogger(UWGrpListBL.class);
	public UWGrpListBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;

	public void UWGrpListBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		if (!getPrintData()) {
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		ListTable ListTable = new ListTable();
		ListTable.setName("List");
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport
		xmlexport.createDocument("UWGrpList.vts", "printer");
		TextTag texttag = new TextTag(); // Create a TextTag instance
		/*
		 * String tSQL="select distinct (a.riskcode) from ljapaygrp a,lmriskapp
		 * b " +" where a.riskcode=b.riskcode and b.riskprop='G' and a.managecom
		 * like '"+strMngCom+"%' " +" and a.paycount='1' and a.paytype='ZC' and " +"
		 * exists (select 'X' from lcgrpcont t where t.grpcontno=a.grpcontno and
		 * t.signdate between '"+strStartDate+"' and '"+strEndDate+"') ";
		 */
		String tSQL = "select /*+rule*/ distinct (a.riskcode) from ljapaygrp a,lcgrpcont c where a.grpcontno=c.grpcontno and"
				+ " c.signdate between '"
				+ strStartDate
				+ "' and '"
				+ strEndDate
				+ "' and "
				+ " a.managecom like '"
				+ strMngCom
				+ "%' and a.paycount='1' and a.paytype='ZC' and "
				+ " exists (select 'X' from lmriskapp where riskcode=a.riskcode and riskprop='G') ";

		ExeSQL tExeSQL = new ExeSQL();
		SSRS allSSRS = tExeSQL.execSQL(tSQL);
		int allRiskCount = allSSRS.getMaxRow();
		logger.debug("allRiskCount=" + allRiskCount);

		// 卡单
		ListTable CardListTable = new ListTable();
		CardListTable.setName("CardList");
		/*
		 * String cSQL = "select distinct (a.riskcode)from ljapayperson
		 * a,lmriskapp b where b.riskprop='G' and a.riskcode=b.riskcode and " + "
		 * a.managecom like '" + strMngCom + "%' and a.paycount='1' and
		 * a.paytype='ZC' " + " and exists (select 'X' from lccont where
		 * contno=a.contno and signdate between '" + strStartDate + "' and '" +
		 * strEndDate + "' and cardflag='3') ";
		 */
		String cSQL = "select /*+rule*/ distinct (a.riskcode) from ljapayperson a,lccont c where a.contno=c.contno And "
				+ " c.signdate between '"
				+ strStartDate
				+ "' and '"
				+ strEndDate
				+ "' and c.cardflag='3' and "
				+ " a.managecom like '"
				+ strMngCom
				+ "%' and a.paycount='1' and a.paytype='ZC' "
				+ " and exists (select 'X' from lmriskapp where riskcode=a.riskcode and riskprop='G')  ";

		ExeSQL cExeSQL = new ExeSQL();
		SSRS cSSRS = cExeSQL.execSQL(cSQL);
		int allCardRiskCount = cSSRS.getMaxRow();
		logger.debug("allCardRiskCount====" + allCardRiskCount);
		if (allRiskCount == 0 && allCardRiskCount == 0) {
			CError tError = new CError();
			tError.moduleName = "PNoticeBillBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		int allcount = 0;
		double allpremcount = 0;
		// 对于每一个险种进行循环判断相关的取值
		for (int i = 0; i < allRiskCount; i++) {

			int num = 0; // 序号
			int count = 0; // 件数小记
			double amntcount = 0.00; // 保额小记
			double premcount = 0.00; // 保费小记
			int pecont = 0; // 承保人数小记
			String[] tCount = new String[10];
			String sSQL = "select /*+rule*/ l.prtno,p.grpcontno,l.grpname,(select sum(amnt) from lcpol where lcpol.grppolno=l.grppolno and lcpol.riskcode=l.riskcode), "
					+ " p.sumactupaymoney, "
					+ " (select sum(decode(insuredpeoples,0,1,insuredpeoples)) from lcinsured where grpcontno=l.grpcontno and  contno in(select contno from ljapayperson where ljapayperson.payno=p.payno and ljapayperson.grppolno=p.grppolno )), "
					+ "  p.agentcode, "
					+ "(select name from laagent where agentcode = trim(p.agentcode)), "
					+ " (select codename from ldcode where codetype = 'agenttype' and code = p.agenttype), "
					+ " (select codename from ldcode where codetype = 'payintv' and code = p.payintv), "
					+ " (select sum(Mult) from lcpol Where Grppolno=l.grppolno And appflag='1' ) "
					+ " from lcgrppol l ,ljapaygrp p "
					+ " where l.grppolno=p.grppolno and l.riskcode = '"
					+ allSSRS.GetText(i + 1, 1)
					+ "' and "
					+ " l.managecom like '"
					+ strMngCom
					+ "%' and p.paycount='1' and p.paytype='ZC' "
					+ " and exists (select 'X' from lcgrpcont t where t.grpcontno=p.grpcontno and t.grpcontno=l.grpcontno and t.signdate between '"
					+ strStartDate
					+ "' and '"
					+ strEndDate
					+ "') "
					+ " order by l.prtno ";
			SSRS Onessrs = new SSRS();
			Onessrs = tExeSQL.execSQL(sSQL);
			logger.debug("@@@@@@@@@@count===" + Onessrs.getMaxRow());
			if (Onessrs.getMaxRow() > 0) {
				String[] colss = new String[11];
				colss[0] = "序号";
				colss[1] = "投保单号";
				colss[2] = "保单号";
				colss[3] = "投保单位/投保人";
				colss[4] = "保险金额";
				colss[5] = "保险费";
				colss[6] = "承保人数";
				colss[7] = "业务员代码";
				colss[8] = "业务员姓名";
				colss[9] = "销售方式";
				colss[10] = "缴费方式";
				ListTable.add(colss);

				for (int k = 1; k <= Onessrs.getMaxRow(); k++) {
					num++;
					count++;
					// logger.debug("~~~~~~~~~~k:"+k);
					if (allSSRS.GetText(i + 1, 1).equals("00292000")) {// 292险种显示份数不显示金额
																		// yinxiaoyi
																		// 20070208
						logger.debug("~~~~~~~~~~!!!!!!:");
						if (Onessrs.GetText(k, 11) != null
								&& !Onessrs.GetText(k, 11).equals("")) {
							amntcount = amntcount
									+ Double
											.parseDouble(Onessrs.GetText(k, 11));

						}
					} else {
						amntcount = amntcount
								+ Double.parseDouble(Onessrs.GetText(k, 4));
					}
					// logger.debug("~~~~~~~~~~test1:");
					premcount = premcount
							+ Double.parseDouble(Onessrs.GetText(k, 5));
					// logger.debug("~~~~~~~~~~test2:");
					// logger.debug("~~~~~~~~~~test2:"+Onessrs.GetText(k,
					// 6));
					if (Onessrs.GetText(k, 6) != null
							&& !Onessrs.GetText(k, 6).equals("")
							&& !Onessrs.GetText(k, 6).equals("null")) {
						// logger.debug("~~~~~~~~~~!!!!!!:");
						pecont = pecont
								+ Integer.parseInt(Onessrs.GetText(k, 6));
					}
					// logger.debug("~~~~~~~~~~test3:");
					allpremcount = allpremcount
							+ Double.parseDouble(Onessrs.GetText(k, 5));// 总保费
					logger.debug("~~~~~~~~~~pecount:" + pecont);
					String[] cols = new String[11];
					cols[0] = num + "";
					cols[1] = Onessrs.GetText(k, 1);
					cols[2] = Onessrs.GetText(k, 2);
					cols[3] = Onessrs.GetText(k, 3);
					if (allSSRS.GetText(i + 1, 1).equals("00292000")) {// 292险种显示份数不显示金额
																		// yinxiaoyi
																		// 20070208
						if (Onessrs.GetText(k, 11) != null) {
							cols[4] = Onessrs.GetText(k, 11) + "份";
						} else {
							cols[4] = "";
						}

					} else {
						cols[4] = mDecimalFormat.format(new Double(Onessrs
								.GetText(k, 4)));
					}
					cols[5] = mDecimalFormat.format(new Double(Onessrs.GetText(
							k, 5)));
					cols[6] = Onessrs.GetText(k, 6);
					cols[7] = Onessrs.GetText(k, 7);
					cols[8] = Onessrs.GetText(k, 8);
					cols[9] = Onessrs.GetText(k, 9);
					cols[10] = Onessrs.GetText(k, 10);
					ListTable.add(cols);
					// logger.debug("~~~~~~~~~~k:"+k);
				}

				String[] colsss = new String[11];
				colsss[0] = "险种代码:";
				colsss[1] = "" + allSSRS.GetText(i + 1, 1);
				colsss[2] = "件数小计:  " + count;
				// ////////////////////////
				// ////////////////////////
				colsss[3] = "保险金额小计：";
				if (allSSRS.GetText(i + 1, 1).equals("00292000")) {// 292险种显示份数不显示金额
																	// yinxiaoyi
																	// 20070208
					colsss[4] = "" + amntcount + "份";
				} else {
					colsss[4] = ""
							+ mDecimalFormat.format(new Double(amntcount));
				}
				colsss[5] = "";
				colsss[6] = "保险费小计：";
				colsss[7] = "" + mDecimalFormat.format(new Double(premcount));
				colsss[8] = "";
				colsss[9] = "  承保人数小计:" + pecont;
				colsss[10] = "";
				ListTable.add(colsss);
				// 打印出一空行premcount
				String[] colsk = new String[12];
				ListTable.add(colsk);
			}
		}
		// 总记
		String allSQL = " select count(distinct(lcgrppol.grpcontno)) from "
				+ " lcgrpcont ,lcgrppol where lcgrpcont.appflag='1' and  lcgrpcont.grpcontno=lcgrppol.grpcontno and lcgrpcont.managecom like '"
				+ strMngCom + "%' and lcgrpcont.signdate between '"
				+ strStartDate + "' and '" + strEndDate + "' ";
		ExeSQL allExeSQL = new ExeSQL();
		SSRS aSSRS = allExeSQL.execSQL(allSQL);
		allcount = Integer.parseInt(aSSRS.GetText(1, 1));

		String[] col = new String[1];
		xmlexport.addListTable(ListTable, col);

		int allcardcount = 0;
		double allcardpremcount = 0;
		// 按险种循环取
		for (int i = 0; i < allCardRiskCount; i++) {
			int num = 0; // 序号
			int count = 0; // 件数小记
			double amntcount = 0.00; // 保额小记
			double premcount = 0.00; // 保费小记
			int pecont = 0; // 承保人数小记

			String crSQL = "select n.prtno,m.contno , "
					+ " (select appntname from lcappnt where contno= m.contno), "
					+ "  n.amnt,m.sumactupaymoney, "
					+ " (select count(distinct contno) from ljapayperson where ljapayperson.contno=n.contno), "
					+ " m.agentcode, "
					+ "  (select name from laagent where agentcode = trim(m.agentcode)), "
					+ "  (select codename from ldcode where codetype = 'salechnl' and code = n.salechnl), "
					+ "  (select codename from ldcode where codetype = 'payintv' and code = m.payintv) "
					+ "  from ljapayperson m,lccont n  where m.riskcode='"
					+ cSSRS.GetText(i + 1, 1)
					+ "' and  m.paycount='1' and m.paytype='ZC' "
					+ "  and  m.contno=n.contno  and  n.cardflag='3' and n.managecom like '"
					+ strMngCom + "%' " + "   and n.signdate between date'"
					+ strStartDate + "' and date'" + strEndDate + "'";
			ExeSQL crExeSQL = new ExeSQL();
			SSRS crSSRS = crExeSQL.execSQL(crSQL);
			if (crSSRS.getMaxRow() > 0) {
				String[] colss = new String[11];
				colss[0] = "序号";
				colss[1] = "投保单号";
				colss[2] = "保单号";
				colss[3] = "投保单位/投保人";
				colss[4] = "保险金额";
				colss[5] = "保险费";
				colss[6] = "承保人数";
				colss[7] = "业务员代码";
				colss[8] = "业务员姓名";
				colss[9] = "销售方式";
				colss[10] = "缴费方式";
				CardListTable.add(colss);
				for (int k = 1; k <= crSSRS.getMaxRow(); k++) {
					num++;
					count++;
					amntcount = amntcount
							+ Double.parseDouble(crSSRS.GetText(k, 4));
					premcount = premcount
							+ Double.parseDouble(crSSRS.GetText(k, 5));
					pecont = pecont + Integer.parseInt(crSSRS.GetText(k, 6));
					allcardpremcount = allcardpremcount
							+ Double.parseDouble(crSSRS.GetText(k, 5)); // 总保费
					String[] cols = new String[11];
					cols[0] = num + "";
					cols[1] = crSSRS.GetText(k, 1);
					cols[2] = crSSRS.GetText(k, 2);
					cols[3] = crSSRS.GetText(k, 3);
					cols[4] = mDecimalFormat.format(new Double(crSSRS.GetText(
							k, 4)));
					cols[5] = mDecimalFormat.format(new Double(crSSRS.GetText(
							k, 5)));
					cols[6] = crSSRS.GetText(k, 6);
					cols[7] = crSSRS.GetText(k, 7);
					cols[8] = crSSRS.GetText(k, 8);
					cols[9] = crSSRS.GetText(k, 9);
					cols[10] = crSSRS.GetText(k, 10);
					CardListTable.add(cols);

				}

				String[] colsss = new String[11];
				colsss[0] = "险种代码:";
				colsss[1] = "" + cSSRS.GetText(i + 1, 1);
				colsss[2] = "件数小计:  " + count;
				colsss[3] = "保险金额小计：";
				colsss[4] = "" + mDecimalFormat.format(new Double(amntcount));
				colsss[5] = "";
				colsss[6] = "保险费小计：";
				colsss[7] = "" + mDecimalFormat.format(new Double(premcount));
				colsss[8] = "";
				colsss[9] = "  承保人数小计:" + pecont;
				colsss[10] = "";
				CardListTable.add(colsss);
				// 打印出一空行premcount
				String[] colsk = new String[12];
				CardListTable.add(colsk);
			}
		}
		// 卡单的总计
		String allCardSQL = "select count(distinct lccont.contno) from lccont,ljapayperson,lmriskapp where lmriskapp.riskcode=ljapayperson.riskcode and lmriskapp.riskprop='G' and  lccont.cardflag='3'  and lccont.managecom like '"
				+ strMngCom
				+ "%'"
				+ "  and lccont.signdate between '"
				+ strStartDate
				+ "' and '"
				+ strEndDate
				+ "' and lccont.contno=ljapayperson.contno and ljapayperson.paycount='1' and ljapayperson.paytype='ZC' ";
		ExeSQL allCardExeSQL = new ExeSQL();
		SSRS acardSSRS = allCardExeSQL.execSQL(allCardSQL);
		allcardcount = Integer.parseInt(acardSSRS.GetText(1, 1));

		texttag = new TextTag();
		texttag.add("PrintDate", "" + PubFun.getCurrentDate());
		texttag.add("ManageCom", "" + strMngCom);
		texttag.add("SignStartDate", "" + strStartDate);
		texttag.add("SignEndDate", "" + strEndDate);
		texttag.add("allcount", "" + String.valueOf(allcount));
		texttag.add("allpremcount", ""
				+ mDecimalFormat.format(new Double(allpremcount)));
		texttag.add("allcardcount", "" + String.valueOf(allcardcount));
		texttag.add("allcardpremcount", ""
				+ mDecimalFormat.format(new Double(allcardpremcount)));

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		String[] ccol = new String[1];
		xmlexport.addListTable(CardListTable, ccol);

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpPolListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {

		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String args[]) {
		VData tVData = new VData();
		tVData.addElement("8621");
		tVData.addElement("2006-12-04");
		tVData.addElement("2006-12-08");
		UWGrpListBL tUWGrpListBL = new UWGrpListBL();
		tUWGrpListBL.submitData(tVData, "PRINT");

	}

	private void jbInit() throws Exception {
	}

}
