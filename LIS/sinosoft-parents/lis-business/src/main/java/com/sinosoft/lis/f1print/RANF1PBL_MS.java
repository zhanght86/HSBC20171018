package com.sinosoft.lis.f1print;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class RANF1PBL_MS extends NoticeF1PBO {
	private static Logger logger = Logger.getLogger(RANF1PBL_MS.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private String mOperate = "";

	private String CurrentDate = PubFun.getCurrentDate();

	public RANF1PBL_MS() {
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
			if(!dealData()){
				return false;
			}
			if (cOperate.equals("CONFIRM")) {
				mResult.clear();

				// 准备所有要打印的数据
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

		cError.moduleName = "RANF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		logger.debug(cError);
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
			buildError("saveData", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(CurrentDate);
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		RANF1PBLS_MS tRANF1PBLS = new RANF1PBLS_MS();
		tRANF1PBLS.submitData(mResult, mOperate);
		if (tRANF1PBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tRANF1PBLS.mErrors);
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
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		String[] strRiskInfo = null;
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(strPolNo);
		tLCContDB.getInfo();
		double dSumMoney = 0.0;
		double dSumGivenMoney = 0.0;
		LCPolDB tLCPolDB = new LCPolDB();
		for (int nIndex = 0; nIndex < aLJAGetSet.size(); nIndex++) {

			tLJAGetSchema = aLJAGetSet.get(nIndex + 1);

			// 检查其它号码标志

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
				String strSQL = "SELECT RiskCode, (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) FROM LJAGetTempFee WHERE ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' "
						+ " GROUP BY RiskCode";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSQL);
				sqlbv1.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(sqlbv1);

				for (int n = 0; n < ssrs.getMaxRow(); n++) {
					strRiskInfo = new String[4];
					strRiskInfo[1] = getRiskName(ssrs.GetText(n + 1, 1));
					strRiskInfo[3] = ssrs.GetText(n + 1, 2);
					dSumMoney += Double.parseDouble(strRiskInfo[3]);

					// 已交费
					// 从暂收费表中取金额
					strSQL = "SELECT (case when sum(paymoney) is not null then sum(paymoney)  else 0 end) FROM LJTempFee WHERE TempFeeNo = '"
							+ "?strTempFeeNo?"
							+ "' "
							+ "and riskcode='"
							+ "?riskcode?" + "'";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(strSQL);
					sqlbv2.put("strTempFeeNo", strTempFeeNo);
					sqlbv2.put("riskcode", ssrs.GetText(n + 1, 1));
					exeSQL = new ExeSQL();
					// zy 2009-05-11
					// 通知书中增加实付号码的显示，由于客户给的模板中定义的变量为strRiskInfo[2]，所以......

					strRiskInfo[2] = tLJAGetSchema.getActuGetNo();
					strRiskInfo[0] = exeSQL.execSQL(sqlbv2).GetText(1, 1);
					dSumGivenMoney += Double.parseDouble(strRiskInfo[0]);
					listTable.add(strRiskInfo);
				}
			} else {
				throw new Exception("不支持的其它号码类型");
			}
		}

		// 将得到的数据放到xmlExport对象中
		TextTag texttag = new TextTag();
		String printName = "";
		// 判断是退现金还是自动转账
		if (bHaveFoundBank) {
			printName = "拒保通知书CT";
			texttag.add("BankAccNo", tLJTempFeeClassSchema.getBankAccNo());
			// zy 如果 获取为bankcode为null，则不进行相应的查询
			if (!("".equals(tLJTempFeeClassSchema.getBankCode()) || tLJTempFeeClassSchema
					.getBankCode() == null))
				texttag.add("BankName", getBankName(tLJTempFeeClassSchema
						.getBankCode()));
			else
				texttag.add("BankName", "");
			texttag.add("AccName", tLJTempFeeClassSchema.getAccName());
		} else {
			printName = "拒保通知书CC";
			texttag.add("BankAccNo", "");
			texttag.add("BankName", "");
			texttag.add("AccName", "");
		}
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq(),"其他内容");//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);//用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(strPolNo);
		LCCUWMasterSet tLCCUWMasterSet = tLCCUWMasterDB.query();

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			throw new Exception("在取得核保意见时出现错误");
		}
		String strUWError = "";
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("UWERROR");
		if (tLCCUWMasterSet.size() >= 1) {
			if (tLCCUWMasterSet.get(1).getCommonReason() != null
					&& !tLCCUWMasterSet.get(1).getCommonReason().equals("")) {
				// zy 2009-05-15 按要求统一去uwidea
				strUWError = tLCCUWMasterSet.get(1).getUWIdea();
				if (strUWError == null)
					strUWError = "";
				getContent(tlistTable, strUWError, 40);
			}
		}
		strArr = new String[1];
		strArr[0] = "UWERROR";
		xmlExport.addListTable(tlistTable, strArr);
		/* 取主险信息 */
		String tempSQL_Main = "select * from lcpol where contno='" + "?strPolNo?"
				+ "' and mainpolno=polno ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tempSQL_Main);
		sqlbv3.put("strPolNo", strPolNo);
		tLCPolDB.setSchema(tLCPolDB.executeQuery(sqlbv3).get(1));

		String thead = PrintTool.getHead(tLCContDB.getAppntName(),
				tLCContDB.getAppntSex(), uLanguage);
		texttag.add("AppntName", thead); // 投保人
		texttag.add("PolNo", tLCContDB.getContNo());
		texttag.add("PrtNo", tLCContDB.getPrtNo());
		texttag.add("AgentName", getAgentName(tLCContDB.getAgentCode()));
		texttag.add("AgentCode", tLCContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCContDB.getManageCom()));
		texttag.add("InsuredName", tLCContDB.getInsuredName());
		texttag.add("UWCode", tLCContDB.getUWOperator());
		if (dSumMoney != 0) {
			xmlExport.addDisplayControl("display"); // 模版上的特别约定部分的控制标记
			texttag.add("Prem", new DecimalFormat("0.00").format(dSumMoney));
			texttag.add("GivenPrem", new DecimalFormat("0.00")
					.format(dSumGivenMoney));
			// 设置列表名字和字段名
			strRiskInfo = new String[4];

			strRiskInfo[0] = "GivenMoney";
			strRiskInfo[1] = "RiskName";
			strRiskInfo[2] = "GetNoticeNo";
			strRiskInfo[3] = "GetMoney";

			listTable.setName("RiskInfo");
			xmlExport.addListTable(listTable, strRiskInfo);
		}
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("ActuGetNo", mLOPRTManagerSchema.getOtherNo());
		String remark = "";
		if (mLOPRTManagerSchema.getRemark() != null
				&& !mLOPRTManagerSchema.getRemark().equals(""))
			remark = mLOPRTManagerSchema.getRemark() + "，";
		texttag.add("Remark", remark);// ln 2008-10-18 add 投保建议
		texttag.add("UWError", strUWError);

		texttag.add("Today", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));

		xmlExport.addTextTag(texttag);

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
