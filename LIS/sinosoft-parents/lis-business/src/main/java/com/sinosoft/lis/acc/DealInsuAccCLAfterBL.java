package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.claim.*;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:投连后续处理理赔计价CL实现
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
public class DealInsuAccCLAfterBL extends DealInsuAccAfter {
private static Logger logger = Logger.getLogger(DealInsuAccCLAfterBL.class);
	public DealInsuAccCLAfterBL() {
	}

	private VData mResult = new VData();

	MMap mmap = new MMap();

	private BqNameFun mBqNameFun = new BqNameFun();

	/** 传出数据的容器 */
	public LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();

	private LOPolAfterDealSchema _LOPolAfterDealSchema = new LOPolAfterDealSchema();

	private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();

	private LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();

	private LCInsureAccTraceSet _LCInsureAccTraceSet = new LCInsureAccTraceSet();

	private LLClaimSchema _LLClaimSchema = new LLClaimSchema();

	private LLClaimPolicySet _LLClaimPolicySet = new LLClaimPolicySet();

	private LLClaimDetailSet _LLClaimDetailSet = new LLClaimDetailSet();

	private LLClaimDutyKindSet _LLClaimDutyKindSet = new LLClaimDutyKindSet();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	public String mCurrentDate = PubFun.getCurrentDate();

	public String mCurrentTime = PubFun.getCurrentTime();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/* 根据LOPolAfterDeal表中的账户信息进行处理 */
	public boolean dealAfter(GlobalInput tGlobalInput,
			LOPolAfterDealSchema tLOPolAfterDealSchema) {
		mGlobalInput = tGlobalInput;

		/* 账户转换CL后续处理逻辑 */
		if (!getInfo(tLOPolAfterDealSchema)) {
			logger.debug("-----------------------------------账户信息查询失败!---------------------------");
			return false;
		}
		LCInsureAccSet tLCInsureAccSaveSet = new LCInsureAccSet();
		LCInsureAccClassSet tLCInsureAccClassSaveSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		for (int i = 1; i <= _LCInsureAccSet.size(); i++) {

			LCInsureAccSchema tLCInsureAccSchema = _LCInsureAccSet.get(i);

			tLCInsureAccSchema.setUnitCount(0);
			tLCInsureAccSchema.setInsuAccBala(0);

			tLCInsureAccSchema.setModifyDate(mCurrentDate);
			tLCInsureAccSchema.setModifyTime(mCurrentTime);

			double accSumPay=0.0;
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
					.query();

			for (int m = 1; m <= tLCInsureAccClassSet.size(); m++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet
						.get(m);
				LCInsureAccTraceSchema tLCInsureAccTraceLPSchema = new LCInsureAccTraceSchema();
				String tLimit = PubFun.getNoLimit(tLCInsureAccSchema
						.getManageCom());
				String tSerialNo = PubFun1.CreateMaxNo("CLM", tLimit);
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLCInsureAccTraceLPSchema,
						tLCInsureAccClassSchema);

				tLCInsureAccTraceLPSchema.setSerialNo(tSerialNo); /* 流水号 */
				tLCInsureAccTraceLPSchema.setOtherNo(tLOPolAfterDealSchema.getAccAlterNo()); /* 对应赔案号 */
				tLCInsureAccTraceLPSchema.setOtherType(tLOPolAfterDealSchema.getAccAlterType());
				tLCInsureAccTraceLPSchema.setMoneyType("LP");
				tLCInsureAccTraceLPSchema.setUnitCount(-tLCInsureAccClassSchema
						.getUnitCount()); /* 本次单位数 */
				tLCInsureAccTraceLPSchema.setPayDate(tLOPolAfterDealSchema.getConfDate());

				double tPrice = 0.00;
				double tAccBala = 0.00;
				String sAccBala= new String ();
				LOAccUnitPriceSet tLOAccUnitPriceSet = new LOAccUnitPriceSet();
				LOAccUnitPriceDB tLOAccUnitPriceDB = new LOAccUnitPriceDB();
				// 按照理算日期的下一个计价日单位价格进行帐户计算
				String tStartDate = PubInsuAccFun.getNextStartDate(tLOPolAfterDealSchema.getConfDate(), tLCInsureAccTraceLPSchema.getInsuAccNo());
				if(tStartDate == null || "".equals(tStartDate)){
					this.mErrors.addOneError("投连险保单["
							+ tLCInsureAccTraceLPSchema.getPolNo() + "]在理算日期"
							+ mCurrentDate + "未公布资产评估价格！");
					return false;
				}
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(" select * from LOAccUnitPrice "
						+ " where state = '0' and insuaccno = '"
						+ "?insuAccNo?" + "' "
						+ " and startdate = '"+"?tStartDate?"+"'");
				sqlbv.put("insuAccNo", tLCInsureAccTraceLPSchema.getInsuAccNo());
				sqlbv.put("tStartDate", tStartDate);
//				         "(select min(startdate) from "
//						+ " LOAccUnitPrice where state = '0' and insuaccno = '"
//						+ tLCInsureAccTraceLPSchema.getInsuAccNo() + "' "
//						+ " and startdate >= '" + tLOPolAfterDealSchema.getConfDate() + "')";

				tLOAccUnitPriceSet = tLOAccUnitPriceDB.executeQuery(sqlbv);
				if (tLOAccUnitPriceSet.size() == 1) { // 立案确认后下一个计价日单位价格已出来
					tPrice = tLOAccUnitPriceSet.get(1).getUnitPriceSell();
				} else {
					this.mErrors.addOneError("投连险保单["
							+ tLCInsureAccTraceLPSchema.getPolNo() + "]在理算日期"
							+ mCurrentDate + "未公布下一个资产评估价格！");
					return false;
				}
				tAccBala = tPrice * tLCInsureAccClassSchema.getUnitCount();
				sAccBala=mBqNameFun.getRound(tAccBala, "0.00");

				tLCInsureAccTraceLPSchema.setMoney("-"+sAccBala);
				tLCInsureAccTraceLPSchema.setState("1");
				tLCInsureAccTraceLPSchema.setManageCom(tLCInsureAccSchema
						.getManageCom());
				tLCInsureAccTraceLPSchema.setOperator(tLCInsureAccSchema
						.getOperator());
				tLCInsureAccTraceLPSchema.setMakeDate(mCurrentDate);
				tLCInsureAccTraceLPSchema.setMakeTime(mCurrentTime);
				tLCInsureAccTraceLPSchema.setModifyDate(mCurrentDate);
				tLCInsureAccTraceLPSchema.setModifyTime(mCurrentTime);
				tLCInsureAccTraceLPSchema.setFeeCode("000000");
				tLCInsureAccTraceLPSchema.setAccAlterNo(tLOPolAfterDealSchema.getAccAlterNo());
				tLCInsureAccTraceLPSchema.setAccAlterType(tLOPolAfterDealSchema.getAccAlterType());
				tLCInsureAccTraceLPSchema.setBusyType("CL");
				tLCInsureAccTraceLPSchema.setShouldValueDate(tLOAccUnitPriceSet
						.get(1).getStartDate());
				tLCInsureAccTraceLPSchema.setValueDate(tLOPolAfterDealSchema.getDealDate());
				tLCInsureAccTraceSet.add(tLCInsureAccTraceLPSchema);

				tLCInsureAccClassSchema.setUnitCount(0);
				tLCInsureAccClassSchema.setInsuAccBala(0);
				tLCInsureAccClassSchema.setModifyDate(mCurrentDate);
				tLCInsureAccClassSchema.setModifyTime(mCurrentTime);

				//double sumPay=tLCInsureAccClassSchema.getSumPaym()+Double.parseDouble(sAccBala);
				//tLCInsureAccClassSchema.setSumPaym(sumPay);
				//accSumPay+=sumPay;
				tLCInsureAccClassSaveSet.add(tLCInsureAccClassSchema);

			}
			//double sumAccSumPay=tLCInsureAccSchema.getSumPaym()+accSumPay;
			//tLCInsureAccSchema.setSumPaym(sumAccSumPay);

			tLCInsureAccSaveSet.add(tLCInsureAccSchema);
		}

		_LOPolAfterDealSchema = tLOPolAfterDealSchema.getSchema();
//		_LOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
		_LOPolAfterDealSchema.setState("2");// 手续处理完成

		mmap.put(_LOPolAfterDealSchema, "UPDATE");
		mmap.put(tLCInsureAccClassSaveSet, "DELETE&INSERT");
		mmap.put(tLCInsureAccTraceSet, "DELETE&INSERT");
		mmap.put(tLCInsureAccSaveSet, "DELETE&INSERT");
		/*
		 * LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		 * LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		 * LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		 *
		 * for (int j = 1; j <= _LCInsureAccClassSet.size(); j++) {
		 * LCInsureAccClassSchema tLCInsureAccClassSchema = new
		 * LCInsureAccClassSchema(); LCInsureAccTraceSchema
		 * tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		 *
		 * Reflections refl = new Reflections(); tLCInsureAccClassSchema =
		 * _LCInsureAccClassSet.get(j);
		 * refl.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSchema);
		 *
		 * String tUnitCount = "-" + _LCInsureAccClassSet.get(j).getUnitCount();
		 * String tMoney = "-" + _LCInsureAccClassSet.get(j).getInsuAccBala();
		 * String tLimit = PubFun.getNoLimit(tLCInsureAccClassSchema
		 * .getManageCom()); String tSerialNo = PubFun1.CreateMaxNo("CLM",
		 * tLimit);
		 *
		 * tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
		 * tLCInsureAccTraceSchema.setUnitCount(tUnitCount);
		 * tLCInsureAccTraceSchema.setMoney(tMoney);
		 * tLCInsureAccTraceSchema.setAccAlterNo(tLOPolAfterDealSchema
		 * .getAccAlterNo()); tLCInsureAccTraceSchema.setAccAlterType("4");
		 * tLCInsureAccTraceSchema.setBusyType("CL");
		 * tLCInsureAccTraceSchema.setMoneyType("LP"); tLCInsureAccTraceSchema
		 * .setPolNo(tLCInsureAccClassSchema.getPolNo());
		 * tLCInsureAccTraceSchema.setState("1");
		 * tLCInsureAccTraceSchema.setManageCom(tLCInsureAccClassSchema
		 * .getManageCom());
		 * tLCInsureAccTraceSchema.setShouldValueDate(mCurrentDate);
		 * tLCInsureAccTraceSchema.setValueDate(mCurrentDate);
		 * tLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
		 * tLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
		 * tLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
		 * tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
		 *
		 * tLCInsureAccTraceSchema.setPayDate(mCurrentDate);
		 * tLCInsureAccTraceSchema.setFeeCode("000000");
		 * tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		 *
		 * tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		 *
		 * tLCInsureAccClassSchema.setInsuAccBala("0.000000");
		 * tLCInsureAccClassSchema.setUnitCount("0.00");
		 *
		 * tLCInsureAccClassSet.add(tLCInsureAccClassSchema); } for (int i = 1;
		 * i <= _LCInsureAccSet.size(); i++) { LCInsureAccSchema
		 * tLCInsureAccSchema = new LCInsureAccSchema();
		 *
		 * tLCInsureAccSchema = _LCInsureAccSet.get(i);
		 *
		 * tLCInsureAccSchema.setInsuAccBala("0.00");
		 * tLCInsureAccSchema.setUnitCount("0.000000");
		 *
		 * tLCInsureAccSet.add(tLCInsureAccSchema); }
		 *
		 * mmap.put(tLCInsureAccSet, "UPDATE"); mmap.put(tLCInsureAccClassSet,
		 * "UPDATE"); mmap.put(tLCInsureAccTraceSet, "INSERT");
		 *
		 * _LOPolAfterDealSchema = tLOPolAfterDealSchema.getSchema();
		 * _LOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
		 * _LOPolAfterDealSchema.setState("2");// 手续处理完成
		 *
		 * mmap.put(_LOPolAfterDealSchema, "UPDATE");
		 */

		if (!updateAccInfo()) {
			CError.buildErr(this, "账户信息更新失败!");
			return false;
		}
		return true;
	}

	/* 更新账户信息 */
	public boolean updateAccInfo() {
		VData tVData = new VData();

		// 准备公共提交数据
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
	private boolean getInfo(LOPolAfterDealSchema tLOPolAfterDealSchema) {
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		// LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();

		tLCInsureAccDB.setContNo(tLOPolAfterDealSchema.getContNo());
		tLCInsureAccDB.setPolNo(tLOPolAfterDealSchema.getPolNo());

		// tLCInsureAccClassDB.setContNo(tLOPolAfterDealSchema.getContNo());
		// tLCInsureAccClassDB.setPolNo(tLOPolAfterDealSchema.getPolNo());

		_LCInsureAccSet = tLCInsureAccDB.query();
		// _LCInsureAccClassSet = tLCInsureAccClassDB.query();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return null;
	}
}
