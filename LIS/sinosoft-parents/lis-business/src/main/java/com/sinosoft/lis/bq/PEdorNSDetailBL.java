package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 新增附加险保全项目
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author Minim
 * @ReWrite by lizhuo 2005.07.20
 * @ReWrite by pst 2008.09.01
 * @version 1.0,2.0
 */



import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.tb.CustomerImpartBL;

public class PEdorNSDetailBL {
private static Logger logger = Logger.getLogger(PEdorNSDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String CValiDate;

	private String NewAddType;

	private String NewCValiDate;

//	private String EndDate;

	private boolean mFlag = false; // 是否按保单生效日生效

	private BqCalBase mBqCalBase = new BqCalBase();

	/** 保存主险保单号的数据 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	/** 传出的业务数据 */
	private LCPolSet mLCPolSet = new LCPolSet();

	private LPPolSet mLPPolSet = new LPPolSet();

	private LPDutySet mLPDutySet = new LPDutySet();

	private LPGetSet mLPGetSet = new LPGetSet();

	private LPPremSet mLPPremSet = new LPPremSet();

	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	
	private LJSGetEndorseSet mZKLJSGetEndorseSet = new LJSGetEndorseSet();
	
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	
	private LJSPayPersonSet mZKLJSPayPersonSet = new LJSPayPersonSet();
	
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();

	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();

	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();

	private LCContSchema mLCContSchema = new LCContSchema();
	
	private LPCSpecSet mLPCSpecSet = new LPCSpecSet();
	
	private LPBnfSet mLPBnfSet = new LPBnfSet();

	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();
	
	private Reflections tRef = new Reflections();

	public PEdorNSDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		if (!checkData()) {
			return false;
		}
		// 进行业务处理
		if (mOperate.equals("DELETE")) {
			if (!deleteData()) {
				return false;
			}
		} else {
			if (!dealData()) {
				return false;
			}
		}
		logger.debug("---End dealData---");

		// 需要传到后台处理
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			mLCPolSet = (LCPolSet) mInputData.getObjectByObjectName("LCPolSet",
					0);
			NewAddType = mLPEdorItemSchema.getStandbyFlag1();
			CValiDate = mLPEdorItemSchema.getStandbyFlag2();
			LCPolDB rLCPolDB = new LCPolDB();

			rLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
			if (!rLCPolDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PEdorNSDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获取主险保单的数据失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCPolSchema = rLCPolDB.getSchema();
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "PEdorNSDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 	新增附加险
       需求：长期：A：新增后期数必须是费率表中存在的要素值，周年生效，少于主险要素值。B：存在豁免的险种，不可以新增附加险。
            短期：指定生效日为整期。如果条款中存在短期费率表才可以做即时生效。
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		
		
		
		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if(tLCCSpecSet.size()>0)
		{
			for(int k=1;k<=tLCCSpecSet.size();k++)
			{
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				tRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}
		//投保人  为核保准备数据
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		tRef.transFields(tLPAppntSchema, tLCAppntSchema);
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSet.add(tLPAppntSchema);
		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = null;
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LPInsuredSchema tLPInsuredSchema = null;
		
		
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCInsuredSchema=tLCInsuredDB.query().get(1);
		tLPInsuredSchema = new LPInsuredSchema();
		tRef.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPAppntSet, "DELETE&INSERT");
		
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCPolDB.setAppFlag("2");
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet.size() < 1) {
			mErrors.addOneError("查询新增险种信息失败!");
			return false;
		}

		 double mGetMoney = 0.0;
		 double mChgPrem = 0.0;
		 double mChgAmnt = 0.0;
		 double mChgSumPrem = 0.0;
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			String PolNo = tLCPolSet.get(i).getPolNo();
			//EndDate = "";
			// // 计算短期费率
			double aRate = 1; // 短期费率 初始化为“1”
			double aPrem = 0.0; // 新增险种的总保费
			double oldPrem = 0.0;
			String tEndDate="";
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tRef.transFields(tLPPolSchema, tLCPolSet.get(i));
									
			
			LCBnfDB tLCBnfDB = new LCBnfDB();
			LCBnfSet tLCBnfSet = new LCBnfSet();
			tLCBnfDB.setPolNo(PolNo);
			tLCBnfSet = tLCBnfDB.query();
			if(tLCBnfSet.size()>0)
			{
				for(int k=1;k<=tLCBnfSet.size();k++)
				{
					LPBnfSchema tLPBnfSchema = new LPBnfSchema();
					tRef.transFields(tLPBnfSchema, tLCBnfSet.get(k));	
					tLPBnfSchema.setContNo(mLPEdorItemSchema.getContNo());
					tLPBnfSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPBnfSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					mLPBnfSet.add(tLPBnfSchema);
				}

			}
			
			String  tFlag= getDutyEndDate(tLPPolSchema, mLCPolSchema);
            if("Y".equals(tFlag))
            {
				mErrors.addOneError("新增附加险的责任止期不能大于主险!");
				return false;
            }

		    tFlag= getPayEndDate(tLPPolSchema, mLCPolSchema);
            if("Y".equals(tFlag))
            {
				mErrors.addOneError("新增附加险的缴费止期不能大于主险!");
				return false;
            }


			// 对于即时新增的计算短期费率
			if ("2".equals(NewAddType)) {
                String tCalCode="";
				String tCalCodeSQL="select calcode from lmedorcal where edortype='NS' and caltype='NSRATE' and"
					               +" riskcode='?riskcode?' and dutycode='000000'";
				ExeSQL tExeSQL=new ExeSQL();
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(tCalCodeSQL);
				sbv1.put("riskcode", tLPPolSchema.getRiskCode());
				tCalCode=tExeSQL.getOneValue(sbv1);
				if ("".equals(tCalCode)) {
				mErrors.addOneError("查询计算编码表失败,不可以做即时新增，请重新选择!");
				return false;
			    }
				
				String isLongRiskCodeSQL="select 'X' from lmriskapp where riskcode='?riskcode?' and riskperiod='L'";
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(isLongRiskCodeSQL);
				sbv2.put("riskcode", tLPPolSchema.getRiskCode());
				String lFLag=tExeSQL.getOneValue(sbv2);

				if("X".equals(lFLag))
				{
					   //判断主险是的生效日是否是整年期生效,即于主险保单年度保持区间跨度保持一致,即时是指定新增，其生效日也要么是预约新增，要么是追溯新增。
					    String tPreCavaliDate=getNewCValiDate(mLPEdorItemSchema.getPolNo(), "1");
					    String tNextCavaliDate=getNewCValiDate(mLPEdorItemSchema.getPolNo(), "3");	
                        int tPreIntv= PubFun.calInterval(tPreCavaliDate,CValiDate,"D");
                        int tNextIntv= PubFun.calInterval(CValiDate,tNextCavaliDate,"D");
					    if(tNextIntv!=0 && tPreIntv!=0)
					    {
							mErrors.addOneError("即时新增的长期附加险必须整年日生效");
							return false;
					    }
				}
				int tAppAge = PubFun.calInterval(tLPPolSchema
						.getInsuredBirthday(), mLPEdorItemSchema
						.getEdorValiDate(), "Y");
				logger.debug("EdorValiDate"
						+ mLPEdorItemSchema.getEdorValiDate());
				logger.debug("EndDate" + tLPPolSchema.getEndDate());
				int tNSMonths = PubFun.calInterval2(mLPEdorItemSchema
						.getEdorValiDate(), tLPPolSchema.getEndDate(), "M");


				String tJob = tLCInsuredSchema.getOccupationType();

				BqCalBL tBqCalBL = new BqCalBL();

				oldPrem = tLPPolSchema.getPrem();
				BqCalBase tBqCalBase = new BqCalBase();
				
				tBqCalBase.setJob(tJob);
				tBqCalBase.setGet(oldPrem);
				tBqCalBase.setAppAge(tAppAge);
				tBqCalBase.setContNo(tLPPolSchema.getContNo());
				tBqCalBase.setPolNo(tLPPolSchema.getPolNo());
				tBqCalBase.setAAYears(tNSMonths);
				tBqCalBase.setGrpPolNo(tLPPolSchema.getGrpPolNo());
				tBqCalBase.setInsuYearFlag("D");
				tBqCalBase.setInsuYear(PubFun.calInterval(CValiDate, tLPPolSchema.getEndDate(), "D"));
				tBqCalBL.setLPEdorItemSchema(mLPEdorItemSchema);
				aRate = tBqCalBL.calChgMoney(tCalCode,
						tBqCalBase); // 短期汇率
				if(aRate==0)
				{
					mErrors.addOneError("计算短期费率失败!");
					return false;
				}								
			}
			// Pol

			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLPPolSchema.setPrem(tLPPolSchema.getPrem() * aRate);
			tLPPolSchema.setSumPrem(tLPPolSchema.getPrem() * aRate);

			mChgAmnt += tLPPolSchema.getAmnt();
			mChgPrem+= tLPPolSchema.getPrem();
			mChgSumPrem += tLPPolSchema.getSumPrem();
			// Duty
			LCDutyDB tLCDutyDB = new LCDutyDB();
			LCDutySet tLCDutySet = new LCDutySet();
			tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
			tLCDutySet = tLCDutyDB.query();
			if (tLCDutySet.size() != 1) {
				mErrors.addOneError("查询新增险种责任不符合规范!");
				return false;
			}
			for (int k = 1; k <= tLCDutySet.size(); k++) {
				LPDutySchema tLPDutySchema = new LPDutySchema();
				tRef.transFields(tLPDutySchema, tLCDutySet.get(k));
				tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDutySchema.setPrem(tLPDutySchema.getPrem() * aRate);
				tLPDutySchema.setSumPrem(tLPDutySchema.getPrem() * aRate);
				tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
				tLPDutySchema.setModifyTime(PubFun.getCurrentTime());
				mLPDutySet.add(tLPDutySchema);

			}

			// Prem
			LCPremDB tLCPremDB = new LCPremDB();
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
			tLCPremDB.setDutyCode(tLCDutySet.get(1).getDutyCode());
			tLCPremSet = tLCPremDB.query();
			if (tLCPremSet.size() < 1) {
				mErrors.addOneError("查询新增保费项失败!");
				return false;
			}
			
			LCDiscountSet tLCDiscountSet = new LCDiscountSet();
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			tLCDiscountDB.setPolNo(tLPPolSchema.getPolNo());
			tLCDiscountSet = tLCDiscountDB.query();
			LPDiscountSet tLPDiscountSet = new LPDiscountSet();
			if(tLCDiscountSet!=null && tLCDiscountSet.size()>0){
				for(int j=1;j<=tLCDiscountSet.size();j++){
					LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
					tRef.transFields(tLPDiscountSchema, tLCDiscountSet.get(j));
					tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPDiscountSet.add(tLPDiscountSchema);
				}
				mLPDiscountSet.add(tLPDiscountSet);
			}
			
			LPPremSet tLPPremSet = new LPPremSet();
			for (int j = 1; j <= tLCPremSet.size(); j++) {
				LPPremSchema tLPPremSchema = new LPPremSchema();
				tRef.transFields(tLPPremSchema, tLCPremSet.get(j));

				tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
				tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
				tLPPremSchema.setPrem(tLPPremSchema.getPrem() * aRate);
				tLPPremSchema.setSumPrem(tLPPremSchema.getPrem() * aRate);
				tLPPremSet.add(tLPPremSchema);
				//mLPPremSetadd(tLPPremSchema);
                //预约新增不产生补退费
//				if(!"1".equals(NewAddType))
//				{}

				// 生产个人应收信息
				LJSPayPersonSchema mLJSPayPersonSchema = new LJSPayPersonSchema();

				mLJSPayPersonSchema.setPolNo(tLPPolSchema.getPolNo());
				mLJSPayPersonSchema.setPayCount(1);
				mLJSPayPersonSchema.setPayAimClass("1");
				mLJSPayPersonSchema.setDutyCode(tLPPremSchema.getDutyCode());
				mLJSPayPersonSchema.setPayPlanCode(tLPPremSchema
						.getPayPlanCode());
				mLJSPayPersonSchema.setPayType("NS");
				mLJSPayPersonSchema.setGrpContNo("00000000000000000000");
				mLJSPayPersonSchema.setGrpPolNo("00000000000000000000");
				mLJSPayPersonSchema.setContNo(tLPPolSchema.getContNo());
				mLJSPayPersonSchema.setManageCom(mLCContSchema.getManageCom());
				mLJSPayPersonSchema.setRiskCode(tLPPolSchema.getRiskCode());
				mLJSPayPersonSchema.setAgentCom(tLPPolSchema.getAgentCom());
				mLJSPayPersonSchema.setAgentCode(tLPPolSchema.getAgentCode());
				mLJSPayPersonSchema.setAgentGroup(tLPPolSchema.getAgentGroup());
				mLJSPayPersonSchema.setAppntNo(tLPPolSchema.getAppntNo());
				mLJSPayPersonSchema.setSumDuePayMoney(tLPPremSchema.getPrem()
						* aRate);
				mLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
				mLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
				mLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
				mLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
				mLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
				mLJSPayPersonSchema.setPayDate(mLPEdorItemSchema
						.getEdorValiDate());
				mLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema
						.getEdorValiDate());
				mLJSPayPersonSchema.setCurPayToDate(tEndDate);
				mLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema
						.getEdorNo());
				mLJSPayPersonSchema.setCurrency(tLPPolSchema.getCurrency());
				//营改增 add zhangyingfeng 2016-07-13
				//价税分离 计算器
				TaxCalculator.calBySchema(mLJSPayPersonSchema);
				//end  zhangyingfeng 2016-07-13
				mLJSPayPersonSet.add(mLJSPayPersonSchema);

				// 生成批改补退费信息
				LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
				if (tLJSGetEndorseSchema == null) {
					mErrors.addOneError("生产批改补退费信息失败!");
					return false;
				}
				tLJSGetEndorseSchema.setPolNo(PolNo);
				tLJSGetEndorseSchema.setGetMoney(tLPPremSchema.getPrem()
						* aRate);
				mGetMoney+=tLPPremSchema.getPrem()* aRate;
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem);
				tLJSGetEndorseSchema.setManageCom(mLCContSchema.getManageCom());
				tLJSGetEndorseSchema.setDutyCode(mLJSPayPersonSchema
						.getDutyCode());
				tLJSGetEndorseSchema.setPayPlanCode(mLJSPayPersonSchema
						.getPayPlanCode());
				tLJSGetEndorseSchema.setCurrency(tLPPolSchema.getCurrency());
				//营改增 add zhangyingfeng 2016-07-13
				//价税分离 计算器
				TaxCalculator.calBySchema(tLJSGetEndorseSchema);
				//end  zhangyingfeng 2016-07-13
				mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			
				
			}
			mLPPremSet.add(tLPPremSet);
			
			LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("PayCount", "1");
			tTransferData.setNameAndValue("PayIntv", String.valueOf(tLPPolSchema.getPayIntv()));
			tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);
			PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
			VData tzkVData = new VData();
			tzkVData.add(tLPPolSchema);
			tzkVData.add(tLPPremSet);
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
					if (!createLJSGetEndorseSchemaZK(mLPEdorItemSchema,tLPPolSchema,tLJSPayPersonSet.get(j))) {
						mErrors.addOneError("添加保费LJSGetEndorse!");
					}
				}
				//营改增 add zhangyingfeng 2016-07-13
				//价税分离 计算器
				TaxCalculator.calBySchemaSet(tLJSPayPersonSet);
				//end  zhangyingfeng 2016-07-13
				mZKLJSPayPersonSet.add(tLJSPayPersonSet);
			}
			
			// Get
			LCGetDB tLCGetDB = new LCGetDB();
			LCGetSet tLCGetSet = new LCGetSet();
			tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
			tLCGetDB.setContNo(tLPPolSchema.getContNo());
			tLCGetSet = tLCGetDB.query();
			if (tLCGetSet.size() < 1) {
				mErrors.addOneError("新增领取项不符合规则!");
				return false;
			}

			for (int a = 1; a <= tLCGetSet.size(); a++) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				tRef.transFields(tLPGetSchema, tLCGetSet.get(a));

				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
				tLPGetSchema.setModifyTime(PubFun.getCurrentTime());
				mLPGetSet.add(tLPGetSchema);
			}
			logger.debug("险种的生效日期：" + tLPPolSchema.getCValiDate());
			logger.debug("险种的责任止期：" + tLPPolSchema.getEndDate());
			mLPPolSet.add(tLPPolSchema);

		}

		// 健康告知处理
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema();
			for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
				aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
				aLCCustomerImpartSchema.setGrpContNo(mLCContSchema
						.getGrpContNo());
				if (mLCContSchema.getGrpContNo() == null) {
					aLCCustomerImpartSchema
							.setGrpContNo("00000000000000000000");
				}
				aLCCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				logger.debug("===================== PrtNo ==========="
						+ mLCContSchema.getPrtNo());
				aLCCustomerImpartSchema.setCustomerNo(mLCContSchema
						.getInsuredNo());
				aLCCustomerImpartSchema.setCustomerNoType("1");
				mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
				logger.debug(mLCCustomerImpartSet.get(k).getCustomerNo());
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			logger.debug("654654987356");
			VData cVData = new VData();
			cVData.add(mLCCustomerImpartSet);
			cVData.add(mGlobalInput);
			CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
			tCustomerImpartBL.submitData(cVData, "IMPART||DEAL");
			mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
			if (tCustomerImpartBL.mErrors.needDealError()) {

				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = tCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
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
				CError.buildErr(this, "接受数据失败!");
				return false;
			}


			if (tLCCustomerImpartSet != null && tLCCustomerImpartSet.size() > 0) {
				for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
					LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
					tRef.transFields(tLPCustomerImpartSchema,
							tLCCustomerImpartSet.get(i));
					tLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPCustomerImpartSchema
							.setGrpContNo("00000000000000000000");
					tLPCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
				}
			}
			if (tLCCustomerImpartParamsSet != null
					&& tLCCustomerImpartParamsSet.size() > 0) {
				for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
					LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
					tRef.transFields(tLPCustomerImpartParamsSchema,
							tLCCustomerImpartParamsSet.get(i));

					tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPCustomerImpartParamsSchema
							.setGrpContNo("00000000000000000000");
					tLPCustomerImpartParamsSchema.setPrtNo(mLCContSchema
							.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartParamsSet
							.add(tLPCustomerImpartParamsSchema);
				}
			}
		}
		
		
		//删除多次保存的告知
		//删除多次保存的告知 NS modify by jiaqiangli 2009-04-13
		String tSqla = "DELETE FROM LPCustomerImpart WHERE LPCustomerImpart.EdorType='?EdorType?' and exists (select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPCustomerImpart.EdorNo)";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(tSqla);
		sbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv3.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		mMap.put(sbv3, "DELETE");
		String tSqlb = "DELETE FROM LPCustomerImpartParams WHERE LPCustomerImpartParams.EdorType='?EdorType?' and exists (select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPCustomerImpartParams.EdorNo)";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(tSqlb);
		sbv4.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv4.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		mMap.put(sbv4, "DELETE");
		
		if (mLPCustomerImpartSet.size() > 0) {
			mMap.put(mLPCustomerImpartSet, "DELETE&INSERT");
		}
		if (mLPCustomerImpartParamsSet.size() > 0) {
			mMap.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");
		}
		
		LPContSchema tLPContSchema = new LPContSchema();
		tRef.transFields(tLPContSchema, mLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType("NS");
		tLPContSchema.setPrem(mLCContSchema.getPrem() + mChgPrem);
		tLPContSchema.setSumPrem(mLCContSchema.getSumPrem() + mChgSumPrem);
		tLPContSchema.setAmnt(mLCContSchema.getAmnt() + mChgAmnt);
		mMap.put(tLPContSchema, "DELETE&INSERT");


		String tUPDATESQL = "update LPEdorItem set ChgAmnt=?mChgAmnt?,GetMoney=?mGetMoney?,ChgPrem=?mChgPrem?,"
				+ "StandbyFlag1='?NewAddType?',StandbyFlag2='?CValiDate?',EdorState='3'" + " where edorno='?edorno?' and edortype='NS' and polno='?polno?'";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(tUPDATESQL);
		sbv5.put("mChgAmnt", mChgAmnt);
		sbv5.put("mGetMoney", mGetMoney);
		sbv5.put("mChgPrem", mChgPrem);
		sbv5.put("NewAddType", NewAddType);
		sbv5.put("CValiDate", CValiDate);
		sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv5.put("polno", mLPEdorItemSchema.getPolNo());

		mMap.put(sbv5, "UPDATE");
		mMap.put(mLPBnfSet, "DELETE&INSERT");
		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");
		mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
		mMap.put(mLJSPayPersonSet, "DELETE&INSERT");
		if(mLPDiscountSet.size()>0){
			mMap.put(mLPDiscountSet, "DELETE&INSERT");
		}
		if(mZKLJSGetEndorseSet.size()>0){
			mMap.put(mZKLJSGetEndorseSet, "DELETE&INSERT");
		}
		if(mZKLJSPayPersonSet.size()>0){
			mMap.put(mZKLJSPayPersonSet, "DELETE&INSERT");
		}

		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());

		mLPEdorItemSchema = tLPEdorItemDB.query().get(1);

//		// 预约新增，险种的生效日期为主险的下个周年日
//		if ("1".equals(NewAddType)) {
//			CValiDate = getNewCValiDate(mLPEdorItemSchema.getPolNo(), "1");
//			if ("".equals(CValiDate)) {
//				mErrors.addOneError("获取新增附加险生效日失败!");
//				return false;
//			}
//		}
//		// 追溯新增，险种的生效日期为主险的前个周年日
//		if ("3".equals(NewAddType)) {
//			CValiDate = getNewCValiDate(mLPEdorItemSchema.getPolNo(), "3");
//			if ("".equals(CValiDate)) {
//				mErrors.addOneError("获取新增附加险生效日失败!");
//				return false;
//			}
//		}
//		if ("2".equals(NewAddType)) {
//			CValiDate = NewCValiDate;
//		}

		// CValiDate = mLPEdorItemSchema.getEdorValiDate();
		//
		// // 校验是否按保单生效日生效
		// String strSQL = "select cvalidate from lccont where contno = '"
		// + mLPEdorItemSchema.getContNo() + "'";
		// SSRS tSSRS = new SSRS();
		// ExeSQL tExeSQL = new ExeSQL();
		// tSSRS = tExeSQL.execSQL(strSQL);
		// if (tSSRS == null || tSSRS.getMaxRow() < 1) {
		// mErrors.addOneError("查询保单信息失败!");
		// return false;
		// }
		// String OldCValidate = tSSRS.GetText(1, 1);
		// String Old = OldCValidate;
		// FDate tFDate = new FDate();
		// int n = 1;
		// while (tFDate.getDate(OldCValidate).before(
		// tFDate.getDate(mLPEdorItemSchema.getEdorValiDate()))) {
		// OldCValidate = PubFun.calDate(Old, n, "Y", null);
		// n++;
		// }
		// EndDate = OldCValidate;
		// int tCount = PubFun.calInterval2(mLPEdorItemSchema.getEdorValiDate(),
		// OldCValidate, "D");
		// if (tCount <= 30) { // 新的生效日期和止期
		// CValiDate = OldCValidate;
		// EndDate = PubFun.calDate(CValiDate, 1, "Y", null);
		// mFlag = true;
		// }

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(mMap);
			mResult.add(mBqCalBase);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "PEdorNSDetailBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean deleteData() {

		if (mLCPolSet == null || mLCPolSet.size() < 1) {
			mErrors.addOneError("请选择要删除的险种!");
			return false;
		}

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			String PolNo = mLCPolSet.get(i).getPolNo();
			String delEndorse = "delete from LJSGetEndorse where GetNoticeNo = '?GetNoticeNo?' and EndorsementNo = '?GetNoticeNo?' and FeeOperationType = 'NS' and PolNo = '?PolNo?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(delEndorse);
			sbv1.put("GetNoticeNo", mLPEdorItemSchema.getEdorNo());
			sbv1.put("PolNo", PolNo);
			String delPayPerson = "delete from LJSPayPerson where polno = '?PolNo?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(delPayPerson);
			sbv2.put("PolNo", PolNo);
			String delLCPol = "delete from lcpol where polno = '?PolNo?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(delLCPol);
			sbv3.put("PolNo", PolNo);
			String delLCSpec = "delete from LCSpec where polno = '?PolNo?'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(delLCSpec);
			sbv4.put("PolNo", PolNo);
			String delLCBnf = "delete from lcbnf where polno = '?PolNo?'";
			SQLwithBindVariables sbv5=new SQLwithBindVariables();
			sbv5.sql(delLCBnf);
			sbv5.put("PolNo", PolNo);
			String delLCDuty = "delete from lcduty where polno = '?PolNo?'";
			SQLwithBindVariables sbv6=new SQLwithBindVariables();
			sbv6.sql(delLCDuty);
			sbv6.put("PolNo", PolNo);
			String delLCPrem = "delete from lcprem where polno = '?PolNo?'";
			SQLwithBindVariables sbv7=new SQLwithBindVariables();
			sbv7.sql(delLCPrem);
			sbv7.put("PolNo", PolNo);
			String delLCGet = "delete from lcget where polno = '?PolNo?'";
			SQLwithBindVariables sbv8=new SQLwithBindVariables();
			sbv8.sql(delLCGet);
			sbv8.put("PolNo", PolNo);
			String delLPPol = "delete from lppol where edortype = 'NS' and edorno = '?edorno?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv9=new SQLwithBindVariables();
			sbv9.sql(delLPPol);
			sbv9.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv9.put("PolNo", PolNo);
			String delLPDuty = "delete from lpduty where edortype = 'NS' and edorno = '?edorno?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv10=new SQLwithBindVariables();
			sbv10.sql(delLPDuty);
			sbv10.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv10.put("PolNo", PolNo);
			String delLPPrem = "delete from lpprem where edortype = 'NS' and edorno = '?edorno?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv11=new SQLwithBindVariables();
			sbv11.sql(delLPPrem);
			sbv11.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv11.put("PolNo", PolNo);
			String delLPGet = "delete from lpget where edortype = 'NS' and edorno = '?edorno?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv12=new SQLwithBindVariables();
			sbv12.sql(delLPGet);
			sbv12.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv12.put("PolNo", PolNo);
			String updateLPedorItem="update LPEdorItem set ChgAmnt=0,GetMoney=0,ChgPrem=0,"
			        + "StandbyFlag1=null,StandbyFlag2=null,EdorState='3'" + " where edorno='?edorno?' and edortype='NS' and Polno='?Polno?'";
			SQLwithBindVariables sbv13=new SQLwithBindVariables();
			sbv13.sql(updateLPedorItem);
			sbv13.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv13.put("Polno", mLPEdorItemSchema.getPolNo());
			String delLPImPar = "delete from LPCustomerImpart where edortype = 'NS' and edorno = '?edorno?' and ContNo = '?ContNo?'";
			SQLwithBindVariables sbv14=new SQLwithBindVariables();
			sbv14.sql(delLPImPar);
			sbv14.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv14.put("ContNo", mLPEdorItemSchema.getContNo());
			String delLPImParas = "delete from LPCustomerImpartParams where edortype = 'NS' and edorno = '?edorno?' and ContNo = '?ContNo?'";
			SQLwithBindVariables sbv15=new SQLwithBindVariables();
			sbv15.sql(delLPImParas);
			sbv15.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv15.put("ContNo", mLPEdorItemSchema.getContNo());
			
			String delLPCont = "delete from LPCont where edortype = 'NS' and edorno = '?edorno?' and ContNo = '?ContNo?'";
			SQLwithBindVariables sbv16=new SQLwithBindVariables();
			sbv16.sql(delLPCont);
			sbv16.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv16.put("ContNo", mLPEdorItemSchema.getContNo());
			
			String delLPBnf = "delete from LPBnf where edortype = 'NS' and edorno = '?edorno?' and ContNo = '?ContNo?' and  polno = '?PolNo?'";
			SQLwithBindVariables sbv17=new SQLwithBindVariables();
			sbv17.sql(delLPBnf);
			sbv17.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv17.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv17.put("PolNo", PolNo);
			
			String delLPSpec = "delete from LPSpec where edortype = 'NS' and edorno = '?edorno?' and ContNo = '?ContNo?' and  polno = '?PolNo?'";
			SQLwithBindVariables sbv18=new SQLwithBindVariables();
			sbv18.sql(delLPSpec);
			sbv18.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv18.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv18.put("PolNo", PolNo);
			
			mMap.put(sbv1, "DELETE");
			mMap.put(sbv2, "DELETE");
			mMap.put(sbv3, "DELETE");
			mMap.put(sbv5, "DELETE");
			mMap.put(sbv4, "DELETE");
			mMap.put(sbv6, "DELETE");
			mMap.put(sbv7, "DELETE");
			mMap.put(sbv8, "DELETE");
			mMap.put(sbv9, "DELETE");
			mMap.put(sbv10, "DELETE");
			mMap.put(sbv11, "DELETE");
			mMap.put(sbv12, "DELETE");
			mMap.put(sbv13, "UPDATE");
			mMap.put(sbv14, "DELETE");
			mMap.put(sbv15, "DELETE");
			mMap.put(sbv17, "DELETE");
			mMap.put(sbv18, "DELETE");
			mMap.put(sbv16, "DELETE");
		}

		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());

		// 删除完毕,应该更新保全状态,防止保存后删除
		// XinYQ added on 2007-03-08
//		mLPEdorItemSchema.setEdorState("3");
//		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
//		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
//		mMap.put(mLPEdorItemSchema, "UPDATE");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		Reflections tRef = new Reflections();
		// mPayCount++;
		tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		// mLJSGetEndorseSchema.setFeeFinaType("BF");
		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),
				strfinType, mLPEdorItemSchema.getPolNo());
		if (finType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
			return null;
		}
		tLJSGetEndorseSchema.setFeeFinaType(finType);
		tLJSGetEndorseSchema.setDutyCode("0");
		tLJSGetEndorseSchema.setPayPlanCode("0");

		// 走保全交费财务流程
		tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付

		tRef.transFields(tLJSGetEndorseSchema, mLCPolSchema);

		tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);

		tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSGetEndorseSchema.setGetFlag("0");

		// mLJSGetEndorseSchema.setSerialNo(mLJSPaySchema.getSerialNo());
		// mLJSGetEndorseSchema.setGetMoney(mLJSPaySchema.getSumDuePayMoney());

		return tLJSGetEndorseSchema;
	}

	/**根据保单号获取新增附加险的生效日
	 * */
	public String getNewCValiDate(String tPolNo, String tFlag) {
		String tCValiDate = "";
		String tSQL = "select cvalidate from lcpol where polno=mainpolno and polno='?tPolNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("tPolNo", tPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		tCValiDate = tExeSQL.getOneValue(sqlbv);
		if ("".equals(tCValiDate) || tCValiDate == null) {
			return "";
		} else {
			// 预约新增
			if ("1".equals(tFlag)) {
				// 获取本年度生效日
				String tCurDate = calDate(Integer.parseInt(theCurrentDate
						.substring(0, 4)), tCValiDate);
				// 下年度周年日
				String tNextCValiDate = PubFun.calDate(tCurDate, 1, "Y", "");
//				int tIntv = PubFun.calInterval(mLPEdorItemSchema
//						.getEdorValiDate(), tNextCValiDate, "D");
//				// 倘若此保全的生效日大于保单下一个生效日则返回
//				if (tIntv < 0) {
//					return "";
//				}

				int tIntvC = PubFun.calInterval(mLPEdorItemSchema
						.getEdorValiDate(), tCurDate, "D");
				// 此次保全在主险保单本年度生效日之前申请则生效日为本年度生效日
				if (tIntvC > 0) {
					tCValiDate = tCurDate;
				} else // 此次保全在主险保单本年度生效日之后申请则生效日为次年度生效日
				{
					tCValiDate = tNextCValiDate;
				}
			} // 追溯新增
			else if ("3".equals(tFlag)) {
				// 获取本年度生效日
				String tCurDate = calDate(Integer.parseInt(theCurrentDate
						.substring(0, 4)), tCValiDate);
				// 下年度周年日
				String tPreCValiDate = PubFun.calDate(tCurDate, -1, "Y", "");
				int tIntv = 0;
				int tIntvC = PubFun.calInterval(mLPEdorItemSchema
						.getEdorValiDate(), tCurDate, "D");
				// 此次保全在主险保单本年度生效日之前申请则生效日为本年度生效日
				if (tIntvC < 0) {
					tCValiDate = tCurDate;
				} else // 此次保全在主险保单本年度生效日之后申请则生效日为前一年度生效日
				{
					tCValiDate = tPreCValiDate;
				}
			}

		}
		return tCValiDate;
	}

	/**
	 * 得到tDate在tYear这一年的对应日
	 * 
	 * @param tYear
	 *            所在年度
	 * @param tDate
	 *            日期
	 * @return String : tDate在tYear这一年的对应日
	 */
	private String calDate(int tYear, String tDate) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 如果tEdorValiDate是2月29日，而其上一年不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 2;
				tDay = 28;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}

		return coDate;
	}

	/**
	 * 闰年校验
	 * 
	 * @param mYear
	 *            年度
	 * @return boolean 闰年:true 平年：false
	 */
	private boolean isLeap(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}
   /**判断主附加险的责任止期大小*/
	public String getDutyEndDate(LPPolSchema tLPPolSchema, LCPolSchema mLCPolSchema) {

		int tIntvC = PubFun.calInterval(mLCPolSchema.getEndDate(), tLPPolSchema
				.getEndDate(), "D");
		if (tIntvC > 0) {
			return "Y";
		} else {
			return "N";
		}
	}
	   /**判断主附加险的缴费止期大小*/
	public String getPayEndDate(LPPolSchema tLPPolSchema, LCPolSchema mLCPolSchema) {

		int tIntvC = PubFun.calInterval(mLCPolSchema.getPayEndDate(), tLPPolSchema
				.getPayEndDate(), "D");
		if (tIntvC > 0) {
			return "Y";
		} else {
			return "N";
		}
	}
	
	private boolean createLJSGetEndorseSchemaZK(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, LJSPayPersonSchema cLJSPayPersonSchema) {

		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initLJSGetEndorse(tLPEdorItemSchema,tLPPolSchema,"ZK");
		tRef.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
		tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());

		tLJSGetEndorseSchema.setSubFeeOperationType(cLJSPayPersonSchema.getPayType());
		//营改增 add zhangyingfeng 2016-07-13
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		//end  zhangyingfeng 2016-07-13
		mZKLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}
	
	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, String strfinType) {
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
		tRef.transFields(tLJSGetEndorseSchema, tLPPolSchema);
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

	/**
	 * 主函数，测试用
	 */
	// public static void main(String[] args)
	// {
	// PEdorNSDetailBL PE = new PEdorNSDetailBL();
	// GlobalInput tg = new GlobalInput();
	// tg.Operator = "lee";
	// tg.ManageCom = "86";
	// LPEdorItemSchema td = new LPEdorItemSchema();
	// LPEdorItemDB dd = new LPEdorItemDB();
	// LCCustomerImpartSet tdd = new LCCustomerImpartSet();
	// dd.setEdorNo("6020050901000128");
	// td = dd.query().get(1);
	// VData a = new VData();
	// a.add(tg);
	// a.add(td);
	// a.add(tdd);
	// PE.submitData(a, "");
	//
	// }
}
