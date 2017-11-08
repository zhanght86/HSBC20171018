package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContTempInfoDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContTempInfoSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPContTempInfoSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:被保人基本信息变更批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成保全项目被保险人重要资料变更信息变更的批单数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst 
 * 
 * @version 1.0
 */

public class PEdorICPrintBL implements EdorPrint {
@SuppressWarnings("unused")
private static Logger logger = Logger.getLogger(PEdorICPrintBL.class);

	// 公共数据
	private VData mResult = new VData();
	//public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorAppSchema mLPEdorAppSchema =new LPEdorAppSchema();
	private LPContTempInfoSchema mLCContTempInfoSchema = new LPContTempInfoSchema(); 
	private XmlExportNew xmlexport = new XmlExportNew();
	private TextTag mTextTag = new TextTag();
	private	ListTable tlistTable = new ListTable();
	
	private	ListTable tPlistTable = new ListTable();
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorICPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "IC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "IC数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "IC数据生成失败!");
			return false;
		}
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
		mLJAGetSet = (LJAGetSet)mInputData
		.getObjectByObjectName("LJAGetSet", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTIC")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		// LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (mLPEdorItemSet.size() < 1) {
			return false;
		}
		mLPEdorItemSchema.setSchema(mLPEdorItemSet.get(1));		
		LPContTempInfoDB tLPContTempInfoDB = new LPContTempInfoDB();
	    LPContTempInfoSet tLPContTempInfoSet = new LPContTempInfoSet();
		tLPContTempInfoDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContTempInfoDB.setState("0");
		tLPContTempInfoDB.setEdorType("CM");
		tLPContTempInfoDB.setEdorNo(mLPEdorItemSchema.getStandbyFlag1());
		tLPContTempInfoSet = tLPContTempInfoDB.query();
		if (tLPContTempInfoSet.size() < 1) {
			return false;
		}
		mLCContTempInfoSchema.setSchema(tLPContTempInfoSet.get(1));
		
		// 保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "打印生成数据时，取保单信息失败!");
			return false;
		}
		
		return true;
	}

	private boolean dealData() {
		//xmlexport.addDisplayControl("displayHead1");
BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		


       BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名

		

		LPPersonDB tLPPersonDB = new LPPersonDB();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonDB.setEdorNo(mLPEdorItemSchema.getStandbyFlag1());
		tLPPersonDB.setEdorType("CM");
		tLPPersonDB.setCustomerNo(mLPEdorItemSchema.getStandbyFlag2());
		if (!tLPPersonDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取客户新信息失败!");
			return false;
		}
		tLPPersonSchema = tLPPersonDB.getSchema();

		LDPersonDB tLDPersonDB = new LDPersonDB();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tLDPersonDB.setCustomerNo(mLPEdorItemSchema.getStandbyFlag2());
		if (!tLDPersonDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取客户原信息失败!");
			return false;
		}
		tLDPersonSchema = tLDPersonDB.getSchema();
		mTextTag.add("BBLPInsured.CustomerNo", tLDPersonSchema.getCustomerNo());

		mTextTag.add("BBLPInsured.Name", tLPPersonSchema.getName());

		
		
		LPContDB tLPContDB = new LPContDB();
		LPContSchema tLPContSchema = new LPContSchema();	
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();		
		
		if (!getAllPolSchema(tLPContSchema)) {
			return false;
		}
       ExeSQL exeSQL = new ExeSQL();
	    //自垫本金加利息
	    String strSQL = "SELECT abs(Sum(GetMoney))"
	        + " FROM LJSGetEndorse"
	        + " where EndorsementNo='?EndorsementNo?'"
	        + " and FeeOperationType='?FeeOperationType?'"
	        + " and ( SubFeeOperationType like concat(concat('%','?AutoPayPrem?'),'%') or SubFeeOperationType like concat(concat('%','?AutoPayPremInterest?'),'%'))" ;
	    SQLwithBindVariables sbv1=new SQLwithBindVariables();
	    sbv1.sql(strSQL);
	    sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	    sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
	    sbv1.put("AutoPayPrem", BqCode.Pay_AutoPayPrem);
	    sbv1.put("AutoPayPremInterest", BqCode.Pay_AutoPayPremInterest);
	    String tSumZDLoan = exeSQL.getOneValue (sbv1) ;
	    if (!"".equals(tSumZDLoan))
	    {
	       xmlexport.addDisplayControl ("displayICZD") ;
	      mTextTag.add ("SumLoanZD",PubFun.getChnMoney(Double.parseDouble(tSumZDLoan))+"(￥"+tSumZDLoan+")") ;
	    }

	    
	    //未清偿贷款本金加利息
	    strSQL = "SELECT abs(Sum(GetMoney))"
	        + " FROM LJSGetEndorse"
	        + " where EndorsementNo='?EndorsementNo?'"
	        + " and FeeOperationType='?FeeOperationType?'"
	        + " and (SubFeeOperationType like concat(concat('%','?LoanCorpus?'),'%') or SubFeeOperationType like concat(concat('%','?LoanCorpusInterest?'),'%'))" ;
	    sbv1=new SQLwithBindVariables();
	    sbv1.sql(strSQL);
	    sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	    sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
	    sbv1.put("LoanCorpus", BqCode.Pay_LoanCorpus);
	    sbv1.put("LoanCorpusInterest", BqCode.Pay_LoanCorpusInterest);
	    String SumLoan = exeSQL.getOneValue (sbv1);
	    if (!"".equals(SumLoan))
	    {      
	      xmlexport.addDisplayControl ("displayICLoan") ;	
	      mTextTag.add ("SumLoan",PubFun.getChnMoney(Double.parseDouble(SumLoan))+"(￥"+SumLoan+")") ;
	    }
	    
	    String tGetMoney=BqNameFun.getSumGetMoney(mLPEdorItemSchema, mLPEdorItemSchema.getContNo());
        if(!"".equals(tGetMoney))
        {
  	      xmlexport.addDisplayControl ("displayICGet") ;	
	      mTextTag.add ("GetMoney",PubFun.getChnMoney(Double.parseDouble(tGetMoney))+"(￥"+tGetMoney+")") ;
        }
	    
		//处理项目收付费信息
//		BqNameFun.AddEdorPayGetInfo( mLPEdorItemSchema,
//				mLPEdorAppSchema, xmlexport,
//				 mTextTag);

	    BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema, xmlexport, mTextTag);
	    //BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
         BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);

		return true;
	}


	private boolean getAllPolSchema(LPContSchema tLPContSchema) {

		LCPolDB tLCPolDB = new LCPolDB();

		// 查询客户作为主被保人的险种,以及有效的保单
		String sql = " select * from lcpol where contno = '?contno?' "
				//cvalidate可能排除了新增附加险的预约生效的polno 此处的逻辑需要讨论
				+ " and appflag in ('1') and cvalidate <= '"
				//enddate排除的趸交的附加险
				+ "?date?" + "' and enddate > '"
				//order by 确保豁免险最后处理
				+ "?date?" + "' order by (case when exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and risktype7 in ('1','2')) then 1 else 0 end) ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("date", mLPEdorItemSchema.getEdorValiDate());
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if(tLCPolSet==null || tLCPolSet.size()< 1)
		{
			mErrors.addOneError("打印生成数据时，取险种信息失败!");
			return false;
		}
		String[] strArr = null;
		xmlexport.addDisplayControl("displayICCont");
		tlistTable.setName("CMC1");
		for(int k=1;k<=tLCPolSet.size();k++)
		{
			LCPolSchema tLCPolSchema = new LCPolSchema();	
			tLCPolSchema=tLCPolSet.get(k);
			strArr = new String[4];
			strArr[0] = BqNameFun.getRiskShortName(tLCPolSchema.getRiskCode());
			if(tLCPolSchema.getPayIntv()==0)
			{
				strArr[1] = "趸缴";
			}else
			{
				strArr[1] = tLCPolSchema.getPayYears()+"年";
			}

			if(tLCPolSchema.getInsuYear()>100)
			{
				strArr[2] = "终身";				
			}else
			{
				String YearFlag="";
				if("A".equals(tLCPolSchema.getInsuYearFlag()))
				{
					YearFlag="岁";
				}
				if("Y".equals(tLCPolSchema.getInsuYearFlag()))
				{
					YearFlag="年";
				}
				if("M".equals(tLCPolSchema.getInsuYearFlag()))
				{
					YearFlag="月";
				}
				if("D".equals(tLCPolSchema.getInsuYearFlag()))
				{
					YearFlag="天";
				}
			    strArr[2] = tLCPolSchema.getInsuYear() + YearFlag;
			}
			strArr[3] = tLCPolSchema.getPrem()+"元";
			tlistTable.add(strArr);
		}
		
		LPPolDB tLPPolDB = new LPPolDB();

		LPPolSet tLPPolSet= new LPPolSet();	
		tLPPolDB.setContNo(tLPContSchema.getContNo());
		tLPPolDB.setEdorNo(tLPContSchema.getEdorNo());
		tLPPolDB.setEdorType(tLPContSchema.getEdorType());
		tLPPolDB.setAppFlag("1");
		tLPPolSet=tLPPolDB.query();
		if(tLPPolSet==null || tLPPolSet.size()< 1)
		{
			mErrors.addOneError("打印生成数据时，取险种信息失败!");
			return false;
		}
		tPlistTable.setName("CMC2");
		for(int k=1;k<=tLPPolSet.size();k++)
		{
			LPPolSchema tLPPolSchema = new LPPolSchema();	
			tLPPolSchema=tLPPolSet.get(k);
			strArr = new String[4];
			strArr[0] = BqNameFun.getRiskShortName(tLPPolSchema.getRiskCode());
			if(tLPPolSchema.getPayIntv()==0)
			{
				strArr[1] = "趸缴";
			}else
			{
			strArr[1] = tLPPolSchema.getPayYears()+"年";
			}
			if(tLPPolSchema.getInsuYear()>100)
			{
				strArr[2] = "终身";				
			}else
			{
				String YearFlag="";
				if("A".equals(tLPPolSchema.getInsuYearFlag()))
				{
					YearFlag="岁";
				}
				if("Y".equals(tLPPolSchema.getInsuYearFlag()))
				{
					YearFlag="年";
				}
				if("M".equals(tLPPolSchema.getInsuYearFlag()))
				{
					YearFlag="月";
				}
				if("D".equals(tLPPolSchema.getInsuYearFlag()))
				{
					YearFlag="天";
				}
			    strArr[2] = tLPPolSchema.getInsuYear() + YearFlag;
			}
			strArr[3] = tLPPolSchema.getPrem()+"元";
			tPlistTable.add(strArr);
		}
		
		xmlexport.addListTable(tPlistTable, strArr);
		xmlexport.addListTable(tlistTable, strArr);
		
		//String tSQL="select *　from lj"
		//财务信息如何处理？
		String tTemp=BqNameFun.NVL(BqNameFun.getSumPayMoney(mLPEdorItemSchema, tLPContSchema.getContNo()), "0.00");
		mTextTag.add("SumAmount",PubFun.getChnMoney(Double.parseDouble(tTemp))+"(￥"+tTemp+")" );//累计补费
		String tData[]=BqNameFun.getContNextPrem(mLPEdorItemSchema, mLPEdorItemSchema.getEdorAppDate());
		mTextTag.add("NextPayToDate", BqNameFun.getChDate(tData[0]));//下一个缴费期
	    xmlexport.addDisplayControl ("displayICPrem") ;	
		mTextTag.add("NextPayMoney", PubFun.getChnMoney(Double.parseDouble(tData[1]))+"(￥"+tData[1]+")");// 下期应缴保费（应该去算）
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
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
