package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class RNGrpAutoVerifyQuery {
private static Logger logger = Logger.getLogger(RNGrpAutoVerifyQuery.class);
	private String mGrpContNo, mMangeCom, mStartDate, mAgentCode, mEndDate;
	private TransferData mTransferData = new TransferData();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private GlobalInput mGI = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public RNGrpAutoVerifyQuery() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGI = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null) {
			mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
			mMangeCom = (String) mTransferData.getValueByName("MangeCom");
			mStartDate = (String) mTransferData.getValueByName("StartDate");
			mEndDate = (String) mTransferData.getValueByName("EndDate");
			mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		}
		if (mMangeCom == null || mMangeCom.equals("")) // 如果不录入管理机构则管理机构取全局
		{
			mMangeCom = mGI.ManageCom;
		}

		if (mMangeCom != null && !mMangeCom.equals("")) {
			String UserManagecom = mGI.ManageCom;
			int UserManagecomLength = UserManagecom.length();
			int ManagecomLength = mMangeCom.length();
			if (ManagecomLength < UserManagecomLength) {
				CError.buildErr(this, "机构越权操作");
				return false;
			} else if (!mMangeCom.substring(0, UserManagecomLength).equals(
					UserManagecom)) {
				String r = mMangeCom.substring(0, UserManagecomLength);
				CError.buildErr(this, "机构越权操作");
				return false;
			}

		}

		return true;
	}

	private boolean dealData() {
		String tSql = "select * from lcgrpcont where grpcontno in"
				+ "(select otherno from ljspay where othernotype='1'"
				+ " and (exists (select 1 from ljtempfee where enteraccdate is not null and enteraccdate<>'3000-1-1' and confdate is null and tempfeeno=ljspay.getnoticeno)"
				+ " or exists (select 1 from lcgrpcont where dif>0 and grpcontno=ljspay.otherno)))";

		if (mGrpContNo != null && !mGrpContNo.equals("")) {
			tSql = tSql + " and GrpContNo='" + mGrpContNo + "'";
		}
		if (mMangeCom != null && !mMangeCom.equals("")) {
			tSql = tSql + " and managecom like '" + mMangeCom + "%'";
		}
		if (mStartDate != null && !mStartDate.equals("")) {
			tSql = tSql + " and startpaydate >='" + mStartDate + "'";
		}
		if (mEndDate != null && !mEndDate.equals("")) {
			tSql = tSql + " and startpaydate <='" + mEndDate + "'";
		}
		if (mAgentCode != null && !mAgentCode.equals("")) {
			tSql = tSql + " and agentcode='" + mAgentCode + "'";
		}
		logger.debug("SQL================" + tSql);
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		mLCGrpContSet = tLCGrpContDB.executeQuery(tSql);
		if (mLCGrpContSet.size() == 0) {
			CError.buildErr(this, "未查询到待核销保单");
			return false;
		}
		mResult.add(mLCGrpContSet);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		RNGrpAutoVerifyQuery rngrpautoverifyquery = new RNGrpAutoVerifyQuery();
	}
}
