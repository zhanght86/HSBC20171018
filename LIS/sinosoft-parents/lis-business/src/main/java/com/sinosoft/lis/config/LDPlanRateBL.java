package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

import com.sinosoft.lis.db.LARateCommisionDB;
import com.sinosoft.lis.db.LAWageCalElementDB;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LARateCommisionBSchema;
import com.sinosoft.lis.schema.LARateCommisionSchema;
import com.sinosoft.lis.schema.LAWageCalElementSchema;
import com.sinosoft.lis.schema.LDPlanSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.vschema.LARateCommisionBSet;
import com.sinosoft.lis.vschema.LARateCommisionSet;
import com.sinosoft.lis.vschema.LAWageCalElementSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 产品组合佣金录入处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class LDPlanRateBL {
private static Logger logger = Logger.getLogger(LDPlanRateBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mInputData;
	private VData mResult = new VData();
	private MMap ResultMap = new MMap();

	private TransferData mTransferData = new TransferData();
	/** 业务处理相关变量 */
	private LDPlanSchema mLDPlanSchema = new LDPlanSchema();
	private LARateCommisionSchema mLARateCommisionSchema = new LARateCommisionSchema();
	/** 全局数据 */
	private GlobalInput mGI = new GlobalInput();
	private String LogManageCom;
	private String LogOperator;

	private String tCurrentDate = PubFun.getCurrentDate();
	private String tCurrentTime = PubFun.getCurrentTime();

	// 构造函数
	public LDPlanRateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 获取前台传入的数据
		if (!getInputData(cInputData)) {
			return false;
		}
		// 校验前台传入数据
		if (!checkData()) {
			return false;
		}
		// 处理前台传入的数据
		if (!dealData()) {
			return false;
		}
		// 提交保存处理完成的数据
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 获取操作员信息
		mGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		// 获取产品组合佣金率信息失败
		mLARateCommisionSchema = (LARateCommisionSchema) cInputData
				.getObjectByObjectName("LARateCommisionSchema", 0);

		if (mGI == null) {
			buildError("getInputData", " 获取前台传入操作员信息失败！！！");
			return false;
		}
		LogOperator = mGI.Operator;
		LogManageCom = mGI.ManageCom;

		if (mLARateCommisionSchema == null) {
			buildError("getInputData", " 获取前台传入产品组合佣金率信息失败！！！");
			return false;
		}

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		try {
			// 采用TransferData传参，
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("RiskCode", mLARateCommisionSchema
					.getRiskCode());// 产品组合代码
			tTransferData.setNameAndValue("ContPlancode",
					mLARateCommisionSchema.getRiskCode());// 产品组合代码
			tTransferData.setNameAndValue("RateCommision",
					mLARateCommisionSchema.getRate());// 产品组合费率
			tTransferData.setNameAndValue("LogManageCom", LogManageCom);// 操作员登录机构
			// 计算要素<此处无需该类传入参数，但是调用punfun/CheckFieldCom方法需要此类>
			FieldCarrier tFieldCarrier = new FieldCarrier();
			// 此处传入参数，用于查询算法
			LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
			tLMCheckFieldSchema.setRiskCode(mLARateCommisionSchema
					.getRiskCode());// 传入产品代码
			tLMCheckFieldSchema.setFieldName("CHKRATECOMMISION"); // 投保

			// 以下调用punfun/CheckFieldCom方法进行校验
			VData tVData = new VData();
			tVData.add(tFieldCarrier);
			tVData.add(tTransferData);
			tVData.add(tLMCheckFieldSchema);
			String strMsg = "";
			boolean MsgFlag = false;
			CheckFieldCom tCheckFieldCom = new CheckFieldCom();
			if (tCheckFieldCom.CheckField(tVData) == false) {
				mErrors.copyAllErrors(tCheckFieldCom.mErrors);
				return false;
			} else {
				LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
						.GetCheckFieldSet();
				for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
					LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
					if ((tField.getReturnValiFlag() != null)
							&& tField.getReturnValiFlag().equals("N")) {
						if ((tField.getMsgFlag() != null)
								&& tField.getMsgFlag().equals("Y")) {
							MsgFlag = true;
							strMsg = strMsg + tField.getMsg() + " ; ";
							break;
						}
					}
				}
				if (MsgFlag == true) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalBL";
					tError.functionName = "CheckTBField";
					tError.errorMessage = "数据有误：" + strMsg;
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "CheckTBField";
			tError.errorMessage = "发生错误，请检验CheckField模块:" + ex;
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		Reflections sRef = new Reflections();
		// 查询是否该组合已经录入过佣金率,如果已经录入过佣金率，那么则删除并备份到B表
		LARateCommisionDB tLARateCommisionDB = new LARateCommisionDB();
		LARateCommisionSet tLARateCommisionSet = new LARateCommisionSet();
//		tLARateCommisionDB.setSerialNo(mLARateCommisionSchema.getSerialNo());
		tLARateCommisionDB.setRiskCode(mLARateCommisionSchema.getRiskCode());
		// mLARateCommisionDB.setSchema(mLARateCommisionSchema);
		tLARateCommisionSet = tLARateCommisionDB.query();
		if (tLARateCommisionSet != null && tLARateCommisionSet.size() > 0) {
			LARateCommisionBSet tLARateCommisionBSet = new LARateCommisionBSet();
			// 如果已经录入过佣金率，那么则删除并备份到B表
			for (int i = 1; i <= tLARateCommisionSet.size(); i++) {
				LARateCommisionBSchema tLARateCommisionbSchema = new LARateCommisionBSchema();
				sRef.transFields(tLARateCommisionbSchema, tLARateCommisionSet
						.get(i));
//				tLARateCommisionbSchema.setEdorNo(tLARateCommisionSet.get(i)
//						.getSerialNo());
				tLARateCommisionbSchema.setEdorType(tLARateCommisionSet.get(i)
						.getBranchType());
				tLARateCommisionbSchema.setModifyDate(tCurrentDate);
				tLARateCommisionbSchema.setModifyTime(tCurrentTime);
				tLARateCommisionBSet.add(tLARateCommisionbSchema);
			}
			ResultMap.put(tLARateCommisionSet, "DELETE");
			ResultMap.put(tLARateCommisionBSet, "INSERT");
		}
		// 以下部分处理把传入的佣金率存储起来
		LARateCommisionSchema tLARateCommisionSchema = new LARateCommisionSchema();
		tLARateCommisionSchema.setSchema(mLARateCommisionSchema);
		// 前台会传入RiskCode、Rate、SatrtDate、EndDate、ManageCom等字段
		// 获取最大号码,如此做法可能产生同步并发操作问题
		// String tSql=" select max(to_number(serialno))+1 from laratecommision
		// ";
		// ExeSQL tExeSQL = new ExeSQL();
		// String tMaxSerialno = tExeSQL.getOneValue(tSql);
		// if(tMaxSerialno==null || tMaxSerialno.equals("")) { tMaxSerialno =
		// "0"; }
		String tMaxSerialno = PubFun1.CreateMaxNo("RATECOMMISIONNO", 8);// 长度八位
//		tLARateCommisionSchema.setSerialNo(tMaxSerialno);//
		tLARateCommisionSchema.setBranchType("1");
//		tLARateCommisionSchema.setBranchType2("01");
		tLARateCommisionSchema.setsex("A");
		tLARateCommisionSchema.setAppAge(0);
		tLARateCommisionSchema.setYear(0);
		tLARateCommisionSchema.setPayIntv("0");
		tLARateCommisionSchema.setCurYear(1);
		tLARateCommisionSchema.setF01("0");
		tLARateCommisionSchema.setF02("0");
		tLARateCommisionSchema.setF03(0);
		tLARateCommisionSchema.setF04(0);
		tLARateCommisionSchema.setF05("0");
		tLARateCommisionSchema.setF06("0");
		tLARateCommisionSchema.setOperator(LogOperator);
		tLARateCommisionSchema.setMakeDate(tCurrentDate);
		tLARateCommisionSchema.setMakeTime(tCurrentTime);
		tLARateCommisionSchema.setModifyDate(tCurrentDate);
		tLARateCommisionSchema.setModifyTime(tCurrentTime);
		ResultMap.put(tLARateCommisionSchema, "INSERT");

		// 判断如果LAWageCalElement存在数据，那么则不作任何操作，否则，则插入数据
		LAWageCalElementDB tLAWageCalElementDB = new LAWageCalElementDB();
		tLAWageCalElementDB.setRiskCode(tLARateCommisionSchema.getRiskCode());
		LAWageCalElementSet tLAWageCalElementSet = tLAWageCalElementDB.query();
		if (tLAWageCalElementSet == null || tLAWageCalElementSet.size() == 0) {
			LAWageCalElementSchema tLAWageCalElementSchema = new LAWageCalElementSchema();
			tLAWageCalElementSchema.setRiskCode(tLARateCommisionSchema
					.getRiskCode());
			tLAWageCalElementSchema.setBranchType(tLARateCommisionSchema
					.getBranchType());
//			tLAWageCalElementSchema.setBranchType2(tLARateCommisionSchema
//					.getBranchType2());
			tLAWageCalElementSchema.setCalType("00");
			tLAWageCalElementSchema.setF1("");
			tLAWageCalElementSchema.setF2("");
			tLAWageCalElementSchema.setF3("PAYINTV");
			tLAWageCalElementSchema.setF4("PAYYEARS ");
			tLAWageCalElementSchema.setF5("PAYYEAR");
			tLAWageCalElementSchema.setCalCode("AR0010");

			LAWageCalElementSchema ttLAWageCalElementSchema = new LAWageCalElementSchema();
			ttLAWageCalElementSchema.setRiskCode(tLARateCommisionSchema
					.getRiskCode());
			ttLAWageCalElementSchema.setBranchType(tLARateCommisionSchema
					.getBranchType());
//			ttLAWageCalElementSchema.setBranchType2(tLARateCommisionSchema
//					.getBranchType2());
			ttLAWageCalElementSchema.setCalType("04");
			ttLAWageCalElementSchema.setF1("");
			ttLAWageCalElementSchema.setF2("");
			ttLAWageCalElementSchema.setF3("");
			ttLAWageCalElementSchema.setF4(" ");
			ttLAWageCalElementSchema.setF5("");
			ttLAWageCalElementSchema.setCalCode("AR0010");
			ResultMap.put(tLAWageCalElementSchema, "DELETE&INSERT");
			ResultMap.put(ttLAWageCalElementSchema, "DELETE&INSERT");
		}
		// 打包map对象数据，用于准备向后抬提交
		mResult.add(ResultMap);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误处理
	 * 
	 * @param cFunc
	 *            String 出错函数名称
	 * @param cErrMsg
	 *            String 出错信息
	 */
	private void buildError(String cFunc, String cErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LDPlanRateBL";
		cError.functionName = cFunc;
		cError.errorMessage = cErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {

		GlobalInput GI = new GlobalInput();
		GI.ComCode = "8621";
		GI.ManageCom = "8621";
		GI.Operator = "001";

		LARateCommisionSchema tLARateCommisionSchema = new LARateCommisionSchema();
		tLARateCommisionSchema.setRiskCode("21000009");
		tLARateCommisionSchema.setManageCom("8621");
//		tLARateCommisionSchema.setStartDate("2007-08-08");
//		tLARateCommisionSchema.setEndDate("2017-08-08");
		tLARateCommisionSchema.setRate(0.20);

		VData sVData = new VData();
		sVData.add(tLARateCommisionSchema);
		sVData.add(GI);

		LDPlanRateBL tLDPlanRateBL = new LDPlanRateBL();
		if (!tLDPlanRateBL.submitData(sVData, "")) {
			logger.debug(" 操作失败，错误如下： ");
			CErrors sErrors = new CErrors();
			sErrors.copyAllErrors(tLDPlanRateBL.mErrors);
			logger.debug(sErrors.getErrContent());
		} else {
			logger.debug(" 操作成功 ");
		}
	}

}
