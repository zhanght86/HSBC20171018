package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPDiscountDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.AccountManage;
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
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.tb.CustomerImpartBL;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
//import com.sun.org.glassfish.external.arc.Taxonomy;

/**
 * <p>
 * Title: 保全项目报单复效明细
 * </p>
 * 
 * <p>
 * Description:
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
 * @author lizhuo
 * @version 1.0
 */
public class PEdorREDetailBL {
private static Logger logger = Logger.getLogger(PEdorREDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private BqCalBase mBqCalBase = new BqCalBase();

	private Reflections mRef = new Reflections();

	/** 全局数据 */
	private MMap map = new MMap();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private Reflections mReflections = new Reflections();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	private LJSGetEndorseSet mZKLJSGetEndorseSet = new LJSGetEndorseSet();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();
	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LPContStateSet mLPContStateSet = new LPContStateSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LCDiscountSet mLCDiscountSet = new LCDiscountSet();
	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();

	double mGetMoney = 0;
	double mTotalPrem = 0;
	double mPrem = 0;
	double mTotalInterest = 0;
	double mInterest = 0;
	double mAddJK = 0;
	double mAddZY = 0;
	double mAutoPay = 0;
	double mYearGet = 0;
	double mRate = 0;
	private int tPaySTime;

	private String mPolNo;

	private double totalMoney = 0; // 记录本次保全应收总金额
	private LPReturnLoanSet aLPReturnLoanSet = new LPReturnLoanSet(); // 保存还款明细
	
	//借用standbyflag3来保存是否计息（应用于应交未交，借款，垫款） 默认计息
	String tisCalInterest = "on";

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
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

		// 数据处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData.getObjectByObjectName("LCCustomerImpartSet", 0);
			
			//是否计息
			tisCalInterest = mLPEdorItemSchema.getStandbyFlag3();
			
			//add by jiaqiangli 2009-04-10
			if (tisCalInterest == null || tisCalInterest.equals("")) {
				tisCalInterest = "off";
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			mErrors.addOneError("接收数据失败!");
			return false;
		}
		if (mLPEdorItemSchema == null) {
			mErrors.addOneError("接受数据无效!");
			return false;
		}

		if (!"NOQUERY".equals(mOperate)) {
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPEdorItemSet = tLPEdorItemDB.query();
			if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
				CError.buildErr(this, "查询保全项目信息失败!");
				return false;
			}
			mLPEdorItemSchema = tLPEdorItemSet.get(1);
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据计算处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		logger.debug("start to dealData in re");
		
		//为核保准备数据
		//投保人
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		mReflections.transFields(tLPAppntSchema, tLCAppntSchema);
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
		mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		map.put(tLPInsuredSet, "DELETE&INSERT");
		map.put(tLPAppntSet, "DELETE&INSERT");
		
	    //告知
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
				mRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			map.put(mLPCSpecSet, "DELETE&INSERT");
		}
		
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		//add by jiaqiangl 2009-03-11 此处要修改三处状态 payprem,loan下的lccontstate.polno存的是6个0
		String e_sql = "select * from lccontstate where contno = '?contno?' and statetype in ('Available','PayPrem','Loan') and state = '1' and EndDate is null "
				//+ "and polno not in (select polno from lccontstate where contno = '" + mLPEdorItemSchema.getContNo()
				//+ "' and statetype = 'Terminate' and state = '1' and EndDate is null and polno=lccontstate.polno)"
				;
		logger.debug(e_sql);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(e_sql);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		if (tLCContStateSet.size() < 1) {
			mErrors.addOneError("此保单没有失效险种!");
			return false;
		}

		// 处理Available
		for (int j = 1; j <= tLCContStateSet.size(); j++) {
			LPContStateSchema tLPContStateSchema = new LPContStateSchema();
			LPContStateSchema cLPContStateSchema = new LPContStateSchema();
			mRef.transFields(tLPContStateSchema, tLCContStateSet.get(j));
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
			cLPContStateSchema.setSchema(tLPContStateSchema);
			tLPContStateSchema.setEndDate(PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(), -1, "D", null));
			//新状态无原因，需要清空，只有失效，挂失，终止记录才有原因
			cLPContStateSchema.setStateReason("");
			cLPContStateSchema.setState("0");
			cLPContStateSchema.setStartDate(mLPEdorItemSchema.getEdorValiDate());
			cLPContStateSchema.setMakeDate(PubFun.getCurrentDate());
			cLPContStateSchema.setMakeTime(PubFun.getCurrentTime());

			FDate tFD = new FDate();
			if (tFD.getDate(tLPContStateSchema.getStartDate()).equals(tFD.getDate(mLPEdorItemSchema.getEdorValiDate()))) {
				tLPContStateSchema.setEndDate(tLPContStateSchema.getStartDate());
				cLPContStateSchema.setStartDate(PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(), 1, "D", null));
			}

			mLPContStateSet.add(tLPContStateSchema);
			mLPContStateSet.add(cLPContStateSchema);
		}
		map.put(mLPContStateSet, "DELETE&INSERT");

		// 同时需要处理loan和payprem lccontstate

		// 取得保单信息(不处理保全变动带来的影响)
		LCPolSet tLCPolSet = new LCPolSet();
		ExeSQL tPolExeSQL = new ExeSQL();
		SSRS tPolSSRS = new SSRS();
		//add by jiaqiangl 2009-03-11 取保单时只需要取出Available即可
		String tPolSQL = "select distinct polno from lccontstate where contno = '?contno?' and statetype in ('Available') and state = '1' and EndDate is null "
				//+ "and polno not in (select polno from lccontstate where contno = '" + mLPEdorItemSchema.getContNo()
				//+ "' and statetype = 'Terminate' and state = '1' and EndDate is null and polno=lccontstate.polno)"
				;
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tPolSQL);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		tPolSSRS = tPolExeSQL.execSQL(sbv);
		// deal polno
		for (int i = 1; i <= tPolSSRS.getMaxRow(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet aLCPolSet = new LCPolSet();
			String i_sql = "select * from LCPol where ContNo = '?ContNo?' and (payintv <> 0 or payintv = 0 and not exists (select 1  from lmrisk "
					+ " where riskcode = lcpol.riskcode and rnewflag = 'Y')) and payintv <> -1 and PolNo = '?PolNo?' and appflag = '1'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(i_sql);
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv1.put("PolNo", tPolSSRS.GetText(i, 1));
			aLCPolSet = tLCPolDB.executeQuery(sbv1);
			tLCPolSet.add(aLCPolSet);
		}

		// 按险种层次进行处理
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			mLCPolSchema.setSchema(tLCPolSet.get(j));
			mPolNo = mLCPolSchema.getPolNo();

			// 移植lis5.3程序
			/* 第一部分：计算借款及利息 loloan */
			if(mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo()))
			{
				if (!calLoan("0")) {
					CError.buildErr(this, "生成借款还款数据失败!");
					return false;
				}
	
				/* 第二部分：计算垫款及利息 payprem */
				if (!calLoan("1")) {
					CError.buildErr(this, "生成垫款还款数据失败!");
					return false;
				}
			}
		}

		// 应交未交保费及利息
		if (!calREPrem(tLCPolSet)) {
			CError.buildErr(this, "生成复效保费数据失败!");
			return false;
		}

		//为防止重复保存，删除前一次保存的特约
		String sLPCustomerImpart = " delete from LPCustomerImpart where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sLPCustomerImpart);
		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
	    String sLPCustomerImpartParams = " delete from LPCustomerImpartParams where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sLPCustomerImpartParams);
		sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv3.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
	    
		map.put(sbv2, "DELETE");
		map.put(sbv3, "DELETE");
		// ljsgetendorse
		// ljspayperson
		// see also in preparedata

		// 健康告知处理
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema();
			for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
				aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
				aLCCustomerImpartSchema.setGrpContNo(mLCContSchema.getGrpContNo());
				if (mLCContSchema.getGrpContNo() == null) {
					aLCCustomerImpartSchema.setGrpContNo("00000000000000000000");
				}
				aLCCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
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
				tError.errorMessage = tCustomerImpartBL.mErrors.getFirstError().toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			VData tempVData = new VData();
			tempVData = tCustomerImpartBL.getResult();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			try {
				tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData.getObjectByObjectName("LCCustomerImpartSet", 0);
				tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData.getObjectByObjectName(
						"LCCustomerImpartParamsSet", 0);
			} catch (Exception e) {
				CError.buildErr(this, "接受数据失败!");
				return false;
			}
			Reflections tRef = new Reflections();
			LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
			LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
			for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
				tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLPCustomerImpartSchema, tLCCustomerImpartSet.get(i));
				tLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPCustomerImpartSchema.setGrpContNo("00000000000000000000");
				tLPCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
			}

			//modify by jiaqiangli 2009-03-17 子表前面返回有可能是null
			if (tLCCustomerImpartParamsSet != null) {
				for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
					tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
					tRef.transFields(tLPCustomerImpartParamsSchema, tLCCustomerImpartParamsSet.get(i));

					tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPCustomerImpartParamsSchema.setGrpContNo("00000000000000000000");
					tLPCustomerImpartParamsSchema.setPrtNo(mLCContSchema.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
				}
			}
			if (mLPCustomerImpartSet.size() > 0)
				map.put(mLPCustomerImpartSet, "DELETE&INSERT");			
			if (mLPCustomerImpartParamsSet.size() > 0)
				map.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");
		}

		mResult.clear();

		return true;
	}

	/*
	 * 生成复效保费信息 此处为应交为交保费
	 */
	private boolean calREPrem(LCPolSet sLCPolSet) {
		String strSQL = "";
		FDate tFDate = new FDate(); // 日期格式化类
		Date lastPayToDate; // 最新交至日期
		Date lastLapseDate; // 最新宽限期止期

		// 循环处理主附险中需要补交续期保费的保单
		// add by jiaqiangli 2008-10-15
		// 保费存入ljspayperson ljsgetendorse
		// 利息存入ljsgetendorse
		for (int i = 1; i <= sLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = sLCPolSet.get(i);
			if(tLCPolSchema.getPayIntv()==0)
			{
				//sLCPolSet中非长险只有一种情况，即主险长趸交，此种情况不需要计算应缴未缴保费。
				continue;
			}
			LCPolSet tLCPolSet = new LCPolSet();
			LCDutySet tLCDutySet = null; // 本次保单责任信息
			LCPremSet tLCPremSet = null; // 本次保单保费信息
			LCGetSet tLCGetSet = null;
			LCDiscountSet tLCDiscountSet = null; // 本次保单保费信息
			boolean ifZK=false; //保单是否有折扣
			double totalLX = 0; // 本次保单累计多期保费利息

			// 2 获取交至日期 和 计算宽限期止期(包括额外延长的日期)
			lastPayToDate = tFDate.getDate(tLCPolSchema.getPaytoDate());
			lastLapseDate = tFDate
					.getDate(PubFun.calLapseDate(tLCPolSchema.getPaytoDate(), tLCPolSchema.getRiskCode()));

			// 是否需要变换保费结构 如单纯的垫缴至天可能不需要修改paytodate等
			boolean tNeedToChangePol = false;

			// 3 循环处理每一期复效保费
			while (lastLapseDate.before(tFDate.getDate(mLPEdorItemSchema.getEdorValiDate()))
			// 保全复效日期已经超过宽限期
					&& lastPayToDate.before(tFDate.getDate(tLCPolSchema.getPayEndDate())))
			// 未交到满期
			{
				// add by jiaqiangli 2008-10-27 表示需要变更保费结构
				tNeedToChangePol = true;

				// 3.1 首期复效保费需要获取保费和责任表信息
				if (tLCPremSet == null) {
					// 首期复效保费记录查询，查找所有需要交费保费记录信息,排除红利增额交情的保费信息
					strSQL = "select * From lcprem where contno='?contno?' and polno='?polno?' and urgepayflag='Y' and payintv>0 and paytodate='?paytodate?' and paytodate<payenddate";
					SQLwithBindVariables sbv4=new SQLwithBindVariables();
					sbv4.sql(strSQL);
					sbv4.put("contno", mLPEdorItemSchema.getContNo());
					sbv4.put("polno", tLCPolSchema.getPolNo());
					sbv4.put("paytodate", tLCPolSchema.getPaytoDate());
					tLCPremSet = new LCPremDB().executeQuery(sbv4);
					if (tLCPremSet == null || tLCPremSet.size() <= 0) {
						CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")应收保费记录失败!");
						return false;
					}

					// 首期复效查找所有责任记录信息
					LCDutyDB tLCDutyDB = new LCDutyDB();
					SQLwithBindVariables sbv5=new SQLwithBindVariables();
					sbv5.sql("select * from lcduty where contno='?contno?' and polno='?polno?' and char_length(dutycode)=6 ");
					sbv5.put("contno", mLPEdorItemSchema.getContNo());
					sbv5.put("polno", tLCPolSchema.getPolNo());
					tLCDutySet = tLCDutyDB.executeQuery(sbv5);
					if (tLCDutySet == null || tLCDutySet.size() <= 0) {
						CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")责任记录失败!");
						return false;
					}
					
					LCDiscountDB tLCDiscountDB = new LCDiscountDB();
					tLCDiscountDB.setPolNo(tLCPolSchema.getPolNo());
					tLCDiscountSet = tLCDiscountDB.query();
					if(tLCDiscountSet==null || tLCDiscountSet.size()<=0)
					{
						logger.debug("保单(" + tLCPolSchema.getPolNo() + ")没有折扣记录!");
						ifZK = false;
					}
					else
						ifZK = true; //有折扣
				}

				// 3.2 计算新的交至日期
				lastPayToDate = FinFeePubFun.calOFDate(lastPayToDate, tLCPolSchema.getPayIntv(), "M", tFDate.getDate(tLCPolSchema.getCValiDate()));
				logger.debug("[new PaytoDate] " + tFDate.getString(lastPayToDate));

				// 3.3 循环保费表记录，生成相应财务收费表明细，
				// 并设置保费表和责任表的相关字段信息
				double sumPolPayMoney = 0;				
				LJSPayPersonSet tempLJSPayPersonSet = new LJSPayPersonSet();
				for (int j = 1; j <= tLCPremSet.size(); j++) {
					LJSGetEndorseSchema tLJSGetEndorseSchema = initLJSGetEndorse(tLCPolSchema, tLCPremSet.get(j),
							tFDate.getString(lastPayToDate));

					if (tLJSGetEndorseSchema == null || tLJSGetEndorseSchema.getGetMoney() <= 0.001) {
						// 可能有免交件，不处理保费余额
						continue;
					}

					sumPolPayMoney += tLJSGetEndorseSchema.getGetMoney();
//					// 此处要先加1处理
//					tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem
//							+ String.valueOf(tLCPremSet.get(1).getPayTimes() + 1));
//
//					mLJSGetEndorseSet.add(tLJSGetEndorseSchema);

					// lcprem 3个字段
					tLCPremSet.get(j).setSumPrem(tLCPremSet.get(j).getSumPrem() + tLJSGetEndorseSchema.getGetMoney());
					tLCPremSet.get(j).setPayTimes(tLCPremSet.get(j).getPayTimes() + 1);
					tLCPremSet.get(j).setPaytoDate(tFDate.getString(lastPayToDate));
					
					tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem+ String.valueOf(tLCPremSet.get(j).getPayTimes()));
					//营改增 add zhangyingfeng 2016-07-13
					//价税分离 计算器
					TaxCalculator.calBySchema(tLJSGetEndorseSchema);
					//end zhangyingfeng 2016-07-13
					mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
					
					//处理每个prem的ljspayperson add by jiaqiangli 2009-07-08
					LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
					tLJSPayPersonSchema.setPolNo(tLCPolSchema.getPolNo());
					tLJSPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
					tLJSPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
					tLJSPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
					tLJSPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
					tLJSPayPersonSchema.setContNo(tLCPolSchema.getContNo());
					tLJSPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
					tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
					tLJSPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());
					tLJSPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
					// 交费目的
					tLJSPayPersonSchema.setPayAimClass("4");
					tLJSPayPersonSchema.setDutyCode(tLCPremSet.get(j).getDutyCode());
					tLJSPayPersonSchema.setPayPlanCode(tLCPremSet.get(j).getPayPlanCode());

					tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
					tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
					tLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());

					tLJSPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
					tLJSPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
					tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());

					tLJSPayPersonSchema.setSumActuPayMoney(tLJSGetEndorseSchema.getGetMoney());
					tLJSPayPersonSchema.setSumDuePayMoney(tLJSGetEndorseSchema.getGetMoney());

					tLJSPayPersonSchema.setPayCount(tLCPremSet.get(j).getPayTimes());
					tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
					tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
					tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
					tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
					tLJSPayPersonSchema.setBankAccNo("");
					tLJSPayPersonSchema.setBankCode("");
					tempLJSPayPersonSet.add(tLJSPayPersonSchema);
					
					// 设责责任表保费
					boolean bDutyFlag = false;
					for (int k = 1; k <= tLCDutySet.size(); k++) {
						if (tLCPremSet.get(j).getDutyCode().equals(tLCDutySet.get(k).getDutyCode())) {
							// lcduty 2个字段
							tLCDutySet.get(k).setSumPrem(
									tLCDutySet.get(k).getSumPrem() + tLJSGetEndorseSchema.getGetMoney());
							tLCDutySet.get(k).setPaytoDate(tFDate.getString(lastPayToDate));
							bDutyFlag = true;
							break;
						}
					}

					if (!bDutyFlag) {
						CError.buildErr(this, "未找到保费保费对应责任表数据!");
						return false;
					}
					
					//增加折扣处理
					if(ifZK)
					{
						LCPremSchema tLCPremSchema = new LCPremSchema();
						tLCPremSchema.setSchema(tLCPremSet.get(j));
						double PolPayZKMoney = dealDiscount(tempLJSPayPersonSet,tLCPolSchema,tLCPremSchema,sumPolPayMoney);//计算折扣是根据实际应补保费还是基本保费？
						sumPolPayMoney += PolPayZKMoney;
					}
				}
				
				//add by jiaqiangli 2009-04-01 增加lcget与lpget
				LCGetDB tLCGetDB = new LCGetDB();
				SQLwithBindVariables sbv6=new SQLwithBindVariables();
				sbv6.sql("select * from lcget where contno='?contno?' and polno='?polno?' and char_length(dutycode)=6 ");
				sbv6.put("contno", mLPEdorItemSchema.getContNo());
				sbv6.put("polno", tLCPolSchema.getPolNo());
				tLCGetSet = tLCGetDB.executeQuery(sbv6);
				if (tLCGetSet == null || tLCGetSet.size() <= 0) {
					CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")领取责任记录失败!");
					return false;
				}
				//add by jiaqiangli 2009-04-01 增加lcget与lpget

				if (sumPolPayMoney < 0.001) {
					CError.buildErr(this, "保单(" + tLCPolSchema.getPolNo() + ")需交保费为0");
					return false;
				}				

				// 3.4 计算该保单当前一期复效保费利息
				// 累计计算利息
				totalLX += calPremInterest(sumPolPayMoney, PubFun.calDate(PubFun.calLapseDate(tLCPolSchema
						.getPaytoDate(), tLCPolSchema.getRiskCode()), 1, "D", ""),tLCPolSchema.getRiskCode(),tLCPolSchema.getCurrency());

				// 3.5 设置保单表信息
				tLCPolSchema.setSumPrem(tLCPolSchema.getSumPrem() + sumPolPayMoney);
				tLCPolSchema.setPaytoDate(tFDate.getString(lastPayToDate));

				//comment by jiaqiangli 209-07-08 此处没有对加费的分开处理，而是作为一条记录处理
				/*
				// 保费保存入ljspayperson
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
				tLJSPayPersonSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJSPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJSPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJSPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJSPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJSPayPersonSchema.setContNo(tLCPolSchema.getContNo());
				tLJSPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
				tLJSPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJSPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
				// 交费目的
				tLJSPayPersonSchema.setPayAimClass("4");
				tLJSPayPersonSchema.setDutyCode(tLCPremSet.get(1).getDutyCode());
				tLJSPayPersonSchema.setPayPlanCode(tLCPremSet.get(1).getPayPlanCode());
				// tLJSPayPersonSchema.setPayIntv("0");
				// comment by jiaqiangli 2008-10-28 摒弃lis53payintv的置法
				// 改成lcpol.payintv
				tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
				tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
				tLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());

				// tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
				// comment by jiaqiangli 2008-10-28 前推取得LastPayToDate
				tLJSPayPersonSchema.setLastPayToDate(FinFeePubFun.calOFDate(tLCPolSchema.getPaytoDate(), -tLCPolSchema.getPayIntv(), "M", tLCPolSchema.getCValiDate()));
				tLJSPayPersonSchema.setCurPayToDate(tLCPolSchema.getPaytoDate());

				// comment by jiaqiangli 2008-10-28 set mainpolyear
				int tMainPolYear = PubFun.calInterval(tLCPolSchema.getCValiDate(), tLJSPayPersonSchema.getCurPayToDate(), "Y");
				tLJSPayPersonSchema.setMainPolYear(tMainPolYear); // 设置主险年度
				// comment by jiaqiangli 2008-10-28 set mainpolyear

				tLJSPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJSPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());

				// 保费项的保存 SumActuPayMoney = SumDuePayMoney
				tLJSPayPersonSchema.setSumActuPayMoney(sumPolPayMoney);
				tLJSPayPersonSchema.setSumDuePayMoney(sumPolPayMoney);
				
				// add by jiaqiangli 2009-04-03 此处次数加1处理
				//tLJSPayPersonSchema.setPayCount(tLCPremSet.get(1).getPayTimes()+1);
				tLJSPayPersonSchema.setPayCount(tLCPremSet.get(1).getPayTimes());
				tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
				tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
				tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
				tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
				tLJSPayPersonSchema.setBankAccNo("");
				tLJSPayPersonSchema.setBankCode("");
				tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
				mLJSPayPersonSet.add(tLJSPayPersonSchema);*/
				//置paytodate mainpolyear
				for (int k=1;k<=tempLJSPayPersonSet.size();k++) {
					tempLJSPayPersonSet.get(k).setLastPayToDate(FinFeePubFun.calOFDate(tLCPolSchema.getPaytoDate(), -tLCPolSchema.getPayIntv(), "M", tLCPolSchema.getCValiDate()));
					tempLJSPayPersonSet.get(k).setCurPayToDate(tLCPolSchema.getPaytoDate());
					tempLJSPayPersonSet.get(k).setMainPolYear(PubFun.calInterval(tLCPolSchema.getCValiDate(), tempLJSPayPersonSet.get(k).getCurPayToDate(), "Y"));
				}
				//营改增 add zhangyingfeng 2016-07-13
				//价税分离 计算器
				TaxCalculator.calBySchemaSet(tempLJSPayPersonSet);
				//end zhangyingfeng 2016-07-13
				mLJSPayPersonSet.add(tempLJSPayPersonSet);

				// 3.6 累计复效总金额
				totalMoney += PubFun.round(sumPolPayMoney, 2);

				// 3.7 重新计算复效当期保费后的宽限期止期
				lastLapseDate = tFDate.getDate(PubFun.calLapseDate(tLCPolSchema.getPaytoDate(), tLCPolSchema.getRiskCode()));
			}

			// 4 生成当前保单复效保费累计利息
			if (totalLX > 0) {
				LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSet.get(mLJSGetEndorseSet.size()));

				tLJSGetEndorseSchema.setGetMoney(totalLX);// 设置累计利息利息

				// 设置财务类型
				String finType = BqCalBL
						.getFinType(mLPEdorItemSchema.getEdorType(), "LX", mLPEdorItemSchema.getPolNo());
				if (finType == null || finType.equals("")) {
					// @@错误处理
					CError.buildErr(this, "在LDCode1中缺少保全复效财务接口转换类型编码!");
					return false;
				}
				tLJSGetEndorseSchema.setFeeFinaType(finType);
				// 复效补交保费利息
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_PremInterest_Re
						+ String.valueOf(tLCPremSet.get(1).getPayTimes()));

				// 设置财务类型
				tLJSGetEndorseSchema.setDutyCode("0");
				tLJSGetEndorseSchema.setPayPlanCode("0");
				tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setOtherNoType("3");
				totalMoney += totalLX; // 累计复效总金额
				//营改增 add zhangyingfeng 2016-07-13
				//价税分离 计算器
				TaxCalculator.calBySchema(tLJSGetEndorseSchema);
				//end zhangyingfeng 2016-07-13
				mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			}
			// 为了这个的保存mLPEdorItemSchema.setGetInterest(mTotalInterest);
			mTotalInterest += totalLX;	
			//查询折扣信息
			LCDiscountSet qLCDiscountSet = new LCDiscountSet();
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			tLCDiscountDB.setPolNo(tLCPolSchema.getPolNo());			
			qLCDiscountSet = tLCDiscountDB.query();
			mLCDiscountSet.add(qLCDiscountSet);

			if (tNeedToChangePol == true) {
				// lcpol表 2个字段 此处应该放在paytodate循环外
				tLCPolSet.add(tLCPolSchema);
				// 5 保存当前保单修改的lcpol 责任表和保费表
				mLCPolSet.add(tLCPolSet);
				mLCDutySet.add(tLCDutySet);
				mLCPremSet.add(tLCPremSet);
				mLCGetSet.add(tLCGetSet);
			}
			// add by jiaqiangli 2009-04-01 此处有可能是借款或是垫缴至宽限期外失效，但没有应交未交的补费(也就是paytodate不需要变)
			// 此处也需要生成保费结构的p表数据 人工核保需要用
			else {
				// 生成duty
				LCDutyDB tLCDutyDB = new LCDutyDB();
				//排除增额的
				SQLwithBindVariables sbv7=new SQLwithBindVariables();
				sbv7.sql("select * from lcduty where contno='?contno?' and polno='?polno?' and char_length(dutycode)=6 ");
				sbv7.put("contno", mLPEdorItemSchema.getContNo());
				sbv7.put("polno", tLCPolSchema.getPolNo());
				tLCDutySet = tLCDutyDB.executeQuery(sbv7);
				if (tLCDutySet == null || tLCDutySet.size() <= 0) {
					CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")责任记录失败!");
					return false;
				}
				// 生成prem 增额的为urgepayflag='N'
				strSQL = "select * From lcprem where contno='?contno?' and polno='?polno?' and urgepayflag='Y' and payintv>0 and paytodate='?paytodate?' and paytodate<payenddate";
				SQLwithBindVariables sbv8=new SQLwithBindVariables();
				sbv8.sql(strSQL);
				sbv8.put("contno", mLPEdorItemSchema.getContNo());
				sbv8.put("polno", tLCPolSchema.getPolNo());
				sbv8.put("paytodate", tLCPolSchema.getPaytoDate());
				tLCPremSet = new LCPremDB().executeQuery(sbv8);
				if (tLCPremSet == null || tLCPremSet.size() <= 0) {
					CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")应收保费记录失败!");
					return false;
				}
				// 生成get
				//add by jiaqiangli 2009-04-01 增加lcget与lpget
				LCGetDB tLCGetDB = new LCGetDB();
				//排除增额的
				SQLwithBindVariables sbv9=new SQLwithBindVariables();
				sbv9.sql("select * from lcget where contno='?contno?' and polno='?polno?' and char_length(dutycode)=6 ");
				sbv9.put("contno", mLPEdorItemSchema.getContNo());
				sbv9.put("polno", tLCPolSchema.getPolNo());
				tLCGetSet = tLCGetDB.executeQuery(sbv9);
				if (tLCGetSet == null || tLCGetSet.size() <= 0) {
					CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")领取责任记录失败!");
					return false;
				}
				//add by jiaqiangli 2009-04-01 增加lcget与lpget
				
				tLCPolSet.add(tLCPolSchema);
				mLCPolSet.add(tLCPolSet);
				mLCDutySet.add(tLCDutySet);
				mLCPremSet.add(tLCPremSet);
				mLCGetSet.add(tLCGetSet);
			}
		}

		// get ready for lp table
		// 准备保单表数据
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			mReflections.transFields(tLPPolSchema, mLCPolSet.get(i));
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPPolSet.add(tLPPolSchema);
		}

		// 准备责任表数据
		for (int i = 1; i <= mLCDutySet.size(); i++) {
			LPDutySchema tLPDutySchema = new LPDutySchema();
			mReflections.transFields(tLPDutySchema, mLCDutySet.get(i));
			tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPDutySet.add(tLPDutySchema);
		}

		// 准备保费表数据
		for (int i = 1; i <= mLCPremSet.size(); i++) {
			LPPremSchema tLPPremSchema = new LPPremSchema();
			mReflections.transFields(tLPPremSchema, mLCPremSet.get(i));
			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPPremSet.add(tLPPremSchema);
		}
		
		// 准备lcget表
		for (int i = 1; i <= mLCGetSet.size(); i++) {
			LPGetSchema tLPGetSchema = new LPGetSchema();
			mReflections.transFields(tLPGetSchema, mLCGetSet.get(i));
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPGetSet.add(tLPGetSchema);
		}
		
		// 准备折扣表
		for (int i = 1; i <= mLCDiscountSet.size(); i++) {
			LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
			mReflections.transFields(tLPDiscountSchema, mLCDiscountSet.get(i));
			tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPDiscountSet.add(tLPDiscountSchema);
		}

		return true;
	}
	
	private double dealDiscount(LJSPayPersonSet tempLJSPayPersonSet,LCPolSchema tLCPolSchema,LCPremSchema tLCPremSchema,double tPayMoney)
	{
		double tZK = 0.00;
		LJSPayPersonSet tLJSPayPersonSet=new LJSPayPersonSet();
		LPDiscountSet qLPDiscountSet = new LPDiscountSet();
		
		LCDiscountSet qLCDiscountSet = new LCDiscountSet();
		LCDiscountDB tLCDiscountDB = new LCDiscountDB();
		tLCDiscountDB.setPolNo(tLCPolSchema.getPolNo());			
		qLCDiscountSet = tLCDiscountDB.query();
		//************ start
		if(qLCDiscountSet!=null && qLCDiscountSet.size()>0)
		{
			for(int i=1;i<=qLCDiscountSet.size();i++)
			{
				LPDiscountSchema qLPDiscountSchema = new LPDiscountSchema();
				mReflections.transFields(qLPDiscountSchema, qLCDiscountSet.get(i));
				qLPDiscountSet.add(qLPDiscountSchema);
			}				
		}
		LPPremSchema tLPPremSchema=new LPPremSchema();
		mReflections.transFields(tLPPremSchema, tLCPremSchema);
		tLPPremSchema.setPrem(tPayMoney);//应交保费
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		
		LPPolSchema tLPPolSchema=new LPPolSchema();
		mReflections.transFields(tLPPolSchema, tLCPolSchema);			
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		
		if(qLPDiscountSet!=null && qLPDiscountSet.size()>0)
		{		
			LPPremSet tLPPremSet=new LPPremSet();
			tLPPremSet.add(tLPPremSchema);
			TransferData tTransferData=new TransferData();
			tTransferData.setNameAndValue("PayCount", String.valueOf(tLCPremSchema.getPayTimes()));
			tTransferData.setNameAndValue("PayIntv", String.valueOf(tLCPolSchema.getPayIntv()));
			tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);	
			tTransferData.setNameAndValue("PayAimClass", "4");//交费目的
			PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
			VData tzkVData = new VData();
			tzkVData.add(tLPPolSchema);
			tzkVData.add(tLPPremSet);
			tzkVData.add(qLPDiscountSet);
			tzkVData.add(mLPEdorItemSchema);
			tzkVData.add(tTransferData);
			//
			if(!tDiscountCalBL.calculate(tzkVData))
			{
				CError.buildErr(this, "折扣计算失败！");
				tZK = 1;
			}
			
			//
			VData rVData = tDiscountCalBL.getResult();
			tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
		}	
		if(tLJSPayPersonSet!=null && tLJSPayPersonSet.size()>0)
		{
			// 添加保费LJSGetEndorse
			for(int i=1;i<=tLJSPayPersonSet.size();i++)
			{
				if (!createLJSGetEndorseSchemaZK(tLPPolSchema,tLJSPayPersonSet.get(i))) {
					mErrors.addOneError("添加保费LJSGetEndorse!");
					tZK = 1;
				}
				//计算折扣总额
				tZK = tZK +tLJSPayPersonSet.get(i).getSumActuPayMoney();
			}
			tempLJSPayPersonSet.add(tLJSPayPersonSet);
		}
		
		return tZK;
	}
	
	/**
	 * 由LJSPayPerson信息生成当期保费折扣的批改补退费信息
	 * 
	 * @param cLJSPayPersonSchema
	 * @return
	 */
	// 添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchemaZK(LPPolSchema tLPPolSchema, LJSPayPersonSchema tLJSPayPersonSchema) {

		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSet.get(mLJSGetEndorseSet.size()));

		tLJSGetEndorseSchema.setGetMoney(tLJSPayPersonSchema.getSumActuPayMoney());

		// 设置财务类型
		String finType = BqCalBL
				.getFinType(mLPEdorItemSchema.getEdorType(), "ZK", mLPEdorItemSchema.getPolNo());
		if (finType == null || finType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全复效财务接口转换类型编码!");
			return false;
		}
		tLJSGetEndorseSchema.setFeeFinaType(finType);
		tLJSGetEndorseSchema.setSubFeeOperationType(tLJSPayPersonSchema.getPayType()
				+ String.valueOf(tLJSPayPersonSchema.getPayCount()));	
		//营改增 add zhangyingfeng 2016-07-13
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-13
		mZKLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		
		return true;
	}

	private LJSGetEndorseSchema initLJSGetEndorse(LCPolSchema tLCPolSchema, LCPremSchema tLCPremSchema,
			String lastPayToDate) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		mReflections.transFields(tLJSGetEndorseSchema, tLCPolSchema);

		tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());

		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), "BF", mLPEdorItemSchema.getPolNo());
		if (finType == null || finType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全复效财务接口转换类型编码!");
			return null;
		}
		tLJSGetEndorseSchema.setFeeFinaType(finType);

		tLJSGetEndorseSchema.setGetFlag("0");
		tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setDutyCode(tLCPremSchema.getDutyCode());
		tLJSGetEndorseSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());

		// 代表续收催收期数
//		tLJSGetEndorseSchema.setOtherNo(lastPayToDate); // 新的交至日期
//		tLJSGetEndorseSchema.setOtherNoType(Integer.toString(tLCPremSchema.getPayTimes() + 1)); // 交费次数
//		tLJSGetEndorseSchema.setSerialNo(tLCPremSchema.getPaytoDate()); // 上次交至日期
		
		tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付

		tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());

		// 计算免交保费
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(tLCPremSchema.getPolNo());
		tLCDutyDB.setDutyCode(tLCPremSchema.getDutyCode());
		if (!tLCDutyDB.getInfo()) {
			CError.buildErr(this, "获取保费记录对应责任信息失败!");
			return null;
		}
		double actuPayPrem = tLCPremSchema.getPrem();
		if ((tLCDutyDB.getFreeFlag() != null) && !tLCDutyDB.getFreeFlag().equals("1") && (tLCDutyDB.getFreeRate() <= 1)
				&& (tLCDutyDB.getFreeRate() > 0)) {
			// 有免交标记
			actuPayPrem = tLCPremSchema.getPrem() * (1 - tLCDutyDB.getFreeRate());
		}

		tLJSGetEndorseSchema.setGetMoney(actuPayPrem);

		return tLJSGetEndorseSchema;
	}

	/**
	 * 复效利息计算
	 * 
	 * @return
	 */
	private double calPremInterest(double dPrem, String sLapseDate,String tRiskCode,String tCurrency) {
		double aGetInterest = 0;

		try {
			// aGetInterest = tAccountManage.getInterest(dPrem,sLapseDate,
			// mLPEdorItemSchema.getEdorValiDate(),tLMLoanDB.getInterestRate(),
			// tLMLoanDB.getInterestMode(), "1", "D");
			//属于借款的复利年利率
			if (this.tisCalInterest != null && this.tisCalInterest.equals("on"))
				aGetInterest = dPrem
				//add by jiaqiangli 2009-04-23 日期相减后作加一天处理
					* AccountManage.calMultiRateMS(sLapseDate, PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),1,"D",null), tRiskCode,
							mLPEdorItemSchema.getEdorType(), "L", "C", "Y",tCurrency);
		} catch (Exception ex) {
		}
		return aGetInterest;
	}

	/*
	 * 生成还款或还垫记录 rType：0-还款，1-还垫
	 */
	private boolean calLoan(String rType) {
		LOLoanSet cLOLoanSet = new LOLoanSet();

		// 1 查询主险借款、垫款记录
		LOLoanDB tLOLoanDB = new LOLoanDB();
		// 去掉polno的查询
		tLOLoanDB.setContNo(this.mLPEdorItemSchema.getContNo());
		tLOLoanDB.setPayOffFlag("0");
		tLOLoanDB.setLoanType(rType);
		LOLoanSet tLOLoanSet = tLOLoanDB.query();
		
		//add by jiaqiangli 2009-09-09 借款按合同存储 此法不够严谨 
		//垫缴中的loloan.edorno与另外一张单子的借款的lpedoritem.edorno号段重复 导致程序错位
		cLOLoanSet = tLOLoanSet;

		// 2 通过主险信息查询相应批次主险和附加先借款垫款记录
//		for (int i = 1; i <= tLOLoanSet.size(); i++) {
//			tLOLoanDB = new LOLoanDB();
//			tLOLoanDB.setEdorNo(tLOLoanSet.get(i).getEdorNo());
//			cLOLoanSet.add(tLOLoanDB.query());
//		}

		// 3 根据借款、垫款信息生成还款和计算相应利息信息 以及 应收财务信息
		VData tVData = new VData();
		for (int i = 1; i <= cLOLoanSet.size(); i++) {
			LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
			// deal loloan && lploan （use lpreturnloan not lploan）

			// 3.1 设置还款基本信息，还款金额为相应借款、垫款记录剩余金额
			mReflections.transFields(tLPReturnLoanSchema, cLOLoanSet.get(i));

			tLPReturnLoanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPReturnLoanSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPReturnLoanSchema.setLoanNo(cLOLoanSet.get(i).getEdorNo());
			tLPReturnLoanSchema.setOperator(mGlobalInput.Operator);
			tLPReturnLoanSchema.setMakeDate(PubFun.getCurrentDate());
			tLPReturnLoanSchema.setMakeTime(PubFun.getCurrentTime());
			tLPReturnLoanSchema.setModifyDate(PubFun.getCurrentDate());
			tLPReturnLoanSchema.setModifyTime(PubFun.getCurrentTime());
			tLPReturnLoanSchema.setReturnMoney(cLOLoanSet.get(i).getLeaveMoney());
			// 缴清标志和日期
			tLPReturnLoanSchema.setPayOffFlag("1");
			tLPReturnLoanSchema.setPayOffDate(mLPEdorItemSchema.getEdorValiDate());
			totalMoney = totalMoney + PubFun.round(tLPReturnLoanSchema.getReturnMoney(), 2); // 累积复效总金额

			// 计算还款金额相应利息
			tVData.clear();
			tVData.add(cLOLoanSet.get(i));
			// double interestMoney = tAccountManage.getMultiInterest("1",
			// tVData, tLPReturnLoanSchema.getReturnMoney(),
			// tLPReturnLoanSchema.getLoanDate(),
			// mLPEdorItemSchema.getEdorValiDate());
			double interestMoney = 0.00;
			
			if (this.tisCalInterest != null && this.tisCalInterest.equals("on")) {
				if (rType.equals("0"))
					interestMoney = cLOLoanSet.get(i).getLeaveMoney()
						* AccountManage.calMultiRateMS(cLOLoanSet.get(i).getNewLoanDate(),//wk 修改为 NewLoanDate
							mLPEdorItemSchema.getEdorValiDate(), mLCPolSchema.getRiskCode(), mLPEdorItemSchema
									.getEdorType(), "L", "C", "Y",cLOLoanSet.get(i).getCurrency());
				//垫款计息做日期相减后加1处理
				else if (rType.equals("1"))
					interestMoney = cLOLoanSet.get(i).getLeaveMoney()
						* AccountManage.calMultiRateMS(cLOLoanSet.get(i).getLoanDate(),//wk 垫交不能修改 
							PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),1,"D",null), mLCPolSchema.getRiskCode(), mLPEdorItemSchema
								.getEdorType(), "L", "C", "Y",cLOLoanSet.get(i).getCurrency());
			}

			tLPReturnLoanSchema.setReturnInterest(interestMoney);
			totalMoney = totalMoney + PubFun.round(tLPReturnLoanSchema.getReturnInterest(), 2); // 累积复效总金额

			aLPReturnLoanSet.add(tLPReturnLoanSchema);

			// 3.2 设置还款应收财务信息 LJSGetEndorse
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

			mReflections.transFields(tLJSGetEndorseSchema, mLCPolSchema);

			tLJSGetEndorseSchema.setPolNo(tLPReturnLoanSchema.getPolNo());
			tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo()); // 无作用
			tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
			tLJSGetEndorseSchema.setOtherNo(tLPReturnLoanSchema.getLoanNo()); // 原批单号
			tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJSGetEndorseSchema.setPayPlanCode("0"); // 无作用
			tLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
			tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
			tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());

			// 从描述表中获取财务接口类型
			String finType = "";
			if (rType.equals("0")) {
				finType = BqCalBL.getFinType("RF", "HK", mLCPolSchema.getPolNo());
				// 贷款本金清偿
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpus);
			} else {
				finType = BqCalBL.getFinType("TR", "HD", mLCPolSchema.getPolNo());
				// add by jiaqiangli 2008-10-20
				// 垫缴到天与垫整期相同，此处确保财务核销不会生成ljapayperson
				// see also in ljfinaconfirm bqcode.xx+paycount
				// 自垫本金清偿
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_AutoPayPrem);
			}
			if (finType.equals("")) {
				// @@错误处理
				CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
				return false;
			}
			tLJSGetEndorseSchema.setFeeFinaType(finType); // 还款本金属于补费
			tLJSGetEndorseSchema.setGetFlag("0");
			tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
			tLJSGetEndorseSchema.setGetMoney(tLPReturnLoanSchema.getReturnMoney());

			// benjin
			//营改增 add zhangyingfeng 2016-07-13
			//价税分离 计算器
			TaxCalculator.calBySchema(tLJSGetEndorseSchema);
			//end zhangyingfeng 2016-07-13
			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);

			// 3.3 设置还款利息应收财务信息 LX
			LJSGetEndorseSchema rateLJSGetEndorseSchema = new LJSGetEndorseSchema();
			rateLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);

			// 从描述表中获取财务接口类型
			if (rType.equals("0")) {
				finType = BqCalBL.getFinType("RF", "LX", mLCPolSchema.getPolNo());
				// 贷款利息清偿
				rateLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpusInterest);
			} else {
				finType = BqCalBL.getFinType("TR", "LX", mLCPolSchema.getPolNo());
				// 自垫利息清偿
				rateLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_AutoPayPremInterest);
			}

			if (finType.equals("")) {
				// @@错误处理
				CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
				return false;
			}
			rateLJSGetEndorseSchema.setFeeFinaType(finType); // 还款利息
			rateLJSGetEndorseSchema.setGetMoney(tLPReturnLoanSchema.getReturnInterest());

			// 保存所有利息
			mTotalInterest += rateLJSGetEndorseSchema.getGetMoney();

			// lixi
			if (rateLJSGetEndorseSchema.getGetMoney() > 0)
				//营改增 add zhangyingfeng 2016-07-13
				//价税分离 计算器
				TaxCalculator.calBySchema(rateLJSGetEndorseSchema);
				//end zhangyingfeng 2016-07-13
				mLJSGetEndorseSet.add(rateLJSGetEndorseSchema);
		}

		return true;
	}

	private boolean prepareData() {
		// 准备LJSGetEndorse表
		double getMoney = 0;
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
			LJSGetEndorseSchema tLJS = new LJSGetEndorseSchema();
			tLJS.setSchema(mLJSGetEndorseSet.get(i));
			getMoney += tLJS.getGetMoney();
			tLJSGetEndorseSet.add(tLJS);
		}
		//为防止利息的多次点击，应该先删除ljsgetendorse getnoticeno+EndorsementNo+FeeOperationType
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("delete from ljsgetendorse where GetNoticeNo='?GetNoticeNo?' and EndorsementNo='?EndorsementNo?' and FeeOperationType='?FeeOperationType?'");
		sqlbv.put("GetNoticeNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("", mLPEdorItemSchema.getEdorType());
		map.put(sqlbv, "DELETE");
		
		map.put(tLJSGetEndorseSet, "DELETE&INSERT");
		map.put(mZKLJSGetEndorseSet, "DELETE&INSERT");

		map.put(mLJSPayPersonSet, "DELETE&INSERT");

		// 保费结构表
		map.put(mLPPolSet, "DELETE&INSERT");
		map.put(mLPDutySet, "DELETE&INSERT");
		map.put(mLPPremSet, "DELETE&INSERT");
		map.put(mLPGetSet, "DELETE&INSERT");
		map.put(mLPDiscountSet, "DELETE&INSERT");

		// 借款表
		map.put(aLPReturnLoanSet, "DELETE&INSERT");

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		// 在此抽取firstpaydate??
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType("RE");
		tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		map.put(tLPContSchema, "DELETE&INSERT");

		// 准备LPEdorItem表
		//保存是否继续利息
		mLPEdorItemSchema.setStandbyFlag3(this.tisCalInterest);
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setGetMoney(getMoney);
		mLPEdorItemSchema.setGetInterest(mTotalInterest);
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		map.put(mLPEdorItemSchema, "UPDATE");

		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mResult.add(mBqCalBase);
		mResult.add(map);
		return true;
	}

	public static void main(String[] args) {
		PEdorREDetailBL t = new PEdorREDetailBL();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo("6120090104000004");
		tLPEdorItemSet = tLPEdorItemDB.query();
		tLPEdorItemSchema = tLPEdorItemSet.get(1);
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		tVData.add(tLCCustomerImpartSet);
		t.submitData(tVData, "");
	}
}
