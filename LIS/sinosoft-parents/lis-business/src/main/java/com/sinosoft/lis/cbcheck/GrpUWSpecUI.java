package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 新契约人工核保团单核保结论处理接口
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class GrpUWSpecUI {
private static Logger logger = Logger.getLogger(GrpUWSpecUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GrpUWSpecUI() {
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		GrpUWSpecBL tGrpUWSpecBL = new GrpUWSpecBL();

		logger.debug("----UI BEGIN---");
		if (tGrpUWSpecBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpUWSpecBL.mErrors);
			return false;
		}
		logger.debug("----UI END---");
		return true;
	}

}
