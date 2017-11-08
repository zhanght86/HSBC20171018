package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.audit.CheckSQL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.tb.*;

public class AutoSignCardPol extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoSignCardPol.class);


	/**
	 * 个险卡单自动签单 liuqh 2009-04-29
	 */
	private CErrors mErrors = new CErrors();
	private GlobalInput mG = new GlobalInput();
	private String mCurrentDate =PubFun.getCurrentDate();
	private String mCurrentTime =PubFun.getCurrentTime();
	private LCContSet mLCContSet =new LCContSet();
	private LCContSchema mLCContSchema =new LCContSchema();
	VData mVData = new VData();
	String mContent="";
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC0033")) {
			return false;
		}
		return true;
	}

	public boolean dealMain()
	{
		mG.Operator="KD";
		mG.ComCode="86";
		mG.ManageCom="86";
		/* 业务处理逻辑 */
		logger.debug("开始自动签单处理...");
		//查询待签单卡单
		LCContDB tLCContDB =new LCContDB();
		LCContSet tLCContSet =new LCContSet();
		String tSignSQL =" select * from lccont b where b.appflag = '0' and b.cardflag = '4'"
			            //+" and prtno in('86240209042708','86240209042710') "
			            +" and b.managecom like '86%' order by b.managecom, b.makedate, b.contno";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSignSQL);
		tLCContSet =tLCContDB.executeQuery(sqlbv);
		for(int i=1;i<=tLCContSet.size();i++){
			mLCContSet =new LCContSet();
			mLCContSchema =new LCContSchema();
			mLCContSchema =tLCContSet.get(i);
			String tPrtNo =mLCContSchema.getPrtNo();
			try {
				if (!lockNo(tPrtNo)) {
					logger.debug("印刷号：["+tPrtNo+"]锁定号码失败!");
					this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
					//tLockFlag = false;
					//mPubLock.unLock();
					continue;
				}
				mLCContSet.add(mLCContSchema);
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("CurrentDate",mCurrentDate);
				tTransferData.setNameAndValue("CurrentTime",mCurrentTime);
				tTransferData.setNameAndValue("mark", "card");
				mVData.clear();
				mVData.add(mG);
				mVData.add(mLCContSet);
				mVData.add(tTransferData);
				LCContSignBL tLCContSignBL = new LCContSignBL();
				String flag = "sign";
				if (tLCContSignBL.submitData(mVData, flag) == false) 
				{
					int a =tLCContSignBL.mErrors.getErrorCount();
					String strErr = "\\n";
					for (int t = 0; t < a - 1; t++) {
						strErr += (t + 1)
						+ ": "
						+ tLCContSignBL.mErrors.getError(t).errorMessage;
					}
					mContent = strErr;
					logger.debug("签单失败！"+mContent);
					continue;
				}
				else 
				{
					logger.debug("个险卡单自动签单成功！");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				mPubLock.unLock();
			}
		}
		
		logger.debug("自动签单结束...");

		return true;
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		AutoSignCardPol tAutoSignCardPol = new AutoSignCardPol();
		logger.debug("before OtoFTask!");
		tAutoSignCardPol.dealMain();

	}

}
