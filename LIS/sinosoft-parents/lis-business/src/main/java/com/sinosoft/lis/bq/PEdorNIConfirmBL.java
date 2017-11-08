package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.tb.LCContSignBL;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
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
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @ReWrite: ZhangRong
 * @modify by Lizhuo at 2005-12-13
 * @version 1.0
 */

public class PEdorNIConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorNIConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 签单调用传入的集体保单号 */
	private String mInputGrpPolNo = "";

	/** 传入的业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 传出的业务数据 */
	private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();

	public PEdorNIConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GEdorNIDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean dealData() {
		try {

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setAppFlag("2");
			tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
			LCContSet tLCContSet = tLCContDB.query();
			if (tLCContSet.size() == 0) {
				mErrors.addOneError(new CError("获取保单数据失败"));
				return false;
			}
			// 如果是投保单，则进行签单，否则直接保全确认，主要解决因事务不能一致，而导致签单成功而保全失败后，无法继续操作的问题
			if (!tLCContSet.get(1).getAppFlag().equals("1")) {
				// 准备调用签单需要的数据
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("EdorNo", mLPEdorItemSchema
						.getEdorNo());
				tTransferData.setNameAndValue("EdorAcceptNo", mLPEdorItemSchema
						.getEdorAcceptNo());
				tTransferData.setNameAndValue("EdorType", mLPEdorItemSchema
						.getEdorType());

				VData vContPub = new VData();
				mGlobalInput.ComCode = mGlobalInput.ManageCom;
				vContPub.add(mGlobalInput);
				vContPub.add(tLCContSet);
				vContPub.add(tTransferData);

				// 准备签单数据
				logger.debug("Start 准备签单数据...");

				LCContSignBL tLCContSignBL = new LCContSignBL(); // 调用个单合同签单程序
				if (!tLCContSignBL.submitData(vContPub, "INSERT||ENDORSE")) {
					mErrors.copyAllErrors(tLCContSignBL.mErrors);
					mErrors.addOneError(new CError("新增保单签单失败！"));
					return false;
				}

				VData tResultData = tLCContSignBL.getResult();
				MMap tMap = (MMap) tResultData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					mErrors.copyAllErrors(tLCContSignBL.mErrors);
					mErrors.addOneError(new CError("获得签单程序返回数据失败!"));
				}
				mMap.add(tMap);

			}

			// 设置保全生效日为保全确认日
			mLPEdorItemSchema.setEdorState("0");
			mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GEdorNIDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获取批改补退费表数据
	 * 
	 * @return
	 */
	private LJAGetEndorseSet verifyLJAGetEndorse() {
		String strSql = "select * from LJAGetEndorse where EndorsementNo='?EndorsementNo?'"
				+ " and FeeOperationType='?FeeOperationType?'" + " and ContNo='?ContNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		return tLJAGetEndorseDB.executeQuery(sqlbv);
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 检查主附险--保存主附险的团单号
	 * 
	 * @param tLPEdorItemSchema
	 * @return
	 */
	private boolean checkRiskCode(LPEdorItemSchema tLPEdorItemSchema) {
		mInputGrpPolNo = "";
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
		if (tLCPolDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "GEdorNIDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有查询到投保单! " + tLPEdorItemSchema.getPolNo();
			this.mErrors.addOneError(tError);
			return false;
		}
		LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
		if (tLMRiskAppDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GEdorNIDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "LMRiskApp表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();

		// 如果是附加险
		if (tLMRiskAppSchema.getSubRiskFlag().equals("S")) {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setPrtNo(tLCPolSchema.getPrtNo());
			tLCGrpPolDB.setRiskCode(tLCPolSchema.getRiskCode());
			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
			if (tLCGrpPolSet.size() == 0) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GEdorNIDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "LCGrpPol表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mInputGrpPolNo = tLCGrpPolSet.get(1).getGrpPolNo();
		} else {
			mInputGrpPolNo = tLPEdorItemSchema.getGrpContNo();
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		tGI.Operator = "lee";
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setContNo("130110000015146");
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);
		VData tVD = new VData();
		tVD.add(tGI);
		tVD.add(tLPEdorItemSchema);
		PEdorNIConfirmBL tPE = new PEdorNIConfirmBL();
		tPE.submitData(tVD, "");

	}
}
