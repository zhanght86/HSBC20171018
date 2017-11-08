package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团险保单产品组合录入
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:Sinosoft
 * </p>
 * 
 * @author:Chenrong
 * @version 1.0
 */
public class GrpPlanRiskUI {
private static Logger logger = Logger.getLogger(GrpPlanRiskUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GrpPlanRiskUI() {
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

		GrpPlanRiskBL tGrpPlanRiskBL = new GrpPlanRiskBL();

		logger.debug("---UI BEGIN---");
		if (tGrpPlanRiskBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpPlanRiskBL.mErrors);
			return false;
		} else {
			mResult = tGrpPlanRiskBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GrpPlanRiskUI grpplanriskui = new GrpPlanRiskUI();
	}
}
