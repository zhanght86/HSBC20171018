package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:附加险加保
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */

public class PEdorAAPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(PEdorAAPrintBL.class);

	// 公共数据
	private VData mResult = new VData();
	//public CErrors mErrors = new CErrors();
	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorAppSchema mLPEdorAppSchema =new LPEdorAppSchema();
	private XmlExportNew xmlexport = new XmlExportNew();
	private TextTag mTextTag = new TextTag();
	private ListTable tAAListTable = new ListTable();
	private ListTable tNSSListTable = new ListTable();
	private String[] strNSS;
	private LPPolSchema tLPPolSchema;
	private double RNewPrem;
	private String Amnt;

	// public static final String FormulaOneVTS = "PrtEndorsement.vts";

	public PEdorAAPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AA数据传输失败!");
			return false;
		}
		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}
		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AA数据打印失败!");
			return false;
		}
		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
			return false;
		}

		logger.debug("End preparing the data to print ====>" + mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
	    mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName (
	            "LPEdorAppSchema",0) ;
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAA")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	@SuppressWarnings("static-access")
	private boolean dealData() {
		//xmlexport.addDisplayControl("displayHead1");
BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayAA");
		ExeSQL exeSQL = new ExeSQL();
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号
        BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名

		// 加保附加险
		tAAListTable.setName("AA");
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() == 0) {
			mErrors.addOneError("打印生成数据时，取附加险加保信息失败1!");
			return false;
		}

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setSchema(tLPPolSet.get(i));
			// 加保附加险信息
			// 交费和计
			// 增加保额，增加保费
			// 险种代码，保险起期止期，有效保额
			// 险种名称
			LMRiskDB tLMRiskDB = new LMRiskDB();
			LMRiskSchema tLMRiskSchema = new LMRiskSchema();
			tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
				return false;
			}
			tLMRiskSchema = tLMRiskDB.getSchema();
			String strSQL=""; 
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			/************deleted  by pst  on 2008-09-11 MS批单无需显示健康，职业加费*************/
			// 计算健康加费，职业加费
//			String strSQL = "SELECT Sum(a.GetMoney)"
//					+ " FROM LJSGetEndorse a,LPDuty b" + " WHERE b.PolNo='"
//					+ tLPPolSchema.getPolNo() + "'" + " and b.EdorNo='"
//					+ mLPEdorItemSchema.getEdorNo() + "'" + " and b.EdorType='"
//					+ mLPEdorItemSchema.getEdorType() + "'"
//					+ " and a.DutyCode= b.DutyCode" + " and a.EndorsementNo='"
//					+ mLPEdorItemSchema.getEdorNo() + "'"
//					+ " and a.FeeOperationType='"
//					+ mLPEdorItemSchema.getEdorType() + "'"
//					+ " and (a.SubFeeOperationType like '%"
//					+ BqCode.Pay_InsurAddPremHealth
//					+ "%' or a.SubFeeOperationType like '%"
//					+ BqCode.Pay_AppntAddPremHealth + "%')";
//			String JPrem = "0.00";
//			if (exeSQL.getOneValue(strSQL) != "") {
//				JPrem = String.valueOf(exeSQL.getOneValue(strSQL));
//			}
//			strSQL = "SELECT Sum(a.GetMoney)"
//					+ " FROM LJSGetEndorse a,LPDuty b" + " WHERE b.PolNo='"
//					+ tLPPolSchema.getPolNo() + "'" + " and b.EdorNo='"
//					+ mLPEdorItemSchema.getEdorNo() + "'" + " and b.EdorType='"
//					+ mLPEdorItemSchema.getEdorType() + "'"
//					+ " and a.DutyCode= b.DutyCode" + " and a.EndorsementNo='"
//					+ mLPEdorItemSchema.getEdorNo() + "'"
//					+ " and a.FeeOperationType='"
//					+ mLPEdorItemSchema.getEdorType() + "'"
//					+ " and (a.SubFeeOperationType like '%"
//					+ BqCode.Pay_InsurAddPremOccupation
//					+ "%' or a.SubFeeOperationType like '%"
//					+ BqCode.Pay_AppntAddPremOccupation + "%')";
//			String ZPrem = "0.00";
//			if (exeSQL.getOneValue(strSQL) != "") {
//				ZPrem = String.valueOf(exeSQL.getOneValue(strSQL));
//			}
			// 增加保额份数,判断险种销售方式并记录
			String tSql = "SELECT (case when exists(select 'x' from LCDuty b where ContNo='"
					+ "?ContNo?"
					+ "' and PolNo='"
					+ "?PolNo?"
					+ "' and exists(select 'y' from LMDuty where DutyCode=b.DutyCode and AmntFlag='1')) then '1'"
					+ "  else '2'" + "  end)" + "FROM dual";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", tLPPolSchema.getContNo());
			sqlbv.put("PolNo", tLPPolSchema.getPolNo());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorERDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获得险种销售方式时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if ("2".equals(tSSRS.GetText(1, 1))) {
				strSQL = "SELECT (a.Mult-b.Mult) as a"
						+ " FROM LPPol a,LCPol b" + " WHERE a.EdorNo='"
						+ "?EdorNo?" + "'"
						+ " and a.EdorType='" + "?EdorType?"
						+ "'" + " and a.PolNo='" + "?PolNo?"
						+ "'" + " and b.PolNo=a.PolNo";
				Amnt = "0";
				sbv=new SQLwithBindVariables();
				sbv.sql(strSQL);
				sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv.put("PolNo", tLPPolSchema.getPolNo());
				if (!"".equals(exeSQL.getOneValue(sbv))) {
					Amnt = BqNameFun.getRound(String.valueOf(exeSQL
							.getOneValue(sbv)), "0")
							+ "份";
				}
			} else {
				strSQL = "SELECT (a.Amnt-b.Amnt) as a"
						+ " FROM LPPol a,LCPol b" + " WHERE a.EdorNo='"
						+ "?EdorNo?" + "'"
						+ " and a.EdorType='" + "?EdorType?"
						+ "'" + " and a.PolNo='" + "?PolNo?"
						+ "'" + " and b.PolNo=a.PolNo";
				sbv=new SQLwithBindVariables();
				sbv.sql(strSQL);
				sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
				sbv.put("EdorType", mLPEdorItemSchema.getEdorType());
				sbv.put("PolNo", tLPPolSchema.getPolNo());
				Amnt = "0.00";
				if (exeSQL.getOneValue(sbv) != "") {
					Amnt = BqNameFun.getRound(String.valueOf(exeSQL
							.getOneValue(sbv)));
				}
			}

			// 增加保费
			strSQL = "SELECT Sum(a.GetMoney)"
					+ " FROM LJSGetEndorse a,LPDuty b" + " WHERE b.PolNo='"
					+ "?PolNo?" + "'" + " and b.EdorNo='"
					+ "?EdorNo?" + "'" + " and b.EdorType='"
					+ "?EdorType?" + "'"
					+ " and a.DutyCode= b.DutyCode" + " and a.EndorsementNo='"
					+ "?EdorNo?" + "'"
					+ " and a.FeeOperationType='"
					+ "?EdorType?" + "'"
					+ " and a.SubFeeOperationType like concat(concat('%','" + "?SubFeeOperationType?"
					+ "'),'%')";
			sbv=new SQLwithBindVariables();
			sbv.sql(strSQL);
			sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv.put("PolNo", tLPPolSchema.getPolNo());
			sbv.put("SubFeeOperationType", BqCode.Pay_Prem);
			String Prem = "0.00";
			if (exeSQL.getOneValue(sbv) != "") {
				Prem = String.valueOf(exeSQL.getOneValue(sbv));
			}
			String[] strAA = new String[7];
			strAA[0] = tLMRiskSchema.getRiskShortName(); // 险种名称
			strAA[1] =String.valueOf(tLPPolSchema.getInsuYear())+"年"; // 保险年期
			strAA[2] = Amnt; // 增加保额份数
			strAA[3] = String.valueOf(tLPPolSchema.getPayYears())+"年"; //缴费年期	
			strAA[4] =String.valueOf(tLPPolSchema.getStandPrem())+"元"; // 保险费 
			strAA[5] = BqNameFun.getRound(Prem); // 增加保费
			strAA[6] = tLPPolSchema.getCValiDate(); // 保单生效日期
//			strAA[5] = BqNameFun.getRound(JPrem); // 健康加费
//			strAA[6] = BqNameFun.getRound(ZPrem); // 职业加费
//			strAA[6] = BqNameFun.getRound(String
//					.valueOf(tLPPolSchema.getAmnt())); // 实际保额
			tAAListTable.add(strAA);
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.addOneError("查询原险种信息失败!");
				return false;
			}
			tLCPolSchema.setSchema(tLCPolDB.getSchema());
			if (!StrTool.compareString(tLPPolSchema.getPolNo(), tLPPolSchema
					.getMainPolNo())) {
				// 附加险算续期
				RNewPolCalBL tRNewPolCalBL = new RNewPolCalBL();
				if (!tRNewPolCalBL.RNPolCalBL(tLPPolSchema)) {
					mErrors.copyAllErrors(tRNewPolCalBL.mErrors);
					mErrors.addOneError("计算续期险种保费失败!");
					return false;
				}
				double aJK = 0;
				double aZY = 0;
				double aPrem = 0;
				aPrem = tRNewPolCalBL.getRNewPrem();
				if (tLPPolSchema.getAmnt() > 0 && tLCPolSchema.getAmnt() > 0) {
					aPrem = aPrem * tLPPolSchema.getAmnt()
							/ tLCPolSchema.getAmnt();
				} else {
					aPrem = aPrem * tLPPolSchema.getMult()
							/ tLCPolSchema.getMult();
				}
				aJK = tRNewPolCalBL.getJKSuperRisk(tLPPolSchema);
				aZY = tRNewPolCalBL.getZYSuperRisk(tLPPolSchema);
				double aScale = (tLPPolSchema.getAmnt() - tLCPolSchema
						.getAmnt())
						/ tLPPolSchema.getAmnt();
				RNewPrem += aPrem * (1 + (aJK / 100 + aZY / 100) * aScale);
			}

		}
		String strSQL1 = "SELECT Sum(GetMoney)" + " FROM LJSGetEndorse"
				+ " WHERE EndorsementNo='" + "?EndorsementNo?"
				+ "'" + " and FeeOperationType='"
				+ "?FeeOperationType?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL1);
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		String SumPayMoney = "0.00";
		if (exeSQL.getOneValue(sbv1) != "") {
			SumPayMoney = BqNameFun.getRound(String.valueOf(exeSQL
					.getOneValue(sbv1)));
		}

		mTextTag.add("SumPayMoney",PubFun.getChnMoney(Double.parseDouble(SumPayMoney))+"(￥"+SumPayMoney+")" );
		mTextTag.add("NextPayToDate", tLPPolSchema.getPaytoDate());//下一个缴费期
		String SumMoney = "0.00";

		String StrSQL = "select * from lcpol"
				+ " where polno <> mainpolno"
				+ " and contno = '"
				+ "?contno?"
				+ "'"
				+ " and polno not in (select polno from lppol where edortype = 'AA' and edorno = '"
				+ "?edorno?" + "')" + " and appflag = '1'"
				+ " and rnewflag <> '-2'" + "";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(StrSQL);
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		LCPolDB iLCPolDB = new LCPolDB();
		LCPolSet iLCPolSet = new LCPolSet();
		iLCPolSet = iLCPolDB.executeQuery(sbv2);
		if (iLCPolSet != null && iLCPolSet.size() > 0) {
			for (int j = 1; j <= iLCPolSet.size(); j++) {
				RNewPolCalBL tRNewPolCalBL = new RNewPolCalBL();
				if (!tRNewPolCalBL.RNPolCalBL(iLCPolSet.get(j))) {
					mErrors.copyAllErrors(tRNewPolCalBL.mErrors);
					mErrors.addOneError("计算续期险种保费失败!");
					return false;
				}
				RNewPrem += tRNewPolCalBL.getRNewPrem();
			}
		}
		String StrSQL1 = "select * from lppol where mainpolno = polno and contno = '"
				+ "?contno?"
				+ "' and edortype = 'AA' and edorno = '"
				+ "?edorno?" + "' and payintv <> 0";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(StrSQL1);
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
		String StrSQL2 = "select * from lcpol where mainpolno = polno and contno = '"
				+ "?contno?" + "' and payintv <> 0";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(StrSQL2);
		sbv4.put("contno", mLPEdorItemSchema.getContNo());
		// Add By QianLy on 2006-09-16---------
		// 取长期附加险续期保费标准
		String SSQL1 = "select *" + " from LPPol a"
				+ " where a.polno <> a.mainpolno" + " and a.appflag = '1'"
				+ " and exists(select 1" + "              from lmriskapp"
				+ "             where riskcode = a.riskcode"
				+ "               and riskperiod = 'L')"
				+ " and exists (select 'X'" + "               from lcprem"
				+ "              where polno = a.polno"
				+ "                and urgepayflag = 'Y')"
				+ " and not exists (select 'X'"
				+ "                   from lcprem"
				+ "                  where polno = a.polno"
				+ "                    and freeflag = 1)" + " and a.contno = '"
				+ "?contno?" + "'"
				+ " and a.edortype = 'AA'" + " and a.edorno = '"
				+ "?edorno?" + "'" + " and payintv <> 0"
				+ "";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(SSQL1);
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
		String SSQL2 = "select *" + " from LCPol a"
				+ " where a.polno <> a.mainpolno" + " and a.appflag = '1'"
				+ " and exists(select 1" + "              from lmriskapp"
				+ "             where riskcode = a.riskcode"
				+ "               and riskperiod = 'L')"
				+ " and exists (select 'X'" + "               from lcprem"
				+ "              where polno = a.polno"
				+ "                and urgepayflag = 'Y')"
				+ " and not exists (select 'X'"
				+ "                   from lcprem"
				+ "                  where polno = a.polno"
				+ "                    and freeflag = 1)" + " and a.contno = '"
				+ "?contno?" + "'" + " and payintv <> 0"
				+ "";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(SSQL2);
		sbv6.put("contno", mLPEdorItemSchema.getContNo());
		// --------------
		iLCPolSet.clear();
		iLCPolSet = iLCPolDB.executeQuery(sbv3);
		if (iLCPolSet == null || iLCPolSet.size() < 1) {
			iLCPolSet = iLCPolDB.executeQuery(sbv4);
		}
		if (iLCPolSet != null && iLCPolSet.size() > 0) {
			RNewPrem += iLCPolSet.get(1).getPrem();
		}
		// Add By QianLy on 2006-09-16--------
		LPPolDB iLPPolDB = new LPPolDB();
		LPPolSet iLPPolSet = new LPPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		iLPPolSet = iLPPolDB.executeQuery(sbv5);
		if (iLPPolSet != null && iLPPolSet.size() > 0) {
			RNewPrem += iLPPolSet.get(1).getPrem();
		} else if (iLPPolSet == null || iLPPolSet.size() <= 0) {
			tLCPolSet = tLCPolDB.executeQuery(sbv6);
			if (tLCPolSet != null && tLCPolSet.size() > 0) {
				RNewPrem += tLCPolSet.get(1).getPrem();
			}
		}
		// ----------

		if (RNewPrem > 0) {
			SumMoney = BqNameFun.getRound(String.valueOf(RNewPrem));
		}
		String Str325 = "select * from lcpol where riskcode = '00325000' and appflag = '1' and contno = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(Str325);
		sbv7.put("contno", mLPEdorItemSchema.getContNo());
		LCPolDB LCPolDB325 = new LCPolDB();
		LCPolSet LCPolSet325 = new LCPolSet();
		LCPolSet325 = LCPolDB325.executeQuery(sbv7);
		if (LCPolSet325 != null && LCPolSet325.size() > 0) {
			SumMoney = "0.00";
		}
		mTextTag.add("SumMoney", SumMoney);
		// 核保特约
		String strSQL = "select polno from LPPol where contno = '"
				+ "?contno?" + "' and edorno = '"
				+ "?edorno?" + "'";
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		sbv8.sql(strSQL);
		sbv8.put("contno", mLPEdorItemSchema.getContNo());
		sbv8.put("edorno", mLPEdorItemSchema.getEdorNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sbv8);
		if (tSSRS != null || tSSRS.MaxRow > 0) {
			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				tNSSListTable.setName("NSS");

				strNSS = new String[2];
				SQLwithBindVariables sqlbv0=new SQLwithBindVariables();
				sqlbv0.sql("select riskshortname from lppol a, lmrisk b where a.polno = '"+ "?polno?"+ "' and a.riskcode = b.riskcode");
				sqlbv0.put("polno", tSSRS.GetText(j, 1));
				strNSS[0] = tExeSQL
						.getOneValue(sqlbv0);
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(" select speccontent from LPSpec where 1=1 "
						+ " and SerialNo = (select max(SerialNo) from lpspec where polno = '"
						+ "?polno?"
						+ "' and edorno = '"
						+ "?edorno?" + "')");
				sqlbv1.put("edorno", tSSRS.GetText(j, 1));
				strNSS[1] = tExeSQL
						.getOneValue(sqlbv1);
				if (!"".equals(strNSS[1])) {
					tNSSListTable.add(strNSS);
					xmlexport.addDisplayControl("displayDASpec");
				}
			}
		}
		// 其它信息

	    //BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
         BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] b_strAA = new String[8];
		xmlexport.addListTable(tAAListTable, b_strAA);
		if (tNSSListTable.size() > 0) {
			String[] c_strNS = new String[2];
			xmlexport.addListTable(tNSSListTable, c_strNS);
		}
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "QlyAA");
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// public static void main(String[] args) {
	// GlobalInput mGlobalInput = new GlobalInput();
	// mGlobalInput.ManageCom = "86";
	// mGlobalInput.Operator = "001";
	// VData tVData = new VData();
	// tVData.add(mGlobalInput);
	//
	// LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
	// tLPEdorItemDB.setEdorAcceptNo("6120070226000001");
	// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	// tLPEdorItemSchema = tLPEdorItemDB.query().get(1);
	// tVData.add(tLPEdorItemSchema);
	//
	// XmlExport tXmlExport = new XmlExport();
	// try {
	// tXmlExport.createDocument(FormulaOneVTS, "printer"); //初始化 XML 文档
	// }
	// catch (Exception ex) {
	// logger.debug(
	// "\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne VTS 文件异常！");
	// }
	// tVData.add(tXmlExport);
	//
	// PEdorAAPrintBL tPEdorAAPrintBL = new PEdorAAPrintBL();
	// if (tPEdorAAPrintBL.submitData(tVData, "PRINTAA")) {
	// logger.debug("OK了 了 了了了 了了了了 了了了");
	// }
	// }

}
