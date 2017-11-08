package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICustomerAccDB;
import com.sinosoft.lis.db.FICustomerAccTraceDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FICustomerAccSchema;
import com.sinosoft.lis.schema.FICustomerAccTraceBakeSchema;
import com.sinosoft.lis.schema.FICustomerAccTraceSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.lis.vschema.FICustomerAccTraceSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class FICustomerHB extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerHB.class);

	private FICustomerAccSchema mFICustomerAccSchema = new FICustomerAccSchema();
	private FICustomerAccSchema mFICustomerAccSchemanew = new FICustomerAccSchema();

	public boolean getInputData(VData cInputData)
	{
		this.mFICustomerAccSchema = ((FICustomerAccSchema) cInputData.getObjectByObjectName("FICustomerAccSchema", 0));
		this.mFICustomerAccSchemanew = ((FICustomerAccSchema) cInputData.getObjectByObjectName("FICustomerAccSchema", 1));
		return true;
	}

	@Override
	protected boolean dealData()
	{
		try
		{
			//增加校验,对于合并前后两个账户编码一样的,不做合并
			if(mFICustomerAccSchema.getInsuAccNo().equals(mFICustomerAccSchemanew.getInsuAccNo()))
			{
				buildError("FICustomerHB", "dealdata", "客户账户相同,不需要做合并");
				return false;
			}
			FICustomerAccDB tFICustomerAccDB = new FICustomerAccDB();
			FICustomerAccSet tFICustomerAccSet = new FICustomerAccSet();
			FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
			tFICustomerAccDB.setInsuAccNo(mFICustomerAccSchema.getInsuAccNo());
			tFICustomerAccSet = tFICustomerAccDB.query();
			tFICustomerAccSchema = tFICustomerAccSet.get(1);
			super.mMap.put(tFICustomerAccSchema, "DELETE");
			
			FICustomerAccDB tFICustomerAccDBnew = new FICustomerAccDB();
			FICustomerAccSet tFICustomerAccSetnew = new FICustomerAccSet();
			FICustomerAccSchema tFICustomerAccSchemanew = new FICustomerAccSchema();
			tFICustomerAccDBnew.setInsuAccNo(mFICustomerAccSchemanew.getInsuAccNo());
			tFICustomerAccSetnew = tFICustomerAccDBnew.query();
			tFICustomerAccSchemanew = tFICustomerAccSetnew.get(1);
			tFICustomerAccSchemanew.setSummoney(tFICustomerAccSchemanew.getSummoney()+tFICustomerAccSchema.getSummoney());
			super.mMap.put(tFICustomerAccSchemanew, "DELETE&INSERT");
			
			//校验币种是否一致,不一致,不允许合并
			if(!tFICustomerAccSchema.getCurrency().equals(tFICustomerAccSchemanew.getCurrency()))
			{
				buildError("FICustomerHB", "dealdata", "客户账户币种不同,不允许做合并");
				return false;
			}
			
					FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
					FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
					FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
					tFICustomerAccTraceDB.setInsuAccNo(mFICustomerAccSchema.getInsuAccNo());
					tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
					if (tFICustomerAccTraceSet.size() > 0)
					{
						for (int i = 1; i <= tFICustomerAccTraceSet.size(); i++)
						{
							tFICustomerAccTraceSchema = tFICustomerAccTraceSet.get(i);			
							super.mMap.put(tFICustomerAccTraceSchema, "DELETE");
							FICustomerAccTraceSchema dFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
							dFICustomerAccTraceSchema = (FICustomerAccTraceSchema) tFICustomerAccTraceSchema.clone();
							dFICustomerAccTraceSchema.setInsuAccNo(mFICustomerAccSchemanew.getInsuAccNo());
							dFICustomerAccTraceSchema.setCustomerNo(mFICustomerAccSchemanew.getCustomerNo());
							super.mMap.put(dFICustomerAccTraceSchema, "INSERT");
						}
					}

				
			VData mInputData = new VData();
			mInputData.add(mMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, ""))
			{
				if (tPubSubmit.mErrors.needDealError())
				{
					// @@错误处理
					buildError("FICustomerZD", "PubSubmit", "");
					return false;
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

}
