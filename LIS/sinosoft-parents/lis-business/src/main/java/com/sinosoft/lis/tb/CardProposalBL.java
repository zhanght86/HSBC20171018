package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCInsuredBL;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CardProposalBL {
private static Logger logger = Logger.getLogger(CardProposalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap mapContBL = new MMap();
	private MMap mapInsuredBL = new MMap();
	private MMap mapLcpolBL = new MMap();
	private MMap total = new MMap();
	private MMap map = new MMap();
	private String contno;
	private String prtno;
	private String cValidate;
	private String enddate;
	private String agentcode;
	private String polno;
	private String mPrtNo; //用于控制并发 
	private String mRiskCode;
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData rInputData; // 被保险人
	private VData aInputData; // 合同信息与投保人
	private VData dInputData; // 要删除的数据
	private VData iInputData; // 要删除的数据
	/** 数据操作字符串 */
	private String mOperate;
	private String mSamePersonFlag; // 所选投保人和被保人是否是同一人 add by zhangxing
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	/** 业务处理相关变量 */
	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 其他相关表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema(); // 投保人信息
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); // 被保险人
	private LCInsuredSchema mNewLCInsuredSchema = new LCInsuredSchema(); // 被保险人
	private LCPolSchema mLCPolSchema = new LCPolSchema(); // 险种信息
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema(); // 投保人
	private LCAddressSchema rLCAddressSchema = new LCAddressSchema(); // 被保险人
	private LCAccountSchema mLCAccountSchema = new LCAccountSchema(); // 投保人信息
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet(); // 投保人信息
	private LACommisionDetailSet mLACommisionDetailSet = new LACommisionDetailSet(); // 投保人信息
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema(); // 客户表
	private LCInsuredDB mOLDLCInsuredDB = new LCInsuredDB(); // 被保险人
	private LCCustomerImpartDetailSet mLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet(); // 被保险人
	private LCBnfSet mLCBnfSet = new LCBnfSet(); // 受益人信息
	private LCDutySchema mLCDutySchema = new LCDutySchema();// 险种信息
	private boolean ISPlanRisk = false;
	
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC0047")) {
			return false;
		}
		return true;
	}

	public CardProposalBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		dInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 并发控制 -- 获取操作号
		if(!getBasicInputData(cInputData)){
			return false;
		}
		logger.debug("操作-----" + cOperate);
		try {
		if (!lockNo(mPrtNo)) {
				logger.debug("印刷号：["+mPrtNo+"]锁定号码失败!");
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				//tLockFlag = false;
				//mPubLock.unLock();
				//continue;
				return false;
			}
			if (cOperate != "DELETE||CONT") {
				// 得到外部传入的数据，将数据备份到本类中
				if (!getInputData(cInputData)) {
					return false;
				}

				
				// 进行业务处理
				if (!dealData()) {
					return false;
				}
				// 准备往后台的数据
				this.prepareOutputData();
				//090824 lixiang  新保险法需求  如果险种是141812 则根据最新需求去校验被保人只能有一份该险种
				//090827 lixiang  311603规则同141812
				if("141812".equals(mRiskCode)||"311603".equals(mRiskCode)){
					if(!checkMult(mNewLCInsuredSchema,mRiskCode)){
						return false;
					}
				}
				
			} else {
				if (!this.delData(dInputData)) {
					return false;
				}
			}
			
			PubSubmit tPubSubmit = new PubSubmit();
			logger.debug("Start tPRnewManualDunBLS Submit...");
			
			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			mPubLock.unLock();
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private void prepareOutputData() {
		try{
			logger.debug("操作方式===== in cardproposalbl=" + mOperate);
			mInputData.clear();
			total.add(mapContBL);
			total.add(mapInsuredBL);
			if(this.mOperate.equals("UPDATE||CONT")){
				//删除险种相关表
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql("delete from lcbnf where polno in (select polno from lcpol where prtno ='"+"?prtno?"+"')");
				sqlbv.put("prtno", prtno);
				total.put(sqlbv, "DELETE");
				
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql("delete from lcprem where polno in (select polno from lcpol where prtno ='"+"?prtno?"+"')");
				sqlbv1.put("prtno", prtno);
				total.put(sqlbv1, "DELETE");
				
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql("delete from lcget where polno in (select polno from lcpol where prtno ='"+"?prtno?"+"')");
				sqlbv2.put("prtno", prtno);
				total.put(sqlbv2, "DELETE");
				
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv1.sql("delete from lcduty where polno in (select polno from lcpol where prtno ='"+"?prtno?"+"')");
				sqlbv1.put("prtno", prtno);
				total.put(sqlbv3, "DELETE");
				
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv1.sql("delete from lcpol where polno ='"+"?prtno?"+"'");
				sqlbv1.put("prtno", prtno);
				total.put(sqlbv4, "DELETE");
			}
				
			total.add(mapLcpolBL);
			
			// mInputData.add(mapContBL);
			// mInputData.add(mapInsuredBL);
			// mInputData.add(mapLcpolBL);
			mInputData.add(total);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private boolean prepareOutputData(VData vData) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		ContBL mContBL = new ContBL();
		String tOperate="";
		if(this.mOperate.equals("UPDATE||CONT")){
			tOperate ="UPDATE||CONT";
		}else{
			tOperate = "INSERT||CONT";
		}
		if (!mContBL.submitData(mInputData, tOperate)) {

			this.mErrors.copyAllErrors(mContBL.mErrors);
			return false;

		} else {
			logger.debug("合同信息保存成功！");
			mapContBL.add((MMap) mContBL.getCardResult().getObjectByObjectName(
					"MMap", 0));
			aInputData = mContBL.getResult();
		}

		LCContSchema kLCContSchema = new LCContSchema();
		kLCContSchema.setSchema((LCContSchema) aInputData
				.getObjectByObjectName("LCContSchema", 0));
		logger.debug("代理机构=====" + kLCContSchema.getManageCom());
		logger.debug("投保人姓名 in=====" + kLCContSchema.getAppntNo());
		/** @todo 为合同表部分字段赋值（其中涉及到复核标志、核保标志等） */
		kLCContSchema.setGrpContNo("00000000000000000000");
		kLCContSchema.setUWFlag("9");
		kLCContSchema.setUWDate(PubFun.getCurrentDate());
		kLCContSchema.setUWOperator(mGlobalInput.Operator);
		kLCContSchema.setUWTime(PubFun.getCurrentTime());
		kLCContSchema.setApproveCode(mGlobalInput.Operator);
		kLCContSchema.setApproveFlag("9");
		kLCContSchema.setApproveDate(PubFun.getCurrentDate());
		kLCContSchema.setApproveTime(PubFun.getCurrentTime());

		// add by zhangxing 如果投保人和被保人是同一人，应该把投保人的客户号码传给被保险人
		logger.debug("mSamePersonFlag:" + mSamePersonFlag);
		String tAppntNo = kLCContSchema.getAppntNo();
		if (mSamePersonFlag != null && mSamePersonFlag.equals("1")) {
			// 取出投保人客户号码

			// 将投保人客户号码付给被保险人
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLCInsuredSchema = (LCInsuredSchema) mInputData
					.getObjectByObjectName("LCInsuredSchema", 0);

			tLCInsuredSchema.setInsuredNo(tAppntNo);
			// 对客户表进行相同的操作
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLDPersonSchema = (LDPersonSchema) mInputData
					.getObjectByObjectName("LDPersonSchema", 0);
			tLDPersonSchema.setCustomerNo(tAppntNo);
			logger.debug("结束更新程序");
		}

		// LCAddressSchema tt2 = new LCAddressSchema();
		// tt2.setSchema((LCAddressSchema) aInputData.
		// getObjectByObjectName("LCAddressSchema", 0));

		/** @todo 将VData中多余的LCContSchema... */
		mInputData.remove(0);
		// if(!mInputData.removeElement(LCContScema))
		// {
		// logger.debug("删除VData中多余的LCContSchema失败...");
		// }
		mInputData.add(kLCContSchema);
		// mInputData.add(tt2);
		String roperator="";
		if(this.mOperate.equals("UPDATE||CONT")){
			roperator="UPDATE||CONTINSURED";
		}else{
			roperator = "INSERT||CONTINSURED";
		}
		// if(mOperate == "INSERT||CONT")
		// {
		// roperator = "INSERT||CONTINSURED";
		// }
		// else
		// {
		// roperator = "DELETE||CONTINSURED";
		// }
		ContInsuredBL tContInsuredBL = new ContInsuredBL();
		tContInsuredBL.submitData(mInputData, roperator);
		if (tContInsuredBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tContInsuredBL.mErrors);
			return false;

		} else {
			logger.debug("被保险人信息保存成功！");
			mapInsuredBL.add((MMap) tContInsuredBL.getCardResult()
					.getObjectByObjectName("MMap", 0));
			rInputData = tContInsuredBL.getResult();
		}
		// LCInsuredSchema tt3 = new LCInsuredSchema();
		LCInsuredBL mLCInsuredBL = new LCInsuredBL();
		mLCInsuredBL.setSchema((LCInsuredSchema) rInputData
				.getObjectByObjectName("LCInsuredSchema", 0));
		mNewLCInsuredSchema = mLCInsuredBL.getSchema();
		// 如果投保人的客户号码为空，将appntno得值付其之
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntSchema = (LCAppntSchema) mInputData.getObjectByObjectName(
				"LCAppntSchema", 0);
		tLCAppntSchema.setAppntNo(tAppntNo);

		mInputData.add(mLCInsuredBL);

		logger.debug("被保人年龄==== in cardproposalbl=="
				+ mLCInsuredBL.getBirthday());
		logger.debug("被保人号码==== in cardproposalbl=="
				+ mLCInsuredBL.getInsuredNo());
		logger.debug("被保人姓名==== in cardproposalbl=="
				+ mLCInsuredBL.getName());
		// String poperator = "";
		// if(mOperate == "INSERT||CONT")
		// {
		// poperator = "INSERT||PROPOSAL";
		// }
		// else
		// {
		// poperator = "DELETE||PROPOSAL";
		// }
		// 如果是套餐险种，则不用进行险种信息处理，直接在被保人中处理
		if (!ISPlanRisk) {
			String poperator="";
			if(this.mOperate.equals("UPDATE||CONT")){
				poperator="UPDATE||PROPOSAL";
			}else{
				poperator = "INSERT||PROPOSAL";
			}
			ProposalBL tProposalBL = new ProposalBL();
			tProposalBL.submitData(mInputData, poperator);
			// logger.debug("mInputData.getObjectByObjectName:"+mInputData.getObjectByObjectName("LCBnfSet",0));
			// mLCBnfSet.set((LCBnfSet) mInputData.
			// getObjectByObjectName("LCBnfSet",
			// 0));

			// logger.debug("mLCBnfSet.get(1).getname:"+
			// mLCBnfSet.get(1).getName());
			if (tProposalBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tProposalBL.mErrors);
				return false;
			} else {
				logger.debug("险种信息保存成功！");
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema =  (LCPolSchema) tProposalBL.getCardResult().getObjectByObjectName("LCPolSchema", 0);
				polno = tLCPolSchema.getPolNo();
				cValidate = tLCPolSchema.getCValiDate();
				enddate = tLCPolSchema.getEndDate();
				agentcode = tLCPolSchema.getAgentCode();
				mapLcpolBL.add((MMap) tProposalBL.getCardResult()
						.getObjectByObjectName("MMap", 0));
			}
		} else {
			GrpPlanRiskDispatchBL tGrpPlanRiskDispatchBL = new GrpPlanRiskDispatchBL();
			if (!tGrpPlanRiskDispatchBL.submitData(mInputData, "")) {
				this.mErrors.copyAllErrors(tGrpPlanRiskDispatchBL.mErrors);
				return false;
			}
			mapLcpolBL.add((MMap) tGrpPlanRiskDispatchBL.getResult()
					.getObjectByObjectName("MMap", 0));
//			logger.debug("险种信息保存成功！");
//			LCPolSchema tLCPolSchema = new LCPolSchema();
//			tLCPolSchema =  (LCPolSchema) tProposalBL.getCardResult().getObjectByObjectName("LCPolSchema", 0);
//			polno = tLCPolSchema.getPolNo();
//			cValidate = tLCPolSchema.getCValiDate();
//			enddate = tLCPolSchema.getEndDate();
//			agentcode = tLCPolSchema.getAgentCode();
		}

		return true;
	}
	private boolean getBasicInputData(VData cInputData){
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));
		mPrtNo =mLCContSchema.getPrtNo();
		if(mPrtNo==null||"".equals(mPrtNo)){
			CError.buildErr(this, "印刷号为空！");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mGlobalInput出错!";
			this.mErrors.addOneError(tError);

		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mTransferData出错!";
			this.mErrors.addOneError(tError);

		}

		// 是否为套餐险种
		String tISPlanRisk = (String) mTransferData
				.getValueByName("ISPlanRisk");
		if (tISPlanRisk != null && tISPlanRisk.equals("Y")) {
			ISPlanRisk = true;
		}

		mSamePersonFlag = (String) mTransferData
				.getValueByName("samePersonFlag");
		if (mSamePersonFlag == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintAgentNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));
		if (mLCContSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLCContSchema出错!";
			this.mErrors.addOneError(tError);

		}
		contno = mLCContSchema.getContNo();
		prtno = mLCContSchema.getPrtNo();
		//
		mLCInsuredSchema.setSchema((LCInsuredSchema) cInputData
				.getObjectByObjectName("LCInsuredSchema", 0));
		if (mLCInsuredSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLCAddressSchema出错!";
			this.mErrors.addOneError(tError);

		}

		mLDPersonSchema.setSchema((LDPersonSchema) cInputData
				.getObjectByObjectName("LDPersonSchema", 0));
		if (mLDPersonSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLDPersonSchema出错!";
			this.mErrors.addOneError(tError);

		}
		//
		// mLCAccountSchema.setSchema((LCAccountSchema) cInputData.
		// getObjectByObjectName("LCAccountSchema",
		// 0));
		// if (mLCAccountSchema == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "CardBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "处理mLCAccountSchema出错!";
		// this.mErrors.addOneError(tError);
		//
		// }
		//
		// LCBnfSet mLCBnfSet = new LCBnfSet();
		// mLCBnfSet.set(LCBnfSet)
		// cInputData.getObjectByObjectName("LCBnfSet",0));
		// if (mLCBnfSet == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "CardBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "处理mLCCustomerImpartSet出错!";
		// this.mErrors.addOneError(tError);
		//
		// }
		//
		mLACommisionDetailSet.set((LACommisionDetailSet) cInputData
				.getObjectByObjectName("LACommisionDetailSet", 0));
		if (mLACommisionDetailSet == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLACommisionDetailSet出错!";
			this.mErrors.addOneError(tError);

		}
		
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName("LCPolSchema", 0);
		mRiskCode = mLCPolSchema.getRiskCode();
		if(mRiskCode==null||"".equals(mRiskCode)){
			logger.debug("险种编码为空！");
		}
		//
		// aInputData.add(mGlobalInput);
		// aInputData.add(mTransferData);
		// aInputData.add(mLCContSchema);
		// aInputData.add(mLCAddressSchema);
		// aInputData.add(mLCAppntSchema);
		// aInputData.add(mLCAccountSchema);
		// aInputData.add(mLCCustomerImpartSet);
		// aInputData.add(mLACommisionDetailSet);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean delData(VData dInputData) {
		logger.debug("in delData-------start");
		// 删除合同表
		mLCContSchema.setSchema((LCContSchema) dInputData
				.getObjectByObjectName("LCContSchema", 0));
		if (mLCContSchema != null) {
			map.put(mLCContSchema, "DELETE");
		}
		// 删除投保人表
		mLCAppntSchema.setSchema((LCAppntSchema) dInputData
				.getObjectByObjectName("LCAppntSchema", 0));
		if (mLCAppntSchema != null) {
			map.put(mLCAppntSchema, "DELETE");
		}
		// 删除被保人表
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("delete from LCInsured where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv, "DELETE");
		// 删除连带被保人表
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("delete from lcinsuredrelated where  polno in (Select polno From lcpol Where contno ='"
				+ "?ContNo?" + "')");
		sqlbv1.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv1, "DELETE");
		map.put(sqlbv1, "DELETE");
		// 删除险种信息表
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("delete from LCPol where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv2.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv2, "DELETE");
		// 删除受益人表
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("delete from LCBnf where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv3.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv3, "DELETE");
		// 删除险种责任表
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("delete from LCDuty where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv4.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv4, "DELETE");
		// 删除保费项表表
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("delete from LCPrem where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv5.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv5, "DELETE");
		// 删除保费项表和客户帐户表的关联表
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("delete from lcpremtoacc where polno in (select polno from lcpol where contno='"
				+ "?ContNo?" + "')");
		sqlbv6.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv6, "DELETE");
		// 删除领取项表表
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("delete from LCGet where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv7.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv7, "DELETE");
		// 客户告知
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("delete from lccustomerimpart where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv8.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv8, "DELETE");
		// 客户告知参数
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("delete from lccustomerimpartparams where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv9.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv9, "DELETE");
		// 删除保单发佣分配表
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("delete from LACommisionDetail where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv10.put("ContNo", mLCContSchema.getContNo());
		map.put(sqlbv10, "DELETE");

		logger.debug("in delData-------end");
		mInputData.clear();
		mInputData.add(map);
		return true;
	}

	
	/**
	 * 090824 根据最新保险法的需求 校验份数的规则改为如下：
	 * (1)证件号码为身份证号时：被保人姓名、性别、出生日期一致、且证件号码为15位或18位身份证号码一致时
	 * ，校验为同一客户。（“证件号码”一致性的校验规则：“证件号码”15位或18位与原有客户资料不匹配时，
	 * 系统自动将18位的号码转为15位后，进行校验；因“证件号码”中含有英文字母时，校验时不区分大小写。）
     * (2)证件号码不为身份证号时：被保人姓名、性别、出生日期一致，证件号码一致，且业务员代码一致时，
     * 校验为同一客户。
     * 
     * @return true-可以投保 false-不能投保
	 * */
	private boolean checkMult(LCInsuredSchema tLCInsuredSchema,String tRiskCode){
		//证件类型是身份证的情况
		String tResult = "";
		String tSql = "";
		
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		//证件类型是身份证的情况
		if("0".equals(tLCInsuredSchema.getIDType())){
			//身份证号是18位的情况
			if(StrTool.cTrim(tLCInsuredSchema.getIDNo()).length()==18){
				tSql ="select 1 from dual where (select (case when sum(a.mult) is null then 0 else sum(a.mult) end) from lcpol a where "
							+ " exists (select 1 from lcinsured b where b.insuredno=a.insuredno"
							+ " and b.name='"+"?name?"
							+ "' and b.sex='"+"?sex?"
							+ "' and b.birthday='"+"?birthday?"+"'" 
							+ " and (lower(idno)='"+"?idno1?"
							+ "' or lower(idno)='"+"?idno2?"+"'))"
							+ " and a.polno <> '"+"?polno?"+"'"
							+ " and a.riskcode = '"+"?tRiskCode?"+"'"
							+ " and ((CValidate <= '"+"?cValidate?"+"' and EndDate > '"+"?cValidate?"+"') or"
							+ " (CValidate < '"+"?enddate?"+"' and EndDate >= '"+"?enddate?"+"'))"
							+ " and a.uwflag not in ('1', '2', 'a')"
							+") >0";
				sqlbv.sql(tSql);
				sqlbv.put("name", tLCInsuredSchema.getName());
				sqlbv.put("sex", tLCInsuredSchema.getSex());
				sqlbv.put("birthday", tLCInsuredSchema.getBirthday());
				sqlbv.put("idno1", get15IDNo(tLCInsuredSchema.getIDNo()));
				sqlbv.put("idno2", tLCInsuredSchema.getIDNo());
				sqlbv.put("polno", polno);
				sqlbv.put("tRiskCode", tRiskCode);
				sqlbv.put("cValidate", cValidate);
				sqlbv.put("enddate", enddate);
			} else {  //身份证号是15位的情况
				tSql = "select 1 from dual where (select (case when sum(a.mult) is null then 0 else sum(a.mult) end) from lcpol a where "
							+ " exists (select 1 from lcinsured b where b.insuredno=a.insuredno"
							+ " and b.name='"+"?name?"
							+ "' and b.sex='"+"?sex?"
							+ "' and b.birthday='"+"?birthday?"+"'" 
							+ " and (lower(idno)='"+"?idno1?"
							+ "' or lower(idno)='"+"?idno2?"+"'))"
							+ " and a.polno <> '"+"?polno?"+"'"
							+ " and a.riskcode = '"+"?tRiskCode?"+"'"
							+ " and ((CValidate <= '"+"?cValidate?"+"' and EndDate > '"+"?cValidate?"+"') or"
							+ " (CValidate < '"+"?enddate?"+"' and EndDate >= '"+"?enddate?"+"'))"
							+ " and a.uwflag not in ('1', '2', 'a')"
							+") >0";
				sqlbv.sql(tSql);
				sqlbv.put("name", tLCInsuredSchema.getName());
				sqlbv.put("sex", tLCInsuredSchema.getSex());
				sqlbv.put("birthday", tLCInsuredSchema.getBirthday());
				sqlbv.put("idno1", get18IDNo(tLCInsuredSchema.getIDNo(),tLCInsuredSchema.getBirthday()));
				sqlbv.put("idno2", tLCInsuredSchema.getIDNo());
				sqlbv.put("polno", polno);
				sqlbv.put("tRiskCode", tRiskCode);
				sqlbv.put("cValidate", cValidate);
				sqlbv.put("enddate", enddate);
			}
		} else { //证件类型不是身份证的情况
			tSql = "select 1 from dual where (select (case when sum(a.mult) is null then 0 else sum(a.mult) end) from lcpol a where" 
					+ " exists (select 1 from lcinsured b where b.insuredno=a.insuredno"
					+ " and b.name='"+"?name?"+"'"
					+ " and b.sex='"+"?sex?"+"'"
					+ " and b.birthday='"+"?birthday?"+"' "
					+ " and idno='"+"?idno?"+"')"
					+ " and a.agentcode='"+"?agentcode?"+"'"
					+ " and a.polno <> '"+"?polno?"+"'"
					+ " and a.riskcode = '"+"?tRiskCode?"+"'"
					+ " and ((CValidate <= '"+"?cValidate?"+"' and EndDate > '"+"?cValidate?"+"') or"
					+ "(CValidate < '"+"?enddate?"+"' and EndDate >= '"+"?enddate?"+"'))"
					+" and a.uwflag not in ('1', '2', 'a')"
					+") >0";
			sqlbv.sql(tSql);
			sqlbv.put("name", tLCInsuredSchema.getName());
			sqlbv.put("sex", tLCInsuredSchema.getSex());
			sqlbv.put("birthday", tLCInsuredSchema.getBirthday());
			sqlbv.put("idno", tLCInsuredSchema.getIDNo());
			sqlbv.put("agentcode", agentcode);
			sqlbv.put("polno", polno);
			sqlbv.put("tRiskCode", tRiskCode);
			sqlbv.put("cValidate", cValidate);
			sqlbv.put("enddate", enddate);
		}
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv);
		if(tResult!=null&&!tResult.equals("")&&Integer.parseInt(tResult)>0){
			CError.buildErr(this, "本保险每一被保险人限购一份");
			return false;
		}
		return true;
	}
	
	/**
	 * 18位身份证号转15位
	 * */
	public String get15IDNo(String IDNo) {
		String str = "";
		str += IDNo.substring(0, 6);
		str += IDNo.substring(8, 17);
		return str;
	}
	
	/**
	 * 15位身份证号转18位
	 * @param  IDNo-证件号码    Birthday-出生日期
	 * 
	 * @return 证件号码
	 * */
	public static String get18IDNo(String IDNo, String Birthday) {
		if (IDNo.length() == 18) {
			if (IDNo.endsWith("x"))
				IDNo = IDNo.substring(0, 17) + "X";
			return IDNo;
		}
		String str = "";
		str += IDNo.substring(0, 6);
		if (Birthday.length() == 10) {
			str += Birthday.substring(0, 2);
		} else
			str += "19";
		str += IDNo.substring(6, 15);
		int n = 0;
		int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2 };
		for (int i = 0; i < 17; i++) {
			n += Integer.parseInt(str.substring(i, i + 1)) * weight[i];
		}
		n %= 11;
		if (n == 0)
			str += "1";
		else if (n == 1)
			str += "0";
		else if (n == 2)
			str += "X";
		else if (n == 3)
			str += "9";
		else if (n == 4)
			str += "8";
		else if (n == 5)
			str += "7";
		else if (n == 6)
			str += "6";
		else if (n == 7)
			str += "5";
		else if (n == 8)
			str += "4";
		else if (n == 9)
			str += "3";
		else if (n == 10)
			str += "2";
		return str;
	}
	
	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86320501";
		tG.Operator = "001";
		tG.ComCode = "86320501";
		
		// 合同信息
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setProposalContNo("00200509130501");
		tLCContSchema.setPrtNo("00200509130501");
		tLCContSchema.setManageCom("86320501");
		tLCContSchema.setSellType("01");
		tLCContSchema.setAgentCode("02295060");
		tLCContSchema.setAgentGroup("020000001781");
		tLCContSchema.setAgentCom("");
		tLCContSchema.setSaleChnl("1");
		// 投保人信息
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		
		tLCAppntSchema.setAppntName("xx");
		tLCAppntSchema.setAppntSex("1");
		tLCAppntSchema.setAppntBirthday("1981-01-01");
		tLCAppntSchema.setIDType("8");
		tLCAppntSchema.setOccupationCode("Z008002");
		tLCAppntSchema.setOccupationType("1");
		tLCAppntSchema.setIDNo("123");
		tLCAppntSchema.setOccupationType("1");
		tLCAppntSchema.setOccupationCode("B001001");
		// 地址信息
		LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		mLCAddressSchema.setProvince("213");
		mLCAddressSchema.setCity("213");
		mLCAddressSchema.setCounty("313251");
		
		// 被保险人
		LCInsuredSchema tCInsuredSchema = new LCInsuredSchema();
		tCInsuredSchema.setInsuredNo("");
		tCInsuredSchema.setName("xx");
		tCInsuredSchema.setSex("1");
		tCInsuredSchema.setOccupationCode("Z008002");
		tCInsuredSchema.setOccupationType("1");
		tCInsuredSchema.setBirthday("1981-01-01");
		tCInsuredSchema.setIDType("8");
		tCInsuredSchema.setIDNo("123");
		tCInsuredSchema.setOccupationType("1");
		tCInsuredSchema.setOccupationCode("B001001");
		
		LCAccountSchema mLCAccountSchema = new LCAccountSchema();
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
		LACommisionDetailSet mLACommisionDetailSet = new LACommisionDetailSet();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LCInsuredDB tOLDLCInsuredDB = new LCInsuredDB();
		
		// tLDPersonSchema.setCustomerNo("");
		tLDPersonSchema.setName("xx");
		tLDPersonSchema.setSex("1");
		tLDPersonSchema.setBirthday("1981-01-01");
		tLDPersonSchema.setIDType("8");
		tLDPersonSchema.setIDNo("123");
		tLDPersonSchema.setOccupationType("1");
		tLDPersonSchema.setOccupationCode("B001001");
		
		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		tLCBnfSchema.setBnfType("1");
		tLCBnfSchema.setName("xx");
		tLCBnfSchema.setIDType("8");
		tLCBnfSchema.setBnfGrade("1");
		tLCBnfSchema.setIDNo("123");
		tLCBnfSet.add(tLCBnfSchema);
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
		tTransferData.setNameAndValue("GrpNo", "00000000000000000000");
		tTransferData.setNameAndValue("mark", "card");
		
		tTransferData.setNameAndValue("ContNo", "00200509130001");
		tTransferData.setNameAndValue("FamilyType", "0"); // 家庭单标记
		tTransferData.setNameAndValue("ContType", "1"); // 团单，个人单标记
		tTransferData.setNameAndValue("PolTypeFlag", "0"); // 无名单标记
		tTransferData.setNameAndValue("InsuredPeoples", "1"); // 被保险人人数
		// tTransferData.setNameAndValue("InsuredAppAge",
		// request.getParameter("InsuredAppAge")); //被保险人年龄
		tTransferData.setNameAndValue("SequenceNo", "00"); // 内部客户号
		
		// tTransferData.setNameAndValue("getIntv",
		// request.getParameter("getIntv")); //领取间隔（方式）
		// tTransferData.setNameAndValue("GetDutyKind",
		// request.getParameter("GetDutyKind"));
		
		tTransferData.setNameAndValue("samePersonFlag", "1"); // 投保人同被保人标志
		
		tTransferData.setNameAndValue("deleteAccNo", "1");
		tTransferData.setNameAndValue("ChangePlanFlag", "1");
		
		LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
		tLACommisionDetailSchema.setGrpContNo("00200509130501");
		tLACommisionDetailSchema.setAgentCode("02295060");
		tLACommisionDetailSchema.setBusiRate(1);
		tLACommisionDetailSchema.setAgentGroup("020000001781");
		tLACommisionDetailSchema.setPolType("0");
		
		mLACommisionDetailSet.add(tLACommisionDetailSchema);
		
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setProposalNo("00200509130501");
		tLCPolSchema.setPrtNo("00200509130501");
		tLCPolSchema.setManageCom("86320501");
		tLCPolSchema.setSaleChnl("1");
		tLCPolSchema.setAgentCom("");
		tLCPolSchema.setAgentType("");
		tLCPolSchema.setAgentCode("02295060");
		tLCPolSchema.setAgentGroup("020000001781");
		tLCPolSchema.setOccupationType("1");
		tLCPolSchema.setHandler("hyq");
		tLCPolSchema.setAgentCode1("");
		tLCPolSchema.setInsuredAppAge(""); // 被保人投保年龄
		tLCPolSchema.setInsuredPeoples("1"); // 被保人人数
		tLCPolSchema.setPolTypeFlag(""); // 保单类型标记
		tLCPolSchema.setContNo("00200509130501");
		tLCPolSchema.setGrpContNo("000000000000000000");
		tLCPolSchema.setFirstPayDate("");
		tLCPolSchema.setAgentPayFlag("");
		tLCPolSchema.setAgentGetFlag("");
		tLCPolSchema.setRemark("");
		tLCPolSchema.setMasterPolNo("00200509130501");
		
		tLCPolSchema.setPolApplyDate("2005-9-28");
		
		tLCPolSchema.setRiskCode("00397000");
		tLCPolSchema.setRiskVersion("");
		tLCPolSchema.setCValiDate("2005-9-28"); // 保单生效日期
		tLCPolSchema.setHealthCheckFlag(""); // 是否体检件
		tLCPolSchema.setPayLocation(""); // 收费方式
		tLCPolSchema.setLiveGetMode(""); // 生存保险金领取方式
		tLCPolSchema.setMult(1); // 份数
		tLCPolSchema.setPrem(""); // 保费
		tLCPolSchema.setAmnt(""); // 保额
		
		tLCPolSchema.setRnewFlag("-1");
		
		tLCPolSchema.setSpecifyValiDate("N");
		tLCPolSchema.setFloatRate(""); //
		tLCPolSchema.setBonusMan(""); // 红利领取人
		tLCPolSchema.setBonusGetMode(""); // 红利领取方式
		tLCPolSchema.setStandbyFlag1(""); // 内部分类
		tLCPolSchema.setStandbyFlag2("");
		tLCPolSchema.setStandbyFlag3(""); // 打印标志printflag
		tLCPolSchema.setManageFeeRate(""); // 管理费比例
		tLCPolSchema.setKeepValueOpt("");
		// ///////////责任信息//////////////////////
		// 以下页面信息未整理
		LCDutySchema tLCDutySchema = new LCDutySchema();
		tLCDutySchema.setPayIntv("0"); // 交费方式
		tLCDutySchema.setInsuYear(""); // 保险期间
		tLCDutySchema.setInsuYearFlag("");
		tLCDutySchema.setPayEndYear(""); // 交费年期
		tLCDutySchema.setPayEndYearFlag("");
		tLCDutySchema.setGetYear(""); // 年金开始领取年龄
		tLCDutySchema.setGetYearFlag("");
		tLCDutySchema.setGetStartType("");
		tLCDutySchema.setCalRule("");
		tLCDutySchema.setFloatRate("");
		tLCDutySchema.setPremToAmnt("");
		tLCDutySchema.setStandbyFlag1("");
		tLCDutySchema.setStandbyFlag2("");
		tLCDutySchema.setStandbyFlag3("");
		tLCDutySchema.setPrem("");
		tLCDutySchema.setAmnt("");
		tLCDutySchema.setMult(1);
		tLCDutySchema.setGetLimit("");
		tLCDutySchema.setGetRate("");
		tLCDutySchema.setSSFlag("");
		tLCDutySchema.setPeakLine("");
		
		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(tCInsuredSchema);
		tVData.add(tLCPolSchema);
		tVData.add(tLCDutySchema);
		tVData.add(tLCAppntSchema);
		tVData.add(tLCBnfSet);
		tVData.addElement(tG);
		tVData.add(mLCAddressSchema);
		tVData.add(tLDPersonSchema);
		tVData.add(mLCAccountSchema);
		tVData.add(mLCCustomerImpartSet);
		tVData.add(mLACommisionDetailSet);
		tVData.add(tTransferData);
		tVData.add(tOLDLCInsuredDB);
		
		CardProposalBL tCardProposalBL = new CardProposalBL();
		tCardProposalBL.submitData(tVData, "");
		
	}
}
