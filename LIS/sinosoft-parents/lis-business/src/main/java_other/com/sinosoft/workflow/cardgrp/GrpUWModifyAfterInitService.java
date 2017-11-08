package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCGCUWSubDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGCUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGCUWSubSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCGCUWSubSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGCUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.Reflections;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流服务类:团体新契约核保订正
 * </p>
 * <p>
 * Description:团体核保订正工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class GrpUWModifyAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpUWModifyAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	/** 业务数据类 */
	private LCContSet mLCContSet = new LCContSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCCUWSubSet mLCCUWSubSet= new LCCUWSubSet();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();
	private LCGCUWSubSet mLCGCUWSubSet = new LCGCUWSubSet();
	private Reflections mRef =new Reflections();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mMissionID;
	private String mSubMissionID;
	private String mGrpContNo;
	private String mPrtNo;
	private String mGrpContSql;
	private String mGrpPolSql;
	private String mContSql;
	private String mPolSql;
	private String mCCUWMasterSql;
	private String mCUWMasterSql;
	private String mGCUWMasterSql;
	private String mGUWMasterSql;
	private String mCCUWSubSql;
	private String mCUWSubSql;
	private String mGCUWSubSql;
	private String mGUWSubSql;

	public GrpUWModifyAfterInitService() {
	}

	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LW0004")) {
			return false;
		}
		return true;
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

		//控制并发 
		String tPrtNo = (String)mTransferData.getValueByName("PrtNo");
		boolean tLockFlag = true;
		if (!lockNo(tPrtNo)) {
			logger.debug("锁定号码失败!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			tLockFlag = false;
			//mPubLock.unLock();
			return false;
		}// 锁定主附险投保单号以及暂收费号码)
		try{
		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			mPubLock.unLock();
		}
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mGrpContSql, "UPDATE");
		map.put(mGrpPolSql, "UPDATE");
		map.put(mContSql, "UPDATE");
		map.put(mPolSql, "UPDATE");
		map.put(mCCUWMasterSql, "UPDATE");
		map.put(mCUWMasterSql, "UPDATE");
		map.put(mGCUWMasterSql, "UPDATE");
		map.put(mGUWMasterSql, "UPDATE");

		map.put(mCCUWSubSql, "INSERT");
		map.put(mCUWSubSql, "INSERT");
		map.put(mGCUWSubSql, "INSERT");
		map.put(mGUWSubSql, "INSERT");
		
		map.put(mLCCUWSubSet, "INSERT");
		map.put(mLCUWSubSet, "INSERT");
		map.put(mLCGUWSubSet, "INSERT");
		map.put(mLCGCUWSubSet, "INSERT");

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
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWRReportModifyAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "集体保单" + mGrpContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema.setSchema(tLCGrpContDB);
		mPrtNo = mLCGrpContSchema.getPrtNo();

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setPrtNo(mPrtNo);
		mLCContSet = tLCContDB.query();
		if (mLCContSet == null || mLCContSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "UWRReportModifyAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "个人合同保单信息查询失败!";
			this.mErrors.addOneError(tError);
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

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProposalApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的GrpContNo
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中GrpContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// LCPolDB tLCPolDB = null;
		// LCUWSubDB tLCUWSubDB =null;
		// for(int i =1;i<mLCContSet.size();i++)
		// {
		// //查询合同核保主表，如果有uwflag等于5的纪录，则说明自核不通过，没有则说明自核通过
		// //核保订正后使其恢复自核后的状态
		// String tSql = "select distinct 1 from LCCUWSub where 1=1 "
		// + " and ContNo = '"+mLCContSet.get(i).getContNo() + "'"
		// + " and uwflag = '5'"
		// ;
		// ExeSQL lccuw = new ExeSQL();
		// String rs = lccuw.getOneValue(tSql);
		// if(rs.equals("1"))
		// {
		// //此分支表示自核未通过的保单
		// mLCContSet.get(i).setUWFlag("5");
		// tLCPolDB = new LCPolDB();
		// tLCPolDB.setContNo(mLCContSet.get(i).getContNo());
		// mLCPolSet = tLCPolDB.query();
		// if(mLCPolSet==null||mLCPolSet.size()<=0)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "GrpUWModifyAfterInitService";
		// tError.functionName = "dealData";
		// tError.errorMessage = "查询险种保单表失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// for(int j=1;j<=mLCPolSet.size();j++)
		// {
		// String tPolSql = "select distinct 1 from LCUWSub where 1=1 "
		// + " and PolNo = '"+mLCPolSet.get(j).getPolNo() + "'"
		// + " and uwflag = '5'"
		// ;
		// ExeSQL lcuw = new ExeSQL();
		// String rsc = lcuw.getOneValue(tSql);
		// if (rsc.equals("1"))
		// {
		// mLCPolSet.get(j).setUWFlag("5");
		// }
		// }
		// }
		// }
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		
		mGrpContSql = "update lcgrpcont set uwflag= 'z' where 1=1 "
				+ " and grpcontno = '" + mGrpContNo + "'";

		mGrpPolSql = " update lcgrppol set uwflag = 'z' where 1=1 "
				+ " and grpcontno = '" + mGrpContNo + "'";

		mContSql = "update lccont set uwflag = 'z' where 1=1 "
				+ " and contno in (select distinct contno from LCCUWSub where 1=1"
				+ " and grpcontno = '" + mGrpContNo + "'"
				+ " and passflag = '5')";
		mPolSql = "update lcpol set uwflag = 'z' where 1=1 "
				+ " and polno in (select distinct polno from lcuwsub where 1=1"
				+ " and grpcontno = '" + mGrpContNo + "'"
				+ " and passflag = '5')";

		mCCUWMasterSql = "update lccuwmaster set passflag = 'z',uwno=uwno+1 where 1=1 "
				+ " and contno in (select distinct contno from LCCUWSub where 1=1"
				+ " and grpcontno = '"
				+ mGrpContNo
				+ "'"
				+ " and passflag = '5')";
		
        LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
        LCCUWSubSchema tLCCUWSubSchema =new LCCUWSubSchema();
        LCCUWMasterDB mLCCUWMasterDB = new LCCUWMasterDB();
        String tLCCUWMSQL = " select * from lccuwmaster where 1=1  and contno in " 
        	               +"(select distinct contno from LCCUWSub where 1=1 and "
        	               +"grpcontno = '"+mGrpContNo+"' and passflag = '5')";
        mLCCUWMasterSet = mLCCUWMasterDB.executeQuery(tLCCUWMSQL);
        
        if(mLCCUWMasterSet.size()>0)
        {
        	mRef.transFields(tLCCUWSubSchema, mLCCUWMasterSet.get(1));
    		mLCCUWSubSet.add(tLCCUWSubSchema);
        }
//		mCCUWSubSql = "insert into lccuwsub select * from lccuwmaster where 1=1 "
//				+ " and contno in (select distinct contno from LCCUWSub where 1=1"
//				+ " and grpcontno = '"
//				+ mGrpContNo
//				+ "'"
//				+ " and passflag = '5') ";
		mCUWMasterSql = "update lcuwmaster set passflag = 'z',uwno=uwno+1 where 1=1 "
				+ " and polno in (select distinct polno from lcuwsub where 1=1"
				+ " and grpcontno = '"
				+ mGrpContNo
				+ "'"
				+ " and passflag = '5')";
		
		LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
        LCUWSubSchema tLCUWSubSchema =new LCUWSubSchema();
        LCUWMasterDB mLCUWMasterDB = new LCUWMasterDB();
        String tLCUWMSQL=" select * from lcuwmaster where 1=1  and polno in "
        	            +" (select distinct polno from lcuwsub where 1=1 and grpcontno = '"+mGrpContNo+"' "
        	            +" and passflag = '5')and passflag = 'z'";
        mLCUWMasterSet=mLCUWMasterDB.executeQuery(tLCUWMSQL);
        if(mLCUWMasterSet.size()>0)
        {
        	mRef.transFields(tLCUWSubSchema, mLCUWMasterSet.get(1));
        	mLCUWSubSet.add(tLCUWSubSchema);
        }
//		mCUWSubSql = "insert into lcuwsub select * from lcuwmaster where 1=1 "
//				+ " and polno in (select distinct polno from lcuwsub where 1=1"
//				+ " and grpcontno = '" + mGrpContNo + "'"
//				+ " and passflag = '5')" + " and passflag = 'z'";

		mGCUWMasterSql = "update lcgcuwmaster set passflag = 'z',uwno=uwno+1 where 1=1 "
				+ " and grpcontno = (select grpcontno from lcgrpcont where prtno = '"
				+ mPrtNo + "')";
		
//		LCGCUWMasterSet mLCGCUWMasterSet = new LCGCUWMasterSet();
//        LCGCUWSubSchema tLCGCUWSubSchema =new LCGCUWSubSchema();
//        LCGCUWMasterDB mLCCGUWMasterDB = new LCGCUWMasterDB();
//        String tLCGCUWSQL="select * from lcgcuwmaster where 1=1  and grpcontno = (select grpcontno from lcgrpcont where prtno = '"+mPrtNo+"')";
//        mLCGCUWMasterSet=mLCCGUWMasterDB.executeQuery(tLCGCUWSQL);
//        if(mLCGCUWMasterSet.size()>0)
//        {
//        	mRef.transFields(tLCGCUWSubSchema, mLCGCUWMasterSet.get(1));
//        	mLCGCUWSubSet.add(tLCGCUWSubSchema);
//        }
		mGCUWSubSql = "insert into lcgcuwsub select * from lcgcuwmaster where 1=1 "
				+ " and grpcontno = (select grpcontno from lcgrpcont where prtno = '"
				+ mPrtNo + "')";

		mGUWMasterSql = "update lcguwmaster set passflag = 'z',uwno=uwno+1 where 1=1 "
				+ " and grpcontno = (select grpcontno from lcgrpcont where prtno = '"
				+ mPrtNo + "')";
		
//		LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
//        LCGUWSubSchema tLCGUWSubSchema =new LCGUWSubSchema();
//        LCGUWMasterDB mLCGUWMasterDB = new LCGUWMasterDB();
//        String tLCGUWSQL="select * from lcguwmaster where 1=1 and grpcontno = (select grpcontno from lcgrpcont where prtno = '"+mPrtNo+"')";
//        mLCGUWMasterSet=mLCGUWMasterDB.executeQuery(tLCGUWSQL);
//        if(mLCGUWMasterSet.size()>0)
//        {
//        	mRef.transFields(tLCGUWSubSchema, mLCGUWMasterSet.get(1));
//        	mLCGUWSubSet.add(tLCGUWSubSchema);
//        }
		mGUWSubSql = "insert into lcguwsub select * from lcguwmaster where 1=1 "
				+ " and grpcontno = (select grpcontno from lcgrpcont where prtno = '"
				+ mPrtNo + "')";

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("GrpContNo", mGrpContNo);
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("GrpNo", mLCGrpContSchema.getAppntNo());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());
		String tSql = " select missionprop12 from lwmission where 1 =1 "
				+ " and activityid = '0000011005' and missionid = '"
				+ mMissionID + "'" + " and submissionid = '" + mSubMissionID
				+ "'";
		logger.debug("tSql:" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		String tUWAuthority = tExeSQL.getOneValue(tSql);
		if (tUWAuthority == null || tUWAuthority.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "核保师级别查询失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		mTransferData.setNameAndValue("UWAuthority", tUWAuthority);

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
