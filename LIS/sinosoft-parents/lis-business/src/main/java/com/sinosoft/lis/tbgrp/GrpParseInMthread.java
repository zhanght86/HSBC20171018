/*
 * @(#)ParseGuideIn.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.MultThreadErrorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.tb.ContPlanQryBL;
import com.sinosoft.lis.tb.ProposalBL;
import com.sinosoft.lis.vbl.LCBnfBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.MultThreadErrorSet;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/*
 * <p>Title: Web业务系统</p> <p>ClassName:ParserGuidIn </p> <p>Description:
 * 磁盘投保入口类文件 </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: Sinosoft
 * </p> @author：WUJS @version：6.0 @CreateDate：2004-12-13
 */

public class GrpParseInMthread  extends CovBase {
private static Logger logger = Logger.getLogger(GrpParseInMthread.class);
	// @Fields
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	public CErrors logErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */

	private GlobalInput mGlobalInput = new GlobalInput();





	private String mGrpContNo;


	private String mBatchNo = "";
	private String mPrtNo = "";
	private String mContNo = "";
	private String mEdorType;
	private String mEdorTypeCal;
	private String mBQFlag;
	private String mSavePolType;
	private String mEdorValiDate;
	private LCPolBL mainPolBL = new LCPolBL();
	private String mPolNo ="";

	private GrpPolImpInfo m_GrpPolImpInfo = new GrpPolImpInfo();

	private boolean mRelation=false;
	private String mMainSequenceNo = "1";
	private String mRelationToRisk = "";
	private String mRiskCode[]=null;
	private VData mInputDataNew;
	// @Constructors
	public GrpParseInMthread() {
	}


	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (VData) tObject;
	}
	
	public void run() {
		boolean signResult = true;
		try {
		
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			tLCInsuredSet = (LCInsuredSet)this.mInputDataNew.getObjectByObjectName("LCInsuredSet",0);
			VData tPolVData = (VData)this.mInputDataNew.getObjectByObjectName("VData",0);
			TransferData tTransferData = (TransferData)this.mInputDataNew.getObjectByObjectName("TransferData",0);
			String tContID = (String)tTransferData.getValueByName("CONTID");
			mBatchNo = (String)tTransferData.getValueByName("BatchNo");
			mBQFlag = (String)tTransferData.getValueByName("BQFlag");
			mEdorType = (String)tTransferData.getValueByName("EdorType");
			mEdorTypeCal = (String)tTransferData.getValueByName("EdorTypeCal");
			mEdorValiDate = (String)tTransferData.getValueByName("EdorValiDate");
			mSavePolType = (String)tTransferData.getValueByName("SavePolType");

			LCBnfSet tLCBnfSet = new LCBnfSet();
			tLCBnfSet = (LCBnfSet)this.mInputDataNew.getObjectByObjectName("LCBnfSet",0);
			LCInsuredRelatedSet tLCInsuredRelatedSet  = (LCInsuredRelatedSet)this.mInputDataNew.getObjectByObjectName("LCInsuredRelatedSet",0);
			LCGrpContSchema tLCGrpContSchema = (LCGrpContSchema)this.mInputDataNew.getObjectByObjectName("LCGrpContSchema",0);
			mGrpContNo = tLCGrpContSchema.getGrpContNo();

			mPrtNo = tLCGrpContSchema.getPrtNo();
			mGlobalInput = (GlobalInput)this.mInputDataNew.getObjectByObjectName("GlobalInput",0);
			m_GrpPolImpInfo = new GrpPolImpInfo();
			m_GrpPolImpInfo.addContPolData(tContID, tPolVData);
			if (!m_GrpPolImpInfo.init(mBatchNo, this.mGlobalInput,
					tTransferData, tLCInsuredSet, tLCBnfSet,
					tLCInsuredRelatedSet, tLCGrpContSchema)) {

				CError.buildErr(this, "合同ID号为" + tContID+ "的信息初始化失败!");
			}else{
				this.DiskContImport(tContID);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(this.mErrors.getErrorCount()>0)
		{
			MultThreadErrorSet tMultThreadErrorSet =new MultThreadErrorSet();
			MMap tMap =new MMap();
			for(int i=0;i<mErrors.getErrorCount();i++){
				//将个单签单的错误保存到数据库中 通过传进来的Batch_No确定本次签单信息
				MultThreadErrorDB tMultThreadErrorDB=new MultThreadErrorDB();
				String tSerialNo =PubFun1.CreateMaxNo("SerialNo", 20);
				tMultThreadErrorDB.setSerino(tSerialNo);
				tMultThreadErrorDB.setBatchNo(mBatchNo);
				tMultThreadErrorDB.setDealPos("2");//团险磁盘导入
				tMultThreadErrorDB.setErrCode(mErrors.getError(i).errorNo);
				tMultThreadErrorDB.setErrType(mErrors.getError(i).errorType);
				tMultThreadErrorDB.setPrtNo(mGrpContNo);
				tMultThreadErrorDB.setReason(mErrors.getError(i).errorMessage);
				tMultThreadErrorDB.setOperator(mGlobalInput.Operator);
				tMultThreadErrorDB.setMakeDate(PubFun.getCurrentDate());
				tMultThreadErrorDB.setMakeTime(PubFun.getCurrentTime());
				tMultThreadErrorSet.add(tMultThreadErrorDB.getSchema());
			}
			tMap.put(tMultThreadErrorSet, "INSERT");
			PubSubmit pubSubmit = new PubSubmit();
			VData tVData =new VData();
			tVData.add(tMap);
			signResult = pubSubmit.submitData(tVData, "");
			if (!signResult) {
				if (pubSubmit.mErrors.getErrorCount() > 0) {
					this.mErrors.copyAllErrors(pubSubmit.mErrors);
				} else {
					CError.buildErr(this, "错误信息保存有误!");

				}
			}
			
		}
		
		this.close();
		
	}

	

	/**
	 * 提交到proposalBL ,做数据生成和准备
	 * 
	 * @param tNewVData
	 *            VData
	 * @return boolean
	 */
	private MMap submiDatattoProposalBL(VData tNewVData) {
		// tNewVData 里面还有一些要创建的信息，因此需要重新获得
		MMap map = (MMap) tNewVData.getObjectByObjectName("MMap", 0);
		
		LDPersonSchema tLDPersonSchema = (LDPersonSchema) tNewVData.getObjectByObjectName("LDPersonSchema", 0);
		LLAccountSchema tLLAccountSchema = (LLAccountSchema) tNewVData.getObjectByObjectName("LLAccountSchema", 0);
		
		if (map == null)
			map = new MMap();
		String strID = (String) ((TransferData) tNewVData.getObjectByObjectName("TransferData", 0)).getValueByName("ID");
		String PolKey = (String) ((TransferData) tNewVData.getObjectByObjectName("TransferData", 0)).getValueByName("PolKey");
		String contId = m_GrpPolImpInfo.getContKey(PolKey);
		ProposalBL tProposalBL = new ProposalBL();
		if (!tProposalBL.PrepareSubmitData(tNewVData, "INSERT||PROPOSAL")) {
			CError.buildErr(this, "合同["+contId+"]错误:");
			this.mErrors.copyAllErrors(tProposalBL.mErrors);
			return null;
		} else {
			VData pData = tProposalBL.getSubmitResult();
			if (pData == null) {
				CError.buildErr(this, "保单提交计算失败!");
				return null;
			}

			LCPolSchema rPolSchema = (LCPolSchema) pData.getObjectByObjectName(
					"LCPolSchema", 0);
			LCPremBLSet rPremBLSet = (LCPremBLSet) pData.getObjectByObjectName(
					"LCPremBLSet", 0);
			if (rPremBLSet == null) {
				CError.buildErr(this, "保单提交计算保费项数据准备有误!");
				return null;
			}
			LCPremSet rPremSet = new LCPremSet();
			for (int i = 1; i <= rPremBLSet.size(); i++) {
				rPremSet.add(rPremBLSet.get(i));
			}

			LCDutyBLSet rDutyBLSet = (LCDutyBLSet) pData.getObjectByObjectName(
					"LCDutyBLSet", 0);
			if (rDutyBLSet == null) {
				CError.buildErr(this, "保单提交计算责任项数据准备有误!");
				return null;
			}
			LCDutySet rDutySet = new LCDutySet();
			for (int i = 1; i <= rDutyBLSet.size(); i++) {
				rDutySet.add(rDutyBLSet.get(i));
			}

			LCGetBLSet rGetBLSet = (LCGetBLSet) pData.getObjectByObjectName(
					"LCGetBLSet", 0);
			if (rGetBLSet == null) {
				CError.buildErr(this, "保单提交计算给付项数据准备有误!");
				return null;
			}
			LCGetSet rGetSet = new LCGetSet();
			for (int i = 1; i <= rGetBLSet.size(); i++) {
				rGetSet.add(rGetBLSet.get(i));
			}

			LCBnfBLSet rBnfBLSet = (LCBnfBLSet) pData.getObjectByObjectName(
					"LCBnfBLSet", 0);
			LCBnfSet rBnfSet = new LCBnfSet();
			if (rBnfBLSet != null) {
				for (int i = 1; i <= rBnfBLSet.size(); i++) {
					rBnfSet.add(rBnfBLSet.get(i));
				}
			}
			LCInsuredRelatedSet tLCInsuredRelatedSet = (LCInsuredRelatedSet) pData
					.getObjectByObjectName("LCInsuredRelatedSet", 0);

			// 更新个单合同金额相关，更新的时候同时更新缓存中的数据
			LCContSchema tLCContSchema = m_GrpPolImpInfo.findLCContfromCache(contId);
			if (tLCContSchema == null) {
				CError.buildErr(this, "查找合同[" + contId + "]失败");
				return null;
			}
			mContNo = tLCContSchema.getContNo();
			tLCContSchema.setPrem(PubFun.setPrecision(tLCContSchema.getPrem()
					+ rPolSchema.getPrem(), "0.00"));
			tLCContSchema.setAmnt(tLCContSchema.getAmnt()
					+ rPolSchema.getAmnt());
			tLCContSchema.setSumPrem(tLCContSchema.getSumPrem()
					+ rPolSchema.getSumPrem());
			tLCContSchema.setMult(tLCContSchema.getMult()
					+ rPolSchema.getMult());
			LCContSchema tContSchema = new LCContSchema();
			tContSchema.setSchema(tLCContSchema);
			LCContSet tContSet = new LCContSet();
			tContSet.add(tContSchema);

			LCInsuredSchema tLCInsuredSchema  = m_GrpPolImpInfo.findInsuredfromCache(contId);
			
			/* wangxw  20100926签入
			 * 因为在测试被保人导入的时候，发现 LCCont 和 LCInsured 表没有插入数据，根据同事（佟盟）的建议，
			 * 暂时在这里添加两个表的插入，不保证后续还有其他问题。
			 */
			map.put(tContSet, "DELETE&INSERT");
			map.put(tLCInsuredSchema, "DELETE&INSERT");
			
			map.put(rPolSchema, "INSERT");
			map.put(rPremSet, "INSERT");
			map.put(rDutySet, "INSERT");
			map.put(rGetSet, "INSERT");
			map.put(rBnfSet, "INSERT");
			
			if(tLDPersonSchema!=null)
			{
				map.put(tLDPersonSchema, "DELETE&INSERT");
			}
//			if(tLLAccountSchema!=null&&tLLAccountSchema.getContNo()!=null&&!"".equals(tLLAccountSchema.getContNo()))
//			{
//				map.put(tLLAccountSchema, "DELETE&INSERT");	
//			}
			map.put(tLCInsuredRelatedSet, "INSERT");
			Date date = new Date();
			Random rd = new Random(date.getTime());
			long u = rd.nextLong();
			// 个人单需要重新统计个单合同人数
			if ("0".equals(tLCContSchema.getPolType())) {
				String s0 = " update lccont set peoples=(select count(distinct insuredno) from lcpol where '"
						+ u
						+ "'='"
						+ u
						+ "' and contno='"+ mContNo+ "')"
						+ " where contno='"+ mContNo + "'" + " and PolType = '0' ";
				map.put(s0, "UPDATE");
			}

			// 缓存保存的主险保单信息
			m_GrpPolImpInfo.cachePolInfo(strID, rPolSchema);
		}

		return map;
	}

	private boolean batchSave(MMap map) {
		PubSubmit pubSubmit = new PubSubmit();
		VData sData = new VData();
		sData.add(map);
		boolean tr = pubSubmit.submitData(sData, "");

		if (!tr) {
			if (pubSubmit.mErrors.getErrorCount() > 0) {
				// 错误回退
				mErrors.copyAllErrors(pubSubmit.mErrors);
				pubSubmit.mErrors.clearErrors();
			} else {
				CError.buildErr(this, "保存数据库的时候失败！");
			}
			return false;
		}
		return true;
	}

	/**
	 * 以合同为单位做磁盘投保,对一个合同所有保单，一旦有一个险种保单导入失败，则要么失败
	 * 
	 * @return boolean
	 */
	private boolean DiskContImport(String contIndex) {
		LCInsuredSchema insuredSchema = null;
		LCInsuredSet tInsuredSet = m_GrpPolImpInfo.getLCInsuredSet();
		MMap subMap = null; // 提交结果集缓存
		boolean state = true; // 导入状态，
		boolean saveState = true;
		boolean somedata = true;//是否有可保存的数据
		String InType ="0"; //1-保障计划  0-非计划导入
		String logRiskCode = "";
		String insuredIndex = "";

		subMap = null;
		mContNo = "";
		LCGrpImportLogSchema logSchema = m_GrpPolImpInfo.getLogInfo(mBatchNo,contIndex);
		if (logSchema != null && "0".equals(logSchema.getErrorState())) {
			return true;
		}

		state = true;
		// 先查找有保障计划的导入
		for (int i = 1; i <= tInsuredSet.size(); i++) {
			logger.debug("先查找有保障计划的导入" + i); // 初始化，避免重复使用

			logRiskCode = "";
			if (state == false)
				break;
			insuredSchema = tInsuredSet.get(i);
			insuredSchema.setSequenceNo(String.valueOf(i));
			// 不是同一个合同的，跳出
			if (!insuredSchema.getContNo().equals(contIndex))
				continue;
			// 不是保障计划的，跳出
			if ("".equals(StrTool.cTrim(insuredSchema.getContPlanCode()))) {
				continue;
			}

			logger.debug("是保障计划");
			InType ="1"; //导入标志

			// 解析XML时借用PrtNo保存被保人序号
			insuredIndex = insuredSchema.getPrtNo();
			if ("".equals(StrTool.cTrim(insuredIndex))) {
				CError.buildErr(this, "第[" + i + "]个被保人ID没有填写");
				state = false;
				break; // break for insured
			}

			// 保障计划代码不空才导入
			LCContPlanSchema tContPlanSchema = m_GrpPolImpInfo
					.findLCContPlan(insuredSchema.getContPlanCode());
			if (tContPlanSchema == null) {
				CError.buildErr(this, "被保险人[" + i + "]保单保险计划["+ insuredSchema.getContPlanCode() + "]查找失败");
				m_GrpPolImpInfo.mErrors.copyAllErrors(this.mErrors);
				state = false;
				break; // break for insured
			}


			// 险种保险计划
			LCContPlanRiskSet tLCContPlanRiskSet = m_GrpPolImpInfo
					.findLCContPlanRiskSet(insuredSchema.getContPlanCode());

			if (tLCContPlanRiskSet == null || tLCContPlanRiskSet.size() <= 0) {
				CError.buildErr(this, "被保险人[" + i + "]保单险种保险计划["
						+ insuredSchema.getContPlanCode() + "]查找失败");
				state = false;
				break; // break for insured
			}

			// 对保险险种计划排序，确保主险在前面
			LCContPlanRiskSet mainPlanRiskSet = new LCContPlanRiskSet();
			LCContPlanRiskSet subPlanRiskSet = new LCContPlanRiskSet();
			LCContPlanRiskSchema contPlanRiskSchema = null;
			for (int t = 1; t <= tLCContPlanRiskSet.size(); t++) {
				contPlanRiskSchema = tLCContPlanRiskSet.get(t);
				if (contPlanRiskSchema.getRiskCode().equals(
						contPlanRiskSchema.getMainRiskCode())) {
					mainPlanRiskSet.add(contPlanRiskSchema);
				} else {
					subPlanRiskSet.add(contPlanRiskSchema);
				}

			}
			mainPlanRiskSet.add(subPlanRiskSet);
			// 根据险种计划生成险种保单相关信息
			for (int j = 1; j <= mainPlanRiskSet.size(); j++) {
				logRiskCode = mainPlanRiskSet.get(j).getRiskCode();
				// 生成保单，责任等信息
				VData tData = prepareContPlanData(contIndex, insuredIndex,
						insuredSchema, mainPlanRiskSet.get(j));

				if (tData == null) {

					state = false;
					break; // break for conplarisk
				}
				// 提交生成数据
				MMap oneRisk = submiDatattoProposalBL(tData);
				if (oneRisk == null) {
					// 失败
					state = false;
					break; // break for insured
				} else {
					// 成功
					if (subMap == null)
						subMap = oneRisk;
					else
						subMap.add(oneRisk);
				}
			}

		}
		// 计划导入准备期间发生错误
		if (state == false) {

			saveState = false;

			return false;
		} else {
			if("0".equals(InType)){
				logger.debug("无计划导入....");
				// 无计划导入
				// 导入险种保单，先根据合同ID查找到所有相同合同id下的所有保单数据，再一张一张保单信息导入
				VData tContPolData = (VData) m_GrpPolImpInfo.getContPolData(contIndex);
				if (tContPolData != null) {
					
					LCPolSchema tLCPolSchema = null;
					VData tPolData = null;
					for (int u = 0; u < tContPolData.size(); u++) {
						tPolData = (VData) tContPolData.get(u);
						if (state == false)
							break;
						List riskList = new ArrayList(); 
						for (int i = 0; i < tPolData.size(); i++) {
							// 初始化，避免重复使用
							logRiskCode = "";
							if (state == false)
								break;
							VData onePolData = (VData) tPolData.get(i);
							if (onePolData == null)
								continue;
							// 备份原ID号
							tLCPolSchema = (LCPolSchema) onePolData.getObjectByObjectName("LCPolSchema", 0);
							logRiskCode = tLCPolSchema.getRiskCode();
							if(riskList.contains(logRiskCode))
							{
								CError tError = new CError();
								tError.moduleName = "ContBL";
								tError.functionName = "submitData";
								tError.errorMessage = "合同ID号为" + contIndex+ "险种保单有相同的数据!";
								this.mErrors.addOneError(tError);
								state = false;
							}else{
								riskList.add(logRiskCode);
							}
							
							insuredIndex = tLCPolSchema.getInsuredNo();
							contIndex = tLCPolSchema.getContNo();
							
							
							VData tprepareData = this.prepareProposalBLData(onePolData);
							if (tprepareData == null) {
								state = false;
							} else {
								MMap tMap = null;
								if(mRelation){
									//是连带被保人 则只提交lcinsuredrelated和lcinsured两个表
									tMap =this.lcinsuredRealtedDataSubmit(tprepareData);
								}else{
									tMap = this.submiDatattoProposalBL(tprepareData);
								}
								if (tMap == null) {
									state = false;
								}
								if (subMap == null){
									subMap = tMap;
								}else{
									subMap.add(tMap);
								}
							}
						}
					}
					if(tPolData.size()<1&&"".equals(StrTool.cTrim(insuredSchema.getContPlanCode()))){
						//当没有录入保障计划和险种信息时，没有提交数据， 应提示保存失败 add at 2008-12-18
						somedata = false;
						CError.buildErr(this, "没有录入险种信息!");
					}
				}
			}
			}
		if (state == false) {


			saveState = false;
			return false;
			// continue;
		}

		boolean bs = true;

		if (state && subMap != null && subMap.keySet().size() > 0) {
			//提交所有数据
			MMap logMap = m_GrpPolImpInfo.logSucc(mBatchNo, mGrpContNo,
					contIndex, mPrtNo, mContNo, mGlobalInput);
			subMap.add(logMap);
			bs = batchSave(subMap);
		}

		if (!bs || !state ||!somedata) {
			// 合同导入没有成功，需要回退缓存
			m_GrpPolImpInfo.removeCaceh(contIndex);
			saveState = false;
		} else {
			logger.debug("合同[" + contIndex + "]导入成功！！");
		}
		return saveState;
	}

	private MMap lcinsuredRealtedDataSubmit(VData tRelationData){
		MMap tMap = new MMap();
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		Reflections tReflections = new Reflections();
		LDPersonSet tLDPersonSet= new LDPersonSet();
		LDPersonDB tLDPersonDB =new LDPersonDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredRelatedSet tLCInsuredRelatedSet = (LCInsuredRelatedSet) tRelationData.getObjectByObjectName("LCInsuredRelatedSet", 0);
		if(tLCInsuredRelatedSet.size()>0){
			mPolNo =tLCInsuredRelatedSet.get(1).getPolNo();
		}else{
			CError.buildErr(this, "没有连带被保人信息，而又要提交连带被保人！请确认错误原因。");
		}
		LCInsuredSchema tLCInsuredSchema = (LCInsuredSchema) tRelationData.getObjectByObjectName("LCInsuredSchema", 0);
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tReflections.transFields(tLDPersonSchema, tLCInsuredSchema);
		tLDPersonSchema.setOperator(mGlobalInput.Operator);
		tLDPersonSchema.setMakeDate(tNowDate);
		tLDPersonSchema.setMakeTime(tNowTime);
		tLDPersonSchema.setModifyDate(tNowDate);
		tLDPersonSchema.setCustomerNo(tLCInsuredSchema.getInsuredNo());
		tLDPersonSet.add(tLDPersonSchema);
		tLDPersonDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
		if(!tLDPersonDB.getInfo()){
			tMap.put(tLDPersonSet, "INSERT");
		}
		//连带被保人的合同号应该和其主被保人合同号相同
		String tMainContNo = queryLCInsuRelaNo(mBatchNo,"ContNo");
		tLCInsuredSchema.setContNo(tMainContNo);
		tLCInsuredSet.add(tLCInsuredSchema);
		tMap.put(tLCInsuredRelatedSet, "INSERT");
		tMap.put(tLCInsuredSet, "INSERT");
		return tMap;
	}
	/*
	 * 查询主被保人的合同号
	 * */
	private String queryLCInsuRelaNo(String tBatchNo,String tFlag){
    	String No = "";
    	String tStrSQL = "";
    	ExeSQL tExeSQL = new ExeSQL();
    	if(tFlag.equals("ContNo")){
//    	tStrSQL =  "select contno from lcgrpimportlog where batchno = '"+tBatchNo+"'  and contno in (select contno" +
//    	" from lcpol where polno in (select polno from lcinsuredrelated  where maincustomerno = customerno" +
//    	" and sequenceno='"+mMainSequenceNo+"'))";
    		//通过已经得到的polno查询到保单的合同号码
    		tStrSQL ="select contno from lcpol where polno ='"+mPolNo+"'";
    	}else if (tFlag.equals("PolNo")){
    		tStrSQL = "select polno from lcpol where contno = "+
    		" (select contno from lcgrpimportlog where batchno = '"+tBatchNo+"'  and contno in (select contno" +
    		" from lcpol where polno in (select polno from lcinsuredrelated  where maincustomerno = customerno" +
    		" and sequenceno='"+mMainSequenceNo+"'))) and riskcode='"+mRelationToRisk+"'";
    	}
    	 No = tExeSQL.getOneValue(tStrSQL);
    	return No;
    }
	/**
	 * 按保险计划导入的数据准备
	 * 
	 * @param contIndex
	 *            String 合同Id
	 * @param insuredIndex
	 *            String 被保险人Id
	 * @param insuredSchema
	 *            LCInsuredSchema 主被保险人信息
	 * @param tLCContPlanRiskSchema
	 *            LCContPlanRiskSchema 保险险种计划
	 * @return VData
	 */
	private VData prepareContPlanData(String contIndex, String insuredIndex,
			LCInsuredSchema insuredSchema,
			LCContPlanRiskSchema tLCContPlanRiskSchema) {
		LLAccountSchema tLLAccountSchema = new LLAccountSchema();
		LDPersonSchema  tLDPersonSchema  =new LDPersonSchema();
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();
		LCGrpPolSchema sLCGrpPolSchema = m_GrpPolImpInfo.findLCGrpPolSchema(strRiskCode);

		if (sLCGrpPolSchema == null) {
			buildError("prepareContPlanData", strRiskCode + "险种对应的集体投保单没有找到!");
			return null;
		}

		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);

		String PolKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
				strRiskCode);

		boolean contFlag = false;
		LCContSchema contSchema = new LCContSchema();
		contSchema = m_GrpPolImpInfo.findLCContfromCache(contIndex);
		if (contSchema != null
				&& !"".equals(StrTool.cTrim(contSchema.getContNo()))) { // 合同已经创建过
			contFlag = true;
		}
		logger.debug("-contFlag-" + contFlag);
		// 提交生成被保险人号,创建合同的sql
		VData tContData = m_GrpPolImpInfo.getContData(contIndex, insuredIndex,
				tLCGrpPolSchema);
		if (tContData == null) {
			logger.debug("创建合同信息时出错!");
			return null;
		}
		MMap contMap = (MMap) tContData.getObjectByObjectName("MMap", 0);
		tmpMap.add(contMap);
		insuredSchema = (LCInsuredSchema) tContData.getObjectByObjectName(
				"LCInsuredSchema", 0);
		tLLAccountSchema = (LLAccountSchema) tContData.getObjectByObjectName(
				"LLAccountSchema", 0);
		
		tLDPersonSchema  =(LDPersonSchema)tContData.getObjectByObjectName("LDPersonSchema", 0);
		
		tNewVData.add(insuredSchema);
		tNewVData.add(tLLAccountSchema); // added by wanglei
		tNewVData.add(tLDPersonSchema);
		contSchema = (LCContSchema) tContData.getObjectByObjectName(
				"LCContSchema", 0);
		logger.debug("====contFlag==========" + contFlag);
		if (!contFlag) {
			// 合同第一次创建
			// 设置主被保险人信息
			contSchema.setInsuredBirthday(insuredSchema.getBirthday());
			contSchema.setInsuredNo(insuredSchema.getInsuredNo());
			contSchema.setInsuredName(insuredSchema.getName());
			if (insuredSchema.getIDType().equals("0")) {
				contSchema
						.setInsuredIDNo(insuredSchema.getIDNo().toUpperCase());
			} else {
				contSchema.setInsuredIDNo(insuredSchema.getIDNo());
			}

			contSchema.setInsuredIDType(insuredSchema.getIDType());
			contSchema.setInsuredSex(insuredSchema.getSex());
		}

		tNewVData.add(contSchema);


		LCPolSchema tLCPolSchema = formLCPolSchema(insuredSchema,
				tLCContPlanRiskSchema, contSchema);
		// 主险保单信息
		String mainRiskCode = m_GrpPolImpInfo.getMainRiskCode(
				tLCContPlanRiskSchema.getContPlanCode(), strRiskCode);

		if (mainRiskCode.equals("")) {
			mainRiskCode = strRiskCode;
			mainPolBL.setRiskCode(mainRiskCode);
		}
		String mainPolKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
				mainRiskCode);
		tLCPolSchema.setMainPolNo(mainPolKey);
		tLCPolSchema.setInsuredNo(insuredIndex);
		// 填充所有保单信息
		VData polData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, PolKey);
		if (polData == null) {
			CError.buildErr(this, "出错了:", m_GrpPolImpInfo.mErrors);
			return null;
		}
		MMap polRelaMap = (MMap) polData.getObjectByObjectName("MMap", 0);
		tLCPolSchema = (LCPolSchema) polData.getObjectByObjectName(
				"LCPolSchema", 0);
		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
		if (polRelaMap != null && polRelaMap.keySet().size() > 0)
			tmpMap.add(polRelaMap);

		// 责任信息查询和生成

		ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
		VData tVData = new VData();
		tVData.add(tLCContPlanRiskSchema);
		boolean b = contPlanQryBL.submitData(tVData, "");
		if (!b || contPlanQryBL.mErrors.needDealError()) {
			CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
			return null;
		}
		tVData = contPlanQryBL.getResult();
		LCDutySet tDutySet = (LCDutySet) tVData.getObjectByObjectName(
				"LCDutySet", 0);
		if (tDutySet == null) {
			LCDutySchema tDutySchema = (LCDutySchema) tVData
					.getObjectByObjectName("LCDutySchema", 0);
			if (tDutySchema == null) {
				CError.buildErr(this, "查询计划要约出错:责任信息不能为空");
				return null;
			}
			setPolInfoByDuty(tLCPolSchema, tDutySchema);
			tDutySet = new LCDutySet();
			tDutySet.add(tDutySchema);
		}
		if (tDutySet == null || tDutySet.size() <= 0) {
			CError.buildErr(this, strRiskCode + "要约信息生成错误出错");
			return null;
		}

		tNewVData.add(tDutySet);
		// 保费
		LCPremSet tPremSet = new LCPremSet();
		tNewVData.add(tPremSet);

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		// 主被保险人受益人
		LCBnfSet lLCBnfSet = m_GrpPolImpInfo.getBnfSet(PolKey);
		if (lLCBnfSet != null && lLCBnfSet.size() > 0){
			tLCBnfSet.add(lLCBnfSet);
		}
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			VData tr = m_GrpPolImpInfo.perareBnf(tLCBnfSet, insuredSchema,
					tLCGrpPolSchema, insuredIndex);

			MMap tmap = (MMap) tr.getObjectByObjectName("MMap", 0);
			if (tmap != null)
				tmpMap.add(tmap);
			tLCBnfSet = (LCBnfSet) tr.getObjectByObjectName("LCBnfSet", 0);
			tNewVData.add(tLCBnfSet);

		}

		// 加入对应险种的集体投保单信息,险种承保描述信息
		LMRiskAppSchema tLMRiskAppSchema =new LMRiskAppSchema();
		LMRiskSchema tLMRiskSchema=new LMRiskSchema ();
		//String RiskCode1[]=strRiskCode.split(";");
		String RiskCode2[]=strRiskCode.split("；");
		      mRiskCode=RiskCode2;//模板中可能用";"或"；" 所以要判断
		if(mRiskCode!=null){
			for(int i=0;i<mRiskCode.length;i++){
				tLMRiskAppSchema = m_GrpPolImpInfo
				.findLMRiskAppSchema(mRiskCode[i]);
				if (tLMRiskAppSchema == null) {
					buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
				tLMRiskSchema = m_GrpPolImpInfo
				.findLMRiskSchema(mRiskCode[i]);
				if (tLMRiskSchema == null) {
					buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
			}
		}

		LDGrpSchema ttLDGrpSchema = m_GrpPolImpInfo
				.findLDGrpSchema(tLCGrpPolSchema.getCustomerNo());
		if (ttLDGrpSchema == null) {
			buildError("prepareContPlanData", tLCGrpPolSchema.getCustomerNo()
					+ "对应的集体客户信息没有找到!");
			return null;
		}

		// 其他信息
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("samePersonFlag", "0");
		tTransferData.setNameAndValue("GrpImport", 1);
		tTransferData.setNameAndValue("ID", PolKey);
		tTransferData.setNameAndValue("PolKey", PolKey);
		// jixf 20051102
		if (mBQFlag != null &&  "2".equals(mBQFlag)) {

			tTransferData.setNameAndValue("SavePolType", mSavePolType);
			tTransferData.setNameAndValue("BQFlag", mBQFlag);
			tTransferData.setNameAndValue("EdorType", mEdorType);
			tTransferData.setNameAndValue("EdorTypeCal", mEdorTypeCal);
			tTransferData.setNameAndValue("EdorValiDate", mEdorValiDate);

		}
		// jixf end

		tNewVData.add(mGlobalInput);
		tNewVData.add(tLCGrpPolSchema);
		tNewVData.add(tLMRiskAppSchema);
		tNewVData.add(tLMRiskSchema);
		tNewVData.add(ttLDGrpSchema);
		tNewVData.add(m_GrpPolImpInfo.getLCGrpAppntSchema());
		tNewVData.add(tLCPolSchema);
		tNewVData.add(tTransferData);
		tNewVData.add(this.mainPolBL);
		tNewVData.add(tmpMap);
		return tNewVData;

	}

	/**
	 * 生成保单信息
	 * 
	 * @param insuredSchema
	 *            LCInsuredSchema
	 * @param tLCContPlanRiskSchema
	 *            LCContPlanRiskSchema
	 * @return LCPolSchema
	 */
	private LCPolSchema formLCPolSchema(LCInsuredSchema insuredSchema,
			LCContPlanRiskSchema tLCContPlanRiskSchema, LCContSchema contSchema) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();

		LCGrpPolSchema tLCGrpPolSchema = m_GrpPolImpInfo
				.findLCGrpPolSchema(strRiskCode);

		if (tLCGrpPolSchema == null) {
			buildError("formatLCPol", "找不到指定险种所对应的集体保单，险种为：" + strRiskCode);
			return null;
		}

		tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
		tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		//tongmeng 2009-03-23 modify
		//如果指定生效日,使用指定的生效日
		if(contSchema.getCValiDate()!=null&&!contSchema.getCValiDate().equals(""))
		{
			tLCPolSchema.setCValiDate(contSchema.getCValiDate());
			tLCPolSchema.setSpecifyValiDate("Y");
		}
		else
		{
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			// 如果是保全增人，磁盘倒入指定生效日期，且生效日期字段无值,那么就用 页面传入值
			// jixf 20051102
			logger.debug("---------------------");
			if (mBQFlag != null && !mBQFlag.equals("")) {
				tLCPolSchema.setCValiDate(mEdorValiDate);
			}
		}


		tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		tLCPolSchema.setSaleChnl(tLCGrpPolSchema.getSaleChnl());
		tLCPolSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
		tLCPolSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
		tLCPolSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
		tLCPolSchema.setAgentCode1(tLCGrpPolSchema.getAgentCode1());
		tLCPolSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
		tLCPolSchema.setContType("2");
		tLCPolSchema.setPolTypeFlag(contSchema.getPolType());
		tLCPolSchema.setInsuredPeoples(contSchema.getPeoples());
		tLCPolSchema.setRiskCode(strRiskCode);

		tLCPolSchema.setContNo(insuredSchema.getContNo().trim());
		tLCPolSchema.setInsuredSex(insuredSchema.getSex().trim());
		tLCPolSchema.setInsuredBirthday(insuredSchema.getBirthday());
		tLCPolSchema.setInsuredName(insuredSchema.getName().trim());

		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());

		return tLCPolSchema;

	}

	public String getExtendFileName(String aFileName) {
		File tFile = new File(aFileName);
		String aExtendFileName = "";
		String name = tFile.getName();
		for (int i = name.length() - 1; i >= 0; i--) {
			if (i < 1) {
				i = 1;
			}
			if (name.substring(i - 1, i).equals(".")) {
				aExtendFileName = name.substring(i, name.length());
				logger.debug("ExtendFileName;" + aExtendFileName);
				return aExtendFileName;
			}
		}
		return aExtendFileName;
	}

	/**
	 * 字符串替换
	 * 
	 * @param s1
	 * @param OriginStr
	 * @param DestStr
	 * @return
	 */
	public static String replace(String s1, String OriginStr, String DestStr) {
		s1 = s1.trim();
		int mLenOriginStr = OriginStr.length();
		for (int j = 0; j < s1.length(); j++) {
			int befLen = s1.length();
			if (s1.substring(j, j + 1) == null
					|| s1.substring(j, j + 1).trim().equals("")) {
				continue;
			} else {
				if (OriginStr != null && DestStr != null) {
					if (j + mLenOriginStr <= s1.length()) {

						if (s1.substring(j, j + mLenOriginStr)
								.equals(OriginStr)) {

							OriginStr = s1.substring(j, j + mLenOriginStr);

							String startStr = s1.substring(0, j);
							String endStr = s1.substring(j + mLenOriginStr, s1
									.length());

							s1 = startStr + DestStr + endStr;

							j = j + s1.length() - befLen;
							if (j < 0)
								j = 0;
						}
					} else {
						break;
					}
				} else
					break;
			}
		}
		return s1;
	}

	/**
	 * 得到日志显示结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ParseGuideIn tPGI = new ParseGuideIn();
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "37370000001_01.xls");
		tTransferData.setNameAndValue("FilePath", "E:");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "GrpImport";
		tG.ManageCom = "86110000";

		tVData.add(tTransferData);
		tVData.add(tG);
		tPGI.submitData(tVData, "");

	}

	/**
	 * Build a instance document object for function transfromNode()
	 */




	/**
	 * 保单数据准备(按险种单导入的时候使用)
	 * 
	 * @param aVData
	 *            VData
	 * @return VData 返回数据集，包含提交给ProposalBL需要用到的信息。
	 */
	private VData prepareProposalBLData(VData aVData) {
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		TransferData tTransferData = (TransferData) aVData.getObjectByObjectName("TransferData", 0);
		LCPolSchema tLCPolSchema = (LCPolSchema) aVData.getObjectByObjectName("LCPolSchema", 0);
		LCPremSet tLCPremSet = (LCPremSet) aVData.getObjectByObjectName("LCPremSet", 0);
		LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) aVData.getObjectByObjectName("LCPremToAccSet", 0);
		LCDutySet tLCDutySet = (LCDutySet) aVData.getObjectByObjectName("LCDutySet", 0);

		// 险种ID
		String strID = (String) tTransferData.getValueByName("ID");
		String tgetintv = (String) tTransferData.getValueByName("GetIntv");
		// 备份原ID号
		String strRiskCode = tLCPolSchema.getRiskCode();
		String InsuredId = tLCPolSchema.getInsuredNo();
		String contId = tLCPolSchema.getContNo();
		// 导入的时候使用appflag字段缓存连带被保险人ID信息

		// 查询集体保单信息
		LCGrpPolSchema sLCGrpPolSchema =new LCGrpPolSchema();
		String RiskCode1[]=strRiskCode.split(";");
		String RiskCode2[]=strRiskCode.split("；");
		      mRiskCode=RiskCode2;//模板中可能用";"或"；" 所以要判断
		if(RiskCode1.length>RiskCode2.length)
			mRiskCode=RiskCode1;
		if(mRiskCode!=null){
			for(int i=0;i<mRiskCode.length;i++){
				sLCGrpPolSchema = m_GrpPolImpInfo.findLCGrpPolSchema(mRiskCode[i]);
				if (sLCGrpPolSchema == null) {
					buildError("prepareProposalBLData", strRiskCode + "险种对应的集体投保单没有找到!");
					return null;
				}
			}
		}

		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);
		boolean mainPolFlag = false;
		if (strID.equals(tLCPolSchema.getMainPolNo())) {
			mainPolFlag = true;
		}

		// 格式化保单信息
		VData tPolData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, strID);
		if (tPolData == null) {
			mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
			return null;
		}
		tLCPolSchema = (LCPolSchema) tPolData.getObjectByObjectName("LCPolSchema", 0);
		if (!mainPolFlag) {
			LCPolSchema mainPolSchema = m_GrpPolImpInfo.findMainPolSchema(tLCPolSchema.getMainPolNo());
			if (mainPolSchema == null) {
				CError.buildErr(this, "查找主险保单[" + strID + "]失败");
				return null;
			}
			mainPolBL.setSchema(mainPolSchema);
		} else {
			mainPolBL.setSchema(tLCPolSchema);
		}

		MMap poldataMap = (MMap) tPolData.getObjectByObjectName("MMap", 0);
		if (poldataMap != null)
			tmpMap.add(poldataMap);

		// 被保险人、合同信息
		LCInsuredSchema insuredSchema = m_GrpPolImpInfo.findInsuredfromCache(InsuredId);

		if (insuredSchema == null) {
			CError.buildErr(this, "未找到被保险人[" + InsuredId + "]");
			return null;
		}

		LCContSchema contSchema = m_GrpPolImpInfo.findLCContfromCache(contId);
		if (contSchema == null) {
			CError.buildErr(this, "未找到合同[" + contId + "]");
			return null;
		}
		
		// 设置主被保险人信息
		contSchema.setInsuredBirthday(insuredSchema.getBirthday());
		contSchema.setInsuredNo(insuredSchema.getInsuredNo());
		contSchema.setInsuredName(insuredSchema.getName());
		if (insuredSchema.getIDType().equals("0")) {
			contSchema.setInsuredIDNo(insuredSchema.getIDNo().toUpperCase());
		} else {
			contSchema.setInsuredIDNo(insuredSchema.getIDNo());
		}

		contSchema.setInsuredIDType(insuredSchema.getIDType());
		contSchema.setInsuredSex(insuredSchema.getSex());

		// 责任信息查询和生成

		LCContPlanRiskSchema tLCContPlanRiskSchema = new LCContPlanRiskSchema();
		tLCContPlanRiskSchema.setMainRiskCode(mainPolBL.getRiskCode());
		tLCContPlanRiskSchema.setGrpContNo(this.mGrpContNo);
		tLCContPlanRiskSchema.setRiskCode(tLCPolSchema.getRiskCode());
		// 00-默认值
		tLCContPlanRiskSchema.setContPlanCode("00");
		tLCContPlanRiskSchema.setProposalGrpContNo(this.mGrpContNo);

		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		tLCContPlanRiskDB.setSchema(tLCContPlanRiskSchema);

		LCDutySet tempDutySet = null;
		// 如果没有默认值，则取excel中提交的数据
		if (!tLCContPlanRiskDB.getInfo()) {
			if (tLCDutySet != null && tLCDutySet.size() > 0) {
				// 责任项页签中有数据
				tempDutySet = tLCDutySet;

			} else {
				// 责任项页签没有数据，则用险种保单页签的数据
				LCDutySchema ntempDutySchema = new LCDutySchema();
				// tgetintv
				tLCPolSchema.setAgentCode1(tgetintv);
				setDutyByPolInfo(ntempDutySchema, tLCPolSchema);
				tempDutySet = new LCDutySet();
				tempDutySet.add(ntempDutySchema);
			}
		} else {
			// 如果有默认值，则需要查询默认值
			ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
			VData tVData = new VData();
			tVData.add(tLCContPlanRiskSchema);
			boolean b = contPlanQryBL.submitData(tVData, "");
			if (!b || contPlanQryBL.mErrors.needDealError()) {
				CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
				return null;
			}
			tVData = contPlanQryBL.getResult();
			tempDutySet = (LCDutySet) tVData.getObjectByObjectName("LCDutySet",
					0);
			if (tempDutySet == null) {
				// 只有一条责任项，以险种保单页签的数据为准
				LCDutySchema ttempDutySchema = (LCDutySchema) tVData
						.getObjectByObjectName("LCDutySchema", 0);

				setDutyByPolInfo(ttempDutySchema, tLCPolSchema);
				tempDutySet = new LCDutySet();
				tempDutySet.add(ttempDutySchema);

			}
			// 有多条责任项，以责任项页签的数据为准
			for (int i = 1; i <= tLCDutySet.size(); i++) {

				for (int j = 1; j <= tempDutySet.size(); j++) {
					if (tempDutySet.get(j).getDutyCode().equals(
							tLCDutySet.get(i).getDutyCode())) {
						if (tLCDutySet.get(i).getMult() > 0) {
							tempDutySet.get(j).setMult(
									tLCDutySet.get(i).getMult());
						}
						if (tLCDutySet.get(i).getPrem() > 0) {
							tempDutySet.get(j).setPrem(
									tLCDutySet.get(i).getPrem());
						}
						if (tLCDutySet.get(i).getAmnt() > 0) {
							tempDutySet.get(j).setAmnt(
									tLCDutySet.get(i).getAmnt());
						}
						if (tLCDutySet.get(i).getPayIntv() > 0)
							tempDutySet.get(j).setPayIntv(
									tLCDutySet.get(i).getPayIntv());
						if (tLCDutySet.get(i).getInsuYear() > 0)
							tempDutySet.get(j).setInsuYear(
									tLCDutySet.get(i).getInsuYear());

						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getInsuYearFlag())))
							tempDutySet.get(j).setInsuYearFlag(
									tLCDutySet.get(i).getInsuYearFlag());

						if (tLCDutySet.get(i).getPayEndYear() > 0)
							tempDutySet.get(j).setPayEndYear(
									tLCDutySet.get(i).getPayEndYear());
						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getPayEndYearFlag())))
							tempDutySet.get(j).setPayEndYearFlag(
									tLCDutySet.get(i).getPayEndYearFlag());
						if (tLCDutySet.get(i).getGetYear() > 0)
							tempDutySet.get(j).setGetYear(
									tLCDutySet.get(i).getGetYear());

						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getGetYearFlag())))
							tempDutySet.get(j).setGetYearFlag(
									tLCDutySet.get(i).getGetYearFlag());

						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getGetStartType())))
							tempDutySet.get(j).setGetStartType(
									tLCDutySet.get(i).getGetStartType());

						// 计算方向
						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getPremToAmnt()))) {
							tempDutySet.get(j).setPremToAmnt(
									tLCDutySet.get(i).getPremToAmnt());
						}
						// 计算规则 被缓存在 最终核保人编码 字段
						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getCalRule()))) {
							tempDutySet.get(j).setCalRule(
									tLCDutySet.get(i).getCalRule());
						}
						// 费率
						if (tLCDutySet.get(i).getFloatRate() > 0) {
							if ("0".equals(tempDutySet.get(j).getCalRule())
									|| "1".equals(tempDutySet.get(j)
											.getCalRule())) {
								// 计算规则为 0-表定费率 或 1-统一费率 时
								// 费率必须是从默认值中取得
							} else {
								tempDutySet.get(j).setFloatRate(
										tLCDutySet.get(i).getFloatRate());
							}
						}
						// 免赔额
						if (tLCDutySet.get(i).getGetLimit() > 0) {
							tempDutySet.get(j).setGetLimit(
									tLCDutySet.get(i).getGetLimit());
						}
						// 赔付比例
						if (tLCDutySet.get(i).getGetRate() > 0) {
							tempDutySet.get(j).setGetRate(
									tLCDutySet.get(i).getGetRate());
						}
						if (tLCDutySet.get(i).getPeakLine()>0){
							tempDutySet.get(j).setPeakLine(
									tLCDutySet.get(i).getPeakLine());
                        }
                        if (tLCDutySet.get(i).getStandbyFlag1()!=null && !tLCDutySet.get(j).getStandbyFlag1().equals("")){
                        	tempDutySet.get(j).setStandbyFlag1(
                        			tLCDutySet.get(i).getStandbyFlag1());
                        }
                        if (tLCDutySet.get(i).getStandbyFlag2()!=null && !tLCDutySet.get(j).getStandbyFlag2().equals("")){
                        	tempDutySet.get(j).setStandbyFlag2(
                        			tLCDutySet.get(i).getStandbyFlag2());
                        }
                        if (tLCDutySet.get(i).getStandbyFlag3()!=null && !tLCDutySet.get(j).getStandbyFlag3().equals("")){
                        	tempDutySet.get(j).setStandbyFlag3(
                        			tLCDutySet.get(i).getStandbyFlag3());
                        }
                        if (tLCDutySet.get(i).getAscriptionRuleCode()!=null && !tLCDutySet.get(j).getAscriptionRuleCode().equals("")){
                        	tempDutySet.get(j).setAscriptionRuleCode(
                        			tLCDutySet.get(i).getAscriptionRuleCode());
                        }
                        tempDutySet.get(j).setGetIntv(tLCDutySet.get(j).getGetIntv());
                        tempDutySet.get(j).setPayIntv(tLCPolSchema.getPayIntv());

						break;
					}
				}
			}
		}
		// 清空借用字段
		tLCPolSchema.setAppFlag("");
		tLCPolSchema.setUWCode("");
		tLCPolSchema.setApproveFlag("");
		tLCPolSchema.setUWFlag("");

		// 处理连身被保险人
		LCInsuredRelatedSet tRelaInsSet = null;
		String[] relaIns = null;
		relaIns = m_GrpPolImpInfo.getInsuredRela(contId,InsuredId,strRiskCode);

			if (relaIns != null) {
				tRelaInsSet = m_GrpPolImpInfo.getInsuredRelaData(tLCPolSchema
						.getInsuredNo(), relaIns,this.mBatchNo);
				if (tRelaInsSet == null) {
					// 创建连带被保险人出错
					mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
					return null;
				}else{
					//判断是否为连带被保人 如果是 则提交所有数据时只提交lcinsured & lcinsuredrelated表
					if(tRelaInsSet.size()>0){
						String tRelation =tRelaInsSet.get(1).getRelationToInsured();
						if(!"00".equals(tRelation)&&!"".equals(tRelation)){
							//连带被保人
							mRelation =true;
						}else{
							mRelation =false;
							mMainSequenceNo = tRelaInsSet.get(1).getSequenceNo();
						}
					}
				}

			}


		String PolKey = m_GrpPolImpInfo.getPolKey(contId, InsuredId,
				strRiskCode);
		tTransferData.setNameAndValue("PolKey", PolKey);

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		tLCBnfSet = m_GrpPolImpInfo.getBnfSetByPolId(strID);
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			VData tr = m_GrpPolImpInfo.perareBnf(tLCBnfSet, insuredSchema,
					tLCGrpPolSchema, InsuredId);

			MMap tmap = (MMap) tr.getObjectByObjectName("MMap", 0);
			if (tmap != null)
				tmpMap.add(tmap);
			tLCBnfSet = (LCBnfSet) tr.getObjectByObjectName("LCBnfSet", 0);
		}

		tTransferData.setNameAndValue("samePersonFlag", 0);
		tTransferData.setNameAndValue("GrpImport", 1); // 磁盘投保标志
		// 险种描述信息
		LMRiskAppSchema tLMRiskAppSchema =new LMRiskAppSchema();
		LMRiskSchema tLMRiskSchema=new LMRiskSchema();
		if(mRiskCode!=null){
			for(int j=0;j<mRiskCode.length;j++){
				tLMRiskAppSchema = m_GrpPolImpInfo
				.findLMRiskAppSchema(mRiskCode[j]);
				if (tLMRiskAppSchema == null) {
					buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
				tLMRiskSchema = m_GrpPolImpInfo
				.findLMRiskSchema(mRiskCode[j]);
				if (tLMRiskSchema == null) {
					buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
			}
		}

		LDGrpSchema ttLDGrpSchema = m_GrpPolImpInfo
				.findLDGrpSchema(tLCGrpPolSchema.getCustomerNo());
		if (ttLDGrpSchema == null) {
			buildError("prepareData", tLCGrpPolSchema.getCustomerNo()
					+ "对应的集体信息没有找到!");
			return null;
		}

		// 增加了保全分支判断,如果是 保全增加被保人，设置保全保存标记 2
		// jixf 20051102
		if (mBQFlag != null &&"2".equals(StrTool.cTrim(mBQFlag))
				&& ("NI".equals(StrTool.cTrim(mEdorType)) || "NR".equals(StrTool.cTrim(mEdorType)))) {
			// 保全保存标记
			tTransferData.removeByName("SavePolType");
			tTransferData.setNameAndValue("SavePolType", "2");
			tTransferData.setNameAndValue("BQFlag", mBQFlag);
			tTransferData.setNameAndValue("EdorType", mEdorType);
			tTransferData.setNameAndValue("EdorTypeCal", mEdorTypeCal);
			tTransferData.setNameAndValue("EdorValiDate", mEdorValiDate);
		}
		if (("Y".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null)
				  ||(tLCPolSchema.getCValiDate()!=null
				    &&!"".equals(tLCPolSchema.getCValiDate()))) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());

			if (mBQFlag!= null&& !mBQFlag.equals("")) {

				tLCPolSchema.setCValiDate(mEdorValiDate);
			}

		}

		tNewVData.add(tTransferData);
		tNewVData.add(mGlobalInput);

		if (tempDutySet == null || tempDutySet.size() <= 0) {
			CError.buildErr(this, "险种[" + strRiskCode + "]责任不能为空");
			return null;
		}
		tNewVData.add(tempDutySet);
		if (tRelaInsSet != null)
			tNewVData.add(tRelaInsSet);
		tNewVData.add(tLCPolSchema);
		tNewVData.add(tLCPremSet);
		tNewVData.add(tLCPremToAccSet);
		tNewVData.add(contSchema);
		tNewVData.add(insuredSchema);
		tNewVData.add(tLCBnfSet);

		tNewVData.add(tLCGrpPolSchema);
		tNewVData.add(m_GrpPolImpInfo.getLCGrpAppntSchema());
		tNewVData.add(tLMRiskAppSchema);
		tNewVData.add(tLMRiskSchema);
		tNewVData.add(ttLDGrpSchema);
		tNewVData.add(mainPolBL);
		tNewVData.add(tmpMap);

		return tNewVData;
	}

	/**
	 * 创建错误日志
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ParseGuideIn";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}



	/**
	 * 通过duty设置一些lcpol的元素
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param dutySchema
	 *            LCDutySchema
	 */
	private void setPolInfoByDuty(LCPolSchema tLCPolSchema,
			LCDutySchema dutySchema) {

		tLCPolSchema.setInsuYear(dutySchema.getInsuYear());
		tLCPolSchema.setInsuYearFlag(dutySchema.getInsuYearFlag());
		tLCPolSchema.setPrem(dutySchema.getPrem());
		tLCPolSchema.setAmnt(dutySchema.getAmnt());
		tLCPolSchema.setPayEndYear(dutySchema.getPayEndYear());
		tLCPolSchema.setPayEndYearFlag(dutySchema.getPayEndYearFlag());
		tLCPolSchema.setGetYear(dutySchema.getGetYear());
		tLCPolSchema.setGetYearFlag(dutySchema.getGetYearFlag());
		tLCPolSchema.setAcciYear(dutySchema.getAcciYear());
		tLCPolSchema.setAcciYearFlag(dutySchema.getAcciYearFlag());
		tLCPolSchema.setMult(dutySchema.getMult());
		// 计算方向,在按分数卖的保单，切记算方向为O的时候
		if (dutySchema.getMult() > 0
				&& "O".equals(StrTool.cTrim(dutySchema.getPremToAmnt()))) {
			tLCPolSchema.setPremToAmnt(dutySchema.getPremToAmnt());
		}
		tLCPolSchema.setStandbyFlag1(dutySchema.getStandbyFlag1());
		tLCPolSchema.setStandbyFlag2(dutySchema.getStandbyFlag2());
		tLCPolSchema.setStandbyFlag3(dutySchema.getStandbyFlag3());
	}

	private void setDutyByPolInfo(LCDutySchema dutySchema,
			LCPolSchema tLCPolSchema) {
		if (tLCPolSchema.getMult() > 0) {
			dutySchema.setMult(tLCPolSchema.getMult());
		}
		if (tLCPolSchema.getPrem() > 0) {
			dutySchema.setPrem(tLCPolSchema.getPrem());
		}
		if (tLCPolSchema.getAmnt() > 0) {
			dutySchema.setAmnt(tLCPolSchema.getAmnt());
		}
		if (tLCPolSchema.getPayIntv() > 0) {
			dutySchema.setPayIntv(tLCPolSchema.getPayIntv());
		}
		if (tLCPolSchema.getInsuYear() > 0) {
			dutySchema.setInsuYear(tLCPolSchema.getInsuYear());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getInsuYearFlag()))) {
			dutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
		}
		if (tLCPolSchema.getPayEndYear() > 0) {
			dutySchema.setPayEndYear(tLCPolSchema.getPayEndYear());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayEndYearFlag()))) {
			dutySchema.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
		}
		if (tLCPolSchema.getGetYear() > 0) {
			dutySchema.setGetYear(tLCPolSchema.getGetYear());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetYearFlag()))) {
			dutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetStartType()))) {
			dutySchema.setGetStartType(tLCPolSchema.getGetStartType());
		}
		// 计算方向
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getPremToAmnt()))) {
			dutySchema.setPremToAmnt(tLCPolSchema.getPremToAmnt());
		}
		// 计算规则被缓存在 最终核保人编码 UWCode 字段
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWCode()))) {
			dutySchema.setCalRule(tLCPolSchema.getUWCode());
		}
		// 费率
		if (tLCPolSchema.getFloatRate() > 0) {
			if ("0".equals(dutySchema.getCalRule())
					|| "1".equals(dutySchema.getCalRule())) {
				// 计算规则为 0-表定费率 或 1-统一费率 时
				// 费率必须是从默认值中取得
			} else {
				dutySchema.setFloatRate(tLCPolSchema.getFloatRate());
			}
		}

		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag1()))) {
			dutySchema.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag2()))) {
			dutySchema.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag3()))) {
			dutySchema.setStandbyFlag3(tLCPolSchema.getStandbyFlag3());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getApproveFlag()))) {
			dutySchema.setGetLimit(tLCPolSchema.getApproveFlag());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWFlag()))) {
			dutySchema.setGetRate(tLCPolSchema.getUWFlag());
		}
		// 增加缴费规则与归属规则
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getAscriptionRuleCode()))) {
			dutySchema.setAscriptionRuleCode(tLCPolSchema
					.getAscriptionRuleCode());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayRuleCode()))) {
			dutySchema.setPayRuleCode(tLCPolSchema.getPayRuleCode());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getBonusGetMode()))) {
			dutySchema.setBonusGetMode(tLCPolSchema.getBonusGetMode());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getAgentCode1()))) {
			dutySchema.setGetIntv(tLCPolSchema.getAgentCode1());
		}
	}

}
