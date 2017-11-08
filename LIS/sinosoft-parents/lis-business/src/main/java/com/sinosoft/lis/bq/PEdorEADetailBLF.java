package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.Set;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:公司解约BLF
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */
public class PEdorEADetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorEADetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 返回到界面的信息 */
	// private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	/** 批改补/退费分类信息，拆分信息初始化界面 */
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private MMap mMap = new MMap();

	public PEdorEADetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 获取数据
		if (!getInputData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		// 接收后台处理结果数据
		if (!getLJSGetEndorseSetFromResult()) {
			return false;
		}

		if (mOperate.equals("QUERY||MAIN")) {
			// 生成数组
			if (!getArrayForFormatPage()) {
				return false;
			}
		} else {
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			try {
				// 获得总退费金额
				double tSumMoney = 0.0;
				if (mLJSGetEndorseSet != null && mLJSGetEndorseSet.size() > 0) {
					for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
						tSumMoney += mLJSGetEndorseSet.get(i).getGetMoney();
					}
				}
				tLOPRTManagerSchema = this.getLOPRTManager(tSumMoney);
			} catch (Exception e) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorEADetailBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "生成打印管理表数据时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 加入打印管理表数据到结果
			mMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
			mResult.add(mMap);

			// 数据提交
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorEADetailBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorEADetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "接收前台数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "PEdorEADetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "获得前台数据错误！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// Reflections tReflections = new Reflections();
		// VData aVData = new VData();
		// LCPolSet tLCPolSet = new LCPolSet();
		// LPPolSet tLPPolSet = new LPPolSet();
		// LCPolDB tLCPolDB = new LCPolDB();
		// tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLCPolSet = tLCPolDB.query();
		// LPPolSchema tLPPolSchema = new LPPolSchema();
		// for(int i = 1; i <= tLCPolSet.size(); i++){
		// tReflections.transFields(tLPPolSchema,tLCPolSet.get(i));
		// tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPPolSet.add(tLPPolSchema);
		// }
		// aVData.add(tLPPolSet);
		// aVData.add(mGlobalInput);
		// aVData.add(mLPEdorItemSchema);
		// if (mTransferData.getValueByName("CancelMode").equals("0")) {
		// PEdorXTDetailBL tPEdorXTDetailBL = new PEdorXTDetailBL();
		// logger.debug("=======================s===================XT====================");
		// if (!tPEdorXTDetailBL.submitData(aVData, mOperate)) {
		// this.mErrors.copyAllErrors(tPEdorXTDetailBL.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorEADetailBLF";
		// tError.functionName = "dealData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mResult.clear();
		// mResult = tPEdorXTDetailBL.getResult();
		// } else {
		// logger.debug("==================f========================CT====================");
		// PEdorCTDetailBL tPEdorCTDetailBL = new PEdorCTDetailBL();
		// if (!tPEdorCTDetailBL.submitData(aVData, mOperate)) {
		// this.mErrors.copyAllErrors(tPEdorCTDetailBL.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorEADetailBLF";
		// tError.functionName = "dealData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mResult.clear();
		// mResult = tPEdorCTDetailBL.getResult();
		// }

		// 如果是查询，则获取所有险种数据
		if (mOperate.equals("QUERY||MAIN")) {
			LCPolDB tLCPolDB = new LCPolDB();
			// tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
			// String tSql = "SELECT *"
			// + " FROM LCPol a"
			// + " WHERE ContNo='" + mLPEdorItemSchema.getContNo() + "'"
			// + " and (AppFlag='1' or (AppFlag='4' and exists(select 'T' from
			// LCContState where PolNo=a.PolNo and StateType='Terminate' and
			// State='1' and StartDate <= '" +
			// mLPEdorItemSchema.getEdorValiDate() + "' and ((EndDate is null)
			// or (EndDate>='" + mLPEdorItemSchema.getEdorValiDate() + "')) and
			// StateReason='01')))";
			// + " and AppFlag='1'"
			// + " and not exists(select 'A' from LCContState where
			// PolNo=a.PolNo and StateType='Terminate' and State='1' and
			// StartDate<='" + mLPEdorItemSchema.getEdorValiDate() + "' and
			// EndDate>='" + mLPEdorItemSchema.getEdorValiDate() + "')"
			// + " and not exists(select 'B' from LCContState where
			// PolNo=a.PolNo and StateType='Terminate' and State='1' and
			// StartDate<='" + mLPEdorItemSchema.getEdorValiDate() + "' and
			// EndDate is null)";
			// 判断如果保单下的所有险种被退保，则该保单也被退保
			// LCPolDB tLCPolDB = new LCPolDB();
			String tSql = " select * from LCPol c where ContNo='?ContNo?' and ( "
					+ " (appflag = '1' and  (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and ((startdate <= '?date?' and '?date?' <= enddate )or(startdate <= '?date?' and enddate is null ))and s.polno = c.polno) = 0 )"
					+ " or "
					+ " (appflag = '4' and  (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(statereason) in ('05', '06') and ((startdate <= '?date?' and '?date?' <= enddate )or(startdate <= '?date?' and enddate is null ))and s.polno = c.polno) > 0 )"
					+ " )";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv.put("date", mLPEdorItemSchema.getEdorAppDate());
			LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
			// LCPolSet tLCPolSet = new LCPolSet();
			// tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorEADetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "获得保单险种基本信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LPPolSet tLPPolSet = new LPPolSet();
			LCPolSchema tLCPolSchema = null;
			LPPolSchema tLPPolSchema = null;
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setSchema(tLCPolSet.get(i));
				tLPPolSchema = new LPPolSchema();
				mReflections.transFields(tLPPolSchema, tLCPolSchema);
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSet.add(tLPPolSchema);
			}
			mInputData.add(tLPPolSet);
		}

		PEdorCTDetailBL tPEdorCTDetailBL = new PEdorCTDetailBL();
		if (!tPEdorCTDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorCTDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorEADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mResult.clear();
		mResult = tPEdorCTDetailBL.getResult();
		// mTransferData = tPEdorEADetailBL.getTransferData();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 获得两个二维数组，初始化MulLine用 一个是险种信息，一个是明晰信息
	 * 行内容：险种编码、险种名称、是否主险、被保人号、被保人、保费、保额、生效日期、退费金额、基本保额现金价值、红利保额现金价值、保费(犹豫期内)、职业加费、健康加费、终了红利、工本费、自垫本金、自垫利息、贷款本金、贷款利息、、、、、、PolNo（隐藏列）
	 * 
	 * @return true--Success,false--Fail
	 */
	private boolean getArrayForFormatPage() {
		try {
			// 说明：一个数组是险种相关信息的内容，另一个数组是费用明晰相关的内容。
			// /***************险种退费信息二维数组************************************\
			// --内容：险种代码0、险种名称1、是否主险2、被保人姓名3、退费金额(后查)4、基本保额5、标准保费6、健康加费7、职业加费8、PolNo9、是否主险标志10、是否已选过(后查)11
			String tSql = "SELECT distinct RiskCode,"
					+ " (select RiskName from LMRisk where RiskCode=a.RiskCode),"
					+ " (case PolNo when MainPolNo then '是' else '否' end),"
					+ " InsuredName,"
					+ " Amnt,"
					+ " (case when (select sum(Prem) from LCPrem where PolNo=a.PolNo and PayPlanType='0') is not null then (select sum(Prem) from LCPrem where PolNo=a.PolNo and PayPlanType='0') else 0 end),"
					+ " (case when (select sum(Prem) from LCPrem where PolNo=a.PolNo and PayPlanType in ('01','03')) is not null then (select sum(Prem) from LCPrem where PolNo=a.PolNo and PayPlanType in ('01','03')) else 0 end),"
					+ " (case when (select sum(Prem) from LCPrem where PolNo=a.PolNo and PayPlanType in ('02','04')) is not null then (select sum(Prem) from LCPrem where PolNo=a.PolNo and PayPlanType in ('02','04')) else 0 end),"
					+ " PolNo,"
					+ " (case PolNo when MainPolNo then '1' else '0' end) as MF"
					+ " FROM LCPol a"
					+ " WHERE ContNo='?ContNo?' and ( "
					+ " (appflag = '1' and  (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and ((startdate <= '?date?' and '?date?' <= enddate )or(startdate <= '?date?' and enddate is null ))and s.polno = a.polno) = 0 )"
					+ " or "
					+ " (appflag = '4' and  (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(statereason) in ('05', '06') and ((startdate <= '?date?' and '?date?' <= enddate )or(startdate <= '?date?' and enddate is null ))and s.polno = a.polno) > 0 )"
					+ " )"
					// ContNo='" + mLPEdorItemSchema.getContNo() + "'"
					// + " and (AppFlag='1' or (AppFlag='4' and exists(select
					// 'T' from LCContState where PolNo=a.PolNo and
					// StateType='Terminate' and State='1' and StartDate <= '" +
					// mLPEdorItemSchema.getEdorValiDate() + "' and ((EndDate is
					// null) or (EndDate>='" +
					// mLPEdorItemSchema.getEdorValiDate() + "')) and
					// StateReason='01')))"
					// + " and AppFlag='1'"
					// + " and not exists(select 'A' from LCContState where
					// PolNo=a.PolNo and StateType='Terminate' and State='1' and
					// StartDate<='" + mLPEdorItemSchema.getEdorValiDate() + "'
					// and EndDate>='" + mLPEdorItemSchema.getEdorValiDate() +
					// "')"
					// + " and not exists(select 'B' from LCContState where
					// PolNo=a.PolNo and StateType='Terminate' and State='1' and
					// StartDate<='" + mLPEdorItemSchema.getEdorValiDate() + "'
					// and EndDate is null)"
					+ " ORDER BY MF desc";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv.put("date", mLPEdorItemSchema.getEdorAppDate());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 没有自垫信息
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorEADetailBL";
				tError.functionName = "getArrayForFormatPage";
				tError.errorMessage = "查询保单险种基本信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			int tPolArrLen = tSSRS.MaxRow;
			String rPolArray[][] = new String[tPolArrLen][12];

			// 已查到的信息
			for (int i = 0; i < tPolArrLen; i++) {
				rPolArray[i][0] = tSSRS.GetText(i + 1, 1);
				rPolArray[i][1] = tSSRS.GetText(i + 1, 2);
				rPolArray[i][2] = tSSRS.GetText(i + 1, 3);
				rPolArray[i][3] = tSSRS.GetText(i + 1, 4);
				rPolArray[i][4] = "0"; // 退费金额(后查)
				rPolArray[i][5] = tSSRS.GetText(i + 1, 5);
				rPolArray[i][6] = tSSRS.GetText(i + 1, 6);
				rPolArray[i][7] = tSSRS.GetText(i + 1, 7);
				rPolArray[i][8] = tSSRS.GetText(i + 1, 8);
				rPolArray[i][9] = tSSRS.GetText(i + 1, 9);
				rPolArray[i][10] = tSSRS.GetText(i + 1, 10);
				rPolArray[i][11] = "0"; // 是否已选过(后查)
			}
			// //获得险种退费金额和是否已选标志
			// if (mLJSGetEndorseSet == null || mLJSGetEndorseSet.size() <= 0)
			// {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "PEdorEADetailBL";
			// tError.functionName = "getArrayForFormatPage";
			// tError.errorMessage = "返回险种退费信息错误！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// 查询上次保存过的记录
			boolean tLastSaveFlag = false; // 上次是否保存过记录
			tSql = "SELECT distinct PolNo FROM LPPol WHERE EdorNo='?EdorNo?' and EdorType='?EdorType?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(tSql);
			sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv.put("EdorType", mLPEdorItemSchema.getEdorType());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 查到了上次保存的记录
				tLastSaveFlag = true;
			}
			// 寻找匹配PolNo，显示已解约险种
			for (int j = 0; j < tPolArrLen; j++) {
				if (rPolArray[j][9] != null) {
					if (tLastSaveFlag) {
						for (int k = 1; k <= tSSRS.MaxRow; k++) {
							if (tSSRS.GetText(k, 1) != null) {
								if (rPolArray[j][9].trim().equals(
										tSSRS.GetText(k, 1).trim())) {
									rPolArray[j][11] = "1";
									// 跳出k层循环
									break;
								}
							}
						}
					}
				}
			}

			// 按险种匹配退费金额
			if (mLJSGetEndorseSet != null && mLJSGetEndorseSet.size() > 0) {
				for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
					// 寻找匹配PolNo
					for (int j = 0; j < tPolArrLen; j++) {
						if (rPolArray[j][9] != null) {
							if (mLJSGetEndorseSet.get(i).getPolNo() != null) {
								if (rPolArray[j][9].trim().equals(
										mLJSGetEndorseSet.get(i).getPolNo()
												.trim())) {
									rPolArray[j][4] = String.valueOf(Double
											.parseDouble(rPolArray[j][4])
											+ mLJSGetEndorseSet.get(i)
													.getGetMoney());
									// 跳出j层循环
									break;
								}
							}
						}
					}
				}
			}
			// \********************************************************************/
			// /***************险种费用明细二维数组************************************\
			// --内容：PolNo、险种代码、险种名称、费用名称、费用金额、费用类型

			// \********************************************************************/

			// /**********先获得MulLine结构二维数组************************************\
			// --注意：前六列(012345)是固定的，第一列(0)是序号，第二列(1)是PolNo隐藏列，第三列(2)是险种编码，第四列(3)是是否主险，第五列(4)是险种名称，第六列(5)是总退还金额（分正负）
			// LDCodeDB tLDCodeDB = new LDCodeDB();
			// LDCodeSet tLDCodeSet = new LDCodeSet();
			// LDCodeSchema tLDCodeSchema = null;
			// tLDCodeDB.setCodeType("BQSubFeeType");
			// tLDCodeSet = tLDCodeDB.query();
			// if (tLDCodeSet == null || tLDCodeSet.size() <= 0)
			// {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "PEdorEADetailBLF";
			// tError.functionName = "getArrayForFormatPage";
			// tError.errorMessage = "获得子业务类型时产生错误！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// //产生数组，注意，数组第三列(2)为，不限制数据宽度，宽度最后统一确定
			// int tFrameArrLen = 6 + tLDCodeSet.size();
			// String rFrameArray[][] = new String[tFrameArrLen][5];
			// rFrameArray[0][0] = "序号"; //列名称
			// //rFrameArray[0][1] = "30px"; //列显示宽度
			// //rFrameArray[0][2] = ""; //列数据宽度，不限制
			// rFrameArray[0][3] = "0"; //是否显示。0-显示，3-隐藏
			// rFrameArray[0][4] = ""; //备用列，存Code子业务类型
			//
			// rFrameArray[1][0] = "PolNo";
			// rFrameArray[1][3] = "3";
			// rFrameArray[1][4] = "";
			//
			// rFrameArray[2][0] = "险种编码";
			// rFrameArray[2][3] = "0";
			// rFrameArray[2][4] = "";
			//
			// rFrameArray[3][0] = "是否主险";
			// rFrameArray[3][3] = "0";
			// rFrameArray[3][4] = "";
			//
			// rFrameArray[4][0] = "险种名称";
			// rFrameArray[4][3] = "0";
			// rFrameArray[4][4] = "";
			//
			// rFrameArray[5][0] = "总退费金额";
			// rFrameArray[5][3] = "0";
			// rFrameArray[5][4] = "";
			//
			// //子业务类型
			// for (int i = 6; i < tFrameArrLen; i++)
			// {
			// tLDCodeSchema = new LDCodeSchema();
			// tLDCodeSchema.setSchema(tLDCodeSet.get(i-5));
			// rFrameArray[i][0] = tLDCodeSchema.getCodeName();
			// rFrameArray[i][3] = "0";
			// rFrameArray[i][4] = tLDCodeSchema.getCode();
			// }
			//
			// //获得列宽度，根据列标题文字长度换算
			// double tQuotiety = 7; //系数，可调整
			// for (int i = 0; i < tFrameArrLen; i++)
			// {
			// if (i == 4)
			// {
			// //这是险种名称，此列由于字比较多，所以单独加宽
			// rFrameArray[i][1] =
			// String.valueOf(StrTool.getLength(rFrameArray[i][0]) * tQuotiety *
			// 3) + "px";
			// }
			// else
			// {
			// rFrameArray[i][1] =
			// String.valueOf(StrTool.getLength(rFrameArray[i][0]) * tQuotiety)
			// + "px";
			// }
			// }
			// //\********************************************************************/
			//
			// ///**********再获得MulLine数据二维数组************************************\
			// String tSql = "SELECT
			// PolNo,RiskCode,decode(PolNo,MainPolNo,'是','否') as MF,(select
			// RiskName from LMRisk where RiskCode=LCPol.RiskCode)"
			// + " FROM LCPol"
			// + " WHERE ContNo='" + mLPEdorItemSchema.getContNo() + "'"
			// + " ORDER BY MF desc";
			// SSRS tSSRS = new SSRS();
			// ExeSQL tExeSQL = new ExeSQL();
			// tSSRS = tExeSQL.execSQL(tSql);
			// if (tSSRS == null || tSSRS.MaxRow <= 0)
			// {
			// //没有自垫信息
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "PEdorEADetailBL";
			// tError.functionName = "getArrayForFormatPage";
			// tError.errorMessage = "查询保单险种基本信息失败！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// int tContentArrLen = tSSRS.MaxRow;
			// String rContentArray[][] = new
			// String[tContentArrLen][tFrameArrLen-1];
			//
			// //放入PolNo，险种编码，是否主险，险种名称
			// for (int i = 0; i < tContentArrLen; i++)
			// {
			// rContentArray[i][0] = tSSRS.GetText(i+1,1);
			// rContentArray[i][1] = tSSRS.GetText(i+1,2);
			// rContentArray[i][2] = tSSRS.GetText(i+1,3);
			// rContentArray[i][3] = tSSRS.GetText(i+1,4);
			// //初始化其它，包括总金额，默认为0
			// for (int j = 4; j < tFrameArrLen-1; j++)
			// {
			// rContentArray[i][j] = "0";
			// }
			// }
			//
			// //填入某险种的某子业务类型对应的值
			// LJSGetEndorseSchema tLJSGetEndorseSchema = null;
			// if (mLJSGetEndorseSet == null || mLJSGetEndorseSet.size() <= 0)
			// {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "PEdorEADetailBL";
			// tError.functionName = "getArrayForFormatPage";
			// tError.errorMessage = "返回险种退费信息错误！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// for (int i = 1; i <= mLJSGetEndorseSet.size(); i++)
			// {
			// tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			// tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSet.get(i));
			// //寻找匹配PolNo
			// for (int j = 0; j < tContentArrLen; j++)
			// {
			// if (rContentArray[j][0] != null &&
			// tLJSGetEndorseSchema.getPolNo() != null)
			// {
			// if
			// (rContentArray[j][0].trim().equals(tLJSGetEndorseSchema.getPolNo().trim()))
			// {
			// //寻找匹配子业务类型(SubFeeOperationType)
			// for (int k = 6; k < tFrameArrLen; k++)
			// {
			// if (rFrameArray[k][4] != null &&
			// tLJSGetEndorseSchema.getSubFeeOperationType() != null)
			// {
			// if
			// (rFrameArray[k][4].trim().equals(tLJSGetEndorseSchema.getSubFeeOperationType().trim()))
			// {
			// rContentArray[j][k-1] =
			// String.valueOf(Double.parseDouble(rContentArray[j][k-1]) +
			// tLJSGetEndorseSchema.getGetMoney());
			// }
			// }
			// }
			// }
			// }
			// }
			// }
			//
			// //计算总金额
			// double tSum = 0.0;
			// for (int i = 0; i < tContentArrLen; i++)
			// {
			// tSum = 0.0;
			// for (int j = 5; j < tFrameArrLen-1; j++)
			// {
			// tSum += Double.parseDouble(rContentArray[i][j]);
			// }
			// rContentArray[i][4] = String.valueOf(tSum);
			// }
			// \********************************************************************/

			// 先加入结构数组，数据数组令包装一层加入
			mResult.clear();
			mResult.add(rPolArray);
			// VData tVData = new VData();
			// tVData.add(rContentArray);
			// mResult.add(tVData);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorEADetailBLF";
			tError.functionName = "getArrayForFormatPage";
			tError.errorMessage = "获得险种退还信息时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 从结果中获得批改补退费信息
	 * 
	 * @return boolean
	 */
	private boolean getLJSGetEndorseSetFromResult() {
		// 前台界面初始化
		// 接收后台处理结果数据
		try {
			MMap tMMap = new MMap();
			tMMap = (MMap) mResult.getObjectByObjectName("MMap", 0);
			// 备份MMap
			mMap.add(tMMap);
			Object o = null;
			String className = "";
			Set set = tMMap.keySet();
			for (int i = 0; i < set.size(); i++) {
				// 获取操作对象Schema或Set或SQL
				o = tMMap.getOrder().get(String.valueOf(i + 1));
				// 获得类名
				className = o.getClass().getName();
				if (className.endsWith("LJSGetEndorseSet")) {
					mLJSGetEndorseSet = (LJSGetEndorseSet) o;
					break;
				}
			}
			// 从结果中去除MMap，如果是保存，则将打印管理表数据放入MMap后再加入
			mResult.removeElement(tMMap);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorEADetailBLF";
			tError.functionName = "getLJSGetEndorseSetFromResult";
			tError.errorMessage = "接收后台返回险种退费信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// =====del======zhangtao========2005-10-10====有时退保不产生退费========BGN======
		// if (mLJSGetEndorseSet == null || mLJSGetEndorseSet.size() <= 0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "PEdorEADetailBLF";
		// tError.functionName = "getLJSGetEndorseSetFromResult";
		// tError.errorMessage = "获得后台返回险种退费信息错误！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// =====del======zhangtao========2005-10-10====有时退保不产生退费========BGN======
		return true;
	}

	/**
	 * 生成打印管理表数据
	 * 
	 * @param tLJSPaySchema
	 * @param tEdorNo
	 * @param type
	 * @return
	 */
	private LOPRTManagerSchema getLOPRTManager(double tMoney) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		String mLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
		tLOPRTManagerSchema.setPrtSeq(serNo);

		tLOPRTManagerSchema.setOtherNo(mLPEdorItemSchema.getContNo());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 合同号
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorEndCont);// 公司解约通知书
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom);
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (tLCContDB.getInfo()) {
			tLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode());
		}
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType("1"); // 后台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		tLOPRTManagerSchema
				.setStandbyFlag1(mLPEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		tLOPRTManagerSchema.setStandbyFlag2(String.valueOf(tMoney)); // 退保金

		return tLOPRTManagerSchema;
	}

	public static void main(String[] args) {
		PEdorEADetailBLF tPEdorEADetailBLF = new PEdorEADetailBLF();
		// 后面要执行的动作：查询
		CErrors tError = null;
		String FlagStr = "";
		String Content = "";
		String tFrameArray[][];
		VData tempVData = new VData();
		String tContentArray[][];

		// 个人保单批改信息
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120050808000001");
		tLPEdorItemSchema.setContNo("230110000003504");
		tLPEdorItemSchema.setEdorNo("6020050808000010    ");
		tLPEdorItemSchema.setEdorType("EA");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorAppDate("2005-05-08");
		tLPEdorItemSchema.setEdorValiDate("2005-05-08");

		TransferData tTransferData = new TransferData();
		// 个别险种处理
		tTransferData.setNameAndValue("InsuYear", "");
		tTransferData.setNameAndValue("GetDutyKind", "");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		try {
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tLPEdorItemSchema);
			tVData.add(tG);
			boolean tag = tPEdorEADetailBLF.submitData(tVData, "QUERY||MAIN");
		} catch (Exception ex) {
			Content = "查询险种退费信息产生错误:" + ex.toString();
			FlagStr = "Fail";
			logger.debug(tPEdorEADetailBLF.mErrors.getErrContent());
		}

		// 如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr.equals("")) {
			tError = tPEdorEADetailBLF.getErrors();
			if (!tError.needDealError()) {
				FlagStr = "Success";

				if (tPEdorEADetailBLF.getResult() != null
						&& tPEdorEADetailBLF.getResult().size() > 0) {
					VData tResult = new VData();
					tResult = tPEdorEADetailBLF.getResult();
					if (tResult == null) {
						FlagStr = "Fail";
						Content = "获取险种退费信息失败！";
					}
					tFrameArray = (String[][]) tResult.get(0);
					// tempVData = (VData)
					// tResult.getObjectByObjectName("VData", 0);
					// tContentArray = (String[][]) tempVData.get(0);

					for (int i = 0; i < tFrameArray.length; i++) {
						for (int j = 0; j < tFrameArray[i].length; j++) {
							logger.debug(tFrameArray[i][j]);
						}
					}

					// for (int i = 0; i < tContentArray.length; i++)
					// {
					//
					// for (int j = 0; j < tContentArray[i].length; j++)
					// {
					// logger.debug(tContentArray[i][j]);
					// }
					// }

				}
			} else {
				Content = tError.getFirstError();
				FlagStr = "Fail";
			}
		}
		logger.debug(FlagStr + Content);
	}
}
