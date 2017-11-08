package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPPENoticeDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPPENoticeSchema;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPPENoticeSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class BQTakeBackAutoHealthAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(BQTakeBackAutoHealthAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mCertifyNo;
	private String mCertifyCode;
	private boolean mPatchFlag;
	private String mTakeBackOperator;
	private String mTakeBackMakeDate;
	private String mOldPrtSeq; // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉

	/** 执行续保工作流特约活动表任务0000000011 */
	/** 合同保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	// /** 续保核保主表 */
	// private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉

	/** 体检主表 */
	private LPPENoticeSchema mLPPENoticeSchema = new LPPENoticeSchema();
	private LPPENoticeSet mLPPENoticeSet = new LPPENoticeSet();

	/** 单证发放表 */
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();

	public BQTakeBackAutoHealthAfterInitService() {
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

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加核保通知书打印管理表数据
		if (mLOPRTManagerSet != null && mLOPRTManagerSet.size() > 0) {
			map.put(mLOPRTManagerSet, "UPDATE");
		}

		// //添加续保批单核保主表数据
		// if (mLCCUWMasterSchema != null)
		// {
		// map.put(mLCCUWMasterSchema, "UPDATE");
		// }

		// 添加体检表数据
		if (mLPPENoticeSet != null && mLPPENoticeSet.size() > 0) {
			map.put(mLPPENoticeSet, "UPDATE");
		}

		// 添加续保体检通知书自动发放表数据
		if (mLZSysCertifySet != null && mLZSysCertifySet.size() > 0) {
			map.put(mLZSysCertifySet, "UPDATE");
		}

		mResult.add(map);
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
			CError.buildErr(this, "保单" + mContNo + "信息查询失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// //校验续保批单核保主表
		// //校验保单信息
		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setContNo(mContNo);
		// if (!tLCCUWMasterDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "PRnewPrintTakeBackAutoHealthAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "续保批单核保主表信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		String StrSQL = "select * from LOPRTManager where prtseq = '"
				+ "?mCertifyNo?" + "' and otherno = '" + "?mContNo?"
				+ "' and othernotype = '" + "?othernotype?"
				+ "' and code in ('23') and stateflag = '1'";

		// tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PE); //
		// tLOPRTManagerDB.setPrtSeq(mCertifyNo);
		// tLOPRTManagerDB.setOtherNo(mContNo);
		// tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); //合同号
		// tLOPRTManagerDB.setStateFlag("1");
		//
		// LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(StrSQL);
		sqlbv1.put("mCertifyNo",mCertifyNo);
		sqlbv1.put("mContNo",mContNo);
		sqlbv1.put("othernotype",PrintManagerBL.ONT_CONT);
		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(sqlbv1);
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError.buildErr(this, "查询打印管理表信息出错!");
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "在打印队列中没有处于已打印待回收状态的体检通知书!");
			return false;
		}

		mLOPRTManagerSchema = tLOPRTManagerSet.get(1);
		if (mLOPRTManagerSchema.getPatchFlag() == null)
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("0"))
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("1"))
			mPatchFlag = true;

		// 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
		if (mPatchFlag == true) {
			LOPRTManagerDB tempLOPRTManagerDB = new LOPRTManagerDB();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();
			String tStr = "select * from LOPRTManager where (PrtSeq = '"
					+ "?mOldPrtSeq?" + "'" + "or OldPrtSeq = '" + "?mOldPrtSeq?" + "')";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tStr);
			sqlbv2.put("mOldPrtSeq",mOldPrtSeq);
			LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
					.executeQuery(sqlbv2);
			;
			if (tempLOPRTManagerSet.size() == 1) {
				// @@错误处理
				CError.buildErr(this, "查询在打印队列中没有该补打体检通知书的原通知书记录信息出错!");
				return false;
			}

			for (int i = 1; i <= tempLOPRTManagerSet.size(); i++) {
				mLOPRTManagerSet.add(tempLOPRTManagerSet.get(i));
			}
		} else {
			LOPRTManagerDB tempLOPRTManagerDB = new LOPRTManagerDB();
			mOldPrtSeq = mLOPRTManagerSchema.getPrtSeq();
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tempLOPRTManagerDB.setOldPrtSeq(mOldPrtSeq);
				LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
						.query();
				if (tempLOPRTManagerSet != null
						&& tempLOPRTManagerSet.size() > 0) {
					for (int i = 1; i <= tempLOPRTManagerSet.size(); i++) {
						mLOPRTManagerSet.add(tempLOPRTManagerSet.get(i));
					}
				}
			}
		}

		// 查询系统单证回收队列表
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			if (mLOPRTManagerSet.get(i).getStateFlag() != null
					&& mLOPRTManagerSet.get(i).getStateFlag().trim()
							.equals("1")) {
				LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
				LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
				if (mLOPRTManagerSet.get(i).getCode().trim().equals("23")) {
					tLZSysCertifyDB.setCertifyCode("2000"); // 保全体检通知书标识
				} else if (mLOPRTManagerSet.get(i).getCode().trim().equals(
						"LP03")) {
					tLZSysCertifyDB.setCertifyCode("4403"); // 理赔体检通知书标识
				} else {
					tLZSysCertifyDB.setCertifyCode("8888"); // 体检通知书标识
				}
				tLZSysCertifyDB.setCertifyNo(mLOPRTManagerSet.get(i)
						.getPrtSeq());
				tLZSysCertifySet = tLZSysCertifyDB.query();
				if (tLZSysCertifySet == null || tLZSysCertifySet.size() != 1) {
					// @@错误处理
					CError.buildErr(this, "回收体检通知书时,LZSysCertifySchema表信息查询失败!");
					return false;
				}
				mLZSysCertifySet.add(tLZSysCertifySet.get(1));
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
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得业务处理数据
		mCertifyNo = (String) mTransferData.getValueByName("CertifyNo");
		if (mCertifyNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得业务处理数据
		mCertifyCode = (String) mTransferData.getValueByName("CertifyCode");
		if (mCertifyCode == null || mCertifyCode.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		// //获得业务处理数据
		// LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		// tLZSysCertifySchema = (LZSysCertifySchema)
		// mTransferData.getValueByName(
		// "LZSysCertifySchema");
		// if (tLZSysCertifySchema == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "UWPrintTakeBackAutoHealthAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中LZSysCertifySchema失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		mTakeBackOperator = (String) mTransferData
				.getValueByName("TakeBackOperator");
		if (mTakeBackOperator == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中TakeBackOperator失败!");
			return false;
		}

		mTakeBackMakeDate = (String) mTransferData
				.getValueByName("TakeBackMakeDate");
		if (mTakeBackMakeDate == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中mTakeBackMakeDate失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 更新体检通知书
		if (prepareAutoHealth() == false)
			return false;

		// 打印队列
		if (preparePrint() == false)
			return false;

		// 发放系统单证打印队列
		if (prepareAutoSysCertSendOut() == false)
			return false;

		return true;

	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAutoHealth() {

		// //准备核保主表信息
		// mLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		// mLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		// mLCCUWMasterSchema.setHealthFlag("3"); //发送核保通知书

		// 更新体检通知书
		LPPENoticeDB tLPPENoticeDB = new LPPENoticeDB();
		// tLCPENoticeDB.setContNo(mContNo);
		tLPPENoticeDB.setPrtSeq(mOldPrtSeq); // 单证管理表中主键CertifyNo为打印管理表中的打印流水号
		// tLCPENoticeDB.setPrintFlag("2"); //体检通知书已回收
		// tLCPENoticeDB.setCustomerNo(mLOPRTManagerSchema.getStandbyFlag1());
		// //备用字段1存放客户号
		LPPENoticeSet tLPPENoticeSet = tLPPENoticeDB.query();
		if (tLPPENoticeSet == null || tLPPENoticeSet.size() <= 0) {
			mErrors.copyAllErrors(tLPPENoticeDB.mErrors);
			return false;
		}
		for (int i = 1; i <= tLPPENoticeSet.size(); i++) {
			mLPPENoticeSchema = tLPPENoticeSet.get(i);
			mLPPENoticeSchema.setPrintFlag("2"); // 体检通知书已回收
			mLPPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
			mLPPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
			mLPPENoticeSet.add(mLPPENoticeSchema);
		}

		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		// 准备打印管理表数据
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			mLOPRTManagerSet.get(i).setStateFlag("2");
		}
		return true;
	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAutoSysCertSendOut() {
		// 准备单证回收管理表数据
		for (int i = 1; i <= mLZSysCertifySet.size(); i++) {
			mLZSysCertifySet.get(i)
					.setTakeBackMakeDate(PubFun.getCurrentDate());
			mLZSysCertifySet.get(i)
					.setTakeBackMakeTime(PubFun.getCurrentTime());
			mLZSysCertifySet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLZSysCertifySet.get(i).setModifyTime(PubFun.getCurrentTime());
			mLZSysCertifySet.get(i).setTakeBackOperator(mTakeBackOperator);
			mLZSysCertifySet.get(i).setStateFlag("1");
		}
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ContNo", mLPPENoticeSchema.getContNo());
		mTransferData.setNameAndValue("PrtNo", mLPPENoticeSchema.getContNo());
		mTransferData.removeByName("PrtSeq");
		mTransferData.setNameAndValue("PrtSeq", mLPPENoticeSchema.getPrtSeq());
		mTransferData.setNameAndValue("CustomerNo", mLPPENoticeSchema
				.getCustomerNo());
		mTransferData.setNameAndValue("Operator", mOperater);
		mTransferData.setNameAndValue("MakeDate", PubFun.getCurrentDate());

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
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
