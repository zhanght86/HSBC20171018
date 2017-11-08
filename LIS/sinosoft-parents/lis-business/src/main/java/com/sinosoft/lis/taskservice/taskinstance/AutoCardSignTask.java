package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.list.SinApproveBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.*;


public class AutoCardSignTask extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoCardSignTask.class);


	public AutoCardSignTask()
	{
	}

	private CErrors mErrors = new CErrors();
	private GlobalInput mG = new GlobalInput();


	public boolean dealMain()
	{
		mG.Operator="KD";
		mG.ComCode="86";
		mG.ManageCom="86";
		/*业务处理逻辑*/
		
		// 查询已经交费并且到帐未核销的卡单信息
//		String tSql = "select A.tempfeeno,A.riskcode,A.tempfeetype"
//				+ " from"
//				+ " (select t.tempfeeno,t.riskcode,t.tempfeetype"
//				+ " from ljtempfee t,ratecard k"
//				+ " where substr(t.tempfeeno,3,1)<>'7'"
//				+ " and t.riskcode=k.riskcode and t.paymoney=k.prem   and t.confflag='0'  and t.enteraccdate is not null and  length(trim(t.tempfeeno))=20"
//				+ " union all"
//				+ " select t.tempfeeno,t.riskcode,t.tempfeetype"
//				+ " from ljtempfee t"
//				+ " where substr(t.tempfeeno,3,1)='7'"
//				+ " and t.confflag='0'  and t.enteraccdate is not null and  length(trim(t.tempfeeno))=20"
//				+ " and t.paymoney=(select nvl(sum(k.calfactorvalue),0) from ldplandutyparam k where k.contplancode=t.riskcode and calfactor='Prem')"
//				+ " )A"
//				//+ " where A.tempfeeno='86240018091200000011'"//测试用
//				+ " order by A.tempfeeno";
		//zy 2010-02-23 新的自助卡单只针对产品组合类进行自动签单
		String tSql ="select t.tempfeeno,t.riskcode,t.tempfeetype from ljtempfee t where t.confflag='0'  and t.enteraccdate is not null and  char_length(trim(t.tempfeeno))=20"
			        + " and t.paymoney=(select (case when sum(k.calfactorvalue) is not null then sum(k.calfactorvalue) else 0 end) from ldplandutyparam k where k.contplancode=t.riskcode and calfactor='Prem') order by t.tempfeeno ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		logger.debug("查询未核销的暂收费信息" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		for (int i = 1; i <= tSSRS.MaxRow; i++)
		{
			logger.debug("现在处理第" + i + "数据");
			LJTempFeeSchema tempFeeSchema = new LJTempFeeSchema();
			LJTempFeeDB tempFeeDB = new LJTempFeeDB();
			tempFeeDB.setTempFeeNo(tSSRS.GetText(i, 1));
			tempFeeDB.setRiskCode(tSSRS.GetText(i, 2));
			tempFeeDB.setTempFeeType(tSSRS.GetText(i, 3));
			if (!tempFeeDB.getInfo())
			{
				CError.buildErr(this, "没有相应的交费信息,请核对！");				
				continue;
			}

			// 获取卡单的暂收费记录信息
    		tempFeeSchema =tempFeeDB.query().get(1);
    	    VData vd = new VData();
    		vd.addElement(tempFeeSchema);

			// 对该张卡单进行核销处理
    		SinApproveBL tApproveBL = new SinApproveBL();
    		if(!tApproveBL.submitData(vd, "INSERT"))
    		{
				CError.buildErr(this, "核销失败！");				
				continue;  			
    		}
    		
    		//每核销成功一笔,插入一笔核销成功记录
    		logger.debug("核销处理完毕");
            InserErr("00", tempFeeSchema.getTempFeeNo(), "核销处理完毕！");
		}


		return true;

	}
	

	/**
	 * 卡单核信息记录日志
	 */
	public boolean InserErr(String aErrType, String aPolNo, String aRemark)
	{
		
		CardErrLogDB mCardErrLogDB = new CardErrLogDB();
		mCardErrLogDB.setSerialNo(PubFun1.CreateMaxNo("SERIALNO", 20));
		mCardErrLogDB.setErrType(aErrType);
		mCardErrLogDB.setPolNo(aPolNo);
		mCardErrLogDB.setRemark(aRemark);
		mCardErrLogDB.setManageCom(mG.ManageCom);
		mCardErrLogDB.setOperator(mG.Operator);
		mCardErrLogDB.setMakeDate(PubFun.getCurrentDate());
		mCardErrLogDB.setMakeTime(PubFun.getCurrentTime());
		if (!mCardErrLogDB.insert())
			logger.debug("插入错误信息失败！");
		return true;

	}

	public static void main(String[] args)
	{
		AutoCardSignTask autoCardSignTask = new AutoCardSignTask();
		
		autoCardSignTask.dealMain();
	}

}
