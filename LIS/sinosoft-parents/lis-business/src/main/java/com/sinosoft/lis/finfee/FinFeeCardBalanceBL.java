package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.certify.PubCertifyTakeBack;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LXbalanceSchema;
import com.sinosoft.lis.schema.LXbalanceSubSchema;
import com.sinosoft.lis.vschema.LXbalanceSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 卡单结算
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class FinFeeCardBalanceBL {
private static Logger logger = Logger.getLogger(FinFeeCardBalanceBL.class);
	private GlobalInput mGlobalInput = new GlobalInput();
	public CErrors mErrors = new CErrors();
	private String BalanceNo = "";
	private MMap mMap = new MMap();
	private TransferData mTransferData = new TransferData();
	private LXbalanceSubSet mLXbalanceSubSet = new LXbalanceSubSet();
	private LXbalanceSchema mLXbalanceSchema = new LXbalanceSchema();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mOtherNo = "";
	private String mOtherNoType = "";
	private String mBalanceType = "";

	public FinFeeCardBalanceBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLXbalanceSubSet.set((LXbalanceSubSet) cInputData
				.getObjectByObjectName("LXbalanceSubSet", 0));
		if (mLXbalanceSubSet.size() == 0) {
			CError.buildErr(this, "传入参数为空");
			return false;
		}

		if (cInputData.getObjectByObjectName("TransferData", 0) != null) {
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			mOtherNo = (String) mTransferData.getValueByName("OtherNo");
			mOtherNoType = (String) mTransferData.getValueByName("OtherNoType");
		}
		return true;
	}

	private boolean checkdata() {

		return true;
	}

	public MMap getMap() {
		return mMap;
	}

	private boolean dealData() {
		double SumMoney = 0;
		int SumInt = 0;
		// 生成结算号
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		BalanceNo = PubFun1.CreateMaxNo("BALANCENO", strNoLimit);
		for (int i = 1; i <= mLXbalanceSubSet.size(); i++) {
			LXbalanceSubSchema tLXbalanceSubSchema = new LXbalanceSubSchema();
			tLXbalanceSubSchema = mLXbalanceSubSet.get(i);
			SumMoney = SumMoney + tLXbalanceSubSchema.getUnitPrice();
			SumInt = SumInt + tLXbalanceSubSchema.getCount();
			tLXbalanceSubSchema.setProFlag("0");
			tLXbalanceSubSchema.setBalanceNo(BalanceNo);
			tLXbalanceSubSchema.setMakeDate(CurrentDate);
			tLXbalanceSubSchema.setMakeTime(CurrentTime);
			tLXbalanceSubSchema.setModifyDate(CurrentDate);
			tLXbalanceSubSchema.setModifyTime(CurrentTime);
			tLXbalanceSubSchema.setOperator(mGlobalInput.Operator);
			mLXbalanceSubSet.set(i, tLXbalanceSubSchema);
		}

		mLXbalanceSchema.setBalanceNo(BalanceNo);
		if (!mLXbalanceSubSet.get(1).getStandbyFlag1().equals("")) {
			mLXbalanceSchema.setBalanceType(mLXbalanceSubSet.get(1)
					.getStandbyFlag2());
			mLXbalanceSchema.setOtherNo(mLXbalanceSubSet.get(1)
					.getStandbyFlag1());
		} else if (mOtherNo.equals("") && mOtherNoType.equals("")
				&& mBalanceType.equals("")) {
			mLXbalanceSchema.setOtherNo("99");
			mLXbalanceSchema.setBalanceType("0");
		} else {
			mLXbalanceSchema.setOtherNo(mOtherNo);
			mLXbalanceSchema.setOtherNoType(mOtherNoType);
			mLXbalanceSchema.setBalanceType(mBalanceType);
		}
		mLXbalanceSchema.setOtherNoType(mLXbalanceSubSet.get(1)
				.getStandbyFlag2());
		mLXbalanceSchema.setAgentCom(mLXbalanceSubSet.get(1).getAgentCom());
		mLXbalanceSchema.setManageCom(mLXbalanceSubSet.get(1).getManageCom());
		mLXbalanceSchema.setSumPrem(SumMoney);
		mLXbalanceSchema.setMult(SumInt);
		mLXbalanceSchema.setProposalNo(mLXbalanceSubSet.get(1)
				.getStandbyFlag1());
		mLXbalanceSchema.setMakeDate(CurrentDate);
		mLXbalanceSchema.setMakeTime(CurrentTime);
		mLXbalanceSchema.setModifyDate(CurrentDate);
		mLXbalanceSchema.setModifyTime(CurrentTime);
		mLXbalanceSchema.setOperator(mGlobalInput.Operator);

		return true;
	}

	public boolean CertifyTakeBack() {
		int ErrNo = 0;
		String ErrMassage = "";
		PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
		if (tPubCertifyTakeBack
				.CheckNewType(tPubCertifyTakeBack.CERTIFY_CheckNo1)) {
			for (int i = 1; i <= mLXbalanceSubSet.size(); i++) {
				LXbalanceSubSchema tLXbalanceSubSchema = new LXbalanceSubSchema();
				tLXbalanceSubSchema = mLXbalanceSubSet.get(i);
				String tStartNo = tLXbalanceSubSchema.getStartNo();
				String tEndNo = tLXbalanceSubSchema.getEndNo();
				String tCertifyCode = tLXbalanceSubSchema.getCardType();
				tPubCertifyTakeBack = new PubCertifyTakeBack();
				if (!tPubCertifyTakeBack.CertifyTakeBack_A(tStartNo, tEndNo,
						tCertifyCode, mGlobalInput)) {
					this.mErrors.copyAllErrors(tPubCertifyTakeBack.mErrors);
					return false;
				} else {
					MMap tCertMap = new MMap();
					tCertMap = (MMap) tPubCertifyTakeBack.getResult()
							.getObjectByObjectName("MMap", 0);
					VData tVData = new VData();
					tVData.add(tCertMap);
					PubSubmit tSubmit = new PubSubmit();
					if (!tSubmit.submitData(tVData, "")) {
						// @@错误处理
						ErrNo++;
						ErrMassage = ErrMassage + tStartNo + "至" + tEndNo
								+ "回收失败; ";
						continue;
					}
				}
			}
			if (ErrNo > 0) {
				CError.buildErr(this, "结算成功,但 " + ErrMassage + " 请手工回收");
				return false;
			}
		}
		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLXbalanceSchema, "INSERT");
		mMap.put(mLXbalanceSubSet, "INSERT");
		return true;
	}

	public String getBalanceNo() {
		return BalanceNo;
	}

	public static void main(String[] args) {
		String Date = "2006-11-08";
		logger.debug(Date.substring(2, 4));
		logger.debug(Date.substring(5, 7));
		logger.debug(Date.substring(8, 10));
		String tNoLimit = PubFun.getNoLimit("86210000");
		String BalanceNo = PubFun1.CreateMaxNo("BalanceNo", tNoLimit);
		logger.debug("BalanceNo===================" + BalanceNo);
		FinFeeCardBalanceBL finfeecardbalancebl = new FinFeeCardBalanceBL();
	}
}
