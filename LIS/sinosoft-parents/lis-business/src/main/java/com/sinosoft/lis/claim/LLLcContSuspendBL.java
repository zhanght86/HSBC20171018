/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LCContHangUpBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保单挂起
 * </p>
 * <p>
 * Description: 保单挂起逻辑处理类
 * </p>
 * Copyright (c) 2005 sinosoft Co. Ltd.
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: yuejinwei
 * @version 1.0
 */

public class LLLcContSuspendBL {
private static Logger logger = Logger.getLogger(LLLcContSuspendBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private LCContHangUpStateSchema mLCContHangUpStateSchema = new LCContHangUpStateSchema();
	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();
	private GlobalInput mG = new GlobalInput();

	private String mClmNo = ""; // 赔案号

	private TransferData mTransferData = new TransferData();

	public LLLcContSuspendBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
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
		logger.debug("----------LLLcContSuspendBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		if (!getInputData())// 得到外部传入的数据,将数据备份到本类中
		{
			return false;
		}
		logger.debug("----------after getInputData----------");
		if (!checkInputData()) // 检查数据合法性
		{
			return false;
		}
		logger.debug("----------after checkInputData----------");
		if (!dealData(cOperate))// 进行业务处理
		{
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate))// @@错误处理
		{
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLLcContSuspendBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("--LLLcContSuspendBL.submitData--");
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");

		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLCContHangUpStateSet = (LCContHangUpStateSet) mInputData
				.getObjectByObjectName("LCContHangUpStateSet", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号

		if (mLCContHangUpStateSet == null)// @@错误处理
		{
			CError tError = new CError();
			tError.moduleName = "LLLcContSuspendBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 检测数据
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLcContSuspendBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		// 调用公用挂起方法
		LCContHangUpStateSet tLCContHangUpStateSaveSet = new LCContHangUpStateSet(); // 保存
		LCContHangUpStateSet tLCContHangUpStateDelSet = new LCContHangUpStateSet(); // 删除

		for (int i = 1; i <= mLCContHangUpStateSet.size(); i++) {
			String tContNo = mLCContHangUpStateSet.get(i).getContNo();

			// 判断该合同是否被挂起
			// 如果一张保单已经被挂起则不能因这张保单而影响其他保单的挂起操作，应客户要求
			// 如挂起操作中有已经被挂起的保单则在后台中打印出错误信息而不return false
			LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, mClmNo,
					"4", tContNo);
			if (!tLCContHangUpBL.queryHungUpForContNo()) {
				mErrors.copyAllErrors(tLCContHangUpBL.mErrors);
				for (int k=0;k<mErrors.getErrorCount();k++){
					logger.debug(mErrors.getErrorCount());
				}
				continue;
			}

			String tPosFlag = mLCContHangUpStateSet.get(i).getPosFlag();
			String tRNFlag = mLCContHangUpStateSet.get(i).getRNFlag();
			tLCContHangUpBL.setPosFlag(tPosFlag); // 1挂起,0正常
			tLCContHangUpBL.setRnFlag(tRNFlag);
			tLCContHangUpBL.setClmFlag("1");
			LCContHangUpStateSchema tLCContHangUpStateSchema = tLCContHangUpBL
					.getHungUp();

			// 如果都不挂起,则直接删除记录
			if (tPosFlag.equals("0") && tRNFlag.equals("0")) {
				tLCContHangUpStateDelSet.add(tLCContHangUpStateSchema);
			} else {
				tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);
			}
		}

		map.put(tLCContHangUpStateDelSet, "DELETE");
		map.put(tLCContHangUpStateSaveSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLcContSuspendBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void jbInit() throws Exception {
		//
	}
}
