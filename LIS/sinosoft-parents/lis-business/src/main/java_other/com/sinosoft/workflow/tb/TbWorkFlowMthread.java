package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:新契约工作流-多线程版本
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
 * @author tongmeng
 * @version 6.5
 */

public class TbWorkFlowMthread extends CovBase {
private static Logger logger = Logger.getLogger(TbWorkFlowMthread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;

	public TbWorkFlowMthread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (VData) tObject;
	}
	
	public void run() {
		TransferData mTransferData = new TransferData();
		mTransferData = mInputDataNew.getObjectByObjectName("TransferData", 0)==null?null
				:(TransferData)mInputDataNew.getObjectByObjectName("TransferData", 0);
		if(mTransferData!=null)
		{
			String tActivityid = (String)mTransferData.getValueByName("ActivityID");
			submitData(mInputDataNew, tActivityid);
		}
		this.close();
	}

	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		TbWorkFlowBL tTbWorkFlowBL = new TbWorkFlowBL();

		logger.debug("---TbWorkFlowBL UI BEGIN---");
		if (tTbWorkFlowBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tTbWorkFlowBL.mErrors);
			mResult.clear();
			return false;
		}
		mResult = tTbWorkFlowBL.getResult();
		return true;
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}
