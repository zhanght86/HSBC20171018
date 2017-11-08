package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.TxtExport;
import com.sinosoft.utility.VData;
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04, 1.05, 1.06, 1.07, 1.08
 * @date     : 2005-10-26, 2005-11-10, 2006-01-06, 2006-02-09, 2006-03-25, 2006-03-31, 2006-05-29, 2006-07-18, 2006-09-07, 2006-10-20
 * @direction: 失效通知给邮局的清单
 ******************************************************************************/

// ******************************************************************************

public class InvalidPolToPostBL {
private static Logger logger = Logger.getLogger(InvalidPolToPostBL.class);
	// public InvalidPolToPostBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	// 私有变量
	private String sExportTxtName;
	// 输出数据
	private String[][] arrOutputData; // 需要生成的文件的数据容器

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> InvalidPolToPostBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> InvalidPolToPostBL.submitData() : 无法获取 InputData 数据！");
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
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> InvalidPolToPostBL.submitData() 结束");
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
		// logger.debug("\t@> InvalidPolToPostBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = ((GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> InvalidPolToPostBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取提交的数据信息！");
			logger.debug("\t@> InvalidPolToPostBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 统计起期
		String sStartDate = (String) rTransferData.getValueByName("StartDate");
		if (sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "无法获取起始日期信息！");
			return false;
		}

		// 统计止期
		String sEndDate = (String) rTransferData.getValueByName("EndDate");
		if (sEndDate == null || sEndDate.trim().equals("")) {
			CError.buildErr(this, "无法获取结束日期信息！");
			return false;
		}

		// logger.debug("\t@> InvalidPolToPostBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> InvalidPolToPostBL.dealData() 开始");

		// 获取提交的信息
		String sStartDate = (String) rTransferData.getValueByName("StartDate");
		String sEndDate = (String) rTransferData.getValueByName("EndDate");
		String sContType = (String) rTransferData.getValueByName("ContType");
		String sInputManageCom = (String) rTransferData
				.getValueByName("ManageCom");
		String sLoginManageCom = rGlobalInput.ManageCom;

		// ----------------------------------------------------------------------

		// 拼写 SQL 语句
		String QuerySQL = new String("");
		QuerySQL = "select (select ZipCode "
				+ "from LCAddress "
				+ "where 1 = 1 "
				+ "and CustomerNo = b.AppntNo "
				+ "and AddressNo = "
				+ "(select AddressNo "
				+ "from LCAppnt "
				+ "where 1 = 1 "
				+ "and ContNo = b.ContNo "
				+ "and AppntNo = b.AppntNo)), "
				// 地址
				+ "(concat((select PlaceName "
				+ "from LDAddress "
				+ "where 1 = 1 "
				+ "and PlaceType = '03' "
				+ "and trim(PlaceCode) = "
				+ "(select County "
				+ "from LCAddress "
				+ "where 1 = 1 "
				+ "and CustomerNo = b.AppntNo "
				+ "and AddressNo = "
				+ "(select AddressNo "
				+ "from LCAppnt "
				+ "where 1 = 1 "
				+ "and ContNo = b.ContNo "
				+ "and AppntNo = b.AppntNo))) , "
				+ "(select PostalAddress "
				+ "from LCAddress "
				+ "where 1 = 1 "
				+ "and CustomerNo = b.AppntNo "
				+ "and AddressNo = "
				+ "(select AddressNo "
				+ "from LCAppnt "
				+ "where 1 = 1 "
				+ "and ContNo = b.ContNo "
				+ "and AppntNo = b.AppntNo)))), "
				// 姓名
				+ "b.AppntName, "
				// 称谓
				+ "(select (case AppntSex when '0' then '先生' when '1' then '女士' else '' end) "
				+ "from LCAppnt "
				+ "where 1 = 1 "
				+ "and ContNo = b.ContNo "
				+ "and AppntNo = b.AppntNo), "
				// 保单号
				+ "b.ContNo, "
				// 失效日期
				+ "a.StartDate, "
				// 应交第几次保费
				+ " (case when (select max(PayCount) from LJSPayPerson where PolNo = b.PolNo) is not null then (select max(PayCount) from LJSPayPerson where PolNo = b.PolNo)  else year(b.PayToDate) - year(b.CValiDate) + 1 end), "
				// 保费金额
				+ "round(b.Prem, 2), "
				// 营业区
				+ "(select Name "
				+ "from LABranchGroup "
				+ "where AgentGroup = "
				+ "(select trim(UpBranch) "
				+ "from LABranchGroup "
				+ "where AgentGroup = "
				+ "(select trim(UpBranch) "
				+ "from LABranchGroup "
				+ "where AgentGroup = "
				+ "(select BranchCode "
				+ "from LAAgent "
				+ "where AgentCode = trim(b.AgentCode))))), "
				// 营销部
				+ "(select Name "
				+ "from LABranchGroup "
				+ "where AgentGroup = "
				+ "(select trim(UpBranch) "
				+ "from LABranchGroup "
				+ "where AgentGroup = "
				+ "(select BranchCode "
				+ "from LAAgent "
				+ "where AgentCode = trim(b.AgentCode)))), "
				// 营销组
				+ "(select Name "
				+ "from LABranchGroup "
				+ "where AgentGroup = "
				+ "(select BranchCode "
				+ "from LAAgent "
				+ "where AgentCode = trim(b.AgentCode))), "
				// 业务员姓名
				+ "(select Name from LAAgent where AgentCode = trim(b.AgentCode)), "
				// 业务员编号
				+ "b.AgentCode, "
				// 公司名称
				+ "(select Name from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 4)), "
				// 公司地址
				+ "(select Address from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 6)), "
				// 公司邮编
				+ "(select ZipCode from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 6)), "
				// 咨询电话
				+ "'95567' " + "from LCContState a, LCPol b, LCCont c "
				+ "where 1 = 1 " + "and a.StateType = 'Available' "
				+ "and a.State = '1' " + "and a.EndDate is null "
				+ "and a.StartDate " + "between to_date('" + "?sStartDate?"
				+ "', 'yyyy-mm-dd') " + "and to_date('" + "?sEndDate?"
				+ "', 'yyyy-mm-dd') ";
		// 选择的机构
		if (sInputManageCom != null && !sInputManageCom.trim().equals("")) {
			QuerySQL += "and b.ManageCom like concat('"+ "?sInputManageCom?" + "','%') "
					+ "and c.ManageCom like concat('"+ "?sInputManageCom?" + "','%') ";
		}
		// 登陆的机构
		QuerySQL += "and b.ManageCom like concat('"+ "?sLoginManageCom?" + "','%') "
				+ "and c.ManageCom like concat('"+ "?sLoginManageCom?" + "','%')";
		// 剩下的 SQL
		QuerySQL += "and b.PolNo = a.PolNo "
				+ "and c.ContNo = b.ContNo "
				+ "and c.ManageCom = b.ManageCom "
				+ "and c.AppFlag = '1' " // 排除已经退保、解约或理赔终止
				+ "and c.PayToDate " + "between subdate(to_date('" + "?sStartDate?"
				+ "', 'yyyy-mm-dd'),61) "
				+ "and subdate(to_date('"
				+ "?sEndDate?"
				+ "', 'yyyy-mm-dd'),61) "
				+ "and not exists " // 如果已经打印通知书则不包含在内
				+ "(select 'X' " + "from LDContInvoiceMap " + "where 1= 1 "
				+ "and ContNo = b.ContNo " + "and OperType = '4' "
				+ "and MakeDate >= to_date('" + "?sStartDate?"
				+ "', 'yyyy-mm-dd')) ";
		// 区分在职单和孤儿单
		if (sContType != null && sContType.trim().equals("0")) // 在职单
		{
			QuerySQL += "and exists " + "(select 'X' "
					+ "from LACommisionDetail " + "where 1 = 1 "
					+ "and GrpContNo = b.ContNo " + "and PolType = '0') ";
		} else if (sContType != null && sContType.trim().equals("1")) // 孤儿单
		{
			QuerySQL += "and exists " + "(select 'X' "
					+ "from LACommisionDetail " + "where 1 = 1 "
					+ "and GrpContNo = b.ContNo " + "and PolType = '1') ";
		}
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------
		// 执行 SQL 查询
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(QuerySQL);
		sqlbv1.put("sStartDate", sStartDate);
		sqlbv1.put("sEndDate", sEndDate);
		sqlbv1.put("sInputManageCom", sInputManageCom);
		sqlbv1.put("sLoginManageCom", sLoginManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(sqlbv1);
		} catch (Exception ex) {
			CError.buildErr(this, "执行 SQL 查询异常！");
			return false;
		}

		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "该时间段内无失效保单纪录！");
			logger.debug("\t@> " + sStartDate + " 至 " + sEndDate
					+ " 时间段内无失效保单纪录！");
			return false;
		} else {
			// 要输出的列名:
			// "邮编", "地址", "姓名", "称谓", "保单号", "失效日期", "应交第几次保费", "保费金额", "营业区",
			// "营销部", "营销组", "业务员姓名", "业务员编号", "公司名称", "公司地址", "公司邮编", "咨询电话"
			arrOutputData = new String[tSSRS.getMaxRow()][tSSRS.getMaxCol()];
			for (int i = 0; i < tSSRS.getMaxRow(); i++) {
				try {
					arrOutputData[i][0] = tSSRS.GetText(i + 1, 1); // 邮编
					arrOutputData[i][1] = tSSRS.GetText(i + 1, 2); // 地址
					arrOutputData[i][2] = tSSRS.GetText(i + 1, 3); // 投保人姓名
					arrOutputData[i][3] = tSSRS.GetText(i + 1, 4); // 称谓
					arrOutputData[i][4] = tSSRS.GetText(i + 1, 5); // 保单号
					arrOutputData[i][5] = tSSRS.GetText(i + 1, 6); // 失效日期
					arrOutputData[i][6] = tSSRS.GetText(i + 1, 7); // 应交第几次保费
					arrOutputData[i][7] = tSSRS.GetText(i + 1, 8); // 保费金额
					arrOutputData[i][8] = tSSRS.GetText(i + 1, 9); // 营业区
					arrOutputData[i][9] = tSSRS.GetText(i + 1, 10); // 营销部
					arrOutputData[i][10] = tSSRS.GetText(i + 1, 11); // 营销组
					arrOutputData[i][11] = tSSRS.GetText(i + 1, 12); // 业务员姓名
					arrOutputData[i][12] = tSSRS.GetText(i + 1, 13); // 业务员编号
					arrOutputData[i][13] = tSSRS.GetText(i + 1, 14); // 公司名称
					arrOutputData[i][14] = tSSRS.GetText(i + 1, 15); // 公司地址
					arrOutputData[i][15] = tSSRS.GetText(i + 1, 16); // 公司邮编
					arrOutputData[i][16] = tSSRS.GetText(i + 1, 17); // 咨询电话
				} catch (Exception ex) {
					logger.debug("\t@> InvalidPolToPostBL.dealData() : 赋值给数组异常 : "
									+ i);
				}
			} // end for
		}
		tExeSQL = null;
		tSSRS = null;

		// logger.debug("\t@> InvalidPolToPostBL.dealData() 结束");
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
		// logger.debug("\t@> InvalidPolToPostBL.outputData() 开始");

		String sStartDate = (String) rTransferData.getValueByName("StartDate");
		String sEndDate = (String) rTransferData.getValueByName("EndDate");
		String sFileType = (String) rTransferData.getValueByName("FileType");

		// ----------------------------------------------------------------------

		if (sFileType != null && sFileType.trim().equalsIgnoreCase("txt")) {
			TxtExport tTxtExport = new TxtExport();
			try {
				tTxtExport.createTxtDocument(FileManager.getFileName(
						FileManager.THIRDFILETXT, rGlobalInput.Operator,
						sStartDate, sEndDate), null);
			} catch (Exception ex) {
				CError.buildErr(this, "查询数据成功，但创建目标文件失败！");
				return false;
			}
			tTxtExport.outArray(getTitles());
			for (int i = 0; i < arrOutputData.length; i++) {
				tTxtExport.outArray(arrOutputData[i]);
			}
			sExportTxtName = tTxtExport.getFileName();
			logger.debug("失效通知给邮局的 Txt 清单输出文件名: " + sExportTxtName);
			tTxtExport = null;
		}

		// logger.debug("\t@> InvalidPolToPostBL.outputData() 结束");
		return true;
	} // function outputData end

	// ==========================================================================

	/**
	 * 返回本类输出的列标题
	 * 
	 * @param null
	 * @return String[]
	 */
	public String[] getTitles() {
		String[] arrOutputTitle = new String[] { "邮编", "地址", "姓名", "称谓", "保单号",
				"失效日期", "应交第几次保费", "保费金额", "营业区", "营销部", "营销组", "业务员姓名",
				"业务员编号", "公司名称", "公司地址", "公司邮编", "咨询电话" };
		return arrOutputTitle;
	} // function getTitles end

	// ==========================================================================

	/**
	 * 返回经过本类生成的 Txt 文件名
	 * 
	 * @param null
	 * @return String
	 */
	public String getTxtName() {
		String sTxtFullPath = new String("");
		String sTxtFileName = new String("");
		if (sExportTxtName != null && !sExportTxtName.trim().equals("")) {
			sTxtFullPath = sExportTxtName.replace('\\', '/');
			sTxtFileName = sTxtFullPath
					.substring(sTxtFullPath.lastIndexOf("/") + 1);
		}
		return sTxtFileName;
	} // function getTxtName end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public String[][] getResult() {
		return arrOutputData;
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
	} // function collectGarbage end

	// ==========================================================================


} // class InvalidPolToPostBL end
