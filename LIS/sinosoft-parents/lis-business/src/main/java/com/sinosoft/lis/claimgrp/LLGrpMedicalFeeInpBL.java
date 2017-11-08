/*
 * <p>ClassName: LLGrpMedicalFeeInpBL </p>
 * <p>Description: LLGrpMedicalFeeInpBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @Author：pd
 * @CreateDate：2005-11-10
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLCommendHospitalSchema;
import com.sinosoft.lis.db.LLCommendHospitalDB;
import com.sinosoft.lis.schema.LLFeeMainSchema;
import com.sinosoft.lis.schema.LLCaseReceiptSchema;
import com.sinosoft.service.BusinessService;
public class LLGrpMedicalFeeInpBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLGrpMedicalFeeInpBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LLFeeMainSet mLLFeeMainSet = new LLFeeMainSet();
	private LLCaseReceiptSet mLLCaseReceiptSet = new LLCaseReceiptSet();
	private LLFeeMainSet mLLFeeMainSetSave = new LLFeeMainSet();
	private LLCaseReceiptSet mLLCaseReceiptSetSave = new LLCaseReceiptSet();

	public LLGrpMedicalFeeInpBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("begin getinputdata");
		if (!getInputData(cInputData)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpMedicalFeeInpBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LLGrpMedicalFeeInpBL-->getInputData!";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpMedicalFeeInpBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpMedicalFeeInpBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LLGrpMedicalFeeInpBL-->submitquery!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("----------LLGrpMedicalFeeInpBL after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (this.mOperate.equals("IMPORT||MAIN")) {
			return true;
		} else if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimRegisterDealBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("----------LLGrpMedicalFeeInpBL after tPubSubmit.submitData----------");
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------mOperate----------" + mOperate);
		LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
		if (this.mOperate.equals("INSERT")
				|| this.mOperate.equals("IMPORT||MAIN")) {
			if (mLLFeeMainSet != null && mLLFeeMainSet.size() > 0) {
				for (int i = 1; i <= mLLFeeMainSet.size(); i++) {
					LLFeeMainSchema mLLFeeMainSchema = new LLFeeMainSchema();
					mLLFeeMainSchema = mLLFeeMainSet.get(i);
					// 查询医院名称和等级
					LLCommendHospitalDB tLLCommendHospitalDB = new LLCommendHospitalDB();
					if(mLLFeeMainSchema.getHospitalCode()==null||"".equals(mLLFeeMainSchema.getHospitalCode())){
						// @@错误处理
						this.mErrors
								.copyAllErrors(tLLCommendHospitalDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "LLClaimConfirmAfterInitService";
						tError.functionName = "dealData";
						tError.errorMessage = "导入的医院代码为空！";
						this.mErrors.addOneError(tError);
					}
					tLLCommendHospitalDB.setHospitalCode(mLLFeeMainSchema
							.getHospitalCode());
					LLCommendHospitalSet tLLCommendHospitalSet = tLLCommendHospitalDB
							.query();
					LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
					if (tLLCommendHospitalSet != null
							&& tLLCommendHospitalSet.size() > 0) {
						tLLCommendHospitalSchema = tLLCommendHospitalSet.get(1);
					} else {
						// @@错误处理
						this.mErrors
								.copyAllErrors(tLLCommendHospitalDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "LLClaimConfirmAfterInitService";
						tError.functionName = "dealData";
						tError.errorMessage = "没有查询到"
								+ mLLFeeMainSchema.getHospitalCode()
								+ "代码的相关医院信息!!";
						this.mErrors.addOneError(tError);
						return false;
					}

					mLLFeeMainSchema.setHospitalGrade(tLLCommendHospitalSchema
							.getHosAtti());
					mLLFeeMainSchema.setMakeDate(PubFun.getCurrentDate());
					mLLFeeMainSchema.setMakeTime(PubFun.getCurrentTime());
					mLLFeeMainSchema.setModifyDate(PubFun.getCurrentDate());
					mLLFeeMainSchema.setModifyTime(PubFun.getCurrentTime());
					mLLFeeMainSchema.setOperator(mGlobalInput.Operator);
					mLLFeeMainSchema.setMngCom(mGlobalInput.ManageCom);
					// 先删除原先的记录在从新写入
					map.put(mLLFeeMainSchema, "DELETE&INSERT");
				}
				/******************************************************************************************/

				for (int t = 1; t <= mLLCaseReceiptSet.size(); t++) {
					LLCaseReceiptSchema mLLCaseReceiptSchema = new LLCaseReceiptSchema();
					mLLCaseReceiptSchema = mLLCaseReceiptSet.get(t);
					// 生成账单费用明细流水号
					String tFeeDetailNo = PubFun1
							.CreateMaxNo("FeeDetailNo", 10);
					mLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo);
					// 设置住院天数
					if (mLLCaseReceiptSchema.getDayCount() == null
							|| mLLCaseReceiptSchema.getDayCount().equals("")) {
						int tDay = PubFun.calInterval(mLLCaseReceiptSchema
								.getStartDate(), mLLCaseReceiptSchema
								.getEndDate(), "D");
						if (tDay == 0) {
							tDay = 1;
						}
						mLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));
					} else {
						//mLLCaseReceiptSchema.setDayCount(mLLCaseReceiptSchema.
						// getDayCount());
						//------------jinsh--2007-06-12--------------------------
						// -------------//
						// 修改原因:修改页面上的住院天始结束日期的时候,住院天数不变,造成理算错误
						// 增加对天数的校验,
						int tDay = PubFun.calInterval(mLLCaseReceiptSchema
								.getStartDate(), mLLCaseReceiptSchema
								.getEndDate(), "D");
						if (tDay == 0) {
							tDay = 1;
						}

						if (!mLLCaseReceiptSchema.getDayCount().equals(
								String.valueOf(tDay))) {
							mLLCaseReceiptSchema.setDayCount(String
									.valueOf(tDay));
						} else {
							mLLCaseReceiptSchema
									.setDayCount(mLLCaseReceiptSchema
											.getDayCount());
						}
					}
					mLLCaseReceiptSchema.setDealFlag(mLLCaseReceiptSchema
							.getDealFlag());
					// 初始化时设置调整金额为初始金额
					double Fee = mLLCaseReceiptSchema.getFee();// 原始费用
					double RefuseAmnt = mLLCaseReceiptSchema.getRefuseAmnt();// 扣除费用
					double SecurityAmnt = mLLCaseReceiptSchema
							.getSecurityAmnt();// 社保赔付费用
					double HospLineAmnt = mLLCaseReceiptSchema
							.getHospLineAmnt();// 住院起付线
					mLLCaseReceiptSchema.setFee(Fee);
					mLLCaseReceiptSchema.setRefuseAmnt(RefuseAmnt);
					mLLCaseReceiptSchema.setHospLineAmnt(HospLineAmnt);// 住院起付线
					mLLCaseReceiptSchema.setAdjSum(Fee - RefuseAmnt);// 合理费用
					mLLCaseReceiptSchema.setCutApartAmnt(Fee - RefuseAmnt
							- SecurityAmnt);// 分割单费用
					mLLCaseReceiptSchema
							.setOperator(this.mGlobalInput.Operator);
					mLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
					mLLCaseReceiptSchema.setMakeDate(PubFun.getCurrentDate());
					mLLCaseReceiptSchema.setMakeTime(PubFun.getCurrentTime());
					mLLCaseReceiptSchema.setModifyDate(PubFun.getCurrentDate());
					mLLCaseReceiptSchema.setModifyTime(PubFun.getCurrentTime());
					// 住院起付线
					mLLCaseReceiptSchema.setModifyTime(PubFun.getCurrentTime());
					tLLCaseReceiptSet.add(mLLCaseReceiptSchema);
					String SQL2 = "delete from LLCaseReceipt where ClmNo = '"
							+ mLLCaseReceiptSet.get(t).getClmNo()
							+ "' and mainfeeno = '"
							+ mLLCaseReceiptSet.get(t).getMainFeeNo() + "'"
							+ " and customerno = '"
							+ mLLCaseReceiptSet.get(t).getCustomerNo() + "'";
					map.put(SQL2, "DELETE");
				}
				// 先删除原先的记录在从新写入
				map.put(tLLCaseReceiptSet, "INSERT");
			}
		}

		if (this.mOperate.equals("UPDATE")) {
			if (mLLFeeMainSet != null && mLLFeeMainSet.size() > 0) {
				for (int k = 1; k <= mLLCaseReceiptSet.size(); k++) {
					LLFeeMainSchema mLLFeeMainSchema = new LLFeeMainSchema();
					mLLFeeMainSchema = mLLFeeMainSet.get(k);
					mLLFeeMainSchema.setMakeDate(PubFun.getCurrentDate());
					mLLFeeMainSchema.setMakeTime(PubFun.getCurrentTime());
					mLLFeeMainSchema.setModifyDate(PubFun.getCurrentDate());
					mLLFeeMainSchema.setModifyTime(PubFun.getCurrentTime());
					mLLFeeMainSchema.setOperator(mGlobalInput.Operator);
					mLLFeeMainSchema.setMngCom(mGlobalInput.ManageCom);
					mLLFeeMainSet.add(mLLFeeMainSchema);
				}
				map.put(mLLFeeMainSet, "UPDATE");
			}
			if (mLLCaseReceiptSet != null && mLLCaseReceiptSet.size() > 0) {
				for (int i = 1; i <= mLLCaseReceiptSet.size(); i++) {
					LLCaseReceiptSchema mLLCaseReceiptSchema = new LLCaseReceiptSchema();
					mLLCaseReceiptSchema = mLLCaseReceiptSet.get(i);
					logger.debug("修改的数据" + mLLCaseReceiptSchema.encode());
					// 设置住院天数
					if (mLLCaseReceiptSchema.getDayCount() == null
							|| mLLCaseReceiptSchema.getDayCount().equals("")) {
						int tDay = PubFun.calInterval(mLLCaseReceiptSchema
								.getStartDate(), mLLCaseReceiptSchema
								.getEndDate(), "D");
						if (tDay == 0) {
							tDay = 1;
						}
						mLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));
					} else {
						//mLLCaseReceiptSchema.setDayCount(mLLCaseReceiptSchema.
						// getDayCount());
						//------------jinsh--2007-06-12--------------------------
						// -------------//
						// 修改原因:修改页面上的住院天始结束日期的时候,住院天数不变,造成理算错误
						// 增加对天数的校验,
						int tDay = PubFun.calInterval(mLLCaseReceiptSchema
								.getStartDate(), mLLCaseReceiptSchema
								.getEndDate(), "D");
						if (tDay == 0) {
							tDay = 1;
						}

						if (!mLLCaseReceiptSchema.getDayCount().equals(
								String.valueOf(tDay))) {
							mLLCaseReceiptSchema.setDayCount(String
									.valueOf(tDay));
						} else {
							mLLCaseReceiptSchema
									.setDayCount(mLLCaseReceiptSchema
											.getDayCount());
						}

					}
					mLLCaseReceiptSchema.setDealFlag(mLLCaseReceiptSchema
							.getDealFlag());
					// 初始化时设置调整金额为初始金额
					double Fee = mLLCaseReceiptSchema.getFee();// 原始费用
					double RefuseAmnt = mLLCaseReceiptSchema.getSelfAmnt();// 扣除费用
					double SecurityAmnt = mLLCaseReceiptSchema
							.getSecurityAmnt();// 社保赔付费用
					double HospLineAmnt = mLLCaseReceiptSchema
							.getHospLineAmnt();// 住院起付线
					mLLCaseReceiptSchema.setHospLineAmnt(HospLineAmnt);// 住院起付线
					mLLCaseReceiptSchema.setFee(Fee);
					mLLCaseReceiptSchema.setSelfAmnt(RefuseAmnt);
					mLLCaseReceiptSchema.setAdjSum(Fee - RefuseAmnt);// 合理费用
					// if(SecurityAmnt > 0){
					mLLCaseReceiptSchema.setCutApartAmnt(Fee - RefuseAmnt
							- SecurityAmnt);// 合理费用
					// }
					mLLCaseReceiptSchema
							.setOperator(this.mGlobalInput.Operator);
					mLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
					mLLCaseReceiptSchema.setMakeDate(PubFun.getCurrentDate());
					mLLCaseReceiptSchema.setMakeTime(PubFun.getCurrentTime());
					mLLCaseReceiptSchema.setModifyDate(PubFun.getCurrentDate());
					mLLCaseReceiptSchema.setModifyTime(PubFun.getCurrentTime());
					mLLCaseReceiptSetSave.add(mLLCaseReceiptSchema);
				}
				map.put(mLLCaseReceiptSetSave, "UPDATE");
			}
		}
		if (this.mOperate.equals("DELETE")) {
			if (mLLFeeMainSet != null && mLLFeeMainSet.size() > 0) {
				map.put(mLLFeeMainSet, "DELETE");
			}
			if (mLLCaseReceiptSet != null && mLLCaseReceiptSet.size() > 0) {
				map.put(mLLCaseReceiptSet, "DELETE");
			}
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLLFeeMainSet.set((LLFeeMainSet) cInputData.getObjectByObjectName(
				"LLFeeMainSet", 0));
		this.mLLCaseReceiptSet.set((LLCaseReceiptSet) cInputData
				.getObjectByObjectName("LLCaseReceiptSet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBnfBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public MMap getMMap() {
		return this.map;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
