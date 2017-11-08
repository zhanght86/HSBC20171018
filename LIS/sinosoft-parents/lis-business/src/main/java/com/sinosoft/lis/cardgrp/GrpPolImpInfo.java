/*
 * @(#)GrpPolImpInfo.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpImportLogDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.tb.ContInsuredBL;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCGrpImportLogSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保存及填充磁盘投保过程中需要的一些信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Kevin
 * @version 6.0
 * @date 2003-07-09
 */
public class GrpPolImpInfo {
private static Logger logger = Logger.getLogger(GrpPolImpInfo.class);
	public CErrors mErrors = new CErrors();

	private boolean m_bInited = false;

	private GlobalInput mGlobalInput = null;

	// 2005-11-03==增加==方便特殊数据传输
	private TransferData mTransferData = null;

	// 同一个印刷号下的所有集体保单的信息
	private LCGrpPolSet m_LCGrpPolSet = new LCGrpPolSet();

	// 同一个印刷号下的所有集体保单的险种承保描述信息
	private LMRiskAppSet m_LMRiskAppSet = new LMRiskAppSet();

	// 同一个印刷号下的所有集体保单的险种描述信息
	private LMRiskSet m_LMRiskSet = new LMRiskSet();

	// 集体信息表
	private LDGrpSet m_LDGrpSet = new LDGrpSet();
	private LCGrpAppntSchema mLCGrpAppntSchema = null;
	private String m_strBatchNo = "";
	private LCGrpContSchema mLCGrpContSchema = null;
	private LCContPlanSet mLCContPlanSet = new LCContPlanSet();
	private LCContPlanRiskSet mLCContPlanRiskSet = new LCContPlanRiskSet();
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCInsuredRelatedSet mLCInsuredRelatedSet = new LCInsuredRelatedSet();

	// 缓存的一些信息
	// 对于有主附险的情况，将主险记录的相关信息缓存，因为在处理附加险的时候
	// 需要主险的保单号等相关的信息
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet(); // 保存原始信息

	// 保存经过创建或系统查询出来后的被保险人信息
	private HashMap mInsuredMap = new HashMap();
	private HashMap mMainPolMap = new HashMap();

	// 保存创建或系统中查询出来的合同信息
	private HashMap mContCache = new HashMap();
	private HashMap mBnfMap = new HashMap();
	private HashMap mContPolData = new HashMap(); // 保存以合同为索引的保单信息
	
	private String mRelationToRisk = "";//用于查询连带被保人的主被保人
	private String mRiskCode[] = null;//用于查询连带被保人的主被保人

	public GrpPolImpInfo() {
		// reset();
	}

	/**
	 * 通过磁盘投保文件中的团体保单号来初始化一些信息
	 * 
	 * @param strBatchNo
	 *            String
	 * @param GlobalInput
	 *            GlobalInput
	 * @param tLCInsuredSet
	 *            LCInsuredSet
	 * @param tLCBnfSet
	 *            LCBnfSet
	 * @param tLCInsuredRelatedSet
	 *            LCInsuredRelatedSet
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return boolean
	 */
	public boolean init(String strBatchNo, GlobalInput GlobalInput,
			TransferData TransferData, LCInsuredSet tLCInsuredSet,
			LCBnfSet tLCBnfSet, LCInsuredRelatedSet tLCInsuredRelatedSet,
			LCGrpContSchema tLCGrpContSchema) {
		mGlobalInput = GlobalInput;
		mTransferData = TransferData;
		// 清空
		mContCache.clear();
		// mContCache = new MMap();

		mInsuredMap.clear();
		mMainPolMap.clear();
		// mContPolData.clear();

		mLCBnfSet = tLCBnfSet;
		mLCInsuredRelatedSet = tLCInsuredRelatedSet;
		if (strBatchNo == null || strBatchNo.equals("")) {
			buildError("init", "传入非法的磁盘投保批次号");
			return false;
		}
		m_strBatchNo = strBatchNo;

		// 初始化合同id信息
		mLCInsuredSet = tLCInsuredSet;
		String contId = "";

		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			contId = tLCInsuredSet.get(i).getContNo();
			if ("".equals(contId)) {
				buildError("init", "被保人信息中有没有填写的合同号!");
				return false;
			}
			if (!mContCache.containsKey(contId))
				mContCache.put(contId, null);
		}

		mLCGrpContSchema = tLCGrpContSchema;
		String grpcontNo = mLCGrpContSchema.getGrpContNo();

		if (m_LDGrpSet == null || m_LDGrpSet.size() == 0) {
			if (initLDGrp(tLCGrpContSchema.getAppntNo()) == false)
				return false;
		}

		if (mLCGrpAppntSchema == null) {
			if (!this.initLDGrpAppnt(tLCGrpContSchema.getGrpContNo(),
					tLCGrpContSchema.getAppntNo())) {
				return false;
			}
		}
		// 根据合同号查询集体保单
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(grpcontNo);
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

		LCGrpPolSchema tLCGrpPolSchema = null;

		// 集体险种保单信息
		for (int nIndex = 0; nIndex < tLCGrpPolSet.size(); nIndex++) {
			tLCGrpPolSchema = tLCGrpPolSet.get(nIndex + 1);
			isCachedRisk(tLCGrpPolSchema.getRiskCode());

		}

		m_LCGrpPolSet = tLCGrpPolSet;

		// 保障计划,保障计划险种计划初始化
		boolean needquery = true;
		for (int t = 1; t <= mLCContPlanSet.size(); t++) {
			if (mLCContPlanSet.get(t).getGrpContNo().equals(grpcontNo)) {
				needquery = false;
			}
		}
		if (needquery) {
			queryLCContPlan(grpcontNo);
			for (int t = 1; t <= mLCContPlanSet.size(); t++) {
				this.queryLCContPlanRisk(grpcontNo, mLCContPlanSet.get(t)
						.getContPlanCode());
			}
		}
		return true;
	}

	public LCInsuredSet getLCInsuredSet() {
		return this.mLCInsuredSet;
	}

	/**
	 * 根据合同号移除已经缓存的合同 ， 被保险人 和 主险保单
	 * 
	 * @param contId
	 *            String
	 * @return boolean
	 */
	public boolean removeCaceh(String contId) {
		// 本部分代码没有测过
		// 移除缓存合同

		this.mContCache.put(contId, null);
		String polKey = "";
		Iterator it = null;
		// 移除被保险人,移除主险保单，记录下金额
		LCPolSchema tPol = null;
		for (int i = 1; i <= this.mLCInsuredSet.size(); i++) {
			// String insuredId = this.mLCInsuredSet.get(i).getInsuredNo() ;
			if (contId.equals(this.mLCInsuredSet.get(i).getContNo())) {
				polKey = contId + "-"
						+ this.mLCInsuredSet.get(i).getInsuredNo();
				this.mInsuredMap.remove(this.mLCInsuredSet.get(i)
						.getInsuredNo());
				it = this.mMainPolMap.keySet().iterator();
				String key = "";

				while (it.hasNext()) {
					key = (String) it.next();
					if (key.indexOf("-") > 0) {
						if (polKey.equals(key
								.substring(0, key.lastIndexOf("-")))) {
							tPol = (LCPolSchema) this.mMainPolMap.get(key);
							if (tPol != null) {
								this.substractMoney(tPol.getRiskCode(), tPol
										.getPrem(), tPol.getAmnt());
								this.mMainPolMap.remove(key);
							}
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 根据险种代码查找出对应的集体险种保单，减去传入的保费保额
	 * 
	 * @param riskcode
	 *            String
	 * @param prem
	 *            double
	 * @param amnt
	 *            double
	 */
	private void substractMoney(String riskcode, double prem, double amnt) {
		LCGrpPolSchema grpPol = this.findLCGrpPolSchema(riskcode);
		double tPrem = grpPol.getPrem() - prem;
		if (tPrem < 0)
			tPrem = 0;
		double tAmnt = grpPol.getAmnt() - amnt;
		if (tAmnt < 0)
			tAmnt = 0;

		grpPol.setPrem(tPrem);
		grpPol.setAmnt(tAmnt);
		this.mLCGrpContSchema.setPrem(mLCGrpContSchema.getPrem() - tPrem);
		this.mLCGrpContSchema.setAmnt(mLCGrpContSchema.getAmnt() - tAmnt);
	}

	/**
	 * 根据合同ID,主被保险人ID,险种代码查找连身被保险人信息
	 * 
	 * @param contId
	 *            String
	 * @param mainInsured
	 *            String
	 * @param riskcode
	 *            String
	 * @return String[]
	 */
	public String[] getInsuredRela(String contId, String mainInsured,
			String riskcode) {
		StringBuffer sb = null;
		for (int i = 1; i <= mLCInsuredRelatedSet.size(); i++) {
			if (contId.equals(mLCInsuredRelatedSet.get(i).getOperator())
					//&& mainInsured.equals(mLCInsuredRelatedSet.get(i).getMainCustomerNo()) // 借用段
					//&& riskcode.equals(mLCInsuredRelatedSet.get(i).getPolNo())
					) {
				if (sb == null) {
					sb = new StringBuffer();

				} else {
					sb.append(",");
				}
				sb.append(mLCInsuredRelatedSet.get(i).getCustomerNo());
			}

		}
		if (sb != null)
			return sb.toString().split(",");
		return null;
	}

	/**
	 * 通过险种索引获取受益人信息，在有计划磁盘投保时使用
	 * 
	 * @param strPolId
	 *            String
	 * @return LCBnfSet
	 */
	public LCBnfSet getBnfSet(String strPolId) {
		LCBnfSet tBnfSet = new LCBnfSet();
		LCBnfSchema tSchema = null;
		for (int i = 1; i <= this.mLCBnfSet.size(); i++) {
			tSchema = mLCBnfSet.get(i);
			if (getPolKey(tSchema.getContNo(), tSchema.getInsuredNo(),
					tSchema.getPolNo()).equals(strPolId)) {
				LCBnfSchema newSchema = new LCBnfSchema();
				newSchema.setSchema(tSchema);
				tBnfSet.add(newSchema);
			}
		}
		return tBnfSet;
	}

	/**
	 * 通过险种唯一ID查找受益人信息。在以险种保单为线索导入的时候使用
	 * 
	 * @param strPolId
	 *            String 险种ID
	 * @return LCBnfSet
	 */
	public LCBnfSet getBnfSetByPolId(String strPolId) {
		LCBnfSet tBnfSet = new LCBnfSet();
		LCBnfSchema tSchema = null;
		int polId = this.parseId(strPolId);
		for (int i = 1; i <= this.mLCBnfSet.size(); i++) {
			tSchema = mLCBnfSet.get(i);
			// 险种ID 被缓存在 BnfNo
			if (tSchema.getBnfNo() == polId) {
				LCBnfSchema newSchema = new LCBnfSchema();
				newSchema.setSchema(tSchema);
				tBnfSet.add(newSchema);
			}
		}
		return tBnfSet;

	}

	public LCGrpContSchema getLCGrpContSchema() {
		return this.mLCGrpContSchema;
	}

	/**
	 * 生成险种保单索引
	 * 
	 * @param contId
	 *            String
	 * @param insuredId
	 *            String
	 * @param riskcode
	 *            String
	 * @return String
	 */
	public String getPolKey(String contId, String insuredId, String riskcode) {
		return contId + "-" + insuredId + "-" + riskcode;
	}

	/**
	 * 查某一个险种保险计划
	 * 
	 * @param riskcode
	 *            String
	 * @param contPlanCode
	 *            String
	 * @return LCContPlanRiskSchema
	 */
	public LCContPlanRiskSchema findLCContPlanRiskSchema(String riskcode,
			String contPlanCode) {
		for (int i = 1; i < mLCContPlanRiskSet.size(); i++) {
			if (mLCContPlanRiskSet.get(i).getRiskCode().equals(riskcode)
					&& mLCContPlanRiskSet.get(i).getContPlanCode().equals(
							contPlanCode)) {
				return mLCContPlanRiskSet.get(i);
			}
		}
		return null;
	}

	/**
	 * 从缓存中查找保险计划代码对应的所有险种保险计划
	 * 
	 * @param contPlanCode
	 *            String
	 * @return LCContPlanRiskSet
	 */
	public LCContPlanRiskSet findLCContPlanRiskSet(String contPlanCode) {
		LCContPlanRiskSet tLCContPlanRiskSet = new LCContPlanRiskSet();
		for (int i = 1; i <= mLCContPlanRiskSet.size(); i++) {
			if (mLCContPlanRiskSet.get(i).getContPlanCode()
					.equals(contPlanCode)) {
				tLCContPlanRiskSet.add(mLCContPlanRiskSet.get(i));
			}
		}
		return tLCContPlanRiskSet;
	}

	/**
	 * 查询保险计划下所有险种保险计划
	 * 
	 * @param grpContNo
	 *            String
	 * @param contPlanCode
	 *            String
	 * @return LCContPlanRiskSet
	 */
	private void queryLCContPlanRisk(String grpContNo, String contPlanCode) {
		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		tLCContPlanRiskDB.setGrpContNo(grpContNo);
		tLCContPlanRiskDB.setContPlanCode(contPlanCode);
		mLCContPlanRiskSet.add(tLCContPlanRiskDB.query());

	}

	/**
	 * 查找保险计划
	 * 
	 * @param contPlanCode
	 *            String
	 * @return LCContPlanSchema
	 */
	public LCContPlanSchema findLCContPlan(String contPlanCode) {
		for (int i = 1; i <= mLCContPlanSet.size(); i++) {
			if (mLCContPlanSet.get(i).getContPlanCode().equals(contPlanCode)) {
				return mLCContPlanSet.get(i);
			}
		}
		return null;

	}

	/**
	 * 查询保险计划
	 * 
	 * @param grpContNo
	 *            String
	 * @param contPlanCode
	 *            String
	 * @return LCContPlanSchema
	 */
	public void queryLCContPlan(String grpContNo) {
		LCContPlanDB tContPlanDB = new LCContPlanDB();
		tContPlanDB.setGrpContNo(grpContNo);
		mLCContPlanSet.add(tContPlanDB.query());
	}

	/**
	 * 格式化个人保单信息。从磁盘投保文件中得到的个人保单信息， 没有主险保单号，集体保单号等信息，需要重新设置。
	 * 
	 * @param tLCPolSchema
	 *            险种信息
	 * @param strID
	 *            险种ID
	 * @return
	 */
	public VData formatLCPol(LCPolSchema tLCPolSchema, String strID) {
		VData tReturnData = new VData();
		String strRiskCode = tLCPolSchema.getRiskCode();
		LCInsuredSchema tLCInsuredSchema = null;
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		String RiskCode1[]=strRiskCode.split(";");
		String RiskCode2[]=strRiskCode.split("；");
		      mRiskCode=RiskCode2;//模板中可能用";"或"；" 所以要判断
		if(RiskCode1.length>RiskCode2.length)
			mRiskCode=RiskCode1;
		if(mRiskCode!=null){
			for (int i=0;i<mRiskCode.length;i++){
				tLCGrpPolSchema = findLCGrpPolSchema(mRiskCode[i]);
			}
		}
//		LCGrpPolSchema tLCGrpPolSchema = findLCGrpPolSchema(strRiskCode);

		if (tLCGrpPolSchema == null) {
			buildError("formatLCPol", "找不到指定险种所对应的集体保单，险种为：" + strRiskCode);
			return null;
		}

		// 校验险种是否存在，能不能挂在其指定主险中
		tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
		tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		if ("Y".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != "") {
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				tLCPolSchema.setCValiDate(EdorValiDate);
			}

		}

		tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		tLCPolSchema.setSaleChnl(tLCGrpPolSchema.getSaleChnl());
		tLCPolSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
		tLCPolSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
		tLCPolSchema.setAgentType(tLCGrpPolSchema.getAgentType());
		tLCPolSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
		tLCPolSchema.setAgentCode1(tLCGrpPolSchema.getAgentCode1());
		tLCPolSchema.setAppntName(tLCGrpPolSchema.getGrpName());
		tLCPolSchema.setAppntNo(tLCGrpPolSchema.getCustomerNo());
		tLCPolSchema.setPolApplyDate(this.mLCGrpContSchema.getPolApplyDate());
		String mainPolId = StrTool.cTrim(tLCPolSchema.getMainPolNo());
		// String mainRiskCode = tLCPolSchema.getRiskCode();
		// if (!"".equals(mainPolId) && !strID.equals(mainPolId))
		// {
		// //如果不是主险保单，则查找
		// LCPolSchema tMainLCPolSchema = findCacheLCPolSchema(mainPolId);
		// //如果从导入轨迹中没有找到
		// if (tMainLCPolSchema == null)
		// {
		// buildError("formatLCPol",
		// "数据库和导入轨迹中找不到附加险被保人(" + mainPolId +
		// ")对应的个人主险保单纪录");
		//
		// return null;
		// }
		// // mainRiskCode= tMainLCPolSchema.getRiskCode();
		// tLCPolSchema.setMainPolNo(tMainLCPolSchema.getPolNo());
		// tLCPolSchema.setContNo(tMainLCPolSchema.getContNo().trim());
		// //被保险人也应该存在
		// String insuredIndex = tLCPolSchema.getInsuredNo();
		// if (mainPolId.indexOf("-") > 0)
		// {
		// String[] arr = mainPolId.split("-");
		//
		// insuredIndex = arr[1];
		// }
		// //附加险的被保人即是主险的被保人
		// tLCInsuredSchema = this.findInsuredfromCache(insuredIndex);
		// if (tLCInsuredSchema == null)
		// {
		// mErrors.clearErrors();
		// buildError("formatLCPol",
		// "新增附加险:找不到对应主险的被保人信息,ID=" + insuredIndex);
		// return null;
		// }
		//
		// }
		// else
		// {

		// 本身为主险保单,设置主险保单号空，查找或创建被保险人
		tLCPolSchema.setMainPolNo("");
		tLCInsuredSchema = this.findInsuredfromCache(tLCPolSchema
				.getInsuredNo());
		if (tLCInsuredSchema == null) {
			// 需要创建新被保险人
			VData tData = this.getContData(tLCPolSchema.getContNo(),
					tLCPolSchema.getInsuredNo(), tLCGrpPolSchema);
			if (tData == null) {
				return null;
			}

			MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
			if (map != null && map.keySet().size() > 0)
				tReturnData.add(map);
			tLCInsuredSchema = (LCInsuredSchema) tData.getObjectByObjectName(
					"LCInsuredSchema", 0);

		}
		// }

		tLCPolSchema.setContNo(tLCInsuredSchema.getContNo().trim());
		tLCPolSchema.setInsuredSex(tLCInsuredSchema.getSex());
		tLCPolSchema.setOccupationType(tLCInsuredSchema.getOccupationType()); //
		tLCPolSchema.setInsuredBirthday(tLCInsuredSchema.getBirthday());
		tLCPolSchema.setInsuredName(tLCInsuredSchema.getName());
		tLCPolSchema.setInsuredNo(tLCInsuredSchema.getInsuredNo());

		tReturnData.add(tLCPolSchema);

		return tReturnData;
	}

	/**
	 * 通过计划附加险查找主险Id
	 * 
	 * @param contId
	 *            String
	 * @param insuredId
	 *            String
	 * @param riskcode
	 *            String
	 * @return String
	 */
	public String getMainRiskCode(String planId, String riskcode) {
		LCContPlanRiskSchema schema = null;
		String mainRiskCode = "";
		for (int i = 1; i <= this.mLCContPlanRiskSet.size(); i++) {
			schema = mLCContPlanRiskSet.get(i);
			if (schema.getContPlanCode().equals(planId)
					&& schema.getRiskCode().equals(riskcode)) {
				// return this.mLCContPlanRiskSet.get(i).getMainRiskCode()
				if (schema.getMainRiskCode().equals("000000")
						|| "".equals(StrTool.cTrim(schema.getMainRiskCode()))) {
					mainRiskCode = riskcode;
				} else {
					mainRiskCode = schema.getMainRiskCode();
				}
				break;
			}
		}
		return mainRiskCode;
	}

	/**
	 * 从被保险人咧表中查找到id号一样的被保险人，创建连身被保险人信息。
	 * 
	 * @param mainInsuredNo
	 *            String
	 * @param relaIns
	 *            String[]
	 * @return LCInsuredRelatedSet
	 */
	public LCInsuredRelatedSet getInsuredRelaData(String mainInsuredNo,
			String[] relaIns,String tBatchNo) {
		LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
		// LCInsuredSchema mainInsured = this.findInsuredfromCache(mainInsureId);
		// if ( mainInsured ==null ){
		// CError.buildErr(this, "查找主被保险人["+ mainInsured +"]信息出错!");
		// return null;
		// }
		for (int i = 0; i < relaIns.length; i++) {
			String tmpId = relaIns[i];
			LCInsuredSchema tSchema = this.findInsuredfromCache(tmpId);
			if (tSchema == null) {
				// 需要创建新的LCInsuredSchema
				VData tInsData = this.getInsuredData(tmpId);
				if (tInsData == null) {
					CError.buildErr(this, "在准备连身被保险人[" + tmpId + "]的时候出现错误");
					return null;
				}

				tSchema = (LCInsuredSchema) tInsData.getObjectByObjectName(
						"LCInsuredSchema", 0);
			}
			// modify on 2009-02-11
			LCInsuredRelatedSchema tLCInsuredRelatedSchema = this.mLCInsuredRelatedSet.get(i+1);
            if(tLCInsuredRelatedSchema == null){
            	CError.buildErr(this, "在准备连身被保险人[" + tmpId + "]信息的时候出现错误");
                return null;
            }
            String RelationToInsured = tLCInsuredRelatedSchema.getRelationToInsured();
            String MainSequenceNo = tLCInsuredRelatedSchema.getMainCustomerNo();
            String CustomerNo = tLCInsuredRelatedSchema.getCustomerNo();
            mRelationToRisk = tLCInsuredRelatedSchema.getModifyTime();//在GrpPolParse.java中暂时保存
    		String RiskCode1[]=mRelationToRisk.split(";");
    		String RiskCode2[]=mRelationToRisk.split("；");
    		      mRiskCode=RiskCode2;//模板中可能用";"或"；" 所以要判断
    		if(RiskCode1.length>RiskCode2.length)
    			mRiskCode=RiskCode1;
            logger.debug("该连带被保人的主被保人的险种为:"+mRelationToRisk);
             if (tSchema != null) {        
            	 if(mRiskCode!=null){
            		 for(int k =0;k<mRiskCode.length;k++){
            			 LCInsuredRelatedSchema tRelaSchema = new LCInsuredRelatedSchema();
            			 tRelaSchema.setCustomerNo(tSchema.getInsuredNo());//客户号
            			 tRelaSchema.setName(tSchema.getName());//姓名
            			 tRelaSchema.setIDNo(tSchema.getIDNo());//证件号码
            			 tRelaSchema.setIDType(tSchema.getIDType());//证件类型
            			 if(RelationToInsured!=null &&!"".equals(RelationToInsured)&& !RelationToInsured.equals("00")){
            				 if(MainSequenceNo==null || "".equals(MainSequenceNo)){
            					 CError.buildErr(this, "被保险人[" + tmpId + "]的主被保人序号错误");
            					 return null;
            				 }
            				 
            				 String tMainInsuredNo = queryLCInsuRelaNo(MainSequenceNo, tSchema.getPrtNo(),tBatchNo,mRiskCode[k],"C");
            				 String tMainPolNo = queryLCInsuRelaNo(MainSequenceNo, tSchema.getPrtNo(),tBatchNo,mRiskCode[k],"P");
            				 if(tMainInsuredNo==null || "".equals(tMainInsuredNo)){
            					 CError.buildErr(this, "在查找连身被保险人[" + tmpId + "]的客户号时候出现错误");
            					 return null;
            				 }
            				 if(tMainPolNo==null || "".equals(tMainPolNo)){
            					 CError.buildErr(this, "在查找连身被保险人[" + tmpId + "]的保单号时候出现错误");
            					 return null;
            				 }
            				 tRelaSchema.setMainCustomerNo(tMainInsuredNo);
            				 tRelaSchema.setPolNo(tMainPolNo);
            			 }else{
            				 tRelaSchema.setMainCustomerNo(mainInsuredNo);//主客户号
            			 }
            			 
            			 tRelaSchema.setAddressNo(tSchema.getAddressNo());//地址编码
            			 tRelaSchema.setSex(tSchema.getSex());//性别
            			 tRelaSchema.setRelationToInsured(tSchema.getRelationToMainInsured());//与主被保人关系
            			 tRelaSchema.setOperator(mGlobalInput.Operator);//操作员
            			 tRelaSchema.setMakeDate(PubFun.getCurrentDate());//入机日期
            			 tRelaSchema.setMakeTime(PubFun.getCurrentTime());//入机时间
            			 tRelaSchema.setModifyDate(tRelaSchema.getMakeDate());//修改日期
            			 tRelaSchema.setModifyTime(tRelaSchema.getMakeTime());//修改时间
            			 tRelaSchema.setBirthday(tSchema.getBirthday());//生日
            			 tRelaSchema.setSequenceNo(tSchema.getSequenceNo());//公司内部序号
            			 
            			 tLCInsuredRelatedSet.add(tRelaSchema);
            		 }
            	 }
               }
//			if (tSchema != null) {
//				LCInsuredRelatedSchema tRelaSchema = new LCInsuredRelatedSchema();
//				tRelaSchema.setCustomerNo(tSchema.getInsuredNo());
//				tRelaSchema.setName(tSchema.getName());
//				if (tSchema.getIDType().equals("0")) {
//					tRelaSchema.setIDNo(tSchema.getIDNo().toUpperCase());
//				} else {
//					tRelaSchema.setIDNo(tSchema.getIDNo());
//				}
//				tRelaSchema.setMainCustomerNo(mainInsuredNo);
//				tRelaSchema.setAddressNo(tSchema.getAddressNo());
//				// tRelaSchema.setPolNo();
//				tRelaSchema.setSex(tSchema.getSex());
//				tRelaSchema.setRelationToInsured(tSchema
//						.getRelationToMainInsured());
//				tRelaSchema.setOperator(mGlobalInput.Operator);
//				tRelaSchema.setMakeDate(PubFun.getCurrentDate());
//				tRelaSchema.setMakeTime(PubFun.getCurrentTime());
//				tRelaSchema.setModifyDate(tRelaSchema.getMakeDate());
//				tRelaSchema.setModifyTime(tRelaSchema.getMakeTime());
//				tRelaSchema.setBirthday(tSchema.getBirthday());
//				//add by liuqh 2009-02-09
//				tRelaSchema.setSequenceNo(tSchema.getSequenceNo());
//				tLCInsuredRelatedSet.add(tRelaSchema);
//			}

		}
		return tLCInsuredRelatedSet;
	}

	/* 
	 * 
     * 按公司内部序号和印刷号查询关联被保人责任的客户号
     */
    private String queryLCInsuRelaNo(String SequenceNo,String PrtNo,String tBatchNo,String tRiskCode,String tFlag){
    	String CustomerNo = "";
    	String PolNo = "";
        String tStrSQL = "";
        tStrSQL =  "select * from LCInsuredRelated where 1=1 " +
        		   "and CustomerNo in(select a.InsuredNo from LCInsured a,LCPol b where 1=1 and a.InsuredNo=b.InsuredNo and a.PrtNo=b.PrtNo and b.PolTypeFlag='0' and b.PrtNo='"+PrtNo+"') " 
        		   +"and SequenceNo='"+SequenceNo+"'"
        		   +" and polno =(select polno from lcpol where contno in (select contno from lcgrpimportlog a where "
        		   +" batchno='"+tBatchNo+"' and id ="+SequenceNo+") and riskcode ='"+tRiskCode+"')"//取为此次批次号的第一个保存的
        		   ;

        LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
        LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB.executeQuery(tStrSQL);
        if(tLCInsuredRelatedSet!=null && tLCInsuredRelatedSet.size()>0){
        	CustomerNo = tLCInsuredRelatedSet.get(1).getCustomerNo();
        	PolNo = tLCInsuredRelatedSet.get(1).getPolNo();
        }
        logger.debug("-----> GrpPolImpInfo-729-主被保险人的客户号为:"+CustomerNo);
        logger.debug("-----> GrpPolImpInfo-729-主被保险人的保单号为:"+PolNo);
        if(tFlag.equals("C"))
           return CustomerNo;
        else
           return PolNo;
    }
	/**
	 * 缓存数据准备成功的险种保单信息
	 * 
	 * @param strID
	 *            String
	 * @param aLCPolSchema
	 *            LCPolSchema
	 */
	public void cachePolInfo(String strID, LCPolSchema aLCPolSchema) {

		mMainPolMap.put(strID, aLCPolSchema);
	}

	/**
	 * 通过主险保单号查询保单
	 * 
	 * @param mainPolNo
	 *            String
	 * @return LCPolSchema
	 */
	public LCPolSchema findMainPolSchema(String mainPolNo) {
		Iterator it = mMainPolMap.keySet().iterator();
		LCPolSchema tmpSchema;
		while (it.hasNext()) {
			tmpSchema = (LCPolSchema) mMainPolMap.get(it.next());
			if (tmpSchema.getPolNo().equals(mainPolNo)) {
				return tmpSchema;
			}

		}
		return null;
	}

	/**
	 * 缓存创建的合同信息
	 * 
	 * @param strID
	 *            String
	 * @param aLCContSchema
	 *            LCContSchema
	 */
	private void cacheLCContSchema(String strID, LCContSchema aLCContSchema) {
		mContCache.put(strID, aLCContSchema);
	}

	/**
	 * 缓存被保险人信息，该被保险人被创建或查询出具体信息
	 * 
	 * @param strID
	 *            String
	 * @param schema
	 *            LCInsuredSchema
	 */
	private void cacheLCInsuredSchema(String strID, LCInsuredSchema schema) {
		mInsuredMap.put(strID, schema);
	}

	private LCPolSchema findCacheLCPolSchema(String strID) {
		return (LCPolSchema) this.mMainPolMap.get(strID);
	}

	/**
	 * 根据strID获取被保险人信息，该被保险人被创建或查询出具体信息
	 * 
	 * @param strID
	 *            String
	 * @return LCInsuredSchema
	 */
	public LCInsuredSchema findInsuredfromCache(String strID) {
		return (LCInsuredSchema) mInsuredMap.get(strID);
	}

	/**
	 * 从缓存中获取合同信息
	 * 
	 * @param strID
	 *            String
	 * @return LCContSchema
	 */
	public LCContSchema findLCContfromCache(String strID) {
		Object dest = mContCache.get(strID);
		if (dest == null)
			return null;
		if (dest.getClass().getName().equals("java.lang.String"))
			return null;
		return (LCContSchema) dest;
	}

	/**
	 * 从缓存中根据id号获取未被修改的被保险人信息
	 * 
	 * @param index
	 *            int
	 * @return LCInsuredSchema
	 */
	private LCInsuredSchema findInsured(String index) {
		// if (index < 0 || index > mLCInsuredSet.size())return null;
		// return mLCInsuredSet.get(index);
		for (int i = 1; i <= mLCInsuredSet.size(); i++) {
			if (mLCInsuredSet.get(i).getPrtNo().equals(index)) {
				return mLCInsuredSet.get(i);
			}
		}
		return null;
	}

	/**
	 * 记录失败导入的日志,待完成
	 * 
	 * @param batchNo
	 *            String
	 * @param grpContNo
	 *            String
	 * @param contId
	 *            String
	 * @param InsuredId
	 *            String
	 * @param polId
	 *            String
	 * @param contPlanCode
	 *            String
	 * @param riskCode
	 *            String
	 * @param errInfo
	 *            CErrors
	 * @param globalInput
	 *            GlobalInput
	 */
	public boolean logError(String batchNo, String grpContNo, String contId,
			String insuredId, String polId, String contPlanCode,
			String riskCode, LCInsuredSchema insuredSchema, CErrors errInfo,
			GlobalInput globalInput) {
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
		LCGrpImportLogSchema delLCGrpImportLogSchema = new LCGrpImportLogSchema();
		// 设置主键
		delLCGrpImportLogSchema.setContID(contId);
		delLCGrpImportLogSchema.setBatchNo(batchNo);

		LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
		// 避免报错
		if (insuredSchema == null)
			insuredSchema = new LCInsuredSchema();
		// 设置主键
		tLCGrpImportLogSchema.setIdNo(polId);
		tLCGrpImportLogSchema.setContID(contId);
		tLCGrpImportLogSchema.setID(contId);
		tLCGrpImportLogSchema.setBatchNo(batchNo);
		tLCGrpImportLogSchema.setContNo(contId);
		tLCGrpImportLogSchema.setRiskCode(riskCode);
		tLCGrpImportLogSchema.setGrpContNo(grpContNo);
		tLCGrpImportLogSchema.setOtherNo(grpContNo);
		tLCGrpImportLogSchema.setOtherNoType("4");
		tLCGrpImportLogSchema.setInsuredID(insuredId);
		tLCGrpImportLogSchema.setContPlanCode(contPlanCode);
		tLCGrpImportLogSchema.setInsuredNo(insuredSchema.getInsuredNo());
		tLCGrpImportLogSchema.setInsuredName(insuredSchema.getName());
		tLCGrpImportLogSchema.setOperator(globalInput.Operator);
		tLCGrpImportLogSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpImportLogSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGrpImportLogSchema.setErrorType("1");
		tLCGrpImportLogSchema.setErrorState("1");
		String errMess = "";
		for (int i = 0; i < errInfo.getErrorCount(); i++) {
			errMess += errInfo.getError(i).errorMessage + ";";
		}

		if ("".equals(errMess))
			errMess = "未捕捉到的错误。";
		errMess.replaceAll("\n", "");
		tLCGrpImportLogSchema.setErrorInfo(errMess);

		tLCGrpImportLogDB.setSchema(delLCGrpImportLogSchema);
		boolean res = true;
		res = tLCGrpImportLogDB.delete();
		//if (res) 
		{
			tLCGrpImportLogDB.setSchema(tLCGrpImportLogSchema);
			res = tLCGrpImportLogDB.insert();
		}
		return res;
	}

	/**
	 * 记录成功导入的信息,待完成
	 * 
	 * @param batchNo
	 *            String
	 * @param grpContNo
	 *            String
	 * @param contId
	 *            String
	 * @param InsuredId
	 *            String
	 * @param polId
	 *            String
	 * @param aLCInsuredSchema
	 *            LCInsuredSchema
	 * @param aPolSchema
	 *            LCPolSchema
	 * @param errInfo
	 *            CErrors
	 * @param globalInput
	 *            GlobalInput
	 */
	public MMap logSucc(String batchNo, String grpContNo, String contId,
			String prtNo, String contNo, GlobalInput globalInput) {
		MMap tmpMap = new MMap();
		LCGrpImportLogSchema delLCGrpImportLogSchema = new LCGrpImportLogSchema();

		// 设置主键
		delLCGrpImportLogSchema.setContID(contId);
		delLCGrpImportLogSchema.setBatchNo(batchNo);
		tmpMap.put(delLCGrpImportLogSchema, "DELETE");

		LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
		tLCGrpImportLogSchema.setID(contId);  //update by liuqh 
		tLCGrpImportLogSchema.setOtherNo(grpContNo);//add by liuqh 
		tLCGrpImportLogSchema.setOtherNoType("4"); //add by liuqh 
		tLCGrpImportLogSchema.setBatchNo(batchNo);
		tLCGrpImportLogSchema.setGrpContNo(grpContNo);
		tLCGrpImportLogSchema.setContNo(contNo);
		tLCGrpImportLogSchema.setOperator(globalInput.Operator);
		tLCGrpImportLogSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpImportLogSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGrpImportLogSchema.setErrorType("1");
		tLCGrpImportLogSchema.setErrorState("0");
		tLCGrpImportLogSchema.setPrtNo(prtNo);
		tLCGrpImportLogSchema.setErrorInfo("导入成功");
		tmpMap.put(tLCGrpImportLogSchema, "INSERT");

		return tmpMap;

	}

	/**
	 * 查询系统中是否已经有改日志信息
	 * 
	 * @param batchNo
	 *            String
	 * @param ContId
	 *            String
	 * @param InsuredId
	 *            String
	 * @param polId
	 *            String
	 * @return boolean
	 */
	public LCGrpImportLogSchema getLogInfo(String batchNo, String contId) {
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
		tLCGrpImportLogDB.setBatchNo(batchNo);
		tLCGrpImportLogDB.setContID(contId);
		boolean bExist = tLCGrpImportLogDB.getInfo();
		if (bExist) {
			return tLCGrpImportLogDB.getSchema();
		}
		return null;
	}

	/**
	 * 写日志信息
	 * 
	 * @param strID
	 *            String
	 * @param aLCInsuredSchema
	 *            LCInsuredSchema
	 * @param bSucc
	 *            boolean
	 * @param errInfo
	 *            CErrors
	 * @param globalInput
	 *            GlobalInput
	 */
	// public void logError(String strID, LCInsuredSchema aLCInsuredSchema,
	// boolean bSucc, CErrors errInfo,
	// GlobalInput globalInput)
	// {
	// LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
	//
	// // 设置主键
	// tLCGrpImportLogSchema.setIDNo(strID);
	// tLCGrpImportLogSchema.setBatchNo(m_strBatchNo);
	//
	// String strRiskCode = strID.substring(strID.lastIndexOf("-"));
	// String plancode = aLCInsuredSchema.getContPlanCode();
	// tLCGrpImportLogSchema.setRiskCode(plancode);
	// LCGrpPolSchema tLCGrpPolSchema = findLCGrpPolSchema(strRiskCode);
	// tLCGrpImportLogSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
	//
	// LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
	// tLCGrpImportLogDB.setSchema(tLCGrpImportLogSchema);
	//
	// boolean bExist = tLCGrpImportLogDB.getInfo();
	// String strState = tLCGrpImportLogDB.getErrorState();
	//
	// if (bExist)
	// {
	// tLCGrpImportLogSchema.setSchema(tLCGrpImportLogDB);
	// tLCGrpImportLogDB.delete();
	// }
	//
	// tLCGrpImportLogDB = new LCGrpImportLogDB();
	// tLCGrpImportLogDB.setSchema(tLCGrpImportLogSchema);
	//
	// tLCGrpImportLogDB.setOperator(globalInput.Operator);
	// tLCGrpImportLogDB.setMakeDate(PubFun.getCurrentDate());
	// tLCGrpImportLogDB.setMakeTime(PubFun.getCurrentTime());
	// tLCGrpImportLogDB.setErrorType("1");
	//
	// if (bSucc)
	// {
	// tLCGrpImportLogDB.setErrorState("0");
	// tLCGrpImportLogDB.setPolNo(aLCInsuredSchema.getContNo());
	// tLCGrpImportLogDB.setInsuredNo(aLCInsuredSchema.getInsuredNo());
	// tLCGrpImportLogDB.setPrtNo(tLCGrpPolSchema.getPrtNo());
	// }
	// else
	// {
	// if (bExist)
	// {
	// tLCGrpImportLogDB.setErrorState(strState);
	// }
	// else
	// {
	// tLCGrpImportLogDB.setErrorState("1");
	// }
	// tLCGrpImportLogDB.setErrorInfo(errInfo.getFirstError());
	// }
	//
	// tLCGrpImportLogDB.insert();
	//
	// }
	/**
	 * 写日志信息
	 * 
	 * @param strID
	 *            String
	 * @param aLCPolSchema
	 *            LCPolSchema
	 * @param bSucc
	 *            boolean
	 * @param errInfo
	 *            CErrors
	 * @param globalInput
	 *            GlobalInput
	 */
	// public void logError(String strID,
	// LCPolSchema aLCPolSchema,
	// boolean bSucc,
	// CErrors errInfo,
	// GlobalInput globalInput)
	// {
	//
	// LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
	//
	// // 设置主键
	// tLCGrpImportLogSchema.setIDNo(strID);
	// tLCGrpImportLogSchema.setBatchNo(m_strBatchNo);
	//
	// String strRiskCode = aLCPolSchema.getRiskCode();
	// tLCGrpImportLogSchema.setRiskCode(strRiskCode);
	//
	// LCGrpPolSchema tLCGrpPolSchema = findLCGrpPolSchema(strRiskCode);
	// tLCGrpImportLogSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
	//
	// LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
	// tLCGrpImportLogDB.setSchema(tLCGrpImportLogSchema);
	//
	// boolean bExist = tLCGrpImportLogDB.getInfo();
	// String strState = tLCGrpImportLogDB.getErrorState();
	//
	// if (bExist)
	// {
	// tLCGrpImportLogSchema.setSchema(tLCGrpImportLogDB);
	// tLCGrpImportLogDB.delete();
	// }
	//
	// tLCGrpImportLogDB = new LCGrpImportLogDB();
	// tLCGrpImportLogDB.setSchema(tLCGrpImportLogSchema);
	//
	// tLCGrpImportLogDB.setOperator(globalInput.Operator);
	// tLCGrpImportLogDB.setMakeDate(PubFun.getCurrentDate());
	// tLCGrpImportLogDB.setMakeTime(PubFun.getCurrentTime());
	// tLCGrpImportLogDB.setErrorType("1");
	//
	// if (bSucc)
	// {
	// tLCGrpImportLogDB.setErrorState("0");
	// tLCGrpImportLogDB.setPolNo(aLCPolSchema.getPolNo());
	// tLCGrpImportLogDB.setInsuredNo(aLCPolSchema.getInsuredNo());
	// tLCGrpImportLogDB.setPrtNo(aLCPolSchema.getPrtNo());
	// }
	// else
	// {
	// if (bExist)
	// {
	// tLCGrpImportLogDB.setErrorState(strState);
	// }
	// else
	// {
	// tLCGrpImportLogDB.setErrorState("1");
	// }
	// tLCGrpImportLogDB.setErrorInfo(errInfo.getFirstError());
	// }
	//
	// tLCGrpImportLogDB.insert();
	// }

	/**
	 * 获取错误信息
	 * 
	 * @return LCGrpImportLogSet
	 */
	public LCGrpImportLogSet getErrors() {
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();

		String strSQL = "SELECT * FROM LCGrpImportLog" + " WHERE BatchNo = '"
				+ m_strBatchNo + "'"
				+ " AND ( ErrorState = '1' OR ErrorInfo IS NOT NULL ) and errorinfo<>'导入成功' "
				+ " ORDER BY TO_NUMBER(IDNo)";

		return tLCGrpImportLogDB.executeQuery(strSQL);
	}

	/**
	 * 创建错误信息
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "GrpPolImpInfo";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 判断险种描述是否已经在缓存中 如果不在，从库中查找并缓存 查找失败返回false
	 * 
	 * @param strRiskCode
	 *            String
	 * @return int
	 */
	private boolean isCachedRisk(String strRiskCode) {

		LMRiskAppSchema tRiskAppSchema = this.findLMRiskAppSchema(strRiskCode);
		if (tRiskAppSchema == null) {
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			tLMRiskAppDB.setRiskCode(strRiskCode);
			if (!tLMRiskAppDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
				return false;
			}
			tRiskAppSchema = tLMRiskAppDB.getSchema();
			m_LMRiskAppSet.add(tRiskAppSchema.getSchema());

			if (initLMRisk(strRiskCode) == false)
				return false;
		}

		return true;
		// if (tRiskAppSchema.getSubRiskFlag() != null
		// && tRiskAppSchema.getSubRiskFlag().equals("M")) {
		// return 1;
		// }
		// else {
		// return 0;
		// }
	}

	/**
	 * 初始化险种描述数据信息
	 * 
	 * @param strRiskCode
	 * @return
	 */
	private boolean initLMRisk(String strRiskCode) {
		LMRiskDB tLMRiskDB = new LMRiskDB();

		tLMRiskDB.setRiskCode(strRiskCode);

		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			return false;
		}

		m_LMRiskSet.add(tLMRiskDB.getSchema());
		return true;
	}

	/**
	 * 初始化集体单位信息
	 * 
	 * @param custNo
	 *            String
	 * @return boolean
	 */
	private boolean initLDGrp(String custNo) {
		LDGrpDB tLDGrpDB = new LDGrpDB();

		tLDGrpDB.setCustomerNo(custNo);

		if (tLDGrpDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDGrpDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "dealDataPerson";
			tError.errorMessage = "LDGrp表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		m_LDGrpSet.add(tLDGrpDB.getSchema());
		return true;
	}

	/**
	 * 出始化集体投保人信息，并缓存
	 * 
	 * @param grpContNo
	 *            String
	 * @param custNo
	 *            String
	 * @return boolean
	 */
	private boolean initLDGrpAppnt(String grpContNo, String custNo) {
		LCGrpAppntDB tLDGrpAppntDB = new LCGrpAppntDB();

		tLDGrpAppntDB.setCustomerNo(custNo);
		tLDGrpAppntDB.setGrpContNo(grpContNo);
		if (tLDGrpAppntDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDGrpAppntDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "dealDataPerson";
			tError.errorMessage = "LDGrp表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// m_LDGrpSet.add(tLDGrpDB.getSchema());
		this.mLCGrpAppntSchema = tLDGrpAppntDB.getSchema();
		return true;
	}

	public LCGrpAppntSchema getLCGrpAppntSchema() {
		return this.mLCGrpAppntSchema;
	}

	/**
	 * 根据个人保单的险种编码找到对应的集体保单
	 * 
	 * @param strRiskCode
	 * @return
	 */
	public LCGrpPolSchema findLCGrpPolSchema(String strRiskCode) {
		for (int nIndex = 0; nIndex < m_LCGrpPolSet.size(); nIndex++) {
			if (m_LCGrpPolSet.get(nIndex + 1).getRiskCode().equals(strRiskCode)) {
				return m_LCGrpPolSet.get(nIndex + 1);
			}
		}

		return null;
	}

	// houzm add
	/**
	 * 根据个人保单的险种编码找到对应的集体保单
	 * 
	 * @param strRiskCode
	 * @return
	 */
	public LMRiskAppSchema findLMRiskAppSchema(String strRiskCode) {
		LMRiskAppSchema tLMRiskAppSchema = null;

		for (int nIndex = 0; nIndex < m_LMRiskAppSet.size(); nIndex++) {
			tLMRiskAppSchema = m_LMRiskAppSet.get(nIndex + 1);

			if (tLMRiskAppSchema.getRiskCode().equals(strRiskCode)) {
				return tLMRiskAppSchema;
			}
		}

		return null;
	}

	/**
	 * 根据个人保单的险种编码找到对应的集体保单
	 * 
	 * @param strRiskCode
	 * @return
	 */
	public LMRiskSchema findLMRiskSchema(String strRiskCode) {
		LMRiskSchema tLMRiskSchema = null;

		for (int nIndex = 0; nIndex < m_LMRiskSet.size(); nIndex++) {
			tLMRiskSchema = m_LMRiskSet.get(nIndex + 1);

			if (tLMRiskSchema.getRiskCode().equals(strRiskCode)) {
				return tLMRiskSchema;
			}
		}

		return null;
	}

	/**
	 * 查找集体单位信息
	 * 
	 * @param GrpNo
	 *            String
	 * @return LDGrpSchema
	 */
	public LDGrpSchema findLDGrpSchema(String GrpNo) {
		LDGrpSchema tLDGrpSchema = null;
		for (int nIndex = 0; nIndex < m_LDGrpSet.size(); nIndex++) {
			tLDGrpSchema = m_LDGrpSet.get(nIndex + 1);
			if (tLDGrpSchema.getCustomerNo().equals(GrpNo)) {
				return tLDGrpSchema;
			}

		}
		return null;
	}

	/**
	 * 获取缓存的合同信息
	 * 
	 * @return HashMap
	 */
	public HashMap getContCache() {
		return this.mContCache;
	}

	/**
	 * 合同信息查找或创建，涉及到被保险人 当被保险人不存在的时候;创建被保险人，存在则取用 合同不存在，创建;存在，取用
	 * 
	 * @param polSchema
	 *            LCPolSchema
	 * @param insuredSchema
	 *            LCInsuredSchema
	 * @return boolean
	 */
	public VData getContData(String contId, String insuredId,
			LCGrpPolSchema tLCGrpPolSchema) {
		VData tInputData = new VData();
		VData tResult = new VData();
		LCContSchema contSchema = null;
		LCInsuredSchema tLCInsuerdSchema = null;
		LLAccountSchema tLLAccountSchema = null; // added by wanglei
		if (contId != null)
			contSchema = findLCContfromCache(contId);
		if (insuredId != null)
			tLCInsuerdSchema = this.findInsuredfromCache(insuredId);
		if (tLCInsuerdSchema != null && contSchema != null
				&& !"".equals(StrTool.cTrim(contSchema.getContNo()))) { // 已经创建，直接返回

			tResult.add(contSchema);
			tResult.add(tLCInsuerdSchema);
			tLLAccountSchema = new LLAccountSchema();
			tLLAccountSchema.setGrpContNo(tLCInsuerdSchema.getGrpContNo());
			tLLAccountSchema.setAccount(tLCInsuerdSchema.getBankAccNo());
			tLLAccountSchema.setName(tLCInsuerdSchema.getName());
			tLLAccountSchema.setContNo(tLCInsuerdSchema.getContNo());
			tLLAccountSchema.setBankCode(tLCInsuerdSchema.getBankCode());
			tResult.add(tLLAccountSchema);
			return tResult;
		}
		Reflections ref = new Reflections();
		// boolean needtocach = false;
		boolean createIns = false;
		boolean createCont = false;
		boolean createLLAccount = false;
		// LDPersonSchema tPersonSchema;
		if (contSchema == null) {
			createCont = true;
		}
		if (tLLAccountSchema == null) {
			createLLAccount = true;
		}
		if (tLCInsuerdSchema == null) {
			// 如果被保险人没有被创建过，则从原始解析的xml中获取
			// 标识要创建被保险人
			createIns = true;
			tLCInsuerdSchema = this.findInsured(insuredId);
			if (tLCInsuerdSchema == null) {
				CError.buildErr(this, "导入文件中没有被保险人[" + insuredId + "]");
				return null;
			}
			if (!createCont) { // 合同已经存在，
				tLCInsuerdSchema.setContNo(contSchema.getContNo());

			}
		}
		//
		LDPersonSchema tPersonSchema = new LDPersonSchema();
		ref.transFields(tPersonSchema, tLCInsuerdSchema);
		tPersonSchema.setState(tLCInsuerdSchema.getInsuredStat());
		tPersonSchema.setCustomerNo(tLCInsuerdSchema.getInsuredNo());
		LDPersonSchema nPersonSchema = checkInSystemPerson(tPersonSchema);
		LCInsuredSchema cacheInsuredSchema = new LCInsuredSchema();
		cacheInsuredSchema.setSchema(tLCInsuerdSchema);

		if (nPersonSchema != null) {
			nPersonSchema.setOccupationCode(tLCInsuerdSchema.getOccupationCode());
			nPersonSchema.setOccupationType(tLCInsuerdSchema.getOccupationType());
			tPersonSchema = nPersonSchema;
			tLCInsuerdSchema.setName(tPersonSchema.getName());
			tLCInsuerdSchema.setSex(tPersonSchema.getSex());
			tLCInsuerdSchema.setBirthday(tPersonSchema.getBirthday());
			if (tPersonSchema.getIDType().equals("0")) {
				tLCInsuerdSchema.setIDNo(tPersonSchema.getIDNo().toUpperCase());
			} else {
				tLCInsuerdSchema.setIDNo(tPersonSchema.getIDNo());
			}
			tLCInsuerdSchema.setIDType(tPersonSchema.getIDType());
			tLCInsuerdSchema.setInsuredNo(nPersonSchema.getCustomerNo());
		}
		MMap map = new MMap();
		if (createCont) {
			contSchema = new LCContSchema();

			if (!createIns) {
				// 被保险人已经创建
				contSchema.setInsuredNo(tPersonSchema.getCustomerNo());
			} else
				contSchema.setInsuredNo("");
			contSchema.setInsuredSex(tLCInsuerdSchema.getSex());
			contSchema.setInsuredName(tLCInsuerdSchema.getName());
			contSchema.setInsuredBirthday(tLCInsuerdSchema.getBirthday());
			contSchema.setBankAccNo(tLCInsuerdSchema.getBankAccNo());
			contSchema.setBankCode(tLCInsuerdSchema.getBankCode());
			contSchema.setAccName(tLCInsuerdSchema.getAccName());
			// 借用被保人信用等级字段保存 保单类型标记
			contSchema.setPolType(tLCInsuerdSchema.getCreditGrade());
			if ("1".equals(tLCInsuerdSchema.getCreditGrade())) {
				// 借用入机时间字段保存 被保人数目
				contSchema.setPeoples(String.valueOf(tLCInsuerdSchema
						.getMakeTime()));
			} else {
				contSchema.setPeoples("1");
			}
			//tongmeng 2009-03-23 modify
			//支持团单下个单指定生效日
			if(tLCInsuerdSchema.getModifyDate()!=null&&!tLCInsuerdSchema.getModifyDate().equals(""))
			{
				contSchema.setCValiDate(tLCInsuerdSchema.getModifyDate());
				//tLCInsuerdSchema.setModifyDate(null);
			}
			
			contSchema.setContType("2");
			// contSchema.setPolType("0");

			contSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
			contSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
			contSchema.setAppntName(tLCGrpPolSchema.getGrpName());
			contSchema.setAppntNo(tLCGrpPolSchema.getCustomerNo());
			contSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
			contSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
			// contSchema.setInputOperator(tLCGrpPolSchema.getOperator());
			contSchema.setOperator(mGlobalInput.Operator);
			contSchema.setManageCom(mGlobalInput.ManageCom);
			contSchema.setMakeDate(tLCGrpPolSchema.getMakeDate());
			contSchema.setMakeTime(tLCGrpPolSchema.getMakeTime());
			contSchema.setModifyDate(tLCGrpPolSchema.getModifyDate());
			contSchema.setModifyTime(tLCGrpPolSchema.getModifyTime());
			// 增加了保全分支判断,如果是 保全增加被保人，设置保全保存标记 2
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != ""
					&& "2".equals(StrTool.cTrim(mTransferData.getValueByName(
							"BQFlag").toString())))
			// &&"NI".equals(StrTool.cTrim(mTransferData.getValueByName("EdorType").toString())))
			{
				// 保全保存标记
				contSchema.setAppFlag("2");
			}
		}
		if (createLLAccount) { // added by wanglei
			tLLAccountSchema = new LLAccountSchema();
			tLLAccountSchema.setContNo(tLCInsuerdSchema.getContNo());
			tLLAccountSchema.setName(tLCInsuerdSchema.getName());
			tLLAccountSchema.setBankCode(tLCInsuerdSchema.getBankCode());
			tLLAccountSchema.setAccount(tLCInsuerdSchema.getBankAccNo());
			tLLAccountSchema.setBnfRate(1);
			tLLAccountSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
		}
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setSchema(tLCInsuerdSchema);
		tLCInsuredDB.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
		LCCustomerImpartSet impartSet = new LCCustomerImpartSet();

		TransferData transferData = new TransferData();
		transferData.setNameAndValue("FamilyType", "0");
		transferData.setNameAndValue("DiskImportFlag", "1");
		transferData.setNameAndValue("ContType", "2");
		transferData.setNameAndValue("PolTypeFlag", contSchema.getPolType());
		transferData.setNameAndValue("InsuredPeoples", String
				.valueOf(contSchema.getPeoples()));
		transferData.setNameAndValue("EdorType", mTransferData
				.getValueByName("EdorType"));
		transferData.setNameAndValue("SequenceNo",tLCInsuredDB.getPrtNo());//被保人公司内部号
		logger.debug("被保人公司内部序列号"+tLCInsuredDB.getPrtNo());
		tInputData.add(mGlobalInput);
		tInputData.add(contSchema);
		tInputData.add(tLCInsuerdSchema);
		tInputData.add(tLLAccountSchema);
		logger.debug("rrrrrrrrrrrrrrrrrrrrr:"
				+ tLCInsuerdSchema.getOccupationType());
		logger.debug("rrrrrrrrrrrrrrrrrrrrr:"
				+ tLCInsuerdSchema.getOccupationCode());
		tInputData.add(tLCInsuredDB);
		LCAddressSchema inLCAddressSchema = new LCAddressSchema();
		// 在这增加对地址编码的处理
		if (tLCInsuerdSchema.getWorkType() != null) {
			inLCAddressSchema.setMobile(tLCInsuerdSchema.getWorkType());
		}
		if (tLCInsuerdSchema.getRgtAddress() != null) {
			inLCAddressSchema.setEMail(tLCInsuerdSchema.getRgtAddress());
		}
		tInputData.add(inLCAddressSchema);
		tInputData.add(tPersonSchema);
		tInputData.add(impartSet);
		tInputData.add(transferData);

		// 提交生成或得到团单下个单合同
		ContInsuredBL contInsuredBl = new ContInsuredBL();
		boolean cRes = contInsuredBl.preparesubmitData(tInputData,
				"INSERT||CONTINSURED");
		if (!cRes) {
			// CError.buildErr(this, contInsuredBl.mErrors.getErrContent());
			this.mErrors.copyAllErrors(contInsuredBl.mErrors);
			return null;
		}
		tResult = contInsuredBl.getResult();

		map.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		LCAddressSchema tLCAddressSchema = (LCAddressSchema) tResult
				.getObjectByObjectName("LCAddressSchema", 0);
		if (tLCAddressSchema != null
				&& "" + tLCAddressSchema.getAddressNo() != "") {
			// tLCAddressSet.add( tLCAddressSchema );
			//map.put(tLCAddressSchema, "INSERT");
		}
		LCInsuredSchema rInsureSchema = (LCInsuredSchema) tResult
				.getObjectByObjectName("LCInsuredSchema", 0);
		LCContSchema rContSchema = (LCContSchema) tResult
				.getObjectByObjectName("LCContSchema", 0);
		LLAccountSchema rLLAccountSchema = (LLAccountSchema) tResult
				.getObjectByObjectName("LLAccountSchema", 0);
		if (tLCAddressSchema != null) {
			// rInsureSchema.setAddressNo(tLCAddressSchema.getAddressNo());

		}

		// 如果被保险人页签里面的信息不为空，则以填写的为准.
		// if (!StrTool.cTrim(cacheInsuredSchema.getOccupationType()).equals(""))
		// rInsureSchema.setOccupationType(cacheInsuredSchema.
		// getOccupationType());
		// if (!StrTool.cTrim(cacheInsuredSchema.getOccupationCode()).equals(""))
		// rInsureSchema.setOccupationCode(cacheInsuredSchema.
		// getOccupationCode());
		// if (!StrTool.cTrim(cacheInsuredSchema.getPluralityType()).equals(""))
		// rInsureSchema.setPluralityType(cacheInsuredSchema.
		// getPluralityType());

		tLCInsuerdSchema.setInsuredStat("0");
		// 缓存创建了的信息
		if (createCont)
			cacheLCContSchema(contId, rContSchema);
		if (createIns) {
			// rContSchema.setPeoples(rContSchema.getPeoples() + 1);
			cacheLCInsuredSchema(insuredId, rInsureSchema);
		}

		tResult.add(rContSchema);
		tResult.add(rLLAccountSchema);
		tResult.add(rInsureSchema);
		if (map != null)
			tResult.add(map);
		return tResult;
	}

	/**
	 * 受益人信息：查找被保险人，当受益人跟被保险人为同一人，则利用被保险人的信息
	 * 
	 * 如果不是同一人，查找被保险人是否已经创建，若没需要创建 ， 创建受益人信息
	 * 
	 * @param tLCBnfSet
	 *            LCBnfSet
	 * @param insuredSchema
	 *            LCInsuredSchema
	 * @param tLCGrpPolSchema
	 *            LCGrpPolSchema
	 * @param insureIndex
	 *            String
	 * @param strID
	 *            String
	 * @return VData
	 */
	public VData perareBnf(LCBnfSet tLCBnfSet, LCInsuredSchema insuredSchema,
			LCGrpPolSchema tLCGrpPolSchema, String insureIndex) {
		MMap tmpMap = null;
		// 校验处理受益人的被保险人信息
		LCBnfSchema tBnfSchema = null;
		for (int t = 1; t <= tLCBnfSet.size(); t++) {
			tBnfSchema = tLCBnfSet.get(t);
			// 主被保险人的合同号一致
			tBnfSchema.setContNo(insuredSchema.getContNo());
			// if ("".equals(StrTool.cTrim(tBnfSchema.getInsuredNo()))
			// || tBnfSchema.getInsuredNo().equals(insureIndex))
			// {

			if ("1".equals(StrTool.cTrim(tBnfSchema.getBnfType()))
					&& "00".equals(StrTool.cTrim(tBnfSchema
							.getRelationToInsured()))) {
				CError.buildErr(this, "合同[" + tBnfSchema.getContNo()
						+ "]有受益人类型为'死亡受益人'，不能指定为被保人本人");
				return null;
			}
			// 受益人为被保险人本人
			else if ("00".equals(StrTool.cTrim(tBnfSchema
					.getRelationToInsured()))) {
				tBnfSchema.setInsuredNo(insuredSchema.getInsuredNo());
				tBnfSchema.setCustomerNo(insuredSchema.getInsuredNo());
				tBnfSchema.setBirthday(insuredSchema.getBirthday());
				tBnfSchema.setName(insuredSchema.getName());
				tBnfSchema.setSex(insuredSchema.getSex());
				tBnfSchema.setIDType(insuredSchema.getIDType());
				if (insuredSchema.getIDType().equals("0")) {
					tBnfSchema.setIDNo(insuredSchema.getIDNo().toUpperCase());
				} else {
					tBnfSchema.setIDNo(insuredSchema.getIDNo());
				}
			} else {
				// 查询被保险人
				LCInsuredSchema tInsuredSchema = findInsuredfromCache(tBnfSchema
						.getInsuredNo());
				if (tInsuredSchema == null) {
					CError.buildErr(this, "受益人信息没有查找到被保险人["
							+ tBnfSchema.getInsuredNo() + "]信息");
					return null;
				}
				tBnfSchema.setInsuredNo(tInsuredSchema.getInsuredNo());
				// 根据被保险人，判断是否需要新创建被保险人合同和表
				// tBnfSchema.setContNo(tInsuredSchema.getContNo());
				// 察看系统中看看不需要创建新的客户
				LDPersonSchema person = checkInSystem_Bnf(tBnfSchema);
				if (person == null) {
					CError.buildErr(this, "受益人信息创建失败");

					return null;
				}
				tBnfSchema.setCustomerNo(person.getCustomerNo());
			}
			if ("".equals(tBnfSchema.getBnfGrade())) {
				tBnfSchema.setBnfGrade("1");
			}

			tBnfSchema.setOperator(mGlobalInput.Operator);
			tBnfSchema.setMakeDate(PubFun.getCurrentDate());
			tBnfSchema.setMakeTime(PubFun.getCurrentTime());
			tBnfSchema.setModifyDate(tBnfSchema.getMakeDate());
			tBnfSchema.setModifyTime(tBnfSchema.getMakeTime());
		}

		VData tData = new VData();
		if (tmpMap != null && tmpMap.keySet().size() > 0)
			tData.add(tmpMap);
		tData.add(tLCBnfSet);
		return tData;
	}

	/**
	 * 查询Person是否已经存在于系统,不存在则创建LDPerson
	 * 
	 * @param schema
	 *            LDPersonSchema
	 * @return LDPersonSchema
	 */
	private LDPersonSchema checkInSystemPerson(LDPersonSchema schema) {
		LDPersonDB tdb = new LDPersonDB();
		LDPersonSet iSet = null;
		if (!"".equals(StrTool.cTrim(schema.getCustomerNo()))) {
			tdb = new LDPersonDB();
			tdb.setCustomerNo(schema.getCustomerNo());
			iSet = tdb.query();
		}
		if (iSet != null && iSet.size() >= 0)
			return iSet.get(iSet.size());

		boolean err = false;
		if ("".equals(StrTool.cTrim(schema.getName()))) {
			CError.buildErr(this, "客户姓名不能为空！");
			err = true;
		}
		if (!"".equals(StrTool.cTrim(schema.getSex()))
				&& !"".equals(StrTool.cTrim(schema.getBirthday()))
				&& !"".equals(StrTool.cTrim(schema.getIDType()))
				&& !"".equals(StrTool.cTrim(schema.getIDNo()))) {
			/*
			 * if ("".equals(StrTool.cTrim(schema.getSex()))) {
			 * CError.buildErr(this, "客户性别不能为空！"); err = true;
			 *  } if ("".equals(StrTool.cTrim(schema.getBirthday()))) {
			 * CError.buildErr(this, "客户生日不能为空！"); err = true; } if
			 * ("".equals(StrTool.cTrim(schema.getBirthday()))) {
			 * CError.buildErr(this, "客户证件类型不能为空！"); err = true; }
			 * 
			 * if ("".equals(StrTool.cTrim(schema.getBirthday()))) {
			 * CError.buildErr(this, "客户证件号码不能为空！"); err = true; }
			 */
			if (schema.getIDType().equals("0")) {
				tdb.setIDNo(schema.getIDNo().toUpperCase());
			} else {
				tdb.setIDNo(schema.getIDNo());
			}
			tdb.setIDType(schema.getIDType());
			tdb.setBirthday(schema.getBirthday());
			tdb.setName(schema.getName());
			tdb.setSex(schema.getSex());

			iSet = tdb.query();
			// 查询到，返回
			if (!tdb.mErrors.needDealError() && iSet != null && iSet.size() > 0) {
				return iSet.get(iSet.size());
			}
		}

		return schema;
	}

	/**
	 * 查询受益人是否已经存在于系统,不存在则创建LDPerson 在系统中，则contNo不为空，否则contNo为空
	 * 
	 * @param schema
	 *            LCInsuredSchema
	 * @return LCInsuredSchema
	 */

	private LDPersonSchema checkInSystem_Bnf(LCBnfSchema schema) {
		LDPersonDB tdb = new LDPersonDB();
		LDPersonSet iSet = null;
		if (!"".equals(StrTool.cTrim(schema.getCustomerNo()))) {
			tdb = new LDPersonDB();
			tdb.setCustomerNo(schema.getCustomerNo());
			iSet = tdb.query();
		}
		if (iSet != null && iSet.size() >= 0)
			return iSet.get(iSet.size());

		boolean err = false;
		if ("".equals(StrTool.cTrim(schema.getName()))) {
			CError.buildErr(this, "客户姓名不能为空！");
			err = true;
		}
		// if ("".equals(StrTool.cTrim(schema.getSex())))
		// {
		// CError.buildErr(this, "客户性别不能为空！");
		// err = true;
		//
		// }
		// if ("".equals(StrTool.cTrim(schema.getBirthday())))
		// {
		// CError.buildErr(this, "客户生日不能为空！");
		// err = true;
		// }
		if (!"".equals(StrTool.cTrim(schema.getSex()))
				&& !"".equals(StrTool.cTrim(schema.getBirthday()))
				&& !"".equals(StrTool.cTrim(schema.getIDType()))
				&& !"".equals(StrTool.cTrim(schema.getIDNo()))) {

			if (schema.getIDType().equals("0")) {
				tdb.setIDNo(schema.getIDNo().toUpperCase());
			} else {
				tdb.setIDNo(schema.getIDNo());
			}
			tdb.setIDType(schema.getIDType());
			tdb.setBirthday(schema.getBirthday());
			tdb.setName(schema.getName());
			tdb.setSex(schema.getSex());

			iSet = tdb.query();

			// 查询到，返回
			if (!tdb.mErrors.needDealError() && iSet != null && iSet.size() > 0) {
				return iSet.get(iSet.size());
			}
		}
		// 需要创建客户

		String nolimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String customerno = PubFun1.CreateMaxNo("CustomerNo", nolimit);
		LDPersonSchema tPerson = new LDPersonSchema();
		tPerson.setCustomerNo(customerno);
		tPerson.setBirthday(schema.getBirthday());
		if (schema.getIDType().equals("0")) {
			tPerson.setIDNo(schema.getIDNo().toUpperCase());
		} else {
			tPerson.setIDNo(schema.getIDNo());
		}
		tPerson.setIDType(schema.getIDType());
		tPerson.setOperator(mGlobalInput.Operator);
		tPerson.setMakeDate(PubFun.getCurrentDate());
		tPerson.setMakeTime(PubFun.getCurrentTime());
		tPerson.setModifyDate(tPerson.getMakeDate());
		tPerson.setModifyTime(tPerson.getMakeTime());
		tPerson.setName(schema.getName());
		tPerson.setSex(schema.getSex());

		tdb.setSchema(tPerson);
		// if (!tdb.insert())
		// {
		// CError.buildErr(this, "创建客户时出错:", tdb.mErrors);
		// return null;
		// }
		return tdb.getSchema();

	}

	/**
	 * 查询或创建被保人信息，但是与合同无关；创建完毕以后缓存被保险人信息
	 * 
	 * @param insuredId
	 *            String
	 * @return VData
	 */
	private VData getInsuredData(String insuredId) {

		VData tData = new VData();
		LDPersonDB tdb = new LDPersonDB();
		Reflections ref = new Reflections();
		// int insuredIndex = this.parseId(insuredId);
		LCInsuredSchema tSchema = this.findInsured(insuredId);

		LCInsuredSchema schema = new LCInsuredSchema();
		// 拷贝一份缓存
		schema.setSchema(tSchema);
		schema.setContNo("");
		if (schema == null) {
			CError.buildErr(this, "创建被保险人时未从导入数据中查找到被保险人[" + insuredId + "]");
			return null;
		}
		// 需要创建客户
		boolean err = false;
		if ("".equals(StrTool.cTrim(schema.getName()))) {
			CError.buildErr(this, "客户姓名不能为空！");
			err = true;
		}
		if ("".equals(StrTool.cTrim(schema.getSex()))) {
			CError.buildErr(this, "客户性别不能为空！");
			err = true;

		}
		if ("".equals(StrTool.cTrim(schema.getBirthday()))) {
			CError.buildErr(this, "客户生日不能为空！");
			err = true;
		}
		LDPersonSet iSet = null;
		if (!"".equals(StrTool.cTrim(schema.getInsuredNo()))) {

			tdb.setCustomerNo(schema.getInsuredNo());
			iSet = tdb.query();
		}
		if (iSet == null) {
			// }
			// else if ("0".equals(StrTool.cTrim(schema.getIDType())) &&
			// schema.getIDNo() != null) {
			// 用身份证id查询
			if (schema.getIDType().equals("0")) {
				tdb.setIDNo(schema.getIDNo().toUpperCase());
			} else {
				tdb.setIDNo(schema.getIDNo());
			}
			tdb.setIDType(schema.getIDType());
			tdb.setName(schema.getName());
			tdb.setSex(schema.getSex());
			tdb.setBirthday(schema.getBirthday());
			iSet = tdb.query();
		}

		if (!tdb.mErrors.needDealError() && iSet != null && iSet.size() > 0) {

			LDPersonSchema tPersonSchema = iSet.get(1);
			ref.transFields(schema, tPersonSchema);
			schema.setInsuredNo(tPersonSchema.getCustomerNo());
		} else {

			String nolimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String customerno = PubFun1.CreateMaxNo("CustomerNo", nolimit);
			LDPersonSchema tPerson = new LDPersonSchema();
			tPerson.setCustomerNo(customerno);
			tPerson.setBirthday(schema.getBirthday());
			if (schema.getIDType().equals("0")) {
				tPerson.setIDNo(schema.getIDNo().toUpperCase());
			} else {
				tPerson.setIDNo(schema.getIDNo());
			}
			tPerson.setIDType(schema.getIDType());
			tPerson.setOperator(mGlobalInput.Operator);
			tPerson.setMakeDate(PubFun.getCurrentDate());
			tPerson.setMakeTime(PubFun.getCurrentTime());
			tPerson.setModifyDate(tPerson.getMakeDate());
			tPerson.setModifyTime(tPerson.getMakeTime());
			tPerson.setName(schema.getName());
			tPerson.setSex(schema.getSex());

			tdb.setSchema(tPerson);
			if (!tdb.insert()) {
				CError.buildErr(this, "创建客户时出错:", tdb.mErrors);
				return null;
			}

			ref.transFields(schema, tPerson);
			schema.setInsuredNo(tPerson.getCustomerNo());
		}

		this.cacheLCInsuredSchema(insuredId, schema);
		tData.add(schema);
		return tData;
	}

	/**
	 * 类型转换
	 * 
	 * @param id
	 *            String
	 * @return int
	 */
	private int parseId(String id) {
		int newId = 0;
		id = StrTool.cTrim(id);
		try {
			newId = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
		return newId;
	}

	/**
	 * 获取险种保单数据
	 * 
	 * @param contId
	 *            String
	 * @return Object
	 */
	public Object getContPolData(String contId) {
		return this.mContPolData.get(contId);
	}

	public void intiContPolData() {
		this.mContPolData = new HashMap();

	}

	/**
	 * 按合同号缓存险种保单数据
	 * 
	 * @param contId
	 *            String
	 * @param data
	 *            Object
	 */
	public void addContPolData(String contId, Object data) {

		if (mContPolData.containsKey(contId)) {
			VData tVData = (VData) mContPolData.get(contId);
			tVData.add(data);
		} else {
			VData contpolData = new VData();
			contpolData.add(data);
			mContPolData.put(contId, contpolData);
		}
	}

	/**
	 * 解析获取合同id ,
	 * 
	 * @param contpolIns，
	 *            String like contid-polid-insuredid
	 * @return String ，contid
	 */
	public String getContKey(String contpolIns) {
		String rs = contpolIns;
		if (contpolIns.indexOf("-") > 0) {
			Object[] obj = contpolIns.split("-");
			rs = (String) obj[0];
		}
		return rs;
	}

}
