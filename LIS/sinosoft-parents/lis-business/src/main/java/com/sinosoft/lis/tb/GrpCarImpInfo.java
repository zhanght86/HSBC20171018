package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpImportLogDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCGrpImportLogSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.lis.vschema.LDGrpSet;
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
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpCarImpInfo {
private static Logger logger = Logger.getLogger(GrpCarImpInfo.class);
	public CErrors mErrors = new CErrors();
	private GlobalInput mGlobalInput = null;

	// 2005-5-10==增加==方便特殊数据传输
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
	private LCPolOtherSet mLCPolOtherSet = new LCPolOtherSet();

	// 缓存的一些信息
	// 对于有主附险的情况，将主险记录的相关信息缓存，因为在处理附加险的时候
	// 需要主险的保单号等相关的信息
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet(); // 保存原始信息

	// 保存经过创建或系统查询出来后的被保险人信息
	private HashMap mInsuredMap = new HashMap();
	private HashMap mLCPolOtherMap = new HashMap();
	private HashMap mMainPolMap = new HashMap();

	// 保存创建或系统中查询出来的合同信息
	private HashMap mContCache = new HashMap();
	private HashMap mContPolData = new HashMap(); // 保存以合同为索引的保单信息

	public GrpCarImpInfo() {
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
	 * @param TransferData
	 *            TransferData
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
			LCGrpContSchema tLCGrpContSchema, LCPolOtherSet tLCPolOtherSet) {
		mGlobalInput = GlobalInput;
		mTransferData = TransferData;

		// 清空
		mContCache.clear();

		mInsuredMap.clear();
		mLCPolOtherMap.clear();
		mMainPolMap.clear();
		// mContPolData.clear();

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
			logger.debug("GrpPolImpInfo,init,birthday"
					+ tLCInsuredSet.get(i).getBirthday());
			if ("".equals(contId)) {
				buildError("init", "被保人信息中有没有填写的合同号!");
				return false;
			}
			if (!mContCache.containsKey(contId)) {
				mContCache.put(contId, null);
			}
		}

		mLCPolOtherSet = tLCPolOtherSet;

		mLCGrpContSchema = tLCGrpContSchema;
		String grpcontNo = mLCGrpContSchema.getGrpContNo();

		if (m_LDGrpSet == null || m_LDGrpSet.size() == 0) {
			if (initLDGrp(tLCGrpContSchema.getAppntNo()) == false) {
				return false;
			}
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

	public LCPolOtherSet getLCPolOtherSet() {
		return this.mLCPolOtherSet;
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
		if (tPrem < 0) {
			tPrem = 0;
		}
		double tAmnt = grpPol.getAmnt() - amnt;
		if (tAmnt < 0) {
			tAmnt = 0;
		}

		grpPol.setPrem(tPrem);
		grpPol.setAmnt(tAmnt);
		this.mLCGrpContSchema.setPrem(mLCGrpContSchema.getPrem() - tPrem);
		this.mLCGrpContSchema.setAmnt(mLCGrpContSchema.getAmnt() - tAmnt);
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
		logger.debug("strRiskCode=" + strRiskCode);
		LCInsuredSchema tLCInsuredSchema = null;

		LCGrpPolSchema tLCGrpPolSchema = findLCGrpPolSchema(strRiskCode);
		if (tLCGrpPolSchema == null) {
			buildError("formatLCPol", "找不到指定险种所对应的集体保单，险种为：" + strRiskCode);
			return null;
		}

		// 校验险种是否存在，能不能挂在其指定主险中
		tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
		tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		tLCPolSchema.setPayIntv(tLCGrpPolSchema.getPayIntv());
		tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
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

		String mainRiskCode = StrTool.cTrim(tLCPolSchema.getMainPolNo());
		// ////////////////////////关键的地方用来处理附加险可能会用到的主险信息///////////////
		logger.debug("mainRiskCode=" + mainRiskCode);
		logger.debug("strID=" + strID);
		if (!"".equals(mainRiskCode)
				&& !strRiskCode.trim().equals(mainRiskCode.trim())) {
			// 如果不是主险保单，则查找
			logger.debug("不是主险单,查找");
			String tContId = strID.substring(0, strID.indexOf('-'));
			LCPolSchema tMainLCPolSchema = findCacheLCPolSchema(tContId + "-"
					+ mainRiskCode);
			// 如果从导入轨迹中没有找到
			if (tMainLCPolSchema == null) {
				buildError("formatLCPol", "数据库和导入轨迹中找不到附加险被保人(" + mainRiskCode
						+ ")对应的个人主险保单纪录");

				return null;
			}
			if (tLCPolSchema.getRiskCode().equals(
					tMainLCPolSchema.getRiskCode())) {
				buildError("formatLCPol", "附加险险种号码"
						+ tLCPolSchema.getRiskCode() + "和主险险种号码相同！");
				return null;
			}
			// mainRiskCode= tMainLCPolSchema.getRiskCode();
			tLCPolSchema.setMainPolNo(tMainLCPolSchema.getPolNo());
			logger.debug("主险单号=====" + tMainLCPolSchema.getPolNo()); // 已经存在了可以借助它给下一个附加险
			tLCPolSchema.setContNo(tMainLCPolSchema.getContNo().trim());
			// 被保险人也应该存在
			String insuredIndex = tLCPolSchema.getInsuredNo();
			if (mainRiskCode.indexOf("-") > 0) {
				String[] arr = mainRiskCode.split("-");

				insuredIndex = arr[1];
			}
			// 附加险的被保人即是主险的被保人
			tLCInsuredSchema = this.findInsuredfromCache(insuredIndex);
			if (tLCInsuredSchema == null) {
				mErrors.clearErrors();
				buildError("formatLCPol", "新增附加险:找不到对应主险的被保人信息,ID="
						+ insuredIndex);
				return null;
			}

		} else {

			// 本身为主险保单,设置主险保单号空，查找或创建被保险人
			tLCPolSchema.setMainPolNo("");
			logger.debug("是主险单");
			logger.debug("被保人客户号是: " + tLCPolSchema.getInsuredNo());
			tLCInsuredSchema = this.findInsuredfromCache(tLCPolSchema
					.getInsuredNo());
			logger.debug("after findInsuredfromCache");
			if (tLCInsuredSchema == null) {
				// 需要创建新被保险人
				VData tData = this.getContData(tLCPolSchema.getContNo(),
						tLCGrpPolSchema);
				logger.debug(this.mErrors.toString());
				if (tData == null) {
					return null;
				}

				MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
				logger.debug("after tData.getObjectByObjectName(MMap, 0)");
				if (map != null && map.keySet().size() > 0) {
					tReturnData.add(map);
				}
				tLCInsuredSchema = (LCInsuredSchema) tData
						.getObjectByObjectName("LCInsuredSchema", 0);
				logger.debug("getObjectByObjectName(LCInsuredSchema, 0)");
			}
		}

		tLCPolSchema.setContNo(tLCInsuredSchema.getContNo().trim());
		tLCPolSchema.setInsuredSex(tLCInsuredSchema.getSex());
		tLCPolSchema.setOccupationType(tLCInsuredSchema.getOccupationType()); //
		tLCPolSchema.setInsuredBirthday(tLCInsuredSchema.getBirthday());
		tLCPolSchema.setInsuredName(tLCInsuredSchema.getName());
		tLCPolSchema.setInsuredNo(tLCInsuredSchema.getInsuredNo());
		tLCPolSchema.setInsuredPeoples(tLCInsuredSchema.getInsuredPeoples());
		tReturnData.add(tLCPolSchema);
		return tReturnData;
	}

	public LCPolOtherSet formatLCPolOtherSet(LCPolSchema cLCPolSchema) {
		LCPolOtherSet tLCPolOtherSet = new LCPolOtherSet();
		LCPolSchema tLCPolSchema = cLCPolSchema.getSchema();
		for (int i = 0; i < mLCPolOtherSet.size(); i++) {
			LCPolOtherSchema tLCPolOtherSchema = mLCPolOtherSet.get(i + 1);
			tLCPolOtherSchema.setContNo(tLCPolSchema.getContNo());
			tLCPolOtherSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCPolOtherSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCPolOtherSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCPolOtherSchema.setDutyCode("000000");
			tLCPolOtherSet.add(tLCPolOtherSchema);
		}
		return tLCPolOtherSet;
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
		if (dest == null) {
			return null;
		}
		if (dest.getClass().getName().equals("java.lang.String")) {
			return null;
		}
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
			String riskCode, LCPolOtherSchema cLCPolOtherSchema,
			CErrors errInfo, GlobalInput globalInput, String fileName) {
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
		LCGrpImportLogSchema delLCGrpImportLogSchema = new LCGrpImportLogSchema();
		// 设置主键
		delLCGrpImportLogSchema.setID(contId);
		delLCGrpImportLogSchema.setBatchNo(batchNo);
		delLCGrpImportLogSchema.setOtherNoType("4");

		LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
		// 避免报错
		if (cLCPolOtherSchema == null) {
			cLCPolOtherSchema = new LCPolOtherSchema();
		}
		// 设置主键
		tLCGrpImportLogSchema.setIdNo(polId);
		tLCGrpImportLogSchema.setID(contId);
		tLCGrpImportLogSchema.setBatchNo(batchNo);
		// tLCGrpImportLogSchema.setRiskCode(riskCode);
		tLCGrpImportLogSchema.setGrpContNo(grpContNo);
		tLCGrpImportLogSchema.setOtherNo(grpContNo);
		tLCGrpImportLogSchema.setInsuredID(insuredId);
		tLCGrpImportLogSchema.setContPlanCode(contPlanCode);
		tLCGrpImportLogSchema.setOtherNoType("4");
		tLCGrpImportLogSchema.setInsuredNo(insuredId);
		tLCGrpImportLogSchema.setInsuredName(cLCPolOtherSchema.getP1());
		tLCGrpImportLogSchema.setOperator(globalInput.Operator);
		tLCGrpImportLogSchema.setStandbyFlag3(fileName.substring(0, fileName
				.lastIndexOf(".")));
		tLCGrpImportLogSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpImportLogSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGrpImportLogSchema.setErrorType("1");
		tLCGrpImportLogSchema.setErrorState("1");
		String errMess = "";
		for (int i = 0; i < errInfo.getErrorCount(); i++) {
			errMess += errInfo.getError(i).errorMessage + ";";
		}

		if ("".equals(errMess)) {
			errMess = "未捕捉到的错误。";
		}
		errMess.replaceAll("\n", "");
		tLCGrpImportLogSchema.setErrorInfo(errMess);

		tLCGrpImportLogDB.setSchema(delLCGrpImportLogSchema);
		boolean res = true;
		res = tLCGrpImportLogDB.delete();
		if (res) {
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
		delLCGrpImportLogSchema.setID(contId);
		delLCGrpImportLogSchema.setBatchNo(batchNo);
		delLCGrpImportLogSchema.setOtherNoType("4");
		tmpMap.put(delLCGrpImportLogSchema, "DELETE");

		LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
		tLCGrpImportLogSchema.setID(contId);
		tLCGrpImportLogSchema.setBatchNo(batchNo);
		tLCGrpImportLogSchema.setGrpContNo(grpContNo);
		tLCGrpImportLogSchema.setOtherNo(grpContNo);
		tLCGrpImportLogSchema.setContNo(contNo);
		tLCGrpImportLogSchema.setOperator(globalInput.Operator);
		tLCGrpImportLogSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpImportLogSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGrpImportLogSchema.setOtherNoType("4");
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
		tLCGrpImportLogDB.setID(contId);
		tLCGrpImportLogDB.setOtherNoType("4");
		boolean bExist = tLCGrpImportLogDB.getInfo();
		if (bExist) {
			return tLCGrpImportLogDB.getSchema();
		}
		return null;
	}

	/**
	 * 获取错误信息
	 * 
	 * @return LCGrpImportLogSet
	 */
	public LCGrpImportLogSet getErrors() {
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();

		String strSQL = "SELECT * FROM LCGrpImportLog" + " WHERE BatchNo = '"
				+ m_strBatchNo + "'"
				+ " AND ( ErrorState = '1' OR ErrorInfo IS NOT NULL )"
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

			if (initLMRisk(strRiskCode) == false) {
				return false;
			}
		}

		return true;
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
	public VData getContData(String contId, LCGrpPolSchema tLCGrpPolSchema) {
		logger.debug("getContData start.......");
		VData tInputData = new VData();
		VData tResult = new VData();
		LCContSchema contSchema = null;
		LCInsuredSchema tLCInsuerdSchema = null;
		if (contId != null) {
			contSchema = findLCContfromCache(contId);
			tLCInsuerdSchema = this.findInsuredfromCache(contId);
		}

		if (tLCInsuerdSchema != null && contSchema != null
				&& !"".equals(StrTool.cTrim(contSchema.getContNo()))) { // 已经创建，直接返回
			tResult.add(contSchema);
			tResult.add(tLCInsuerdSchema);
			return tResult;
		}

		Reflections ref = new Reflections();
		boolean createCont = false;
		// LDPersonSchema tPersonSchema;
		if (contSchema == null) {
			createCont = true;
			// 如果被保险人没有被创建过，则从原始解析的xml中获取
			// 标识要创建被保险人
			tLCInsuerdSchema = this.findInsured(contId);
			if (tLCInsuerdSchema == null) {
				CError.buildErr(this, "导入文件中没有被保险人[" + contId + "]");
				return null;
			}

		}
		LDPersonSchema tPersonSchema = new LDPersonSchema();
		ref.transFields(tPersonSchema, tLCInsuerdSchema);
		tPersonSchema.setState(tLCInsuerdSchema.getInsuredStat());
		tPersonSchema.setCustomerNo(tLCInsuerdSchema.getInsuredNo());

		MMap map = new MMap();
		if (createCont) {
			contSchema = new LCContSchema();
			contSchema.setInsuredNo("");
			contSchema.setInsuredSex(tPersonSchema.getSex());
			contSchema.setInsuredName(tPersonSchema.getName());
			contSchema.setInsuredBirthday(tPersonSchema.getBirthday());
			contSchema.setBankAccNo(tLCInsuerdSchema.getBankAccNo());
			contSchema.setBankCode(tLCInsuerdSchema.getBankCode());
			contSchema.setAccName(tLCInsuerdSchema.getAccName());
			contSchema.setPeoples(tLCInsuerdSchema.getInsuredPeoples());
			// 借用被保人信用等级字段保存 保单类型标记
			contSchema.setPolType("1");
			contSchema.setPeoples(tLCInsuerdSchema.getInsuredPeoples());
			contSchema.setContType("2");

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
		tInputData.add(mGlobalInput);
		tInputData.add(contSchema);
		tInputData.add(tLCInsuerdSchema);
		tInputData.add(tLCInsuredDB);
		LCAddressSchema inLCAddressSchema = new LCAddressSchema();
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
			map.put(tLCAddressSchema, "INSERT");
		}
		LCInsuredSchema rInsureSchema = (LCInsuredSchema) tResult
				.getObjectByObjectName("LCInsuredSchema", 0);
		LCContSchema rContSchema = (LCContSchema) tResult
				.getObjectByObjectName("LCContSchema", 0);
		if (tLCAddressSchema != null) {
			// rInsureSchema.setAddressNo(tLCAddressSchema.getAddressNo());
		}

		tLCInsuerdSchema.setInsuredStat("");
		// 缓存创建了的信息
		if (createCont) {
			cacheLCContSchema(contId, rContSchema);
			cacheLCInsuredSchema(contId, rInsureSchema);
		}

		tResult.add(rContSchema);
		tResult.add(rInsureSchema);
		if (map != null) {
			tResult.add(map);
		}
		return tResult;
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

	public static void main(String[] args) {
		GrpCarImpInfo grpcarimpinfo = new GrpCarImpInfo();
	}
}
