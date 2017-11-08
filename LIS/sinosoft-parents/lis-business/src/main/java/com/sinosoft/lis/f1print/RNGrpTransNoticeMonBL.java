package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class RNGrpTransNoticeMonBL {
private static Logger logger = Logger.getLogger(RNGrpTransNoticeMonBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private LYReturnFromBankBSchema mLYReturnFromBankBSchema = new LYReturnFromBankBSchema();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	public RNGrpTransNoticeMonBL() {
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
		if (!getLoprtmanagerInfo()) {
			return false;
		}
		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}

		return true;

	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8102100651177");
		tLOPRTManagerSchema.setOtherNo("BJ039931281000566");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8632";
		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);
		// tVData.addElement(tTransferData);
		RNGrpTransNoticeMonBL tXQWoodcutterNoticeBL = new RNGrpTransNoticeMonBL();

		if (!tXQWoodcutterNoticeBL.submitData(tVData, "PRINT")) {
			logger.debug("失败");

		} else {
			logger.debug("成功");
		}

	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		// 得到打印管理表中的数据 prtseq. otherno ,
		mLOPRTManagerSchema = (LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0);
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	private boolean getLoprtmanagerInfo() {
		/** @todo 优化 mLOPRTManagerSet mLOPRTManagerSchema */
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		logger.debug(mLOPRTManagerSchema.getPrtSeq());
		logger.debug(mLOPRTManagerSchema.getOtherNo());
		mLOPRTManagerSet = tLOPRTManagerDB.query();
		if (mLOPRTManagerSet.size() <= 0) {
			buildError("getLYReturnFromBankBInfo", "LYReturnFromBankB获得数据有误！");
			return false;
		}
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */

	private boolean getPrintData() {
		double tMainRisk = 0;
		double tExtendRisk = 0;
		double tCountRisk = 0;
		String tFee = "";
		boolean tFlag = false;
		ExeSQL exeSql = new ExeSQL();
		SSRS tzuSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		SSRS ydSSRS = new SSRS();
		String remark = "";

		String tAgentCode = "";
		// 有了客户的全部信息

		tzuSSRS = exeSql
				.execSQL(" select lc.postaladdress,lc.zipcode,la.appntname , la.appntsex from lcaddress lc ,lcappnt la where lc.addressno = la.addressno and lc.customerno = la.appntno and la.contno ='"
						+ mLOPRTManagerSet.get(1).getOtherNo() + "'");
		String tPostalAddress = tzuSSRS.GetText(1, 1);
		String tZipCode = tzuSSRS.GetText(1, 2);
		String tAppntName = tzuSSRS.GetText(1, 3);
		String tAppntSex = tzuSSRS.GetText(1, 4);
		String txsAppntName = "";
		if (tAppntSex.equals("0")) {
			txsAppntName = tAppntName + "先生";
		} else {
			txsAppntName = tAppntName + "女士";
		}
		// 有了代理员的信息 String tAgentManage
		nSSRS = exeSql.execSQL("select agentcode   from lccont where contno ='"
				+ mLOPRTManagerSet.get(1).getOtherNo() + "'");

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(nSSRS.GetText(1, 1));
		if (!tLAAgentDB.getInfo()) {
			buildError("dealData", "查询代理人信息失败!");
			return false;
		} else {
			tAgentCode = nSSRS.GetText(1, 1);
			String ManageName = tLAAgentDB.getManageCom();
			String tAgentName = tLAAgentDB.getName();
			String tAgentPhone = tLAAgentDB.getPhone();

			tzuSSRS = exeSql
					.execSQL("select (select name from labranchgroup where managecom ='"
							+ ManageName
							+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
							+ tAgentCode
							+ "' ))), "
							+ " (select name from labranchgroup where managecom ='"
							+ ManageName
							+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
							+ tAgentCode
							+ "' ))), "
							+ "  ((select name from labranchgroup where managecom ='"
							+ ManageName
							+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
							+ tAgentCode + "' ))) from dual ");
			String tAgentManage = tzuSSRS.GetText(1, 1) + tzuSSRS.GetText(1, 2)
					+ tzuSSRS.GetText(1, 3);
			// 得到本次需要打印划款通知书的多次getnoticeno 以便统计各种值

			String[] ContractTitle = new String[3]; // 随意定义的与显示无关
			ContractTitle[0] = "受益人";
			ContractTitle[1] = "证件号";
			ContractTitle[2] = "受益顺序";
			// ContractTitle[3] = "受益份额";
			ListTable tlistTable = new ListTable();
			tlistTable.setName("XQHK");
			String strArr[] = null;
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();

			String tCSql = "select getnoticeno " + "from ljapayperson "
					+ "where curpaytodate >= add_months('"
					+ mLOPRTManagerSet.get(1).getStandbyFlag6() + "', -11) "
					+ "and contno = '" + mLOPRTManagerSet.get(1).getOtherNo()
					+ "' " + "and curpaytodate <= '"
					+ mLOPRTManagerSet.get(1).getStandbyFlag6() + "' "
					+ "order by curpaytodate  ";
			logger.debug("提取本年度所有在这个日期的getnoticeno开始: " + tCSql);
			tSSRS = tExeSQL.execSQL(tCSql);
			String mGetnoticeno = "";
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String tGetnoticeno = tSSRS.GetText(i, 1);
				mGetnoticeno += "'" + tGetnoticeno + "' , ";
				logger.debug("=======需要打印的通知书号码==========" + tGetnoticeno);
				strArr = new String[3];
				// 应缴保费日
				String tSql = "select lastpaytodate from ljapayperson where getnoticeno = '"
						+ tGetnoticeno + "'";
				tzuSSRS = exeSql.execSQL(tSql);
				if (tzuSSRS.MaxRow > 0) {
					if (tzuSSRS.GetText(1, 1).equals("1899-12-31")) {
						tSql = "select cvalidate from lcpol where mainpolno = polno and contno = '"
								+ mLOPRTManagerSet.get(1).getOtherNo() + "'";
						tzuSSRS = exeSql.execSQL(tSql);
						strArr[0] = tzuSSRS.GetText(1, 1);
					} else {
						strArr[0] = tzuSSRS.GetText(1, 1);
					}
				}

				// 实收保费日
				tSql = "select EnterAccDate ,paymoney ,paymode  from ljtempfeeclass where tempfeeno = '"
						+ tGetnoticeno + "'";
				tzuSSRS = exeSql.execSQL(tSql);
				strArr[1] = tzuSSRS.GetText(1, 1);
				// 保费金额
				tCountRisk += Double.parseDouble(tzuSSRS.GetText(1, 2));
				String tPayMoney = tzuSSRS.GetText(1, 2);
				strArr[2] = new DecimalFormat("0.00").format(Double
						.parseDouble(tPayMoney));
				// 交费形式 strArr[3]
				// String tPayMode = tzuSSRS.GetText(1, 3);
				// if (!tPayMode.equals("null") && tPayMode.equals("7")) {
				// strArr[3] = "银行划款";
				// }
				// else if( tPayMode.equals("1")){
				// strArr[3] = "现金缴费";
				//
				// }
				// else
				// {
				// strArr[3] = "其他缴费";
				// }
				tlistTable.add(strArr);
			}

			String t1Sql = "update loprtmanager set stateflag = '1'   where code = '48' and standbyflag4 in("
					+ mGetnoticeno + " '111111')";

			String countriskmoney = PubFun.getChnMoney(tCountRisk);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("RNGrpTransNoticeMon.vts", "PRINT"); // 最好紧接着就初始化xml文档
			xmlexport.addListTable(tlistTable, ContractTitle);

			texttag.add("AppntName", txsAppntName);
			texttag.add("Address", tPostalAddress);
			texttag.add("ZipCode", tZipCode);
			texttag.add("LCCont.AppntName", tAppntName);
			texttag.add("LAAgent.AgentCode", tAgentCode);
			texttag.add("LAAgent.Name", tAgentName);
			texttag.add("ManageComName", tAgentManage);

			texttag.add("LCCont.ContNo", mLOPRTManagerSet.get(1).getOtherNo());

			texttag.add("SumPrem", countriskmoney);

			texttag.add("SystemDate", PubFun.getCurrentDate());
			texttag.add("ESumPrem", new DecimalFormat("0.00")
					.format(tCountRisk));
			texttag.add("LAAgent.Phone", tAgentPhone);

			// 如果是已经打印的，则在通知书的下面显示已经打印的标记
			//
			if (mLOPRTManagerSet.get(1).getRemark() != null
					&& !mLOPRTManagerSet.get(1).getRemark().equals("null")
					&& mLOPRTManagerSet.get(1).getRemark().equals("1")) {
				texttag
						.add("reNewPrint",
								"您的续期保费已划转成功，此通知单是我公司认可的缴费凭证。本保单项下本保单年度内缴费以此凭证为准，原证同时作废，特此。");
			}

			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			// xmlexport.outputDocumentToFile("D:\\", "atest"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setSchema(mLOPRTManagerSet.get(1));
			tLOPRTManagerDB.setStateFlag("1");
			tLOPRTManagerDB.setRemark("1");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			MMap map = new MMap();
			map.put(tLOPRTManagerSchema, "UPDATE");
			map.put(t1Sql, "UPDATE");
			VData t1VData = new VData();
			t1VData.add(map);

			PubSubmit p = new PubSubmit();
			if (!p.submitData(t1VData, "")) {
				buildError("dealData", "打印表处理失败!");
				return false;
			}

		}
		return true;
	}
}
