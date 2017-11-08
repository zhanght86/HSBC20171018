package com.sinosoft.lis.claim;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class LLPRTGetNoticeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTGetNoticeBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private String mClmNo = ""; // 赔案号
	private String mPrtSeq = ""; // 流水号
	private String mPrtType="";

	public LLPRTGetNoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------批单-理赔给付批注-----LLPRTPostilPerBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}


		if(!getPrintData())
		{
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("----------批单-理赔给付批注-----LLPRTPostilPerBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
		// this.mCusNo = (String) mTransferData.getValueByName("CustNo");
		// //出险人客户号
		this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		this.mPrtType = (String) mTransferData.getValueByName("PrtType");
		return true;
	}


	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getPrintData()
	{

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String mSQL = "";


		//赔案号
		mSQL = "select OtherNo,managecom from LOPRTManager where PrtSeq='" + "?PrtSeq?"
				+ "'";
		logger.debug("根据打印管理表的流水号" + mPrtSeq + "查询赔案号的sql=" + mSQL);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(mSQL);
		sqlbv1.put("PrtSeq", mPrtSeq);
		SSRS pSsrs = new SSRS();
		pSsrs=tExeSQL.execSQL(sqlbv1);
		String mComcode="";
		if(pSsrs.MaxRow>0)
		{
			mClmNo = pSsrs.GetText(1, 1);
			mComcode = pSsrs.GetText(1, 2);
		}
		if(mComcode.length()>4)
			mComcode=mComcode.substring(0,4);

		// 理赔立案信息 
		mSQL = "select rgtantname,postcode,rgtantaddress,accidentdate from llregister  where rgtno='" + "?clmno?" + "'";
		logger.debug("根据赔案号" + mPrtSeq + "查询立案信息的sql=" + mSQL);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(mSQL);
		sqlbv2.put("clmno", mClmNo);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		String tRgtantName=tSSRS.GetText(1, 1);   //申请人姓名
		String tRgtantAddress=tSSRS.GetText(1, 3);// 申请人地址
		String tPostCode=tSSRS.GetText(1, 2);     // 申请人邮编
		String tAccidentDate=tSSRS.GetText(1, 4); //  事故日期

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("PdLpjfNotice00020.vts", "");
		tXmlExport.createDocument("理赔给付通知书[个人]");
		
		//----------------------------------------------------------------------
		tTextTag.add("RptorName", tRgtantName);// 赔案号
		tTextTag.add("RptorZip", tPostCode);// 事件号
		tTextTag.add("RptorAddress", tRgtantAddress);// 理赔类型
		tTextTag.add("AccidentDate", tAccidentDate);// 出险人
		tTextTag.add("PrtSeq", mPrtSeq);// 出险人

		String tComSql="select name from ldcom where trim(comcode)='"+"?comcode?"+"'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tComSql);
		sqlbv3.put("comcode", mComcode);
		String tComName=tExeSQL.getOneValue(sqlbv3);
		if(tComName.indexOf("MSRS保险股份公司")!=-1)
		{
			tComName=tComName.substring(tComName.indexOf("MSRS保险股份公司"));
		}
		tTextTag.add("MngCom", tComName);// 分公司机构名称
		tTextTag.add("Year", StrTool.getYear());
		tTextTag.add("Month", StrTool.getMonth());
		tTextTag.add("Day", StrTool.getDay());

		//总给付金额-----------------------------------------------------------
		// --
		String realPaySQL = "select (case when sum(pay) is null then 0 else sum(pay) end) from ljagetclaim where otherno='"+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(realPaySQL);
		sqlbv4.put("clmno", mClmNo);
		ExeSQL realPayExeSQL = new ExeSQL();
		tSSRS = realPayExeSQL.execSQL(sqlbv4);
		String amontFee = tSSRS.GetText(1, 1);
		logger.debug("赔案" + mClmNo + "的总给付金额是" + amontFee);
		double tZJFJE = Double.parseDouble(amontFee); // 为格式化小数点
//		String DXJE = PubFun.getChnMoney(tZJFJE);
		amontFee = new DecimalFormat("0.00").format(tZJFJE);
		tTextTag.add("ZJFJE", amontFee);
		// 理赔受益人给付信息循环输出---------------------------------
		ListTable tListTable = new ListTable();
		tListTable.setName("LPJF");

		String[] Title = new String[3];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";

		String tBnfSql = "select * from llbnfgather where clmno='"+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tBnfSql);
		sqlbv5.put("clmno", mClmNo);
		LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
		LLBnfGatherSet tLLBnfGatherSet = new LLBnfGatherSet();
		tLLBnfGatherSet = tLLBnfGatherDB.executeQuery(sqlbv5);
		// 循环受益人信息
		for (int i = 1; i <= tLLBnfGatherSet.size(); i++) {
			LLBnfGatherSchema tLLBnfGatherSchema = tLLBnfGatherSet.get(i);
			String[] bnfStr = new String[6];
			bnfStr[0] = "受益人"  ;
			bnfStr[1] = tLLBnfGatherSchema.getName();
			
			double bSumMoney = tLLBnfGatherSchema.getGetMoney();
			logger.debug("受益人" + tLLBnfGatherSchema.getName() + "的总给付金额是" + bSumMoney);
			bnfStr[2] = "受益金额" ;
			bnfStr[3] =  new DecimalFormat("0.00").format(bSumMoney);

			//延滞利息
			double tYZLX =0.00;
			String yzSql ="select (case when pay is null then 0 else pay end) from ljagetclaim where actugetno='"+"?actugetno?"+"' and feefinatype='YCLX'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(yzSql);
			sqlbv6.put("actugetno", tLLBnfGatherSchema.getOtherNo());
			SSRS mSSRS = new SSRS();
			mSSRS = tExeSQL.execSQL(sqlbv6);
			if(mSSRS.MaxRow>0)
			{
				tYZLX =Double.parseDouble(mSSRS.GetText(1, 1)) ;
			}
			logger.debug("受益人" + tLLBnfGatherSchema.getName() + "的延滞利息金额是" + tYZLX);
			bnfStr[4] = "延滞利息金额";
			bnfStr[5] =  new DecimalFormat("0.00").format(tYZLX);
			tListTable.add(bnfStr);			
			
			String tBnfStr2[] = new String[6];
			//支付方式
			String tZffs = tLLBnfGatherSchema.getCasePayMode();
			if("1".equals(tZffs)){
				tZffs = "现金";
			}
			if("2".equals(tZffs)){
				tZffs = "现金支票";
			}
			if("3".equals(tZffs)){
				tZffs = "转账支票";
			}
			if("4".equals(tZffs)){
				tZffs = "银行转账";
			}
			if("5".equals(tZffs)){
				tZffs = "内部转账";
			}
			tBnfStr2[0]="支付方式";
			tBnfStr2[1]=tZffs;

			//支付银行
			String tBankCode = tLLBnfGatherSchema.getBankCode();
			String tBankSql = "select bankname from ldbank where bankcode='"+"?bankcode?"+"'";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tBankSql);
			sqlbv12.put("bankcode", tBankCode);
			ExeSQL tBankExeSQL = new ExeSQL();
			String tBankName = tBankExeSQL.getOneValue(sqlbv12);
			
			if(tBankName==null)
			{
				tBankName="";
			}
			tBnfStr2[2]="支付银行";
			tBnfStr2[3]=tBankName;
			//银行账户名
			String tAccName = tLLBnfGatherSchema.getAccName();
			
			if(tAccName==null)
			{
				tAccName="";
			}
			tBnfStr2[4]="银行账户名";
			tBnfStr2[5]=tAccName;			
			tListTable.add(tBnfStr2);
		}

		
//		tTextTag.add("DXJE", DXJE);
		tXmlExport.addListTable(tListTable, Title);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入多行数据的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// xmlexport.addDisplayControl("Risk"); //模版上的主险附加险部分的控制标记
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		// 判断是否有医疗信息-----------------------------------------------
		String meSQL = "select feeitemtype,(case when sum(fee) is null then 0 else sum(fee) end),(case when sum(selfamnt) is null then 0 else sum(selfamnt) end) from llcasereceipt a  where clmno='"+"?clmno?"+"' "
					 + " and exists(select 1 from llclaimdetail where clmno=a.clmno and getdutykind in ('100','200')) group by feeitemtype" ;
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(meSQL);
		sqlbv7.put("clmno", mClmNo);
		ExeSQL meExeSQL = new ExeSQL();
		SSRS ySSRS = new SSRS();
		ySSRS=meExeSQL.execSQL(sqlbv7);

        double mAMoney=0.00;//门诊费用
        double mBMoney=0.00;//住院费用
        double mTotalMoney=0.00;//实际给付金额
        double mAdjMoney=0.00;//合理金额即理算基数金额
        double mOriMoney=0.00;//账单金额
        double mBTmoney=0.00;//每日补贴金额
        double mOtherMoney=0.00;//第三方给付
        for(int m=1; m<=ySSRS.MaxRow;m++)
        {      
        	double fee=Double.parseDouble(ySSRS.GetText(m, 2));//账单费用金额
        	double selfamnt=Double.parseDouble(ySSRS.GetText(m, 3));//自费金额
        	mOriMoney=mOriMoney+fee;//待理算金额即账单费用金额
        	double adjfee=fee-selfamnt;//账单扣除自费的金额
        	double adjsum=0.00;
        	String dTypeSql="select (case when sum(adjsum) is null then 0 else sum(adjsum) end) from llclaimdutyfee where clmno='"+"?clmno?"+"' and dutyfeetype='"+"?dutyfeetype?"+"' " ;
        	SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
    		sqlbv8.sql(dTypeSql);
    		sqlbv8.put("clmno", mClmNo);
    		sqlbv8.put("dutyfeetype", ySSRS.GetText(m, 1));
        	SSRS dTypeSSRS = meExeSQL.execSQL(sqlbv8);
        	if(dTypeSSRS.MaxRow>0)
        	{
        		adjsum=Double.parseDouble(dTypeSSRS.GetText(1, 1));//该类型账单的理算金额(含住院补贴)
        	}
        	        	       	
        	mBTmoney=mBTmoney+(adjsum-adjfee);//每日补贴金额
        	mAdjMoney=mAdjMoney+adjfee;
        	if("A".equals(ySSRS.GetText(m, 1)))
        	{
        		mAMoney=mAMoney+adjfee;
        	}
        	if("B".equals(ySSRS.GetText(m, 1)))
        	{
        		mBMoney=mBMoney+adjfee;
        	}	        
        }
        String oSql="select (case when sum(factorvalue) is null then 0 else sum(factorvalue) end) from llotherfactor where clmno='"+"?clmno?"+"' and feeitemtype='D'";//第三方给付
        SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(oSql);
		sqlbv9.put("clmno", mClmNo);
        SSRS oSSRS = meExeSQL.execSQL(sqlbv9);
        if(oSSRS.MaxRow>0)
        {
        	mOtherMoney=Double.parseDouble(oSSRS.GetText(1, 1));
        }
	    String mTSql="select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where clmno='"+"?clmno?"+"' and getdutykind in ('100','200')"; //实际给付金额
	    SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(mTSql);
		sqlbv10.put("clmno", mClmNo);
	    SSRS mTSSRS = meExeSQL.execSQL(sqlbv10);
	    if(mTSSRS.MaxRow>0)
	    {
    	  mTotalMoney=Double.parseDouble(mTSSRS.GetText(1, 1));
	    }

		if (ySSRS.MaxRow > 0) {

			TextTag pTextTag = new TextTag(); // 新建一个TextTag的实例
			pTextTag.add("MedicalFee", new DecimalFormat("0.00").format(mOriMoney));//疗账单费用
			pTextTag.add("CalSum", new DecimalFormat("0.00").format(mAdjMoney));//合理费用
			pTextTag.add("MedFeeSum", new DecimalFormat("0.00").format(mAMoney));//门诊费用
			pTextTag.add("InHosMedFeeSum",  new DecimalFormat("0.00").format(mBMoney));//住院费用
			pTextTag.add("DayPay", new DecimalFormat("0.00").format(mBTmoney));//每日补贴
			pTextTag.add("OtherPay", new DecimalFormat("0.00").format(mOtherMoney));//第三方给付
			pTextTag.add("TotalPay", new DecimalFormat("0.00").format(mTotalMoney));//赔付医疗保险金
			tXmlExport.addTextTag(pTextTag);
			tXmlExport.addDisplayControl("display");// 控制医疗账单信息是否显示
		}
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		String updateStateSql = "update loprtmanager set stateflag='1' where otherno='"
				+ "?clmno?" + "' and code='PCT020'";
		logger.debug("更新打印管理表的sql:" + updateStateSql);
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(updateStateSql);
		sqlbv11.put("clmno", mClmNo);
		mMMap.put(sqlbv11, "UPDATE");
		mResult.add(mMMap);

		return true;
	
	}
}
