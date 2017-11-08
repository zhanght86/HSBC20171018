/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.acc.LLClaimClearBackBL;
import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.db.LLBnfDB;
import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.db.LLCaseBackDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimDutyFeeDB;
import com.sinosoft.lis.db.LLClaimDutyKindDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLContStateDB;
import com.sinosoft.lis.db.LLDutyDB;
import com.sinosoft.lis.db.LLExemptDB;
import com.sinosoft.lis.db.LLGetDB;
import com.sinosoft.lis.db.LLGrpRegisterDB;
import com.sinosoft.lis.db.LLPrepayClaimDB;
import com.sinosoft.lis.db.LLPrepayDetailDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LLUWPremMasterDB;
import com.sinosoft.lis.db.LMDutyGetClmDB;
import com.sinosoft.lis.db.LOPolAfterDealDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLAppClaimReasonSchema;
import com.sinosoft.lis.schema.LLBAppClaimReasonSchema;
import com.sinosoft.lis.schema.LLBBalanceSchema;
import com.sinosoft.lis.schema.LLBBnfGatherSchema;
import com.sinosoft.lis.schema.LLBBnfSchema;
import com.sinosoft.lis.schema.LLBCaseSchema;
import com.sinosoft.lis.schema.LLBContStateSchema;
import com.sinosoft.lis.schema.LLBDutySchema;
import com.sinosoft.lis.schema.LLBExemptSchema;
import com.sinosoft.lis.schema.LLBGetSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.schema.LLCaseBackSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLClaimDutyKindSchema;
import com.sinosoft.lis.schema.LLClaimPolicySchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWDutyFeeSchema;
import com.sinosoft.lis.schema.LLClaimUWDutyKindSchema;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLClaimUWPolicySchema;
import com.sinosoft.lis.schema.LLContStateSchema;
import com.sinosoft.lis.schema.LLDutySchema;
import com.sinosoft.lis.schema.LLExemptSchema;
import com.sinosoft.lis.schema.LLGetSchema;
import com.sinosoft.lis.schema.LLGrpRegisterSchema;
import com.sinosoft.lis.schema.LLPrepayClaimSchema;
import com.sinosoft.lis.schema.LLPrepayDetailSchema;
import com.sinosoft.lis.schema.LLPrepayUWClaimSchema;
import com.sinosoft.lis.schema.LLPrepayUWDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLUWPremMasterSchema;
import com.sinosoft.lis.schema.LLUWPremSubSchema;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLBAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLBBalanceSet;
import com.sinosoft.lis.vschema.LLBBnfGatherSet;
import com.sinosoft.lis.vschema.LLBBnfSet;
import com.sinosoft.lis.vschema.LLBCaseSet;
import com.sinosoft.lis.vschema.LLBContStateSet;
import com.sinosoft.lis.vschema.LLBDutySet;
import com.sinosoft.lis.vschema.LLBExemptSet;
import com.sinosoft.lis.vschema.LLBGetSet;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLCaseBackSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.vschema.LLClaimUWDetailSet;
import com.sinosoft.lis.vschema.LLClaimUWDutyFeeSet;
import com.sinosoft.lis.vschema.LLClaimUWDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
import com.sinosoft.lis.vschema.LLClaimUWMainSet;
import com.sinosoft.lis.vschema.LLClaimUWPolicySet;
import com.sinosoft.lis.vschema.LLContStateSet;
import com.sinosoft.lis.vschema.LLDutySet;
import com.sinosoft.lis.vschema.LLExemptSet;
import com.sinosoft.lis.vschema.LLGetSet;
import com.sinosoft.lis.vschema.LLPrepayClaimSet;
import com.sinosoft.lis.vschema.LLPrepayDetailSet;
import com.sinosoft.lis.vschema.LLPrepayUWClaimSet;
import com.sinosoft.lis.vschema.LLPrepayUWDetailSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LLUWPremSubSet;
import com.sinosoft.lis.vschema.LMDutyGetClmSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPolAfterDealSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔审批通过回退处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛
 * @version 1.0
 */
public class LLClaimConfirmPassReturnBL {
private static Logger logger = Logger.getLogger(LLClaimConfirmPassReturnBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap mMMap = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	TransferData mTransferData = new TransferData();

	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private Reflections mReflections = new Reflections();

	// 理赔公用方法类
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private JdbcUrl mJdbcUrl = new JdbcUrl();

	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LLCaseBackSchema tLLCaseBackSchema=null;//案件回退信息

	private String mClmNo = ""; // 理赔号
	private String mBackNo = ""; // 回退号
	private String mInsDate = ""; // 意外事故发生日期
	private String mExamDate = ""; // 审批通过日期

	public LLClaimConfirmPassReturnBL() {
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
		logger.debug("-----------------------------LLClaimConfirmPassReturnBL----理赔回退----开始-----------------------------");

		mInputData = (VData) cInputData.clone();
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

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("-----------------------------LLClaimConfirmPassReturnBL----理赔回退----结束-----------------------------");

		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		// mLLCaseBackSchema =
		// (LLCaseBackSchema)mInputData.getObjectByObjectName("LLCaseBackSchema",
		// 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 理赔号
		mBackNo = (String) mTransferData.getValueByName("BackNo"); // 理赔号

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－理赔回退前数据校验－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

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
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该赔案是否存在
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();

		if (tLLClaimDB.mErrors.needDealError() || tLLClaimSet.size() != 1) {
			CError.buildErr(this, "该赔案不存在理赔计算信息，不能执行回退操作!");
			return false;
		}
		mLLClaimSchema = tLLClaimSet.get(1);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1 对该赔案进行状态位的校验
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tClmState = StrTool.cTrim(mLLClaimSchema.getClmState());
		if (!(tClmState.equals("60")||tClmState.equals("50"))) {
			CError.buildErr(this, "该赔案的赔案状态不在完成状态或签批确认状态，不能执行回退操作!");
			return false;
		}

		String tGiveType = StrTool.cTrim(mLLClaimSchema.getGiveType());
		if (tGiveType.equals("0") || tGiveType.equals("1")) {

		} else {
			CError.buildErr(this, "该赔案的赔付结论不是给付或拒付，不能执行回退操作!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 判断该赔案的登记信息是否存在
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(mClmNo);
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();

		if (tLLClaimDB.mErrors.needDealError() || tLLClaimSet.size() != 1) {
			CError.buildErr(this, "该赔案不存在立案信息，不能执行回退操作!");
			return false;
		}
		mLLRegisterSchema = tLLRegisterSet.get(1);
		
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 查询回退信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tLLCaseBackSchema = new LLCaseBackSchema();
		LLCaseBackDB tLLCaseBackDB = new LLCaseBackDB();
		tLLCaseBackDB.setBackNo(mBackNo);
		tLLCaseBackDB.setClmNo(mClmNo);
		tLLCaseBackDB.setBackState("0");
		LLCaseBackSet tLLCaseBackSet = tLLCaseBackDB.query();

		if (tLLCaseBackDB.mErrors.needDealError()) {
			CError.buildErr(this, "回退号[" + mBackNo + "]，查询发生错误,不能进行回退!!!"
					+ tLLCaseBackDB.mErrors.getError(0).errorMessage);

			return false;
		}

		if (tLLCaseBackSet.size() != 1) {
			
			CError.buildErr(this, "回退号[" + mBackNo + "]，已经申请的回退记录为["
					+ tLLCaseBackSet.size() + "]不唯一,不能进行回退!!!");
			return false;
		}
		
		tLLCaseBackSchema = tLLCaseBackSet.get(1);
		
		
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 判断回退申请时产生的财务应收数据 是否
		 * 被回销 如果有数据，认为没有回销，不允许进行回退操作
		 * 只校验已执行财务给付的案件
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if(tLLCaseBackSchema.getBackType().equals("0"))
		{

			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setOtherNo(mClmNo);
			tLJSPayDB.setOtherNoType("5");
			LJSPaySet tLJSPaySet = tLJSPayDB.query();

			if (tLJSPayDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询财务应收发生错误，不能回退!");
				return false;
			}

			if (tLJSPaySet.size() != 0) {
				CError.buildErr(this, "该赔案的财务应收记录未回销，不能回退!");
				return false;
			}
		}
		
		
		
	

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－理赔回退前数据校验－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－理赔正式回退－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 回退信息保存
	 */
	private boolean dealLLCaseBack() {

		logger.debug("---------------------------------进行回退信息保存----开始---------------------------------");

		tLLCaseBackSchema.setBackState("1");

		tLLCaseBackSchema.setOperator(mG.Operator);
		tLLCaseBackSchema.setMngCom(mG.ManageCom);
		tLLCaseBackSchema.setModifyDate(this.CurrentDate);
		tLLCaseBackSchema.setModifyTime(this.CurrentTime);

		tLLCaseBackSchema.setAffirmUser(mG.Operator); // 确认人
		tLLCaseBackSchema.setAffirmDate(this.CurrentDate); // 确认日期
		tLLCaseBackSchema.setAffirmTime(this.CurrentTime); // 确认时间

		mMMap.put(tLLCaseBackSchema, "DELETE&INSERT");
		

		logger.debug("案件"+tLLCaseBackSchema.getClmNo()+"的回退类型:"+tLLCaseBackSchema.getBackType());
		logger.debug("案件"+tLLCaseBackSchema.getClmNo()+"的是否同时回退预付信息标志:"+tLLCaseBackSchema.getCheckBackPreFlag());

		logger.debug("---------------------------------进行回退信息保存----结束---------------------------------");
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		logger.debug("---------------------------------进行回退数据处理----开始---------------------------------");
		
		
		// 进行回退信息保存
		if (!dealLLCaseBack()) {
			return false;
		}
			
		
		/**
		 * 已签批未给付案件只执行部分操作 BackType='1'
		 * 已执行财务给付案件流程则需要根据是否同时清除预付信息
		 */
			
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 进行工作流回退
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		
		
		if (!getDealWorkFlow()) {
			return false;
		}
		
		
		/**
		 * ---------------------------------------------------------------------BEG
		 * No6.1 进行财务数据的回退
		 * --------------------------------------------------------------------
		 */
		// if (!getDealFinance())
		// {
		// return false;
		// }



		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0 进行数据备份
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getDealBackData()) {
			return false;
		}
		
		
		
		if(tLLCaseBackSchema.getBackType().equals("0"))
		{
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 进行保额冲减回退
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getDealAmnt()) {
				return false;
			}
			
			/**
			 * ---------------------------------------------------------------------BEG
			 * No5.1 进行保险责任终止回退
			 * --------------------------------------------------------------------
			 */
			if (!getDealDutyCode()) {
				return false;
			}
			
			
			/**
			 * ---------------------------------------------------------------------BEG
			 * No7.1 合同处理回退
			 * ---------------------------------------------------------------------
			 */
			if (!getDealLCContState()) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1 豁免处理
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getDealLLExempt()) {
				return false;
			}
		}


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.0 进行SQL语句回退
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getDealSql()) {
			return false;
		}


	

		logger.debug("---------------------------------进行回退数据处理----结束---------------------------------");

		return true;
	}

	/**
	 * 进行数据备份
	 * 
	 * @return boolean
	 */
	private boolean getDealBackData() {
		logger.debug("---------------------------------进行数据备份处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 备份__立案理赔类型备份表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
		tLLAppClaimReasonDB.setCaseNo(this.mClmNo);
		LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();

		if (tLLAppClaimReasonSet.mErrors.needDealError()
				|| tLLAppClaimReasonSet.size() == 0) {
			
			CError.buildErr(this, "没有找到立案理赔类型信息，不能执行回退操作!!!");
			tLLAppClaimReasonSet=null;
			tLLAppClaimReasonDB=null;
			return false;
		}
		

		String tEditNo = PubFun1.CreateMaxNo("EditNo", "10"); // 生成修改顺序号

	
		LLBAppClaimReasonSet tLLBAppClaimReasonSaveSet = new LLBAppClaimReasonSet();
		LLAppClaimReasonSchema tLLAppClaimReasonSchema = null;
		LLBAppClaimReasonSchema tLLBAppClaimReasonSchema =null;

		for (int i = 1; i <= tLLAppClaimReasonSet.size(); i++) {

			
			tLLAppClaimReasonSchema = tLLAppClaimReasonSet.get(i);
			
			tLLBAppClaimReasonSchema = new LLBAppClaimReasonSchema();

			this.mReflections.transFields(tLLBAppClaimReasonSchema,
					tLLAppClaimReasonSchema);

			tLLBAppClaimReasonSchema.setEditNo(tEditNo);
			tLLBAppClaimReasonSchema.setBackNo(this.mBackNo);			
			tLLBAppClaimReasonSchema.setOperator(this.mG.Operator);
			tLLBAppClaimReasonSchema.setMngCom(this.mG.ManageCom);
			tLLBAppClaimReasonSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBAppClaimReasonSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBAppClaimReasonSaveSet.add(tLLBAppClaimReasonSchema);
			
			tLLBAppClaimReasonSchema=null;
			tLLAppClaimReasonSchema=null;

		}
		
		mMMap.put(tLLBAppClaimReasonSaveSet, "DELETE&INSERT");
		
		tLLAppClaimReasonSet=null;
		tLLBAppClaimReasonSaveSet=null;
		tLLAppClaimReasonDB=null;
		
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 备份__立案理赔类型备份表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(this.mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.query();

		if (tLLCaseSet.mErrors.needDealError() || tLLCaseSet.size() == 0) {
			
			CError.buildErr(this, "没有找到立案分案信息，不能执行回退操作!!!");
			tLLCaseSet=null;
			tLLCaseDB=null;
			return false;
		}

		LLBCaseSet tLLBCaseSaveSet = new LLBCaseSet();
		
		LLCaseSchema tLLCaseSchema =null;
		LLBCaseSchema tLLBCaseSchema =null;
		
		for (int i = 1; i <= tLLCaseSet.size(); i++) {
			
			tLLCaseSchema = tLLCaseSet.get(i);
			tLLBCaseSchema = new LLBCaseSchema();

			this.mReflections.transFields(tLLBCaseSchema, tLLCaseSchema);

			tLLBCaseSchema.setEditNo(tEditNo);
			tLLBCaseSchema.setBackNo(this.mBackNo);
			tLLBCaseSchema.setOperator(this.mG.Operator);
			tLLBCaseSchema.setMngCom(this.mG.ManageCom);
			tLLBCaseSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBCaseSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBCaseSaveSet.add(tLLBCaseSchema);
			
			tLLCaseSchema=null;
			tLLBCaseSchema=null;

		}
		mMMap.put(tLLBCaseSaveSet, "DELETE&INSERT");
		
		tLLBCaseSaveSet=null;
		tLLCaseSet=null;
		tLLCaseDB=null;

		
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 备份__案件核赔履历表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setCaseNo(this.mClmNo);
		LLClaimUWMainSet tLLClaimUWMainSet = tLLClaimUWMainDB.query();

		LLClaimUWMDetailSet tLLClaimUWMDetailSet = new LLClaimUWMDetailSet();
		
		ExeSQL exesql = new ExeSQL();
		
		LLClaimUWMainSchema tLLClaimUWMainSchema=null;
		LLClaimUWMDetailSchema tLLClaimUWMDetailSchema=null;

		for (int i = 1; i <= tLLClaimUWMainSet.size(); i++) {
			tLLClaimUWMainSchema = tLLClaimUWMainSet.get(i);
			tLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();

			this.mReflections.transFields(tLLClaimUWMDetailSchema,
					tLLClaimUWMainSchema);
			
			//查询LLClaimUWMDetail核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
			       + " ClmNO='" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("ClmNO", tLLClaimUWMainSchema.getClmNo());
			String tMaxNo = exesql.getOneValue(sqlbv);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else {
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}

			tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);
			tLLClaimUWMDetailSchema.setBackNo(this.mBackNo);
			tLLClaimUWMDetailSchema.setClmUWer(this.mG.Operator);
			tLLClaimUWMDetailSchema.setOperator(this.mG.Operator);
			tLLClaimUWMDetailSchema.setMngCom(this.mG.ManageCom);
			tLLClaimUWMDetailSchema.setModifyDate(PubFun.getCurrentDate());
			tLLClaimUWMDetailSchema.setModifyTime(PubFun.getCurrentTime());

			tLLClaimUWMDetailSet.add(tLLClaimUWMDetailSchema);
			
			strSQL=null;
			tMaxNo=null;
			tLLClaimUWMDetailSchema=null;
			tLLClaimUWMainSchema=null;
		}
		mMMap.put(tLLClaimUWMDetailSet, "DELETE&INSERT");
		tLLClaimUWMDetailSet=null;
		tLLClaimUWMainSet=null;
		tLLClaimUWMainDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 核赔__赔案理赔类型
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDutyKindDB tLLClaimDutyKindDB = new LLClaimDutyKindDB();
		tLLClaimDutyKindDB.setClmNo(this.mClmNo);
		LLClaimDutyKindSet tLLClaimDutyKindSet = tLLClaimDutyKindDB.query();

		LLClaimUWDutyKindSet tLLClaimUWDutyKindSet = new LLClaimUWDutyKindSet();
		
		LLClaimDutyKindSchema tLLClaimDutyKindSchema=null;
		LLClaimUWDutyKindSchema tLLClaimUWDutyKindSchema=null;

		for (int i = 1; i <= tLLClaimDutyKindSet.size(); i++) {
			
			tLLClaimDutyKindSchema= tLLClaimDutyKindSet
					.get(i);
			tLLClaimUWDutyKindSchema = new LLClaimUWDutyKindSchema();

			this.mReflections.transFields(tLLClaimUWDutyKindSchema,
					tLLClaimDutyKindSchema);
			
			//查询LLClaimUWPolicy核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWDutyKind where "
			       + " ClmNO='" + "?ClmNO?" + "'";		
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("ClmNO", tLLClaimUWDutyKindSchema.getClmNo());
			String tMaxNo = exesql.getOneValue(sqlbv1);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else {
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}

			tLLClaimUWDutyKindSchema.setClmUWNo(tMaxNo);
			tLLClaimUWDutyKindSchema.setBackNo(this.mBackNo);
			tLLClaimUWDutyKindSet.add(tLLClaimUWDutyKindSchema);
			
			strSQL=null;
			tMaxNo=null;
			tLLClaimDutyKindSchema=null;
			tLLClaimUWDutyKindSchema=null;
		}
		mMMap.put(tLLClaimUWDutyKindSet, "DELETE&INSERT");
		tLLClaimUWDutyKindSet=null;
		tLLClaimDutyKindSet=null;
		tLLClaimDutyKindDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 核赔__赔案保单名细
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		tLLClaimPolicyDB.setClmNo(this.mClmNo);
		LLClaimPolicySet tLLClaimPolicySet = tLLClaimPolicyDB.query();

		LLClaimUWPolicySet tLLClaimUWPolicySet = new LLClaimUWPolicySet();
		
		LLClaimPolicySchema tLLClaimPolicySchema=null;
		LLClaimUWPolicySchema tLLClaimUWPolicySchema=null;

		for (int i = 1; i <= tLLClaimPolicySet.size(); i++) {
			tLLClaimPolicySchema = tLLClaimPolicySet.get(i);
			tLLClaimUWPolicySchema = new LLClaimUWPolicySchema();

			this.mReflections.transFields(tLLClaimUWPolicySchema,
					tLLClaimPolicySchema);
			
			//查询LLClaimUWPolicy核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWPolicy where "
			       + " ClmNO='" + "?ClmNO?" + "'";		
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("ClmNO", tLLClaimPolicySchema.getClmNo());
			String tMaxNo = exesql.getOneValue(sqlbv2);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else {
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}

			tLLClaimUWPolicySchema.setClmUWNo(tMaxNo);
			tLLClaimUWPolicySchema.setBackNo(this.mBackNo);
			tLLClaimUWPolicySchema.setOperator(this.mG.Operator);
			tLLClaimUWPolicySchema.setMngCom(this.mG.ManageCom);
			tLLClaimUWPolicySchema.setModifyDate(PubFun.getCurrentDate());
			tLLClaimUWPolicySchema.setModifyTime(PubFun.getCurrentTime());

			tLLClaimUWPolicySet.add(tLLClaimUWPolicySchema);
			
			strSQL=null;
			tMaxNo=null;
			tLLClaimPolicySchema=null;
			tLLClaimUWPolicySchema=null;

		}
		mMMap.put(tLLClaimUWPolicySet, "DELETE&INSERT");
		
		tLLClaimUWPolicySet=null;
		tLLClaimPolicySet=null;
		tLLClaimPolicyDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 核赔__核赔履历
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(this.mClmNo);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();

		LLClaimUWDetailSet tLLClaimUWDetailSet = new LLClaimUWDetailSet();
		
		LLClaimDetailSchema tLLClaimDetailSchema=null;
		LLClaimUWDetailSchema tLLClaimUWDetailSchema=null;

		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			
			
			tLLClaimDetailSchema = tLLClaimDetailSet.get(i);
			tLLClaimUWDetailSchema = new LLClaimUWDetailSchema();

			this.mReflections.transFields(tLLClaimUWDetailSchema,
					tLLClaimDetailSchema);
			
			//查询LLClaimUWPolicy核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWDetail where "
			       + " ClmNO='" + "?ClmNO?" + "'";		
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL);
			sqlbv3.put("ClmNO", tLLClaimUWDetailSchema.getClmNo());
			String tMaxNo = exesql.getOneValue(sqlbv3);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else {
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}

			tLLClaimUWDetailSchema.setClmUWNo(tMaxNo);
			tLLClaimUWDetailSchema.setBackNo(this.mBackNo);
			tLLClaimUWDetailSchema.setOperator(this.mG.Operator);
			tLLClaimUWDetailSchema.setMngCom(this.mG.ManageCom);
			tLLClaimUWDetailSchema.setModifyDate(PubFun.getCurrentDate());
			tLLClaimUWDetailSchema.setModifyTime(PubFun.getCurrentTime());

			tLLClaimUWDetailSet.add(tLLClaimUWDetailSchema);
			
			strSQL=null;
			tMaxNo=null;
			tLLClaimDetailSchema=null;
			tLLClaimUWDetailSchema=null;
		}
		
		mMMap.put(tLLClaimUWDetailSet, "DELETE&INSERT");
		tLLClaimUWDetailSet=null;
		tLLClaimDetailSet=null;
		tLLClaimDetailDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 核赔__核赔责任费用统计
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDutyFeeDB tLLClaimDutyFeeDB = new LLClaimDutyFeeDB();
		tLLClaimDutyFeeDB.setClmNo(this.mClmNo);
		LLClaimDutyFeeSet tLLClaimDutyFeeSet = tLLClaimDutyFeeDB.query();

		LLClaimUWDutyFeeSet tLLClaimUWDutyFeeSet = new LLClaimUWDutyFeeSet();
		
		LLClaimDutyFeeSchema tLLClaimDutyFeeSchema=null;
		LLClaimUWDutyFeeSchema tLLClaimUWDutyFeeSchema=null;

		for (int i = 1; i <= tLLClaimDutyFeeSet.size(); i++) {
			
			tLLClaimDutyFeeSchema = tLLClaimDutyFeeSet
					.get(i);
			tLLClaimUWDutyFeeSchema = new LLClaimUWDutyFeeSchema();

			this.mReflections.transFields(tLLClaimUWDutyFeeSchema,
					tLLClaimDutyFeeSchema);
			
			//查询LLClaimUWPolicy核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWDutyFee where "
			       + " ClmNO='" + "?ClmNO?" + "'";		
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSQL);
			sqlbv4.put("ClmNO", tLLClaimUWDutyFeeSchema.getClmNo());
			String tMaxNo = exesql.getOneValue(sqlbv4);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else 
			{
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}

			tLLClaimUWDutyFeeSchema.setClmUWNo(tMaxNo);
			tLLClaimUWDutyFeeSchema.setBackNo(this.mBackNo);
			tLLClaimUWDutyFeeSchema.setOperator(this.mG.Operator);
			tLLClaimUWDutyFeeSchema.setMngCom(this.mG.ManageCom);
			tLLClaimUWDutyFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLLClaimUWDutyFeeSchema.setModifyTime(PubFun.getCurrentTime());

			tLLClaimUWDutyFeeSet.add(tLLClaimUWDutyFeeSchema);
			
			
			strSQL=null;
			tMaxNo=null;
			tLLClaimDutyFeeSchema=null;
			tLLClaimUWDutyFeeSchema=null;
		}
		mMMap.put(tLLClaimUWDutyFeeSet, "DELETE&INSERT");
		tLLClaimUWDutyFeeSet=null;
		tLLClaimDutyFeeSet=null;
		tLLClaimDutyFeeDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0 备份__理赔结算表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBalanceDB tLLBalanceDB = new LLBalanceDB();
		tLLBalanceDB.setClmNo(this.mClmNo);
		LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();

		LLBBalanceSet tLLBBalanceSet = new LLBBalanceSet();
		
		LLBalanceSchema tLLBalanceSchema=null;
		LLBBalanceSchema tLLBBalanceSchema=null;

		for (int i = 1; i <= tLLBalanceSet.size(); i++) {
			tLLBalanceSchema = tLLBalanceSet.get(i);
			tLLBBalanceSchema = new LLBBalanceSchema();

			this.mReflections.transFields(tLLBBalanceSchema, tLLBalanceSchema);

			tLLBBalanceSchema.setBackNo(this.mBackNo);
			tLLBBalanceSchema.setOperator(this.mG.Operator);
			tLLBBalanceSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBBalanceSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBBalanceSet.add(tLLBBalanceSchema);
			
			tLLBBalanceSchema=null;
			tLLBalanceSchema=null;
		}
		mMMap.put(tLLBBalanceSet, "DELETE&INSERT");
		tLLBBalanceSet=null;
		tLLBalanceSet=null;
		tLLBalanceDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0 备份__理赔受益人账户
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfDB tLLBnfDB = new LLBnfDB();
		tLLBnfDB.setClmNo(this.mClmNo);
		LLBnfSet tLLBnfSet = tLLBnfDB.query();

		LLBBnfSet tLLBBnfSet = new LLBBnfSet();
		
		LLBnfSchema tLLBnfSchema=null;
		LLBBnfSchema tLLBBnfSchema =null;

		for (int i = 1; i <= tLLBnfSet.size(); i++) {
			tLLBnfSchema = tLLBnfSet.get(i);
			tLLBBnfSchema= new LLBBnfSchema();

			this.mReflections.transFields(tLLBBnfSchema, tLLBnfSchema);

			tLLBBnfSchema.setBackNo(this.mBackNo);
			tLLBBnfSchema.setOperator(this.mG.Operator);
			tLLBBnfSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBBnfSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBBnfSet.add(tLLBBnfSchema);
			
			tLLBnfSchema=null;
			tLLBBnfSchema =null;
		}
		mMMap.put(tLLBBnfSet, "DELETE&INSERT");
		tLLBnfSet=null;
		tLLBBnfSet=null;
		tLLBnfDB=null;
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.0 备份__理赔受益人账户汇总表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
		tLLBnfGatherDB.setClmNo(this.mClmNo);
		LLBnfGatherSet tLLBnfGatherSet = tLLBnfGatherDB.query();

		LLBBnfGatherSet tLLBBnfGatherSet = new LLBBnfGatherSet();
		
		LLBnfGatherSchema tLLBnfGatherSchema=null;
		LLBBnfGatherSchema tLLBBnfGatherSchema =null;

		for (int i = 1; i <= tLLBnfGatherSet.size(); i++) {
			tLLBnfGatherSchema = tLLBnfGatherSet.get(i);
			tLLBBnfGatherSchema= new LLBBnfGatherSchema();

			this.mReflections.transFields(tLLBBnfGatherSchema, tLLBnfGatherSchema);

			tLLBBnfGatherSchema.setBackNo(this.mBackNo);
			tLLBBnfGatherSchema.setOperator(this.mG.Operator);
			tLLBBnfGatherSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBBnfGatherSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBBnfGatherSet.add(tLLBBnfGatherSchema);
			
			tLLBBnfGatherSchema=null;
			tLLBnfGatherSchema =null;
		}
		mMMap.put(tLLBBnfGatherSet, "DELETE&INSERT");
		tLLBnfGatherSet=null;
		tLLBBnfGatherSet=null;
		tLLBnfGatherDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.0 备份__保费豁免记录表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLExemptDB tLLExemptDB = new LLExemptDB();
		tLLExemptDB.setClmNo(this.mClmNo);
		LLExemptSet tLLExemptSet = tLLExemptDB.query();

		LLBExemptSet tLLBExemptSet = new LLBExemptSet();
		
		LLExemptSchema tLLExemptSchema=null;
		LLBExemptSchema tLLBExemptSchema=null;

		for (int i = 1; i <= tLLExemptSet.size(); i++) {
			
			tLLExemptSchema = tLLExemptSet.get(i);
			tLLBExemptSchema = new LLBExemptSchema();

			this.mReflections.transFields(tLLBExemptSchema, tLLExemptSchema);

			tLLBExemptSchema.setBackNo(this.mBackNo);
			tLLBExemptSchema.setOperator(this.mG.Operator);
			tLLBExemptSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBExemptSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBExemptSet.add(tLLBExemptSchema);
			
			tLLExemptSchema=null;
			tLLBExemptSchema=null;
		}
		mMMap.put(tLLBExemptSet, "DELETE&INSERT");
		tLLBExemptSet=null;
		tLLExemptSet=null;
		tLLExemptDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.0 备份__理赔回退保单状态表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLContStateDB tLLContStateDB = new LLContStateDB();
		tLLContStateDB.setClmNo(this.mClmNo);
		LLContStateSet tLLContStateSet = tLLContStateDB.query();

		LLBContStateSet tLLBContStateSet = new LLBContStateSet();
		
		LLContStateSchema tLLContStateSchema = null;
		LLBContStateSchema tLLBContStateSchema= null;
		for (int i = 1; i <= tLLContStateSet.size(); i++) {
			
			tLLContStateSchema = tLLContStateSet.get(i);
			tLLBContStateSchema = new LLBContStateSchema();

			this.mReflections.transFields(tLLBContStateSchema,
					tLLContStateSchema);

			tLLBContStateSchema.setBackNo(this.mBackNo);
			tLLBContStateSchema.setOperator(this.mG.Operator);
			tLLBContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBContStateSchema.setModifyTime(PubFun.getCurrentTime());

			tLLBContStateSet.add(tLLBContStateSchema);
			
			tLLContStateSchema = null;
			tLLBContStateSchema= null;
		}
		mMMap.put(tLLBContStateSet, "DELETE&INSERT");
		tLLBContStateSet=null;
		tLLContStateSet=null;
		tLLContStateDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.0 备份__理赔年金责任备份表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLDutyDB tLLDutyDB = new LLDutyDB();
		tLLDutyDB.setClmNo(this.mClmNo);
		LLDutySet tLLDutySet = tLLDutyDB.query();

		LLBDutySet tLLBDutySaveSet = new LLBDutySet();
		
		LLDutySchema tLLDutySchema=null;
		LLBDutySchema tLLBDutySchema=null;

		for (int i = 1; i <= tLLDutySet.size(); i++) {
			tLLDutySchema = tLLDutySet.get(i);
			tLLBDutySchema = new LLBDutySchema();

			this.mReflections.transFields(tLLBDutySchema, tLLDutySchema);

			tLLBDutySchema.setBackNo(this.mBackNo);
			tLLBDutySchema.setOperator(this.mG.Operator);
			tLLBDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLLBDutySchema.setModifyTime(PubFun.getCurrentTime());


			tLLBDutySaveSet.add(tLLBDutySchema);
			
			tLLBDutySchema=null;
			tLLDutySchema=null;
		}
		
		mMMap.put(tLLBDutySaveSet, "DELETE&INSERT");
		tLLDutySet=null;
		tLLBDutySaveSet=null;
		tLLDutyDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No14.0 备份__理赔年金领取项备份表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLGetDB tLLGetDB = new LLGetDB();
		tLLGetDB.setClmNo(this.mClmNo);
		LLGetSet tLLGetSet = tLLGetDB.query();

		LLBGetSet tLLBGetSaveSet = new LLBGetSet();
		
		LLGetSchema tLLGetSchema =null;
		LLBGetSchema tLLBGetSchema = null;

		for (int i = 1; i <= tLLGetSet.size(); i++) {
			tLLGetSchema =  tLLGetSet.get(i);
			tLLBGetSchema = new LLBGetSchema();

			this.mReflections.transFields(tLLBGetSchema, tLLGetSchema);

			tLLBGetSchema.setBackNo(this.mBackNo);	
			tLLBGetSchema.setOperator(this.mG.Operator);
			tLLBGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLLBGetSchema.setModifyTime(PubFun.getCurrentTime());


			tLLBGetSaveSet.add(tLLBGetSchema);
		}
		mMMap.put(tLLBGetSaveSet, "DELETE&INSERT");
		tLLBGetSaveSet=null;
		tLLGetSet=null;
		tLLGetDB=null;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.0 备份__理赔核保加费轨迹表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		 LLUWPremMasterDB tLLUWPremMasterDB = new LLUWPremMasterDB();
		 tLLUWPremMasterDB.setClmNo(this.mClmNo);
		 LLUWPremMasterSet tLLUWPremMasterSet = tLLUWPremMasterDB.query();
		
		 LLUWPremSubSet tLLUWPremSubSaveSet = new LLUWPremSubSet();
		 for (int i = 1; i <= tLLUWPremMasterSet.size(); i++)
		 {
			 LLUWPremMasterSchema tLLUWPremMasterSchema =tLLUWPremMasterSet.get(i);
			 
			 LLUWPremSubSchema tLLUWPremSubSchema = new LLUWPremSubSchema();
			 
			 this.mReflections.transFields(tLLUWPremSubSchema,tLLUWPremMasterSchema);
			
			 tLLUWPremSubSchema.setBatNo(this.mBackNo);
			 tLLUWPremSubSchema.setBackNo(this.mBackNo);
			
			 tLLUWPremSubSaveSet.add(tLLUWPremSubSchema);
	     }
		 mMMap.put(tLLUWPremSubSaveSet, "DELETE&INSERT");
		
		
		/**
		 * 2009-01-10 zhangzheng
		 * 当需要回退预付信息时也备份预付信息
		 */
		if(!(tLLCaseBackSchema.getCheckBackPreFlag()==null||tLLCaseBackSchema.getCheckBackPreFlag().equals("")))
		{
			if(tLLCaseBackSchema.getCheckBackPreFlag().equals("1"))
			{
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No15.0 备份_预付赔案记录表
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
				tLLPrepayClaimDB.setClmNo(this.mClmNo);
				LLPrepayClaimSet tLLPrepayClaimSet = tLLPrepayClaimDB.query();

				LLPrepayUWClaimSet tLLPrepayUWClaimSet = new LLPrepayUWClaimSet();
				
				LLPrepayClaimSchema tLLPrepayClaimSchema = null;
				LLPrepayUWClaimSchema tLLPrepayUWClaimSchema= null;
				
				for (int i = 1; i <= tLLPrepayClaimSet.size(); i++) {
					
					tLLPrepayClaimSchema = tLLPrepayClaimSet.get(i);
					tLLPrepayUWClaimSchema = new LLPrepayUWClaimSchema();

					this.mReflections.transFields(tLLPrepayUWClaimSchema,
							tLLPrepayClaimSchema);

					tLLPrepayUWClaimSchema.setBackNo(this.mBackNo);
					tLLPrepayUWClaimSchema.setOperator(this.mG.Operator);
					tLLPrepayUWClaimSchema.setMngCom(this.mG.ManageCom);
					tLLPrepayUWClaimSchema.setModifyDate(PubFun.getCurrentDate());
					tLLPrepayUWClaimSchema.setModifyTime(PubFun.getCurrentTime());

					tLLPrepayUWClaimSet.add(tLLPrepayUWClaimSchema);
					
					tLLPrepayClaimSchema = null;
					tLLPrepayUWClaimSchema= null;
				}
				mMMap.put(tLLPrepayUWClaimSet, "DELETE&INSERT");
				tLLPrepayClaimSet=null;
				tLLPrepayUWClaimSet=null;

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No16.0 备份__预付明细备份记录表
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LLPrepayDetailDB tLLPrepayDetailDB = new LLPrepayDetailDB();
				tLLPrepayDetailDB.setClmNo(this.mClmNo);
				LLPrepayDetailSet tLLPrepayDetailSet = tLLPrepayDetailDB.query();

				LLPrepayUWDetailSet tLLPrepayUWDetailSet = new LLPrepayUWDetailSet();
				
				LLPrepayDetailSchema tLLPrepayDetailSchema=null;
				LLPrepayUWDetailSchema tLLPrepayUWDetailSchema=null;

				for (int i = 1; i <= tLLPrepayDetailSet.size(); i++) {
					
					tLLPrepayDetailSchema = tLLPrepayDetailSet.get(i);
					tLLPrepayUWDetailSchema = new LLPrepayUWDetailSchema();

					this.mReflections.transFields(tLLPrepayUWDetailSchema, tLLPrepayDetailSchema);

					tLLPrepayUWDetailSchema.setBackNo(this.mBackNo);
					tLLPrepayUWDetailSchema.setOperator(this.mG.Operator);
					tLLPrepayUWDetailSchema.setMngCom(this.mG.ManageCom);
					tLLPrepayUWDetailSchema.setModifyDate(PubFun.getCurrentDate());
					tLLPrepayUWDetailSchema.setModifyTime(PubFun.getCurrentTime());

					tLLPrepayUWDetailSet.add(tLLPrepayUWDetailSchema);
					
					tLLPrepayDetailSchema=null;
					tLLDutySchema=null;
				}
				
				mMMap.put(tLLPrepayUWDetailSet, "DELETE&INSERT");
				tLLPrepayDetailSet=null;
				tLLPrepayUWDetailSet=null;
				tLLPrepayDetailDB=null;
			}
		}


		logger.debug("---------------------------------进行数据备份处理----结束---------------------------------");

		return true;
	}

	/**
	 * 处理SQL语句
	 * 
	 * @return boolean
	 */
	private boolean getDealSql() {

		logger.debug("---------------------------------进行SQL语句处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 将理赔的次数减1一
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLCPolSql = "update LCPol b set b.claimtimes = b.claimtimes - 1 where 1=1 "
				+ "and b.polno in (select a.polno from LLClaimPolicy a where a.clmno = '"
				+ "?clmno?" + "')";
		
		logger.debug("tLCPolSql:"+tLCPolSql);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tLCPolSql);
		sqlbv5.put("clmno", mClmNo);
		mMMap.put(sqlbv5, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 删除结算信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLLBalanceSql = "delete from LLBalance where ClmNo='"
				+ "?clmno?"+"'"
				//+ "' and substr(FeeOperationType,1,1) in ('C','D') and substr(FeeOperationType,1,3) not in ('C08','C30') ";
				+ " and substr(FeeOperationType,1,1) in('A','C','D')";
		
		logger.debug("tLLBalanceSql:"+tLLBalanceSql);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tLLBalanceSql);
		sqlbv6.put("clmno", this.mClmNo);
		this.mMMap.put(sqlbv6, "DELETE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 删除受益人信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLLBnfSql = "delete from LLBnf where  bnfkind='A' and ClmNo='" + "?clmno?"+ "' ";
		logger.debug("tLLBnfSql:"+tLLBnfSql);
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tLLBnfSql);
		sqlbv7.put("clmno", this.mClmNo);
		mMMap.put(sqlbv7, "DELETE");
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 删除受益人汇总信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLLBnfGatherSql = "delete from LLBnfGather where  bnfkind='A' and ClmNo='" + "?clmno?"+ "' ";
		logger.debug("tLLBnfGatherSql:"+tLLBnfGatherSql);
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tLLBnfGatherSql);
		sqlbv8.put("clmno", this.mClmNo);
		mMMap.put(sqlbv8, "DELETE");


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 删除合同状态暂存表信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLLContStateSql = "delete from LLContState where ClmNo='"
				+ "?clmno?" + "'";
		logger.debug("tLLContStateSql:"+tLLContStateSql);
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tLLContStateSql);
		sqlbv9.put("clmno", this.mClmNo);
		mMMap.put(sqlbv9, "DELETE");
		


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.0 更改赔案状态的信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLRegisterSchema.setClmState("30");
		mLLRegisterSchema.setEndCaseFlag("0");
		mLLRegisterSchema.setEndCaseDate("");
		mMMap.put(mLLRegisterSchema, "UPDATE");

		// 如为团体则需要修改团体立案表 2006-6-5 11:26 周磊
		if (mLLRegisterSchema.getRgtObj().equals("2")) {
			LLGrpRegisterDB tLLGrpRegisterDB = new LLGrpRegisterDB();
			tLLGrpRegisterDB.setRgtNo(mLLRegisterSchema.getRgtObjNo());
			if (!tLLGrpRegisterDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLEndCaseBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询团体赔案出错！";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LLGrpRegisterSchema tLLGrpRegisterSchema = tLLGrpRegisterDB
						.getSchema();
				if (tLLGrpRegisterSchema.getEndCaseFlag().equals("1")) {
					tLLGrpRegisterSchema.setClmState("30"); // 赔案状态置为审核
					tLLGrpRegisterSchema.setEndCaseFlag("0");
					tLLGrpRegisterSchema.setEndCaseDate("");
					tLLGrpRegisterSchema.setModifyDate(CurrentDate);
					tLLGrpRegisterSchema.setModifyTime(CurrentTime);

					mMMap.put(tLLGrpRegisterSchema, "DELETE&INSERT");
				}
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.0 更改赔案状态,回退标志，回退编号的信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLClaimSchema.setClmState("30");
		mLLClaimSchema.setConBalFlag("0");
		mLLClaimSchema.setConDealFlag("0");
		mLLClaimSchema.setEndCaseDate("");
		mLLClaimSchema.setBackFlag("1");
		mLLClaimSchema.setBackNo(this.mBackNo);
		mMMap.put(mLLClaimSchema, "UPDATE");
		
		
		//赔案明细表
		String llclaimpolicy1 = " update llclaimpolicy set ClmState = '30' where"
			        + " clmno = '" + "?clmno?" + "'";
		
		logger.debug("llclaimpolicy1:"+llclaimpolicy1);
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(llclaimpolicy1);
		sqlbv10.put("clmno", mLLClaimSchema.getClmNo());
		mMMap.put(sqlbv10, "UPDATE");


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.0 删除打印管理表的信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLOPRTManagerSqlA = "delete from LOPRTManager where OtherNo='"
				+ "?clmno?" + "' and StandbyFlag6 = '50'";
		
		logger.debug("tLOPRTManagerSqlA:"+tLOPRTManagerSqlA);
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tLOPRTManagerSqlA);
		sqlbv11.put("clmno", this.mClmNo);
		mMMap.put(sqlbv11, "DELETE");

		String tLOPRTManagerSqlB = "delete from LOPRTManager where OtherNo='"
				+ "?clmno?" + "' and Code in ('PCT008','PCT019')";
		
		logger.debug("tLOPRTManagerSqlB:"+tLOPRTManagerSqlB);
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tLOPRTManagerSqlB);
		sqlbv12.put("clmno", this.mClmNo);
		mMMap.put(sqlbv12, "DELETE");
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No14.0 删除豁免信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLLExempt = "delete from LLExempt where clmno='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(tLLExempt);
		sqlbv13.put("clmno", this.mClmNo);
		mMMap.put(sqlbv13, "DELETE");
		
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No15.0 删除审核,审批结论信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tLLClaimUWMainSql = "delete from LLClaimUWMain where clmno='"
				+ "?clmno?" + "' and checktype='0'";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(tLLClaimUWMainSql);
		sqlbv14.put("clmno", this.mClmNo);
		mMMap.put(sqlbv14, "DELETE");



		// String tLLJSGetSql = "delete from LLJSGet where
		// ClmNo='"+this.mClmNo+"'";
		// String tLLJSGetDrawSql = "delete from LLJSGetDraw where
		// ClmNo='"+this.mClmNo+"'";
		// mMMap.put(tLLJSGetSql,"DELETE");
		// mMMap.put(tLLJSGetDrawSql,"DELETE");

		// String tLLJSPaySql = "delete from LLJSPay where
		// ClmNo='"+this.mClmNo+"'";
		// String tLLJSPayPersonSql = "delete from LLJSPayPerson where
		// ClmNo='"+this.mClmNo+"'";
		// mMMap.put(tLLJSPaySql,"DELETE");
		// mMMap.put(tLLJSPayPersonSql,"DELETE");
		
		/**
		 * 2009-01-10 zhangzheng
		 * 当需要回退预付信息时也需要回滚预付信息
		 */
		if(!(tLLCaseBackSchema.getCheckBackPreFlag()==null||tLLCaseBackSchema.getCheckBackPreFlag().equals("")))
		{
			if(tLLCaseBackSchema.getCheckBackPreFlag().equals("1"))
			{
		        String prepayclaimSql="delete from LLPrepayClaim where clmno='"+"?ClmNo?"+"'";
		        String prepaydetaliSql="delete from LLPrepayDetail where clmno='"+"?ClmNo?"+"'";
		        
		        String llclaimSql="update llclaim set realpay='"+"?realpay?"+"',beforepay='0' where clmno='"+"?ClmNo?"+"'";
		        String llclaimdetailSql="update llclaimdetail set PrepayFlag='0',PrepaySum='0' where clmno='"+"?ClmNo?"+"'";
		        
				String LLBnfSql2 = "delete from LLBnf where  bnfkind='B' and ClmNo='" + "?ClmNo?"+ "' ";
				String LLBnfGatherSql2 = "delete from LLBnfGather where  bnfkind='B' and ClmNo='" + "?ClmNo?"+ "' ";
				String LLBalanceSql2 = "delete from LLBalance where ClmNo='"+ "?ClmNo?"+"'"+ " and Feeoperationtype='B'";		
				
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No15.0 删除审核,审批结论信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tLLClaimUWMainSql2 = "delete from LLClaimUWMain where clmno='"
						+ "?ClmNo?" + "' and checktype='1'";				
				
				
				logger.debug("prepayclaimSql:"+prepayclaimSql);
				logger.debug("prepaydetaliSql:"+prepaydetaliSql);
				
				logger.debug("llclaimSql:"+llclaimSql);
				logger.debug("llclaimdetailSql:"+llclaimdetailSql);
				
				logger.debug("LLBnfSql2:"+LLBnfSql2);
				logger.debug("LLBalanceSql2:"+LLBnfSql2);
				
				logger.debug("tLLClaimUWMainSql2:"+tLLClaimUWMainSql2);
			    
				// 打包提交数据       
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(prepayclaimSql);
				sqlbv15.put("ClmNo", mClmNo);
		        mMMap.put(sqlbv15, "DELETE");
		        SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql(prepaydetaliSql);
				sqlbv16.put("ClmNo", mClmNo);
		        mMMap.put(sqlbv16, "DELETE");
		        SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(llclaimSql);
				sqlbv17.put("ClmNo", mClmNo);
				sqlbv17.put("realpay", (mLLClaimSchema.getRealPay()+mLLClaimSchema.getBeforePay()));
		        mMMap.put(sqlbv17, "UPDATE");
		        SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(llclaimdetailSql);
				sqlbv18.put("ClmNo", mClmNo);
		        mMMap.put(sqlbv18, "UPDATE");
		        SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(LLBnfSql2);
				sqlbv19.put("ClmNo", this.mClmNo);
				mMMap.put(sqlbv19, "DELETE");
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(LLBnfGatherSql2);
				sqlbv20.put("ClmNo", this.mClmNo);
				mMMap.put(sqlbv20, "DELETE");
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(LLBalanceSql2);
				sqlbv21.put("ClmNo", this.mClmNo);
				mMMap.put(sqlbv21, "DELETE");
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql(tLLClaimUWMainSql2);
				sqlbv22.put("ClmNo", this.mClmNo);
				mMMap.put(sqlbv22, "DELETE");
				
		        llclaimdetailSql=null;
		        llclaimSql=null;
		        prepaydetaliSql=null;
		        prepayclaimSql=null;
		        LLBnfSql2=null;
		        LLBalanceSql2=null;
		        LLBnfGatherSql2=null;
		        tLLClaimUWMainSql2=null;
			}
			else
			{
				String llclaimdetailSql=" update llclaimdetail a set a.PrepaySum = (select b.PrepaySum"
					                   +" from llprepaydetail b   where a.clmno = b.clmno   and a.getdutykind = b.getdutykind"
					                   +" and a.getdutycode = b.getdutycode) "
					                   +" where a.clmno='"+"?clmno?"+"'";
				logger.debug("llclaimdetailSql:"+llclaimdetailSql);
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql(llclaimdetailSql);
				sqlbv23.put("clmno", mClmNo);
		        mMMap.put(sqlbv23, "UPDATE");
		        llclaimdetailSql=null;
			}
		}

		/**
		 * 09-06-10  增加回退时清空ldperson表中的deathdate字段
		 * */
		String tLDPersonSql = "update ldperson set deathdate='',modifydate='"+"?CurrentDate?"
					+ "',modifytime='"+"?CurrentTime?"+"'"
					+ " where customerno in (select customerno from llcase where caseno='"+"?clmno?"+"')";
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql(tLDPersonSql);
		sqlbv24.put("CurrentDate", CurrentDate);
		sqlbv24.put("CurrentTime", CurrentTime);
		sqlbv24.put("clmno", mClmNo);
		mMMap.put(sqlbv24, "UPDATE");
		//释放强引用
		tLCPolSql=null;		
		tLLBalanceSql=null;
		tLLBnfSql=null;
		tLLContStateSql=null;
		llclaimpolicy1=null;		
        tLOPRTManagerSqlA=null;
        tLOPRTManagerSqlB=null;        
        tLLClaimUWMainSql=null;

		logger.debug("---------------------------------进行SQL语句处理----结束---------------------------------");
		return true;
	}

	/**
	 * 工作流回退
	 * 
	 * @return boolean
	 */
	private boolean getDealWorkFlow() {
		logger.debug("---------------------------------进行工作流回退处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 删除工作流备份表的审批[40]，结案[50],财务给付[60] 信息 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tLBMissionSql = "delete from LBMission where missionprop1 = '"
				+ "?clmno?" + "' and missionprop2 in ('40','50','60')";
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(tLBMissionSql);
		sqlbv25.put("clmno", mClmNo);
		mMMap.put(sqlbv25, "DELETE");
		
		String tLWMissionSql = "delete from LWMission where missionprop1 = '"
			+ "?clmno?" + "' and missionprop2 in ('50','60')";
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(tLWMissionSql);
		sqlbv26.put("clmno", mClmNo);
	    mMMap.put(sqlbv26, "DELETE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 1 简易案件回到简易案件的共享池中,RgtState
		 * 2 正常案件回到原审核人的工作队列中; －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
	    if(mLLRegisterSchema.getRgtState().equals("01"))
	    {
	    	LBMissionDB tLBMissionDB = new LBMissionDB();
			tLBMissionDB.setMissionProp1(mClmNo);
			tLBMissionDB.setActivityID("0000005065");
			LBMissionSet tLBMissionSet = tLBMissionDB.query();

			if (tLBMissionSet.size() == 0) {
				mErrors.addOneError("工作流轨迹的备份表不存在简易案件阶段的业务信息，不能执行回退操作!!!");
				return false;
			}
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();

			this.mReflections.transFields(tLWMissionSchema, tLBMissionSet
					.get(tLBMissionSet.size()));
			mMMap.put(tLWMissionSchema, "DELETE&INSERT");
	    }
	    else
	    {
	    	LBMissionDB tLBMissionDB = new LBMissionDB();
			tLBMissionDB.setMissionProp1(mClmNo);
			tLBMissionDB.setMissionProp2("30");
			LBMissionSet tLBMissionSet = tLBMissionDB.query();

			if (tLBMissionSet.size() == 0) {
				mErrors.addOneError("工作流轨迹的备份表不存在审核阶段的业务信息，不能执行回退操作!!!");
				return false;
			}
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();

			this.mReflections.transFields(tLWMissionSchema, tLBMissionSet
					.get(tLBMissionSet.size()));
			mMMap.put(tLWMissionSchema, "DELETE&INSERT");
	    }

		logger.debug("---------------------------------进行工作流回退处理----结束---------------------------------");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－回退过程中－－－－非帐户保额冲减－－－－－续涛加－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 进行保额冲减回退
	 * 
	 * @param
	 * @return 续涛,2005.08.10
	 */
	private boolean getDealAmnt() {
		
		
		logger.debug("---------------------------------进行保额回退处理----开始---------------------------------\n");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询赔案给付的保项数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select b.* from llclaim a,LLClaimDetail b where 1=1"
				+ " and a.clmno = b.clmno " + " and a.clmno = '" + "?clmno?" + "'"
				+ " and a.givetype='0'" + " and b.givetype='0'"
				+ " and substr(b.GetDutyKind,2,2) <> '09'";
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql(tSql);
		sqlbv27.put("clmno", mClmNo);
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB
				.executeQuery(sqlbv27);

		mLLClaimDetailSet.set(tLLClaimDetailSet);

		logger.debug("------------------------------------------------------------");
		logger.debug("--处理赔案的给付保项[" + tLLClaimDetailSet.size() + "]:["
				+ tSql + "]");
		logger.debug("------------------------------------------------------------");

		if (tLLClaimDetailDB.mErrors.needDealError()) {
			mErrors.addOneError("查询赔案给付保项发生错误，不能执行回退操作!!!"
					+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 循环赔案给付的保项数据,进行保额冲减
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailSchema tLLClaimDetailSchema = null;
		LCGetSet tLCGetSet = new LCGetSet();
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			tLLClaimDetailSchema = tLLClaimDetailSet.get(i);

			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tPosFlag = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag());// 0未做保全,1做过保全
			String tPosPosEdorNo = StrTool.cTrim(tLLClaimDetailSchema
					.getPosEdorNo());// 保全批单号
			String tNBPolNo = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo());// 承保时的保单号

			String tPolNo = tLLClaimDetailSchema.getPolNo();
			String tGetDutyKind = tLLClaimDetailSchema.getGetDutyKind();
			String tGetDutyCode = tLLClaimDetailSchema.getGetDutyCode();

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 7.理赔保额冲减
			 * 如果不冲减，继续循环 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "14")) {
				logger.debug("------------------------------------------------------------");
				logger.debug("--险种[" + tRiskCode + "]无须冲减保额，被过滤掉");
				logger.debug("------------------------------------------------------------");
				continue;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3 非帐户型保额冲减
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tAcctFlag = StrTool
					.cTrim(tLLClaimDetailSchema.getAcctFlag());
			if (!tAcctFlag.equals("1")) {
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 本责任保额冲减
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tLCGetSchema = getLCGet(tLLClaimDetailSchema);
				if (tLCGetSchema == null) {
					return false;
				} else {
					tLCGetSet.add(tLCGetSchema);
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.5
				 * 判断是否冲减其他保项的保额
				 * 必须用CnterCalCode判断，因为豁免用到了OthCalCode,没有用到CnterCalCode。
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
						.getLMDutyGetClm(tGetDutyKind, tGetDutyCode);

				if (tLMDutyGetClmSchema == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				String tCnterCalCode = StrTool.cTrim(tLMDutyGetClmSchema
						.getCnterCalCode());
				if (tCnterCalCode.length() > 0) {
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.6
					 * 计算出准备冲减其他责任的LCGet信息
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					LCGetSet tOtherLCGetSet = new LCGetSet();
					tOtherLCGetSet = getOtherLCGet(tLLClaimDetailSchema,
							tLMDutyGetClmSchema);
					if (tOtherLCGetSet == null) {
						return false;
					} else {
						tLCGetSet.add(tOtherLCGetSet);
					}

				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.6 帐户型保额冲减
			 * dealAccount(tLLClaimDetailSchema);
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			else {
				//判断是否是投连险
				String sql="select 1 from lmrisktoacc c, lmriskinsuacc r "// 是投连险
				+" where r.insuaccno = c.insuaccno and r.acckind = '2' and c.riskcode ='"+"?riskcode?"+"'";
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(sql);
				sqlbv28.put("riskcode", tLLClaimDetailSchema.getRiskCode());
				SSRS tSSRS= new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sqlbv28);
				if(tSSRS==null || tSSRS.getMaxRow()<=0){
					if (!dealAccount(tLLClaimDetailSchema)) {
						return false;
					}
				}
				else
				{
					logger.debug("账户型险种结算回退开始......");
					LLClaimClearBackBL tLLClaimClearBackBL=new LLClaimClearBackBL();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("BackNo", mBackNo);
					tTransferData.setNameAndValue("ClmNo", mClmNo);
					tTransferData.setNameAndValue("ContNo", tLLClaimDetailSchema.getContNo());
					tTransferData.setNameAndValue("PolNo", tLLClaimDetailSchema.getPolNo());
					tTransferData.setNameAndValue("RiskCode", tLLClaimDetailSchema.getRiskCode());
					VData tVData = new VData();
					tVData.add(tTransferData);
					if(tLLClaimClearBackBL.submitData(tVData, ""))
					{
						VData tResult = new VData();
						tResult=tLLClaimClearBackBL.getResult();
						mMMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
						
						LOPolAfterDealDB tLOPolAfterDealDB = new LOPolAfterDealDB();
						tLOPolAfterDealDB.setPolNo(tLLClaimDetailSchema.getPolNo());
						tLOPolAfterDealDB.setBusyType("CL");
						tLOPolAfterDealDB.setAccAlterNo(mClmNo);
						tLOPolAfterDealDB.setAccAlterType("4");
						
						LOPolAfterDealSet tLOPolAfterDealSet = tLOPolAfterDealDB.query();
						if (tLOPolAfterDealSet != null && tLOPolAfterDealSet.size() > 0) {
							mMMap.put(tLOPolAfterDealSet, "DELETE");
						}						
					}
					else
					{
						mErrors.copyAllErrors(tLLClaimClearBackBL.mErrors);
						return false;
					}
				}
			}

		}

		mMMap.put(tLCGetSet, "UPDATE");

		logger.debug("\n---------------------------------进行保额回退处理----结束---------------------------------");

		return true;
	}

	/**
	 * 进行本责任保额冲减
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private LCGetSchema getLCGet(LLClaimDetailSchema pLLClaimDetailSchema) {

		logger.debug("----------------------进行本责任保额回退处理----开始----------------------");

		String tPolNo = pLLClaimDetailSchema.getPolNo();
		String tDutyCode = pLLClaimDetailSchema.getDutyCode();
		String tGetDutyCode = pLLClaimDetailSchema.getGetDutyCode();

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(tPolNo);
		tLCGetDB.setDutyCode(tDutyCode);
		tLCGetDB.setGetDutyCode(tGetDutyCode);

		LCGetSet tLCGetSet = tLCGetDB.query();
		if (tLCGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],给付责任:["
					+ tDutyCode + "],领取项信息失败!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() != 1) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],给付责任:[" + tDutyCode + "],查询出的记录数为:["
					+ tLCGetSet.size() + "],领取项信息不唯一!");
			return null;
		}

		LCGetSchema tLCGetSchema = tLCGetSet.get(1);

		double t_SumMoney = 0;
		t_SumMoney = tLCGetSchema.getSumMoney();

		t_SumMoney = Arith.round(tLCGetSchema.getSumMoney()
				- pLLClaimDetailSchema.getRealPay(), 2);

		logger.debug("------------------------------开始---------正常冲减保额------------------------------");
		logger.debug("保单险种号PolNo:[" + tPolNo + "],责任DutyCode:["
				+ tDutyCode + "],给付责任GetDutyCode:[" + tGetDutyCode + "]");
		logger.debug("基本保额StandMoney:[" + tLCGetSchema.getStandMoney()
				+ "],已冲减的金额SumMoney:[" + tLCGetSchema.getSumMoney()
				+ "],本次回退金额RealPay:[" + pLLClaimDetailSchema.getRealPay()
				+ "],冲减后金额:[" + t_SumMoney + "]");
		logger.debug("------------------------------结束---------正常冲减保额------------------------------");

		tLCGetSchema.setSumMoney(t_SumMoney);
		tLCGetSchema.setOperator(mG.Operator);
		tLCGetSchema.setMakeDate(this.CurrentDate);
		tLCGetSchema.setMakeTime(this.CurrentTime);
		logger.debug("----------------------进行本责任保额回退处理----结束----------------------");
		return tLCGetSchema;
	}

	/**
	 * 进行其他起责任保额冲减
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private LCGetSet getOtherLCGet(LLClaimDetailSchema pLLClaimDetailSchema,
			LMDutyGetClmSchema pLMDutyGetClmSchema) {

		logger.debug("---------------------------------进行其他责任保额冲减----开始---------------------------------");

		double t_OthMoney = 0;
		t_OthMoney = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_OthMoney));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1 查询LCPol的信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = pLLClaimDetailSchema.getPolNo();
		String tDutyCode = pLLClaimDetailSchema.getDutyCode();

		mLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
		if (mLCPolSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return null;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2 通过公式计算出冲减其他责任的金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tCnterCalCode = StrTool.cTrim(pLMDutyGetClmSchema
				.getCnterCalCode());// 获取LCGet的公式
		String tOthCalCode = StrTool.cTrim(pLMDutyGetClmSchema.getOthCalCode()); // 计算公式的金额
		String tGetDutyCode = tCnterCalCode;

		String tSql = executeCalCode(tCnterCalCode, pLLClaimDetailSchema); // 解析后的LCGet语句
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(tSql);
		t_OthMoney = executePay(tOthCalCode, pLLClaimDetailSchema);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 查询其他责任
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbva);
		if (tLCGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],领取项信息失败!" + tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() == 0) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],查询出的记录数为:[" + tLCGetSet.size() + "],领取项信息能为空!");
			return null;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 对其他责任的保额进行冲减
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCGetSet tLCGetSaveSet = new LCGetSet();
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);

			double t_SumMoney = 0;
			t_SumMoney = tLCGetSchema.getSumMoney();

			t_SumMoney = Arith
					.round(tLCGetSchema.getSumMoney() - t_OthMoney, 2);

			logger.debug("------------------------------开始[" + i
					+ "]个---------其他责任冲减保额------------------------------");
			logger.debug("保单险种号PolNo:[" + tPolNo + "],责任DutyCode:["
					+ tDutyCode + "],给付责任GetDutyCode:[" + tGetDutyCode + "]");
			logger.debug("基本保额StandMoney:["
					+ tLCGetSchema.getStandMoney() + "],已冲减的金额SumMoney:["
					+ tLCGetSchema.getSumMoney() + "],本次给付金额:[" + t_OthMoney
					+ "],冲减后金额:[" + t_SumMoney + "]");
			logger.debug("------------------------------结束[" + i
					+ "]个---------其他责任冲减保额------------------------------");

			tLCGetSchema.setSumMoney(t_SumMoney);
			tLCGetSchema.setOperator(mG.Operator);
			tLCGetSchema.setMakeDate(this.CurrentDate);
			tLCGetSchema.setMakeTime(this.CurrentTime);
			tLCGetSaveSet.add(tLCGetSchema);
		}

		logger.debug("----------------------进行其他责任保额冲减回退----结束----------------------");
		return tLCGetSaveSet;
	}

	/**
	 * 目的：执行产品指向的算法公式
	 */
	private String executeCalCode(String pCalCode,
			LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";

		if (pCalCode == null || pCalCode.length() == 0) {
			return tReturn;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种参数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("----------开始---------执行产品指向的算法公式-------------------------------");

		logger.debug("===========================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		// 合同号
		tTransferData.setNameAndValue("ContNo", String
				.valueOf(pLLClaimDetailSchema.getContNo()));

		// 保单险种号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(pLLClaimDetailSchema.getPolNo()));

		// 责任
		tTransferData.setNameAndValue("DutyCode", String
				.valueOf(pLLClaimDetailSchema.getDutyCode()));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		logger.debug("===========================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算要素--tName:" + tName + "  tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("===========================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("executeCalCode--tName:" + tName + " tValue:"
			// + tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}

		tReturn = tCalculator.getCalSQL();
		logger.debug("----------------------------------------------------------------------------------\n");
		logger.debug("解析前的计算公式==[" + pCalCode + "]");
		logger.debug("解析后的计算公式==[" + tReturn + "]");
		logger.debug("\n----------------------------------------------------------------------------------");

		logger.debug("\n----------结束---------执行产品指向的算法公式-------------------------------");

		return tReturn;

	}

	/**
	 * 目的：理赔计算
	 * 
	 * @param pCalSum
	 *            计算金额
	 * @param pCalCode
	 *            计算公式
	 * @return double
	 */
	private double executePay(String pCalCode,
			LLClaimDetailSchema pLLClaimDetailSchema) {
		double rValue;

		if (null == pCalCode || "".equals(pCalCode)) {
			return 0;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种计算要素 ?Amnt?
		 * \?PolNo??\?GetDutyCode?\?GetDutyKind?\?ClmNO?
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		logger.debug("==================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();

		// 基本保额,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Amnt", String
				.valueOf(getAmnt(pLLClaimDetailSchema)));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(pLLClaimDetailSchema.getPolNo()));

		// 给付责任类型
		tTransferData.setNameAndValue("GetDutyCode", String
				.valueOf(pLLClaimDetailSchema.getGetDutyCode()));

		// 赔案号
		tTransferData.setNameAndValue("ClmNO", String.valueOf(this.mClmNo));
		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("PubCalculator.addBasicFactor--tName:" + tName
			// + " tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		logger.debug("==================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算其他责任计算要素名称--tName:" + tName + "  tValue:"
					+ tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("==================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 进行计算，Calculator.calculate()
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tStr = "";
		logger.debug("计算公式=" + tCalculator.getCalSQL());
		tStr = tCalculator.calculate();
		if (tStr.trim().equals("")) {
			rValue = 0;
		} else {
			rValue = Double.parseDouble(tStr);
		}

		if (tCalculator.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tCalculator.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "executepay";
			tError.errorMessage = "计算其他责任发生错误!原公式:" + pCalCode + "||,解析后的公式:"
					+ tCalculator.getCalSQL();
			this.mErrors.addOneError(tError);
		}
		logger.debug("==================================================================");
		return rValue;
	}

	/**
	 * 得到基本保额,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private double getAmnt(LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "";

		String tPosFlag = StrTool.cTrim(pLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全 */
		if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LCGet where 1=1 "
					+ " and PolNo ='" + "?PolNo?" + "'"
					+ " and DutyCode ='" + "?DutyCode?"
					+ "'" + " and GetDutyCode ='"
					+ "?GetDutyCode?" + "'";
		} else {

			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LPGet where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?"
					+ "'" + " and PolNo ='" + "?PolNo?"
					+ "'" + " and DutyCode ='"
					+ "?DutyCode?" + "'"
					+ " and GetDutyCode ='"
					+ "?GetDutyCode?" + "'";
		}

		logger.debug("------------------------------------------------------------");
		logger.debug("--要素Amnt[基本保额]：[" + tSql + "]");
		logger.debug("------------------------------------------------------------");
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql(tSql);
		sqlbv30.put("PolNo", pLLClaimDetailSchema.getPolNo());
		sqlbv30.put("DutyCode", pLLClaimDetailSchema.getDutyCode());
		sqlbv30.put("GetDutyCode", pLLClaimDetailSchema.getGetDutyCode());
		sqlbv30.put("EdorNo", pLLClaimDetailSchema.getPosEdorNo());
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv30));
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("得到基本保额发生错误!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			t_Sum_Return = Double.parseDouble(tReturn);
		}

		return t_Sum_Return;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－过程中－－－－－帐户保额冲减－－－－－续涛加－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 处理帐户型的保额冲减
	 */
	private boolean dealAccount(LLClaimDetailSchema pLLClaimDetailSchema) {
		logger.debug("---------------------------------进行帐户型保额冲减----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = StrTool.cTrim(pLLClaimDetailSchema.getContNo());
		String tPolNo = StrTool.cTrim(pLLClaimDetailSchema.getPolNo());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据赔案号，从LLRegister表中取出立案登记信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLRegisterSchema tLLRegisterSchema = mLLClaimPubFunBL
				.getLLRegister(mClmNo);

		if (tLLRegisterSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		String tRgtDate = tLLRegisterSchema.getRgtDate().substring(0, 10);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 查出该保单的所有帐户
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		String strSql = "select * from LCInsureAcc where 1=1" + " and PolNo ='"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql(strSql);
		sqlbv31.put("PolNo", tPolNo);
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv31);
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单号:[" + tPolNo + "]保险帐户信息查询失败!");
			return false;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			CError.buildErr(this, "保单号:[" + tPolNo + "]保险帐户信息查询为空!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1
		 * 查出该保单的所有帐户信息LCInsureAcc
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCInsureAccSet tLCInsureAccSaveSet = new LCInsureAccSet();
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.2 定义业务处理变量
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet.get(i);

			String tSql = "";
			double tRealPay = 0.0; // 最后赔付金额
			double tSumPaym = 0.0; // 帐户已领金额
			double tInsuAccGetMoney = 0;// 账户可领金额
			double tClmAccBal = 0.0; // 理赔帐户差额

			String tInsuAccNo = StrTool
					.cTrim(tLCInsureAccSchema.getInsuAccNo());
			tRealPay = pLLClaimDetailSchema.getRealPay(); // 最后赔付金额
			tSumPaym = tLCInsureAccSchema.getSumPaym(); // 帐户已领金额
			tInsuAccGetMoney = tLCInsureAccSchema.getInsuAccGetMoney();// 账户可领金额

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.4 计算理赔轨迹信息金总额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			tSql = "select currency,(case when sum(money) is null then 0 else sum(money) end) from LCInsureAccTrace where 1=1 "
					+ " and PolNo = '" + "?PolNo?" + "'" + " and InsuAccNo = '"
					+ "?InsuAccNo?" + "'" + " and MoneyType in ('LP') "
					+ " and OtherNo = '" + "?clmno?" + "'"
					+ " and OtherType = '5'"
					+ " group by currency";
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql(tSql);
			sqlbv32.put("PolNo", tPolNo);
			sqlbv32.put("InsuAccNo", tInsuAccNo);
			sqlbv32.put("clmno", mClmNo);
			ExeSQL tExeSQL = new ExeSQL();
			//String tSReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
			SSRS tSSRS = tExeSQL.execSQL(sqlbv32);
			double tMoney=0.00;
			LDExch tLDExch = new LDExch();
			for(int j=1;j<=tSSRS.getMaxRow();j++)
			{
				tMoney = tMoney + tLDExch.toOtherCur(tSSRS.GetText(j, 1), pLLClaimDetailSchema.getCurrency(), this.CurrentDate, Double.parseDouble(tSSRS.GetText(j, 2)));
			}
			

			logger.debug("----------------------------------------------------------------------------");
			logger.debug("--计算理赔轨迹信息金总额[" + tMoney + "]:[" + tSql + "]");
			logger.debug("----------------------------------------------------------------------------");

			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.addOneError("计算出险时点之后的利息发生错误!!!");
				return false;
			}

			double tAccLastInterest = 0;
			if (tMoney > 0) {
				tInsuAccGetMoney = Arith.round(tMoney, 2);
			}

			tClmAccBal = tInsuAccGetMoney - tRealPay; // 账户可领金额 - 最后赔付金额

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.5 账户可领金额 >=
			 * 最后赔付金额, 则累计领取 = 原值 + 本次赔付;可领金额 = 原值 - 本次赔付
			 * 
			 * 账户可领金额 < 最后赔付金额, 则累计领取 = 原值 + 本次可领;可领金额 = 原值 - 本次可领 = 0
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tClmAccBal >= 0) {
				tLCInsureAccSchema.setSumPaym(tSumPaym - tRealPay); // 累计领取
				tLCInsureAccSchema.setInsuAccGetMoney(tInsuAccGetMoney
						+ tRealPay); // 设置账户可领金额
			} else {
				tLCInsureAccSchema.setSumPaym(tSumPaym - tInsuAccGetMoney);// 累计领取
				tLCInsureAccSchema.setInsuAccGetMoney(tInsuAccGetMoney); // 设置账户可领金额

			}

			tLCInsureAccSchema.setModifyDate(this.CurrentDate);
			tLCInsureAccSchema.setModifyTime(this.CurrentTime);
			tLCInsureAccSaveSet.add(tLCInsureAccSchema);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.6
			 * 查出保险账户分类表信息LCInsureAccClass
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
					.query();

			LCInsureAccClassSet tLCInsureAccClassSaveSet = new LCInsureAccClassSet();
			for (int m = 1; m <= tLCInsureAccClassSet.size(); m++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet
						.get(m);

				if (tClmAccBal >= 0) {
					tLCInsureAccSchema.setSumPaym(tSumPaym - tRealPay); // 累计领取
					tLCInsureAccSchema.setInsuAccGetMoney(tInsuAccGetMoney
							+ tRealPay); // 设置账户可领金额
				} else {
					tLCInsureAccSchema.setSumPaym(tSumPaym - tInsuAccGetMoney);// 累计领取
					tLCInsureAccSchema.setInsuAccGetMoney(tInsuAccGetMoney); // 设置账户可领金额

				}
				tLCInsureAccClassSchema.setModifyDate(this.CurrentDate);
				tLCInsureAccClassSchema.setModifyTime(this.CurrentTime);
				tLCInsureAccClassSaveSet.add(tLCInsureAccClassSchema);
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.7
			 * 对保险帐户表记价履历表LCInsureAccTrace进行操作 加一条正的利息信息 加一条负的本金 + 利息信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

			LCInsureAccTraceSchema tLCInsureAccTraceLPSchema = new LCInsureAccTraceSchema();
			String sLimit = PubFun
					.getNoLimit(tLCInsureAccSchema.getManageCom());
			String tserNo = "CLM" + PubFun1.CreateMaxNo("CLMACC", 10);
			tLCInsureAccTraceLPSchema.setGrpContNo(tLCInsureAccSchema
					.getContNo()); /* 集体合同号码 */
			tLCInsureAccTraceLPSchema.setGrpPolNo(tLCInsureAccSchema
					.getGrpPolNo());/* 集体保单险种号码 */
			tLCInsureAccTraceLPSchema.setContNo(tLCInsureAccSchema.getContNo()); /* 合同号码 */
			tLCInsureAccTraceLPSchema.setPolNo(tLCInsureAccSchema.getPolNo()); /* 保单险种号码 */
			tLCInsureAccTraceLPSchema.setSerialNo(tserNo); /* 流水号 */

			tLCInsureAccTraceLPSchema.setInsuAccNo(tLCInsureAccSchema
					.getInsuAccNo()); /* 保险帐户号码 */
			tLCInsureAccTraceLPSchema.setRiskCode(tLCInsureAccSchema
					.getRiskCode()); /* 险种编码 */
			tLCInsureAccTraceLPSchema.setPayPlanCode("902020"); /* 交费计划编码 */
			tLCInsureAccTraceLPSchema.setOtherNo(mClmNo); /* 对应赔案号 */
			tLCInsureAccTraceLPSchema.setOtherType("5");

			tLCInsureAccTraceLPSchema.setAccAscription("0");
			tLCInsureAccTraceLPSchema.setMoneyType("LP");

			if (tClmAccBal >= 0) {
				tLCInsureAccTraceLPSchema.setMoney(tRealPay);
			} else {
				tLCInsureAccTraceLPSchema.setMoney(tInsuAccGetMoney);
			}

			tLCInsureAccTraceLPSchema.setUnitCount("0");/* 本次单位数 */
			tLCInsureAccTraceLPSchema.setPayDate(tRgtDate);/* 交费日期 */
			tLCInsureAccTraceLPSchema.setState(tLCInsureAccSchema.getState());/* 状态 */
			tLCInsureAccTraceLPSchema.setManageCom(tLCInsureAccSchema
					.getManageCom());
			tLCInsureAccTraceLPSchema.setOperator(tLCInsureAccSchema
					.getOperator());
			tLCInsureAccTraceLPSchema.setMakeDate(this.CurrentDate);
			tLCInsureAccTraceLPSchema.setMakeTime(this.CurrentTime);
			tLCInsureAccTraceLPSchema.setModifyDate(this.CurrentDate);
			tLCInsureAccTraceLPSchema.setModifyTime(this.CurrentTime);
			tLCInsureAccTraceLPSchema.setFeeCode("100001");
			tLCInsureAccTraceLPSchema.setAccAlterNo(mBackNo);
			tLCInsureAccTraceLPSchema.setAccAlterType("2");
			tLCInsureAccTraceSet.add(tLCInsureAccTraceLPSchema);

			mMMap.put(tLCInsureAccClassSaveSet, "DELETE&INSERT");
			mMMap.put(tLCInsureAccTraceSet, "DELETE&INSERT");
		}

		mMMap.put(tLCInsureAccSaveSet, "DELETE&INSERT");

		logger.debug("---------------------------------进行帐户型保额冲减----结束---------------------------------");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－回退过程中－－－－－保额冲减回退－－－－－结束－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－回退过程中－－－－－－终止责任回退－－－－－开始－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 处理3：遍历LLClaimDetail表中每一个给付标志GiveType为0（给付）的保项，然后根据
	 * 给付责任编码到LMDutyGetClm表中去找AfterGet，给付后动作这个信息做如下操作
	 */
	private boolean getDealDutyCode() {
		logger.debug("---------------------------------进行AfterGet给付后动作处理----开始---------------------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 循环保项信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

			LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
			LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmSet();
			String tSql = "";

			tSql = "select * from LMDutyGetClm where 1 = 1 "
					+ " and getdutycode = '"
					+ "?getdutycode?" + "'"
					+ " and GetDutyKind = '"
					+ "?GetDutyKind?" + "'";
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql(tSql);
			sqlbv33.put("getdutycode", tLLClaimDetailSchema.getGetDutyCode());
			sqlbv33.put("GetDutyKind", tLLClaimDetailSchema.getGetDutyKind());
			tLMDutyGetClmSet = tLMDutyGetClmDB.executeQuery(sqlbv33);
			if (tLMDutyGetClmSet == null && tLMDutyGetClmSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLClaimConfirmPassBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询责任给付赔付数据时出错!";
				this.mErrors.addOneError(tError);
				logger.debug("------------------------------------------------------");
				logger.debug("--LLClaimConfirmPassBL.dealData03()--查询责任给付赔付数据时发生错误!"
								+ tSql);
				logger.debug("------------------------------------------------------");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 进行终止判断
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int j = 1; j <= tLMDutyGetClmSet.size(); j++) {

				LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmSet
						.get(j);
				String tAfterGet = StrTool.cTrim(tLMDutyGetClmSchema
						.getAfterGet());

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 AfterGet＝000
				 * 无动作 AfterGet＝001 [审批通过]保额递减，只冲减保额 AfterGet＝003
				 * 无条件销户时,终止该合同(包括所有的险种) AfterGet＝004
				 * 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作 AfterGet＝005 [待定]
				 * AfterGet＝006 [审批通过]终止该责任给付时,终止LCGet的数据 AfterGet＝007
				 * [待定]终止该责任时：根据DutyCode的前六位，在LCDuty表中查找总数，如果与总数相等，
				 * 则终止LCContState表中的状态,终止本险种. AfterGet＝008 终止本险种
				 * 
				 * 
				 * LMRiskApp.SubRiskFlag='S'[附险],LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				if (tAfterGet.equals("006")) {
					setLCGetEndState(tLLClaimDetailSchema);

				}
			}// for end
		}
		logger.debug("---------------------------------进行AfterGet给付后动作处理----结束---------------------------------");
		return true;
	}

	/**
	 * 在LCGet表中找出相应记录并将其GetEndState状态置为1终止
	 */
	private boolean setLCGetEndState(LLClaimDetailSchema pLLClaimDetailSchema) {

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(pLLClaimDetailSchema.getPolNo());
		tLCGetDB.setDutyCode(pLLClaimDetailSchema.getDutyCode());
		tLCGetDB.setGetDutyCode(pLLClaimDetailSchema.getGetDutyCode());
		LCGetSet tLCGetSet = tLCGetDB.query();

		if (tLCGetDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "setLCGetEndState";
			tError.errorMessage = "查询领取项信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLCGetSet.size() != 1) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "setLCGetEndState";
			tError.errorMessage = "查询领取项信息不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCGetSchema tLCGetSchema = new LCGetSchema();
		tLCGetSchema = tLCGetSet.get(1);
		tLCGetSchema.setGetEndState("");
		tLCGetSchema.setOperator(mG.Operator);
		tLCGetSchema.setModifyDate(this.CurrentDate);
		tLCGetSchema.setModifyTime(this.CurrentTime);

		mMMap.put(tLCGetSchema, "UPDATE");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－回退过程中－－－－－－终止责任回退－－－－－结束－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 处理6.合同回退处理
	 */
	private boolean getDealLCContState() {
		logger.debug("---------------------------------进行合同回退处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询理赔合同状态暂存表数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLContStateDB tLLContStateQueryDB = new LLContStateDB();
		tLLContStateQueryDB.setClmNo(mClmNo);
		LLContStateSet tLLContStateQuerySet = tLLContStateQueryDB.query();
		if (tLLContStateQueryDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLContStateQueryDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询理赔合同终止记录暂存表时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 循环理赔合同状态暂存表数据,进行处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCContStateSet tLCContStateSaveSet = new LCContStateSet(); // 合同状态正式表
		LLContStateSet tLLContStateSaveSet = new LLContStateSet(); // 合同状态暂存表
		LCContSet tLCContSaveSet = new LCContSet(); // 合同表
		LCPolSet tLCPolSaveSet = new LCPolSet(); // 保单险种表

		for (int j = 1; j <= tLLContStateQuerySet.size(); j++) {

			LLContStateSchema tLLContStateQuerySchema = tLLContStateQuerySet
					.get(j);

			String tContNo = tLLContStateQuerySchema.getContNo();
			String tPolNo = tLLContStateQuerySchema.getPolNo();
			String tStateReason = StrTool.cTrim(tLLContStateQuerySchema
					.getStateReason());
			String tClmState = StrTool.cTrim(tLLContStateQuerySchema
					.getClmState());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
			 * 查询LCContState表中的数据
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			LCContStateDB tLCContStateDB = new LCContStateDB();
			tLCContStateDB.setContNo(tContNo);
			tLCContStateDB.setPolNo(tPolNo);
			tLCContStateDB.setStateType("Terminate");
			tLCContStateDB.setState("1");
			tLCContStateDB.setStateReason("04");
			LCContStateSet tLCContStateSet = tLCContStateDB.query();
			
			//2009-04-16 zhangzheng 由于合同处理生效是在结案保存时,所以对于已签批确认未给付的案件回退lccontstate表中还不存在终止结论,继续循环下一条记录
			
			if(tLCContStateSet.size()>0)
			{
				if (tLCContStateDB.mErrors.needDealError()
						|| tLCContStateSet.size() != 1) {
					mErrors.addOneError("保单状态信息不唯一，不能执行回退操作!!!");
					return false;
				}
				LCContStateSchema tLCContStateSaveSchema = new LCContStateSchema();
				tLCContStateSaveSchema = tLCContStateSet.get(1);

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
				 * 删除合同状态正式表中旧的终止记录,并插入新的记录
				 * --8--终止的开始时间相同,在本表中已存在,回退时需要替换C表终止原因StateReason
				 * --9--终止的开始时间不相同,在本表中不存在,暂时存放,用于回退时恢复C表数据
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tDeleteSql = "delete from LCContState where 1=1 "
						+ " and ContNo='" + "?ContNo?" + "'" + " and PolNo='" + "?PolNo?"
						+ "'" + " and StateType='Terminate' "
						+ " and StateReason = '04'" + " and state='1'";
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				sqlbv34.sql(tDeleteSql);
				sqlbv34.put("ContNo", tContNo);
				sqlbv34.put("PolNo", tPolNo);
				mMMap.put(sqlbv34, "DELETE");

				if (tClmState.equals("8")) {
					tLCContStateSaveSchema.setStateReason(tStateReason);
					mMMap.put(tLCContStateSaveSchema, "DELETE&INSERT");
				} else if (tClmState.equals("9")) {
					this.mReflections.transFields(tLCContStateSaveSchema,
							tLLContStateQuerySchema);
					mMMap.put(tLCContStateSaveSchema, "DELETE&INSERT");
				}
				
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0
				 * 如果tClmState为8或9，不修改AppFlag标志 因为以前就是终止状态
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tClmState.equals("8") || tClmState.equals("9")) {
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
				 * 更新LCPol的AppFlag置为1 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
				if (tLCPolSchema == null) {
					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				} else {
					tLCPolSchema.setAppFlag("1");
					tLCPolSchema.setModifyDate(CurrentDate);
					tLCPolSchema.setMakeTime(CurrentTime);
					tLCPolSchema.setOperator(mG.Operator);
					tLCPolSaveSet.add(tLCPolSchema);
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0
				 * 更新LCCont的AppFlag置为1 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tMainPolNo = tLCPolSchema.getMainPolNo();
				if (tMainPolNo.equals(tPolNo)) {
					LCContSchema tLCContSchema = new LCContSchema();
					tLCContSchema = mLLClaimPubFunBL.getLCCont(tContNo);
					if (tLCContSchema == null) {
						this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
						return false;
					} else {
						tLCContSchema.setAppFlag("1");
						tLCContSchema.setModifyDate(CurrentDate);
						tLCContSchema.setMakeTime(CurrentTime);
						// tLCContSchema.setOperator(mG.Operator);
						tLCContSaveSet.add(tLCContSchema);
					}
				}
			}

		}

		mMMap.put(tLCContSaveSet, "UPDATE");
		mMMap.put(tLCPolSaveSet, "UPDATE");

		logger.debug("---------------------------------进行合同回退处理----结束---------------------------------");

		return true;
	}

	/**
	 * 处理7.豁免处理: 将LLExempt表的FreeFlag[免交标志], FreeStartDate[免交起期], FreeEndDate
	 * [免交止期],更新到LCPrem表中对应的数据
	 */
	private boolean getDealLLExempt() {
		logger.debug("---------------------------------进行豁免回退处理----开始---------------------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询理赔处理的豁免信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLExemptDB tLLExemptDB = new LLExemptDB();
		tLLExemptDB.setClmNo(mClmNo);
		LLExemptSet tLLExemptSet = new LLExemptSet();
		tLLExemptSet = tLLExemptDB.query();
		if (tLLExemptDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保费豁免记录时出错!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据查询理赔处理的豁免信息对LCPrem表进行回退操作
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCPremSet tLCPremSet = new LCPremSet();
		for (int j = 1; j <= tLLExemptSet.size(); j++) {

			LLExemptSchema tLLExemptSchema = tLLExemptSet.get(j);
			String tFreeFlag = StrTool.cTrim(tLLExemptSchema.getFreeFlag());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
			 * 如果免交标志不为1[表示免交]，继续循环 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!tFreeFlag.equals("1")) {
				continue;
			}

			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(tLLExemptSchema.getPolNo());
			tLCPremDB.setDutyCode(tLLExemptSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(tLLExemptSchema.getPayPlanCode());

			if (tLCPremDB.getInfo() == false) {
				this.mErrors.addOneError("进行豁免处理时查询缴费信息失败!");
				return false;
			}

			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setSchema(tLCPremDB.getSchema());

			tLCPremSchema.setFreeFlag("0");
			tLCPremSchema.setFreeRate("0");
			tLCPremSchema.setFreeStartDate("");
			tLCPremSchema.setFreeEndDate("");
			tLCPremSchema.setOperator(mG.Operator);
			tLCPremSchema.setModifyDate(CurrentDate);
			tLCPremSchema.setMakeTime(CurrentTime);
			tLCPremSet.add(tLCPremSchema);
		}

		mMMap.put(tLCPremSet, "DELETE&INSERT");

		logger.debug("---------------------------------进行豁免回退处理----结束---------------------------------");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－理赔正式回退－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

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
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
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
			CError.buildErr(this, "数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		LLCaseBackSchema tLLCaseBackSchema = new LLCaseBackSchema();

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		String tClmNo = "90000034750"; /* 赔案号 */
		// String tBackNo = 7 + PubFun1.CreateMaxNo("BackNo","10"); //生成修改顺序号
		String tBackNo = "7100000000074";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tTransferData.setNameAndValue("BackNo", tBackNo);

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		tVData.addElement(tLLCaseBackSchema);

		LLClaimConfirmPassReturnBL tLLClaimConfirmPassReturnBL = new LLClaimConfirmPassReturnBL();
		tLLClaimConfirmPassReturnBL.submitData(tVData, "");

		int n = tLLClaimConfirmPassReturnBL.mErrors.getErrorCount();
		logger.debug("---------------------------------------------------------------------------");
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content
					+ "原因是: "
					+ tLLClaimConfirmPassReturnBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
		logger.debug("---------------------------------------------------------------------------");

	}

}
