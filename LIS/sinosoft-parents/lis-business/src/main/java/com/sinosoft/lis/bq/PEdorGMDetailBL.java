package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMDutyGetAliveDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LMDutyGetAliveSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
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
 * Title:
 * </p>
 * <p>
 * Description: 领取方式（间隔）变更（保全项目代码：GM）项目明细
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WuHao ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorGMDetailBL {
private static Logger logger = Logger.getLogger(PEdorGMDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 控制信息传输类 */
	private TransferData mTransferData = new TransferData();
	/** 重算后的领取标准 */
	private String mStandMoney;
	// 准备校验规则所需要的计算要素
	private BqCalBase mBqCalBase = new BqCalBase();
	Reflections mReflections = new Reflections();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPolSet mLPPolSet = new LPPolSet();

	// 获得重算后的领取标准金额，可在保全操作完毕后由BLF层加入mResult
	public String getStandMoney() {
		return this.mStandMoney;
	}

	public VData getResult() {
		return mResult;
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

		// 数据查询业务处理(queryData())
		if (!cOperate.equals("UPDATE||MAIN")) {
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = (VData) cInputData.clone();

		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
				"LPGetSchema", 0);
		if (mGlobalInput == null || mLPEdorItemSchema == null
				|| mLPGetSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		// ====add=======liuxiaosong===========2006-12-25==========================start============
		// 磁盘导入数据补全
		// mTransferData 在前台页面录入时不包括在vdata中，在此还需要if处理
		mLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPGetSchema.setEdorType("GM");
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null) {
			logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():开始磁盘导入数据补全========");
			String tRiskCode = (String) mTransferData
					.getValueByName("RiskCode");
			String tSQL = "SELECT b.GetDutyCode,b.DutyCode,b.GetDutyKind "
					+ " FROM LCGet b,LMDutyGet c " + " WHERE b.PolNo= '?PolNo?'" + " and b.ContNo ='?ContNo?' "
					+ " and b.GetDutyCode=c.GetDutyCode";

			if (!"00115000".equals(tRiskCode) && !"00115001".equals(tRiskCode)) {
				tSQL += " and c.GetType2='1'";
			} else {
				tSQL += " and c.GetType2='0'";
			}
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSQL);
			sbv1.put("PolNo", mLPGetSchema.getPolNo());
			sbv1.put("ContNo", mLPGetSchema.getContNo());
			logger.debug("磁盘导入数据补全SQL" + tSQL);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv1);

			mLPGetSchema.setGetDutyCode(tSSRS.GetText(1, 1));
			mLPGetSchema.setDutyCode(tSSRS.GetText(1, 2));
			logger.debug("设置后的dutycode" + mLPGetSchema.getDutyCode());
			logger.debug("设置后的getdutycode"
					+ mLPGetSchema.getGetDutyCode());
			logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():完成磁盘导入数据补全========");
		}
		// ====add=======liuxiaosong===========2006-12-25==========================start============
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSchema.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemSchema.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemSchema.setInsuredNo(mLPEdorItemSchema.getInsuredNo());

		tLPEdorItemDB.setSchema(tLPEdorItemSchema);
		if (this.getOperate().equals("UPDATE||MAIN")) {
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGMDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "无保全申请数据!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 获取保全主表数据，节省查询次数
			mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1).getSchema());
			logger.debug("mLPEdorItemSchema.getEdorState()"
					+ mLPEdorItemSchema.getEdorState());

			if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGMDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该保全已经申请确认不能修改!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		// 判断变更后是否需要重算领取标准
		boolean tReCal = false;

		// 领取表
		LPGetBL tLPGetBL = new LPGetBL();
		if (!tLPGetBL.queryLastLPGet(this.mLPEdorItemSchema, this.mLPGetSchema)) {
			// add by liuxiaosong
			mErrors.clearErrors();
			mErrors.copyAllErrors(tLPGetBL.mErrors);
			return false;
		}
		LPGetSchema tLPGetSchema = new LPGetSchema();
		tLPGetSchema.setSchema(tLPGetBL.getSchema());
		tLPGetSchema.setGetDutyKind(mLPGetSchema.getGetDutyKind()); // 目前根据产品定义的描述方式，养老金领取方式通过GetDutyKind来表示，GetIntv相应进行变化，由CalBL处理
																	// by
																	// zhangrong
		tLPGetSchema.setModifyDate(strCurrentDate);
		tLPGetSchema.setModifyTime(strCurrentTime);
		// mLPGetSet.add(tLPGetSchema);

		// 添加计算要素
		String tRiskCode = "";

		// //查询用
		// SSRS tSSRS = null;
		// ExeSQL tExeSQL = null;
		// String strsql = "";
		// strsql = "Select riskcode from LCPol where PolNo =
		// '"+mLPEdorItemSchema.getPolNo()+"'";
		// try
		// {
		// tSSRS = new SSRS();
		// tExeSQL = new ExeSQL();
		// tSSRS = tExeSQL.execSQL(strsql);
		// }
		// catch (Exception e)
		// {
		// // @@错误处理
		// CError.buildErr(this, "查询保单险种代码时产生错误!");
		// }
		// if (tSSRS != null && tSSRS.MaxRow > 0)
		// {
		// tRiskCode = tSSRS.GetText(1,1);
		// }

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "查询保单险种代码时产生错误!");
		}
		tRiskCode = tLCPolDB.getRiskCode();

		mBqCalBase.setPolNo(mLPEdorItemSchema.getPolNo());
		mBqCalBase.setRiskCode(tRiskCode);

		// 变更领取方式后需要重算领取标准
		tReCal = true;
		if (!ReCalculate(tLPGetSchema)) {
			mErrors.addOneError(new CError("重算领取标准失败！"));
			return false;
		}
		// ///////////////////////////////////////
		// 领取方式变更后领取相关信息
		for (int i = 1; i <= mLPGetSet.size(); i++) {
			if (tLPGetSchema.getGetDutyCode() != null
					&& tLPGetSchema.getGetDutyCode().equals(
							mLPGetSet.get(i).getGetDutyCode())) {
				// 重算后的领取标准
				tLPGetSchema.setSchema(mLPGetSet.get(i).getSchema());
				tLPGetSchema.setModifyDate(strCurrentDate);
				tLPGetSchema.setModifyTime(strCurrentTime);
				mLPGetSet.clear();
				mLPGetSet.add(tLPGetSchema);
				break;
			}
		}

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);

		mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(mLPGetSet, "DELETE&INSERT");

		if (tRiskCode != null
				&& (tRiskCode.substring(0, 5).equals("00128") || tRiskCode
						.substring(0, 5).equals("00145"))) {
			mMap.put(mLPDutySet, "DELETE&INSERT");
			mMap.put(mLPPolSet, "DELETE&INSERT");
		}

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);
		return true;
	}

	private boolean needCalculate(LPGetSchema aLPGetSchema) {
		boolean flag = false;
		String strSQL = "Select a.NeedReCompute from LMDutyGetAlive a"
				+ " where a.GetDutyCode ='?GetDutyCode?'" + " and a.GetDutyKind ='?GetDutyKind?'";	
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(strSQL);
		sbv2.put("GetDutyCode", aLPGetSchema.getGetDutyCode());
		sbv2.put("GetDutyKind", aLPGetSchema.getGetDutyKind());
		try {
			ExeSQL exeSQL = new ExeSQL();
			if (exeSQL.getOneValue(sbv2).equals("1")) { // 领取时需要重新计算
				flag = true;
			} else if (exeSQL.getOneValue(sbv2).equals("0")) { // 领取时不需要重新计算
				flag = false;
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGMDetailBL";
			tError.functionName = "needCalculate";
			tError.errorMessage = "判断领取时是否需要重新计算时产生错误!";
			this.mErrors.addOneError(tError);
		}
		return flag;
	}

	private boolean ReCalculate(LPGetSchema aLPGetSchema) {
		LPPolBL tLPPolBL = new LPPolBL();
		tLPPolBL.setContNo(aLPGetSchema.getContNo());
		tLPPolBL.setPolNo(aLPGetSchema.getPolNo());
		tLPPolBL.setEdorNo(aLPGetSchema.getEdorNo());
		tLPPolBL.setEdorType(aLPGetSchema.getEdorType());
		tLPPolBL.queryLPPol(mLPEdorItemSchema);

		ReCalBL tReCalBL = new ReCalBL(tLPPolBL.getSchema(), mLPEdorItemSchema);
		// 准备数据进行重算
		// 准备重算需要的保单表数据
		LCPolBL tLCPolBL = tReCalBL.getRecalPol(tLPPolBL.getSchema());
		// 准备重算需要的责任表数据
		LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(mLPEdorItemSchema);
		// 准备重算需要的保费项表数据
		LCPremSet tLCPermSet = tReCalBL.getRecalPrem(mLPEdorItemSchema);
		// 准备重算需要的领取项表数据
		LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(mLPEdorItemSchema);
		for (int i = 1; i <= tLCGetBLSet.size(); i++) {
			if (aLPGetSchema.getGetDutyCode().equals(
					tLCGetBLSet.get(i).getGetDutyCode())) {
				tLCGetBLSet.get(i)
						.setGetDutyKind(aLPGetSchema.getGetDutyKind());
				tLCGetBLSet.get(i).setGettoDate("");
				tLCGetBLSet.get(i).setGetEndDate("");
			}
		}
//		String tRiskCode = tLCPolBL.getRiskCode();
//		if (tRiskCode != null
//				&& (tRiskCode.substring(0, 5).equals("00128") || tRiskCode
//						
//																// by zhangrong
//			LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
//			tLMDutyGetAliveDB
//					.setGetDutyCode(tLMDutyGetAliveDB.getGetDutyCode());// ??TODO
//																		// null?
//			tLMDutyGetAliveDB.setGetDutyKind(aLPGetSchema.getGetDutyKind());
//			LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.query();
//			if (tLMDutyGetAliveSet == null || tLMDutyGetAliveSet.size() <= 0) {
//				CError.buildErr(this, "查询产品定义生存给付项信息失败！");
//				return false;
//			}
//			if (tLMDutyGetAliveSet.get(1).getGetIntv() != 0) {
//				tLCPolBL.setInsuYear(1000);
//				for (int j = 1; j <= tLCDutyBLSet.size(); j++) {
//					// if
//					// (tLCDutyBLSet.get(j).getDutyCode().equals(aLPGetSchema.getDutyCode()))
//					// {
//					tLCDutyBLSet.get(j).setInsuYear(1000);
//					// }
//				}
//			} else {
//				for (int j = 1; j <= tLCDutyBLSet.size(); j++) {
//					// if
//					// (tLCDutyBLSet.get(j).getDutyCode().equals(aLPGetSchema.getDutyCode()))
//					// {
//					tLCPolBL.setInsuYear(tLCDutyBLSet.get(j).getGetYear());
//					tLCDutyBLSet.get(j).setInsuYear(
//							tLCDutyBLSet.get(j).getGetYear());
//					// }
//				}
//			}
//		}

		if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPermSet,
				tLCGetBLSet, mLPEdorItemSchema)) {
			this.mErrors.copyAllErrors(tReCalBL.mErrors);
			CError tCError = new CError();
			tCError.buildErr(this, "重算失败");
			mErrors.addOneError(tCError);
			return false;
		}
		mLPGetSet.add(tReCalBL.aftLPGetSet);
		logger.debug(tReCalBL.aftLPGetSet.get(1).getGetEndDate());
		logger.debug(tReCalBL.aftLPGetSet.get(1).getGettoDate());
		mLPDutySet.add(tReCalBL.aftLPDutySet);
		mLPPolSet.add(tReCalBL.aftLPPolSet);

		return true;
	}
}
