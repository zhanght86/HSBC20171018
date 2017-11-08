

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
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
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public class RITaskApplyBL implements BusinessService {
	public RITaskApplyBL() {
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 初始化类 */
	private RIInitData mRIInitData;
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	// private TransferData mTransferData = new TransferData();
	private RIWFLogSet mRIWFLogSet;
	private String mPath = "";
	private MMap mMap = new MMap();
	private PubSubmit mPubSubmit = new PubSubmit();
	private String batchNo;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;

	public static void main(String[] args) {
		RITaskApplyBL tRITaskApplyBL = new RITaskApplyBL();
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";

		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("200801");
		mRIWFLogSchema.setTaskCode("L000000014");
		mRIWFLogSchema.setTaskType("01");
		mRIWFLogSchema.setNodeState("01");
		mRIWFLogSchema.setStartDate("2008-1-1");
		mRIWFLogSchema.setEndDate("2008-3-1");
		mRIWFLogSchema.setOperator("001");
		mRIWFLogSchema.setManageCom("86");
		mRIWFLogSchema.setMakeDate("2008-1-1");
		mRIWFLogSchema.setMakeTime("2008-1-1");

		RIWFLogSet tRIWFLogSet = new RIWFLogSet();
		tRIWFLogSet.add(mRIWFLogSchema);
		tVData.add(tRIWFLogSet);
		tVData.add(tGlobalInput);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PATH", "E:\\ing\\ui");
		tVData.add(tTransferData);
		tRITaskApplyBL.submitData(tVData, "");
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		// 进行业务处理
		if (cOperate.equals("01")) {
			if (!dealData(cOperate)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "RITaskApplyBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败RITaskApplyBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			if (!dealData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "RITaskApplyBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败RITaskApplyBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		if (!prepareOutputData()) {
			return false;
		}
		if (!submitData()) {
			return false;
		}
		// if (!log()) {
		// return false;
		// }
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		batchNo = PubFun1.CreateMaxNo("RIWFLOG", 10);
		for (int i = 1; i <= mRIWFLogSet.size(); i++) {
			mRIWFLogSet.get(i).setBatchNo(batchNo);
			mRIWFLogSet.get(i).setTaskType("01");
			mRIWFLogSet.get(i).setNodeState("00");
			mRIWFLogSet.get(i).setManageCom(mGlobalInput.ManageCom);
			mRIWFLogSet.get(i).setOperator(mGlobalInput.Operator);
			mRIWFLogSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mRIWFLogSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mRIWFLogSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mRIWFLogSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		mMap.put(mRIWFLogSet, "INSERT");
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String tBatchNo) {
		for (int i = 1; i <= mRIWFLogSet.size(); i++) {
			mRIWFLogSet.get(i).setTaskType("01");
			mRIWFLogSet.get(i).setNodeState("00");
			mRIWFLogSet.get(i).setManageCom(mGlobalInput.ManageCom);
			mRIWFLogSet.get(i).setOperator(mGlobalInput.Operator);
			mRIWFLogSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mRIWFLogSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mRIWFLogSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mRIWFLogSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		mMap.put(mRIWFLogSet, "INSERT");
		return true;
	}

	private boolean log() {
		RIPubFun tRIPubFun = new RIPubFun();
		for (int i = 1; i <= mRIWFLogSet.size(); i++) {
			try {
				if (!init(mRIWFLogSet.get(i).getTaskCode())) {
					return false;
				}
				if (!tRIPubFun.recordLog(mRIWFLogSet.get(i), mPath, "01")) {
					buildError("submitData", tRIPubFun.mErrors.getFirstError());
					return false;
				}
			} catch (Exception ex) {
				return false;
			} finally {
				if (!destroy()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean init(String accumulateDefNo) {
		try {
			if (accumulateDefNo == null || accumulateDefNo.equals("")) {
				buildError("initInfo", "分保计算任务申请程序运行初始化程序时出错: 累计风险编号为空");
				return false;
			}
			mRIInitData = RIInitData.getObject(accumulateDefNo);
		} catch (Exception ex) {
			buildError("initInfo", "分保计算任务申请运行初始化程序时出错。" + ex.getMessage());
			return false;
		}

		return true;
	}

	private boolean destroy() {
		try {
			if (null != mRIInitData) {
				System.out.println(" ==================正在销毁累计风险为： "
						+ mRIInitData.getRIAccumulateDefNo()
						+ " 的初始化类==================");
				mRIInitData.destory();
				mRIInitData = null;
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mRIWFLogSet = (RIWFLogSet) cInputData.getObjectByObjectName(
				"RIWFLogSet", 0);
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		// mTransferData = (TransferData) cInputData.getObjectByObjectName(
		// "TransferData", 0);
		// mPath = (String) mTransferData.getValueByName("PATH");
		// System.out.println(" RITaskApplyBL --> path: " + mPath);
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			mInputData.add(mMap);
			mResult.clear();
			mResult.add(mRIWFLogSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RITaskApplyBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 向后台提交数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitData() {
		try {
			if (!mPubSubmit.submitData(this.mInputData, "")) {
				if (mPubSubmit.mErrors.needDealError()) {
					buildError("submitData", "保存申请任务时出现错误:"
							+ mPubSubmit.mErrors.getFirstError());
					return false;
				}
			}
			mMap = null;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RITaskApplyBL";
			tError.functionName = "saveResult";
			tError.errorMessage = "保存申请任务时出现错误：" + ex.getMessage();
			System.out.println(" ex.getMessage() " + ex.getMessage());
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		TransferData t = new TransferData();
		t.setNameAndValue("RIWFLogNo", batchNo);
		this.mResult.add(t);
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CalItemDeal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

}
