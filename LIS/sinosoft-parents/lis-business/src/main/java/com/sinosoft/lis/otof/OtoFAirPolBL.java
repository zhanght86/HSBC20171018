package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.text.*;
import java.util.*;
/**
 * <p>Title: Lis_New</p>
 * <p>Description: 航意险财务接口</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Sinosoft
 * @version 1.0
 */

public class OtoFAirPolBL
{
private static Logger logger = Logger.getLogger(OtoFAirPolBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors=new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 数据操作字符串 */
    private String mOperate;

    private String mStartDate = "";
    private String mEndDate = "";
    private String mToday = "";
    private int mTime=0;
    private String mInputDate = "";            //界面传入的记帐日期
	private String DateFlag = "0"; //自动运行

    private String tmoney = "";             //>金额</money>
    private LIVertifySet mLIVertifySet = new LIVertifySet();         //科目约束性

    private String mBillID = "";               //对应凭证科目，便于查找 == 凭证类别+凭证分类(一张凭证内部有多个分类的情况)+内部编码
    private String mReversedStatus = "";       //冲消状态
    private String mOrigRowID = "";            //被冲消的行
    private String mReversedRowID = "";        //冲消生成的行
    private String mCurrencyCode = "";         //币别
    private String mVoucherType = "";          //凭证类别
    private String mManageCom = "";            //核算单位代码
    private String mSegment2 = "";             //成本中心
    private String mAccountCode = "";          //科目代码
    private String mAccountSubCode = "";       //科目明细代码
    private String mSaleChnl = "";             //销售渠道
    private String mRiskCode = "";             //保险产品代码
    private String mTransDate = "";            //事务日期
    private String mAccountingDate = "";       //记帐日期
    private int mMatchID = 0;                  //借贷关系key值
    private String mBatchNo = "";              //批次号
    private String mEnteredDR = "";            //事务借计金额
    private String mEnteredCR = "";            //事务贷计金额
    private String mHeadDescription = "";      //日记帐摘要
    private String mLineDescription = "";      //行记帐摘要
    private String mPolNo = "";                //保单号
    private String mInsuredName = "";          //被保险人姓名
    private String mBussNo = "";               //收付款单据号
    private String mAttribute5 = "";           //行为明细类别
    private String mAttribute6 = "";           //代理机构（航意险应收凭证）
    private GlobalInput mGlobalInput =new GlobalInput() ;
	private Date dbdate;
	private Date dedate;
	private String  rBatchNo ;

//对表的定义
    OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
    LITranInfoSet mLITranInfoSet = new LITranInfoSet();
    LITranErrSet mLITranErrSet = new LITranErrSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();
    private String tRecordNo = "";          //加锁的号码
    private String tManagecom="";//提取管理机构


    public OtoFAirPolBL()
    {
    }
    /**
 传输数据的公共方法
 */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("--- OtoFAirPolBL begin ---");
        mInputData = (VData)cInputData.clone() ;
        this.mOperate = cOperate;

        try
        {
            if( !cOperate.equals("AirPol") )
            {
            	CError.buildErr(this, "不支持的操作字符串");
                return false;
            }

            initVar();                                          //避免getinputdata中的数据被冲掉
            // 得到外部传入的数据，将数据备份到本类中
            if( !getInputData(cInputData) )
            {
                return false;
            }

            
            if(!checkData())
            {
            	CError.buildErr(this, "校验数据失败！");
            	return false;
            }


            if(!dealData())
            {
            	return false;
            }            

        }

        catch (Exception ex)
        {
            ex.printStackTrace();
            CError.buildErr(this, "发生异常");
            return false;
        }
        finally
        {
        	mLock.unLock();
        }
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		mStartDate = (String)mTransferData.getValueByName("bdate");
		mEndDate = (String)mTransferData.getValueByName("edate");
		Integer itemp = (Integer)mTransferData.getValueByName("itemp");
		mTime = itemp.intValue();
		DateFlag = (String)mTransferData.getValueByName("DateFlag");
		mInputDate = (String)mTransferData.getValueByName("accountdate"); // 记账日期  
        tManagecom = (String)mTransferData.getValueByName("managecom");                                    //管理机构

        if (mStartDate.equals(""))
        {
        	CError.buildErr(this, "没有起始日期!");
            return false;
        }

        if (mEndDate.equals(""))
        {
        	CError.buildErr(this, "没有终止日期!");
            return false;
        }

        if (mStartDate.length()!=10 || mEndDate.length()!=10 )
        {
        	CError.buildErr(this, "请检查本地机器的时间格式设置，请采用YYYY-MM-DD格式!");
            return false;
        }

        if( mGlobalInput==null )
        {
        	CError.buildErr(this, "没有得到足够的信息!");
            return false;
        }
        return true;
    }

	private boolean checkData()
	{
        FDate chgdate = new FDate();
        dbdate = chgdate.getDate(mStartDate);
        dedate = chgdate.getDate(mEndDate);
		mLITranErrSet.clear();
		mLITranInfoSet.clear();

		return true;
	}

	
	private boolean dealData()
	{
        while(dbdate.compareTo(dedate) <= 0) 
        {  
	          // 准备所有要打印的数据
	          if( !getFeeDataAirPol() )
	          {
	        	  CError.buildErr(this, "正常保单生成应收保费错误");
	              return false;
	          }
	
	
	          if(!getAbateAirPol())
	          {
	        	  CError.buildErr(this, "退保保单进行红冲错误");
	              return false;
	          }
	
	          if(!getLoanAirPol())
	          {
	        	  CError.buildErr(this, "退保保单生成应收保费错误");
	              return false;
	          }
	          dbdate = PubFun.calDate(dbdate,1,"D",null);
        } 
		return true;
	}
    //对有效保单
    private boolean getFeeDataAirPol()
    {
    	try
    	{
            FDate chgdate = new FDate();
		    mToday = chgdate.getString(dbdate);
		    tRecordNo=new SimpleDateFormat("yyyyMMdd").format(dbdate)+String.valueOf(mTime)+"01";//正常保单加锁,对所有机构进行加锁
	        logger.debug("开始加锁---------");
	        if(!mLock.lock(tRecordNo, "LF1001", mGlobalInput.Operator))
	        {
	        	CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
	            return false;
	        }
	        mOFInterfaceSet.clear();
	        mLITranErrSet.clear();
	        mLITranInfoSet.clear();
	        String tSql = "select * from LCAirPol "
	                    + "where state in ('A','B','C') and ManageCom like concat('?tManagecom?','%') and polstate<>'2' "
	                    + " and makedate='?mToday?'"
	                    + "union "
	                    + "select * from LBAirPol "
	                    + "where state in ('A','B','C') and ManageCom like concat('?tManagecom?','%') and polstate<>'2' "
	                    + " and makedate='?mToday?'";
	        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	        sqlbv.sql(tSql);
	        sqlbv.put("tManagecom", tManagecom);
	        sqlbv.put("mToday",mToday);
	        logger.debug(tSql);
	        LCAirPolDB tLCAirPolDB = new LCAirPolDB();
	        LCAirPolSet tLCAirPolSet = tLCAirPolDB.executeQuery(sqlbv);
	        if(tLCAirPolSet.size()>0)
	        {
	          mBatchNo = PubFun1.CreateMaxNo("OTOF","20");
	          for (int i = 1 ; i <= tLCAirPolSet.size() ; i++)
	          {
	              LCAirPolSchema tLCAirPolSchema = new LCAirPolSchema();
	              tLCAirPolSchema = tLCAirPolSet.get(i);
	              if (tLCAirPolSchema.getPrem() <= 0)
	                  continue;
	
	              initVar();
	              mMatchID ++ ;
	              mVoucherType = "3";                           //该借贷关系记在保费收入凭证中
	              mPolNo = tLCAirPolSchema.getPolNo().trim();
	              mBussNo = tLCAirPolSchema.getPolNo().trim();
	              mAccountingDate =(mInputDate.equals("")) ? tLCAirPolSchema.getMakeDate() : mInputDate;
	              mTransDate = tLCAirPolSchema.getCValiDate();
	              mRiskCode = tLCAirPolSchema.getRiskCode();
	              mSaleChnl = "01";                             //航意险默认为团险销售渠道
	              mInsuredName = tLCAirPolSchema.getInsuredName();
	              mManageCom = tLCAirPolSchema.getManageCom().substring(0,6);
	              mSegment2 = tLCAirPolSchema.getManageCom();
	              mAttribute6 = tLCAirPolSchema.getAgentCom();
	              tmoney = new DecimalFormat("0.00").format(new Double(tLCAirPolSchema.getPrem()) );
	
	              for ( int j = 1; j <=2; j++)
	              {
	                  if(j == 1)
	                  {
	                  mBillID = "1300";
	                  mAccountCode = "应收保费-首期-中介代理";
	                  mEnteredDR = String.valueOf(tmoney);
	                  mEnteredCR = "";
	                  mHeadDescription = "应收保费";
	              }
	              else
	              {
	                  mEnteredDR = "";
	                  mEnteredCR = String.valueOf(tmoney);
	                  mBillID = "1301";
	                  mAccountCode = "保费收入-趸缴";                               //航意险保费记录
	                  mHeadDescription = "保费收入";
	              }
	
	              if (tLCAirPolSchema.getInsuredName() != null)
	                  mHeadDescription = mAccountCode + tLCAirPolSchema.getInsuredName() ;
	              else
	                  mHeadDescription = mAccountCode ;
	
	              if (isExitInTab(mPolNo,mPolNo,mBillID))
	                  break;
	
	              OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	              if(tOFInterfaceSchema != null)
	                  mOFInterfaceSet.add(tOFInterfaceSchema);	
	              dealLITranInfo();
	              }
	          }
	          if(!prepareOutputData())
	          {
	        	  CError.buildErr(this, "提交数据库失败");
	              return false;
	          }
	        }
    	}
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        finally
        {
        	mLock.unLock();
        }
        logger.debug("完成有效保单应收保费的生成-------- ");
        return true;
    }

    //无效保单（在财务接口表中有应收记录的保单）进行红冲
    private boolean getAbateAirPol()
    {
    	try
    	{
    		//zy 2009-07-09 如果是应收保费的冲消则判断规则为当天的所有航意险冲消凭证生成一个批次号
    		rBatchNo = PubFun1.CreateMaxNo("OTOF","20");
            FDate chgdate = new FDate();
		    mToday = chgdate.getString(dbdate);
		    tRecordNo=new SimpleDateFormat("yyyyMMdd").format(dbdate)+String.valueOf(mTime)+"02";//无效保单加锁,对所有机构进行加锁
	        logger.debug("开始加锁---------");
	        if(!mLock.lock(tRecordNo, "LF1001", mGlobalInput.Operator))
	        {
	        	CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
	            return false;
	        }
	        String dSql = "select * from LCAirPol "
	                    + "where state in ('A','B','C') and ManageCom like concat('?tManagecom?','%') and polstate='2' "
	                    + " and modifydate='?mToday?'";
	        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(dSql);
	        sqlbv1.put("tManagecom",tManagecom);
	        sqlbv1.put("mToday",mToday);
	        logger.debug("查询退保保单－－－－－－－－－－－－"+dSql);
	        LCAirPolDB dLCAirPolDB = new LCAirPolDB();
	        LCAirPolSet dLCAirPolSet = dLCAirPolDB.executeQuery(sqlbv1);
	        OFInterfaceSet tOFInterfaceSet = new OFInterfaceSet();
	        VData tVData = new VData();
	        boolean flag= false ;
	        for(int i=1;i<=dLCAirPolSet.size();i++)
	        {
	            LCAirPolSchema dLCAirPolSchema = new LCAirPolSchema();
	            dLCAirPolSchema = dLCAirPolSet.get(i);
	            OFInterfaceSet dOFInterfaceSet = new OFInterfaceSet();
	            String ofsql= "select * from ofinterface "
	                        + "where  polno='?polno?' and vouchertype='3' "
	                        + "and managecom like concat('?managecom?','%') "
	                        + "and (ReversedStatus is null or ReversedStatus='0') and entereddr = 0 ";
	                      //应财务人员强烈要求，必须调整应收凭证冲消规则：进行凭证冲消时取消财务凭证过账的规则
//	                        + "and voucherflag<>'NA' and voucherid<>'-1' and voucherdate is not null";
	            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	            sqlbv2.sql(ofsql);
	            sqlbv2.put("polno", dLCAirPolSchema.getPolNo().trim());
	            sqlbv2.put("managecom", dLCAirPolSchema.getManageCom().substring(0,6));
	            OFInterfaceDB dOFInterfaceDB = new OFInterfaceDB();
	            dOFInterfaceSet = dOFInterfaceDB.executeQuery(sqlbv2);
	            logger.debug("查询财务接口表中应收保费----------"+ofsql);
	
	            if(dOFInterfaceSet.size()<=0)
	            {
	                continue;
	            }
	            flag = true;
	            tOFInterfaceSet.add(dOFInterfaceSet);
	            mAccountingDate =(mInputDate.equals("")) ? dLCAirPolSchema.getModifyDate() : mInputDate;
	        }
	        if(flag)
	        {
	            TransferData tTransferData=new TransferData();
	            tTransferData.setNameAndValue("Reason","航意险凭证冲销");
//	            tTransferData.setNameAndValue("AccountingDate",mEndDate);
	            //zy 按照信息部财务记账规则进行调整，记账日期为保单作废日期，即lcairpol的modifydate
	            tTransferData.setNameAndValue("AccountingDate",mToday);
	            tTransferData.setNameAndValue("rBatchNo", rBatchNo);
	            tTransferData.setNameAndValue("tFlag", "AirPol");
	            tVData.add(tOFInterfaceSet);
	            tVData.add(mGlobalInput);
	            tVData.add(tTransferData);
	            OtoFReverseUI tOtoFReverseUI = new OtoFReverseUI();
	            if(!tOtoFReverseUI.submitData(tVData,"Reverse"))
	            {
	                // @@错误处理
	                this.mErrors.copyAllErrors(tOtoFReverseUI.mErrors);
	                return false;
	            }
	
	        }
    	}
    	catch(Exception ex)
    	{
            ex.printStackTrace();
            return false;	
    	}
    	finally
    	{
    		mLock.unLock();
    	}

        logger.debug("完成无效保单应收保费的红冲 ");
        return true;

    }


    //在财务接口表中没有应收保费记录的退保保单
    private boolean getLoanAirPol()
    {
    	try
    	{
            FDate chgdate = new FDate();
		    mToday = chgdate.getString(dbdate);
		    tRecordNo=new SimpleDateFormat("yyyyMMdd").format(dbdate)+String.valueOf(mTime)+"03";//即时保单加锁,对所有机构进行加锁
	        logger.debug("开始加锁---------");
	        if(!mLock.lock(tRecordNo, "LF1001", mGlobalInput.Operator))
	        {
	        	CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
	            return false;
	        }
	        mOFInterfaceSet.clear();
	        mLITranErrSet.clear();
	        mLITranInfoSet.clear();
	        OFInterfaceSet mOFInterfaceSet2 = new OFInterfaceSet();
	        String lSql = "select * from lcairpol a where a.state in ('A','B','C') and a.polstate='2' "
	                    + "and not exists (select 1 from ofinterface b where b.polno=trim(a.polno) and b.vouchertype='3')"
	                    + " and a.managecom like concat('?tManagecom?','%')"
	                    + " and a.modifydate='?mToday?' "
	                    + "order by cvalidate";
	        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	        sqlbv3.sql(lSql);
	        sqlbv3.put("tManagecom", tManagecom);
	        sqlbv3.put("mToday", mToday);
	        logger.debug("查询退保（没有应收保费记录）--------------"+lSql);
	        LCAirPolDB lLCAirPolDB = new LCAirPolDB();
	        LCAirPolSet lLCAirPolSet = lLCAirPolDB.executeQuery(sqlbv3);
	        if(lLCAirPolSet.size()>0)
	        {
	            mBatchNo = PubFun1.CreateMaxNo("OTOF","20");
	            for (int i = 1 ; i <= lLCAirPolSet.size() ; i++)
	            {
	                LCAirPolSchema lLCAirPolSchema = new LCAirPolSchema();
	                lLCAirPolSchema = lLCAirPolSet.get(i);
	                if (lLCAirPolSchema.getPrem() <= 0)
	                    continue;
	
	                initVar();
	                mMatchID ++ ;
	                mVoucherType = "3";                           //该借贷关系记在保费收入凭证中
	                mPolNo = lLCAirPolSchema.getPolNo();
	                mBussNo = lLCAirPolSchema.getPolNo();
	                mAccountingDate =(mInputDate.equals("")) ? lLCAirPolSchema.getModifyDate() : mInputDate;
	                mTransDate = lLCAirPolSchema.getCValiDate();
	                mRiskCode = lLCAirPolSchema.getRiskCode();
	                mSaleChnl = "01";                             //航意险默认为团险销售渠道
	                mInsuredName = lLCAirPolSchema.getInsuredName();
	                mManageCom = lLCAirPolSchema.getManageCom().substring(0,6);
	                mSegment2 = lLCAirPolSchema.getManageCom();
	                mAttribute6 = lLCAirPolSchema.getAgentCom();
	                tmoney = new DecimalFormat("0.00").format(new Double(lLCAirPolSchema.getPrem()) );
	
	                for ( int j = 1; j <=2; j++)
	                {
	                    if(j == 1)
	                    {
	                    mBillID = "1300";
	                    mAccountCode = "应收保费-首期-中介代理";       //新会计准则科目调整
	                    mEnteredDR = String.valueOf(tmoney);
	                    mEnteredCR = "";
	                    mHeadDescription = "应收保费";
	                }
	                else
	                {
	                    mEnteredDR = "";
	                    mEnteredCR = String.valueOf(tmoney);
	                    mBillID = "1301";
	                    mAccountCode = "保费收入-趸缴";                               //航意险保费记录
	                    mHeadDescription = "保费收入";
	                }
	
	                if (lLCAirPolSchema.getInsuredName() != null)
	                    mHeadDescription = mAccountCode + lLCAirPolSchema.getInsuredName() ;
	                else
	                    mHeadDescription = mAccountCode ;
	
	                if (isExitInTab(mPolNo,mPolNo,mBillID))
	                    break;
	
	                OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	                OFInterfaceSchema tOFInterfaceSchema2 = (OFInterfaceSchema) entry();//记录贷方数据，以进行冲消
	
	                if(tOFInterfaceSchema != null)
	                    mOFInterfaceSet.add(tOFInterfaceSchema);
	                if(j==2)
	                {
	                    if(tOFInterfaceSchema2 != null)
	                    mOFInterfaceSet2.add(tOFInterfaceSchema);       //记录贷方数据，以进行冲消
	                }
	
	
	                dealLITranInfo();
	                }
	            }
	            if(!prepareOutputData())
	            {
	        	  CError.buildErr(this, "提交数据库失败");
	              return false;
	            }
	            //进行冲消
	            TransferData tTransferData=new TransferData();
	            VData tVData = new VData();
	            tTransferData.setNameAndValue("Reason","航意险凭证冲销");
//	            tTransferData.setNameAndValue("AccountingDate",mEndDate);
	            //zy 按照信息部财务记账规则进行调整，记账日期为保单作废日期，即lcairpol的modifydate
	            tTransferData.setNameAndValue("AccountingDate",mToday);
	            tTransferData.setNameAndValue("rBatchNo", rBatchNo);
	            tTransferData.setNameAndValue("tFlag", "AirPol");
	            tVData.add(mOFInterfaceSet2);
	            tVData.add(mGlobalInput);
	            tVData.add(tTransferData);
	            OtoFReverseUI tOtoFReverseUI = new OtoFReverseUI();
	            if(!tOtoFReverseUI.submitData(tVData,"Reverse"))
	            {
	                // @@错误处理
	                this.mErrors.copyAllErrors(tOtoFReverseUI.mErrors);
	                return false;
	            }
	        }
    	}
    	catch(Exception ex)
    	{
            ex.printStackTrace();
            return false;	
    	}
    	finally
    	{
    		mLock.unLock();
    	}

        logger.debug("完成退保应收保费的生成-------");
        return true;
    }



    //提交数据库操作
    private boolean prepareOutputData()
    {
        mInputData.clear();
        MMap mp = new MMap();
        mp.put(mLITranErrSet, "INSERT");
        mp.put(mLITranInfoSet, "INSERT");
        mp.put(mOFInterfaceSet, "INSERT");
        mInputData.add(mp);
        PubSubmit ps = new PubSubmit();
        if(!ps.submitData(mInputData))
        {
        	mErrors.copyAllErrors(ps.mErrors);
        	return false;
        }
        return true;
    }

    private void initVar()
    {
        mReversedStatus = "0";         //冲消状态
        mOrigRowID = "";               //被冲消的行
        mReversedRowID = "";           //冲消生成的行
        mCurrencyCode = "CNY";         //币别
        mVoucherType = String.valueOf(mTime);             //凭证类别
        mManageCom = "NA";             //核算单位代码
        mSegment2 = "NA";              //成本中心
        mAccountCode = "NA";           //科目代码
        mAccountSubCode = "NA";        //科目明细代码
        mSaleChnl = "NA";              //销售渠道
        mRiskCode = "NA";              //保险产品代码
        mTransDate = "";               //事务日期
        mAccountingDate = mToday ;      //记帐日期
        mEnteredDR = "";               //事务借计金额
        mEnteredCR = "";               //事务贷计金额
        mHeadDescription = "NA";       //日记帐摘要
        mLineDescription = "NA";       //行记帐摘要
        mPolNo = "";                   //保单号
        mInsuredName = "";             //被保险人姓名
        mBussNo = "";                  //收付款单据号
        mAttribute5 =  "核心业务系统";    //行为明细类别－来源
        mAttribute6 = "NA";
    }

    private boolean isExitInTab(String tBussNo,String tPolNo,String tBillId)
    {
        if (tBillId == null || tBillId.equals("") )
        {
            return true;
        }
        LITranInfoDB tLITranInfoDB = new LITranInfoDB();
        tLITranInfoDB.setBussNo(tBussNo);
        tLITranInfoDB.setBillId(tBillId);
        tLITranInfoDB.setPolNo(tPolNo);
        if (tLITranInfoDB.getCount() > 0)
            return true;
        return false;
    }


    private OFInterfaceSchema entry()
    {
        String tMakeDate = PubFun.getCurrentDate();
        String tMakeTime = PubFun.getCurrentTime();
        mLineDescription = mManageCom + "||" + mBussNo + "||" + mInsuredName + "||" + mAttribute5 + "||" + mBillID; //公司段说明||收付单据号||保户人姓名||类别明细||BillID
        String [] tVerSubject = {mAccountSubCode,mManageCom,mRiskCode,mSaleChnl};

        try{
            tVerSubject = vertifySubject(mAccountCode);                  //校验科目代码
            String tRecordID = PubFun1.CreateMaxNo("OTOF", "RECORD");

            OFInterfaceSchema rOFInterfaceSchema = new OFInterfaceSchema();
            rOFInterfaceSchema.setRecordID(tRecordID);                   //记录行ID
            rOFInterfaceSchema.setReversedStatus(mReversedStatus);       //冲消状态
            rOFInterfaceSchema.setOrigRowID(mOrigRowID);                 //被冲消的行
            rOFInterfaceSchema.setReversedRowID(mReversedRowID);         //冲消生成的行
            rOFInterfaceSchema.setCurrencyCode(mCurrencyCode);           //币别
            rOFInterfaceSchema.setVoucherType(mVoucherType);             //凭证类别
            rOFInterfaceSchema.setManageCom(tVerSubject[1]);             //核算单位代码
            rOFInterfaceSchema.setSegment2(mSegment2);                   //成本中心
            rOFInterfaceSchema.setAccountCode(mAccountCode);             //科目代码
            rOFInterfaceSchema.setAccountSubCode(tVerSubject[0]);        //科目明细代码
            rOFInterfaceSchema.setSaleChnl(tVerSubject[3]);              //销售渠道
            rOFInterfaceSchema.setRiskCode(tVerSubject[2]);              //保险产品代码
            rOFInterfaceSchema.setSegment7("NA");                        //备用段1
            rOFInterfaceSchema.setSegment8("NA");                        //备用段2
            rOFInterfaceSchema.setTransDate(mTransDate);                 //事务日期
            rOFInterfaceSchema.setAccountingDate(mAccountingDate);       //记帐日期
            rOFInterfaceSchema.setMakeDate(tMakeDate);                   //创建日期
            rOFInterfaceSchema.setMakeTime(tMakeTime);                   //创建时间
            rOFInterfaceSchema.setModifyDate(tMakeDate);                 //最后一次修改日期
            rOFInterfaceSchema.setModifyTime(tMakeTime);                 //最后一次修改时间
            rOFInterfaceSchema.setMatchID(mMatchID);                     //借贷关系key值
            rOFInterfaceSchema.setBatchNo(mBatchNo);                     //批次号
            rOFInterfaceSchema.setEnteredDR(mEnteredDR);                 //事务借计金额
            rOFInterfaceSchema.setEnteredCR(mEnteredCR);                 //事务贷计金额
            rOFInterfaceSchema.setHeadDescription(mHeadDescription);     //日记帐摘要
            rOFInterfaceSchema.setLineDescription(mLineDescription);     //行记帐摘要
            rOFInterfaceSchema.setCurrencyConversionDate("");            //汇率日期
            rOFInterfaceSchema.setCurrencyConversionRate("");            //汇率
            rOFInterfaceSchema.setAccountedDR("");                       //记帐借计金额
            rOFInterfaceSchema.setAccountedCR("");                       //记帐贷计金额
            rOFInterfaceSchema.setAttribute1("");                        //空闲属性1
            rOFInterfaceSchema.setPolNo(mPolNo);                         //保单号
            rOFInterfaceSchema.setInsuredName(mInsuredName);             //被保险人姓名
            rOFInterfaceSchema.setBussNo(mBussNo);                       //收付款单据号
            rOFInterfaceSchema.setAttribute5(mAttribute5);               //行为明细类别
            rOFInterfaceSchema.setAttribute6(mAttribute6);               //代理机构
            rOFInterfaceSchema.setAttribute7("NA");                      //空闲属性7
            rOFInterfaceSchema.setAttribute8("NA");                      //空闲属性8
            rOFInterfaceSchema.setVoucherID("-1");                         //总帐凭证号回写
            rOFInterfaceSchema.setVoucherFlag("NA");                       //回写标志
            rOFInterfaceSchema.setVoucherDate("");                       //回写导入日期

            return rOFInterfaceSchema;
            }catch(Exception ex)
            {
                logger.debug("Exception in entry " + ex.toString());
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
		if(tLIVertifyDB.query().size()<=0)
		{
			dealError("livertify", mBussNo, mBillID+mMatchID, "010", mVoucherType, cSubjectName+"对应的科目描述不存在");
			//如果科目描述不存在，则默认为NA
			rSubject[0] = "NA";
			rSubject[2] = "NA";
			rSubject[3] = "NA";
		}
		else
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


	/***********************************************************************************************************************************************************
	 * 错误处理函数开始
	 */
	private void dealError(String TabName, String RecNo, String PNo, String ErrNo, String BID, String ErrDesc)
	{
		LITranErrSchema tLITranErrSchema = new LITranErrSchema();
		tLITranErrSchema.setSerialNo(PubFun1.CreateMaxNo("LITranErrNo",20));
		tLITranErrSchema.setBatchNo(mBatchNo);
		tLITranErrSchema.setBussDate(mToday);
		tLITranErrSchema.setTableName(TabName);
		tLITranErrSchema.setBussNo(RecNo);
		tLITranErrSchema.setErrFlag(ErrNo);
		tLITranErrSchema.setErrDes(ErrDesc);
		tLITranErrSchema.setPolicyNo(PNo);
		tLITranErrSchema.setBillId(BID);
		tLITranErrSchema.setManageCom(tManagecom);
		tLITranErrSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranErrSchema.setMakeTime(PubFun.getCurrentTime());
		tLITranErrSchema.setOperator(mGlobalInput.Operator);
		if((tLITranErrSchema.getBussNo() == null) || (tLITranErrSchema.getPolicyNo() == null))
			return;
		mLITranErrSet.add(tLITranErrSchema);
	}


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
        if (!isExistInSet(tLITranInfoSchema))
            mLITranInfoSet.add(tLITranInfoSchema);
    }



    private HashSet mLITranInfoSetHashtable=new HashSet();
    //预留出对日志表的操作
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
//        for (int i=1;i<=mLITranInfoSet.size();i++)
//        {
//            LITranInfoSchema ttLITranInfoSchema = new LITranInfoSchema();
//            ttLITranInfoSchema.setSchema(mLITranInfoSet.get(i));
//            if ((ttLITranInfoSchema.getBillId().trim().equals(tLITranInfoSchema.getBillId().trim()))&&
//                (ttLITranInfoSchema.getBussNo().trim().equals(tLITranInfoSchema.getBussNo().trim())) &&
//                (ttLITranInfoSchema.getPolNo().trim().equals(tLITranInfoSchema.getPolNo().trim())))
//            {
//                return true;
//            }
//        }
//        return false;
    }





    public static void main(String[] args)
    {
        OtoFAirPolBL tOtoFAirPolBL = new OtoFAirPolBL();
        VData vData = new VData();
        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom="86";
        String bdate="2007-10-21";
        String edate="2007-10-21";
        vData.addElement(tG);
        vData.addElement(bdate);
        vData.addElement(edate);
        Integer itemp = new Integer(13);
        vData.addElement(itemp);
        vData.addElement("86");                     //新增管理机构
        vData.addElement("");                           //新增记帐日期
        tOtoFAirPolBL.submitData(vData, "PRINT");
    }
}
