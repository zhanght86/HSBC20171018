package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全删除业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lh
 * @version 1.0
 */

// 功能：个人保单一个保全项目申请确认时的删除处理
// 入口参数：个单的保单号、批单号和批改类型
// 出口参数：各个表单要删除的记录数据
public class EdorAppCancelBL {
private static Logger logger = Logger.getLogger(EdorAppCancelBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	// private LPEdorMainBLSet mLPEdorMainBLSet = new LPEdorMainBLSet();
	// private LCPolBL mLCPolBL=new LCPolBL();
	// private LCPolBLSet mLCPolBLSet=new LCPolBLSet();
	// private LPPolBL mLPPolBL=new LPPolBL();
	// private LPPolBLSet mLPPolBLSet=new LPPolBLSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public EdorAppCancelBL() {
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
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (cOperate.equals("DELETE||EDOR")) {
			logger.debug("BL:checkData start");
			if (!checkData()) {
				return false;
			}
			logger.debug("checkdata");
			if (!prepareData()) {
				return false;
			}
			logger.debug("preparedata");
			EdorAppCancelBLS tEdorAppCancelBLS = new EdorAppCancelBLS();
			if (!tEdorAppCancelBLS.submitData(mInputData, mOperate)) {
				return false;
			}

			logger.debug("----submit---");
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 查询条件
		logger.debug("moperator:" + mOperate);

		if (mOperate.equals("DELETE||EDOR")) {
			mLPEdorItemSchema.setSchema((LPEdorItemSchema) cInputData
					.getObjectByObjectName("LPEdorItemSchema", 0));
			logger.debug(mLPEdorItemSchema.getEdorState());
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		/*
		 * 删除表名：
		 * LPPrem,LPPerson,LPPol,LPDuty,LPGet,LPLoan,LPCustomerImpart,LPAppntInd,
		 * LPInsured,LPBnf,LPMove,LPInsureAcc,LPInsureAccTrace,LPAppntTrace,
		 * LJSPaySchema,LJSGetSchema,LJSGetEndorse,LPEdorMain,
		 * LPUWError,LPUWSub,LPUWMaster,LPUWMasterMain,等核保表
		 */
		String tGrpContNo;
		String tEdorNo;
		String tEdorType;
		tGrpContNo = mLPEdorItemSchema.getGrpContNo();
		tEdorNo = mLPEdorItemSchema.getEdorNo();
		tEdorType = mLPEdorItemSchema.getEdorType();

		/*
		 * LPPremSchema tLPPremSchema = new LPPremSchema();
		 * //tLPPremSchema.setPolNo(tPolNo); tLPPremSchema.setEdorNo(tEdorNo);
		 * tLPPremSchema.setEdorType(tEdorType); mInputData.clear();
		 * mInputData.add(tLPPremSchema); mResult.clear();
		 * mResult.add(tLPPremSchema);
		 * 
		 * LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		 * tLPPersonSchema.setEdorNo(tEdorNo);
		 * tLPPersonSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPPersonSchema); mResult.add(tLPPersonSchema);
		 * 
		 * LPPolSchema tLPPolSchema = new LPPolSchema();
		 * //tLPPolSchema.setPolNo(tPolNo); tLPPolSchema.setEdorNo(tEdorNo);
		 * tLPPolSchema.setEdorType(tEdorType); mInputData.add(tLPPolSchema);
		 * mResult.add(tLPPolSchema);
		 * 
		 * LPDutySchema tLPDutySchema = new LPDutySchema();
		 * //tLPDutySchema.setPolNo(tPolNo); tLPDutySchema.setEdorNo(tEdorNo);
		 * tLPDutySchema.setEdorType(tEdorType); mInputData.add(tLPDutySchema);
		 * mResult.add(tLPDutySchema);
		 * 
		 * LPGetSchema tLPGetSchema = new LPGetSchema();
		 * //tLPGetSchema.setPolNo(tPolNo); tLPGetSchema.setEdorNo(tEdorNo);
		 * tLPGetSchema.setEdorType(tEdorType); mInputData.add(tLPGetSchema);
		 * mResult.add(tLPGetSchema);
		 * 
		 * LPLoanSchema tLPLoanSchema = new LPLoanSchema();
		 * //tLPLoanSchema.setPolNo(tPolNo); tLPLoanSchema.setEdorNo(tEdorNo);
		 * tLPLoanSchema.setEdorType(tEdorType); mInputData.add(tLPLoanSchema);
		 * mResult.add(tLPLoanSchema);
		 * 
		 * LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
		 * tLPReturnLoanSchema.setEdorNo(tEdorNo);
		 * tLPReturnLoanSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPReturnLoanSchema);
		 * mResult.add(tLPReturnLoanSchema);
		 * 
		 * LPCustomerImpartSchema tLPCustomerImpartSchema = new
		 * LPCustomerImpartSchema(); //tLPCustomerImpartSchema.setPolNo(tPolNo);
		 * tLPCustomerImpartSchema.setEdorNo(tEdorNo);
		 * tLPCustomerImpartSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPCustomerImpartSchema);
		 * mResult.add(tLPCustomerImpartSchema);
		 * 
		 * LPAppntIndSchema tLPAppntIndSchema = new LPAppntIndSchema();
		 * //tLPAppntIndSchema.setPolNo(tPolNo);
		 * tLPAppntIndSchema.setEdorNo(tEdorNo);
		 * tLPAppntIndSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPAppntIndSchema); mResult.add(tLPAppntIndSchema);
		 * 
		 * LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		 * //tLPInsuredSchema.setPolNo(tPolNo);
		 * tLPInsuredSchema.setEdorNo(tEdorNo);
		 * tLPInsuredSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPInsuredSchema); mResult.add(tLPInsuredSchema);
		 * 
		 * LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		 * //tLPBnfSchema.setPolNo(tPolNo); tLPBnfSchema.setEdorNo(tEdorNo);
		 * tLPBnfSchema.setEdorType(tEdorType); mInputData.add(tLPBnfSchema);
		 * mResult.add(tLPBnfSchema);
		 * 
		 * LPMoveSchema tLPMoveSchema = new LPMoveSchema();
		 * tLPMoveSchema.setEdorNo(tEdorNo);
		 * tLPMoveSchema.setEdorType(tEdorType); mInputData.add(tLPMoveSchema);
		 * mResult.add(tLPMoveSchema);
		 * 
		 * LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		 * tLPInsureAccSchema.setEdorNo(tEdorNo);
		 * tLPInsureAccSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPInsureAccSchema); mResult.add(tLPInsureAccSchema);
		 * 
		 * LPInsureAccTraceSchema tLPInsureAccTraceSchema = new
		 * LPInsureAccTraceSchema(); tLPInsureAccTraceSchema.setEdorNo(tEdorNo);
		 * tLPInsureAccTraceSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPInsureAccTraceSchema);
		 * mResult.add(tLPInsureAccTraceSchema);
		 * 
		 * LPAppntTraceSchema tLPAppntTraceSchema = new LPAppntTraceSchema();
		 * tLPAppntTraceSchema.setEdorNo(tEdorNo);
		 * tLPAppntTraceSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPAppntTraceSchema);
		 * mResult.add(tLPAppntTraceSchema);
		 * 
		 * LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		 * tLJSPaySchema.setOtherNo(tEdorNo); tLJSPaySchema.setOtherNoType("3");
		 * mInputData.add(tLJSPaySchema); mResult.add(tLJSPaySchema);
		 * 
		 * LJSGetSchema tLJSGetSchema = new LJSGetSchema();
		 * tLJSGetSchema.setOtherNo(tEdorNo); tLJSGetSchema.setOtherNoType("3");
		 * mInputData.add(tLJSGetSchema); mResult.add(tLJSGetSchema);
		 * 
		 * LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		 * tLJSGetEndorseSchema.setEndorsementNo(tEdorNo);
		 * tLJSGetEndorseSchema.setFeeOperationType(tEdorType);
		 * mInputData.add(tLJSGetEndorseSchema);
		 * mResult.add(tLJSGetEndorseSchema);
		 */

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorNo(tEdorNo);
		tLPEdorItemSchema.setEdorType(tEdorType);
		// tLPEdorMainSchema.setPolNo(tPolNo);
		mInputData.add(tLPEdorItemSchema);
		mResult.add(tLPEdorItemSchema);

		// 核保表
		/*
		 * LPUWErrorSchema tLPUWErrorSchema = new LPUWErrorSchema();
		 * tLPUWErrorSchema.setEdorNo(tEdorNo);
		 * tLPUWErrorSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPUWErrorSchema); mResult.add(tLPUWErrorSchema);
		 * 
		 * LPUWSubSchema tLPUWSubSchema = new LPUWSubSchema();
		 * tLPUWSubSchema.setEdorNo(tEdorNo);
		 * tLPUWSubSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPUWSubSchema); mResult.add(tLPUWSubSchema);
		 * 
		 * LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
		 * tLPUWMasterSchema.setPolNo(tPolNo);
		 * tLPUWMasterSchema.setEdorNo(tEdorNo);
		 * tLPUWMasterSchema.setEdorType(tEdorType);
		 * mInputData.add(tLPUWMasterSchema); mResult.add(tLPUWMasterSchema);
		 * 
		 * //保全核保主表数据表 LPUWMasterMainSchema tLPUWMasterMainSchema = new
		 * LPUWMasterMainSchema(); tLPUWMasterMainSchema.setPolNo(tPolNo);
		 * tLPUWMasterMainSchema.setEdorNo(tEdorNo);
		 * mInputData.add(tLPUWMasterMainSchema);
		 * mResult.add(tLPUWMasterMainSchema);
		 */

		// 保全核保工作流相关数据表
		// LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LBMissionSchema tLBMissionSchema = new LBMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
		tLWMissionDB.setProcessID("0000000000");
		tLWMissionDB.setActivityID("0000000000");
		tLWMissionDB.setMissionProp1(tEdorNo);
		tLWMissionDB.setMissionProp2(tGrpContNo);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet == null) {
			return false;
		}
		if (tLWMissionSet.size() > 0) {
			// 工作流Ｃ表
			tempLWMissionSchema = tLWMissionSet.get(1);
			tLWMissionSchema.setProcessID(tempLWMissionSchema.getProcessID());
			tLWMissionSchema.setMissionID(tempLWMissionSchema.getMissionID());
			mInputData.add(tLWMissionSchema);
			mResult.add(tLWMissionSchema);

			// 工作流Ｂ表
			tLBMissionSchema.setProcessID(tempLWMissionSchema.getProcessID());
			tLBMissionSchema.setMissionID(tempLWMissionSchema.getMissionID());
			mInputData.add(tLBMissionSchema);
			mResult.add(tLBMissionSchema);
		}

		// 保全工作流特约数据表
		LPSpecSchema tLPSpecSchema = new LPSpecSchema();
		tLPSpecSchema.setEndorsementNo(tEdorNo);
		tLPSpecSchema.setPolNo(tGrpContNo);
		mInputData.add(tLPSpecSchema);
		mResult.add(tLPSpecSchema);

		// 新增附加险, add by Minim
		if (tEdorType.equals("NS")) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tGrpContNo);
			if (!tLCPolDB.getInfo()) {
				return false;
			}

			LCPolDB tLCPolDB2 = new LCPolDB();
			String strSql = "select * from lcpol where prtno='"
					+ "?prtno?" + "' and appflag in (2, 3)";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("prtno", tLCPolDB.getPrtNo());
			logger.debug("NS strSql:" + strSql);
			LCPolSet tLCPolSet = tLCPolDB2.executeQuery(sqlbv);

			mInputData.add(tLCPolSet);
			mResult.add(tLCPolSet);

			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("SPECIAL", "NS");
			mInputData.add(mTransferData);
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		logger.debug("-----------");
		if (!tLPEdorItemDB.getInfo()) {
			return false;
		}
		logger.debug(";;;;;;;;;;");
		if (tLPEdorItemDB.getEdorState().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAppCancelBL";
			tError.functionName = "Checkdata";
			tError.errorMessage = "此保全已经经过了保全确认,不可进行删除!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("--------check_data");
		return true;
	}

	public static void main(String[] args) {
		EdorAppCancelBL tEdorAppCancelBL = new EdorAppCancelBL();
		VData tVData = new VData();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorNo("00000120020420000086");
		tLPEdorItemSchema.setGrpContNo("00000120020210000016");
		tLPEdorItemSchema.setEdorType("BB");

		// tLPEdorMainSchema.setEdorState("1");
		tVData.addElement(tLPEdorItemSchema);
		tEdorAppCancelBL.submitData(tVData, "DELETE||EDOR");
	}
}
