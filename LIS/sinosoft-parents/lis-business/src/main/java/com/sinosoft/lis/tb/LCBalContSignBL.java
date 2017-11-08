package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.text.DecimalFormat;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LXbalanceDB;
import com.sinosoft.lis.db.LXbalanceSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LXbalanceSchema;
import com.sinosoft.lis.schema.LXbalanceSubSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LXbalanceSet;
import com.sinosoft.lis.vschema.LXbalanceSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class LCBalContSignBL {
private static Logger logger = Logger.getLogger(LCBalContSignBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	public CErrors msErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private String mOperator;
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private DecimalFormat mDecimalFormat = new DecimalFormat("0.000"); // 数字转换对象

	private LXbalanceSchema mLXbalanceSchema = new LXbalanceSchema();
	private LXbalanceSubSchema mLXbalanceSubSchema = new LXbalanceSubSchema();
	private LXbalanceSubSet mLXbalanceSubSet = new LXbalanceSubSet();
	private LCContSet mLCContSet = new LCContSet();

	private int prepSignCount = 0;// 准备签单保单数量
	private int succSignCount = 0;// 成功签单保单数量

	TransferData mTransferData;

	double mSumPrem = 0.00;// 准备签单保单的保费
	double mSumPay = 0.00;// 结算号保单的暂交费
	double mSuccPrem = 0.00;// 已签单保单的保费

	public LCBalContSignBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mInputData = (VData) cInputData.clone();

		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start PubSubmit Submit...");
		PubSubmit tPubSubmit = new PubSubmit();
		// 如果有需要处理的错误，则返回
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		logger.debug("End PubSubmit Submit...");

		return true;
	}

	/**
	 * 获取输入数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		this.mGlobalInput.setSchema((GlobalInput) mInputData
				.getObjectByObjectName("GlobalInput", 0));
		if (mGlobalInput == null) {
			this.buildError("getInputData", "没有传入全局信息！");
			return false;
		}
		this.mOperator = mGlobalInput.Operator;

		this.mLXbalanceSchema.setSchema((LXbalanceSchema) mInputData
				.getObjectByObjectName("LXbalanceSchema", 0));
		if (mLXbalanceSchema == null) {
			this.buildError("getInputData", "没有传入结算信息！");
			return false;
		}

		LXbalanceDB tLXblanceDB = new LXbalanceDB();
		LXbalanceSet tLXbalanceSet = new LXbalanceSet();
		LXbalanceSubDB tLXblanceSubDB = new LXbalanceSubDB();
		String tStrSQL = "";

		tStrSQL = "select * from lxbalance where balanceno='"
				+ "?balanceno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tStrSQL);
		sqlbv.put("balanceno", this.mLXbalanceSchema.getBalanceNo());
		tLXbalanceSet = tLXblanceDB.executeQuery(sqlbv);
		if (tLXbalanceSet == null || tLXbalanceSet.size() <= 0) {
			this.buildError("checkData", "结算信息查询失败！");
			return false;
		}
		mLXbalanceSchema = tLXbalanceSet.get(1);
		if ("1".equals(mLXbalanceSchema.getConflag())) {
			this.buildError("checkData", "此结算号对应保单已经签单！");
			return false;
		}

		tStrSQL = "select * from lxbalancesub where balanceno='"
				+ "?balanceno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStrSQL);
		sqlbv1.put("balanceno", this.mLXbalanceSchema.getBalanceNo());
		mLXbalanceSubSet = tLXblanceSubDB.executeQuery(sqlbv1);
		if (mLXbalanceSubSet == null || mLXbalanceSubSet.size() <= 0) {
			this.buildError("checkData", "结算信息子表LXblanceSub查询失败！");
			return false;
		}

		if (!prepareLCContSet()) {
			return false;
		}

		return true;
	}

	/**
	 * 校验数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (!checkTempFee()) {
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		int tCount = mLCContSet.size();

		for (int i = 1; i <= tCount; i++) {
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = mLCContSet.get(i);
			if (tLCContSchema == null) {
				continue;
			}
			dealOneCont(tLCContSchema);
		}

		// if (msErrors.getErrorCount() > 0) {
		// return false;
		// }
		try { // 获取已经签单成功保单的保费
			getSuccPrem();
			// 已经签单成功的报单的保费总额及签单成功的保单数都为零，则返回错误
			logger.debug("succSignCount== " + succSignCount
					+ ";  -------mSuccPrem=== " + mSuccPrem);
			if (succSignCount == 0 && mSuccPrem == 0) {
				// mErrors = msErrors;
				return false;
			}
		} catch (Exception ex) {
			return false;
		}

		// 判断是否有保单已经签单成功，如果有则核销该条结算记录
		mLXbalanceSchema.setConfDate(PubFun.getCurrentDate());
		mLXbalanceSchema.setConflag("1");
		mLXbalanceSchema.setModifyDate(PubFun.getCurrentDate());
		mLXbalanceSchema.setModifyTime(PubFun.getCurrentTime());
		mLXbalanceSchema.setOperator(mOperator);
		//

		return true;
	}

	/**
	 * 准备后台处理数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		MMap tMap = new MMap();
		tMap.put(mLXbalanceSchema, "UPDATE");
		// 核销财务信息
		if (mLXbalanceSchema.getConflag() != null
				&& mLXbalanceSchema.getConflag().equals("1")) {
			// 生成溢交退费
			MMap sMap = new MMap();
			sMap = getLJAGet(mLXbalanceSchema, mSuccPrem, mSumPay);
			if (sMap == null) {
				return false;
			}
			tMap.add(sMap);
			// 核销暂缴费
			String tBalanceNo = mLXbalanceSchema.getBalanceNo();
			String tConfDate = mLXbalanceSchema.getConfDate();
			String upTmpfee = " update ljtempfee set confdate='"
					+ "?confdate?"
					+ "' ,confflag='1' where otherno='"
					+ "?otherno?"
					+ "' "
					+ " and confdate is null and (enteraccdate is not null and enteraccdate <>'3000-01-01') ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(upTmpfee);
			sqlbv2.put("confdate", tConfDate);
			sqlbv2.put("otherno", tBalanceNo);
			String upTmpfeeClass = " update ljtempfeeclass set confdate='"
					+ "?confdate?"
					+ "' ,confflag='1' where otherno='"
					+ "?otherno?"
					+ "' "
					+ " and confdate is null and (enteraccdate is not null and enteraccdate <>'3000-01-01') ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(upTmpfeeClass);
			sqlbv3.put("confdate", tConfDate);
			sqlbv3.put("otherno", tBalanceNo);
			tMap.put(sqlbv2, "UPDATE");
			tMap.put(sqlbv3, "UPDATE");
		}

		this.mInputData.clear();
		this.mInputData.add(tMap);

		return true;
	}

	// 生成溢交退费
	private MMap getLJAGet(LXbalanceSchema sLXbalanceSchema, double succPrem,
			double sumPay) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		try {
			mLXbalanceSubSchema = mLXbalanceSubSet.get(1);
			tLAAgentDB.setAgentCode(mLXbalanceSubSchema.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				buildError("getLJAGet", "获取业务员["
						+ mLXbalanceSubSchema.getAgentCode() + "]信息失败。");
				return null;
			}
			tLAAgentSchema = tLAAgentDB.getSchema();
		} catch (Exception ex) {
			buildError("getLJAGet", "获取业务员代码及组别信息失败。");
			return null;
		}

		MMap sMMap = new MMap();
		// 如果已签单保费总额小于暂交费总额，则产生溢交退费
		double sGetMoney = sumPay - succPrem;
		if (sGetMoney <= 0)
			return sMMap;

		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		String getNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生即付通知书号
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo(getNo);
		tLJAGetSchema.setSerialNo(serNo);
		tLJAGetSchema.setPayMode("1"); // 现金付费
		tLJAGetSchema.setBankAccNo("");
		tLJAGetSchema.setBankCode("");
		tLJAGetSchema.setAccName("");
		tLJAGetSchema.setOtherNo(sLXbalanceSchema.getBalanceNo());
		tLJAGetSchema.setOtherNoType("6"); // 其它类型退费(溢交保费退费)
		tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
		tLJAGetSchema.setAppntNo("");
		tLJAGetSchema.setSumGetMoney(sGetMoney);
		tLJAGetSchema.setSaleChnl("");
		tLJAGetSchema.setOperator(mGlobalInput.Operator);
		tLJAGetSchema.setManageCom(sLXbalanceSchema.getManageCom());
		tLJAGetSchema.setAgentCode(tLAAgentSchema.getAgentCode());
		tLJAGetSchema.setAgentGroup(tLAAgentSchema.getAgentGroup());
		tLJAGetSchema.setAgentCom(sLXbalanceSchema.getAgentCom());
		tLJAGetSchema.setShouldDate(sLXbalanceSchema.getModifyDate());
		tLJAGetSchema.setStartGetDate(sLXbalanceSchema.getModifyDate());
		tLJAGetSchema.setMakeDate(sLXbalanceSchema.getMakeDate());
		tLJAGetSchema.setMakeTime(sLXbalanceSchema.getMakeTime());
		tLJAGetSchema.setModifyDate(sLXbalanceSchema.getModifyDate());
		tLJAGetSchema.setModifyTime(sLXbalanceSchema.getModifyTime());

		LJAGetOtherSchema tLJAGetOtherShema = new LJAGetOtherSchema();
		tLJAGetOtherShema.setActuGetNo(tLJAGetSchema.getActuGetNo());
		tLJAGetOtherShema.setSerialNo(tLJAGetSchema.getSerialNo());
		tLJAGetOtherShema.setOtherNo(tLJAGetSchema.getOtherNo());
		tLJAGetOtherShema.setOtherNoType("0"); // 其它类型退费(溢交保费退费)
		tLJAGetOtherShema.setPayMode(tLJAGetSchema.getPayMode());
		tLJAGetOtherShema.setGetMoney(tLJAGetSchema.getSumGetMoney());
		tLJAGetOtherShema.setGetDate(sLXbalanceSchema.getModifyDate());
		tLJAGetOtherShema.setFeeOperationType("YJ"); // 溢交退费
		tLJAGetOtherShema.setFeeFinaType("TF"); // 溢交退费
		tLJAGetOtherShema.setManageCom(tLJAGetSchema.getManageCom());
		tLJAGetOtherShema.setAgentCom(tLJAGetSchema.getAgentCom());
		tLJAGetOtherShema.setAgentType(tLJAGetSchema.getAgentType());
		tLJAGetOtherShema.setAPPntName("");
		tLJAGetOtherShema.setAgentGroup(tLJAGetSchema.getAgentGroup());
		tLJAGetOtherShema.setAgentCode(tLJAGetSchema.getAgentCode());
		tLJAGetOtherShema.setOperator(mGlobalInput.Operator);
		tLJAGetOtherShema.setMakeTime(tLJAGetSchema.getMakeTime());
		tLJAGetOtherShema.setMakeDate(tLJAGetSchema.getMakeDate());
		tLJAGetOtherShema.setModifyDate(tLJAGetSchema.getModifyDate());
		tLJAGetOtherShema.setModifyTime(tLJAGetSchema.getModifyTime());

		// MMap sMMap = new MMap();
		sMMap.put(tLJAGetSchema, "INSERT");
		sMMap.put(tLJAGetOtherShema, "INSERT");
		return sMMap;
	}

	//
	private boolean checkTempFee() {
		String tStrSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tPay = "";
		double tSumPay = 0.00;

		tStrSQL = "select (case when sum(paymoney) is null then 0 else sum(paymoney) end) from ljtempfee where otherno='"
				+ "?otherno?"
				+ "' "
				+ " and confdate is null and (enteraccdate is not null and enteraccdate <>'3000-01-01')";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tStrSQL);
		sqlbv4.put("otherno", mLXbalanceSchema.getBalanceNo());
		tPay = tExeSQL.getOneValue(sqlbv4);
		tSumPay = Double.parseDouble(tPay);

		tSumPay = Double.parseDouble(mDecimalFormat.format(tSumPay));
		mSumPrem = Double.parseDouble(mDecimalFormat.format(mSumPrem));
		logger.debug("&&&&&&&&&&&&&&交费金额=====" + tSumPay);
		logger.debug("&&&&&&&&&&&&&&保费金额=====" + mSumPrem);
		if (tSumPay <= 0) {
			buildError("checkTempFee", "交费金额金额为零，请先交费！");
			return false;
		}

		if (tSumPay < mSumPrem) {
			buildError("checkTempFee", "此结算号暂交费不足！");
			return false;
		}

		mSumPay = tSumPay;
		// if (tSumPay > mSumPrem) {
		// buildError("checkTempFee", "此结算号暂交费超出！");
		// return false;
		// }
		return true;
	}

	/**
	 * 根据号段生成保单数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean prepareLCContSet() {
		int tCount = mLXbalanceSubSet.size();
		String tStartNo, tEndNo;
		ExeSQL tExeSQL = new ExeSQL();

		// 循环结算子表，取得每一个子表的号段
		for (int i = 1; i <= tCount; i++) {
			mLXbalanceSubSchema = mLXbalanceSubSet.get(i);
			tStartNo = mLXbalanceSubSchema.getStartNo();
			tEndNo = mLXbalanceSubSchema.getEndNo();
			long tNo = bigIntegerDiff(tEndNo, tStartNo);

			// 循环得到的号段，对每个保单号进行签单处理
			for (long j = 0; j <= tNo; j++) {
				String tContNo = this.bigIntegerPlus(tStartNo, String
						.valueOf(j), tStartNo.length());
				LCContSchema tLCContSchema = this.queryLCCont(tContNo);
				if (tLCContSchema == null) {
					buildError("prepareLCContSet", "对应号段的" + tContNo
							+ "没有相应的保单记录！");
					// return false;
					continue;
				}

				String tStrSQL = "";
				String tPrem = "";
				tStrSQL = "select (case when sum(prem) is null then 0 else sum(prem) end) from lcpol where contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tLCContSchema.getContNo());
				sqlbv5.put("contno", tLCContSchema.getContNo());
				tPrem = tExeSQL.getOneValue(sqlbv5);
				mSumPrem = mSumPrem + Double.parseDouble(tPrem);

				if ("0".equals(tLCContSchema.getAppFlag())) {
					mLCContSet.add(tLCContSchema);
				}
				//
			}
		}

		if (mLCContSet.size() <= 0) {
			buildError("prepareLCContSet", "没有要签单的保单数据！");
			return false;
		}

		return true;
	}

	/**
	 * 判断是否可以核销结算号记录,如果已经签单成功的保单保费总额>0,那么则可以核销记录
	 */
	private void getSuccPrem() {
		int tCount = mLXbalanceSubSet.size();
		String tStartNo, tEndNo;
		ExeSQL tExeSQL = new ExeSQL();
		String sContPrem = "";
		mSuccPrem = 0.00;
		succSignCount = 0;
		// 循环结算子表，取得每一个子表的号段
		for (int i = 1; i <= tCount; i++) {
			mLXbalanceSubSchema = mLXbalanceSubSet.get(i);
			tStartNo = mLXbalanceSubSchema.getStartNo();
			tEndNo = mLXbalanceSubSchema.getEndNo();
			long tNo = bigIntegerDiff(tEndNo, tStartNo);

			// 循环得到的号段，判断每个保单号是否已经进行签单处理成功
			for (long j = 0; j <= tNo; j++) {
				String tContNo = this.bigIntegerPlus(tStartNo, String
						.valueOf(j), tStartNo.length());
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql("select (case when sum(prem) is null then 0 else sum(prem) end) from lcpol where prtno='"
						+ "?prtno?" + "' and appflag='1' ");
				sqlbv6.put("prtno", tContNo);
				sContPrem = tExeSQL
						.getOneValue(sqlbv6);
				if (Double.parseDouble(sContPrem) <= 0) {
					continue;
				}
				mSuccPrem = mSuccPrem + Double.parseDouble(sContPrem);
				succSignCount++;
			}
		}

	}

	/**
	 * 对保单进行签单
	 * 
	 * @param cLCContSchema
	 *            LCContSchema
	 * @return boolean
	 */
	private void dealOneCont(LCContSchema cLCContSchema) {
		VData tVData = new VData();
		LCContSet tLCContSet = new LCContSet();
		LCContSchema tLCContSchema = new LCContSchema();
		TransferData tTransferData = new TransferData();
		tLCContSchema = cLCContSchema.getSchema();
		tLCContSet.add(tLCContSchema);

		// 产品组合标志，默认为“N-非产品组合”
		ExeSQL exeSQL = new ExeSQL();
		String strSOL = " select contplancode from lcinsured where contno='"
				+ "?contno?" + "' "
				+ " and contplancode is not null ";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(strSOL);
		sqlbv7.put("contno", cLCContSchema.getContNo());
		String ISPlanRisk = exeSQL.getOneValue(sqlbv7);
		if (ISPlanRisk != null && !ISPlanRisk.equals("")) {
			tTransferData.setNameAndValue("ISPlanRisk", "Y");
		} else {
			tTransferData.setNameAndValue("ISPlanRisk", "N");
		}
		tTransferData.setNameAndValue("CurrentDate", PubFun.getCurrentDate());
		tTransferData.setNameAndValue("CurrentTime", PubFun.getCurrentTime());
		if ("3".equals(cLCContSchema.getCardFlag())) {
			tTransferData.setNameAndValue("mark", "card");
			tTransferData.setNameAndValue("CardContNo", tLCContSchema
					.getForceUWReason());
		}
		tTransferData.setNameAndValue("BalFlag", "Y"); // 结算标志
		tTransferData.setNameAndValue("BalanceNo", mLXbalanceSchema
				.getBalanceNo()); // 结算号

		tVData.add(this.mGlobalInput);
		tVData.add(tLCContSet);
		tVData.add(tTransferData);
		tVData.add(mLXbalanceSchema);
		LCContSignBL tLCContSignBL = new LCContSignBL();
		String flag = "sign";
		if (!tLCContSignBL.submitData(tVData, flag)) {
			msErrors.copyAllErrors(tLCContSignBL.mErrors);
		}
	}

	// 根据保单号，查询报单是否存在
	private LCContSchema queryLCCont(String cContNo) {
		String sql = "select * from lccont where prtno='" + "?prtno?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("?prtno?", cContNo);
		LCContSet tLCContSet = null;
		LCContDB tLCContDB = new LCContDB();
		tLCContSet = tLCContDB.executeQuery(sqlbv8);
		if (tLCContDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			return null;
		}
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			return null;
		}
		return tLCContSet.get(1);

	}

	//

	/**
	 * 返回两个大数字的差值
	 * 
	 * @param strMinuend
	 *            String
	 * @param strSubtrahend
	 *            String
	 * @return long
	 */
	private long bigIntegerDiff(String strMinuend, String strSubtrahend) {
		BigInteger bigIntMinuend = new BigInteger(strMinuend);
		BigInteger bigIntSubtrahend = new BigInteger(strSubtrahend);
		return bigIntMinuend.subtract(bigIntSubtrahend).longValue();
	}

	/**
	 * 返回两个大数相加的结果
	 * 
	 * @param strX
	 *            加数一
	 * @param strY
	 *            加数二
	 * @param nMinLen
	 *            返回结果的最小长度
	 * @return strX + strY
	 */
	private String bigIntegerPlus(String strX, String strY, int nMinLen) {
		BigInteger bigX = new BigInteger(strX);
		BigInteger bigY = new BigInteger(strY);

		String str = (bigX.add(bigY)).toString();
		String strTemp = "00000000000000000000";
		strTemp = strTemp.substring(1, (nMinLen - str.length()) + 1);

		return strTemp + str;
	}

	private boolean backTempFee() {
		return true;

	}

	/**
	 * 错误处理
	 * 
	 * @param cFunc
	 *            String 出错函数名称
	 * @param cErrMsg
	 *            String 出错信息
	 */
	private void buildError(String cFunc, String cErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LDPlanBL";
		cError.functionName = cFunc;
		cError.errorMessage = cErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		VData tVData = new VData();

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "8621";
		tG.Operator = "001";

		// 结算信息
		String tBalanceNo = "86210705180001"; // 卡号
		LXbalanceSchema tLXbalanceSchema = new LXbalanceSchema();
		tLXbalanceSchema.setBalanceNo(tBalanceNo);

		tVData.add(tG);
		tVData.add(tLXbalanceSchema);

		LCBalContSignBL tLCBalContSignBL = new LCBalContSignBL();
		String flag = "sign";
		if (tLCBalContSignBL.submitData(tVData, flag) == false) {

		}

	}

}
