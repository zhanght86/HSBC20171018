

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;

/**
 * <p>
 * Title: RIFinishCalulate
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public class RIFinishCalulate implements RICalMan {
	public CErrors mErrors = new CErrors();
	private RIWFLogSchema mRIWFLogSchema;
	private String mAccumulateDefNo;
	private RIInitSplitSeg mRIInitSplitSeg;
	private String[][] mSeg;
	private MMap mMap;
	private VData mInputData = new VData();
	private PubSubmit mPubSubmit = new PubSubmit();
	private String mEventType;

	public RIFinishCalulate() {
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
		// 记录工作流程日志
		if (!recordLog()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		try {
			mEventType = cOperate;
			mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
					"RIWFLogSchema", 0);
			mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
		} catch (Exception ex) {
			buildError("initInfo", " 生成计算参数记录时，初始化参数失败: " + ex);
			return false;
		}
		return true;

	}

	private boolean init() {
		try {
			mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			mSeg = mRIInitSplitSeg.getValue();
		} catch (Exception ex) {
			buildError("initInfo", " 生成分保记录时初始化参数失败: " + ex);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		StringBuffer strBuffer;
		for (int i = 0; i < mSeg.length; i++) {
			mMap = new MMap();
			strBuffer = new StringBuffer();
			strBuffer
					.append("insert into ripolrecordbake (select * from ripolrecord a where a.accumulatedefno='");
			strBuffer.append(mAccumulateDefNo);
			strBuffer.append("' and a.batchno='");
			strBuffer.append(mRIWFLogSchema.getBatchNo());
			strBuffer.append("' and a. EventType ='");
			strBuffer.append(mEventType);
			strBuffer.append("' and a.EventNo between '");
			strBuffer.append(mSeg[i][0]);
			strBuffer.append("' and '");
			strBuffer.append(mSeg[i][1]);
			strBuffer.append("') ");
			mMap.put(strBuffer.toString(), "INSERT");

			strBuffer = new StringBuffer();
			strBuffer
					.append("delete from ripolrecord a where a.accumulatedefno='");
			strBuffer.append(mAccumulateDefNo);
			strBuffer.append("' and a.batchno='");
			strBuffer.append(mRIWFLogSchema.getBatchNo());
			strBuffer.append("' and a. EventType ='");
			strBuffer.append(mEventType);
			strBuffer.append("' and a.EventNo between '");
			strBuffer.append(mSeg[i][0]);
			strBuffer.append("' and '");
			strBuffer.append(mSeg[i][1]);
			strBuffer.append("' ");
			mMap.put(strBuffer.toString(), "DELETE");
			if (!saveResult()) {
				return false;
			}
		}
		return true;
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
					buildError("saveResult", "保存再保计算备份接口数据时出现错误!");
					return false;
				}
			}
			mMap = null;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RICalItemProc";
			tError.functionName = "saveResult";
			tError.errorMessage = "保存再保结果时出错：" + ex.getMessage();
			System.out.println(" ex.getMessage() " + ex.getMessage());
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean recordLog() {

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIDataMake";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public static void main(String[] args) {
		RIFinishCalulate tRIFinishCalulate = new RIFinishCalulate();
		VData tVData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("200801");
		mRIWFLogSchema.setTaskCode("L001000001");
		tVData.add(mRIWFLogSchema);
		tRIFinishCalulate.submitData(tVData, "");
	}
}
