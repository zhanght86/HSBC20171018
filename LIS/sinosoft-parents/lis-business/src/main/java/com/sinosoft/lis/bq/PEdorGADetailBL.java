package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPInsureAccClassBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGetToAccDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LMDutyGetAliveDB;
import com.sinosoft.lis.db.LPAccMoveGetDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAccMoveGetSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LPAccMoveGetSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 业务系统给付申请功能部分
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class PEdorGADetailBL {
private static Logger logger = Logger.getLogger(PEdorGADetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPGetSet aLPGetSet = new LPGetSet();
	private LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
	private TransferData mTransferData = new TransferData();
	private LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private LPAccMoveGetSet mLPAccMoveGetSet = new LPAccMoveGetSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String currDate = PubFun.getCurrentDate();
	private String currTime = PubFun.getCurrentTime();
	private MMap map = new MMap();

	public PEdorGADetailBL() {
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
		logger.debug("-----GA in BL ---");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		// 数据校验操作（checkdata)
		if (!checkData())
			return false;

		// 数据查询业务处理
		if (!queryData())
			return false;
		logger.debug("---operate:" + this.getOperate());
		if (this.getOperate().equals("INSERT||MAIN")) {
			// 数据复杂业务处理(dealData())
			if (!dealData())
				return false;
			// 数据准备操作（preparedata())
			if (!prepareData())
				return false;

			this.setOperate("APPCONFIRM||GA");

			logger.debug("---" + mOperate);

			// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
			// 数据提交
			// PEdorGADetailBLS tPEdorGADetailBLS = new PEdorGADetailBLS();
			// logger.debug("Start AppConfirm GA BL Submit...");
			// if (!tPEdorGADetailBLS.submitData(mInputData,mOperate))
			// {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tPEdorGADetailBLS.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "PEdorGADetailBL";
			// tError.functionName = "submitData";
			// tError.errorMessage = "数据提交失败!";
			// this.mErrors .addOneError(tError) ;
			// return false;
			// }
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private void getInputData(VData cInputData) {
		aLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPAccMoveGetSet = (LPAccMoveGetSet) cInputData.getObjectByObjectName(
				"LPAccMoveGetSet", 0);
		// mLPGrpEdorItemSchema
		// =(LPGrpEdorItemSchema)cInputData.getObjectByObjectName("LPGrpEdorItemSchema",0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			if (mLPGrpEdorItemSchema == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PInsuredBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "无保全申请数据!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
				tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
				if (tLPGrpEdorItemDB.getInfo()) {
					mLPGrpEdorItemSchema
							.setSchema(tLPGrpEdorItemDB.getSchema());
				}
			}
		}
		if (this.getOperate().equals("INSERT||DATA")) {
			if (!tLPEdorItemDB.getEdorState().trim().equals("1")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PInsuredBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "该保全已经申请确认不能修改!";
				logger.debug("------" + tError);
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return flag;

	}

	/**
	 * 复杂业务处理
	 * 
	 * @return
	 */

	private boolean dealData() {
		// aLPGetSet = (LPGetSet)mInputData.getObjectByObjectName("LPGetSet",0);
		// aLCInsureAccClassSet =
		// (LCInsureAccClassSet)mInputData.getObjectByObjectName("LCInsureAccClassSet",0);
		// mTransferData =
		// (TransferData)mInputData.getObjectByObjectName("TransferData",0);

		// String tAnnuityFlag =
		// (String)mTransferData.getValueByName("annuityFlag");
		// String tCashFlag = (String)mTransferData.getValueByName("cashFlag");
		// String tAnnuityType="";
		// String tCashType ="";
		// String tAnnuity = "";
		// String tCash ="";

		// String tRAnnuity = (String)mTransferData.getValueByName("RAnnuity");
		// String tCAnnuity = (String)mTransferData.getValueByName("CAnnuity");
		// String tRCash = (String)mTransferData.getValueByName("RCash");
		// String tCCash = (String)mTransferData.getValueByName("CCash");
		// mLPAccMoveGetSet=(LPAccMoveGetSet)mInputData.getObjectByObjectName("LPAccMoveGetSet",0);
		if (mLPAccMoveGetSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "请选择任意一项给付金进行操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LPGetSchema tLPGetSchema = new LPGetSchema();
		Reflections tReflections = new Reflections();
		LCInsureAccClassSchema aLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LPInsureAccClassSchema aLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPPolBL tLPPolBL = new LPPolBL();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		tLPGetSchema = aLPGetSet.get(1);
		mLPGetSchema.setSchema(tLPGetSchema);
		mLPGetSet.set(aLPGetSet);

		// 准备帐户信息
		tLCInsureAccClassSchema = aLCInsureAccClassSet.get(1);
		tReflections.transFields(aLPInsureAccClassSchema,
				tLCInsureAccClassSchema);
		aLPInsureAccClassSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		aLPInsureAccClassSchema.setEdorType(aLPEdorItemSchema.getEdorType());

		LPInsureAccClassBL tLPInsureAccClassBL = new LPInsureAccClassBL();
		tLPInsureAccClassBL.queryLPInsureAccClass(aLPInsureAccClassSchema);
		tReflections.transFields(aLCInsureAccClassSchema, tLPInsureAccClassBL
				.getSchema());
		logger.debug("---baladate1:"
				+ aLCInsureAccClassSchema.getBalaDate());
		logger.debug("---baladate2:"
				+ tLCInsureAccClassSchema.getBalaDate());
		if (!aLCInsureAccClassSchema.getBalaDate().equals(
				tLCInsureAccClassSchema.getBalaDate())) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "请重新结息后进行保全操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		aLCInsureAccClassSchema.setBalaDate(tLCInsureAccClassSchema
				.getBalaDate());
		aLCInsureAccClassSchema.setBalaTime(tLCInsureAccClassSchema
				.getBalaTime());
		aLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema
				.getInsuAccBala());

		// 准备保单信息
		if (!tLPPolBL.queryLPPol(aLPEdorItemSchema)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLPPolBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorGADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLPPolSchema.setSchema(tLPPolBL.getSchema());
			mLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			mLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());
		}
		tReflections.transFields(tLCPolSchema, tLPPolBL.getSchema());
		mLCPolSchema.setSchema(tLCPolSchema);

		// 准备批改数据
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo())
			mLPEdorItemSchema = this.initLPEdorItemSchema(mLPPolSchema);
		else
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		// 准备给付信息
		LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
		tLMDutyGetAliveDB.setGetDutyCode(tLPGetSchema.getGetDutyCode());
		tLMDutyGetAliveDB.setGetDutyKind(tLPGetSchema.getGetDutyKind());
		if (!tLMDutyGetAliveDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "生存给付责任查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
		tLCGetDB.setDutyCode(mLPGetSchema.getDutyCode());
		tLCGetDB.setGetDutyCode(mLPGetSchema.getGetDutyCode());
		if (!tLCGetDB.getInfo())
			return false;
		else {
			mLPGetSchema.setSchema(this.initLPGet(tLCGetDB.getSchema(),
					mLPGetSchema));
			mLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			mLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		}

		tReflections = new Reflections();
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		tReflections.transFields(tLPInsureAccClassSchema,
				aLCInsureAccClassSchema);
		tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		// mLPAccMoveGetSet.clear();
		double tAccBalance = aLCInsureAccClassSchema.getInsuAccBala();
		double tGetAccBalance = 0;// 转出金额
		String tAnnuity = "";// 转出比率

		for (int i = 1; i <= mLPAccMoveGetSet.size(); i++) {
			LPAccMoveGetSchema tLPAccMoveGetSchema = mLPAccMoveGetSet.get(i);
			tLPAccMoveGetSchema
					.setSchema(initLPAccMoveGet(tLPInsureAccClassSchema));

			// 金额
			if (tLPAccMoveGetSchema.getMoneyMoveType().equals("C")) {
				tGetAccBalance = tLPAccMoveGetSchema.getAccMoveBala();
				tAnnuity = String.valueOf(tGetAccBalance / tAccBalance);
			}
			// 比例
			else {
				tGetAccBalance = tAccBalance
						* tLPAccMoveGetSchema.getAccMoveRate();
				tAnnuity = String.valueOf(tLPAccMoveGetSchema.getAccMoveRate());
			}
			if (tGetAccBalance > tAccBalance) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGADetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "转年金金额超出帐户总金额!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLPAccMoveGetSchema.setAccMoveBala(tGetAccBalance);
			tLPAccMoveGetSchema.setAccMoveRate(tAnnuity);
		}
		// mLPAccMoveGetSet.add(tLPAccMoveSchema);

		return true;
	}

	private LPAccMoveGetSchema initLPAccMoveGet(
			LPInsureAccClassSchema aLPInsureAccClassSchema) {
		LPAccMoveGetSchema tLPAccMoveGetSchema = new LPAccMoveGetSchema();
		tLPAccMoveGetSchema.setEdorNo(aLPInsureAccClassSchema.getEdorNo());
		tLPAccMoveGetSchema.setEdorType(aLPInsureAccClassSchema.getEdorType());
		tLPAccMoveGetSchema.setPolNo(aLPInsureAccClassSchema.getPolNo());
		tLPAccMoveGetSchema
				.setInsuAccNo(aLPInsureAccClassSchema.getInsuAccNo());
		// remark by pq
		// tLPAccMoveGetSchema.setSubInsuAccNo(aLPInsureAccClassSchema.getOtherNo());
		tLPAccMoveGetSchema.setOtherNo(aLPInsureAccClassSchema.getOtherNo());
		tLPAccMoveGetSchema
				.setOtherType(aLPInsureAccClassSchema.getOtherType());
		tLPAccMoveGetSchema.setRiskCode(aLPInsureAccClassSchema.getRiskCode());
		tLPAccMoveGetSchema.setAccType(aLPInsureAccClassSchema.getAccType());
		// tLPAccMoveGetSchema.setDutyCode(aDutyCode);
		// tLPAccMoveGetSchema.setGetDutyCode(aGetDutyCode);
		// tLPAccMoveGetSchema.setMoneyMoveType("");
		// tLPAccMoveGetSchema.setAccMoveBala("");
		// tLPAccMoveGetSchema.setAccMoveRate("");
		tLPAccMoveGetSchema.setAccMoveUnit("");
		// tLPAccMoveGetSchema.setAccMoveType(aAccMoveType);
		tLPAccMoveGetSchema.setOperator(mGlobalInput.Operator);
		tLPAccMoveGetSchema.setMakeDate(currDate);
		tLPAccMoveGetSchema.setMakeTime(currTime);
		tLPAccMoveGetSchema.setModifyDate(currDate);
		tLPAccMoveGetSchema.setModifyTime(currTime);
		return tLPAccMoveGetSchema;
	}

	/**
	 * 得到变更后的给付责任
	 * 
	 * @param aLCGetSchema
	 * @param aLPGetSchema
	 * @return
	 */
	private LPGetSchema initLPGet(LCGetSchema aLCGetSchema,
			LPGetSchema aLPGetSchema) {
		Reflections tRef = new Reflections();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		tRef.transFields(tLPGetSchema, aLCGetSchema);
		tLPGetSchema.setEdorNo(aLPGetSchema.getEdorNo());
		tLPGetSchema.setEdorType(aLPGetSchema.getEdorType());
		LPGetBL tLPGetBL = new LPGetBL();
		LCGetSchema tLCGetSchema = new LCGetSchema();

		if (!tLPGetBL.queryLPGet(tLPGetSchema))
			return tLPGetSchema;

		tLPGetSchema.setSchema(tLPGetBL.getSchema());
		tLPGetSchema.setAddRate(aLPGetSchema.getAddRate());
		tLPGetSchema.setGetDutyKind(aLPGetSchema.getGetDutyKind());
		tLPGetSchema.setGetStartDate(aLPGetSchema.getGetStartDate());
		tLPGetSchema.setGetEndDate(aLPGetSchema.getGetEndDate());
		tLPGetSchema.setActuGet(aLPGetSchema.getActuGet());
		tLPGetSchema.setGetIntv(aLPGetSchema.getGetIntv());

		// display
		logger.debug("----------------------Get info--------------");
		// tRef.printFields(tLCGetSchema);
		return tLPGetSchema;
	}

	/**
	 * 查询业务处理
	 * 
	 * @return
	 */
	private boolean queryData() {
		if (this.getOperate().equals("QUERY||LPGET")) {
			String tReturn;
			LPGetSet tLPGetSet = new LPGetSet();
			LPGetSet qryLPGetSet = new LPGetSet();
			LPGetSchema tLPGetSchema = new LPGetSchema();
			tLPGetSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setPolNo(aLPEdorItemSchema.getPolNo());
			tLPGetSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			// tLPGetSchema.setLiveGetType("0");
			// tLPGetSet = tLPGetDB.query();

			LPGetBL tLPGetBL = new LPGetBL();
			tLPGetBL.queryLPGet(tLPGetSchema);

			// LCGetSet tLCGetSet = new LCGetSet();
			// LCGetDB tLCGetDB = new LCGetDB();
			// tLCGetDB.setPolNo(aLPEdorItemSchema.getPolNo());
			// tLCGetDB.setLiveGetType("0");
			// //不是到该功能与催付标志的关系，为了进行，先去掉，by Minim at 2004-1-15
			// // tLCGetDB.setUrgeGetFlag("N");
			// tLCGetDB.setCanGet("1");
			// tLCGetSet = tLCGetDB.query();
			// if (tLCGetSet.size()<=0)
			// {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "PEdorGADetailBL";
			// tError.functionName = "queryData";
			// tError.errorMessage = "该保单不用进行给付申请!";
			// this.mErrors .addOneError(tError) ;
			// return false;
			// }
			// else
			// {
			// for (int i=1;i<=tLCGetSet.size();i++)
			// {
			// LPGetSchema tLPGetSchema = new LPGetSchema();
			// if (tLPGetSet.size()>0)
			// {
			// for (int j=1 ;j<=tLPGetSet.size();j++)
			// {
			// if
			// (tLCGetSet.get(i).getDutyCode().equals(tLPGetSet.get(j).getDutyCode())&&tLCGetSet.get(i).getGetDutyCode().equals(tLPGetSet.get(j).getGetDutyCode()))
			// continue;
			// Reflections tReflections = new Reflections();
			// tReflections.transFields(tLPGetSchema,tLCGetSet.get(i));
			// LPGetBL tLPGetBL = new LPGetBL();
			// tLPGetBL.queryLPGet(tLPGetSchema);
			// tLPGetSet.add(tLPGetBL.getSchema());
			// }
			// }
			// else
			// {
			// Reflections tReflections = new Reflections();
			// tReflections.transFields(tLPGetSchema,tLCGetSet.get(i));
			// tLPGetSet.add(tLPGetSchema);
			// }
			// }
			// }
			logger.debug("---size:" + tLPGetSet.size());
			tReturn = tLPGetSet.encode();
			mResult.clear();
			mResult.add(tReturn);
		} else if (this.getOperate().equals("QUERY||ACCOUNT")) {
			mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
					"LPGetSchema", 0);

			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

			tLPEdorItemDB.setSchema(aLPEdorItemSchema);
			if (tLPEdorItemDB.getInfo())
				mLPEdorItemSchema = tLPEdorItemDB.getSchema();
			else {
				mLPEdorItemSchema.setEdorValiDate(mLPGrpEdorItemSchema
						.getEdorValiDate());
			}
			String tReturn;

			String aBalanceDate = mLPEdorItemSchema.getEdorValiDate();

			if (mLPGetSchema.getGetStartDate() == null
					|| mLPGetSchema.getGetStartDate().trim().equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGADetailBL";
				tError.functionName = "queryData";
				tError.errorMessage = "起领日期未确定,该保单不能作帐户转换!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 给付金帐户关联
			LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
			LCGetToAccDB tLCGetToAccDB = new LCGetToAccDB();
			tLCGetToAccDB.setPolNo(mLPGetSchema.getPolNo());
			tLCGetToAccDB.setDutyCode(mLPGetSchema.getDutyCode());
			tLCGetToAccDB.setGetDutyCode(mLPGetSchema.getGetDutyCode());
			tLCGetToAccDB.setDealDirection("1");
			tLCGetToAccSet = tLCGetToAccDB.query();
			if (tLCGetToAccSet.size() > 0) {
				LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
				for (int i = 1; i <= tLCGetToAccSet.size(); i++) {
					LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
					LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();

					tLCInsureAccClassDB.setPolNo(tLCGetToAccSet.get(i)
							.getPolNo());
					tLCInsureAccClassDB.setInsuAccNo(tLCGetToAccSet.get(i)
							.getInsuAccNo());
					// tLCInsureAccClassDB.setAccType("002");//目前仅限于个人缴费帐户
					tLCInsureAccClassSet = tLCInsureAccClassDB.query();
					for (int j = 1; j <= tLCInsureAccClassSet.size(); j++) {
						LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
						tLPInsureAccClassDB.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						tLPInsureAccClassDB.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPInsureAccClassDB.setPolNo(mLPEdorItemSchema
								.getPolNo());
						tLPInsureAccClassDB.setInsuAccNo(tLCInsureAccClassSet
								.get(j).getInsuAccNo());
						// remark by pq
						// tLPInsureAccClassDB.setOtherNo(tLCInsureAccClassSet.get(j).getOtherNo());
						if (!tLPInsureAccClassDB.getInfo()) {
							// 帐户结息
							AccountManage tAccountManage = new AccountManage();
							// remark by pq
							// tAccountManage.getAccBalance(tLCInsureAccClassSet.get(j).getOtherNo(),tLCInsureAccClassSet.get(j).getInsuAccNo(),tLCInsureAccClassSet.get(j).getPolNo(),aBalanceDate,"Y","D");
							// aLCInsureAccClassSet.add(tAccountManage.getInsureAccClass().get(1));
						} else {
							Reflections tReflections = new Reflections();
							LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
							tReflections.transFields(tLCInsureAccClassSchema,
									tLPInsureAccClassDB.getSchema());
							aLCInsureAccClassSet.add(tLCInsureAccClassSchema);
						}
					}
				}
				tReturn = aLCInsureAccClassSet.encode();
				logger.debug("---return:" + tReturn);
				mResult.clear();
				mResult.add(tReturn);
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGADetailBL";
				tError.functionName = "queryData";
				tError.errorMessage = "没有给付金关联帐户!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else if (this.getOperate().equals("QUERY||MANAGE")) {
			mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
					"LPGetSchema", 0);
			aLCInsureAccClassSet = (LCInsureAccClassSet) mInputData
					.getObjectByObjectName("LCInsureAccClassSet", 0);
			LCInsureAccClassSchema tLCInsureAccClassSchema = aLCInsureAccClassSet
					.get(1);

			LPAccMoveGetSet tLPAccMoveSet = new LPAccMoveGetSet();
			LPAccMoveGetDB tLPAccMoveGetDB = new LPAccMoveGetDB();
			tLPAccMoveGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAccMoveGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAccMoveGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
			tLPAccMoveGetDB.setOtherNo(mLPGetSchema.getGetDutyCode());
			tLPAccMoveGetDB
					.setInsuAccNo(tLCInsureAccClassSchema.getInsuAccNo());
			// remark by pq
			// tLPAccMoveGetDB.setSubInsuAccNo(tLCInsureAccClassSchema.getOtherNo());
			tLPAccMoveSet = tLPAccMoveGetDB.query();
			String tReturn = tLPAccMoveSet.encode();
			logger.debug("---return:" + tReturn);
			mResult.clear();
			mResult.add(tReturn);
		}
		return true;
	}

	/**
	 * 得到个人申请主表记录
	 * 
	 * @param aLPPolSchema
	 * @return
	 */
	private LPEdorItemSchema initLPEdorItemSchema(LPPolSchema aLPPolSchema) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		// remark by pq
		// tLPEdorItemSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// tLPEdorItemSchema.setGrpPolNo(mLPGrpEdorItemSchema.getGrpPolNo());
		// tLPEdorItemSchema.setEdorValiDate(mLPGrpEdorItemSchema.getEdorValiDate());
		// tLPEdorItemSchema.setEdorAppDate(mLPGrpEdorItemSchema.getEdorAppDate());
		// tLPEdorItemSchema.setPolNo(aLPPolSchema.getPolNo());
		// tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// tLPEdorItemSchema.setContNo(aLPPolSchema.getContNo());
		// tLPEdorItemSchema.setInsuredNo(aLPPolSchema.getInsuredNo());
		// tLPEdorItemSchema.setInsuredName(aLPPolSchema.getInsuredName());
		// tLPEdorItemSchema.setPaytoDate(aLPPolSchema.getPaytoDate());
		// tLPEdorItemSchema.setSumPrem(aLPPolSchema.getSumPrem());
		// tLPEdorItemSchema.setEdorState("1");
		// tLPEdorItemSchema.setUWState("0");
		// tLPEdorItemSchema.setChgPrem("0");
		// tLPEdorItemSchema.setChgAmnt("0");
		// tLPEdorItemSchema.setChgGetAmnt("0");
		// tLPEdorItemSchema.setGetMoney("0");
		// tLPEdorItemSchema.setGetInterest("0");
		// tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		// tLPEdorItemSchema.setManageCom(aLPPolSchema.getManageCom());
		// LPEdorItemBL tLPEdorItemBL=new LPEdorItemBL();
		// tLPEdorItemBL.setSchema(tLPEdorItemSchema);
		// tLPEdorItemBL.setDefaultFields();
		// tLPEdorItemSchema=tLPEdorItemBL.getSchema();
		return tLPEdorItemSchema;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mInputData.clear();
		mInputData.addElement(mLPGetSchema);
		mInputData.addElement(mLPAccMoveGetSet);
		mInputData.addElement(mLPEdorItemSchema);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("delete from lpaccmoveget where edorno='?edorno?' and edortype='?edortype?'" + " and polno='?polno?'");
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("polno", mLPEdorItemSchema.getPolNo());
		map.put(sqlbv, "DELETE");
		map.put(mLPAccMoveGetSet, "INSERT");
		return true;
	}

	private static void main(String[] args) {

	}
}
