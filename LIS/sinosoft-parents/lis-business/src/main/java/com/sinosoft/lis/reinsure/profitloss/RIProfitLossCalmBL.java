

package com.sinosoft.lis.reinsure.profitloss;

/**
 * <p>ClassName: RIProfitLossCalmBL.java </p>
 * <p>Description: 盈余佣金计算 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/8/22
 */

//包名
//package com.sinosoft.lis.config;

import com.sinosoft.lis.db.RIProLossCalDB;
import com.sinosoft.lis.db.RIProLossRelaDB;
import com.sinosoft.lis.db.RIProLossResultDB;
import com.sinosoft.lis.db.RIProLossValueDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIProLossCalSchema;
import com.sinosoft.lis.schema.RIProLossRelaSchema;
import com.sinosoft.lis.schema.RIProLossResultSchema;
import com.sinosoft.lis.schema.RIProLossValueSchema;
import com.sinosoft.lis.vschema.RIProLossRelaSet;
import com.sinosoft.lis.vschema.RIProLossResultSet;
import com.sinosoft.lis.vschema.RIProLossValueSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIProfitLossCalmBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	private String mCalType = "";
	private String tCalSql = "";
	private double rate = 0;
	private String mRIProfitNo = "";
	private String mReComCode = "";
	private String mCurrency = "";
	private String mCalSql = "";
	private String batno = "";
	private String tcalyear = "";
	private String tcurrency = "";
	private String currentDate = PubFun.getCurrentDate();
	private String currentTime = PubFun.getCurrentTime();
	/** 业务处理相关变量 */
	private RIProLossRelaSchema mRIProLossRelaSchema = new RIProLossRelaSchema();
	private RIProLossRelaSet mRIProLossRelaSet = new RIProLossRelaSet();
	private RIProLossRelaSchema tRIProLossRelaSchema = new RIProLossRelaSchema();
	private RIProLossCalSchema tRIProLossCalSchema = new RIProLossCalSchema();
	private RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();
	private RIProLossResultSet tRIProLossResultSet = new RIProLossResultSet();

	private RIProLossValueSet tRIProLossValueSet = new RIProLossValueSet();
	private MMap map = new MMap();

	public RIProfitLossCalmBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = cInputData;
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		System.out.println("Start RIProfitLossCalmBL Submit...");

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, null)) {

			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存信息时出现错误!");
				return false;
			}
		}

		mInputData = null;
		System.out.println("End RIProfitLossCalmBL Submit...");
		return true;

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIProfitLossCalmBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		if (mOperate.equals("ININTPARAM")) {
			this.mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 2));
			this.mRIProLossRelaSchema
					.setSchema((RIProLossRelaSchema) mInputData
							.getObjectByObjectName("RIProLossRelaSchema", 0));
			TransferData transferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			this.tcalyear = (String) transferData.getValueByName("calyear");
		}
		if (mOperate.equals("CALCULATE")) {
			this.tRIProLossRelaSchema
					.setSchema((RIProLossRelaSchema) mInputData
							.getObjectByObjectName("RIProLossRelaSchema", 0));
			this.tRIProLossValueSet.set((RIProLossValueSet) mInputData
					.getObjectByObjectName("RIProLossValueSet", 1));
			this.tRIProLossCalSchema.setSchema((RIProLossCalSchema) mInputData
					.getObjectByObjectName("RIProLossCalSchema", 2));
			this.tRIProLossResultSchema
					.setSchema((RIProLossResultSchema) mInputData
							.getObjectByObjectName("RIProLossResultSchema", 3));
			this.mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 4));
			RIProLossCalDB cdb = new RIProLossCalDB();
			cdb.setRIProfitNo(tRIProLossCalSchema.getRIProfitNo());
			cdb.getInfo();
			tRIProLossCalSchema.setSchema(cdb);
		}
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if ("ININTPARAM".equals(mOperate)) {
			System.out.println("BL+++++++++++++++"
					+ mRIProLossRelaSchema.getRIProfitNo());
			RIProLossValueDB mRIProLossValueDB = new RIProLossValueDB();
			RIProLossValueSet mRIProLossValueSet = new RIProLossValueSet();
			mRIProLossValueSet = mRIProLossValueDB
					.executeQuery("select * from RIProLossValue where RIProfitNo='"
							+ mRIProLossRelaSchema.getRIProfitNo()
							+ "' and calyear='"
							+ tcalyear
							+ "' and  currency='"
							+ mRIProLossRelaSchema.getCurrency() + "'");
			System.out.println(mRIProLossValueSet.size());
			if (mRIProLossValueSet.size() == 0) {
				param();
			} else {
				for (int i = 1; i <= mRIProLossValueSet.size(); i++) {
					RIProLossValueSchema mRIProLossValueSchema = new RIProLossValueSchema();
					mRIProLossValueSchema.setBatchNo(mRIProLossValueSet.get(i)
							.getBatchNo());
					mRIProLossValueSchema.setRIProfitNo(mRIProLossRelaSchema
							.getRIProfitNo());
					mRIProLossValueSchema.setCurrency(mRIProLossValueSet.get(i)
							.getCurrency());

					mRIProLossValueSchema.setFactorCode(mRIProLossValueSet.get(
							i).getFactorCode());
				}
				map.put(mRIProLossValueSet, "DELETE");
				RIProLossResultDB mRIProLossResultDB = new RIProLossResultDB();
				RIProLossResultSet mRIProLossResultSet = new RIProLossResultSet();
				mRIProLossResultSet = mRIProLossResultDB
						.executeQuery("select * from RIProLossResult where RIProfitNo='"
								+ mRIProLossRelaSchema.getRIProfitNo()
								+ "'and calyear='"
								+ tcalyear
								+ "' and  currency='"
								+ mRIProLossRelaSchema.getCurrency() + "'");
				System.out.println(mRIProLossResultSet.size());

				for (int i = 1; i <= mRIProLossResultSet.size(); i++) {
					RIProLossResultSchema mRIProLossResultSchema = new RIProLossResultSchema();
					mRIProLossResultSchema.setBatchNo(mRIProLossResultSet
							.get(i).getBatchNo());
					mRIProLossResultSchema.setRIProfitNo(mRIProLossResultSet
							.get(i).getRIProfitNo());
					mRIProLossResultSchema.setCurrency(mRIProLossResultSet.get(
							i).getCurrency());
				}
				map.put(mRIProLossResultSet, "DELETE");
				param();
			}
		} else if ("CALCULATE".equals(mOperate)) {
			if (tRIProLossValueSet.get(1).getBatchNo().equals("")
					|| tRIProLossValueSet.get(1).getBatchNo() == null) {
				batno = tRIProLossResultSchema.getCalYear()
						+ PubFun1.CreateMaxNo("RIPROLOSSVALUE", 6);
			} else {
				batno = tRIProLossValueSet.get(1).getBatchNo();
			}

			for (int i = 1; i <= tRIProLossValueSet.size(); i++) {
				RIProLossValueSchema tRIProLossValueSchema = tRIProLossValueSet
						.get(i);

				tRIProLossValueSchema.setBatchNo(batno);

				tRIProLossValueSchema.setRIProfitNo(tRIProLossValueSet.get(i)
						.getRIProfitNo());
				tRIProLossValueSchema.setCalYear(tRIProLossValueSet.get(i)
						.getCalYear());
				tRIProLossValueSchema.setCurrency(tRIProLossValueSet.get(i)
						.getCurrency());
				tRIProLossValueSchema.setReComCode(tRIProLossValueSet.get(i)
						.getReComCode());
				tRIProLossValueSchema.setRIContNo(tRIProLossValueSet.get(i)
						.getRIContNo());
				tRIProLossValueSchema.setFactorCode(tRIProLossValueSet.get(i)
						.getFactorCode());
				tRIProLossValueSchema.setFactorName(tRIProLossValueSet.get(i)
						.getFactorName());
				tRIProLossValueSchema.setFactorValue(tRIProLossValueSet.get(i)
						.getFactorValue());
				tRIProLossValueSchema.setMakeDate(currentDate);
				tRIProLossValueSchema.setMakeTime(currentTime);
				tRIProLossValueSchema.setModifyDate(currentDate);
				tRIProLossValueSchema.setModifyTime(currentTime);
				tRIProLossValueSchema.setManageCom(mGlobalInput.ManageCom);
				tRIProLossValueSchema.setOperator(mGlobalInput.Operator);
				System.out.println("====="
						+ tRIProLossValueSchema.getFactorValue());
				// tRIProLossValueSet.add(tRIProLossValueSchema);
			}
			map.put(tRIProLossValueSet, "DELETE&INSERT");
			System.out.println(tRIProLossValueSet.size());
			System.out.println(tRIProLossCalSchema.getRIProfitNo());
			System.out.println("========开始计算==========");
			if (tRIProLossCalSchema.getItemCalType().equals("1")) {
			} else if (tRIProLossCalSchema.getItemCalType().equals("2")) {
				mCalSql = tRIProLossCalSchema.getCalSQL();
				PubCalculator tPubCalculator = new PubCalculator();
				tPubCalculator.setCalSql(mCalSql);
				System.out.println(" calSql: " + mCalSql);
				for (int i = 1; i <= tRIProLossValueSet.size(); i++) {
					String clumncode = tRIProLossValueSet.get(i)
							.getFactorCode();

					String clumnvalue = tRIProLossValueSet.get(i)
							.getFactorValue();
					if (clumncode.equals("ProRate")) {
						rate = Double.parseDouble(clumnvalue);
					}
					tPubCalculator.addBasicFactor(clumncode, clumnvalue);
				}
				String tCalSql = tPubCalculator.calculateEx();
				System.out.println("纯溢手续费计算sql为：" + tCalSql);
				ExeSQL tExeSQL = new ExeSQL();
				String temp = tExeSQL.getOneValue(tCalSql);
				if (tExeSQL.mErrors.needDealError()) {
					buildError("verifyOperate", "纯溢手续费sql计算出错：");
					return false;
				} else if (temp.equals("")) {
					buildError("verifyOperate", "纯溢手续费sql计算出错：");
					return false;
				} else {
					tRIProLossResultSchema.setProfitAmnt(Double
							.parseDouble(temp));
				}
				System.out.println(tRIProLossResultSchema.getProfitAmnt());
			} else if (tRIProLossCalSchema.getItemCalType().equals("3")) {

			}
			tRIProLossResultSchema.setBatchNo(tRIProLossValueSet.get(1)
					.getBatchNo());
			tRIProLossResultSchema.setRIProfitNo(tRIProLossRelaSchema
					.getRIProfitNo());
			tRIProLossResultSchema.setState("02");
			// 盈余小于0，则没有盈余佣金
			if (tRIProLossResultSchema.getProfitAmnt() < 0) {
				tRIProLossResultSchema.setProLosAmnt(0);
			} else {
				tRIProLossResultSchema
						.setProLosAmnt((rate * (tRIProLossResultSchema
								.getProfitAmnt())));
			}

			tRIProLossResultSchema.setCurrency(tRIProLossValueSet.get(1)
					.getCurrency());
			tRIProLossResultSchema.setCalYear(tRIProLossValueSet.get(1)
					.getCalYear());
			tRIProLossResultSchema.setReComCode(tRIProLossValueSet.get(1)
					.getReComCode());
			tRIProLossResultSchema.setRIContNo(tRIProLossValueSet.get(1)
					.getRIContNo());
			tRIProLossResultSchema.setMakeDate(currentDate);
			tRIProLossResultSchema.setMakeTime(currentTime);
			tRIProLossResultSchema.setModifyDate(currentDate);
			tRIProLossResultSchema.setModifyTime(currentTime);
			tRIProLossResultSchema.setManageCom(mGlobalInput.ManageCom);
			tRIProLossResultSchema.setOperator(mGlobalInput.Operator);
			tRIProLossResultSet.add(tRIProLossResultSchema);

			map.put(tRIProLossResultSet, "DELETE&INSERT");
		}
		return true;
	}

	private void param() {
		RIProLossRelaDB db = new RIProLossRelaDB();
		mRIProLossRelaSet = db
				.executeQuery("select * from RIProLossRela where RIProfitNo='"
						+ mRIProLossRelaSchema.getRIProfitNo() + "'");
		mCurrency = mRIProLossRelaSchema.getCurrency();
		mRIProfitNo = mRIProLossRelaSchema.getRIProfitNo();
		mReComCode = mRIProLossRelaSchema.getReComCode();
		String[][] resultArr = new String[mRIProLossRelaSet.size()][];
		System.out.println("array长度" + resultArr.length);
		for (int i = 1; i <= mRIProLossRelaSet.size(); i++) {
			RIProLossRelaSchema tRIProLossRelaSchema = new RIProLossRelaSchema();
			mCalType = mRIProLossRelaSet.get(i).getItemCalType();
			tCalSql = mRIProLossRelaSet.get(i).getCalSQL();

			if (mCalType.equals("1")) {
			} else if (mCalType.equals("2")) {
				tRIProLossRelaSchema.setCurrency(mCurrency);
				tRIProLossRelaSchema.setInOutType(mRIProLossRelaSet.get(i)
						.getInOutType());
				tRIProLossRelaSchema.setInputType(mRIProLossRelaSet.get(i)
						.getInputType());
				tRIProLossRelaSchema.setFactorCode(mRIProLossRelaSet.get(i)
						.getFactorCode());
				tRIProLossRelaSchema.setFactorName(mRIProLossRelaSet.get(i)
						.getFactorName());
				PubCalculator tPubCalculator = new PubCalculator();
				tPubCalculator.setCalSql(tCalSql);
				tPubCalculator.addBasicFactor("CalYear", tcalyear);
				tPubCalculator.addBasicFactor("Currency", mCurrency);
				tPubCalculator.addBasicFactor("RIcomCode", mReComCode);
				tPubCalculator.addBasicFactor("RIProfitNo", mRIProfitNo);
				tCalSql = tPubCalculator.calculateEx();
				System.out.println(" calSql: " + tCalSql);
				ExeSQL tExeSQL = new ExeSQL();
				String temp = tExeSQL.getOneValue(tCalSql);
				double result = Double.parseDouble(temp);
				System.out.println("计算结果  " + i + " ： " + result);
				if (tExeSQL.mErrors.needDealError()) {
					buildError("verifyOperate", "初始化sql计算出错：");
					return;
				} else {
					// decode(a.InOutType,'01','收入','02','支出')
					resultArr[i - 1] = new String[10];
					resultArr[i - 1][0] = "01".equals(mRIProLossRelaSchema
							.getInOutType()) ? "收入" : "支出";
					resultArr[i - 1][1] = tRIProLossRelaSchema.getFactorCode();
					resultArr[i - 1][2] = tRIProLossRelaSchema.getFactorName();
					resultArr[i - 1][3] = "01".equals(tRIProLossRelaSchema
							.getInputType()) ? "系统计算" : "手工录入";
					resultArr[i - 1][4] = result + "";
					resultArr[i - 1][5] = mCurrency;
					resultArr[i - 1][6] = tRIProLossRelaSchema.getInputType();
					resultArr[i - 1][7] = result + "";
				}
			} else if (mCalType.equals("3")) {
			}

		}
		this.mResult.add(resultArr);
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProfitLossCalBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
	}
}

