package com.sinosoft.lis.customer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICustomerAccTraceDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FICustomerAccSchema;
import com.sinosoft.lis.schema.FICustomerAccTraceSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.lis.vschema.FICustomerAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.VData;

public class FICustomerLQ extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerLQ.class);

	private VData mInputData = new VData();
	
	private FICustomerAccTraceSet mFICustomerAccTraceSet = new FICustomerAccTraceSet();

	private String mCurrentDate = "";

	private String mCurrentTime = "";

	public boolean getInputData(VData cInputData)
	{
		this.mFICustomerAccTraceSet.set((FICustomerAccTraceSet) cInputData.getObjectByObjectName("FICustomerAccTraceSet", 0));
		mCurrentDate = PubFun.getCurrentDate();
		mCurrentTime = PubFun.getCurrentTime();
		return true;
	}

	protected boolean dealData()
	{
		try
		{
			double sumMoney = 0.0;
			String aAccType = "0";// 普通账户
			Map contMap = new HashMap();
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
					
					if(!contMap.containsKey(tFICustomerAccTraceSchema.getContNo())){
						LCPolSet lcPolSet = new LCPolSet();
						LCPolDB lcpolDB = new LCPolDB();
						lcpolDB.setContNo(tFICustomerAccTraceSchema.getContNo());
						lcPolSet = lcpolDB.query();
						if(lcPolSet.size()>0){
							for(int n=1;n<=lcPolSet.size();n++){
								//余额领取要清空leavingmoney
								lcPolSet.get(i).setLeavingMoney(0);
							}
							
							super.mMap.put(lcPolSet, "UPDATE");
						}
						contMap.put(tFICustomerAccTraceSchema.getContNo(), tFICustomerAccTraceSchema.getContNo());
					}
					
					tFICustomerAccTraceSchema.setConfDate(PubFun.getCurrentDate());
					tFICustomerAccTraceSchema.setConfTime(PubFun.getCurrentTime());
					tFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
					tFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
					tFICustomerAccTraceSchema.setState("02");// 领取
					tFICustomerAccTraceSchema.setOperType("5");// 退费
					super.mMap.put(tFICustomerAccTraceSchema, "UPDATE");
					FICustomerAccTraceSchema cFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
					String Sequence = PubFun1.CreateMaxNo("FISEQUENCE", "66");
					cFICustomerAccTraceSchema = (FICustomerAccTraceSchema) tFICustomerAccTraceSchema.clone();
					cFICustomerAccTraceSchema.setSequence(Sequence);
					cFICustomerAccTraceSchema.setDCFlag("C");
					cFICustomerAccTraceSchema.setOperType("5");// 退费
					cFICustomerAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
					cFICustomerAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
					cFICustomerAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
					cFICustomerAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
					super.mMap.put(cFICustomerAccTraceSchema, "INSERT");
					sumMoney += tFICustomerAccTraceSchema.getMoney();
					if (tFICustomerAccTraceSchema.getMoney() > 0)
					{
						if (!dealLjaget(tFICustomerAccTraceSchema))
						{
							buildError("FICustomerLQ", "dealdata", "余额领取财务应付表数据生成错误!");
							return false;
						}
					}
					LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
					LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
					LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
					tLJTempFeeDB.setTempFeeNo(tFICustomerAccTraceSchema.getOtherNo());
					tLJTempFeeDB.setCurrency(tFICustomerAccTraceSchema.getCurrency());
					tLJTempFeeDB.setConfFlag("0");
					tLJTempFeeSet = tLJTempFeeDB.query();
					String tLimit = "";
					String tempNo = "";
					if (tLJTempFeeSet.size() > 0)
					{
						tLJTempFeeSchema = tLJTempFeeSet.get(1);
						tLJTempFeeSchema.setConfDate(PubFun.getCurrentDate());
						tLJTempFeeSchema.setConfFlag("1");
						super.mMap.put(tLJTempFeeSchema, "UPDATE");
					}

					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
					LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
					tLJTempFeeClassDB.setTempFeeNo(tFICustomerAccTraceSchema.getOtherNo());
					tLJTempFeeClassDB.setCurrency(tFICustomerAccTraceSchema.getCurrency());
					tLJTempFeeClassDB.setConfFlag("0");
					tLJTempFeeClassSet = tLJTempFeeClassDB.query();
					if (tLJTempFeeClassSet.size() > 0)
					{
						tLJTempFeeClassSchema = tLJTempFeeClassSet.get(1);
						tLJTempFeeClassSchema.setConfDate(PubFun.getCurrentDate());
						tLJTempFeeClassSchema.setConfFlag("1");
						super.mMap.put(tLJTempFeeClassSchema, "UPDATE");
					}
				}
			}

			// 客户账户表减去领取金额
			FICustomerAccSet tFICustomerAccSet = queryAccountAcc(aAccType, mFICustomerAccTraceSet.get(1).getOperationNo(), mFICustomerAccTraceSet.get(1).getOperationType(), mFICustomerAccTraceSet.get(1).getCurrency());
			if (tFICustomerAccSet.size() > 0)
			{
				FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
				tFICustomerAccSchema = tFICustomerAccSet.get(1);
				tFICustomerAccSchema.setSummoney(tFICustomerAccSet.get(1).getSummoney() - sumMoney);
				tFICustomerAccSchema.setModifyDate(PubFun.getCurrentDate());
				tFICustomerAccSchema.setModifyTime(PubFun.getCurrentTime());
				super.mMap.put(tFICustomerAccSchema, "UPDATE");
			}
			
			//余额领取后，需要修改lcpol的leavingmoney
			
		}
		catch (Exception ex)
		{
			buildError("FICustomerLQ", "dealdata", "");
			return false;
		}
		
		mInputData.add(mMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
            if (tPubSubmit.mErrors.needDealError()) {
                // @@错误处理
                buildError("FICustomerLQ","dealdata", "余额领取数据提交时出现错误!");
                return false;
            }
        }
		
		return true;
	}

	public boolean dealLjaget(FICustomerAccTraceSchema tFICustomerAccTraceSchema)
	{
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(tFICustomerAccTraceSchema.getContNo());
		tLCContSchema = tLCContDB.query().get(1);
		tLJAGetSchema = getLJAGet(tLCContSchema, tFICustomerAccTraceSchema.getMoney(), tFICustomerAccTraceSchema.getCurrency());
		if (tLJAGetSchema == null)
		{
			buildError("FICustomerLQ","dealLjaget", "准备溢交退费实付总表失败！");
			return false;
		}
		super.mMap.put(tLJAGetSchema, "INSERT");
		tLJAGetOtherSchema = getLJAGetOther(tLJAGetSchema, tLCContSchema, tFICustomerAccTraceSchema.getContNo(), tFICustomerAccTraceSchema.getCurrency());
		if (tLJAGetOtherSchema == null)
		{
			buildError("FICustomerLQ","dealLjaget", "准备溢交退费其他实付表失败！");
			return false;
		}
		super.mMap.put(tLJAGetOtherSchema, "INSERT");
		return true;
	}

	/**
	 * getLJAGetOther
	 * @param tLJAGetSchema LJAGetSchema
	 * @param tLCContSchema LCContSchema
	 * @return LJAGetOtherSchema
	 */
	private LJAGetOtherSchema getLJAGetOther(LJAGetSchema tLJAGetSchema, LCContSchema tLCContSchema, String newContNo, String tCurrency)
	{
		LJAGetOtherSchema tLJAGetOtherShema = new LJAGetOtherSchema();
		tLJAGetOtherShema.setActuGetNo(tLJAGetSchema.getActuGetNo());
		tLJAGetOtherShema.setOtherNo(tLCContSchema.getPrtNo());
		tLJAGetOtherShema.setOtherNoType("8"); // 其它类型退费(溢交保费退费)
		tLJAGetOtherShema.setPayMode(tLCContSchema.getNewPayMode());
		tLJAGetOtherShema.setGetMoney(tLJAGetSchema.getSumGetMoney());
		tLJAGetOtherShema.setGetDate(mCurrentDate);
		tLJAGetOtherShema.setFeeOperationType("YJ"); // 溢交退费
		tLJAGetOtherShema.setFeeFinaType("TF"); // 溢交退费
		tLJAGetOtherShema.setManageCom(tLCContSchema.getManageCom());
		tLJAGetOtherShema.setAgentCom(tLCContSchema.getAgentCom());
		tLJAGetOtherShema.setAgentType(tLCContSchema.getAgentType());
		tLJAGetOtherShema.setAPPntName(tLCContSchema.getAppntName());
		tLJAGetOtherShema.setAgentGroup(tLCContSchema.getAgentGroup());
		tLJAGetOtherShema.setAgentCode(tLCContSchema.getAgentCode());
		tLJAGetOtherShema.setSerialNo(tLJAGetSchema.getSerialNo());
		tLJAGetOtherShema.setGetNoticeNo(newContNo);
		tLJAGetOtherShema.setOperator("001");
		tLJAGetOtherShema.setMakeTime(mCurrentTime);
		tLJAGetOtherShema.setMakeDate(mCurrentDate);
		tLJAGetOtherShema.setModifyDate(mCurrentDate);
		tLJAGetOtherShema.setModifyTime(mCurrentTime);
		tLJAGetOtherShema.setCurrency(tCurrency);
		if ("2".equals(tLCContSchema.getOutPayFlag()))
		{
			tLJAGetOtherShema.setState("5"); // 溢缴转下期，将状态记入特殊值
		}

		return tLJAGetOtherShema;
	}
	
	private LJAGetSchema getLJAGet(LCContSchema tLCContSchema, double sumPayMoney, String tCurrency)
	{
		String tLimit = PubFun.getNoLimit("86");
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		String getNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生即付通知书号

		// 实付总表
		tLJAGetSchema.setActuGetNo(getNo);
		tLJAGetSchema.setShouldDate(mCurrentDate);
		tLJAGetSchema.setStartGetDate(mCurrentTime);

		/** @todo 加入对于银行代收方式交费的退费 */
		// 由于某些机构只有代收业务而没有代付业务，所以对于这些机构需要进行配置，如果没有代收业务那么需要把这种退费的付费方式变更为现金
		// 如果有代付业务则需要用代付的付款方式 add by HYQ
		// 准备SysVar,解析管理机构信息
		String tSysVar = "ComEFT" + tLCContSchema.getManageCom().substring(2, 4);

		// 将有代收但没代付的机构信息描述在LDSysVar表中，如果有，且SysVarValue='1',则说明该机构没有代收业务
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar(tSysVar);
		String tPayTypeFlag = "";
		if (!tLDSysVarDB.getInfo())
		{
			tPayTypeFlag = "0"; // 该机构有代付业务
		}
		else
		{
			LDSysVarSchema tLDSysVarSchema = tLDSysVarDB.getSchema();
			tPayTypeFlag = tLDSysVarSchema.getSysVarValue();
		}

		if (tPayTypeFlag.equals("0"))
		{ // 该机构是否有代付业务
			tLDSysVarDB.setSysVar(tSysVar);
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setOtherNo(tLCContSchema.getContNo());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
			if (tLJTempFeeClassSet == null || tLJTempFeeClassSet.size() <= 0)
			{
				CError.buildErr(this, "查询暂收费信息失败！");
				return null;
			}

			// 如果组合交费方式，则按现金付费
			int tPayModeFlag = 0;
			for (int i = 1; i <= tLJTempFeeClassSet.size(); i++)
			{
				if (tLJTempFeeClassSet.get(i).getPayMode().equals("7")
						|| tLJTempFeeClassSet.get(i).getPayMode().equals("A"))
				{
					tPayModeFlag++;
				}
			}

			if (tPayModeFlag == tLJTempFeeClassSet.size())
			{
				tLJAGetSchema.setPayMode("4"); // 银行代付
				tLJAGetSchema.setBankAccNo(tLJTempFeeClassSet.get(1).getBankAccNo());
				tLJAGetSchema.setBankCode(tLJTempFeeClassSet.get(1).getBankCode());
				tLJAGetSchema.setAccName(tLJTempFeeClassSet.get(1).getAccName());
				tLJAGetSchema.setDrawer(tLJTempFeeClassSet.get(1).getAccName());
				tLJAGetSchema.setDrawerID(tLJTempFeeClassSet.get(1).getIDNo());
			}
			else
			{
				tLJAGetSchema.setPayMode("1"); // 现金付费
				tLJAGetSchema.setBankAccNo("");
				tLJAGetSchema.setBankCode("");
				tLJAGetSchema.setAccName("");
			}
		}
		else
		{
			tLJAGetSchema.setPayMode("1"); // 现金付费
			tLJAGetSchema.setBankAccNo("");
			tLJAGetSchema.setBankCode("");
			tLJAGetSchema.setAccName("");
		}
		tLJAGetSchema.setOtherNo(tLCContSchema.getPrtNo());
		tLJAGetSchema.setOtherNoType("8"); // 其它类型退费(溢交保费退费)
		tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
		tLJAGetSchema.setAppntNo(tLCContSchema.getAppntNo());
		tLJAGetSchema.setSumGetMoney(sumPayMoney);
		tLJAGetSchema.setSaleChnl(tLCContSchema.getSaleChnl());
		tLJAGetSchema.setSerialNo(serNo);
		tLJAGetSchema.setOperator("001");
		tLJAGetSchema.setManageCom(tLCContSchema.getManageCom());
		tLJAGetSchema.setAgentCode(tLCContSchema.getAgentCode());
		tLJAGetSchema.setAgentCom(tLCContSchema.getAgentCom());
		tLJAGetSchema.setAgentGroup(tLCContSchema.getAgentGroup());
		tLJAGetSchema.setShouldDate(mCurrentDate);
		tLJAGetSchema.setStartGetDate(mCurrentTime);
		tLJAGetSchema.setMakeDate(mCurrentDate);
		tLJAGetSchema.setMakeTime(mCurrentTime);
		tLJAGetSchema.setModifyDate(mCurrentDate);
		tLJAGetSchema.setModifyTime(mCurrentTime);
		tLJAGetSchema.setCurrency(tCurrency);

		return tLJAGetSchema;
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
