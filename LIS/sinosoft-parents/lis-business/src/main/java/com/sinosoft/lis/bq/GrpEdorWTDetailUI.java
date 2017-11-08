package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单犹豫期退保明细</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * ReWrite ZhangRong
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpEdorWTDetailUI {
private static Logger logger = Logger.getLogger(GrpEdorWTDetailUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GrpEdorWTDetailUI() {
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

		GrpEdorWTDetailBL tGrpEdorWTDetailBL = new GrpEdorWTDetailBL();
		if (!tGrpEdorWTDetailBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpEdorWTDetailBL.mErrors);
			return false;
		} else {
			mResult = tGrpEdorWTDetailBL.getResult();
		}

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
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		tLPGrpEdorItemSchema.setEdorNo("410110000000195");
		tLPGrpEdorItemSchema.setGrpContNo("240110000000166");
		tLPGrpEdorItemSchema.setEdorType("WT");
		tLPGrpEdorItemSchema.setGetMoney(12);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPGrpEdorItemSchema);

		GrpEdorLRDetailUI tGrpEdorLRDetailUI = new GrpEdorLRDetailUI();
		if (!tGrpEdorLRDetailUI.submitData(tVData, "INSERT")) {
		}
	}
}
