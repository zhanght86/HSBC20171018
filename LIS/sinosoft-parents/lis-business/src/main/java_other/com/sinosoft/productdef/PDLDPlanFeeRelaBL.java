/**
 * <p>Title: PDDutyGetClm</p>
 * <p>Description: 责任给付生存</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sun.jimi.core.encoder.jpg.Mcu;

/**
 * 程序名称：累加器BL 程序功能：累加器配置 创建时间：2016-5-26 创建人：王海超
 */

public class PDLDPlanFeeRelaBL implements BusinessService {
	private static Logger logger = Logger.getLogger(PDLDPlanFeeRelaBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();

	private PD_LDPlanFeeRelaSchema mPD_LDPlanFeeRelaSchema = new PD_LDPlanFeeRelaSchema();

	private String mCalculatorCode = "";

	public PDLDPlanFeeRelaBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		// 获取数据
		if (!getInputData(cInputData)) {
			this.mResult.add(0, this.mErrors.getFirstError());
			return false;
		}
		// 数据校验
		if (!check()) {
			this.mResult.add(0, this.mErrors.getFirstError());
			return false;
		}
		// 数据处理
		if (!dealData()) {
			CError tError = new CError();
			tError.moduleName = "PDLDPlanFeeRelaBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败PDLCalculatorBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, "")) {
			CError tError = new CError();
			tError.moduleName = "PDLDPlanFeeRelaBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "数据提交失败。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		try {
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
			this.mPD_LDPlanFeeRelaSchema = (PD_LDPlanFeeRelaSchema) cInputData
					.getObjectByObjectName("PD_LDPlanFeeRelaSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "获取数据出错!");

			return false;
		}

		return true;
	}

	private boolean dealData() {
		try {

			if (this.mOperate.equals("INSERT")) {

				if (mPD_LDPlanFeeRelaSchema != null) {
					map.put(mPD_LDPlanFeeRelaSchema, "INSERT");
				}
			}
			if (this.mOperate.equals("UPDATE")) {

				if (mPD_LDPlanFeeRelaSchema != null) {
					map.put(mPD_LDPlanFeeRelaSchema, "DELETE&INSERT");
				}
			}
			if (this.mOperate.equals("DELETE")) {

				if (mPD_LDPlanFeeRelaSchema != null) {
					map.put(mPD_LDPlanFeeRelaSchema, "DELETE");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "获取数据出错!");
			return false;
		}

		return true;
	}

	private boolean check() {
		// 数据校验
		// 判断操作类型
		if (this.mOperate != null && !"".equals(this.mOperate)
				&& this.mOperate.equals("INSERT")) {
			PD_LDPlanFeeRelaDB tPD_LDPlanFeeRelaDB = new PD_LDPlanFeeRelaDB();
			tPD_LDPlanFeeRelaDB.setPlanCode(mPD_LDPlanFeeRelaSchema
					.getPlanCode());
			tPD_LDPlanFeeRelaDB.setRiskCode(mPD_LDPlanFeeRelaSchema
					.getRiskCode());
			tPD_LDPlanFeeRelaDB.setDutyCode(mPD_LDPlanFeeRelaSchema
					.getDutyCode());
			tPD_LDPlanFeeRelaDB.setGetDutyCode(mPD_LDPlanFeeRelaSchema
					.getGetDutyCode());
			tPD_LDPlanFeeRelaDB
					.setFeeCode(mPD_LDPlanFeeRelaSchema.getFeeCode());

			if (tPD_LDPlanFeeRelaDB.getInfo()) {
				buildError("check", "该数据在PD_LDPlanFeeRela表中已存在，请重新操作。");
				return false;
			} else {
				mPD_LDPlanFeeRelaSchema.setOperator(mGlobalInput.Operator);
				mPD_LDPlanFeeRelaSchema.setMakeDate(PubFun.getCurrentDate());
				mPD_LDPlanFeeRelaSchema.setMakeTime(PubFun.getCurrentTime());
				mPD_LDPlanFeeRelaSchema.setModifyDate(PubFun.getCurrentDate());
				mPD_LDPlanFeeRelaSchema.setModifyTime(PubFun.getCurrentTime());
				mPD_LDPlanFeeRelaSchema.setManageCom(mGlobalInput.ManageCom);
			}

		}
		if (this.mOperate != null && !"".equals(this.mOperate)
				&& this.mOperate.equals("UPDATE")) {

			PD_LDPlanFeeRelaDB tPD_LDPlanFeeRelaDB = new PD_LDPlanFeeRelaDB();
			tPD_LDPlanFeeRelaDB.setPlanCode(mPD_LDPlanFeeRelaSchema
					.getPlanCode());
			tPD_LDPlanFeeRelaDB.setRiskCode(mPD_LDPlanFeeRelaSchema
					.getRiskCode());
			tPD_LDPlanFeeRelaDB.setDutyCode(mPD_LDPlanFeeRelaSchema
					.getDutyCode());
			tPD_LDPlanFeeRelaDB.setGetDutyCode(mPD_LDPlanFeeRelaSchema
					.getGetDutyCode());
			tPD_LDPlanFeeRelaDB
					.setFeeCode(mPD_LDPlanFeeRelaSchema.getFeeCode());

			if (!tPD_LDPlanFeeRelaDB.getInfo()) {
				buildError("check", "该数据在PD_LDPlanFeeRela表中不存在，请重新操作。");
				return false;
			} else {
				PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema = tPD_LDPlanFeeRelaDB
						.getSchema();
				tPD_LDPlanFeeRelaSchema.setFeeType(mPD_LDPlanFeeRelaSchema
						.getFeeType());
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitAnnual(mPD_LDPlanFeeRelaSchema
								.getMoneyLimitAnnual());
				tPD_LDPlanFeeRelaSchema
						.setCountLimitAnnual(mPD_LDPlanFeeRelaSchema
								.getCountLimitAnnual());
				tPD_LDPlanFeeRelaSchema
						.setDaysLimitAnnual(mPD_LDPlanFeeRelaSchema
								.getDaysLimitAnnual());
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitEveryTime(mPD_LDPlanFeeRelaSchema
								.getMoneyLimitEveryTime());
				tPD_LDPlanFeeRelaSchema
						.setDaysLimitEveryTime(mPD_LDPlanFeeRelaSchema
								.getDaysLimitEveryTime());
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitEveryDay(mPD_LDPlanFeeRelaSchema
								.getMoneyLimitEveryDay());
				tPD_LDPlanFeeRelaSchema
						.setPayMoneyEveryDay(mPD_LDPlanFeeRelaSchema
								.getPayMoneyEveryDay());
				tPD_LDPlanFeeRelaSchema.setObsPeriod(mPD_LDPlanFeeRelaSchema
						.getObsPeriod());
				tPD_LDPlanFeeRelaSchema.setObsPeriodUn(mPD_LDPlanFeeRelaSchema
						.getObsPeriodUn());
				tPD_LDPlanFeeRelaSchema.setStandFlag1(mPD_LDPlanFeeRelaSchema.getStandFlag1());//是否扣除自付比例
				tPD_LDPlanFeeRelaSchema.setStandFlag2(mPD_LDPlanFeeRelaSchema.getStandFlag2());//自付比例
				tPD_LDPlanFeeRelaSchema.setPreAuthFlag(mPD_LDPlanFeeRelaSchema.getPreAuthFlag());//免赔额

				mPD_LDPlanFeeRelaSchema = tPD_LDPlanFeeRelaSchema;
			}

		}
		if (this.mOperate != null && !"".equals(this.mOperate)
				&& this.mOperate.equals("DELETE")) {

			PD_LDPlanFeeRelaDB tPD_LDPlanFeeRelaDB = new PD_LDPlanFeeRelaDB();
			tPD_LDPlanFeeRelaDB.setPlanCode(mPD_LDPlanFeeRelaSchema
					.getPlanCode());
			tPD_LDPlanFeeRelaDB.setRiskCode(mPD_LDPlanFeeRelaSchema
					.getRiskCode());
			tPD_LDPlanFeeRelaDB.setDutyCode(mPD_LDPlanFeeRelaSchema
					.getDutyCode());
			tPD_LDPlanFeeRelaDB.setGetDutyCode(mPD_LDPlanFeeRelaSchema
					.getGetDutyCode());
			tPD_LDPlanFeeRelaDB
					.setFeeCode(mPD_LDPlanFeeRelaSchema.getFeeCode());

			if (!tPD_LDPlanFeeRelaDB.getInfo()) {
				buildError("check", "该数据在PD_LDPlanFeeRela表中不存在，请重新操作。");
				return false;
			} else {
				PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema = tPD_LDPlanFeeRelaDB
						.getSchema();
				mPD_LDPlanFeeRelaSchema = tPD_LDPlanFeeRelaSchema;
			}
		}
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCExchangeRateBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PDLDPlanFeeRelaBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
