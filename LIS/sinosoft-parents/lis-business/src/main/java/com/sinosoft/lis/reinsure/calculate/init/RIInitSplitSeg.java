

package com.sinosoft.lis.reinsure.calculate.init;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @ zhangbin
 * @version 1.0
 */

import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.reinsure.tools.RISplitSerialNo;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class RIInitSplitSeg {
	public CErrors mErrors = new CErrors();
	private RIWFLogSchema mRIWFLogSchema;
	private String mAccumulateDefNo;
	private String[][] mSeg;
	/** 分段间隔默认值为5000 */
	private int mLen = 5000;
	/** 私有静态构造器 */
	private static RIInitSplitSeg mRIInitSplitSeg = null;

	private RIInitSplitSeg() {
	}

	private RIInitSplitSeg(RIWFLogSchema riWFLogSchema) throws Exception {
		mAccumulateDefNo = riWFLogSchema.getTaskCode();
		mRIWFLogSchema = riWFLogSchema;
		if (!init()) {
			Exception r = new Exception(mErrors.getFirstError());
			throw r;
		}
	}

	private RIInitSplitSeg(RIWFLogSchema riWFLogSchema, int len)
			throws Exception {
		mAccumulateDefNo = riWFLogSchema.getTaskCode();
		mRIWFLogSchema = riWFLogSchema;
		mLen = len;
		if (!init()) {
			Exception r = new Exception(mErrors.getFirstError());
			throw r;
		}
	}

	/**
	 * 得到初始化类实例方法
	 */
	public static RIInitSplitSeg getObject(RIWFLogSchema riWFLogSchema)
			throws Exception {
		if (mRIInitSplitSeg == null) {
			try {
				mRIInitSplitSeg = new RIInitSplitSeg(riWFLogSchema);
			} catch (Exception ex) {
				throw ex;
			} finally {
			}
		}
		return mRIInitSplitSeg;
	}

	/**
	 * 得到初始化类实例方法
	 * 
	 * @param riWFLogSchema
	 *            RIWFLogSchema
	 * @param len
	 *            int 分段长度
	 * @return RIInitSplitSeg
	 * @throws Exception
	 */
	public static RIInitSplitSeg getObject(RIWFLogSchema riWFLogSchema, int len)
			throws Exception {
		// mRIInitSplitSeg=null;
		if (mRIInitSplitSeg == null) {
			try {
				mRIInitSplitSeg = new RIInitSplitSeg(riWFLogSchema, len);
			} catch (Exception ex) {
				throw ex;
			}
		}
		return mRIInitSplitSeg;
	}

	/**
	 * 初始化方法
	 * 
	 * @return boolean
	 */
	private boolean init() {
		// 初始化
		if (!initRISplitSeg()) {
			buildError("init", "记录号分段失败" + mErrors.getFirstError());
			return false;
		}
		return true;
	}

	// 初始化
	private boolean initRISplitSeg() {
		if (!verify()) {
			buildError("initRISplitSeg",
					"分段接口表数据出错 " + this.mErrors.getFirstError());
			return false;
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append(" select nvl(min(a.eventno),0),nvl(max(a.eventno),0) from ripolrecord a where a.accumulatedefno='");
		strBuffer.append(mAccumulateDefNo);
		strBuffer.append("' and a.batchno='");
		strBuffer.append(mRIWFLogSchema.getBatchNo());
		strBuffer.append("'");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("initRISplitSeg", "接口表记录分段失败" + mErrors.getFirstError());
			return false;
		}
		RISplitSerialNo tRISplitSerialNo = new RISplitSerialNo();
		mSeg = tRISplitSerialNo.splitSerialNo(tSSRS.GetText(1, 1),
				tSSRS.GetText(1, 2), mLen);
		if (tRISplitSerialNo.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tRISplitSerialNo.mErrors);
			return false;
		}
		if (mSeg == null) {
			this.mErrors.copyAllErrors(tRISplitSerialNo.mErrors);
			return false;
		}
		return true;
	}

	public String[][] getValue() {
		return mSeg;
	}

	private boolean verify() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append(" select count(*) from ripolrecord a where a.accumulatedefno='");
		strBuffer.append(mAccumulateDefNo);
		strBuffer.append("' and a.batchno='");
		strBuffer.append(mRIWFLogSchema.getBatchNo());
		strBuffer.append("'");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("initRISplitSeg",
					"RISplitSeg程序查询再保接口表失败" + mErrors.getFirstError());
			return false;
		}
		if (tSSRS.GetText(1, 1).equals("0")) {
			return true;
		}
		RIPubFun tRIPubFun = new RIPubFun();
		tRIPubFun.recordLog(mRIWFLogSchema,
				"累计风险编号为：" + mRIWFLogSchema.getTaskCode() + "   分保处理数据 "
						+ tSSRS.GetText(1, 1) + " 条", "01");
		return true;
	}

	/**
	 * 销毁实例。
	 */
	public void destory() {
		mSeg = null;
		mLen = 0;
		mRIInitSplitSeg = null;
	}

	public static void main(String[] args) {
		try {
			RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
			mRIWFLogSchema.setBatchNo("200801");
			mRIWFLogSchema.setTaskCode("L001000001");
			mRIWFLogSchema.setNodeState("09");
			RIInitSplitSeg tRIInitSplitSeg = RIInitSplitSeg.getObject(
					mRIWFLogSchema, 2);
			String[][] a = tRIInitSplitSeg.getValue();
			for (int i = 0; i <= a.length; i++) {
				System.out.println(" aa: " + a[i][0] + "   " + a[i][1]);
			}
		} catch (Exception ex) {

		}
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIInitSplitSeg";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
