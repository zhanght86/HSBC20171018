

package com.sinosoft.lis.reinsure.calculate.init;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

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
 * @author not attributable
 * @version 1.0
 */
public class RIInitSerialNo {
	public CErrors mErrors = new CErrors();
	private static RIInitSerialNo mRIInitSerialNo = null;
	/** 分保记录序列号 */
	private String mCessRecordSerialNo = "";

	private RIInitSerialNo() throws Exception {
		if (!init()) {
			Exception r = new Exception(mErrors.getFirstError());
			throw r;
		}
	}

	/**
	 * 得到初始化类实例方法
	 */
	public static RIInitSerialNo getObject() throws Exception {
		if (mRIInitSerialNo == null) {
			try {
				mRIInitSerialNo = new RIInitSerialNo();
			} catch (Exception ex) {
				throw ex;
			}
		}
		return mRIInitSerialNo;
	}

	private boolean init() {
		if (!initCessRecordSerialNo()) {
			return false;
		}
		return true;
	}

	public String getCessRecordSerialNo() {
		return mCessRecordSerialNo;
	}

	public void setCessRecordSerialNo(String pSerialNo) {
		this.mCessRecordSerialNo = pSerialNo;
	}

	// 初始化分保记录序列号
	private boolean initCessRecordSerialNo() {
		String tSQL = "select max(a.SerialNo) from RIRecordTraceTemp a";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(tSQL);
		if (tExeSQL.mErrors.needDealError()) {
			buildError("initRISplitSeg",
					"RISplitSeg程序查询再保接口表失败" + mErrors.getFirstError());
			return false;
		}
		if (tSSRS.GetText(1, 1) == null || tSSRS.GetText(1, 1).equals("")) {
			tSQL = "select max(a.SerialNo) from RIRecordTrace a";
			tSSRS = tExeSQL.execSQL(tSQL);
			if (tSSRS.GetText(1, 1) == null || tSSRS.GetText(1, 1).equals("")) {
				mCessRecordSerialNo = "00000000000000300000";
			} else {
				mCessRecordSerialNo = tSSRS.GetText(1, 1);
			}
			return true;
		} else {
			mCessRecordSerialNo = tSSRS.GetText(1, 1);
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "InitInfo";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public void destory() {
		mRIInitSerialNo = null;
	}

	public static void main(String[] args) {
		try {
			RIInitSerialNo tRIInitSerialNo = RIInitSerialNo.getObject();
			System.out.println(" aa: "
					+ tRIInitSerialNo.getCessRecordSerialNo());
		} catch (Exception ex) {

		}
	}

}
