package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPMoveSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPMoveSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单迁移项目明细
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lanjun
 * @version 1.0
 */
public class PEdorPRDetailBL {
private static Logger logger = Logger.getLogger(PEdorPRDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private BqCalBase mBqCalBase = new BqCalBase();

	/**  */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private String manageCom = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	
	private LPMoveSchema inLPMoveSchema = new LPMoveSchema();
	private LPMoveSet outLPMoveSet = new LPMoveSet();

	public PEdorPRDetailBL() {
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
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		// 数据查询业务处理(queryData())

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}

		// PubSubmit tSubmit = new PubSubmit();
		// if (!tSubmit.submitData(mResult, "")) {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorPRDetailBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		return true;
	}

	public boolean getSubmitData(VData cInputData, String cOperate) { // 不含数据提交的数据处理
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		// 数据校验操作（checkdata)
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		
		inLPMoveSchema = (LPMoveSchema)mInputData.getObjectByObjectName("LPMoveSchema", 0);
		
		// mLPAppntSchema = (LPAppntSchema) mInputData
		// .getObjectByObjectName("LPAppntSchema", 0);
		// mLPAddressSchema = (LPAddressSchema) mInputData
		// .getObjectByObjectName("LPAddressSchema", 0);
		//
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		manageCom = (String) cInputData.getObjectByObjectName("String", 0);
		if (mLPEdorItemSchema == null || mGlobalInput == null
				|| manageCom == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获取保全主表数据，节省查询次数
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1).getSchema());
		if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		String sManageCom = mLPEdorItemSchema.getManageCom();
		mLPEdorItemSchema.setManageCom(manageCom);
		Reflections tRef = new Reflections();
		// *********************************************//
		// 查询个人险种信息，存储在p表中，并设置相应的管理机构
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		LPPolSet tLPPolSet = new LPPolSet();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCPolDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		LCPolSet tLCPolSet = tLCPolDB.query(); // 从C表获得该合同下该被保人的所有险种保单信息
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			mErrors.addOneError(new CError("查询个人险种保单失败！"));
			// return false;
		} else {
			int n = tLCPolSet.size();
			for (int i = 1; i <= n; i++) {
				LPPolSchema tLPPolSchema = new LPPolSchema();
				tLCPolSchema.setSchema(tLCPolSet.get(i).getSchema());
				tRef.transFields(tLPPolSchema, tLCPolSchema);
				tLPPolSchema.setManageCom(mLPEdorItemSchema.getManageCom());
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				//tLPPolSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPPolSchema.setMakeTime(PubFun.getCurrentTime());
				tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
				tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
				tLPPolSet.add(tLPPolSchema);
				
				//add by jiaqiangli 2009-02-10 to deal table lpmove
				LPMoveSchema tLPMoveSchema = new LPMoveSchema();
		        tLPMoveSchema.setSchema(inLPMoveSchema);
		        tLPMoveSchema.setPolNoOld(tLPPolSchema.getPolNo());
		        //comment by jiaqiangli 以下字段无实际的应用意义，冗余lis5.3的做法
		        // add by jiaqiangli 2008-10-08 这些字段不知有啥用 最小完毕集合只需记录新旧机构及contno polno and so on
		        tLPMoveSchema.setRiskCode(tLPPolSchema.getRiskCode());
		        tLPMoveSchema.setStandPrem(tLPPolSchema.getStandPrem());
		        tLPMoveSchema.setLastGetDate(tLPPolSchema.getLastGetDate());
		        tLPMoveSchema.setGetStartDate(tLPPolSchema.getGetStartDate());
		        tLPMoveSchema.setLeavingMoney(tLPPolSchema.getLeavingMoney());
		        tLPMoveSchema.setSumPrem(tLPPolSchema.getSumPrem());
		        tLPMoveSchema.setPaytoDate(tLPPolSchema.getPaytoDate());
		        //记录移动表
		        tLPMoveSchema.setModifyDate(PubFun.getCurrentDate());
		        tLPMoveSchema.setModifyTime(PubFun.getCurrentTime());
		        outLPMoveSet.add(tLPMoveSchema);
			}
			mMap.put(tLPPolSet, "DELETE&INSERT");
			mMap.put(outLPMoveSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单（保全）的信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());

		if (!tLCContDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			tLCContSchema.setSchema(tLCContDB.getSchema());
			tRef.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setManageCom(mLPEdorItemSchema.getManageCom());
			tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			//tLPContSchema.setMakeDate(PubFun.getCurrentDate());
			//tLPContSchema.setMakeTime(PubFun.getCurrentTime());
			tLPContSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContSchema.setModifyTime(PubFun.getCurrentTime());

			mMap.put(tLPContSchema, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单领取项表的信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();

		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCInsuredDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredSet.size() < 1 || tLCInsuredSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "领取项表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
				tLCInsuredSchema.setSchema(tLCInsuredSet.get(i).getSchema());
				tRef.transFields(tLPInsuredSchema, tLCInsuredSchema);
				tLPInsuredSchema.setManageCom(mLPEdorItemSchema.getManageCom());
				tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				//tLPInsuredSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPInsuredSchema.setMakeTime(PubFun.getCurrentTime());
				tLPInsuredSchema.setModifyDate(PubFun.getCurrentDate());
				tLPInsuredSchema.setModifyTime(PubFun.getCurrentTime());
				tLPInsuredSet.add(tLPInsuredSchema);
			}
			mMap.put(tLPInsuredSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单申请人的信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();

		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCAppntDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LCAppntSet tLCAppntSet = tLCAppntDB.query();
		if (tLCAppntSet.size() < 1 || tLCAppntSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "领取项表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			for (int i = 1; i <= tLCAppntSet.size(); i++) {
				LPAppntSchema tLPAppntSchema = new LPAppntSchema();
				logger.debug(i);
				tLCAppntSchema.setSchema(tLCAppntSet.get(i).getSchema());
				tRef.transFields(tLPAppntSchema, tLCAppntSchema);
				tLPAppntSchema.setManageCom(mLPEdorItemSchema.getManageCom());
				tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				//tLPAppntSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPAppntSchema.setMakeTime(PubFun.getCurrentTime());
				tLPAppntSchema.setModifyDate(PubFun.getCurrentDate());
				tLPAppntSchema.setModifyTime(PubFun.getCurrentTime());
				tLPAppntSet.add(tLPAppntSchema);
			}
			mMap.put(tLPAppntSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单领取项表的信息
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSchema tLCGetSchema = new LCGetSchema();

		tLCGetDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPGetSet tLPGetSet = new LPGetSet();
		LCGetSet tLCGetSet = tLCGetDB.query();
		if (tLCGetSet.size() < 1 || tLCGetSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "领取项表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			for (int i = 1; i <= tLCGetSet.size(); i++) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				logger.debug(i);
				tLCGetSchema.setSchema(tLCGetSet.get(i).getSchema());
				tRef.transFields(tLPGetSchema, tLCGetSchema);
				tLPGetSchema.setGetDutyCode(tLCGetSchema.getGetDutyCode());
				tLPGetSchema.setManageCom(mLPEdorItemSchema.getManageCom());
				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				//tLPGetSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPGetSchema.setMakeTime(PubFun.getCurrentTime());
				tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
				tLPGetSchema.setModifyTime(PubFun.getCurrentTime());
				tLPGetSet.add(tLPGetSchema);
			}
			mMap.put(tLPGetSet, "DELETE&INSERT");
		}

		/** ******************************************** */

		// *********************************************//
		// 准备个人保单保费项表的信息
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSchema tLCPremSchema = new LCPremSchema();

		tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCPremDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPPremSet tLPPremSet = new LPPremSet();
		LCPremSet tLCPremSet = tLCPremDB.query();
		if (tLCPremSet.size() < 1 || tLCPremSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保费项表表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			for (int i = 1; i <= tLCPremSet.size(); i++) {
				LPPremSchema tLPPremSchema = new LPPremSchema();
				tLCPremSchema.setSchema(tLCPremSet.get(i).getSchema());
				tRef.transFields(tLPPremSchema, tLCPremSchema);
				tLPPremSchema.setManageCom(mLPEdorItemSchema.getManageCom());
				tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
				tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
				tLPPremSet.add(tLPPremSchema);
			}
			mMap.put(tLPPremSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备保险帐户管理费表的信息
		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();

		tLCInsureAccFeeDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCInsureAccFeeDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LCInsureAccFeeSet tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		if (tLCInsureAccFeeSet.size() < 1 || tLCInsureAccFeeSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保险帐户管理费表查询失败!";
			this.mErrors.addOneError(tError);
			//return false;
		} else {
			for (int i = 1; i <= tLCInsureAccFeeSet.size(); i++) {
				LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
				tLCInsureAccFeeSchema.setSchema(tLCInsureAccFeeSet.get(i)
						.getSchema());
				tRef.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeSchema);
				tLPInsureAccFeeSchema.setManageCom(mLPEdorItemSchema
						.getManageCom());
				tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				//tLPInsureAccFeeSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPInsureAccFeeSchema.setMakeTime(PubFun.getCurrentTime());
				tLPInsureAccFeeSchema.setModifyDate(PubFun.getCurrentDate());
				tLPInsureAccFeeSchema.setModifyTime(PubFun.getCurrentTime());
				tLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
			}
			mMap.put(tLPInsureAccFeeSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单保费项表的信息
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();

		tLCInsureAccDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCInsureAccDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
		if (tLCInsureAccSet.size() < 1 || tLCInsureAccSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保费项表表查询失败!";
			this.mErrors.addOneError(tError);
			//return false;
		} else {

			for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
				LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
				tLCInsureAccSchema
						.setSchema(tLCInsureAccSet.get(i).getSchema());
				tRef.transFields(tLPInsureAccSchema, tLCInsureAccSchema);
				tLPInsureAccSchema.setManageCom(mLPEdorItemSchema
						.getManageCom());
				tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				//tLPInsureAccSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPInsureAccSchema.setMakeTime(PubFun.getCurrentTime());
				tLPInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
				tLPInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
				tLPInsureAccSet.add(tLPInsureAccSchema);
			}
			mMap.put(tLPInsureAccSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单保费项表的信息
		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();

		tLCInsureAccClassFeeDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCInsureAccClassFeeDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB
				.query();
		if (tLCInsureAccClassFeeSet.size() < 1
				|| tLCInsureAccClassFeeSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保费项表表查询失败!";
			this.mErrors.addOneError(tError);
			//return false;
		} else {
			for (int i = 1; i <= tLCInsureAccClassFeeSet.size(); i++) {
				LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
				tLCInsureAccClassFeeSchema.setSchema(tLCInsureAccClassFeeSet
						.get(i).getSchema());
				tRef.transFields(tLPInsureAccClassFeeSchema,
						tLCInsureAccClassFeeSchema);
				tLPInsureAccClassFeeSchema.setManageCom(mLPEdorItemSchema
						.getManageCom());
				tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				//tLPInsureAccClassFeeSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPInsureAccClassFeeSchema.setMakeTime(PubFun.getCurrentTime());
				tLPInsureAccClassFeeSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLPInsureAccClassFeeSchema.setModifyTime(PubFun
						.getCurrentTime());
				tLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
			}
			mMap.put(tLPInsureAccClassFeeSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备保险账户分类表的信息
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();

		tLCInsureAccClassDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCInsureAccClassDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassSet.size() < 1 || tLCInsureAccClassSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保险账户分类表查询失败!";
			this.mErrors.addOneError(tError);
			//return false;
		} else {
			for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
				LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
				tLCInsureAccClassSchema.setSchema(tLCInsureAccClassSet.get(i)
						.getSchema());
				tRef.transFields(tLPInsureAccClassSchema,
						tLCInsureAccClassSchema);
				tLPInsureAccClassSchema.setManageCom(mLPEdorItemSchema
						.getManageCom());
				tLPInsureAccClassSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				//tLPInsureAccClassSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPInsureAccClassSchema.setMakeTime(PubFun.getCurrentTime());
				tLPInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
				tLPInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
				tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
			}
			mMap.put(tLPInsureAccClassSet, "DELETE&INSERT");
		}

		// *********************************************//
		// 准备个人保单保费项表的信息
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();

		tLCInsureAccTraceDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCInsureAccTraceDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		if (tLCInsureAccTraceSet.size() < 1 || tLCInsureAccTraceSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "保费项表表查询失败!";
			this.mErrors.addOneError(tError);
			//return false;
		} else {

			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
				tLCInsureAccTraceSchema.setSchema(tLCInsureAccTraceSet.get(i)
						.getSchema());
				tRef.transFields(tLPInsureAccTraceSchema,
						tLCInsureAccTraceSchema);
				tLPInsureAccTraceSchema.setManageCom(mLPEdorItemSchema
						.getManageCom());
				tLPInsureAccTraceSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				//tLPInsureAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
				//tLPInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
				tLPInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
				tLPInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
				tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
			}
			mMap.put(tLPInsureAccTraceSet, "DELETE&INSERT");
		}

		// if
		// (StrTool.compareString(String.valueOf(mLPAddressSchema.getAddressNo()),
		// "")) {
		// ExeSQL tExeSQL = new ExeSQL();
		// SSRS tSSRS = new SSRS();
		// String sql = "Select Case When max(AddressNo) Is Null Then '1' Else
		// max(AddressNo) End from LCAddress where CustomerNo='"
		// + mLPAppntSchema.getAppntNo() +
		// "'";
		//
		// tSSRS = tExeSQL.execSQL(sql);
		// Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
		// int ttNo = firstinteger.intValue() + 1;
		// // Integer integer = new Integer(ttNo);
		// logger.debug("得到的地址码是：" + ttNo);
		//
		// mLPAddressSchema.setAddressNo("" + ttNo);
		//
		// }
		// String customerNo = "";
		// LPAppntDB tLPAppntDB = new LPAppntDB();
		// LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		// LCAppntDB tLCAppntDB = new LCAppntDB();
		// tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		// LCAppntSchema tLCAppntSchema = tLCAppntDB.query().get(1);
		//
		// tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		//
		// if(!tLPAppntDB.getInfo())
		// {
		// customerNo = tLCAppntSchema.getAppntNo();
		// }
		// else
		// {
		// customerNo = tLPAppntDB.query().get(1).getAppntNo();
		// }
		//
		// mLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// mLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// mLPAddressSchema.setCustomerNo(customerNo);
		// mLPAddressSchema.setOperator(mGlobalInput.Operator);
		// mLPAddressSchema.setMakeDate(PubFun.getCurrentDate());
		// mLPAddressSchema.setMakeTime(PubFun.getCurrentTime());
		// mLPAddressSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPAddressSchema.setModifyTime(PubFun.getCurrentTime());
		// mMap.put(mLPAddressSchema, "DELETE&INSERT");
		//
		//
		// Reflections rf = new Reflections();
		// rf.transFields(mLPAppntSchema,tLCAppntSchema);
		// mLPAppntSchema.setAddressNo(String.valueOf(mLPAddressSchema.getAddressNo()));
		// mLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// mLPAppntSchema.setContNo(mLPEdorItemSchema.getContNo());
		// mLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// mLPAppntSchema.setOperator(mGlobalInput.Operator);
		// mLPAppntSchema.setManageCom(manageCom);
		// mLPAppntSchema.setMakeDate(PubFun.getCurrentDate());
		// mLPAppntSchema.setMakeTime(PubFun.getCurrentTime());
		// mLPAppntSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPAppntSchema.setModifyTime(PubFun.getCurrentTime());
		// mMap.put(mLPAppntSchema, "DELETE&INSERT");

		int CountDate = PubFun.calInterval2(tLPContSchema.getSignDate(),
				mLPEdorItemSchema.getEdorValiDate(), "D");
		if (CountDate <= 10) {
			mBqCalBase.setHesitateFlag("Y");
		} else {
			mBqCalBase.setHesitateFlag("N");
		}
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());

		mLPEdorItemSchema.setManageCom(sManageCom);
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mResult.clear();
		mResult.add(mBqCalBase);
		mResult.add(mMap);
		return true;
	}

}
