package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 删除发送银行数据和暂交费数据</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBTempFeeClassSchema;
import com.sinosoft.lis.schema.LBTempFeeSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LBTempFeeClassSet;
import com.sinosoft.lis.vschema.LBTempFeeSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class DeleteSendToBankBL {
private static Logger logger = Logger.getLogger(DeleteSendToBankBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 额外传递的参数 */
	// private TransferData mTransferData = null;
	private Reflections mReflections = new Reflections();

	/** 传入的业务数据 */
	private LJTempFeeClassSet inLJTempFeeClassSet = new LJTempFeeClassSet();

	/** 传出的业务数据 */
	private LJSPaySet outLJSPaySet = new LJSPaySet();
	private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
	private LBTempFeeSet outLBTempFeeSet = new LBTempFeeSet();
	private LBTempFeeClassSet outLBTempFeeClassSet = new LBTempFeeClassSet();

	public DeleteSendToBankBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"DELETE"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		// 需要传到后台处理
		if (mOperate.equals("DELETE") || mOperate.equals("UPDATE")) {
			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}
			logger.debug("---End prepareOutputData---");

			logger.debug("Start DeleteSendToBank BLS Submit...");
			DeleteSendToBankBLS tDeleteSendToBankBLS = new DeleteSendToBankBLS();
			if (tDeleteSendToBankBLS.submitData(mInputData, cOperate) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tDeleteSendToBankBLS.mErrors);
				mResult.clear();
				mResult.add(mErrors.getFirstError());
				return false;
			} else {
				mResult = tDeleteSendToBankBLS.getResult();
			}
			logger.debug("End DeleteSendToBank BLS Submit...");
		}
		// 不需要传到后台处理
		else if (mOperate.equals("")) {
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			// mTransferData =
			// (TransferData)mInputData.getObjectByObjectName("TransferData",
			// 0);

			inLJTempFeeClassSet = (LJTempFeeClassSet) mInputData
					.getObjectByObjectName("LJTempFeeClassSet", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "DeleteSendToBankBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 删除暂交费分类表数据
	 * 
	 * @param inLJTempFeeClassSchema
	 * @throws Exception
	 */
	private void deleteLJTempFeeClass(
			LJTempFeeClassSchema inLJTempFeeClassSchema) throws Exception {
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassSchema.setTempFeeNo(inLJTempFeeClassSchema
				.getTempFeeNo());
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassSchema.getDB()
				.query();

		if (tLJTempFeeClassSet.size() > 1) {
			throw new Exception("该暂交费号对应有银行代收以外的交费方式，不允许删除！");
		}

		if (tLJTempFeeClassSet.get(1).getEnterAccDate() != null
				|| tLJTempFeeClassSet.get(1).getConfFlag().equals("1")) {
			throw new Exception("该暂交费数据已到帐或已核销，不允许删除！");
		}

		outLJTempFeeClassSet = tLJTempFeeClassSet;

		// 备份分类表数据
		if (outLJTempFeeClassSet.size() > 0) {
			for (int i = 0; i < outLJTempFeeClassSet.size(); i++) {
				LBTempFeeClassSchema tLBTempFeeClassSchema = new LBTempFeeClassSchema();
				mReflections.transFields(tLBTempFeeClassSchema,
						outLJTempFeeClassSet.get(i + 1));
				tLBTempFeeClassSchema.setBackUpSerialNo(PubFun1.CreateMaxNo(
						"LBTFClass", 20));
				outLBTempFeeClassSet.add(tLBTempFeeClassSchema);
			}
		}
	}

	/**
	 * 删除应收总表数据
	 * 
	 * @param inLJTempFeeClassSchema
	 * @throws Exception
	 */
	private void deleteLJSPay(LJTempFeeClassSchema inLJTempFeeClassSchema)
			throws Exception {
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		tLJSPaySchema.setGetNoticeNo(inLJTempFeeClassSchema.getTempFeeNo());
		LJSPaySet tLJSPaySet = tLJSPaySchema.getDB().query();

		if (tLJSPaySet.size() > 1) {
			throw new Exception("出现重复银行代收数据，问题严重，请与技术管理员联系核查！");
		}

		if (tLJSPaySet.get(1).getBankOnTheWayFlag().equals("1")) {
			throw new Exception("有银行在途数据，不允许删除！");
		}

		outLJSPaySet = tLJSPaySet;
	}

	/**
	 * 删除暂交费表数据
	 * 
	 * @param inLJTempFeeClassSchema
	 * @throws Exception
	 */
	private void deleteLJTempFee(LJTempFeeClassSchema inLJTempFeeClassSchema)
			throws Exception {
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo(inLJTempFeeClassSchema.getTempFeeNo());
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeSchema.getDB().query();

		for (int i = 0; i < tLJTempFeeSet.size(); i++) {
			if (tLJTempFeeSet.get(i + 1).getEnterAccDate() != null
					|| tLJTempFeeSet.get(i + 1).getConfFlag().equals("1")) {
				throw new Exception("该暂交费有数据已到帐或已核销，不允许删除！");
			}
		}

		outLJTempFeeSet = tLJTempFeeSet;

		// 备份暂交费表数据
		if (outLJTempFeeSet.size() > 0) {
			for (int i = 0; i < outLJTempFeeSet.size(); i++) {
				LBTempFeeSchema tLBTempFeeSchema = new LBTempFeeSchema();
				mReflections.transFields(tLBTempFeeSchema, outLJTempFeeSet
						.get(i + 1));
				tLBTempFeeSchema.setBackUpSerialNo(PubFun1.CreateMaxNo(
						"LBTempFee", 20));
				outLBTempFeeSet.add(tLBTempFeeSchema);
			}
		}
	}

	/**
	 * 更新应收总表数据
	 * 
	 * @param inLJTempFeeClassSchema
	 */
	private void updateLJSPay(LJTempFeeClassSchema inLJTempFeeClassSchema)
			throws Exception {
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		tLJSPaySchema.setGetNoticeNo(inLJTempFeeClassSchema.getTempFeeNo());
		LJSPaySet tLJSPaySet = tLJSPaySchema.getDB().query();

		if (tLJSPaySet.size() > 1) {
			throw new Exception("出现重复银行代收数据，问题严重，请与技术管理员联系核查！");
		}

		// 清空已发送银行次数，让其能再发送给银行
		tLJSPaySet.get(1).setSendBankCount("0");

		outLJSPaySet = tLJSPaySet;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			if (mOperate.equals("DELETE")) {
				for (int i = 0; i < inLJTempFeeClassSet.size(); i++) {
					LJTempFeeClassSchema inLJTempFeeClassSchema = inLJTempFeeClassSet
							.get(i + 1);

					// 删除暂交费分类表数据
					deleteLJTempFeeClass(inLJTempFeeClassSchema);

					// 删除应收总表数据
					deleteLJSPay(inLJTempFeeClassSchema);

					// 删除暂交费表数据
					deleteLJTempFee(inLJTempFeeClassSchema);
				}
			} else if (mOperate.equals("UPDATE")) {
				for (int i = 0; i < inLJTempFeeClassSet.size(); i++) {
					LJTempFeeClassSchema inLJTempFeeClassSchema = inLJTempFeeClassSet
							.get(i + 1);

					// 更新应收总表数据
					updateLJSPay(inLJTempFeeClassSchema);
				}
			}
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "DeleteSendToBankBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();

			mInputData.add(outLJSPaySet);
			mInputData.add(outLJTempFeeSet);
			mInputData.add(outLJTempFeeClassSet);
			mInputData.add(outLBTempFeeSet);
			mInputData.add(outLBTempFeeClassSet);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "DeleteSendToBankBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		DeleteSendToBankBL DeleteSendToBankBL1 = new DeleteSendToBankBL();
	}
}
