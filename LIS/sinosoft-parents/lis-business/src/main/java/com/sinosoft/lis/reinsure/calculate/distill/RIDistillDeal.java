

package com.sinosoft.lis.reinsure.calculate.distill;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.init.RIInitDistillData;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable zhangbin
 * @version 1.0
 */
public class RIDistillDeal implements RICalMan {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private String mAccumulateDefNo = "";
	private RIWFLogSchema mRIWFLogSchema;
	private MMap mMap = new MMap();
	private VData mInputData = new VData();
	private PubSubmit mPubSubmit = new PubSubmit();
	/** 数据批次处理限制数 */
	private int mMaxDealNUm = 10000;

	private RIInitDistillData mRIInitDistillData;

	public RIDistillDeal() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!init()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!recordLog()) {
			return false;
		}
		return true;
	}

	/**
	 * 得到业务数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mVData = cInputData;
		mRIWFLogSchema = (RIWFLogSchema) mVData.getObjectByObjectName(
				"RIWFLogSchema", 0);
		mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
		return true;
	}

	/**
	 * 初始化数据
	 * 
	 * @return boolean
	 */
	private boolean init() {
		try {
			mRIInitDistillData = new RIInitDistillData(mAccumulateDefNo);
		} catch (Exception ex) {
			buildError("initInfo", " RIPreceptAssign->1 分保方案分配查询失败 ");
			return false;
		}
		return true;
	}

	/**
	 * 数据处理
	 * 
	 * @param aAccumulateDefNO
	 *            String
	 * @return boolean
	 */
	private boolean dealData() {
		StringBuffer strBuffer;
		// 取得提数算法
		RIItemCalSet riItemCalSet = (RIItemCalSet) mRIInitDistillData
				.getRIDistillCalSet();
		for (int i = 1; i <= riItemCalSet.size(); i++) {
			strBuffer = new StringBuffer();
			mMap = new MMap();
			// 如果为sql算法提数
			if (riItemCalSet.get(i).getItemCalType().equals("2")) {
				PubCalculator tPubCalculator = new PubCalculator();
				tPubCalculator.addBasicFactor("BatchNo",
						mRIWFLogSchema.getBatchNo());
				tPubCalculator.addBasicFactor("AccumulateDefNO",
						mRIWFLogSchema.getTaskCode());
				tPubCalculator.addBasicFactor("StartDate",
						mRIWFLogSchema.getStartDate());
				tPubCalculator.addBasicFactor("EndDate",
						mRIWFLogSchema.getEndDate());
				tPubCalculator.addBasicFactor("MakeDate",
						PubFun.getCurrentDate());
				tPubCalculator.addBasicFactor("MakeTime",
						PubFun.getCurrentTime());
				String sql = riItemCalSet.get(i).getCalSQL();
				if (sql == null || sql.equals("")) {
					buildError("distillData", "类型为"
							+ riItemCalSet.get(i).getArithmeticID()
							+ "数据提取SQL未定义!");
					return false;
				}
				tPubCalculator.setCalSql(sql);
				sql = tPubCalculator.calculateEx();
				while (haveBusynessData(sql)) {
					strBuffer.append("Insert into RIPolRecord ");
					strBuffer.append(sql);
					strBuffer.append(" and rownum <=" + mMaxDealNUm);
					mMap.put(strBuffer.toString(), "INSERT");
					if (!saveResult()) {
						return false;
					}
				}
				if (mErrors.needDealError()) {
					return false;
				}
			} else if (riItemCalSet.get(i).getItemCalType().equals("3")) { // 如何为配置类算法提数
				try {
					Class tClass = Class.forName(riItemCalSet.get(i)
							.getCalClass());
					RIDistill tRIDistill = (RIDistill) tClass.newInstance();
					if (!tRIDistill.submitData(mVData, "")) {
						mErrors.copyAllErrors(tRIDistill.getCErrors());
						return false;
					}
				} catch (Exception ex) {
					buildError("RICalItemProc", " 再保提数时，取得算法为："
							+ riItemCalSet.get(i).getArithmeticID() + "算法序号为："
							+ riItemCalSet.get(i).getCalItemOrder()
							+ "的参数值时出错：" + ex);
					return false;
				}
			} else {

			}
		}
		return true;
	}

	private boolean haveBusynessData(String sql) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			sql = "select count(*) from (" + sql + " and rownum<="
					+ mMaxDealNUm + ")";
			return Integer.parseInt(tExeSQL.getOneValue(sql)) > 0 ? true
					: false;
		} catch (Exception ex) {
			buildError("RICalItemProc", " 提数时查询核心业务系统数据出错。" + ex.getMessage());
			return false;
		}
	}

	/**
	 * 保存结果
	 * 
	 * @return boolean
	 */
	private boolean saveResult() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
			if (!mPubSubmit.submitData(this.mInputData, "")) {
				if (mPubSubmit.mErrors.needDealError()) {
					buildError("saveResult", "保存再保计算结果时出现错误!");
					return false;
				}
			}
			mMap = null;
		} catch (Exception ex) {
			buildError("saveResult", "保存再保结果时出错：" + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean recordLog() {
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIDistillDeal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}
