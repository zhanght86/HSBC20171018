package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 万能险结算状态报告</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @rewrite：pst
 * @version：1.0
 * @CreateDate：2007-11-19
 */
public class InsuAccYearPrintBL implements PrintService
{
private static Logger logger = Logger.getLogger(InsuAccYearPrintBL.class);

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
    private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
   
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    
    //基本的标签信息

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
    /** 本期末保险金额*/                      
    private String mAmnt;     
	/**本年度结算日*/
	private String mBalaDate;	
	/**上年度生效日*/
	private  String mLastCValiDate;
	/**本年度生效日*/
	private  String mCurCValiDate;
   
    /**声明TextTag的实例*/
    TextTag texttag = new TextTag(); 
    /**声明ListTable的实例*/
    private ListTable tBonusListTable = new ListTable();
    public InsuAccYearPrintBL()
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
            tError.moduleName = "InsuAccYearPrintBL";
            tError.functionName = "submitData";
            tError.errorMessage = "不支持的操作字符串！";
            this.mErrors.addOneError(tError);

            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
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

    private boolean getInputData()
    {
        mGlobalInput.setSchema(
              (GlobalInput)
              mInputData.getObjectByObjectName("GlobalInput",0));
        mLOPRTManagerSchema.setSchema(
              (LOPRTManagerSchema)
              mInputData.getObjectByObjectName("LOPRTManagerSchema", 0));


        if (mGlobalInput == null || mLOPRTManagerSchema == null)
        {
            mErrors.addOneError(new CError("数据传输不完全！"));
            return false;
        }
        if (mLOPRTManagerSchema.getPrtSeq() == null)
        {
             mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
            return false;
        }
        LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
        tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
        if (tLOPRTManagerDB.getInfo() == false)
        {
             mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
             mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
             return false;
         }
         mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
         String mNoType = mLOPRTManagerSchema.getOtherNoType();

         if (mNoType == null || mNoType.trim().equals(""))
         {
             // @@错误处理
             CError tError = new CError();
             tError.moduleName = "InsuAccYearPrintBL";
             tError.functionName = "getInputData";
             tError.errorMessage = "获取OtherNoType失败！";
             this.mErrors.addOneError(tError);

             return false;
        }else if(!mNoType.trim().equals(PrintManagerBL.ONT_CONT))
        {
             // @@错误处理
             CError tError = new CError();
             tError.moduleName = "InsuAccYearPrintBL";
             tError.functionName = "getInputData";
             tError.errorMessage = "错误的OtherNo类型！";
             this.mErrors.addOneError(tError);

             return false;
         }

        String tNoticeType = mLOPRTManagerSchema.getCode();
        if(tNoticeType == null || tNoticeType.trim().equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "InsuAccYearPrintBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "获取单据类型失败！";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(!tNoticeType.trim().equals(PrintManagerBL.CODE_EdorINSUACCY))
        {
            CError tError = new CError();
            tError.moduleName = "InsuAccYearPrintBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "单据类型错误：万能险结算报告书！";
            this.mErrors.addOneError(tError);
            return false;
        }

        mContNo = mLOPRTManagerSchema.getOtherNo();

        if (mContNo == null || mContNo.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "InsuAccYearPrintBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "获取保单险种号失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
        mPolNo=mLOPRTManagerSchema.getStandbyFlag1();       
        if (mPolNo == null || mPolNo.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "InsuAccYearPrintBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "获取保单险种号失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
        mBalaDate=mLOPRTManagerSchema.getStandbyFlag2();       
        if (mBalaDate == null || mBalaDate.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "InsuAccYearPrintBL";
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
            tError.moduleName = "InsuAccYearPrintBL";
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

		// 查询保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLCPolSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LoanOveDulNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema.setSchema(tLCContDB.getSchema());
    	//查询投保人信息
        LCAppntDB tLCAppntDB = new LCAppntDB();
        LCAppntSchema tLCAppntSchema = new LCAppntSchema();
        tLCAppntDB.setContNo(mContNo);
        if (!tLCAppntDB.getInfo())
        {
            CError tError = new CError();
            tError.moduleName = "InsuAccYearPrintBL";
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
               tError.moduleName = "InsuAccYearPrintBL";
               tError.functionName = "getBaseData";
               tError.errorMessage = "投保人名字为空!";
               this.mErrors.addOneError(tError);
               return false;
    	}

        //查询投保人地址及保单信息
        LCAddressDB tLCAddressDB = new LCAddressDB();
        LCAddressSchema tLCAddressSchema = new LCAddressSchema();
        String tSQLAD="select * from lcaddress l "
				     + " where l.customerno =(select c.appntno from lcappnt c where c.contno ='"+"?mContNo?"+"') "
					 +" and l.addressno ='"+"?addressno?"+"'";
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(tSQLAD);
        sqlbv1.put("mContNo", mContNo);
        sqlbv1.put("addressno", tLCAppntSchema.getAddressNo());
        tLCAddressSchema = tLCAddressDB.executeQuery(sqlbv1).get(1);
        if(tLCAddressSchema==null )
        {
 	       CError tError = new CError();
           tError.moduleName = "InsuAccYearPrintBL";
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
           tError.moduleName = "InsuAccYearPrintBL";
           tError.functionName = "getBaseData";
           tError.errorMessage = "投保人地址为空!";
           this.mErrors.addOneError(tError);
           return false;
    	}                      

       mAmnt = Double.toString(mLCPolSchema.getAmnt());
       
       ExeSQL tExeSQL = new ExeSQL();
       String tSQL="select fee from LCInsureAccClassFee where contno='"+"?mContNo?"+"' "
                  + " and InsuAccNo='000006' and feecode like '%01' and polno='"+"?mPolNo?"+"'";
       SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
       sqlbv2.sql(tSQL);
       sqlbv2.put("mContNo", mContNo);
       sqlbv2.put("mPolNo", mPolNo);
       String tInitFee=tExeSQL.getOneValue(sqlbv2);
       if("".equals(tInitFee))
       {
 	       CError tError = new CError();
           tError.moduleName = "InsuAccYearPrintBL";
           tError.functionName = "getBaseData";
           tError.errorMessage = "查询保单初始费用失败!";
           this.mErrors.addOneError(tError);
           return false;
       }
       //2008-03-03----------2008-04-01 
       //2008-04-01----------2009-04-01
       //2008-03-03----------2009-03-01 上次累计
      // 2008-03-03----------2009-03-01 本次累计
       String tYear=mBalaDate.substring(0, 4); //避免跨年度问题
	   int sYear=Integer.parseInt(tYear);
	   mCurCValiDate =calDate(sYear,mLCPolSchema.getCValiDate());   
	   //说明已经跨年度，重新计算本年度生效日，保单生效日在12月份就会出现这样的情况
	   if("12".equals(mCurCValiDate.substring(5, 7)))
	   {
		   mCurCValiDate =PubFun.calDate(mCurCValiDate, -1, "Y", "");     
	   }
       mLastCValiDate=PubFun.calDate(mCurCValiDate, -1, "Y", ""); 
	    //考虑 生效日为每月1号的问题
		if("01".equals(mLCPolSchema.getCValiDate().substring(8, mLCPolSchema.getCValiDate().length())))
		{
			mCurCValiDate=PubFun.calDate(mCurCValiDate, 1, "D", "");
		}

       tSQL="select sum(money) from LCInsureAccTrace where contno='"+"?mContNo?"+"' "
          + " and payDate>='1900-01-01' and payDate <'"+"?mLastCValiDate?"+"' and polno='"+"?mPolNo?"+"'";
       SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
       sqlbv3.sql(tSQL);
       sqlbv3.put("mContNo", mContNo);
       sqlbv3.put("mLastCValiDate", mLastCValiDate);
       sqlbv3.put("mPolNo", mPolNo);
String tLastAccBala=tExeSQL.getOneValue(sqlbv3);
if("".equals(tLastAccBala))
{
	tLastAccBala="0.00";
}

		tSQL="select sum(money) from LCInsureAccTrace where contno='"+"?mContNo?"+"' "
		+ " and payDate>=to_date('1900-01-01','yyyy-mm-dd') and payDate <'"+"?mCurCValiDate?"+"' and polno='"+"?mPolNo?"+"'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL);
		sqlbv4.put("mContNo", mContNo);
		sqlbv4.put("mCurCValiDate", mCurCValiDate);
		sqlbv4.put("mPolNo", mPolNo);
		String tAccBala=tExeSQL.getOneValue(sqlbv4);
if("".equals(tAccBala))
{
	tAccBala="0.00";
}


		tSQL="select riskname from LMRiskApp where riskcode='"+"?riskcode?"+"'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL);
		sqlbv5.put("riskcode", mLCPolSchema.getRiskCode());
		String tRiskName=tExeSQL.getOneValue(sqlbv5);
if("".equals(tRiskName))
{
     CError tError = new CError();
     tError.moduleName = "InsuAccYearPrintBL";
     tError.functionName = "getBaseData";
     tError.errorMessage = "查询保单险种信息失败!";
     this.mErrors.addOneError(tError);
     return false;
}

	   texttag.add("Post", mPost);
       texttag.add("Address",mAddress);
       texttag.add("AppntName",mName);    
       texttag.add("ContNo",mContNo);      
       texttag.add("Amnt",BqNameFun.getFormat(mAmnt)); 
       texttag.add("Prem",BqNameFun.getFormat(mLCPolSchema.getPrem()));
       texttag.add("InitFee",BqNameFun.getFormat(tInitFee));
       texttag.add("CvaliDate",mLCPolSchema.getCValiDate());
       texttag.add("LastInsuAccBala",BqNameFun.getFormat(tLastAccBala));
       texttag.add("RiskName",tRiskName);
       texttag.add("InsuAccBala",BqNameFun.getFormat(tAccBala));
       texttag.add("StartDate",mLastCValiDate);
       texttag.add("EndDate",mCurCValiDate);
       //添加代理人信息
	     BqNameFun.AddBqNoticeAgentInfo(tLCContSchema, texttag);

       return true;
    }
       /**获取表格数据*/
    
    
       private boolean getLisTableData() {

		tBonusListTable.setName("HL");
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		// 将数据放入ListTable
		String strIO[]=null;
		String tSQL="Select PAYDATE,  "             
		+"	(case when (select (case when (select concat('0',trim(cast(r.rate as char(20))))  "             
		+" from lminsuaccrate r  "             
		+"	where r.baladate = a.paydate  "             
		+"	and r.riskcode = a.riskcode  "             
		+"	and r.insuaccno = a.insuaccno) is not null then (select concat('0',trim(cast(r.rate as char(20))))  "             
		+" from lminsuaccrate r  "             
		+"	where r.baladate = a.paydate  "             
		+"	and r.riskcode = a.riskcode  "             
		+"	and r.insuaccno = a.insuaccno)  else (select concat('0',trim(cast(r.rate as char(20))))  "             
		+"	from lminsuaccrate r  "             
		+"	where r.baladate = concat(substr(a.paydate, 1, 8) , '01')   "             
		+" and r.riskcode = a.riskcode "             
		+" and r.insuaccno = a.insuaccno) end)  "             
		+" from dual) is not null then (select (case when (select concat('0',trim(cast(r.rate as char(20))))  "             
		+" from lminsuaccrate r  "             
		+"	where r.baladate = a.paydate  "             
		+"	and r.riskcode = a.riskcode  "             
		+"	and r.insuaccno = a.insuaccno) is not null then (select concat('0',trim(cast(r.rate as char(20)))  "             
		+" from lminsuaccrate r  "             
		+"	where r.baladate = a.paydate  "             
		+"	and r.riskcode = a.riskcode  "             
		+"	and r.insuaccno = a.insuaccno)  else (select concat('0',trim(cast(r.rate as char(20))))  "             
		+"	from lminsuaccrate r  "             
		+"	where r.baladate = concat(substr(a.paydate, 1, 8) , '01') "             
		+" and r.riskcode = a.riskcode "             
		+" and r.insuaccno = a.insuaccno) end)  "             
		+"	from dual)  else '' end) ,   "             
		+"	(case " 
		+" when ((select  trim(codename)  " 				
		+"  from ldcode  "             
		+"	where '1209882020000' = '1209882020000'  "             
		+"	and codetype = 'finfeetype' "             
		+"	and trim(code) = trim(a.MONEYTYPE))='退保金' and "
		+" (select 'X' from LpInsureAccTrace q where q.serialno=a.serialno and q.edortype='OP')='X' )"
		+"  then '部分领取' "
		+" when ((select  trim(codename)  " 				
		+"  from ldcode  "             
		+"	where '1209882020000' = '1209882020000'  "             
		+"	and codetype = 'finfeetype' "             
		+"	and trim(code) = trim(a.MONEYTYPE))='领取手续费' and "
		+" (select 'X' from LpInsureAccTrace q where q.serialno=a.serialno and q.edortype='OP')='X' )"
		+"  then '领取费用' "	
		+" when ((select  trim(codename)  " 				
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
		+" and a.paydate >='"+"?mLastCValiDate?"+"' and a.paydate<'"+"?mCurCValiDate?"+"'"  		
		+" and contno='"+"?mContNo?"
		+"' order by paydate,MONEY desc";
		logger.debug("执行SQL"+tSQL);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSQL);
		sqlbv6.put("mLastCValiDate", mLastCValiDate);
		sqlbv6.put("mCurCValiDate", mCurCValiDate);
		sqlbv6.put("mContNo", mContNo);
		BQSSRS = tExeSQL.execSQL(sqlbv6);
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
				strIO[3]=BqNameFun.getFormat(BQSSRS.GetText(i, 4));
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
    	String printName = "万能险个人帐户报告（年）";
		XmlExportNew xmlExport = new XmlExportNew();
		xmlExport.createDocument(printName); // 初始化数据存储类
		String uLanguage = PrintTool.getCustomerLanguage(mLCPolSchema.getAppntNo());
		if(uLanguage!=null && !"".equals(uLanguage)){
			xmlExport.setUserLanguage(uLanguage);//用户语言
		}
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		
		if (tBonusListTable.size() > 0)
		{
			String b_strIO[] = new String[5];
			xmlExport.addListTable(tBonusListTable, b_strIO);
		}
		mResult.clear();
		mResult.addElement(xmlExport);
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
				tMonth = 3;
				tDay = 1;
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
		tLOPRTManagerDB.setOtherNo("86110020080219000566");
		tLOPRTManagerDB.setCode("BQ01");
		tLOPRTManagerSchema = tLOPRTManagerDB.query().get(1);
		VData tVData = new VData();
		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);
		InsuAccYearPrintBL tInsuAccYearPrintBL = new InsuAccYearPrintBL();
		tInsuAccYearPrintBL.submitData(tVData, "PRINT");
		tInsuAccYearPrintBL = null;

    }
}
