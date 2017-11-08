package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLIssuePolDB;
import com.sinosoft.lis.db.LLUWSpecMasterDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LLUWSpecMasterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLIssuePolSet;
import com.sinosoft.lis.vschema.LLUWSpecMasterSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class LLTakeBackSendNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLTakeBackSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	

	// tongmeng 2007-11-26 add
	// 增加对问题件的状态字段的维护
	private LLIssuePolSet mLLIssuePolSet = new LLIssuePolSet();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mCertifyNo;
	private String mCertifyCode;
	private String mClmNo;
	private String mBatNo;
	private boolean mPatchFlag;
	private String mTakeBackOperator;
	private String mTakeBackMakeDate;
	private String mOldPrtSeq; // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	private String mCodeType;//通知书类型
	private String mOperate = "";
	/** 执行工作流特约活动表任务0000000011 */
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 核保主表 */
	private LLCUWMasterSchema mLLCUWMasterSchema = new LLCUWMasterSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();
	private String mQueModFlag = "0";
	private String mQueModFlag_UnPrint = "0";
	private String mQueModFlag_Agent = "0";
	private String mAddFee_Only = "0";
	private String mState="2";
	private LLUWSpecMasterSet mLLUWSpecMasterSet = new LLUWSpecMasterSet();// 特约通知书

	public LLTakeBackSendNoticeAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData(cOperate))
			return false;

		// 进行业务处理
		if (!dealData())
			return false;
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		if (!prepareTransferData())
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

		// 添加批单核保主表数据
		if (mLLCUWMasterSchema != null) {
			map.put(mLLCUWMasterSchema, "UPDATE");
		}

		// 添加体检通知书自动发放表数据
		if (mLZSysCertifySet != null && mLZSysCertifySet.size() > 0) {
			map.put(mLZSysCertifySet, "UPDATE");
		}
		//工作流结束后会自动删除该节点并备份，不必重复删除备份
//		if (this.mQueModFlag.equals("0")) {
//			// 不做扭转,直接删除工作流结点
//			map.put(this.mLBMissionSetBak, "INSERT");
//			map.put(this.mLWMissionSetDEL, "DELETE");
//		}
		if (this.mLLIssuePolSet != null && this.mLLIssuePolSet.size() > 0) {
			map.put(mLLIssuePolSet, "UPDATE");
		}
		map.put(this.mLLUWSpecMasterSet, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData(String cOperate) {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		// tongmeng 2007-11-19 modify
		// 去掉此处注释,目前核保通知书分为两种,打印类和非打印类,所以去除code条件
		if (mCodeType.equals("LP90")) {
			tLOPRTManagerDB.setCode(PrintManagerBL.CODE_LPHB);
		} else if (mCodeType.equals("TB90")) {
			tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UW_UnPrint);
		}
		// tongmeng 2007-11-23 add
		// 支持业务员通知书回收
		else if (mCodeType.equals("14")) {
			tLOPRTManagerDB.setCode(PrintManagerBL.CODE_AGEN_QUEST);// 业务员问题件
		}

		// tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UW); //
		tLOPRTManagerDB.setPrtSeq(mCertifyNo);
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStateFlag("1");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError.buildErr(this, "查询打印管理表信息出错!") ;
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "在打印队列中没有处于已打印待回收状态的核保通知书!") ;
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
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tStr);
			sqlbv1.put("mOldPrtSeq", mOldPrtSeq);
			LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
					.executeQuery(sqlbv1);
			;
			if (tempLOPRTManagerSet.size() == 1) {
				// @@错误处理
				CError.buildErr(this, "查询在打印队列中没有该补打核保通知书的原通知书记录信息出错!") ;
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
		this.mClmNo=mLOPRTManagerSchema.getStandbyFlag4();
		this.mBatNo=mLOPRTManagerSchema.getStandbyFlag5();
		// 录入发送的业务员通知书没有核保主表信息
		LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
		tLLCUWMasterDB.setContNo(mContNo);
		tLLCUWMasterDB.setCaseNo(mClmNo);
		tLLCUWMasterDB.setBatNo(mBatNo);
		if (!tLLCUWMasterDB.getInfo()) {
			mLLCUWMasterSchema = null;
		} else {
			mLLCUWMasterSchema.setSchema(tLLCUWMasterDB);
		}
		// 查询系统单证回收队列表
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			if (mLOPRTManagerSet.get(i).getStateFlag() != null
					&& mLOPRTManagerSet.get(i).getStateFlag().trim()
							.equals("1")) {
				LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
				LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
				if ("LP90".equals(mCodeType))  //TODO 
					tLZSysCertifyDB.setCertifyCode("4490"); // 核保通知书标识
				else
					tLZSysCertifyDB.setCertifyCode("9996"); // 业务员通知书标识
				tLZSysCertifyDB.setCertifyNo(mLOPRTManagerSet.get(i)
						.getPrtSeq());
				tLZSysCertifySet = tLZSysCertifyDB.query();
				if (tLZSysCertifySet == null || tLZSysCertifySet.size() != 1) {
					// @@错误处理
					CError.buildErr(this, "回收核保通知书时,LZSysCertifySchema表信息查询失败!") ;
					return false;
				}
				mLZSysCertifySet.add(tLZSysCertifySet.get(1));
			}
		}
		//
		String tSQL = "select * from llissuepol where contno='" + "?contno?"
				+ "' and prtseq='" + "?prtseq?" + "' and needprint = 'Y'";
		if (mLOPRTManagerSchema.getCode().equals("LP90")) {
			// 打印类核保通知书
			tSQL = tSQL + " and standbyflag2='Y' ";
		} else if (mLOPRTManagerSchema.getCode().equals("TB90")) {
			// 打印类核保通知书
			tSQL = tSQL + " and standbyflag2='N' ";
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("contno", this.mContNo);
		sqlbv2.put("prtseq", mLOPRTManagerSchema.getOldPrtSeq());
		LLIssuePolDB tLLIssuePolDB = new LLIssuePolDB();
		// LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		// tLCIssuePolSet =
		this.mLLIssuePolSet.add(tLLIssuePolDB.executeQuery(sqlbv2));
		// tongmeng 2007-12-28 add
		if (mLOPRTManagerSchema.getCode().equals("LP90")) {
			String tSQL_spec = "select * from lluwspecmaster where contno='"
					+ "?contno?" + "' and prtseq='"
					+ "?prtseq?" + "' and clmno='"
					+ "?mClmNo?"+"' and batno='"
					+ "?mBatNo?"+"'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL_spec);
			sqlbv3.put("contno", this.mContNo);
			sqlbv3.put("prtseq", mLOPRTManagerSchema.getOldPrtSeq());
			sqlbv3.put("mClmNo", mClmNo);
			sqlbv3.put("mBatNo", mBatNo);
			LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
			LLUWSpecMasterSet tLLUWSpecMasterSet = new LLUWSpecMasterSet();
			tLLUWSpecMasterSet = tLLUWSpecMasterDB.executeQuery(sqlbv3);
			for (int i = 1; i <= tLLUWSpecMasterSet.size(); i++) {
				LLUWSpecMasterSchema tempLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
				tempLLUWSpecMasterSchema.setSchema(tLLUWSpecMasterSet.get(i));
				this.mLLUWSpecMasterSet.add(tempLLUWSpecMasterSchema);
			}
		}
		// tongmeng 2007-11-28 add 
		// --2008-12-8 ln modify 如果只有加费、只有特约、只有承保计划变更则工作流扭转到自核（还要判断同时没有体检和生调）
		// 判断是否只有加费,如果只有加费的话,返回到自动核保
		boolean tSpecFlag = false;
		boolean tAddFeeFlag = false;
		boolean tChangePolFlag = false;
		boolean tIssuePol = true;	
		
		//2008-12-9 ln add--没有任何已发送问题件
		if (mLOPRTManagerSchema.getCode().equals("LP90")) {      
		//是否有问题件
			if (mLLIssuePolSet.size() <= 0) 
				tIssuePol = false;		

//		// 判断是否有特约承保
//			String tSQL_Spec = "select count(*) from lpcspec where contno='"
//					+ this.mContNo + "' and prtseq='"
//					+ mLOPRTManagerSchema.getOldPrtSeq() + "' and edorno='"
//					+ mEdorNo+"' and EdorType='"
//					+ mEdorType+"'";
//			ExeSQL tExeSQL = new ExeSQL();
//			String tResult = tExeSQL.getOneValue(tSQL_Spec);
//			if (tResult == null || tResult.equals("")) {
//				CError.buildErr(this, "查询特约信息出错!") ;
//				return false;
//			} else if (Integer.parseInt(tResult) > 0) {
//				tSpecFlag = true ;
//			}
//		
//		//判断是否有加费
//			String tSQL_Add = "select count(*) from lpprem where contno='"
//						+ this.mContNo + "' and payplancode like '000000%%' ";
//			tExeSQL = new ExeSQL();
//			tResult = tExeSQL.getOneValue(tSQL_Add);
//				if (tResult == null || tResult.equals("")) {
//					CError.buildErr(this, "查询加费信息出错!") ;
//					return false;
//				} else if (Integer.parseInt(tResult) > 0) {
//					tAddFeeFlag =false;					
//				}
//		//判断是否有承保计划变更
//			String tSQL_ChangePol = "select count(*) from LCUWMaster where contno='"
//						+ mContNo + "' and ChangePolFlag='1' ";
//					tResult = tExeSQL.getOneValue(tSQL_ChangePol);
//					if (tResult == null || tResult.equals("")) {
//						CError.buildErr(this, "查询承保计划变更信息出错!") ;
//						return false;
//					} else if (Integer.parseInt(tResult) > 0) {
//						tChangePolFlag = false;						
//					}					
//			
//			//判断工作流扭转方向 
//			if( tIssuePol == false &&(tSpecFlag = true|| tAddFeeFlag == true || tChangePolFlag == true ))
//				this.mAddFee_Only = "1";//扭转到自核条件一成立
			
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
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!") ;
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operate失败!") ;
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!") ;
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据失败!") ;
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!") ;
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中MissionID失败!") ;
			return false;
		}

		// 获得当前工作任务的子任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!") ;
			return false;
		}

		// 获得业务处理数据
		mCertifyNo = (String) mTransferData.getValueByName("CertifyNo");
		if (mCertifyNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中CertifyNo失败!") ;
			return false;
		}

		// 获得业务处理数据
		mCertifyCode = (String) mTransferData.getValueByName("CertifyCode");
		if (mCertifyCode == null || mCertifyCode.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中CertifyCode失败!") ;
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
		// tError.moduleName = "PrintTakeSendNoticeAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中LZSysCertifySchema失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		mTakeBackOperator = (String) mTransferData
				.getValueByName("TakeBackOperator");
		if (mTakeBackOperator == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中TakeBackOperator失败!") ;
			return false;
		}

		mTakeBackMakeDate = (String) mTransferData
				.getValueByName("TakeBackMakeDate");
		if (mTakeBackMakeDate == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中TakeBackMakeDat失败!") ;
			return false;
		}
		mCodeType=(String)mTransferData.getValueByName("CodeType");
		if(mCodeType==null){
			CError.buildErr(this, "前台传输业务数据中CodeType失败!") ;
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保生调信息
		if (prepareSendNotice() == false)
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
	private boolean prepareSendNotice() {

		// //准备核保主表信息
		if (mLLCUWMasterSchema != null) {
			mLLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			mLLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			mLLCUWMasterSchema.setPrintFlag("3"); // 回收核保通知书
			mLLCUWMasterSchema.setSpecFlag("2");
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
		// tongmeng 2007-11-26 add
		// 增加对问题件状态字段的维护
		for (int i = 1; i <= this.mLLIssuePolSet.size(); i++) {
			mLLIssuePolSet.get(i).setState("2");// 回收完毕
			mLLIssuePolSet.get(i).setReplyMan(mGlobalInput.Operator);//问题件回收完毕
			mLLIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLLIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		// tongmeng 2007-12-28 add
		// 增加对特约的维护
		for (int i = 1; i <= this.mLLUWSpecMasterSet.size(); i++) {
			mLLUWSpecMasterSet.get(i).setPrtFlag("2");// 回收完毕
			mLLUWSpecMasterSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLLUWSpecMasterSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		
		return true;
	}


	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("QueModFlag", mQueModFlag);
		mTransferData.setNameAndValue("QueModFlag_UnPrint",
				this.mQueModFlag_UnPrint);
		mTransferData.setNameAndValue("QueModFlag_Agent",
				this.mQueModFlag_Agent);
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		mTransferData.setNameAndValue("PrtSeq", mCertifyNo);
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		// tongmeng 2007-11-28
		mTransferData.setNameAndValue("AddFee_Only", this.mAddFee_Only);
		mTransferData.setNameAndValue("ClmNo", this.mClmNo);
		mTransferData.setNameAndValue("BatNo", this.mBatNo);
		// tongmeng 2007-11-22 modify
		// 为问题件增加管理机构字段
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError.buildErr(this, "查询代理人：" + mLCContSchema.getAgentCode()
					+ "基本信息失败!") ;
			return false;
		}
		mTransferData.setNameAndValue("AgentGroup", tLAAgentDB.getBranchCode());
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());
		mTransferData.setNameAndValue("State",this.mState);
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
