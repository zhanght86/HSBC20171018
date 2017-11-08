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
 * @date     : 2006-09-28, 2006-10-10, 2006-10-18, 2007-05-15
 * @direction: 保全受益人变更明细
 ******************************************************************************/


import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorBCDetailBL {
private static Logger logger = Logger.getLogger(PEdorBCDetailBL.class);
	// public PEdorBCDetailBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPBnfSet rLPBnfSet;
	private LPEdorItemSchema rLPEdorItemSchema;
	private Reflections mReflections = new Reflections();
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
		// logger.debug("\t@> PEdorBCDetailBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorBCDetailBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> PEdorBCDetailBL.submitData() 结束");
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
		// logger.debug("\t@> PEdorBCDetailBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorBCDetailBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPEdorItemSchema
		rLPEdorItemSchema = new LPEdorItemSchema();
		rLPEdorItemSchema = (LPEdorItemSchema) rInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		if (rLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> PEdorBCDetailBL.getInputData() : 无法获取 LPEdorItemSchema 数据！");
			return false;
		}

		// LPBnfSet
		rLPBnfSet = new LPBnfSet();
		rLPBnfSet = (LPBnfSet) rInputData.getObjectByObjectName("LPBnfSet", 0);
		if (rLPBnfSet == null) {
			CError.buildErr(this, "无法获取变更之后的受益人信息！");
			logger.debug("\t@> PEdorBCDetailBL.getInputData() : 无法获取 LPBnfSet 数据！");
			return false;
		}

		// logger.debug("\t@> PEdorBCDetailBL.getInputData() 结束");
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
		// logger.debug("\t@> PEdorBCDetailBL.checkData() 开始");

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

		// logger.debug("\t@> PEdorBCDetailBL.checkData() 结束");
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
		// logger.debug("\t@> PEdorBCDetailBL.dealData() 开始");

		rMap = new MMap();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 更新 LPEdorItem
		rLPEdorItemSchema.setEdorState("3");
		rLPEdorItemSchema.setModifyDate(sCurrentDate);
		rLPEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPEdorItemSchema, "UPDATE");


		
		//投保人  为核保准备数据
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(rLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		mReflections.transFields(tLPAppntSchema, tLCAppntSchema);
		tLPAppntSchema.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPAppntSet.add(tLPAppntSchema);
		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = null;
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LPInsuredSchema tLPInsuredSchema = null;
		
		
		tLCInsuredDB.setContNo(rLPEdorItemSchema.getContNo());		
		tLCInsuredSchema=tLCInsuredDB.query().get(1);
		tLPInsuredSchema = new LPInsuredSchema();
		mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(rLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if(tLCCSpecSet.size()>0)
		{
			for(int k=1;k<=tLCCSpecSet.size();k++)
			{
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				mReflections.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(rLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(rLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			rMap.put(mLPCSpecSet, "DELETE&INSERT");
		}
		
		rMap.put(tLPInsuredSet, "DELETE&INSERT");
		rMap.put(tLPAppntSet, "DELETE&INSERT");
		// ----------------------------------------------------------------------

		// 删除 LPCont
		DeleteSQL = "delete from LPCont " + "where 1 = 1 " + "and EdorNo = '"
				+ "?EdorNo?" + "' " + "and EdorType = '"
				+ "?EdorType?" + "' " + "and ContNo = '"
				+ "?ContNo?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("EdorNo", rLPEdorItemSchema.getEdorNo());
		sbv1.put("EdorType", rLPEdorItemSchema.getEdorType());
		sbv1.put("ContNo", rLPEdorItemSchema.getContNo());
		// logger.debug(DeleteSQL);
		rMap.put(sbv1, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCCont
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(rLPEdorItemSchema.getContNo());
		LCContSet tLCContSet = new LCContSet();
		try {
			tLCContSet = tLCContDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约合同表发生异常！");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			CError.buildErr(this, "在新契约合同表中找不到待操作保单的纪录！");
			return false;
		} else {
			// 拷贝 LCCont 到 LPCont
			// 没什么意义, 但是保全复核需要用到 LPCont 数据
			LPContSet tLPContSetNew = new LPContSet();
			for (int i = 1; i <= tLCContSet.size(); i++) {
				LCContSchema tLCContSchema = new LCContSchema();
				LPContSchema tLPContSchemaNew = new LPContSchema();
				tLCContSchema = tLCContSet.get(i);
				PubFun.copySchema(tLPContSchemaNew, tLCContSchema);
				tLPContSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
				tLPContSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
				// tLPContSchemaNew.setOperator(rGlobalInput.Operator);
				tLPContSchemaNew.setModifyDate(sCurrentDate);
				tLPContSchemaNew.setModifyTime(sCurrentTime);
				tLPContSetNew.add(tLPContSchemaNew);
				tLPContSchemaNew = null;
				tLCContSchema = null;
			}
			rMap.put(tLPContSetNew, "INSERT");
			tLPContSetNew = null;
		}
		tLCContDB = null;
		tLCContSet = null;

		// ----------------------------------------------------------------------

		// 删除 LPPol
		DeleteSQL = "delete from LPPol " + "where 1 = 1 " + "and EdorNo = '"
				+ "?EdorNo?" + "' " + "and EdorType = '"
				+ "?EdorType?" + "' " + "and ContNo = '"
				+ "?ContNo?" + "' " + "and PolNo = '"
				+ "?PolNo?" + "'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("EdorNo", rLPEdorItemSchema.getEdorNo());
		sbv2.put("EdorType", rLPEdorItemSchema.getEdorType());
		sbv2.put("ContNo", rLPEdorItemSchema.getContNo());
		sbv2.put("PolNo", rLPEdorItemSchema.getPolNo());
		rMap.put(sbv2, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCPol
		LCPolDB tLCPolDB = new LCPolDB();
		//为了保全二核，不仅仅处理主险数据，而且需要处理整个保单下有效险种
		tLCPolDB.setContNo(rLPEdorItemSchema.getContNo());
		tLCPolDB.setAppFlag("1");
		//tLCPolDB.setPolNo(rLPEdorItemSchema.getPolNo()); 
		LCPolSet tLCPolSet = new LCPolSet();
		try {
			tLCPolSet = tLCPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约险种表发生异常！");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			CError.buildErr(this, "在新契约险种表中找不到待操作保单的纪录！");
			return false;
		} else {
			// 拷贝 LCPol 到 LPPol
			// 没什么意义, 但是校验规则需要用到 LPPol 数据
			LPPolSet tLPPolSetNew = new LPPolSet();
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				LPPolSchema tLPPolSchemaNew = new LPPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				PubFun.copySchema(tLPPolSchemaNew, tLCPolSchema);
				tLPPolSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
				tLPPolSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
				// tLPPolSchemaNew.setOperator(rGlobalInput.Operator);
				tLPPolSchemaNew.setModifyDate(sCurrentDate);
				tLPPolSchemaNew.setModifyTime(sCurrentTime);
				tLPPolSetNew.add(tLPPolSchemaNew);
				
				
				// 删除 LPDuty
				DeleteSQL = "delete from LPDuty " + "where 1 = 1 " + "and EdorNo = '"
						+ "?EdorNo?" + "' " + "and EdorType = '"
						+ "?EdorType?" + "' " + "and ContNo = '"
						+ "?ContNo?" + "' " + "and PolNo = '"
						+ "?PolNo?" + "'";
				// logger.debug(DeleteSQL);
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql(DeleteSQL);
				sbv3.put("EdorNo", rLPEdorItemSchema.getEdorNo());
				sbv3.put("EdorType", rLPEdorItemSchema.getEdorType());
				sbv3.put("ContNo", rLPEdorItemSchema.getContNo());
				sbv3.put("PolNo", tLCPolSchema.getPolNo());
				rMap.put(sbv3, "DELETE");

				// ----------------------------------------------------------------------

				// 查询 LCDuty
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setContNo(rLPEdorItemSchema.getContNo());
				tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
				LCDutySet tLCDutySet = new LCDutySet();
				try {
					tLCDutySet = tLCDutyDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询新契约责任表发生异常！");
					return false;
				}
				if (tLCDutySet == null || tLCDutySet.size() <= 0) {
					CError.buildErr(this, "在新契约责任表中找不到待操作保单的纪录！");
					return false;
				} else {
					// 拷贝 LCDuty 到 LPDuty
					// 没什么意义, 但是保全复核需要用到 LPDuty 数据
					LPDutySet tLPDutySetNew = new LPDutySet();
					for (int k = 1; k <= tLCDutySet.size(); k++) {
						LCDutySchema tLCDutySchema = new LCDutySchema();
						LPDutySchema tLPDutySchemaNew = new LPDutySchema();
						tLCDutySchema = tLCDutySet.get(k);
						PubFun.copySchema(tLPDutySchemaNew, tLCDutySchema);
						tLPDutySchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
						tLPDutySchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
						tLPDutySchemaNew.setOperator(rGlobalInput.Operator);
						tLPDutySchemaNew.setModifyDate(sCurrentDate);
						tLPDutySchemaNew.setModifyTime(sCurrentTime);
						tLPDutySetNew.add(tLPDutySchemaNew);
						tLPDutySchemaNew = null;
						tLCDutySchema = null;
					}
					rMap.put(tLPDutySetNew, "INSERT");
					tLPDutySetNew = null;
				}
				tLCDutyDB = null;
				tLCDutySet = null;

				// ----------------------------------------------------------------------

				// 删除 LPPrem
				DeleteSQL = "delete from LPPrem " + "where 1 = 1 " + "and EdorNo = '"
						+ "?EdorNo?" + "' " + "and EdorType = '"
						+ "?EdorType?" + "' " + "and ContNo = '"
						+ "?ContNo?" + "' " + "and PolNo = '"
						+ "?PolNo?" + "'";
				// logger.debug(DeleteSQL);
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(DeleteSQL);
				sbv4.put("EdorNo", rLPEdorItemSchema.getEdorNo());
				sbv4.put("EdorType", rLPEdorItemSchema.getEdorType());
				sbv4.put("ContNo", rLPEdorItemSchema.getContNo());
				sbv4.put("PolNo", tLCPolSchema.getPolNo());
				rMap.put(sbv4, "DELETE");

				// ----------------------------------------------------------------------

				// 查询 LCPrem
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setContNo(rLPEdorItemSchema.getContNo());
				tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
				LCPremSet tLCPremSet = new LCPremSet();
				try {
					tLCPremSet = tLCPremDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询新契约保费表发生异常！");
					return false;
				}
				if (tLCPremSet == null || tLCPremSet.size() <= 0) {
					CError.buildErr(this, "在新契约保费表中找不到待操作保单的纪录！");
					return false;
				} else {
					// 拷贝 LCPrem 到 LPPrem
					// 没什么意义, 但是保全复核需要用到 LPPrem 数据
					LPPremSet tLPPremSetNew = new LPPremSet();
					for (int l = 1; l <= tLCPremSet.size(); l++) {
						LCPremSchema tLCPremSchema = new LCPremSchema();
						LPPremSchema tLPPremSchemaNew = new LPPremSchema();
						tLCPremSchema = tLCPremSet.get(l);
						PubFun.copySchema(tLPPremSchemaNew, tLCPremSchema);
						tLPPremSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
						tLPPremSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
						tLPPremSchemaNew.setOperator(rGlobalInput.Operator);
						tLPPremSchemaNew.setModifyDate(sCurrentDate);
						tLPPremSchemaNew.setModifyTime(sCurrentTime);
						tLPPremSetNew.add(tLPPremSchemaNew);
						tLPPremSchemaNew = null;
						tLCPremSchema = null;
					}
					rMap.put(tLPPremSetNew, "INSERT");
					tLPPremSetNew = null;
				}
				tLCPremDB = null;
				tLCPremSet = null;

				// ----------------------------------------------------------------------

				// 删除 LPGet
				DeleteSQL = "delete from LPGet " + "where 1 = 1 " + "and EdorNo = '"
						+ "?EdorNo?" + "' " + "and EdorType = '"
						+ "?EdorType?" + "' " + "and ContNo = '"
						+ "?ContNo?" + "' " + "and PolNo = '"
						+ "?PolNo?" + "'";
				// logger.debug(DeleteSQL);
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(DeleteSQL);
				sbv5.put("EdorNo", rLPEdorItemSchema.getEdorNo());
				sbv5.put("EdorType", rLPEdorItemSchema.getEdorType());
				sbv5.put("ContNo", rLPEdorItemSchema.getContNo());
				sbv5.put("PolNo", tLCPolSchema.getPolNo());
				rMap.put(sbv5, "DELETE");

				// ----------------------------------------------------------------------

				// 查询 LCGet
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setContNo(rLPEdorItemSchema.getContNo());
				tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
				LCGetSet tLCGetSet = new LCGetSet();
				try {
					tLCGetSet = tLCGetDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询新契约领取表发生异常！");
					return false;
				}
				if (tLCGetSet != null && tLCGetSet.size() > 0) {
					// 拷贝 LCGet 到 LPGet
					// 没什么意义, 但是保全复核需要用到 LPGet 数据
					LPGetSet tLPGetSetNew = new LPGetSet();
					for (int n = 1; n <= tLCGetSet.size(); n++) {
						LCGetSchema tLCGetSchema = new LCGetSchema();
						LPGetSchema tLPGetSchemaNew = new LPGetSchema();
						tLCGetSchema = tLCGetSet.get(n);
						PubFun.copySchema(tLPGetSchemaNew, tLCGetSchema);
						tLPGetSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
						tLPGetSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
						tLPGetSchemaNew.setOperator(rGlobalInput.Operator);
						tLPGetSchemaNew.setModifyDate(sCurrentDate);
						tLPGetSchemaNew.setModifyTime(sCurrentTime);
						tLPGetSetNew.add(tLPGetSchemaNew);
						tLPGetSchemaNew = null;
						tLCGetSchema = null;
					}
					rMap.put(tLPGetSetNew, "INSERT");
					tLPGetSetNew = null;
				}
				tLCGetDB = null;
				tLCGetSet = null;
				
				tLPPolSchemaNew = null;
				tLCPolSchema = null;
			}
			rMap.put(tLPPolSetNew, "INSERT");
			tLPPolSetNew = null;
		}
		tLCPolDB = null;
		tLCPolSet = null;

		// ----------------------------------------------------------------------



		// ----------------------------------------------------------------------

		// 删除 LPBnf
		DeleteSQL = "delete from LPBnf " + "where 1 = 1 " + "and EdorNo = '"
				+ "?EdorNo?" + "' " + "and EdorType = '"
				+ "?EdorType?" + "' " + "and PolNo = '"
				+ "?PolNo?" + "'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(DeleteSQL);
		sbv.put("EdorNo", rLPEdorItemSchema.getEdorNo());
		sbv.put("EdorType", rLPEdorItemSchema.getEdorType());
		sbv.put("PolNo", rLPEdorItemSchema.getPolNo());
		rMap.put(sbv, "DELETE");

		// ----------------------------------------------------------------------

		// 插入 LPBnf
		// 本类的重点
		if (rLPBnfSet.size() > 0 && rLPBnfSet.get(1).getBnfNo() != 0) // 加入对磁盘导入的支持
		{
			LPBnfSet tLPBnfSetNew = new LPBnfSet();
			for (int i = 1; i <= rLPBnfSet.size(); i++) {
				LPBnfSchema tLPBnfSchema = new LPBnfSchema();
				LPBnfSchema tLPBnfSchemaNew = new LPBnfSchema();
				tLPBnfSchema = rLPBnfSet.get(i);
				PubFun.copySchema(tLPBnfSchemaNew, tLPBnfSchema);
				tLPBnfSchemaNew.setEdorNo(rLPEdorItemSchema.getEdorNo());
				tLPBnfSchemaNew.setEdorType(rLPEdorItemSchema.getEdorType());
				tLPBnfSchemaNew.setInsuredNo(rLPEdorItemSchema.getInsuredNo());
				tLPBnfSchemaNew.setPolNo(rLPEdorItemSchema.getPolNo());
				tLPBnfSchemaNew.setOperator(rGlobalInput.Operator);
				tLPBnfSchemaNew.setMakeDate(sCurrentDate);
				tLPBnfSchemaNew.setMakeTime(sCurrentTime);
				tLPBnfSchemaNew.setModifyDate(sCurrentDate);
				tLPBnfSchemaNew.setModifyTime(sCurrentTime);
				tLPBnfSetNew.add(tLPBnfSchemaNew);
				tLPBnfSchemaNew = null;
				tLPBnfSchema = null;
			}
			rMap.put(tLPBnfSetNew, "INSERT");
			tLPBnfSetNew = null;
		}

		// logger.debug("\t@> PEdorBCDetailBL.dealData() 结束");
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
		// logger.debug("\t@> PEdorBCDetailBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorBCDetailBL.outputData() 结束");
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
		if (rLPBnfSet != null)
			rLPBnfSet = null;
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
	// PEdorBCDetailBL tPEdorBCDetailBL = new PEdorBCDetailBL();
	// } //function main end
	// ==========================================================================

} // class PEdorBCDetailBL end
