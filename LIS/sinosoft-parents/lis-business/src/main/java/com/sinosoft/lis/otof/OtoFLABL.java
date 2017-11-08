package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import com.sinosoft.lis.db.LAAssuMoneyDB;
import com.sinosoft.lis.db.LAJAGetAssuMoneyDB;
import com.sinosoft.lis.db.LALinkupWageDB;
import com.sinosoft.lis.db.LAWageDB;
import com.sinosoft.lis.db.LITranInfoDB;
import com.sinosoft.lis.db.LIVertifyDB;
import com.sinosoft.lis.db.OfinaStorDataDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAssuMoneySchema;
import com.sinosoft.lis.schema.LAJAGetAssuMoneySchema;
import com.sinosoft.lis.schema.LALinkupWageSchema;
import com.sinosoft.lis.schema.LAWageSchema;
import com.sinosoft.lis.schema.LITranErrSchema;
import com.sinosoft.lis.schema.LITranInfoSchema;
import com.sinosoft.lis.schema.LIVertifySchema;
import com.sinosoft.lis.schema.OFInterfaceSchema;
import com.sinosoft.lis.schema.OfinaStorDataSchema;
import com.sinosoft.lis.vschema.LAAssuMoneySet;
import com.sinosoft.lis.vschema.LAJAGetAssuMoneySet;
import com.sinosoft.lis.vschema.LALinkupWageSet;
import com.sinosoft.lis.vschema.LAWageSet;
import com.sinosoft.lis.vschema.LITranErrSet;
import com.sinosoft.lis.vschema.LITranInfoSet;
import com.sinosoft.lis.vschema.OFInterfaceSet;
import com.sinosoft.lis.vschema.OfinaStorDataSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class OtoFLABL
{
private static Logger logger = Logger.getLogger(OtoFLABL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	private String mStartDate = "";
	private String mEndDate = "";
	private String mStatYear = "";
	private String mStatMon = "";
	private String mToday = "";
	private int mTime = 0;
	private String DateFlag = "0";
	private String mInputDate = ""; // 界面传入的记帐日期
	private GlobalInput mGlobalInput = new GlobalInput();
	private String cManageCom = ""; // 提取管理机构\
	private String xManageCom = ""; // 提取管理机构\

	private String tmoney = ""; // >金额</money>
	private String mBillID = ""; // 对应凭证科目，便于查找 == 凭证类别+凭证分类(一张凭证内部有多个分类的情况)+内部编码
	private String mReversedStatus = ""; // 冲消状态
	private String mOrigRowID = ""; // 被冲消的行
	private String mReversedRowID = ""; // 冲消生成的行
	private String mCurrencyCode = ""; // 币别
	private String mVoucherType = ""; // 凭证类别
	private String mManageCom = ""; // 核算单位代码
	private String mSegment2 = ""; // 成本中心
	private String mAccountCode = ""; // 科目代码
	private String mAccountSubCode = ""; // 科目明细代码
	private String mSaleChnl = ""; // 销售渠道
	private String mRiskCode = ""; // 保险产品代码
	private String mTransDate = ""; // 事务日期
	private String mAccountingDate = ""; // 记帐日期
	private int mMatchID = 0; // 借贷关系key值
	private String mBatchNo = ""; // 批次号
	private String mEnteredDR = ""; // 事务借计金额
	private String mEnteredCR = ""; // 事务贷计金额
	private String mHeadDescription = ""; // 日记帐摘要
	private String mLineDescription = ""; // 行记帐摘要
	private String mPolNo = ""; // 保单号
	private String mContNo = ""; // 合同号
	private String mInsuredName = ""; // 被保险人姓名
	private String mBussNo = ""; // 收付款单据号
	private String mAttribute5 = ""; // 行为明细类别
	private String mAttribute6 = ""; // 代理机构（航意险应收凭证），业务员代码（业务员直佣）
	private String mAttribute7 = ""; //投保人姓名 （业务员直佣）
	private String mAttribute8 = ""; //手续费支付比例（直佣比例乘折扣）（业务员直佣）
	private Date dbdate;
	private Date dedate;

	// 对表的定义
	private OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
	private LITranInfoSet mLITranInfoSet = new LITranInfoSet();
	private LITranErrSet mLITranErrSet = new LITranErrSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();
	private String tRecordNo;     //进行锁表的管理机构


	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		logger.debug("--- OtoFLABL begin ---");
		mInputData = (VData)cInputData.clone();
		this.mOperate = cOperate;

		try
		{
			if(!cOperate.equals("PRINT"))
			{
				CError.buildErr(this, "不支持的操作字符串");
				return false;
			}

			initVar(); // 避免getinputdata中的数据被冲掉
			// 得到外部传入的数据，将数据备份到本类中
			if(!getInputData(cInputData))
			{
				return false;
			}
			if(!checkData())
			{
				CError.buildErr(this, "校验数据失败！");
				return false;
			}

     //为提高财务凭证提取效率，采用“三级机构一提交”方式，即：循环每个三级机构，根据此三级机构获取数据并提交
			String tComQuerySql = "select distinct(comcode) from ldcom where comcode Like concat('?xManageCom?','%') "
				 				     + "and char_length(Trim(comcode))=6";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tComQuerySql);
			sqlbv.put("xManageCom", xManageCom);
            ExeSQL tExeQuerySQL = new ExeSQL();
            SSRS tSSRSQuery = tExeQuerySQL.execSQL(sqlbv);

            for (int q=1;q<=tSSRSQuery.MaxRow;q++)
            	{
            		cManageCom = tSSRSQuery.GetText(q,1);
            		
            	   	mLITranErrSet.clear();
            	   	mLITranInfoSet.clear();
            	   	mOFInterfaceSet.clear();
            	   	
            		//为保证每个三级机构的dbdate和dedate正确，需要将dbdate和dedate重置（dealData函数会将dbdate加一天，以保证按照时间循环处理）
            		if(mTime==82 || mTime==83)
            		{
            			FDate chgdate = new FDate();
            			dbdate = chgdate.getDate(mStartDate);
            			dedate = chgdate.getDate(mEndDate);
            		}
            		else
            		{
            			String mDate = mStatYear+"-"+mStatMon+"-01";
            			FDate chgdate = new FDate();
            			dbdate = chgdate.getDate(mDate);
            			dedate = dbdate;
            		}
            		
            		if(!dealData())
                	 	{
                	 		continue;
                	 	}
                	 else
                	 	{
                	 		prepareOutputData();
                    	 	PubSubmit ps = new PubSubmit();
                    	    
                    	 	if(!ps.submitData(mInputData))
                    	 		{
                    	 			mErrors.copyAllErrors(ps.mErrors);
                    	 			CError.buildErr(this, "提交数据库失败!");
                    	 			return false;
                    	 		}
                	 	}
                	 	
                 	}

		}

		catch(Exception ex)
		{
			ex.printStackTrace();
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}
		return true;
	}

	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		Integer itemp = (Integer)mTransferData.getValueByName("itemp");
		mTime = itemp.intValue();
		DateFlag = (String)mTransferData.getValueByName("DateFlag");
		mInputDate = (String)mTransferData.getValueByName("mInputDate"); // 记账日期
		xManageCom = (String)mTransferData.getValueByName("cManageCom"); // 提取管理机构
		
		if(mTime==82 || mTime==83)
		{
			mStartDate = (String)mTransferData.getValueByName("mStartDate");
			mEndDate = (String)mTransferData.getValueByName("mEndDate");
			FDate chgdate = new FDate();
			dbdate = chgdate.getDate(mStartDate);
			dedate = chgdate.getDate(mEndDate);
			
			if(mStartDate.equals(""))
			{
				CError.buildErr(this, "没有起始日期!");
				return false;
			}

			if(mEndDate.equals(""))
			{
				CError.buildErr(this, "没有终止日期!");
				return false;
			}
		}
		else
		{
			mStatYear = (String)mTransferData.getValueByName("mStatYear");
			mStatMon = (String)mTransferData.getValueByName("mStatMon");
			mStatMon = PubFun.LCh(mStatMon, "0", 2);
			String mDate = mStatYear+"-"+mStatMon+"-01";
			FDate chgdate = new FDate();
			dbdate = chgdate.getDate(mDate);
			dedate = dbdate;
			if(mStatYear.equals(""))
			{
				CError.buildErr(this, "没有统计年!");
				return false;
			}

			if(mStatMon.equals(""))
			{
				CError.buildErr(this, "没有统计月!");
				return false;
			}
			mStartDate = mStatYear+"-"+mStatMon+"-01";
		}
		logger.debug("凭证编码：" + mTime);




		if(mGlobalInput == null)
		{
			CError.buildErr(this, "没有得到足够的信息!");
			return false;
		}
		return true;
	}
	
	
	private boolean checkData()
	{  	      		
        mBatchNo = PubFun1.CreateMaxNo("OTOF","20");
		mLITranErrSet.clear();
		mLITranInfoSet.clear();

		return true;
	}
	
	
	private boolean dealData()
	{
		while(dbdate.compareTo(dedate) <= 0)
		{
			FDate chgdate = new FDate();
			mToday = chgdate.getString(dbdate);
	        if(mTime==82 || mTime==83)
	        {
	        	tRecordNo=cManageCom+new SimpleDateFormat("yyyyMMdd").format(dbdate)+String.valueOf(mTime);
	        }
	        else if(mTime==102)
	        {
	        	
	        	tRecordNo=cManageCom+PubFun.getCurrentDate().substring(0,4).trim()+PubFun.getCurrentDate().substring(5,7).trim()
	        	          +mStatYear+mStatMon+String.valueOf(mTime);
	        }
	        else
	        {
	        	tRecordNo=cManageCom+mStatYear+mStatMon+"01"+String.valueOf(mTime);
	        }
	    	//调用并发控制程序
	    	if(!mLock.lock(tRecordNo, "LF1001", mGlobalInput.Operator))
	    	{
				CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;
	    	}
	    	
			// 准备所有要打印的数据
			if(!getVoucherData(mTime))
			{
				CError.buildErr(this, "凭证提取错误!");
				return false;
			}
			dbdate = PubFun.calDate(dbdate, 1, "D", null);
		}

		return true;
	}
	
/**
 * 凭证提取的分类 
 **/	
	private boolean getVoucherData(int i)
	{
		try
		{
			switch(i)
			{
	            case 10:                    //业务员佣金
	                if (!PayCommionJ())     return false;
	                break;
	            case 11:                    //续收员佣金
	                if (!PayReAgentJ())     return false;
	                break;
	            case 12:                    //续收督导津贴
	                if (!PayAdmiAgent()) 	return false;
	                break;
	            case 21:                    //西点工程佣金
	                if (!PayAgentXiDian()) 	return false;
	                break;
	            case 22 :
	                if (!PayReAgency()) 	return false;
	                break;                  //中介督导津贴
	            case 62:
	                if (!PayYCommionJ()) 	return false;
	                break;                       //预提佣金
	            case 81 :
	            	if(!getReBranch())  	 return false;
	            	break; 
	            	                            //收展佣金
	            case 82 :
	            	if(!getAgentChargeFee())  return false;
	            	break;                           //业务员押金收费
	            case 83 :
	            	if(!getAgentChargePay())  return false;
	            	break;                           //业务员押金付费
	            	
	            case 102 :
	            	if(!PayLWage())  return false;
	            	break;                           //系统外衔接资金
	            	
	            case 142 :
	            	if(!PayAgencyFee()) return false; 
	            	break;                           //中介手续费
	            case 162 :
	            	if(!PayBanksFee()) return false; 
	            	break;                           //银代手续费
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CError.buildErr(this, ex.toString());
			return false;
		}

		return true;
	}
	

	
    /**业务员佣金应付操作
     * 
     * @return
     */
    private boolean PayCommionJ()
    {
        String tCalDate[] = new String[2];
        String mStartDate = mStatYear+"-"+mStatMon+"-01";
        tCalDate = PubFun.calFLDate(mStartDate);
         
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);
        
       
       String pManageCom = cManageCom;       
 	     String ExistSql= "Select count(*),max(bussno),min(MatchID) From Ofinterface Where 1 = 1 And ReversedStatus='0' and Vouchertype='10' " 
		     + "and transdate='?transdate?' and ManageCom = '?pManageCom?'";
 	     SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
 	     sqlbv1.sql(ExistSql);
 	     sqlbv1.put("transdate", mStatYear+"-"+mStatMon+"-01");
 	     sqlbv1.put("pManageCom",pManageCom);
  	   ExeSQL tExistSQL = new ExeSQL();
  	   SSRS tExistSSRS1 = tExistSQL.execSQL(sqlbv1);
  	   
  	   if(!"0".equals(tExistSSRS1.GetText(1, 1)))
  	   {
  		   dealError("Ofinterface", tExistSSRS1.GetText(1, 2), tExistSSRS1.GetText(1, 2), "013", tExistSSRS1.GetText(1, 3), "管理机构为"+pManageCom+"，"+mStatYear+"年"+mStatMon+"月的业务员佣金凭证已经提取");
  		   return true;
  	   }
  	   
  	   
  	   LAWageDB tLAWageDB = new LAWageDB();
  	   String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWage "
                     + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?pManageCom?','%') "
                     + "and branchtype='1' ";
  	   SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
  	   sqlbv2.sql(tComSql);
  	   sqlbv2.put("tWageNo", tWageNo);
  	   sqlbv2.put("pManageCom", pManageCom);
  	   ExeSQL ttExeSQL = new ExeSQL();
  	   SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv2);
      

  	   for (int k=1;k<=tSSRS1.MaxRow;k++)
  	   {
  		   String tManageCom = tSSRS1.GetText(k,1);
          
          
  		   //附加佣金提取
  		   String tSql = "select * from LAWage "
  			   			+ "where IndexCalNo='?tWageNo?' and ManageCom like concat('?tManageCom?','%') "
  			   			+ "and branchtype='1' and f25>=0 and state='1' ";
  		   SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
  		   sqlbv3.sql(tSql);
  		   sqlbv3.put("tWageNo", tWageNo);
  		   sqlbv3.put("tManageCom", tManageCom);
  		   logger.debug(tSql);
  		   LAWageSet tLAWageSet = tLAWageDB.executeQuery(sqlbv3);
  		   if (tLAWageSet.size()==0)
  		   {
  			   dealError("LAWage", tWageNo, tRecordNo, "014", "010", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"没有相应的业务员附佣记录");
  			   return true;
  		   }
  		   double tSumMoney;
  		   double[] tWage;
  		   double[] tTax;
  		   String[] tWageItem;
  		   String[] tTaxItem;
	            //新法薪资
	            for (int i = 1; i <= tLAWageSet.size(); i++)
	            {
	                LAWageSchema tLAWageSchema = new LAWageSchema();
	                tLAWageSchema.setSchema(tLAWageSet.get(i));
	                if (tLAWageSchema.getState() == null)
	                {
	                	dealError("LAWage", tWageNo, tRecordNo, "015", "010", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的业务员佣金记录审核状态为空");
	                    return true;
	                }
	
	                if (tLAWageSchema.getState().equals("0"))
	                {
	                	dealError("LAWage", tWageNo, tRecordNo, "015", "010", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的业务员佣金记录未审核");
	                    return true;
	                }
	            }

	//            tWage = new double[21];
	            tWage = new double[24];
	//            tTax = new double[3];
	            tTax = new double[2];
	            for (int i = 1; i <= tLAWageSet.size(); i++)
	            {
	                LAWageSchema tLAWageSchema = new LAWageSchema();
	                tLAWageSchema.setSchema(tLAWageSet.get(i));
	                tWage[0] = tWage[0] + tLAWageSchema.getF06();               //全勤奖金
	                tWage[1] = tWage[1] + tLAWageSchema.getF07();               //转正奖金
	                tWage[2] = tWage[2] + tLAWageSchema.getF08();               //月度销售奖
	                tWage[3] = tWage[3] + tLAWageSchema.getF10();               //继续率奖金
	                tWage[4] = tWage[4] + tLAWageSchema.getF11();               //个人突出贡献奖
	                tWage[5] = tWage[5] + tLAWageSchema.getF09();               //增员辅导津贴
	                tWage[6] = tWage[6] + tLAWageSchema.getF17();               //客户服务奖
	                tWage[7] = tWage[7] + tLAWageSchema.getF12();               //永续经营奖
	                tWage[8] = tWage[8] + tLAWageSchema.getF13();               //组职务底薪
	                tWage[9] = tWage[9] + tLAWageSchema.getF14();               //部职务底薪
	                tWage[10] = tWage[10] + tLAWageSchema.getF15();             //区职务底薪
	                tWage[11] = tWage[11] + tLAWageSchema.getF16();             //总监职务底薪
	                tWage[12] = tWage[12] + tLAWageSchema.getF22();             //衔接薪资
	                tWage[13] = tWage[13] + tLAWageSchema.getF30();             //加扣款1
	                tWage[14] = tWage[14] + tLAWageSchema.getK11();             //差惩款
	                tWage[15] = tWage[15] - tLAWageSchema.getK21();             //差勤扣款
	                tWage[16] = tWage[16] + tLAWageSchema.getLastMoney();       //上期余额
	                tWage[17] = tWage[17] + tLAWageSchema.getK12();             //加扣款2
	                //zy 2009-10-27 由于加扣款6采用实物方式进行支付，财务采用手工记账方法（发票作为原始凭证）
	//                tWage[18] = tWage[18] + tLAWageSchema.getF19();             //推动费加扣款
	//                tWage[19] = tWage[19] + tLAWageSchema.getF18();             //推动费奖金加款
	//                tWage[20] = tWage[20] + tLAWageSchema.getF21();             //加扣款7
	//                tWage[21] = tWage[21] + tLAWageSchema.getF24();             //zy 2009-08-13 新增育成奖
	                tWage[18] = tWage[18] + tLAWageSchema.getF18();             //推动费奖金加款
	                tWage[19] = tWage[19] + tLAWageSchema.getF21();             //加扣款7
	                tWage[20] = tWage[20] + tLAWageSchema.getF24();             //zy 2009-08-13 新增育成奖
	                tWage[21] = tWage[21] + tLAWageSchema.getF27();             //综拓FYC
	                tWage[22] = tWage[22] + tLAWageSchema.getF28();             //综拓管理津贴
	                tWage[23] = tWage[23] + tLAWageSchema.getF29();             //综拓助理津贴

	                
	
	                tTax[0] = tTax[0] + tLAWageSchema.getK01();                 //营业税及附加
	                tTax[1] = tTax[1] + tLAWageSchema.getK02();                 //个人所得税
	                //zy 2009-11-26 由于加扣款6的税已在营业税中体现，所以此处取消加扣款6的税
	//                tTax[2] = tTax[2] + tLAWageSchema.getF20();                 //推动费加扣款
	            }
	
	            tWageItem = new String[24];
	//            tWageItem = new String[22];
	//            tTaxItem = new String[3];
	            tTaxItem = new String[2];
	            tWageItem[0] = "全勤奖金";
	            tWageItem[1] = "转正奖";
	            tWageItem[2] = "月度销售奖";
	            tWageItem[3] = "继续率奖金";
	            tWageItem[4] = "个人突出贡献奖";
	            tWageItem[5] = "增员辅导津贴";
	            tWageItem[6] = "客户服务奖";
	            tWageItem[7] = "永续经营奖";
	            tWageItem[8] = "组职务底薪";
	            tWageItem[9] = "部职务底薪";
	            tWageItem[10] = "区职务底薪";
	            tWageItem[11] = "总监职务底薪";
	            tWageItem[12] = "同业衔接资金(支持薪资)";                          //衔接薪资
	            tWageItem[13] = "加扣款";
	            tWageItem[14] = "奖惩款项";
	            tWageItem[15] = "差勤扣款";
	            tWageItem[16] = "业务员佣金上月余额";                             //上期余额
	            tWageItem[17] = "同业衔接资金(加扣款2)";
	//            tWageItem[18] = "推动费加扣款";
	//            tWageItem[19] = "推动费奖金加款";
	//            tWageItem[20] = "机构发展资金(加扣款7)";
	//            tWageItem[21] = "育成奖";
	            tWageItem[18] = "推动费奖金加款";
		        tWageItem[19] = "机构发展资金(加扣款7)";
		        tWageItem[20] = "育成奖";
		        tWageItem[21] = "综拓FYC";
		        tWageItem[22] = "综拓管理津贴";
		        tWageItem[23] = "综拓助理津贴";
		        
	            tTaxItem[0] = "代理人营业税";
	            tTaxItem[1] = "代理人个人所得税";
	//            tTaxItem[2] = "推动费加扣款";
	
	            initVar();
	            mMatchID ++ ;
	            mManageCom = tManageCom;
	            mSegment2 = tManageCom.concat("00");
	            mAccountingDate = PubFun.getCurrentDate();
	            mTransDate =mStartDate;
	            mBillID = "1001";
	            mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
	            mPolNo = mBussNo;
	            tSumMoney = 0;
	            for(int j=0 ; j<tWage.length ; j++)
	            {
	            	//如果为综拓的科目，则相应渠道为综拓渠道
	            	 if(tWageItem[j].indexOf("综拓") > -1)
	                {
	            		 mSaleChnl="09";  //综拓
	                }
	            	 else
	            	 {
	            		 mSaleChnl="02";  //个险
	            	 }
	                if (tWage[j]==0)
	                {
	                	dealError("LAWage", mBussNo, mPolNo, "004", "010", tWageItem[j]+"的金额为0");
	                    continue;
	                }
	                tSumMoney = tSumMoney + tWage[j];
	
	                tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]));
	                mAccountCode = "佣金支出-附加佣金(业务员佣金)-"+tWageItem[j];   //新附加佣金业务员佣金支出
	                if(tWageItem[j].equalsIgnoreCase("差勤扣款"))
	                {
	                    mAccountCode = "其他应付款-"+tWageItem[j];               //附加佣金业务员佣金支出
	                }
	                if(tWageItem[j].indexOf("推动费") > -1)
	                {
	                    mAccountCode = "佣金支出-附加佣金(推动费)-"+tWageItem[j];  //推动费
	                }
	                mEnteredDR = String.valueOf(tmoney);
	                mEnteredCR = "";
	                mHeadDescription = mManageCom + "的" + tWageItem[j];
	                if (isExitInTab(mBussNo,mBussNo,mBillID))
	                    break;
	                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                if(tOFInterfaceSchema != null)
	                    mOFInterfaceSet.add(tOFInterfaceSchema);
	                dealLITranInfo();
	            }

	            mBillID = "1002";
	            double tTaxMoney=0;
	            for(int j=0;j<tTax.length;j++)
	            {
	            	mSaleChnl="02";  //个险
	                if (tTax[j]==0)
	                {
	                	dealError("LAWage", mBussNo, mPolNo, "004", "010", tTaxItem[j]+"的金额为0");
	                    continue;
	                }
	                tTaxMoney = tTaxMoney + tTax[j];
	                tmoney = new DecimalFormat("0.00").format(new Double(tTax[j]));
	                 mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
	                if(tTaxItem[j].equalsIgnoreCase("推动费加扣款"))
	                {
	                    mAccountCode = "应付佣金-"+tTaxItem[j];
	                }
	
	                mEnteredDR = "";
	                mEnteredCR = String.valueOf(tmoney);
	                mHeadDescription = mManageCom + "的" + tTaxItem[j];
	                if (isExitInTab(mBussNo,mBussNo,mBillID))
	                    break;
	                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                if(tOFInterfaceSchema != null)
	                    mOFInterfaceSet.add(tOFInterfaceSchema);
	                dealLITranInfo();
	            }
	
	            double tMoneyShould = tSumMoney-tTaxMoney;
	            if (tMoneyShould>0)
	            {
	                tmoney = new DecimalFormat("0.00").format(new Double(tMoneyShould));
	
	                mBillID = "1003";
	                mAccountCode = "应付佣金";                                  //应付附加佣金业务员佣金
	                mEnteredDR = "";
	                mEnteredCR = String.valueOf(tmoney);
	                mHeadDescription = mManageCom + "的" + "应付佣金支出";
	                if (!isExitInTab(mBussNo,mBussNo,mBillID))
	                {
	                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                    if(tOFInterfaceSchema != null)
	                        mOFInterfaceSet.add(tOFInterfaceSchema);
	                    dealLITranInfo();
	                }
	            }
	        }
	
	        //提取直接佣金
	        tComSql = "select distinct(substr(ManageCom,1,6)) from LACommision "
	                + "where WageNo='?tWageNo?' and ManageCom like concat('?pManageCom?','%')";
	        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	        sqlbv4.sql(tComSql);
	        sqlbv4.put("tWageNo", tWageNo);
	        sqlbv4.put("pManageCom", pManageCom);
	
	        SSRS tSSRS2 = ttExeSQL.execSQL(sqlbv4);
	        for (int k=1;k<=tSSRS2.MaxRow;k++)
	        {
	        	String tManageCom=tSSRS2.GetText(k,1);
	        	
	        	//意外险，短期健康险，提取信息精确到合同级
	        	initVar();
	        	mMatchID ++ ;
	        	mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
	        	mManageCom = tManageCom;
	            mAccountingDate = PubFun.getCurrentDate();
	            mTransDate = mStatYear+"-"+mStatMon+"-01";
	            mSegment2 = tManageCom.concat("00");
	            mSaleChnl="02";  
	            double mSumMoney = 0;
	            
	            String tSql = "select riskcode,payyear,BranchType,contno,p11,agentcode,Max(fycrate),sum(Fyc)  from  "
	                        + "(select riskcode,payyear,BranchType,contno,p11,agentcode,fycrate,Fyc "
	                        + "from LACommision where ManageCom like concat('?tManageCom?','%') "
	                        + "and Fyc!=0 and riskmark='0' and commdire='1' and branchtype='1' "
	                        + "and agentcode in(select agentcode from lawage where state='1' and "
	                        + "indexcalno='?tWageNo?' and f25>=0) and caldate>='?caldate1?' and caldate<='?caldate2?'"
	                        + " and riskcode in (select riskcode from lmriskapp a where a.RiskType='H' And riskcode In  "
	                        + "(select riskcode from lmriskapp where RiskPeriod In ('S','M')) "
	                        + " Union (select riskcode From lmriskapp where RiskType='A'))) g "
	                        + " group by riskcode,payyear,BranchType,contno,p11,agentcode "; 
	            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	            sqlbv5.sql(tSql);
	            sqlbv5.put("tManageCom", tManageCom);
	            sqlbv5.put("tWageNo", tWageNo);
	            sqlbv5.put("caldate1", tCalDate[0]);
	            sqlbv5.put("caldate2", tCalDate[1]);
	            	
	            logger.debug(tSql);
	            ExeSQL tExeSQL = new ExeSQL();
	            SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
	            
	            //产生借方数据
	            for (int i=1;i<=tSSRS.MaxRow;i++)
	            {
	
	                tmoney = String.valueOf(tSSRS.GetText(i,8));
	                mSumMoney = mSumMoney + Double.parseDouble(tmoney);
	                
	                if (Double.valueOf(tmoney).doubleValue() == 0)
	                {
	                	dealError("LACommision", mManageCom+tSSRS.GetText(i, 1), tSSRS.GetText(i, 3), "004", "010", "直接佣金金额为0");
	                    continue;
	                }
	                mRiskCode = tSSRS.GetText(i,1);  //存放险种编码
	                mPolNo = tSSRS.GetText(i,4);     //存放合同号
	                mSaleChnl = tranAgentmSaleChnl(tSSRS.GetText(i,3).trim());
	                mAttribute6 = tSSRS.GetText(i,6);  //存放业务员代码
	                mAttribute7 = tSSRS.GetText(i,5);  //存放投保人姓名
	                mAttribute8 = tSSRS.GetText(i,7);  //存放手续费支付比例
	               
	                String tPayYear = tSSRS.GetText(i,2);
	                
	                if(tPayYear.equals("0"))
	                    tPayYear = "首期佣金";
	                else
	                    tPayYear = "续期佣金";
	
	
	                mBillID = "1004";
	                mAccountCode = "佣金支出-直接佣金-" + tPayYear;          //直接佣金支出
	                mEnteredDR = String.valueOf(tmoney);
	                mEnteredCR = "";
	
	                if (isExitInTab(mBussNo,mPolNo,mBillID))
	                	break;
	                	OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                	if(tOFInterfaceSchema != null)
	                        mOFInterfaceSet.add(tOFInterfaceSchema);
	                    dealLITranInfo();
	            }
	            
	            if (mSumMoney>0)
	            {
	            //汇总意外险，短期健康险的直接佣金借方金额，产生贷方数据（应付佣金）
	            tmoney = new DecimalFormat("0.00").format(new Double(mSumMoney));
	            mBillID = "1005";  
	            mAccountCode = "应付佣金";   //应付直接佣金业务员佣金
	            mEnteredDR = "";
	            mEnteredCR = String.valueOf(tmoney);
	            mPolNo = mBussNo;
	            mAttribute6 = "NA";
	            mAttribute7 = "NA";
	            mAttribute8 = "NA";
	            
	            	
	            if (!isExitInTab(mBussNo,mPolNo,mBillID))
	            	{
	                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                if(tOFInterfaceSchema != null)
	                mOFInterfaceSet.add(tOFInterfaceSchema);
	                dealLITranInfo();
	            	}
	            }
	            
	            
	            //非意外险，短期健康险，提取信息精确到险种级
	        	initVar();
	        	mMatchID ++ ;
	        	mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
	        	mManageCom = tManageCom;
	            mAccountingDate = PubFun.getCurrentDate();
	            mTransDate = mStatYear+"-"+mStatMon+"-01";
	            mSegment2 = tManageCom.concat("00");
	            mSaleChnl="02";  
	            double pSumMoney = 0;
	            
	            
	            String pSql = "select riskcode,payyear,BranchType,sum(Fyc) from "
                  + "(select riskcode,payyear,BranchType,Fyc "
                  + "from LACommision where ManageCom like concat('?tManageCom?','%') "
                  + "and Fyc!=0 and riskmark='0' and commdire='1' and branchtype='1' "
                  + "and agentcode in(select agentcode from lawage where state='1' and "
                  + "indexcalno='?tWageNo?' and f25>=0) and caldate>='?caldate1?' and caldate<='?caldate2?' "
                  + " and riskcode not in (select riskcode from lmriskapp a where a.RiskType='H' And riskcode In  "
                  + " (select riskcode from lmriskapp where RiskPeriod In ('S','M')) "
                  + " Union (select riskcode From lmriskapp where RiskType='A'))) g "
                  + " group by riskcode,payyear,BranchType ";
	            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	            sqlbv6.sql(pSql);
	            sqlbv6.put("tManageCom", tManageCom);
	            sqlbv6.put("tWageNo", tWageNo);
	            sqlbv6.put("caldate1", tCalDate[0]);
	            sqlbv6.put("caldate2", tCalDate[1]);
	            	
	            logger.debug(tSql);
	            ExeSQL pExeSQL = new ExeSQL();
	            SSRS pSSRS = pExeSQL.execSQL(sqlbv6);
	            
	            //产生借方数据
	            for (int i=1;i<=pSSRS.MaxRow;i++)
	            {
	
	            	tmoney = String.valueOf(pSSRS.GetText(i,4));
	            	pSumMoney = pSumMoney + Double.parseDouble(tmoney);
	                
	                if (Double.valueOf(tmoney).doubleValue() == 0)
	                {
	                	dealError("LACommision", mManageCom+pSSRS.GetText(i, 1), pSSRS.GetText(i, 3), "004", "010", "直接佣金金额为0");
	                    continue;
	                }
	                mRiskCode = pSSRS.GetText(i,1);
	                mPolNo = mBussNo;
	                mSaleChnl = tranAgentmSaleChnl(pSSRS.GetText(i,3).trim());
	                
	                String tPayYear = pSSRS.GetText(i,2);
	                
	                if(tPayYear.equals("0"))
	                    tPayYear = "首期佣金";
	                else
	                    tPayYear = "续期佣金";
	
	
	                mBillID = "1004";
	                mAccountCode = "佣金支出-直接佣金-" + tPayYear;          //直接佣金支出
	                mEnteredDR = String.valueOf(tmoney);
	                mEnteredCR = "";
	
	                if (isExitInTab(mBussNo,mBussNo,mBillID))
	                	break;
	                	OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                	if(tOFInterfaceSchema != null)
	                        mOFInterfaceSet.add(tOFInterfaceSchema);
	                    dealLITranInfo();
	            }
	            
	             if (pSumMoney>0)
	            {
	            // 汇总非意外险，短期健康险的直接佣金借方金额，产生贷方数据（应付佣金）
	            tmoney = new DecimalFormat("0.00").format(new Double(pSumMoney));
	            mBillID = "1005";  
	            mAccountCode = "应付佣金";   //应付直接佣金业务员佣金
	            mEnteredDR = "";
	            mEnteredCR = String.valueOf(tmoney);
	            mPolNo = mBussNo;
	            mAttribute6 = "NA";
	            mAttribute7 = "NA";
	            mAttribute8 = "NA";
	            
	            	
	            if (!isExitInTab(mBussNo,mBussNo,mBillID))
	            	{
	                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                if(tOFInterfaceSchema != null)
	                mOFInterfaceSet.add(tOFInterfaceSchema);
	                dealLITranInfo();
	           	   }
	            }
	            
	            
	        }

	        return true;
	
    }

    /**续收津贴接口操作
     * 
     * @return
     */
    private boolean PayReAgentJ()
    {

   
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);
        LAWageDB tLAWageDB = new LAWageDB();

        String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWage "
                       + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?cManageCom?','%') "
                       + "and branchtype='4' ";
        SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        sqlbv7.sql(tComSql);
        sqlbv7.put("tWageNo", tWageNo);
        sqlbv7.put("cManageCom", cManageCom);
        logger.debug("query sql : " + tComSql);
        ExeSQL ttExeSQL = new ExeSQL();
        SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv7);
        for (int k=1;k<=tSSRS1.MaxRow;k++)
        {
            String tManageCom=tSSRS1.GetText(k,1);
            //附加佣金提取
            String tSql = "select * from LAWage "
                        + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?tManageCom?','%') "
                        + "and branchtype='4' and f25 >= 0 and state='1' ";
            SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
            sqlbv8.sql(tSql);
            sqlbv8.put("tWageNo", tWageNo);
            sqlbv8.put("tManageCom", tManageCom);
            logger.debug(tSql);
            LAWageSet tLAWageSet = tLAWageDB.executeQuery(sqlbv8);
            if (tLAWageSet.size()==0)
            {
            	dealError("LAWage", tWageNo, tRecordNo, "014", "011", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"没有相应的续收员附佣记录");
                continue;
            }
            for (int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                if (tLAWageSchema.getState() == null || tLAWageSchema.getState().equals("0"))
                {
                	dealError("LAWage", tWageNo, tRecordNo, "015", "011", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的续收员佣金记录未审核");
                    return true;
                }
            }

            double[] tWage =  new double[27]; 
            double[] tTax = new double[2]; 

            for(int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                tWage[0] = tWage[0] + tLAWageSchema.getF01();                     //岗位津贴
                //08续期基本发修改，薪资项变更

                tWage[1] = tWage[1] + tLAWageSchema.getF14();                     //展业达成津贴
                tWage[2] = tWage[2] + tLAWageSchema.getF15();                     //展业超额奖金
                tWage[3] = tWage[3] + tLAWageSchema.getF16();                     //继续率奖金
                tWage[4] = tWage[4] + tLAWageSchema.getF03() + tLAWageSchema.getF11();  //续期奖金 = F03 + F11
                tWage[5] = tWage[5] + tLAWageSchema.getF07();                     //复效奖金
                tWage[6] = tWage[6] + tLAWageSchema.getF09();                     //主任月奖金
                tWage[7] = tWage[7] + tLAWageSchema.getF08() + tLAWageSchema.getF10();  //年终奖金 = F08 + F10
//                tWage[8] = tWage[8] + tLAWageSchema.getF30();                     //加扣款
//                tWage[9] = tWage[9] + tLAWageSchema.getLastMoney();               //上期余额
//                tWage[10] = tWage[10] + tLAWageSchema.getK11();                     //品质加扣款
//                tWage[11] = tWage[11] + tLAWageSchema.getW10();                     //训练津贴
                tWage[8] = tWage[8] + tLAWageSchema.getLastMoney();               //上期余额
                tWage[9] = tWage[9] + tLAWageSchema.getK11();                     //品质加扣款调整为续收品质加扣款
                tWage[10] = tWage[10] + tLAWageSchema.getW10();                     //训练津贴
                tWage[11] = tWage[11] + tLAWageSchema.getF30();                   //续收普通加扣款
                tWage[12] = tWage[12] + tLAWageSchema.getF17();                    //展业普通加扣款
                tWage[13] = tWage[13] + tLAWageSchema.getF18();                   //展业品质加扣款
                //新增科目 2009-04-22
                tWage[14] = tWage[14] + tLAWageSchema.getF14();                   //伯乐奖金
                tWage[15] = tWage[15] + tLAWageSchema.getF15();                   //收费管理奖金
                tWage[16] = tWage[16] + tLAWageSchema.getF26();                   //辅导奖金
                tWage[17] = tWage[17] + tLAWageSchema.getF09();                   //展业管理奖金
//                tWage[18] = tWage[18] + tLAWageSchema.getF19();                   //四次收费服务奖金 
                tWage[18] = tWage[18] - tLAWageSchema.getF27();                   //养老金（税后扣值）                
                tWage[19] = tWage[19] + tLAWageSchema.getF02();                   //银代服务奖金              
                tWage[20] = tWage[20] + tLAWageSchema.getF20();                   //银代品质加扣款               
                tWage[21] = tWage[21] + tLAWageSchema.getF21();                   //银代普通加扣款           
                tWage[22] = tWage[22] + tLAWageSchema.getF24();                   //直辖管理奖金
                tWage[23] = tWage[23] + tLAWageSchema.getF28();                   //直辖管理奖金
                tWage[24] = tWage[24] + tLAWageSchema.getK03();                   //综拓奖金
                tWage[25] = tWage[25] + tLAWageSchema.getK04();                   //综拓管理奖金
                tWage[26] = tWage[26] + tLAWageSchema.getF29();                   //区主任育成回计奖金

                tTax[0] = tTax[0] + tLAWageSchema.getK01();                       //营业税及附加
                tTax[1] = tTax[1] + tLAWageSchema.getK02();                       //个人所得税
            }
            String[] tWageItem = new String[27];                                  //新增科目时需要在sour.xml中增加描述
            String[] tTaxItem = new String[2];
            tWageItem[0] = "岗位津贴";
            tWageItem[1] = "展业达成津贴";
            tWageItem[2] = "展业超额奖金";
            tWageItem[3] = "继续率奖金";
            tWageItem[4] = "续期奖金";
            tWageItem[5] = "复效奖金";
            tWageItem[6] = "主任月奖金";
            tWageItem[7] = "年终奖金";
//            tWageItem[8] = "加扣款";
//            tWageItem[9] = "上期余额";
//            tWageItem[10] = "品质加扣款";
//            tWageItem[11] = "训练津贴";
            tWageItem[8] = "上月余额";
            tWageItem[9] = "续收品质加扣款";//调整为续收品质加扣款
            tWageItem[10] = "训练津贴";
            tWageItem[11] = "续收普通加扣款";
            tWageItem[12] = "展业普通加扣款";
            tWageItem[13] = "展业品质加扣款";
            tWageItem[14] = "伯乐奖";
            tWageItem[15] = "收费管理奖金";
            tWageItem[16] = "辅导奖";
            tWageItem[17] = "展业管理奖金";
//            tWageItem[18] = "四次收费服务奖金";
//            tWageItem[18] = "养老金";
          //zy 2009-10-27 将养老金调整为贷方科目
            tWageItem[18] = "代扣养老金";
            tWageItem[19] = "银代服务奖金";
            tWageItem[20] = "银代品质加扣款";
            tWageItem[21] = "银代普通加扣款";
            tWageItem[22] = "直辖管理奖金";
            tWageItem[23] = "个人贡献奖";
            tWageItem[24] = "综拓奖金";
            tWageItem[25] = "综拓管理奖金";
            tWageItem[26] = "区主任育成回计奖金";
            

            tTaxItem[0] = "代理人营业税";        //营业税及附加
            tTaxItem[1] = "代理人个人所得税";    //个人所得税

            initVar();
            mMatchID ++ ;
            mManageCom = tManageCom;
            mSegment2 = tManageCom.concat("00");
            mAccountingDate = PubFun.getCurrentDate();
            mTransDate = mStartDate;
            mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
            mPolNo = mBussNo;

            double tSumMoney = 0;
            for(int j = 0;j < tWage.length;j++)
            {
                if (tWage[j] == 0)
                {
                	dealError("LAWage", mBussNo, mBussNo, "004", "010", tWageItem[j]+"金额为0");
                    continue;
                }
                //应再次要求取消展业达成津贴、展业超额奖金、主任月奖金
                if(j==1||j==2||j==6)
                	continue;

                tSumMoney=tSumMoney+tWage[j];
                tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]));
                //注意这采用的是变量值，所以如果增加新的科目时一定要注意顺序问题
                if(j==1||j==2||j==3||j==12||j==13)
                mSaleChnl = "10";//展业普通加扣款、展业品质加扣款、展业达成津贴、展业超额奖金、继续率奖金的渠道为10,
                else
                	mSaleChnl = "02";

                if(tWageItem[j].equalsIgnoreCase("代扣养老金"))
                {
                	//贷方取原借方相反的养老金数值
                	tmoney=new DecimalFormat("0.00").format(new Double(0-tWage[j]));
	                mBillID = "1100";
	                mAccountCode = "应付佣金-"+tWageItem[j];                 //代扣养老金
	                mEnteredDR = "";
	                mEnteredCR = String.valueOf(tmoney);
                }
                else
                {
	                mBillID = "1100";
	                mAccountCode = "佣金支出-续收津贴-"+tWageItem[j];                 //续收津贴
	                mEnteredDR = String.valueOf(tmoney);
	                mEnteredCR = "";
                }
                mHeadDescription = mManageCom + "的" + tWageItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tTaxMoney=0;
            for(int j=0;j<tTax.length;j++)
            {
                if (tTax[j]==0)
                {
                	dealError("LAWage", mBussNo, mBussNo, "004", "010", tTaxItem[j]+"金额为0");
                    continue;
                }
                tTaxMoney=tTaxMoney+tTax[j];
                tmoney = new DecimalFormat("0.00").format(new Double(tTax[j]));

                mBillID = "1101";
                mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription = mManageCom + "的" + tTaxItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tMoneyShould = tSumMoney-tTaxMoney;
            if (tMoneyShould>0)
            {
                tmoney =new DecimalFormat("0.00").format(new Double(tMoneyShould));

                mBillID = "1102";
                mAccountCode="应付佣金";                                    //应付附加佣金业务员佣金
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription =mManageCom + "的应付续收津贴支出";
                if (!isExitInTab(mBussNo,mBussNo,mBillID))
                {
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }

        //提取展业奖金
        tComSql = "select distinct(substr(ManageCom,1,6)) from LACommision "
                + "where WageNo='?tWageNo?' and branchtype='4' and ManageCom like concat('?cManageCom?','%')";
        SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        sqlbv9.sql(tComSql);
        sqlbv9.put("tWageNo", tWageNo);
        sqlbv9.put("cManageCom", cManageCom);

        SSRS tSSRS2 = ttExeSQL.execSQL(sqlbv9);
        for (int k=1;k<=tSSRS2.MaxRow;k++)
        {
            String tManageCom = tSSRS2.GetText(k,1);
            //lacommision partition (p6)为存放08年lacommision表的分区，临时解决数据查询慢的问题，但是只能查询08年的数据，其他年份查询有问题
            //注意升级时将分区去掉，上线时注意分区问题
            String  tSql = "";
            SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            tSql = "select riskcode,sum(h) from ( "
                		 +" select /*+index (a LACOMMISION_MANAGECOM) */riskcode,NVL(sum(fyc),0) h from lacommision   a "
                		 +" where a.managecom like concat('?tManageCom?','%') and a.CommDire='1' and p5!=1  and  a.WageNo='?tWageNo?' "
		                 +" and a.branchtype in ('4','5') and a.payyear<1 and (to_char(lastpaytodate,'yyyymm')>='200512' or lastpaytodate='1899-12-31') "
		                 +" and agentcode in(select agentcode from lawage where state='1' and branchtype = '4' "
		                 +" and indexcalno='?tWageNo?' and f25>=0) "
		                 +" and exists (select 1 from lrascription where contno = a.contno union select 1 from lrascriptionb where contno=a.contno ) "
		                 +" and exists (select 1 from laagent where agentcode = a.agentcode and employdate <= (select enddate from lastatsegment  "
		                 +"where stattype = '1' and yearmonth = '?tWageNo?') and (outworkdate is null or outworkdate > (select startdate from "
		                 +"lastatsegment where stattype = '1' and yearmonth = '?tWageNo?'))) "
		                 +" and  not exists (  select d.polno from lacommision d ,lpedoritem b,ljapay c where d.receiptno=c.payno and b.edorno=c.incomeno  "
		                 +"and c.IncomeType='10' and b.edortype='RE'  and d.branchtype in ('4','5') and to_char(d.tmakedate,'yyyymm')='?tWageNo?' "
		                 +"and d.commdire='1' and b.polno=a.polno) group by riskcode "
		                 +" union all "
		                 +" select /*+index (a LACOMMISION_MANAGECOM) */riskcode,NVL(sum(fyc),0) h  from lacommision   a "
		                 +" where a.managecom like concat('?tManageCom?','%') and a.CommDire='1' and  a.WageNo='?tWageNo?' and a.branchtype in ('4','5') "
		                 +" and a.payyear<1 and (to_char(lastpaytodate,'yyyymm')>='200512' or lastpaytodate='1899-12-31') "
		                 +" and agentcode in(select agentcode from lawage where state='1' and branchtype = '4' "
		                 +" and indexcalno='?tWageNo?' and f25>=0)  "
		                 +" and not exists (select 1 from lrascription where contno = a.contno union select 1 from lrascriptionb where contno=a.contno)  "
		                 +" and exists (select 1 from laagent where agentcode = a.agentcode and employdate <= (select enddate from lastatsegment "
		                 +"where stattype = '1' and yearmonth = '?tWageNo?') and (outworkdate is null or "
		                 +"outworkdate > (select startdate from lastatsegment where stattype = '1' and yearmonth = '?tWageNo?'))) "
		                 +" group by riskcode  ) "
		                 +" group by riskcode" ;
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
                tSql = "select riskcode,sum(h) from ( "
               		 +" select riskcode,(case when sum(fyc) is not null then sum(fyc) else 0 end) h from lacommision   a "
               		 +" where a.managecom like concat('?tManageCom?','%') and a.CommDire='1' and p5!=1  and  a.WageNo='?tWageNo?' "
		                 +" and a.branchtype in ('4','5') and a.payyear<1 and (to_char(lastpaytodate,'yyyymm')>='200512' or lastpaytodate='1899-12-31') "
		                 +" and agentcode in(select agentcode from lawage where state='1' and branchtype = '4' "
		                 +" and indexcalno='?tWageNo?' and f25>=0) "
		                 +" and exists (select 1 from lrascription where contno = a.contno union select 1 from lrascriptionb where contno=a.contno ) "
		                 +" and exists (select 1 from laagent where agentcode = a.agentcode and employdate <= (select enddate from lastatsegment  "
		                 +"where stattype = '1' and yearmonth = '?tWageNo?') and (outworkdate is null or outworkdate > (select startdate from "
		                 +"lastatsegment where stattype = '1' and yearmonth = '?tWageNo?'))) "
		                 +" and  not exists (  select d.polno from lacommision d ,lpedoritem b,ljapay c where d.receiptno=c.payno and b.edorno=c.incomeno  "
		                 +"and c.IncomeType='10' and b.edortype='RE'  and d.branchtype in ('4','5') and to_char(d.tmakedate,'yyyymm')='?tWageNo?' "
		                 +"and d.commdire='1' and b.polno=a.polno) group by riskcode "
		                 +" union all "
		                 +" select riskcode,(case when sum(fyc) is not null then sum(fyc) else 0 end) h  from lacommision   a "
		                 +" where a.managecom like concat('?tManageCom?','%') and a.CommDire='1' and  a.WageNo='?tWageNo?' and a.branchtype in ('4','5') "
		                 +" and a.payyear<1 and (to_char(lastpaytodate,'yyyymm')>='200512' or lastpaytodate='1899-12-31') "
		                 +" and agentcode in(select agentcode from lawage where state='1' and branchtype = '4' "
		                 +" and indexcalno='?tWageNo?' and f25>=0)  "
		                 +" and not exists (select 1 from lrascription where contno = a.contno union select 1 from lrascriptionb where contno=a.contno)  "
		                 +" and exists (select 1 from laagent where agentcode = a.agentcode and employdate <= (select enddate from lastatsegment "
		                 +"where stattype = '1' and yearmonth = '?tWageNo?') and (outworkdate is null or "
		                 +"outworkdate > (select startdate from lastatsegment where stattype = '1' and yearmonth = '?tWageNo?'))) "
		                 +" group by riskcode  ) g "
		                 +" group by riskcode" ;
            	
            }
            sqlbv10.sql(tSql);
            sqlbv10.put("tManageCom", tManageCom);
            sqlbv10.put("tWageNo", tWageNo);

            logger.debug(tSql);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(sqlbv10);
            for (int i=1;i<=tSSRS.MaxRow;i++)
            {
                initVar();
                mMatchID ++ ;
                mManageCom = tManageCom;
                mSegment2 = tManageCom.concat("00");
                mAccountingDate = PubFun.getCurrentDate() ;
                mTransDate = mStartDate ;
                tmoney = String.valueOf(tSSRS.GetText(i,2));
                if (Double.valueOf(tmoney).doubleValue() == 0)
                {
                	dealError("LACommision", mManageCom, tSSRS.GetText(i,1), "004", "011", "首期佣金金额为0");
                    continue;
                }
                mRiskCode = tSSRS.GetText(i,1);
//                mSaleChnl = "02";      //branchtype = '4'
                mSaleChnl = "10";
                mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
                mPolNo = mBussNo;
                for ( int j = 1; j <= 2; j++)
                {
                    if (j == 1)
                    {
                        mBillID = "1103";
                        mAccountCode = "佣金支出-直接佣金-首期佣金";               //首年度展业服务奖金
                        mEnteredDR = String.valueOf(tmoney);
                        mEnteredCR = "";
                        mHeadDescription = mAccountCode + "险种" + mRiskCode;
                    }
                    else
                    {
                        mBillID = "1104";
                        mAccountCode = "应付佣金";                          //应付附加佣金业务员佣金
                        mEnteredDR = "";
                        mEnteredCR = String.valueOf(tmoney);
                        mHeadDescription = mAccountCode;
                    }
                    if (isExitInTab(mBussNo,mBussNo,mBillID))
                        break;
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
            String  tSql2 = "";
            SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            	tSql2 = "select riskcode,sum(h) from ( "
                        +" select /*+index (a LACOMMISION_MANAGECOM) */riskcode,NVL(sum(fyc),0) h from lacommision  a "
                        +" where ManageCom like concat('?tManageCom?','%') and PayYear > 0 and not exists(select 1 from latree where agentcode=a.agentcode  and agentgrade='F02') "
                        +" and commdire = '1' and branchtype in ('4','5') "
                        +" and agentcode in (select agentcode from lawage where state='1' and branchtype = '4' and "
                        +"indexcalno='?tWageNo?' and f25>=0)  "
                        +" and WageNo='?tWageNo?' and polno not in (select polno from lrascription "
                        +"union select polno from lrascriptionb) "
                        +" and exists (select 1 from laagent where agentcode = a.agentcode and "
                        +"employdate <= (select enddate from lastatsegment where stattype = '1' "
                        +"and yearmonth = '?tWageNo?') and (outworkdate is null or outworkdate > (select "
                        +"startdate from lastatsegment where stattype = '1' and yearmonth = '?tWageNo?'))) "
                        +" group by riskcode ) "
                        +" group by riskcode" ;
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
              	tSql2 = "select riskcode,sum(h) from ( "
                        +" select riskcode,(case when sum(fyc) is not null then sum(fyc) else 0 end) h from lacommision  a "
                        +" where ManageCom like concat('?tManageCom?','%') and PayYear > 0 and not exists(select 1 from latree where agentcode=a.agentcode  and agentgrade='F02') "
                        +" and commdire = '1' and branchtype in ('4','5') "
                        +" and agentcode in (select agentcode from lawage where state='1' and branchtype = '4' and "
                        +"indexcalno='?tWageNo?' and f25>=0)  "
                        +" and WageNo='?tWageNo?' and polno not in (select polno from lrascription "
                        +"union select polno from lrascriptionb) "
                        +" and exists (select 1 from laagent where agentcode = a.agentcode and "
                        +"employdate <= (select enddate from lastatsegment where stattype = '1' "
                        +"and yearmonth = '?tWageNo?') and (outworkdate is null or outworkdate > (select "
                        +"startdate from lastatsegment where stattype = '1' and yearmonth = '?tWageNo?'))) "
                        +" group by riskcode ) g "
                        +" group by riskcode" ;
            }
            sqlbv11.sql(tSql2);
            sqlbv11.put("tManageCom", tManageCom);
            sqlbv11.put("tWageNo", tWageNo);
           logger.debug(tSql2);
		   SSRS tSSRS3 = tExeSQL.execSQL(sqlbv11);
		   for (int i=1;i<=tSSRS3.MaxRow;i++)
		   {
		       initVar();
		       mMatchID ++ ;
		       mManageCom = tManageCom;
		       mSegment2 = tManageCom.concat("00");
		       mAccountingDate = PubFun.getCurrentDate() ;
		       mTransDate = mStartDate ;
		       tmoney = String.valueOf(tSSRS3.GetText(i,2));
		       if (Double.valueOf(tmoney).doubleValue() == 0)
		       {
		    	   dealError("lacommision", mManageCom, tSSRS3.GetText(i, 1), "004", "011", "续期佣金金额为0");
		           continue;
		       }
		       mRiskCode = tSSRS3.GetText(i,1);
//		       mSaleChnl = "02";      //branchtype = '4'
		       mSaleChnl = "10";
		       mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
		       mPolNo = mBussNo;
		       for ( int j = 1; j <= 2; j++)
		       {
		           if (j == 1)
		           {
		               mBillID = "1105";
		               mAccountCode = "佣金支出-直接佣金-续期佣金";               //续期展业服务奖金
		               mEnteredDR = String.valueOf(tmoney);
		               mEnteredCR = "";
		               mHeadDescription = mAccountCode + "险种" + mRiskCode;
		           }
		           else
		           {
		               mBillID = "1106";
		               mAccountCode = "应付佣金";                          //应付附加佣金业务员佣金
		               mEnteredDR = "";
		               mEnteredCR = String.valueOf(tmoney);
		               mHeadDescription = mAccountCode;
		           }
		           if (isExitInTab(mBussNo,mBussNo,mBillID))
		               break;
		           OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
		           if(tOFInterfaceSchema != null)
		               mOFInterfaceSet.add(tOFInterfaceSchema);
		           dealLITranInfo();
		       }
		   }
        }
        return true;
    }





    /**续收督导津贴接口操作
     * 
     * @return
     */
    private boolean PayAdmiAgent()
    {

        String tCalDate[] = new String[2];
        String mStartDate = mStatYear+"-"+mStatMon+"-01";
        tCalDate = PubFun.calFLDate(mStartDate);
         
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);
        LAWageDB tLAWageDB = new LAWageDB();

        String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWage "
                       + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?cManageCom?','%') "
                       + "and branchtype='5' ";
        SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
        sqlbv12.sql(tComSql);
        sqlbv12.put("tWageNo", tWageNo);
        sqlbv12.put("cManageCom", cManageCom);
        logger.debug("query sql : " + tComSql);
        ExeSQL ttExeSQL = new ExeSQL();
        SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv12);
        for (int k=1;k<=tSSRS1.MaxRow;k++)
        {
            String tManageCom=tSSRS1.GetText(k,1);
            //附加佣金提取
            String tSql = "select * from LAWage "
                        + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?tManageCom?','%') "
                        + "and branchtype='5' and f25 >= 0 and state='1' ";
            SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
            sqlbv13.sql(tSql);
            sqlbv13.put("tWageNo", tWageNo);
            sqlbv13.put("tManageCom", tManageCom);
            logger.debug(tSql);
            LAWageSet tLAWageSet = tLAWageDB.executeQuery(sqlbv13);
            if (tLAWageSet.size()==0)
            {
            	dealError("LAWage", tWageNo, tRecordNo, "014", "012", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"没有相应的续收督导津贴附佣记录");
                continue;
            }
            for (int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                if (tLAWageSchema.getState() == null || tLAWageSchema.getState().equals("0"))
                {
                	dealError("LAWage", tWageNo, tRecordNo, "015", "012", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的续收督导记录未审核");
                    return true;
                }
            }

            double[] tWage = {0,0,0,0,0,0,0,0,0,0};
            double[] tTax = {0,0};

            for(int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                tWage[0] = tWage[0] + tLAWageSchema.getF01();                   //岗位津贴
                tWage[1] = tWage[1] + tLAWageSchema.getF02();                   //服务补贴
                tWage[2] = tWage[2] + tLAWageSchema.getF03();                   //督导服务奖金
                tWage[3] = 0;//tWage[3] + tLAWageSchema.getF07();                     //收费服务奖金
                tWage[4] = 0;//tWage[4] + tLAWageSchema.getF09();                     //复效还垫奖金
                tWage[5] = tWage[5] + tLAWageSchema.getF08();                   //年终奖金
                tWage[6] = tWage[6] + tLAWageSchema.getF30();                   //加扣款
                tWage[7] = tWage[7] + tLAWageSchema.getLastMoney();             //上期余额
                tWage[8] = tWage[8] + tLAWageSchema.getK11();                   //品质加扣款
                tWage[9] = tWage[9] + tLAWageSchema.getW10();                   //训练津贴

                tTax[0] = tTax[0] + tLAWageSchema.getK01();                     //营业税及附加
                tTax[1] = tTax[1] + tLAWageSchema.getK02();                     //个人所得税
            }
            String[] tWageItem = new String[10];                                 //新增科目时需要在sour.xml中增加描述
            String[] tTaxItem = new String[2];
            tWageItem[0] = "岗位津贴";
            tWageItem[1] = "服务补贴";
            tWageItem[2] = "督导服务奖金";
            tWageItem[3] = "收费服务奖金";
            tWageItem[4] = "复效还垫奖金";
            tWageItem[5] = "年终奖金";
            tWageItem[6] = "加扣款";
            tWageItem[7] = "上期余额";
            tWageItem[8] = "品质加扣款";
            tWageItem[9] = "训练津贴";

            tTaxItem[0] = "代理人营业税";             //营业税及附加
            tTaxItem[1] = "代理人个人所得税";          //个人所得税

            initVar();
            mMatchID ++;
            mManageCom = tManageCom;
            mSegment2 = tManageCom.concat("00");
            mAccountingDate = PubFun.getCurrentDate() ;
            mTransDate = mStartDate;
            mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;
            mPolNo = mBussNo;

            double tSumMoney = 0;
            for(int j = 0;j < tWage.length;j++)
            {
                if (tWage[j] == 0)
                {
                	dealError("LAWage", mBussNo, mBussNo, "004", "012", tWageItem[j]+"金额为0");
                    continue;
                }
                tSumMoney=tSumMoney+tWage[j];
                tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]) );

                mBillID = "1200";
                mAccountCode = "佣金支出-续期督导津贴-" + tWageItem[j];            //续收督导津贴
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mManageCom + "的" + tWageItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tTaxMoney=0;
            for(int j=0;j<tTax.length;j++)
            {
                if (tTax[j]==0)
                {
                	dealError("LAWage", mBussNo, mBussNo, "004", "012", tTaxItem[j]+"金额为0");
                    continue;
                }
                tTaxMoney=tTaxMoney+tTax[j];
                tmoney =new DecimalFormat("0.00").format(new Double(tTax[j]));

                mBillID = "1201";
                 mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription = mManageCom+"的"+tTaxItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tMoneyShould = tSumMoney-tTaxMoney;
            if (tMoneyShould>0)
            {
                tmoney = new DecimalFormat("0.00").format(new Double(tMoneyShould));

                mBillID = "1202";
                mAccountCode = "应付佣金";          //应付附加佣金业务员佣金
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription = mManageCom + "的应付续收津贴支出";
                if (!isExitInTab(mBussNo,mBussNo,mBillID))
                {
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }

        //提取展业奖金
        tComSql = "select distinct(substr(ManageCom,1,6)) from LACommision "
                + "where WageNo='?tWageNo?' and branchtype='5' and ManageCom like concat('?cManageCom?','%')";
        SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
        sqlbv14.sql(tComSql);
        sqlbv14.put("tWageNo", tWageNo);
        sqlbv14.put("cManageCom", cManageCom);
        SSRS tSSRS2 = ttExeSQL.execSQL(sqlbv14);
        for (int k=1;k<=tSSRS2.MaxRow;k++)
        {
            String tManageCom = tSSRS2.GetText(k,1);
            String  tSql = "select riskcode,sum(m) from ( "
                         + "select riskcode,sum(Fyc) m from LACommision "
                         + "where ManageCom like concat('?tManageCom?','%') "
                         + "and commdire='1' and branchtype='5' and payyear < 1 "
                         + "and agentcode in(select agentcode from lawage where state='1' and branchtype='5' and indexcalno='?tWageNo?' "
                         + "and f25>=0) and caldate>='?caldate1?' and caldate<='?caldate2?' "
                         + "group by riskcode "
                         + "union all "
                         + "select riskcode,sum(directwage) m from LACommision "
                         + "where ManageCom like concat('?tManageCom?','%') "
                         + "and commdire='1' and branchtype='5' and payyear >= 1 "
                         + "and agentcode in(select agentcode from lawage where state='1' and branchtype='5' and indexcalno='?tWageNo?' "
                         + "and f25>=0) and caldate>='?caldate1?' and caldate<='?caldate2?' "
                         + "group by riskcode "
                         + ") g group by riskcode "
            ;
            SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
            sqlbv15.sql(tSql);
            sqlbv15.put("tManageCom", tManageCom);
            sqlbv15.put("tWageNo", tWageNo);
            sqlbv15.put("caldate1", tCalDate[0]);
            sqlbv15.put("caldate2", tCalDate[1]);
            logger.debug(tSql);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(sqlbv15);
            for (int i=1;i<=tSSRS.MaxRow;i++)
            {
                initVar();
                mMatchID ++ ;
                mManageCom = tManageCom;
                mSegment2 = tManageCom.concat("00");
                mAccountingDate = PubFun.getCurrentDate();
                mTransDate = mStartDate;
                tmoney = String.valueOf(tSSRS.GetText(i,2));
                if (Double.valueOf(tmoney).doubleValue() ==0)
                {
                	dealError("LACommision", mManageCom, tSSRS.GetText(i, 1), "004", "012", "展业服务奖金金额为0");
                    continue;
                }
                mRiskCode = tSSRS.GetText(i,1);
//                mSaleChnl = "02";              //branchtype = '5'
                mSaleChnl = "10";
                mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;
                mPolNo = mBussNo;
                for ( int j = 1; j <= 2; j++)
                {
                    if (j == 1)
                    {
                        mBillID = "1203";
                        mAccountCode = "佣金支出-续期督导津贴-展业服务奖金";        //展业奖金
                        mEnteredDR = String.valueOf(tmoney);
                        mEnteredCR = "";
                        mHeadDescription = mAccountCode + "险种" + mRiskCode;
                    }
                    else
                    {
                        mBillID = "1204";
                        mAccountCode = "应付佣金";                               //应付附加佣金业务员佣金
                        mEnteredDR = "";
                        mEnteredCR = String.valueOf(tmoney);
                        mHeadDescription = mAccountCode;
                    }
                    if (isExitInTab(mBussNo,mBussNo,mBillID))
                        break;
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }
        return true;
    }





    /**西点工程佣金接口操作
     * 
     * @return
     */
    private boolean PayAgentXiDian()
    {

        String tCalDate[] = new String[2];
        String mStartDate = mStatYear+"-"+mStatMon+"-01";
        tCalDate = PubFun.calFLDate(mStartDate);
         
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);
        LAWageDB tLAWageDB = new LAWageDB();

        /***********branchtype*************/
        String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWage "
                       + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?cManageCom?','%') "
                       + "and branchtype='8' ";
        SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
        sqlbv16.sql(tComSql);
        sqlbv16.put("tWageNo", tWageNo);
        sqlbv16.put("cManageCom", cManageCom);

        logger.debug("query sql : " + tComSql);
        ExeSQL ttExeSQL = new ExeSQL();
        SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv16);
        for (int k=1;k<=tSSRS1.MaxRow;k++)
        {
            String tManageCom=tSSRS1.GetText(k,1);
            //附加佣金提取

            String tSql = "select * from LAWage "
                        + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?tManageCom?','%') "
                        + "and branchtype='8' and f25 >= 0 and state='1' ";
            SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
            sqlbv17.sql(tSql);
            sqlbv17.put("tWageNo", tWageNo);
            sqlbv17.put("tManageCom", tManageCom);

            logger.debug(tSql);
            LAWageSet tLAWageSet = tLAWageDB.executeQuery(sqlbv17);
            if (tLAWageSet.size()==0)
            {
            	dealError("LAWage", tWageNo, tRecordNo, "014", "021", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"没有相应的西点工程附佣记录");
                continue;
            }
            for (int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                if (tLAWageSchema.getState() == null || tLAWageSchema.getState().equals("0"))
                {
                	dealError("LAWage", tWageNo, tRecordNo, "015", "021", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的西点佣金记录未审核");
                    return true;
                }
            }
             /***********这里的tWage和tTax要根据具体的佣金项目进行编写**********/
            double[] tWage = {0,0,0,0,0,0,0,0,0,0};
            double[] tTax = {0,0};
             /***********将各奖项和名称放在变量里**********/
            for(int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                tWage[0] = 0;//tWage[0] + tLAWageSchema.getF01();                   //岗位津贴
                tWage[1] = 0;//tWage[1] + tLAWageSchema.getF02();                   //服务补贴
                tWage[2] = 0;//tWage[2] + tLAWageSchema.getF03();                       //业务拓展津贴
                tWage[3] = 0;//tWage[3] + tLAWageSchema.getF04();                       //管理津贴
                tWage[4] = 0;//tWage[4] + tLAWageSchema.getF09();                   //复效还垫奖金
                tWage[5] = 0;//tWage[5] + tLAWageSchema.getF08();                   //年终奖金
                tWage[6] = tWage[6] + tLAWageSchema.getF30();                       //加扣款
                tWage[7] = tWage[7] + tLAWageSchema.getLastMoney();                 //上期余额
                tWage[8] = 0;//tWage[8] + tLAWageSchema.getK11();                   //品质加扣款
                tWage[9] = 0;//tWage[9] + tLAWageSchema.getW10();                   //训练津贴

                tTax[0] = tTax[0] + tLAWageSchema.getK01();                         //营业税及附加
                tTax[1] = tTax[1] + tLAWageSchema.getK02();                         //个人所得税
            }
            String[] tWageItem = new String[10];                                    //新增科目时需要在sour.xml中增加描述
            String[] tTaxItem = new String[2];
            tWageItem[0] = "岗位津贴";
            tWageItem[1] = "服务补贴";
            tWageItem[2] = "业务拓展津贴";
            tWageItem[3] = "管理津贴";
            tWageItem[4] = "复效还垫奖金";
            tWageItem[5] = "年终奖金";
            tWageItem[6] = "加扣款";
            tWageItem[7] = "上期余额";
            tWageItem[8] = "品质加扣款";
            tWageItem[9] = "训练津贴";

            tTaxItem[0] = "代理人营业税";             //营业税及附加
            tTaxItem[1] = "代理人个人所得税";          //个人所得税

            initVar();
            mMatchID ++;
            mManageCom = tManageCom;
            mSegment2 = tManageCom.concat("00");
            mAccountingDate = PubFun.getCurrentDate() ;
            mTransDate = mStartDate;
            mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;
            mPolNo = mBussNo;

            double tSumMoney = 0;
            for(int j = 0;j < tWage.length;j++)
            {
                if (tWage[j] == 0)
                {
                	dealError("LAWage", tWageNo, tRecordNo, "014", "021", tWageItem[j]+"相应的附加佣金金额为非正数");
                	 continue;
                }
                   
                tSumMoney=tSumMoney+tWage[j];
                tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]) );

                mBillID = "2100";
                /***********这里要改成为西点工程新设的会计科目**********/
                mAccountCode = "佣金支出-西点工程津贴-" + tWageItem[j];
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mManageCom + "的" + tWageItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tTaxMoney=0;
            for(int j=0;j<tTax.length;j++)
            {
                if (tTax[j]==0)
                {
                	dealError("LAWage", tWageNo, tRecordNo, "014", "021", tTaxItem[j]+"相应的税项金额为非正数");
                	continue;
                }
                    
                tTaxMoney=tTaxMoney+tTax[j];
                tmoney =new DecimalFormat("0.00").format(new Double(tTax[j]));

                mBillID = "2101";
                mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription = mManageCom+"的"+tTaxItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tMoneyShould = tSumMoney-tTaxMoney;
            if (tMoneyShould != 0)
            {
                tmoney = new DecimalFormat("0.00").format(new Double(tMoneyShould));

                mBillID = "2102";
                mAccountCode = "应付佣金";          //应付附加佣金
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                /***********这里要改成为西点工程应付支出的说法**********/
                mHeadDescription = mManageCom + "的应付西点工程佣金支出";
                if (!isExitInTab(mBussNo,mBussNo,mBillID))
                {
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }


        /**************************************************/
        /*************直接佣金****************************/
        //提取直接佣金
        tComSql = "select distinct(substr(ManageCom,1,6)) from LACommision "
                + "where WageNo='?tWageNo?' and ManageCom like concat('?cManageCom?','%')";
        SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
        sqlbv18.sql(tComSql);
        sqlbv18.put("tWageNo", tWageNo);
        sqlbv18.put("cManageCom", cManageCom);

        SSRS tSSRS2 = ttExeSQL.execSQL(sqlbv18);
        for (int k=1;k<=tSSRS2.MaxRow;k++)
        {
            String tManageCom=tSSRS2.GetText(k,1);
            SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
            String tSql = "select riskcode,payyear,BranchType,sum(Fyc) from "
                        + "(select riskcode,payyear,BranchType,Fyc "
                        + "from LACommision where ManageCom like concat('?tManageCom?','%') "
                        + "and Fyc!=0 and commdire='1' and branchtype='8' and agentcode in "
                        + "(select agentcode from lawage where state='1' and branchtype='8' and "
                        + "indexcalno='?tWageNo?' and f25>=0) and caldate>='?caldate1?' and caldate<='?caldate2?') g "
                        + "group by riskcode,payyear,BranchType "
                        ;
            sqlbv19.sql(tSql);
            sqlbv19.put("tManageCom", tManageCom);
            sqlbv19.put("tWageNo", tWageNo);
            sqlbv19.put("caldate1", tCalDate[0]);
            sqlbv19.put("caldate2", tCalDate[1]);
            logger.debug(tSql);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(sqlbv19);
            for (int i=1;i<=tSSRS.MaxRow;i++)
            {
                initVar();
                mMatchID ++ ;
                mManageCom = tManageCom;
                mAccountingDate = PubFun.getCurrentDate();
                mTransDate = mStartDate;
                mSegment2 = tManageCom.concat("00");
                tmoney = String.valueOf(tSSRS.GetText(i,4));
                if (Double.valueOf(tmoney).doubleValue() == 0)
                {
                	dealError("LACommision", mManageCom+tSSRS.GetText(i, 1), tSSRS.GetText(i, 2), "004", "021", "直接佣金金额为0");
                	continue;
                }
                    
                mRiskCode = tSSRS.GetText(i,1);
                mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
                mPolNo = mBussNo;
                mSaleChnl = tranAgentmSaleChnl(tSSRS.GetText(i,3).trim());
                String tPayYear = tSSRS.GetText(i,2);
                if(tPayYear.equals("0"))
                    tPayYear = "首期佣金";
                else
                    tPayYear = "续期佣金";

                for ( int j = 1; j <=2; j++)
                {
                    if (j == 1)
                    {
                        mBillID = "2103";
                        mAccountCode = "佣金支出-直接佣金-" + tPayYear;          //直接佣金支出
                        mEnteredDR = String.valueOf(tmoney);
                        mEnteredCR = "";
                    }
                    else
                    {
                        mBillID = "2104";
                        mAccountCode = "应付佣金";                              //应付直接佣金业务
                        mEnteredDR = "";
                        mEnteredCR = String.valueOf(tmoney);
                    }
                    if (isExitInTab(mBussNo,mBussNo,mBillID))
                        break;
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }
        return true;
    }

/**中介督导津贴
 * 
 */
    private boolean PayReAgency()
    {

        String tCalDate[] = new String[2];
        String mStartDate = mStatYear+"-"+mStatMon+"-01";
        tCalDate = PubFun.calFLDate(mStartDate);
         
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);

        LAWageDB tLAWageDB = new LAWageDB();

        String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWage "
                       + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?cManageCom?','%') "
                       + "and branchtype='9' ";
        SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
        sqlbv20.sql(tComSql);
        sqlbv20.put("tWageNo", tWageNo);
        sqlbv20.put("cManageCom", cManageCom);
        logger.debug("query sql : " + tComSql);
        ExeSQL ttExeSQL = new ExeSQL();
        SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv20);
        for (int k=1;k<=tSSRS1.MaxRow;k++)
        {
           String tManageCom=tSSRS1.GetText(k,1);
           //附加佣金提取
           String tSql = "select * from LAWage "
                       + "where IndexCalNo='?tWageNo?' and ManageCom like concat('?tManageCom?','%') "
                       + "and branchtype='9' and f25 >= 0 and state='1' ";
           SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
           sqlbv21.sql(tSql);
           sqlbv21.put("tWageNo", tWageNo);
           sqlbv21.put("tManageCom", tManageCom);
           logger.debug(tSql);
           LAWageSet tLAWageSet = tLAWageDB.executeQuery(sqlbv21);
           if (tLAWageSet.size()==0)
           {
        	   dealError("LAWage", tWageNo, tRecordNo, "014", "022", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"没有相应中介督导的附加佣金记录");
        	   continue; 
           }
            
           for (int i=1;i<=tLAWageSet.size();i++)
           {
              LAWageSchema tLAWageSchema = new LAWageSchema();
              tLAWageSchema.setSchema(tLAWageSet.get(i));
              if (tLAWageSchema.getState() == null || tLAWageSchema.getState().equals("0"))
              {
            	  dealError("LAWage", tWageNo, tRecordNo, "015", "022", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的中介督导佣金记录未审核");
                 return true;
              }
           }

//           double[] tWage = {0,0,0,0,0,0,0,0,0};
           double[] tWage = new double[10];
           double[] tTax = {0,0};

           for(int i=1;i<=tLAWageSet.size();i++)
           {
            LAWageSchema tLAWageSchema = new LAWageSchema();
            tLAWageSchema.setSchema(tLAWageSet.get(i));
            //zy 2009-11-16 根据中介督导新规则进行调整
//            tWage[0] = tWage[0] + tLAWageSchema.getW10();                   //训练津贴 取消
            tWage[0] = tWage[0] + tLAWageSchema.getW10();                   //计划达成津贴 
            tWage[1] = tWage[1] + tLAWageSchema.getF01();                   //岗位津贴
//            tWage[2] = tWage[2] + tLAWageSchema.getF02();                   //服务补贴 取消
            tWage[2] = tWage[2] + tLAWageSchema.getF02();                    //继续率奖金 
            tWage[3] = tWage[3] + tLAWageSchema.getF03();                   //督导服务奖金
//            tWage[4] = tWage[4] + tLAWageSchema.getF06();                   //展业服务奖金 取消
            tWage[4] = tWage[4] + tLAWageSchema.getK12();                    //展业普通加扣款
            tWage[5] = tWage[5] + tLAWageSchema.getF08();                   //年终奖金
            tWage[6] = tWage[6] + tLAWageSchema.getF30();                   //加扣款
            tWage[7] = tWage[7] + tLAWageSchema.getK11();                   //品质加扣款
            tWage[8] = tWage[8] + tLAWageSchema.getLastMoney();             //上期余额
            tWage[9] = tWage[9] + tLAWageSchema.getK13();                    //展业品质加扣款
            

            tTax[0] = tTax[0] + tLAWageSchema.getK01();                     //营业税及附加
            tTax[1] = tTax[1] + tLAWageSchema.getK02();                     //个人所得税
           }
           String[] tWageItem = new String[10];                                 //新增科目时需要在sour.xml中增加描述
           String[] tTaxItem = new String[2];
//           tWageItem[0] = "训练津贴";
           tWageItem[0] = "计划达成津贴";
           tWageItem[1] = "岗位津贴";
//           tWageItem[2] = "服务补贴";
           tWageItem[2] = "继续率奖金";
           tWageItem[3] = "督导服务奖金";
//           tWageItem[4] = "展业服务奖金";
           tWageItem[4] = "展业普通加扣款";
           tWageItem[5] = "年终奖金";
           tWageItem[6] = "加扣款";
           tWageItem[7] = "品质加扣款";
           tWageItem[8] = "上期余额";
           tWageItem[9] = "展业品质加扣款";

           tTaxItem[0] = "代理人营业税";             //营业税及附加
           tTaxItem[1] = "代理人个人所得税";          //个人所得税

           initVar();
           mMatchID ++;
           mManageCom = tManageCom;
           mSegment2 = tManageCom.concat("00");
           mAccountingDate = PubFun.getCurrentDate() ;
           mTransDate = mStartDate;
           mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;
           mPolNo = mBussNo;
           mSaleChnl ="05";//渠道划分，除展业奖金外其余佣金支出科目的渠道为专业

           double tSumMoney = 0;
           for(int j = 0;j < tWage.length;j++)
           {
              if (tWage[j] == 0)
              {
            	  dealError("LAWage", tWageNo, tRecordNo, "014", "022", tWageItem[j]+"没有相应的记录");
            	  continue;
              }
               
              tSumMoney=tSumMoney+tWage[j];
              tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]) );

              mBillID = "2200";
              mAccountCode = "佣金支出-中介督导津贴-" + tWageItem[j];            //中介督导津贴
              mEnteredDR = String.valueOf(tmoney);
              mEnteredCR = "";
              mHeadDescription = mManageCom + "的" + tWageItem[j];
              if (isExitInTab(mBussNo,mBussNo,mBillID))
               break;
              OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
              if(tOFInterfaceSchema != null)
               mOFInterfaceSet.add(tOFInterfaceSchema);
              dealLITranInfo();
           }

           double tTaxMoney=0;
           for(int j=0;j<tTax.length;j++)
           {
            if (tTax[j]==0)
            {
            	 dealError("LAWage", tWageNo, tRecordNo, "014", "022", tTaxItem[j]+"没有相应的记录");
            	 continue;
            }
             
            tTaxMoney=tTaxMoney+tTax[j];
            tmoney =new DecimalFormat("0.00").format(new Double(tTax[j]));

            mBillID = "2201";

            mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
            mEnteredDR = "";
            mEnteredCR = String.valueOf(tmoney);
            mHeadDescription = mManageCom+"的"+tTaxItem[j];
            if (isExitInTab(mBussNo,mBussNo,mBillID))
             break;
            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
            if(tOFInterfaceSchema != null)
             mOFInterfaceSet.add(tOFInterfaceSchema);
            dealLITranInfo();
           }

           double tMoneyShould = tSumMoney-tTaxMoney;
           if (tMoneyShould>0)
           {
              tmoney = new DecimalFormat("0.00").format(new Double(tMoneyShould));

              mBillID = "2202";
              mAccountCode = "应付佣金";          //应付附加佣金业务员佣金
              mEnteredDR = "";
              mEnteredCR = String.valueOf(tmoney);
              mHeadDescription = mManageCom + "的应付续收津贴支出";
              if (!isExitInTab(mBussNo,mBussNo,mBillID))
              {
                 OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                 if(tOFInterfaceSchema != null)
                 mOFInterfaceSet.add(tOFInterfaceSchema);
                 dealLITranInfo();
              }
           }
        }

//提取展业奖金
        tComSql = "select distinct(substr(ManageCom,1,6)) from LACommision "
                + "where WageNo='?tWageNo?' and branchtype='9' and ManageCom like concat('?cManageCom?','%')";
        SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
        sqlbv22.sql(tComSql);
        sqlbv22.put("tWageNo", tWageNo);
        sqlbv22.put("cManageCom", cManageCom);

        SSRS tSSRS2 = ttExeSQL.execSQL(sqlbv22);
        for (int k=1;k<=tSSRS2.MaxRow;k++)
        {
           String tManageCom = tSSRS2.GetText(k,1);
           String  tSql = "select riskcode,sum(m) from ( "
                        + "select riskcode,sum(Fyc) m from LACommision "
                        + "where ManageCom like concat('?tManageCom?','%') "
                        + "and commdire='1' and branchtype='9' and payyear < 1 "
                        + "and agentcode in(select agentcode from lawage where state='1' and branchtype='9' and "
                        + "indexcalno='?tWageNo?' and f25>=0) "
                        + "and caldate>='?caldate1?' and caldate<='?caldate2?' "
                        + "group by riskcode "
                        + "union all "
                        + "select riskcode,sum(directwage) m from LACommision "
                        + "where ManageCom like concat('?tManageCom?','%') "
                        + "and commdire='1' and branchtype='9' and payyear >= 1 "
                        + "and agentcode in(select agentcode from lawage where state='1' and branchtype='9' and "
                        + "indexcalno='?tWageNo?' and f25>=0) "
                        + "and caldate>='?caldate1?' and caldate<='?caldate2?' "
                        + "group by riskcode "
                        + ") g group by riskcode ";
           SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
           sqlbv23.sql(tSql);
           sqlbv23.put("tManageCom", tManageCom);
           sqlbv23.put("tWageNo", tWageNo);
           sqlbv23.put("caldate1", tCalDate[0]);
           sqlbv23.put("caldate2", tCalDate[1]);
           logger.debug(tSql);
           ExeSQL tExeSQL = new ExeSQL();
           SSRS tSSRS = tExeSQL.execSQL(sqlbv23);
           for (int i=1;i<=tSSRS.MaxRow;i++)
           {
              initVar();
              mMatchID ++ ;
              mManageCom = tManageCom;
              mSegment2 = tManageCom.concat("00");
              mAccountingDate = PubFun.getCurrentDate();
              mTransDate = mStartDate;
              tmoney = String.valueOf(tSSRS.GetText(i,2));
              if (Double.valueOf(tmoney).doubleValue() ==0)
              {
            	  dealError("LACommision", mManageCom+tSSRS.GetText(i, 1), tSSRS.GetText(i, 1), "004", "022", "直接佣金金额为0");
            	  continue;
              }
               
              mRiskCode = tSSRS.GetText(i,1);
              mSaleChnl = "10";  //渠道划分，展业奖金渠道放到10
//              mSaleChnl = "02";
              mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;
              mPolNo = mBussNo;
              for ( int j = 1; j <= 2; j++)
              {
                 if (j == 1)
                 {
                   mBillID = "2203";
                   mAccountCode = "佣金支出-中介督导津贴-展业服务奖金";        //展业奖金
                   mEnteredDR = String.valueOf(tmoney);
                   mEnteredCR = "";
                   mHeadDescription = mAccountCode + "险种" + mRiskCode;
                 }
                 else
                 {
                   mBillID = "2204";
                   mAccountCode = "应付佣金";                               //应付附加佣金业务员佣金
                   mEnteredDR = "";
                   mEnteredCR = String.valueOf(tmoney);
                   mHeadDescription = mAccountCode;
                 }
                 if (isExitInTab(mBussNo,mBussNo,mBillID))
                  break;
                 OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                 if(tOFInterfaceSchema != null)
                  mOFInterfaceSet.add(tOFInterfaceSchema);
                 dealLITranInfo();
              }
           }
        }
     return true;
    }

    /**
     * 预提佣金
     * **/
    private boolean PayYCommionJ()
    {

     String tCalDate[] = new String[2];
     String mStartDate = mStatYear+"-"+mStatMon+"-01";
     tCalDate = PubFun.calFLDate(mStartDate);
     String tWageNo = mStatYear+mStatMon;

      VData pData = new VData();
      pData.addElement(mGlobalInput);
      TransferData td = new TransferData();
      String accountDate =mAccountingDate=PubFun.getCurrentDate() ;
      td.setNameAndValue("tWageNo",tWageNo);
      td.setNameAndValue("accountDate",accountDate); //新增记帐日期
      td.setNameAndValue("itemp",mTime);
      td.setNameAndValue("Managecom",cManageCom); //新增管理机构
      pData.addElement(td);

      ReceiDataBL tReceiDataBL = new ReceiDataBL();
      if (!tReceiDataBL.submitData(pData, "Commion")) 
      {
    	  CError.buildErr(this, "获取应收保费失败");
        return false;
      }


      String tStartDate = tCalDate[0]; //默认业务日期为每月1号


      String mSql = "select distinct managecom from OfinaStorData where transdate='?tStartDate?' and vouchertype='62'  "
      			  + "and type in ('W','T')and managecom like concat('?cManageCom?','%')";
      SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
      sqlbv24.sql(mSql);
      sqlbv24.put("tStartDate", tStartDate);
      sqlbv24.put("cManageCom", cManageCom);
      ExeSQL mExeSQL = new ExeSQL();
      SSRS mSSRS = mExeSQL.execSQL(sqlbv24);
      for (int k = 1; k <= mSSRS.getMaxRow(); k++) {

        String tManageCom = mSSRS.GetText(k, 1);
        //附加佣金提取
        String wSql = "select * from OfinaStorData where transdate='?tStartDate?' and vouchertype='62' and type='W' "
        			+ "and managecom like concat('?tManageCom?','%')";
              logger.debug("查询预提佣金---------" + wSql);
              SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
              sqlbv25.sql(wSql);
              sqlbv25.put("tStartDate", tStartDate);
              sqlbv25.put("tManageCom", tManageCom);
      OfinaStorDataDB wOfinaStorDataDB = new OfinaStorDataDB();
      OfinaStorDataSet wOfinaStorDataSet = wOfinaStorDataDB.executeQuery(sqlbv25);

        if (wOfinaStorDataSet.size() == 0)
        {
        	dealError("OfinaStorData", tStartDate, "OfinaStorData", "002", "062", "查找OfinaStorData中间表的附加佣金结果为0");
          return true;
        }

        double tSumMoney;


        initVar();
        mMatchID++;
        mManageCom = tManageCom;
        mSegment2 = tManageCom.concat("00");
        mTransDate = tStartDate;
        mAccountingDate = PubFun.getCurrentDate();
        mBillID = "6201";
        mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID; ;
        mPolNo = mBussNo;

        tSumMoney = 0;
        for(int j=1;j<=wOfinaStorDataSet.size();j++) {
        	mSaleChnl = "02";
          OfinaStorDataSchema sOfinaStorDataSchema = new OfinaStorDataSchema();
          sOfinaStorDataSchema = wOfinaStorDataSet.get(j);
          if (sOfinaStorDataSchema.getMoney() <= 0.0) 
          {
        	  dealError("LAWage", mBussNo, mBussNo, "004", "010",sOfinaStorDataSchema.getSegment1()+"金额为0");
            continue;
          }

          tSumMoney = tSumMoney + sOfinaStorDataSchema.getMoney();

          tmoney = new DecimalFormat("0.00").format(new Double(sOfinaStorDataSchema.getMoney()));
          mAccountCode = "佣金支出-附加佣金(业务员佣金)-" + sOfinaStorDataSchema.getSegment1(); //新附加佣金业务员佣金支出
          if (sOfinaStorDataSchema.getSegment1().equalsIgnoreCase("差勤扣款")) {
            mAccountCode = "其他应付款-" + sOfinaStorDataSchema.getSegment1(); //附加佣金业务员佣金支出
          }
          if (sOfinaStorDataSchema.getSegment1().indexOf("推动费") > -1) {
            mAccountCode = "佣金支出-附加佣金(推动费)-" + sOfinaStorDataSchema.getSegment1(); //推动费
          }
          mEnteredDR = String.valueOf(tmoney);
          mEnteredCR = "";
          mHeadDescription = mManageCom + "的" + sOfinaStorDataSchema.getSegment1();
          if (isExitInTab(mBussNo, mBussNo, mBillID))
            break;
          OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
          if (tOFInterfaceSchema != null)
            mOFInterfaceSet.add(tOFInterfaceSchema);
          dealLITranInfo();
        }

        String tSql = "select * from OfinaStorData where transdate='?tStartDate?' and vouchertype='62' and "
        			+ "type='T' and managecom like concat('?tManageCom?','%')";
        logger.debug("查询预提佣金---------" + tSql);
        SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
        sqlbv26.sql(tSql);
        sqlbv26.put("tStartDate", tStartDate);
        sqlbv26.put("tManageCom", tManageCom);
        OfinaStorDataDB tOfinaStorDataDB = new OfinaStorDataDB();
        OfinaStorDataSet tOfinaStorDataSet = tOfinaStorDataDB.executeQuery(sqlbv26);

        mBillID = "6202";
        double tTaxMoney = 0;
        for (int j=1;j<=tOfinaStorDataSet.size();j++) {
          OfinaStorDataSchema tOfinaStorDataSchema = new OfinaStorDataSchema();
          tOfinaStorDataSchema = tOfinaStorDataSet.get(j);
          if (tOfinaStorDataSchema.getMoney() <= 0.0) 
          {
        	  dealError("OfinaStorData", mBussNo, mBussNo, "004", "062", "直佣金额为0");
            continue;
          }

          tTaxMoney = tTaxMoney + tOfinaStorDataSchema.getMoney();
          tmoney = new DecimalFormat("0.00").format(new Double(tOfinaStorDataSchema.getMoney()));
          mAccountCode = "应交税费-代扣税项-" + tOfinaStorDataSchema.getSegment1();
          if (tOfinaStorDataSchema.getSegment1().equalsIgnoreCase("推动费加扣款")) {
            mAccountCode = "应付佣金-" + tOfinaStorDataSchema.getSegment1();
          }

          mEnteredDR = "";
          mEnteredCR = String.valueOf(tmoney);
          mHeadDescription = mManageCom + "的" + tOfinaStorDataSchema.getSegment1();
          if (isExitInTab(mBussNo, mBussNo, mBillID))
            break;
          OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
          if (tOFInterfaceSchema != null)
            mOFInterfaceSet.add(tOFInterfaceSchema);
          dealLITranInfo();
        }

        double tMoneyShould = tSumMoney - tTaxMoney;
        if (tMoneyShould > 0) {
          tmoney = new DecimalFormat("0.00").format(new Double(tMoneyShould));

          mBillID = "6203";
          mAccountCode = "应付佣金"; //应付附加佣金业务员佣金
          mEnteredDR = "";
          mEnteredCR = String.valueOf(tmoney);
          mHeadDescription = mManageCom + "的" + "应付佣金支出";
          if (!isExitInTab(mBussNo, mBussNo, mBillID)) {
            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
            if (tOFInterfaceSchema != null)
              mOFInterfaceSet.add(tOFInterfaceSchema);
            dealLITranInfo();
          }
        }
      }

    //提取直接佣金
    String mfSql = "select distinct managecom from OfinaStorData where transdate='?tStartDate?' and vouchertype='62' "
    			 + "and type='F' and managecom like concat('?cManageCom?','%')";
    ExeSQL mfExeSQL = new ExeSQL();
    SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
    sqlbv27.sql(mfSql);
    sqlbv27.put("tStartDate", tStartDate);
    sqlbv27.put("cManageCom", cManageCom);
    SSRS mfSSRS = mfExeSQL.execSQL(sqlbv27);
    for (int k = 1; k <= mfSSRS.getMaxRow(); k++) {

      String tManageCom = mfSSRS.GetText(k, 1);

      String fSql = "select * from OfinaStorData where transdate='?tStartDate?' and vouchertype='62' and type='F' "
      			  + "and managecom like concat('?tManageCom?','%')";
      logger.debug("查询预提佣金---------" + fSql);
      OfinaStorDataDB fOfinaStorDataDB = new OfinaStorDataDB();
      SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
      sqlbv28.sql(fSql);
      sqlbv28.put("tStartDate", tStartDate);
      sqlbv28.put("tManageCom", tManageCom);
      OfinaStorDataSet fOfinaStorDataSet = fOfinaStorDataDB.executeQuery(sqlbv28);

      for (int i = 1; i <= fOfinaStorDataSet.size(); i++) {
        OfinaStorDataSchema fOfinaStorDataSchema = new OfinaStorDataSchema();
        fOfinaStorDataSchema = fOfinaStorDataSet.get(i);
        tmoney = new DecimalFormat("0.00").format(new Double(fOfinaStorDataSchema.getMoney()));
        if (fOfinaStorDataSchema.getMoney() <= 0.0) 
        {
        	dealError("OfinaStorData", fOfinaStorDataSchema.getRiskCode(), tStartDate, "004", "062", "直佣金额为非正数");
          continue;
        }

        initVar();
        mMatchID++;
        mManageCom = tManageCom;
        mTransDate = tStartDate;
        mAccountingDate = PubFun.getCurrentDate();
        mSegment2 = tManageCom.concat("00");
        mRiskCode = fOfinaStorDataSchema.getRiskCode();
        mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID; ;
        mPolNo = mBussNo;
//        mSaleChnl = tranAgentmSaleChnl(fOfinaStorDataSchema.getSaleChnl().trim());
        mSaleChnl = "02";
        String tPayYear = fOfinaStorDataSchema.getSegment1();
        if (tPayYear.equals("0"))
          tPayYear = "首期佣金";
        else
          tPayYear = "续期佣金";

        for (int j = 1; j <= 2; j++) {
          if (j == 1) {
            mBillID = "6204";
            mAccountCode = "佣金支出-直接佣金-" + tPayYear; //直接佣金支出
            mEnteredDR = String.valueOf(tmoney);
            mEnteredCR = "";
          }
          else {
            mBillID = "6205";
            mAccountCode = "应付佣金"; //应付直接佣金业务
            mEnteredDR = "";
            mEnteredCR = String.valueOf(tmoney);
          }
          if (isExitInTab(mBussNo, mBussNo, mBillID))
            break;
          OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
          if (tOFInterfaceSchema != null)
            mOFInterfaceSet.add(tOFInterfaceSchema);
          dealLITranInfo();
        }
      }
    }

    return true;
  }

    /*提取展业渠道的佣金*/
    private boolean getReBranch()
	{

   
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);
        LAWageDB tLAWageDB = new LAWageDB();

        String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWage where IndexCalNo='?tWageNo?' "
        			   + "and ManageCom like concat('?cManageCom?','%') and branchtype='99' ";
        SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
        sqlbv29.sql(tComSql);
        sqlbv29.put("tWageNo", tWageNo);
        sqlbv29.put("cManageCom", cManageCom);
        logger.debug("query sql : " + tComSql);
        ExeSQL ttExeSQL = new ExeSQL();
        SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv29);
        for (int k=1;k<=tSSRS1.MaxRow;k++)
        {
            String tManageCom=tSSRS1.GetText(k,1);
            //附加佣金提取
            String tSql = "select * from LAWage where IndexCalNo='?tWageNo?' and ManageCom like concat('?tManageCom?','%') "
            			+ "and branchtype='99' and f25 >= 0 and state='1' order by managecom";
            logger.debug(tSql);
            SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
            sqlbv30.sql(tSql);
            sqlbv30.put("tWageNo", tWageNo);
            sqlbv30.put("tManageCom", tManageCom);
            LAWageSet tLAWageSet = tLAWageDB.executeQuery(sqlbv30);
            if (tLAWageSet.size()==0)
            {
            	dealError("LAWage", tWageNo, tRecordNo, "014", "081", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"没有相应的展业佣金记录");
                continue;
            }
            for (int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                if (tLAWageSchema.getState() == null || tLAWageSchema.getState().equals("0"))
                {
                	dealError("LAWage", tWageNo, tRecordNo, "015", "081", "管理机构为"+tManageCom+"，佣金月为"+tWageNo+"的展业佣金记录未审核");
                    return true;
                }
            }

            double[] tWage = new double[22];
            double[] tTax = new double[2];

            for(int i=1;i<=tLAWageSet.size();i++)
            {
                LAWageSchema tLAWageSchema = new LAWageSchema();
                tLAWageSchema.setSchema(tLAWageSet.get(i));
                tWage[0] = tWage[0] + tLAWageSchema.getF12();                     //展业达成津贴
                tWage[1] = tWage[1] + tLAWageSchema.getF08();                     //展业超额奖金
                tWage[2] = tWage[2] + tLAWageSchema.getF10();                     //年终奖金
                tWage[3] = tWage[3] + tLAWageSchema.getF06();                     //训练津贴
                tWage[4] = tWage[4] + tLAWageSchema.getF11();                     //岗位津贴
                tWage[5] = tWage[5] + tLAWageSchema.getF13();                     //继续率奖金
                tWage[6] = tWage[6] + tLAWageSchema.getF14();                     //职务津贴
                tWage[7] = tWage[7] + tLAWageSchema.getF16();                     //直接管理津贴
                tWage[8] = tWage[8] + tLAWageSchema.getF18();                     //推荐奖金
                tWage[9] = tWage[9] + tLAWageSchema.getF19();                     //转正奖金
                tWage[10] = tWage[10] + tLAWageSchema.getF20();                   //增才奖金
                tWage[11] = tWage[11] + tLAWageSchema.getF21();                   //辅导奖金
                tWage[12] = tWage[12] + tLAWageSchema.getF22();                   //增区奖金
                tWage[13] = tWage[13] + tLAWageSchema.getF23();                   //增科奖金
                tWage[14] = tWage[14] + tLAWageSchema.getF24();                   //增处奖金
                tWage[15] = tWage[15] - tLAWageSchema.getK21();                          //差勤扣款（税后）
                tWage[16] = tWage[16] - tLAWageSchema.getF17();                   //养老金（税后）
                tWage[17] = tWage[17] + tLAWageSchema.getF09();                   //还垫复效奖金
                tWage[18] = tWage[18] + tLAWageSchema.getF30();                  //加扣款
                tWage[19] = tWage[19] + tLAWageSchema.getLastMoney();             //上期余额
                tWage[20] = tWage[20] + tLAWageSchema.getF07();                 //收费服务津贴
                tWage[21] = tWage[21] + tLAWageSchema.getF15();                 //收费服务管理津贴


                tTax[0] = tTax[0] + tLAWageSchema.getK01();                       //营业税及附加
                tTax[1] = tTax[1] + tLAWageSchema.getK02();                       //个人所得税
            }


            String[] tWageItem = new String[22];                                  
            String[] tTaxItem = new String[2];
            tWageItem[0] = "展业达成津贴";
            tWageItem[1] = "展业超额奖金";
            tWageItem[2] = "年终奖金";
            tWageItem[3] = "训练津贴";
            tWageItem[4] = "岗位津贴";
            tWageItem[5] = "继续率奖金";
            tWageItem[6] = "职务津贴";
            tWageItem[7] = "直接管理津贴";
            tWageItem[8] = "推荐奖金";
            tWageItem[9] = "转正奖金";
            tWageItem[10] = "增才奖金";
            tWageItem[11] = "辅导奖金";
            tWageItem[12] = "增区奖金";
            tWageItem[13] = "增科奖金";
            tWageItem[14] = "增处奖金";
            tWageItem[15] = "差勤扣款";
            tWageItem[16] = "养老金";
            tWageItem[17] = "复效奖金";          //还垫复效奖金             
            tWageItem[18] = "加扣款";
            tWageItem[19] = "上期余额";
            tWageItem[20] = "收费服务津贴";
            tWageItem[21] = "收费服务管理津贴";


            tTaxItem[0] = "代理人营业税";        //营业税及附加
            tTaxItem[1] = "代理人个人所得税";    //个人所得税

            initVar();
            mMatchID ++ ;
            mManageCom = tManageCom;
            mSegment2 = tManageCom.concat("00");
            mAccountingDate = PubFun.getCurrentDate();
            mTransDate = mStartDate;
            mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
            mPolNo = mBussNo;

            double tSumMoney = 0;
            for(int j = 0;j < tWage.length;j++)
            {
            	mSaleChnl = "10";
                if (tWage[j] == 0)
                {
                	
                	dealError("LAWage", mBussNo, mBussNo, "004", "081", tWageItem[j]+"金额为0");
                    continue;
                }
                tSumMoney=tSumMoney+tWage[j];
                tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]));

                mBillID = "8100";
                if(tWageItem[j].equalsIgnoreCase("复效奖金") || tWageItem[j].equalsIgnoreCase("收费服务津贴") 
                		|| tWageItem[j].equalsIgnoreCase("收费服务管理津贴"))
                mAccountCode = "佣金支出-续收津贴-"+tWageItem[j];
                else
                mAccountCode = "佣金支出-收展津贴-"+tWageItem[j];                 //展业津贴
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mManageCom + "的" + tWageItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tTaxMoney=0;
            for(int j=0;j<tTax.length;j++)
            {
            	mSaleChnl = "10";
                if (tTax[j]==0)
                {
                	dealError("LAWage", mBussNo, mBussNo, "004", "081", tTaxItem[j]+"金额为0");
                    continue;
                }
                tTaxMoney=tTaxMoney+tTax[j];
                tmoney = new DecimalFormat("0.00").format(new Double(tTax[j]));

                mBillID = "8101";
                mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription = mManageCom + "的" + tTaxItem[j];
                if (isExitInTab(mBussNo,mBussNo,mBillID))
                    break;
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }

            double tMoneyShould = tSumMoney-tTaxMoney;
            if (tMoneyShould>0)
            {
                tmoney =new DecimalFormat("0.00").format(new Double(tMoneyShould));

                mBillID = "8102";
                mAccountCode="应付佣金";                                    //应付附加佣金业务员佣金
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
                mHeadDescription =mManageCom + "的应付展业津贴支出";
                if (!isExitInTab(mBussNo,mBussNo,mBillID))
                {
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }

        //提取直接佣金
        tComSql = "select distinct(substr(ManageCom,1,6)) from LACommision "
                + "where WageNo='?tWageNo?' and ManageCom like concat('?cManageCom?','%') ";
        SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
        sqlbv31.sql(tComSql);
        sqlbv31.put("tWageNo", tWageNo);
        sqlbv31.put("cManageCom", cManageCom);
        SSRS tSSRS2 = ttExeSQL.execSQL(sqlbv31);
        for (int k=1;k<=tSSRS2.MaxRow;k++)
        {
            String tManageCom=tSSRS2.GetText(k,1);
            String tSql = " select riskcode,payyear,sum(fyc) from (select riskcode,payyear,fyc from lacommision "
            			+ "where wageno='?tWageNo?' and  agentcode in (select agentcode from lawage where state='1' "
            			+ "and indexcalno='?tWageNo?' and f25>=0 and branchtype='99' )"
                        + "and payyear<1 and commdire='1' and branchtype='99' and managecom like concat('?tManageCom?','%') "
                        + "and commisionsn not in (select commisionsn from lacommision a where a.agentcode in ( " 
                        + "select agentcode from lawage where state='1' and indexcalno='?tWageNo?' and f25>=0 "
                        + "and branchtype='99') and managecom like concat('?tManageCom?','%') and a.wageno='?tWageNo?' and "
                        + "a.payyear<1 and a.p5='1' and a.commdire='1'  and a.branchtype='99' "
                        + " and exists(select 'X' from lrascription  b where  b.contno=a.contno and b.riskcode=a.riskcode "
                        + "and b.ascriptiondate>='2005-12-1'"
                        + "union all select 'X' from lrascriptionb b where  b.contno=a.contno and b.riskcode=a.riskcode "
                        + "and b.ascriptiondate>='2005-12-1'))"
                        +" union all "
                        + " select riskcode,payyear,fyc from lacommision where wageno='?tWageNo?' and agentcode in "
                        + "(select agentcode from lawage where state='1' and indexcalno='?tWageNo?' and f25>=0 and branchtype='99' )"
                        + "and payyear>=1 and commdire='1' and branchtype='99' and managecom like concat('?tManageCom?','%') "
                        + "and commisionsn not in (select commisionsn from lacommision a where a.agentcode in (select agentcode "
                        + "from lawage where state='1' and indexcalno='?tWageNo?' and f25>=0 and branchtype='99') and "
                        + "managecom like concat('?tManageCom?','%') and a.wageno='?tWageNo?' and a.payyear>=1 and a.commdire='1'  "
                        + "and a.branchtype='99' "
                        + " and exists(select 'X' from lrascription  b where  b.contno=a.contno and b.riskcode=a.riskcode and "
                        + "b.ascriptiondate>='2005-12-1'"
                        + "union all select 'X' from lrascriptionb b where  b.contno=a.contno and b.riskcode=a.riskcode and "
                        + "b.ascriptiondate>='2005-12-1'))) g "
                        +"group by riskcode,payyear";
            SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
            sqlbv32.sql(tSql);
            sqlbv32.put("tWageNo", tWageNo);
            sqlbv32.put("tManageCom", tManageCom);

            logger.debug(tSql);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(sqlbv32);
            for (int i=1;i<=tSSRS.MaxRow;i++)
            {
                initVar();
                mMatchID ++ ;
                mManageCom = tManageCom;
                mAccountingDate = PubFun.getCurrentDate();
                mTransDate = mStartDate;
                mSegment2 = tManageCom.concat("00");
                tmoney = String.valueOf(tSSRS.GetText(i,3));
                if (Double.valueOf(tmoney).doubleValue() == 0)
                {
                	dealError("lacommision", mBussNo, mBussNo, "004", "010", "直佣金额为0");
                    continue;
                }
                mRiskCode = tSSRS.GetText(i,1);
                mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;;
                mPolNo = mBussNo;
//                mSaleChnl ="02";
                mSaleChnl = "10";
                String tPayYear = tSSRS.GetText(i,2);
                if(tPayYear.equals("0"))
                    tPayYear = "首期佣金";
                else
                    tPayYear = "续期佣金";

                for ( int j = 1; j <=2; j++)
                {
                    if (j == 1)
                    {
                        mBillID = "8104";
                        mAccountCode = "佣金支出-直接佣金-" + tPayYear;          //直接佣金支出
                        mEnteredDR = String.valueOf(tmoney);
                        mEnteredCR = "";
                    }
                    else
                    {
                        mBillID = "8105";
                        mAccountCode = "应付佣金";                              //应付直接佣金业务
                        mEnteredDR = "";
                        mEnteredCR = String.valueOf(tmoney);
                    }
                    if (isExitInTab(mBussNo,mBussNo,mBillID))
                        break;
                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }
        return true;
    }

    /*提取业务员押金收费*/
    private boolean getAgentChargeFee()
	{
        //业务员押金收费
        String tSql = "select * from LAAssuMoney  where ConfMakeDate ='?mToday?' and SManageCom like concat('?cManageCom?','%')" ;
        SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
        sqlbv33.sql(tSql);
        sqlbv33.put("mToday", mToday);
        sqlbv33.put("cManageCom", cManageCom);
        logger.debug("查询押金收费记录--------"+tSql);
        LAAssuMoneyDB tAssuMoneyDB = new LAAssuMoneyDB();
        LAAssuMoneySet tLAAssuMoneySet = tAssuMoneyDB.executeQuery(sqlbv33);
        for (int i = 1; i <= tLAAssuMoneySet.size(); i++)
        {
        	LAAssuMoneySchema tLAAssuMoneySchema = new LAAssuMoneySchema();
        	tLAAssuMoneySchema = tLAAssuMoneySet.get(i);
        	if(tLAAssuMoneySchema.getAssuMoney()<=0)
        	{
        		dealError("LAAssuMoney", tLAAssuMoneySchema.getReceiptNo(), tLAAssuMoneySchema.getSerialNo(), "004", "082", "金额为0");
        		continue;
        	}


            if(tLAAssuMoneySchema.getAssuMoney()> 0)
            {
                //为变量附值
                initVar();
                mMatchID ++;
                mPolNo = tLAAssuMoneySchema.getSerialNo();  //内部流水号
                mBussNo = tLAAssuMoneySchema.getReceiptNo();   //收据号
                mTransDate = tLAAssuMoneySchema.getConfMakeDate();
                mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
                mSegment2 = tLAAssuMoneySchema.getSManageCom();
                mManageCom = tLAAssuMoneySchema.getSManageCom().substring(0,6);
                tmoney = new DecimalFormat("0.00").format(new Double(tLAAssuMoneySchema.getAssuMoney()) );
                String tFailFlag="0";

                for ( int j = 1; j <=2; j++)
                {
                    if (j == 1)
                    {
                       if ("1".equals(tLAAssuMoneySchema.getAssuPayMode()))  
                       {

                          mBillID = "8201";
                          mAccountCode = "库存现金-人民币-业务";
                       }
                       else if("3".equals(tLAAssuMoneySchema.getAssuPayMode()))
                       {
                           mBillID = "8204";
                           mAccountCode = "银行存款-活期-人民币";                 //银行支票
                           mAccountSubCode = "支" + mManageCom ;
                    	   
                       }
                       else if ("5".equals(tLAAssuMoneySchema.getAssuPayMode()))
                       {
                           mBillID = "8205";
                           mAccountCode = "应付业务支出-内部转帐";                    //内部转账借
                       }
                       else
                       {
                           if (tLAAssuMoneySchema.getBankCode()==null)      //银行托收银行代码为空
                           {
                               dealError("LJTempFeeClass",tLAAssuMoneySchema.getReceiptNo(),
                            		   tLAAssuMoneySchema.getReceiptNo(),"082","008","银行托收编码为空");
                               tFailFlag="1";
                               break;
                           }
                           mBillID = "8202";
                           mAccountCode = "银行存款-活期-人民币";                
                           mAccountSubCode = "收" + tLAAssuMoneySchema.getBankCode();
                       }

                        mEnteredDR = String.valueOf(tmoney);                    //借
                        mEnteredCR = "";                                        //贷
                        mHeadDescription = "收" + tLAAssuMoneySchema.getAgentName() + "业务员押金";
                    }
                    else
                    {
                        if (tFailFlag.equals("1"))
                            continue;
                        mBillID = "8203";
                        mAccountCode = "其他应付款-业务员押金";                     
                        mEnteredDR = "";                                        //借
                        mEnteredCR = String.valueOf(tmoney);                    //贷
                        mHeadDescription = "收" + tLAAssuMoneySchema.getAgentName() + "业务员押金";
                    }

                    if (isExitInTab(mBussNo,mPolNo,mBillID))
                        break;

                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }
        return true;
    }
    
    
    /*提取业务员押金付费*/
    private boolean getAgentChargePay()
	{
        //业务员押金付费
        String tSql = "select * from LAJAGetAssuMoney  where GetConfirmDate ='?mToday?' and FManageCom like concat('?cManageCom?','%')" ;
        SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
        sqlbv34.sql(tSql);
        sqlbv34.put("mToday", mToday);
        sqlbv34.put("cManageCom", cManageCom);
        logger.debug("查询押金付费记录--------"+tSql);
        LAJAGetAssuMoneyDB tLAJAGetAssuMoneyDB = new LAJAGetAssuMoneyDB();
        LAJAGetAssuMoneySet tLAJAGetAssuMoneySet = tLAJAGetAssuMoneyDB.executeQuery(sqlbv34);
        for (int i = 1; i <= tLAJAGetAssuMoneySet.size(); i++)
        {
        	LAJAGetAssuMoneySchema tLAJAGetAssuMoneySchema = new LAJAGetAssuMoneySchema();
        	tLAJAGetAssuMoneySchema = tLAJAGetAssuMoneySet.get(i);

        	if(tLAJAGetAssuMoneySchema.getAssuGetMoney()<=0)
        	{
        		dealError("LAJAGetAssuMoney", tLAJAGetAssuMoneySchema.getGetNoticeNo(), 
        				tLAJAGetAssuMoneySchema.getReceiptNo(), "004", "010", "金额为0");
        		continue;        		
        	}

            if(tLAJAGetAssuMoneySchema.getAssuGetMoney()> 0)
            {
                //为变量附值
                initVar();
                mMatchID ++;
                mPolNo = tLAJAGetAssuMoneySchema.getReceiptNo();  //收据号
                mBussNo =tLAJAGetAssuMoneySchema.getGetNoticeNo();   //给付通知书号
                mTransDate = tLAJAGetAssuMoneySchema.getGetConfirmDate();
                mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
                mSegment2 = tLAJAGetAssuMoneySchema.getFManageCom();
                mManageCom =tLAJAGetAssuMoneySchema.getFManageCom().substring(0,6);
                tmoney = new DecimalFormat("0.00").format(new Double(tLAJAGetAssuMoneySchema.getAssuGetMoney()) );

                for ( int j = 1; j <=2; j++)
                {
                    if (j == 1)
                    {
                        mBillID = "8301";
                        mAccountCode = "其他应付款-业务员押金";         
                        mEnteredDR = String.valueOf(tmoney);                    //借
                        mEnteredCR = "";                                        //贷
                        mHeadDescription = "付" + tLAJAGetAssuMoneySchema.getAgentName() + "业务员押金";
 
                    }
                    else
                    {
                        if(!tLAJAGetAssuMoneySchema.getGetMode().equals("") && !tLAJAGetAssuMoneySchema.getGetMode().equals("1"))
                        {
                            if (tLAJAGetAssuMoneySchema.getGetMode().equals("5"))
                            {
                                mBillID = "8304";
                                mAccountCode = "应付业务支出-内部转帐" ;       //内部转账贷
                            }
                            else
                            {
                                mBillID = "8303";
                                mAccountCode = "银行存款-活期-人民币";                
                                mAccountSubCode = "付" + tLAJAGetAssuMoneySchema.getBankCode() ;
                            }
                            
                        }
                        else
                        {
                            mBillID = "8302";
                            mAccountCode = "库存现金-人民币-业务";
                        }

                         mEnteredDR = "";                                        //借
                         mEnteredCR = String.valueOf(tmoney);                    //贷
                         mHeadDescription = "付" + tLAJAGetAssuMoneySchema.getAgentName() + "业务员押金";

                    }

                    if (isExitInTab(mBussNo,mPolNo,mBillID))
                        break;

                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                    if(tOFInterfaceSchema != null)
                        mOFInterfaceSet.add(tOFInterfaceSchema);
                    dealLITranInfo();
                }
            }
        }
        return true;
    }
    
    //衔接资金凭证提取
    private boolean PayLWage()
    {

        String tCurrDate = PubFun.getCurrentDate();     //凭证提取为操作月当月
        String DWageNo = tCurrDate.substring(0,4).trim()+tCurrDate.substring(5,7).trim();
        String tCalDate[] = new String[2];
        tCalDate = PubFun.calFLDate(tCurrDate);
        String FWageNo = mStatYear + mStatMon;
        logger.debug("DWageNo:"+DWageNo + "---FWageNo:" + FWageNo);
        LALinkupWageDB tLinkupWageDB = new LALinkupWageDB();
        LALinkupWageSchema tLinkupWageSchema = null;
        LALinkupWageSet tLinkupWageSet = new LALinkupWageSet();
 
        String tSql = "select * from LALinkupWage where FWageNo='?FWageNo?' and DWageNo='?DWageNo?' "
                    + "and Com3Code like concat('?cManageCom?','%')  and ConfState='1'";
        logger.debug("查询凭证信息---------------"+tSql);
        SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
        sqlbv35.sql(tSql);
        sqlbv35.put("FWageNo", FWageNo);
        sqlbv35.put("DWageNo", DWageNo);
        sqlbv35.put("cManageCom", cManageCom);
        tLinkupWageSet = tLinkupWageDB.executeQuery(sqlbv35);
        if (tLinkupWageSet.size()==0)
        {
        	dealError("LALinkupWage", FWageNo, DWageNo, "014", "084", "管理机构为"+cManageCom+"，佣金月为"+FWageNo+"没有相应的衔接资金记录");
            return true;
        }

        double tSumMoney;
        double[] tWage;
        double[] tTax;
        String[] tWageItem;
        String[] tTaxItem;

        for (int i = 1; i <= tLinkupWageSet.size(); i++)
        {
        	tLinkupWageSchema = new LALinkupWageSchema();
        	tLinkupWageSchema = tLinkupWageSet.get(i);
            if ("".equals(tLinkupWageSchema.getConfState()) || "0".equals(tLinkupWageSchema.getConfState()))
            {
            	dealError("LALinkupWage", FWageNo, DWageNo, "015", "084", "管理机构为"+cManageCom+"，佣金月为"+FWageNo+"的衔接资金记录未审核");
                return true;
            }
        }

        tWage = new double[3];
        tTax = new double[2];
        for (int i = 1; i <= tLinkupWageSet.size(); i++)
        {
        	tLinkupWageSchema = new LALinkupWageSchema();
        	tLinkupWageSchema = tLinkupWageSet.get(i);           
            tWage[0] = tWage[0] + tLinkupWageSchema.getT1();               //同业衔接加扣款
            tWage[1] = tWage[1] + tLinkupWageSchema.getT2();               //奖惩款
            tWage[2] = tWage[2] - tLinkupWageSchema.getT7();               //差勤扣款实扣


            tTax[0] = tTax[0] + tLinkupWageSchema.getT4();                 //营业税及附加
            tTax[1] = tTax[1] + tLinkupWageSchema.getT5();                 //个人所得税

        }

        tWageItem = new String[3];
        tTaxItem = new String[2];
        tWageItem[0] = "同业衔接资金(加扣款2)";
        tWageItem[1] = "奖惩款项";
        tWageItem[2] = "差勤扣款";

        tTaxItem[0] = "代理人营业税";
        tTaxItem[1] = "代理人个人所得税";

        initVar();
        mMatchID ++ ;
        mManageCom = cManageCom;
        mAccountingDate = tCurrDate;
        mTransDate = tCalDate[0];
        mBillID = "8401";
        mBussNo = String.valueOf(mTime) + cManageCom + FWageNo + mMatchID;;
        mPolNo = String.valueOf(mTime) + cManageCom +DWageNo+ mMatchID;

        tSumMoney = 0;
        for(int j=0 ; j<tWage.length ; j++)
        {
        	mSaleChnl="02";  //个险
            if (tWage[j]==0)
            {               	
            	dealError("LALinkupWage", mBussNo, mBussNo, "004", "084", tWageItem[j]+"金额为0");
                continue;
            }
            	                
            tSumMoney = tSumMoney + tWage[j];

            tmoney = new DecimalFormat("0.00").format(new Double(tWage[j]));
            mAccountCode = "佣金支出-附加佣金(业务员佣金)-"+tWageItem[j];   //附加佣金业务员佣金支出
            if(tWageItem[j].equalsIgnoreCase("差勤扣款"))
            {
                mAccountCode = "其他应付款-"+tWageItem[j];               //附加佣金业务员佣金支出
            }

            mEnteredDR = String.valueOf(tmoney);
            mEnteredCR = "";
            mHeadDescription = mManageCom + "的" + tWageItem[j];
            if (isExitInTab(mBussNo,mPolNo,mBillID))
                break;
            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
            if(tOFInterfaceSchema != null)
                mOFInterfaceSet.add(tOFInterfaceSchema);
            dealLITranInfo();
        }

        mBillID = "8402";
        double tTaxMoney=0;
        for(int j=0;j<tTax.length;j++)
        {
            if (tTax[j]==0)
            {
            	dealError("LALinkupWage", mBussNo, mBussNo, "004", "084", tTaxItem[j]+"金额为0");
            	continue;
            }
                
            tTaxMoney = tTaxMoney + tTax[j];
            tmoney = new DecimalFormat("0.00").format(new Double(tTax[j]));
            mAccountCode = "应交税费-代扣税项-" + tTaxItem[j];
            mEnteredDR = "";
            mEnteredCR = String.valueOf(tmoney);
            mHeadDescription = mManageCom + "的" + tTaxItem[j];
            if (isExitInTab(mBussNo,mPolNo,mBillID))
                break;
            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
            if(tOFInterfaceSchema != null)
                mOFInterfaceSet.add(tOFInterfaceSchema);
            dealLITranInfo();
        }
        double summoney = 0;
        for (int i = 1; i <= tLinkupWageSet.size(); i++)
        {  
        	summoney += tLinkupWageSet.get(i).getT8();
        }
        tmoney = new DecimalFormat("0.00").format(new Double(summoney));


            mBillID = "8403";
            mAccountCode = "应付佣金";                                  //应付附加佣金业务员佣金
            mEnteredDR = "";
            mEnteredCR = String.valueOf(tmoney);
            mHeadDescription = mManageCom + "的" + "应付佣金支出";
            if (!isExitInTab(mBussNo,mPolNo,mBillID))
            {
                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                if(tOFInterfaceSchema != null)
                    mOFInterfaceSet.add(tOFInterfaceSchema);
                dealLITranInfo();
            }
        return true;
    }
    
    //中介手续费
    private boolean PayAgencyFee()
    {
    	
    	  String tCalDate[] = new String[2];
          String mStartDate = mStatYear+"-"+mStatMon+"-01";
          tCalDate = PubFun.calFLDate(mStartDate);
          
          
          String ExistSql= "Select count(*),max(bussno),min(MatchID) From Ofinterface Where 1 = 1 And ReversedStatus='0' and Vouchertype='142' " 
  		     + "and transdate='?transdate?' and ManageCom = '?cManageCom?'";
   	   
   	   ExeSQL tExistSQL = new ExeSQL();
   	   SQLwithBindVariables sqlbv36=new SQLwithBindVariables();
   	   sqlbv36.sql(ExistSql);
   	   sqlbv36.put("transdate", mStatYear+"-"+mStatMon+"-01");
   	   sqlbv36.put("cManageCom", cManageCom);
   	   SSRS tExistSSRS1 = tExistSQL.execSQL(sqlbv36);
   	   
   	   if(!"0".equals(tExistSSRS1.GetText(1, 1)))
   	   {
   		   dealError("Ofinterface", tExistSSRS1.GetText(1, 2), tExistSSRS1.GetText(1, 2), "013", tExistSSRS1.GetText(1, 3), "管理机构为"+cManageCom+"，"+mStatYear+"年"+mStatMon+"月的中介手续费凭证已经提取");
   		   return true;
   	   }
          
         String tWageNo = mStatYear+mStatMon;
         logger.debug("tWageNo:"+tWageNo);
         
          String tMngSql = "select distinct(substr(ManageCom,1,6)),substr(agentcom,1,13) from lachargezj where wageno='?tWageNo?' "
          			   + "and ManageCom like concat('?cManageCom?','%') and branchtype in ('6','7') "
          			   + "union select distinct(substr(ManageCom,1,6)),agentcom from zjcontinuedrate where yearmonth = '?tWageNo?' "
  	        	   	   + "and  managecom like concat('?cManageCom?','%') ";

          ExeSQL ttExeSQL = new ExeSQL();
          logger.debug("查询6位管理机构--------"+tMngSql);
          SQLwithBindVariables sqlbv37=new SQLwithBindVariables();
          sqlbv37.sql(tMngSql);
          sqlbv37.put("tWageNo", tWageNo);
          sqlbv37.put("cManageCom", cManageCom);
          SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv37);
          for(int m=1;m<=tSSRS1.MaxRow;m++)
  	    {
          	String tManageCom = tSSRS1.GetText(m, 1);
          	//zy 调整中介机构为发生中介费用的中介机构
          	 String tAgentCom= tSSRS1.GetText(m, 2);
//  	        String tComSql = "select distinct agentcom from lacom  where branchtype in ('6','7') "
//  	        			   + "and length(trim(agentcom))=13 and managecom like '"+tManageCom+"%'  ";
//  	
//  	        logger.debug("查询本机构下的13位代理机构 : " + tComSql);
//  	
//  	        SSRS tComSSRS = ttExeSQL.execSQL(tComSql);
//  	        for (int k=1;k<=tComSSRS.MaxRow;k++)
//  	        {
//  	        	  String tAgentCom=tComSSRS.GetText(k,1);
  	        	  
  	        	  //非意外险、短期健康险,
  		           initVar();
  		           mManageCom = tManageCom;
  		           mAccountingDate = PubFun.getCurrentDate();
  		           mTransDate = mStartDate;
  		           mMatchID ++ ;
  		           mBussNo = String.valueOf(mTime) + tAgentCom + tWageNo + mMatchID;
  		         
  		           double qSumMoney = 0;
  		          
  		           //提取首续期手续费,与销售保持一致，代理机构取前13位
  		           String tSql = " select c.chargetype,a.payyear,c.riskcode, sum(c.charge) from lachargezj c, lacommision a "
  				        	   + "where a.CommisionSN = c.CommisionSN and c.chargetype in ('A1','A2') and c.branchtype in ('6', '7') "        	   
  				        	   + "and c.wageno = '?tWageNo?' and substr(c.agentcom,1,13)='?tAgentCom?' and c.managecom like concat('?tManageCom?','%') "
  				        	   + "and c.riskcode not in (select riskcode from lmriskapp a where a.RiskType='H' And riskcode In  "
  				        	   + "(select riskcode from lmriskapp where RiskPeriod In ('S','M')) "
  				       	       + "Union (select riskcode From lmriskapp where RiskType='A')) "
  				        	   + "group by  c.chargetype,a.payyear,c.riskcode ";
  		
  		           logger.debug("查询手续费-------------"+tSql);
  		           ExeSQL tExeSQL = new ExeSQL();
  		           SQLwithBindVariables sqlbv38=new SQLwithBindVariables();
  		           sqlbv38.sql(tSql);
  		           sqlbv38.put("tWageNo", tWageNo);
  		           sqlbv38.put("tAgentCom", tAgentCom);
  		           sqlbv38.put("tManageCom", tManageCom);
  		           SSRS tSSRS = tExeSQL.execSQL(sqlbv38);
  		           for (int i=1;i<=tSSRS.MaxRow;i++)
  		           {
  		        	  mPolNo = mBussNo;
  		              mSegment2 ="40400";//中介成本中心
  		              mSaleChnl = "05";  //专业渠道
  		              if(tAgentCom.length()>=9 && tAgentCom.substring(8,9).equals("8"))
  		              {
  		            	  mSegment2="50100"; 
  		            	  mSaleChnl="07";  //应财务要求，修改为联办的成本中心和渠道
  		              }
  		            	  
  		              mAttribute6=tAgentCom;//存放代理机构
  		              
  		              String tChargeTyep =tSSRS.GetText(i, 1); //手续费类型
  		              String tPayYear = tSSRS.GetText(i, 2); //首续期类型
  		              mRiskCode = tSSRS.GetText(i,3); //险种代码
  		              tmoney = String.valueOf(tSSRS.GetText(i,4));
  		              qSumMoney = qSumMoney + Double.parseDouble(tmoney);
  		          	  
  		              if (Double.valueOf(tmoney).doubleValue() ==0)
  		              {
  		            	  dealError("lachargezj", mManageCom+tSSRS.GetText(i, 1), tSSRS.GetText(i, 1), "004", "022", "手续费金额为0");
  		            	  continue;
  		              }
  		              

  		              if("A2".equals(tChargeTyep))
  		              {
  		            	  mBillID = "10301";
  		            	  mAccountCode = "手续费支出-代理手续费-续期服务津贴";        //中介续期服务津贴
  		              }
  		              else if("A1".equals(tChargeTyep) && "0".equals(tPayYear))
  		              {
  		            	  mBillID = "10302";
  		            	  mAccountCode = "手续费支出-代理手续费-首期手续费";        //中介首期手续费
  		              }
  		              else
  		              {
  		            	  mBillID = "10303";
  		            	  mAccountCode = "手续费支出-代理手续费-续期手续费";        //中介续期手续费
  		              }
  		
  		              mEnteredDR = String.valueOf(tmoney);
  		              mEnteredCR = "";
  		              mHeadDescription = mAccountCode + "险种" + mRiskCode;
  			          if (isExitInTab(mBussNo,mBussNo,mBillID))
  			               break;
  		              OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
  		              if(tOFInterfaceSchema != null)
  		            	  mOFInterfaceSet.add(tOFInterfaceSchema);
  		              dealLITranInfo();
  		           }
  		           
  		           //汇总所有非意外险、短期健康险的手续费借方金额
  		           if (qSumMoney>0)
  		           {
  		           tmoney = new DecimalFormat("0.00").format(new Double(qSumMoney));
                     mBillID = "10304";
                     mAccountCode = "应付手续费";                               //应付手续费
                     mEnteredDR = "";
                     mEnteredCR = String.valueOf(tmoney);
                     mHeadDescription = mAccountCode;  
                     mPolNo = mBussNo;
                     if (!isExitInTab(mBussNo,mPolNo,mBillID))
                     	 {
                  	   OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                  	   if(tOFInterfaceSchema != null)
                  	   mOFInterfaceSet.add(tOFInterfaceSchema);
                  	   dealLITranInfo();
                     	 }
  		           }

  		     
                   //意外险、短期健康险，提取的手续费精确到保单级
  		           initVar();
  		           mManageCom = tManageCom;
  		           mAccountingDate = PubFun.getCurrentDate();
  		           mTransDate = mStartDate;
  		           mMatchID ++ ;
  		           mBussNo = String.valueOf(mTime) + tAgentCom + tWageNo + mMatchID;
  		         
  		           double xSumMoney = 0;
  		           
  		           
  		           //提取首续期手续费,与销售保持一致，代理机构取前13位
  		           String ySql = " select c.chargetype,a.payyear,c.riskcode,c.contno,a.p11,max(c.chargerate),sum(c.charge) from lachargezj c, lacommision a "
  				        	   + "where a.CommisionSN = c.CommisionSN and c.chargetype in ('A1','A2') and c.branchtype in ('6', '7') "        	   
  				        	   + "and c.wageno = '?tWageNo?' and substr(c.agentcom,1,13)='?tAgentCom?' and c.managecom like concat('?tManageCom?','%') "
  				        	   + "and c.riskcode in (select riskcode from lmriskapp a where a.RiskType='H' And riskcode In  "
  				        	   + "(select riskcode from lmriskapp where RiskPeriod In ('S','M')) "
  				       	       + "Union (select riskcode From lmriskapp where RiskType='A')) "
  				        	   + "group by c.chargetype,a.payyear,c.riskcode,c.contno,a.p11";
  		           SQLwithBindVariables sqlbv39=new SQLwithBindVariables();
  		           sqlbv39.sql(ySql);
  		           sqlbv39.put("tWageNo", tWageNo);
  		           sqlbv39.put("tAgentCom", tAgentCom);
  		           sqlbv39.put("tManageCom", tManageCom);
  		
  		           logger.debug("查询手续费-------------"+tSql);
  		           SSRS ySSRS = new SSRS();
  		           ySSRS = tExeSQL.execSQL(sqlbv39);
  		           for (int i=1;i<=ySSRS.MaxRow;i++)
  		           {
  		        	  mPolNo = ySSRS.GetText(i,4); //存放保单号
  		              mSegment2 ="40400";//中介成本中心
  		              mSaleChnl = "05";  //专业渠道
  		              if(tAgentCom.length()>=9 && tAgentCom.substring(8,9).equals("8"))
  		              {
  		            	  mSegment2="50100"; 
  		            	  mSaleChnl="07";  //应财务要求，修改为联办的成本中心和渠道
  		              }
  		            	  
  		              mAttribute6=tAgentCom;  //存放代理机构
  		              mAttribute7 = ySSRS.GetText(i,5);  //存放投保人姓名
  		              mAttribute8 = ySSRS.GetText(i,6);  //存放手续费支付比例
  		                
  		              String tChargeTyep =ySSRS.GetText(i, 1); //手续费类型
  		              String tPayYear = ySSRS.GetText(i, 2); //首续期类型
  		              mRiskCode = ySSRS.GetText(i,3); //险种代码
  		              tmoney = String.valueOf(ySSRS.GetText(i,7));
  		              xSumMoney = xSumMoney + Double.parseDouble(tmoney);
  		          	  
  		              if (Double.valueOf(tmoney).doubleValue() ==0)
  		              {
  		            	  dealError("lachargezj", mManageCom+ySSRS.GetText(i, 1), ySSRS.GetText(i, 1), "004", "022", "手续费金额为0");
  		            	  continue;
  		              }

  		              if("A2".equals(tChargeTyep))
  		              {
  		            	  mBillID = "10301";
  		            	  mAccountCode = "手续费支出-代理手续费-续期服务津贴";        //中介续期服务津贴
  		              }
  		              else if("A1".equals(tChargeTyep) && "0".equals(tPayYear))
  		              {
  		            	  mBillID = "10302";
  		            	  mAccountCode = "手续费支出-代理手续费-首期手续费";        //中介首期手续费
  		              }
  		              else
  		              {
  		            	  mBillID = "10303";
  		            	  mAccountCode = "手续费支出-代理手续费-续期手续费";        //中介续期手续费
  		              }
  		
  		              mEnteredDR = String.valueOf(tmoney);
  		              mEnteredCR = "";
  		              mHeadDescription = mAccountCode + "险种" + mRiskCode;
  			          if (isExitInTab(mBussNo,mBussNo,mBillID))
  			               break;
  		              OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
  		              if(tOFInterfaceSchema != null)
  		            	  mOFInterfaceSet.add(tOFInterfaceSchema);
  		              dealLITranInfo();
  		           }
  		           // 汇总所有意外险、短期健康险的手续费借方金额
  		           if (xSumMoney>0)
  		           {
  		           tmoney = new DecimalFormat("0.00").format(new Double(xSumMoney));
                     mBillID = "10304";
                     mAccountCode = "应付手续费";                               //应付手续费
                     mEnteredDR = "";
                     mEnteredCR = String.valueOf(tmoney);
                     mHeadDescription = mAccountCode;          
                     mPolNo = mBussNo;
                     if (!isExitInTab(mBussNo,mPolNo,mBillID))
                     	 {
                  	   OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                  	   if(tOFInterfaceSchema != null)
                  	   mOFInterfaceSet.add(tOFInterfaceSchema);
                  	   dealLITranInfo();
                     	 }
  		           }

	           
	           String jWageNo=mStatYear+PubFun.LCh(String.valueOf(Integer.parseInt(mStatMon)-2), "0", 2);
	           logger.debug("继续率的月份----------"+jWageNo);
	           //处理13个月,25,37个月的继续率奖金提取
	           String xSql = "select round(((select (case when sum(DrawRate) is not null then sum(DrawRate) else 0 end) from LAWAGERADIX where wagecode = 'ZCR000' and BranchType = '7' "
	        	   		   + "and agentgrade='R13' and m.continuedrate13>= FYCMin/100 and m.continuedrate13< FYCMax/100) *m.actcontinuedmoney13),2), "
	        	   		   + "round(((select (case when sum(DrawRate) is not null then sum(DrawRate) else 0 end) from LAWAGERADIX where wagecode = 'ZCR000' and BranchType = '7' "
	        	   		   + "and agentgrade='R25' and m.continuedrate25>= FYCMin/100 and m.continuedrate25< FYCMax/100) *m.actcontinuedmoney25),2), "
	        	   		   + "round(((select (case when sum(DrawRate) is not null then sum(DrawRate) else 0 end) from LAWAGERADIX where wagecode = 'ZCR000' and BranchType = '7' "
	        	   		   + "and agentgrade='R37' and m.continuedrate37>= FYCMin/100 and m.continuedrate37< FYCMax/100) *m.actcontinuedmoney37),2) "
	        	   		   + "from zjcontinuedrate m where yearmonth = '?jWageNo?' "
	        	   		   + "and agentcom='?tAgentCom?' and managecom like concat('?tManageCom?','%') ";
	           SQLwithBindVariables sqlbv40=new SQLwithBindVariables();
	           sqlbv40.sql(xSql);
	           sqlbv40.put("jWageNo", jWageNo);
	           sqlbv40.put("tAgentCom", tAgentCom);
	           sqlbv40.put("tManageCom", tManageCom);
	        	   
			   logger.debug("查询继续率奖金-------------"+xSql);
			   SSRS xSSRS = new SSRS();
			   xSSRS = tExeSQL.execSQL(sqlbv40);
			   for (int i=1;i<=xSSRS.MaxRow;i++)
			   {
			      initVar();
			      mMatchID ++ ;
			      mManageCom = tManageCom; 
			      mSegment2 ="40400";
			      mSaleChnl = "05";  //专业渠道
	              if(tAgentCom.length()>=9 && tAgentCom.substring(8,9).equals("8"))
	              {
	            	  mSegment2="50100"; 
	            	  mSaleChnl="07";  //应财务要求，修改为联办的成本中心和渠道
	              }
			      mAttribute6=tAgentCom;//存放代理机构
			      mAccountingDate = PubFun.getCurrentDate();
			      mTransDate = mStartDate;
			      mBussNo = String.valueOf(mTime) + tAgentCom + jWageNo + mMatchID;
			      mPolNo = mBussNo;
			      for(int n=1;n<=xSSRS.MaxCol;n++)
			      {
				      for ( int j = 1; j <= 2; j++)
				      {
				         if (j == 1)
				         {
				        		 if(n==1)
				        		 {
				        		   mBillID = "10305";
				        		   mAccountCode = "手续费支出-代理手续费-继续率奖金（13）";        //13个月中介继续率奖金	
				        		   tmoney=String.valueOf(xSSRS.GetText(i,1));
				        		 }
				        		 else if(n==2)
				        		 {
				        		   mBillID = "10306";
				        		   mAccountCode = "手续费支出-代理手续费-继续率奖金（25）";        //25个月中介继续率奖金	 
				        		   tmoney=String.valueOf(xSSRS.GetText(i,2));
				        		 }
				        		 else
				        		 {
				        		   mBillID = "10307";
				        		   mAccountCode = "手续费支出-代理手续费-继续率奖金（37）";        //37个月中介继续率奖金	 
				        		   tmoney=String.valueOf(xSSRS.GetText(i,3));
				        		 }
					   		      if (Double.valueOf(tmoney).doubleValue() ==0)
							      {
							    	  dealError("lachargezj", cManageCom+mManageCom, mManageCom, "004", "022", "继续率奖金金额为0");
							    	  break;
							      }
						           mEnteredDR = String.valueOf(tmoney);
						           mEnteredCR = "";
						           mHeadDescription = mAccountCode + "险种" + mRiskCode;
					           if (isExitInTab(mBussNo,mBussNo,mBillID))
						          break;
						         OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
						         if(tOFInterfaceSchema != null)
						          mOFInterfaceSet.add(tOFInterfaceSchema);
						         dealLITranInfo();
				         }
				         else
				         {
				             mBillID = "10308";
				             mAccountCode = "应付手续费";                               //应付手续费
				             mEnteredDR = "";
				             mEnteredCR = String.valueOf(tmoney);
				             mHeadDescription = mAccountCode;		         
					         if (isExitInTab(mBussNo,mBussNo,mBillID))
					          break;
					         OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					         if(tOFInterfaceSchema != null)
					          mOFInterfaceSet.add(tOFInterfaceSchema);
					         dealLITranInfo();
				         }
				      }
			   }
			   }
			   //手续费支出-代理手续费-月度奖金、 手续费支出-代理手续费-年终奖金由于销售暂时没有，所以财务暂时不予提取
//	        }
	    }
     return true;
    
    }
    
    //银代手续费
    private boolean PayBanksFee()
    {

    	
        String tCalDate[] = new String[2];
        String mStartDate = mStatYear+"-"+mStatMon+"-01";
        tCalDate = PubFun.calFLDate(mStartDate);
         
        String tWageNo = mStatYear+mStatMon;
        logger.debug("tWageNo:"+tWageNo);
        String tMngSql = "select distinct(substr(ManageCom,1,6)) from lachargezj where wageno='?tWageNo?' "
        			   + "and ManageCom like concat('?cManageCom?','%') and branchtype='3' "
        			   + "union select distinct(substr(ManageCom,1,6)) from zjcontinuedrate where yearmonth = '?tWageNo?' "
	        	   	   + "and  managecom like concat('?cManageCom?','%') ";
        SQLwithBindVariables sqlbv41=new SQLwithBindVariables();
        sqlbv41.sql(tMngSql);
        sqlbv41.put("tWageNo", tWageNo);
        sqlbv41.put("cManageCom", cManageCom);

        ExeSQL ttExeSQL = new ExeSQL();
        logger.debug("查询6位管理机构--------"+tMngSql);
        SSRS tSSRS1 = ttExeSQL.execSQL(sqlbv41);
        for(int m=1;m<=tSSRS1.MaxRow;m++)
	    {
        	String tManageCom = tSSRS1.GetText(m, 1);
	           //提取首续期手续费,与销售保持一致，代理机构取前13位
	           String tSql = " select a.payyear,c.riskcode,substr(c.agentcom,1,4), sum(c.charge) from lachargezj c, lacommision a "
			        	   + "where a.CommisionSN = c.CommisionSN and  c.branchtype ='3' "        	   
			        	   + "and c.wageno = '?tWageNo?'  and c.managecom like concat('?tManageCom?','%') "
			        	   + "group by  a.payyear,c.riskcode,substr(c.agentcom,1,4)";
	           SQLwithBindVariables sqlbv42=new SQLwithBindVariables();
	           sqlbv42.sql(tSql);
	           sqlbv42.put("tWageNo", tWageNo);
	           sqlbv42.put("tManageCom", tManageCom);
	
	           logger.debug("查询手续费-------------"+tSql);
	           ExeSQL tExeSQL = new ExeSQL();
	           SSRS tSSRS = tExeSQL.execSQL(sqlbv42);
	           for (int i=1;i<=tSSRS.MaxRow;i++)
	           {
	              initVar();
	              mMatchID ++ ;
	              mManageCom = tManageCom;
	              mSegment2 ="40200";//银代成本中心
	              mSaleChnl = "03";  //专业渠道
	            	  
	              mAttribute6=tSSRS.GetText(i, 3);;//存放代理机构
	              mAccountingDate = PubFun.getCurrentDate();
	              mTransDate = mStartDate;
	              String tPayYear = tSSRS.GetText(i, 1); //首续期类型
	              mRiskCode = tSSRS.GetText(i,2); //险种代码
	              tmoney = String.valueOf(tSSRS.GetText(i,4));
	              if (Double.valueOf(tmoney).doubleValue() ==0)
	              {
	            	  dealError("lachargezj", mManageCom+tSSRS.GetText(i, 1), tSSRS.GetText(i, 1), "004", "022", "手续费金额为0");
	            	  continue;
	              }

	              mBussNo = String.valueOf(mTime) + tManageCom + tWageNo + mMatchID;
	              mPolNo = mBussNo;
	              for ( int j = 1; j <= 2; j++)
	              {
	                 if (j == 1)
	                 {

	                	 if( "0".equals(tPayYear))
	                	 {
	                		 mBillID = "14302";
	                		 mAccountCode = "手续费支出-代理手续费-首期手续费";        //中介首期手续费
	                	 }
	                	 else
	                	 {
	                		 mBillID = "14303";
	                		 mAccountCode = "手续费支出-代理手续费-续期手续费";        //中介续期手续费
	                	 }
	
	                   mEnteredDR = String.valueOf(tmoney);
	                   mEnteredCR = "";
	                   mHeadDescription = mAccountCode + "险种" + mRiskCode;
		               if (isExitInTab(mBussNo,mBussNo,mBillID))
		                  break;
	                   OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                   if(tOFInterfaceSchema != null)
	                	   mOFInterfaceSet.add(tOFInterfaceSchema);
	                   dealLITranInfo();
	                 }
	                 else
	                 {
	                   mBillID = "14304";
	                   mAccountCode = "应付手续费";                               //应付手续费
	                   mEnteredDR = "";
	                   mEnteredCR = String.valueOf(tmoney);
	                   mHeadDescription = mAccountCode;               
		               if (isExitInTab(mBussNo,mBussNo,mBillID))
		                  break;
		               OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
		               if(tOFInterfaceSchema != null)
		                  mOFInterfaceSet.add(tOFInterfaceSchema);
		               dealLITranInfo();
	                 }
	              }
	           }
	    }
     return true;
    
    
    }
    /**
     * 销售系统和核心业务系统渠道转换：
     * @param cBranchType
     * branchtype = 1,4,5,(6) ~~ salechnl = 02  个人
     * branchtype = 2  ~~ salechnl = 01 团险
     * branchtype = 3  ~~ salechnl = 03 中介（银代）
     */
    private String tranAgentmSaleChnl(String cBranchType)
    {
        String rSaleChnl = "";

        if (cBranchType.equals("1"))
            rSaleChnl = "02";
        else if (cBranchType.equals("2"))
            rSaleChnl = "01";
        else if (cBranchType.equals("3"))
            rSaleChnl = "03";
        else
            rSaleChnl = "02";

        return rSaleChnl;
    }
    
    
	/************************
	 * 错误处理函数开始
	 */
	private void dealError(String TabName, String RecNo, String PNo, String ErrNo, String BID, String ErrDesc)
	{
		LITranErrSchema tLITranErrSchema = new LITranErrSchema();
		tLITranErrSchema.setSerialNo(PubFun1.CreateMaxNo("LITranErrNo", 20));
		tLITranErrSchema.setBatchNo(mBatchNo);
		tLITranErrSchema.setBussDate(mToday);
		tLITranErrSchema.setTableName(TabName);
		tLITranErrSchema.setBussNo(RecNo);
		tLITranErrSchema.setErrFlag(ErrNo);
		tLITranErrSchema.setErrDes(ErrDesc);
		tLITranErrSchema.setPolicyNo(PNo);
		tLITranErrSchema.setBillId(BID);
		tLITranErrSchema.setManageCom(cManageCom);
		tLITranErrSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranErrSchema.setMakeTime(PubFun.getCurrentTime());
		tLITranErrSchema.setOperator(mGlobalInput.Operator);
		if((tLITranErrSchema.getBussNo() == null) || (tLITranErrSchema.getPolicyNo() == null))
			return;
		mLITranErrSet.add(tLITranErrSchema);
	}

	/**
	 * 进行科目校验表的处理
	 * **/
	private void dealLITranInfo()
	{
		LITranInfoSchema tLITranInfoSchema = new LITranInfoSchema();
		tLITranInfoSchema.setBatchNo(mBatchNo);
		tLITranInfoSchema.setBillId(mBillID);
		tLITranInfoSchema.setBussNo(mBussNo);
		tLITranInfoSchema.setPolNo(mPolNo);
		tLITranInfoSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranInfoSchema.setMakeTime(PubFun.getCurrentTime());
		tLITranInfoSchema.setTransDate(this.mToday);
		tLITranInfoSchema.setVoucherType(this.mTime);
		tLITranInfoSchema.setManageCom(mManageCom);
		tLITranInfoSchema.setMatchID(mMatchID);
		if(!isExistInSet(tLITranInfoSchema))
			mLITranInfoSet.add(tLITranInfoSchema);
	}

	/**
	 *进行财务接口表的赋值 
	 **/
	private OFInterfaceSchema entry()
	{
		String tMakeDate = PubFun.getCurrentDate();
		String tMakeTime = PubFun.getCurrentTime();
		mLineDescription = mManageCom + "||" + mBussNo + "||" + mInsuredName + "||" + mAttribute5 + "||" + mBillID; // 公司段说明||收付单据号||保户人姓名||类别明细||BillID
		String[] tVerSubject = { mAccountSubCode, mManageCom, mRiskCode, mSaleChnl };

		try
		{
			tVerSubject = vertifySubject(mAccountCode); // 校验科目代码
			String tRecordID = PubFun1.CreateMaxNo("OTOF", "RECORD");

			OFInterfaceSchema rOFInterfaceSchema = new OFInterfaceSchema();
			rOFInterfaceSchema.setRecordID(tRecordID); // 记录行ID
			rOFInterfaceSchema.setReversedStatus(mReversedStatus); // 冲消状态
			rOFInterfaceSchema.setOrigRowID(mOrigRowID); // 被冲消的行
			rOFInterfaceSchema.setReversedRowID(mReversedRowID); // 冲消生成的行
			rOFInterfaceSchema.setCurrencyCode(mCurrencyCode); // 币别
			rOFInterfaceSchema.setVoucherType(mVoucherType); // 凭证类别
			rOFInterfaceSchema.setManageCom(tVerSubject[1]); // 核算单位代码
			rOFInterfaceSchema.setSegment2(mSegment2); // 成本中心
			rOFInterfaceSchema.setAccountCode(mAccountCode); // 科目代码
			rOFInterfaceSchema.setAccountSubCode(tVerSubject[0]); // 科目明细代码
			rOFInterfaceSchema.setSaleChnl(tVerSubject[3]); // 销售渠道
			rOFInterfaceSchema.setRiskCode(tVerSubject[2]); // 保险产品代码
			rOFInterfaceSchema.setSegment7("NA"); // 备用段1
			rOFInterfaceSchema.setSegment8("NA"); // 备用段2
			rOFInterfaceSchema.setTransDate(mTransDate); // 事务日期
			rOFInterfaceSchema.setAccountingDate(mAccountingDate); // 记帐日期
			rOFInterfaceSchema.setMakeDate(tMakeDate); // 创建日期
			rOFInterfaceSchema.setMakeTime(tMakeTime); // 创建时间
			rOFInterfaceSchema.setModifyDate(tMakeDate); // 最后一次修改日期
			rOFInterfaceSchema.setModifyTime(tMakeTime); // 最后一次修改时间
			rOFInterfaceSchema.setMatchID(mMatchID); // 借贷关系key值
			rOFInterfaceSchema.setBatchNo(mBatchNo); // 批次号
			rOFInterfaceSchema.setEnteredDR(mEnteredDR); // 事务借计金额
			rOFInterfaceSchema.setEnteredCR(mEnteredCR); // 事务贷计金额
			rOFInterfaceSchema.setHeadDescription(mHeadDescription); // 日记帐摘要
			rOFInterfaceSchema.setLineDescription(mLineDescription); // 行记帐摘要
			rOFInterfaceSchema.setCurrencyConversionDate(""); // 汇率日期
			rOFInterfaceSchema.setCurrencyConversionRate(""); // 汇率
			rOFInterfaceSchema.setAccountedDR(""); // 记帐借计金额
			rOFInterfaceSchema.setAccountedCR(""); // 记帐贷计金额
			rOFInterfaceSchema.setAttribute1(""); // 空闲属性1
			rOFInterfaceSchema.setPolNo(mPolNo); // 保单号
			rOFInterfaceSchema.setInsuredName(mInsuredName); // 被保险人姓名
			rOFInterfaceSchema.setBussNo(mBussNo); // 收付款单据号
			rOFInterfaceSchema.setAttribute5(mAttribute5); // 行为明细类别
			rOFInterfaceSchema.setAttribute6(mAttribute6); // 代理机构
			rOFInterfaceSchema.setAttribute7(mAttribute7); // 投保人姓名
			rOFInterfaceSchema.setAttribute8(mAttribute8); // 手续费支付比例（直佣比例*折扣）
			rOFInterfaceSchema.setVoucherID("-1"); // 总帐凭证号回写
			rOFInterfaceSchema.setVoucherFlag("NA"); // 回写标志
			rOFInterfaceSchema.setVoucherDate(""); // 回写导入日期

			return rOFInterfaceSchema;
		}
		catch(Exception ex)
		{
			logger.debug("Exception in entry " + ex.toString());
			dealError("OFInterface", mBussNo, mPolNo, "012", String.valueOf(mMatchID), "接口表赋值错误");
			CError.buildErr(this, ex.toString());
			return null;
		}
	}

	/**
	 * 校验科目约束性
	 */
	private String[] vertifySubject(String cSubjectName)
	{
		String[] rSubject = { mAccountSubCode, mManageCom, mRiskCode, mSaleChnl };
		LIVertifyDB tLIVertifyDB = new LIVertifyDB();
		tLIVertifyDB.setSubjectName(cSubjectName);
		if(tLIVertifyDB.query().size()>0)
		{
			LIVertifySchema tLIVertifySchema = tLIVertifyDB.query().get(1); // 主键查询必定最多只有一条记录
			if(tLIVertifySchema.getSubjectName().equals(cSubjectName))
			{
				if(tLIVertifySchema.getSegAccountSub().equals("N"))
					rSubject[0] = "NA";
				if(tLIVertifySchema.getSegComCode().equals("N"))
					rSubject[1] = "NA";
				if(tLIVertifySchema.getSegRiskCode().equals("N"))
					rSubject[2] = "NA";
				if(tLIVertifySchema.getSegSaleChnl().equals("N"))
					rSubject[3] = "NA";
			}
		}

		return rSubject;
	}


	private HashSet mLITranInfoSetHashtable=new HashSet();
	// 预留出对日志表的操作
	private boolean isExistInSet(LITranInfoSchema tLITranInfoSchema)
	{
		if (!mLITranInfoSetHashtable
				.contains((tLITranInfoSchema.getBillId().trim() + ":"
						+ tLITranInfoSchema.getBussNo().trim() + ":" + tLITranInfoSchema
						.getPolNo().trim()))) {
			mLITranInfoSetHashtable
					.add((tLITranInfoSchema.getBillId().trim() + ":"
							+ tLITranInfoSchema.getBussNo().trim() + ":" + tLITranInfoSchema
							.getPolNo().trim()));
			return false;
		}
		return true;
//		for(int i = 1; i <= mLITranInfoSet.size(); i++)
//		{
//			LITranInfoSchema ttLITranInfoSchema = new LITranInfoSchema();
//			ttLITranInfoSchema.setSchema(mLITranInfoSet.get(i));
//			if((ttLITranInfoSchema.getBillId().trim().equals(tLITranInfoSchema.getBillId().trim()))
//					&& (ttLITranInfoSchema.getBussNo().trim().equals(tLITranInfoSchema.getBussNo().trim()))
//					&& (ttLITranInfoSchema.getPolNo().trim().equals(tLITranInfoSchema.getPolNo().trim())))
//			{
//				return true;
//			}
//		}
//		return false;
	}
	
	
	
	/**
	 * 进行科目重复的校验
	 * 
	 * **/
	private boolean isExitInTab(String tBussNo, String tPolNo, String tBillId)
	{
		if(tBillId == null || tBillId.equals(""))
		{
			dealError("LITranInfo", tBussNo, tPolNo, "010", "001", "科目代码为空");
			return true;
		}
		LITranInfoDB tLITranInfoDB = new LITranInfoDB();
		tLITranInfoDB.setBussNo(tBussNo);
		tLITranInfoDB.setBillId(tBillId);
		tLITranInfoDB.setPolNo(tPolNo);
		if(tLITranInfoDB.getCount() > 0)
		{
			dealError("LITranInfo", tBussNo, tPolNo, "011", "001", "该保单已经提取过相应的凭证");
			return true;
		}
		return false;
	}

	// 预留出对接口流转表的操作
	private void prepareOutputData()
	{
		MMap mp = new MMap();
		mp.put(mLITranInfoSet, "INSERT");
		mp.put(mLITranErrSet, "INSERT");
		mp.put(mOFInterfaceSet, "INSERT");
		mInputData.clear();
		mInputData.add(mp);
	}


	/**
	 * 进行初始化
	 * **/
	private void initVar()
	{
		mReversedStatus = "0"; // 冲消状态
		mOrigRowID = ""; // 被冲消的行
		mReversedRowID = ""; // 冲消生成的行
		mCurrencyCode = "CNY"; // 币别
		mVoucherType = String.valueOf(mTime); // 凭证类别
		mManageCom = "NA"; // 核算单位代码
		mSegment2 = "NA"; // 成本中心
		mAccountCode = "NA"; // 科目代码
		mAccountSubCode = "NA"; // 科目明细代码
		mSaleChnl = "NA"; // 销售渠道
		mRiskCode = "NA"; // 保险产品代码
		mTransDate = ""; // 事务日期
		mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate; // 记帐日期
		mEnteredDR = ""; // 事务借计金额
		mEnteredCR = ""; // 事务贷计金额
		mHeadDescription = "NA"; // 日记帐摘要
		mLineDescription = "NA"; // 行记帐摘要
		mPolNo = ""; // 保单号
		mInsuredName = ""; // 被保险人姓名
		mBussNo = ""; // 收付款单据号
		mAttribute5 =  (mTime==82 || mTime==83) ? "核心业务系统" : "销售管理系统";  // 行为明细类别－来源
		mAttribute6 = "NA";
		mAttribute7 = "NA";
		mAttribute8 = "NA";
	}
	
	
	public static void main(String[] args)
	{
		OtoFUI tOtoFUI = new OtoFUI();
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		String bdate = "2008-06-20";
		String edate = "2008-06-20";
		String tDateFlag = "0";
		vData.addElement(tG);
		vData.addElement(bdate);
		vData.addElement(edate);
		Integer itemp = new Integer(1);
		vData.addElement(itemp);
		vData.addElement(tDateFlag);
		vData.addElement(""); // 新增记帐日期
		vData.addElement("86"); // 新增管理机构
		tOtoFUI.submitData(vData, "PRINT");
	}
}
