package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.HashMap;

import com.sinosoft.lis.bl.LCInsuredBL;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpCardImportProposal {
private static Logger logger = Logger.getLogger(GrpCardImportProposal.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	//
	private MMap mapContBL = new MMap();
	private MMap mapInsuredBL = new MMap();
	private MMap mapLcpolBL = new MMap();
	private MMap totalMap = new MMap();

	private MMap map = new MMap();
	private String contno;
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	private Reflections ref = new Reflections();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData rInputData; // 被保险人
	private VData aInputData; // 合同信息与投保人
	private VData dInputData; // 要删除的数据
	/** 数据操作字符串 */
	private String mOperate;
	private String mSamePersonFlag; // 所选投保人和被保人是否是同一人 add by zhangxing
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	// 保存经过创建或系统查询出来后的被保险人信息
	private HashMap mInsuredMap = new HashMap();
	private HashMap mMainPolMap = new HashMap();

	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 其他相关表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema(); // 投保人信息
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); // 被保险人
	private LCPolSchema mLCPolSchema = new LCPolSchema(); // 险种信息
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema(); // 投保人
	private LCAddressSchema rLCAddressSchema = new LCAddressSchema(); // 被保险人
	private LCAccountSchema mLCAccountSchema = new LCAccountSchema(); // 投保人信息
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet(); // 投保人信息
	private LACommisionDetailSet mLACommisionDetailSet = new LACommisionDetailSet(); // 投保人信息
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema(); // 客户表
	private LCInsuredDB mOLDLCInsuredDB = new LCInsuredDB(); // 被保险人
	private LCCustomerImpartDetailSet mLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet(); // 被保险人
	private LCBnfSet mLCBnfSet = new LCBnfSet(); // 受益人信息
	private LCDutySchema mLCDutySchema = new LCDutySchema();// 险种信息
	private boolean ISPlanRisk = false;

	public GrpCardImportProposal() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(mInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		this.prepareOutputData();

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void prepareInputData() {
		logger.debug("操作方式===== in cardproposalbl=" + mOperate);
		mInputData.clear();

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private void prepareOutputData() {
		logger.debug("操作方式===== in cardproposalbl=" + mOperate);
		mInputData.clear();
		totalMap.add(mapContBL);
		totalMap.add(mapInsuredBL);
		totalMap.add(mapLcpolBL);
		for (int i = 0; i <= totalMap.getOrder().size(); i++) {
			logger.debug(totalMap.getKeyByOrder(String.valueOf(i)));
		}

		mInputData.add(totalMap);
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// 处理保单信息及投保人信息
		ContBL mContBL = new ContBL();
		String tOperate = "INSERT||CONT";
		if (!mContBL.submitData(mInputData, tOperate)) {
			this.mErrors.copyAllErrors(mContBL.mErrors);
			return false;
		} else {
			logger.debug("合同信息保存成功！");
			// mapContBL.add((MMap)
			// mContBL.getCardResult().getObjectByObjectName("MMap",0));
			aInputData = mContBL.getResult();
			mapContBL.put(aInputData.getObjectByObjectName("LCContSchema", 0),
					"INSERT");
			mapContBL.put(aInputData.getObjectByObjectName("LCAppntSchema", 0),
					"INSERT");
			mapContBL.put(aInputData
					.getObjectByObjectName("LCAddressSchema", 0),
					"DELETE&INSERT");
			mapContBL.put(aInputData.getObjectByObjectName("LDPersonSet", 0),
					"DELETE&INSERT");
			mapContBL.put(aInputData.getObjectByObjectName(
					"LACommisionDetailSet", 0), "DELETE&INSERT");
		}

		LCContSchema kLCContSchema = new LCContSchema();
		kLCContSchema.setSchema((LCContSchema) aInputData
				.getObjectByObjectName("LCContSchema", 0));
		kLCContSchema.setGrpContNo("00000000000000000000");
		kLCContSchema.setUWFlag("9");
		kLCContSchema.setUWDate(PubFun.getCurrentDate());
		kLCContSchema.setUWOperator(mGlobalInput.Operator);
		kLCContSchema.setUWTime(PubFun.getCurrentTime());
		kLCContSchema.setApproveCode(mGlobalInput.Operator);
		kLCContSchema.setApproveFlag("9");
		kLCContSchema.setApproveDate(PubFun.getCurrentDate());
		kLCContSchema.setApproveTime(PubFun.getCurrentTime());

		// 取出投保人信息，用于判断后面判断是否为同一人
		LCAppntSchema kLCAppntSchema = new LCAppntSchema();
		kLCAppntSchema = (LCAppntSchema) aInputData.getObjectByObjectName(
				"LCAppntSchema", 0);
		String tAppntNo = kLCAppntSchema.getAppntNo();
		// 如果投保人和被保人是同一人，应该把投保人的客户号码传给被保险人
		logger.debug("mSamePersonFlag:" + mSamePersonFlag);

		/** @todo 将VData中多余的LCContSchema... */
		mInputData.remove(0);
		mInputData.add(kLCContSchema);

		// 主被保人
		LCInsuredSchema MainInsuSchema = new LCInsuredSchema();

		// 以下处理被保人信息,如果多被保人则循环处理
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredSet tempLCInsuredSet = new LCInsuredSet();
		tLCInsuredSet.set((LCInsuredSet) mInputData.getObjectByObjectName(
				"LCInsuredSet", 0));

		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			// 被保人
			LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
			tmLCInsuredSchema = (LCInsuredSchema) mInputData
					.getObjectByObjectName("LCInsuredSchema", 0);
			tmLCInsuredSchema.decode("");
			tmLCInsuredSchema.setSchema(tLCInsuredSet.get(i));
			// 客户表
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLDPersonSchema = (LDPersonSchema) mInputData
					.getObjectByObjectName("LDPersonSchema", 0);
			tLDPersonSchema.decode("");
			ref.transFields(tLDPersonSchema, tmLCInsuredSchema); // 客户表
			// 判断投保人被保人是否为同一人
			mSamePersonFlag = getSamePersonflag(kLCAppntSchema,
					tmLCInsuredSchema);
			if (mSamePersonFlag != null && mSamePersonFlag.equals("1")) {
				tmLCInsuredSchema.setInsuredNo(tAppntNo);
				tLDPersonSchema.setCustomerNo(tAppntNo);
			}
			// 地址表已经存在
			mTransferData.removeByName("SequenceNo");
			mTransferData.setNameAndValue("SequenceNo", tmLCInsuredSchema
					.getSequenceNo()); // 内部客户号<>
			mTransferData.removeByName("mSamePersonFlag");
			mTransferData.setNameAndValue("mSamePersonFlag", mSamePersonFlag); // 内部客户号<>

			String roperator = "INSERT||CONTINSURED";
			ContInsuredBL tContInsuredBL = new ContInsuredBL();
			tContInsuredBL.submitData(mInputData, roperator);
			if (tContInsuredBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tContInsuredBL.mErrors);
				return false;
			} else {
				logger.debug("被保险人信息保存成功！");
				// mapInsuredBL.add((MMap)
				// tContInsuredBL.getCardResult().getObjectByObjectName("MMap",
				// 0));
				rInputData = tContInsuredBL.getResult();
				mapInsuredBL.put(rInputData.getObjectByObjectName(
						"LCContSchema", 0), "DELETE&INSERT");
				mapInsuredBL.put(rInputData.getObjectByObjectName(
						"LCInsuredSchema", 0), "DELETE&INSERT");
				mSamePersonFlag = getSamePersonflag(kLCAppntSchema,
						tmLCInsuredSchema);
				// 如果投保人与被保人为不同一人，则存储被保人信息，因为如果为同一人时，投保人处已经存储
				if (mSamePersonFlag != null && !mSamePersonFlag.equals("1")) {
					mapInsuredBL.put(rInputData.getObjectByObjectName(
							"LCAddressSchema", 0), "DELETE&INSERT");
					mapInsuredBL.put(rInputData.getObjectByObjectName(
							"LDPersonSchema", 0), "DELETE&INSERT");
				}
			}
			LCInsuredBL mLCInsuredBL = new LCInsuredBL();
			mLCInsuredBL.setSchema((LCInsuredSchema) rInputData
					.getObjectByObjectName("LCInsuredSchema", 0));
			// 把主被保人放到数据包里,准备进行计算
			if (mLCInsuredBL.getSequenceNo().equals("1")
					&& mLCInsuredBL.getRelationToMainInsured().equals("00")) {
				mInputData.add(mLCInsuredBL);
				MainInsuSchema.setSchema(mLCInsuredBL.getSchema());
			}
			tempLCInsuredSet.add(mLCInsuredBL.getSchema());
		}

		// 将主被保人信息写入lccont表
		kLCContSchema.setInsuredNo(MainInsuSchema.getInsuredNo());
		kLCContSchema.setInsuredSex(MainInsuSchema.getSex());
		kLCContSchema.setInsuredBirthday(MainInsuSchema.getBirthday());
		kLCContSchema.setInsuredIDType(MainInsuSchema.getIDType());
		kLCContSchema.setInsuredIDNo(MainInsuSchema.getIDNo());
		kLCContSchema.setInsuredName(MainInsuSchema.getName());
		mapInsuredBL.put(kLCContSchema, "DELETE&INSERT");

		// 采用 TransferData 传入连带被保人信息，用于投保规则校验
		mTransferData.setNameAndValue("SumInsu", tempLCInsuredSet.size());// 被保人总数

		// 得到连带与主被保人关系，以及连带被保人年龄
		String InsNo = "";
		String InsRela = "";
		int InsAge = 0;
		// 获得连带被保人
		LCInsuredRelatedSet tmLCInsuredRelatedSet = new LCInsuredRelatedSet();
		tmLCInsuredRelatedSet = (LCInsuredRelatedSet) mInputData
				.getObjectByObjectName("LCInsuredRelatedSet", 0);
		for (int i = 1; i <= tmLCInsuredRelatedSet.size(); i++) {
			String sMCuNo = tmLCInsuredRelatedSet.get(i).getMainCustomerNo();
			String sCuNo = tmLCInsuredRelatedSet.get(i).getCustomerNo();
			// 得到主被保人号码
			tmLCInsuredRelatedSet.get(i).setMainCustomerNo(
					getInsuSchema(tempLCInsuredSet, sMCuNo).getInsuredNo());
			// 得到连带被保人号码
			tmLCInsuredRelatedSet.get(i).setCustomerNo(
					getInsuSchema(tempLCInsuredSet, sCuNo).getInsuredNo());
			// 得到连带被保人与主被保人关系
			tmLCInsuredRelatedSet.get(i).setRelationToInsured(
					getInsuSchema(tempLCInsuredSet, sCuNo)
							.getRelationToMainInsured());
			// 得到连带与主被保人关系，以及连带被保人年龄
			InsNo = tmLCInsuredRelatedSet.get(i).getCustomerNo();
			InsAge = PubFun.calInterval(tmLCInsuredRelatedSet.get(i)
					.getBirthday(), kLCContSchema.getPolApplyDate(), "Y");
			InsRela = tmLCInsuredRelatedSet.get(i).getRelationToInsured();

			mTransferData.removeByName("InsNo" + String.valueOf(i));
			mTransferData.setNameAndValue("InsNo" + String.valueOf(i), InsNo);
			mTransferData.removeByName("InsRela" + String.valueOf(i));
			mTransferData.setNameAndValue("InsRela" + String.valueOf(i),
					InsRela);
			mTransferData.removeByName("InsAge" + String.valueOf(i));
			mTransferData.setNameAndValue("InsAge" + String.valueOf(i), InsAge);
		}

		// 获得受益人
		LCBnfSet tmLCBnfSet = new LCBnfSet();
		tmLCBnfSet = (LCBnfSet) mInputData.getObjectByObjectName("LCBnfSet", 0);
		for (int i = 1; i <= tmLCBnfSet.size(); i++) {
			String sInsuNo = tmLCBnfSet.get(i).getInsuredNo();
			tmLCBnfSet.get(i).setInsuredNo(
					getInsuSchema(tempLCInsuredSet, sInsuNo).getInsuredNo());
		}

		// 以下处理险种,非产品组合
		if (!ISPlanRisk) {
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet.set((LCPolSet) mInputData.getObjectByObjectName(
					"LCPolSet", 0));
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				String poperator = "INSERT||PROPOSAL";
				ProposalBL tProposalBL = new ProposalBL();
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
						"LCPolSchema", 0);
				tLCPolSchema.setSchema(tLCPolSet.get(i));
				// 因为导入解析时将主险号码（mainriskcode）用mainpolno字段保存，所以此处将解析出mainpolno替换
				tLCPolSchema.setMainPolNo((String) mMainPolMap.get(tLCPolSchema
						.getMainPolNo()));
				tProposalBL.submitData(mInputData, poperator);
				if (tProposalBL.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tProposalBL.mErrors);
					return false;
				} else {
					logger.debug("险种信息保存成功！");
					mapLcpolBL.add((MMap) tProposalBL.getCardResult()
							.getObjectByObjectName("MMap", 0));
					// 取出险种，如果为主险则将 其 polno缓存起来，以备导入附加险时使用
					tLCPolSchema.setSchema((LCPolSchema) tProposalBL
							.getCardResult().getObjectByObjectName(
									"LCPolSchema", 0));
					if (tLCPolSchema.getMainPolNo().equals(
							tLCPolSchema.getPolNo())) {
						mMainPolMap.put(tLCPolSchema.getRiskCode(),
								tLCPolSchema.getMainPolNo());
					}
				}
			}

		} else {
			GrpPlanRiskDispatchBL tGrpPlanRiskDispatchBL = new GrpPlanRiskDispatchBL();
			if (!tGrpPlanRiskDispatchBL.submitData(mInputData, "")) {
				this.mErrors.copyAllErrors(tGrpPlanRiskDispatchBL.mErrors);
				return false;
			}
			mapLcpolBL.add((MMap) tGrpPlanRiskDispatchBL.getResult()
					.getObjectByObjectName("MMap", 0));
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mGlobalInput出错!";
			this.mErrors.addOneError(tError);
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mTransferData出错!";
			this.mErrors.addOneError(tError);

		}

		// 是否为套餐险种
		String tISPlanRisk = (String) mTransferData
				.getValueByName("ISPlanRisk");
		if (tISPlanRisk != null && tISPlanRisk.equals("Y")) {
			ISPlanRisk = true;
		}

		mSamePersonFlag = (String) mTransferData
				.getValueByName("samePersonFlag");
		if (mSamePersonFlag == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintAgentNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));
		if (mLCContSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLCContSchema出错!";
			this.mErrors.addOneError(tError);

		}
		contno = mLCContSchema.getContNo();
		//
		mLCInsuredSchema.setSchema((LCInsuredSchema) cInputData
				.getObjectByObjectName("LCInsuredSchema", 0));
		if (mLCInsuredSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLCAddressSchema出错!";
			this.mErrors.addOneError(tError);

		}

		// mLDPersonSchema.setSchema((LDPersonSchema)
		// cInputData.getObjectByObjectName("LDPersonSchema",0));
		// if (mLDPersonSchema == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "CardBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "处理mLDPersonSchema出错!";
		// this.mErrors.addOneError(tError);
		//
		// }

		mLACommisionDetailSet.set((LACommisionDetailSet) cInputData
				.getObjectByObjectName("LACommisionDetailSet", 0));
		if (mLACommisionDetailSet == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mLACommisionDetailSet出错!";
			this.mErrors.addOneError(tError);
		}
		return true;
	}

	// 根据被保人序号从set获取被保人信息
	private LCInsuredSchema getInsuSchema(LCInsuredSet sLCInsuredSet,
			String sSeqNo) {
		if (sLCInsuredSet == null || sLCInsuredSet.size() == 0) {
			return null;
		}
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		for (int i = 1; i <= sLCInsuredSet.size(); i++) {
			if (sLCInsuredSet.get(i).getSequenceNo().equals(sSeqNo)) {
				tLCInsuredSchema.setSchema(sLCInsuredSet.get(i));
				break;
			}
		}
		return tLCInsuredSchema;
	}

	// 返回判断投保人与被保人是否同一人, 0-否，1-是
	private String getSamePersonflag(LCAppntSchema sAppSchema,
			LCInsuredSchema sInsuSchema) {
		String SamePersonflag = "0";
		try {
			if (sAppSchema != null && sInsuSchema != null) {
				if (sAppSchema.getAppntName().trim().equals(
						sInsuSchema.getName().trim())
						&& sAppSchema.getAppntSex().trim().equals(
								sInsuSchema.getSex().trim())
						&& sAppSchema.getAppntBirthday().trim().equals(
								sInsuSchema.getBirthday().trim())
						&& sAppSchema.getIDType().trim().equals(
								sInsuSchema.getIDType().trim())
						&& sAppSchema.getIDNo().trim().equals(
								sInsuSchema.getIDNo().trim())) {
					SamePersonflag = "1";
				}
			}
		} catch (Exception ex) {
			SamePersonflag = "0";
		}
		return SamePersonflag;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean delData(VData dInputData) {
		return true;
	}

}
