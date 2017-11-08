package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2005-12-05
 * @direction: 保单特殊复效打印
 ******************************************************************************/

// ******************************************************************************

public class LRNSpecialAvailablePrintBL {
private static Logger logger = Logger.getLogger(LRNSpecialAvailablePrintBL.class);
	// public LRNSpecialAvailablePrintBL() { }

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData = new VData();
	private String rOperation = new String();
	// 全局变量
	private GlobalInput rGlobalInput = new GlobalInput();
	private TransferData rTransferData = new TransferData();
	// 输出数据
	private VData rResultData = new VData();
	// FormulaOne 打印模板文件
	public static final String FormulaOneVTS = "LRNSpecialAvailable.vts";

	// ==========================================================================

	/**
	 * submitData(VData cInputData, String cOperate)
	 * 
	 * @param :
	 *            VData
	 * @param :
	 *            String
	 * @return : boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.submitData()
		// 开始");
		// 接受参数
		rInputData = (VData) cInputData.clone();
		rOperation = (cOperate != null) ? cOperate.trim() : "";
		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!printData())
			return false;
		collectGarbage();
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.submitData()
		// 成功");
		return true;
	} // function submitData end

	// ==========================================================================

	private boolean getInputData() {
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.getInputData()
		// 开始");
		// GlobalInput
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> LRNSpecialAvailablePrintBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}
		// TransferData
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取用户查询条件信息！");
			logger.debug("\t@> LRNSpecialAvailablePrintBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.getInputData()
		// 成功");
		return true;
	} // function getInputData end

	// ==========================================================================

	private boolean checkData() {
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.checkData() 开始");
		if (!rOperation.equalsIgnoreCase("Print")) {
			CError.buildErr(this, "未知的操作请求！");
			logger.debug("\t@> LRNSpecialAvailablePrintBL.checkData() : submitData(cInputData, cOperate) 中 cOperate 必须为 \"PRINT\"！");
			return false;
		}
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.checkData() 成功");
		return true;
	} // function checkData end

	// ==========================================================================

	private boolean printData() {
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.printData() 开始");
		// 声明 SQL 变量
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String strSQL = "select a.ContNo, "
				+ "a.RevalidateTimes, "
				+ "concat(concat(a.InvalidReason , '-') , "
				+ "(select CodeName "
				+ "from LDCode "
				+ "where CodeType = 'continvalidreason' and trim(Code) = a.InvalidReason)), "
				+ "(select Name from LDCom where ComCode = a.ManageCom), "
				+ "a.Remark, " + "a.Operator, " + "a.MakeDate, "
				+ "a.MakeTime " + "from LPRevalidateTrack a " + "where 1 = 1 ";
		// 执行 SQL 查询
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		// 附加SQL条件
		if (rTransferData.getValueByName("ContNo") != null
				&& !((String) rTransferData.getValueByName("ContNo")).trim()
						.equals("")){
			strSQL = strSQL + "and a.ContNo = '"
			+ "?ContNo?"
			+ "' ";
			sqlbv1.put("ContNo", ((String) rTransferData.getValueByName("ContNo")).trim());
		}
		if (rTransferData.getValueByName("InvalidReason") != null
				&& !((String) rTransferData.getValueByName("InvalidReason"))
						.trim().equals("")){
			strSQL = strSQL
			+ "and a.InvalidReason = '"
			+ "?InvalidReason?" + "' ";
			sqlbv1.put("InvalidReason", ((String) rTransferData.getValueByName("InvalidReason")).trim());
		}
		if (rTransferData.getValueByName("Operator") != null
				&& !((String) rTransferData.getValueByName("Operator")).trim()
						.equals("")){
			strSQL = strSQL
			+ "and a.Operator = '"
			+ "?Operator?" + "' ";
			sqlbv1.put("Operator", ((String) rTransferData.getValueByName("Operator")).trim());
		}
		if (rTransferData.getValueByName("ManageCom") != null
				&& !((String) rTransferData.getValueByName("ManageCom")).trim()
						.equals("")){
			strSQL = strSQL
			+ "and a.ManageCom like concat('"+ "?ManageCom?" + "','%') ";
			sqlbv1.put("Operator", ((String) rTransferData.getValueByName("Operator")).trim());
		}
		if (rTransferData.getValueByName("StartDate") != null
				&& !((String) rTransferData.getValueByName("StartDate")).trim()
						.equals("")){
			sqlbv1.put("MakeDate1", ((String) rTransferData.getValueByName("StartDate")).trim());
			strSQL = strSQL
			+ "and a.MakeDate >= '"
			+ "?MakeDate1?" + "' ";
			sqlbv1.put("ManageCom", ((String) rTransferData.getValueByName("ManageCom")).trim());
		}
		if (rTransferData.getValueByName("EndDate") != null
				&& !((String) rTransferData.getValueByName("EndDate")).trim()
						.equals("")){
			strSQL = strSQL + "and a.MakeDate <= '"
			+ "?MakeDate2?"
			+ "' ";
			sqlbv1.put("MakeDate2", ((String) rTransferData.getValueByName("EndDate")).trim());
		}
			
		
		
		
		
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "没有符合条件的纪录！");
			logger.debug("\t@> LRNSpecialAvailablePrintBL.printData() : ExeSQL.execSQL 结果为空！");
			return false;
		} else {
			// ListTitle
			String arrListTitle[] = new String[tSSRS.getMaxCol()];
			try {
				for (int j = 0; j < tSSRS.getMaxCol(); j++) {
					arrListTitle[j] = "TR" + String.valueOf(j);
				}
			} catch (Exception ex) {
				CError.buildErr(this, "初始化列表标题错误！");
				logger.debug("\t@> LRNSpecialAvailablePrintBL.printData() : ListTitle[] 赋值异常！");
				return false;
			}
			// ListTable
			ListTable tListTable = new ListTable();
			tListTable.setName("F1");
			String sResultLine[];
			// 循环取值
			try {
				for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
					sResultLine = new String[tSSRS.getMaxCol()];
					sResultLine[0] = tSSRS.GetText(k, 1);
					sResultLine[1] = tSSRS.GetText(k, 2);
					sResultLine[2] = tSSRS.GetText(k, 3);
					sResultLine[3] = tSSRS.GetText(k, 4);
					sResultLine[4] = tSSRS.GetText(k, 5);
					sResultLine[5] = tSSRS.GetText(k, 6);
					sResultLine[6] = tSSRS.GetText(k, 7);
					sResultLine[7] = tSSRS.GetText(k, 8);
					tListTable.add(sResultLine);
				}
			} catch (Exception ex) {
				CError.buildErr(this, "获取记录集到表格错误！");
				logger.debug("\t@> LRNSpecialAvailablePrintBL.printData() : ListTable[] 赋值异常！");
				return false;
			}
			// 模板赋值
			TextTag tTextTag = new TextTag();
			tTextTag
					.add(
							"ManageCom",
							(rTransferData.getValueByName("ManageCom") != null) ? ((String) rTransferData
									.getValueByName("ManageCom")).trim()
									: "");
			tTextTag
					.add(
							"StartDate",
							(rTransferData.getValueByName("StartDate") != null) ? ((String) rTransferData
									.getValueByName("StartDate")).trim()
									: "");
			tTextTag
					.add(
							"EndDate",
							(rTransferData.getValueByName("EndDate") != null) ? ((String) rTransferData
									.getValueByName("EndDate")).trim()
									: "");
			tTextTag.add("RowCount", tSSRS.getMaxRow());
			tTextTag.add("Operator", rGlobalInput.Operator);
			tTextTag.add("CurrentDate", StrTool.getChnDate(PubFun
					.getCurrentDate()));
			// 垃圾处理
			tSSRS = null;
			tExeSQL = null;
			// 输出文档
			XmlExport tXmlExport = new XmlExport();
			try {
				tXmlExport.createDocument(FormulaOneVTS, "printer"); // 初始化
				// XML
				// 文档
			} catch (Exception ex) {
				CError.buildErr(this, "初始化打印模板错误！");
				logger.debug("\t@> LRNSpecialAvailablePrintBL.printData() : 设置 FormulaOne VTS 文件异常！");
				return false;
			}
			tXmlExport.addTextTag(tTextTag);
			tXmlExport.addListTable(tListTable, arrListTitle);
			// 输出数据
			rResultData.clear();
			rResultData.addElement(tXmlExport);
			// 垃圾处理
			tTextTag = null;
			tXmlExport = null;
		}
		// return to submitData
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.printData() 成功");
		return true;
	}// function printData end

	// ==========================================================================

	/**
	 * 返回处理结果
	 * 
	 * @param :
	 *            null
	 * @return : VData
	 */
	public VData getResult() {
		return this.rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回错误信息
	 * 
	 * @param :
	 *            null
	 * @return : CErrors
	 */
	public CErrors getErrors() {
		return this.mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理运行时产生的垃圾
	 * 
	 * @param :
	 *            null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rTransferData != null)
			rTransferData = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 测试主程序业务方法
	 * 
	 * @param :
	 *            String[]
	 */
	// public static void main(String args[])
	// {
	// LRNSpecialAvailablePrintBL tLRNSpecialAvailablePrintBL = new
	// LRNSpecialAvailablePrintBL();
	// } //function main end
	// ==========================================================================
} // class LRNSpecialAvailablePrintUI end
