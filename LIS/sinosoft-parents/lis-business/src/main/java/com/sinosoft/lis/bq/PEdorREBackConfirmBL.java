package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LOReturnLoanDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOReturnLoanSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 复效回退处理确认类
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
 * @author Lizhuo
 * @CreateDate 2005-10-10
 * @version 1.0
 */
public class PEdorREBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorREBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorREBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 复效回退确认生效 ===");
		Reflections tRef = new Reflections();

		// 保单状态处理
		LCContStateSet aLCContStateSet = new LCContStateSet();
		LCContStateSet tLCContStateSet = new LCContStateSet();

		//复效要处理三种保单状态
		String[] tStateType = {"Available","Loan","PayPrem"};
		for (int iType=0;iType<tStateType.length;iType++) {
			LPContStateDB tLPContStateDB = new LPContStateDB();
			LPContStateSet tLPContStateSet = new LPContStateSet();
			tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateDB.setStateType(tStateType[iType]);
			tLPContStateDB.setState("1");
			tLPContStateSet = tLPContStateDB.query();
//			if (tLPContStateSet.size() < 1) {
//				CError.buildErr(this, "查询原保单状态信息失败!");
//				return false;
//			}
			LCContStateSchema tLCContStateSchema;
			for (int i = 1; i <= tLPContStateSet.size(); i++) {
				tLCContStateSchema = new LCContStateSchema();
				tRef.transFields(tLCContStateSchema, tLPContStateSet.get(i));
				tLCContStateSchema.setModifyDate(CurrDate);
				tLCContStateSchema.setModifyTime(CurrTime);
				aLCContStateSet.add(tLCContStateSchema);
			}
			LCContStateDB tLCContStateDB = new LCContStateDB();
			for (int i = 1; i <= aLCContStateSet.size(); i++) {
				// 按险种查询两条Available的记录(State为0,1)
				String StrSQL = "select * from lccontstate where polno = '?polno?' and statetype = '?statetype?' and startdate >= '?startdate?'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(StrSQL);
				sqlbv.put("polno", aLCContStateSet.get(i).getPolNo());
				sqlbv.put("statetype", tStateType[iType]);
				sqlbv.put("startdate", aLCContStateSet.get(i).getStartDate());
				tLCContStateSet.add(tLCContStateDB.executeQuery(sqlbv));
			}
//			if (tLCContStateSet.size() < 1) {
//				CError.buildErr(this, "查询保单状态信息失败!");
//				return false;
//			}
		}
		map.put(tLCContStateSet, "DELETE");
		map.put(aLCContStateSet, "DELETE&INSERT");

		LCPolSet aLCPolSet = new LCPolSet();
		LPPolSet aLPPolSet = new LPPolSet();

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
//		if (tLPPolSet.size() < 1) {
//			CError.buildErr(this, "查询险种表出错!");
//			return false;
//		}
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setModifyDate(CurrDate);
			tLCPolSchema.setModifyTime(CurrTime);
			aLCPolSet.add(tLCPolSchema);

			LCPolDB tLCPolDB = new LCPolDB();
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(CurrDate);
			tLPPolSchema.setModifyTime(CurrTime);
			aLPPolSet.add(tLPPolSchema);

			// Duty
			LCDutySet aLCDutySet = new LCDutySet();
			LPDutySet aLPDutySet = new LPDutySet();

			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPDutySet = tLPDutyDB.query();
//			if (tLPDutySet.size() < 1) {
//				CError.buildErr(this, "查询险种责任表失败!");
//				return false;
//			}
			for (int j = 1; j <= tLPDutySet.size(); j++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tRef.transFields(tLCDutySchema, tLPDutySet.get(j));
				tLCDutySchema.setModifyDate(CurrDate);
				tLCDutySchema.setModifyTime(CurrTime);
				aLCDutySet.add(tLCDutySchema);

				LPDutySchema tLPDutySchema = new LPDutySchema();
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLCDutySchema.getPolNo());
				tLCDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
				if (!tLCDutyDB.getInfo()) {
					mErrors.copyAllErrors(tLCDutyDB.mErrors);
					mErrors.addOneError(new CError("查询险种保单表失败！"));
					return false;
				}
				tRef.transFields(tLPDutySchema, tLCDutyDB.getSchema());
				tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPDutySet.add(tLPDutySchema);
			}
			map.put(aLCDutySet, "DELETE&INSERT");
			map.put(aLPDutySet, "DELETE&INSERT");

			LCPremSet aLCPremSet = new LCPremSet();
			LPPremSet aLPPremSet = new LPPremSet();

			LPPremDB tLPPremDB = new LPPremDB();
			LPPremSet tLPPremSet = new LPPremSet();
			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPPremSet = tLPPremDB.query();
			if (tLPPremSet.size() > 0) {
				for (int j = 1; j <= tLPPremSet.size(); j++) {
					LCPremSchema tLCPremSchema = new LCPremSchema();
					tRef.transFields(tLCPremSchema, tLPPremSet.get(j));
					tLCPremSchema.setModifyDate(CurrDate);
					tLCPremSchema.setModifyTime(CurrTime);
					aLCPremSet.add(tLCPremSchema);
				}

				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet tLCPremSet = new LCPremSet();
				tLCPremDB.setPolNo(tLPPolSet.get(i).getPolNo());
				tLCPremSet = tLCPremDB.query();
				for (int j = 1; j <= tLCPremSet.size(); j++) {
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tRef.transFields(tLPPremSchema, tLCPremSet.get(j));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPremSchema.setModifyDate(CurrDate);
					tLPPremSchema.setModifyTime(CurrTime);
					aLPPremSet.add(tLPPremSchema);
				}

				map.put(tLCPremSet, "DELETE");
				map.put(tLPPremSet, "DELETE");
				map.put(aLCPremSet, "DELETE&INSERT");
				map.put(aLPPremSet, "DELETE&INSERT");
			}
		}
		map.put(aLCPolSet, "DELETE&INSERT");
		map.put(aLPPolSet, "DELETE&INSERT");

		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询批改保单信息失败!");
			return false;
		}
		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
		tLCContSchema.setModifyDate(CurrDate);
		tLCContSchema.setModifyTime(CurrTime);
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(CurrDate);
		tLPContSchema.setModifyTime(CurrTime);
		map.put(tLCContSchema, "UPDATE");
		map.put(tLPContSchema, "UPDATE");
		
		//loloan update
		//loreturnloan delete
		LOReturnLoanDB tLOReturnLoanDB = new LOReturnLoanDB();
		LOReturnLoanSet tLOReturnLoanSet = new LOReturnLoanSet();
		tLOReturnLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLOReturnLoanDB.setContNo(mLPEdorItemSchema.getContNo());
		tLOReturnLoanSet = tLOReturnLoanDB.query();
		map.put(tLOReturnLoanSet, "DELETE");
		LOLoanDB tLOLoanDB = new LOLoanDB();
		LOLoanSet tLOLoanSet = new LOLoanSet();
		if (tLOReturnLoanSet.size() >= 1) {
			tLOLoanDB.setEdorNo(tLOReturnLoanSet.get(1).getLoanNo());
			tLOLoanDB.setContNo(mLPEdorItemSchema.getContNo());
			tLOLoanSet = tLOLoanDB.query();
			for (int i=1;i<=tLOLoanSet.size();i++) {
				tLOLoanSet.get(i).setLeaveMoney(tLOLoanSet.get(i).getSumMoney());
				tLOLoanSet.get(i).setPayOffDate("");
				tLOLoanSet.get(i).setPayOffFlag("0");
			}
			map.put(tLOLoanSet, "UPDATE");
		}
		
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorREBackConfirmBL pedorrebackconfirmbl = new PEdorREBackConfirmBL();
	}
}
