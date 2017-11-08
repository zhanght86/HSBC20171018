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
 * @date     : 2005-11-14, 2006-03-11, 2006-08-12, 2006-10-17
 * @direction: 保全保费自动垫交变更明细
 ******************************************************************************/


import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
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
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorAPDetailBL {
private static Logger logger = Logger.getLogger(PEdorAPDetailBL.class);
	// public PEdorAPDetailBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
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
		// logger.debug("\t@> PEdorAPDetailBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			logger.debug("\t@> PEdorAPDetailBL.submitData() : 无法获取 InputData 数据！");
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
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

		// logger.debug("\t@> PEdorAPDetailBL.submitData() 结束");
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
		// logger.debug("\t@> PEdorAPDetailBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorAPDetailBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取自动垫交标记信息！");
			logger.debug("\t@> PEdorAPDetailBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// LPEdorItemSchema
		rLPEdorItemSchema = new LPEdorItemSchema();
		rLPEdorItemSchema = (LPEdorItemSchema) rInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		if (rLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> PEdorAPDetailBL.getInputData() : 无法获取 LPEdorItemSchema 数据！");
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

		// 合同号码
		String sContNo = rLPEdorItemSchema.getContNo();
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的合同号码信息！");
			return false;
		}

		// 保单号码
		// String sPolNo = rLPEdorItemSchema.getPolNo();
		// if (sPolNo == null || sPolNo.trim().equals(""))
		// {
		// CError.buildErr(this, "无法获取保全项目的保单号码信息！");
		// return false;
		// }

		// 自垫标记
		String sAutoPayFlag = (String) rTransferData
				.getValueByName("AutoPayFlag");
		if (sAutoPayFlag == null || sAutoPayFlag.trim().equals("")) {
			CError.buildErr(this, "无法获取保单自动垫交标记信息！");
			return false;
		}

		// logger.debug("\t@> PEdorAPDetailBL.getInputData() 结束");
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
		// logger.debug("\t@> PEdorAPDetailBL.checkData() 开始");

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

		// logger.debug("\t@> PEdorAPDetailBL.checkData() 结束");
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
		logger.debug("\t@> PEdorAPDetailBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String DeleteSQL = null;
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// 接收传递变量
		String sAutoPayFlag = (String) rTransferData
				.getValueByName("AutoPayFlag");

		// 更新 LPEdorItem
		rLPEdorItemSchema.setEdorState("3");
		rLPEdorItemSchema.setModifyDate(sCurrentDate);
		rLPEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPEdorItemSchema, "UPDATE");

		// ------------add by ssx 生成相应的LPCont,及其下能做自垫变更险种的LPPol------------
		// 删除LPCont
		DeleteSQL = "delete from LPCont " + "where 1 = 1 " + "and EdorNo = '"
				+ "?EdorNo?" + "' " + "and EdorType = '"
				+ "?EdorType?" + "' " + "and ContNo = '"
				+ "?ContNo?" + "' ";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(DeleteSQL);
        sbv1.put("EdorNo", rLPEdorItemSchema.getEdorNo());
        sbv1.put("EdorType", rLPEdorItemSchema.getEdorType());
        sbv1.put("ContNo", rLPEdorItemSchema.getContNo());
		rMap.put(sbv1, "DELETE");

		// 生成新的LPCont
		LPContSchema tLPContSchema = new LPContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(rLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息发生异常！");
			return false;
		}
		LCContSchema tLCContSchema = null;
		tLCContSchema = tLCContDB.getSchema();
		// 将C表的数据放到P表
		tReflections.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(rLPEdorItemSchema.getEdorType());
		logger.debug("新生成的LPCONT表的保单号:" + tLPContSchema.getContNo());
		tLPContSchema.setAutoPayFlag(sAutoPayFlag);
		tLPContSchema.setOperator(rGlobalInput.Operator);// 设置操作员
		tLPContSchema.setModifyDate(sCurrentDate);
		tLPContSchema.setModifyTime(sCurrentTime);
		rMap.put(tLPContSchema, "INSERT");

		// 删除 LPPol
		DeleteSQL = "delete from LPPol " + "where 1 = 1 " + "and EdorNo = '"
				+ "?EdorNo?" + "' " + "and EdorType = '"
				+ "?EdorType?" + "' " + "and ContNo = '"
				+ "?ContNo?" + "' ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("EdorNo", rLPEdorItemSchema.getEdorNo());
		sbv2.put("EdorType", rLPEdorItemSchema.getEdorType());
		sbv2.put("ContNo", rLPEdorItemSchema.getContNo());
		rMap.put(sbv2, "DELETE");

		// 生成LPPOLSET 存放该CONT下所有可做自垫变更的险种

		// 查询所有可做自垫变更的LCPol
		LCPolDB tLCPolDB = new LCPolDB();
		String sql = "select * from lcpol c where c.contno = '"
				+ "?contno?"
				+ "' and c.riskcode in (select riskcode from lmriskapp where autopayflag = '1') and appflag='1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("contno", rLPEdorItemSchema.getContNo());
		LCPolSet tLCPolSet = null;
		try {
			tLCPolSet = tLCPolDB.executeQuery(sqlbv);
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
				tLPPolSchemaNew.setAutoPayFlag(sAutoPayFlag);
				tLPPolSchemaNew.setOperator(rGlobalInput.Operator);
				tLPPolSchemaNew.setModifyDate(sCurrentDate);
				tLPPolSchemaNew.setModifyTime(sCurrentTime);
				tLPPolSetNew.add(tLPPolSchemaNew);
			}
			rMap.put(tLPPolSetNew, "INSERT");
		}

		logger.debug("\t@> PEdorAPDetailBL.dealData() 结束");
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
		// logger.debug("\t@> PEdorAPDetailBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorAPDetailBL.outputData() 结束");
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
		if (rTransferData != null)
			rTransferData = null;
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
	// PEdorAPDetailBL tPEdorAPDetailBL = new PEdorAPDetailBL();
	// } //function main end
	// ==========================================================================

} // class PEdorAPDetailBL end
