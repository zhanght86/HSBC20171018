package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDSysTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统保全人工核保保单申请功能部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class PRnewUWManuApplyChkBL {
private static Logger logger = Logger.getLogger(PRnewUWManuApplyChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mIsueManageCom;
	private String mManageCom;
	private String mflag = ""; // 分，个单标记

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";
	private String mOperatePos = "";
	/** 轨迹锁表* */
	private LDSysTraceSet mLDSysTraceSet = new LDSysTraceSet();
	private LDSysTraceSet mAllLDSysTraceSet = new LDSysTraceSet();

	public PRnewUWManuApplyChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// GlobalInput tGlobalInput = new GlobalInput();
		// this.mOperate = tGlobalInput.;

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---PRnewUWManuApplyChkBL getInputData---");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = mLCPolSet.get(1);
		mLCPolSchema = mLCPolSet.get(1);
		mOldPolNo = tLCPolSchema.getProposalNo();
		mPolNo = tLCPolSchema.getProposalNo();

		// 校验是不是已经有人申请此保单
		if (!checkApply()) {
			return false;
		}

		logger.debug("---PRnewUWManuApplyChkBL checkData---");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---PRnewUWManuApplyChkBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---PRnewUWManuApplyChkBL prepareOutputData---");
		// 数据提交
		PRnewUWManuApplyChkBLS tPRnewUWManuApplyChkBLS = new PRnewUWManuApplyChkBLS();
		logger.debug("Start PRnewUWManuApplyChkBL Submit...");
		if (!tPRnewUWManuApplyChkBLS.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPRnewUWManuApplyChkBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "PRnewUWManuApplyChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---PRnewUWManuApplyChkBL commitData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (prepareApply() == false) {
			return false;
		}

		LDSysTraceSet tLDSysTraceSet = new LDSysTraceSet();
		tLDSysTraceSet.set(mLDSysTraceSet);
		mAllLDSysTraceSet.add(tLDSysTraceSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;

		mLCPolSet.set((LCPolSet) cInputData
				.getObjectByObjectName("LCPolSet", 0));

		int n = mLCPolSet.size();
		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (n > 0) {
			flag = 1;
		} else {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PRnewUWManuApplyChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入保单信息!";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 准备信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApply() {
		LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
		LDSysTraceDB tLDSysTraceDB = new LDSysTraceDB();
		LDSysTraceSet tLDSysTraceSet = new LDSysTraceSet();
		String tsql = "";

		tsql = "select * from ldsystrace where polno = '?mPolNo?' and operator <> '?mOperate?' and polstate = 2001"; // 保全人工核保
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("mPolNo", mPolNo);
		sqlbv.put("mOperate", mOperate);

		tLDSysTraceSet = tLDSysTraceDB.executeQuery(sqlbv);

		if (tLDSysTraceSet.size() > 0) {
			tLDSysTraceSchema = tLDSysTraceSet.get(1);

			CError tError = new CError();
			tError.moduleName = "PRnewUWManuApplyChkBL";
			tError.functionName = "checkApply";
			tError.errorMessage = "此保单已经被"
					+ tLDSysTraceSchema.getOperator().trim() + "核保师申请，申请失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareApply() {
		LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();

		tLDSysTraceSchema.setCreatePos("续保人工核保");
		tLDSysTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tLDSysTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setModifyTime(PubFun.getCurrentTime());
		tLDSysTraceSchema.setManageCom(mManageCom);
		tLDSysTraceSchema.setOperator(mOperate);
		tLDSysTraceSchema.setOperator2(mOperate);
		tLDSysTraceSchema.setPolNo(mPolNo);
		tLDSysTraceSchema.setPolState(2001);
		tLDSysTraceSchema.setRemark("U");

		mLDSysTraceSet.add(tLDSysTraceSchema);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(mAllLDSysTraceSet);
	}
}
