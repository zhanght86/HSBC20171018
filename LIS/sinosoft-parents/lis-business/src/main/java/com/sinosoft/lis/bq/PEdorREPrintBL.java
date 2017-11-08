package com.sinosoft.lis.bq;

import java.util.ArrayList;
import java.util.List;

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
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class PEdorREPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorREPrintBL.class);

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

	private ListTable tListTable = new ListTable();

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorREPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("RE数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("RE数据打印失败!");
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

		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		mLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName("LJAGetSet",
				0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTRE")) {
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
		xmlexport.addDisplayControl("displayRE");

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		tListTable.setName("RE");
		ExeSQL tExeSQL = new ExeSQL();
		String[] strRE = null;

		String tRiskInfoSQl = "";
		// 判断是否是主险长趸交失效件，此种情况下没有lppol记录
		String check_sql = "select 1 from lcpol a where a.contno='?contno?' and polno=mainpolno and payintv=0 and not exists (select 1  from lmrisk "
				+ " where riskcode = a.riskcode and rnewflag = 'Y')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(check_sql);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		if ("1".equals(tExeSQL.getOneValue(sqlbv))) {
			tRiskInfoSQl = "	select concat(concat((select riskname from lmriskapp where riskcode = a.riskcode) , '--'), "
					+ " a.riskcode),0,0 from lcpol a, lccontstate b where a.contno = b.contno "
					+ " and a.contno = '?contno?' and statetype in ('Available') "
					+ " and state = '1' and b.EndDate is null order by a.polno ";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tRiskInfoSQl);
			sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		} else {
			tRiskInfoSQl = "select concat(concat((select riskname from lmriskapp where riskcode=a.riskcode),'--'),a.riskcode),(select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse c where EndorsementNo = '?edorno?' and FeeOperationType = '?edortype?' and ContNo = '?ContNo?' and SubFeeOperationType like concat('?SubFeeOperationType1?','%') and c.polno=a.polno ),(select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse b where EndorsementNo = '?edorno?' and FeeOperationType = '?edortype?' and ContNo = '?ContNo?' and SubFeeOperationType like concat('?SubFeeOperationType2?','%') and b.polno=a.polno) from lppol a where edorno = '?edorno?' "
					+ "and edortype = '?edortype?' and contno = '?ContNo?'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tRiskInfoSQl);
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv.put("SubFeeOperationType1", BqCode.Pay_Prem);
			sqlbv.put("SubFeeOperationType2", BqCode.Pay_PremInterest_Re);
			sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
			sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		}

		SSRS tBJSSRS = tExeSQL.execSQL(sqlbv);
		for (int i = 1; i <= tBJSSRS.getMaxRow(); i++) {
			strRE = new String[tBJSSRS.getMaxCol()];
			for (int j = 1; j <= tBJSSRS.getMaxCol(); j++) {
				strRE[j - 1] = tBJSSRS.GetText(i, j);
			}
			tListTable.add(strRE);
		}
		strRE = new String[tBJSSRS.getMaxCol()];
		xmlexport.addListTable(tListTable, strRE);


		tRiskInfoSQl = "select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' and SubFeeOperationType in (?SubFeeOperationType?) ";
		/*ArrayList<String> strArr=new ArrayList<String>();
		tRiskInfoSQl = "select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' and SubFeeOperationType in (?SubFeeOperationType?) ";
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(BqCode.Pay_AutoPayPrem);
		strArr.add(BqCode.Pay_AutoPayPremInterest);*/
		List<String> list = new ArrayList<String>();
        list.add("BqCode.Pay_AutoPayPrem");
        list.add("BqCode.Pay_AutoPayPremInterest");
		String str ="";
		for(int i=0;i<list.size();i++){
			logger.debug("---"+i+"---"+list.size());
			if(i==0)
				str=list.get(i)+",";
				else if(i+1==list.size()){
				   str=str+list.get(i);	
				}else{
				 str=str+list.get(i)+",";}	
				}
				logger.debug("---"+str);
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tRiskInfoSQl);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv.put("SubFeeOperationType", str);
		double tAutoPremInterest = Double.parseDouble(tExeSQL
				.getOneValue(sqlbv));
		if (tAutoPremInterest > 0) {
			xmlexport.addDisplayControl("displayREPrem");
			mTextTag.add("AutoPremInterest", PubFun
					.getChnMoney(tAutoPremInterest)
					+ "(￥" + tAutoPremInterest + ")");
		}

		tRiskInfoSQl = "select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' and SubFeeOperationType in (?SubFeeOperationType?) ";
		ArrayList<String> strArr1=new ArrayList<String>();
		strArr1.add(BqCode.Pay_LoanCorpus);
		strArr1.add(BqCode.Pay_LoanCorpusInterest);
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tRiskInfoSQl);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv.put("SubFeeOperationType", strArr1);
		double tLoanInterest = Double.parseDouble(tExeSQL
				.getOneValue(sqlbv));
		if (tLoanInterest > 0) {
			xmlexport.addDisplayControl("displayRELoan");
			mTextTag.add("LoanInterest", PubFun.getChnMoney(tLoanInterest)
					+ "(￥" + tLoanInterest + ")");
		}

		tRiskInfoSQl = "select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' ";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tRiskInfoSQl);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		double tAllPayMoney = Double.parseDouble(tExeSQL
				.getOneValue(sqlbv));

		xmlexport.addDisplayControl("displayREAdd");
		mTextTag.add("AllPayMoney", PubFun.getChnMoney(tAllPayMoney) + "(￥"
				+ tAllPayMoney + ")");

		// tRiskInfoSQl = "select paytodate,sum(prem) from lppol a where edorno
		// = '" + mLPEdorItemSchema.getEdorNo()
		// + "' " + "and edortype = '" + mLPEdorItemSchema.getEdorType() + "'
		// and contno = '"
		// + mLPEdorItemSchema.getContNo() + "' group by paytodate";
		// modify by jiaqiangli 2009-03-25 有可能是借款失效，取lcpol 此处还需要后续修改
		// 豁免出险的保费更准确取lcprem
		// tRiskInfoSQl = "select paytodate,sum(prem) from lcpol a where appflag
		// = '1' and payintv > 0 and contno = '"
		// + mLPEdorItemSchema.getContNo() + "' group by paytodate";

		// tBJSSRS = tExeSQL.execSQL(tRiskInfoSQl);
		// double tNextPayPrem = Double.parseDouble(tBJSSRS.GetText(1, 2));
		String tData[] = BqNameFun.getContNextPrem(mLPEdorItemSchema,
				mLPEdorItemSchema.getEdorAppDate());
		logger.debug("tNextPayPrem" + tData[1]);
		mTextTag.add("PayToDate", BqNameFun.getChDate(tData[0]));
		mTextTag.add("NextPayPrem", PubFun.getChnMoney(Double
				.parseDouble(tData[1]))
				+ "(￥" + tData[1] + ")");

		// BqNameFun.AddEdorPayGetInfo( mLPEdorItemSchema,mLPEdorAppSchema,
		// xmlexport,mTextTag);

		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);

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
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
