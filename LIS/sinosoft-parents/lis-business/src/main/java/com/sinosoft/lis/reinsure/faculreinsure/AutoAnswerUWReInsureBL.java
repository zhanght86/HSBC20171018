

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.db.RIUWTaskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIAnwserIdeaSchema;
import com.sinosoft.lis.schema.RIUWTaskSchema;
import com.sinosoft.lis.vschema.RIUWTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: </p>
 * <p>Description: 再保,临分审核自动回复</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @caosg
 * @version 1.0
 */
public class AutoAnswerUWReInsureBL {

	/** 传入参数 */
	private VData mInputData;

	/** 传入操作符 */
	private String mOperate;

	/** 登陆信息 */
	private GlobalInput mGlobalInput;

	/** 报错存储对象 */
	public CErrors mErrors = new CErrors();

	/** 最后保存结果 */
	private VData mResult = new VData();

	/** 最后递交Map */
	private MMap map = new MMap();

	private String mOpeData;

	private String mOpeFlag;

	private String mFilePath;

	private String mFileName;

	private TransferData mTransferData = new TransferData();

	/** 再保审核任务表 */
	private RIUWTaskSchema mRIUWTaskSchema = new RIUWTaskSchema();

	/** 再保审核任务核保意见表 */
	private RIAnwserIdeaSchema mRIAnwserIdeaSchema = new RIAnwserIdeaSchema();
	
	public AutoAnswerUWReInsureBL(){
		
	}

	/**
	 * submitData
	 * 
	 * @param nInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData nInputData, String cOperate) {
		this.mInputData = nInputData;
		this.mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(this.mResult, "INSERT")) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * getInputData
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AutoAnswerUWReInsureBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * checkData
	 * @return boolean
	 */
	private boolean checkData() {
		if (this.mGlobalInput == null) {
			String str = "登陆信息为null，可能是页面超时，请重新登陆!";
			buildError("checkData", str);
			return false;
		}
		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		mOpeData = (String) mTransferData.getValueByName("OpeData");
		mOpeFlag = (String) mTransferData.getValueByName("OpeFlag");
		RIUWTaskDB tRIUWTaskDB = new RIUWTaskDB();
		StringBuffer sql = new StringBuffer();

		if (mOpeFlag.equals("01")) {
			sql.append("select * from RIUWTASK b where b.serialno=(select max(a.serialno) from RIUWTask a where a.proposalgrpcontno='000000' and a.proposalcontno='");
			sql.append(mOpeData);
			sql.append("' and a.AuditType='01') ");

			RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
			tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
			if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
				buildError("dealData", "查询任务信息失败");
				return false;
			} else {
				mRIUWTaskSchema = tRIUWTaskSet.get(1);
				mRIUWTaskSchema.setState("01");
				mRIUWTaskSchema.setOperator("系统");
				mRIUWTaskSchema.setManageCom("系统");
				mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
				mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(mRIUWTaskSchema, "UPDATE");
			}
			mRIAnwserIdeaSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
			mRIAnwserIdeaSchema.setUWNo(mRIUWTaskSchema.getUWNo());
			mRIAnwserIdeaSchema.setProposalGrpContNo("000000");
			mRIAnwserIdeaSchema.setProposalContNo(mOpeData);
			mRIAnwserIdeaSchema.setAuditType(mOpeFlag);
			mRIAnwserIdeaSchema.setAuditCode("000000");
			mRIAnwserIdeaSchema.setAuditAffixCode("000000");
			mRIAnwserIdeaSchema.setAdjunctPath("");
			mRIAnwserIdeaSchema.setReInsurer("系统");
			mRIAnwserIdeaSchema.setOperator("系统");
			mRIAnwserIdeaSchema.setUWIdea("系统自动回复");
			mRIAnwserIdeaSchema.setManageCom(mGlobalInput.ManageCom);
			mRIAnwserIdeaSchema.setMakeDate(PubFun.getCurrentDate());
			mRIAnwserIdeaSchema.setMakeTime(PubFun.getCurrentTime());
			mRIAnwserIdeaSchema.setModifyDate(PubFun.getCurrentDate());
			mRIAnwserIdeaSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(mRIAnwserIdeaSchema, "INSERT");

		} else if (mOpeFlag.equals("03")) {

		} else if (mOpeFlag.equals("04")) {

		} else if (mOpeFlag.equals("05")) {

		} else {
			String str = "非法数据处理类型 !";
			buildError("dealData", str);
		}
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		this.mResult.add(map);
		return true;
	}

	/**
	 * 出错处理
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "AutoAnswerUWReInsureBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
