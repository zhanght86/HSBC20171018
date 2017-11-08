/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanDutyParamDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.db.LMRiskDutyFactorDB;
import com.sinosoft.lis.db.LQBenefitDB;
import com.sinosoft.lis.db.LQBenefitDutyParamDB;
import com.sinosoft.lis.db.LQBenefitToRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPlanDutyParamSchema;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LQBenefitDutyParamSchema;
import com.sinosoft.lis.schema.LQBenefitSchema;
import com.sinosoft.lis.schema.LQBenefitToRiskSchema;
import com.sinosoft.lis.vdb.LCContPlanDBSet;
import com.sinosoft.lis.vdb.LCContPlanDutyParamDBSet;
import com.sinosoft.lis.vdb.LCContPlanRiskDBSet;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.lis.vschema.LMRiskDutyFactorSet;
import com.sinosoft.lis.vschema.LQBenefitDutyParamSet;
import com.sinosoft.lis.vschema.LQBenefitSet;
import com.sinosoft.lis.vschema.LQBenefitToRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 数据准备类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据操作类型不同，对数据进行校验、准备处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class ImportContPlanBL {
private static Logger logger = Logger.getLogger(ImportContPlanBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LQBenefitSchema mLQBenefitShchema = new LQBenefitSchema();
	private LQBenefitSet mLQBenefitSet = new LQBenefitSet();
	private LQBenefitDB mLQBenefitDB = new LQBenefitDB();
	
	private LQBenefitToRiskSchema mLQBenefitToRiskSchema = new LQBenefitToRiskSchema();
	private LQBenefitToRiskSet mLQBenefitToRiskSet = new LQBenefitToRiskSet();
	private LQBenefitToRiskDB mLQBenefitToRiskDB = new LQBenefitToRiskDB();
	
	private LQBenefitDutyParamSchema mLQBenefitDutyParamSchema = new LQBenefitDutyParamSchema();
	private LQBenefitDutyParamSet mLQBenefitDutyParamSet = new LQBenefitDutyParamSet();
	private LQBenefitDutyParamDB mLQBenefitDutyParamDB = new LQBenefitDutyParamDB();
	
	
	private LCContPlanSchema mLCContPlanSchema = new LCContPlanSchema();
	private LCContPlanSet mLCContPlanSet = new LCContPlanSet();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCContPlanRiskSchema mLCContPlanRiskSchema = new LCContPlanRiskSchema();
	private LCContPlanRiskSet mLCContPlanRiskSet = new LCContPlanRiskSet();
	private LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
	private LCContPlanDutyParamSchema mLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
	// private String mGrpContNo = ""; //团体合同号
	// private String mContPlanCode = ""; //保险计划或默认值编码
	// private String mProposalGrpContNo = "";
	// private String mPlanType = "";
	// private String mPlanSql = "";
	// private String mPeoples3 = "";
	private String mSql = "";
	
	//wangxw 20100917
	private String mAskPriceNo = "";
	private String mAskNo = "";
	private String mGrpContNo = "";
	private String mProposalGrpContNo = "";
	private String mGrpPolNo = "";
	private ExeSQL mExeSQL = new ExeSQL();
	
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	
	public ImportContPlanBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		//if (!getInputData(cInputData)) {
		if ( !getInputData(cInputData) ) {
			return false;
		}
		if ( !this.prepareImportData() ){
			return false;
		}
/*		
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败ImportContPlanBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
*/		
		
		if ( !ImportLCContPlan() ){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败ImportContPlanBL-->ImportLCContPlan!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
/*		if (!prepareOutputData()) {
			return false;
		}*/
		mInputData = null;
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean prepareImportData() {
			
		boolean ImportFlag = true;
		
		//查询信息
		this.mLQBenefitDB.setAskPriceNo(mAskPriceNo);
		this.mLQBenefitDB.setAskNo(mAskNo);
	 	this.mLQBenefitSet = this.mLQBenefitDB.query();
	 	if( mLQBenefitSet.size()==0 ){
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "获取询价系统保单保险计划时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
		
	 	this.mLQBenefitToRiskDB.setAskPriceNo(mAskPriceNo);
		this.mLQBenefitToRiskDB.setAskNo(mAskNo);
	 	this.mLQBenefitToRiskSet = this.mLQBenefitToRiskDB.query();
	 	if( mLQBenefitToRiskSet.size()==0 ){
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "获取询价系统保单保险计划的险种信息时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
		
	 	this.mLQBenefitDutyParamDB.setAskPriceNo(mAskPriceNo);
		this.mLQBenefitDutyParamDB.setAskNo(mAskNo);
	 	this.mLQBenefitDutyParamSet = this.mLQBenefitDutyParamDB.query();

	 	if( mLQBenefitDutyParamSet.size()==0 ){
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "获取询价系统保单保险计划的责任要素值时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
	 	
	 	//设置信息
	 	/** lccontplan
	 	 *  1. GRPCONTNO, PROPOSALGRPCONTNO
	 	 *  2. CONTPLANCODE, PLANTYPE,
	 	 *  3. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
	 	 *  4. 其他： CONTPLANNAME , PLANSQL, PEOPLES3
	 	 */
	 	try{
		 	for( int i=1; i<= mLQBenefitSet.size() ; i++ )
		 	{
		 		this.mLQBenefitShchema = mLQBenefitSet.get(i);
		 		this.mLCContPlanSchema = new LCContPlanSchema();
		 		
		 		mLCContPlanSchema.setGrpContNo(mGrpContNo);
		 		mLCContPlanSchema.setProposalGrpContNo(mProposalGrpContNo);
		 		
		 		mLCContPlanSchema.setContPlanCode(mLQBenefitShchema.getContPlanCode());
		 		mLCContPlanSchema.setContPlanName(mLQBenefitShchema.getContPlanName());
		 		mLCContPlanSchema.setPlanType(mLQBenefitShchema.getPlanType());
		 		mLCContPlanSchema.setPlanSql(mLQBenefitShchema.getPlanSql());
		 		mLCContPlanSchema.setPeoples3(mLQBenefitShchema.getPeoples3());
	
		 		mLCContPlanSchema.setOperator(this.mGlobalInput.Operator);
		 		mLCContPlanSchema.setMakeDate(theCurrentDate);
		 		mLCContPlanSchema.setMakeTime(theCurrentTime);
		 		mLCContPlanSchema.setModifyDate(theCurrentDate);
		 		mLCContPlanSchema.setModifyTime(theCurrentTime);
		 		
		 		this.mLCContPlanSet.add(this.mLCContPlanSchema);
		 		if( mLCContPlanSet.size() == 0 ){
		 			ImportFlag = false ;
		 		}	
		 	}
	 	}catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "导入保单保险计划时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
	 	
	 	/** lccontplanrisk
	 	 * 1. GRPCONTNO , PROPOSALGRPCONTNO
	 	 * 2. MAINRISKCODE , RISKCODE,   RISKVERSION  , CONTPLANCODE
	 	 * 3. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
	 	 * 4. 其他
	 	 */
	 	try{
	 		for ( int i=1; i<=mLQBenefitToRiskSet.size() ;i++ ){
	 			this.mLQBenefitToRiskSchema = mLQBenefitToRiskSet.get(i);
	 			this.mLCContPlanRiskSchema = new LCContPlanRiskSchema();
	 			
	 			mLCContPlanRiskSchema.setGrpContNo(mGrpContNo);
	 			mLCContPlanRiskSchema.setProposalGrpContNo(mProposalGrpContNo);
	 			
	 			mLCContPlanRiskSchema.setMainRiskCode(mLQBenefitToRiskSchema.getMainRiskCode());
	 			mLCContPlanRiskSchema.setRiskCode(mLQBenefitToRiskSchema.getRiskCode());
	 			mLCContPlanRiskSchema.setRiskVersion(mLQBenefitToRiskSchema.getRiskVersion());//查询必须项
	 			mLCContPlanRiskSchema.setContPlanCode(mLQBenefitToRiskSchema.getContPlanCode());
	 			mLCContPlanRiskSchema.setPlanType(mLQBenefitToRiskSchema.getPlanType());
	 			
	 			mLCContPlanRiskSchema.setOperator(this.mGlobalInput.Operator);
	 			mLCContPlanRiskSchema.setMakeDate(theCurrentDate);
	 			mLCContPlanRiskSchema.setMakeTime(theCurrentTime);
	 			mLCContPlanRiskSchema.setModifyDate(theCurrentDate);
	 			mLCContPlanRiskSchema.setModifyTime(theCurrentTime);
	 			
	 			this.mLCContPlanRiskSet.add(mLCContPlanRiskSchema);
		 		
		 		if( mLCContPlanRiskSet.size() == 0 ){
		 			ImportFlag = false ;
		 		}
	 		}
	 		
	 	}catch(Exception ex ){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "导入保单险种保险计划时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
	 	
	 	/** lccontplandutyparam
	 	 * 1. GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO
	 	 * 2. MAINRISKCODE, RISKCODE, CONTPLANCODE, DUTYCODE, CALFACTOR, CALFACTORTYPE, PLANTYPE
	 	 * 3. 其他: ContPlanName , CalFactorValue
	 	 */
	 	try{
	 		for ( int i=1; i<=mLQBenefitDutyParamSet.size() ;i++ ){
	 			this.mLQBenefitDutyParamSchema = mLQBenefitDutyParamSet.get(i);
	 			this.mLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
	 			
	 			mLCContPlanDutyParamSchema.setGrpContNo(mGrpContNo);
	 			mLCContPlanDutyParamSchema.setProposalGrpContNo(mProposalGrpContNo);
	 			mLCContPlanDutyParamSchema.setGrpPolNo(mGrpPolNo);
	 			
	 			mLCContPlanDutyParamSchema.setMainRiskCode(mLQBenefitDutyParamSchema.getMainRiskCode());
	 			mLCContPlanDutyParamSchema.setRiskCode(mLQBenefitDutyParamSchema.getRiskCode());
	 			mLCContPlanDutyParamSchema.setRiskVersion(mLQBenefitDutyParamSchema.getRiskVersion());//查询必须项
	 			mLCContPlanDutyParamSchema.setContPlanCode(mLQBenefitDutyParamSchema.getContPlanCode());
	 			mLCContPlanDutyParamSchema.setContPlanName(mLQBenefitDutyParamSchema.getContPlanName());
	 			mLCContPlanDutyParamSchema.setDutyCode(mLQBenefitDutyParamSchema.getDutyCode());
	 			mLCContPlanDutyParamSchema.setCalFactor(mLQBenefitDutyParamSchema.getCalFactor());
	 			mLCContPlanDutyParamSchema.setCalFactorType(mLQBenefitDutyParamSchema.getCalFactor());
	 			mLCContPlanDutyParamSchema.setCalFactorValue(mLQBenefitDutyParamSchema.getCalFactorValue());
	 			mLCContPlanDutyParamSchema.setPlanType(mLQBenefitDutyParamSchema.getPlanType());
	 			
	 			this.mLCContPlanDutyParamSet.add(mLCContPlanDutyParamSchema);
	 			
		 		if( mLCContPlanDutyParamSet.size() == 0 ){
		 			ImportFlag = false ;
		 		}
	 		}
	 		
	 	}catch(Exception ex ){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "导入保险计划责任要素值时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
	 	 
	 	 
	 	if( ImportFlag ) { 
	 		return true;
	 	}else{
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "prepareImportData";
			tError.errorMessage = "导入保险计划时出错。";
			this.mErrors.addOneError(tError);
			return false;
	 	}
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		boolean tReturn = true;
		// 新增处理，修改处理
		if (this.mOperate.compareTo("DELETE||MAIN") != 0) {
			String RiskCode = "";
			String MainRiskCode = "";
			String DutyCode = "";
			int k = 0;

			LMRiskDutyFactorDB tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
			LMRiskDutyFactorSet tLMRiskDutyFactorSet = new LMRiskDutyFactorSet();
			// 首先校验是不是保险套餐，如果是判断本合同下有没有添加保险套餐的险种yaory
			String plantype = mLCContPlanSchema.getPlanType();
			logger.debug("是不是保险套餐:" + plantype);
			if (plantype.equals("1")) {
				// 如果都具有，还要把GRPPOLNO保存到险种责任要素里mLCContPlanDutyParam。setgrppolno
				String plancode = mLCContPlanSchema.getRemark();
				LDPlanRiskDB mLDPlanRiskDB = new LDPlanRiskDB();
				LDPlanRiskSet mLDPlanRiskSet = new LDPlanRiskSet();
				String sql = "select  * FROM ldplanrisk where contplancode='"
						+ plancode + "'";
				logger.debug("+++++++++++" + sql);
				mLDPlanRiskSet = mLDPlanRiskDB.executeQuery(sql);
				if (mLDPlanRiskSet == null || mLDPlanRiskSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "ImportContPlanBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保险套餐不存在或定义错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= mLDPlanRiskSet.size(); i++) {
					LCGrpPolDB mLCGrpPol = new LCGrpPolDB();
					LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
					String tsql = "select * From lcgrppol where riskcode='"
							+ mLDPlanRiskSet.get(i).getRiskCode()
							+ "' and grpcontno='"
							+ mLCContPlanSchema.getGrpContNo() + "'";
					logger.debug(tsql);
					mLCGrpPolSet = mLCGrpPol.executeQuery(tsql);
					if (mLCGrpPolSet == null || mLCGrpPolSet.size() == 0) {
						CError tError = new CError();
						tError.moduleName = "ImportContPlanBL";
						tError.functionName = "dealData";
						tError.errorMessage = "请先添加保险套餐下的险种"
								+ mLDPlanRiskSet.get(i).getRiskCode() + "！";
						this.mErrors.addOneError(tError);
						return false;

					} else {
						// 把GRPPOLNO保存到险种责任要素里mLCContPlanDutyParam。setgrppolno
						logger.debug("\\\\\\\\\\\\:"
								+ mLCContPlanDutyParamSet.size());
						for (int j = 1; j <= mLCContPlanDutyParamSet.size(); j++) {
							logger.debug("\\\\\\\\\\\\:"
									+ mLCContPlanDutyParamSet.get(j)
											.getRiskCode());
							logger.debug("\\\\\\\\\\\\:"
									+ mLCGrpPolSet.get(1).getRiskCode());
							if (mLCContPlanDutyParamSet.get(j).getRiskCode()
									.equals(mLCGrpPolSet.get(1).getRiskCode())) {
								mLCContPlanDutyParamSet.get(j).setGrpPolNo(
										mLCGrpPolSet.get(1).getGrpPolNo());
							}
						}
					}
				}
			}

			// 循环保险计划责任要素值集合，添加了主险校验
			for (int i = 1; i <= this.mLCContPlanDutyParamSet.size(); i++) {
				if (RiskCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
						.getRiskCode()) != 0
						|| MainRiskCode.compareTo(this.mLCContPlanDutyParamSet
								.get(i).getMainRiskCode()) != 0) {
					// 当险种更换后，校验要素个数，且第一次进入时不校验
					if (RiskCode.compareTo("") != 0
							&& k != tLMRiskDutyFactorSet.size()) {
						// @@错误处理
						logger.debug("失败1");
						CError tError = new CError();
						tError.moduleName = "ImportContPlanBL";
						tError.functionName = "dealData";
						tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode
								+ "责任要素信息有误！";
						this.mErrors.addOneError(tError);
						return false;
					}
					// 险种更换，则责任编码初始化
					RiskCode = this.mLCContPlanDutyParamSet.get(i)
							.getRiskCode();
					MainRiskCode = this.mLCContPlanDutyParamSet.get(i)
							.getMainRiskCode();
					DutyCode = "";

					// 准备保单险种保险计划表数据，只与险种和计划挂钩，不关心责任、要素信息
					mLCContPlanRiskSchema = new LCContPlanRiskSchema();
					mLCContPlanRiskSchema.setPlanType(this.mLCContPlanSchema
							.getPlanType());
					mLCContPlanRiskSchema
							.setGrpContNo(this.mLCContPlanDutyParamSet.get(i)
									.getGrpContNo());
					mLCContPlanRiskSchema
							.setProposalGrpContNo(this.mLCContPlanDutyParamSet
									.get(i).getProposalGrpContNo());
					mLCContPlanRiskSchema
							.setRiskCode(this.mLCContPlanDutyParamSet.get(i)
									.getRiskCode());
					mLCContPlanRiskSchema
							.setRiskVersion(this.mLCContPlanDutyParamSet.get(i)
									.getRiskVersion());
					mLCContPlanRiskSchema
							.setContPlanCode(this.mLCContPlanDutyParamSet
									.get(i).getContPlanCode());
					mLCContPlanRiskSchema
							.setContPlanName(this.mLCContPlanDutyParamSet
									.get(i).getContPlanName());
					mLCContPlanRiskSchema
							.setMainRiskCode(this.mLCContPlanDutyParamSet
									.get(i).getMainRiskCode());
					mLCContPlanRiskSchema
							.setMainRiskVersion(this.mLCContPlanDutyParamSet
									.get(i).getMainRiskVersion());
					mLCContPlanRiskSchema
							.setOperator(this.mGlobalInput.Operator);
					mLCContPlanRiskSchema.setMakeDate(PubFun.getCurrentDate());
					mLCContPlanRiskSchema.setMakeTime(PubFun.getCurrentTime());
					mLCContPlanRiskSchema
							.setModifyDate(PubFun.getCurrentDate());
					mLCContPlanRiskSchema
							.setModifyTime(PubFun.getCurrentTime());
					mLCContPlanRiskSet.add(mLCContPlanRiskSchema);

					// 新险种的第一个责任项校验
					if (DutyCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 责任更换
						DutyCode = this.mLCContPlanDutyParamSet.get(i)
								.getDutyCode();
						// 如果要素信息是Deductible则，需要对要素信息做一定处理
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ DutyCode + "'";
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(lssql);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLCContPlanSchema.getPlanType().compareTo("0") == 0) {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ DutyCode + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ DutyCode + "'";
						}
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(mSql);
					}
					k = 1;
				} else {
					// 同一险种下的不同责任校验
					if (DutyCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 同一险种下，责任更换时，校验要素个数
						if (k != tLMRiskDutyFactorSet.size()) {
							// @@错误处理
							logger.debug("失败2");
							CError tError = new CError();
							tError.moduleName = "ImportContPlanBL";
							tError.functionName = "dealData";
							tError.errorMessage = "险种" + RiskCode + "下的"
									+ DutyCode + "责任要素信息有误！";
							this.mErrors.addOneError(tError);
							return false;
						}
						// 责任更换
						DutyCode = this.mLCContPlanDutyParamSet.get(i)
								.getDutyCode();
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ DutyCode + "'";
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(lssql);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						k = 1;
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLCContPlanSchema.getPlanType().compareTo("0") == 0) {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ DutyCode + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ DutyCode + "'";
						}
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(mSql);
					} else {
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ DutyCode + "'";
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(lssql);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						k += 1;
					}
				}
			}
			// if (k != tLMRiskDutyFactorSet.size()) {
			// // @@错误处理
			// logger.debug("失败3");
			// CError tError = new CError();
			// tError.moduleName = "ImportContPlanBL";
			// tError.functionName = "dealData";
			// tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode +
			// "责任要素信息有误！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// 准备保单保险计划表数据
			// mLCContPlanSchema = new LCContPlanSchema();
			// mLCContPlanSchema.setGrpContNo(this.mLCContPlanDutyParamSet.get(1).
			// getGrpContNo());
			// mLCContPlanSchema.setProposalGrpContNo(this.mLCContPlanDutyParamSet.
			// get(1).getProposalGrpContNo());
			// mLCContPlanSchema.setContPlanCode(this.mLCContPlanDutyParamSet.get(
			// 1).
			// getContPlanCode());
			// mLCContPlanSchema.setContPlanName(this.mLCContPlanDutyParamSet.get(
			// 1).
			// getContPlanName());
			// mLCContPlanSchema.setPlanType(this.mPlanType); //计划类别，此处全部为固定计划
			// mLCContPlanSchema.setPlanSql(this.mPlanSql); //分类说明
			// mLCContPlanSchema.setPeoples3(this.mPeoples3);//可保人数
			mLCContPlanSchema.setOperator(this.mGlobalInput.Operator);
			mLCContPlanSchema.setMakeDate(PubFun.getCurrentDate());
			mLCContPlanSchema.setMakeTime(PubFun.getCurrentTime());
			mLCContPlanSchema.setModifyDate(PubFun.getCurrentDate());
			mLCContPlanSchema.setModifyTime(PubFun.getCurrentTime());
			mLCContPlanSet.add(mLCContPlanSchema);
		}
		// 由于删除操作可能没有multine数据，因此需要一些特殊参数，并且根据参数查询要删除的数据
		if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
			// 准备保单保险计划的删除数据
			LCContPlanDB tLCContPlanDB = new LCContPlanDB();
			tLCContPlanDB.setGrpContNo(this.mLCContPlanSchema.getGrpContNo());
			tLCContPlanDB.setContPlanCode(this.mLCContPlanSchema
					.getContPlanCode());
			tLCContPlanDB.setPlanType(this.mLCContPlanSchema.getPlanType());
			tLCContPlanDB.setProposalGrpContNo(this.mLCContPlanSchema
					.getProposalGrpContNo());
			mLCContPlanSet = tLCContPlanDB.query();
			// 准备保单险种保险计划的删除数据
			LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
			tLCContPlanRiskDB.setGrpContNo(this.mLCContPlanSchema
					.getGrpContNo());
			tLCContPlanRiskDB.setContPlanCode(this.mLCContPlanSchema
					.getContPlanCode());
			tLCContPlanRiskDB.setPlanType(this.mLCContPlanSchema.getPlanType());
			tLCContPlanRiskDB.setProposalGrpContNo(this.mLCContPlanSchema
					.getProposalGrpContNo());
			mLCContPlanRiskSet = tLCContPlanRiskDB.query();
			if (mLCContPlanSet.size() > 0 && mLCContPlanRiskSet.size() > 0) {
				if (mLCContPlanSet.get(1).getPlanType().equals("1")) {
					for (int i = 1; i <= mLCContPlanRiskSet.size(); i++) {

						LCContPlanRiskDB ttLCContPlanRiskDB = new LCContPlanRiskDB();
						ttLCContPlanRiskDB.setGrpContNo(this.mLCContPlanSchema
								.getGrpContNo());
						ttLCContPlanRiskDB.setRiskCode(mLCContPlanRiskSet
								.get(i).getRiskCode());
						int planRiskCount = ttLCContPlanRiskDB.getCount();
						if (ttLCContPlanRiskDB.mErrors.needDealError()) {
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "GroupRiskBL";
							tError.functionName = "deleteData";
							tError.errorMessage = "查询保险计划时失败!";
							this.mErrors.addOneError(tError);
							return false;

						}
						if (planRiskCount == 1) {
							LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
							tLCGrpPolDB.setGrpContNo(this.mLCContPlanSchema
									.getGrpContNo());
							tLCGrpPolDB.setRiskCode(mLCContPlanRiskSet.get(i)
									.getRiskCode());
							LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
							if (tLCGrpPolSet == null
									|| tLCGrpPolSet.size() <= 0) {
								// @@错误处理
								CError tError = new CError();
								tError.moduleName = "GrpContBL";
								tError.functionName = "dealData";
								tError.errorMessage = "请您确认：要删除的集体险种代码传入错误!";
								this.mErrors.addOneError(tError);
								return false;
							}
							mLCGrpPolSet.add(tLCGrpPolSet.get(1));
						}
					}
				}
			}
			// 准备保险计划责任要素值的删除数据
			LCContPlanDutyParamDB tLCContPlanDutyParamDB = new LCContPlanDutyParamDB();
			tLCContPlanDutyParamDB.setGrpContNo(this.mLCContPlanSchema
					.getGrpContNo());
			tLCContPlanDutyParamDB.setContPlanCode(this.mLCContPlanSchema
					.getContPlanCode());
			tLCContPlanDutyParamDB.setPlanType(this.mLCContPlanSchema
					.getPlanType());
			tLCContPlanDutyParamDB.setProposalGrpContNo(this.mLCContPlanSchema
					.getProposalGrpContNo());
			mLCContPlanDutyParamSet = tLCContPlanDutyParamDB.query();
		}

		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		this.mGlobalInput = ((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mTransferData = ( TransferData ) cInputData.getObjectByObjectName("TransferData", 0);
		this.mGrpContNo = (String)this.mTransferData.getValueByName("GrpContNo");
		this.mProposalGrpContNo = (String)this.mTransferData.getValueByName("ProposalGrpContNo");
		this.mAskPriceNo = (String)this.mTransferData.getValueByName("AskpriceNo");
		this.mAskNo = (String)this.mTransferData.getValueByName("AskNo");
		
		//lcgrpcont.ProposalGrpContNo 对应 lcgrppol.prtno
		this.mGrpPolNo = mExeSQL.getOneValue("select grppolno from lcgrppol where grpcontno ='"+ mGrpContNo +"' and prtno = '"+ mProposalGrpContNo + "' "   );
		
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start ImportContPlanBLQuery Submit...");
		LCContPlanDB tLCContPlanDB = new LCContPlanDB();
		tLCContPlanDB.setSchema(this.mLCContPlanSchema);
		this.mLCContPlanSet = tLCContPlanDB.query();
		this.mResult.add(this.mLCContPlanSet);
		logger.debug("End ImportContPlanBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCContPlanDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContPlanDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mResult.clear();
			this.mResult.add(this.mLCContPlanSet);
			this.mResult.add(this.mLCContPlanRiskSet);
			this.mResult.add(this.mLCContPlanDutyParamSet);
			//this.mInputData.add(this.mLCGrpPolSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
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
	
	/**
	 * 导入数据
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean ImportLCContPlan() {
		boolean tReturn = true;
		logger.debug("Start Importing Data...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			LCContPlanDBSet tLCContPlanDBSet = new LCContPlanDBSet(conn);
			tLCContPlanDBSet.set( mLCContPlanSet);

			LCContPlanDB tLCContPlanDB = new LCContPlanDB(conn);
			tLCContPlanDB.setContPlanCode(tLCContPlanDBSet.get(1)
					.getContPlanCode());
			tLCContPlanDB.setGrpContNo(tLCContPlanDBSet.get(1).getGrpContNo());
			LCContPlanSet tLCContPlanSet = tLCContPlanDB.query();
			if (tLCContPlanSet.size() == 0) {
				if (!tLCContPlanDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ImportContPlanBL";
					tError.functionName = "saveData";
					tError.errorMessage = "LCContPlan 数据保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			LCContPlanRiskDBSet tLCContPlanRiskDBSet = new LCContPlanRiskDBSet(
					conn);
			tLCContPlanRiskDBSet.set( mLCContPlanRiskSet );
			if (!tLCContPlanRiskDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ImportContPlanBL";
				tError.functionName = "saveData";
				tError.errorMessage = " LCContPlanRisk 数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanDutyParamDBSet tLCContPlanDutyParamDBSet = new LCContPlanDutyParamDBSet(
					conn);
			tLCContPlanDutyParamDBSet.set( mLCContPlanDutyParamSet );
			if (!tLCContPlanDutyParamDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ImportContPlanBL";
				tError.functionName = "saveData";
				tError.errorMessage = " LCContPlanDutyParam 数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ImportContPlanBL";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			tReturn = false;
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
		}
		return tReturn;
	}
}
