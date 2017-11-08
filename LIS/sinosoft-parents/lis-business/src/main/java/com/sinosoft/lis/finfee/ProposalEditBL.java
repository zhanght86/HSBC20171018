//LJCertifyPayBL.java

package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJCertifyGetAgentDB;
import com.sinosoft.lis.db.LJCertifyGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJCertifyGetAgentSchema;
import com.sinosoft.lis.schema.LJCertifyGetSchema;
import com.sinosoft.lis.vschema.LJCertifyGetAgentSet;
import com.sinosoft.lis.vschema.LJCertifyGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class ProposalEditBL {
private static Logger logger = Logger.getLogger(ProposalEditBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private String AgentCode = "";
	private String ActuGetNo = "";
	private double mDelMoney = 0;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LJCertifyGetAgentSet mLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
	private LJCertifyGetAgentSet OutLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
	private LJCertifyGetSchema mLJCertifyGetSchema = new LJCertifyGetSchema();

	public ProposalEditBL() {

	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mLJCertifyGetAgentSet = (LJCertifyGetAgentSet) cInputData
				.getObjectByObjectName("LJCertifyGetAgentSet", 0);
		for (int i = 1; i <= mLJCertifyGetAgentSet.size(); i++) {
			logger.debug("OPOPOP==================="
					+ mLJCertifyGetAgentSet.get(i).getFeeFinaType());
		}
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLJCertifyGetAgentSet.size() == 0) {
			CError.buildErr(this, "传入信息不足");
			return false;
		}
		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 获得业务数据；
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 数据效验
		if (!checkDate()) {
			return false;
		}
		// 获得打印数据
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("----------LJCertifyTempFeeBL End----------");
		return true;
	}

	private boolean checkDate() {
		ActuGetNo = mLJCertifyGetAgentSet.get(1).getActuGetNo();
		AgentCode = mLJCertifyGetAgentSet.get(1).getAgentCode();
		LJCertifyGetSet tLJCertifyGetSet = new LJCertifyGetSet();
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();
		tLJCertifyGetDB.setActuGetNo(ActuGetNo);
		tLJCertifyGetSet = tLJCertifyGetDB.query();

		if (!tLJCertifyGetDB.getInfo()) {
			CError.buildErr(this, "未找到代理人信息");
			return false;
		}

		String tEnterAccDate = tLJCertifyGetDB.getEnterAccDate();
		logger.debug("tEnterAccDate   " + tEnterAccDate);
		if (tEnterAccDate != null) {
			CError.buildErr(this, "此次操作已完成");
			return false;
		}

		LJCertifyGetAgentSet tLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
		LJCertifyGetAgentDB tLJCertifyGetAgentDB = new LJCertifyGetAgentDB();
		tLJCertifyGetAgentDB.setActuGetNo(ActuGetNo);
		tLJCertifyGetAgentSet = tLJCertifyGetAgentDB.query();

		String ttEnterAccDate = tLJCertifyGetAgentSet.get(1).getEnterAccDate();
		if (tEnterAccDate != null) {
			CError.buildErr(this, "此次操作已完成");
			return false;
		}

		return true;
	}

	private boolean dealData() {
		for (int i = 1; i <= mLJCertifyGetAgentSet.size(); i++) {
			LJCertifyGetAgentSchema tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
			tLJCertifyGetAgentSchema = mLJCertifyGetAgentSet.get(i);
			logger.debug("##################" + i + "#################");
			logger.debug("##################"
					+ tLJCertifyGetAgentSchema.getFeeFinaType()
					+ "#################");
			if (tLJCertifyGetAgentSchema.getFeeFinaType().equals("HK")
					&& !tLJCertifyGetAgentSchema.getFeeFinaType().equals("")) {
				logger.debug("=======================" + i
						+ "=======================");
				mDelMoney = mDelMoney + tLJCertifyGetAgentSchema.getGetMoney();
				String tSql = "select * from LJCertifyGetAgent where actugetno='?actugetno?'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				
				sqlbv.sql(tSql);
				sqlbv.put("actugetno", tLJCertifyGetAgentSchema.getActuGetNo());
				LJCertifyGetAgentSet tLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
				LJCertifyGetAgentDB tLJCertifyGetAgentDB = new LJCertifyGetAgentDB();
				tLJCertifyGetAgentSet = tLJCertifyGetAgentDB.executeQuery(sqlbv);
				if (tLJCertifyGetAgentSet.size() == 0) {
					CError.buildErr(this, "还没有申请领取");
					return false;
				}
				LJCertifyGetAgentSchema nLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
				nLJCertifyGetAgentSchema = tLJCertifyGetAgentSet.get(1);
				nLJCertifyGetAgentSchema.setFeeFinaType("HK");
				nLJCertifyGetAgentSchema.setGetMoney(tLJCertifyGetAgentSchema
						.getGetMoney());
				nLJCertifyGetAgentSchema.setMakeDate(CurrentDate);
				nLJCertifyGetAgentSchema.setModifyDate(CurrentDate);
				nLJCertifyGetAgentSchema.setMakeTime(CurrentTime);
				nLJCertifyGetAgentSchema.setModifyTime(CurrentTime);
				nLJCertifyGetAgentSchema.setOperator(mGlobalInput.Operator);
				OutLJCertifyGetAgentSet.add(nLJCertifyGetAgentSchema);
			}
		}

		LJCertifyGetSet tLJCertifyGetSet = new LJCertifyGetSet();
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();
		tLJCertifyGetDB.setActuGetNo(mLJCertifyGetAgentSet.get(1)
				.getActuGetNo());
		tLJCertifyGetSet = tLJCertifyGetDB.query();
		mLJCertifyGetSchema = tLJCertifyGetSet.get(1);
		double tMoney = 0;
		tMoney = mLJCertifyGetSchema.getSumGetMoney();
		tMoney = tMoney + mDelMoney;
		mLJCertifyGetSchema.setSumGetMoney(tMoney);
		mLJCertifyGetSchema.setModifyDate(CurrentDate);
		mLJCertifyGetSchema.setModifyTime(CurrentTime);
		map.put(mLJCertifyGetSchema, "UPDATE");
		map.put(OutLJCertifyGetAgentSet, "DELETE&INSERT");
		return true;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyPayBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		LJCertifyGetAgentSchema tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
		LJCertifyGetAgentSet tLJCertifyGetAgentSet = new LJCertifyGetAgentSet();
		// tLJCertifyGetAgentSchema.setFeeFinaType("KK");
		// tLJCertifyGetAgentSchema.setActuGetNo("370320000036200");
		// tLJCertifyGetAgentSchema.setGetMoney("-230");
		// tLJCertifyGetAgentSet.add(tLJCertifyGetAgentSchema);
		tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
		tLJCertifyGetAgentSchema.setFeeFinaType("TF");
		tLJCertifyGetAgentSchema.setActuGetNo("370320000036201");
		tLJCertifyGetAgentSchema.setGetMoney("500");
		tLJCertifyGetAgentSet.add(tLJCertifyGetAgentSchema);
		tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
		tLJCertifyGetAgentSchema.setFeeFinaType("HK");
		tLJCertifyGetAgentSchema.setActuGetNo("370320000036201");
		tLJCertifyGetAgentSchema.setGetMoney("100");
		tLJCertifyGetAgentSet.add(tLJCertifyGetAgentSchema);
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "862100";
		tGI.ManageCom = "862100";
		tGI.Operator = "001";
		VData tVData = new VData();
		tVData.addElement(tGI);
		tVData.addElement(tLJCertifyGetAgentSet);
		ProposalEditUI tProposalEditUI = new ProposalEditUI();
		tProposalEditUI.submitData(tVData, "VERIFY");
	}

}
