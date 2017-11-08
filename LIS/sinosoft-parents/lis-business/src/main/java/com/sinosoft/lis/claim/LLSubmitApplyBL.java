/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.schema.LLSubmitApplySchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 发起呈报逻辑处理类
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
public class LLSubmitApplyBL {
private static Logger logger = Logger.getLogger(LLSubmitApplyBL.class);
	
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private LLSubmitApplySchema mLLSubmitApplySchema = new LLSubmitApplySchema();
	private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();

	private GlobalInput mG = new GlobalInput();
	private MMap map = new MMap();
	private TransferData mTransferData = new TransferData();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	public LLSubmitApplyBL() {
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
		logger.debug("----------LLSubmitApplyBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------LLSubmitApplyBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLSubmitApplyBL after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLSubmitApplyBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLSubmitApplyBL after prepareOutputData----------");

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
		mLLSubmitApplySchema = (LLSubmitApplySchema) mInputData
				.getObjectByObjectName("LLSubmitApplySchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

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
		logger.debug("----------begin checkInputData----------");
		try {
			// 非空字段检验
			if (mLLSubmitApplySchema.getClmNo() == null)// 赔案号
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLSubmitApplyBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的赔案号为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSubmitApplyBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 更新mTransferData中的值，为工作流准备数据
	 * 
	 * @return boolean
	 */
	private boolean perpareMissionProp() {
		mTransferData.removeByName("SubNo");
		mTransferData.setNameAndValue("SubNo", mLLSubmitApplySchema.getSubNo());// 呈报序号
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("------start dealData-----");
		// int tSubCount = 0;
		boolean tReturn = false;

		// 添加纪录
		if (cOperate.equals("INSERT")) {
			// ***********
			// 呈报申请表
			// ***********
			// 生成呈报序号流水号
			String tSubNo = PubFun1.CreateMaxNo("SubNo", 10);
			logger.debug("-----生成呈报序号= " + tSubNo);

			mLLSubmitApplySchema.setSubNo(tSubNo);// 呈报序号(生成)
			// mLLSubmitApplySchema.setSubCount(tSubCount);//呈报次数

			mLLSubmitApplySchema.setOperator(mG.Operator);
			mLLSubmitApplySchema.setMakeDate(CurrentDate);
			mLLSubmitApplySchema.setMakeTime(CurrentTime);
			mLLSubmitApplySchema.setModifyDate(CurrentDate);
			mLLSubmitApplySchema.setModifyTime(CurrentTime);
			map.put(mLLSubmitApplySchema, "INSERT");

			String updatesql = "update LLSubReport set SubmitFlag='1' where SubRptNo='"
					+ "?SubRptNo?"
					+ "' and CustomerNo='"
					+ "?CustomerNo?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(updatesql);
			sqlbv.put("SubRptNo", mLLSubmitApplySchema.getClmNo());
			sqlbv.put("CustomerNo", mLLSubmitApplySchema.getCustomerNo());
			map.put(sqlbv, "UPDATE");

			// 更新mTransferData中的值
			if (!perpareMissionProp()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLSubmitApplyBL";
				tError.functionName = "perpareMissionProp";
				tError.errorMessage = "为工作流准备数据失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tReturn = true;
		} else {
			tReturn = false;
		}
		return tReturn;
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
			tError.moduleName = "LLSubmitApplyBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
