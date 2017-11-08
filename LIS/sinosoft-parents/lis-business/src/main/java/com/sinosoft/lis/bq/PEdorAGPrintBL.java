package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 保单年金（满期金）给付
 * </p>
 * 
 * <p>
 * Description: 数据生成
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

public class PEdorAGPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorAGPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable tAGListTable = new ListTable();

	// public static final String FormulaOneVTS = "PrtEndorsement.vts";

	public PEdorAGPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AG数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AG数据打印失败!");
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
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAG")) {
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

	private boolean dealData() {
		// xmlexport.addDisplayControl("displayHead1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayAG");

		ExeSQL exeSQL = new ExeSQL();
		CodeNameQuery tCodeNameQuery = new CodeNameQuery();

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		tAGListTable.setName("AG");
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet == null || tLPGetSet.size() == 0) {
			mErrors.addOneError("打印生成数据时，取给付信息失败!");
			return false;
		}
		String tGetStartDate = "";
		LPGetSchema tLPGetSchema;
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			tLPGetSchema = new LPGetSchema();
			tLPGetSchema.setSchema(tLPGetSet.get(i));
			// tGetStarDate = tLPGetSchema.getGetStartDate();
			// LMDutyGetAlive
			String strSQL = "SELECT GetDate" + " FROM LJSGetDraw"
					+ " WHERE PolNo='" + "?PolNo?" + "'"
					+ " and DutyCode='" + "?DutyCode?" + "'"
					+ " and GetDutyCode='" + "?GetDutyCode?"
					+ "'" + " and GetDutyKind='"
					+ "?GetDutyKind?" + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("PolNo", tLPGetSchema.getPolNo());
			sqlbv.put("DutyCode", tLPGetSchema.getDutyCode());
			sqlbv.put("GetDutyCode", tLPGetSchema.getGetDutyCode());
			sqlbv.put("GetDutyKind", tLPGetSchema.getGetDutyKind());
			if (!"".equals(exeSQL.getOneValue(sqlbv))) {
				tGetStartDate = exeSQL.getOneValue(sqlbv);
			}
			tGetStartDate = BqNameFun.getFDate(tGetStartDate);
			strSQL = "SELECT GetDutyName" + " FROM LMDutyGetAlive"
					+ " WHERE GetDutyCode='" + "?GetDutyCode?"
					+ "'" + " and GetDutyKind='"
					+ "?GetDutyKind?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("GetDutyCode", tLPGetSchema.getGetDutyCode());
			sqlbv.put("GetDutyKind", tLPGetSchema.getGetDutyKind());
			String GetName = "";
			if (!"".equals(exeSQL.getOneValue(sqlbv))) {
				GetName = exeSQL.getOneValue(sqlbv);
			}
			strSQL = "SELECT Sum(GetMoney)" + " FROM LJSGetDraw"
					+ " WHERE PolNo='" + "?PolNo?" + "'"
					+ " and DutyCode='" + "?DutyCode?" + "'"
					+ " and GetDutyCode='" + "?GetDutyCode?"
					+ "'" + " and GetDutyKind='"
					+ "?GetDutyKind?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("PolNo", tLPGetSchema.getPolNo());
			sqlbv.put("DutyCode", tLPGetSchema.getDutyCode());
			sqlbv.put("GetDutyCode", tLPGetSchema.getGetDutyCode());
			sqlbv.put("GetDutyKind", tLPGetSchema.getGetDutyKind());
			String GetActuGet = "0.00";
			if (!"".equals(exeSQL.getOneValue(sqlbv))) {
				GetActuGet = exeSQL.getOneValue(sqlbv);
			}
			String[] strAG = new String[3];
			strAG[0] = GetName;
			strAG[1] = tGetStartDate;
			strAG[2] = BqNameFun.getRound(GetActuGet);
			tAGListTable.add(strAG);

			String strBonusSQL = "";
			strBonusSQL = "select getdate, getmoney" + " from ljabonusget"
					+ " where getnoticeno in (select otherno"
					+ " from ljsgetendorse j" + " where j.endorsementno = '"
					+ "?endorsementno?" + "' and othernotype = '5')"
					+ "";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(strBonusSQL);
			sbv.put("endorsementno", tLPGetSchema.getEdorNo());
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			String strLine[] = null;
			tssrs = texesql.execSQL(sbv);
			if (tssrs == null || tssrs.getMaxRow() <= 0) {
			} else {
				for (int j = 1; j <= tssrs.getMaxRow(); j++) {
					strLine = new String[3];
					strLine[0] = "红利领取";
					strLine[1] = BqNameFun.getFDate(tssrs.GetText(j, 1));
					strLine[2] = BqNameFun.getRound(tssrs.GetText(j, 2));// 保留小数点后两位
					tAGListTable.add(strLine);
				}
			}
			// return true;
		}
		// mTextTag.add("LCGet.GetStartDate",tGetStartDate);//申请领取日期
		// 续期领取方式
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPPolDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取续期领取方式失败!");
			return false;
		}
		tLPPolSchema = tLPPolDB.getSchema();
		String aCode = "";
		if (tLPPolSchema.getGetForm() != null) {
			xmlexport.addDisplayControl("displayAG1");
			aCode = tLPPolSchema.getGetForm();
			mTextTag.add("LCPol.GetForm", tCodeNameQuery.getName("getlocation",
					aCode));// 续期领取方式
		}

		if ("0".equals(aCode)) {
			xmlexport.addDisplayControl("displayBank");
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("Select BankName from LDBank where BankCode = '"
					+ "?BankCode?"
					+ "'");
			sbv.put("BankCode", tLPPolSchema.getGetBankCode());
			mTextTag
					.add(
							"LCPol.GetBankCode",
							exeSQL
									.getOneValue(sbv));// 开户行
			mTextTag.add("LCPol.GetBankAccNo", tLPPolSchema.getGetBankAccNo());// 帐户
			mTextTag.add("LCPol.GetAccName", tLPPolSchema.getGetAccName());// 户名
		}
		// 其它信息
		xmlexport.addDisplayControl("displayAG2");
		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}

		String[] b_strAG = new String[3];
		xmlexport.addListTable(tAGListTable, b_strAG);
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "QlyAG");
		mResult.add(xmlexport);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// public static void main(String[] args){
	// GlobalInput mGlobalInput=new GlobalInput();
	// mGlobalInput.ManageCom="86";
	// mGlobalInput.Operator="001";
	// VData tVData=new VData();
	// tVData.add(mGlobalInput);
	//
	// LPEdorItemDB tLPEdorItemDB=new LPEdorItemDB();
	// tLPEdorItemDB.setEdorAcceptNo("6120061107000006");
	// LPEdorItemSchema tLPEdorItemSchema=new LPEdorItemSchema();
	// tLPEdorItemSchema = tLPEdorItemDB.query().get(1);
	// tVData.add(tLPEdorItemSchema);
	//
	// XmlExport tXmlExport=new XmlExport();
	// try
	// {
	// tXmlExport.createDocument(FormulaOneVTS, "printer"); //初始化 XML 文档
	// }
	// catch (Exception ex)
	// {
	// logger.debug("\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne
	// VTS 文件异常！");
	// }
	// tVData.add(tXmlExport);
	//
	// PEdorAGPrintBL tPEdorItemPrintBL = new PEdorAGPrintBL();
	// if(tPEdorItemPrintBL.submitData(tVData,"PRINTAG")){
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }

}
