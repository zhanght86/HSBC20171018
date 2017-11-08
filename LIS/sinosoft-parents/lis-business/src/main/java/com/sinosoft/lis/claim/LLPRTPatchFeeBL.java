package com.sinosoft.lis.claim;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.f1print.AccessVtsFile;
import com.sinosoft.lis.f1print.CombineVts;
import com.sinosoft.lis.f1print.FileQueue;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.f1print.VtsFileCombine;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LLBalanceSet;
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
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证打印：补交保费 -- PCT002,RemFeeNoticeC000190.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLPRTPatchFeeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTPatchFeeBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private XmlExportNew xmlExportAll = new XmlExportNew();
	private GlobalInput tGI;
	private String mClmNo = ""; // 赔案号
	private String tWebPath = ""; // 模板路径
	private String mPrtSeq = ""; // 流水号

	public LLPRTPatchFeeBL() {
	}

	// 主函数
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ClmNo",tClmNo);
		// tTransferData.setNameAndValue("CustNo",tCusno);
		tTransferData.setNameAndValue("PrtSeq", "0000001434");
		tTransferData.setNameAndValue("Path", "D:/ui/f1print/NCLtemplate");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRTPatchFeeBL tLLPRTPatchFeeBL = new LLPRTPatchFeeBL();
		if (tLLPRTPatchFeeBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------补交保费通知书-----LLPRTPatchFeeBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------补交保费通知书-----LLPRTPatchFeeBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		if (tGI == null)// 批量打印使用
		{
			tGI = new GlobalInput();
			tGI.ComCode = "86";
			tGI.ManageCom = "86";
			tGI.Operator = "001";
		}

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
		this.tWebPath = (String) mTransferData.getValueByName("Path");
		this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 模板路径
		String strTemplatePath = tWebPath;

		// 根据流水号查询赔案号
		String mClmNoSQL = "select OtherNo from LOPRTManager where "
				+ " PrtSeq = '" + "?PrtSeq?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mClmNoSQL);
		sqlbv.put("PrtSeq", mPrtSeq);
		ExeSQL clmExeSQL = new ExeSQL();
		mClmNo = clmExeSQL.getOneValue(sqlbv);
		if (mClmNo == null || mClmNo.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLPRTPatchFeeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔案号失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询赔案下的所有需要补费投保人
		String pageSQL = "select distinct appntno from lccont where contno in "
				+ "(select distinct contno from llbalance " + " where clmno='"
				+ "?clmno?" + "'" + " and FeeOperationType='C02'"
				+ " and SubFeeOperationType='C0201'" + " and FeeFinaType='BF')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(pageSQL);
		sqlbv1.put("clmno", mClmNo);
		ExeSQL es = new ExeSQL();
		SSRS ssrs = es.execSQL(sqlbv1);
		int page = 0;
		page = ssrs.getMaxRow();
		if (page == 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRTPatchFeeBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "在该赔案下没有查询到任何需要补费的投保人!";
			mErrors.addOneError(tError);
			return false;

		}
		String[] strVFPathName = new String[page];
//		xmlExportAll.createDocuments("printer", this.tGI);
//		xmlExportAll.setTemplateName("RemFeeNoticeC000190.vts");
		xmlExportAll.createDocument("补缴保费通知书");
		
		// 根据投保人数量循环
		for (int p = 0; p < page; p++) {
			String mAppntNo = ssrs.GetText(p + 1, 1);

			TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
			XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//			tXmlExport.createDocument("RemFeeNoticeC000190.vts", "");
			tXmlExport.createDocument("补缴保费通知书");
			LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

			// 投保人信息(姓名和性别)---------------------------------------------------
			String appntnameSQL = "select * from ldperson where CustomerNo='"
					+ "?appntno?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(appntnameSQL);
			sqlbv2.put("appntno", mAppntNo);
			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSet tLDPersonSet = tLDPersonDB.executeQuery(sqlbv2);
			String appntName = tLDPersonSet.get(1).getName();
			String sex = tLDPersonSet.get(1).getSex();
			if (sex == null) {
				sex = "";
			}

			// 投保人信息(地址，邮编，电话)----------------------------------------------
			String appntSql = "select * from LCAddress a where "
					+ "a.customerno = '" + "?appntno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(appntSql);
			sqlbv3.put("appntno", mAppntNo);
			LCAddressDB tLCAddressDB = new LCAddressDB();
			LCAddressSet tLCAddressSet = tLCAddressDB.executeQuery(sqlbv3);
			String address = tLCAddressSet.get(1).getPostalAddress();
			String zipCode = tLCAddressSet.get(1).getZipCode();
			String phoneNumber = tLCAddressSet.get(1).getPhone();

			// 系统时间---------------------------------------------------------------
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			// 保单服务人员姓名、电话----------------------------------------------------
			LAAgentSchema tLAAgentSchema = new LAAgentSchema();
			tLAAgentSchema = tLLPRTPubFunBL.getLAAgent("00", mAppntNo);
			String Server = "";
			String telephoneNo = "";
			Server = tLAAgentSchema.getName();
			telephoneNo = tLAAgentSchema.getPhone();

			// 公司------------------------------------------------------------------
			// LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
			// tLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(mClmNo,
			// mAppntNo);
			// String mngCom = "";
			// mngCom = tLLSubReportSchema.getMngCom();
			// String comSQL =
			// "select a.name from ldcom a where comcode='" +mngCom + "'";
			// ExeSQL comExeSQL = new ExeSQL();
			// String company = "";
			

			// ----------------------------------------------------------------------
			tTextTag.add("Address", address); // 投保人地址
			tTextTag.add("ZipCode", zipCode); // 投保人邮编
			// 投保人姓名和称谓 模板上没有sex
			if (sex.equals("0")) {
				tTextTag.add("LCCont.AppntName", appntName + " 先生");
			} else {
				if (sex.equals("1")) {
					tTextTag.add("LCCont.AppntName", appntName + " 女士");
				} else {
					tTextTag.add("LCCont.AppntName", appntName);
				}
			}
			tTextTag.add("Server", Server); // 服务人员
			tTextTag.add("TelephoneNo", telephoneNo); // 服务人员联系电话
			// tTextTag.add("ManageCom", company); //通知书落款
			tTextTag.add("SysDate", SysDate); // 系统日期
			tTextTag.add("PhoneNumber", phoneNumber); // 投保人联系电话

			// 应缴保费合计-------------------后面---------------------------------
			double sumMoney = 0;
			// tTextTag.add("SumMoney", sumMoney);

			// 通知日期(取主险交至日期)---------后面--------------------------------
			String year = "";
			String month = "";
			// String paytodate = "";
			// String paytodateSQL =
			// "select min(paytodate) from lccont where "
			// + " appntno = '" + mAppntNo + "'"
			// + " and contno in "
			// + "(select distinct contno from llbalance "
			// + " where clmno='" + mClmNo + "'"
			// + " and FeeOperationType='C02'"
			// + " and SubFeeOperationType='C0201'"
			// + " and FeeFinaType='BF')";
			// logger.debug(paytodateSQL);
			// ExeSQL dateExeSQL = new ExeSQL();
			// paytodate = dateExeSQL.getOneValue(paytodateSQL);
			//
			// FDate mFDate = new FDate();
			// GregorianCalendar mCalendar = new GregorianCalendar();
			// mCalendar.setTime(mFDate.getDate(paytodate));
			// String year = String.valueOf(mCalendar.get(Calendar.YEAR));
			// String month = String.valueOf(mCalendar.get(Calendar.MONTH));
			// tTextTag.add("Year", year); //年份
			// tTextTag.add("Month", month); //月份

			// 首先查出保单号(合同号)的数量作为最外层循环-----------------------------------
			String contSQL = "";
			contSQL = "select * from lccont where appntno='" + "?appntno?"
					+ "' and contno in "
					+ "(select distinct contno from llbalance "
					+ "where clmno='" + "?clmno?" + "' and FeeOperationType='C02'"
					+ " and SubFeeOperationType='C0201'"
					+ " and FeeFinaType='BF')";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(contSQL);
			sqlbv4.put("appntno", mAppntNo);
			sqlbv4.put("clmno", mClmNo);
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv4);

			ListTable tListTable = new ListTable();
			tListTable.setName("CONT");

			String[] Title = new String[6];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";

			// ----------------------------------------------------------------------
			for (int i = 1; i <= tLCContSet.size(); i++) {
				// 取得保单号(合同号)
				String contNo = tLCContSet.get(i).getContNo();
				// 交费对应日--tLCContSet.get(i).getPaytoDate()

				// 交费宽限期截至日需先查出合同号的主险的polNo,然后从 LCPol 得到险种编号,再通过方法得到宽限期
				String polNoSQL = "select * from llbalance where "
						+ " contno = '" + "?contno?" + "'"
						+ " and FeeOperationType='C02' "
						+ " and SubFeeOperationType='C0201'"
						+ " and FeeFinaType='BF'"; // 此语句确定 llbalance 中唯一polno
				SQLwithBindVariables sqlbv5= new SQLwithBindVariables();
				sqlbv5.sql(polNoSQL);
				sqlbv5.put("contno", contNo);
				LLBalanceDB tLLBalanceDB = new LLBalanceDB();
				LLBalanceSet tLLBalanceSet = tLLBalanceDB
						.executeQuery(sqlbv5);

				String mPolNo = ""; // 主险的PolNo
				double mFee = 0; // 主险保费

				// 此循环用于判断主险的PolNo
				for (int j = 1; j <= tLLBalanceSet.size(); j++) {
					String riskFlagSql = "select SubRiskFlag from LMRiskApp where 1=1 "
							+ " and riskcode = "
							+ "  (select riskcode from LCPol where PolNo='"
							+ tLLBalanceSet.get(j).getPolNo() + "')";
					logger.debug(riskFlagSql);
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(riskFlagSql);
					sqlbv6.put("PolNo", tLLBalanceSet.get(j).getPolNo());
					ExeSQL riskFlagExeSQL = new ExeSQL();
					String riskFlag = riskFlagExeSQL.getOneValue(sqlbv6);
					if (riskFlag == null) {
						riskFlag = "";
					}
					if (riskFlag.equals("M")) {
						mPolNo = tLLBalanceSet.get(j).getPolNo();
						mFee = Math.abs(tLLBalanceSet.get(j).getPay()); // 主险保费
						break; // 如果是主险则跳出循环
					}
				}

				// 查询保单信息--------------------------------------------------------
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(mPolNo);
				if (!tLCPolDB.getInfo()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCPolDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLPRTPatchFeeBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保单信息失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setSchema(tLCPolDB.getSchema());
				String riskCode = tLCPolSchema.getRiskCode(); // 险种编码
				String tPayToDate = tLCPolSchema.getPaytoDate(); // 交至日期

				FDate mFDate = new FDate();
				GregorianCalendar mCalendar = new GregorianCalendar();
				mCalendar.setTime(mFDate.getDate(tPayToDate));
				year = String.valueOf(mCalendar.get(Calendar.YEAR));
				month = String.valueOf(mCalendar.get(Calendar.MONTH) + 1); // 月份加1,否则不一致

				PubFun tPubFun = new PubFun();
				String date1 = tPubFun.calLapseDate(tPayToDate, riskCode); // 62天
				// String date = EdorCalZT.CalLapseDate(riskCode, tPayToDate);
				// //62天
				String date = tPubFun.calDate(date1, -2, "D", "");

				// 保费-----------------------------------------------------------
				String sFeeSQL = "select abs(sum(pay)) from llbalance where "
						+ " clmno='" + "?clmno?" + "'" + " and contno='" + "?contno?"
						+ "'" + " and FeeOperationType='C02'"
						+ " and SubFeeOperationType='C0201'"
						+ " and FeeFinaType='BF'";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(sFeeSQL);
				sqlbv7.put("clmno", mClmNo);
				sqlbv7.put("contno", contNo);
				ExeSQL sFeeExeSQL = new ExeSQL();
				double sFee = Double.parseDouble(sFeeExeSQL
						.getOneValue(sqlbv7))
						- mFee;

				// ---------------------------------------------------------------
				String[] contStr = new String[6];
				contStr[0] = contNo; // 保单号
				contStr[1] = tPayToDate; // 交费对应日(交至日期)
				contStr[2] = date; // 交费宽限期截至日
				contStr[3] = String.valueOf(mFee); // 主险保费
				contStr[4] = String.valueOf(sFee); // 附险保费
				contStr[5] = tLCContSet.get(i).getBankAccNo(); // 交费账号
				sumMoney = mFee + sFee + sumMoney;
				tListTable.add(contStr);

				String[] spaceStr = new String[1];
				spaceStr[0] = "";
				tListTable.add(spaceStr); // 加空行
			} // 合同循环结束-------------------------------------------------------

			tXmlExport.addListTable(tListTable, Title);

			// 通知日期-----------前面没有处理的-------------------------------------
			tTextTag.add("Year", year); // 年份
			tTextTag.add("Month", month); // 月份

			// 保费合计-----------前面没有处理的-------------------------------------
			tTextTag.add("SumMoney", String.valueOf(sumMoney)); // 投保人联系电话

			if (tTextTag.size() > 0) {
				tXmlExport.addTextTag(tTextTag);
			}
			xmlExportAll.addDataSet(tXmlExport.getDocument().getRootElement());

			CombineVts tcombineVts = null;
			tcombineVts = new CombineVts(tXmlExport.getInputStream(),
					strTemplatePath);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);

			strVFPathName[p] = strTemplatePath + p + "RemFeeNoticeC000190.vts";
			// 把dataStream存储到磁盘文件
			// logger.debug("存储文件到"+strVFPathName);
			AccessVtsFile.saveToFile(dataStream, strVFPathName[p]);

		}
		VtsFileCombine vtsfilecombine = new VtsFileCombine();
		BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
		FileQueue tFileQueue = new FileQueue();
		String t = tFileQueue.getFileName();
		String allname = strTemplatePath + tGI.Operator + t + ".vts";
		try {
			// vtsfilecombine.write(tb, strTemplatePath + "new.vts");
			vtsfilecombine.write(tb, allname); // niuzj 20050920
		} catch (IOException ex) {
		} catch (F1Exception ex) {
		}

		String remark = "new.vts";
		mResult.clear();
		// mResult.addElement(strTemplatePath + "new.vts");
		mResult.addElement(allname); // niuzj 20050920
		mResult.addElement(xmlExportAll);
		mResult.add(mTransferData);
		// 把后台动态生成的文件名返回前台
		String b = (String) mResult.getObjectByObjectName("String", 0);
		logger.debug("********************" + b);

	    String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT008'";
	    logger.debug("更新打印管理表的sql:"+updateStateSql);
	    SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
	    sqlbv8.sql(updateStateSql);
	    sqlbv8.put("clmno", mClmNo);
	    mMMap.put(sqlbv8, "UPDATE");
	    
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRTPatchFeeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
