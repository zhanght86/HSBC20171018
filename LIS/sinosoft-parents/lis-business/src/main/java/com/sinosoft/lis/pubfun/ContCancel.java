/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCAppntGrpDB;
import com.sinosoft.lis.db.LCAppntIndDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCustomerImpartDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPayRuleFactoryDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.schema.LBAppntGrpSchema;
import com.sinosoft.lis.schema.LBAppntIndSchema;
import com.sinosoft.lis.schema.LBBnfSchema;
import com.sinosoft.lis.schema.LBContSchema;
import com.sinosoft.lis.schema.LBCustomerImpartSchema;
import com.sinosoft.lis.schema.LBDutySchema;
import com.sinosoft.lis.schema.LBGetSchema;
import com.sinosoft.lis.schema.LBGrpFeeSchema;
import com.sinosoft.lis.schema.LBGrpPolSchema;
import com.sinosoft.lis.schema.LBInsureAccSchema;
import com.sinosoft.lis.schema.LBInsureAccTraceSchema;
import com.sinosoft.lis.schema.LBInsuredSchema;
import com.sinosoft.lis.schema.LBPayRuleFactorySchema;
import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.lis.schema.LBPremSchema;
import com.sinosoft.lis.schema.LCAppntGrpSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vdb.LBAppntGrpDBSet;
import com.sinosoft.lis.vdb.LBAppntIndDBSet;
import com.sinosoft.lis.vdb.LBBnfDBSet;
import com.sinosoft.lis.vdb.LBCustomerImpartDBSet;
import com.sinosoft.lis.vdb.LBDutyDBSet;
import com.sinosoft.lis.vdb.LBGetDBSet;
import com.sinosoft.lis.vdb.LBGrpPolDBSet;
import com.sinosoft.lis.vdb.LBInsureAccDBSet;
import com.sinosoft.lis.vdb.LBInsureAccTraceDBSet;
import com.sinosoft.lis.vdb.LBInsuredDBSet;
import com.sinosoft.lis.vdb.LBPolDBSet;
import com.sinosoft.lis.vdb.LBPremDBSet;
import com.sinosoft.lis.vdb.LCAppntGrpDBSet;
import com.sinosoft.lis.vdb.LCAppntIndDBSet;
import com.sinosoft.lis.vdb.LCBnfDBSet;
import com.sinosoft.lis.vdb.LCCustomerImpartDBSet;
import com.sinosoft.lis.vdb.LCDutyDBSet;
import com.sinosoft.lis.vdb.LCGetDBSet;
import com.sinosoft.lis.vdb.LCInsureAccDBSet;
import com.sinosoft.lis.vdb.LCInsureAccTraceDBSet;
import com.sinosoft.lis.vdb.LCInsuredDBSet;
import com.sinosoft.lis.vdb.LCPolDBSet;
import com.sinosoft.lis.vdb.LCPremDBSet;
import com.sinosoft.lis.vschema.LBAppntGrpSet;
import com.sinosoft.lis.vschema.LBAppntIndSet;
import com.sinosoft.lis.vschema.LBBnfSet;
import com.sinosoft.lis.vschema.LBCustomerImpartSet;
import com.sinosoft.lis.vschema.LBDutySet;
import com.sinosoft.lis.vschema.LBGetSet;
import com.sinosoft.lis.vschema.LBGrpFeeSet;
import com.sinosoft.lis.vschema.LBGrpPolSet;
import com.sinosoft.lis.vschema.LBInsureAccSet;
import com.sinosoft.lis.vschema.LBInsureAccTraceSet;
import com.sinosoft.lis.vschema.LBInsuredSet;
import com.sinosoft.lis.vschema.LBPayRuleFactorySet;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LBPremSet;
import com.sinosoft.lis.vschema.LCAppntGrpSet;
import com.sinosoft.lis.vschema.LCAppntIndSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全确认逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj modify by Alex
 * @version 1.0
 */
public class ContCancel {
private static Logger logger = Logger.getLogger(ContCancel.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 保单号 */
	private String mContNo;
	/** 保单类型 */
	private String mContType;
	/** 相关操作的代码 */
	private String mOtherNo;
	private String mEdorNo;
	/** SQL相关变量 */

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/**
	 * constructor
	 * 
	 * @param aContNo
	 */
	public ContCancel() {
	}

	public ContCancel(String aContNo) {
		mContNo = aContNo;
	}

	/**
	 * 
	 * @param aContNo
	 * @param aOtherNo
	 */
	public ContCancel(String aContNo, String aOtherNo) {
		mContNo = aContNo;
		mOtherNo = aOtherNo;
		mContType = "I";
	}

	/**
	 * 
	 * @param aContNo
	 * @param aOtherNo
	 * @param aContType
	 */
	public ContCancel(String aContNo, String aEdorNo, String aContType) {
		mContNo = aContNo;
		mEdorNo = aEdorNo;
		mContType = aContType; // G团单，I个单
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData() {
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean dealData() {
		if (mContNo == null || mEdorNo == null || "".equals(mContNo)
				|| "".equals(mContNo)) {
			mErrors.addOneError("所需的参数未被设置");
			return false;
		}
		if (mContType.equals("G")) {
			mMap.add(prepareGrpContData(mContNo, mEdorNo));
			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setGrpContNo(mContNo);
			LPContSet tLPContSet = tLPContDB.query();
			for (int i = 0; i < tLPContSet.size(); i++) {
				mMap.add(prepareContData(tLPContSet.get(i + 1).getContNo(),
						mEdorNo));
			}
		} else if (mContType.equals("I")) {
			mMap.add(prepareContData(mContNo, mEdorNo));
		} else {
			mErrors.addOneError("不被支持的合同类型");
			return false;
		}
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	// public boolean dealData()
	// {
	// if (mContType.equals("G"))
	// {
	// LCPolDB tLCPolDB = new LCPolDB();
	// LCPolSet tLCPolSet = new LCPolSet();
	//
	// tLCPolDB.setGrpPolNo(mContNo);
	// tLCPolSet = tLCPolDB.query();
	// for (int i = 1; i <= tLCPolSet.size(); i++)
	// {
	// VData tInputData = new VData();
	// // tInputData = preparePolData(tLCPolSet.get(i).getPolNo(),mOtherNo);
	// if (!saveData(tInputData))
	// {
	// return false;
	// }
	// }
	// if (tLCPolSet.size() > 0)
	// {
	// VData tInputData = new VData();
	// tInputData = prepareGrpData(mContNo, mOtherNo);
	// if (!saveGrpData(tInputData))
	// {
	// return false;
	// }
	// }
	// }
	// else
	// {
	// VData tInputData = new VData();
	// // tInputData = preparePolData(mContNo,mOtherNo);
	// if (!saveData(tInputData))
	// {
	// return false;
	// }
	// }
	// return true;
	// }

	/**
	 * 
	 * @param aGrpPolNo
	 * @param aOtherNo
	 * @return VData
	 */
	private VData prepareGrpData(String aGrpPolNo, String aOtherNo) {
		VData aInputData = new VData();
		aInputData.clear();

		LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();
		Reflections tReflections = new Reflections();

		// 准备数据
		// 保单信息备份
		tLCGrpPolSet.clear();
		tLBGrpPolSet.clear();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(aGrpPolNo);
		tLCGrpPolSet = tLCGrpPolDB.query();
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			tLBGrpPolSchema = new LBGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			tReflections.transFields(tLBGrpPolSchema, tLCGrpPolSchema);
			tLBGrpPolSchema.setEdorNo(aOtherNo);
			tLBGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLBGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLBGrpPolSet.add(tLBGrpPolSchema);
		}

		aInputData.clear();
		aInputData.addElement(tLBGrpPolSet);
		return aInputData;
	}

	/**
	 * 准备需要保存的数据 (备份团体险种信息)
	 * 
	 * @param aPolNo
	 * @param aEdorNo
	 * @return VData
	 */
	public MMap prepareGrpPolData(String aGrpPolNo, String aEdorNo) {
		Reflections tReflections = new Reflections();
		MMap map = new MMap();

		// 集体险种表
		LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		LCGrpPolSet aLCGrpPolSet = new LCGrpPolSet();
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(aGrpPolNo);
		tLCGrpPolSet = tLCGrpPolDB.query();
		for (int j = 1; j <= tLCGrpPolSet.size(); j++) {
			tLCGrpPolSchema = tLCGrpPolSet.get(j);
			tLBGrpPolSchema = new LBGrpPolSchema();
			tReflections.transFields(tLBGrpPolSchema, tLCGrpPolSchema);
			tLBGrpPolSchema.setEdorNo(aEdorNo);
			tLBGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLBGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
			aLCGrpPolSet.add(tLCGrpPolSchema);
			aLBGrpPolSet.add(tLBGrpPolSchema);
		}
		map.put(aLCGrpPolSet, "DELETE");
		map.put(aLBGrpPolSet, "INSERT");

		// 集体险种管理费描述表
		LBGrpFeeSchema tLBGrpFeeSchema = new LBGrpFeeSchema();
		LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
		LCGrpFeeSet aLCGrpFeeSet = new LCGrpFeeSet();
		LBGrpFeeSet aLBGrpFeeSet = new LBGrpFeeSet();
		LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		tLCGrpFeeDB.setGrpPolNo(aGrpPolNo);
		tLCGrpFeeSet = tLCGrpFeeDB.query();
		for (int j = 1; j <= tLCGrpFeeSet.size(); j++) {
			tLCGrpFeeSchema = tLCGrpFeeSet.get(j);
			tLBGrpFeeSchema = new LBGrpFeeSchema();
			tReflections.transFields(tLBGrpFeeSchema, tLCGrpFeeSchema);
			tLBGrpFeeSchema.setEdorNo(aEdorNo);
			tLBGrpFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLBGrpFeeSchema.setModifyTime(PubFun.getCurrentTime());
			aLCGrpFeeSet.add(tLCGrpFeeSchema);
			aLBGrpFeeSet.add(tLBGrpFeeSchema);
		}
		map.put(aLCGrpFeeSet, "DELETE");
		map.put(aLBGrpFeeSet, "INSERT");
		// 缴费规则定义
		LBPayRuleFactorySchema tLBPayRuleFactorySchema = new LBPayRuleFactorySchema();
		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		LCPayRuleFactorySet aLCPayRuleFactorySet = new LCPayRuleFactorySet();
		LBPayRuleFactorySet aLBPayRuleFactorySet = new LBPayRuleFactorySet();
		LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
		LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
		tLCPayRuleFactoryDB.setGrpPolNo(aGrpPolNo);
		tLCPayRuleFactorySet = tLCPayRuleFactoryDB.query();
		for (int j = 1; j <= tLCPayRuleFactorySet.size(); j++) {
			tLCPayRuleFactorySchema = tLCPayRuleFactorySet.get(j);
			tLBPayRuleFactorySchema = new LBPayRuleFactorySchema();
			tReflections.transFields(tLBPayRuleFactorySchema,
					tLCPayRuleFactorySchema);
			tLBPayRuleFactorySchema.setEdorNo(aEdorNo);
			tLBPayRuleFactorySchema.setModifyDate(PubFun.getCurrentDate());
			tLBPayRuleFactorySchema.setModifyTime(PubFun.getCurrentTime());
			aLCPayRuleFactorySet.add(tLCPayRuleFactorySchema);
			aLBPayRuleFactorySet.add(tLBPayRuleFactorySchema);
		}
		map.put(aLCPayRuleFactorySet, "DELETE");
		map.put(aLBPayRuleFactorySet, "INSERT");

		return map;
	}

	/**
	 * 准备需要保存的数据 (备份团体合同信息)
	 * 
	 * @param aGrpContNo
	 * @param aEdorNo
	 * @return VData
	 */
	public MMap prepareGrpContData(String aGrpContNo, String aEdorNo) {
		String sqlStr = "";
		MMap map = new MMap();
		// 数组中的c表和b表必须一一对应
		String[] lcTable = { "LCGrpCont", "LCGrpAppnt", "LCGeneral",
				"LCGeneralToRisk", "LCContPlan", "LCContPlanRisk",
				"LCContPlanFactory", "LCContPlanParam", "LCGrpPol", "LCGrpFee",
				"LCPayRuleFactory" };
		String[] lbTable = { "LBGrpCont", "LBGrpAppnt", "LBGeneral",
				"LBGeneralToRisk", "LBContPlan", "LBContPlanRisk",
				"LBContPlanFactory", "LBContPlanParam", "LBGrpPol", "LBGrpFee",
				"LBPayRuleFactory" };
		for (int i = 0; i < lbTable.length; i++) {
			sqlStr = "insert into " + lbTable[i] + " (select '" + "?v2?"
					+ "'," + "?v3?" + ".* from " + lcTable[i]
					+ " where GrpContNo='" + "?v5?" + "')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sqlStr);
			
			sqlbv.put("v2", aEdorNo);
			sqlbv.put("v3", lcTable[i]);
			
			sqlbv.put("v5", aGrpContNo);
			
			map.put(sqlbv, "INSERT");
		}
		for (int i = 0; i < lcTable.length; i++) {
			sqlStr = "delete from " + lcTable[i] + " where GrpContNo='"
					+ "?v7?" + "'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sqlStr);
			
			sqlbv1.put("v7", aGrpContNo);
			map.put(sqlbv1, "DELETE");
		}

		// Reflections tReflections = new Reflections();
		// LBGrpContSet aLBGrpContSet = new LBGrpContSet();
		// LCGrpContSet aLCGrpContSet = new LCGrpContSet();
		// LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		// tLCGrpContDB.setGrpContNo(aGrpContNo);
		// tLCGrpContDB.getInfo();
		// if (tLCGrpContDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "查询团体保单" + aGrpContNo + "失败！");
		// return null;
		// }
		//
		// LBGrpContSchema tLBGrpContSchema = new LBGrpContSchema();
		//
		// tReflections.transFields(tLBGrpContSchema, tLCGrpContDB.getSchema());
		// tLBGrpContSchema.setEdorNo(aEdorNo);
		// tLBGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGrpContSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// aLCGrpContSet.add(tLCGrpContDB.getSchema());
		// aLBGrpContSet.add(tLBGrpContSchema);
		//
		// map.put(aLCGrpContSet, "DELETE");
		// map.put(aLBGrpContSet, "INSERT");
		//
		// //集体投保人表
		// LBGrpAppntSchema tLBGrpAppntSchema = new LBGrpAppntSchema();
		// LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
		// LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();
		// LBGrpAppntSet aLBGrpAppntSet = new LBGrpAppntSet();
		// LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
		// LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		// tLCGrpAppntDB.setGrpContNo(aGrpContNo);
		// tLCGrpAppntSet = tLCGrpAppntDB.query();
		// for (int j = 1; j <= tLCGrpAppntSet.size(); j++)
		// {
		// tLCGrpAppntSchema = tLCGrpAppntSet.get(j);
		// tLBGrpAppntSchema = new LBGrpAppntSchema();
		// tReflections.transFields(tLBGrpAppntSchema, tLCGrpAppntSchema);
		// tLBGrpAppntSchema.setEdorNo(aEdorNo);
		// tLBGrpAppntSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGrpAppntSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCGrpAppntSet.add(tLCGrpAppntSchema);
		// aLBGrpAppntSet.add(tLBGrpAppntSchema);
		// }
		// map.put(aLCGrpAppntSet, "DELETE");
		// map.put(aLBGrpAppntSet, "INSERT");
		//
		// //总括保单处理分单表
		// LBGeneralSchema tLBGeneralSchema = new LBGeneralSchema();
		// LCGeneralSchema tLCGeneralSchema = new LCGeneralSchema();
		// LCGeneralSet aLCGeneralSet = new LCGeneralSet();
		// LBGeneralSet aLBGeneralSet = new LBGeneralSet();
		// LCGeneralSet tLCGeneralSet = new LCGeneralSet();
		// LCGeneralDB tLCGeneralDB = new LCGeneralDB();
		// tLCGeneralDB.setGrpContNo(aGrpContNo);
		// tLCGeneralSet = tLCGeneralDB.query();
		// for (int j = 1; j <= tLCGeneralSet.size(); j++)
		// {
		// tLCGeneralSchema = tLCGeneralSet.get(j);
		// tLBGeneralSchema = new LBGeneralSchema();
		// tReflections.transFields(tLBGeneralSchema, tLCGeneralSchema);
		// tLBGeneralSchema.setEdorNo(aEdorNo);
		// tLBGeneralSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGeneralSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCGeneralSet.add(tLCGeneralSchema);
		// aLBGeneralSet.add(tLBGeneralSchema);
		// }
		// map.put(aLCGeneralSet, "DELETE");
		// map.put(aLBGeneralSet, "INSERT");
		// //总括保单处理险种关联表
		// LBGeneralToRiskSchema tLBGeneralToRiskSchema = new
		// LBGeneralToRiskSchema();
		// LCGeneralToRiskSchema tLCGeneralToRiskSchema = new
		// LCGeneralToRiskSchema();
		// LCGeneralToRiskSet aLCGeneralToRiskSet = new LCGeneralToRiskSet();
		// LBGeneralToRiskSet aLBGeneralToRiskSet = new LBGeneralToRiskSet();
		// LCGeneralToRiskSet tLCGeneralToRiskSet = new LCGeneralToRiskSet();
		// LCGeneralToRiskDB tLCGeneralToRiskDB = new LCGeneralToRiskDB();
		// tLCGeneralToRiskDB.setGrpContNo(aGrpContNo);
		// tLCGeneralToRiskSet = tLCGeneralToRiskDB.query();
		// for (int j = 1; j <= tLCGeneralToRiskSet.size(); j++)
		// {
		// tLCGeneralToRiskSchema = tLCGeneralToRiskSet.get(j);
		// tLBGeneralToRiskSchema = new LBGeneralToRiskSchema();
		// tReflections.transFields(tLBGeneralToRiskSchema,
		// tLCGeneralToRiskSchema);
		// tLBGeneralToRiskSchema.setEdorNo(aEdorNo);
		// tLBGeneralToRiskSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGeneralToRiskSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCGeneralToRiskSet.add(tLCGeneralToRiskSchema);
		// aLBGeneralToRiskSet.add(tLBGeneralToRiskSchema);
		// }
		// map.put(aLCGeneralToRiskSet, "DELETE");
		// map.put(aLBGeneralToRiskSet, "INSERT");
		// //保单保险计划
		// LBContPlanSchema tLBContPlanSchema = new LBContPlanSchema();
		// LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
		// LCContPlanSet aLCContPlanSet = new LCContPlanSet();
		// LBContPlanSet aLBContPlanSet = new LBContPlanSet();
		// LCContPlanSet tLCContPlanSet = new LCContPlanSet();
		// LCContPlanDB tLCContPlanDB = new LCContPlanDB();
		// tLCContPlanDB.setGrpContNo(aGrpContNo);
		// tLCContPlanSet = tLCContPlanDB.query();
		// for (int j = 1; j <= tLCContPlanSet.size(); j++)
		// {
		// tLCContPlanSchema = tLCContPlanSet.get(j);
		// tLBContPlanSchema = new LBContPlanSchema();
		// tReflections.transFields(tLBContPlanSchema, tLCContPlanSchema);
		// tLBContPlanSchema.setEdorNo(aEdorNo);
		// tLBContPlanSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBContPlanSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCContPlanSet.add(tLCContPlanSchema);
		// aLBContPlanSet.add(tLBContPlanSchema);
		// }
		// map.put(aLCContPlanSet, "DELETE");
		// map.put(aLBContPlanSet, "INSERT");
		// //保险计划责任要素值
		// LBContPlanRiskSchema tLBContPlanRiskSchema = new
		// LBContPlanRiskSchema();
		// LCContPlanRiskSchema tLCContPlanRiskSchema = new
		// LCContPlanRiskSchema();
		// LCContPlanRiskSet aLCContPlanRiskSet = new LCContPlanRiskSet();
		// LBContPlanRiskSet aLBContPlanRiskSet = new LBContPlanRiskSet();
		// LCContPlanRiskSet tLCContPlanRiskSet = new LCContPlanRiskSet();
		// LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		// tLCContPlanRiskDB.setGrpContNo(aGrpContNo);
		// tLCContPlanRiskSet = tLCContPlanRiskDB.query();
		// for (int j = 1; j <= tLCContPlanRiskSet.size(); j++)
		// {
		// tLCContPlanRiskSchema = tLCContPlanRiskSet.get(j);
		// tLBContPlanRiskSchema = new LBContPlanRiskSchema();
		// tReflections.transFields(tLBContPlanRiskSchema,
		// tLCContPlanRiskSchema);
		// tLBContPlanRiskSchema.setEdorNo(aEdorNo);
		// tLBContPlanRiskSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBContPlanRiskSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCContPlanRiskSet.add(tLCContPlanRiskSchema);
		// aLBContPlanRiskSet.add(tLBContPlanRiskSchema);
		// }
		// map.put(aLCContPlanRiskSet, "DELETE");
		// map.put(aLBContPlanRiskSet, "INSERT");
		//
		// LBContPlanFactorySchema tLBContPlanFactorySchema = new
		// LBContPlanFactorySchema();
		// LCContPlanFactorySchema tLCContPlanFactorySchema = new
		// LCContPlanFactorySchema();
		// LCContPlanFactorySet aLCContPlanFactorySet = new
		// LCContPlanFactorySet();
		// LBContPlanFactorySet aLBContPlanFactorySet = new
		// LBContPlanFactorySet();
		// LCContPlanFactorySet tLCContPlanFactorySet = new
		// LCContPlanFactorySet();
		// LCContPlanFactoryDB tLCContPlanFactoryDB = new LCContPlanFactoryDB();
		// tLCContPlanFactoryDB.setGrpContNo(aGrpContNo);
		// tLCContPlanFactorySet = tLCContPlanFactoryDB.query();
		// for (int j = 1; j <= tLCContPlanFactorySet.size(); j++)
		// {
		// tLCContPlanFactorySchema = tLCContPlanFactorySet.get(j);
		// tLBContPlanFactorySchema = new LBContPlanFactorySchema();
		// tReflections.transFields(tLBContPlanFactorySchema,
		// tLCContPlanFactorySchema);
		// tLBContPlanFactorySchema.setEdorNo(aEdorNo);
		// tLBContPlanFactorySchema.setModifyDate(PubFun.getCurrentDate());
		// tLBContPlanFactorySchema.setModifyTime(PubFun.getCurrentTime());
		// aLCContPlanFactorySet.add(tLCContPlanFactorySchema);
		// aLBContPlanFactorySet.add(tLBContPlanFactorySchema);
		// }
		// map.put(aLCContPlanFactorySet, "DELETE");
		// map.put(aLBContPlanFactorySet, "INSERT");
		//
		// LBContPlanParamSchema tLBContPlanParamSchema = new
		// LBContPlanParamSchema();
		// LCContPlanParamSchema tLCContPlanParamSchema = new
		// LCContPlanParamSchema();
		// LCContPlanParamSet aLCContPlanParamSet = new LCContPlanParamSet();
		// LBContPlanParamSet aLBContPlanParamSet = new LBContPlanParamSet();
		// LCContPlanParamSet tLCContPlanParamSet = new LCContPlanParamSet();
		// LCContPlanParamDB tLCContPlanParamDB = new LCContPlanParamDB();
		// tLCContPlanParamDB.setGrpContNo(aGrpContNo);
		// tLCContPlanParamSet = tLCContPlanParamDB.query();
		// for (int j = 1; j <= tLCContPlanParamSet.size(); j++)
		// {
		// tLCContPlanParamSchema = tLCContPlanParamSet.get(j);
		// tLBContPlanParamSchema = new LBContPlanParamSchema();
		// tReflections.transFields(tLBContPlanParamSchema,
		// tLCContPlanParamSchema);
		// tLBContPlanParamSchema.setEdorNo(aEdorNo);
		// tLBContPlanParamSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBContPlanParamSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCContPlanParamSet.add(tLCContPlanParamSchema);
		// aLBContPlanParamSet.add(tLBContPlanParamSchema);
		// }
		// map.put(aLCContPlanParamSet, "DELETE");
		// map.put(aLBContPlanParamSet, "INSERT");
		// //集体险种表
		// LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
		// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		// LCGrpPolSet aLCGrpPolSet = new LCGrpPolSet();
		// LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();
		// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		// tLCGrpPolDB.setGrpContNo(aGrpContNo);
		// tLCGrpPolSet = tLCGrpPolDB.query();
		// for (int j = 1; j <= tLCGrpPolSet.size(); j++)
		// {
		// tLCGrpPolSchema = tLCGrpPolSet.get(j);
		// tLBGrpPolSchema = new LBGrpPolSchema();
		// tReflections.transFields(tLBGrpPolSchema, tLCGrpPolSchema);
		// tLBGrpPolSchema.setEdorNo(aEdorNo);
		// tLBGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCGrpPolSet.add(tLCGrpPolSchema);
		// aLBGrpPolSet.add(tLBGrpPolSchema);
		// }
		// map.put(aLCGrpPolSet, "DELETE");
		// map.put(aLBGrpPolSet, "INSERT");
		//
		// //集体险种管理费描述表
		// LBGrpFeeSchema tLBGrpFeeSchema = new LBGrpFeeSchema();
		// LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
		// LCGrpFeeSet aLCGrpFeeSet = new LCGrpFeeSet();
		// LBGrpFeeSet aLBGrpFeeSet = new LBGrpFeeSet();
		// LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		// LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		// tLCGrpFeeDB.setGrpContNo(aGrpContNo);
		// tLCGrpFeeSet = tLCGrpFeeDB.query();
		// for (int j = 1; j <= tLCGrpFeeSet.size(); j++)
		// {
		// tLCGrpFeeSchema = tLCGrpFeeSet.get(j);
		// tLBGrpFeeSchema = new LBGrpFeeSchema();
		// tReflections.transFields(tLBGrpFeeSchema, tLCGrpFeeSchema);
		// tLBGrpFeeSchema.setEdorNo(aEdorNo);
		// tLBGrpFeeSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGrpFeeSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCGrpFeeSet.add(tLCGrpFeeSchema);
		// aLBGrpFeeSet.add(tLBGrpFeeSchema);
		// }
		// map.put(aLCGrpFeeSet, "DELETE");
		// map.put(aLBGrpFeeSet, "INSERT");
		// //缴费规则定义
		// LBPayRuleFactorySchema tLBPayRuleFactorySchema = new
		// LBPayRuleFactorySchema();
		// LCPayRuleFactorySchema tLCPayRuleFactorySchema = new
		// LCPayRuleFactorySchema();
		// LCPayRuleFactorySet aLCPayRuleFactorySet = new LCPayRuleFactorySet();
		// LBPayRuleFactorySet aLBPayRuleFactorySet = new LBPayRuleFactorySet();
		// LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
		// LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
		// tLCPayRuleFactoryDB.setGrpContNo(aGrpContNo);
		// tLCPayRuleFactorySet = tLCPayRuleFactoryDB.query();
		// for (int j = 1; j <= tLCPayRuleFactorySet.size(); j++)
		// {
		// tLCPayRuleFactorySchema = tLCPayRuleFactorySet.get(j);
		// tLBPayRuleFactorySchema = new LBPayRuleFactorySchema();
		// tReflections.transFields(tLBPayRuleFactorySchema,
		// tLCPayRuleFactorySchema);
		// tLBPayRuleFactorySchema.setEdorNo(aEdorNo);
		// tLBPayRuleFactorySchema.setModifyDate(PubFun.getCurrentDate());
		// tLBPayRuleFactorySchema.setModifyTime(PubFun.getCurrentTime());
		// aLCPayRuleFactorySet.add(tLCPayRuleFactorySchema);
		// aLBPayRuleFactorySet.add(tLBPayRuleFactorySchema);
		// }
		// map.put(aLCPayRuleFactorySet, "DELETE");
		// map.put(aLBPayRuleFactorySet, "INSERT");

		return map;
	}

	/**
	 * 准备需要保存的数据 (备份合同信息)
	 * 
	 * @param aPolNo
	 * @param aEdorNo
	 * @return VData
	 */
	public MMap prepareContData(String aContNo, String aEdorNo) {
		String sqlStr = "";
		MMap map = new MMap();
		// 数组中的c表和b表必须一一对应
		String[] lcTable = { "LCCont", "LCAppnt", "LCCustomerImpart",
				"LCCustomerImpartParams" };
		String[] lbTable = { "LBCont", "LBAppnt", "LBCustomerImpart",
				"LBCustomerImpartParams" };
		for (int i = 0; i < 2; i++) {
			sqlStr = "insert into " + lbTable[i] + " (select '" + "?v22?"
					+ "'," + "?v33?" + ".* from " + lcTable[i]
					+ " where ContNo='" + "?v55?" + "')";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sqlStr);
			
			sqlbv2.put("v22", aEdorNo);
			sqlbv2.put("v33", lcTable[i]);
			
			sqlbv2.put("v55", aContNo);
			
			map.put(sqlbv2, "INSERT");
		}
		for (int i = 0; i < 2; i++) {
			sqlStr = "delete from " + lbTable[i] + " where ContNo='" + "?a2?"
					+ "'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sqlStr);
			
			sqlbv3.put("a2", aContNo);
			map.put(sqlbv3, "DELETE");
		}
		for (int i = 2; i < lbTable.length; i++) {
			sqlStr = "insert into " + lbTable[i] + " (select '" + "?a4?"
					+ "'," + "?a5?" + ".* from " + lcTable[i]
					+ " where ContNo='" + "?a7?" + "' and CustomerNoType='0')";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(sqlStr);
			
			sqlbv4.put("a4", aEdorNo);
			sqlbv4.put("a5", lcTable[i]);
		
			sqlbv4.put("a7", aContNo);
			map.put(sqlbv4, "INSERT");
		}
		for (int i = 2; i < lcTable.length; i++) {
			sqlStr = "delete from " + lbTable[i] + " where ContNo='" + "?a22?"
					+ "' and CustomerNoType='0'";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sqlStr);
			
			sqlbv5.put("a22", aContNo);
			map.put(sqlbv5, "DELETE");
		}

		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(aContNo);
		tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询个人合同" + aContNo + "被保险人失败！");
			return null;
		}
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			MMap tmap = new MMap();
			tmap = this.prepareInsuredData(aContNo, tLCInsuredSet.get(1)
					.getInsuredNo(), aEdorNo);
			if (mErrors.needDealError()) {
				CError.buildErr(this, "准备被保险人"
						+ tLCInsuredSet.get(1).getInsuredNo() + "数据失败！");
				return null;
			}
			map.add(tmap);
		}
		// Reflections tReflections = new Reflections();
		// LBContSchema tLBContSchema = new LBContSchema();
		// LCContSchema tLCContSchema = new LCContSchema();
		// LCContSet aLCContSet = new LCContSet();
		// LBContSet aLBContSet = new LBContSet();
		// LCContSet tLCContSet = new LCContSet();
		// LCContDB tLCContDB = new LCContDB();
		// tLCContDB.setContNo(aContNo);
		// tLCContSet = tLCContDB.query();
		// for (int j = 1; j <= tLCContSet.size(); j++)
		// {
		// tLCContSchema = tLCContSet.get(j);
		// tLBContSchema = new LBContSchema();
		// tReflections.transFields(tLBContSchema, tLCContSchema);
		// tLBContSchema.setEdorNo(aEdorNo);
		// tLBContSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBContSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCContSet.add(tLCContSchema);
		// aLBContSet.add(tLBContSchema);
		// }
		// map.put(aLCContSet, "DELETE");
		// map.put(aLBContSet, "INSERT");
		//
		// LBAppntSchema tLBAppntSchema = new LBAppntSchema();
		// LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		// LCAppntSet aLCAppntSet = new LCAppntSet();
		// LBAppntSet aLBAppntSet = new LBAppntSet();
		// LCAppntSet tLCAppntSet = new LCAppntSet();
		// LCAppntDB tLCAppntDB = new LCAppntDB();
		// tLCAppntDB.setContNo(aContNo);
		// tLCAppntSet = tLCAppntDB.query();
		// for (int j = 1; j <= tLCAppntSet.size(); j++)
		// {
		// tLCAppntSchema = tLCAppntSet.get(j);
		// tLBAppntSchema = new LBAppntSchema();
		// tReflections.transFields(tLBAppntSchema, tLCAppntSchema);
		// tLBAppntSchema.setEdorNo(aEdorNo);
		// tLBAppntSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBAppntSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCAppntSet.add(tLCAppntSchema);
		// aLBAppntSet.add(tLBAppntSchema);
		// }
		// map.put(aLCAppntSet, "DELETE");
		// map.put(aLBAppntSet, "INSERT");
		//
		// LBCustomerImpartSchema tLBCustomerImpartSchema = new
		// LBCustomerImpartSchema();
		// LCCustomerImpartSchema tLCCustomerImpartSchema = new
		// LCCustomerImpartSchema();
		// LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		// LBCustomerImpartSet aLBCustomerImpartSet = new LBCustomerImpartSet();
		// LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		// LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
		// tLCCustomerImpartDB.setContNo(aContNo);
		// tLCCustomerImpartDB.setCustomerNoType("0"); //投保人告知
		// tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		// for (int j = 1; j <= tLCCustomerImpartSet.size(); j++)
		// {
		// tLCCustomerImpartSchema = tLCCustomerImpartSet.get(j);
		// tLBCustomerImpartSchema = new LBCustomerImpartSchema();
		// tReflections.transFields(tLBCustomerImpartSchema,
		// tLCCustomerImpartSchema);
		// tLBCustomerImpartSchema.setEdorNo(aEdorNo);
		// tLBCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		// aLBCustomerImpartSet.add(tLBCustomerImpartSchema);
		// }
		// map.put(aLCCustomerImpartSet, "DELETE");
		// map.put(aLBCustomerImpartSet, "INSERT");
		//
		// LBCustomerImpartParamsSchema tLBCustomerImpartParamsSchema = new
		// LBCustomerImpartParamsSchema();
		// LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new
		// LCCustomerImpartParamsSchema();
		// LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new
		// LCCustomerImpartParamsSet();
		// LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new
		// LBCustomerImpartParamsSet();
		// LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new
		// LCCustomerImpartParamsSet();
		// LCCustomerImpartParamsDB tLCCustomerImpartParamsDB = new
		// LCCustomerImpartParamsDB();
		// tLCCustomerImpartParamsDB.setContNo(aContNo);
		// tLCCustomerImpartDB.setCustomerNoType("0"); //投保人告知
		// tLCCustomerImpartParamsSet = tLCCustomerImpartParamsDB.query();
		// for (int j = 1; j <= tLCCustomerImpartParamsSet.size(); j++)
		// {
		// tLCCustomerImpartParamsSchema = tLCCustomerImpartParamsSet.get(j);
		// tLBCustomerImpartParamsSchema = new LBCustomerImpartParamsSchema();
		// tReflections.transFields(tLBCustomerImpartParamsSchema,
		// tLCCustomerImpartParamsSchema);
		// tLBCustomerImpartParamsSchema.setEdorNo(aEdorNo);
		// tLBCustomerImpartParamsSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBCustomerImpartParamsSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
		// aLBCustomerImpartParamsSet.add(tLBCustomerImpartParamsSchema);
		//
		// }
		// map.put(aLCCustomerImpartParamsSet, "DELETE");
		// map.put(aLBCustomerImpartParamsSet, "INSERT");

		return map;
	}

	/**
	 * 准备需要保存的数据 (备份被保险人信息)
	 * 
	 * @param aInsuredNo
	 * @param aEdorNo
	 * @return MMap
	 */
	public MMap prepareInsuredData(String aContNo, String aInsuredNo,
			String aEdorNo) {
		String sqlStr = "";
		MMap map = new MMap();

		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(aContNo);
		tLCPolDB.setInsuredNo(aInsuredNo);
		tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询被保险人" + aInsuredNo + "的险种保单失败！");
			return null;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1);
			MMap tmap = new MMap();
			tmap = this.preparePolData(tLCPolSchema.getPolNo(), aEdorNo);
			if (mErrors.needDealError()) {
				CError.buildErr(this, "准备险种保单号" + tLCPolSchema.getPolNo()
						+ "数据失败！");
				return null;
			}
			map.add(tmap);
		}
		sqlStr = "insert into LBInsured (select '" + "?a16?"
				+ "',LCInsured.* from LCInsured where ContNo='" + "?a17?"
				+ "' and InsuredNo='" + "?a18?" + "')";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlStr);
		sqlbv6.put("a16", aEdorNo);
		sqlbv6.put("a17", aContNo);
		sqlbv6.put("a18", aInsuredNo);
		map.put(sqlbv6, "INSERT");
		sqlStr = "delete from LCInsured where ContNo='" + "?w1?"
				+ "' and InsuredNo='" + "?w2?" + "'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sqlStr);
		sqlbv7.put("w1", aContNo);
		sqlbv7.put("w2", aInsuredNo);
		map.put(sqlbv7, "DELETE");

		sqlStr = "insert into LBCustomerImpart (select '" + "?w3?"
				+ "',LCCustomerImpart.* from LCCustomerImpart where ContNo='"
				+ "?w4?" + "' and CustomerNo='" + "?w5?"
				+ "' and CustomerNoType='I')";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(sqlStr);
		sqlbv8.put("w3", aEdorNo);
		sqlbv8.put("w4", aContNo);
		sqlbv8.put("w5", aInsuredNo);
		map.put(sqlbv8, "INSERT");
		sqlStr = "delete from LCCustomerImpart where ContNo='" + "?w6?"
				+ "' and CustomerNo='" + "?w7?"
				+ "' and CustomerNoType='I'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sqlStr);
		sqlbv9.put("w6", aContNo);
		sqlbv9.put("w7", aInsuredNo);
		map.put(sqlbv9, "DELETE");

		sqlStr = "insert into LBCustomerImpartParams (select '"
				+ "?w11?"
				+ "',LCCustomerImpartParams.* from LCCustomerImpartParams where ContNo='"
				+ "?w12?" + "' and CustomerNo='" + "?w13?"
				+ "' and CustomerNoType='I')";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(sqlStr);
		sqlbv10.put("w11", aEdorNo);
		sqlbv10.put("w12", aContNo);
		sqlbv10.put("w13", aInsuredNo);
		map.put(sqlbv10, "INSERT");
		sqlStr = "delete from LCCustomerImpartParams where ContNo='" + "?w14?"
				+ "' and CustomerNo='" + "?w15?"
				+ "' and CustomerNoType='I'";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(sqlStr);
		sqlbv11.put("w14", aContNo);
		sqlbv11.put("w15", aInsuredNo);
		map.put(sqlbv11, "DELETE");

		// Reflections tReflections = new Reflections();
		// LBInsuredSchema tLBInsuredSchema = new LBInsuredSchema();
		// LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		// LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		// LBInsuredSet aLBInsuredSet = new LBInsuredSet();
		// LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		// LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		// tLCInsuredDB.setContNo(aContNo);
		// tLCInsuredDB.setInsuredNo(aInsuredNo);
		// tLCInsuredSet = tLCInsuredDB.query();
		// for (int j = 1; j <= tLCInsuredSet.size(); j++)
		// {
		// tLCInsuredSchema = tLCInsuredSet.get(j);
		// tLBInsuredSchema = new LBInsuredSchema();
		// tReflections.transFields(tLBInsuredSchema, tLCInsuredSchema);
		// tLBInsuredSchema.setEdorNo(aEdorNo);
		// tLBInsuredSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsuredSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsuredSet.add(tLCInsuredSchema);
		// aLBInsuredSet.add(tLBInsuredSchema);
		// }
		// map.put(aLCInsuredSet, "DELETE");
		// map.put(aLBInsuredSet, "INSERT");
		//
		// LBCustomerImpartSchema tLBCustomerImpartSchema = new
		// LBCustomerImpartSchema();
		// LCCustomerImpartSchema tLCCustomerImpartSchema = new
		// LCCustomerImpartSchema();
		// LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		// LBCustomerImpartSet aLBCustomerImpartSet = new LBCustomerImpartSet();
		// LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		// LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
		// tLCCustomerImpartDB.setContNo(aContNo);
		// tLCCustomerImpartDB.setCustomerNo(aInsuredNo);
		// tLCCustomerImpartDB.setCustomerNoType("I"); //被保人告知
		// tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		// for (int j = 1; j <= tLCCustomerImpartSet.size(); j++)
		// {
		// tLCCustomerImpartSchema = tLCCustomerImpartSet.get(j);
		// tLBCustomerImpartSchema = new LBCustomerImpartSchema();
		// tReflections.transFields(tLBCustomerImpartSchema,
		// tLCCustomerImpartSchema);
		// tLBCustomerImpartSchema.setEdorNo(aEdorNo);
		// tLBCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		// aLBCustomerImpartSet.add(tLBCustomerImpartSchema);
		// }
		// map.put(aLCCustomerImpartSet, "DELETE");
		// map.put(aLBCustomerImpartSet, "INSERT");
		//
		// LBCustomerImpartParamsSchema tLBCustomerImpartParamsSchema = new
		// LBCustomerImpartParamsSchema();
		// LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new
		// LCCustomerImpartParamsSchema();
		// LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new
		// LCCustomerImpartParamsSet();
		// LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new
		// LBCustomerImpartParamsSet();
		// LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new
		// LCCustomerImpartParamsSet();
		// LCCustomerImpartParamsDB tLCCustomerImpartParamsDB = new
		// LCCustomerImpartParamsDB();
		// tLCCustomerImpartParamsDB.setContNo(aContNo);
		// tLCCustomerImpartParamsDB.setCustomerNoType("I"); //被保人告知
		// tLCCustomerImpartParamsDB.setCustomerNo(aInsuredNo);
		// tLCCustomerImpartParamsSet = tLCCustomerImpartParamsDB.query();
		// for (int j = 1; j <= tLCCustomerImpartParamsSet.size(); j++)
		// {
		// tLCCustomerImpartParamsSchema = tLCCustomerImpartParamsSet.get(j);
		// tLBCustomerImpartParamsSchema = new LBCustomerImpartParamsSchema();
		// tReflections.transFields(tLBCustomerImpartParamsSchema,
		// tLCCustomerImpartParamsSchema);
		// tLBCustomerImpartParamsSchema.setEdorNo(aEdorNo);
		// tLBCustomerImpartParamsSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBCustomerImpartParamsSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
		// aLBCustomerImpartParamsSet.add(tLBCustomerImpartParamsSchema);
		// }
		// map.put(aLCCustomerImpartParamsSet, "DELETE");
		// map.put(aLBCustomerImpartParamsSet, "INSERT");

		return map;
	}

	/**
	 * 准备需要保存的数据 (备份保单信息)
	 * 
	 * @param aPolNo
	 * @param aEdorNo
	 * @return VData
	 */
	public MMap preparePolData(String aPolNo, String aEdorNo) {
		// 定义变量
		String sqlStr = "";
		MMap map = new MMap();
		String[] lcTable = { "LCPol", "LCInsureAcc", "LCInsureAccTrace",
				"LCDuty", "LCPrem", "LCGet", "LCBnf", "LCInsuredRelated",
				"LCPremToAcc", "LCGetToAcc", "LCInsureAccFee",
				"LCInsureAccClassFee", "LCInsureAccClass" };
		String[] lbTable = { "LBPol", "LBInsureAcc", "LBInsureAccTrace",
				"LBDuty", "LBPrem", "LBGet", "LBBnf", "LBInsuredRelated",
				"LBPremToAcc", "LBGetToAcc", "LBInsureAccFee",
				"LBInsureAccClassFee", "LBInsureAccClass" };
		for (int i = 0; i < lbTable.length; i++) {
			sqlStr = "insert into " + lbTable[i] + " (select '" + "?f2?"
					+ "'," + "?f3?" + ".* from " + lcTable[i]
					+ " where PolNo='" + "?f5?" + "')";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(sqlStr);
		
			sqlbv12.put("f2", aEdorNo);
			sqlbv12.put("f3", lcTable[i]);
		
			sqlbv12.put("f5", aPolNo);
			
			map.put(sqlbv12, "INSERT");
		}
		for (int i = 0; i < lcTable.length; i++) {
			sqlStr = "delete from " + lbTable[i] + " where PolNo='" + "?f7?"
					+ "'";
			SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
			sqlbv13.sql(sqlStr);
		
			sqlbv13.put("f7", aPolNo);
			map.put(sqlbv13, "DELETE");
		}
		// 加入两个特殊表的SQL
		sqlStr = "insert into LBCustomerImpart (select '"
				+ "?f8?"
				+ "',LCCustomerImpart.* from LCCustomerImpart where ContNo in (select ContNO from LCPol where PolNo='"
				+ "?f9?"
				+ "') and CustomerNo in (select CustomerNo from LCBnf where PolNo='"
				+ "?f10?" + "') and CustomerNoType='3')";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		sqlbv14.sql(sqlStr);
		sqlbv14.put("f8", aEdorNo);
		sqlbv14.put("f9", aPolNo);
		sqlbv14.put("f10", aPolNo);
		map.put(sqlbv14, "INSERT");
		sqlStr = "delete from LCCustomerImpart where ContNo in (select ContNO from LCPol where PolNo='"
				+ "?f11?"
				+ "') and CustomerNo in (select CustomerNo from LCBnf where PolNo='"
				+ "?f12?" + "') and CustomerNoType='3'";
		SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
		sqlbv15.sql(sqlStr);
		sqlbv15.put("f11", aPolNo);
		sqlbv15.put("f12", aPolNo);
		map.put(sqlbv15, "DELETE");

		sqlStr = "insert into LBCustomerImpartParams (select '"
				+ "?f15?"
				+ "',LCCustomerImpartParams.* from LCCustomerImpartParams where ContNo in (select ContNO from LCPol where PolNo='"
				+ "?f16?"
				+ "') and CustomerNo in (select CustomerNo from LCBnf where PolNo='"
				+ "?f17?" + "') and CustomerNoType='I')";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(sqlStr);
		sqlbv16.put("f15", aEdorNo);
		sqlbv16.put("f16", aPolNo);
		sqlbv16.put("f17", aPolNo);
		map.put(sqlbv16, "INSERT");
		sqlStr = "delete from LCCustomerImpartParams where ContNo in (select ContNO from LCPol where PolNo='"
				+ "?f19?"
				+ "') and CustomerNo in (select CustomerNo from LCBnf where PolNo='"
				+ "?f20?" + "') and CustomerNoType='I'";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(sqlStr);
		sqlbv17.put("f19", aPolNo);
		sqlbv17.put("f20", aPolNo);
		map.put(sqlbv17, "DELETE");

		// VData tVData = new VData();
		// Reflections tReflections = new Reflections();
		// LBContSchema tLBContSchema = new LBContSchema();
		// LCContSchema tLCContSchema = new LCContSchema();
		//
		// LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
		// LCGrpPolSchema tLCGPolSchema = new LCGrpPolSchema();
		// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		// LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();
		//
		// LBPolSchema tLBPolSchema = new LBPolSchema();
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// LCPolSet aLCPolSet = new LCPolSet();
		// LBPolSet aLBPolSet = new LBPolSet();
		//
		// LBDutySchema tLBDutySchema = new LBDutySchema();
		// LCDutySchema tLCDutySchema = new LCDutySchema();
		// LCDutySet aLCDutySet = new LCDutySet();
		// LBDutySet aLBDutySet = new LBDutySet();
		//
		// LBPremSchema tLBPremSchema = new LBPremSchema();
		// LCPremSchema tLCPremSchema = new LCPremSchema();
		// LCPremSet aLCPremSet = new LCPremSet();
		// LBPremSet aLBPremSet = new LBPremSet();
		//
		// LBGetSchema tLBGetSchema = new LBGetSchema();
		// LCGetSchema tLCGetSchema = new LCGetSchema();
		// LCGetSet aLCGetSet = new LCGetSet();
		// LBGetSet aLBGetSet = new LBGetSet();
		//
		// LBCustomerImpartSchema tLBCustomerImpartSchema = new
		// LBCustomerImpartSchema();
		// LCCustomerImpartSchema tLCCustomerImpartSchema = new
		// LCCustomerImpartSchema();
		// LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		// LBCustomerImpartSet aLBCustomerImpartSet = new LBCustomerImpartSet();
		//
		// LBAppntGrpSchema tLBAppntGrpSchema = new LBAppntGrpSchema();
		// LCAppntGrpSchema tLCAppntGrpSchema = new LCAppntGrpSchema();
		// LCAppntGrpSet aLCAppntGrpSet = new LCAppntGrpSet();
		// LBAppntGrpSet aLBAppntGrpSet = new LBAppntGrpSet();
		//
		// LBAppntIndSchema tLBAppntIndSchema = new LBAppntIndSchema();
		// LCAppntIndSchema tLCAppntIndSchema = new LCAppntIndSchema();
		// LCAppntIndSet aLCAppntIndSet = new LCAppntIndSet();
		// LBAppntIndSet aLBAppntIndSet = new LBAppntIndSet();
		//
		// LBInsuredSchema tLBInsuredSchema = new LBInsuredSchema();
		// LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		// LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		// LBInsuredSet aLBInsuredSet = new LBInsuredSet();
		//
		// LBBnfSchema tLBBnfSchema = new LBBnfSchema();
		// LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		// LCBnfSet aLCBnfSet = new LCBnfSet();
		// LBBnfSet aLBBnfSet = new LBBnfSet();
		//
		// LBCustomerImpartSchema tLBCustomerImpartSchema = new
		// LBCustomerImpartSchema();
		// LCCustomerImpartSchema tLCCustomerImpartSchema = new
		// LCCustomerImpartSchema();
		// LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		// LBCustomerImpartSet aLBCustomerImpartSet = new LBCustomerImpartSet();
		//
		// LBCustomerImpartParamsSchema tLBCustomerImpartParamsSchema = new
		// LBCustomerImpartParamsSchema();
		// LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new
		// LCCustomerImpartParamsSchema();
		// LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new
		// LCCustomerImpartParamsSet();
		// LBCustomerImpartParamsSet aLBCustomerImpartParamsSet = new
		// LBCustomerImpartParamsSet();
		//
		// LBInsuredRelatedSchema tLBInsuredRelatedSchema = new
		// LBInsuredRelatedSchema();
		// LCInsuredRelatedSchema tLCInsuredRelatedSchema = new
		// LCInsuredRelatedSchema();
		// LCInsuredRelatedSet aLCInsuredRelatedSet = new LCInsuredRelatedSet();
		// LBInsuredRelatedSet aLBInsuredRelatedSet = new LBInsuredRelatedSet();
		//
		// LBPremToAccSchema tLBPremToAccSchema = new LBPremToAccSchema();
		// LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
		// LCPremToAccSet aLCPremToAccSet = new LCPremToAccSet();
		// LBPremToAccSet aLBPremToAccSet = new LBPremToAccSet();
		//
		// LBGetToAccSchema tLBGetToAccSchema = new LBGetToAccSchema();
		// LCGetToAccSchema tLCGetToAccSchema = new LCGetToAccSchema();
		// LCGetToAccSet aLCGetToAccSet = new LCGetToAccSet();
		// LBGetToAccSet aLBGetToAccSet = new LBGetToAccSet();
		//
		// LBInsureAccFeeSchema tLBInsureAccFeeSchema = new
		// LBInsureAccFeeSchema();
		// LCInsureAccFeeSchema tLCInsureAccFeeSchema = new
		// LCInsureAccFeeSchema();
		// LCInsureAccFeeSet aLCInsureAccFeeSet = new LCInsureAccFeeSet();
		// LBInsureAccFeeSet aLBInsureAccFeeSet = new LBInsureAccFeeSet();
		//
		// LBInsureAccClassFeeSchema tLBInsureAccClassFeeSchema = new
		// LBInsureAccClassFeeSchema();
		// LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new
		// LCInsureAccClassFeeSchema();
		// LCInsureAccClassFeeSet aLCInsureAccClassFeeSet = new
		// LCInsureAccClassFeeSet();
		// LBInsureAccClassFeeSet aLBInsureAccClassFeeSet = new
		// LBInsureAccClassFeeSet();
		//
		// LBInsureAccClassSchema tLBInsureAccClassSchema = new
		// LBInsureAccClassSchema();
		// LCInsureAccClassSchema tLCInsureAccClassSchema = new
		// LCInsureAccClassSchema();
		// LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
		// LBInsureAccClassSet aLBInsureAccClassSet = new LBInsureAccClassSet();
		//
		// LBInsureAccSchema tLBInsureAccSchema = new LBInsureAccSchema();
		// LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		// LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
		// LBInsureAccSet aLBInsureAccSet = new LBInsureAccSet();
		//
		// LBInsureAccTraceSchema tLBInsureAccTraceSchema = new
		// LBInsureAccTraceSchema();
		// LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
		// LCInsureAccTraceSchema();
		// LCInsureAccTraceSet aLCInsureAccTraceSet = new LCInsureAccTraceSet();
		// LBInsureAccTraceSet aLBInsureAccTraceSet = new LBInsureAccTraceSet();
		//
		// tVData.clear();
		// //准备数据
		// //保单信息备份
		// LCPolDB tLCPolDB = new LCPolDB();
		// tLCPolDB.setPolNo(aPolNo);
		// aLCPolSet = tLCPolDB.query();
		// for (int i = 1; i <= aLCPolSet.size(); i++)
		// {
		// tLBPolSchema = new LBPolSchema();
		// tLCPolSchema = aLCPolSet.get(i);
		// tReflections.transFields(tLBPolSchema, tLCPolSchema);
		// tLBPolSchema.setEdorNo(aEdorNo);
		// tLBPolSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBPolSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// aLBPolSet.add(tLBPolSchema);
		//
		// //保单帐户信息
		// LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		// LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		// tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCInsureAccSet = tLCInsureAccDB.query();
		// for (int j = 1; j <= tLCInsureAccSet.size(); j++)
		// {
		// tLCInsureAccSchema = tLCInsureAccSet.get(j);
		// tLBInsureAccSchema = new LBInsureAccSchema();
		// tReflections.transFields(tLBInsureAccSchema, tLCInsureAccSchema);
		// tLBInsureAccSchema.setEdorNo(aEdorNo);
		// tLBInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsureAccSet.add(tLCInsureAccSchema);
		// aLBInsureAccSet.add(tLBInsureAccSchema);
		// }
		//
		// //保单帐户轨迹信息
		// LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		// LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		// tLCInsureAccTraceDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		// for (int j = 1; j <= tLCInsureAccTraceSet.size(); j++)
		// {
		// tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(j);
		// tLBInsureAccTraceSchema = new LBInsureAccTraceSchema();
		// tReflections.transFields(tLBInsureAccTraceSchema,
		// tLCInsureAccTraceSchema);
		// tLBInsureAccTraceSchema.setEdorNo(aEdorNo);
		// tLBInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		// aLBInsureAccTraceSet.add(tLBInsureAccTraceSchema);
		// }
		//
		// //保单责任表备份
		// LCDutySet tLCDutySet = new LCDutySet();
		// LCDutyDB tLCDutyDB = new LCDutyDB();
		// tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCDutySet = tLCDutyDB.query();
		// for (int j = 1; j <= tLCDutySet.size(); j++)
		// {
		// tLBDutySchema = new LBDutySchema();
		// tLCDutySchema = tLCDutySet.get(j);
		// tReflections.transFields(tLBDutySchema, tLCDutySchema);
		// tLBDutySchema.setEdorNo(aEdorNo);
		// tLBDutySchema.setModifyDate(PubFun.getCurrentDate());
		// tLBDutySchema.setModifyTime(PubFun.getCurrentTime());
		// aLCDutySet.add(tLCDutySchema);
		// aLBDutySet.add(tLBDutySchema);
		// }
		// //保单交费表备份
		// LCPremSet tLCPremSet = new LCPremSet();
		// LCPremDB tLCPremDB = new LCPremDB();
		// tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCPremSet = tLCPremDB.query();
		// for (int j = 1; j <= tLCPremSet.size(); j++)
		// {
		// tLBPremSchema = new LBPremSchema();
		// tLCPremSchema = tLCPremSet.get(j);
		// tReflections.transFields(tLBPremSchema, tLCPremSchema);
		// tLBPremSchema.setEdorNo(aEdorNo);
		// tLBPremSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBPremSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCPremSet.add(tLCPremSchema);
		// aLBPremSet.add(tLBPremSchema);
		// }
		//
		// //保单给付责任表备份
		// LCGetSet tLCGetSet = new LCGetSet();
		// LCGetDB tLCGetDB = new LCGetDB();
		// tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCGetSet = tLCGetDB.query();
		//
		// for (int j = 1; j <= tLCGetSet.size(); j++)
		// {
		// tLCGetSchema = tLCGetSet.get(j);
		// tLBGetSchema = new LBGetSchema();
		// tReflections.transFields(tLBGetSchema, tLCGetSchema);
		// tLBGetSchema.setEdorNo(aEdorNo);
		// tLBGetSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGetSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// aLCGetSet.add(tLCGetSchema);
		// aLBGetSet.add(tLBGetSchema);
		// }
		//
		// //保单受益人信息
		// LCBnfSet tLCBnfSet = new LCBnfSet();
		// LCBnfDB tLCBnfDB = new LCBnfDB();
		// tLCBnfDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCBnfSet = tLCBnfDB.query();
		// for (int j = 1; j <= tLCBnfSet.size(); j++)
		// {
		// tLCBnfSchema = tLCBnfSet.get(j);
		// tLBBnfSchema = new LBBnfSchema();
		// tReflections.transFields(tLBBnfSchema, tLCBnfSchema);
		// tLBBnfSchema.setEdorNo(aEdorNo);
		// tLBBnfSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBBnfSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCBnfSet.add(tLCBnfSchema);
		// aLBBnfSet.add(tLBBnfSchema);
		//
		// LCCustomerImpartSet tLCCustomerImpartSet = new
		// LCCustomerImpartSet();
		// LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
		// tLCCustomerImpartDB.setContNo(tLCPolSchema.getContNo());
		// tLCCustomerImpartDB.setCustomerNo(tLCBnfSchema.getCustomerNo());
		// tLCCustomerImpartDB.setCustomerNoType("3"); //受益人告知
		// tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		// for (int k = 1; k <= tLCCustomerImpartSet.size(); k++)
		// {
		// tLCCustomerImpartSchema = tLCCustomerImpartSet.get(k);
		// tLBCustomerImpartSchema = new LBCustomerImpartSchema();
		// tReflections.transFields(tLBCustomerImpartSchema,
		// tLCCustomerImpartSchema);
		// tLBCustomerImpartSchema.setEdorNo(aEdorNo);
		// tLBCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		// aLBCustomerImpartSet.add(tLBCustomerImpartSchema);
		// }
		//
		// LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new
		// LCCustomerImpartParamsSet();
		// LCCustomerImpartParamsDB tLCCustomerImpartParamsDB = new
		// LCCustomerImpartParamsDB();
		// tLCCustomerImpartParamsDB.setContNo(tLCPolSchema.getContNo());
		// tLCCustomerImpartParamsDB.setCustomerNoType("I"); //被保人告知
		// tLCCustomerImpartParamsDB.setCustomerNo(tLCBnfSchema.
		// getCustomerNo());
		// tLCCustomerImpartParamsSet = tLCCustomerImpartParamsDB.query();
		// for (int k = 1; k <= tLCCustomerImpartParamsSet.size(); k++)
		// {
		// tLCCustomerImpartParamsSchema = tLCCustomerImpartParamsSet.
		// get(
		// k);
		// tLBCustomerImpartParamsSchema = new
		// LBCustomerImpartParamsSchema();
		// tReflections.transFields(tLBCustomerImpartParamsSchema,
		// tLCCustomerImpartParamsSchema);
		// tLBCustomerImpartParamsSchema.setEdorNo(aEdorNo);
		// tLBCustomerImpartParamsSchema.setModifyDate(PubFun.
		// getCurrentDate());
		// tLBCustomerImpartParamsSchema.setModifyTime(PubFun.
		// getCurrentTime());
		// aLCCustomerImpartParamsSet.add(
		// tLCCustomerImpartParamsSchema);
		// aLBCustomerImpartParamsSet.add(
		// tLBCustomerImpartParamsSchema);
		// }
		//
		// }
		// //连带被保险人表
		// LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
		// LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
		// tLCInsuredRelatedDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
		// for (int j = 1; j <= tLCInsuredRelatedSet.size(); j++)
		// {
		// tLCInsuredRelatedSchema = tLCInsuredRelatedSet.get(j);
		// tLBInsuredRelatedSchema = new LBInsuredRelatedSchema();
		// tReflections.transFields(tLBInsuredRelatedSchema,
		// tLCInsuredRelatedSchema);
		// tLBInsuredRelatedSchema.setEdorNo(aEdorNo);
		// tLBInsuredRelatedSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsuredRelatedSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
		// aLBInsuredRelatedSet.add(tLBInsuredRelatedSchema);
		// }
		// //保费项帐户关联表
		// LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		// LCPremToAccDB tLCPremToAccDB = new LCPremToAccDB();
		// tLCPremToAccDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCPremToAccSet = tLCPremToAccDB.query();
		// for (int j = 1; j <= tLCPremToAccSet.size(); j++)
		// {
		// tLCPremToAccSchema = tLCPremToAccSet.get(j);
		// tLBPremToAccSchema = new LBPremToAccSchema();
		// tReflections.transFields(tLBPremToAccSchema, tLCPremToAccSchema);
		// tLBPremToAccSchema.setEdorNo(aEdorNo);
		// tLBPremToAccSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBPremToAccSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCPremToAccSet.add(tLCPremToAccSchema);
		// aLBPremToAccSet.add(tLBPremToAccSchema);
		// }
		// //给付项帐户关联表
		// LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		// LCGetToAccDB tLCGetToAccDB = new LCGetToAccDB();
		// tLCGetToAccDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCGetToAccSet = tLCGetToAccDB.query();
		// for (int j = 1; j <= tLCGetToAccSet.size(); j++)
		// {
		// tLCGetToAccSchema = tLCGetToAccSet.get(j);
		// tLBGetToAccSchema = new LBGetToAccSchema();
		// tReflections.transFields(tLBGetToAccSchema, tLCGetToAccSchema);
		// tLBGetToAccSchema.setEdorNo(aEdorNo);
		// tLBGetToAccSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBGetToAccSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCGetToAccSet.add(tLCGetToAccSchema);
		// aLBGetToAccSet.add(tLBGetToAccSchema);
		// }
		// //
		// LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		// LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		// tLCInsureAccFeeDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		// for (int j = 1; j <= tLCInsureAccFeeSet.size(); j++)
		// {
		// tLCInsureAccFeeSchema = tLCInsureAccFeeSet.get(j);
		// tLBInsureAccFeeSchema = new LBInsureAccFeeSchema();
		// tReflections.transFields(tLBInsureAccFeeSchema,
		// tLCInsureAccFeeSchema);
		// tLBInsureAccFeeSchema.setEdorNo(aEdorNo);
		// tLBInsureAccFeeSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsureAccFeeSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		// aLBInsureAccFeeSet.add(tLBInsureAccFeeSchema);
		// }
		//
		// LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new
		// LCInsureAccClassFeeSet();
		// LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new
		// LCInsureAccClassFeeDB();
		// tLCInsureAccClassFeeDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
		// for (int j = 1; j <= tLCInsureAccClassFeeSet.size(); j++)
		// {
		// tLCInsureAccClassFeeSchema = tLCInsureAccClassFeeSet.get(j);
		// tLBInsureAccClassFeeSchema = new LBInsureAccClassFeeSchema();
		// tReflections.transFields(tLBInsureAccClassFeeSchema,
		// tLCInsureAccClassFeeSchema);
		// tLBInsureAccClassFeeSchema.setEdorNo(aEdorNo);
		// tLBInsureAccClassFeeSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsureAccClassFeeSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
		// aLBInsureAccClassFeeSet.add(tLBInsureAccClassFeeSchema);
		// }
		//
		// LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		// LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		// tLCInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
		// tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		// for (int j = 1; j <= tLCInsureAccClassSet.size(); j++)
		// {
		// tLCInsureAccClassSchema = tLCInsureAccClassSet.get(j);
		// tLBInsureAccClassSchema = new LBInsureAccClassSchema();
		// tReflections.transFields(tLBInsureAccClassSchema,
		// tLCInsureAccClassSchema);
		// tLBInsureAccClassSchema.setEdorNo(aEdorNo);
		// tLBInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
		// tLBInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
		// aLCInsureAccClassSet.add(tLCInsureAccClassSchema);
		// aLBInsureAccClassSet.add(tLBInsureAccClassSchema);
		// }
		//
		// }
		// if (aLCPolSet.size() > 0)
		// {
		// map.put(aLCPolSet, "DELETE");
		// map.put(aLBPolSet, "INSERT");
		// map.put(aLCDutySet, "DELETE");
		// map.put(aLBDutySet, "INSERT");
		// map.put(aLCPremSet, "DELETE");
		// map.put(aLBPremSet, "INSERT");
		// map.put(aLCGetSet, "DELETE");
		// map.put(aLBGetSet, "INSERT");
		// map.put(aLCBnfSet, "DELETE");
		// map.put(aLBBnfSet, "INSERT");
		//
		// map.put(aLCCustomerImpartSet, "DELETE");
		// map.put(aLBCustomerImpartSet, "INSERT");
		// map.put(aLCCustomerImpartParamsSet, "DELETE");
		// map.put(aLBCustomerImpartParamsSet, "INSERT");
		//
		// map.put(aLCInsuredRelatedSet, "DELETE");
		// map.put(aLBInsuredRelatedSet, "INSERT");
		// map.put(aLCPremToAccSet, "DELETE");
		// map.put(aLBPremToAccSet, "INSERT");
		// map.put(aLCGetToAccSet, "DELETE");
		// map.put(aLBGetToAccSet, "INSERT");
		// map.put(aLCInsureAccFeeSet, "DELETE");
		// map.put(aLBInsureAccFeeSet, "INSERT");
		// map.put(aLCInsureAccClassFeeSet, "DELETE");
		// map.put(aLBInsureAccClassFeeSet, "INSERT");
		//
		// map.put(aLCInsureAccClassSet, "DELETE");
		// map.put(aLBInsureAccClassSet, "INSERT");
		//
		// map.put(aLCInsureAccSet, "DELETE");
		// map.put(aLBInsureAccSet, "INSERT");
		// map.put(aLCInsureAccTraceSet, "DELETE");
		// map.put(aLBInsureAccTraceSet, "INSERT");
		//
		// }
		return map;
	}

	/**
	 * 主附险同时撤单数据准备
	 * 
	 * @param aPolNo
	 * @param aEdorNo
	 * @return
	 */
	public VData prepareMainPolData(String aPolNo, String aEdorNo) {
		// 定义变量
		int m, count;
		VData tVData = new VData();
		Reflections tReflections = new Reflections();

		LBContSchema tLBContSchema = new LBContSchema();
		LCContSchema tLCContSchema = new LCContSchema();

		LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
		LCGrpPolSchema tLCGPolSchema = new LCGrpPolSchema();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();

		LBPolSchema tLBPolSchema = new LBPolSchema();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolSet aLCPolSet = new LCPolSet();
		LBPolSet aLBPolSet = new LBPolSet();

		LBDutySchema tLBDutySchema = new LBDutySchema();
		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCDutySet aLCDutySet = new LCDutySet();
		LBDutySet aLBDutySet = new LBDutySet();

		LBPremSchema tLBPremSchema = new LBPremSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet aLCPremSet = new LCPremSet();
		LBPremSet aLBPremSet = new LBPremSet();

		LBGetSchema tLBGetSchema = new LBGetSchema();
		LCGetSchema tLCGetSchema = new LCGetSchema();
		LCGetSet aLCGetSet = new LCGetSet();
		LBGetSet aLBGetSet = new LBGetSet();

		LBCustomerImpartSchema tLBCustomerImpartSchema = new LBCustomerImpartSchema();
		LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LBCustomerImpartSet aLBCustomerImpartSet = new LBCustomerImpartSet();

		LBAppntGrpSchema tLBAppntGrpSchema = new LBAppntGrpSchema();
		LCAppntGrpSchema tLCAppntGrpSchema = new LCAppntGrpSchema();
		LCAppntGrpSet aLCAppntGrpSet = new LCAppntGrpSet();
		LBAppntGrpSet aLBAppntGrpSet = new LBAppntGrpSet();

		LBAppntIndSchema tLBAppntIndSchema = new LBAppntIndSchema();
		LCAppntIndSchema tLCAppntIndSchema = new LCAppntIndSchema();
		LCAppntIndSet aLCAppntIndSet = new LCAppntIndSet();
		LBAppntIndSet aLBAppntIndSet = new LBAppntIndSet();

		LBInsuredSchema tLBInsuredSchema = new LBInsuredSchema();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LBInsuredSet aLBInsuredSet = new LBInsuredSet();

		LBBnfSchema tLBBnfSchema = new LBBnfSchema();
		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		LCBnfSet aLCBnfSet = new LCBnfSet();
		LBBnfSet aLBBnfSet = new LBBnfSet();

		LBInsureAccSchema tLBInsureAccSchema = new LBInsureAccSchema();
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
		LBInsureAccSet aLBInsureAccSet = new LBInsureAccSet();

		LBInsureAccTraceSchema tLBInsureAccTraceSchema = new LBInsureAccTraceSchema();
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		LCInsureAccTraceSet aLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LBInsureAccTraceSet aLBInsureAccTraceSet = new LBInsureAccTraceSet();

		m = 0;
		count = 0;
		tVData.clear();
		// 准备数据
		// 保单信息备份
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setMainPolNo(aPolNo);
		aLCPolSet = tLCPolDB.query();
		for (int i = 1; i <= aLCPolSet.size(); i++) {
			tLBPolSchema = new LBPolSchema();
			tLCPolSchema = aLCPolSet.get(i);
			tReflections.transFields(tLBPolSchema, tLCPolSchema);
			/*
			 * Lis5.3 upgrade set tLBPolSchema.setEdorNo(aEdorNo);
			 */
			tLBPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLBPolSchema.setModifyTime(PubFun.getCurrentTime());

			aLBPolSet.add(tLBPolSchema);

			// 保单帐户信息
			LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccSet = tLCInsureAccDB.query();
			for (int j = 1; j <= tLCInsureAccSet.size(); j++) {
				tLCInsureAccSchema = tLCInsureAccSet.get(j);
				tLBInsureAccSchema = new LBInsureAccSchema();

				tReflections
						.transFields(tLBInsureAccSchema, tLCInsureAccSchema);
				/*
				 * Lis5.3 upgrade set tLBInsureAccSchema.setEdorNo(aEdorNo);
				 */
				tLBInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
				tLBInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
				aLCInsureAccSet.add(tLCInsureAccSchema);
				aLBInsureAccSet.add(tLBInsureAccSchema);
			}

			// 保单帐户轨迹信息
			LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
			tLCInsureAccTraceDB.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
			for (int j = 1; j <= tLCInsureAccTraceSet.size(); j++) {
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(j);
				tLBInsureAccTraceSchema = new LBInsureAccTraceSchema();
				tReflections.transFields(tLBInsureAccTraceSchema,
						tLCInsureAccTraceSchema);
				/*
				 * Lis5.3 upgrade set
				 * tLBInsureAccTraceSchema.setEdorNo(aEdorNo);
				 */
				tLBInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
				tLBInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
				aLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				aLBInsureAccTraceSet.add(tLBInsureAccTraceSchema);
			}

			// 保单责任表备份
			LCDutySet tLCDutySet = new LCDutySet();
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
			tLCDutySet = tLCDutyDB.query();
			for (int j = 1; j <= tLCDutySet.size(); j++) {
				tLBDutySchema = new LBDutySchema();
				tLCDutySchema = tLCDutySet.get(j);
				tReflections.transFields(tLBDutySchema, tLCDutySchema);
				/*
				 * Lis5.3 upgrade set tLBDutySchema.setEdorNo(aEdorNo);
				 */
				tLBDutySchema.setModifyDate(PubFun.getCurrentDate());
				tLBDutySchema.setModifyTime(PubFun.getCurrentTime());
				aLCDutySet.add(tLCDutySchema);
				aLBDutySet.add(tLBDutySchema);
			}
			// 保单交费表备份
			LCPremSet tLCPremSet = new LCPremSet();
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
			tLCPremSet = tLCPremDB.query();
			for (int j = 1; j <= tLCPremSet.size(); j++) {
				tLBPremSchema = new LBPremSchema();
				tLCPremSchema = tLCPremSet.get(j);
				tReflections.transFields(tLBPremSchema, tLCPremSchema);
				/*
				 * Lis5.3 upgrade set tLBPremSchema.setEdorNo(aEdorNo);
				 */
				tLBPremSchema.setModifyDate(PubFun.getCurrentDate());
				tLBPremSchema.setModifyTime(PubFun.getCurrentTime());
				aLCPremSet.add(tLCPremSchema);
				aLBPremSet.add(tLBPremSchema);
			}

			// 保单给付责任表备份
			LCGetSet tLCGetSet = new LCGetSet();
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
			tLCGetSet = tLCGetDB.query();

			for (int j = 1; j <= tLCGetSet.size(); j++) {
				tLCGetSchema = tLCGetSet.get(j);
				tLBGetSchema = new LBGetSchema();
				tReflections.transFields(tLBGetSchema, tLCGetSchema);
				/*
				 * Lis5.3 upgrade set tLBGetSchema.setEdorNo(aEdorNo);
				 */
				tLBGetSchema.setModifyDate(PubFun.getCurrentDate());
				tLBGetSchema.setModifyTime(PubFun.getCurrentTime());

				aLCGetSet.add(tLCGetSchema);
				aLBGetSet.add(tLBGetSchema);
			}

			// 保单个人健康告知备份
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
			/*
			 * Lis5.3 upgrade set
			 * tLCCustomerImpartDB.setPolNo(tLCPolSchema.getPolNo());
			 */
			tLCCustomerImpartSet = tLCCustomerImpartDB.query();
			for (int j = 1; j <= tLCCustomerImpartSet.size(); j++) {
				tLCCustomerImpartSchema = tLCCustomerImpartSet.get(j);
				tLBCustomerImpartSchema = new LBCustomerImpartSchema();
				tReflections.transFields(tLBCustomerImpartSchema,
						tLCCustomerImpartSchema);
				/*
				 * Lis5.3 upgrade set
				 * tLBCustomerImpartSchema.setEdorNo(aEdorNo);
				 */
				tLBCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
				tLBCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());

				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
				aLBCustomerImpartSet.add(tLBCustomerImpartSchema);
			}

			// 保单被保人信息备份
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			/*
			 * Lis5.3 upgrade set
			 * tLCInsuredDB.setPolNo(tLCPolSchema.getPolNo());
			 */
			tLCInsuredSet = tLCInsuredDB.query();
			for (int j = 1; j <= tLCInsuredSet.size(); j++) {
				tLCInsuredSchema = tLCInsuredSet.get(j);
				tLBInsuredSchema = new LBInsuredSchema();
				tReflections.transFields(tLBInsuredSchema, tLCInsuredSchema);
				/*
				 * Lis5.3 upgrade set tLBInsuredSchema.setEdorNo(aEdorNo);
				 */
				tLBInsuredSchema.setModifyDate(PubFun.getCurrentDate());
				tLBInsuredSchema.setModifyTime(PubFun.getCurrentTime());

				aLCInsuredSet.add(tLCInsuredSchema);
				aLBInsuredSet.add(tLBInsuredSchema);
			}

			// 保单投保人信息
			LCAppntIndSet tLCAppntIndSet = new LCAppntIndSet();
			LCAppntIndDB tLCAppntIndDB = new LCAppntIndDB();
			tLCAppntIndDB.setPolNo(tLCPolSchema.getPolNo());
			tLCAppntIndSet = tLCAppntIndDB.query();
			for (int j = 1; j <= tLCAppntIndSet.size(); j++) {
				tLCAppntIndSchema = tLCAppntIndSet.get(j);
				tLBAppntIndSchema = new LBAppntIndSchema();
				tReflections.transFields(tLBAppntIndSchema, tLCAppntIndSchema);
				tLBAppntIndSchema.setEdorNo(aEdorNo);
				tLBAppntIndSchema.setModifyDate(PubFun.getCurrentDate());
				tLBAppntIndSchema.setModifyTime(PubFun.getCurrentTime());

				aLCAppntIndSet.add(tLCAppntIndSchema);
				aLBAppntIndSet.add(tLBAppntIndSchema);
			}

			// 保单集体投保人信息
			LCAppntGrpSet tLCAppntGrpSet = new LCAppntGrpSet();
			LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB();
			tLCAppntGrpDB.setPolNo(tLCPolSchema.getPolNo());
			tLCAppntGrpSet = tLCAppntGrpDB.query();
			for (int j = 1; j <= tLCAppntGrpSet.size(); j++) {
				tLCAppntGrpSchema = tLCAppntGrpSet.get(j);
				tLBAppntGrpSchema = new LBAppntGrpSchema();
				tReflections.transFields(tLBAppntGrpSchema, tLCAppntGrpSchema);
				tLBAppntGrpSchema.setEdorNo(aEdorNo);
				tLBAppntGrpSchema.setModifyDate(PubFun.getCurrentDate());
				tLBAppntGrpSchema.setModifyTime(PubFun.getCurrentTime());

				aLCAppntGrpSet.add(tLCAppntGrpSchema);
				aLBAppntGrpSet.add(tLBAppntGrpSchema);
			}

			// 保单受益人信息
			LCBnfSet tLCBnfSet = new LCBnfSet();
			LCBnfDB tLCBnfDB = new LCBnfDB();
			tLCBnfDB.setPolNo(tLCPolSchema.getPolNo());
			tLCBnfSet = tLCBnfDB.query();
			for (int j = 1; j <= tLCBnfSet.size(); j++) {
				tLCBnfSchema = tLCBnfSet.get(j);
				tLBBnfSchema = new LBBnfSchema();
				tReflections.transFields(tLBBnfSchema, tLCBnfSchema);
				/*
				 * Lis5.3 upgrade set tLBBnfSchema.setEdorNo(aEdorNo);
				 */
				tLBBnfSchema.setModifyDate(PubFun.getCurrentDate());
				tLBBnfSchema.setModifyTime(PubFun.getCurrentTime());
				aLCBnfSet.add(tLCBnfSchema);
				aLBBnfSet.add(tLBBnfSchema);
			}
		}
		if (aLCPolSet.size() > 0) {
			tVData.addElement(aLCPolSet);
			tVData.addElement(aLBPolSet);
			tVData.addElement(aLCDutySet);
			tVData.addElement(aLBDutySet);
			tVData.addElement(aLCPremSet);
			tVData.addElement(aLBPremSet);
			tVData.addElement(aLCGetSet);
			tVData.addElement(aLBGetSet);
			tVData.addElement(aLCInsuredSet);
			tVData.addElement(aLBInsuredSet);
			tVData.addElement(aLCAppntIndSet);
			tVData.addElement(aLBAppntIndSet);
			tVData.addElement(aLCBnfSet);
			tVData.addElement(aLBBnfSet);
			tVData.addElement(aLCCustomerImpartSet);
			tVData.addElement(aLBCustomerImpartSet);

			tVData.addElement(aLCInsureAccSet);
			tVData.addElement(aLBInsureAccSet);
			tVData.addElement(aLCInsureAccTraceSet);
			tVData.addElement(aLBInsureAccTraceSet);
		}
		return tVData;
	}

	/**
	 * 保存个人保单信息
	 * 
	 * @param aInputData
	 * @return boolean
	 */
	private boolean saveData(VData aInputData) {

		LBPolSet tLBPolSet = new LBPolSet();
		LBDutySet tLBDutySet = new LBDutySet();
		LBPremSet tLBPremSet = new LBPremSet();
		LBGetSet tLBGetSet = new LBGetSet();
		LBCustomerImpartSet tLBCustomerImpartSet = new LBCustomerImpartSet();
		LBAppntIndSet tLBAppntIndSet = new LBAppntIndSet();
		LBAppntGrpSet tLBAppntGrpSet = new LBAppntGrpSet();
		LBInsuredSet tLBInsuredSet = new LBInsuredSet();
		LBBnfSet tLBBnfSet = new LBBnfSet();

		LCPolDB tLCPolDB;
		LCDutyDB tLCDutyDB;
		LCPremDB tLCPremDB;
		LCGetDB tLCGetDB;
		LCCustomerImpartDB tLCCustomerImpartDB;
		LCAppntIndDB tLCAppntIndDB;
		LCInsuredDB tLCInsuredDB;
		LCBnfDB tLCBnfDB;

		LBPolDBSet tLBPolDBSet;
		LBDutyDBSet tLBDutyDBSet;
		LBPremDBSet tLBPremDBSet;
		LBGetDBSet tLBGetDBSet;
		LBCustomerImpartDBSet tLBCustomerImpartDBSet;
		LBAppntIndDBSet tLBAppntIndDBSet;
		LBInsuredDBSet tLBInsuredDBSet;
		LBBnfDBSet tLBBnfDBSet;

		tLBPolSet = (LBPolSet) aInputData.getObjectByObjectName("LBPolSet", 0);
		tLBDutySet = (LBDutySet) aInputData.getObjectByObjectName("LBDutySet",
				0);
		tLBPremSet = (LBPremSet) aInputData.getObjectByObjectName("LBPremSet",
				0);
		tLBGetSet = (LBGetSet) aInputData.getObjectByObjectName("LBGetSet", 0);
		tLBCustomerImpartSet = (LBCustomerImpartSet) aInputData
				.getObjectByObjectName("LBCustomerImpartSet", 0);
		tLBAppntIndSet = (LBAppntIndSet) aInputData.getObjectByObjectName(
				"LBAppntIndSet", 0);
		tLBAppntGrpSet = (LBAppntGrpSet) aInputData.getObjectByObjectName(
				"LBAppntGrpSet", 0);
		tLBInsuredSet = (LBInsuredSet) aInputData.getObjectByObjectName(
				"LBInsuredSet", 0);
		tLBBnfSet = (LBBnfSet) aInputData.getObjectByObjectName("LBBnfSet", 0);

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContCancel";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			// 保单信息备份
			if (tLBPolSet != null && tLBPolSet.size() > 0) {
				tLCPolDB = new LCPolDB(conn);
				tLCPolDB.setPolNo(tLBPolSet.get(1).getPolNo());
				if (!tLCPolDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保单信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBPolDBSet = new LBPolDBSet(conn);
				tLBPolDBSet.set(tLBPolSet);

				if (!tLBPolDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保单信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 责任信息备份
			if (tLBDutySet != null && tLBDutySet.size() > 0) {
				tLCDutyDB = new LCDutyDB(conn);
				tLCDutyDB.setPolNo(tLBDutySet.get(1).getPolNo());
				if (!tLCDutyDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBDutyDBSet = new LBDutyDBSet(conn);
				tLBDutyDBSet.set(tLBDutySet);

				if (!tLBDutyDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 交费信息备份
			if (tLBPremSet != null && tLBPremSet.size() > 0) {
				tLCPremDB = new LCPremDB(conn);
				tLCPremDB.setPolNo(tLBPremSet.get(1).getPolNo());
				if (!tLCPremDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBPremDBSet = new LBPremDBSet(conn);
				tLBPremDBSet.set(tLBPremSet);

				if (!tLBPremDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 给付信息备份
			if (tLBGetSet != null && tLBGetSet.size() > 0) {
				tLCGetDB = new LCGetDB(conn);
				tLCGetDB.setPolNo(tLBGetSet.get(1).getPolNo());
				if (!tLCGetDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "给付项信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBGetDBSet = new LBGetDBSet(conn);
				logger.debug("------" + tLBGetSet.size());
				tLBGetDBSet.set(tLBGetSet);
				logger.debug("------" + tLBGetDBSet.size());
				if (!tLBGetDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "给付项信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 健康告知信息备份
			if (tLBCustomerImpartSet != null && tLBCustomerImpartSet.size() > 0) {
				tLCCustomerImpartDB = new LCCustomerImpartDB(conn);
				/*
				 * Lis5.3 upgrade set
				 * tLCCustomerImpartDB.setPolNo(tLBCustomerImpartSet.get(1).getPolNo());
				 */
				if (!tLCCustomerImpartDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "健康告知信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBCustomerImpartDBSet = new LBCustomerImpartDBSet(conn);
				tLBCustomerImpartDBSet.set(tLBCustomerImpartSet);

				if (!tLBCustomerImpartDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "健康告知信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 投保人信息备份
			if (tLBAppntIndSet != null && tLBAppntIndSet.size() > 0) {
				tLCAppntIndDB = new LCAppntIndDB(conn);
				tLCAppntIndDB.setPolNo(tLBAppntIndSet.get(1).getPolNo());
				if (!tLCAppntIndDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBAppntIndDBSet = new LBAppntIndDBSet(conn);
				tLBAppntIndDBSet.set(tLBAppntIndSet);

				if (!tLBAppntIndDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 集体投保人信息备份
			if (tLBAppntGrpSet != null && tLBAppntGrpSet.size() > 0) {
				LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB(conn);
				tLCAppntGrpDB.setPolNo(tLBAppntGrpSet.get(1).getPolNo());
				if (!tLCAppntGrpDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBAppntGrpDBSet tLBAppntGrpDBSet = new LBAppntGrpDBSet(conn);
				tLBAppntGrpDBSet.set(tLBAppntGrpSet);

				if (!tLBAppntGrpDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 被保人信息备份
			if (tLBInsuredSet != null && tLBInsuredSet.size() > 0) {
				tLCInsuredDB = new LCInsuredDB(conn);
				/*
				 * Lis5.3 upgrade set
				 * tLCInsuredDB.setPolNo(tLBInsuredSet.get(1).getPolNo());
				 */
				if (!tLCInsuredDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "被保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBInsuredDBSet = new LBInsuredDBSet(conn);
				tLBInsuredDBSet.set(tLBInsuredSet);

				if (!tLBInsuredDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "被保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 受益人信息备份
			if (tLBBnfSet != null && tLBBnfSet.size() > 0) {
				tLCBnfDB = new LCBnfDB(conn);
				tLCBnfDB.setPolNo(tLBBnfSet.get(1).getPolNo());
				if (!tLCBnfDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "受益人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLBBnfDBSet = new LBBnfDBSet(conn);
				tLBBnfDBSet.set(tLBBnfSet);

				if (!tLBBnfDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "受益人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContCancel";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 集体单备份
	 * 
	 * @param aInputData
	 * @return boolean
	 */
	private boolean saveGrpData(VData aInputData) {
		LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();

		tLBGrpPolSet = (LBGrpPolSet) aInputData.getObjectByObjectName(
				"LBGrpPolSet", 0);

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorConfirmWTBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			// 保单信息备份
			if (tLBGrpPolSet != null && tLBGrpPolSet.size() > 0) {
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);
				tLCGrpPolDB.setGrpPolNo(mContNo);
				if (!tLCGrpPolDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PGrpEdorConfirmWTBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "集体保单删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBGrpPolDBSet tLBGrpPolDBSet = new LBGrpPolDBSet(conn);
				tLBGrpPolDBSet.set(tLBGrpPolSet);

				if (!tLBGrpPolDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PGrpEdorConfirmWTBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "集体保单备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * 保存个人保单信息
	 * 
	 * @param aInputData
	 * @return boolean
	 */
	private boolean saveMainPolData(VData aInputData) {

		LBPolSet tLBPolSet = new LBPolSet();
		LBDutySet tLBDutySet = new LBDutySet();
		LBPremSet tLBPremSet = new LBPremSet();
		LBGetSet tLBGetSet = new LBGetSet();
		LBCustomerImpartSet tLBCustomerImpartSet = new LBCustomerImpartSet();
		LBAppntIndSet tLBAppntIndSet = new LBAppntIndSet();
		LBInsuredSet tLBInsuredSet = new LBInsuredSet();
		LBBnfSet tLBBnfSet = new LBBnfSet();

		LCPolSet tLCPolSet = new LCPolSet();
		LCDutySet tLCDutySet = new LCDutySet();
		LCPremSet tLCPremSet = new LCPremSet();
		LCGetSet tLCGetSet = new LCGetSet();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCAppntIndSet tLCAppntIndSet = new LCAppntIndSet();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCBnfSet tLCBnfSet = new LCBnfSet();

		tLBPolSet = (LBPolSet) aInputData.getObjectByObjectName("LBPolSet", 0);
		tLBDutySet = (LBDutySet) aInputData.getObjectByObjectName("LBDutySet",
				0);
		tLBPremSet = (LBPremSet) aInputData.getObjectByObjectName("LBPremSet",
				0);
		tLBGetSet = (LBGetSet) aInputData.getObjectByObjectName("LBGetSet", 0);
		tLBCustomerImpartSet = (LBCustomerImpartSet) aInputData
				.getObjectByObjectName("LBCustomerImpartSet", 0);
		tLBAppntIndSet = (LBAppntIndSet) aInputData.getObjectByObjectName(
				"LBAppntIndSet", 0);
		tLBInsuredSet = (LBInsuredSet) aInputData.getObjectByObjectName(
				"LBInsuredSet", 0);
		tLBBnfSet = (LBBnfSet) aInputData.getObjectByObjectName("LBBnfSet", 0);

		tLCPolSet = (LCPolSet) aInputData.getObjectByObjectName("LCPolSet", 0);
		tLCDutySet = (LCDutySet) aInputData.getObjectByObjectName("LCDutySet",
				0);
		tLCPremSet = (LCPremSet) aInputData.getObjectByObjectName("LCPremSet",
				0);
		tLCGetSet = (LCGetSet) aInputData.getObjectByObjectName("LCGetSet", 0);
		tLCCustomerImpartSet = (LCCustomerImpartSet) aInputData
				.getObjectByObjectName("LCCustomerImpartSet", 0);
		tLCAppntIndSet = (LCAppntIndSet) aInputData.getObjectByObjectName(
				"LCAppntIndSet", 0);
		tLCInsuredSet = (LCInsuredSet) aInputData.getObjectByObjectName(
				"LCInsuredSet", 0);
		tLCBnfSet = (LCBnfSet) aInputData.getObjectByObjectName("LCBnfSet", 0);

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContCancel";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			// 保单信息备份
			if (tLBPolSet != null && tLBPolSet.size() > 0) {
				LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
				tLCPolDBSet.set(tLCPolSet);
				if (!tLCPolDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保单信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBPolDBSet tLBPolDBSet = new LBPolDBSet(conn);
				tLBPolDBSet.set(tLBPolSet);

				if (!tLBPolDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保单信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 责任信息备份
			if (tLBDutySet != null && tLBDutySet.size() > 0) {
				LCDutyDBSet tLCDutyDBSet = new LCDutyDBSet(conn);
				tLCDutyDBSet.set(tLCDutySet);
				if (!tLCDutyDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBDutyDBSet tLBDutyDBSet = new LBDutyDBSet(conn);
				tLBDutyDBSet.set(tLBDutySet);

				if (!tLBDutyDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 交费信息备份
			if (tLBPremSet != null && tLBPremSet.size() > 0) {
				LCPremDBSet tLCPremDBSet = new LCPremDBSet(conn);
				tLCPremDBSet.set(tLCPremSet);
				if (!tLCPremDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBPremDBSet tLBPremDBSet = new LBPremDBSet(conn);
				tLBPremDBSet.set(tLBPremSet);

				if (!tLBPremDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 给付信息备份
			if (tLBGetSet != null && tLBGetSet.size() > 0) {
				LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
				tLCGetDBSet.set(tLCGetSet);
				if (!tLCGetDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "给付项信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBGetDBSet tLBGetDBSet = new LBGetDBSet(conn);
				logger.debug("------" + tLBGetSet.size());
				tLBGetDBSet.set(tLBGetSet);
				logger.debug("------" + tLBGetDBSet.size());
				if (!tLBGetDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "给付项信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 健康告知信息备份
			if (tLBCustomerImpartSet != null && tLBCustomerImpartSet.size() > 0) {
				LCCustomerImpartDBSet tLCCustomerImpartDBSet = new LCCustomerImpartDBSet(
						conn);
				tLCCustomerImpartDBSet.set(tLCCustomerImpartSet);
				if (!tLCCustomerImpartDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "健康告知信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBCustomerImpartDBSet tLBCustomerImpartDBSet = new LBCustomerImpartDBSet(
						conn);
				tLBCustomerImpartDBSet.set(tLBCustomerImpartSet);

				if (!tLBCustomerImpartDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "健康告知信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 投保人信息备份
			if (tLBAppntIndSet != null && tLBAppntIndSet.size() > 0) {
				LCAppntIndDBSet tLCAppntIndDBSet = new LCAppntIndDBSet(conn);
				tLCAppntIndDBSet.set(tLCAppntIndSet);
				if (!tLCAppntIndDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBAppntIndDBSet tLBAppntIndDBSet = new LBAppntIndDBSet(conn);
				tLBAppntIndDBSet.set(tLBAppntIndSet);

				if (!tLBAppntIndDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 被保人信息备份
			if (tLBInsuredSet != null && tLBInsuredSet.size() > 0) {
				LCInsuredDBSet tLCInsuredDBSet = new LCInsuredDBSet(conn);
				tLCInsuredDBSet.set(tLCInsuredSet);
				if (!tLCInsuredDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "被保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBInsuredDBSet tLBInsuredDBSet = new LBInsuredDBSet(conn);
				tLBInsuredDBSet.set(tLBInsuredSet);

				if (!tLBInsuredDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "被保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 受益人信息备份
			if (tLBBnfSet != null && tLBBnfSet.size() > 0) {
				LCBnfDBSet tLCBnfDBSet = new LCBnfDBSet(conn);
				tLCBnfDBSet.set(tLCBnfSet);
				if (!tLCBnfDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBBnfDBSet tLBBnfDBSet = new LBBnfDBSet(conn);
				tLBBnfDBSet.set(tLBBnfSet);

				if (!tLBBnfDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "受益人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContCancel";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 保存个人保单信息
	 * 
	 * @param aInputData
	 * @return boolean
	 */
	public boolean saveMainPolData(VData aInputData, Connection conn) {

		LBPolSet tLBPolSet = new LBPolSet();
		LBDutySet tLBDutySet = new LBDutySet();
		LBPremSet tLBPremSet = new LBPremSet();
		LBGetSet tLBGetSet = new LBGetSet();
		LBCustomerImpartSet tLBCustomerImpartSet = new LBCustomerImpartSet();
		LBAppntIndSet tLBAppntIndSet = new LBAppntIndSet();
		LBAppntGrpSet tLBAppntGrpSet = new LBAppntGrpSet();
		LBInsuredSet tLBInsuredSet = new LBInsuredSet();
		LBBnfSet tLBBnfSet = new LBBnfSet();
		LBInsureAccSet tLBInsureAccSet = new LBInsureAccSet();
		LBInsureAccTraceSet tLBInsureAccTraceSet = new LBInsureAccTraceSet();

		LCPolSet tLCPolSet = new LCPolSet();
		LCDutySet tLCDutySet = new LCDutySet();
		LCPremSet tLCPremSet = new LCPremSet();
		LCGetSet tLCGetSet = new LCGetSet();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCAppntIndSet tLCAppntIndSet = new LCAppntIndSet();
		LCAppntGrpSet tLCAppntGrpSet = new LCAppntGrpSet();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		tLBPolSet = (LBPolSet) aInputData.getObjectByObjectName("LBPolSet", 0);
		tLBDutySet = (LBDutySet) aInputData.getObjectByObjectName("LBDutySet",
				0);
		tLBPremSet = (LBPremSet) aInputData.getObjectByObjectName("LBPremSet",
				0);
		tLBGetSet = (LBGetSet) aInputData.getObjectByObjectName("LBGetSet", 0);
		tLBCustomerImpartSet = (LBCustomerImpartSet) aInputData
				.getObjectByObjectName("LBCustomerImpartSet", 0);
		tLBAppntIndSet = (LBAppntIndSet) aInputData.getObjectByObjectName(
				"LBAppntIndSet", 0);
		tLBAppntGrpSet = (LBAppntGrpSet) aInputData.getObjectByObjectName(
				"LBAppntGrpSet", 0);
		tLBInsuredSet = (LBInsuredSet) aInputData.getObjectByObjectName(
				"LBInsuredSet", 0);
		tLBBnfSet = (LBBnfSet) aInputData.getObjectByObjectName("LBBnfSet", 0);
		tLBInsureAccSet = (LBInsureAccSet) aInputData.getObjectByObjectName(
				"LBInsureAccSet", 0);
		tLBInsureAccTraceSet = (LBInsureAccTraceSet) aInputData
				.getObjectByObjectName("LBInsureAccTraceSet", 0);

		tLCPolSet = (LCPolSet) aInputData.getObjectByObjectName("LCPolSet", 0);
		tLCDutySet = (LCDutySet) aInputData.getObjectByObjectName("LCDutySet",
				0);
		tLCPremSet = (LCPremSet) aInputData.getObjectByObjectName("LCPremSet",
				0);
		tLCGetSet = (LCGetSet) aInputData.getObjectByObjectName("LCGetSet", 0);
		tLCCustomerImpartSet = (LCCustomerImpartSet) aInputData
				.getObjectByObjectName("LCCustomerImpartSet", 0);
		tLCAppntIndSet = (LCAppntIndSet) aInputData.getObjectByObjectName(
				"LCAppntIndSet", 0);
		tLCAppntGrpSet = (LCAppntGrpSet) aInputData.getObjectByObjectName(
				"LCAppntGrpSet", 0);
		tLCInsuredSet = (LCInsuredSet) aInputData.getObjectByObjectName(
				"LCInsuredSet", 0);
		tLCBnfSet = (LCBnfSet) aInputData.getObjectByObjectName("LCBnfSet", 0);
		tLCInsureAccSet = (LCInsureAccSet) aInputData.getObjectByObjectName(
				"LCInsureAccSet", 0);
		tLCInsureAccTraceSet = (LCInsureAccTraceSet) aInputData
				.getObjectByObjectName("LCInsureAccTraceSet", 0);

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContCancel";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			// 保单信息备份
			if (tLBPolSet != null && tLBPolSet.size() > 0) {
				LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
				tLCPolDBSet.set(tLCPolSet);
				if (!tLCPolDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保单信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBPolDBSet tLBPolDBSet = new LBPolDBSet(conn);
				tLBPolDBSet.set(tLBPolSet);
				if (!tLBPolDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保单信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 责任信息备份
			if (tLBDutySet != null && tLBDutySet.size() > 0) {
				LCDutyDBSet tLCDutyDBSet = new LCDutyDBSet(conn);
				tLCDutyDBSet.set(tLCDutySet);
				if (!tLCDutyDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBDutyDBSet tLBDutyDBSet = new LBDutyDBSet(conn);
				tLBDutyDBSet.set(tLBDutySet);

				if (!tLBDutyDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 交费信息备份
			if (tLBPremSet != null && tLBPremSet.size() > 0) {
				LCPremDBSet tLCPremDBSet = new LCPremDBSet(conn);
				tLCPremDBSet.set(tLCPremSet);
				if (!tLCPremDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBPremDBSet tLBPremDBSet = new LBPremDBSet(conn);
				tLBPremDBSet.set(tLBPremSet);

				if (!tLBPremDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 给付信息备份
			if (tLBGetSet != null && tLBGetSet.size() > 0) {
				LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
				tLCGetDBSet.set(tLCGetSet);
				if (!tLCGetDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "给付项信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBGetDBSet tLBGetDBSet = new LBGetDBSet(conn);
				logger.debug("------" + tLBGetSet.size());
				tLBGetDBSet.set(tLBGetSet);
				logger.debug("------" + tLBGetDBSet.size());
				if (!tLBGetDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "给付项信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 健康告知信息备份
			if (tLBCustomerImpartSet != null && tLBCustomerImpartSet.size() > 0) {
				LCCustomerImpartDBSet tLCCustomerImpartDBSet = new LCCustomerImpartDBSet(
						conn);
				tLCCustomerImpartDBSet.set(tLCCustomerImpartSet);
				if (!tLCCustomerImpartDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "健康告知信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBCustomerImpartDBSet tLBCustomerImpartDBSet = new LBCustomerImpartDBSet(
						conn);
				tLBCustomerImpartDBSet.set(tLBCustomerImpartSet);

				if (!tLBCustomerImpartDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "健康告知信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 投保人信息备份
			if (tLBAppntIndSet != null && tLBAppntIndSet.size() > 0) {
				LCAppntIndDBSet tLCAppntIndDBSet = new LCAppntIndDBSet(conn);
				tLCAppntIndDBSet.set(tLCAppntIndSet);
				if (!tLCAppntIndDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBAppntIndDBSet tLBAppntIndDBSet = new LBAppntIndDBSet(conn);
				tLBAppntIndDBSet.set(tLBAppntIndSet);

				if (!tLBAppntIndDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "投保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			// 集体投保人信息备份
			if (tLBAppntGrpSet != null && tLBAppntGrpSet.size() > 0) {
				LCAppntGrpDBSet tLCAppntGrpDBSet = new LCAppntGrpDBSet(conn);
				tLCAppntGrpDBSet.set(tLCAppntGrpSet);
				if (!tLCAppntGrpDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "集体投保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBAppntGrpDBSet tLBAppntGrpDBSet = new LBAppntGrpDBSet(conn);
				tLBAppntGrpDBSet.set(tLBAppntGrpSet);

				if (!tLBAppntGrpDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "集体投保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 被保人信息备份
			if (tLBInsuredSet != null && tLBInsuredSet.size() > 0) {
				LCInsuredDBSet tLCInsuredDBSet = new LCInsuredDBSet(conn);
				tLCInsuredDBSet.set(tLCInsuredSet);
				if (!tLCInsuredDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "被保人信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBInsuredDBSet tLBInsuredDBSet = new LBInsuredDBSet(conn);
				tLBInsuredDBSet.set(tLBInsuredSet);

				if (!tLBInsuredDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "被保人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 受益人信息备份
			if (tLBBnfSet != null && tLBBnfSet.size() > 0) {
				LCBnfDBSet tLCBnfDBSet = new LCBnfDBSet(conn);
				tLCBnfDBSet.set(tLCBnfSet);
				if (!tLCBnfDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBBnfDBSet tLBBnfDBSet = new LBBnfDBSet(conn);
				tLBBnfDBSet.set(tLBBnfSet);

				if (!tLBBnfDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "受益人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 帐户信息备份
			if (tLBInsureAccSet != null && tLBInsureAccSet.size() > 0) {
				LCInsureAccDBSet tLCInsureAccDBSet = new LCInsureAccDBSet(conn);
				tLCInsureAccDBSet.set(tLCInsureAccSet);
				if (!tLCInsureAccDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LBInsureAccDBSet tLBInsureAccDBSet = new LBInsureAccDBSet(conn);
				tLBInsureAccDBSet.set(tLBInsureAccSet);
				if (!tLBInsureAccDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "受益人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 帐户轨迹信息备份
			if (tLBInsureAccTraceSet != null && tLBInsureAccTraceSet.size() > 0) {
				LCInsureAccTraceDBSet tLCInsureAccTraceDBSet = new LCInsureAccTraceDBSet(
						conn);
				tLCInsureAccTraceDBSet.set(tLCInsureAccTraceSet);
				if (!tLCInsureAccTraceDBSet.delete()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "责任信息删除失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				LBInsureAccTraceDBSet tLBInsureAccTraceDBSet = new LBInsureAccTraceDBSet(
						conn);
				tLBInsureAccTraceDBSet.set(tLBInsureAccTraceSet);

				if (!tLBInsureAccTraceDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ContCancel";
					tError.functionName = "saveData";
					tError.errorMessage = "受益人信息备份失败!";
					this.mErrors.addOneError(tError);
					logger.debug(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContCancel";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// String aContNo, aOtherNo;
		// VData vData = new VData();
		// aContNo = "240110000000166";
		// aOtherNo = "410110000000161";
		// ContCancel aContCancel = new ContCancel(aContNo, aOtherNo, "G");
		// if (aContCancel.submitData())
		// {
		// vData = aContCancel.getResult();
		// PubSubmit ps = new PubSubmit();
		// ps.submitData(vData, "");
		// }
	}

}
