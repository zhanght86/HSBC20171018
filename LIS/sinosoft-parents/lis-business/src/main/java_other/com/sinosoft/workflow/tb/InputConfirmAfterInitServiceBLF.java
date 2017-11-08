package com.sinosoft.workflow.tb;

import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.ProposalCheckBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;
import com.sinosoft.workflowengine.WorkFlowService;

public class InputConfirmAfterInitServiceBLF  {
	private static Logger logger = Logger
			.getLogger(InputConfirmAfterInitServiceBLF.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	// private String mApproveFlag;
	private String mMissionID;
	private String mIssueFlag; // 是否有问题件flag
	// private String mOutIssueFlag; //是否是外部问题件flag

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCPolSet mLCPolSet = new LCPolSet();

	/*
	 * private String mNoticeType; private String mCustomerNoticeType; private
	 * String mPrtSeq1; private String mPrtSeq2; private LOPRTManagerSet
	 * mLOPRTManagerSet;
	 */
	private MMap mMap;
	private MMap updatemap = new MMap();

	/*
	 * private String mSerialNo1; private String mSerialNo2; private
	 * LCIssuePolSchema mLCIssuePolSchema;
	 */

	public InputConfirmAfterInitServiceBLF() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验业务数据
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;
		// tongmeng 2008-07-03 modify
		// 新系统支持多主险,多被保人,以下原有修改SQL是错误的!
		// 修改保单容易出现错误的数据

		// if(!updateContInfo())
		// return false;
		// 为工作流下一节点属性字段准备数据

		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		// mResult.clear();
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.add(this.mMap);
		map.put(mLCContSchema, "UPDATE");
		// 在点击录入完毕后统一更新lccont中的prem amnt mult ，令其与lcpol表相符
		// 录入险种时，保单表中已对多币种情况进行转换，此处不再汇总lcpol的值
		String tUpdateContSql = "update lccont a"
				+ " set payintv = (select max(payintv) from lcpol where contno=a.contno and mainpolno=polno ),"
				// +" prem = (select sum(prem) from lcpol where contno = a.contno),"
				// +" amnt = (select sum(amnt) from lcpol where contno = a.contno),"
				+ " mult = (select sum(mult) from lcpol where contno = a.contno)"
				+ " where a.contno = '" + "?contno?" + "'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tUpdateContSql);
		sqlbv1.put("contno", mContNo);
		map.put(sqlbv1, "UPDATE");
		map.add(updatemap);
		mResult.add(map);
		return true;
	}

	/***************************************************************************
	 * 修改保单信息中容易出现错误的数据(2006-08-04添加)
	 **************************************************************************/
	private boolean updateContInfo() {
		// 修改LCPol表MainPolno(主险种保单号)字段为该单主险的PolNo(险种保单号)
		/*
		 * String updatemainpolnosql = "update lcpol a set a.mainpolno=( " +
		 * " select b.polno from lcpol b where b.contno=a.contno and exists (" +
		 * " select 'X' from lmriskapp where lmriskapp.riskcode=b.riskcode and lmriskapp.subriskflag='M'"
		 * + "  ) )" + " where a.contno='" + mLCContSchema.getContNo() + "'"; //
		 * 修改LCCont表的被保人信息为LCinsured表里的第一被保人的信息 String updateinsuredsql =
		 * "update lccont set (insuredno,insuredname,insuredsex,insuredbirthday,insuredidtype,insuredidno)"
		 * +
		 * "=(select insuredno,name,sex,birthday,idtype,idno from lcinsured where lcinsured.contno=lccont.contno and sequenceno='1')"
		 * + " where contno='" + mLCContSchema.getContNo() + "'";
		 * updatemap.put(updatemainpolnosql, "UPDATE");
		 * updatemap.put(updateinsuredsql, "UPDATE");
		 */
		String tUpdateSQL = " update lccont a set "
				+ " payintv = (select max(payintv) from lcpol where contno=a.contno and mainpolno=polno ), "
				+ " mult = (select sum(mult) from lcpol where contno=a.contno), "
				+ " prem = (select sum(prem) from lcpol where contno=a.contno), "
				+ " amnt = (select sum(amnt) from lcpol where contno=a.contno), "
				+ " sumprem = (select sum(sumprem) from lcpol where contno=a.contno) "
				+ " where a.contno='" + "?contno1?" + "' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tUpdateSQL);
		sqlbv2.put("contno1", mLCContSchema.getContNo());
		updatemap.put(sqlbv2, "UPDATE");
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);
		mLCContSchema.setInputOperator(mGlobalInput.Operator);
		mLCContSchema.setInputDate(PubFun.getCurrentDate());
		mLCContSchema.setInputTime(PubFun.getCurrentTime());

		// 校验合同单下是否有被保人
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		mLCInsuredSet = tLCInsuredDB.query();
		if (mLCInsuredSet == null || mLCInsuredSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单号" + mLCContSchema.getContNo()
					+ "下没有被保人信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 校验合同单下是否有险种单
		for (int i = 0; i < mLCInsuredSet.size(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(mContNo);
			tLCPolDB.setInsuredNo(mLCInsuredSet.get(i + 1).getInsuredNo());
			mLCPolSet = tLCPolDB.query();

			// 联生险
			LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
			tLCInsuredRelatedDB.setCustomerNo(mLCInsuredSet.get(i + 1)
					.getInsuredNo());
			LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
			tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();

			if ((mLCPolSet == null || mLCPolSet.size() == 0)
					&& (tLCInsuredRelatedSet == null || tLCInsuredRelatedSet
							.size() == 0)) {
				CError tError = new CError();
				tError.moduleName = "InputConfirmAfterInitService";
				tError.functionName = "checkData";
				tError.errorMessage = "险种保单" + mLCContSchema.getContNo()
						+ "信息查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		// ------------------------------------------------------Beg
		// ----------修改人：chenrong
		// ----------修改日期：2006-2-24
		// 校验险种信息是否正确
		VData tInputData = new VData();
		tInputData.add(mLCContSchema);
		CheckRiskField tCheckRiskField = new CheckRiskField();
		if (!tCheckRiskField.CheckTBField(tInputData, "TBALLINSERT")) {
			this.mErrors.copyAllErrors(tCheckRiskField.mErrors);
			return false;
		}
		// -----------------------------------------------------End
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
		// mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 
	 */
	private boolean dealData() {
		// /////////////////////////////////////////////
		// 录入完毕时进行保单信息校验
		ProposalCheckBL proposalcheckbl = new ProposalCheckBL();
		GlobalInput aGlobalInput = new GlobalInput();
		aGlobalInput.ManageCom = mLCContSchema.getManageCom();
		aGlobalInput.Operator = mLCContSchema.getOperator();
		VData bVData = new VData();
		TransferData aTransferData = new TransferData();
		String PrtNo = mLCContSchema.getPrtNo();
		aTransferData.setNameAndValue("PrtNo", PrtNo);
		bVData.add(aTransferData);
		bVData.add(aGlobalInput);
		if (!proposalcheckbl.submitData(bVData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(proposalcheckbl.mErrors);
			return false;
		}
		// ////////////////////////////////////////
		// liuning 2007-12-25 modify
		// 有无问题件均 mIssueFlag =0,不进行打印
		// ////////////////////////////////////////////
		this.mIssueFlag = "0";
		/**
		 * LCIssuePolDB aLCIssuePolDB = new LCIssuePolDB(); //
		 * aLCIssuePolDB.setContNo(mContNo); String aSQL = "select * from
		 * LCIssuePol where contno='" + mContNo +
		 * "' and replyman is null and replyresult is null";
		 * logger.debug("111111111111aSQL===" + aSQL); LCIssuePolSet
		 * aLCIssuePolSet = aLCIssuePolDB.executeQuery(aSQL); //有问题件 if
		 * (aLCIssuePolSet.size() > 0) { this.mIssueFlag = "1"; } else {
		 * this.mIssueFlag = "0"; }
		 * 
		 * 
		 * this.mNoticeType = "0"; this.mCustomerNoticeType = "0";
		 * 
		 * //有外部问题件 //tongmeng 2007-10-18 modify //BackObjType='2' 发送给客户
		 * //BackObjType='3' 发送给业务员 //对于这两种问题件都进行通知书打印。 //
		 * aLCIssuePolDB.setBackObjType("2"); //问题件类型：1-内部 2-外部 //LCIssuePolSet
		 * bLCIssuePolSet = aLCIssuePolDB.query(); //aSQL = aSQL + " and
		 * BackObjType='2'"; aSQL = aSQL + " and BackObjType in ('2','3') ";
		 * LCIssuePolSet bLCIssuePolSet = aLCIssuePolDB.executeQuery(aSQL);
		 * logger.debug("22222222222aSQL===" + aSQL); if (bLCIssuePolSet.size()
		 * > 0) { this.mOutIssueFlag = "1"; //如果是外部问题件，发送外部问题件通知书 //添加相关业务类
		 * //为调用打印新契约通知书准备数据 if (!prepareLCCont()) { CError tError = new
		 * CError(); tError.moduleName = "PrintNBNoticeAfterInitService";
		 * tError.functionName = "dealData"; tError.errorMessage =
		 * "不存在符合该合同号的合同信息!"; this.mErrors.addOneError(tError); return false; }
		 * UWIssuePolPrintBL aUMIssuePolPrintBL = new UWIssuePolPrintBL(); VData
		 * aVData = new VData(); aVData.add(this.mGlobalInput);
		 * aVData.add(this.mLCContSchema); boolean tResult =
		 * aUMIssuePolPrintBL.submitData(aVData, ""); if (tResult) { mMap =
		 * (MMap) aUMIssuePolPrintBL.getResult(). getObjectByObjectName("MMap",
		 * 0); mLOPRTManagerSet = (LOPRTManagerSet) aUMIssuePolPrintBL.
		 * getResult(). getObjectByObjectName( "LOPRTManagerSet", 0);
		 * 
		 * mLCIssuePolSchema = (LCIssuePolSchema) aUMIssuePolPrintBL.
		 * getResult(). getObjectByObjectName( "LCIssuePolSchema", 0);
		 * //判断通知书的种类 //如果mLOPRTManagerSet中有两条记录则既打印契约变更通知书又打印客户变更通知书 //
		 * if(this.mLOPRTManagerSet.size()==2){ // this.mNoticeType="1"; //
		 * this.mCustomerNoticeType="1"; // } // else if
		 * (this.mLOPRTManagerSet.get(1).getCode().equals("14")){ //
		 * this.mNoticeType="1"; // this.mCustomerNoticeType="0"; // } // else
		 * if(this.mLOPRTManagerSet.get(1).getCode().equals("17")){ //
		 * this.mNoticeType="0"; // this.mCustomerNoticeType="1"; // }
		 * this.mNoticeType = "0"; this.mCustomerNoticeType = "0"; //将打印序列号赋值
		 * logger
		 * .debug("----mLOPRTManagerSet.size()==="+mLOPRTManagerSet.size()); for
		 * (int i = 0; i < this.mLOPRTManagerSet.size(); i++) { if
		 * (this.mLOPRTManagerSet.get(i+1).getCode().equals("14")) {
		 * this.mPrtSeq1 = this.mLOPRTManagerSet.get(i+1).getPrtSeq();
		 * //一般通知书打印序列号 this.mNoticeType = "1"; // this.mSerialNo1 =
		 * this.mLCIssuePolSchema.getSerialNo(); } else if
		 * (this.mLOPRTManagerSet.get(i+1).getCode().equals( "17")) {
		 * this.mPrtSeq2 = this.mLOPRTManagerSet.get(i+1).getPrtSeq();
		 * //客户合并打印序列号 this.mCustomerNoticeType = "1"; // this.mSerialNo2 =
		 * this.mLCIssuePolSchema.getSerialNo(); } } // if
		 * (mLCIssuePolSchema.getIssueType().equals("22")) { // mNoticeType =
		 * "2"; // } else { // mNoticeType = "1"; // } } else {
		 * this.mErrors.copyAllErrors(aUMIssuePolPrintBL.mErrors); } } else {
		 * this.mOutIssueFlag = "0"; } logger.debug("==this.mIssueFlag==" +
		 * this.mIssueFlag); logger.debug("==this.mOutIssueFlag==" +
		 * this.mOutIssueFlag); logger.debug("==this.mNoticeType==" +
		 * this.mNoticeType); logger.debug("==this.mCustomerNoticeType==" +
		 * this.mCustomerNoticeType);
		 */
		logger.debug("==this.mIssueFlag==" + this.mIssueFlag);
		logger.debug("判断结束！");
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("Operator", mLCContSchema.getOperator());
		mTransferData.setNameAndValue("MakeDate", mLCContSchema.getMakeDate());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("AgentGroup",
				mLCContSchema.getAgentGroup());
		// 加入问题件相关变量 hanlin 20050419
		// tongmeng 2008-08-05 modify
		// 放在BeforeEnd中统一处理
		 mTransferData.setNameAndValue("IssueFlag", this.mIssueFlag);
		// mTransferData.setNameAndValue("OutIssueFlag", this.mOutIssueFlag);
		// mTransferData.setNameAndValue("NoticeType", this.mNoticeType);
		// mTransferData.setNameAndValue("CustomerNoticeType",
		// this.mCustomerNoticeType);
		mTransferData.setNameAndValue("ApproveDate", PubFun.getCurrentDate());
		/*
		 * if (this.mNoticeType.equals("1")) {
		 * mTransferData.setNameAndValue("PrtSeq1", this.mPrtSeq1); //
		 * mTransferData.setNameAndValue("SerialNo1", this.mSerialNo1);
		 * 
		 * } if (this.mCustomerNoticeType.equals("1")) {
		 * mTransferData.setNameAndValue("PrtSeq2", this.mPrtSeq2); //
		 * mTransferData.setNameAndValue("SerialNo2", this.mSerialNo2); }
		 */
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 通过ContNo查询LCCont表的信息
	 * 
	 * @return boolean
	 * 
	 */
	private boolean prepareLCCont() {
		LCContDB aLCContDB = new LCContDB();
		aLCContDB.setContNo(this.mContNo);
		LCContSet aLCContSet = aLCContDB.query();
		if (aLCContSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "PrintNBNoticeAfterInitService";
			tError.functionName = "prepareLCCont";
			tError.errorMessage = "通过合同号查询LCCont表没有数据!";
			this.mErrors.addOneError(tError);
			return false;

		}
		this.mLCContSchema = aLCContSet.get(1);
		return true;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
