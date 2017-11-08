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
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author kevin
 * @version 1.0
 */

public class RANF1PBL extends NoticeF1PBO {
private static Logger logger = Logger.getLogger(RANF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private double fPremSum = 0;
	private double fPremAddSum = 0;
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();

	public RANF1PBL() {
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
	}

	private void getPrintData() throws Exception {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
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
		tLJAGetDB.setGetNoticeNo(mLOPRTManagerSchema.getOtherNo());
		LJAGetSet tLJAGetSet = tLJAGetDB.query();

		XmlExport xmlExport = new XmlExport();

		dealLJAGetSet(tLJAGetSet, mLOPRTManagerSchema.getStandbyFlag1(),
				xmlExport);
		// xmlExport.outputDocumentToFile("e:\\", "jbnotice");
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
		RANF1PBLS tRANF1PBLS = new RANF1PBLS();
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
			XmlExport xmlExport) throws Exception {
		boolean bHaveFoundBank = false;
		ListTable listTable = new ListTable();
		LJAGetSchema tLJAGetSchema = null;
		LJTempFeeClassSchema tLJTempFeeClassSchema = null;
		String[] strRiskInfo = null;
		LCContDB tLCContDB = null;
		double dSumMoney = 0.0;

		for (int nIndex = 0; nIndex < aLJAGetSet.size(); nIndex++) {
			strRiskInfo = new String[3];
			tLJAGetSchema = aLJAGetSet.get(nIndex + 1);

			// 检查其它号码标志
			if (tLJAGetSchema.getOtherNoType().equals("6")) {
				// 溢交退费：对应的其它号码为个人保单号

				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLJAGetSchema.getOtherNo());
				// tLCPolDB.query();
				if (!tLCPolDB.getInfo()) {
					mErrors.copyAllErrors(tLCContDB.mErrors);
					throw new Exception("找不到指定的个人投保单");
				}

				if (!bHaveFoundBank) {
					tLJTempFeeClassSchema = queryBankInfo(tLCPolDB);

					if (tLJTempFeeClassSchema != null) {
						bHaveFoundBank = true;
					}
				}

				strRiskInfo[0] = tLJAGetSchema.getActuGetNo();
				strRiskInfo[1] = getRiskName(tLCPolDB.getRiskCode());

				// 从其它付费表中取金额
				String strSQL = "SELECT (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) FROM LJAGetOther WHERE ActuGetNo = '"
						+ "?ActuGetNo?" + "'";
				ExeSQL exeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSQL);
				sqlbv1.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
				strRiskInfo[2] = exeSQL.execSQL(sqlbv1).GetText(1, 1);
				dSumMoney += Double.parseDouble(strRiskInfo[2]);

			} else if (tLJAGetSchema.getOtherNoType().equals("4")) {
				// 暂交费退费：对应暂交费退费表的暂缴费收据号
				String strTempFeeNo = tLJAGetSchema.getOtherNo();

				if (!bHaveFoundBank) {
					tLJTempFeeClassSchema = queryBankInfo(strTempFeeNo);

					if (tLJTempFeeClassSchema != null) {
						bHaveFoundBank = true;
					}
				}

				strRiskInfo[0] = tLJAGetSchema.getActuGetNo();

				// 从暂交费实付退费表中取
				String strSQL = "SELECT RiskCode, (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) FROM LJAGetTempFee WHERE ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' AND TempFeeNo = '"
						+ "?strTempFeeNo?" + "' GROUP BY RiskCode";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSQL);
				sqlbv2.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
				sqlbv2.put("strTempFeeNo", strTempFeeNo);
				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(sqlbv2);
				for (int n = 0; n < ssrs.getMaxRow(); n++) {
					strRiskInfo[1] = getRiskName(ssrs.GetText(n + 1, 1));
					strRiskInfo[2] = ssrs.GetText(n + 1, 2);
					dSumMoney += Double.parseDouble(strRiskInfo[2]);
				}

			} else {
				throw new Exception("不支持的其它号码类型");
			}

			listTable.add(strRiskInfo);
		}

		// 将得到的数据放到xmlExport对象中
		TextTag texttag = new TextTag();

		// 判断是退现金还是自动转账
		if (bHaveFoundBank) {
			xmlExport.createDocument("RANT.vts", "");

			texttag.add("BankAccNo", tLJTempFeeClassSchema.getBankAccNo());
			texttag.add("BankName", getBankName(tLJTempFeeClassSchema
					.getBankCode()));
			texttag.add("AccName", tLJTempFeeClassSchema.getAccName());
		} else {
			xmlExport.createDocument("RANC.vts", "");

			texttag.add("BankAccNo", "");
			texttag.add("BankName", "");
			texttag.add("AccName", "");
		}

		tLCContDB = new LCContDB();
		tLCContDB.setContNo(strPolNo);

		// 如果strMainPolNo没有被正确地设置，则查询不出有用的数据，但是不报错。
		tLCContDB.getInfo();

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();

		String strSQL = "SELECT * FROM LCCUWMaster WHERE ContNo = '" + "?strPolNo?"
				+ "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSQL);
		sqlbv3.put("strPolNo", strPolNo);
		LCCUWMasterSet tLCCUWMasterSet = tLCCUWMasterDB.executeQuery(sqlbv3);

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			throw new Exception("在取得核保意见时出现错误");
		}

		String strUWError = "";

		if (tLCCUWMasterSet.size() >= 1) {
			strUWError = tLCCUWMasterSet.get(1).getUWIdea();
		}

		texttag.add("AppntName", tLCContDB.getAppntName());
		// texttag.add("PolNo", tLCContDB.getPolNo());
		texttag.add("PrtNo", tLCContDB.getPrtNo());
		texttag.add("AgentName", getAgentName(tLCContDB.getAgentCode()));
		texttag.add("AgentCode", tLCContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCContDB.getManageCom()));
		// texttag.add("RiskName", getRiskName(tLCContDB.getRiskCode()));
		texttag.add("InsuredName", tLCContDB.getInsuredName());
		texttag.add("UWCode", tLCContDB.getUWOperator());
		texttag.add("Prem", new DecimalFormat("0.00").format(dSumMoney));
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("ActuGetNo", mLOPRTManagerSchema.getOtherNo());
		texttag.add("UWError", strUWError);

		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("Today", df.format(new Date()));

		xmlExport.addTextTag(texttag);

		// 设置列表名字和字段名
		strRiskInfo = new String[3];
		strRiskInfo[0] = "GetNoticeNo";
		strRiskInfo[1] = "RiskName";
		strRiskInfo[2] = "Money";

		listTable.setName("RiskInfo");

		xmlExport.addListTable(listTable, strRiskInfo);
	}
}
