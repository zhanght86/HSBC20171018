package com.sinosoft.lis.acc;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPerInvestPlanDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPerInvestPlanSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPerInvestPlanSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LCPerInvestPlanBL
{
private static Logger logger = Logger.getLogger(LCPerInvestPlanBL.class);


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
	private String mUintLinkValiFlag;

	private LCPerInvestPlanSet mLCPerInvestPlanSet = new LCPerInvestPlanSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private double sumpay;

	// private String mPlanCode = "";
	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值

	String mCurrentTime = PubFun.getCurrentTime();

	private MMap map = new MMap();

	public LCPerInvestPlanBL()
	{}

	public boolean submitData(VData cInputData, String operate)
	{
		mInputData = cInputData;
		// 数据操作字符串拷贝到本类中
		this.mOperate = operate;
		if (!getInputData())
		{
			this.buildError("submitData", "无法获取输入信息");
			return false;
		}
		if (!checkData())
		{
			this.buildError("", "提交的数据有误，请核对后再操作");
			return false;
		}
		if (!dealData())
		{
			this.buildError("submitData", "处理数据时失败");
			return false;
		}
		if (!prepareData())
		{
			return false;
		}
		logger.debug("--------end prepareOutputData");
		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, mOperate))
		{ // 数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PMAscriptionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
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
		mUintLinkValiFlag = (String) mInputData.getObjectByObjectName("String", 0);
		logger.debug("mPlanFlag====" + mUintLinkValiFlag);

		// 要从VData　类的对象中取出全局变量
		mLCPerInvestPlanSet.set((LCPerInvestPlanSet) mInputData.getObjectByObjectName("LCPerInvestPlanSet", 0));
		if (mGlobalInput == null || mLCPerInvestPlanSet == null)
		{
			buildError("getInputData", "传过来的数据不全");
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLCPerInvestPlanSet.get(1).getPolNo());
		if (tLCPolDB.getInfo())
		{
			mLCPolSchema.setSchema(tLCPolDB.getSchema());
			if (!mUintLinkValiFlag.equals("2") && !mUintLinkValiFlag.equals("4"))
			{
				buildError("getInputData", "请选择正确的参数!");
				return false;
			}
			if (mUintLinkValiFlag.trim().equals(""))
			{
				mLCPolSchema.setUintLinkValiFlag("4");
			}
			else
			{
				mLCPolSchema.setUintLinkValiFlag(mUintLinkValiFlag);
			}
		}
		else
		{
			buildError("getInputData", "数据查询失败");
			return false;
		}
		
		for(int i=1;i<=this.mLCPerInvestPlanSet.size();i++)
		{
			//tongmeng 2010-11-17 多币种支持
			//tongmeng 2010-11-30 modify
			//如果分币种账户缴费处理
			if(mLCPerInvestPlanSet.get(i).getCurrency()==null||mLCPerInvestPlanSet.get(i).getCurrency().equals(""))
			{
				mLCPerInvestPlanSet.get(i).setCurrency(mLCPolSchema.getCurrency());
			}
		}
		return true;
	}

	private boolean checkData()
	{
		double a = 0;
		//tongmeng 2010-11-29 modify 
		//分币种账户缴费处理
		Hashtable tCurrencyHashtable = new Hashtable();
		logger.debug("mLCPerInvestPlanSet.get(1).getInputMode()===" + mLCPerInvestPlanSet.get(1).getInputMode());
		if (mLCPerInvestPlanSet.get(1).getInputMode().equals("1")) // 按比例投资
		{
			
			
			for (int i = 1; i <= mLCPerInvestPlanSet.size(); i++)
			{
				// if(mLCPerInvestPlanSet.get(i).getInvestMaxRate()<0||mLCPerInvestPlanSet.get(i).getInvestMaxRate()>1){
				// CError.buildErr(this, "投资比例上限应大等于0,小于等于1,请重新录入!");
				// return false;
				// }
				// if(mLCPerInvestPlanSet.get(i).getInvestMinRate()<0||mLCPerInvestPlanSet.get(i).getInvestMinRate()>1){
				// CError.buildErr(this, "投资比例下限应大等于0,小于等于1,请重新录入!");
				// return false;
				// }

				if (mLCPerInvestPlanSet.get(i).getInvestRate() < 0 || mLCPerInvestPlanSet.get(i).getInvestRate() > 1)
				{
					CError.buildErr(this, "投资比例应大于等于0,小于等于1,请重新录入!");
					return false;
				}
				a += mLCPerInvestPlanSet.get(i).getInvestRate();
				
				String tCurrency = mLCPerInvestPlanSet.get(i).getCurrency()+":"+mLCPerInvestPlanSet.get(i).getPayPlanCode();
				double tRate = mLCPerInvestPlanSet.get(i).getInvestRate();
				if(tCurrencyHashtable.containsKey(tCurrency))
				{
					double tempRate = (Double)tCurrencyHashtable.get(tCurrency);
					tempRate = PubFun.round(tempRate,2) + PubFun.round(tRate, 2);
					tCurrencyHashtable.put(tCurrency,tempRate);
				}
				else
				{
					tCurrencyHashtable.put(tCurrency, tRate);
				}
			}
			
			ExeSQL tExeSQL = new ExeSQL();
			Enumeration eKey=tCurrencyHashtable.keys(); 
			while(eKey.hasMoreElements()) 
			{ 
				String key=(String)eKey.nextElement();
				double tValue = (Double)tCurrencyHashtable.get(key);
				
				 
				if(tValue!=1)
				{
					String tCurrency = key.substring(0,key.indexOf(":"));
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					String tSQL_Currency = "select currname from ldcurrency where currcode='"+"?tCurrency?"+"' ";
					sqlbv.sql(tSQL_Currency);
					sqlbv.put("tCurrency", tCurrency);
					String tCurrencyName = "";
					tCurrencyName = tExeSQL.getOneValue(sqlbv);
					CError.buildErr(this, tCurrencyName+"账户投资比例不为1");
					return false;
				}
				
			}
			// if (Arith.round(a, 2) != 1)
			// {
			// CError.buildErr(this, "同一缴费帐户下的所有投资帐户的投资比例之和不为1,请重新录入");
			// return false;
			// }
		}
		else
		{
			LCPremDB tLCPremDB = new LCPremDB();
			LCPremSet tLCPremSet = new LCPremSet();
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String strpolno = mLCPerInvestPlanSet.get(1).getPolNo();
			String strPayPlanCode = mLCPerInvestPlanSet.get(1).getPayPlanCode();
			String SQL = "select * from LCPrem where polno='" + "?strpolno?"
					+ "' and PayPlanCode='" + "?strPayPlanCode?" + "'";
			sqlbv.sql(SQL);
			sqlbv.put("strpolno", strpolno);
			sqlbv.put("strPayPlanCode", strPayPlanCode);

			tLCPremSet = tLCPremDB.executeQuery(sqlbv);
			// tLCPremDB.setPolNo(mLCPerInvestPlanSet.get(1).getPolNo());
			// tLCPremDB.setPayPlanCode(mLCPerInvestPlanSet.get(1).getPayPlanCode());
			for (int j = 1; j <= tLCPremSet.size(); j++)
			{
				sumpay = sumpay + tLCPremSet.get(j).getPrem();
			}
			for (int i = 1; i <= mLCPerInvestPlanSet.size(); i++)
			{
				if (mLCPerInvestPlanSet.get(i).getInvestMoney() < 0)
				{
					CError.buildErr(this, "投资金额应大于等于0,请重新录入!");
					return false;
				}
				a += mLCPerInvestPlanSet.get(i).getInvestMoney();
			}
			if (Arith.round(a, 2) != sumpay)
			{
				CError.buildErr(this, "同一缴费帐户下的所有投资帐户的投资金额之和不等于该缴费帐户下的总保费(" + sumpay + "),请重新录入");
				return false;
			}

		}
		return true;
	}

	private boolean dealData()
	{
		logger.debug("mPMRetireRuleSet.size()=====" + mLCPerInvestPlanSet.size());
		// 新增处理
		if (mOperate.equals("INSERT"))
		{
			if (!getOtherDate())
			{
				return false;
			}
			for (int i = 1; i <= mLCPerInvestPlanSet.size(); i++)
			{

				if (prepareNewData(i) == false)
				{
					return false;
				}
			}
			map.put(mLCPerInvestPlanSet, "DELETE&INSERT");
			map.put(mLCPolSchema, "UPDATE");
		}
		// 删除处理
		if (mOperate.equals("DELETE"))
		{
			// 准备需要删除的数据
			if (prepareOldData() == false)
			{
				return false;
			}
		}
		if (mOperate.equals("UPDATE"))
		{

			LCPerInvestPlanSchema tempLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
			tempLCPerInvestPlanSchema = mLCPerInvestPlanSet.get(1);
			LCPerInvestPlanDB tLCPerInvestPlanDB = new LCPerInvestPlanDB();
			tLCPerInvestPlanDB.setPolNo(tempLCPerInvestPlanSchema.getPolNo());
			tLCPerInvestPlanDB.setInsuAccNo(tempLCPerInvestPlanSchema.getInsuAccNo());
			tLCPerInvestPlanDB.setPayPlanCode(tempLCPerInvestPlanSchema.getPayPlanCode());
			LCPerInvestPlanSet tLCPerInvestPlanSet = new LCPerInvestPlanSet();
			LCPerInvestPlanSet tempLCPerInvestPlanSet = new LCPerInvestPlanSet();
			tLCPerInvestPlanSet = tLCPerInvestPlanDB.query();
			// 当没有查询到结果时，用CError　类在前台报错！
			if (tLCPerInvestPlanSet == null || tLCPerInvestPlanSet.size() <= 0)
			{
				mErrors.copyAllErrors(tLCPerInvestPlanDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "TkBdDealBL";
				tError.functionName = "checkData";
				tError.errorMessage = "没有查询到保险单号编码:" + mLCPerInvestPlanSet.get(1).getPolNo() + " 账户号编码:"
						+ mLCPerInvestPlanSet.get(1).getInsuAccNo() + " 的数据,不能做数据修改!!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!getOtherDate())
			{
				return false;
			}
			String oldMakeDate = tLCPerInvestPlanSet.get(1).getMakeDate();
			String oldMakeTime = tLCPerInvestPlanSet.get(1).getMakeTime();
			String oldOperator = tLCPerInvestPlanSet.get(1).getOperator();

			for (int i = 1; i <= mLCPerInvestPlanSet.size(); i++)
			{
				LCPerInvestPlanSchema tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
				tLCPerInvestPlanSchema = mLCPerInvestPlanSet.get(i);
				tLCPerInvestPlanSchema.setModifyOperator(mGlobalInput.Operator);
				tLCPerInvestPlanSchema.setOperator(oldOperator);
				tLCPerInvestPlanSchema.setMakeDate(oldMakeDate); // 当前值
				tLCPerInvestPlanSchema.setMakeTime(oldMakeTime);
				tLCPerInvestPlanSchema.setModifyDate(mCurrentDate); // 当前值
				tLCPerInvestPlanSchema.setModifyTime(mCurrentTime);
				tempLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);

			}
			SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
			String strPolNo = mLCPerInvestPlanSet.get(1).getPolNo();
			String strInsuAccNo = mLCPerInvestPlanSet.get(1).getInsuAccNo();
			String strPayPlanCode = mLCPerInvestPlanSet.get(1).getPayPlanCode();
			tsqlbv.sql("delete from LCPerInvestPlan where PolNo='" + "?strPolNo?"
					+ "' and InsuAccNo='" + "?strInsuAccNo?" + "' and PayPlanCode='"
					+ "?strPayPlanCode?" + "'");
			tsqlbv.put("strPolNo", strPolNo);
			tsqlbv.put("strInsuAccNo", strInsuAccNo);
			tsqlbv.put("strPayPlanCode", strPayPlanCode);
			map.put(tsqlbv, "DELETE");
			map.put(tempLCPerInvestPlanSet, "DELETE&INSERT");
			/*
			 * map.put("update LCPerInvestPlan set PolNo='" +
			 * mLCPerInvestPlanSet.get(1).getPolNo()+ "',InsuAccNo='" +
			 * mLCPerInvestPlanSet.get(1).getInsuAccNo() +
			 * "' where PayPlanCode='" +
			 * mLCPerInvestPlanSet.get(1).getPayPlanCode() + "'", "UPDATE");
			 * //执行更新语句
			 */
			map.put(mLCPolSchema, "UPDATE");
			return true;
		}

		return true;
	}

	private boolean prepareNewData(int tIndex)
	{
		// 准备计算要素信息数据

		mLCPerInvestPlanSet.get(tIndex).setModifyOperator(mGlobalInput.Operator);
		mLCPerInvestPlanSet.get(tIndex).setOperator(mGlobalInput.Operator);
		mLCPerInvestPlanSet.get(tIndex).setMakeDate(mCurrentDate); // 当前值
		mLCPerInvestPlanSet.get(tIndex).setMakeTime(mCurrentTime);
		mLCPerInvestPlanSet.get(tIndex).setModifyDate(mCurrentDate); // 当前值
		mLCPerInvestPlanSet.get(tIndex).setModifyTime(mCurrentTime);
	
		
		return true;
	}

	private boolean prepareOldData()
	{
		LCPerInvestPlanSchema tempLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
		tempLCPerInvestPlanSchema = mLCPerInvestPlanSet.get(1);
		LCPerInvestPlanDB tLCPerInvestPlanDB = new LCPerInvestPlanDB();
		tLCPerInvestPlanDB.setPolNo(tempLCPerInvestPlanSchema.getPolNo());
		tLCPerInvestPlanDB.setInsuAccNo(tempLCPerInvestPlanSchema.getInsuAccNo());
		// tLCPerInvestPlanDB.setPayPlanCode(tempLCPerInvestPlanSchema.getPayPlanCode());
		LCPerInvestPlanSet tLCPerInvestPlanSet = tLCPerInvestPlanDB.query();
		// 当没有查询到结果时，用CError　类在前台报错！
		if (tLCPerInvestPlanSet == null || tLCPerInvestPlanSet.size() <= 0)
		{
			mErrors.copyAllErrors(tLCPerInvestPlanDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "TkBdDealBL";
			tError.functionName = "checkData";
			tError.errorMessage = "没有找到相应的数据,无法删除!";
			this.mErrors.addOneError(tError);
			return false;
		}
		SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
		tsqlbv.sql("delete from LCPerInvestPlan where PolNo='" + "?strPolNo?" + "'");
		tsqlbv.put("strPolNo", tempLCPerInvestPlanSchema.getPolNo());

		map.put(tsqlbv, "DELETE");

		/*
		 * "' and InsuAccNo='" + tempLCPerInvestPlanSchema.getInsuAccNo() +
		 * "' and PayPlanCode='" + tempLCPerInvestPlanSchema.getPayPlanCode() +
		 * "'", "DELETE");
		 */
		return true;
	}

	private boolean getOtherDate()
	{
		String GrpPolNo = "";
		String GrpContNo = "";
		String InsuredNo = "";
		String RiskCode = "";
		String ContNo = "";

		// LCPerInvestPlanDB tLCPerInvestPlanDB=new LCPerInvestPlanDB();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLCPerInvestPlanSet.get(1).getPolNo());
		if (tLCPolDB.getInfo())
		{
			GrpPolNo = tLCPolDB.getGrpPolNo();
			GrpContNo = tLCPolDB.getGrpContNo();
			InsuredNo = tLCPolDB.getInsuredNo();
			RiskCode = tLCPolDB.getRiskCode();
			ContNo = tLCPolDB.getContNo();
		}
		else
		{
			return false;
		}
		for (int i = 1; i <= mLCPerInvestPlanSet.size(); i++)
		{
			mLCPerInvestPlanSet.get(i).setGrpContNo(GrpContNo);
			mLCPerInvestPlanSet.get(i).setGrpPolNo(GrpPolNo);
			mLCPerInvestPlanSet.get(i).setInsuredNo(InsuredNo);
			mLCPerInvestPlanSet.get(i).setRiskCode(RiskCode);
			mLCPerInvestPlanSet.get(i).setContNo(ContNo);
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg)
	{
		CError cError = new CError();
		cError.moduleName = "PMAscriptionRuleBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
