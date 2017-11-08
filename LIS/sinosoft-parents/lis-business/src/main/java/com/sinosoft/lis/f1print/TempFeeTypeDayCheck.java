package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class TempFeeTypeDayCheck {
private static Logger logger = Logger.getLogger(TempFeeTypeDayCheck.class);
	String tManageCom = "";
	String tPolicyCom = "";
	String tEndDay = "";
	String tStartDay = "";
	double day = 0;
	double month = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	public TempFeeTypeDayCheck() {
	}

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

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "862100");
		tTransferData.setNameAndValue("PolicyCom", "862100");
		tTransferData.setNameAndValue("StartDay", "2006-6-29");
		tTransferData.setNameAndValue("EndDay", "2006-6-29");

		VData tVData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tVData.addElement(tTransferData);
		tVData.addElement(tGI);
		TempFeeTypeDayCheck tCollectBankFeeBL = new TempFeeTypeDayCheck();
		if (!tCollectBankFeeBL.submitData(tVData, "PRINT")) {
			logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	private boolean getInputData(VData cInputData) {
		// 全局变量
		// mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// 收付机构
		tManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (tManageCom.equals("") || tManageCom == null) {
			return false;
		}
		// 管理机构
		tPolicyCom = (String) mTransferData.getValueByName("PolicyCom");
		if (tPolicyCom.equals("") || tPolicyCom == null) {
			return false;
		}
		// 起始日期
		tEndDay = (String) mTransferData.getValueByName("EndDay");
		// 终止日期
		tStartDay = (String) mTransferData.getValueByName("StartDay");

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {

		SSRS nSSRS = new SSRS();

		// 当前日期
		String tCurrentDate = PubFun.getCurrentDate();
		// 当前时间
		String tCurrentTime = PubFun.getCurrentTime();
		// 登陆机构名称
		String msql = "select name from ldcom where comcode = '"
				+ "?mGlobalInput?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("mGlobalInput", mGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv1);
		String bManageComName = nSSRS.GetText(1, 1);
		// 收付机构名称
		msql = "select name from ldcom where comcode = '" + "?tManageCom?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(msql);
		sqlbv2.put("tManageCom", tManageCom);
		ExeSQL rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv2);
		String sManageComName = nSSRS.GetText(1, 1);
		// 管理机构名称
		msql = "select name from ldcom where comcode = '" + "?tPolicyCom?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(msql);
		sqlbv3.put("tPolicyCom", tPolicyCom);
		ExeSQL sExeSQL = new ExeSQL();
		nSSRS = sExeSQL.execSQL(sqlbv3);
		String sPolicyComName = nSSRS.GetText(1, 1);

		/** @todo 新契约个人 */
		// 新契约个人
		logger.debug("===============================新契约个人==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype = '6'"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(msql);
		sqlbv4.put("tStartDay", tStartDay);
		sqlbv4.put("tEndDay", tEndDay);
		sqlbv4.put("tManageCom", tManageCom);
		sqlbv4.put("tPolicyCom", tPolicyCom);
		
		nSSRS = rExeSQL.execSQL(sqlbv4);
		String newperd = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + newperd);

		if (newperd != null && !newperd.equals("") && !newperd.equals("null")) {
			day = day + Double.parseDouble(newperd);
		}
		if (newperd == null || newperd.equals("null")) {
			newperd = "0";
		}
		/** @todo 新契约银代个人 */
		// 新契约银代个人
		logger.debug("===============================新契约银代==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype = '7'"
				+ "    and paymoney >= 0 "
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		// +" and not exists (select tempfeeno from ljtempfeeclass where
		// paymode='5' and tempfeeno = ljtempfee.tempfeeno)";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(msql);
		sqlbv5.put("tStartDay", tStartDay);
		sqlbv5.put("tEndDay", tEndDay);
		sqlbv5.put("tManageCom", tManageCom);
		sqlbv5.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv5);
		String newaged = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + newaged);

		if (newaged != null && !newaged.equals("") && !newaged.equals("null")) {
			day = day + Double.parseDouble(newaged);
		}
		if (newaged == null || newaged.equals("null")) {
			newaged = "0";
		}

		/** @todo 新契约团体 */
		// 新契约团体
		logger.debug("===============================新契约团体==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype = '4'"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(msql);
		sqlbv6.put("tStartDay", tStartDay);
		sqlbv6.put("tEndDay", tEndDay);
		sqlbv6.put("tManageCom", tManageCom);
		sqlbv6.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv6);
		String neworgd = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + newaged);

		if (neworgd != null && !neworgd.equals("") && !neworgd.equals("null")) {
			day = day + Double.parseDouble(neworgd);
		}
		if (neworgd == null || neworgd.equals("null")) {
			neworgd = "0";
		}

		/** @todo 新契约直销 */
		// 新契约直销
		logger.debug("===============================新契约团体==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype = '0'"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(msql);
		sqlbv7.put("tStartDay", tStartDay);
		sqlbv7.put("tEndDay", tEndDay);
		sqlbv7.put("tManageCom", tManageCom);
		sqlbv7.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv7);
		String NewZx = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + newaged);

		if (NewZx != null && !NewZx.equals("") && !NewZx.equals("null")) {
			day = day + Double.parseDouble(NewZx);
		}
		if (NewZx == null || NewZx.equals("null")) {
			NewZx = "0";
		}

		/** @todo 单证保证金 */
		// 单证保证金
		logger.debug("===============================单证保证金==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype = '11'"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(msql);
		sqlbv8.put("tStartDay", tStartDay);
		sqlbv8.put("tEndDay", tEndDay);
		sqlbv8.put("tManageCom", tManageCom);
		sqlbv8.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv8);
		String AgentMoney = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + newaged);

		if (AgentMoney != null && !AgentMoney.equals("")
				&& !AgentMoney.equals("null")) {
			day = day + Double.parseDouble(AgentMoney);
		}
		if (AgentMoney == null || AgentMoney.equals("null")) {
			AgentMoney = "0";
		}

		/** @todo 续期催缴 */
		// 续期催缴
		logger.debug("===============================续期催缴==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype in ('2','3','8','1')"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(msql);
		sqlbv9.put("tStartDay", tStartDay);
		sqlbv9.put("tEndDay", tEndDay);
		sqlbv9.put("tManageCom", tManageCom);
		sqlbv9.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv9);
		String nomdund = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + nomdund);

		if (nomdund != null && !nomdund.equals("") && !nomdund.equals("null")) {
			day = day + Double.parseDouble(nomdund);
		}
		if (nomdund == null || nomdund.equals("null")) {
			nomdund = "0";
		}

		/** @todo 续期预存 */
		// 续期预存
		logger.debug("===============================续期预存==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and othernotype in ('02','03')"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(msql);
		sqlbv10.put("tStartDay", tStartDay);
		sqlbv10.put("tEndDay", tEndDay);
		sqlbv10.put("tManageCom", tManageCom);
		sqlbv10.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv10);
		String nomprepd = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + nomprepd);

		if (nomprepd != null && !nomprepd.equals("")
				&& !nomprepd.equals("null")) {
			day = day + Double.parseDouble(nomprepd);
		}
		if (nomprepd == null || nomprepd.equals("null")) {
			nomprepd = "0";
		}

		/** @todo 客户预存 */
		// 客户预存
		logger.debug("===============================客户预存==================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "  and othernotype='5'"
				+ "    and enteraccdate >='"
				+ "?tStartDay?"
				+ "' "
				+ "    and enteraccdate <='"
				+ "?tEndDay?"
				+ "' "
				+ "    and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "    and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "    and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(msql);
		sqlbv11.put("tStartDay", tStartDay);
		sqlbv11.put("tEndDay", tEndDay);
		sqlbv11.put("tManageCom", tManageCom);
		sqlbv11.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv11);
		String cliprepsad = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + cliprepsad);

		if (cliprepsad != null && !cliprepsad.equals("")
				&& !cliprepsad.equals("null")) {
			day = day + Double.parseDouble(cliprepsad);
		}
		if (cliprepsad == null || cliprepsad.equals("null")) {
			cliprepsad = "0";
		}

		/** @todo 保全财务处理 */
		/***********************************************************************
		 * 保全财务处理
		 **********************************************************************/
		logger.debug("=====================================开始保全业务===============================");
		double BqBf = 0; // 保全保费
		double BqLx = 0; // 保全利息
		double BqDk = 0; // 贷款还款及利息
		double BqGb = 0; // 工本费
		double BqHt = 0; // 保全回退
		double BqOther = 0; // 其他

		String EdorSql = "select tempfeeno,sum(paymoney) from ljtempfeeclass where 1=1"
				+ " and othernotype='10'"
				+ " and enteraccdate between '"
				+ "?tStartDay?"
				+ "' and '"
				+ "?tEndDay?"
				+ "'"
				+ " and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ " and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ " and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')"
				+ " group by tempfeeno";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(EdorSql);
		sqlbv12.put("tStartDay", tStartDay);
		sqlbv12.put("tEndDay", tEndDay);
		sqlbv12.put("tManageCom", tManageCom);
		sqlbv12.put("tPolicyCom", tPolicyCom);
		tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv12);
		if (nSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
				String TempFeeNo = nSSRS.GetText(i, 1);
				double TempMoney = Double.parseDouble(nSSRS.GetText(i, 2));
				logger.debug("==========================================================================================");
				logger.debug("保全处理暂缴费 '" + TempFeeNo + "' 金额::::::: '"
						+ TempMoney + "' ");

				/***************************************************************
				 * 保全业务集中在三个表中 ljapayperson 存放保全核销后涉及到的保费 ljagetedorse
				 * 存放保全核销后的除保费的其他信息 ljsgetedorse 存放保全核销前的保全所有信息 ljagetdraw
				 * 存放保全回退核销后收回的满期金领取
				 **************************************************************/
				LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
				LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
				LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
				LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
				LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
				LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
				LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();

				// 查询对应的财务、业务类型
				tLJAGetEndorseDB.setGetNoticeNo(TempFeeNo);
				tLJSGetEndorseDB.setGetNoticeNo(TempFeeNo);
				tLJAPayPersonDB.setGetNoticeNo(TempFeeNo);
				tLJAGetDrawDB.setGetNoticeNo(TempFeeNo);

				tLJAGetEndorseSet = tLJAGetEndorseDB.query();
				tLJSGetEndorseSet = tLJSGetEndorseDB.query();
				tLJAPayPersonSet = tLJAPayPersonDB.query();
				tLJAGetDrawSet = tLJAGetDrawDB.query();
				/***************************************************************
				 * 保费及利息的部分到账处理 多种交费方式，整个暂缴费部分到账，先取到账部分计入保费 1、根据 保费/总金额 = t
				 * 取保费所占百分比 2、到账金额 * t = 本日到账保费 3、总金额 - 本日到账保费 = 本日到账利息
				 **************************************************************/

				// 此处为把保全保费及利息分开
				// 保费/总金额(实收)
				double k = 1;
				// 实收保费
				double SSBF = 0;
				// 实收利息
				double SSLX = 0;
				// 要件变更中的扣回年金部分
				double SSYF = 0;

				// 保费/总金额(应收)
				double j = 1;
				// 实收保费
				double YSBF = 0;
				// 实收利息
				double YSLX = 0;
				// 年金
				double YSYF = 0;

				if (tLJAGetEndorseSet.size() > 0) {
					for (int t = 1; t <= tLJAGetEndorseSet.size(); t++) {
						LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
						tLJAGetEndorseSchema = tLJAGetEndorseSet.get(t);

						// 质押贷款还款及利息
						if (tLJAGetEndorseSchema.getSubFeeOperationType()
								.equals("P008")
								|| tLJAGetEndorseSchema
										.getSubFeeOperationType()
										.equals("P009")) {
							logger.debug("其中质押贷款及利息金额::::" + TempMoney);
							BqDk = BqDk + TempMoney;
							break;
						}
						// 工本费
						else if (tLJAGetEndorseSchema.getFeeFinaType().equals(
								"GB")
								&& !tLJAGetEndorseSchema.getFeeOperationType()
										.equals("RB")) {
							logger.debug("其中工本费::::"
									+ tLJAGetEndorseSchema.getGetMoney());
							BqGb = BqGb + tLJAGetEndorseSchema.getGetMoney();
							continue;
						}
						// 保全回退
						else if (tLJAGetEndorseSchema.getFeeOperationType()
								.equals("RB")) {
							logger.debug("其中保全回退::::" + TempMoney);
							BqHt = BqHt + TempMoney;
							break;
						}
						// 保全保费
						else if (tLJAGetEndorseSchema.getFeeFinaType().equals(
								"BF")
								|| tLJAGetEndorseSchema
										.getSubFeeOperationType().substring(0,
												4).equals("P010")) {
							logger.debug("其中保全保费::::"
									+ tLJAGetEndorseSchema.getGetMoney());
							SSBF = SSBF + tLJAGetEndorseSchema.getGetMoney();
							continue;
						}
						// 保全利息
						else if (tLJAGetEndorseSchema.getFeeFinaType().equals(
								"LX")) {
							logger.debug("其中保全利息::::"
									+ tLJAGetEndorseSchema.getGetMoney());
							SSLX = SSLX + tLJAGetEndorseSchema.getGetMoney();
							continue;
						} else {
							logger.debug("其中未知的财务类型::::"
									+ tLJAGetEndorseSchema.getGetMoney());
							BqOther = BqOther
									+ tLJAGetEndorseSchema.getGetMoney();
							continue;
						}
					}
				}

				// if (SSBF > 0 && SSLX > 0)
				// {
				// k = SSBF / (SSLX + SSBF);
				// BqBf = BqBf + tLJTempFeeClassSchema.getPayMoney() * k;
				// BqLx = BqLx +
				// (tLJTempFeeClassSchema.getPayMoney() -
				// tLJTempFeeClassSchema.getPayMoney() * k);
				// continue;
				// }
				// else if (SSBF > 0 && SSLX == 0)
				// {
				// BqBf = BqBf + SSBF;
				// continue;
				// }

				if (tLJAPayPersonSet.size() > 0) {
					for (int t = 1; t <= tLJAPayPersonSet.size(); t++) {
						LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
						tLJAPayPersonSchema = tLJAPayPersonSet.get(t);
						logger.debug("其中保全保费::::"
								+ tLJAPayPersonSchema.getSumActuPayMoney());
						SSBF = SSBF + tLJAPayPersonSchema.getSumActuPayMoney();
					}
				}

				if (SSBF > 0 && SSLX > 0) {
					k = SSBF / (SSLX + SSBF);
					logger.debug("本次计算 实收保费/(实收保费+利息) ::::" + k);
					BqBf = BqBf + TempMoney * k;
					BqLx = BqLx + (TempMoney - TempMoney * k);
					continue;
				} else if (SSBF > 0 && SSLX == 0) {
					BqBf = BqBf + SSBF;
					continue;
				}

				if (tLJSGetEndorseSet.size() > 0) {
					logger.debug("??本次收费未核销,明细如下??");
					for (int t = 1; t <= tLJSGetEndorseSet.size(); t++) {
						LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
						tLJSGetEndorseSchema = tLJSGetEndorseSet.get(t);

						// 质押贷款还款及利息
						if (tLJSGetEndorseSchema.getSubFeeOperationType()
								.equals("P008")
								|| tLJSGetEndorseSchema
										.getSubFeeOperationType()
										.equals("P009")) {
							logger.debug("其中质押贷款及利息金额::::" + TempMoney);
							BqDk = BqDk + TempMoney;
							break;
						}
						// 工本费
						else if (tLJSGetEndorseSchema.getFeeFinaType().equals(
								"GB")) {
							logger.debug("其中工本费::::"
									+ tLJSGetEndorseSchema.getGetMoney());
							BqGb = BqGb + tLJSGetEndorseSchema.getGetMoney();
							continue;
						}
						// 保全回退
						else if (tLJSGetEndorseSchema.getFeeOperationType()
								.equals("RB")) {
							logger.debug("其中保全回退::::" + TempMoney);
							BqHt = BqHt + TempMoney;
							break;
						}
						// 保全保费
						else if (tLJSGetEndorseSchema.getFeeFinaType().equals(
								"BF")
								|| tLJSGetEndorseSchema
										.getSubFeeOperationType().substring(0,
												4).equals("P010")) {
							logger.debug("其中保全保费::::"
									+ tLJSGetEndorseSchema.getGetMoney());
							YSBF = YSBF + tLJSGetEndorseSchema.getGetMoney();
							continue;
						}

						// 保全利息
						else if (tLJSGetEndorseSchema.getFeeFinaType().equals(
								"LX")) {
							logger.debug("其中保全利息::::"
									+ tLJSGetEndorseSchema.getGetMoney());
							YSLX = YSLX + tLJSGetEndorseSchema.getGetMoney();
							continue;
						} else if (tLJSGetEndorseSchema.getFeeFinaType()
								.equals("YF")) {
							YSYF = YSYF + tLJSGetEndorseSchema.getGetMoney();
							continue;
						}

						else {
							logger.debug("其中未知的财务类型::::"
									+ tLJSGetEndorseSchema.getGetMoney());
							BqOther = BqOther
									+ tLJSGetEndorseSchema.getGetMoney();
							continue;
						}
					}
				}

				if (YSBF > 0 && YSLX > 0) {
					j = YSBF / (YSLX + YSBF);
					logger.debug("本次计算 实收保费/(实收保费+利息) ::::" + j);
					BqBf = BqBf + TempMoney * j;
					BqLx = BqLx + (TempMoney - TempMoney * j);
					continue;
				} else if (YSBF > 0 && YSLX == 0) {
					BqBf = BqBf + YSBF;
					continue;
				}

				if (tLJAGetDrawSet.size() > 0) {
					for (int t = 1; t <= tLJAGetDrawSet.size(); t++) {
						LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
						tLJAGetDrawSchema = tLJAGetDrawSet.get(t);
						double tMoney = tLJAGetDrawSchema.getGetMoney();
						BqHt = BqHt - tMoney;
					}
				}

				logger.debug("==========================================================================================");
			}
		}

		logger.debug("保全保费=================" + BqBf);
		logger.debug("保全利息=================" + BqLx);
		logger.debug("贷款还款及利息============" + BqDk);
		logger.debug("工本费===================" + BqGb);
		logger.debug("保全回退=================" + BqHt);
		logger.debug("错误的业务财务类型数据=====" + BqOther);

		day = day + BqBf + BqLx + BqDk + BqGb + BqHt;
		// 退保回退
		String TBCancel = String.valueOf(BqHt);
		String bqgbd = String.valueOf(BqGb);
		String bqbfd = String.valueOf(BqBf);
		String bqlxd = String.valueOf(BqLx);
		String dkmoneyd = String.valueOf(BqDk);
		String BQOther = String.valueOf(BqOther);
		logger.debug("=====================================保全业务结束===============================");

		/** @todo 理赔回退 */
		// 理赔回退56141
		logger.debug("=================================理赔回退================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and exists (select tempfeeno from ljtempfee where tempfeetype='6' and ManageCom like concat('"
				+ "?tManageCom?" + "','%') and policycom like concat('" + "?tPolicyCom?"
				+ "','%')  and tempfeeno=ljtempfeeclass.tempfeeno)"
				+ "    and enteraccdate >='" + "?tStartDay?" + "' "
				+ "    and enteraccdate <='" + "?tEndDay?" + "' "
				+ "    and ManageCom like '" + "?tManageCom?" + "%' "
				+ "    and policycom like '" + "?tPolicyCom?" + "%' ";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(msql);
		sqlbv13.put("tManageCom", tManageCom);
		sqlbv13.put("tPolicyCom", tPolicyCom);
		sqlbv13.put("tStartDay", tStartDay);
		sqlbv13.put("tEndDay", tEndDay);
		rExeSQL = new ExeSQL();
		nSSRS = new SSRS();
		nSSRS = rExeSQL.execSQL(sqlbv13);
		String LLHT = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + LLHT);

		if (LLHT != null && !LLHT.equals("") && !LLHT.equals("null")) {
			day = day + Double.parseDouble(LLHT);
		}
		if (LLHT == null || LLHT.equals("null")) {
			LLHT = "0";
		}

		/** @todo 银保通撤单冲正 */
		// 银保通撤单冲正
		logger.debug("=================================银保通撤单冲正================================");
		msql = " select sum(payMoney) from ljtempfeeclass "
				+ "  where 1=1"
				+ "    and exists (select tempfeeno from ljtempfee where tempfeetype='C' and othernotype='7' and ManageCom like concat('"
				+ "?tManageCom?" + "','%') and policycom like concat('" + "?tPolicyCom?"
				+ "','%')  and tempfeeno=ljtempfeeclass.tempfeeno)"
				+ "    and enteraccdate >='" + "?tStartDay?" + "' "
				+ "    and enteraccdate <='" + "?tEndDay?" + "' "
				+ "    and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "    and policycom like concat('" + "?tPolicyCom?" + "','%') ";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(msql);
		sqlbv14.put("tManageCom", tManageCom);
		sqlbv14.put("tStartDay", tStartDay);
		sqlbv14.put("tEndDay", tEndDay);
		sqlbv14.put("tPolicyCom", tPolicyCom);
		rExeSQL = new ExeSQL();
		nSSRS = new SSRS();
		nSSRS = rExeSQL.execSQL(sqlbv14);
		String A7CZ = nSSRS.GetText(1, 1);

		logger.debug("金额=====================" + A7CZ);

		if (A7CZ != null && !A7CZ.equals("") && !A7CZ.equals("null")) {
			day = day + Double.parseDouble(A7CZ);
		}
		if (A7CZ == null || A7CZ.equals("null")) {
			A7CZ = "0";
		}
		/** @todo 取操作员姓名 */
		/** ------------------取操作员姓名--------------------------* */
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?mGlobalInput?" + "'";
		logger.debug("OperSql===========" + OperSql);
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(OperSql);
		sqlbv15.put("mGlobalInput", mGlobalInput.Operator);
		ExeSQL oExeSQL = new ExeSQL();
		nSSRS = oExeSQL.execSQL(sqlbv15);
		String mOperator = nSSRS.GetText(1, 1);

		String mDate = tStartDay + "至" + tEndDay;

		/** @todo 向模板输出数据 */
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("CollectBankFee.vts", "PRINT"); // 最好紧接着就初始化xml文档
		logger.debug("day = " + String.valueOf(day));
		logger.debug("monty = " + String.valueOf(month));
		texttag.add("RBTB", TBCancel);
		texttag.add("A7CZ", A7CZ);
		texttag.add("LLHT", LLHT);
		texttag.add("bqgbd", bqgbd);
		texttag.add("bqbfd", bqbfd);
		texttag.add("bqlxd", bqlxd);
		texttag.add("bqdklxd", dkmoneyd);
		texttag.add("nomprepd", nomprepd);
		texttag.add("cliprepsad", cliprepsad);
		texttag.add("BQOther", BQOther);
		texttag.add("nomdund", nomdund);
		texttag.add("neworgd", neworgd);
		texttag.add("newperd", newperd);
		texttag.add("newaged", newaged);
		texttag.add("AgentMoney", AgentMoney);
		texttag.add("NewZx", NewZx);
		texttag.add("hjr", String.valueOf(day));
		texttag.add("sManageCom", tManageCom);
		texttag.add("ManageCom", mGlobalInput.ManageCom);
		texttag.add("Operator", mOperator);
		texttag.add("sManageComName", sManageComName);
		texttag.add("sPolicyComName", sPolicyComName);
		texttag.add("ManageComName", bManageComName);
		texttag.add("CurrentDate", tCurrentDate);
		texttag.add("CurrentTime", tCurrentTime);
		texttag.add("Date", mDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

}
