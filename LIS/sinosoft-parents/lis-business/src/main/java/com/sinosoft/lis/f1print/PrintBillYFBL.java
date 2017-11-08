package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LYReturnFromBankBDB;
import com.sinosoft.lis.db.LYReturnFromBankDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LDCode1Schema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.schema.LYReturnFromBankSchema;
import com.sinosoft.lis.vschema.LDBankSet;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LYReturnFromBankBSet;
import com.sinosoft.lis.vschema.LYReturnFromBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :
 * @version 1.0
 * @date 2003-02-17
 */

public class PrintBillYFBL {
private static Logger logger = Logger.getLogger(PrintBillYFBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strBillNo;
	private String strBankCode;

	private String mBankName; // 银行名称
	private String mErrorReason; // 失败原因
	private String mChkSuccFlag; // 银行校验成功标志；
	private String mChkFailFlag; // 银行校验失败标志；
	private String mStation = "";
	private PremBankPubFun mPremBankPubFun = new PremBankPubFun();
	
	public PrintBillYFBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		logger.debug("getInputData begin");
		strBillNo = (String) cInputData.get(0);
		strBankCode = (String) cInputData.get(1);
		mStation = (String) cInputData.get(2);
		
		logger.debug("在PrintBillBL.java中得到的批次号码是" + strBillNo);
//		logger.debug("在PrintBillBL.java中得到的银行代码是" + strBankCode);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PayNoticeF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {
		logger.debug("查询银行名称的函数！！！");
		LDBankDB tLDBankDB = new LDBankDB();
		tLDBankDB.setBankCode(strBankCode);
		LDBankSet tLDBankSet = new LDBankSet();
		tLDBankSet.set(tLDBankDB.query());
		int bank_count = tLDBankSet.size();
		if (bank_count == 0) {
			this.mErrors.copyAllErrors(tLDBankDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "PrintBillBL";
			tError.functionName = "submitData";
			tError.errorMessage = "银行信息表查询失败，没有该银行代码！！！";
			this.mErrors.addOneError(tError);
			return false;
		}
		LDBankSchema tLDBankSchema = new LDBankSchema();
		tLDBankSchema.setSchema(tLDBankSet.get(1));
		mBankName = tLDBankSchema.getBankName();
		logger.debug("打印出银行信息和批次号码！！！");
		ListTable tlistTable = new ListTable();
		tlistTable.setName("MODE");

		String mSuccFlag = "'0000'";
		if (mSuccFlag == "") {
			buildError("getPrintRightData()", "无法获得银行" + strBankCode + "的成功标志！");
			return false;
		}

		logger.debug("对银行返回盘记录表的查询");
		LYReturnFromBankDB tLYReturnFromBankDB = new LYReturnFromBankDB();
		LYReturnFromBankSet tLYReturnFromBankSet = new LYReturnFromBankSet();
		String y_sql = "select * from LYReturnFromBank where SerialNo = '"
				+ "?SerialNo?" + "' and BankSuccFlag not in ('?mSuccFlag?')"
				+ " and comcode like concat('?mStation?','%') ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(y_sql);
		sqlbv1.put("SerialNo", strBillNo.trim());
		sqlbv1.put("mSuccFlag", mSuccFlag);
		sqlbv1.put("mStation", mStation);
		tLYReturnFromBankSet = tLYReturnFromBankDB.executeQuery(sqlbv1);
		logger.debug("在返回盘中所执行的操作是" + y_sql);
		int n = tLYReturnFromBankSet.size();
		if (n == 0) {
			// 定义总金额和总笔数的变量
			double SumMoney_b = 0;
			int SumCount_b = 0;
			logger.debug("该次交易可能被核销，在银行返回盘记录备份表中进行查询");
			LYReturnFromBankBDB tLYReturnFromBankBDB = new LYReturnFromBankBDB();
			LYReturnFromBankBSet tLYReturnFromBankBSet = new LYReturnFromBankBSet();
			String t_sql = "select * from LYReturnFromBankB where SerialNo = '"
					+ "?SerialNo?" + "' and BankSuccFlag not in ("
					+ "?mSuccFlag?" + ")"
					+ " and comcode like concat('"+"?mStation?"+"','%') ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(t_sql);
			sqlbv2.put("SerialNo", strBillNo.trim());
			sqlbv2.put("mSuccFlag", mSuccFlag);
			sqlbv2.put("mStation", mStation);
			tLYReturnFromBankBSet = tLYReturnFromBankBDB.executeQuery(sqlbv2);
			logger.debug("在备份表中所执行的查询语句是" + t_sql);
			int m = tLYReturnFromBankBSet.size();
			if (m == 0) {
				this.mErrors.copyAllErrors(tLYReturnFromBankBDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "PrintBillBL";
				tError.functionName = "submitData";
				tError.errorMessage = "没有满足条件的信息！！！";
				this.mErrors.addOneError(tError);
				return false;
			}
			int b_count;
			b_count = 1;
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("PrintYFBill.vts", "printer");
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");
			for (int j = 1; j <= m; j++) {
				logger.debug("在银行返回盘记录备份表的处理中此时的J的数值是￥￥￥￥" + j);
				String[] cols = new String[11];
				LYReturnFromBankBSchema tLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
				tLYReturnFromBankBSchema
						.setSchema(tLYReturnFromBankBSet.get(j));
				String tbPayCode = tLYReturnFromBankBSchema.getPayCode();
				String b_BankSuccFlag;
				if (tLYReturnFromBankBSchema.getBankSuccFlag() == null
						|| tLYReturnFromBankBSchema.getBankSuccFlag()
								.equals(""))
					b_BankSuccFlag = "1";
				else {
					b_BankSuccFlag = tLYReturnFromBankBSchema.getBankSuccFlag();
				}
				// logger.debug("银行的成功标志是"+b_BankSuccFlag);
				// logger.debug("采用了胡博的方法");
				// String hq_flag_b = getBankSuccFlag(strBankCode);
				// logger.debug("获取的成功标志是"+hq_flag_b);
				// boolean jy_flag_b =
				// verifyBankSuccFlag(hq_flag_b,b_BankSuccFlag);
				//
				// logger.debug("校验后的成功标志是"+jy_flag_b);
				// if(jy_flag_b)
				// continue;

				b_count++;
				SumCount_b++;
				cols[6] = mPremBankPubFun.getErrInfo("bankerror", strBankCode,
						b_BankSuccFlag);// 失败原因
				cols[0] = tLYReturnFromBankBSchema.getAgentCode();
				cols[3] = tLYReturnFromBankBSchema.getAccNo();
				cols[4] = tLYReturnFromBankBSchema.getAccName();
				cols[9] = tLYReturnFromBankBSchema.getPolNo();
				cols[10] = String.valueOf(tLYReturnFromBankBSchema
						.getPayMoney());
				SumMoney_b = SumMoney_b
						+ tLYReturnFromBankBSchema.getPayMoney();
				logger.debug("银行的账号是"
						+ tLYReturnFromBankBSchema.getAccNo());
				logger.debug("银行的账号名是"
						+ tLYReturnFromBankBSchema.getAccName());
				logger.debug("代理人编码是" + cols[0]);
				logger.debug("当J的值是" + j + "时，银行的账号是" + cols[3]);
				logger.debug("当J的值是" + j + "时，银行的账号名是" + cols[4]);
				logger.debug("当J的值是" + j + "时，错误原因是" + cols[6]);
				if (!(cols[0].equals("") || cols[0] == null)) {
					logger.debug("查询出代理人的详细信息！！！");
					// 根据代理人的编码查询出代理人的姓名
					String AgentName_sql = "Select Name,AgentGroup from LAAgent Where AgentCode = '"
							+ "?AgentCode?" + "'";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(AgentName_sql);
					sqlbv3.put("AgentCode", cols[0]);
					ExeSQL exesql = new ExeSQL();
					SSRS AgentName_ssrs = exesql.execSQL(sqlbv3);
					// 查询出代理人组别的名称
					String AgentGroup_sql = "Select Name from LABranchGroup Where AgentGroup = '"
							+ "?AgentGroup?" + "'";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(AgentGroup_sql);
					sqlbv4.put("AgentGroup",  AgentName_ssrs.GetText(1, 2));
					SSRS AgentGroup_ssrs = exesql.execSQL(sqlbv4);
					if (AgentName_ssrs.getMaxRow() == 0) {
						cols[1] = "无";
					} else {
						cols[1] = AgentName_ssrs.GetText(1, 1);
						logger.debug("代理人的姓名是"
								+ AgentName_ssrs.GetText(1, 1));
					}
					if (AgentGroup_ssrs.getMaxRow() == 0) {
						cols[2] = "无";
					} else {
						cols[2] = AgentGroup_ssrs.GetText(1, 1);
						logger.debug("代理人组别是"
								+ AgentGroup_ssrs.GetText(1, 1));
					}
				}

				LJAGetDB tLJAGetDB = new LJAGetDB();
				tLJAGetDB.setActuGetNo(tbPayCode);
				tLJAGetDB.setBankCode(strBankCode);
				LJAGetSet tLJAGetSet = new LJAGetSet();
				tLJAGetSet.set(tLJAGetDB.query());
				int a = tLJAGetSet.size();
				if (a != 0) {
					LJAGetSchema tLJAGetSchema = new LJAGetSchema();
					tLJAGetSchema.setSchema(tLJAGetSet.get(1));
					cols[5] = String.valueOf(tLJAGetSchema.getSendBankCount());
					logger.debug("总失败次数是"
							+ tLJAGetSchema.getSendBankCount());
				}
				// 对投保人和投保人的联系方式进行附值
				// 查询出投保人的客户号码
//				ExeSQL exesql = new ExeSQL();
//				String AppntNo_sql = "select '000000' from dual ";
//				String t_NoType = tLYReturnFromBankBSchema.getNoType();
//				if (!(t_NoType == null || t_NoType.equals("")))
//				// if(!(t_NoType.equals("")||t_NoType==null))
//				{
//					if (t_NoType.equals("0")) {
//						AppntNo_sql = "select '000000' from dual ";
//					}
//					logger.debug("2003-11-06标志是" + t_NoType);
//					if (t_NoType.equals("1")) {
//						logger.debug("NoType==1时代表集体保单号码");
//						AppntNo_sql = "Select AppntNo From LCPol Where GrpPolNo = '"
//								+ tLYReturnFromBankBSchema.getPolNo() + "'";
//					}
//					if (t_NoType.equals("2")) {
//						logger.debug("NoType==2时代表的是个人保单号");
//						AppntNo_sql = "Select AppntNo From LCPol Where PolNo = '"
//								+ tLYReturnFromBankBSchema.getPolNo() + "'";
//					}
//					// if(t_NoType.equals("3"))
//					// {
//					// logger.debug("NoType==3时代表的是个人批改号");
//					// }
//					if (t_NoType.equals("4")) {
//						logger.debug("NoType==4时代表的是合同投保单号");
//						AppntNo_sql = "Select AppntNo From LCPol Where ContNo = '"
//								+ tLYReturnFromBankBSchema.getPolNo() + "'";
//
//					}
//					// if(t_NoType.equals("5"))
//					// {
//					// logger.debug("NoType==5时代表的是集体投保单号");
//					//
//					// }
//					if (t_NoType.equals("6")) {
//						logger.debug("NoType==6时代表的是个人投保单号");
//						AppntNo_sql = "Select AppntNo From LCPol Where ProposalNo = '"
//								+ tLYReturnFromBankBSchema.getPolNo() + "'";
//					}
//					// if(t_NoType.equals("7"))
//					// {
//					// logger.debug("NoType==7时代表的是表示合同印刷号");
//					// }
//					// if(t_NoType.equals("8"))
//					// {
//					// logger.debug("NoType==8时代表的是表示集体印刷号");
//					// }
//					if (t_NoType.equals("9")) {
//						logger.debug("NoType==9时代表的是表示个人印刷号");
//						AppntNo_sql = "Select AppntNo From LCPol Where PrtNo = '"
//								+ tLYReturnFromBankBSchema.getPolNo() + "'";
//					}

					// else
					// {
					//
					// }
//				}

				// String AppntNo_sql =
				// "Select AppntNo From LJAGet Where ActuGetNo = '"
				// +tLYReturnFromBankBSchema.getPayCode()+"'";
//				logger.debug("2003-11-07出错之前的信息");
//				SSRS AppntNo_ssrs = exesql.execSQL(AppntNo_sql);
//				logger.debug("2003-11-07查询到的结果集是"
//						+ AppntNo_ssrs.getMaxRow());
				// logger.debug("备份表中的投保人客户号是"+AppntNo_ssrs.GetText(1,1));
				// 查询出投保人的名称和联系方式

//				String AppntName_sql = "Select Name,Mobile from LCAppntInd Where CustomerNo = '"
//						+ AppntNo_ssrs.GetText(1, 1) + "'";
//
//				SSRS AppntName_ssrs = exesql.execSQL(AppntName_sql);

//				if (AppntName_ssrs.getMaxRow() == 0) {
					cols[7] = "无";
//				} else {
//					cols[7] = AppntName_ssrs.GetText(1, 1);
//					logger.debug("投保人的信息如下：投保人的姓名是"
//							+ AppntName_ssrs.GetText(1, 1));
//				}
//				if (AppntName_ssrs.getMaxRow() == 0) {
					cols[8] = "无";
//				} else {
//					cols[8] = AppntName_ssrs.GetText(1, 2);
//					System.out
//							.println("投保人的名称是" + AppntName_ssrs.GetText(1, 2));
//				}
				alistTable.add(cols);
			}
			String[] col = new String[11];
			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, col);
			texttag.add("BillNo", strBillNo);
			texttag.add("BankCode", strBankCode);
			texttag.add("BankName", mBankName);
			texttag.add("Date", PubFun.getCurrentDate());
			texttag.add("SumMoney", SumMoney_b);
			texttag.add("SumCount", SumCount_b);
			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);

			// xmlexport.outputDocumentToFile("e:\\","PrintYFBill");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

			logger.debug("￥￥￥￥￥￥b_count的值是====" + b_count);
			if (b_count == 1) {
				this.mErrors.copyAllErrors(tLDBankDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "PrintBillBL";
				tError.functionName = "submitData";
				tError.errorMessage = "没有失败的信息！！！不能进行打印！！！";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			// 定义记录总金额和总笔数的变量
			double SumMoney = 0;
			int SumCount = 0;
			int _count;
			_count = 1;

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("PrintYFBill.vts", "printer");
			ListTable blistTable = new ListTable();
			blistTable.setName("INFO");
			for (int i = 1; i <= n; i++) {
				LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
				tLYReturnFromBankSchema.setSchema(tLYReturnFromBankSet.get(i));
				// 记录账号名和账户
				String[] cols = new String[11];
				String mBankSuccFlag;
				// String _BankSuccFlag ;
				// if(tLYReturnFromBankSchema.getBankSuccFlag().equals("")||tLYReturnFromBankSchema.getBankSuccFlag()==null)
				// _BankSuccFlag = "1";
				// else
				// {
				// _BankSuccFlag = tLYReturnFromBankSchema.getBankSuccFlag();
				// }
				// logger.debug("银行的成功标志是"+_BankSuccFlag);
				// logger.debug("采用了胡博的方法后回盘表的数据");
				// logger.debug("银行的成功标志是"+_BankSuccFlag);
				// String hq_flag = getBankSuccFlag(strBankCode);
				// logger.debug("获取的成功标志是"+hq_flag);
				// boolean jy_flag = verifyBankSuccFlag(hq_flag,_BankSuccFlag);
				// logger.debug("银行的校验标志是"+jy_flag);
				// if(jy_flag)
				// continue;
				_count++;
				SumCount++;
				String tPayCode = tLYReturnFromBankSchema.getPayCode();
				mBankSuccFlag = tLYReturnFromBankSchema.getBankSuccFlag();
				LDCode1DB mLDCode1DB = new LDCode1DB();
				mLDCode1DB.setCodeType("bankerror");
				mLDCode1DB.setCode(strBankCode);
				mLDCode1DB.setCode1(mBankSuccFlag);
				// mLDCode1DB.setCodeName(mBankSuccFlag);
				LDCode1Set mLDCode1Set = new LDCode1Set();
				LDCode1Schema mLDCode1Schema = new LDCode1Schema();
				mLDCode1Set.set(mLDCode1DB.query());
				int c = mLDCode1Set.size();
				String errorReason;
				if (c == 0)
					errorReason = "没有指明错误原因";
				else {
					mLDCode1Schema.setSchema(mLDCode1Set.get(1));
					errorReason = mLDCode1Schema.getCodeName();
					logger.debug("查询应收总表，查询出代理人信息");
				}
				logger.debug("失败原因是" + errorReason);
				cols[0] = tLYReturnFromBankSchema.getAgentCode();
				cols[3] = tLYReturnFromBankSchema.getAccNo();
				cols[4] = tLYReturnFromBankSchema.getAccName();
				cols[6] = errorReason;
				cols[9] = tLYReturnFromBankSchema.getPolNo();
				cols[10] = String
						.valueOf(tLYReturnFromBankSchema.getPayMoney());
				SumMoney = SumMoney + tLYReturnFromBankSchema.getPayMoney();
				if (!(cols[0].equals("") || cols[0] == null)) {
					logger.debug("查询出代理人的详细信息！！！");
					// 根据代理人的编码查询出代理人的姓名
					String AgentName_sql = "Select Name,AgentGroup from LAAgent Where AgentCode = '"
							+ "?AgentCode?" + "'";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(AgentName_sql);
					sqlbv5.put("AgentCode", cols[0]);
					ExeSQL exesql = new ExeSQL();
					SSRS AgentName_ssrs = exesql.execSQL(sqlbv5);
					// 查询出代理人组别的名称
					String AgentGroup_sql = "Select Name from LABranchGroup Where AgentGroup = '"
							+ "?AgentGroup?" + "'";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(AgentGroup_sql);
					sqlbv6.put("AgentGroup", AgentName_ssrs.GetText(1, 2));
					SSRS AgentGroup_ssrs = exesql.execSQL(sqlbv6);
					if (AgentName_ssrs.getMaxRow() == 0) {
						cols[1] = "无";
					} else {
						cols[1] = AgentName_ssrs.GetText(1, 1);
						logger.debug("代理人的姓名是"
								+ AgentName_ssrs.GetText(1, 1));
					}
					if (AgentGroup_ssrs.getMaxRow() == 0) {
						cols[2] = "无";
					} else {
						cols[2] = AgentGroup_ssrs.GetText(1, 1);
						logger.debug("代理人组别是"
								+ AgentGroup_ssrs.GetText(1, 1));
					}
				}

				LJAGetDB tLJAGetDB = new LJAGetDB();
				tLJAGetDB.setActuGetNo(tPayCode);
				tLJAGetDB.setBankCode(strBankCode);
				LJAGetSet tLJAGetSet = new LJAGetSet();
				tLJAGetSet.set(tLJAGetDB.query());
				int a = tLJAGetSet.size();
				if (a != 0) {
					LJAGetSchema tLJAGetSchema = new LJAGetSchema();
					tLJAGetSchema.setSchema(tLJAGetSet.get(1));
					cols[5] = String.valueOf(tLJAGetSchema.getSendBankCount());
					logger.debug("总失败次数是"
							+ tLJAGetSchema.getSendBankCount());
				}
				// 对投保人和投保人的联系方式进行附值
				// 查询出投保人的客户号码
//				ExeSQL exesql = new ExeSQL();
//				String AppntNo_sql = "";
//				String t_NoType = tLYReturnFromBankSchema.getNoType();
//				if (!(t_NoType.equals("") || t_NoType == null)) {
//					if (t_NoType.equals("0")) {
//						AppntNo_sql = "select '000000' from dual ";
//					}
//					if (t_NoType.equals("1")) {
//						logger.debug("NoType==1时代表集体保单号码");
//						AppntNo_sql = "Select AppntNo From LCPol Where GrpPolNo = '"
//								+ tLYReturnFromBankSchema.getPolNo() + "'";
//					}
//					if (t_NoType.equals("2")) {
//						logger.debug("NoType==2时代表的是个人保单号");
//						AppntNo_sql = "Select AppntNo From LCPol Where PolNo = '"
//								+ tLYReturnFromBankSchema.getPolNo() + "'";
//					}
//					if (t_NoType.equals("4")) {
//						logger.debug("NoType==4时代表的是合同投保单号");
//						AppntNo_sql = "Select AppntNo From LCPol Where ContNo = '"
//								+ tLYReturnFromBankSchema.getPolNo() + "'";
//					}
//					if (t_NoType.equals("6")) {
//						logger.debug("NoType==6时代表的是个人投保单号");
//						AppntNo_sql = "Select AppntNo From LCPol Where ProposalNo = '"
//								+ tLYReturnFromBankSchema.getPolNo() + "'";
//					}
//					if (t_NoType.equals("9")) {
//						logger.debug("NoType==9时代表的是表示个人印刷号");
//						AppntNo_sql = "Select AppntNo From LCPol Where PrtNo = '"
//								+ tLYReturnFromBankSchema.getPolNo() + "'";
//					}
//
//				}
//
//				SSRS AppntNo_ssrs = exesql.execSQL(AppntNo_sql);
//				logger.debug("备份表中的投保人客户号是" + AppntNo_ssrs.GetText(1, 1));
//				// 查询出投保人的名称和联系方式
//				String AppntName_sql = "Select Name,Mobile from LCAppntInd Where CustomerNo = '"
//						+ AppntNo_ssrs.GetText(1, 1) + "'";
//				SSRS AppntName_ssrs = exesql.execSQL(AppntName_sql);
//				if (AppntName_ssrs.getMaxRow() == 0) {
					cols[7] = "无";
//				} else {
//					cols[7] = AppntName_ssrs.GetText(1, 1);
//					logger.debug("投保人的信息如下：投保人的姓名是"
//							+ AppntName_ssrs.GetText(1, 1));
//				}
//				if (AppntName_ssrs.getMaxRow() == 0) {
					cols[8] = "无";
//				} else {
//					cols[8] = AppntName_ssrs.GetText(1, 2);
//					System.out
//							.println("投保人的名称是" + AppntName_ssrs.GetText(1, 2));
//				}

				blistTable.add(cols);
				logger.debug("getPrintData end");
			}
			String[] b_col = new String[11];
			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(blistTable, b_col);
			texttag.add("BillNo", strBillNo);
			texttag.add("BankCode", strBankCode);
			texttag.add("BankName", mBankName);
			texttag.add("Date", PubFun.getCurrentDate());
			texttag.add("SumMoney", SumMoney);
			texttag.add("SumCount", SumCount);
			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:\\","PrintYFBill");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			logger.debug("getPrintData begin");
		}
		// 对变量进行附值
		return true;
	}


	public static void main(String[] args) {
		String tBillNo = "00000000000000000036";
		String tBankCode = "01";
		String tMngCom = "86";
		VData tVData = new VData();
		tVData.addElement(tBillNo);
		tVData.addElement(tBankCode);
		tVData.addElement(tMngCom);
		PrintBillUI tPrintBillUI = new PrintBillUI();
		tPrintBillUI.submitData(tVData, "PRINT");
	}
}
