

/**
 * LRBsnsBillBL.java
 * com.sinosoft.lis.reinsure
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 Nov 4, 2010 		曹淑国
 *
 * Copyright (c) 2010, TNT All Rights Reserved.
 */

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIBsnsBillBatchDB;
import com.sinosoft.lis.db.RIIncomeCompanyDB;
import com.sinosoft.lis.db.RIWFLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBsnsBillBatchSchema;
import com.sinosoft.lis.schema.RIBsnsBillDetailsSchema;
import com.sinosoft.lis.vschema.RIBsnsBillBatchSet;
import com.sinosoft.lis.vschema.RIBsnsBillDetailsSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * ClassName:LRBsnsBillBL Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author 曹淑国
 * @version
 * 
 */
public class LRBsnsBillBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String strOperate;
	private String mModType;
	private MMap mMap = new MMap();
	private PubSubmit tPubSubmit = new PubSubmit();
	ExeSQL mExeSQL = new ExeSQL();

	private RIBsnsBillBatchSchema mRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();

	private RIBsnsBillBatchSet outRIBsnsBillBatchSet = new RIBsnsBillBatchSet();
	private RIBsnsBillDetailsSet outRIBsnsBillDetailsSet = new RIBsnsBillDetailsSet();
	private RIBsnsBillBatchDB mRIBsnsBillBatchDB = new RIBsnsBillBatchDB();

	private String mStartDate;
	private String mEndDate;
	private String mRIcomCode;
	private String mRIContNo;

	// 业务处理相关变量
	/** 全局数据 */
	public LRBsnsBillBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;

		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("updateData", "修改时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		System.out.print("====Operator  BL=======" + globalInput.Operator);
		mRIBsnsBillBatchSchema = (RIBsnsBillBatchSchema) cInputData
				.getObjectByObjectName("RIBsnsBillBatchSchema", 0);
		mStartDate = mRIBsnsBillBatchSchema.getStartDate();
		mEndDate = mRIBsnsBillBatchSchema.getEndDate();
		mRIcomCode = mRIBsnsBillBatchSchema.getIncomeCompanyNo();
		mRIContNo = mRIBsnsBillBatchSchema.getRIContNo();

		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 根据分保数据生成 账单批次和明细数据 RIBsnsBillDetails RIBsnsBillBatch

		// （1）获取日期区间内的分保批次

		String logSQL = "select * from RIWFLog a where NodeState = '99' ";
		if (mRIContNo != null && !"".equals(mRIContNo)) {
			logSQL = logSQL
					+ " and exists(select '1' from RIPrecept b where b.AccumulateDefNO = a.taskcode and b.RIContNo = '"
					+ mRIContNo + "' )";
		}
		if (mRIcomCode != null && !"".equals(mRIcomCode)) {
			logSQL = logSQL
					+ " and exists(select '1' from RIPrecept c ,RIIncomeCompany d where c.AccumulateDefNO = a.taskcode and c.RIPreceptNo = d.RIPreceptNo and d.incomecompanytype = '01' and d.IncomeCompanyNo='"
					+ mRIcomCode + "')";
		}
		logSQL = logSQL + "and StartDate>='" + mStartDate + "' and EndDate<='"
				+ mEndDate + "'";

		RIWFLogSet tRIWFLogSet = new RIWFLogDB().executeQuery(logSQL);

		System.out.println("SQL=" + logSQL);

		if (tRIWFLogSet != null && tRIWFLogSet.size() > 0) {
			RIIncomeCompanyDB tRIIncomeCompanyDB = new RIIncomeCompanyDB();
			RIBsnsBillBatchSchema tRIBsnsBillBatchSchema = null;

			for (int i = 1; i <= tRIWFLogSet.size(); i++) {
				// （2）提取批次内的账单数据
				String sInComSql = " select * from RIIncomeCompany a where incomecompanytype = '01' "
						+ " and exists(select '1' from RIWFLog b , RIPrecept c where  a.RIPreceptNo = c.RIPreceptNo and b.taskcode=c.AccumulateDefNO and b.BatchNo = '"
						+ tRIWFLogSet.get(i).getBatchNo() + "')";
				if (mRIContNo != null && !"".equals(mRIContNo)) {
					sInComSql = sInComSql + " and a.RIContNo = '" + mRIContNo
							+ "'";
				}
				if (mRIcomCode != null && !"".equals(mRIcomCode)) {
					sInComSql = sInComSql + " and a.IncomeCompanyNo = '"
							+ mRIcomCode + "'";
				}

				RIIncomeCompanySet tRIIncomeCompanySet = tRIIncomeCompanyDB
						.executeQuery(sInComSql);
				if (tRIIncomeCompanySet != null
						&& tRIIncomeCompanySet.size() > 0) {
					for (int j = 1; j <= tRIIncomeCompanySet.size(); j++) {
						String tCycle = " select gitype from RIBarGainInfo  where RIContNo='"
								+ tRIIncomeCompanySet.get(j).getRIContNo()
								+ "' ";

						tRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
						String billno = PubFun1.CreateMaxNo("ribillno", 10);
						;

						tRIBsnsBillBatchSchema.setBillNo(billno);
						tRIBsnsBillBatchSchema.setRIContNo(tRIIncomeCompanySet
								.get(j).getRIContNo());
						tRIBsnsBillBatchSchema
								.setIncomeCompanyNo(tRIIncomeCompanySet.get(j)
										.getIncomeCompanyNo());
						// tRIBsnsBillBatchSchema.setBillingCycle(mExeSQL.getOneValue(tCycle));
						tRIBsnsBillBatchSchema.setBillTimes("1");
						tRIBsnsBillBatchSchema.setEndDate(mEndDate);
						tRIBsnsBillBatchSchema.setStartDate(mStartDate);
						tRIBsnsBillBatchSchema.setState("02");
						tRIBsnsBillBatchSchema
								.setOperator(globalInput.Operator);
						tRIBsnsBillBatchSchema
								.setManageCom(globalInput.ComCode);
						tRIBsnsBillBatchSchema.setMakeDate(PubFun
								.getCurrentDate());
						tRIBsnsBillBatchSchema.setMakeTime(PubFun
								.getCurrentTime());
						tRIBsnsBillBatchSchema.setModifyDate(PubFun
								.getCurrentDate());
						tRIBsnsBillBatchSchema.setModifyTime(PubFun
								.getCurrentTime());

						RIBsnsBillDetailsSet ttRIBsnsBillDetailsSet = getBillDetails(
								tRIWFLogSet.get(i).getBatchNo(),
								tRIIncomeCompanySet.get(j).getRIContNo(),
								tRIIncomeCompanySet.get(j).getIncomeCompanyNo(),
								billno);

						if (ttRIBsnsBillDetailsSet != null
								&& ttRIBsnsBillDetailsSet.size() > 0) {
							outRIBsnsBillDetailsSet.add(ttRIBsnsBillDetailsSet);
							outRIBsnsBillBatchSet.add(tRIBsnsBillBatchSchema);
						}
					}
				}
			}

			mMap.put(outRIBsnsBillDetailsSet, "INSERT");
			mMap.put(outRIBsnsBillBatchSet, "INSERT");
		} else {
			buildError("getInputData", "没有得到该日期内的分保数据！");
			return false;
		}
		return true;
	}

	private RIBsnsBillDetailsSet getBillDetails(String batchNo,
			String riContNo, String riComCode, String billNo) {
		RIBsnsBillDetailsSet tRIBsnsBillDetailsSet = new RIBsnsBillDetailsSet();

		String check = "select * from RIBsnsBillBatch where RIContNo = '"
				+ riContNo + "' and IncomeCompanyNo = '" + riComCode
				+ "' and EndDate>='" + mStartDate + "' ";
		RIBsnsBillBatchSet tRIBsnsBillBatchSet = mRIBsnsBillBatchDB
				.executeQuery(check);
		// 账单已经计算过
		if (tRIBsnsBillBatchSet != null && tRIBsnsBillBatchSet.size() > 0) {
			return tRIBsnsBillDetailsSet;
		}

		String sql = " select '01','Opening Reserve(期初准备金)', nvl(sum(b.StandbyDouble3),0),0 from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ riComCode
				+ "' and a.batchno in ("
				+ batchNo
				+ ") "
				+ " union all select '02','Reinsurance Premium(再保保费)',nvl(sum(b.PremChang),0),0 from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ riComCode
				+ "' and a.batchno in ("
				+ batchNo
				+ ") "
				+ " union all select '03','Interest on Reserves（准备金利息）',(nvl(sum(b.StandbyDouble5),0))*3/12,0 from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ riComCode
				+ "' and a.batchno in ("
				+ batchNo
				+ ") "
				+ " union all select '04','Commissions incurred by the Company（返还给经纪中介的佣金和摊回税收）',0,nvl(sum(b.StandbyDouble1),0)+nvl(sum(0),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ mRIcomCode
				+ "' and a.batchno in ("
				+ batchNo
				+ ") "
				+ " union all select '05','Reinsurance commissions（摊回佣金）' ,0,nvl(sum(b.CommChang),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ riComCode
				+ "' and a.batchno in ("
				+ batchNo
				+ ")  "
				+ " union all select '06','Claims incurred（理赔摊回）' ,0,nvl(sum(b.ReturnPay),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype='04' and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ riComCode
				+ "' and a.batchno in ("
				+ batchNo
				+ ")   "
				+ " union all select '07','Policyholder''s profit sharing incurred by the Company',0,0 from ldsysvar where sysvar='onerow'  "
				+ " union all select '08','Closing reserves（期末准备金）' ,0,nvl(sum(b.StandbyDouble3),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = '"
				+ riContNo
				+ "')) and b.incomecompanyno = '"
				+ riComCode
				+ "' and a.batchno in( " + batchNo + ") ";

		System.out.println(" SQL: " + sql);

		SSRS tSSRS = mExeSQL.execSQL(sql);
		// 查询结果的记录条数
		int count = tSSRS.getMaxRow();
		System.out.println("该sql执行后的记录条数为：" + count);
		// 将查询结果存放到临时 二维数组中
		String temp[][] = tSSRS.getAllData();
		RIBsnsBillDetailsSchema t1RIBsnsBillDetailsSchema = null;
		RIBsnsBillDetailsSchema t2RIBsnsBillDetailsSchema = null;
		for (int i = 0; i < count; i++) {
			t1RIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
			t2RIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();

			t1RIBsnsBillDetailsSchema.setBillNo(billNo);
			// t1RIBsnsBillDetailsSchema.setSerialNo("1");
			// t1RIBsnsBillDetailsSchema.setDebCre("D");
			// t1RIBsnsBillDetailsSchema.setDetails(temp[i][0]);
			// t1RIBsnsBillDetailsSchema.setDetailsName( temp[i][1]);
			t1RIBsnsBillDetailsSchema.setSummoney(temp[i][2]);
			t1RIBsnsBillDetailsSchema.setMakeDate(PubFun.getCurrentDate());
			t1RIBsnsBillDetailsSchema.setMakeTime(PubFun.getCurrentTime());
			t1RIBsnsBillDetailsSchema.setModifyDate(PubFun.getCurrentDate());
			t1RIBsnsBillDetailsSchema.setModifyTime(PubFun.getCurrentTime());
			t1RIBsnsBillDetailsSchema.setOperator(globalInput.Operator);
			t1RIBsnsBillDetailsSchema.setManageCom(globalInput.ComCode);

			t2RIBsnsBillDetailsSchema.setBillNo(billNo);
			// t2RIBsnsBillDetailsSchema.setSerialNo("1");
			// t2RIBsnsBillDetailsSchema.setDebCre("C");
			// t2RIBsnsBillDetailsSchema.setDetails(temp[i][0]);
			// t2RIBsnsBillDetailsSchema.setDetailsName( temp[i][1]);
			t2RIBsnsBillDetailsSchema.setSummoney(temp[i][3]);
			t2RIBsnsBillDetailsSchema.setMakeDate(PubFun.getCurrentDate());
			t2RIBsnsBillDetailsSchema.setMakeTime(PubFun.getCurrentTime());
			t2RIBsnsBillDetailsSchema.setModifyDate(PubFun.getCurrentDate());
			t2RIBsnsBillDetailsSchema.setModifyTime(PubFun.getCurrentTime());
			t2RIBsnsBillDetailsSchema.setOperator(globalInput.Operator);
			t2RIBsnsBillDetailsSchema.setManageCom(globalInput.ComCode);

			tRIBsnsBillDetailsSet.add(t1RIBsnsBillDetailsSchema);
			tRIBsnsBillDetailsSet.add(t2RIBsnsBillDetailsSchema);

		}

		return tRIBsnsBillDetailsSet;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public String getResult() {
		return "";
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * main:(这里用一句话描述这个方法的作用)
	 * 
	 * @param @param args 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */

	public static void main(String[] args) {

		// TODO Auto-generated method stub

	}

}
