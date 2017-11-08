/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLInqConclusionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLInqConclusionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLClaimInqConclusionBL {
private static Logger logger = Logger.getLogger(LLClaimInqConclusionBL.class);
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

	private TransferData mTransferData = new TransferData();
	private LLInqConclusionSchema mLLInqConclusionSchema = new LLInqConclusionSchema();

	private GlobalInput mG = new GlobalInput();
	private String mInqConclusion;
	private String mRemark;
	private String mMarFlag;

	public LLClaimInqConclusionBL() {
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
		logger.debug("----------LLClaimInqConclusionBL begin submitData----------");
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
			tError.moduleName = "LLClaimInqConclusionBL";
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
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLLInqConclusionSchema = (LLInqConclusionSchema) mInputData
				.getObjectByObjectName("LLInqConclusionSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mInqConclusion = mLLInqConclusionSchema.getInqConclusion();
		mRemark = mLLInqConclusionSchema.getRemark();
		mMarFlag = mLLInqConclusionSchema.getMasFlag();
		if (mLLInqConclusionSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionBL";
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
			// 非空字段检验
			if (mLLInqConclusionSchema.getClmNo() == null)// 赔案号
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLClaimInqConclusionBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的赔案号为空!";
				this.mErrors.addOneError(tError);
				return false;
			}

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionBL";
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
		logger.debug("------start dealData-----");

		boolean tReturn = false;
		// 调查结论表----只修改 调查结论及状态
		LLInqConclusionDB tLLInqConclusionDB = new LLInqConclusionDB();
		tLLInqConclusionDB.setClmNo(mLLInqConclusionSchema.getClmNo());
		tLLInqConclusionDB.setConNo(mLLInqConclusionSchema.getConNo());
		tLLInqConclusionDB.getInfo();
		LLInqConclusionSchema tLLInqConclusionSchema = new LLInqConclusionSchema();
		tLLInqConclusionSchema.setSchema(tLLInqConclusionDB.getSchema());
		tLLInqConclusionSchema.setFiniFlag("1");
		tLLInqConclusionSchema.setInqConclusion(mInqConclusion);
		tLLInqConclusionSchema.setRemark(mRemark);
		tLLInqConclusionSchema.setMasFlag(mMarFlag);
		tLLInqConclusionSchema.setModifyDate(CurrentDate);
		tLLInqConclusionSchema.setModifyTime(CurrentTime);
		map.put(tLLInqConclusionSchema, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.add(map);
			mResult.add(mTransferData);
			mResult.add(mG);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
