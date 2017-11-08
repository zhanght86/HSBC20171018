package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 增补告知
 * </p>
 * 
 * <p>
 * Description: 生成批单数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author pst
 * @version 1.0
 */

public class PEdorHIPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(PEdorHIPrintBL.class);

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
	private ListTable tHIListTable = new ListTable();
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorHIPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("HI数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("HI数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
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
		if (!mOperate.equals("PRINTHI")) {
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
		xmlexport.addDisplayControl("displayHI");



		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		

        BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名
        //0 是投保人，1是被保人
        if("0".equals(mLPEdorItemSchema.getStandbyFlag1()))
        {
    		mTextTag.add("InsuredName", tLCContSchema.getAppntName());
        }else
        {
	    	mTextTag.add("InsuredName", tLCContSchema.getInsuredName());
        }
		
		tHIListTable.setName("HI");
		LPCustomerImpartSet allLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartDB  allLPCustomerImpartDB = new LPCustomerImpartDB();
		allLPCustomerImpartDB.setContNo(mLPEdorItemSchema.getContNo());
//		allLPCustomerImpartDB.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
		allLPCustomerImpartDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		allLPCustomerImpartDB.setEdorType(mLPEdorItemSchema.getEdorType());
		allLPCustomerImpartSet=allLPCustomerImpartDB.query();
		if(allLPCustomerImpartSet.size()>0 )
		{
			for(int k=1;k<=allLPCustomerImpartSet.size();k++)
			{
				LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tLPCustomerImpartSchema =allLPCustomerImpartSet.get(k);		
				String[] strNSS = new String[3];
				strNSS[0]=BqNameFun.getCodeName(tLPCustomerImpartSchema.getImpartVer(),"ImpartVer");
				strNSS[1]=tLPCustomerImpartSchema.getImpartCode();
				strNSS[2]=" 询问事项："+tLPCustomerImpartSchema.getImpartContent()+" 告知内容："+tLPCustomerImpartSchema.getImpartParamModle();	
				tHIListTable.add(strNSS);
			}
		}
		String tData[]=BqNameFun.getContNextPrem(mLPEdorItemSchema, mLPEdorItemSchema.getEdorAppDate());
		mTextTag.add("PayToDate", BqNameFun.getChDate(tData[0]));//下一个缴费期
		mTextTag.add("SumPrem",PubFun.getChnMoney(Double.parseDouble(tData[1]))+"(￥"+tData[1]+")");//下期保费
		
		ExeSQL tExeSQL = new ExeSQL();
		String strSQL1 = "SELECT (case when Sum(GetMoney) is not null then Sum(GetMoney) else 0 end)" + " FROM LJSGetEndorse"
				+ " WHERE EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL1);
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		String tSumGetMoney = tExeSQL.getOneValue(sbv1);		
		mTextTag.add("GetMoney", PubFun.getChnMoney(Double
				.parseDouble(tSumGetMoney))
				+ "(￥" + tSumGetMoney + ")");//本次累计
		//处理项目收付费信息
		
//		BqNameFun.AddEdorPayGetInfo( mLPEdorItemSchema,
//				mLPEdorAppSchema, xmlexport,
//				 mTextTag);
		
		/**投被保人标记 AppntIsInsuredFlag 投保人还是被保人0,投保人，1，被保人，2 两者兼之,但是注意，只针对某一保单*/
		String tAppntIsInsuredFlag="";
		String sql = " select 1 from lcAppnt where contno = '?contno?' and AppntNo ='?AppntNo?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sql);
		sbv2.put("contno", tLCContSchema.getContNo());
		sbv2.put("AppntNo", mLPEdorItemSchema.getInsuredNo());
		String tFlag=tExeSQL.getOneValue(sbv2);
        if("1".equals(tFlag))
		{
			tAppntIsInsuredFlag="0";
		}
		       sql=" select 1 from lcinsured where contno = '?contno?' and insuredno ='?insuredno?'";
				sbv2=new SQLwithBindVariables();
		       sbv2.sql(sql);
				sbv2.put("contno", tLCContSchema.getContNo());
				sbv2.put("insuredno", mLPEdorItemSchema.getInsuredNo());
		       tFlag=tExeSQL.getOneValue(sbv2);
        if("1".equals(tFlag))
		{
			tAppntIsInsuredFlag="1";
		}
		     
		 sql=" select 1 from lcinsured where contno = '?contno?' and insuredno = (select appntno from lccont where contno = '?contno?')";			
		sbv2=new SQLwithBindVariables();	
		 sbv2.sql(sql);
			sbv2.put("contno", tLCContSchema.getContNo());
		 tFlag=tExeSQL.getOneValue(sbv2);
        if("1".equals(tFlag))
		{
			tAppntIsInsuredFlag="2";
		}

		//取出虽有的保单
		String tSumConNo="";
		LCContDB rLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		if("0".equals(tAppntIsInsuredFlag))
		{
			rLCContDB.setAppntNo(mLPEdorItemSchema.getInsuredNo());			
		}else
		{
			rLCContDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());			
		}

		rLCContDB.setAppFlag("1");
		tLCContSet=rLCContDB.query();
		if(tLCContSet.size()>0)
		{
			for(int k=1;k<=tLCContSet.size();k++)
			{
				if(!mLPEdorItemSchema.getContNo().equals(tLCContSet.get(k).getContNo()))
				{
					tSumConNo+=tLCContSet.get(k).getContNo()+",";						
				}
			
			}
		}else
		{
			mErrors.addOneError("获取客户保单信息失败！");
			return false;
		}

		if(!"".equals(tSumConNo))
		{
			tSumConNo=tSumConNo.substring(0, tSumConNo.lastIndexOf(","));
			xmlexport.addDisplayControl("displayHITail");
			mTextTag.add("SumContNo", tSumConNo);
		}
		
	    BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema, xmlexport, mTextTag);
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
		xmlexport.addTextTag(mTextTag);
		if (tHIListTable.size() > 0) {
			String[] c_strNS = new String[3];
			xmlexport.addListTable(tHIListTable, c_strNS);
		}
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
