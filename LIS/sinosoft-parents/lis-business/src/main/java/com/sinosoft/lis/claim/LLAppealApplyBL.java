/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLCaseRelaDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimDutyKindDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LLReportRelaDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAppealSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCaseRelaSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.vschema.LLClaimUWMainSet;
import com.sinosoft.lis.vschema.LLReportRelaSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 申请申诉业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */

public class LLAppealApplyBL {
private static Logger logger = Logger.getLogger(LLAppealApplyBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();
	private TransferData mTransferData = new TransferData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
	private Reflections ref = new Reflections();

	private GlobalInput mG = new GlobalInput();
	private String mClmNo = "";
	private String mRptNo = "";

	public LLAppealApplyBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLAppealApplyBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClaimNo");

		if (mClmNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的赔案号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("---start dealData()");
		mRptNo = PubFun1.CreateMaxNo("RPTONLYNO", 10); // 生成赔案号流水号
		mRptNo = "9" + mRptNo;
		logger.debug("-----生成赔案号= " + mRptNo);

		// 新建理赔申诉错误表
		LLAppealSchema tLLAppealSchema = new LLAppealSchema();
		tLLAppealSchema.setAppealNo(mRptNo);
		tLLAppealSchema.setCaseNo(mClmNo);
		tLLAppealSchema.setAppealState("0"); // 申诉状态
		tLLAppealSchema.setAppealType("1"); // 申诉/错误类型
		tLLAppealSchema.setOperator(mG.Operator);
		tLLAppealSchema.setMakeDate(CurrentDate);
		tLLAppealSchema.setMakeTime(CurrentTime);
		tLLAppealSchema.setModifyDate(CurrentDate);
		tLLAppealSchema.setModifyTime(CurrentTime);
		tLLAppealSchema.setManageCom(mG.ManageCom);

		map.put(tLLAppealSchema, "DELETE&INSERT");

		// 复制分案事件关联
		LLCaseRelaSet tLLCaseRelaSet = new LLCaseRelaSet();
		LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
		tLLCaseRelaDB.setCaseNo(mClmNo);
		tLLCaseRelaSet = tLLCaseRelaDB.query();
		if (tLLCaseRelaSet == null || tLLCaseRelaSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询分案事件关联信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLCaseRelaSet.size(); i++) {
			tLLCaseRelaSet.get(i).setCaseNo(mRptNo);
			tLLCaseRelaSet.get(i).setSubRptNo(mRptNo);
		}

		map.put(tLLCaseRelaSet, "DELETE&INSERT");

		// 复制报案事件关联表
		LLReportRelaSet tLLReportRelaSet = new LLReportRelaSet();
		LLReportRelaDB tLLReportRelaDB = new LLReportRelaDB();
		tLLReportRelaDB.setRptNo(mClmNo);
		tLLReportRelaSet = tLLReportRelaDB.query();
		if (tLLReportRelaSet == null || tLLReportRelaSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询报案事件关联表信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLReportRelaSet.size(); i++) {
			tLLReportRelaSet.get(i).setRptNo(mRptNo);
		}

		map.put(tLLReportRelaSet, "DELETE&INSERT");

		// 复制立案表
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(mClmNo);
		tLLRegisterDB.getInfo();
		tLLRegisterSchema = tLLRegisterDB.getSchema();
		tLLRegisterSchema.setRgtNo(mRptNo);
		tLLRegisterSchema.setRgtState("13"); // 申诉案件
		tLLRegisterSchema.setClmState("40"); // 案件置为审批状态

		map.put(tLLRegisterSchema, "DELETE&INSERT");

		// 复制分案表
		LLCaseSet tLLCaseSet = new LLCaseSet();
		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(mClmNo);
		tLLCaseSet = tLLCaseDB.query();
		if (tLLCaseSet == null || tLLCaseSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询分案信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLCaseSet.size(); i++) {
			tLLCaseSet.get(i).setCaseNo(mRptNo);
			tLLCaseSet.get(i).setRgtNo(mRptNo);
			tLLCaseSet.get(i).setRgtType("13"); // 申诉案件
		}

		map.put(tLLCaseSet, "DELETE&INSERT");

		// 复制立案理赔类型
		LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet();
		LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
		tLLAppClaimReasonDB.setCaseNo(mClmNo);
		tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
		if (tLLAppClaimReasonSet == null || tLLAppClaimReasonSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询立案理赔类型失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLAppClaimReasonSet.size(); i++) {
			tLLAppClaimReasonSet.get(i).setCaseNo(mRptNo);
			tLLAppClaimReasonSet.get(i).setRgtNo(mRptNo);
		}

		map.put(tLLAppClaimReasonSet, "DELETE&INSERT");

		// 复制赔案
		LLClaimSet tLLClaimSet = new LLClaimSet();
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		tLLClaimSet = tLLClaimDB.query();
		if (tLLClaimSet == null || tLLClaimSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔案信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLClaimSet.size(); i++) {
			tLLClaimSet.get(i).setClmNo(mRptNo);
			tLLClaimSet.get(i).setCaseNo(mRptNo);
			tLLClaimSet.get(i).setRgtNo(mRptNo);
			tLLClaimSet.get(i).setClmState("40"); // 设置为审批状态
		}

		map.put(tLLClaimSet, "DELETE&INSERT");

		// 复制赔案理赔类型
		LLClaimDutyKindSet tLLClaimDutyKindSet = new LLClaimDutyKindSet();
		LLClaimDutyKindDB tLLClaimDutyKindDB = new LLClaimDutyKindDB();
		tLLClaimDutyKindDB.setClmNo(mClmNo);
		tLLClaimDutyKindSet = tLLClaimDutyKindDB.query();
		if (tLLClaimDutyKindSet == null || tLLClaimDutyKindSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔案保单名细信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLClaimDutyKindSet.size(); i++) {
			tLLClaimDutyKindSet.get(i).setClmNo(mRptNo);
		}

		map.put(tLLClaimDutyKindSet, "DELETE&INSERT");

		// 复制赔案保单名细
		LLClaimPolicySet tLLClaimPolicySet = new LLClaimPolicySet();
		LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		tLLClaimPolicyDB.setClmNo(mClmNo);
		tLLClaimPolicySet = tLLClaimPolicyDB.query();
		if (tLLClaimPolicySet == null || tLLClaimPolicySet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔案保单名细信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLClaimPolicySet.size(); i++) {
			tLLClaimPolicySet.get(i).setClmNo(mRptNo);
			tLLClaimPolicySet.get(i).setCaseNo(mRptNo);
			tLLClaimPolicySet.get(i).setRgtNo(mRptNo);
			tLLClaimPolicySet.get(i).setClmState("40"); // 设置为审批状态
		}

		map.put(tLLClaimPolicySet, "DELETE&INSERT");

		// 复制赔付明细
		LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(mClmNo);
		tLLClaimDetailSet = tLLClaimDetailDB.query();
		if (tLLClaimDetailSet == null || tLLClaimDetailSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔案保单名细信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			tLLClaimDetailSet.get(i).setClmNo(mRptNo);
			tLLClaimDetailSet.get(i).setRgtNo(mRptNo);
			tLLClaimDetailSet.get(i).setCaseNo(mRptNo);
		}

		map.put(tLLClaimDetailSet, "DELETE&INSERT");

		// 复制审核审批结论表
		LLClaimUWMainSet tLLClaimUWMainSet = new LLClaimUWMainSet();
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		tLLClaimUWMainSet = tLLClaimUWMainDB.query();
		if (tLLClaimUWMainSet == null || tLLClaimUWMainSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询审核审批结论信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLClaimUWMainSet.size(); i++) {
			tLLClaimUWMainSet.get(i).setClmNo(mRptNo);
			tLLClaimUWMainSet.get(i).setRgtNo(mRptNo);
			tLLClaimUWMainSet.get(i).setCaseNo(mRptNo);
		}

		map.put(tLLClaimUWMainSet, "DELETE&INSERT");
		// //复制责任费用统计
		// LLClaimDutyFeeSet tLLClaimDutyFeeSet = new LLClaimDutyFeeSet();
		// LLClaimDutyFeeDB tLLClaimDutyFeeDB = new LLClaimDutyFeeDB();
		// tLLClaimDutyFeeDB.setClmNo(mClmNo);
		// tLLClaimDutyFeeSet = tLLClaimDutyFeeDB.query();
		// if (tLLClaimDutyFeeSet == null || tLLClaimDutyFeeSet.size() == 0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LLAppealApplyBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "查询赔案保单名细信息失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// for (int i = 1; i <= tLLClaimDutyFeeSet.size(); i++)
		// {
		// tLLClaimDutyFeeSet.get(i).setClmNo(mRptNo);
		// tLLClaimDutyFeeSet.get(i).setCaseNo(mRptNo);
		// }
		//
		// map.put(tLLClaimDutyFeeSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);

			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("clmno", mRptNo);
			mResult.add(tTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
