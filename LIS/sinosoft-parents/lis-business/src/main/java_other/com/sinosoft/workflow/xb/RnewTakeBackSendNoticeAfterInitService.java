package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.db.RnewIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.lis.vschema.RnewIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流节点任务:新契约人工核保回收核保通知书
 * </p>
 * <p>
 * Description: 人工核保核保通知书回收AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RnewTakeBackSendNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RnewTakeBackSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 工作流任务节点表 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	private LWMissionDB mLWMissionDB = new LWMissionDB();

	private LBMissionSet mLBMissionSetBak = new LBMissionSet();
	private LWMissionSet mLWMissionSetDEL = new LWMissionSet();
	// tongmeng 2007-11-26 add
	// 增加对问题件的状态字段的维护
	private RnewIssuePolSet mRnewIssuePolSet = new RnewIssuePolSet();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mCertifyNo;
	private String mCertifyCode;
	private boolean mPatchFlag;
	private String mTakeBackOperator;
	private String mTakeBackMakeDate;
	private String mOldPrtSeq; // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉

	private String mOperate = "";
	/** 执行工作流特约活动表任务0000000011 */
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterDB mLCUWMasterDB = new LCUWMasterDB();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();
	private String mQueModFlag = "0";
	private String mQueModFlag_UnPrint = "0";
	private String mQueModFlag_Agent = "0";
	private String mAddFee_Only = "0";
	private String mState="2";
	private LCCSpecSet mLCCSpecSet = new LCCSpecSet();// 特约通知书

	public RnewTakeBackSendNoticeAfterInitService() {
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
		if (mLCUWMasterSet != null) {
			map.put(mLCUWMasterSet, "UPDATE");
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
		if (this.mRnewIssuePolSet != null && this.mRnewIssuePolSet.size() > 0) {
			map.put(mRnewIssuePolSet, "UPDATE");
		}
		map.put(this.mLCCSpecSet, "UPDATE");
		map.put(this.mLWMissionSet, "UPDATE");
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

		// 交验lwmission表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		// tongmeng 2007-11-23 modify
		// 增加回收业务员通知书逻辑
		tLWMissionDB.setActivityID(cOperate);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		if (!tLWMissionDB.getInfo()) {
			CError.buildErr(this, "工作流轨迹表信息查询失败!") ;
			return false;
		}
		//mLWMissionSchema = tLWMissionDB.getSchema();


		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		// tongmeng 2007-11-19 modify
		// 去掉此处注释,目前核保通知书分为两种,打印类和非打印类,所以去除code条件
		if (cOperate.equals("0000007006")) {
			String tTempCode = "";
			String tTemp_SQL = "select missionprop15 from lwmission where missionid='"
					+ "?missionid?"
					+ "' "
					+ " and submissionid = '"
					+ "?submissionid?" + "' and activityid='0000007006' ";
			logger.debug("tTemp_SQL:" + tTemp_SQL);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tTemp_SQL);
			sqlbv1.put("missionid", this.mMissionID);
			sqlbv1.put("submissionid", this.mSubMissionID);
			ExeSQL tExeSQL = new ExeSQL();
			tTempCode = tExeSQL.getOneValue(sqlbv1);
			if (tTempCode == null || tTempCode.equals("")) {
				CError.buildErr(this, "查询打印管理表类型出错!") ;
				return false;
			}
			if (tTempCode.equals("45")) {
				tLOPRTManagerDB.setCode("45");
			} else if (tTempCode.equals("TB90")) {
				tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UW_UnPrint);
			}
			// tongmeng 2007-11-23 add
			// 支持业务员通知书回收
			else if (tTempCode.equals("14")) {
				tLOPRTManagerDB.setCode(PrintManagerBL.CODE_AGEN_QUEST);// 业务员问题件
			}
		}

		else if (cOperate.equals("0000001019")) {
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
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tStr);
			sqlbv2.put("mOldPrtSeq", mOldPrtSeq);
			LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
					.executeQuery(sqlbv2);
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

		// 查询系统单证回收队列表
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			if (mLOPRTManagerSet.get(i).getStateFlag() != null
					&& mLOPRTManagerSet.get(i).getStateFlag().trim()
							.equals("1")) {
				LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
				LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
				if (this.mOperate.equals("0000007006"))
					tLZSysCertifyDB.setCertifyCode("7006"); // 核保通知书标识
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
		String tSQL = "select * from RnewIssuePol where contno='" + "?contno?"
				+ "' and prtseq='" + "?prtseq?" + "' and needprint = 'Y'";
//		if (mLOPRTManagerSchema.getCode().equals("45")) {
//			// 打印类核保通知书
//			tSQL = tSQL + " and standbyflag2='Y' ";
//		} else if (mLOPRTManagerSchema.getCode().equals("TB90")) {
//			// 打印类核保通知书
//			tSQL = tSQL + " and standbyflag2='N' ";
//		}
		logger.debug(tSQL);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL);
		sqlbv3.put("contno", this.mContNo);
		sqlbv3.put("prtseq", mLOPRTManagerSchema.getOldPrtSeq());
		RnewIssuePolDB tRnewIssuePolDB = new RnewIssuePolDB();
		this.mRnewIssuePolSet.add(tRnewIssuePolDB.executeQuery(sqlbv3));
		// tongmeng 2007-12-28 add
		if (mLOPRTManagerSchema.getCode().equals("45")) {
			String tSQL_spec = "select * from lccspec where contno='"
					+ "?contno?" + "' and prtseq='"
					+ "?prtseq?" + "'";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSQL_spec);
			sqlbv9.put("contno", this.mContNo);
			sqlbv9.put("prtseq", mLOPRTManagerSchema.getOldPrtSeq());
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv9);
			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
				this.mLCCSpecSet.add(tempLCCSpecSchema);
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
		if (mLOPRTManagerSchema.getCode().equals("45")) {      
		//是否有问题件
			if (mRnewIssuePolSet.size() <= 0) 
				tIssuePol = false;		

		// 判断是否有特约承保
			String tSQL_Spec = "select count(*) from lccspec where contno='"
					+ "?contno?" + "' and prtseq='"
					+ "?prtseq?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSQL_Spec);
			sqlbv4.put("contno", this.mContNo);
			sqlbv4.put("prtseq", mLOPRTManagerSchema.getOldPrtSeq());
			ExeSQL tExeSQL = new ExeSQL();
			String tResult = tExeSQL.getOneValue(sqlbv4);
			if (tResult == null || tResult.equals("")) {
				CError.buildErr(this, "查询特约信息出错!") ;
				return false;
			} else if (Integer.parseInt(tResult) > 0) {
				tSpecFlag = true ;
			}
		
		//判断是否有加费
			String tSQL_Add = "select count(*) from lcprem where contno='"
						+ "?contno?" + "' and payplancode like '000000%%' ";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL_Add);
			sqlbv5.put("contno", this.mContNo);
			tExeSQL = new ExeSQL();
			tResult = tExeSQL.getOneValue(sqlbv5);
				if (tResult == null || tResult.equals("")) {
					CError.buildErr(this, "查询加费信息出错!") ;
					return false;
				} else if (Integer.parseInt(tResult) > 0) {
					tAddFeeFlag =false;					
				}
		//判断是否有承保计划变更
			String tSQL_ChangePol = "select count(*) from LCUWMaster where contno='"
						+ "?mContNo?" + "' and ChangePolFlag='1' ";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL_ChangePol);
			sqlbv6.put("mContNo", mContNo);
					tResult = tExeSQL.getOneValue(sqlbv6);
					if (tResult == null || tResult.equals("")) {
						CError.buildErr(this, "查询承保计划变更信息出错!") ;
						return false;
					} else if (Integer.parseInt(tResult) > 0) {
						tChangePolFlag = false;						
					}					
			
			//判断工作流扭转方向 
			if( tIssuePol == false &&(tSpecFlag = true|| tAddFeeFlag == true || tChangePolFlag == true ))
				this.mAddFee_Only = "1";//扭转到自核条件一成立
			
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
			CError.buildErr(this, "前台传输业务数据中MissionID失败!") ;
			return false;
		}

		// 获得业务处理数据
		mCertifyCode = (String) mTransferData.getValueByName("CertifyCode");
		if (mCertifyCode == null || mCertifyCode.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中MissionID失败!") ;
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
		// 选择工作流activity
		if (chooseActivity() == false)
			return false;
        //处理工作流信息
//		if(!dealMission())
//			return false;
		return true;
	}
	private boolean dealMission(){
		//给发送通知书任务置上操作员
		LWMissionSchema xLWMissionSchema = new LWMissionSchema();
		LWMissionDB xLWMissionDB = new LWMissionDB();
		LWMissionSet xLWMissionSet = new LWMissionSet();
		xLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		xLWMissionDB.setActivityID("0000007006");
		xLWMissionSet=xLWMissionDB.query();
		if(xLWMissionSet.size()!=1){
//			if(!tLWMissionDB.getInfo()){
			CError.buildErr(this, "查询LWMission失败！");
			return false;
		}
		xLWMissionSchema = xLWMissionSet.get(1);
		xLWMissionSchema.setDefaultOperator(this.mGlobalInput.Operator);
		xLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		xLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		mLWMissionSet.add(xLWMissionSchema);
		return true;
	}
	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareSendNotice() {

		// //准备核保主表信息
		LCPolDB tLCPolDB =new LCPolDB();
		LCPolSet tLCPolSet =new LCPolSet();
		tLCPolDB.setContNo(this.mContNo);
		tLCPolDB.setAppFlag("9");
		tLCPolDB.setRnewFlag("-1");
		tLCPolSet=tLCPolDB.query();
		for(int i=1;i<=tLCPolSet.size();i++)
		{
			mLCUWMasterSchema = new LCUWMasterSchema();
			mLCUWMasterDB = new LCUWMasterDB();
			mLCUWMasterDB.setProposalNo(tLCPolSet.get(i).getProposalNo());
			if(!mLCUWMasterDB.getInfo())
			{
				this.mErrors.copyAllErrors(mLCUWMasterDB.mErrors);
				CError.buildErr(this, "查找险种核保记录表失败!") ;
				return false;
			}
			mLCUWMasterSchema=mLCUWMasterDB.query().get(1);
			mLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			mLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			mLCUWMasterSchema.setPrintFlag("3"); // 回收核保通知书
			//mLCUWMasterSchema.setSpecFlag("2");
			
			mLCUWMasterSet.add(mLCUWMasterSchema);
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
		for (int i = 1; i <= this.mRnewIssuePolSet.size(); i++) {
			mRnewIssuePolSet.get(i).setState("2");// 回收完毕
			mRnewIssuePolSet.get(i).setReplyMan(this.mGlobalInput.Operator);
			mRnewIssuePolSet.get(i).setReplyResult("系统自动回复");
			mRnewIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mRnewIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		// tongmeng 2007-12-28 add
		// 增加对特约的维护
		for (int i = 1; i <= this.mLCCSpecSet.size(); i++) {
			mLCCSpecSet.get(i).setPrtFlag("2");// 回收完毕
			mLCCSpecSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLCCSpecSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		//如果保单在问题件修改岗 查询是否有其他问题件  修改将其missionprop9-保单是否可操作
//		String tPrtNo = mRnewIssuePolSet.get(1).getContNo();
//		mLWMissionDB.setActivityID("0000001002");
//		mLWMissionDB.setMissionProp1(tPrtNo);
//		mLWMissionSet = mLWMissionDB.query();
//		if(mLWMissionSet.size()>0)
//		{
//			//问题件修改岗
//			String tState = mLWMissionSet.get(1).getMissionProp9();
//			logger.debug("当前的问题件修改岗的状态是 ："+tState);
//			if(tState.equals("1")){
//				//查询是否含有其他问题件 如果没有 将State-missionprop9置为‘0’
//				String strSql = "select count(*) from RnewIssuePol where ";
//			}
//		}else{
//			CError.buildErr(this, "工作流错误,问题件修改岗没有创建节点！");
//		}
		
		return true;
	}

	/**
	 * 选择工作流流转
	 * 
	 * @return
	 */
	private boolean chooseActivity() {

		/*
		 * String tStr = "Select * from LWMission where MissionID = '" +
		 * mMissionID + "'" + "and ActivityID = '0000007006'" + "and
		 * SubMissionID <> '" + mSubMissionID + "'" + "and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" + " union " + "Select *
		 * from LWMission where MissionID = '" + mMissionID + "'" + " and
		 * ActivityID = '0000001115'" + " and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" + " union " + "Select *
		 * from LWMission where MissionID = '" + mMissionID + "'" + " and
		 * ActivityID = '0000001107'" + " and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" + " union " + "Select *
		 * from LWMission where MissionID = '" + mMissionID + "'" + " and
		 * ActivityID = '0000001017'" + " and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" + " union " + "Select *
		 * from LWMission where MissionID = '" + mMissionID + "'" + " and
		 * ActivityID = '0000001018'" + " and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" + " union " + "Select *
		 * from LWMission where MissionID = '" + mMissionID + "'" + " and
		 * ActivityID = '0000001019'" + " and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" + " union " + "Select *
		 * from LWMission where MissionID = '" + mMissionID + "'" + " and
		 * ActivityID = '0000001020'" + " and MissionProp14 = '" +
		 * mLWMissionSchema.getMissionProp14() + "'" ;
		 * 
		 * logger.debug("tStr==" + tStr); LWMissionDB tLWMissionDB = new
		 * LWMissionDB(); mLWMissionSet = tLWMissionDB.executeQuery(tStr); int i =
		 * mLWMissionSet.size(); tStr = "select count(*) from lwmission where
		 * MissionID = '" + mMissionID + "'" + " and activityid in
		 * ('0000007006','0000001115','0000001107')" ;
		 * logger.debug("tStr=" + tStr); String tReSult = new String();
		 * ExeSQL tExeSQL = new ExeSQL(); tReSult = tExeSQL.getOneValue(tStr);
		 * if (tExeSQL.mErrors.needDealError()) { // @@错误处理
		 * this.mErrors.copyAllErrors(tExeSQL.mErrors); CError tError = new
		 * CError(); tError.moduleName = "PrintTakeBackRReportAfterEndService";
		 * tError.functionName = "prepareMission"; tError.errorMessage =
		 * "执行SQL语句：" + tStr + "失败!"; this.mErrors.addOneError(tError); return
		 * false; } if (tReSult == null || tReSult.equals("")) { return false; }
		 * int tCount = 0; tCount = Integer.parseInt(tReSult); //已包括了本次节点及相关同步节点
		 * 
		 * if (i > tCount - 1) { mQueModFlag = "0"; } else { mQueModFlag = "1"; }
		 * 
		 */
		// tongmeng 2007-11-19 modify
		// 修改判断工作流逻辑
		/*
		 * String tSQL = " select nvl(sum(A.x),0) from ( " + " select
		 * decode(count(*),0,0,1) x from lwmission where
		 * missionid='"+mMissionID+"' " + " and activityid='0000007006' and
		 * submissionid<>'"+mSubMissionID+"' " + " union " + " select
		 * decode(count(*),0,0,1) x from lwmission where
		 * missionid='"+mMissionID+"' " + " and activityid='0000001019' and
		 * submissionid<>'"+mSubMissionID+"' " + " union " + " select
		 * decode(count(*),0,0,1) x from lwmission where
		 * missionid='"+mMissionID+"' " + " and activityid='0000001020' and
		 * submissionid<>'"+mSubMissionID+"' " + " union " + " select
		 * decode(count(*),0,0,1) x from lwmission where
		 * missionid='"+mMissionID+"' " + " and activityid='0000001303' and
		 * submissionid<>'"+mSubMissionID+"' " + " ) A ";
		 */

		String tSQL = "select (case when sum(A.x) is not null then sum(A.x)  else 0 end) from ("
				// 判断除了本次回收之外是否还有未打印或未回收的数据
				+ " select (case count(*) when 0 then 0 else 1 end) x from loprtmanager "
				+ " where otherno='"
				+ "?otherno?"
				+ "' and code in ('45','TB90','14') "
				+ " and oldprtseq<>'"
				+ "?oldprtseq?"
				+ "' and stateflag in ('0','1') "
				+ " union "
				// 判断是否有管理中心未回复的问题件
				+ " select (case count(*) when 0 then 0 else 1 end) x from RnewIssuePol where  backobjtype='4' and needprint = 'Y'"
				+ " and state='0' and replyresult is null " + " and contno='"
				+ "?otherno?" + "' " + "	) A";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSQL);
		sqlbv7.put("otherno", this.mContNo);
		sqlbv7.put("oldprtseq", this.mLOPRTManagerSchema.getOldPrtSeq());
		String tResult = new String();
		logger.debug("判断工作流流转tSQL:" + tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv7);
		if (tResult == null || tResult.equals("")) {
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError.buildErr(this, "执行SQL语句：" + tSQL + "失败!") ;
			return false;
		}

		if (Integer.parseInt(tResult) > 0) {
			// 不做工作流扭转,需要删除工作流结点
			mQueModFlag = "0";
			mQueModFlag_UnPrint = "0";
			mQueModFlag_Agent = "0";
			// 准备数据
			String tSQL_LWMission = " select * from lwmission where missionid='"
					+ "?mMissionID?"
					+ "' "
					+ " and submissionid = '"
					+ "?mSubMissionID?"
					+ "' and activityid='"
					+ "?mOperate?"
					+ "' ";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSQL_LWMission);
			sqlbv8.put("mMissionID", this.mMissionID);
			sqlbv8.put("mSubMissionID", this.mSubMissionID);
			sqlbv8.put("mOperate", this.mOperate);
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionSet = tLWMissionDB.executeQuery(sqlbv8);
			Reflections mReflections = new Reflections();
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
				LBMissionSchema tempLBMissionSchema = new LBMissionSchema();
				tempLWMissionSchema.setSchema(tLWMissionSet.get(i));
				String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
				mReflections.transFields(tempLBMissionSchema,
						tempLWMissionSchema);
				tempLBMissionSchema.setSerialNo(tSerielNo);
				tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
				tempLBMissionSchema.setLastOperator(mOperater);
				tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
				this.mLBMissionSetBak.add(tempLBMissionSchema);
				this.mLWMissionSetDEL.add(tempLWMissionSchema);
			}
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
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		// tongmeng 2007-11-28
		mTransferData.setNameAndValue("AddFee_Only", this.mAddFee_Only);
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
