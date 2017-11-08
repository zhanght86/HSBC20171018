package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput; 
import com.sinosoft.service.BusinessService;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HL
 * @version 6.0
 */
public class ForceUWUI  implements BusinessService{
private static Logger logger = Logger.getLogger(ForceUWUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ForceUWUI() {
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		ForceUWBL tForceUWBL = new ForceUWBL();

		logger.debug("---UI BEGIN---");
		if (tForceUWBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tForceUWBL.mErrors);
			return false;
		} else {
			mResult = tForceUWBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ForceUWUI forceuwui = new ForceUWUI();
		LCContSchema mLCContSchema = new LCContSchema();
		mLCContSchema.setContNo("130110000014086");
		mLCContSchema.setForceUWFlag("1");
		mLCContSchema.setForceUWReason("adfasdfasd");
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86110000";
		tGI.Operator = "001";
		VData tInputData = new VData();
		tInputData.add(mLCContSchema);
		tInputData.add(tGI);
		if (!forceuwui.submitData(tInputData, "UPDATE")) {
			logger.debug("forceuwui=="
					+ forceuwui.mErrors.getFirstError());
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
