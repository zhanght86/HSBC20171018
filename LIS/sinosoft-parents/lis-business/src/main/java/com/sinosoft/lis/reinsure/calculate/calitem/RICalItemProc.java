

package com.sinosoft.lis.reinsure.calculate.calitem;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.vschema.RIPreceptSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class RICalItemProc implements RICalMan {
	public RICalItemProc() {
	}

	public CErrors mErrors = new CErrors();
	private RIInitData mRIInitData;
	private String mAccumulateDefNo = "";
	private RIWFLogSchema mRIWFLogSchema;
	private int rowNum = 1000; // 逐条数据处理时设置事务大小,现不采用
	private boolean mEndFlag = false; // 逐条数据处理时,结束标志,现不采用
	private MMap mMap = new MMap();
	private VData mInputData = new VData();
	private PubSubmit mPubSubmit = new PubSubmit();
	private RIInitSplitSeg mRIInitSplitSeg;
	private String[][] mSeg;
	private String mEventType;

	/**
	 * 计算、保存再保结果
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
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
		// 记录工作流程日志
		if (!recordLog()) {
			return false;
		}
		return true;
	}

	/**
	 * 得到业务数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mEventType = cOperate;
		mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
				"RIWFLogSchema", 0);
		mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
		return true;
	}

	private boolean init() {
		try {
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			mSeg = mRIInitSplitSeg.getValue();
		} catch (Exception ex) {
			buildError("initInfo", " 生成计算参数记录时，初始化参数失败: " + ex);
			return false;
		}
		return true;
	}

	/**
	 * 数据处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (!batchDeal()) {
			return false;
		}
		return true;
	}

	/**
	 * 批处理
	 * 
	 * @return boolean
	 */
	private boolean batchDeal() {
		RIPreceptSet tRIPreceptSet = mRIInitData.getRIPreceptSet();
		for (int i = 1; i <= tRIPreceptSet.size(); i++) {
			RIItemCalSet tRIItemCalSet = (RIItemCalSet) mRIInitData
					.getRIItemCalTD().getValueByName(
							tRIPreceptSet.get(i).getRIPreceptNo());
			RIItemCalSet riItemCalSet = new RIItemCalSet();
			//
			for (int j = 1; j <= tRIItemCalSet.size(); j++) {
				if (tRIItemCalSet.get(j).getCalItemType().equals(mEventType)) {
					riItemCalSet.add(tRIItemCalSet.get(j));
				}
			}
			if (!batchDealCal(riItemCalSet)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 批处理计算
	 * 
	 * @return boolean
	 */
	private boolean batchDealCal(RIItemCalSet aRIItemCalSet) {
		RIItemCalSet riItemCalSet = aRIItemCalSet;
		StringBuffer strBuffer;
		for (int j = 0; j < mSeg.length; j++) {
			for (int i = 1; i <= riItemCalSet.size(); i++) {
				strBuffer = new StringBuffer();
				mMap = new MMap();
				if (riItemCalSet.get(i).getItemCalType().equals("1")) {
					// 固定值
				} else if (riItemCalSet.get(i).getItemCalType().equals("2")) {

					strBuffer.append("update RIRecordTrace a set");
					strBuffer.append(" a.");
					strBuffer.append(riItemCalSet.get(i).getTarGetClumn());
					strBuffer.append(" =(");
					strBuffer.append(riItemCalSet.get(i).getCalSQL());
					if (riItemCalSet.get(i).getCalSQL2() != null
							&& !riItemCalSet.get(i).getCalSQL2().equals("")) {
						strBuffer
								.append(" " + riItemCalSet.get(i).getCalSQL2());
					}
					if (riItemCalSet.get(i).getCalSQL3() != null
							&& !riItemCalSet.get(i).getCalSQL3().equals("")) {
						strBuffer
								.append(" " + riItemCalSet.get(i).getCalSQL3());
					}
					strBuffer.append(") ");
					strBuffer.append(" where a.BatchNo='");
					strBuffer.append(mRIWFLogSchema.getBatchNo());
					strBuffer.append("' and a.AccumulateDefNO='");
					strBuffer.append(mRIWFLogSchema.getTaskCode());
					strBuffer.append("' and a.eventtype='");
					strBuffer.append(mEventType);
					strBuffer
							.append("' and exists(select 'X' from riprecept where arithmeticid = '");
					strBuffer.append(riItemCalSet.get(i).getArithmeticDefID());
					strBuffer.append("' and ripreceptno = a.ripreceptno)");
					strBuffer.append(" and a.EventNo between '");
					strBuffer.append(mSeg[j][0]);
					strBuffer.append("' and '");
					strBuffer.append(mSeg[j][1]);
					strBuffer.append("' ");
					mMap.put(strBuffer.toString(), "UPDATE");
					System.out.println(" sql: " + strBuffer.toString());
				} else if (riItemCalSet.get(i).getItemCalType().equals("3")) {
					try {
						strBuffer.append("update RIRecordTrace a set");
						Class tClass = Class.forName(riItemCalSet.get(i)
								.getCalClass());
						RIItemCal tRIItemCal = (RIItemCal) tClass.newInstance();
						tRIItemCal.dealData();
						strBuffer.append(" a.");
						strBuffer.append(riItemCalSet.get(i).getTarGetClumn());
						strBuffer.append(" = ");
						strBuffer.append(tRIItemCal.getValue());
						strBuffer.append(" where a.BatchNo='");
						strBuffer.append(mRIWFLogSchema.getBatchNo());
						strBuffer.append("' and a.AccumulateDefNO='");
						strBuffer.append(mRIWFLogSchema.getTaskCode());
						strBuffer.append("' and a.eventtype='");
						strBuffer.append(mEventType);
						strBuffer
								.append("' and exists(select 'X' from riprecept where arithmeticid = '");
						strBuffer.append(riItemCalSet.get(i)
								.getArithmeticDefID());
						strBuffer.append("' and ripreceptno = a.ripreceptno)");
						strBuffer.append(" and a.EventNo between '");
						strBuffer.append(mSeg[j][0]);
						strBuffer.append("' and '");
						strBuffer.append(mSeg[j][1]);
						strBuffer.append("' ");
						mMap.put(strBuffer.toString(), "UPDATE");
						System.out.println(" sql: " + strBuffer.toString());
					} catch (Exception ex) {
						buildError("RICalItemProc", " 再保计算时，取得算法为："
								+ riItemCalSet.get(i).getArithmeticID()
								+ "算法序号为："
								+ riItemCalSet.get(i).getCalItemOrder()
								+ "的参数值时出错：" + ex);
					}
				}
				if (!saveResult()) {
					return false;
				}
			}
		}
		return true;
	}

	//
	// /**
	// * 逐条计算结果(不采用)
	// * @return boolean
	// */
	// private boolean everyCalResult(){
	// RIItemCalClass tCalClass = new RIItemCalClass();
	// StringBuffer strBuffer = new StringBuffer();
	// strBuffer.append(" select a.* from rirecordtrace a ,(select r.eventno B1 from ripolrecord r where r.batchno='");
	// strBuffer.append(mRIWFLogSchema.getBatchNo());
	// strBuffer.append("' and r.accumulatedefno='");
	// strBuffer.append(mRIInitData.getRIAccumulateDefNo());
	// strBuffer.append("' and r.nodestate <> '11' and rownum<=");
	// strBuffer.append(rowNum);
	// strBuffer.append(" order by eventno) b where a.eventno=b.B1 order by serialno ");
	// System.out.println(" 再保记录表sql : "+strBuffer.toString());
	//
	// RIRecordTraceDB tRIRecordTraceDB = new RIRecordTraceDB();
	// RIRecordTraceSet tRIRecordTraceSet =
	// tRIRecordTraceDB.executeQuery(strBuffer.toString());
	// if (tRIRecordTraceDB.mErrors.needDealError()) {
	// this.mErrors.copyAllErrors(tRIRecordTraceDB.mErrors);
	// return false;
	// }
	// if (tRIRecordTraceSet.size() == 0) {
	// System.out.println(" 计算累计风险："+mRIInitData.getRIAccumulateDefNo()+" 的再保计算项完毕。");
	// mEndFlag = true;
	// return true;
	// }
	//
	// strBuffer = new StringBuffer();
	// strBuffer.append(" select a.* from ricalparam a ,(select r.eventno B1 from ripolrecord r where r.batchno='");
	// strBuffer.append(mRIWFLogSchema.getBatchNo());
	// strBuffer.append("' and r.accumulatedefno='");
	// strBuffer.append(mRIInitData.getRIAccumulateDefNo());
	// strBuffer.append("' and r.nodestate <> '11' and rownum<=");
	// strBuffer.append(rowNum);
	// strBuffer.append(" order by eventno) b where a.eventno=b.B1 order by serialno ");
	// System.out.println(" 再保参数表sql : "+strBuffer.toString());
	//
	// RICalParamDB tRICalParamDB = new RICalParamDB();
	// RICalParamSet tRICalParamSet =
	// tRICalParamDB.executeQuery(strBuffer.toString());
	// if (tRICalParamDB.mErrors.needDealError()) {
	// this.mErrors.copyAllErrors(tRICalParamDB.mErrors);
	// return false;
	// }
	// if (tRICalParamSet.size() == 0) {
	// buildError("calResult", "再保计算结果时出现错误：计算参数为空!");
	// return false;
	// }
	//
	// for(int i=1;i<=tRIRecordTraceSet.size();i++){
	// RIItemCalSet tRIItemCalSet =
	// (RIItemCalSet)mRIInitData.getRIItemCalTD().getValueByName(tRIRecordTraceSet.get(i).getRIPreceptNo());
	// for(int j=1;j<=tRIItemCalSet.size();j++){
	// if(tRIItemCalSet.get(j).getCalItemType().equals(tRIRecordTraceSet.get(i).getEventType())){
	// if(!tCalClass.calItem(tRIRecordTraceSet.get(i),tRICalParamSet.get(i),tRIItemCalSet.get(j))){
	// return false;
	// }
	// tRIRecordTraceSet.get(i).setV(tRIItemCalSet.get(j).getTarGetClumn(),tCalClass.getValue()+"");
	// }
	// }
	// }
	// mMap = new MMap();
	// mMap.put(tRIRecordTraceSet,"UPDATE");
	//
	// strBuffer = new StringBuffer();
	// strBuffer.append(" update ripolrecord a set a.nodestate='99' where exists (select * from (select b.eventno C1 from ripolrecord b where b.batchno = '");
	// strBuffer.append(mRIWFLogSchema.getBatchNo());
	// strBuffer.append("' and b.AccumulateDefNO= '");
	// strBuffer.append(mAccumulateDefNo);
	// strBuffer.append("' and b.nodestate='11' and rownum<=");
	// strBuffer.append(rowNum);
	// strBuffer.append(" order by b.eventno) c where a.eventno=c.C1) ");
	// System.out.println(" 再保参数表sql : "+strBuffer.toString());
	// mMap.put(strBuffer.toString(),"UPDATE");
	// if(!saveResult()){
	// return false;
	// }
	// return true;
	// }

	/**
	 * 执行SQL
	 * 
	 * @param strSQL
	 *            String
	 * @return boolean
	 */
	private boolean execUpdateSQL(String strSQL) {
		ExeSQL tExeSQL = new ExeSQL();
		if (!tExeSQL.execUpdateSQL(strSQL)) {
			buildError("RIDataMake", tExeSQL.mErrors.getFirstError());
			return false;
		}
		if (tExeSQL.mErrors.needDealError()) {
			buildError("RIDataMake", tExeSQL.mErrors.getFirstError());
			return false;
		}
		return true;
	}

	/**
	 * 保存结果
	 * 
	 * @return boolean
	 */
	private boolean saveResult() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
			if (!mPubSubmit.submitData(this.mInputData, "")) {
				if (mPubSubmit.mErrors.needDealError()) {
					buildError("saveResult", "保存再保计算结果时出现错误!");
					return false;
				}
			}
			mMap = null;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RICalItemProc";
			tError.functionName = "saveResult";
			tError.errorMessage = "保存再保结果时出错：" + ex.getMessage();
			System.out.println(" ex.getMessage() " + ex.getMessage());
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 记录工作流日志
	 * 
	 * @return boolean
	 */
	private boolean recordLog() {
		return true;
	}

	public static void main(String[] args) {
		RICalItemProc tRICalItemProc = new RICalItemProc();
		VData tVData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("0000000006");
		mRIWFLogSchema.setTaskCode("L000000004");
		tVData.add(mRIWFLogSchema);
		tRICalItemProc.submitData(tVData, "01");
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RICalItemProc";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

}
