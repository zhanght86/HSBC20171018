package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.certify.CertReveTakeBackUI;
import com.sinosoft.lis.certify.CertifyFunc;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LXbalanceDB;
import com.sinosoft.lis.db.LXbalanceSubDB;
import com.sinosoft.lis.db.LZCardDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LXbalanceSchema;
import com.sinosoft.lis.schema.LXbalanceSubSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LXbalanceSubSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class FinFeeCardCancelBL {
private static Logger logger = Logger.getLogger(FinFeeCardCancelBL.class);
	private String mBalanceNo = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	public CErrors mErrors = new CErrors();
	private MMap mMap = new MMap();
	private TransferData mTransferData = new TransferData();
	private LXbalanceSubSet mLXbalanceSubSet = new LXbalanceSubSet();
	private LXbalanceSchema mLXbalanceSchema = new LXbalanceSchema();
	private LZCardSet mLZCardSet = new LZCardSet();
	private VData mResult = new VData();

	public FinFeeCardCancelBL() {
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
		if (!ConfData()) {
			return false;
		}
		return true;
	}

	public MMap getMap() {
		return mMap;
	}

	private boolean ConfData() {
		mResult.add(mMap);
		PubSubmit tSubmit = new PubSubmit();
		logger.debug("go into PubSubmit");
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		if (mLZCardSet.size() > 0) {
			// 准备传输数据 VData
			VData vData = new VData();

			vData.addElement(mGlobalInput);
			vData.addElement(mLZCardSet);

			Hashtable hashParams = new Hashtable();
			hashParams.put("CertifyClass", CertifyFunc.CERTIFY_CLASS_CERTIFY);
			vData.addElement(hashParams);
			CertReveTakeBackUI tCertReveTakeBackUI = new CertReveTakeBackUI();
			if (!tCertReveTakeBackUI.submitData(vData, "INSERT")) {
				this.mErrors.copyAllErrors(tCertReveTakeBackUI.mErrors);
				return false;
			}
		}
		return true;
	}

	private boolean checkdata() {
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		String tSql = "select * from ljtempfee where tempfeetype='1' and otherno='?otherno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("otherno", mLXbalanceSchema.getProposalNo());
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
		if (tLJTempFeeSet.size() > 0) {
			CError.buildErr(this, "已交暂缴费,不能撤销");
			return false;
		}
		tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeDB.setOtherNo(mLXbalanceSchema.getBalanceNo());
		tLJTempFeeDB.setTempFeeType("9");
		tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeSet.size() > 0) {
			CError.buildErr(this, "已交暂缴费,不能撤销");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		// 卡单回收回退
		for (int i = 1; i <= mLXbalanceSubSet.size(); i++) {
			LXbalanceSubSchema tLXbalanceSubSchema = new LXbalanceSubSchema();
			tLXbalanceSubSchema = mLXbalanceSubSet.get(i);
			LZCardDB tLZCardDB = new LZCardDB();
			tLZCardDB.setCertifyCode(tLXbalanceSubSchema.getCardType());
			tLZCardDB.setStartNo(tLXbalanceSubSchema.getStartNo());
			tLZCardDB.setEndNo(tLXbalanceSubSchema.getEndNo());
			LZCardSet tLZCardSet = new LZCardSet();
			tLZCardSet = tLZCardDB.query();
			for (int t = 1; t <= tLZCardSet.size(); t++) {
				LZCardSchema tLZCardSchema = new LZCardSchema();
				tLZCardSchema = tLZCardSet.get(t);
				String SendMc = tLZCardSchema.getReceiveCom();
				String ReciMc = tLZCardSchema.getSendOutCom();
				tLZCardSchema.setSendOutCom(SendMc);
				tLZCardSchema.setReceiveCom(ReciMc);
				tLZCardSet.set(t, tLZCardSchema);
			}
			mLZCardSet.add(tLZCardSet);
		}
		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLXbalanceSchema, "DELETE");
		mMap.put(mLXbalanceSubSet, "DELETE");
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (cInputData.getObjectByObjectName("TransferData", 0) != null) {
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			mBalanceNo = (String) mTransferData.getValueByName("BalanceNo");
		}
		if (mBalanceNo == null || mBalanceNo.equals("")) {
			CError.buildErr(this, "传入参数为空");
			return false;
		}

		LXbalanceSubDB tLXbalanceSubDB = new LXbalanceSubDB();
		String tSql = "select * from LXbalanceSub where BalanceNo = '?mBalanceNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mBalanceNo", mBalanceNo);
		mLXbalanceSubSet = tLXbalanceSubDB.executeQuery(sqlbv1);
		if (mLXbalanceSubSet.size() == 0) {
			CError.buildErr(this, "结算子表为空");
			return false;
		}
		LXbalanceDB tLXbalanceDB = new LXbalanceDB();
		tSql = "select * from LXbalance where BalanceNo = '?mBalanceNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("mBalanceNo", mBalanceNo);

		mLXbalanceSchema = tLXbalanceDB.executeQuery(sqlbv2).get(1);
		if (mLXbalanceSchema == null) {
			CError.buildErr(this, "结算总表为空");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String mBalanceNo = "86210612060001";

		TransferData tTransferData = new TransferData();
		VData tVData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "862100";
		tGI.ManageCom = "86210000";
		tGI.Operator = "001";
		tVData.addElement(tGI);
		tTransferData.setNameAndValue("BalanceNo", mBalanceNo);
		tVData.addElement(tTransferData);
		FinFeeCardCancelBL finfeecardcancelbl = new FinFeeCardCancelBL();
		finfeecardcancelbl.submitData(tVData, "");
	}
}
