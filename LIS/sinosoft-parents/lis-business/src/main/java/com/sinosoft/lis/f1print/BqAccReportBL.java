package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
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

public class BqAccReportBL {
private static Logger logger = Logger.getLogger(BqAccReportBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
   
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    
    //基本的标签信息
    
    /**险种编码*/
    private String mRiskCode;

    /**保单印刷号*/
    private String mPrtNo;
    /**保单险种号*/
    private String mPolNo;  
    /**合同号*/
    private String mContNo;  
    /**邮政编码*/
    private String mPost;  
    /** 地址*/
    private String mAddress;  
    /** 投保人姓名*/
    private String mName;      
    /** 被保人姓名*/
    private String mInsureName; 
    /** 生效日*/                       
    private String mCValiDate;  
    /** 本期末保险金额*/                      
    private String mAmnt;     
    /** 当天*/                      
    private String mDay;      
    /**本次结算利率类型*/
	private String mRateIntv;   
	/**本起始日期*/
	private String mStartDate;		
	/**本结束日期*/
	private String mEndDate;	


    private String mCurrentDate = PubFun.getCurrentDate();    
    private String mCurrentTime = PubFun.getCurrentTime(); 
    /**模板名称，只读*/
    public static final String VTS_NAME = "InsuAccReport.vts";     
    
    /**声明TextTag的实例*/
    TextTag texttag = new TextTag(); 
    /**声明ListTable的实例*/
    private ListTable tBonusListTable = new ListTable();
    public BqAccReportBL()
    {}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     * @param: cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        if (!mOperate.equals("PRINT"))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "submitData";
            tError.errorMessage = "不支持的操作字符串！";
            this.mErrors.addOneError(tError);

            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        /**获得标签数据*/
        if (!getBaseData())
        {
            return false;
        }
        /**获取表格数据*/
        if(!getLisTableData())
        {
            return false;
        }
 
        //准备需要打印的数据
        if (!preparePrintData())
        {
            return false;
        }

        return true;
    }

    private boolean getInputData(VData cInputData)
    {
        mGlobalInput.setSchema(
              (GlobalInput)
              cInputData.getObjectByObjectName("GlobalInput",0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData
				.getObjectByObjectName("GlobalInput", 0));

		mContNo = (String) mTransferData
				.getValueByName("ContNo");


        if (mContNo == null || mContNo.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "获取保单险种号失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
        mPolNo=(String) mTransferData
		.getValueByName("PolNo");      
        if (mPolNo == null || mPolNo.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "获取保单险种号失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
        mStartDate=(String) mTransferData
		.getValueByName("StartDate");        
        if (mStartDate == null || mStartDate.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "结算日期获取失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
        
        mEndDate=(String) mTransferData
		.getValueByName("EndDate");        
        if (mEndDate == null || mEndDate.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "结算日期获取失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
    	//查询投保人信息
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mPolNo);
        if (!tLCPolDB.getInfo())
        {
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "getBaseData";
            tError.errorMessage = "查询投保人信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLCPolSchema = tLCPolDB.getSchema();
        return true;
    }


    /** 获得标签数据*/
    private boolean getBaseData()
    {

    	//查询投保人信息
        LCAppntDB tLCAppntDB = new LCAppntDB();
        LCAppntSchema tLCAppntSchema = new LCAppntSchema();
        tLCAppntDB.setContNo(mContNo);
        if (!tLCAppntDB.getInfo())
        {
            CError tError = new CError();
            tError.moduleName = "BqAccReportBL";
            tError.functionName = "getBaseData";
            tError.errorMessage = "查询投保人信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        tLCAppntSchema = tLCAppntDB.getSchema();
        mName = tLCAppntSchema.getAppntName();    
        //logger.debug("投保人姓名："+mName);        
      	if(mName == null || mName.trim().equals(""))
    	{
    	       CError tError = new CError();
               tError.moduleName = "BqAccReportBL";
               tError.functionName = "getBaseData";
               tError.errorMessage = "投保人名字为空!";
               this.mErrors.addOneError(tError);
               return false;
    	}

        //查询投保人地址及保单信息
        LCAddressDB tLCAddressDB = new LCAddressDB();
        LCAddressSchema tLCAddressSchema = new LCAddressSchema();
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        String tSQLAD="select * from lcaddress l "
				     + " where l.customerno =(select c.appntno from lcappnt c where c.contno ='"+"?mContNo?"+"') "
					 +" and l.addressno ='"+"?addressno?"+"'";
        sqlbv1.sql(tSQLAD);
        sqlbv1.put("mContNo", mContNo);
        sqlbv1.put("addressno", tLCAppntSchema.getAddressNo());
        tLCAddressSchema = tLCAddressDB.executeQuery(sqlbv1).get(1);
        if(tLCAddressSchema==null )
        {
 	       CError tError = new CError();
           tError.moduleName = "BqAccReportBL";
           tError.functionName = "getBaseData";
           tError.errorMessage = "投保人地址信息为空!";
           this.mErrors.addOneError(tError);
           return false;
        }
        mPost = tLCAddressSchema.getZipCode();  

       mAddress = tLCAddressSchema.getPostalAddress();    
    	if(mAddress == null || mAddress.trim().equals(""))
    	{    
 	       CError tError = new CError();
           tError.moduleName = "BqAccReportBL";
           tError.functionName = "getBaseData";
           tError.errorMessage = "投保人地址为空!";
           this.mErrors.addOneError(tError);
           return false;
    	}                      

       mAmnt = Double.toString(mLCPolSchema.getAmnt());
       
       ExeSQL tExeSQL = new ExeSQL();
       SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
       String tSQL="select fee from LCInsureAccClassFee where contno='"+"?mContNo?"+"' "
                  + " and InsuAccNo='000006' and feecode like '%01' and polno='"+"?mPolNo?"+"'";
       sqlbv2.sql(tSQL);
       sqlbv2.put("mContNo", mContNo);
       sqlbv2.put("mPolNo", mPolNo);
       String tInitFee=tExeSQL.getOneValue(sqlbv2);
       if("".equals(tInitFee))
       {
 	       CError tError = new CError();
           tError.moduleName = "BqAccReportBL";
           tError.functionName = "getBaseData";
           tError.errorMessage = "查询保单初始费用失败!";
           this.mErrors.addOneError(tError);
           return false;
       }

       

       tSQL="select sum(money) from LCInsureAccTrace where contno='"+"?mContNo?"+"' "
          + " and payDate>='1900-01-01' and payDate <'"+"?mStartDate?"+"' and polno='"+"?mPolNo?"+"'";
       sqlbv2.sql(tSQL);
       sqlbv2.put("mContNo", mContNo);
       sqlbv2.put("mStartDate", mStartDate);
       sqlbv2.put("mPolNo", mPolNo);

String tLastAccBala=tExeSQL.getOneValue(sqlbv2);
if("".equals(tLastAccBala))
{
	tLastAccBala="0.00";
}

 tSQL="select sum(money) from LCInsureAccTrace where contno='"+"?mContNo?"+"' "
+ " and payDate>='1900-01-01' and payDate <'"+"?mEndDate?"+"' and polno='"+"?mPolNo?"+"'";
 sqlbv2.sql(tSQL);
 sqlbv2.put("mContNo", mContNo);
 sqlbv2.put("mEndDate", mEndDate);
 sqlbv2.put("mPolNo", mPolNo);

String tAccBala=tExeSQL.getOneValue(sqlbv2);
if("".equals(tAccBala))
{
	tAccBala="0.00";
}


 tSQL="select riskname from LMRiskApp where riskcode='"+"?riskcode?"+"'";
 sqlbv2.sql(tSQL);
 sqlbv2.put("riskcode", mLCPolSchema.getRiskCode());

String tRiskName=tExeSQL.getOneValue(sqlbv2);
if("".equals(tRiskName))
{
     CError tError = new CError();
     tError.moduleName = "BqAccReportBL";
     tError.functionName = "getBaseData";
     tError.errorMessage = "查询保单险种信息失败!";
     this.mErrors.addOneError(tError);
     return false;
}

	   texttag.add("Post", mPost);
       texttag.add("Address",mAddress);
       texttag.add("AppntName",mName);    
       texttag.add("ContNo",mContNo);      
       texttag.add("Amnt",mAmnt); 
       texttag.add("Prem",mLCPolSchema.getPrem());
       texttag.add("InitFee",tInitFee);
       texttag.add("CvaliDate",mLCPolSchema.getCValiDate());
       texttag.add("LastInsuAccBala",tLastAccBala);
       texttag.add("RiskName",tRiskName);
       texttag.add("InsuAccBala",BqNameFun.getRound(tAccBala, ""));
       texttag.add("StartDate",mStartDate);
       texttag.add("EndDate",mEndDate);
       

       return true;
    }
       /**获取表格数据*/
    
    
       private boolean getLisTableData() {

		tBonusListTable.setName("HL");
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		// 将数据放入ListTable
		String strIO[]=null;
		String tSQL="Select PAYDATE,  "             
		+"	 (select (case when (select r.rate  "             
		+" from lminsuaccrate r  "             
		+"	where r.baladate = a.paydate  "             
		+"	and r.riskcode = a.riskcode  "             
		+"	and r.insuaccno = a.insuaccno) is not null then (select r.rate  "             
		+" from lminsuaccrate r  "             
		+"	where r.baladate = a.paydate  "             
		+"	and r.riskcode = a.riskcode  "             
		+"	and r.insuaccno = a.insuaccno)  else (select r.rate  "             
		+"	from lminsuaccrate r  "             
		+"	where r.baladate = concat(substr(a.paydate, 1, 8),'01')"             
		+"	and r.riskcode = a.riskcode "             
		+"  and r.insuaccno = a.insuaccno) end)"             
		+"	from dual),   "             
		+"	(case " 
		+"  when ((select  trim(codename)  " 				
		+"  from ldcode  "             
		+"	where '1209882020000' = '1209882020000'  "             
		+"	and codetype = 'finfeetype' "             
		+"	and trim(code) = trim(a.MONEYTYPE))='退保金' and "
		+" (select 'X' from LpInsureAccTrace q where q.serialno=a.serialno and q.edortype='OP')='X' )"
		+"  then '部分领取' "
		+"  when ((select  trim(codename)  " 				
		+"  from ldcode  "             
		+"	where '1209882020000' = '1209882020000'  "             
		+"	and codetype = 'finfeetype' "             
		+"	and trim(code) = trim(a.MONEYTYPE))='领取手续费' and "
		+" (select 'X' from LpInsureAccTrace q where q.serialno=a.serialno and q.edortype='OP')='X' )"
		+"  then '领取费用' "	
		+"  when ((select  trim(codename)  " 				
		+"  from ldcode  "             
		+"	where '1209882020000' = '1209882020000'  "             
		+"	and codetype = 'finfeetype' "             
		+"	and trim(code) = trim(a.MONEYTYPE))='领取手续费' and "
		+" (select 'X' from LpInsureAccTrace q where q.serialno=a.serialno and q.edortype='CT')='X' )"
		+"  then '退保费用' "	
		+"  else "
		+" (select  trim(codename)  " 				
		+"  from ldcode  "             
		+"	where '1209882020000' = '1209882020000'  "             
		+"	and codetype = 'finfeetype' "             
		+"	and trim(code) = trim(a.MONEYTYPE)) "
		+" end  ),	"             
		+"	MONEY,	 "             
		+"	(case   "             
		+"	when (select sum(MONEY)  "             
		+"	from LCInsureAccTrace r   "             
		+"	where r.POLNO = a.POLNO  "             
		+"	and r.PAYDATE >= '1900-01-01'  "             
		+"	and r.PAYDATE < a.paydate) < 0 then   "             
		+"	0  "             
		+"	else  "             
		+"	(select sum(MONEY)  "             
		+"	from LCInsureAccTrace r    "             
		+"	where r.POLNO = a.POLNO   "             
		+"	and r.PAYDATE >= '1900-01-01'  "             
		+"	and r.PAYDATE < a.paydate) "             
		+"	 end),moneytype   "             
		+"	From LCInsureAccTrace a                                                 "             
		+" where a.InsuAccNo = '000006' "  
		+" and a.paydate >='"+"?mStartDate?"+"' and a.paydate<'"+"?mEndDate?"+"'"  		
		+" and contno='"+"?mContNo?"
		+"' order by paydate,money desc ";
		sqlbv.sql(tSQL);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mContNo", mContNo);
		logger.debug("执行SQL"+tSQL);
		BQSSRS = tExeSQL.execSQL(sqlbv);
		if (BQSSRS != null && BQSSRS.MaxRow > 0) {
			String tPreAccBala="";
			for (int i = 1; i <= BQSSRS.getMaxRow(); i++) {
				strIO = new String[5];	
				strIO[0]=BQSSRS.GetText(i, 1);
				strIO[1]=BQSSRS.GetText(i, 2);
				//如果是保费收入，利率置空
				if(BQSSRS.GetText(i, 6).equals("BF"))
				{
					strIO[1]="";
				}
				strIO[2]=BQSSRS.GetText(i, 3);
				strIO[3]=BqNameFun.getRound(BQSSRS.GetText(i, 4));
				if(i==1)
				{
					if(BQSSRS.GetText(i, 5)==null||"null".equals(BQSSRS.GetText(i, 5))) //说明是首期保费收入
					{
						strIO[4] = BqNameFun.getFormat(BQSSRS.GetText(i, 4));	
					}
					else
					{
						strIO[4] = getRealTimeMoney(BQSSRS.GetText(i, 3),BQSSRS.GetText(i, 4),BQSSRS.GetText(i, 5));
					}							
				}
				else
				{
					strIO[4] = getRealTimeMoney(BQSSRS.GetText(i, 3),BQSSRS.GetText(i, 4),tPreAccBala);				
				}
				tPreAccBala=strIO[4];

				tBonusListTable.add(strIO);	
			}
		}

		return true;
	}
       
  	/**
  	 * 实时计算出每天每笔收支的对账户的影响
  	 * @param 当天帐户的金额 tCurDayAccBala
  	 * @param 此笔收支类型, tMoneyType
  	 * @param 此笔收支金额, tMoney
  	 * */
      private String getRealTimeMoney(String tMoneyType,String tMoney, String tCurDayAccBala )
      {
  		return BqNameFun.getFormat((Double.parseDouble(tCurDayAccBala)+Double.parseDouble(tMoney)));
  	  }
    private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		
		if (tBonusListTable.size() > 0)
		{
			String b_strIO[] = new String[5];
			tXmlExport.addListTable(tBonusListTable, b_strIO);
		}
		// tXmlExport.outputDocumentToFile("E:\\", "P03"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}  

	/**
	 * 得到tDate在tYear这一年的对应日
	 * @param tYear 所在年度
	 * @param　tDate 日期
	 * @return　String : tDate在tYear这一年的对应日
	 * */
	private String calDate(int tYear, String tDate) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			//如果是2月29日，而其上一年不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 2;
				tDay = 28;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}

		return coDate;
	}

	/**
	 * 闰年校验
	 * @param mYear 年度
	 * @return  boolean 闰年:true  平年：false
	 * */
	private boolean isLeap(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}
  

   
    public VData getResult()
    {
        return mResult;
    }
    public CErrors getErrors()
    {
        return mErrors;
    }
    public static void main(String[] args)
    {
       GlobalInput mGlobalInput = new GlobalInput();
       mGlobalInput.Operator = "bq";
       mGlobalInput.ManageCom = "86";
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerDB.setOtherNo("86230020080219032153");
		tLOPRTManagerDB.setCode("BQ01");
		tLOPRTManagerSchema = tLOPRTManagerDB.query().get(1);
		VData tVData = new VData();
		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);
		BqAccReportBL tBqAccReportBL = new BqAccReportBL();
		tBqAccReportBL.submitData(tVData, "PRINT");
		tBqAccReportBL = null;

    }


}
