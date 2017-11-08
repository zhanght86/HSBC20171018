/*
 * @(#)EdorVTSParser.java	2006-05-18
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPParseGuideDataSchema;
import com.sinosoft.lis.vschema.LPParseGuideDataSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 解析磁盘导入文件存入临时表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @date 2006-05-18
 */
public class EdorVTSParser {
private static Logger logger = Logger.getLogger(EdorVTSParser.class);

	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();

	// 用户登陆信息
	private GlobalInput mGlobalInput = new GlobalInput();

	// 保全批改信息
	private String mEdorAcceptNo = "";
	private String mGrpContNo = ""; // 团单号 add by liuxiaosong 2006-12-11
	private String mEdorType = "";

	// 导入文件信息
	private String m_strFileName = "";
	private int m_SheetCount;
	private int[] m_Sheet_ColCount = null; // 每页最大列数

	// ======add======liuxiaosong===========2006-12-12============================start=============
	// LPParseGuideFieldMap中特殊字段对应的列编号边界值
	private int[] m_Sheet_ContNoColCount = null; // 个单号
	private int[] m_Sheet_InsuredNoColCount = null; // 被保人客户号
	private int[] m_Sheet_PolNoColCount = null; // 险种号
	// ======add======liuxiaosong===========2006-12-12============================end===============
	// 一个文件中处理的行数
	private static int ROW_LIMIT = 1000;

	private BookModelImpl m_book = new BookModelImpl();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	/**
	 * 构造函数
	 */
	public EdorVTSParser(String strFileName, GlobalInput pGlobalInput) {
		m_strFileName = strFileName;
		mGlobalInput = pGlobalInput;
	}

	/**
	 * 解析磁盘文件
	 * 
	 * @return boolean
	 */
	public boolean transform() {
		// 初始化解析信息并与配置信息做一致性比较
		if (!initFileInfo()) {
			return false;
		}
		int iMaxRow;

		// 准备临时数据表
		LPParseGuideDataSet instLPParseGuideDataSet = new LPParseGuideDataSet();
		LPParseGuideDataSchema tLPParseGuideDataSchema;
		String sValue; // 单元格值
		String sIndexNo; // 页号
		// ===========add===========liuxiaosong=============2006-12-11===========start=========
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tCustomerSeqNo = ""; // 客户序号
		String tRiskCode = ""; // 险种号
		String tContNo = ""; // 查询得到客户号
		String tPolNo = ""; // 查询得到险种号
		String tInsuredNo = ""; // 被保人客户号
		String tOldCustomerName = ""; // 所填写的原员工序号，容错处理
		// String tFilledName = ""; //模板中填写的姓名；
		// ===========add==========liuxiaosong=============2006-12-11=============end=========

		for (int iSheet = 0; iSheet < m_SheetCount; iSheet++) // 逐Sheet
		{
			iMaxRow = getMaxRow(iSheet); // 导入文件第iSheet页的行数
			if (iMaxRow == -1) {
				// 错误信息getMaxRow()中已处理
				return false;
			}

			for (int iRow = 1; iRow < iMaxRow; iRow++) // 逐行
			{
				try {
					// 从导入文件相应sheet的第一列中获得业务单元序列号
					sIndexNo = m_book.getText(iSheet, iRow, 0);
				} catch (Exception ex) {
					CError tError = new CError();
					tError.moduleName = "EdorVTSParser";
					tError.functionName = "transform";
					tError.errorMessage = "数据读取错误!";
					mErrors.addOneError(tError);
					return false;
				}

				for (int iCol = 1; iCol < m_Sheet_ColCount[iSheet]; iCol++) // 逐列
				{
					try {
						// 逐单元格获取数据
						sValue = m_book.getText(iSheet, iRow, iCol);
						// ========add========liuxiaosong======2006-12-11===================start==============
						// 模板第一列为业务处理单位序列号，第二为客户序号，第三为险种编码
						if (iCol == 1) {
							tCustomerSeqNo = sValue;
						}
						if (iCol == 3) {
							tRiskCode = sValue;
						}
						// ========add========liuxiaosong======2006-12-11===================end==============

						// ========add========liuxiaosong======2006-12-22===================start==============
						// 容错处理，查看是否所填写的员工姓名与系统中的一致
						// if (iCol == 2)
						// {
						// tFilledName = sValue;
						// }

						// ========add========liuxiaosong======2006-12-22===================end==============
					} catch (Exception ex) {
						CError tError = new CError();
						tError.moduleName = "EdorVTSParser";
						tError.functionName = "transform";
						tError.errorMessage = "数据读取错误!";
						mErrors.addOneError(tError);

						return false;
					}

					// 将读取的数据存入临时数据表中
					// advise:该动作与解析重组临时数据动作可以省略，而在此逐行直接根据配置表重组数据
					// 数据量大时，LPParseGuideDataSet的容量将很惊人
					// 而且直接处理会使对错误处理更集中更容易
					tLPParseGuideDataSchema = new LPParseGuideDataSchema();
					tLPParseGuideDataSchema.setEdorAcceptNo(mEdorAcceptNo);
					tLPParseGuideDataSchema.setEdorType(mEdorType);
					tLPParseGuideDataSchema.setSheetNo(iSheet);
					tLPParseGuideDataSchema.setIndexNo(sIndexNo);
					tLPParseGuideDataSchema.setRowNo(iRow);
					tLPParseGuideDataSchema.setColNo(iCol);
					tLPParseGuideDataSchema.setValue(sValue);
					tLPParseGuideDataSchema.setOperator(mGlobalInput.Operator);
					tLPParseGuideDataSchema.setMakeDate(mCurrentDate);
					tLPParseGuideDataSchema.setMakeTime(mCurrentTime);
					tLPParseGuideDataSchema.setModifyDate(mCurrentDate);
					tLPParseGuideDataSchema.setModifyTime(mCurrentTime);
					instLPParseGuideDataSet.add(tLPParseGuideDataSchema);
				}
				// ====add=========liuxiaosong==============2006-12-11========start===================
				// 约定：模板第一列为业务处理单位序列号，第二为客户序号，第三为险种编码
				// 特殊字段值的查询条件从模板第一二列获得，团单号依据保全受理号查得

				// Q: 1.如果另立菜单，团单号如何获得？
				// 2.负编码给contno，对于polno如何对付,customerNO呢？如果以后随着业务增加，某个特殊项目需要特殊字段，又如何处理？
				// 可否采用前缀编码方式？-1000等，以后如果需要特殊字段，则只需要更改本类程序，并在描述表里新添加编码描述。这样
				// colno不再单纯是代表excel文件的列号，而是字段编码
				// 3.在数据重组时或后台处理时会有错误：有些Schema没有setCustomerSeqNo();
				// A: 1.依据保全受理号查询
				// 2.采用编码制，为不影响模板定制，后台程序处理的字段对应的colno一律为负，按不同的字段分别进行编码
				// contno:-1001
				// polno: -2001
				// customerno:-3001
				// ISDiskImport:-4001
				// 其他特殊字段依次类推，标准存放到哪里便于以后管理？LDCODE？
				// 3.将LPParseGuideFieldMap中对应的TableName字段改为TransferData

				// 查询保单号、险种号、被保人客户号、姓名-----------------------------
				try {
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					tSQL = " select p.contno, p.polno, p.insuredno,c.name "
							+ " from lcpol p,lcinsured c" + " where 1=1"
							+ " and c.grpcontno = '" + "?mGrpContNo?" + "'"
							+ " and c.customerseqno = '" + "?tCustomerSeqNo?" + "'"
							+ " and p.contno = c.contno";

					sqlbv.put("mGrpContNo", mGrpContNo);
					sqlbv.put("tCustomerSeqNo", tCustomerSeqNo);
					if (!"".equals(tRiskCode)) {
						tSQL = tSQL + " and p.riskcode = '" + "?tRiskCode?" + "'";
						sqlbv.put("tRiskCode", tRiskCode);
					}
					sqlbv.sql(tSQL);
					tSSRS = tExeSQL.execSQL(sqlbv);
					tContNo = tSSRS.GetText(1, 1); // 保单号
					tPolNo = tSSRS.GetText(1, 2); // 险种号
					tInsuredNo = tSSRS.GetText(1, 3); // 客户号
					tOldCustomerName = tSSRS.GetText(1, 4); // 客户姓名
				} catch (Exception ex) {
					CError tError = new CError();
					tError.moduleName = "EdorVTSParser";
					tError.functionName = "transform";
					tError.errorMessage = "不能取得团单号和险种号!";
					mErrors.addOneError(tError);
					return false;
				}
				// -------------------------------------------------------------
				// 容错处理，以原员工姓名检验是否填写正确
				// 在edordiskimport.java中统一容错
				// if (!tOldCustomerName.equals(tFilledName))
				// {
				// CError tError = new CError();
				// tError.moduleName = "EdorVTSParser";
				// tError.functionName = "transform";
				// tError.errorMessage = "填写的被保人姓名错误，请重新核对!";
				// mErrors.addOneError(tError);
				// return false;
				// }
				// --------------------------------------------------------------
				// 统一对各个需要的特殊字段进行赋值，注意数据的编码最大值
				// contno:-1001
				// polno:-2001
				// InsuredNo:-3001

				// 对需要contno的字段进行赋值---------------------------------------
				for (int iCol = -1001; iCol >= m_Sheet_ContNoColCount[iSheet]; iCol--) {
					// 将查询的数据存入临时数据表中
					tLPParseGuideDataSchema = new LPParseGuideDataSchema();
					tLPParseGuideDataSchema.setEdorAcceptNo(mEdorAcceptNo);
					tLPParseGuideDataSchema.setEdorType(mEdorType);
					tLPParseGuideDataSchema.setSheetNo(iSheet);
					tLPParseGuideDataSchema.setIndexNo(sIndexNo);
					tLPParseGuideDataSchema.setRowNo(iRow);
					tLPParseGuideDataSchema.setColNo(iCol);
					tLPParseGuideDataSchema.setValue(tContNo);
					tLPParseGuideDataSchema.setOperator(mGlobalInput.Operator);
					tLPParseGuideDataSchema.setMakeDate(mCurrentDate);
					tLPParseGuideDataSchema.setMakeTime(mCurrentTime);
					tLPParseGuideDataSchema.setModifyDate(mCurrentDate);
					tLPParseGuideDataSchema.setModifyTime(mCurrentTime);
					instLPParseGuideDataSet.add(tLPParseGuideDataSchema);
				}
				// --------------------------------------------------------------

				// PolNo---------------------------------------------------------
				for (int iCol = -2001; iCol >= m_Sheet_PolNoColCount[iSheet]; iCol--) {
					// 将查询的数据存入临时数据表中
					tLPParseGuideDataSchema = new LPParseGuideDataSchema();
					tLPParseGuideDataSchema.setEdorAcceptNo(mEdorAcceptNo);
					tLPParseGuideDataSchema.setEdorType(mEdorType);
					tLPParseGuideDataSchema.setSheetNo(iSheet);
					tLPParseGuideDataSchema.setIndexNo(sIndexNo);
					tLPParseGuideDataSchema.setRowNo(iRow);
					tLPParseGuideDataSchema.setColNo(iCol);
					tLPParseGuideDataSchema.setValue(tPolNo);
					tLPParseGuideDataSchema.setOperator(mGlobalInput.Operator);
					tLPParseGuideDataSchema.setMakeDate(mCurrentDate);
					tLPParseGuideDataSchema.setMakeTime(mCurrentTime);
					tLPParseGuideDataSchema.setModifyDate(mCurrentDate);
					tLPParseGuideDataSchema.setModifyTime(mCurrentTime);
					instLPParseGuideDataSet.add(tLPParseGuideDataSchema);
				}
				// --------------------------------------------------------------

				// 对需要InsuredNo的字段进行赋值-----------------------------------
				for (int iCol = -3001; iCol >= m_Sheet_InsuredNoColCount[iSheet]; iCol--) {
					// 将查询的数据存入临时数据表中
					tLPParseGuideDataSchema = new LPParseGuideDataSchema();
					tLPParseGuideDataSchema.setEdorAcceptNo(mEdorAcceptNo);
					tLPParseGuideDataSchema.setEdorType(mEdorType);
					tLPParseGuideDataSchema.setSheetNo(iSheet);
					tLPParseGuideDataSchema.setIndexNo(sIndexNo);
					tLPParseGuideDataSchema.setRowNo(iRow);
					tLPParseGuideDataSchema.setColNo(iCol);
					tLPParseGuideDataSchema.setValue(tInsuredNo);
					tLPParseGuideDataSchema.setOperator(mGlobalInput.Operator);
					tLPParseGuideDataSchema.setMakeDate(mCurrentDate);
					tLPParseGuideDataSchema.setMakeTime(mCurrentTime);
					tLPParseGuideDataSchema.setModifyDate(mCurrentDate);
					tLPParseGuideDataSchema.setModifyTime(mCurrentTime);
					instLPParseGuideDataSet.add(tLPParseGuideDataSchema);
				}
				// --------------------------------------------------------------

				// 设置是否批量导入标志,无须循环-------------------------------------
				// 无论各个项目相应的BL需要额外数据补全处理，该字段都存在于描述中
				tLPParseGuideDataSchema = new LPParseGuideDataSchema();
				tLPParseGuideDataSchema.setEdorAcceptNo(mEdorAcceptNo);
				tLPParseGuideDataSchema.setEdorType(mEdorType);
				tLPParseGuideDataSchema.setSheetNo(iSheet);
				tLPParseGuideDataSchema.setIndexNo(sIndexNo);
				tLPParseGuideDataSchema.setRowNo(iRow);
				tLPParseGuideDataSchema.setColNo(-4001);
				tLPParseGuideDataSchema.setValue("yes"); // "yes"
				tLPParseGuideDataSchema.setOperator(mGlobalInput.Operator);
				tLPParseGuideDataSchema.setMakeDate(mCurrentDate);
				tLPParseGuideDataSchema.setMakeTime(mCurrentTime);
				tLPParseGuideDataSchema.setModifyDate(mCurrentDate);
				tLPParseGuideDataSchema.setModifyTime(mCurrentTime);
				instLPParseGuideDataSet.add(tLPParseGuideDataSchema);
				// --------------------------------------------------------------

				// 系统中存储的客户姓名-------------------------------------
				// 便于容错处理
				tLPParseGuideDataSchema = new LPParseGuideDataSchema();
				tLPParseGuideDataSchema.setEdorAcceptNo(mEdorAcceptNo);
				tLPParseGuideDataSchema.setEdorType(mEdorType);
				tLPParseGuideDataSchema.setSheetNo(iSheet);
				tLPParseGuideDataSchema.setIndexNo(sIndexNo);
				tLPParseGuideDataSchema.setRowNo(iRow);
				tLPParseGuideDataSchema.setColNo(-5001);
				tLPParseGuideDataSchema.setValue(tOldCustomerName); // 查询到的客户姓名
				tLPParseGuideDataSchema.setOperator(mGlobalInput.Operator);
				tLPParseGuideDataSchema.setMakeDate(mCurrentDate);
				tLPParseGuideDataSchema.setMakeTime(mCurrentTime);
				tLPParseGuideDataSchema.setModifyDate(mCurrentDate);
				tLPParseGuideDataSchema.setModifyTime(mCurrentTime);
				instLPParseGuideDataSet.add(tLPParseGuideDataSchema);
				// --------------------------------------------------------------

				// ==========add=======liuxiaosong========2006-12-11=================end==================

			}
		}
		logger.debug("\t@>============================临时数据表容量为："
				+ instLPParseGuideDataSet.size());
		map.put(instLPParseGuideDataSet, "INSERT");
		batchPubSubmit(map);
		return true;
	}

	/**
	 * 设置要解析的文件名
	 */
	private boolean initFileInfo() {
		try {
			logger.debug("====导入文件读取====" + m_strFileName);
			m_book.read(m_strFileName, new com.f1j.ss.ReadParams());
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "initFileInfo";
			tError.errorMessage = "无法读取导入文件!";
			mErrors.addOneError(tError);
			return false;
		}
		// 根据文件名提取业务类型及业务号码
		int iStart = m_strFileName.lastIndexOf("/");
		mEdorType = m_strFileName.substring(iStart + 1, iStart + 3);
		mEdorAcceptNo = m_strFileName.substring(iStart + 4, m_strFileName
				.lastIndexOf("_"));
		// ===============add========liuxiaosong===========2006-12-12=======start==============
		// 根据保全受理号获得团单号
		String sql = "select grpcontno from lpgrpedoritem where edoracceptno='"
				+ "?mEdorAcceptNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sqlResult = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError() || sqlResult == null
				|| sqlResult.equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "initFileInfo";
			tError.errorMessage = "保全信息查询失败，请确定已经申请了受理号为" + mEdorAcceptNo
					+ "的保全项目!";
			mErrors.addOneError(tError);
			return false;
		}
		mGrpContNo = sqlResult;
		// ===============add========liuxiaosong===========2006-12-12=======end==============

		// 从配置表中查出该业务类型磁盘文件的Sheet数
		sql = " select max(sheetno) + 1 from Lpparseguidefieldmap "
				+ " where edortype = '" + "?mEdorType?" + "'";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorType", mEdorType);
		tExeSQL = new ExeSQL();
		sqlResult = tExeSQL.getOneValue(sqlbv);

		if (tExeSQL.mErrors.needDealError() || sqlResult == null
				|| sqlResult.equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "initFileInfo";
			tError.errorMessage = "磁盘导入配置信息查询失败，无法获得页数!";
			mErrors.addOneError(tError);
			return false;
		}
		m_SheetCount = Integer.parseInt(sqlResult);

		m_Sheet_ColCount = new int[m_SheetCount]; // 每个sheet的正值列数

		// =====add========liuxiaosong============2006-12-12======================start===========
		// 各个Sheet的各个特殊业务字段的负值列号编码最小值
		m_Sheet_ContNoColCount = new int[m_SheetCount];
		m_Sheet_InsuredNoColCount = new int[m_SheetCount];
		m_Sheet_PolNoColCount = new int[m_SheetCount];
		// =====add========liuxiaosong============2006-12-12=================================

		for (int iSheetNo = 0; iSheetNo < m_SheetCount; iSheetNo++) {
			// 从配置表中查出每个Sheet的正值列数
			sql = " select max(colno) + 1 from Lpparseguidefieldmap "
					+ " where sheetno = " + "?iSheetNo?" + " and edortype = '"
					+ "?mEdorType?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("iSheetNo", iSheetNo);
			sqlbv.put("mEdorType", mEdorType);
			sqlResult = tExeSQL.getOneValue(sqlbv);
			if (tExeSQL.mErrors.needDealError() || sqlResult == null
					|| sqlResult.equals("")) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "磁盘导入配置信息查询失败，无法获得第" + iSheetNo
						+ "页最大列数!";
				mErrors.addOneError(tError);
				return false;
			}
			m_Sheet_ColCount[iSheetNo] = Integer.parseInt(sqlResult);
			// =====add==========liuxiaosong=========2006-12-11===============start=======================
			// 从配置表中查出每个Sheet的各个特殊业务字段的负值列号编码最小值
			getMinusColNo(iSheetNo);
			// =====add==========liuxiaosong=========2006-12-11===============end=======================
		}

		// 从导入文件中读出Sheet数和每个Sheet的列数,与配置信息做一致性比较
		int A_SheetCount = m_book.getNumSheets();
		int[] A_Sheet_ColCount = new int[A_SheetCount]; // 每个sheet的列数
		if (m_SheetCount != A_SheetCount) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "initFileInfo";
			tError.errorMessage = "导入文件不符合规范：与配置信息不匹配!";
			mErrors.addOneError(tError);
			return false;
		}

		int iColCount;

		// 从导入文件中提取每个Sheet的列数
		for (int iSheetNo = 0; iSheetNo < A_SheetCount; iSheetNo++) {

			iColCount = getMaxCol(iSheetNo);
			if (iColCount == -1) {
				// 错误信息在getMaxCol中处理
				return false;
			}
			A_Sheet_ColCount[iSheetNo] = iColCount;
		}

		// 比较导入文件每个sheet的列数是否与配置信息中每个sheet的列数一致
		for (int iSheetNo = 0; iSheetNo < m_SheetCount; iSheetNo++) {
			if (m_Sheet_ColCount[iSheetNo] != A_Sheet_ColCount[iSheetNo]) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "导入文件第" + iSheetNo + "页的列数与配置信息不匹配!";
				mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	// =====add==========liuxiaosong=========2006-12-12===============start=======================
	/**
	 * 从配置表中查出每个Sheet的各个特殊业务字段的负值列数
	 * 
	 * @param iSheetNo
	 *            int
	 * @return boolean
	 */
	private boolean getMinusColNo(int iSheetNo) {
		// 查contno对应的--------------------------------------------------------
		String sql = " select min(colno) from Lpparseguidefieldmap "
				+ " where sheetno = " + "?iSheetNo?" + " and edortype = '"
				+ "?mEdorType?" + "'" + " and fieldname ='ContNo'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("iSheetNo", iSheetNo);
		sqlbv.put("mEdorType", mEdorType);
		ExeSQL tExeSQL = new ExeSQL();
		String sqlResult = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "getMinusColNo";
			tError.errorMessage = "磁盘导入配置信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (sqlResult == null || sqlResult.equals("")) {
			m_Sheet_ContNoColCount[iSheetNo] = 0;
		} else {
			m_Sheet_ContNoColCount[iSheetNo] = Integer.parseInt(sqlResult);
		}
		logger.debug("\t======ContNo最小列号："
				+ m_Sheet_ContNoColCount[iSheetNo]);

		// ----------------------------------------------------------------------

		// 查InsuredNo对应-------------------------------------------------------
		sql = " select min(colno) from Lpparseguidefieldmap "
				+ " where sheetno = " + "?iSheetNo?" + " and edortype = '"
				+ "?mEdorType?" + "'"
				+ " and fieldname in ('CustomerNo','InsuredNo')";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("iSheetNo", iSheetNo);
		sqlbv.put("mEdorType", mEdorType);
		
		sqlResult = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "getMinusColNo";
			tError.errorMessage = "磁盘导入配置信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (sqlResult == null || sqlResult.equals("")) {
			m_Sheet_InsuredNoColCount[iSheetNo] = 0;
		} else {
			m_Sheet_InsuredNoColCount[iSheetNo] = Integer.parseInt(sqlResult);
		}
		logger.debug("\t======InsuredNo最小列号："
				+ m_Sheet_InsuredNoColCount[iSheetNo]);
		// ---------------------------------------------------------------------

		// 查polno对应的值--------------------------------------------------------
		sql = " select min(colno) from Lpparseguidefieldmap "
				+ " where sheetno = " + "?iSheetNo?" + " and edortype = '"
				+ "?mEdorType?" + "'" + " and fieldname ='PolNo'";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("iSheetNo", iSheetNo);
		sqlbv.put("mEdorType", mEdorType);
		sqlResult = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "EdorVTSParser";
			tError.functionName = "getMinusColNo";
			tError.errorMessage = "磁盘导入配置信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (sqlResult == null || sqlResult.equals("")) {
			m_Sheet_PolNoColCount[iSheetNo] = 0;
		} else {
			m_Sheet_PolNoColCount[iSheetNo] = Integer.parseInt(sqlResult);
		}
		logger.debug("\t======PolNo最小列号："
				+ m_Sheet_PolNoColCount[iSheetNo]);
		// ----------------------------------------------------------------------
		return true;
	}

	// =====add==========liuxiaosong=========2006-12-11===============end=======================

	/**
	 * 获取指定sheet的最大行数
	 * 
	 * @param iSheet
	 */
	private int getMaxRow(int iSheet) {
		String sValue = ""; // 遇空行时返回
		int iRow;
		for (iRow = 0; iRow < 1000000; iRow++) {
			try {
				sValue = m_book.getText(iSheet, iRow, 0);
			} catch (Exception ex) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "getMaxRow";
				tError.errorMessage = "无法导入文件第" + iSheet + "页的行数!";

				return -1;
			}
			if (sValue == null || sValue.trim().equals("")) {
				break;
			}
		}

		return iRow;
	}

	/**
	 * 获取指定sheet的最大列数
	 * 
	 * @param iSheet
	 */
	private int getMaxCol(int iSheet) {
		String sValue = ""; // 标题列
		int iCol;
		for (iCol = 0; iCol < 1000000; iCol++) {
			try {
				sValue = m_book.getText(iSheet, 0, iCol);
			} catch (Exception ex) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "getMaxCol";
				tError.errorMessage = "提取导入文件第" + iSheet + "页信息失败！";
				mErrors.addOneError(tError);
				return -1;
			}

			// 遇标题空列时返回
			if (sValue == null || sValue.trim().equals("")) {
				break;
			}
		}

		return iCol;
	}

	/**
	 * 提交数据库
	 * 
	 * @param map
	 */
	private boolean batchPubSubmit(MMap map) {
		PubSubmit pubSubmit = new PubSubmit();
		VData sData = new VData();
		sData.add(map);
		if (!pubSubmit.submitData(sData, "")) {
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		return true;
	}

	// public static void main(String[] args)
	// {
	// GlobalInput tG = new GlobalInput();
	// tG.Operator = "zhangtao";
	// tG.ManageCom = "86110000";
	// String sFileName = "E:\\temp\\edorimport\\IC_6120060518000003_01.xls";
	// EdorVTSParser tEdorVTSParser = new EdorVTSParser(sFileName, tG);
	//
	// String strStartTime = PubFun.getCurrentTime();
	//
	// tEdorVTSParser.transform();
	//
	// String strEndTime = PubFun.getCurrentTime();
	//
	// logger.debug("===============================================");
	// logger.debug(" the start time is : " + strStartTime);
	// logger.debug("===============================================");
	//
	// logger.debug("===============================================");
	// logger.debug(" the end time is : " + strEndTime);
	// logger.debug("===============================================");
	// }
}
