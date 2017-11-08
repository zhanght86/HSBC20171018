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
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证补打原因保存业务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: yuejw
 * @version 1.0
 */
public class LLPrtagainReasonBL {
private static Logger logger = Logger.getLogger(LLPrtagainReasonBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mG = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mPrtSeq;// 流水号
	private String mRemark;// 补打原因

	public LLPrtagainReasonBL() {

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
		logger.debug("----------LLPrtagainReasonBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

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
		if (!dealData(cOperate)) {
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
			tError.moduleName = "LLPrtagainReasonBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLOPRTManagerSchema = (LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0);// 按类取值
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		mRemark = mLOPRTManagerSchema.getRemark();
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 非空字段检验
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrtagainReasonBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("------start dealData-----" + cOperate);
		try {
			// 然后从 打印管理表（LOPRTManager）中查询出 “印刷流水号---PrtSeq”
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mPrtSeq);
			tLOPRTManagerDB.getInfo();
			tLOPRTManagerSchema.setSchema(tLOPRTManagerDB.getSchema());
			tLOPRTManagerSchema.setPatchFlag("1");// 1－－ 补打单证
			// tLOPRTManagerSchema.setRemark(mRemark);
			// map.put(tLOPRTManagerSchema,"DELETE&INSERT");

			/*******************************************************************
			 * //Modify by niuzj,2005-11-01
			 * //记录补打原因时,如果是多次补打,则后次的补打原因会把前次的补打原因覆盖掉
			 * //现在希望能把多次的补打原因都保存,如:补打原因=上次补打原因+本次补打日期+本次补打操作人+本次补打原因
			 ******************************************************************/
			String tManaCom = mG.ManageCom;
			String tOperator = mG.Operator;
			String tOpeDate = CurrentDate;
			String tOpeTime = CurrentTime;
			String tRemark = tLOPRTManagerSchema.getRemark();
			if (tRemark == null || tRemark == "") {
				tRemark = tOpeDate + "&" + tOperator + "&" + mRemark;
			} else {
				tRemark = tRemark + "/" + tOpeDate + "&" + tOperator + "&"
						+ mRemark;
			}
			tLOPRTManagerSchema.setRemark(tRemark);
			map.put(tLOPRTManagerSchema, "DELETE&INSERT");

			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrtagainReasonBL";
			tError.functionName = "dealData";
			tError.errorMessage = "在处理输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

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
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrtagainReasonBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public VData getResult() {
		return mResult;
	}

}
