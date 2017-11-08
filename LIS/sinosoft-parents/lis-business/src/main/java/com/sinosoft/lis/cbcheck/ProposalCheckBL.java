package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
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
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class ProposalCheckBL {
private static Logger logger = Logger.getLogger(ProposalCheckBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mPrtNo; // 印刷号
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema(); // 投保人表
	private LCAppntSet mLCAppntSet = new LCAppntSet(); // 投保人表
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); // 被保人表
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet(); // 被保人表
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCBnfSchema mLCBnfSchema = new LCBnfSchema(); // 受益人表
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSchema mLCpolSchema = new LCPolSchema(); // 保单险种
	private LCInsuredRelatedSet mLCInsuredRelatedSet = new LCInsuredRelatedSet();

	public ProposalCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		if (cOperate.equals("submit")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCInsuredUWBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProposalApproveAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProposalApproveAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		if (mPrtNo == null || mPrtNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProposalCheckBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传输投保单号失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	private boolean dealData() {
		// 进行被保人，校验投保人和被保人进行校验
		if (!getProposalInfo()) {
			return false;
		}
		if (!checkAppnt()) {
			return false;
		}
		if (!checkInsured()) {
			return false;
		}

		return true;
	}

	/**
	 * 根据PrtNo查询投保单的信息 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean getProposalInfo() {
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCBnfDB tLCBnfDB = new LCBnfDB();
		LCPolDB tLCPolDB = new LCPolDB();
		// ///////////////////////////////////////////////////////
		// 查询投保人信息
		tLCAppntDB.setPrtNo(mPrtNo);
		this.mLCAppntSet = tLCAppntDB.query();
		if (mLCAppntSet == null || mLCAppntSet.size() < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalCheckBL";
			tError.functionName = "getProposalInfo";
			tError.errorMessage = "投保人信息查询失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// ///////////////////////////////////////////////////////
		// 查询被保人信息
		tLCInsuredDB.setPrtNo(mPrtNo);
		mLCInsuredSet = tLCInsuredDB.query();
		if (mLCInsuredSet == null || mLCInsuredSet.size() < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalCheckBL";
			tError.functionName = "getProposalInfo";
			tError.errorMessage = "被保人信息查询失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// ///////////////////////////////////////////////////////
		// 查询险种信息
		tLCPolDB.setPrtNo(mPrtNo);
		mLCPolSet = tLCPolDB.query();
		if (mLCPolSet == null || mLCPolSet.size() < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalCheckBL";
			tError.functionName = "getProposalInfo";
			tError.errorMessage = "险种信息查询失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// ///////////////////////////////////////////////////////

		return true;
	}

	/**
	 * 进行如果投保人与被保人是本人时对投保人校验 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean checkAppnt() {
		// 进行被保人，校验投保人和被保人进行校验
		// tongmeng 2008-01-18 add
		// 增加投保人与被保人关系不能为空的校验

		if (mLCAppntSet.get(1).getRelationToInsured() == null
				|| mLCAppntSet.get(1).getRelationToInsured().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ProposalCheckBL";
			tError.functionName = "getProposalInfo";
			tError.errorMessage = "投保人与被保人关系不能为空！";
			this.mErrors.addOneError(tError);

			return false;
		}
		if (mLCAppntSet.get(1).getRelationToInsured().equals("00")) {
			// 先查询出数据库中,与投保人是同一个客户号的被保人信息
			String tSQL = " select * from lcinsured where contno='"
					+ "?contno?" + "' "
					+ " and insuredno='" + "?insuredno?"
					+ "' " + "";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("contno", mLCAppntSet.get(1).getContNo());
			sqlbv.put("insuredno", mLCAppntSet.get(1).getAppntNo());
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredSet = tLCInsuredDB.executeQuery(sqlbv);
			if (tLCInsuredSet.size() <= 0) {
				CError.buildErr(this, "查询被保险人信息失败,请确认投被保人关系！");
				return false;
			}
			for (int i = 0; i < tLCInsuredSet.size(); i++) {
				// 如果被保人和投保人是同一人
				// tongmeng 2008-07-04 modify
				// 支持多被保人录入,如果被保人下有一个一致就可以.
				// mLCInsuredSet
				if (!tLCInsuredSet.get(i + 1).getInsuredNo().equals(
						mLCAppntSet.get(1).getAppntNo())) {

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "投保人与被保人是同一人但是客户号不一致！";
					this.mErrors.addOneError(tError);

					return false;
				}
				// 判断姓名是否一致
				if (!tLCInsuredSet.get(i + 1).getName().equals(
						mLCAppntSet.get(1).getAppntName())) {

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "投保人与被保人是同一人但是姓名不一致！";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 判断性别是否一致
				if (!tLCInsuredSet.get(i + 1).getSex().equals(
						mLCAppntSet.get(1).getAppntSex())) {

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "投保人与被保人是同一人但是性别不一致！";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 判断生日是否一致
				if (!tLCInsuredSet.get(i + 1).getBirthday().equals(
						mLCAppntSet.get(1).getAppntBirthday())) {

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "投保人与被保人是同一人但是生日不一致！";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 判断证件号码是否一致
				// 判断证件类型是否一致
				if (!tLCInsuredSet.get(i + 1).getIDType().equals(
						mLCAppntSet.get(1).getIDType())) {

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "投保人与被保人是同一人但是证件类型不一致！";
					this.mErrors.addOneError(tError);
					return false;
				}
				if(!tLCInsuredSet.get(i + 1).getIDType().equals("9")){
					if (!tLCInsuredSet.get(i + 1).getIDNo().equals(
							mLCAppntSet.get(1).getIDNo())) {
						
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "ProposalCheckBL";
						tError.functionName = "getProposalInfo";
						tError.errorMessage = "投保人与被保人是同一人但是证件号码不一致！";
						this.mErrors.addOneError(tError);
						return false;
					}
				}

			}

		}

		return true;
	}

	/**
	 * 进行被保人校验，判断被保人和险种信息是否一致 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @return boolean
	 * 
	 */
	private boolean checkInsured() {
		// 进行被保人，校验投保人和被保人进行校验
		// --------modify by haopan 2007-1-29
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		// 循环保单进行校验
		for (int j = 0; j < this.mLCPolSet.size(); j++) {
			// 校验每张投保单的被保人是否属于被保人表中

			tLCInsuredDB.setPrtNo(mLCPolSet.get(j + 1).getPrtNo());
			tLCInsuredDB.setInsuredNo(mLCPolSet.get(j + 1).getInsuredNo());

			tLCInsuredSet = tLCInsuredDB.query();
			if (tLCInsuredSet == null || tLCInsuredSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalCheckBL";
				tError.functionName = "getProposalInfo";
				tError.errorMessage = "被保人信息与险种下的被保人信息不一致，请重新进入险种界面进行修改或删除险种后重新添加！";
				this.mErrors.addOneError(tError);
				return false;

			}
			// 每个受益人的被保人是否属于被保人表
			LCBnfDB tLCBnfDB = new LCBnfDB();
			tLCBnfDB.setPolNo(mLCPolSet.get(j + 1).getPolNo());
			this.mLCBnfSet = tLCBnfDB.query();
			for (int i = 0; i < mLCBnfSet.size(); i++) {
				tLCInsuredDB.setPrtNo(mLCPolSet.get(j + 1).getPrtNo());
				tLCInsuredDB.setInsuredNo(mLCBnfSet.get(i + 1).getInsuredNo());
				tLCInsuredSet = tLCInsuredDB.query();
				if (tLCInsuredSet == null || tLCInsuredSet.size() == 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "被保人信息与受益人所属被保人的信息不一致，请重新进入险种界面进行修改或删除险种后重新添加！";
					this.mErrors.addOneError(tError);
					return false;

				}
			}
			// 每个连带的被保人是否属于被保人表
			LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
			tLCInsuredRelatedDB.setPolNo(mLCPolSet.get(j + 1).getPolNo());
			this.mLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
			for (int i = 0; i < mLCInsuredRelatedSet.size(); i++) {
				tLCInsuredDB.setPrtNo(mLCPolSet.get(j + 1).getPrtNo());
				tLCInsuredDB.setInsuredNo(mLCInsuredRelatedSet.get(i + 1)
						.getCustomerNo());
				tLCInsuredSet = tLCInsuredDB.query();
				if (tLCInsuredSet == null || tLCInsuredSet.size() == 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalCheckBL";
					tError.functionName = "getProposalInfo";
					tError.errorMessage = "被保人信息与险种下的被保人信息不一致，请重新进入险种界面进行修改或删除险种后重新添加！";
					this.mErrors.addOneError(tError);
					return false;

				}

			}

		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		return true;
	}

	public static void main(String[] args) {
		ProposalCheckBL proposalcheckbl = new ProposalCheckBL();
		GlobalInput aGlobalInput = new GlobalInput();
		aGlobalInput.ManageCom = "86110000";
		aGlobalInput.Operator = "001";
		VData aVData = new VData();
		TransferData aTransferData = new TransferData();
		String PrtNo = "10110000000000";
		aTransferData.setNameAndValue("PrtNo", PrtNo);
		aVData.add(aTransferData);
		aVData.add(aGlobalInput);
		if (!proposalcheckbl.submitData(aVData, "")) {
			logger.debug(proposalcheckbl.mErrors.getError(0).errorMessage);
		} else {
			logger.debug("Success!");
		}

	}
}
