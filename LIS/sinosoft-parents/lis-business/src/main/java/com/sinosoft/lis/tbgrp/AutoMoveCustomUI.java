package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 扫描件申请
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class AutoMoveCustomUI {
private static Logger logger = Logger.getLogger(AutoMoveCustomUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public AutoMoveCustomUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---AutoMoveCustom BL BEGIN---");
		AutoMoveCustomBL tAutoMoveCustomBL = new AutoMoveCustomBL();

		if (tAutoMoveCustomBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tAutoMoveCustomBL.mErrors);
			mResult.clear();
			return false;
		}
		logger.debug("---AutoMoveCustom BL END---");

		mResult = tAutoMoveCustomBL.getResult();
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		AutoMoveCustomUI scanApplyUI1 = new AutoMoveCustomUI();

		VData tVData = new VData();
		ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();

		// tES_DOC_MAINSchema.setDOC_ID(9);
		tES_DOC_MAINSchema.setDocCode("861120020300000014");
		// tES_DOC_MAINSchema.setOperator("002");
		tES_DOC_MAINSchema.setInputState("1");

		tVData.add(tES_DOC_MAINSchema);

		// scanApplyUI1.submitData(tVData, "INSERT||MAIN");
		scanApplyUI1.submitData(tVData, "QUERY||MAIN");
		VData outVData = scanApplyUI1.getResult();
		logger.debug("Result:" + (String) outVData.get(0));
		logger.debug("Result:" + outVData.size());
	}
}
