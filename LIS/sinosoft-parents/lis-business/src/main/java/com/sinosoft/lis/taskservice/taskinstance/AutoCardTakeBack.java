package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.certify.CertTakeBackBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.*;

/*
 * 2008-07-01
 * 卡单自动核销程序
 * zz
 */
public class AutoCardTakeBack extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoCardTakeBack.class);


	public AutoCardTakeBack()
	{
	}
	private GlobalInput mG = new GlobalInput();

	public boolean dealMain()
	{
		mG.Operator="KD";
		mG.ComCode="86";
		mG.ManageCom="86";

		/*业务处理逻辑*/	
		// 查询已核销的卡单信息
		logger.debug("start AutoCardTakeBack……");
//		String tSql =" select t.tempfeeno from ljtempfee t,ratecard k where t.riskcode=k.riskcode and t.paymoney=k.prem and t.confflag='1'"
//			        +" and length(trim(t.tempfeeno))=20 and exists(select 1 from lzcard where startno=t.tempfeeno and endno =t.tempfeeno) order by t.tempfeeno";
		String tSql =" select t.tempfeeno from ljtempfee t where t.confflag = '1'  and char_length(trim(t.tempfeeno)) = 20 "
					+ "and t.paymoney = (select (case when sum(k.calfactorvalue) is not null then sum(k.calfactorvalue) else 0 end) from ldplandutyparam k where k.contplancode = t.riskcode and calfactor = 'Prem') "
					+ "and exists (select 1 from lzcard where startno = t.tempfeeno and endno = t.tempfeeno) "
					+ "order by t.tempfeeno";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		logger.debug("查询已核销的暂收费信息" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		logger.debug("tSSRS.getMaxRow()="+tSSRS.getMaxRow());
		for (int i = 1; i <= tSSRS.getMaxRow(); i++)
		{
			logger.debug("现在处理第" + i + "数据,卡号是:"+tSSRS.GetText(i,1));
			LZCardSet tLZCardSet = new LZCardSet();
			LZCardSchema tLZCardSchema = new LZCardSchema(); 
			tLZCardSchema.setCertifyCode(GetCertifyType(tSSRS.GetText(i,1)));
			tLZCardSchema.setStartNo(tSSRS.GetText(i,1));
			tLZCardSchema.setEndNo(tSSRS.GetText(i,1));
			tLZCardSet.add(tLZCardSchema);
			
			VData tVData = new VData();
			tVData.add(tLZCardSet);
			tVData.add(mG);
			CertTakeBackBL tCertTakeBackBL = new CertTakeBackBL();
			if(!tCertTakeBackBL.submitData(tVData, "TAKEBACK"))
			{
				logger.debug("回收单证"+tSSRS.GetText(i,1)+"失败！");
				InserErr("08", tSSRS.GetText(i,1), "单证核销失败！");
				continue;
			}
		}
		logger.debug("end AutoCardTakeBack……");
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
	
	  /**
	   * 得到自助卡单的单证类型
	   * parem:CertifyNo
	   * return :CertityCode
	   * 卡号的3-8是24+四位版本号,从0015开始; 
	   * 如果5,6的数字都还是0,则表示还没有产生借位,则系统组合出的单证类型为24+7-8位的两位数字,如2415,2416;
	   * 如果第5位是0,而第6位不是0,表明第6位的数字被借用,则系统组合出的单证类型为24+6-8位的三位数字,如24100,24101;
	   * 如果第5位不是0,不管第6位是否为0,系统组合出的单证就是卡号的3-8位数字,如241000,241001
	   */
	  public String GetCertifyType(String x) 
	  {
		  logger.debug("ActivateBL:开始根据卡号得到对应的单证类型");
		  String inputNo2=x;
		  logger.debug("需要拼出卡单类型的卡号:"+inputNo2);

		  String certifycode="";//单证类型
		  
		  //没有借位
		  if(inputNo2.substring(4,5).equals("0")&&inputNo2.substring(5,6).equals("0"))
		  {
			  certifycode=inputNo2.substring(2,4)+inputNo2.substring(6,8);
		  }
		  //两位都被借,即四位版本号的第1位被借用
		  else if(inputNo2.substring(5,6).equals("0"))
		  {
			  certifycode=inputNo2.substring(2,8);
		  }
		  //四位版本号的第2位被借用
		  else
		  {
			  certifycode=inputNo2.substring(2,4)+inputNo2.substring(5,8);
		  }

		  logger.debug("卡号"+inputNo2+"对应的单证类型:"+certifycode);
		  
		  return certifycode;
	  }

	public static void main(String[] args)
	{
		AutoCardTakeBack tAutoCardTakeBack = new AutoCardTakeBack();
		
		tAutoCardTakeBack.dealMain();
	}

}
