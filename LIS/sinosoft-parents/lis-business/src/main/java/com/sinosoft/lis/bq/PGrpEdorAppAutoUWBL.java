package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPAppUWMasterMainDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAppUWMasterMainSchema;
import com.sinosoft.lis.schema.LPAppUWSubMainSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LPAppUWMasterMainSet;
import com.sinosoft.lis.vschema.LPAppUWSubMainSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全自动核保框架
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Fanx
 * @version 1.0
 */
public class PGrpEdorAppAutoUWBL {
private static Logger logger = Logger.getLogger(PGrpEdorAppAutoUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mUWGrade = "A";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPAppUWMasterMainSet mLPAppUWMasterMainSet = new LPAppUWMasterMainSet();
	private LPAppUWSubMainSet mLPAppUWSubMainSet = new LPAppUWSubMainSet();
	private MMap mMap = new MMap();
	private MMap mUWFinaResult = new MMap();
	private String mUWState = "5";
	private String mGetNoticeNo = "";

	public PGrpEdorAppAutoUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		pInputData = new VData();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据处理操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) // 数据提交
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorAppAutoUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mResult.clear();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", mLPEdorAppSchema.getUWState());
		tTransferData.setNameAndValue("UWGrade", mLPEdorAppSchema.getUWGrade());
		// tTransferData.setNameAndValue("PayPrintParams", mPayPrintParams);
		mResult.add(tTransferData);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 得到保单号
		mLPEdorAppSchema = (LPEdorAppSchema) cInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorAppSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		if (mLPEdorAppSchema.getEdorAcceptNo() == null
				|| mLPEdorAppSchema.getEdorAcceptNo().trim().equals("")) {
			mErrors.addOneError(new CError("申请主表数据不完全！"));
			return false;
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.addOneError(new CError("保全申请主表中没有相关申请号的记录"));
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mLPEdorAppSchema.getOtherNoType().equals("1")
				|| mLPEdorAppSchema.getOtherNoType().equals("3")) { // OtherNoType为1，3时表示是个人保全申请
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
			mLPEdorMainSet = tLPEdorMainDB.query();
			if (mLPEdorMainSet.size() < 1) {
				mErrors.addOneError(new CError("保全受理号"
						+ mLPEdorAppSchema.getEdorAcceptNo() + "下无相应的批单记录！"));
				return false;
			}
			pInputData.clear();
			pInputData.addElement(mGlobalInput);
			pInputData.addElement(mLPEdorMainSet);
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("EdorAppLevel", "Y");
			pInputData.addElement(tTransferData);
			PEdorAutoUWBL tPEdorAutoUWBL = new PEdorAutoUWBL();
			if (!tPEdorAutoUWBL.submitData(pInputData, mOperate)) {
				mErrors.copyAllErrors(tPEdorAutoUWBL.mErrors);
				mErrors.addOneError(new CError("保全受理号"
						+ mLPEdorAppSchema.getEdorAcceptNo() + "下的批单自动核保失败！"));
				// return false;
			}
			MMap map = new MMap();
			map = (MMap) tPEdorAutoUWBL.getResult().getObjectByObjectName(
					"MMap", 0);
			// TransferData tTransferData = new TransferData();
			tTransferData = (TransferData) tPEdorAutoUWBL.getResult()
					.getObjectByObjectName("TransferData", 0);
			mUWState = (String) tTransferData.getValueByName("UWFlag");
			mUWGrade = (String) tTransferData.getValueByName("UWGrade");

			if (map != null) {
				mMap.add(map);
			}
		} else if (mLPEdorAppSchema.getOtherNoType().equals("2")
				|| mLPEdorAppSchema.getOtherNoType().equals("4")) { // OtherNoType为2,4时表示是团体保全申请
			LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
			tLPGrpEdorMainDB
					.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
			mLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
			if (mLPGrpEdorMainSet.size() < 1) {
				mErrors.addOneError(new CError("保全受理号"
						+ mLPEdorAppSchema.getEdorAcceptNo() + "下无相应的批单记录！"));
				return false;
			}
			for (int i = 0; i < mLPGrpEdorMainSet.size(); i++) {
				pInputData.clear();
				pInputData.addElement(mGlobalInput);
				pInputData.addElement(mLPGrpEdorMainSet.get(i + 1));
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("EdorAppLevel", "Y");
				pInputData.addElement(tTransferData);
				PGrpEdorAutoUWBL tPGrpEdorAutoUWBL = new PGrpEdorAutoUWBL();
				if (!tPGrpEdorAutoUWBL.submitData(pInputData, mOperate)) {
					mErrors.copyAllErrors(tPGrpEdorAutoUWBL.mErrors);
					mErrors.addOneError(new CError("保全受理号"
							+ mLPEdorAppSchema.getEdorAcceptNo()
							+ "下的批单自动核保失败！"));
					continue;
				}
				MMap map = new MMap();
				map = (MMap) tPGrpEdorAutoUWBL.getResult()
						.getObjectByObjectName("MMap", 0);
				if (map != null) {
					mMap.add(map);
				}

				tTransferData = (TransferData) tPGrpEdorAutoUWBL.getResult()
						.getObjectByObjectName("TransferData", 0);
				mUWState = (String) tTransferData.getValueByName("UWFlag");
				mUWGrade = (String) tTransferData.getValueByName("UWGrade");
			}
		}

		if ("5".equals(mUWState)) {
			mLPEdorAppSchema.setUWState("5");
			//
			// EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
			// TransferData tTransferData = new TransferData();
			// tTransferData.setNameAndValue("EdorAcceptNo",
			// mLPEdorAppSchema.getEdorAcceptNo());
			// tTransferData.setNameAndValue("ManageCom",
			// mGlobalInput.ManageCom);
			// VData tWorkFlowVData = new VData();
			// tWorkFlowVData.add(tTransferData);
			// tWorkFlowVData.add(mGlobalInput);
			// if (!tEdorWorkFlowUI.submitData(tWorkFlowVData, "9999999998"))
			// //创建保全核保工作流节点0000000017,0000000018
			// {
			// this.mErrors.copyAllErrors(tEdorWorkFlowUI.mErrors);
			// return false;
			// } //comment by zhangrong 2005.5.24 工作流改造后不再需要此段自核后创建人工核保工作流节点的工作
		} else {
			mLPEdorAppSchema.setUWState("9");
			// //保全财务合计处理，将批改补退费表数据合计到应收总表/实付总表
			// PEdorAppUWFinaProduce tPEdorAppUWFinaProduce = new
			// PEdorAppUWFinaProduce(mLPEdorAppSchema.getEdorAcceptNo());
			// tPEdorAppUWFinaProduce.setLimit(PubFun.getNoLimit(mLPEdorAppSchema.getManageCom()));
			// tPEdorAppUWFinaProduce.setOperator(mGlobalInput.Operator);
			//
			// if (!tPEdorAppUWFinaProduce.submitData())
			// {
			// mErrors.copyAllErrors(tPEdorAppUWFinaProduce.mErrors);
			// mErrors.addOneError(new CError("生成财务应收、应付信息失败！"));
			// return false;
			// }
			// if (tPEdorAppUWFinaProduce.getResult() == null)
			// {
			// mErrors.copyAllErrors(tPEdorAppUWFinaProduce.mErrors);
			// mErrors.addOneError(new CError("获得财务应收、应付信息失败！"));
			// return false;
			// }
			// mGetNoticeNo = tPEdorAppUWFinaProduce.getActNoticeNo();
			// mUWFinaResult = (MMap) ((VData)
			// tPEdorAppUWFinaProduce.getResult()).
			// getObjectByObjectName("MMap", 0);
			// mMap.add(mUWFinaResult);

		}
		mLPEdorAppSchema.setAppGrade(mUWGrade);
		mLPEdorAppSchema.setUWGrade(mUWGrade);
		mLPEdorAppSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setUWDate(PubFun.getCurrentDate());
		mLPEdorAppSchema.setUWTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorAppSchema, "UPDATE");

		LPAppUWMasterMainSchema tLPAppUWMasterMainSchema = prepareEdorAppUWMasterMain(mLPEdorAppSchema);
		if (tLPAppUWMasterMainSchema == null) {
			mErrors.addOneError(new CError("生成保全申请核保主表信息失败！"));
			return false;
		}
		if (prepareEdorAppUWSubMain(tLPAppUWMasterMainSchema) == null) {
			mErrors.addOneError(new CError("生成保全申请核保子表信息失败！"));
			return false;
		}

		mMap.put(mLPAppUWMasterMainSet, "DELETE&INSERT");
		mMap.put(mLPAppUWSubMainSet, "INSERT");

		// 生成打印数据
		// if (!getPrintData(mGetNoticeNo))
		// {
		// return false;
		// }

		mResult.add(mMap);

		return true;
	}

	private LPAppUWMasterMainSchema prepareEdorAppUWMasterMain(
			LPEdorAppSchema aLPEdorAppSchema) // 生成申请核保主信息
	{
		int tUWNo = 0;
		LPAppUWMasterMainSchema tLPAppUWMasterMainSchema = new LPAppUWMasterMainSchema();

		LPAppUWMasterMainDB tLPAppUWMasterMainDB = new LPAppUWMasterMainDB();
		tLPAppUWMasterMainDB
				.setEdorAcceptNo(aLPEdorAppSchema.getEdorAcceptNo());
		LPAppUWMasterMainSet tLPAppUWMasterMainSet = tLPAppUWMasterMainDB
				.query();
		if (tLPAppUWMasterMainSet.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPAppUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全申请核保主表信息失败！"));
			return null;
		}
		if (tLPAppUWMasterMainSet == null || tLPAppUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPAppUWMasterMainSchema.setEdorAcceptNo(aLPEdorAppSchema
					.getEdorAcceptNo());
			tLPAppUWMasterMainSchema.setOtherNo(aLPEdorAppSchema.getOtherNo());
			tLPAppUWMasterMainSchema.setOtherNoType(aLPEdorAppSchema
					.getOtherNoType());
			tLPAppUWMasterMainSchema.setUpReportContent("");
			tLPAppUWMasterMainSchema.setHealthFlag("0");
			tLPAppUWMasterMainSchema.setQuesFlag("0");
			tLPAppUWMasterMainSchema.setSpecFlag("0");
			tLPAppUWMasterMainSchema.setAddPremFlag("0");
			tLPAppUWMasterMainSchema.setAddPremReason("");
			tLPAppUWMasterMainSchema.setReportFlag("0");
			tLPAppUWMasterMainSchema.setPrintFlag("0");
			tLPAppUWMasterMainSchema.setPrintFlag2("0");
			tLPAppUWMasterMainSchema.setSpecReason("");
			tLPAppUWMasterMainSchema.setManageCom(aLPEdorAppSchema
					.getManageCom());
			tLPAppUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPAppUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPAppUWMasterMainSet.get(1).getUWNo() + 1;
			tLPAppUWMasterMainSchema.setSchema(tLPAppUWMasterMainSet.get(1));
		}
		tLPAppUWMasterMainSchema.setUWNo(tUWNo);
		tLPAppUWMasterMainSchema.setAutoUWFlag("1");
		tLPAppUWMasterMainSchema.setPassFlag(aLPEdorAppSchema.getUWState());
		tLPAppUWMasterMainSchema.setPostponeDay("");
		tLPAppUWMasterMainSchema.setState(aLPEdorAppSchema.getUWState());
		tLPAppUWMasterMainSchema.setUWIdea("");
		tLPAppUWMasterMainSchema.setUWGrade(aLPEdorAppSchema.getUWGrade());
		tLPAppUWMasterMainSchema.setAppGrade(aLPEdorAppSchema.getAppGrade());
		tLPAppUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPAppUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());
		mLPAppUWMasterMainSet.add(tLPAppUWMasterMainSchema);
		return tLPAppUWMasterMainSchema;
	}

	private LPAppUWSubMainSchema prepareEdorAppUWSubMain(
			LPAppUWMasterMainSchema aLPAppUWMasterMainSchema) // 生成申请核保子表
	{
		LPAppUWSubMainSchema tLPAppUWSubMainSchema = new LPAppUWSubMainSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLPAppUWSubMainSchema,
				aLPAppUWMasterMainSchema);
		tLPAppUWSubMainSchema.setOperator(mGlobalInput.Operator);
		tLPAppUWSubMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppUWSubMainSchema.setModifyTime(PubFun.getCurrentTime());
		tLPAppUWSubMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLPAppUWSubMainSchema.setMakeTime(PubFun.getCurrentTime());

		mLPAppUWSubMainSet.add(tLPAppUWSubMainSchema);
		return tLPAppUWSubMainSchema;
	}

	/**
	 * 生成打印数据
	 * 
	 * @return
	 */
	private boolean getPrintData(String aGetNoticeNo) {
		try {
			// 生成打印数据
			logger.debug("Start 生成打印数据 insert into Print Table");

			VData tVData = new VData();
			tVData.addElement(mGlobalInput);

			PrtAppEndorsementBL tPrtAppEndorsementBL = new PrtAppEndorsementBL(
					mLPEdorAppSchema.getEdorAcceptNo(), aGetNoticeNo);
			if (!tPrtAppEndorsementBL.submitData(tVData, "")) {
				this.mErrors.copyAllErrors(tPrtAppEndorsementBL.mErrors);
				return false;
			}
			tVData = tPrtAppEndorsementBL.getResult();
			MMap mPrintMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			mMap.add(mPrintMap);
		} catch (Exception e) {
			CError.buildErr(this, "生成打印数据失败");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tVData.addElement(tGlobalInput);
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("86000000000093");
		tVData.addElement(tLPEdorAppSchema);

		PGrpEdorAppAutoUWBL tPGrpEdorAppAutoUWBL = new PGrpEdorAppAutoUWBL();
		tPGrpEdorAppAutoUWBL.submitData(tVData, "");

	}

}
