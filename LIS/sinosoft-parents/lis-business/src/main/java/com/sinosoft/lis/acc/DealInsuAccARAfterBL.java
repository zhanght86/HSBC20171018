package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.bq.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:投连后续处理账户赎回AR实现
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:sinosoft
 * </p>
 * 
 * @author:ck
 * @version 1.0
 */
public class DealInsuAccARAfterBL extends DealInsuAccAfter {
private static Logger logger = Logger.getLogger(DealInsuAccARAfterBL.class);
	public DealInsuAccARAfterBL() {
	}

	private VData mResult = new VData();

	/** 传出数据的容器 */
	public LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();

	private LOPolAfterDealSchema _LOPolAfterDealSchema = new LOPolAfterDealSchema();

	private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();

	private LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();

	private LCInsureAccTraceSet _LCInsureAccTraceSet = new LCInsureAccTraceSet();
	
	LPEdorMainSchema lzLPEdorMainSchema = new LPEdorMainSchema();
	
	DecimalFormat df2 = new DecimalFormat("0.00"); 

	LCInsureAccClassSchema lzLCInsureAccClassSchema = new LCInsureAccClassSchema();

	LCInsureAccSchema lzLCInsureAccSchema = new LCInsureAccSchema();

	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();
	
	private LJAGetSet _LJAGetSet = new LJAGetSet();

	private LJAGetEndorseSet _LJAGetEndorseSet = new LJAGetEndorseSet();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	LPEdorAppSchema lzLPEdorAppSchema = new LPEdorAppSchema();

	LPInsuAccOutSet mLPInsuAccOutSet = new LPInsuAccOutSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	public String mCurrentDate = PubFun.getCurrentDate();

	public String mCurrentTime = PubFun.getCurrentTime();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/* 根据LOPolAfterDeal表中的账户信息进行处理 */
	public boolean dealAfter(GlobalInput tGlobalInput,
			LOPolAfterDealSchema tLOPolAfterDealSchema) {
		/* 账户转换AR后续处理逻辑 */
		String _DealDate = tLOPolAfterDealSchema.getDealDate();
		mGlobalInput = tGlobalInput;
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		tLCInsureAccTraceDB
				.setAccAlterNo(tLOPolAfterDealSchema.getAccAlterNo());
		tLCInsureAccTraceDB.setAccAlterType(tLOPolAfterDealSchema
				.getAccAlterType());
		tLCInsureAccTraceDB.setBusyType(tLOPolAfterDealSchema.getBusyType());
		tLCInsureAccTraceDB.setState("0");
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		if (tLCInsureAccTraceSet.size() > 0) {
			CError.buildErr(this, "存在待计价的记录!");
			return false;
		}
		tLCInsureAccTraceDB.setState("1");
		tLCInsureAccTraceSet.clear();
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();

		if (tLCInsureAccTraceSet.size() == 0) {
			return false;
		}
		if (!getInfo(tLCInsureAccTraceSet.get(1))) {
			return false;
		}

		LPInsuAccOutDB tLPInsuAccOutDB = new LPInsuAccOutDB();
		tLPInsuAccOutDB.setEdorNo(tLOPolAfterDealSchema.getAccAlterNo());
		tLPInsuAccOutDB.setEdorType(tLOPolAfterDealSchema.getBusyType());
		mLPInsuAccOutSet = tLPInsuAccOutDB.query();
		if (mLPInsuAccOutSet.size() != tLCInsureAccTraceSet.size()) {
			CError.buildErr(this,
					"保全账户转出信息LPInsuAccOut与轨迹表LCInsureAccTrace存在不一致!");
			return false;
		}

		double OutMoney = 0.00;
		/* 得到卖出的 */
		for (int i = 1; i <= mLPInsuAccOutSet.size(); i++) {
			LPInsuAccOutSchema mLPInsuAccOutSchema = new LPInsuAccOutSchema();
			mLPInsuAccOutSchema = mLPInsuAccOutSet.get(i);
			LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
			mLCInsureAccTraceDB.setSerialNo(mLPInsuAccOutSchema.getSerialNo());
			mLCInsureAccTraceSet = mLCInsureAccTraceDB.query();
			if (mLCInsureAccTraceSet.size() == 0) {
				CError.buildErr(this, "账户转出交易轨迹查询失败!");
				return false;
			}
			mLPInsuAccOutSet.get(i).setState("0");
		}
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(tLOPolAfterDealSchema.getAccAlterNo());
		tLPEdorItemDB.setEdorType(tLOPolAfterDealSchema.getBusyType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() == 0) {
			CError.buildErr(this, "批改项目信息查询失败!");
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "保全受理信息查询失败!");
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		_LOPolAfterDealSchema = tLOPolAfterDealSchema.getSchema();
		//_LOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
		_LOPolAfterDealSchema.setState("2");// 手续处理完成

		double summoney = 0.00;
		
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("Select distinct Currency From lcinsureacctrace a Where a.accalterno = '"+"?accAlterNo?"
		    	+"' And a.accaltertype = '"+"?accAlterType?"+"' And a.busytype = '"+"?busyType?"+"'");
		sqlbv.put("accAlterNo", _LOPolAfterDealSchema.getAccAlterNo());
		sqlbv.put("accAlterType", _LOPolAfterDealSchema.getAccAlterType());
		sqlbv.put("busyType", _LOPolAfterDealSchema.getBusyType());
		ExeSQL es = new ExeSQL();
		SSRS curSSRS = es.execSQL(sqlbv);
		for(int c =1;c<=curSSRS.getMaxRow();c++){
			LJAGetSchema _LJAGetSchema = createLJAGet(curSSRS.GetText(c, 1));
			double curMoney = 0.0;
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select distinct payplancode from lcinsureaccclass where contno = '"+"?contNo?"+"'");
			sqlbv1.put("contNo", mLPEdorItemSchema.getContNo());
//			ExeSQL es = new ExeSQL();
			SSRS ss = new SSRS();
			ss = es.execSQL(sqlbv1);
			for(int iq = 1;iq<=ss.getMaxRow();iq++)
			{
				String strpayplancode = ss.GetText(iq, 1);
				String strcurrency = curSSRS.GetText(c, 1);
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql("Select 'X' From lcinsureaccclass Where contno = '"+"?contNo?"
		            	+"' And payplancode = '"+"?strpayplancode?"+"' And currency = '"+"?strcurrency?"+"'");
				sqlbv2.put("contNo", mLPEdorItemSchema.getContNo());
				sqlbv2.put("strpayplancode", strpayplancode);
				sqlbv2.put("strcurrency", strcurrency);
            	String tCur = es.getOneValue(sqlbv2);
            	if(tCur == null || "".equals(tCur) || !"X".equals(tCur)){
            		continue;
            	}
				
				LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
				LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql("select * from lcinsureacctrace where accalterno='"
						+ "?accAlterNo?" + "' and busytype='"
						+ "?busyType?" + "' and accaltertype='"
						+ "?accAlterType?" + "' and payplancode = '"+"?strpayplancode?"+"'");
				sqlbv3.put("accAlterNo", _LOPolAfterDealSchema.getAccAlterNo());
				sqlbv3.put("busyType", _LOPolAfterDealSchema.getBusyType());
				sqlbv3.put("accAlterType", _LOPolAfterDealSchema.getAccAlterType());
				sqlbv3.put("strpayplancode", strpayplancode);
				mLCInsureAccTraceSet = mLCInsureAccTraceDB.executeQuery(sqlbv3);
				if(mLCInsureAccTraceSet == null || mLCInsureAccTraceSet.size() <= 0){
					continue;
				}
				double lzgetmoney = 0;
				for (int i = 1; i <= mLCInsureAccTraceSet.size(); i++) {
	
					LCInsureAccTraceSchema mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					mLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(i);
					curMoney -= mLCInsureAccTraceSchema.getMoney();
//					summoney -= mLCInsureAccTraceSchema.getMoney();
	
					LCPolDB mLCPolDB = new LCPolDB();
					mLCPolDB.setPolNo(mLCInsureAccTraceSchema.getPolNo());
					if (!mLCPolDB.getInfo()) {
						CError.buildErr(this, "保单信息查询失败!");
						return false;
					}
					lzgetmoney = lzgetmoney + mLCInsureAccTraceSchema.getMoney();
				}
				LJAGetEndorseSchema _LJAGetEndorseSchema = new LJAGetEndorseSchema();
				_LJAGetEndorseSchema = createLJAGetEndorse(mLCInsureAccTraceSet.get(1),
						_LJAGetSchema, -lzgetmoney, "TB",BqCode.Get_BaseCashValue,curSSRS.GetText(c, 1));
	//			logger.debug("AR后续处理中LJAGetEndorse的PayPlanCode ::::?|?|?|"+_LJAGetEndorseSchema.getPayPlanCode());
				_LJAGetEndorseSet.add(_LJAGetEndorseSchema);
				
				//赎回手续费,根据账户比例计算
				TLbqForFee lzTLbqForFee = new TLbqForFee();
				double lzCalFee = 0;
				LCInsureAccTraceSchema iLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(1);
				iLCInsureAccTraceSchema.setMoney(lzgetmoney);
				lzCalFee = lzTLbqForFee.CalAccFee(iLCInsureAccTraceSchema);
				logger.debug("手续费是:::" + lzCalFee);
				if (lzCalFee > 0) {
					LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
					tLJAGetEndorseSchema.setSchema(_LJAGetEndorseSchema);
					tLJAGetEndorseSchema.setGetMoney(lzCalFee);
					tLJAGetEndorseSchema.setSubFeeOperationType(BqCode.Get_TLBQARFee); // 补退费子业务类型
					tLJAGetEndorseSchema.setPayPlanCode(strpayplancode);
					tLJAGetEndorseSchema.setDutyCode("000000");
					_LJAGetEndorseSet.add(tLJAGetEndorseSchema);
				}
				curMoney -= lzCalFee;
				
			}
			LDExch ex = new LDExch();
			summoney +=  ex.toBaseCur(curSSRS.GetText(c, 1),mLCPolSchema.getCurrency(), _DealDate, curMoney);
			_LJAGetSchema.setSumGetMoney(curMoney);
			_LJAGetSet.add(_LJAGetSchema);
		}

		//////////////////单独更新class的sumapym\\\\\\\\\\\\\\\
		LCInsureAccClassSet lzLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccClassDB lzLCInsureAccClassDB = new LCInsureAccClassDB();
		lzLCInsureAccClassDB.setContNo(_LOPolAfterDealSchema.getContNo());
		lzLCInsureAccClassSet = lzLCInsureAccClassDB.query();
		for(int i = 1;i<= lzLCInsureAccClassSet.size();i++)
		{
			LCInsureAccClassSchema lzlzLCInsureAccClassSchema = new LCInsureAccClassSchema();
			lzlzLCInsureAccClassSchema = lzLCInsureAccClassSet.get(i);
			LCInsureAccTraceSet lzLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LCInsureAccTraceDB lzLCInsureAccTraceDB = new LCInsureAccTraceDB();
			lzLCInsureAccTraceDB.setAccAlterNo(_LOPolAfterDealSchema.getAccAlterNo());
			lzLCInsureAccTraceDB.setAccAlterType(_LOPolAfterDealSchema.getAccAlterType());
			lzLCInsureAccTraceDB.setBusyType(_LOPolAfterDealSchema.getBusyType());
			lzLCInsureAccTraceDB.setInsuAccNo(lzLCInsureAccClassSet.get(i).getInsuAccNo());
			lzLCInsureAccTraceDB.setPayPlanCode(lzLCInsureAccClassSet.get(i).getPayPlanCode());
			lzLCInsureAccTraceSet = lzLCInsureAccTraceDB.query();
			if(lzLCInsureAccTraceSet.size()<=0)
			{
				continue;
			}
			else
			{
				for(int j=1;j<=lzLCInsureAccTraceSet.size();j++)
				{
					lzlzLCInsureAccClassSchema.setSumPaym(Double.parseDouble(df2.format(lzlzLCInsureAccClassSchema.getSumPaym()))+Double.parseDouble(df2.format(lzLCInsureAccTraceSet.get(j).getMoney())));
				}
				logger.debug("lzlzLCInsureAccClassSchema的sumpaym::::(*..*)::::"+Double.parseDouble(df2.format(lzlzLCInsureAccClassSchema.getSumPaym())));
				_LCInsureAccClassSet.add(lzlzLCInsureAccClassSchema);
			}
		}
		
		//\\\\\\\\\\\\\\\\更新完毕/////////////////////////
		
		//////////////////单独更新acc的sumapym\\\\\\\\\\\\\\\
		
		LCInsureAccSet lzLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccDB lzLCInsureAccDB = new LCInsureAccDB();
		lzLCInsureAccDB.setContNo(_LOPolAfterDealSchema.getContNo());
		lzLCInsureAccSet = lzLCInsureAccDB.query();
		for(int i = 1;i<= lzLCInsureAccSet.size();i++)
		{
			LCInsureAccSchema lzlzLCInsureAccSchema = new LCInsureAccSchema();
			lzlzLCInsureAccSchema = lzLCInsureAccSet.get(i);
			LCInsureAccTraceSet lzLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LCInsureAccTraceDB lzLCInsureAccTraceDB = new LCInsureAccTraceDB();
			lzLCInsureAccTraceDB.setAccAlterNo(_LOPolAfterDealSchema.getAccAlterNo());
			lzLCInsureAccTraceDB.setAccAlterType(_LOPolAfterDealSchema.getAccAlterType());
			lzLCInsureAccTraceDB.setBusyType(_LOPolAfterDealSchema.getBusyType());
			lzLCInsureAccTraceDB.setInsuAccNo(lzLCInsureAccSet.get(i).getInsuAccNo());
			lzLCInsureAccTraceSet = lzLCInsureAccTraceDB.query();
			if(lzLCInsureAccTraceSet.size()<=0)
			{
				continue;
			}
			else
			{
				for(int j=1;j<=lzLCInsureAccTraceSet.size();j++)
				{
					lzlzLCInsureAccSchema.setSumPaym(Double.parseDouble(df2.format(lzlzLCInsureAccSchema.getSumPaym()))+Double.parseDouble(df2.format(lzLCInsureAccTraceSet.get(j).getMoney())));
				}
				logger.debug("lzlzLCInsureAccSchema的sumpaym::::(*_*)::::"+Double.parseDouble(df2.format(lzlzLCInsureAccSchema.getSumPaym())));
				_LCInsureAccSet.add(lzlzLCInsureAccSchema);
			}
		}
		
		//\\\\\\\\\\\\\\\\\\\\\\\\/////////////////////////
		
//		//赎回手续费
//		// 确定手续费从哪里扣！！ 
//		TLbqForFee lzTLbqForFee = new TLbqForFee();
//		double lzCalFee = 0;
//		try {
//			lzCalFee = lzTLbqForFee.GetCalFee(summoney,mLPEdorItemSchema.getEdorNo());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.debug("手续费是:::" + lzCalFee);
//		if (lzCalFee > 0) {
//			LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
//			tLJAGetEndorseSchema.setSchema(_LJAGetEndorseSet.get(1));
//			tLJAGetEndorseSchema.setGetMoney(lzCalFee);
//			tLJAGetEndorseSchema.setSubFeeOperationType(BqCode.Get_TLBQARFee); // 补退费子业务类型
//			tLJAGetEndorseSchema.setPayPlanCode("000000");
//			tLJAGetEndorseSchema.setDutyCode("000000");
//			_LJAGetEndorseSet.add(tLJAGetEndorseSchema);
//		}

//		mLJAGetSchema.setSumGetMoney(summoney - lzCalFee);// 该不该加？
		mLPEdorItemSchema.setGetMoney(-summoney);//mLJAGetSchema.getSumGetMoney());
		LPEdorAppDB lzLPEdorAppDB = new LPEdorAppDB();
		lzLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		lzLPEdorAppSchema = lzLPEdorAppDB.query().get(1);
		lzLPEdorAppSchema.setGetMoney(-summoney);//mLJAGetSchema.getSumGetMoney());
		LPEdorMainDB lzLPEdorMainDB = new LPEdorMainDB();
		lzLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		
		lzLPEdorMainSchema = lzLPEdorMainDB.query().get(1);
		lzLPEdorMainSchema.setGetMoney(-summoney);//mLJAGetSchema.getSumGetMoney());
		logger.debug("#&^&%^&^*(@$@$(&*)(#$%(*)@%$*%@#%%^&#$@#*^&*&");

		if (!updateAccInfo()) {
			CError.buildErr(this, "账户信息更新失败!");
			return false;
		}
		return true;
	}

	/* 更新账户信息 */
	public boolean updateAccInfo() {
		VData tVData = new VData();
		MMap mmap = new MMap();
		// 准备公共提交数据

//		mmap.put(mLJAGetSchema, "INSERT");
		mmap.put(_LJAGetSet, "INSERT");
		mmap.put(_LJAGetEndorseSet, "INSERT");
		mmap.put(_LOPolAfterDealSchema, "UPDATE");
		mmap.put(mLPInsuAccOutSet, "UPDATE");

		mmap.put(_LCInsureAccClassSet, "UPDATE");
		mmap.put(_LCInsureAccSet, "UPDATE");
		mmap.put(mLPEdorItemSchema, "UPDATE");	
		mmap.put(lzLPEdorAppSchema, "UPDATE");
		mmap.put(lzLPEdorMainSchema, "UPDATE");

		if (mmap != null && mmap.keySet().size() > 0)
			tVData.add(mmap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean getInfo(LCInsureAccTraceSchema pLCInsureAccTraceSchema) {
		// 查询保全项目信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(pLCInsureAccTraceSchema.getAccAlterNo());
		tLPEdorItemDB.setEdorType(pLCInsureAccTraceSchema.getBusyType());
		tLPEdorItemDB.setContNo(pLCInsureAccTraceSchema.getContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询批改项目信息失败!");
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemSet.get(1);

		// 查询险种保单详细信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(pLCInsureAccTraceSchema.getPolNo());
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种信息查询失败!");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() != 1) {
			CError.buildErr(this, "没有查到险种信息!");
			return false;
		}
		mLCPolSchema.setSchema(tLCPolSet.get(1));

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		// 创建或更新付费总表
		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetDB.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
		LJAGetSet tLJAGetSet = tLJAGetDB.query();
		if (tLJAGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全实付总表查询失败!");
			return false;
		}
		if (tLJAGetSet == null || tLJAGetSet.size() < 1) { // 创建
			createLJAGetSchema();
		} else if (tLJAGetSet.get(1).getEnterAccDate() != null
				&& !tLJAGetSet.get(1).getEnterAccDate().trim().equals("")) { // 已经到账，创建
			createLJAGetSchema();
		} else { // 更新
			mLJAGetSchema = tLJAGetSet.get(1);
		}

		return true;
	}

	/* 创建实付表结构 */
	public LJAGetSchema createLJAGet(String tCurrency) {
		LJAGetSchema mLJAGetSchema = new LJAGetSchema();
		String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
		String sActNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
		String sActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);

		mLJAGetSchema.setGetNoticeNo(sActNoticeNo);
		mLJAGetSchema.setActuGetNo(sActuGetNo);
		mLJAGetSchema.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLJAGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
		mLJAGetSchema.setBankCode(mLPEdorAppSchema.getBankCode());
		mLJAGetSchema.setBankAccNo(mLPEdorAppSchema.getBankAccNo());
		mLJAGetSchema.setAccName(mLPEdorAppSchema.getAccName());
		mLJAGetSchema.setPayMode(mLPEdorAppSchema.getGetForm());
		mLJAGetSchema.setOperator(mGlobalInput.Operator);
		mLJAGetSchema.setMakeDate(mCurrentDate);
		mLJAGetSchema.setMakeTime(mCurrentTime);
		mLJAGetSchema.setModifyDate(mCurrentDate);
		mLJAGetSchema.setModifyTime(mCurrentTime);
		mLJAGetSchema.setManageCom(mLCPolSchema.getManageCom());
		mLJAGetSchema.setDrawer(mLPEdorAppSchema.getPayGetName()); // 领取人
		mLJAGetSchema.setDrawerID(mLPEdorAppSchema.getPersonID()); // 身份证号
		mLJAGetSchema.setShouldDate(mCurrentDate);
		mLJAGetSchema.setCurrency(tCurrency);
		return mLJAGetSchema;
	}

	/* 创建批改补退费表结构 */
	public LJAGetEndorseSchema createLJAGetEndorse(
			LCInsureAccTraceSchema aLCInsureAccTraceSchema,
			LJAGetSchema aLJAGetSchema, double money, String aFeeType,
			String sSubFeeOperationType,String tCurrency) {
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		try {
			// 生成批改交退费表
			tLJAGetEndorseSchema.setActuGetNo(aLJAGetSchema.getActuGetNo());
			tLJAGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo()); // 给付通知书号码
			tLJAGetEndorseSchema
					.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tLJAGetEndorseSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
			tLJAGetEndorseSchema.setContNo(mLCPolSchema.getContNo());
			tLJAGetEndorseSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
			tLJAGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
			tLJAGetEndorseSchema
					.setGetDate(mLPEdorItemSchema.getEdorValiDate());
			tLJAGetEndorseSchema.setGetMoney(-money);
			tLJAGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
					.getEdorType()); // 补退费业务类型
			tLJAGetEndorseSchema.setSubFeeOperationType(sSubFeeOperationType); // 补退费子业务类型
			tLJAGetEndorseSchema.setFeeFinaType(aFeeType); // 补退费财务类型
			tLJAGetEndorseSchema.setPayPlanCode(aLCInsureAccTraceSchema
					.getPayPlanCode()); // 无作用
			tLJAGetEndorseSchema.setDutyCode(aLCInsureAccTraceSchema
					.getInsuAccNo());
			tLJAGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
			tLJAGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJAGetEndorseSchema.setGetFlag("0");
			tLJAGetEndorseSchema.setAgentCode(mLCPolSchema.getAgentCode());
			tLJAGetEndorseSchema.setAgentCom(mLCPolSchema.getAgentCom());
			tLJAGetEndorseSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
			tLJAGetEndorseSchema.setAgentType(mLCPolSchema.getAgentType());
			tLJAGetEndorseSchema.setKindCode(mLCPolSchema.getKindCode());
			tLJAGetEndorseSchema.setAppntNo(mLCPolSchema.getAppntNo());
			tLJAGetEndorseSchema.setRiskCode(mLCPolSchema.getRiskCode());
			tLJAGetEndorseSchema.setRiskVersion(mLCPolSchema.getRiskVersion());
			tLJAGetEndorseSchema.setApproveCode(mLCPolSchema.getApproveCode());
			tLJAGetEndorseSchema.setApproveDate(mLCPolSchema.getApproveDate());
			tLJAGetEndorseSchema.setApproveTime(mLCPolSchema.getApproveTime());
			tLJAGetEndorseSchema.setManageCom(mLCPolSchema.getManageCom());
			tLJAGetEndorseSchema.setOperator(aLJAGetSchema.getOperator());
			
			LMDutyPayRelaSchema lzLMDutyPayRelaSchema = new LMDutyPayRelaSchema();
			LMDutyPayRelaDB lzLMDutyPayRelaDB = new LMDutyPayRelaDB();
			lzLMDutyPayRelaDB.setPayPlanCode(tLJAGetEndorseSchema.getPayPlanCode());
			lzLMDutyPayRelaSchema = lzLMDutyPayRelaDB.query().get(1);
			
			tLJAGetEndorseSchema.setDutyCode(lzLMDutyPayRelaSchema.getDutyCode());
			tLJAGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tLJAGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
			tLJAGetEndorseSchema.setCurrency(tCurrency);
		} catch (Exception ex) {
			mErrors.addOneError(new CError("建立批改补退费信息异常！"));
			return null;
		}
		return tLJAGetEndorseSchema;
	}

	/**
	 * 准备付费总表数据
	 * 
	 * @param aGetMoney
	 * @param sManageCom
	 * @return LJSGetSchema
	 */
	public void createLJAGetSchema() {
		mLJAGetSchema = new LJAGetSchema();

		String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
		String sActNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
		String sActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);

		mLJAGetSchema.setGetNoticeNo(sActNoticeNo);
		mLJAGetSchema.setActuGetNo(sActuGetNo);
		mLJAGetSchema.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLJAGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
		mLJAGetSchema.setBankCode(mLPEdorAppSchema.getBankCode());
		mLJAGetSchema.setBankAccNo(mLPEdorAppSchema.getBankAccNo());
		mLJAGetSchema.setAccName(mLPEdorAppSchema.getAccName());
		mLJAGetSchema.setPayMode(mLPEdorAppSchema.getGetForm());
		mLJAGetSchema.setOperator(mGlobalInput.Operator);
		mLJAGetSchema.setMakeDate(mCurrentDate);
		mLJAGetSchema.setMakeTime(mCurrentTime);
		mLJAGetSchema.setModifyDate(mCurrentDate);
		mLJAGetSchema.setModifyTime(mCurrentTime);
		mLJAGetSchema.setManageCom(mLCPolSchema.getManageCom());

		mLJAGetSchema.setDrawer(mLPEdorAppSchema.getPayGetName()); // 领取人
		mLJAGetSchema.setDrawerID(mLPEdorAppSchema.getPersonID()); // 身份证号
		mLJAGetSchema.setShouldDate(mCurrentDate);
		mLJAGetSchema.setCurrency(mLCPolSchema.getCurrency());

		return;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return null;
	}
}
