

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.db.RIAccumulateDefDB;
import com.sinosoft.lis.db.RIRecordTraceTempDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIRecordTraceTempSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 把有分保记录的临时表中的数据存到结果表
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
public class RIRemoveRecord implements RICalMan {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mAccumulateDefNo = "";
	private MMap mMap;
	private VData mVData = new VData();
	private RIWFLogSchema mRIWFLogSchema;
	private RIInitSplitSeg mRIInitSplitSeg;
	private String mEventType;
	private String[][] mSeg;
	private PubSubmit mPubSubmit = new PubSubmit();

	public RIRemoveRecord() {
	}

	private boolean init() {
		try {
			mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			mSeg = mRIInitSplitSeg.getValue();
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
			mEventType = cOperate;
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
		RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
		tRIAccumulateDefDB.setAccumulateDefNO(mAccumulateDefNo);
		tRIAccumulateDefDB.getInfo();

		if ("02".equals(tRIAccumulateDefDB.getBFFlag())) {
			if (!delRedunRecordVali()) {
				return false;
			}
		} else {
			if (!delRedunRecord()) {
				return false;
			}
		}
		
		if (!delRedunRecordAttach()) {
			return false;
		}

		if (!delRedunRecordAttachAddAmnt()) {
			return false;
		}

		if (!saveData()) {
			return false;
		}
		return true;
	}

	private boolean delRedunRecordVali() {
		if (mEventType.equals("01")) {
			return true;
		}
		String[] accbf =new String[2];
		if("L01010010012".equals(mAccumulateDefNo)){
			accbf[0]="L01010010011";
			accbf[1]="L01010010012";
		}else if("L01010010014".equals(mAccumulateDefNo)){
			accbf[0]="L01010010013";
			accbf[1]="L01010010014";
		}else if("L01010010022".equals(mAccumulateDefNo)){
			accbf[0]="L01010010021";
			accbf[1]="L01010010022";
		}else if("L01010030011".equals(mAccumulateDefNo)){
			accbf[0]="L01010030010";
			accbf[1]="L01010030011";
		}else if("L01010100011".equals(mAccumulateDefNo)){
			accbf[0]="L01010100010";
			accbf[1]="L01010100011";
		}else if("L01010160011".equals(mAccumulateDefNo)){
			accbf[0]="L01010160010";
			accbf[1]="L01010160011";
		}else if("L01010170011".equals(mAccumulateDefNo)){
			accbf[0]="L01010170010";
			accbf[1]="L01010170011";
		}else if("L01010180011".equals(mAccumulateDefNo)){
			accbf[0]="L01010180010";
			accbf[1]="L01010180011";
		}
		
		for (int i = 0; i < mSeg.length; i++) {
			StringBuffer strBuffer;
			strBuffer = new StringBuffer();
			// 有分保记录的临时表记录不删除
			strBuffer
					.append(" select * from rirecordtraceTemp a where not exists (select 'X' from rirecordtrace x where x.ContNo=a.contno and x.OtherNo=a.Otherno and x.AreaID=a.AreaID and x.RiskCode=a.RiskCode and x.AccumulateDefNO in ('"+accbf[0]+"','"+accbf[1]+"') and x.ridate<=a.ridate)");
			if (mEventType.equals("02")) {

				strBuffer.append(" and a.Eventtype='02'");

			} else if (mEventType.equals("03")) {
				// 保全必须是 减额/减人/客户资料变更 的保全项
				strBuffer
						.append(" and a.Eventtype='03' and a.Addsubflag in('3','4','CM') ");

			} else if (mEventType.equals("04")) {

				strBuffer.append(" and a.Eventtype='04'");
			}
			strBuffer.append(" and a.BatchNo='");
			strBuffer.append(this.mRIWFLogSchema.getBatchNo());
			strBuffer.append("' and a.AccumulateDefNO='");
			strBuffer.append(this.mAccumulateDefNo);
			strBuffer.append("' and a.eventno between '");
			strBuffer.append(mSeg[i][0]);
			strBuffer.append("' and '");
			strBuffer.append(mSeg[i][1]);
			strBuffer.append("' ");

			System.out.println(" 业务类型: " + mEventType + " 删除多余分保记录 sql： "
					+ strBuffer.toString());

			RIRecordTraceTempDB tRIRecordTraceTempDB = new RIRecordTraceTempDB();
			RIRecordTraceTempSet tRIRecordTraceTempSet = tRIRecordTraceTempDB
					.executeQuery(strBuffer.toString());
			if (tRIRecordTraceTempDB.mErrors.needDealError()) {
				buildError("initInfo", "累计风险编码：" + mAccumulateDefNo
						+ ",查询需要删除的分保记录时出错: ");
				return false;
			}
			mMap = new MMap();
			mMap.put(tRIRecordTraceTempSet, "DELETE");

			if (!saveData()) {
				return false;
			}
		}

		MMap tMap = new MMap();
		// 将临时表数据存入分保表
		String strSQL = "insert into rirecordtrace select * from rirecordtracetemp a where a.eventtype='"
				+ mEventType
				+ "' and (a.addsubflag!='6' or a.addsubflag is null ) and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "INSERT");
		// 删除临时表
		strSQL = " delete from rirecordtracetemp a where a.eventtype='"
				+ mEventType
				+ "'  and (a.addsubflag!='6' or a.addsubflag is null ) and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "DELETE");
		if (!saveData(tMap)) {
			return false;
		}
		return true;
	}

	/**
	 * 删除多余的记录
	 * 
	 * @return boolean
	 */
	private boolean delRedunRecord() {
		if (mEventType.equals("01")) {
			return true;
		}
		for (int i = 0; i < mSeg.length; i++) {
			StringBuffer strBuffer;
			strBuffer = new StringBuffer();
			// 有分保记录的临时表记录不删除
			strBuffer
					.append(" select * from rirecordtraceTemp a where not exists (select 'X' from rirecordtrace x where x.RIPreceptNo=a.RIPreceptNo and x.ContNo=a.contno and x.OtherNo=a.Otherno and x.AreaID=a.AreaID and x.RiskCode=a.RiskCode and x.AccumulateDefNO=a.AccumulateDefNO and x.ridate<=a.ridate)");
			if (mEventType.equals("02")) {

				strBuffer.append(" and a.Eventtype='02'");

			} else if (mEventType.equals("03")) {
				// 保全必须是 减额/减人/客户资料变更 的保全项
				strBuffer
						.append(" and a.Eventtype='03' and a.Addsubflag in('3','4','CM') ");

			} else if (mEventType.equals("04")) {

				strBuffer.append(" and a.Eventtype='04'");
			}
			strBuffer.append(" and a.BatchNo='");
			strBuffer.append(this.mRIWFLogSchema.getBatchNo());
			strBuffer.append("' and a.AccumulateDefNO='");
			strBuffer.append(this.mAccumulateDefNo);
			strBuffer.append("' and a.eventno between '");
			strBuffer.append(mSeg[i][0]);
			strBuffer.append("' and '");
			strBuffer.append(mSeg[i][1]);
			strBuffer.append("' ");

			System.out.println(" 业务类型: " + mEventType + " 删除多余分保记录 sql： "
					+ strBuffer.toString());

			RIRecordTraceTempDB tRIRecordTraceTempDB = new RIRecordTraceTempDB();
			RIRecordTraceTempSet tRIRecordTraceTempSet = tRIRecordTraceTempDB
					.executeQuery(strBuffer.toString());
			if (tRIRecordTraceTempDB.mErrors.needDealError()) {
				buildError("initInfo", "累计风险编码：" + mAccumulateDefNo
						+ ",查询需要删除的分保记录时出错: ");
				return false;
			}
			mMap = new MMap();
			mMap.put(tRIRecordTraceTempSet, "DELETE");

			if (!saveData()) {
				return false;
			}
		}

		MMap tMap = new MMap();
		// 将临时表数据存入分保表
		String strSQL = "insert into rirecordtrace select * from rirecordtracetemp a where a.eventtype='"
				+ mEventType
				+ "' and (a.addsubflag!='6' or a.addsubflag is null ) and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "INSERT");
		// 删除临时表
		strSQL = " delete from rirecordtracetemp a where a.eventtype='"
				+ mEventType
				+ "'  and (a.addsubflag!='6' or a.addsubflag is null ) and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "DELETE");
		if (!saveData(tMap)) {
			return false;
		}
		return true;
	}

	/**
	 * 删除依附主险新单多余的记录
	 * 
	 * @return boolean
	 */
	private boolean delRedunRecordAttach() {
		if (!mEventType.equals("01")) {
			return true;
		}
		for (int i = 0; i < mSeg.length; i++) {
			StringBuffer strBuffer;
			strBuffer = new StringBuffer();
			// 有分保记录的临时表记录不删除
			strBuffer
					.append(" select * from rirecordtraceTemp a where not exists (select 'X' from rirecordtrace x where 1=1 and x.ContNo = a.contno and x.OtherNo = a.Otherno and x.AreaID = a.AreaID and x.Eventtype = a.eventtype and exists (SELECT 1 FROM ripolrecord u,ripolrecordbake v where u.mainpolno=v.polno and v.eventtype = '01'  and v.eventno = x.eventno and u.eventno = a.eventno )) ");
			strBuffer.append(" and a.Eventtype='01'");
			strBuffer.append(" and a.BatchNo='");
			strBuffer.append(this.mRIWFLogSchema.getBatchNo());
			strBuffer.append("' and a.AccumulateDefNO='");
			strBuffer.append(this.mAccumulateDefNo);
			strBuffer.append("' and a.eventno between '");
			strBuffer.append(mSeg[i][0]);
			strBuffer.append("' and '");
			strBuffer.append(mSeg[i][1]);
			strBuffer.append("' ");

			System.out.println(" 业务类型: " + mEventType + " 删除依附主险新单多余的记录 sql： "
					+ strBuffer.toString());

			RIRecordTraceTempDB tRIRecordTraceTempDB = new RIRecordTraceTempDB();
			RIRecordTraceTempSet tRIRecordTraceTempSet = tRIRecordTraceTempDB
					.executeQuery(strBuffer.toString());
			if (tRIRecordTraceTempDB.mErrors.needDealError()) {
				buildError("initInfo", "累计风险编码：" + mAccumulateDefNo
						+ ",查询需要删除的分保记录时出错: ");
				return false;
			}
			mMap = new MMap();
			mMap.put(tRIRecordTraceTempSet, "DELETE");

			if (!saveData()) {
				return false;
			}
		}

		MMap tMap = new MMap();
		// 将临时表数据存入分保表
		String strSQL = "insert into rirecordtrace select * from rirecordtracetemp a where a.eventtype='"
				+ mEventType
				+ "' and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "INSERT");
		// 删除临时表
		strSQL = " delete from rirecordtracetemp a where a.eventtype='"
				+ mEventType + "' and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "DELETE");
		if (!saveData(tMap)) {
			return false;
		}
		return true;
	}

	/**
	 * 删除依附主险增额多余的记录
	 * 
	 * @return boolean
	 */
	private boolean delRedunRecordAttachAddAmnt() {
		if (!mEventType.equals("03")) {
			return true;
		}
		for (int i = 0; i < mSeg.length; i++) {
			StringBuffer strBuffer;
			strBuffer = new StringBuffer();
			// 有分保记录的临时表记录不删除
			strBuffer
					.append(" select * from rirecordtraceTemp a where not exists (select 'X' from rirecordtrace x where 1=1 and x.ContNo = a.contno and x.OtherNo = a.Otherno and x.AreaID = a.AreaID and x.Eventtype = '01' and  a.eventtype ='03' and exists (SELECT 1 FROM ripolrecord u,ripolrecordbake v where u.mainpolno=v.polno and v.eventtype = '01'  and u.eventtype='03' and v.eventno = x.eventno and u.eventno = a.eventno )) ");
			strBuffer.append(" and a.Eventtype='03' and a.Addsubflag='6' ");
			strBuffer.append(" and a.BatchNo='");
			strBuffer.append(this.mRIWFLogSchema.getBatchNo());
			strBuffer.append("' and a.AccumulateDefNO='");
			strBuffer.append(this.mAccumulateDefNo);
			strBuffer.append("' and a.eventno between '");
			strBuffer.append(mSeg[i][0]);
			strBuffer.append("' and '");
			strBuffer.append(mSeg[i][1]);
			strBuffer.append("' ");

			System.out.println(" 业务类型: " + mEventType + " 删除依附主险增额多余的记录 sql： "
					+ strBuffer.toString());

			RIRecordTraceTempDB tRIRecordTraceTempDB = new RIRecordTraceTempDB();
			RIRecordTraceTempSet tRIRecordTraceTempSet = tRIRecordTraceTempDB
					.executeQuery(strBuffer.toString());
			if (tRIRecordTraceTempDB.mErrors.needDealError()) {
				buildError("initInfo", "累计风险编码：" + mAccumulateDefNo
						+ ",查询需要删除的分保记录时出错: ");
				return false;
			}
			mMap = new MMap();
			mMap.put(tRIRecordTraceTempSet, "DELETE");

			if (!saveData()) {
				return false;
			}
		}

		MMap tMap = new MMap();
		// 将临时表数据存入分保表
		String strSQL = "insert into rirecordtrace select * from rirecordtracetemp a where a.eventtype='"
				+ mEventType
				+ "'  and a.addsubflag='6' and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "INSERT");
		// 删除临时表
		strSQL = " delete from rirecordtracetemp a where a.eventtype='"
				+ mEventType + "'  and a.addsubflag='6' and a.batchno='"
				+ this.mRIWFLogSchema.getBatchNo()
				+ "' and a.AccumulateDefNO='" + this.mAccumulateDefNo + "'";
		tMap.put(strSQL, "DELETE");
		if (!saveData(tMap)) {
			return false;
		}
		return true;
	}

	/**
	 * 保存结果
	 * 
	 * @return boolean
	 */
	private boolean saveData() {
		try {
			this.mVData.clear();
			this.mVData.add(mMap);
			if (!mPubSubmit.submitData(this.mVData, "")) {
				return false;
			}
			mMap = null;
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
	 * 保存结果
	 * 
	 * @return boolean
	 */
	private boolean saveData(MMap aMap) {
		try {
			this.mVData.clear();
			this.mVData.add(aMap);
			if (!mPubSubmit.submitData(this.mVData, "")) {
				return false;
			}
			aMap = null;
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

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CalItemDeal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public static void main(String[] args) {
		// FDate chgdate = new FDate();

	}
}

