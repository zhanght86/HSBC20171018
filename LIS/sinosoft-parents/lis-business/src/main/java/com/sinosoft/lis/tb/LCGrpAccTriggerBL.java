package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMAccTriggerDB;
import com.sinosoft.lis.db.LMRiskAccPayDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpAccTriggerSchema;
import com.sinosoft.lis.schema.LMAccTriggerSchema;
import com.sinosoft.lis.schema.LMRiskAccPaySchema;
import com.sinosoft.lis.vschema.LCGrpAccTriggerSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMAccTriggerSet;
import com.sinosoft.lis.vschema.LMRiskAccPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 承保帐户触发器定义类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:帐户触发器定义
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 */
public class LCGrpAccTriggerBL {
private static Logger logger = Logger.getLogger(LCGrpAccTriggerBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 业务处理相关变量 */
	LCGrpAccTriggerSet mLCGrpAccTriggerSet = new LCGrpAccTriggerSet();

	private String mGrpContNo = "";

	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值
	String mCurrentTime = PubFun.getCurrentTime();

	public LCGrpAccTriggerBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = cInputData;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutData()) {
			return false;
		}

		// 使用PubSubmit执行数据库操作
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(this.mInputData, mOperate)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAccTriggerBL";
			tError.functionName = "submitDat";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("End LCGrpAccTriggerBL Submit...");
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LCGrpAccTriggerSet tLCGrpAccTriggerSet = new LCGrpAccTriggerSet();
		try {
			LCGrpAccTriggerSchema tLCGrpAccTriggerSchema = new LCGrpAccTriggerSchema();
			for (int i = 1; i <= mLCGrpAccTriggerSet.size(); i++) {
				tLCGrpAccTriggerSchema = mLCGrpAccTriggerSet.get(i);
				logger.debug("kkkkkkkkkkkkkkkkkkkk:payplancode:"
						+ tLCGrpAccTriggerSchema.getPayPlanCode());
				// 从账户触发器计算方法定义提取数据
				LMAccTriggerDB tLMAccTriggerDB = new LMAccTriggerDB();
				tLMAccTriggerDB.setInsuAccNo(tLCGrpAccTriggerSchema
						.getInsuAccNo());
				tLMAccTriggerDB.setPayPlanCode(tLCGrpAccTriggerSchema
						.getPayPlanCode());
				tLMAccTriggerDB.setActionCalMode("02");
				LMAccTriggerSet tLMAccTriggerSet = new LMAccTriggerSet();
				tLMAccTriggerSet = tLMAccTriggerDB.query();
				LMAccTriggerSchema tLMAccTriggerSchema = new LMAccTriggerSchema();
				logger.debug(tLMAccTriggerSet.size());
				if (tLMAccTriggerSet.size() > 0) {
					tLMAccTriggerSchema = tLMAccTriggerSet.get(1);
					// logger.debug(tLMAccTriggerSchema.getAccType());
					tLCGrpAccTriggerSchema.setAccType(tLMAccTriggerSchema
							.getAccType());
					tLCGrpAccTriggerSchema.setActionCalMode(tLMAccTriggerSchema
							.getActionCalMode());
					tLCGrpAccTriggerSchema
							.setActionCalModeType(tLMAccTriggerSchema
									.getActionCalModeType());
					tLCGrpAccTriggerSchema.setActionCalCode(tLMAccTriggerSchema
							.getActionCalCode());
					tLCGrpAccTriggerSchema.setValue(tLMAccTriggerSchema
							.getValue());
					tLCGrpAccTriggerSchema.setCompareValue(tLMAccTriggerSchema
							.getCompareValue());
				}
				// 从险种保险帐户缴费表提取信息
				LMRiskAccPayDB tLMRiskAccPayDB = new LMRiskAccPayDB();
				tLMRiskAccPayDB.setRiskCode(tLCGrpAccTriggerSchema
						.getRiskCode());
				tLMRiskAccPayDB.setInsuAccNo(tLCGrpAccTriggerSchema
						.getInsuAccNo());
				tLMRiskAccPayDB.setPayPlanCode(tLCGrpAccTriggerSchema
						.getPayPlanCode());
				LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
				tLMRiskAccPaySet = tLMRiskAccPayDB.query();
				LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
				if (tLMRiskAccPaySet.size() > 0) {
					tLMRiskAccPaySchema = tLMRiskAccPaySet.get(1);
					tLCGrpAccTriggerSchema
							.setRiskAccPayName(tLMRiskAccPaySchema
									.getRiskAccPayName());

				}
				tLCGrpAccTriggerSchema.setChgType("0"); // 帐户变动类型：“0”
				tLCGrpAccTriggerSchema.setChgOperationType("02"); // 账户变动业务类型:"02"分红
				tLCGrpAccTriggerSchema.setTriggerOrder(1); // ????????????????????
				tLCGrpAccTriggerSchema.setAction("01"); // 触发动作："01" 移动
				tLCGrpAccTriggerSchema.setActionObject("00"); // 处理对象 ：”00“ 自身
				tLCGrpAccTriggerSchema.setToPolNo("111111111111111");
				tLCGrpAccTriggerSchema.setActionCond("p");
				tLCGrpAccTriggerSchema.setToDutycode("212121");
				tLCGrpAccTriggerSchema.setToInsuAccNo("000008");

				tLCGrpAccTriggerSchema.setToPayPlanCode(tLCGrpAccTriggerSchema
						.getPayPlanCode());

				if (tLCGrpAccTriggerSchema.getToObjectType().equals("00")) { // 抵缴保费
					tLCGrpAccTriggerSchema.setToObject("00"); // 00-自身
					tLCGrpAccTriggerSchema
							.setToInsuAccNo(tLCGrpAccTriggerSchema
									.getInsuAccNo());
				} else if (tLCGrpAccTriggerSchema.getToObjectType()
						.equals("01")) { // 归属对应红利帐户
					tLCGrpAccTriggerSchema.setToObject("01"); // 01-规定账户
					tLCGrpAccTriggerSchema.setToInsuAccNo("000002");
				} else if (tLCGrpAccTriggerSchema.getToObjectType()
						.equals("02")) { // 归属企业红利帐户
					tLCGrpAccTriggerSchema.setToObject("01"); // 02-规定账户
					tLCGrpAccTriggerSchema.setToInsuAccNo("000002");
					// 查询并设置公共帐户的PolNo
					LCPolDB tmpLCPolDB = new LCPolDB();
					tmpLCPolDB.setGrpContNo(mGrpContNo);
					tmpLCPolDB.setPolTypeFlag("2");
					tmpLCPolDB
							.setRiskCode(tLCGrpAccTriggerSchema.getRiskCode());
					LCPolSet tmpLCPolSet = new LCPolSet();
					tmpLCPolSet = tmpLCPolDB.query();
					if (tmpLCPolSet.size() <= 0) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LCGrpAccTriggerBL";
						tError.functionName = "prepareData";
						tError.errorMessage = "本保单未曾定义公共帐户，红利分配方式不可以选择企业红利帐户！。";
						this.mErrors.addOneError(tError);
						return false;
					} else {
						tLCGrpAccTriggerSchema.setToPolNo(tmpLCPolSet.get(1)
								.getPolNo());
					}
				}

				tLCGrpAccTriggerSchema.setOperator(mGlobalInput.Operator);
				tLCGrpAccTriggerSchema.setMakeDate(PubFun.getCurrentDate());
				tLCGrpAccTriggerSchema.setMakeTime(PubFun.getCurrentTime());
				tLCGrpAccTriggerSchema.setModifyDate(PubFun.getCurrentDate());
				tLCGrpAccTriggerSchema.setModifyTime(PubFun.getCurrentTime());

				tLCGrpAccTriggerSet.add(tLCGrpAccTriggerSchema);
			}
			String tDelSql = "delete from lcgrpacctrigger where grppolno='"
					+ tLCGrpAccTriggerSchema.getGrpPolNo() + "'";
			mMap.put(tDelSql, "DELETE");
			mMap.put(tLCGrpAccTriggerSet, "DELETE&INSERT");
			mResult.add(tLCGrpAccTriggerSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAccTriggerBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mLCGrpAccTriggerSet = ((LCGrpAccTriggerSet) mInputData
				.getObjectByObjectName("LCGrpAccTriggerSet", 0));
		if ((mGlobalInput == null) || (mLCGrpAccTriggerSet == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAccTriggerUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLCGrpAccTriggerSet.size() > 0) {
			mGrpContNo = mLCGrpAccTriggerSet.get(1).getGrpContNo();
		}

		return true;
	}

	/**
	 * 往后台传送准备好的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutData() {
		mInputData.clear();
		mInputData.add(mMap);
		return true;
	}

	/**
	 * 返回集
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}
}
