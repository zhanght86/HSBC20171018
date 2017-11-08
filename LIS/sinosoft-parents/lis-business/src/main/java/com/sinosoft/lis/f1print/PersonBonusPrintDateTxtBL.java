package com.sinosoft.lis.f1print;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqPolBalBL;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.TxtExport;
import com.sinosoft.utility.VData;
/*******************************************************************************
 * <p>Title: Lis 6.0</p>

 * <p>Copyright: Copyright (c) 2007 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: nci</p>
 * <p>WebSite: </p>
 *
 * @author   : wangyy
 * @version  : 1.00
 * @date     : 2007-06-01
 * @direction: 分红外包数据
 ******************************************************************************/

// ******************************************************************************

public class PersonBonusPrintDateTxtBL {
private static Logger logger = Logger.getLogger(PersonBonusPrintDateTxtBL.class);
	// ==========================================================================

	// public PersonBonusPrintDateTxtBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	private StringBuffer mSqlBf = new StringBuffer();

	private Connection mConnection = null;
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	private String sSaleChnl = "";

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private String sStartDate;
	private String sEndDate;

	private String tManageCom;

	private GlobalInput mGlobalInput = new GlobalInput();
	private PubFun tpunfun = new PubFun();
	/* 标志位 */
	public boolean v_flag = false;
	// 不能直接查询的数据用计算
	String sPolNo = ""; // 险种号
	String sDatePoint = ""; // 分红日
	double dBonusAmnt = 0.0; // 红利保额
	double dSumdBonusAmnt = 0.0; // 累计红利保额
	double dSumPrem = 0.0; // 累计保费, 计算用
	String sSumPrem = ""; // 累计保费, 显示用
	double dSumdBonusCashValue = 0.0; // 累计红利保额现金价值, 计算用
	String sSumdBonusCashValue = ""; // 累计红利保额现金价值, 显示用
	double dBonusCashValue = 0.0; // 红利保额现金价值, 计算用
	String sBonusCashValue = ""; // 红利保额现金价值, 显示用
	String state = "";

	// ==========================================================================
	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PersonBonusPrintDateTxtBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PersonBonusPrintDateTxtBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

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
		// logger.debug("\t@> PersonBonusPrintDateTxtBL.getInputData()
		// 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PersonBonusPrintDateTxtBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> PersonBonusPrintDateTxtBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 统计起期
		// sStartDate = (String) rTransferData.getValueByName("StartDate");
		// if (sStartDate == null || sStartDate.trim().equals(""))
		// {
		// CError.buildErr(this, "无法获取起始日期信息！");
		// return false;
		// }

		tManageCom = (String) rTransferData.getValueByName("ManageCom");
		if (tManageCom == null || tManageCom.trim().equals("")) {
			CError.buildErr(this, "无法获取管理机构信息！");
			return false;
		}

		// 统计止期
		sSaleChnl = (String) rTransferData.getValueByName("SaleChnl");
		if (sSaleChnl == null || sSaleChnl.trim().equals("")) {
			CError.buildErr(this, "无法获取销售渠道信息！");
			return false;
		} else {
			if (sSaleChnl.equals("1")) {
				sSaleChnl = "I";
			} else if (sSaleChnl.equals("3")) {
				sSaleChnl = "B";
			} else if (sSaleChnl.equals("2")) {
				sSaleChnl = "G";
			}

		}

		// 统计止期
		// sEndDate = (String) rTransferData.getValueByName("EndDate");
		// if (sEndDate == null || sEndDate.trim().equals(""))
		// {
		// CError.buildErr(this, "无法获取结束日期信息！");
		// return false;
		// }

		logger.debug("\t@> PersonBonusPrintDateTxtBL.getInputData() 成功");
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
		logger.debug("\t@> PersonBonusPrintDateTxtBL.dealData() 开始");

		String[] arrOutputTitle = new String[] { "投保人邮编", "投保人地址", "投保人姓名",
				"投保人称谓", "险种名称", "保单号", "会计年度", "分红日前一日", "有效保额", "最近一次已交保费",
				"已交保费", "分红日", "基本保额", "红利保额", "现金价值", "累计红利保额", "累计现金价值",
				"有效保额", "年度分红率", "终了分红率", "营业区", "营销部", "业务员姓名", "业务员编号",
				"公司名称", "公司地址", "公司邮编", "咨询电话", "销售渠道", "中心支公司代码", "分公司公司地址",
				"网点名称", "网点代码", "保单生效日" };
		// 查询外包数据的公司
		// Processtate: '1', '数据处理中';'2', '数据处理完毕'; '0', '数据处理失败'; '3',
		// '生成数据文件中'; '4','生成数据成功'; '5', '生成数据失败';null, '数据未处理')
		String comSQL = "Select trim(a.COMCODE),a.NAME,trim(a.RISKTYPE),a.serno,a.comgrade,a.processnum,a.OTHERSQL,a.PROCESSTATE,to_char(a.startdate,'yyyy-mm-dd'),to_char(a.enddate,'yyyy-mm-dd') From PersonBonusLog a Where a.comcode like concat('"
				+ "?tManageCom?"
				+ "','%') and a.risktype='"
				+ "?sSaleChnl?"
				+ "' and a.PROCESSTATE is null order by a.comcode,a.risktype,a.serno";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(comSQL);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("sSaleChnl", sSaleChnl);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tcomSSRS = new SSRS();
		tcomSSRS = tExeSQL.execSQL(sqlbv1);
		// 按日志表循环
		for (int i = 0; i < tcomSSRS.getMaxRow(); i++) {
			// 按公司层级统计
			String mSQL = "Select trim(comcode) From ldcom Where Comgrade = '"
					+ "?Comgrade?" + "' and comcode like concat('"
					+ "?comcode?" + "','%')";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(mSQL);
			sqlbv2.put("Comgrade", tcomSSRS.GetText(i + 1, 5));
			sqlbv2.put("comcode", tcomSSRS.GetText(i + 1, 1));
			String tempSQL = "Select a.PROCESSTATE From PersonBonusLog a Where a.comcode like '"
					+ "?comcode?"
					+ "%' and a.risktype='"
					+ "?risktype?"
					+ "' and a.serno='"
					+ "?serno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tempSQL);
			sqlbv3.put("comcode", tcomSSRS.GetText(i + 1, 1));
			sqlbv3.put("risktype", tcomSSRS.GetText(i + 1, 3));
			sqlbv3.put("serno", tcomSSRS.GetText(i + 1, 4));
			ExeSQL mExeSQL = new ExeSQL();
			SSRS mSSRS = new SSRS();
			mSSRS = mExeSQL.execSQL(sqlbv3);
			if (!mSSRS.GetText(1, 1).equals(null)
					&& !mSSRS.GetText(1, 1).equals("")) {
				continue;
			}

			mSSRS = mExeSQL.execSQL(sqlbv2);
			TxtExport tTxtExport = new TxtExport();
			TxtExport eTxtExport = new TxtExport();
			sStartDate = tcomSSRS.GetText(i + 1, 9);
			sEndDate = tcomSSRS.GetText(i + 1, 10);
			// 错误数据文件
			eTxtExport.createTxtDocument(FileManager.getFileName(
					FileManager.THIRDFILETXT, rGlobalInput.Operator, tcomSSRS
							.GetText(i + 1, 1)
							+ "_"
							+ tcomSSRS.GetText(i + 1, 3)
							+ "_"
							+ sStartDate, sEndDate + "_"
							+ tcomSSRS.GetText(i + 1, 4) + "_error"), null);
			// 正确数据文件
			tTxtExport.createTxtDocument(FileManager.getFileName(
					FileManager.THIRDFILETXT, rGlobalInput.Operator, tcomSSRS
							.GetText(i + 1, 1)
							+ "_"
							+ tcomSSRS.GetText(i + 1, 3)
							+ "_"
							+ sStartDate, sEndDate + "_"
							+ tcomSSRS.GetText(i + 1, 4) + "_right"), null);
			// 修改数据状态
			ExeSQL sExeSQL = new ExeSQL();

			String filename =	rGlobalInput.Operator
								+ tcomSSRS.GetText(i + 1, 1)
								+ "_"
								+ tcomSSRS.GetText(i + 1, 3)
								+ "_"
								+ sStartDate
								+ "_"
								+ sEndDate
								+ "_"
								+ tcomSSRS.GetText(i + 1, 4)
								+ "_right";
					
			try { // 输出标题
				tTxtExport.outArray(arrOutputTitle);
				eTxtExport.outArray(arrOutputTitle);
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(" update PersonBonusLog set PROCESSTATE='1',processnum=0,makedate=to_date('"
						+ "?makedate?"
						+ "','yyyy-mm-dd'),maketime='"
						+ "?maketime?"
						+ "' ,filename='?filename?'"
						+ " where comcode='"
						+ "?comcode?"
						+ "' and risktype='"
						+ "?risktype?"
						+ "' and serno='"
						+ "?serno?" + "'");
				sqlbv4.put("makedate", tpunfun.getCurrentDate());
				sqlbv4.put("maketime", tpunfun.getCurrentTime());
				sqlbv4.put("filename", filename);
				sqlbv4.put("comcode", tcomSSRS.GetText(i + 1, 1));
				sqlbv4.put("risktype", tcomSSRS.GetText(i + 1, 3));
				sqlbv4.put("serno", tcomSSRS.GetText(i + 1, 4));
				sExeSQL.execUpdateSQL(sqlbv4);
				// 按统计层级循环
				for (int j = 0; j < mSSRS.getMaxRow(); j++) {
					if (!connectDataBase()) {
						logger.debug("连接数据库失败:" + PubFun.getCurrentDate()
								+ "------" + PubFun.getCurrentTime());
					}
					// 拼写 SQL 语句
					mSqlBf.replace(0, mSqlBf.length(), "");
					this.mSqlBf.append(selectsql(sStartDate, sEndDate, mSSRS
							.GetText(j + 1, 1), tcomSSRS.GetText(i + 1, 3),
							tcomSSRS.GetText(i + 1, 7)));
					// 执行 SQL 查询
					logger.debug(mSqlBf.toString());
					if (getResultSetBySQL(mSqlBf.toString())) {
						logger.debug("File is opened ...");
						if (this.mResultSet.getRow() <= 0) {
							logger.debug("no data");
							continue;
						}

						try { // 向文件输出数据
							while (this.mResultSet.next()) { // 组织一条数据

								try { // 输出内容
									// 不能直接查询的数据用计算=====开始=================================
									String[] arrOutputData = new String[34];
									sPolNo = ""; // 险种号
									sDatePoint = ""; // 分红日
									dBonusAmnt = 0.0; // 红利保额
									dSumdBonusAmnt = 0.0; // 累计红利保额
									dSumPrem = 0.0; // 累计保费, 计算用
									sSumPrem = ""; // 累计保费, 显示用
									dSumdBonusCashValue = 0.0; // 累计红利保额现金价值,
																// 计算用
									sSumdBonusCashValue = ""; // 累计红利保额现金价值,
																// 显示用
									dBonusCashValue = 0.0; // 红利保额现金价值, 计算用
									sBonusCashValue = ""; // 红利保额现金价值, 显示用
									sPolNo = mResultSet.getString(21);
									sDatePoint = mResultSet.getString(35);
									state = "";

									// 已交保费
									BqPolBalBL tBqPolBalBL = new BqPolBalBL();
									if (!tBqPolBalBL.getSumPremPerPol(sPolNo,
											sDatePoint)) {
										logger.debug("\t@> PersonBonusPrintDateTxtBL.dealData() : 计算已交保费失败 : "
														+ sPolNo);
										sSumPrem = "";
										state = "error";
									} else {
										dSumPrem = tBqPolBalBL
												.getCalResultRound();
										sSumPrem = BqNameFun.getRound(dSumPrem);
									}
									tBqPolBalBL = null;

									// 累计红利保额现金价值
									EdorCalZT tEdorCalZT = new EdorCalZT(
											rGlobalInput);
									dSumdBonusCashValue = tEdorCalZT
											.getBonusCashValue(sPolNo,
													sDatePoint);
									tEdorCalZT = null;
									if (dSumdBonusCashValue < 0) {
										logger.debug("\t@> PersonBonusPrintDateTxtBL.dealData() : 计算累计红利保额现金价值失败 : "
														+ sPolNo);
										sSumdBonusCashValue = "";
										state = "error";
									} else {
										sSumdBonusCashValue = BqNameFun
												.getRound(dSumdBonusCashValue);
									}

									// 红利保额和累计红利保额
									try {
										if (!mResultSet.getString(14).trim()
												.equals("")) {
											dBonusAmnt = Double
													.parseDouble(mResultSet
															.getString(14));
										} else {
											dBonusAmnt = 0.0;
											state = "error";
										}
										if (!mResultSet.getString(16).trim()
												.equals("")) {
											dSumdBonusAmnt = Double
													.parseDouble(mResultSet
															.getString(16));
										} else {
											dSumdBonusAmnt = 0.0;
											state = "error";
										}
									} catch (Exception ex) {
										logger.debug("\t@> PersonBonusPrintDateTxtBL.dealData() : 数据类型转换异常 : "
														+ sPolNo);
										state = "error";
									}

									// 红利保额现金价值
									try {
										dBonusCashValue = dBonusAmnt
												/ dSumdBonusAmnt
												* dSumdBonusCashValue;
										sBonusCashValue = BqNameFun
												.getRound(dBonusCashValue);
									} catch (Exception ex) {
										logger.debug("\t@> PersonBonusPrintDateTxtBL.dealData() : 红利保额现金价值计算失败 : "
														+ sPolNo);
										sBonusCashValue = "";
										state = "error";
									}

									try {
										arrOutputData[0] = mResultSet
												.getString(1); // 邮编
										arrOutputData[1] = mResultSet
												.getString(2); // 地址
										arrOutputData[2] = mResultSet
												.getString(3); // 姓名
										arrOutputData[3] = mResultSet
												.getString(4); // 称谓
										arrOutputData[4] = mResultSet
												.getString(5); // 险种名称
										arrOutputData[5] = mResultSet
												.getString(6); // 保单号
										arrOutputData[6] = mResultSet
												.getString(7); // 会计年度
										arrOutputData[7] = mResultSet
												.getString(8); // 分红日前一日
										arrOutputData[8] = mResultSet
												.getString(9); // 有效保额
										arrOutputData[9] = mResultSet
												.getString(10); // 最近一次已交保费
										arrOutputData[10] = sSumPrem; // 已交保费(#)
										arrOutputData[11] = mResultSet
												.getString(12); // 分红日
										arrOutputData[12] = mResultSet
												.getString(13); // 基本保额
										arrOutputData[13] = mResultSet
												.getString(14); // 红利保额
										arrOutputData[14] = sBonusCashValue; // 现金价值(#)
										arrOutputData[15] = mResultSet
												.getString(16); // 累计红利保额
										arrOutputData[16] = sSumdBonusCashValue; // 累计现金价值(#)
										arrOutputData[17] = mResultSet
												.getString(18); // 有效保额
										arrOutputData[18] = mResultSet
												.getString(19); // 年度分红率
										arrOutputData[19] = mResultSet
												.getString(20); // 终了分红率
										arrOutputData[20] = mResultSet
												.getString(22); // 营业区
										arrOutputData[21] = mResultSet
												.getString(23); // 营销部
										arrOutputData[22] = mResultSet
												.getString(24); // 业务员姓名
										arrOutputData[23] = mResultSet
												.getString(25); // 业务员编号
										arrOutputData[24] = mResultSet
												.getString(26); // 公司名称
										arrOutputData[25] = mResultSet
												.getString(27); // 公司地址
										arrOutputData[26] = mResultSet
												.getString(28); // 公司邮编
										arrOutputData[27] = mResultSet
												.getString(29); // 咨询电话
										arrOutputData[28] = mResultSet
												.getString(30); // "销售渠道"
										arrOutputData[29] = mResultSet
												.getString(31); // "中心支公司代码"
										arrOutputData[30] = mResultSet
												.getString(32); // "分公司公司地址"
										arrOutputData[31] = mResultSet
												.getString(33); // "网点名称"
										arrOutputData[32] = mResultSet
												.getString(34); // "网点代码"
										arrOutputData[33] = mResultSet
												.getString(35); // 保单生效日
									} catch (Exception ex) {
										logger.debug("\t@> PersonBonusPrintDateTxtBL.dealData() : 赋值给数组出现异常 : "
														+ sPolNo);
									}

									if (state.equals("error")) {
										eTxtExport.outArray(arrOutputData);
									} else {
										tTxtExport.outArray(arrOutputData);
										SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
										sqlbv5.sql("update PersonBonusLog set processnum=processnum+1 where comcode='"
												+ "?comcode?"
												+ "' and serno='"
												+ "?serno?"
												+ "' and risktype='"
												+ "?risktype?" + "'");
										sqlbv5.put("comcode", tcomSSRS.GetText(i + 1, 1));
										sqlbv5.put("serno", tcomSSRS.GetText(i + 1, 4));
										sqlbv5.put("risktype", tcomSSRS.GetText(i + 1, 3));
										sExeSQL.execUpdateSQL(sqlbv5);
										SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
										sqlbv6.sql("Insert Into Bonuscontlog  (Bonuscontlog.Contno, Bonuscontlog.Fiscalyear, Bonuscontlog.Availableamnt, Bonuscontlog.Lastprem, Bonuscontlog.Sumprem, Bonuscontlog.Bonusdate, Bonuscontlog.Baseamnt, Bonuscontlog.Bonusamnt,"
												+ "Bonuscontlog.Cashvalue, Bonuscontlog.Countamnt, Bonuscontlog.Countcashvalue, Bonuscontlog.Bonusrate, Bonuscontlog.Terminalbonusrate,Bonuscontlog.branchcom) Values "
												+ "('"
												+ "?var6?"
												+ "','"
												+ "?var7?"
												+ "','"
												+ "?var9?"
												+ "','"
												+ "?var10?"
												+ "','"
												+ "?sSumPrem?"
												+ "','"
												+ "?var12?"
												+ "','"
												+ "?var13?"
												+ "','"
												+ "?var14?"
												+ "','"
												+ "?sBonusCashValue?"
												+ "','"
												+ "?var16?"
												+ "','"
												+ "?sSumdBonusCashValue?"
												+ "','"
												+ "?var19?"
												+ "','"
												+ "?var20?"
												+ "','"
												+ "?var31?"
												+ "')");
										sqlbv6.put("var6", mResultSet.getString(6));
										sqlbv6.put("var7", mResultSet.getString(7));
										sqlbv6.put("var9", mResultSet.getString(9));
										sqlbv6.put("var10", mResultSet.getString(10));
										sqlbv6.put("sSumPrem", sSumPrem);
										sqlbv6.put("var12", mResultSet.getString(12));
										sqlbv6.put("var13", mResultSet.getString(13));
										sqlbv6.put("var14", mResultSet.getString(14));
										sqlbv6.put("sBonusCashValue", sBonusCashValue);
										sqlbv6.put("var16", mResultSet.getString(16));
										sqlbv6.put("sSumdBonusCashValue", sSumdBonusCashValue);
										sqlbv6.put("var19", mResultSet.getString(19).substring(0,mResultSet.getString(19).lastIndexOf("%")));
										sqlbv6.put("var20", mResultSet.getString(20).substring(0,mResultSet.getString(20).lastIndexOf("%")));
										sqlbv6.put("var31", mResultSet.getString(31));
										sExeSQL.execUpdateSQL(sqlbv6);
									}
								} catch (Exception ed) {
									logger.debug("输出数据错误"
											+ mResultSet.getString(21));
									return true;
								}
							}
						} catch (Exception ex) {
							logger.debug("生成分红数据文件失败"
									+ mSSRS.GetText(j + 1, 1));
							return true;
						}
					}
				}
				String tExportFileName = tTxtExport.getFileName();
				String eExportFileName = eTxtExport.getFileName();
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(" update PersonBonusLog set PROCESSTATE='2' ,modifydate=to_date('"
						+ "?modifydate?"
						+ "','yyyy-mm-dd'),modifytime='"
						+ "?modifytime?"
						+ "'"
						+ " where comcode='"
						+ "?comcode?"
						+ "' and risktype='"
						+ "?risktype?"
						+ "' and serno='"
						+ "?serno?" + "'");
				sqlbv7.put("modifydate", tpunfun.getCurrentDate());
				sqlbv7.put("modifytime", tpunfun.getCurrentTime());
				sqlbv7.put("comcode", tcomSSRS.GetText(i + 1, 1));
				sqlbv7.put("risktype", tcomSSRS.GetText(i + 1, 3));
				sqlbv7.put("serno", tcomSSRS.GetText(i + 1, 4));
				sExeSQL.execUpdateSQL(sqlbv7);

				tTxtExport = null;
				eTxtExport = null;
				logger.debug("生成分红数据文件: " + tExportFileName);
			} catch (Exception ex) {
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(" update PersonBonusLog set PROCESSTATE='0'  ,modifydate=to_date('"
						+ "?modifydate?"
						+ "','yyyy-mm-dd') ,modifytime='"
						+ "?modifytime?"
						+ "'"
						+ " where comcode='"
						+ "?comcode?"
						+ "' and risktype='"
						+ "?risktype?"
						+ "' and serno='"
						+ "?serno?" + "'");
				sqlbv8.put("modifydate", tpunfun.getCurrentDate());
				sqlbv8.put("modifytime", tpunfun.getCurrentTime());
				sqlbv8.put("comcode", tcomSSRS.GetText(i + 1, 1));
				sqlbv8.put("risktype", tcomSSRS.GetText(i + 1, 3));
				sqlbv8.put("serno", tcomSSRS.GetText(i + 1, 4));
				sExeSQL.execUpdateSQL(sqlbv8);

				logger.debug("生成分红数据文件失败");
				return true;
			}
		}
		return true;
	}

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
	}

	// -------------------------------------------------------
	// 查询分红保单明细
	private boolean getResultSetBySQL(String tSql) {
		try {
			this.mStatement = this.mConnection.createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			this.mResultSet = this.mStatement.executeQuery(StrTool
					.GBKToUnicode(tSql));
			return true;
		} catch (Exception e) {
			logger.debug("####Error when execute SQL: " + tSql);
			e.printStackTrace();

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "callCenterBL";
			tError.functionName = "getResultSetBySQL";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	// ---------------------------------------------------------
	// 建立数据库连接
	private boolean connectDataBase() {
		try {
			this.mConnection = DBConnPool.getConnection();
			return true;
		} catch (Exception e) {
			logger.debug("####Error when connect database!");
			e.printStackTrace();

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "callCenterBL";
			tError.functionName = "connectDataBase";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	// ---------------------------------------------------------
	// 查询保单明细SQL
	private StringBuffer selectsql(String startdate, String enddate,
			String managecom, String risktype, String othersql) {
		StringBuffer mSql = new StringBuffer();
		mSql.append("select (select ZipCode from LCAddress where CustomerNo = b.AppntNo and AddressNo = (select trim(AddressNo) "
						+ "from LCAppnt where ContNo = b.ContNo and AppntNo = b.AppntNo)), " // 地址
						+ "((select trim(PlaceName) from LDAddress where PlaceType = '03' and trim(PlaceCode) = (select County "
						+ "from LCAddress where CustomerNo = b.AppntNo and AddressNo = (select trim(AddressNo) "
						+ "from LCAppnt where ContNo = b.ContNo and AppntNo = b.AppntNo))) || "
						+ "(select trim(PostalAddress) from LCAddress where CustomerNo = b.AppntNo and AddressNo = "
						+ "(select trim(AddressNo) from LCAppnt where ContNo = b.ContNo and AppntNo = b.AppntNo))), " // 姓名
						+ "b.AppntName, " // 称谓
						+ "(select decode(AppntSex, '0', '先生', '1', '女士', '') "
						+ "from LCAppnt where ContNo = b.ContNo and AppntNo = b.AppntNo), "
						// 险种名称
						+ "(select RiskStatName from LMRisk where RiskCode = b.RiskCode), "
						// 保单号
						+ "b.ContNo, "
						// 会计年度
						+ "a.FiscalYear, "
						// 分红日前一日
						+ "(case when a.BonusMakeDate >= a.SDispatchDate then to_char(subdate(a.BonusMakeDate , 1),'yyyy-mm-dd') else to_char(subdate(a.SDispatchDate , 1),'yyyy-mm-dd') end), "
						// 有效保额
						+ "nvl(round((select AvailableAmnt from LOEngBonusPol where PolNo = b.PolNo "
						+ "and FiscalYear = a.FiscalYear - 1), 2), round(b.Amnt, 2)), "
						// 最近一次已交保费
						+ "b.Prem, "
						// 已交保费
						+ "'', "
						// 分红日
						+ "(case when a.BonusMakeDate >= a.SDispatchDate then to_char(a.BonusMakeDate,'yyyy-mm-dd') else to_char(a.SDispatchDate,'yyyy-mm-dd') end), "
						// 基本保额
						+ "round(a.BaseAmnt, 2), "
						// 红利保额
						+ "round(a.BonusAmnt, 2), "
						// 现金价值
						+ "'', "
						// 累计红利保额
						+ "round(a.AvailableAmnt - a.BaseAmnt, 2), "
						// 累计现金价值
						+ "'', "
						// 有效保额
						+ "round(a.AvailableAmnt, 2), "
						// 年度分红率
						+ "(case when (round(a.BonusRate * 100, 2) > 0 and round(a.BonusRate * 100, 2) < 1) then '0' || round(a.BonusRate * 100, 2) || '%' "
						+ "else round(a.BonusRate * 100, 2) || '%' end), "
						// 终了分红率
						+ "(case when round(a.TerminalBonusRate * 100, 2) is null then '' "
						+ "when (round(a.TerminalBonusRate * 100, 2) > 0 and round(a.TerminalBonusRate * 100, 2) < 1) then "
						+ "'0' || round(a.TerminalBonusRate * 100, 2) || '%'  else round(a.TerminalBonusRate * 100, 2) || '%' "
						+ "end), "
						// 保单号, 不显示，但是下面计算现金价值需要
						+ "a.PolNo, "
						+ "(select trim(Name) from LABranchGroup where AgentGroup = (select trim(UpBranch) from LABranchGroup where AgentGroup =  (select trim(UpBranch) "
						+ "from LABranchGroup  where AgentGroup =  (select BranchCode from LAAgent where AgentCode = trim(b.AgentCode))))), "
						// 营销部
						+ "(select trim(Name) from LABranchGroup where AgentGroup =  (select trim(UpBranch) "
						+ "from LABranchGroup where AgentGroup =  (select BranchCode from LAAgent where AgentCode = trim(b.AgentCode)))), "
						+ "(select trim(Name) from LAAgent where AgentCode = trim(b.AgentCode)), "
						// 业务员编号
						+ "b.AgentCode, "
						+ "(select Name from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 4)) 分公司名称, "
						// 公司地址
						+ "(select Address from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 6)) 中心支公司公司地址, "
						// 公司邮编
						+ "(select ZipCode from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 6)) 中心支公司公司邮编, "
						// 咨询电话
						+ "'95567' 客服电话, Decode(trim(b.Salechnl), '1', '个险', '3', '银代', '') 销售渠道,substr(b.Managecom, 1, 6) 中心支公司代码, "
						+ "(select Address from LDCom where trim(ComCode) = substr(b.ManageCom, 1, 4)) 分公司公司地址 ");
		// 银行网点,银行网点名称
		if (risktype.equals("B")) {
			mSql.append( // 银行网点,银行网点名称
					",(Select name From lacom Where agentcom=b.agentcom), trim(b.agentcom) ");
		} else {
			mSql.append(",' ',' ' ");
		}

		mSql.append(",to_char(a.SDispatchDate,'yyyy-mm-dd') from LOEngBonusPol a, LCPol b "
						+ " where a.DispatchType in ('0', '1') and "
						+ " a.aDispatchDate between to_date('"
						+ startdate
						+ "', 'yyyy-mm-dd') and to_date('"
						+ enddate
						+ "', 'yyyy-mm-dd') and "
						+ " not Exists (select 1 from bonuscontlog where contno=b.contno and a.Fiscalyear=Fiscalyear) and "
						+ " a.Fiscalyear = '2006' and b.ManageCom like '"
						+ managecom
						+ "%' and "
						+ " Exists (Select 1 From lmriskapp Where mngcom ='"
						+ risktype
						+ "' and b.riskcode=riskcode) and "
						+ " not exists (select 1 from lccontstate where contno =b.contno and polno=b.polno and STATETYPE='Terminate' and STATE='1' and ENDDATE is null) and "
						+ " b.PolNo = a.PolNo and b.polno=b.mainpolno and "
						+ " not Exists (Select 1 From Lpedoritem Where Lpedoritem.Edorstate = '0' And contno=b.contno And Lpedoritem.Edortype = 'PU') "
						+ othersql);
		return mSql;
	}

	// ==========================================================================
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		VData inVData = new VData();
		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("StartDate", "2007-06-09");
		transferData1.setNameAndValue("EndDate", "2007-06-16");

		transferData1.setNameAndValue("SaleChnl", "1");
		transferData1.setNameAndValue("ManageCom", "86");

		inVData.add(transferData1);
		inVData.add(tGlobalInput);
		PersonBonusPrintDateTxtBL tPersonBonusPrintDateTxtBL = new PersonBonusPrintDateTxtBL();

		if (!tPersonBonusPrintDateTxtBL.submitData(inVData, "")) {

			logger.debug("Submit Failed! ");
		} else {

			logger.debug("Submit Succed!");
		}

	}

} // class PersonBonusPrintDateTxtBL end
