package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpContCarAllDelUI {
private static Logger logger = Logger.getLogger(GrpContCarAllDelUI.class);
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GrpContCarAllDelUI() {
	}

	public boolean submitData(VData cInputData) {
		GrpContCarAllDelBL tGrpContCarAllDelBL = new GrpContCarAllDelBL();
		if (tGrpContCarAllDelBL.submitData(cInputData) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpContCarAllDelBL.mErrors);
			return false;
		} else {
			mResult = tGrpContCarAllDelBL.getResult();
			return true;
		}
	}

	/**
	 * 获取从BL层取得的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GrpContCarAllDelUI grpcontcaralldelui = new GrpContCarAllDelUI();
	}
}
