package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: 保全处理
 * </p>
 * <p>
 * Description:个人保单查询
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lihs
 * @ReWrite lihs
 * @version 1.0
 */
public class LLClaimRepRegRelaUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimRepRegRelaUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LLClaimRepRegRelaUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		TransferData tTransferData = new TransferData();
		LLClaimRepRegRelaBL tLLClaimRepRegRelaBL = new LLClaimRepRegRelaBL();
		if (tLLClaimRepRegRelaBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimRepRegRelaBL.mErrors);
			return false;
		} else {
			mResult = tLLClaimRepRegRelaBL.getResult();
		}
		return true;
	}

	/**
	 * 返回查询结果
	 * 
	 * @param VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 测试主程序业务方法
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		try {
			
		} catch (Exception ex) {
			logger.debug("Exception");
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
