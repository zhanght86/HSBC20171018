package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMEdorItemSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPContTempInfoSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPContTempInfoSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请-添加保全项目处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @date 2005-04-29
 */
public class PEdorAppItemBL {
private static Logger logger = Logger.getLogger(PEdorAppItemBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	private List mBomList = new ArrayList();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	/** 业务处理相关变量 */
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPContTempInfoSet mLPContTempInfoSet = new LPContTempInfoSet();
	
	
	// add by jiaqiangli 2009-04-27
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mDisplayType;
	private String mOtherNo;
	private String mOtherNoType;
	private String mAppType;

	private static String mVAILIDATE_CALTYPE = "ValiDate";
	// 统一更新日期，时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAppItemBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData()) {
			return false;
		}

		logger.debug("---getInputData---");

		// 根据业务逻辑对数据进行处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---dealData---");

		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAppItemBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {

		// 全局变量
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		// 批改项目表
		mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
				"LPEdorItemSet", 0);

		if (mLPEdorItemSet == null) {
			CError.buildErr(this, "没有传入批改项目信息!");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		// 调用 AddEdorItemCheckBL 做添加保全项目校验处理
		AddEdorItemCheckBL tAddEdorItemCheckBL = new AddEdorItemCheckBL();
		if (!tAddEdorItemCheckBL.submitData(mInputData, "")) {
			mErrors.copyAllErrors(tAddEdorItemCheckBL.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		if (mOperate.equals("INSERT||EDORITEM")) {
			if (!getOtherNo())
				return false;
			String sAppObj = "";

			if (mOtherNoType.equals("3")) // 个人保单号
			{
				sAppObj = "I";
			}

			if (mOtherNoType.equals("1")) // 个人客户号
			{
				sAppObj = "B";
				// 查出该客户下所有保单，每个保单都添加该保全项目
				BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
				LCContSet tLCContSet = tContHangUpBL.getCustomerCont(mOtherNo,
						mLPEdorItemSet.get(1).getEdorAppDate(), mAppType);
				if (tLCContSet == null || tLCContSet.size() < 1) {
					CError.buildErr(this, "该客户没有相关有效保单!");
					return false;
				}

				// 往打印管理表插入一条数据
				InsertPRT();

				// 以个人客户号申请每次添加保全项目只会有一条
				LPEdorItemSchema tLPEdorItemSchema = mLPEdorItemSet.get(1);
				mLPEdorItemSet.clear();
				LPEdorItemSchema nLPEdorItemSchema;
				Reflections tRef = new Reflections();

				for (int k = 1; k <= tLCContSet.size(); k++) {
					nLPEdorItemSchema = new LPEdorItemSchema();
					tRef.transFields(nLPEdorItemSchema, tLPEdorItemSchema);
					nLPEdorItemSchema.setContNo(tLCContSet.get(k).getContNo());
					nLPEdorItemSchema.setInsuredNo(tLCContSet.get(k).getInsuredNo()); // 此处修改为保单实际的被保人
					nLPEdorItemSchema.setPolNo("000000");
					mLPEdorItemSet.add(nLPEdorItemSchema);
				}
			}

			LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
			String strEdorAppNo = null;
			for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
				mDisplayType = mLPEdorItemSet.get(i).getDisplayType();// 显示级别
				if (mDisplayType.equals("1")) {
					// 保单级的保全项目
					if (mOtherNoType.equals("3")) {
						// 个人保单号
						mLPEdorItemSet.get(i).setContNo(mOtherNo);
					}
				}

				// 计算保全生效日期 设置和申请日期一致，用于明细中各类计算
				String sEdorValiDate = mLPEdorItemSet.get(i).getEdorAppDate(); //calValiDate(mLPEdorItemSet.get(i));
				if (sEdorValiDate == null) {
					return false;
				}
				mLPEdorItemSet.get(i).setEdorValiDate(sEdorValiDate);

				// 处理保全批改表
				tLPEdorMainSet.clear();
				// ===add===zhangtao====2006-06-06====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====BGN=======
				if ((mLPEdorItemSet.get(i).getEdorAcceptNo() == null || mLPEdorItemSet
						.get(i).getEdorAcceptNo().equals(""))
						&& (mLPEdorItemSet.get(i).getContNo() == null || mLPEdorItemSet
								.get(i).getContNo().equals(""))) {
					CError.buildErr(this, "保全项目查询条件为空!");
					return false;
				}
				// ===add===zhangtao====2006-06-06====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====END=======
				LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
				tLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSet.get(i)
						.getEdorAcceptNo());
				tLPEdorMainDB.setContNo(mLPEdorItemSet.get(i).getContNo());
				tLPEdorMainSet.set(tLPEdorMainDB.query());
				if (tLPEdorMainDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询批改主表失败！");
					return false;
				}
				if (tLPEdorMainSet != null && tLPEdorMainSet.size() > 0) {
					mLPEdorItemSet.get(i).setEdorNo(
							tLPEdorMainSet.get(1).getEdorNo());
					mLPEdorItemSet.get(i).setEdorAppNo(
							tLPEdorMainSet.get(1).getEdorAppNo());
				} else if (strEdorAppNo != null && mOtherNoType.equals("3")) // 只针对保单层面的申请
				{
					mLPEdorItemSet.get(i).setEdorNo(strEdorAppNo);
					mLPEdorItemSet.get(i).setEdorAppNo(strEdorAppNo);
				} else // 如果没有保全申请批单，生成一条
				{
					LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
					tLPEdorMainSchema.setEdorAcceptNo(mLPEdorItemSet.get(i)
							.getEdorAcceptNo());

					String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
					strEdorAppNo = PubFun1.CreateMaxNo("EdorAppNo", strLimit);
					if (StrTool.compareString(strEdorAppNo, "")) {
						CError.buildErr(this, "生成保全申请批单号错误！");
						return false;
					} else {
						tLPEdorMainSchema.setEdorAppNo(strEdorAppNo);
						tLPEdorMainSchema.setEdorNo(strEdorAppNo);
						mLPEdorItemSet.get(i).setEdorNo(strEdorAppNo);
						mLPEdorItemSet.get(i).setEdorAppNo(strEdorAppNo);
					}

					tLPEdorMainSchema.setContNo(mLPEdorItemSet.get(i)
							.getContNo());
					tLPEdorMainSchema.setEdorAppDate(mLPEdorItemSet.get(i)
							.getEdorAppDate());
					tLPEdorMainSchema.setEdorValiDate(mLPEdorItemSet.get(i)
							.getEdorValiDate());
					tLPEdorMainSchema.setEdorState("3");
					tLPEdorMainSchema.setUWState("0");
					tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
					//tLPEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
					tLPEdorMainSchema.setManageCom(this.mLPEdorAppSchema.getManageCom());
					tLPEdorMainSchema.setMakeDate(mCurrentDate);
					tLPEdorMainSchema.setMakeTime(mCurrentTime);
					tLPEdorMainSchema.setModifyDate(mCurrentDate);
					tLPEdorMainSchema.setModifyTime(mCurrentTime);

					mLPEdorMainSet.add(tLPEdorMainSchema);
				}

				LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
				tLMEdorItemDB.setEdorCode(mLPEdorItemSet.get(i).getEdorType());
				tLMEdorItemDB.setAppObj(sAppObj);
				LMEdorItemSet tLMEdorItemSet = tLMEdorItemDB.query();
				if (tLMEdorItemSet == null || tLMEdorItemSet.size() <= 0) {
					CError.buildErr(this, "查询保全定义表失败！");
					return false;
				}
				LMEdorItemSchema tLMEdorItemShema = tLMEdorItemSet.get(1);
				if ("0".equals(tLMEdorItemShema.getNeedDetail())) {
					mLPEdorItemSet.get(i).setEdorState("1"); // 不需要录入明细
				} else {
					mLPEdorItemSet.get(i).setEdorState("3"); // 申请待录入状态
				}
				mLPEdorItemSet.get(i).setGrpContNo("00000000000000000000");
				//mLPEdorItemSet.get(i).setManageCom(mGlobalInput.ManageCom);
				mLPEdorItemSet.get(i).setManageCom(this.mLPEdorAppSchema.getManageCom());
				mLPEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
				mLPEdorItemSet.get(i).setMakeDate(mCurrentDate);
				mLPEdorItemSet.get(i).setMakeTime(mCurrentTime);
				mLPEdorItemSet.get(i).setModifyDate(mCurrentDate);
				mLPEdorItemSet.get(i).setModifyTime(mCurrentTime);
			}

			// 校验传入的数据
			if (!checkData()) {
				return false;
			}
			if (mOtherNoType.equals("1")) // 个人客户号
			{
				//增加对中间表lpconttempinfo的处理
				for(int j = 1; j <= mLPEdorItemSet.size(); j++)
				{
					ExeSQL  xExeSQL =new ExeSQL ();
					LPContTempInfoSchema tLPContTempInfoSchema = new LPContTempInfoSchema();
					tLPContTempInfoSchema.setEdorAcceptNo(mLPEdorItemSet.get(j).getEdorAcceptNo());
					tLPContTempInfoSchema.setEdorNo(mLPEdorItemSet.get(j).getEdorNo());
					tLPContTempInfoSchema.setEdorType(mLPEdorItemSet.get(j).getEdorType());
					tLPContTempInfoSchema.setContNo(mLPEdorItemSet.get(j).getContNo());
					SQLwithBindVariables sbv=new SQLwithBindVariables();
					sbv.sql("select appntno from lccont where contno='"+"?contno?"+"'");
					sbv.put("contno", mLPEdorItemSet.get(j).getContNo());
					tLPContTempInfoSchema.setAppntNo(xExeSQL.getOneValue(sbv));
					tLPContTempInfoSchema.setInsuredNo(mLPEdorItemSet.get(j).getInsuredNo());
					tLPContTempInfoSchema.setState("0");//初始都为0，一旦自核处理完成后置为2
					//以下5个字段不能为空，不过暂时用不上，所以都给置上统一值
					tLPContTempInfoSchema.setIsNeedReCal("0");
					tLPContTempInfoSchema.setConfOperator("auto");
					tLPContTempInfoSchema.setConfDate(mCurrentDate);
					tLPContTempInfoSchema.setApplyOperator("auto");
					tLPContTempInfoSchema.setApplyDate(mCurrentDate);
					//////////////////////////////////////////////////////
					tLPContTempInfoSchema.setManageCom(mLPEdorItemSet.get(j).getManageCom());
					tLPContTempInfoSchema.setRemark("");
					tLPContTempInfoSchema.setOperator(mGlobalInput.Operator);
					tLPContTempInfoSchema.setMakeDate(mCurrentDate);
					tLPContTempInfoSchema.setMakeTime(mCurrentTime);
					tLPContTempInfoSchema.setModifyDate(mCurrentDate);
					tLPContTempInfoSchema.setModifyTime(mCurrentTime);
	
					tLPContTempInfoSchema.setICEdorAcceptNo("");
					tLPContTempInfoSchema.setICEdorNo("");	
					
					mLPContTempInfoSet.add(tLPContTempInfoSchema);
				}
			}

			if (mLPContTempInfoSet != null && mLPContTempInfoSet.size() > 0) 
			{
				map.put(mLPContTempInfoSet, "INSERT");
			}
			
			if (mLPEdorMainSet != null && mLPEdorMainSet.size() > 0) {
				map.put(mLPEdorMainSet, "INSERT");
			}

			map.put(mLPEdorItemSet, "INSERT");
			//多币种改造
			String tCurrencyItemSQL = "update lpedoritem a "
									+ "  set a.currency = (select currency from lccont where contno = a.contno)"
									+ " where a.edorno = '"+"?edorno?"+"'";
			 
			String tCurrencyMainSQL = "update lpedormain a "
									+ " set a.currency = (select currency from lccont where contno = a.contno)"
									+ " where a.edorno = '"+"?edorno?"+"'";

			String tCurrencyAppSQL = "update lpedorapp a "
									+ "    set a.currency = (select currency from lccont where contno = a.otherno)"
									+ "  where a.edoracceptno = '"+"?edoracceptno?"+"'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tCurrencyItemSQL);
			sbv1.put("edorno", mLPEdorItemSet.get(1).getEdorNo());
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tCurrencyMainSQL);
			sbv2.put("edorno", mLPEdorItemSet.get(1).getEdorNo());
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tCurrencyAppSQL);
			sbv3.put("edoracceptno", mLPEdorItemSet.get(1).getEdorAcceptNo());
			map.put(sbv1, "UPDATE");
			map.put(sbv2, "UPDATE");
			map.put(sbv3, "UPDATE");
			
//			String EdorTypes = "(FS,FT,CT,RP,ET,ES,PH,CF,MO,PR,NP)";
//			String aEdorType = mLPEdorItemSet.get(1).getEdorType();
//			aEdorType = aEdorType == null ? "XX" : aEdorType;
			Map FlagMap = new HashMap();
			String PosFlag = "1";
			String ClaimFlag = "0";
			String RNFlag = "1";
//			if (EdorTypes.contains(aEdorType)) {
//				RNFlag = "1";
//				TransferData tTransferData = new TransferData();
//				tTransferData.setNameAndValue("ContNo", mLPEdorItemSet.get(1).getContNo());
//				tTransferData.setNameAndValue("EdorAcceptNo", mLPEdorItemSet.get(1).getEdorAcceptNo());
//				tTransferData.setNameAndValue("DelType", aEdorType);
//				tTransferData.setNameAndValue("CurrentDate", mCurrentDate);
//				tTransferData.setNameAndValue("CurrentTime", mCurrentTime);
//				VData tVData = new VData();
//				tVData.add(mGlobalInput);
//				tVData.add(tTransferData);
//			}
			FlagMap.put("PosFlag", PosFlag);
			FlagMap.put("ClaimFlag", ClaimFlag);
			FlagMap.put("RNFlag", RNFlag);

			BqContHangUpBL tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
			if (!tBqContHangUpBL.checkHangUpState(
					mLPEdorAppSchema.getOtherNo(), mLPEdorAppSchema
							.getOtherNoType())) {
				mErrors.copyAllErrors(tBqContHangUpBL.mErrors);
				return false;
			}

			if (!tBqContHangUpBL.hangUpCont(mLPEdorAppSchema.getOtherNo(), "1",
					mLPEdorAppSchema.getEdorAcceptNo())) {
				CError.buildErr(this, "保单挂起失败! 保单号："
						+ mLPEdorAppSchema.getOtherNo());
				return false;
			} else {
				MMap tMap = tBqContHangUpBL.getMMap();
				map.add(tMap);
			}

		}

		return true;
	}


	/**
	 * 根据保全申请日期计算保全生效日期
	 * 
	 * @param: 无
	 * @return: void
	 */
	private String calValiDate(LPEdorItemSchema tLPEdorItemSchema) {
		// ********************************************
		// 生效日期的计算代码将描述在[LMEdorCal]表中
		// ********************************************
		String sEdorValiDate = ""; // 生效日期
		String sCalCode = ""; // 计算代码
		String sRiskCode = "000000";
		String sPolNo = tLPEdorItemSchema.getPolNo();
		if (sPolNo.equals("000000")) {
			sRiskCode = "000000";
		} else {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单信息失败!"));
				return null;
			}
			sRiskCode = tLCPolDB.getRiskCode();
		}
		LMEdorCalSet tLMEdorCalSet;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode("000000");
		tLMEdorCalDB.setDutyCode("000000");
		tLMEdorCalDB.setEdorType(tLPEdorItemSchema.getEdorType());
		tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
		tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
			mErrors.addOneError(new CError("查询保全生效日期计算代码失败!"));
			return null;
		}
		if (tLMEdorCalSet != null && tLMEdorCalSet.size() == 1) {
			sCalCode = tLMEdorCalSet.get(1).getCalCode();
		}
		logger.debug("===sCalCode1===" + sCalCode);
		if (sCalCode == null || sCalCode.trim().equals("")) {
			// 如果该险种没有特殊描述保全生效日期计算代码，则取通用计算代码
			tLMEdorCalDB = new LMEdorCalDB();
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setDutyCode("000000");
			tLMEdorCalDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
			tLMEdorCalSet = tLMEdorCalDB.query();
			if (tLMEdorCalDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
				mErrors.addOneError(new CError("查询保全生效日期计算代码失败!"));
				return null;
			}
			if (tLMEdorCalSet != null && tLMEdorCalSet.size() == 1) {
				sCalCode = tLMEdorCalSet.get(1).getCalCode();
			}
			logger.debug("===sCalCode2===" + sCalCode);
			if (sCalCode == null || sCalCode.trim().equals("")) {
				// 如果该保全项目没有特殊描述保全生效日期计算代码，则取通用计算代码
				tLMEdorCalDB = new LMEdorCalDB();
				tLMEdorCalDB.setRiskCode("000000");
				tLMEdorCalDB.setDutyCode("000000");
				tLMEdorCalDB.setEdorType("00");
				tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
				if (!tLMEdorCalDB.getInfo()) {
					mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
					mErrors.addOneError(new CError("查询保全生效日期计算代码失败!"));
					return null;
				}
				sCalCode = tLMEdorCalDB.getCalCode();
				logger.debug("===sCalCode3===" + sCalCode);
			}
		}
		int iInterval = getInterval(tLPEdorItemSchema.getContNo(),
				tLPEdorItemSchema.getEdorAppDate());
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===BGN=====
		// if (iInterval == -1)
		// {
		// return null;
		// }
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===END=====
		PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
		BqCalBase tBqCalBase=new BqCalBase();
		tBqCalBase.setPolNo(tLPEdorItemSchema.getPolNo());
		tBqCalBase.setContNo(tLPEdorItemSchema.getContNo());
		tBqCalBase.setInterval(iInterval);
		mPrepareBOMBQEdorBL.setBqCalBase(tBqCalBase);
		if (!prepareBOMList(tBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return "执行规则引擎报错";
		}
		
		Calculator tCalculator = new Calculator();
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(sCalCode);
		// 增加基本要素
		tCalculator.addBasicFactor("EdorAppDate", tLPEdorItemSchema
				.getEdorAppDate());// 申请日期
		tCalculator.addBasicFactor("PolNo", tLPEdorItemSchema.getPolNo()); // 险种号码
		tCalculator.addBasicFactor("ContNo", tLPEdorItemSchema.getContNo()); // 保单号码
		tCalculator.addBasicFactor("Interval", String.valueOf(iInterval)); // 保单号码
		// 进行计算
		sEdorValiDate = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "保全生效日期计算失败!");
			return null;
		}
		if (sEdorValiDate == null || sEdorValiDate.equals("")) {
			CError.buildErr(this, "保全生效日期计算为空!");
			return null;
		}
		if (sEdorValiDate.length() > 10) {
			sEdorValiDate = sEdorValiDate.substring(0, 10);
		}
		return sEdorValiDate;
	}

	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase mBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSet.get(1));
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}
	private int getInterval(String sContNo, String sEdorAppDate) {
		int iInterval = 0;
		// 根据PolNo查询出保单生效日期
		String sql = " select cvalidate from lccont " + " where contno = '"
				+ "?sContNo?" + "'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sContNo", sContNo);
		String sCValiDate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return -1;
		}
		if (sCValiDate == null || sCValiDate.equals("")) {
			CError.buildErr(this, "保单生效日期为空!");
			return -1;
		}
		if (sCValiDate.length() > 10) {
			sCValiDate = sCValiDate.substring(0, 10);
		}

		// 根据保单生效日期计算保单年度
		String calYearsType = "0";
		if (calYearsType.equals("0")) // 0-舍弃法(向下撇)
		{
			iInterval = PubFun.calInterval(sCValiDate, sEdorAppDate, "Y");
		} else if (calYearsType.equals("1")) // 1-约进法(向上撇)
		{
			iInterval = PubFun.calInterval2(sCValiDate, sEdorAppDate, "Y");
		}
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===BGN=====
		// if (iInterval < 0)
		// {
		// CError.buildErr(this, "保单年度不符合实际!");
		// return -1;
		// }
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===END=====
		return iInterval;
	}

	/**
	 * 查询保全受理信息
	 * 
	 * @return: boolean
	 */
	private boolean getOtherNo() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSet.get(1).getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mOtherNo = tLPEdorAppDB.getOtherNo();
		mOtherNoType = tLPEdorAppDB.getOtherNoType();
		mAppType = tLPEdorAppDB.getAppType();

		this.mLPEdorAppSchema = tLPEdorAppDB.getSchema();
		return true;
	}

	/**
	 * 往打印管理表插入一条数据
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void InsertPRT() {
		LPEdorItemSchema tLPEdorItemSchema = mLPEdorItemSet.get(1);
		String sEdorType = tLPEdorItemSchema.getEdorType();
		String sCode = "";
		if (sEdorType.equals("CM")) {
			sCode = PrintManagerBL.CODE_PEdorCMInfo;
		} else if (sEdorType.equals("HI")) {
			sCode = PrintManagerBL.CODE_PEdorHIInfo;
		} else {
			return;
		}

		LOPRTManagerSchema mmLOPRTManagerSchema = new LOPRTManagerSchema();

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
		mmLOPRTManagerSchema.setOtherNo(tLPEdorItemSchema.getEdorAcceptNo());
		mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDORACCEPT); // 保全受理号
		mmLOPRTManagerSchema.setCode(sCode); // 打印类型
		mmLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
		mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		mmLOPRTManagerSchema.setMakeDate(mCurrentDate);
		mmLOPRTManagerSchema.setMakeTime(mCurrentTime);

		mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);

		map.put(mmLOPRTManagerSchema, "INSERT");
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppItemBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("6120050810000041");

		LPEdorItemSchema tLPEdorItemSchema;
		LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

		tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060303000009");
		tLPEdorItemSchema.setEdorType("CM");
		tLPEdorItemSchema.setContNo("HB310422361000083");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo("000025723");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorAppDate("2006-03-03");
		mLPEdorItemSet.add(tLPEdorItemSchema);

		tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060303000009");
		tLPEdorItemSchema.setEdorType("CM");
		tLPEdorItemSchema.setContNo("HB310422361000089");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo("000025723");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorAppDate("2006-03-03");
		mLPEdorItemSet.add(tLPEdorItemSchema);

		tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060303000009");
		tLPEdorItemSchema.setEdorType("CM");
		tLPEdorItemSchema.setContNo("HB310422361000090");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo("000025723");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorAppDate("2006-03-03");
		mLPEdorItemSet.add(tLPEdorItemSchema);

		tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060303000009");
		tLPEdorItemSchema.setEdorType("CM");
		tLPEdorItemSchema.setContNo("HB310526161000045");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo("000025723");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorAppDate("2006-03-03");
		mLPEdorItemSet.add(tLPEdorItemSchema);

		tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060303000009");
		tLPEdorItemSchema.setEdorType("CM");
		tLPEdorItemSchema.setContNo("HB310526161000069");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo("000025723");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorAppDate("2006-03-03");
		mLPEdorItemSet.add(tLPEdorItemSchema);

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(mLPEdorItemSet);
		tVData.add(tLPEdorAppSchema);

		PEdorAppItemBL tPEdorAppItemBL = new PEdorAppItemBL();

		if (!tPEdorAppItemBL.submitData(tVData, "INSERT||EDORITEM")) {
			logger.debug(tPEdorAppItemBL.mErrors.getError(0).errorMessage);
		}

	}
}
