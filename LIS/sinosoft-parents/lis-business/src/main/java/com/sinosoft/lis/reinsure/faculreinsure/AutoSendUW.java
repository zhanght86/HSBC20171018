

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.db.RIGUWErrorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIClaimStateSchema;
import com.sinosoft.lis.schema.RIGUWErrorSchema;
import com.sinosoft.lis.schema.RIGrpStateSchema;
import com.sinosoft.lis.schema.RIUWIdeaSchema;
import com.sinosoft.lis.schema.RIUWTaskSchema;
import com.sinosoft.lis.vschema.RIClaimStateSet;
import com.sinosoft.lis.vschema.RIGUWErrorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class AutoSendUW {
	public AutoSendUW() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** 传入参数 */
	private VData mInputData;
	/** 登陆信息 */
	private GlobalInput mGlobalInput;
	/** 报错存储对象 */
	public CErrors mErrors = new CErrors();
	/** 最后递交Map */
	private MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String mCaseNo;
	private String mSerialNo;

	private TransferData mTransferData;
	private RIUWTaskSchema tRIUWTaskSchema;
	private RIGrpStateSchema mRIGrpStateSchema;
	private RIUWIdeaSchema mRIUWIdeaSchema;
	private PubSubmit tPubSubmit = new PubSubmit();

	/**
	 * 根据理赔自核结果，自动向 再保部门 发送审核意见
	 * 
	 * @param nInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData nInputData, String cOperate) {
		this.mInputData = nInputData;
		mOperate = cOperate;
		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoSendUWReInsure";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public boolean checkData() {
		if (this.mGlobalInput == null) {
			String str = "登陆信息为null，可能是页面超时，请重新登陆!";
			buildError("checkData", str);
			return false;
		}
		return true;
	}

	public boolean dealData() {
		mCaseNo = (String) mTransferData.getValueByName("CaseNo");

		// 获取理赔自核结果
		RIGUWErrorDB tRIGUWErrorDB = new RIGUWErrorDB();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from RIGUWError a where a.CalItemType='14' and a.AuditAffixCode='000000'  and a.AuditCode='");
		sql.append(mCaseNo);
		sql.append("'");

		RIGUWErrorSet tRIGUWErrorSet = new RIGUWErrorSet();
		tRIGUWErrorSet = tRIGUWErrorDB.executeQuery(sql.toString());

		if (tRIGUWErrorSet != null && tRIGUWErrorSet.size() > 0) {
			// 创建一个任务
			tRIUWTaskSchema = new RIUWTaskSchema();
			mSerialNo = PubFun1.CreateMaxNo("RIUWTASK", 20);
			tRIUWTaskSchema.setSerialNo(mSerialNo);
			tRIUWTaskSchema.setUWNo(1);
			tRIUWTaskSchema.setProposalGrpContNo("000000");
			tRIUWTaskSchema.setProposalContNo("000000");
			tRIUWTaskSchema.setAuditCode(mCaseNo);
			tRIUWTaskSchema.setAuditType("04");
			tRIUWTaskSchema.setAuditAffixCode("000000");
			tRIUWTaskSchema.setState("00"); // 00 待回复
			tRIUWTaskSchema.setOperator(mGlobalInput.Operator);
			tRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
			tRIUWTaskSchema.setMakeDate(PubFun.getCurrentDate());
			tRIUWTaskSchema.setMakeTime(PubFun.getCurrentTime());
			tRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
			tRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());

			// 临分保单状态表
			mRIGrpStateSchema = new RIGrpStateSchema();
			mRIGrpStateSchema.setSerialNo(tRIUWTaskSchema.getSerialNo());
			mRIGrpStateSchema.setProposalGrpContNo(tRIUWTaskSchema
					.getProposalGrpContNo());
			mRIGrpStateSchema.setProposalContNo(tRIUWTaskSchema
					.getProposalContNo());
			mRIGrpStateSchema.setCaseNo(tRIUWTaskSchema.getAuditCode());
			mRIGrpStateSchema.setState("02"); // 02:理赔审核 03:审核处理
			mRIGrpStateSchema.setExetype("04"); // 04-理赔
			mRIGrpStateSchema.setOperator(mGlobalInput.Operator);
			mRIGrpStateSchema.setMakeDate(PubFun.getCurrentDate());
			mRIGrpStateSchema.setMakeTime(PubFun.getCurrentTime());
			mRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
			mRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

			// 再保审核任务核保意见表
			mRIUWIdeaSchema = new RIUWIdeaSchema();
			mRIUWIdeaSchema.setSerialNo(tRIUWTaskSchema.getSerialNo());
			mRIUWIdeaSchema.setUWNo(tRIUWTaskSchema.getUWNo());
			mRIUWIdeaSchema.setProposalGrpContNo("000000");
			mRIUWIdeaSchema.setProposalContNo(tRIUWTaskSchema
					.getProposalContNo());
			mRIUWIdeaSchema.setAuditType(tRIUWTaskSchema.getAuditType());
			mRIUWIdeaSchema.setAuditCode(tRIUWTaskSchema.getAuditCode());
			mRIUWIdeaSchema.setAuditAffixCode("000000");
			mRIUWIdeaSchema.setUWOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setUWIdea("该险种的赔付金额超过通知限额"); // 审核意见
			mRIUWIdeaSchema.setManageCom(mGlobalInput.ManageCom);
			mRIUWIdeaSchema.setMakeDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setMakeTime(PubFun.getCurrentTime());
			mRIUWIdeaSchema.setModifyDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setModifyTime(PubFun.getCurrentTime());

			map.put(tRIUWTaskSchema, "INSERT");
			map.put(mRIGrpStateSchema, "INSERT");
			map.put(mRIUWIdeaSchema, "INSERT");

			// 赔案状态表
			RIClaimStateSet mRIClaimStateSet = new RIClaimStateSet();
			RIClaimStateSchema mRIClaimStateSchema;
			for (int i = 1; i <= tRIGUWErrorSet.size(); i++) {
				RIGUWErrorSchema tRIGUWErrorSchema = tRIGUWErrorSet.get(i);

				mRIClaimStateSchema = new RIClaimStateSchema();
				mRIClaimStateSchema.setCaseNo(mCaseNo);
				if (tRIGUWErrorSchema.getRiskcode() == ""
						|| tRIGUWErrorSchema.getRiskcode() == null) {
					mRIClaimStateSchema.setRIskCode("000000");
				} else {
					mRIClaimStateSchema.setRIskCode(tRIGUWErrorSchema
							.getRiskcode());
				}
				if (tRIGUWErrorSchema.getDutyCode() == ""
						|| tRIGUWErrorSchema.getDutyCode() == null) {
					mRIClaimStateSchema.setDutyCode("000000");
				} else {
					mRIClaimStateSchema.setDutyCode(tRIGUWErrorSchema
							.getDutyCode());
				}
				mRIClaimStateSchema.setState("01"); // 01-通知限额，02-参与限额，03-其它非正常理赔

				mRIClaimStateSchema.setOperator(mGlobalInput.Operator);
				mRIClaimStateSchema.setMakeDate(PubFun.getCurrentDate());
				mRIClaimStateSchema.setMakeTime(PubFun.getCurrentTime());
				mRIClaimStateSet.add(mRIClaimStateSchema);
			}
			map.put(mRIClaimStateSet, "DELETE&INSERT");

			if (!prepareOutputData()) {
				return false;
			}
			if (!tPubSubmit.submitData(this.mInputData, "")) {
				if (tPubSubmit.mErrors.needDealError()) {
					buildError("update", "保存审核任务时出现错误!");
					return false;
				}
			}
			map = null;
			tPubSubmit = null;
		}
		return true;
	}

	public boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "AutoSendUWReInsure";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		AutoSendUW tAutoSendUW = new AutoSendUW();
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CaseNo", "90000000028");
		GlobalInput globalInput = new GlobalInput();
		globalInput.ManageCom = "8611";
		globalInput.Operator = "001";
		tVData.add(tTransferData);
		tVData.add(globalInput);

		tAutoSendUW.submitData(tVData, "");
	}

	private void jbInit() throws Exception {
	}
}
