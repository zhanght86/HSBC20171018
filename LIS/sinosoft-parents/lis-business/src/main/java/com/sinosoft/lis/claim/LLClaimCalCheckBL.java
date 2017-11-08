package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLCaseRelaDB;
import com.sinosoft.lis.db.LLGrpRegisterDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLAccidentSet;
import com.sinosoft.lis.vschema.LLCaseRelaSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 计算步骤一：理赔计算前的数据校验
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 * @modify by zhoulei 校验赔案状态,防止审核后的匹配理算操作
 */
public class LLClaimCalCheckBL {
private static Logger logger = Logger.getLogger(LLClaimCalCheckBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	private String mAccNo = ""; // 事件号
	private String mAccDate = ""; // 意外事故发生日期
	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号
	private String mContType = ""; // 总单类型,1-个人总投保单,2-集体总单

	public LLClaimCalCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理算步骤一-----数据校验-----LLClaimCalCheckBL测试-----开始----------");

		// Reflections rf = new Reflections();
		// rf.transFields(mLLClaimPolicySchema,LLCasePolicyDB.getSchema());

		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}


		logger.debug("----------理算步骤一-----数据校验-----LLClaimCalCheckBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mAccNo = (String) mTransferData.getValueByName("AccNo"); // 事件号
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mAccDate = (String) mTransferData.getValueByName("AccDate"); // 意外事故发生日期
		this.mContType = (String) mTransferData.getValueByName("ContType"); // 总单类型,1-个人总投保单,2-集体总单

		this.mContType = StrTool.cTrim(mContType);

		// logger.debug("this.mCusNo"+this.mCusNo);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 校验事件信息
		if (!getLLAccidentInfo()) {
			return false;
		}

		// 校验事件赔案关联信息
		if (!this.getLLCaseRelaInfo()) {
			return false;
		}

		// 校验立案信息
		if (!getLLRegisterInfo()) {
			return false;
		}

		// 校验立案分案信息
		if (!this.getLLCaseInfo()) {
			return false;
		}

		// 如果类型为团险，判断是否有团体的立案信息
		if (this.mContType.equals("2")) {
			if (!this.getLLGrpRegister()) {
				return false;
			}

		}

		return true;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－开始－－－－－－－进行数据校验－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 目的：找出该赔案对应的事件信息
	 * 
	 * @return
	 */
	private boolean getLLAccidentInfo() {

		LLAccidentDB tLLAccidentDB = new LLAccidentDB();
		String tSql = "select * from LLAccident where 1 = 1 "
				+ " and AccNo in "
				+ " (select AccNo from LLAccidentSub where AccNo = '"
				+ "?AccNo?" + "') " + " and AccDate = to_date('"
				+ "?AccDate?" + "','yyyy-mm-dd') ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("AccNo", this.mAccNo);
		sqlbv.put("AccDate", this.mAccDate);
		LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(sqlbv);
		if (tLLAccidentSet == null || tLLAccidentSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLAccidentDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "getLLAccidentInfo";
			tError.errorMessage = "在LLAccident表中没有查找到相关的事件信息，不能理算!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;

	}

	/**
	 * 目的：找出该赔案对应的事件赔案关联信息
	 * 
	 * @return
	 */
	private boolean getLLCaseRelaInfo() {

		LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
		tLLCaseRelaDB.setCaseRelaNo(this.mAccNo); // 事件号
		tLLCaseRelaDB.setCaseNo(this.mClmNo); // 赔案号
		LLCaseRelaSet tLLCaseRelaSet = tLLCaseRelaDB.query();
		if (tLLCaseRelaSet == null || tLLCaseRelaSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLCaseRelaSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "getLLCaseRelaInfo";
			tError.errorMessage = "在LLCaseRela表中没有查找到事件赔案关联信息，不能理算!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;

	}

	/**
	 * 查找并判断是否有立案信息
	 * 
	 * @return
	 */
	private boolean getLLRegisterInfo() {
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(this.mClmNo);

		if (tLLRegisterDB.getInfo() == false) {
			mErrors.addOneError("没有查到赔案号：[" + this.mClmNo + "]的立案信息，不能匹配理算!!!"
					+ tLLRegisterDB.mErrors.getError(0).errorMessage);
			return false;
		}

		mLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
		String tClmState = mLLRegisterSchema.getClmState();

		// 校验赔案状态,防止审核后的匹配理算操作
		if (tClmState.endsWith("40")) {
			mErrors.addOneError("赔案已经流转到审批阶段,不允许重新匹配理算!");
			return false;
		} else if (tClmState.endsWith("50")) {
			mErrors.addOneError("赔案已经流转到结案阶段,不允许重新匹配理算!");
			return false;
		} else if (tClmState.endsWith("60")) {
			mErrors.addOneError("赔案已经处理完成,不允许重新匹配理算!");
			return false;
		} else if (tClmState.endsWith("70")) {
			mErrors.addOneError("赔案已经关闭,不允许重新匹配理算!");
			return false;
		}

		return true;
	}

	/**
	 * 判断立案分案的赔案状态，RgtState
	 * 
	 * @return
	 */
	private boolean getLLCaseInfo() {
		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(this.mClmNo);

		LLCaseSet tLLCaseDBSet = tLLCaseDB.query();
		if (tLLCaseDBSet == null || tLLCaseDBSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLCaseDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "getLLCaseInfo";
			tError.errorMessage = "在LLCaser表中没有找到立案分案信息，不能理算!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 判断是否有团体立案信息
	 * 
	 * @return
	 */
	private boolean getLLGrpRegister() {
		// 团体赔案号
		String tRgtObjNo = StrTool.cTrim(mLLRegisterSchema.getRgtObjNo());

		LLGrpRegisterDB tLLGrpRegisterDB = new LLGrpRegisterDB();
		tLLGrpRegisterDB.setRgtNo(tRgtObjNo);

		if (tLLGrpRegisterDB.getInfo() == false) {
			mErrors.addOneError("没有查到团体赔案号：[" + tRgtObjNo + "]的立案信息，不能匹配理算!!!"
					+ tLLGrpRegisterDB.mErrors.getError(0).errorMessage);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－结束－－－－－－－进行数据校验－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－以下程序为废弃不用或者没有进行整理－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

}
