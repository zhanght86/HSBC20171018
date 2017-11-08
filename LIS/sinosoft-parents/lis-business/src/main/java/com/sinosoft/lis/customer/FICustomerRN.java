package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.FICustomerAccTraceDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.FICustomerAccSchema;
import com.sinosoft.lis.schema.FICustomerAccTraceSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.lis.vschema.FICustomerAccTraceSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FICustomerRN extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerRN.class);


	private TransferData mTransferData = new TransferData();

	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();

	private String mOperationType = "";

	private double mSumDuePayMoney = 0.0;

	private String mCurrency = ""; 
	private String tempNo = "";

	public boolean getInputData(VData cInputData)
	{
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		this.mLJTempFeeSet.set((LJTempFeeSet) cInputData.getObjectByObjectName("LJTempFeeSet", 0));
		this.mLJTempFeeClassSet.set((LJTempFeeClassSet) cInputData.getObjectByObjectName("LJTempFeeClassSet", 0));
		this.mOperationType = String.valueOf(mTransferData.getValueByName("OperationType"));
		this.mSumDuePayMoney = (Double) ((mTransferData.getValueByName("SumDuePayMoney")));
		return true;
	}

	protected boolean dealData()
	{
		try
		{
			DecimalFormat df = new DecimalFormat("0.00");
			//需要获取币种
			this.mCurrency = (String)mTransferData.getValueByName("Currency");
			double sumMoney = 0.0;
			String aAccType = "0";// 普通账户
			String aOperationNo = "";
			String aOperationType = mOperationType;
			// 根据不同的业务类型获取合同号
			// 新单交费
			if ("1".equals(mLJTempFeeSet.get(1).getTempFeeType()))
			{
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setPrtNo(mLJTempFeeSet.get(1).getOtherNo());
				LCContSet tLCContSet = tLCContDB.query();
				LCContSchema tLCContSchema = tLCContSet.get(1);
				if (tLCContSet == null || tLCContSet.size() < 1)
				{
					return false;
				}
				else
				{
					aOperationNo = tLCContSchema.getContNo();
					aOperationType = "1";
				}
			}
			// 续期催收交费//预交续期保费
			else if ("2".equals(mLJTempFeeSet.get(1).getTempFeeType())
					|| "3".equals(mLJTempFeeSet.get(1).getTempFeeType()))
			{
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLJTempFeeSet.get(1).getOtherNo());
				LCContSet tLCContSet = tLCContDB.query();
				LCContSchema tLCContSchema = tLCContSet.get(1);
				if (tLCContSet == null || tLCContSet.size() < 1)
				{
					return false;
				}
				else
				{
					aOperationNo = tLCContSchema.getContNo();
					aOperationType = "2";
				}
			}
			// 保全交费
			else if ("4".equals(mLJTempFeeSet.get(1).getTempFeeType()))
			{
				LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
				tLPEdorItemDB.setEdorAcceptNo(mLJTempFeeSet.get(1).getOtherNo());
				LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
				//LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(1);
				
				LPGrpEdorItemSet tLPGrpEdorItemSet=new LPGrpEdorItemSet();
				LPGrpEdorItemDB tLPGrpEdorItemDB= new LPGrpEdorItemDB();
				tLPGrpEdorItemDB.setEdorAcceptNo(mLJTempFeeSet.get(1).getOtherNo());
				tLPGrpEdorItemSet=tLPGrpEdorItemDB.query();
				
				if ((tLPEdorItemSet == null || tLPEdorItemSet.size() < 1)&&(tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1))
				{
					
						return false;
				
				}
				else
				{
					aOperationNo = mLJTempFeeSet.get(1).getOtherNo();
					aOperationType = "3";
				}
				
			}
			// 应付转入或者溢交的时候，取mOperationType
			else if ("7".equals(mLJTempFeeSet.get(1).getTempFeeType())
					|| "8".equals(mLJTempFeeSet.get(1).getTempFeeType()))
			{
				aOperationNo = mLJTempFeeSet.get(1).getOtherNo();
				aOperationType = mOperationType;
			}
			// 理赔收费
			// if ("5".equals(mLJTempFeeSet.get(1).getTempFeeType()))
			// {}
			else
			{
				//理赔是4
				aOperationNo = mLJTempFeeSet.get(1).getOtherNo();
				aOperationType = "4";
			}

			for (int i = 1; i <= mLJTempFeeSet.size(); i++)
			{
				if(mLJTempFeeSet.get(i).getCurrency().equals(this.mCurrency))
				{
					sumMoney += mLJTempFeeSet.get(i).getPayMoney();
				}
			}
			
			
			if(mOperationType.equals("2"))
			{
				//余额使用,需要模拟生成暂收据
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = (LJTempFeeSchema) mLJTempFeeSet.get(1).clone();
				String tLimit = PubFun.getNoLimit(tLJTempFeeSchema.getManageCom());
				tempNo = "TS" + PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
				tLJTempFeeSchema.setTempFeeNo(tempNo);// 临时生成暂交费号
				tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
				String tempfeetype = "10";//余额使用
				
				double tYEShouleUseMoney = (Double)this.mTransferData.getValueByName("YEShouleUseMoney");
				String tGetNoticeNo = (String)this.mTransferData.getValueByName("GetNoticeNo");
				
				tLJTempFeeSchema.setTempFeeType(tempfeetype);// 交费余额
				tLJTempFeeSchema.setPayMoney(PubFun.round(tYEShouleUseMoney,2));
				tLJTempFeeSchema.setOtherNoType("0");// 交费对应的个单合同号
				tLJTempFeeSchema.setOtherNo(aOperationNo);
				tLJTempFeeSchema.setConfDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setConfFlag("1");
				tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
				tLJTempFeeSchema.setCurrency(mCurrency);

				super.mMap.put(tLJTempFeeSchema, "INSERT");
				// 余额重新生成ljtempfeeclass
				LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tLJTempFeeClassSchema = (LJTempFeeClassSchema) mLJTempFeeClassSet.get(1).clone();
				tLJTempFeeClassSchema.setTempFeeNo(tempNo);// 临时生成暂交费号
				tLJTempFeeClassSchema.setPayMoney(tYEShouleUseMoney);
				tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
				tLJTempFeeClassSchema.setOtherNoType(mOperationType);// 重新指定用途
				tLJTempFeeClassSchema.setConfFlag("1");
				tLJTempFeeClassSchema.setConfDate(PubFun.getCurrentDate());
				tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
				tLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
				tLJTempFeeClassSchema.setPayMoney(PubFun.round(tYEShouleUseMoney,2));
				tLJTempFeeClassSchema.setOtherNo(tGetNoticeNo); //其他号码记录缴费号
				tLJTempFeeClassSchema.setPayMode("M");
				tLJTempFeeClassSchema.setCurrency(mCurrency);
				super.mMap.put(tLJTempFeeClassSchema, "INSERT");
			}
			
			// 余额重新生成ljtempfee ?
			//对于续期,不会有这种情况
			if (sumMoney > mSumDuePayMoney)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = (LJTempFeeSchema) mLJTempFeeSet.get(1).clone();
				String tLimit = PubFun.getNoLimit(tLJTempFeeSchema.getManageCom());
				tempNo = "TS" + PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
				tLJTempFeeSchema.setTempFeeNo(tempNo);// 临时生成暂交费号
				tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
				String tempfeetype = mOperationType;
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
					tempfeetype = "5";
				}
				tLJTempFeeSchema.setTempFeeType(tempfeetype);// 交费余额
				tLJTempFeeSchema.setOtherNoType(mOperationType);// 重新指定用途
				tLJTempFeeSchema.setConfDate("");
				tLJTempFeeSchema.setConfFlag("0");

				tLJTempFeeSchema.setPayMoney(df.format(sumMoney - mSumDuePayMoney));

				super.mMap.put(tLJTempFeeSchema, "INSERT");
				// 余额重新生成ljtempfeeclass
				LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tLJTempFeeClassSchema = (LJTempFeeClassSchema) mLJTempFeeClassSet.get(1).clone();
				tLJTempFeeClassSchema.setTempFeeNo(tempNo);// 临时生成暂交费号
				tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
				tLJTempFeeClassSchema.setOtherNoType(mOperationType);// 重新指定用途
				tLJTempFeeClassSchema.setConfDate("");
				tLJTempFeeClassSchema.setConfFlag("0");
				tLJTempFeeClassSchema.setPayMoney(df.format(sumMoney - mSumDuePayMoney));
				tLJTempFeeClassSchema.setPayMode("M");
				tLJTempFeeClassSchema.setCurrency(mCurrency);
				super.mMap.put(tLJTempFeeClassSchema, "INSERT");
			}

			// 客户账户表减去核销金额
			//需要 按照币种获取客户账户
			FICustomerAccSet tFICustomerAccSet = queryAccountAcc(aAccType, aOperationNo, aOperationType,this.mCurrency);
			if (tFICustomerAccSet.size() > 0)
			{
				FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
				tFICustomerAccSchema = tFICustomerAccSet.get(1);
				tFICustomerAccSchema.setSummoney(PubFun.round((tFICustomerAccSet.get(1).getSummoney() - mSumDuePayMoney),2));
				tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
				tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());
				super.mMap.put(tFICustomerAccSchema, "UPDATE");
			}
			// 客户账户轨迹表按照金额从小到大核销金额
			double useedmoney = 0.0;
			FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
			FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
			String tSql = "select * from FICustomerAccTrace where OperationNo = '" + "?aOperationNo?"
					+ "' and operationtype = '" + "?aOperationType?" + "' and state in ('00','03') "
					+ " and currency = '"+"?currency?"+"' "
					+ " order by money ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("aOperationNo",aOperationNo);
			sqlbv1.put("aOperationType",aOperationType);
			sqlbv1.put("currency",this.mCurrency);
			tFICustomerAccTraceSet = tFICustomerAccTraceDB.executeQuery(sqlbv1);
			if (tFICustomerAccTraceSet.size() > 0)
			{
				for (int i = 1; i <= tFICustomerAccTraceSet.size(); i++)
				{
					if (useedmoney < mSumDuePayMoney)
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
						cFICustomerAccTraceSchema.setOperType("4");// 业务使用
						if (useedmoney + cFICustomerAccTraceSchema.getMoney() > mSumDuePayMoney)
						{
							cFICustomerAccTraceSchema.setMoney(df.format(mSumDuePayMoney - useedmoney));
							FICustomerAccTraceSchema ccFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
							Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
							ccFICustomerAccTraceSchema = (FICustomerAccTraceSchema) cFICustomerAccTraceSchema.clone();
							ccFICustomerAccTraceSchema.setSequence(Sequence);
							ccFICustomerAccTraceSchema.setMoney(df.format(useedmoney
									+ tFICustomerAccTraceSchema.getMoney() - mSumDuePayMoney));
							ccFICustomerAccTraceSchema.setOtherNo(tempNo);
							ccFICustomerAccTraceSchema.setOperType("7");// 重新指定
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
							dFICustomerAccTraceSchema.setOperationType(mOperationType);
							dFICustomerAccTraceSchema.setDCFlag("D");
							dFICustomerAccTraceSchema.setState("00");
							dFICustomerAccTraceSchema.setConfDate("");
							dFICustomerAccTraceSchema.setConfTime("");
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
	// FICustomerAccTraceSet tFICustomerAccTraceSet = new
	// FICustomerAccTraceSet();
	// FICustomerAccTraceDB tFICustomerAccTraceDB = new FICustomerAccTraceDB();
	// String tSql = "select * from FICustomerAccTrace where contno =
	// '781000003181' and operationtype = '2' and state = '03' order by money";
	// tFICustomerAccTraceSet = tFICustomerAccTraceDB.executeQuery(tSql);

	}

}
