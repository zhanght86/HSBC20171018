package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
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
 * Description: 团体保全集体下个人功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */
public class PGrpEdorXTDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorXTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPGrpContSchema mLPGrpContSchema = new LPGrpContSchema();
	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();

	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
	private LPContSet mLPContSet = new LPContSet();
	private LPPolSet mLPPolSet = new LPPolSet();

	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LJSGetEndorseSet saveLJSGetEndorseSet = new LJSGetEndorseSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections ref = new Reflections();
	private TransferData tTransferData = new TransferData();
	private String currDate = PubFun.getCurrentDate();
	private String currTime = PubFun.getCurrentTime();
	private MMap map = new MMap();

	public PGrpEdorXTDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 数据校验操作
		if (!checkData())
			return false;
		logger.debug("---End checkData---");

		// 数据准备操作
		if (mOperate.equals("INSERT||EDOR")) {
			if (!prepareData())
				return false;
			logger.debug("---End prepareData---");
		}
		// 数据准备操作
		if (mOperate.equals("DELETE||EDOR")) {
			if (!deleteData())
				return false;
		}

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mResult, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
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
			mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
					"LPEdorItemSet", 0);

			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorXTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性
	 * 
	 * @return
	 */
	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PInsuredBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}

		// 将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

		if (!tLPGrpEdorItemDB.getEdorState().trim().equals("1")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PInsuredBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return
	 */
	private boolean prepareData() {
		// 按个人保全主表进行处理LPEdorItem
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = mLPEdorItemSet.get(1);
			// ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
			// tLPEdorItemSchema.setPolNo("000000");
			tLPEdorItemSchema.setManageCom(mGlobalInput.ManageCom);
			tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			tLPEdorItemSchema.setUWFlag("0");
			String money = (String) tTransferData.getValueByName("GetMoney");
			tLPEdorItemSchema.setGetMoney(money);
			tLPEdorItemSchema.setMakeDate(currDate);
			tLPEdorItemSchema.setMakeTime(currTime);
			tLPEdorItemSchema.setModifyDate(currDate);
			tLPEdorItemSchema.setModifyTime(currTime);
			saveLPEdorItemSet.add(tLPEdorItemSchema);

			// 生成LPEdorMain表
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorAcceptNo(tLPEdorItemSchema.getEdorAcceptNo());
			tLPEdorMainDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPEdorMainDB.setContNo(tLPEdorItemSchema.getContNo());
			LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
			tLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询个人保全主表失败!");
				return false;
			}
			if (tLPEdorMainSet.size() == 0) {
				LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
				ref.transFields(tLPEdorMainSchema, tLPEdorItemSchema);
				tLPEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
				tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
				tLPEdorMainSchema.setGetMoney(money);
				tLPEdorMainSchema.setUWState("0");
				tLPEdorMainSchema.setMakeDate(currDate);
				tLPEdorMainSchema.setMakeTime(currTime);
				tLPEdorMainSchema.setModifyDate(currDate);
				tLPEdorMainSchema.setModifyTime(currTime);
				saveLPEdorMainSet.add(tLPEdorMainSchema);
			}

			// 生成批改补退费表
			mLJSGetEndorseSchema.setGetNoticeNo(tLPEdorItemSchema.getEdorNo()); // 给付通知书号码
			mLJSGetEndorseSchema
					.setEndorsementNo(tLPEdorItemSchema.getEdorNo());
			mLJSGetEndorseSchema.setContNo(tLPEdorItemSchema.getContNo());
			mLJSGetEndorseSchema.setGrpContNo(tLPEdorItemSchema.getGrpContNo());
			mLJSGetEndorseSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			mLJSGetEndorseSchema.setFeeOperationType(tLPEdorItemSchema
					.getEdorType());
			mLJSGetEndorseSchema
					.setGetDate(tLPEdorItemSchema.getEdorValiDate());
			mLJSGetEndorseSchema.setGetMoney(money);
			mLJSGetEndorseSchema.setFeeOperationType("XT");
			BqCalBL tBqCalBl = new BqCalBL();
			String feeFinaType = tBqCalBl.getFinType("XT", "TB",
					tLPEdorItemSchema.getContNo());
			if (feeFinaType.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.errorMessage = "在LDCode1中缺少保全财务接口转换类型编码!";
				this.mErrors.addOneError(tError);
				return false;
			}

			mLJSGetEndorseSchema.setFeeFinaType(feeFinaType);
			mLJSGetEndorseSchema.setPayPlanCode("00000000"); // 无作用
			mLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
			mLJSGetEndorseSchema.setOtherNo(tLPEdorItemSchema.getContNo()); // 无作用
			mLJSGetEndorseSchema.setOtherNoType("3");
			mLJSGetEndorseSchema.setGetFlag("0");
			mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
			mLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLPEdorItemSchema.getContNo());
			if (tLCContDB.getInfo()) {
				LCContSchema tLCContSchema = tLCContDB.getSchema();
				LPContSchema tLPContSchema = new LPContSchema();
				Reflections ref = new Reflections();
				ref.transFields(tLPContSchema, tLCContSchema);
				tLPContSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPContSchema.setEdorType(tLPEdorItemSchema.getEdorType());
				mLPContSet.add(tLPContSchema);
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolDB.setContNo(tLCContSchema.getContNo());
				tLCPolSet = tLCPolDB.query();
				for (int j = 0; j < tLCPolSet.size(); j++) {
					LCPolSchema tLCPolSchema = tLCPolSet.get(j + 1);
					LPPolSchema tLPPolSchema = new LPPolSchema();
					ref.transFields(tLPPolSchema, tLCPolSchema);
					tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					mLPPolSet.add(tLPPolSchema);
				}

				mLJSGetEndorseSchema.setAgentCode(tLCContDB.getAgentCode());
				mLJSGetEndorseSchema.setAgentCom(tLCContDB.getAgentCom());
				mLJSGetEndorseSchema.setAgentGroup(tLCContDB.getAgentGroup());
				mLJSGetEndorseSchema.setAgentType(tLCContDB.getAgentType());
			} else {
				CError tError = new CError();
				tError.errorMessage = "不存在合同号为" + tLPEdorItemSchema.getContNo()
						+ "的合同!";
				this.mErrors.addOneError(tError);
				return false;
			}
			String Smoney = (String) tTransferData.getValueByName("GetMoney");
			mLJSGetEndorseSchema.setGetMoney(Smoney);
			mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());

	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(mLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-14
			saveLJSGetEndorseSet.add(mLJSGetEndorseSchema);
		}

		map.put(mLPGrpEdorItemSchema, "UPDATE");
		// map.put(mLPGrpContSchema, "DELETE&INSERT");
		map.put(mLPGrpPolSet, "DELETE&INSERT");
		map.put(mLPContSet, "DELETE&INSERT");
		map.put(mLPPolSet, "DELETE&INSERT");
		map.put(saveLJSGetEndorseSet, "DELETE&INSERT");
		map.put(saveLPEdorItemSet, "INSERT");
		map.put(saveLPEdorMainSet, "INSERT");
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return
	 */
	private boolean deleteData() {

		String cotnNoStr = "";
		// 按个人保全主表进行处理
		logger.debug("mLPEdorItemSet.size()----:" + mLPEdorItemSet.size());
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			if (i != mLPEdorItemSet.size()) {
				cotnNoStr = cotnNoStr + "'" + mLPEdorItemSet.get(i).getContNo()
						+ "',";
			} else
				cotnNoStr = cotnNoStr + "'" + mLPEdorItemSet.get(i).getContNo()
						+ "'";
			logger.debug("edottype-----"
					+ mLPEdorItemSet.get(i).getEdorType());
			String deleteSql = "delete from lpedoritem where contno='"
					+ mLPEdorItemSet.get(i).getContNo() + "' and edorno='"
					+ mLPEdorItemSet.get(i).getEdorNo() + "'"
					+ " and edortype='XT' ";
			logger.debug("deleteSql:" + deleteSql);

			// mLPEdorItemSet.get(i).getEdorType() + "'";
			if (mLPEdorItemSet.get(i).getInsuredNo() != null
					&& !mLPEdorItemSet.get(i).getInsuredNo().equals("")
					&& !mLPEdorItemSet.get(i).getInsuredNo().equals("000000")) {
				cotnNoStr = cotnNoStr + "' and InsuredNo='"
						+ mLPEdorItemSet.get(i).getContNo() + "'";
				if (mLPEdorItemSet.get(i).getPolNo() != null
						&& !mLPEdorItemSet.get(i).getPolNo().equals("")
						&& !mLPEdorItemSet.get(i).getPolNo().equals("000000")) {
					cotnNoStr = cotnNoStr + "' and PolNo='"
							+ mLPEdorItemSet.get(i).getPolNo() + "'";
				}
			}
			// 删除个人批改项目
			map.put(deleteSql, "DELETE");

		}
		// 当个人批单主表没有批改项目时需要删掉个人批改主表
		String sql1 = "delete from LPEdorMain a where a.edorno='"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and a.contno in ("
				+ cotnNoStr
				+ ") and 0=(select count(1) from lpedoritem b where b.contno=a.contno "
				+ "and b.edorNo='" + mLPGrpEdorItemSchema.getEdorNo() + "') ";
		map.put(sql1, "UPDATE");
		mResult.clear();
		mResult.add(map);
		return true;
	}

}
