package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
//public class PEdorNSDetailBLF implements EdorDetail {
	public class PEdorNSDetailBLF implements BusinessService,EdorDetail { 
	private static Logger logger = Logger.getLogger(PEdorNSDetailBLF.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PEdorNSDetailBLF() {
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param: cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据准备操作
		if (!dealData())
			return false;

		if (!pubSubmit())
			return false;

		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorNSDetailBL tPEdorNSDetailBL = new PEdorNSDetailBL();
		if (!tPEdorNSDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorNSDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorNSDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorNSDetailBL.getResult();
		return true;
	}

	/**
	 * 提交本类的处理结果到数据库
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean pubSubmit() {
		// logger.debug("\t@> PEdorNSDetailBLF.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, mOperate)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> PEdorNSDetailBLF.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;

		// logger.debug("\t@> PEdorNSDetailBLF.pubSubmit() 结束");
		return true;
	} // function pubSubmit end

	public CErrors getErrors() {
		return mErrors;
	}

}
