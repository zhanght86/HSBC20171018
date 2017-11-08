package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

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
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class GrpAppModifyMakeSureUI implements BusinessService{
private static Logger logger = Logger.getLogger(GrpAppModifyMakeSureUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GrpAppModifyMakeSureUI() {
	}

	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		GrpAppModifyMakeSureBL tGrpAppModifyMakeSureBL = new GrpAppModifyMakeSureBL();

		logger.debug("----UI BEGIN---");
		if (tGrpAppModifyMakeSureBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpAppModifyMakeSureBL.mErrors);
			return false;
		}
		logger.debug("----UI END---");
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
