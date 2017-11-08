package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.finfee.LJAGetQueryUI;
import com.sinosoft.lis.finfee.OperFinFeeGetBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FICustomerDE extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerDE.class);


	private TransferData mTransferData = new TransferData();

	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private FICustomerAccTraceSet mFICustomerAccTraceSet = new FICustomerAccTraceSet();

	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();

	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();

	private GlobalInput globalInput = new GlobalInput();

	private String mNbflag = "";

	protected boolean getInputData(VData cInputData)
	{
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		this.globalInput = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mLJTempFeeSet.set((LJTempFeeSet) cInputData.getObjectByObjectName("LJTempFeeSet", 0));
		this.mLJTempFeeClassSet.set((LJTempFeeClassSet) cInputData.getObjectByObjectName("LJTempFeeClassSet", 0));
		this.mLJAGetSchema = ((LJAGetSchema) cInputData.getObjectByObjectName("LJAGetSchema", 0));

		return true;
	}

	protected boolean dealData()
	{
		try
		{
			for (int k = 1; k <= mLJTempFeeSet.size(); k++)
			{

				for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
				{
					if (mLJTempFeeSet.get(k).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
							&& mLJTempFeeSet.get(k).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo()))
					{
						try
						{
							double sumMoney = 0.0;
							String aAccType = "0";// 普通账户
							FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
							tFICustomerAccTraceDB.setOtherNo(mLJTempFeeClassSet.get(j).getTempFeeNo());
							mFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
							if (mFICustomerAccTraceSet.size() > 0)
							{
								for (int i = 1; i <= mFICustomerAccTraceSet.size(); i++)
								{
									FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
									tFICustomerAccTraceSchema = mFICustomerAccTraceSet.get(i);

									Reflections tRef = new Reflections();
									FICustomerAccTraceBakeSchema tFICustomerAccTraceBakeSchema = new FICustomerAccTraceBakeSchema();
									tRef.transFields(tFICustomerAccTraceBakeSchema, tFICustomerAccTraceSchema);
									// 删除备份到bake表
									super.mMap.put(tFICustomerAccTraceSchema, "DELETE");
									super.mMap.put(tFICustomerAccTraceBakeSchema, "INSERT");

									sumMoney += tFICustomerAccTraceSchema.getMoney();

								}
							}

							// 客户账户表减去领取金额
							FICustomerAccSet tFICustomerAccSet = queryAccountAcc(aAccType, mFICustomerAccTraceSet.get(1).getOperationNo(), mFICustomerAccTraceSet.get(1).getOperationType());
							if (tFICustomerAccSet.size() > 0)
							{
								FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
								tFICustomerAccSchema = tFICustomerAccSet.get(1);
								tFICustomerAccSchema.setSummoney(tFICustomerAccSet.get(1).getSummoney() - sumMoney);
								tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
								tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());
								super.mMap.put(tFICustomerAccSchema, "UPDATE");
							}
						}
						catch (Exception ex)
						{
							buildError("FICustomerDE", "dealdata", "");
							return false;
						}
					}
				}
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
