

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.lis.db.RIWFLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.reinsure.calculate.assign.RIPreceptAssign;
import com.sinosoft.lis.reinsure.calculate.check.RICheckData;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistillDeal;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSerialNo;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.reinsure.calculate.process.RICalPro;
import com.sinosoft.lis.reinsure.calculate.riskcal.RIRiskCal;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
public class RIWFManage implements RICalMan, BusinessService {
	private String mAutoFlag;
	private RIInitData mRIInitData;
	private TransferData mTransferData;
	private RIInitSplitSeg mRIInitSplitSeg;
	private RIInitSerialNo mRIInitSerialNo;
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private RIWFLogSet mRIWFLogSet;
	private RIWFLogSchema mRIWFLogSchema;
	private String mTaskCode;
	private String mBatchNo;
	private RIPubFun mRIPubFun = new RIPubFun();
	private String mAccumulateDefNo = "";
	private String[] procOrder = { "01", "03", "04", "08" };
	private VData mInputData = new VData();
	private String mOperate = "";
	// private String mPath = "";
	private GlobalInput mGlobalInput = new GlobalInput();

	public RIWFManage() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		try {
			if (!dealData()) {
				return false;
			}
		} catch (Exception ex) {
			buildError("initInfo", "数据处理失败" + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		try {
			this.mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			this.mRIWFLogSet = (RIWFLogSet) cInputData.getObjectByObjectName(
					"RIWFLogSet", 0);
			this.mTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);
			// this.mPath = (String) mTransferData.getValueByName("PATH");
			this.mAutoFlag = (String) mTransferData.getValueByName("AUTOFLAG");
			this.mOperate = cOperate;

		} catch (Exception ex) {
			buildError("recordLog", " 流程管理程序得到业务数据时出错。 " + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean init() {
		try {
			this.mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
			if (mAccumulateDefNo == null || mAccumulateDefNo.equals("")) {
				buildError("initInfo", "流程管理程序运行初始化程序时出错: 累计风险编号为空");
				return false;
			}
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			if (!mRIWFLogSchema.getNodeState().equals("01")
					&& !mRIWFLogSchema.getNodeState().equals("00")) {
				mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			}
			mRIInitSerialNo = RIInitSerialNo.getObject();
		} catch (Exception ex) {
			buildError("initInfo", "流程管理程序运行初始化程序时出错。" + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean dealData() {
		for (int i = 1; i <= mRIWFLogSet.size(); i++) {
			try {
				mRIWFLogSchema = mRIWFLogSet.get(i);
				this.mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
				if (mAccumulateDefNo == null || mAccumulateDefNo.equals("")) {
					buildError("initInfo", "流程管理程序运行初始化程序时出错: 累计风险编号为空");
					return false;
				}
				// if (!init()) {
				// return false;
				// }
				// 如果NodeState为"03",则从RIWFLog表中得到当前实际的执行状态
				mTaskCode = mRIWFLogSet.get(i).getTaskCode();
				mBatchNo = mRIWFLogSet.get(i).getBatchNo();
				if (mRIWFLogSchema.getNodeState().equals("03")) {
					char flag = getRecordNum(mTaskCode, mBatchNo);
					if (flag == '3') { // 出错
						return false;
					} else if (flag == '2') { // 数据为空
												// 将任务状态置为'99'
						if (!recordLog("99")) {
							return false;
						}
						continue;
					}
					RIWFLogDB tRIWFLogDB = new RIWFLogDB();
					tRIWFLogDB.setBatchNo(mRIWFLogSchema.getBatchNo());
					tRIWFLogDB.setTaskCode(mRIWFLogSchema.getTaskCode());
					if (!tRIWFLogDB.getInfo()) {
						buildError("dealData", "流程管理程序进行数据处理时出错。没有得到任务信息 "
								+ tRIWFLogDB.mErrors.getFirstError());
						return false;
					}
					RIWFLogSchema tRIWFLogSchema = tRIWFLogDB.getSchema();
					mRIWFLogSchema.setLogPath(tRIWFLogSchema.getLogPath());
					// 如果不是数据校验
					if (!tRIWFLogSchema.getNodeState().equals("02")) {
						for (int j = 0; j < procOrder.length; j++) {
							if (!tRIWFLogSchema.getNodeState().equals(
									procOrder[procOrder.length - 1])) {
								if (tRIWFLogSchema.getNodeState().equals(
										procOrder[j])) {
									// 执行当前状态的下一状态
									mRIWFLogSchema
											.setNodeState(procOrder[j + 1]);
									break;
								}
							}
						}
					}
				}
				System.out.println("当前任务状态 : " + mRIWFLogSchema.getNodeState());
				if (!verify()) {
					return false;
				}
				if (!prepareOutputData()) {
					return false;
				}

				if (mAutoFlag.equals("1")) { // 全部执行
					if (!autoExeWFlow()) {
						recordErrLog();
						return false;
					}
				}
				if (mAutoFlag.equals("2")) { // 分步执行
					if (!singlExeWFlow()) {
						recordErrLog();
						return false;
					}
				}
			} catch (Exception ex) {
				buildError("dealData", "流程管理程序进行数据处理时出错。" + ex.getMessage());
				return false;
			} finally { // 销毁静态对象
				if (!destroy()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean autoExeWFlow() {
		int nodeState = Integer.parseInt(mRIWFLogSchema.getNodeState());
		switch (nodeState) {
		case 1: // 提数
			RIDistillDeal tRIDistillDeal = new RIDistillDeal();
			if (!tRIDistillDeal.submitData(mInputData, "")) {
				buildError("autoExeWFlow",
						tRIDistillDeal.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("01")) {
				return false;
			}
			// 如果是批处理
			if (this.mOperate.equals("01")) {
				char flag = getRecordNum(mTaskCode, mBatchNo);
				if (flag == '3') { // 出错
					return false;
				} else if (flag == '2') { // 数据为空,将任务状态置为'99'
					System.out
							.println("********************************************************************** 数据采集为空************************************************************************");
					if (!recordLog("99")) {
						return false;
					}
					return true;
				}
				try {
					mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
				} catch (Exception ex) {
					buildError("autoExeWFlow", "批处理初始化段值报错"
							+ mRIInitSplitSeg.mErrors.getFirstError());
					return false;
				}
			}
		case 3: // 方案分配
			if (!init()) {
				return false;
			}
			RIPreceptAssign tRIPreceptAssign = new RIPreceptAssign();
			if (!tRIPreceptAssign.submitData(mInputData, "")) {
				buildError("autoExeWFlow",
						tRIPreceptAssign.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("03")) {
				return false;
			}
		case 4: // 风险计算，分保记录生成
			RIRiskCal tRIRiskCal = new RIRiskCal();
			if (!tRIRiskCal.submitData(mInputData, "")) {
				buildError("autoExeWFlow", tRIRiskCal.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("04")) {
				return false;
			}
		case 8: // 再保计算
			RICalPro tRICalPro = new RICalPro();
			if (!tRICalPro.submitData(mInputData, "")) {
				buildError("autoExeWFlow", tRICalPro.mErrors.getFirstError());
				return false;
			}
			// 更新再保编号
			// RISerialNo tRISerialNo = new RISerialNo();
			// if (!tRISerialNo.submitData(mInputData, "")) {
			// buildError("autoExeWFlow", tRISerialNo.mErrors.getFirstError());
			// return false;
			// }
			if (!recordLog("99")) {
				return false;
			}
		case 99:
			return true;
		default:
			buildError("autoExeWFlow", "未定义的工作流节点");
			return false;
		}
	}

	private boolean singlExeWFlow() {
		if (mRIWFLogSchema.getNodeState().equals("01")) {
			RIDistillDeal tRIDistillDeal = new RIDistillDeal();
			if (!tRIDistillDeal.submitData(mInputData, "")) {
				buildError("RIWFManage", tRIDistillDeal.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("01")) {
				return false;
			}
		} else if (mRIWFLogSchema.getNodeState().equals("02")) {
			RICheckData tRICheckData = new RICheckData();
			if (!tRICheckData.submitData(mInputData, "")) {
				buildError("RIWFManage", tRICheckData.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("02")) {
				return false;
			}
		} else if (mRIWFLogSchema.getNodeState().equals("03")) { // 方案分配
			RIPreceptAssign tRIPreceptAssign = new RIPreceptAssign();
			if (!tRIPreceptAssign.submitData(mInputData, "")) {
				buildError("RIWFManage",
						tRIPreceptAssign.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("03")) {
				return false;
			}
		} else if (mRIWFLogSchema.getNodeState().equals("04")) { // 风险计算，分保记录生成
			RIRiskCal tRIRiskCal = new RIRiskCal();
			if (!tRIRiskCal.submitData(mInputData, "")) {
				buildError("RIWFManage", tRIRiskCal.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("04")) {
				return false;
			}

		} else if (mRIWFLogSchema.getNodeState().equals("08")) { // 再保计算
			RICalPro tRICalPro = new RICalPro();
			if (!tRICalPro.submitData(mInputData, "")) {
				buildError("RIWFManage", tRICalPro.mErrors.getFirstError());
				return false;
			}
			if (!recordLog("99")) {
				return false;
			}
		} else if (mRIWFLogSchema.getNodeState().equals("99")) {
			if (!recordLog("99")) {
				return false;
			}
		} else {
			if (!recordLog(mRIWFLogSchema.getNodeState())) {
				return false;
			}
		}
		return true;
	}

	private char getRecordNum(String tAccumulateDefNo, String tBatchNo) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append(" select count(*) from ripolrecord a where a.accumulatedefno='");
		strBuffer.append(tAccumulateDefNo);
		strBuffer.append("' and a.batchno='");
		strBuffer.append(tBatchNo);
		strBuffer.append("'");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
		System.out.println("initRISplitSeg: " + strBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("initRISplitSeg",
					"RISplitSeg程序查询再保接口表失败" + mErrors.getFirstError());
			return '3'; // 报错
		}
		if (tSSRS.GetText(1, 1).equals("0")) {
			return '2'; // 无数据
		} else {
			return '1'; // 有数据
		}
	}

	private boolean destroy() {
		try {
			System.out.println(" ==================销毁累计风险为：" + mAccumulateDefNo
					+ "的初始化类 ================ ");
			if (null != mRIInitData) {
				mRIInitData.destory();
				mRIInitData = null;
			}
		} catch (Exception ex) {
			return false;
		} finally {
			try {
				if (mRIInitSerialNo != null) {
					mRIInitSerialNo.destory();
					mRIInitSerialNo = null;
				}
			} catch (Exception ex) {
				return false;
			} finally {
				try {
					System.out.println(mRIInitSplitSeg);
					if (null != mRIInitSplitSeg) {
						mRIInitSplitSeg.destory();
						mRIInitSplitSeg = null;
					}
				} catch (Exception ex) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean verify() {
		if (mAccumulateDefNo == null || mAccumulateDefNo.equals("")) {
			buildError("initInfo", " 流程管理程序出错，累计风险编号为空 ");
			return false;
		}
		if (mRIWFLogSchema.getBatchNo() == null
				|| mRIWFLogSchema.getBatchNo().equals("")) {
			buildError("initInfo", " 流程管理程序出错，批次号为空 ");
			return false;
		}
		if (mRIWFLogSchema.getNodeState() == null
				|| mRIWFLogSchema.getNodeState().equals("")
				|| mRIWFLogSchema.getNodeState().equals("99")) {
			buildError("initInfo", " 流程管理程序出错，工作流节点为空或任务已结束 ");
			return false;
		}
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			mInputData.add(mRIWFLogSchema);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RITaskApplyBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean recordLog(String nodeState) {
		mRIWFLogSchema.setNodeState(nodeState);
		RIPubFun tRIPubFun = new RIPubFun();
		if (!tRIPubFun.recordWFLog(mRIWFLogSchema, "")) {
			// System.out.println(" path: " + mPath);
			buildError("recordLog",
					"流程管理程序记录日志时出错： " + tRIPubFun.mErrors.getFirstError());
			return false;
		}
		return true;
	}

	private boolean recordErrLog() {
		RIRecordErrLog tRIRecordErrLog = new RIRecordErrLog();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ErrorInfo", mErrors.getFirstError());
		VData tVData = new VData();
		tVData.add(mRIWFLogSchema);
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		if (!tRIRecordErrLog.submitData(tVData, "")) {
			buildError("recordLog", "流程管理程序记录错误日志时出错： "
					+ tRIRecordErrLog.mErrors.getFirstError());
			return false;
		}
		RIPubFun tRIPubFun = new RIPubFun();
		if (!tRIPubFun.recordLog(mRIWFLogSchema, mErrors.getFirstError(), "01")) {
			buildError("recordLog",
					"流程管理程序记录错误日志时出错： " + tRIPubFun.mErrors.getFirstError());
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		RIWFManage tRIWFManage = new RIWFManage();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		VData tVData = new VData();
		RIWFLogDB tRIWFLogDB = new RIWFLogDB();
		// tRIWFLogDB.setBatchNo("0000000160");

		TransferData tTransferData = new TransferData();

		String tSQL = " select * from RIWFLog a where a.nodestate = '00' ";
		RIWFLogSet tRIWFLogSet = tRIWFLogDB.executeQuery(tSQL);
		if (tRIWFLogSet.size() != 0) {
			for (int i = 1; i <= tRIWFLogSet.size(); i++) {
				tRIWFLogSet.get(i).setNodeState("01");
			}
			tTransferData.setNameAndValue("AUTOFLAG", "2"); // 1-全部执行, 2-分布执行
		} else {
			tSQL = " select * from RIWFLog a where a.nodestate <> '99' ";
			tRIWFLogSet = tRIWFLogDB.executeQuery(tSQL);
			for (int i = 1; i <= tRIWFLogSet.size(); i++) {
				tRIWFLogSet.get(i).setNodeState("03");
			}
			tTransferData.setNameAndValue("AUTOFLAG", "1"); // 1-全部执行, 2-分布执行
		}
		tTransferData.setNameAndValue("PATH", "E:\\ing\\ui");
		tVData.add(tRIWFLogSet);
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		System.out.println(" start: " + PubFun.getCurrentTime());
		tRIWFManage.submitData(tVData, "");
		System.out.println(" end: " + PubFun.getCurrentTime());

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIWFManage";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}

