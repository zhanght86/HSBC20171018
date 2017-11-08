package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-09-02, 2006-10-10,, 2006-10-18, 2007-05-15
 * @direction: 保全受益人变更确认
 ******************************************************************************/


import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorBCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorBCConfirmBL.class);
	// public PEdorBCConfirmBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	//public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPEdorItemSchema rLPEdorItemSchema;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorBCConfirmBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorBCConfirmBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorBCConfirmBL.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> PEdorBCConfirmBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorBCConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPEdorItemSchema
		rLPEdorItemSchema = new LPEdorItemSchema();
		rLPEdorItemSchema = (LPEdorItemSchema) rInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		if (rLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> PEdorBCConfirmBL.getInputData() : 无法获取 LPEdorItemSchema 数据！");
			return false;
		}

	    mEdorNo = rLPEdorItemSchema.getEdorNo();
	    mEdorType = rLPEdorItemSchema.getEdorType();
	    mContNo = rLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(rGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
		// logger.debug("\t@> PEdorBCConfirmBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> PEdorBCConfirmBL.checkData() 开始");

		// 查询 LPEdorItem
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(rLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(rLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setPolNo(rLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全项目明细表发生异常！");
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			rLPEdorItemSchema = tLPEdorItemSet.get(1);
		}
		tLPEdorItemDB = null;
		tLPEdorItemSet = null;

		// logger.debug("\t@> PEdorBCConfirmBL.checkData() 结束");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorBCConfirmBL.dealData() 开始");

		rMap = new MMap();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 更新 LPEdorItem
		rLPEdorItemSchema.setEdorState("6");
		rLPEdorItemSchema.setModifyDate(sCurrentDate);
		rLPEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPEdorItemSchema, "UPDATE");

	  	//采用新的方式进行 CP 互换
	    //String[] chgTables = {"LCAppnt","LCInsured"};
		String[] chgTables = {"LCCont","LCPol","LCDuty","LCGet","LCAppnt","LCInsured"};
		
	    mValidateEdorData.changeData(chgTables);
	    rMap.add(mValidateEdorData.getMap());
	    //处理特约
	    rMap.add(BqNameFun.DealSpecData(rLPEdorItemSchema));
		// ----------------------------------------------------------------------

		// 删除 LCBnf
		DeleteSQL = "delete from LCBnf " + "where 1 = 1 " + "and ContNo = '"
				+ "?ContNo?" + "' " + "and PolNo = '"
				+ "?PolNo?" + "'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DeleteSQL);
		sqlbv.put("ContNo", rLPEdorItemSchema.getContNo());
		sqlbv.put("PolNo", rLPEdorItemSchema.getPolNo());
		rMap.put(sqlbv, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LPBnf
		LPBnfDB tLPBnfDB = new LPBnfDB();
		tLPBnfDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPBnfDB.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPBnfDB.setContNo(rLPEdorItemSchema.getContNo());
		tLPBnfDB.setPolNo(rLPEdorItemSchema.getPolNo());
		LPBnfSet tLPBnfSet = new LPBnfSet();
		try {
			tLPBnfSet = tLPBnfDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全受益人表发生异常！");
			return false;
		}
		if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
			// 拷贝 LPBnf 到 LCBnf
			LCBnfSet tLCBnfSetNew = new LCBnfSet();
			for (int i = 1; i <= tLPBnfSet.size(); i++) {
				LPBnfSchema tLPBnfSchema = new LPBnfSchema();
				tLPBnfSchema = tLPBnfSet.get(i);
				LCBnfSchema tLCBnfSchemaNew = new LCBnfSchema();
				PubFun.copySchema(tLCBnfSchemaNew, tLPBnfSchema);
				tLCBnfSchemaNew.setOperator(rGlobalInput.Operator);
				tLCBnfSchemaNew.setModifyDate(sCurrentDate);
				tLCBnfSchemaNew.setModifyTime(sCurrentTime);
				tLCBnfSetNew.add(tLCBnfSchemaNew);
				tLCBnfSchemaNew = null;
				tLPBnfSchema = null;
			}
			rMap.put(tLCBnfSetNew, "INSERT");
			tLCBnfSetNew = null;
		}
		tLPBnfDB = null;
		tLPBnfSet = null;

		// ----------------------------------------------------------------------

		// 删除 LPBnf
		DeleteSQL = "delete from LPBnf " + "where 1 = 1 " + "and EdorNo = '"
				+ "?EdorNo?" + "' " + "and EdorType = '"
				+ "?EdorType?" + "' " + "and ContNo = '"
				+ "?ContNo?" + "' " + "and PolNo = '"
				+ "?PolNo?" + "'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(DeleteSQL);
		sbv.put("EdorNo", rLPEdorItemSchema.getEdorNo());
		sbv.put("EdorType", rLPEdorItemSchema.getEdorType());
		sbv.put("ContNo", rLPEdorItemSchema.getContNo());
		sbv.put("PolNo", rLPEdorItemSchema.getPolNo());
		rMap.put(sbv, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCBnf
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(rLPEdorItemSchema.getContNo());
		tLCBnfDB.setPolNo(rLPEdorItemSchema.getPolNo());
		LCBnfSet tLCBnfSet = new LCBnfSet();
		try {
			tLCBnfSet = tLCBnfDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询契约受益人表发生异常！");
			return false;
		}
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			// 拷贝 LCBnf 到 LPBnf
			LPBnfSet tLPBnfSetNew = new LPBnfSet();
			for (int i = 1; i <= tLCBnfSet.size(); i++) {
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				LPBnfSchema tLPBnfSchemaNew = new LPBnfSchema();
				tLCBnfSchema = tLCBnfSet.get(i);
				PubFun.copySchema(tLPBnfSchemaNew, tLCBnfSchema);
				tLPBnfSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
				tLPBnfSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
				tLPBnfSchemaNew.setOperator(rGlobalInput.Operator);
				tLPBnfSchemaNew.setModifyDate(sCurrentDate);
				tLPBnfSchemaNew.setModifyTime(sCurrentTime);
				tLPBnfSetNew.add(tLPBnfSchemaNew);
				tLPBnfSchemaNew = null;
				tLCBnfSchema = null;
			}
			rMap.put(tLPBnfSetNew, "INSERT");
			tLPBnfSetNew = null;
		}
		tLCBnfDB = null;
		tLCBnfSet = null;
		
		Reflections tReflections = new Reflections();
		// 保费项目表 lcprem - lpprem
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSchema.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setContNo(rLPEdorItemSchema.getContNo());
		tLPPremSchema.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPPremDB.setSchema(tLPPremSchema);
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet.size() < 1) {
			CError.buildErr(this, "查询保费项目表失败!");
			return false;
		}
		
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tReflections.transFields(aLCPremSchema, aLPPremSchema);
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			
			 LCPremDB tLCPremDB = new LCPremDB();
			 tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			 tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			 tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			 
			 boolean tExistsFlag = tLCPremDB.getInfo();
			if (tLCPremDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLCPremDB.mErrors);
				mErrors.addOneError(new CError("查询保费项目表失败！"));
				return false;
			}
			if (tExistsFlag == true) {
				tReflections.transFields(aLPPremSchema, tLCPremDB.getSchema());
				aLPPremSchema.setEdorNo(rLPEdorItemSchema.getEdorNo());
				aLPPremSchema.setEdorType(rLPEdorItemSchema.getEdorType());
				aLPPremSet.add(aLPPremSchema);
			}
			 
			 aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
		}
		rMap.put(aLPPremSet,"UPDATE");
		rMap.put(aLCPremSet, "DELETE&INSERT");

		// logger.debug("\t@> PEdorBCConfirmBL.dealData() 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> PEdorBCConfirmBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorBCConfirmBL.outputData() 结束");
		return true;
	} // function outputData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rLPEdorItemSchema != null)
			rLPEdorItemSchema = null;
		if (rMap != null)
			rMap = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// PEdorBCConfirmBL tPEdorBCConfirmBL = new PEdorBCConfirmBL();
	// } //function main end
	// ==========================================================================

} // class PEdorBCConfirmBL end
