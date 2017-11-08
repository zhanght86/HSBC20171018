package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: 团体保全-VIP客户查询项</p>
 * <p>Description: 团体保全-VIP客户查询结果处理类</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @todo     : 保全-VIP客户查询-生成EXCEL文件
 * @author   : liuxiaosong (liuxs@sinosoft.com.cn)
 * @version  : 1.00
 * @date     : 2006-10-12
 ******************************************************************************/

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.TxtExport;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class VIPCustomerQueryPrintBL {
private static Logger logger = Logger.getLogger(VIPCustomerQueryPrintBL.class);
	// ==========================================================================

	// public VIPCustomerQueryPrintBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData; // 字符串数据集合
	private String rQuerySQL;// 查询用SQL，从前台传入
	// 输出数据
	private String[][] arrOutputData; // 需要生成 Excel 或TXT文件的数据容器
	private TxtExport tTxtExport = null; // 生成TXT文件的公用类

	// ==========================================================================

	/**
	 * 高层应用调用本类的入口函数
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> ProxyBonusToPostBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> ProxyBonusToPostBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}

		// 根据操作类型选择进行EXCEL导出还是 TXT导出
		rOperation = cOperate.trim();
		logger.debug("导出类型为：" + rOperation);

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> VIPCustomerQueryPrintBL.submitData() 成功");
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
		logger.debug("\t@> VIPCustomerQueryPrintBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> VIPCustomerQueryPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> VIPCustomerQueryPrintBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// 获得查询条件SQL语句
		rQuerySQL = (String) rTransferData.getValueByName("QuerySQL");
		if (rQuerySQL == null) {
			CError.buildErr(this, "无法获取查询条件！");
			logger.debug("\t@> VIPCustomerQueryPrintBL.getInputData() : 无法获取 QuerySQL 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		logger.debug("\t@> VIPCustomerQueryPrintBL.getInputData() 成功");
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
		logger.debug("\t@> VIPCustomerQueryPrintBL.dealData() 开始");

		// 获取提交的信息
		// =========del=========liuxiaosong================2006-11-20========start=================
		// =========在前台直接将SQL拼出，避免重复拼写和错误=================
		// String viptype = (String) rTransferData.getValueByName("VipType");
		// String customerNo = (String)
		// rTransferData.getValueByName("CustomerNo");
		// String sextype = (String) rTransferData.getValueByName("Sex");
		// String birthday = (String) rTransferData.getValueByName("Birthday");
		// String agentcode = (String) rTransferData.getValueByName("Operator");
		// String curdate = (String) rTransferData.getValueByName("CurDate");
		// String startlife = (String)
		// rTransferData.getValueByName("StartLife");
		// String endlife = (String) rTransferData.getValueByName("EndLife");
		// String managecom = (String)
		// rTransferData.getValueByName("ManageCom");
		// String curmanagecom = rGlobalInput.ManageCom;
		// String QuerySQL = new String();
		//
		// //----------------------------------------------------------------------
		//
		// //拼写 SQL 语句,使用年龄段查询时附加年龄段查询条件不使用
		// //按不同条件拼SQL
		// String QuerySQLHead = "select (select codename from ldcode where
		// '1'='1' and codetype = 'vipvalue' and code=d.vipvalue)"
		// +
		// ",d.customerno,d.name,d.sex,d.birthday,d.IDType,d.idno,"
		// +
		// "c.Phone,c.Mobile,c.HomeAddress,c.PostalAddress,c.ZipCode "
		// + "from LDPerson d , LCAddress c ,LCAppnt a ";
		//
		// String QuerySQLDetail = "where 1=1 ";
		//
		// String QuerySQLTail =
		// " and c.customerno=d.customerno and d.customerno=a.appntno "
		// + " and a.managecom like '" + curmanagecom +
		// "%' and a.managecom like '" + managecom + "%' ";
		//
		// //将不为空的条件加入SQL中
		// if(!customerNo.equals("")){
		// QuerySQLDetail=QuerySQLDetail+ " and d.customerno='" + customerNo +
		// "' ";//客户号不空
		// }
		// if(!viptype.equals("")){
		// QuerySQLDetail=QuerySQLDetail+ " and d.VIPValue='" + viptype + "'
		// ";//VIP值不空
		// }
		// if(!sextype.equals("")){
		// QuerySQLDetail=QuerySQLDetail+ " and d.sex='" + sextype + "' ";//性别不空
		// }
		// if(!birthday.equals("")){
		// QuerySQLDetail=QuerySQLDetail+ " and d.birthday='" + birthday + "'
		// ";//生日不空
		// }
		// if(!agentcode.equals("")){
		// QuerySQLDetail=QuerySQLDetail+ " and d.operator='" + agentcode +
		// "'";//业务员编码不空
		// }
		//
		//
		// if (startlife != "" && endlife != "")
		// {
		// QuerySQLDetail = QuerySQLDetail
		// + " and (date'" + curdate + "' - d.birthday)/(365)>'" +
		// startlife + "'"
		// + " and (date'" + curdate + "' - d.birthday)/(365)<'" +
		// endlife + "'";
		// }
		//
		// //最终查询SQL
		// QuerySQLHead=QuerySQLHead+QuerySQLDetail+QuerySQLTail;
		// =========del=========liuxiaosong================2006-11-20========end=================
		// ----------------------------------------------------------------------

		// 执行 SQL 查询
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(rQuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "执行 SQL 查询异常！");
			return false;
		}

		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			logger.debug("\t@> 无纪录！");
			CError.buildErr(this, "无纪录！");
			return false;
		} else {
			// 要输出的列名
			// customerno,name,sex,birthday,idno,RgtAddress,GrpName
			// "序号", "客户号", "客户姓名", "性别", "出生日期", "证件类型", "证件号码", "联系电话",
			// "移动电话", "家庭住址", "通讯地址", "邮编"
			int rows = tSSRS.getMaxRow();
			int cols = tSSRS.getMaxCol(); // 这样写，无论怎么改SQL中的查询字段，都不用去管结果集的列数
			arrOutputData = new String[rows][cols + 1]; // 共12列=1列序号列 + 11列数据列

			// 查询结果可能超过单个的excel文件所能承受的大小，这种情况暂没处理

			for (int i = 0; i < rows; i++) {
				arrOutputData[i][0] = "" + (i + 1); // 导出时第0列附加“序号”，再循环填充其他值
				for (int j = 0; j < cols; j++) {
					try {
						arrOutputData[i][j + 1] = tSSRS.GetText(i + 1, j + 1);
					} catch (Exception ex) {
						logger.debug("\t@> VIPCustomerQueryPrintBL.dealData() : 赋值给数组出现异常 : "
										+ j + ex.toString());
					}
				}

			} // end for

			// 判断导出为哪种操作，如果导出为EXCEL，则只需要将查询结果以字符串数组传递给前台JSP页面，
			// 如果导出为TXT文件，则需要在此生成TXT文件，并在前台页面上附加下载TXT文件按钮

			if (rOperation.equals("exportTxt")) {
				logger.debug("\t@> VIPCustomerQueryPrintBL.dealData()：开始导出txt文件");
				tTxtExport = new TxtExport(); // 在需要生成TXT文件时才NEW一个该对象
				tTxtExport.createTxtDocument(FileManager.getFileName(
						FileManager.TXTVIPEXPORT, rGlobalInput.Operator, PubFun
								.getCurrentDate(), PubFun.getCurrentDate()),
						"Unicode");
				tTxtExport.outArray(new String[] { "序号", "客户号", "客户姓名", "性别",
						"出生日期", "证件类型", "证件号码", "联系电话", "移动电话", "家庭住址", "通讯地址",
						"邮编" }); // 列标题
				for (int i = 0; i < rows; i++) {
					tTxtExport.outArray(arrOutputData[i]); // 循环按行将值填充到TXT文件中
				}
				String sExportFileName = tTxtExport.getFileName();
				logger.debug("\t@> VIPCustomerQueryPrintBL.dealData()，导出的文件名称为 ："
								+ sExportFileName);
			} // end if (rOperation.equals("exportTXT"))

		} // end else

		// 垃圾处理,直接传出就不处理了
		// tSSRS = null;
		// tExeSQL = null;
		logger.debug("\t@> VIPCustomerQueryPrintBL.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return String[][]
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
		if (rInputData != null) {
			rInputData = null;
		}
		if (rGlobalInput != null) {
			rGlobalInput = null;
		}
		if (rTransferData != null) {
			rTransferData = null;
		}
	} // function collectGarbage end

	// ==========================================================================


} // class VIPCustomerQueryPrintBL end
