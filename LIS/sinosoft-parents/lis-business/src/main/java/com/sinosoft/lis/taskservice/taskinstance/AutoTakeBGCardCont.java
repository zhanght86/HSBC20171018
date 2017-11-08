package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.certify.CertTakeBackBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.*;

/*
 * 2009-05-20
 * 团险卡单核销批处理
 * liuqh
 */
public class AutoTakeBGCardCont extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoTakeBGCardCont.class);


	public AutoTakeBGCardCont()
	{
	}
	
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC0034")) {
			return false;
		}
		return true;
	}
	
	private GlobalInput mG = new GlobalInput();

	public boolean dealMain()
	{
		mG.Operator="Card";
		mG.ComCode="86";
		mG.ManageCom="86";

		/*业务处理逻辑*/	
		// 查询已核销的卡单信息
		logger.debug("start AutoTakeBGCardCont……");
		String tSql =" select a.certifycode,a.startcode,a.endcode,b.grpcontno from lcinsured a, lcgrpcont b"
			        +" where a.prtno = b.prtno and b.cardflag = '1' and b.appflag = '1' and signdate is not null"
			        +" and a.startcode is not null and a.endcode is not null and b.agentcode1 is null"
			        +" and (to_date(substr(now(), 1, 10), 'yyyy-mm-dd') - to_date(substr(signdate, 1, 10), 'yyyy-mm-dd')) between 0 and 7";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		logger.debug("查询已签单在7天之内的团单信息" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		String tGrpContNo ="";
		logger.debug("tSSRS.getMaxRow()="+tSSRS.getMaxRow());
		for (int i = 1; i <= tSSRS.getMaxRow(); i++)
		{
			String tPrtNo =tSSRS.GetText(i, 4);
			try {
				if (!lockNo(tPrtNo)) {
					logger.debug("团体合同号：["+tPrtNo+"]锁定号码失败!");
					this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
					//tLockFlag = false;
					//mPubLock.unLock();
					continue;
				}
				logger.debug("现在处理第" + i + "数据,卡单起号是:"+tSSRS.GetText(i,2)+",卡单止号是："+tSSRS.GetText(i, 3));
				tGrpContNo =tSSRS.GetText(i, 4);
				LZCardSet tLZCardSet = new LZCardSet();
				LZCardSchema tLZCardSchema = new LZCardSchema(); 
				tLZCardSchema.setCertifyCode(tSSRS.GetText(i, 1));
				tLZCardSchema.setStartNo(tSSRS.GetText(i,2));
				tLZCardSchema.setEndNo(tSSRS.GetText(i,3));
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
				//将核销过的卡单做个标记 
				MMap tMap=new MMap();
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("update lcgrpcont set agentcode1='1' where grpcontno ='"+"?tGrpContNo?"+"'");
                sqlbv1.put("tGrpContNo", tGrpContNo);
				tMap.put(sqlbv1, "UPDATE");
				PubSubmit ps = new PubSubmit();
				VData sd = new VData();
				sd.add(tMap);
				if (!ps.submitData(sd, null)) {
					CError.buildErr(this, "团体卡单核销标记修改失败！");
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				mPubLock.unLock();
			}
		}
		logger.debug("end AutoTakeBGCardCont……");
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
		AutoTakeBGCardCont tAutoTakeBGCardCont = new AutoTakeBGCardCont();
		
		tAutoTakeBGCardCont.dealMain();
	}

}
