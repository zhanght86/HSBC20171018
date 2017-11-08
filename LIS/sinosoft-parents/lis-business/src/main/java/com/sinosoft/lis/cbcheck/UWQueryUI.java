package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统保单查询部分
 * </p>
 * <p>
 * Description:接口功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class UWQueryUI {
private static Logger logger = Logger.getLogger(UWQueryUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWQueryUI() {
	}

	public static void main(String[] args) {
		UWQueryUI tUI = new UWQueryUI();
		LCPolSchema tSch = new LCPolSchema();
		tSch.setPolNo("00000120020110000265");

		VData tV = new VData();

		tV.add(tSch);
		tUI.submitData(tV, "QUERY||LCUWSUB");
		VData tVData = new VData();
		tVData = tUI.getResult();
		// tSet1=(LMDutySet)tV.get(0);
		// tS=tSet1.get(1);
		// logger.debug(tS.getDutyCode());
		// logger.debug(StrTool.unicodeToGBK(tS.getDutyName()));
		LCUWSubSchema tSub = new LCUWSubSchema();
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		UWQueryBL tUWQueryBL = new UWQueryBL();

		if (tUWQueryBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mInputData = tUWQueryBL.getResult();
		}

		return true;
	}

	public VData getResult() {
		return mInputData;
	}

}
