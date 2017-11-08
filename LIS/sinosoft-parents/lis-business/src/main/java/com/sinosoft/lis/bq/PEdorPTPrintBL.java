package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class PEdorPTPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(PEdorPTPrintBL.class);

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
	private ListTable tPTListTable = new ListTable();
	private ListTable tPTListTable2 = new ListTable();
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	// public static final String FormulaOneVTS = "PrtEndorsement.vts";
	public PEdorPTPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("PT数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("PT数据打印失败!");
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
		
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName ("LPEdorAppSchema",0);
		
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		mLJAGetSet = (LJAGetSet)mInputData
		.getObjectByObjectName("LJAGetSet", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTPT")) {
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
		//xmlexport.addDisplayControl("displayHead1");
         BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayPT");
		
	    BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名

		tPTListTable.setName("PT");
		String[] strPT = null;

		String tRiskInfoSQl = "select concat(concat((select riskname from lmriskapp where riskcode=a.riskcode),'-'),a.riskcode)," +
				"(case when exists(select 1 from lmduty "
				+ "where calmode='O' and dutycode =(select dutycode from lmriskduty where riskcode=a.riskcode)) then b.mult else b.amnt end)," +
						"(case when exists(select 1 from lmduty "
				+ "where calmode='O' and dutycode =(select dutycode from lmriskduty where riskcode=a.riskcode)) then a.mult else a.amnt end),"
				+ "(select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?contno?' and polno = a.polno) from lppol a,lcpol b where a.polno = b.polno and b.appflag='1' and b.contno = '?contno?' and edorno = '?EndorsementNo?' "
				+ "and edortype = '?FeeOperationType?' and a.contno = '?contno?'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tRiskInfoSQl);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		logger.debug("tRiskInfoSQl"+tRiskInfoSQl);
		SSRS tBJSSRS = tExeSQL.execSQL(sqlbv);
		for (int i=1;i<=tBJSSRS.getMaxRow();i++) {
			strPT = new String[tBJSSRS.getMaxCol()];
			for (int j=1;j<=tBJSSRS.getMaxCol();j++) {
				strPT[j-1] = tBJSSRS.GetText(i, j);
			}
			tPTListTable.add(strPT);
		}
		strPT = new String[tBJSSRS.getMaxCol()];
		xmlexport.addListTable(tPTListTable, strPT);
		
		xmlexport.addDisplayControl("displayPTAdd");
		tPTListTable2.setName("PTT");
		tRiskInfoSQl = "select concat(concat((select riskname from lmriskapp where riskcode=a.riskcode),'--'),a.riskcode),"
				+ "(case when exists(select 1 from lmduty "
				+ "where calmode='O' and dutycode =(select dutycode from lmriskduty where riskcode=a.riskcode)) then a.mult else a.amnt end),"
				+ "'正常缴费' from lppol a where edorno = '?edorno?' "
				+ "and edortype = '?edortype?' and contno = '?contno?'"
				//此处的union是统计未做减少保额的保单险种
				+ "union select concat(concat((select riskname from lmriskapp where riskcode=a.riskcode),'--'),a.riskcode),(case when exists(select 1 from lmduty "
				+ "where calmode='O' and dutycode =(select dutycode from lmriskduty where riskcode=a.riskcode)) then a.mult else a.amnt end),'正常缴费' from lcpol a "
				+ "where contno = '?contno?' and appflag = '1' and polno not in (select polno from lppol where edorno = '?edorno?' "
				+ "and edortype = '?edortype?' and contno = '?contno?')";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tRiskInfoSQl);
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		tBJSSRS = tExeSQL.execSQL(sqlbv);
		logger.debug("tRiskInfoSQl"+tRiskInfoSQl);
		for (int i = 1; i <= tBJSSRS.getMaxRow(); i++) {
			strPT = new String[tBJSSRS.getMaxCol()];
			for (int j = 1; j <= tBJSSRS.getMaxCol(); j++) {
				strPT[j - 1] = tBJSSRS.GetText(i, j);
			}
			tPTListTable2.add(strPT);
		}
		strPT = new String[tBJSSRS.getMaxCol()];
		xmlexport.addListTable(tPTListTable2, strPT);
		
		tRiskInfoSQl = "select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' ";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tRiskInfoSQl);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		double tAllGetMoney = Double.parseDouble(tExeSQL.getOneValue(sqlbv));
		mTextTag.add("AllGetMoney", PubFun.getChnMoney(tAllGetMoney)+"(￥"+tAllGetMoney+")");
		
		tRiskInfoSQl = "select paytodate,sum(prem) from (select paytodate,prem from lppol a where payintv >0  and edorno = '?edorno?' " + "and edortype = '?edortype?' and contno = '?contno?' "
				+ "union all select paytodate,prem from lcpol where payintv > 0 and contno='?contno?' and appflag='1' and polno not in (select polno from lppol where edorno = '?edorno?' " + "and edortype = '?edortype?' and contno = '?contno?') "
				+") g group by paytodate";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tRiskInfoSQl);
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		//tBJSSRS = tExeSQL.execSQL(tRiskInfoSQl);
		//String tNextPayPremSting = tBJSSRS.GetText(1, 2);
		//double tNextPayPrem = 0.00;
		//if (!tNextPayPremSting.equals("")) 
		//	tNextPayPrem = Double.parseDouble(tNextPayPremSting);
		String tData[]=BqNameFun.getContNextPrem(mLPEdorItemSchema, mLPEdorItemSchema.getEdorAppDate());
		logger.debug("tNextPayPrem"+tData[1]);
		//有可能是趸交的减少保额
//		if (tNextPayPrem > 0) {
//
//		}
		mTextTag.add("PayToDate", BqNameFun.getChDate(tData[0]));
		mTextTag.add("NextPayPrem", PubFun.getChnMoney(Double.parseDouble(tData[1])) + "(￥" + tData[1] + ")");
		
		xmlexport.addDisplayControl("displayPTNextPay");

	    //BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
	     BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema, xmlexport, mTextTag);
         BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
	    
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
	
	 @SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		VData tVData = new VData();
		tVData.add(mGlobalInput);

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo("6120070226000014");
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);
		tVData.add(tLPEdorItemSchema);
	}
}
