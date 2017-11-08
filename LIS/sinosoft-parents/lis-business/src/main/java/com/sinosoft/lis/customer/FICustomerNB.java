package com.sinosoft.lis.customer;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICustomerAccTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.FICustomerAccSchema;
import com.sinosoft.lis.schema.FICustomerAccTraceSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.lis.vschema.FICustomerAccTraceSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FICustomerNB extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerNB.class);



	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();


	private GlobalInput globalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();
	private Hashtable mShouldHashtable = new Hashtable();
	private TransferData mTransferData = new TransferData();
	private String mNbflag = "";

	protected boolean getInputData(VData cInputData)
	{
		this.globalInput = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mLJTempFeeSet.set((LJTempFeeSet) cInputData.getObjectByObjectName("LJTempFeeSet", 0));
		this.mLJTempFeeClassSet.set((LJTempFeeClassSet) cInputData.getObjectByObjectName("LJTempFeeClassSet", 0));
		mLCContSchema = (LCContSchema)cInputData.getObjectByObjectName("LCContSchema", 0);
		mShouldHashtable = (Hashtable)cInputData.getObjectByObjectName("Hashtable", 0);
		mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		
		return true;
	}

	protected boolean dealData()
	{
		try
		{
			String aOperationNo = mLCContSchema.getPrtNo();
			String aOperationType = "1";
			String aAccType = "0";// 普通账户
			String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
			String tempNo = "TS" + PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
			//缺少溢交退费情况下对账户的处理
			String mContNo = (String)mTransferData.getValueByName("ContNo");
			//?????????????????????
			Enumeration eKey=mShouldHashtable.keys(); 
			while(eKey.hasMoreElements()) 
			{ 
				//分币种核销
				String key=(String)eKey.nextElement();
				double tValue = (Double)mShouldHashtable.get(key);
				String[] keys = key.split(":");
				String tCurrency = keys[1];
				String tYJFlag = keys[0];
				
				logger.debug("tYJFlag:"+tYJFlag+":tCurrency:"+tCurrency+":tValue:"+tValue);
				// 客户账户表减去核销金额
				FICustomerAccSet tFICustomerAccSet = queryAccountAcc(aAccType, aOperationNo, aOperationType,tCurrency);
				
				FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
				if (tFICustomerAccSet.size() > 0)
				{
					
					tFICustomerAccSchema = tFICustomerAccSet.get(1);
					tFICustomerAccSchema.setSummoney(PubFun.round((tFICustomerAccSchema.getSummoney() - tValue),2));
					tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
					tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());
					//super.mMap.put(tFICustomerAccSchema, "UPDATE");
				}
				// 客户账户轨迹表按照金额从小到大核销金额
				double useedmoney = 0.0;
				FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
				FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
				String tSql = "select * from FICustomerAccTrace where OperationNo = '" + "?aOperationNo?"
						+ "' and operationtype = '" + "?aOperationType?" + "' and currency='"+"?tCurrency?"+"' and state in ('00','03') order by money";
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
						//需要做换号处理?
						//tFICustomerAccTraceSet.get(i).setContNo(this.mLCContSchema.getContNo());
						if (useedmoney < tValue)
						{
							FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
							tFICustomerAccTraceSchema = tFICustomerAccTraceSet.get(i);
							tFICustomerAccTraceSchema.setConfDate(PubFun.getCurrentDate());
							tFICustomerAccTraceSchema.setConfTime(PubFun.getCurrentTime());
							tFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
							tFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
							tFICustomerAccTraceSchema.setState("01");// 核销
							//更新合同号
							tFICustomerAccTraceSchema.setContNo(mContNo);
							
							super.mMap.put(tFICustomerAccTraceSchema, "UPDATE");
							FICustomerAccTraceSchema cFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
							String Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
							cFICustomerAccTraceSchema = (FICustomerAccTraceSchema) tFICustomerAccTraceSchema.clone();
							cFICustomerAccTraceSchema.setSequence(Sequence);
							cFICustomerAccTraceSchema.setDCFlag("C");
							cFICustomerAccTraceSchema.setOperType("4");// 业务使用
							if (useedmoney + cFICustomerAccTraceSchema.getMoney() > tValue)
							{
								cFICustomerAccTraceSchema.setMoney(PubFun.round((tValue - useedmoney),2));
								FICustomerAccTraceSchema ccFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
								Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
								ccFICustomerAccTraceSchema = (FICustomerAccTraceSchema) cFICustomerAccTraceSchema.clone();
								ccFICustomerAccTraceSchema.setSequence(Sequence);
								ccFICustomerAccTraceSchema.setMoney(PubFun.round((useedmoney
										+ tFICustomerAccTraceSchema.getMoney() - tValue),2));
								ccFICustomerAccTraceSchema.setOtherNo(tempNo);
								ccFICustomerAccTraceSchema.setOperType("7");// 重新指定
								//更新合同号
								ccFICustomerAccTraceSchema.setContNo(mContNo);
								
								ccFICustomerAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
								ccFICustomerAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
								ccFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
								ccFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
								super.mMap.put(ccFICustomerAccTraceSchema, "INSERT");
								// 领取时再到页面操作，这里只后台生成客户账户领取类型
								// if (mOperationType.equals("5"))
								// {
								// // 生成应付数据
								// }
								// else
								// {
								FICustomerAccTraceSchema dFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
								Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
								dFICustomerAccTraceSchema = (FICustomerAccTraceSchema) ccFICustomerAccTraceSchema.clone();
								dFICustomerAccTraceSchema.setSequence(Sequence);
								//dFICustomerAccTraceSchema.setOperationType("1");
								dFICustomerAccTraceSchema.setOperationType("2"); //给 续期使用
								dFICustomerAccTraceSchema.setDCFlag("D");
								
								//做余额的不核销
								if(tYJFlag.equals("2"))
								{
									dFICustomerAccTraceSchema.setState("00");
									dFICustomerAccTraceSchema.setConfDate("");
									dFICustomerAccTraceSchema.setConfTime("");
								}
								else
								{
									//做溢缴退费的直接核销掉
									dFICustomerAccTraceSchema.setState("01");
									dFICustomerAccTraceSchema.setConfDate(PubFun.getCurrentDate());
									dFICustomerAccTraceSchema.setConfTime(PubFun.getCurrentTime());
									dFICustomerAccTraceSchema.setOperType("5");//退费
									tFICustomerAccSchema.setSummoney(PubFun.round((tFICustomerAccSchema.getSummoney() - dFICustomerAccTraceSchema.getMoney()),2));
								}
								//设置为合同号
								dFICustomerAccTraceSchema.setOperationNo(mContNo);
								//更新合同号
								dFICustomerAccTraceSchema.setContNo(mContNo);
								
								dFICustomerAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
								dFICustomerAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
								dFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
								dFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
								super.mMap.put(dFICustomerAccTraceSchema, "INSERT");
								// }
							}
							cFICustomerAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
							cFICustomerAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
							cFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
							cFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
							super.mMap.put(cFICustomerAccTraceSchema, "INSERT");
							useedmoney += tFICustomerAccTraceSchema.getMoney();
						}
						
						//如果直接退费
						else if(tValue==0)
						{
							FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
							tFICustomerAccTraceSchema = tFICustomerAccTraceSet.get(i);
							tFICustomerAccTraceSchema.setConfDate(PubFun.getCurrentDate());
							tFICustomerAccTraceSchema.setConfTime(PubFun.getCurrentTime());
							tFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
							tFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
							tFICustomerAccTraceSchema.setState("01");// 核销
							super.mMap.put(tFICustomerAccTraceSchema, "UPDATE");
							FICustomerAccTraceSchema cFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
							String Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
							cFICustomerAccTraceSchema = (FICustomerAccTraceSchema) tFICustomerAccTraceSchema.clone();
							cFICustomerAccTraceSchema.setSequence(Sequence);
							cFICustomerAccTraceSchema.setDCFlag("C");
							cFICustomerAccTraceSchema.setOperType("5");//退费
							
							cFICustomerAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
							cFICustomerAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
							cFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
							cFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
							super.mMap.put(cFICustomerAccTraceSchema, "INSERT");
							
							tFICustomerAccSchema.setSummoney(PubFun.round((tFICustomerAccSchema.getSummoney() - cFICustomerAccTraceSchema.getMoney()),2));

						}
					}
				}
				super.mMap.put(tFICustomerAccSchema, "UPDATE");
			}
		}
		catch (Exception ex)
		{
			buildError("FICustomerFI", "dealdata", "");
			return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	// TODO 自动生成方法存根

	}

}
