package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft<S/p>
 * 
 * @author zhangxing
 * @version 1.0
 */
public class GrpUWResultPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpUWResultPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 输入的查询sql语句
	private String mAgentCode = "";
	private String mFlag = "";
	boolean RefuseFlag = false; // 拒保标志
	boolean AddPremFlag = false; // 加费标志
	boolean SpceFlag = false; // 特约标志
	double Sum1 = 0;
	double Sum = 0;
	double SumCharge = 0;
	boolean PolFlag = false; // 判断有没有个人核保结论

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCGUWMasterSchema mLCGUWMasterSchema = new LCGUWMasterSchema();
	private LCGUWMasterSet LCGUWMasterSet1 = new LCGUWMasterSet();
	private LCGUWMasterSet jLCGUWMasterSet = new LCGUWMasterSet();
	private LCGUWMasterSet ttLCGUWMasterSet = new LCGUWMasterSet();
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	public GrpUWResultPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCGrpContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 根据印刷号查询打印队列中的纪录
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "";
		String prtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		LCGUWMasterSet tNormalLCGUWMasterSet = new LCGUWMasterSet();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(prtSeq);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		logger.debug(mLOPRTManagerSchema.getCode());
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());

		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			buildError("outputXML", "在取得LCGrpCont的数据时发生错误");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		// 代理人信息
		mAgentCode = mLCGrpContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息

		// 代理人组别
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet = tLCGUWMasterDB.query();

		ListTable tGrpRiskListTable = new ListTable(); // 对应ListTable
		String[] GrpRiskInfoTitle = new String[4]; //

		tGrpRiskListTable.setName("RISK1"); // 对应模版投保信息部分的行对象名(拒保)

		GrpRiskInfoTitle[0] = "RiskName"; // 险种名称
		GrpRiskInfoTitle[1] = "PassFlag"; // 核保结论
		GrpRiskInfoTitle[2] = "PremBefore"; // 核保前金额
		GrpRiskInfoTitle[3] = "PremAfter"; // 核保后金额

		String strRisk1[] = null;

		if (tLCGUWMasterSet.size() == 0) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LCGUWMaster的数据时发生错误");
			return false;
		}
		for (int i = 1; i <= tLCGUWMasterSet.size(); i++) {
			strRisk1 = new String[4];
			// 加空行
			strRisk1[0] = "";
			strRisk1[1] = "";
			strRisk1[2] = "";
			strRisk1[3] = "";
			mLCGUWMasterSchema = tLCGUWMasterSet.get(i).getSchema();
			mFlag = mLCGUWMasterSchema.getPassFlag();
			String tUWIdea = mLCGUWMasterSchema.getUWIdea();
			String tRiskCode = this.getRiskCode(mLCGUWMasterSchema
					.getGrpPolNo());
			String tRiskName = this.getRiskName(tRiskCode);
			double tNowPrem = 0;
			double tOldPrem = 0;
			double tChgPrem = 0;
			strRisk1[0] = "对本次投保的";
			strRisk1[1] = tRiskName;
			strRisk1[2] = "险种，核保意见为:";
			tSQL = "select nvl(prem,0) from lcgrppol where grpcontno='"
					+ mLCGrpContSchema.getGrpContNo() + "' and grppolno='"
					+ mLCGUWMasterSchema.getGrpPolNo() + "'";
			tNowPrem = Double.parseDouble(tExeSQL.getOneValue(tSQL));
			tSQL = "select nvl(sum(standprem*decode(floatrate,0,1,floatrate)),0)"
					+ " from lcduty where polno in (select polno from lcpol where grpcontno='"
					+ mLCGrpContSchema.getGrpContNo()
					+ "' and grppolno='"
					+ mLCGUWMasterSchema.getGrpPolNo() + "')";
			tOldPrem = Double.parseDouble(tExeSQL.getOneValue(tSQL));

			if (mFlag.equals("1")) {
				tNowPrem = Double.parseDouble(tExeSQL.getOneValue(tSQL));
				strRisk1[3] = "拒保";
				tGrpRiskListTable.add(strRisk1);

				// 对保费的显示在下一行
				strRisk1 = new String[4];
				if (tNowPrem != 0) {
					Sum1 = Sum1 + tNowPrem;
					strRisk1[0] = "";
					strRisk1[1] = "    应退保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tNowPrem);
					strRisk1[3] = "";
					tGrpRiskListTable.add(strRisk1);
				}
			} else if (mFlag.equals("x")) {

				strRisk1[3] = "下浮费率承保";
				tGrpRiskListTable.add(strRisk1);

				tChgPrem = tOldPrem - tNowPrem;
				if (tChgPrem != 0) {
					Sum1 = Sum1 + tChgPrem;
					// 对保费的显示在下一行
					strRisk1 = new String[4];
					strRisk1[0] = "";
					strRisk1[1] = "  核保前应交保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tOldPrem);
					strRisk1[3] = "核保后应交保费： ￥"
							+ mDecimalFormat.format(tNowPrem);
					tGrpRiskListTable.add(strRisk1);
				}
			} else if (mFlag.equals("s")) {
				strRisk1[3] = "上浮费率承保";
				tGrpRiskListTable.add(strRisk1);
				tChgPrem = tNowPrem - tOldPrem;
				if (tChgPrem != 0) {
					Sum = Sum + tChgPrem;
					// 对保费的显示在下一行
					strRisk1 = new String[4];
					strRisk1[0] = "";
					strRisk1[1] = "  核保前应交保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tOldPrem);
					strRisk1[3] = "核保后应交保费： ￥"
							+ mDecimalFormat.format(tNowPrem);
					tGrpRiskListTable.add(strRisk1);
				}
			} else if (mFlag.equals("r")) {
				strRisk1[3] = "特约承保-责任调整";
				tGrpRiskListTable.add(strRisk1);
				tChgPrem = tOldPrem - tNowPrem;
				if (tChgPrem != 0) {
					Sum1 = Sum1 + tChgPrem;
					// 对保费的显示在下一行
					strRisk1 = new String[4];
					strRisk1[0] = "";
					strRisk1[1] = "  核保前应交保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tOldPrem);
					strRisk1[3] = "核保后应交保费： ￥"
							+ mDecimalFormat.format(tNowPrem);
					tGrpRiskListTable.add(strRisk1);
				}

			} else if (mFlag.equals("m")) {
				strRisk1[3] = "特约承保-免陪额调整及陪付比例调整";
				tGrpRiskListTable.add(strRisk1);
				tChgPrem = tOldPrem - tNowPrem;
				if (tChgPrem != 0) {
					Sum1 = Sum1 + tChgPrem;
					// 对保费的显示在下一行
					strRisk1 = new String[4];
					strRisk1[0] = "";
					strRisk1[1] = "  核保前应交保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tOldPrem);
					strRisk1[3] = "核保后应交保费： ￥"
							+ mDecimalFormat.format(tNowPrem);
					tGrpRiskListTable.add(strRisk1);
				}

			} else if (mFlag.equals("q")) {
				strRisk1[3] = "特约承保-其它调整";
				tGrpRiskListTable.add(strRisk1);
				tChgPrem = tOldPrem - tNowPrem;
				if (tChgPrem != 0) {
					Sum1 = Sum1 + tChgPrem;
					// 对保费的显示在下一行
					strRisk1 = new String[4];
					strRisk1[0] = "";
					strRisk1[1] = "  核保前应交保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tOldPrem);
					strRisk1[3] = "核保后应交保费： ￥"
							+ mDecimalFormat.format(tNowPrem);
					tGrpRiskListTable.add(strRisk1);
				}

			}

			else if (mFlag.equals("9")) {
				strRisk1[3] = "正常承保";
				tGrpRiskListTable.add(strRisk1);
				tChgPrem = tOldPrem - tNowPrem;
				if (tChgPrem != 0) {
					Sum1 = Sum1 + tChgPrem;
					// 对保费的显示在下一行
					strRisk1 = new String[4];
					strRisk1[0] = "";
					strRisk1[1] = "  核保前应交保费： ";
					strRisk1[2] = "￥" + mDecimalFormat.format(tOldPrem);
					strRisk1[3] = "核保后应交保费： ￥"
							+ mDecimalFormat.format(tNowPrem);
					tGrpRiskListTable.add(strRisk1);
				}

			}
			if (tUWIdea != null && !"".equals(tUWIdea)) {
				// 对核保意见的显示在下一行
				strRisk1 = new String[4];
				strRisk1[0] = "";
				strRisk1[1] = tUWIdea;
				strRisk1[2] = "";
				strRisk1[3] = "";
				tGrpRiskListTable.add(strRisk1);
			}
			strRisk1 = new String[4];
			// 加空行
			strRisk1[0] = "";
			strRisk1[1] = "";
			strRisk1[2] = "";
			strRisk1[3] = "";
			tGrpRiskListTable.add(strRisk1);
			// tGrpRiskListTable.add(strRisk1);
			if (!mFlag.equals("1")) {
				tNormalLCGUWMasterSet.add(mLCGUWMasterSchema);
			}
		}

		// 对于没有下拒保结论的集体险种，则计算该险种下个别拒保的金额
		if (tNormalLCGUWMasterSet != null && tNormalLCGUWMasterSet.size() > 0) {
			double tRefusePrem = 0;
			for (int i = 1; i <= tNormalLCGUWMasterSet.size(); i++) {
				tRefusePrem = 0;
				LCGUWMasterSchema tLCGUWMasterSchema = tNormalLCGUWMasterSet
						.get(i);

				tExeSQL = new ExeSQL();
				tSQL = "select nvl(sum(a.prem),0) from lcpol a,lcuwmaster b where a.grpcontno='"
						+ mLCGrpContSchema.getGrpContNo()
						+ "'"
						+ " and a.grppolno='"
						+ tLCGUWMasterSchema.getGrpPolNo()
						+ "'"
						+ " and b.polno=a.polno and (b.passflag='1' or b.passflag='r')";
				tRefusePrem = Double.parseDouble(tExeSQL.getOneValue(tSQL));
				Sum1 = Sum1 + tRefusePrem;
			}
		}

		double Change = Sum - Sum1;
		if (Change >= 0) {
			SumCharge = Change;
		} else {
			SumCharge = Sum1 - Sum;
		}

		/**
		 * 对于个人保单的处理
		 */

		String tSql = "select a.insuredno,a.insuredname,a.polno,b.prem,'','j' "
				+ "from lcpol a,lcprem b where a.grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "'"
				+ " and b.polno=a.polno and b.payplantype = '03' and b.payplancode like '000000%'"
				+ " union "
				+ "select a.insuredno,a.insuredname,a.polno,0,b.speccontent,'t'"
				+ " from lcpol a,lcspec b "
				+ "where a.grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and b.proposalno=a.proposalno"
				+ " and b.serialNo = (select max(SerialNo) from lcspec where polno = a.polno)"
				+ " union "
				+ "select a.insuredno,a.insuredname,a.polno,c.prem,'','1'"
				+ " from lcpol a,lcuwmaster b,lcprem c"
				+ " where a.grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and b.polno=a.polno"
				+ " and b.passflag='1' and c.polno=a.polno"
				+ " union "
				+ "select a.insuredno,a.insuredname,a.polno,c.prem,'','x'"
				+ " from lcpol a,lcuwmaster b,lcprem c"
				+ " where a.grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and b.polno=a.polno"
				+ " and b.passflag='x' and c.polno=a.polno"
				+ " order by insuredno,polno";
		logger.debug("tSQL=" + tSql);

		SSRS tSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			PolFlag = true;
		}
		ListTable tRiskListTable = new ListTable(); // 个人核保结论对应ListTable
		String[] RiskInfoTitle = new String[8]; // 个人核保结论
		tRiskListTable.setName("RISK"); // 对应模版投保信息部分的行对象名(个人核保结论)
		logger.debug("个单=");
		String strRisk[] = null;
		int tNo = 0;
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			// 按被保人序号进行分组显示
			if (a == 1) {
				strRisk = new String[8];
				tNo = 1;
				strRisk[0] = "序号：";
				strRisk[1] = (new Integer(a)).toString(); // 序号
				strRisk[2] = "姓名："; // 被保险人姓名
				strRisk[3] = tSSRS.GetText(a, 2);
				strRisk[4] = "";
				strRisk[5] = "";
				strRisk[6] = "";
				strRisk[7] = "";

				tRiskListTable.add(strRisk);
			} else {
				String tInsuredNo = tSSRS.GetText(a, 1);
				if (tInsuredNo != null && tSSRS.GetText(a - 1, 1) != null
						&& !tInsuredNo.equals(tSSRS.GetText(a - 1, 1))) {
					tNo = tNo + 1;
					strRisk = new String[8];
					strRisk[0] = "序号：";
					strRisk[1] = (new Integer(tNo)).toString(); // 序号
					strRisk[2] = "姓名："; // 被保险人姓名
					strRisk[3] = tSSRS.GetText(a, 2);
					strRisk[4] = "";
					strRisk[5] = "";
					strRisk[6] = "";
					strRisk[7] = "";

					tRiskListTable.add(strRisk);
				}
			}
			strRisk = new String[8];
			strRisk[1] = "险种名：";
			strRisk[2] = getRiskName(getPolRiskCode(tSSRS.GetText(a, 3))); // 险种名称
			strRisk[3] = "";
			strRisk[4] = getPolState(tSSRS.GetText(a, 6)); // 核保结论

			if (tSSRS.GetText(a, 6).equals("j")) {
				// String PremSql = " select prem from lcprem where 1 = 1 "
				// + " and polno = '" + tSSRS.GetText(a, 3) + "'"
				// + " and payplantype = '0' ";

				String tAddPrem = tSSRS.GetText(a, 4);
				// String tPrem = tExeSQL.getOneValue(PremSql);
				if (tAddPrem == null || tAddPrem.equals("")) {
					tAddPrem = "0.0";
				}

				// if (tPrem == null || tPrem.equals("")) {
				// mErrors.copyAllErrors(tLAAgentDB.mErrors);
				// buildError("outputXML", "在取得个单保费信息的数据时发生错误");
				// return false;
				// }
				double AddPrem = Double.parseDouble(tAddPrem);
				strRisk[5] = "";
				strRisk[7] = " 加费金额：￥" + mDecimalFormat.format(AddPrem);
			} else if (tSSRS.GetText(a, 6).equals("t")) {
				strRisk[5] = "";
				strRisk[7] = "";
				tRiskListTable.add(strRisk);
				strRisk = new String[8];
				strRisk[0] = "";
				strRisk[1] = "";
				strRisk[2] = "特约内容为：" + tSSRS.GetText(a, 5); // ; //核保结论
				strRisk[3] = "";
				strRisk[4] = "";
				strRisk[5] = "";
				strRisk[6] = "";
				strRisk[7] = "";
			} else if (tSSRS.GetText(a, 6).equals("1")
					|| tSSRS.GetText(a, 6).equals("x")) {
				String Prem = tSSRS.GetText(a, 4);
				if (Prem == null || "".equals(Prem)) {
					Prem = "0.00";
				}
				strRisk[5] = "";
				strRisk[7] = "应退保费：￥"
						+ mDecimalFormat.format(Double.parseDouble(Prem));
			} else {
				strRisk[5] = "";
				strRisk[7] = "";
			}

			tRiskListTable.add(strRisk);
		}

		// 对于个单部分核保员、日期的显示，要求：如果是一个被保人一条信息，则空五行显示；
		// 否则空两行显示
		if (tSSRS.getMaxRow() <= 5) {
			for (int i = 0; i <= 5; i++) {
				strRisk = new String[8];
				strRisk[0] = "";
				strRisk[1] = "";
				strRisk[2] = "";
				strRisk[3] = "";
				strRisk[4] = "";
				strRisk[5] = "";
				strRisk[6] = "";
				strRisk[7] = "";
				tRiskListTable.add(strRisk);
			}
		} else {
			for (int i = 0; i <= 3; i++) {
				strRisk = new String[8];
				strRisk[0] = "";
				strRisk[1] = "";
				strRisk[2] = "";
				strRisk[3] = "";
				strRisk[4] = "";
				strRisk[5] = "";
				strRisk[6] = "";
				strRisk[7] = "";
				tRiskListTable.add(strRisk);
			}
		}
		// 显示核保员
		strRisk = new String[8];
		strRisk[0] = "";
		strRisk[1] = "";
		strRisk[2] = "";
		strRisk[3] = "";
		strRisk[4] = "";
		strRisk[5] = "";
		strRisk[6] = "核保员：" + mLCGrpContSchema.getUWOperator();
		strRisk[7] = "";
		tRiskListTable.add(strRisk);
		// 显示日期
		strRisk = new String[8];
		strRisk[0] = "";
		strRisk[1] = "";
		strRisk[2] = "";
		strRisk[3] = "";
		strRisk[4] = "";
		strRisk[5] = "";
		strRisk[6] = "日期：" + SysDate;
		strRisk[7] = "";
		tRiskListTable.add(strRisk);
		// 显示单证类型
		strRisk = new String[8];
		strRisk[0] = "";
		strRisk[1] = "";
		strRisk[2] = "";
		strRisk[3] = "";
		strRisk[4] = "";
		strRisk[5] = "";
		strRisk[6] = "0610A";
		strRisk[7] = "";
		tRiskListTable.add(strRisk);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GrpVerdict.vts", "printer");

		texttag.add("BarCode1", "UN066");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", mLCGrpContSchema.getGrpContNo());
		texttag.add("LCCont.AppntName", mLCGrpContSchema.getGrpName());
		texttag.add("AppntName", mLCGrpContSchema.getGrpName());
		texttag.add("AgentGroup", tLABranchGroupDB.getName());
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
		texttag.add("LCCont.UWOperator", mLCGrpContSchema.getUWOperator());
		texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());
		texttag.add("ManageComName",
				getComName(mLCGrpContSchema.getManageCom()));

		texttag.add("SumPrem", mLCGrpContSchema.getPrem());
		texttag.add("SysDate", SysDate);

		// 团体特约信息
		String gSpecSql = "select a.specreason,b.speccontent from LCGCUWMaster a,"
				+ " LCCGrpSpec b where a.grpcontno=b.grpcontno and "
				+ " a.grpcontno='" + mLCGrpContSchema.getGrpContNo() + "'";
		tSSRS = tExeSQL.execSQL(gSpecSql);
		if (tSSRS.getMaxRow() == 1) {
			logger.debug("团单层面有下特别约定");
			texttag.add("GrpSpecReason", tSSRS.GetText(1, 1).trim());
			texttag.add("GrpSpecContent", tSSRS.GetText(1, 2).trim());
			xmlexport.addDisplayControl("displayResult5");

		}
		// 补退费信息
		if (SumCharge > 0) {
			xmlexport.addDisplayControl("displayResult3");
			texttag.add("SumCharge", mDecimalFormat.format(SumCharge));
		}

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(tGrpRiskListTable, GrpRiskInfoTitle);

		if (PolFlag == true) {
			xmlexport.addDisplayControl("displayResult4"); // 模版上的加费部分的控制标记
			xmlexport.addListTable(tRiskListTable, RiskInfoTitle);
			xmlexport.addDisplayControl("display"); // 模版上的个人核保结论部分的控制标记
		}

		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	// 下面是一些辅助函数

	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return null;

		}
		return tLDCodeDB.getCodeName();
	}

	// 获得个单核保结论
	private String getPolState(String strPolState) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strPolState);
		tLDCodeDB.setCodeType("polstate");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return null;

		}
		return tLDCodeDB.getCodeName();
	}

	private String getRiskName(String strRiskCode) {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			buildError("outputXML", "在取得主险LMRisk的数据时发生错误");
			return null;
		}

		return tLMRiskDB.getRiskName();
	}

	private String getRiskCode(String strGrpPolNo) {
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(strGrpPolNo);
		if (!tLCGrpPolDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
			buildError("outputXML", "在取得LCGrpPol的数据时发生错误");
			return null;
		}
		return tLCGrpPolDB.getRiskCode();
	}

	// 根据polno得到riskcode
	private String getPolRiskCode(String strPolNo) {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(strPolNo);
		if (!tLCPolDB.getInfo()) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			buildError("outputXML", "在取得LCGrpPol的数据时发生错误");
			return null;
		}
		return tLCPolDB.getRiskCode();
	}

}
