package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bqgrp.GrpEdorConfirmBL;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LPAccMoveDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LPAccMoveSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LPAccMoveSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLGrpClaimEdorTypeCABL
{
private static Logger logger = Logger.getLogger(LLGrpClaimEdorTypeCABL.class);


	/**
	 * @param zy
	 *   2009-07-29 康福账户资金转移业务处理
	 */

	public LLGrpClaimEdorTypeCABL()
	{
	}

	public CErrors mErrors = new CErrors();

	private VData mInputData = new VData();
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	private String mOperate;
	private GlobalInput mGlobalInput = null;
	private TransferData mTransferData;

	private LCInsureAccTraceSchema outLCInsureAccTraceSchema = new LCInsureAccTraceSchema();//转出的账户信息
	private LCInsureAccTraceSet inLCInsureAccTraceSet = new LCInsureAccTraceSet();//转入的账户信息
	private double tAccTotalMoney=0.0;//公共账户总金额
	private double tPTotalMoney=0.0;//转移资金总金额
	private PubConcurrencyLock mLock = new PubConcurrencyLock();
	private String tOperatedNo;


	public boolean submitData(VData cInputData, String cOperate)
	{
		this.mInputData = (VData)cInputData.clone();
		this.mOperate = cOperate;
		try
		{
			// 获取前台传递的数据
			if(!getInputData(cInputData))
			{
				return false;
			}
			logger.debug("LLGrpClaimEdorTypeCABL--getInputData--end");
			if(!checkData())
			 return false;
			
			//进行加锁(针对需要资金转移的转出账户进行并发)
			 tOperatedNo = outLCInsureAccTraceSchema.getGrpContNo();
	
			if(!mLock.lock(tOperatedNo, "LP0007", mGlobalInput.Operator))
			{
				CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;

			}
			//处理账户资金转移操作
			if("INSERT".equals(mOperate))
			{
	
				if(!dealData())
				{
					return false;
				}
	
				logger.debug("LLGrpClaimEdorTypeCABL--dealData--end");
				if(!prepareData())
				{
					return false;
				}
	
				PubSubmit tSubmit = new PubSubmit();
				if(!tSubmit.submitData(mResult, ""))
				{
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					CError.buildErr(this, "数据提交失败");
					return false;
				}
				logger.debug("LLGrpClaimEdorTypeCABL--PubSubmit--end");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}

		return true;
	}

	private boolean getInputData(VData sugoInputData)
	{

			mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
			mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
			outLCInsureAccTraceSchema = (LCInsureAccTraceSchema)mInputData.getObjectByObjectName("LCInsureAccTraceSchema", 0);
			inLCInsureAccTraceSet = (LCInsureAccTraceSet)mInputData.getObjectByObjectName("LCInsureAccTraceSet", 0);
			tAccTotalMoney =Double.parseDouble( (String)mTransferData.getValueByName("tAccTotal"));

			if( mGlobalInput==null)
			{
				CError.buildErr(this, "获取系统登录信息失败，请您确认!");
				return false;
			}
			
			if( mTransferData==null || outLCInsureAccTraceSchema==null || inLCInsureAccTraceSet==null)
			{
				CError.buildErr(this, "获取账户信息失败，请您确认!");
				return false;
			}

			if( tAccTotalMoney<=0)
			{
				CError.buildErr(this, "获取总账户金额小于0，请您确认!");
				return false;
			}
			return true;
	}

	public VData getResult()
	{
		return mResult;
	}



	//进行数据校验
	private boolean checkData()
	{
		//判断保单是否被保全挂起
		ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);
		if (!tContHangUpBL.checkGrpHangUpState(outLCInsureAccTraceSchema.getGrpContNo(), "2")) // 2-保全
		{
			mErrors.copyAllErrors(tContHangUpBL.mErrors);
			 CError.buildErr(this, "合同"+outLCInsureAccTraceSchema.getGrpContNo()+"正处于保全挂起状态，不允许做资金转移操作!");
			return false;
		} 
		
		return true;
	}
	private boolean dealData()
	{
		
		LCInsureAccTraceSet tLCInsureAccTraceSetIn = new LCInsureAccTraceSet();
		LCInsureAccTraceSet tLCInsureAccTraceSetOut = new LCInsureAccTraceSet();
		LPAccMoveSet rLPAccMoveSet = new LPAccMoveSet();
		//处理保险帐户表记价履历表
		//处理转入账户轨迹信息
		for(int i=1; i<=inLCInsureAccTraceSet.size();i++)
		{
			LCInsureAccTraceSchema mLCInsureAccTraceSchema = inLCInsureAccTraceSet.get(i);
			//获取个人账户分类表信息
			String tPAccSql = "select * from lcinsureaccclass  a where grpcontno='"+mLCInsureAccTraceSchema.getGrpContNo()+"' and contno='"+mLCInsureAccTraceSchema.getContNo()+"' "
							+ " and payplancode='"+mLCInsureAccTraceSchema.getPayPlanCode()+"' and riskcode='"+mLCInsureAccTraceSchema.getRiskCode()+"' ";
			logger.debug("获取个人账户分类表信息---"+tPAccSql);
			LCInsureAccClassDB pLCInsureAccClassDB = new LCInsureAccClassDB();
			LCInsureAccClassSet pLCInsureAccClassSet = pLCInsureAccClassDB.executeQuery(tPAccSql);
			if(pLCInsureAccClassSet.size()<=0)
			{
				CError.buildErr(this, "保单"+mLCInsureAccTraceSchema.getContNo()+"对应的账户信息不存在，请核实");
				return false;
			}
			
			//处理每个需要转入金额的个人账户信息
			LCInsureAccClassSchema pLCInsureAccClassSchema = pLCInsureAccClassSet.get(1);//由于康福类的每个单子的个人账户只有一个，所以直接获取第一条记录
			LCInsureAccTraceSchema tLCInsureAccTraceSchemaIn = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchemaIn = initLCInsureAccTrace(pLCInsureAccClassSchema, mLCInsureAccTraceSchema.getMoney());
			tLCInsureAccTraceSchemaIn.setMoneyType("AI");
			tLCInsureAccTraceSchemaIn.setEdorType("CA");
			tLCInsureAccTraceSetIn.add(tLCInsureAccTraceSchemaIn);
			tPTotalMoney =tPTotalMoney +mLCInsureAccTraceSchema.getMoney();
			
			// 处理帐户转换表
			LPAccMoveSchema rLPAccMoveSchema = new LPAccMoveSchema();
			rLPAccMoveSchema.setEdorNo(PubFun1.CreateMaxNo("SERIALNO", PubFun.getNoLimit(mGlobalInput.ManageCom)));
			rLPAccMoveSchema.setEdorType("CA");
			rLPAccMoveSchema.setPolNo(pLCInsureAccClassSchema.getContNo());
			rLPAccMoveSchema.setInsuAccNo(pLCInsureAccClassSchema.getInsuAccNo());
			rLPAccMoveSchema.setPayPlanCode(pLCInsureAccClassSchema.getPayPlanCode());
			rLPAccMoveSchema.setRiskCode(pLCInsureAccClassSchema.getRiskCode());
			rLPAccMoveSchema.setAccType(pLCInsureAccClassSchema.getAccType());
			rLPAccMoveSchema.setAccMoveType("C");
			rLPAccMoveSchema.setOtherNo(mLCInsureAccTraceSchema.getOtherNo());//存放立案号
			rLPAccMoveSchema.setOtherType(mLCInsureAccTraceSchema.getOtherType());
			rLPAccMoveSchema.setAccAscription(pLCInsureAccClassSchema.getAccAscription());
			rLPAccMoveSchema.setAccMoveNo(pLCInsureAccClassSchema.getInsuAccNo());
			rLPAccMoveSchema.setAccMoveCode(pLCInsureAccClassSchema.getPayPlanCode());
			rLPAccMoveSchema.setAccMoveBala(mLCInsureAccTraceSchema.getMoney());
			rLPAccMoveSchema.setSerialNoIn(tLCInsureAccTraceSchemaIn.getSerialNo());
			rLPAccMoveSchema.setOperator(mGlobalInput.Operator);
			rLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
			rLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
			rLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
			rLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
			rLPAccMoveSet.add(rLPAccMoveSchema);
		}
		tPTotalMoney = PubFun.round(tPTotalMoney, 2);
		tAccTotalMoney = PubFun.round(tAccTotalMoney, 2);
		
		logger.debug("tPTotalMoney---"+tPTotalMoney);
		logger.debug("tAccTotalMoney---"+tAccTotalMoney);
		if(Math.abs(tAccTotalMoney-tPTotalMoney)<0.001)
		{
			CError.buildErr(this, "转移总金额超出公共账户总金额，请核实！");
			return false;
		}
		mMap.put(rLPAccMoveSet, "DELETE&INSERT");
		//处理公共账户轨迹信息
		//获取公共账户分类表的信息
		String tAccSql = "select * from lcinsureaccclass  a where grpcontno='"+outLCInsureAccTraceSchema.getGrpContNo()+"' "
					   + " and exists(select 1 from lccont where  contno=a.contno and grpcontno='"+outLCInsureAccTraceSchema.getGrpContNo()+"' and poltype='2') ";
		
		
		logger.debug("获取个人账户分类表信息---"+tAccSql);
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(tAccSql);
		if(tLCInsureAccClassSet.size()<=0)
		{
			CError.buildErr(this, "团单"+outLCInsureAccTraceSchema.getGrpContNo()+"对应的账户信息不存在，请核实");
			return false;
		}
		
		LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);//由于康福类的公共账户只有一个，所以直接获取第一条记录
		//处理保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchemaOut = new LCInsureAccTraceSchema();
		
		// 处理转出帐户
		tLCInsureAccTraceSchemaOut = initLCInsureAccTrace(tLCInsureAccClassSchema, -(PubFun.round((tPTotalMoney), 2)));//总共转出金额
		tLCInsureAccTraceSchemaOut.setMoneyType("AO");
		tLCInsureAccTraceSchemaOut.setEdorType("CA");
		tLCInsureAccTraceSetOut.add(tLCInsureAccTraceSchemaOut);

		mMap.put(tLCInsureAccTraceSetIn, "INSERT");
		mMap.put(tLCInsureAccTraceSetOut, "INSERT");

		
		// 处理帐户转换表
		LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
		tLPAccMoveSchema.setEdorNo(PubFun1.CreateMaxNo("SERIALNO", PubFun.getNoLimit(mGlobalInput.ManageCom)));
		tLPAccMoveSchema.setEdorType("CA");
		tLPAccMoveSchema.setPolNo(tLCInsureAccClassSchema.getContNo());
		tLPAccMoveSchema.setInsuAccNo(tLCInsureAccClassSchema.getInsuAccNo());
		tLPAccMoveSchema.setPayPlanCode(tLCInsureAccClassSchema.getPayPlanCode());
		tLPAccMoveSchema.setRiskCode(tLCInsureAccClassSchema.getRiskCode());
		tLPAccMoveSchema.setAccType(tLCInsureAccClassSchema.getAccType());
		tLPAccMoveSchema.setAccMoveType("C");
		tLPAccMoveSchema.setOtherNo(outLCInsureAccTraceSchema.getOtherNo());//存放立案号
		tLPAccMoveSchema.setOtherType(outLCInsureAccTraceSchema.getOtherType());
		tLPAccMoveSchema.setAccAscription(tLCInsureAccClassSchema.getAccAscription());
		tLPAccMoveSchema.setAccMoveNo(tLCInsureAccClassSchema.getInsuAccNo());
		tLPAccMoveSchema.setAccMoveCode(tLCInsureAccClassSchema.getPayPlanCode());
		tLPAccMoveSchema.setAccMoveBala(tPTotalMoney);
		tLPAccMoveSchema.setSerialNoOut(tLCInsureAccTraceSchemaOut.getSerialNo());
		tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
		tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
		tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
		tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());

		mMap.put(tLPAccMoveSchema, "DELETE&INSERT");
		//删除任一张理算涉及的表，用以控制必须重新进行理算
		String dSql ="delete from LLClaimDetail where clmno = '" +outLCInsureAccTraceSchema.getOtherNo()+ "'";
		mMap.put(dSql, "DELETE");
		return true;
	}

	private LCInsureAccTraceSchema initLCInsureAccTrace(LCInsureAccClassSchema aLCInsureAccClassSchema,
			double aAccBalance)
	{
		LCInsureAccTraceSchema aLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		String curdate = PubFun.getCurrentDate();
		String curtime =PubFun.getCurrentTime();
		String serialno =PubFun1.CreateMaxNo("SERIALNO", PubFun.getNoLimit(mGlobalInput.ManageCom));
		Reflections ref = new Reflections();
		ref.transFields(aLCInsureAccTraceSchema, aLCInsureAccClassSchema);
		aLCInsureAccTraceSchema.setMakeDate(curdate);
		aLCInsureAccTraceSchema.setMakeTime(curtime);
		aLCInsureAccTraceSchema.setManageCom(mGlobalInput.ManageCom);
		aLCInsureAccTraceSchema.setModifyDate(curdate);
		aLCInsureAccTraceSchema.setModifyTime(curtime);
		aLCInsureAccTraceSchema.setMoney(aAccBalance);
		aLCInsureAccTraceSchema.setFeeCode("000000");
		aLCInsureAccTraceSchema.setPayDate(curdate);
		aLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		aLCInsureAccTraceSchema.setSerialNo(serialno);
		aLCInsureAccTraceSchema.setState("");
		aLCInsureAccTraceSchema.setUnitCount(0);
		aLCInsureAccTraceSchema.setOtherNo(outLCInsureAccTraceSchema.getOtherNo());//存放立案号
		aLCInsureAccTraceSchema.setOtherType(outLCInsureAccTraceSchema.getOtherType());
		return aLCInsureAccTraceSchema;
	}


	
	private boolean prepareData()
	{
		mResult.clear();
		mResult.add(mMap);
		return true;
	}
	




	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
