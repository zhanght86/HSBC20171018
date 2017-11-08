/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔单证打印控制
 * </p>
 * <p>
 * Description:理赔单证打印控制
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author:
 * @version
 */

public class LLClaimCertiPrtControlBL {
private static Logger logger = Logger.getLogger(LLClaimCertiPrtControlBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	//
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mG = new GlobalInput();

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	public LLClaimCertiPrtControlBL() {
	}

	public static void main(String[] args) {
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();// 打印管理表
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
		logger.debug("----------LLClaimCertiPrtControlBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCertiPrtControlBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

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
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mLOPRTManagerSet = (LOPRTManagerSet) mInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0); // 按类取值

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try { // 非空字段检验
			if (mLOPRTManagerSet == null) {
				CError tError = new CError();
				tError.moduleName = "LLClaimCertiPrtControlBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "传入的数据集合为空!";
				this.mErrors.addOneError(tError);
				return false;
			}

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimCertiPrtControlBL";
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
	private boolean dealData() {
		logger.debug("------start dealData-----");
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();// 打印管理表
		int n = mLOPRTManagerSet.size();
		for (int i = 1; i <= n; i++) {
			String tPrtSeq = mLOPRTManagerSet.get(i).getPrtSeq();// 印刷流水号
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(tPrtSeq);
			tLOPRTManagerDB.getInfo();
			tLOPRTManagerSchema.setSchema(tLOPRTManagerDB.getSchema());
			tLOPRTManagerSchema.setStateFlag("0");// 修改状态
			tLOPRTManagerSet.add(tLOPRTManagerSchema);
		}
		mMap.put(tLOPRTManagerSet, "DELETE&INSERT");
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
			mInputData.add(mMap);
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimCertiPrtControlBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

}
