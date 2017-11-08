package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubBLInterface;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 续期发票打印
 * <p>
 * Description:
 * </p>
 * 续期发票打印
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author wangyy
 * @version 1.0
 */

public class ExtendInvoiceBL implements PubBLInterface {
private static Logger logger = Logger.getLogger(ExtendInvoiceBL.class);
	/** 全局变量 */
	private GlobalInput tGI = new GlobalInput();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private String mtype = "";
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	private String mLastPayDate = "";
	private String mPayDate = "";
	private String mBankAccNo = "";
	private double mEMoney = 0;
	/** 数据操作字符串 */
	private String mOperate;

	public ExtendInvoiceBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * submitData
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 * @todo Implement this com.sinosoft.lis.pubfun.PubBLInterface method
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	public boolean dealData() {

		ExeSQL tExeQuery = new ExeSQL();
		SSRS tSSRS = new SSRS();
		if (mtype.equals("1")) {
			logger.debug("mtype=============1,查询实收表!!!!!!!!!!!!!!");

			// 查询业务员信息
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(" select labranchgroup.name , "
					+ " laagent.name, "
					+ " laagent.phone, "
					+ " labranchgroup.agentgroup, "
					+ " laagent.agentcode , "
					+ " getbranchglobalname(labranchgroup.agentgroup) "
					+ " from labranchgroup, laagent "
					+ " where trim(laagent.branchcode) = trim(labranchgroup.agentgroup) "
					+ " and Exists (Select 'X' From lcpol "
					+ " Where Laagent.Agentcode=trim(agentcode) "
					+ " and polno=mainpolno " + " and Contno = '?Contno?')");
			sqlbv.put("Contno", mLJAPaySchema.getOtherNo());
			tSSRS = tExeQuery
					.execSQL(sqlbv);

			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
			if (!tLOPRTManagerDB.getInfo()) {
				buildError("dealData", "打印管理表查询失败!");
				return false;
			}
			tLOPRTManagerDB.setStateFlag("1");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			MMap map = new MMap();
			map.put(tLOPRTManagerSchema, "UPDATE");
			VData tVData = new VData();
			tVData.add(map);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("ExtendInvoiceHB.vts", "printer"); // 最好紧接着就初始化xml文档

			SSRS t1SSRS = new SSRS();
			SSRS t1MSSRS = new SSRS();
			String tnextPayDate = "";

			// t1SSRS = tExeQuery.execSQL("select ls.riskshortname, sum(a.SumActuPayMoney)
			// ,a.paycount , d.payintv , a.CurPayToDate from ljapayperson a, ljapay b,
			// lmrisk ls, lcpol d where b.otherno = a.contno "
			// +"and a.GetNoticeNo = b.GetNoticeNo and b.OtherNoType = '2' and a.contno =
			// '"+mLJAPaySchema.getOtherNo()+"' "
			// +" and ls.riskcode = a.riskcode and a.polno = d.polno and a.contno = d.contno
			// and d.polno = d.mainpolno and a.paycount = (select max(paycount) from
			// ljapayperson where contno = '" +
			// mLJAPaySchema.getOtherNo()+"' and paytype in ('ZC','HM'))");
			// 查询实收数据
			String tSql1 = "select ls.riskshortname,sum(a.SumActuPayMoney),a.paycount,a.payintv,to_char(a.CurPayToDate,'yyyy-mm-dd')"
					+ " from ljapayperson a, ljapay b, lmrisk ls"
					+ " where b.otherno = a.contno"
					+ " and a.GetNoticeNo = b.GetNoticeNo"
					+ " and a.payno=b.payno"
					+ " and b.OtherNoType in ('2','3','8','02','03','08')"
					+ " and a.contno = '?contno?'"
					+ " and ls.riskcode = a.riskcode"
					+ " and exists (select polno from lcpol where mainpolno=polno and polno=a.polno and a.contno='?contno?')"
					+ " and a.paycount=(select max(paycount) from ljapayperson where getnoticeno='?getnoticeno?')"
					+ " and a.paytype in ('ZC', 'HM')"
					+ " and a.getnoticeno ='?getnoticeno?' "
					+ " group by ls.riskshortname,a.paycount,a.payintv,a.CurPayToDate";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql1);
			sqlbv1.put("contno", mLJAPaySchema.getOtherNo());
			sqlbv1.put("getnoticeno", tLOPRTManagerSchema.getStandbyFlag1());

			t1SSRS = tExeQuery.execSQL(sqlbv1);
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(" select sum(a.SumActuPayMoney), "
					+ "(select codename from ldcode "
					+ " where codetype = 'paylocation' "
					+ " and code = d.paylocation),"
					+ " d.payyears"
					+ " from ljapayperson a, ljapay b, lcpol d "
					+ " where b.getnoticeno = '?getnoticeno?' and  b.otherno = a.contno  "
					+ " and a.GetNoticeNo = b.GetNoticeNo "
					+ " and b.OtherNoType in ('2','3','8','02','03','08') "
					+ " and a.contno = '?contno?' "
					+ " and a.polno = d.polno "
					+ " and a.contno = d.contno "
					+ " and d.polno = d.mainpolno "
					+ " and a.confdate = (select max(confdate) from ljapayperson "
					+ " where contno = '?contno?' "
					+ " and getnoticeno = '?getnoticeno?' and paytype = 'ZC')"
					+ " group by b.SumActuPayMoney,d.paylocation,d.payyears");
			sqlbv2.put("getnoticeno", tLOPRTManagerSchema.getStandbyFlag1());
			sqlbv2.put("contno", mLJAPaySchema.getOtherNo());

			t1MSSRS = tExeQuery
					.execSQL(sqlbv2);
			texttag.add("PayLocation", t1MSSRS.GetText(1, 2));
			logger.debug("=-=-=-=-=-=-PayYears=-=-=-=-=-=-=-"
					+ t1MSSRS.GetText(1, 3));
			texttag.add("PayYears", t1MSSRS.GetText(1, 3));

			SSRS t2SSRS = new SSRS();
			SSRS t2MSSRS = new SSRS();

			/**
			 * 得到附加险的名称
			 */

			String tSql2 = "  select  b.riskshortname , a.riskcode , to_char(a.CurPayToDate,'yyyy-mm-dd') "
					+ "from ljapayperson a , lmrisk b , lcpol c "
					+ "where a.riskcode = b.riskcode "
					+ "and a.paytype in ('ZC', 'HM') and a.polno = c.polno "
					+ "and c.polno <> c.mainpolno and a.getnoticeno = '?getnoticeno?' "
					+ "group by a.riskcode , b.riskshortname , a.CurPayToDate ";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSql2);
			sqlbv3.put("getnoticeno", tLOPRTManagerSchema.getStandbyFlag1());
			t2SSRS = tExeQuery.execSQL(sqlbv3);
			logger.debug(tSql2);

			/**
			 * 定义附加险备注 String tRemark
			 */
			String tRemark = "";
			for (int i = 1; i <= t2SSRS.getMaxRow(); i++) {
				texttag.add("Risk" + i, t2SSRS.GetText(i, 1));
				// t2MSSRS = tExeQuery.execSQL( " select sum(a.SumActuPayMoney) from
				// ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno "
				// + " and a.riskcode = '"+t2SSRS.GetText(i,2) +"' and a.GetNoticeNo =
				// b.GetNoticeNo and b.OtherNoType = '2' and a.contno = '" +
				// mLJAPaySchema.getOtherNo()+"'"
				// +
				// " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno
				// and a.confdate in (select max(confdate) from ljapayperson where contno = '" +
				// mLJAPaySchema.getOtherNo()+"' and paytype = 'ZC')");
				/**
				 * 得到附加险的总的保费，其中包括加费
				 */
				tSql2 = " select sum(a.sumactupaymoney) "
						+ "from ljapayperson a " + "where a.riskcode = '?riskcode?' "
						+ "and a.paytype in('ZC ','HM ') "
						+ "and a.getnoticeno = '?getnoticeno?'";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(tSql2);
				sqlbv4.put("riskcode", t2SSRS.GetText(i, 2));
				sqlbv4.put("getnoticeno", tLOPRTManagerSchema.getStandbyFlag1());

				t2MSSRS = tExeQuery.execSQL(sqlbv4);

				texttag.add("Money" + i, new DecimalFormat("0.00")
						.format(Double.parseDouble(t2MSSRS.GetText(1, 1))));
				logger.debug(new DecimalFormat("0.00").format(Double
						.parseDouble(t2MSSRS.GetText(1, 1))));
				tRemark += t2SSRS.GetText(i, 1)
						+ " "
						+ new DecimalFormat("0.00").format(Double
								.parseDouble(t2MSSRS.GetText(1, 1))) + " ";
				mEMoney += Double.parseDouble(t2MSSRS.GetText(1, 1));
			}
			ExeSQL tExeSQL = new ExeSQL();

			SSRS tySSRS = new SSRS();
			if (t1SSRS.getMaxRow() > 0 && t1SSRS.GetText(1, 4) != null
					&& !t1SSRS.GetText(1, 4).equals("null")
					&& !t1SSRS.GetText(1, 4).equals("")) {
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(" select codename from ldcode where codetype = 'payintv' and code = '?code?'");
				sqlbv5.put("code", t1SSRS.GetText(1, 4));
				tySSRS = tExeSQL
						.execSQL(sqlbv5);
				tnextPayDate = t1SSRS.GetText(1, 5);
			} else {
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql(" select b.codename from lccont a , ldcode b where b.codetype = 'payintv' "
						+ " and a.payintv = b.code and a.contno = '?contno?'");
				sqlbv6.put("contno", mLJAPaySchema.getOtherNo());
				tySSRS = tExeSQL
						.execSQL(sqlbv6);
				tnextPayDate = t2SSRS.GetText(1, 3);

			}
			SSRS tcomSSRS = new SSRS();
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(" select address, phone from ldcom where comcode = '?comcode?'");
			sqlbv7.put("comcode", tGI.ManageCom);
			tcomSSRS = tExeSQL
					.execSQL(sqlbv7);
			texttag.add("tRemark", tRemark);
			texttag.add("FeeMode", tySSRS.GetText(1, 1));
			texttag.add("GIManageCom", tGI.ManageCom);
			texttag.add("GIPhone", tcomSSRS.GetText(1, 2));
			texttag.add("GIaddress", tcomSSRS.GetText(1, 1));
			texttag.add("nextPayDate", tnextPayDate);
			if (!tnextPayDate.equals("null") && !tnextPayDate.equals("")
					&& !tnextPayDate.equals(null)) {
				// logger.debug("======tnextPayDate is not null======") ;
				texttag.add("nextPayDate.Year", tnextPayDate.substring(0, 4));
				// logger.debug("nextPayDate.Year" +tnextPayDate.substring (0,4)) ;
				texttag.add("nextPayDate.Month", tnextPayDate.substring(5, 7));
				// logger.debug("nextPayDate.Month" + tnextPayDate.substring (5,7)) ;
				texttag.add("nextPayDate.Day", tnextPayDate.substring(8, 10));
				logger.debug("nextPayDate.Year"
						+ tnextPayDate.substring(0, 4) + "nextPayDate.Month"
						+ tnextPayDate.substring(5, 7) + "nextPayDate.Day"
						+ tnextPayDate.substring(8, 10));
			}
			texttag.add("paycount", t1SSRS.GetText(1, 3));
			texttag.add("LCCont.AppntName", getAppntName(mLJAPaySchema
					.getAppntNo()));
			texttag.add("LCCont.ContNo", mLJAPaySchema.getOtherNo());
			texttag.add("ESUMMoney", new DecimalFormat("0.00").format(mEMoney));
			texttag.add("SysDate", PubFun.getCurrentDate());
			// texttag.add("ReceiveDate", tLJAPayPersonSchema.getCurPayToDate());
			// texttag.add("SumAmnt",new
			// DecimalFormat("0.00").format(mLJAPaySchema.getSumActuPayMoney()));
			// texttag.add("SumAccrual", "");
			texttag.add("Issue", mLastPayDate);
			texttag.add("Operator", tGI.Operator);
			if (!mLastPayDate.equals("null") && !mLastPayDate.equals("")
					&& !mLastPayDate.equals(null)) {
				texttag.add("Issue.Year", mLastPayDate.substring(0, 4));
				texttag.add("Issue.Month", mLastPayDate.substring(5, 7));
				texttag.add("Issue.Day", mLastPayDate.substring(8, 10));
			}
			texttag.add("CSumMoney", PubFun.getChnMoney(mLJAPaySchema
					.getSumActuPayMoney()));
			texttag.add("SumMoney", new DecimalFormat("0.00")
					.format(mLJAPaySchema.getSumActuPayMoney()));
			texttag.add("PayDate", mPayDate);
			if (!mPayDate.equals("null") && !mPayDate.equals("")) {
				texttag.add("PayYear", mPayDate.substring(0, 4));
				texttag.add("PayMonth", mPayDate.substring(5, 7));
				texttag.add("PayDay", mPayDate.substring(8, 10));
			}
			logger.debug("mtype==================1"
					+ "=====================mBankAccNo" + mBankAccNo);
			texttag.add("BankAccNo", mBankAccNo);
			texttag.add("LAAgent.AgentCode", tSSRS.GetText(1, 5)); // mLJAPaySchema.getAgentCode
			// ()) ;
			texttag.add("LAAgent.Name", tSSRS.GetText(1, 2));
			texttag.add("LAAgent.Phone", tSSRS.GetText(1, 3));
			texttag.add("LABranchGroup.Name", tSSRS.GetText(1, 1));
			// if (tSSRS.getMaxRow () > 0)
			// {
			// logger.debug("=====LABranchGroup.AgentGroup=====" +
			// tSSRS.GetText (1,4)) ;
			// logger.debug("开始查询业务员区部组") ;
			// String tSQL = "select getbranchglobalname('" +
			// tSSRS.GetText (1,4) +
			// "') from dual" ;
			// SSRS sSSRS = new SSRS () ;
			// sSSRS = tExeSQL.execSQL (tSQL) ;
			// logger.debug("业务员区部组为::::::::::::::" + sSSRS.GetText (1,1)) ;
			// texttag.add ("LAAgent.AgentBranch",sSSRS.GetText (1,1)) ;
			// }
			texttag.add("LAAgent.AgentBranch", tSSRS.GetText(1, 6));
			if (t1SSRS.getMaxRow() > 0 && t1SSRS.GetText(1, 1) != null
					&& !t1SSRS.GetText(1, 1).equals("null")
					&& !t1SSRS.GetText(1, 1).equals("")) {
				texttag.add("MainRisk", t1SSRS.GetText(1, 1));
				if (t1MSSRS.getMaxRow() > 0 && t1MSSRS.GetText(1, 1) != null) {
					texttag.add("MainMoney", new DecimalFormat("0.00")
							.format(Double.parseDouble(t1MSSRS.GetText(1, 1))));
				} else {
					texttag.add("MainMoney", "0.00");
				}
			} else {
				texttag.add("MainRisk", "");
				texttag.add("MainMoney", "");
			}
			texttag.add("currentDate", PubFun.getCurrentDate());
			if (tGI.ManageCom.length() > 4) {
				texttag.add("AreaID", tGI.ManageCom.substring(0, 4));
				logger.debug(tGI.ManageCom.substring(0, 4));
			} else {
				texttag.add("AreaID", tGI.ManageCom);
			}
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			PubSubmit p = new PubSubmit();
			if (!p.submitData(tVData, "")) {
				buildError("dealData", "打印表处理失败!");
				return false;
			}

			// 保存补充资料信息
			// xmlexport.outputDocumentToFile("e:\\", "ExtendInvoice"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

			return true;
		}
		if (mtype.equals("2")) {
			logger.debug("mtype===================2,查询应收表!!!!!!!!!!");
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(" select labranchgroup.name , "
					+ " laagent.name, "
					+ " laagent.phone, "
					+ " labranchgroup.agentgroup, "
					+ " laagent.agentcode , "
					+ " getbranchglobalname(labranchgroup.agentgroup) "
					+ " from labranchgroup, laagent "
					+ " where trim(laagent.branchcode) = trim(labranchgroup.agentgroup) "
					+ " and Exists (Select 'X' From lcpol "
					+ " Where Laagent.Agentcode=trim(agentcode) "
					+ " and polno=mainpolno " + " and Contno = '?Contno?')");
			sqlbv8.put("Contno", mLJSPaySchema.getOtherNo());
			tSSRS = tExeQuery
					.execSQL(sqlbv8);

			String tnextPayDate = "";
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
			if (!tLOPRTManagerDB.getInfo()) {
				buildError("dealData", "应收总表查询失败!");
				return false;
			}
			tLOPRTManagerDB.setStateFlag("1");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			MMap map = new MMap();
			map.put(tLOPRTManagerSchema, "UPDATE");
			VData tVData = new VData();
			tVData.add(map);

			// mLJSPaySchema = tLJSPayDB.getSchema();

			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setContNo(mLJSPaySchema.getOtherNo());
			tLJSPayPersonDB.setGetNoticeNo(mLJSPaySchema.getGetNoticeNo());
			LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonDB.query()
					.get(1);

			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("ExtendInvoiceHB.vts", "printer"); // 最好紧接着就初始化xml文档
			/*
			 * ExeSQL tExeQuery = new ExeSQL () ; SSRS tSSRS = new SSRS () ; tSSRS =
			 * tExeQuery.execSQL ( " select labranchgroup.name , " + " laagent.name, " + "
			 * laagent.phone, " + " labranchgroup.agentgroup, " + " laagent.name , " + "
			 * getbranchglobalname(labranchgroup.agentgroup) " + " from
			 * labranchgroup, laagent " + " where trim(laagent.branchcode) =
			 * trim(labranchgroup.agentgroup) " + " and Exists (Select 'X' From
			 * lcpol " + " Where Laagent.Agentcode=trim(agentcode) " + " and
			 * polno=mainpolno " + " and Contno = '" + mLJAPaySchema.getOtherNo () +
			 * "')") ;
			 */

			// " and trim(laagent.agentcode) in " +
			// "(select trim(agentcode) " +
			// " from lccont where contno ='" +
			// mLJSPaySchema.getOtherNo() + "')");
			SSRS t1SSRS = new SSRS();
			SSRS t1MSSRS = new SSRS();
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			sqlbv9.sql(" select ls.riskshortname, "
					+ " sum(a.SumActuPayMoney) ,"
					+ " a.paycount, d.payintv ,"
					+ " to_char(a.CurPayToDate,'yyyy-mm-dd') "
					+ " from ljspayperson a, ljspay b, lmrisk ls, lcpol d "
					+ " where b.otherno = a.contno "
					+ " and a.GetNoticeNo = b.GetNoticeNo "
					+ " and b.OtherNoType in( '2','3','8','02','03','08') "
					+ " and a.contno = '?contno?' "
					+ " and ls.riskcode = a.riskcode "
					+ " and a.polno = d.polno "
					+ " and a.contno = d.contno "
					+ " and d.polno = d.mainpolno "
					+ " and a.paycount=(select max(paycount) from ljspayperson where getnoticeno='?getnoticeno?') "
					+ " group by ls.riskshortname,a.paycount,d.payintv,a.CurPayToDate ");
			sqlbv9.put("contno", mLJSPaySchema.getOtherNo());
			sqlbv9.put("getnoticeno", tLOPRTManagerSchema.getStandbyFlag1());
			t1SSRS = tExeQuery
					.execSQL(sqlbv9);
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(" select sum(a.SumActuPayMoney),"
					+ " (select codename from ldcode "
					+ " where codetype = 'paylocation' "
					+ " and code = d.paylocation)," + " d.PayYears"
					+ " from ljspayperson a, ljspay b, lmrisk ls, lcpol d "
					+ " where b.otherno = a.contno "
					+ " and a.GetNoticeNo = b.GetNoticeNo "
					+ " and b.OtherNoType in( '2','3','8','02','03','08') "
					+ " and a.contno = '?contno?' "
					+ " and ls.riskcode = a.riskcode "
					+ " and a.polno = d.polno " + " and a.contno = d.contno "
					+ " and d.polno = d.mainpolno"
					+ " group by b.SumDuePayMoney,d.paylocation,d.payyears");
			sqlbv10.put("contno", mLJSPaySchema.getOtherNo());
			t1MSSRS = tExeQuery.execSQL(sqlbv10);
			texttag.add("PayLocation", t1MSSRS.GetText(1, 2));
			logger.debug("=-=-=-=-=-=-=-=PayYears-=-=-=-=-=-=-=-=-"
					+ t1MSSRS.GetText(1, 3));
			texttag.add("PayYears", t1MSSRS.GetText(1, 3));
			SSRS t2SSRS = new SSRS();
			SSRS t2MSSRS = new SSRS();
			String tSql = " select ls.riskshortname ,a.riskcode , to_char(a.CurPayToDate,'yyyy-mm-dd') from ljspayperson a, ljspay b, lmrisk ls,lcpol d where b.otherno = a.contno  "
					+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType in( '2','3','8','02','03','08') and ls.riskcode = a.riskcode  and a.contno = '?contno?' "
					+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno group by ls.riskshortname ,a.riskcode , to_char(a.CurPayToDate,'yyyy-mm-dd')";
			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
			sqlbv11.sql(tSql);
			sqlbv11.put("contno", mLJSPaySchema.getOtherNo());
			t2SSRS = tExeQuery.execSQL(sqlbv11);

			logger.debug(tSql);
			/**
			 * 定义附加险的汇总信息 String tRemark
			 */
			String tRemark = "";

			for (int i = 1; i <= t2SSRS.getMaxRow(); i++) {
				texttag.add("Risk" + i, t2SSRS.GetText(i, 1));
				SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
				sqlbv12.sql(" select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lmrisk ls,lcpol d where b.otherno = a.contno  "
						+ " and a.GetNoticeNo = b.GetNoticeNo and a.riskcode = '?riskcode?' and b.OtherNoType in( '2','3','8','02','03','08') and ls.riskcode = a.riskcode  and a.contno = '?contno?' "
						+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ");
				sqlbv12.put("riskcode", t2SSRS.GetText(i, 2));
				sqlbv12.put("contno", mLJSPaySchema.getOtherNo());
				t2MSSRS = tExeQuery
						.execSQL(sqlbv12);

				texttag.add("Money" + i, new DecimalFormat("0.00")
						.format(Double.parseDouble(t2MSSRS.GetText(1, 1))));

				tRemark += t2SSRS.GetText(i, 1)
						+ " "
						+ new DecimalFormat("0.00").format(Double
								.parseDouble(t2MSSRS.GetText(1, 1))) + " ";
				mEMoney += Double.parseDouble(t2MSSRS.GetText(1, 1));
			}
			// 计算缴费年期
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tySSRS = new SSRS();

			if (t1SSRS.getMaxRow() > 0 && t1SSRS.GetText(1, 4) != null
					&& !t1SSRS.GetText(1, 4).equals("null")
					&& !t1SSRS.GetText(1, 4).equals("")) {
				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
				sqlbv13.sql(" select codename from ldcode where codetype = 'payintv' and code = '?code?'");
				sqlbv13.put("code", t1SSRS.GetText(1, 4));
				tySSRS = tExeSQL
						.execSQL(sqlbv13);
				tnextPayDate = t1SSRS.GetText(1, 5);
			} else {
				SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
				sqlbv14.sql(" select b.codename from lccont a , ldcode b where b.codetype = 'payintv' "
						+ " and a.payintv = b.code and a.contno = '?contno?'");
				sqlbv14.put("", mLJSPaySchema.getOtherNo());
				tySSRS = tExeSQL
						.execSQL(sqlbv14);

				tnextPayDate = t2SSRS.GetText(1, 3);
			}

			SSRS tcomSSRS = new SSRS();
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(" select address, phone from ldcom where comcode = '?comcode?'");
			sqlbv15.put("comcode", tGI.ManageCom);
			tcomSSRS = tExeSQL
					.execSQL(sqlbv15);
			texttag.add("GIManageCom", tGI.ManageCom);
			texttag.add("tRemark", tRemark);
			texttag.add("GIPhone", tcomSSRS.GetText(1, 2));
			texttag.add("GIaddress", tcomSSRS.GetText(1, 1));

			texttag.add("FeeMode", tySSRS.GetText(1, 1));
			texttag.add("nextPayDate", tnextPayDate);
			if (!tnextPayDate.equals("null") && !tnextPayDate.equals("")
					&& !tnextPayDate.equals(null)) {
				logger.debug("tnextPayDate is not null");
				texttag.add("nextPayDate.Year", tnextPayDate.substring(0, 4));
				logger.debug("nextPayDate.Year"
						+ tnextPayDate.substring(0, 4));
				texttag.add("nextPayDate.Month", tnextPayDate.substring(5, 7));
				logger.debug("nextPayDate.Month"
						+ tnextPayDate.substring(5, 7));
				texttag.add("nextPayDate.Day", tnextPayDate.substring(8, 10));
				logger.debug("nextPayDate.Day"
						+ tnextPayDate.substring(8, 10));
			}
			texttag.add("paycount", t1SSRS.GetText(1, 3));
			texttag.add("LCCont.AppntName", getAppntName(mLJSPaySchema
					.getAppntNo()));
			texttag.add("LCCont.ContNo", mLJSPaySchema.getOtherNo());
			texttag.add("ESUMMoney", new DecimalFormat("0.00").format(mEMoney));
			texttag.add("SysDate", PubFun.getCurrentDate());
			// texttag.add("ReceiveDate", tLJSPayPersonSchema.getCurPayToDate());
			// texttag.add("SumAmnt", mLJSPaySchema.getSumDuePayMoney());
			texttag.add("SumAccrual", "");
			texttag.add("Issue", mLJSPaySchema.getStartPayDate());
			texttag.add("Operator", tGI.Operator);
			if (!mLJSPaySchema.getStartPayDate().equals("null")
					&& !mLJSPaySchema.getStartPayDate().equals("")
					&& !mLJSPaySchema.getStartPayDate().equals(null)) {
				texttag.add("Issue.Year", mLJSPaySchema.getStartPayDate()
						.substring(0, 4));
				texttag.add("Issue.Month", mLJSPaySchema.getStartPayDate()
						.substring(5, 7));
				texttag.add("Issue.Day", mLJSPaySchema.getStartPayDate()
						.substring(8, 10));
			}
			texttag.add("CurPayDate", tLJSPayPersonSchema.getCurPayToDate());
			texttag.add("PayDate", mPayDate);
			if (!mPayDate.equals("null") && !mPayDate.equals("")) {
				texttag.add("PayYear", mPayDate.substring(0, 4));
				texttag.add("PayMonth", mPayDate.substring(5, 7));
				texttag.add("PayDay", mPayDate.substring(8, 10));
			}
			logger.debug("mtype==================2"
					+ "======================mBankAccNo" + mBankAccNo);
			texttag.add("BankAccNo", mBankAccNo);
			texttag.add("CSumMoney", PubFun.getChnMoney(mLJSPaySchema
					.getSumDuePayMoney()));
			texttag.add("SumMoney", new DecimalFormat("0.00")
					.format(mLJSPaySchema.getSumDuePayMoney()));
			texttag.add("LAAgent.AgentCode", tSSRS.GetText(1, 5));
			texttag.add("LAAgent.Name", tSSRS.GetText(1, 2));
			texttag.add("LAAgent.Phone", tSSRS.GetText(1, 3));
			texttag.add("LABranchGroup.Name", tSSRS.GetText(1, 1));
			// if (tSSRS.getMaxRow () > 0)
			// {
			// String tSQL = "select getbranchglobalname('" + tSSRS.GetText (1,4) + "') from
			// dual" ;
			// SSRS sSSRS = new SSRS () ;
			// sSSRS = tExeSQL.execSQL (tSQL) ;
			// texttag.add ("LAAgent.AgentBranch",sSSRS.GetText (1,1)) ;
			// }
			texttag.add("LAAgent.AgentBranch", tSSRS.GetText(1, 6));
			if (t1SSRS.getMaxRow() > 0 && t1SSRS.GetText(1, 1) != null
					&& !t1SSRS.GetText(1, 1).equals("null")
					&& !t1SSRS.GetText(1, 1).equals("")) {
				texttag.add("MainRisk", t1SSRS.GetText(1, 1));
				if (t1MSSRS.getMaxRow() > 0 && t1MSSRS.GetText(1, 1) != null) {
					texttag.add("MainMoney", new DecimalFormat("0.00")
							.format(Double.parseDouble(t1MSSRS.GetText(1, 1))));
				} else {
					texttag.add("MainMoney", "0.00");
				}
			} else {
				texttag.add("MainRisk", "");
				texttag.add("MainMoney", "");
			}

			if (tGI.ManageCom.length() > 4) {
				texttag.add("AreaID", tGI.ManageCom.substring(0, 4));
				logger.debug(tGI.ManageCom.substring(0, 4));
			} else {
				texttag.add("AreaID", tGI.ManageCom);
			}
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			PubSubmit p = new PubSubmit();
			if (!p.submitData(tVData, "")) {
				buildError("dealData", "打印表处理失败!");
				return false;
			}

			// 保存补充资料信息
			// xmlexport.outputDocumentToFile("e:\\", "ExtendInvoice"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

			return true;

		}
		return false;
	}

	public boolean checkData() {
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql("select paydate,bankaccno from ljtempfeeclass where tempfeeno = '?tempfeeno?'");
		sqlbv16.put("tempfeeno", mLOPRTManagerSchema.getStandbyFlag1());
		tSSRS = exeSql
				.execSQL(sqlbv16);
		if (tSSRS.MaxRow != 0) {
			if (tSSRS.GetText(1, 1) != null
					&& !tSSRS.GetText(1, 1).equals("null")) {
				mPayDate = tSSRS.GetText(1, 1);
			}
			if (tSSRS.GetText(1, 2) != null
					&& !tSSRS.GetText(1, 2).equals("null")) {
				mBankAccNo = tSSRS.GetText(1, 2);
				logger.debug("+++++++++++mBankAccNo++++++++++++"
						+ mBankAccNo);
			}
		}

		boolean tFlag = false; // 在打印续期发票的时候有核销没核销的，但都要打印出来，因为他们在不同的表中，为了控制查ljspay还是ljapay
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(mLOPRTManagerSchema.getStandbyFlag1());
		if (!tLJSPayDB.getInfo()) {
			tFlag = true;
		}
		mLJSPaySchema = tLJSPayDB.getSchema();
		mtype = "2";
		if (tFlag) {
			LJAPayDB tLJAPayDB = new LJAPayDB();
			tLJAPayDB.setGetNoticeNo(mLOPRTManagerSchema.getStandbyFlag1());
			mLJAPaySchema = tLJAPayDB.query().get(1);

			SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
			sqlbv17.sql("select lastpaytodate,curpaytodate from ljapayperson where getnoticeno ='?getnoticeno?' ");
			sqlbv17.put("getnoticeno", mLOPRTManagerSchema.getStandbyFlag1());
			tSSRS = exeSql
					.execSQL(sqlbv17);
			mLastPayDate = tSSRS.GetText(1, 1);
			mtype = "1";
			if (mLJAPaySchema == null && mLJAPaySchema.equals("null")
					&& mLJAPaySchema.equals("")) {
				buildError("dealData", "查表出错，请您核对正确后重新查询!");
				return false;
			}
			return true;
		}
		return true;
	}

	/**
	 * 获得投保人名称
	 * 
	 * @return String
	 */
	private String getAppntName(String tAppntNo) {
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tAppntNo);
		if (!tLDPersonDB.getInfo()) {
			buildError("getAppntName", "客户信息查询失败!");
			return null;
		}
		return tLDPersonDB.getName();
	}

	public boolean getInputData(VData cInputData) {
		mInputData = (VData) cInputData.clone();

		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		if (tGI == null) {
			buildError("getInputData", "全局变量传递失败!");
			return false;
		}
		// mLJAPaySchema = (LJAPaySchema) mInputData.getObjectByObjectName(
		// "LJAPaySchema", 0);
		// if (mLJAPaySchema == null)
		// {
		// buildError("getInputData", "没有传入应收总表数据！");
		// return false;
		// }
		mLOPRTManagerSchema = (LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0);
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有传入打印管理表数据！");
			return false;
		}

		return true;
	}

	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PayHistoryQueryBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * getErrors
	 * 
	 * @return CErrors
	 * @todo Implement this com.sinosoft.lis.pubfun.PubBLInterface method
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * getResult
	 * 
	 * @return VData
	 * @todo Implement this com.sinosoft.lis.pubfun.PubBLInterface method
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String arg[]) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8621";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		tLOPRTManagerSchema.setStandbyFlag1("3100001463355");
		tLJAPaySchema.setOtherNo("JNK30326011001317");
		tLOPRTManagerSchema.setPrtSeq("8103100205566");
		VData tVData = new VData();
		VData mResult = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);
		tVData.addElement(tLJAPaySchema);
		ExtendInvoiceBL b = new ExtendInvoiceBL();
		if (!b.submitData(tVData, "")) {
			logger.debug("submit fail");
		} else {
			logger.debug("submit succ");
		}

	}

	private void jbInit() throws Exception {
	}

}
