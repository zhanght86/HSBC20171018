package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICustomerAccTraceDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FICustomerAccTraceBakeSchema;
import com.sinosoft.lis.schema.FICustomerAccTraceSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.FICustomerAccTraceSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class FICustomerZD extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerZD.class);

	private FICustomerAccTraceSet mFICustomerAccTraceSet = new FICustomerAccTraceSet();

	public boolean getInputData(VData cInputData)
	{
		this.mFICustomerAccTraceSet.set((FICustomerAccTraceSet) cInputData.getObjectByObjectName("FICustomerAccTraceSet", 0));
		return true;
	}

	@Override
	protected boolean dealData()
	{
		try
		{
			if (mFICustomerAccTraceSet.size() > 0)
			{
				for (int i = 1; i <= mFICustomerAccTraceSet.size(); i++)
				{
					FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
					FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
					FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
					tFICustomerAccTraceDB.setSequence(mFICustomerAccTraceSet.get(i).getSequence());
					tFICustomerAccTraceSet = tFICustomerAccTraceDB.query();
					tFICustomerAccTraceSchema = tFICustomerAccTraceSet.get(1);

					Reflections tRef = new Reflections();
					FICustomerAccTraceBakeSchema tFICustomerAccTraceBakeSchema = new FICustomerAccTraceBakeSchema();
					tRef.transFields(tFICustomerAccTraceBakeSchema, tFICustomerAccTraceSchema);
					// 删除备份到bake表
					super.mMap.put(tFICustomerAccTraceSchema, "DELETE");
					super.mMap.put(tFICustomerAccTraceBakeSchema, "INSERT");

					FICustomerAccTraceBakeSchema dFICustomerAccTraceBakeSchema = new FICustomerAccTraceBakeSchema();
					String Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
					dFICustomerAccTraceBakeSchema = (FICustomerAccTraceBakeSchema) tFICustomerAccTraceBakeSchema.clone();
					dFICustomerAccTraceBakeSchema.setSequence(Sequence);
					dFICustomerAccTraceBakeSchema.setDCFlag("C");
					// 备份表生成核销数据
					super.mMap.put(dFICustomerAccTraceBakeSchema, "INSERT");

					

					LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
					LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
					LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
					tLJTempFeeDB.setTempFeeNo(tFICustomerAccTraceSchema.getOtherNo());
					tLJTempFeeDB.setCurrency(tFICustomerAccTraceSchema.getCurrency());
					
					tLJTempFeeSet = tLJTempFeeDB.query();
					String tLimit = "";
					String tempNo = "";
					if (tLJTempFeeSet.size() > 0)
					{
						tLJTempFeeSchema = tLJTempFeeSet.get(1);
						tLimit = PubFun.getNoLimit(tLJTempFeeSchema.getManageCom());
						tempNo = "TS" + PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
						
						
						LJTempFeeSchema dLJTempFeeSchema = (LJTempFeeSchema)tLJTempFeeSchema.clone();
						dLJTempFeeSchema.setTempFeeNo(tempNo);
						dLJTempFeeSchema.setOtherNo(mFICustomerAccTraceSet.get(i).getOperationNo());
						dLJTempFeeSchema.setOtherNoType(mFICustomerAccTraceSet.get(i).getOperationType());
						String tempfeetype = mFICustomerAccTraceSet.get(i).getOperationType();
						if (tempfeetype.equals("2"))
						{
							tempfeetype = "3";
						}
						else if (tempfeetype.equals("3"))
						{
							tempfeetype = "4";
						}
						else if (tempfeetype.equals("4"))
						{
							tempfeetype = "6";
						}
						dLJTempFeeSchema.setTempFeeType(tempfeetype);
						super.mMap.put(dLJTempFeeSchema, "INSERT");
						
						tLJTempFeeSchema.setConfDate(PubFun.getCurrentDate());
						tLJTempFeeSchema.setConfFlag("1");
						super.mMap.put(tLJTempFeeSchema, "UPDATE");
					}

					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
					LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
					tLJTempFeeClassDB.setTempFeeNo(tFICustomerAccTraceSchema.getOtherNo());
					tLJTempFeeClassDB.setCurrency(tFICustomerAccTraceSchema.getCurrency());
					tLJTempFeeClassSet = tLJTempFeeClassDB.query();
					if (tLJTempFeeClassSet.size() > 0)
					{
						tLJTempFeeClassSchema = tLJTempFeeClassSet.get(1);
						
						
						LJTempFeeClassSchema dLJTempFeeClassSchema = (LJTempFeeClassSchema)tLJTempFeeClassSchema.clone();
						dLJTempFeeClassSchema.setTempFeeNo(tempNo);
						dLJTempFeeClassSchema.setOtherNo(mFICustomerAccTraceSet.get(i).getOperationNo());
						dLJTempFeeClassSchema.setOtherNoType(mFICustomerAccTraceSet.get(i).getOperationType());
						super.mMap.put(dLJTempFeeClassSchema, "INSERT");
						
						tLJTempFeeClassSchema.setConfDate(PubFun.getCurrentDate());
						tLJTempFeeClassSchema.setConfFlag("1");
						super.mMap.put(tLJTempFeeClassSchema, "UPDATE");
					}
					
					FICustomerAccTraceSchema dFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
					Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
					dFICustomerAccTraceSchema = (FICustomerAccTraceSchema) tFICustomerAccTraceSchema.clone();
					dFICustomerAccTraceSchema.setSequence(Sequence);
					dFICustomerAccTraceSchema.setOtherNo(tempNo);
					dFICustomerAccTraceSchema.setOperationNo(mFICustomerAccTraceSet.get(i).getOperationNo());
					dFICustomerAccTraceSchema.setOperationType(mFICustomerAccTraceSet.get(i).getOperationType());
					// 生成重新指定数据
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
