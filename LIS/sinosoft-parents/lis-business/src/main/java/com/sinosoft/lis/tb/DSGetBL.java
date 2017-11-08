package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBPOContDB;
import com.sinosoft.lis.db.LBPOPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOPolSchema;
import com.sinosoft.lis.vschema.LBPOContSet;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.lis.vschema.LBPOPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DSGetBL {
private static Logger logger = Logger.getLogger(DSGetBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	private VData rResult = new VData(); // add by yaory

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	private LBPOContSchema mLBPOContSchema = new LBPOContSchema();

	private LBPOPolSchema mLBPOPolSchema = new LBPOPolSchema();

	private TransferData mTransferData = new TransferData();

	private String tSequenceNo = "";

	private String tReleaseFlag = "";

	private String tFamilyType = "";

	private String tInsuredNo = "";

	private String tRiskSequence1 = "";

	private String tRiskSequence2 = "";

	private String tRiskSequence3 = "";

	private String tBonusGetMode1 = "";

	private String tBonusGetMode2 = "";

	private String tBonusGetMode3 = "";
	/**ContType 为2时代表多主险，为3时代表中介投保单*/
	private String tContType= "";
	/** 投保单如果是银代或简易投保单则BankFlag为1*/
	private String tBankFlag = "";

	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in DSGetBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");

		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		PubSubmit tPubSubmit = new PubSubmit();

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

		logger.debug("---commitData---");
		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLBPOPolSchema.setSchema((LBPOPolSchema) mInputData
				.getObjectByObjectName("LBPOPolSchema", 0));
		mLBPOContSchema.setSchema((LBPOContSchema) mInputData
				.getObjectByObjectName("LBPOContSchema", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tSequenceNo = (String) mTransferData.getValueByName("SequenceNo");
		tFamilyType = (String) mTransferData.getValueByName("FamilyType");
		tInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		tRiskSequence1 = (String) mTransferData.getValueByName("RiskSequence1");
		tRiskSequence2 = (String) mTransferData.getValueByName("RiskSequence2");
		tRiskSequence3 = (String) mTransferData.getValueByName("RiskSequence3");
		tBonusGetMode1 = (String) mTransferData
				.getValueByName("BonusGetMode1");
		tBonusGetMode2 = (String) mTransferData
				.getValueByName("BonusGetMode2");
		tBonusGetMode3 = (String) mTransferData
				.getValueByName("BonusGetMode3");
		tContType = (String) mTransferData.getValueByName("ContType");
		tBankFlag = (String) mTransferData.getValueByName("BankFlag");
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		if (tFamilyType.equals("1")) {
			/**
			 * 家庭单
			 * */
			if (!tSequenceNo.equals("") && tSequenceNo != null) {
				LBPOPolDB tLBPOPolDB = new LBPOPolDB();
				LBPOPolSet tLBPOPolSet = new LBPOPolSet();
				String tSql = "select insuredno from lbpoinsured where contno='"
						+ "?contno?"
						+ "' and inputno='"
						+ "?inputno?"
						+ "' and SequenceNo='"
						+ "?SequenceNo?" + "'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("contno", mLBPOPolSchema.getContNo());
				sqlbv.put("inputno", mLBPOPolSchema.getInputNo());
				sqlbv.put("SequenceNo", tSequenceNo);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(sqlbv);
				String ttInsuredNo = tSSRS.GetText(1, 1);
				tLBPOPolDB.setInsuredNo(ttInsuredNo);
				tLBPOPolDB.setContNo(mLBPOPolSchema.getContNo());
				tLBPOPolDB.setInputNo(mLBPOPolSchema.getInputNo());
				tLBPOPolSet = tLBPOPolDB.query();

				for (int i = 1; i <= tLBPOPolSet.size(); i++) {
					tLBPOPolSet.get(i).setInsuredNo(ttInsuredNo);
					tLBPOPolSet.get(i).setLiveGetMode(
							mLBPOPolSchema.getLiveGetMode());
					tLBPOPolSet.get(i).setBonusGetMode(
							mLBPOPolSchema.getBonusGetMode());
					tLBPOPolSet.get(i).setGetYear(mLBPOPolSchema.getGetYear());
					tLBPOPolSet.get(i)
							.setGetLimit(mLBPOPolSchema.getGetLimit());
					tLBPOPolSet.get(i).setGetYearFlag(
							mLBPOPolSchema.getGetYearFlag());
					tLBPOPolSet.get(i).setStandbyFlag3(
							mLBPOPolSchema.getStandbyFlag3());
					tLBPOPolSet.get(i).setModifyDate(theCurrentDate);
					tLBPOPolSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLBPOPolSet, "UPDATE");
			}

			    tReleaseFlag = mLBPOContSchema.getReleaseFlag();
				LBPOContDB tLBPOContDB = new LBPOContDB();
				LBPOContSet tLBPOContSet = new LBPOContSet();
				tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
				tLBPOContDB.setInputNo(mLBPOContSchema.getInputNo());
				tLBPOContDB.setFillNo(mLBPOContSchema.getFillNo());
				if (tLBPOContDB.getInfo() == false) {
					CError tError = new CError();
					tError.moduleName = "DSGetBL";
					tError.functionName = "checkPerson";
					tError.errorMessage = "查询合同信息失败!";
					this.mErrors.addOneError(tError);

					return false;
				}
				tLBPOContDB.setReleaseFlag(tReleaseFlag);
				tLBPOContDB.setAmnt(mLBPOContSchema.getAmnt());
				tLBPOContDB.setPrem(mLBPOContSchema.getPrem());
				tLBPOContDB.setPassword(mLBPOContSchema.getPassword());
				tLBPOContDB.setSumPrem(mLBPOContSchema.getSumPrem());
				tLBPOContDB.setPayIntv(mLBPOContSchema.getPayIntv());
				tLBPOContDB.setOutPayFlag(mLBPOContSchema.getOutPayFlag());
				tLBPOContDB.setPayMode(mLBPOContSchema.getPayMode());
				tLBPOContDB.setPayLocation(mLBPOContSchema.getPayLocation());
				tLBPOContDB.setBankCode(mLBPOContSchema.getBankCode());
				tLBPOContDB.setAccName(mLBPOContSchema.getAccName());
				tLBPOContDB.setBankAccNo(mLBPOContSchema.getBankAccNo());
				tLBPOContDB.setAutoPayFlag(mLBPOContSchema.getAutoPayFlag());
				tLBPOContDB.setModifyDate(theCurrentDate);
				tLBPOContDB.setModifyTime(theCurrentTime);

				map.put(tLBPOContDB.getSchema(), "UPDATE");
			return true;
		} else if(tContType.equals("2")){

			LBPOPolDB tLBPOPolDB = new LBPOPolDB();
			
			if (tBonusGetMode1 != null && !tBonusGetMode1.equals("")) {
				LBPOPolSet tLBPOPolSet1 = new LBPOPolSet();
				tLBPOPolDB.setInsuredNo(tInsuredNo);
				tLBPOPolDB.setContNo(mLBPOPolSchema.getContNo());
				tLBPOPolDB.setInputNo(mLBPOPolSchema.getInputNo());
				tLBPOPolDB.setRiskSequence(tRiskSequence1);
				tLBPOPolSet1 = tLBPOPolDB.query();
				if(tLBPOPolSet1.size()<1){
					CError tError = new CError();
					tError.moduleName = "DSGetBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该主险序号下未查到险种信息!";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= tLBPOPolSet1.size(); i++) {
					tLBPOPolSet1.get(i).setInsuredNo(tInsuredNo);
					tLBPOPolSet1.get(i).setLiveGetMode(
							mLBPOPolSchema.getLiveGetMode());
					tLBPOPolSet1.get(i).setBonusGetMode(tBonusGetMode1);
					tLBPOPolSet1.get(i).setGetYear(mLBPOPolSchema.getGetYear());
					tLBPOPolSet1.get(i)
							.setGetLimit(mLBPOPolSchema.getGetLimit());
					tLBPOPolSet1.get(i).setGetYearFlag(
							mLBPOPolSchema.getGetYearFlag());
					tLBPOPolSet1.get(i).setStandbyFlag3(
							mLBPOPolSchema.getStandbyFlag3());
					tLBPOPolSet1.get(i).setModifyDate(theCurrentDate);
					tLBPOPolSet1.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLBPOPolSet1, "UPDATE");
			}
			if (tBonusGetMode2 != null && !tBonusGetMode2.equals("")) {
				LBPOPolSet tLBPOPolSet2 = new LBPOPolSet();
				tLBPOPolDB.setInsuredNo(tInsuredNo);
				tLBPOPolDB.setContNo(mLBPOPolSchema.getContNo());
				tLBPOPolDB.setInputNo(mLBPOPolSchema.getInputNo());
				tLBPOPolDB.setRiskSequence(tRiskSequence2);
				tLBPOPolSet2 = tLBPOPolDB.query();
				if(tLBPOPolSet2.size()<1){
					CError tError = new CError();
					tError.moduleName = "DSGetBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该主险序号下未查到险种信息!";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= tLBPOPolSet2.size(); i++) {
					tLBPOPolSet2.get(i).setInsuredNo(tInsuredNo);
					tLBPOPolSet2.get(i).setLiveGetMode(
							mLBPOPolSchema.getLiveGetMode());
					tLBPOPolSet2.get(i).setBonusGetMode(tBonusGetMode2);
					tLBPOPolSet2.get(i).setGetYear(mLBPOPolSchema.getGetYear());
					tLBPOPolSet2.get(i)
							.setGetLimit(mLBPOPolSchema.getGetLimit());
					tLBPOPolSet2.get(i).setGetYearFlag(
							mLBPOPolSchema.getGetYearFlag());
					tLBPOPolSet2.get(i).setStandbyFlag3(
							mLBPOPolSchema.getStandbyFlag3());
					tLBPOPolSet2.get(i).setModifyDate(theCurrentDate);
					tLBPOPolSet2.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLBPOPolSet2, "UPDATE");
			}
			if (tBonusGetMode3 != null && !tBonusGetMode3.equals("")) {
				LBPOPolSet tLBPOPolSet3 = new LBPOPolSet();
				tLBPOPolDB.setInsuredNo(tInsuredNo);
				tLBPOPolDB.setContNo(mLBPOPolSchema.getContNo());
				tLBPOPolDB.setInputNo(mLBPOPolSchema.getInputNo());
				tLBPOPolDB.setRiskSequence(tRiskSequence3);
				tLBPOPolSet3 = tLBPOPolDB.query();
				if(tLBPOPolSet3.size()<1){
					CError tError = new CError();
					tError.moduleName = "DSGetBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该主险序号下未查到险种信息!";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= tLBPOPolSet3.size(); i++) {
					tLBPOPolSet3.get(i).setInsuredNo(tInsuredNo);
					tLBPOPolSet3.get(i).setLiveGetMode(
							mLBPOPolSchema.getLiveGetMode());
					tLBPOPolSet3.get(i).setBonusGetMode(tBonusGetMode3);
					tLBPOPolSet3.get(i).setGetYear(mLBPOPolSchema.getGetYear());
					tLBPOPolSet3.get(i)
							.setGetLimit(mLBPOPolSchema.getGetLimit());
					tLBPOPolSet3.get(i).setGetYearFlag(
							mLBPOPolSchema.getGetYearFlag());
					tLBPOPolSet3.get(i).setStandbyFlag3(
							mLBPOPolSchema.getStandbyFlag3());
					tLBPOPolSet3.get(i).setModifyDate(theCurrentDate);
					tLBPOPolSet3.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLBPOPolSet3, "UPDATE");
			}
			

//			LBPOContDB tLBPOContDB = new LBPOContDB();
//			tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
//			tLBPOContDB.setInputNo(mLBPOContSchema.getInputNo());
//			if (tLBPOContDB.getInfo() == false) {
//				CError tError = new CError();
//				tError.moduleName = "DSGetBL";
//				tError.functionName = "dealData";
//				tError.errorMessage = "查询合同信息失败!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//			tLBPOContDB.setAmnt(mLBPOContSchema.getAmnt());
//			tLBPOContDB.setPrem(mLBPOContSchema.getPrem());
//			tLBPOContDB.setPassword(mLBPOContSchema.getPassword());
//			tLBPOContDB.setSumPrem(mLBPOContSchema.getSumPrem());
//			tLBPOContDB.setPayIntv(mLBPOContSchema.getPayIntv());
//			tLBPOContDB.setOutPayFlag(mLBPOContSchema.getOutPayFlag());
//			tLBPOContDB.setPayMode(mLBPOContSchema.getPayMode());
//			tLBPOContDB.setPayLocation(mLBPOContSchema.getPayLocation());
//			tLBPOContDB.setBankCode(mLBPOContSchema.getBankCode());
//			tLBPOContDB.setAccName(mLBPOContSchema.getAccName());
//			tLBPOContDB.setBankAccNo(mLBPOContSchema.getBankAccNo());
//			tLBPOContDB.setAutoPayFlag(mLBPOContSchema.getAutoPayFlag());
//			tLBPOContDB.setApproveTime(mLBPOContSchema.getApproveTime());
//			tLBPOContDB.setApproveCode(mLBPOContSchema.getApproveCode());
			mLBPOContSchema.setModifyDate(theCurrentDate);
			mLBPOContSchema.setModifyTime(theCurrentTime);

			map.put(mLBPOContSchema, "UPDATE");
			return true;
		}else if(tContType.equals("3")){
			LBPOContDB tLBPOContDB = new LBPOContDB();
			LBPOContSet tLBPOContSet = new LBPOContSet();
			tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
			tLBPOContDB.setInputNo(mLBPOContSchema.getInputNo());
			tLBPOContDB.setFillNo(mLBPOContSchema.getFillNo());
			if (tLBPOContDB.getInfo() == false) {
				CError.buildErr(this, "查询合同信息失败！");
				return false;
			}
			tLBPOContDB.setReleaseFlag(tReleaseFlag);
			tLBPOContDB.setAmnt(mLBPOContSchema.getAmnt());
			tLBPOContDB.setPrem(mLBPOContSchema.getPrem());
			tLBPOContDB.setPassword(mLBPOContSchema.getPassword());
			tLBPOContDB.setSumPrem(mLBPOContSchema.getSumPrem());
			tLBPOContDB.setPayIntv(mLBPOContSchema.getPayIntv());
			tLBPOContDB.setOutPayFlag(mLBPOContSchema.getOutPayFlag());
			tLBPOContDB.setPayMode(mLBPOContSchema.getPayMode());
			tLBPOContDB.setPayLocation(mLBPOContSchema.getPayLocation());
			if(!"1".equals(tBankFlag)){
				tLBPOContDB.setBankCode(mLBPOContSchema.getBankCode());
				tLBPOContDB.setAccName(mLBPOContSchema.getAccName());
				tLBPOContDB.setBankAccNo(mLBPOContSchema.getBankAccNo());
			}
			tLBPOContDB.setRnewFlag(mLBPOContSchema.getRnewFlag());
			tLBPOContDB.setAutoPayFlag(mLBPOContSchema.getAutoPayFlag());
			tLBPOContDB.setApproveTime(mLBPOContSchema.getApproveTime());
			tLBPOContDB.setApproveCode(mLBPOContSchema.getApproveCode());
			tLBPOContDB.setModifyDate(theCurrentDate);
			tLBPOContDB.setModifyTime(theCurrentTime);
			map.put(tLBPOContDB.getSchema(), "UPDATE");
			
			LBPOPolDB tLBPOPolDB = new LBPOPolDB();
			LBPOPolSet tLBPOPolSet = new LBPOPolSet();
			LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
			tLBPOPolDB.setContNo(mLBPOContSchema.getContNo());
			tLBPOPolDB.setInputNo(mLBPOContSchema.getInputNo());
			tLBPOPolDB.setFillNo("1");
			tLBPOPolSet = tLBPOPolDB.query();
			tLBPOPolSchema = tLBPOPolSet.get(1);
			tLBPOPolSchema.setLiveGetMode(mLBPOPolSchema.getLiveGetMode());
			tLBPOPolSchema.setBonusGetMode(mLBPOPolSchema.getBonusGetMode());
			tLBPOPolSchema.setGetYear(mLBPOPolSchema.getGetYear());
			tLBPOPolSchema.setGetYearFlag(mLBPOPolSchema.getGetYearFlag());
			tLBPOPolSchema.setGetLimit(mLBPOPolSchema.getGetLimit());
			tLBPOPolSchema.setStandbyFlag3(mLBPOPolSchema.getStandbyFlag3());
			map.put(tLBPOPolSchema, "UPDATE");
			return true;
		}
		return true;
	}

	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
