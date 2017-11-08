package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class GrpEdorCTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorCTConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
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

		if (!dealData()) {
			return false;
		}

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	private boolean dealData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
			CError.buildErr(this, "查询个人保全信息失败!");
			return false;
		}
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			mInputData.clear();
			mInputData.add(mGlobalInput);
			mInputData.add(tLPEdorItemSet.get(i));
			PEdorCTConfirmBL tPEdorCTConfirmBL = new PEdorCTConfirmBL();
			if (!tPEdorCTConfirmBL.submitData(mInputData, "")) {
				mErrors.copyAllErrors(tPEdorCTConfirmBL.mErrors);
				CError.buildErr(this, "处理个人退保失败!");
				return false;
			}
			MMap map = (MMap) tPEdorCTConfirmBL.getResult()
					.getObjectByObjectName("MMap", 0);
			if (map == null) {
				CError.buildErr(this, "接收个人退保处理数据失败!");
				return false;
			}
			mMap.add(map);
		}

		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolSet == null || tLPGrpPolSet.size() == 0) {
			CError.buildErr(this, "查询退保险种信息失败!");
			return false;
		}

		MMap map = dealGrpPol(tLPGrpPolSet);
		if (map == null) {
			return false;
		}
		mMap.add(map);

		return true;
	}

	/**
	 * 处理团体险种信息
	 * 
	 * @param cLPGrpPolSet
	 * @return MMap
	 */
	private MMap dealGrpPol(LPGrpPolSet cLPGrpPolSet) {
		MMap map = new MMap();
		LPGrpContStateSet aLPGrpContStateSet = new LPGrpContStateSet();
		LCGrpContStateSet aLCGrpContStateSet = new LCGrpContStateSet();

		LCGrpPolSet aLCGrpPolSet = new LCGrpPolSet();
		LPGrpPolSet aLPGrpPolSet = new LPGrpPolSet();

		Reflections tRef = new Reflections();

		String GrpPolNo = "";
		for (int i = 1; i <= cLPGrpPolSet.size(); i++) {
			if (i == 1) {
				GrpPolNo = "'" + cLPGrpPolSet.get(i).getGrpPolNo() + "'";
			} else {
				GrpPolNo += "," + "'" + cLPGrpPolSet.get(i).getGrpPolNo() + "'";
			}

			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tRef.transFields(tLCGrpPolSchema, cLPGrpPolSet.get(i));
			tLCGrpPolSchema.setModifyDate(CurrDate);
			tLCGrpPolSchema.setModifyTime(CurrTime);
			tLCGrpPolSchema.setOperator(mGlobalInput.Operator);
			aLCGrpPolSet.add(tLCGrpPolSchema);

			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(cLPGrpPolSet.get(i).getGrpPolNo());
			if (!tLCGrpPolDB.getInfo()) {
				CError.buildErr(this, "查询团体险种信息失败!");
				return null;
			}
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tRef.transFields(tLPGrpPolSchema, tLCGrpPolDB.getSchema());
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setModifyDate(CurrDate);
			tLPGrpPolSchema.setModifyTime(CurrTime);
			tLPGrpPolSchema.setOperator(mGlobalInput.Operator);
			aLPGrpPolSet.add(tLPGrpPolSchema);

			LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
			LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
			String SQL = "select * from lcgrpcontstate "
					+ " where grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "'"
					+ " and grppolno = '" + cLPGrpPolSet.get(i).getGrpPolNo()
					+ "'" + " and statetype = 'Terminate'" + " and state = '0'"
					+ " and startdate <= '"
					+ mLPGrpEdorItemSchema.getEdorValiDate() + "'"
					+ " and (enddate is null or enddate > '"
					+ mLPGrpEdorItemSchema.getEdorValiDate() + "'";
			tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(SQL);
			if (tLCGrpContStateSet == null || tLCGrpContStateSet.size() == 0) {
				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tLCGrpContStateSchema.setGrpContNo(mLPGrpEdorItemSchema
						.getGrpContNo());
				tLCGrpContStateSchema.setGrpPolNo(cLPGrpPolSet.get(i)
						.getGrpPolNo());
				tLCGrpContStateSchema.setStateType("Terminate");
				tLCGrpContStateSchema.setState("1");
				tLCGrpContStateSchema.setStateReason("02");
				tLCGrpContStateSchema.setStartDate(mLPGrpEdorItemSchema
						.getEdorValiDate());
				tLCGrpContStateSchema.setEndDate("");
				tLCGrpContStateSchema.setOperator(mGlobalInput.Operator);
				tLCGrpContStateSchema.setMakeDate(CurrDate);
				tLCGrpContStateSchema.setMakeTime(CurrTime);
				tLCGrpContStateSchema.setModifyDate(CurrDate);
				tLCGrpContStateSchema.setModifyTime(CurrTime);
				aLCGrpContStateSet.add(tLCGrpContStateSchema);
				tLCGrpContStateSchema = null;
			} else {
				LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
				tRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet
						.get(1));
				tLPGrpContStateSchema.setOperator(mGlobalInput.Operator);
				tLPGrpContStateSchema.setModifyDate(CurrDate);
				tLPGrpContStateSchema.setModifyTime(CurrTime);
				tLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema
						.getEdorType());
				aLPGrpContStateSet.add(tLPGrpContStateSchema);
				tLPGrpContStateSchema = null;

				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tLCGrpContStateSchema.setSchema(tLCGrpContStateSet.get(1));// 终止以前的状态
				LCGrpContStateSchema cLCGrpContStateSchema = new LCGrpContStateSchema();
				cLCGrpContStateSchema.setSchema(tLCGrpContStateSet.get(1));// 增加新的状态

				cLCGrpContStateSchema.setEndDate("");
				cLCGrpContStateSchema.setState("1");
				cLCGrpContStateSchema.setStateReason("02");
				cLCGrpContStateSchema.setOperator(mGlobalInput.Operator);
				cLCGrpContStateSchema.setMakeDate(CurrDate);
				cLCGrpContStateSchema.setMakeTime(CurrTime);
				cLCGrpContStateSchema.setModifyDate(CurrDate);
				cLCGrpContStateSchema.setModifyTime(CurrTime);

				// 为了避免主键冲突，对于原状态的StartDate与EdorValidate相同时进行特殊处理
				FDate tFD = new FDate();
				if (tFD.getDate(tLCGrpContStateSchema.getStartDate()).equals(
						tFD.getDate(mLPGrpEdorItemSchema.getEdorValiDate()))) {
					tLCGrpContStateSchema.setEndDate(mLPGrpEdorItemSchema
							.getEdorValiDate());
					aLCGrpContStateSet.add(tLCGrpContStateSchema);
					cLCGrpContStateSchema.setStartDate(PubFun.calDate(
							mLPGrpEdorItemSchema.getEdorValiDate(), 1, "D",
							null));
					aLCGrpContStateSet.add(cLCGrpContStateSchema);
					cLCGrpContStateSchema = null;
					tLCGrpContStateSchema = null;
				} else {
					tLCGrpContStateSchema.setEndDate(PubFun.calDate(
							mLPGrpEdorItemSchema.getEdorValiDate(), -1, "D",
							null));
					aLCGrpContStateSet.add(tLCGrpContStateSchema);
					cLCGrpContStateSchema.setStartDate(mLPGrpEdorItemSchema
							.getEdorValiDate());
					aLCGrpContStateSet.add(cLCGrpContStateSchema);
					cLCGrpContStateSchema = null;
					tLCGrpContStateSchema = null;
				}
			}
		}

		ExeSQL tES = new ExeSQL();
		SSRS tSSRS = tES
				.execSQL("select 1 from lcgrppol where appflag = '1' and grppolno not in ("
						+ GrpPolNo
						+ ") and grpcontno = '"
						+ mLPGrpEdorItemSchema.getGrpContNo() + "'");
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
			tLCGrpContStateSchema.setGrpContNo(mLPGrpEdorItemSchema
					.getGrpContNo());
			tLCGrpContStateSchema.setGrpPolNo("000000");
			tLCGrpContStateSchema.setStateType("Terminate");
			tLCGrpContStateSchema.setState("1");
			tLCGrpContStateSchema.setStateReason("02");
			tLCGrpContStateSchema.setStartDate(mLPGrpEdorItemSchema
					.getEdorValiDate());
			tLCGrpContStateSchema.setEndDate("");
			tLCGrpContStateSchema.setOperator(mGlobalInput.Operator);
			tLCGrpContStateSchema.setMakeDate(CurrDate);
			tLCGrpContStateSchema.setMakeTime(CurrTime);
			tLCGrpContStateSchema.setModifyDate(CurrDate);
			tLCGrpContStateSchema.setModifyTime(CurrTime);
			aLCGrpContStateSet.add(tLCGrpContStateSchema);
			tLCGrpContStateSchema = null;

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			if (!tLCGrpContDB.getInfo()) {
				CError.buildErr(this, "查询团险保单信息失败!");
				return null;
			}
			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
			tLCGrpContSchema.setSchema(tLCGrpContDB.getSchema());
			tRef.transFields(tLPGrpContSchema, tLCGrpContSchema);
			tLPGrpContSchema.setOperator(mGlobalInput.Operator);
			tLPGrpContSchema.setModifyDate(CurrDate);
			tLPGrpContSchema.setModifyTime(CurrTime);
			tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());

			tLCGrpContSchema.setAppFlag("4");
			map.put(tLCGrpContSchema, "UPDATE");
			map.put(tLPGrpContSchema, "INSERT");
		}
		// ===add===zhangtao===2006-08-04===支持团体公共账户处理=================BGN========
		// 公共账户额外处理，暂时只对轨迹表进行处理
		LCInsureAccTraceSet insPubLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCPolDB aLCPolDB = new LCPolDB();
		aLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		aLCPolDB.setPolTypeFlag("2");
		LCPolSet aLCPolSet = aLCPolDB.query();
		if (aLCPolSet.size() > 0) {
			LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
			tLPInsureAccTraceDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPInsureAccTraceDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPInsureAccTraceDB.setPolNo(aLCPolSet.get(1).getPolNo()); // 公共账户
			tLPInsureAccTraceDB.setMoneyType("PG");
			LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB
					.query();
			if (tLPInsureAccTraceDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
				return null;
			}
			if (tLPInsureAccTraceSet != null && tLPInsureAccTraceSet.size() > 0) {
				LCInsureAccTraceSchema tLCInsureAccTraceSchema;
				for (int k = 1; k <= tLPInsureAccTraceSet.size(); k++) {
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tRef.transFields(tLCInsureAccTraceSchema,
							tLPInsureAccTraceSet.get(k));
					insPubLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				}
				map.put(insPubLCInsureAccTraceSet, "INSERT");
			}
		}
		// ===add===zhangtao===2006-08-04===支持团体公共账户处理=================END========
		map.put(aLPGrpContStateSet, "DELETE&INSERT");
		map.put(aLCGrpContStateSet, "DELETE&INSERT");
		map.put(aLPGrpPolSet, "DELETE&INSERT");
		map.put(aLCGrpPolSet, "DELETE&INSERT");

		return map;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "接收数据不完整!");
			return false;
		}

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
