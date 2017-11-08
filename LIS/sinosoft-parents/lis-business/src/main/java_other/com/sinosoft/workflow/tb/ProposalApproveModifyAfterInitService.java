package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.ProposalCheckBL;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流服务类:新契约问题件修改
 * </p>
 * <p>
 * Description: 问题件修改工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author: HYQ
 * @version 1.0
 */

public class ProposalApproveModifyAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(ProposalApproveModifyAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mUWFlag = ""; // 核保标志
	/** 业务数据操作字符串 */
	private String mContNo;
	private String mApproveFlag;
	private String mMissionID;
	private String mSubMissionID;
	// 判断问题件修改完毕是否该扭转到下一结点标记
//	private String mOtherNoticeFlag = "";
	private String mOperate = "";
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LWMissionSet mLWMissionSet_Update = new LWMissionSet();// 如果结点未扭转,需要更新lwmission表的状态为等待中
	private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();// 如果结点需要扭转,那么,删除除了本结点之外的所有其他结点
	private LBMissionSet mLBMissionSet_Delete = new LBMissionSet();// 如果结点需要扭转,那么,备份除了本结点之外的所有其他结点

	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();

	private LCIssuePolSet mLCIssuePolSet_Reply = new LCIssuePolSet();// 需要系统自动回复的问题件
	private MMap updatemap = new MMap();

	public ProposalApproveModifyAfterInitService() {
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
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验业务数据
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// //修改保单容易出现错误的数据
		// if (!updateContInfo())
		// return false;
		// 处理是否做下一扭转需要判断字段的准备
		//tongmeng 2009-02-21 add
		//该方法转移到客户号手工合并服务类中处理
		/*
		if (!prepareNextMission()) {
			return false;
		}*/
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// mResult.clear();
		return true;
	}

	/**
	 * 准备下一结点判断字段
	 * 
	 * @return
	 */
	/*
	private boolean prepareNextMission() {
		//tongmeng 2009-02-21 add
		//判断是否有本次需要下发的问题件,如果有的话,不能删除当前的节点.
		//此处需要考虑并发问题???
		//
		if(checkIssuePol())
		{
			return true;
		}
		
		// tongmeng 2007-12-20 modify
		// 判断是否有生调未处理
		// 判段问题件修改后,是否该承保申请已处于人工核保已回复状态.modify ln 2008-11-03		
		
		String tSQL = "Select count(*)"
				+ " from LWMission"
				+ " where 1 = 1 and missionid= '"
				+ this.mMissionID + "' and (activityid in "
				//考虑处于发送核保通知书到自核之间的工作流节点
				+ "(0000001108, 0000001120, 0000001116, 0000001108, "
                + " 0000001302, 0000001112, 0000001301, "
                + " 0000001107, 0000001112, 0000001115, "
                + " 0000001020, "
                + " 0000001017, 0000001018, 0000001019, "
                + " 0000001106, 0000001111, 0000001114) "
                //有直接到自核的工作流节点要判断节点状态不为4
				+ " or (activityid in ('0000001002', '0000001113', '0000001404', '0000001121') and activitystatus<>'4')) "
				;
//		String tSQL = "select decode(count(*),0,0,1) from LOPRTManager where code='04' and stateflag<>'2' and otherno='"
//				+ this.mContNo + "' ";
		ExeSQL tExeSQL = new ExeSQL();
		String tResult = tExeSQL.getOneValue(tSQL);
		if (tResult == null || tResult.equals("")) {
			CError.buildErr(this, "判断是否有未扭转的工作流结点出错！");
			return false;
		}
		if (Integer.parseInt(tResult) > 1) {
			// 还有其他操作未完成,不能扭转到自动核保,修改工作流结点状态
			this.mOtherNoticeFlag = "1";
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
			String tSQL_temp = "select * from lwmission where missionid='"
					+ this.mMissionID + "' " + " and submissionid='"
					+ this.mSubMissionID + "' and activityid='" + this.mOperate
					+ "'";
			tLWMissionSet = tLWMissionDB.executeQuery(tSQL_temp);
			if (tLWMissionSet.size() != 1) {
				CError.buildErr(this, "查询工作流结点出错！");
				return false;
			}
			//节点已完成 modify ln 2008-11-04
//			for (int i = 1; i <= tLWMissionSet.size(); i++) {
//				tLWMissionSet.get(i).setActivityStatus("4");
//				// 如果不做扭转,肯定是体检或生调未做完操作,此处应该设置状态为核保未回复状态
//				tLWMissionSet.get(i).setMissionProp18("4");
//				this.mLWMissionSet_Update.add(tLWMissionSet.get(i));
//			}
			Reflections mReflections = new Reflections();
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
				LBMissionSchema tempLBMissionSchema = new LBMissionSchema();

				tempLWMissionSchema = tLWMissionSet.get(i);
				String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
				mReflections.transFields(tempLBMissionSchema,
						tempLWMissionSchema);
				tempLBMissionSchema.setSerialNo(tSerielNo);
				tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
				tempLBMissionSchema.setLastOperator(mOperater);
				tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
				this.mLBMissionSet_Delete.add(tempLBMissionSchema);
				this.mLWMissionSet_Delete.add(tempLWMissionSchema);
			}
		} else {
			// 扭转工作流,删除其他操作数据
			this.mOtherNoticeFlag = "0";
			String tSQL_temp = "select * from lwmission where missionid='"
					+ this.mMissionID + "' " + " and activityid<>'"
					+ this.mOperate + "' ";
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionSet = tLWMissionDB.executeQuery(tSQL_temp);
			Reflections mReflections = new Reflections();
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
				LBMissionSchema tempLBMissionSchema = new LBMissionSchema();

				tempLWMissionSchema = tLWMissionSet.get(i);
				String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
				mReflections.transFields(tempLBMissionSchema,
						tempLWMissionSchema);
				tempLBMissionSchema.setSerialNo(tSerielNo);
				tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
				tempLBMissionSchema.setLastOperator(mOperater);
				tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
				this.mLBMissionSet_Delete.add(tempLBMissionSchema);
				this.mLWMissionSet_Delete.add(tempLWMissionSchema);
			}
		}

		return true;
	}
	 */
	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLCContSchema, "UPDATE");
		map.put(mLCPolSet, "UPDATE");
		map.add(updatemap);
		map.put(mLCIssuePolSet_Reply, "UPDATE");
//		if (this.mOtherNoticeFlag.equals("0")) {
//			// 需要扭转
//			map.put(this.mLBMissionSet_Delete, "INSERT");
//			map.put(this.mLWMissionSet_Delete, "DELETE");
//		} else if (this.mOtherNoticeFlag.equals("1")) {
//			// 不需要扭转
//			map.put(this.mLWMissionSet_Update, "UPDATE");
//		}
		//tongmeng 2009-02-21 add
		//将删除工作流节点的工作放在客户号手工合并服务类中处理.
	/*	
		if (this.mLBMissionSet_Delete!=null && this.mLBMissionSet_Delete.size()>0)
			map.put(this.mLBMissionSet_Delete, "INSERT");
		if (this.mLWMissionSet_Delete!=null && this.mLWMissionSet_Delete.size()>0)
			map.put(this.mLWMissionSet_Delete, "DELETE");
		if (this.mLWMissionSet_Update!=null && this.mLWMissionSet_Update.size()>0)
			map.put(this.mLWMissionSet_Update, "UPDATE");
		*/
		mResult.add(map);
		return true;

	}

	/***************************************************************************
	 * 修改保单信息中容易出现错误的数据(2006-08-04添加)
	 **************************************************************************/
	private boolean updateContInfo() {
		// 修改LCPol表MainPolno(主险种保单号)字段为该单主险的PolNo(险种保单号)
		String updatemainpolnosql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		updatemainpolnosql = "update lcpol a set a.mainpolno=( "
				+ " select b.polno from lcpol b where b.contno=a.contno and exists ("
				+ " select 'X' from lmriskapp where lmriskapp.riskcode=b.riskcode and lmriskapp.subriskflag='M'"
				+ "  ) )" + " where a.contno='" + "?contno?"
				+ "'";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			updatemainpolnosql="UPDATE lcpol a INNER JOIN lmriskapp b ON a.riskcode = b.riskcode SET a.mainpolno = a.polno " 
		        +"WHERE a.contno = '?contno?' " 
		        +" AND b.subriskflag = 'm' ";
		}
		
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(updatemainpolnosql);
	        sqlbv1.put("contno", mLCContSchema.getContNo());
		// 修改LCCont表的被保人信息为LCinsured表里的第一被保人的信息
	        String updateinsuredsql = "";
	        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	        	updateinsuredsql = "update lccont set (insuredno,insuredname,insuredsex,insuredbirthday,insuredidtype,insuredidno)"
	    				+ "=(select insuredno,name,sex,birthday,idtype,idno from lcinsured where lcinsured.contno=lccont.contno and sequenceno='1')"
	    				+ " where contno='" + "?contno1?" + "'";
	        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	        	updateinsuredsql = "UPDATE lccont a INNER JOIN lcinsured b ON b.contno = a.contno SET "
	        			+"a.insuredno = b.insuredno,"
	        			+"a.insuredname = b.NAME,"
	           			+"a.insuredsex = b.sex,"
	        			+"a.insuredbirthday = b.birthday,"
	          			+"a.insuredidtype = b.idtype,"
	        	  	    +"a.insuredidno = b.idno "
	        			+"WHERE b.sequenceno = '1' "
	         	  	    +"AND a.contno = '?contno1?' ";
	        }
		
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(updateinsuredsql);
	        sqlbv2.put("contno1", mLCContSchema.getContNo());
		updatemap.put(sqlbv1, "UPDATE");
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
			CError.buildErr(this,"投保单" + mContNo + "合同信息查询失败！");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 校验合同单下是否有被保人
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		mLCInsuredSet = tLCInsuredDB.query();
		if (mLCInsuredSet == null || mLCInsuredSet.size() == 0) {
			CError.buildErr(this,"投保单" + mContNo + "被保人信息查询失败！");
			return false;
		}

		// 校验合同单下是否有险种单
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mContNo);
		mLCPolSet = tLCPolDB.query();
		if (mLCPolSet == null || mLCPolSet.size() < 0) {

			CError.buildErr(this,"投保单" + mContNo + "险种信息查询失败！");
			return false;
		}

		// ------------------------------------------------------Beg
		// ----------修改人：chenrong
		// ----------修改日期：2006-3-28
		// 校验险种信息是否正确
		VData tInputData = new VData();
		tInputData.add(mLCContSchema);
		CheckRiskField tCheckRiskField = new CheckRiskField();
		if (!tCheckRiskField.CheckTBField(tInputData, "TBALLINSERT")) {
			this.mErrors.copyAllErrors(tCheckRiskField.mErrors);
			return false;
		}
		// -----------------------------------------------------End

		if (mLCContSchema.getAppFlag() != null
				&& !mLCContSchema.getAppFlag().trim().equals("0")) {
			// @@错误处理
			CError.buildErr(this,"此单不是投保单，不能进行问题件修改确认操作！");
			return false;
		}

		// if (mLCContSchema.getUWFlag() != null &&
		// !mLCContSchema.getUWFlag().trim().equals("0")) {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "ProposalApproveBL";
		// tError.functionName = "checkData";
		// tError.errorMessage = "此投保单已经开始核保，不能进行复核操作!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// 判断是否将问题件处理完毕
		// tongmeng 2007-11-22 modify
		// 对需要打印的核保通知书的问题件自动做回复
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		/*
		 * String aSQL = "select * from LCIssuePol where contno='" + mContNo + "'
		 * and replyman is null and replyresult is null";
		 */

		String aSQL = "select * from lcissuepol where contno='"
				+ "?contno2?"
				+ "' "
				+ " and state is not null "
				+ " and "
				+ " ( "
				+ " (backobjtype in( '2','3') and needprint='Y' and replyresult is null ) "
				+ " ) ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(aSQL);
        sqlbv3.put("contno2", mContNo);
		logger.debug("333333aSQL===" + aSQL);
		LCIssuePolSet aLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv3);
		if (aLCIssuePolSet.size() != 0) {
			for (int i = 1; i <= aLCIssuePolSet.size(); i++) {
				aLCIssuePolSet.get(i).setReplyMan("SYS");
				aLCIssuePolSet.get(i).setReplyResult("系统自动回复");
				aLCIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
				aLCIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
				this.mLCIssuePolSet_Reply.add(aLCIssuePolSet.get(i));
			}

		}
		// 判断是否有给问题件修改岗的问题件未做回复
		//tongmeng 2009-05-15 modify
		//needprint
		String tSQL_a = "select * from lcissuepol where contno='"
				+ "?contno3?"
				+ "' "
				+ " and state is not null and backobjtype ='1' and replyresult is null "
				+ " and needprint='Y' ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql(tSQL_a);
        sqlbv4.put("contno3", mContNo);
		tLCIssuePolDB = new LCIssuePolDB();
		aLCIssuePolSet = new LCIssuePolSet();
		aLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv4);
		logger.debug("44444aSQL===" + tSQL_a);
		if (aLCIssuePolSet.size() != 0) {
			CError.buildErr(this,"此投保单的给操作员的问题件还没有回复，不能进行问题件修改确认操作！");
			return false;
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
		// mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中MissionID失败!");
			return false;
		}
		mSubMissionID = mTransferData.getValueByName("SubMissionID") == null ? ""
				: (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中ContNo失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 具体操作为从TransferData取出投保单ID，检索此投保单下所有被保人和险种信息；
	 * 如果被保人（投保人）重要信息与此被保人险种中的信息存在变化则重新计算险种的保额和保费； 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// /////////////////////////////////////////////
		// 录入完毕时进行保单信息校验
		ProposalCheckBL proposalcheckbl = new ProposalCheckBL();
		GlobalInput aGlobalInput = new GlobalInput();
		aGlobalInput.ManageCom = (String) mLCContSchema.getManageCom();
		aGlobalInput.Operator = (String) mLCContSchema.getOperator();
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

		mInputData.clear();

		mApproveFlag = "0"; // 复核确认标志
		// 准备保单的复核标志
		mLCContSchema.setApproveFlag(mApproveFlag);
		for (int i = 1; i < mLCPolSet.size(); i++) {
			mLCPolSet.get(i).setApproveFlag(mApproveFlag);
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {

		mTransferData.setNameAndValue("PrtSeq", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());
		mTransferData.setNameAndValue("AgentNo", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("ApproveFlag", mApproveFlag);
		//mTransferData.setNameAndValue("OtherNoticeFlag", mOtherNoticeFlag);

		return true;
	}

	/**
	 * preparePrt 准备核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareContUW() {
		mLCCUWSubSet.clear();

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		if (!tLCCUWMasterDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendNoticeAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = "LCCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);
		mLCCUWMasterSchema.setPassFlag(mUWFlag);
		mLCCUWMasterSchema.setQuesFlag("2");
		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendNoticeAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = "LCCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(mContNo);
			tLCCUWSubSchema.setGrpContNo(mLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(mLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLCCUWSubSchema.setPrintFlag("1");
			tLCCUWSubSchema.setAutoUWFlag(mLCCUWMasterSchema.getAutoUWFlag());
			tLCCUWSubSchema.setState(mLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setOperator(mLCCUWMasterSchema.getOperator()); // 操作员
			tLCCUWSubSchema.setManageCom(mLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendNoticeAfterInitService";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCCUWSubSet.add(tLCCUWSubSchema);
		return true;
	}

	/**
	 * 准备险种核保表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW() {
		mLCUWMasterSet.clear();
		mLCUWSubSet.clear();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			tLCUWMasterSchema = new LCUWMasterSchema();
			tLCUWMasterDB = new LCUWMasterDB();
			tLCPolSchema = mLCPolSet.get(i);
			tLCUWMasterDB.setProposalNo(tLCPolSchema.getProposalNo());

			if (!tLCUWMasterDB.getInfo()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWSendNoticeAfterInitService";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWMaster表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCUWMasterSchema.setSchema(tLCUWMasterDB);

			// tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo()+1);核保主表中的UWNo表示该投保单经过几次人工核保(等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
			tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCUWMasterSchema.setQuesFlag("2");
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			mLCUWMasterSet.add(tLCUWMasterSchema);

			// 核保轨迹表
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubDB = new LCUWSubDB();
			tLCUWSubSet = new LCUWSubSet();
			tLCUWSubDB.setContNo(tLCPolSchema.getContNo());
			tLCUWSubSet = tLCUWSubDB.query();
			if (tLCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWSendNoticeAfterInitService";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWSub表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			int m = tLCUWSubSet.size();
			logger.debug("subcount=" + m);
			if (m > 0) {
				m++; // 核保次数
				tLCUWSubSchema = new LCUWSubSchema();
				tLCUWSubSchema.setUWNo(m); // 第几次核保
				tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
				tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
				tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
				tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
						.getProposalContNo());
				tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo());
				tLCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
				tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
				tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
				tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
				tLCUWSubSchema.setPrintFlag("1");
				tLCUWSubSchema.setState(mUWFlag);
				tLCUWSubSchema.setOperator(mOperater); // 操作员
				tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
				tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWSendNoticeAfterInitService";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWSub表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCUWSubSet.add(tLCUWSubSchema);
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
	 * tongmeng 2009-02-21 add
	 * 问题件修改岗处理完毕后,如果有需要继续下发问题件(机构和业务员),那么不能扭转工作流.
	 * 不需要扭转 返回 true
	 * 需要扭转 返回 false
	 * @return
	 */
	private boolean checkIssuePol()
	{
		boolean tFlag = false;
		String tsql = "";
		//判断是否有发给客户的问题件,如果有的话,不能发放问题件,工作流扭转到自核.
		tsql = "select * from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?contno4?"
				+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
				+ " and prtseq is null ";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(tsql);
        sqlbv5.put("contno4", mContNo);
		logger.debug("tSql==" + tsql);
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv5);
		if (tLCIssuePolSet.size() > 0) {	
			return false;
		}

	// 查询发送给业务员通知书
	//有业务员问题件,可以发放
	tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
			+ "?contno5?" + "' and backobjtype = '3' and prtseq is null"
			+ " and needprint = 'Y' ";
	SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
    sqlbv6.sql(tsql);
    sqlbv6.put("contno5", mContNo);
	tLCIssuePolSet = new LCIssuePolSet();
	logger.debug("tSql==" + tsql);
	tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv6);
	if (tLCIssuePolSet.size() > 0) {
		return true;
	}
	// 查询发送给机构的问题件
	//有机构问题件可以发放
	tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
			+ "?contno6?" + "' and backobjtype = '4' and replyresult is null";
	SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
    sqlbv7.sql(tsql);
    sqlbv7.put("contno6", mContNo);
	tLCIssuePolSet = new LCIssuePolSet();
	logger.debug("tSql==" + tsql);
	tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv7);
	if (tLCIssuePolSet.size() > 0) {
		return true;
	}		
		return tFlag;
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
