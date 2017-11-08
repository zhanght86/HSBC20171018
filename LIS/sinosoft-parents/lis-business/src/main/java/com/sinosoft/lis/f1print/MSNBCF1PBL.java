package com.sinosoft.lis.f1print;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class MSNBCF1PBL extends NoticeF1PBO {
	private static Logger logger = Logger.getLogger(MSNBCF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	// public CErrors mErrors=new CErrors(); 在基础类中定义
	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private String mOperate = "";

	private String CurrentDate = PubFun.getCurrentDate();

	public MSNBCF1PBL() {
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

			if (cOperate.equals("PRINT")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData();

			} else if (cOperate.equals("CONFIRM")) {
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
		cError.moduleName = "NBCF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (!tLOPRTManagerDB.getInfo()) {
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
		// tongmeng 2007-12-29 add
		// 使用loprtmanager standbyflag6 记录给付通知书号
		logger.debug("打印管理表记录的给付通知书号:" + mLOPRTManagerSchema.getStandbyFlag6());
		tLJAGetDB.setGetNoticeNo(mLOPRTManagerSchema.getStandbyFlag6());
		LJAGetSet tLJAGetSet = tLJAGetDB.query();

		XmlExportNew xmlExport = new XmlExportNew();

		dealLJAGetSet(tLJAGetSet, mLOPRTManagerSchema.getOtherNo(), xmlExport);

		mResult.clear();
		mResult.addElement(xmlExport);
	}

	private boolean saveData(VData mInputData) {
		// 根据印刷号查询打印队列中的纪录
		// mLOPRTManagerSchema
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(CurrentDate);
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		// tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		NBCF1PBLS tNBCF1PBLS = new NBCF1PBLS();
		tNBCF1PBLS.submitData(mResult, mOperate);
		if (tNBCF1PBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tNBCF1PBLS.mErrors);
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
		LCPolDB tLCPolDB = null;
		double dSumMoney = 0.0;
		double GetMoney = 0.0;// 合计已交保费
		double GivenMoney = 0.0;// 合计应交保费
		boolean strRiskInfoFlag = false;

		for (int nIndex = 0; nIndex < aLJAGetSet.size(); nIndex++) {
			tLJAGetSchema = aLJAGetSet.get(nIndex + 1);

			// 检查其它号码标志
			if (tLJAGetSchema.getOtherNoType().equals("6")) {
				// 溢交退费：对应的其它号码为个人合同
				tLCPolDB = new LCPolDB();
				// tongmeng 2007-12-29 add
				// 查询主险的险种信息
				tLCPolDB.setContNo(tLJAGetSchema.getOtherNo());
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolSet = tLCPolDB.query();
				if (tLCPolSet.size() <= 0) {
					mErrors.copyAllErrors(tLCPolDB.mErrors);
					throw new Exception("找不到指定的个人投保单");
				}

				if (!bHaveFoundBank) {
					tLJTempFeeClassSchema = queryBankInfo(tLCPolSet.get(1));

					if (tLJTempFeeClassSchema != null) {
						bHaveFoundBank = true;
					}
				}

				// zy 2009-05-15 调整查询暂收信息的逻辑
				String strSQL1 = "SELECT (case when sum(PayMoney) is not null then sum(PayMoney)  else 0 end) FROM LJTempFee"
						+ " WHERE ((OtherNo ='" + "?ContNo?"
						+ "' " + " OR OtherNo = '"
						+ "?PrtNo?" + "'))";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSQL1);
				sqlbv1.put("ContNo", tLCPolSet.get(1).getContNo());
				sqlbv1.put("PrtNo", tLCPolSet.get(1).getPrtNo());
				ExeSQL exeSQL1 = new ExeSQL();
				SSRS strRiskSSRS = exeSQL1.execSQL(sqlbv1);
				// zy 2009-05-14 由于模板调整，如果有记录则显示，所以相应逻辑调整
				if (strRiskSSRS.getMaxRow() <= 0)
					strRiskInfoFlag = false;
				else {
					for (int j = 1; j <= strRiskSSRS.getMaxRow(); j++) {
						strRiskInfoFlag = true;
						strRiskInfo = new String[5];
						strRiskInfo[0] = tLJAGetSchema.getActuGetNo();
						strRiskInfo[1] = getRiskName(tLCPolSet.get(1)
								.getRiskCode());
						strRiskInfo[2] = strRiskSSRS.GetText(j, 1);// ln
																	// 2008-11-19
																	// add 已交保费
						GetMoney += Double.parseDouble(strRiskInfo[2]);

						double GetPrem = 0;
						for (int i = 1; i <= tLCPolSet.size(); i++)
							GetPrem += tLCPolSet.get(i).getPrem();
						strRiskInfo[3] = String.valueOf(GetPrem);// ln
																	// 2008-11-19
																	// add 应交保费
						GivenMoney += Double.parseDouble(strRiskInfo[3]);

						// 从其它付费表中取金额
						String strSQL = "SELECT (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) FROM LJAGetOther WHERE ActuGetNo = '"
								+ "?ActuGetNo?" + "'";
						SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						sqlbv.sql(strSQL);
						sqlbv.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
						ExeSQL exeSQL = new ExeSQL();

						strRiskInfo[4] = exeSQL.execSQL(sqlbv).GetText(j, 1);
						dSumMoney += Double.parseDouble(strRiskInfo[4]);
					}
				}
			}
			else {
				throw new Exception("不支持的其它号码类型");
			}

			listTable.add(strRiskInfo);
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLJAGetSchema.getOtherNo());
		LCContSet tLCContSet = new LCContSet();
		tLCContSet = tLCContDB.query();
		if (tLCContSet.size() <= 0) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			throw new Exception("找不到合同信息");
		}
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setSchema(tLCContSet.get(1));

		// 将得到的数据放到xmlExport对象中
		TextTag texttag = new TextTag();
		String tPrintName = "";
		// 判断是退现金还是自动转账
		if (bHaveFoundBank) {
			tPrintName = "溢交退费通知书CT";
			texttag.add("BankAccNo", tLJTempFeeClassSchema.getBankAccNo());
			texttag.add("BankName", getBankName(tLJTempFeeClassSchema
					.getBankCode()));
			texttag.add("AccName", tLJTempFeeClassSchema.getAccName());
		} else {
			tPrintName = "溢交退费通知书CC";
			texttag.add("BankAccNo", "");
			texttag.add("BankName", "");
			texttag.add("AccName", "");
		}
		xmlExport.createDocument(tPrintName);// 初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());// 条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContSchema
				.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage))
			xmlExport.setUserLanguage(uLanguage);// 用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));// 系统语言

		String strUWError = "溢交保费";

		String thead = PrintTool.getHead(tLCContSchema.getAppntName(),
				tLCContSchema.getAppntSex(), uLanguage);

		texttag.add("AppntName", thead); // 投保人
		texttag.add("PrtNo", tLCContSchema.getPrtNo());
		texttag.add("AgentName", getAgentName(tLCContSchema.getAgentCode()));
		texttag.add("AgentCode", tLCContSchema.getAgentCode());
		texttag.add("ManageCom", getComName(tLCContSchema.getManageCom()));
		texttag.add("InsuredName", tLCContSchema.getInsuredName());
		texttag.add("UWCode", tLCContSchema.getUWOperator());
		texttag.add("BackMoney", new DecimalFormat("0.00").format(dSumMoney));
		texttag.add("GetMoney", new DecimalFormat("0.00").format(GetMoney));// 合计已交保费
		texttag.add("GivenMoney", new DecimalFormat("0.00").format(GivenMoney));// 合计应交保费
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("ActuGetNo", mLOPRTManagerSchema.getStandbyFlag6());
		texttag.add("Reason", strUWError);

		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("Today", df.format(new Date()));

		xmlExport.addTextTag(texttag);

		// 设置列表名字和字段名
		strRiskInfo = new String[5];
		strRiskInfo[0] = "GetNoticeNo";
		strRiskInfo[1] = "RiskName";
		strRiskInfo[2] = "GetMoney";
		strRiskInfo[3] = "GivenMoney";
		strRiskInfo[4] = "BackMoney";

		listTable.setName("RiskInfo");
		if (strRiskInfoFlag) {
			xmlExport.addDisplayControl("displayprem"); // 模版上的问题部分的控制标记
			xmlExport.addListTable(listTable, strRiskInfo);
		}
	}
}
