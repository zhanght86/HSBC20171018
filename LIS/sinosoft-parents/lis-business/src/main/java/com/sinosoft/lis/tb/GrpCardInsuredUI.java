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
public class GrpCardInsuredUI {
private static Logger logger = Logger.getLogger(GrpCardInsuredUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public GrpCardInsuredUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpCardInsuredBL tGrpCardInsuredBL = new GrpCardInsuredBL();
		tGrpCardInsuredBL.submitData(cInputData, mOperate);
		// logger.debug("End OLCInsured UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tGrpCardInsuredBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpCardInsuredBL.mErrors);
			return false;
		}
		if (!mOperate.equals("DELETE||CONTINSURED")) {
			this.mResult.clear();
			this.mResult = tGrpCardInsuredBL.getResult();
		}
		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */
	public VData getResult() {
		return this.mResult;
	}
}
