/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLParaClaimSimpleDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLParaClaimSimpleSchema;
import com.sinosoft.lis.vschema.LLParaClaimSimpleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交简单案例标准类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author:
 * @version 1.0
 */
public class LLParaClaimSimpleBL {
private static Logger logger = Logger.getLogger(LLParaClaimSimpleBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */

	private MMap map = new MMap();
	/** 往后面传输的数据库操作 */
	private MMap mapDel = new MMap();
	/** 执行删除的数据库操作，放在最后 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private LLParaClaimSimpleSchema mLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();

	public LLParaClaimSimpleBL() {
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
		logger.debug("----------LLParaClaimSimpleBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		logger.debug("----------LLParaClaimSimpleBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mInputData = cInputData;
		this.mOperate = cOperate;
		logger.debug("LLParaClaimSimpleBL测试--" + this.mOperate);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLLParaClaimSimpleSchema = (LLParaClaimSimpleSchema) mInputData
				.getObjectByObjectName("LLParaClaimSimpleSchema", 0);

		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLLParaClaimSimpleSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBL";
			tError.functionName = "checkData";
			tError.errorMessage = "接受的Schema信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		boolean tReturn = false;
		logger.debug("操作符" + mOperate);
		// 进行机构的录入
		if (this.mOperate.equals("Simple||INSERT")) {
			if (!SimpleInsert()) {
				return false;
			}
		}

		// 进行机构的删除
		if (this.mOperate.equals("Simple||DELETE")) {
			if (!SimpleDelete()) {
				return false;
			}
		}

		// 进行机构的修改
		if (this.mOperate.equals("Simple||UPDATE")) {
			if (!SimpleUpdate()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 进行简单案例标准数据查询
	 * 
	 * @return
	 */
	private boolean SimpleInsert() {
		// 进行简单案例标准数据查询
		LLParaClaimSimpleDB tLLParaClaimSimpleDB = new LLParaClaimSimpleDB();

		tLLParaClaimSimpleDB.setComCode(mLLParaClaimSimpleSchema.getComCode()); // 机构编号
		tLLParaClaimSimpleDB.setComCodeName(mLLParaClaimSimpleSchema
				.getComCodeName()); // 机构名称
		tLLParaClaimSimpleDB.setStartDate(mLLParaClaimSimpleSchema
				.getStartDate()); // 启用日期
		tLLParaClaimSimpleDB.setUpComCode(mLLParaClaimSimpleSchema
				.getUpComCode()); // 上级机构

		LLParaClaimSimpleSet tLLParaClaimSimpleSet = new LLParaClaimSimpleSet();
		tLLParaClaimSimpleSet = tLLParaClaimSimpleDB.query();

		if (tLLParaClaimSimpleDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLParaClaimSimpleDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBL";
			tError.functionName = "SimpleInsert";
			tError.errorMessage = "简单案例标准表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 如果简单案例标准表无数据，则添加数据
		if (tLLParaClaimSimpleSet == null || tLLParaClaimSimpleSet.size() == 0) {
			LLParaClaimSimpleSchema tLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();

			tLLParaClaimSimpleSchema.setComCode(mLLParaClaimSimpleSchema
					.getComCode());
			tLLParaClaimSimpleSchema.setComCodeName(mLLParaClaimSimpleSchema
					.getComCodeName());
			tLLParaClaimSimpleSchema.setUpComCode(mLLParaClaimSimpleSchema
					.getUpComCode());
			tLLParaClaimSimpleSchema.setBaseMin(mLLParaClaimSimpleSchema
					.getBaseMin());
			tLLParaClaimSimpleSchema.setBaseMax(mLLParaClaimSimpleSchema
					.getBaseMax());
			tLLParaClaimSimpleSchema.setStartDate(mLLParaClaimSimpleSchema
					.getStartDate());
			tLLParaClaimSimpleSchema.setEndDate(mLLParaClaimSimpleSchema
					.getEndDate());

			tLLParaClaimSimpleSchema.setOperator(this.mGlobalInput.Operator);
			// tLLParaClaimSimpleSchema.setMngCom(this.mGlobalInput.ManageCom);
			tLLParaClaimSimpleSchema.setMakeDate(this.CurrentDate);
			tLLParaClaimSimpleSchema.setMakeTime(this.CurrentTime);
			tLLParaClaimSimpleSchema.setModifyDate(this.CurrentDate);
			tLLParaClaimSimpleSchema.setModifyTime(this.CurrentTime);

			map.put(tLLParaClaimSimpleSchema, "INSERT");

		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBL";
			tError.functionName = "SimpleInsert";
			tError.errorMessage = "该条信息已存在!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 进行简单案例标准的删除
	 * 
	 * @return
	 */
	private boolean SimpleDelete() {
		LLParaClaimSimpleSchema tLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();
		tLLParaClaimSimpleSchema.setSchema(mLLParaClaimSimpleSchema);

		map.put(tLLParaClaimSimpleSchema, "DELETE");
		return true;
	}

	/**
	 * 进行简单案例标准的修改
	 * 
	 * @return
	 */
	private boolean SimpleUpdate() {

		LLParaClaimSimpleSchema tLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();
		tLLParaClaimSimpleSchema.setSchema(mLLParaClaimSimpleSchema);

		tLLParaClaimSimpleSchema.setOperator(this.mGlobalInput.Operator);
		// tLLParaClaimSimpleSchema.setMngCom(this.mGlobalInput.ManageCom);
		tLLParaClaimSimpleSchema.setMakeDate(this.CurrentDate);
		tLLParaClaimSimpleSchema.setMakeTime(this.CurrentTime);
		tLLParaClaimSimpleSchema.setModifyDate(this.CurrentDate);
		tLLParaClaimSimpleSchema.setModifyTime(this.CurrentTime);

		// logger.debug("LLMedicalFeeInpBL测试--tDay"+tDay);

		map.put(tLLParaClaimSimpleSchema, "UPDATE");
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
			// mResult.add(mLLAccidentSchema);//供前台界面使用
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
	}

}
