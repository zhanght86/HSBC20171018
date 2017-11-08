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
 * @version  : 1.00, 1.01
 * @date     : 2005-11-19, 2006-03-13
 * @direction: 保全保费自动垫交变更确认后回退
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
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorAPBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorAPBackConfirmBL.class);
	// public PEdorAPBackConfirmBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	//public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	// 全局变量
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
		// logger.debug("\t@> PEdorAPBackConfirmBL.submitData() 开始");

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
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorAPBackConfirmBL.submitData() 成功");
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
		// logger.debug("\t@> PEdorAPBackConfirmBL.getInputData() 开始");

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
		if (rLPEdorItemSchema.getEdorAcceptNo() == null
				|| rLPEdorItemSchema.getEdorAcceptNo().trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}
		// 批单号码
		if (rLPEdorItemSchema.getEdorNo() == null
				|| rLPEdorItemSchema.getEdorNo().trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}
		// 批改类型
		if (rLPEdorItemSchema.getEdorType() == null
				|| rLPEdorItemSchema.getEdorType().trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}
		// 保单号码
		if (rLPEdorItemSchema.getPolNo() == null
				|| rLPEdorItemSchema.getPolNo().trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		// logger.debug("\t@> PEdorAPBackConfirmBL.getInputData() 成功");
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
		// logger.debug("\t@> PEdorAPBackConfirmBL.checkData() 开始");

		// 检查保全项目明细表
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(rLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setInsuredNo(rLPEdorItemSchema.getInsuredNo());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全项目明细表出现异常！");
			return false;
		}
		if (tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else
			rLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		// 垃圾处理
		tLPEdorItemSet = null;
		tLPEdorItemDB = null;

		// logger.debug("\t@> PEdorAPBackConfirmBL.checkData() 成功");
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
		// logger.debug("\t@> PEdorAPBackConfirmBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// 更新保全项目表状态
		rLPEdorItemSchema.setModifyDate(sCurrentDate);
		rLPEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPEdorItemSchema, "UPDATE");

		// -----------add by ssx 2008-7-31 增加cont表的P,C互换-------------
		// 检查LPCont表
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(rLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(rLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询保全合同表发生异常！");
			return false;
		}
		LPContSchema tLPContSchemaOld = tLPContDB.getSchema();

		// 检查LCCont表
		LCContDB tlCContDB = new LCContDB();
		tlCContDB.setContNo(rLPEdorItemSchema.getContNo());
		if (!tlCContDB.getInfo()) {
			CError.buildErr(this, "查询合同表发生异常！");
			return false;
		}
		LCContSchema tLCContSchemaOld = tlCContDB.getSchema();
		LCContSchema tLCContSchemaNew = new LCContSchema();
		// 拷贝LPCONT到LCONT
		try {
			tReflections.transFields(tLCContSchemaNew, tLPContSchemaOld);
		} catch (Exception ex) {
			CError.buildErr(this, "拷贝保全合同表到合同表失败！");
			return false;
		}

		tLCContSchemaNew.setMakeDate(tLCContSchemaOld.getMakeDate());
		tLCContSchemaNew.setMakeTime(tLCContSchemaOld.getMakeTime());
		tLCContSchemaNew.setModifyDate(sCurrentDate);
		tLCContSchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLCContSchemaNew, "DELETE&INSERT");

		// ------------add end-------------------------------------------

		// ------------查询及更新范围变化----MD by ssx 2008-08-01------------------------
		// 检查 LPPol 表
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setContNo(rLPEdorItemSchema.getContNo());
		tLPPolDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(rLPEdorItemSchema.getEdorType());
		LPPolSet tLPPolSet = null;
		LPPolSchema tLPPolSchemaOld = new LPPolSchema();
		try {
			tLPPolSet = tLPPolDB.query();// 本次保全操作的险种表的集合
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全险种表信息出现异常！");
			return false;
		}
		if (tLPPolSet == null || tLPPolSet.size() <= 0) {
			CError.buildErr(this, "保全险种表中不存在此保单！");
			return false;
		}

		// 查询LCPol
		LCPolSet tLCPolSetNew = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchemaOld = null;
		LCPolSchema tLCPolSchemaNew = null;
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			tLPPolSchemaOld = tLPPolSet.get(i);
			tLCPolDB.setPolNo(tLPPolSchemaOld.getPolNo());
			try {
				tLCPolSchemaOld = tLCPolDB.query().get(1);
			} catch (Exception ex) {
				CError.buildErr(this, "查询契约险种表出现异常！");
				return false;
			}
			tLCPolSchemaNew = new LCPolSchema();
			// 拷贝P表数据到C表
			try {
				tReflections.transFields(tLCPolSchemaNew, tLPPolSchemaOld);
				tLCPolSchemaNew.setMakeDate(tLCPolSchemaOld.getMakeDate());
				tLCPolSchemaNew.setMakeTime(tLCPolSchemaOld.getMakeTime());
				tLCPolSchemaNew.setModifyDate(sCurrentDate);
				tLCPolSchemaNew.setModifyTime(sCurrentTime);
			} catch (Exception ex) {
				CError.buildErr(this, "拷贝保全险种表到契约险种表失败！");
				return false;
			}

			tLCPolSetNew.add(tLCPolSchemaNew);

		}
		rMap.put(tLCPolSetNew, "DELETE&INSERT");

		// ------------------------------end----------------------------------------

		// 检查 LCPol 表
		// LCPolDB tLCPolDB = new LCPolDB();
		// tLCPolDB.setPolNo(rLPEdorItemSchema.getPolNo());
		// LCPolSet tLCPolSet = new LCPolSet();
		// LCPolSchema tLCPolSchemaOld = new LCPolSchema();
		// try
		// {
		// tLCPolSet = tLCPolDB.query();
		// }
		// catch (Exception ex)
		// {
		// CError.buildErr(this, "查询契约险种表出现异常！");
		// return false;
		// }
		// if (tLCPolSet.size() <= 0)
		// {
		// CError.buildErr(this, "在契约险种表中找不到待操作保单的纪录！");
		// return false;
		// }
		// tLCPolSchemaOld = tLCPolSet.get(1);
		// //垃圾处理
		// tLCPolSet = null;
		// tLCPolDB = null;

		// ----------------------------------------------------------------------

		// 拷贝 LPPol 到 LCPol
		// LCPolSchema tLCPolSchemaNew = new LCPolSchema();
		// try
		// {
		// tReflections.transFields(tLCPolSchemaNew, tLPPolSchemaOld);
		// tLCPolSchemaNew.setMakeDate(tLCPolSchemaOld.getMakeDate());
		// tLCPolSchemaNew.setMakeTime(tLCPolSchemaOld.getMakeTime());
		// tLCPolSchemaNew.setModifyDate(sCurrentDate);
		// tLCPolSchemaNew.setModifyTime(sCurrentTime);
		// rMap.put(tLCPolSchemaNew, "DELETE&INSERT");
		// }
		// catch (Exception ex)
		// {
		// CError.buildErr(this, "拷贝保全险种表到契约险种表失败！");
		// return false;
		// }
		//
		// //----------------------------------------------------------------------
		//
		// //垃圾处理
		// tReflections = null;
		// tLPPolSchemaOld = null;
		// tLCPolSchemaOld = null;
		// tLCPolSchemaNew = null;

		// ----------------------------------------------------------------------

		// 准备返回需要的数据
		rResultData = new VData();
		rResultData.add(rMap);

		// ----------------------------------------------------------------------

		// logger.debug("\t@> PEdorAPBackConfirmBL.dealData() 成功");
		return true;
	} // function dealData end

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
	// GlobalInput tGlobalInput = new GlobalInput();
	// tGlobalInput.Operator = "XinYQ";
	// tGlobalInput.ManageCom = "86";
	// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	// tLPEdorItemSchema.setPolNo(""); //测试的险种号码
	// VData tVData = new VData();
	// tVData.addElement(tGlobalInput);
	// tVData.addElement(tLPEdorItemSchema);
	// //提交数据, 测试
	// PEdorAPBackConfirmBL tPEdorAPBackConfirmBL = new PEdorAPBackConfirmBL();
	// if (!tPEdorAPBackConfirmBL.submitData(tVData, "OPERATE"))
	// logger.debug("\t@> PEdorAPBackConfirmBL.main() : 测试保全 AP 项目回退失败");
	// }
	// ==========================================================================

} // class PEdorAPBackConfirmBL end
