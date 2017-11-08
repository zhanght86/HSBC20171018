package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LLAppealDB;
import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.db.LLCaseBackDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimUserDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.finfee.TempFeeBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LCContHangUpBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LLCaseBackSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LLAppealSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLCaseBackSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.vschema.LLClaimUserSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 案件回退处理类
 * </p>
 * 
 * <p>
 * Description: <案件回退信息更新提交信息类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 万泽辉 2005/11/17
 * @version 1.0
 */
public class LLCaseBackBL {
private static Logger logger = Logger.getLogger(LLCaseBackBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	// 全局变量
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mG = new GlobalInput();

	TransferData mTransferData = new TransferData();

	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

	private Reflections mReflections = new Reflections();

	// 理赔公用方法类
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private LLCaseBackSchema mLLCaseBackSchema = new LLCaseBackSchema();

	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();

	private String mClmNo = ""; // 理赔号

	private String mBackNo = ""; // 回退号

	private String mInsDate = ""; // 意外事故发生日期

	private String mExamDate = ""; // 审批通过日期

	private PubConcurrencyLock mLock = new PubConcurrencyLock();// 并发控制

	public LLCaseBackBL() {
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
		logger.debug("-------进行回退LLCaseBackBL申请处理---------开始-------");
		try {
			this.mInputData = (VData) cInputData.clone();
			this.mOperate = cOperate;

			if (!getInputData()) {
				return false;
			}

			// 获取全局变量
			if (!getGlobalVariable()) {
				return false;
			}

			// 检查数据合法性
			if (!checkData()) {
				return false;
			}

			// 业务处理
			if (!dealData()) {
				return false;
			}

			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}
			// 提交数据库
			if (!pubSubmit()) {
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;
		} finally {
			mLock.unLock();
		}
		logger.debug("---------进行回退LLCaseBackBL申请处理---------开始--------");

		return true;
	}

	private boolean getInputData() {
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mLLCaseBackSchema = (LLCaseBackSchema) mInputData.getObjectByObjectName("LLCaseBackSchema",
				0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 理赔号
		return true;
	}

	/**
	 * 获取全局变量
	 */
	private boolean getGlobalVariable() {
		this.mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo); // 出险时间
		this.mExamDate = mLLClaimPubFunBL.getExamDate(mClmNo);// 审批通过日期
		return true;
	}

	/**
	 * 对数据进行校验
	 */
	private boolean checkData() {
		/**
		 * No1.0 判断该赔案是否存在
		 */
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();
		if (tLLClaimDB.mErrors.needDealError() || tLLClaimSet.size() != 1) {
			CError.buildErr(this, "该赔案不存在理赔计算信息,不能执行回退操作!");
			return false;
		}
		mLLClaimSchema = tLLClaimSet.get(1);

		/**
		 * No1.1 对该赔案进行状态位的校验
		 */
		String tClmState = StrTool.cTrim(mLLClaimSchema.getClmState());
		if (!(tClmState.equals("60") || tClmState.equals("50"))) {
			CError.buildErr(this, "该赔案的赔案状态不在完成状态或签批确认状态,不能执行回退操作!");
			return false;
		}

		/**
		 * No1.2 对该赔案结论的校验
		 */
		String tGiveType = StrTool.cTrim(mLLClaimSchema.getGiveType());
		if (!tGiveType.equals("0") && !tGiveType.equals("1")) {
			CError.buildErr(this, "该赔案的赔付结论不是给付或拒付,不能执行回退操作!");
			return false;
		}

		/**
		 * No1.3 判断该赔案的登记信息是否存在
		 */
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(mClmNo);
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();
		if (tLLClaimDB.mErrors.needDealError() || tLLClaimSet.size() != 1) {
			CError.buildErr(this, "该赔案不存在立案信息,不能执行回退操作!");
			return false;
		}
		mLLRegisterSchema = tLLRegisterSet.get(1);

		/**
		 * No1.4 判断该赔案领取方式的校验
		 */
		String tGetMode = StrTool.cTrim(mLLRegisterSchema.getGetMode());
		if (tGetMode.equals("2")) {
			CError.buildErr(this, "该赔案的领取方式为按年金领取,不能执行回退操作!");
			return false;
		}

		/**
		 * No1.5 判断该赔案的是否是申述案件，如果是不允许回退
		 */
		LLAppealDB tLLAppealDB = new LLAppealDB();
		tLLAppealDB.setAppealNo(mClmNo);
		LLAppealSet tLLAppealSet = tLLAppealDB.query();
		if (tLLAppealDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询该赔案的申述信息失败,不能执行回退操作!");
			return false;
		}
		if (tLLAppealSet.size() > 0) {
			CError.buildErr(this, "该赔案为申述案件,不能执行回退操作!");
			return false;
		}

		/**
		 * No1.6 校验操作用户所在的机构 与赔案所在的机构 是否有上下级的关系
		 */
		if (!checkComCode()) {
			return false;
		}

		/**
		 * No1.7 判断该赔案的所涉及的合同在结案之后是否发生过保全变更
		 */
		// if (!checkPos()) {
		// return false;
		// }
		return true;
	}

	/**
	 * 校验操作用户所在的机构 与 赔案所在的机构 是否有上下级的关系
	 */
	private boolean checkComCode() {

		/**
		 * No1.0 找出登录用户所在的机构
		 */
		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(mG.Operator);
		LLClaimUserSet tLLClaimUserSet = tLLClaimUserDB.query();
		if (tLLClaimUserDB.mErrors.needDealError() || tLLClaimUserSet.size() != 1) {
			CError.buildErr(this, "登录用户:[" + mG.Operator + "]在理赔用户信息中没有定义，不能执行回退操作!");
			return false;
		}

		/**
		 * No2.0 找出赔案的审批机构
		 */
		LLClaimUWMainSchema tLLClaimUWMainSchema = mLLClaimPubFunBL.getLLClaimUWMain(mClmNo);
		if (tLLClaimUWMainSchema == null) {
			CError.buildErr(this, "查询不到案件的审核,审批结论");
			return false;
		}

		/**
		 * No3.1 进行机构比较处理
		 */
		String tExamCom = StrTool.cTrim(tLLClaimUWMainSchema.getExamCom()); // 赔案审批所在机构
		String tComCode = StrTool.cTrim(tLLClaimUserSet.get(1).getComCode());// 登录用户所在机构
		String tCompCom = "";

		/**
		 * No3.1 进行机构比较处理
		 */
		if (tExamCom.length() < tComCode.length()) {
			CError.buildErr(this, "登录用户[" + mG.Operator + "]所在机构为[" + tComCode + "],赔案审批所在的机构为["
					+ tExamCom + "]，机构之间不存在直属关系，不能执行回退操作!");
			return false;
		}

		/**
		 * No3.2 进行机构比较处理
		 */
		if (tExamCom.length() > tComCode.length()) {
			tCompCom = tExamCom.substring(0, tComCode.length());
			if (!tComCode.equals(tCompCom)) {
				CError.buildErr(this, "登录用户[" + mG.Operator + "]所在机构为[" + tComCode
						+ "],赔案审批所在的机构为[" + tExamCom + "]，机构之间不存在直属关系，不能执行回退操作!");
				return false;
			}
		}

		if (tExamCom.length() == tComCode.length()) {
			if (!tExamCom.equals(tComCode)) {
				CError.buildErr(this, "登录用户[" + mG.Operator + "]所在机构为[" + tComCode
						+ "],赔案审批所在的机构为[" + tExamCom + "]，机构之间不存在直属关系，不能执行回退操作!");
				return false;
			}
		}

		return true;
	}

	/**
	 * 校验结案日期后的保全操作
	 */
	private boolean checkPos() {
		/**
		 * No1.0 循环每个保项所在的合同，在结案日期后的保全操作
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(mClmNo);
		mLLClaimDetailSet = tLLClaimDetailDB.query();
		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);
			String tContNo = StrTool.cTrim(tLLClaimDetailSchema.getContNo());
			String tPolNo = StrTool.cTrim(tLLClaimDetailSchema.getPolNo());
			String tGiveType = StrTool.cTrim(tLLClaimDetailSchema.getGiveType());
			if (tGiveType.equals("2")) {
				continue;
			}

			/**
			 * No2.1 需要校验的保全项目,曾军2006\05\11提供的列表
			 */
			LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemAfter(tContNo,
					tPolNo, mExamDate, null);// 得到保全的批单号
			if (tLPEdorItemSchema == null) {
				continue;
			}
			String tEdorType = tLPEdorItemSchema.getEdorType();

			if (tEdorType.equals("AA") || // 附加险加保
					tEdorType.equals("AG") || // 年金、满期金给付
					tEdorType.equals("AP") || // 保费自垫申请、终止
					tEdorType.equals("BD") || // 质押银行贷款清偿
					tEdorType.equals("BL") || // 保单质押银行贷款
					tEdorType.equals("CT") || // 解除合同
					tEdorType.equals("EA") || // 公司解约
					tEdorType.equals("LN") || // 保单质押贷款
					tEdorType.equals("PL") || // 保单挂失
					tEdorType.equals("PT") || // 减保
					tEdorType.equals("PU") || // 减额缴清
					tEdorType.equals("RB") || // 保全回退
					tEdorType.equals("RF") || // 贷款清偿
					tEdorType.equals("RG") || // 生存领取追回
					tEdorType.equals("RN") || // 重新出单
					tEdorType.equals("TA") || // 转养老金
					tEdorType.equals("TR") || // 保费自垫清偿
					tEdorType.equals("XT")) // 协议退保
			{
				CError.buildErr(this, "合同号[" + tContNo + "],在结案日期[" + mExamDate + "]之后发生过保全类型为["
						+ tEdorType + "]的变更，不能执行回退操作!");
				return false;
			}
		}
		return true;
	}

	private boolean dealData() {
		if (!dealLLCaseBack()) {
			return false;
		}

		return true;
	}

	/**
	 * 回退信息保存
	 */
	private boolean dealLLCaseBack() {
		logger.debug("-------进行回退信息保存----开始------");

		/**
		 * No1.0 查询是否有回退信息
		 */
		LLCaseBackDB tLLCaseBackDB = new LLCaseBackDB();
		tLLCaseBackDB.setClmNo(mClmNo);
		tLLCaseBackDB.setBackState("0");
		LLCaseBackSet tLLCaseBackSet = tLLCaseBackDB.query();
		if (tLLCaseBackDB.mErrors.needDealError()) {
			CError.buildErr(this, "回退号发生错误,不能进行回退,不能执行回退操作!");
			return false;
		}
		if (tLLCaseBackSet.size() > 0) {
			CError.buildErr(this, "已经申请的回退记录为[" + tLLCaseBackSet.size() + "],不能进行回退!");
			return false;
		}

		LLCaseBackDB mLLCaseBackDB = new LLCaseBackDB();
		mLLCaseBackDB.setClmNo(mClmNo);
		mLLCaseBackDB.setBackState("1");
		LLCaseBackSet mLLCaseBackSet = mLLCaseBackDB.query();
		if (mLLCaseBackDB.mErrors.needDealError()) {
			CError.buildErr(this, "回退号发生错误,不能进行回退,不能执行回退操作!");
			return false;
		}
		if (mLLCaseBackSet.size() > 0) {
			CError.buildErr(this, "已存在回退的记录,不能再次回退，请核实!");
			return false;
		}
		/**
		 * No2.0 保存回退申请的信息
		 */
		LLCaseBackSchema tLLCaseBackSchema = new LLCaseBackSchema();
		if (tLLCaseBackSet.size() == 1) {
			tLLCaseBackSchema = tLLCaseBackSet.get(1);
			tLLCaseBackSchema.setBackState("0"); // 回退状态
		} else {
			/**
			 * No2.1 生成财务数据：根据实付信息生成应收信息；如果实付未核销，需要核销实付信息，同时生成暂收信息，收费方式为内部转账
			 */
			if (mLLCaseBackSchema.getBackType().equals("0")) {// 已财务给付的案件回退
				if (!getDealFinance(mClmNo)) {
					return false;
				}
			}

			/**
			 * No2.2 挂起保单
			 */
			if (!getLCContStateHangUp(mClmNo)) {
				return false;
			}

			/**
			 * No2.3 生成回退号
			 */
			mBackNo = 7 + PubFun1.CreateMaxNo("BackNo", "10"); // 生成修改顺序号
			logger.debug("－－－－系统生成的回退号为－－－－－:[" + mBackNo + "]");

			mTransferData.setNameAndValue("BackNo", mBackNo);

			tLLCaseBackSchema.setBackNo(mBackNo); // 回退号
			tLLCaseBackSchema.setClmNo(mClmNo); // 赔案号
			tLLCaseBackSchema.setBackState("0"); // 回退状态
			tLLCaseBackSchema.setBackDate(this.CurrentDate);
			tLLCaseBackSchema.setMakeDate(this.CurrentDate);
			tLLCaseBackSchema.setMakeTime(this.CurrentTime);

			/**
			 * No2.4 提取原审批机构等信息
			 */
			LLClaimUWMainSchema tLLClaimUWMainSchema = mLLClaimPubFunBL.getLLClaimUWMain(mClmNo);
			if (tLLClaimUWMainSchema == null) {
				mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}
			tLLCaseBackSchema.setOriExamCom(tLLClaimUWMainSchema.getExamCom());// 原审批机构
			tLLCaseBackSchema.setOriGiveType(tLLClaimUWMainSchema.getAuditConclusion());// 原理赔结论
			tLLCaseBackSchema.setApplyDate(this.CurrentDate); // 申请日期
			tLLCaseBackSchema.setApplyTime(this.CurrentTime); // 申请时间
		}
		tLLCaseBackSchema.setOperator(mG.Operator);
		tLLCaseBackSchema.setMngCom(mG.ManageCom);
		tLLCaseBackSchema.setModifyDate(this.CurrentDate);
		tLLCaseBackSchema.setModifyTime(this.CurrentTime);

		tLLCaseBackSchema.setApplyUser(mLLCaseBackSchema.getApplyUser()); // 申请人或签报号
		tLLCaseBackSchema.setBackReason(mLLCaseBackSchema.getBackReason()); // 回退原因
		tLLCaseBackSchema.setBackDesc(mLLCaseBackSchema.getBackDesc()); // 回退描述
		tLLCaseBackSchema.setNewGiveType(mLLCaseBackSchema.getNewGiveType()); // 新理赔结论
		tLLCaseBackSchema.setRemark(mLLCaseBackSchema.getRemark()); // 备注
		tLLCaseBackSchema.setBackType(mLLCaseBackSchema.getBackType());// 回退类型
		tLLCaseBackSchema.setCheckBackPreFlag(mLLCaseBackSchema.getCheckBackPreFlag());// 是否同时回退预付信息标志

		mMMap.put(tLLCaseBackSchema, "DELETE&INSERT");

		logger.debug("--------进行回退信息保存----结束-------");
		return true;
	}

	/**
	 * 对保单进行挂起
	 * 
	 * @return
	 */
	private boolean getLCContStateHangUp(String pClmNo) {
		/**
		 * No1.0 查询该赔案涉及的所有合同号
		 */
		String tSql = "select distinct ContNo from  LLClaimPolicy where 1=1 " + " and ClmNo='"
				+ "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ClmNo", pClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询赔案涉及的合同号发生错误!!!");
			return false;
		}
		logger.debug("--查询赔案涉及的合同号数量[" + tSSRS.getMaxRow() + "]");

		/**
		 * No3.0 对查询出来的合同号进行判断，是否被挂起
		 */
		LCContHangUpStateSet tLCContHangUpStateSaveSet = new LCContHangUpStateSet();
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String tContNo = tSSRS.GetText(i, 1); // 合同号

			/**
			 * No6.0 判断该合同是否被挂起
			 */
			LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, mClmNo, "4", tContNo);
			if (!tLCContHangUpBL.queryHungUpForContNo()) {
				CError.buildErr(this, "查询合同是否挂起信息失败," + tLCContHangUpBL.mErrors.getLastError());
				return false;
			}

			/**
			 * No7.0 挂起合同处理
			 */
			tLCContHangUpBL.setPosFlag("1");
			tLCContHangUpBL.setRnFlag("1");
			tLCContHangUpBL.setClmFlag("1");// 2009-01-09
			// zhangzheng，挂起时也必须同时挂起理赔,即不能同时对同一个合同进行交叉理赔
			LCContHangUpStateSchema tLCContHangUpStateSchema = tLCContHangUpBL.getHungUp();
			tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);
		}
		mMMap.put(tLCContHangUpStateSaveSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 进行财务回退处理 循环实付数据，将实付总表转成应收总表,实付明细表的信息冲成负记录,当需要回退预付信息时也需要回滚预付信息
	 * 
	 */
	private boolean getDealFinance(String pClmNo) {
		logger.debug("------------进行财务回退处理----开始-----------");

		/**
		 * No1.0 查询实付数据
		 */
		String tSql = "";
		if (!(mLLCaseBackSchema.getCheckBackPreFlag() == null || mLLCaseBackSchema
				.getCheckBackPreFlag().equals(""))) {
			if (mLLCaseBackSchema.getCheckBackPreFlag().equals("1")) {// 包含预付,则回退全系统的信息
				tSql = "select * from LJAGet a where 1=1 and OtherNo = '" + "?OtherNo?" + "'"
						+ " and OtherNoType = '5' and Sumgetmoney > 0 "
						+ " and not exists(select 1 from ljtempfee where otherno=a.otherno and tempfeenotype='2')";//增加对多次回滚的处理
			} else {// 不回退预付信息,只回退二次赔付信息
				tSql = "select * from LJAGet b where 1=1 and OtherNo = '"
						+ "?OtherNo?"
						+ "' and OtherNoType = '5' and Sumgetmoney > 0 "
						+ "and exists (select 1 from ljagetclaim a where a.ActuGetNo=b.ActuGetNo and feeoperationtype!='B')"
						+ " and not exists(select 1 from ljtempfee where otherno=b.otherno and tempfeenotype='2')";
			}
		} else {// 默认不回退预付信息,只回退二次赔付信息
			tSql = "select * from LJAGet b  where 1=1 and OtherNo = '"
					+ "?OtherNo?"
					+ "' and OtherNoType = '5' and Sumgetmoney > 0 "
					+ "and exists (select 1 from ljagetclaim a where a.ActuGetNo=b.ActuGetNo and feeoperationtype!='B')"
					+ " and not exists(select 1 from ljtempfee where otherno=b.otherno and tempfeenotype='2')";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("OtherNo", pClmNo);
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = tLJAGetDB.executeQuery(sqlbv1);
		if (tLJAGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "回退时，发生错误!");
			return false;
		}
		logger.debug("--准备生成应收的实付汇总记录数[" + tLJAGetSet.size() + "]：" + tSql);

		/**
		 * No2.0循环实付数据，将实付总表转成应收总表
		 */
		LJSPaySet tLJSPaySaveSet = new LJSPaySet();
		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJFIGetSet tLJFIGetSet = new LJFIGetSet();
		for (int i = 1; i <= tLJAGetSet.size(); i++) {
			LJAGetSchema tLJAGetSchema = tLJAGetSet.get(i);

			if (!mLock.lock(tLJAGetSchema.getActuGetNo(), "LF0003", mG.Operator)) {
				CError tError = new CError(mLock.mErrors.getLastError());
				CError.buildErr(this, "该号码处于业务其他操作中，请稍后再试！");
				this.mErrors.addOneError(tError);
				return false;
			}

			//判断是否银行在途
			if("1".equals(tLJAGetSchema.getBankOnTheWayFlag()))
			{
				CError.buildErr(this, "该案件的实付处于银行在途状态，不允许理赔回滚，请核实!");
				return false;
			}
			/**
			 * No2.1 进行判断，该实付总表的实付号是否在 LLBnfGather 中，如果不在，不进行财务回退
			 */
			// LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
			// tLLBnfGatherDB.setClmNo(pClmNo);
			// tLLBnfGatherDB.setOtherNo(tLJAGetSchema.getActuGetNo());// 实付号码
			// tLLBnfGatherDB.setBnfNo(tLJAGetSchema.getSerialNo());
			// tLLBnfGatherDB.setInsuredNo(tLJAGetSchema.getInsuredNo());
			// LLBnfGatherSet tLLBnfGatherSet = tLLBnfGatherDB.query();
			// if (tLLBnfGatherDB.mErrors.needDealError() ||
			// tLLBnfGatherSet.size() == 0) {
			// continue;
			// }
			// logger.debug("实付号:[" + tLJAGetSchema.getActuGetNo() +
			// "]的受益人汇总记录数为["
			// + tLLBnfGatherSet.size() + "]");
			/**
			 * No2.3 生成应收总表数据
			 */
			String tPayNo = PubFun1.CreateMaxNo("PAYNOTICENO", PubFun.getNoLimit(tLJAGetSchema
					.getManageCom()));// 生成应收号
//			String tGetNo = PubFun1.CreateMaxNo("GETNO", PubFun.getNoLimit(tLJAGetSchema
//					.getManageCom())); // 生成实付号
			logger.debug("----新生成的应收号:[" + tPayNo + "]");
//			logger.debug("----新生成的实付号:[" + tGetNo + "]");

			LJSPaySchema tLJSPaySchema = new LJSPaySchema(); // 建立应收汇总数据
			tLJSPaySchema.setGetNoticeNo(tPayNo); // 应收通知书号码
			tLJSPaySchema.setOtherNo(tLJAGetSchema.getOtherNo()); // 赔案号
			tLJSPaySchema.setOtherNoType("5"); //
			tLJSPaySchema.setAppntNo(tLJAGetSchema.getAppntNo()); // 投保人客户号码
			tLJSPaySchema.setSumDuePayMoney(tLJAGetSchema.getSumGetMoney());// 总实交金额
			tLJSPaySchema.setCurrency(tLJAGetSchema.getCurrency());
			tLJSPaySchema.setPayDate(""); // 交费日期
			tLJSPaySchema.setSerialNo(tLJAGetSchema.getActuGetNo()); // 实付数据的实付号码

			tLJSPaySchema.setManageCom(tLJAGetSchema.getManageCom());
			tLJSPaySchema.setAgentCom(tLJAGetSchema.getAgentCom()); // 管理机构
			tLJSPaySchema.setAgentType(tLJAGetSchema.getAgentType()); // 代理机构内部分类
			tLJSPaySchema.setBankCode(tLJAGetSchema.getBankCode()); // 银行编码
			tLJSPaySchema.setBankAccNo(tLJAGetSchema.getBankAccNo()); // 银行帐号

			tLJSPaySchema.setRiskCode(""); // 险种编码
			tLJSPaySchema.setAgentCode(tLJAGetSchema.getAgentCode()); // 代理人编码
			tLJSPaySchema.setAgentGroup(tLJAGetSchema.getAgentGroup()); // 代理人组别
			tLJSPaySchema.setAccName(tLJAGetSchema.getAccName()); // 帐户名

			tLJSPaySchema.setIDNo(tLJAGetSchema.getDrawerID());// 证件号码
			tLJSPaySchema.setIDType(tLJAGetSchema.getDrawerType());// 证件类型
			
			tLJSPaySchema.setTax(tLJAGetSchema.getTax());
			tLJSPaySchema.setTaxAmount(tLJAGetSchema.getTaxAmount());
			tLJSPaySchema.setNetAmount(tLJAGetSchema.getNetAmount());

			tLJSPaySchema.setStartPayDate(""); // 最早收费日期
			tLJSPaySchema.setPayTypeFlag(""); // 续保收费标记

			tLJSPaySchema.setOperator(mG.Operator);
			tLJSPaySchema.setMakeDate(CurrentDate);
			tLJSPaySchema.setMakeTime(CurrentTime);
			tLJSPaySchema.setModifyDate(CurrentDate);
			tLJSPaySchema.setModifyTime(CurrentTime);
			

			tLJSPaySaveSet.add(tLJSPaySchema);

			// 实付未到账，核销实付信息，同时生成暂收信息，收费方式为内部转账
			if (tLJAGetSchema.getConfDate() == null || tLJAGetSchema.getConfDate().equals("")) {
				/**
				 * No2.4 核销实付信息
				 */
				tLJAGetSchema.setEnterAccDate(CurrentDate);
				tLJAGetSchema.setConfDate(CurrentDate);
				tLJAGetSchema.setPayMode("5");// 5-内部转帐
				tLJAGetSchema.setModifyDate(CurrentDate);
				tLJAGetSchema.setModifyTime(CurrentTime);

				/**
				 * No2.5 核销理赔实付信息
				 */
				tLJAGetClaimSet.add(this.updateLJAGetClaim(tLJAGetSchema));

				/**
				 * No2.6 生成财务给付表LJFIGet
				 */
				LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
				tLJFIGetSchema.setEnterAccDate(tLJAGetSchema.getEnterAccDate());
				tLJFIGetSchema.setConfDate(tLJAGetSchema.getConfDate());
				tLJFIGetSchema.setActuGetNo(tLJAGetSchema.getActuGetNo());
				tLJFIGetSchema.setPayMode("5"); // 5---内部转帐
				tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
				tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
				tLJFIGetSchema.setGetMoney(tLJAGetSchema.getSumGetMoney());
				tLJFIGetSchema.setCurrency(tLJAGetSchema.getCurrency());
				tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
				tLJFIGetSchema.setBankCode(tLJAGetSchema.getBankCode());
				tLJFIGetSchema.setBankAccNo(tLJAGetSchema.getBankAccNo());
				tLJFIGetSchema.setAccName(tLJAGetSchema.getAccName());
				tLJFIGetSchema.setInBankCode(tLJAGetSchema.getBankCode());
				tLJFIGetSchema.setInBankAccNo(tLJAGetSchema.getBankAccNo());
				tLJFIGetSchema.setInAccName(tLJAGetSchema.getAccName());
				tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
				tLJFIGetSchema.setSerialNo(tLJAGetSchema.getSerialNo());
				tLJFIGetSchema.setConfMakeDate(tLJAGetSchema.getConfDate());
				tLJFIGetSchema.setManageCom(tLJAGetSchema.getManageCom());
				tLJFIGetSchema.setPolicyCom(tLJAGetSchema.getPolicyCom());
				tLJFIGetSchema.setAgentCom(tLJAGetSchema.getAgentCom());
				tLJFIGetSchema.setAgentType(tLJAGetSchema.getAgentType());
				tLJFIGetSchema.setAgentGroup(tLJAGetSchema.getAgentGroup());
				tLJFIGetSchema.setAgentCode(tLJAGetSchema.getAgentCode());
				tLJFIGetSchema.setOperator(tLJAGetSchema.getOperator());
				tLJFIGetSchema.setConfMakeTime(tLJAGetSchema.getMakeTime());
				tLJFIGetSchema.setDrawer(tLJAGetSchema.getDrawer());
				tLJFIGetSchema.setDrawerID(tLJAGetSchema.getDrawerID());
				tLJFIGetSchema.setMakeDate(CurrentDate); // 入机日期
				tLJFIGetSchema.setMakeTime(CurrentTime); // 入机时间
				tLJFIGetSchema.setModifyDate(CurrentDate); // 最后一次修改日期
				tLJFIGetSchema.setModifyTime(CurrentTime); // 最后一次修改时间
				tLJFIGetSet.add(tLJFIGetSchema);

				/**
				 * No2.7 生成暂收数据
				 */
				LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
				LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tLJTempFeeClassSchema.setTempFeeNo(tPayNo);// 应收号
				tLJTempFeeClassSchema.setPayMode("5");// 5---内部转帐
				tLJTempFeeClassSchema.setChequeNo(tLJAGetSchema.getActuGetNo());// 实付号
				tLJTempFeeClassSchema.setPayMoney(tLJAGetSchema.getSumGetMoney());
				tLJTempFeeClassSchema.setCurrency(tLJAGetSchema.getCurrency());
				tLJTempFeeClassSchema.setPayDate(CurrentDate);
				tLJTempFeeClassSchema.setChequeDate(CurrentDate);
				tLJTempFeeClassSchema.setEnterAccDate(CurrentDate);// 到帐日期
				tLJTempFeeClassSchema.setManageCom(tLJAGetSchema.getManageCom());
				tLJTempFeeClassSchema.setPolicyCom(tLJAGetSchema.getManageCom());
				tLJTempFeeClassSchema.setBankCode(tLJAGetSchema.getBankCode());
				tLJTempFeeClassSchema.setBankAccNo(tLJAGetSchema.getBankAccNo());
				tLJTempFeeClassSchema.setAccName(tLJAGetSchema.getAccName());
				tLJTempFeeClassSchema.setIDType(tLJAGetSchema.getDrawerType());
				tLJTempFeeClassSchema.setIDNo(tLJAGetSchema.getDrawerType());
				tLJTempFeeClassSchema.setOperator("0001");
				tLJTempFeeClassSchema.setOperState("0");
				tLJTempFeeClassSet.add(tLJTempFeeClassSchema);

				LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema.setTempFeeNo(tPayNo);// 应收号
				tLJTempFeeSchema.setTempFeeType("6");// 6--理赔收费
				tLJTempFeeSchema.setRiskCode("000000");// 保单级收费
				tLJTempFeeSchema.setTempFeeNoType("2");// 2-理赔回滚
				tLJTempFeeSchema.setOtherNoType("2");// 理赔赔案号
				tLJTempFeeSchema.setAgentGroup(tLJAGetSchema.getAgentGroup());
				tLJTempFeeSchema.setAgentCode(tLJAGetSchema.getAgentCode());
				tLJTempFeeSchema.setPayMoney(tLJAGetSchema.getSumGetMoney());
				tLJTempFeeSchema.setCurrency(tLJAGetSchema.getCurrency());
				tLJTempFeeSchema.setManageCom(tLJAGetSchema.getManageCom());
				tLJTempFeeSchema.setPolicyCom(tLJAGetSchema.getManageCom());
				tLJTempFeeSchema.setOtherNo(tLJAGetSchema.getOtherNo());// 理赔赔案号
				tLJTempFeeSchema.setPayDate(CurrentDate);// 交费日期
				tLJTempFeeSchema.setEnterAccDate(CurrentDate);// 到帐日期
				tLJTempFeeSchema.setOperator("0001");
				tLJTempFeeSchema.setPayIntv("0");// 0 -- 趸交
				tLJTempFeeSchema.setPayYears("");
				tLJTempFeeSchema.setOperState("0"); // 配合团单签单，正常数据为0
				tLJTempFeeSchema.setSerialNo(""); // 续期收据号
				tLJTempFeeSchema.setState("");
				tLJTempFeeSchema.setRemark("");// 续期收据号 zy 2009-06-29

				if (tLJTempFeeSchema.getAgentCode() == null
						|| tLJTempFeeSchema.getAgentCode().equals("")) {
					String sql_lccont = "select * from lccont a where a.contno in "
							+ "(select contno from ljagetclaim b where b.actugetno = '"
							+ "?actugetno?" + "')";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(sql_lccont);
					sqlbv2.put("actugetno", tLJAGetSchema.getActuGetNo());
					LCContDB tLCContDB = new LCContDB();
					LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv2);
					if (tLCContSet.size() >= 1 && tLCContSet.get(1).getAgentCode() != null) {
						tLJTempFeeSchema.setAgentCode(tLCContSet.get(1).getAgentCode());
					}
				}
				tLJTempFeeSet.add(tLJTempFeeSchema);

				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("SubmitFlag", "CaseBack");// 是否提交数据库的标识

				VData tVData = new VData();
				tVData.addElement(tLJTempFeeSet);
				tVData.addElement(tLJTempFeeClassSet);
				tVData.addElement(tTransferData);
				tVData.addElement(mG);

				TempFeeBL tTempFeeBL = new TempFeeBL();
				if (!tTempFeeBL.submitData(tVData, "INSERT")) {
					CError.buildErr(this, tTempFeeBL.mErrors.getFirstError());
					CError.buildErr(this, "自动生成暂收信息失败!");
					return false;
				} else {
					VData mVData = tTempFeeBL.getResult();
					MMap tmpMMap = (MMap) mVData.getObjectByObjectName("MMap", 0);
					// tmpMMap.

					mMMap.add((MMap) mVData.getObjectByObjectName("MMap", 0));
				}
			}
		}

		mMMap.put(tLJSPaySaveSet, "INSERT");
		mMMap.put(tLJAGetSet, "UPDATE");
		mMMap.put(tLJAGetClaimSet, "UPDATE");
		mMMap.put(tLJFIGetSet, "INSERT");

		logger.debug("---------进行财务回退处理----结束---------");
		return true;
	}

	/**
	 * 赔付实付表
	 */
	private LJAGetClaimSet updateLJAGetClaim(LJAGetSchema tLJAGetSchema) {
		if (tLJAGetSchema == null || tLJAGetSchema.getActuGetNo() == null) {
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJAGetClaim where ActuGetNo='"
				+ "?ActuGetNo?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
		logger.debug("赔付实付明细表:" + sqlStr);
		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv3);
		if (tLJAGetClaimDB.mErrors.needDealError() == true) {
			this.mErrors.copyAllErrors(tLJAGetClaimDB.mErrors);
			CError.buildErr(this, "赔付实付表查询失败!");
			return null;
		}
		if (tLJAGetClaimSet.size() == 0) {
			CError.buildErr(this, "没有可以更新的赔付实付表！");
			return null;
		}

		for (int n = 1; n <= tLJAGetClaimSet.size(); n++) {
			tLJAGetClaimSet.get(n).setConfDate(tLJAGetSchema.getConfDate());
			tLJAGetClaimSet.get(n).setEnterAccDate(tLJAGetSchema.getEnterAccDate());
			tLJAGetClaimSet.get(n).setModifyDate(CurrentDate);
			tLJAGetClaimSet.get(n).setModifyTime(CurrentTime);
		}

		return tLJAGetClaimSet;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mMMap);

			//mResult.add(mMMap);
			mResult.add(mTransferData);
		} catch (Exception ex) {
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
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
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		return true;

	}

	public static void main(String[] args) {
		LLCaseBackSchema tLLCaseBackSchema = new LLCaseBackSchema();

		GlobalInput tG = new GlobalInput();
		tG.Operator = "kangzheng";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		String tClmNo = "90000021248"; /* 赔案号 */

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tLLCaseBackSchema.setBackDesc("大江东去,黄河溪流");
		tLLCaseBackSchema.setClmNo(tClmNo);

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		tVData.addElement(tLLCaseBackSchema);

		LLCaseBackBL tLLCaseBackBL = new LLCaseBackBL();
		tLLCaseBackBL.submitData(tVData, "");

		int n = tLLCaseBackBL.mErrors.getErrorCount();
		logger.debug("----------------------------------------------------");
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: " + tLLCaseBackBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
		logger.debug("-----------------------------------------------------");
	}
}
