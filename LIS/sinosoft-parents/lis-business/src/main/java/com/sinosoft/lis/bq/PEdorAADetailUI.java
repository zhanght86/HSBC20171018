package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:团体保全集体下个人功能类
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
public class PEdorAADetailUI implements BusinessService {
private static Logger logger = Logger.getLogger(PEdorAADetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PEdorAADetailUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---PEdorAADetail BL BEGIN---");
		PEdorAADetailBLF tPEdorAADetailBLF = new PEdorAADetailBLF();

		if (tPEdorAADetailBLF.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAADetailBLF.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tPEdorAADetailBLF.getResult();
		}
		logger.debug("---PEdorAADetail BL END---");

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

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		logger.debug("-------test...");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
		mLPEdorItemSchema.setEdorAcceptNo("86110000001261");
		mLPEdorItemSchema.setEdorNo("430110000001030");
		mLPEdorItemSchema.setContNo("230110000000441");
		mLPEdorItemSchema.setEdorType("AA");
		mLPEdorItemSchema.setPolNo("210110000000980");
		mLPEdorItemSchema.setInsuredNo("0000490510");

		LPDutySchema mLPDutySchema = new LPDutySchema();
		mLPDutySchema.setDutyCode("602001");
		mLPDutySchema.setAmnt("36200.00");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(mLPEdorItemSchema);
		tVData.add(mLPDutySchema);

		PEdorAADetailUI tEdorAADetailUI = new PEdorAADetailUI();
		if (!tEdorAADetailUI.submitData(tVData, "INSERT||EDOR")) {
			logger.debug(tEdorAADetailUI.mErrors.getErrContent());
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
