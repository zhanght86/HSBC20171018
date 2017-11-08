package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.FileManager;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.TxtExport;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 财务余额结算处理
 * <p>
 * Description:
 * </p>
 * 提取预收保费的未核销数据
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
public class LJFinBalanceBL {
private static Logger logger = Logger.getLogger(LJFinBalanceBL.class);
	public CErrors mErrors = new CErrors();
	private String mManageCom = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mCheckType = "ALL";
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String[] Title = new String[8];
	TxtExport txtExport = new TxtExport(); // 新建一个XmlExport的实例

	public LJFinBalanceBL() {
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mCheckType = (String) mTransferData.getValueByName("CheckType");
		return true;
	}

	private boolean checkdata() {
		if (mManageCom == null || mManageCom.equals("")) {
			CError.buildErr(this, "管理机构不能为空");
			return false;
		}
		if (mStartDate == null || mStartDate.equals("")) {
			CError.buildErr(this, "统计起期不能为空");
			return false;
		}
		if (mEndDate == null || mEndDate.equals("")) {
			CError.buildErr(this, "统计止期不能为空");
			return false;
		}

		return true;
	}

	private boolean dealData() {

		txtExport.createTxtDocument(FileManager.getFileName(
				FileManager.LJFINBALANCE, mGlobalInput.Operator, mStartDate,
				mEndDate), null);
		Title[0] = "收费号";
		Title[1] = "收费机构";
		Title[2] = "业务名称";
		Title[3] = "业务号";
		Title[4] = "金额";
		Title[5] = "到帐日期";
		Title[6] = "核销日期";
		Title[7] = "缴费方式";
		txtExport.outArray(Title);

		if (mCheckType != null && mCheckType.equals("1")) {
			DealNB();
		} else if (mCheckType != null && mCheckType.equals("2")) {
			DealRN();
		} else {
			DealNB();
			DealRN();
		}

		String fn = txtExport.getFileName();

		mResult.clear();
		mResult.add(fn);
		logger.debug(fn);
		return true;
	}

	private boolean DealNB() {
		LJFinBalanceSub tLJFinBalanceSub = new LJFinBalanceSub();
		String[][] Strr = tLJFinBalanceSub.NBBalance(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		return true;
	}

	private boolean DealStrr(String[][] Strr) {
		for (int i = 1; i <= Strr.length; i++) {
			String[] tt = new String[8];
			tt[0] = Strr[i - 1][0];
			tt[1] = Strr[i - 1][1];
			tt[2] = Strr[i - 1][2];
			tt[3] = Strr[i - 1][3];
			tt[4] = Strr[i - 1][4];
			tt[5] = Strr[i - 1][5];
			tt[6] = Strr[i - 1][6];
			tt[7] = Strr[i - 1][7];
			txtExport.outArray(tt);
		}
		return true;
	}

	private boolean DealRN() {
		String[][] Strr = null;
		LJFinBalanceSub tLJFinBalanceSub = new LJFinBalanceSub();
		Strr = tLJFinBalanceSub.RNBalanceDataTr(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub
				.RNBalanceFront(mStartDate, mEndDate, mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub.RNBalanceFrontConf(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub.RNBalanceFrontConfB(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub.RNBalanceMixPaymode(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub.RNBalanceNormal(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub.RNBalanceNotConf(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		Strr = tLJFinBalanceSub.RNBalanceRollback(mStartDate, mEndDate,
				mManageCom);
		DealStrr(Strr);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private boolean prepareOutputData() {
		return true;
	}

	public VData GetMMap() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.ManageCom = "86";
		tG.Operator = "Gaoht";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "866108");
		tTransferData.setNameAndValue("StartDate", "2006-04-01");
		tTransferData.setNameAndValue("EndDate", "2007-02-28");
		// tTransferData.setNameAndValue("CheckType","1");
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tG);
		LJFinBalanceBL ljfinbalancebl = new LJFinBalanceBL();
		ljfinbalancebl.submitData(tVData, "");
		XmlExport txmlExport = new XmlExport();
		VData tResult = new VData();
		tResult = ljfinbalancebl.getResult();
		txmlExport = (XmlExport) tResult.getObjectByObjectName("XmlExport", 0);
	}

}
