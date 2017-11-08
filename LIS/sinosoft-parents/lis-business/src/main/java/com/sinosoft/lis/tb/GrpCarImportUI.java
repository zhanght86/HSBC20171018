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
public class GrpCarImportUI {
private static Logger logger = Logger.getLogger(GrpCarImportUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpCarImportUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		GrpCarImport tGrpCarImport = new GrpCarImport();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tGrpCarImport.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpCarImport.mErrors);
			return false;
		} else {
			if (tGrpCarImport.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tGrpCarImport.mErrors);
			}
			mResult = tGrpCarImport.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GrpCarImportUI grpcarimportui = new GrpCarImportUI();
	}
}
