package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
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
 * @author Lizhuo
 * @version 1.0
 */
public class EdorUWNoticeUI implements BusinessService{
private static Logger logger = Logger.getLogger(EdorUWNoticeUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public boolean submitData(VData cInputData, String cOperate) {

		EdorUWNoticeBLF tEdorUWNoticeBLF = new EdorUWNoticeBLF();

		if (!tEdorUWNoticeBLF.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEdorUWNoticeBLF.mErrors);
			return false;
		}

		mResult = tEdorUWNoticeBLF.getResult();

		return true;
	}

	public EdorUWNoticeUI() {
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		EdorUWNoticeUI edoruwnoticeui = new EdorUWNoticeUI();
	}
}
