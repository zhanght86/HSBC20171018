package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.lis.vschema.FICustomerAccTraceBakeSet;
import com.sinosoft.lis
    .vschema.FICustomerAccTraceSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
 
public abstract class FICustomer 
{
private static Logger logger = Logger.getLogger(FICustomer.class);

	public CErrors mErrors = new CErrors();

	public MMap mMap = new MMap();

	/** 财务收费，其他模块核销 */
	protected boolean submitData(VData cInputData, String cFlagValue)
	{
		if (!getInputData(cInputData))
			return false;
		if (!dealData())
		{
			return false;
		}
		return true;
	}

	protected abstract boolean getInputData(VData cInputData);

	protected abstract boolean dealData();

	/**
	 * 创建或更新客户账户 账号类型 aAccType,客户号码 CustomerNo,客户类型 CusotmerType,合同号码
	 * ContNo,业务号码 OperationNo,业务类型 OperationType, 交费方式 PayMode,操作类型
	 * OperType,其它号码 OtherNo,借贷标记 DCFlag,币别 Currency,本次发生额 Money
	 */
	protected final boolean dealAccount(String aAccType, String aContNo, String aOperationNo, String aOperationType,
			String aPayMode, String aOperType, String aOtherNo, String aDCFlag, String aCurrency, double aMoney,
			String aState, String ConfFlag, String EnteraccFalg)
	{
		String aCustomerNo = "";
		String aCustomerType = "1";
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aContNo);
		LCContSet tLCContSet = tLCContDB.query();
		LCContSchema tLCContSchema = tLCContSet.get(1);
		if (tLCContSet == null || tLCContSet.size() < 1)
		{
			buildError("FICustomer", "dealAccount", "创建更新客户账号失败，无法找到保单！");
			return false;
		}
		else
		{
			if (!tLCContSchema.getAppntNo().equals("") && !tLCContSchema.getAppntNo().equals("null")
					&& tLCContSchema.getAppntNo() != null)
			{
				aCustomerNo = tLCContSchema.getAppntNo();
			}
			else
			{
				buildError("FICustomer", "dealAccount", "创建更新客户账号失败，没有客户信息！");
				return false;
			}
		}
		String InsuAccNo = "";
		double bMoney = 0.0;
		FICustomerAccDB tFICustomerAccDB = new FICustomerAccDB();
		tFICustomerAccDB.setCustomerNo(aCustomerNo);
		tFICustomerAccDB.setAccType(aAccType);
		tFICustomerAccDB.setCurrency(aCurrency);
		FICustomerAccSet tFICustomerAccSet = tFICustomerAccDB.query();
		FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();

		//		 查看本次循环中的已建立账户
		boolean aFlag = true;// 本次循环中没有建立账户的标志，没有则true，有则false
		if (mMap.getAllObjectByObjectName("FICustomerAccSet", 0).length > 0)
		{
			Object[] tttFICustomerAccSet = mMap.getAllObjectByObjectName("FICustomerAccSet", 0);
			for (int k = 0; k < tttFICustomerAccSet.length; k++)
			{
				FICustomerAccSet ttFICustomerAccSet = (FICustomerAccSet) tttFICustomerAccSet[k];
				if (ttFICustomerAccSet != null && ttFICustomerAccSet.size() > 0)
				{
					for (int i = 1; i <= ttFICustomerAccSet.size(); i++)
					{
						if (ttFICustomerAccSet.get(i).getCustomerNo().equals(aCustomerNo)
								&&ttFICustomerAccSet.get(i).getCurrency().equals(aCurrency)
								)
						{
							aFlag = false;
							tFICustomerAccSchema = ttFICustomerAccSet.get(i);
							if (aDCFlag.equals("C"))
							{
								bMoney = -aMoney;
							}
							else
							{
								bMoney = aMoney;
							}
							tFICustomerAccSchema.setSummoney(bMoney + tFICustomerAccSchema.getSummoney());
							tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
							tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());
							break;
						}
					}
				}
				if (!aFlag)
				{
					break;
				}
			}
		}
		if (aFlag)
		{
		// 账户为空，新建账户
		if (tFICustomerAccSet == null || tFICustomerAccSet.size() < 1)
		{

			InsuAccNo = PubFun1.CreateMaxNo("FIINSUACCNO", "67");
			tFICustomerAccSchema.setInsuAccNo(InsuAccNo);
			tFICustomerAccSchema.setCurrency(aCurrency);
			tFICustomerAccSchema.setCustomerNo(aCustomerNo);
			tFICustomerAccSchema.setCustomerType(aCustomerType);
			tFICustomerAccSchema.setAccType(aAccType);
			tFICustomerAccSchema.setSummoney(aMoney);
			tFICustomerAccSchema.setState("1");
			tFICustomerAccSchema.setOperator("001");
			tFICustomerAccSchema.setMakeDate(PubFun.getCurrentDate());
			tFICustomerAccSchema.setMakeTime(PubFun.getCurrentTime());
			tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
			tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());

		}
		else
		{
			tFICustomerAccSchema = tFICustomerAccSet.get(1);

			if (aDCFlag.equals("C"))
			{
				bMoney = -aMoney;
			}
			else
			{
				bMoney = aMoney;
			}
			tFICustomerAccSchema.setSummoney(bMoney + tFICustomerAccSchema.getSummoney());
			tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
			tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());
		}
		}
		InsuAccNo = tFICustomerAccSchema.getInsuAccNo();
		String Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");

		if (Sequence == null || InsuAccNo == null)
		{
			buildError("FICustomer", "createAccount", "生成客户账户流水号失败！");
			return false;
		}
		else
		{
			logger.debug("本次客户账户轨迹表流水号:::::::::::::::::::" + Sequence);
			logger.debug("本次客户账户流水号::::::::::::::::" + InsuAccNo);
		}

		FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
		tFICustomerAccTraceSchema.setSequence(Sequence);
		tFICustomerAccTraceSchema.setInsuAccNo(InsuAccNo);
		tFICustomerAccTraceSchema.setCustomerNo(aCustomerNo);
		tFICustomerAccTraceSchema.setCustomerType(aCustomerType);
		tFICustomerAccTraceSchema.setContNo(aContNo);
		tFICustomerAccTraceSchema.setOperationNo(aOperationNo);
		tFICustomerAccTraceSchema.setOperationType(aOperationType);
		tFICustomerAccTraceSchema.setPayMode(aPayMode);
		tFICustomerAccTraceSchema.setOperType(aOperType);
		tFICustomerAccTraceSchema.setOtherNo(aOtherNo);
		tFICustomerAccTraceSchema.setDCFlag(aDCFlag);
		tFICustomerAccTraceSchema.setCurrency(aCurrency);
		tFICustomerAccTraceSchema.setMoney(aMoney);
		tFICustomerAccTraceSchema.setState(aState);
		tFICustomerAccTraceSchema.setOperator("001");
		tFICustomerAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tFICustomerAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
		if (ConfFlag.equals("1"))
		{
			tFICustomerAccTraceSchema.setConfDate(PubFun.getCurrentDate());
			tFICustomerAccTraceSchema.setConfTime(PubFun.getCurrentTime());
		}
		if (EnteraccFalg.equals("1"))
		{
			tFICustomerAccTraceSchema.setEnterAccDate(PubFun.getCurrentDate());
			tFICustomerAccTraceSchema.setEnterAccTime(PubFun.getCurrentTime());
		}
		FICustomerAccTraceSet mFICustomerAccTraceSet = new FICustomerAccTraceSet();
		mFICustomerAccTraceSet.add(tFICustomerAccTraceSchema);
		if (mFICustomerAccTraceSet.size() > 0)
		{
			mMap.put(mFICustomerAccTraceSet, "DELETE&INSERT");
		}
		FICustomerAccSet mFICustomerAccSet = new FICustomerAccSet();
		mFICustomerAccSet.add(tFICustomerAccSchema);
		if (mFICustomerAccSet.size() > 0)
		{
			mMap.put(mFICustomerAccSet, "DELETE&INSERT");
		}
		return true;
	}

	/** 删除客户账户 */
	protected final boolean deleteAccount(String aAccType, String aCustomerNo, String InsuAccNo)
	{
		FICustomerAccDB tFICustomerAccDB = new FICustomerAccDB();
		tFICustomerAccDB.setCustomerNo(aCustomerNo);
		tFICustomerAccDB.setAccType(aAccType);
		if (InsuAccNo != null)
		{
			tFICustomerAccDB.setInsuAccNo(InsuAccNo);
		}
		FICustomerAccSet tFICustomerAccSet = tFICustomerAccDB.query();
		if (tFICustomerAccSet.size() > 0)
		{
			mMap.put(tFICustomerAccSet, "DELETE");
		}

		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		tFICustomerAccTraceDB.setCustomerNo(aCustomerNo);
		if (InsuAccNo != null)
		{
			tFICustomerAccTraceDB.setInsuAccNo(InsuAccNo);
		}
		FICustomerAccTraceSet tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
		if (tFICustomerAccTraceSet.size() > 0)
		{
			mMap.put(tFICustomerAccTraceSet, "DELETE");
		}
		return true;
	}

	/** 备份客户账户 */
	public final boolean backupAccount(String aAccType, String aCustomerNo, String InsuAccNo)
	{
		Reflections tRef = new Reflections();
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		tFICustomerAccTraceDB.setCustomerNo(aCustomerNo);
		tFICustomerAccTraceDB.setInsuAccNo(InsuAccNo);
		FICustomerAccTraceSet tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
		if (tFICustomerAccTraceSet.size() > 0)
		{
			mMap.put(tFICustomerAccTraceSet, "DELETE");
		}
		FICustomerAccTraceBakeSet tFICustomerAccTraceBakeSet = new FICustomerAccTraceBakeSet();
		for (int i = 1; i <= tFICustomerAccTraceSet.size(); i++)
		{
			FICustomerAccTraceBakeSchema tFICustomerAccTraceBakeSchema = new FICustomerAccTraceBakeSchema();
			tRef.transFields(tFICustomerAccTraceBakeSchema, tFICustomerAccTraceSet.get(i));
			tFICustomerAccTraceBakeSet.add(tFICustomerAccTraceBakeSchema);
		}
		if (tFICustomerAccTraceBakeSet.size() > 0)
		{
			mMap.put(tFICustomerAccTraceBakeSet, "INSERT");
		}
		return true;
	}

	/**
	 * 查询有效可用的客户账户余额 OperationType 1、 新单 2、 续期 3、 保全 4、 赔案 5、 领取
	 * 2012-01-31 modify
	 * 增加按照币种查询
	 */
	public final double queryAccount(String aOperationNo, String aOperationType,String tCurrency)
	{
		double money = 0.0;
		FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		String tSql = "select * from FICustomerAccTrace where operationno = '" + "?aOperationNo?" + "' and operationtype = '"
					+ "?aOperationType?" + "' and state in ('00','03') "
					+ " and currency='"+"?tCurrency?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("aOperationNo",aOperationNo);
		sqlbv1.put("aOperationType",aOperationType);
		sqlbv1.put("tCurrency",tCurrency);
		tFICustomerAccTraceSet = tFICustomerAccTraceDB.executeQuery(sqlbv1);
		if (tFICustomerAccTraceSet.size() > 0)
		{
			for (int i = 1; i <= tFICustomerAccTraceSet.size(); i++)
			{
				if (tFICustomerAccTraceSet.get(i).getDCFlag().equals("D"))
				{
					money += tFICustomerAccTraceSet.get(i).getMoney();
				}
				else
				{
					money -= tFICustomerAccTraceSet.get(i).getMoney();
				}

			}
		}
		return money;
	}
	
	/////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////

	/**
	 * 查询客户账户轨迹表 OperationType 1、 新单 2、 续期 3、 保全 4、 赔案 5、 领取
	 */
	public final FICustomerAccTraceSet queryAccountTrace(String aOperationNo, String aOperationType, String aState)
	{
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		tFICustomerAccTraceDB.setOperationNo(aOperationNo);
		tFICustomerAccTraceDB.setOperationType(aOperationType);
		tFICustomerAccTraceDB.setState(aState);
		FICustomerAccTraceSet tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
		return tFICustomerAccTraceSet;
	}

	/**
	 * 查询有效可用的客户账户表 OperationType 1、 新单 2、 续期 3、 保全 4、 赔案 5、 领取 state 00 03
	 */
	public final FICustomerAccSet queryAccountAcc(String aAccType, String aOperationNo, String aOperationType)
	{
		FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		String tSql = "select * from FICustomerAccTrace where OperationNo = '" + "?aOperationNo?" + "' and operationtype = '"
				+ "?aOperationType?" + "' and state in ('00','03') ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("aOperationNo",aOperationNo);
		sqlbv2.put("aOperationType",aOperationType);
		tFICustomerAccTraceSet = tFICustomerAccTraceDB.executeQuery(sqlbv2);

		FICustomerAccDB tFICustomerAccDB = new FICustomerAccDB();
		FICustomerAccSet tFICustomerAccSet = new FICustomerAccSet();
		if (tFICustomerAccTraceSet.size() > 0)
		{
			tFICustomerAccDB.setCustomerNo(tFICustomerAccTraceSet.get(1).getCustomerNo());
			tFICustomerAccDB.setAccType(aAccType);
			tFICustomerAccSet = tFICustomerAccDB.query();
		}
		return tFICustomerAccSet;
	}

	///////////////////////////////////////////////////////////////////////
	/**
	 * 查询有效可用的客户账户表 OperationType 1、 新单 2、 续期 3、 保全 4、 赔案 5、 领取 state 00 03
	 * 分币种
	 */
	public final FICustomerAccSet queryAccountAcc(String aAccType, String aOperationNo, String aOperationType,String aCurrency)
	{
		FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		String tSql = "select * from FICustomerAccTrace where OperationNo = '" + "?aOperationNo?" + "' and operationtype = '"
				+ "?aOperationType?" + "' and state in ('00','03') and currency='"+"?aCurrency?"+"' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("aOperationNo",aOperationNo);
		sqlbv3.put("aOperationType",aOperationType);
		sqlbv3.put("aCurrency",aCurrency);
		tFICustomerAccTraceSet = tFICustomerAccTraceDB.executeQuery(sqlbv3);

		FICustomerAccDB tFICustomerAccDB = new FICustomerAccDB();
		FICustomerAccSet tFICustomerAccSet = new FICustomerAccSet();
		if (tFICustomerAccTraceSet.size() > 0)
		{
			tFICustomerAccDB.setCustomerNo(tFICustomerAccTraceSet.get(1).getCustomerNo());
			tFICustomerAccDB.setAccType(aAccType);
			tFICustomerAccDB.setCurrency(aCurrency);
			tFICustomerAccSet = tFICustomerAccDB.query();
		}
		return tFICustomerAccSet;
	}
	///////////////////////////////////////////////////////////////////////
	/**
	 * 查询客户账户状态 OperationType 1、 新单 2、 续期 3、 保全 4、 赔案 5、 领取 aState 00 -- 未使用--
	 * 收费 01 -- 已使用-- 核销 02 -- 已领取 03 -- 挂起
	 */
	public final boolean updateAccountState(String aOperationNo, String aOperationType, String aState)
	{
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		tFICustomerAccTraceDB.setOperationNo(aOperationNo);
		tFICustomerAccTraceDB.setOperationType(aOperationType);
		tFICustomerAccTraceDB.setState("00");
		FICustomerAccTraceSet tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
		if (tFICustomerAccTraceSet.size() > 0)
		{
			for (int i = 1; i <= tFICustomerAccTraceSet.size(); i++)
			{
				tFICustomerAccTraceSet.get(i).setState(aState);
			}
		}
		if (tFICustomerAccTraceSet.size() > 0)
		{
			mMap.put(tFICustomerAccTraceSet, "UPDATE");
		}
		return true;
	}

	public final boolean updateAccountState(String aOperationNo,String aCurrency, String aOperationType, String aState)
	{
		FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
		tFICustomerAccTraceDB.setOperationNo(aOperationNo);
		tFICustomerAccTraceDB.setOperationType(aOperationType);
		tFICustomerAccTraceDB.setCurrency(aCurrency);
		tFICustomerAccTraceDB.setState("00");
		FICustomerAccTraceSet tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
		if (tFICustomerAccTraceSet.size() > 0)
		{
			for (int i = 1; i <= tFICustomerAccTraceSet.size(); i++)
			{
				tFICustomerAccTraceSet.get(i).setState(aState);
			}
		}
		if (tFICustomerAccTraceSet.size() > 0)
		{
			mMap.put(tFICustomerAccTraceSet, "UPDATE");
		}
		return true;
	}
	
	public MMap getMMap()
	{
		return mMap;
	}

	protected void buildError(String moduleName, String szFunc, String szErrMsg)
	{
		CError cError = new CError();

		cError.moduleName = moduleName;
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/** 合并客户账户 */
	public boolean combineAccount()
	{
		return true;
	}
}
