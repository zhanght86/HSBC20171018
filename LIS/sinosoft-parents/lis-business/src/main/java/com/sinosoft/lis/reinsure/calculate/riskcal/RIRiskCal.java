



package com.sinosoft.lis.reinsure.calculate.riskcal;

import com.sinosoft.lis.db.RIPolRecordDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.reinsure.calculate.process.RIRecord;
import com.sinosoft.lis.reinsure.calculate.process.RIRecordMake;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIExchangeRateSet;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Compansy: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public class RIRiskCal implements RICalMan {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private int rowNum = 1000;
	private String mAccumulateDefNo = "";
	private boolean mEndFlag = false;
	private MMap mMap;
	private VData mVData = new VData();
	private RIWFLogSchema mRIWFLogSchema;
	private RIInitData mRIInitData;
	private RIRecordMake mRIRecordMake;
	private PubSubmit mPubSubmit = new PubSubmit();
	private RIExchangeRateSet mRIExchangeRateSet = new RIExchangeRateSet();

	public RIRiskCal() {
	}

	private boolean init() {
		try {
			mRIRecordMake = new RIRecordMake(mAccumulateDefNo);
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			mRIExchangeRateSet = mRIInitData.getRIExchangeRateSet();

		} catch (Exception ex) {
			buildError("initInfo", " 出错信息: " + ex.getMessage());
			return false;
		}
		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!init()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		try {
			mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
					"RIWFLogSchema", 0);
			if (mRIWFLogSchema == null) {
				buildError("getInputData", "得到日志信息失败！");
				return false;
			}
			mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
			if (mRIWFLogSchema.getStartDate() == null
					|| mRIWFLogSchema.getEndDate() == null) {
				buildError("getInputData", "日志信息中日期有误，请进行核对！");
				return false;
			}
			return true;
		} catch (Exception e) {
			buildError("getInputData", "数据校验程序取得参数时失败，失败原因：" + e.getMessage());
			return false;
		}

	}

	private boolean dealData() {
		while (1 == 1) {
			if (!calAccAmnt()) {
				return false;
			}
			if (mEndFlag) {
				break;
			}
		}

		return true;
	}

	private boolean calAccAmnt() {
		try {
			mMap = new MMap();
			mVData.clear();
			// 清空分保记录
			RIRecord.destory();
			RIPolRecordSet newRIPolRecordSet = getRIPolRecordSet();
			if (newRIPolRecordSet == null) {
				buildError("calAccAmnt",
						"查询接口表数据是出错：" + this.mErrors.getFirstError());
				return false;
			}
			if (newRIPolRecordSet.size() == 0) {
				mEndFlag = true;
				return true;
			}
			String insuredNo = "";
			ExeSQL tExeSQL = new ExeSQL();
			double accAmnt = 0;
			SSRS tSSRS = new SSRS();
			for (int i = 1; i <= newRIPolRecordSet.size(); i++) {
				double amountChang = 0;
				boolean calflag = true;// 计算风险保额标记
				if (newRIPolRecordSet.get(i).getRIPreceptNo() == null
						|| newRIPolRecordSet.get(i).getRIPreceptNo().equals("")) {
					buildError("calAccAmnt", "计算风险保额时出错，再保方案号为空。");
					return false;
				}
				RIAccumulateDefSchema tRIAccumulatedefSchema = mRIInitData
						.getRIAccumulateDefSchema();
				// 累计方式
				String accFlag = tRIAccumulatedefSchema.getAccumulateMode();
				// 风险保额变化量
				amountChang = newRIPolRecordSet.get(i).getChRiskAmnt()
						- newRIPolRecordSet.get(i).getPreChRiskAmnt();
				if (accFlag.equals("01")
						|| newRIPolRecordSet.get(i).getEventType().equals("11")) {// 不需要累计
					newRIPolRecordSet.get(i).setAccAmnt(
							newRIPolRecordSet.get(i).getChRiskAmnt());
				} else if (accFlag == null
						|| accFlag.equals("")
						|| accFlag.equals("02")
						|| (accFlag.equals("03") && newRIPolRecordSet.get(i)
								.getEventType().equals("02"))
						|| (accFlag.equals("03")
								&& newRIPolRecordSet.get(i).getEventType()
										.equals("03") && amountChang <= 0)) {// 需要累计
					tSSRS.Clear();
					// 如果是新被保人，开始重新累计
					if (!newRIPolRecordSet.get(i).getInsuredNo()
							.equals(insuredNo)) {
						insuredNo = newRIPolRecordSet.get(i).getInsuredNo();
						// 累计风险保额清零
						accAmnt = 0;
						// 清空分保记录
						RIRecord.destory();
						// 满期保单不进行分保计算。
						StringBuffer strBuffer = new StringBuffer();
						strBuffer
								.append("select nvl(sum(a.accamnt),0) from ripolrecordbake a where a.eventno = (select max(b.eventno) from ripolrecordbake b where b.InsuredNo=a.insuredno and b.AccumulateDefNO=a.accumulatedefno and b.EventType <> '04' and b.Getdate=(select max(c.Getdate) from ripolrecordbake c where c.InsuredNo=b.InsuredNo and c.AccumulateDefNO=b.AccumulateDefNO and c.EventType <> '04' and c.getdate<='");
						strBuffer.append(newRIPolRecordSet.get(i).getGetDate());
						strBuffer.append("')) and a.insuredno='");
						strBuffer.append(newRIPolRecordSet.get(i)
								.getInsuredNo());
						strBuffer.append("' and a.accumulatedefno='");
						strBuffer.append(mRIInitData.getRIAccumulateDefNo());
						strBuffer.append("' and a.eventtype<>'04' ");
						System.out.println(" cal accAmnt: "
								+ strBuffer.toString());
						tSSRS = tExeSQL.execSQL(strBuffer.toString());
						// 计算历史累计风险保额
						if(tSSRS.getMaxRow()!=0){
							accAmnt = (Double.parseDouble(tSSRS.GetText(1, 1)));
						}
					}
					if (!newRIPolRecordSet.get(i).getEventType().equals("04")
							&& calflag == true) {
						System.out.println(" eventno: "
								+ newRIPolRecordSet.get(i).getEventNo());
						//bake表不存在记录（可能是新单保全在提数时同时提到）
						if(accAmnt==0){
							accAmnt = calAccAmntNew(newRIPolRecordSet.get(i));
						}else
						{
							//续期时的风险保额变化量为0
							if(newRIPolRecordSet.get(i).getEventType().equals("02"))
							{
								amountChang = 0;
							}
							accAmnt = accAmnt + amountChang;
						}
						newRIPolRecordSet.get(i).setAccAmnt(accAmnt);
					}
				} else if ((accFlag.equals("03") && newRIPolRecordSet.get(i)
						.getEventType().equals("01"))
						|| (accFlag.equals("03")
								&& newRIPolRecordSet.get(i).getEventType()
										.equals("03") && amountChang > 0)) {// 对新单和增额保单重新累计风险保额，目前通过数据库函数实现
					accAmnt = calAccAmntNew(newRIPolRecordSet.get(i));
					// accAmnt = accAmnt;
					newRIPolRecordSet.get(i).setAccAmnt(accAmnt);
				}
				newRIPolRecordSet.get(i).setNodeState("08");
				// 如果为保单终止不进行处理
				if (newRIPolRecordSet.get(i).getEventType().equals("05")) {
					continue;
				}
				// 生成分保记录
				if (!mRIRecordMake.deal(newRIPolRecordSet.get(i))) {
					System.out.println(" eventno: "
							+ newRIPolRecordSet.get(i).getEventNo());
					this.mErrors.copyAllErrors(mRIRecordMake.mErrors);
					return false;
				}
				VData tVData = mRIRecordMake.getResult();
				if (!getMapInfo(tVData)) {
					buildError("calAccAmnt", "计算累计风险后，得到分保记录时出错。");
					return false;
				}
			}
			mMap.put(newRIPolRecordSet, "UPDATE");
			if (!saveData()) {
				buildError("calAccAmnt", "计算累计风险，更新接口表数据时出错。");
				return false;
			}
		} catch (Exception ex) {
			buildError("calAccAmnt", "计算累计风险时出错。" + ex.getMessage());
			return false;
		} finally {
			// 清空分保记录
			RIRecord.destory();
		}
		return true;
	}

	private RIPolRecordSet getRIPolRecordSet() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select * from (select * from ripolrecord a ,(select distinct insuredno A1 from ripolrecord s where s.AccumulateDefNO='");
		strBuffer.append(mAccumulateDefNo);
		strBuffer.append("' and s.NodeState <> '08' and s.batchno='");
		strBuffer.append(mRIWFLogSchema.getBatchNo());
		strBuffer.append("' and rownum <= ");
		strBuffer.append(rowNum);
		strBuffer.append(" ) x where a.insuredno=x.A1 and a.AccumulateDefNO='");
		strBuffer.append(mAccumulateDefNo
				+ "' and a.NodeState <> '08' and a.batchno='");
		strBuffer.append(mRIWFLogSchema.getBatchNo());
		strBuffer
				.append("' ) x order by x.InsuredNo,x.GetDate,x.EventType,x.MakeDate,x.MakeTime ");

		System.out.println(" 累计风险计算 sql: " + strBuffer.toString());

		RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
		RIPolRecordSet newRIPolRecordSet = tRIPolRecordDB
				.executeQuery(strBuffer.toString());
		if (tRIPolRecordDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tRIPolRecordDB.mErrors);
			return null;
		}
		return newRIPolRecordSet;
	}

	private double calAccAmntNew(RIPolRecordSchema tRIPolRecordSchema) {
		double accAmnt = 0;
		String CurrentDate = PubFun.getCurrentDate();
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		double exchangeRate = 0;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append(" select nvl(risk_amnt2(ceil(months_between(date'"
						+ CurrentDate
						+ "',b.cvalidate-1)/12),b.Amnt ,b.DutyCode ,b.PolNo,b.Currency,'"
						+ mRIInitData.getRIAccumulateDefNo()
						+ "'),0),b.Currency from lcpol a,lcduty b  ");
		strBuffer.append(" where a.Polno = b.Polno  ");
		strBuffer.append(" and (b.Cvalidate<'"+tRIPolRecordSchema.getCValiDate()+"' " +
				"or (b.Cvalidate='"+tRIPolRecordSchema.getCValiDate()+"' and a.maketime<(select maketime from lcpol where polno='"+tRIPolRecordSchema.getPolNo()+"')) )");
		strBuffer.append(" and a.Insuredno = '");
		strBuffer.append(tRIPolRecordSchema.getInsuredNo());
		strBuffer
				.append("' and exists (select * from Riaccumulategetduty x where x.Accumulatedefno = '");
		strBuffer.append(mRIInitData.getRIAccumulateDefNo());
		strBuffer
				.append("' and x.Associatedcode = a.Riskcode and b.Dutycode like x.Getdutycode||'%' ) ");
		//strBuffer.append(" and a.Contno!='" + tRIPolRecordSchema.getContNo()+ "'");
		strBuffer
				.append(" and appflag ='1' and exists(select 'X' from lccontstate where  StateType = 'Available' and State = '0' and enddate is null and polno=a.polno ) ");
		System.out.println(strBuffer.toString());

		tSSRS = tExeSQL.execSQL(strBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("InitInfo", "获取累计风险保额明细出错！");
			return -1;
		}
		//不存在该被保人的其他记录
		//累计风险保额为当前的风险保额
		if (tSSRS.getMaxRow() == 0) {
			accAmnt = tRIPolRecordSchema.getChRiskAmnt();
			return accAmnt;
		}
		String tCurrency = "";

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tCurrency = tSSRS.GetText(i, 2);
			for (int j = 1; j <= mRIExchangeRateSet.size(); j++) {
				if (tCurrency.equals(mRIExchangeRateSet.get(j).getCurrency())) {
					exchangeRate = mRIExchangeRateSet.get(j).getExchangeRate();
				}
			}
			accAmnt = accAmnt+ Double.parseDouble(tSSRS.GetText(i, 1))
					* exchangeRate;
		}
		//当前累计风险保额=以前的风险保额+当前的风险保额
		accAmnt = accAmnt + tRIPolRecordSchema.getChRiskAmnt();
		return accAmnt;
	}

	/**
	 * 保存结果
	 * 
	 * @return boolean
	 */
	private boolean saveData() {
		try {
			/**
			 * -----调试用----- System.out.println(" size: "+mMap.keySet().size());
			 * if(mMap!=null){ for (int i = 1; i <= mMap.keySet().size(); i++) {
			 * Object o = new Object(); if (mMap.getKeyByOrder(i + "")
			 * instanceof RIRecordTraceSet) { RIRecordTraceSet tRIRecordTraceSet
			 * = (RIRecordTraceSet)mMap.getKeyByOrder(i + ""); for(int
			 * j=1;j<=tRIRecordTraceSet.size();j++){
			 * System.out.println(" aa: "+tRIRecordTraceSet.get(j).getEventNo()
			 * +" bb: " +tRIRecordTraceSet.get(j).getAmountChang()); } } else if
			 * (mMap.getKeyByOrder(i + "") instanceof RIRecordTraceTempSet) {
			 * RIRecordTraceTempSet tRIRecordTraceTempSet =
			 * (RIRecordTraceTempSet) mMap.getKeyByOrder(i + ""); for (int j =
			 * 1; j <= tRIRecordTraceTempSet.size(); j++) {
			 * System.out.println(" bb: " +
			 * tRIRecordTraceTempSet.get(j).getEventNo() + " bb: " +
			 * tRIRecordTraceTempSet.get(j).getAmountChang()); } }else{
			 * RIPolRecordSet tRIPolRecordSet = (RIPolRecordSet)
			 * mMap.getKeyByOrder(i + ""); for (int j = 1; j <=
			 * tRIPolRecordSet.size(); j++) { System.out.println(" bb: " +
			 * tRIPolRecordSet.get(j).getEventNo() + " bb: " +
			 * tRIPolRecordSet.get(j).getRiskAmnt()); } } } } ----------------
			 **/
			mVData.clear();
			mVData.add(mMap);
			if (!mPubSubmit.submitData(mVData, "")) {
				return false;
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RICalItemProc";
			tError.functionName = "saveResult";
			tError.errorMessage = "风险计算，保存数据时出错：" + ex.getMessage();
			System.out.println(" ex.getMessage() " + ex.getMessage());
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 分保记录添加到MMap
	 * 
	 * @param tVData
	 *            VData
	 * @return boolean
	 */
	private boolean getMapInfo(VData tVData) {
		try {
			MMap tmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			mMap.add(tmap);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static void main(String[] args) {
		MMap map = new MMap();
		map.put("111", "1");
		map.put("222", "2");
		System.out.println(" aa:　" + map.getKeyByOrder("1"));
		System.out.println(" order: " + (String) map.getKeyByOrder("1"));

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIRiskCal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}

