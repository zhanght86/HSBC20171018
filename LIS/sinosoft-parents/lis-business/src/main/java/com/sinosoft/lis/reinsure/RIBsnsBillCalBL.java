

/**
 * <p>ClassName: RIBsnsBillCalBL.java </p>
 * <p>Description: 账单计算 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011-08-18
 */

//包名
//package com.sinosoft.lis.config;
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIBsnsBillDefDB;
import com.sinosoft.lis.db.RIBsnsBillRelaDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIBsnsBillBatchSchema;
import com.sinosoft.lis.schema.RIBsnsBillDefSchema;
import com.sinosoft.lis.schema.RIBsnsBillDetailsSchema;
import com.sinosoft.lis.schema.RIBsnsBillRelaSchema;
import com.sinosoft.lis.vschema.RIBsnsBillDefSet;
import com.sinosoft.lis.vschema.RIBsnsBillDetailsSet;
import com.sinosoft.lis.vschema.RIBsnsBillRelaSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIBsnsBillCalBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private String mBillType;
	private String mStartDate;
	private String mEndDate;
	private String mRIComCode;
	private String mBillTimes;
	private String mBillBatchNo;

	private RIBsnsBillBatchSchema mRIBsnsBillBatchSchema;
	private RIBsnsBillDetailsSet mRIBsnsBillDetailsSet;

	private MMap map;
	private ExeSQL tExeSQL = new ExeSQL();

	public RIBsnsBillCalBL() {
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

		mInputData = null;
		System.out.println("End RIBsnsBillCalBL Submit...");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		this.mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		mBillType = (String) tTransferData.getValueByName("BillType");
		mStartDate = (String) tTransferData.getValueByName("StartDate");
		mEndDate = (String) tTransferData.getValueByName("EndDate");
		mRIComCode = (String) tTransferData.getValueByName("RIComCode");
		mBillTimes = (String) tTransferData.getValueByName("BillTimes");
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		RIBsnsBillDefDB tRIBsnsBillDefDB = new RIBsnsBillDefDB();
		RIBsnsBillDefSet tRIBsnsBillDefSet = tRIBsnsBillDefDB
				.executeQuery("SELECT * FROM ribsnsbilldef a WHERE a.incomecompanyno='"
						+ mRIComCode
						+ "' and a.billno like '"
						+ mBillType
						+ "%'");

		if (tRIBsnsBillDefSet.size() > 0) {
			mBillBatchNo = PubFun1.CreateMaxNo("RIBILLBATCH", 9);
		}

		for (int i = 1; i <= tRIBsnsBillDefSet.size(); i++) {
			RIBsnsBillDefSchema tRIBsnsBillDefSchema = tRIBsnsBillDefSet.get(i);
			mRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
			mRIBsnsBillDetailsSet = new RIBsnsBillDetailsSet();
			mRIBsnsBillBatchSchema.setBatchNo(mBillBatchNo);
			mRIBsnsBillBatchSchema.setBillNo(tRIBsnsBillDefSchema.getBillNo());
			mRIBsnsBillBatchSchema.setCurrency(tRIBsnsBillDefSchema
					.getCurrency());
			mRIBsnsBillBatchSchema.setIncomeCompanyNo(tRIBsnsBillDefSchema
					.getIncomeCompanyNo());
			mRIBsnsBillBatchSchema.setState("01");
			mRIBsnsBillBatchSchema.setRIContNo(tRIBsnsBillDefSchema
					.getRIContNo());
			mRIBsnsBillBatchSchema.setBillTimes(mBillTimes);
			mRIBsnsBillBatchSchema.setStartDate(mStartDate);
			mRIBsnsBillBatchSchema.setEndDate(mEndDate);
			RIPubFun.fillDefaultField(mRIBsnsBillBatchSchema, mGlobalInput);

			RIBsnsBillRelaDB tRIBsnsBillRelaDB = new RIBsnsBillRelaDB();
			RIBsnsBillRelaSet tRIBsnsBillRelaSet = new RIBsnsBillRelaSet();
			tRIBsnsBillRelaDB.setBillNo("000000");
			tRIBsnsBillRelaDB.setCalOrder(0);
			tRIBsnsBillRelaDB.getInfo();
			String where = tRIBsnsBillRelaDB.getCalSQL();

			PubCalculator tPubCalculator = new PubCalculator();
			tPubCalculator.addBasicFactor("StartDate",
					mRIBsnsBillBatchSchema.getStartDate());

			tPubCalculator.addBasicFactor("EndDate",
					mRIBsnsBillBatchSchema.getEndDate());
			tPubCalculator.addBasicFactor("RIContNo",
					mRIBsnsBillBatchSchema.getRIContNo());
			tPubCalculator.addBasicFactor("BillNo",
					mRIBsnsBillBatchSchema.getBillNo());
			tPubCalculator.addBasicFactor("RIComCode",
					mRIBsnsBillBatchSchema.getIncomeCompanyNo());
			tPubCalculator.addBasicFactor("Currency",
					mRIBsnsBillBatchSchema.getCurrency());

			tPubCalculator.setCalSql(where);
			System.out.println(" calSql: " + where);
			String tWhere = tPubCalculator.calculateEx();
			System.out.println(" tcalSql: " + tWhere);

			tRIBsnsBillRelaSet = tRIBsnsBillRelaDB
					.executeQuery("select * from RIBsnsBillRela where billno='"
							+ mRIBsnsBillBatchSchema.getBillNo() + "'");
			for (int j = 1; j <= tRIBsnsBillRelaSet.size(); j++) {
				RIBsnsBillRelaSchema t = tRIBsnsBillRelaSet.get(j);
				RIBsnsBillDetailsSchema tRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
				tRIBsnsBillDetailsSchema.setBatchNo(mRIBsnsBillBatchSchema
						.getBatchNo());
				tRIBsnsBillDetailsSchema.setBillNo(mRIBsnsBillBatchSchema
						.getBillNo());
				tRIBsnsBillDetailsSchema.setCurrency(mRIBsnsBillBatchSchema
						.getCurrency());
				tRIBsnsBillDetailsSchema.setFeeCode(t.getFeeCode());
				tRIBsnsBillDetailsSchema.setFeeName(t.getFeeName());
				RIPubFun.fillDefaultField(tRIBsnsBillDetailsSchema,
						mGlobalInput);

				if ("01".equals(t.getInputType())) {// 系统计算项
					String tItemCalType = t.getItemCalType();
					if ("0".equals(tItemCalType)) { // 不需计算
					}
					if ("1".equals(tItemCalType)) {// 固定值
					}
					if ("2".equals(tItemCalType)) {// SQL
						String mCalSQL = t.getCalSQL();

						tPubCalculator.setCalSql(mCalSQL);
						System.out.println(" calSql: " + mCalSQL);
						String tCalSql = tPubCalculator.calculateEx();
						System.out.println(" tcalSql: " + tCalSql);

						String temp = tExeSQL.getOneValue(tCalSql);
						if (tExeSQL.mErrors.needDealError()) {
							buildError("verifyOperate", "初始化sql计算出错：");
							return false;
						} else {
							if ("01".equals(t.getBillItemType())) {// 费用项
								tRIBsnsBillDetailsSchema.setSummoney(temp);
							} else if ("02".equals(t.getBillItemType())) {// 信息项
								tRIBsnsBillDetailsSchema.setBillItem(temp);
							}
						}
					}
					if ("3".equals(tItemCalType)) {// class
					}
				}

				mRIBsnsBillDetailsSet.add(tRIBsnsBillDetailsSchema);
			}

			String updateSQL = "update rirecordtrace a set billno='"
					+ mRIBsnsBillBatchSchema.getBillNo() + "' ,billbatchno='"
					+ mRIBsnsBillBatchSchema.getBatchNo()
					+ "',settleflag='02' where " + tWhere;

			map = new MMap();
			map.put(updateSQL, "UPDATE");
			map.put(mRIBsnsBillBatchSchema, "INSERT");
			map.put(mRIBsnsBillDetailsSet, "INSERT");
			if (!prepareOutputData()) {
				return false;
			}
		}

		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
			PubSubmit tPubSubmit = new PubSubmit();

			System.out.println("Start RIBsnsBillCalBL Submit...");

			if (!tPubSubmit.submitData(mInputData, null)) {
				if (tPubSubmit.mErrors.needDealError()) {
					buildError("insertData", "保存统计信息时出现错误!");
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIBsnsBillCalBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
