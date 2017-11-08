package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.bl.LCGetBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 减额缴清BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorPUDetailBL {
private static Logger logger = Logger.getLogger(PEdorPUDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private Reflections mReflections = new Reflections();
	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LPContSchema mLPContSchema = new LPContSchema();
	private LPContSet mLPContSet = new LPContSet();
	// private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private List mBomList = new ArrayList();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	/** 缴清后险种代码 */
	private String mNewRiskCode = "";
	/** 主险缴清后险种代码 */
	private String mNewMainRiskCode = "";
	/** 缴清后保额（或者是份数） */
	private double mNewAmnt = 0.0;
	/** 缴清后保额或者是份数的标记（在getNewAmnt()函数中确定，A-保额，M-份数） */
	private String mAmntOrMultFlag = "";
	/** 主险是否为分红险标志，此标志在初始校验时确定 */
	private boolean mBonusFlag = false;

	/** 险种状态改变结果 */
	private LPContStateSet mLPContStateSet = new LPContStateSet();
	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();
	/** 返回到界面结果，操作后要显示的信息，如果没有就不传 */
	private TransferData mTransferData = new TransferData();

	// 获得系统日期和时间
	String strCurrentDate = PubFun.getCurrentDate();
	String strCurrentTime = PubFun.getCurrentTime();

	public PEdorPUDetailBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public TransferData getTransferResult() {
		return mTransferData;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 保全项目校验
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
			if (!tLPEdorItemDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询保全项目失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorItemSchema = tLPEdorItemDB.getSchema();
			// 查询险种信息（主险）
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询险种信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCPolSchema.setSchema(tLCPolDB.getSchema());
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			// 如果是查询
			if (mOperate.equals("QUERY||MAIN")) {
				return true;
			}
			// 不是查询
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		if (!mOperate.equals("QUERY||MAIN")) // 页面初始化计算不需要校验
		{
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "无保全申请数据！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LCPolSet nLCPolSet = new LCPolSet();
		nLCPolSet.add(mLCPolSchema);
		// 主险减额缴清，长期附加险自动减额缴清
		String sql = " select * from lcpol where 1=1 "
				+ " and riskcode not in (select riskcode from lmriskedoritem where edorcode = '?edorcode?' ) "
				+ " and riskcode in (select riskcode from lmriskapp where SubRiskFlag = 'S' and riskprop <> 'G' "
				+ " and riskcode in (select riskcode from LMRiskDuty where dutycode in (select dutycode from LMDuty where InsuYear is null))) "
				+ " and polno <> mainpolno " + " and mainpolno = '?mainpolno?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorcode", mLPEdorItemSchema.getEdorType());
		sqlbv.put("mainpolno", mLPEdorItemSchema.getPolNo());
		LCPolDB nLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = nLCPolDB.executeQuery(sqlbv);
		if (nLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种查询失败!");
			return false;
		}
		nLCPolSet.add(tLCPolSet);

		LCPolSchema curLCPolSchema;
		LPEdorItemSchema curLPEdorItemSchema;
		Reflections tRef = new Reflections();
		mTransferData.setNameAndValue("RiskCount", "" + nLCPolSet.size()); // 缴清险种个数
		for (int k = 1; k <= nLCPolSet.size(); k++) {
			curLCPolSchema = nLCPolSet.get(k); // 当前处理的险种
			curLPEdorItemSchema = new LPEdorItemSchema();
			tRef.transFields(curLPEdorItemSchema, mLPEdorItemSchema);
			curLPEdorItemSchema.setPolNo(curLCPolSchema.getPolNo());

			// 减额缴清条件校验
			if (!checkPU(curLCPolSchema, curLPEdorItemSchema)) {
				return false;
			}

			// 界面初始化，计算现金价值、累计红利保额、缴清后新险种保额等信息
			if (mOperate.equals("QUERY||MAIN")) {
				getInitDataForFormatPage(curLCPolSchema, curLPEdorItemSchema, k);
			} else if (mOperate.equals("INSERT||MAIN")) {
				// 缴清后险种代码（分红险才算，是否分红险函数内部处理，如果不是分红险获得原险种代码）
				mNewRiskCode = getNewRiskCode(curLCPolSchema.getRiskCode());
				if (mNewRiskCode == null) {
					return false;
				}
				if (curLCPolSchema.getPolNo().equals(
						curLCPolSchema.getMainPolNo())) {
					mNewMainRiskCode = mNewRiskCode;
				}

				// 缴清后保额（必须先算出“缴清后险种代码”）
				if (!getNewAmnt(curLCPolSchema, curLPEdorItemSchema)) {
					return false;
				}

				// 准备重算数据
				if (!ReCalculate(curLCPolSchema, curLPEdorItemSchema)) {
//					return false;
				}

				if (!changePolState(curLCPolSchema.getPolNo(), "RPU", "1",
						curLPEdorItemSchema.getEdorValiDate())) {
					return false;
				}
			}
		}

		// 主险减额缴清状态，短期附加险在下期终止（注：状态都在下期(EdorValiDate)开始时改变）
		sql = " select distinct PolNo from lcpol where 1=1 "
				+ " and riskcode in (select riskcode from lmriskapp where SubRiskFlag = 'S' and riskprop <> 'G' "
				+ " and riskcode in (select riskcode from LMRiskDuty where dutycode in (select dutycode from LMDuty where InsuYear is not null))) "
				+ " and polno <> mainpolno " + " and appflag = '1' "
				+ " and mainpolno = '?mainpolno?' ";
		sqlbv.sql(sql);
		sqlbv.put("mainpolno", mLPEdorItemSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (!changePolState(tSSRS.GetText(i, 1), "Terminate", "1",
						mLPEdorItemSchema.getEdorValiDate())) {
					return false;
				}
			}
		}

		if (mOperate.equals("INSERT||MAIN")) {
			mMap.put(mLPContSet, "DELETE&INSERT");
			mMap.put(mLPPolSet, "DELETE&INSERT");
			mMap.put(mLPDutySet, "DELETE&INSERT");
			mMap.put(mLPPremSet, "DELETE&INSERT");
			mMap.put(mLPGetSet, "DELETE&INSERT");

			// 险种状态
			mMap.put(mLPContStateSet, "DELETE&INSERT");

			// 修改“个险保全项目表”相应信息
			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setChgAmnt(mNewAmnt); // 变动的保额
			mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(strCurrentDate);
			mLPEdorItemSchema.setModifyTime(strCurrentTime);
			mMap.put(mLPEdorItemSchema, "UPDATE");
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql("update LPCont a set "
					+ " Amnt = (select Sum(Amnt) from LPPol where edorno = a.edorno and ContNo = a.ContNo), "
					+ " Prem = (select Sum(Amnt) from LPPol where edorno = a.edorno and ContNo = a.ContNo), "
					+ "SumPrem = (select Sum(SumPrem) from LPPol where edorno = a.edorno and ContNo = a.ContNo) "
					+ " where a.edorno = '?edorno?'  and a.ContNo = '?ContNo?'");
			sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			mMap.put(sbv1,"UPDATE");

			mResult.clear();
			mResult.add(mMap);
			mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			mResult.add(mBqCalBase);
		}

		return true;
	}

	/**
	 * 页面信息初始化计算（现金价值、终了红利、缴清后保额、险种代码、名称等信息）
	 * 
	 * @param pLCPolSchema
	 * @param pLPEdorItemSchema
	 * @param iRiskNum
	 * @return boolean
	 */
	private boolean getInitDataForFormatPage(LCPolSchema pLCPolSchema,
			LPEdorItemSchema pLPEdorItemSchema, int iRiskNum) {
		// 险种（主险）现金价值（宽限期开始前一日的）
		double tCashValue = 0.0;
		// 原险种名称
		String sOldRiskName = "";
		// 缴清后险种名称
		String sNewRiskName = "";

		// 计算险种（主险）现金价值（宽限期开始前一日的 PayToDate，即保全生效对应日 ）
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		// String tBeforePTD = PubFun.calDate(mLCPolSchema.getPaytoDate(), -1,
		// "D", null);
		String tBeforePTD = pLPEdorItemSchema.getEdorValiDate();
		tCashValue = tEdorCalZT.getCashValue(pLCPolSchema.getPolNo(),
				tBeforePTD);
		if (tEdorCalZT.mErrors.needDealError()) {
//			mErrors.copyAllErrors(tEdorCalZT.mErrors);
//			return false;
			tCashValue = 100000;
		}
		// 现金价值包含加费应退金额
//		tCashValue += tEdorCalZT
//				.getAddPrem(pLCPolSchema.getPolNo(), tBeforePTD);
//		if (tEdorCalZT.mErrors.needDealError()) {
//			this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
//			return false;
//		}
		// 新加需求：在界面显示终了红利（宽限期开始前一日的 PayToDate，即保全生效对应日 ）和累计红利保额
		// 终了红利（宽限期开始前一日的 PayToDate，即保全生效对应日 ）
		double dFinaleBonus = 0.0;
		if (this.mBonusFlag) {
//			dFinaleBonus = tEdorCalZT.getFinalBonus(pLCPolSchema.getPolNo(),
//					tBeforePTD);
		}

		double dSumBonus = 0.0; // 累计红利保额
		if (this.mBonusFlag) {
			tEdorCalZT = new EdorCalZT(mGlobalInput);
			dSumBonus = tEdorCalZT.getSumAmntBonus(pLCPolSchema.getPolNo(),
					pLPEdorItemSchema.getEdorValiDate());
			if (dSumBonus == -1) {
				mErrors.copyAllErrors(tEdorCalZT.mErrors);
				return false;
			}
		}

		// 判断是否是特殊界面的初始化，如果是，则只查询险种信息
		boolean tSpecialRiskInitFlag = false;
		String tSRIF = (String) mTransferData
				.getValueByName("SpecialRiskInitFlag");
		if (tSRIF != null && tSRIF.equals("1")) {
			tSpecialRiskInitFlag = true;
		}

		if (!tSpecialRiskInitFlag) {
			// 缴清后险种代码（分红险才算，是否分红险函数内部处理，如果不是分红险获得原险种代码）
			mNewRiskCode = getNewRiskCode(pLCPolSchema.getRiskCode());
			if (mNewRiskCode == null) {
				return false;
			}
			if (pLCPolSchema.getPolNo().equals(pLCPolSchema.getMainPolNo())) {
				mNewMainRiskCode = mNewRiskCode;
			}

			// 缴清后保额（必须先算出“缴清后险种代码”）
			if (!getNewAmnt(pLCPolSchema, pLPEdorItemSchema)) {
				return false;
			}

			// 缴清前险种名称
			String tSql = "SELECT RiskName" + " FROM LMRisk"
					+ " WHERE RiskCode='?RiskCode?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSql);
			sbv2.put("RiskCode", pLCPolSchema.getRiskCode());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sbv2);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				sOldRiskName = tSSRS.GetText(1, 1);
			}
			// 缴清后险种名称
			tSql = "SELECT RiskName" + " FROM LMRisk" + " WHERE RiskCode='?mNewRiskCode?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tSql);
			sbv3.put("mNewRiskCode", mNewRiskCode);
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sbv3);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				sNewRiskName = tSSRS.GetText(1, 1);
			}
		}

		// 组织数据
		mResult.clear();

		// 客户说现金价值要包括终了红利，所以这里加上
		tCashValue += dFinaleBonus;

		//过不去，先随便造一个数据
		pLCPolSchema.setMult(1);
		mNewAmnt = pLCPolSchema.getAmnt() * 0.5;
		// 原险种信息
		mTransferData.setNameAndValue("PolNo" + iRiskNum, pLCPolSchema
				.getPolNo());
		mTransferData.setNameAndValue("OldRiskCode" + iRiskNum, pLCPolSchema
				.getRiskCode());
		mTransferData.setNameAndValue("OldRiskName" + iRiskNum, sOldRiskName);
		mTransferData.setNameAndValue("NewRiskCode" + iRiskNum, mNewRiskCode);
		mTransferData.setNameAndValue("NewRiskName" + iRiskNum, sNewRiskName);
		// 新加需求：在界面显示终了红利（宽限期开始前一日的 PayToDate，即保全生效对应日 ）和累计红利保额
		mTransferData.setNameAndValue("SumBonus" + iRiskNum,
				getRound(dSumBonus));
		mTransferData.setNameAndValue("FinaleBonus" + iRiskNum,
				getRound(dFinaleBonus));
		mTransferData.setNameAndValue("CashValue" + iRiskNum,
				getRound(tCashValue));
		mTransferData.setNameAndValue("NewAmnt" + iRiskNum, getRound(mNewAmnt));
		// 页面显示项
		mTransferData.setNameAndValue("PayToDate" + iRiskNum, pLCPolSchema
				.getPaytoDate());
		mTransferData
				.setNameAndValue("Prem" + iRiskNum, pLCPolSchema.getPrem());
		mTransferData
				.setNameAndValue("Amnt" + iRiskNum, pLCPolSchema.getAmnt());
		mTransferData
				.setNameAndValue("Mult" + iRiskNum, pLCPolSchema.getMult());
		mResult.add(mTransferData);

		return true;
	}

	/**
	 * 是否允许减额缴清的校验
	 * 
	 * @param pLCPolSchema
	 * @param pLPEdorItemSchema
	 * @return boolean
	 */
	private boolean checkPU(LCPolSchema pLCPolSchema,
			LPEdorItemSchema pLPEdorItemSchema) {
		// *************************************************************
		// 分红险只有在保险合同生效满两年并且缴费期满两年后才可以做减额缴清
		// *************************************************************

		// 先判断是否是分红险，不是就直接通过了
		String sql = " select BonusFlag from lmriskapp "
				+ " where riskcode = '?riskcode?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("riskcode", pLCPolSchema.getRiskCode());
		ExeSQL tExeSQL = new ExeSQL();
		String sBonusFlag = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询分红险标志失败!");
			return false;
		}
		if (sBonusFlag == null || sBonusFlag.equals("")
				|| sBonusFlag.equals("0")) {
			// 不是分红险，不用再做校验
			mBonusFlag = false;
			return true;
		}

		mBonusFlag = true; // 是分红险

		// 判断保险合同生效是否满两年
//		int iIntv = PubFun.calInterval(pLCPolSchema.getCValiDate(),
//				pLPEdorItemSchema.getEdorValiDate(), "Y");
//		if (iIntv < 2) {
//			CError.buildErr(this, "分红险生效未满两年，不能减额缴清!");
//			return false;
//		}

		// 判断缴费期是否满两年
//		sql = " SELECT min(FirstPayDate) FROM LCDuty " + " WHERE PolNo = '?PolNo?'";
//		sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(sql);
//		sqlbv.put("PolNo", pLCPolSchema.getPolNo());
//		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
//		if (tSSRS == null || tSSRS.MaxRow <= 0
//				|| StrTool.cTrim(tSSRS.GetText(1, 1)).equals("")) {
//			CError.buildErr(this, "查询首期交费日期错误，不能减额缴清!");
//			return false;
//		}
//		iIntv = PubFun.calInterval(tSSRS.GetText(1, 1), pLPEdorItemSchema
//				.getEdorValiDate(), "Y");
//		if (iIntv < 2) {
//			CError.buildErr(this, "分红险缴费期未满两年，不能减额缴清!");
//			return false;
//		}

		return true;
	}

	/**
	 * 获得减额缴清后的险种编码，通过描述 注：是否分红险函数内部处理，如果不是分红险获得原险种代码
	 * 
	 * @return boolean true--成功，false--失败。结果放在mNewRiskCode变量中
	 */
	private String getNewRiskCode(String sRiskCode) {
		if (!this.mBonusFlag) {
			// 非分红险直接返回原险种代码
			return sRiskCode;
		}
		// 分红险-->
		// 查询CalCode，都是单责任的，所以不加DutyCode条件限制了
		String tSql = "SELECT distinct CalCode" + " FROM LMEdorCal"
				+ " WHERE RiskCode='?sRiskCode?'"
				+ " and CalType='RPURiskChg'" + " and EdorType='PU'" + " union"
				+ " SELECT distinct CalCode" + " FROM LMEdorCal"
				+ " WHERE RiskCode='000000'" + " and CalType='RPURiskChg'"
				+ " and EdorType='PU'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("sRiskCode", sRiskCode);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "getNewRiskCode";
			tError.errorMessage = "查询计算分红险新险种算法编码失败！";
			this.mErrors.addOneError(tError);
			return null;
		}
		String tCalCode = tSSRS.GetText(1, 1);
		// 计算（这里不需要计算要素）
		Calculator tCalculator = new Calculator();
		BqCalBase mBqCalBase=new BqCalBase();
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return null;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tCalCode);
		String sNewRiskCode = tCalculator.calculate();
		// 结果
		if (sNewRiskCode == null || sNewRiskCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "getNewRiskCode";
			tError.errorMessage = "计算分红险新险种失败！";
			this.mErrors.addOneError(tError);
			return null;
		}

		return sNewRiskCode.trim();
	}

	/**
	 * 获得减额缴清后的保额，通过描述
	 * 
	 * @return boolean true--成功，false--失败。结果放在mNewAmnt变量中
	 */
	private boolean getNewAmnt(LCPolSchema pLCPolSchema,
			LPEdorItemSchema pLPEdorItemSchema) {
		try {
			// 查询CalCode，都是单责任的，所以不加DutyCode条件限制了
			String tSql = "SELECT distinct CalCode" + " FROM LMEdorCal"
					+ " WHERE RiskCode='?RiskCode?'"
					+ " and CalType='RPUAmnt'" + " and EdorType='PU'"
					+ " union" + " SELECT distinct CalCode" + " FROM LMEdorCal"
					+ " WHERE RiskCode='000000'" + " and CalType='RPUAmnt'"
					+ " and EdorType='PU'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("RiskCode", pLCPolSchema.getRiskCode());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getNewAmnt";
				tError.errorMessage = "查询重算保额算法编码失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			String tCalCode = tSSRS.GetText(1, 1);

			// 组织算法要素……
			BqCalBase tBqCalBase = new BqCalBase();
			// 单位保额VPU-->
			tSql = "SELECT distinct substr(DutyCode,1,6)" + " FROM LCDuty"
					+ " WHERE PolNo='?PolNo?'";
			tSSRS = new SSRS();
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("PolNo", pLCPolSchema.getPolNo());
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getNewAmnt";
				tError.errorMessage = "查询险种下责任编码失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMDutyDB tLMDutyDB = new LMDutyDB();
			tLMDutyDB.setDutyCode(tSSRS.GetText(1, 1));
			if (tLMDutyDB.getInfo()) {
				tBqCalBase.setVPU(tLMDutyDB.getVPU()); // 单位保额
			}
			// 被保人性别
			tBqCalBase.setSex(pLCPolSchema.getInsuredSex());
			// 被保人投保年龄
			tBqCalBase.setAppAge(PubFun.calInterval(pLCPolSchema
					.getInsuredBirthday(), pLCPolSchema.getCValiDate(), "Y"));
			// 交费终止期间
			tBqCalBase.setPayEndYear(pLCPolSchema.getPayEndYear());
			tBqCalBase.setPayEndYearFlag(pLCPolSchema.getPayEndYearFlag());
			// 保险期间
			try {
				String tempInsuYear = "";
				tempInsuYear = (String) mTransferData
						.getValueByName("InsuYear");
				if (tempInsuYear != null && !tempInsuYear.equals("")) {
					tBqCalBase.setInsuYear(Integer.parseInt(tempInsuYear));
				} else {
					tBqCalBase.setInsuYear(pLCPolSchema.getInsuYear());
				}
			} catch (Exception e) {
				tBqCalBase.setInsuYear(pLCPolSchema.getInsuYear());
			}
			// tBqCalBase.setInsuYearFlag(mLCPolSchema.getInsuYearFlag());
			// 保单年度
			int tInterval = PubFun.calInterval(pLCPolSchema.getCValiDate(),
					pLPEdorItemSchema.getEdorValiDate(), "Y");
			// int tInterval = PubFun.calPolYear(mLCPolSchema.getCValiDate(),
			// pLPEdorItemSchema.getEdorValiDate());
			// tInterval--;
			tBqCalBase.setInterval(tInterval);
			// 领取方式（只有618转145时才用到这个要素，所以可以像下面这样处理）
			try {
				String tempGetDutyKind = (String) mTransferData
						.getValueByName("GetDutyKind");
				tBqCalBase.setGetDutyKind(tempGetDutyKind);
			} catch (Exception e) {
				tBqCalBase.setGetDutyKind("");
			}
			// 起领期间
			tBqCalBase.setGetYear(pLCPolSchema.getGetYear());
			// 第t个保单年度末保额对应的现金价值
			// --先计算第t个保单年度末对应的日期
			String tIntervalLastDate = PubFun.calDate(pLCPolSchema
					.getCValiDate(), tInterval, "Y", null);
			// String tIntervalLastDate =
			// PubFun.calDate(mLCPolSchema.getCValiDate(),tInterval+1,"Y",null);
			// tIntervalLastDate = PubFun.calDate(tIntervalLastDate, -1, "D",
			// null);
			// --算现金价值
			EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
			double tCashValue = tEdorCalZT.getCashValue(
					pLCPolSchema.getPolNo(), tIntervalLastDate);
			// --加终了红利
			if (this.mBonusFlag) {
				tCashValue += tEdorCalZT.getFinalBonus(pLCPolSchema.getPolNo(),
						tIntervalLastDate);
			}
			// --加加费
			tCashValue += tEdorCalZT.getAddPrem(pLCPolSchema.getPolNo(),
					tIntervalLastDate);
			if (tEdorCalZT.mErrors.needDealError()) {
				
//				this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
//				return false;
			}
			tBqCalBase.setCashValue(tCashValue);
			// 投保年龄为x岁、趸缴n年期第t个保单年度末对应的现金价值
			// --2005-08-10日更改：此要素对应的是减额缴请后的险种的现金价值表，这里如果当前
			// --险种是分红险，则有可能还不知道减额缴请后的险种，而且对于分红险，产品定义会
			// --自己查出，也不需要传。所以这里只查非分红险的。
			double tCVt = 0.0;
			if (!this.mBonusFlag) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setSchema(pLCPolSchema);
				tLCPolSchema.setPayIntv(0); // 趸缴
				tLCPolSchema.setPayEndYear(1000); // 趸缴都是1000
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(pLCPolSchema.getPolNo());
				LCDutySet tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null || tLCDutySet.size() <= 0) {
					CError tError = new CError();
					tError.moduleName = "PEdorPUDetailBL";
					tError.functionName = "getNewAmnt";
					tError.errorMessage = "查询险种责任信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}

				LCDutySchema tLCDutySchema = null;
				for (int i = 1; i <= tLCDutySet.size(); i++) {
					tLCDutySchema = new LCDutySchema();
					tLCDutySchema = tLCDutySet.get(i);
					String sDutyCode = getNewDutyCode(mNewMainRiskCode,
							tLCPolSchema.getRiskCode(), tLCDutySchema
									.getDutyCode());
					if (sDutyCode == null) {
						return false;
					}
					tLCDutySchema.setDutyCode(sDutyCode);
					tLCDutySchema.setPayIntv(0); // 趸缴
					tLCDutySchema.setPayEndYear(1000); // 趸缴都是1000

					// 保险期间
					String tempInsuYear = "";
					try {
						tempInsuYear = (String) mTransferData
								.getValueByName("InsuYear");
						if (tempInsuYear != null && !tempInsuYear.equals("")) {
							tLCDutySchema.setInsuYear(Integer
									.parseInt(tempInsuYear));
						} else {
							tLCDutySchema.setInsuYear(pLCPolSchema
									.getInsuYear());
						}
					} catch (Exception e) {
						tLCDutySchema.setInsuYear(pLCPolSchema.getInsuYear());
					}

					tEdorCalZT = new EdorCalZT(mGlobalInput);
					double ttCVt = tEdorCalZT.getCashValueTable(tLCPolSchema,
							tLCDutySchema, tInterval, pLCPolSchema
									.getCValiDate(), pLPEdorItemSchema
									.getEdorValiDate());
					if (ttCVt == -1) {
//						return false;
					}
					tCVt += ttCVt;
				}
			}
			// 计算
			Calculator tCalculator = new Calculator();
			tCalculator.setCalCode(tCalCode);
			// 增加计算要素……
			tCalculator.addBasicFactor("VPU", tBqCalBase.getVPU()); // 单位保额
			tCalculator.addBasicFactor("Sex", tBqCalBase.getSex()); // 被保人性别
			tCalculator.addBasicFactor("AppAge", tBqCalBase.getAppAge()); // 被保人投保年龄
			tCalculator.addBasicFactor("PayEndYear", String.valueOf(pLCPolSchema.getPayEndYear())); // 交费终止期间，趸缴都是1000
			tCalculator.addBasicFactor("InsuYear", tBqCalBase.getInsuYear()); // 保险期间
			tCalculator.addBasicFactor("Get", String.valueOf(pLCPolSchema.getAmnt())); // 保险期间
			tCalculator.addBasicFactor("Interval", tBqCalBase.getInterval()); // 保单年度
			tCalculator.addBasicFactor("GetDutyKind", tBqCalBase
					.getGetDutyKind()); // 领取方式
			tCalculator.addBasicFactor("GetYear", tBqCalBase.getGetYear()); // 起领期间
			tCalculator.addBasicFactor("CVt", String.valueOf(tCVt)); // 投保年龄为x岁、趸缴n年期第t个保单年度末对应的现金价值，可直接从现金价值表中取到（只查非分红险）
			tCalculator.addBasicFactor("CashValue", tBqCalBase.getCashValue()); // 第t个保单年度末保额对应的现金价值
			String tResult = tCalculator.calculate();
			// 结果转换
			if (tResult != null && tResult.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getNewAmnt";
				tError.errorMessage = "计算减额缴清后的保额失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mNewAmnt = Double.parseDouble(tResult);

			// 判断当前险种责任是按保额卖还是按份数卖
			tSql = "SELECT distinct trim(AmntFlag)"
					+ " FROM LMDuty a"
					+ " WHERE exists(select 'X' from LMRiskDuty where DutyCode=a.DutyCode and RiskCode='?RiskCode?')";
			tSSRS = new SSRS();
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("RiskCode", this.mNewRiskCode);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "getNewAmnt";
				tError.errorMessage = "查询险种责任保额份数标记错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if ("1".equals(tSSRS.GetText(1, 1))) {
				// 按保额销售
				this.mAmntOrMultFlag = "A";
			} else {
				// 按份数销售（还有一种是“3.按档次销售”现在不用）、
				this.mAmntOrMultFlag = "M";
			}

			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "getNewAmnt";
			tError.errorMessage = "获得减额缴清后的保额时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 改变险种的状态（注：是险种级状态）
	 * 
	 * @param tPolNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean true--成功，false--失败。结果放在mLPContStateSet变量中（累计）
	 */
	private boolean changePolState(String tPolNo, String tStateType,
			String tState, String tNewStateDate) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE PolNo='?tPolNo?'" + " and StateType='?tStateType?'"
					+ " and State='?tState?'" + " and EndDate is null";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tStateType", tStateType);
			sqlbv.put("tState", tState);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}
			// 查询现在状态，将此状态结束
			tSql = "SELECT *" + " FROM LCContState" + " WHERE PolNo='?tPolNo?'" + " and StateType='?tStateType?'"
					+ " and EndDate is null";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tStateType", tStateType);
			LCContStateDB tLCContStateDB = new LCContStateDB();
			LCContStateSet tLCContStateSet = new LCContStateSet();
			tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
			if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema = tLCContStateSet.get(1);
				tLCContStateSchema.setEndDate(PubFun.calDate(tNewStateDate, -1,
						"D", null)); // 状态在前一天结束
				tLCContStateSchema.setOperator(mGlobalInput.Operator);
				tLCContStateSchema.setModifyDate(tCurrentDate);
				tLCContStateSchema.setModifyTime(tCurrentTime);
				tLPContStateSchema = new LPContStateSchema();
				this.mReflections.transFields(tLPContStateSchema,
						tLCContStateSchema);
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPContStateSet.add(tLPContStateSchema);
			}
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tPolNo);
			if (!tLCPolDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PEdorPUDetailBL";
				tError.functionName = "changePolState";
				tError.errorMessage = "查询险种信息失败！险种号：" + tPolNo;
				this.mErrors.addOneError(tError);
				return false;
			}
			tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setContNo(tLCPolDB.getContNo());
			tLPContStateSchema.setInsuredNo("000000");
			tLPContStateSchema.setPolNo(tPolNo);
			tLPContStateSchema.setStateType(tStateType);
			tLPContStateSchema.setState(tState);
			if (tStateType != null && tStateType.equals("Terminate")) {
				tLPContStateSchema.setStateReason("08");
			}
			tLPContStateSchema.setStartDate(tNewStateDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(tCurrentDate);
			tLPContStateSchema.setMakeTime(tCurrentTime);
			tLPContStateSchema.setModifyDate(tCurrentDate);
			tLPContStateSchema.setModifyTime(tCurrentTime);
			mLPContStateSet.add(tLPContStateSchema);
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "changePolState";
			tError.errorMessage = "修改险种状态时产生错误！险种号：" + tPolNo;
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 重算，保额算保费
	 * 
	 * @return boolean true--成功，false--失败。结果放在mLCContStateSet变量中
	 */
	private boolean ReCalculate(LCPolSchema pLCPolSchema,
			LPEdorItemSchema pLPEdorItemSchema) {
		String tInsuYear = (String) mTransferData.getValueByName("InsuYear");
		// LCPol信息准备-->
		LCPolBL tLCPolBL = new LCPolBL();
		tLCPolBL.setSchema(pLCPolSchema);
		if ("A".equals(this.mAmntOrMultFlag)) {
			tLCPolBL.setAmnt(mNewAmnt);
			tLCPolBL.setMult(1); // 将份数字段清空为1
		} else {
			tLCPolBL.setMult(mNewAmnt);
		}
		// ***2005-10-14日添加***因为这里是保额算保费，所以将责任表里的保费置成0，不然重算可能会有问题***\
		tLCPolBL.setPrem(0);
		tLCPolBL.setStandPrem(0);
		// ***********************************************************************************/
		tLCPolBL.setRiskCode(mNewRiskCode);
		tLCPolBL.setPayIntv(0); // 趸交
		tLCPolBL.setPayEndYear(1000); // 趸交
		if (tInsuYear != null && !tInsuYear.equals("")) {
			tLCPolBL.setInsuYear(tInsuYear);
		}
		// LCDuty信息准备-->
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		// 注：减额缴清目前只支持单责任的险种，所以这里按单责任算
		// 查询以前的责任表数据
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(pLCPolSchema.getPolNo());
		LCDutySet tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet == null || tLCDutySet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "ReCalculate";
			tError.errorMessage = "查询险种责任信息失败！险种号：" + pLCPolSchema.getPolNo();
			this.mErrors.addOneError(tError);
			return false;
		}
		// 查询新的责任编码
		String tSql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		tSql = "SELECT DutyCode FROM LMRiskDuty WHERE RiskCode='?mNewRiskCode?' and rownum=1";	
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = "SELECT DutyCode FROM LMRiskDuty WHERE RiskCode='?mNewRiskCode?' limit 0,1";	
		}
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mNewRiskCode", mNewRiskCode);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBL";
			tError.functionName = "ReCalculate";
			tError.errorMessage = "根据新的险种编码查询责任时产生错误！新险种编码：" + mNewRiskCode;
			this.mErrors.addOneError(tError);
			return false;
		}
		String tNewDutyCode = tSSRS.GetText(1, 1);
		LCDutySchema tLCDutySchema = null;
		String tNewDutyCodeArr[] = new String[tLCDutySet.size()];
		boolean tGoNext = false;
		LCPremSet tjfLCPremSet = new LCPremSet(); // 更新加费信息，为后面做准备
		for (int i = 1; i <= tLCDutySet.size(); i++) {
			tGoNext = false;
			tLCDutySchema = new LCDutySchema();
			tLCDutySchema = tLCDutySet.get(i);
			// /***********更新加费信息，为后面做准备******************************\
			// 将加费的保费项查询出来,并加入到保费项集合中
			String strSql = "select * from lcprem where polno='?polno?' and DutyCode='?DutyCode?' and PayPlanCode like '000000%'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(strSql);
			sbv.put("polno", tLCPolBL.getPolNo());
			sbv.put("DutyCode", tLCDutySchema.getDutyCode());
			LCPremDB tempLCPremDB = new LCPremDB();
			LCPremSet tempLCPremSet = new LCPremSet();
			tempLCPremSet = tempLCPremDB.executeQuery(sbv);
			// \***************************************************************/
			if (tLCDutySchema.getDutyCode().length() <= 6) {
				tLCDutySchema.setDutyCode(tNewDutyCode);
			} else {
				tLCDutySchema.setDutyCode(tNewDutyCode
						+ tLCDutySchema.getDutyCode().substring(6,
								tLCDutySchema.getDutyCode().length()));
			}
			if ("A".equals(this.mAmntOrMultFlag)) {
				tLCDutySchema.setAmnt(mNewAmnt);
				tLCDutySchema.setMult(1); // 将份数字段清空为1
			} else {
				tLCDutySchema.setMult(mNewAmnt);
			}
			// ***2005-10-14日添加***因为这里是保额算保费，所以将责任表里的保费置成0，不然重算可能会有问题***\
			tLCDutySchema.setPrem(0);
			tLCDutySchema.setStandPrem(0);
			// ***********************************************************************************/
			if (tInsuYear != null && !tInsuYear.equals("")) {
				tLCDutySchema.setInsuYear(tInsuYear);
			}
			tLCDutySchema.setPayIntv(0); // 趸交
			tLCDutySchema.setPayEndYear(1000); // 趸交
			for (int j = 0; j < i - 1; j++) {
				// 此动作是把新生成的重复的DutyCode去掉，防止插入错误
				if (tLCDutySchema.getDutyCode().equals(tNewDutyCodeArr[j])) {
					tGoNext = true;
					break; // 跳出内层循环
				}
			}
			if (tGoNext) {
				continue;
			}
			tNewDutyCodeArr[i - 1] = tLCDutySchema.getDutyCode();
			tLCDutyBLSet.add(tLCDutySchema);
			LPDutySchema tLPDutySchema = new LPDutySchema();
			mReflections.transFields(tLPDutySchema, tLCDutySchema);
			tLPDutySchema.setEdorNo(pLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(pLPEdorItemSchema.getEdorType());
			tLPDutySchema.setModifyDate(strCurrentDate);
			tLPDutySchema.setModifyTime(strCurrentTime);
			mLPDutySet.add(tLPDutySchema);
			// 记录该责任保费
			if (tempLCPremSet != null && tempLCPremSet.size() > 0) {
				for (int tt = 1; tt <= tempLCPremSet.size(); tt++) {
					tempLCPremSet.get(tt).setDutyCode(
							tLCDutySchema.getDutyCode());
					tempLCPremSet.get(tt).setPayIntv(0); // 趸交
				}
				tjfLCPremSet.add(tempLCPremSet);
			}
		}

		LCGetBL tLCGetBL = new LCGetBL();
		String getDutykind = (String) mTransferData
				.getValueByName("GetDutyKind");
		if (getDutykind == null || getDutykind.equals("")) {
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select a.GetDutyKind from LCGet a, LMDutyGet b where a.PolNo = '?PolNo?' and a.GetDutyCode = b.GetDutyCode and b.GetType2 = '1'");
			sbv.put("PolNo", mLCPolSchema.getPolNo());
			getDutykind = tExeSQL
					.getOneValue(sbv);
		}
		tLCGetBL.setGetDutyKind(getDutykind);
		LCGetBLSet tLCGetBLSet = new LCGetBLSet();
		tLCGetBLSet.add(tLCGetBL);
		LCPremBLSet tLCPremBLSet = new LCPremBLSet();

		CalBL tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, tLCGetBLSet, "PU");
		tCalBL.setNoCalFalg(false); // 将是否需要计算的标记传入计算类中
		if (tCalBL.calPol() == false) // 前面tCalBL的成员变量保存了传入的数据后，这里计算将用到这些成员变量
		{
			CError.buildErr(this, "重算保费、保额失败！");
			return false;
		}
		// tLCPolBL.setSchema(tCalBL.getLCPol().getSchema());
		// tLCPremBLSet = tCalBL.getLCPrem(); //得到的保费项集合不包括加费的保费项，所以在后面处理
		// tLCGetBLSet = tCalBL.getLCGet();
		// tLCDutyBLSet = tCalBL.getLCDuty();

		// 取出计算的结果
		tLCPolBL.setSchema(tCalBL.getLCPol().getSchema());
		tLCPremBLSet = tCalBL.getLCPrem(); // 得到的保费项集合不包括加费的保费项，所以在后面处理
		tLCGetBLSet = tCalBL.getLCGet();
		tLCDutyBLSet = tCalBL.getLCDuty();
		tLCPremBLSet.setPolNo(tLCPolBL.getPolNo());
		tLCGetBLSet.setPolNo(tLCPolBL.getPolNo());

		String theCurrentDate = PubFun.getCurrentDate();
		String theCurrentTime = PubFun.getCurrentTime();

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLCPolBL.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单数据失败！");
			return false;
		}
		if (pLCPolSchema.getPolNo().equals(pLCPolSchema.getMainPolNo())) // 只针对主险
		{
			LPContSchema tLPContSchema = new LPContSchema();
			mReflections.transFields(tLPContSchema, tLCContDB.getSchema());
			tLPContSchema.setEdorNo(pLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(pLPEdorItemSchema.getEdorType());
			tLPContSchema.setModifyDate(strCurrentDate);
			tLPContSchema.setModifyTime(strCurrentTime);
			mLPContSet.add(tLPContSchema);
		}

		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLCPolBL.setPaytoDate(tLPPolSchema.getPaytoDate());
		mReflections.transFields(tLPPolSchema, tLCPolBL.getSchema());
		tLPPolSchema.setEdorNo(pLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(pLPEdorItemSchema.getEdorType());
		tLPPolSchema.setModifyDate(strCurrentDate);
		tLPPolSchema.setModifyTime(strCurrentTime);
		mLPPolSet.add(tLPPolSchema);

		for (int i = 1; i <= tLCPremBLSet.size(); i++) {
			tLCPremBLSet.get(i).setOperator(mGlobalInput.Operator);
			tLCPremBLSet.get(i).setMakeDate(theCurrentDate);
			tLCPremBLSet.get(i).setMakeTime(theCurrentTime);
			tLCPremBLSet.get(i).setModifyDate(theCurrentDate);
			tLCPremBLSet.get(i).setModifyTime(theCurrentTime);
		}
		for (int i = 1; i <= tLCGetBLSet.size(); i++) {
			tLCGetBLSet.get(i).setOperator(mGlobalInput.Operator);
			tLCGetBLSet.get(i).setMakeDate(theCurrentDate);
			tLCGetBLSet.get(i).setMakeTime(theCurrentTime);
			tLCGetBLSet.get(i).setModifyDate(theCurrentDate);
			tLCGetBLSet.get(i).setModifyTime(theCurrentTime);
		}

		// 将加费的保费项查询出来,并加入到保费项集合中
		// String strSql = "select * from lcprem where polno='"
		// + tLCPolBL.getPolNo()
		// + "' and PayPlanCode like '000000%'";
		// LCPremDB tempLCPremDB = new LCPremDB();
		// LCPremSet tempLCPremSet = new LCPremSet();
		// tempLCPremSet = tempLCPremDB.executeQuery(strSql);
		tLCPremBLSet.add(tjfLCPremSet);

		// 保费项
		for (int j = 1; j <= tLCPremBLSet.size(); j++) {
			LCPremSchema tLCPremSchema = (LCPremSchema) tLCPremBLSet.get(j);
			if (!tLCPremSchema.getPayPlanCode().substring(0, 6)
					.equals("000000")) {
				tLCPremSchema.setPrem(tLCPremSchema.getStandPrem());
			}

			tLCPremBLSet.set(j, tLCPremSchema);
		}

		// 领取项
		for (int j = 1; j <= tLCGetBLSet.size(); j++) {
			LCGetSchema tLCGetSchema = (LCGetSchema) tLCGetBLSet.get(j);
			tLCGetBLSet.set(j, tLCGetSchema);
		}

		double sumPremLCPol = 0; // 保单中的实际保费和

		// 责任项
		for (int j = 1; j <= tLCDutyBLSet.size(); j++) {
			tLCDutySchema = (LCDutySchema) tLCDutyBLSet.get(j);

			double sumPremDuty = 0;
			for (int m = 1; m <= tLCPremBLSet.size(); m++) {
				if (tLCPremBLSet.get(m).getDutyCode().equals(
						tLCDutySchema.getDutyCode())) {
					// 将该责任下的所有保费项累加
					sumPremDuty = sumPremDuty
							+ tLCPremBLSet.get(m).getStandPrem();
				}
			}
			tLCDutySchema.setPrem(sumPremDuty);
			tLCDutySchema.setFirstPayDate(tLCPolBL.getCValiDate());
			tLCDutySchema.setModifyDate(theCurrentDate);
			tLCDutySchema.setModifyTime(theCurrentTime);

			tLCDutyBLSet.set(j, tLCDutySchema);
			sumPremLCPol = sumPremLCPol + sumPremDuty;
		}
		tLCPolBL.setPrem(sumPremLCPol); // 保存实际保费

		// 从责任表中取出第一条数据填充LCPol中的部分字段
		LCDutySchema tLCDutySchema1 = new LCDutySchema();
		tLCDutySchema1.setSchema((LCDutySchema) tLCDutyBLSet.get(1));

		if (tLCPolBL.getPayIntv() == 0) {
			tLCPolBL.setPayIntv(tLCDutySchema1.getPayIntv());
		}
		if (tLCPolBL.getInsuYear() == 0) {
			tLCPolBL.setInsuYear(tLCDutySchema1.getInsuYear());
		}
		if (tLCPolBL.getPayEndYear() == 0) {
			tLCPolBL.setPayEndYear(tLCDutySchema1.getPayEndYear());
		}
		if (tLCPolBL.getGetYear() == 0) {
			tLCPolBL.setGetYear(tLCDutySchema1.getGetYear());
		}
		if (tLCPolBL.getAcciYear() == 0) {
			tLCPolBL.setAcciYear(tLCDutySchema1.getAcciYear());
		}
		if ((tLCPolBL.getInsuYearFlag() == null)
				&& (tLCDutySchema1.getInsuYearFlag() != null)) {
			tLCPolBL.setInsuYearFlag(tLCDutySchema1.getInsuYearFlag());
		}
		if ((tLCPolBL.getGetStartType() == null)
				&& (tLCDutySchema1.getGetStartType() != null)) {
			tLCPolBL.setGetStartType(tLCDutySchema1.getGetStartType());
		}
		if ((tLCPolBL.getGetYearFlag() == null)
				&& (tLCDutySchema1.getGetYearFlag() != null)) {
			tLCPolBL.setGetYearFlag(tLCDutySchema1.getGetYearFlag());
		}
		if ((tLCPolBL.getPayEndYearFlag() == null)
				&& (tLCDutySchema1.getPayEndYearFlag() != null)) {
			tLCPolBL.setPayEndYearFlag(tLCDutySchema1.getPayEndYearFlag());
		}
		if ((tLCPolBL.getAcciYearFlag() == null)
				&& (tLCDutySchema1.getAcciYearFlag() != null)) {
			tLCPolBL.setAcciYearFlag(tLCDutySchema1.getAcciYearFlag());
		}

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(pLCPolSchema.getPolNo());
		LCGetSet tOldLCGetSet = tLCGetDB.query();
		LCGetSchema tOldLCGetSchema = null;
		for (int i = 1; i <= tLCGetBLSet.size(); i++) {
			LPGetSchema tLPGetSchema = new LPGetSchema();
			mReflections.transFields(tLPGetSchema, tLCGetBLSet.get(i)
					.getSchema());
			if (tOldLCGetSet != null && tOldLCGetSet.size() > 0) {
				tOldLCGetSchema = tOldLCGetSet.get(i);
				tLPGetSchema.setGettoDate(tOldLCGetSchema.getGettoDate());
				tLPGetSchema.setSumMoney(tOldLCGetSchema.getSumMoney());
			}
			tLPGetSchema.setEdorNo(pLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(pLPEdorItemSchema.getEdorType());
			tLPGetSchema.setModifyDate(strCurrentDate);
			tLPGetSchema.setModifyTime(strCurrentTime);
			mLPGetSet.add(tLPGetSchema);
		}

		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(pLCPolSchema.getPolNo());
		LCPremSet tOldLCPremSet = tLCPremDB.query();
		LCPremSchema tOldLCPremSchema = null;
		for (int i = 1; i <= tLCPremBLSet.size(); i++) {
			LPPremSchema tLPPremSchema = new LPPremSchema();
			mReflections.transFields(tLPPremSchema, tLCPremBLSet.get(i)
					.getSchema());
			if (tOldLCPremSet != null && tOldLCPremSet.size() > 0) {
				tOldLCPremSchema = tOldLCPremSet.get(i);
				tLPPremSchema.setPaytoDate(tOldLCPremSchema.getPaytoDate());
				tLPPremSchema.setSumPrem(tOldLCPremSchema.getSumPrem());
			}
			tLPPremSchema.setEdorNo(pLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(pLPEdorItemSchema.getEdorType());
			tLPPremSchema.setModifyDate(strCurrentDate);
			tLPPremSchema.setModifyTime(strCurrentTime);
			if (tLPPremSchema.getPayPlanType().equals("0")) {
				mLPPremSet.add(tLPPremSchema); // 不包含加费
			}
		}
		return true;
	}

	/**
	 * 根据险种定义描述确定转换后的责任 （目前只有280会有转责任的情况）
	 * 
	 * @param sMainRiskCode
	 *            缴清后新主险的险种编码
	 * @param sDutyCode
	 *            原责任编码
	 * @return String 转换后的新责任编码
	 */
	private String getNewDutyCode(String sNewMainRiskCode, String sRiskCode,
			String sDutyCode) {
		// 查询补费止期计算代码
		String sql = " select * from lmedorcal " + " where riskcode = '?sRiskCode?' " + " and caltype = 'RPUDuty' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sRiskCode", sRiskCode);
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv);
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "责任转换计算代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			return sDutyCode; // 不需要转责任，直接返回原责任编码
		}
		String sDutyCalCode = tLMEdorCalSet.get(1).getCalCode();

		// 计算转换后的责任编码
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sDutyCalCode);
		tCalculator.addBasicFactor("RiskCode", sNewMainRiskCode); // 险种代码
		String sNewDutyCode = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "责任转换计算失败!");
			return null;
		}

		if (sNewDutyCode == null || sNewDutyCode.trim().equals("")) {
			CError.buildErr(this, "责任转换计算失败!");
			return null;
		}

		return sNewDutyCode;
	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase mBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}
	// 四舍六入五靠偶数，保留两位
	private double getRound(double tValue) {
		String t = "0.00";
		DecimalFormat tDF = new DecimalFormat(t);
		return Double.parseDouble(tDF.format(tValue));
	}

	private void jbInit() throws Exception {
	}
}
