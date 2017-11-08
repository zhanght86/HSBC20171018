package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorPTDetailBL {
private static Logger logger = Logger.getLogger(PEdorPTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private Reflections mRef = new Reflections();

	/** 传入数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private double mGetMoney = 0.0; // 应退

	private EdorCalZT mEdorCalZT;
	private double mContCashValue = 0.0; // 现金价值 contno层
	private double mCashValue = 0.0; // 现金价值 当前处理险种的polno层
	private double mLoanCorpus = 0.0; // 贷款本金
	private double mLoanInterest = 0.0; // 贷款利息

	private double mOwePrem = 0.0; // 欠交保费
	private double mOweInterest = 0.0; // 欠交保费利息

	private double mScale = 0.0; // 减保比例
	private String DutyCode = "";
	
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LJSGetEndorseSet mZKLJSGetEndorseSet = new LJSGetEndorseSet();
	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();
	
	private String mPTFlag = null;

	public PEdorPTDetailBL() {
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param: cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName("LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
			
			//0减少保费1减少份数2减少保额
			this.mPTFlag = (String) mInputData.getObjectByObjectName("String", 0);
			logger.debug("mPTFlag"+mPTFlag);
		}
		catch (Exception e) {
			mErrors.addOneError("接收数据失败");
			return false;
		}
		mEdorCalZT = new EdorCalZT(mGlobalInput);
		return true;
	}

	private boolean checkData() {

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(mLPEdorItemSchema);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		
		mLPEdorItemSchema = tLPEdorItemDB.query().get(1);
		
		//add jiaqiangli 2009-03-17 增加退保原因及与业务员关系
		mLPEdorItemSchema.setEdorReasonCode(tLPEdorItemSchema.getEdorReasonCode());
		mLPEdorItemSchema.setEdorReason(tLPEdorItemSchema.getEdorReason());
		mLPEdorItemSchema.setStandbyFlag2(tLPEdorItemSchema.getStandbyFlag2());

		mEdorCalZT.setEdorInfo(mLPEdorItemSchema);
		
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLPPolSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			mErrors.addOneError("查询险种表失败!");
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		//comment by jiaqiangli 减少的值大于0但大于等于原值 不合法
		if (mLPPolSchema.getAmnt() > 0 && mLCPolSchema.getAmnt() <= mLPPolSchema.getAmnt()) {
			logger.debug("Old Amnt ========>" + mLCPolSchema.getAmnt());
			logger.debug("New Amnt ========>" + mLPPolSchema.getAmnt());
			mErrors.addOneError("所减金额不符合规范，请重新输入!请确认所减金额是大于0的整数，若减保后的金额为0，请做退保处理！");
			return false;
		}
		if (mLPPolSchema.getMult() > 0 && mLCPolSchema.getMult() <= mLPPolSchema.getMult()) {
			mErrors.addOneError("所减金额不符合规范，请重新输入!请确认所减金额是大于0的整数，若减保后的金额为0，请做退保处理！");
			return false;
		}
		//还需增加校验 保费算保额时 关于哪一档的变化
		if (mLPPolSchema.getStandPrem() > 0 && mLCPolSchema.getStandPrem() <= mLPPolSchema.getStandPrem()) {
			mErrors.addOneError("所减金额不符合规范，请重新输入!请确认所减金额是大于0的整数，若减保后的金额为0，请做退保处理！");
			return false;
		}
		
		try {
			int a = (int) mLPPolSchema.getAmnt();
			int b = (int) mLPPolSchema.getMult();
			if (a - mLPPolSchema.getAmnt() != 0) {
				mErrors.addOneError("所减金额非整数，请重新输入!");
				return false;
			}
			if (b - mLPPolSchema.getMult() != 0) {
				mErrors.addOneError("所减金额非整数，请重新输入!");
				return false;
			}

			String sVPU = "select * from lmduty where dutycode in (select dutycode from lcduty where polno = '?polno?')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sVPU);
			sqlbv.put("polno", mLCPolSchema.getPolNo());
			LMDutyDB sLMDutyDB = new LMDutyDB();
			LMDutySet sLMDutySet = sLMDutyDB.executeQuery(sqlbv);
			if (sLMDutySet == null || sLMDutySet.size() < 1) {
				CError.buildErr(this, "查询责任定义信息失败!");
				return false;
			}
			if ("1".equals(sLMDutySet.get(1).getAmntFlag())) {
				int iVPU = (int) sLMDutySet.get(1).getVPU();
				if (a % iVPU != 0) {
					CError.buildErr(this, "减保需录入单位保额的整倍数");
					return false;
				}
			}
		} catch (Exception e) {
			mErrors.addOneError("所减金额非整数，请重新输入!");
			return false;
		}
		if (mLPPolSchema.getAmnt() == 0 && mLPPolSchema.getMult() == 0 && mLPPolSchema.getStandPrem() == 0) {
			mErrors.addOneError("所减金额为零，请重新输入!");
			return false;
		}

		return true;
	}

	private boolean dealData() {
		
		if (!DealPTRisk()) {
			mErrors.addOneError("减保处理失败!");
			return false;
		}
		
		this.mOwePrem = 0;
		this.mOweInterest = 0;
		
		//处理应交未交保费 针对合同级别的
//		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
//		if (tBqPolBalBL.calDuePremAddInterest(mLPEdorItemSchema) == false) {
//			mErrors.addOneError("处理应交未交保费失败!");
//			return false;
//		}
//		this.mOwePrem = tBqPolBalBL.getDuePrem();
//		this.mOweInterest = tBqPolBalBL.getDueAddInterest();
		
		//add by jiaqiangli 2008-11-21
		//MS减保不要求扣除贷款、欠交等 但要校验：贷款本息+欠款本息+减保所退金额 <= 当年现价
		// 计算贷款本金
		BqPolBalBL aBqPolBalBL = new BqPolBalBL();
		aBqPolBalBL.calLoanCorpus(mLCPolSchema, mLPEdorItemSchema.getEdorAppDate());
		mLoanCorpus = aBqPolBalBL.getCalResultRound();
		if (mLoanCorpus == -1) {
			mErrors.addOneError("计算贷款本金失败!");
			return false;
		}
		//add by jiaqiangli 2009-04-10 需要重新初始化类 为安全起见
		aBqPolBalBL = new BqPolBalBL();
		// 计算贷款利息
		aBqPolBalBL.calLoanInterest(mLCPolSchema, mLPEdorItemSchema.getEdorAppDate());
		mLoanInterest = aBqPolBalBL.getCalResultRound();
		if (mLoanInterest == -1) {
			mErrors.addOneError("计算贷款利息失败!");
			return false;
		}

		//应该做上次险种值累加本次值 多险种保存多次
		//comment by jiaqiangli 2008-12-30 金额汇总已经放在保存按钮里
		//mLPEdorItemSchema.setGetMoney(mLPEdorItemSchema.getGetMoney()+mGetMoney);
		mLPEdorItemSchema.setEdorState("3");
		mMap.put(mLPEdorItemSchema, "UPDATE");

		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
		tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_BaseCashValue);
		//需要保持保全操作协议减保的实退应退金额 初始化保存时实退=应退
		//此处可以防止调整金额后再次点击减保带来的金额对不上的问题
		//getmoney 保存实退金额
		tLJSGetEndorseSchema.setGetMoney(this.mGetMoney);
		//getmoney 保存应退金额
		tLJSGetEndorseSchema.setSerialNo(String.valueOf(tLJSGetEndorseSchema.getGetMoney()));
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-11
		tLJSGetEndorseSet.add(tLJSGetEndorseSchema);

		mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");
		
		// add by jiaqiangli 2008-12-30
		// 最后的汇总ljsgetendorse到lpedoritem.getmoney
		// lpedoritem.getmoney standbyflag3汇总值 ljsgetendorse.getmoney serialno 明细值
		String tGetMoneySQL = "update lpedoritem set getmoney = (select sum(getmoney) from ljsgetendorse where endorsementno = '?endorsementno?' and contno = '?contno?' "
				+ " and feeoperationtype='?feeoperationtype?'),standbyflag3 = (select sum(serialno) from ljsgetendorse where endorsementno = '?endorsementno?' and contno = '?contno?' "
				+ " and feeoperationtype='?feeoperationtype?') where edoracceptno='?edoracceptno?' and contno='?contno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tGetMoneySQL);
		sqlbv.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		mMap.put(sqlbv, "UPDATE");
		// add by jiaqiangli 2008-12-30

		mResult.clear();
		mResult.add(mMap);

		//先计算contno的现金价值
		mContCashValue = mEdorCalZT.getContCashValue(this.mLPEdorItemSchema);
		
		//check 贷款本息+欠款本息+减保所退金额 <= 当年现价 此校验应该是针对contno层的
		//if (mOwePrem+mOweInterest+mLoanCorpus+mLoanInterest+mLPEdorItemSchema.getGetMoney() > mContCashValue) {
		if (mLoanCorpus+mLoanInterest+mLPEdorItemSchema.getGetMoney() > mContCashValue) {
			mErrors.addOneError("借款本息+应交未交本息+减保所退金额之和大于当年现价!");
			return false;
		}
		
		//add by jiaqiangli 2009-02-07 update lccont.prem
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		LCContDB tLCContDB = new LCContDB();

		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mRef.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// 只需要处理prem和amnt两个字段即可 sumprem字段含义不明确 = 当前prem*期数 补退费保全已经平衡
		// tLPContSchema 的累加
		tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(tLPContSchema, "DELETE&INSERT");
		
		//注意此处不能做累加 否则多次保存后存在问题
		//上面已经insert进lpcont表 变动保费与保费通过update语句执行
		String tLPContSQL = "update lpcont set prem = prem+(select sum(prem) from lppol where edorno='?edorno?' "
		        + "and edortype='?edortype?' and contno='?contno?') - "
		        + "(select sum(prem) from lcpol where contno='?contno?' and polno in (select polno from lppol where edorno='?edorno?' "
			    + "and edortype='?edortype?' and contno='?contno?')), "
			    + "amnt = amnt+(select sum(amnt) from lppol where edorno='?edorno?' "
			    + "and edortype='?edortype?' and contno='?contno?') - "
			    + "(select sum(amnt) from lcpol where contno='?contno?' and polno in (select polno from lppol where edorno='?edorno?' "
				+ "and edortype='?edortype?' and contno='?contno?')) "
			    + "where edorno='?edorno?' and contno = '?contno?' "
				+ "and edortype='?edortype?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tLPContSQL);
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		mMap.put(sbv, "UPDATE");

		return true;
	}

	/**
	 * MS减保不要求扣除贷款、欠交等 但要校验：贷款本息+欠款本息+减保所退金额 <= 当年现价
	 * 计算应交未交保费和利息 方便后面的校验判断
	 */
	private boolean DealPTRisk() {
		String i_sql = "select * from LCDuty where PolNo = '?PolNo?' and char_length(trim(dutycode)) = 6 order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(i_sql);
		sqlbv.put("PolNo", mLCPolSchema.getPolNo());
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();

		LPDutySchema aLPDutySchema = new LPDutySchema();
		LCPremSet aLCPremSet = new LCPremSet();

		tLCDutySet = tLCDutyDB.executeQuery(sqlbv);
		if (tLCDutySet.size() < 1) {
			mErrors.addOneError("查询责任信息不符合规范!");
			return false;
		}
		logger.debug("险种对应责任的数目:------" + tLCDutySet.size());
		
		this.DutyCode = tLCDutySet.get(1).getDutyCode();

		// 0减少保费1减少份数2减少保额
		if (this.mPTFlag.equals("2") && mLPPolSchema.getAmnt() > 0) {
			// 按保额进行减保
			mRef.transFields(aLPDutySchema, tLCDutySet.get(1));
			aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			aLPDutySchema.setModifyTime(PubFun.getCurrentTime());
			aLPDutySchema.setAmnt(tLCDutySet.get(1).getAmnt() - mLPPolSchema.getAmnt());
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(mLPPolSchema.getPolNo());
			tLCPremDB.setDutyCode(aLPDutySchema.getDutyCode());
			aLCPremSet = tLCPremDB.query();
			if (aLCPremSet.size() < 1) {
				mErrors.addOneError("查询保费项表失败!");
				return false;
			}
			mScale = mLPPolSchema.getAmnt() / mLCPolSchema.getAmnt();
		}
		else if (this.mPTFlag.equals("1") && mLPPolSchema.getMult() > 0) {
			mRef.transFields(aLPDutySchema, tLCDutySet.get(1));
			aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			aLPDutySchema.setModifyTime(PubFun.getCurrentTime());
			aLPDutySchema.setMult(tLCDutySet.get(1).getMult() - mLPPolSchema.getMult());
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(mLPPolSchema.getPolNo());
			tLCPremDB.setDutyCode(aLPDutySchema.getDutyCode());
			aLCPremSet = tLCPremDB.query();
			if (aLCPremSet.size() < 1) {
				mErrors.addOneError("查询保费项表失败!");
				return false;
			}
			mScale = mLPPolSchema.getMult() / mLCPolSchema.getMult();
		}
		else if (this.mPTFlag.equals("0") && mLPPolSchema.getStandPrem() > 0) {
			mRef.transFields(aLPDutySchema, tLCDutySet.get(1));
			aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			aLPDutySchema.setModifyTime(PubFun.getCurrentTime());
			aLPDutySchema.setStandPrem(tLCDutySet.get(1).getStandPrem() - mLPPolSchema.getStandPrem());
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(mLPPolSchema.getPolNo());
			tLCPremDB.setDutyCode(aLPDutySchema.getDutyCode());
			aLCPremSet = tLCPremDB.query();
			if (aLCPremSet.size() < 1) {
				mErrors.addOneError("查询保费项表失败!");
				return false;
			}
			mScale = mLPPolSchema.getStandPrem() / mLCPolSchema.getStandPrem();
		}
		else {
			CError tCError = new CError();
			tCError.moduleName = "PEdorPTDetail";
			tCError.functionName = "MainRisk";
			tCError.errorMessage = "请正确按保费/保额/份数输入正数据";
			mErrors.addOneError(tCError);
			return false;
		}

		// 计算现金价值
		mCashValue = mEdorCalZT.getCashValue(mLCPolSchema.getPolNo(), mLPEdorItemSchema.getEdorAppDate());
		
		logger.debug("mCashValue"+mCashValue);

		if (mCashValue < 0) {
			mErrors.copyAllErrors(mEdorCalZT.mErrors);
			mErrors.addOneError("计算现金价值失败!");
			return false;
		}
		//PT应退金额为当年现金价值乘以比例系数
		mGetMoney = mCashValue * mScale;
		
		logger.debug("mCashValue"+mCashValue);
		logger.debug("mGetMoney"+mGetMoney);
		logger.debug("mScale"+mScale);

		mGetMoney = this.getRound(mGetMoney);
		mCashValue = this.getRound(mCashValue);

		ReCalBL tReCalBL = new ReCalBL(mLPPolSchema, mLPEdorItemSchema);

		LCPolSchema cLCPolSchema = new LCPolSchema();
		cLCPolSchema.setSchema(mLCPolSchema);
		
		if (mLPPolSchema.getAmnt() > 0) {
			cLCPolSchema.setAmnt(cLCPolSchema.getAmnt() - mLPPolSchema.getAmnt());
		}
		if (mLPPolSchema.getMult() > 0) {
			cLCPolSchema.setMult(cLCPolSchema.getMult() - mLPPolSchema.getMult());
		}
		if (mLPPolSchema.getStandPrem() > 0) {
			cLCPolSchema.setStandPrem(cLCPolSchema.getStandPrem() - mLPPolSchema.getStandPrem());
		}
		
		LCPolBL cLCPolBL = new LCPolBL();
		cLCPolBL.setSchema(cLCPolSchema);
		tReCalBL.preLCPolSchema.setSchema(cLCPolSchema);

		LCDutySchema cLCDutySchema = new LCDutySchema();
		mRef.transFields(cLCDutySchema, aLPDutySchema);
		LCDutyBLSet cLCDutyBLSet = new LCDutyBLSet();
		cLCDutyBLSet.add(cLCDutySchema);
		tReCalBL.preLCDutySet.clear();
		tReCalBL.preLCDutySet.add(cLCDutyBLSet);

		LCPremBLSet cLCPremBLSet = new LCPremBLSet();
		cLCPremBLSet.clear();
		cLCPremBLSet.add(aLCPremSet);
		tReCalBL.preLCPremSet.clear();
		tReCalBL.preLCPremSet.add(aLCPremSet);

		LCGetBLSet cLCGetBLSet = new LCGetBLSet();
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setPolNo(mLPPolSchema.getPolNo());
		tLCGetSet = tLCGetDB.query();
		cLCGetBLSet.clear();
		cLCGetBLSet.add(tLCGetSet);
		tReCalBL.preLCGetSet.clear();
		tReCalBL.preLCGetSet.add(tLCGetSet);

		if (!tReCalBL.recalWithData(cLCPolBL, cLCDutyBLSet, cLCPremBLSet,cLCGetBLSet, mLPEdorItemSchema)) {
			mErrors.addOneError("重算数据失败，请检查录入值是否正确!");
			return false;
		}

		//lpprem还需要处理加费的prem
		mLPDutySet = tReCalBL.aftLPDutySet;
		mLPPolSet = tReCalBL.aftLPPolSet;
		mLPGetSet = tReCalBL.aftLPGetSet;
		
		// 重新计算加费金额
		AddPremReCalBQInterface tAddPremReCalBQInterface = new AddPremReCalBQInterface(mGlobalInput);
		LPPremSet afterAddFeeLPPremSet = tAddPremReCalBQInterface.recalAddPrem(mLPPolSet.get(1), tReCalBL.aftLPPremSet,cLCDutyBLSet);
		if (afterAddFeeLPPremSet == null || afterAddFeeLPPremSet.size() == 0) {
			CError.buildErr(this, "重新计算加费金额失败");
			return false;
		}
		//目前只保证standprem prem准确 sumprem无实际确切含义
		//还要将加费的standprem的差值返回累加到lppol.prem上
		mLPPolSet.get(1).setPrem(mLPPolSet.get(1).getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
		//加费lpprem重算 添加加费后的lcprem
		mLPPremSet.add(afterAddFeeLPPremSet);
		
		//累加加费的prem到lpduty
		for (int i = 1; i <= mLPDutySet.size(); i++) {
			//LCDUTY的standprem重算已经置上 此处prem需要累加加费重算的差额 同lcpol的处理
			mLPDutySet.get(i).setPrem(mLPDutySet.get(i).getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
			mLPDutySet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLPDutySet.get(i).setModifyTime(PubFun.getCurrentTime());			
		}
		
		//增加校验 保费算保额的险种减保的保费修改校验 如200，400，600，只校验保额是否为0
		if (mLPPolSet.get(1).getAmnt() == 0) {
			mErrors.addOneError("保单重算数据失败，请检查录入值是否正确!");
			return false;
		}
		
		//增加附加豁免的联动处理
		//先查询出当前处理的保单险种mLPPolSet.get(1)
		//后查询出需要联动的豁免处理信息 若有的话
		LCPolDB tExemptLCPolDB = new LCPolDB();
		LCPolSet tExemptLCPolSet = new LCPolSet();
		//判断是否有附加豁免关联
		String tExemptSQL = "select * from lcpol a where a.appflag='1' and contno = '?contno?' and riskcode in (select riskcode from lmriskapp where risktype7 in ('1','2')) "
		                  //exists 作关联的判断逻辑
		                  + "and payintv > 0 and exists (select 1 from lcpol b,ldcode1 c where polno='?polno?' and contno = '?contno?' and c.codetype = 'freerisk' and c.code=a.riskcode and b.riskcode=c.code1)";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tExemptSQL);
		sbv.put("contno", mLPPolSet.get(1).getContNo());
		sbv.put("polno", mLPPolSet.get(1).getPolNo());
		tExemptLCPolSet = tExemptLCPolDB.executeQuery(sbv);
		if (tExemptLCPolSet.size() > 0) {
			double tExemptGetMoney = 0.00;
			//先实例化一个lpedoritem
			LPEdorItemSchema tExemptLPEdorItemSchema = new LPEdorItemSchema();
			PubFun.copySchema(tExemptLPEdorItemSchema, mLPEdorItemSchema);
			//调用附加豁免处理
			//PT是循环险种，需要累加豁免保费
			ExemptRiskReCalBL tExemptRiskReCalBL = new ExemptRiskReCalBL(mLPPolSet.get(1),tExemptLCPolSet.get(1),tExemptLPEdorItemSchema,mGlobalInput);
			if (tExemptRiskReCalBL.PTreCalExempt() == false) {
				this.mErrors.copyAllErrors(tExemptRiskReCalBL.mErrors);
				CError.buildErr(this, "豁免保费重算失败");
				return false;
			}
			//添加豁免重算后信息
			mLPPolSet.add(tExemptRiskReCalBL.aftExemptLPPolSet);
			mLPDutySet.add(tExemptRiskReCalBL.aftExemptLPDutySet);
			mLPPremSet.add(tExemptRiskReCalBL.aftExemptLPPremSet);
			mLPGetSet.add(tExemptRiskReCalBL.aftExemptLPGetSet);
			
			//增加附加豁免的联动处理 累加附豁的退保金额
			tExemptGetMoney += tExemptRiskReCalBL.mExemptGetMoney;
			
			LCPolSchema tTmpLCPolSchema = new LCPolSchema();
			PubFun.copySchema(tTmpLCPolSchema, tExemptRiskReCalBL.aftExemptLPPolSet.get(1));
			//豁免险的ljsgetendorse独立于被豁免险
			LJSGetEndorseSchema tExemptLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tExemptLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
			tExemptLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tExemptLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
			// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
			String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),"TB", tTmpLCPolSchema.getPolNo());
			if (finType.equals("")) {
				// @@错误处理
				CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
				return false;
			}
			tExemptLJSGetEndorseSchema.setFeeFinaType(finType);
			tExemptLJSGetEndorseSchema.setDutyCode("0");
			tExemptLJSGetEndorseSchema.setPayPlanCode("0");
			// 走保全交费财务流程
			tExemptLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
			tExemptLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			mRef.transFields(tExemptLJSGetEndorseSchema, tTmpLCPolSchema);
			tExemptLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tExemptLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tExemptLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tExemptLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
			tExemptLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
			tExemptLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
			//0为补费1为退费
			tExemptLJSGetEndorseSchema.setGetFlag("1");
			tExemptLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_BaseCashValue);
			//实退金额
			tExemptLJSGetEndorseSchema.setGetMoney(tExemptGetMoney);
			//应退金额
			tExemptLJSGetEndorseSchema.setSerialNo(String.valueOf(tExemptLJSGetEndorseSchema.getGetMoney()));
			
			//豁免险种的那条ljsgetendorse
	          //营改增 add zhangyingfeng 2016-07-13
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tExemptLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-13
			mMap.put(tExemptLJSGetEndorseSchema, "DELETE&INSERT");
		}
		
		LCDiscountSet tLCDiscountSet = new LCDiscountSet();
		LCDiscountDB tLCDiscountDB = new LCDiscountDB();
		tLCDiscountDB.setPolNo(mLPPolSchema.getPolNo());
		tLCDiscountSet = tLCDiscountDB.query();
		LPDiscountSet tLPDiscountSet = new LPDiscountSet();
		if(tLCDiscountSet!=null && tLCDiscountSet.size()>0){
			for(int j=1;j<=tLCDiscountSet.size();j++){
				LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
				mRef.transFields(tLPDiscountSchema, tLCDiscountSet.get(j));
				tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDiscountSet.add(tLPDiscountSchema);
			}
			mLPDiscountSet.add(tLPDiscountSet);
		}
		
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PayCount", "1");
		tTransferData.setNameAndValue("PayIntv", String.valueOf(mLPPolSchema.getPayIntv()));
		tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);
		PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
		VData tzkVData = new VData();
		tzkVData.add(mLPPolSet.getObj(1));
		tzkVData.add(mLPPremSet);
		tzkVData.add(tLPDiscountSet);
		tzkVData.add(mLPEdorItemSchema);
		tzkVData.add(tTransferData);
		//得到该保单折扣减去的钱 ，为负值
		if(!tDiscountCalBL.calculate(tzkVData))
		{
			CError.buildErr(this, "折扣计算失败！");
			return false;
		}
		VData rVData = tDiscountCalBL.getResult();
		tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
		if(tLJSPayPersonSet!=null && tLJSPayPersonSet.size()>0)
		{
			// 添加保费LJSGetEndorse
			for(int j=1;j<=tLJSPayPersonSet.size();j++)
			{
				if (!createLJSGetEndorseSchemaZK(mLPEdorItemSchema,mLPPolSchema,tLJSPayPersonSet.get(j))) {
					mErrors.addOneError("添加保费LJSGetEndorse!");
				}
			}
		}
				
		//保全后的信息
		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");
		
		if(mLPDiscountSet.size()>0){
			mMap.put(mLPDiscountSet, "DELETE&INSERT");
		}
		if(mZKLJSGetEndorseSet.size()>0){
			mMap.put(mZKLJSGetEndorseSet, "DELETE&INSERT");
		}
		return true;
	}
	
	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		Reflections tReflections = new Reflections();
		// mPayCount++;
		mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		
		// mLJSGetEndorseSchema.setFeeFinaType("BF");
		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),strfinType, mLPPolSchema.getPolNo());
		if (finType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
			return null;
		}
		mLJSGetEndorseSchema.setFeeFinaType(finType);

		mLJSGetEndorseSchema.setPayPlanCode("0");
		mLJSGetEndorseSchema.setPolNo(mLPPolSchema.getPolNo());

		// 走保全交费财务流程
		mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		mLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付

		tReflections.transFields(mLJSGetEndorseSchema, mLCPolSchema);
		mLJSGetEndorseSchema.setDutyCode(DutyCode);
		mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);

		mLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		mLJSGetEndorseSchema.setGetFlag("1");

		tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

		return tLJSGetEndorseSchema;
	}

	// 四舍六入五靠偶数，保留两位
	private double getRound(double tValue) {
//		String t = "0.00";
//		DecimalFormat tDF = new DecimalFormat(t);
//		return Double.parseDouble(tDF.format(tValue));
		//MS采用通常意义上的四舍五入规则而不是上述的四舍六入五靠偶数
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		return PubFun.round(tValue,2);
	}
	
	private boolean createLJSGetEndorseSchemaZK(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, LJSPayPersonSchema cLJSPayPersonSchema) {

		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initZKLJSGetEndorse(tLPEdorItemSchema,tLPPolSchema,"ZK");
		mRef.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
		tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());

		tLJSGetEndorseSchema.setSubFeeOperationType(cLJSPayPersonSchema.getPayType());
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-11
		mZKLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}
	
	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initZKLJSGetEndorse(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema.setGetNoticeNo(tLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setEndorsementNo(tLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setFeeOperationType(tLPEdorItemSchema.getEdorType());
		tLJSGetEndorseSchema.setFeeFinaType(strfinType);
		tLJSGetEndorseSchema.setDutyCode("0");
		tLJSGetEndorseSchema.setPayPlanCode("0");
		// 走保全交费财务流程
		tLJSGetEndorseSchema.setOtherNo(tLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		mRef.transFields(tLJSGetEndorseSchema, tLPPolSchema);
		tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		tLJSGetEndorseSchema.setGetDate(tLPEdorItemSchema.getEdorValiDate());
		// 0为补费1为退费
		tLJSGetEndorseSchema.setGetFlag("0");

		return tLJSGetEndorseSchema;
	}
}
