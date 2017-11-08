package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.lis.pubfun.PubFun;
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
 * <p>Title: 万能险年结报告清单</p>
 * <p>Description: 万能险年结报告清单</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: NCL</p>
 * @author：pst
 * @version 1.0
 */

public class InsuAccYearListPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(InsuAccYearListPrintBL.class);
	//公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	public static final String VTS_NAME = "InsuAccYearList.vts";

	//全局变量
	private String mOperate;

	private VData mInputData = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private TextTag mTextTag = new TextTag();

	private ListTable mListTable1 = new ListTable();

	private ListTable mListTable2 = new ListTable();

	private ExeSQL tExeSQL = new ExeSQL();

	private String StartDate; //统计起期

	private String EndDate; //统计止期

	private String mManageCom; //机构

	private String mManageComName; //机构名称

	private String mChannel; //渠道
	/**文件生成名称*/
	private String mExCelFile="";
	/**模板路径*/
	private String strTemplatePath="";
	private String mEdorDate; //统计日期

	private String mSumMoney; //合计金额

	private String mSumCount; //合计件数
	
    private String currentdate = PubFun.getCurrentDate();

	public InsuAccYearListPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		//数据传输
		if (!getInputData()) {
			CError.buildErr(this, "BL清单数据传输失败!");
			return false;
		}

		//打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "BL清单数据提取失败!");
			return false;
		}

		//准备数据
		if (!prepareData()) {
			CError.buildErr(this, "BL清单数据生成失败!");
			return false;
		}
		return true;
	}

	//获取外部传入数据
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		StartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		EndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 机构
		mChannel = (String) mTransferData.getValueByName("Chanel"); // 渠道
		mExCelFile = (String) mTransferData.getValueByName("ExCelFile"); // ExCel文件名称
		strTemplatePath = (String) mTransferData
				.getValueByName("ExCelTemplatePath"); // 模板路径
		if (StartDate == null || EndDate == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (strTemplatePath == null || mExCelFile == null) {
			mErrors.addOneError(new CError("模板或者输出文件名为空！"));
			return false;
		}
		return true;
	}

	//提取清单打印需要的数据
	private boolean dealData() {
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		mListTable1.setName("HL");		
		//查询满足条件的保单
		String tSql = "select otherno,standbyflag2 from loprtmanager where code='BQ01' "
            + " and standbyflag2>'"+"?StartDate?"+"' and standbyflag2<='"+"?EndDate?"+"' and managecom like concat('"+"?mManageCom?"+"','%')"
            + " order by otherno  ";
		sqlbv1.sql(tSql);
		sqlbv1.put("StartDate", StartDate);
		sqlbv1.put("EndDate", EndDate);
		sqlbv1.put("mManageCom", mManageCom);
		SSRS rSSRS = tExeSQL.execSQL(sqlbv1);
		if (rSSRS == null || rSSRS.MaxRow < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorBLBillPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "统计期间没有记录";
			this.mErrors.addOneError(tError);
			return false;
		}else
		{
		int tMaxRow=0;	
		for (int k = 1; k <= rSSRS.MaxRow; k++) {
			String  tContNo=rSSRS.GetText(k, 1);
			String  tCurBalaDate=rSSRS.GetText(k, 2);
			
		       ExeSQL tExeSQL = new ExeSQL();
		       SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		       String tSQL="select CValiDate from LCCont where contno='"+"?tContNo?"+"'";	
		       sqlbv2.sql(tSQL);
		       sqlbv2.put("tContNo", tContNo);
		       String tCValiDate=tExeSQL.getOneValue(sqlbv2);
		       tCValiDate=tCValiDate.substring(0, 10);

		       String tYear=tCurBalaDate.substring(0, 4); //避免跨年度问题
			   int sYear=Integer.parseInt(tYear);
			   String mCurCValiDate =calDate(sYear,tCValiDate);  
			   //说明已经跨年度，重新计算本年度生效日，保单生效日在12月份就会出现这样的情况
			   if("12".equals(mCurCValiDate.substring(5, 7)))
			   {
				   mCurCValiDate =calDate(sYear-1,tCValiDate);     
			   }
		       String mLastCValiDate=PubFun.calDate(mCurCValiDate, -1, "Y", ""); 
			    //考虑 生效日为每月1号的问题
				if("01".equals(tCValiDate.substring(8, tCValiDate.length())))
				{
					mCurCValiDate=PubFun.calDate(mCurCValiDate, 1, "D", "");
				}
		//查询全记录
				
//				个人账户万能年报
//				投保人姓名、通讯地址、邮政编码、投保人手机号、保单号、险种名称、保单生效日、
//				统计时间、趸交保费、初始费用、基本保险金额、前一报告期末个人账户价值、
//				本年度保单账户结算价值、统计时间内保单变动（包括结算日期、结算利率、项目名称、账户收支金额、账户价值变化）
//				、服务人员姓名、业务代码、服务人员联系方式。
//		
				 SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		   tSql = " Select CONTNO 保单号, "
				+ " (select appntname from lcappnt c where c.contno = a.contno) 投保人姓名, "
				+ " (select postaladdress "
				+ "	from lcaddress l   "
				+ " where l.customerno ="
				+ "			 (select c.appntno from lcappnt c where c.contno = a.contno) "
				+ "	 and l.addressno =(select c.addressno "
				+ "									from lcappnt c "
				+ "								 where c.contno = a.contno)) 地址, "
				+ " (select zipcode "
				+ "	from lcaddress l "
				+ " where l.customerno = "
				+ "			 (select c.appntno from lcappnt c where c.contno = a.contno) "
				+ "	 and l.addressno =(select c.addressno "
				+ "									from lcappnt c "
				+ "								 where c.contno = a.contno)) 邮编, "
				+ " (select concat(concat(concat(concat(l.mobile ,'/'), l.phone) ,'/'), l.homephone) "
				+ "	from lcaddress l "
				+ " where l.customerno = "
				+ "			 (select c.appntno from lcappnt c where c.contno = a.contno) "
				+ "	 and l.addressno =(select c.addressno "
				+ "									from lcappnt c "
				+ "								 where c.contno = a.contno)) 投保人手机, "				
				+ " (select riskname from lmriskapp m where m.riskcode = a.RISKCODE) 险种名称, "
				+ " (select t.cvalidate from lccont t where t.contno = a.contno) 保单生效日, "
				+ " '"
				+ "?mLastCValiDate?"
				+ "', "
				+ " '"
				+ "?mCurCValiDate?"
				+ "', "
				+ " (select lc.insuaccbala from lcinsureacc lc where lc.contno = a.contno) 帐户价值, "
				+ " (select p.prem from lccont p where p.contno = a.contno) 趸交保费, "
				+ " (select f.fee "
				+ "	from lcinsureaccclassfee f  "
				+ " where f.contno = a.contno "
				+ "	 and f.feecode like '%01') 初始费用, "
				+ " (select q.amnt from lccont q where q.contno = a.contno) 基本保额, "
				+ " (select (case when sum(tr.money) is not null then sum(tr.money)  else 0 end) "
				+ "	from lcinsureacctrace tr "
				+ " where tr.contno = a.contno "
				+ "	 and tr.paydate < "
				+ " '"+"?mLastCValiDate?"+"') 前一报告期末帐户价值, "
				+ " a.paydate 帐户变动日期, "
				+ " (case when ( select (case when ( select concat('0',trim(cast(r.rate as char(20)))) "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) is not null then ( select concat('0',trim(cast(r.rate as char(20)))) "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno)  else ( select concat('0',trim(cast(r.rate as char(20)))) "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = concat(substr(a.paydate,1,8),'01') "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) end) from dual ) is not null then ( select (case when ( select concat('0',trim(cast(r.rate as char(20)))) "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) is not null then ( select concat('0',trim(cast(r.rate as char(20)))) "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno)  else ( select concat('0',trim(cast(r.rate as char(20)))) "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = concat(substr(a.paydate,1,8),'01') "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) end) from dual )  else '' end) 结算利率,    "
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
				+ " MONEY 帐户收支, "
				+ " (case "
				+ "	 when (select sum(MONEY) "
				+ "					 from LCInsureAccTrace r "
				+ "					where r.POLNO = a.POLNO "
				+ "						and r.PAYDATE >= '1900-01-01' "
				+ "						and r.PAYDATE < a.paydate) < 0 then "
				+ "		0 "
				+ "	 else "
				+ "		(select sum(MONEY) "
				+ "			 from LCInsureAccTrace r "
				+ "			where r.POLNO = a.POLNO "
				+ "				and r.PAYDATE >= '1900-01-01' "
				+ "				and r.PAYDATE < a.paydate)  "
				+ " end) 账户累计金额," 
				+ "        (select f.name "
				+ "  from laagent f, lccont b "
				+ " where f.agentcode = b.agentcode "
				+ " and b.contno = a.contno) 服务人员姓名 , "
				+ " (select f.agentcode "
				+ " from laagent f, lccont b "
				+ " where f.agentcode = b.agentcode "
				+ " and b.contno = a.contno) 业务代码 , "
				+ " (select concat(concat(f.mobile,'/'),phone) "
				+ " from laagent f, lccont b "
				+ " where f.agentcode = b.agentcode "
				+ " and b.contno = a.contno) 服务人员联系方式 ,moneytype "
				+ ",(select substr(b.managecom,1,6) from ldcom d,lccont b where b.contno=a.contno and d.comcode = b.managecom), "
				+" (select d.shortname from ldcom d,lccont b where b.contno=a.contno and d.comcode = b.managecom) mg, "
				+" (select d.name from lacom d,lccont b  where b.contno=a.contno and d.agentcom = b.agentcom),"
				+" (select codename from ldcode d,lccont b where b.contno=a.contno and d.code = b.salechnl and codetype = 'salechnl')sale, " 
				+" (select name from lcinsured c where c.contno = a.contno) 被保人姓名 "
				+ "	From LCInsureAccTrace a "
				+ " where  "
				+ " a.InsuAccNo = '000006' and a.contno='"+"?tContNo?"+"'"
				+ " and a.PAYDATE>='"+"?mLastCValiDate?"+"' and a.PAYDATE<'"+"?mCurCValiDate?"+"'"		
                + " order by  帐户变动日期,帐户收支 desc ";
		   
		logger.debug("执行SQL"+tSql);
		sqlbv3.sql(tSql);
		sqlbv3.put("mLastCValiDate", mLastCValiDate);
		sqlbv3.put("mCurCValiDate", mCurCValiDate);
		sqlbv3.put("tContNo", tContNo);
		SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorBLBillPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "统计期间没有记录";
			this.mErrors.addOneError(tError);
			return false;
		}
		//用于保存本次帐户收支的出入日期，现在帐户没法处理同一天的帐户进出情况。

		String tPreAccBala="";
		for (int j = 1; j <= tSSRS.MaxRow; j++) {
			String[] strfl = new String[27];
			if(j==1)
			{
				strfl[0] = tSSRS.GetText(j, 1);
				strfl[1] = tSSRS.GetText(j, 2);
				strfl[2] = tSSRS.GetText(j, 3);
				strfl[3] = tSSRS.GetText(j, 4);
				strfl[4] = tSSRS.GetText(j, 5);
				strfl[5] = tSSRS.GetText(j, 6);
				strfl[6] = tSSRS.GetText(j, 7);
				strfl[7] = tSSRS.GetText(j, 8);
				strfl[8] = tSSRS.GetText(j, 9);
				strfl[9] = BqNameFun.getFormat(tSSRS.GetText(j, 10));
				strfl[10] = tSSRS.GetText(j, 11);
				strfl[11] = tSSRS.GetText(j, 12);
				strfl[12] = tSSRS.GetText(j, 13);
				strfl[13] = BqNameFun.getFormat(tSSRS.GetText(j, 14));
			}else
			{
				strfl[0] = "";
				strfl[1] = "";
				strfl[2] = "";
				strfl[3] = "";
				strfl[4] = "";
				strfl[5] = "";
				strfl[6] = "";
				strfl[7] = "";
				strfl[8] = "";
				strfl[9] = "";
				strfl[10] = "";
				strfl[11] = "";
				strfl[12] = "";
				strfl[13] = "";
			}
			strfl[14] = tSSRS.GetText(j, 15);
			strfl[15] = tSSRS.GetText(j, 16);
            //如果是保费收入，利率置空
			if(tSSRS.GetText(j, 23).equals("BF"))
			{
				strfl[15]="";
			}
			strfl[16] = tSSRS.GetText(j, 17);
			strfl[17] = BqNameFun.getFormat(tSSRS.GetText(j, 18));

			if(j==1)
			{
				if(tSSRS.GetText(j, 14)==null||"null".equals(tSSRS.GetText(j, 14))||"0".equals(tSSRS.GetText(j, 14))) //说明是首期保费收入
				{
					strfl[18] = BqNameFun.getFormat(tSSRS.GetText(j, 18));	
				}	
				else
				{
					strfl[18] = getRealTimeMoney(tSSRS.GetText(j, 17),tSSRS.GetText(j, 18),tSSRS.GetText(j, 14));	
				}
			}
			else
			{
				strfl[18] = getRealTimeMoney(tSSRS.GetText(j, 17),tSSRS.GetText(j, 18),tPreAccBala);				
			}
			tPreAccBala=strfl[18];
			strfl[19] = tSSRS.GetText(j, 20);
			strfl[20] = tSSRS.GetText(j, 21);
			strfl[21] = tSSRS.GetText(j, 22);
			
			strfl[22] = tSSRS.GetText(j, 24);
			strfl[23] = tSSRS.GetText(j, 25);
			strfl[24] = tSSRS.GetText(j, 26);
			strfl[25] = tSSRS.GetText(j, 27);
			strfl[26] = tSSRS.GetText(j, 28);
			tMaxRow++;
			mListTable1.add(strfl);
		}
		}
        try
        {
    		String tData[][] = new String[tMaxRow][27];
        	for(int k=0;k<mListTable1.size();k++)
        	{
        		tData[k]=mListTable1.get(k);
        	}
            HashReport tHashReport = new HashReport();
            String tpath = "";
            LDSysVarDB tLDSysVarDB = new LDSysVarDB();
            tLDSysVarDB.setSysVar("BQExcelPath");
            if(!tLDSysVarDB.getInfo())
             {
                return false;
             }
            tpath = tLDSysVarDB.getSysVarValue();
            
            TransferData tempTransferData=new TransferData();
            //输入表头等信息
            tempTransferData.setNameAndValue("&date",StartDate+"至"+EndDate);
            tempTransferData.setNameAndValue("&operator",this.mGlobalInput.Operator);
            tempTransferData.setNameAndValue("&makedate",this.currentdate);
            //test 用
//            tpath= "D:\\temp\\";
//            strTemplatePath="E:\\work6\\work6.5\\ui\\f1print\\exceltemplate\\";
            tHashReport.outputArrayToFile1(tpath+mExCelFile+".xls",strTemplatePath+"InsuAccYearList.xls",tData,tempTransferData);
        }
        catch(Exception ex)
        {
           CError cError = new CError();
           cError.moduleName = "ReRateDetail";
           cError.functionName = "dealData";
           cError.errorMessage = "生成excel表时出错";
           this.mErrors.addOneError(cError);
           return false;
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
	// 数据准备
	private boolean prepareData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档

		//        if(mManageCom == null || "".equals(mManageCom))
		//        {
		//            mManageComName = getComName(mGlobalInput.ManageCom);
		//        }
		//        else
		//        {
		//            mManageComName = getComName(mManageCom);
		//        }
		//        mTextTag.add("Company", mManageComName);        //制表单位
		//
		//        FDate fDate = new FDate();
		//        GregorianCalendar tCalendar = new GregorianCalendar();
		//        tCalendar.setTime(fDate.getDate(StartDate));
		//        int tYear =tCalendar.get(Calendar.YEAR);
		//        int tMonth = tCalendar.get(Calendar.MONTH) + 1;
		//        int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
		//        StartDate = tYear + "年" + tMonth + "月" + tDay + "日";
		//        mTextTag.add("StartDate", StartDate);           //统计起期
		//
		//        tCalendar.setTime(fDate.getDate(EndDate));
		//        tYear =tCalendar.get(Calendar.YEAR);
		//        tMonth = tCalendar.get(Calendar.MONTH) + 1;
		//        tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
		//        EndDate = tYear + "年" + tMonth + "月" + tDay + "日";
		//        mTextTag.add("EndDate", EndDate);               //统计止期
		//        mTextTag.add("Count", mSumCount);               //合计件数
		//
		//        if (mTextTag.size() > 0)
		//        {
		//            tXmlExport.addTextTag(mTextTag);
		//        }
		String[] a_strEN = new String[18];
		tXmlExport.addListTable(mListTable1, a_strEN);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorPremBill.xml");
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
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		InsuAccYearListPrintBL tInsuAccYearListPrintBL = new InsuAccYearListPrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		TransferData tr = new TransferData();
		tr.setNameAndValue("StartDate", "2009-03-26");
		tr.setNameAndValue("EndDate", "2009-05-31");
		tr.setNameAndValue("ManageCom", "86");
		tr.setNameAndValue("Chanel", "1");
		tr.setNameAndValue("DateType", "1");

		tVData.add(tGlobalInput);
		tVData.add(tr);
		if (!tInsuAccYearListPrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
