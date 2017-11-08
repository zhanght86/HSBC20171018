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
 * @version  : 1.00
 * @date     : 2006-04-25
 * @direction: 保全公用数据校验
 * @comment  : 发工资的日子, 但愿每天都是 25 号 :)
 ******************************************************************************/


import java.util.Vector;

import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;

// ******************************************************************************

public class EdorVerifyBL {
private static Logger logger = Logger.getLogger(EdorVerifyBL.class);

	// public EdorVerifyBL() {}

	// ==========================================================================

	private static final String THIS_FILE = "EdorVerifyBL";
	private static final String OUTPUTMSG = "\t@> " + THIS_FILE + " : ";

	// ==========================================================================

	/**
	 * 判断保全保单是否属于犹豫期退保
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isRefundInBoggle(String sEdorAcceptNo) {
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			logger.debug(OUTPUTMSG + "isRefundInBoggle : 参数保全受理号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select 'X' " + "from LPEdorItem " + "where 1 = 1 "
				+ "and EdorAcceptNo = '" + "?sEdorAcceptNo?" + "' "
				+ "and EdorType in ('CT', 'PT', 'XT', 'EA') "
				+ "and StandByFlag1 = '1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		// logger.debug(OUTPUTMSG + QuerySQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			logger.debug(OUTPUTMSG + "isRefundInBoggle : 执行 SQL 查询异常！");
			return false;
		}
		tExeSQL = null;
		if (tSSRS != null && tSSRS.getMaxRow() > 0)
			return true;
		else
			return false;

	} // function isRefundInBoggle end

	// ==========================================================================

	/**
	 * 获取保全保单的首期交费方式
	 * 
	 * @param String
	 * @return SSRS
	 */
	public static SSRS getFirstPayMode(String sEdorAcceptNo) {
		SSRS tResultSSRS = new SSRS();

		// ----------------------------------------------------------------------

		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			logger.debug(OUTPUTMSG + "getFirstPayMode : 参数保全受理号不能为空！");
			return null;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select distinct PayMode " + "from LJTempFeeClass "
				+ "where 1 = 1 " + "and TempFeeNo in "
				+ "(select distinct TempFeeNo " + "from LJTempFee "
				+ "where 1 = 1 " + "and TempFeeType = '1' " + "and OtherNo = "
				+ "(select OtherNo " + "from LPEdorApp "
				+ "where EdorAcceptNo = '" + "?sEdorAcceptNo?" + "'))";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		// logger.debug(OUTPUTMSG + QuerySQL);
		ExeSQL tExeSQL = new ExeSQL();
		try {
			tResultSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			logger.debug(OUTPUTMSG + "getFirstPayMode : 执行 SQL 查询异常！");
			return null;
		}
		tExeSQL = null;
		// ----------------------------------------------------------------------

		return tResultSSRS;
	} // function getFirstPayMode end

	// ==========================================================================

	/**
	 * 检查保全保单的犹豫期退保的支票退费方式
	 * 
	 * @param String
	 * @return String
	 */
	public static String getRefundGetForm(String sEdorAcceptNo) {
		String sResultValue = new String("");

		// ----------------------------------------------------------------------

		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			logger.debug(OUTPUTMSG + "getRefundGetForm : 参数保全受理号不能为空！");
			return null;
		}

		// ----------------------------------------------------------------------

		if (!isRefundInBoggle(sEdorAcceptNo)) {
			logger.debug(OUTPUTMSG + "getRefundGetForm : 该保全保单不属于犹豫期退保！");
			return null;
		}

		SSRS tSSRS = new SSRS();
		tSSRS = getFirstPayMode(sEdorAcceptNo);
		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			logger.debug(OUTPUTMSG
					+ "getRefundGetForm : 获取保全保单的首期交费方式失败！");
			return null;
		} else {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String sTempMode = tSSRS.GetText(i, 1);
				if (sTempMode != null) {
					sTempMode = sTempMode.trim();
					// 首期缴费形式如果存在3-支票或4-转账支票(非银行划款)则都为3-支票
					if (sTempMode.equals("3") || sTempMode.equals("4")) {
						sResultValue = "3";
						break;
					}
				}
			} // end loop
		}
		tSSRS = null;

		// ----------------------------------------------------------------------

		return sResultValue;
	} // function getRefundGetForm end

	// ==========================================================================

	/**
	 * 获取保全保单的和首期匹配的退费方式
	 * 
	 * @param String
	 * @return String
	 */
	public static String getExactGetForm(String sEdorAcceptNo) {
		String sResultValue = new String("");

		// ----------------------------------------------------------------------

		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			logger.debug(OUTPUTMSG + "getExactGetForm : 参数保全受理号不能为空！");
			return null;
		}

		// ----------------------------------------------------------------------

		SSRS tSSRS = new SSRS();
		tSSRS = getFirstPayMode(sEdorAcceptNo);
		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			logger.debug(OUTPUTMSG + "getExactGetForm : 获取保全保单的首期交费方式失败！");
			return null;
		} else
			sResultValue = PubFun.PayModeTransform(tSSRS);

		// ----------------------------------------------------------------------

		return sResultValue;
	} // function getExactGetForm end

	// ==========================================================================

	/**
	 * 保全相关数据的校验（使用LMEdorCal）
	 * 
	 * @param aTransferData
	 *            校验所需要素
	 * @param aCalType
	 *            校验的类型，LMEdorCal中使用
	 * @param aEdorType
	 *            保全项目
	 * @param aRiskCode
	 *            险种代码
	 * @return
	 */
	public static boolean EdorCheckData(TransferData aTransferData,
			String aCalType, String aEdorType, String aRiskCode) {
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
		tLMEdorCalDB.setCalType(aCalType);
		tLMEdorCalDB.setEdorType(aEdorType);

		if (aRiskCode == null || aRiskCode.equals(""))
			tLMEdorCalDB.setRiskCode("000000");
		else
			tLMEdorCalDB.setRiskCode(aRiskCode);

		tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			return false;
		}

		Vector tVector = new Vector();
		tVector = aTransferData.getValueNames();
		if (tVector == null || tVector.size() == 0) {
			return false;
		}

		for (int i = 1; i <= tLMEdorCalSet.size(); i++) {
			Calculator tCalculator = new Calculator();
			for (int j = 0; j < tVector.size(); j++) {
				String tFactorName = new String("");
				String tFactorValue = new String("");
				tFactorName = (String) tVector.get(j);
				tFactorValue = (String) aTransferData
						.getValueByName(tFactorName);
				tCalculator.addBasicFactor(tFactorName, tFactorValue);
			}
			tCalculator.setCalCode(tLMEdorCalSet.get(i).getCalCode());
			String tValue = tCalculator.calculate();
			if (!"1".equals(tValue)) {
				return false;
			}
			tCalculator = null;
		}
		tLMEdorCalSet = null;
		return true;
	}

	/**
	 * 获取某保全项目的利息结算方式
	 * 
	 * @param sEdorType
	 * @return String D-按日结息 M-按月结息
	 */
	public static String getInterestCalType(String sEdorType) {
		String sql = " select trim(codename) from ldcode where codetype = 'InterestCalType' and code = '"
				+ "?sEdorType?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sEdorType", sEdorType);
		ExeSQL tExeSQL = new ExeSQL();
		String sInterestCalType = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			return "M"; // 默认值按月结算
		}
		if (sInterestCalType == null || sInterestCalType.equals("")) {
			sInterestCalType = "M"; // 默认值按月结算
		}
		return sInterestCalType;
	}

	/**
	 * 获取某保全项目的利率取值方式
	 * 
	 * @param sEdorType
	 * @return String D-动态 S-静态
	 */
	public static String getInterestRateType(String sEdorType) {
		String sql = " select trim(codename) from ldcode where codetype = 'InterestRateType' and code = '"
				+ "?sEdorType?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sEdorType", sEdorType);
		ExeSQL tExeSQL = new ExeSQL();
		String sInterestCalType = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			return "S"; // 默认值静态利率
		}
		if (sInterestCalType == null || sInterestCalType.equals("")) {
			sInterestCalType = "S"; // 默认值静态利率
		}
		return sInterestCalType;
	}
	// ==========================================================================


} // class EdorVerifyBL end
