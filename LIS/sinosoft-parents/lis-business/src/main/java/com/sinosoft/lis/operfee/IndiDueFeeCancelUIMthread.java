package com.sinosoft.lis.operfee;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:续期催收自动撤销
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangfh
 * @version 6.5
 */

public class IndiDueFeeCancelUIMthread extends CovBase {
private static Logger logger = Logger.getLogger(IndiDueFeeCancelUIMthread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	LPEdorAppSchema tLPEdorAppSchema;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;
	
	int nSuccCount = 0; 
	int ErrCount =0;

	public IndiDueFeeCancelUIMthread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	public void run() {
		TransferData mTransferData = new TransferData();
		MMap tMMap = new MMap();
		for(int i=0;i<this.mInputDataNew.size();i++)
		{
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(i);
			VData tVData = new VData();
			tVData.add(tMap.get("LCContSchema"));
			tVData.add(tMap.get("tG"));
			
			VData mInputData = new VData();
			if(mTransferData!=null)
			{
//				tLPEdorAppSchema = (LPEdorAppSchema)mTransferData.getValueByName("LPEdorAppSchema");
				submitData(tVData, "INSERT");
			}
			this.close();
		}
		 logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
//			日志监控,结果监控
	        PubFun.logResult (tG,tG.LogID[1],"续期催收自动撤销: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
	        logger.debug("\t@> RnDealBLMthread.run() : 续期催收自动撤销任务完毕");
	}

	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		mInputData = (VData) cInputData.clone();

		IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
		tIndiDueFeeCancelBL.submitData(mInputData, cOperate);
		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tIndiDueFeeCancelBL.mErrors.needDealError()) {
			ErrCount++;
			this.mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
			return false;
		}
		else
		{
			nSuccCount++;
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		return true;

	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}
