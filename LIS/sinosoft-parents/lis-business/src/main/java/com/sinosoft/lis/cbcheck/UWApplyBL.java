package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 工作流任务申请
 * </p>
 * <p>
 * Description: 每个操作员可以每次申请1个任务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ [upd zhangtao]
 * @version 1.0
 */

public class UWApplyBL {
private static Logger logger = Logger.getLogger(UWApplyBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mType;//ln 2008-10-10 add --1为存在其他投保单需一并处理
	private String mCom;//ln 2008-10-10 add --1为存在其他投保单需一并处理
	private String mUWLevel;//ln 2008-10-10 add --1为存在其他投保单需一并处理
	private String mPrtNo;//ln 2008-10-10 add --1为存在其他投保单需一并处理
	
	private String mEdorAcceptNo; //add by xiongzh 09-11-16 客户层保全人工核保的受理号
	private String mCustomerBQFlag ="0" ;//add by xiongzh 09-11-16 人工核保申请时, 客户层保全标记 0-非客户层 ；1-客户层	
	private LWMissionSet UPDate_LWMissionSet = new LWMissionSet();
	private SSRS BQMissionData = new SSRS();
	private ExeSQL tExeSQL = new ExeSQL();

	public UWApplyBL() {
	}

	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC0032")) {
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
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mInputData, mOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");
		//锁表 并发控制09-06-06
		if("1".equals(mCustomerBQFlag)) //客户层保全
		{
			for(int i=1;i<=BQMissionData.MaxRow;i++)
			{
				mMissionID = BQMissionData.GetText(i, 1);
				boolean tLockFlag =true;
				if (!lockNo(mMissionID+mActivityID)) {
					logger.debug("锁定号码失败!");
					this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
					tLockFlag = false;
					//mPubLock.unLock();
					return false;
				}
			}
		}
		else
		{
			boolean tLockFlag =true;
			if (!lockNo(mMissionID+mActivityID)) {
				logger.debug("锁定号码失败!");
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				tLockFlag = false;
				//mPubLock.unLock();
				return false;
			}
		}
		try {
			// 进行业务处理
			if (!dealData()) {
				return false;
			}
	
			if (!prepareOutputData()) {
				return false;
			}
	
			logger.debug("dealData successful!");
	
			// 数据提交
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "ICaseCureBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mInputData = null;
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			mPubLock.unLock();
		}
		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealOneData(String lActivityID,String lMissionID,String lSubMissionID) {
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(lActivityID);
		tLWMissionDB.setMissionID(lMissionID);
		tLWMissionDB.setSubMissionID(lSubMissionID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流任务查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLWMissionSchema.setSchema(tLWMissionDB.getSchema());
		//tongmeng 2009-06-19 modify
		//校验默认操作员是否为空,如果不为空,不允许申请.
		if(tLWMissionSchema.getDefaultOperator()!=null&&!tLWMissionSchema.getDefaultOperator().equals(""))
		{
			CError.buildErr(this,"该单已经被申请.");
			return false;
		}
		tLWMissionSchema.setLastOperator(mOperator);
		
		tLWMissionSchema.setDefaultOperator(mOperator);
		//lzf modify 20130425
//		if (lActivityID != null && lActivityID.equals("0000001100")) {
//		tLWMissionSchema.setMissionProp14(mOperator);
//		}
		if (lActivityID != null) {
			String sql ="select functionid from lwactivity where activityid ='"+"?activityid?"+"'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("activityid", lActivityID);
			String tFunctionID = new ExeSQL().getOneValue(sqlbv);
			if(tFunctionID.equals("10010028")){
			tLWMissionSchema.setMissionProp14(mOperator);
			}
		}
  //end
		tLWMissionSchema.setInDate(PubFun.getCurrentDate());
		tLWMissionSchema.setInTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(tLWMissionSchema.getInDate());
		tLWMissionSchema.setModifyTime(tLWMissionSchema.getInTime());

		// 向工作流锁表中添加记录
//		LWLockSchema tLWLockSchema = new LWLockSchema();
//		tLWLockSchema.setLockActivityID(lActivityID);
//		tLWLockSchema.setMissionID(lMissionID);
//		tLWLockSchema.setSubMissionID(lSubMissionID);
//		tLWLockSchema.setLockType("1");
//		tLWLockSchema.setProcessID(tLWMissionDB.getProcessID());
//		tLWLockSchema.setLockStatus("0");
//		tLWLockSchema.setMakeDate(PubFun.getCurrentDate());
//		tLWLockSchema.setMakeTime(PubFun.getCurrentTime());
//		tLWLockSchema.setOperator(mOperator);
//		tLWLockSchema.setTimeOut(0.00);
//
//		map.put(tLWLockSchema, "INSERT");
//		map
//				.put("select * from lwmission where activityid='" + lActivityID
//						+ "' and missionid='" + lMissionID
//						+ "' and submissionid='" + lSubMissionID
//						+ "' and DefaultOperator is not null", "SELECT");
		UPDate_LWMissionSet.add(tLWMissionSchema);
		return true;
	}
	
	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
			if("1".equals(mCustomerBQFlag)) //客户层保全
			{
				for(int i=1;i<=BQMissionData.MaxRow;i++)
				{
					mMissionID = BQMissionData.GetText(i, 1);
					mSubMissionID = BQMissionData.GetText(i, 2);
					if(!dealOneData(mActivityID, mMissionID, mSubMissionID))
						return false;
				}
			}
			else
			{
				if(!dealOneData(mActivityID, mMissionID, mSubMissionID))
					return false;
			}
			map.put(UPDate_LWMissionSet, "UPDATE");
			if(mType.equals("1")) {
				String lActivityID = "";
				String lMissionID = "";
				String lSubMissionID = "";
				//tongmeng 2009-05-11 modify
				//并单排除4状态保单
				String sql="select w.activityid,w.missionid,w.submissionid from lwmission w,lcinsured b ,lccuwmaster c"
				       //+ " where w.activityid='0000001100' and w.missionprop18 in ('1','2','3','4')"
					  // + " where w.activityid='0000001100' and w.missionprop18 in ('1','2','3')"
						+" where w.activityid in (select activityid from lwactivity where functionid='10010028') "
					   + " and c.contno = w.missionprop2 and c.uwstate in('1','2','3') "
				       + " and b.contno = w.MissionProp2 "
				       + " and w.MissionProp10 like concat('"+"?mCom?"+"','%')"
					   + " and w.MissionProp12<='"+"?mUWLevel?"+"'"  
					   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
					   + " and w.defaultoperator is null "
				       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+"?prtno?"+"' ) and w.MissionProp1 <> '"+"?prtno?"+"'";		   
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("mCom", mCom);
				sqlbv1.put("mUWLevel", mUWLevel);
				sqlbv1.put("prtno", mPrtNo);
				ExeSQL exeSQL = new ExeSQL();
		        SSRS ssrs = exeSQL.execSQL(sqlbv1);
	
		        for(int n = 0; n < ssrs.getMaxRow(); n++) {
		        	lActivityID = ssrs.GetText(n+1, 1);
		        	lMissionID = ssrs.GetText(n+1, 2);
		        	lSubMissionID = ssrs.GetText(n+1, 3);
		            if(!dealOneData(lActivityID, lMissionID, lSubMissionID))
		            	return false;
		        }
			}	
		
		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
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
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null || mSubMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		if (mActivityID == null || mActivityID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ActivityID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}		
		
		mType = (String) mTransferData.getValueByName("Type");
		if (mType == null || mType.trim().equals("")) {
			mType = "0";
		}
		
		mUWLevel = (String) mTransferData.getValueByName("UWLevel");
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		mCom = (String) mTransferData.getValueByName("Com");
		
		//判断是否是客户层保全
		String sql1="select EdorAcceptNo from lpedorapp where othernotype='1' and EdorAcceptNo in " 
			+"(select missionprop1 from lwmission l "
			+" where activityid = '"+"?activityid?"+"' /*and processid = '0000000000'*/ and defaultoperator is null and missionid='"+"?missionid?"+"' and submissionid='"+"?submissionid?"+"')  ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql1);
		sqlbv2.put("activityid",mActivityID);
		sqlbv2.put("missionid",mMissionID);
		sqlbv2.put("submissionid",mSubMissionID);
		String xEdorAcceptNo="";
		xEdorAcceptNo = tExeSQL.getOneValue(sqlbv2);
		if(xEdorAcceptNo!=null&&!xEdorAcceptNo.equals(""))//客户层保全
		{
			mCustomerBQFlag="1";
			//此时前台界面确保传进来的肯定是客户层保全原始受理号
			mEdorAcceptNo = xEdorAcceptNo;
			
			String customerBQ_mission="select missionid,submissionid from lwmission l "
			+" where activityid = '"+"?activityid?"+"' /*and processid = '0000000000'*/ and defaultoperator is null "
            +" and missionprop1 in(select icedoracceptno from lpconttempinfo x where x.edoracceptno='"+"?edoracceptno?"+"' union select'"+"?edoracceptno?"+"' from dual )"
	        +" and missionprop7 like concat('"+"?managecom?"+"','%') order by MakeDate, MakeTime ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(customerBQ_mission);
			sqlbv3.put("activityid", mActivityID);
			sqlbv3.put("edoracceptno", mEdorAcceptNo);
			sqlbv3.put("managecom", mManageCom);
			BQMissionData = new SSRS();
			BQMissionData = tExeSQL.execSQL(sqlbv3);
			if(BQMissionData.MaxRow==0)
			{
				CError tError = new CError();
				tError.moduleName = "UWApplyBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "客户层保全查询所有子任务失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

}
