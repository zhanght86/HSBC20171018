package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.certify.PubCertifyTakeBack;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class GroupPolBL {
private static Logger logger = Logger.getLogger(GroupPolBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private boolean TakeBackCertifyFalg = false; // 是否回收单证标记

	/** 业务处理相关变量 */
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	private LCGrpPolSchema mOldGrpPol = new LCGrpPolSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mSavePolType = "";
	private LDGrpSchema tLDGrpSchema;

	// @Constructor
	public GroupPolBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (!mOperate.equals("DELETE||GROUPPOL")) {
			if (this.checkData() == false)
				return false;
			logger.debug("---checkData---");
		}

		// 根据业务逻辑对数据进行处理
		if (!mOperate.equals("DELETE||GROUPPOL")) {
			if (this.dealData() == false)
				return false;
		} else {
			// 首先删除集体下个人单
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setGrpPolNo(mLCGrpPolSchema.getGrpPolNo());
			LCPolSet tLCPolSet = tLCPolDB.query();
			if (tLCPolSet != null) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				VData tempVData = new VData();
				String tOperate = "DELETE||PROPOSAL";
				for (int n = 1; n <= tLCPolSet.size(); n++) {
					tempVData.clear();
					tLCPolSchema = tLCPolSet.get(n);
					tempVData.addElement(tLCPolSchema);
					tempVData.addElement(mGlobalInput);
					ProposalUI tProposalUI = new ProposalUI();
					if (!tProposalUI.submitData(tempVData, tOperate)) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "GrpProposalBL";
						tError.functionName = "dealData";
						tError.errorMessage = "删除集体下个单失败！投保单号（"
								+ tLCPolSchema.getProposalNo() + "）,原因:"
								+ tProposalUI.mErrors.getFirstError()
								+ ",请确认后再操作";
						this.mErrors.addOneError(tError);
						return false;
					}

				}
			}
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		GroupPolBLS tGroupPolBLS = new GroupPolBLS();
		if (tGroupPolBLS.submitData(mInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGroupPolBLS.mErrors);
			return false;
		}
		logger.debug("---commitData---");

		if (TakeBackCertifyFalg) { // 如果回收单证标记为真
			// 单证回收
			PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
			if (tPubCertifyTakeBack
					.CheckNewType(tPubCertifyTakeBack.CERTIFY_CheckNo2)) { // 如果需要单证回收
				String operator = mGlobalInput.Operator;
				if (!tPubCertifyTakeBack.CertifyTakeBack_A(mLCGrpPolSchema
						.getPrtNo(), mLCGrpPolSchema.getPrtNo(),
						tPubCertifyTakeBack.CERTIFY_GrpProposal, mGlobalInput)) {
					logger.debug("单证回收错误（集体投保单）:"
							+ mLCGrpPolSchema.getPrtNo());
					logger.debug("错误原因："
							+ tPubCertifyTakeBack.mErrors.getFirstError()
									.toString());
					// 保存错误信息
				}
			}
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
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 集体投保单
		mLCGrpPolSchema.setSchema((LCGrpPolSchema) mInputData
				.getObjectByObjectName("LCGrpPolSchema", 0));
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		if (tTransferData != null) {
			mSavePolType = (String) tTransferData.getValueByName("SavePolType");
		}
		if (mOperate.equals("UPDATE||GROUPPOL")) {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(mLCGrpPolSchema.getGrpProposalNo());
			if (tLCGrpPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				return false;
			}
			mOldGrpPol.setSchema(tLCGrpPolDB);
		}

		String riskCode = mLCGrpPolSchema.getRiskCode();
		LMRiskSchema tempLMRiskSchema = new LMRiskSchema();
		LMRiskSet tempLMRiskSet = new LMRiskSet();
		LMRiskDB tempLMRiskDB = new LMRiskDB();
		tempLMRiskSchema.setRiskCode(riskCode);
		tempLMRiskDB.setSchema(tempLMRiskSchema);
		tempLMRiskSet = tempLMRiskDB.query();
		if (tempLMRiskSet == null || tempLMRiskSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在接受数据时没有查询到险种编码对应的险种定义纪录!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tempLMRiskSchema = tempLMRiskSet.get(1);
		mLCGrpPolSchema.setRiskVersion(tempLMRiskSchema.getRiskVer());

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		// 校验非空值
		if (mLCGrpPolSchema.getRiskCode() == null
				|| mLCGrpPolSchema.getRiskCode().trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolBL";
			tError.functionName = "checkData";
			tError.errorMessage = "请录入险种代码!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(mLCGrpPolSchema.getRiskCode());
		if (tLMRiskAppDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "checkData";
			tError.errorMessage = "LMRiskApp表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
		if (mOperate.equals("INSERT||GROUPPOL")) {
			if (tLMRiskAppSchema.getSubRiskFlag().equals("M")) {
				String strSQL = "select count(*) from lcgrppol where prtno="
						+ mLCGrpPolSchema.getPrtNo()
						+ " and riskcode in (select riskcode from lmriskapp where SubRiskFlag='M')";
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(strSQL);
				String strCount = tSSRS.GetText(1, 1);
				int SumCount = Integer.parseInt(strCount);
				if (SumCount > 0) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "ProposalBL";
					tError.functionName = "checkData";
					tError.errorMessage = "同一印刷号只能录入一个主险!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}

		if (mLCGrpPolSchema.getCValiDate() == null
				|| mLCGrpPolSchema.getCValiDate().trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolBL";
			tError.functionName = "checkData";
			tError.errorMessage = "请录入保单生效日期!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/*
		 * Lis5.3 upgrade get if( mLCGrpPolSchema.getGrpNo() == null ||
		 * mLCGrpPolSchema.getGrpNo().trim().equals( "" )) { String tLimit =
		 * "SN"; String tNo = PubFun1.CreateMaxNo( "GrpNo", tLimit );
		 * mLCGrpPolSchema.setGrpNo( tNo ); }
		 */
		if (mLCGrpPolSchema.getManageCom() == null
				|| mLCGrpPolSchema.getManageCom().trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolBL";
			tError.functionName = "checkData";
			tError.errorMessage = "请录入管理机构!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		// 产生集体投保单号码
		if (mOperate.equals("INSERT||GROUPPOL")) {
			// 校验代理人编码并置上代理人组别
			if (mLCGrpPolSchema.getAgentCode() == null
					|| mLCGrpPolSchema.getAgentCode().equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpProposalBL";
				tError.functionName = "dealData";
				tError.errorMessage = "请您确认有：代理人编码!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(mLCGrpPolSchema.getAgentCode());
				if (tLAAgentDB.getInfo() == false) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "GrpProposalBL";
					tError.functionName = "dealData";
					tError.errorMessage = "请您确认：代理人编码没有输入错误!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tLAAgentDB.getManageCom() == null) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "GrpProposalBL";
					tError.functionName = "dealData";
					tError.errorMessage = "代理人编码对应数据库中的管理机构为空！";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (!tLAAgentDB.getManageCom().equals(
						mLCGrpPolSchema.getManageCom())) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "GrpProposalBL";
					tError.functionName = "dealData";
					tError.errorMessage = "您录入的管理机构和数据库中代理人编码对应的管理机构不符合！";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLCGrpPolSchema.setAgentGroup(tLAAgentDB.getAgentGroup());

			}

			TakeBackCertifyFalg = true;
			String tLimit = PubFun.getNoLimit(mLCGrpPolSchema.getManageCom());
			String tNo = PubFun1.CreateMaxNo("GrpProposalNo", tLimit);
			mLCGrpPolSchema.setGrpProposalNo(tNo);

			mLCGrpPolSchema.setGrpPolNo(mLCGrpPolSchema.getGrpProposalNo());
			/*
			 * Lis5.3 upgrade set mLCGrpPolSchema.setContNo( SysConst.ZERONO );
			 */
			if (mLCGrpPolSchema.getAppFlag() == null
					|| mLCGrpPolSchema.getAppFlag().equals("")) {
				if (mSavePolType != null) {
					if (mSavePolType.trim().equals("")) {
						mLCGrpPolSchema.setAppFlag("0");
					} else {
						mLCGrpPolSchema.setAppFlag(mSavePolType);
					}
				} else {
					mLCGrpPolSchema.setAppFlag("0");
				}
			}

			mLCGrpPolSchema.setUWFlag("0");
			mLCGrpPolSchema.setApproveFlag("0");
			mLCGrpPolSchema.setMult(0);
			mLCGrpPolSchema.setPrem(0);
			mLCGrpPolSchema.setAmnt(0);

			mLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			mLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());

		}
		if (mOperate.equals("INSERT||GROUPPOL")) {
			mLCGrpPolSchema.setOperator(mGlobalInput.Operator);
			mLCGrpPolSchema.setMakeDate(PubFun.getCurrentDate());
			mLCGrpPolSchema.setMakeTime(PubFun.getCurrentTime());

			// 集体投保人部分信息
			LDGrpDB tLDGrpDB = new LDGrpDB();
			/*
			 * Lis5.3 upgrade set tLDGrpDB.setGrpNo( mLCGrpPolSchema.getGrpNo() );
			 */
			if (tLDGrpDB.getInfo() == false) {
				// 建立新的集体客户表数据
				tLDGrpSchema = new LDGrpSchema();
				Reflections tReflections = new Reflections();
				LDPersonSchema tLDPersonSchema = new LDPersonSchema();
				tReflections.transFields(tLDGrpSchema, mLCGrpPolSchema);
			} else {
				/*
				 * Lis5.3 upgrade set mLCGrpPolSchema.setBusinessType(
				 * tLDGrpDB.getBusinessType() ); mLCGrpPolSchema.setGrpNature(
				 * tLDGrpDB.getGrpNature() );
				 */
			}
		}
		if (mOperate.equals("UPDATE||GROUPPOL")) {
			mLCGrpPolSchema.setGrpPolNo(mOldGrpPol.getGrpProposalNo());
			/*
			 * Lis5.3 upgrade set/get mLCGrpPolSchema.setContNo(
			 * mOldGrpPol.getContNo() );
			 */
			mLCGrpPolSchema.setAppFlag(mOldGrpPol.getAppFlag());
			mLCGrpPolSchema.setUWFlag(mOldGrpPol.getUWFlag());
			mLCGrpPolSchema.setApproveFlag(mOldGrpPol.getApproveFlag());
			mLCGrpPolSchema.setApproveDate(mOldGrpPol.getApproveDate()); // add
																			// by
																			// sxy
																			// 2004-03-18
			mLCGrpPolSchema.setApproveCode(mOldGrpPol.getApproveCode()); // add
																			// by
																			// sxy
																			// 2004-03-18
			mLCGrpPolSchema.setAgentCode(mOldGrpPol.getAgentCode()); // add
																		// by
																		// sxy
																		// 2004-03-18
			mLCGrpPolSchema.setAgentGroup(mOldGrpPol.getAgentGroup());
			mLCGrpPolSchema.setMult(mOldGrpPol.getMult());
			mLCGrpPolSchema.setPrem(mOldGrpPol.getPrem());
			mLCGrpPolSchema.setAmnt(mOldGrpPol.getAmnt());
			mLCGrpPolSchema.setPeoples2(mOldGrpPol.getPeoples2());
			mLCGrpPolSchema.setSpecFlag(mOldGrpPol.getSpecFlag()); // add by
																	// sxy
																	// 2004-03-18
			mLCGrpPolSchema.setUWOperator(mOldGrpPol.getUWOperator()); // add
																		// by
																		// sxy
																		// 2004-03-18
			mLCGrpPolSchema.setOperator(mOldGrpPol.getOperator());
			mLCGrpPolSchema.setMakeDate(mOldGrpPol.getMakeDate());
			mLCGrpPolSchema.setMakeTime(mOldGrpPol.getMakeTime());
			mLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			mLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
		}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(mLCGrpPolSchema);
		if (tLDGrpSchema != null)
			mInputData.add(tLDGrpSchema);

		mResult.clear();
		mResult.add(mLCGrpPolSchema);
		if (tLDGrpSchema != null)
			mInputData.add(tLDGrpSchema);
	}

	public VData getResult() {
		return mResult;
	}

}
