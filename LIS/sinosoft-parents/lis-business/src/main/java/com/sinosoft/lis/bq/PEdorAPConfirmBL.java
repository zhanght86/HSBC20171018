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
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-14, 2006-03-11, 2006-08-12
 * @direction: 保全保费自动垫交变更确认
 ******************************************************************************/


import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorAPConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorAPConfirmBL.class);
	// public PEdorAPConfirmBL() {}

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

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorAPConfirmBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorAPConfirmBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> PEdorAPConfirmBL.submitData() 结束");
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
		// logger.debug("\t@> PEdorAPConfirmBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorAPConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPEdorItemSchema
		rLPEdorItemSchema = new LPEdorItemSchema();
		rLPEdorItemSchema = (LPEdorItemSchema) rInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		if (rLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> PEdorAPConfirmBL.getInputData() : 无法获取 LPEdorItemSchema 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = rLPEdorItemSchema.getEdorAcceptNo();
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}

		// 批单号码
		String sEdorNo = rLPEdorItemSchema.getEdorNo();
		if (sEdorNo == null || sEdorNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}

		// 批改类型
		String sEdorType = rLPEdorItemSchema.getEdorType();
		if (sEdorType == null || sEdorType.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}

		// 保单号码
		String sPolNo = rLPEdorItemSchema.getPolNo();
		if (sPolNo == null || sPolNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		// logger.debug("\t@> PEdorAPConfirmBL.getInputData() 结束");
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
		// logger.debug("\t@> PEdorAPConfirmBL.checkData() 开始");

		// 查询 LPEdorItem
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(rLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setInsuredNo(rLPEdorItemSchema.getInsuredNo());
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

		// logger.debug("\t@> PEdorAPConfirmBL.checkData() 结束");
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
		// logger.debug("\t@> PEdorAPConfirmBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String DeleteSQL = null;
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 更新 LPEdorItem
		rLPEdorItemSchema.setEdorState("6");
		rLPEdorItemSchema.setModifyDate(sCurrentDate);
		rLPEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPEdorItemSchema, "UPDATE");

		// -----------add by ssx 2008-07-31 增加了LCCont与LPCont的互换------------
		// 删除 LCCont
		DeleteSQL = "delete from lCCont where 1 = 1 " + " and ContNo = '"
				+ "?ContNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DeleteSQL);
		sqlbv.put("ContNo", rLPEdorItemSchema.getContNo());
		rMap.put(sqlbv, "DELETE");
		// 查询LPCont 将P表数据拷贝到C表
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(rLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(rLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询保全合同表发生异常！");
			return false;
		}
		LPContSchema tLPContSchema = tLPContDB.getSchema();
		LCContSchema tLCContSchemaNew = new LCContSchema();
		tReflections.transFields(tLCContSchemaNew, tLPContSchema);
		tLCContSchemaNew.setOperator(rGlobalInput.Operator);
		tLCContSchemaNew.setModifyDate(sCurrentDate);
		tLCContSchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLCContSchemaNew, "INSERT");

		// 删除LPCont
		DeleteSQL = "delete from lPCont where 1 = 1 " + " and ContNo = '"
				+ "?ContNo?" + "'" + " and EdorNo = '"
				+ "?EdorNo?" + "'" + " and EdorType = '"
				+ "?EdorType?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("ContNo", rLPEdorItemSchema.getContNo());
		sbv1.put("EdorNo", rLPEdorItemSchema.getEdorNo());
		sbv1.put("EdorType", rLPEdorItemSchema.getEdorType());
		rMap.put(sbv1, "DELETE");

		// 查询LCCont
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(rLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询个人合同表发生异常！");
			return false;
		}
		LCContSchema tLCContSchema = tLCContDB.getSchema();
		LPContSchema tLPContSchemaNew = new LPContSchema();
		tReflections.transFields(tLPContSchemaNew, tLCContSchema);
		tLPContSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPContSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPContSchemaNew.setOperator(rGlobalInput.Operator);
		tLPContSchemaNew.setModifyDate(sCurrentDate);
		tLPContSchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLPContSchemaNew, "INSERT");

		// -----------------cont表的PC互换结束--------------------------------

		// 删除 合同下相应的做了自垫变更的LCPol
		DeleteSQL = "delete from LCPol " + " where 1 = 1 " + " and ContNo = '"
				+ "?ContNo?" + "'" + " and polno in "
				+ " (select p.polno from lppol p" + " where p.contno = '"
				+ "?ContNo?" + "' " + " and p.edorno = '"
				+ "?edorno?" + "' " + " and p.edortype = '"
				+ "?edortype?" + "') ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("ContNo", rLPEdorItemSchema.getContNo());
		sbv2.put("edorno", rLPEdorItemSchema.getEdorNo());
		sbv2.put("edortype", rLPEdorItemSchema.getEdorType());
		
		// logger.debug(DeleteSQL);
		rMap.put(sbv2, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LPPol
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(rLPEdorItemSchema.getEdorType());
		// tLPPolDB.setPolNo(rLPEdorItemSchema.getPolNo());
		// 跟据合同号contno来查询
		tLPPolDB.setContNo(rLPEdorItemSchema.getContNo());
		LPPolSet tLPPolSet = null;
		try {
			tLPPolSet = tLPPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全险种表发生异常！");
			return false;
		}

		// String[] tPolNoSet = new String[tLPPolSet.size()];

		if (tLPPolSet == null || tLPPolSet.size() <= 0) {
			CError.buildErr(this, "在保全险种表中找不到待操作保单的纪录！");
			return false;
		} else {
			// 拷贝 LPPol 到 LCPol
			LCPolSet tLCPolSetNew = new LCPolSet();
			for (int i = 1; i <= tLPPolSet.size(); i++) {
				LPPolSchema tLPPolSchema = new LPPolSchema();
				tLPPolSchema = tLPPolSet.get(i);
				LCPolSchema tLCPolSchemaNew = new LCPolSchema();
				tReflections.transFields(tLCPolSchemaNew, tLPPolSchema);
				tLCPolSchemaNew.setOperator(rGlobalInput.Operator);
				tLCPolSchemaNew.setModifyDate(sCurrentDate);
				tLCPolSchemaNew.setModifyTime(sCurrentTime);
				tLCPolSetNew.add(tLCPolSchemaNew);
				// tPolNoSet[i-1] = tLPPolSchema.getPolNo();
				tLCPolSchemaNew = null;
				tLPPolSchema = null;
			}
			rMap.put(tLCPolSetNew, "INSERT");
			tLCPolSetNew = null;
		}
		tLPPolDB = null;
		tLPPolSet = null;

		// ----------------------------------------------------------------------

		// 删除 LPPol
		DeleteSQL = "delete from LPPol " + "where 1 = 1 " + "and EdorNo = '"
				+ "?edorno?" + "' " + "and EdorType = '"
				+ "?edortype?" + "' " + "and ContNo = '"
				+ "?ContNo?" + "'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(DeleteSQL);
		sbv3.put("ContNo", rLPEdorItemSchema.getContNo());
		sbv3.put("edorno", rLPEdorItemSchema.getEdorNo());
		sbv3.put("edortype", rLPEdorItemSchema.getEdorType());
		// logger.debug(DeleteSQL);
		rMap.put(sbv3, "DELETE");

		// ----------------------------------------------------------------------

		// 查询本次保全操作对应的LCPol
		LCPolDB tLCPolDB = new LCPolDB();
		String tSQL = "select * from lcpol c " + "where 1 = 1 "
				+ " and c.polno in " + " (select p.polno from lppol p"
				+ " where p.contno = '" + "?contno?" + "' "
				+ " and p.edorno = '" + "?edorno?" + "' "
				+ " and p.edortype = '" + "?edortype?"
				+ "') ";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(tSQL);
		sbv4.put("contno", rLPEdorItemSchema.getContNo());
		sbv4.put("edorno", rLPEdorItemSchema.getEdorNo());
		sbv4.put("edortype", rLPEdorItemSchema.getEdorType());
		LCPolSet tLCPolSet = null;
		try {
			tLCPolSet = tLCPolDB.executeQuery(sbv4);
		} catch (Exception ex) {
			CError.buildErr(this, "查询契约险种表发生异常！");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			CError.buildErr(this, "在契约险种表中找不到待操作保单的纪录！");
			return false;
		} else {
			// 拷贝 LCPol 到 LPPol
			LPPolSet tLPPolSetNew = new LPPolSet();
			LCPolSchema tLCPolSchema = null;
			LPPolSchema tLPPolSchemaNew = null;
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLPPolSchemaNew = new LPPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				tReflections.transFields(tLPPolSchemaNew, tLCPolSchema);
				tLPPolSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
				tLPPolSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
				tLPPolSchemaNew.setOperator(rGlobalInput.Operator);
				tLPPolSchemaNew.setModifyDate(sCurrentDate);
				tLPPolSchemaNew.setModifyTime(sCurrentTime);
				tLPPolSetNew.add(tLPPolSchemaNew);
			}
			rMap.put(tLPPolSetNew, "INSERT");
			tLPPolSetNew = null;
		}
		tLCPolDB = null;
		tLCPolSet = null;

		// ----------------------------------------------------------------------

		// 垃圾处理
		tReflections = null;

		// logger.debug("\t@> PEdorAPConfirmBL.dealData() 结束");
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
		// logger.debug("\t@> PEdorAPConfirmBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorAPConfirmBL.outputData() 结束");
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
	// PEdorAPConfirmBL tPEdorAPConfirmBL = new PEdorAPConfirmBL();
	// } //function main end
	// ==========================================================================

} // class PEdorAPConfirmBL end
