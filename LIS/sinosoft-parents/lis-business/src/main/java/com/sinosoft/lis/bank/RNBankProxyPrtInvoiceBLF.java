package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 银行代打发票
 * </p>
 * 
 * <p>
 * Description: 银行代打发票生成数据库文件模块
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author : guanwei
 * 
 * @version 1.0
 */
public class RNBankProxyPrtInvoiceBLF {
private static Logger logger = Logger.getLogger(RNBankProxyPrtInvoiceBLF.class);
	// 错误处理
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput tGI = new GlobalInput();
	/** 业务数据 */
	private String serialNo = "";
	private TransferData mTransferData = new TransferData();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mBankCode = "";
	private String mManageCom = "";

	/** 直接与数据库进行连接的变量 */
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private boolean mExistFlag = false;

	public VData getResult() {
		return mResult;
	}

	// 提交数据
	public boolean submitData(VData cInputData, String cOperate)
			throws SQLException {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "RNBankProxyPrtInvoiceBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!mExistFlag) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "RNBankProxyPrtInvoiceBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "没有满足该条件的数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 得到输入数据
	private boolean getInputData(VData mInputData) {
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 起始日期
		logger.debug("|||||||||StartDate||||||||" + mStartDate);
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 终止日期
		logger.debug("|||||||||mEndDate||||||||" + mEndDate);
		mBankCode = (String) mTransferData.getValueByName("BankCode"); // 银行代码
		logger.debug("|||||||||mBankCode||||||||" + mBankCode);
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		logger.debug("|||||||||mManageCom||||||||" + mManageCom);
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		return true;
	}

	// 处理数据
	private boolean dealData() throws SQLException {
		int i = 0;
		try {
			conn = DBConnPool.getConnection();
			logger.debug(conn.toString());
			st = conn.createStatement();
			mManageCom = tGI.ManageCom;

			String sSql = "insert into LYBankBill(SerialNo,BankCode,OtherNo,OtherNoType,"
					+ " GetNoticeNo,AppntNo,BankAccNo,AccName,PayMoney,PayCount,"
					+ " BankSuccDate,Operator,"
					+ " MakeDate,MakeTime, ModifyDate,ModifyTime,SendDate) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement tPreparedStatement = conn.prepareStatement(sSql);
			logger.debug("插入的语句为:" + sSql);

			// 联表查询LOPRTManager和LYReturnFromBankB，得到银行划款成功但未打印发票的数据
			String tSql = "select b.BankCode,b.AccNo,b.AccName,b.PayCode,b.BankDealDate,"
					+ " a.OtherNo,a.OtherNoType from LOPRTManager a, LYReturnFromBankB b "
					+ " where a.StandbyFlag1 = b.PayCode and a.Code = '32' "
					+ "   and not exists (select 'X' from LYBankBill where GetNoticeNo = b.PayCode)"
					+ "   and b.NoType in ('2', '3') and a.StateFlag = '0' "
					+ "   and b.BankSuccFlag = '0000' and b.BankDealDate between '"
					+ mStartDate
					+ "' and '"
					+ mEndDate
					+ "' "
					+ " and b.BankCode = '"
					+ mBankCode
					+ "'  and b.ComCode like '" + mManageCom + "%'";
//			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
//			sqlbv1.sql(tSql);
//			sqlbv1.put("a1", mStartDate);
//			sqlbv1.put("a2", mEndDate);
//			sqlbv1.put("a3", mBankCode);
			logger.debug("主查询的语句为:" + tSql);
			rs = st.executeQuery(tSql);

			boolean flag = false;
			while (rs.next()) {
				flag = true;
				logger.debug("flag======" + flag);
				String tBankCode = rs.getString("BankCode"); // 银行代码
				String tAccNo = rs.getString("AccNo"); // 银行账号
				String tAccName = rs.getString("AccName"); // 帐户名称
				String tGetNoticeNo = rs.getString("PayCode"); // 缴费通知书号
				String tOtherNo = rs.getString("OtherNo"); // 其它号码
				String tOtherNoType = rs.getString("OtherNoType"); // 其它号码类型
				String tBankDealDate = rs.getString("BankDealDate").substring(
						0, 10); // 银行扣款日期

				ExeSQL exeSql = new ExeSQL();
				String rSql = " select max(PayCount),sum(SumDuePayMoney)"
						+ "   from LJAPayPerson where GetNoticeNo = '"
						+ "?a5?" + "'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(rSql);
				sqlbv2.put("a5", tGetNoticeNo);
				SSRS tSSRS = exeSql.execSQL(sqlbv2);
				String tPayCount = "";
				String tPayMoney = "";
				if (tSSRS.getMaxRow() > 0 && !tSSRS.GetText(1, 1).equals("")
						&& !tSSRS.GetText(1, 1).equals("null")
						&& !tSSRS.GetText(1, 1).equals(null)
						&& tSSRS.GetText(1, 1).length() <= '0') {
					tPayCount = tSSRS.GetText(1, 1); // 缴费期数
					tPayMoney = tSSRS.GetText(1, 2); // 缴费金额
				} else {
					String bSql = " select max(PayCount),sum(SumDuePayMoney) "
							+ "   from  LJSPayPerson where GetNoticeNo = '"
							+ "?a6?" + "'";
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(bSql);
					sqlbv3.put("a6", tGetNoticeNo);
					SSRS bSSRS = exeSql.execSQL(sqlbv3);
					tPayCount = bSSRS.GetText(1, 1); // 缴费期数
					tPayMoney = bSSRS.GetText(1, 2); // 缴费金额
				}
				// String tCurPayToDate = tSSRS.GetText(1, 3); //当前交至日期
				// String tLastPayToDate = tSSRS.GetText(1, 4); //原交至日期
				String tCurrentDate = PubFun.getCurrentDate(); // 入机日期
				String tCurrentTime = PubFun.getCurrentTime(); // 入机时间
				String tOperator = tGI.Operator; // 操作员
				serialNo = PubFun1.CreateMaxNo("InvoiceSerialNo", 12); // 生成流水号

				// 查询LCPol
				String cSql = "select AppntNo from LCCont where ContNo = '"
						+ "?a7?" + "'";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(cSql);
				sqlbv4.put("a7", tOtherNo);
				SSRS nSSRS = new SSRS();
				nSSRS = exeSql.execSQL(sqlbv4);
				String tAppntNo = "";
				if (nSSRS.getMaxRow() > 0) {
					tAppntNo = nSSRS.GetText(1, 1); // 投保人客户号码
				}
				// 生成送银行表数据
				tPreparedStatement.setString(1, serialNo); // 流水号
				logger.debug("1, serialNo===========" + serialNo);
				tPreparedStatement.setString(2, tBankCode); // 银行编码
				logger.debug("tBankCode" + tBankCode);
				tPreparedStatement.setString(3, tOtherNo); // 其它号码
				logger.debug("tOtherNo" + tOtherNo);
				tPreparedStatement.setString(4, tOtherNoType); // 其它号码类型
				logger.debug("tOtherNoType" + tOtherNoType);
				tPreparedStatement.setString(5, tGetNoticeNo); // 交费通知书号
				logger.debug("tGetNoticeNo" + tGetNoticeNo);
				tPreparedStatement.setString(6, tAppntNo); // 投保人客户号码
				logger.debug("tAppntNo" + tAppntNo);
				tPreparedStatement.setString(7, tAccNo); // 银行账号
				logger.debug("tAccNo" + tAccNo);
				tPreparedStatement.setString(8, tAccName); // 帐户名称
				logger.debug("tAccName" + tAccName);
				tPreparedStatement.setString(9, tPayMoney); // 缴费金额
				logger.debug("tPayMoney" + tPayMoney);
				tPreparedStatement.setString(10, tPayCount); // 缴费期数
				logger.debug("tPayCount" + tPayCount);
				// tPreparedStatement.setString(11, tLastPayToDate); //原交至日期
				// tPreparedStatement.setString(12, tCurPayToDate); //入机日期
				tPreparedStatement.setString(11, tBankDealDate); // 投保人客户号码
				logger.debug("tBankDealDate" + tBankDealDate);
				tPreparedStatement.setString(12, tOperator); // 最后一次修改日期
				logger.debug("tOperator" + tOperator);
				tPreparedStatement.setString(13, tCurrentDate); // 入机日期
				logger.debug("tCurrentDate" + tCurrentDate);
				tPreparedStatement.setString(14, tCurrentTime); // 入机时间
				logger.debug("tCurrentTime" + tCurrentTime);
				tPreparedStatement.setString(15, tCurrentDate); // 最后一次修改日期
				logger.debug("tCurrentDate" + tCurrentDate);
				tPreparedStatement.setString(16, tCurrentTime); // 最后一次修改时间
				logger.debug("tCurrentTime" + tCurrentTime);
				tPreparedStatement.setString(17, tCurrentDate);
				logger.debug("tCurrentDate" + tCurrentDate);
				tPreparedStatement.executeUpdate();
				i++;
				mExistFlag = true;
				try {
					conn.commit();
				} catch (Exception ex) {
					logger.debug("问题 哎！咋就出问题了呢？");
					continue;
				}
			}
			logger.debug("++++++++++++++++++++++++++++++共插入了" + i + "条记录");
			rs.close();
			st.close();
			conn.close();
			tPreparedStatement.close();
			logger.debug("tPreparedStatement.close");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		RNBankProxyPrtInvoiceBLF tRNBankProxyPrtInvoiceBLF = new RNBankProxyPrtInvoiceBLF();
		logger.debug("!!!----GOGOGOGO----!!!");
		if (!tRNBankProxyPrtInvoiceBLF.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tRNBankProxyPrtInvoiceBLF.mErrors);
			return false;
		}
		mResult.clear();
		mResult = tRNBankProxyPrtInvoiceBLF.getResult();
		return true;
	}
}
