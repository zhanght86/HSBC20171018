package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LJSGetEndorseBL;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单遗失补发申请确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class GrpEdorWTAppConfirmBL implements EdorAppConfirm {
private static Logger logger = Logger.getLogger(GrpEdorWTAppConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorWTAppConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
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
		if (!prepareData()) {
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
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorLRAppConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() != 1) {
			mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询保全项目信息失败！"));
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		if (!tLPGrpEdorMainDB.getInfo()) {
			mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
			mErrors.addOneError(new CError("查询保全信息失败！"));
			return false;
		}
		mLPGrpEdorMainSchema = tLPGrpEdorMainDB.getSchema();

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 设置批改补退费表
		// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		//
		// tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
		// //给付通知书号码
		// tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.
		// getEdorNo());
		// tLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// tLJSGetEndorseSchema.setPolNo("000000");
		// tLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema.
		// getEdorType());
		// tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.
		// getEdorValiDate());
		// tLJSGetEndorseSchema.setGetMoney(mLPGrpEdorItemSchema.getGetMoney());
		// tLJSGetEndorseSchema.setFeeOperationType("LR");
		// tLJSGetEndorseSchema.setFeeFinaType("BF"); //补费
		// tLJSGetEndorseSchema.setPayPlanCode("00000000"); //无作用
		// tLJSGetEndorseSchema.setDutyCode("0"); //无作用，但一定要，转ljagetendorse时非空
		// tLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
		// //无作用
		// tLJSGetEndorseSchema.setOtherNoType("3"); //保全给付
		// tLJSGetEndorseSchema.setGetFlag("0");
		// tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		// tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		// tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		// tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		// tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		// mLPGrpEdorItemSchema.setEdorState("2");
		// mLPGrpEdorItemSchema.setUWFlag("0");

		// 得到需要退保的保单（主附险），去除作犹豫期退保的保单
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolSet == null || tLPGrpPolSet.size() < 1) {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
			if (tLCGrpPolSet == null || tLCGrpPolSet.size() < 1) {
				CError tError = new CError();
				tError.errorMessage = "集体险种表中没有对应的记录!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				Reflections ref = new Reflections();
				tLPGrpPolSet.add(new LPGrpPolSchema());
				ref.transFields(tLPGrpPolSet, tLCGrpPolSet);
			}
		}

		for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
			// 生成批改交退费表
			// 退保累计交费
			LJSGetEndorseBL tLJSGetEndorseBL = new LJSGetEndorseBL();
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema
					.getEdorType());
			tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema
					.getEdorValiDate());
			tLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema
					.getGrpContNo());
			tLJSGetEndorseSchema.setGrpPolNo(tLPGrpPolSet.get(i).getGrpPolNo());
			tLJSGetEndorseSchema.setPolNo(tLPGrpPolSet.get(i).getGrpPolNo());
			tLJSGetEndorseSchema
					.setAppntNo(tLPGrpPolSet.get(i).getCustomerNo());
			tLJSGetEndorseSchema.setKindCode(tLPGrpPolSet.get(i).getKindCode());
			tLJSGetEndorseSchema.setRiskCode(tLPGrpPolSet.get(i).getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(tLPGrpPolSet.get(i)
					.getRiskVersion());
			tLJSGetEndorseSchema.setAgentCom(tLPGrpPolSet.get(i).getAgentCom());
			tLJSGetEndorseSchema.setAgentCode(tLPGrpPolSet.get(i)
					.getAgentCode());
			tLJSGetEndorseSchema.setAgentType(tLPGrpPolSet.get(i)
					.getAgentType());
			tLJSGetEndorseSchema.setAgentGroup(tLPGrpPolSet.get(i)
					.getAgentGroup());
			tLJSGetEndorseSchema.setGetMoney(-tLPGrpPolSet.get(i).getSumPrem());
			// 从描述表中获取财务接口类型
			String finType = BqCalBL.getFinType(mLPGrpEdorItemSchema
					.getEdorType(), "TF", tLPGrpPolSet.get(i).getGrpPolNo());
			if (finType.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.errorMessage = "在LDCode1中缺少保全财务接口转换类型编码!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLJSGetEndorseSchema.setFeeFinaType(finType);
			tLJSGetEndorseSchema.setDutyCode("0");
			tLJSGetEndorseSchema.setPayPlanCode("0");
			tLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setOtherNoType("3");
			tLJSGetEndorseSchema.setPolType("1");
			tLJSGetEndorseSchema.setSerialNo("");
			tLJSGetEndorseSchema.setGetFlag("1");
			tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
			tLJSGetEndorseSchema.setManageCom(tLPGrpPolSet.get(i)
					.getManageCom());
			tLJSGetEndorseBL.setSchema(tLJSGetEndorseSchema);
			tLJSGetEndorseBL.setDefaultFields();
			mLJSGetEndorseSet.add(tLJSGetEndorseBL.getSchema());

			// 主表信息更新
			// mLPGrpEdorItemSchema.setChgPrem(mLPGrpEdorItemSchema.getChgPrem()
			// + tLJSGetEndorseSchema.getGetMoney());
			mLPGrpEdorItemSchema.setGetMoney(mLPGrpEdorItemSchema.getGetMoney()
					+ tLJSGetEndorseSchema.getGetMoney());

			// 查找余额退还
			if (tLPGrpPolSet.get(i).getDif() > 0) {

				LJSGetEndorseBL tLJS = new LJSGetEndorseBL();
				tLJS.setSchema(tLJSGetEndorseBL.getSchema());
				// 从描述表中获取财务接口类型
				finType = BqCalBL.getFinType(
						mLPGrpEdorItemSchema.getEdorType(), "YE", tLPGrpPolSet
								.get(i).getGrpPolNo());
				if (finType.equals("")) {
					// @@错误处理
					CError tError = new CError();
					tError.errorMessage = "在LDCode1中缺少保全财务接口转换类型编码!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLJS.setFeeFinaType(finType);

				tLJS.setGetMoney(-tLPGrpPolSet.get(i).getDif());
				mLJSGetEndorseSet.add(tLJS);
				mLPGrpEdorItemSchema.setGetMoney(mLPGrpEdorItemSchema
						.getGetMoney()
						+ tLJS.getGetMoney());
				// logger.debug("--------allmoney"+mLPGrpEdorItemSchema.getGetMoney());
			}
		}

		// 暂时不处理帐户的平衡问题
		// 得到当前LPGrpEdorItem保单信息主表的状态，并更新状态为申请确认。
		// double getMoney = -mLPGrpEdorItemSchema.getGetMoney();
		// mLPGrpEdorItemSchema.setGetMoney(getMoney);
		mLPGrpEdorItemSchema.setEdorState("2");
		mLPGrpEdorItemSchema.setUWFlag("0");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(mLJSGetEndorseSet, "INSERT");
		map.put(mLPGrpEdorItemSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		tLPGrpEdorItemSchema.setEdorNo("43000000006");
		tLPGrpEdorItemSchema.setGrpContNo("240110000000085");
		tLPGrpEdorItemSchema.setEdorType("WT");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPGrpEdorItemSchema);

		GrpEdorWTAppConfirmBL tGrpEdorWTAppConfirmBL = new GrpEdorWTAppConfirmBL();
		if (!tGrpEdorWTAppConfirmBL.submitData(tVData, "INSERT")) {
			logger.debug(tGrpEdorWTAppConfirmBL.mErrors.getErrContent());
		}

	}

}
