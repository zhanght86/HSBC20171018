package com.sinosoft.lis.acc;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPerInvestPlanDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPerInvestPlanSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPerInvestPlanSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class NBErrorModifyBL
{
private static Logger logger = Logger.getLogger(NBErrorModifyBL.class);


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mContNo = "";
	private String mPolNo = "";
	private String mRiskCode = "";


	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值

	String mCurrentTime = PubFun.getCurrentTime();

	private MMap map = new MMap();
	
	private LCPerInvestPlanSet mLCPerInvestPlanSet = new LCPerInvestPlanSet();
	//需要删除的数据
	//需要删除的账户轨迹数据
	private LCInsureAccTraceSet mDelLCInsureAccTraceSet = new LCInsureAccTraceSet();
	//需要删除的账户管理费轨迹数据
	private LCInsureAccFeeTraceSet mDelLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
	
	//需要修改的账户数据
	private LCInsureAccTraceSet mUpdateLCInsureAccTraceSet = new LCInsureAccTraceSet();
	private LCInsureAccClassSet mUpdateLCInsureAccClassSet = new LCInsureAccClassSet();
	private LCInsureAccSet mUpdateLCInsureAccSet = new LCInsureAccSet();
	
	//需要修改的账户管理费数据
	private LCInsureAccFeeTraceSet mUpdateLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
	private LCInsureAccClassFeeSet mUpdateLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
	private LCInsureAccFeeSet mUpdateLCInsureAccFeeSet = new LCInsureAccFeeSet();
	
	//需要插入的数据
	private LCInsureAccTraceSet mInsertLCInsureAccTraceSet = new LCInsureAccTraceSet();
	//需要插入的账户管理费轨迹数据
	private LCInsureAccFeeTraceSet mInsertLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
	
	
	
	private Reflections tRef = new Reflections();
	public NBErrorModifyBL()
	{
		
	}

	public boolean submitData(VData cInputData, String operate)
	{
		try {
			
			mInputData = cInputData;
			// 数据操作字符串拷贝到本类中
			this.mOperate = operate;
			if (!getInputData())
			{
				return false;
			}
			//并发控制锁
			if (!this.mPubLock.lock(mContNo, "TL0001")) {
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				return false;
			}
			if (!checkData())
			{
				return false;
			}
			if (!dealData())
			{
				
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.mPubLock.unLock();
		}

		return true;
	}

	/**
	 * prepareData
	 * @return boolean
	 */
	private boolean prepareData()
	{
		mResult.clear();
		mResult.add(map);
		return true;
	}

	private boolean getInputData()
	{
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
	
		// 要从VData　类的对象中取出全局变量
		mLCPerInvestPlanSet.set((LCPerInvestPlanSet) mInputData.getObjectByObjectName("LCPerInvestPlanSet", 0));
		if (mGlobalInput == null || mLCPerInvestPlanSet == null)
		{
			CError.buildErr(this,"传过来的数据不全");
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLCPerInvestPlanSet.get(1).getPolNo());
		if (tLCPolDB.getInfo())
		{
			mLCPolSchema.setSchema(tLCPolDB.getSchema());
		}
		else
		{
			CError.buildErr(this,"数据查询失败");
			return false;
		}
		
		this.mContNo = (String)mInputData.getObjectByObjectName("String", 2);
		this.mPolNo= (String)mInputData.getObjectByObjectName("String", 3);
		this.mRiskCode= (String)mInputData.getObjectByObjectName("String", 4);

		return true;
	}

	private boolean checkData()
	{
		SQLwithBindVariables possqlbv = new SQLwithBindVariables();
		//校验该保单是否已经做过保全/理赔/续期.如果做过,不允许做新契约纠错
		String tSQL_POS = "select count(*) from lpedorapp where otherno='"+"?otherno?"+"' ";
		possqlbv.sql(tSQL_POS);
		possqlbv.put("otherno", this.mLCPolSchema.getContNo());
		
		ExeSQL tExeSQL = new ExeSQL();
		String tFlag = "";
		tFlag = tExeSQL.getOneValue(possqlbv);
		if(!("").equals(tFlag)&&Integer.parseInt(tFlag)>0)
		{
			CError.buildErr(this,"当前保单做过保全申请,不允许做新契约纠错!");
			return false;
		}
		SQLwithBindVariables claimsqlbv = new SQLwithBindVariables();
		String tSQL_Claim = "select count(*) from llclaimpolicy where contno='"+"?contno?"+"' ";
		claimsqlbv.sql(tSQL_Claim);
		claimsqlbv.put("contno", this.mLCPolSchema.getContNo());
		
		tFlag = "";
		tFlag = tExeSQL.getOneValue(claimsqlbv);
		if(!("").equals(tFlag)&&Integer.parseInt(tFlag)>0)
		{
			CError.buildErr(this,"当前保单做过理赔,不允许做新契约纠错!");
			return false;
		}
		SQLwithBindVariables resqlbv = new SQLwithBindVariables();
		String tSQL_Re = "select count(*) from ljapayperson where contno='"+"?contno?"+"' and paycount>1";
		resqlbv.sql(tSQL_Re);
		resqlbv.put("contno", this.mLCPolSchema.getContNo());
		tFlag = "";
		tFlag = tExeSQL.getOneValue(resqlbv);
		if(!("").equals(tFlag)&&Integer.parseInt(tFlag)>0)
		{
			CError.buildErr(this,"当前保单做过续期,不允许做新契约纠错!");
			return false;
		}
		
		return true;
	}

	/**
	 * 处理数据
	 * @return
	 */
	private boolean dealData()
	{
		logger.debug("mPMRetireRuleSet.size()=====" + mLCPerInvestPlanSet.size());
		//处理投资计划表
		//更新相关数据
		for (int i = 1; i <= mLCPerInvestPlanSet.size(); i++)
		{
			LCPerInvestPlanDB tLCPerInvestPlanDB = new LCPerInvestPlanDB();
			tLCPerInvestPlanDB.setPolNo(this.mLCPolSchema.getPolNo());
			tLCPerInvestPlanDB.setPayPlanCode(mLCPerInvestPlanSet.get(i).getPayPlanCode());
			tLCPerInvestPlanDB.setInsuAccNo(mLCPerInvestPlanSet.get(i).getInsuAccNo());
			
			if(!tLCPerInvestPlanDB.getInfo())
			{
				CError.buildErr(this,"获取原投资计划数据失败!");
				return false;
			}
			mLCPerInvestPlanSet.get(i).setOperator(tLCPerInvestPlanDB.getOperator());
			mLCPerInvestPlanSet.get(i).setMakeDate(tLCPerInvestPlanDB.getMakeDate());
			mLCPerInvestPlanSet.get(i).setMakeTime(tLCPerInvestPlanDB.getMakeTime());
			
			mLCPerInvestPlanSet.get(i).setGrpContNo(this.mLCPolSchema.getGrpContNo());
			mLCPerInvestPlanSet.get(i).setGrpPolNo(this.mLCPolSchema.getPolNo());
			mLCPerInvestPlanSet.get(i).setInsuredNo(this.mLCPolSchema.getInsuredNo());
			mLCPerInvestPlanSet.get(i).setRiskCode(this.mLCPolSchema.getRiskCode());
			mLCPerInvestPlanSet.get(i).setContNo(this.mLCPolSchema.getContNo());
			mLCPerInvestPlanSet.get(i).setModifyDate(this.mCurrentDate);
			mLCPerInvestPlanSet.get(i).setModifyTime(this.mLCPolSchema.getModifyTime());
			mLCPerInvestPlanSet.get(i).setModifyOperator(this.mGlobalInput.Operator);
			mLCPerInvestPlanSet.get(i).setCurrency(tLCPerInvestPlanDB.getCurrency());
			
		}
		map.put(mLCPerInvestPlanSet, "DELETE&INSERT");
		//处理账户表
		//获取账户轨迹表
		LCInsureAccTraceSet oldLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        tLCInsureAccTraceDB.setAccAlterNo(this.mLCPolSchema.getPrtNo());
        tLCInsureAccTraceDB.setBusyType("NB");
        tLCInsureAccTraceDB.setPolNo(this.mLCPolSchema.getPolNo());
        oldLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
        if (oldLCInsureAccTraceSet.size() == 0) {
            CError.buildErr(this, "查询保险帐户计价履历信息失败!");
            return false;
        }
        
        //循环判断账户轨迹表是否被计价
        //未计价的直接删除,已计价的进行冲回处理
        logger.debug("开始进行账户删除或者冲回的处理......");
        if(!dealRollBackData(oldLCInsureAccTraceSet))
        {
        	return false;
        }
        
        logger.debug("结束账户删除或者冲回的处理......");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        logger.debug("开始进行账户新生成处理......");
        
        //以原有未订正前的账户轨迹结构生成新的账户结构数据
        VData tVData = new VData();
        
        //获取账户数据
        LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        SQLwithBindVariables lcisqlbv = new SQLwithBindVariables();
        String tSQL_LCInsureAcc = "select * from lcinsureacc where polno='"+"?polno?"+"'";
        lcisqlbv.sql(tSQL_LCInsureAcc);
        lcisqlbv.put("polno", this.mLCPolSchema.getPolNo());
        tLCInsureAccSet = tLCInsureAccDB.executeQuery(lcisqlbv);
        
        if(tLCInsureAccSet.size()<=0)
        {
        	CError.buildErr(this,"获取账户总表数据失败!");
        	return false;
        }
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        SQLwithBindVariables lciacsqlbv = new SQLwithBindVariables();
        String tSQL_LCInsureAccClass = "select * from lcinsureaccclass where polno='"+"?polno?"+"'";
        lciacsqlbv.sql(tSQL_LCInsureAccClass);
        lciacsqlbv.put("polno", this.mLCPolSchema.getPolNo());
        
        tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(lciacsqlbv);
        if(tLCInsureAccClassSet.size()<=0)
        {
        	CError.buildErr(this,"获取账户分类表数据失败!");
        	return false;
        }
        
        //获取管理费数据
        LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
        LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
        SQLwithBindVariables afsqlbv = new SQLwithBindVariables();
        String tSQL_AccFee = "select * from lcinsureaccfee where polno='"+"?polno?"+"' ";
        afsqlbv.sql(tSQL_AccFee);
        afsqlbv.put("polno", this.mLCPolSchema.getPolNo());
        tLCInsureAccFeeSet = tLCInsureAccFeeDB.executeQuery(afsqlbv);
        if(tLCInsureAccFeeSet.size()<=0)
        {
        	CError.buildErr(this,"获取管理费数据失败!");
        	return false;
        }
        LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
        LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
        SQLwithBindVariables fcsqlbv = new SQLwithBindVariables();
        String tSQL_FeeClass = "select * from  lcinsureaccclassfee where  polno ='"+"?polno?"+"'";
        fcsqlbv.sql(tSQL_FeeClass);
        fcsqlbv.put("polno", this.mLCPolSchema.getPolNo());
        tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.executeQuery(fcsqlbv);
        if(tLCInsureAccClassFeeSet.size()<=0)
        {
        	CError.buildErr(this,"获取管理费分类数据失败!");
        	return false;
        }
        
        //获取LCPrem
        LCPremDB tLCPremDB = new LCPremDB();
        LCPremSet tLCPremSet = new LCPremSet();
        SQLwithBindVariables premsqlbv = new SQLwithBindVariables();
        String tSQL_prem = "select * from lcprem where polno = '"+"?polno?"+"'";
        premsqlbv.sql(tSQL_prem);
        premsqlbv.put("polno", this.mLCPolSchema.getPolNo());
        tLCPremSet = tLCPremDB.executeQuery(premsqlbv);
        if(tLCPremSet.size()<=0)
        {
        	CError.buildErr(this,"获取缴费项出错!");
        	return false;
        }
       
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //开始按照新的投资比例将保费重新注入账户
        HashMap tSumMoneyMap = new HashMap();
        if(!createNewAcc(oldLCInsureAccTraceSet, tLCPremSet, tSumMoneyMap))
        {
        	return false;
        }
        
        logger.debug("结束账户新生成处理......");
        
        logger.debug("开始账户数据汇总处理......");
       
       
        if(!sumAcc(tLCInsureAccSet, tLCInsureAccClassSet, tLCInsureAccFeeSet,
				tLCInsureAccClassFeeSet, tSumMoneyMap))
        {
        	return false;
        }
        
        logger.debug("结束账户数据汇总处理......");
        //准备提交数据

        prepareOutData();
        
        //提交数据
        mResult.add(map);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, "")) {
            CError.buildErr(this, "数据更新失败!");
            return false;
        }
		return true;
	}

	/**累计账户分类表/账户表/管理费分类表/管理费表
	 * @param tLCInsureAccSet
	 * @param tLCInsureAccClassSet
	 * @param tLCInsureAccFeeSet
	 * @param tLCInsureAccClassFeeSet
	 * @param tSumMoneyMap
	 */
	private boolean sumAcc(LCInsureAccSet tLCInsureAccSet,
			LCInsureAccClassSet tLCInsureAccClassSet,
			LCInsureAccFeeSet tLCInsureAccFeeSet,
			LCInsureAccClassFeeSet tLCInsureAccClassFeeSet, HashMap tSumMoneyMap) {
		try {
			for(int i=1;i<=tLCInsureAccClassSet.size();i++)
			{
				double tempPay = 0;
				String tKey = tLCInsureAccClassSet.get(i).getPayPlanCode()+tLCInsureAccClassSet.get(i).getInsuAccNo()+"PAY";
				if(tSumMoneyMap.containsKey(tKey))
				{
					tempPay = (Double)tSumMoneyMap.get(tKey);
				}
				tLCInsureAccClassSet.get(i).setSumPay(tempPay);
				String tKey1 = tLCInsureAccClassSet.get(i).getPayPlanCode()+tLCInsureAccClassSet.get(i).getInsuAccNo()+"MONEY";
				tempPay = 0;
				if(tSumMoneyMap.containsKey(tKey1))
				{
					tempPay = (Double)tSumMoneyMap.get(tKey1);
				}
				tLCInsureAccClassSet.get(i).setLastAccBala(0);
				tLCInsureAccClassSet.get(i).setInsuAccBala(tempPay);
				tLCInsureAccClassSet.get(i).setLastUnitCount(0);
				tLCInsureAccClassSet.get(i).setUnitCount(0);
				tLCInsureAccClassSet.get(i).setModifyDate(this.mCurrentDate);
				tLCInsureAccClassSet.get(i).setModifyTime(this.mCurrentTime);
				tLCInsureAccClassSet.get(i).setOperator(this.mGlobalInput.Operator);
			}
			
			this.mUpdateLCInsureAccClassSet.add(tLCInsureAccClassSet);
			
			for(int i=1;i<=tLCInsureAccSet.size();i++)
			{
				double tempPay = 0;
				String tKey = tLCInsureAccSet.get(i).getInsuAccNo()+"PAY";
				if(tSumMoneyMap.containsKey(tKey))
				{
					tempPay = (Double)tSumMoneyMap.get(tKey);
				}
				tLCInsureAccSet.get(i).setSumPay(tempPay);
				String tKey1 = tLCInsureAccSet.get(i).getInsuAccNo()+"MONEY";
				tempPay = 0;
				if(tSumMoneyMap.containsKey(tKey1))
				{
					tempPay = (Double)tSumMoneyMap.get(tKey1);
				}
				tLCInsureAccSet.get(i).setLastAccBala(0);
				tLCInsureAccSet.get(i).setInsuAccBala(tempPay);
				tLCInsureAccSet.get(i).setLastUnitCount(0);
				tLCInsureAccSet.get(i).setUnitCount(0);
				tLCInsureAccSet.get(i).setModifyDate(this.mCurrentDate);
				tLCInsureAccSet.get(i).setModifyTime(this.mCurrentTime);
				tLCInsureAccSet.get(i).setOperator(this.mGlobalInput.Operator);
			}
			
			this.mUpdateLCInsureAccSet.add(tLCInsureAccSet);
			for(int i=1;i<=tLCInsureAccClassFeeSet.size();i++)
			{
				double tempPay = 0;
				String tKey = tLCInsureAccClassFeeSet.get(i).getPayPlanCode()+tLCInsureAccClassFeeSet.get(i).getInsuAccNo()+"FEE";
				if(tSumMoneyMap.containsKey(tKey))
				{
					tempPay = (Double)tSumMoneyMap.get(tKey);
				}
				
				tLCInsureAccClassFeeSet.get(i).setFee(tempPay);
				tLCInsureAccClassFeeSet.get(i).setModifyDate(this.mCurrentDate);
				tLCInsureAccClassFeeSet.get(i).setModifyTime(this.mCurrentTime);
				tLCInsureAccClassFeeSet.get(i).setOperator(this.mGlobalInput.Operator);
			}
			
			this.mUpdateLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSet);
			for(int i=1;i<=tLCInsureAccFeeSet.size();i++)
			{
				double tempPay = 0;
				String tKey = tLCInsureAccFeeSet.get(i).getInsuAccNo()+"FEE";
				if(tSumMoneyMap.containsKey(tKey))
				{
					tempPay = (Double)tSumMoneyMap.get(tKey);
				}
				
				tLCInsureAccFeeSet.get(i).setFee(tempPay);
				tLCInsureAccFeeSet.get(i).setModifyDate(this.mCurrentDate);
				tLCInsureAccFeeSet.get(i).setModifyTime(this.mCurrentTime);
				tLCInsureAccFeeSet.get(i).setOperator(this.mGlobalInput.Operator);
			}
			
			this.mUpdateLCInsureAccFeeSet.add(tLCInsureAccFeeSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,"sumAcc出错!"+e.toString());
			return false;
		}
        
        return true;
	}

	/**创建新的账户轨迹和管理费轨迹
	 * @param oldLCInsureAccTraceSet
	 * @param tLCPremSet
	 * @param tSumMoneyMap
	 */
	private boolean createNewAcc(LCInsureAccTraceSet oldLCInsureAccTraceSet,
			LCPremSet tLCPremSet, HashMap tSumMoneyMap) {
		try {
			for(int i=1;i<=mLCPerInvestPlanSet.size();i++)
			{
				LCPerInvestPlanSchema tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
				tLCPerInvestPlanSchema = mLCPerInvestPlanSet.get(i);
				String tLimit = PubFun.getNoLimit(mLCPolSchema.getManageCom());
				String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				//获取需要冲回的账户的数据信息
				for(int n=1;n<=oldLCInsureAccTraceSet.size();n++)
				{
					if(oldLCInsureAccTraceSet.get(n).getPayPlanCode().equals(tLCPerInvestPlanSchema.getPayPlanCode())
							&&oldLCInsureAccTraceSet.get(n).getInsuAccNo().equals(tLCPerInvestPlanSchema.getInsuAccNo())
							)
					{
						tLCInsureAccTraceSchema.setSchema(oldLCInsureAccTraceSet.get(n));
						break;
					}
				}
				//非关键字段赋值
				tLCInsureAccTraceSchema.setManageCom(mLCPolSchema.getManageCom());
			    tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
			    //tLCInsureAccTraceSchema.setPayDate(); //新生成的数据PayDate不能改变
			    tLCInsureAccTraceSchema.setMakeDate(this.mCurrentDate);
			    tLCInsureAccTraceSchema.setMakeTime(this.mCurrentTime);
			    tLCInsureAccTraceSchema.setModifyDate(this.mCurrentDate);
			    tLCInsureAccTraceSchema.setModifyTime(this.mCurrentTime);
			    
			    
				
				//获取当前缴费对应的lcprem数据
				LCPremSet currentLCPremSet = new LCPremSet();
				for(int n=1;n<=tLCPremSet.size();n++)
				{
					if(tLCPremSet.get(n).getPayPlanCode().equals(tLCPerInvestPlanSchema.getPayPlanCode()))
					{
						currentLCPremSet.add(tLCPremSet.get(n));
						//break;
					}
				}
				
				//累计保费
				if(currentLCPremSet.size()<=0)
				{
					CError.buildErr(this,"获取投资保费项出错!");
					return false;
				}
				
				//初始保费
				double InpputPrem = 0;
				//注入账户的费用
				double InverstPrem = 0;
				//初始费用
				double ManageFee = 0;
				//投资比例
				double rate = 0;
				
				for(int n=1;n<=currentLCPremSet.size();n++)
				{
					InpputPrem  = PubFun.round(InpputPrem, 2) + PubFun.round(currentLCPremSet.get(n).getStandPrem(), 2);
				}
				rate = tLCPerInvestPlanSchema.getInvestRate();
				InpputPrem = PubFun.round(InpputPrem * rate,2);
				logger.debug("初始保费:"+InpputPrem);
				
				
				sumMoneyAndFee(tSumMoneyMap, tLCInsureAccTraceSchema, InpputPrem,"PAY");
				
				//开始处理管理费

				// 查询管理费
					LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
					tLMRiskFeeDB.setInsuAccNo(tLCPerInvestPlanSchema.getInsuAccNo());
					tLMRiskFeeDB.setPayPlanCode(tLCPerInvestPlanSchema.getPayPlanCode());
					tLMRiskFeeDB.setFeeKind("03"); // 03-个单管理费
					tLMRiskFeeDB.setFeeTakePlace("01"); // 01－交费时
					LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
					if (tLMRiskFeeDB.mErrors.needDealError()) {
						CError.buildErr(this, "账户管理费查询失败!");
						return false;
					}
					if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0) {
						double dRiskFee;
						for (int k = 1; k <= tLMRiskFeeSet.size(); k++) // 循环计算每条管理费
						{
							// 计算管理费金额
							dRiskFee = calRiskFee(tLMRiskFeeSet.get(k), InpputPrem);
							if (dRiskFee == -1) {
								return false;
							}
							//创建管理费轨迹
							LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
							
							tRef.transFields(tLCInsureAccFeeTraceSchema, tLCInsureAccTraceSchema);
							
							String feeSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
							tLCInsureAccFeeTraceSchema.setSerialNo(feeSerialNo);
					        tLCInsureAccFeeTraceSchema.setState("1");
							tLCInsureAccFeeTraceSchema.setMoneyType("GL");
							tLCInsureAccFeeTraceSchema.setFee(dRiskFee);
							tLCInsureAccFeeTraceSchema.setPayDate(tLCInsureAccTraceSchema.getPayDate());
							tLCInsureAccFeeTraceSchema.setFeeCode(tLMRiskFeeSet.get(k).getFeeCode());
							tLCInsureAccFeeTraceSchema.setCurrency(this.mLCPolSchema.getCurrency());
							
							this.mInsertLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
							
							
							sumMoneyAndFee(tSumMoneyMap, tLCInsureAccTraceSchema, dRiskFee,"FEE");
					        
							ManageFee = PubFun.round(ManageFee,2) +  PubFun.round(dRiskFee,2);
						}
					}
					//
				
					InverstPrem = PubFun.round(InpputPrem -ManageFee,2);
					
				tLCInsureAccTraceSchema.setAccAlterNo(this.mLCPolSchema.getPrtNo());
			    tLCInsureAccTraceSchema.setAccAlterType("1");
			    tLCInsureAccTraceSchema.setSerialNo(SerialNo);
			    
			    tLCInsureAccTraceSchema.setMoney(InverstPrem);
			    tLCInsureAccTraceSchema.setUnitCount(0);
			    tLCInsureAccTraceSchema.setState("0");
			   
			   
			    sumMoneyAndFee(tSumMoneyMap, tLCInsureAccTraceSchema, InverstPrem,"MONEY");
			     
			    this.mInsertLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "createNewAcc出错!"+e.toString());
			return false;
		}
		
		return true;
	}

	/**处理需要回滚的数据
	 * @param oldLCInsureAccTraceSet
	 */
	private boolean dealRollBackData(LCInsureAccTraceSet oldLCInsureAccTraceSet) {
		try {
			for(int i=1;i<=oldLCInsureAccTraceSet.size();i++)
			{
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				tLCInsureAccTraceSchema.setSchema(oldLCInsureAccTraceSet.get(i));
				
				LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
				LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB = new LCInsureAccFeeTraceDB();
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				String tSQL_ManageFee = "select * from lcinsureaccfeetrace where polno='"+"?polno?"+"' "
				                      + " and payplancode='"+"?payplancode?"+"' "
				                      + " and insuaccno='"+"?insuaccno?"+"' and moneytype='GL' and state='1'";
				sqlbv.sql(tSQL_ManageFee);
				sqlbv.put("polno", tLCInsureAccTraceSchema.getPolNo());
				sqlbv.put("payplancode", tLCInsureAccTraceSchema.getPayPlanCode());
				sqlbv.put("insuaccno", tLCInsureAccTraceSchema.getInsuAccNo());
				tLCInsureAccFeeTraceSet = tLCInsureAccFeeTraceDB.executeQuery(sqlbv);
				if(tLCInsureAccFeeTraceSet.size()<=0)
				{
					CError.buildErr(this,"获取管理费数据失败!");
					return false;
				}
				LCInsureAccFeeTraceSet tOldLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
				tOldLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSet);
				
				LCInsureAccFeeTraceSet tNewLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
				for(int n=1;n<=tLCInsureAccFeeTraceSet.size();n++)
				{
					LCInsureAccFeeTraceSchema tempLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
					tempLCInsureAccFeeTraceSchema.setSchema(tLCInsureAccFeeTraceSet.get(n));
					tNewLCInsureAccFeeTraceSet.add(tempLCInsureAccFeeTraceSchema);
				}
				
				
				//判断状态
				if(("0").equals(oldLCInsureAccTraceSet.get(i).getState()))
				{
					//待计价,直接删除
					this.mDelLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
					//获取账户轨迹表
					this.mDelLCInsureAccFeeTraceSet.add(tOldLCInsureAccFeeTraceSet);
				}
				else if(("1").equals(oldLCInsureAccTraceSet.get(i).getState()))
				{
					//已计价,冲回处理
					//处理原来的数据,更新 
					oldLCInsureAccTraceSet.get(i).setState("3");
					oldLCInsureAccTraceSet.get(i).setModifyDate(this.mCurrentDate);
					oldLCInsureAccTraceSet.get(i).setModifyTime(this.mCurrentTime);
					this.mUpdateLCInsureAccTraceSet.add(oldLCInsureAccTraceSet.get(i));
					
					//生成新数据
					//账户轨迹表
					String tLimit = PubFun.getNoLimit(oldLCInsureAccTraceSet.get(i).
			                getManageCom());
					String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					
					//关键字段赋值
					tLCInsureAccTraceSchema.setMoney(-tLCInsureAccTraceSchema.getMoney());
					tLCInsureAccTraceSchema.setUnitCount(-tLCInsureAccTraceSchema.getUnitCount());
					tLCInsureAccTraceSchema.setPayDate(this.mCurrentDate);
					tLCInsureAccTraceSchema.setMoneyType("TF");
					tLCInsureAccTraceSchema.setState("2");
					tLCInsureAccTraceSchema.setSerialNo(serNo);
					tLCInsureAccTraceSchema.setBusyType("EB");
					tLCInsureAccTraceSchema.setAccAlterNo(oldLCInsureAccTraceSet.get(i).getSerialNo());
					tLCInsureAccTraceSchema.setAccAlterType("6");
					tLCInsureAccTraceSchema.setValueDate("");
					//其他字段赋值
					tLCInsureAccTraceSchema.setMakeDate(this.mCurrentDate);
					tLCInsureAccTraceSchema.setMakeTime(this.mCurrentTime);
					tLCInsureAccTraceSchema.setModifyDate(this.mCurrentDate);
					tLCInsureAccTraceSchema.setModifyTime(this.mCurrentTime);
					tLCInsureAccTraceSchema.setOperator(this.mGlobalInput.Operator);
					
					this.mInsertLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
					
					//管理费表处理
					//旧数据更新
					for(int n=1;n<=tOldLCInsureAccFeeTraceSet.size();n++)
					{
						tOldLCInsureAccFeeTraceSet.get(n).setState("3");
						tOldLCInsureAccFeeTraceSet.get(n).setModifyDate(this.mCurrentDate);
						tOldLCInsureAccFeeTraceSet.get(n).setModifyTime(this.mCurrentTime);
					}
					
					
					this.mUpdateLCInsureAccFeeTraceSet.add(tOldLCInsureAccFeeTraceSet);
					
					//新数据生成
					//关键字段赋值
					for(int n=1;n<=tNewLCInsureAccFeeTraceSet.size();n++)
					{
						String serialno = PubFun1.CreateMaxNo("SERIALNO", tLimit);
						tNewLCInsureAccFeeTraceSet.get(n).setSerialNo(serialno);
						tNewLCInsureAccFeeTraceSet.get(n).setFee(-tNewLCInsureAccFeeTraceSet.get(n).getFee());
						tNewLCInsureAccFeeTraceSet.get(n).setFeeUnit(-tNewLCInsureAccFeeTraceSet.get(n).getFeeUnit());
						tNewLCInsureAccFeeTraceSet.get(n).setState("2");
						//其他字段赋值
						tNewLCInsureAccFeeTraceSet.get(n).setOperator(this.mGlobalInput.Operator);
						tNewLCInsureAccFeeTraceSet.get(n).setMakeDate(this.mCurrentDate);
						tNewLCInsureAccFeeTraceSet.get(n).setMakeTime(this.mCurrentTime);
						tNewLCInsureAccFeeTraceSet.get(n).setModifyDate(this.mCurrentDate);
						tNewLCInsureAccFeeTraceSet.get(n).setModifyTime(this.mCurrentTime);
					}
					
					this.mInsertLCInsureAccFeeTraceSet.add(tNewLCInsureAccFeeTraceSet);
					
				}
				else
				{
					//假设存在有2,3等状态,不做处理
					continue;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "dealRollBackData方法出错!"+e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 准备需要处理的数据
	 */
	private void prepareOutData() {
		
		//删除
        if(this.mDelLCInsureAccTraceSet.size()>0)
        {
        	map.put(mDelLCInsureAccTraceSet,"DELETE");
        }
        if(this.mDelLCInsureAccFeeTraceSet.size()>0)
        {
        	map.put(mDelLCInsureAccFeeTraceSet,"DELETE");
        }
        
        //插入
        if(this.mInsertLCInsureAccTraceSet.size()>0)
        {
        	map.put(mInsertLCInsureAccTraceSet,"INSERT");
        }
        
        if(this.mInsertLCInsureAccFeeTraceSet.size()>0)
        {
        	map.put(mInsertLCInsureAccFeeTraceSet,"INSERT");
        }
        
        //修改
        if(this.mUpdateLCInsureAccTraceSet.size()>0)
        {
        	map.put(mUpdateLCInsureAccTraceSet,"UPDATE");
        }
        
        if(this.mUpdateLCInsureAccClassSet.size()>0)
        {
        	map.put(mUpdateLCInsureAccClassSet,"UPDATE");
        }
        
        if(this.mUpdateLCInsureAccSet.size()>0)
        {
        	map.put(mUpdateLCInsureAccSet,"UPDATE");
        }
        
        
        if(this.mUpdateLCInsureAccFeeTraceSet.size()>0)
        {
        	map.put(mUpdateLCInsureAccFeeTraceSet,"UPDATE");
        }
        
        if(this.mUpdateLCInsureAccClassFeeSet.size()>0)
        {
        	map.put(mUpdateLCInsureAccClassFeeSet,"UPDATE");
        }
        
        if(this.mUpdateLCInsureAccFeeSet.size()>0)
        {
        	map.put(mUpdateLCInsureAccFeeSet,"UPDATE");
        }
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        //增加一段update 的语句
        String tSQL_Update = "update lcinsureacctrace  set valuedate=null,shouldvaluedate=null where polno='"+"?polno?"+"' and state='0'";
        sqlbv.sql(tSQL_Update);
        sqlbv.put("polno", this.mLCPolSchema.getPolNo());
        map.put(sqlbv, "UPDATE");
	}

	/**
	 * 按照账户/账户+缴费编码 累计缴费,手续费
	 * 
	 * @param tSumMoneyMap
	 * @param tLCInsureAccTraceSchema
	 * @param InpputPrem
	 * @param tSumType
	 */
	private void sumMoneyAndFee(HashMap tSumMoneyMap,
			LCInsureAccTraceSchema tLCInsureAccTraceSchema, double InpputPrem,String tSumType) {
		String tKey = tLCInsureAccTraceSchema.getPayPlanCode()+tLCInsureAccTraceSchema.getInsuAccNo()+tSumType;
		
		
		if(tSumMoneyMap.containsKey(tKey))
		{
			double tempFeeMoney = (Double)tSumMoneyMap.get(tKey);
			tempFeeMoney = PubFun.round(tempFeeMoney, 2);
			tSumMoneyMap.put(tKey, tempFeeMoney+InpputPrem);
		}
		else
		{
			tSumMoneyMap.put(tKey, InpputPrem);
		}
		
		String tKey1 = tLCInsureAccTraceSchema.getInsuAccNo()+tSumType;
		
		if(tSumMoneyMap.containsKey(tKey1))
		{
			double tempFeeMoney = (Double)tSumMoneyMap.get(tKey1);
			tempFeeMoney = PubFun.round(tempFeeMoney, 2);
			tSumMoneyMap.put(tKey1, tempFeeMoney+InpputPrem);
		}
		else
		{
			tSumMoneyMap.put(tKey1, InpputPrem);
		}
	}
	
	
	/**
	 * 计算管理费
	 * @param pLMRiskFeeSchema
	 * @param dSumPrem
	 * @return
	 */
	private double calRiskFee(LMRiskFeeSchema pLMRiskFeeSchema, double dSumPrem) {
		double dRiskFee = 0.0;
		if (pLMRiskFeeSchema.getFeeCalModeType().equals("0")) // 0-直接取值
		{
			if (pLMRiskFeeSchema.getFeeCalMode().equals("01")) // 固定值内扣
			{
				dRiskFee = pLMRiskFeeSchema.getFeeValue();
			} else if (pLMRiskFeeSchema.getFeeCalMode().equals("02")) // 固定比例内扣
			{
				dRiskFee = dSumPrem * pLMRiskFeeSchema.getFeeValue();
			} else {
				dRiskFee = pLMRiskFeeSchema.getFeeValue(); // 默认情况
			}
		} else if (pLMRiskFeeSchema.getFeeCalModeType().equals("1")) // 1-SQL算法描述
		{
			// 准备计算要素
			Calculator tCalculator = new Calculator();
			tCalculator.setCalCode(pLMRiskFeeSchema.getFeeCalCode());
			// 累计已交保费
            tCalculator.addBasicFactor("InsuredNo", mLCPolSchema.getInsuredNo());
			tCalculator.addBasicFactor("SumPrem", String.valueOf(dSumPrem));
            tCalculator.addBasicFactor("AccValue", String.valueOf(dSumPrem));

            tCalculator.addBasicFactor("PayIntv", String.valueOf(mLCPolSchema.getPayIntv()));
            tCalculator.addBasicFactor("Interval", String.valueOf(1));
			String sCalResultValue = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				CError.buildErr(this, "管理费计算失败!");
				return -1;
			}

			try {
				dRiskFee = Double.parseDouble(sCalResultValue);
			} catch (Exception e) {
				CError.buildErr(this, "管理费计算结果错误!" + "错误结果：" + sCalResultValue);
				return -1;
			}
		}

		return dRiskFee;
	}

}
