package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * Title: Web业务系统
 * 
 * Copyright: Copyright (c) 2002
 * 
 * Company: Sinosoft
 * 
 * @author HZM
 * @version 1.0
 */
public class RnDealBLF {
private static Logger logger = Logger.getLogger(RnDealBLF.class);
	// 错误处理
	public CErrors mErrors = new CErrors();

	private GlobalInput tGI = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 得到前台数据的容器 */
	private VData mResult = new VData();

	private VData m1Result = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	
	private TransferData mTransferData = new TransferData();

	private String  mStartDate="", mEndDate="";

	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	public RnDealBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	public VData getLCResult() {
		return m1Result;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		tGI = ((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		return true;
	}

	private boolean dealData() {
		
	    //日志监控,过程监控        
	  	PubFun.logTrack(tGI,tGI.LogID[1],"续期续保自动催收开始");

		// 定义处理的出错标记
		int ErrCount = 0;
		int suc=0;

		// 查询所有符合抽档条件的所有保单
		IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
		//LCContSet tLCContSet = new LCContSet();		
		String[][] xResult ;
		if (!tIndiDueFeePartQuery.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tIndiDueFeePartQuery.mErrors);
			return false;
		} else {
			VData tVData = tIndiDueFeePartQuery.getResult();
			xResult = (String[][]) tVData.get(0);
		}
		
        int xLength = xResult.length;
		// 加锁contno，LR0001：续期催收
		String LockNo = "";
		try {
			// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
			VData mVData = new VData();
			for (int i = 1; i <= xLength; i++) 
			{
				//先加锁
				logger.debug(i);
				logger.debug("当前处理contno:"+xResult[i-1][0]);
				LockNo = "";
				LockNo= xResult[i-1][0];
				// if (!mPubLock.lock(LockNo, "LR0001", tGI.Operator))
				// {
				// CError tError = new CError(mPubLock.mErrors.getLastError());
				// this.mErrors.addOneError(tError);
				// continue;
				// }
				MMap tMap = new MMap();
				RnDealBL tRnDealBL = new RnDealBL();
				LCContSchema tLCContSchema = new LCContSchema();
				LCContDB xLCContDB = new LCContDB();
				xLCContDB.setContNo(xResult[i-1][0]);
				if(!xLCContDB.getInfo())
				{
					this.mErrors.addOneError("未找到保单"+xResult[i-1][0]+"的信息");
//					日志监控,状态监控          
	  		    	PubFun.logState(tGI,xResult[i-1][0],"未找到保单"+xResult[i-1][0]+"的信息","0");	  		    	
					ErrCount++;
					continue;
				}
				tLCContSchema=xLCContDB.getSchema();
				mVData.clear();
				mVData.add(tLCContSchema);
				mVData.add(mStartDate);
				mVData.add(mEndDate);
				mVData.add(tGI);
				if (!tRnDealBL.submitData(mVData, "ContNo")) {
					this.mErrors.copyAllErrors(tRnDealBL.mErrors);
//					日志监控,状态监控          
	  		    	PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"续期抽档失败，原因是:"+mErrors.getFirstError(),"0");
					ErrCount++;
					continue;
				} else {
					tMap.add(tRnDealBL.getMap());
				}

				mResult.add(tMap);

				// 校验应收是否有数据（分别处理）
				String SpaySql = "select * from ljspay where othernotype in ('03','02','3','2') and otherno = '?otherno?'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(SpaySql);
				sqlbv.put("otherno", tLCContSchema.getContNo());
				LCContDB tLCContDB = new LCContDB();
				LCContSet nLCContSet = new LCContSet();
				nLCContSet = tLCContDB.executeQuery(sqlbv);
				if (nLCContSet.size() > 0) 
				{
					logger.debug("注意:发现相同抽档操作,请中止其中之一!");
					CError tError = new CError();
					tError.moduleName = "IndiDueFeePartBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "处理保单" + tLCContSchema.getContNo()
							+ "时发现相同抽档操作,请中止其中之一!";
//					日志监控,状态监控          
	  		    	PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"续期抽档失败，原因是:"+tError.errorMessage,"0");
					ErrCount++;
					continue;
				}

				// 提交数据
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) 
				{
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					ErrCount++;
					mResult.clear();
					continue;
				}
//				日志监控,状态监控          
  		    	PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"续期抽档成功","1");
  		    	suc++;
				mResult.clear();
				mPubLock.unLock();// 解锁
			}
//			日志监控,结果监控
			PubFun.logResult (tGI,tGI.LogID[1],"共有"+suc+"个保单续期抽档成功");
			PubFun.logResult (tGI,tGI.LogID[1],"共有"+ErrCount+"个保单续期抽档失败");	
			if (ErrCount > 0) 
			{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} finally {
			mPubLock.unLock();// 解锁
		}
	    //日志监控,过程监控        
	  	PubFun.logTrack(tGI,tGI.LogID[1],"续期续保自动催收结束");

		return true;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Contno", "NJ050526161000880");
		tTransferData.setNameAndValue("StartDate", "2006-4-1");
		tTransferData.setNameAndValue("EndDate", "2006-4-30");

		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.Operator = "001";

		VData tv = new VData();
		tv.add(tTransferData);
		tv.add(tGI);

		RnDealBLF IndiDueFeePartBLF1 = new RnDealBLF();
		if (IndiDueFeePartBLF1.submitData(tv, "")) {
			logger.debug("个单批处理催收完成");
		} else {
			logger.debug("个单批处理催收信息提示：");
		}
	}
}
