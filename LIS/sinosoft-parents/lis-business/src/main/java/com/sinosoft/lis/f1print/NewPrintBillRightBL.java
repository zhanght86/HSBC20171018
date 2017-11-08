package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.vschema.LCPolSet;
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
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function ://打印“银行代扣正确清单包括（首期和续期）”
 * @version 1.0
 * @date 2004-6-28
 */

public class NewPrintBillRightBL {
private static Logger logger = Logger.getLogger(NewPrintBillRightBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mSerialNo = "";
	private String mBankCode = "";
	private String mTFFlag = "";// 查询正确错误清单T--正确,F--错误
	private String mXQFlag = "";// X---续期S--首期
    private String mStation = "";
	  // private String mSuccFlag = "";//记录每个银行成功返回值的标志

	private int mCount = 0;
	private String mMessage = "";
	private double mSumDuePayMoney = 0.00;

	private PremBankPubFun mPremBankPubFun = new PremBankPubFun();

	public NewPrintBillRightBL() {
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
		if (mTFFlag.equals("T")) {
			if (!getPrintRightData()) {
				return false;
			}

		}
		if (mTFFlag.equals("F")) {
			if (!getPrintErrorData()) {
				return false;
			}
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mSerialNo = (String) cInputData.get(0);
		mTFFlag = (String) cInputData.get(1);
		mXQFlag = (String) cInputData.get(2);
		mBankCode = (String) cInputData.get(3);
		mStation = (String) cInputData.get(4);

		mSerialNo = mSerialNo.trim();
		mTFFlag = mTFFlag.trim();
		mXQFlag = mXQFlag.trim();
		mBankCode = mBankCode.trim();
		if(mStation==null || mStation.equals("")|| mStation.equals("null"))
			mStation="";
		else
			mStation = mStation.trim();
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PrintBillRightBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 查询出首期或者是续期的银行代收正确清单
	private boolean getPrintRightData() {
		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("PrintBillRight.vts", "printer");

		ListTable alistTable = new ListTable();
		alistTable.setName("INFO");

		// 转账成功的清单，要查询应收和实收的并集因为在没有进行核销时，仍为应收信息
		// 打印成功的清单

		// mSuccFlag = this.getSuccFlag(mBankCode);
		String mSuccFlag = "'0000'"; // 使用统一编码
		if (mSuccFlag == "") {
			buildError("getPrintRightData()", "无法获得银行" + mBankCode + "的成功标志！");
			return false;
		}
		String notype="1=1";
		if (mXQFlag.equals("S")) {
			notype = "notype not in('2','10')";
			mMessage = "银行没有返回首期交费成功清单记录";
		} else if (mXQFlag.equals("X")) {
			notype = "notype = '2'";
			mMessage = "银行没有返回续期交费成功清单记录";
		} else if (mXQFlag.equals("B")) {
			notype = "notype = '10'";
			mMessage = "银行没有返回保全交费成功清单记录";
		}
		String main_sql = "select b.paycode,b.agentcode,(select agentgroup from laagent where agentcode=b.agentcode),b.AccNo,b.AccName,"
				+ "(select sum(paymoney) from ljtempfee where tempfeeno=b.paycode),b.polno,b.riskcode,b.notype "
				+ " from lyreturnfrombankb b where b.serialno = '"
				+ "?mSerialNo?"
				+ "'  and "
				+ notype
				+ " and exists(select 1 from ljtempfee where tempfeeno = b.paycode and enteraccdate is not null) "
				+ " and comcode like concat('"+"?mStation?"+"','%') "
				+ " and BankSuccFlag in ("
				+ mSuccFlag
				+ ") and DealType = 'S'"
				+ " union all "
				+ "select b.paycode,b.agentcode,(select agentgroup from laagent where agentcode=b.agentcode),b.AccNo,b.AccName,"
//				+ "(select sum(paymoney) from ljtempfee where tempfeeno=b.paycode),b.polno,b.riskcode,b.notype "
				+ "paymoney,b.polno,b.riskcode,b.notype "
				+ " from lyreturnfrombank b where b.serialno = '"
				+ "?mSerialNo?"
				+ "'  and "
				+ notype
//				+ " and exists(select 1 from ljtempfee where tempfeeno = b.paycode and enteraccdate is not null) "
				+ " and comcode like concat('"+"?mStation?"+"','%') "
				+ "and BankSuccFlag in (" + mSuccFlag + ") and DealType = 'S'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(main_sql);
		sqlbv1.put("mSerialNo", mSerialNo);
		sqlbv1.put("mStation", mStation);
		logger.debug("主查询语句是" + main_sql);
		ExeSQL main_exesql = new ExeSQL();
		SSRS main_ssrs = main_exesql.execSQL(sqlbv1);
		if (main_ssrs.getMaxRow() > 0) {
			logger.debug("查询到的记录是 ：" + main_ssrs.getMaxRow());
			for (int i = 1; i <= main_ssrs.getMaxRow(); i++) {
				// 对其明细信息进行附值
				String[] cols = new String[10];
				cols[0] = main_ssrs.GetText(i, 2);// 业务员编码
				cols[1] = mPremBankPubFun.getAgentInfo(main_ssrs.GetText(i, 2))
						.getName().trim();// 业务员姓名
				cols[2] = mPremBankPubFun
						.getAgentGroup(main_ssrs.GetText(i, 3)).trim();// 业务原组别
				cols[3] = main_ssrs.GetText(i, 4).trim();// 银行账号
				cols[4] = main_ssrs.GetText(i, 5).trim();// 银行户名
				cols[5] = main_ssrs.GetText(i, 6).trim();// 累计金额
				cols[6] = main_ssrs.GetText(i, 7).trim();// 保单号
				if (mXQFlag.equals("X")) {
					String sql = "select a.agentcode,name from laagent a,lradimascription b where a.agentcode=b.agentcode and polno='"
							+ "?polno?"
							+ "' union all select a.agentcode,name from laagent a,lrascription b where a.agentcode=b.agentnew and b.oldpolno='"
							+ "?polno?" + "'";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(sql);
					sqlbv2.put("polno", main_ssrs.GetText(i, 7));
					SSRS tSSRS = new ExeSQL().execSQL(sqlbv2);
					if (tSSRS.getMaxRow() == 1) {
						cols[7] = tSSRS.GetText(1, 1);
						cols[8] = tSSRS.GetText(1, 2);
					}
					
				}
				
					String sql = "select name from lacom where agentcom in  ( "
						+ " select distinct agentcom from lccont where prtno in (select otherno from ljtempfee where tempfeeno='"	+ "?tempfeeno?" + "' ) and agentcom is not null "
						+ " union all "
						+ " select distinct agentcom from lccont where contno in (select otherno from ljtempfee where tempfeeno='"	+ "?tempfeeno?" + "' ) and agentcom is not null )";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(sql);
					sqlbv3.put("tempfeeno", main_ssrs.GetText(i, 1));
					SSRS tSSRS = new ExeSQL().execSQL(sqlbv3);
					if (tSSRS.getMaxRow() >= 1) {
						cols[9] = tSSRS.GetText(1, 1);
					}

				mCount = mCount + 1;
				alistTable.add(cols);
				mSumDuePayMoney = mSumDuePayMoney + Double.parseDouble(cols[5]);
			}
		}
		if (mCount > 0) {
			logger.debug("开始填充数据！");
			String[] b_col = new String[9];
			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, b_col);
			texttag.add("BillNo", mSerialNo);
			texttag.add("Date", PubFun.getCurrentDate());
			texttag.add("BankCode", mBankCode);
			texttag.add("BankName", mPremBankPubFun.getBankInfo(mBankCode)
					.getBankName().trim());
			texttag.add("SumDuePayMoney", mSumDuePayMoney);
			texttag.add("Count", mCount);
			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:\\","NewPrintBillRightBL");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;
		} else {
			CError.buildErr(this, mMessage);
			return false;
		}
	}

	private boolean getPrintErrorData() {
		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

		xmlexport.createDocument("PrintBillExtra.vts", "printer");

		ListTable alistTable = new ListTable();
		alistTable.setName("INFO");

		// 转账成功的清单，要查询应收和实收的并集因为在没有进行核销时，仍为应收信息
		// 打印成功的清单
		// mSuccFlag = this.getSuccFlag(mBankCode);
		String mSuccFlag = "'0000'"; // 使用统一编码
		if (mSuccFlag == "") {
			buildError("getPrintErrorData()", "无法获得银行" + mBankCode + "的成功标志！");
			return false;
		}

		String notype="1=1";
		if (mXQFlag.equals("S")) {
			notype = "notype not in('2','10')";
			mMessage = "银行没有返回首期交费失败清单记录";
		} else if (mXQFlag.equals("X")) {
			notype = "notype = '2'";
			mMessage = "银行没有返回续期交费失败清单记录";
		} else if (mXQFlag.equals("B")) {
			notype = "notype = '10'";
			mMessage = "银行没有返回保全交费失败清单记录";
		}

		String main_sql = "select b.paycode,b.agentcode,b.AccNo,b.AccName,"
				+ "paymoney,BankSuccFlag,b.polno,b.notype "
				+ " from lyreturnfrombankb b where b.serialno = '" + "?mSerialNo?"
				+ "'  and " + notype + " and BankSuccFlag not in (" + mSuccFlag
				+ ") and DealType = 'S'" 
				+ " and comcode like concat('"+"?mStation?"+"','%') " + " union all "
				+ "select b.paycode,b.agentcode,b.AccNo,b.AccName,"
				+ "paymoney,BankSuccFlag,b.polno,b.notype "
				+ " from lyreturnfrombank b where b.serialno = '" + "?mSerialNo?"
				+ "'  and " + notype + " and BankSuccFlag not in (" + mSuccFlag
				+ ") and DealType = 'S'" + " and comcode like concat('"+"?mStation?"+"','%') ";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(main_sql);
		sqlbv4.put("mSerialNo", mSerialNo);
		sqlbv4.put("mStation", mStation);
		logger.debug("查询的语句是" + main_sql);
		ExeSQL sec_exesql = new ExeSQL();
		SSRS sec_ssrs = sec_exesql.execSQL(sqlbv4);
		if (sec_ssrs.getMaxRow() > 0) {
			logger.debug("查询的结果是" + sec_ssrs.getMaxRow());
			for (int thr_count = 1; thr_count <= sec_ssrs.getMaxRow(); thr_count++) {
				String[] cols = new String[15];
				cols[0] = sec_ssrs.GetText(thr_count, 2);// 业务员编码
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(sec_ssrs.GetText(thr_count, 2).trim());
				if (!tLAAgentDB.getInfo()) {
					// buildError("getPrintErrorData()","无法获得代理员"
					// +sec_ssrs.GetText(thr_count,1)+ "的信息！");
					// continue; //继续循环下一条记录
					cols[1] = "(无法查到)";
				} else {
					LAAgentSchema tLAAgentSchema = new LAAgentSchema();
					tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
					cols[1] = tLAAgentSchema.getName();
					// 进行查询
					cols[2] = mPremBankPubFun.getAgentGroup(tLAAgentSchema
							.getAgentGroup());// 业务原组别
				}
				cols[3] = sec_ssrs.GetText(thr_count, 3); // 银行账号
				cols[4] = sec_ssrs.GetText(thr_count, 4); // 银行户名
				// 查询ljspay中的数据
				LJSPayDB tLJSPayDB = new LJSPayDB();
				tLJSPayDB.setGetNoticeNo(sec_ssrs.GetText(thr_count, 1));
				if (tLJSPayDB.getInfo()) {
					cols[5] = String.valueOf(tLJSPayDB.getSendBankCount());
				} else {
					cols[5] = "Z";
				}
				cols[6] = mPremBankPubFun.getErrInfo("bankerror", mBankCode,
						sec_ssrs.GetText(thr_count, 6));// 失败原因
				if ((cols[6] == null) || (cols[6].equals(""))) {
					cols[6] = "(无:" + sec_ssrs.GetText(thr_count, 6) + ")";
				}
				// logger.debug("投保人信息"+sec_ssrs.GetText(1,8));
				String othernotype = sec_ssrs.GetText(thr_count, 8);
				String polno = sec_ssrs.GetText(thr_count, 7);
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				String mPolNo=polno;
				if (othernotype.equals("1")) {
					// 集体保单号码
					tLCPolDB.setGrpPolNo(polno);
				} else if (othernotype.equals("2")) {
					// 个人保单号码
					tLCPolDB.setPolNo(polno);
				} else if (othernotype.equals("10")) {
					// 保全申请号码
					String sql = "select contno from lpedormain where edorappno='"
							+ "?polno?" + "'";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(sql);
					sqlbv5.put("polno", polno);
					tLCPolDB.setContNo(new ExeSQL().getOneValue(sqlbv5));
					mPolNo=new ExeSQL().getOneValue(sqlbv5);
				} else if (othernotype.equals("6")) {
					tLCPolDB.setProposalNo(polno);
				} else if (othernotype.equals("9")) {
					tLCPolDB.setPrtNo(polno);
				} else{
					logger.debug("This is an Exception: otherno is "+othernotype);
					tLCPolDB.setContNo("nothing");
				}
				tLCPolSet.set(tLCPolDB.query());
				if (tLCPolSet.size() > 0) {
					LCAppntIndSchema appntInfo = mPremBankPubFun
							.getAppntInfo(tLCPolSet.get(1).getAppntNo());
					cols[7] = appntInfo.getName();
					cols[8] = appntInfo.getPhone();// 投保人的联系方式
					cols[11] = String.valueOf(tLCPolSet.get(1).getPayIntv());
					if (othernotype.equals("2")) {
						String sql = "select a.agentcode,name from laagent a,lradimascription b where a.agentcode=b.agentcode and polno='"
								+ "?polno?"
								+ "' union all select a.agentcode,name from laagent a,lrascription b where a.agentcode=b.agentnew and b.oldpolno='"
								+ "?polno?" + "'";
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						sqlbv6.sql(sql);
						sqlbv6.put("polno", tLCPolSet.get(1).getPolNo());
						SSRS tSSRS = new ExeSQL().execSQL(sqlbv6);
						if (tSSRS.getMaxRow() > 0) {
							cols[12] = tSSRS.GetText(1, 1);
							cols[13] = tSSRS.GetText(1, 2);
						}
					}
				}
				cols[9] = polno;
				cols[10] = sec_ssrs.GetText(thr_count, 5);// 累计金额

//					String sql = "select name from lacom where agentcom in  ( "
//						+ " select distinct agentcom from lccont where prtno in (select otherno from ljtempfee where tempfeeno='"	+ sec_ssrs.GetText(thr_count, 1) + "' ) and agentcom is not null "
//						+ " union all "
//						+ " select distinct agentcom from lccont where contno in (select otherno from ljtempfee where tempfeeno='"	+ sec_ssrs.GetText(thr_count, 1) + "' ) and agentcom is not null )";
				//对于续期催收和保全应收只有扣款成功才会产生暂收数据
				String sql ="select name from lacom where agentcom in (select  distinct agentcom from lcpol where polno = '"+"?mPolNo?"+"' union "
					       +"select  distinct agentcom from lcpol where prtno = '"+"?mPolNo?"+"' union  select  distinct agentcom from lcpol where contno = '"+"?mPolNo?"+"')";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(sql);
				sqlbv7.put("mPolNo", mPolNo);	
				SSRS tSSRS = new ExeSQL().execSQL(sqlbv7);
					if (tSSRS.getMaxRow() >= 1) {
						cols[14] = tSSRS.GetText(1, 1);
					}

				mCount = mCount + 1;
				alistTable.add(cols);
				mSumDuePayMoney = mSumDuePayMoney
						+ Double.parseDouble(cols[10]);
			}
			// 对其明细信息进行附值
		}
		if (mCount > 0) {
			logger.debug("开始填充数据！");
			String[] b_col = new String[15];
			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, b_col);
			texttag.add("BillNo", mSerialNo);
			texttag.add("Date", PubFun.getCurrentDate());
			texttag.add("BankCode", mBankCode);
			texttag.add("BankName", mPremBankPubFun.getBankInfo(mBankCode)
					.getBankName().trim());
			texttag.add("SumMoney", mSumDuePayMoney);
			texttag.add("SumCount", mCount);
			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:\\","NewPrintBillErrorBL");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;
		} else {
			CError tError = new CError();
			tError.moduleName = "NewPrintBillRightBL";
			tError.functionName = "getPrintErrorData()";
			tError.errorMessage = mMessage;
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public String getSuccFlag(String aBankCode) {
		String aSuccFlag = "";
		;
		LDBankDB tLDBankDB = new LDBankDB();
		tLDBankDB.setBankCode(aBankCode);
		if (!tLDBankDB.getInfo()) {
			buildError("getSuccFlag", "无法获得银行" + aBankCode + "的成功标志！");
			return "";
		}

		String[] arrSucc = PubFun.split(tLDBankDB.getSchema()
				.getAgentPaySuccFlag().trim(), ";");
		// logger.debug("数组的长度是"+arrSucc.length);
		for (int i = 0; i < arrSucc.length; i++) {
			if (arrSucc[i] != null && !arrSucc[i].equals("")) {
				aSuccFlag += "'" + arrSucc[i] + "',";
			}
		}
		aSuccFlag = aSuccFlag.substring(0, aSuccFlag.lastIndexOf(","));
		logger.debug("该银行的成功标志是" + aSuccFlag);
		return aSuccFlag;
	}

	public static void main(String[] args) {
		String aSerialNo = "00000000000000000068";
		String aBankCode = "0301";
		String aFlag = "YS";
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		String aStation = "86";
		String aTFFlag = "F";// T是正确F是错误
		String aXQFlag = "S";

		VData tVData = new VData();
		tVData.addElement(aSerialNo);
		tVData.addElement(aBankCode);
		tVData.addElement(aFlag);
		tVData.addElement(aStation);
		tVData.addElement(aTFFlag);
		tVData.addElement(aXQFlag);

		NewPrintBillRightBL tNewPrintBillRightBL = new NewPrintBillRightBL();
		if (!tNewPrintBillRightBL.submitData(tVData, "PRINT")) {
			logger.debug("Fail : "
					+ tNewPrintBillRightBL.mErrors.getFirstError());
		} else {
			logger.debug("Success ! ");
			// XmlExport
			// tXmlExport=(XmlExport)tNewPrintBillRightBL.getResult().getObjectByObjectName("XmlExport",0);
			// tXmlExport.outputDocumentToFile("e:/","out");
		}
	}
}
