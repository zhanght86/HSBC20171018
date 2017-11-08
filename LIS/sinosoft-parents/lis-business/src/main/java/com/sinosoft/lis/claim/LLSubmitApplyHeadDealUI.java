/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLSubmitApplySchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: 呈报处理
 * </p>
 * <p>
 * Description:呈报处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class LLSubmitApplyHeadDealUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLSubmitApplyHeadDealUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLSubmitApplyHeadDealUI() {
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";
		GlobalInput tGI = new GlobalInput();
		String Operator = tGI.Operator; // 保存登陆管理员账号
		String ManageCom = tGI.ManageCom; // 保存登陆区站,ManageCom=内存中登陆区站代码
		String transact = "UPDATE||Reply";// 获取操作类型
		// ***************************************
		// 获取呈报处理页面信息
		// ***************************************
		// 输入参数
		LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); // 呈报处理表
		tLLSubmitApplySchema.setClmNo("90000001580");
		tLLSubmitApplySchema.setSubNo("0000000092");
		tLLSubmitApplySchema.setSubCount("1");
		tLLSubmitApplySchema.setSubType("2");// 呈报类型置“2--分公司到总公司”
		tLLSubmitApplySchema.setDispType("2");// 处理类型
		tLLSubmitApplySchema.setDispIdea("ghjsdgcj");// 处理意见
		tLLSubmitApplySchema.setSubState("1");// 呈报状态

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		LLSubmitApplyHeadDealBLF tLLSubmitApplyHeadDealBLF = new LLSubmitApplyHeadDealBLF();
		if (tLLSubmitApplyHeadDealBLF.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLSubmitApplyHeadDealBLF.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLSubmitApplyHeadDealUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLSubmitApplyHeadDealBLF.getResult();
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
