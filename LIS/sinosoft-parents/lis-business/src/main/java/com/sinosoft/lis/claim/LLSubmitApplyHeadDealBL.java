/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLSubmitApplyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLSubmitApplySchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

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
public class LLSubmitApplyHeadDealBL {
private static Logger logger = Logger.getLogger(LLSubmitApplyHeadDealBL.class);
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
	private LLSubmitApplySchema mLLSubmitApplySchema = new LLSubmitApplySchema();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	public LLSubmitApplyHeadDealBL() {
		try {
			// jbInit();
		} catch (Exception ex) {
			// ex.printStackTrace();
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
		logger.debug("----------LLSubmitApplyHeadDealBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLSubmitApplyHeadDealBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLSubmitApplyHeadDealBL after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLSubmitApplyHeadDealBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLSubmitApplyHeadDealBL after prepareOutputData----------");
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
		logger.debug("--LLSubmitApplyHeadDealBL start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLLSubmitApplySchema = (LLSubmitApplySchema) mInputData
				.getObjectByObjectName("LLSubmitApplySchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"mTransferData", 0);
		if (mLLSubmitApplySchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSubmitApplyBL";
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
		logger.debug("----------LLSubmitApplyHeadDealBL begin checkInputData----------");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 用于处理各种操作，本页包含三个操作：提起调查（修改）、呈报（添加）、回复（修改）
	 */
	private boolean dealData(String cOperate) {
		logger.debug("------LLSubmitApplyHeadDealBL start dealData-----"
				+ cOperate);
		boolean tReturn = false;
		if (cOperate.equals("UPDATE||Reply")) // 回复意见处理模块
		{
			// 用来查找需要的修改记录
			LLSubmitApplyDB tLLSubmitApplyDB = new LLSubmitApplyDB();
			tLLSubmitApplyDB.setClmNo(mLLSubmitApplySchema.getClmNo());
			tLLSubmitApplyDB.setSubCount(mLLSubmitApplySchema.getSubCount());
			tLLSubmitApplyDB.setSubNo(mLLSubmitApplySchema.getSubNo());
			tLLSubmitApplyDB.getInfo();
			LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema();
			tLLSubmitApplySchema.setSchema(tLLSubmitApplyDB.getSchema());
			// 修改字段
			tLLSubmitApplySchema.setDispPer(mG.Operator);// /** 承接人员编号 */
			tLLSubmitApplySchema.setDispDept(mG.ComCode);// /** 承接机构代码 */
			tLLSubmitApplySchema.setDispDate(CurrentDate);// 处理日期
			// 处理类型、处理意见、呈报类型、呈报状态
			tLLSubmitApplySchema
					.setDispType(mLLSubmitApplySchema.getDispType());
			tLLSubmitApplySchema
					.setDispIdea(mLLSubmitApplySchema.getDispIdea());
			tLLSubmitApplySchema.setSubType(mLLSubmitApplySchema.getSubType());
			tLLSubmitApplySchema.setSubState("1"); // 呈报状态（置“1--完成”）
			tLLSubmitApplySchema.setDispDate(CurrentDate);// 处理日期
			tLLSubmitApplySchema.setOperator(mG.Operator); // 操作员
			tLLSubmitApplySchema.setModifyDate(CurrentDate);// 最后一次修改日期
			tLLSubmitApplySchema.setModifyTime(CurrentTime);// 最后一次修改时间
			map.put(tLLSubmitApplySchema, "DELETE&INSERT");
			tReturn = true;
		}
		// 更新mTransferData中的值
		if (!perpareMissionProp()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSubmitApplyHeadDealBL";
			tError.functionName = "perpareMissionProp";
			tError.errorMessage = "为工作流准备数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return tReturn;
	}

	/**
	 * 更新mTransferData中的值，为工作流准备数据
	 * 
	 * @return boolean
	 */
	private boolean perpareMissionProp() {
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
			mResult.add(mG);
			mResult.add(mTransferData);
			mResult.add(mLLSubmitApplySchema);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSubmitApplyHeadDealBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
