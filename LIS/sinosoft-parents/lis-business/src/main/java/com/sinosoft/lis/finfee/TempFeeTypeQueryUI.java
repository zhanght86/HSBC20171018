package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保单查询功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class TempFeeTypeQueryUI implements BusinessService{
private static Logger logger = Logger.getLogger(TempFeeTypeQueryUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public TempFeeTypeQueryUI() {
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setPolNo("86110020020210000217");
		tLCPolSchema.setManageCom("86");
		tVData.add(tLCPolSchema);
		TempFeeTypeQueryUI tTempFeeTypeQueryUI = new TempFeeTypeQueryUI();
		tTempFeeTypeQueryUI.submitData(tVData, "QUERY");
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		TempFeeTypeQueryBL tTempFeeTypeQueryBL = new TempFeeTypeQueryBL();

		if (tTempFeeTypeQueryBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tTempFeeTypeQueryBL.mErrors);
			CError.buildErr(this, "数据查询失败!");
			mInputData.clear();
			return false;
		} else
			mInputData = tTempFeeTypeQueryBL.getResult();

		return true;
	}

	public VData getResult() {
		return mInputData;
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
