package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.lang.*;
import java.util.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.cbcheck.*;

/**
 * <p>Title: Web业务系统承保个人单交费状态查询部分</p>
 * <p>Description: 逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author SXY
 * @version 1.0
 */
public class PolFeeStatusChkBL
{
private static Logger logger = Logger.getLogger(PolFeeStatusChkBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData ;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mpassflag; //通过标记
	private int merrcount; //错误条数
	private String mCalCode; //计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private float mValue;
    //private String muwgrade = "";

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mmLCPolSet = new LCPolSet();
	private LCPolSet m2LCPolSet = new LCPolSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";
	private String mPrtNo="";

	/**计算公式表**/
	//private LMUWSchema mLMUWSchema = new LMUWSchema();
	private LMUWSet mLMUWSet = new LMUWSet();
    private LMUWSet m2LMUWSet = new LMUWSet();
    private LMUWSet mmLMUWSet = new LMUWSet();

    private LMCalModeSet mmLMCalModeSet = new LMCalModeSet();
    private LMCalModeSet mLMCalModeSet = new LMCalModeSet();

	public PolFeeStatusChkBL() {}

	/**
	* 传输数据的公共方法
	* @param: cInputData 输入的数据
	*         cOperate 数据操作
	* @return:
	*/
	public boolean submitData(VData cInputData,String cOperate)
	{
		int flag = 0; //判断是不是所有数据都不成功
		int j = 0; //符合条件数据个数

		//将操作数据拷贝到本类中
		mInputData = (VData)cInputData.clone();
		logger.debug("---1---");

	   	//得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		  return false;
		logger.debug("---PolFeeStatusChkBL getInputData---");
        LCPolSchema tLCPolSchema = new LCPolSchema();
        tLCPolSchema = ( LCPolSchema )mLCPolSet.get( 1 );
        mLCPolSchema = ( LCPolSchema )mLCPolSet.get( 1 );
        String tProposalNo = tLCPolSchema.getProposalNo();
        mOldPolNo = tLCPolSchema.getProposalNo();
        mPolNo = tLCPolSchema.getPolNo();
		mPrtNo=tLCPolSchema.getPrtNo();
        logger.debug("Polno:"+mPolNo);
        // 数据操作业务处理
        if (!dealData(tLCPolSchema))
          return false;

        //准备返回的数据
        prepareOutputData();
        return true;
        }

	/**
	* 数据操作类业务处理
	* 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	*/
	private boolean dealData(LCPolSchema tLCPolSchema)
	{
		//准备算法
	  logger.debug("---DealData Begin---");
		if (CheckKinds(tLCPolSchema) == false)
		  return  false;
		//取保单信息
		int n = mmLMCalModeSet.size();
		logger.debug("LMCalModeSet.size:"+n);
		if (n == 0)
		{
		   mLMCalModeSet.clear();
		}
		else
		{
            mLMCalModeSet.clear();
		    for (int i = 1;i<=n;i++)
		    {
			//取计算编码
			LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
			tLMCalModeSchema = mmLMCalModeSet.get(i);
			mCalCode = tLMCalModeSchema.getCalCode();
			logger.debug("CalCode:"+mCalCode);
			if(CheckPol()==0.0)
	        {
			  if(mCalCode.equals("F00002"))
			  {
				tLMCalModeSchema.setRemark(tLMCalModeSchema.getRemark()+"  " + mValue);
			    mLMCalModeSet.add(tLMCalModeSchema);
			  }
			}
            else
			{
			  tLMCalModeSchema.setRemark(tLMCalModeSchema.getRemark()+"  " + mValue);
			  mLMCalModeSet.add(tLMCalModeSchema);
			}
		    }
		}

     logger.debug("---DealData End---");
		return true;
	}


	/**
	* 从输入数据中得到所有对象
	*输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	*/
	private boolean getInputData(VData cInputData)
	{
	        GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		mOperate = tGlobalInput.Operator;

		mLCPolSet.set((LCPolSet)cInputData.getObjectByObjectName("LCPolSet",0));
		int n = mLCPolSet.size();
		int flag = 0; //怕判断是不是所有保单都失败
		int j = 0; //符合条件保单个数

		if(n > 0)
        {
            LCPolDB tLCPolDB = new LCPolDB();
			LCPolSchema tLCPolSchema = mLCPolSet.get(1);
			tLCPolDB.setPolNo( tLCPolSchema.getPolNo());
			String temp = tLCPolSchema.getPolNo();
			logger.debug("temp"+temp);
			if (tLCPolDB.getInfo() == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "PolStatusChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = temp+"投保单查询失败!";
				this.mErrors .addOneError(tError) ;
				return false;
			}
	    }
        else
        {
           CError tError = new CError();
           tError.moduleName = "PolStatusChkBL";
           tError.functionName = "getInputData";
           tError.errorMessage = "数据传输失败!";
           this.mErrors .addOneError(tError) ;
           return false;
        }

      return true;
	}

	/**
	* 校验投保单是否复核
	* 输出：如果发生错误则返回false,否则返回true
	*/
	private boolean checkApprove(LCPolSchema tLCPolSchema)
	{
  		if (tLCPolSchema.getApproveCode() == null || tLCPolSchema.getApproveDate() == null)
  		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + tLCPolSchema.getPolNo().trim() + "）";
			this.mErrors .addOneError(tError) ;
			return false;
		}
		return true;
	}



    /**
	* 核保险种信息校验,准备核保算法
	* 输出：如果发生错误则返回false,否则返回true
	*/
	private boolean CheckKinds(LCPolSchema tLCPolSchema)
	{
	  logger.debug("---CheckKinds Begin---");
        String tsql = "";
	    mmLMCalModeSet.clear();
	    LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
	  //查询算法编码
        tsql = "select * from LMCalMode where Type = 'M' order by calcode";
	    LMCalModeDB tLMCalModeDB = new LMCalModeDB();
		logger.debug("---LMCalModeDB Begin---");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
	    mmLMCalModeSet = tLMCalModeDB.executeQuery(sqlbv);
		logger.debug("---LMCalModeDB End---");

	    if (tLMCalModeDB.mErrors.needDealError() == true)
	    {
	        // @@错误处理
		    this.mErrors.copyAllErrors(tLMCalModeDB.mErrors);
	        CError tError = new CError();
	        tError.moduleName = "UWAutoChkBL";
	        tError.functionName = "CheckKinds";
	        tError.errorMessage = tLCPolSchema.getRiskCode().trim()+"险种信息查询失败!";
	        this.mErrors.addOneError(tError);
	        mLMUWSet.clear();
	        return false;
	    }
		logger.debug("---CheckKinds End---");
      	    return true;
	}


	/**
	* 个人单核保
	* 输出：如果发生错误则返回false,否则返回true
	*/
	private float CheckPol()
	{
	    // 计算
	    Calculator mCalculator = new Calculator();
	    mCalculator.setCalCode( mCalCode );
	    //增加基本要素
		mCalculator.addBasicFactor("PolNo", mPolNo );
        mCalculator.addBasicFactor("PrtNo", mPrtNo );
		String tStr = "";
		tStr = mCalculator.calculate() ;
		if (tStr==null||tStr.trim().equals(""))
			mValue = 0;
		else
			mValue = Float.parseFloat( tStr );
        logger.debug(mValue);
		return mValue;
	}


	/**
	* 准备需要保存的数据
	*/
	private void prepareOutputData()
	{
		mResult.clear();
        mResult.add(mLMCalModeSet);
	}

    public VData getResult()
    {
        return mResult;
    }

}
