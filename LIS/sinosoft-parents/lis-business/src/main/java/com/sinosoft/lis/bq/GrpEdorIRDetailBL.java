package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全业务处理
 * </p>
 * <p>
 * Description: 换人明细业务处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @CreateDate 2005-11-02
 * @version 1.0
 */
public class GrpEdorIRDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorIRDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPPersonSchema mLPPersonSchema = new LPPersonSchema();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();
	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private BqCalBase mBqCalBase;

	// ==add=======liuxiaosong=======2006-12-16=========保全磁盘导入=========start====
	private TransferData mTransferData = new TransferData();

	// ==add=======liuxiaosong=======2006-12-16=========保全磁盘导入========end=======

	public GrpEdorIRDetailBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		logger.debug(" Begin GEdorIRDetailBL ================");

		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		logger.debug(" after getInputData ================");

		if (!checkData()) {
			return false;
		}

		logger.debug(" after getcheckData ================");

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		logger.debug(" after dealData ================");

		if (!prepareData()) {
			return false;
		}

		logger.debug(" End GEdorIRDetailBL ================");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
					"LPEdorItemSet", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
					.getObjectByObjectName("LPAddressSchema", 0);
			mLPPersonSchema = (LPPersonSchema) mInputData
					.getObjectByObjectName("LPPersonSchema", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			mLPGetSchema = // 领取方式
			(LPGetSchema) mInputData.getObjectByObjectName("LPGetSchema", 0);
			mLPPolSchema = // 领取年龄
			(LPPolSchema) mInputData.getObjectByObjectName("LPPolSchema", 0);

			// =====add======liuxiaosong====2006-12-16====保全磁盘导入数据补全=========start=======
			logger.debug("保全磁盘导入数据补全");
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			if (mTransferData != null) {
				String tISDiskImport = (String) mTransferData
						.getValueByName("ISDiskImport");
				if ("yes".equals(tISDiskImport)) {
					if (!diskImprotReGetData()) {
						CError.buildErr(this, "磁盘导入组织数据失败");
						return false;
					}
				}
			}
			// =====add======liuxiaosong====2006-12-16====保全磁盘导入数据补全===========end=======

			if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团体保全项目表失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSet.get(1).getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSet.get(1).getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSet.get(1).getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSet.get(1).getContNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSet.get(1).getPolNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSet.get(1).getInsuredNo());
		if (!tLPEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询保全项目信息失败!");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		return true;
	}

	// =====add======liuxiaosong====2006-12-16====保全磁盘导入特殊处理===========start=====
	/**
	 * 保全磁盘导入数据补全处理
	 * 
	 * @return boolean
	 */
	private boolean diskImprotReGetData() {
		logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():开始磁盘导入数据补全========");
		String tEdorNO = mLPEdorItemSet.get(1).getEdorNo();
		String tEdorType = "IR";
		String tRiskCode = (String) mTransferData.getValueByName("RiskCode");

		// mLPGetSchema数据补全---------------------------------------------------
		mLPGetSchema.setEdorNo(tEdorNO);
		mLPGetSchema.setEdorType(tEdorType);
		mLPGetSchema.setGetDutyCode("");
		mLPGetSchema.setDutyCode("");
		String tSQL = "SELECT b.GetDutyCode,b.DutyCode,b.GetDutyKind "
				+ " FROM LCGet b,LMDutyGet c " + " WHERE b.PolNo= '"
				+ mLPGetSchema.getPolNo() + "'" + " and b.ContNo ='"
				+ mLPGetSchema.getContNo() + "' "
				+ " and b.GetDutyCode = c.GetDutyCode "
				+ " and c.GetType2 = '1' ";

		logger.debug("磁盘导入数据补全SQL\n" + tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSQL);
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			mLPGetSchema.setGetDutyCode(tSSRS.GetText(1, 1));
			mLPGetSchema.setDutyCode(tSSRS.GetText(1, 2));
		}
		// ----------------------------------------------------------------------
		// mLPPerson数据补全------------------------------------------------------
		mLPPersonSchema.setEdorNo(tEdorNO);
		mLPPersonSchema.setEdorType(tEdorType);
		// ----------------------------------------------------------------------
		// mLPAddress数据补全-----------------------------------------------------
		mLPAddressSchema.setEdorNo(tEdorNO);
		mLPAddressSchema.setEdorType(tEdorType);
		// ----------------------------------------------------------------------

		logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():完成磁盘导入数据补全========");
		return true;
	}

	// =====add======liuxiaosong====2006-12-16====保全磁盘导入特殊处理===========end=======

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 确认被保人的客户号
		if (!CertifyCustomerNo()) {
			return false;
		}

		// 确认客户地址号
		if (!CertifyAddressNo()) {
			mErrors.addOneError("确认客户地址号失败!");
			return false;
		}

		return true;
	}

	/**
	 * 对业务数据进行处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// 清空已经保存过的信息
		if (!delPData()) {
			return false;
		}

		// 原被保人号
		String OldInsuredNo = mLPEdorItemSchema.getInsuredNo();

		Reflections tRef = new Reflections();

		// 查询原被保人信息
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCInsuredDB.setInsuredNo(OldInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			mErrors.addOneError("查询被保人信息失败!被保人号：" + OldInsuredNo);
			return false;
		}
		tRef.transFields(tLPInsuredSchema, mLPPersonSchema);
		// 替换新的被保人信息
		tLPInsuredSchema.setAddressNo(String.valueOf(mLPAddressSchema
				.getAddressNo()));
		tLPInsuredSchema.setInsuredNo(mLPPersonSchema.getCustomerNo());
		tLPInsuredSchema.setGrpContNo(tLCInsuredDB.getGrpContNo());
		tLPInsuredSchema.setCustomerSeqNo(tLCInsuredDB.getCustomerSeqNo());
		tLPInsuredSchema.setContNo(tLCInsuredDB.getContNo());
		tLPInsuredSchema.setPrtNo(tLCInsuredDB.getPrtNo());
		tLPInsuredSchema.setAppntNo(tLCInsuredDB.getAppntNo());
		tLPInsuredSchema.setManageCom(tLCInsuredDB.getManageCom());
		tLPInsuredSchema.setExecuteCom(tLCInsuredDB.getExecuteCom());
		tLPInsuredSchema.setRelationToAppnt(tLCInsuredDB.getRelationToAppnt());
		tLPInsuredSchema.setRelationToMainInsured(tLCInsuredDB
				.getRelationToMainInsured());
		tLPInsuredSchema.setSequenceNo(tLCInsuredDB.getSequenceNo());
		tLPInsuredSchema.setContPlanCode(tLCInsuredDB.getContPlanCode());
		tLPInsuredSchema.setOperator(mGlobalInput.Operator);
		tLPInsuredSchema.setMakeDate(CurrDate);
		tLPInsuredSchema.setMakeTime(CurrTime);
		tLPInsuredSchema.setModifyDate(CurrDate);
		tLPInsuredSchema.setModifyTime(CurrTime);

		mMap.put(tLPInsuredSchema, "INSERT");

		// 处理保单信息
		LPContSchema tLPContSchema = new LPContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
		if (StrTool.compareString(tLPContSchema.getInsuredNo(), OldInsuredNo)) {
			tLPContSchema.setInsuredName(mLPPersonSchema.getName());
			tLPContSchema.setInsuredSex(mLPPersonSchema.getSex());
			tLPContSchema.setInsuredBirthday(mLPPersonSchema.getBirthday());
			tLPContSchema.setInsuredIDType(mLPPersonSchema.getIDType());
			tLPContSchema.setInsuredIDNo(mLPPersonSchema.getIDNo());
			tLPContSchema.setInsuredNo(mLPPersonSchema.getCustomerNo());
		}
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(CurrDate);
		tLPContSchema.setModifyTime(CurrTime);
		mMap.put(tLPContSchema, "INSERT");

		// 处理险种和给付表信息
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet.size() < 1) {
			mErrors.addOneError("查询险种信息失败!");
			return false;
		}
		LPPolSchema tLPPolSchema;
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			tLPPolSchema = new LPPolSchema();
			tRef.transFields(tLPPolSchema, tLCPolSet.get(i));

			EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
			// 保单现金价值
			double dPolCashValue = tEdorCalZT.getCashValue(tLPPolSchema
					.getPolNo(), mLPGrpEdorItemSchema.getEdorAppDate());
			if (dPolCashValue == -1) {
				mErrors.addOneError("现金价值计算失败!");
				return false;
			}

			// 设置新的被保人信息
			if (tLPPolSchema.getInsuredNo().equals(
					mLPEdorItemSchema.getInsuredNo())) { // 如果该险种被保人是被换被保人,将被保人信息替换成新的被保人

				int iNewInsuredAppAge = PubFun.calInterval(mLPPersonSchema
						.getBirthday(), tLPPolSchema.getCValiDate(), "Y"); // 新换进被保人的投保,
				// 根据保单原生效日期计算
				tLPPolSchema.setInsuredName(mLPPersonSchema.getName());
				tLPPolSchema.setInsuredSex(mLPPersonSchema.getSex());
				tLPPolSchema.setInsuredBirthday(mLPPersonSchema.getBirthday());
				tLPPolSchema.setInsuredAppAge(iNewInsuredAppAge);
				tLPPolSchema.setInsuredNo(mLPPersonSchema.getCustomerNo());
				if (tLPPolSchema.getPolNo().equals(mLPPolSchema.getPolNo())) { // 领取年龄
					tLPPolSchema.setGetYear(mLPPolSchema.getGetYear());
				}
			}

			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setSchema(mLPEdorItemSchema);
			tLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());

			ReCalBL tReCalBL = new ReCalBL(tLPPolSchema.getSchema(),
					tLPEdorItemSchema);
			ReCalBL tReCalBL2 = new ReCalBL(tLPPolSchema.getSchema(),
					tLPEdorItemSchema);

			// 准备重算需要的保单表数据
			LCPolBL tLCPolBL = tReCalBL.getRecalPol(tLPPolSchema.getSchema());
			// 准备重算需要的责任表数据
			LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(tLPEdorItemSchema);
			// 准备重算需要的保费项表数据
			LCPremSet tLCPermSet = tReCalBL.getRecalPrem(tLPEdorItemSchema);
			// 准备重算需要的领取项表数据
			LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(tLPEdorItemSchema);

			// 设置新的领取方式 add by zhangtao 2006-10-17
			for (int j = 1; j <= tLCGetBLSet.size(); j++) {
				if (tLCGetBLSet.get(j).getPolNo().equals(
						mLPGetSchema.getPolNo())
						&& tLCGetBLSet.get(j).getGetDutyCode().equals(
								mLPGetSchema.getGetDutyCode())) {
					tLCGetBLSet.get(i).setGetDutyKind(
							mLPGetSchema.getGetDutyKind());
					tLCGetBLSet.get(i).setGettoDate("");
					tLCGetBLSet.get(i).setGetEndDate("");
				}
			}

			// 设置新的领取年龄 add by zhangtao 2006-10-17
			for (int j = 1; j <= tLCDutyBLSet.size(); j++) {
				if (tLCDutyBLSet.get(j).getPolNo().equals(
						mLPPolSchema.getPolNo())) { // 领取年龄
					tLCDutyBLSet.get(j).setGetYear(mLPPolSchema.getGetYear());
				}
			}

			// 根据新换进被保人年龄信息及新选择的领取方式和领取年龄重新计算
			if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPermSet,
					tLCGetBLSet, tLPEdorItemSchema)) {
				this.mErrors.copyAllErrors(tReCalBL.mErrors);
				CError.buildErr(this, "重算失败");
				return false;
			}

			// -------------------------------------------------------------------------------
			// 从LMEdorCal表中查询领取标准折算算法，如果险种没有此算法，代表不需要重新折算
			LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
			tLMEdorCalDB.setRiskCode(tLPPolSchema.getRiskCode());
			tLMEdorCalDB.setCalType("IRGet");
			LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
			if (tLMEdorCalDB.mErrors.needDealError()) {
				CError.buildErr(this, "领取标准折算算法查询失败!");
				return false;
			}
			if (tLMEdorCalSet != null && tLMEdorCalSet.size() > 0) { // 需要根据现价折算新进被保人的领取标准重新计算领取标准,进而重新确定交费标准 add by zhangtao
				// 2006-10-18
				Calculator tCalculator = new Calculator();
				tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());

				// 准备重算需要的保单表数据
				tLCPolBL = tReCalBL2.getRecalPol(tLPPolSchema.getSchema());
				// 准备重算需要的责任表数据
				tLCDutyBLSet = tReCalBL2.getRecalDuty(tLPEdorItemSchema);
				// 准备重算需要的保费项表数据
				tLCPermSet = tReCalBL2.getRecalPrem(tLPEdorItemSchema);
				// 准备重算需要的领取项表数据
				tLCGetBLSet = tReCalBL2.getRecalGet(tLPEdorItemSchema);

				tRef.transFields(tLCPolBL, tReCalBL.aftLPPolSet.get(1));
				tRef.transFields(tLCDutyBLSet, tReCalBL.aftLPDutySet);
				tRef.transFields(tLCPermSet, tReCalBL.aftLPPremSet);
				tRef.transFields(tLCGetBLSet, tReCalBL.aftLPGetSet);

				double dPolCashValueTable = 0.0;
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tRef.transFields(tLCPolSchema, tLPPolSchema);
				LCDutySchema tLCDutySchema;
				for (int j = 1; j <= tLCDutyBLSet.size(); j++) {
					tLCDutySchema = tLCDutyBLSet.get(j);
					String sGetIntv = getGetIntv(tLCDutySchema, tLCGetBLSet);
					if (sGetIntv == null) {
						mErrors.addOneError("领取间隔查询失败!");
						return false;
					}
					// 根据第一次的重算结果查询新进被保人的现价表
					int iInterval = PubFun.calInterval(tLCPolSchema
							.getCValiDate(), mLPGrpEdorItemSchema
							.getEdorAppDate(), "Y");
					double dDutyCashValueTable = tEdorCalZT.getCashValueTable(
							tLCPolBL.getSchema(), tLCDutySchema, iInterval,
							tLCDutySchema.getCValiDate(), mLPGrpEdorItemSchema
									.getEdorAppDate(), sGetIntv);
					if (dDutyCashValueTable == -1) {
						mErrors.addOneError("现金价值表查询失败!");
						return false;
					}
					dPolCashValueTable += dDutyCashValueTable;

					// 根据新进被保人折算的领取标准第二次重新计算交费标准
					tCalculator.addBasicFactor("cv", String
							.valueOf(dPolCashValue));
					tCalculator.addBasicFactor("cv1", String
							.valueOf(dDutyCashValueTable));
					tCalculator
							.addBasicFactor("PolNo", tLPPolSchema.getPolNo());
					tCalculator.addBasicFactor("Amnt", String
							.valueOf(tLCPolSchema.getAmnt()));
					tCalculator.addBasicFactor("Sex", tLCPolSchema
							.getInsuredSex());
					tCalculator.addBasicFactor("AppAge", String
							.valueOf(tLCPolSchema.getInsuredAppAge()));
					tCalculator.addBasicFactor("Payintv", String
							.valueOf(tLCPolSchema.getPayIntv()));
					tCalculator.addBasicFactor("GetYear", String
							.valueOf(tLCPolSchema.getGetYear()));
					tCalculator.addBasicFactor("GetDutyKind", String
							.valueOf(mLPGetSchema.getGetDutyKind()));
					double dNewAmnt;
					String sNewAmnt = tCalculator.calculate();
					if (tCalculator.mErrors.needDealError()) {
						CError.buildErr(this, "领取标准计算失败!");
						return false;
					}
					if (sNewAmnt == null || sNewAmnt.trim().equals("")) {
						CError.buildErr(this, "领取标准计算结果为空!");
						return false;
					}
					try {
						dNewAmnt = Double.parseDouble(sNewAmnt);
					} catch (Exception e) {
						CError.buildErr(this, "领取标准计算结果错误!" + "错误结果："
								+ sNewAmnt);
						return false;
					}

					// 根据保单现金价值折算出新进被保险人的领取标准(保额)
					// dNewAmnt = (dPolCashValue / dDutyCashValueTable) * 100;
					tLCDutyBLSet.get(j).setAmnt(dNewAmnt);
				}

				// 根据新进被保人折算的领取标准第二次重新计算交费标准
				if (!tReCalBL2.recalWithData(tLCPolBL, tLCDutyBLSet,
						tLCPermSet, tLCGetBLSet, tLPEdorItemSchema)) {
					this.mErrors.copyAllErrors(tReCalBL.mErrors);
					CError.buildErr(this, "重算失败");
					return false;
				}
				mLPPolSet.add(tReCalBL2.aftLPPolSet);
				mLPDutySet.add(tReCalBL2.aftLPDutySet);
				mLPPremSet.add(tReCalBL2.aftLPPremSet);
				mLPGetSet.add(tReCalBL2.aftLPGetSet);
			}
			// ----------------------------------------------------------------------------------------------
			else {
				mLPPolSet.add(tReCalBL.aftLPPolSet);
				mLPDutySet.add(tReCalBL.aftLPDutySet);
				mLPPremSet.add(tReCalBL.aftLPPremSet);
				mLPGetSet.add(tReCalBL.aftLPGetSet);
			}
		}
		mMap.put(mLPPolSet, "INSERT");
		mMap.put(mLPDutySet, "INSERT");
		mMap.put(mLPPremSet, "INSERT");
		mMap.put(mLPGetSet, "INSERT");

		// 处理健康告知信息（团单）
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			VData cVData = new VData();
			cVData.add(mLCCustomerImpartSet);
			cVData.add(mGlobalInput);
			CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
			tCustomerImpartBL.submitData(cVData, "IMPART||DEAL");
			if (tCustomerImpartBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
				return false;
			}
			VData tempVData = new VData();
			tempVData = tCustomerImpartBL.getResult();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			try {
				tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			} catch (Exception e) {
				CError.buildErr(this, "告知信息处理失败!");
				return false;
			}
			LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
			LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
			for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
				tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLPCustomerImpartSchema, tLCCustomerImpartSet
						.get(i));
				tLPCustomerImpartSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPCustomerImpartSchema.setGrpContNo(tLPContSchema
						.getGrpContNo());
				tLPCustomerImpartSchema.setPrtNo(tLPContSchema.getPrtNo());
				if (tLPContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
			}

			for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
				tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
				tRef.transFields(tLPCustomerImpartParamsSchema,
						tLCCustomerImpartParamsSet.get(i));
				tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPCustomerImpartParamsSchema.setGrpContNo(tLPContSchema
						.getGrpContNo());
				tLPCustomerImpartParamsSchema
						.setPrtNo(tLPContSchema.getPrtNo());
				if (tLPContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				mLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
			}

			mMap.put(mLPCustomerImpartSet, "DELETE&INSERT");
			mMap.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");
		}

		mMap.put(mLPPersonSchema, "DELETE&INSERT");
		mMap.put(mLPAddressSchema, "DELETE&INSERT");

		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(CurrDate);
		mLPEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPEdorItemSchema, "UPDATE");
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------BGN-------
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpPolDB.setAppFlag("1");
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tRef.transFields(tLPGrpPolSchema, tLCGrpPolSet.get(i));
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setModifyDate(CurrDate);
			tLPGrpPolSchema.setModifyTime(CurrTime);
			tLPGrpPolSet.add(tLPGrpPolSchema);
		}

		String sql = "delete from lpgrppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		mMap.put(sql, "DELETE");

		mMap.put(tLPGrpPolSet, "INSERT");

		// 更新团体险种表表更后的保费、保额、份数
		sql = " update lpgrppol g set "
				+ " prem = prem - (select sum(c.prem-p.prem) from lcpol c, lppol p where c.polno = p.polno and p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " amnt = amnt - (select sum(c.amnt-p.amnt) from lcpol c, lppol p where c.polno = p.polno and p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " mult = mult - (select sum(c.mult-p.mult) from lcpol c, lppol p where c.polno = p.polno and p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "') "
				+ " where g.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' "
				+ " and g.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and g.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' ";
		mMap.put(sql, "UPDATE");
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------END-------

		mBqCalBase = new BqCalBase();

		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);
		return true;
	}

	private boolean delPData() {
		// 清除P表中上次保存过的数据
		String contno = mLPEdorItemSchema.getContNo();
		String edorno = mLPEdorItemSchema.getEdorNo();
		String edortype = mLPEdorItemSchema.getEdorType();

		String sqlWhere = " contno = '" + contno + "' and edorno = '" + edorno
				+ "' and edortype = '" + edortype + "'";

		String delLPPerson = " delete from lpperson where edorno = '" + edorno
				+ "' and edortype = '" + edortype
				+ "' and customerno in (select insuredno from lpinsured where "
				+ sqlWhere + ")";
		String delLPAddress = " delete from lpaddress where edorno = '"
				+ edorno + "' and edortype = '" + edortype
				+ "' and customerno in (select insuredno from lpinsured where "
				+ sqlWhere + ")";
		String delLPCustomerImpart = " delete from LPCustomerImpart where "
				+ sqlWhere
				+ " and customerno in (select insuredno from lpinsured where "
				+ sqlWhere + ")";
		String delLPCustomerImpartParams = " delete from LPCustomerImpartParams where "
				+ sqlWhere
				+ " and customerno in (select insuredno from lpinsured where "
				+ sqlWhere + ")";

		mMap.put(delLPPerson, "DELETE");
		mMap.put(delLPAddress, "DELETE");
		mMap.put(delLPCustomerImpart, "DELETE");
		mMap.put(delLPCustomerImpartParams, "DELETE");
		mMap.put("delete from lpinsured where" + sqlWhere, "DELETE");
		mMap.put("delete from lpcont where" + sqlWhere, "DELETE");
		mMap.put("delete from lppol where" + sqlWhere, "DELETE");
		mMap.put("delete from lpduty where" + sqlWhere, "DELETE");
		mMap.put("delete from lpget where" + sqlWhere, "DELETE");
		mMap.put("delete from lpprem where" + sqlWhere, "DELETE");
		return true;
	}

	/**
	 * 确认替换被保人的客户号
	 * 
	 * @return boolean
	 */
	private boolean CertifyCustomerNo() {
		String CustomerNo = "";
		if (mLPPersonSchema.getCustomerNo() == null
				|| mLPPersonSchema.getCustomerNo().equals("")) {
			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSet tLDPersonSet = new LDPersonSet();
			tLDPersonDB.setName(mLPPersonSchema.getName().trim());
			tLDPersonDB.setSex(mLPPersonSchema.getSex());
			tLDPersonDB.setBirthday(mLPPersonSchema.getBirthday());
			tLDPersonDB.setIDType(mLPPersonSchema.getIDType());
			tLDPersonDB.setIDNo(mLPPersonSchema.getIDNo());
			tLDPersonSet = tLDPersonDB.query();
			if (tLDPersonSet.size() < 1) {
				CustomerNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
			} else {
				CustomerNo = tLDPersonSet.get(1).getCustomerNo();
			}
			mLPPersonSchema.setMakeDate(CurrDate);
			mLPPersonSchema.setMakeTime(CurrTime);
			mLPAddressSchema.setMakeDate(CurrDate);
			mLPAddressSchema.setMakeTime(CurrTime);
			setCustomerNo(CustomerNo);
		} else {
			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSet tLDPersonSet = new LDPersonSet();
			tLDPersonDB.setCustomerNo(mLPPersonSchema.getCustomerNo());
			if (tLDPersonDB.getCount() == 0) {
				mLPPersonSchema.setMakeDate(CurrDate);
				mLPPersonSchema.setMakeTime(CurrTime);
				mLPAddressSchema.setMakeDate(CurrDate);
				mLPAddressSchema.setMakeTime(CurrTime);
				setCustomerNo(mLPPersonSchema.getCustomerNo());
			} else {
				tLDPersonDB.setName(mLPPersonSchema.getName().trim());
				tLDPersonDB.setSex(mLPPersonSchema.getSex());
				tLDPersonDB.setBirthday(mLPPersonSchema.getBirthday());
				tLDPersonDB.setIDType(mLPPersonSchema.getIDType());
				tLDPersonDB.setIDNo(mLPPersonSchema.getIDNo());
				tLDPersonSet = tLDPersonDB.query();
				if (tLDPersonSet.size() < 1) {
					CError.buildErr(this, "要件信息与已有信息不一致!");
					return false;
				} else {
					mLPPersonSchema.setMakeDate(tLDPersonSet.get(1)
							.getMakeDate());
					mLPPersonSchema.setMakeTime(tLDPersonSet.get(1)
							.getMakeTime());
				}
			}
		}
		mLPPersonSchema.setOperator(mGlobalInput.Operator);
		mLPPersonSchema.setModifyDate(CurrDate);
		mLPPersonSchema.setModifyTime(CurrTime);
		mLPAddressSchema.setOperator(mGlobalInput.Operator);
		mLPAddressSchema.setModifyDate(CurrDate);
		mLPAddressSchema.setModifyTime(CurrTime);
		return true;
	}

	/**
	 * 更新输入数据的客户号
	 * 
	 * @param aCustomerNo
	 *            String
	 * @return boolean
	 */
	private boolean setCustomerNo(String aCustomerNo) {
		mLPPersonSchema.setCustomerNo(aCustomerNo);
		mLPAddressSchema.setCustomerNo(aCustomerNo);
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= mLCCustomerImpartSet.size(); i++) {
				mLCCustomerImpartSet.get(i).setCustomerNo(aCustomerNo);
			}
		}
		return true;
	}

	/**
	 * 获得新的地址号。这里不再考虑P表了，就比较四个关键字段，如果与C表里的一条相同就修改这 一条，如果都不同则增加地址号。
	 */
	private boolean CertifyAddressNo() {
		String AddressNo = "";
		try {
			String tSql = "SELECT AddressNo" + " FROM LCAddress"
					+ " WHERE CustomerNo='"
					+ this.mLPAddressSchema.getCustomerNo() + "'"
					+ " and Province='" + this.mLPAddressSchema.getProvince()
					+ "'" + " and City='" + this.mLPAddressSchema.getCity()
					+ "'" + " and County='" + this.mLPAddressSchema.getCounty()
					+ "'" + " and PostalAddress='"
					+ this.mLPAddressSchema.getPostalAddress() + "'";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				AddressNo = StrTool.cTrim(tSSRS.GetText(1, 1));
				mLPAddressSchema.setAddressNo(AddressNo);
				mLPAddressSchema.setMakeDate(CurrDate);
				mLPAddressSchema.setMakeTime(CurrTime);
				return true;
			}
			tSql = "SELECT nvl(max(AddressNo),0)+1" + " FROM LCAddress"
					+ " WHERE CustomerNo='"
					+ this.mLPAddressSchema.getCustomerNo() + "'";
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSql);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				AddressNo = StrTool.cTrim(tSSRS.GetText(1, 1));
				mLPAddressSchema.setAddressNo(AddressNo);
				mLPAddressSchema.setMakeDate(CurrDate);
				mLPAddressSchema.setMakeTime(CurrTime);
				return true;
			}
			CError.buildErr(this, "未获得当前可操作地址号！");
			return false;
		} catch (Exception e) {
			CError.buildErr(this, "获得当前可操作地址号时产生错误！");
			return false;
		}
	}

	/**
	 * 获取领取间隔
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	public String getGetIntv(LCDutySchema pLCDutySchema, LCGetSet tLCGetSet) {
		String sql = " select getdutycode from lcget where polno = '"
				+ pLCDutySchema.getPolNo()
				+ "' and dutycode = '"
				+ pLCDutySchema.getDutyCode()
				+ "' and getdutycode in (select getdutycode from lmdutyget where type = '0')  and rownum = 1 ";
		ExeSQL tExeSQL = new ExeSQL();

		String sGetIntv = "";
		;
		String sGetDutyCode = tExeSQL.getOneValue(sql);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询领取间隔失败!");
			return null;
		}
		if (sGetDutyCode == null || sGetDutyCode.trim().equals("")) {
			logger.debug("没有养老金领取项");
			return "";
		}
		for (int j = 1; j <= tLCGetSet.size(); j++) {
			if (tLCGetSet.get(j).getGetDutyCode().equals(sGetDutyCode)) {
				return String.valueOf(tLCGetSet.get(j).getGetIntv());
			}
		}
		return sGetIntv;
	}

	/**
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
