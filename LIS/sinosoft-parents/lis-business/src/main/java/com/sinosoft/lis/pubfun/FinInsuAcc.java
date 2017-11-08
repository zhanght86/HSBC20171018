package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCustomerAccDB;
import com.sinosoft.lis.db.LCCustomerAccTraceDB;
import com.sinosoft.lis.schema.LCCustomerAccSchema;
import com.sinosoft.lis.schema.LCCustomerAccTraceSchema;
import com.sinosoft.lis.vschema.LCCustomerAccSet;
import com.sinosoft.lis.vschema.LCCustomerAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 财务客户账户
 * </p>
 * <p>
 * Description:客户账户处理公共类,可由理赔保全调用
 * </p>
 * <p>
 * Copyright: Copyright ChinaSoft(c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */
public class FinInsuAcc {
private static Logger logger = Logger.getLogger(FinInsuAcc.class);
	/** 全局变量 */
	private GlobalInput tGI = new GlobalInput();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;
	private LCCustomerAccSet mLCCustomerAccSet = new LCCustomerAccSet();
	private LCCustomerAccTraceSet mLCCustomerAccTraceSet = new LCCustomerAccTraceSet();

	private String mCustomerNo = "";
	private String mCustomerType = "";
	private String[] mOperationType;
	private String[] mMoneyType;
	private String mOtherNo = "";
	private String mOtherNoType = "";
	private double[] mMoney;
	private String tLimit = "";
	private String mInsuAccNo = "";
	int mAccNo = 0;

	public FinInsuAcc() {
	}

	/**
	 * 生成客户账户
	 * 
	 * @return boolean
	 */
	public MMap createInsuAcc(GlobalInput tGlobalInput, String tCustomerNo,
			String tCustomerType, String[] tOperationType, String tOtherNo,
			String tOtherNoType, String[] tMoneyType, double[] tMoney) {
		// 赋值
		mCustomerNo = tCustomerNo;
		mCustomerType = tCustomerType;
		mOperationType = tOperationType;
		mMoneyType = tMoneyType;
		mOtherNo = tOtherNo;
		mOtherNoType = tOtherNoType;
		mMoney = tMoney;
		tGI = tGlobalInput;

		MMap map = new MMap();

		if ((mOperationType.length != mMoneyType.length)
				&& (mOperationType.length != tMoney.length)) {
			buildError("createInsuAcc", "传入参数错误，业务类型与财务类型数目不一致！");
			return null;
		}

		LCCustomerAccTraceSchema tLCCustomerAccTraceSchema;
		LCCustomerAccSchema tLCCustomerAccSchema;

		// 准备客户账户总表数据
		tLCCustomerAccSchema = new LCCustomerAccSchema();
		tLCCustomerAccSchema = prepareLCCustomerAcc();

		for (int i = 0; i < mOperationType.length; i++) {

			if (tLCCustomerAccSchema == null) {
				return null;
			}

			// 准备客户账户轨迹表数据
			tLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();
			tLCCustomerAccTraceSchema = prepareLCCustomerAccTrace(
					tLCCustomerAccSchema, i);
			if (tLCCustomerAccTraceSchema == null) {
				return null;
			}

			mLCCustomerAccTraceSet.add(tLCCustomerAccTraceSchema);
		}

		if (mOperate.equals("INSERT")) {
			map.put(tLCCustomerAccSchema, "INSERT");
			map.put(mLCCustomerAccTraceSet, "INSERT");
		} else if (mOperate.equals("UPDATE")) {
			map.put(tLCCustomerAccSchema, "UPDATE");
			map.put(mLCCustomerAccTraceSet, "INSERT");
		}
		// VData tt = new VData();
		// tt.add(map);
		// PubSubmit t = new PubSubmit();
		// t.submitData(tt,"");
		return map;
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
	 * prepareLCCustomerAccTrace 准备客户账户总表数据
	 * 
	 * @return boolean
	 */
	private LCCustomerAccTraceSchema prepareLCCustomerAccTrace(
			LCCustomerAccSchema tLCCustomerAccSchema, int i) {
		String tAccHappenNo = "";
		String CurrentDate = PubFun.getCurrentDate();
		String CurrentTime = PubFun.getCurrentTime();

		LCCustomerAccTraceSchema tLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();

		// 生成财务发生号
		tAccHappenNo = PubFun1.CreateMaxNo("AccHappenNo", tLimit);

		if (mOperate.equals("INSERT")) {
			if (i == 0) {
				mAccNo = 1;
			} else {
				mAccNo = mAccNo + 1;
			}
		} else {
			LCCustomerAccTraceDB tLCCustomerAccTraceDB = new LCCustomerAccTraceDB();
			tLCCustomerAccTraceDB.setCustomerNo(mCustomerNo);
			tLCCustomerAccTraceDB.setCustomerType(mCustomerType);
			tLCCustomerAccTraceDB.setInsuAccNo(tLCCustomerAccSchema
					.getInsuAccNo());
			LCCustomerAccTraceSet tLCCustomerAccTraceSet = tLCCustomerAccTraceDB
					.query();
			if (tLCCustomerAccTraceSet == null
					|| tLCCustomerAccTraceSet.size() == 0) {
				buildError("prepareLCCustomerAccTrace", "查询客户账户履历表失败！");
				return null;
			}
			mAccNo = tLCCustomerAccTraceSet.size() + 1 + i;
		}
		tLCCustomerAccTraceSchema.setCustomerNo(mCustomerNo);
		tLCCustomerAccTraceSchema.setCustomerType(mCustomerType);
		tLCCustomerAccTraceSchema.setInsuAccNo(tLCCustomerAccSchema
				.getInsuAccNo());
		tLCCustomerAccTraceSchema.setAccNo(mAccNo);
		tLCCustomerAccTraceSchema.setAccType("");
		tLCCustomerAccTraceSchema.setAccHappenNo(tAccHappenNo);
		tLCCustomerAccTraceSchema.setOtherNoType(mOtherNoType);
		tLCCustomerAccTraceSchema.setOtherNo(mOtherNo);
		tLCCustomerAccTraceSchema.setOperationType(mOperationType[i]);
		tLCCustomerAccTraceSchema.setMoneyType(mMoneyType[i]);
		tLCCustomerAccTraceSchema.setMoney(mMoney[i]);
		tLCCustomerAccTraceSchema.setOperFlag("1");
		tLCCustomerAccTraceSchema.setOperator(tGI.Operator);
		tLCCustomerAccTraceSchema.setMakeDate(CurrentDate);
		tLCCustomerAccTraceSchema.setMakeTime(CurrentTime);
		tLCCustomerAccTraceSchema.setModifyDate(CurrentDate);
		tLCCustomerAccTraceSchema.setModifyTime(CurrentTime);

		return tLCCustomerAccTraceSchema;
	}

	/**
	 * prepareLCCustomerAcc 准备客户账户总表数据
	 * 
	 * @return boolean
	 */
	private LCCustomerAccSchema prepareLCCustomerAcc() {

		String CurrentDate = PubFun.getCurrentDate();
		String CurrentTime = PubFun.getCurrentTime();

		LCCustomerAccSchema tLCCustomerAccSchema = new LCCustomerAccSchema();
		LCCustomerAccDB tLCCustomerAccDB = new LCCustomerAccDB();
		tLCCustomerAccDB.setCustomerNo(mCustomerNo);
		tLCCustomerAccDB.setCustomerType(mCustomerType);
		LCCustomerAccSet tLCCustomerAccSet = tLCCustomerAccDB.query();

		if (tLCCustomerAccSet == null || tLCCustomerAccSet.size() == 0) {
			mOperate = "INSERT";

			tLimit = PubFun.getNoLimit(tGI.ManageCom);
			// 生成客户账户号码
			mInsuAccNo = PubFun1.CreateMaxNo("InsuAccNo", tLimit);

			tLCCustomerAccSchema.setCustomerNo(mCustomerNo);
			tLCCustomerAccSchema.setCustomerType(mCustomerType);
			tLCCustomerAccSchema.setInsuAccNo(mInsuAccNo);
			tLCCustomerAccSchema.setAccType("");
			tLCCustomerAccSchema.setAccComputeFlag("");
			tLCCustomerAccSchema.setBalaDate(CurrentDate);
			tLCCustomerAccSchema.setBalaTime(CurrentTime);
			tLCCustomerAccSchema.setSumPay("");
			tLCCustomerAccSchema.setSumPaym("");

			double tSumMoney = 0;
			for (int ii = 0; ii < mMoney.length; ii++) {
				tSumMoney = tSumMoney + mMoney[ii];
			}
			tLCCustomerAccSchema.setInsuAccBala(tSumMoney);
			tLCCustomerAccSchema.setInsuAccGetMoney(tSumMoney);
			tLCCustomerAccSchema.setState("");
			tLCCustomerAccSchema.setOperator(tGI.Operator);
			tLCCustomerAccSchema.setMakeDate(CurrentDate);
			tLCCustomerAccSchema.setMakeTime(CurrentTime);
			tLCCustomerAccSchema.setModifyDate(CurrentDate);
			tLCCustomerAccSchema.setModifyTime(CurrentTime);

		} else {
			mOperate = "UPDATE";
			tLCCustomerAccSchema = tLCCustomerAccSet.get(1).getSchema();

			double tOriginalBala = tLCCustomerAccSchema.getInsuAccBala();
			double tOriginalGet = tLCCustomerAccSchema.getInsuAccGetMoney();
			double tSumMoney = 0;
			for (int ii = 0; ii < mMoney.length; ii++) {
				tSumMoney = tSumMoney + mMoney[ii];
			}
			tLCCustomerAccSchema.setInsuAccBala(tOriginalBala + tSumMoney);
			tLCCustomerAccSchema.setInsuAccGetMoney(tOriginalGet + tSumMoney);
			tLCCustomerAccSchema.setBalaDate(CurrentDate);
			tLCCustomerAccSchema.setBalaTime(CurrentTime);
			tLCCustomerAccSchema.setModifyDate(CurrentDate);
			tLCCustomerAccSchema.setModifyTime(CurrentTime);
			tLCCustomerAccSchema.setOperator(tGI.Operator);
		}
		return tLCCustomerAccSchema;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86110000";

		String mCustomerNo = "123123123";
		String mCustomerType = "1";
		String[] mOperationType = new String[2];
		String[] mMoneyType = new String[2];
		double[] mMoney = new double[2];
		mMoneyType[0] = "ff";
		mMoneyType[1] = "ff2";
		mOperationType[0] = "css";
		mOperationType[1] = "dsfs";
		String mOtherNo = "12312312445677";
		String mOtherNoType = "66";
		mMoney[0] = 100;
		mMoney[1] = 1100;

		FinInsuAcc tFinInsuAcc = new FinInsuAcc();
		tFinInsuAcc.createInsuAcc(tGI, mCustomerNo, mCustomerType,
				mOperationType, mOtherNo, mOtherNoType, mMoneyType, mMoney);

	}
}
