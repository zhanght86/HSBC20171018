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
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2006-07-27, 2006-08-11, 2006-08-17, 2006-08-28, 2006-12-22
 * @direction: 团单保全添加保全项目校验处理
 ******************************************************************************/

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LMRiskEdorItemDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LMRiskEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

// ******************************************************************************

public class AddGEdorItemCheckBL {
private static Logger logger = Logger.getLogger(AddGEdorItemCheckBL.class);
	// public AddGEdorItemCheckBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 私有变量
	private String sCurrentDate = PubFun.getCurrentDate();

	// ==========================================================================

	/**
	 * 校验是否允许添加团单保全项目
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	public boolean canAddGEdorItem(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorItem() 开始");

		String sEdorAcceptNo = tLPGrpEdorItemSchema.getEdorAcceptNo();
		if (!chkEdorAppState(sEdorAcceptNo))
			return false;

		// ----------------------------------------------------------------------

		if (!chkGEdorPopedom(tLPGrpEdorItemSchema))
			return false;
		if (!chkEdorItemDate(tLPGrpEdorItemSchema))
			return false;
		if (!isGrpContExists(tLPGrpEdorItemSchema))
			return false;
		if (isRepeatEdorItem(tLPGrpEdorItemSchema))
			return false;
		if (!isOnlyOneItem(tLPGrpEdorItemSchema))
			return false;
		if (!chkGEdorItemRule(tLPGrpEdorItemSchema))
			return false;

		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorItem() 结束");
		return true;
	} // function canAddGEdorItem end

	// ==========================================================================

	/**
	 * 校验是否允许添加团单保全项目
	 * 
	 * @param LPGrpEdorItemSet
	 * @return boolean
	 */
	public boolean canAddGEdorItem(LPGrpEdorItemSet tLPGrpEdorItemSet) {
		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorItem() 开始");

		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			CError.buildErr(this, "无法获取团单保全项目信息。团单保全项目校验失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		String sEdorAcceptNo = tLPGrpEdorItemSet.get(1).getEdorAcceptNo();
		if (!chkEdorAppState(sEdorAcceptNo))
			return false;

		// ----------------------------------------------------------------------

		for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
			LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			if (!chkGEdorPopedom(tLPGrpEdorItemSchema))
				return false;
			if (!chkEdorItemDate(tLPGrpEdorItemSchema))
				return false;
			if (!isGrpContExists(tLPGrpEdorItemSchema))
				return false;
			if (isRepeatEdorItem(tLPGrpEdorItemSchema))
				return false;
			if (!isOnlyOneItem(tLPGrpEdorItemSchema))
				return false;
			if (!chkGEdorItemRule(tLPGrpEdorItemSchema))
				return false;
			tLPGrpEdorItemSchema = null;
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorItem() 结束");
		return true;
	} // function canAddGEdorItem end

	// ==========================================================================

	/**
	 * 检查操作员是否有权限申请保全项目
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean chkGEdorPopedom(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorPopedom() 开始");

		// if (tLPGrpEdorItemSchema == null)
		// {
		// CError.buildErr(this, "无法获取保全项目信息。检查保全权限失败！");
		// return false;
		// }

		// ----------------------------------------------------------------------

		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("EdorType",
		// tLPGrpEdorItemSchema.getEdorType());
		// VData tVData = new VData();
		// tVData.add(rGlobalInput);
		// tVData.add(tTransferData);
		// tTransferData = null;
		// PEdorPopedomCheckBL tPEdorPopedomCheckBL = new PEdorPopedomCheckBL();
		// if (!tPEdorPopedomCheckBL.submitData(tVData, "GApply"))
		// {
		// mErrors.copyAllErrors(tPEdorPopedomCheckBL.mErrors);
		// return false;
		// }
		// tPEdorPopedomCheckBL = null;
		// tVData = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorPopedom() 结束");
		return true;
	} // function chkGEdorPopedom end

	// ==========================================================================

	/**
	 * 检查申请的保全项目的状态
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean chkEdorAppState(String sEdorAcceptNo) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkEdorAppState() 开始");

		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全受理号。检查保全项目状态失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 查询 LPEdorApp
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(sEdorAcceptNo);
		LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		String sEdorState = new String("");
		try {
			tLPEdorAppSet = tLPEdorAppDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全申请表发生异常！");
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() <= 0) {
			CError.buildErr(this, "在保全申请表中找不到待操作批单的纪录！");
			return false;
		} else {
			tLPEdorAppSchema = tLPEdorAppSet.get(1);
			sEdorState = tLPEdorAppSchema.getEdorState();
		}
		tLPEdorAppDB = null;
		tLPEdorAppSet = null;
		tLPEdorAppSchema = null;

		// ----------------------------------------------------------------------

		// 检查 EdorState
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (sEdorState == null || sEdorState.trim().equals("")) {
			CError.buildErr(this, "查询保全受理状态失败。不能添加保全项目！");
			return false;
		} else {
			sEdorState = sEdorState.trim();
			// Edorstate : 1-录入完成 3-等待录入 5-复核修改
			if (sEdorState.equals("1") || sEdorState.equals("3")
					|| sEdorState.equals("5")) {
				return true;
			} else {
				ExeSQL tExeSQL = new ExeSQL();
				String QuerySQL = "select CodeName " + "from LDCode "
						+ "where 1 = 1 " + "and CodeType = 'edorstate' "
						+ "and trim(code) = '" + "?sEdorState?" + "'";
				// logger.debug(QuerySQL);
				sqlbv.sql(QuerySQL);
				sqlbv.put("sEdorState", sEdorState);
				String sStateName = new String("");
				try {
					sStateName = tExeSQL.getOneValue(sqlbv);
				} catch (Exception ex) {
					CError.buildErr(this, "查询获取保全状态名称失败。不能添加保全项目！");
					return false;
				}
				if (sStateName == null || sStateName.trim().equals("")) {
					logger.debug("\t@> AddGEdorItemCheckBL.chkEdorAppState() : 保全受理状态 : "
									+ sEdorState);
					CError.buildErr(this, "未知的保全受理状态。不能添加保全项目！");
					return false;
				} else {
					CError
							.buildErr(this, "该保全处于" + sStateName
									+ "状态。不能添加保全项目！");
					return false;
				}
			} // sEdorState <> 1 3 5
		}
	} // function chkEdorAppState end

	// ==========================================================================

	/**
	 * 检查保全项目的申请日期
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean chkEdorItemDate(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkEdorItemDate() 开始");

		if (tLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取保全项目信息。检查保全项目日期失败！");
			return false;
		} else {
			String sEdorAppDate = tLPGrpEdorItemSchema.getEdorAppDate();
			if (sEdorAppDate == null || sEdorAppDate.trim().equals("")) {
				CError.buildErr(this, "无法获取保全申请日期。检查保全项目日期失败！");
				return false;
			} else {
				int nAppDateIntv = PubFun.calInterval(sCurrentDate,
						sEdorAppDate, "D");
				if (nAppDateIntv > 0) {
					logger.debug("\t@> AddGEdorItemCheckBL.chkEdorItemDate() : 申请日期 "
									+ sEdorAppDate
									+ " 晚于系统日期 "
									+ sCurrentDate
									+ " ！");
					CError.buildErr(this, "保全申请日期不能晚于系统当前日期！");
					return false;
				}
			} // sEdorAppDate != null
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.chkEdorItemDate() 结束");
		return true;

	} // function chkEdorItemDate end

	// ==========================================================================

	/**
	 * 检查团单合同是否存在
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean isGrpContExists(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.isGrpContExists() 开始");

		if (tLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取保全项目信息。检查团单合同是否存在失败！");
			return false;
		} else {
			String sGrpContNo = tLPGrpEdorItemSchema.getGrpContNo();
			if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
				CError.buildErr(this, "无法获取团单合同号。检查团单合同是否存在失败！");
				return false;
			} else {
				// 查询 LCGrpCont
				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
				tLCGrpContDB.setGrpContNo(sGrpContNo);
				LCGrpContSet tLCGrpContSet = new LCGrpContSet();
				try {
					tLCGrpContSet = tLCGrpContDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询新契约团单合同表发生异常！");
					return false;
				}
				if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0) {
					logger.debug("\t@> AddGEdorItemCheckBL.isGrpContExists() : 团单合同号 "
									+ sGrpContNo + " 不存在！");
					CError.buildErr(this, "在新契约团单合同表找不到该团单号！");
					return false;
				}
				tLCGrpContDB = null;
				tLCGrpContSet = null;
			}
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.isGrpContExists() 结束");
		return true;
	} // function isGrpContExists end

	// ==========================================================================

	/**
	 * 检查保全项目是否重复
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean isRepeatEdorItem(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.isRepeatEdorItem() 开始");

		// 查询 LPGrpEdorItem
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(tLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorType(tLPGrpEdorItemSchema.getEdorType());
		int nItemsCount = 0;
		try {
			nItemsCount = tLPGrpEdorItemDB.getCount();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单保全项目明细表发生异常！");
			return false;
		}
		if (nItemsCount > 0) {
			logger.debug("\t@> AddGEdorItemCheckBL.isRepeatEdorItem() : 保全项目 "
							+ tLPGrpEdorItemSchema.getEdorType() + " 已经存在！");
			CError.buildErr(this, "该保全项目已经添加过。不允许重复添加！");
			return true;
		}
		tLPGrpEdorItemDB = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.isRepeatEdorItem() 结束");
		return false;
	} // function isRepeatEdorItem end

	// ==========================================================================

	/**
	 * 一次保全申请下只能添加一个保全项目
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean isOnlyOneItem(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.isOnlyOneItem() 开始");

		// 查询 LPGrpEdorItem
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		String QuerySQL = new String("");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		QuerySQL = "select * from LPGrpEdorItem " + "where 1 = 1 "
				+ "and EdorAcceptNo = '"
				+ "?EdorAcceptNo?" + "' "
				+ "and EdorType <> '" + "?EdorType?"
				+ "'";
		// logger.debug(QuerySQL);
		sqlbv.sql(QuerySQL);
		sqlbv.put("EdorAcceptNo", tLPGrpEdorItemSchema.getEdorAcceptNo());
		sqlbv.put("EdorType", tLPGrpEdorItemSchema.getEdorType());
		try {
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单保全项目明细表发生异常！");
			return false;
		}
		if (tLPGrpEdorItemSet != null && tLPGrpEdorItemSet.size() > 0) {
			logger.debug("\t@> AddGEdorItemCheckBL.isOnlyOneItem() : 保全受理号 "
							+ tLPGrpEdorItemSchema.getEdorAcceptNo()
							+ " 下已经存在另外的保全项目！");
			CError.buildErr(this, "一次保全申请只能受理一个保全项目！");
			return false;
		}
		tLPGrpEdorItemDB = null;
		tLPGrpEdorItemSet = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.isOnlyOneItem() 结束");
		return true;
	} // function isOnlyOneItem end

	// ==========================================================================

	/**
	 * 执行团单保全项目校验规则
	 * 
	 * @param LPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean chkGEdorItemRule(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorItemRule() 开始");

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String QuerySQL = new String("");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		QuerySQL = "select distinct CalCode, Msg, SerialNo "
				+ "from LMCheckField " + "where 1 = 1 "
				+ "and trim(RiskCode) in (select distinct trim(RiskCode) "
				+ "from LCGrpPol " + "where GrpContNo = '"
				+ "?GrpContNo?" + "' " + "union "
				+ "select '000000' from dual) " + "and FieldName = '?FieldName?' " // GXXInsert
				+ "order by char_length(trim(SerialNo)), SerialNo";
		logger.debug(QuerySQL);
		sqlbv.sql(QuerySQL);
		sqlbv.put("GrpContNo", tLPGrpEdorItemSchema.getGrpContNo());
		sqlbv.put("FieldName", "G"+tLPGrpEdorItemSchema.getEdorType()+"Insert");
		
		try {
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单保全项目校验规则发生异常！");
			return false;
		}
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			// 增加计算要素
			Calculator tCalculator = new Calculator();
			tCalculator.addBasicFactor("EdorType", tLPGrpEdorItemSchema
					.getEdorType());
			tCalculator.addBasicFactor("GrpContNo", tLPGrpEdorItemSchema
					.getGrpContNo());
			tCalculator.addBasicFactor("EdorAppDate", tLPGrpEdorItemSchema
					.getEdorAppDate());
			tCalculator.addBasicFactor("CURValidate", sCurrentDate);
			tCalculator.addBasicFactor("EdorTypeCal", tLPGrpEdorItemSchema.getEdorTypeCal());
			// 执行校验规则
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String sCalCode = tSSRS.GetText(i, 1);
				String sErrorMsg = tSSRS.GetText(i, 2);
				String sCalResult = new String("");
				if (sCalCode == null || sCalCode.trim().equals("")) {
					logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorItemRule() : 团单合同 : "
									+ tLPGrpEdorItemSchema.getGrpContNo()
									+ " 校验失败！");
					logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorItemRule() : 计算要素 : CalCode  不能为空！");
				} else {
					tCalculator.setCalCode(sCalCode);
					try {
						sCalResult = tCalculator.calculate();
					} catch (Exception ex) {
						CError.buildErr(this, "执行团单保全项目校验规则发生异常！");
						return false;
					}
					if (sCalResult == null || !sCalResult.equals("1")) {
						logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorItemRule() : 团单合同 : "
										+ tLPGrpEdorItemSchema.getGrpContNo()
										+ " 校验失败！");
						logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorItemRule() : 校验规则 : "
										+ sCalCode + " 校验失败！");
						CError.buildErr(this, sErrorMsg);
						return false;
					}
				}
			} // end for
			tCalculator = null;
		}
		tExeSQL = null;
		tSSRS = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorItemRule() 结束");
		return true;
	} // function chkGEdorItemRule end

	// ==========================================================================

	/**
	 * 校验是否允许添加团单险种
	 * 
	 * @param LPGrpPolSchema
	 * @return boolean
	 */
	public boolean canAddGEdorRisk(LPGrpPolSchema tLPGrpPolSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorRisk() 开始");

		if (!isGrpPolExists(tLPGrpPolSchema))
			return false;
		if (!chkRiskEdorItem(tLPGrpPolSchema))
			return false;
		if (!chkGEdorRiskRule(tLPGrpPolSchema))
			return false;

		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorRisk() 结束");
		return true;
	} // function canAddGEdorRisk end

	// ==========================================================================

	/**
	 * 校验是否允许添加团单险种
	 * 
	 * @param LPGrpPolSet
	 * @return boolean
	 */
	public boolean canAddGEdorRisk(LPGrpPolSet tLPGrpPolSet) {
		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorRisk() 开始");

		if (tLPGrpPolSet == null || tLPGrpPolSet.size() <= 0) {
			CError.buildErr(this, "无法获取保全项目险种信息。团单保全险种校验失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tLPGrpPolSchema = tLPGrpPolSet.get(i);
			if (!isGrpPolExists(tLPGrpPolSchema))
				return false;
			if (!chkRiskEdorItem(tLPGrpPolSchema))
				return false;
			if (!chkGEdorRiskRule(tLPGrpPolSchema))
				return false;
			tLPGrpPolSchema = null;
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.canAddGEdorRisk() 结束");
		return true;
	} // function canAddGEdorRisk end

	// ==========================================================================

	/**
	 * 检查团单险种是否存在
	 * 
	 * @param LPGrpPolSchema
	 * @return boolean
	 */
	private boolean isGrpPolExists(LPGrpPolSchema tLPGrpPolSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.isGrpPolExists() 开始");

		if (tLPGrpPolSchema == null) {
			CError.buildErr(this, "无法获取保全险种信息。检查团单险种是否存在失败！");
			return false;
		} else {
			String sGrpPolNo = tLPGrpPolSchema.getGrpPolNo();
			if (sGrpPolNo == null || sGrpPolNo.trim().equals("")) {
				CError.buildErr(this, "无法获取团单险种号。检查团单险种是否存在失败！");
				return false;
			} else {
				// 查询 LCGrpPol
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				tLCGrpPolDB.setGrpPolNo(sGrpPolNo);
				LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
				try {
					tLCGrpPolSet = tLCGrpPolDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询新契约团单险种表发生异常！");
					return false;
				}
				if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
					logger.debug("\t@> AddGEdorItemCheckBL.isGrpPolExists() : 团单险种号 "
									+ sGrpPolNo + " 不存在！");
					CError.buildErr(this, "在新契约团单险种表找不到该团单号！");
					return false;
				}
				tLCGrpPolDB = null;
				tLCGrpPolSet = null;
			}
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.isGrpPolExists() 结束");
		return true;
	} // function isGrpPolExists end

	// ==========================================================================

	/**
	 * 校验险种是否允许添加保全项目
	 * 
	 * @param LPGrpPolSchema
	 * @return boolean
	 */
	private boolean chkRiskEdorItem(LPGrpPolSchema tLPGrpPolSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkRiskEdorItem() 开始");

		// 查询 LMRiskEdorItem
		LMRiskEdorItemDB tLMRiskEdorItemDB = new LMRiskEdorItemDB();
		tLMRiskEdorItemDB.setRiskCode(tLPGrpPolSchema.getRiskCode());
		tLMRiskEdorItemDB.setEdorCode(tLPGrpPolSchema.getEdorType());
		LMRiskEdorItemSet tLMRiskEdorItemSet = new LMRiskEdorItemSet();
		try {
			tLMRiskEdorItemSet = tLMRiskEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全险种项目配置表发生异常！");
			return false;
		}
		if (tLMRiskEdorItemSet == null || tLMRiskEdorItemSet.size() <= 0) {
			logger.debug("\t@> AddGEdorItemCheckBL.chkRiskEdorItem() : 险种 "
							+ tLPGrpPolSchema.getRiskCode() + " 不存在！");
			logger.debug("\t@> AddGEdorItemCheckBL.chkRiskEdorItem() : 保全项目 "
							+ tLPGrpPolSchema.getEdorType() + " 不存在！");
			CError.buildErr(this, "该险种不允许添加此保全项目！");
			return false;
		}
		tLMRiskEdorItemDB = null;
		tLMRiskEdorItemSet = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.chkRiskEdorItem() 结束");
		return true;
	} // function chkRiskEdorItem end

	// ==========================================================================

	/**
	 * 执行团单保全险种校验规则
	 * 
	 * @param LPGrpPolSchema
	 * @return boolean
	 */
	private boolean chkGEdorRiskRule(LPGrpPolSchema tLPGrpPolSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorRiskRule() 开始");

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String QuerySQL = new String("");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		QuerySQL = "select distinct CalCode, Msg, SerialNo "
				+ "from LMCheckField " + "where 1 = 1 "
				+ "and trim(RiskCode) in (select distinct trim(RiskCode) "
				+ "from LCGrpPol " + "where GrpPolNo = '"
				+ "?GrpPolNo?" + "' " + "union "
				+ "select '000000' from dual) " + "and FieldName = '?FieldName?' " // GRXXInsert
				+ "order by char_length(trim(SerialNo)), SerialNo";
		logger.debug(QuerySQL);
		sqlbv.sql(QuerySQL);
		sqlbv.put("GrpPolNo", tLPGrpPolSchema.getGrpPolNo());
		sqlbv.put("FieldName", "GR"+tLPGrpPolSchema.getEdorType()+"Insert");
		try {
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单保全险种校验规则发生异常！");
			return false;
		}
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			// 查询险种编码
			QuerySQL = "select RiskCode from LCGrpPol where GrpPolNo = '"
					+ "?GrpPolNo?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(QuerySQL);
			sqlbv.put("GrpPolNo", tLPGrpPolSchema.getGrpPolNo());
			String sRiskCode = tExeSQL.getOneValue(sqlbv);
			if (sRiskCode == null)
				sRiskCode = "";
			// 增加计算要素
			Calculator tCalculator = new Calculator();
			tCalculator.addBasicFactor("EdorNo", tLPGrpPolSchema.getEdorNo());
			tCalculator.addBasicFactor("EdorType", tLPGrpPolSchema
					.getEdorType());
			tCalculator.addBasicFactor("GrpContNo", tLPGrpPolSchema
					.getGrpContNo());
			tCalculator.addBasicFactor("GrpPolNo", tLPGrpPolSchema
					.getGrpPolNo());
			tCalculator.addBasicFactor("RiskCode", sRiskCode);
			tCalculator.addBasicFactor("CURValidate", sCurrentDate);
			// 执行校验规则
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String sCalCode = tSSRS.GetText(i, 1);
				String sErrorMsg = tSSRS.GetText(i, 2);
				String sCalResult = new String("");
				if (sCalCode == null || sCalCode.trim().equals("")) {
					logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorRiskRule() : 险种编码 : "
									+ sRiskCode + " 校验失败！");
					logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorRiskRule() : 计算要素 : CalCode  不能为空！");
				} else {
					tCalculator.setCalCode(sCalCode);
					try {
						sCalResult = tCalculator.calculate();
					} catch (Exception ex) {
						CError.buildErr(this, "执行团单保全险种校验规则发生异常！");
						return false;
					}
					if (sCalResult == null || !sCalResult.equals("1")) {
						logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorRiskRule() : 险种编码 : "
										+ sRiskCode + " 校验失败！");
						logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorRiskRule() : 校验规则 : "
										+ sCalCode + " 校验失败！");
						CError.buildErr(this, sErrorMsg);
						return false;
					}
				}
			} // end for
			tCalculator = null;
		}
		tExeSQL = null;
		tSSRS = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.chkGEdorRiskRule() 结束");
		return true;
	} // function chkGEdorRiskRule end

	// ==========================================================================

	/**
	 * 校验是否允许添加团单下的个单
	 * 
	 * @param LPEdorItemSchema
	 * @return boolean
	 */
	public boolean canAddPEdorItem(LPEdorItemSchema tLPEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.canAddPEdorItem() 开始");

		if (!isPContExists(tLPEdorItemSchema))
			return false;
		if (!chkPEdorItemRule(tLPEdorItemSchema))
			return false;

		// logger.debug("\t@> AddGEdorItemCheckBL.canAddPEdorItem() 结束");
		return true;
	} // function canAddPEdorItem end

	// ==========================================================================

	/**
	 * 校验是否允许添加团单下的个单
	 * 
	 * @param LPEdorItemSet
	 * @return boolean
	 */
	public boolean canAddPEdorItem(LPEdorItemSet tLPEdorItemSet) {
		// logger.debug("\t@> AddGEdorItemCheckBL.canAddPEdorItem() 开始");

		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "无法获取分单保全项目信息。团单保全分单校验失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			if (!isPContExists(tLPEdorItemSchema))
				return false;
			if (!chkPEdorItemRule(tLPEdorItemSchema))
				return false;
			tLPEdorItemSchema = null;
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.canAddPEdorItem() 结束");
		return true;
	} // function canAddPEdorItem end

	// ==========================================================================

	/**
	 * 检查分单合同是否存在
	 * 
	 * @param LPEdorItemSchema
	 * @return boolean
	 */
	private boolean isPContExists(LPEdorItemSchema tLPEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.isPContExists() 开始");

		if (tLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取保全项目信息。检查分单合同是否存在失败！");
			return false;
		} else {
			String sContNo = tLPEdorItemSchema.getContNo();
			if (sContNo == null || sContNo.trim().equals("")) {
				CError.buildErr(this, "无法获取分单合同号。检查分单合同是否存在失败！");
				return false;
			} else {
				// 查询 LCCont
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(sContNo);
				LCContSet tLCContSet = new LCContSet();
				try {
					tLCContSet = tLCContDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询新契约个单合同表发生异常！");
					return false;
				}
				if (tLCContSet == null || tLCContSet.size() <= 0) {
					logger.debug("\t@> AddGEdorItemCheckBL.isPContExists() : 个单合同号 "
									+ sContNo + " 不存在！");
					CError.buildErr(this, "在新契约个单合同表找不到该个单号！");
					return false;
				}
				tLCContDB = null;
				tLCContSet = null;
			}
		}

		// logger.debug("\t@> AddGEdorItemCheckBL.isPContExists() 结束");
		return true;
	} // function isPContExists end

	// ==========================================================================

	/**
	 * 执行个单保全项目校验规则
	 * 
	 * @param LPEdorItemSchema
	 * @return boolean
	 */
	private boolean chkPEdorItemRule(LPEdorItemSchema tLPEdorItemSchema) {
		// logger.debug("\t@> AddGEdorItemCheckBL.chkPEdorItemRule() 开始");

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String QuerySQL = new String("");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		QuerySQL = "select distinct CalCode, Msg, SerialNo "
				+ "from LMCheckField " + "where 1 = 1 "
				+ "and trim(RiskCode) in (select distinct trim(RiskCode) "
				+ "from LCPol " + "where ContNo = '"
				+ "?ContNo?" + "' " + "union "
				+ "select '000000' from dual) " + "and FieldName = '?FieldName?' " // GPXXInsert
				+ "order by char_length(trim(SerialNo)), SerialNo";
		logger.debug(QuerySQL);
		sqlbv.sql(QuerySQL);
		sqlbv.put("ContNo", tLPEdorItemSchema.getContNo());
		sqlbv.put("FieldName", "GP"+tLPEdorItemSchema.getEdorType()+"Insert");
		try {
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询个单保全项目校验规则发生异常！");
			return false;
		}
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			// 增加计算要素
			Calculator tCalculator = new Calculator();
			tCalculator.addBasicFactor("EdorNo", tLPEdorItemSchema.getEdorNo());
			tCalculator.addBasicFactor("EdorType", tLPEdorItemSchema
					.getEdorType());
			tCalculator.addBasicFactor("GrpContNo", tLPEdorItemSchema
					.getGrpContNo());
			tCalculator.addBasicFactor("ContNo", tLPEdorItemSchema.getContNo());
			tCalculator.addBasicFactor("PolNo", tLPEdorItemSchema.getPolNo());
			tCalculator.addBasicFactor("InsuredNo", tLPEdorItemSchema
					.getInsuredNo());
			tCalculator.addBasicFactor("EdorAppDate", tLPEdorItemSchema
					.getEdorAppDate());
			tCalculator.addBasicFactor("CURValidate", sCurrentDate);
			// 执行校验规则
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String sCalCode = tSSRS.GetText(i, 1);
				String sErrorMsg = tSSRS.GetText(i, 2);
				String sCalResult = new String("");
				if (sCalCode == null || sCalCode.trim().equals("")) {
					logger.debug("\t@> AddGEdorItemCheckBL.chkPEdorItemRule() : 个人合同 : "
									+ tLPEdorItemSchema.getContNo() + " 校验失败！");
					logger.debug("\t@> AddGEdorItemCheckBL.chkPEdorItemRule() : 计算要素 : CalCode  不能为空！");
				} else {
					tCalculator.setCalCode(sCalCode);
					try {
						sCalResult = tCalculator.calculate();
					} catch (Exception ex) {
						CError.buildErr(this, "执行个单保全项目校验规则发生异常！");
						return false;
					}
					if (sCalResult == null || !sCalResult.equals("1")) {
						logger.debug("\t@> AddGEdorItemCheckBL.chkPEdorItemRule() : 个人合同 : "
										+ tLPEdorItemSchema.getContNo()
										+ " 校验失败！");
						logger.debug("\t@> AddGEdorItemCheckBL.chkPEdorItemRule() : 校验规则 : "
										+ sCalCode + " 校验失败！");
						CError.buildErr(this, sErrorMsg);
						return false;
					}
				}
			} // end for
			tCalculator = null;
		}
		tExeSQL = null;
		tSSRS = null;

		// logger.debug("\t@> AddGEdorItemCheckBL.chkPEdorItemRule() 结束");
		return true;
	} // function chkPEdorItemRule end

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
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// AddGEdorItemCheckBL tAddGEdorItemCheckBL = new AddGEdorItemCheckBL();
	// } //function main end
	// ==========================================================================

} // class AddGEdorItemCheckBL end
