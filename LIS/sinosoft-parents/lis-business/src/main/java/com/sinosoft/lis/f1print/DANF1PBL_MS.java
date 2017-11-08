package com.sinosoft.lis.f1print;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class DANF1PBL_MS extends NoticeF1PBO {
	private static Logger logger = Logger.getLogger(DANF1PBL_MS.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	// public CErrors mErrors=new CErrors(); // 在基础类中定义
	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private double fPremSum = 0;

	private double fPremAddSum = 0;

	private String mOperate = "";

	private String CurrentDate = PubFun.getCurrentDate();

	public DANF1PBL_MS() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {
			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("CONFIRM")) {
				mResult.clear();
				getPrintData();

			} else if (cOperate.equals("PRINT")) {
				if (!saveData(cInputData)) {
					return false;
				}
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	public static void main(String[] args) {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "DANF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		if (mLOPRTManagerSchema.getStateFlag() == null) {
			buildError("getprintData", "无效的打印状态");
		} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
			buildError("getprintData", "该打印请求不是在请求状态");
		}

		// 打印时传入的是实付总表的给付通知书号
		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetDB.setGetNoticeNo(mLOPRTManagerSchema.getStandbyFlag6());
		LJAGetSet tLJAGetSet = new LJAGetSet();
		if (mLOPRTManagerSchema.getStandbyFlag6() != null
				&& !mLOPRTManagerSchema.getStandbyFlag6().equals("")) {
			tLJAGetSet = tLJAGetDB.query();
		}

		XmlExportNew xmlExport = new XmlExportNew();

		dealLJAGetSet(tLJAGetSet, mLOPRTManagerSchema.getOtherNo(), xmlExport);

		mResult.clear();
		mResult.addElement(xmlExport);
	}

	private boolean saveData(VData mInputData) {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setDoneDate(CurrentDate);
		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		DANF1PBLS_MS tDANF1PBLS = new DANF1PBLS_MS();
		tDANF1PBLS.submitData(mResult, mOperate);
		if (tDANF1PBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tDANF1PBLS.mErrors);
			buildError("saveData", "提交数据库出错！");
			return false;
		}
		return true;
	}

	/**
	 * 处理一条实付记录
	 * 
	 * @param aLJAGetSet
	 * @param xmlExport
	 * @param aLJTempFeeClassSchema
	 * @return
	 */
	private void dealLJAGetSet(LJAGetSet aLJAGetSet, String strPolNo,
			XmlExportNew xmlExport) throws Exception {
		boolean bHaveFoundBank = false;
		ListTable listTable = new ListTable();
		LJAGetSchema tLJAGetSchema = null;
		LJTempFeeClassSchema tLJTempFeeClassSchema = null;
		String[] strRiskInfo = null;
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(strPolNo);
		if (!tLCContDB.getInfo()) {
			throw new Exception("查询合同信息出错");
		}
		double dSumMoney = 0.0;
		double dSumGivenMoney = 0.0;

		for (int nIndex = 0; nIndex < aLJAGetSet.size(); nIndex++) {
			tLJAGetSchema = aLJAGetSet.get(nIndex + 1);
			if (tLJAGetSchema.getOtherNoType().equals("4")) {
				// 暂交费退费：对应暂交费退费表的暂缴费收据号
				String strTempFeeNo = tLJAGetSchema.getOtherNo();

				if (!bHaveFoundBank) {
					tLJTempFeeClassSchema = queryBankInfo(strTempFeeNo);

					if (tLJTempFeeClassSchema != null) {
						bHaveFoundBank = true;
					}
				}
				// 从暂交费实付退费表中取
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				String strSQL = "SELECT RiskCode, (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) FROM LJAGetTempFee WHERE ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' "
						+ " AND TempFeeNo = '"
						+ "?strTempFeeNo?"
						+ "'  GROUP BY RiskCode";
				sqlbv1.sql(strSQL);
				sqlbv1.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
				sqlbv1.put("strTempFeeNo", strTempFeeNo);
				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(sqlbv1);

				for (int n = 0; n < ssrs.getMaxRow(); n++) {
					// zy 2009-05-13 防止数据被覆盖
					strRiskInfo = new String[4];
					strRiskInfo[0] = tLJAGetSchema.getActuGetNo();
					strRiskInfo[1] = getRiskName(ssrs.GetText(n + 1, 1));
					strRiskInfo[3] = ssrs.GetText(n + 1, 2);
					dSumMoney += Double.parseDouble(strRiskInfo[3]);

					// 已交费
					// 从暂收费表中取金额
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					strSQL = "SELECT (case when sum(payMoney) is not null then sum(payMoney)  else 0 end) FROM LJTempFee WHERE TempFeeNo = '"
							+ "?strTempFeeNo?"
							+ "' "
							+ "and riskcode='"
							+ "?riskcode?" + "'";
					sqlbv2.sql(strSQL);
					sqlbv2.put("strTempFeeNo", strTempFeeNo);
					sqlbv2.put("riskcode", ssrs.GetText(n + 1, 1));
					exeSQL = new ExeSQL();
					strRiskInfo[2] = exeSQL.execSQL(sqlbv2).GetText(1, 1);
					dSumGivenMoney += Double.parseDouble(strRiskInfo[2]);
					listTable.add(strRiskInfo);
				}

			} else {
				throw new Exception("不支持的其它号码类型");
			}
		}
		// 将得到的数据放到xmlExport对象中
		TextTag textTag = new TextTag();
		String printName = "";
		// 判断是退现金还是自动转账
		if (bHaveFoundBank) {
			printName = "延期通知书CT";
			textTag.add("BankAccNo", tLJTempFeeClassSchema.getBankAccNo());
			if (!("".equals(tLJTempFeeClassSchema.getBankCode()) || tLJTempFeeClassSchema
					.getBankCode() == null))
				textTag.add("BankName", getBankName(tLJTempFeeClassSchema
						.getBankCode()));
			else
				textTag.add("BankName", "");
			textTag.add("AccName", tLJTempFeeClassSchema.getAccName());

		} else {
			printName = "延期通知书CC";
			textTag.add("BankAccNo", "");
			textTag.add("BankName", "");
			textTag.add("AccName", "");
		}
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage))
			xmlExport.setUserLanguage(uLanguage);//用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		// 取得延期的时间
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String strSQL = "SELECT * FROM LCCUWMaster WHERE  ContNo = '"
				+ "?ContNo?" + "'";
		sqlbv3.sql(strSQL);
		sqlbv3.put("ContNo", tLCContDB.getContNo());
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterDB()
				.executeQuery(sqlbv3);

		if (tLCCUWMasterSet.size() < 1) {
			throw new Exception("查询核保主表的信息时出现错误");
		}

		LCCUWMasterSchema tLCCUWMasterSchema = tLCCUWMasterSet.get(1);

		String strUWError = "";
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("UWERROR");
		if (tLCCUWMasterSet.size() == 1) {
			if (tLCCUWMasterSet.get(1).getCommonReason() != null
					&& !tLCCUWMasterSet.get(1).getCommonReason().equals("")) {
				// zy 2009-05-15 按照要求统一为uwidea
				strUWError = tLCCUWMasterSet.get(1).getUWIdea();
				if (strUWError == null)
					strUWError = "";
				getContent(tlistTable, strUWError, 40);
			}
		}
		strArr = new String[1];
		strArr[0] = "UWERROR";
		xmlExport.addListTable(tlistTable, strArr);
		String thead = PrintTool.getHead(tLCContDB.getAppntName(), tLCContDB
				.getAppntSex(), uLanguage);

		textTag.add("AppntName", thead); // 投保人
		textTag.add("PrtNo", tLCContDB.getPrtNo());
		if (dSumMoney != 0) {
			xmlExport.addDisplayControl("display"); // 模版上的特别约定部分的控制标记
			textTag.add("Prem", new DecimalFormat("0.00").format(dSumMoney));
			textTag.add("GivenPrem", new DecimalFormat("0.00")
					.format(dSumGivenMoney));
			// 设置列表名字和字段名
			strRiskInfo = new String[4];
			strRiskInfo[0] = "GetNoticeNo";
			strRiskInfo[1] = "RiskName";
			strRiskInfo[2] = "GivenMoney";
			strRiskInfo[3] = "GetMoney";

			listTable.setName("RiskInfo");

			xmlExport.addListTable(listTable, strRiskInfo);
		}

		textTag.add("UWError", strUWError);
		textTag.add("AgentName", getAgentName(tLCContDB.getAgentCode()));
		textTag.add("AgentCode", tLCContDB.getAgentCode());
		textTag.add("ManageCom", getComName(tLCContDB.getManageCom()));
		textTag.add("UWCode", tLCContDB.getUWOperator());
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		textTag.add("Today", df.format(new Date()));
		textTag.add("Days", tLCCUWMasterSchema.getPostponeDay());
		textTag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		String remark = "";
		if (mLOPRTManagerSchema.getRemark() != null
				&& !mLOPRTManagerSchema.getRemark().equals(""))
			remark = mLOPRTManagerSchema.getRemark() + "，";
		textTag.add("Remark", remark);// ln 2008-10-18 add 投保建议

		xmlExport.addTextTag(textTag);
	}

	// 辅助函数
	/**
	 * 对打印的文字过长一行显示不完时作调整
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private void getContent(ListTable tListTable, String content,
			int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr;
		while (nSpecReasonLen > nMaxCharsInOneLine) {
			content = strSpecReason.substring(0, nMaxCharsInOneLine);
			strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
			nSpecReasonLen -= nMaxCharsInOneLine;

			strArr = new String[1];
			strArr[0] = content;
			tListTable.add(strArr);
		}

		if (nSpecReasonLen > 0) {
			strArr = new String[1];
			strArr[0] = strSpecReason;
			tListTable.add(strArr);
		}
	}
}
