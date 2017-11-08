package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPlanSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author gaoht
 * @version 1.0
 */

public class GetChequeBL implements BusinessService{
private static Logger logger = Logger.getLogger(GetChequeBL.class);
	String tManageCom = "";
	String tEndDay = "";
	String tStartDay = "";
	String mActuGetNo = "";
	String mConfDate = "";
	double day = 0;
	double month = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();
	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	// private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	// private VData mLOPRTManagerSchemas = new VData();
	// private int mSchemaNum = 0;

	private TransferData mTransferData = new TransferData();

	public GetChequeBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			logger.debug("未找到打印数据");
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
	public static void main(String arg[]) {
		TransferData tTransferData = new TransferData();
		GlobalInput mGlobalInput = new GlobalInput();
		VData tVData = new VData();
		VData mResult = new VData();
		String ActuGetNo = "370210000028036";
		tTransferData.setNameAndValue("ActuGetNo", ActuGetNo);
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "8632";
		mGlobalInput.ManageCom = "8632";
		tVData.addElement(mGlobalInput);
		tVData.addElement(tTransferData);
		GetChequeBL tGetChequeBL = new GetChequeBL();
		if (!tGetChequeBL.submitData(tVData, "")) {
			logger.debug("false!!!");
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
		// mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		// if (mGlobalInput == null) {
		// buildError("getInputData", "没有得到足够的信息！");
		// return false;
		// }
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// tManageCom = (String) mTransferData.getValueByName("ManageCom");
		// if (tManageCom.equals("") || tManageCom == null) {
		// return false;
		// }
		// mConfDate = (String) mTransferData.getValueByName("ConfDate");
		mActuGetNo = (String) mTransferData.getValueByName("ActuGetNo");
		if (mActuGetNo.equals(null) || mActuGetNo == "") {
			CError tError = new CError();
			tError.moduleName = "GetChequeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;

		}
		return true;
	}

	private boolean checkdata() {
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tt = "select * from ljaget where actugetno='" + "?mActuGetNo?"
				+ "' and enteraccdate is not null";
		sqlbv1.sql(tt);
		sqlbv1.put("mActuGetNo", mActuGetNo);
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = new LJAGetSet();
		LJAGetSchema t = new LJAGetSchema();

		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv1);
		if (tLJAGetSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "GetChequeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请先付费，再打印凭证!";
			this.mErrors.addOneError(tError);
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
		SSRS tSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		// String PayFlag = request.getParameter("FinDayType");
		// String tBranchAttr = request.getParameter("BranchAttr")+"%";
		String tCurrentDate = PubFun.getCurrentDate();
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("GetCheque");
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String msql = "select Drawer,DrawerID,SumGetMoney,PayMode,ConfDate,otherno,othernotype,operator,actualdrawer,actualdrawerid from LJAGet where ActuGetNo = '"
				+ "?mActuGetNo?" + "'";
		sqlbv2.sql(msql);
		sqlbv2.put("mActuGetNo", mActuGetNo);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv2);
		if (nSSRS.MaxRow <= 0) {
			CError tError = new CError();
			tError.moduleName = "GetChequeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "未找到相关信息";
			this.mErrors.addOneError(tError);
			return false;
		}
		String Drawer = nSSRS.GetText(1, 1);
		String DrawerID = nSSRS.GetText(1, 2);
		String SumGetMoney = nSSRS.GetText(1, 3);
		String PayMode = nSSRS.GetText(1, 4);
		String ConfDate = nSSRS.GetText(1, 5);
		String otherno = nSSRS.GetText(1, 6);
		String othernotype = nSSRS.GetText(1, 7);
		String operator = nSSRS.GetText(1, 8);
		String YewName = "";
		String actualdrawer = nSSRS.GetText(1, 9);
		String actualdrawerid = nSSRS.GetText(1, 10);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String ssql = "select codename from ldcode where codetype='gettype' and code ='"
				+ "?PayMode?" + "'";
		sqlbv3.sql(ssql);
		sqlbv3.put("PayMode", PayMode);
		ExeSQL sExeSQL = new ExeSQL();
		nSSRS = sExeSQL.execSQL(sqlbv3);
		String mPayMode = nSSRS.GetText(1, 1);
		String Riskname = "";
		logger.debug("业务类型编号=============" + othernotype);
		DecimalFormat df = new DecimalFormat("0.00");
		if (othernotype.equals("10")) {
			logger.debug("----------------------保全项目----------------------");
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			String EdorSql = "select b.edorname from LJAGetDraw a ,lmedoritem b  where trim(a.FeeOperationType) = trim(b.edorcode) and  ActuGetNo = '"
					+ "?mActuGetNo?" + "'";
			sqlbv4.sql(EdorSql);
			sqlbv4.put("mActuGetNo", mActuGetNo);
			ExeSQL rExeSQL = new ExeSQL();
			nSSRS = rExeSQL.execSQL(sqlbv4);
			if (nSSRS.getMaxRow() > 0) {
				YewName = nSSRS.GetText(1, 1);
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				EdorSql = " select a.riskname,c.getmoney from lmrisk a ,LJAGetDraw b,lpedorapp c where c.EndorsementNo=b.EndorsementNo and b.riskcode=a.riskcode and c.edoracceptno in (select otherno from ljaget where ActuGetNo = '"
						+ "?mActuGetNo?" + "')";
				sqlbv5.sql(EdorSql);
				sqlbv5.put("mActuGetNo", mActuGetNo);
				ExeSQL cExeSQL = new ExeSQL();
				nSSRS = cExeSQL.execSQL(sqlbv5);

				for (int i = 1; i <= nSSRS.MaxRow; i++) {

					String money = nSSRS.GetText(i, 2);
					strArr = new String[2];
					strArr[0] = nSSRS.GetText(i, 1);
					strArr[1] = df.format(Double.parseDouble(money));
					tlistTable.add(strArr);
				}

			} else {
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				EdorSql = "select b.edorname from LJAGetEndorse a ,lmedoritem b  where trim(a.FeeOperationType) = trim(b.edorcode) and  ActuGetNo = '"
						+ "?mActuGetNo?"+ "'";
				sqlbv6.sql(EdorSql);
				sqlbv6.put("mActuGetNo", mActuGetNo);
				rExeSQL = new ExeSQL();
				nSSRS = rExeSQL.execSQL(sqlbv6);
				if (nSSRS.getMaxRow() > 0) {
					YewName = nSSRS.GetText(1, 1);
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					EdorSql = "select a.riskname,c.codename,sum((-1)*(b.getmoney)) "
							+ "from lmrisk a ,LJAGetEndorse b,ldcode c "
							+ "where  b.riskcode=a.riskcode and c.code=b.FeeFinaType and c.codetype='finfeetype'"
							+ "and b.ActuGetNo in (select ActuGetNo from ljaget where ActuGetNo= '"
							+ "?mActuGetNo?" + "') group by a.riskname,c.codename";
					sqlbv7.sql(EdorSql);
					sqlbv7.put("mActuGetNo", mActuGetNo);
					ExeSQL cExeSQL = new ExeSQL();
					nSSRS = cExeSQL.execSQL(sqlbv7);

					for (int i = 1; i <= nSSRS.MaxRow; i++) {
						String money = nSSRS.GetText(i, 3);
						strArr = new String[2];
						strArr[0] = nSSRS.GetText(i, 1) + nSSRS.GetText(i, 2);
						strArr[1] = df.format(Double.parseDouble(money));
						tlistTable.add(strArr);
					}

				} else if (nSSRS.getMaxRow() <= 0) {
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					EdorSql = "select a.riskname,b.getmoney,(select codename from ldcode where codetype='finfeetype' and trim(code)=trim(b.feefinatype)) from lmrisk a,ljagetdraw b where b.riskcode=a.riskcode and ActuGetNo = '"
							+ "?mActuGetNo?" + "'";
					sqlbv8.sql(EdorSql);
					sqlbv8.put("mActuGetNo", mActuGetNo);
					ExeSQL cExeSQL = new ExeSQL();
					nSSRS = cExeSQL.execSQL(sqlbv8);
					if (nSSRS.getMaxRow() > 0) {

						for (int i = 1; i <= nSSRS.MaxRow; i++) {
							YewName = YewName + nSSRS.GetText(1, 3);
							String money = nSSRS.GetText(i, 2);
							strArr = new String[2];
							strArr[0] = nSSRS.GetText(i, 1);
							strArr[1] = df.format(Double.parseDouble(money));
							tlistTable.add(strArr);

						}

					} else {
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						EdorSql = "select a.riskname,(-1)*b.sumactupaymoney,(select codename from ldcode where codetype='finfeetype' and trim(code)=trim(b.paytype)) from lmrisk a,ljapayperson b where b.riskcode=a.riskcode and b.payno = '"
								+ "?mActuGetNo?" + "'";
						sqlbv9.sql(EdorSql);
						sqlbv9.put("mActuGetNo", mActuGetNo);
						ExeSQL yExeSQL = new ExeSQL();
						nSSRS = yExeSQL.execSQL(sqlbv9);

						if (nSSRS.getMaxRow() > 0) {

							for (int i = 1; i <= nSSRS.MaxRow; i++) {
								YewName = YewName + nSSRS.GetText(1, 3);
								String money = nSSRS.GetText(i, 2);
								strArr = new String[2];
								strArr[0] = nSSRS.GetText(i, 1);

								tlistTable.add(strArr);

							}

						} else {
							CError tError = new CError();
							tError.moduleName = "GetChequeBL";
							tError.functionName = "getInputData";
							tError.errorMessage = "保全项目付费子表查询失败!";
							this.mErrors.addOneError(tError);
							return false;
						}
					}
				}
			}

		}
		if (othernotype.equals("5")) {
			logger.debug("----------------------理赔项目----------------------");
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			String ClaimSql = "select b.baltypedesc,b.subbaltypedesc,c.riskname,a.pay from LJAGetClaim a ,LLBalanceRela b,lmrisk c where trim(a.SubFeeOperationType) = trim(b.subbaltype) and  ActuGetNo = '"
					+ "?mActuGetNo?"
					+ "' and a.feeoperationtype = b.baltype and c.riskcode = a.riskcode";
			sqlbv10.sql(ClaimSql);
			sqlbv10.put("mActuGetNo", mActuGetNo);
			ExeSQL rExeSQL = new ExeSQL();
			nSSRS = rExeSQL.execSQL(sqlbv10);
			YewName = nSSRS.GetText(1, 1) + "-" + nSSRS.GetText(1, 2);
			logger.debug("理赔付费查询" + ClaimSql);
			for (int i = 1; i <= nSSRS.MaxRow; i++) {
				String money = nSSRS.GetText(i, 4);
				strArr = new String[2];
				strArr[0] = nSSRS.GetText(i, 3);
				strArr[1] = df.format(Double.parseDouble(money));
				tlistTable.add(strArr);
			}
		}
		if (othernotype.equals("2")) {
			logger.debug("----------------------生存领取项目----------------------");
			YewName = "生存领取付费";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			String tempfeesql = "select a.riskname,b.getmoney from lmrisk a,LJAGetDraw b where  b.ActuGetNo = '"
					+ "?mActuGetNo?" + "' and b.riskcode=a.riskcode";
			sqlbv11.sql(tempfeesql);
			sqlbv11.put("mActuGetNo", mActuGetNo);
			ExeSQL cExeSQL = new ExeSQL();
			nSSRS = cExeSQL.execSQL(sqlbv11);
			for (int i = 1; i <= nSSRS.MaxRow; i++) {
				String money = nSSRS.GetText(i, 2);
				if (money.equals("") || money.equals("null"))
					money = "0";
				strArr = new String[2];
				strArr[0] = nSSRS.GetText(i, 1);
				strArr[1] = df.format(Double.parseDouble(money));
				tlistTable.add(strArr);
			}

		}
		if (othernotype.equals("4")) {

			logger.debug("----------------------新契约暂交费退费----------------------");
			YewName = "新契约暂交费退费";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			String tempfeesql = "select a.riskname,b.getmoney from ljagettempfee b,lmrisk a where b.actugetno = '"
					+ "?mActuGetNo?" + "' and b.riskcode=a.riskcode";
			sqlbv12.sql(tempfeesql);
			sqlbv12.put("mActuGetNo", mActuGetNo);
			ExeSQL cExeSQL = new ExeSQL();
			nSSRS = cExeSQL.execSQL(sqlbv12);
			if (nSSRS.MaxRow > 0) {
				for (int i = 1; i <= nSSRS.MaxRow; i++) {
					String money = nSSRS.GetText(i, 2);
					strArr = new String[2];
					strArr[0] = nSSRS.GetText(i, 1);
					strArr[1] = df.format(Double.parseDouble(money));
					tlistTable.add(strArr);
				}
			} else {
				String tName = "";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				String tSql = "select riskcode from ljagettempfee where actugetno='"
						+ "?mActuGetNo?" + "'";
				sqlbv13.sql(tSql);
				sqlbv13.put("mActuGetNo", mActuGetNo);
				cExeSQL = new ExeSQL();
				nSSRS = cExeSQL.execSQL(sqlbv13);
				if (nSSRS.MaxRow > 0) {
					LDPlanDB tLDPlanDB = new LDPlanDB();
					LDPlanSet tLDPlanSet = new LDPlanSet();
					tLDPlanDB.setContPlanCode(nSSRS.GetText(1, 1));
					tLDPlanSet = tLDPlanDB.query();
					if (tLDPlanSet.size() > 0) {
						tName = tLDPlanSet.get(1).getContPlanName();
					} else {
						tName = "新契约部分退费";
					}
				}
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				String Sql = "select sum(getmoney) from ljagettempfee where actugetno='"
						+ "?mActuGetNo?" + "'";
				sqlbv14.sql(Sql);
				sqlbv14.put("mActuGetNo", mActuGetNo);
				cExeSQL = new ExeSQL();
				nSSRS = cExeSQL.execSQL(sqlbv14);
				if (nSSRS.MaxRow > 0) {
					String money = nSSRS.GetText(1, 1);
					strArr = new String[2];
					strArr[0] = tName;
					strArr[1] = df.format(Double.parseDouble(money));
					tlistTable.add(strArr);
				}

			}
		}
		if (othernotype.equals("YC")) {

			logger.debug("----------------------新契约暂交费退费----------------------");
			YewName = "新契约暂交费退费";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			String tempfeesql = "select '',b.getmoney from ljagettempfee b where b.actugetno = '"
					+ "?mActuGetNo?" + "'";
			sqlbv15.sql(tempfeesql);
			sqlbv15.put("mActuGetNo", mActuGetNo);
			ExeSQL cExeSQL = new ExeSQL();
			nSSRS = cExeSQL.execSQL(sqlbv15);
			for (int i = 1; i <= nSSRS.MaxRow; i++) {
				String money = nSSRS.GetText(i, 2);
				strArr = new String[2];
				strArr[0] = "客户预存";
				strArr[1] = df.format(Double.parseDouble(money));
				tlistTable.add(strArr);
			}
		}
		if (othernotype.equals("3")) {

			logger.debug("----------------------续期暂交费退费----------------------");
			YewName = "续期暂交费退费";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			String tsql = "select * from ljaget where actugetno='" + "?mActuGetNo?"
					+ "'";
			sqlbv16.sql(tsql);
			sqlbv16.put("mActuGetNo", mActuGetNo);
			LJAGetDB tLJAGetDB = new LJAGetDB();
			LJAGetSet tLJAGetSet = new LJAGetSet();
			tLJAGetSet = tLJAGetDB.executeQuery(sqlbv16);
			String tContno = tLJAGetSet.get(1).getOtherNo();
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			tsql = "select * from lcpol where appflag='1' and contno='"
					+ "?tContno?" + "'";
			sqlbv17.sql(tsql);
			sqlbv17.put("tContno", tContno);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.executeQuery(sqlbv17);
			String RiskName = "";
			logger.debug(tsql);
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				tsql = "select riskshortname from lmrisk where riskcode = '"
						+ "?riskcode?" + "'";
				sqlbv18.sql(tsql);
				sqlbv18.put("riskcode", tLCPolSet.get(i).getRiskCode());
				ExeSQL cExeSQL = new ExeSQL();
				nSSRS = cExeSQL.execSQL(sqlbv18);
				if (i > 1)
					RiskName = RiskName + "+";
				RiskName = RiskName + nSSRS.GetText(1, 1);
			}

			double money = tLJAGetSet.get(1).getSumGetMoney();
			strArr = new String[2];
			strArr[0] = RiskName;
			strArr[1] = df.format(Double.parseDouble(String.valueOf(money)));
			tlistTable.add(strArr);

		}

		if (othernotype.equals("8")) {
			logger.debug("------------------溢交退费---------------------");
			YewName = "溢交退费";
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			String OtherSql = "select distinct a.riskname,c.GetMoney from lcpol b,lmrisk a,LJAGetOther c where b.riskcode=a.riskcode and c.otherno=b.prtno and b.prtno='"
					+ "?otherno?" + "'";
			sqlbv19.sql(OtherSql);
			sqlbv19.put("otherno", otherno);
			ExeSQL cExeSQL = new ExeSQL();
			nSSRS = cExeSQL.execSQL(sqlbv19);
			for (int i = 1; i <= nSSRS.MaxRow; i++) {
				String money = nSSRS.GetText(i, 2);
				strArr = new String[2];
				strArr[0] = nSSRS.GetText(i, 1);
				if (i == 1) {
					strArr[1] = df.format(Double.parseDouble(money));
				} else {
					strArr[1] = null;
				}
				tlistTable.add(strArr);
			}
		}
		if (othernotype.equals("9")) {
			logger.debug("------------------续期回退---------------------");
			YewName = "续期回退";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			String XQsql = "select a.riskname,(-1)*b.sumactupaymoney from ljapayperson b,lmrisk a where b.payno = '"
					+ "?mActuGetNo?" + "' and b.riskcode=a.riskcode";
			sqlbv20.sql(XQsql);
			sqlbv20.put("mActuGetNo", mActuGetNo);
			ExeSQL cExeSQL = new ExeSQL();
			nSSRS = cExeSQL.execSQL(sqlbv20);
			for (int i = 1; i <= nSSRS.MaxRow; i++) {
				String money = nSSRS.GetText(i, 2);
				strArr = new String[2];
				strArr[0] = nSSRS.GetText(i, 1);
				strArr[1] = df.format(Double.parseDouble(money));
				tlistTable.add(strArr);
			}
		}
		/** ------------------取操作员姓名--------------------------* */
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?Operator?" + "'";
		logger.debug("OperSql===========" + OperSql);
		sqlbv21.sql(OperSql);
		sqlbv21.put("Operator", mGlobalInput.Operator);
		ExeSQL oExeSQL = new ExeSQL();
		nSSRS = oExeSQL.execSQL(sqlbv21);
		String moperator = nSSRS.GetText(1, 1);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GetCheque.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("Drawer", Drawer);
		texttag.add("DrawerID", DrawerID);
		texttag.add("DSumGetMoney", PubFun.getChnMoney(Double
				.parseDouble(SumGetMoney)));
		texttag.add("SumGetMoney", SumGetMoney);
		texttag.add("PayMode", mPayMode);
		texttag.add("ConfDate", ConfDate);
		texttag.add("YewName", YewName);
		texttag.add("actualdrawer", actualdrawer);
		texttag.add("actualdrawerid", actualdrawerid);
		texttag.add("moperator", moperator);
		texttag.add("mActuGetNo", mActuGetNo);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		xmlexport.addListTable(tlistTable, strArr);

		// xmlexport.outputDocumentToFile("d:\\", "atest"); //输出xml文档到文件
		logger.debug("w");

		mResult.addElement(xmlexport);
		return true;
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
