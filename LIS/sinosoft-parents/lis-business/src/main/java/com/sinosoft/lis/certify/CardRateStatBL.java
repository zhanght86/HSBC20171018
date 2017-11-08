package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMCertifyDesDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: CardRateStatBL.java
 * </p>
 * <p>
 * Description: 单证核销率/作废率/遗失率报表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author mw
 * @version 1.0
 */

public class CardRateStatBL {
private static Logger logger = Logger.getLogger(CardRateStatBL.class);
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mRealPath;

	private GlobalInput mGI = new GlobalInput();

	private String mBackType; // 核销类型

	private String mBackTypeName; // 核销类型名称

	private String mStateFlag;// 单证状态标志

	private String mManageCom; // 管理机构

	private String mManageLen; // 记录管理机构的长度

	private String mGrade; // 统计粒度

	private String mGradeName; // 统计粒度名称

	private String mCertifyCode; // 单证编码

	private String mReceiveCom; // 接收者

	private String mStartDate; // 开始日期

	private String mEndDate; // 结束日期

	// ------------------------------------------

	private String mManageType = "2"; // 管理机构的类型，如省级分公司(管理机构为4位,如8611)为2，省级公司下的分公司(861100)为3,营销服务部(86110000)为4

	private String mSalechnlType; // 销售渠道类型

	private String mReportType; // 统计报表类型

	private String mPremType; // 是否包含还垫保费 1－包括，0－不包括

	private String mRiskType; // 是否区分险种 1-区分，2-不区分

	private String mRiskCode = ""; // 险种代码

	private String mSalechnlSql;

	private String mRiskCodeSql;

	private String mReportTypeSql;

	private String mActuPaySql = " ";

	private String mTitle;

	private int mMonth; // 根据统计报表类型(mReportType),如果mReportType=1,则mMonth=14

	private int mPayCount;

	private int mCount = 1;

	DecimalFormat mDecimalFormat = new DecimalFormat("0.000000");

	DecimalFormat mDecimalFormat1 = new DecimalFormat("0.0000");

	public CardRateStatBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate))
			return false;

		if (!checkData())
			return false;

		if (!dealData())
			return false;

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("getInputData", "不支持的操作类型!");
			return false;
		}

		mBackType = (String) cInputData.get(0);
		mBackTypeName = (String) cInputData.get(1);
		mManageCom = (String) cInputData.get(2);
		mGrade = (String) cInputData.get(3);
		mGradeName = (String) cInputData.get(4);
		mCertifyCode = (String) cInputData.get(5);
		mReceiveCom = (String) cInputData.get(6);
		mStartDate = (String) cInputData.get(7);
		mEndDate = (String) cInputData.get(8);
		mRealPath = (String) cInputData.get(9);
		mGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 9);

		if (mGI == null || mGI.ManageCom == null) {
			buildError("getInputData", "管理机构为空!");
			return false;
		}

		if (mBackType == null || mBackType.equals("") || mStartDate == null || mStartDate.equals("")
				|| mEndDate == null || mEndDate.equals("")) {
			buildError("getInputData", "数据传输失败!");
			return false;
		}

		if ((mBackType.equals("1") || mBackType.equals("2"))
				&& (mCertifyCode != null && !mCertifyCode.equals(""))) {
			LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
			tLMCertifyDesDB.setCertifyCode(mCertifyCode);
			if (!tLMCertifyDesDB.getInfo()) {
				buildError("getInputData", "查询单证【" + mCertifyCode + "】的定义信息时发生错误!");
				return false;
			} else if (!tLMCertifyDesDB.getTackBackFlag().equals("Y")) {
				buildError("getInputData", "单证【" + mCertifyCode + "】不需要核销!");
				return false;
			}
		}

		// 统计粒度换算成机构代码长度
		if (mGrade != null && !mGrade.equals("")) {
			mManageLen = (Integer.parseInt(mGrade) + 1) + "";
		} else {
			mManageLen = "";
		}

		// 将前台传进来的时间格式化为10位的
		ExeSQL tExeSQL = new ExeSQL();
		String date_sql = " select to_char(to_date('" + "?startdate?"
				+ "','yyyy-mm-dd'),'yyyy-mm-dd'),to_char(to_date('" + "?enddate?"
				+ "','yyyy-mm-dd'),'yyyy-mm-dd') from dual ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(date_sql);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		mStartDate = tExeSQL.execSQL(sqlbv).GetText(1, 1);
		mEndDate = tExeSQL.execSQL(sqlbv).GetText(1, 2);

		return true;
	}

	private boolean checkData() {
		return true;
	}

	// 4、自动缴销 5、手工缴销
	// 6、使用作废 7、停用作废
	// 10、遗失
	private boolean dealData() {
		try {
			String sqlPlun = "";
			if ("1".equals(mBackType)) {// 1|核销率
				mStateFlag = " ('4', '5', '6', '7', '10') ";
			} else if ("2".equals(mBackType)) {// 2|有效期内核销率
				mStateFlag = " ('4', '5', '6', '7', '10') ";
				sqlPlun = " and (b.sendoutcom like 'A%' or b.sendoutcom like 'B%' or exists (select 1 from lmcertifydes where certifycode = b.certifycode and havevalidate='N')" 
						+ " or b.makedate <= (select max(makedate) from lzcardtrack where certifycode = b.certifycode and startno = b.startno and receivecom = b.sendoutcom) "
						+ "  + (select validate1 from lmcertifydes where certifycode = b.certifycode and exists	(select 1 from laagent where branchtype not in (2, 3, 6, 7) and agentcode=substr(b.sendoutcom, 2))"
						+ "     union select validate2 from lmcertifydes where certifycode = b.certifycode	and exists (select 1 from laagent where branchtype in (2, 3, 6, 7) and agentcode=substr(b.sendoutcom, 2))))";
			} else if ("3".equals(mBackType)) {// 3|作废率
				mStateFlag = " ('6', '7') ";
			} else if ("4".equals(mBackType)) {// 4|遗失率
				mStateFlag = " ('10') ";
			}

			String tSQL = "select aa.receivecom,(select name from ldcom where concat('A' , comcode) = aa.receivecom) name, "
					+ " aa.certifycode,(select certifyname from lmcertifydes where certifycode = aa.certifycode) certifyname, sum(aa.sum1), sum(aa.sum2), (case sum(aa.sum1) when 0 then 0 when round(sum(aa.sum2) / sum(aa.sum1) then 4 end))"
					+ " from (select a.receivecom, a.certifycode, a.sumcount sum1,"
					+ " (case when (select sum(b.sumcount) from lzcardb b where b.certifycode = a.certifycode and b.stateflag in "
					+ "?stateflag?"
					+ " and b.startno >= a.startno and b.startno <= a.endno"
					+ sqlPlun
					+ ") is null then 0 else (select sum(b.sumcount) from lzcardb b where b.certifycode = a.certifycode and b.stateflag in "
					+ "?stateflag?"
					+ " and b.startno >= a.startno and b.startno <= a.endno"
					+ sqlPlun
					+ ") end) sum2"
					+ " from lzcardtrack a"
					+ " where 1 = 1 and a.stateflag in ('2','3') and a.reason is null "
					+ getWherePart("a.certifycode", ReportPubFun.getParameterStr(mCertifyCode, "?certifycode?") , "=")
					+ getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?startdate?"), ">=")
					+ getWherePart("a.makedate", ReportPubFun.getParameterStr(mEndDate,"?enddate?"), "<=")
					+ getWherePart("a.receivecom", ReportPubFun.getParameterStr('A' + mManageCom,"?Amngcom?"), "like")
					+ getWherePart("char_length(a.receivecom)", ReportPubFun.getParameterStr(mManageLen+ "","?length?") , "=")
					+ getWherePart("a.ReceiveCom", ReportPubFun.getParameterStr(mReceiveCom,"?receivecom?"), "=")
					+ " union all "// 减去发放回退的单证
					+ " select a.sendoutcom, a.certifycode, -a.sumcount sum1,"
					+ " -(case when (select sum(b.sumcount) from lzcardb b where b.certifycode = a.certifycode and b.stateflag in "
					+ "?stateflag?"
					+ " and b.startno >= a.startno and b.startno <= a.endno"
					+ sqlPlun
					+ ") then 0 else (select sum(b.sumcount) from lzcardb b where b.certifycode = a.certifycode and b.stateflag in "
					+ "?stateflag?"
					+ " and b.startno >= a.startno and b.startno <= a.endno"
					+ sqlPlun
					+ ") end) sum2"
					+ " from lzcardtrack a"
					+ " where 1 = 1 and a.stateflag in ('2','3') and a.reason in ('1','2')"
					+ getWherePart("a.certifycode", ReportPubFun.getParameterStr(mCertifyCode, "?certifycode?"), "=")
					+ getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?startdate?"), ">=")
					+ getWherePart("a.makedate", ReportPubFun.getParameterStr(mEndDate,"?enddate?"), "<=")
					+ getWherePart("a.sendoutcom", ReportPubFun.getParameterStr('A' + mManageCom,"?Amngcom?"), "like")
					+ getWherePart("length(a.sendoutcom)", ReportPubFun.getParameterStr(mManageLen+ "","?length?"), "=")
					+ getWherePart("a.sendoutcom", ReportPubFun.getParameterStr(mReceiveCom,"?receivecom?"), "=")
					+ ") aa"
					+ " group by aa.receivecom, aa.certifycode order by aa.receivecom, aa.certifycode ";
			logger.debug("核销率SQL: " + tSQL);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("stateflag", mStateFlag);
			sqlbv1.put("certifycode", mCertifyCode);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			sqlbv1.put("Amngcom", 'A' + mManageCom);
			sqlbv1.put("length", mManageLen);
			sqlbv1.put("receivecom", mReceiveCom);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv1);

			mCount = tSSRS.getMaxRow();
			logger.debug("tSSRS.getMaxRow():" + tSSRS.getMaxRow());
			if (tSSRS.getMaxRow() <= 0) {
				buildError("dealData", "没有所要查询的数据!");
				return false;
			}

			String[][] result = new String[tSSRS.getMaxRow()][7]; // 用来装最后的结果数据
			for (int index = 1; index <= tSSRS.getMaxRow(); index++) {
				String[] cols = new String[7];
				cols[0] = tSSRS.GetText(index, 1);// 接收者代码
				cols[1] = tSSRS.GetText(index, 2);// 接收者名称
				cols[2] = tSSRS.GetText(index, 3);// 单证编码
				cols[3] = tSSRS.GetText(index, 4);// 单证名称
				cols[4] = tSSRS.GetText(index, 5);// 单证数量
				cols[5] = tSSRS.GetText(index, 6);// 核销数量
				cols[6] = mDecimalFormat1.format(Double.parseDouble(tSSRS.GetText(index, 7)));// XX率

				result[index - 1] = cols;
			}

			if (!trans(result, tSSRS.getMaxRow(), 7)) {
				logger.debug("将数组中为null的项置为空时出错");
				buildError("dealData", "将数组中为null的项置为空时出错!");
				return false;
			}

			// 输入表头等信息
			TransferData tempTransferData = new TransferData();
			tempTransferData.setNameAndValue("&BackTypeName", mBackTypeName);
			tempTransferData.setNameAndValue("&SysDate", PubFun.getCurrentDate());
			// tempTransferData.setNameAndValue("&BackType", mBackType);
			tempTransferData.setNameAndValue("&ManageCom", mManageCom);
			// tempTransferData.setNameAndValue("&Grade", mGrade);
			tempTransferData.setNameAndValue("&GradeName", mGradeName);
			tempTransferData.setNameAndValue("&CertifyCode", mCertifyCode);
			tempTransferData.setNameAndValue("&ReceiveCom", mReceiveCom);
			tempTransferData.setNameAndValue("&StartDate", mStartDate);
			tempTransferData.setNameAndValue("&EndDate", mEndDate);

			// 获取文件名
			String tFileName = "";
			tFileName = "CardRateStat" + "_" + mBackType + "_" + mManageCom + "_" + mGrade + "_"
					+ mCertifyCode + "_" + mReceiveCom + "_" + mStartDate + "_" + mEndDate + "_"
					+ mGI.ComCode;

			// 输出文件
			HashReport xHashReport = new HashReport();
			xHashReport.outputArrayToFile1(mRealPath + "/Excel/Card/" + tFileName + ".xls", mRealPath
					+ "/Excel/Card/" + "CardRateStat.xls", result, tempTransferData);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("dealData", "统计时发生异常！");
			return false;
		}
		return true;
	}

	private String reportpubfun(String mEndDate2, String string) {
		// TODO 自动生成的方法存根
		return null;
	}

	// 处理中介机构的继续率统计(不区分险种)
	private boolean dealLAComData_norisk() {
		try {
			double tSumFirstPrem = 0; // 首期保费之和
			double tSumActuPrem = 0; // 实收保费之和
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");

			if ("2".equals(mReportType)) // 25个月继续率
			{
				mTitle = mTitle + "个月实收保费";
				xmlexport.createDocument("PolContinuedLAComRate1.vts", "printer");
			} else {
				mTitle = mTitle + "个月继续率";
				xmlexport.createDocument("PolContinuedLAComRate.vts", "printer");
			}

			ExeSQL tExeSQL = new ExeSQL();

			String tSQL = "select (select name from lacom where agentcom=rpad(x,20)) agentname,sumfirstmoney,sumsecondmoney,rate from "
					+ " (select  x,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, sum(secondmoney)/(case sum(secondmoney) when 0 then 1 else sum(firstmoney) end) rate from "
					+ "( "
					+ "(select substr(agentcom,1,"
					+ "?mnglen?"
					+ ") x, "
					// + "(select nvl(sum(sumactupaymoney),0) from ljapayperson
					// where polno=a.polno and paycount=1 and paytype='ZC')
					// firstmoney, "
					// + "(select nvl(sum(transmoney),0) from lacommision where
					// payyear="+this.mPayCount+" and commdire='1' and
					// polno=a.polno "+this.mActuPaySql+") secondmoney "
					// //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
					+ this.mReportTypeSql
					+ "from lcpol a "
					+ "where AppFlag='1' "
					+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "
					+ "?month?"
					+ ") and polno=a.polno) "
					+ "and PayIntV=12 "
					// + "and substr(polstate,1,2) not in ('03','04','05') "
					// //非终止,非豁免,非暂停件
					+ "and (FreeFlag is null or Freeflag <> '1') " // 非豁免
					+ "and GrpPolNo='00000000000000000000' "
					+ mSalechnlSql
					+ "and cvalidate>='"
					+ "?startdate?"
					+ "' "
					+ "and cvalidate<='"
					+ "?enddate?"
					+ "' "
					+ "and managecom >='"
					+ PubFun.RCh(mManageCom, "0", 8)
					+ "' "
					+ "and managecom <='"
					+ PubFun.RCh(mManageCom, "9", 8)
					+ "' "
					+ ") "
					+ "union all "
					+ "(select substr(agentcom,1,"
					+ "?mnglen?"
					+ ") x, "
					// + "(select nvl(sum(sumactupaymoney),0) from ljapayperson
					// where polno=a.polno and paycount=1 and paytype='ZC')
					// firstmoney, "
					// + "(select nvl(sum(transmoney),0) from lacommision where
					// payyear="+this.mPayCount+" and commdire='1' and
					// polno=a.polno "+this.mActuPaySql+") secondmoney "
					+ this.mReportTypeSql
					+ "from lbpol a "
					+ "where AppFlag='1' "
					+ "and PayIntV=12 "
					+ "and not exists(select 1 from lpedormain where edorno=a.edorno and edortype='WT' and edorstate='0') "
					// + "and substr(polstate,1,2) not in ('03','05') "
					+ "and (FreeFlag is null or Freeflag <> '1') "
					+ "and GrpPolNo='00000000000000000000' "
					+ "and not exists(select 1 from LCEdorReason c,lpedormain d where c.edorno=a.edorno and c.edorno=d.edorno and  c.polno=a.polno and c.edortype='XT' and  c.TYPE in ('1','2') and c.reason  like '%业务需要%' and d.confdate<=add_months(a.cvalidate, "
					+ "?month?"
					+ ")) "// "年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
					+ mSalechnlSql
					+ "and cvalidate>='"
					+ "?startdate?"
					+ "' "
					+ "and cvalidate<='"
					+ "?enddate?"
					+ "' "
					+ "and managecom >='"
					+ "?mngcom1?"
					+ "' "
					+ "and managecom <='"
					+ "?mngcom2?"
					+ "' "
					+ "and not exists(select 1 from llclaimpolicy c where c.ClmNo=a.edorno and c.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"
					+ "?month?" + ")) " + ") " + ") group by x ) d";
			//SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			//sqlbv1.sql(tSQL);
			// logger.debug("*****SQL: "+tSQL);
			// SSRS tSSRS
			// =this.getAgentComRate(this.mReportType,this.mManageCom);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("mnglen", mManageLen);
			sqlbv2.put("month", mMonth);
			sqlbv2.put("startdate", mStartDate);
			sqlbv2.put("enddate", mEndDate);
			sqlbv2.put("mngcom1", PubFun.RCh(mManageCom, "0", 8));
			sqlbv2.put("mngcom2", PubFun.RCh(mManageCom, "9", 8));
			
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);
			// 合并个险续期数据
			mCount = tSSRS.getMaxRow();
			logger.debug("tSSRS查询的结果是" + tSSRS.getMaxRow());
			if (tSSRS.getMaxRow() <= 0) {
				CError tError = new CError();
				tError.moduleName = "CardRateStatBL";
				tError.functionName = "checkData";
				tError.errorMessage = "没有所要查询的数据!";
				this.mErrors.addOneError(tError);
				return false;

			}
			String[][] result = new String[tSSRS.getMaxRow() + 1][4]; // 用来装最后的结果数据
			for (int main_count = 1; main_count <= tSSRS.getMaxRow(); main_count++) {
				String[] cols = new String[4];

				cols[0] = tSSRS.GetText(main_count, 1);// 代理机构名称
				cols[1] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count, 2)));// 首期保费
				cols[2] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count, 3)));// 实收保费
				cols[3] = mDecimalFormat1.format(Double.parseDouble(tSSRS.GetText(main_count, 4)));// 实收保费与承保保费的比例
				tSumFirstPrem += Double.parseDouble(tSSRS.GetText(main_count, 2));
				tSumActuPrem += Double.parseDouble(tSSRS.GetText(main_count, 3));
				alistTable.add(cols);
				result[main_count - 1] = cols;

			}
			String[] b_col = new String[4];
			b_col[0] = "合  计 ";
			b_col[1] = mDecimalFormat.format(tSumFirstPrem);
			b_col[2] = mDecimalFormat.format(tSumActuPrem);
			b_col[3] = mDecimalFormat1.format(Double.parseDouble(b_col[2])
					/ (Double.parseDouble(b_col[1]) + 0.000000001)); // 防止分母为0

			alistTable.add(b_col);
			result[tSSRS.getMaxRow()] = b_col;

			logger.debug("开始生成文件！");
			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, b_col);

			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("Title", mTitle);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

			if (!trans(result, tSSRS.getMaxRow() + 1, 4)) {
				logger.debug("将数组中为null的项置为空时出错");
				CError tError = new CError();
				tError.moduleName = "CardRateStatBL";
				tError.functionName = "trans";
				tError.errorMessage = "将数组中为null的项置为空时出错!";
				this.mErrors.addOneError(tError);

				return false;
			}

			TransferData tempTransferData = new TransferData();
			// 输入表头等信息
			tempTransferData.setNameAndValue("&count", mTitle);
			tempTransferData.setNameAndValue("&tjdate", PubFun.getCurrentDate());
			tempTransferData.setNameAndValue("&cbqj", mStartDate + "至" + mEndDate);

			HashReport xHashReport = new HashReport();
			String tpath = "";
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("XSCreatListPath");
			if (!tLDSysVarDB.getInfo()) {
				return false;
			}
			tpath = tLDSysVarDB.getSysVarValue();
			// 本地测试用
			// tpath = "d:\\temp\\";
			String tFileName = "";

			if (mReportType.equals("2")) {
				tFileName = "PolContinuedLAComRate1" + "_" + mStartDate + "_" + mEndDate + "_"
						+ mSalechnlType + "_" + mManageType + "_" + mReportType + "_" + mPremType + "_"
						+ this.mGI.ComCode;

				xHashReport.outputArrayToFile1(tpath + tFileName + ".xls", tpath
						+ "PolContinuedLAComRate1.xls", result, tempTransferData);

			} else if (mReportType.equals("4")) {
				tFileName = "PolContinuedLAComRate3" + "_" + mStartDate + "_" + mEndDate + "_"
						+ mSalechnlType + "_" + mManageType + "_" + mReportType + "_" + mPremType + "_"
						+ this.mGI.ComCode;

				xHashReport.outputArrayToFile1(tpath + tFileName + ".xls", tpath
						+ "PolContinuedLAComRate3.xls", result, tempTransferData);

			} else {
				tFileName = "PolContinuedLAComRate" + "_" + mStartDate + "_" + mEndDate + "_" + mSalechnlType
						+ "_" + mManageType + "_" + mReportType + "_" + mPremType + "_" + this.mGI.ComCode;

				xHashReport.outputArrayToFile1(tpath + tFileName + ".xls", tpath
						+ "PolContinuedLAComRate.xls", result, tempTransferData);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CardRateStatBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 将数组中为null的置为"",count1为数组的行数,count2为数组列数
	private boolean trans(String[][] result, int count1, int count2) {
		int a = 0;
		int b = 0;
		a = count1;
		b = count2;
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				if (result[i][j] == null) {
					result[i][j] = "";
				}
			}
		}
		return true;
	}

	private String getWherePart(String strColName, String strColValue, String strOperate) {
		String strWherePart = "";
		if (strColValue != null && !strColValue.equals("")) {
			if (!strOperate.equals("like") && !strOperate.equals("LIKE")) {
				strWherePart = " and " + strColName + strOperate + "'" + strColValue + "' ";
			} else if ((strOperate.equals("like") || strOperate.equals("LIKE")) && strColValue.length() > 1) {
				strWherePart = " and " + strColName + " like concat('" + strColValue + "','%') ";
			}
		}

		return strWherePart;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tG.ComCode = "86";

		String BackType = "4";
		String BackTypeName = "遗失率";

		String ManageCom = "86";
		String Grade = "";
		String GradeName = "";

		String CertifyCode = "";
		String ReceiveCom = "";

		String StartDate = "2009-02-05";
		String EndDate = "2009-03-20";

		logger.debug("****核销类型: " + BackType);
		logger.debug("****核销类型名称: " + BackTypeName);
		logger.debug("****管理机构: " + ManageCom);
		logger.debug("****统计粒度名称: " + GradeName);
		logger.debug("****统计粒度: " + Grade);
		logger.debug("****单证编码: " + CertifyCode);
		logger.debug("****接收者: " + ReceiveCom);
		logger.debug("****开始日期: " + StartDate);
		logger.debug("****结束日期: " + EndDate);

		VData tVData = new VData();
		tVData.addElement(BackType);
		tVData.addElement(BackTypeName);
		tVData.addElement(ManageCom);
		tVData.addElement(Grade);
		tVData.addElement(GradeName);
		tVData.addElement(CertifyCode);
		tVData.addElement(ReceiveCom);
		tVData.addElement(StartDate);
		tVData.addElement(EndDate);
		tVData.addElement(tG);

		CardRateStatBL tCardRateStatBL = new CardRateStatBL();
		tCardRateStatBL.submitData(tVData, "PRINT");

	}
}
