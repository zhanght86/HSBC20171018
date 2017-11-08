

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.db.RIGrpStateDB;
import com.sinosoft.lis.db.RIUWTaskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIGrpStateSchema;
import com.sinosoft.lis.schema.RIUWIdeaSchema;
import com.sinosoft.lis.schema.RIUWTaskSchema;
import com.sinosoft.lis.vschema.RIGrpStateSet;
import com.sinosoft.lis.vschema.RIUWTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 再保
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @Zhang Bin
 * @version 1.0
 */
public class SendUWReInsureBL {
	public SendUWReInsureBL() {
	}

	/** 传入参数 */
	private VData mInputData;
	/** 传入操作符 */
	private String mOperate;
	/** 登陆信息 */
	private GlobalInput mGlobalInput;
	/** 报错存储对象 */
	public CErrors mErrors = new CErrors();
	/** 最后保存结果 */
	private VData mResult = new VData();
	/** 最后递交Map */
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOpeData;
	private String mUWConclusionInfo;
	private String mSerialNo = "";
	private String mEdorNo;
	private String mEdorType;
	private String mCaseNo;
	private String mOpeFlag;
	private String mRemark;
	private String mFilePath;
	private String mFileName;
	private int mNo; // 实际申请的个数
	private TransferData mTransferData = new TransferData();
	/** 再保审核任务表 */
	private RIUWTaskSchema mRIUWTaskSchema = new RIUWTaskSchema();

	/** 再保审核任务核保意见表 */
	private RIUWIdeaSchema mRIUWIdeaSchema = new RIUWIdeaSchema();
	private RIGrpStateSchema mRIGrpStateSchema = new RIGrpStateSchema();

	/**
	 * submitData
	 * 
	 * @param nInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData nInputData, String cOperate) {
		this.mInputData = nInputData;
		this.mOperate = cOperate;
		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(this.mResult, "")) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * getInputData
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "SendUWReInsurBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (this.mGlobalInput == null) {
			String str = "登陆信息为null，可能是页面超时，请重新登陆!";
			buildError("checkData", str);
			return false;
		}
		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (mOperate.equals("FALSE")) {// 发送
			if (!sendOperate()) {
				return false;
			}
		} else if (mOperate.equals("TRUE")) {
			if (!overOperate()) { // 办结
				return false;
			}
		} else if (mOperate.equals("FALSE1")) { // 审核完毕
			if (!auditEnd()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * auditEnd
	 * 
	 * @return boolean
	 */
	private boolean auditEnd() {
		System.out.println(" 正在处理审核完毕............................... ");
		mOpeData = (String) mTransferData.getValueByName("OpeData");
		mOpeFlag = (String) mTransferData.getValueByName("OpeFlag");
		mCaseNo = (String) mTransferData.getValueByName("CaseNo");
		mUWConclusionInfo = (String) mTransferData
				.getValueByName("UWConclusionInfo");
		mSerialNo = (String) mTransferData.getValueByName("SerialNo");
		if (mOpeFlag.equals("01")) { // 核保
			RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
			tRIGrpStateDB.setSerialNo(mSerialNo);
			if (!tRIGrpStateDB.getInfo()) {
				mSerialNo = PubFun1.CreateMaxNo("RIUWTASK", 20);
				mRIGrpStateSchema.setSerialNo(mSerialNo);
				mRIGrpStateSchema.setProposalGrpContNo("000000");
				mRIGrpStateSchema.setProposalContNo(mOpeData);
				mRIGrpStateSchema.setCaseNo("000000");
				mRIGrpStateSchema.setState("03"); // 02:临分审核 03:临分处理 04:处理完毕
				mRIGrpStateSchema.setExetype("01"); // 01-核保 02-核赔
				mRIGrpStateSchema.setOperator(mGlobalInput.Operator);
				mRIGrpStateSchema.setMakeDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setMakeTime(PubFun.getCurrentTime());
				mRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

				map.put(mRIGrpStateSchema, "INSERT");
			} else {
				mRIGrpStateSchema.setSchema(tRIGrpStateDB.getSchema());
				mRIGrpStateSchema.setState("03");
				map.put(mRIGrpStateSchema, "UPDATE");
			}
		} else if (mOpeFlag.equals("04")) { // 核赔
			RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
			tRIGrpStateDB.setSerialNo(mSerialNo);
			if (!tRIGrpStateDB.getInfo()) {
				mSerialNo = PubFun1.CreateMaxNo("RIUWTASK", 20);
				mRIGrpStateSchema.setSerialNo(mSerialNo);
				mRIGrpStateSchema.setProposalGrpContNo("000000");
				mRIGrpStateSchema.setProposalContNo(mOpeData);
				mRIGrpStateSchema.setCaseNo(mCaseNo);
				mRIGrpStateSchema.setState("03"); // 02:理赔审核 03:审核完毕
				mRIGrpStateSchema.setExetype("04"); // 01:个人新单 02:个人续期 03:个人保全
													// 04:理赔 05:团体新单 06:团体保全
				mRIGrpStateSchema.setOperator(mGlobalInput.Operator);
				mRIGrpStateSchema.setMakeDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setMakeTime(PubFun.getCurrentTime());
				mRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

				map.put(mRIGrpStateSchema, "INSERT");
			} else {
				mRIGrpStateSchema.setSchema(tRIGrpStateDB.getSchema());
				mRIGrpStateSchema.setState("03");
				map.put(mRIGrpStateSchema, "UPDATE");
			}
		} else if (mOpeFlag.equals("05")) {

		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";
		globalInput.ManageCom = "86";
		SendUWReInsureBL tSendUWReInsureBL = new SendUWReInsureBL();

		VData t = new VData();
		t.add(globalInput);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OpeData", "2008070110");
		tTransferData.setNameAndValue("FilePath", "filepath");
		tTransferData.setNameAndValue("FileName", "filename");
		tTransferData.setNameAndValue("EdorNo", null);
		tTransferData.setNameAndValue("EdorType", null);
		tTransferData.setNameAndValue("CaseNo", null);
		tTransferData.setNameAndValue("OpeFlag", "01");
		tTransferData.setNameAndValue("Remark", "aaaaaaaaaa");
		tTransferData.setNameAndValue("UWConclusionInfo", "01");
		t.add(tTransferData);
		tSendUWReInsureBL.submitData(t, "TRUE");
	}

	/**
	 * 审核发送操作
	 * 
	 * @return boolean
	 */
	private boolean sendOperate() {
		mOpeData = (String) mTransferData.getValueByName("OpeData");
		mRemark = (String) mTransferData.getValueByName("Remark");
		mFilePath = (String) mTransferData.getValueByName("FilePath");
		mFileName = (String) mTransferData.getValueByName("FileName");
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		mCaseNo = (String) mTransferData.getValueByName("CaseNo");
		mOpeFlag = (String) mTransferData.getValueByName("OpeFlag");

		int uwno = 0; // 核保顺序号
		RIUWTaskDB tRIUWTaskDB = new RIUWTaskDB();
		StringBuffer sql = new StringBuffer();

		if (mOpeFlag.equals("01")) {
			// send
			sql.append("select * from RIUWTask a where a.serialno = (select max(b.Serialno) from RIUWTASK b where b.proposalgrpcontno='000000' and b.proposalcontno='");
			sql.append(mOpeData);
			sql.append("' and trim(AuditType)='01')");
			sql.append(" and exists (select * from RIGrpState c where c.SerialNo = a.SerialNo and c.State = '02')");
			System.out.println(" ******************************** sql: "
					+ sql.toString());
			RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
			if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
				mSerialNo = PubFun1.CreateMaxNo("RIUWTASK", 20);
				mRIUWTaskSchema.setSerialNo(mSerialNo);
				mRIUWTaskSchema.setUWNo(1);
				mRIUWTaskSchema.setProposalGrpContNo("000000");
				mRIUWTaskSchema.setProposalContNo(mOpeData);
				mRIUWTaskSchema.setAuditType(mOpeFlag);
				mRIUWTaskSchema.setAuditCode("000000");
				mRIUWTaskSchema.setAuditAffixCode("000000");
				mRIUWTaskSchema.setState("00"); // 00-待回复,01-已回复,02-已办结
				mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
				mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
				mRIUWTaskSchema.setMakeDate(PubFun.getCurrentDate());
				mRIUWTaskSchema.setMakeTime(PubFun.getCurrentTime());
				mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
				mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
				// 临分保单状态表
				mRIGrpStateSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
				mRIGrpStateSchema.setProposalGrpContNo(mRIUWTaskSchema
						.getProposalGrpContNo());
				mRIGrpStateSchema.setProposalContNo(mRIUWTaskSchema
						.getProposalContNo());
				mRIGrpStateSchema.setCaseNo("000000");
				mRIGrpStateSchema.setState("02"); // 02:临分审核 03:临分处理 04:处理完毕
				mRIGrpStateSchema.setExetype("01"); // 01-核保 02-核赔
				mRIGrpStateSchema.setOperator(mGlobalInput.Operator);
				mRIGrpStateSchema.setMakeDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setMakeTime(PubFun.getCurrentTime());
				mRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

				map.put(mRIUWTaskSchema, "INSERT");
				map.put(mRIGrpStateSchema, "INSERT");
			} else {
				mRIUWTaskSchema = tRIUWTaskSet.get(1);
				mRIUWTaskSchema.setSchema(tRIUWTaskSet.get(1));
				mRIUWTaskSchema.setState("00");
				mRIUWTaskSchema.setUWNo(tRIUWTaskSet.get(1).getUWNo() + 1);
				// 临分保单状态表
				RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
				tRIGrpStateDB.setProposalContNo(mRIUWTaskSchema
						.getProposalContNo());
				tRIGrpStateDB.setCaseNo("000000");
				tRIGrpStateDB.setExetype("01");
				RIGrpStateSet tRIGrpStateSet = tRIGrpStateDB.query();
				if (tRIGrpStateSet.size() == 0) {
					buildError("dealData", "没有得到任务状态");
					return false;
				}
				RIGrpStateSchema tRIGrpStateSchema = tRIGrpStateSet.get(1);
				tRIGrpStateSchema.setState("02");
				tRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
				tRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(mRIUWTaskSchema, "UPDATE");
				map.put(tRIGrpStateSchema, "UPDATE");
			}
			mRIUWIdeaSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
			mRIUWIdeaSchema.setUWNo(mRIUWTaskSchema.getUWNo());
			mRIUWIdeaSchema.setProposalGrpContNo("000000");
			mRIUWIdeaSchema.setProposalContNo(mOpeData);
			mRIUWIdeaSchema.setAuditType(mOpeFlag);
			mRIUWIdeaSchema.setAuditCode("000000");
			mRIUWIdeaSchema.setAuditAffixCode("000000");
			mRIUWIdeaSchema.setAdjunctPath(mFilePath + mFileName);
			mRIUWIdeaSchema.setUWOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setUWIdea(mRemark);
			mRIUWIdeaSchema.setManageCom(mGlobalInput.ManageCom);
			mRIUWIdeaSchema.setMakeDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setMakeTime(PubFun.getCurrentTime());
			mRIUWIdeaSchema.setModifyDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(mRIUWIdeaSchema, "INSERT");
		} else if (mOpeFlag.equals("03")) {
			// 个单保全
		} else if (mOpeFlag.equals("04")) {
			// 个单理赔
			sql.append("select * from RIUWTask a where a.serialno = (select max(b.Serialno) from RIUWTASK b where b.proposalgrpcontno = '000000' and b.auditcode = trim('");
			sql.append(mCaseNo);
			sql.append("') and trim(AuditType) = '04') and exists (select * from RIGrpState c where c.SerialNo = a.SerialNo and c.State = '02')");

			System.out.println(" ******************************** sql: "
					+ sql.toString());
			RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());

			if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
				mSerialNo = PubFun1.CreateMaxNo("RIUWTASK", 20);
				mRIUWTaskSchema.setSerialNo(mSerialNo);
				mRIUWTaskSchema.setUWNo(1);
				mRIUWTaskSchema.setProposalGrpContNo("000000");
				mRIUWTaskSchema.setProposalContNo(mOpeData);
				mRIUWTaskSchema.setAuditType(mOpeFlag);
				mRIUWTaskSchema.setAuditCode(mCaseNo);
				mRIUWTaskSchema.setAuditAffixCode("000000");
				mRIUWTaskSchema.setState("00"); // 00-待回复,01-已回复,02-已办结
				mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
				mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
				mRIUWTaskSchema.setMakeDate(PubFun.getCurrentDate());
				mRIUWTaskSchema.setMakeTime(PubFun.getCurrentTime());
				mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
				mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
				// 临分保单状态表
				mRIGrpStateSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
				mRIGrpStateSchema.setProposalGrpContNo(mRIUWTaskSchema
						.getProposalGrpContNo());
				mRIGrpStateSchema.setProposalContNo(mRIUWTaskSchema
						.getProposalContNo());
				mRIGrpStateSchema.setCaseNo(mRIUWTaskSchema.getAuditCode());
				mRIGrpStateSchema.setState("02"); // 02:理赔审核 03:理赔处理
				mRIGrpStateSchema.setExetype("04"); // 01-核保 02-核赔
				mRIGrpStateSchema.setOperator(mGlobalInput.Operator);
				mRIGrpStateSchema.setMakeDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setMakeTime(PubFun.getCurrentTime());
				mRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
				mRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

				map.put(mRIUWTaskSchema, "INSERT");
				map.put(mRIGrpStateSchema, "INSERT");
			} else {
				mRIUWTaskSchema = tRIUWTaskSet.get(1);
				mRIUWTaskSchema.setSchema(tRIUWTaskSet.get(1));
				mRIUWTaskSchema.setState("00");
				mRIUWTaskSchema.setUWNo(tRIUWTaskSet.get(1).getUWNo() + 1);
				// 临分保单状态表
				RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
				tRIGrpStateDB.setProposalContNo(mRIUWTaskSchema
						.getProposalContNo());
				tRIGrpStateDB.setCaseNo(mRIUWTaskSchema.getAuditCode());
				tRIGrpStateDB.setExetype("04");
				RIGrpStateSet tRIGrpStateSet = tRIGrpStateDB.query();
				if (tRIGrpStateSet.size() == 0) {
					buildError("dealData", "没有得到任务状态");
					return false;
				}
				RIGrpStateSchema tRIGrpStateSchema = tRIGrpStateSet.get(1);
				tRIGrpStateSchema.setState("02");
				tRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
				tRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(mRIUWTaskSchema, "UPDATE");
				map.put(tRIGrpStateSchema, "UPDATE");
			}
			mRIUWIdeaSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
			mRIUWIdeaSchema.setUWNo(mRIUWTaskSchema.getUWNo());
			mRIUWIdeaSchema.setProposalGrpContNo("000000");
			mRIUWIdeaSchema.setProposalContNo(mOpeData);
			mRIUWIdeaSchema.setAuditType(mOpeFlag);
			mRIUWIdeaSchema.setAuditCode(mRIUWTaskSchema.getAuditCode());
			mRIUWIdeaSchema.setAuditAffixCode("000000");
			mRIUWIdeaSchema.setAdjunctPath(mFilePath + mFileName);
			mRIUWIdeaSchema.setUWOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setUWIdea(mRemark);
			mRIUWIdeaSchema.setManageCom(mGlobalInput.ManageCom);
			mRIUWIdeaSchema.setMakeDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setMakeTime(PubFun.getCurrentTime());
			mRIUWIdeaSchema.setModifyDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(mRIUWIdeaSchema, "INSERT");

		} else if (mOpeFlag.equals("05")) {
			sql.append("select * from RIUWTask where ProposalGrpContNo = '");
			sql.append(mOpeData);
			sql.append("' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditAffixCode='000000' and AuditType='05' order by uwno desc");

			RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
			if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
				uwno = 1;
			} else {
				uwno = tRIUWTaskSet.size() + 1;
			}
			// 再保审核任务表
			mRIUWTaskSchema.setUWNo(uwno);
			mRIUWTaskSchema.setProposalGrpContNo(mOpeData);
			mRIUWTaskSchema.setProposalContNo("000000");
			mRIUWTaskSchema.setAuditType("05");
			mRIUWTaskSchema.setAuditCode("000000");
			mRIUWTaskSchema.setAuditAffixCode("000000");
			mRIUWTaskSchema.setState("00"); // 置待回复状态
			mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
			mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
			mRIUWTaskSchema.setMakeDate(PubFun.getCurrentDate());
			mRIUWTaskSchema.setMakeTime(PubFun.getCurrentTime());
			mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
			mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());

			// 再保审核任务核保意见表
			mRIUWIdeaSchema.setUWNo(uwno);
			mRIUWIdeaSchema.setProposalGrpContNo(mOpeData);
			mRIUWIdeaSchema.setProposalContNo("000000");
			mRIUWIdeaSchema.setAuditType("05");
			mRIUWIdeaSchema.setAuditCode("000000");
			mRIUWIdeaSchema.setAuditAffixCode("000000");
			mRIUWIdeaSchema.setAdjunctPath(mFilePath + mFileName);
			mRIUWIdeaSchema.setUWOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setOperator(mGlobalInput.Operator);
			mRIUWIdeaSchema.setUWIdea(mRemark);
			mRIUWIdeaSchema.setManageCom(mGlobalInput.ManageCom);
			mRIUWIdeaSchema.setMakeDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setMakeTime(PubFun.getCurrentTime());
			mRIUWIdeaSchema.setModifyDate(PubFun.getCurrentDate());
			mRIUWIdeaSchema.setModifyTime(PubFun.getCurrentTime());
			RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
			tRIGrpStateDB.setProposalGrpContNo(this.mOpeData);
			if (!tRIGrpStateDB.getInfo()) {
				mRIGrpStateSchema.setProposalGrpContNo(this.mOpeData);
				mRIGrpStateSchema.setState("02");
				map.put(mRIGrpStateSchema, "INSERT");
			} else {
				mRIGrpStateSchema.setSchema(tRIGrpStateDB.getSchema());
				mRIGrpStateSchema.setState("02");
				map.put(mRIGrpStateSchema, "UPDATE");
			}
		} else {
			String str = "非法数据处理类型 !";
			buildError("dealData", str);
		}
		return true;
	}

	private boolean overOperate() {
		mOpeData = (String) mTransferData.getValueByName("OpeData");
		System.out.println(" mOpeData: " + mOpeData);
		mRemark = (String) mTransferData.getValueByName("Remark");
		mFilePath = (String) mTransferData.getValueByName("FilePath");
		mFileName = (String) mTransferData.getValueByName("FileName");
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		mCaseNo = (String) mTransferData.getValueByName("CaseNo");
		mOpeFlag = (String) mTransferData.getValueByName("OpeFlag");
		mSerialNo = (String) mTransferData.getValueByName("SerialNo");
		StringBuffer sql = new StringBuffer();
		int uwno = 0; // 核保顺序号
		RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
		RIUWTaskDB tRIUWTaskDB = new RIUWTaskDB();
		if (mOpeFlag.equals("01")) {// 得到最后一次
			sql.append(" select * from RIUWTASK b where b.serialno=(select max(a.serialno) from RIUWTask a where a.proposalgrpcontno='000000' and a.proposalcontno='");
			sql.append(mOpeData);
			sql.append("' and a.AuditType='01') ");
			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
			mRIUWTaskSchema = tRIUWTaskSet.get(1);
			mRIUWTaskSchema.setState("02");

			// 临分保单状态表
			RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
			tRIGrpStateDB
					.setProposalContNo(mRIUWTaskSchema.getProposalContNo());
			tRIGrpStateDB.setCaseNo("000000");
			tRIGrpStateDB.setExetype("01");
			RIGrpStateSet tRIGrpStateSet = tRIGrpStateDB.query();
			if (tRIGrpStateSet.size() == 0) {
				buildError("dealData", "没有得到任务状态");
				return false;
			}
			RIGrpStateSchema tRIGrpStateSchema = tRIGrpStateSet.get(1);
			tRIGrpStateSchema.setState("02");
			tRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
			tRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

			map.put(tRIGrpStateSchema, "UPDATE");
			map.put(mRIUWTaskSchema, "UPDATE");
		}
		if (mOpeFlag.equals("03")) {
		}
		if (mOpeFlag.equals("04")) { // 核赔 办结

			sql.append("select * from RIUWTASK b where b.serialno = (select max(a.serialno) from RIUWTask a where a.proposalgrpcontno = '000000' and a.auditcode = '");
			sql.append(mCaseNo);
			sql.append("' and a.AuditType = '04')");

			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
			mRIUWTaskSchema = tRIUWTaskSet.get(1);
			mRIUWTaskSchema.setState("02");

			// 再保临分保单状态表
			RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
			tRIGrpStateDB
					.setProposalContNo(mRIUWTaskSchema.getProposalContNo());
			tRIGrpStateDB.setCaseNo(mCaseNo);
			tRIGrpStateDB.setExetype("04");
			RIGrpStateSet tRIGrpStateSet = tRIGrpStateDB.query();
			if (tRIGrpStateSet.size() == 0) {
				buildError("dealData", "没有得到任务状态");
				return false;
			}
			RIGrpStateSchema tRIGrpStateSchema = tRIGrpStateSet.get(1);
			tRIGrpStateSchema.setState("02");
			tRIGrpStateSchema.setModifyDate(PubFun.getCurrentDate());
			tRIGrpStateSchema.setModifyTime(PubFun.getCurrentTime());

			map.put(tRIGrpStateSchema, "UPDATE");
			map.put(mRIUWTaskSchema, "UPDATE");

		}
		if (mOpeFlag.equals("05")) {
			sql.append("select * from RIUWTask where ProposalGrpContNo = '");
			sql.append(mOpeData);
			sql.append("' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditAffixCode='000000' and AuditType='05' order by uwno desc");
			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
			System.out.println(" bb: " + tRIUWTaskSet.size());
			if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
				buildError("", "没有此审核记录");
				return false;
			} else {
				uwno = tRIUWTaskSet.size();
			}
			tRIUWTaskDB.setProposalGrpContNo(mOpeData);
			tRIUWTaskDB.setProposalContNo("000000");
			tRIUWTaskDB.setAuditCode("000000");
			tRIUWTaskDB.setAuditAffixCode("000000");
			tRIUWTaskDB.setUWNo(uwno);

			mRIUWTaskSchema = tRIUWTaskDB.getSchema();
			mRIUWTaskSchema.setState("02");
		}
		if (mOpeFlag.equals("06")) {
		}
		return true;
	}

	public String getResult() {
		return this.mSerialNo;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		this.mResult.add(map);
		return true;
	}

	/**
	 * 出错处理
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "SendUWReInsureBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
