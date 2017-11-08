package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保险计划变更附加险中止退费处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class ChangePlanSubWithdrawBL {
private static Logger logger = Logger.getLogger(ChangePlanSubWithdrawBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 存储全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 业务处理相关变量 */
	Reflections mReflections = new Reflections();
	String tLimit = "";
	String tNo = "";

	private LJTempFeeSchema inLJTempFeeSchema = new LJTempFeeSchema();

	private LJAGetTempFeeSet outLJAGetTempFeeSet = new LJAGetTempFeeSet();
	private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LJAGetSet outLJAGetSet = new LJAGetSet();
	private LJTempFeeSet outInsertLJTempFeeSet = new LJTempFeeSet();

	public ChangePlanSubWithdrawBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("---End dealData---");

		// 申请投保单处理
		if (mOperate.equals("INSERT||MAIN")) {
			// 准备往后台的数据
			if (!prepareOutputData())
				return false;
			logger.debug("---End prepareOutputData---");

			logger.debug("Start ChangePlanSubWithdraw BLS Submit...");
			ChangePlanSubWithdrawBLS tChangePlanSubWithdrawBLS = new ChangePlanSubWithdrawBLS();
			if (tChangePlanSubWithdrawBLS.submitData(mInputData, cOperate) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tChangePlanSubWithdrawBLS.mErrors);
				mResult.clear();
				return false;
			}
			logger.debug("End ChangePlanSubWithdraw BLS Submit...");

			// 如果有需要处理的错误，则返回
			if (tChangePlanSubWithdrawBLS.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tChangePlanSubWithdrawBLS.mErrors);
			}
		}
		// 查询
		else if (mOperate.equals("QUERY||MAIN")) {
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
			inLJTempFeeSchema = (LJTempFeeSchema) mInputData
					.getObjectByObjectName("LJTempFeeSchema", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获取暂交费数据
	 * 
	 * @return
	 */
	private LJTempFeeSet getLJTempFee() {
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();

		try {
			// 获取根据印刷号关联的暂交费数据
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema.setRiskCode(inLJTempFeeSchema.getRiskCode());
			tLJTempFeeSchema.setOtherNo(inLJTempFeeSchema.getOtherNo());
			tLJTempFeeSchema.setOtherNoType("4");
			tLJTempFeeSchema.setConfFlag("0");
			tLJTempFeeSet = tLJTempFeeSchema.getDB().query();

			// 获取根据投保单号关联的暂交费数据
			LJTempFeeSchema tLJTempFeeSchema2 = new LJTempFeeSchema();
			tLJTempFeeSchema2.setRiskCode(inLJTempFeeSchema.getRiskCode());
			tLJTempFeeSchema2.setOtherNo(inLJTempFeeSchema.getTempFeeNo());
			tLJTempFeeSchema2.setOtherNoType("0");
			tLJTempFeeSchema2.setConfFlag("0");
			tLJTempFeeSet.add(tLJTempFeeSchema2.getDB().query());
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "getLJTempFee";
			tError.errorMessage = "获取暂交费数据失败!!";
			this.mErrors.addOneError(tError);
			return tLJTempFeeSet;
		}

		return tLJTempFeeSet;
	}

	/**
	 * 生成暂交费退费实付数据
	 * 
	 * @param tLJTempFeeSchema
	 * @return
	 */
	private boolean setLJAGetTempFee(LJTempFeeSchema tLJTempFeeSchema) {
		try {
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();

			// 将暂交费表的内容复制到暂交费退费实付表
			mReflections.transFields(tLJAGetTempFeeSchema, tLJTempFeeSchema);
			tLJAGetTempFeeSchema.setGetMoney(tLJTempFeeSchema.getPayMoney());
			tLJAGetTempFeeSchema.setOperator(mGlobalInput.Operator);
			// logger.debug("inLJAGetTempFeeSchema:"+inLJAGetTempFeeSchema.encode());

			tLJAGetTempFeeSchema.setMakeDate(PubFun.getCurrentDate()); // 入机时间
			tLJAGetTempFeeSchema.setMakeTime(PubFun.getCurrentTime()); // 入机日期
			tLJAGetTempFeeSchema.setModifyDate(PubFun.getCurrentDate()); // 最后一次修改日期
			tLJAGetTempFeeSchema.setModifyTime(PubFun.getCurrentTime()); // 最后一次修改时间

			tLimit = PubFun.getNoLimit(tLJTempFeeSchema.getManageCom());
			tNo = PubFun1.CreateMaxNo("GETNO", tLimit);
			logger.debug("暂交费退费实付表实付号码:" + tNo);

			tLJAGetTempFeeSchema.setActuGetNo(tNo); // 实付号码
			// 退费原因编码,先默认为0
			tLJAGetTempFeeSchema.setGetReasonCode("0");
			// 核销
			tLJAGetTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
			tLJAGetTempFeeSchema.setConfDate(PubFun.getCurrentDate());
			outLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "setLJAGetTempFee";
			tError.errorMessage = "生成暂交费退费实付数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 生成实付总表数据
	 * 
	 * @param tLJTempFeeSchema
	 * @return
	 */
	private boolean setLJAGet(LJTempFeeSchema tLJTempFeeSchema) {
		try {
			String tSo = "";

			tSo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setActuGetNo(tNo);
			tLJAGetSchema.setOtherNo(tLJTempFeeSchema.getTempFeeNo());
			tLJAGetSchema.setOtherNoType("4");
			// tLJAGetSchema.setPayMode(tLJAGetTempFeeSchema.getPayMode());
			tLJAGetSchema.setSumGetMoney(tLJTempFeeSchema.getPayMoney());
			tLJAGetSchema.setSaleChnl(tLJTempFeeSchema.getSaleChnl());
			tLJAGetSchema.setSerialNo(tSo);

			tLJAGetSchema.setOperator(mGlobalInput.Operator);
			tLJAGetSchema.setMakeDate(PubFun.getCurrentDate());
			tLJAGetSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAGetSchema.setModifyTime(PubFun.getCurrentTime());

			tLJAGetSchema.setManageCom(mGlobalInput.ManageCom);
			tLJAGetSchema.setAgentCom(tLJTempFeeSchema.getAgentCom());
			tLJAGetSchema.setAgentType(tLJTempFeeSchema.getAgentType());
			// 核销
			tLJAGetSchema.setEnterAccDate(PubFun.getCurrentDate());
			tLJAGetSchema.setConfDate(PubFun.getCurrentDate());
			outLJAGetSet.add(tLJAGetSchema);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "setLJAGet";
			tError.errorMessage = "生成暂交费退费实付数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获取主险印刷号
	 * 
	 * @return
	 */
	private String getMainRiskCode() {
		LCPolDB tLCPolDB = new LCPolDB();
		String riskCode = "";

		try {
			tLCPolDB.setPrtNo(inLJTempFeeSchema.getOtherNo());
			LCPolSet tLCPolSet = tLCPolDB.query();

			for (int i = 0; i < tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = tLCPolSet.get(i + 1);

				if (tLCPolSchema.getMainPolNo().equals(
						tLCPolSchema.getProposalNo())) {
					riskCode = tLCPolSchema.getRiskCode();
					break;
				}
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "setLJAGet";
			tError.errorMessage = "生成暂交费退费实付数据失败!!";
			this.mErrors.addOneError(tError);
			return "";
		}

		return riskCode;
	}

	/**
	 * 新增暂交费表
	 * 
	 * @param tLJTempFeeSchema
	 * @return
	 */
	private boolean setLJTempFee(LJTempFeeSchema mLJTempFeeSchema) {
		try {
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			mReflections.transFields(tLJTempFeeSchema, mLJTempFeeSchema);

			String tTempFeeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
			String tSeNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

			tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
			tLJTempFeeSchema.setTempFeeType("1");

			// 获取主险印刷号
			String riskCode = getMainRiskCode();
			if (riskCode.equals("")) {
				throw new Exception("获取主险印刷号失败！");
			}

			tLJTempFeeSchema.setRiskCode(riskCode);
			tLJTempFeeSchema.setOtherNo(mLJTempFeeSchema.getOtherNo());
			tLJTempFeeSchema.setOtherNoType("4");
			tLJTempFeeSchema.setConfFlag("0");
			tLJTempFeeSchema.setSerialNo(tSeNo);
			// 约定的附险退费转主险的操作员
			tLJTempFeeSchema.setOperator("SYS001");
			tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
			tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

			outInsertLJTempFeeSet.add(tLJTempFeeSchema);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "setLJTempFee";
			tError.errorMessage = "新增暂交费表数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			if (mOperate.equals("INSERT||MAIN")) {
				// 获取暂交费数据
				LJTempFeeSet tLJTempFeeSet = getLJTempFee();
				if (tLJTempFeeSet.size() == 0)
					throw new Exception("获取暂交费数据失败!");

				for (int i = 1; i <= tLJTempFeeSet.size(); i++) { // SET是从1开始记数
					// 往SET集合里增加的是SCHEMA的指针，所以要每条记录都要用一个NEW对象，也可以用CLONE来解决
					LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
					tLJTempFeeSchema = tLJTempFeeSet.get(i);
					// logger.debug("tLJTempFeeSchema:"+tLJTempFeeSchema.encode());

					// 生成暂交费退费实付数据
					if (!setLJAGetTempFee(tLJTempFeeSchema))
						throw new Exception("生成暂交费退费实付数据失败！");

					// 完成退费数据处理，进行核销处理
					tLJTempFeeSchema.setConfFlag("1");
					outLJTempFeeSet.add(tLJTempFeeSchema);

					// 生成实付总表数据
					if (!setLJAGet(tLJTempFeeSchema))
						throw new Exception("生成实付总表数据失败！");

					// 新增暂交费表，并关联到该附险的主险
					if (!setLJTempFee(tLJTempFeeSchema))
						throw new Exception("新增暂交费表数据失败！");
				}
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBL";
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
		mInputData = new VData();
		try {
			mInputData.add(outLJAGetTempFeeSet);
			mInputData.add(outLJTempFeeSet);
			mInputData.add(outLJAGetSet);
			mInputData.add(outInsertLJTempFeeSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
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
	 * 主方法，测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChangePlanSubWithdrawBL changePlanSubWithdrawBL1 = new ChangePlanSubWithdrawBL();
	}
}
