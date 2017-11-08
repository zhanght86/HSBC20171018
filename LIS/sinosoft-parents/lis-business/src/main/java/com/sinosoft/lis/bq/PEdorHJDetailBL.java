package com.sinosoft.lis.bq;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LMRiskPayDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.f1print.FYDate;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import org.apache.log4j.Logger;

public class PEdorHJDetailBL 
{
	private static Logger logger = Logger.getLogger(PEdorHJDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    /** 数据操作字符串 */
    private String mOperate;
    private String sPolNo = "";
    private String sCurrentDate = PubFun.getCurrentDate();
    private String sCurrentTime = PubFun.getCurrentTime();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCContSchema  mLCContSchmea = new LCContSchema();
    private LJSGetEndorseSchema  mLJSGetEndorseSchema = new LJSGetEndorseSchema();
    private LPContStateSet mLPContStateSet = new LPContStateSet();
    private MMap mMap = new MMap();
    private String sRemark = ""; //备注信息
    private LCPolSet tLCPolSet = new LCPolSet();
    private double mGetMoney = 0.0;
    private double mPrem = 0.0;
    private double mTotalPrem = 0.0;
    private String mPayToDate = "";
    private LCDutySet mLCDutySet = new LCDutySet();
    private LCGetSet mLCGetSet = new LCGetSet();
    private LCPremSet mLCPremSet = new LCPremSet();
    private double[] tWPAmnt = new double[30];
    private String mPayDate = "";
    private String mGetNoticeNo = "";
	private String mBankCode = "";
	private String mBankAccNo = "";
	private String mBankAccName = "";
	private Date mLastPaytoDate = new Date(); // 现在的缴费对应日
	private Date mCurPayToDate = new Date(); // 下一次的缴费对应日
	LJSPayPersonSet aLJSPayPersonSet = new LJSPayPersonSet();
	private Reflections mRef = new Reflections();
	private int mMainPolYear = 0;
	private String mCValiDate = null;
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	private LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
	private int mPayCount;
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPPolSet mLPPolSet = new LPPolSet();
    public PEdorHJDetailBL() 
    {}
	
    
	public boolean submitData(VData cInputData, String sOperate) 
	{
		
		mInputData = (VData) cInputData.clone();
        mOperate = sOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        //校验数据
        if (!checkData())
        {
        	return false;
        }
        
        //核心处理类
        if (!dealDate())
        {
        	return false;
        }
        //
        if (!insertData())
        {
        	return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, ""))
        {
            mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError.buildErr(this,"数据提交失败!");
            return false;
        }
		return true;
	}
	
	/**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        mGlobalInput  =   (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        // mTransforData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        
        if (mLPEdorItemSchema == null)
        {
            CError.buildErr(this, "无法获取待操作保全项目信息！");
            return false;
        }

        if (mGlobalInput == null)
        {
            CError.buildErr(this, "无法获取用户登录机构信息！");
            return false;
        }
        //sPolNo = mLPEdorItemSchema.getPolNo(); 
        return true;
    }
    
    /**
     * 校验数据
     */
    public boolean checkData()
    {
    	//查询保全项目信息
    	LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        try
        {
            tLPEdorItemSet = tLPEdorItemDB.query();
        }
        catch (Exception ex)
        {
            CError.buildErr(this, "查询保全项目信息出现异常！");
            return false;
        }
        if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0)
        {
            mLPEdorItemSchema = tLPEdorItemSet.get(1);
        }
        else
        {
            CError.buildErr(this, "没有查询到保全项目数据！");
            return false;
        }
        
    	//查询保单信息表
    	LCContSet  tLCContSet = new LCContSet();
    	LCContDB  tLCContDB = new LCContDB();
    	tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
    	try
    	{
    		tLCContSet = tLCContDB.query();
    	}catch (Exception ex)
    	{
    		CError.buildErr(this, "查询保单信息出现异常！");
    		return false;
    	}
    	if(tLCContSet != null && tLCContSet.size() > 0)
    	{
    		mLCContSchmea = tLCContSet.get(1);
    	}
    	else
    	{
    		CError.buildErr(this, "没有查询到要处理的保单信息！");
    		return false;
    	}
    	//查询险种信息
    	LCPolSet tLCPolSet = new LCPolSet();
    	LCPolDB tLCPolDB = new LCPolDB();
    	tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
    	try
    	{
    		tLCPolSet = tLCPolDB.query();
    	}catch (Exception ex)
    	{
    		CError.buildErr(this, "查询险种信息出现异常！");
    		return false;
    	}
    	if (tLCPolSet != null && tLCPolSet.size() >0)
    	{
    		mLCPolSchema = tLCPolSet.get(1);
    	}
    	else
    	{
    		CError.buildErr(this, "没有查询到要处理的险种信息！");
    		return false;
    	}
    	return true;
    }
    
    /**
     * 核心处理
     * @return
     */
	public boolean dealDate()
	{
		//1. 求出恢复缴费的申请日期 
		String  sEdorAppDate = mLPEdorItemSchema.getEdorAppDate();
		String sGetNoticeNo = "";  //续期核销通知书号
		String sLastPayToDate= ""; //本期缴费对应日
		String sCurPayToDate = ""; //下期缴费对应日
		String sSumPayMoney = "";  //应缴的一期保费
		//2.查询续期抽当记录，求出第一个应缴日，以及应缴费的金额
		String tSql ="select to_char(LastPayToDate, 'YYYY-MM-DD'),  "
			 		+       "to_char(PayDate, 'YYYY-MM-DD'), "
			 		+       "PayCount, "
			 		+       "GetNoticeNo, "
			 		+       "to_char(CurPayToDate, 'YYYY-MM-DD'), "
			 		+       "(case when sumActuPayMoney is not null then sumActuPayMoney else 0 end) "       
			 		+  "From LJSPayPerson a "
			 		+ "where  exists "
			 		+ "(select 'X' "
			 		+          "from LCPol "
			 		+         "where MainPolNo = a.PolNo "
			 		+           "and ContNo = '?ContNo?') and paytype = 'ZC'" ;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
	      SSRS tSSRS = new SSRS();
	      ExeSQL tExeSQL = new ExeSQL();
	      try
	      {
	    	  tSSRS = tExeSQL.execSQL(sqlbv);
	      }catch (Exception ex)
	      {
	    	  CError.buildErr(this, "查询应收记录出现异常！");
	    	  return false;
	      }
	      
	      if (tSSRS == null || tSSRS.MaxRow <= 0) {
			// @@错误处理
			CError.buildErr(this, "无续期应收记录！");
			return false;
		}
		sLastPayToDate = tSSRS.GetText(1, 1);
		sGetNoticeNo = tSSRS.GetText(1, 4);
		sCurPayToDate = tSSRS.GetText(1, 5);
		sSumPayMoney = tSSRS.GetText(1, 6);
		
		
	       // 计算保单的生效对应日
	       String sCValiToDate = new String("");
	       FDate tFDate = new FDate(); 
	       sCValiToDate = tFDate.getString(tFDate.getDate(BqNameFun.getCValiMatchDate(sLastPayToDate,mLPEdorItemSchema.getEdorValiDate())));
	       //判断该保全项目的申请日期是否为最近的应缴日，及保单周年日
	       int  nYears = 1; //求出中间隔的期数
	       if (sCValiToDate != null && !sCValiToDate.equals("") && sCValiToDate.equals(mLPEdorItemSchema.getEdorValiDate()))
	       {
	    	   //当期按保全补退费收取
	    	   nYears = PubFun.calInterval(sLastPayToDate, sCValiToDate, "Y") + 1;
	       }
	       else
	       {
	    	   //如果期数不只一期
	    	   if (PubFun.calInterval(sLastPayToDate, sCValiToDate, "Y") > 1)
	    	   {
	    		   nYears = PubFun.calInterval(sLastPayToDate, sCValiToDate, "Y");
	    	   }
	       }
	       // 续期一期应缴的钱
	       double dSumPayMoney = 0.00;
	       if (sSumPayMoney != null && !sSumPayMoney.equals(""))
	       {
	    	   dSumPayMoney = Double.parseDouble(sSumPayMoney);
	       }
	       
	       //恢复交费应缴的钱 = 一期应缴的钱 * 经过的期数  (dSumPayMoney * nYears )
	       double dHJPayMoney = 0.00;
	       dHJPayMoney = dSumPayMoney * nYears ;
	      /* if (dHJPayMoney != 0)
	       {
	    	   //生产补退费记录
	    	   if (!initLJSGetEndorse ("BF", dHJPayMoney))
	    	   {
	    		   return false;
	    	   }
	       }
	       else
	       {
	    	   CError.buildErr(this, "没有需要缴费的信息");
	    	   return false;
	       }*/
	       
	        ExeSQL ts = new ExeSQL();
			String str = "select min(paycount) from ljspayperson where PolNo='?PolNo?' and substr(trim(PayPlanCode),1,6) <> '000000' order by paycount";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(str);
			sbv1.put("PolNo", mLCPolSchema.getPolNo());
			String str2 = "select min(paycount) from ljspayperson where PolNo='?PolNo?' and substr(trim(PayPlanCode),1,6) = '000000' order by paycount";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(str2);
			sbv2.put("PolNo", mLCPolSchema.getPolNo());
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			LJSPayPersonDB dLJSPayPersonDB = new LJSPayPersonDB();
			LJSPayPersonSet dLJSPayPersonSet = new LJSPayPersonSet();
			String sqlPay = "select * from LJSPayPerson where polno = '?polno?' and PayCount = ?PayCount? and substr(trim(PayPlanCode),1,6) <> '000000'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.put("polno", mLCPolSchema.getPolNo());
			sbv3.put("PayCount", ts.getOneValue(sbv1));
			logger.debug(sqlPay);
			String sqlPay2 = "select * from LJSPayPerson where polno = '?polno?' and PayCount = ?PayCount? and substr(trim(PayPlanCode),1,6) = '000000'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();

			sbv4.put("polno", mLCPolSchema.getPolNo());
			sbv4.put("PayCount", ts.getOneValue(sbv2));
			logger.debug(sqlPay2);
			sbv3.sql(sqlPay + " and paytype = 'ZC'");
			aLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sbv3);
			if (aLJSPayPersonSet.size() < 1) {
				sbv3=new SQLwithBindVariables();
				sbv3.sql(sqlPay + " and paytype = 'HJ'");
				sbv3.put("polno", mLCPolSchema.getPolNo());
				sbv3.put("PayCount", ts.getOneValue(sbv1));
				aLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sbv3);
			}
			if (aLJSPayPersonSet.size() < 1) {
				CError.buildErr(this, "该保单没有首期催收数据,不能复效!(LJSPayPerson)");
				return false;
			}
			sbv4.sql(sqlPay2 + " and paytype = 'ZC'");
			dLJSPayPersonSet = dLJSPayPersonDB.executeQuery(sbv4);
			if (dLJSPayPersonSet != null && dLJSPayPersonSet.size() > 0) {
				aLJSPayPersonSet.add(dLJSPayPersonSet);
			} else {
				sbv4=new SQLwithBindVariables();
				sbv4.sql(sqlPay2 + " and paytype = 'HJ'");
				sbv4.put("polno", mLCPolSchema.getPolNo());
				sbv4.put("PayCount", ts.getOneValue(sbv2));
				dLJSPayPersonSet = dLJSPayPersonDB.executeQuery(sbv4);
				if (dLJSPayPersonSet != null && dLJSPayPersonSet.size() > 0) {
					aLJSPayPersonSet.add(dLJSPayPersonSet);
				}
			}
			// 查询ljsperson表中所有要交的金额
			for (int i = 1; i <= aLJSPayPersonSet.size(); i++) {
				aLJSPayPersonSet.get(i).setAgentCode(mLCPolSchema.getAgentCode());
				aLJSPayPersonSet.get(i).setAgentCom(mLCPolSchema.getAgentCom());
				aLJSPayPersonSet.get(i).setAgentGroup(mLCPolSchema.getAgentGroup());
			}
			LJSPayPersonSchema tLJPayPersonSchema = new LJSPayPersonSchema();
			tLJPayPersonSchema = aLJSPayPersonSet.get(1);
			tLJPayPersonSchema.setPayType("HJ");
			mPayCount = tLJPayPersonSchema.getPayCount(); // 作为个人应收表标记使用
	       
	       
	       //生成应收数据
	       mCValiDate = mLCPolSchema.getCValiDate();
	       String tLimit = PubFun.getNoLimit(mLCPolSchema.getManageCom());
	       mGetNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
	       FDate aFDate = new FDate();
	       	//取得保单责任信息
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
			mLCDutySet = tLCDutyDB.query();
			if (mLCDutySet.size() < 1)
			{
				mErrors.addOneError("没有保单责任信息");
				return false;
			}
			// 取得保单领取信息
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(mLCPolSchema.getPolNo());
			mLCGetSet = tLCGetDB.query();
			if (mLCGetSet.size() < 1)
			{
				mErrors.addOneError("没有保单责任信息");
				return false;
			}
			// 取得当前所有保费项信息（每期总的交费金额）
			if (!setReceivablePrem(mLCPolSchema.getPolNo()))
			{
				return false;
			}
			mPayToDate = mLCPolSchema.getPaytoDate();
	       for (int i = 1; i <= nYears; i++)
			{
				if (i != 1 )
				{
					mGetMoney = 0.0;
					aLJSPayPersonSet.clear();
					if (!initLJSPaypersonSet(mLCPolSchema, mLCDutySet, mLCPremSet, mLCGetSet, tWPAmnt[i]))
					{
						CError.buildErr(this, "处理应首数据出错！");
						return false;
					}
					for (int k = 1; k <= aLJSPayPersonSet.size(); k++)
					{
						mGetMoney += aLJSPayPersonSet.get(k).getSumDuePayMoney();
					}
				}
				tWPAmnt[i] += mGetMoney;
				mPrem = mGetMoney ;
				mTotalPrem += mPrem;
							
				// 首期个人应收表信息处理  
				for (int k = 1; k <= aLJSPayPersonSet.size(); k++)
				{
					LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
					mRef.transFields(tLJSPayPersonSchema, aLJSPayPersonSet.get(k));
					tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
					tLJSPayPersonSchema.setPayType("HJ");
					tLJSPayPersonSchema.setPolNo(mLCPolSchema.getPolNo());
					tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
					// ===========add===liuxiaosong==2006-10-18=========================start========
					mMainPolYear = PubFun.calInterval(mCValiDate, tLJSPayPersonSchema.getLastPayToDate(), "Y");
					tLJSPayPersonSchema.setMainPolYear(mMainPolYear + 1); // 设置主险年度
					// ===========add===liuxiaosong==2006-10-18=========================end==========
					tLJSPayPersonSchema.setBankAccNo("");
					tLJSPayPersonSchema.setBankCode("");
					tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
					tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
					tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
					tLJSPayPersonSchema.setPayCount(mLCPremSet.get(1).getPayTimes() + 1);
	                //营改增 add zhangyingfeng 2016-07-14
	                //价税分离 计算器
	                TaxCalculator.calBySchema(tLJSPayPersonSchema);
	                //end zhangyingfeng 2016-07-14
					mLJSPayPersonSet.add(tLJSPayPersonSchema);
					if (!this.createLJSGetEndorseSchema(tLJSPayPersonSchema))
					{
						return false;
					}
				}
				// 计算新的交至日期
				mPayToDate = PubFun.calDate(mLCPolSchema.getPaytoDate(),  mLCPolSchema.getPayIntv(), "M", null);
				
				if (aFDate.getDate(mLCPolSchema.getPayEndDate()).before(
						aFDate.getDate(mPayToDate)))
				{
					mPayToDate = mLCPolSchema.getPayEndDate();
				}
				this.initDueLCPrem(1);
				this.initDueLCDuty();
				this.initDueLCPol();
			}
	       
	       LPPolSchema tLPPolSchema = new LPPolSchema();
			mRef.transFields(tLPPolSchema, mLCPolSchema);
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setLastRevDate(mLPEdorItemSchema.getEdorValiDate());
			mLPPolSet.add(tLPPolSchema);
			//生成LP表数据
			for (int i = 1; i <= mLCDutySet.size(); i++)
			{
				LPDutySchema tLPDutySchema = new LPDutySchema();
				mRef.transFields(tLPDutySchema, mLCDutySet.get(i));
				tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPDutySet.add(tLPDutySchema);
			}
			// 或者在lcprem中得到paystartdate
			for (int i = 1; i <= mLCPremSet.size(); i++)
			{
				LPPremSchema tLPPremSchema = new LPPremSchema();
				mRef.transFields(tLPPremSchema, mLCPremSet.get(i));
				tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPPremSet.add(tLPPremSchema);
			}
			for (int i = 1; i <= mLCGetSet.size(); i++)
			{
				LPGetSchema tLPGetSchema = new LPGetSchema();
				mRef.transFields(tLPGetSchema, mLCGetSet.get(i));
				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPGetSet.add(tLPGetSchema);
			}
	       
	       mLPEdorItemSchema.setStandbyFlag3(nYears+""); //存期数
	       mLPEdorItemSchema.setEdorState("1");
	       LPContSchema tLPContSchema= new LPContSchema();
	       PubFun.copySchema(tLPContSchema, mLCContSchmea);
	       tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
	       tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
	       //tLPContSchema.setDif(dHJPayMoney); //将钱放到dif 字段 又续期来核销 
	       tLPContSchema.setPaytoDate(mPayToDate);
	       tLPContSchema.setState("101"); //有效-缴费期内
	       
	     //修改保单状态为正常交费状态
	       if (!changePolState(mLCPolSchema.getContNo(), "AutoSlow", "0",mLPEdorItemSchema.getEdorValiDate())) 
	       {
	         return false;
	       }
	       LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
	       double getMoney = 0;
			for (int i = 1; i <= aLJSGetEndorseSet.size(); i++)
			{
				LJSGetEndorseSchema tLJS = new LJSGetEndorseSchema();
				tLJS.setSchema(aLJSGetEndorseSet.get(i));
				getMoney += tLJS.getGetMoney();
				tLJSGetEndorseSet.add(tLJS);
			}
		   //mLJSPaySet
//			LJSPayDB tLJSPayDB = new LJSPayDB();
//			tLJSPayDB.setOtherNo(mLPEdorItemSchema.getContNo());
//			mLJSPaySet = tLJSPayDB.query();
//			if(mLJSPaySet.size()==0){
//				tLJSPayDB.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
//				mLJSPaySet = tLJSPayDB.query();
//			}
//			
//			mLJSPaySet.get(1).setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
		   getMoney = BqNameFun.getBJMoney(getMoney);
		   mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");
	       mMap.put(mLJSPayPersonSet, "DELETE&INSERT");
	       mMap.put(mLJSPaySet, "UPDATE");
	       mMap.put(mLPContStateSet, "DELETE&INSERT");
	       mMap.put(tLPContSchema, "DELETE&INSERT");
	       mMap.put(mLPEdorItemSchema, "UPDATE");
	       mMap.put(mLPPolSet, "DELETE&INSERT");
	       mMap.put(mLPDutySet, "DELETE&INSERT");
	       mMap.put(mLPPremSet, "DELETE&INSERT");
	       mMap.put(mLPGetSet, "DELETE&INSERT");
	       
		return true;
	}
	
//	 准备催收保费项
	private void initDueLCPrem(int aPaySTime)
	{
		for (int i = 1; i <= mLCPremSet.size(); i++)
		{
			// mLCPremSet.get(i).setUrgePayFlag("N");
			mLCPremSet.get(i).setPayTimes(mLCPremSet.get(i).getPayTimes() + aPaySTime);
			mLCPremSet.get(i).setPaytoDate(mPayToDate);
		}
	}
	/**
     * 准备催收责任信息
     */
	private void initDueLCDuty()
	{
		for (int i = 1; i <= mLCDutySet.size(); i++)
		{
			mLCDutySet.get(i).setPaytoDate(mPayToDate);
		}
	}
	
	/**
     * 准备催收保单信息
     */
	private void initDueLCPol()
	{
		mLCPolSchema.setPaytoDate(mPayToDate);
		mLCPolSchema.setAppFlag("1");
	}
	
	private boolean createLJSGetEndorseSchema(LJSPayPersonSchema cLJSPayPersonSchema)
	{
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initLJSGetEndorse("BF", true);
		mRef.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
		tLJSGetEndorseSchema.setGetMoney(BqNameFun.getBJMoney(cLJSPayPersonSchema.getSumDuePayMoney()));
		tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		// 处理SubFeeOperationType
		if (cLJSPayPersonSchema.getPayPlanCode() != null && cLJSPayPersonSchema.getPayPlanCode().startsWith("000000"))
		{
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(cLJSPayPersonSchema.getPolNo());
			tLCPremDB.setDutyCode(cLJSPayPersonSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(cLJSPayPersonSchema.getPayPlanCode());
			if (!tLCPremDB.getInfo())
			{
				CError.buildErr(this, "查询交费计划类型失败!");
				return false;
			}
			if ("01".equals(tLCPremDB.getPayPlanType()) || "03".equals(tLCPremDB.getPayPlanType()))
			{

				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_InsurAddPremHealth + String.valueOf(cLJSPayPersonSchema.getPayCount()) );
			}
			else if ("02".equals(tLCPremDB.getPayPlanType()) || "04".equals(tLCPremDB.getPayPlanType()))
			{
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_InsurAddPremOccupation
						+ String.valueOf(cLJSPayPersonSchema.getPayCount())	);
			}
			else
			{
				CError.buildErr(this, "加费交费计划类型错误!");
				return false;
			}
		}
		else
		{
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem  + String.valueOf(cLJSPayPersonSchema.getPayCount()));
		}
		aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}
	/**
     * 生成交退费记录 aPolFlag = true 是传入险种信息
     * 
     * @return
     */
	private LJSGetEndorseSchema initLJSGetEndorse(String strfinType, boolean aPolFlag)
	{
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		mLJSGetEndorseSchema.setFeeFinaType("G001");
		mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), strfinType, mLPEdorItemSchema.getPolNo());
		if (finType.equals(""))
		{
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
			return null;
		}
		mLJSGetEndorseSchema.setFeeFinaType(finType);
		mLJSGetEndorseSchema.setDutyCode("0");
		mLJSGetEndorseSchema.setPayPlanCode("0");
		// 走保全交费财务流程
		mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		mLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		if (aPolFlag)
		{
			mRef.transFields(mLJSGetEndorseSchema, mLCPolSchema);
		}
		mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		mLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		mLJSGetEndorseSchema.setGetFlag("0");
		tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);
		return tLJSGetEndorseSchema;
	}
	//
	private boolean initLJSPaypersonSet(LCPolSchema aLCPolSchema, LCDutySet aLCDutySet, LCPremSet aLCPremSet,
			LCGetSet aLCGetSet, double aAmnt)
	{
//			该保单下的每个险种的每期交费应收记录
			getLJSPaypersonSet(aLCPolSchema, aLCPremSet);

		return true;
	}
	private boolean getLJSPaypersonSet(LCPolSchema aLCPolSchema, LCPremSet aLCPremSet)
	{
		FDate tFDate = new FDate();
		//改保单下的每个险种的每期交费应收记录
		for (int i = 1; i <= aLCPremSet.size(); i++)
		{
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = aLCPremSet.get(i);
			String tOperator = "";
			tOperator = "ZC";
			
			aLJSPayPersonSet.add(preperaLjspayperson(aLCPolSchema, tLCPremSchema, tOperator));
		}
		return true;
	}
	
	private LJSPayPersonSet preperaLjspayperson(LCPolSchema tLCPolSchema, LCPremSchema tLCPremSchema, String tOperator)
	{
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		String GrpPolNo = tLCPolSchema.getGrpPolNo();
		String AgentCode = tLCPolSchema.getAgentCode();
		String AgentGroup = tLCPolSchema.getAgentGroup();
		String AgentCom = tLCPolSchema.getAgentCom();
		String AgentType = tLCPolSchema.getAgentType();
		String ManageCom = tLCPolSchema.getManageCom();
		String ApproveCode = tLCPolSchema.getApproveCode();
		String ApproveDate = tLCPolSchema.getApproveDate();
		String ApproveTime = tLCPolSchema.getApproveTime();
		String RiskCode = tLCPolSchema.getRiskCode();
		/** @todo 8.1计算新的交至日期 */
		logger.debug("计算新的交费日期");
		logger.debug("原交费日期::::::::::::::" + tLCPolSchema.getPaytoDate());
		FDate tD = new FDate();
		String tLastPaytoDate = "";
		tLastPaytoDate = tLCPolSchema.getPaytoDate();
		
		//宽限期满的日期
		mPayDate = this.DealPayDate(RiskCode, tLastPaytoDate);
		//交费方式
		int PayInterval = 0;
		if (tLCPolSchema.getPayIntv() > 0)
		{
			PayInterval = tLCPolSchema.getPayIntv();
		}
		else
		{
			// 目前短期附加险都为一年期，应该用lcpol中的InsuYearFlag,InsuYear判断
			PayInterval = 12;
		}
		//Date tCurPayToDate = FinFeePubFun.calOFDate(tD.getDate(tLastPaytoDate), PayInterval, "M", tD
		//		.getDate(tLCPolSchema.getPayEndDate()));
		logger.debug("计算交费对应日的校正参数 EndDate =============" + tLCPolSchema.getPayEndDate());
		FYDate t = new FYDate(tLastPaytoDate);
		logger.debug(t.getOracleDate());
		//保单第一次应该交至的日期
		//String strNewPayToDate = tD.getString(tCurPayToDate);
		
		//logger.debug("现交至日期::::::::::::::" + strNewPayToDate);
		String strNewPayToDate = PubFun.calDate(tLastPaytoDate, 12, "M", null);
		// 计算保单年度
		String MainPolYearSql = "Select trunc(Months_between('?Date?',lcpol.CValiDate)/12)+1 From lcpol Where polno = '?polno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(MainPolYearSql);
		sqlbv.put("Date", t.getOracleDate());
		sqlbv.put("polno", tLCPolSchema.getMainPolNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		//主险的保单年度
		String tMainPolyear = tSSRS.GetText(1, 1);
		// 正常交费
		if (tOperator.equals("ZC"))
		{
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setLastPayToDate(tLastPaytoDate);
			//tLJSPayPersonSchema.setCurPayToDate(tCurPayToDate);
			tLJSPayPersonSchema.setCurPayToDate(strNewPayToDate);
			tLJSPayPersonSchema.setPayType("HJ");
			tLJSPayPersonSchema.setPayDate(mPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
			tLJSPayPersonSchema.setMakeDate(sCurrentDate);
			tLJSPayPersonSchema.setMakeTime(sCurrentTime);
			tLJSPayPersonSchema.setModifyDate(sCurrentDate);
			tLJSPayPersonSchema.setModifyTime(sCurrentTime);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
		}
		// 豁免
		else if (tOperator.equals("FREE"))
		{
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setLastPayToDate(mLastPaytoDate);
			tLJSPayPersonSchema.setCurPayToDate(mCurPayToDate);
			tLJSPayPersonSchema.setPayType("HJ");
			tLJSPayPersonSchema.setPayDate(mPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
			tLJSPayPersonSchema.setMakeDate(sCurrentDate);
			tLJSPayPersonSchema.setMakeTime(sCurrentTime);
			tLJSPayPersonSchema.setModifyDate(sCurrentDate);
			tLJSPayPersonSchema.setModifyTime(sCurrentTime);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
			LJSPayPersonSchema nLJSPayPersonSchema = new LJSPayPersonSchema();
			nLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			nLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			nLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			nLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			nLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			nLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			nLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			nLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			nLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			nLJSPayPersonSchema.setManageCom(ManageCom);
			nLJSPayPersonSchema.setAgentCode(AgentCode);
			nLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			nLJSPayPersonSchema.setAgentCom(AgentCom);
			nLJSPayPersonSchema.setAgentGroup(AgentGroup);
			nLJSPayPersonSchema.setAgentType(AgentType);
			nLJSPayPersonSchema.setPayAimClass("1");
			nLJSPayPersonSchema.setSumActuPayMoney((-1) * tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setSumDuePayMoney((-1) * tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setLastPayToDate(mLastPaytoDate);
			nLJSPayPersonSchema.setCurPayToDate(mCurPayToDate);
			nLJSPayPersonSchema.setPayType("HM");
			nLJSPayPersonSchema.setPayDate(mPayDate);
			nLJSPayPersonSchema.setBankCode(mBankCode);
			nLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			nLJSPayPersonSchema.setBankOnTheWayFlag("0");
			nLJSPayPersonSchema.setBankSuccFlag("0");
			nLJSPayPersonSchema.setApproveCode(ApproveCode);
			nLJSPayPersonSchema.setApproveDate(ApproveDate);
			nLJSPayPersonSchema.setApproveTime(ApproveTime);
			nLJSPayPersonSchema.setRiskCode(RiskCode);
			nLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
			nLJSPayPersonSchema.setMakeDate(sCurrentDate);
			nLJSPayPersonSchema.setMakeTime(sCurrentTime);
			nLJSPayPersonSchema.setModifyDate(sCurrentDate);
			nLJSPayPersonSchema.setModifyTime(sCurrentTime);
			tLJSPayPersonSet.add(nLJSPayPersonSchema);
		}
		//该险种下所有每期应缴钱数
		return tLJSPayPersonSet;
	}
	
	private String DealPayDate(String cRiskCode, String cPayToDate)
	{
		String tPayDate = "";
		/* 计算此险种的宽限期 */
		Date PayDate = new Date();
		FDate tD = new FDate();
		LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
		LMRiskPaySet tLMRiskPaySet = new LMRiskPaySet();
		tLMRiskPayDB.setRiskCode(cRiskCode);
		tLMRiskPaySet = tLMRiskPayDB.query();
		int Dayintv = 0;
		String DateType = "D";
		if (tLMRiskPaySet.size() > 0)
		{
			Dayintv = tLMRiskPaySet.get(1).getGracePeriod();
			DateType = tLMRiskPaySet.get(1).getGracePeriodUnit();
		}
		String tLastPaytoDate = cPayToDate;
		PayDate = FinFeePubFun.calOFDate(tD.getDate(tLastPaytoDate), Dayintv, DateType, null);
		tPayDate = tD.getString(PayDate);
		logger.debug("险种" + cRiskCode + " 宽限期为::::" + tPayDate);
		return tPayDate;
	}
	/**
     * 得到应收保费项
     * 
     * @param aPolNo
     * @return boolean
     */
	private boolean setReceivablePrem(String aPolNo)
	{
		//判断每个保单下的每个险种的每期应缴保费
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		//每个险种下的所以要交费的信息
		String i_sql = "select * from LCPrem where PolNo = '?aPolNo?' and PayStartDate <= to_date('?date?','YYYY-MM-DD') and PayEndDate >= to_date('?date?','YYYY-MM-DD') and UrgePayFlag = 'Y'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(i_sql);
		sqlbv.put("date", mLCPolSchema.getPaytoDate());
		sqlbv.put("aPolNo", aPolNo);
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		if (tLCPremSet.size() < 1)
		{
			mErrors.addOneError("保费项表中没有对应催交信息!");
			return false;
		}
		// mGetMoney = 0;
		for (int i = 1; i <= tLCPremSet.size(); i++)
		{
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(tLCPremSchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCPremSchema.getDutyCode());
			if (!tLCDutyDB.getInfo())
			{
				CError.buildErr(this, "查询责任表失败!");
				return false;
			}
			else
			{
				mLCPremSet.add(tLCPremSchema);
				//判断是否为豁免险种
				if ((tLCDutyDB.getFreeFlag() != null) && !tLCDutyDB.getFreeFlag().equals("1")
						&& (tLCDutyDB.getFreeRate() < 1) && (tLCDutyDB.getFreeRate() > 0))
				{
					FDate cFDate = new FDate();
					if (cFDate.getDate(mLCPolSchema.getPaytoDate()).before(cFDate.getDate(tLCDutyDB.getFreeEndDate())))
					{
						tLCPremSchema.setPrem(tLCPremSchema.getPrem() * (1 - tLCDutyDB.getFreeRate()));
					}
				}
				
			}
		}
		if (mLCPremSet.size() == 0)
		{ // 如果过滤后的保费项表记录数=0
			this.mErrors.addOneError("查询保险责任表失败，原因是:都为免交");
			return false;
		}
		return true;
	}
	 /**
     * 生成交退费记录
     * @return
     */
    private boolean initLJSGetEndorse(String strfinType, double dSumMoney)
    {
        LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

        Reflections tReflections = new Reflections();
        // mPayCount++;
        tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
        tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
        tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
        
        String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), strfinType, mLCPolSchema.getPolNo());
        if (finType == null || finType.trim().equals(""))
        {
            // @@错误处理
            CError.buildErr(this, "查询获取保全财务接口类型编码失败！");
            return false;
        }
        tLJSGetEndorseSchema.setFeeFinaType(finType);
        tLJSGetEndorseSchema.setPayPlanCode("0");
        tLJSGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
        // 走保全交费财务流程
        tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
        tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
        tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem);  //补缴保费
        tLJSGetEndorseSchema.setGetMoney(dSumMoney);
        tReflections.transFields(tLJSGetEndorseSchema, mLCPolSchema);
        tLJSGetEndorseSchema.setDutyCode("000000");
        tLJSGetEndorseSchema.setMakeDate(sCurrentDate);
        tLJSGetEndorseSchema.setMakeTime(sCurrentTime);
        tLJSGetEndorseSchema.setModifyDate(sCurrentDate);
        tLJSGetEndorseSchema.setModifyTime(sCurrentTime);
        tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);

        tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
        tLJSGetEndorseSchema.setGetFlag("0");
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchema(tLJSGetEndorseSchema);
        //end zhangyingfeng 2016-07-14
        mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
        
        return true;
    }

    /**
     * 改变险种的状态（注：是险种级状态,要循环修改）
     * @param tContNo
     * @param tStateType “AutoSlow”
     * @param tState    “0”
     * @param tNewStateDate 有效起期
     * @return boolean true--成功，false--失败。结果放在mLPContStateSet变量中（累计）
     */
    private boolean changePolState(String tContNo, String tStateType,String tState, String tNewStateDate)
    {
      try
      {
        LCContStateSchema tLCContStateSchema = null;
        LPContStateSchema tLPContStateSchema = null;
        //先查询当前状态是否是要改变的状态，如果是，则保持
        String tSql = "SELECT *"
            		+ " FROM LCContState"
            		+ " WHERE ContNo='?tContNo?'"
            		+   " and StateType= '?tStateType?'"  
            		+   " and State='?tState?'"
            		+   " and EndDate is null";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql);
        sqlbv.put("tContNo", tContNo);
        sqlbv.put("tStateType", tStateType);
        sqlbv.put("tState", tState);
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS = tExeSQL.execSQL(sqlbv);
        if (tSSRS != null && tSSRS.MaxRow > 0) 
        {
          //现在的状态就是要改变后的状态，所以，保持
          return true;
        }
        LCContStateSet tLCContStateSet = new LCContStateSet();
        LCContStateDB tLCContStateDB = new LCContStateDB();
//        tLCContStateDB.setContNo(tContNo);
//        tLCContStateDB.setStateType(tStateType);
        tSql = "select *"
             + " from LCContState"
             + " where ContNo='?tContNo?'"
             +   " and StateType='?tStateType?'"
             +   " and EndDate is null";
        try
        {
        	sqlbv=new SQLwithBindVariables();
            sqlbv.sql(tSql);
            sqlbv.put("tContNo", tContNo);
            sqlbv.put("tStateType", tStateType);
        	tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
        }catch (Exception ex)
        {
        	CError.buildErr(this, "查询轨迹信息出现异常！");
        	return false;
        }
        if (tLCContStateSet != null && tLCContStateSet.size() > 0) 
        {
            //查询现在状态，将此状态结束
            for(int i = 1;i <= tLCContStateSet.size();i++)
            {
              tLCContStateSchema = new LCContStateSchema();
              tLCContStateSchema = tLCContStateSet.get(i);
              tLCContStateSchema.setEndDate(tNewStateDate); //状态在前一天结束
              tLCContStateSchema.setOperator(mGlobalInput.Operator);
              tLCContStateSchema.setModifyDate(sCurrentDate);
              tLCContStateSchema.setModifyTime(sCurrentTime);
              tLPContStateSchema = new LPContStateSchema();
              PubFun.copySchema(tLPContStateSchema,tLCContStateSchema);
              tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
              tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
              mLPContStateSet.add(tLPContStateSchema);
              
              // 做一个判断，因为lccontstate 表中 StartDate 是主键，如果同一天进入缓缴 和恢复交费 会主键冲突，这种情况不置新轨迹
              if (mLPEdorItemSchema.getEdorValiDate().compareTo(tLCContStateSchema.getStartDate()) == 0)
              {
            	  // 不做处理
              }
              else
              {
            	  //新状态信息
                  LPContStateSchema xLPContStateSchema = new LPContStateSchema();
                  PubFun.copySchema(xLPContStateSchema,tLCContStateSchema);
                  xLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                  xLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                  xLPContStateSchema.setContNo(tContNo);
                  xLPContStateSchema.setStateType(tStateType);
//                  xLPContStateSchema.setStateType("Available");//modify by LH  2008-11-25 应该是置为有效起期
                  xLPContStateSchema.setState(tState);
                  xLPContStateSchema.setStartDate(tNewStateDate);
                  xLPContStateSchema.setEndDate("");
                  xLPContStateSchema.setOperator(mGlobalInput.Operator);
                  xLPContStateSchema.setMakeDate(sCurrentDate);
                  xLPContStateSchema.setMakeTime(sCurrentTime);
                  xLPContStateSchema.setModifyDate(sCurrentDate);
                  xLPContStateSchema.setModifyTime(sCurrentTime);
                  mLPContStateSet.add(xLPContStateSchema);
              }
              
            }
        }
        return true;
      }catch (Exception e) 
      {
        CError.buildErr(this,"修改险种状态时产生错误！保单号：" + tContNo);
        return false;
      }
    }
    
    
	public CErrors getErrors() 
	{
		return mErrors;
	}

	public VData getResult() 
	{
		return mResult;
	}
	
	/**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean insertData()
    {
        mResult.clear();
        mResult.add(mMap);

        return true;
    }


	

}
