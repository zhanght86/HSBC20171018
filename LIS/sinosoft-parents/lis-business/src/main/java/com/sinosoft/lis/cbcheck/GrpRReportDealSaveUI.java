package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:团体生调处理总单处理UI类
 * </p>
 * <p>
 * Company: NCL
 * </p>
 * 
 * @author 郭明宇
 * @version 1.0
 * @date 2005-7-20 11:27
 */
public class GrpRReportDealSaveUI {
private static Logger logger = Logger.getLogger(GrpRReportDealSaveUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpRReportDealSaveUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpRReportDealSaveBL tGrpRReportDealSaveBL = new GrpRReportDealSaveBL();

		if (tGrpRReportDealSaveBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpRReportDealSaveBL.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "GrpRReportDealSaveUI";
			// tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			// this.mErrors .addOneError(tError) ;
			mResult.clear();
			return false;
		} else {
			mResult = tGrpRReportDealSaveBL.getResult();
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
